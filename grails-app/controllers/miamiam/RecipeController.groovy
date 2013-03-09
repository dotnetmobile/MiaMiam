package miamiam

import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.Chunk
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.Image;

import org.springframework.dao.DataIntegrityViolationException


class RecipeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def searchableService // inject the service
	
	def search () {
		generatePdf()
		
		def query = params.q
		
		if(query) {
			String filter = query
			filter = filter.replaceAll('\\*','')
			//def srchResults = searchableService.search("*" + query + "*")
			def srchResults = searchableService.search("*" + filter + "*")
			render(view: "list",
				   model: [recipeInstanceList: srchResults.results,
						 recipeInstanceTotal:srchResults.total])
		} else {
			redirect(action: "list", params: params)
		}
	}
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		params.sort = params.sort ?: "name"
		params.order = params.order ?: "asc"
		
        [recipeInstanceList: Recipe.list(params), recipeInstanceTotal: Recipe.count()]
    }

    def create() {
        [recipeInstance: new Recipe(params)]
    }

    def save() {
        def recipeInstance = new Recipe(params)
        if (!recipeInstance.save(flush: true)) {
            render(view: "create", model: [recipeInstance: recipeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'recipe.label', default: 'Recipe'), recipeInstance.id])
        redirect(action: "show", id: recipeInstance.id)
    }

    def show(Long id) {
        def recipeInstance = Recipe.get(id)
        if (!recipeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), id])
            redirect(action: "list")
            return
        }

        [recipeInstance: recipeInstance]
    }

    def edit(Long id) {
        def recipeInstance = Recipe.get(id)
        if (!recipeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), id])
            redirect(action: "list")
            return
        }

        [recipeInstance: recipeInstance]
    }

    def update(Long id, Long version) {
        def recipeInstance = Recipe.get(id)
        if (!recipeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (recipeInstance.version > version) {
                recipeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'recipe.label', default: 'Recipe')] as Object[],
                          "Another user has updated this Recipe while you were editing")
                render(view: "edit", model: [recipeInstance: recipeInstance])
                return
            }
        }

        recipeInstance.properties = params

        if (!recipeInstance.save(flush: true)) {
            render(view: "edit", model: [recipeInstance: recipeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'recipe.label', default: 'Recipe'), recipeInstance.id])
        redirect(action: "show", id: recipeInstance.id)
    }

    def delete(Long id) {
        def recipeInstance = Recipe.get(id)
        if (!recipeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recipe.label', default: 'Recipe'), id])
            redirect(action: "list")
            return
        }

        try {
			deleteAllRecipeDependencies(recipeInstance)
            recipeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recipe.label', default: 'Recipe'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recipe.label', default: 'Recipe'), id])
            redirect(action: "show", id: id)
        }
    }
	
	void deleteAllRecipeDependencies(Recipe recipe) {
		List<PhotoStep> photoStepList = PhotoStep.findAllWhere(recipe: recipe)
				
		for (PhotoStep iterator in photoStepList) {
			iterator.delete(flush: true)
		}

	}
	
	def generatePdf() {
		// step 1
		def document = new Document()
		println("Document Created")
		
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream("Micheldocument.pdf"))
		println("PdfWriter Created")
		
		// step 3
		document.open()
		println("Document Opened")
		
		def allRecipes = Recipe.list(sort:'name')
		
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
		Font font18 = new Font(bf, 18);
		Font font12 = new Font(bf, 12);
		
		// step 4
		for (Recipe iterator:allRecipes) {
			document.newPage()
			
			Paragraph paragraph1 = new Paragraph(iterator.name, font18)
			document.add(paragraph1)
			
			Paragraph paragraph2 = new Paragraph("", font12)
			paragraph2.add(new LineSeparator(0.5f, 100, null, 0, -5))
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add("Categorie: " + iterator.category.name)
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add("Ingredients:") 
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add(iterator.ingredient)
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add("Description:")
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add(iterator.description)
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add(Chunk.NEWLINE)
			
			if (iterator.photoSteps.size()>0) {
				paragraph2.add("Photo:")
				Image img = Image.getInstance(256, 1, 3, 8, iterator.photoSteps.toList().get(0));
				img.scaleAbsolute(256, 50);
				
				paragraph2.add(img)
				paragraph2.add(Chunk.NEWLINE)
				paragraph2.add(Chunk.NEWLINE)
			}
			document.add(paragraph2)
		}
		
		// step 5
		document.close()
		println("Document Closed")
		
	}

}

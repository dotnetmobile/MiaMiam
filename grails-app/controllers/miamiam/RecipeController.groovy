package miamiam

import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.fonts.otf.TableHeader
import com.itextpdf.text.Chunk
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;

import org.springframework.dao.DataIntegrityViolationException


class RecipeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def searchableService // inject the service
	
	def search () {
		
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
		PdfGenerator pdfGenerator = new PdfGenerator()

		// force download
		def fileName = "PiqueAssiette.pdf"
		def baseFolder = grailsAttributes.getApplicationContext().getResource("/images/").getFile().toString()

		//response.setContentType("application/octet-stream")
		response.setContentType("application/pdf")
		response.setHeader "Content-disposition", "attachment; filename=\"${fileName}\""
		response.outputStream <<  pdfGenerator.extractAllRecipesInMemory(baseFolder).toByteArray()
		response.outputStream.flush()

		return true

		//redirect(action: "list", params: params)
		
	}

	def generatePdfSingleRecipe(Long id) {
		def recipeInstance = Recipe.get(id)
		
		PdfGenerator pdfGenerator = new PdfGenerator()

		// force download
		def fileName = recipeInstance.name + ".pdf"

		//response.setContentType("application/octet-stream")
		response.setContentType("application/pdf")
		response.setHeader "Content-disposition", "attachment; filename=\"${fileName}\""
		response.outputStream <<  pdfGenerator.extractRecipeInMemory(recipeInstance).toByteArray()
		response.outputStream.flush()

		return true

		//redirect(action: "list", params: params)
		
	}

}

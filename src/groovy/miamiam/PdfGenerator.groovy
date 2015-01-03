/*  <MiaMiam: Web application for publishing recipes
    Copyright (C) <2014>  <Michel Petrovic> <email: dotnetmobile@gmail.com>

    Contributor(s): _____________________.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package miamiam

import com.itextpdf.awt.geom.Rectangle
import com.itextpdf.text.Anchor
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.PageSize
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Chunk
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Image
import com.itextpdf.text.Phrase
import com.itextpdf.text.Element
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.fonts.otf.TableHeader
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfWriter

class PdfGenerator {
	BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
	
	Font font40 = new Font(bf, 40);
	Font font20 = new Font(bf, 20);
	Font font18 = new Font(bf, 18);
	Font font12 = new Font(bf, 12);

	Document document
	ByteArrayOutputStream exportRecipes
	boolean exportAllRecipes = false

	/**
	 * Exports all recipes
	 * @return a byte array containing all recipes
	 */
	ByteArrayOutputStream extractAllRecipesInMemory(def folder) {
		exportAllRecipes = true
		
		initDocument()
		
		createBookCover(folder)

		createIntroduction(folder)
		
		createAllRecipes()
		
		document.close()

		return exportRecipes
	}

	
	/**
	 * Exports one recipe
	 * @return a byte array containing only one recipe
	 */
	ByteArrayOutputStream extractRecipeInMemory(Recipe recipe) {
		
		initDocument()
		
		addRecipe(recipe)
		
		document.close()

		return exportRecipes
	}
	
	/**
	 * Adds to the document one recipe
	 * @param recipe selected recipe
	 */
	void addRecipe(Recipe recipe) {					

		addTitleRecipe(recipe)
		
		Paragraph paragraph2 = new Paragraph("", font12)
		
		paragraph2.add(new LineSeparator(0.5f, 100, null, 0, -5))
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add("Catégorie: " + recipe.category.name)
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add("Ingrédients:")
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add(recipe.ingredient)
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add("Description:")
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add(recipe.description)
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add(Chunk.NEWLINE)
		
		document.add(paragraph2)
		document.add(Chunk.NEXTPAGE)
		document.newPage()

		addPhotos(recipe)
	}

	private addTitleRecipe(Recipe recipe) {
		// name of the recipe
		def recipeTitle = new Chunk(recipe.name, font18).setLocalDestination(recipe.name)
		//Paragraph paragraph1 = new Paragraph(recipe.name, font18)
		Paragraph paragraph1 = new Paragraph(recipeTitle)
		document.add(paragraph1)
	}


	/**
	 * Initializes the document
	 */
	void initDocument() {
		// step 1
		document = new Document(PageSize.A4, 36, 36, 54, 36)
		
		// step 2
		exportRecipes = new ByteArrayOutputStream()
		PdfWriter writer = PdfWriter.getInstance(document, exportRecipes)
		
		// assign the creation of the header and footer
		HeaderFooter event = new HeaderFooter();
		writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
		writer.setPageEvent(event);
		
		// step 3
		document.open()
		
		document.addAuthor("Slavica Petrovic")
		document.addCreator("Slavica Petrovic")
		document.addHeader("Liste des recettes en provenance de www.piqueassiette.com","")

	}
	
	/**
	 * Creates book cover
	 */
	void createBookCover(def folder) {		
		def bookCover = new Chunk("Pique Assiette", font40)
		Paragraph paragraph1 = new Paragraph(bookCover)
		paragraph1.setAlignment(Element.ALIGN_CENTER);
		paragraph1.setSpacingBefore(350);
		
		Image image = Image.getInstance(folder + "/fait_maison.png")

		
		PdfPTable table = new PdfPTable(1)
		
		table.setSpacingBefore(180f);
		table.setSpacingAfter(10f);
		
		PdfPCell cell = new PdfPCell(image, false)
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		
		table.addCell(cell)

		paragraph1.add(table)
		
		document.add(paragraph1)
		document.add(Chunk.NEWLINE)

		
		document.newPage()
	}
	
	/**
	 * Creates table of contents
	 * @param allRecipes all recipes available
	 */
	void createTOC(List<Recipe> allRecipes) {					
		def tocTitle = new Chunk("Liste des recettes", font20)

		Paragraph paragraph1 = new Paragraph(tocTitle)
		document.add(paragraph1)
		document.add(Chunk.NEWLINE)

		allRecipes.each {recipeIterator ->			

			def link = new Chunk("          " + recipeIterator.name, font12).setLocalGoto(recipeIterator.name)

			document.add(new Paragraph(link));	
		}
		
		document.newPage()
		
	}

	/**
	 * Creates table of contents
	 * @param allRecipes all recipes available
	 */
	void createCategoryTOC(List<Recipe> allRecipes) {
		boolean displayCategory = true
		
		allRecipes.each {recipeIterator ->

			if (displayCategory) {
				def categoryName = new Chunk("   " + recipeIterator.category.name, font12)
				
				document.add(new Paragraph(categoryName));
				
				displayCategory = false				
			}
			
			def link = new Chunk("          " + recipeIterator.name, font12).setLocalGoto(recipeIterator.name)

			document.add(new Paragraph(link));
		}
		
	}

	/**
	 * Creates author's introduction
	 */
	private createIntroduction(def folder) {
		def preface = new Chunk("Préface", font20)

		Paragraph paragraph1 = new Paragraph(preface)
		paragraph1.add(new LineSeparator(0.5f, 100, null, 0, -5))
		document.add(paragraph1)
		document.add(Chunk.NEWLINE)
		
		def aPropos = new Chunk("""Il y a des choses qu'on aime partager entre amies, familles et connaissances, ce sont nos recettes préférées. 
		C'est la raison pour laquelle j'ai voulu créer mon site pour faire plaisir et se faire plaisir!
		Ma cuisine se veut facile, gouteuse, respectueuse des saisons et hétéroclite.

		Bon appétit! 

		Slavica Petrovic""", font12)
		
		Paragraph paragraph2 = new Paragraph(aPropos)
		document.add(paragraph2)
		document.add(Chunk.NEWLINE)


		Paragraph paragraph3 = new Paragraph()
		paragraph3.setAlignment(Element.ALIGN_CENTER);
		
		Image image = Image.getInstance(folder + "/miamiam_logo.png")

		
		PdfPTable table = new PdfPTable(1)
		
		table.setSpacingBefore(80f);
		table.setSpacingAfter(10f);
		
		PdfPCell cell = new PdfPCell(image, false)
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		
		table.addCell(cell)

		paragraph3.add(table)

		document.add(paragraph3)	
				
		document.newPage()
	}
	
	/**
	 * Creates all recipes
	 * 
	 */
	private createAllRecipes() {
		def tocTitle = new Chunk("Liste des recettes", font20)

		Paragraph paragraph1 = new Paragraph(tocTitle)
		document.add(paragraph1)
		document.add(Chunk.NEWLINE)

 
		List<Recipe> allRecipes = new ArrayList<Recipe>()
		
		def categories = Category.list(sort:'name')
		
		categories.each { categoryIterator ->
			List<Recipe> recipesCategory = new ArrayList<Recipe>()
			
			def subListRecipes 
			
			def recipeCriteria = Recipe.createCriteria()
			
			subListRecipes = recipeCriteria.list(sort:'name') {
				eq("category.id", categoryIterator.id)
			}	
			
			subListRecipes.each { rec-> 
				recipesCategory.add(rec)
				allRecipes.add(rec)
			}
			
			createCategoryTOC(recipesCategory)
		}
				
		
		
		document.newPage()
		
		allRecipes.each { recipe->
			addRecipe(recipe)
		}
	}

	/**
	 * Creates the page containing the photos for the corresponding recipe
	 * @param recipe recipe
	 */
	private addPhotos(Recipe recipe) {
		// don't add an image page if no image is available
		if (recipe.photoSteps.size()==0) return
		
		addTitleRecipe(recipe)
		
		Paragraph paragraph = new Paragraph("", font12)
		paragraph.add(new LineSeparator(0.5f, 100, null, 0, -5))
		paragraph.add(Chunk.NEWLINE)
		paragraph.add(Chunk.NEWLINE)

		int totalPhotos = recipe.photoSteps.size()
		
		if (totalPhotos == 1)
			addSinglePhoto(recipe, paragraph)
		else if (totalPhotos == 2)
			addDualPhotos(recipe, paragraph)
		else
			addTablePhotos(recipe, paragraph)
/*			
		recipe.photoSteps.each {iteratorPhoto ->
			Image img = Image.getInstance(iteratorPhoto.photo);
			//img.scaleAbsolute(250, 180);

			paragraph.add(img)
		}
*/
		document.add(paragraph)
		document.add(Chunk.NEXTPAGE)
		document.newPage()
	}

	private void addSinglePhoto(Recipe recipe, Paragraph paragraph) {
		addDualPhotos(recipe, paragraph)
	}

	private void addDualPhotos(Recipe recipe, Paragraph paragraph) {
		int size = recipe.photoSteps.size()
		int columns
		
		if (size == 0) return
		else if (size == 1) columns = 1
		else columns = 2
		
		
		PdfPTable table = new PdfPTable(columns)
		
		table.setSpacingBefore(50f)
		table.setSpacingAfter(10f)
		
		recipe.photoSteps.each { photoIterator ->
			Image image = Image.getInstance(photoIterator.photo)
			
			PdfPCell cell = new PdfPCell(image, false)
			cell.setBorder(Rectangle.NO_BORDER)
			cell.setHorizontalAlignment(Element.ALIGN_CENTER)
			cell.setVerticalAlignment(Element.ALIGN_CENTER)
			cell.setPadding(5f)
			
			table.addCell(cell)
		}
		
		paragraph.add(table)
	}

	private void addTablePhotos(Recipe recipe, Paragraph paragraph) {
		addDualPhotos(recipe, paragraph)
	}

    /** Inner class to add a header and a footer. */
    class HeaderFooter extends PdfPageEventHelper {
        /** Alternating phrase for the header. */
        Phrase[] header = new Phrase[2];
        /** Current page number (will be reset for every chapter). */
        int pagenumber;
 
        /**
         * Initialize one of the headers.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document) {
			Anchor anchor = new Anchor("www.piqueassiette.com")
			anchor.setReference("http://www.piqueassiette.com")
			
            header[0] = new Phrase("")
			header[1] = new Phrase("")
			header[0].add(anchor)
			header[1].add(anchor)
        }
 
        /**
         * Initialize one of the headers, based on the chapter title;
         * reset the page number.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onChapter(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document, float,
         *      com.itextpdf.text.Paragraph)
         */
        public void onChapter(PdfWriter writer, Document document,
                float paragraphPosition, Paragraph title) {
            header[1] = new Phrase(title.getContent());
            pagenumber = 1;
        }
 
        /**
         * Increase the page number.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onStartPage(PdfWriter writer, Document document) {
            pagenumber++;
        }
 
        /**
         * Adds the header and the footer.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document) {
			if (exportAllRecipes && pagenumber<=2) return
			
            Rectangle rect = writer.getBoxSize("art")
			
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, header[0], rect.right, rect.top, 0)

			float fX = (rect.left + rect.right) / 2
			float fY = rect.bottom - 18
			
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),fX , fY, 0)

        }
    }
}

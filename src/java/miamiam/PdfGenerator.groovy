package miamiam

import com.itextpdf.awt.geom.Rectangle
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
	Document document
	ByteArrayOutputStream exportRecipes

	ByteArrayOutputStream extractAllRecipesInMemory() {
		
		initDocument()
		
		def allRecipes = Recipe.list(sort:'name')

		allRecipes.each { recipe->
			addRecipe(recipe)
		}
		
		document.close()

		return exportRecipes
	}
	
	ByteArrayOutputStream extractRecipeInMemory(Recipe recipe) {
		
		initDocument()
		
		addRecipe(recipe)
		
		document.close()

		return exportRecipes
	}
	void addRecipe(Recipe recipe) {
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
		Font font18 = new Font(bf, 18);
		Font font12 = new Font(bf, 12);
					
		// name of the recipe
		Paragraph paragraph1 = new Paragraph(recipe.name, font18)
		document.add(paragraph1)
		
		Paragraph paragraph2 = new Paragraph("", font12)
		
		paragraph2.add(new LineSeparator(0.5f, 100, null, 0, -5))
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add("CatŽgorie: " + recipe.category.name)
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add(Chunk.NEWLINE)
		paragraph2.add("IngrŽdients:")
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
		

		recipe.photoSteps.each {iteratorPhoto ->
			Image img = Image.getInstance(iteratorPhoto.photo);
			//img.scaleAbsolute(250, 180);
			
			paragraph2.add(img)
		}

		paragraph2.add(Chunk.NEXTPAGE)
		document.add(paragraph2)
		
		//document.newPage()
	}

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
            header[0] = new Phrase("www.piqueassiette.com")
			header[1] = new Phrase("www.piqueassiette.com")
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
            Rectangle rect = writer.getBoxSize("art")
			
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, header[0], rect.right, rect.top, 0)

			float fX = (rect.left + rect.right) / 2
			float fY = rect.bottom - 18
			
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),fX , fY, 0)

        }
    }}

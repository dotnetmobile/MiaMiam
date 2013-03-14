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

	ByteArrayOutputStream generateInMemoryPdf() {
		
		// step 1
		def document = new Document(PageSize.A4, 36, 36, 54, 36)
		
		
		document.addAuthor("Slavica Petrovic")
		document.addCreator("Slavica Petrovic")
		document.addHeader("Liste des recettes en provenance de www.piqueassiette.com","")
		
		println("Document Created")
		
		// step 2
		def exportRecipes = new ByteArrayOutputStream()
		PdfWriter writer = PdfWriter.getInstance(document, exportRecipes)
		println("PdfWriter Created")
		
		// assign the creation of the header and footer
		HeaderFooter event = new HeaderFooter();
		writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
		writer.setPageEvent(event);

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

			//event..setHeader("Liste des recettes en provenance de www.piqueassiette.com")
			
			Paragraph paragraph1 = new Paragraph(iterator.name, font18)
			document.add(paragraph1)
			println(iterator.name)
			
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
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add("Description:")
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add(iterator.description)
			paragraph2.add(Chunk.NEWLINE)
			paragraph2.add(Chunk.NEWLINE)
			
			if (iterator.photoSteps.size()>0) {
				//paragraph2.add("Photo:")
				PhotoStep step = iterator.photoSteps.toList().get(0)
				
				Image img = Image.getInstance(step.photo);
				img.scaleAbsolute(250, 180);
				
				paragraph2.add(img)
				paragraph2.add(Chunk.NEWLINE)
				paragraph2.add(Chunk.NEWLINE)
			}
			document.add(paragraph2)
		}
		
		// step 5
		document.close()
		println("Document Closed")

		return exportRecipes
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
            header[0] = new Phrase("www.piqueassiette.com");
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
			
            switch(writer.getPageNumber() % 2) {
/*            case 0:
                ColumnText.showTextAligned(writer.getDirectContent(),
                        Element.ALIGN_RIGHT, header[0],
                        rect.getRight(), rect.getTop(), 0);
                break;
            case 1:
                ColumnText.showTextAligned(writer.getDirectContent(),
                        Element.ALIGN_LEFT, header[1],
                        rect.getLeft(), rect.getTop(), 0);
                break;
*/                
				case 0:
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, header[0], rect.right, rect.top, 0)
				break;
			case 1:
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, header[1], rect.left, rect.top, 0) 
				break;
            }
/*			
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),
                    (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
*/
			float fX = (rect.left + rect.right) / 2
			float fY = rect.bottom - 18
			
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),fX , fY, 0)

        }
    }}

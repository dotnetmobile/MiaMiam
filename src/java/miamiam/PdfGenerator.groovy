package miamiam

import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Chunk
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import com.itextpdf.text.pdf.fonts.otf.TableHeader
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfPCell;


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
/*
		TableHeader event = new TableHeader();
		writer.setPageEvent(event);
*/
		// step 3
		document.open()
		println("Document Opened")
		
		def allRecipes = Recipe.list(sort:'name')
		
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
		Font font18 = new Font(bf, 18);
		Font font12 = new Font(bf, 12);
		
		// step 4
		for (Recipe iterator:allRecipes) {
			//document.add(getTable(new Date()))
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
	
	PdfPTable getTable(Date day)
	throws DocumentException, IOException {
	   final Font f = FontFactory.getFont(BaseFont.COURIER, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED, 10);
		
	    final float[] widths = [0.5f, 0.5f, 1.25f, 1.25f ] as float[];
	    final PdfPTable table = new PdfPTable(widths); // Code 1
		
		final PdfPCell dateCell = new PdfPCell(new Phrase("HEELO", f));
		table.addCell(dateCell);
		
		final PdfPCell timeCell = new PdfPCell(new Phrase("TTTT", f));
		table.addCell(timeCell);
		
		final PdfPCell nameCell = new PdfPCell(new Phrase("GGG", f));
		table.addCell(nameCell);
		
		final PdfPCell eventCell = new PdfPCell(new Phrase("HHHHH", f));
		table.addCell(eventCell);
    
		return table;
	}
}

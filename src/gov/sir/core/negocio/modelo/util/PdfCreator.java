package gov.sir.core.negocio.modelo.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;


import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import org.pdfbox.io.RandomAccess;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;

import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import gov.sir.print.common.Imprimible;

public class PdfCreator {
	/** Constante que define el tipo de documento */
    public static final String TIPO_DOCUMENTO = "TIPO_DOCUMENTO";

    /** Constatne que define el tamaño de pulgada */
    private static final int INCH = 72;

    /** Constante que define el ancho de una hoja carta */
    private static final double LETTER_WIDTH = 8.5 * INCH;

    /** Constante que define el alto de una hoja carta */
    private static final double LETTER_HEIGHT = 11 * INCH;
    
    public static final int MARGEN_IZQ = 36;
	/**	 */
	public static final int MARGEN_SUP = 50;
	
	Imprimible imprimible;
	String tipoImprimible;
	
	public PdfCreator() {	
	}
	
	public PdfCreator(Imprimible imprimible, String tipoImprimible) {
		this.imprimible = imprimible;
		this.tipoImprimible = tipoImprimible; 
	}

	public Imprimible getImprimible() {
		return imprimible;
	}

	public void setImprimible(Imprimible imprimible) {
		this.imprimible = imprimible;
	}

	public String getTipoImprimible() {
		return tipoImprimible;
	}

	public void setTipoImprimible(String tipoImprimible) {
		this.tipoImprimible = tipoImprimible;
	}
	
	/**
     * Generar un pdf
     * @param imprimible
     * @param request
     * @param response
     */
    public ByteArrayOutputStream generar(Imprimible imprimible) {
    	ByteArrayOutputStream outPdf = new ByteArrayOutputStream();
    	//File file = new File("");
    	PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pf = printJob.defaultPage();
        Paper papel = new Paper();
        double mWidth = LETTER_WIDTH;
        double mHeight = LETTER_HEIGHT;
        
        com.lowagie.text.Document pdfDocument = new com.lowagie.text.Document(
        		new com.lowagie.text.Rectangle((int)mWidth,(int)mHeight), (int)(INCH / 4), INCH / 4,(int)( mWidth - (0.5 * INCH)),(int)( mHeight - (0.5 * INCH)));

        papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH), mHeight - (0.5 * INCH));
        pf.setPaper(papel);
        
       
        imprimible.generate(pf);
    	
    	try {
    		
    		// step 3: we open the document
            PdfWriter writer = PdfWriter.getInstance(pdfDocument,outPdf);
            //writer.setViewerPreferences(PdfWriter.HideMenubar |PdfWriter.HideToolbar);
            //writer.setEncryption(PdfWriter.STRENGTH128BITS, null, null, 0);

            pdfDocument.open();

            PdfContentByte content = writer.getDirectContent();
            content.setColorFill(Color.WHITE);

            int num_pages = imprimible.getNumberOfPages();

            for (int i = 0; i < num_pages; i++) {
                Printable page = imprimible.getPrintable(i);

                // we create a template and a Graphics2D object that
                // corresponds with it
                PageFormat pageformat = imprimible.getPageFormat(i);
                int pageWidth = (int) pageformat.getWidth();
                int pageHeight = (int) pageformat.getHeight();
                PdfTemplate template = content.createTemplate(pageWidth,
                        pageHeight);
                template.setWidth(pageWidth);
                template.setHeight(pageHeight);

                Graphics2D g2d = template.createGraphics(pageWidth, pageHeight);
                g2d.setColor(Color.black);
                page.print(g2d, pageformat, 0);

                if (pageformat.getOrientation() == PageFormat.LANDSCAPE) {
                    content.addTemplate(template, 0, 1, -1, 0, pageHeight, 0);
                } else {
                    content.addTemplate(template, 0, 0);
                }
                pdfDocument.newPage();
            }
           
            pdfDocument.close();
        } catch (Exception e) {
        	Log.getInstance().error(PdfCreator.class, e);
        }
        
        //Mirar como se procesa

        return outPdf;
    }
    
    /**
     * Generar un pdf
     * @param imprimible
     * @param request
     * @param response
     */
    /*public ByteArrayOutputStream generarImprimibleAPIPDDocument(Imprimible imprimible, String idWorkFlow, String tipo) {
    	ByteArrayOutputStream respuesta = new ByteArrayOutputStream();
    	com.lowagie.text.Document pdfDocument = new Document (PageSize.A4);
    	PDDocument document = null;
    	ByteArrayOutputStream outPdf = new ByteArrayOutputStream();
    	PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pf = printJob.defaultPage();
        Paper papel = new Paper();
        double mWidth = LETTER_WIDTH;
        double mHeight = LETTER_HEIGHT;

        papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH), mHeight - (0.5 * INCH));
        pf.setPaper(papel);
        
        imprimible.generate(pf);
        
        String nombreImprimible = "Imprimible" + idWorkFlow;
        String nombreTotal = this.rutaImprimibles + nombreImprimible;
     
        File temp = null;
        String ubicacionArchivo = "";
        
    	try {
    		
    		temp = File.createTempFile(nombreImprimible,".pdf");

            String pathTemp = temp.getAbsolutePath();
            //logger.debug("PATH DEL ARCHIVO TEMPORAL " + pathTemp);
            ubicacionArchivo = pathTemp;// + "\\" + nombreImprimible;
            String pathTemporal = temp.getPath() + nombreImprimible ; 
            //temp.delete();
            
            //FileOutputStream fileout = new FileOutputStream(ubicacionArchivo);
            
          
            
    		// step 3: we open the document
            PdfWriter writer = PdfWriter.getInstance(pdfDocument,new FileOutputStream(ubicacionArchivo));
            //writer.setViewerPreferences(PdfWriter.HideMenubar |PdfWriter.HideToolbar);
            //writer.setEncryption(PdfWriter.STRENGTH128BITS, null, null, PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
            //writer.setEncryption(PdfWriter.STRENGTH40BITS, null, null, PdfWriter.AllowPrinting);

            pdfDocument.open();

            PdfContentByte content = writer.getDirectContent();
            content.setColorFill(Color.WHITE);

            int num_pages = imprimible.getNumberOfPages();
            
            for (int i = 0; i < num_pages; i++) {
                Printable page = imprimible.getPrintable(i);
                
                // we create a template and a Graphics2D object that
                // corresponds with it
                PageFormat pageformat = imprimible.getPageFormat(i);
                int pageWidth = (int) pageformat.getWidth();
                int pageHeight = (int) pageformat.getHeight();
                PdfTemplate template = content.createTemplate(pageWidth,
                        pageHeight);
                template.setWidth(pageWidth);
                template.setHeight(pageHeight);
                
                Graphics2D g2d = template.createGraphics(pageWidth, pageHeight);
                g2d.setBackground(Color.white);
                
                //--- Draw a border around the page
                Rectangle2D.Double border = new Rectangle2D.Double (0,
                                                                    0,
                                                                    pageformat.getImageableWidth(),
                                                                    pageformat.getImageableHeight());
               g2d.draw (border);
               
                page.print(g2d, pageformat, 0);
                                
                

                if (pageformat.getOrientation() == PageFormat.LANDSCAPE) {
                    content.addTemplate(template, 0, 1, -1, 0, pageHeight, 0);
                } else {
                    content.addTemplate(template, 0, 0);
                }
                
                pdfDocument.newPage();
            }
            
            //fileout.close();
            pdfDocument.close();
        } catch (Exception e) {
            logger.error(e);
        }
        
        try {
        	PDDocument doc = null;
            doc = PDDocument.load(ubicacionArchivo);
            
            ByteArrayOutputStream outPutSteam = new ByteArrayOutputStream();
            doc.save(outPutSteam);
            respuesta = outPutSteam;

        } catch (Exception e) {
        	logger.error(e);
        }
        return respuesta;
    }*/
    
   
	//Returns the contents of the file in a byte array.
	 public byte[] getBytesFromFile(File file) throws IOException {
		 InputStream is = new FileInputStream(file);
    
		 // Get the size of the file
		 long length = file.length();
    
		 // You cannot create an array using a long type.
		 // It needs to be an int type.
		 // Before converting to an int type, check
		 // to ensure that file is not larger than Integer.MAX_VALUE.
		 if (length > Integer.MAX_VALUE) {
			 // File is too large
		 }
    
		 // Create the byte array to hold the data
		 byte[] bytes = new byte[(int)length];
    
		 // Read in the bytes
		 int offset = 0;
		 int numRead = 0;
		 while (offset < bytes.length
				&& (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			 offset += numRead;
		 }
    
		 // Ensure all the bytes have been read in
		 if (offset < bytes.length) {
			 throw new IOException("Could not completely read file "+file.getName());
		 }
    
		 // Close the input stream and return bytes
		 is.close();
		 return bytes;
	 }
	 
	public void generarProcesoImpresion(byte[] pdfInfo, PrintService servicio) {
			PDDocument document = null;
			try
	        {
				ByteArrayInputStream arraInp = new ByteArrayInputStream(pdfInfo);
				BufferedInputStream stream = new BufferedInputStream(arraInp);
				document = this.load(stream, null);
	            printPdf(document,servicio);
	            if( document != null )
	            {
	                document.close();
	            }
	        } catch (Exception e) {
	        	Log.getInstance().error(PdfCreator.class, e);
	        }
		}
		 public static void printPdf(PDDocument document, PrintService servicio){
			 PrintService servicioImpresion;
			 PrinterJob job = PrinterJob.getPrinterJob();
			 try {
	        	   if (servicio != null){
	        		   servicioImpresion = servicio;
	        	   } else {
	        		   servicioImpresion = PrintServiceLookup.lookupDefaultPrintService();   
	        	   }
	        	   
	        	   job.setPageable(document);
	        	   //logger.debug("Información job: " + job.toString() + ", Nombre del servicios " + servicioImpresion.getName());
	               job.setPrintService(servicioImpresion); 
	        	   job.print(); 
	        } catch (PrinterException e) { 
	        	Log.getInstance().debug(PdfCreator.class, "Error de impresión: " , e);
	        }
	    }
		
		public static PDDocument load(BufferedInputStream bufferStream, RandomAccess scratchFile ) throws IOException {
			PDFParser parser = new PDFParser(bufferStream, scratchFile );
			parser.parse();
		    return parser.getPDDocument();
		}
}

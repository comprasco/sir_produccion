/*
 * Clase de ultilidades para crear PDFs.
 */
package gov.sir.core.negocio.modelo.imprimibles.util;

import gov.sir.core.negocio.acciones.comun.ANPago;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.util.PdfCreator;
import gov.sir.fenrir.FenrirProperties;
import gov.sir.hermod.HermodProperties;
import gov.sir.hermod.workflow.Processor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author: Fernando Padilla Velez, 02/07/2015
 * @change: 1195.IMPRESION.CERTIFICADOS.EXENTOS.PDF
 */
public class PDFUtil {
    
    private static final int INCH = 72;
    
    /** Constante que define el ancho de una hoja carta */
    private static final double LETTER_WIDTH = 8.5 * INCH;
    /** Constante que define el alto de una hoja carta */
    private static final double LETTER_HEIGHT = 11 * INCH;

    public static void generarCertificadoPDF(ImprimibleCertificado imprimibleCertificado, String turnoPadre) {
		try {
                ByteArrayOutputStream pdfFormulario = null;
                String rutaTempPDF = HermodProperties.getInstancia().getProperty(HermodProperties.HERMOD_RUTA_TEMP_GENERACION);
                PdfCreator pdf = new PdfCreator();
                imprimibleCertificado.setPdf(true);
                pdfFormulario = pdf.generar(imprimibleCertificado);
                
                String ruta = rutaTempPDF + FenrirProperties.getInstancia().getProperty(FenrirProperties.SO) + turnoPadre;
                
                File dir = new File(ruta);
                if (!dir.exists()) {
                    dir.mkdir();                    
                }

                File file = new File(ruta + FenrirProperties.getInstancia().getProperty(FenrirProperties.SO) + imprimibleCertificado.getFolio().getIdMatricula() + ".pdf");
                
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(pdfFormulario.toByteArray());
                fos.close();

            } catch (IOException eio) {
                Log.getInstance().error(ANPago.class, "ERROR GENERANDO PDF (IO): " + eio.getMessage());
            } catch (Exception e) {
                Log.getInstance().debug(ANPago.class, "ERROR GENERANDO PDF: "+e.getMessage());
            }
    }
    
    /*public static void generarCertificadoPDF(ImprimibleCertificado imprimibleCertificado, String turnoPadre) {
		try {

			PrinterJob printJob = PrinterJob.getPrinterJob();
			PageFormat pf = printJob.defaultPage();

			Paper papel = new Paper();
			double mWidth = LETTER_WIDTH;
			double mHeight = LETTER_HEIGHT;

			papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
					mHeight - (0.5 * INCH));
			pf.setPaper(papel);

                        Document pdfDocument = new Document();
                        
                        String rutaTempPDF = HermodProperties.getInstancia().
                                getProperty(HermodProperties.HERMOD_RUTA_TEMP_GENERACION) +
                                FenrirProperties.getInstancia().getProperty(FenrirProperties.SO) 
                                 + turnoPadre;
                        
			String certificado = rutaTempPDF + 
                                FenrirProperties.getInstancia().getProperty(FenrirProperties.SO)+
                                imprimibleCertificado.getFolio().getIdMatricula()+ ".pdf";
                        
                        File dir = new File(rutaTempPDF);
                        if (!dir.exists()) {
                            dir.mkdir();                    
                        }
                        
                        PdfWriter writer = PdfWriter.getInstance(pdfDocument,
					new FileOutputStream(certificado));
			//writer.setViewerPreferences(PdfWriter.HideMenubar
					//| PdfWriter.HideToolbar);
			//writer.setEncryption(null, null, PdfWriter.ALLOW_DEGRADED_PRINTING,
					//PdfWriter.STANDARD_ENCRYPTION_128);

			imprimibleCertificado.generate(pf);

			Pageable imprimible = (Pageable) imprimibleCertificado;

			pdfDocument.open();
                        PdfContentByte content = writer.getDirectContent();

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
			e.printStackTrace();
		}
	}    */
}
/*
 * PrintWorker.java
 *
 * Created on 16 de septiembre de 2004, 15:50
 */

package gov.sir.print.common;

import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConsulta;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFotocopiaCrearSolicitud;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.Serializable;
import java.util.Properties;

import org.auriga.core.modelo.TransferObject;

/**
 *
 * @author  dcantor
 */
public abstract class Imprimible implements Pageable, Serializable {
    
	protected static final int INCH = 72;
	protected static final double LETTER_WIDTH = 8.5 * INCH;
	protected static final double LETTER_HEIGHT = 11 * INCH;
        private static final long serialVersionUID = 1L;
    private Properties properties;
    private int numberOfPages = 1;
    private TransferObject transferObject;
    private String tipoImprimible;
    
    public Imprimible(String tipoImprimible){
    	this.tipoImprimible=tipoImprimible;
    }
    /**
     * @param properties
     */
    public final void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * @return las propiedades del imprimible
     */
    public final Properties getProperties() {
        return properties;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * @param numberOfPages
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    /**
     * @throws PrinterException
     */
    public void imprimir() throws PrinterException {    	
        PrinterJob printJob = PrinterJob.getPrinterJob();
        
        PageFormat pf = obtenerPageFormat(); 
        if(pf==null){
			pf = printJob.defaultPage();	
        }
        
        Paper papel = determinarTamanoPapel();
        
        pf.setPaper(papel);
        
        this.generate(pf);
        printJob.setPageable(this);
        printJob.print();
    }
    


    /**
     * @param service
     * @throws PrinterException
     */
    public void imprimir(javax.print.PrintService service) throws PrinterException {
        PrinterJob printJob = PrinterJob.getPrinterJob();        
		printJob.setPrintService(service);
		
		PageFormat pf = obtenerPageFormat(); 
		if(pf==null)
		{
			pf = printJob.defaultPage();	
		}
		
        Paper papel = determinarTamanoPapel();
        
        pf.setPaper(papel);
        
        this.generate(pf);
        printJob.setPageable(this);
        printJob.print();
    }

    /**
     * @param pf
     */
    public void generate(PageFormat pf) {}

    /**
     * @param graphics
     * @param pageFormat
     * @param pageIndex
     * @return codigo de impreson
     * @throws PrinterException
     */
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        return Printable.PAGE_EXISTS;
    }

    public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
        return PrinterJob.getPrinterJob().defaultPage();
    }

    public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
        return null;
    }

    /**
     * @return Objeto transfer
     */
    public TransferObject getTransferObject() {
        return this.transferObject;
    }

    /**
     * @param transferObject
     */
    public void setTransferObject(TransferObject transferObject) {
        this.transferObject = transferObject;
    }
    

	/**
	 * @return
	 */
	private PageFormat obtenerPageFormat() {
	
		PageFormat pf = null;
		
		if(this instanceof ImprimibleRecibo){		
			pf = new PageFormat();		
			pf.setOrientation( java.awt.print.PageFormat.PORTRAIT ); 	
		}
		
		return pf ;
	}

	
	/**
	 * @return
	 */
	private java.awt.print.Paper determinarTamanoPapel() {
				
		java.awt.print.Paper result = new java.awt.print.Paper();

		if(this instanceof ImprimibleRecibo){		
			
			ImprimibleRecibo impRecibo = (ImprimibleRecibo)this;
			
			if(impRecibo.isTamanoCarta()){

				double mWidth = LETTER_WIDTH;
				double mHeight = LETTER_HEIGHT;
				result.setImageableArea(INCH / 4, INCH / 4, mWidth - 0.5 * INCH, mHeight - 0.5 * INCH);				
			
			}else{
				double xWidth  = 612d;
								double xHeight = 395d;			
								double margin = 5.0d;
			
								result.setSize( xWidth , xHeight );
								result.setImageableArea( margin
													  , margin
													  , xWidth - (2 * margin) 
													  , xHeight - (2 * margin)  );
									
			}
						
		}else if (this instanceof ImprimibleConsulta) {
				
				ImprimibleConsulta imprimibleConsulta = (ImprimibleConsulta)this;
				if(imprimibleConsulta.isTamanoCarta()){
					double mWidth = LETTER_WIDTH;
					double mHeight = LETTER_HEIGHT;
					result.setImageableArea(INCH / 4, INCH / 4, mWidth - 0.5 * INCH, mHeight - 0.5 * INCH);
				} else {
					double xWidth  = 612d;
					double xHeight = 395d;			
					double margin = 5.0d;

					result.setSize( xWidth , xHeight );
					result.setImageableArea( margin
										  , margin
										  , xWidth - (2 * margin) 
										  , xHeight - (2 * margin)  );
				}
			
		} else if (this instanceof ImprimibleFotocopiaCrearSolicitud) {
			
			ImprimibleFotocopiaCrearSolicitud imprimibleConsulta = (ImprimibleFotocopiaCrearSolicitud)this;
			if(imprimibleConsulta.isTamanoCarta()){
				double mWidth = LETTER_WIDTH;
				double mHeight = LETTER_HEIGHT;
				result.setImageableArea(INCH / 4, INCH / 4, mWidth - 0.5 * INCH, mHeight - 0.5 * INCH);
			} else {
				double xWidth  = 612d;
				double xHeight = 395d;			
				double margin = 5.0d;

				result.setSize( xWidth , xHeight );
				result.setImageableArea( margin
									  , margin
									  , xWidth - (2 * margin) 
									  , xHeight - (2 * margin)  );
			}
		
		}else {
			double mWidth = LETTER_WIDTH;
			double mHeight = LETTER_HEIGHT;
			result.setImageableArea(INCH / 4, INCH / 4, mWidth - 0.5 * INCH, mHeight - 0.5 * INCH);
		}
		return result ;
	}	



	/**
	 * @return Returns the tipoImprimible.
	 */
	public String getTipoImprimible() {
		return tipoImprimible;
	}
	
}
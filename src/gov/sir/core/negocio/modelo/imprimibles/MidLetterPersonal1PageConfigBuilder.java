/**
 * 
 */
package gov.sir.core.negocio.modelo.imprimibles;

import java.awt.print.PageFormat;

/**
 * Tamaño 
 * Alto:  13.57 cms   |  5.34 pulg |  384
 * Ancho: 21.59 cms   |  8.50 pulg |  612  -- ancho de hoja carta 
 * 
 * */
public class MidLetterPersonal1PageConfigBuilder implements PageConfigBuilder {

	public PageFormat buildPageFormat() {
		java.awt.print.Paper paper 
		= pageConf_buildPaper();
		
		PageFormat pageFormat 
		= new PageFormat();
		
		pageConf_configurePageFormat( pageFormat );		
		
		pageFormat.setPaper( paper );	
		
		return pageFormat;
	}
	
	
	public java.awt.print.Paper 
	pageConf_buildPaper() {
		
		java.awt.print.Paper result = new java.awt.print.Paper();
		
		double xWidth  = 612d;//396d;//
		double xHeight = 384d; // 792d;//396d;//396d;//
		
		double margin = 14.17322d;
		
		result.setSize( xWidth , xHeight );
		result.setImageableArea( margin
							  , margin
							  , xWidth - (2 * margin) 
							  , xHeight - (2 * margin) + 4  );
		// papel.setSize( mWidth,mHeight );		
		// papel.setImageableArea( INCH / 4, INCH / 4, mWidth - 0.5 * INCH, mHeight - 0.5 * INCH);
		
		
		return( result );
		
		// double[] thisTrMatrix = {				
		//	0d, 1d
		//	  ,-1d, 0d
		//	  , 0d, 0d			
		// };
				
	}	
	  
	public void 
	pageConf_configurePageFormat( java.awt.print.PageFormat pf ) {
		
		pf.setOrientation( java.awt.print.PageFormat.PORTRAIT );
		
	}
	
	
}
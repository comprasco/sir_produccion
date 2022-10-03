/**
 * 
 */
package gov.sir.core.negocio.modelo.imprimibles;

import java.awt.print.PageFormat;

/**
 * Tamaño 
 * Alto: 14 cms
 * Ancho: 17.2 cms 
 * 
 * Tamaño 
 * Alto:  13.97 cms   |  5.50 pulg |  396
 * Ancho: 17.20 cms   |  6.77 pulg |  487  -- ancho de hoja carta 

 * 
 * */

public class PersonalSize1PageConfigBuilder implements PageConfigBuilder {

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
		
		double xWidth  = 487d;	// 17.2cm
		double xHeight = 396d;  // 14.0cm ~ mid-letter-height
		
		double margin = 14.17322d;
		
		result.setSize( xWidth , xHeight );
		result.setImageableArea( margin
							  , margin
							  , xWidth - (2 * margin) 
							  , xHeight - (2 * margin)  );
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
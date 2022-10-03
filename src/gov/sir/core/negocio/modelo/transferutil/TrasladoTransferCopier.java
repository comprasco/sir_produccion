package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TrasladoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Traslado transferObject = new gov.sir.core.negocio.modelo.Traslado();
		cache.put(enhancedObject, transferObject );
		
		  // IdTraslado
  	    try {
	  	transferObject.setIdTraslado(enhancedObject.getIdTraslado());
	  	} catch (Throwable th ) {}
	  			
		  // FolioOrigen
//  	  gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced folioOrigenEnh = null;
//	  try {
//	  	folioOrigenEnh = enhancedObject.getFolioOrigen();
//	  	} catch (Throwable th) {}
//	  	if (folioOrigenEnh != null)
//	  	{
//	  		boolean assigned = false;
//	  			  			  		if (!assigned)
//	  		{
//	  		gov.sir.core.negocio.modelo.Folio objTo =  (gov.sir.core.negocio.modelo.Folio)cache.get(folioOrigenEnh);
//	  		if (objTo == null)
//	  		{
//	  			objTo = (gov.sir.core.negocio.modelo.Folio)FolioTransferCopier.deepCopy(folioOrigenEnh, cache);
//	  		}
//	  		transferObject.setFolioOrigen(objTo);
//	  		}
//	  			  		
//	  	}
	  			
		  // Resolucion
  	    try {
	  	transferObject.setResolucion(enhancedObject.getResolucion());
	  	} catch (Throwable th ) {}
	  			
		  // FechaTraslado
  		try {
		if (enhancedObject.getFechaTraslado() != null)
		{
	  	 transferObject.setFechaTraslado(new Date(enhancedObject.getFechaTraslado().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // FolioDestino
          /**
          * @author      :  Julio Alcazar
          * @change      :  Revision: FolioDestino cambio de ser un objeto Folio a ser String
          * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
          */
  	  try {
	  	transferObject.setFolioDestino(enhancedObject.getFolioDestino());

	  	} catch (Throwable th) {}	  	

          /**
         * @author      :  Julio Alcazar
         * @change      :  nuevas propiedades de el objeto
         * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
         */

          // Resolucion Destino
  	    try {
	  	transferObject.setResolucionDestino(enhancedObject.getResolucionDestino());
	  	} catch (Throwable th ) {}

            // Fecha Resolucion Origen
  	    try {
		if (enhancedObject.getFechaResolucionOrigen() != null)
	    {
                    transferObject.setFechaResolucionOrigen(new Date(enhancedObject.getFechaResolucionOrigen().getTime()));
	    }
	    } catch (Throwable th ) {}

            // Fecha Resolucion Destino
  	    try {
		if (enhancedObject.getFechaResolucionDestino() != null)
	    {
                    transferObject.setFechaResolucionDestino(new Date(enhancedObject.getFechaResolucionDestino().getTime()));
	    }
	    } catch (Throwable th ) {}
            /*
            * @author      : Carlos Mario Torres Urina
            * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
            */
  
          gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced trasladoDatosEnh = null;
	  try {
	  	trasladoDatosEnh = enhancedObject.getTrasladoDatos();
	  	} catch (Throwable th) {}
	  	if (trasladoDatosEnh != null)
	  	{
	  		boolean assigned = false;
	  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TrasladoDatos objTo =  (gov.sir.core.negocio.modelo.TrasladoDatos)cache.get(trasladoDatosEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TrasladoDatos)TrasladoDatosTransferCopier.deepCopy(trasladoDatosEnh, cache);
	  		}
	  		transferObject.setTrasladoDatos(objTo);
	  		}
	  			  		
	  	}
                /*028_589*/
                  // FechaTraslado
  		try {
		if (enhancedObject.getFechaConfirTras() != null)
		{
	  	 transferObject.setFechaConfirTras(new Date(enhancedObject.getFechaConfirTras().getTime()));
	  	}
	  	} catch (Throwable th ) {}	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Traslado transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdTraslado
  	    try {
	  	enhancedObject.setIdTraslado(transferObject.getIdTraslado());
	  	} catch (Throwable th ) {}
	  			
		  // FolioOrigen
//  	    gov.sir.core.negocio.modelo.Folio folioOrigen = null;
//	  	try {
//	  	folioOrigen = (gov.sir.core.negocio.modelo.Folio)transferObject.getFolioOrigen();
//	  	} catch (Throwable th ) {}
//	  	if (folioOrigen != null)
//	  	{
//	  		boolean assigned = false;
//	  			  			  		if (!assigned)
//	  		{
//	  		gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)cache.get(folioOrigen);
//	  		if (objEn == null)
//	  		{
//	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)FolioTransferCopier.deepCopy(folioOrigen, cache);
//	  		}
//	  		enhancedObject.setFolioOrigen(objEn);
//	  		}
//	  			  		
//	  	}
	  			
		  // Resolucion
  	    try {
	  	enhancedObject.setResolucion(transferObject.getResolucion());
	  	} catch (Throwable th ) {}
	  			
		  // FechaTraslado
  	    try {
		if (transferObject.getFechaTraslado() != null)
		{
		  	enhancedObject.setFechaTraslado(new Date(transferObject.getFechaTraslado().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // FolioDestino
                /**
                 * @author      :  Julio Alcazar
                 * @change      :  Revision: FolioDestino cambio de ser un objeto Folio a ser String
                 * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                 */
	  	try {
                 enhancedObject.setFolioDestino(transferObject.getFolioDestino());
	  	
	  	} catch (Throwable th ) {}
	  	

            /**
             * @author      :  Julio Alcazar
             * @change      :  nuevas propiedades de el objeto
             * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
             */

            // Resolucion Destino
  	        try {
                    enhancedObject.setResolucionDestino(transferObject.getResolucionDestino());
	  	} catch (Throwable th ) {}

		// Fecha Resolucion Origen
  	        try {
                    if (transferObject.getFechaResolucionOrigen() != null)
		{
		  	enhancedObject.setFechaResolucionOrigen(new Date(transferObject.getFechaResolucionOrigen().getTime()));
		}
		} catch (Throwable th ) {}

                // Fecha Resolucion Destino
  	        try {
                    if (transferObject.getFechaResolucionDestino() != null)
		{
		  	enhancedObject.setFechaResolucionDestino(new Date(transferObject.getFechaResolucionDestino().getTime()));
		}
		} catch (Throwable th ) {}
              /*
                * @author      : Carlos Mario Torres Urina
                * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                */

	    gov.sir.core.negocio.modelo.TrasladoDatos trasladoDatos = null;
            try {
	  	trasladoDatos = transferObject.getTrasladoDatos();
	  	} catch (Throwable th) {}
	  	if (trasladoDatos != null)
	  	{
	  		boolean assigned = false;
	  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced objTo =  (gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced)cache.get(trasladoDatos);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced)TrasladoDatosTransferCopier.deepCopy(trasladoDatos, cache);
	  		}
	  		enhancedObject.setTrasladoDatos(objTo);
	  		}
	  			  		
	  	}
	  	/*028_589*/		
		  // FechaConfirTras
  		try {
		if (enhancedObject.getFechaConfirTras() != null)
		{
	  	 transferObject.setFechaConfirTras(new Date(enhancedObject.getFechaConfirTras().getTime()));
	  	}
	  	} catch (Throwable th ) {}
		return enhancedObject;
	}
}
package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class BloqueoFolioTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.BloqueoFolio transferObject = new gov.sir.core.negocio.modelo.BloqueoFolio();
		cache.put(enhancedObject, transferObject );
		
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Folio
  	  gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced folioEnh = null;
	  try {
	  	folioEnh = enhancedObject.getFolio();
	  	} catch (Throwable th) {}
	  	if (folioEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Folio objTo =  (gov.sir.core.negocio.modelo.Folio)cache.get(folioEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Folio)FolioTransferCopier.deepCopy(folioEnh, cache);
	  		}
	  		transferObject.setFolio(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdLlave
  	    try {
	  	transferObject.setIdLlave(enhancedObject.getIdLlave());
	  	} catch (Throwable th ) {}
	  			
		  // LlaveBloqueo
  	  gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced llaveBloqueoEnh = null;
	  try {
	  	llaveBloqueoEnh = enhancedObject.getLlaveBloqueo();
	  	} catch (Throwable th) {}
	  	if (llaveBloqueoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.LlaveBloqueo objTo =  (gov.sir.core.negocio.modelo.LlaveBloqueo)cache.get(llaveBloqueoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.LlaveBloqueo)LlaveBloqueoTransferCopier.deepCopy(llaveBloqueoEnh, cache);
	  		}
	  		transferObject.setLlaveBloqueo(objTo);
	  		}
	  			  		
	  	}
	  			
		  // FechaBloqueo
  		try {
		if (enhancedObject.getFechaBloqueo() != null)
		{
	  	 transferObject.setFechaBloqueo(new Date(enhancedObject.getFechaBloqueo().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // FechaDesbloqueo
  		try {
		if (enhancedObject.getFechaDesbloqueo() != null)
		{
	  	 transferObject.setFechaDesbloqueo(new Date(enhancedObject.getFechaDesbloqueo().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdWorkflowBloqueo
  	    try {
	  	transferObject.setIdWorkflowBloqueo(enhancedObject.getIdWorkflowBloqueo());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.BloqueoFolio transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Folio
  	    gov.sir.core.negocio.modelo.Folio folio = null;
	  	try {
	  	folio = (gov.sir.core.negocio.modelo.Folio)transferObject.getFolio();
	  	} catch (Throwable th ) {}
	  	if (folio != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)cache.get(folio);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)FolioTransferCopier.deepCopy(folio, cache);
	  		}
	  		enhancedObject.setFolio(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdLlave
  	    try {
	  	enhancedObject.setIdLlave(transferObject.getIdLlave());
	  	} catch (Throwable th ) {}
	  			
		  // LlaveBloqueo
  	    gov.sir.core.negocio.modelo.LlaveBloqueo llaveBloqueo = null;
	  	try {
	  	llaveBloqueo = (gov.sir.core.negocio.modelo.LlaveBloqueo)transferObject.getLlaveBloqueo();
	  	} catch (Throwable th ) {}
	  	if (llaveBloqueo != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced)cache.get(llaveBloqueo);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LlaveBloqueoEnhanced)LlaveBloqueoTransferCopier.deepCopy(llaveBloqueo, cache);
	  		}
	  		enhancedObject.setLlaveBloqueo(objEn);
	  		}
	  			  		
	  	}
	  			
		  // FechaBloqueo
  	    try {
		if (transferObject.getFechaBloqueo() != null)
		{
		  	enhancedObject.setFechaBloqueo(new Date(transferObject.getFechaBloqueo().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // FechaDesbloqueo
  	    try {
		if (transferObject.getFechaDesbloqueo() != null)
		{
		  	enhancedObject.setFechaDesbloqueo(new Date(transferObject.getFechaDesbloqueo().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdWorkflowBloqueo
  	    try {
	  	enhancedObject.setIdWorkflowBloqueo(transferObject.getIdWorkflowBloqueo());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
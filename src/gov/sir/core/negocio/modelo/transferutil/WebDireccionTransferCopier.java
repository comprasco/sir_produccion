package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class WebDireccionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.WebDireccion transferObject = new gov.sir.core.negocio.modelo.WebDireccion();
		cache.put(enhancedObject, transferObject );
		
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Especificacion
  	    try {
	  	transferObject.setEspecificacion(enhancedObject.getEspecificacion());
	  	} catch (Throwable th ) {}
	  			
		  // ValorEje1
  	    try {
	  	transferObject.setValorEje1(enhancedObject.getValorEje1());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebSegeng
  	    try {
	  	transferObject.setIdWebSegeng(enhancedObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebDireccion
  	    try {
	  	transferObject.setIdWebDireccion(enhancedObject.getIdWebDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // IdEje1
  	    try {
	  	transferObject.setIdEje1(enhancedObject.getIdEje1());
	  	} catch (Throwable th ) {}
	  			
		  // IdEje2
  	    try {
	  	transferObject.setIdEje2(enhancedObject.getIdEje2());
	  	} catch (Throwable th ) {}
	  			
		  // ValorEje2
  	    try {
	  	transferObject.setValorEje2(enhancedObject.getValorEje2());
	  	} catch (Throwable th ) {}
	  			
		  // FolioNuevo
  	  gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced folioNuevoEnh = null;
	  try {
	  	folioNuevoEnh = enhancedObject.getFolioNuevo();
	  	} catch (Throwable th) {}
	  	if (folioNuevoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.WebFolioNuevo objTo =  (gov.sir.core.negocio.modelo.WebFolioNuevo)cache.get(folioNuevoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.WebFolioNuevo)WebFolioNuevoTransferCopier.deepCopy(folioNuevoEnh, cache);
	  		}
	  		transferObject.setFolioNuevo(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.WebDireccion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.WebDireccionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Especificacion
  	    try {
	  	enhancedObject.setEspecificacion(transferObject.getEspecificacion());
	  	} catch (Throwable th ) {}
	  			
		  // ValorEje1
  	    try {
	  	enhancedObject.setValorEje1(transferObject.getValorEje1());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebSegeng
  	    try {
	  	enhancedObject.setIdWebSegeng(transferObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
		  // IdWebDireccion
  	    try {
	  	enhancedObject.setIdWebDireccion(transferObject.getIdWebDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // IdEje1
  	    try {
	  	enhancedObject.setIdEje1(transferObject.getIdEje1());
	  	} catch (Throwable th ) {}
	  			
		  // IdEje2
  	    try {
	  	enhancedObject.setIdEje2(transferObject.getIdEje2());
	  	} catch (Throwable th ) {}
	  			
		  // ValorEje2
  	    try {
	  	enhancedObject.setValorEje2(transferObject.getValorEje2());
	  	} catch (Throwable th ) {}
	  			
		  // FolioNuevo
  	    gov.sir.core.negocio.modelo.WebFolioNuevo folioNuevo = null;
	  	try {
	  	folioNuevo = (gov.sir.core.negocio.modelo.WebFolioNuevo)transferObject.getFolioNuevo();
	  	} catch (Throwable th ) {}
	  	if (folioNuevo != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced)cache.get(folioNuevo);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced)WebFolioNuevoTransferCopier.deepCopy(folioNuevo, cache);
	  		}
	  		enhancedObject.setFolioNuevo(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}
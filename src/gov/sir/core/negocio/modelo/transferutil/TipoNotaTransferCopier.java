package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoNotaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoNota transferObject = new gov.sir.core.negocio.modelo.TipoNota();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Proceso
  	  gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced procesoEnh = null;
	  try {
	  	procesoEnh = enhancedObject.getProceso();
	  	} catch (Throwable th) {}
	  	if (procesoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Proceso objTo =  (gov.sir.core.negocio.modelo.Proceso)cache.get(procesoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Proceso)ProcesoTransferCopier.deepCopy(procesoEnh, cache);
	  		}
	  		transferObject.setProceso(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoNota
  	    try {
	  	transferObject.setIdTipoNota(enhancedObject.getIdTipoNota());
	  	} catch (Throwable th ) {}
	  			
		  // Visibilidad
  	    try {
	  	transferObject.setVisibilidad(enhancedObject.getVisibilidad());
	  	} catch (Throwable th ) {}
	  			
		  // Devolutiva
    try {
  	transferObject.setDevolutiva(enhancedObject.isDevolutiva());
  } catch (Throwable th ) {}
  			
		  // Fase
  	    try {
	  	transferObject.setFase(enhancedObject.getFase());
	  	} catch (Throwable th ) {}
             		  
            /**
     * * @author : HGOMEZ, FPADILLA ** @change : Se agrega código para menejar la persistencia de los
     * nuevos campos version y activo
     * version de cada una de ellas. ** Caso Mantis : 12621
     */
            
		  // Version
  	    try {
	  	transferObject.setVersion(enhancedObject.getVersion());
	  	} catch (Throwable th ) {}

            
            // Activo
  	    try {
	  	transferObject.setActivo(enhancedObject.getActivo());
	  	} catch (Throwable th ) {}
	  		
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoNota transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoNotaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Proceso
  	    gov.sir.core.negocio.modelo.Proceso proceso = null;
	  	try {
	  	proceso = (gov.sir.core.negocio.modelo.Proceso)transferObject.getProceso();
	  	} catch (Throwable th ) {}
	  	if (proceso != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced)cache.get(proceso);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced)ProcesoTransferCopier.deepCopy(proceso, cache);
	  		}
	  		enhancedObject.setProceso(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoNota
  	    try {
	  	enhancedObject.setIdTipoNota(transferObject.getIdTipoNota());
	  	} catch (Throwable th ) {}
	  			
		  // Visibilidad
  	    try {
	  	enhancedObject.setVisibilidad(transferObject.getVisibilidad());
	  	} catch (Throwable th ) {}
	  			
		  // Devolutiva
      try {
  	enhancedObject.setDevolutiva(transferObject.isDevolutiva());
  	} catch (Throwable th ) {}
  			
		  // Fase
  	    try {
	  	enhancedObject.setFase(transferObject.getFase());
	  	} catch (Throwable th ) {}
           
            /**
     * * @author : HGOMEZ, FPADILLA ** @change : Se agrega código para menejar la persistencia de los
     * nuevos campos version y activo
     * version de cada una de ellas. ** Caso Mantis : 12621
     */
          
            	  // Version
  	    try {
	  	enhancedObject.setVersion(transferObject.getVersion());
	  	} catch (Throwable th ) {}
	  		
            // Activo
  	    try {
	  	enhancedObject.setActivo(transferObject.getActivo());
	  	} catch (Throwable th ) {}
		
		return enhancedObject;
	}
}

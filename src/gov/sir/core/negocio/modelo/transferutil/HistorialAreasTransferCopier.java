package gov.sir.core.negocio.modelo.transferutil;


public class HistorialAreasTransferCopier {
    public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.HistorialAreasEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.HistorialAreas transferObject = new gov.sir.core.negocio.modelo.HistorialAreas();
		cache.put(enhancedObject, transferObject );
		
		  // IdHistorial
  	    try {
	  	transferObject.setIdHistorial(enhancedObject.getIdHistorial());
	  	} catch (Throwable th ) {}
            
            // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
            
            // Hectareas
  	    try {
	  	transferObject.setHectareas(enhancedObject.getHectareas());
	  	} catch (Throwable th ) {}
            
            // Metros
  	    try {
	  	transferObject.setMetros(enhancedObject.getMetros());
	  	} catch (Throwable th ) {}
            
            // Centimetros
  	    try {
	  	transferObject.setCentimetros(enhancedObject.getCentimetros());
	  	} catch (Throwable th ) {}
            
            // FechaActualizacion
  	    try {
	  	transferObject.setFechaActualizacion(enhancedObject.getFechaActualizacion());
	  	} catch (Throwable th ) {}
              
            return transferObject;
        }
    
    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.HistorialAreas transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.HistorialAreasEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.HistorialAreasEnhanced();
		cache.put(transferObject, enhancedObject);
                
		  // IdHistorial
  	    try {
	  	enhancedObject.setIdHistorial(transferObject.getIdHistorial());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Hectareas
  	    try {
	  	enhancedObject.setHectareas(transferObject.getHectareas());
	  	} catch (Throwable th ) {}
	  			
		  // Metros
  	    try {
	  	enhancedObject.setMetros(transferObject.getMetros());
	  	} catch (Throwable th ) {}
	  			
		  // Centimetros
  	    try {
	  	enhancedObject.setCentimetros(transferObject.getCentimetros());
	  	} catch (Throwable th ) {}
	  			
		  // Fecha Actuaizacion
  	    try {
	  	enhancedObject.setFechaActualizacion(transferObject.getFechaActualizacion());
	  	} catch (Throwable th ) {}
	  			
		return enhancedObject;
	}
}
   

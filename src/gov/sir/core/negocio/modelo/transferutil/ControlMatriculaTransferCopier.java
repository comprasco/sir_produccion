package gov.sir.core.negocio.modelo.transferutil;


public class ControlMatriculaTransferCopier {
    public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ControlMatriculaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.ControlMatricula transferObject = new gov.sir.core.negocio.modelo.ControlMatricula();
		cache.put(enhancedObject, transferObject );
		
            
            // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
            
            // accion
  	    try {
	  	transferObject.setAccion(enhancedObject.getAccion());
	  	} catch (Throwable th ) {}
            
            // rol
  	    try {
	  	transferObject.setRol(enhancedObject.getRol());
	  	} catch (Throwable th ) {}
            
            // turno
  	    try {
	  	transferObject.setTurno(enhancedObject.getTurno());
	  	} catch (Throwable th ) {}
            
            
            return transferObject;
        }
    
    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.ControlMatricula transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ControlMatriculaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ControlMatriculaEnhanced();
		cache.put(transferObject, enhancedObject);
                
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Accion
  	    try {
	  	enhancedObject.setAccion(transferObject.getAccion());
	  	} catch (Throwable th ) {}
	  			
		  // Rol
  	    try {
	  	enhancedObject.setRol(transferObject.getRol());
	  	} catch (Throwable th ) {}
	  			
		  // Turno
  	    try {
	  	enhancedObject.setTurno(transferObject.getTurno());
	  	} catch (Throwable th ) {}
	  			
		return enhancedObject;
	}
}
   

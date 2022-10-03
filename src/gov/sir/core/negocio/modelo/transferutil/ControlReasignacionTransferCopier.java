package gov.sir.core.negocio.modelo.transferutil;


public class ControlReasignacionTransferCopier {
    public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ControlReasignacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.ControlReasignacion transferObject = new gov.sir.core.negocio.modelo.ControlReasignacion();
		cache.put(enhancedObject, transferObject );
		
            
            // idWorkflow
  	    try {
	  	transferObject.setIdWorkflow(enhancedObject.getIdWorkflow());
	  	} catch (Throwable th ) {}
            
            // fase
  	    try {
	  	transferObject.setFase(enhancedObject.getFase());
	  	} catch (Throwable th ) {}
            
            // usuarioOrigen
  	    try {
	  	transferObject.setUsuarioOrigen(enhancedObject.getUsuarioOrigen());
	  	} catch (Throwable th ) {}
            
            // usuarioDestino
  	    try {
	  	transferObject.setUsuarioDestino(enhancedObject.getUsuarioDestino());
	  	} catch (Throwable th ) {}
            
            
            return transferObject;
        }
    
    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.ControlReasignacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ControlReasignacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ControlReasignacionEnhanced();
		cache.put(transferObject, enhancedObject);
                
		  // idWorkflow
  	    try {
	  	enhancedObject.setIdWorkflow(transferObject.getIdWorkflow());
	  	} catch (Throwable th ) {}
	  			
		  // Fase
  	    try {
	  	enhancedObject.setFase(transferObject.getFase());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioOrigen
  	    try {
	  	enhancedObject.setUsuarioOrigen(transferObject.getUsuarioOrigen());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioDestino
  	    try {
	  	enhancedObject.setUsuarioDestino(transferObject.getUsuarioDestino());
	  	} catch (Throwable th ) {}
	  			
		return enhancedObject;
	}
}
   

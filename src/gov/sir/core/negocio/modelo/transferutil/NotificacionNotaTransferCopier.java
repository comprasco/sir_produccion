package gov.sir.core.negocio.modelo.transferutil;


public class NotificacionNotaTransferCopier {
    public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.NotificacionNotaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.NotificacionNota transferObject = new gov.sir.core.negocio.modelo.NotificacionNota();
		cache.put(enhancedObject, transferObject );
		
		  // IdNotificacion
  	    try {
	  	transferObject.setIdNotificacion(enhancedObject.getIdNotificacion());
	  	} catch (Throwable th ) {}
            
            // Destino
  	    try {
	  	transferObject.setDestino(enhancedObject.getDestino());
	  	} catch (Throwable th ) {}
            
            // Tipo
  	    try {
	  	transferObject.setTipo(enhancedObject.getTipo());
	  	} catch (Throwable th ) {}
            
            // Apoderado
  	    try {
	  	transferObject.setApoderado(enhancedObject.getApoderado());
	  	} catch (Throwable th ) {}
            
            // Nombres
  	    try {
	  	transferObject.setNombres(enhancedObject.getNombres());
	  	} catch (Throwable th ) {}
            
            // Apellidos
  	    try {
	  	transferObject.setApellidos(enhancedObject.getApellidos());
	  	} catch (Throwable th ) {}
            
            // Correo
  	    try {
	  	transferObject.setCorreo(enhancedObject.getCorreo());
	  	} catch (Throwable th ) {}
            
            // Tipo Documento
  	    try {
	  	transferObject.setTipoDocumento(enhancedObject.getTipoDocumento());
	  	} catch (Throwable th ) {}
            
            // Documento
  	    try {
	  	transferObject.setDocumento(enhancedObject.getDocumento());
	  	} catch (Throwable th ) {}
            
            // Direccion
  	    try {
	  	transferObject.setDireccion(enhancedObject.getDireccion());
	  	} catch (Throwable th ) {}
            
            // Telefono
  	    try {
	  	transferObject.setTelefono(enhancedObject.getTelefono());
	  	} catch (Throwable th ) {}
            
            // Fecha de Notificacion
  	    try {
	  	transferObject.setFechaNotificacion(enhancedObject.getFechaNotificacion());
	  	} catch (Throwable th ) {}
            
            // Oficina Origen
  	    try {
	  	transferObject.setIdOficinaOrigen(enhancedObject.getIdOficinaOrigen());
	  	} catch (Throwable th ) {}
            
            // Turno
  	    try {
	  	transferObject.setTurnoWF(enhancedObject.getTurnoWF());
	  	} catch (Throwable th ) {}
            
            return transferObject;
        }
    
    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.NotificacionNota transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.NotificacionNotaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.NotificacionNotaEnhanced();
		cache.put(transferObject, enhancedObject);
                
		  // Idnotificacion
  	    try {
	  	enhancedObject.setIdNotificacion(transferObject.getIdNotificacion());
	  	} catch (Throwable th ) {}
	  			
		  // Destino
  	    try {
	  	enhancedObject.setDestino(transferObject.getDestino());
	  	} catch (Throwable th ) {}
	  			
		  // Tipo
  	    try {
	  	enhancedObject.setTipo(transferObject.getTipo());
	  	} catch (Throwable th ) {}
	  			
		  // Apoderado
  	    try {
	  	enhancedObject.setApoderado(transferObject.getApoderado());
	  	} catch (Throwable th ) {}
	  			
		  // Nombres
  	    try {
	  	enhancedObject.setNombres(transferObject.getNombres());
	  	} catch (Throwable th ) {}
	  			
		  // Apellidos
  	    try {
	  	enhancedObject.setApellidos(transferObject.getApellidos());
	  	} catch (Throwable th ) {}
            
             // Correo
  	    try {
	  	enhancedObject.setCorreo(transferObject.getCorreo());
	  	} catch (Throwable th ) {}
            
            		  // Tipo Documento
  	    try {
	  	enhancedObject.setTipoDocumento(transferObject.getTipoDocumento());
	  	} catch (Throwable th ) {}
            
            		  //Documento
  	    try {
	  	enhancedObject.setDocumento(transferObject.getDocumento());
	  	} catch (Throwable th ) {}
            
            		  // Direccion
  	    try {
	  	enhancedObject.setDireccion(transferObject.getDireccion());
	  	} catch (Throwable th ) {}
            
            		  // Telefono
  	    try {
	  	enhancedObject.setTelefono(transferObject.getTelefono());
	  	} catch (Throwable th ) {}
            
            		  // Fecha Notificacion
  	    try {
	  	enhancedObject.setFechaNotificacion(transferObject.getFechaNotificacion());
	  	} catch (Throwable th ) {}
            
            		  // Id Oficina Origen
  	    try {
	  	enhancedObject.setIdOficinaOrigen(transferObject.getIdOficinaOrigen());
	  	} catch (Throwable th ) {}
            
            		  // Turno
  	    try {
	  	enhancedObject.setTurnoWF(transferObject.getTurnoWF());
	  	} catch (Throwable th ) {}
	  			
		return enhancedObject;
	}
}
   

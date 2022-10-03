package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class DocumentoPagoCorreccionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.DocumentoPagoCorreccion transferObject = new gov.sir.core.negocio.modelo.DocumentoPagoCorreccion();
		cache.put(enhancedObject, transferObject );
		
                 // docpagocoorr
  	    try {
	  	transferObject.setIdDocumentoPagoCorreccion(enhancedObject.getIdDocumentoPagoCorreccion());
	  	} catch (Throwable th ) {}
		 // tipoPago
  	    try {
	  	transferObject.setTipoPago(enhancedObject.getTipoPago());
	  	} catch (Throwable th ) {}
	  	
		  // valorDocumento
  	    try {
	  	transferObject.setValorDocumento(enhancedObject.getValorDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // idDocumentoPago
  	    try {
	  	transferObject.setIdDocumentoPago(enhancedObject.getIdDocumentoPago());
	  	} catch (Throwable th ) {}
	  		
              // usuario creacion
  	    try {
	  	transferObject.setUsuariocreacion(enhancedObject.getUsuariocreacion());
	  	} catch (Throwable th ) {}
	  	
              // usuario modifica
  	    try {
	  	transferObject.setUsuariomodifica(enhancedObject.getUsuariomodifica());
	  	} catch (Throwable th ) {}
	  	
              // fecha modificacion
  	    try {
	  	transferObject.setFechamodificacion(enhancedObject.getFechamodificacion());
	  	} catch (Throwable th ) {}
	  	
	    // SaldoDocumento
  	    try {
	  	transferObject.setSaldoDocumento(enhancedObject.getSaldoDocumento());
	  	} catch (Throwable th ) {}
	       // Estado Correccion
  	    try {
	  	transferObject.setEstadocorreccion(enhancedObject.getEstadocorreccion());
	  	} catch (Throwable th ) {}			
		  // SaldoDocumentoAnulacion
  	    try {
	  	transferObject.setSaldoDocumentoAnulacion(enhancedObject.getSaldoDocumentoAnulacion());
	  	} catch (Throwable th ) {}
	  	
	    // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  	    try {
	  	transferObject.setFechaCreacion(enhancedObject.getFechaCreacion());
	  	} catch (Throwable th ) {}
            
            try {
	  	transferObject.setNombreCanal(enhancedObject.getNombreCanal());
	  	} catch (Throwable th ) {}
	  			
			  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.DocumentoPagoCorreccion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoCorreccionEnhanced();
		cache.put(transferObject, enhancedObject);
		     // docpagocoorr
  	    try {
	  	enhancedObject.setIdDocumentoPagoCorreccion(transferObject.getIdDocumentoPagoCorreccion());
	  	} catch (Throwable th ) {}
		// tipoPago
  	    try {
  	    	enhancedObject.setTipoPago(transferObject.getTipoPago());
	  	} catch (Throwable th ) {}
	  	
		 // valorDocumento
  	    try {
  	    	enhancedObject.setValorDocumento(transferObject.getValorDocumento());
	  	} catch (Throwable th ) {}
            // usuario creacion
  	    try {
	  	enhancedObject.setUsuariocreacion(transferObject.getUsuariocreacion());
	  	} catch (Throwable th ) {}
	  	
              // usuario modifica
  	    try {
	  	enhancedObject.setUsuariomodifica(transferObject.getUsuariomodifica());
	  	} catch (Throwable th ) {}
	  	
              // fecha modificacion
  	    try {
	  	enhancedObject.setFechamodificacion(transferObject.getFechamodificacion());
	  	} catch (Throwable th ) {}
		  // idDocumentoPago
  	    try {
  	    	enhancedObject.setIdDocumentoPago(transferObject.getIdDocumentoPago());
	  	} catch (Throwable th ) {}
	  		
	    // SaldoDocumento
  	    try {
  	    	enhancedObject.setSaldoDocumento(transferObject.getSaldoDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // SaldoDocumentoAnulacion
  	    try {
  	    	enhancedObject.setSaldoDocumentoAnulacion(transferObject.getSaldoDocumentoAnulacion());
	  	} catch (Throwable th ) {}
	    // Estado Correccion
  	    try {
  	    	enhancedObject.setEstadocorreccion(transferObject.getEstadocorreccion());
	  	} catch (Throwable th ) {}	
	    // Circulo
  	    try {
  	    	enhancedObject.setCirculo(transferObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  	    try {
  	    	enhancedObject.setFechaCreacion(transferObject.getFechaCreacion());
	  	} catch (Throwable th ) {}
            
              	    try {
  	    	enhancedObject.setNombreCanal(transferObject.getNombreCanal());
	  	} catch (Throwable th ) {}
	  	
					
		
		return enhancedObject;
	}
}
package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class DocumentoPagoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.DocumentoPago transferObject = new gov.sir.core.negocio.modelo.DocumentoPago();
		cache.put(enhancedObject, transferObject );
		
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
            
            try {
	  	transferObject.setConsignacion_ant(enhancedObject.getConsignacion_ant());
	  	} catch (Throwable th ) {}
            
            try {
	  	transferObject.setTarjeta_ant(enhancedObject.getTarjeta_ant());
	  	} catch (Throwable th ) {}
            
            try {
	  	transferObject.setAprobacion_ant(enhancedObject.getAprobacion_ant());
	  	} catch (Throwable th ) {}
            
	  			
			  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.DocumentoPago transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoEnhanced();
		cache.put(transferObject, enhancedObject);
		
		// tipoPago
  	    try {
  	    	enhancedObject.setTipoPago(transferObject.getTipoPago());
	  	} catch (Throwable th ) {}
	  	
		 // valorDocumento
  	    try {
  	    	enhancedObject.setValorDocumento(transferObject.getValorDocumento());
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
            
            try {
  	    	enhancedObject.setConsignacion_ant(transferObject.getConsignacion_ant());
	  	} catch (Throwable th ) {}
	  
            try {
  	    	enhancedObject.setTarjeta_ant(transferObject.getTarjeta_ant());
	  	} catch (Throwable th ) {}
	  
            try {
  	    	enhancedObject.setAprobacion_ant(transferObject.getAprobacion_ant());
	  	} catch (Throwable th ) {}
	  
					
		
		return enhancedObject;
	}
}
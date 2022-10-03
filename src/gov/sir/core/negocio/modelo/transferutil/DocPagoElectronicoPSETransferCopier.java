/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Pago Electrónico PSE del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo.transferutil;
import java.util.Date;

public class DocPagoElectronicoPSETransferCopier {
    public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.DocPagoElectronicoPSEEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.DocPagoElectronicoPSE transferObject = new gov.sir.core.negocio.modelo.DocPagoElectronicoPSE();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdDocumentoPago
  	    try {
	  	transferObject.setIdDocumentoPago(enhancedObject.getIdDocumentoPago());
	  	} catch (Throwable th ) {}
	  			
		  // TipoPago
  	    try {
	  	transferObject.setTipoPago(enhancedObject.getTipoPago());
	  	} catch (Throwable th ) {}
            
            // Numero Aprobacion
  	    try {
	  	transferObject.setNumeroAprobacion(enhancedObject.getNumeroAprobacion());
            } catch (Throwable th ) {}
            
            // Numero Aprobacion
  	    try {
	  	transferObject.setFechaDocumento(enhancedObject.getFecha());
            } catch (Throwable th ) {}
	  			
		  // ValorDocumento
  	    try {
	  	transferObject.setValorDocumento(enhancedObject.getValorDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // SaldoDocumento
  	    try {
	  	transferObject.setSaldoDocumento(enhancedObject.getSaldoDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // SaldoDocumentoAnulacion
  	    try {
	  	transferObject.setSaldoDocumentoAnulacion(enhancedObject.getSaldoDocumentoAnulacion());
	  	} catch (Throwable th ) {}
            
            //Edgar Lora: Mantis: 0012422
            // BancoFranquicia
            gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced bancoFranquiciaEnh = null;
            try {
                bancoFranquiciaEnh = enhancedObject.getBancoFranquicia();
            } catch (Throwable th) {}


            if (bancoFranquiciaEnh != null)
            {
                boolean assigned = false;
                if (!assigned){
                    gov.sir.core.negocio.modelo.BancoFranquicia objTo =  (gov.sir.core.negocio.modelo.BancoFranquicia)cache.get(bancoFranquiciaEnh);
                    if (objTo == null){
                        objTo = (gov.sir.core.negocio.modelo.BancoFranquicia)BancoFranquiciaTransferCopier.deepCopy(bancoFranquiciaEnh, cache);
                    }
                    transferObject.setBancoFranquicia(objTo);
                }
            }
            
            return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.DocPagoElectronicoPSE transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.DocPagoElectronicoPSEEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DocPagoElectronicoPSEEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Circulo
  	    try {
	  	enhancedObject.setCirculo(transferObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdDocumentoPago
  	    try {
	  	enhancedObject.setIdDocumentoPago(transferObject.getIdDocumentoPago());
	  	} catch (Throwable th ) {}
	  			
		  // TipoPago
  	    try {
	  	enhancedObject.setTipoPago(transferObject.getTipoPago());
	  	} catch (Throwable th ) {}
            
            // Numero Aprobación
  	    try {
	  	enhancedObject.setNumeroAprobacion(transferObject.getNumeroAprobacion());
	  	} catch (Throwable th ) {}
            
            // Numero Aprobación
  	    try {
	  	enhancedObject.setFecha(transferObject.getFechaDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // ValorDocumento
  	    try {
	  	enhancedObject.setValorDocumento(transferObject.getValorDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // SaldoDocumento
  	    try {
	  	enhancedObject.setSaldoDocumento(transferObject.getSaldoDocumento());
	  	} catch (Throwable th ) {}
	  			
		  // SaldoDocumentoAnulacion
  	    try {
	  	enhancedObject.setSaldoDocumentoAnulacion(transferObject.getSaldoDocumentoAnulacion());
	  	} catch (Throwable th ) {}
            
            //Edgar Lora: Mantis: 0012422
            //BancoFranquicia
            gov.sir.core.negocio.modelo.BancoFranquicia bancoFranquicia = null;
            try {
                bancoFranquicia = (gov.sir.core.negocio.modelo.BancoFranquicia)transferObject.getBancoFranquicia();
            } catch (Throwable th ) {}

            if (bancoFranquicia != null){
                boolean assigned = false;
                if (!assigned){
                    gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced)cache.get(bancoFranquicia);
                    if (objEn == null){
                        objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced)BancoFranquiciaTransferCopier.deepCopy(bancoFranquicia, cache);
                    }
                    enhancedObject.setBancoFranquicia(objEn);
                }
            }
		return enhancedObject;
	}
}

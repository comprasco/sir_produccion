package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class DocPagoTimbreConstanciaLiquidacionCorreccionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionCorreccionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacionCorreccion transferObject = new gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacionCorreccion();
		cache.put(enhancedObject, transferObject );
		
		  // Fecha
  	    try {
	  	transferObject.setFecha(enhancedObject.getFecha());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroTimbre
  	    try {
	  	transferObject.setNumeroTimbre(enhancedObject.getNumeroTimbre());
	  	} catch (Throwable th ) {}
	  			
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
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacionCorreccion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionCorreccionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionCorreccionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Fecha
  	    try {
	  	enhancedObject.setFecha(transferObject.getFecha());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroTimbre
  	    try {
	  	enhancedObject.setNumeroTimbre(transferObject.getNumeroTimbre());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		
		return enhancedObject;
	}
}
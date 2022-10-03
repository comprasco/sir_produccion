package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class DocPagoChequeTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.DocPagoCheque transferObject = new gov.sir.core.negocio.modelo.DocPagoCheque();
		cache.put(enhancedObject, transferObject );
		
		  // Fecha
  	    try {
	  	transferObject.setFecha(enhancedObject.getFecha());
	  	} catch (Throwable th ) {}
	  			
		  // NoCuenta
  	    try {
	  	transferObject.setNoCuenta(enhancedObject.getNoCuenta());
	  	} catch (Throwable th ) {}
	  			
		  // NoCheque
  	    try {
	  	transferObject.setNoCheque(enhancedObject.getNoCheque());
	  	} catch (Throwable th ) {}
	  			
		  // Banco
  	  gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced bancoEnh = null;
	  try {
	  	bancoEnh = enhancedObject.getBanco();
	  	} catch (Throwable th) {}
	  	if (bancoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Banco objTo =  (gov.sir.core.negocio.modelo.Banco)cache.get(bancoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Banco)BancoTransferCopier.deepCopy(bancoEnh, cache);
	  		}
	  		transferObject.setBanco(objTo);
	  		}
	  			  		
	  	}
	  			
		  // CodSucursalBanco
  	    try {
	  	transferObject.setCodSucursalBanco(enhancedObject.getCodSucursalBanco());
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
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.DocPagoCheque transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Fecha
  	    try {
	  	enhancedObject.setFecha(transferObject.getFecha());
	  	} catch (Throwable th ) {}
	  			
		  // NoCuenta
  	    try {
	  	enhancedObject.setNoCuenta(transferObject.getNoCuenta());
	  	} catch (Throwable th ) {}
	  			
		  // NoCheque
  	    try {
	  	enhancedObject.setNoCheque(transferObject.getNoCheque());
	  	} catch (Throwable th ) {}
	  			
		  // Banco
  	    gov.sir.core.negocio.modelo.Banco banco = null;
	  	try {
	  	banco = (gov.sir.core.negocio.modelo.Banco)transferObject.getBanco();
	  	} catch (Throwable th ) {}
	  	if (banco != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced)cache.get(banco);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced)BancoTransferCopier.deepCopy(banco, cache);
	  		}
	  		enhancedObject.setBanco(objEn);
	  		}
	  			  		
	  	}
	  			
		  // CodSucursalBanco
  	    try {
	  	enhancedObject.setCodSucursalBanco(transferObject.getCodSucursalBanco());
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
package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class PagoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Pago transferObject = new gov.sir.core.negocio.modelo.Pago();
		cache.put(enhancedObject, transferObject );
		
		  // IdLiquidacion
  	    try {
	  	transferObject.setIdLiquidacion(enhancedObject.getIdLiquidacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Liquidacion
  	  gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhanced liquidacionEnh = null;
	  try {
	  	liquidacionEnh = enhancedObject.getLiquidacion();
	  	} catch (Throwable th) {}
	  	if (liquidacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  		
	  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoCertificadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced)liquidacionEnh, cache);
	  		}
	  		transferObject.setLiquidacion(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoCertificadoMasivoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced)liquidacionEnh, cache);
	  		}
	  		transferObject.setLiquidacion(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoConsultaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced)liquidacionEnh, cache);
	  		}
	  		transferObject.setLiquidacion(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoCorreccionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced)liquidacionEnh, cache);
	  		}
	  		transferObject.setLiquidacion(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoDevolucionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced)liquidacionEnh, cache);
	  		}
	  		transferObject.setLiquidacion(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoFotocopiaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced)liquidacionEnh, cache);
	  		}
	  		transferObject.setLiquidacion(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoRegistroTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced)liquidacionEnh, cache);
	  		}
	  		transferObject.setLiquidacion(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoRepartoNotarialTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced)liquidacionEnh, cache);
	  		}
	  		transferObject.setLiquidacion(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoRestitucionRepartoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced)liquidacionEnh, cache);
	  		}
	  		transferObject.setLiquidacion(objTo);
	  		assigned = true;
	  		}
	  			  			  		
	  	}
	  			
		  // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Fecha
  		try {
		if (enhancedObject.getFecha() != null)
		{
	  	 transferObject.setFecha(new Date(enhancedObject.getFecha().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Usuario
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioEnh = null;
	  try {
	  	usuarioEnh = enhancedObject.getUsuario();
	  	} catch (Throwable th) {}
	  	if (usuarioEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioEnh, cache);
	  		}
	  		transferObject.setUsuario(objTo);
	  		}
	  			  		
	  	}
	  			
		  // AplicacionPago
    	List aplicacionPago = null;
  	try
  	{
  	aplicacionPago = enhancedObject.getAplicacionPagos();
  	} catch (Throwable th) {}
	  	if (aplicacionPago != null)
	  	{
		  	for(Iterator itera = aplicacionPago.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced aplicacionPagoEnh = (gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.AplicacionPago objTo = (gov.sir.core.negocio.modelo.AplicacionPago)cache.get(aplicacionPagoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.AplicacionPago)AplicacionPagoTransferCopier.deepCopy(aplicacionPagoEnh, cache);
		  		}
		  		transferObject.addAplicacionPago(objTo);
		  		}
		  				  	}
		}
				
		  // FechaImpresion
  		try {
		if (enhancedObject.getFechaImpresion() != null)
		{
	  	 transferObject.setFechaImpresion(new Date(enhancedObject.getFechaImpresion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // NumRecibo
  	    try {
	  	transferObject.setNumRecibo(enhancedObject.getNumRecibo());
	  	} catch (Throwable th ) {}
	  	
	  	  //concepto
	  	try {
		  	transferObject.setConcepto(enhancedObject.getConcepto());
		  	} catch (Throwable th ) {}
	  			
		  // LastNumRecibo
  	    try {
	  	transferObject.setLastNumRecibo(enhancedObject.getLastNumRecibo());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Pago transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdLiquidacion
  	    try {
	  	enhancedObject.setIdLiquidacion(transferObject.getIdLiquidacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Liquidacion
  	    gov.sir.core.negocio.modelo.Liquidacion liquidacion = null;
	  	try {
	  	liquidacion = (gov.sir.core.negocio.modelo.Liquidacion)transferObject.getLiquidacion();
	  	} catch (Throwable th ) {}
	  	if (liquidacion != null)
	  	{
	  		boolean assigned = false;
	  			  		
	  		if (liquidacion instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced)cache.get(liquidacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced)LiquidacionTurnoCertificadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado)liquidacion, cache);
	  		}
	  		enhancedObject.setLiquidacion(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacion instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced)cache.get(liquidacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced)LiquidacionTurnoCertificadoMasivoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo)liquidacion, cache);
	  		}
	  		enhancedObject.setLiquidacion(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacion instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced)cache.get(liquidacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced)LiquidacionTurnoConsultaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta)liquidacion, cache);
	  		}
	  		enhancedObject.setLiquidacion(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacion instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced)cache.get(liquidacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced)LiquidacionTurnoCorreccionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion)liquidacion, cache);
	  		}
	  		enhancedObject.setLiquidacion(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacion instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced)cache.get(liquidacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced)LiquidacionTurnoDevolucionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion)liquidacion, cache);
	  		}
	  		enhancedObject.setLiquidacion(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacion instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced)cache.get(liquidacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced)LiquidacionTurnoFotocopiaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia)liquidacion, cache);
	  		}
	  		enhancedObject.setLiquidacion(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacion instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced)cache.get(liquidacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced)LiquidacionTurnoRegistroTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro)liquidacion, cache);
	  		}
	  		enhancedObject.setLiquidacion(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacion instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced)cache.get(liquidacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced)LiquidacionTurnoRepartoNotarialTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial)liquidacion, cache);
	  		}
	  		enhancedObject.setLiquidacion(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (liquidacion instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced)cache.get(liquidacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced)LiquidacionTurnoRestitucionRepartoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto)liquidacion, cache);
	  		}
	  		enhancedObject.setLiquidacion(objEn);
	  		assigned = true;
	  		}
	  			  			  		
	  	}
	  			
		  // Circulo
  	    try {
	  	enhancedObject.setCirculo(transferObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // Fecha
  	    try {
		if (transferObject.getFecha() != null)
		{
		  	enhancedObject.setFecha(new Date(transferObject.getFecha().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Usuario
  	    gov.sir.core.negocio.modelo.Usuario usuario = null;
	  	try {
	  	usuario = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuario();
	  	} catch (Throwable th ) {}
	  	if (usuario != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuario);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuario, cache);
	  		}
	  		enhancedObject.setUsuario(objEn);
	  		}
	  			  		
	  	}
	  			
		  // AplicacionPago
    	List aplicacionPago = null;
  	try
  	{
  	aplicacionPago = transferObject.getAplicacionPagos();
  	} catch (Throwable th) { }
	  	if (aplicacionPago != null)
	  	{
		  	for(Iterator itera = aplicacionPago.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.AplicacionPago aplicacionPagoto = (gov.sir.core.negocio.modelo.AplicacionPago)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced)cache.get(aplicacionPagoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced)AplicacionPagoTransferCopier.deepCopy(aplicacionPagoto, cache);
		  		}
		  		enhancedObject.addAplicacionPago(objEn);
		  				  		}
		  				  	}
		}
				
		  // FechaImpresion
  	    try {
		if (transferObject.getFechaImpresion() != null)
		{
		  	enhancedObject.setFechaImpresion(new Date(transferObject.getFechaImpresion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // NumRecibo
  	    try {
	  	enhancedObject.setNumRecibo(transferObject.getNumRecibo());
	  	} catch (Throwable th ) {}
	  	
	  	  // Concepto
  	    try {
	  	enhancedObject.setConcepto(transferObject.getConcepto());
	  	} catch (Throwable th ) {}
	  			
		  // LastNumRecibo
  	    try {
	  	enhancedObject.setLastNumRecibo(transferObject.getLastNumRecibo());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
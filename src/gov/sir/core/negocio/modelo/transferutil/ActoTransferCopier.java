package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ActoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ActoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Acto transferObject = new gov.sir.core.negocio.modelo.Acto();
		cache.put(enhancedObject, transferObject );
		
		  // Cuantia
  	    try {
	  	transferObject.setCuantia(enhancedObject.getCuantia());
	  	} catch (Throwable th ) {}
	  			
		  // IdActo
  	    try {
	  	transferObject.setIdActo(enhancedObject.getIdActo());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // TipoActo
  	  gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced tipoActoEnh = null;
	  try {
	  	tipoActoEnh = enhancedObject.getTipoActo();
	  	} catch (Throwable th) {}
	  	if (tipoActoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoActo objTo =  (gov.sir.core.negocio.modelo.TipoActo)cache.get(tipoActoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoActo)TipoActoTransferCopier.deepCopy(tipoActoEnh, cache);
	  		}
	  		transferObject.setTipoActo(objTo);
	  		}
	  			  		
	  	}
	  			
		  // TipoImpuesto
  	  gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced tipoImpuestoEnh = null;
	  try {
	  	tipoImpuestoEnh = enhancedObject.getTipoImpuesto();
	  	} catch (Throwable th) {}
	  	if (tipoImpuestoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoImpuesto objTo =  (gov.sir.core.negocio.modelo.TipoImpuesto)cache.get(tipoImpuestoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoImpuesto)TipoImpuestoTransferCopier.deepCopy(tipoImpuestoEnh, cache);
	  		}
	  		transferObject.setTipoImpuesto(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Valor
  	    try {
	  	transferObject.setValor(enhancedObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // CobroImpuestos
    try {
  	transferObject.setCobroImpuestos(enhancedObject.isCobroImpuestos());
  } catch (Throwable th ) {}
  			
		  // TipoDerechoReg
  	  gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced tipoDerechoRegEnh = null;
	  try {
	  	tipoDerechoRegEnh = enhancedObject.getTipoDerechoReg();
	  	} catch (Throwable th) {}
	  	if (tipoDerechoRegEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoActoDerechoRegistral objTo =  (gov.sir.core.negocio.modelo.TipoActoDerechoRegistral)cache.get(tipoDerechoRegEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoActoDerechoRegistral)TipoActoDerechoRegistralTransferCopier.deepCopy(tipoDerechoRegEnh, cache);
	  		}
	  		transferObject.setTipoDerechoReg(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ValorImpuestos
  	    try {
	  	transferObject.setValorImpuestos(enhancedObject.getValorImpuestos());
	  	} catch (Throwable th ) {}
	  			
		  // ValorMora
  	    try {
	  	transferObject.setValorMora(enhancedObject.getValorMora());
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
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Acto transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.ActoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ActoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Cuantia
  	    try {
	  	enhancedObject.setCuantia(transferObject.getCuantia());
	  	} catch (Throwable th ) {}
	  			
		  // IdActo
  	    try {
	  	enhancedObject.setIdActo(transferObject.getIdActo());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // TipoActo
  	    gov.sir.core.negocio.modelo.TipoActo tipoActo = null;
	  	try {
	  	tipoActo = (gov.sir.core.negocio.modelo.TipoActo)transferObject.getTipoActo();
	  	} catch (Throwable th ) {}
	  	if (tipoActo != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced)cache.get(tipoActo);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced)TipoActoTransferCopier.deepCopy(tipoActo, cache);
	  		}
	  		enhancedObject.setTipoActo(objEn);
	  		}
	  			  		
	  	}
	  			
		  // TipoImpuesto
  	    gov.sir.core.negocio.modelo.TipoImpuesto tipoImpuesto = null;
	  	try {
	  	tipoImpuesto = (gov.sir.core.negocio.modelo.TipoImpuesto)transferObject.getTipoImpuesto();
	  	} catch (Throwable th ) {}
	  	if (tipoImpuesto != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced)cache.get(tipoImpuesto);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoEnhanced)TipoImpuestoTransferCopier.deepCopy(tipoImpuesto, cache);
	  		}
	  		enhancedObject.setTipoImpuesto(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Valor
  	    try {
	  	enhancedObject.setValor(transferObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // CobroImpuestos
      try {
  	enhancedObject.setCobroImpuestos(transferObject.isCobroImpuestos());
  	} catch (Throwable th ) {}
  			
		  // TipoDerechoReg
  	    gov.sir.core.negocio.modelo.TipoActoDerechoRegistral tipoDerechoReg = null;
	  	try {
	  	tipoDerechoReg = (gov.sir.core.negocio.modelo.TipoActoDerechoRegistral)transferObject.getTipoDerechoReg();
	  	} catch (Throwable th ) {}
	  	if (tipoDerechoReg != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced)cache.get(tipoDerechoReg);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced)TipoActoDerechoRegistralTransferCopier.deepCopy(tipoDerechoReg, cache);
	  		}
	  		enhancedObject.setTipoDerechoReg(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ValorImpuestos
  	    try {
	  	enhancedObject.setValorImpuestos(transferObject.getValorImpuestos());
	  	} catch (Throwable th ) {}
	  			
		  // ValorMora
  	    try {
	  	enhancedObject.setValorMora(transferObject.getValorMora());
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
	  			
		
		return enhancedObject;
	}
}
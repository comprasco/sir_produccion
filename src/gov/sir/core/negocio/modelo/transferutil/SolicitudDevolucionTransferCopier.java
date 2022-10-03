package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SolicitudDevolucionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SolicitudDevolucion transferObject = new gov.sir.core.negocio.modelo.SolicitudDevolucion();
		cache.put(enhancedObject, transferObject );
		
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Ciudadano
  	  gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced ciudadanoEnh = null;
	  try {
	  	ciudadanoEnh = enhancedObject.getCiudadano();
	  	} catch (Throwable th) {}
	  	if (ciudadanoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Ciudadano objTo =  (gov.sir.core.negocio.modelo.Ciudadano)cache.get(ciudadanoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Ciudadano)CiudadanoTransferCopier.deepCopy(ciudadanoEnh, cache);
	  		}
	  		transferObject.setCiudadano(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Aprobada
    try {
  	transferObject.setAprobada(enhancedObject.isAprobada());
  } catch (Throwable th ) {}
  
  //NumeroFolios
    try {
	transferObject.setNumeroFolios(enhancedObject.getNumeroFolios());
	} catch (Throwable th ) {}
  			
		  // CheckedItem
    	List checkedItem = null;
  	try
  	{
  	checkedItem = enhancedObject.getCheckedItems();
  	} catch (Throwable th) {}
	  	if (checkedItem != null)
	  	{
		  	for(Iterator itera = checkedItem.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced checkedItemEnh = (gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.SolCheckedItemDev objTo = (gov.sir.core.negocio.modelo.SolCheckedItemDev)cache.get(checkedItemEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.SolCheckedItemDev)SolCheckedItemDevTransferCopier.deepCopy(checkedItemEnh, cache);
		  		}
		  		transferObject.addCheckedItem(objTo);
		  		}
		  				  	}
		}
	  	
	    // solicitantes
    	List solicitantes = null;
  	try
  	{
  		solicitantes = enhancedObject.getSolicitantes();
  	} catch (Throwable th) {}
	  	if (solicitantes != null)
	  	{
		  	for(Iterator itera = solicitantes.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.SolicitanteEnhanced solteEnh = (gov.sir.core.negocio.modelo.jdogenie.SolicitanteEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Solicitante objTo = (gov.sir.core.negocio.modelo.Solicitante)cache.get(solteEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Solicitante)SolicitanteTransferCopier.deepCopy(solteEnh, cache);
		  		}
		  		transferObject.addSolicitante(objTo);
		  		}
		  				  	}
		}
	  	
	 // consignaciones
    	List consignaciones = null;
  	try
  	{
  		consignaciones = enhancedObject.getConsignaciones();
  	} catch (Throwable th) {}
	  	if (consignaciones != null)
	  	{
		  	for(Iterator itera = consignaciones.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced consEnh = (gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Consignacion objTo = (gov.sir.core.negocio.modelo.Consignacion)cache.get(consEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Consignacion)ConsignacionTransferCopier.deepCopy(consEnh, cache);
		  		}
		  		transferObject.addConsignacion(objTo);
		  		}
		  				  	}
		}
				
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Circulo
  	  gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced circuloEnh = null;
	  try {
	  	circuloEnh = enhancedObject.getCirculo();
	  	} catch (Throwable th) {}
	  	if (circuloEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Circulo objTo =  (gov.sir.core.negocio.modelo.Circulo)cache.get(circuloEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Circulo)CirculoTransferCopier.deepCopy(circuloEnh, cache);
	  		}
	  		transferObject.setCirculo(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Proceso
  	  gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced procesoEnh = null;
	  try {
	  	procesoEnh = enhancedObject.getProceso();
	  	} catch (Throwable th) {}
	  	if (procesoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Proceso objTo =  (gov.sir.core.negocio.modelo.Proceso)cache.get(procesoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Proceso)ProcesoTransferCopier.deepCopy(procesoEnh, cache);
	  		}
	  		transferObject.setProceso(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Comentario
  	    try {
	  	transferObject.setComentario(enhancedObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Fecha
  		try {
		if (enhancedObject.getFecha() != null)
		{
	  	 transferObject.setFecha(new Date(enhancedObject.getFecha().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // DatosAntiguoSistema
  	  gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced datosAntiguoSistemaEnh = null;
	  try {
	  	datosAntiguoSistemaEnh = enhancedObject.getDatosAntiguoSistema();
	  	} catch (Throwable th) {}
	  	if (datosAntiguoSistemaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.DatosAntiguoSistema objTo =  (gov.sir.core.negocio.modelo.DatosAntiguoSistema)cache.get(datosAntiguoSistemaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.DatosAntiguoSistema)DatosAntiguoSistemaTransferCopier.deepCopy(datosAntiguoSistemaEnh, cache);
	  		}
	  		transferObject.setDatosAntiguoSistema(objTo);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // Turno
  	  gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced turnoEnh = null;
	  try {
	  	turnoEnh = enhancedObject.getTurno();
	  	} catch (Throwable th) {}
	  	if (turnoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Turno objTo =  (gov.sir.core.negocio.modelo.Turno)cache.get(turnoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Turno)TurnoTransferCopier.deepCopy(turnoEnh, cache);
	  		}
	  		transferObject.setTurno(objTo);
	  		}
	  			  		
	  	}
	  			
		  // TurnoAnterior
  	  gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced turnoAnteriorEnh = null;
	  try {
	  	turnoAnteriorEnh = enhancedObject.getTurnoAnterior();
	  	} catch (Throwable th) {}
	  	if (turnoAnteriorEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Turno objTo =  (gov.sir.core.negocio.modelo.Turno)cache.get(turnoAnteriorEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Turno)TurnoTransferCopier.deepCopy(turnoAnteriorEnh, cache);
	  		}
	  		transferObject.setTurnoAnterior(objTo);
	  		}
	  			  		
	  	}
	  			
		  // SolicitudFolio
    	List solicitudFolio = null;
  	try
  	{
  	solicitudFolio = enhancedObject.getSolicitudFolios();
  	} catch (Throwable th) {}
	  	if (solicitudFolio != null)
	  	{
		  	for(Iterator itera = solicitudFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced solicitudFolioEnh = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.SolicitudFolio objTo = (gov.sir.core.negocio.modelo.SolicitudFolio)cache.get(solicitudFolioEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.SolicitudFolio)SolicitudFolioTransferCopier.deepCopy(solicitudFolioEnh, cache);
		  		}
		  		transferObject.addSolicitudFolio(objTo);
		  		}
		  				  	}
		}
				
		  // Liquidacion
    	List liquidacion = null;
  	try
  	{
  	liquidacion = enhancedObject.getLiquidaciones();
  	} catch (Throwable th) {}
	  	if (liquidacion != null)
	  	{
		  	for(Iterator itera = liquidacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhanced liquidacionEnh = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhanced)itera.next();
		  				  		
		  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced)
		  		{
		  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoCertificadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced)liquidacionEnh, cache);
		  		}
		  		transferObject.addLiquidacion(objTo);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced)
		  		{
		  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoCertificadoMasivoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced)liquidacionEnh, cache);
		  		}
		  		transferObject.addLiquidacion(objTo);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced)
		  		{
		  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoConsultaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced)liquidacionEnh, cache);
		  		}
		  		transferObject.addLiquidacion(objTo);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced)
		  		{
		  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoCorreccionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced)liquidacionEnh, cache);
		  		}
		  		transferObject.addLiquidacion(objTo);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced)
		  		{
		  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoDevolucionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced)liquidacionEnh, cache);
		  		}
		  		transferObject.addLiquidacion(objTo);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced)
		  		{
		  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoFotocopiaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced)liquidacionEnh, cache);
		  		}
		  		transferObject.addLiquidacion(objTo);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced)
		  		{
		  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoRegistroTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced)liquidacionEnh, cache);
		  		}
		  		transferObject.addLiquidacion(objTo);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced)
		  		{
		  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoRepartoNotarialTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced)liquidacionEnh, cache);
		  		}
		  		transferObject.addLiquidacion(objTo);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionEnh instanceof gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced)
		  		{
		  		gov.sir.core.negocio.modelo.Liquidacion objTo = (gov.sir.core.negocio.modelo.Liquidacion)cache.get(liquidacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Liquidacion)LiquidacionTurnoRestitucionRepartoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced)liquidacionEnh, cache);
		  		}
		  		transferObject.addLiquidacion(objTo);
		  		assigned = true;
		  		}
		  				  				  	}
		}
				
		  // SolicitudesPadre
    	List solicitudesPadre = null;
  	try
  	{
  	solicitudesPadre = enhancedObject.getSolicitudesPadres();
  	} catch (Throwable th) {}
	  	if (solicitudesPadre != null)
	  	{
		  	for(Iterator itera = solicitudesPadre.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced solicitudesPadreEnh = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.SolicitudAsociada objTo = (gov.sir.core.negocio.modelo.SolicitudAsociada)cache.get(solicitudesPadreEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.SolicitudAsociada)SolicitudAsociadaTransferCopier.deepCopy(solicitudesPadreEnh, cache);
		  		}
		  		transferObject.addSolicitudesPadre(objTo);
		  		}
		  				  	}
		}
				
		  // SolicitudesHija
    	List solicitudesHija = null;
  	try
  	{
  	solicitudesHija = enhancedObject.getSolicitudesHijas();
  	} catch (Throwable th) {}
	  	if (solicitudesHija != null)
	  	{
		  	for(Iterator itera = solicitudesHija.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced solicitudesHijaEnh = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.SolicitudAsociada objTo = (gov.sir.core.negocio.modelo.SolicitudAsociada)cache.get(solicitudesHijaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.SolicitudAsociada)SolicitudAsociadaTransferCopier.deepCopy(solicitudesHijaEnh, cache);
		  		}
		  		transferObject.addSolicitudesHija(objTo);
		  		}
		  				  	}
		}
				
		  // LastIdLiquidacion
  	    try {
	  	transferObject.setLastIdLiquidacion(enhancedObject.getLastIdLiquidacion());
	  	} catch (Throwable th ) {}
	  			
		  // SolicitudesVinculada
    	List solicitudesVinculada = null;
  	try
  	{
  	solicitudesVinculada = enhancedObject.getSolicitudesVinculadas();
  	} catch (Throwable th) {}
	  	if (solicitudesVinculada != null)
	  	{
		  	for(Iterator itera = solicitudesVinculada.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.SolicitudVinculadaEnhanced solicitudesVinculadaEnh = (gov.sir.core.negocio.modelo.jdogenie.SolicitudVinculadaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.SolicitudVinculada objTo = (gov.sir.core.negocio.modelo.SolicitudVinculada)cache.get(solicitudesVinculadaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.SolicitudVinculada)SolicitudVinculadaTransferCopier.deepCopy(solicitudesVinculadaEnh, cache);
		  		}
		  		transferObject.addSolicitudesVinculada(objTo);
		  		}
		  				  	}
		}
				
		  // NumReimpresionesRecibo
  	    try {
	  	transferObject.setNumReimpresionesRecibo(enhancedObject.getNumReimpresionesRecibo());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SolicitudDevolucion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Ciudadano
  	    gov.sir.core.negocio.modelo.Ciudadano ciudadano = null;
	  	try {
	  	ciudadano = (gov.sir.core.negocio.modelo.Ciudadano)transferObject.getCiudadano();
	  	} catch (Throwable th ) {}
	  	if (ciudadano != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced)cache.get(ciudadano);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced)CiudadanoTransferCopier.deepCopy(ciudadano, cache);
	  		}
	  		enhancedObject.setCiudadano(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Aprobada
      try {
  	enhancedObject.setAprobada(transferObject.isAprobada());
  	} catch (Throwable th ) {}
  			
  	//NumeroFolios
    try {
    	enhancedObject.setNumeroFolios(transferObject.getNumeroFolios());
	} catch (Throwable th ) {}
	
	
		  // CheckedItem
    	List checkedItem = null;
  	try
  	{
  	checkedItem = transferObject.getCheckedItems();
  	} catch (Throwable th) { }
	  	if (checkedItem != null)
	  	{
		  	for(Iterator itera = checkedItem.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.SolCheckedItemDev checkedItemto = (gov.sir.core.negocio.modelo.SolCheckedItemDev)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced)cache.get(checkedItemto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced)SolCheckedItemDevTransferCopier.deepCopy(checkedItemto, cache);
		  		}
		  		enhancedObject.addCheckedItem(objEn);
		  				  		}
		  				  	}
		}
	  	
	  	 // solicitantes
    	List solicitantes = null;
  	try
  	{
  		solicitantes = transferObject.getSolicitantes();
  	} catch (Throwable th) { }
	  	if (solicitantes != null)
	  	{
		  	for(Iterator iteras = solicitantes.iterator();
		  			iteras.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Solicitante solicitante = (gov.sir.core.negocio.modelo.Solicitante)iteras.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.SolicitanteEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitanteEnhanced)cache.get(solicitante);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitanteEnhanced)SolicitanteTransferCopier.deepCopy(solicitante, cache);
		  		}
		  		enhancedObject.addSolicitante(objEn);
		  				  		}
		  				  	}
		}
	  	
	 // consignaciones
    	List consignaciones = null;
  	try
  	{
  		consignaciones = transferObject.getConsignaciones();
  	} catch (Throwable th) { }
	  	if (consignaciones != null)
	  	{
		  	for(Iterator iteras = consignaciones.iterator();
		  			iteras.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Consignacion consignacion = (gov.sir.core.negocio.modelo.Consignacion)iteras.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced)cache.get(consignacion);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced)ConsignacionTransferCopier.deepCopy(consignacion, cache);
		  		}
		  		enhancedObject.addConsignacion(objEn);
		  				  		}
		  				  	}
		}
				
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Circulo
  	    gov.sir.core.negocio.modelo.Circulo circulo = null;
	  	try {
	  	circulo = (gov.sir.core.negocio.modelo.Circulo)transferObject.getCirculo();
	  	} catch (Throwable th ) {}
	  	if (circulo != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)cache.get(circulo);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced)CirculoTransferCopier.deepCopy(circulo, cache);
	  		}
	  		enhancedObject.setCirculo(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Proceso
  	    gov.sir.core.negocio.modelo.Proceso proceso = null;
	  	try {
	  	proceso = (gov.sir.core.negocio.modelo.Proceso)transferObject.getProceso();
	  	} catch (Throwable th ) {}
	  	if (proceso != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced)cache.get(proceso);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced)ProcesoTransferCopier.deepCopy(proceso, cache);
	  		}
	  		enhancedObject.setProceso(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Comentario
  	    try {
	  	enhancedObject.setComentario(transferObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Fecha
  	    try {
		if (transferObject.getFecha() != null)
		{
		  	enhancedObject.setFecha(new Date(transferObject.getFecha().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // DatosAntiguoSistema
  	    gov.sir.core.negocio.modelo.DatosAntiguoSistema datosAntiguoSistema = null;
	  	try {
	  	datosAntiguoSistema = (gov.sir.core.negocio.modelo.DatosAntiguoSistema)transferObject.getDatosAntiguoSistema();
	  	} catch (Throwable th ) {}
	  	if (datosAntiguoSistema != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced)cache.get(datosAntiguoSistema);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.DatosAntiguoSistemaEnhanced)DatosAntiguoSistemaTransferCopier.deepCopy(datosAntiguoSistema, cache);
	  		}
	  		enhancedObject.setDatosAntiguoSistema(objEn);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // Turno
  	    gov.sir.core.negocio.modelo.Turno turno = null;
	  	try {
	  	turno = (gov.sir.core.negocio.modelo.Turno)transferObject.getTurno();
	  	} catch (Throwable th ) {}
	  	if (turno != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)cache.get(turno);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)TurnoTransferCopier.deepCopy(turno, cache);
	  		}
	  		enhancedObject.setTurno(objEn);
	  		}
	  			  		
	  	}
	  			
		  // TurnoAnterior
  	    gov.sir.core.negocio.modelo.Turno turnoAnterior = null;
	  	try {
	  	turnoAnterior = (gov.sir.core.negocio.modelo.Turno)transferObject.getTurnoAnterior();
	  	} catch (Throwable th ) {}
	  	if (turnoAnterior != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)cache.get(turnoAnterior);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced)TurnoTransferCopier.deepCopy(turnoAnterior, cache);
	  		}
	  		enhancedObject.setTurnoAnterior(objEn);
	  		}
	  			  		
	  	}
	  			
		  // SolicitudFolio
    	List solicitudFolio = null;
  	try
  	{
  	solicitudFolio = transferObject.getSolicitudFolios();
  	} catch (Throwable th) { }
	  	if (solicitudFolio != null)
	  	{
		  	for(Iterator itera = solicitudFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.SolicitudFolio solicitudFolioto = (gov.sir.core.negocio.modelo.SolicitudFolio)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced)cache.get(solicitudFolioto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced)SolicitudFolioTransferCopier.deepCopy(solicitudFolioto, cache);
		  		}
		  		enhancedObject.addSolicitudFolio(objEn);
		  				  		}
		  				  	}
		}
				
		  // Liquidacion
    	List liquidacion = null;
  	try
  	{
  	liquidacion = transferObject.getLiquidaciones();
  	} catch (Throwable th) { }
	  	if (liquidacion != null)
	  	{
		  	for(Iterator itera = liquidacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Liquidacion liquidacionto = (gov.sir.core.negocio.modelo.Liquidacion)itera.next();
		  				  		
		  		if (liquidacionto instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado)
		  		{
		  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced)cache.get(liquidacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced)LiquidacionTurnoCertificadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado)liquidacionto, cache);
		  		}
		  		enhancedObject.addLiquidacion(objEn);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionto instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo)
		  		{
		  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced)cache.get(liquidacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced)LiquidacionTurnoCertificadoMasivoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo)liquidacionto, cache);
		  		}
		  		enhancedObject.addLiquidacion(objEn);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionto instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta)
		  		{
		  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced)cache.get(liquidacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoConsultaEnhanced)LiquidacionTurnoConsultaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta)liquidacionto, cache);
		  		}
		  		enhancedObject.addLiquidacion(objEn);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionto instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion)
		  		{
		  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced)cache.get(liquidacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCorreccionEnhanced)LiquidacionTurnoCorreccionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion)liquidacionto, cache);
		  		}
		  		enhancedObject.addLiquidacion(objEn);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionto instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion)
		  		{
		  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced)cache.get(liquidacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoDevolucionEnhanced)LiquidacionTurnoDevolucionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion)liquidacionto, cache);
		  		}
		  		enhancedObject.addLiquidacion(objEn);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionto instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia)
		  		{
		  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced)cache.get(liquidacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoFotocopiaEnhanced)LiquidacionTurnoFotocopiaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia)liquidacionto, cache);
		  		}
		  		enhancedObject.addLiquidacion(objEn);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionto instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro)
		  		{
		  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced)cache.get(liquidacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRegistroEnhanced)LiquidacionTurnoRegistroTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro)liquidacionto, cache);
		  		}
		  		enhancedObject.addLiquidacion(objEn);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionto instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial)
		  		{
		  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced)cache.get(liquidacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRepartoNotarialEnhanced)LiquidacionTurnoRepartoNotarialTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial)liquidacionto, cache);
		  		}
		  		enhancedObject.addLiquidacion(objEn);
		  		assigned = true;
		  		}
		  				  		
		  		if (liquidacionto instanceof gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto)
		  		{
		  		gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced)cache.get(liquidacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoRestitucionRepartoEnhanced)LiquidacionTurnoRestitucionRepartoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto)liquidacionto, cache);
		  		}
		  		enhancedObject.addLiquidacion(objEn);
		  		assigned = true;
		  		}
		  				  				  	}
		}
				
		  // SolicitudesPadre
    	List solicitudesPadre = null;
  	try
  	{
  	solicitudesPadre = transferObject.getSolicitudesPadres();
  	} catch (Throwable th) { }
	  	if (solicitudesPadre != null)
	  	{
		  	for(Iterator itera = solicitudesPadre.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.SolicitudAsociada solicitudesPadreto = (gov.sir.core.negocio.modelo.SolicitudAsociada)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced)cache.get(solicitudesPadreto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced)SolicitudAsociadaTransferCopier.deepCopy(solicitudesPadreto, cache);
		  		}
		  		enhancedObject.addSolicitudesPadre(objEn);
		  				  		}
		  				  	}
		}
				
		  // SolicitudesHija
    	List solicitudesHija = null;
  	try
  	{
  	solicitudesHija = transferObject.getSolicitudesHijas();
  	} catch (Throwable th) { }
	  	if (solicitudesHija != null)
	  	{
		  	for(Iterator itera = solicitudesHija.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.SolicitudAsociada solicitudesHijato = (gov.sir.core.negocio.modelo.SolicitudAsociada)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced)cache.get(solicitudesHijato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced)SolicitudAsociadaTransferCopier.deepCopy(solicitudesHijato, cache);
		  		}
		  		enhancedObject.addSolicitudesHija(objEn);
		  				  		}
		  				  	}
		}
				
		  // LastIdLiquidacion
  	    try {
	  	enhancedObject.setLastIdLiquidacion(transferObject.getLastIdLiquidacion());
	  	} catch (Throwable th ) {}
	  			
		  // SolicitudesVinculada
    	List solicitudesVinculada = null;
  	try
  	{
  	solicitudesVinculada = transferObject.getSolicitudesVinculadas();
  	} catch (Throwable th) { }
	  	if (solicitudesVinculada != null)
	  	{
		  	for(Iterator itera = solicitudesVinculada.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.SolicitudVinculada solicitudesVinculadato = (gov.sir.core.negocio.modelo.SolicitudVinculada)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.SolicitudVinculadaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudVinculadaEnhanced)cache.get(solicitudesVinculadato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudVinculadaEnhanced)SolicitudVinculadaTransferCopier.deepCopy(solicitudesVinculadato, cache);
		  		}
		  		enhancedObject.addSolicitudesVinculada(objEn);
		  				  		}
		  				  	}
		}
				
		  // NumReimpresionesRecibo
  	    try {
	  	enhancedObject.setNumReimpresionesRecibo(transferObject.getNumReimpresionesRecibo());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
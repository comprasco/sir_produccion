package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class FolioTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Folio transferObject = new gov.sir.core.negocio.modelo.Folio();
		cache.put(enhancedObject, transferObject );
		
		  // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Comentario
  	    try {
	  	transferObject.setComentario(enhancedObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Documento
  	  gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced documentoEnh = null;
	  try {
	  	documentoEnh = enhancedObject.getDocumento();
	  	} catch (Throwable th) {}
	  	if (documentoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Documento objTo =  (gov.sir.core.negocio.modelo.Documento)cache.get(documentoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Documento)DocumentoTransferCopier.deepCopy(documentoEnh, cache);
	  		}
	  		transferObject.setDocumento(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Salvedade
    	List salvedade = null;
  	try
  	{
  	salvedade = enhancedObject.getSalvedades();
  	} catch (Throwable th) {}
	  	if (salvedade != null)
	  	{
		  	for(Iterator itera = salvedade.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced salvedadeEnh = (gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.SalvedadFolio objTo = (gov.sir.core.negocio.modelo.SalvedadFolio)cache.get(salvedadeEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.SalvedadFolio)SalvedadFolioTransferCopier.deepCopy(salvedadeEnh, cache);
		  		}
		  		transferObject.addSalvedade(objTo);
		  		}
		  				  	}
		}
				
		  // Estado
  	  gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced estadoEnh = null;
	  try {
	  	estadoEnh = enhancedObject.getEstado();
	  	} catch (Throwable th) {}
	  	if (estadoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.EstadoFolio objTo =  (gov.sir.core.negocio.modelo.EstadoFolio)cache.get(estadoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.EstadoFolio)EstadoFolioTransferCopier.deepCopy(estadoEnh, cache);
	  		}
	  		transferObject.setEstado(objTo);
	  		}
	  			  		
	  	}
	  			
		  // LastIdSalvedad
  	    try {
	  	transferObject.setLastIdSalvedad(enhancedObject.getLastIdSalvedad());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioCreacion
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioCreacionEnh = null;
	  try {
	  	usuarioCreacionEnh = enhancedObject.getUsuarioCreacion();
	  	} catch (Throwable th) {}
	  	if (usuarioCreacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioCreacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioCreacionEnh, cache);
	  		}
	  		transferObject.setUsuarioCreacion(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Complementacion
  	  gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced complementacionEnh = null;
	  try {
	  	complementacionEnh = enhancedObject.getComplementacion();
	  	} catch (Throwable th) {}
	  	if (complementacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Complementacion objTo =  (gov.sir.core.negocio.modelo.Complementacion)cache.get(complementacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Complementacion)ComplementacionTransferCopier.deepCopy(complementacionEnh, cache);
	  		}
	  		transferObject.setComplementacion(objTo);
	  		}
	  			  		
	  	}
	  			
		  // CodCatastral
  	    try {
	  	transferObject.setCodCatastral(enhancedObject.getCodCatastral());
	  	} catch (Throwable th ) {}
	  			
		  // TipoPredio
  	  gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced tipoPredioEnh = null;
	  try {
	  	tipoPredioEnh = enhancedObject.getTipoPredio();
	  	} catch (Throwable th) {}
	  	if (tipoPredioEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoPredio objTo =  (gov.sir.core.negocio.modelo.TipoPredio)cache.get(tipoPredioEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoPredio)TipoPredioTransferCopier.deepCopy(tipoPredioEnh, cache);
	  		}
	  		transferObject.setTipoPredio(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ZonaRegistral
  	  gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced zonaRegistralEnh = null;
	  try {
	  	zonaRegistralEnh = enhancedObject.getZonaRegistral();
	  	} catch (Throwable th) {}
	  	if (zonaRegistralEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.ZonaRegistral objTo =  (gov.sir.core.negocio.modelo.ZonaRegistral)cache.get(zonaRegistralEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.ZonaRegistral)ZonaRegistralTransferCopier.deepCopy(zonaRegistralEnh, cache);
	  		}
	  		transferObject.setZonaRegistral(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Anotacione
    	List anotacione = null;
  	try
  	{
  	anotacione = enhancedObject.getAnotaciones();
  	} catch (Throwable th) {}
	  	if (anotacione != null)
	  	{
		  	for(Iterator itera = anotacione.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced anotacioneEnh = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Anotacion objTo = (gov.sir.core.negocio.modelo.Anotacion)cache.get(anotacioneEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Anotacion)AnotacionTransferCopier.deepCopy(anotacioneEnh, cache);
		  		}
		  		transferObject.addAnotacione(objTo);
		  		}
		  				  	}
		}
				
		  // BloqueoFolio
    	List bloqueoFolio = null;
  	try
  	{
  	bloqueoFolio = enhancedObject.getBloqueoFolios();
  	} catch (Throwable th) {}
	  	if (bloqueoFolio != null)
	  	{
		  	for(Iterator itera = bloqueoFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced bloqueoFolioEnh = (gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.BloqueoFolio objTo = (gov.sir.core.negocio.modelo.BloqueoFolio)cache.get(bloqueoFolioEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.BloqueoFolio)BloqueoFolioTransferCopier.deepCopy(bloqueoFolioEnh, cache);
		  		}
		  		transferObject.addBloqueoFolio(objTo);
		  		}
		  				  	}
		}
				
		  // Direccione
    	List direccione = null;
  	try
  	{
  	direccione = enhancedObject.getDirecciones();
  	} catch (Throwable th) {}
	  	if (direccione != null)
	  	{
		  	for(Iterator itera = direccione.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced direccioneEnh = (gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Direccion objTo = (gov.sir.core.negocio.modelo.Direccion)cache.get(direccioneEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Direccion)DireccionTransferCopier.deepCopy(direccioneEnh, cache);
		  		}
		  		transferObject.addDireccione(objTo);
		  		}
		  				  	}
		}
				
		  // Lindero
  	    try {
	  	transferObject.setLindero(enhancedObject.getLindero());
	  	} catch (Throwable th ) {}
            
                // NUPRE
  	    try {
	  	transferObject.setNupre(enhancedObject.getNupre());
	  	} catch (Throwable th ) {}
            
                // Hectareas Area del Terreno
  	    try {
	  	transferObject.setHectareas(enhancedObject.getHectareas());
	  	} catch (Throwable th ) {}
            
                // Metros Area del Terreno
  	    try {
	  	transferObject.setMetros(enhancedObject.getMetros());
	  	} catch (Throwable th ) {}
            
                // Centimetros Area del Terreno
  	    try {
	  	transferObject.setCentimetros(enhancedObject.getCentimetros());
	  	} catch (Throwable th ) {}
            
                // DeterminaInm
  	    try {
	  	transferObject.setDeterminaInm(enhancedObject.getDeterminaInm());
	  	} catch (Throwable th ) {}
            
                // PrivMetros Area Privada
  	    try {
	  	transferObject.setPrivMetros(enhancedObject.getPrivMetros());
	  	} catch (Throwable th ) {}
            
                // PrivCentimetros Area Privada
  	    try {
	  	transferObject.setPrivCentimetros(enhancedObject.getPrivCentimetros());
	  	} catch (Throwable th ) {}
            
                // ConsMetros Area Construida
  	    try {
	  	transferObject.setConsMetros(enhancedObject.getConsMetros());
	  	} catch (Throwable th ) {}
            
                // ConsCentimetros Area Construida
  	    try {
	  	transferObject.setConsCentimetros(enhancedObject.getConsCentimetros());
	  	} catch (Throwable th ) {}
            
                // Coeficiente
  	    try {
	  	transferObject.setCoeficiente(enhancedObject.getCoeficiente());
	  	} catch (Throwable th ) {}
            
                // Linderos Tecnicamente Definidos
  	    try {
	  	transferObject.setLinderosDef(enhancedObject.getLinderosDef());
	  	} catch (Throwable th ) {}
    		
                // Avaluo
  	    try {
	  	transferObject.setAvaluo(enhancedObject.getAvaluo());
	  	} catch (Throwable th ) {}
            
                // FechaAvaluo
  		try {
		if (enhancedObject.getFechaAvaluo()!= null)
		{
	  	 transferObject.setFechaAvaluo(new Date(enhancedObject.getFechaAvaluo().getTime()));
	  	}
	  	} catch (Throwable th ) {}
            
                // DestEconomica
  	    try {
	  	transferObject.setDestEconomica(enhancedObject.getDestEconomica());
	  	} catch (Throwable th ) {}
            
		// FechaApertura
  		try {
		if (enhancedObject.getFechaApertura() != null)
		{
	  	 transferObject.setFechaApertura(new Date(enhancedObject.getFechaApertura().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Definitivo
    try {
  	transferObject.setDefinitivo(enhancedObject.isDefinitivo());
  } catch (Throwable th ) {}
  			
		  // LastIdDireccion
  	    try {
	  	transferObject.setLastIdDireccion(enhancedObject.getLastIdDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdAnotacion
  	    try {
	  	transferObject.setLastIdAnotacion(enhancedObject.getLastIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // Radicacion
  	    try {
	  	transferObject.setRadicacion(enhancedObject.getRadicacion());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioGrabacion
  	    try {
	  	transferObject.setUsuarioGrabacion(enhancedObject.getUsuarioGrabacion());
	  	} catch (Throwable th ) {}
	  			
		  // CodCatastralAnterior
  	    try {
	  	transferObject.setCodCatastralAnterior(enhancedObject.getCodCatastralAnterior());
	  	} catch (Throwable th ) {}
	  			
		  // TurnosFolio
    	List turnosFolio = null;
  	try
  	{
  	turnosFolio = enhancedObject.getTurnosFolios();
  	} catch (Throwable th) {}
	  	if (turnosFolio != null)
	  	{
		  	for(Iterator itera = turnosFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.TurnoFolioEnhanced turnosFolioEnh = (gov.sir.core.negocio.modelo.jdogenie.TurnoFolioEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.TurnoFolio objTo = (gov.sir.core.negocio.modelo.TurnoFolio)cache.get(turnosFolioEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.TurnoFolio)TurnoFolioTransferCopier.deepCopy(turnosFolioEnh, cache);
		  		}
		  		transferObject.addTurnosFolio(objTo);
		  		}
		  				  	}
		}
				
		  // Inconsistente
    try {
  	transferObject.setInconsistente(enhancedObject.isInconsistente());
  } catch (Throwable th ) {}
  			
		  // HistorialEstado
    	List historialEstado = null;
  	try
  	{
  	historialEstado = enhancedObject.getHistorialEstados();
  	} catch (Throwable th) {}
	  	if (historialEstado != null)
	  	{
		  	for(Iterator itera = historialEstado.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced historialEstadoEnh = (gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.EstadoHistoria objTo = (gov.sir.core.negocio.modelo.EstadoHistoria)cache.get(historialEstadoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.EstadoHistoria)EstadoHistoriaTransferCopier.deepCopy(historialEstadoEnh, cache);
		  		}
		  		transferObject.addHistorialEstado(objTo);
		  		}
		  				  	}
		}
				
		  // LastIdHistorialEstados
  	    try {
	  	transferObject.setLastIdHistorialEstados(enhancedObject.getLastIdHistorialEstados());
	  	} catch (Throwable th ) {}
	  			
		  // NombreLote
  	    try {
	  	transferObject.setNombreLote(enhancedObject.getNombreLote());
	  	} catch (Throwable th ) {}
	  			
		  // Directo
  	    try {
	  	transferObject.setDirecto(enhancedObject.getDirecto());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Folio transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Circulo
  	    try {
	  	enhancedObject.setCirculo(transferObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Comentario
  	    try {
	  	enhancedObject.setComentario(transferObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Documento
  	    gov.sir.core.negocio.modelo.Documento documento = null;
	  	try {
	  	documento = (gov.sir.core.negocio.modelo.Documento)transferObject.getDocumento();
	  	} catch (Throwable th ) {}
	  	if (documento != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced)cache.get(documento);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced)DocumentoTransferCopier.deepCopy(documento, cache);
	  		}
	  		enhancedObject.setDocumento(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Salvedade
    	List salvedade = null;
  	try
  	{
  	salvedade = transferObject.getSalvedades();
  	} catch (Throwable th) { }
	  	if (salvedade != null)
	  	{
		  	for(Iterator itera = salvedade.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.SalvedadFolio salvedadeto = (gov.sir.core.negocio.modelo.SalvedadFolio)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced)cache.get(salvedadeto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced)SalvedadFolioTransferCopier.deepCopy(salvedadeto, cache);
		  		}
		  		enhancedObject.addSalvedade(objEn);
		  				  		}
		  				  	}
		}
				
		  // Estado
  	    gov.sir.core.negocio.modelo.EstadoFolio estado = null;
	  	try {
	  	estado = (gov.sir.core.negocio.modelo.EstadoFolio)transferObject.getEstado();
	  	} catch (Throwable th ) {}
	  	if (estado != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced)cache.get(estado);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced)EstadoFolioTransferCopier.deepCopy(estado, cache);
	  		}
	  		enhancedObject.setEstado(objEn);
	  		}
	  			  		
	  	}
	  			
		  // LastIdSalvedad
  	    try {
	  	enhancedObject.setLastIdSalvedad(transferObject.getLastIdSalvedad());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioCreacion
  	    gov.sir.core.negocio.modelo.Usuario usuarioCreacion = null;
	  	try {
	  	usuarioCreacion = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuarioCreacion();
	  	} catch (Throwable th ) {}
	  	if (usuarioCreacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuarioCreacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuarioCreacion, cache);
	  		}
	  		enhancedObject.setUsuarioCreacion(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Complementacion
  	    gov.sir.core.negocio.modelo.Complementacion complementacion = null;
	  	try {
	  	complementacion = (gov.sir.core.negocio.modelo.Complementacion)transferObject.getComplementacion();
	  	} catch (Throwable th ) {}
	  	if (complementacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced)cache.get(complementacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced)ComplementacionTransferCopier.deepCopy(complementacion, cache);
	  		}
	  		enhancedObject.setComplementacion(objEn);
	  		}
	  			  		
	  	}
	  			
		  // CodCatastral
  	    try {
	  	enhancedObject.setCodCatastral(transferObject.getCodCatastral());
	  	} catch (Throwable th ) {}
	  			
		  // TipoPredio
  	    gov.sir.core.negocio.modelo.TipoPredio tipoPredio = null;
	  	try {
	  	tipoPredio = (gov.sir.core.negocio.modelo.TipoPredio)transferObject.getTipoPredio();
	  	} catch (Throwable th ) {}
	  	if (tipoPredio != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced)cache.get(tipoPredio);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced)TipoPredioTransferCopier.deepCopy(tipoPredio, cache);
	  		}
	  		enhancedObject.setTipoPredio(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ZonaRegistral
  	    gov.sir.core.negocio.modelo.ZonaRegistral zonaRegistral = null;
	  	try {
	  	zonaRegistral = (gov.sir.core.negocio.modelo.ZonaRegistral)transferObject.getZonaRegistral();
	  	} catch (Throwable th ) {}
	  	if (zonaRegistral != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced)cache.get(zonaRegistral);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced)ZonaRegistralTransferCopier.deepCopy(zonaRegistral, cache);
	  		}
	  		enhancedObject.setZonaRegistral(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Anotacione
    	List anotacione = null;
  	try
  	{
  	anotacione = transferObject.getAnotaciones();
  	} catch (Throwable th) { }
	  	if (anotacione != null)
	  	{
		  	for(Iterator itera = anotacione.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Anotacion anotacioneto = (gov.sir.core.negocio.modelo.Anotacion)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)cache.get(anotacioneto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)AnotacionTransferCopier.deepCopy(anotacioneto, cache);
		  		}
		  		enhancedObject.addAnotacione(objEn);
		  				  		}
		  				  	}
		}
				
		  // BloqueoFolio
    	List bloqueoFolio = null;
  	try
  	{
  	bloqueoFolio = transferObject.getBloqueoFolios();
  	} catch (Throwable th) { }
	  	if (bloqueoFolio != null)
	  	{
		  	for(Iterator itera = bloqueoFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.BloqueoFolio bloqueoFolioto = (gov.sir.core.negocio.modelo.BloqueoFolio)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced)cache.get(bloqueoFolioto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.BloqueoFolioEnhanced)BloqueoFolioTransferCopier.deepCopy(bloqueoFolioto, cache);
		  		}
		  		enhancedObject.addBloqueoFolio(objEn);
		  				  		}
		  				  	}
		}
				
		  // Direccione
    	List direccione = null;
  	try
  	{
  	direccione = transferObject.getDirecciones();
  	} catch (Throwable th) { }
	  	if (direccione != null)
	  	{
		  	for(Iterator itera = direccione.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Direccion direccioneto = (gov.sir.core.negocio.modelo.Direccion)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced)cache.get(direccioneto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced)DireccionTransferCopier.deepCopy(direccioneto, cache);
		  		}
		  		enhancedObject.addDireccione(objEn);
		  				  		}
		  				  	}
		}
				
		  // Lindero
  	    try {
	  	enhancedObject.setLindero(transferObject.getLindero());
	  	} catch (Throwable th ) {}
            
            // NUPRE
  	    try {
	  	enhancedObject.setNupre(transferObject.getNupre());
	  	} catch (Throwable th ) {}
            
            // Hectareas Area del Terreno
  	    try {
	  	enhancedObject.setHectareas(transferObject.getHectareas());
	  	} catch (Throwable th ) {}
            
            // Metros Area del Terreno
  	    try {
	  	enhancedObject.setMetros(transferObject.getMetros());
	  	} catch (Throwable th ) {}
            
            // Centimetros Area del Terreno
  	    try {
	  	enhancedObject.setCentimetros(transferObject.getCentimetros());
	  	} catch (Throwable th ) {}
            
            // Determinación de Inmueble
  	    try {
	  	enhancedObject.setDeterminaInm(transferObject.getDeterminaInm());
	  	} catch (Throwable th ) {}
            
            // PrivMetros Area Privada
  	    try {
	  	enhancedObject.setPrivMetros(transferObject.getPrivMetros());
	  	} catch (Throwable th ) {}
            
            // PrivCentimetros Area Privada
  	    try {
	  	enhancedObject.setPrivCentimetros(transferObject.getPrivCentimetros());
	  	} catch (Throwable th ) {}
            
            // ConsMetros Area Construida
  	    try {
	  	enhancedObject.setConsMetros(transferObject.getConsMetros());
	  	} catch (Throwable th ) {}
            
            // ConsCentimetros Area Construida
  	    try {
	  	enhancedObject.setConsCentimetros(transferObject.getConsCentimetros());
	  	} catch (Throwable th ) {}
            
            // Coeficiente
  	    try {
	  	enhancedObject.setCoeficiente(transferObject.getCoeficiente());
	  	} catch (Throwable th ) {}
            
            // Linderos Tecnicamente Definidos
  	    try {
	  	enhancedObject.setLinderosDef(transferObject.getLinderosDef());
	  	} catch (Throwable th ) {}
            
            // Avaluo
  	    try {
	  	enhancedObject.setAvaluo(transferObject.getAvaluo());
	  	} catch (Throwable th ) {}
	  			
            // FechaAvaluo
  	    try {
		if (transferObject.getFechaAvaluo()!= null)
		{
		  	enhancedObject.setFechaAvaluo(new Date(transferObject.getFechaAvaluo().getTime()));
		}
		} catch (Throwable th ) {}
            
            // DestEconomica
  	    try {
	  	enhancedObject.setDestEconomica(transferObject.getDestEconomica());
	  	} catch (Throwable th ) {}
	  			
		  // FechaApertura
  	    try {
		if (transferObject.getFechaApertura() != null)
		{
		  	enhancedObject.setFechaApertura(new Date(transferObject.getFechaApertura().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Definitivo
      try {
  	enhancedObject.setDefinitivo(transferObject.isDefinitivo());
  	} catch (Throwable th ) {}
  			
		  // LastIdDireccion
  	    try {
	  	enhancedObject.setLastIdDireccion(transferObject.getLastIdDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdAnotacion
  	    try {
	  	enhancedObject.setLastIdAnotacion(transferObject.getLastIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // Radicacion
  	    try {
	  	enhancedObject.setRadicacion(transferObject.getRadicacion());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioGrabacion
  	    try {
	  	enhancedObject.setUsuarioGrabacion(transferObject.getUsuarioGrabacion());
	  	} catch (Throwable th ) {}
	  			
		  // CodCatastralAnterior
  	    try {
	  	enhancedObject.setCodCatastralAnterior(transferObject.getCodCatastralAnterior());
	  	} catch (Throwable th ) {}
	  			
		  // TurnosFolio
    	List turnosFolio = null;
  	try
  	{
  	turnosFolio = transferObject.getTurnosFolios();
  	} catch (Throwable th) { }
	  	if (turnosFolio != null)
	  	{
		  	for(Iterator itera = turnosFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.TurnoFolio turnosFolioto = (gov.sir.core.negocio.modelo.TurnoFolio)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.TurnoFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoFolioEnhanced)cache.get(turnosFolioto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoFolioEnhanced)TurnoFolioTransferCopier.deepCopy(turnosFolioto, cache);
		  		}
		  		enhancedObject.addTurnosFolio(objEn);
		  				  		}
		  				  	}
		}
				
		  // Inconsistente
      try {
  	enhancedObject.setInconsistente(transferObject.isInconsistente());
  	} catch (Throwable th ) {}
  			
		  // HistorialEstado
    	List historialEstado = null;
  	try
  	{
  	historialEstado = transferObject.getHistorialEstados();
  	} catch (Throwable th) { }
	  	if (historialEstado != null)
	  	{
		  	for(Iterator itera = historialEstado.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.EstadoHistoria historialEstadoto = (gov.sir.core.negocio.modelo.EstadoHistoria)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced)cache.get(historialEstadoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced)EstadoHistoriaTransferCopier.deepCopy(historialEstadoto, cache);
		  		}
		  		enhancedObject.addHistorialEstado(objEn);
		  				  		}
		  				  	}
		}
				
		  // LastIdHistorialEstados
  	    try {
	  	enhancedObject.setLastIdHistorialEstados(transferObject.getLastIdHistorialEstados());
	  	} catch (Throwable th ) {}
	  			
		  // NombreLote
  	    try {
	  	enhancedObject.setNombreLote(transferObject.getNombreLote());
	  	} catch (Throwable th ) {}
	  			
		  // Directo
  	    try {
	  	enhancedObject.setDirecto(transferObject.getDirecto());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
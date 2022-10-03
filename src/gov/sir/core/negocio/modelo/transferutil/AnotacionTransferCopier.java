package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class AnotacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Anotacion transferObject = new gov.sir.core.negocio.modelo.Anotacion();
		cache.put(enhancedObject, transferObject );
		
		  // Valor
  	    try {
	  	transferObject.setValor(enhancedObject.getValor());
	  	} catch (Throwable th ) {}
            
            // Modalidad
  	    try {
	  	transferObject.setModalidad(enhancedObject.getModalidad());
	  	} catch (Throwable th ) {}
	  			
		  // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdWorkflow
  	    try {
	  	transferObject.setIdWorkflow(enhancedObject.getIdWorkflow());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion
  	    try {
	  	transferObject.setIdAnotacion(enhancedObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // Folio
  	  gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced folioEnh = null;
	  try {
	  	folioEnh = enhancedObject.getFolio();
	  	} catch (Throwable th) {}
	  	if (folioEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Folio objTo =  (gov.sir.core.negocio.modelo.Folio)cache.get(folioEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Folio)FolioTransferCopier.deepCopy(folioEnh, cache);
	  		}
	  		transferObject.setFolio(objTo);
	  		}
	  			  		
	  	}
	  			
		  // NaturalezaJuridica
  	  gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced naturalezaJuridicaEnh = null;
	  try {
	  	naturalezaJuridicaEnh = enhancedObject.getNaturalezaJuridica();
	  	} catch (Throwable th) {}
	  	if (naturalezaJuridicaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.NaturalezaJuridica objTo =  (gov.sir.core.negocio.modelo.NaturalezaJuridica)cache.get(naturalezaJuridicaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.NaturalezaJuridica)NaturalezaJuridicaTransferCopier.deepCopy(naturalezaJuridicaEnh, cache);
	  		}
	  		transferObject.setNaturalezaJuridica(objTo);
	  		}
	  			  		
	  	}
	  			
		  // TipoAnotacion
  	  gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced tipoAnotacionEnh = null;
	  try {
	  	tipoAnotacionEnh = enhancedObject.getTipoAnotacion();
	  	} catch (Throwable th) {}
	  	if (tipoAnotacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoAnotacion objTo =  (gov.sir.core.negocio.modelo.TipoAnotacion)cache.get(tipoAnotacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoAnotacion)TipoAnotacionTransferCopier.deepCopy(tipoAnotacionEnh, cache);
	  		}
	  		transferObject.setTipoAnotacion(objTo);
	  		}
	  			  		
	  	}
	  			
		  // AnotacionesCancelacion
    	List anotacionesCancelacion = null;
  	try
  	{
  	anotacionesCancelacion = enhancedObject.getAnotacionesCancelacions();
  	} catch (Throwable th) {}
	  	if (anotacionesCancelacion != null)
	  	{
		  	for(Iterator itera = anotacionesCancelacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced anotacionesCancelacionEnh = (gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Cancelacion objTo = (gov.sir.core.negocio.modelo.Cancelacion)cache.get(anotacionesCancelacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Cancelacion)CancelacionTransferCopier.deepCopy(anotacionesCancelacionEnh, cache);
		  		}
		  		transferObject.addAnotacionesCancelacion(objTo);
		  		}
		  				  	}
		}
				
		  // AnotacionesCiudadano
    	List anotacionesCiudadano = null;
  	try
  	{
  	anotacionesCiudadano = enhancedObject.getAnotacionesCiudadanos();
  	} catch (Throwable th) {}
	  	if (anotacionesCiudadano != null)
	  	{
		  	for(Iterator itera = anotacionesCiudadano.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced anotacionesCiudadanoEnh = (gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.AnotacionCiudadano objTo = (gov.sir.core.negocio.modelo.AnotacionCiudadano)cache.get(anotacionesCiudadanoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.AnotacionCiudadano)AnotacionCiudadanoTransferCopier.deepCopy(anotacionesCiudadanoEnh, cache);
		  		}
		  		transferObject.addAnotacionesCiudadano(objTo);
		  		}
		  				  	}
		}
				
		  // AnotacionesHijo
    	List anotacionesHijo = null;
  	try
  	{
  	anotacionesHijo = enhancedObject.getAnotacionesHijos();
  	} catch (Throwable th) {}
	  	if (anotacionesHijo != null)
	  	{
		  	for(Iterator itera = anotacionesHijo.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced anotacionesHijoEnh = (gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.FolioDerivado objTo = (gov.sir.core.negocio.modelo.FolioDerivado)cache.get(anotacionesHijoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.FolioDerivado)FolioDerivadoTransferCopier.deepCopy(anotacionesHijoEnh, cache);
		  		}
		  		transferObject.addAnotacionesHijo(objTo);
		  		}
		  				  	}
		}
				
		  // AnotacionesPadre
    	List anotacionesPadre = null;
  	try
  	{
  	anotacionesPadre = enhancedObject.getAnotacionesPadre();
  	} catch (Throwable th) {}
	  	if (anotacionesPadre != null)
	  	{
		  	for(Iterator itera = anotacionesPadre.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced anotacionesPadreEnh = (gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.FolioDerivado objTo = (gov.sir.core.negocio.modelo.FolioDerivado)cache.get(anotacionesPadreEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.FolioDerivado)FolioDerivadoTransferCopier.deepCopy(anotacionesPadreEnh, cache);
		  		}
		  		transferObject.addAnotacionesPadre(objTo);
		  		}
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
		  		gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced salvedadeEnh = (gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.SalvedadAnotacion objTo = (gov.sir.core.negocio.modelo.SalvedadAnotacion)cache.get(salvedadeEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.SalvedadAnotacion)SalvedadAnotacionTransferCopier.deepCopy(salvedadeEnh, cache);
		  		}
		  		transferObject.addSalvedade(objTo);
		  		}
		  				  	}
		}
				
		  // FechaRadicacion
  		try {
		if (enhancedObject.getFechaRadicacion() != null)
		{
	  	 transferObject.setFechaRadicacion(new Date(enhancedObject.getFechaRadicacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // NumRadicacion
  	    try {
	  	transferObject.setNumRadicacion(enhancedObject.getNumRadicacion());
	  	} catch (Throwable th ) {}
	  			
		  // Estado
  	  gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhanced estadoEnh = null;
	  try {
	  	estadoEnh = enhancedObject.getEstado();
	  	} catch (Throwable th) {}
	  	if (estadoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.EstadoAnotacion objTo =  (gov.sir.core.negocio.modelo.EstadoAnotacion)cache.get(estadoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.EstadoAnotacion)EstadoAnotacionTransferCopier.deepCopy(estadoEnh, cache);
	  		}
	  		transferObject.setEstado(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Orden
  	    try {
	  	transferObject.setOrden(enhancedObject.getOrden());
	  	} catch (Throwable th ) {}
	  			
		  // Especificacion
  	    try {
	  	transferObject.setEspecificacion(enhancedObject.getEspecificacion());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdSalvedad
  	    try {
	  	transferObject.setLastIdSalvedad(enhancedObject.getLastIdSalvedad());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoAnotacion
    	List turnoAnotacion = null;
  	try
  	{
  	turnoAnotacion = enhancedObject.getTurnoAnotacions();
  	} catch (Throwable th) {}
	  	if (turnoAnotacion != null)
	  	{
		  	for(Iterator itera = turnoAnotacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced turnoAnotacionEnh = (gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.TurnoAnotacion objTo = (gov.sir.core.negocio.modelo.TurnoAnotacion)cache.get(turnoAnotacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.TurnoAnotacion)TurnoAnotacionTransferCopier.deepCopy(turnoAnotacionEnh, cache);
		  		}
		  		transferObject.addTurnoAnotacion(objTo);
		  		}
		  				  	}
		}
				
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
	  			
		  // ToDelete
    try {
  	transferObject.setToDelete(enhancedObject.isToDelete());
  } catch (Throwable th ) {}
  			
		  // Temporal
    try {
  	transferObject.setTemporal(enhancedObject.isTemporal());
  } catch (Throwable th ) {}
  			
		  // TemporalConContraparteDefinitiva
    try {
  	transferObject.setTemporalConContraparteDefinitiva(enhancedObject.isTemporalConContraparteDefinitiva());
  } catch (Throwable th ) {}
  			
		  // IdAnotacionModificada
  	    try {
	  	transferObject.setIdAnotacionModificada(enhancedObject.getIdAnotacionModificada());
	  	} catch (Throwable th ) {}
	  			
		  // ToUpdateValor
    try {
  	transferObject.setToUpdateValor(enhancedObject.isToUpdateValor());
  } catch (Throwable th ) {}
  			
		  // UsuarioCreacionTMP
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioCreacionTMPEnh = null;
	  try {
	  	usuarioCreacionTMPEnh = enhancedObject.getUsuarioCreacionTMP();
	  	} catch (Throwable th) {}
	  	if (usuarioCreacionTMPEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioCreacionTMPEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioCreacionTMPEnh, cache);
	  		}
	  		transferObject.setUsuarioCreacionTMP(objTo);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // OrdenLPAD
  	    try {
	  	transferObject.setOrdenLPAD(enhancedObject.getOrdenLPAD());
	  	} catch (Throwable th ) {}
	  			
		  // Heredada
    try {
  	transferObject.setHeredada(enhancedObject.isHeredada());
  } catch (Throwable th ) {}
  			
		  // Link
    try {
  	transferObject.setLink(enhancedObject.isLink());
  } catch (Throwable th ) {}
  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Anotacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Valor
  	    try {
	  	enhancedObject.setValor(transferObject.getValor());
	  	} catch (Throwable th ) {}
            
            // Modalidad
  	    try {
	  	enhancedObject.setModalidad(transferObject.getModalidad());
	  	} catch (Throwable th ) {}
	  			
		  // Circulo
  	    try {
	  	enhancedObject.setCirculo(transferObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdWorkflow
  	    try {
	  	enhancedObject.setIdWorkflow(transferObject.getIdWorkflow());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion
  	    try {
	  	enhancedObject.setIdAnotacion(transferObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // Folio
  	    gov.sir.core.negocio.modelo.Folio folio = null;
	  	try {
	  	folio = (gov.sir.core.negocio.modelo.Folio)transferObject.getFolio();
	  	} catch (Throwable th ) {}
	  	if (folio != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)cache.get(folio);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)FolioTransferCopier.deepCopy(folio, cache);
	  		}
	  		enhancedObject.setFolio(objEn);
	  		}
	  			  		
	  	}
	  			
		  // NaturalezaJuridica
  	    gov.sir.core.negocio.modelo.NaturalezaJuridica naturalezaJuridica = null;
	  	try {
	  	naturalezaJuridica = (gov.sir.core.negocio.modelo.NaturalezaJuridica)transferObject.getNaturalezaJuridica();
	  	} catch (Throwable th ) {}
	  	if (naturalezaJuridica != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced)cache.get(naturalezaJuridica);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced)NaturalezaJuridicaTransferCopier.deepCopy(naturalezaJuridica, cache);
	  		}
	  		enhancedObject.setNaturalezaJuridica(objEn);
	  		}
	  			  		
	  	}
	  			
		  // TipoAnotacion
  	    gov.sir.core.negocio.modelo.TipoAnotacion tipoAnotacion = null;
	  	try {
	  	tipoAnotacion = (gov.sir.core.negocio.modelo.TipoAnotacion)transferObject.getTipoAnotacion();
	  	} catch (Throwable th ) {}
	  	if (tipoAnotacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced)cache.get(tipoAnotacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced)TipoAnotacionTransferCopier.deepCopy(tipoAnotacion, cache);
	  		}
	  		enhancedObject.setTipoAnotacion(objEn);
	  		}
	  			  		
	  	}
	  			
		  // AnotacionesCancelacion
    	List anotacionesCancelacion = null;
  	try
  	{
  	anotacionesCancelacion = transferObject.getAnotacionesCancelacions();
  	} catch (Throwable th) { }
	  	if (anotacionesCancelacion != null)
	  	{
		  	for(Iterator itera = anotacionesCancelacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Cancelacion anotacionesCancelacionto = (gov.sir.core.negocio.modelo.Cancelacion)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced)cache.get(anotacionesCancelacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced)CancelacionTransferCopier.deepCopy(anotacionesCancelacionto, cache);
		  		}
		  		enhancedObject.addAnotacionesCancelacion(objEn);
		  				  		}
		  				  	}
		}
				
		  // AnotacionesCiudadano
    	List anotacionesCiudadano = null;
  	try
  	{
  	anotacionesCiudadano = transferObject.getAnotacionesCiudadanos();
  	} catch (Throwable th) { }
	  	if (anotacionesCiudadano != null)
	  	{
		  	for(Iterator itera = anotacionesCiudadano.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.AnotacionCiudadano anotacionesCiudadanoto = (gov.sir.core.negocio.modelo.AnotacionCiudadano)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced)cache.get(anotacionesCiudadanoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced)AnotacionCiudadanoTransferCopier.deepCopy(anotacionesCiudadanoto, cache);
		  		}
		  		enhancedObject.addAnotacionesCiudadano(objEn);
		  				  		}
		  				  	}
		}
				
		  // AnotacionesHijo
    	List anotacionesHijo = null;
  	try
  	{
  	anotacionesHijo = transferObject.getAnotacionesHijos();
  	} catch (Throwable th) { }
	  	if (anotacionesHijo != null)
	  	{
		  	for(Iterator itera = anotacionesHijo.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.FolioDerivado anotacionesHijoto = (gov.sir.core.negocio.modelo.FolioDerivado)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced)cache.get(anotacionesHijoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced)FolioDerivadoTransferCopier.deepCopy(anotacionesHijoto, cache);
		  		}
		  		enhancedObject.addAnotacionesHijo(objEn);
		  				  		}
		  				  	}
		}
				
		  // AnotacionesPadre
    	List anotacionesPadre = null;
  	try
  	{
  	anotacionesPadre = transferObject.getAnotacionesPadre();
  	} catch (Throwable th) { }
	  	if (anotacionesPadre != null)
	  	{
		  	for(Iterator itera = anotacionesPadre.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.FolioDerivado anotacionesPadreto = (gov.sir.core.negocio.modelo.FolioDerivado)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced)cache.get(anotacionesPadreto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced)FolioDerivadoTransferCopier.deepCopy(anotacionesPadreto, cache);
		  		}
		  		enhancedObject.addAnotacionesPadre(objEn);
		  				  		}
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
		  		gov.sir.core.negocio.modelo.SalvedadAnotacion salvedadeto = (gov.sir.core.negocio.modelo.SalvedadAnotacion)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced)cache.get(salvedadeto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced)SalvedadAnotacionTransferCopier.deepCopy(salvedadeto, cache);
		  		}
		  		enhancedObject.addSalvedade(objEn);
		  				  		}
		  				  	}
		}
				
		  // FechaRadicacion
  	    try {
		if (transferObject.getFechaRadicacion() != null)
		{
		  	enhancedObject.setFechaRadicacion(new Date(transferObject.getFechaRadicacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // NumRadicacion
  	    try {
	  	enhancedObject.setNumRadicacion(transferObject.getNumRadicacion());
	  	} catch (Throwable th ) {}
	  			
		  // Estado
  	    gov.sir.core.negocio.modelo.EstadoAnotacion estado = null;
	  	try {
	  	estado = (gov.sir.core.negocio.modelo.EstadoAnotacion)transferObject.getEstado();
	  	} catch (Throwable th ) {}
	  	if (estado != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhanced)cache.get(estado);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhanced)EstadoAnotacionTransferCopier.deepCopy(estado, cache);
	  		}
	  		enhancedObject.setEstado(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Orden
  	    try {
	  	enhancedObject.setOrden(transferObject.getOrden());
	  	} catch (Throwable th ) {}
	  			
		  // Especificacion
  	    try {
	  	enhancedObject.setEspecificacion(transferObject.getEspecificacion());
	  	} catch (Throwable th ) {}
	  			
		  // LastIdSalvedad
  	    try {
	  	enhancedObject.setLastIdSalvedad(transferObject.getLastIdSalvedad());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoAnotacion
    	List turnoAnotacion = null;
  	try
  	{
  	turnoAnotacion = transferObject.getTurnoAnotacions();
  	} catch (Throwable th) { }
	  	if (turnoAnotacion != null)
	  	{
		  	for(Iterator itera = turnoAnotacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.TurnoAnotacion turnoAnotacionto = (gov.sir.core.negocio.modelo.TurnoAnotacion)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced)cache.get(turnoAnotacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced)TurnoAnotacionTransferCopier.deepCopy(turnoAnotacionto, cache);
		  		}
		  		enhancedObject.addTurnoAnotacion(objEn);
		  				  		}
		  				  	}
		}
				
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
	  			
		  // ToDelete
      try {
  	enhancedObject.setToDelete(transferObject.isToDelete());
  	} catch (Throwable th ) {}
  			
		  // Temporal
      try {
  	enhancedObject.setTemporal(transferObject.isTemporal());
  	} catch (Throwable th ) {}
  			
		  // TemporalConContraparteDefinitiva
      try {
  	enhancedObject.setTemporalConContraparteDefinitiva(transferObject.isTemporalConContraparteDefinitiva());
  	} catch (Throwable th ) {}
  			
		  // IdAnotacionModificada
  	    try {
	  	enhancedObject.setIdAnotacionModificada(transferObject.getIdAnotacionModificada());
	  	} catch (Throwable th ) {}
	  			
		  // ToUpdateValor
      try {
  	enhancedObject.setToUpdateValor(transferObject.isToUpdateValor());
  	} catch (Throwable th ) {}
  			
		  // UsuarioCreacionTMP
  	    gov.sir.core.negocio.modelo.Usuario usuarioCreacionTMP = null;
	  	try {
	  	usuarioCreacionTMP = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuarioCreacionTMP();
	  	} catch (Throwable th ) {}
	  	if (usuarioCreacionTMP != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuarioCreacionTMP);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuarioCreacionTMP, cache);
	  		}
	  		enhancedObject.setUsuarioCreacionTMP(objEn);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // OrdenLPAD
  	    try {
	  	enhancedObject.setOrdenLPAD(transferObject.getOrdenLPAD());
	  	} catch (Throwable th ) {}
	  			
		  // Heredada
      try {
  	enhancedObject.setHeredada(transferObject.isHeredada());
  	} catch (Throwable th ) {}
  			
		  // Link
      try {
  	enhancedObject.setLink(transferObject.isLink());
  	} catch (Throwable th ) {}
  			
		
		return enhancedObject;
	}
}
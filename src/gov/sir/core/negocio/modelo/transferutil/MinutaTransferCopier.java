package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class MinutaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Minuta transferObject = new gov.sir.core.negocio.modelo.Minuta();
		cache.put(enhancedObject, transferObject );
		
		  // Valor
  	    try {
	  	transferObject.setValor(enhancedObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Comentario
  	    try {
	  	transferObject.setComentario(enhancedObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Estado
  	    try {
	  	transferObject.setEstado(enhancedObject.getEstado());
	  	} catch (Throwable th ) {}
	  			
		  // Solicitud
  	  gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced solicitudEnh = null;
	  try {
	  	solicitudEnh = enhancedObject.getSolicitud();
	  	} catch (Throwable th) {}
	  	if (solicitudEnh != null)
	  	{
	  		boolean assigned = false;
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudAntiguoSistemaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudCertificadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudCertificadoMasivoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudConsultaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudCorreccionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudDevolucionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudFotocopiaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudRegistroTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudRepartoNotarialTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitudEnh instanceof gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo = (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudRestitucionRepartoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced)solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		assigned = true;
	  		}
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Solicitud objTo =  (gov.sir.core.negocio.modelo.Solicitud)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Solicitud)SolicitudTransferCopier.deepCopy(solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdMinuta
  	    try {
	  	transferObject.setIdMinuta(enhancedObject.getIdMinuta());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroFolios
  	    try {
	  	transferObject.setNumeroFolios(enhancedObject.getNumeroFolios());
	  	} catch (Throwable th ) {}
	  			
		  // Unidades
  	    try {
	  	transferObject.setUnidades(enhancedObject.getUnidades());
	  	} catch (Throwable th ) {}
	  			
		  // Categoria
  	  gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced categoriaEnh = null;
	  try {
	  	categoriaEnh = enhancedObject.getCategoria();
	  	} catch (Throwable th) {}
	  	if (categoriaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Categoria objTo =  (gov.sir.core.negocio.modelo.Categoria)cache.get(categoriaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Categoria)CategoriaTransferCopier.deepCopy(categoriaEnh, cache);
	  		}
	  		transferObject.setCategoria(objTo);
	  		}
	  			  		
	  	}
	  			
		  // OficinaCategoriaAsignada
  	  gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced oficinaCategoriaAsignadaEnh = null;
	  try {
	  	oficinaCategoriaAsignadaEnh = enhancedObject.getOficinaCategoriaAsignada();
	  	} catch (Throwable th) {}
	  	if (oficinaCategoriaAsignadaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.OficinaCategoria objTo =  (gov.sir.core.negocio.modelo.OficinaCategoria)cache.get(oficinaCategoriaAsignadaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.OficinaCategoria)OficinaCategoriaTransferCopier.deepCopy(oficinaCategoriaAsignadaEnh, cache);
	  		}
	  		transferObject.setOficinaCategoriaAsignada(objTo);
	  		}
	  			  		
	  	}
	  			
		  // RepartoNotarial
  	  gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhanced repartoNotarialEnh = null;
	  try {
	  	repartoNotarialEnh = enhancedObject.getRepartoNotarial();
	  	} catch (Throwable th) {}
	  	if (repartoNotarialEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.RepartoNotarial objTo =  (gov.sir.core.negocio.modelo.RepartoNotarial)cache.get(repartoNotarialEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.RepartoNotarial)RepartoNotarialTransferCopier.deepCopy(repartoNotarialEnh, cache);
	  		}
	  		transferObject.setRepartoNotarial(objTo);
	  		}
	  			  		
	  	}
	  			
		  // EntidadesPublica
    	List entidadesPublica = null;
  	try
  	{
  	entidadesPublica = enhancedObject.getEntidadesPublicas();
  	} catch (Throwable th) {}
	  	if (entidadesPublica != null)
	  	{
		  	for(Iterator itera = entidadesPublica.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced entidadesPublicaEnh = (gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.MinutaEntidadPublica objTo = (gov.sir.core.negocio.modelo.MinutaEntidadPublica)cache.get(entidadesPublicaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.MinutaEntidadPublica)MinutaEntidadPublicaTransferCopier.deepCopy(entidadesPublicaEnh, cache);
		  		}
		  		transferObject.addEntidadesPublica(objTo);
		  		}
		  				  	}
		}
				
		  // OtorgantesNaturale
    	List otorgantesNaturale = null;
  	try
  	{
  	otorgantesNaturale = enhancedObject.getOtorgantesNaturales();
  	} catch (Throwable th) {}
	  	if (otorgantesNaturale != null)
	  	{
		  	for(Iterator itera = otorgantesNaturale.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced otorgantesNaturaleEnh = (gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.OtorganteNatural objTo = (gov.sir.core.negocio.modelo.OtorganteNatural)cache.get(otorgantesNaturaleEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.OtorganteNatural)OtorganteNaturalTransferCopier.deepCopy(otorgantesNaturaleEnh, cache);
		  		}
		  		transferObject.addOtorgantesNaturale(objTo);
		  		}
		  				  	}
		}
				
		  // AccionesNotariale
    	List accionesNotariale = null;
  	try
  	{
  	accionesNotariale = enhancedObject.getAccionesNotariales();
  	} catch (Throwable th) {}
	  	if (accionesNotariale != null)
	  	{
		  	for(Iterator itera = accionesNotariale.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced accionesNotarialeEnh = (gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.MinutaAccionNotarial objTo = (gov.sir.core.negocio.modelo.MinutaAccionNotarial)cache.get(accionesNotarialeEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.MinutaAccionNotarial)MinutaAccionNotarialTransferCopier.deepCopy(accionesNotarialeEnh, cache);
		  		}
		  		transferObject.addAccionesNotariale(objTo);
		  		}
		  				  	}
		}
				
		  // Normal
    try {
  	transferObject.setNormal(enhancedObject.isNormal());
  } catch (Throwable th ) {}
  			
		  // CirculoNotarial
  	  gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced circuloNotarialEnh = null;
	  try {
	  	circuloNotarialEnh = enhancedObject.getCirculoNotarial();
	  	} catch (Throwable th) {}
	  	if (circuloNotarialEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.CirculoNotarial objTo =  (gov.sir.core.negocio.modelo.CirculoNotarial)cache.get(circuloNotarialEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.CirculoNotarial)CirculoNotarialTransferCopier.deepCopy(circuloNotarialEnh, cache);
	  		}
	  		transferObject.setCirculoNotarial(objTo);
	  		}
	  			  		
	  	}
	  			
		  // NumModificaciones
  	    try {
	  	transferObject.setNumModificaciones(enhancedObject.getNumModificaciones());
	  	} catch (Throwable th ) {}
	  			
		  // FechaAnulada
  		try {
		if (enhancedObject.getFechaAnulada() != null)
		{
	  	 transferObject.setFechaAnulada(new Date(enhancedObject.getFechaAnulada().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioAnula
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioAnulaEnh = null;
	  try {
	  	usuarioAnulaEnh = enhancedObject.getUsuarioAnula();
	  	} catch (Throwable th) {}
	  	if (usuarioAnulaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioAnulaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioAnulaEnh, cache);
	  		}
	  		transferObject.setUsuarioAnula(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Minuta transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.MinutaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Valor
  	    try {
	  	enhancedObject.setValor(transferObject.getValor());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Comentario
  	    try {
	  	enhancedObject.setComentario(transferObject.getComentario());
	  	} catch (Throwable th ) {}
	  			
		  // Estado
  	    try {
	  	enhancedObject.setEstado(transferObject.getEstado());
	  	} catch (Throwable th ) {}
	  			
		  // Solicitud
  	    gov.sir.core.negocio.modelo.Solicitud solicitud = null;
	  	try {
	  	solicitud = (gov.sir.core.negocio.modelo.Solicitud)transferObject.getSolicitud();
	  	} catch (Throwable th ) {}
	  	if (solicitud != null)
	  	{
	  		boolean assigned = false;
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudAntiguoSistema)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudAntiguoSistemaEnhanced)SolicitudAntiguoSistemaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudAntiguoSistema)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudCertificado)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced)SolicitudCertificadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudCertificado)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced)SolicitudCertificadoMasivoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudConsulta)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced)SolicitudConsultaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudConsulta)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudCorreccion)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudCorreccionEnhanced)SolicitudCorreccionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudCorreccion)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudDevolucion)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudDevolucionEnhanced)SolicitudDevolucionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudDevolucion)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudFotocopia)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudFotocopiaEnhanced)SolicitudFotocopiaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudFotocopia)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudRegistro)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRegistroEnhanced)SolicitudRegistroTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudRegistro)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudRepartoNotarial)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRepartoNotarialEnhanced)SolicitudRepartoNotarialTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudRepartoNotarial)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  		
	  		if (solicitud instanceof gov.sir.core.negocio.modelo.SolicitudRestitucionReparto)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudRestitucionRepartoEnhanced)SolicitudRestitucionRepartoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.SolicitudRestitucionReparto)solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		assigned = true;
	  		}
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudEnhanced)SolicitudTransferCopier.deepCopy(solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdMinuta
  	    try {
	  	enhancedObject.setIdMinuta(transferObject.getIdMinuta());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroFolios
  	    try {
	  	enhancedObject.setNumeroFolios(transferObject.getNumeroFolios());
	  	} catch (Throwable th ) {}
	  			
		  // Unidades
  	    try {
	  	enhancedObject.setUnidades(transferObject.getUnidades());
	  	} catch (Throwable th ) {}
	  			
		  // Categoria
  	    gov.sir.core.negocio.modelo.Categoria categoria = null;
	  	try {
	  	categoria = (gov.sir.core.negocio.modelo.Categoria)transferObject.getCategoria();
	  	} catch (Throwable th ) {}
	  	if (categoria != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced)cache.get(categoria);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced)CategoriaTransferCopier.deepCopy(categoria, cache);
	  		}
	  		enhancedObject.setCategoria(objEn);
	  		}
	  			  		
	  	}
	  			
		  // OficinaCategoriaAsignada
  	    gov.sir.core.negocio.modelo.OficinaCategoria oficinaCategoriaAsignada = null;
	  	try {
	  	oficinaCategoriaAsignada = (gov.sir.core.negocio.modelo.OficinaCategoria)transferObject.getOficinaCategoriaAsignada();
	  	} catch (Throwable th ) {}
	  	if (oficinaCategoriaAsignada != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced)cache.get(oficinaCategoriaAsignada);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced)OficinaCategoriaTransferCopier.deepCopy(oficinaCategoriaAsignada, cache);
	  		}
	  		enhancedObject.setOficinaCategoriaAsignada(objEn);
	  		}
	  			  		
	  	}
	  			
		  // RepartoNotarial
  	    gov.sir.core.negocio.modelo.RepartoNotarial repartoNotarial = null;
	  	try {
	  	repartoNotarial = (gov.sir.core.negocio.modelo.RepartoNotarial)transferObject.getRepartoNotarial();
	  	} catch (Throwable th ) {}
	  	if (repartoNotarial != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhanced)cache.get(repartoNotarial);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.RepartoNotarialEnhanced)RepartoNotarialTransferCopier.deepCopy(repartoNotarial, cache);
	  		}
	  		enhancedObject.setRepartoNotarial(objEn);
	  		}
	  			  		
	  	}
	  			
		  // EntidadesPublica
    	List entidadesPublica = null;
  	try
  	{
  	entidadesPublica = transferObject.getEntidadesPublicas();
  	} catch (Throwable th) { }
	  	if (entidadesPublica != null)
	  	{
		  	for(Iterator itera = entidadesPublica.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.MinutaEntidadPublica entidadesPublicato = (gov.sir.core.negocio.modelo.MinutaEntidadPublica)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced)cache.get(entidadesPublicato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.MinutaEntidadPublicaEnhanced)MinutaEntidadPublicaTransferCopier.deepCopy(entidadesPublicato, cache);
		  		}
		  		enhancedObject.addEntidadesPublica(objEn);
		  				  		}
		  				  	}
		}
				
		  // OtorgantesNaturale
    	List otorgantesNaturale = null;
  	try
  	{
  	otorgantesNaturale = transferObject.getOtorgantesNaturales();
  	} catch (Throwable th) { }
	  	if (otorgantesNaturale != null)
	  	{
		  	for(Iterator itera = otorgantesNaturale.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.OtorganteNatural otorgantesNaturaleto = (gov.sir.core.negocio.modelo.OtorganteNatural)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced)cache.get(otorgantesNaturaleto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.OtorganteNaturalEnhanced)OtorganteNaturalTransferCopier.deepCopy(otorgantesNaturaleto, cache);
		  		}
		  		enhancedObject.addOtorgantesNaturale(objEn);
		  				  		}
		  				  	}
		}
				
		  // AccionesNotariale
    	List accionesNotariale = null;
  	try
  	{
  	accionesNotariale = transferObject.getAccionesNotariales();
  	} catch (Throwable th) { }
	  	if (accionesNotariale != null)
	  	{
		  	for(Iterator itera = accionesNotariale.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.MinutaAccionNotarial accionesNotarialeto = (gov.sir.core.negocio.modelo.MinutaAccionNotarial)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced)cache.get(accionesNotarialeto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.MinutaAccionNotarialEnhanced)MinutaAccionNotarialTransferCopier.deepCopy(accionesNotarialeto, cache);
		  		}
		  		enhancedObject.addAccionesNotariale(objEn);
		  				  		}
		  				  	}
		}
				
		  // Normal
      try {
  	enhancedObject.setNormal(transferObject.isNormal());
  	} catch (Throwable th ) {}
  			
		  // CirculoNotarial
  	    gov.sir.core.negocio.modelo.CirculoNotarial circuloNotarial = null;
	  	try {
	  	circuloNotarial = (gov.sir.core.negocio.modelo.CirculoNotarial)transferObject.getCirculoNotarial();
	  	} catch (Throwable th ) {}
	  	if (circuloNotarial != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced)cache.get(circuloNotarial);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoNotarialEnhanced)CirculoNotarialTransferCopier.deepCopy(circuloNotarial, cache);
	  		}
	  		enhancedObject.setCirculoNotarial(objEn);
	  		}
	  			  		
	  	}
	  			
		  // NumModificaciones
  	    try {
	  	enhancedObject.setNumModificaciones(transferObject.getNumModificaciones());
	  	} catch (Throwable th ) {}
	  			
		  // FechaAnulada
  	    try {
		if (transferObject.getFechaAnulada() != null)
		{
		  	enhancedObject.setFechaAnulada(new Date(transferObject.getFechaAnulada().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // UsuarioAnula
  	    gov.sir.core.negocio.modelo.Usuario usuarioAnula = null;
	  	try {
	  	usuarioAnula = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuarioAnula();
	  	} catch (Throwable th ) {}
	  	if (usuarioAnula != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuarioAnula);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuarioAnula, cache);
	  		}
	  		enhancedObject.setUsuarioAnula(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}
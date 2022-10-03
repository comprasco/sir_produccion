package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SolicitudFolioTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SolicitudFolio transferObject = new gov.sir.core.negocio.modelo.SolicitudFolio();
		cache.put(enhancedObject, transferObject );
		
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // Estado
  	    try {
	  	transferObject.setEstado(enhancedObject.getEstado());
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
	  			
		  // Oficio
  	    try {
	  	transferObject.setOficio(enhancedObject.getOficio());
	  	} catch (Throwable th ) {}
	  			
		  // Marcado
    try {
  	transferObject.setMarcado(enhancedObject.isMarcado());
  } catch (Throwable th ) {}
  			
		  // RecienCreadoAntiguoSistema
    try {
  	transferObject.setRecienCreadoAntiguoSistema(enhancedObject.isRecienCreadoAntiguoSistema());
  } catch (Throwable th ) {}
  			
		  // IdBusquedaConsulta
  	    try {
	  	transferObject.setIdBusquedaConsulta(enhancedObject.getIdBusquedaConsulta());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SolicitudFolio transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // Circulo
  	    try {
	  	enhancedObject.setCirculo(transferObject.getCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // Estado
  	    try {
	  	enhancedObject.setEstado(transferObject.getEstado());
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
	  			
		  // Oficio
  	    try {
	  	enhancedObject.setOficio(transferObject.getOficio());
	  	} catch (Throwable th ) {}
	  			
		  // Marcado
      try {
  	enhancedObject.setMarcado(transferObject.isMarcado());
  	} catch (Throwable th ) {}
  			
		  // RecienCreadoAntiguoSistema
      try {
  	enhancedObject.setRecienCreadoAntiguoSistema(transferObject.isRecienCreadoAntiguoSistema());
  	} catch (Throwable th ) {}
  			
		  // IdBusquedaConsulta
  	    try {
	  	enhancedObject.setIdBusquedaConsulta(transferObject.getIdBusquedaConsulta());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
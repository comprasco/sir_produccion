package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Autom�ticamente con TransferUtilsGenerator.
 *
 */
public class RangoFolioTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.RangoFolioEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.RangoFolio transferObject = new gov.sir.core.negocio.modelo.RangoFolio();
		cache.put(enhancedObject, transferObject );
		
		  // IdSolicitud
  	    try {
	  	transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
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
	  			
		  // IdRangoFolio
  	    try {
	  	transferObject.setIdRangoFolio(enhancedObject.getIdRangoFolio());
	  	} catch (Throwable th ) {}
	  			
		  // MatriculaFinal
  	    try {
	  	transferObject.setMatriculaFinal(enhancedObject.getMatriculaFinal());
	  	} catch (Throwable th ) {}
	  			
		  // MatriculaInicial
  	    try {
	  	transferObject.setMatriculaInicial(enhancedObject.getMatriculaInicial());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.RangoFolio transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.RangoFolioEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.RangoFolioEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdSolicitud
  	    try {
	  	enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
	  	} catch (Throwable th ) {}
	  			
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
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
	  			
		  // IdRangoFolio
  	    try {
	  	enhancedObject.setIdRangoFolio(transferObject.getIdRangoFolio());
	  	} catch (Throwable th ) {}
	  			
		  // MatriculaFinal
  	    try {
	  	enhancedObject.setMatriculaFinal(transferObject.getMatriculaFinal());
	  	} catch (Throwable th ) {}
	  			
		  // MatriculaInicial
  	    try {
	  	enhancedObject.setMatriculaInicial(transferObject.getMatriculaInicial());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
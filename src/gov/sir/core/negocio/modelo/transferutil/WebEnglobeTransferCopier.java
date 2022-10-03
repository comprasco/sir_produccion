package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class WebEnglobeTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.WebEnglobe transferObject = new gov.sir.core.negocio.modelo.WebEnglobe();
		cache.put(enhancedObject, transferObject );
		
		  // FolioNuevo
  	  gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced folioNuevoEnh = null;
	  try {
	  	folioNuevoEnh = enhancedObject.getFolioNuevo();
	  	} catch (Throwable th) {}
	  	if (folioNuevoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.WebFolioNuevo objTo =  (gov.sir.core.negocio.modelo.WebFolioNuevo)cache.get(folioNuevoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.WebFolioNuevo)WebFolioNuevoTransferCopier.deepCopy(folioNuevoEnh, cache);
	  		}
	  		transferObject.setFolioNuevo(objTo);
	  		}
	  			  		
	  	}
	  			
		  // FoliosEnglobar
    	List foliosEnglobar = null;
  	try
  	{
  	foliosEnglobar = enhancedObject.getFoliosEnglobars();
  	} catch (Throwable th) {}
	  	if (foliosEnglobar != null)
	  	{
		  	for(Iterator itera = foliosEnglobar.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced foliosEnglobarEnh = (gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.WebFolioEnglobe objTo = (gov.sir.core.negocio.modelo.WebFolioEnglobe)cache.get(foliosEnglobarEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.WebFolioEnglobe)WebFolioEnglobeTransferCopier.deepCopy(foliosEnglobarEnh, cache);
		  		}
		  		transferObject.addFoliosEnglobar(objTo);
		  		}
		  				  	}
		}
				
		  // IdMatriculaUbicacion
  	    try {
	  	transferObject.setIdMatriculaUbicacion(enhancedObject.getIdMatriculaUbicacion());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // IdProceso
  	    try {
	  	transferObject.setIdProceso(enhancedObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // Anotacion
  	  gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced anotacionEnh = null;
	  try {
	  	anotacionEnh = enhancedObject.getAnotacion();
	  	} catch (Throwable th) {}
	  	if (anotacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.WebAnotacion objTo =  (gov.sir.core.negocio.modelo.WebAnotacion)cache.get(anotacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.WebAnotacion)WebAnotacionTransferCopier.deepCopy(anotacionEnh, cache);
	  		}
	  		transferObject.setAnotacion(objTo);
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
	  			
		  // IdWebSegeng
  	    try {
	  	transferObject.setIdWebSegeng(enhancedObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
		  // TipoOperacion
  	    try {
	  	transferObject.setTipoOperacion(enhancedObject.getTipoOperacion());
	  	} catch (Throwable th ) {}
	  			
		  // FoliosHeredado
    	List foliosHeredado = null;
  	try
  	{
  	foliosHeredado = enhancedObject.getFoliosHeredados();
  	} catch (Throwable th) {}
	  	if (foliosHeredado != null)
	  	{
		  	for(Iterator itera = foliosHeredado.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced foliosHeredadoEnh = (gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.WebFolioHeredado objTo = (gov.sir.core.negocio.modelo.WebFolioHeredado)cache.get(foliosHeredadoEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.WebFolioHeredado)WebFolioHeredadoTransferCopier.deepCopy(foliosHeredadoEnh, cache);
		  		}
		  		transferObject.addFoliosHeredado(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.WebEnglobe transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.WebEnglobeEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FolioNuevo
  	    gov.sir.core.negocio.modelo.WebFolioNuevo folioNuevo = null;
	  	try {
	  	folioNuevo = (gov.sir.core.negocio.modelo.WebFolioNuevo)transferObject.getFolioNuevo();
	  	} catch (Throwable th ) {}
	  	if (folioNuevo != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced)cache.get(folioNuevo);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebFolioNuevoEnhanced)WebFolioNuevoTransferCopier.deepCopy(folioNuevo, cache);
	  		}
	  		enhancedObject.setFolioNuevo(objEn);
	  		}
	  			  		
	  	}
	  			
		  // FoliosEnglobar
    	List foliosEnglobar = null;
  	try
  	{
  	foliosEnglobar = transferObject.getFoliosEnglobars();
  	} catch (Throwable th) { }
	  	if (foliosEnglobar != null)
	  	{
		  	for(Iterator itera = foliosEnglobar.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.WebFolioEnglobe foliosEnglobarto = (gov.sir.core.negocio.modelo.WebFolioEnglobe)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced)cache.get(foliosEnglobarto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebFolioEnglobeEnhanced)WebFolioEnglobeTransferCopier.deepCopy(foliosEnglobarto, cache);
		  		}
		  		enhancedObject.addFoliosEnglobar(objEn);
		  				  		}
		  				  	}
		}
				
		  // IdMatriculaUbicacion
  	    try {
	  	enhancedObject.setIdMatriculaUbicacion(transferObject.getIdMatriculaUbicacion());
	  	} catch (Throwable th ) {}
	  			
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
	  			
		  // IdProceso
  	    try {
	  	enhancedObject.setIdProceso(transferObject.getIdProceso());
	  	} catch (Throwable th ) {}
	  			
		  // Anotacion
  	    gov.sir.core.negocio.modelo.WebAnotacion anotacion = null;
	  	try {
	  	anotacion = (gov.sir.core.negocio.modelo.WebAnotacion)transferObject.getAnotacion();
	  	} catch (Throwable th ) {}
	  	if (anotacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced)cache.get(anotacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced)WebAnotacionTransferCopier.deepCopy(anotacion, cache);
	  		}
	  		enhancedObject.setAnotacion(objEn);
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
	  			
		  // IdWebSegeng
  	    try {
	  	enhancedObject.setIdWebSegeng(transferObject.getIdWebSegeng());
	  	} catch (Throwable th ) {}
	  			
		  // TipoOperacion
  	    try {
	  	enhancedObject.setTipoOperacion(transferObject.getTipoOperacion());
	  	} catch (Throwable th ) {}
	  			
		  // FoliosHeredado
    	List foliosHeredado = null;
  	try
  	{
  	foliosHeredado = transferObject.getFoliosHeredados();
  	} catch (Throwable th) { }
	  	if (foliosHeredado != null)
	  	{
		  	for(Iterator itera = foliosHeredado.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.WebFolioHeredado foliosHeredadoto = (gov.sir.core.negocio.modelo.WebFolioHeredado)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced)cache.get(foliosHeredadoto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.WebFolioHeredadoEnhanced)WebFolioHeredadoTransferCopier.deepCopy(foliosHeredadoto, cache);
		  		}
		  		enhancedObject.addFoliosHeredado(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}
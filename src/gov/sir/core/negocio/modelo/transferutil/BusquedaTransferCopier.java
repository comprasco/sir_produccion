package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class BusquedaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Busqueda transferObject = new gov.sir.core.negocio.modelo.Busqueda();
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
	  			
		  // Direccion
  	    try {
	  	transferObject.setDireccion(enhancedObject.getDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // IdBusqueda
  	    try {
	  	transferObject.setIdBusqueda(enhancedObject.getIdBusqueda());
	  	} catch (Throwable th ) {}
	  			
		  // Apellido1Ciudadano
  	    try {
	  	transferObject.setApellido1Ciudadano(enhancedObject.getApellido1Ciudadano());
	  	} catch (Throwable th ) {}
	  			
		  // Apellido2Ciudadano
  	    try {
	  	transferObject.setApellido2Ciudadano(enhancedObject.getApellido2Ciudadano());
	  	} catch (Throwable th ) {}
	  			
		  // Matricula
  	    try {
	  	transferObject.setMatricula(enhancedObject.getMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // NombreCiudadano
  	    try {
	  	transferObject.setNombreCiudadano(enhancedObject.getNombreCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroCatastral
  	    try {
	  	transferObject.setNumeroCatastral(enhancedObject.getNumeroCatastral());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroDocCiudadano
  	    try {
	  	transferObject.setNumeroDocCiudadano(enhancedObject.getNumeroDocCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroIntento
  	    try {
	  	transferObject.setNumeroIntento(enhancedObject.getNumeroIntento());
	  	} catch (Throwable th ) {}
	  			
		  // TipoDocCiudadano
  	    try {
	  	transferObject.setTipoDocCiudadano(enhancedObject.getTipoDocCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculoBusqueda
  	    try {
	  	transferObject.setIdCirculoBusqueda(enhancedObject.getIdCirculoBusqueda());
	  	} catch (Throwable th ) {}
	  			
		  // Solicitud
  	  gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced solicitudEnh = null;
	  try {
	  	solicitudEnh = enhancedObject.getSolicitud();
	  	} catch (Throwable th) {}
	  	if (solicitudEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.SolicitudConsulta objTo =  (gov.sir.core.negocio.modelo.SolicitudConsulta)cache.get(solicitudEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.SolicitudConsulta)SolicitudConsultaTransferCopier.deepCopy(solicitudEnh, cache);
	  		}
	  		transferObject.setSolicitud(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ResultadosFolio
    	List resultadosFolio = null;
  	try
  	{
  	resultadosFolio = enhancedObject.getResultadosFolios();
  	} catch (Throwable th) {}
	  	if (resultadosFolio != null)
	  	{
		  	for(Iterator itera = resultadosFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced resultadosFolioEnh = (gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.ResultadoFolio objTo = (gov.sir.core.negocio.modelo.ResultadoFolio)cache.get(resultadosFolioEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.ResultadoFolio)ResultadoFolioTransferCopier.deepCopy(resultadosFolioEnh, cache);
		  		}
		  		transferObject.addResultadosFolio(objTo);
		  		}
		  				  	}
		}
				
		  // ValorEje
  	    try {
	  	transferObject.setValorEje(enhancedObject.getValorEje());
	  	} catch (Throwable th ) {}
	  			
		  // IdEje
  	    try {
	  	transferObject.setIdEje(enhancedObject.getIdEje());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoPredio
  	    try {
	  	transferObject.setIdTipoPredio(enhancedObject.getIdTipoPredio());
	  	} catch (Throwable th ) {}
	  			
		  // NombreNaturalezaJuridica
  	    try {
	  	transferObject.setNombreNaturalezaJuridica(enhancedObject.getNombreNaturalezaJuridica());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroResultados
  	    try {
	  	transferObject.setNumeroResultados(enhancedObject.getNumeroResultados());
	  	} catch (Throwable th ) {}
	  			
		  // NombreEje
  	    try {
	  	transferObject.setNombreEje(enhancedObject.getNombreEje());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Busqueda transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.BusquedaEnhanced();
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
	  			
		  // Direccion
  	    try {
	  	enhancedObject.setDireccion(transferObject.getDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // IdBusqueda
  	    try {
	  	enhancedObject.setIdBusqueda(transferObject.getIdBusqueda());
	  	} catch (Throwable th ) {}
	  			
		  // Apellido1Ciudadano
  	    try {
	  	enhancedObject.setApellido1Ciudadano(transferObject.getApellido1Ciudadano());
	  	} catch (Throwable th ) {}
	  			
		  // Apellido2Ciudadano
  	    try {
	  	enhancedObject.setApellido2Ciudadano(transferObject.getApellido2Ciudadano());
	  	} catch (Throwable th ) {}
	  			
		  // Matricula
  	    try {
	  	enhancedObject.setMatricula(transferObject.getMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // NombreCiudadano
  	    try {
	  	enhancedObject.setNombreCiudadano(transferObject.getNombreCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroCatastral
  	    try {
	  	enhancedObject.setNumeroCatastral(transferObject.getNumeroCatastral());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroDocCiudadano
  	    try {
	  	enhancedObject.setNumeroDocCiudadano(transferObject.getNumeroDocCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroIntento
  	    try {
	  	enhancedObject.setNumeroIntento(transferObject.getNumeroIntento());
	  	} catch (Throwable th ) {}
	  			
		  // TipoDocCiudadano
  	    try {
	  	enhancedObject.setTipoDocCiudadano(transferObject.getTipoDocCiudadano());
	  	} catch (Throwable th ) {}
	  			
		  // IdCirculoBusqueda
  	    try {
	  	enhancedObject.setIdCirculoBusqueda(transferObject.getIdCirculoBusqueda());
	  	} catch (Throwable th ) {}
	  			
		  // Solicitud
  	    gov.sir.core.negocio.modelo.SolicitudConsulta solicitud = null;
	  	try {
	  	solicitud = (gov.sir.core.negocio.modelo.SolicitudConsulta)transferObject.getSolicitud();
	  	} catch (Throwable th ) {}
	  	if (solicitud != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced)cache.get(solicitud);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.SolicitudConsultaEnhanced)SolicitudConsultaTransferCopier.deepCopy(solicitud, cache);
	  		}
	  		enhancedObject.setSolicitud(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ResultadosFolio
    	List resultadosFolio = null;
  	try
  	{
  	resultadosFolio = transferObject.getResultadosFolios();
  	} catch (Throwable th) { }
	  	if (resultadosFolio != null)
	  	{
		  	for(Iterator itera = resultadosFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.ResultadoFolio resultadosFolioto = (gov.sir.core.negocio.modelo.ResultadoFolio)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced)cache.get(resultadosFolioto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.ResultadoFolioEnhanced)ResultadoFolioTransferCopier.deepCopy(resultadosFolioto, cache);
		  		}
		  		enhancedObject.addResultadosFolio(objEn);
		  				  		}
		  				  	}
		}
				
		  // ValorEje
  	    try {
	  	enhancedObject.setValorEje(transferObject.getValorEje());
	  	} catch (Throwable th ) {}
	  			
		  // IdEje
  	    try {
	  	enhancedObject.setIdEje(transferObject.getIdEje());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoPredio
  	    try {
	  	enhancedObject.setIdTipoPredio(transferObject.getIdTipoPredio());
	  	} catch (Throwable th ) {}
	  			
		  // NombreNaturalezaJuridica
  	    try {
	  	enhancedObject.setNombreNaturalezaJuridica(transferObject.getNombreNaturalezaJuridica());
	  	} catch (Throwable th ) {}
	  			
		  // NumeroResultados
  	    try {
	  	enhancedObject.setNumeroResultados(transferObject.getNumeroResultados());
	  	} catch (Throwable th ) {}
	  			
		  // NombreEje
  	    try {
	  	enhancedObject.setNombreEje(transferObject.getNombreEje());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
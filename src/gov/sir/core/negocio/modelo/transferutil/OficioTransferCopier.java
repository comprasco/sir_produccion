package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class OficioTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Oficio transferObject = new gov.sir.core.negocio.modelo.Oficio();
		cache.put(enhancedObject, transferObject );
		
		  // Tipo
  	  gov.sir.core.negocio.modelo.jdogenie.TipoOficioEnhanced tipoEnh = null;
	  try {
	  	tipoEnh = enhancedObject.getTipo();
	  	} catch (Throwable th) {}
	  	if (tipoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoOficio objTo =  (gov.sir.core.negocio.modelo.TipoOficio)cache.get(tipoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoOficio)TipoOficioTransferCopier.deepCopy(tipoEnh, cache);
	  		}
	  		transferObject.setTipo(objTo);
	  		}
	  			  		
	  	}
	  			
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // Numero
  	    try {
	  	transferObject.setNumero(enhancedObject.getNumero());
	  	} catch (Throwable th ) {}
	  			
		  // IdOficio
  	    try {
	  	transferObject.setIdOficio(enhancedObject.getIdOficio());
	  	} catch (Throwable th ) {}
	  			
		  // TextoOficio
  	    try {
	  	transferObject.setTextoOficio(enhancedObject.getTextoOficio());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoHistoria
  	  gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced turnoHistoriaEnh = null;
	  try {
	  	turnoHistoriaEnh = enhancedObject.getTurnoHistoria();
	  	} catch (Throwable th) {}
	  	if (turnoHistoriaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TurnoHistoria objTo =  (gov.sir.core.negocio.modelo.TurnoHistoria)cache.get(turnoHistoriaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TurnoHistoria)TurnoHistoriaTransferCopier.deepCopy(turnoHistoriaEnh, cache);
	  		}
	  		transferObject.setTurnoHistoria(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Firmado
    try {
  	transferObject.setFirmado(enhancedObject.isFirmado());
  } catch (Throwable th ) {}
  			
		  // FechaFirma
  		try {
		if (enhancedObject.getFechaFirma() != null)
		{
	  	 transferObject.setFechaFirma(new Date(enhancedObject.getFechaFirma().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Oficio transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.OficioEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Tipo
  	    gov.sir.core.negocio.modelo.TipoOficio tipo = null;
	  	try {
	  	tipo = (gov.sir.core.negocio.modelo.TipoOficio)transferObject.getTipo();
	  	} catch (Throwable th ) {}
	  	if (tipo != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoOficioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoOficioEnhanced)cache.get(tipo);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoOficioEnhanced)TipoOficioTransferCopier.deepCopy(tipo, cache);
	  		}
	  		enhancedObject.setTipo(objEn);
	  		}
	  			  		
	  	}
	  			
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // Numero
  	    try {
	  	enhancedObject.setNumero(transferObject.getNumero());
	  	} catch (Throwable th ) {}
	  			
		  // IdOficio
  	    try {
	  	enhancedObject.setIdOficio(transferObject.getIdOficio());
	  	} catch (Throwable th ) {}
	  			
		  // TextoOficio
  	    try {
	  	enhancedObject.setTextoOficio(transferObject.getTextoOficio());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoHistoria
  	    gov.sir.core.negocio.modelo.TurnoHistoria turnoHistoria = null;
	  	try {
	  	turnoHistoria = (gov.sir.core.negocio.modelo.TurnoHistoria)transferObject.getTurnoHistoria();
	  	} catch (Throwable th ) {}
	  	if (turnoHistoria != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced)cache.get(turnoHistoria);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced)TurnoHistoriaTransferCopier.deepCopy(turnoHistoria, cache);
	  		}
	  		enhancedObject.setTurnoHistoria(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Firmado
      try {
  	enhancedObject.setFirmado(transferObject.isFirmado());
  	} catch (Throwable th ) {}
  			
		  // FechaFirma
  	    try {
		if (transferObject.getFechaFirma() != null)
		{
		  	enhancedObject.setFechaFirma(new Date(transferObject.getFechaFirma().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
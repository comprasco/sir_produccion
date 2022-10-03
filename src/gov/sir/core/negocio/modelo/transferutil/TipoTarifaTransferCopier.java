package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoTarifaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoTarifa transferObject = new gov.sir.core.negocio.modelo.TipoTarifa();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdTipo
  	    try {
	  	transferObject.setIdTipo(enhancedObject.getIdTipo());
	  	} catch (Throwable th ) {}
	  			
		  // Tarifa
    	List tarifa = null;
  	try
  	{
  	tarifa = enhancedObject.getTarifas();
  	} catch (Throwable th) {}
	  	if (tarifa != null)
	  	{
		  	for(Iterator itera = tarifa.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced tarifaEnh = (gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.Tarifa objTo = (gov.sir.core.negocio.modelo.Tarifa)cache.get(tarifaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.Tarifa)TarifaTransferCopier.deepCopy(tarifaEnh, cache);
		  		}
		  		transferObject.addTarifa(objTo);
		  		}
		  				  	}
		}
				
		  // ConfigurablePorCirculo
    try {
  	transferObject.setConfigurablePorCirculo(enhancedObject.isConfigurablePorCirculo());
  } catch (Throwable th ) {}
  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoTarifa transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoTarifaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdTipo
  	    try {
	  	enhancedObject.setIdTipo(transferObject.getIdTipo());
	  	} catch (Throwable th ) {}
	  			
		  // Tarifa
    	List tarifa = null;
  	try
  	{
  	tarifa = transferObject.getTarifas();
  	} catch (Throwable th) { }
	  	if (tarifa != null)
	  	{
		  	for(Iterator itera = tarifa.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.Tarifa tarifato = (gov.sir.core.negocio.modelo.Tarifa)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced)cache.get(tarifato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced)TarifaTransferCopier.deepCopy(tarifato, cache);
		  		}
		  		enhancedObject.addTarifa(objEn);
		  				  		}
		  				  	}
		}
				
		  // ConfigurablePorCirculo
      try {
  	enhancedObject.setConfigurablePorCirculo(transferObject.isConfigurablePorCirculo());
  	} catch (Throwable th ) {}
  			
		
		return enhancedObject;
	}
}
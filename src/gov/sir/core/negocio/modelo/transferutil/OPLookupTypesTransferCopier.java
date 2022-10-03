package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

//
/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class OPLookupTypesTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.OPLookupTypes transferObject = new gov.sir.core.negocio.modelo.OPLookupTypes();
		cache.put(enhancedObject, transferObject );
		
		  // Tipo
  	    try {
	  	transferObject.setTipo(enhancedObject.getTipo());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // OPLookupCode
    	List oPLookupCode = null;
  	try
  	{
  	oPLookupCode = enhancedObject.getOPLookupCodes();
  	} catch (Throwable th) {}
	  	if (oPLookupCode != null)
	  	{
		  	for(Iterator itera = oPLookupCode.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced oPLookupCodeEnh = (gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.OPLookupCodes objTo = (gov.sir.core.negocio.modelo.OPLookupCodes)cache.get(oPLookupCodeEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.OPLookupCodes)OPLookupCodesTransferCopier.deepCopy(oPLookupCodeEnh, cache);
		  		}
		  		transferObject.addOPLookupCode(objTo);
		  		}
		  				  	}
		}
				
		  // Fecha_creacion
  		try {
		if (enhancedObject.getFecha_creacion() != null)
		{
	  	 transferObject.setFecha_creacion(new Date(enhancedObject.getFecha_creacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.OPLookupTypes transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.OPLookupTypesEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Tipo
  	    try {
	  	enhancedObject.setTipo(transferObject.getTipo());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // OPLookupCode
    	List oPLookupCode = null;
  	try
  	{
  	oPLookupCode = transferObject.getOPLookupCodes();
  	} catch (Throwable th) { }
	  	if (oPLookupCode != null)
	  	{
		  	for(Iterator itera = oPLookupCode.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.OPLookupCodes oPLookupCodeto = (gov.sir.core.negocio.modelo.OPLookupCodes)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced)cache.get(oPLookupCodeto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.OPLookupCodesEnhanced)OPLookupCodesTransferCopier.deepCopy(oPLookupCodeto, cache);
		  		}
		  		enhancedObject.addOPLookupCode(objEn);
		  				  		}
		  				  	}
		}
				
		  // Fecha_creacion
  	    try {
		if (transferObject.getFecha_creacion() != null)
		{
		  	enhancedObject.setFecha_creacion(new Date(transferObject.getFecha_creacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
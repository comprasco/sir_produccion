package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SecuenciasTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Secuencias transferObject = new gov.sir.core.negocio.modelo.Secuencias();
		cache.put(enhancedObject, transferObject );
		
		  // TableName
  	    try {
	  	transferObject.setTableName(enhancedObject.getTableName());
	  	} catch (Throwable th ) {}
	  			
		  // LastUsedId
  	    try {
	  	transferObject.setLastUsedId(enhancedObject.getLastUsedId());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Secuencias transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced();
		cache.put(transferObject, enhancedObject);
		  // TableName
  	    try {
	  	enhancedObject.setTableName(transferObject.getTableName());
	  	} catch (Throwable th ) {}
	  			
		  // LastUsedId
  	    try {
	  	enhancedObject.setLastUsedId(transferObject.getLastUsedId());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
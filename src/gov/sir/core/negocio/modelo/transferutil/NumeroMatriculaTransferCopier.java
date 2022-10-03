package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class NumeroMatriculaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.NumeroMatriculaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.NumeroMatricula transferObject = new gov.sir.core.negocio.modelo.NumeroMatricula();
		cache.put(enhancedObject, transferObject );
		
		  // IdNumero
  	    try {
	  	transferObject.setIdNumero(enhancedObject.getIdNumero());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.NumeroMatricula transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.NumeroMatriculaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.NumeroMatriculaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdNumero
  	    try {
	  	enhancedObject.setIdNumero(transferObject.getIdNumero());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
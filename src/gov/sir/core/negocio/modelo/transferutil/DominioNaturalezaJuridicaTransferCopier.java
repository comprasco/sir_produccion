package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class DominioNaturalezaJuridicaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.DominioNaturalezaJuridicaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.DominioNaturalezaJuridica transferObject = new gov.sir.core.negocio.modelo.DominioNaturalezaJuridica();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdDominioNatJur
  	    try {
	  	transferObject.setIdDominioNatJur(enhancedObject.getIdDominioNatJur());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.DominioNaturalezaJuridica transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.DominioNaturalezaJuridicaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DominioNaturalezaJuridicaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdDominioNatJur
  	    try {
	  	enhancedObject.setIdDominioNatJur(transferObject.getIdDominioNatJur());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
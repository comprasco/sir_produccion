package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SucursalBancoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SucursalBanco transferObject = new gov.sir.core.negocio.modelo.SucursalBanco();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdBanco
  	    try {
	  	transferObject.setIdBanco(enhancedObject.getIdBanco());
	  	} catch (Throwable th ) {}
	  			
		  // Banco
  	  gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced bancoEnh = null;
	  try {
	  	bancoEnh = enhancedObject.getBanco();
	  	} catch (Throwable th) {}
	  	if (bancoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Banco objTo =  (gov.sir.core.negocio.modelo.Banco)cache.get(bancoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Banco)BancoTransferCopier.deepCopy(bancoEnh, cache);
	  		}
	  		transferObject.setBanco(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdSucursal
  	    try {
	  	transferObject.setIdSucursal(enhancedObject.getIdSucursal());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SucursalBanco transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SucursalBancoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdBanco
  	    try {
	  	enhancedObject.setIdBanco(transferObject.getIdBanco());
	  	} catch (Throwable th ) {}
	  			
		  // Banco
  	    gov.sir.core.negocio.modelo.Banco banco = null;
	  	try {
	  	banco = (gov.sir.core.negocio.modelo.Banco)transferObject.getBanco();
	  	} catch (Throwable th ) {}
	  	if (banco != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced)cache.get(banco);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced)BancoTransferCopier.deepCopy(banco, cache);
	  		}
	  		enhancedObject.setBanco(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdSucursal
  	    try {
	  	enhancedObject.setIdSucursal(transferObject.getIdSucursal());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
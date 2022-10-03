package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class OficinaCategoriaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.OficinaCategoria transferObject = new gov.sir.core.negocio.modelo.OficinaCategoria();
		cache.put(enhancedObject, transferObject );
		
		  // IdCategoria
  	    try {
	  	transferObject.setIdCategoria(enhancedObject.getIdCategoria());
	  	} catch (Throwable th ) {}
	  			
		  // OficinaOrigen
  	  gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced oficinaOrigenEnh = null;
	  try {
	  	oficinaOrigenEnh = enhancedObject.getOficinaOrigen();
	  	} catch (Throwable th) {}
	  	if (oficinaOrigenEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.OficinaOrigen objTo =  (gov.sir.core.negocio.modelo.OficinaOrigen)cache.get(oficinaOrigenEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.OficinaOrigen)OficinaOrigenTransferCopier.deepCopy(oficinaOrigenEnh, cache);
	  		}
	  		transferObject.setOficinaOrigen(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Categoria
  	  gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced categoriaEnh = null;
	  try {
	  	categoriaEnh = enhancedObject.getCategoria();
	  	} catch (Throwable th) {}
	  	if (categoriaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Categoria objTo =  (gov.sir.core.negocio.modelo.Categoria)cache.get(categoriaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Categoria)CategoriaTransferCopier.deepCopy(categoriaEnh, cache);
	  		}
	  		transferObject.setCategoria(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdOficinaOrigen
  	    try {
	  	transferObject.setIdOficinaOrigen(enhancedObject.getIdOficinaOrigen());
	  	} catch (Throwable th ) {}
	  /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            // version
  	    try {
	  	transferObject.setVersion(enhancedObject.getVersion());
	  	} catch (Throwable th ) {}
		  // PesoReparto
  	    try {
	  	transferObject.setPesoReparto(enhancedObject.getPesoReparto());
	  	} catch (Throwable th ) {}
	  			
		  // OrdenInicial
  	    try {
	  	transferObject.setOrdenInicial(enhancedObject.getOrdenInicial());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.OficinaCategoria transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdCategoria
  	    try {
	  	enhancedObject.setIdCategoria(transferObject.getIdCategoria());
	  	} catch (Throwable th ) {}
	  			
		  // OficinaOrigen
  	    gov.sir.core.negocio.modelo.OficinaOrigen oficinaOrigen = null;
	  	try {
	  	oficinaOrigen = (gov.sir.core.negocio.modelo.OficinaOrigen)transferObject.getOficinaOrigen();
	  	} catch (Throwable th ) {}
	  	if (oficinaOrigen != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced)cache.get(oficinaOrigen);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced)OficinaOrigenTransferCopier.deepCopy(oficinaOrigen, cache);
	  		}
	  		enhancedObject.setOficinaOrigen(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Categoria
  	    gov.sir.core.negocio.modelo.Categoria categoria = null;
	  	try {
	  	categoria = (gov.sir.core.negocio.modelo.Categoria)transferObject.getCategoria();
	  	} catch (Throwable th ) {}
	  	if (categoria != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced)cache.get(categoria);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CategoriaEnhanced)CategoriaTransferCopier.deepCopy(categoria, cache);
	  		}
	  		enhancedObject.setCategoria(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdOficinaOrigen
  	    try {
	  	enhancedObject.setIdOficinaOrigen(transferObject.getIdOficinaOrigen());
	  	} catch (Throwable th ) {}
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            //Version
            try {
	  	enhancedObject.setVersion(transferObject.getVersion());
	  	} catch (Throwable th ) {}
		  // PesoReparto
  	    try {
	  	enhancedObject.setPesoReparto(transferObject.getPesoReparto());
	  	} catch (Throwable th ) {}
	  			
		  // OrdenInicial
  	    try {
	  	enhancedObject.setOrdenInicial(transferObject.getOrdenInicial());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}

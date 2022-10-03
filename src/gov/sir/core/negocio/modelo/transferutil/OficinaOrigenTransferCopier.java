package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class OficinaOrigenTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.OficinaOrigen transferObject = new gov.sir.core.negocio.modelo.OficinaOrigen();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Direccion
  	    try {
	  	transferObject.setDireccion(enhancedObject.getDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // Telefono
  	    try {
	  	transferObject.setTelefono(enhancedObject.getTelefono());
	  	} catch (Throwable th ) {}
	  			
		  // Numero
  	    try {
	  	transferObject.setNumero(enhancedObject.getNumero());
	  	} catch (Throwable th ) {}
	  			
		  // IdOficinaOrigen
  	    try {
	  	transferObject.setIdOficinaOrigen(enhancedObject.getIdOficinaOrigen());
	  	} catch (Throwable th ) {}
	  /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
           //version
            try {
	  	transferObject.setVersion(enhancedObject.getVersion());
	  	} catch (Throwable th ) {}
            /*
            *  @author Fernando Padilla Velez
            *  @chage  1203.AJUSTE.VERSION.CATEGORIA.REPARTO.NOTARIAL,
            */
            try{
                transferObject.setIdUnico(enhancedObject.getIdUnico());
            }catch(Throwable th){}
		  // TipoOficina
  	  gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced tipoOficinaEnh = null;
	  try {
	  	tipoOficinaEnh = enhancedObject.getTipoOficina();
	  	} catch (Throwable th) {}
	  	if (tipoOficinaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.TipoOficina objTo =  (gov.sir.core.negocio.modelo.TipoOficina)cache.get(tipoOficinaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.TipoOficina)TipoOficinaTransferCopier.deepCopy(tipoOficinaEnh, cache);
	  		}
	  		transferObject.setTipoOficina(objTo);
	  		}
	  			  		
	  	}
	  			
		  // NaturalezaJuridicaEntidad
  	    try {
	  	transferObject.setNaturalezaJuridicaEntidad(enhancedObject.getNaturalezaJuridicaEntidad());
	  	} catch (Throwable th ) {}
	  			
		  // Vereda
  	  gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced veredaEnh = null;
	  try {
	  	veredaEnh = enhancedObject.getVereda();
	  	} catch (Throwable th) {}
	  	if (veredaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Vereda objTo =  (gov.sir.core.negocio.modelo.Vereda)cache.get(veredaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Vereda)VeredaTransferCopier.deepCopy(veredaEnh, cache);
	  		}
	  		transferObject.setVereda(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Categoria
    	List categoria = null;
  	try
  	{
  	categoria = enhancedObject.getCategorias();
  	} catch (Throwable th) {}
	  	if (categoria != null)
	  	{
		  	for(Iterator itera = categoria.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced categoriaEnh = (gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.OficinaCategoria objTo = (gov.sir.core.negocio.modelo.OficinaCategoria)cache.get(categoriaEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.OficinaCategoria)OficinaCategoriaTransferCopier.deepCopy(categoriaEnh, cache);
		  		}
		  		transferObject.addCategoria(objTo);
		  		}
		  				  	}
		}
				
		  // ExentoDerechoNotarial
    try {
  	transferObject.setExentoDerechoNotarial(enhancedObject.isExentoDerechoNotarial());
  } catch (Throwable th ) {}
  			
		  // Email
  	    try {
	  	transferObject.setEmail(enhancedObject.getEmail());
	  	} catch (Throwable th ) {}
	  			
		  // Encargado
  	    try {
	  	transferObject.setEncargado(enhancedObject.getEncargado());
	  	} catch (Throwable th ) {}
	  			
		  // Fax
  	    try {
	  	transferObject.setFax(enhancedObject.getFax());
	  	} catch (Throwable th ) {}
	  			
		  // CategoriaNotaria
  	  gov.sir.core.negocio.modelo.jdogenie.CategoriaNotariaEnhanced categoriaNotariaEnh = null;
	  try {
	  	categoriaNotariaEnh = enhancedObject.getCategoriaNotaria();
	  	} catch (Throwable th) {}
	  	if (categoriaNotariaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.CategoriaNotaria objTo =  (gov.sir.core.negocio.modelo.CategoriaNotaria)cache.get(categoriaNotariaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.CategoriaNotaria)CategoriaNotariaTransferCopier.deepCopy(categoriaNotariaEnh, cache);
	  		}
	  		transferObject.setCategoriaNotaria(objTo);
	  		}
	  			  		
	  	}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.OficinaOrigen transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // Direccion
  	    try {
	  	enhancedObject.setDireccion(transferObject.getDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // Telefono
  	    try {
	  	enhancedObject.setTelefono(transferObject.getTelefono());
	  	} catch (Throwable th ) {}
	  			
		  // Numero
  	    try {
	  	enhancedObject.setNumero(transferObject.getNumero());
	  	} catch (Throwable th ) {}
	  			
		  // IdOficinaOrigen
  	    try {
	  	enhancedObject.setIdOficinaOrigen(transferObject.getIdOficinaOrigen());
	  	} catch (Throwable th ) {}
	   /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
           //version
            try {
	  	enhancedObject.setVersion(transferObject.getVersion());
	  	} catch (Throwable th ) {}
            
            /*
            *  @author Fernando Padilla Velez
            *  @chage  1203.AJUSTE.VERSION.CATEGORIA.REPARTO.NOTARIAL,
            */
            try{
                enhancedObject.setIdUnico(transferObject.getIdUnico());
            }catch(Throwable th){}
		  // TipoOficina
  	    gov.sir.core.negocio.modelo.TipoOficina tipoOficina = null;
	  	try {
	  	tipoOficina = (gov.sir.core.negocio.modelo.TipoOficina)transferObject.getTipoOficina();
	  	} catch (Throwable th ) {}
	  	if (tipoOficina != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced)cache.get(tipoOficina);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoOficinaEnhanced)TipoOficinaTransferCopier.deepCopy(tipoOficina, cache);
	  		}
	  		enhancedObject.setTipoOficina(objEn);
	  		}
	  			  		
	  	}
	  			
		  // NaturalezaJuridicaEntidad
  	    try {
	  	enhancedObject.setNaturalezaJuridicaEntidad(transferObject.getNaturalezaJuridicaEntidad());
	  	} catch (Throwable th ) {}
	  			
		  // Vereda
  	    gov.sir.core.negocio.modelo.Vereda vereda = null;
	  	try {
	  	vereda = (gov.sir.core.negocio.modelo.Vereda)transferObject.getVereda();
	  	} catch (Throwable th ) {}
	  	if (vereda != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced)cache.get(vereda);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced)VeredaTransferCopier.deepCopy(vereda, cache);
	  		}
	  		enhancedObject.setVereda(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Categoria
    	List categoria = null;
  	try
  	{
  	categoria = transferObject.getCategorias();
  	} catch (Throwable th) { }
	  	if (categoria != null)
	  	{
		  	for(Iterator itera = categoria.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.OficinaCategoria categoriato = (gov.sir.core.negocio.modelo.OficinaCategoria)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced)cache.get(categoriato);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced)OficinaCategoriaTransferCopier.deepCopy(categoriato, cache);
		  		}
		  		enhancedObject.addCategoria(objEn);
		  				  		}
		  				  	}
		}
				
		  // ExentoDerechoNotarial
      try {
  	enhancedObject.setExentoDerechoNotarial(transferObject.isExentoDerechoNotarial());
  	} catch (Throwable th ) {}
  			
		  // Email
  	    try {
	  	enhancedObject.setEmail(transferObject.getEmail());
	  	} catch (Throwable th ) {}
	  			
		  // Encargado
  	    try {
	  	enhancedObject.setEncargado(transferObject.getEncargado());
	  	} catch (Throwable th ) {}
	  			
		  // Fax
  	    try {
	  	enhancedObject.setFax(transferObject.getFax());
	  	} catch (Throwable th ) {}
	  			
		  // CategoriaNotaria
  	    gov.sir.core.negocio.modelo.CategoriaNotaria categoriaNotaria = null;
	  	try {
	  	categoriaNotaria = (gov.sir.core.negocio.modelo.CategoriaNotaria)transferObject.getCategoriaNotaria();
	  	} catch (Throwable th ) {}
	  	if (categoriaNotaria != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.CategoriaNotariaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CategoriaNotariaEnhanced)cache.get(categoriaNotaria);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.CategoriaNotariaEnhanced)CategoriaNotariaTransferCopier.deepCopy(categoriaNotaria, cache);
	  		}
	  		enhancedObject.setCategoriaNotaria(objEn);
	  		}
	  			  		
	  	}
	  			
		
		return enhancedObject;
	}
}

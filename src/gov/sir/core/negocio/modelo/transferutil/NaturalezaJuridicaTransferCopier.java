package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class NaturalezaJuridicaTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.NaturalezaJuridica transferObject = new gov.sir.core.negocio.modelo.NaturalezaJuridica();
		cache.put(enhancedObject, transferObject );
		
		  // Nombre
  	    try {
	  	transferObject.setNombre(enhancedObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdNaturalezaJuridica
  	    try {
	  	transferObject.setIdNaturalezaJuridica(enhancedObject.getIdNaturalezaJuridica());
	  	} catch (Throwable th ) {}
	  			
		  // GrupoNaturalezaJuridica
  	  gov.sir.core.negocio.modelo.jdogenie.GrupoNaturalezaJuridicaEnhanced grupoNaturalezaJuridicaEnh = null;
	  try {
	  	grupoNaturalezaJuridicaEnh = enhancedObject.getGrupoNaturalezaJuridica();
	  	} catch (Throwable th) {}
	  	if (grupoNaturalezaJuridicaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica objTo =  (gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica)cache.get(grupoNaturalezaJuridicaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica)GrupoNaturalezaJuridicaTransferCopier.deepCopy(grupoNaturalezaJuridicaEnh, cache);
	  		}
	  		transferObject.setGrupoNaturalezaJuridica(objTo);
	  		}
	  			  		
	  	}
	  			
		  // DominioNaturalezaJuridica
  	  gov.sir.core.negocio.modelo.jdogenie.DominioNaturalezaJuridicaEnhanced dominioNaturalezaJuridicaEnh = null;
	  try {
	  	dominioNaturalezaJuridicaEnh = enhancedObject.getDominioNaturalezaJuridica();
	  	} catch (Throwable th) {}
	  	if (dominioNaturalezaJuridicaEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.DominioNaturalezaJuridica objTo =  (gov.sir.core.negocio.modelo.DominioNaturalezaJuridica)cache.get(dominioNaturalezaJuridicaEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.DominioNaturalezaJuridica)DominioNaturalezaJuridicaTransferCopier.deepCopy(dominioNaturalezaJuridicaEnh, cache);
	  		}
	  		transferObject.setDominioNaturalezaJuridica(objTo);
	  		}
	  			  		
	  	}
	  			
		  // HabilitadoCalificacion
    try {
  	transferObject.setHabilitadoCalificacion(enhancedObject.isHabilitadoCalificacion());
  } catch (Throwable th ) {}
    /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
  	  // Version
  	    try {
	  	transferObject.setVersion(enhancedObject.getVersion());
	  	} catch (Throwable th ) {}
            
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.NaturalezaJuridica transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Nombre
  	    try {
	  	enhancedObject.setNombre(transferObject.getNombre());
	  	} catch (Throwable th ) {}
	  			
		  // IdNaturalezaJuridica
  	    try {
	  	enhancedObject.setIdNaturalezaJuridica(transferObject.getIdNaturalezaJuridica());
	  	} catch (Throwable th ) {}
	  			
		  // GrupoNaturalezaJuridica
  	    gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica grupoNaturalezaJuridica = null;
	  	try {
	  	grupoNaturalezaJuridica = (gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica)transferObject.getGrupoNaturalezaJuridica();
	  	} catch (Throwable th ) {}
	  	if (grupoNaturalezaJuridica != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.GrupoNaturalezaJuridicaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.GrupoNaturalezaJuridicaEnhanced)cache.get(grupoNaturalezaJuridica);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.GrupoNaturalezaJuridicaEnhanced)GrupoNaturalezaJuridicaTransferCopier.deepCopy(grupoNaturalezaJuridica, cache);
	  		}
	  		enhancedObject.setGrupoNaturalezaJuridica(objEn);
	  		}
	  			  		
	  	}
	  			
		  // DominioNaturalezaJuridica
  	    gov.sir.core.negocio.modelo.DominioNaturalezaJuridica dominioNaturalezaJuridica = null;
	  	try {
	  	dominioNaturalezaJuridica = (gov.sir.core.negocio.modelo.DominioNaturalezaJuridica)transferObject.getDominioNaturalezaJuridica();
	  	} catch (Throwable th ) {}
	  	if (dominioNaturalezaJuridica != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.DominioNaturalezaJuridicaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DominioNaturalezaJuridicaEnhanced)cache.get(dominioNaturalezaJuridica);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.DominioNaturalezaJuridicaEnhanced)DominioNaturalezaJuridicaTransferCopier.deepCopy(dominioNaturalezaJuridica, cache);
	  		}
	  		enhancedObject.setDominioNaturalezaJuridica(objEn);
	  		}
	  			  		
	  	}
	  			
		  // HabilitadoCalificacion
      try {
  	enhancedObject.setHabilitadoCalificacion(transferObject.isHabilitadoCalificacion());
  	} catch (Throwable th ) {}
  	   
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
            	  // Version
  	    try {
	  	enhancedObject.setVersion(transferObject.getVersion());
	     } catch (Throwable th ) {}		
		
		return enhancedObject;
	}
}

package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SalvedadAnotacionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SalvedadAnotacion transferObject = new gov.sir.core.negocio.modelo.SalvedadAnotacion();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion
  	    try {
	  	transferObject.setIdAnotacion(enhancedObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // NumRadicacion
  	    try {
	  	transferObject.setNumRadicacion(enhancedObject.getNumRadicacion());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioCreacion
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioCreacionEnh = null;
	  try {
	  	usuarioCreacionEnh = enhancedObject.getUsuarioCreacion();
	  	} catch (Throwable th) {}
	  	if (usuarioCreacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioCreacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioCreacionEnh, cache);
	  		}
	  		transferObject.setUsuarioCreacion(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ToDelete
    try {
  	transferObject.setToDelete(enhancedObject.isToDelete());
  } catch (Throwable th ) {}
  			
		  // UsuarioCreacionTMP
  	  gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced usuarioCreacionTMPEnh = null;
	  try {
	  	usuarioCreacionTMPEnh = enhancedObject.getUsuarioCreacionTMP();
	  	} catch (Throwable th) {}
	  	if (usuarioCreacionTMPEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Usuario objTo =  (gov.sir.core.negocio.modelo.Usuario)cache.get(usuarioCreacionTMPEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Usuario)UsuarioTransferCopier.deepCopy(usuarioCreacionTMPEnh, cache);
	  		}
	  		transferObject.setUsuarioCreacionTMP(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Anotacion
  	  gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced anotacionEnh = null;
	  try {
	  	anotacionEnh = enhancedObject.getAnotacion();
	  	} catch (Throwable th) {}
	  	if (anotacionEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Anotacion objTo =  (gov.sir.core.negocio.modelo.Anotacion)cache.get(anotacionEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Anotacion)AnotacionTransferCopier.deepCopy(anotacionEnh, cache);
	  		}
	  		transferObject.setAnotacion(objTo);
	  		}
	  			  		
	  	}
	  			
		  // IdSalvedad
  	    try {
	  	transferObject.setIdSalvedad(enhancedObject.getIdSalvedad());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoSalvedadAnotacion
    	List turnoSalvedadAnotacion = null;
  	try
  	{
  	turnoSalvedadAnotacion = enhancedObject.getTurnoSalvedadAnotacions();
  	} catch (Throwable th) {}
	  	if (turnoSalvedadAnotacion != null)
	  	{
		  	for(Iterator itera = turnoSalvedadAnotacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced turnoSalvedadAnotacionEnh = (gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion objTo = (gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion)cache.get(turnoSalvedadAnotacionEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion)TurnoSalvedadAnotacionTransferCopier.deepCopy(turnoSalvedadAnotacionEnh, cache);
		  		}
		  		transferObject.addTurnoSalvedadAnotacion(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SalvedadAnotacion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdAnotacion
  	    try {
	  	enhancedObject.setIdAnotacion(transferObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // NumRadicacion
  	    try {
	  	enhancedObject.setNumRadicacion(transferObject.getNumRadicacion());
	  	} catch (Throwable th ) {}
	  			
		  // UsuarioCreacion
  	    gov.sir.core.negocio.modelo.Usuario usuarioCreacion = null;
	  	try {
	  	usuarioCreacion = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuarioCreacion();
	  	} catch (Throwable th ) {}
	  	if (usuarioCreacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuarioCreacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuarioCreacion, cache);
	  		}
	  		enhancedObject.setUsuarioCreacion(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ToDelete
      try {
  	enhancedObject.setToDelete(transferObject.isToDelete());
  	} catch (Throwable th ) {}
  			
		  // UsuarioCreacionTMP
  	    gov.sir.core.negocio.modelo.Usuario usuarioCreacionTMP = null;
	  	try {
	  	usuarioCreacionTMP = (gov.sir.core.negocio.modelo.Usuario)transferObject.getUsuarioCreacionTMP();
	  	} catch (Throwable th ) {}
	  	if (usuarioCreacionTMP != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)cache.get(usuarioCreacionTMP);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced)UsuarioTransferCopier.deepCopy(usuarioCreacionTMP, cache);
	  		}
	  		enhancedObject.setUsuarioCreacionTMP(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // Anotacion
  	    gov.sir.core.negocio.modelo.Anotacion anotacion = null;
	  	try {
	  	anotacion = (gov.sir.core.negocio.modelo.Anotacion)transferObject.getAnotacion();
	  	} catch (Throwable th ) {}
	  	if (anotacion != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)cache.get(anotacion);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)AnotacionTransferCopier.deepCopy(anotacion, cache);
	  		}
	  		enhancedObject.setAnotacion(objEn);
	  		}
	  			  		
	  	}
	  			
		  // IdSalvedad
  	    try {
	  	enhancedObject.setIdSalvedad(transferObject.getIdSalvedad());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoSalvedadAnotacion
    	List turnoSalvedadAnotacion = null;
  	try
  	{
  	turnoSalvedadAnotacion = transferObject.getTurnoSalvedadAnotacions();
  	} catch (Throwable th) { }
	  	if (turnoSalvedadAnotacion != null)
	  	{
		  	for(Iterator itera = turnoSalvedadAnotacion.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion turnoSalvedadAnotacionto = (gov.sir.core.negocio.modelo.TurnoSalvedadAnotacion)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced)cache.get(turnoSalvedadAnotacionto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced)TurnoSalvedadAnotacionTransferCopier.deepCopy(turnoSalvedadAnotacionto, cache);
		  		}
		  		enhancedObject.addTurnoSalvedadAnotacion(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}
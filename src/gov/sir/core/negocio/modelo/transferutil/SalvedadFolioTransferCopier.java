package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class SalvedadFolioTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.SalvedadFolio transferObject = new gov.sir.core.negocio.modelo.SalvedadFolio();
		cache.put(enhancedObject, transferObject );
		
		  // FechaCreacion
  		try {
		if (enhancedObject.getFechaCreacion() != null)
		{
	  	 transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
	  	}
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Folio
  	  gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced folioEnh = null;
	  try {
	  	folioEnh = enhancedObject.getFolio();
	  	} catch (Throwable th) {}
	  	if (folioEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Folio objTo =  (gov.sir.core.negocio.modelo.Folio)cache.get(folioEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Folio)FolioTransferCopier.deepCopy(folioEnh, cache);
	  		}
	  		transferObject.setFolio(objTo);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // IdSalvedad
  	    try {
	  	transferObject.setIdSalvedad(enhancedObject.getIdSalvedad());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoSalvedadFolio
    	List turnoSalvedadFolio = null;
  	try
  	{
  	turnoSalvedadFolio = enhancedObject.getTurnoSalvedadFolios();
  	} catch (Throwable th) {}
	  	if (turnoSalvedadFolio != null)
	  	{
		  	for(Iterator itera = turnoSalvedadFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced turnoSalvedadFolioEnh = (gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced)itera.next();
		  				  				  		if (!assigned)
		  		{
		  		gov.sir.core.negocio.modelo.TurnoSalvedadFolio objTo = (gov.sir.core.negocio.modelo.TurnoSalvedadFolio)cache.get(turnoSalvedadFolioEnh);
		  		if (objTo == null)
		  		{
		  		objTo = (gov.sir.core.negocio.modelo.TurnoSalvedadFolio)TurnoSalvedadFolioTransferCopier.deepCopy(turnoSalvedadFolioEnh, cache);
		  		}
		  		transferObject.addTurnoSalvedadFolio(objTo);
		  		}
		  				  	}
		}
				
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.SalvedadFolio transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced();
		cache.put(transferObject, enhancedObject);
		  // FechaCreacion
  	    try {
		if (transferObject.getFechaCreacion() != null)
		{
		  	enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
		}
		} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Folio
  	    gov.sir.core.negocio.modelo.Folio folio = null;
	  	try {
	  	folio = (gov.sir.core.negocio.modelo.Folio)transferObject.getFolio();
	  	} catch (Throwable th ) {}
	  	if (folio != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)cache.get(folio);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced)FolioTransferCopier.deepCopy(folio, cache);
	  		}
	  		enhancedObject.setFolio(objEn);
	  		}
	  			  		
	  	}
	  			
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
	  			
		  // IdSalvedad
  	    try {
	  	enhancedObject.setIdSalvedad(transferObject.getIdSalvedad());
	  	} catch (Throwable th ) {}
	  			
		  // TurnoSalvedadFolio
    	List turnoSalvedadFolio = null;
  	try
  	{
  	turnoSalvedadFolio = transferObject.getTurnoSalvedadFolios();
  	} catch (Throwable th) { }
	  	if (turnoSalvedadFolio != null)
	  	{
		  	for(Iterator itera = turnoSalvedadFolio.iterator();
		  			itera.hasNext();)
		  	{
		  		boolean assigned = false;
		  		gov.sir.core.negocio.modelo.TurnoSalvedadFolio turnoSalvedadFolioto = (gov.sir.core.negocio.modelo.TurnoSalvedadFolio)itera.next();
		  				  				  		if (!assigned)
		  		{
		  				  		gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced)cache.get(turnoSalvedadFolioto);
		  		if (objEn == null)
		  		{
		  			objEn = (gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced)TurnoSalvedadFolioTransferCopier.deepCopy(turnoSalvedadFolioto, cache);
		  		}
		  		enhancedObject.addTurnoSalvedadFolio(objEn);
		  				  		}
		  				  	}
		}
				
		
		return enhancedObject;
	}
}
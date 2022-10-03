package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class DireccionTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.Direccion transferObject = new gov.sir.core.negocio.modelo.Direccion();
		cache.put(enhancedObject, transferObject );
		
		  // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
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
	  			
		  // Especificacion
  	    try {
	  	transferObject.setEspecificacion(enhancedObject.getEspecificacion());
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
	  			
		  // ValorEje
  	    try {
	  	transferObject.setValorEje(enhancedObject.getValorEje());
	  	} catch (Throwable th ) {}
	  			
		  // IdDireccion
  	    try {
	  	transferObject.setIdDireccion(enhancedObject.getIdDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // Eje
  	  gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced ejeEnh = null;
	  try {
	  	ejeEnh = enhancedObject.getEje();
	  	} catch (Throwable th) {}
	  	if (ejeEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Eje objTo =  (gov.sir.core.negocio.modelo.Eje)cache.get(ejeEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Eje)EjeTransferCopier.deepCopy(ejeEnh, cache);
	  		}
	  		transferObject.setEje(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Eje1
  	  gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced eje1Enh = null;
	  try {
	  	eje1Enh = enhancedObject.getEje1();
	  	} catch (Throwable th) {}
	  	if (eje1Enh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Eje objTo =  (gov.sir.core.negocio.modelo.Eje)cache.get(eje1Enh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Eje)EjeTransferCopier.deepCopy(eje1Enh, cache);
	  		}
	  		transferObject.setEje1(objTo);
	  		}
	  			  		
	  	}
	  			
		  // ValorEje1
  	    try {
	  	transferObject.setValorEje1(enhancedObject.getValorEje1());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.Direccion transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced();
		cache.put(transferObject, enhancedObject);
		  // Circulo
  	    try {
	  	enhancedObject.setCirculo(transferObject.getCirculo());
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
	  			
		  // Especificacion
  	    try {
	  	enhancedObject.setEspecificacion(transferObject.getEspecificacion());
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
	  			
		  // ValorEje
  	    try {
	  	enhancedObject.setValorEje(transferObject.getValorEje());
	  	} catch (Throwable th ) {}
	  			
		  // IdDireccion
  	    try {
	  	enhancedObject.setIdDireccion(transferObject.getIdDireccion());
	  	} catch (Throwable th ) {}
	  			
		  // Eje
  	    gov.sir.core.negocio.modelo.Eje eje = null;
	  	try {
	  	eje = (gov.sir.core.negocio.modelo.Eje)transferObject.getEje();
	  	} catch (Throwable th ) {}
	  	if (eje != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced)cache.get(eje);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced)EjeTransferCopier.deepCopy(eje, cache);
	  		}
	  		enhancedObject.setEje(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Eje1
  	    gov.sir.core.negocio.modelo.Eje eje1 = null;
	  	try {
	  	eje1 = (gov.sir.core.negocio.modelo.Eje)transferObject.getEje1();
	  	} catch (Throwable th ) {}
	  	if (eje1 != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced)cache.get(eje1);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.EjeEnhanced)EjeTransferCopier.deepCopy(eje1, cache);
	  		}
	  		enhancedObject.setEje1(objEn);
	  		}
	  			  		
	  	}
	  			
		  // ValorEje1
  	    try {
	  	enhancedObject.setValorEje1(transferObject.getValorEje1());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
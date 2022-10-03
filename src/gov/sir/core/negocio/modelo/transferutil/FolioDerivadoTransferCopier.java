package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class FolioDerivadoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.FolioDerivado transferObject = new gov.sir.core.negocio.modelo.FolioDerivado();
		cache.put(enhancedObject, transferObject );
		
		  // IdAnotacion
  	    try {
	  	transferObject.setIdAnotacion(enhancedObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	transferObject.setIdMatricula(enhancedObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	transferObject.setDescripcion(enhancedObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula1
  	    try {
	  	transferObject.setIdMatricula1(enhancedObject.getIdMatricula1());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion1
  	    try {
	  	transferObject.setIdAnotacion1(enhancedObject.getIdAnotacion1());
	  	} catch (Throwable th ) {}
	  			
		  // Porcentaje
  	    try {
	  	transferObject.setPorcentaje(enhancedObject.getPorcentaje());
	  	} catch (Throwable th ) {}
	  			
		  // Hijo
  	  gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced hijoEnh = null;
	  try {
	  	hijoEnh = enhancedObject.getHijo();
	  	} catch (Throwable th) {}
	  	if (hijoEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Anotacion objTo =  (gov.sir.core.negocio.modelo.Anotacion)cache.get(hijoEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Anotacion)AnotacionTransferCopier.deepCopy(hijoEnh, cache);
	  		}
	  		transferObject.setHijo(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Padre
  	  gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced padreEnh = null;
	  try {
	  	padreEnh = enhancedObject.getPadre();
	  	} catch (Throwable th) {}
	  	if (padreEnh != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.Anotacion objTo =  (gov.sir.core.negocio.modelo.Anotacion)cache.get(padreEnh);
	  		if (objTo == null)
	  		{
	  			objTo = (gov.sir.core.negocio.modelo.Anotacion)AnotacionTransferCopier.deepCopy(padreEnh, cache);
	  		}
	  		transferObject.setPadre(objTo);
	  		}
	  			  		
	  	}
	  			
		  // Area
  	    try {
	  	transferObject.setArea(enhancedObject.getArea());
	  	} catch (Throwable th ) {}
                
                // Hectareas
            try {
	  	transferObject.setHectareas(enhancedObject.getHectareas());
	  	} catch (Throwable th ) {}
            
                // Metros
            try {
	  	transferObject.setMetros(enhancedObject.getMetros());
	  	} catch (Throwable th ) {}
            
                // Centimetros
            try {
	  	transferObject.setCentimetros(enhancedObject.getCentimetros());
	  	} catch (Throwable th ) {}
            
                // PrivMetros
            try {
	  	transferObject.setPrivMetros(enhancedObject.getPrivMetros());
	  	} catch (Throwable th ) {}
            
                // PrivCentimetros
            try {
	  	transferObject.setPrivCentimetros(enhancedObject.getPrivCentimetros());
	  	} catch (Throwable th ) {}
            
                // ConsMetros
            try {
	  	transferObject.setConsMetros(enhancedObject.getConsMetros());
	  	} catch (Throwable th ) {}
            
                // ConsCentimetros
            try {
	  	transferObject.setConsCentimetros(enhancedObject.getConsCentimetros());
	  	} catch (Throwable th ) {}
            
                // DeterminaInm
            try {
	  	transferObject.setDeterminaInm(enhancedObject.getDeterminaInm());
	  	} catch (Throwable th ) {}
	  			
		  // Lote
  	    try {
	  	transferObject.setLote(enhancedObject.getLote());
	  	} catch (Throwable th ) {}
	  			
				return transferObject;
}
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.FolioDerivado transferObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced();
		cache.put(transferObject, enhancedObject);
		  // IdAnotacion
  	    try {
	  	enhancedObject.setIdAnotacion(transferObject.getIdAnotacion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula
  	    try {
	  	enhancedObject.setIdMatricula(transferObject.getIdMatricula());
	  	} catch (Throwable th ) {}
	  			
		  // Descripcion
  	    try {
	  	enhancedObject.setDescripcion(transferObject.getDescripcion());
	  	} catch (Throwable th ) {}
	  			
		  // IdMatricula1
  	    try {
	  	enhancedObject.setIdMatricula1(transferObject.getIdMatricula1());
	  	} catch (Throwable th ) {}
	  			
		  // IdAnotacion1
  	    try {
	  	enhancedObject.setIdAnotacion1(transferObject.getIdAnotacion1());
	  	} catch (Throwable th ) {}
	  			
		  // Porcentaje
  	    try {
	  	enhancedObject.setPorcentaje(transferObject.getPorcentaje());
	  	} catch (Throwable th ) {}
	  			
		  // Hijo
  	    gov.sir.core.negocio.modelo.Anotacion hijo = null;
	  	try {
	  	hijo = (gov.sir.core.negocio.modelo.Anotacion)transferObject.getHijo();
	  	} catch (Throwable th ) {}
	  	if (hijo != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)cache.get(hijo);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)AnotacionTransferCopier.deepCopy(hijo, cache);
	  		}
	  		enhancedObject.setHijo(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Padre
  	    gov.sir.core.negocio.modelo.Anotacion padre = null;
	  	try {
	  	padre = (gov.sir.core.negocio.modelo.Anotacion)transferObject.getPadre();
	  	} catch (Throwable th ) {}
	  	if (padre != null)
	  	{
	  		boolean assigned = false;
	  			  			  		if (!assigned)
	  		{
	  		gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)cache.get(padre);
	  		if (objEn == null)
	  		{
	  			objEn = (gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced)AnotacionTransferCopier.deepCopy(padre, cache);
	  		}
	  		enhancedObject.setPadre(objEn);
	  		}
	  			  		
	  	}
	  			
		  // Area
  	    try {
	  	enhancedObject.setArea(transferObject.getArea());
	  	} catch (Throwable th ) {}
                
                // Hectareas
            try {
	  	enhancedObject.setHectareas(transferObject.getHectareas());
	  	} catch (Throwable th ) {}
                
                // Metros
            try {
	  	enhancedObject.setMetros(transferObject.getMetros());
	  	} catch (Throwable th ) {}
            
                // Centimetros
            try {
	  	enhancedObject.setCentimetros(transferObject.getCentimetros());
	  	} catch (Throwable th ) {}
            
                // PrivMetros
            try {
	  	enhancedObject.setPrivMetros(transferObject.getPrivMetros());
	  	} catch (Throwable th ) {}
                
                // PrivCentimetros
            try {
	  	enhancedObject.setPrivCentimetros(transferObject.getPrivCentimetros());
	  	} catch (Throwable th ) {}
            
                // ConsMetros
            try {
	  	enhancedObject.setConsMetros(transferObject.getConsMetros());
	  	} catch (Throwable th ) {}
            
                // ConsCentimetros
            try {
	  	enhancedObject.setConsCentimetros(transferObject.getConsCentimetros());
	  	} catch (Throwable th ) {}
            
                // DeterminaInm
            try {
	  	enhancedObject.setDeterminaInm(transferObject.getDeterminaInm());
	  	} catch (Throwable th ) {}
	  			
		  // Lote
  	    try {
	  	enhancedObject.setLote(transferObject.getLote());
	  	} catch (Throwable th ) {}
	  			
		
		return enhancedObject;
	}
}
package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoImpuestoCirculoTransferCopier {

	public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoCirculoEnhanced enhancedObject, java.util.HashMap cache)
	{
		gov.sir.core.negocio.modelo.TipoImpuestoCirculo transferObject = new gov.sir.core.negocio.modelo.TipoImpuestoCirculo();
		cache.put(enhancedObject, transferObject );
		
  	    try {
	  	transferObject.setIdTipoImpuestoCirculo(enhancedObject.getIdTipoImpuestoCirculo());
	  	} catch (Throwable th ) {}
	  			
		  // IdTipoImpuesto
  	    try {
	  	transferObject.setIdTipoImpuesto(enhancedObject.getIdTipoImpuesto());
	  	} catch (Throwable th ) {}

  	    try {
	  	transferObject.setIdCirculo(enhancedObject.getIdCirculo());
	  	} catch (Throwable th ) {}

  	    try {
	  	transferObject.setFechaInicioVigencia(enhancedObject.getFechaInicioVigencia());
	  	} catch (Throwable th ) {}

  	    try {
	  	transferObject.setFechaFinVigencia(enhancedObject.getFechaFinVigencia());
	  	} catch (Throwable th ) {}

  	    try {
                    transferObject.setSoporte(enhancedObject.getSoporte());
	  	} catch (Throwable th ) {}
            return transferObject;
        }
	
	public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.TipoImpuestoCirculo transferObject, java.util.HashMap cache)
        {
            gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoCirculoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoImpuestoCirculoEnhanced();
            cache.put(transferObject, enhancedObject);

            try {
                enhancedObject.setIdTipoImpuestoCirculo(transferObject.getIdTipoImpuestoCirculo());
            } catch (Throwable th ) {}

            try {
                enhancedObject.setIdTipoImpuesto(transferObject.getIdTipoImpuesto());
            } catch (Throwable th ) {}

            try {
                enhancedObject.setIdCirculo(transferObject.getIdCirculo());
            } catch (Throwable th ) {}

            try {
                enhancedObject.setFechaInicioVigencia(transferObject.getFechaInicioVigencia());
            } catch (Throwable th ) {}

            try {
                enhancedObject.setFechaFinVigencia(transferObject.getFechaFinVigencia());
            } catch (Throwable th ) {}
            
            try {
                enhancedObject.setSoporte(transferObject.getSoporte());
            } catch (Throwable th ) {}

            return enhancedObject;
	}
}
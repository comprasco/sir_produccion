package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class AccionNotarialTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced enhancedObject, java.util.HashMap cache)
    {
        gov.sir.core.negocio.modelo.AccionNotarial transferObject = new gov.sir.core.negocio.modelo.AccionNotarial();
        cache.put(enhancedObject, transferObject );

        // IdAccionNotarial
        try {
            transferObject.setIdAccionNotarial(enhancedObject.getIdAccionNotarial());
        } catch (Throwable th ) {}

        // Cuantia
        try {
            transferObject.setCuantia(enhancedObject.isCuantia());
        } catch (Throwable th ) {}
  			
        // Nombre
        try {
            transferObject.setNombre(enhancedObject.getNombre());
        } catch (Throwable th ) {}

        // Tipo
        try {
            transferObject.setTipo(enhancedObject.getTipo());
        } catch (Throwable th ) {}
	// Activo
        try {
            transferObject.setActivo(enhancedObject.isActivo());
        } catch (Throwable th ) {}  			
        return transferObject;
}
	
    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.AccionNotarial transferObject, java.util.HashMap cache)
    {
        gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.AccionNotarialEnhanced();
        cache.put(transferObject, enhancedObject);
        // IdAccionNotarial
        try {
            enhancedObject.setIdAccionNotarial(transferObject.getIdAccionNotarial());
        } catch (Throwable th ) {}

        // Cuantia
        try {
          enhancedObject.setCuantia(transferObject.isCuantia());
        } catch (Throwable th ) {}

        // Nombre
        try {
            enhancedObject.setNombre(transferObject.getNombre());
        } catch (Throwable th ) {}

        // Tipo
        try {
            enhancedObject.setTipo(transferObject.getTipo());
        } catch (Throwable th ) {}

        // Activo
        try {
            transferObject.setActivo(enhancedObject.isActivo());
        } catch (Throwable th ) {}  
        
        return enhancedObject;
    }
}
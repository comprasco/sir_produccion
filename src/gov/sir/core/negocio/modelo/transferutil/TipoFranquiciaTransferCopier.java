/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Tipo Franquicia del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo.transferutil;

public class TipoFranquiciaTransferCopier {
    
    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.TipoFranquiciaEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.TipoFranquicia transferObject = new gov.sir.core.negocio.modelo.TipoFranquicia();
        cache.put(enhancedObject, transferObject);

        // IdTipoFranquicia
        try {
            transferObject.setIdTipoFranquicia(enhancedObject.getIdTipoFranquicia());
        } catch (Throwable th) {
        }
        
        //Nombre
        try{
            transferObject.setNombreFranquicia(enhancedObject.getNombreFranquicia());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.TipoFranquicia transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.TipoFranquiciaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoFranquiciaEnhanced();
        cache.put(transferObject, enhancedObject);
        
        // IdTipoFranquicia
        try {
            enhancedObject.setIdTipoFranquicia(transferObject.getIdTipoFranquicia());
        } catch (Throwable th) {
        }
        
        //Nombre
        try{
           enhancedObject.setNombreFranquicia(transferObject.getNombreFranquicia());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }
}

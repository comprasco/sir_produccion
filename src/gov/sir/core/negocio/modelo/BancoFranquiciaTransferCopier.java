/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Banco Franquicia del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo.transferutil;

public class BancoFranquiciaTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.BancoFranquicia transferObject = new gov.sir.core.negocio.modelo.BancoFranquicia();
        cache.put(enhancedObject, transferObject);

        // IdBanco
        try {
            transferObject.setIdBanco(enhancedObject.getIdBanco());
        } catch (Throwable th) {
        }
        
        // IdTipoFranquicia
        try {
            transferObject.setIdTipoFranquicia(enhancedObject.getIdTipoFranquicia());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.BancoFranquicia transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced();
        cache.put(transferObject, enhancedObject);
        
        // IdBanco
        try {
            enhancedObject.setIdBanco(transferObject.getIdBanco());
        } catch (Throwable th) {
        }
        
        // IdTipoFranquicia
        try {
            enhancedObject.setIdTipoFranquicia(transferObject.getIdTipoFranquicia());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }
}

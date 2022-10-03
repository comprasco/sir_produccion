package gov.sir.core.negocio.modelo.transferutil;

/**
 * @Autor: Santiago Vásquez @Change: 2062.TARIFAS.REGISTRALES.2017
 */
public class TarifaRegistralUVTTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.TarifaRegistralUVTEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.TarifaRegistralUVT transferObject = new gov.sir.core.negocio.modelo.TarifaRegistralUVT();
        cache.put(enhancedObject, transferObject);

        try {
            transferObject.setInicioCuantia(enhancedObject.getInicioCuantia());
        } catch (Throwable th) {
        }

        try {
            transferObject.setInicioInclusive(enhancedObject.isInicioInclusive());
        } catch (Throwable th) {
        }
        
        try {
            transferObject.setFinalCuantia(enhancedObject.getFinalCuantia());
        } catch (Throwable th) {
        }

        try {
            transferObject.setFinalInclusive(enhancedObject.isFinalInclusive());
        } catch (Throwable th) {
        }
        
        try {
            transferObject.setTipoAplicacionTarifa(enhancedObject.getTipoAplicacionTarifa());
        } catch (Throwable th) {
        }

        try {
            transferObject.setValorTarifa(enhancedObject.getValorTarifa());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.TarifaRegistralUVT transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.TarifaRegistralUVTEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TarifaRegistralUVTEnhanced();
        cache.put(transferObject, enhancedObject);

        try {
            enhancedObject.setInicioCuantia(transferObject.getInicioCuantia());
        } catch (Throwable th) {
        }

        try {
            enhancedObject.setInicioInclusive(transferObject.isInicioInclusive());
        } catch (Throwable th) {
        }

        try {
            enhancedObject.setFinalCuantia(transferObject.getFinalCuantia());
        } catch (Throwable th) {
        }

        try {
            enhancedObject.setFinalInclusive(transferObject.isFinalInclusive());
        } catch (Throwable th) {
        }
        
        try {
            enhancedObject.setTipoAplicacionTarifa(transferObject.getTipoAplicacionTarifa());
        } catch (Throwable th) {
        }
        
        try {
            enhancedObject.setValorTarifa(transferObject.getValorTarifa());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }
}

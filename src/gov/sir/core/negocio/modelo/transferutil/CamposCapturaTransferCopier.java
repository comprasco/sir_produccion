/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.transferutil;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class CamposCapturaTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.CamposCapturaEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.CamposCaptura transferObject = new gov.sir.core.negocio.modelo.CamposCaptura();
        cache.put(enhancedObject, transferObject);
        //IdCamposCaptura
        try {
            transferObject.setIdCamposCaptura(enhancedObject.getIdCamposCaptura());
        } catch (Throwable th) {
        }
        //NombreCampo
        try {
            transferObject.setNombreCampo(enhancedObject.getNombreCampo());
        } catch (Throwable th) {
        }
        //CampoDestino
        try {
            transferObject.setCampoDestino(enhancedObject.getCampoDestino());
        } catch (Throwable th) {
        }
        //FormLabel
        try {
            transferObject.setFormLabel(enhancedObject.getFormLabel());
        } catch (Throwable th) {
        }
        //FormName
        try {
            transferObject.setFormName(enhancedObject.getFormName());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.CamposCaptura transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.CamposCapturaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CamposCapturaEnhanced();
        cache.put(transferObject, enhancedObject);
        //IdCamposCaptura
        try {
            enhancedObject.setIdCamposCaptura(transferObject.getIdCamposCaptura());
        } catch (Throwable th) {
        }
        //NombreCampo
        try {
            enhancedObject.setNombreCampo(transferObject.getNombreCampo());
        } catch (Throwable th) {
        }
        //CampoDestino
        try {
            enhancedObject.setCampoDestino(transferObject.getCampoDestino());
        } catch (Throwable th) {
        }
        //FormLabel
        try {
            enhancedObject.setFormLabel(transferObject.getFormLabel());
        } catch (Throwable th) {
        }
        //FormName
        try {
            enhancedObject.setFormName(transferObject.getFormName());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }

}

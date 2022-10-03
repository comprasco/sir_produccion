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
public class JustificaTiposTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.JustificaTiposEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.JustificaTipos transferObject = new gov.sir.core.negocio.modelo.JustificaTipos();
        cache.put(enhancedObject, transferObject);

        //tipIdTipo
        try {
            transferObject.setTipIdTipo(enhancedObject.getTipIdTipo());
        } catch (Throwable th) {
        }

        //tipNombreNovedad
        try {
            transferObject.setTipNombreNovedad(enhancedObject.getTipNombreNovedad());
        } catch (Throwable th) {
        }

        //tipDescripcion
        try {
            transferObject.setTipDescripcion(enhancedObject.getTipDescripcion());
        } catch (Throwable th) {
        }
        
        //tipVersion
        try {
            transferObject.setTipVersion(enhancedObject.getTipVersion());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.JustificaTipos transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.JustificaTiposEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.JustificaTiposEnhanced();
        cache.put(transferObject, enhancedObject);

        //tipIdTipo
        try {
            enhancedObject.setTipIdTipo(transferObject.getTipIdTipo());
        } catch (Throwable th) {
        }

        //tipNombreNovedad
        try {
            enhancedObject.setTipNombreNovedad(transferObject.getTipNombreNovedad());
        } catch (Throwable th) {
        }

        //tipDescripcion
        try {
            enhancedObject.setTipDescripcion(transferObject.getTipDescripcion());
        } catch (Throwable th) {
        }
        
        //tipVersion
        try {
            enhancedObject.setTipVersion(transferObject.getTipVersion());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }

}

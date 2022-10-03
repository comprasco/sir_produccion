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
public class FormaPagoCamposTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.FormaPagoCamposEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.FormaPagoCampos transferObject = new gov.sir.core.negocio.modelo.FormaPagoCampos();
        cache.put(enhancedObject, transferObject);
        //IdFormaPagoCampos
        try {
            transferObject.setIdFormaPagoCampos(enhancedObject.getIdFormaPagoCampos());
        } catch (Throwable th) {
        }
        //IdFormaPago
        try {
            transferObject.setIdFormaPago(enhancedObject.getIdFormaPago());
        } catch (Throwable th) {
        }
        //IdCamposCaptura
        try {
            transferObject.setIdCamposCaptura(enhancedObject.getIdCamposCaptura());
        } catch (Throwable th) {
        }
        //Estado
        try {
            transferObject.setEstado(enhancedObject.isEstado());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.FormaPagoCampos transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.FormaPagoCamposEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.FormaPagoCamposEnhanced();
        cache.put(transferObject, enhancedObject);
        //IdFormaPagoCampos
        try {
            enhancedObject.setIdFormaPagoCampos(transferObject.getIdFormaPagoCampos());
        } catch (Throwable th) {
        }
        //IdFormaPago
        try {
            enhancedObject.setIdFormaPago(transferObject.getIdFormaPago());
        } catch (Throwable th) {
        }
        //IdCamposCaptura
        try {
            enhancedObject.setIdCamposCaptura(transferObject.getIdCamposCaptura());
        } catch (Throwable th) {
        }
        //Estado
        try {
            enhancedObject.setEstado(transferObject.isEstado());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }

}

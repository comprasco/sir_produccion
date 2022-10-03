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
public class CanalesRecaudoTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.CanalesRecaudo transferObject = new gov.sir.core.negocio.modelo.CanalesRecaudo();
        cache.put(enhancedObject, transferObject);
        //IdCanal
        try {
            transferObject.setIdCanal(enhancedObject.getIdCanal());
        } catch (Throwable th) {
        }
        //NombreCanal
        try {
            transferObject.setNombreCanal(enhancedObject.getNombreCanal());
        } catch (Throwable th) {
        }
        //Activo
        try {
            transferObject.setActivo(enhancedObject.isActivo());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.CanalesRecaudo transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced();
        cache.put(transferObject, enhancedObject);
        //IdCanal
        try {
            enhancedObject.setIdCanal(transferObject.getIdCanal());
        } catch (Throwable th) {
        }
        //NombreCanal
        try {
            enhancedObject.setNombreCanal(transferObject.getNombreCanal());
        } catch (Throwable th) {
        }
        //Activo
        try {
            enhancedObject.setActivo(transferObject.isActivo());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }

}

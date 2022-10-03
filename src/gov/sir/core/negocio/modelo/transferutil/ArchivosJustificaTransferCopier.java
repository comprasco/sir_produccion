/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.transferutil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class ArchivosJustificaTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.ArchivosJustifica transferObject = new gov.sir.core.negocio.modelo.ArchivosJustifica();
        cache.put(enhancedObject, transferObject);

        //jusIdArchivo
        try {
            transferObject.setJusIdArchivo(enhancedObject.getJusIdArchivo());
        } catch (Throwable th) {
        }

        //jusFechaDeSubida
        try {
            transferObject.setJusFechaDeSubida(enhancedObject.getJusFechaDeSubida());
        } catch (Throwable th) {
        }
        //jusNombreOriginal
        try {
            transferObject.setJusNombreOriginal(enhancedObject.getJusNombreOriginal());
        } catch (Throwable th) {
        }

        //jusTipoArchivo
        try {
            transferObject.setJusTipoArchivo(enhancedObject.getJusTipoArchivo());
        } catch (Throwable th) {
        }

        //jusTamano
        try {
            transferObject.setJusTamanoArchivo(enhancedObject.getJusTamanoArchivo());
        } catch (Throwable th) {
        }

        //jusNombreProceso
        try {
            transferObject.setJusNombreProceso(enhancedObject.getJusNombreProceso());
        } catch (Throwable th) {
        }

        //jusRutaFisica
        try {
            transferObject.setJusRutaFisica(enhancedObject.getJusRutaFisica());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.ArchivosJustifica transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced();
        cache.put(transferObject, enhancedObject);

        //jusIdArchivo
        try {
            enhancedObject.setJusIdArchivo(transferObject.getJusIdArchivo());
        } catch (Throwable th) {
        }

        //jusFechaDeSubida
        try {
            enhancedObject.setJusFechaDeSubida(transferObject.getJusFechaDeSubida());
        } catch (Throwable th) {
        }

        //jusNombreOriginal
        try {
            enhancedObject.setJusNombreOriginal(transferObject.getJusNombreOriginal());
        } catch (Throwable th) {
        }

        //jusTipoArchivo
        try {
            enhancedObject.setJusTipoArchivo(transferObject.getJusTipoArchivo());
        } catch (Throwable th) {
        }

        //jusTamano
        try {
            enhancedObject.setJusTamanoArchivo(transferObject.getJusTamanoArchivo());
        } catch (Throwable th) {
        }

        //jusNombreProceso
        try {
            enhancedObject.setJusNombreProceso(transferObject.getJusNombreProceso());
        } catch (Throwable th) {
        }

        //jusRutaFisica
        try {
            enhancedObject.setJusRutaFisica(transferObject.getJusRutaFisica());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.transferutil;

import gov.sir.core.negocio.modelo.ArchivosJustifica;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class JustificaAdmTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.JustificaAdmEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.JustificaAdm transferObject = new gov.sir.core.negocio.modelo.JustificaAdm();
        cache.put(enhancedObject, transferObject);

        //admId
        try {
            transferObject.setAdmId(enhancedObject.getAdmId());
        } catch (Throwable th) {
        }
        //admDescripcion
        try {
            transferObject.setAdmDescripcion(enhancedObject.getAdmDescripcion());
        } catch (Throwable th) {
        }
        //admIpFuncLog
        try {
            transferObject.setAdmIpPcFuncLog(enhancedObject.getAdmIpPcFuncLog());
        } catch (Throwable th) {
        }
        //admIdUsuario
        try {
            transferObject.setAdmIdUsuario(enhancedObject.getAdmIdUsuario());
        } catch (Throwable th) {
        }
        //admFecha
        try {
            transferObject.setAdmFecha(enhancedObject.getAdmFecha());
        } catch (Throwable th) {
        }
        //admUsuarioModifica
        try {
            transferObject.setAdmUsuarioModifica(enhancedObject.getAdmUsuarioModifica());
        } catch (Throwable th) {
        }
        //admCirculoUsrModificado
        try {
            transferObject.setAdmCirculoUsrModificado(enhancedObject.getAdmCirculoUsrModificado());
        } catch (Throwable th) {
        }
        //ArchivosJustifica
        try {
            transferObject.setArchivosJustifica((gov.sir.core.negocio.modelo.ArchivosJustifica) enhancedObject.getArchivosJustifica().toTransferObject());
        } catch (Throwable th) {
        }
        //JustificaTipos
        try {
            transferObject.setJustificaTipos((gov.sir.core.negocio.modelo.JustificaTipos) enhancedObject.getJustificaTipos().toTransferObject());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.JustificaAdm transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.JustificaAdmEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.JustificaAdmEnhanced();
        cache.put(transferObject, enhancedObject);

        //admId
        try {
            enhancedObject.setAdmId(transferObject.getAdmId());
        } catch (Throwable th) {
        }
        //admDescripcion
        try {
            enhancedObject.setAdmDescripcion(transferObject.getAdmDescripcion());
        } catch (Throwable th) {
        }
        //admIpFuncLog
        try {
            enhancedObject.setAdmIpPcFuncLog(transferObject.getAdmIpPcFuncLog());
        } catch (Throwable th) {
        }
        //admIdUsuario
        try {
            enhancedObject.setAdmIdUsuario(transferObject.getAdmIdUsuario());
        } catch (Throwable th) {
        }
        //admFecha
        try {
            enhancedObject.setAdmFecha(transferObject.getAdmFecha());
        } catch (Throwable th) {
        }
        //admUsuarioModifica
        try {
            enhancedObject.setAdmUsuarioModifica(transferObject.getAdmUsuarioModifica());
        } catch (Throwable th) {
        }
        //admCirculoUsrModificado
        try {
            enhancedObject.setAdmCirculoUsrModificado(transferObject.getAdmCirculoUsrModificado());
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setArchivosJustifica((gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced) cache.get(transferObject.getArchivosJustifica()));
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setJustificaTipos((gov.sir.core.negocio.modelo.jdogenie.JustificaTiposEnhanced) cache.get(transferObject.getJustificaTipos()));
        } catch (Throwable th) {
        }

        return enhancedObject;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.transferutil;

/**
 *
 * @author developer5
 */
public class TramiteSuspensionTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.TramiteSuspensionEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.TramiteSuspension transferObject = new gov.sir.core.negocio.modelo.TramiteSuspension();
        cache.put(enhancedObject, transferObject);

        //admId
        try {
            transferObject.setTramsId(enhancedObject.getTramsId());
        } catch (Throwable th) {
        }
        //admDescripcion
        try {
            transferObject.setTramsNombreFase(enhancedObject.getTramsNombreFase());
        } catch (Throwable th) {
        }
        //admIpFuncLog
        try {
            transferObject.setTramsFecha(enhancedObject.getTramsFecha());
        } catch (Throwable th) {
        }

        try {
            transferObject.setTramsUsuario(enhancedObject.getTramsUsuario());
        } catch (Throwable th) {
        }

        //admIdUsuario
        try {
            transferObject.setTramsIdUsuario(enhancedObject.getTramsIdUsuario());
        } catch (Throwable th) {
        }
        //admFecha
        try {
            transferObject.setTramsDescripcion(enhancedObject.getTramsDescripcion());
        } catch (Throwable th) {
        }

        //ArchivosJustifica
        try {
            transferObject.setTramsIdArchivoJustifica((gov.sir.core.negocio.modelo.ArchivosJustifica) enhancedObject.getTramsIdArchivoJustifica().toTransferObject());
        } catch (Throwable th) {
        }

        try {
            transferObject.setTramsTurno(enhancedObject.getTramsTurno());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.TramiteSuspension transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.TramiteSuspensionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TramiteSuspensionEnhanced();
        cache.put(transferObject, enhancedObject);

        //admId
        try {
            enhancedObject.setTramsId(transferObject.getTramsId());
        } catch (Throwable th) {
        }
        //admDescripcion
        try {
            enhancedObject.setTramsNombreFase(transferObject.getTramsNombreFase());
        } catch (Throwable th) {
        }
        //admIpFuncLog
        try {
            enhancedObject.setTramsFecha(transferObject.getTramsFecha());
        } catch (Throwable th) {
        }

        try {
            enhancedObject.setTramsUsuario(transferObject.getTramsUsuario());
        } catch (Throwable th) {
        }

        //admIdUsuario
        try {
            enhancedObject.setTramsIdUsuario(transferObject.getTramsIdUsuario());
        } catch (Throwable th) {
        }
        //admFecha
        try {
            enhancedObject.setTramsDescripcion(transferObject.getTramsDescripcion());
        } catch (Throwable th) {
        }

        try {
            enhancedObject.setTramsIdArchivoJustifica((gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced) cache.get(transferObject.getTramsIdArchivoJustifica()));
        } catch (Throwable th) {
        }

        try {
            enhancedObject.setTramsTurno(transferObject.getTramsTurno());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }
}

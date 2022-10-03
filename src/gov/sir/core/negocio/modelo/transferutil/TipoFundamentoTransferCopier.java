/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 *
 * @author ctorres
 */
class TipoFundamentoTransferCopier {
    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.TipoFundamentoEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.TipoFundamento transferObject = new gov.sir.core.negocio.modelo.TipoFundamento();
        cache.put(enhancedObject, transferObject);

        // IdTraslado
        try {
            transferObject.setIdTipoFundamento(enhancedObject.getIdTipoFundamento());
        } catch (Throwable th) {
        }
        
        try {
            if(enhancedObject.getFechaCreacion()!=null){
                transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
            }
        } catch (Throwable th) {
        }

        // IdTraslado
        try {
            transferObject.setNombre(enhancedObject.getNombre());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.TipoFundamento transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.TipoFundamentoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoFundamentoEnhanced();
        cache.put(transferObject, enhancedObject);
        // IdTraslado
        try {
            enhancedObject.setIdTipoFundamento(transferObject.getIdTipoFundamento());
        } catch (Throwable th) {
        }
        
        // IdTraslado
        try {
            enhancedObject.setFechaCreacion(transferObject.getFechaCreacion());
        } catch (Throwable th) {
        }

        // IdTraslado
        try {
            enhancedObject.setNombre(transferObject.getNombre());
        } catch (Throwable th) {
        }
        return enhancedObject;
    }
}

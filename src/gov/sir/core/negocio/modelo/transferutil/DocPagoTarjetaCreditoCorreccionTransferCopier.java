/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Tarjeta Credito del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

public class DocPagoTarjetaCreditoCorreccionTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(
            gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaCreditoCorreccionEnhanced enhancedObject,
            java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.DocPagoTarjetaCreditoCorreccion transferObject = new gov.sir.core.negocio.modelo.DocPagoTarjetaCreditoCorreccion();
        cache.put(enhancedObject, transferObject);

        // Fecha
        try {
            transferObject.setFecha(enhancedObject.getFecha());
        } catch (Throwable th) {
        }

        // BancoFranquicia
        gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced bancoFranquiciaEnh = null;
        try {
            bancoFranquiciaEnh = enhancedObject.getBancoFranquicia();
        } catch (Throwable th) {
        }

        if (bancoFranquiciaEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.BancoFranquicia objTo = (gov.sir.core.negocio.modelo.BancoFranquicia) cache.get(bancoFranquiciaEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.BancoFranquicia) BancoFranquiciaTransferCopier.deepCopy(bancoFranquiciaEnh, cache);
                }
                transferObject.setBancoFranquicia(objTo);
            }
        }

        // NoTarjetaCredito
        try {
            transferObject.setNumeroTarjeta(enhancedObject.getNumeroTarjeta());
        } catch (Throwable th) {
        }

        // FechaCreacion
        try {
            if (enhancedObject.getFechaCreacion() != null) {
                transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
            }
        } catch (Throwable th) {
        }

        // Circulo
        try {
            transferObject.setCirculo(enhancedObject.getCirculo());
        } catch (Throwable th) {
        }

        // IdDocumentoPago
        try {
            transferObject.setIdDocumentoPago(enhancedObject.getIdDocumentoPago());
        } catch (Throwable th) {
        }

        // TipoPago
        try {
            transferObject.setTipoPago(enhancedObject.getTipoPago());
        } catch (Throwable th) {
        }

        // ValorDocumento
        try {
            transferObject.setValorDocumento(enhancedObject.getValorDocumento());
        } catch (Throwable th) {
        }

        // SaldoDocumento
        try {
            transferObject.setSaldoDocumento(enhancedObject.getSaldoDocumento());
        } catch (Throwable th) {
        }

        // SaldoDocumentoAnulacion
        try {
            transferObject.setSaldoDocumentoAnulacion(enhancedObject.getSaldoDocumentoAnulacion());
        } catch (Throwable th) {
        }

        //Edgar Lora: Mantis: 0012422
        try {
            transferObject.setNumeroAprobacion(enhancedObject.getNumeroAprobacion());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.DocPagoTarjetaCreditoCorreccion transferObject,
            java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaCreditoCorreccionEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaCreditoCorreccionEnhanced();
        cache.put(transferObject, enhancedObject);

        // Fecha
        try {
            enhancedObject.setFecha(transferObject.getFecha());
        } catch (Throwable th) {
        }

        //BancoFranquicia
        gov.sir.core.negocio.modelo.BancoFranquicia bancoFranquicia = null;
        try {
            bancoFranquicia = (gov.sir.core.negocio.modelo.BancoFranquicia) transferObject.getBancoFranquicia();
        } catch (Throwable th) {
        }

        if (bancoFranquicia != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced) cache.get(bancoFranquicia);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoFranquiciaEnhanced) BancoFranquiciaTransferCopier.deepCopy(bancoFranquicia, cache);
                }
                enhancedObject.setBancoFranquicia(objEn);
            }
        }

        //NoTarjetaCredito
        try {
            enhancedObject.setNumeroTarjeta(transferObject.getNumeroTarjeta());
        } catch (Throwable th) {
        }

        // FechaCreacion
        try {
            if (transferObject.getFechaCreacion() != null) {
                enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
            }
        } catch (Throwable th) {
        }

        // Circulo
        try {
            enhancedObject.setCirculo(transferObject.getCirculo());
        } catch (Throwable th) {
        }

        // IdDocumentoPago
        try {
            enhancedObject.setIdDocumentoPago(transferObject.getIdDocumentoPago());
        } catch (Throwable th) {
        }

        // TipoPago
        try {
            enhancedObject.setTipoPago(transferObject.getTipoPago());
        } catch (Throwable th) {
        }

        // ValorDocumento
        try {
            enhancedObject.setValorDocumento(transferObject.getValorDocumento());
        } catch (Throwable th) {
        }

        // SaldoDocumento
        try {
            enhancedObject.setSaldoDocumento(transferObject.getSaldoDocumento());
        } catch (Throwable th) {
        }

        // SaldoDocumentoAnulacion
        try {
            enhancedObject.setSaldoDocumentoAnulacion(transferObject.getSaldoDocumentoAnulacion());
        } catch (Throwable th) {
        }

        //Edgar Lora: Mantis: 0012422
        try {
            enhancedObject.setNumeroAprobacion(transferObject.getNumeroAprobacion());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }
}

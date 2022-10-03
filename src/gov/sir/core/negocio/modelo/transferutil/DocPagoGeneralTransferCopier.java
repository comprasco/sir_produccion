package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 * @author Geremias Ortiz Lozano
 *
 */
public class DocPagoGeneralTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.DocPagoGeneral transferObject = new gov.sir.core.negocio.modelo.DocPagoGeneral();
        cache.put(enhancedObject, transferObject);

        try {
            transferObject.setIdTipoDocPago(enhancedObject.getIdTipoDocPago());
        } catch (Throwable th) {
        }
        try {
            transferObject.setFechaDocu(enhancedObject.getFechaDocu());
        } catch (Throwable th) {
        }
        try {
            transferObject.setNoConsignacion(enhancedObject.getNoConsignacion());
        } catch (Throwable th) {
        }
        gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced bancoEnh = null;
        try {
            bancoEnh = enhancedObject.getBanco();
        } catch (Throwable th) {
        }
        if (bancoEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.Banco objTo = (gov.sir.core.negocio.modelo.Banco) cache.get(bancoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.Banco) BancoTransferCopier.deepCopy(bancoEnh, cache);
                }
                transferObject.setBanco(objTo);
            }
        }
        try {
            transferObject.setCodSucursal(enhancedObject.getCodSucursal());
        } catch (Throwable th) {
        }
        try {
            transferObject.setNoCuenta(enhancedObject.getNoCuenta());
        } catch (Throwable th) {
        }
        try {
            transferObject.setValorDocumento(enhancedObject.getValorDocumento());
        } catch (Throwable th) {
        }
        try {
            transferObject.setNoAprobacion(enhancedObject.getNoAprobacion());
        } catch (Throwable th) {
        }
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
        try {
            transferObject.setNoDocumento(enhancedObject.getNoDocumento());
        } catch (Throwable th) {
        }
        try {
            transferObject.setIdCtp(enhancedObject.getIdCtp());
        } catch (Throwable th) {
        }
        try {
            transferObject.setTipoPago(enhancedObject.getTipoPago());
        } catch (Throwable th) {
        }
        try {
            transferObject.setNombreCanal(enhancedObject.getNombreCanal());
        } catch (Throwable th) {
        }
        try {
            transferObject.setSaldoDocumento(enhancedObject.getSaldoDocumento());
        } catch (Throwable th) {
        }
        try {
            transferObject.setIdDocumentoPago(enhancedObject.getIdDocumentoPago());
        } catch (Throwable th ) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.DocPagoGeneral transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced();
        cache.put(transferObject, enhancedObject);

        try {
            enhancedObject.setIdTipoDocPago(transferObject.getIdTipoDocPago());
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setFechaDocu(transferObject.getFechaDocu());
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setNoConsignacion(transferObject.getNoConsignacion());
        } catch (Throwable th) {
        }
        gov.sir.core.negocio.modelo.Banco banco = null;
        try {
            banco = (gov.sir.core.negocio.modelo.Banco) transferObject.getBanco();
        } catch (Throwable th) {
        }
        if (banco != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced) cache.get(banco);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced) BancoTransferCopier.deepCopy(banco, cache);
                }
                enhancedObject.setBanco(objEn);
            }
        }
        try {
            enhancedObject.setCodSucursal(transferObject.getCodSucursal());
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setNoCuenta(transferObject.getNoCuenta());
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setValorDocumento(transferObject.getValorDocumento());
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setNoAprobacion(transferObject.getNoAprobacion());
        } catch (Throwable th) {
        }
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
        try {
            enhancedObject.setNoDocumento(transferObject.getNoDocumento());
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setIdCtp(transferObject.getIdCtp());
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setTipoPago(transferObject.getTipoPago());
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setNombreCanal(transferObject.getNombreCanal());
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setSaldoDocumento(transferObject.getSaldoDocumento());
        } catch (Throwable th) {
        }
        try {
            enhancedObject.setIdDocumentoPago(transferObject.getIdDocumentoPago());
        } catch (Throwable th ) {
        }

        return enhancedObject;
    }
}

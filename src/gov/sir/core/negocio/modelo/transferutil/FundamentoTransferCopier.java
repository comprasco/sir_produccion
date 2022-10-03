package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class FundamentoTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.FundamentoEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.Fundamento transferObject = new gov.sir.core.negocio.modelo.Fundamento();
        cache.put(enhancedObject, transferObject);

        // IdTraslado
        try {
            transferObject.setNumeroFundamento(enhancedObject.getNumeroFundamento());
        } catch (Throwable th) {
        }
        // IdTraslado
        try {
            transferObject.setIdFundamento(enhancedObject.getIdFundamento());
        } catch (Throwable th) {
        }
        // IdTraslado
        try {
            if(enhancedObject.getFechaCreacion()!=null){
                transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
            }
        } catch (Throwable th) {
        }

        // FolioOrigen
        gov.sir.core.negocio.modelo.jdogenie.TipoFundamentoEnhanced tipoFundamentoEnh = null;
        try {
            tipoFundamentoEnh = enhancedObject.getTipoFundamento();
        } catch (Throwable th) {
        }

        if (tipoFundamentoEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.TipoFundamento objTo = (gov.sir.core.negocio.modelo.TipoFundamento) cache.get(tipoFundamentoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.TipoFundamento) TipoFundamentoTransferCopier.deepCopy(tipoFundamentoEnh, cache);
                }
                transferObject.setTipoFundamento(objTo);
            }

        }
        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.Fundamento transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.FundamentoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.FundamentoEnhanced();
        cache.put(transferObject, enhancedObject);
        // IdTraslado
        try {
            enhancedObject.setNumeroFundamento(transferObject.getNumeroFundamento());
        } catch (Throwable th) {
        }
        // IdTraslado
        try {
            enhancedObject.setIdFundamento(transferObject.getIdFundamento());
        } catch (Throwable th) {
        }
        // IdTraslado
        try {
            enhancedObject.setFechaCreacion(transferObject.getFechaCreacion());
        } catch (Throwable th) {
        }
        
        // FolioOrigen
        gov.sir.core.negocio.modelo.TipoFundamento tipoFundamento = null;
        try {
            tipoFundamento = (gov.sir.core.negocio.modelo.TipoFundamento) transferObject.getTipoFundamento();
        } catch (Throwable th) {
        }
        if (tipoFundamento != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.TipoFundamentoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoFundamentoEnhanced) cache.get(tipoFundamento);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoFundamentoEnhanced) TipoFundamentoTransferCopier.deepCopy(tipoFundamento, cache);
                }
                enhancedObject.setTipoFundamento(objEn);
            }

        }
        return enhancedObject;
    }
}
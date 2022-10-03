package gov.sir.core.negocio.modelo.transferutil;

import gov.sir.core.negocio.modelo.Fundamento;
import gov.sir.core.negocio.modelo.TrasladoDatos;
import gov.sir.core.negocio.modelo.jdogenie.FundamentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */        
public class TrasladoFundamentoTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.TrasladoFundamentoEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.TrasladoFundamento transferObject = new gov.sir.core.negocio.modelo.TrasladoFundamento();
        cache.put(enhancedObject, transferObject);

        // numeroTraslado
        try {
            transferObject.setNumeroTraslado(enhancedObject.getNumeroTraslado());
        } catch (Throwable th) {
        }

        // fechaCreacion
        try {
            transferObject.setIdFundamento(enhancedObject.getIdFundamento());
        } catch (Throwable th) {
        }

        try {
            transferObject.setTipoOrigen(enhancedObject.getTipoOrigen());
        } catch (Throwable th) {
        }

        // traslados
        TrasladoDatosEnhanced trasladoDatos = null;
        try {
            trasladoDatos = enhancedObject.getTrasladoDatos();
        } catch (Throwable th) {
        }
        if (trasladoDatos != null) {

            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.TrasladoDatos objTo = (gov.sir.core.negocio.modelo.TrasladoDatos) cache.get(trasladoDatos);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.TrasladoDatos) TrasladoDatosTransferCopier.deepCopy(trasladoDatos, cache);
                }
                transferObject.setTrasladoDatos(objTo);
            }
        }

        // traslados
        FundamentoEnhanced fundamento = null;
        try {
            fundamento = enhancedObject.getFundamento();
        } catch (Throwable th) {
        }
        if (fundamento != null) {

            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.Fundamento objTo = (gov.sir.core.negocio.modelo.Fundamento) cache.get(fundamento);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.Fundamento) FundamentoTransferCopier.deepCopy(fundamento, cache);
                }
                transferObject.setFundamento(objTo);
            }
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.TrasladoFundamento transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.TrasladoFundamentoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TrasladoFundamentoEnhanced();
        cache.put(transferObject, enhancedObject);
        // IdTraslado
        try {
            enhancedObject.setNumeroTraslado(transferObject.getNumeroTraslado());
        } catch (Throwable th) {
        }

        // IdTraslado
        try {
            enhancedObject.setIdFundamento(transferObject.getIdFundamento());
        } catch (Throwable th) {
        }

        // traslados
        TrasladoDatos trasladoDatos = null;
        try {
            trasladoDatos = transferObject.getTrasladoDatos();
        } catch (Throwable th) {
        }
        if (trasladoDatos != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced objTo = (gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced) cache.get(trasladoDatos);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced) TrasladoDatosTransferCopier.deepCopy(trasladoDatos, cache);
                }
                enhancedObject.setTrasladoDatos(objTo);
            }
        }

        Fundamento fundamento = null;
        try {
            fundamento = transferObject.getFundamento();
        } catch (Throwable th) {
        }
        if (fundamento != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.FundamentoEnhanced objTo = (gov.sir.core.negocio.modelo.jdogenie.FundamentoEnhanced) cache.get(fundamento);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.jdogenie.FundamentoEnhanced) FundamentoTransferCopier.deepCopy(fundamento, cache);
                }
                enhancedObject.setFundamento(objTo);
            }
        }


        return enhancedObject;
    }
}
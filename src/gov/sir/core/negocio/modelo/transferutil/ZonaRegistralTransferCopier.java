package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class ZonaRegistralTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.ZonaRegistral transferObject = new gov.sir.core.negocio.modelo.ZonaRegistral();
        cache.put(enhancedObject, transferObject);

        // Circulo
        gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced circuloEnh = null;
        try {
            circuloEnh = enhancedObject.getCirculo();
        } catch (Throwable th) {
        }
        if (circuloEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.Circulo objTo = (gov.sir.core.negocio.modelo.Circulo) cache.get(circuloEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.Circulo) CirculoTransferCopier.deepCopy(circuloEnh, cache);
                }
                transferObject.setCirculo(objTo);
            }

        }

        // Vereda
        gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced veredaEnh = null;
        try {
            veredaEnh = enhancedObject.getVereda();
        } catch (Throwable th) {
        }
        if (veredaEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.Vereda objTo = (gov.sir.core.negocio.modelo.Vereda) cache.get(veredaEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.Vereda) VeredaTransferCopier.deepCopy(veredaEnh, cache);
                }
                transferObject.setVereda(objTo);
            }

        }

        // IdZonaRegistral
        try {
            transferObject.setIdZonaRegistral(enhancedObject.getIdZonaRegistral());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.ZonaRegistral transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced();
        cache.put(transferObject, enhancedObject);
        // Circulo
        gov.sir.core.negocio.modelo.Circulo circulo = null;
        try {
            circulo = (gov.sir.core.negocio.modelo.Circulo) transferObject.getCirculo();
        } catch (Throwable th) {
        }
        if (circulo != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced) cache.get(circulo);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced) CirculoTransferCopier.deepCopy(circulo, cache);
                }
                enhancedObject.setCirculo(objEn);
            }

        }

        // Vereda
        gov.sir.core.negocio.modelo.Vereda vereda = null;
        try {
            vereda = (gov.sir.core.negocio.modelo.Vereda) transferObject.getVereda();
        } catch (Throwable th) {
        }
        if (vereda != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced) cache.get(vereda);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.VeredaEnhanced) VeredaTransferCopier.deepCopy(vereda, cache);
                }
                enhancedObject.setVereda(objEn);
            }

        }

        // IdZonaRegistral
        try {
            enhancedObject.setIdZonaRegistral(transferObject.getIdZonaRegistral());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }
}

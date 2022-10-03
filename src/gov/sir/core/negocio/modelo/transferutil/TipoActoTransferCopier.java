package gov.sir.core.negocio.modelo.transferutil;

import java.util.Iterator;
import java.util.List;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class TipoActoTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.TipoActo transferObject = new gov.sir.core.negocio.modelo.TipoActo();
        cache.put(enhancedObject, transferObject);

        // Nombre
        try {
            transferObject.setNombre(enhancedObject.getNombre());
        } catch (Throwable th) {
        }

        // IdTipoActo
        try {
            transferObject.setIdTipoActo(enhancedObject.getIdTipoActo());
        } catch (Throwable th) {
        }

        // TipoCalculo
        gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced tipoCalculoEnh = null;
        try {
            tipoCalculoEnh = enhancedObject.getTipoCalculo();
        } catch (Throwable th) {
        }
        if (tipoCalculoEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.TipoCalculo objTo = (gov.sir.core.negocio.modelo.TipoCalculo) cache.get(tipoCalculoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.TipoCalculo) TipoCalculoTransferCopier.deepCopy(tipoCalculoEnh, cache);
                }
                transferObject.setTipoCalculo(objTo);
            }

        }

        // TiposDerechosRegistrale
        List tiposDerechosRegistrale = null;
        try {
            tiposDerechosRegistrale = enhancedObject.getTiposDerechosRegistrales();
        } catch (Throwable th) {
        }
        if (tiposDerechosRegistrale != null) {
            for (Iterator itera = tiposDerechosRegistrale.iterator();
                    itera.hasNext();) {
                boolean assigned = false;
                gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced tiposDerechosRegistraleEnh = (gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced) itera.next();
                if (!assigned) {
                    gov.sir.core.negocio.modelo.TipoActoDerechoRegistral objTo = (gov.sir.core.negocio.modelo.TipoActoDerechoRegistral) cache.get(tiposDerechosRegistraleEnh);
                    if (objTo == null) {
                        objTo = (gov.sir.core.negocio.modelo.TipoActoDerechoRegistral) TipoActoDerechoRegistralTransferCopier.deepCopy(tiposDerechosRegistraleEnh, cache);
                    }
                    transferObject.addTiposDerechosRegistrale(objTo);
                }
            }
        }

        // ImpPorCuantia
        try {
            transferObject.setImpPorCuantia(enhancedObject.isImpPorCuantia());
        } catch (Throwable th) {
        }

        /*
   *@autor          : JATENCIA
   * @mantis        : 0015082 
   * @Requerimiento : 027_589_Acto_liquidación_copias 
   * @descripcion   : Se establece el recorriodo de la capa de negocio
   * a los furmularios.
         */
        try {
            transferObject.setActivo(enhancedObject.isActivo());
        } catch (Throwable th) {
        }
        /* - Fin del bloque - */

        /**
         * @Autor: Santiago Vásquez
         * @Change: 2062.TARIFAS.REGISTRALES.2017
         */
        try {
            transferObject.setIncentivoRegistral(enhancedObject.isIncentivoRegistral());
        } catch (Throwable th) {
        }
        /* - Fin del bloque - */

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.TipoActo transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced();
        cache.put(transferObject, enhancedObject);
        // Nombre
        try {
            enhancedObject.setNombre(transferObject.getNombre());
        } catch (Throwable th) {
        }

        // IdTipoActo
        try {
            enhancedObject.setIdTipoActo(transferObject.getIdTipoActo());
        } catch (Throwable th) {
        }

        // TipoCalculo
        gov.sir.core.negocio.modelo.TipoCalculo tipoCalculo = null;
        try {
            tipoCalculo = (gov.sir.core.negocio.modelo.TipoCalculo) transferObject.getTipoCalculo();
        } catch (Throwable th) {
        }
        if (tipoCalculo != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced) cache.get(tipoCalculo);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoCalculoEnhanced) TipoCalculoTransferCopier.deepCopy(tipoCalculo, cache);
                }
                enhancedObject.setTipoCalculo(objEn);
            }

        }

        // TiposDerechosRegistrale
        List tiposDerechosRegistrale = null;
        try {
            tiposDerechosRegistrale = transferObject.getTiposDerechosRegistrales();
        } catch (Throwable th) {
        }
        if (tiposDerechosRegistrale != null) {
            for (Iterator itera = tiposDerechosRegistrale.iterator();
                    itera.hasNext();) {
                boolean assigned = false;
                gov.sir.core.negocio.modelo.TipoActoDerechoRegistral tiposDerechosRegistraleto = (gov.sir.core.negocio.modelo.TipoActoDerechoRegistral) itera.next();
                if (!assigned) {
                    gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced) cache.get(tiposDerechosRegistraleto);
                    if (objEn == null) {
                        objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoActoDerechoRegistralEnhanced) TipoActoDerechoRegistralTransferCopier.deepCopy(tiposDerechosRegistraleto, cache);
                    }
                    enhancedObject.addTiposDerechosRegistrale(objEn);
                }
            }
        }

        // ImpPorCuantia
        try {
            enhancedObject.setImpPorCuantia(transferObject.isImpPorCuantia());
        } catch (Throwable th) {
        }

        /*
      *@autor          : JATENCIA
      * @mantis        : 0015082 
      * @Requerimiento : 027_589_Acto_liquidación_copias 
      * @descripcion   : Se establece el recorriodo de los formularios
      * a la capa de negocio.
         */
        try {
            enhancedObject.setActivo(transferObject.isActivo());
        } catch (Throwable th) {
        }
        /* - Fin del bloque - */

        /**
         * @Autor: Santiago Vásquez
         * @Change: 2062.TARIFAS.REGISTRALES.2017
         */
        try {
            enhancedObject.setIncentivoRegistral(transferObject.isIncentivoRegistral());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }
}

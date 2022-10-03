package gov.sir.core.negocio.modelo.transferutil;

import java.util.Date;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class CirculoTipoPagoTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.CirculoTipoPago transferObject = new gov.sir.core.negocio.modelo.CirculoTipoPago();
        cache.put(enhancedObject, transferObject);

        // FechaCreacion
        try {
            if (enhancedObject.getFechaCreacion() != null) {
                transferObject.setFechaCreacion(new Date(enhancedObject.getFechaCreacion().getTime()));
            }
        } catch (Throwable th) {
        }

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

        // IdCirculo
        try {
            transferObject.setIdCirculo(enhancedObject.getIdCirculo());
        } catch (Throwable th) {
        }

        // IdTipoDocPago
        try {
            transferObject.setIdTipoDocPago(enhancedObject.getIdTipoDocPago());
        } catch (Throwable th) {
        }

        // TipoPago
        gov.sir.core.negocio.modelo.jdogenie.TipoPagoEnhanced tipoPagoEnh = null;
        try {
            tipoPagoEnh = enhancedObject.getTipoPago();
        } catch (Throwable th) {
        }
        if (tipoPagoEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.TipoPago objTo = (gov.sir.core.negocio.modelo.TipoPago) cache.get(tipoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.TipoPago) TipoPagoTransferCopier.deepCopy(tipoPagoEnh, cache);
                }
                transferObject.setTipoPago(objTo);
            }

        }

        // IdCirculoTipoPago
        try {
            transferObject.setIdCirculoTipoPago(enhancedObject.getIdCirculoTipoPago());
        } catch (Throwable th) {
        }

        // IdCuentaBancaria
        try {
            transferObject.setIdCuentaBancaria(enhancedObject.getIdCuentaBancaria());
        } catch (Throwable th) {
        }

        // IdCanalRecaudo
        try {
            transferObject.setIdCanalRecaudo(enhancedObject.getIdCanalRecaudo());
        } catch (Throwable th) {
        }

        // CuentasBancarias
        gov.sir.core.negocio.modelo.jdogenie.CuentasBancariasEnhanced cuentasBancariasEnh = null;
        try {
            cuentasBancariasEnh = enhancedObject.getCuentasBancarias();
        } catch (Throwable th) {
        }
        if (cuentasBancariasEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.CuentasBancarias objTo = (gov.sir.core.negocio.modelo.CuentasBancarias) cache.get(cuentasBancariasEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.CuentasBancarias) CuentasBancariasTransferCopier.deepCopy(cuentasBancariasEnh, cache);
                }
                transferObject.setCuentasBancarias(objTo);
            }

        }

        // CanalesRecaudo
        gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced canalesRecaudoEnh = null;
        try {
            canalesRecaudoEnh = enhancedObject.getCanalesRecaudo();
        } catch (Throwable th) {
        }
        if (canalesRecaudoEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.CanalesRecaudo objTo = (gov.sir.core.negocio.modelo.CanalesRecaudo) cache.get(canalesRecaudoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.CanalesRecaudo) CanalesRecaudoTransferCopier.deepCopy(canalesRecaudoEnh, cache);
                }
                transferObject.setCanalesRecaudo(objTo);
            }

        }
        //CtpActivo
        try {
            transferObject.setCtpActivo(enhancedObject.isCtpActivo());
        } catch (Throwable th) {
        }

        //CanalSir
        try {
            transferObject.setCanalSir(enhancedObject.isCanalSir());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.CirculoTipoPago transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.CirculoTipoPagoEnhanced();
        cache.put(transferObject, enhancedObject);
        // FechaCreacion
        try {
            if (transferObject.getFechaCreacion() != null) {
                enhancedObject.setFechaCreacion(new Date(transferObject.getFechaCreacion().getTime()));
            }
        } catch (Throwable th) {
        }

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

        // IdCirculo
        try {
            enhancedObject.setIdCirculo(transferObject.getIdCirculo());
        } catch (Throwable th) {
        }

        // IdTipoDocPago
        try {
            enhancedObject.setIdTipoDocPago(transferObject.getIdTipoDocPago());
        } catch (Throwable th) {
        }

        // TipoPago
        gov.sir.core.negocio.modelo.TipoPago tipoPago = null;
        try {
            tipoPago = (gov.sir.core.negocio.modelo.TipoPago) transferObject.getTipoPago();
        } catch (Throwable th) {
        }
        if (tipoPago != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.TipoPagoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoPagoEnhanced) cache.get(tipoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.TipoPagoEnhanced) TipoPagoTransferCopier.deepCopy(tipoPago, cache);
                }
                enhancedObject.setTipoPago(objEn);
            }

        }

        // IdCirculoTipoPago
        try {
            enhancedObject.setIdCirculoTipoPago(transferObject.getIdCirculoTipoPago());
        } catch (Throwable th) {
        }

        // IdCuentaBancaria
        try {
            enhancedObject.setIdCuentaBancaria(transferObject.getIdCuentaBancaria());
        } catch (Throwable th) {
        }

        // IdCanalRecaudo
        try {
            enhancedObject.setIdCanalRecaudo(transferObject.getIdCanalRecaudo());
        } catch (Throwable th) {
        }

        // CuentasBancarias
        gov.sir.core.negocio.modelo.CuentasBancarias cuentasBancarias = null;
        try {
            cuentasBancarias = (gov.sir.core.negocio.modelo.CuentasBancarias) transferObject.getCuentasBancarias();
        } catch (Throwable th) {
        }
        if (cuentasBancarias != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.CuentasBancariasEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CuentasBancariasEnhanced) cache.get(cuentasBancarias);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.CuentasBancariasEnhanced) CuentasBancariasTransferCopier.deepCopy(cuentasBancarias, cache);
                }
                enhancedObject.setCuentasBancarias(objEn);
            }

        }

        // CanalesRecaudo
        gov.sir.core.negocio.modelo.CanalesRecaudo canalesRecaudo = null;
        try {
            canalesRecaudo = (gov.sir.core.negocio.modelo.CanalesRecaudo) transferObject.getCanalesRecaudo();
        } catch (Throwable th) {
        }
        if (canalesRecaudo != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced) cache.get(canalesRecaudo);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.CanalesRecaudoEnhanced) CanalesRecaudoTransferCopier.deepCopy(canalesRecaudo, cache);
                }
                enhancedObject.setCanalesRecaudo(objEn);
            }

        }
        //CtpActivo
        try {
            enhancedObject.setCtpActivo(transferObject.isCtpActivo());
        } catch (Throwable th) {
        }

        //CanalSir
        try {
            enhancedObject.setCanalSir(transferObject.isCanalSir());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }
}

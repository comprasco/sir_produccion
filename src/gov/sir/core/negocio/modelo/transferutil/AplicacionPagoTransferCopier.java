package gov.sir.core.negocio.modelo.transferutil;

/**
 * Clase generada Automáticamente con TransferUtilsGenerator.
 *
 */
public class AplicacionPagoTransferCopier {

    public static org.auriga.core.modelo.TransferObject deepCopy(gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced enhancedObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.AplicacionPago transferObject = new gov.sir.core.negocio.modelo.AplicacionPago();
        cache.put(enhancedObject, transferObject);

        // IdLiquidacion
        try {
            transferObject.setIdLiquidacion(enhancedObject.getIdLiquidacion());
        } catch (Throwable th) {
        }

        // IdSolicitud
        try {
            transferObject.setIdSolicitud(enhancedObject.getIdSolicitud());
        } catch (Throwable th) {
        }

        // Circulo
        try {
            transferObject.setCirculo(enhancedObject.getCirculo());
        } catch (Throwable th) {
        }

        // DocumentoPago
        gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoEnhanced documentoPagoEnh = null;
        try {
            documentoPagoEnh = enhancedObject.getDocumentoPago();
        } catch (Throwable th) {
        }
        if (documentoPagoEnh != null) {
            boolean assigned = false;

            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoChequeTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }

            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeGerenciaEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoChequeGerenciaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeGerenciaEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }

            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoChequePosfechadoEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoChequePosfechadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoChequePosfechadoEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }

            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoConsignacionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }

            /*
             * @autor         : HGOMEZ 
             * @mantis        : 12422 
             * @Requerimiento : 049_453 
             * @descripcion   : Se crean los métodos apropiados para manejar 
             * las formas de pago Tarjeta de Credito, Tarjeta Debito y Convenio.
             */
            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaCreditoEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoTarjetaCreditoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaCreditoEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }

            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaDebitoEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoTarjetaDebitoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaDebitoEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }

            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoConvenioTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }

            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoEfectivoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }

            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoHeredadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }

            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoElectronicoPSEEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoElectronicoPSETransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoElectronicoPSEEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }

            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoTimbreConstanciaLiquidacionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }
            
            if (documentoPagoEnh instanceof gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced) {
                gov.sir.core.negocio.modelo.DocumentoPago objTo = (gov.sir.core.negocio.modelo.DocumentoPago) cache.get(documentoPagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.DocumentoPago) DocPagoGeneralTransferCopier.deepCopy((gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced) documentoPagoEnh, cache);
                }
                transferObject.setDocumentoPago(objTo);
                assigned = true;
            }

        }

        // IdDocumentoPago
        try {
            transferObject.setIdDocumentoPago(enhancedObject.getIdDocumentoPago());
        } catch (Throwable th) {
        }

        // Pago
        gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced pagoEnh = null;
        try {
            pagoEnh = enhancedObject.getPago();
        } catch (Throwable th) {
        }
        if (pagoEnh != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.Pago objTo = (gov.sir.core.negocio.modelo.Pago) cache.get(pagoEnh);
                if (objTo == null) {
                    objTo = (gov.sir.core.negocio.modelo.Pago) PagoTransferCopier.deepCopy(pagoEnh, cache);
                }
                transferObject.setPago(objTo);
            }

        }

        // ValorAplicado
        try {
            transferObject.setValorAplicado(enhancedObject.getValorAplicado());
        } catch (Throwable th) {
        }

        /**
         * @Autor: Santiago Vásquez
         * @Change: 1242.VER DETALLES DE TURNO VISUALIZACION FORMAS PAGO
         */
        // ValorAplicadoAnulacion
        try {
            transferObject.setValorAplicadoAnulacion(enhancedObject.getValorAplicadoAnulacion());
        } catch (Throwable th) {
        }

        return transferObject;
    }

    public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy(gov.sir.core.negocio.modelo.AplicacionPago transferObject, java.util.HashMap cache) {
        gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced();
        cache.put(transferObject, enhancedObject);
        // IdLiquidacion
        try {
            enhancedObject.setIdLiquidacion(transferObject.getIdLiquidacion());
        } catch (Throwable th) {
        }

        // IdSolicitud
        try {
            enhancedObject.setIdSolicitud(transferObject.getIdSolicitud());
        } catch (Throwable th) {
        }

        // Circulo
        try {
            enhancedObject.setCirculo(transferObject.getCirculo());
        } catch (Throwable th) {
        }

        // DocumentoPago
        gov.sir.core.negocio.modelo.DocumentoPago documentoPago = null;
        try {
            documentoPago = (gov.sir.core.negocio.modelo.DocumentoPago) transferObject.getDocumentoPago();
        } catch (Throwable th) {
        }
        if (documentoPago != null) {
            boolean assigned = false;
            // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
            // Cambio para agregar nota débito requerimiento 108 - Incidencia Mantis 02360.
            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoCheque) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeEnhanced) DocPagoChequeTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoCheque) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            } else if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoNotaDebito) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoNotaDebitoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoNotaDebitoEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoNotaDebitoEnhanced) DocPagoNotaDebitoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoNotaDebito) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }

            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoChequeGerencia) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeGerenciaEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeGerenciaEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequeGerenciaEnhanced) DocPagoChequeGerenciaTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoChequeGerencia) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }

            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoChequePosfechado) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoChequePosfechadoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequePosfechadoEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoChequePosfechadoEnhanced) DocPagoChequePosfechadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoChequePosfechado) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }

            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoConsignacion) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced) DocPagoConsignacionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoConsignacion) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }

            /*
             * @autor         : HGOMEZ 
             * @mantis        : 12422 
             * @Requerimiento : 049_453 
             * @descripcion   : Se crean los métodos apropiados para manejar 
             * las formas de pago Tarjeta de Credito, Tarjeta Debito y Convenio.
             */
            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoTarjetaCredito) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaCreditoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaCreditoEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaCreditoEnhanced) DocPagoTarjetaCreditoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoTarjetaCredito) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }

            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoTarjetaDebito) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaDebitoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaDebitoEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoTarjetaDebitoEnhanced) DocPagoTarjetaDebitoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoTarjetaDebito) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }

            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoConvenio) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoConvenioEnhanced) DocPagoConvenioTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoConvenio) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }

            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoEfectivo) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoEnhanced) DocPagoEfectivoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoEfectivo) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }

            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoHeredado) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoEnhanced) DocPagoHeredadoTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoHeredado) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }

            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoElectronicoPSE) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoElectronicoPSEEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoElectronicoPSEEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoElectronicoPSEEnhanced) DocPagoElectronicoPSETransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoElectronicoPSE) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }

            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacion) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoTimbreConstanciaLiquidacionEnhanced) DocPagoTimbreConstanciaLiquidacionTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoTimbreConstanciaLiquidacion) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }
            
            if (documentoPago instanceof gov.sir.core.negocio.modelo.DocPagoGeneral) {
                gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced) cache.get(documentoPago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.DocPagoGeneralEnhanced) DocPagoGeneralTransferCopier.deepCopy((gov.sir.core.negocio.modelo.DocPagoGeneral) documentoPago, cache);
                }
                enhancedObject.setDocumentoPago(objEn);
                assigned = true;
            }

        }

        // IdDocumentoPago
        try {
            enhancedObject.setIdDocumentoPago(transferObject.getIdDocumentoPago());
        } catch (Throwable th) {
        }

        // Pago
        gov.sir.core.negocio.modelo.Pago pago = null;
        try {
            pago = (gov.sir.core.negocio.modelo.Pago) transferObject.getPago();
        } catch (Throwable th) {
        }
        if (pago != null) {
            boolean assigned = false;
            if (!assigned) {
                gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced objEn = (gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced) cache.get(pago);
                if (objEn == null) {
                    objEn = (gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced) PagoTransferCopier.deepCopy(pago, cache);
                }
                enhancedObject.setPago(objEn);
            }

        }

        // ValorAplicado
        try {
            enhancedObject.setValorAplicado(transferObject.getValorAplicado());
        } catch (Throwable th) {
        }

        /**
         * @Autor: Santiago Vásquez
         * @Change: 1242.VER DETALLES DE TURNO VISUALIZACION FORMAS PAGO
         */
        // ValorAplicadoAnulacion
        try {
            enhancedObject.setValorAplicadoAnulacion(transferObject.getValorAplicadoAnulacion());
        } catch (Throwable th) {
        }

        return enhancedObject;
    }
}

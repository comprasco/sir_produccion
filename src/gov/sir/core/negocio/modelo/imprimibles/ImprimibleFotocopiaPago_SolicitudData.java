package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocPagoGeneral;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;

import java.awt.print.PageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class ImprimibleFotocopiaPago_SolicitudData
        extends ImprimibleFotocopiaLiquidarSolicitud {

    private String usuarioGeneraRecibo;
    private static final long serialVersionUID = 1L;

    public void setUsuarioGeneraRecibo(String local_UsuarioGeneraRecibo) {
        this.usuarioGeneraRecibo = local_UsuarioGeneraRecibo;
    } //:setUsuarioGeneraRecibo

    private String getUsuarioGeneraRecibo() {
        return usuarioGeneraRecibo;
    } //:setUsuarioGeneraRecibo

    /* JAlcazar caso Mantis 05014 Acta - Requerimiento No 192 - Reimpresión de Recibo de Fotocopias
    * Constante que indica que es una reimpresión.
     */
    private boolean reimpresion;

    /**
     * setUsuarioGeneraRecibo
     *
     * @param local_UsuarioGeneraRecibo String
     */
    public void print_UsuarioGeneraRecibo(String local_UsuarioGeneraRecibo) {

        if (null == local_UsuarioGeneraRecibo) {
            return;
        }

        if ("".equalsIgnoreCase(local_UsuarioGeneraRecibo.trim())) {
            return;
        }

        final String USUARIO_MSG = "     Usuario: ";

        this.imprimirLinea(ImprimibleConstantes.PLANO, USUARIO_MSG + local_UsuarioGeneraRecibo);

    } //:setUsuarioGeneraRecibo

    public static final String DEFAULT_IMPRIMIBEFOTOCOPIASPAGO_TITLE = "Recibo de Pago de otros recaudos";

    protected Pago pago;

    // vector implements serializable
    protected Vector listOfAplicacionesPago;

    public ImprimibleFotocopiaPago_SolicitudData(Turno turno, SolicitudFotocopia solicitud, Circulo circulo, Liquidacion liquidacion, Pago pago, Vector listOfAplicacionesPago, String tipoImprimible) {
        super(turno, solicitud, circulo, liquidacion, tipoImprimible);
        setTransferObject(pago);
        this.pago = pago;
        this.listOfAplicacionesPago = listOfAplicacionesPago;
    }

    protected void imprimirEncabezado_NumRecibo(PageFormat pageFormat) {
        logger.debug("pago" + this.pago);
        if (null == this.pago) {
            return;               
        }

        logger.debug("num-recibo:" + this.pago.getNumRecibo());

        final int X_POS_NUMRECIBO = 370;

        String numRecibo = this.pago.getNumRecibo();
        if (null != numRecibo) {
            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, X_POS_NUMRECIBO, numRecibo);
        }
    }

    public void configure() {

        super.configure();

        /*

		Rectangle2D.Double newDimension;

		PageConfigBuilder pageConfigBuilder
		= new MidLetterPageConfigBuilder();
		// = new PersonalSize1PageConfigBuilder();
		Paper page = pageConfigBuilder.buildPageFormat().getPaper();

		newDimension = new Rectangle2D.Double(0,0,page.getWidth(),page.getHeight());


		setImprimirLogoEnabled( false );
		setAreaImprimibleDimension( newDimension );
         */
        setTitulo(DEFAULT_IMPRIMIBEFOTOCOPIASPAGO_TITLE);
    }

    // template method
    public void generate(PageFormat pageFormat) {
        super.generate2(pageFormat);
//        this.imprimirCanalRecaudo();
        this.imprimirFormaPago();
        super.imprimirCuerpo_Generales(pageFormat);
        this.print_UsuarioGeneraRecibo(getUsuarioGeneraRecibo());
        /* JAlcazar caso Mantis 05014 Acta - Requerimiento No 192 - Reimpresión de Recibo de Fotocopias
           * Imprime nota si es una reimpresión.
         */
        if (this.reimpresion) {
            this.textoReimpresion();
        }

    }

    protected void imprimirPie(PageFormat pageFormat) {
        // no imprimir pie

        //Bug 3479
        print_UsuarioGeneraRecibo(getUsuarioGeneraRecibo());

        return;
    }

    protected void imprimirCuerpo_Items(PageFormat pageFormat) {
        // no imprime items
        return;
    }

    /*
	 protected void imprimirPie_Normatividad( PageFormat pageFormat ) {
	 	 String[] normatividadText = {
	 		                         "De acuerdo con el Decreto 1428 de 2000 (julio 26),  "           +
											 "se cobran las siguientes tarifas; "                             +
											 "Documentos almacenados en medio óptico o microfilmado: $500 "   +
											 "por cada página reproducida, "                                  +
											 "Documentos que reposen en los archivos físicos de "             +
											 "la respectiva oficina de registro: $200 "                       +
											 "por cada pagina reproducida, "
										  , "De acuerdo con la Resolución 5123 de 2000 (Artículo 1, "        +
										    "parágrafo 1), "                                                 +
										    "Se entenderá que el interesado desiste de su petición "         +
											 "cuando transcurridos dos meses contados a partir de la "        +
											 "solicitud no acredite el pago respectivo, de lo cual "          +
											 "el Registrador dejará expresa constancia en memorial "          +
											 "petitorio de las copias."
		                       };
    // "El valor a pagar se informará una vez se determine el número total de copias a expedir"
	    this.setFootLegendText( normatividadText );
		 super.imprimirPie_Normatividad( pageFormat );
	 }
     */
    /**
     * Imprime la forma de pago del recibo.
     *
     */
    protected void imprimirFormaPago() {

        this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.SEPARADOR2);
        //this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO1,
                "Datos de Pago:", true);

        //Imprimir las aplicaciones de pago.
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "FORMA DE PAGO:");

        List aplicaciones = null;
        if (null != this.listOfAplicacionesPago) {
            aplicaciones = this.listOfAplicacionesPago;
        } else {
            aplicaciones = pago.getAplicacionPagos();
        }

        logger.debug("pago:" + pago);
        logger.debug("aplicaciones:" + aplicaciones);

        DocumentoPago docPago;
        String line = "";

        if (aplicaciones != null) {
            Iterator it = aplicaciones.iterator();

            while (it.hasNext()) {
                boolean tieneChequeConsignacion = false;
                AplicacionPago ap = (AplicacionPago) it.next();
                docPago = ap.getDocumentoPago();
//                line = "     " + docPago.getTipoPago();

                if (docPago instanceof DocPagoGeneral) {
                    DocPagoGeneral doc = (DocPagoGeneral) docPago;
                    line += "     " + doc.getNombreCanal() + "-" + ((DocPagoGeneral) docPago).getNombreFormaPago();

//                        if (doc.getBanco().getIdBanco() != null || doc.getBanco() != null) {
                    if (doc.getBanco() != null && doc.getBanco().getNombre() != null) {
                        line += ("     BANCO: " + doc.getBanco().getNombre());
                    }

                    //------------------ nueva linea para mostrar el numero PIN --------------- 
                    if (doc.getNoAprobacion() != null && !doc.getNoAprobacion().equals("")) {
                        line += ("     Nro DOC: " + doc.getNoAprobacion());
                    }

                    if (doc.getNoConsignacion() != null && !doc.getNoConsignacion().equals("")) {
                        line += ("     Nro DOC: " + doc.getNoConsignacion());
                    }
                }
                if (docPago instanceof DocPagoCheque) {
                    tieneChequeConsignacion = true;
                    DocPagoCheque doc = (DocPagoCheque) docPago;
                    if (doc.getBanco() != null && doc.getBanco().getNombre() != null) {
                        line += ("                 BANCO: " + doc.getBanco().getNombre());
                    }
                    //line += ("     CUENTA: " + doc.getNoCuenta());
                    line += ("     No Cheque: " + doc.getNoCheque());
                } else if (docPago instanceof DocPagoConsignacion) {
                    tieneChequeConsignacion = true;
                    DocPagoConsignacion doc = (DocPagoConsignacion) docPago;
                    if (doc.getBanco() != null && doc.getBanco().getNombre() != null) {
                        line += ("     BANCO: " + doc.getBanco().getNombre());
                    }
                    //line += ("     CUENTA: " + doc.getNoCuenta());
                    line += ("     No.CONSIGNACION: "
                            + doc.getNoConsignacion());
                }
                if (!tieneChequeConsignacion) {
                    line += ("                                                               "
                            + "                                                               VALOR: $ "
                            + StringFormat.getNumeroFormateado(ap.getValorAplicado()));
                } else {
                    if (docPago instanceof DocPagoConsignacion) {
                        line += ("           VALOR: $ "
                                + StringFormat.getNumeroFormateado(ap.getValorAplicado()));
                    } else if (docPago instanceof DocPagoCheque) {
                        line += ("                 VALOR: $ "
                                + StringFormat.getNumeroFormateado(ap.getValorAplicado()));
                    }
                }

                this.imprimirLinea(ImprimibleConstantes.PLANO, line);
            }
        }

        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");

        Liquidacion liq2 = this.liquidacion;
        double valorT = 0;
        if (liq2 instanceof LiquidacionTurnoRegistro) {
            LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro) liq2;
            valorT = liq.getValorDerechos();
            valorT += liq.getValorImpuestos();
            valorT += liq.getValorMora();
            valorT += liq.getValorOtroImp();
        } else {
            valorT = liq2.getValor();
        }

        String valorTotal = StringFormat.getNumeroFormateado(valorT);
        /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                   * Cambio del texto "VALOR TOTAL " por "VALOR TOTAL AJUSTADO A LA CENTENA POR ACTOS " y cambio en la posición de impresión.
         */
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "VALOR TOTAL AJUSTADO A LA CENTENA POR ACTOS: $ ", false);
        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, 260, valorTotal);

        //this.imprimirLinea(ImprimibleConstantes.tituloGrande, "");
    }

    protected void imprimirCanalRecaudo() {
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "CANAL DE RECAUDO: " + "PRUEBA LUNES 22 ABRIL");
    }

    /* Ellery Robles caso Mantis 07114 Acta - Requerimiento No 218 - Reimpresión de Recibo de Fotocopias
         * void textoReimpresion() Imprime nota reimpresión.
     */
    private void textoReimpresion() {
        String textoReimpresion = "Esta es una reimpresión ";
        String recibo = "";
        if (!(this.pago == null)) {
            recibo = "del recibo " + this.pago.getLastNumRecibo();
        }
        this.imprimirLinea(ImprimibleConstantes.PLANO, textoReimpresion + recibo);
    }

    /* Ellery Robles caso Mantis 07114 Acta - Requerimiento No 218 - Reimpresión de Recibo de Fotocopias
         * Set reimpresion.
     */
    public void setReimpresion(boolean b) {
        reimpresion = b;
    }
}

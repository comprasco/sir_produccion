package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.CheckItemDev;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Consignacion;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocPagoGeneral;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.DocumentoFotocopia;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.MinutaAccionNotarial;
import gov.sir.core.negocio.modelo.MinutaEntidadPublica;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.OtorganteNatural;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.SolCheckedItemDev;
import gov.sir.core.negocio.modelo.Solicitante;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCertificadoMasivo;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;

import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;

import gov.sir.core.negocio.modelo.imprimibles.util.AppletLogger;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLoggerImp1;
import gov.sir.core.negocio.modelo.imprimibles.util.Rupta;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * @author gvillal
 * Clase que representa el imprimible de un recibo de pago.
 * Esta clase representa cualquier tipo de recibo que genera la
 * aplicación.
 */
public class ImprimibleRecibo extends ImprimibleBase {


    protected AppletLogger logger;
    
	/**Constante que se imprime en el recibo de cosnultas cuando no se encuentrá la matrícula.**/
	private static final String MSG_MATRICULA_NO_ENCONTRADA =
			"(no se encontró la matrícula)";

    /**
	 * Author: Ingeniero Diego Hernandez
         * Modificado en 2010/02/23 by jvenegas
     */
    private static final long serialVersionUID = 1L;
	private boolean imprimirDatosCertificadosMasivos =true;
    private List listaPines;



    private static final String TIPO_ACTO_RUPTA = "474";

	/**Objeto que representa los datos del pago del recibo.**/
    protected Pago pago;

	/**Objeto que representa los datos de solicitud.**/
    protected Solicitud solicitud;

      	/**Objeto con los datos básicos del turno.**/
    protected Turno turno;

	/**Constante que indica si la solicitud es de mayor valor.**/
    private boolean esMayorValor = false;

	/**Constante que indica si la solicitud de correcion es de mayor valor.**/
    private boolean esMayorValorCorrecciones = false;

	/**Variable para guardar el tipo de Certtificado.**/
    private String tipoCertificadoId = null;

        /** @author : HGOMEZ
        *** @change : Variable para guardar el nombre del Tipo de Certificado .
        *** Caso Mantis : 11598
     */
    private String tipoCertificadoNombre = null;

	/**Indica si un certificado es de mayor extension.**/
    private boolean mayorExtension = false;
	/** Circulo asociado al imprimible */
    private Circulo circulo;
	/** Fecha de impresión del imprimible */
    protected String fechaImpresion;
	/** Validaciones */
    private Hashtable validaciones;

	/**Determina si el tamaño del papel es carta.**/
    private boolean tamanoCarta = false;

	/**Constante que indica que es una reimpresión.**/
    private boolean reimpresion = false;

	/**Constante que indica si es de consulta exenta.**/
    private boolean consultaExenta = false;

	/***Constante que indica el documento de la consutla exenta*/
    private Documento documento = null;

     	/***Constante que indica el pago si es de masivo*/
    private Pago pagoMasivo = null;

    /**
     * booleano que identifica si el recibo es de certificado de mayor valor
     */
    private boolean esReciboCertificadoMayorValor;

    private boolean esReciboPagoMayorValorRegistro;

    private String justificacionMayorValor;

    private boolean tienematriculasSinMigrar = false;

    private String matriculaSinMigrar;

    private boolean reimpresionPagoMayorValor = false;

    private boolean turnoInternet = false;

    private boolean reimpreso = false;

    private List PagoAsociado = null;
    /**
     * @author : Diana Lora
         * @change : Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
         * Incluir el número de recibo.
     */
    private long reciboMinutaRepartoNotarial = -1;

    public long getReciboMinutaRepartoNotarial() {
        return reciboMinutaRepartoNotarial;
    }

    public void setReciboMinutaRepartoNotarial(long reciboMinutaRepartoNotarial) {
        this.reciboMinutaRepartoNotarial = reciboMinutaRepartoNotarial;
    }

    public List getPagoAsociado() {
        return PagoAsociado;
    }

    public void setPagoAsociado(List PagoAsociado) {
        this.PagoAsociado = PagoAsociado;
    }

    public Circulo getCirculo() {
        return circulo;
    }

    public void setCirculo(Circulo circulo) {
        this.circulo = circulo;
    }
    

    /*Adiciona Funcionalidad Boton de Pago
 * Author: Ingeniero Diego Hernandez
 * Modificacion en: 2010/02/23 by jvenegas
     */

    public List getListaPines() {
        return listaPines;
    }


    public void setListaPines(List listaPines) {
        this.listaPines = listaPines;
    }


    public String getJustificacionMayorValor() {
        return justificacionMayorValor;
    }


    public void setJustificacionMayorValor(String justificacionMayorValor) {
        this.justificacionMayorValor = justificacionMayorValor;
    }


    public boolean isEsReciboPagoMayorValorRegistro() {
        return esReciboPagoMayorValorRegistro;
    }


    public void setEsReciboPagoMayorValorRegistro(boolean esReciboPagoMayorValorRegistro) {
        this.esReciboPagoMayorValorRegistro = esReciboPagoMayorValorRegistro;
    }


    public boolean isEsReciboCertificadoMayorValor() {
        return esReciboCertificadoMayorValor;
    }


    public void setEsReciboCertificadoMayorValor(
            boolean esReciboCertificadoMayorValor) {
        this.esReciboCertificadoMayorValor = esReciboCertificadoMayorValor;
    }


    //Creado Para usar el misnmo formato de impresion que un recibo, sin
    // los datos de pago
	public ImprimibleRecibo(Turno turno, Circulo circulo, String fechaImpresion,String tipoImprimible) {
        super(tipoImprimible);

        // prevenir, cuando se edite y llame a otro constructor
        AppletLogger loggerImpl = AppletLoggerImp1.getAppletLogger();
        if (null == this.logger) {
            this.logger = loggerImpl;
        }

        // setTransferObject(pago);
        // this.pago = pago;
        this.circulo = circulo;
        this.fechaImpresion = fechaImpresion;

        // if( pago != null ) {
        Solicitud tempSolicitud = turno.getSolicitud();
        // }

        //no pintar el margen en los recibos
        this.setImprimirMargen(false);

    }


    /**
     * Constructor de la clase
     * @param pago
     * @param circulo
     * @param fechaImpresion
     */
	public ImprimibleRecibo(Pago pago, Circulo circulo, String fechaImpresion,String tipoImprimible) {
        super(tipoImprimible);

        // prevenir, cuando se edite y llame a otro constructor
        AppletLogger loggerImpl = AppletLoggerImp1.getAppletLogger();
        if (null == this.logger) {
            this.logger = loggerImpl;
        }

        setTransferObject(pago);
        this.pago = pago;
        /**
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   asigna al documento de la solicitud al imprimible
                 * Caso Mantis  :   000941
         */
        List liquidaciones = pago.getLiquidacion().getSolicitud().getLiquidaciones();
                if(!liquidaciones.isEmpty()){
            Liquidacion liquidacion = (Liquidacion) liquidaciones.get(liquidaciones.size() - 1);
            if (pago.getLiquidacion().getSolicitud() instanceof SolicitudCertificadoMasivo
                            && (((LiquidacionTurnoCertificadoMasivo)liquidacion).getTipoTarifa().equals(CTipoTarifa.EXENTO))){
                this.documento = ((SolicitudCertificadoMasivo) pago.getLiquidacion().getSolicitud()).getDocumento();
            }
        }
        this.circulo = circulo;
        this.fechaImpresion = fechaImpresion;

        //if( pago != null ) {
        Solicitud tempSolicitud = pago.getLiquidacion().getSolicitud();
        Turno tempSolicitudTurno = tempSolicitud.getTurno();
        if (tempSolicitudTurno != null) {
            this.solicitud = tempSolicitud.getTurno().getSolicitud();
        }
        // }

        if (this.solicitud == null) {
            this.solicitud = pago.getLiquidacion().getSolicitud();
        }

        //no pintar el margen en los recibos
        this.setImprimirMargen(false);


    }

    /**
     * @author: Diana Lora
     * @change: 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto
     * Notarial
     */
    public ImprimibleRecibo(Pago pago, Circulo circulo, String fechaImpresion, String tipoImprimible, long reciboMinutaRepartoNotarial) {
        super(tipoImprimible);

        // prevenir, cuando se edite y llame a otro constructor
        AppletLogger loggerImpl = AppletLoggerImp1.getAppletLogger();
        if (null == this.logger) {
            this.logger = loggerImpl;
        }

        setTransferObject(pago);
        this.pago = pago;
        this.reciboMinutaRepartoNotarial = reciboMinutaRepartoNotarial;
        /**
         * @author : Julio Alcázar Rivas
         * @change : asigna al documento de la solicitud al imprimible Caso
         * Mantis : 000941
         */
        List liquidaciones = pago.getLiquidacion().getSolicitud().getLiquidaciones();
        if (!liquidaciones.isEmpty()) {
            Liquidacion liquidacion = (Liquidacion) liquidaciones.get(liquidaciones.size() - 1);
            if (pago.getLiquidacion().getSolicitud() instanceof SolicitudCertificadoMasivo
                    && (((LiquidacionTurnoCertificadoMasivo) liquidacion).getTipoTarifa().equals(CTipoTarifa.EXENTO))) {
                this.documento = ((SolicitudCertificadoMasivo) pago.getLiquidacion().getSolicitud()).getDocumento();
            }
        }
        this.circulo = circulo;
        this.fechaImpresion = fechaImpresion;

        //if( pago != null ) {
        Solicitud tempSolicitud = pago.getLiquidacion().getSolicitud();
        Turno tempSolicitudTurno = tempSolicitud.getTurno();
        if (tempSolicitudTurno != null) {
            this.solicitud = tempSolicitud.getTurno().getSolicitud();
        }
        // }

        if (this.solicitud == null) {
            this.solicitud = pago.getLiquidacion().getSolicitud();
        }
        
        //no pintar el margen en los recibos
        this.setImprimirMargen(false);

    }

    /**
     * Constructor de la clase
     *
     * @param pago
     * @param fechaImpresion
     */
    public ImprimibleRecibo(Pago pago, String fechaImpresion, String tipoImprimible) {
        super(tipoImprimible);
        setTransferObject(pago);

        // prevenir, cuando se edite y llame a otro constructor
        AppletLogger loggerImpl = AppletLoggerImp1.getAppletLogger();
        if (null == this.logger) {
            this.logger = loggerImpl;
        }

        logger.debug("Start Executing Super Method");

        this.pago = pago;
        this.fechaImpresion = fechaImpresion;
        this.solicitud = pago.getLiquidacion().getSolicitud().getTurno()
                .getSolicitud();

        if (this.solicitud == null) {
            this.solicitud = pago.getLiquidacion().getSolicitud();
        }

        logger.debug("pago=" + pago);
    }

    /*Adiciona Funcionalidad Boton de Pago
 * Author: Ingeniero Diego Hernandez
 * Modificacion en: 2010/02/23 by jvenegas
     */
    public ImprimibleRecibo(String fechaImpresion, String tipoImprimible) {
        super(tipoImprimible);
        setTransferObject(pago);

        // prevenir, cuando se edite y llame a otro constructor
        AppletLogger loggerImpl = AppletLoggerImp1.getAppletLogger();
        if (null == this.logger) {
            this.logger = loggerImpl;
        }

        logger.debug("Start Executing Super Method");

        //this.pago = pago;
        this.fechaImpresion = fechaImpresion;
        //this.solicitud = pago.getLiquidacion().getSolicitud().getTurno()
        //				 .getSolicitud();

        /*if (this.solicitud == null) {
			this.solicitud = pago.getLiquidacion().getSolicitud();
		}*/
        //logger.debug("pago=" + pago);
    }

    /*
// START-INNER
public class FormatterHelper {

   public static
   String formatDouble( double local_Value, String local_Format ) {
           // TODO Auto-generated method stub
           StringBuffer buffer = new StringBuffer( 128 );
           String decimalFormat = local_Format;
           DecimalFormat format = new DecimalFormat(decimalFormat);
           FieldPosition f = new FieldPosition(0);
           format.format( local_Value ,buffer, f );
           return buffer.toString();
   } // :formatDouble





} // :FormatterHelper
// END-INNER
     */
 /*

        // @see http://www.onjava.com/pub/a/onjava/2000/12/15/formatting_doubles.html
        public void testFormatDouble() {

                double local_Value;
                String local_FormattedValue;
                final String local_Format = "#,##0.00";

                local_Value = 2.748173e8d;
                local_FormattedValue = FormatDouble.formatDouble( local_Value, local_Format );

                assertEquals( "274.817.300,00", local_FormattedValue );


                local_Value = 2000;
                local_FormattedValue = FormatDouble.formatDouble( local_Value, local_Format );

                assertEquals( "2.000,00", local_FormattedValue );

        }

     */
    public static String formatDouble(double local_Value, String local_Format) {
        // TODO Auto-generated method stub
        StringBuffer buffer = new StringBuffer(128);
        String decimalFormat = local_Format;
        DecimalFormat format = new DecimalFormat(decimalFormat);
        FieldPosition f = new FieldPosition(0);
        format.format(local_Value, buffer, f);
        return buffer.toString();
    }

    /**
     * Este metodo se invoca para generar el contenido del imprimible. Genera el
     * vector de hojas imprimibles con toda la información que se va a imprimir.
     */
    public void generate(PageFormat pageFormat) {

        System.out.println("Generando el recibo.....");

        logger.debug("Start Executing Super Method");

        //this.solicitud = pago.getLiquidacion().getSolicitud().getTurno().getSolicitud();
        // retrollamada a la clase hija para imprimir of de registro ... @see: makeNewPage
        super.generate(pageFormat);

        logger.debug("ExecutedSuper");

        if (null == this.turno) {
            this.turno = pago.getLiquidacion().getSolicitud().getTurno();
        }

        logger.debug("Turno.class = " + this.turno);

        List historials = null;

        if (null != this.turno) {
            historials = turno.getHistorials();
        }

        logger.debug("Historials.class = " + historials);

        if ((null != historials)
                && (historials.size() > 0)) {

            String proc = "";
            TurnoHistoria th = (TurnoHistoria) historials.get(historials.size()
                    - 1);

            /*if ((th != null) && (th.getProceso() != null) &&
				(th.getProceso().getNombre() != null)) {
				//proc = th.getProceso().getNombre();
                proc = String.valueOf(th.getProceso().getIdProceso());
			}*/
            if (th != null && th.getFase() != null) {
                proc = th.getFase();
            }

            //es de mayor valor valor?
            //if (proc.equals(CProceso.PROCESO_PAGO_MAYOR_VALOR_REGISTRO)) {
            if (proc.equals(CFase.PMY_REGISTRAR)) {
                this.esMayorValor = true;
                System.out.println("<--------ES MAYOR VALOR----------->");
            } else {
                this.esMayorValor = false;
                System.out.println("<--------NO ES MAYOR VALOR. PROCESO=" + proc + "----------->");
            }
        } else {
            this.esMayorValor = false;
            System.out.println("<--------NO ES MAYOR VALOR. TURNO SIN HISTORIALES----------->");
        }

        if (this.solicitud == null) {
            System.out.println("<.....La solicitud es null.....[1]>");
            this.solicitud = pago.getLiquidacion().getSolicitud();

            if (this.solicitud == null) {
                System.out.println("<.....La solicitud es null.....[2]>");
            } else {
                System.out.println("<Solicitud = " + this.solicitud + "[ok2]>");
            }
        } else {
            System.out.println("<Solicitud = " + this.solicitud + "[OK]>");
        }

        System.out.println("Imprimir encabezado del recibo.....");

        //Encabezado del recibo igual en todos los recibos.
        this.imprimirEncabezadoRecibo();

        System.out.println("Imprimir cuerpo del recibo.....");

        //imprime el cuerpo del recibo
        this.imprimirCuerpoRecibo();

        System.out.println("Imprimir pie del recibo.....");

        imprimirPieRecibo();

        if (this.reimpresion) {
            textoReimpresion();
        }

        if (this.isTurnoInternet()) {
            textoEquivalente();
        }

        System.out.println(".....Recibo Generado");
    }

    // Imprime el pie del recibo
    private void imprimirPieRecibo() {

        Solicitud local_Solicitud = this.solicitud;
        if (local_Solicitud instanceof SolicitudCorreccion) {
            imprimirPieRecibo_Correccion();
        } // :if
        if (local_Solicitud instanceof SolicitudRegistro) {
            imprimirPieRecibo_Registro();
        } // :if
        if (local_Solicitud instanceof SolicitudCertificado) {
            imprimirPieRecibo_Certificado();
        } // :if
        if (local_Solicitud instanceof SolicitudRepartoNotarial) {
            imprimirPieRecibo_RepartoNotarial();
        } // :if
        if (local_Solicitud instanceof SolicitudRestitucionReparto) {
            imprimirPieRecibo_RestitucionReparto();
        } // :if
        if (local_Solicitud instanceof SolicitudConsulta) {
            imprimirPieRecibo_Consulta();
        } // :if
        if (local_Solicitud instanceof SolicitudDevolucion) {
            imprimirPieRecibo_Devolucion();
        } // :if
        if (local_Solicitud instanceof SolicitudCertificadoMasivo) {
            imprimirPieRecibo_Masivos();
        } // :if

    } //:imprimirPieRecibo

    private void textoReimpresion() {
        System.out.println("*****SOLICITUD: " + this.solicitud);

        if (this.solicitud instanceof SolicitudRepartoNotarial) {
            /*
				String textoReimpresion = "Reimpreso por Corrección";
				this.imprimirLinea(ImprimibleConstantes.PLANO, "");
				this.imprimirLinea(ImprimibleConstantes.PLANO, textoReimpresion);
             */

        } else {
            String textoReimpresion = "Esta es una reimpresión del recibo ";
            /**
             * @author : Julio Alcázar Rivas Caso Mantis : 000941 Revision 1 :
             * Ajuste del imprimible a la hoja de recibos
             * @param
             * @return
             */
            //this.imprimirLinea(ImprimibleConstantes.PLANO, "");
            this.imprimirLinea(ImprimibleConstantes.PLANO, textoReimpresion + this.pago.getLastNumRecibo());
        }

    }

    private void textoEquivalente() {

        String textoReimpresion = "Este recibo es equivalente al recibo que se expide por ventanilla y es válido para cualquier reclamo";
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, textoReimpresion);

    }

    // Imprime el pie del recibo en el caso
    // de correcciones
    private void imprimirPieRecibo_Correccion() {

        if (isImprimirFirma_SolicitudCorreccion_Enabled()) {
            imprimirFirma_SolicitudCorreccion();
        } // :if
        print_UsuarioGeneraRecibo(getUsuarioGeneraRecibo());

    } // :imprimirPieRecibo_Correccion

    // Imprime el pie del recibo en el caso
    // de registro
    private void imprimirPieRecibo_Registro() {

        print_UsuarioGeneraRecibo(getUsuarioGeneraRecibo());

    } // :imprimirPieRecibo_Registro

    // Imprime el pie del recibo en el caso
    // de certificados
    private void imprimirPieRecibo_Certificado() {

        print_UsuarioGeneraRecibo(getUsuarioGeneraRecibo());

    } // :imprimirPieRecibo_Registro

    private void imprimirPieRecibo_RepartoNotarial() {

        print_UsuarioGeneraRecibo(getUsuarioGeneraRecibo());

    } // :imprimirPieRecibo_Registro

    private void imprimirPieRecibo_RestitucionReparto() {

        print_UsuarioGeneraRecibo(getUsuarioGeneraRecibo());

    } // :imprimirPieRecibo_RestitucionReparto        SolicitudRestitucionReparto

    private void imprimirPieRecibo_Consulta() {

        print_UsuarioGeneraRecibo(getUsuarioGeneraRecibo());

    } // :imprimirPieRecibo_RestitucionReparto        SolicitudRestitucionReparto

    private void imprimirPieRecibo_Devolucion() {

        print_UsuarioGeneraRecibo(getUsuarioGeneraRecibo());

    } // :imprimirPieRecibo_RestitucionReparto        SolicitudRestitucionReparto

    private void imprimirPieRecibo_Masivos() {
        print_UsuarioGeneraRecibo(getUsuarioGeneraRecibo());
    }

    /**
     * Imprime el cuerpo del recibo dependiendo del tipo de solicitud del mismo.
     */
    private void imprimirCuerpoRecibo() {
        /*if (solicitud instanceof SolicitudCertificadoMasivo) {
			this.imprimirUsuarioRegistrado();
		} else {
			this.imprimirUsuario();
		}*/

        Solicitud solicitudThis = this.solicitud;

        if (solicitudThis instanceof SolicitudCertificado) {
            SolicitudCertificado soliCerti = (SolicitudCertificado) this.solicitud;
            System.out.println("\n\n\n\nTurno ASOCIADO: "
                    + soliCerti.getTurnoAnterior());

            if (soliCerti.getTurnoAnterior() != null) {
                this.imprimirLinea(ImprimibleConstantes.PLANO,
                        "TURNO ANTERIOR: "
                        + soliCerti.getTurnoAnterior().getIdWorkflow());
                System.out.println("idWorkflow: "
                        + soliCerti.getTurnoAnterior().getIdWorkflow());
            }
        } else if (solicitudThis instanceof SolicitudRegistro) {
            SolicitudRegistro soliReg = (SolicitudRegistro) this.solicitud;
            System.out.println("\n\n\n\nTurno ASOCIADO: "
                    + soliReg.getTurnoAnterior() + "[2]");

            if (soliReg.getTurnoAnterior() != null) {
                this.imprimirLinea(ImprimibleConstantes.PLANO,
                        "TURNO ANTERIOR: "
                        + soliReg.getTurnoAnterior().getIdWorkflow());
                System.out.println("idWorkflow: "
                        + soliReg.getTurnoAnterior().getIdWorkflow() + "[2]");
            }
        }

        /*
		 SolicitudCertificado soliCerti = (SolicitudCertificado)this.solicitud;
		 System.out.println("\n\n\n\nTurno ASOCIADO: "+soliCerti.getTurnoAnterior());
				   if(soliCerti.getTurnoAnterior()!=null){
						   this.imprimirLinea(ImprimibleConstantes.PLANO, "TURNO ANTERIOR: "+soliCerti.getTurnoAnterior().getIdWorkflow());
		 System.out.println("idWorkflow: "+soliCerti.getTurnoAnterior().getIdWorkflow());
				   }
         */
        //this.imprimirLinea(ImprimibleConstantes.plano, "");
        imprimirDatosAntiguoSistema();

        if (this.esMayorValor || this.reimpresionPagoMayorValor) {
            this.imprimirCuerpoReciboMayorValor();
        } else if (this.solicitud instanceof SolicitudCertificado) {
            this.imprimirCuerpoReciboCertificado();
        } else if (this.solicitud instanceof SolicitudRegistro) {
            this.imprimirCuerpoReciboRegistro((SolicitudRegistro) this.solicitud);
        } else if (this.solicitud instanceof SolicitudConsulta) {
            this.imprimirCuerpoReciboConsulta();
        } else if (this.solicitud instanceof SolicitudFotocopia) {
            this.imprimirCuerpoReciboFotocopia();
        } else if (this.solicitud instanceof SolicitudDevolucion) {
            this.imprimirCuerpoReciboDevolucion();
        } else if (this.solicitud instanceof SolicitudCorreccion) {
            this.imprimirCuerpoReciboCorreccion();
        } else if (this.solicitud instanceof SolicitudCertificadoMasivo) {
            this.imprimirCuerpoReciboCertificadoMasivo();
        } else if (this.solicitud instanceof SolicitudRepartoNotarial) {
            this.imprimirCuerpoReciboRepartoNotarial();
        } else if (this.solicitud instanceof SolicitudRestitucionReparto) {
            this.imprimirCuerpoReciboRestitucionRepartoNotarial();
        }

    }

    /**
     * Imprime el cuerpo del recibo de certificado.
     */
    private void imprimirCuerpoReciboCertificado() {
        //Se ignora esta linea para que no SALGA el recibo turno padre asociado

        if (this.solicitud instanceof SolicitudCertificado) {
            Solicitud solPadre = this.getNumeroRadicacionAsociadoAlTurnoDeRegistro((SolicitudCertificado) this.solicitud);
            if (solPadre != null) {
                Turno turnoPadre = solPadre.getTurno();
                String numRadAsociada = turnoPadre.getIdWorkflow();

                String msgPadre;
                int cord_x_padre;
                if (solPadre instanceof SolicitudRegistro) {
                    msgPadre = "Asociado al turno de registro: ";
                    cord_x_padre = ImprimibleConstantes.MARGEN_IZQ * 8;
                    //cord_x_padre = ImprimibleConstantes.MARGEN_IZQ * 7 + 22;
                    String line;
                    line = msgPadre;
                    line += numRadAsociada;
                    this.imprimirLinea(ImprimibleConstantes.PLANO, line);
                }

            }
        }
        this.imprimirMatriculasAsociadas();

        this.imprimirSolicitante(this.solicitud);
        // this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        SolicitudCertificado soliCerti = (SolicitudCertificado) this.solicitud;
        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "CERTIFICADOS: " + soliCerti.getNumeroCertificados());
//        this.imprimirCanalRecaudo();
        this.imprimirFormaPago();

        // this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "EL CERTIFICADO SE EXPIDE DE ACUERDO A LOS DATOS SUMINISTRADOS");
    }


    /*Adiciona Funcionalidad Boton de Pago Author: Ingeniero Diego Hernadez Modificacion en: 23/02/2010 by jvenegas */
    /**
     * Imprime el cuerpo del recibo de certificado.
     */
    private void imprimirCuerpoReciboCertificadoMasivo() {
        this.imprimirSolicitante(this.solicitud);

        /**
         * @author : Julio Alcázar Rivas
         * @change : Imprime el documento de la solicitud Caso Mantis : 000941
         */
        this.imprimirDocumento();

        this.imprimirDatosCertificadosMasivos();

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        SolicitudCertificadoMasivo soliCerti = (SolicitudCertificadoMasivo) this.solicitud;

        //this.imprimirLinea(ImprimibleConstantes.TITULO2, "CERTIFICADOS: "+soliCerti.getNumeroCertificados() );
        this.imprimirFormaPago();

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        /**
         * Author: Ingeniero Diego Hernandez Modificado en 2010/02/23 by
         * jvenegas
         */
        if (listaPines != null && listaPines.size() > 0) {
            // Modificado 2010/02/23 by jvenegas para Boton de Pago
            this.imprimirLinea(ImprimibleConstantes.PLANO, "");
            Iterator iter = listaPines.iterator();
            while (iter.hasNext()) {
                String[] linea = ((String) iter.next()).split(";");
                this.imprimirLinea(ImprimibleConstantes.TITULO2, linea[0], false);
                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 6, linea[1], true);
            }
            this.imprimirLinea(ImprimibleConstantes.PLANO, "");
            this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        }
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "LOS CERTIFICADOS SE EXPIDEN DE ACUERDO A LOS DATOS SUMINISTRADOS");
    }

    private void printRegion_DerechosImpuestos(double valorDerechos, double valorImpuestos) {

        double local_Value;
        String local_FormattedValue;
        final String local_Format = "#,##0";

        if (valorDerechos > 0) {

            local_Value = valorDerechos;
            local_FormattedValue = formatDouble(local_Value, local_Format);

            this.imprimirLinea(ImprimibleConstantes.PLANO, "  VALOR DERECHOS: $" + local_FormattedValue);
        }

        if (valorImpuestos > 0) {
            local_Value = valorImpuestos;
            local_FormattedValue = formatDouble(local_Value, local_Format);

            this.imprimirLinea(ImprimibleConstantes.PLANO, "  VALOR IMPUESTOS: $" + local_FormattedValue);
        }
        // -----------------------------------------------------

    } // :printRegion_DerechosImpuestos

    private void printRegion_ValorCertificadosAsociados(double valorCertificadosAsociados) {

        double local_Value;
        String local_FormattedValue;
        final String local_Format = "#,##0";

        if (valorCertificadosAsociados > 0) {

            local_Value = valorCertificadosAsociados;
            local_FormattedValue = formatDouble(local_Value, local_Format);

            this.imprimirLinea(ImprimibleConstantes.PLANO, "  VALOR CERTIFICADOS: $" + local_FormattedValue);
        }

    } // :printRegion_ValorCertAsociados

    /**
     * Imprime el cuerpo del recibo de registro.
     *
     * @param sol
     */
    private void imprimirCuerpoReciboRegistro(SolicitudRegistro sol) {
        this.imprimirSolicitante(sol);
        //this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirEscritura(sol);
        this.imprimirMatriculasAsociadas();

        LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro) this.pago.getLiquidacion();
        List actos = liquidacion.getActos();
        if (actos.isEmpty()) {

            // :: fragmento para imprimir: -------------------------
            // - valorDerechos / valorImpuestos
            double valorDerechos = liquidacion.getValorDerechos();
            double valorImpuestos = liquidacion.getValorImpuestos();

            printRegion_DerechosImpuestos(valorDerechos, valorImpuestos);

            // if (valorDerechos>0){
            //	this.imprimirLinea(ImprimibleConstantes.PLANO,"VALOR DERECHOS: $"+valorDerechos);
            // }
            // if (valorImpuestos>0){
            //	this.imprimirLinea(ImprimibleConstantes.PLANO,"VALOR IMPUESTOS: $"+valorImpuestos);
            // }
            // -----------------------------------------------------
            double valorLiquidacion = liquidacion.getValor();
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "VALOR TOTAL: $" + valorLiquidacion);
            this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "FORMA PAGO:");

            List aplicaciones = liquidacion.getPago().getAplicacionPagos();
            String line = "";
            DocumentoPago docPago = null;
            if (aplicaciones != null) {
                Iterator it = aplicaciones.iterator();
                while (it.hasNext()) {
                    AplicacionPago ap = (AplicacionPago) it.next();
                    docPago = ap.getDocumentoPago();
                    line = "     " + docPago.getTipoPago();

                    if (docPago instanceof DocPagoCheque) {
                        DocPagoCheque doc = (DocPagoCheque) docPago;
                        if (this.solicitud instanceof SolicitudCertificado) {
                            if (doc.getBanco() != null && doc.getBanco().getIdBanco() != null) {
                                line += ("     BANCO: " + doc.getBanco().getIdBanco());
                            }
                        } else {
                            if (doc.getBanco() != null && doc.getBanco().getNombre() != null && doc.getBanco().getNombre().trim() != null) {
                                line += ("     BANCO: " + doc.getBanco().getNombre().trim());
                            }
                        }
                        //line += ("     CUENTA: " + doc.getNoCuenta());
                        line += ("     No: " + doc.getNoCheque());//Numero de Cheque
                    } else if (docPago instanceof DocPagoConsignacion) {
                        DocPagoConsignacion doc = (DocPagoConsignacion) docPago;
                        if (this.solicitud instanceof SolicitudCertificado) {
                            if (doc.getBanco() != null && doc.getBanco().getIdBanco() != null) {
                                line += ("     BANCO: " + doc.getBanco().getIdBanco());
                            }
                        } else {
                            if (doc.getBanco() != null && doc.getBanco().getNombre() != null && doc.getBanco().getNombre().trim() != null) {
                                line += ("     BANCO: " + doc.getBanco().getNombre().trim());
                            }
                        }

                        //       line += ("     CUENTA: " + doc.getNoCuenta());
                        line += ("     No: "
                                + doc.getNoConsignacion());//Numero de Consignacion
                    }

                    line += ("     VALOR: $ "
                            + StringFormat.getNumeroFormateado(ap.getValorAplicado()));

                    this.imprimirLinea(ImprimibleConstantes.PLANO, line);
                }
            }
        } else {
            this.setValidarMensajeReciboRegistro(true);
            this.imprimirActos(actos);
            List solCertAsociados = this.solicitud.getSolicitudesHijas();

            this.imprimirTurnosCertAsociados(solCertAsociados);

            String multa = "0";
            multa = StringFormat.getNumeroFormateado(liquidacion.getValorMora());

            //this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
            //this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
            // :: fragmento para imprimir: -------------------------
            // - valorDerechos / valorImpuestos
            // Bug 3388
            //   Adicionalmente se debe colcar el formato al
            //    numero que se coloca en el imprimible
            //    (@see StringFormat ??)
            // [se mueve a imprimir forma de pago]
            // double valorDerechos;
            // double valorImpuestos;
            // valorDerechos =liquidacion.getValorDerechos();
            // valorImpuestos=liquidacion.getValorImpuestos();
            // printRegion_DerechosImpuestos( valorDerechos, valorImpuestos );
            // -----------------------------------------------------
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "VALOR MULTA:", false);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 130, "$" + multa, true);
//            this.imprimirCanalRecaudo();
            this.imprimirFormaPago();
            this.setValidarMensajeReciboRegistro(false);
        }
    }

    /**
     * @param solCertAsociados
     */
    private void imprimirTurnosCertAsociados(List solCertAsociados) {
        SolicitudCertificado solCertAsoc;
        SolicitudAsociada solAsoc;
        Turno turnoCert;
        String idTurno;
        int iCont = 0;

        if (solCertAsociados == null) {
            return;
        }

        Iterator itera = solCertAsociados.iterator();

        if (itera == null || !itera.hasNext()) {
            return;
        }

        final String TITLE_TURNOSCERTIFICADOSASOCIADOS = "TURNOS CERTIFICADOS ASOCIADOS: ";

        //this.imprimirLinea(ImprimibleConstantes.TITULO2, "TURNOS CERTIFICADOS ASOCIADOS:", false);
        // iterar; luego quitar el primer caracter
        StringBuffer bufferTurnosCertificadosAsociados = new StringBuffer(1024);
        String lineTurnosCertificadosAsociados = TITLE_TURNOSCERTIFICADOSASOCIADOS;

        int local_SumTurnosCertificadosAsociados;

        bufferTurnosCertificadosAsociados.append("TURNOS CERTIFICADOS ASOCIADOS: ");

        local_SumTurnosCertificadosAsociados = 0;

        while (itera.hasNext()) {
            solAsoc = (SolicitudAsociada) itera.next();
            solCertAsoc = (SolicitudCertificado) solAsoc.getSolicitudHija();
            turnoCert = solCertAsoc.getTurno();
            if (turnoCert == null) {
                idTurno = "NULL";
            } else {
                idTurno = turnoCert.getIdWorkflow();
                local_SumTurnosCertificadosAsociados++;
            }

            switch (iCont) {
                case 1:
                    bufferTurnosCertificadosAsociados.append(", ");
                case 0:
                    bufferTurnosCertificadosAsociados.append(idTurno);

                    iCont = 1;
            }
        }
        if (0 == bufferTurnosCertificadosAsociados.length()) {
            lineTurnosCertificadosAsociados = "";
        } else //lineTurnosCertificadosAsociados = bufferTurnosCertificadosAsociados.substring(",".length(), bufferTurnosCertificadosAsociados.length());
        {
            lineTurnosCertificadosAsociados = bufferTurnosCertificadosAsociados.toString();
        }

        // this.imprimirLinea(ImprimibleConstantes.PLANO, lineTurnosCertificadosAsociados, true);
        // this.imprimirLinea(ImprimibleConstantes.TITULO2, lineTurnosCertificadosAsociados, true);
        // this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
        // this.imprimirLinea(ImprimibleConstantes.TITULO_MICRO, "");
        // Bug 0003387
        this.imprimirLinea(ImprimibleConstantes.TITULO2, TITLE_TURNOSCERTIFICADOSASOCIADOS + local_SumTurnosCertificadosAsociados, true);

    }

    /**
     * Imprime el cuerpo del recibo de consulta.
     */
    private void imprimirCuerpoReciboConsulta() {
        SolicitudConsulta soliConsul = (SolicitudConsulta) this.solicitud;

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        /*        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
					"DATO A CONSULTAR:");*/

        this.imprimirBusquedas(soliConsul);
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        this.imprimirSolicitante(this.solicitud);
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        if (this.isConsultaExenta()) {
            this.imprimirDocumento(soliConsul.getDocumento());
            this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        }

        int numConsultas = soliConsul.getNumeroMaximoBusquedas();

        if (numConsultas == 0) {
            numConsultas = soliConsul.getBusquedas().size();
        }

        if (numConsultas == 0) {
            numConsultas++;
        }

        TipoConsulta tipoConsulta = soliConsul.getTipoConsulta();
        if (!tipoConsulta.getIdTipoConsulta().equals(TipoConsulta.TIPO_EXENTO)) {
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "NUMERO DE CONSULTAS: " + numConsultas);
        }

        this.imprimirFormaPago();

        //imprimir resultados de la consulta
        /*        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
				this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
				this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
				this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
				this.imprimirLinea(ImprimibleConstantes.TITULO2, "");

				String titulo = "CONSULTA INDICE DE PROPIETARIO";
				titulo = StringFormat.getCentrada(titulo,
						ImprimibleConstantes.MAX_NUM_CHAR_TITULO1, 0);
				this.imprimirLinea(ImprimibleConstantes.TITULO1, titulo);

				String fechaImp = this.getFechaImpresion();
				fechaImp = StringFormat.getCentrada(fechaImp,
						ImprimibleConstantes.MAX_NUM_CHAR, 0);
				this.imprimirLinea(ImprimibleConstantes.PLANO, fechaImp);
				this.imprimirResultadosBusquedas(soliConsul);*/
    }

    /**
     *
     * @param solConsul
     */
    private void imprimirResultadosBusquedas(SolicitudConsulta solConsul) {
        System.out.println("Imprimiendo los resultados de la busqueda.....");

        List solicitudesFolio = solConsul.getSolicitudFolios();

        if (solicitudesFolio != null) {
            System.out.println("Numero de resultados: "
                    + solicitudesFolio.size());

            if (solicitudesFolio.size() == 0) {
                this.imprimirLinea(ImprimibleConstantes.PLANO,
                        "la consulta no arrojó ningún resultado");
            } else {
                for (int i = 0; i < solicitudesFolio.size(); i++) {
                    this.imprimirLinea(ImprimibleConstantes.TITULO1, "");

                    SolicitudFolio solFol = (SolicitudFolio) solicitudesFolio.
                            get(i);
                    String idmatricula = solFol.getIdMatricula();
                    this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                            "MATRICULA: " + idmatricula);

                    Folio folio = solFol.getFolio();

                    if (folio != null) {
                        List direcciones = folio.getDirecciones();

                        if (direcciones != null) {
                            if (direcciones.size() > 0) {
                                Direccion dir = this.getUltimaDireccion(
                                        direcciones); //(Direccion)direcciones.get(direcciones.size()-1);
                                String direccion = dir.getEspecificacion();

                                if (direccion != null) {
                                    this.imprimirLinea(ImprimibleConstantes.TITULO1,
                                            "DIRECCION: " + direccion);
                                } else {
                                    String direccionEje = "";
                                    String direccionEje1 = "";

                                    Eje eje = dir.getEje();

                                    if (eje != null) {
                                        direccionEje = eje.getNombre();
                                    }

                                    Eje eje1 = dir.getEje1();

                                    if (eje1 != null) {
                                        direccionEje1 = eje1.getNombre();
                                    }

                                    if (direccionEje == null) {
                                        direccionEje = "";
                                    }

                                    if (direccionEje1 == null) {
                                        direccionEje1 = "";
                                    }

                                    String dirEje = direccionEje + " "
                                            + direccionEje1;
                                    this.imprimirLinea(ImprimibleConstantes.TITULO1,
                                            "DIRECCION: " + dirEje);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "la consulta no arrojó ningún resultado");
        }

        System.out.println(".....Saliendo de <imprimirResultadosBusquedas>");
    }

    protected String
            imprimirCuerpoReciboFotocopia_PrintNode_GetDescripcionSegment(
                    String descripcion) {

        if (null == descripcion) {
            return "";
        }
        return descripcion.substring(0, descripcion.length() - 1);
        /*
			   if( null == descripcion ) {
		  return "";
			   }
			   String initialString = descripcion.substring( 0,80 );
			   int nextWhitespace = initialString.lastIndexOf( " " );
			   if( nextWhitespace <= 0 )
		  return initialString;
			   return initialString.substring( 0,nextWhitespace );
         */
    }

    protected void imprimirCuerpoReciboFotocopia_PrintNode(DocumentoFotocopia documento, int index) {
        //try {

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        String descripcion
                = imprimirCuerpoReciboFotocopia_PrintNode_GetDescripcionSegment(
                        documento.getDescripcion());
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "Tipo de Documento: "
                + documento.getTipoDocumento().getNombre());
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "Tipo de Fotocopia: "
                + documento.getTipoFotocopia().getNombre());
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "Numero de copias:  " + documento.getNumCopias());
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "Numero de hojas:   " + documento.getNumHojas());
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "Descipcion:        " + descripcion);

        //}
        //catch( Exception e ) {
        //  logger.debug( "Error al realizar impresion de documento asociado, " + ImprimibleRecibo.class.getName() );
        //  e.printStackTrace();
        //}
    }

    /**
     * Imprime el cuerpo del recibo de fotocopia.
     */
    protected void imprimirCuerpoReciboFotocopia() {
        SolicitudFotocopia solicitud1 = (SolicitudFotocopia) this.solicitud;

        // datos generales -------------------------------------------------------
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                "Datos Generales:");

        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "TST.SOLICITUD.ID" + solicitud1.getIdSolicitud());
        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "TST.SOLICITUD.FECHA" + solicitud1.getFecha());
        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "TST.USUARIO.NOMBRECOMPLETO"
                + solicitud1.getUsuario().getNombreCompletoUsuario());

        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "TST.CIRCULO.NIT" + circulo.getNit());
        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "TST.CIRCULO.NOMBRE" + circulo.getNombre());

        // -----------------------------------------------------------------------
        // docuentos asociados ---------------------------------------------------
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                "Documentos Asociados a esta solicitud:");

        this.imprimirSolicitante(this.solicitud);
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        this.logger.debug("Documentos Asociados:");

        int totalCopias = 0;
        foreach:
        {
            SolicitudFotocopia solicitudFotocopia = solicitud1;

            List documentosAsociados = solicitudFotocopia.getDocumentoFotocopia();

            Iterator iterator = documentosAsociados.iterator();
            long numHojasTotales = 0;
            int index = 0;
            for (; iterator.hasNext(); index++) {
                DocumentoFotocopia documento
                        = (DocumentoFotocopia) iterator.next();
                imprimirCuerpoReciboFotocopia_PrintNode(documento, index);
                numHojasTotales
                        += (documento.getNumHojas() * documento.getNumCopias());
            }

            this.imprimirLinea(ImprimibleConstantes.TITULO_MICRO,
                    "Numero de Hojas: " + numHojasTotales);

            LiquidacionTurnoFotocopia liquidacion
                    = (LiquidacionTurnoFotocopia) this.pago.getLiquidacion();

            // int numHojas  = liquidacion.getNumHojas();
            // int numCopias = liquidacion.getNumCopias();
//		  int itemTotal = numHojas * numCopias;
//		  this.imprimirLinea( ImprimibleConstantes.TITULO2
//							  , "TST.LIQUIDACION.TIPO" + liquidacion.getTipoFotocopia() );
//		  this.imprimirLinea( ImprimibleConstantes.TITULO2
//							  , "TST.LIQUIDACION.DESCRIPCION" + descripcionSolicitud );
//		  this.imprimirLinea( ImprimibleConstantes.TITULO2
//							  , "TST.LIQUIDACION.NUMHOJAS" + numHojas );
//		  this.imprimirLinea( ImprimibleConstantes.TITULO2
//							  , "TST.LIQUIDACION.NUMCOPIAS" + numCopias );
//		  totalCopias += itemTotal;
        } // end foreach

        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");

        //imprimir resultados de la consulta
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");

        String titulo = "FOTOCOPIAS";
        titulo = StringFormat.getCentrada(titulo,
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1, 0);
        this.imprimirLinea(ImprimibleConstantes.TITULO1, titulo);

        String fechaImp = this.getFechaImpresion();
        fechaImp = StringFormat.getCentrada(fechaImp,
                ImprimibleConstantes.MAX_NUM_CHAR,
                0);
        this.imprimirLinea(ImprimibleConstantes.PLANO, fechaImp);

    }

    /**
     * Imprime el cuerpo del recibo de devolución.
     */
    private void imprimirCuerpoReciboDevolucion() {
        this.imprimirSolicitante(this.solicitud);
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        String tipoPago = "";
        long valorPago = 0;
        long total = 0;
        long saldo = 0;
        int numeral = 0;
        String tipoPagoGen = "SIN TIPO PAGO";

        SolicitudDevolucion sol = (SolicitudDevolucion) turno.getSolicitud();
        String descripcion = ((sol.getDescripcion() != null)
                ? sol.getDescripcion() : "");
        String turnoAnterior = (((sol.getTurnoAnterior() != null)
                && (sol.getTurnoAnterior().getIdWorkflow() != null))
                ? sol.getTurnoAnterior().getIdWorkflow()
                : "Ninguno.");

        this.imprimirLinea(ImprimibleConstantes.TITULO2, "DATOS SOLICITUD");
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "Turno Asociado: " + turnoAnterior);
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "Descripción: " + descripcion);
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "DATOS PAGO INGRESADOS");

        if (sol.getTurnoAnterior() != null) {
            Turno tAnt = sol.getTurnoAnterior();
            while (tAnt != null) {
                if (tAnt.getSolicitud() != null && tAnt.getSolicitud().getLiquidaciones() != null) {
                    List liquidaciones = tAnt.getSolicitud().getLiquidaciones();
                    for (int i = 0; i < liquidaciones.size(); i++) {
                        Liquidacion liq = (Liquidacion) liquidaciones.get(i);
                        List aplicacionesPago = liq.getPago().getAplicacionPagos();

                        for (int j = 0; j < aplicacionesPago.size(); j++) {
                            AplicacionPago aPago = (AplicacionPago) aplicacionesPago.get(j);

                            numeral++;
                            tipoPago = aPago.getDocumentoPago().getTipoPago();
                            valorPago = (long)aPago.getDocumentoPago().getValorDocumento();
                            saldo = (long)aPago.getDocumentoPago().getSaldoDocumento();
                            total += valorPago;
                            if (aPago.getDocumentoPago() instanceof DocPagoCheque) {
                                DocPagoCheque docPago = (DocPagoCheque) aPago.getDocumentoPago();
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                                        "Tipo Pago", false);
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                                        "Valor", false);
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                                        "Saldo", false);
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                                        "Banco", false);
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 13,
                                        "Cheque No.", false);
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 15,
                                        "Fecha", true);

                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                                        tipoPago, false);
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                                        "$" + valorPago, false);
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                                        "$" + saldo, false);
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                                        docPago.getBanco().getNombre(), false);
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 13,
                                        docPago.getNoCheque(), false);
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 15,
                                        docPago.getFecha(), true);
                            } else if (aPago.getDocumentoPago() instanceof DocPagoConsignacion) {
                                DocPagoConsignacion docPago = (DocPagoConsignacion) aPago.getDocumentoPago();
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                                        "Tipo Pago", false);
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                                        "Valor", false);
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                                        "Saldo", false);
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                                        "Banco", false);
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 13,
                                        "Consignación No.", false);
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 15,
                                        "Fecha", true);
                                
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                                        tipoPago, false);
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                                        "$" + valorPago, false);
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                                        "$" + saldo, false);
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                                        docPago.getBanco().getNombre(), false);
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 13,
                                        docPago.getNoConsignacion(), false);
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 15,
                                        docPago.getFecha(), true);
                            } else if(aPago.getDocumentoPago() instanceof DocPagoGeneral){
                                DocPagoGeneral docPago = (DocPagoGeneral) aPago.getDocumentoPago();                               
                                valorPago = (long) aPago.getDocumentoPago().getValorDocumento();
                                saldo = (long) aPago.getDocumentoPago().getSaldoDocumento();
                                total += valorPago;
                                this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ,
                                        "PAGO No. " + numeral + ": " + docPago.getNombreCanal() + " - " + docPago.getTipoPago(), true);

                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                                        "Valor", false);
                                if (docPago.getFechaDocu() != null) {
                                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                                        "Saldo", false);
                                }else{
                                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                                        "Saldo", true);
                                }

                                if (docPago.getBanco() != null) {
                                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                                            "Banco", false);
                                }
                                //------------------ nueva linea para mostrar el numero PIN ---------------                                
                                if (docPago.getNoAprobacion() != null) {
                                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                                            "Documento No.", false);
                                }
                                if (docPago.getNoConsignacion() != null) {
                                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                                            "Documento No.", false);
                                }
                                if (docPago.getFechaDocu() != null) {
                                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 13,
                                            "Fecha", true);
                                }
                                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                                        "$" + valorPago, false);

                                if (docPago.getFechaDocu() != null) {
                                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                                        "$" + saldo, false);
                                }else {
                                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                                        "$" + saldo, true);
                                }
                                
                                if (docPago.getBanco() != null) {
                                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                                            docPago.getBanco().getNombre(), false);
                                }
                                //------------------ nueva linea para mostrar el numero PIN ---------------
                                if (docPago.getNoAprobacion() != null) {
                                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                                            docPago.getNoAprobacion(), false);
                                }
                                if (docPago.getNoConsignacion() != null) {
                                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                                            docPago.getNoConsignacion(), false);
                                }
                                if (docPago.getFechaDocu() != null) {
                                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 13,
                                            docPago.getFechaDocu(), true);
                                }

                            } else {
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                                        "Tipo Pago", false);
                                this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                                        "Valor", true);
                                if(aPago.getDocumentoPago() != null){
                                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                                        aPago.getDocumentoPago().getTipoPago(), false);
                                } else {
                                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                                        tipoPagoGen, false);
                                }
                                if(aPago.getDocumentoPago() != null){
                                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                                        "$" + (long) aPago.getDocumentoPago().getValorDocumento(), true);
                                } else {
                                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                                        "$" + valorPago, true);
                                }
                            }
                            this.imprimirLinea(ImprimibleConstantes.PLANO, "");
                        }
                    }
                    tAnt = tAnt.getSolicitud().getTurnoAnterior();
                }
            }

        } else {
            List consignaciones = sol.getConsignaciones();
            for (int i = 0; i < consignaciones.size(); i++) {
                Consignacion cons = (Consignacion) consignaciones.get(i);

                numeral++;
                tipoPago = cons.getDocPago().getTipoPago();
                valorPago = (long) cons.getDocPago().getValorDocumento();
                saldo = (long) cons.getDocPago().getSaldoDocumento();
                total += valorPago;
                this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ,
                        "PAGO No. " + numeral + ": " + tipoPago, true);
                if (cons.getDocPago() instanceof DocPagoCheque) {
                    DocPagoCheque docPago = (DocPagoCheque) cons.getDocPago();
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                            "Valor", false);
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                            "Saldo", false);
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                            "Banco", false);
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                            "Cheque No.", false);
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 13,
                            "Fecha", true);

                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                            "$" + valorPago, false);
                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                            "$" + saldo, false);
                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                            docPago.getBanco().getNombre(), false);
                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                            docPago.getNoCheque(), false);
                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 13,
                            docPago.getFecha(), true);
                } else if (cons.getDocPago() instanceof DocPagoConsignacion) {
                    DocPagoConsignacion docPago = (DocPagoConsignacion) cons.getDocPago();
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                            "Valor", false);
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                            "Saldo", false);
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                            "Banco", false);
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                            "Consignación No.", false);
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, (ImprimibleConstantes.MARGEN_IZQ) * 13,
                            "Fecha", true);

                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 2,
                            "$" + valorPago, false);
                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                            "$" + saldo, false);
                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 6,
                            docPago.getBanco().getNombre(), false);
                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 10,
                            docPago.getNoConsignacion(), false);
                    this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 13,
                            docPago.getFecha(), true);
                }
                this.imprimirLinea(ImprimibleConstantes.PLANO, "");
            }
        }

        this.imprimirLinea(ImprimibleConstantes.PLANO, "TOTAL: $" + total);

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "DOCUMENTOS ANEXADOS:");

        //Obtener listado de Items de Chequeo e imprimirlos.
        List listaItems = sol.getCheckedItems();
        if (listaItems != null) {
            for (int q = 0; q < listaItems.size(); q++) {
                SolCheckedItemDev solChkItem = (SolCheckedItemDev) listaItems.get(q);
                if (solChkItem != null) {
                    CheckItemDev item = solChkItem.getCheckItem();
                    if (item != null) {
                        String tipoDocEntregado = "";
                        if (item.getNombre().trim().equals("DOCUMENTO")) {
                            tipoDocEntregado = sol.getComentario() != null ? ": " + sol.getComentario() : "";
                        }
                        this.imprimirLinea(ImprimibleConstantes.PLANO, item.getNombre() + tipoDocEntregado);
                    }

                }
            }

        }
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");

        String noFolios = Integer.toString(sol.getNumeroFolios());
        this.imprimirLinea(ImprimibleConstantes.TITULO2, ImprimibleConstantes.MARGEN_IZQ,
                "NUMERO DE FOLIOS: ", false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.MARGEN_IZQ) * 4,
                "" + noFolios, true);
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");

    }

    /**
     * Imprime el cuerpo del recibo de Mayor Valor.
     */
    private void imprimirCuerpoReciboMayorValor() {
        this.imprimirSolicitante(this.solicitud);
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

//        this.imprimirCanalRecaudo();
        this.imprimirFormaPago();

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
    }

    /**
     * Imprime el cuerpo del recibo de Correccion.
     */
    private void imprimirCuerpoReciboCorreccion() {
        this.imprimirMatriculasAsociadas();

        this.imprimirLinea(ImprimibleConstantes.PLANO, "SOLICITANTE: "
                + getNombreCompletoConId(this.solicitud.getCiudadano()));
        //TFS 4966: Se debe ingresar el número de teléfono del solicitante en la solicitud de corrección
        if (this.solicitud.getCiudadano() != null) {
            if (this.solicitud.getCiudadano().getTelefono() != null
                    && this.solicitud.getCiudadano().getTelefono().length() > 0) {
                this.imprimirLinea(ImprimibleConstantes.PLANO, "TELEFONO: "
                        + this.solicitud.getCiudadano().getTelefono());
            }
        }
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        List aplicacionesPagoTemp = null;
        boolean hayValorDocPago = false;
        if (pago.getAplicacionPagos() != null) {
            aplicacionesPagoTemp = pago.getAplicacionPagos();
            Iterator it = aplicacionesPagoTemp.iterator();
            while (it.hasNext()) {
                AplicacionPago ap = (AplicacionPago) it.next();
                if (ap.getValorAplicado() > 0) {
                    hayValorDocPago = true;
                }
            }
        }
        boolean pagoMayorValor = aplicacionesPagoTemp != null && hayValorDocPago;

        if (pagoMayorValor) {
            this.imprimirFormaPago_PMV_Correccion();
        } else {
            SolicitudCorreccion soliCorre = (SolicitudCorreccion) this.solicitud;
            String descripcion = ((soliCorre.getDescripcion() != null)
                    ? soliCorre.getDescripcion() : "");
            String comentario = ((soliCorre.getComentario() != null)
                    ? soliCorre.getComentario() : "");
            String direccionEnvio = ((soliCorre.getDireccionEnvio() != null)
                    ? soliCorre.getDireccionEnvio() : "");
            String turnoAnterior = (((soliCorre.getTurnoAnterior() != null)
                    && (soliCorre.getTurnoAnterior().getIdWorkflow() != null))
                    ? soliCorre.getTurnoAnterior().getIdWorkflow()
                    : "");

            String interesJur = ((soliCorre.getInteresJuridico() != null)
                    ? soliCorre.getInteresJuridico() : "");

            boolean derecho = soliCorre.getDerechoPeticion();
            String derechoPet = "NO";

            if (derecho) {
                derechoPet = "SI";
            }

            boolean solicitadaAnteriormente = soliCorre.isSolicitadaAnteriormente();
            String solicitadaAnteriormenteStr = "NO";

            if (solicitadaAnteriormente) {
                solicitadaAnteriormenteStr = "SI";
            }

            if (descripcion == null) {
                descripcion = "";
            }

            if (comentario == null) {
                comentario = "";
            }

            if (direccionEnvio == null) {
                direccionEnvio = "";
            }

            if (turnoAnterior == null) {
                turnoAnterior = "";
            }

            this.imprimirLinea(ImprimibleConstantes.TITULO2, "Datos Solicitud");
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "Turno Asociado: " + turnoAnterior);
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "Descripción: " + descripcion);
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "Comentario/Documentos Anexados: " + comentario);
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "Dirección de Correo: " + direccionEnvio);
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "Derecho de petición: " + derechoPet);
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "Solicitada con Anterioridad: " + solicitadaAnteriormenteStr);
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "Interés Jurídico: " + interesJur);

        }
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
    }

    /**
     * Imprime el encabezado de los recibos.
     */
    protected void makeNewPage() {

        //SI LA HOJA NO ES TAMAÑO CARTA SE DEFINE EL TAMAÑO PARA LA IMPRESIÓN
        if (!this.isTamanoCarta()) {
            Rectangle2D.Double newDimension;
            PageConfigBuilder pageConfigBuilder = new PersonalSize2PageConfigBuilder();// = new PersonalSize1PageConfigBuilder();
            Paper page = pageConfigBuilder.buildPageFormat().getPaper();
            newDimension = new Rectangle2D.Double(0, 0, page.getWidth(), page.getHeight());
            setAreaImprimibleDimension(newDimension);
        }

        setImprimirLogoEnabled(false);

        super.makeNewPage();

        String linea;
        String lineaCertificados;

        final String OFICINA_REGISTRO_TXT = "OFICINA DE REGISTRO DE INSTRUMENTOS PÚBLICOS";
        final int MAX_SIZE_HEADER_LINE = 61; //ImprimibleConstantes.MAX_NUM_CHAR_TITULO1
        final int MAX_SIZE_HEADER_LINE__NIT = MAX_SIZE_HEADER_LINE;
        /**
         * * (int)( ImprimibleConstantes.MAX_NUM_CHAR /
         * ImprimibleConstantes.MAX_NUM_CHAR_TITULO1 );
         */

        linea = StringFormat.getCentrada(
                OFICINA_REGISTRO_TXT,
                MAX_SIZE_HEADER_LINE, // ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);

        this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);

        String circuloReg = this.pago.getLiquidacion().getSolicitud()
                .getCirculo().getNombre();
        if (this.solicitud instanceof SolicitudRepartoNotarial) {
            if (this.pago != null && this.pago.getLiquidacion() != null && this.pago.getLiquidacion().getSolicitud() != null && this.pago.getLiquidacion().getSolicitud().getCirculo() != null && this.pago.getLiquidacion().getSolicitud().getCirculo().getOficinaOrigen() != null && this.pago.getLiquidacion().getSolicitud().getCirculo().getOficinaOrigen().getVereda() != null && this.pago.getLiquidacion().getSolicitud().getCirculo().getOficinaOrigen().getVereda().getMunicipio() != null && this.pago.getLiquidacion().getSolicitud().getCirculo().getOficinaOrigen().getVereda().getMunicipio().getIdMunicipio() != null && this.pago.getLiquidacion().getSolicitud().getCirculo().getOficinaOrigen().getVereda().getMunicipio().getNombre() != null) {
                circuloReg = circuloReg + " - MUNICIPIO : " + this.pago.getLiquidacion().getSolicitud().getCirculo().getOficinaOrigen().getVereda().getMunicipio().getIdMunicipio() + " " + this.pago.getLiquidacion().getSolicitud().getCirculo().getOficinaOrigen().getVereda().getMunicipio().getNombre();
            } else {
                circuloReg = circuloReg + " - MUNICIPIO: NO REGISTRADO";
            }
        }
        linea = StringFormat.getCentrada(circuloReg.toUpperCase(),
                MAX_SIZE_HEADER_LINE,
                ImprimibleConstantes.LONG_LOGO);

        // imprimir el NIT
        // ... para imprimir los otros NIT según el tipo de solicitud en esta "tecnología"
        if (this.solicitud instanceof SolicitudCertificado) {
            String nitOficina = StringFormat.printString(this.circulo.getNit());
            String textoNit = "NIT: " + nitOficina;
            textoNit = textoNit + "    " + circuloReg;
            textoNit = StringFormat.getCentrada(textoNit,
                    MAX_SIZE_HEADER_LINE__NIT, 12);
            this.imprimirLinea(ImprimibleConstantes.TITULO1, textoNit);
        } else if (this.solicitud instanceof SolicitudRegistro) {
            String nitOficina = StringFormat.printString(this.circulo.getNit());
            String textoNit = "NIT: " + nitOficina;
            textoNit = circuloReg + " - " + textoNit;
            textoNit = StringFormat.getCentrada(textoNit,
                    MAX_SIZE_HEADER_LINE, ImprimibleConstantes.LONG_LOGO);
            this.imprimirLinea(ImprimibleConstantes.TITULO1, textoNit);
        } else if (this.solicitud instanceof SolicitudConsulta) {
            this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);
            imprimirNitRecibo(this.circulo, MAX_SIZE_HEADER_LINE__NIT);
        } else if (this.solicitud instanceof SolicitudDevolucion) {
            this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);
            imprimirNitRecibo(this.circulo, MAX_SIZE_HEADER_LINE__NIT);
        } else if (this.solicitud instanceof SolicitudCorreccion) {
            this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);
            imprimirNitRecibo(this.circulo, MAX_SIZE_HEADER_LINE__NIT);
        } else if (this.solicitud instanceof SolicitudCertificadoMasivo) {
            this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);
            imprimirNitRecibo(this.circulo, MAX_SIZE_HEADER_LINE__NIT);
        } else if (this.solicitud instanceof SolicitudRepartoNotarial) {
            this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);
            imprimirNitRecibo(this.circulo, MAX_SIZE_HEADER_LINE__NIT);
        } else if (this.solicitud instanceof SolicitudRestitucionReparto) {
            this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);
            imprimirNitRecibo(this.circulo, MAX_SIZE_HEADER_LINE__NIT);
        }

        String titulo = this.getTitulo();
        this.imprimirLinea(ImprimibleConstantes.TITULO1,
                (int) (ImprimibleConstantes.MARGEN_IZQ * 6.0d - 1), titulo);

        //imprime la fecha y hora de impresion del recibo.
        String fechaImp = this.getFechaImpresion();
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                (int) (ImprimibleConstantes.MARGEN_IZQ * 6.0d - 1), fechaImp);

        /**
         * @author: Diana Lora
         * @change: 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto
         * Notarial Incluir el número de recibo.
         */
        /*if( this.solicitud instanceof SolicitudRepartoNotarial ) {
                    this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                            (int)(ImprimibleConstantes.MARGEN_IZQ * 10.0d),
                            ""+reciboMinutaRepartoNotarial, true);
                }*/
        //System.out.println( "@@XX:" + fechaImp );

        /* JAlcazar caso Mantis 0009055: Acta - Requerimiento No 019_151_Caratula - Reparto Notarial
                 * Se elimino el mensaje "Reimpreso por Corrección" en la reimpresión de los recibos de radicación de minutas.
         */
        // this.imprimirLinea(ImprimibleConstantes.PLANO,
        //				   ImprimibleConstantes.MARGEN_IZQ * 1, ""); // cambiado; tamaño izq (4)
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                ImprimibleConstantes.MARGEN_IZQ * 1, ""); // cambiado; tamaño izq (4)
    }

    public void imprimirNitRecibo(Circulo thisCirculo, int startPosition) {

        if (null != thisCirculo) {
            String nitOficina = StringFormat.printString(thisCirculo.getNit());

            //if (nitOficina != null) {
            String textoNit = "NIT: " + nitOficina;
            textoNit = StringFormat.getCentrada(textoNit,
                    //ImprimibleConstantes.MAX_NUM_CHAR
                    startPosition,
                    12);
            this.imprimirLinea(ImprimibleConstantes.TITULO2, textoNit);
            //}
        }
    }

    /**
     * Retorna el titulo del recibo dependiendo del tipo de solicitud.
     *
     * @return titulo del Recibo.
     */
    protected String getTitulo() {
        String titulo = "RECIBO DE PAGO";
        
        System.out.println("+++++++++++++++ titulo reimpresion ++++++++++++++");
        System.out.println("+++++++++++++++ titulo reimpresion ++++++++++++++");
        System.out.println("+++++++++++++++ titulo reimpresion ++++++++++++++");
        System.out.println("+++++++++++++++ titulo reimpresion ++++++++++++++");        

        if (this.esMayorValor) {
            titulo += " DE MAYORES VALORES Y DEVOLUCIONES";
        } else if (this.solicitud instanceof SolicitudCertificado) {
            if (isEsReciboCertificadoMayorValor()) {
                titulo = "RECIBO DE PAGO DE MAYORES VALORES CERTIFICADOS";
            } else {
                titulo = "SOLICITUD CERTIFICADO DE TRADICION";
            }
        } else if (this.solicitud instanceof SolicitudRegistro) {
            if (isEsReciboPagoMayorValorRegistro()) {
                titulo = "RECIBO DE PAGO DE MAYOR VALOR";
            } else {
                titulo = "SOLICITUD REGISTRO DOCUMENTOS";
                //titulo = "RECIBO DE PAGO DE MAYOR VALOR";----- SE REALIZO UN CAMBIO APARTE
            }
        } else if (this.solicitud instanceof SolicitudConsulta) {
            //titulo+=" DE CONSULTA";
            // titulo = "SOLICITUD CONSULTA INDICE DE PROPIETARIO";

            // Bug 3379
            if (pago != null && pago.getNumRecibo() != null) {
                titulo = "SOLICITUD CONSULTA                   " + pago.getNumRecibo();
            } else {
                titulo = "SOLICITUD CONSULTA";
            }

        } else if (this.solicitud instanceof SolicitudFotocopia) {
            titulo += " DE FOTOCOPIA";
        } else if (this.solicitud instanceof SolicitudDevolucion) {
            titulo = "CONSTANCIA DE SOLICITUD DE DEVOLUCIÓN DE DINERO";
        } else if (this.solicitud instanceof SolicitudCorreccion) {
            if (this.esMayorValorCorrecciones) {
                titulo += " DE MAYORES VALORES";
            } else {
                titulo = "SOLICITUD DE CORRECCION";
            }

        } else if (this.solicitud instanceof SolicitudCertificadoMasivo) {
            titulo = "";
        } else if (this.solicitud instanceof SolicitudRepartoNotarial) {
            /**
             * @author: Diana Lora
             * @change: Mantis 0010397: Acta - Requerimiento No 063_151 -
             * Ajustes Reparto Notarial Cambio de título por "RADICACIÓN
             * SOLICITUD REPARTO NOTARIAL".
             */
            titulo = "RADICACIÓN SOLICITUD REPARTO NOTARIAL";
        } else if (this.solicitud instanceof SolicitudRestitucionReparto) {
            titulo = "SOLICITUD DE RESTITUCIÓN DE TURNOS DE REPARTO NOTARIAL";
        }

        return titulo;
    }

    /**
     * Imprime los actos en el recibo.
     *
     * @param actos lista de actos a imprimir.
     */
    private void imprimirActos(List actos) {
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "ACTOS A REGISTRAR: ");

        Iterator itera = actos.iterator();

        Acto acto;
        String codActo;
        String valor;
        String impuesto;
        String cuantia;
        String tipo;
        String tipoTarifa;
        TipoActo tipoActo;
        int count = 0;
        //boolean flagMayor5 = false;
        boolean masActos = false;
        /*boolean mostrarMasActos = false;

		if (actos.size() >= 5) {
			mostrarMasActos = true;
		}*/

        this.imprimirLinea(ImprimibleConstantes.PLANO, "Tipo....", false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, 140, "Código....", false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, 210, "Cuantía....", false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, 259, "Tipo Tarifa", false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, 315, "Derecho", false);

        /*if (mostrarMasActos) {
			this.imprimirLinea(ImprimibleConstantes.PLANO, 280, "Impuesto", false);
			this.imprimirLinea(ImprimibleConstantes.PLANO, 334, "Tipo....", false);
			this.imprimirLinea(ImprimibleConstantes.PLANO, 449, "Cuantía....", false);
			this.imprimirLinea(ImprimibleConstantes.PLANO, 504, "Derecho", false);
			this.imprimirLinea(ImprimibleConstantes.PLANO, 556, "Impuesto", true);
		} else {*/
        this.imprimirLinea(ImprimibleConstantes.PLANO, 396, "Impuesto", true);
        //}

        while (itera.hasNext()) {
            acto = (Acto) itera.next();
            tipoActo = acto.getTipoActo();
            tipo = tipoActo.getNombre();
            tipo = (tipo.length() >= 19) ? (tipo.substring(0, 18) + "...")
                    : tipo;
            codActo = tipoActo.getIdTipoActo();
            cuantia = StringFormat.getNumeroFormateado(acto.getCuantia());
            tipoTarifa = acto.getTipoDerechoReg().getIdTipoDerechoReg();
            valor = StringFormat.getNumeroFormateado(acto.getValor());
            impuesto = StringFormat.getNumeroFormateado(acto.getValorImpuestos());

            /*if ((count >= 5)) {
				if (!flagMayor5) {
					this.moveToLineaAnterior(5);
					flagMayor5 = true;
				}

				this.imprimirLinea(ImprimibleConstantes.PLANO, 334, tipo, false);
				this.imprimirLinea(ImprimibleConstantes.PLANO, 449, cuantia,
								   false);
				this.imprimirLinea(ImprimibleConstantes.PLANO, 504, "$" + valor, false);
				this.imprimirLinea(ImprimibleConstantes.PLANO, 556, "$" + impuesto, true);


			} else {*/
            this.imprimirLinea(ImprimibleConstantes.PLANO, tipo, false);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 145, codActo, false);
            /**
             * Alineamiento de la cuantia y los derechos a la derecha
             */
            String cuantiaAux = StringFormat.inverso(cuantia);
            String valorAux = StringFormat.inverso("$" + valor);
            this.imprimirLineaHaciaIzquierda(cuantiaAux, 252);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 273, tipoTarifa,
                    false);
            this.imprimirLineaHaciaIzquierda(valorAux, 382);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 396, "$" + impuesto, true);

            //}

            /*
						 this.imprimirLinea(ImprimibleConstantes.PLANO,
						 (273 * (count / 5)) + 35,
						 tipo + "      " + cuantia + "       $ " + valor);
             */
        }
        this.imprimirLinea(ImprimibleConstantes.TAM_LETRAMENUDA, "");
        //this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
    }

    /**
     * Imprime una linea hacia la izquierda dada la posicion inicial del ultimo
     * digito
     *
     * @param cadena, posicion inicial
     */
    protected void imprimirLineaHaciaIzquierda(String cad, int pos) {
        int posicion = 0;
        while (posicion < cad.length()) {
            if (cad.substring(posicion, posicion + 1).equals(".")) {
                pos = pos + 2;
                this.imprimirLinea(ImprimibleConstantes.PLANO, pos, cad.substring(posicion, posicion + 1),
                        false);
                pos = pos - 5;
            } else if (cad.substring(posicion, posicion + 1).equals("$")) {
                this.imprimirLinea(ImprimibleConstantes.PLANO, 303, cad.substring(posicion, posicion + 1),
                        false);
            } else {
                this.imprimirLinea(ImprimibleConstantes.PLANO, pos, cad.substring(posicion, posicion + 1),
                        false);
                pos = pos - 4;
            }
            posicion++;
        }
    }

    /**
     * Retorna el nombre completo del ciudadano, tiene en cuenta el caso en que
     * alguno de los apellidos o el nombre es null, si el ciudadano es un
     * empresa --> solo imprime el apellido1 que corresponde a la razon social
     * de la empresa.
     *
     * @param ciudadano
     * @return una cadena con el nombre completo del ciudadano, en caso de que
     * alguno de los parametros del nombre sea null incluye una nota indicando
     * que no fue registrado el nombre o apellido.
     */
    protected String getNombreCompleto(Ciudadano ciudadano) {
        String completo = "";
        String ape1;
        String ape2;

        if (ciudadano != null) {
            completo = ciudadano.getNombre();
            ape1 = ciudadano.getApellido1();
            ape2 = ciudadano.getApellido2();
            System.out.println("Apellido 1: " + ape1);
            System.out.println("Apellido 2: " + ape2);

            //es una empresa.
            if (CCiudadano.TIPO_DOC_ID_NIT.equalsIgnoreCase(
                    ciudadano.getTipoDoc())) {
                if (ape1 == null) {
                    ape1 = ImprimibleConstantes.RAZON_SOCIAL_NULL;
                }

                completo = ape1;
            } else {
                if (ape1 == null) {
                    ape1 = ImprimibleConstantes.PRIMER_APELLIDO_NULL;
                }

                if (ape2 == null) {
                    ape2 = ImprimibleConstantes.SEGUNDO_APELLIDO_NULL;
                }

                if (completo == null) {
                    completo = "";
                }

                completo += (" " + ape1);
                completo += (" " + ape2);

                if (completo == null) {
                    completo = ImprimibleConstantes.NOMBRE_NULL;
                }
            }
        } else {
            System.out.println(
                    "[ImprimibleReciboCertificado.getNombreCompleto]:Ciudadano es null");
        }

        return completo;
    }

    /*Adiciona Funcionalidad Boton de Pago Author: Ingeniero Diego Hernadez Modificacion en: 23/02/2010 by jvenegas */
    /**
     * Imprime el encabezado del recibo. En particular la parte que es comun a
     * todos los recibos.
     */
    private void imprimirEncabezadoRecibo() {
        //imprimir el numero del recibo
        //this.imprimirLinea(ImprimibleConstantes.titulo2,380,"RECIBO DE CAJA No. ",false);
        String numRecibo = this.pago.getNumRecibo();

        if (numRecibo == null) {
            numRecibo = "";
        } else if (this.solicitud instanceof SolicitudCertificadoMasivo) {
            //if (!((LiquidacionTurnoCertificadoMasivo)this.pago.getLiquidacion()).getTipoTarifa().equals(CTipoTarifa.EXENTO)){
            //	numRecibo = "";
            //}
        }

        // Bug 0003623
        // para el caso del solicitudes de devoluciones
        // no se imprime el numero del recibo
        if ((solicitud instanceof SolicitudDevolucion)) {

            numRecibo = "";

        } // :if

        //En el caso de solicitudes de consulta 
        //no se imprime el numero de recibo aca, pues se imprimio en el encabezado del imprimible
        if ((solicitud instanceof SolicitudConsulta)) {
            numRecibo = "";
        }

        // posicion x del recibo.
        int X_POS_NUMRECIBO = 50;
        if (solicitud instanceof SolicitudCertificado) {
            X_POS_NUMRECIBO = 370;
        }

        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, X_POS_NUMRECIBO, numRecibo, true);

        /*//imprimir el tipo de solicitud de recibo.
		String titulo = this.getTitulo();
		this.imprimirLinea(ImprimibleConstantes.TITULO1,
							(int)(ImprimibleConstantes.MARGEN_IZQ * 4.0d ), titulo);

		//imprime la fecha y hora de impresion del recibo.
		String fechaImp = this.getFechaImpresion();
		this.imprimirLinea(ImprimibleConstantes.PLANO,
						   (int)(ImprimibleConstantes.MARGEN_IZQ * 4.0d), fechaImp);*/
        //System.out.println( "@@XX:" + fechaImp );
        //imprime el número de radicación.
        if (this.solicitud instanceof SolicitudCertificadoMasivo) {
            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                    (int) (ImprimibleConstantes.MARGEN_IZQ * 4.0d),
                    "No. DE SOLICITUD: ", false);
            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                    (int) (ImprimibleConstantes.MARGEN_IZQ * 7.0d),
                    this.turno.getIdWorkflow());
        } else {
            if (this.reimpreso) {
                this.imprimirLinea(ImprimibleConstantes.TITULO2,
                        (int) (ImprimibleConstantes.MARGEN_IZQ * 4.0d),
                        " REIMPRESO ");
            }

            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                    (int) (ImprimibleConstantes.MARGEN_IZQ * 4.0d),
                    "No. RADICACIÓN: ", false);

            final boolean esRupta = Rupta.liquidacionEsRupta((Liquidacion) pago.getLiquidacion());
            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                    (int) (ImprimibleConstantes.MARGEN_IZQ * 6.0d),
                    this.turno.getIdWorkflow() + (esRupta ? Rupta.SIGLA : ""));

            /**
             * @author : HGOMEZ
             *** @change : La siguiente porción de código permite presentar **
             * el tipo de Certificado en el imprimible del Recibo de Solicitud.
             * ** Caso Mantis : 11598
             */
            if (this.solicitud instanceof SolicitudCertificado && this.getTipoCertificadoNombre() != null
                    && (this.getTipoCertificadoNombre().equals("INMEDIATO")
                    || this.getTipoCertificadoNombre().equals("PERTENENCIA") || this.getTipoCertificadoNombre().equals("ANTIGUO_SISTEMA")
                    || this.getTipoCertificadoNombre().equals("NACIONAL") || this.getTipoCertificadoNombre().equals("AMPLIACION_TRADICION"))) {
                //deja una linea en blanco.
                this.imprimirLinea(ImprimibleConstantes.TAM_LETRAMENUDA, " ");

                this.imprimirLinea(ImprimibleConstantes.TITULO2,
                        "TIPO DE CERTIFICADO: " + this.getTipoCertificadoNombre().replace('_', ' '), false);

                //deja una linea en blanco.
                this.imprimirLinea(ImprimibleConstantes.TAM_LETRAMENUDA, " ");
            }

            //deja una linea en blanco.
            this.imprimirLinea(ImprimibleConstantes.TAM_LETRAMENUDA, " ");
        }
    }

    /**
     * Imprime las matriculas asociadas a una solicitud.
     *
     */
    private void imprimirMatriculasAsociadas() {
        String matriculas = "";

        // ------------------------------
        final int matriculasLimit = 5;
        boolean matriculasLimitEnabled = (this.solicitud instanceof SolicitudRegistro) & (true);

        int cantidadMatriculas = 0;

        //MAXIMO SE DEBE PERMITIR IMPRIMIR 5 MATRICULAS
        if (!this.solicitud.getSolicitudFolios().isEmpty() && cantidadMatriculas < 5) {

            String matriText = "MATRICULAS:";
            Iterator itSol = this.solicitud.getSolicitudFolios().iterator();
            boolean matriculaUnica = false;

            if ((this.solicitud instanceof SolicitudCertificado)
                    || (this.solicitud instanceof SolicitudCertificadoMasivo)) {
                if (this.solicitud instanceof SolicitudCertificado) {
                    matriText = "MATRICULA: ";
                    matriculaUnica = true;
                }

                this.imprimirLinea(ImprimibleConstantes.TITULO2, matriText,
                        false);

                SolicitudFolio solFo = (SolicitudFolio) itSol.next();

                this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, 100,
                        solFo.getIdMatricula());

                if (this.tipoCertificadoId != null) {
                    if (this.tipoCertificadoId.equals(
                            CTipoCertificado.TIPO_INMEDIATO_ID)) {
                        if (!mayorExtension) {
                            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                                    "CERTIFICADO(S) SE EXPIDE(N) DE INMEDIATO");
                        } else {
                            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                                    "EL CERTIFICADO ES DE MAYOR EXTENSIÓN");
                        }
                    } else {

                        /**
                         * @author : HGOMEZ
                         *** @change : La siguiente porción de código permite
                         * identificar ** el tipo de Certificado "Nacional" para
                         * generar un mensaje apropiado ** para el mismo en el
                         * imprimible del Recibo de Solicitud. ** Caso Mantis :
                         * 12263
                         */
                        if (this.tipoCertificadoId.equals("11")) {
                            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                                    "CERTIFICADO(S) SE EXPIDE(N) DE INMEDIATO");
                        } else {

                            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                                    "NO SE EXPIDE DE INMEDIATO EL CERTIFICADO");
                        }
                    }
                } else {
                    this.imprimirLinea(ImprimibleConstantes.TITULO2,
                            "NO SE EXPIDE DE INMEDIATO EL CERTIFICADO");
                }

                // this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
            }

            if (this.solicitud instanceof SolicitudConsulta) {
                matriculaUnica = true;

                SolicitudConsulta solCon = (SolicitudConsulta) this.solicitud;

                String tipoConsulta = solCon.getTipoConsulta().getNombre();

                if (tipoConsulta.equals(TipoConsulta.FOLIO)) {
                    int numFolios = this.solicitud.getSolicitudFolios().size();
                    matriText = "MATRICULA: ";

                    if (numFolios > 0) {
                        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                                matriText, false);

                        SolicitudFolio solFo = (SolicitudFolio) itSol.next();

                        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                                100, solFo.getFolio().getIdMatricula());
                    } else {
                        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                                matriText, false);
                        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                                100, MSG_MATRICULA_NO_ENCONTRADA);
                    }
                }

                // this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
            }

            if (!matriculaUnica) {
                List solicitudesFolios = this.solicitud.getSolicitudFolios();
                Iterator itSol2 = solicitudesFolios.iterator();

                if (solicitudesFolios != null) {
                    if (solicitudesFolios.size() > 0) {
                        SolicitudFolio solFo = (SolicitudFolio) itSol2.next();
                        matriculas = solFo.getIdMatricula();

                        boolean masMatriculas = false;
                        int count = 1;

                        while (itSol2.hasNext() && !masMatriculas) {
                            solFo = (SolicitudFolio) itSol2.next();
                            matriculas += (", " + solFo.getIdMatricula());
                            count++;

                            if ((count >= (matriculasLimit)) && (matriculasLimitEnabled)) {
                                masMatriculas = true;
                                break;
                            } else if (count >= 10) {
                                masMatriculas = true;
                            }
                        }

                        if (masMatriculas) {
                            if (itSol2.hasNext()) {
                                matriculas = matriculas + " (existen más matrículas).";
                            }
                        }
                    }
                }

                this.imprimirLinea(ImprimibleConstantes.TITULO1,
                        matriText + "   " + matriculas);
            }
        } else {

            if (this.solicitud instanceof SolicitudCertificado && this.isTienematriculasSinMigrar()) {
                String matriText = "MATRICULA: ";

                this.imprimirLinea(ImprimibleConstantes.TITULO2, matriText, false);

                this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, 100, this.matriculaSinMigrar);
            }

            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                    "NO SE EXPIDE DE INMEDIATO EL CERTIFICADO");
        }
        cantidadMatriculas++;
    }

    /*Adiciona Funcionalidad Boton de Pago Author: Ingeniero Diego Hernadez Modificacion en: 23/02/2010 by jvenegas */
    /**
     * Imprime las matriculas asociadas a una solicitud.
     *
     */
    private void imprimirDatosCertificadosMasivos() {
        String matriculas = "";
        String nCertAsociados = "0";
        String nCertAsociadosText = "CANTIDAD DE CERTIFICADOS SOLICITADOS: ";
        String nCertExpedidos = "0";
        String nCertExpedidosText = "CANTIDAD DE CERTIFICADOS EXPEDIDOS: ";
        String nCertDevueltas = "0";
        String nCertDevueltasText = "CANTIDAD DE CERTIFICADOS DEVUELTOS: ";
        String nDineroRecaudado = "0";
        String nnDineroRecaudadoText = "Total dinero recaudado: ";

        SolicitudCertificadoMasivo solicitudMasiva = (SolicitudCertificadoMasivo) this.solicitud;
        List solicitudesHijas = solicitudMasiva.getSolicitudesHijas();

        if (validaciones == null) {
            validaciones = new Hashtable();
        }

        if (validaciones != null) {
            int nAsociados = 0;
            for (Iterator iter = solicitudMasiva.getSolicitudesHijas().iterator(); iter.hasNext();) {
                SolicitudAsociada solicitudA = (SolicitudAsociada) iter.next();
                SolicitudCertificado solHija = (SolicitudCertificado) solicitudA.getSolicitudHija();
                nAsociados = nAsociados + solHija.getNumeroCertificados();
            }
            //int nAsociados = solicitudMasiva.getSolicitudesHijas().size();
            nCertAsociados = Integer.toString(nAsociados);
            System.out.println("***** Nuevo valor de solicitudes asociadas ="
                    + nCertAsociados);

            int nDevueltas = 0;

            if (!validaciones.isEmpty()) {
                nDevueltas = validaciones.size();
                nCertDevueltas = Integer.toString(nDevueltas);
                System.out.println(
                        "***** Nuevo valor de solicitudes devueltas ="
                        + nCertDevueltas);
            }

            nCertExpedidos = Integer.toString(nAsociados - nDevueltas);
            System.out.println("***** Nuevo valor de solicitudes expedidos ="
                    + nCertDevueltas);

            List ls = solicitudMasiva.getLiquidaciones();

            if ((ls != null) || (ls.size() > 0)) {
                LiquidacionTurnoCertificadoMasivo l = (LiquidacionTurnoCertificadoMasivo) ls.get(0);

                if (l.getPago() != null) {
                    List apagos = l.getPago().getAplicacionPagos();
                    double valor = l.getValor();
                    nDineroRecaudado = Double.toString(valor);
                    System.out.println(
                            "***** Nuevo valor de dinero recaudado ="
                            + nDineroRecaudado);
                }
            }
        }

        /**
         * @author : Julio Alcázar Rivas
         * @change : Eliminados dos espacios de lineas par formato del recibo
         * Caso Mantis : 000941
         */
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        System.out.println("*****" + nCertAsociadosText + nCertAsociados);
        this.imprimirLinea(ImprimibleConstantes.PLANO, nCertAsociadosText, false);
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                ImprimibleConstantes.MARGEN_IZQ * 6, nCertAsociados);
        System.out.println("*****" + nCertExpedidosText + nCertExpedidos);
        this.imprimirLinea(ImprimibleConstantes.PLANO, nCertExpedidosText, false);
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                ImprimibleConstantes.MARGEN_IZQ * 6, nCertExpedidos);
        System.out.println("*****" + nCertDevueltasText + nCertDevueltas);
        this.imprimirLinea(ImprimibleConstantes.PLANO, nCertDevueltasText, false);
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                ImprimibleConstantes.MARGEN_IZQ * 6, nCertDevueltas);
    }

    /**
     * Imprime los parametros de busqueda de la consulta.
     *
     * @param solConsul
     */
    private void imprimirBusquedas(SolicitudConsulta solConsul) {
        List busquedas = solConsul.getBusquedas();

        int n = busquedas.size();

        if (n > 0) {
            Busqueda busqueda;

            for (int j = 1; j <= n; j++) {
                busqueda = (Busqueda) busquedas.get(j - 1);

                String param = busqueda.getApellido1Ciudadano();
                param = this.getString(param);

                if (!param.equalsIgnoreCase("")) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "   Primer Apellido: " + param);
                }

                param = busqueda.getApellido2Ciudadano();
                param = getString(param);

                if (!param.equalsIgnoreCase("")) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "   Segundo Apellido: " + param);
                }

                param = busqueda.getDireccion();
                param = this.getString(param);

                if (!param.equalsIgnoreCase("")) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "   Dirección: " + param);
                }

                param = busqueda.getMatricula();
                param = this.getString(param);

                if (!param.equalsIgnoreCase("")) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "   Matrícula: " + param);
                }

                param = busqueda.getNombreCiudadano();
                param = this.getString(param);

                if (!param.equalsIgnoreCase("")) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "   Nombre: " + param);
                }

                param = busqueda.getNombreNaturalezaJuridica();
                param = this.getString(param);

                if (!param.equalsIgnoreCase("")) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "   Nombre-Naturaleza Juridica: "
                            + param);
                }

                param = busqueda.getNumeroCatastral();
                param = this.getString(param);

                if (!param.equalsIgnoreCase("")) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "   Número Catastral: " + param);
                }

                param = busqueda.getTipoDocCiudadano();
                param = this.getString(param);

                if (!param.equalsIgnoreCase("")) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "   Tipo de Documento: " + param);
                }

                param = busqueda.getNumeroDocCiudadano();
                param = this.getString(param);

                if (!param.equalsIgnoreCase("")) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "   Número de Documento: " + param);
                }
            }
        }

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
    }

    /**
     * Imprime el nombre del solicitante.
     *
     * @param sol La solicitud.
     */
    protected void imprimirSolicitante(Solicitud sol) {

        SolicitudDevolucion solDev = null;
        Solicitante solicitante = null;
        List solicitantes = null;
        Ciudadano ciud = null;

        String apellido1 = null;
        String direccion = null;
        String completo = null;
        String telefono = null;
        String correo = null;

        if (sol instanceof SolicitudDevolucion) {
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "SOLICITANTE(S)");
            solDev = (SolicitudDevolucion) sol;
            solicitantes = solDev.getSolicitantes();

            for (int i = 0; i < solicitantes.size(); i++) {

                solicitante = (Solicitante) solicitantes.get(i);
                ciud = solicitante.getCiudadano();
                apellido1 = ciud.getApellido1();
                telefono = ciud.getTelefono();

                if (apellido1 == null) {
                    apellido1 = "";
                }

                direccion = null;
                correo = null;

                if (this.isTurnoInternet()) {
                    direccion = ciud.getNombre();
                    correo = ciud.getApellido2();
                    ciud.setApellido1(apellido1);
                    ciud.setApellido2("");
                    ciud.setNombre("");
                }

                completo = this.getNombreCompletoConId(ciud);

                this.imprimirLinea(ImprimibleConstantes.PLANO, "NOMBRE: "
                        + completo);

                if (direccion != null) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "DIRECCION: " + direccion);
                }
                if (correo != null) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "CORREO ELECTRONICO: " + correo);
                }
                if (telefono != null) {
                    this.imprimirLinea(ImprimibleConstantes.PLANO,
                            "TELÉFONO: " + telefono);
                }
            }
        } else {

            ciud = sol.getCiudadano();
            apellido1 = ciud.getApellido1();
            telefono = ciud.getTelefono();
            completo = this.getNombreCompletoConId(ciud);
            direccion = null;
            correo = null;

            if (apellido1 == null) {
                apellido1 = "";
            }

            if (this.isTurnoInternet()) {
                direccion = ciud.getNombre();
                correo = ciud.getApellido2();
                ciud.setApellido1(apellido1);
                ciud.setApellido2("");
                ciud.setNombre("");
            }

            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "NOMBRE DEL SOLICITANTE: " + completo);

            if (direccion != null) {
                this.imprimirLinea(ImprimibleConstantes.PLANO,
                        "DIRECCIÓN DEL SOLICITANTE: " + direccion);
            }
            if (correo != null) {
                this.imprimirLinea(ImprimibleConstantes.PLANO,
                        "CORREO ELECTRONICO DEL SOLICITANTE: " + correo);
            }
            if (telefono != null) {
                this.imprimirLinea(ImprimibleConstantes.PLANO,
                        "TELÉFONO: " + telefono);
            }
        }
    }

    /**
     * Imprime los datos de la escritura
     *
     * @param sol
     */
    private void imprimirEscritura(SolicitudRegistro sol) {
        Documento doc = sol.getDocumento();

        if (doc != null) {
            TipoDocumento tipoDocum = doc.getTipoDocumento();
            String tipoDoc = tipoDocum.getNombre();

            String numero = doc.getNumero();

            String fecha = this.getFecha(doc.getFecha());

            String linea = "";
            if (doc.getComentario() != null) {
                String nomOficina = doc.getComentario();
                linea = tipoDoc + " No.: " + numero + " del " + fecha + " " + " de " + nomOficina;

            } else if (doc.getOficinaInternacional() == null) {
                OficinaOrigen oficOrig = doc.getOficinaOrigen();

                String nombreOficina = oficOrig.getNombre();

                String nombCompleto = "";

                if (nombreOficina != null) {
                    nombCompleto = nombreOficina;
                }

                String vereda = doc.getOficinaOrigen().getVereda().getNombre();

                linea = tipoDoc + " No.: " + numero + " del " + fecha + " "
                        + nombCompleto + " " + " de " + vereda;
            } else {
                String nomOficina = doc.getOficinaInternacional();
                linea = tipoDoc + " No.: " + numero + " del " + fecha + " "
                        + " de " + nomOficina;
            }
            this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
        } else {
            System.out.println("Documento Nulo");
        }
    }

    /**
     * Imprime los datos de la escritura
     *
     * @param sol
     */
    private void imprimirDocumento(Documento doc) {

        if (doc != null) {
            TipoDocumento tipoDocum = doc.getTipoDocumento();
            String tipoDoc = tipoDocum.getNombre();

            String numero = doc.getNumero();

            String fecha = this.getFecha(doc.getFecha());

            String linea = "";
            if (doc.getComentario() != null) {
                String nomOficina = doc.getComentario();
                linea = tipoDoc + " No.: " + numero + " del " + fecha + " " + " de " + nomOficina;

            } else if (doc.getOficinaInternacional() == null) {
                OficinaOrigen oficOrig = doc.getOficinaOrigen();

                String nombreOficina = oficOrig.getNombre();

                String nombCompleto = "";

                if (nombreOficina != null) {
                    nombCompleto = nombreOficina;
                }

                String vereda = doc.getOficinaOrigen().getVereda().getNombre();

                linea = tipoDoc + " No.: " + numero + " del " + fecha + " "
                        + nombCompleto + " " + " de " + vereda;
            } else {
                String nomOficina = doc.getOficinaInternacional();
                linea = tipoDoc + " No.: " + numero + " del " + fecha + " "
                        + " de " + nomOficina;
            }
            this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
        } else {
            System.out.println("Documento Nulo");
        }
    }

    /**
     * Imprime el usuario que radicó la solicitud.
     */
    private void imprimirUsuario() {
        Usuario usuario = this.solicitud.getUsuario();

        if (usuario != null) {
            String funcionario = usuario.getNombre();

            if (funcionario != null) {
                this.imprimirLinea(ImprimibleConstantes.PLANO,
                        "RADICADO POR: " + funcionario);
            }
        }
    }

    /**
     * Imprime el usuario que resgistro la solicitud.
     */
    private void imprimirUsuarioRegistrado() {
        Usuario usuario = this.solicitud.getUsuario();

        if (usuario != null) {
            String funcionario = usuario.getNombre();

            if (funcionario != null) {
                this.imprimirLinea(ImprimibleConstantes.PLANO,
                        "REGISTRADO POR: " + funcionario);
            }
        }
    }

    /**
     * Imprime la forma de pago del recibo, discriminando entre campos de
     * impuestos y registro. Se usa para pagos mayor valor cuando vienen de
     * corrección.
     *
     */
    protected void imprimirFormaPago_PMV_Correccion() {
        //Imprimir las aplicaciones de pago.
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "FORMA DE PAGO:");

        List aplicaciones = pago.getAplicacionPagos();
        DocumentoPago docPago;
        String line = "";
        String numRadAsociada = null;

        if (numRadAsociada == null) {
            if (aplicaciones != null) {
                Iterator it = aplicaciones.iterator();
                while (it.hasNext()) {
                    AplicacionPago ap = (AplicacionPago) it.next();
                    docPago = ap.getDocumentoPago();
                    line = "     " + docPago.getTipoPago();

                    if (docPago instanceof DocPagoCheque) {
                        DocPagoCheque doc = (DocPagoCheque) docPago;
                        if (this.solicitud instanceof SolicitudCertificado) {
                            if (doc.getBanco() != null && doc.getBanco().getIdBanco() != null) {
                                line += ("     BANCO: " + doc.getBanco().getIdBanco());
                            }
                        } else {
                            if (doc.getBanco() != null && doc.getBanco().getNombre() != null && doc.getBanco().getNombre().trim() != null) {
                                line += ("     BANCO: " + doc.getBanco().getNombre().trim());
                            }
                        }
                        //line += ("     CUENTA: " + doc.getNoCuenta());
                        line += ("     Nro: " + doc.getNoCheque());
                    } else if (docPago instanceof DocPagoConsignacion) {
                        DocPagoConsignacion doc = (DocPagoConsignacion) docPago;
                        if (this.solicitud instanceof SolicitudCertificado) {
                            if (doc.getBanco() != null && doc.getBanco().getIdBanco() != null) {
                                line += ("     BANCO: " + doc.getBanco().getIdBanco());
                            }
                        } else {
                            if (doc.getBanco() != null && doc.getBanco().getNombre() != null && doc.getBanco().getNombre().trim() != null) {
                                line += ("     BANCO: " + doc.getBanco().getNombre().trim());
                            }
                        }
                        //       line += ("     CUENTA: " + doc.getNoCuenta());
                        line += ("     Nro: "
                                + doc.getNoConsignacion());
                    }

                    line += ("     VALOR: $ "
                            + StringFormat.getNumeroFormateado(ap.getValorAplicado()));

                    this.imprimirLinea(ImprimibleConstantes.PLANO, line);
                }
            }
        }

        //this.imprimirLinea(ImprimibleConstantes.PEQUE, "");
        Liquidacion liq2 = pago.getLiquidacion();
        double valorT = 0;
        LiquidacionTurnoCorreccion liq = (LiquidacionTurnoCorreccion) liq2;
        String valorDerechos = StringFormat.getNumeroFormateado(liq.getValorDerechos());
        String valorImpuestos = StringFormat.getNumeroFormateado(liq.getValorImpuestos());
        this.imprimirLinea(ImprimibleConstantes.PLANO, " VALOR DERECHOS  :$" + valorDerechos);
        this.imprimirLinea(ImprimibleConstantes.PLANO, " VALOR IMPUESTOS :$" + valorImpuestos);

        valorT = liq.getValorDerechos();
        valorT += liq.getValorImpuestos();
        double valorCertificadosAsociados = liq.getValor() - valorT;
        printRegion_ValorCertificadosAsociados(valorCertificadosAsociados);
        valorT += valorCertificadosAsociados;
        String valorTotal = StringFormat.getNumeroFormateado(valorT);

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "VALOR TOTAL: $ ",
                false);
        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, 105, valorTotal);

        //this.imprimirLinea(ImprimibleConstantes.tituloGrande, "");
    }

    protected void imprimirCanalRecaudo() {
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "CANAL DE RECAUDO: " + "PRUEBA LUNES 22 ABRIL");
    }

    /**
     * Imprime la forma de pago del recibo.
     *
     */
    protected void imprimirFormaPago() {
        //Imprimir las aplicaciones de pago.
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "FORMA DE PAGO:");

        List aplicaciones = pago.getAplicacionPagos();
        DocumentoPago docPago;
        String line = "";
        String numRadAsociada = null;
        boolean isCertificadodeMasivo = false;

        // Region :: Impresion asociacion pago ---------------------------------------------------------------------------------------------
        //      SI LA SOLICITUD CERTIFICADO ES HIJA DE UNA SOLICITUD REGISTRO
        if (this.solicitud instanceof SolicitudCertificado) {
            Solicitud solPadre = this.getNumeroRadicacionAsociadoAlTurnoDeRegistro((SolicitudCertificado) this.solicitud);
            if (solPadre != null) {
                Turno turnoPadre = solPadre.getTurno();
                numRadAsociada = turnoPadre.getIdWorkflow();

                if (solPadre instanceof SolicitudRegistro) {
                    numRadAsociada = null;
                }

                if (solPadre instanceof SolicitudCertificadoMasivo) {
                    String msgPadre = "Asociado al turno de Certificado Masivos: ";
                    int cord_x_padre = ImprimibleConstantes.MARGEN_IZQ * 8;
                    //cord_x_padre = ImprimibleConstantes.MARGEN_IZQ * 7 + 22;
                    line = "     PAGO ASOCIADO AL TURNO: ";
                    line += numRadAsociada;
                    this.imprimirLinea(ImprimibleConstantes.PLANO, line);
                    isCertificadodeMasivo = true;
                    if (pagoMasivo.getAplicacionPagos() != null) {
                        aplicaciones = pagoMasivo.getAplicacionPagos();
                    }
                }

            }
        }
        // --------------------------------------------------------------------------------------------------------------------------------

        // Region :: Impresion formas de pago ---------------------------------------------------------------------------------------------
        if (numRadAsociada == null || isCertificadodeMasivo) {
            if (aplicaciones != null) {
                Iterator it = aplicaciones.iterator();
                while (it.hasNext()) {
                    AplicacionPago ap = (AplicacionPago) it.next();
                    docPago = ap.getDocumentoPago();

                                        if( "CONSIGNACIÓN".equals( docPago.getTipoPago() ) ) {
                        line = "     " + "CONSIG.";
                                        }
                                        else
                                        {
                        line = "     " + docPago.getTipoPago();
                                        }
                    
                        if (docPago instanceof DocPagoCheque) {
                        DocPagoCheque doc = (DocPagoCheque) docPago;
                        if (this.solicitud instanceof SolicitudCertificado) {
                            if (doc.getBanco() != null && doc.getBanco().getIdBanco() != null) {
                                line += ("     BANCO: " + doc.getBanco().getIdBanco());
                            }
                        } else {
                            if (doc.getBanco() != null && doc.getBanco().getNombre() != null && doc.getBanco().getNombre().trim() != null) {
                                line += ("     BANCO: " + doc.getBanco().getNombre().trim());
                            }
                        }
                        line += ("     Nro DOC: " + doc.getNoCheque());
                    } else if (docPago instanceof DocPagoConsignacion) {
                        DocPagoConsignacion doc = (DocPagoConsignacion) docPago;
                        if (this.solicitud instanceof SolicitudCertificado) {
                            if (doc.getBanco() != null && doc.getBanco().getIdBanco() != null) {
                                line += ("     BANCO: " + doc.getBanco().getIdBanco());
                            }
                        } else {
                           if (doc.getBanco() != null && doc.getBanco().getNombre() != null && doc.getBanco().getNombre().trim() != null) { 
                                line += ("     BANCO: " + doc.getBanco().getNombre().trim());
                           }
                        }
                        //       line += ("     CUENTA: " + doc.getNoCuenta());
                        line += ("     Nro DOC: "
                                + doc.getNoConsignacion());
                        line += ("     FECHA CONSIG.: "
                                + doc.getFecha());
                    } else if (docPago instanceof DocPagoGeneral) {

                        DocPagoGeneral doc = (DocPagoGeneral) docPago;
                        line += "    " + doc.getNombreCanal();
                        if (this.solicitud instanceof SolicitudCertificado) {
                            if (doc.getBanco() != null && doc.getBanco().getIdBanco() != null) {
                                line += ("     BANCO: " + doc.getBanco().getIdBanco());
                            }
                        } else {
                            if (doc.getBanco() != null && doc.getBanco().getNombre() != null) {
                                line += ("     BANCO: " + doc.getBanco().getNombre());
                            }
                        }

                        //------------------ nueva linea para mostrar el numero PIN ---------------
                        if (doc.getNoAprobacion() != null && !doc.getNoAprobacion().equals("")) {
                            line += ("     Nro DOC: " + doc.getNoAprobacion());
                        }

                        if (doc.getNoConsignacion() != null && !doc.getNoConsignacion().equals("")) {
                            line += ("     Nro DOC: " + doc.getNoConsignacion());
                        }
                        
                        if (doc.getFechaDocu() != null && !doc.getFechaDocu().equals("")) {
                            line += ("     FECHA: " + doc.getFechaDocu());
                        }
                    }

                    if (ap.getValorAplicado() < docPago.getValorDocumento()) {
                        line += ("  VALOR PAGADO: $"
                                + StringFormat.getNumeroFormateado(ap.getValorAplicado()));

                        line += ("  VALOR DOC.: $"
                                + StringFormat.getNumeroFormateado(docPago.getValorDocumento()));
                    } else {
                        line += ("  VALOR: $ "
                                + StringFormat.getNumeroFormateado(ap.getValorAplicado()));
                    }

                    this.imprimirLinea(ImprimibleConstantes.PLANO, line);
                }
            }
        }

        // ---------------------------------------------------------------------------------------------
        //this.imprimirLinea(ImprimibleConstantes.PEQUE, "");
        Liquidacion liq2 = pago.getLiquidacion();

        // Region :: calculo de valor total -----------------------------------------------------------------------
        // imprime derechos e impuestos si se necesita
        double valorT = 0;
        double conservacionDocumental = 0;
        if (liq2 instanceof LiquidacionTurnoRegistro) {
            LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro) liq2;

            conservacionDocumental = liq.getValorConservacionDoc();
            double valorDerechos = liq.getValorDerechos();
            double valorImpuestos = liq.getValorImpuestos();

            valorT = valorDerechos;
            valorT += valorImpuestos;
            valorT += liq.getValorMora();
            valorT += liq.getValorOtroImp();
            valorT += conservacionDocumental;

            // Imprimir Valores Derechos/Impuestos
            printRegion_DerechosImpuestos(valorDerechos, valorImpuestos);

            /**
             * PARA MAYOR VALOR EL RECIBO MUESTRA LOS VALORES DE CERTIFICADO
             */
            if (this.isEsReciboPagoMayorValorRegistro()) {
                // Calcular el valor de los certificados asociados
                double valorCertificadosAsociados;

                valorCertificadosAsociados = liq.getValor() - valorT;
                //System.out.println( "[@@$]" + "val cert asoc=" + valorCertificadosAsociados );
                printRegion_ValorCertificadosAsociados(valorCertificadosAsociados);

                // sumar tambien el valor de los certificados asociados.
                valorT += valorCertificadosAsociados;
            }

        } else if (liq2 instanceof LiquidacionTurnoCorreccion) {
            LiquidacionTurnoCorreccion liq = (LiquidacionTurnoCorreccion) liq2;

            double valorDerechos = liq.getValorDerechos();
            double valorImpuestos = liq.getValorImpuestos();
            conservacionDocumental = liq.getValorConservacionDoc();

            valorT = valorDerechos;
            valorT += valorImpuestos;
            valorT += conservacionDocumental;

            // String valorDerechos =StringFormat.getNumeroFormateado(liq.getValorDerechos());
            // String valorImpuestos=StringFormat.getNumeroFormateado(liq.getValorImpuestos());
            // this.imprimirLinea(ImprimibleConstantes.PLANO, "");
            // this.imprimirLinea(ImprimibleConstantes.PLANO, "VALOR DERECHOS  :$"+valorDerechos);
            // this.imprimirLinea(ImprimibleConstantes.PLANO, "VALOR IMPUESTOS :$"+valorImpuestos);
            // Imprimir Valores Derechos/Impuestos
            printRegion_DerechosImpuestos(valorDerechos, valorImpuestos);

            /**
             * Para el caso de mayor valor en un turno de correcciones se
             * solicita el valor de los certificados, por lo tanto se debe
             * reflejar en el recibo de pago
             */
            double valorCertificadosAsociados = 0;
            valorCertificadosAsociados = liq.getValor() - valorT;
            printRegion_ValorCertificadosAsociados(valorCertificadosAsociados);

//                       sumar tambien el valor de los certificados asociados.
            valorT += valorCertificadosAsociados;

        } else {
            valorT = liq2.getValor();
        }

        // -----------------------------------------------------------------------
        // Region :: Impresion Valor Total -----------------------------------------
        String valorTotal = StringFormat.getNumeroFormateado(valorT);
        String valorTotalConservacion = StringFormat.getNumeroFormateado(conservacionDocumental);
        /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                 * Se coloca el texto "VALOR TOTAL AJUSTADO A LA CENTENA POR ACTOS " para los tipos de solicitud SolicitudConsulta,
                 SolicitudCertificado, SolicitudCertificadoMasivo y Pagos Mayor Valor Correciones, y se coloca el texto" VALOR TOTAL A PAGAR AJUSTADO A LA CENTENA POR ACTOS:
                 para el tipo de solicitud SolicitudRegistro.
         */
        if (solicitud instanceof SolicitudConsulta || solicitud instanceof SolicitudCertificado
                || solicitud instanceof SolicitudCertificadoMasivo || this.esMayorValorCorrecciones) {
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "VALOR TOTAL AJUSTADO A LA CENTENA POR ACTOS: $ ",
                    false);
            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, 260, valorTotal);
        } else if (solicitud instanceof SolicitudRegistro) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 500, "", true);
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "Conservaci\u00f3n documental del 2%", false);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 190, "$", false);
            this.imprimirLineaHaciaIzquierda(StringFormat.inverso(valorTotalConservacion), 260 - 15);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 500, "", true);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 500, "", true);
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "VALOR TOTAL A PAGAR AJUSTADO A LA CENTENA POR ACTOS: $ ",
                    false);
            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, 300, valorTotal);
        } else {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 500, "", true);
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "Conservaci\u00f3n documental del 2%", false);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 190, "$", false);
            this.imprimirLineaHaciaIzquierda(StringFormat.inverso(valorTotalConservacion), 260 - 15);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 500, "", true);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 500, "", true);
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "VALOR TOTAL: $ ", false);
            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, 105, valorTotal);
        }

        // ------------------------------------------------------------------------
        //this.imprimirLinea(ImprimibleConstantes.tituloGrande, "");
        //TFS 3851: SI ES UN RECIBO DE PAGO DE MAYOR VALOR, SE IMPRIME LA JUSTIFICACION DEL PAGO DE MAYOR VALOR
        if (this.isEsReciboPagoMayorValorRegistro() && this.getJustificacionMayorValor() != null
                && this.getJustificacionMayorValor().length() > 0) {
            this.imprimirJustificacionMayorValor();
        }
    }

    private void imprimirJustificacionMayorValor() {
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "JUSTIFICACION PAGO MAYOR VALOR: ",
                true);
        this.imprimirLinea(ImprimibleConstantes.PLANO, this.getJustificacionMayorValor(),
                true);
    }

    /**
     * Retorna el numero de radicicion asociado a la solicitud de certificado.
     *
     * @param sol
     * @return solicitud Padre
     */
    private Solicitud getNumeroRadicacionAsociadoAlTurnoDeRegistro(
            SolicitudCertificado sol) {
        List padres = sol.getSolicitudesPadres();

        Solicitud rta = null;

        if (padres.size() > 0) {
            SolicitudAsociada solAsociada = (SolicitudAsociada) padres.get(0);
            rta = solAsociada.getSolicitudPadre();
        }

        return rta;
    }

    /**
     * devuelve true si la cadena recibida como parametro es no nula o no vacia
     * (es buena). devuelve false si la cadena es inutil.
     *
     *
     */
    public boolean testNotNullOrNotEmptyString(String _source, boolean treatNullAsBlank, boolean trimEnabled, boolean ignoreBreaks) {

        if (treatNullAsBlank) {
            _source = (null == _source) ? ("") : (_source);
        }

        if (ignoreBreaks) {

            _source = filterBreaks(_source);
        }
        if (trimEnabled) {
            _source = _source.trim();
        }

        final String PATTERN = "";
        int comparison = PATTERN.compareTo(_source);

        return (comparison != 0);
    }

    public static String filterBreaks(String string) {

        if (null == string) {
            return null;
        }

        String result = string;

        result = result.replaceAll("\n", "");
        result = result.replaceAll("\r", "");

        return result;

    }

    /**
     * Pasar a una representacion lineal de la estructura
     *
     *
     */
    protected static String datosAntiguoSistema_GetValue(DatosAntiguoSistema datos, String propertyName) {
        if (null == datos) {
            return null;
        }

        if ("idDatosAntiguoSistema".equals(propertyName)) {
            return datos.getIdDatosAntiguoSistema();
        } else if ("libroTipo".equals(propertyName)) {
            return datos.getLibroTipo();
        } else if ("libroNumero".equals(propertyName)) {
            return datos.getLibroNumero();
        } else if ("libroPagina".equals(propertyName)) {
            return datos.getLibroPagina();
        } else if ("libroAnio".equals(propertyName)) {
            return datos.getLibroAnio();
        } else if ("tomoNumero".equals(propertyName)) {
            return datos.getTomoNumero();
        } else if ("tomoPagina".equals(propertyName)) {
            return datos.getTomoPagina();
        } else if ("tomoMunicipio".equals(propertyName)) {
            return datos.getTomoMunicipio();
        } else if ("tomoAnio".equals(propertyName)) {
            return datos.getTomoAnio();
        } else if ("comentario".equals(propertyName)) {
            return datos.getComentario();
        } else if ("documento".equals(propertyName)) {
            Documento documento = datos.getDocumento();
            if (null == documento) {
                return null;
            }
            return documento.getComentario();
        } else if ("fechaCreacion".equals(propertyName)) {
            Date fechaCreacion = datos.getFechaCreacion();
            if (null == fechaCreacion) {
                return null;
            }
            return fechaCreacion.toString();
        } else {
            return null;
        }

    }

    /**
     * Imprime los datos de antiguo sistema.
     */
    private void imprimirDatosAntiguoSistema() {
        DatosAntiguoSistema datos = this.solicitud.getDatosAntiguoSistema();

        if (datos != null) {

            int okTests;

            okTests = 0;

            String testString;

            // tests :
            testString = datosAntiguoSistema_GetValue(datos, "libroAnio");
            if (testNotNullOrNotEmptyString(testString, true, true, true)) {
                okTests++;
            }
            testString = datosAntiguoSistema_GetValue(datos, "libroNumero");
            if (testNotNullOrNotEmptyString(testString, true, true, true)) {
                okTests++;
            }
            testString = datosAntiguoSistema_GetValue(datos, "libroPagina");
            if (testNotNullOrNotEmptyString(testString, true, true, true)) {
                okTests++;
            }
            testString = datosAntiguoSistema_GetValue(datos, "libroTipo");
            if (testNotNullOrNotEmptyString(testString, true, true, true)) {
                okTests++;
            }
            testString = datosAntiguoSistema_GetValue(datos, "tomoAnio");
            if (testNotNullOrNotEmptyString(testString, true, true, true)) {
                okTests++;
            }
            testString = datosAntiguoSistema_GetValue(datos, "tomoMunicipio");
            if (testNotNullOrNotEmptyString(testString, true, true, true)) {
                okTests++;
            }
            testString = datosAntiguoSistema_GetValue(datos, "tomoNumero");
            if (testNotNullOrNotEmptyString(testString, true, true, true)) {
                okTests++;
            }
            testString = datosAntiguoSistema_GetValue(datos, "tomoPagina");
            if (testNotNullOrNotEmptyString(testString, true, true, true)) {
                okTests++;
            }
            testString = datosAntiguoSistema_GetValue(datos, "comentario");
            if (testNotNullOrNotEmptyString(testString, true, true, true)) {
                okTests++;
            }

            // si todas son malas no continua (si no existe algun valor, sale)
            if (okTests <= 0) {
                return;
            }

            // -----------------------------------------------------------
            // probar si alguno de los datos de antiguo sistema
            // es no nulo; si sucede esto, se imprime
            // el conjunto de datos
            //this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_PLANO, "");
            this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO2,
                    "DATOS ANTIGUO SISTEMA: ");

            this.imprimirDatosLibro(datos);
            this.imprimirDatosTomo(datos);

            String comentario = datos.getComentario();

            if (comentario != null) {
                this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO2,
                        "COMENTARIO: ", false);
                this.imprimirLinea(ImprimibleConstantes.TAM_PEQUE, 120, comentario);
            }

            this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO2, "");
        }
    }

    /**
     * Imprime los datos asociados al libro
     *
     * @param datos
     */
    private void imprimirDatosLibro(DatosAntiguoSistema datos) {
        String libroAno = datos.getLibroAnio();
        String libroNumero = datos.getLibroNumero();
        String libroPagina = datos.getLibroPagina();
        String libroTipo = datos.getLibroTipo();

        String line;

        if ((libroAno != null) || (libroNumero != null)
                || (libroPagina != null) || (libroTipo != null)) {
            this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO1,
                    "LIBRO: ", false);
        }

        if (libroAno != null) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 75,
                    "Año: " + libroAno, false);
        }

        if (libroNumero != null) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 115,
                    "No: " + libroNumero, false);
        }

        if (libroPagina != null) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 155,
                    "Pg: " + libroPagina, false);
        }

        if (libroTipo != null) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 192,
                    "Tipo: " + libroTipo, false);
        }

        //this.imprimirLinea(ImprimibleConstantes.PLANO, "");
    }

    /**
     * Imprime los datos del tomo
     *
     * @param datos
     */
    private void imprimirDatosTomo(DatosAntiguoSistema datos) {
        String tomoAno = datos.getTomoAnio();
        String tomoMunicipio = datos.getTomoMunicipio();
        String tomoNumero = datos.getTomoNumero();
        String tomoPagina = datos.getTomoPagina();

        String line;

        if ((tomoAno != null) || (tomoNumero != null) || (tomoPagina != null)
                || (tomoMunicipio != null)) {
            this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO1, 242,
                    "TOMO: ", false);
        }

        if (tomoAno != null) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 277,
                    "Año: " + tomoAno, false);
        }

        if (tomoNumero != null) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 317,
                    "No: " + tomoNumero, false);
        }

        if (tomoPagina != null) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 357,
                    "Pg: " + tomoPagina, false);
        }

        if (tomoMunicipio != null) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 394,
                    "Mun: " + tomoMunicipio, false);
        }

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
    }

    private boolean imprimirFirma_SolicitudCorreccion_Enabled;

    // Bug 0003526: Habilitar una region para que el ciudadano pueda
    // firmar
    private void imprimirFirma_SolicitudCorreccion() {

        // imprimirFirma(); // ImprimibleBase.imprimirFirma
        imprimirLinea(ImprimibleConstantes.PLANO, "");
        imprimirLinea(ImprimibleConstantes.PLANO, "__________________________________________________________________________");
        imprimirLinea(ImprimibleConstantes.PLANO, "                                               FIRMA DEL SOLICITANTE");

    } // :imprimirFirma_SolicitudCorreccion

    /**
     * Imprimie los datos del documento
     *
     * @param documento
     */
    private void imprimirDatosDocumento(Documento documento) {
    }

    /**
     * @param b
     */
    public void setMayorExtension(boolean b) {
        mayorExtension = b;
    }

    public boolean isMayorExtencion() {
        return mayorExtension;
    }

    /**
     * @return tipo de certificado
     */
    public String getTipoCertificadoId() {
        return tipoCertificadoId;
    }

    /**
     * @param string
     */
    public void setTipoCertificadoId(String string) {
        tipoCertificadoId = string;
    }

    /**
     * Metodo que retorna la cadena con la fecha actual de impresión.
     *
     * @return String con la fecha de impresión
     */
    protected String getFechaImpresion() {
        return this.fechaImpresion;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public Turno getTurno() {
        return turno;
    }

    public boolean isImprimirFirma_SolicitudCorreccion_Enabled() {
        return imprimirFirma_SolicitudCorreccion_Enabled;
    }

    /**
     * Metodo que especifica las validaciones necesarias.
     *
     * @param nht
     */
    public void setValidaciones(Hashtable nht) {
        this.validaciones = nht;
    }

    public void setImprimirFirma_SolicitudCorreccion_Enabled(boolean imprimirFirma_SolicitudCorreccion_Enabled) {
        this.imprimirFirma_SolicitudCorreccion_Enabled
                = imprimirFirma_SolicitudCorreccion_Enabled;
    }

    /**
     * Imprime el cuerpo del recibo Reparto Notarial de Minutas.
     */
    private void imprimirCuerpoReciboRepartoNotarial() {

        SolicitudRepartoNotarial solReparto = (SolicitudRepartoNotarial) this.solicitud;
        Minuta minuta = solReparto.getMinuta();

        /*
		if(minuta.isNormal()){
			this.imprimirLinea(ImprimibleConstantes.PLANO, "TIPO REPARTO :", false);
			this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + CMinuta.ORDINARIO);
		}else{
			this.imprimirLinea(ImprimibleConstantes.PLANO, "TIPO REPARTO :", false);
			this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + CMinuta.EXTRAORDINARIO);
		}
         */
        this.imprimirLinea(ImprimibleConstantes.PLANO, "CATEGORIA :", false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + minuta.getCategoria().getIdCategoria() + " - " + minuta.getCategoria().getNombre());

        //this.imprimirLinea(ImprimibleConstantes.PLANO, "CONTRATO :                       " + minuta.getAccionNotarial().getNombre());
        this.imprimirLinea(ImprimibleConstantes.PLANO, "NÚMERO DE FOLIOS :", false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + minuta.getNumeroFolios());

        this.imprimirLinea(ImprimibleConstantes.PLANO, "CUANTÍA O VALOR :", false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + NumberFormat.getInstance().format(minuta.getValor()));

        this.imprimirLinea(ImprimibleConstantes.PLANO, "NÚMERO DE UNIDADES :", false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + minuta.getUnidades());

        if (minuta.getAccionesNotariales() != null && minuta.getAccionesNotariales().size() > 0) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, "");
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "CONTRATO(S) RELACIONADO(S)");

            List accionesNotariales = minuta.getAccionesNotariales();
            Iterator it = accionesNotariales.iterator();
            int a = 0;
            while (it.hasNext()) {
                MinutaAccionNotarial man = (MinutaAccionNotarial) it.next();
                AccionNotarial an = man.getAccionNotarial();
                if (an != null) {
                    /**
                     * @author: Diana Lora
                     * @change: 0010397: Acta - Requerimiento No 063_151 -
                     * Ajustes Reparto Notarial
                     */
                    this.imprimirLinea(ImprimibleConstantes.PLANO, "CONTRATO (" + (a + 1) + ") : "
                            + an.getNombre() + " - CUANTÍA O VALOR: " + NumberFormat.getInstance().format(man.getValor()));
                }
                a++;
            }
        }

        if (minuta.getEntidadesPublicas() != null && minuta.getEntidadesPublicas().size() > 0) {
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "ENTIDAD(ES) PÚBLICA(S) RELACIONADA(S)");

            List entidadesPublicas = minuta.getEntidadesPublicas();
            Iterator iterator = entidadesPublicas.iterator();
            int a = 0;
            while (iterator.hasNext()) {
                MinutaEntidadPublica mep = (MinutaEntidadPublica) iterator.next();
                EntidadPublica ep = mep.getEntidadPublica();
                this.imprimirLinea(ImprimibleConstantes.PLANO, "ENTIDAD PÚBLICA (" + (a + 1) + ") : " + ep.getNombre());
                a++;
            }
        }

        if (minuta.getOtorgantesNaturales() != null && minuta.getOtorgantesNaturales().size() > 0) {
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "PERSONAS NATURALES RELACIONADAS Ó JURÍDICA");

            List otorgantes = minuta.getOtorgantesNaturales();
            Iterator iterator = otorgantes.iterator();
            int a = 0;
            while (iterator.hasNext()) {
                OtorganteNatural oto = (OtorganteNatural) iterator.next();
                this.imprimirLinea(ImprimibleConstantes.PLANO, "PERSONA (" + (a + 1) + ") : " + oto.getNombre());
                a++;
            }

        }

        this.imprimirLinea(ImprimibleConstantes.TITULO2, "OBSERVACIONES");
        this.imprimirLinea(ImprimibleConstantes.PLANO, minuta.getComentario());

        String telefono = ")";

        if (solReparto.getCirculo() != null) {
            if (solReparto.getCirculo().getOficinaOrigen() != null) {
                if (solReparto.getCirculo().getOficinaOrigen().getTelefono() != null) {
                    telefono = " Ó AL TELEFONO " + solReparto.getCirculo().getOficinaOrigen().getTelefono() + ")";
                }
            }
        }

        String verificacion = "VERIFIQUE QUÉ NOTARIA LE CORRESPONDIÓ (EN LA PÁGINA WWW.SUPERNOTARIADO.GOV.CO"
                + telefono
                + " Y ACÉRQUESE ALLÍ CON ESTE RECIBO";
        this.imprimirLinea(ImprimibleConstantes.TITULO2, verificacion);

    }

    /**
     * Imprime el cuerpo del recibo de restitución de reparto notarial de
     * minutas.
     */
    private void imprimirCuerpoReciboRestitucionRepartoNotarial() {

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        SolicitudRestitucionReparto solRestitucion = (SolicitudRestitucionReparto) this.solicitud;

        if (solRestitucion != null && solRestitucion.getTurnoAnterior() != null && solRestitucion.getTurnoAnterior().getIdWorkflow() != null) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, "TURNO ASOCIADO :                " + solRestitucion.getTurnoAnterior().getIdWorkflow());
        }

        if (solRestitucion != null && solRestitucion.getCausalRestitucion() != null && solRestitucion.getCausalRestitucion().getNombre() != null) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, "CAUSAL DE RESTITUCIÓN :  " + solRestitucion.getCausalRestitucion().getNombre());
        }

        if (solRestitucion != null) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, "OBSERVACIONES :  " + solRestitucion.getObservacionesRestitucion());
        }
    }

    // ------------------------------------------------------
    public static final double DOCUMENT_WIDTH = LETTER_WIDTH;
    public static final double DOCUMENT_HEIGHT = LETTER_HEIGHT / 2.0d;

    private String usuarioGeneraRecibo;

    public void setUsuarioGeneraRecibo(String local_UsuarioGeneraRecibo) {
        this.usuarioGeneraRecibo = local_UsuarioGeneraRecibo;
    } //:setUsuarioGeneraRecibo

    public String getUsuarioGeneraRecibo() {
        return usuarioGeneraRecibo;
    } //:setUsuarioGeneraRecibo

    /**
     * @return
     */
    public boolean isReimpresion() {
        return reimpresion;
    }

    /**
     * @param b
     */
    public void setReimpresion(boolean b) {
        reimpresion = b;
    }

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

        final String USUARIO_MSG = "Usuario: ";

        System.out.println("@@ >> print_UsuarioGeneraRecibo: " + local_UsuarioGeneraRecibo);

        this.imprimirLinea(ImprimibleConstantes.PLANO, USUARIO_MSG + local_UsuarioGeneraRecibo);

    } //:setUsuarioGeneraRecibo

    /**
     * Modificado 2010/02/23 by jvenegas para Boton de Pago
     *
     * @param imprimirDatosCertificadosMasivos the
     * imprimirDatosCertificadosMasivos to set
     */
    public void setImprimirDatosCertificadosMasivos(
            boolean imprimirDatosCertificadosMasivos) {
        this.imprimirDatosCertificadosMasivos = imprimirDatosCertificadosMasivos;
    }

    /**
     * @return
     */
    public Pago getPago() {
        return pago;
    }

    /**
     * @return
     */
    public boolean isTamanoCarta() {
        return tamanoCarta;
    }

    /**
     * @param b
     */
    public void setTamanoCarta(boolean b) {
        tamanoCarta = b;
    }

    public boolean isConsultaExenta() {
        return consultaExenta;
    }

    public void setConsultaExenta(boolean consultaExenta) {
        this.consultaExenta = consultaExenta;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Pago getPagoMasivo() {
        return pagoMasivo;
    }

    public void setPagoMasivo(Pago pagoMasivo) {
        this.pagoMasivo = pagoMasivo;
    }

    public boolean isEsMayorValorCorrecciones() {
        return esMayorValorCorrecciones;
    }

    public void setEsMayorValorCorrecciones(boolean esMayorValorCorrecciones) {
        this.esMayorValorCorrecciones = esMayorValorCorrecciones;
    }

    public boolean isTienematriculasSinMigrar() {
        return tienematriculasSinMigrar;
    }

    public void setTienematriculasSinMigrar(boolean tienematriculasSinMigrar) {
        this.tienematriculasSinMigrar = tienematriculasSinMigrar;
    }

    public String getMatriculaSinMigrar() {
        return matriculaSinMigrar;
    }

    public void setMatriculaSinMigrar(String matriculaSinMigrar) {
        this.matriculaSinMigrar = matriculaSinMigrar;
    }

    public boolean isReimpresionPagoMayorValor() {
        return reimpresionPagoMayorValor;
    }

    public void setReimpresionPagoMayorValor(boolean reimpresionPagoMayorValor) {
        this.reimpresionPagoMayorValor = reimpresionPagoMayorValor;
    }

    public boolean isTurnoInternet() {
        return turnoInternet;
    }

    public void setTurnoInternet(boolean turnoInternet) {
        this.turnoInternet = turnoInternet;
    }

    public boolean isReimpreso() {
        return reimpreso;
    }

    public void setReimpreso(boolean reimpreso) {
        this.reimpreso = reimpreso;
    }

    /**
     * @author : HGOMEZ
     *** @change : Métodos para obtener y setear el valor de la variable **
     * tipoCertificadoNombre. ** Caso Mantis : 11598
     */
    public String getTipoCertificadoNombre() {
        return tipoCertificadoNombre;
    }

    public void setTipoCertificadoNombre(String tipoCertificadoNombre) {
        this.tipoCertificadoNombre = tipoCertificadoNombre;
    }

    /**
         * @author      :   Julio Alcázar Rivas
         * @change      :   Imprime el Documento de la solicitud.
         * Caso Mantis  :   000941
         * Revision 1   :   Ajuste del imprimible a la hoja de recibos
     * @param
     * @return
     */
    protected void imprimirDocumento() {

        if (this.documento != null) {

            this.imprimirLinea(ImprimibleConstantes.PLANO, "");
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "DATOS DOCUMENTO: ");

            Calendar hoy = Calendar.getInstance();
            hoy.setTime(this.documento.getFecha());
            String fechaDoc = hoy.get(Calendar.DAY_OF_MONTH) + "/" + (hoy.get(Calendar.MONTH) + 1) + "/" + hoy.get(Calendar.YEAR);

            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "Tipo Documento: " + this.documento.getTipoDocumento().getNombre() + "    Número Documento: " + this.documento.getNumero() + "    Fecha documento: " + fechaDoc);

            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "OFICINA DE PROCEDENCIA: ");

            if (this.documento.getOficinaInternacional() == null) {

                this.imprimirLinea(ImprimibleConstantes.PLANO,
                        "Tipo de Oficina de Origen: " + this.documento.getOficinaOrigen().getTipoOficina().getNombre()
                        + "    Departamento: " + this.documento.getOficinaOrigen().getVereda().getMunicipio().getDepartamento().getNombre()
                        + "    Municipio: " + this.documento.getOficinaOrigen().getVereda().getMunicipio().getNombre());

                this.imprimirLinea(ImprimibleConstantes.PLANO,
                        "Nombre: " + this.documento.getOficinaOrigen().getNombre());
            } else {

                this.imprimirLinea(ImprimibleConstantes.PLANO,
                        "Oficina ubicación Internacional: " + this.documento.getOficinaInternacional());
            }


        }

    }

}

package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
import gov.sir.core.util.DateFormatUtil;

import java.awt.print.PageFormat;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


/**
 * Imprimible con la constancia de Liquidación de Registro
 *
 * @author gardila
 *
 */
public class ImprimibleConstanciaLiquidacion extends ImprimibleBase {
    /** Constante con el título del imprimible */
    protected static final String CONSTANCIA_DE_LIQUIDACIÓN = "CONSTANCIA DE LIQUIDACIÓN";
private static final long serialVersionUID = 1L;
    /** Constante con el Formato de la fecha */
    private static String formatoFecha = "d 'de' MMMM 'de' yyyy ";

    /** Constante con el Formato de la hora */
    private static String formatoHora = " hh:mm:ss a";

    /** Constante con el Formato de la hora */
    private static String formatoValores = "$ ###,###";

    /** Constante con la nota u observación del imprimible */
    private static final String NOTA_LINEA_1 =
        "NOTA: ESTA CONSTANCIA TIENE VIGENCIA " +
        "POR UN DÍA.";

    /** Constante con la nota u observación del imprimible */
    private static final String NOTA_LINEA_2 =
        "EL PROCESO DE REGISTRO EMPIEZA CON LA RADICACIÓN UNA VEZ " +
        "EFECTUADO EL PAGO DE LOS DERECHOS";

    /** Liquidación sobre la que se genera el imprimible */
    private Liquidacion liquidacion;

    /** Fecha de generación del imprimible*/
    private Calendar fecha;

    private String fechaImpresion;
    
    private boolean reimpresion;

    /**
     * @param liquidacion objeto Liquidación que se va a imprimir
     *
     */
    public ImprimibleConstanciaLiquidacion(Liquidacion liquidacion, String fechaImpresion,String tipoImprimible) {
        super(tipoImprimible);

        this.liquidacion = liquidacion;
        this.setImprimirMargen(false);
        this.fecha = Calendar.getInstance();
        this.fechaImpresion= fechaImpresion;
    }

    /**
     * @see gov.sir.print.common.Imprimible#generate(java.awt.print.PageFormat)
     */
    public void generate(PageFormat pageFormat) {
        super.generate(pageFormat);

        this.imprimirEncabezado();
        this.imprimirCuerpo();

        if (this.reimpresion) {
            this.textoReimpresion();
        }
    }

    /**
     * @see gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase#imprimirEncabezado()
     */
    public void imprimirEncabezado() {
        /*
            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, 470,
            this.liquidacion.getIdSolicitud());
        */
        String titulo = this.getTitulo();
        this.imprimirLinea(ImprimibleConstantes.TITULO1,
            ImprimibleConstantes.MARGEN_IZQ * 4, titulo);

        //imprime la fecha y hora de impresion del recibo.
        String fechaImp = this.fechaImpresion;
        this.imprimirLinea(ImprimibleConstantes.PLANO,
            ImprimibleConstantes.MARGEN_IZQ * 4, fechaImp);

        //imprime el número de liquidación
        this.imprimirLinea(ImprimibleConstantes.TITULO1,
            ImprimibleConstantes.MARGEN_IZQ * 4,
            "No. DE LIQUIDACIÓN: L-" + this.liquidacion.getIdSolicitud(), true);

        this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
    }

    /**
     * Generar el cuerpo del imprimible
     */
    private void imprimirCuerpo() {
        SolicitudRegistro solicitud = (SolicitudRegistro) this.liquidacion.getSolicitud();
        LiquidacionTurnoRegistro liqTurno = (LiquidacionTurnoRegistro) this.liquidacion;
        SubtipoSolicitud subtipo = solicitud.getSubtipoSolicitud();
        String nombreSubtipo = (subtipo == null) ? "" : subtipo.getNombre();
        List solicitudFolios = solicitud.getSolicitudFolios();
        double valorCert=0, totalLiquidacion;

        //sacar valor de los certificados
        List certs=solicitud.getSolicitudesHijas();
        Iterator ic= certs.iterator();
        for(;ic.hasNext();){
        	SolicitudAsociada solA= (SolicitudAsociada) ic.next();

        	SolicitudCertificado solC;
        	solC= (SolicitudCertificado)solA.getSolicitudHija();
        	LiquidacionTurnoCertificado liq= (LiquidacionTurnoCertificado)solC.getLiquidaciones().get(0);
        	valorCert+=liq.getValor();

        }

        this.imprimirUsuario();

        if (solicitudFolios.size() > 0) {
            this.imprimirLinea(ImprimibleConstantes.TITULO2, "MATRICULA(S)",
                false);

            for (Iterator iter = solicitudFolios.iterator(); iter.hasNext();) {
                SolicitudFolio soliFolio = (SolicitudFolio) iter.next();
                String matricula = soliFolio.getIdMatricula();
                this.imprimirLinea(ImprimibleConstantes.TITULO2, 100,
                    matricula, true);
            }
        }

        if (nombreSubtipo.length() > 0) {
            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "SUBTIPO DE SOLICITUD: " + nombreSubtipo, false);
        }

        this.imprimirLinea(ImprimibleConstantes.PLANO, " ");

        this.imprimirLinea(ImprimibleConstantes.TITULO2,
            "VALOR DE DERECHOS DE REGISTRO: " +
            this.getNumeroFormateado(liqTurno.getValorDerechos()), true);
        this.imprimirLinea(ImprimibleConstantes.TITULO2,
            "VALOR DE IMPUESTO: " +
            this.getNumeroFormateado(liqTurno.getValorImpuestos()), true);
        this.imprimirLinea(ImprimibleConstantes.TITULO2,
            "VALOR DE LA MULTA: " +
            this.getNumeroFormateado(liqTurno.getValorMora()), true);
        this.imprimirLinea(ImprimibleConstantes.TITULO2,
            "CERTIFICADOS ASOCIADOS: " +
            this.getNumeroFormateado(valorCert), true);

        if (liqTurno.getValorOtroImp() > 0) {
            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "OTRO IMPUESTO: " + liqTurno.getOtroImpuesto(), true);
            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "VALOR OTRO IMPUESTO " +
                this.getNumeroFormateado(liqTurno.getValorOtroImp()), true);
        }

        this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
        this.imprimirLinea(ImprimibleConstantes.TITULO2,"Valor de conservación documental del 2%: " + this.getNumeroFormateado(liqTurno.getValorConservacionDoc()), true);            
        this.imprimirLinea(ImprimibleConstantes.PLANO, " ");

        /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Cambio del texto "VALOR TOTAL " por "VALOR TOTAL A PAGAR AJUSTADO A LA CENTENA POR ACTOS ".
         */
        totalLiquidacion = liqTurno.getValor() + valorCert;
        this.imprimirLinea(ImprimibleConstantes.TITULO1,
            "VALOR TOTAL A PAGAR AJUSTADO A LA CENTENA POR ACTOS " + this.getNumeroFormateado(totalLiquidacion), true);

        this.imprimirLinea(ImprimibleConstantes.PLANO, " ");

        this.imprimirLinea(ImprimibleConstantes.PLANO, NOTA_LINEA_1, true);
        this.imprimirLinea(ImprimibleConstantes.PLANO, NOTA_LINEA_2, true);
    }

    /**
     * @param numero
     * @return obtiene el numero en formato especificado en la constante formatoValores
     */
    private String getNumeroFormateado(double numero) {
        String res = "";
        NumberFormat formateado = new DecimalFormat(formatoValores);
        res = formateado.format(numero);
        
        return res;
    }

    /**
     * @return El título del documento que se coloca en el encabezado
     */
    public String getTitulo() {
        return ImprimibleConstanciaLiquidacion.CONSTANCIA_DE_LIQUIDACIÓN;
    }

    /**
    * Imprime el usuario que resgistro la liquidación.
    */
    private void imprimirUsuario() {
        Usuario usuario = this.liquidacion.getSolicitud().getUsuario();

        if (usuario != null) {
            String funcionario = ""+usuario.getIdUsuario();

            if (funcionario != null) {
                this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "REGISTRADO POR: " + funcionario);
            }
        }
    }

    /**
     * @return fecha del imprimible en formato:
     * "Impreso el fecha a las hora"
     */
    public String getFechaImpresion() {        
        StringBuffer sb = new StringBuffer("Impreso el ");
        sb.append(DateFormatUtil.format(formatoFecha, this.fecha.getTime()));
        sb.append(" a las ");
        sb.append(DateFormatUtil.format(formatoHora, this.fecha.getTime()));

        return sb.toString();
    }

    /* (non-Javadoc)
     * @see gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase#makeNewPage()
     */
    protected void makeNewPage() {
        super.makeNewPage();

        String linea;
        linea = StringFormat.getCentrada("OFICINA DE REGISTRO DE INSTRUMENTOS PÚBLICOS",
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);
        this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);

        Circulo circulo = this.liquidacion.getSolicitud().getCirculo();
        String circuloReg = circulo.getNombre();

        linea = StringFormat.getCentrada(circuloReg.toUpperCase(),
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);
        this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);

        //imprimir el NIT
        String nitOficina = circulo.getNit();

        if (nitOficina != null) {
            String textoNit = "NIT: " + nitOficina;
            textoNit = StringFormat.getCentrada(textoNit, ImprimibleConstantes.MAX_NUM_CHAR, 12);
            this.imprimirLinea(ImprimibleConstantes.TITULO2, textoNit);
        }

        this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
    }
       
    private void textoReimpresion() {
        String textoReimpresion = "Reimpresión del recibo de liquidación";
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.TITULO2, textoReimpresion);
    }
    
    public void setReimpresion(boolean b) {
        reimpresion = b;
    }

    public Liquidacion getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(Liquidacion liquidacion) {
        this.liquidacion = liquidacion;
    }
    
    
}

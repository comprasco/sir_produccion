package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DocumentoFotocopia;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLoggerImp1;
import java.awt.print.PageFormat;
import java.text.DecimalFormat;

public class ImprimibleFotocopiaLiquidarSolicitud extends ImprimibleFotocopiaCrearSolicitud {
        private static final long serialVersionUID = 1L;
	public static final String DEFAULT_IMPRIMIBEFOTOCOPIASCREARSOLICITUD_TITLE = "Liquidacion de Documentos a ser Fotocopiados";

	protected Liquidacion liquidacion;

	public ImprimibleFotocopiaLiquidarSolicitud(Turno turno, SolicitudFotocopia solicitud, Circulo circulo, Liquidacion liquidacion,String tipoImprimible) {		
		super(turno, solicitud, circulo,tipoImprimible);
		this.liquidacion = liquidacion;
		logger = AppletLoggerImp1.getAppletLogger();
	}

	public void configure() {		
		logger = AppletLoggerImp1.getAppletLogger();
		setEmpresaNombre(DEFAULT_EMPRESA_NOMBRE);
		setImprimirLogoEnabled(false);
		setTitulo(DEFAULT_IMPRIMIBEFOTOCOPIASCREARSOLICITUD_TITLE);
	}


	public void generate(PageFormat pageFormat) {
		super.generate(pageFormat);
	}
	
	public void generate2(PageFormat pageFormat) {
		super.generate2(pageFormat);
	}

	protected void imprimirCuerpo_Items_Node(DocumentoFotocopia documento, int index) {

		printLine_Indentation2(ImprimibleConstantes.PLANO, "" + index + ".");
		String descripcion = imprimirCuerpoReciboFotocopia_PrintNode_GetDescripcionSegment(documento.getDescripcion());
		printLine_Indentation3(ImprimibleConstantes.PLANO, "Tipo de Documento: " + documento.getTipoDocumento().getNombre());
		printLine_Indentation3(ImprimibleConstantes.PLANO, "Número de copias:  " + documento.getNumCopias());
		printLine_Indentation3(ImprimibleConstantes.PLANO, "Descripción:        " + descripcion);
		String tipoFotocopiaNombre = (null == documento.getTipoFotocopia().getNombre()) ? ("") : (documento.getTipoFotocopia().getNombre());
		printLine_Indentation3(ImprimibleConstantes.PLANO, "Tipo de fotocopia:        " + tipoFotocopiaNombre);
		printLine_Indentation3(ImprimibleConstantes.PLANO, "Num Hojas:        " + documento.getNumHojas());

	}

	protected static String VALOR_LIQUIDADO_DECIMAL_FORMAT_STRING = " $ ######,###";

	protected void imprimirCuerpo_Items(PageFormat pageFormat) {
		super.imprimirCuerpo_Items(pageFormat);
		double valorLiquidado = -0d;

		DecimalFormat decimalFormat = new DecimalFormat(VALOR_LIQUIDADO_DECIMAL_FORMAT_STRING);

		// salto de linea
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "", true);

		if ((null != this.liquidacion) && ((valorLiquidado = this.liquidacion.getValor()) >= 0)) {
			/* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                         * Cambio del texto "Valor Liquidado : " por "Valor Liquidado Ajustado a la Centena por Actos: ".
                         */
                        this.imprimirLinea(ImprimibleConstantes.TITULO2, "Valor Liquidado Ajustado a la Centena por Actos: " + decimalFormat.format(valorLiquidado), false);
		} else {
			/* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                         * Cambio del texto "Valor liquidado (negativo) :" por "Valor liquidado (negativo) Ajustado a la Centena por Actos: ".
                         */
                        this.imprimirLinea(ImprimibleConstantes.TITULO2, "Valor liquidado (negativo) Ajustado a la Centena por Actos: " + decimalFormat.format(valorLiquidado), false);
		}

	}

}

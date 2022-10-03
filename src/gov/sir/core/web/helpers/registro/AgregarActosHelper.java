package gov.sir.core.web.helpers.registro;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.TipoActo;
import gov.sir.core.negocio.modelo.TipoImpuesto;
import gov.sir.core.negocio.modelo.TipoImpuestoCirculo;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;
import gov.sir.core.web.helpers.comun.TextHelper;
import gov.sir.hermod.HermodProperties;
import java.util.Date;


/**
 * @author jfrias
 * @author ddiaz
 * @author dsalas
*/
public class AgregarActosHelper extends Helper {
	private boolean desHabilitarDerechos;
	private int num;
	List tipos;
	List tipoImp;
	ListaElementoHelper tipoActoHelper = new ListaElementoHelper();
	ListaElementoHelper tipoDerechoHelper = new ListaElementoHelper();
	ListaElementoHelper tipoTarifaHelper = new ListaElementoHelper();
	ListaElementoHelper tipoTarifaImpHelper = new ListaElementoHelper();
	TextHelper textHelper = new TextHelper();
	Circulo circulo;
	boolean calificacion=false;
	String tipoCalculo="";
	String nombreCalculo="";
	boolean carga=false;
	boolean mostrarImpuesto=true;
	boolean mostrarDerecho=false;
	
	private NumberFormat formato = NumberFormat.getInstance();
	/**
	 * 
	 */
	public AgregarActosHelper() {
		super();
	}

	/**
	 * @see org.auriga.core.web.Helper#drawGUI(javax.servlet.http.HttpServletRequest, javax.servlet.jsp.JspWriter)
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws HelperException, IOException {
			
			
	/**
         * @author: Fernando Padilla Velez
         * @change: Modificado para el caso MANTIS 135_141_Impuesto Meta, se realiza
         *          la validacion que exista el TipoImpuesto en la lista de TipoImpuestoCirculo.
         */
        try {
            List<TipoImpuesto> tipoImp = (List<TipoImpuesto>) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_IMPUESTO);
            List<TipoImpuestoCirculo> tipoImpCir = (List<TipoImpuestoCirculo>) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_IMPUESTO_CIRCULO);
            List elem = new ArrayList();
            Date hoy = gov.sir.core.util.DateFormatUtil.parse(gov.sir.core.util.DateFormatUtil.format(new Date()));

            /**
             * @author: Fernando Padilla Velez
             * @change: Se modifica para el caso MANTIS 4729, Acta - Requerimiento No 202 - Impuesto Meta (Puerto Lopez),
             *          Se corrige los numeros de los codigos de los circulos habilitados para el impuesto del meta, ahora serán
             *          llamados desde la propiedad gov.sir.hermod.circulos.impuesto.meta del archivo gov.sir.hermod.properties dentro de mapas.jar
             *
             */
            HermodProperties hp = HermodProperties.getInstancia();
	    String circulos   = hp.getProperty(HermodProperties.HERMOD_CIRCULOS_IMPUESTO_META);
            for (TipoImpuesto t : tipoImp) {
                for (TipoImpuestoCirculo tt : tipoImpCir) {
                    Date fechaInicioVigencia = gov.sir.core.util.DateFormatUtil.parse(gov.sir.core.util.DateFormatUtil.format(tt.getFechaInicioVigencia()));
                    Date fechaFinVigencia = gov.sir.core.util.DateFormatUtil.parse(gov.sir.core.util.DateFormatUtil.format(tt.getFechaFinVigencia()));
                    if (tt.getIdTipoImpuesto().equals(t.getIdTipoImpuesto()) && tt.getIdCirculo().equals(this.circulo.getIdCirculo()) && (hoy.after(fechaInicioVigencia) || hoy.equals(fechaInicioVigencia))
                            && (hoy.before(tt.getFechaFinVigencia()) || hoy.equals(fechaFinVigencia) ))  {
                        if((circulos.indexOf(circulo.getIdCirculo()) != -1 ) &&
                           "IM".equals(t.getIdTipoImpuesto()) &&
                           !"38".equals(request.getSession().getAttribute(CActo.TIPO_ACTO))){
                            continue;
                        }
                        elem.add(new ElementoLista(t.getIdTipoImpuesto(), t.getNombre()));
                     }
                }
            }
            out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
            out.write("	<tr>");
            out.write("		<td width=\"12\"><img name=\"sub_r1_c1\" src=\" " + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.write("		<td background=\"" + request.getContextPath() + "/jsp/images/sub_bgn001.gif\" class=\"bgnsub\"> Nuevo Acto</td>");
            out.write("		<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_acto.gif\" width=\"16\" height=\"21\"></td>");
            out.write("		<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_asociar.gif\" width=\"16\" height=\"21\"></td>");
            out.write("		<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.write("	</tr>");
            out.write("</table>");
            out.write("<br>");
            out.write("<table width=\"100%\" class=\"camposform\">");
            out.write("	<tr>");
            out.write("		<td><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
            out.write("		<td>Acto</td>");
            out.write("	</tr>");
            out.write("	<tr>");
            out.write("		<td width=\"20\">&nbsp;</td>");
            out.write("		<td><table width=\"100%\" class=\"camposform\">"); //TABLA NUEVO ACTO
            out.write("				<tr>"); //PRIMERA FILA TIPO ACTO
            out.write("					<td width=\"20%\">Tipo</td>"); //PRIMERA COLUMNA LABELS
            out.write("					<td width=\"20%\">"); //SEGUNDA COLUMNA, CAMPOS
            textHelper.setNombre("ID_ACTO");
            textHelper.setCssClase("camposformtext");
            textHelper.setId("ID_ACTO");
            textHelper.setFuncion("onBlur=\"cambioID('CARGAR_DERECHOS')\"");
            textHelper.setSize("10");
            textHelper.setMaxlength("5");
            textHelper.render(request, out);
            out.write("					</td>");
            out.write("					<td width=\"10%\">Descripci&oacute;n</td>"); //TERCERA COLUMNA LABELS
            out.write("					<td width\"50%\">"); //CUARTA COLUMNA, CAMPOS
            List elem1 = new ArrayList();
            if (tipos != null) {
                String nombre = null;
                for (int i = 0; i < tipos.size(); i++) {
                    TipoActo t = (TipoActo) tipos.get(i);
                    if (t.getNombre() != null) {
                        nombre = t.getNombre().concat(" - " + t.getIdTipoActo());
                    } else {
                        nombre = t.getIdTipoActo();
                    }
                    elem1.add(new ElementoLista(t.getIdTipoActo(), nombre));
                }
            }
            tipoActoHelper.setTipos(elem1);
            if (carga) {
                //tipoActoHelper.setSelected("16");
                tipoActoHelper.setSelected(WebKeys.SIN_SELECCIONAR);
            }
            tipoActoHelper.setNombre("TIPO_ACTO");
            tipoActoHelper.setCssClase("camposformtext");
            tipoActoHelper.setId("TIPO_ACTO");
            tipoActoHelper.setFuncion("onChange=\"cambioTipo('CARGAR_DERECHOS')\"");
            tipoActoHelper.setShowInstruccion(true);
            tipoActoHelper.render(request, out);
            if (carga) {
                //out.write("<script>cambioTipo('CARGAR_DERECHOS'); </script>");
            }
            out.write("					</td>");
            out.write("				</tr>");
            out.write("				<tr>"); //SEGUNDA FILA
            if (tipoCalculo.equals(CActo.PORCENTUAL)) {
                //PRIMERA COLUMNA LABELS
                out.write("				<td>Cuant&iacute;a o autoavaluo</td>");
            } else if (tipoCalculo.equals(CActo.ABSOLUTO)) {
                out.write("				<td>Cantidad</td>");
            } else if (tipoCalculo.equals(CActo.MANUAL)) {
                out.write("				<td>Valor Acto</td>");
            } else {
                out.write("				<td>Cuant&iacute;a, autoavaluo o cantidad</td>");
            }
            out.write("					<td>"); //SEGUNDA COLUMNA VALOR
            textHelper.setFuncion("");
            textHelper.setNombre("VALOR_ACTO");
            textHelper.setCssClase("camposformtext");
            textHelper.setId("VALOR_ACTO");
            textHelper.setFuncion("onChange=\"validarNumerico('VALOR_ACTO')\" onkeypress=\"return valideKey(event,'VALOR_ACTO');\" ");
            textHelper.setSize("20");
            textHelper.setMaxlength("40");
            textHelper.render(request, out);
            out.write("					</td>");
            out.write("					<td>Tarifa</td>"); //TERCERA COLUMNA LABELS
            out.write("					<td>"); //CUARTA COLUMNA LISTA DE TIPOS DE TARIFA
            List derechos = (List) request.getSession().getAttribute("LISTA_TIPOS_DERECHOS_REGISTRALES");
            if (derechos != null && derechos.size() > 0) {
                tipoDerechoHelper.setTipos(derechos);
                if (request.getSession().getAttribute("TIPO_DERECHO") == null) {
                    request.getSession().setAttribute("TIPO_DERECHO", "N");
                }
            }
            tipoDerechoHelper.setNombre("TIPO_DERECHO");
            tipoDerechoHelper.setCssClase("camposformtext");
            tipoDerechoHelper.setId("TIPO_DERECHO");
            tipoActoHelper.setShowInstruccion(false);
            if (desHabilitarDerechos) {
                textHelper.setNombre("TIPO_DERECHO"); //TIPO TARIFA DE IMPUESTOS HIDDEN
                textHelper.setTipo("hidden");
                textHelper.setCssClase("campositem");
                textHelper.setId("TIPO_DERECHO");
                textHelper.setSize("20%");
                textHelper.render(request, out);
            } else {
                tipoDerechoHelper.render(request, out);
            }
            if (!nombreCalculo.equals("")) {
                out.write("				" + nombreCalculo + "");
            } else {
                out.write("				&nbsp;");
            }
            out.write("					</td>");
            out.write("				</tr>");
            if (circulo.isCobroImpuesto()) {
                out.write("			<tr>"); //TERCERA FILA
                out.write("				<td width=\"20\">Impuesto?</td>"); //PRIMERA COLUMNA LABEL
                out.write("				<td><input name=\"COBRA_IMPUESTO\" type=\"checkbox\" >"); //SEGUNDA COLUMNA VALOR
                tipoTarifaImpHelper.setTipos(elem);
                tipoTarifaImpHelper.setNombre(CActo.TIPO_TARIFA);
                tipoTarifaImpHelper.setId(CActo.TIPO_TARIFA);
                tipoTarifaImpHelper.setCssClase("camposformtext");
                tipoTarifaImpHelper.render(request, out);
                /*textHelper.setNombre(CActo.TIPO_TARIFA);//TIPO TARIFA DE IMPUESTOS HIDDEN
                //textHelper.setTipo("hidden");
                textHelper.setCssClase("camposformtext");
                textHelper.setId(CActo.TIPO_TARIFA);
                textHelper.setSize("20%");
                textHelper.render(request,out);
                 */
                out.write("</td>");
                if (tipoCalculo.equals(CActo.MANUAL)) {
                    out.write("			<td>Valor impuesto </td>"); //TERCERA COLUMNA LABELS
                    out.write("			<td>"); //CUARTA COLUMNA VALOR
                    textHelper.setNombre(CActo.VALOR_IMPUESTO);
                    textHelper.setTipo("text");
                    textHelper.setCssClase("camposformtext");
                    textHelper.setId(CActo.VALOR_IMPUESTO);
                    textHelper.setFuncion("onChange=\"validarNumerico('" + CActo.VALOR_IMPUESTO + "')\"");
                    textHelper.setSize("20");
                    textHelper.render(request, out);
                    out.write("			</td>");
                } else {
                    out.write("			<td></td>"); //TERCERA COLUMNA LIBRE
                    out.write("			<td></td>"); //CUARTA COLUMNA LIBRE
                }
                out.write(" 		</tr>");
            }
            if (mostrarDerecho) {
                out.write("			<tr>"); //CUARTA FILA
                out.write("				<td width=\"20\">Valor Liquidado</td>"); //PRIMERA COLUMNA LABEL
                out.write("			    <td>"); //SEGUNDA COLUMNA VALOR
                textHelper.setNombre(CActo.VALOR_DERECHOS);
                textHelper.setTipo("text");
                textHelper.setCssClase("camposformtext");
                textHelper.setId(CActo.VALOR_DERECHOS);
                textHelper.setFuncion("onChange=\"validarNumerico('" + CActo.VALOR_DERECHOS + "')\"");
                textHelper.setSize("20");
                textHelper.render(request, out);
                out.write("			    </td>");
                out.write("			    <td></td>");
                out.write("			    <td></td>");
                out.write(" 		</tr>");
            }
            out.write("				<tr>"); //CUARTA FILA BOTON
            out.write("					<td>");
            out.write("<input type=\"image\" onClick=\"agregarActo()\" name=\"imageField\" src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" width=\"35\" height=\"25\" border=\"0\">");
            out.write("					</td>");
            out.write("					<td></td><td></td><td></td>");
            out.write("				<tr>");
            out.write("			</table>"); //FIN TABLA NUEVO ACTO
            out.write("	  </td>");
            out.write("	</tr>");
            out.write("</table>");
        } catch (ParseException ex) {
            Logger.getLogger(AgregarActosHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	/**
	 * @see org.auriga.core.web.Helper#setProperties(javax.servlet.http.HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		
		tipos = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_ACTO) ;
		tipoImp = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_IMPUESTO);
		circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		tipoCalculo = (String) request.getSession().getAttribute(CActo.TIPO_CALCULO);
		nombreCalculo = (String) request.getSession().getAttribute(CActo.NOMBRE_TIPO_CALCULO);
		
		
		if(tipoCalculo==null){
			tipoCalculo="";
			carga=true;
		}
		request.getSession().setAttribute(CActo.TIPO_TARIFA, "N");
		
		if(nombreCalculo==null){
			nombreCalculo="";
		}
		
		
	}
	
	public void setCalificacion(boolean b){
		calificacion=b;
	}
	
	public boolean getCalificacion(){
		return calificacion;
	}
	

	/**
	 * @return Returns the mostrarImpuesto.
	 */
	public boolean isMostrarImpuesto() {
		return mostrarImpuesto;
	}
	/**
	 * @param mostrarImpuesto The mostrarImpuesto to set.
	 */
	public void setMostrarImpuesto(boolean mostrarImpuesto) {
		this.mostrarImpuesto = mostrarImpuesto;
	}
	/**
	 * @return
	 */
	public boolean isDesHabilitarDerechos() {
		return desHabilitarDerechos;
	}

	/**
	 * @param b
	 */
	public void setDesHabilitarDerechos(boolean b) {
		desHabilitarDerechos = b;
	}
	
	/**
	 * @return
	 */
	public boolean isMostrarDerechos() {
		return mostrarDerecho;
	}

	/**
	 * @param b
	 */
	public void setMostrarDerechos(boolean b) {
		mostrarDerecho = b;
	}

}

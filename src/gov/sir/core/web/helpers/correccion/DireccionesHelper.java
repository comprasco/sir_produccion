/*
 * Created on 31-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.correccion;

import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CEje;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import gov.sir.core.web.helpers.comun.TextAreaHelper;
import gov.sir.core.web.helpers.comun.TextHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

/**
 * @author I.Siglo21
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DireccionesHelper extends Helper{

	private String nombreFormaEdicionDireccion;

	private String folioSesion;

	private List direccionesExistentes;
	private List direcciones;

	private ListaElementoHelper ejesHelper;
	private ListaElementoHelper ejes2Helper;
	private TextHelper textHelper;
	TextAreaHelper textAreaHelper;
	private String accionAgregar;
	private String accionEditar;
	private String accionEliminar;
	private String accionEditarAceptar;
    private String funcionCambiarAccion = "cambiarAccion";

	private String funcionSugerirEspecificacion = "funcionSugerirEspecificacion";

	private String funcionQuitar = "quitar";
	
	private String funcionEditardir = "editarDir";
	private boolean mostrarAgregarNueva = true;
	private List dirTemporales = null;
	private Turno turno = null;

        // -----------------------------------------------------------------------

        // Se realiza el siguiente conjunto de acciones
        // porque puede que este helper este siendo usado en otras partes de la aplicacion
        // Este conjunto de acciones activa visualmente la opcion de eliminar direcciones definitivas

        // -----------------------------------------------------------------------

        protected boolean enabledDeleteFromDefinitivas = false;
        protected boolean enabledEditFromDefinitivas = false;
        protected String jsFunctionNameDeleteFromDefinitivas;
        protected String jsFunctionNameEditFromDefinitivas;
        protected String actionIdDeleteFromDefinitivas;
        protected String actionIdEditFromDefinitivas;

        public void setEnabledDeleteFromDefinitivas( boolean enabledDeleteFromDefinitivas ) {
            this.enabledDeleteFromDefinitivas = enabledDeleteFromDefinitivas;
        }
        public boolean isEnabledDeleteFromDefinitivas() {
            return this.enabledDeleteFromDefinitivas;
        }
        
        public void setEnabledEditFromDefinitivas( boolean enabledEditFromDefinitivas ) {
            this.enabledEditFromDefinitivas = enabledEditFromDefinitivas;
        }
        public boolean isEnabledEditFromDefinitivas() {
            return this.enabledEditFromDefinitivas;
        }

        public void setJsFunctionNameDeleteFromDefinitivas( String jsFunctionNameDeleteFromDefinitivas ) {
            this.jsFunctionNameDeleteFromDefinitivas = jsFunctionNameDeleteFromDefinitivas;
        }
        
        public void setJsFunctionNameEditFromDefinitivas( String jsFunctionNameEditFromDefinitivas ) {
            this.jsFunctionNameEditFromDefinitivas = jsFunctionNameEditFromDefinitivas;
        }

        public void setActionIdDeleteFromDefinitivas( String actionIdDeleteFromDefinitivas ) {
            this.actionIdDeleteFromDefinitivas = actionIdDeleteFromDefinitivas;
        }
        
        public void setActionIdEditFromDefinitivas( String actionIdEditFromDefinitivas ) {
            this.actionIdEditFromDefinitivas = actionIdEditFromDefinitivas;
        }

        // -----------------------------------------------------------------------

    public DireccionesHelper(String accionAgregar,String accionEliminar) {
		super();
		this.accionAgregar  = accionAgregar;
		this.accionEliminar = accionEliminar;
	}
        
        
	public DireccionesHelper(String accionAgregar,String accionEliminar, String accionEditar, String accionEditarAceptar) {
		super();
		this.accionAgregar  = accionAgregar;
		this.accionEliminar = accionEliminar;
		this.accionEditar= accionEditar;
		/*this.accionEditarAceptar = accionEditarAceptar;*/
	}

	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param request Trae toda la informacion que se a guardado sobre el usuario
	 */
	public void setProperties(HttpServletRequest request)
		throws HelperException {
		HttpSession session = request.getSession();

		Folio folio = null;
		
		turno = (Turno)session.getAttribute(WebKeys.TURNO);

		try{

			if(this.folioSesion!=null){
				if(session!=null){
					folio = (Folio) session.getAttribute(this.folioSesion);
				}
				if(folio!=null){
					direccionesExistentes = folio.getDirecciones();
				}
			}
			if(direccionesExistentes == null) {
				direccionesExistentes = new Vector();
			}

			if(session!=null){
				direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
			}
			if(direcciones == null) {
				direcciones = new Vector();
				session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
			}
			
			// Si la dirección tiene usuario temporal entonces no es definitiva
	        // TFS 3372
			dirtmp:
			for(Iterator dirExisteTmp = direccionesExistentes.iterator();
				dirExisteTmp.hasNext();)
			{
				Direccion direccion = (Direccion) dirExisteTmp.next();
				
				if (direccion.getUsuarioCreacionTMP() != null && direccion.getUsuarioCreacion()==null)/* && !(turno!=null && turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO)
						&&(turno.getIdFase().equals(CFase.CAL_CALIFICACION) || turno.getIdFase().equals(CFase.CAL_DIGITACION))))*/
				{
					dirExisteTmp.remove();
					for(Iterator dirTmpItera = direcciones.iterator();
					dirTmpItera.hasNext();)
					{
						Direccion direccion2 = (Direccion) dirTmpItera.next();
						if (direccion.getIdDireccion().equals(direccion2.getIdDireccion()))
						{
							continue dirtmp;
						}
					}
					direcciones.add(direccion);
				}
			}

			List ejes = null;
			if(session!=null){
				ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
			}
			if(ejes == null){
				ejes = new Vector();
			}

			/////////////////////////////////
			List tmp = new ArrayList();
			tmp.addAll(ejes);
			Iterator itEje = tmp.iterator();
			while (itEje.hasNext()) {
				ElementoLista elementoEje = (ElementoLista) itEje.next();
				if (elementoEje.getId().equals(CEje.ID_EJE_NUMERO)) {
					tmp.remove(elementoEje);
					break;
				}
			}
			///////////////////////////////

			ejesHelper = new ListaElementoHelper();
			ejesHelper.setCssClase("camposformtext");
			ejesHelper.setOrdenar(false);
			ejesHelper.setTipos(tmp);

			List ejes2 = null;
			if(session!=null){
				ejes2 = (List)session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
			}
			ejes2Helper = new ListaElementoHelper();
			ejes2Helper.setCssClase("camposformtext");
			ejes2Helper.setOrdenar(false);
			ejes2Helper.setTipos(ejes2);

			textHelper = new TextHelper();
			textAreaHelper = new TextAreaHelper();
		}catch (Throwable t){
			t.printStackTrace();
			HelperException he = new HelperException();
			throw he;
		}
	}

	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out)
		throws IOException, HelperException {

		try{

			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.write("<tr>");
			out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.write("<td class=\"bgnsub\">Direcciones Adicionadas al Folio</td>");
			out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"" + request.getContextPath() + "/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
			out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"" + request.getContextPath() + "/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.write("</tr>");
			out.write("</table>");


                        // bug 03566  -----------------------------
                        // - calcular count de direcciones definitivas
                        //   para renumerar las temporales

                        int local_t0_Count__Folio_Direcciones; // T0:count( 'folio/direcciones' )
                        local_t0_Count__Folio_Direcciones = 0;

                        local_t0_Count__Folio_Direcciones = ( null != direccionesExistentes )?(direccionesExistentes.size()):(0);

                        // ----------------------------------------
        List lstIdUtlizados = new ArrayList();
        int numEdit = 0;
		if(!direccionesExistentes.isEmpty() || !direcciones.isEmpty()){  
			
			//Tablas donde se muestran los ciudadanos insertados
			if(direccionesExistentes.size() > 0){
                                int i=0;
				Iterator itDirecccion = direccionesExistentes.iterator();
				int indexDireccion = 1;
				out.write("<table width=\"100%\" class=\"camposform\">");
				while(itDirecccion.hasNext()){
					Direccion direccion = (Direccion) itDirecccion.next();

                    // no se muestran las direcciones a eliminar
                    // no se incluyen en el count acumulado
                    
					
					if( direccion.isToDelete() ) {
                    	direcciones.add(direccion);
                    	itDirecccion.remove();
                        local_t0_Count__Folio_Direcciones --;
                        continue;
                    }
					//List lstdirecciones = this.metodoOrdenarDir(direcciones);
					if(accionEditar != null && !accionEditar.equals("")){
						direcciones = this.metodoOrdenarDir(direcciones);
						if(request.getSession() != null)
							request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
						if(!direcciones.isEmpty()){
							Iterator iteraDirecciones = direcciones.iterator();						
							while(iteraDirecciones.hasNext()){
								Direccion dirPrime = (Direccion)iteraDirecciones.next();
								boolean yaEstaituli = false;
								
								for(int j = 0; j<lstIdUtlizados.size(); j++){
									if(dirPrime != null && dirPrime.getIdMatricula() != null &&
										dirPrime.getIdMatricula().equals(lstIdUtlizados.get(j))){
										yaEstaituli = true;
									}else if(dirPrime != null && dirPrime.getOrden() != null && lstIdUtlizados.get(j) != null &&
											dirPrime.getOrden().equals(Integer.valueOf(String.valueOf(lstIdUtlizados.get(j))))){
										yaEstaituli = true;
									}
								}
								if(!yaEstaituli && dirPrime.getOrden() != null && direccion.getIdDireccion() != null && 
									dirPrime.getOrden().intValue() <= Integer.valueOf(direccion.getIdDireccion()).intValue() &&
									!dirPrime.isToDelete()){
									pintarDir(request, out, dirPrime, indexDireccion, i, numEdit);
									numEdit++;
									i++;
									indexDireccion++;
									lstIdUtlizados.add(String.valueOf(dirPrime.getOrden()));
									//break;
								}
							}
						}
					}

					out.write("<tr>");
					out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
					String mostrarDir = "";

					if(direccion.getEspecificacion() == null){
						mostrarDir = ((direccion.getEje()!=null&&direccion.getEje().getNombre()!=null&&!(direccion.getEje().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje().getNombre():"");
						mostrarDir += "&nbsp;&nbsp;";
						mostrarDir += ((direccion.getValorEje()!=null)?direccion.getValorEje():"");
						mostrarDir += "&nbsp;&nbsp;&nbsp;";
						mostrarDir += ((direccion.getEje1()!=null&&direccion.getEje1().getNombre()!=null&&!(direccion.getEje1().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje1().getNombre():"");
						mostrarDir += "&nbsp;&nbsp;";
						mostrarDir += ((direccion.getValorEje1()!=null)?direccion.getValorEje1():"");
						mostrarDir += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					} else{
						mostrarDir = direccion.getEspecificacion();
					}
					out.write("<td class=\"titulotbcentral\"><b>"+(""+indexDireccion)+"&nbsp;&nbsp;-</b>&nbsp;&nbsp;" + mostrarDir + "</td>");
					out.write("<td width=\"20\" align=\"center\" ></td>");
                                        if( isEnabledDeleteFromDefinitivas() ) {
                                            out.write("<td width=\"30\"><a href=\"javascript:"+this.jsFunctionNameDeleteFromDefinitivas +"('" + direccion.getIdDireccion()+ "','" + (actionIdDeleteFromDefinitivas!=null?actionIdDeleteFromDefinitivas:"")+ "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_eliminar.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
                                        }
                                        if( isEnabledEditFromDefinitivas() ) {
                                            out.write("<td width=\"30\"><a href=\"javascript:"+this.jsFunctionNameEditFromDefinitivas +"('" + direccion.getIdDireccion() + "','" + (actionIdEditFromDefinitivas!=null?actionIdEditFromDefinitivas:"")+ "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_editar_on.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
                                        }
                                        
					out.write("<td width=\"20\"></td>");
					out.write("</tr>");
                    i++;
					indexDireccion++;
				}
				out.write("</table>");
			}
			
			if(lstIdUtlizados.isEmpty() && accionEditar != null && !accionEditar.equals("")){
				direcciones = this.metodoOrdenarDir(direcciones);
				if(request.getSession() != null)
					request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
			}

			if(direcciones.size()>0){
				int i=0;
				Iterator itDirecccion = direcciones.iterator();

                // indexDireccion: usado para visualizacion
                //              i: usado para acciones de epliminacion/modificacion

                int indexDireccion;
                // indexDireccion = 1;
                indexDireccion = local_t0_Count__Folio_Direcciones + 1+lstIdUtlizados.size();

                out.write("<table width=\"100%\" class=\"camposform\">");
				while(itDirecccion.hasNext()){
					Direccion direccion = (Direccion) itDirecccion.next();
					Iterator iterAUTI = lstIdUtlizados.iterator();
					boolean	swesta = false;
					while(iterAUTI.hasNext() && !swesta){
						String dirAxuUtili = (String)iterAUTI.next();
						if(direccion != null && direccion.getIdDireccion() != null && 
							direccion.getIdDireccion().equals(dirAxuUtili)){
							swesta = true;
						}else if(direccion != null && direccion.getOrden() != null && dirAxuUtili != null &&
								direccion.getOrden().equals(Integer.valueOf(String.valueOf(dirAxuUtili)))){
							swesta = true;
						}
					}
					if(!direccion.isToDelete() && !swesta)/* || (turno!=null && turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO)
							&&(turno.getIdFase().equals(CFase.CAL_CALIFICACION) || turno.getIdFase().equals(CFase.CAL_DIGITACION))))*/{
						out.write("<tr>");
						out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
	
						String mostrarDir = "";
	
						if(direccion.getEspecificacion() == null){
							mostrarDir = ((direccion.getEje()!=null&&direccion.getEje().getNombre()!=null&&!(direccion.getEje().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje().getNombre():"");
							mostrarDir += "&nbsp;&nbsp;";
							mostrarDir += ((direccion.getValorEje()!=null)?direccion.getValorEje():"");
							mostrarDir += "&nbsp;&nbsp;&nbsp;";
							mostrarDir += ((direccion.getEje1()!=null&&direccion.getEje1().getNombre()!=null&&!(direccion.getEje1().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje1().getNombre():"");
							mostrarDir += "&nbsp;&nbsp;";
							mostrarDir += ((direccion.getValorEje1()!=null)?direccion.getValorEje1():"");
							mostrarDir += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						} else{
							mostrarDir = direccion.getEspecificacion();
						}
						out.write("<td class=\"titulotbcentral\"><b>"+(""+indexDireccion)+"&nbsp;&nbsp;-</b>&nbsp;&nbsp;" + mostrarDir + "</td>");
						out.write("<td width=\"20\" align=\"center\" ></td>");
                        if(this.dirTemporales!=null && this.dirTemporales.size()==direcciones.size() || turno==null){
                        	//out.write("<td width=\"30\"><a href=\"javascript:"+this.funcionQuitar+"('" + i + "','" + (accionEliminar!=null?accionEliminar:"")+ "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_eliminar.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
                        	out.write("<td width=\"30\"><a href=\"javascript:"+this.funcionQuitar+"('" + numEdit + "','" + (accionEliminar!=null?accionEliminar:"")+ "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_eliminar.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
                        	if(accionEditar != null && !accionEditar.equals(""))
                        		out.write("<td width=\"30\"><a href=\"javascript:"+this.funcionEditardir+"('" + numEdit + "','" + (accionEditar!=null?accionEditar:"")+ "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_editar_on.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
    						out.write("</tr>");
						}else{
							//out.write("<td width=\"30\"><a href=\"javascript:"+this.funcionQuitar+"('" + (indexDireccion-local_t0_Count__Folio_Direcciones) + "','" + (accionEliminar!=null?accionEliminar:"")+ "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_eliminar.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
							out.write("<td width=\"30\"><a href=\"javascript:"+this.funcionQuitar+"('" + numEdit + "','" + (accionEliminar!=null?accionEliminar:"")+ "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_eliminar.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
							if(accionEditar != null && !accionEditar.equals(""))
								out.write("<td width=\"30\"><a href=\"javascript:"+this.funcionEditardir+"('" + numEdit + "','" + (accionEditar!=null?accionEditar:"")+ "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_editar_on.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
							out.write("</tr>");
						}
                        numEdit++;
						indexDireccion++;
					}
					i++;
				}
				out.write("</table>");
			}
		} else {
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td class=\"titulotbcentral\">" + "No hay Direcciones asociadas al folio." + "</td>");
			out.write("</tr>");
			out.write("</table>");
		}
		if(mostrarAgregarNueva){
			out.write("<br>");
			out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.write("<tr>");
			out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\"></td>");
			out.write("<td class=\"bgnsub\">Agregar Direcci&oacute;n</td>");
			out.write("<td width=\"16\" class=\"bgnsub\"><img src=\""+request.getContextPath()+"/jsp/images/ico_matriculas.gif\" width=\"16\" height=\"21\"></td>");
			out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\"></td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("<table width=\"100%\" class=\"camposform\">");
			out.write("<tr>");
			out.write("<td width=\"20\"><img src=\""+request.getContextPath()+"/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
			out.write("<td>Direcci&oacute;n</td>");
			out.write("<td>&nbsp;</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>&nbsp;</td>");
			out.write("<td>");
			
			Direccion direccionEdit = null;
			if(request.getSession() != null && request.getSession().getAttribute(AWModificarFolio.ELEMENTO_EDITAR) != null)
				direccionEdit = (Direccion)request.getSession().getAttribute(AWModificarFolio.ELEMENTO_EDITAR);
			
			try {
				ejesHelper.setId(AWModificarFolio.FOLIO_EJE1);
				ejesHelper.setNombre(AWModificarFolio.FOLIO_EJE1);
				ejesHelper.setFuncion("onChange=\"javascript:"+funcionSugerirEspecificacion+"()\"");
				if(direccionEdit != null && direccionEdit.getEje() != null && direccionEdit.getEje().getIdEje() != null){
					ejesHelper.setSelected(direccionEdit.getEje().getIdEje());
				}
				ejesHelper.render(request,out);
			}catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}
			out.write("</td>");
			out.write("<td>");
			try {
				textHelper.setNombre(AWModificarFolio.FOLIO_VALOR1);
				textHelper.setCssClase("camposformtext");
				textHelper.setId(AWModificarFolio.FOLIO_VALOR1);
				textHelper.setFuncion("onChange=\"javascript:"+funcionSugerirEspecificacion+"()\"");
				if(direccionEdit != null && direccionEdit.getValorEje() != null){
					request.getSession().setAttribute(textHelper.getId(), direccionEdit.getValorEje());
				}
				textHelper.render(request,out);
			}catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}
			out.write("</td>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>&nbsp;</td>");
			out.write("<td>");

			try {
				ejes2Helper.setId(AWModificarFolio.FOLIO_EJE2);
				ejes2Helper.setNombre(AWModificarFolio.FOLIO_EJE2);
				ejes2Helper.setShowInstruccion(true);
				ejes2Helper.setFuncion("onChange=\"javascript:"+funcionSugerirEspecificacion+"()\"");
				if(direccionEdit != null && direccionEdit.getEje1() != null && direccionEdit.getEje1().getIdEje() != null){
					ejes2Helper.setSelected(direccionEdit.getEje1().getIdEje());
				}
				ejes2Helper.render(request,out);
			}catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}

			out.write("</td>");
			out.write("<td>");
			try {
				textHelper.setNombre(AWModificarFolio.FOLIO_VALOR2);
				textHelper.setCssClase("camposformtext");
				textHelper.setId(AWModificarFolio.FOLIO_VALOR2);
				textHelper.setFuncion("onChange=\"javascript:"+funcionSugerirEspecificacion+"()\"");
				if(direccionEdit != null && direccionEdit.getValorEje1() != null){
					request.getSession().setAttribute(textHelper.getId(), direccionEdit.getValorEje1());
				}
				textHelper.render(request,out);
			}catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}
			out.write("</td>");
			out.write("</tr>");

			out.write("<tr>");
			out.write("<td>&nbsp;</td>");
			out.write("<td>Especificaci&oacute;n</td>");
			out.write("<td>");
			String folioEspecificacion = "";
			try {
				textHelper.setNombre(AWModificarFolio.FOLIO_ESPECIFICACION_DIR);
				textHelper.setSize("50");
				textHelper.setCssClase("campositem");
				textHelper.setId(AWModificarFolio.FOLIO_ESPECIFICACION_DIR);
				
				if(direccionEdit != null && direccionEdit.getEje() != null && direccionEdit.getEje().getNombre() != null)
					folioEspecificacion = folioEspecificacion+" "+direccionEdit.getEje().getNombre().trim();
				if(direccionEdit != null && direccionEdit.getValorEje() != null)
					folioEspecificacion = folioEspecificacion+" "+direccionEdit.getValorEje().trim();
				if(direccionEdit != null && direccionEdit.getEje1() != null && direccionEdit.getEje1().getNombre() != null)
					folioEspecificacion = folioEspecificacion+" "+direccionEdit.getEje1().getNombre().trim();
				if(direccionEdit != null && direccionEdit.getValorEje1() != null)
					folioEspecificacion = folioEspecificacion+" "+direccionEdit.getValorEje1().trim();
				textHelper.setReadonly(true);
				if(folioEspecificacion != null && !folioEspecificacion.equals(""))
					request.getSession().setAttribute(AWModificarFolio.FOLIO_ESPECIFICACION_DIR,folioEspecificacion);
				else
					request.getSession().setAttribute(AWModificarFolio.FOLIO_ESPECIFICACION_DIR,"");
				textHelper.render(request,out);
			}catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}
			out.write("</td>");
			out.write("</tr>");

			textHelper.setReadonly(false);

			out.write("<tr>");
			out.write("<td>&nbsp;</td>");
			out.write("<td>Complementaci&oacute;n</td>");
			out.write("<td>");
			try {
				textAreaHelper.setNombre(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);
				textAreaHelper.setCols("50");
				textAreaHelper.setRows("5");
				textAreaHelper.setCssClase("camposformtext");
				textAreaHelper.setId(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);
				if(direccionEdit != null && direccionEdit.getEspecificacion() != null && folioEspecificacion != null){
					String newSpec = direccionEdit.getEspecificacion().replaceAll(folioEspecificacion.trim(), "").trim();
					request.getSession().setAttribute(AWModificarFolio.FOLIO_COMPLEMENTO_DIR,newSpec);
				}
				textAreaHelper.render(request,out);
			}catch(HelperException re){
				out.println("ERROR " + re.getMessage());
			}
			out.write("</td>");
			out.write("</tr>");
			out.write("<tr>");


			out.write("<td>&nbsp;</td>");
			out.write("<td colspan=\"2\" align=\"right\">");

			if(nombreFormaEdicionDireccion != null){
				out.println("<script>");
				out.println("function " + funcionSugerirEspecificacion + "()" + "{");

				out.println(""
						  + "		var selectedIndexEje1 = document."+this.nombreFormaEdicionDireccion + "." + AWModificarFolio.FOLIO_EJE1 + ".selectedIndex; \n"
						  + "		var selectedIndexEje2 = document."+this.nombreFormaEdicionDireccion + "." + AWModificarFolio.FOLIO_EJE2 + ".selectedIndex; \n"
						  + "		var textIndexEje2 = document."+this.nombreFormaEdicionDireccion + "." + AWModificarFolio.FOLIO_EJE2 + ".options[selectedIndexEje2].text;  \n\n"
						  + "		var optionIndexEje2 = document."+this.nombreFormaEdicionDireccion + "." + AWModificarFolio.FOLIO_EJE2 + ".options[selectedIndexEje2].value;  \n\n"

						  + "       if(optionIndexEje2=='"+WebKeys.SIN_SELECCIONAR+"'){\n"
						  + "            textIndexEje2 = ' ';\n"
					      + "       }\n"

						  + "		document."+this.nombreFormaEdicionDireccion + "." + AWModificarFolio.FOLIO_ESPECIFICACION_DIR + ".value = \n"
						  + "		document."+this.nombreFormaEdicionDireccion + "." + AWModificarFolio.FOLIO_EJE1 + ".options[selectedIndexEje1].text + ' ' + \n"
						  + "		document."+this.nombreFormaEdicionDireccion + "." + AWModificarFolio.FOLIO_VALOR1 + ".value + ' ' + \n"
						  + "		textIndexEje2 + ' ' + \n"
						  + "		document."+this.nombreFormaEdicionDireccion + "." + AWModificarFolio.FOLIO_VALOR2 + ".value ;\n");
				out.println("	}");
				out.println("</script>");
			}

			out.write("<table  border=\"0\" class=\"camposform\">");
			if(request.getSession() != null && request.getSession().getAttribute(AWModificarFolio.ELEMENTO_EDITAR) != null
				&& (request.getParameter(WebKeys.ACCION).equals(AWModificarFolio.EDITAR_DIRECCION_CORRECCION_TEMP)
					|| request.getParameter(WebKeys.ACCION).equals(AWModificarFolio.FOLIOEDIT_DIRECCIONDEFINITIVA_EDITITEM_ACTION))){
				if(request.getParameter(WebKeys.ACCION).equals(AWModificarFolio.EDITAR_DIRECCION_CORRECCION_TEMP))
					accionEditar = AWModificarFolio.EDITAR_DIRECCION_CORRECCION_ACEPTAR_TEMP;
				else if(request.getParameter(WebKeys.ACCION).equals(AWModificarFolio.FOLIOEDIT_DIRECCIONDEFINITIVA_EDITITEM_ACTION))
					accionEditar = AWModificarFolio.EDITAR_DIRECCION_CORRECCION_ACEPTAR_DEF;
				
				out.write("<tr align=\"right\">");
				out.write("<td>Aceptar Editar</td>");
				out.write("</tr>");
				out.write("<tr align=\"right\">");
				out.write("<td><a href=\"javascript:"+funcionCambiarAccion+"('" + (accionEditar!=null?accionEditar:"") + "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_editar_on.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
			}
			else{
				out.write("<tr align=\"right\">");
				out.write("<td>Agregar</td>");
				out.write("</tr>");
				out.write("<tr align=\"right\">");
				out.write("<td><a href=\"javascript:"+funcionCambiarAccion+"('" + (accionAgregar!=null?accionAgregar:"") + "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_anadir.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
			}
			
			out.write("</tr>");
			out.write("</table>");

			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
		}

		}catch (Throwable t){
			t.printStackTrace();
			HelperException he = new HelperException();
			throw he;
		}


	}
	/**
	 * @param string
	 */
	public void setAccionAgregar(String string) {
		accionAgregar = string;
	}
	
	/**
	 * @param string
	 */
	public void setAccionEditar(String string) {
		accionEditar = string;
	}

	/**
	 * @param string
	 */
	public void setAccionEliminar(String string) {
		accionEliminar = string;
	}
	
	/**
	 * @param string
	 */
	public void setAccionEditarAceptar(String string) {
		accionEditarAceptar = string;
	}

	/**
	 * @return
	 */
	public String getFolioSesion() {
		return folioSesion;
	}

	/**
	 * @param string
	 */
	public void setFolioSesion(String string) {
		folioSesion = string;
	}

    /**
     * Actualizar el valor del atributo funcionCambiarAccion
     * @param funcionCambiarAccion El nuevo valor del atributo funcionCambiarAccion.
     */
    public void setFuncionCambiarAccion(String funcionCambiarAccion) {
        this.funcionCambiarAccion = funcionCambiarAccion;
    }

	/**
	 * @return
	 */
	public String getFuncionQuitar() {
		return funcionQuitar;
	}

	/**
	 * @param string
	 */
	public void setFuncionQuitar(String string) {
		funcionQuitar = string;
	}

	/**
	 * @param b
	 */
	public void setMostrarAgregarNueva(boolean b) {
		mostrarAgregarNueva = b;
	}

	/**
	 * @return
	 */
	public String getNombreFormaEdicionDireccion() {
		return nombreFormaEdicionDireccion;
	}

	/**
	 * @param string
	 */
	public void setNombreFormaEdicionDireccion(String string) {
		nombreFormaEdicionDireccion = string;
	}
	public List getDirTemporales() {
		return dirTemporales;
	}
	public void setDirTemporales(List dirTemporales) {
		this.dirTemporales = dirTemporales;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	public void pintarDir(HttpServletRequest request, JspWriter out, Direccion direccion, int indexDireccion ,int i, int numEdit)throws IOException, HelperException {
		out.write("<tr>");
		out.write("<td width=\"20\"><img src=\"" + request.getContextPath() + "/jsp/images/ind_campotxt.gif\" width=\"20\" height=\"15\"></td>");
		String mostrarDir = "";

		if(direccion.getEspecificacion() == null){
			mostrarDir = ((direccion.getEje()!=null&&direccion.getEje().getNombre()!=null&&!(direccion.getEje().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje().getNombre():"");
			mostrarDir += "&nbsp;&nbsp;";
			mostrarDir += ((direccion.getValorEje()!=null)?direccion.getValorEje():"");
			mostrarDir += "&nbsp;&nbsp;&nbsp;";
			mostrarDir += ((direccion.getEje1()!=null&&direccion.getEje1().getNombre()!=null&&!(direccion.getEje1().getNombre().equals(WebKeys.SIN_SELECCIONAR)))?direccion.getEje1().getNombre():"");
			mostrarDir += "&nbsp;&nbsp;";
			mostrarDir += ((direccion.getValorEje1()!=null)?direccion.getValorEje1():"");
			mostrarDir += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		} else{
			mostrarDir = direccion.getEspecificacion();
		}
		out.write("<td class=\"titulotbcentral\"><b>"+(""+indexDireccion)+"&nbsp;&nbsp;-</b>&nbsp;&nbsp;" + mostrarDir + "</td>");
		out.write("<td width=\"20\" align=\"center\" ></td>");
                            if( isEnabledDeleteFromDefinitivas() ) {
                                out.write("<td width=\"30\"><a href=\"javascript:"+this.funcionQuitar +"('" + numEdit + "','" + (accionEliminar!=null?accionEliminar:"")+ "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_eliminar.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
                            }
                            if( isEnabledEditFromDefinitivas() ) {
                                out.write("<td width=\"30\"><a href=\"javascript:"+this.funcionEditardir +"('" + numEdit + "','" + (accionEditar!=null?accionEditar:"")+ "')\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_short_editar_on.gif\" name=\"Folio\" width=\"35\" height=\"25\" border=\"0\"></a></td>");
                            }
                                          
                            
		out.write("<td width=\"20\"></td>");
		out.write("</tr>");		
	}
	public List metodoOrdenarDir(List Direcciones){
		List lstDirecciones = new ArrayList();
				
		int i = 0;
		boolean swSalir = false;
		while( i <= 50 && !swSalir){						
			for(int j = 0; j<Direcciones.size(); j++){
				Direccion  dirJ = (Direccion)Direcciones.get(j);				
				if(dirJ != null && dirJ.getOrden() != null &&
					i == dirJ.getOrden().intValue() && !dirJ.isToDelete()){				
					lstDirecciones.add(dirJ);
					Direcciones.remove(j);
				}else if(dirJ != null && dirJ.getIdDireccion() != null &&
					i == Integer.valueOf(dirJ.getIdDireccion()).intValue() && !dirJ.isToDelete()){				
					lstDirecciones.add(dirJ);
					Direcciones.remove(j);
				}
			}
			if(lstDirecciones.size() == Direcciones.size()){
				swSalir = true;
			}
			i = i + 1;
		}
		lstDirecciones.addAll(Direcciones);
		
		
		
		/*for(int i = 0; i<lstDirecciones.size(); i++){						
			Direccion  dirI = (Direccion)lstDirecciones.get(i);			
			for(int j = i; j<Direcciones.size(); j++){
				Direccion  dirJ = (Direccion)lstDirecciones.get(j);				
				if(i != j && dirJ != null && dirJ.getOrden() != null && dirI != null && dirI.getOrden() != null &&  
					dirI.getOrden().intValue() > dirJ.getOrden().intValue()){
					
					Direccion DireccionAuc =(Direccion)lstDirecciones.get(i);
					lstDirecciones.set(i,(Direccion)lstDirecciones.get(j));
					lstDirecciones.set(i,DireccionAuc);
					j = 0;
				}
			}			
		}*/
		   
		return lstDirecciones;
	}
}











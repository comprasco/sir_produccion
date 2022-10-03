package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.Anotacion;
//import gov.sir.core.negocio.modelo.AnotacionesFolio;
import gov.sir.core.negocio.modelo.BloqueoFolio;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.EstadoHistoria;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.HistorialAreas;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.TrasladoDatos;
import gov.sir.core.negocio.modelo.TrasladoFundamento;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoFolio;
import gov.sir.core.negocio.modelo.TurnoFolioTramiteMig;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CFolioDerivado;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CHistorialAreas;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CTipoMostrarFolioHelper;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones;
import gov.sir.core.web.acciones.registro.AWFolio;
import gov.sir.core.web.helpers.correccion.DireccionesHelper;
import gov.sir.core.web.helpers.registro.MostrarAnotacionHelper;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;
import org.auriga.smart.SMARTKeys;
import org.auriga.util.FechaConFormato;


/**
 * @author ddiaz
 * Helper encargado de mostrar los datos del folio con paginador incluido.
 * Datos encabezado, anotaciones.
 */
public class MostrarFolioHelper extends Helper {
	
	//SIR-41(R1)	

   public static final String ANOTACIONEDITLINK_EDITARSEGUNORDEN = "ANOTACIONEDITLINK_EDITARSEGUNORDEN";
   public static final String ANOTACIONEDITLINK_EDITARSEGUNID    = "ANOTACIONEDITLINK_EDITARSEGUNID";

    // decidir que se va a tener en cuenta cuando se presione el icono de editar
    private String anotacionEditLink = ANOTACIONEDITLINK_EDITARSEGUNORDEN;

    private boolean temporalConContraparteDefinitivaEnabled;

    //Datos asociados a la vista y a la accion web actual.
	/*  Atributo donde se pone la vista actual del helper*/
	String vistaActual="";
	/*  Atributo donde se pone si el folio solo es de consulta o no.)
	 *  De consulta: -Se muestran todas las anotaciones (temporales y definitivas (con paginador)) sin opcion de edicion o modificacion.
	 *  No de consulta: -Se muestran las anotaaciones en 2 grupos definitivas (con paginador) y temporales con opciones de edicion y eliminacion de anotaciones.
	 *  Por defecto se encontrar en el estado No de consulta.
	 **/
	boolean consulta=false;

	boolean editable=true;

    boolean editarAnotacionesNoTemporales = false;
    
    boolean editarAnotacionesTemporales = true;


    // bug 05042 ---------------------------------------------
    boolean checkboxController_MultiSelectEnabled = false;
	String  checkboxController_JsControllerName   = "";
    //String  checkboxController_SourceFormId       = "";
    //String  checkboxController_TargetFormId       = "";
    String  checkboxController_TargetFormFieldId        = ""; // text-field identifier
    String  checkboxController_SourceFormFieldId        = "ESCOGER_ANOTACION_CANCELACION";
    boolean checkboxController_MultiSelectDebugEnabled = false;


    // -------------------------------------------------------

    boolean mostrarAnotacionesCancelacioMultiple = false;

    boolean mostrarMedidasYGravamenes = false;

    boolean mostrarAlertaInconsistencia=false;

	int tipoMostrarFolio= CTipoMostrarFolioHelper.TIPO_NORMAL;

	boolean isCorreciones = false;

	Calendar anio;

	/*Variables para tipo comprimido*/
	String idAnotacionCancelada="";
	String ordenCancelada="";
	String nomOrdenCancelada="";
	String nombreAccionCancelacion="";
	String nombreFormaCancelacion="";
	
	List foliosDerivadoPadre;
	
	List foliosDerivadoHijo;
        
        List historialAreas;
	
	/*En este atributo se guarda la session del jsp q llama al helper */
	HttpSession session=null;

	/*En este atributo se guarda el request del jsp q llama al helper */
	HttpServletRequest request=null;



	/*
	 *	Los siguientes atributos cumplen la mision de  indicar que datos se deben o no mostrar de la parte del encabezado del folio
	 *  aqui esta expresada la logica por default todas seran true
	 *  Si mostrarGravamenes = true; --> en el encabezado se muestra la informacion de los gravamenes y medidas cautelares del folio
	 *  Si mostrarCabidaLinderos = true; --> en el encabezado se muestra la informacion de la descripcion de las cabidas y linderos.
	 *  Si mostrarComplementacion = true; --> en el encabezado se muestra la informacion de la complementacion del folio.
	 *  Si mostrarDireccionInmueble = true; --> en el encabezado se muestra la informacion de la direccion del inmueble del folio.
	 *  Si mostrarMayorExtension = true; --> en el encabezado se muestra la informacion de la mayor extension del folio.
	 *  Si mostrarAperturaFolio = true; --> en el encabezado se muestra la informacion de la apertura del folio.ç
	 *  Si mostrarDatosDocumento = true; --> en el encabezado se muestra la informacion de los datos del documento del folio.
	 *  Si mostrarRelaciones = true; --> en el encabezado se muestra la informacion de las relaciones del folio.
	 * 	Si mostrarDatosRelevantes = true; --> en el encabezado se muestra la informacion de los datos relevantes del folio.
	 *
	 *  La logica de como se muestra el encabezado se encuentra en drawEncabezado()
	 */
	 boolean mostrarEncabezado = true;
	 boolean mostrarGravamenes = false;
	 boolean mostrarCabidaLinderos = false;
	 boolean mostrarComplementacion = false;
	 boolean mostrarDireccionInmueble = false;
	 /**
	 * @author: Cesar Ramirez
	 * @change: 1245.HABILITAR.TIPO.PREDIO
	 * Variable que define si dibuja el campo del Tipo del Predio
	 **/
	 boolean mostrarTipoPredio = false;
	boolean mostrarTipoPredioEnDireccionInmueble = false;
	 boolean mostrarMayorExtension =false;
	 boolean mostrarAperturaFolio=false;
	 boolean mostrarDatosDocumento=false;
	 boolean mostrarRelaciones=false;
	 boolean mostrarDatosRelevantes=false;
	 boolean mostrarHistorialEstados=false;
	 boolean mostrarAnotacionesFolioTemporal=false;
         

	 /*
	 *	Los siguientes atributos cumplen la mision de  indicar que datos se deben o no mostrar de la parte del encabezado del folio
	 *  aqui esta expresada la logica por default todas seran true
	 *  Si mostrarAnotaciones = true; --> en el cuerpo se muestran las anotaciones temporales y definitivas del paginador
	 *
	 *  La logica de como se muestra el encabezado se encuentra en drawCuerpo()
	 */
	 boolean mostrarAnotaciones = true;

	/*En este atributo se guarda el folio que se va mostrar */
	Folio folio=null;

	/*En este atributo se guarda las variables del paginador*/
	int paginaInicial=0;//la pagina q se mostraria inicialmente de anotaciones.
	int inicio;//el index inicio del paginador en string.
	int cantidad;//la cantidad de anotaciones en cache en string.
	String nombreForma;//nombre de la forma que se encuentra el paginadorAvanzadoHelper (la de la accion cambiarPagina);
	int tamanoCache=100;//variable donde se guarda el tamaño del cache del paginador por default sera 100.
	private boolean consultarAnotacionesDefinitivas = false;

	/*variables internas del helper*/
	boolean carga=true;
	String ocultarFolio="";
	String ocultarAnotaciones="";
	String ocultarComentarioEstado="";
	MostrarFechaHelper fechaHelper=null;
	long numeroAnotaciones=0;
	Turno turno=null;
	String  codCatastral="";
	String  codCatastralAnt="";
	boolean esFolioMayorExtension=false;
	List anotacionesDefinitivas=null;
	List anotacionesTemporales=null;
	MostrarAnotacionHelper mAnot=null;
	PaginadorAvanzadoHelper pag=null;
	String nombreFormaPaginador;// nombre de la forma usada en este helper para llamar la carga de datos del paginador.
	String nombrePaginador;// nombre llave para el paginador de este helper en la session
	String nombreResultado;// nombre llave para los datos de respuesta de este helper en la session
	String nombreFormaFolio;// nombre de la forma que tiene asociado el folio Helper
	String nombreAccionPaginador;// nombre de la accion relacionada con la forma de paginacion de anotaciones.
	String nombreAccionFolio;//nombre de la accion relacionada con la forma del folio
	String nombreAncla;//nombre del ancla para ver las anotaciones del folio
	String nombreOcultarAnotaciones;//nombre del paramatro donde se guarda el valor de ocultar o no las anotaciones
	String nombreOcultarFolio;//nombre del parametro donde se gaurda el valor de ocultar o no el folio
	String nombreOcultarComentarioEstado="OCULTAR_COMENTARIO_ESTADO";//nombre del parametro donde se gaurda el valor de ocultar o no el folio
	String nombreNumAnotacionTemporal;// nombre de la llave donde se va guardar el numero de la anotacion temporal, en caso de que no sea de consulta el folio
	String nombreNumPaginaActual;// nombre de la llave del parametro donde se guarda la pagina a la q se va cambiar el paginador
	String titulo="Folio";//nombre3 del titulo de la pestaña donde se meuestra el folio por default sera "Folio".
	//atributos seteados para encabezados
	/*ver detallerRelevantes */
	Turno turnoTramite;//turnoTramite seteado si se necesita ver datosRelevantes en el encabezado del folio
	List turnosTramiteFolio;//turnoTramite seteado si se necesita mostrar los turnos en tramite en el sistema folio.
	Turno turnoDeuda;//turnoDeuda seteado si se se necesita ver datosRelevantes en el encabezado del folio
	Usuario usuarioBloqueo;//usuarioBloqueo seteado si se se necesita ver datosRelevantes en el encabezado del folio
	/*ver gravamenes y medidas cautelares */
	java.math.BigDecimal totalAnotaciones;//el numero total anotaciones
	List gravamenes;// lista de gravamenes
	List medCautelares;// lista de medidas cautelares
	List falsaTradicion;// lista de gravamenes
	List anotacionesInvalidas;// lista de medidas cautelares
	List anotacionesPatrimonioFamiliar;// lista de patrimino Familiar
	List anotacionesAfectacionVivienda;// lista de afectacion de Vivienda
	

	/*atributos de estilos */
	String eCampoTexto = "";
	String eTitulos = "";
	String eTitulosSecciones = "";
	String eCampos = "";
	String eTituloFolio = "";

	/*atributos de imagenes*/
	String imagenSeparador = "";
	String imagenFolio = "";
	String imagenNAnotaciones = "";
	String imagenSeccionEncabezado = "";

	/*----- ATRIBUTOS DE EDICION DE ENCABEZADO --------------------*/
	/**
	 * Este flag sirve para determinarle el folio helper si debe permitir la edicion de los siguientes
	 * datos del encabezado.
	 * -direccion
	 * -codigo catastral
	 * -cabida y linderos
	 */
	//boolean edicionDatosEncabezado=true;

	boolean edicionDatosCodCatastral=false;
	boolean edicionDatosDireccion=false;
	boolean edicionDatosDireccionDefinitiva=false;
	boolean edicionDatosLinderos=false;
        boolean edicionDatosLinderosDefinidos=false;
	
	//pq
	boolean edicionDigitadorMasivo=false;
	boolean edicionDatosComplementacion=false;
	/**
	* @author: Cesar Ramirez
	* @change: 1245.HABILITAR.TIPO.PREDIO
	* Variable que define si muestra el combobox para la edición del campo.
	**/
	boolean edicionTipoPredio = false;
	/**
	 * este atributo define cual es el .do que se encargara de la edicion de los datos de encabezado
	 */
	String nombreDoEdicionDatosEncabezado="";

	/**
	 * este atributo define cual el nombre de la forma de la cual se va llamar las acciones de edicion de encabezado.
	 */
	String nombreFormaEdicionDatosEncabezado = "";

	/**
	 * este atributo define cual el nombre de la accion cuando se vaya a agregar las direcciones a temporal.
	 */
	String nombreAccionEdicionDireccion= "";

	/**
	 * este atributo define cual el nombre de la accion cuando se vaya a agregar las direcciones a temporal.
	 */
	String nombreAccionAgregarDireccion= "";

	/**
	 * este atributo define cual el nombre de la accion cuando se vaya a eliminar una direccion temporal del folio.
	 */
	String nombreAccionEliminarDireccion= "";

	/**
	 * este atributo define cual el nombre de la accion cuando se vaya a eliminar una direccion definitiva del folio.
	 */
	String nombreAccionEliminarDireccionDefinitiva= "";

	/**
	 * este atributo define cual el nombre de la forma de la cual se va llamar las acciones de edicion de encabezado.
	 */
	String nombreFormaEdicionDireccion = "";

	/**
	 * este atributo define cual el nombre de la accion cuando se vaya a editar el codigo catastral del folio.
	 */
	String nombreAcccionEdicionCodigoCatastral= "";

	/**
	 * este atributo define cual el nombre de la forma de la cual se va llamar las acciones de edicion de encabezado.
	 */
	String nombreFormaEdicionCodCatastral = "";

	/**
	 * este atributo define cual el nombre de la accion cuando se vaya a editar las cabidas y linderos del folio.
	 */
	String nombreAccionEdicionCabidaYLinderos= "";
	String nombreAccionEdicionComplementacion="";
	/**
	* @author: Cesar Ramirez
	* @change: 1245.HABILITAR.TIPO.PREDIO
	* Variable que define la acción a realizar en javascript al guardar la edición del tipo de predio.
	**/
	String nombreAccionEdicionTipoPredio= "";
	/**
	 * este atributo define cual el nombre de la forma de la cual se va llamar las acciones de edicion de encabezado.
	 */
	String nombreFormaEdicionLinderos = "";
	/**
	 * @author: Cesar Ramirez
	 * @change: 1245.HABILITAR.TIPO.PREDIO
	 * Variable que define el nombre del formulario a crear para la edición del campo tipo del predio.
	 **/
	String nombreFormaEdicionTipoPredio = "nada";
	
	String nombreFormaEdicionComplementacion="nada";

	/*----- FIN: ATRIBUTOS DE EDICION DE ENCABEZADO --------------------*/


	public static final String LISTA_ANOTACIONES_MULT_CANCELACION = "LISTA_ANOTACIONES_MULT_CANCELACION";

	private String accionGuardarAnotMultCancelacion = "";
    private boolean mostrarTurnos;

    private boolean enableCheckAnotacionesTemporales = false;
    private List dirTemporales = null;

    /**
    * @author      :  Julio Alcazar
    * @change      :  variables para los nuevos datos en ver detalles de folio
    * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
    */
    private Traslado trasladoDestino;
    private Traslado trasladoOrigen;
    Circulo circuloDestino;
    Circulo circuloOrigen;

    public Traslado getTrasladoDestino() {
        return trasladoDestino;
    }

    public void setTrasladoDestino(Traslado trasladoDestino) {
        this.trasladoDestino = trasladoDestino;
    }

    public Traslado getTrasladoOrigen() {
        return trasladoOrigen;
    }

    public void setTrasladoOrigen(Traslado trasladoOrigen) {
       this.trasladoOrigen = trasladoOrigen;
    }

    public Circulo getCirculoDestino() {
        return circuloDestino;
    }

    public void setCirculoDestino(Circulo circuloDestino) {
        this.circuloDestino = circuloDestino;
    }

    public Circulo getCirculoOrigen() {
        return circuloOrigen;
    }

    public void setCirculoOrigen(Circulo circuloOrigen) {
        this.circuloOrigen = circuloOrigen;
    }

    

	/**
	 * Este método recoje los atributos necesarios del request para poder
	 * inicializar los atributos propios de la clase
	 * @param req Trae toda la informacion que se a guardado sobre el usuario
	 */
    public void setProperties(HttpServletRequest req) throws HelperException {
		//inicializar el session
		session=req.getSession();
		this.request=req;
		//Obtencion del folio esto funciona con flags asociados al helper.
		folio=this.obtenerFolio();

		if(folio!=null){
			/*------inicializar los datos del helper-----*/

			//incializacion de los helpers necesitados.
			fechaHelper = new MostrarFechaHelper();
			mAnot = new MostrarAnotacionHelper();
			mAnot.setHayPaginador(true);
			mAnot.setNombreResultado(this.nombreResultado);
                        mAnot.setTemporalConContraparteDefinitivaEnabled( isTemporalConContraparteDefinitivaEnabled() );


                        if( ( null != anotacionEditLink )
                          &&( ANOTACIONEDITLINK_EDITARSEGUNID.equals( anotacionEditLink )  ) ) {

                            mAnot.setItemEdit_IdentifierFlag( MostrarAnotacionHelper.EDITIDENTIFIERFIELD_ID );

                        } // if


			PaginadorAvanzadoHelper paginadorHelper  = new PaginadorAvanzadoHelper();
			TextAreaHelper textAreaHelper = new TextAreaHelper();


			//incializacion de los variables de ocultar folio y ocultar anotaciones
			NumberFormat nf = NumberFormat.getInstance();
			ocultarFolio = request.getParameter(this.nombreOcultarFolio);
			if(ocultarFolio == null){
				ocultarFolio = (String)session.getAttribute(this.nombreOcultarFolio);
				if(ocultarFolio==null){
					ocultarFolio = "FALSE";
				}
			} else {
				session.setAttribute(this.nombreOcultarFolio,ocultarFolio);
			}


			ocultarAnotaciones = request.getParameter(this.nombreOcultarAnotaciones);
			if(ocultarAnotaciones == null){
				ocultarAnotaciones = (String)session.getAttribute(this.nombreOcultarAnotaciones);
				if(ocultarAnotaciones==null){
					ocultarAnotaciones = "TRUE";
				}
			} else {
				session.setAttribute(this.nombreOcultarAnotaciones,ocultarAnotaciones);
			}

			ocultarComentarioEstado = request.getParameter(this.nombreOcultarComentarioEstado);
			if(ocultarComentarioEstado == null){
				ocultarComentarioEstado = (String)session.getAttribute(this.nombreOcultarComentarioEstado);
				if(ocultarComentarioEstado==null){
					ocultarComentarioEstado = "TRUE";
				}
			} else {
				session.setAttribute(this.nombreOcultarComentarioEstado,ocultarComentarioEstado);
			}



			//poner el folio en la session
			session.setAttribute(AWPaginadorAnotaciones.FOLIO_HELPER, folio);

			//inicializacion valores folio

			codCatastral= (folio.getCodCatastral()==null)?"&nbsp;":folio.getCodCatastral();
			codCatastralAnt= (folio.getCodCatastralAnterior()==null)?"&nbsp;":folio.getCodCatastralAnterior();




			//-------------------------------------------INICIALIZANDO LAS VARIABLES PAGINADOR-----------------------
			//inicilizando la pagina actual(paginaActual por default la 1;
			int paginaActual=1;
			String pagActual= (String) request.getSession().getAttribute(AWPaginadorAnotaciones.NUM_PAGINA_ACTUAL);
			if(pagActual!=null){
				paginaActual= Integer.parseInt(pagActual);
			}
			Log.getInstance().debug(MostrarFolioHelper.class,"pagina actual =" + Integer.toString(paginaActual));
			//request.getSession().setAttribute(AWPaginadorAnotaciones.NUM_PAGINA_ACTUAL, new Integer(paginaActual));

			this.definirInicioYCantidad();


			/*inicializacion del helper del paginador
			*/
			pag= new PaginadorAvanzadoHelper();
			//iniciando sus properties
			pag.setId(this.nombrePaginador);
			pag.setNombreAccion(WebKeys.ACCION);
			pag.setTipoAccion(AWPaginadorAnotaciones.REFRESCAR_PAGINADOR);
			pag.setNombreForm(this.nombreForma);
			pag.setUrlAccionWeb(this.nombreAccionPaginador);
			pag.setVariablePagina(this.nombreNumPaginaActual);
                        pag.setCheckboxController_MultiSelectEnabled( isCheckboxController_MultiSelectEnabled() );
                        pag.setCheckboxController_TargetFormFieldId( getCheckboxController_TargetFormFieldId() );
                        pag.setCheckboxController_MultiSelectDebugEnabled( isCheckboxController_MultiSelectDebugEnabled() );



			if(consulta){
				Boolean OConsulta= new Boolean(consulta);
				request.getSession().setAttribute(AWPaginadorAnotaciones.TIPO_CONSULTA, OConsulta);
			}
			request.getSession().setAttribute(AWPaginadorAnotaciones.NOMBRE_NUM_PAGINA_ACTUAL, nombreNumPaginaActual);

			/*settear el nombre del paginador para q vaya al paginador indicado por el folio*/
			request.getSession().setAttribute(WebKeys.NOMBRE_PAGINADOR, this.nombrePaginador);

			/*obtencion objeto de Datos de respuesta de paginador*/
			DatosRespuestaPaginador RPag=(DatosRespuestaPaginador) request.getSession().getAttribute(this.nombreResultado);
			if(RPag!=null){
				anotacionesDefinitivas=RPag.getAnotacionesActual();
				numeroAnotaciones= RPag.getCantidadRegistros();//RPag.getNumeroAnotacionesDefinitivas();
				carga=false;
			}

			/*obtencion de la variable recarga*/
			Boolean recarga= (Boolean)request.getSession().getAttribute(WebKeys.RECARGA);
			if(recarga!=null && recarga.booleanValue()){
				carga=true;
			}

			//pq
				//if(this.edicionDigitadorMasivo)
					//carga=false;
			
			/*settear el nombre de la donde se guarda el numero de la anotacion temporal*/
			request.getSession().setAttribute(WebKeys.NOMBRE_NUM_ANOTACION_TEMPORAL, this.nombreNumAnotacionTemporal);
		}
    }


	/**
	 * Este método pinta en la pantalla de manera agradable una descripcion del error
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
    public void drawGUI(HttpServletRequest request1, JspWriter out)
        throws IOException, HelperException {

    	if(folio!=null){
			/*-------Parte javascript--------*/
			out.println("");

			out.println("<script type='text/javascript'>");
                        out.println("function valideKey(evt){");
                        out.println("var code = (evt.which) ? evt.which : evt.keyCode;");
                        out.println("if(code==8) {");
                        out.println("return true;");
                        out.println("} else if(code>=48 && code<=57) {");
                        out.println("return true;");
                        out.println("} else{");
                        out.println("return false;");
                        out.println("}");
                        out.println("}");
			out.newLine();
			out.println("function cambiarAccionCalificacion(text) {");
			out.newLine();
			out.println("	   document."+this.nombreFormaFolio+".ACCION.value = text;");
			out.newLine();
			out.println("      document."+this.nombreFormaFolio+".POSSCROLL.value =(document.body ? document.body.scrollTop :0);");
			out.newLine();
			out.println("	   document."+this.nombreFormaFolio+".submit();");
			out.newLine();
			out.println("	   }");
			out.newLine();
			out.println("function editarAnotacion(text){");
			out.newLine();
			out.println("	document.getElementById(\""+this.nombreNumAnotacionTemporal+"\").value = text;");
			out.newLine();			
			out.println("	cambiarAccionCalificacion('EDITAR_ANOTACION');");
			out.newLine();
			out.println("}");
			out.newLine();
			out.println("function editarCancelacion(text){");
			out.newLine();
			out.println("	document.getElementById(\""+this.nombreNumAnotacionTemporal+"\").value = text;");
			out.newLine();
			out.println("	cambiarAccionCalificacion('EDITAR_CANCELACION');");
			out.newLine();
			out.println("}");
			out.newLine();
			out.println("function eliminarAnotacionTemporal(text){");
			out.newLine();
			out.println("	if(confirm(\"Esta seguro que desea eliminar la anotación\")){");
			out.newLine();
			out.println("		document.getElementById(\""+this.nombreNumAnotacionTemporal+"\").value = text;");
			out.newLine();
			out.println("		cambiarAccionCalificacion('ELIMINAR_ANOTACION_TEMPORAL');");
			out.newLine();
			out.println("	}");
			out.newLine();
			out.println("}");
         out.newLine();
         // script para enviar cambios de anotacion en temporal con datos en definitivo
         if( isTemporalConContraparteDefinitivaEnabled() ) {

           out.println("function eliminarCambiosAnotacionDefinitivaTemporal(text){");
           out.newLine();
           out.println("	if(confirm(\"Esta seguro que desea eliminar los cambios de la anotación\")){");
           out.newLine();
           out.println("		document.getElementById(\""+this.nombreNumAnotacionTemporal+"\").value = text;");
           out.newLine();
           out.println("		cambiarAccionCalificacion('ELIMINAR_CAMBIOS_ANOTACION_DEFINITIVA_TEMPORAL');");
           out.newLine();
           out.println("	}");
           out.newLine();
           out.println("}");
           out.newLine();

         } // if

         out.newLine();



			out.println("	function centrar(){");
			out.newLine();
			if(request1 != null && request1.getSession() != null && 
				request1.getSession().getAttribute(WebKeys.FASE) != null &&
				request1.getSession().getAttribute(WebKeys.FASE) instanceof Fase &&
				((Fase)request1.getSession().getAttribute(WebKeys.FASE)).getEstacion() != null &&
				(((Fase)request1.getSession().getAttribute(WebKeys.FASE)).getEstacion().equals("SIR_ROL_CALIFICADOR")
				|| ((Fase)request1.getSession().getAttribute(WebKeys.FASE)).getEstacion().equals("SIR_ROL_AUXILIAR_CORRECCIONES")) ){
				out.println("		//document.location.href=\"#"+this.nombreAncla+"\";");
			}else{
				out.println("		document.location.href=\"#"+this.nombreAncla+"\";");
			}			
			out.newLine();
			out.println("	}");
			out.newLine();

			out.println("	function cargarListas(){");
			out.newLine();
			out.println("		document."+nombreFormaPaginador+".ACCION.value=\""+AWPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO+"\";");
			out.newLine();
			out.println("		document."+nombreFormaPaginador+"."+WebKeys.INICIO+".value=\""+Integer.toString(this.inicio)+"\";");			
			out.newLine();
			out.println("		document."+nombreFormaPaginador+"."+WebKeys.CANTIDAD+".value=\""+Integer.toString(this.cantidad)+"\";");
			out.newLine();
			out.println("		document."+nombreFormaPaginador+"."+CFolio.ID_MATRICULA+".value=\""+folio.getIdMatricula()+"\";");
			out.newLine();
			out.println("		document."+nombreFormaPaginador+"."+CFolio.ID_ZONA_REGISTRAL+".value=\""+ (folio.getZonaRegistral()!=null?folio.getZonaRegistral().getIdZonaRegistral():"")+"\";");
			out.newLine();
			out.println("		document."+nombreFormaPaginador+"."+WebKeys.NOMBRE_PAGINADOR +".value=\""+this.nombrePaginador+"\";");
			out.newLine();
			out.println("		document."+nombreFormaPaginador+"."+WebKeys.NOMBRE_RESULTADOS_PAGINADOR +".value=\""+this.nombreResultado+"\";");
			out.newLine();
			out.println("		document."+nombreFormaPaginador+"."+AWPaginadorAnotaciones.ANOTACIONES_DEFINITIVAS +".value=\""+(this.consultarAnotacionesDefinitivas?"TRUE":"FALSE")+"\";");
			out.newLine();
			out.println("		document."+nombreFormaPaginador+"."+AWPaginadorAnotaciones.ANOTACIONES_FOLIO_TEMPORAL +".value=\""+(this.mostrarAnotacionesFolioTemporal?"TRUE":"FALSE")+"\";");
			out.newLine();
			out.println("		document."+nombreFormaPaginador+".submit();");
			out.newLine();
			out.println("	}");
			out.newLine();
			out.println("</script>");
			//Libreria para mostrar el campo de participacion
			out.println("<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->");
			out.println("<!-- sof:block: \"alt behavior\" -->");

			out.println("<script type=\"text/javascript\">");
			out.println("var ol_fgcolor=\"#ffffc0\";");
			out.println("var ol_border=\"1\";");
			out.println("var ol_bgcolor=\"#FFFFC0\";");
			out.println("var ol_textcolor=\"#000000\";");
			out.println("var ol_capcolor=\"#aaaaaa\";");
			//var ol_css="forms-help";

			out.println("</script>");
			out.println("<style media=\"screen\">");
			out.println(".forms-help {");
			out.println("   border-style: dotted;");
			out.println("   border-width: 1px;");
			out.println("   padding: 5px;");
			out.println("   background-color:#FFFFC0; /* light yellow */");
			out.println("    width: 200px; /* otherwise IE does a weird layout */");
			out.println("   z-index:1000; /* must be higher than forms-tabContent */");
			out.println("}");

			out.println("</style>");
			out.println("<script type=\"text/javascript\" src=\""+ request.getContextPath()+"/jsp/plantillas/privileged/overlib.js\"><!-- overLIB (c) Erik Bosrup --></script>");
			out.println("<div id=\"overDiv\" style=\"position:absolute; visibility:hidden; z-index:1000;\"></div>");

			out.println("<!-- eof:block -->");
			out.println("<!-- + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + -->");

			out.newLine();
			out.println("<form action=\""+this.nombreAccionFolio+"\" method=\"post\" name=\""+this.nombreFormaFolio+"\" id=\""+this.nombreFormaFolio+"\">");
			out.newLine();
			out.println(" <input type=\"hidden\" name=\""+WebKeys.ACCION+"\" id=\""+WebKeys.ACCION+"\" value=\"\">");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+"POSSCROLL"+"\" id=\""+"POSSCROLL"+"\" value=\""+(request1.getParameter("POSSCROLL")!=null?request1.getParameter("POSSCROLL"):"")+"\">");
			out.newLine();
			out.println(" <input type=\"hidden\" name=\""+this.nombreNumAnotacionTemporal+"\" id=\""+this.nombreNumAnotacionTemporal+"\" value=\"\">");
			out.newLine();
			out.println("</form> ");
			out.newLine();
			out.println("<form action=\""+this.nombreAccionPaginador+"\" method=\"post\" type=\"submit\" name=\""+nombreFormaPaginador+"\" id=\""+nombreFormaPaginador+"\" >");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+WebKeys.ACCION+"\" id=\""+WebKeys.ACCION+"\" value=\"\">");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+"POSSCROLL"+"\" id=\""+"POSSCROLL"+"\" value=\""+(request1.getParameter("POSSCROLL")!=null?request1.getParameter("POSSCROLL"):"")+"\">");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+WebKeys.INICIO+"\" id=\""+WebKeys.INICIO+"\" value=\"\">");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+WebKeys.CANTIDAD+"\" id=\""+WebKeys.CANTIDAD+"\" value=\"\">");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+AWPaginadorAnotaciones.ANOTACIONES_DEFINITIVAS+"\" id=\""+AWPaginadorAnotaciones.ANOTACIONES_DEFINITIVAS+"\" value=\""+(this.consultarAnotacionesDefinitivas?"TRUE":"FALSE")+"\">");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+AWPaginadorAnotaciones.PAGINA_INICIAL+"\" id=\""+AWPaginadorAnotaciones.PAGINA_INICIAL+"\" value=\"\">");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+CFolio.ID_MATRICULA+"\" id=\""+CFolio.ID_MATRICULA+"\" value=\"\">");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+CFolio.ID_ZONA_REGISTRAL+"\" id=\""+CFolio.ID_ZONA_REGISTRAL+"\" value=\"\">");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+WebKeys.NOMBRE_PAGINADOR+"\" id=\""+WebKeys.NOMBRE_PAGINADOR+"\" value=\"\">");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+WebKeys.NOMBRE_RESULTADOS_PAGINADOR+"\" id=\""+WebKeys.NOMBRE_RESULTADOS_PAGINADOR+"\" value=\"\">");
			out.newLine();
			out.println("	<input type=\"hidden\" name=\""+AWPaginadorAnotaciones.ANOTACIONES_FOLIO_TEMPORAL+"\" id=\""+AWPaginadorAnotaciones.ANOTACIONES_FOLIO_TEMPORAL+"\" value=\"\">");
			out.newLine();
			out.println("</form> ");
			if(carga){
				out.newLine();
				out.println("	<script> cargarListas(); </script> ");
			}

			if(tipoMostrarFolio==CTipoMostrarFolioHelper.TIPO_NORMAL){
				out.newLine();
				out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
				out.newLine();
				out.println("<!-- fwtable fwsrc=\"SIR_central.png\" fwbase=\"tabla_central.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
				out.newLine();
				out.println("<tr>");
				out.newLine();
				out.println("<td><img src=\""+request1.getContextPath()+"/jsp/images/spacer.gif\" width=\"7\" height=\"10\"></td>");
				out.newLine();
				out.println("<td background=\""+request1.getContextPath()+"/jsp/images/tabla_central_bgn003.gif\"><img src=\""+request1.getContextPath()+"/jsp/images/spacer.gif\" width=\"10\" height=\"10\"></td>");
				out.newLine();
				out.println("<td><img src=\""+request1.getContextPath()+"/jsp/images/spacer.gif\" width=\"10\" height=\"10\"></td>");
				out.newLine();
				out.println("</tr>");
				out.newLine();
				out.println("<tr>");
				out.newLine();
				out.println("<td><img name=\"tabla_central_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/tabla_central_r1_c1.gif\" width=\"7\" height=\"29\" border=\"0\" alt=\"\"></td>");
				out.newLine();
				out.println("<td background=\""+request1.getContextPath()+"/jsp/images/tabla_central_bgn003.gif\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
				out.newLine();
				out.println("	<tr>");
				out.newLine();
				out.println("	<td background=\""+request1.getContextPath()+"/jsp/images/tabla_central_bgn001.gif\" class=\""+ this.eTituloFolio +"\">"+this.titulo+"</td>");
				out.newLine();
				out.println("	<td width=\"9\"><img name=\"tabla_central_r1_c3\" src=\""+request1.getContextPath()+"/jsp/images/tabla_central_r1_c3.gif\" width=\"9\" height=\"29\" border=\"0\" alt=\"\"></td>");
				out.newLine();
				out.println("	<td width=\"20\" align=\"center\" valign=\"top\" background=\""+request1.getContextPath()+"/jsp/images/tabla_central_bgn002.gif\">");
				out.newLine();
				out.println("	<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
				out.newLine();
				out.println("		<tr>");
				out.newLine();
				out.println("		<td><img src=\""+request1.getContextPath()+ this.imagenFolio +"\" width=\"16\" height=\"21\"></td>");
				out.newLine();
				out.println("		</tr>");
				out.newLine();
				out.println("	</table></td>");
				out.newLine();
				out.println("	<td width=\"12\"><img name=\"tabla_central_r1_c5\" src=\""+request1.getContextPath()+"/jsp/images/tabla_central_r1_c5.gif\" width=\"12\" height=\"29\" border=\"0\" alt=\"\"></td>");
				out.newLine();
				out.println("	</tr>");
				out.newLine();
				out.println("</table>");
				out.newLine();
				out.println("</td>");
				out.newLine();
				out.println("<td>");
				out.newLine();
				out.println("<img name=\"tabla_central_pint_r1_c7\" src=\""+request1.getContextPath()+"/jsp/images/tabla_central_pint_r1_c7.gif\" width=\"11\" height=\"29\" border=\"0\" alt=\"\"></td>");
				out.newLine();
				out.println("</tr>");
				out.newLine();
				out.println("<tr>");
				out.newLine();
				out.println("<td background=\""+request1.getContextPath()+"/jsp/images/tabla_central_bgn009.gif\">&nbsp;</td>");
				out.newLine();
				out.println("<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\">");
				out.newLine();
				out.println("<table border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"2\">");
				out.newLine();
				out.println("<tr>");
				if(ocultarFolio.equals("FALSE")){
					out.newLine();
					out.println("				<form action=\""+vistaActual+"\" method=\"post\" type=\"submit\">");
					out.newLine();
					out.println("			   <input type=\"hidden\" name=\""+this.nombreOcultarFolio+"\" value=\"TRUE\">");
					out.newLine();
					out.println("			   <td width=\"16\"><input name=\"MINIMIZAR\" type=\"image\" id=\"MINIMIZAR\" src=\""+ request1.getContextPath()+"/jsp/images/btn_minimizar.gif\" width=\"16\" height=\"16\" border=\"0\"></td>");
					out.newLine();
					out.println("		   </form>");

				}else{
					out.newLine();
					out.println("		   <form action=\""+vistaActual+"\" method=\"post\" type=\"submit\">");
					out.newLine();
					out.println("			   <input type=\"hidden\" name=\""+this.nombreOcultarFolio+"\" value=\"FALSE\">");
					out.newLine();
					out.println("			   <td width=\"170\" class=\"contenido\">Haga click para maximizar</td>");
					out.newLine();
					out.println("			   <td width=\"16\"><input name=\"MAXIMIZAR\" type=\"image\" id=\"MAXIMIZAR\" src=\""+ request1.getContextPath()+"/jsp/images/btn_maximizar.gif\" width=\"16\" height=\"16\" border=\"0\"></td>");
					out.newLine();
					out.println("		   </form>");
				}
				out.newLine();
				out.println("	   </tr>");
				out.newLine();
				out.println("   </table>");
				out.newLine();
				out.println("   </td>");
				out.newLine();
				out.println("<td background=\""+request1.getContextPath()+"/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
				out.newLine();
				out.println("</tr>");
				out.newLine();
				out.println("<tr>");
				out.newLine();
				out.println("<td width=\"7\" background=\""+request1.getContextPath()+"/jsp/images/tabla_central_bgn009.gif\">&nbsp;</td>");
				out.newLine();
				out.println("<td valign=\"top\" bgcolor=\"#79849B\" class=\"tdtablacentral\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
				out.newLine();
				out.println("<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
				out.newLine();
				out.println("</table>");
			}else if(tipoMostrarFolio==CTipoMostrarFolioHelper.TIPO_COMPRIMIDO){

			}else if( tipoMostrarFolio==CTipoMostrarFolioHelper.TIPO_SOLO_ANOTACIONES_NORMAL ) {
	                    //start-region
	                }


			if(ocultarFolio.equals("FALSE")){

				if(mostrarEncabezado){
					this.drawEncabezado(request1, out);
				}
				if(tipoMostrarFolio==CTipoMostrarFolioHelper.TIPO_NORMAL){
					this.drawCuerpoNormal(request1, out);
				}else if(tipoMostrarFolio==CTipoMostrarFolioHelper.TIPO_COMPRIMIDO){
					this.drawCuerpoComprimido(request1, out);
				}else if( tipoMostrarFolio==CTipoMostrarFolioHelper.TIPO_SOLO_ANOTACIONES_NORMAL ) {
	                            this.drawCuerpoSoloAnotacionesNormal(request1, out);
	                        }
			}
			if(tipoMostrarFolio==CTipoMostrarFolioHelper.TIPO_NORMAL){
				out.newLine();
				out.println("<td width=\"11\" background=\""+request1.getContextPath()+"/jsp/images/tabla_central_bgn008.gif\">&nbsp;</td>");
				out.newLine();
				out.println("	</tr>");

				out.newLine();
				out.println("	<tr>");
				out.newLine();
				out.println("	<td><img name=\"tabla_central_r3_c1\" src=\""+request1.getContextPath()+"/jsp/images/tabla_central_r3_c1.gif\" width=\"7\" height=\"6\" border=\"0\" alt=\"\"></td>");
				out.newLine();
				out.println("	<td background=\""+request1.getContextPath()+"/jsp/images/tabla_central_bgn006.gif\"><img src=\""+request1.getContextPath()+"/jsp/images/spacer.gif\" width=\"15\" height=\"6\"></td>");
				out.newLine();
				out.println("	<td><img name=\"tabla_central_pint_r3_c7\" src=\""+request1.getContextPath()+"/jsp/images/tabla_central_pint_r3_c7.gif\" width=\"11\" height=\"6\" border=\"0\" alt=\"\"></td>");
				out.newLine();
				out.println("	</tr>");
				out.newLine();
				out.println("	</table>");
				out.newLine();
				out.println("	</td>");
			} else if(tipoMostrarFolio==CTipoMostrarFolioHelper.TIPO_SOLO_ANOTACIONES_NORMAL){
	                    // end-region
	                }
    	}else{
    		out.newLine();
			out.println("<table class=\"camposform\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
			out.newLine();
			out.println("<!-- fwtable fwsrc=\"SIR_central.png\" fwbase=\"tabla_central.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
			out.newLine();
			out.println("<tr>");
			out.newLine();
			out.println("<td> No hay folio para mostrar.  </td>");
			out.newLine();
			out.println("<td>");
			out.newLine();
			out.println("</tr>");
			out.newLine();
			out.println("</table>");

    	}







    }

	/**
	 * Este método pinta los datos de encabezado al mostrase un folio, dependiendo de los flags
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 *
	 */
	public void drawEncabezado(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {
		String nMatricula="&nbsp";

		gov.sir.core.negocio.modelo.Fase fase = (gov.sir.core.negocio.modelo.Fase)request.getSession().getAttribute(WebKeys.FASE);

		if(folio.isDefinitivo()){
			if(folio.getIdMatricula()!=null){
				nMatricula=folio.getIdMatricula();
			}
		}else{

			//SI SE ESTA EN UNA FASE DE ANTIGUO SISTEMA NO SE MUESTRA EN NÚMERO DEL NUEVO FOLIO, DE LO CONTRARIO SI.
			if(fase==null){
				if(folio.getNombreLote()!=null && !folio.getNombreLote().trim().equals("")){
					nMatricula = folio.getNombreLote();
				}else{
					nMatricula = folio.getIdMatricula();
				}
			}else{
				if(fase.getID().startsWith("ANT_")){
					if(folio.getNombreLote()!=null && !folio.getNombreLote().trim().equals("")){
						nMatricula=folio.getNombreLote();
					}
				}else{
					if(folio.getNombreLote()!=null && !folio.getNombreLote().trim().equals("")){
						nMatricula=folio.getIdMatricula() + " - " +folio.getNombreLote();
					}else{
						nMatricula=folio.getIdMatricula();
					}
				}

			}
		}
		out.newLine();
		out.println("<table width=\"100%\" class=\""+this.eCampos+"\">");
		out.newLine();
		out.println("	<tr>");
		out.newLine();
		out.println("		<td width=\"20\"><img src=\""+request1.getContextPath()+"/jsp/images/ind_turno.gif\" width=\"20\" height=\"15\"></td>");
		out.newLine();
		out.println("		<td width=\"20\" class=\""+this.eCampoTexto+"\">N&ordm;</td>");
		out.newLine();
		out.println("		<td class=\""+this.eCampoTexto+"\">"+ nMatricula +"</td>");
		out.newLine();
		out.println("	</tr>");
		out.newLine();
		out.println("</table>");

		/*---- seccion edicion datos encabezado ------*/
		/**
		 * @author: Cesar Ramirez
		 * @change: 1245.HABILITAR.TIPO.PREDIO
		 * Se añade la opción para visualizar la función de javascript para la edición del tipo de predio.
		 **/
		if(this.edicionDatosCodCatastral || this.edicionDatosDireccion || this.edicionDatosLinderos || this.edicionTipoPredio){
			out.newLine();
		out.println("<script>");
		out.newLine();
		out.println("function editarCodigoCatastral(){");
		out.newLine();
		out.println("	   document."+this.nombreFormaEdicionCodCatastral+".ACCION.value = '"+this.nombreAcccionEdicionCodigoCatastral+"';");
		out.newLine();
		out.println("	   document."+this.nombreFormaEdicionCodCatastral+".submit();");
		out.newLine();
		out.println("}");
		out.newLine();
		out.println("function editarCabidaYLinderos(){");
		out.newLine();
		out.println("	   document."+this.nombreFormaEdicionLinderos+".ACCION.value = '"+this.nombreAccionEdicionCabidaYLinderos+"';");
		out.newLine();
		out.println("	   document."+this.nombreFormaEdicionLinderos+".submit();");
		out.newLine();
		out.println("}");
		out.newLine();
		out.println("function editarDireccion(accion){");
		out.newLine();
		out.println("	   document."+this.nombreFormaEdicionDireccion+".ACCION.value = '"+this.nombreAccionEdicionDireccion+"';");
		out.newLine();
		out.println("	   document."+this.nombreFormaEdicionDireccion+".submit();");
		out.println("	   document."+this.nombreFormaEdicionDireccion+"." + WebKeys.ACCION + ".value =accion;");
		out.newLine();
		out.println("}");
		out.newLine();
		/**
		 * @author: Cesar Ramirez
		 * @change: 1245.HABILITAR.TIPO.PREDIO
		 * Función en javascript que llama a la acción al editar el campo del Tipo de Predio.
		 **/
		out.println("function editarTipoPredio(){");
		out.println("	   document."+this.nombreFormaEdicionTipoPredio+".ACCION.value = '"+this.nombreAccionEdicionTipoPredio+"';");
		out.println("	   document."+this.nombreFormaEdicionTipoPredio+".submit();");
		out.println("}");
		out.newLine();
		                
		//pq
		out.println("function agregarDireccion(accion){");
		out.newLine();
		out.println("	   document."+this.nombreFormaEdicionDireccion+".ACCION.value = '"+this.nombreAccionAgregarDireccion+"';");
		out.newLine();
		out.println("	   document."+this.nombreFormaEdicionDireccion+".submit();");
		out.println("	   document."+this.nombreFormaEdicionDireccion+"." + WebKeys.ACCION + ".value =accion;");
		out.newLine();
		out.println("}");
		
		
		out.println("function quitar( pos,accion ){");
		out.println("	if(confirm(\"Esta seguro que desea eliminar este item ?\")){");
		out.println("	   document."+this.nombreFormaEdicionDireccion + "." + WebKeys.POSICION + ".value = pos;");
		out.println("	   document."+this.nombreFormaEdicionDireccion+".ACCION.value = '"+this.nombreAccionEliminarDireccion+"';");
		out.println("	   document."+this.nombreFormaEdicionDireccion+".submit();");
		out.println("	   document."+this.nombreFormaEdicionDireccion+"." + WebKeys.ACCION + ".value =accion;");
		out.println("	}");
		out.println("	}");
		out.newLine();
		
		
		out.newLine();
		out.println("function guardarComplementacion(){");
		out.newLine();
		if(this.nombreFormaEdicionComplementacion!=null && !this.nombreFormaEdicionComplementacion.equals("")){
			out.println("	   document."+this.nombreFormaEdicionComplementacion+".ACCION.value = '"+this.nombreAccionEdicionComplementacion+"';");
		}else{
			out.newLine();
		}
		out.newLine();
		if(this.nombreFormaEdicionComplementacion!=null && !this.nombreFormaEdicionComplementacion.equals("")){
			out.println("	   document."+this.nombreFormaEdicionComplementacion+".submit();");
		}else{
			out.newLine();
		}
		
		out.newLine();
		out.println("}");
		out.newLine();
		
		//fin pq
		out.newLine();

		out.println("function eliminarDireccionDef(accion){");
		out.newLine();
		out.println("	   document."+this.nombreFormaEdicionDireccion+".ACCION.value = '"+this.nombreAccionEliminarDireccionDefinitiva+"';");
		out.newLine();
		out.println("	   document."+this.nombreFormaEdicionDireccion+".submit();");
		out.println("	   document."+this.nombreFormaEdicionDireccion+"." + WebKeys.ACCION + ".value =accion;");
		out.newLine();
		out.println("}");
		out.newLine();
		out.println("</script>");

		out.newLine();
		out.println("<form action=\""+this.nombreDoEdicionDatosEncabezado+"\" method=\"post\" name=\""+this.nombreFormaEdicionDatosEncabezado+"\" id=\""+this.nombreFormaEdicionDatosEncabezado+"\">");
		out.newLine();
		out.println(" <input type=\"hidden\" name=\""+WebKeys.ACCION+"\" id=\""+WebKeys.ACCION+"\" value=\"\">");
		out.newLine();
		out.println("</form> ");

		}


		/*---- fin: seccion edicion datos encabezado ------*/

		if(this.mostrarDatosRelevantes){
			this.drawDatosRelevantes(request1, out);
		}
		if(this.mostrarGravamenes){
			this.drawGravamenesCautelares(request1, out)	;
		}
		if(this.mostrarAperturaFolio){
			this.drawAperturaFolio(request1, out);
		}
                /**
                * @author      :  Julio Alcazar
                * @change      :  nuevo metodo en ver detalles de folio
                * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                */           
                if(this.trasladoDestino != null){
			this.drawDatosRelevantesTraslado(request1, out);
		}
		if(this.mostrarDatosDocumento){
			this.drawDatosDocumentos(request1, out);
		}
		/**
		 * @author: Cesar Ramirez
		 * @change: 1245.HABILITAR.TIPO.PREDIO
		 * Método que dibuja el campo de tipo de Predio para su visualización o edición.
		 **/
		if (this.mostrarTipoPredio) {
			this.drawTipoPredio(request1, out);
		}
		if(this.mostrarDireccionInmueble){
			this.drawDireccionInmueble(request1, out);
		}
		if(this.mostrarCabidaLinderos){
			this.drawCabidaLinderos(request1, out);
		}
		if(this.mostrarComplementacion){
			this.drawComplementacion(request1, out);
		}
		if(this.mostrarMayorExtension){
			this.drawMayorExtension(request1, out);
		}
		if(this.mostrarRelaciones){
			this.drawRelaciones(request1, out);
		}
        if(this.mostrarTurnos) {
            this.drawTurnos(request1, out);
        }
	}

	/**
     * @param request1
     * @param out
     */
    private void drawTurnos(HttpServletRequest request1, JspWriter out)
        throws IOException {

        List listaTurnos = folio.getTurnosFolios();

        if(listaTurnos == null || listaTurnos.isEmpty())
            return;

        out.print("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        out.print("<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
        out.print("<tr>");
        out.print("<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.print("<td background=\""+request1.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\""+this.eTitulosSecciones+"\"> Turnos Asociados</td>");
        out.print("<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+ this.imagenSeccionEncabezado +"\" width=\"16\" height=\"21\"></td>");
        out.print("<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
        out.print("</tr>");
        out.print("</table>");
        out.print("<br>");
        out.print("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");

        int turnosPorFila = 5;
        int contadorTurnos = 0;

        for(Iterator iteradorTurnos = listaTurnos.iterator(); iteradorTurnos.hasNext(); contadorTurnos++) {
            TurnoFolio turnoFolio = (TurnoFolio)iteradorTurnos.next();
            Turno turnoAsociado = turnoFolio.getTurno();
            if(contadorTurnos == 0) {
                out.print("<tr>");
                out.print("<td>" + turnoAsociado.getIdWorkflow() + "</td>");
            } else if(contadorTurnos < turnosPorFila) {
                out.print("<td>" + turnoAsociado.getIdWorkflow() + "</td>");
            } else {
                out.print("<td>" + turnoAsociado.getIdWorkflow() + "</td>");
                out.print("</tr>");
                contadorTurnos = 0;
            }
        }

        out.print("</table>");
    }


    /**
	 * Este método pinta los datos del cuerpo del folio a partir de laa flags respectivos
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawCuerpoNormal(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {


		if(this.mostrarAnotaciones){
			this.drawAnotaciones(request1, out);
		}

	}

        /**
         * Este método pinta los datos del cuerpo del folio a partir de laa flags respectivos
         * ciertas partes de este se mostraran o no;
         * @param request Trae toda la informacion que ha sido guardada del usuario
         * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
         * @throws IOException
         * @throws HelperException
	 */
        public void drawCuerpoSoloAnotacionesNormal(HttpServletRequest request1, JspWriter out)
                throws IOException, HelperException {

            drawCuerpoNormal( request1, out );
                // if(this.mostrarAnotaciones){
                //        this.drawAnotaciones(request, out);
                //}

        }


	/**
	 * Este método pinta los datos del cuerpo del folio a partir de laa flags respectivos
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawCuerpoComprimido(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {


		if(this.mostrarAnotaciones){
			if(anotacionesDefinitivas == null){
				anotacionesDefinitivas = new LinkedList();
			}
			this.drawAnotacionesComprimidas(request1, out);
		}

	}

	/**
	 * Este método pinta los datos del segemento del encabezado de gravamenes y cautelares.
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawAnotaciones(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {

		/*Inicio segmento del cuerpo de anotaciones (definitivas, temporales y paginador)----------------*/
		out.newLine();
		out.println("<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"2\">");
				if(ocultarAnotaciones.equals("FALSE")){
					out.newLine();
					out.println("	<tr>");
					out.newLine();
					out.println("		<td><hr class=\"linehorizontal\"></td>");
					out.newLine();
					out.println("	</tr>");
					out.newLine();
					out.println("	<tr>");
					out.newLine();
					out.println("		<form action=\""+vistaActual+"\" method=\"post\" type=\"submit\">");
					out.newLine();
					out.println("			<td></td>");
					out.newLine();
					out.println("			<input type=\"hidden\" name=\""+this.nombreOcultarAnotaciones+"\" value=\"TRUE\">");
					out.newLine();
					out.println("			<td width=\"16\"><input name=\"MINIMIZAR\" type=\"image\" id=\"MINIMIZAR\" src=\""+ request1.getContextPath()+"/jsp/images/btn_minimizar.gif\" width=\"16\" height=\"16\" border=\"0\"></td>");
					out.newLine();
					out.println("		</form>");
					out.newLine();
					out.println("	</tr>");
				}else{
					out.newLine();
					out.println("	<tr>");
					out.newLine();
					out.println("<form action=\""+vistaActual+"\" method=\"post\" type=\"submit\">");
					out.newLine();
					out.println("		<input type=\"hidden\" name=\""+this.nombreOcultarAnotaciones+"\" value=\"FALSE\">");
					out.newLine();
					out.println("		<input type=\"hidden\" name=\""+"POSSCROLL"+"\" id=\""+"POSSCROLL"+"\" value=\"1000\">");
					out.newLine();
					out.println("		<td align=\"right\" class=\"contenido\">Haga click para maximizar las anotaciones</td>");
					out.newLine();
					out.println("		<td width=\"16\"><input name=\"MAXIMIZAR\" type=\"image\" id=\"MAXIMIZAR\" src=\""+ request1.getContextPath()+"/jsp/images/btn_maximizar.gif\" onclick=\"capturarScroll(this)\" width=\"16\" height=\"16\" border=\"0\"></td>");
					out.newLine();
					out.println("		</form>");
					out.newLine();
					out.println("	</tr>");
				}
		out.newLine();
		out.println("	</table>");
		out.newLine();
		out.println("	<br>");

		if(ocultarAnotaciones.equals("FALSE")){
			
			if(!(request1 != null && request1.getParameter("POSSCROLL") != null && !request1.getParameter("POSSCROLL").equals(""))){
				out.newLine();
				out.println("<script>centrar();</script>");
			}
			out.newLine();
			out.println("<a name=\"#"+this.nombreAncla+"\"></a>");
			    if(anotacionesDefinitivas==null){
			    	anotacionesDefinitivas=new LinkedList();
			    }
				Iterator itAnotacion = anotacionesDefinitivas.iterator(); //aqui se pide en vez folio.getAnotaciones la anotaciones definitivas                                
				try{
                                    while(itAnotacion.hasNext()){
                                            Anotacion anotacion = (Anotacion)itAnotacion.next();
                                            mAnot.setIdMatricula(folio.getIdMatricula());
                                            mAnot.setAnotacion(anotacion);
                                            mAnot.setConsulta(!editable);
                                            mAnot.setEditarAnotacionNoTemporal( editarAnotacionesNoTemporales );
                                            mAnot.setEditarAnotacionTemporal(editarAnotacionesTemporales);
                                            mAnot.render(request1,out);

                                    }
				}catch(Exception eAnotacion){
					eAnotacion.printStackTrace();
				}

			  	/*-- Aqui va el pedazo del paginador --*/


			  	try {if(!carga){
			  			pag.drawGUI(request1,out);
			  		}
				}
			 	 catch(HelperException re){
			 		Log.getInstance().error(MostrarFolioHelper.class,re);
				 	out.println("ERROR " + re.getMessage());
				}

 /*
			 	if(anotacionesTemporales==null){
			 		anotacionesTemporales=new LinkedList();
			    }
			  	Iterator itAnotacionT = anotacionesTemporales.iterator();
				while(itAnotacionT.hasNext()){
					Anotacion anotacion = (Anotacion)itAnotacionT.next();
					mAnot.setAnotacion(anotacion);
					mAnot.render(request,out);
				}
			  	      	  */
			  } else {


			out.newLine();
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.newLine();
			out.println("	<tr>");
			out.newLine();
			out.println("		<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("		<td background=\""+request1.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\""+this.eTitulosSecciones+"\">Anotaciones</td>");
			out.newLine();
			out.println("		<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+"/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
			out.newLine();
			out.println("		<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("	</tr>");
			out.newLine();
			out.println("</table>");


			}
		out.newLine();
		out.println("<hr class=\"linehorizontal\">");
		/*Fin segmento del cuerpo de anotaciones (definitivas, temporales y paginador)----------------*/

	}

	/**
	 * Este método pinta los datos del segemento del encabezado de gravamenes y cautelares.
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawAnotacionesComprimidas(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {

		/*Inicio segmento del cuerpo de anotaciones (definitivas, temporales y paginador)----------------*/

		out.newLine();
		out.println("<script>");
		out.newLine();
		out.println("function cambiarSeleccionCancelacion(text){");
		out.newLine();
		out.println("		document."+nombreFormaCancelacion+"."+this.nomOrdenCancelada+".value = text;");
		out.newLine();
                out.newLine();

                // ---------------------------------------------------------------------------------

                // llamar opcionalmente una funcion js ( v1 )
                // si esta declarada, para informar sobre el valor que
                // se selecciona en la forma.

                StringBuffer htmBuffer;
                htmBuffer = new StringBuffer();

                htmBuffer.append( "     if( ( undefined != window.notify )     " + "\n" );
                htmBuffer.append( "       ||( null !=  window.notify ) ) {     " + "\n" );
                htmBuffer.append( "       notify( text );                      " + "\n" );
                htmBuffer.append( "     }                                      " + "\n" );

                out.println( htmBuffer.toString() );

                // ---------------------------------------------------------------------------------

                out.newLine();
		out.println("}");
		out.println("function guardar(){");
		out.newLine();
		out.println("		document."+nombreFormaCancelacion+".submit();");
		out.newLine();
		out.println("}");
		out.newLine();
		out.println("</script>");
		out.newLine();
		out.println("<form action=\""+this.nombreAccionCancelacion+"\" method=\"post\" type=\"submit\" name=\""+nombreFormaCancelacion+"\" id=\""+nombreFormaCancelacion+"\" >");

                // Bug 05042
                if( isCheckboxController_MultiSelectEnabled() ) {

                       // remover este key en la accion web
                       final String CHECKBOXCONTROLLER_MULTISELECTENABLED = "CHECKBOXCONTROLLER_MULTISELECTENABLED";
                       final String CHECKBOXCONTROLLER_TARGETFORMFIELDID  = "CHECKBOXCONTROLLER_TARGETFORMFIELDID";

                       HttpSession session;
                       session = request.getSession();

                       session.setAttribute( "MFOLIO:" + CHECKBOXCONTROLLER_MULTISELECTENABLED, Boolean.TRUE );
                       session.setAttribute( "MFOLIO:" + CHECKBOXCONTROLLER_TARGETFORMFIELDID , getCheckboxController_TargetFormFieldId() );

                } // if

                if( isCheckboxController_MultiSelectEnabled() ) {

                   // remover este key en la accion web
                   final String PREFIX = "MFOLIO:";
                   final String CHECKBOXCONTROLLER_MULTISELECTENABLED = "CHECKBOXCONTROLLER_MULTISELECTENABLED";
                   final String CHECKBOXCONTROLLER_TARGETFORMFIELDID  = "CHECKBOXCONTROLLER_TARGETFORMFIELDID";

                   HttpSession session;
                   session = request.getSession();

                   session.setAttribute( PREFIX + CHECKBOXCONTROLLER_MULTISELECTENABLED, Boolean.TRUE );
                   session.setAttribute( PREFIX + CHECKBOXCONTROLLER_TARGETFORMFIELDID , getCheckboxController_TargetFormFieldId() );

                } // if










                // Bug 05042
                if( isCheckboxController_MultiSelectEnabled() ) {


                   htmBuffer = new StringBuffer();

                   htmBuffer.append( "\r\n" );
                   htmBuffer.append( "<input           " + "\r\n" );

                   if( isCheckboxController_MultiSelectDebugEnabled() ) {
                      htmBuffer.append( "  type='text'    " + "\r\n" );
                   }
                   else {
                      htmBuffer.append( "  type='hidden'    " + "\r\n" );
                   } // if

                   htmBuffer.append( "  id='"      + getCheckboxController_TargetFormFieldId() + "'    " + "\r\n" );
                   htmBuffer.append( "  name='"    + getCheckboxController_TargetFormFieldId() + "'    " + "\r\n" );
                   htmBuffer.append( "  value='"   + ""  + "'"  + "\r\n" );
                   htmBuffer.append( "/>        "  + ""  + "\r\n" );
                   htmBuffer.append( "\r\n" );

                   out.println( htmBuffer.toString() );

                } // if


		out.println("<input type=\"hidden\" name=\""+AWFolio.POSICION+"\" id=\""+AWFolio.POSICION+"\">");
		out.println("	<input type=\"hidden\" name=\""+"POSSCROLL"+"\" id=\""+"POSSCROLL"+"\" value=\""+(request1.getParameter("POSSCROLL")!=null?request1.getParameter("POSSCROLL"):"")+"\">");		
		out.println("<input type=\"hidden\" name=\""+this.nomOrdenCancelada+"\" value=\""+idAnotacionCancelada+"\" id=\""+this.nomOrdenCancelada+"\">");
		if(!anotacionesDefinitivas.isEmpty()){
			out.println("<tr>");
			out.println("<td>&nbsp;</td>");
			out.println("<td colspan=\"4\">Anotaci&oacute;n</td>");
			out.println("<td colspan=\"3\">Documento</td>");
			out.println("<td colspan=\"3\">Naturaleza jur&iacute;dica</td>");
			out.println("<td>&nbsp;</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>&nbsp;</td>");
			out.println("<td>Id</td>");
			out.println("<td># radicaci&oacute;n</td>");
			out.println("<td>&nbsp;</td>");
			out.println("<td>&nbsp;</td>");
			out.println("<td>Tipo</td>");
			out.println("<td>N&uacute;mero</td>");
			out.println("<td>Fecha</td>");
			out.println("<td>Id</td>");
			out.println("<td>Naturaleza</td>");
			out.println("<td>Valor</td>");
			out.println("<td>&nbsp;</td>");
			out.println("</tr>");
			Iterator itAnotaciones = anotacionesDefinitivas.iterator();
			while(itAnotaciones.hasNext()){
				Anotacion anotacion = (Anotacion) itAnotaciones.next();
				Documento documento = anotacion.getDocumento();
				TipoDocumento tipoDoc = null;
				String tipoDocumento = null;
				String numDocumento = null;
				String fechaDocumento = null;
				String idEstado="";

				if(documento!=null && documento.getFecha()!=null){
					fechaDocumento = FechaConFormato.formatear( documento.getFecha(), "dd/MM/yyyy");
				}

				if(documento!=null && documento.getNumero()!=null){
					numDocumento = documento.getNumero();
				}

				if(documento!=null&&documento.getTipoDocumento()!=null){
					 tipoDoc = documento.getTipoDocumento();

					 if(tipoDoc!=null && tipoDoc.getIdTipoDocumento()!=null){
					 	tipoDocumento = tipoDoc.getIdTipoDocumento();
					 }

					if(tipoDoc!=null && tipoDoc.getNombre()!=null){
					   tipoDocumento = tipoDoc.getNombre();
					}

				}




				out.newLine();
				out.println("<tr>");

				String tipoInput = "radio";
				if(mostrarAnotacionesCancelacioMultiple){

                                        tipoInput = "checkbox";

                                        // Bug 05042: solo esta colocando un id de anotacion cancelada
                                        // omitir este bloque si esta seleccionada la marca
                                        // se hace por javascript al seleccionar los valores
                                        if( isCheckboxController_MultiSelectEnabled() ) {
                                           idAnotacionCancelada = "";
                                        }
                                        else {


                                            //idAnotacionCancelada = "";
                                            String[] ids = (String[]) request.getSession().getAttribute(LISTA_ANOTACIONES_MULT_CANCELACION);
                                            if(ids != null){
                                                    for(int i = 0; i<ids.length; i++){
                                                            if(anotacion.getIdAnotacion().equals(ids[i])){
                                                                    idAnotacionCancelada = ids[i];
                                                            }
                                                    }
                                            }

                                        } // if
				}

                                StringBuffer local_CheckBoxMultiSelect_JsCall;
                                local_CheckBoxMultiSelect_JsCall = new StringBuffer( 2048 );

                                if( isCheckboxController_MultiSelectEnabled() ) {

                                  if( null != getCheckboxController_JsControllerName() ) {
                                     local_CheckBoxMultiSelect_JsCall.append( getCheckboxController_JsControllerName() );
                                     local_CheckBoxMultiSelect_JsCall.append( "." );
                                     local_CheckBoxMultiSelect_JsCall.append( "onChange( this )" );
                                     local_CheckBoxMultiSelect_JsCall.append( ";" );
                                  } // if

                                } // if

                                String local_CheckBoxMultiSelect_FormFieldId = getCheckboxController_SourceFormFieldId();

				if(mostrarMedidasYGravamenes){
					if(anotacion.getNaturalezaJuridica().getGrupoNaturalezaJuridica().getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.GRAVAMEN)
							||anotacion.getNaturalezaJuridica().getGrupoNaturalezaJuridica().getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR)){
						if(anio == null){
							out.newLine();
							out.println("<td><input type=\"" + tipoInput + "\" " + ( " name=\"" + local_CheckBoxMultiSelect_FormFieldId +"\" " + " id=\"" + local_CheckBoxMultiSelect_FormFieldId + "\" " ) + " onClick=\"javascript:cambiarSeleccionCancelacion('"+anotacion.getIdAnotacion()+"');" + local_CheckBoxMultiSelect_JsCall.toString() + "\" value=\""+anotacion.getIdAnotacion()+"\"></td>");
						} else {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(anotacion.getFecha());
							if(calendar.before(anio)){
								out.newLine();
								out.println("<td><input type=\"" + tipoInput + "\" " + ( " name=\"" + local_CheckBoxMultiSelect_FormFieldId +"\" " + " id=\"" + local_CheckBoxMultiSelect_FormFieldId + "\" " ) + " onClick=\"javascript:cambiarSeleccionCancelacion('"+anotacion.getIdAnotacion()+"');" + local_CheckBoxMultiSelect_JsCall.toString() + "\" value=\""+anotacion.getIdAnotacion()+"\"></td>");
							}
							out.newLine();
							out.println("<td><input type=\"" + tipoInput + "\" "+ ( " name=\"" + local_CheckBoxMultiSelect_FormFieldId +"\" " + " id=\"" + local_CheckBoxMultiSelect_FormFieldId + "\" " ) + " disabled=\"true\" value=\""+anotacion.getIdAnotacion()+"\"></td>");
						}
					} else {
						out.newLine();
						out.println("<td><input type=\"" + tipoInput + "\" " + ( " name=\"" + local_CheckBoxMultiSelect_FormFieldId +"\" " + " id=\"" + local_CheckBoxMultiSelect_FormFieldId + "\" " )+" disabled=\"true\" value=\""+anotacion.getIdAnotacion()+"\"></td>");
					}
				} else {
					if(anotacion.isTemporal()){
						if (enableCheckAnotacionesTemporales) {
							out.newLine();
							out.println("<td><input type=\"" + tipoInput + "\" " + ( " name=\"" + local_CheckBoxMultiSelect_FormFieldId +"\" " + " id=\"" + local_CheckBoxMultiSelect_FormFieldId + "\" " ) + " onClick=\"" + local_CheckBoxMultiSelect_JsCall.toString() + "\"" + " value=\""+anotacion.getIdAnotacion()+"\"></td>");
						}
						else {
							out.newLine();
							out.println("<td><input type=\"" + tipoInput + "\" " + ( " name=\"" + local_CheckBoxMultiSelect_FormFieldId +"\" " + " id=\"" + local_CheckBoxMultiSelect_FormFieldId + "\" " ) + " disabled=\"true\" value=\""+anotacion.getIdAnotacion()+"\"></td>");
						}

					}else{
						if(anotacion.getIdAnotacion().equals(idAnotacionCancelada)){
							out.newLine();
							out.println("<td><input type=\"" + tipoInput + "\" " + ( " name=\"" + local_CheckBoxMultiSelect_FormFieldId +"\" " + " id=\"" + local_CheckBoxMultiSelect_FormFieldId + "\" " ) +" onClick=\"javascript:cambiarSeleccionCancelacion('"+anotacion.getIdAnotacion()+"');" + local_CheckBoxMultiSelect_JsCall.toString() + "\" checked=\"true\" value=\""+anotacion.getIdAnotacion()+"\"></td>");
						}else{
							out.newLine();
							out.println("<td><input type=\"" + tipoInput + "\" " + ( " name=\"" + local_CheckBoxMultiSelect_FormFieldId +"\" " + " id=\"" + local_CheckBoxMultiSelect_FormFieldId + "\" " ) +" onClick=\"javascript:cambiarSeleccionCancelacion('"+anotacion.getIdAnotacion()+"');" + local_CheckBoxMultiSelect_JsCall.toString() +"\" value=\""+anotacion.getIdAnotacion()+"\"></td>");
						}
					}
				}
				out.newLine();
				out.println("<td class=\"campositem\">" + ((anotacion.getOrden() != null) ? anotacion.getOrden() : "&nbsp;") + " </td>");
				out.newLine();
				out.println("<td class=\"campositem\">" + ((anotacion.getNumRadicacion() != null) ? anotacion.getNumRadicacion() : "&nbsp;") + " &nbsp;</td>");
				out.newLine();
				out.println("<td class=\"campositem\">" + ((anotacion.getEstado() != null) ? anotacion.getEstado().getIdEstadoAn() : "&nbsp;") + " </td>");
				out.newLine();
				out.println("<td class=\"campositem\">" + ((anotacion.isTemporal())?"T":"D")  + " </td>");
				out.newLine();
				out.println("<td class=\"campositem\">" + ((tipoDocumento!= null) ? tipoDocumento : "&nbsp;") + " </td>");
				out.newLine();
				out.println("<td class=\"campositem\">" + ((numDocumento != null) ? numDocumento : "&nbsp;") + " </td>");
				out.newLine();
				out.println("<td class=\"campositem\">" + ((fechaDocumento != null) ? fechaDocumento : "&nbsp;") + " </td>");


				out.newLine();
				out.println("<td class=\"campositem\">" + ((anotacion.getNaturalezaJuridica() != null) ? anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() : "&nbsp;") + " </td>");

				out.newLine();
				String naturaleza = null;

				if(anotacion.getEspecificacion()!=null){
					naturaleza = anotacion.getEspecificacion();
					if(naturaleza!=null && naturaleza.length()>33){
						naturaleza = naturaleza.substring(0,33);
					}
					out.println("<td class=\"campositem\">" + (naturaleza != null ? naturaleza : "&nbsp;") + "&nbsp;</td>");
				}else{
					if(anotacion.getNaturalezaJuridica() != null){
						naturaleza = anotacion.getNaturalezaJuridica().getNombre();
						if(naturaleza!=null && naturaleza.length()>33){
							naturaleza = naturaleza.substring(0,33);
						}
						out.println("<td class=\"campositem\">" + (naturaleza != null ? naturaleza : "&nbsp;") + "&nbsp;</td>");
					}
				}

				out.newLine();
				out.println("<td class=\"campositem\" align=\"right\">" + (anotacion.getValor()!=0?(NumberFormat.getInstance().format(anotacion.getValor())):"&nbsp;")  + " </td>");
				out.newLine();
				if (!isCorreciones) {
					out.println("<td width=\"40\"><img onClick=\"verAnotacion('ver.anotacion.view?" + AWFolio.POSICION + "="+anotacion.getOrden()+"','Anotacion','width=900,height=450,scrollbars=yes','"+anotacion.getOrden()+"')\" src=\""+ request1.getContextPath()+ "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" alt=\"Ver Anotación\" style=\"cursor:hand\" height=\"13\"></td>");
				} else {
					int iOrden = Integer.parseInt(anotacion.getOrden()) - 1 ;
					String orden = Integer.toString(iOrden);
					out.println("<td width=\"40\"><img onClick=\"verAnotacion('ver.anotacion.view?" + AWFolio.POSICION + "="+orden+"','Anotacion','width=900,height=450,scrollbars=yes','"+orden+"')\" src=\""+ request1.getContextPath()+ "/jsp/images/btn_mini_verdetalles.gif\" width=\"35\" alt=\"Ver Anotación\" style=\"cursor:hand\" height=\"13\"></td>");
				}
				out.newLine();
				out.println("</tr>");

			}
		}else{
			out.newLine();
			out.println("<tr>");
			out.newLine();
			out.println("<td class=\"campositem\">No hay anotaciones</td>");
			out.newLine();
			out.println("</tr>");
		}



		if(mostrarAnotacionesCancelacioMultiple){
			/*request.getSession().removeAttribute(LISTA_ANOTACIONES_MULT_CANCELACION);
			out.newLine();
			out.println("<tr>");*/
			out.println("<input type=\"hidden\" name=\"" +WebKeys.ACCION + "\" id=\"" +WebKeys.ACCION + "\" value=\"" + accionGuardarAnotMultCancelacion + "\">");
			/*out.newLine();
			out.println("<td>&nbsp;</td>");
			out.println("<td width=\"150\"><a href=\"javascript:guardar()\"><img src=\"" + request.getContextPath() + "/jsp/images/btn_guardar.gif\" name=\"Folio\" width=\"139\" height=\"21\" border=\"0\" id=\"Folio\" alt='Guarda las anotaciones seleccionadas'></td>");
			out.newLine();
			out.println("</tr>");*/
		}

		out.newLine();
		out.println("	</form>");
	  	/*-- Aqui va el pedazo del paginador --*/

	 	out.newLine();
		out.println("<tr>");
		out.newLine();
		out.println("<td colspan=\"12\">");

			  	try {if(!carga){
			  			pag.drawGUI(request1,out);
			  		}
				}
			 	 catch(HelperException re){
				 	out.println("ERROR " + re.getMessage());
			 	 }
			 	out.newLine();
				out.println("</td>");
				out.newLine();
				out.println("</tr>");

		/*Fin segmento del cuerpo de anotaciones (definitivas, temporales y paginador)----------------*/

	}

	/**
	 * Este método pinta los datos del segemento del encabezado de gravamenes y cautelares.
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawGravamenesCautelares(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {



		/*Incio segmento matricula gravamenes y medidas cuatelares----------------*/
		out.newLine();
		out.println(" <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
		out.newLine();
		out.println("	  <tr>");
		out.newLine();
		out.println("		<td width=\"7%\">&nbsp;</td>");

				boolean gravamen = false;
				boolean medidas = false;
				boolean patrimonioFamiliar = false;
				boolean afectacionVivienda = false;
				boolean conflictoComplementaciones = false;
				if(gravamenes!=null && gravamenes.size()>0){
					gravamen = true;
				}

				if(medCautelares!=null && medCautelares.size()>0){
					medidas = true;
				}
				
				if(anotacionesPatrimonioFamiliar!=null && anotacionesPatrimonioFamiliar.size()>0){
					patrimonioFamiliar = true;
				}
				
				if(anotacionesAfectacionVivienda!=null && anotacionesAfectacionVivienda.size()>0){
					afectacionVivienda = true;
				}
				
				if(folio!=null && folio.getComplementacion()!=null && folio.getComplementacion().getComplementacionConflictiva()!=null){
					conflictoComplementaciones = true;
				}
				
				
		String img="";
		String folioTiene="";

		if(gravamen || medidas || patrimonioFamiliar || afectacionVivienda || conflictoComplementaciones) {
			folioTiene= "El folio tiene : ";
		}

		if(gravamen || medidas || patrimonioFamiliar || afectacionVivienda || conflictoComplementaciones){
			img="<img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+ this.imagenNAnotaciones +"\" width=\"24\" height=\"16\" border=\"0\" alt=\"\">";
			if(gravamen){
				folioTiene+=" -Grav&aacute;menes";
			}
			if(medidas){
				folioTiene+=" -Medidas Cautelares";
			}
			if(patrimonioFamiliar){
				folioTiene+=" -Patrimonio Familiar";
			}
			if(afectacionVivienda){
				folioTiene+=" -Afectación de Vivienda Familiar";
			}
			if(conflictoComplementaciones){
				folioTiene+=" -Conflicto de complementaciones FOLIO-SIR";
			}			
		}
		String textoTotalAnotaciones="";
		if(totalAnotaciones!=null){
			textoTotalAnotaciones=Long.toString(numeroAnotaciones);
		}
		
		if(this.edicionDigitadorMasivo && numeroAnotaciones<=0 && totalAnotaciones!=null){
			textoTotalAnotaciones=Long.toString(totalAnotaciones.longValue());
			numeroAnotaciones=totalAnotaciones.longValue();
		}
		
		String textoPostTotalAnotaciones="Anotaciones";
        //TODO
        // Se cambió el código para verificar si el usuario digitó el numero de anotación desde el cual desea visualizar.
        // Si el usuario digitó, se muestra el mensaje "visualizando i de n anotaciones" (donde i es el núumero desde el que se visualiza y n el numero total)
        // Si no se digitó nada se muestra en la página de la forma que se hacia antes.
		//if(numeroAnotaciones==1){
			//textoPostTotalAnotaciones="Anotaci&oacute;n";
		//}
		out.newLine();
        // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
        // Cambio para asolucionar requerimiento 117 - Incidencia Mantis 02399.
        // Se cambió la variable estática por atributos en sesion y en el evento EvnPaginadorAnotaciones.
        
        //if(AnotacionesFolio.getNumAnotacionesTotalV()>0){
        Integer numAnotacionesTotalV = (Integer)request1.getSession().getAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL);
        Integer numAnotacionesDesdeV = (Integer)request1.getSession().getAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_DESDE);
        if(numAnotacionesTotalV != null && numAnotacionesTotalV>0){
            //if(AnotacionesFolio.getNumAnotacionesTotalV()==1){
            if(numAnotacionesTotalV==1){
                textoPostTotalAnotaciones="Anotaci&oacute;n";
            }
            //out.println("		 <td class=\""+this.eTitulos+"\" width=\"43%\">&nbsp;Visualizando&nbsp;"+ (AnotacionesFolio.getNumAnotacionesTotalV()-AnotacionesFolio.getNumAnotacionesFolio()) +"&nbsp;de&nbsp;"+ AnotacionesFolio.getNumAnotacionesTotalV() + "&nbsp;" + textoPostTotalAnotaciones + "</td>            ");
            out.println("		 <td class=\""+this.eTitulos+"\" width=\"43%\">&nbsp;Visualizando&nbsp;"+ (numAnotacionesTotalV - numAnotacionesDesdeV) +"&nbsp;de&nbsp;"+ numAnotacionesTotalV + "&nbsp;" + textoPostTotalAnotaciones + "</td>            ");
        }else{
            if(numeroAnotaciones==1){
                textoPostTotalAnotaciones="Anotaci&oacute;n";
            }
            out.println("		 <td class=\""+this.eTitulos+"\" width=\"43%\">&nbsp;El folio tiene&nbsp;"+textoTotalAnotaciones+"&nbsp;"+textoPostTotalAnotaciones+"</td>            ");
        }
		out.newLine();
		out.println("	  <td class=\""+this.eTitulos+"\" width=\"43%\">"+img+"&nbsp;&nbsp;&nbsp;&nbsp;"+folioTiene+"</td>     ");
		out.newLine();
		out.println("			<td width=\"7%\">&nbsp;</td>  ");
		out.newLine();
		out.println("		</tr>");
		out.newLine();


		String textoFalsaTradicion = "  ";
		if(falsaTradicion!=null){
			Iterator it = falsaTradicion.iterator();
                        Object falsaTradicionElement;
                        String ordenAnotacion = "";
			while(it.hasNext()){
                            falsaTradicionElement = it.next();

                            if( null == falsaTradicionElement  ) {
                               continue;
                            }
                            else if( falsaTradicionElement instanceof gov.sir.core.negocio.modelo.Anotacion ) {
                               ordenAnotacion = ((Anotacion)falsaTradicionElement).getOrden();
                            }
                            else if( falsaTradicionElement instanceof java.lang.String ) {
                               ordenAnotacion = (String)falsaTradicionElement;
                            }
                            textoFalsaTradicion = textoFalsaTradicion + ordenAnotacion + ", ";
			}
		}
		textoFalsaTradicion = textoFalsaTradicion.substring(0,(textoFalsaTradicion.length()-2));

		String textoInvalidas = "  ";
		if(anotacionesInvalidas!=null){
			Iterator it = anotacionesInvalidas.iterator();
			while(it.hasNext()){
				String ordenAnotacion = (String)it.next();
				textoInvalidas = textoInvalidas + ordenAnotacion + ", ";
			}
		}
		textoInvalidas = textoInvalidas.substring(0,(textoInvalidas.length()-2));


		if(textoFalsaTradicion.length()>0 || textoInvalidas.length()>0){
			out.println("	  <tr>");
			out.newLine();
			out.println("		<td width=\"7%\">&nbsp;</td>");
			out.newLine();

			if(textoFalsaTradicion.length()>0 && textoInvalidas.length()>0){
				out.println("		 <td class=\""+this.eTitulos+"\" width=\"43%\">&nbsp;Las siguientes anotaciones son de falsa tradición:&nbsp;"+textoFalsaTradicion+".</td>            ");
				out.newLine();
				out.println("		 <td class=\""+this.eTitulos+"\" width=\"43%\">&nbsp;Las siguientes anotaciones son inválidas:&nbsp;"+textoInvalidas+".</td>            ");
			}else{
				if(textoFalsaTradicion.length()>0){
					out.println("		 <td class=\""+this.eTitulos+"\" width=\"43%\">&nbsp;Las siguientes anotaciones son de falsa tradición:&nbsp;"+textoFalsaTradicion+".</td>            ");
				}
				if(textoInvalidas.length()>0){
					out.println("		 <td class=\""+this.eTitulos+"\" width=\"43%\">&nbsp;Las siguientes anotaciones son inválidas:&nbsp;"+textoInvalidas+".</td>            ");
				}
			}

			out.newLine();
			out.println("			<td width=\"7%\">&nbsp;</td>  ");
			out.newLine();
			out.println("		</tr>");
			out.newLine();

		}


		out.println("	</table>");
		out.newLine();
		out.println("	<br>   ");
		/*Fin segmento matricula gravamenes y medidas cuatelares ----------------*/

	}

	/**
	 * Este método pinta los datos del segemento del encabezado de informacion de la apertura del folio
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawAperturaFolio(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {
			/*Incio segmento informacion de aperturas del folio----------------*/
			out.newLine();
			out.println("	<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.newLine();
			out.println("	<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
			out.newLine();
			out.println("	<tr>");
			out.newLine();
			out.println("	<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("	<td class=\""+this.eTitulosSecciones+"\"><p>Informacion Apertura de Folio</p></td>");
			out.newLine();
			if(this.edicionDatosCodCatastral){
				out.println("	<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><a href=\"javascript:editarCodigoCatastral()\"> <img src=\""+request.getContextPath()+"/jsp/images/btn_mini_guardar.gif\" border=\"0\" alt=\"Guardar Edición Codigo Catastral\"></td>");
				out.newLine();
			}
			out.println("	<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+ this.imagenSeccionEncabezado +"\" width=\"16\" height=\"21\"></td>");
			out.newLine();
			out.println("	<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("	</tr>");
			out.newLine();
			out.println("	</table>");
			out.newLine();
			out.println("	<br>");

			out.newLine();
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
			out.newLine();
			out.println("	<tr>");
			out.newLine();
			out.println("	<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
			out.newLine();
			out.println("	<td>C&iacute;rculo Registral:</td>");
			out.newLine();
			out.println("	<td class=\""+this.eCampoTexto+"\">"+ (folio.getZonaRegistral().getCirculo().getNombre() != null ? folio.getZonaRegistral().getCirculo().getNombre() : "")+"</td>");
			out.newLine();
			out.println("	<td>Depto:</td>");
			out.newLine();
			out.println("	<td class=\""+this.eCampoTexto+"\">"
				+ (folio.getZonaRegistral().getVereda().getMunicipio().getDepartamento().getNombre() != null
					? folio.getZonaRegistral().getVereda().getMunicipio().getDepartamento().getNombre() : "")+"</td>");
			out.newLine();
			out.println("	<td>Municipio:</td>");
			out.newLine();
			out.println("	<td class=\""+this.eCampoTexto+"\">"
				+ (folio.getZonaRegistral().getVereda().getMunicipio().getNombre() != null
					? folio.getZonaRegistral().getVereda().getMunicipio().getNombre() : "") +"</td>");
			out.newLine();
			out.println("	<td>Vereda:</td>");
			out.newLine();
			out.println("	<td class=\""+this.eCampoTexto+"\">"
				+ (folio.getZonaRegistral().getVereda().getNombre() != null
					? folio.getZonaRegistral().getVereda().getNombre() : "") +"</td>");
			out.newLine();
			out.println("	</tr>");
			out.newLine();
			out.println("	<tr>");
			out.newLine();
			out.println("	<td><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
			out.newLine();
			out.println("	<td>Fecha Apertura: </td>");
			out.newLine();
			out.println("	<td class=\""+this.eCampoTexto+"\">");
				try {
									if (folio.getFechaApertura()!=null){
										fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
										fechaHelper.setDate(folio.getFechaApertura());
										fechaHelper.render(request1,out);
									}
									else{
										out.println("");
									}

								}
									catch(HelperException re){
									out.println("ERROR " + re.getMessage());
								}
			out.println("	</td>");
			out.newLine();
			out.println("	<td>Radicaci&oacute;n:</td>");
			out.newLine();
			out.println("	<td class=\""+this.eCampoTexto+"\">");

				if (folio.getRadicacion()!=null){
					out.println(folio.getRadicacion());
				}else{
					out.println("");

				}


			out.println("	&nbsp;</td>");
			out.newLine();
			out.println("	<td>Con:</td>");
				if(folio.getDocumento() == null){
					out.newLine();
					out.println("		<td class=\""+this.eCampoTexto+"\" colspan=\"3\">No hay documento asociado</td>");
				} else {
					out.newLine();
					out.println("		<td class=\""+this.eCampoTexto+"\" colspan=\"3\">"+ (folio.getDocumento().getTipoDocumento().getNombre() != null ? folio.getDocumento().getTipoDocumento().getNombre() : "&nbsp;") +"</td> ");
				}
			out.newLine();
			out.println("	</tr>");
			out.newLine();

			//OPCIONES DEL CÓDIGO CATASTRAL
			out.newLine();
			out.println("	<tr>");
			out.newLine();
			out.println("	<td><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
			out.newLine();

			out.newLine();
			out.println("		<td>C&oacute;d. Catastral : </td>");
			out.newLine();

			if(this.edicionDatosCodCatastral){
				out.println("<td nowrap>");
				out.println("<form action=\""+this.nombreDoEdicionDatosEncabezado+"\" method=\"post\" name=\""+this.nombreFormaEdicionCodCatastral+"\" id=\""+this.nombreFormaEdicionCodCatastral+"\">");
				out.newLine();
				out.println(" <input type=\"hidden\" name=\""+WebKeys.ACCION+"\" id=\""+WebKeys.ACCION+"\" value=\"\">");
				out.newLine();
				session.setAttribute(CFolio.FOLIO_COD_CATASTRAL, codCatastral);


				TextHelper textHelper = new TextHelper();
				textHelper.setNombre(CFolio.FOLIO_COD_CATASTRAL);
				textHelper.setId(CFolio.FOLIO_COD_CATASTRAL);

				if(this.edicionDatosCodCatastral){
                                        /**
                                         * @author: David Panesso
                                         * @change: Caso 904.ACTUALIZACION.FICHAS.CATASTRALES, se agrega class CSS para visualizar toda la información del campo. El estilo queda en style.css
                                         */
					textHelper.setCssClase("camposformtext codCatastral");
				} else {
					textHelper.setCssClase(this.eCampoTexto);
				}
				textHelper.setReadonly(!this.edicionDatosCodCatastral);
				//textHelper.setSize("5");
				textHelper.render(request,out);

				out.newLine();
				out.println("</form> ");
				out.println("</td>");
			}

			else{ out.println("	<td  class=\""+this.eCampoTexto+"\">"+ codCatastral+"</td>"); }

			session.setAttribute(CFolio.FOLIO_COD_CATASTRAL_ANT, codCatastralAnt);
			out.println("	<td>C&oacute;d. Catastral Ant: </td>");
			out.println("	<td class=\""+this.eCampoTexto+"\">"+ codCatastralAnt+"</td>");
			if(folio.getDocumento() != null) {
				out.println("	<td>No. Documento: </td>");
                                //&nbsp;
				out.println("   <td class=\"" + this.eCampoTexto + "\">" + (folio.getDocumento().getNumero() != null ? folio.getDocumento().getNumero() : "&nbsp;") + "</td>");
				out.println("	<td>Fecha Documento: </td>");
				out.println("   <td class=\"" + this.eCampoTexto + "\">");
				fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
				fechaHelper.setDate(folio.getDocumento().getFecha());
				fechaHelper.render(request1,out);
				out.println("</td>");
			} else {
				out.println("   <td colspan=\"4\">&nbsp;</td>");
			}
                        TextHelper textHelper = new TextHelper();
			out.println("	</tr>");
                        //NUPRE
                        out.println("<tr>");
                        out.println("<td><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
                        out.println("<td>NUPRE: </td>");
                        out.println("<td class=\""+this.eCampoTexto+"\">"+(folio.getNupre()!= null ? folio.getNupre(): "&nbsp;")+"</td>");
                        out.println("</tr>");
                        //Avaluo
                        out.println("<tr>");
                        out.println("<td><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
                        out.println("<td>Aval&uacute;o: </td>");
                        out.println("<td class=\""+this.eCampoTexto+"\">"+(folio.getAvaluo()!= 0.0 ? folio.getAvaluo():"&nbsp;")+"</td>");
                        //Fecha de Avaluo
                        out.println("<td>Fecha de Aval&uacute;o: </td>");
                        out.println("<td class=\""+this.eCampoTexto+"\">");
                        fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
                        fechaHelper.setDate(folio.getFechaAvaluo());
			fechaHelper.render(request1,out);
                        out.println("</td>");
                        out.println("</tr>");
			out.println("</table>");


			//out.println("</table>");
			out.newLine();
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
			out.newLine();
			out.println("	<tr>");
			out.newLine();
			out.println("	<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
			out.newLine();
			out.println("	<td width=\"25%\">ESTADO DEL FOLIO </td>");
			out.newLine();
			/**
                        * @author      :  Julio Alcazar
                        * @change      :  Cambios en el estado ver detalles de folio
                        * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                        */
                        out.println("	<td width=\"75%\" class=\""+this.eCampoTexto+"\"><strong>"+ (folio.getEstado().getNombre() != null ? (trasladoOrigen != null ?folio.getEstado().getNombre()+" "+circuloDestino.getNombre()+ "-" +circuloDestino.getIdCirculo(): folio.getEstado().getNombre()) : "&nbsp;") +"</strong></td>");
			out.newLine();
			out.println("	</tr>");
			/* COMENTARIO DEL ESTADO DE FOLIO */

			//SI SE QUIERE MOSTRAR EL HISTARIAL DEL ESTADO PERO NO HAY HISTARIA SOLAMENTE SE MUESTRA EL ESTADO ACTUAL.
			if(this.mostrarHistorialEstados && (folio.getHistorialEstados() == null || folio.getHistorialEstados().isEmpty())){
				this.mostrarHistorialEstados = false;
			}

			//SI SÓLO SE QUIERE SABER EL ÚLTIMO ESTADO Y SU COMENTARIO
			if(!this.mostrarHistorialEstados){

				if(ocultarComentarioEstado.equals("FALSE")){
						out.newLine();
						out.newLine();
						out.println("	<tr>");
						out.newLine();
						out.println("		<form action=\""+vistaActual+"\" method=\"post\" type=\"submit\">");
						out.newLine();
						out.println("			<td colspan=\"3\"></td>");
						out.newLine();
						out.println("			<input type=\"hidden\" name=\""+this.nombreOcultarComentarioEstado+"\" value=\"TRUE\">");
						out.newLine();
						out.println("			<td width=\"16\" ><input name=\"MINIMIZAR\" type=\"image\" id=\"MINIMIZAR\" src=\""+ request1.getContextPath()+"/jsp/images/btn_minimizar.gif\" width=\"16\" height=\"16\" border=\"0\"></td>");
						out.newLine();
						out.println("		</form>");
						out.newLine();
						out.println("	</tr>");
					}else{
						out.newLine();
						out.println("	<tr>");
						out.newLine();
						out.println("<form action=\""+vistaActual+"\" method=\"post\" type=\"submit\">");
						out.newLine();
						out.println("		<input type=\"hidden\" name=\""+this.nombreOcultarComentarioEstado+"\" value=\"FALSE\">");
						out.newLine();
						out.println("		<td colspan=\"2\">&nbsp;</td>");
						out.newLine();
						out.println("		<td align=\"right\" class=\"contenido\">Haga click para maximizar el comentario del estado</td>");
						out.newLine();
						out.println("		<td width=\"16\"><input name=\"MAXIMIZAR\" type=\"image\" id=\"MAXIMIZAR\" src=\""+ request1.getContextPath()+"/jsp/images/btn_maximizar.gif\" width=\"16\" height=\"16\" border=\"0\"></td>");
						out.newLine();
						out.println("		</form>");
						out.newLine();
						out.println("	</tr>");
					}
				out.newLine();
				out.println("	   </tr>");
				if(ocultarComentarioEstado.equals("FALSE")){
					out.println("	<tr>");
					out.newLine();
					out.println("	<td width=\"20\">&nbsp;</td>");
					out.newLine();
					out.println("	<td>Comentario del estado:</td>");
					out.newLine();
					out.println("	<td>");
					out.newLine();
					request.getSession().setAttribute("COMENTARIO_ESTADO_FOLIO_MOSTRAR_FOLIO", (folio.getEstado().getComentario() != null ? folio.getEstado().getComentario() : ""));
					TextAreaHelper TAhelper = new TextAreaHelper();
					try{
						TAhelper.setCols("85");
						TAhelper.setReadOnly(true);
						TAhelper.setCssClase("campositem");
						TAhelper.setId("COMENTARIO_ESTADO_FOLIO_MOSTRAR_FOLIO");
						TAhelper.setNombre("COMENTARIO_ESTADO_FOLIO_MOSTRAR_FOLIO");
						TAhelper.setRows("2");
						TAhelper.render(request,out);
					}catch(HelperException re){
						out.println("ERROR " + re.getMessage());
					}
					out.println("	</td>");
					out.newLine();
					out.println("	</tr>");
				}

			//SI SE QUIERE SABER TODOS LOS ESTADOS Y SUS COMENTARIOS
			}else{

				List historialTurno = folio.getHistorialEstados();

				out.println("<tr><td colspan=\"3\">");
				out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\">");

				int indexHistoriaEstado = 1;
				java.util.Iterator itTH = historialTurno.iterator();
				while(itTH.hasNext()){
					EstadoHistoria eh = (EstadoHistoria)itTH.next();

					out.println("	<tr>");
					out.newLine();
					out.println("	<td width=\"20\">&nbsp;</td>");
					out.newLine();
					out.println("	<td class=\""+this.eCampoTexto+"\" width=\"5%\">&nbsp;"+(""+indexHistoriaEstado)+"");
					out.newLine();
					out.println("	<td class=\""+this.eCampoTexto+"\" width=\"10%\">&nbsp;"+(eh.getEstadoDestino().getNombre() != null ? eh.getEstadoDestino().getNombre() : "")+"");
					out.newLine();
					out.println("	<td class=\""+this.eCampoTexto+"\" width=\"75%\">&nbsp;"+(eh.getComentario() != null ? eh.getComentario() : "")+"");
					out.newLine();
					out.println("	<td class=\""+this.eCampoTexto+"\" width=\"20%\">&nbsp;"+(eh.getFechaRegistro()!= null ? FechaConFormato.formatear( eh.getFechaRegistro(), "dd/MM/yyyy HH:mm:ss") : "")+"");
					out.newLine();
					out.println("	<td class=\""+this.eCampoTexto+"\" width=\"10%\">&nbsp;"+(eh.getUsuario() != null ? String.valueOf(eh.getUsuario().getIdUsuario()) : "")+"");
					out.newLine();
					out.println("	</td>");
					out.newLine();
					out.println("	</tr>");

					indexHistoriaEstado++;
				}

				out.println("</table>");
				out.println("</td></tr>");

			}



			out.newLine();
			out.println("</table>");
			out.newLine();
			out.println("<br>");

			/*Fin segmento informacion de aperturas del folio----------------*/
	}

	private String obtenerStringFecha(Date date) {
		Calendar calendar = Calendar.getInstance();
		String fecha;

		if (date != null) {
			calendar.setTime(date);
			int mes = calendar.get(Calendar.MONTH) + 1;
			int dia = calendar.get(Calendar.DAY_OF_MONTH);
			if (dia < 10) {
				fecha = "0" + dia;
			} else {
				fecha = String.valueOf(dia);
			}


			fecha += "/" + mes + "/" + calendar.get(Calendar.YEAR);

		} else {
			fecha = "";
		}
		return fecha;
	}


	/**
	 * Este método pinta los datos del segemento del encabezado de informacion de complementacion
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawComplementacion(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {

			/* Inicio segmento Complementacion --------------*/

			out.newLine();
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.newLine();
			out.println("<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
			out.newLine();
			out.println("<tr>");
			out.newLine();
			out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("<td background=\""+request1.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\""+this.eTitulosSecciones+"\"> Complementaci&oacute;n</td>");
			if(this.edicionDatosComplementacion){
				out.println("	<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><a href=\"javascript:guardarComplementacion()\"> <img src=\""+request.getContextPath()+"/jsp/images/btn_mini_guardar.gif\" border=\"0\" alt=\"Guardar Complementación\"></td>");
				out.newLine();
			}
			out.newLine();
			out.println("<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+ this.imagenSeccionEncabezado +"\" width=\"16\" height=\"21\"></td>");
			out.newLine();
			out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("</tr>");
			out.newLine();
			out.println("</table>");
			out.newLine();
			out.println("<br>");
			out.newLine();
			
			if(this.edicionDatosComplementacion){
				out.println("<form action=\""+this.nombreDoEdicionDatosEncabezado+"\" method=\"post\" name=\""+this.nombreFormaEdicionComplementacion+"\" id=\""+this.nombreFormaEdicionComplementacion+"\">");
				out.newLine();
				out.println(" <input type=\"hidden\" name=\""+WebKeys.ACCION+"\" id=\""+WebKeys.ACCION+"\" value=\"\">");
				out.newLine();
			}

			if (folio.getComplementacion()!=null && folio.getComplementacion().getComplementacion()!=null){
				if(!this.edicionDigitadorMasivo)
				session.setAttribute(CFolio.COMPLEMENTACION, folio.getComplementacion().getComplementacion());
			}else{
				session.setAttribute(CFolio.COMPLEMENTACION, "");
			}
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
			out.newLine();
			out.println("	<tr>");
			out.newLine();
			out.println("		<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
			out.newLine();
			
			if(!this.edicionDatosComplementacion){
				out.println("		<td class=\""+this.eCampoTexto+"\"><p>");
						if (folio.getComplementacion()!=null && folio.getComplementacion().getComplementacion()!= null){
							out.println(folio.getComplementacion().getComplementacion());
						}else{
							out.println("");
						}
				out.println("		</p></td>");
				out.newLine();
				out.println("	</tr>");
				out.newLine();
				out.println("	<br>");
				out.newLine();
			}else{
				out.println("		<td>");
				TextAreaHelper complementacion = new TextAreaHelper();
				complementacion.setCols("100");
				complementacion.setRows("10");
				complementacion.setCssClase(this.eCampoTexto);
				complementacion.setId(CFolio.COMPLEMENTACION);
				complementacion.setNombre(CFolio.COMPLEMENTACION);
				complementacion.setReadOnly(true);
				complementacion.render(request,out);
				out.println("		</td>");
				out.newLine();
				out.println("	</tr>");
				out.newLine();
				out.println("</table>");
				
				if(this.edicionDigitadorMasivo){
					out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
					out.newLine();
					out.println("	<tr>");
					out.newLine();
					out.println("		<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");				
					out.println("		<td>");
					TextAreaHelper complementacionAdd = new TextAreaHelper();
					complementacionAdd.setCols("100");
					complementacionAdd.setRows("10");
					complementacionAdd.setCssClase(this.eCampoTexto);
					complementacionAdd.setCssClase("camposformtext");
					complementacionAdd.setId(CFolio.COMPLEMENTACION_ADICION);
					complementacionAdd.setNombre(CFolio.COMPLEMENTACION_ADICION);
					complementacionAdd.setReadOnly(false);
					complementacionAdd.render(request,out);
					out.println("		</td>");
					out.newLine();
					out.println("	</tr>");
					out.newLine();
					out.println("</table>");
				}
				
				out.println("</form> ");
				out.newLine();
				out.println("<br>");
			}
			/* Fin segmento Complementacion --------------*/

	}
        
	/**
	 * @author: Cesar Ramirez
	 * @change: 1245.HABILITAR.TIPO.PREDIO
	 * Método que dibuja en el JSP el campo de tipo Predio según su edición o visualización.
	 **/
	public void drawTipoPredio(HttpServletRequest request1, JspWriter out) throws IOException, HelperException {
		/* Inicio segmento campo Tipo de Predio */
		out.newLine();
		out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		out.println("<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
		out.println("    <tr>");
		out.println("        <td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.println("        <td background=\""+request1.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\""+this.eTitulosSecciones+"\"><p>Datos Básicos</p></td>");
		// Visualiza el botón para almacenar el Tipo de Predio
		if(this.edicionTipoPredio) {
			out.println("        <td width=\"16\" class=\""+this.eTitulosSecciones+"\"><a href=\"javascript:editarTipoPredio()\"><img src=\""+request.getContextPath()+"/jsp/images/btn_mini_guardar.gif\" border=\"0\" alt=\"Guardar Edición Tipo del Predio\"></td>");
		}
		out.println("        <td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+ this.imagenSeccionEncabezado +"\" width=\"16\" height=\"21\"></td>");
		out.println("        <td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
		out.println("    </tr>");
		out.println("</table>");
		out.newLine();
		out.println("<br>");
		// Si cumple la condición, visualiza el combobox para editar el Tipo de Predio
		if(this.edicionTipoPredio) {
			out.newLine();
			out.println("<form action=\""+this.nombreDoEdicionDatosEncabezado+"\" method=\"post\" name=\""+this.nombreFormaEdicionTipoPredio+"\" id=\""+this.nombreFormaEdicionTipoPredio+"\">");
			out.println("    <input type=\"hidden\" name=\""+WebKeys.ACCION+"\" id=\""+WebKeys.ACCION+"\" value=\"\">");
			out.newLine();
			out.println("    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
			out.println("        <tr>");
			out.println("            <td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
			out.println("            <td>Tipo del Predio </td>");
			
			//LISTA TIPOS DE PREDIO
			ListaElementoHelper tipoPredioHelper = new ListaElementoHelper();
			List tiposPredio = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);
			if (tiposPredio == null) {
				tiposPredio = new Vector();
			}
			tipoPredioHelper.setOrdenar(false);
			tipoPredioHelper.setTipos(tiposPredio);
			tipoPredioHelper.setCssClase("camposformtext");
			out.println("            <td colspan=\"2\">");
			try {
				tipoPredioHelper.setNombre(CFolio.FOLIO_TIPO_PREDIO);
				tipoPredioHelper.setId(CFolio.FOLIO_TIPO_PREDIO);
				tipoPredioHelper.setSelected(folio.getTipoPredio().getIdPredio());
				tipoPredioHelper.render(request, out);
			} catch (HelperException re) {
				out.println("ERROR TIPO PREDIO " + re.getMessage());
			}
			out.println("            </td>");
			out.println("        </tr>");
			out.println("    </table>");
			out.newLine();
			out.println("</form>");
		} else {
			out.newLine();
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
			out.println("    <tr>");
			out.println("        <td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
			out.println("        <td>Tipo del Predio </td>");
			out.println("        <td class=\""+this.eCampoTexto+"\">"+ (folio.getTipoPredio().getNombre() != null ? folio.getTipoPredio().getNombre() : "")+"</td>");
			out.println("    </tr>");
			out.println("</table>");
		}
		out.newLine();
		out.println("<br>");
		/* Fin segmento campo Tipo de Predio */
	}

	/**
	 * Este método pinta los datos del segemento del encabezado de informacion de complementacion
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawDireccionInmueble(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {

			/* Inicio segmento Dirección del inmueble --------------*/

			out.println("	<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.newLine();
			out.println("	<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
			out.newLine();
			out.println("	<tr>");
			out.newLine();
			out.println("	<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("	<td background=\""+request1.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\""+this.eTitulosSecciones+"\"><p>Direcci&oacute;n del inmueble</p></td>");
			if(this.edicionDatosDireccion){
				if(this.edicionDigitadorMasivo){//pq
    
					out.println("	<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><a href=\"javascript:editarDireccion()\"> <img src=\""+request.getContextPath()+"/jsp/images/btn_mini_guardar.gif\" border=\"0\" alt=\"Guardar Edición Direcciones\"></td>");
					out.newLine();
				}
			}
			out.newLine();
			out.println("	<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+ this.imagenSeccionEncabezado +"\" width=\"16\" height=\"21\"></td>");
			out.newLine();
			out.println("	<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("	</tr>");
			out.newLine();
			out.println("</table>");
			out.newLine();
                        if(this.edicionDatosDireccion){
                         // Inicio Edición Determinación Inmueble
                            out.println("<form action=\""+this.nombreDoEdicionDatosEncabezado+"\" method=\"post\" name=\""+this.nombreFormaEdicionDireccion+"\" id=\""+this.nombreFormaEdicionDireccion+"\">");
                            out.newLine();
                            out.println(" <input type=\"hidden\" name=\""+WebKeys.ACCION+"\" id=\""+WebKeys.ACCION+"\" value=\"\">");
                            out.println(" <input type=\"hidden\" name=\""+WebKeys.POSICION+"\" id=\""+WebKeys.POSICION+"\" value=\"\">");
                            out.println("<table width=\"100%\" class=\""+this.eCampos+"\">");
                            out.println("<tr>");
                            out.println("<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
                            out.println("<td> Determinacion del Inmueble </td>");
                            // LISTA TIPOS DE DETERMINACION DE INMUEBLE
                            ListaElementoHelper tipoDetermInmHelper = new ListaElementoHelper();
                            List determInm = (List) session.getServletContext().getAttribute(WebKeys.LISTA_DETERMINACION_INM);
                            if(determInm == null){
                                determInm = new ArrayList();
                            }
                            tipoDetermInmHelper.setTipos(determInm);
                            tipoDetermInmHelper.setCssClase("camposformtext");
                            if(!this.edicionDigitadorMasivo){
                            out.println("<td>");
                            try {
                            tipoDetermInmHelper.setNombre(CFolio.FOLIO_DETERMINACION_INMUEBLE);
                            tipoDetermInmHelper.setId(CFolio.FOLIO_DETERMINACION_INMUEBLE);
                            tipoDetermInmHelper.setSelected(folio.getDeterminaInm());
                            tipoDetermInmHelper.render(request, out);
                            } catch (HelperException re) {
                            out.println("ERROR " + re.getMessage());
                             }
                            out.println("</td>");
                            } else{
                                out.println("<td>");
                            try {
                                tipoDetermInmHelper.setNombre(CFolio.FOLIO_DETERMINACION_INMUEBLE);
                                tipoDetermInmHelper.setId(CFolio.FOLIO_DETERMINACION_INMUEBLE);
                                tipoDetermInmHelper.setSelected(folio.getDeterminaInm());
                                tipoDetermInmHelper.render(request, out);
                                } catch (HelperException re) {
                                out.println("ERROR " + re.getMessage());
                                 }
                                out.println("</td>");
                            }
                            out.println("</tr>");
                            out.println("</table>");
                         // Fin Edición Determinación Inmueble
                        }
			out.println("<br>");

			/**
			 * @author: Cesar Ramirez
			 * @change: 1245.HABILITAR.TIPO.PREDIO
			 * Se oculta la visualización del Tipo Predio (sólo para la fase de calificación) ya que se utilizará por fuera del formulario de Dirección del Inmueble.
			 * Para los casos en donde sólo será consultado el Tipo Predio se mostrará este campo dentro del formulario de Dirección del Inmueble.
			 **/
			if(this.mostrarTipoPredioEnDireccionInmueble) {
				out.newLine();
				out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
				out.println("    <tr>");
				out.println("        <td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
				out.println("        <td>Tipo del Predio </td>");
				out.println("        <td class=\""+this.eCampoTexto+"\">"+ (folio.getTipoPredio().getNombre() != null ? folio.getTipoPredio().getNombre() : "")+"</td>");
				out.println("    </tr>");
				out.println("</table>");
			}
			out.newLine();

			if(!this.edicionDatosDireccion){

			out.println("<table width=\"100%\" class=\""+this.eCampos+"\">");
				if(folio.getDirecciones().isEmpty()){
					out.newLine();
					out.println("	<tr>");
					out.newLine();
					out.println("	<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
					out.newLine();
					out.println("	<td class=\""+this.eCampoTexto+"\">No tiene direcciones registradas para este folio</td>");
					out.newLine();
					out.println("	</tr>      ");
				} else {
					int indexDireccion = 1;
					Iterator itDirecciones = folio.getDirecciones().iterator();
					while(itDirecciones.hasNext()){
						Direccion direccion = (Direccion) itDirecciones.next();
						if(!direccion.isToDelete()){
							out.newLine();
							out.println("			<tr>");
							out.newLine();
							out.println("			<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
							out.newLine();
							out.println("			<td class=\""+this.eCampoTexto+"\"><b>"+(""+indexDireccion)+"&nbsp;&nbsp;-</b>&nbsp;&nbsp;"+direccion.toString()+"</td>");
							out.newLine();
							out.println("			</tr>	");
							indexDireccion++;
						}
					}
                        
				}
			out.newLine();
			out.println("</table>");
                        
                        // Determinación del Inmueble
                        out.println("<table width=\"100%\" class=\""+this.eCampos+"\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\">");
                        out.println("<tr>");
                        out.println("<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
                        out.println("<td>Determinaci&oacute;n del Inmueble </td>");
                        out.println("<td class=\""+this.eCampoTexto+"\">"+(folio.getDeterminaInm() != null ? folio.getDeterminaInm() : "&nbsp;")+"</td>");
                        out.println("</tr>");
                        out.println("</table>");
                  
                        out.println("<table width=\"100%\" class=\""+this.eCampos+"\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\">");
                        out.println("<tr>");
                        out.println("<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
                        out.println("<td>Destinaci&oacute;n Econ&oacute;mica </td>");
                        out.println("<td class=\""+this.eCampoTexto+"\">"+(folio.getDestEconomica()!= null ? folio.getDestEconomica(): "Por Determinar")+"</td>");
                        out.println("</tr>");
                        out.println("</table>");

			}




		 	if(this.edicionDatosDireccion){
				out.println("<script>");

				out.newLine();
							out.println("function jsFolioDireccionDelItemFromDefinitivas( pos,accion ){");
				out.newLine();
				out.println("	if(confirm(\"Esta seguro que desea eliminar este item ?\")){");
				out.newLine();
				out.println("		document."+this.nombreFormaEdicionDireccion + "." + WebKeys.POSICION + ".value = pos;");
				out.newLine();
				out.println("		eliminarDireccionDef(accion);");
				out.newLine();
				out.println("	}");
				out.println("	}");
				out.newLine();

				out.println("</script>");


				
					out.newLine();
					DireccionesHelper dirHelper = new DireccionesHelper(this.nombreAccionAgregarDireccion,this.nombreAccionEliminarDireccion);
					dirHelper.setFolioSesion(WebKeys.FOLIO);
					if(this.dirTemporales != null){
						dirHelper.setDirTemporales(this.dirTemporales);
						dirHelper.setTurno(turno);
					}
					if(this.edicionDigitadorMasivo){
						dirHelper.setFuncionCambiarAccion("agregarDireccion");
						dirHelper.setTurno(turno);						
					}else{
						dirHelper.setFuncionCambiarAccion("cambiarAccionCorreccion");
					}
				   if(isEdicionDatosDireccionDefinitiva()){
					   dirHelper.setEnabledDeleteFromDefinitivas( true );   
				   }				   
				   dirHelper.setJsFunctionNameDeleteFromDefinitivas( "jsFolioDireccionDelItemFromDefinitivas" );
				   dirHelper.setActionIdDeleteFromDefinitivas( this.nombreAccionEliminarDireccionDefinitiva );
				   dirHelper.setNombreFormaEdicionDireccion(this.nombreFormaEdicionDireccion);

				dirHelper.render(request,out);
				out.println("</form>");
		 	}



			out.newLine();
			out.println("<br>");

			/* Fin segmento Dirección del inmueble --------------*/

	}

	/**
	 * Este método pinta los datos del segemento del encabezado de informacion de complementacion
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawMayorExtension(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {

			/* Inicio segmento Mayor extension --------------*/

			String mayorExtText="no";
			if(esFolioMayorExtension){
				mayorExtText="";
			}
			out.newLine();
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.newLine();
			out.println("	<tr>");
			out.newLine();
			out.println("		<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("		<td background=\""+request1.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\""+this.eTitulosSecciones+"\">El folio "+ mayorExtText +" es de mayor extensi&oacute;n</td>");
			out.newLine();
			out.println("		<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+"/jsp/images/ico_anotacion.gif\" width=\"16\" height=\"21\"></td>");
			out.newLine();
			out.println("		<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("	</tr>");
			out.newLine();
			out.println("</table>");

			/* Fin segmento Mayor extension --------------*/

	}


	/**
	 * Este método pinta los datos del segemento del encabezado de la descripcion de cabida y linderos.
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawCabidaLinderos(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {

			/* Inicio segmento Descripción: Cabida y Linderos --------------*/

			out.newLine();
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.newLine();
			out.println("<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
			out.newLine();
			out.println("<tr>");
			out.newLine();
			out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("<td background=\""+request1.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\""+this.eTitulosSecciones+"\"><p>Descripci&oacute;n: Cabida y Linderos</p></td>");
			if(this.edicionDatosLinderos){
				out.println("	<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><a href=\"javascript:editarCabidaYLinderos()\"> <img src=\""+request.getContextPath()+"/jsp/images/btn_mini_guardar.gif\" border=\"0\" alt=\"Guardar Edición Cabida y Linderos\"></td>");
				out.newLine();
			}
			out.newLine();
			out.println("<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+ this.imagenSeccionEncabezado +"\" width=\"16\" height=\"21\"></td>");
			out.newLine();
			out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("</tr>");
			out.newLine();
			out.println("</table>");
			out.newLine();
			out.println("<br>");

			if(this.edicionDatosLinderos){
				out.println("<form action=\""+this.nombreDoEdicionDatosEncabezado+"\" method=\"post\" name=\""+this.nombreFormaEdicionLinderos+"\" id=\""+this.nombreFormaEdicionLinderos+"\">");
				out.newLine();
				out.println(" <input type=\"hidden\" name=\""+WebKeys.ACCION+"\" id=\""+WebKeys.ACCION+"\" value=\"\">");
				out.newLine();
			}

			if (folio.getLindero()!=null){
				if(!this.edicionDigitadorMasivo)
				session.setAttribute(CFolio.LINDERO, folio.getLindero());
			}else{
				session.setAttribute(CFolio.LINDERO, "");
			}

			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
			out.newLine();
			out.println("	<tr>");
			out.newLine();
			out.println("		<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
			out.newLine();
                        out.println("<td> Linderos &nbsp;&nbsp;&nbsp;&nbsp;</td>");
			out.println("		<td>");

			TextAreaHelper area = new TextAreaHelper();
			  area.setCols("100");
			  area.setRows("10");
                          area.setCssClase(this.eCampoTexto);
			  area.setId(CFolio.LINDERO);
			  area.setNombre(CFolio.LINDERO);
			  area.setReadOnly(true);  
			  area.render(request,out);

			out.println("		</td>");
			out.newLine();
                        out.println("<td>");
                        out.println("<table class=\"camposformnoborder\">");
                        out.println("<tr>");
                        
                        out.println("<td>");
                        out.write("<table width=\"50%\" class=\"camposform\">");
                        out.println("<tr align=\"center\">");
                        out.println("<td>");
                        out.println("Hectareas");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("Metros<sup>2</sup>");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("Centimetros<sup>2</sup>");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("Fecha");
                        out.println("</td>");
                        out.println("</tr>");
                        //TextHelper que identifica las areas de mostrarfolio asi como sus campos de correccion helper
                        TextHelper helper = new TextHelper();
                        
                        boolean htrlExist = false;
                        
                        if (historialAreas != null && historialAreas.size() > 0 ) {
                          htrlExist = true;
                        }
                        
                        if (htrlExist){
                        Iterator itArea = historialAreas.iterator();
                        //TextHelper que identifica el historial de areas del folio historialHelper
                        TextHelper historialHelper = new TextHelper();
                        int i = 0;
                        while(itArea.hasNext()){
                            HistorialAreas historia = (HistorialAreas) itArea.next();
                            String hectareas = historia.getHectareas();
                            String metros = historia.getMetros();
                            String centimetros = historia.getCentimetros();
                            
                            if(hectareas == null || hectareas.equals("null")){
                                hectareas = "";
                            }
                            if(metros == null || metros.equals("null")){
                                metros = "";
                            }
                            if(centimetros == null || centimetros.equals("null")){
                                centimetros = "";
                            }
                          
                            SimpleDateFormat dateForm = new SimpleDateFormat("dd/MM/yyy");
                            String fechaActualizacion = dateForm.format(historia.getFechaActualizacion());
                            out.println("<tr align=\"center\">");
                            out.println("<td>");
                            historialHelper.setId(CHistorialAreas.HISTORIAL_HECTAREAS + i);
                            historialHelper.setNombre(CHistorialAreas.HISTORIAL_HECTAREAS + i);
                            historialHelper.setSize("15");
                            historialHelper.setMaxlength("15");
                            historialHelper.setCssClase("campositem");
                            historialHelper.setReadonly(true);
                            historialHelper.setFuncion(" value=\""+hectareas+"\"");
                            historialHelper.render(request,out);				
                            out.write("</td>");
                            out.println("<td>");
                            historialHelper.setId(CHistorialAreas.HISTORIAL_METROS + i);
                            historialHelper.setNombre(CHistorialAreas.HISTORIAL_METROS + i);
                            historialHelper.setSize("15");
                            historialHelper.setMaxlength("15");
                            historialHelper.setCssClase("campositem");
                            historialHelper.setReadonly(true);
                            historialHelper.setFuncion(" value=\""+metros+"\"");
                            historialHelper.render(request,out);				
                            out.write("</td>");
                            out.println("<td>");
                            historialHelper.setId(CHistorialAreas.HISTORIAL_CENTIMETROS + i);
                            historialHelper.setNombre(CHistorialAreas.HISTORIAL_CENTIMETROS + i);
                            historialHelper.setSize("15");
                            historialHelper.setMaxlength("4");
                            historialHelper.setCssClase("campositem");
                            historialHelper.setFuncion(titulo);
                            historialHelper.setReadonly(true);
                            historialHelper.setFuncion(" value=\""+centimetros+"\"");
                            historialHelper.render(request,out);				
                            out.write("</td>");
                            out.println("<td>");
                            historialHelper.setId(CHistorialAreas.HISTORIAL_FECHA_ACTUALIZACION + i);
                            historialHelper.setNombre(CHistorialAreas.HISTORIAL_FECHA_ACTUALIZACION);
                            historialHelper.setSize("15");
                            historialHelper.setMaxlength("15");
                            historialHelper.setCssClase("campositem");
                            historialHelper.setReadonly(true);
                            historialHelper.setFuncion(" value=\""+(fechaActualizacion!=null? fechaActualizacion:"")+"\"");
                            historialHelper.render(request,out);				
                            out.write("</td>");
                            out.println("</tr>");
                            i++;
                        }
                        } else{
                            out.println("<tr>");
                            out.println("<td colspan=\"4\"> Historial de Area Vacio<td>");
                            out.println("</tr>");
                        }
                        out.println("</table>");
                        out.println("</td>");
                        out.println("</tr>");
                        
                        out.println("<tr>");
                        if(!this.edicionDatosLinderos){
                        // Areas
                        out.println("<td>");
                        out.println("<table class=\"camposformnoborder\">");
                        //Coeficiente 
                        out.println("<tr align=\"right\">");
                        out.println("<td colspan=\"6\">Coeficiente</td>");
                        out.println("<td width=\"100\" class=\""+this.eCampoTexto+"\">"+(folio.getCoeficiente()!= null ? folio.getCoeficiente(): "&nbsp;")+"</td>");
                        out.println("</tr>");
                        out.println("<tr> <td>&nbsp;</td> </tr>");
                        out.println("<tr>");
                        out.println("<td nowrap colspan=\"3\">Area Privada</td>");
                        out.println("<td align=\"right\">Metros<sup>2</sup></td>");
                        out.println("<td width=\"100\" class=\""+this.eCampoTexto+"\">"+(folio.getPrivMetros()!= null ? folio.getPrivMetros(): "&nbsp;")+"</td>");
                        out.println("<td align=\"right\">Centimetros<sup>2</sup></td>");
                        out.println("<td width=\"100\" class=\""+this.eCampoTexto+"\">"+(folio.getPrivCentimetros()!= null ? folio.getPrivCentimetros(): "&nbsp;")+"</td>");
                        out.println("</tr>");
                        out.println("<tr> <td>&nbsp;</td> </tr>");
                        out.println("<tr>");
                        out.println("<td nowrap colspan=\"3\">Area Construida</td>");
                        out.println("<td align=\"right\">Metros<sup>2</sup></td>");
                        out.println("<td width=\"100\" class=\""+this.eCampoTexto+"\">"+(folio.getConsMetros()!= null ? folio.getConsMetros(): "&nbsp;")+"</td>");
                        out.println("<td align=\"right\">Centimetros<sup>2</sup></td>");
                        out.println("<td width=\"100\" class=\""+this.eCampoTexto+"\">"+(folio.getConsCentimetros()!= null ? folio.getConsCentimetros(): "&nbsp;")+"</td>");
                        out.println("</tr>");
                        out.println("<tr> <td>&nbsp;</td> </tr>");
                        out.println("<tr>");
                        out.println("<td>Area </td>");
                        out.println("<td align=\"right\">Hectareas</td>");
                        out.println("<td width=\"100\" class=\""+this.eCampoTexto+"\">"+(folio.getHectareas()!= null ? folio.getHectareas(): "&nbsp;")+"</td>");
                        out.println("<td align=\"right\">Metros<sup>2</sup></td>");
                        out.println("<td width=\"100\" class=\""+this.eCampoTexto+"\">"+(folio.getMetros()!= null ? folio.getMetros(): "&nbsp;")+"</td>");
                        out.println("<td align=\"right\">Centimetros<sup>2</sup></td>");
                        out.println("<td width=\"100\" class=\""+this.eCampoTexto+"\">"+(folio.getCentimetros()!= null ? folio.getCentimetros(): "&nbsp;")+"</td>");
                        out.println("</tr>");
                        out.println("</table>");
                        out.println("</td>");
                        // Final de Areas
                        }
                        out.println("<tr>");
                        out.println("</table>");
                        out.println("</td>");
                        
			out.println("	</tr>");
			out.newLine();

			if(this.edicionDatosLinderos){
                                
				out.println("	<tr>");
				out.newLine();
				out.println("<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
				out.newLine();
                                out.println("<td></td>");
				out.println("		<td>");
					area = new TextAreaHelper();
					area.setCols("100");
					area.setRows("10");
					if(this.edicionDatosLinderos){
						area.setCssClase("camposformtext");
					} else {
						area.setCssClase(this.eCampoTexto);
					}
				    area.setId(CFolio.LINDERO_ADICION);
				    area.setNombre(CFolio.LINDERO_ADICION);
				    area.setReadOnly(!this.edicionDatosLinderos);
				    area.render(request,out);
				out.println("		</td>");
				out.newLine();
                                // Edición de Areas
                                out.println("<td>");
                                out.println("<table class=\"camposformnoborder\">");
                                //Coeficiente 
                                out.println("<tr align=\"right\">");
                                out.println("<td colspan=\"6\">Coeficiente</td>");
                                out.println("<td>");
                                helper.setId(CFolio.FOLIO_COEFICIENTE);
                                helper.setNombre(CFolio.FOLIO_COEFICIENTE);
                                helper.setSize("8");
                                helper.setMaxlength("100");
                                helper.setCssClase("camposformtext");
                                helper.setFuncion(" value=\""+(folio.getCoeficiente()!=null? folio.getCoeficiente():"")+"\"");
                                helper.setReadonly(false);
                                helper.render(request,out);
                                TextHelper areasHelper = new TextHelper();
                                areasHelper.setCssClase("camposformtext");
                                out.write("</td>");
                                out.println("</tr>");
                                out.println("<tr> <td>&nbsp;</td> </tr>");
                                out.println("<tr>");
                                out.println("<td nowrap colspan=\"3\">Area Privada</td>");
                                out.println("<td align=\"right\">Metros<sup>2</sup></td>");
                                out.println("<td>");
                                areasHelper.setId(CFolio.FOLIO_PRIVMETROS);
                                areasHelper.setNombre(CFolio.FOLIO_PRIVMETROS);
                                areasHelper.setSize("8");
                                areasHelper.setMaxlength("9");
                                areasHelper.setCssClase("camposformtext");
                                areasHelper.setFuncion(" onkeypress=\"return valideKeyBD(event,'"+CFolio.FOLIO_PRIVMETROS+"');\"  "
                                        + "onChange=\"valideDot('"+CFolio.FOLIO_PRIVMETROS+"')\"  "
                                        + "onBlur=\"onlyMetrosFormatter('"+CFolio.FOLIO_PRIVMETROS+"','"+CFolio.FOLIO_PRIVCENTIMETROS+"')\"  "
                                        + "value=\""+(folio.getPrivMetros()!=null? folio.getPrivMetros():"")+"\" ");
                                areasHelper.setReadonly(false);
                                areasHelper.render(request,out);				
                                out.write("</td>");
                                out.println("<td align=\"right\">Centimetros<sup>2</sup></td>");
                                out.println("<td>");
                                areasHelper.setId(CFolio.FOLIO_PRIVCENTIMETROS);
                                areasHelper.setNombre(CFolio.FOLIO_PRIVCENTIMETROS);
                                areasHelper.setSize("8");
                                areasHelper.setMaxlength("4");
                                areasHelper.setCssClase("camposformtext");
                                areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" value=\""+(folio.getPrivCentimetros()!=null? folio.getPrivCentimetros():"")+"\"");
                                areasHelper.setReadonly(false);
                                areasHelper.render(request,out);
                                out.write("</td>");
                                out.println("</tr>");
                                out.println("<tr> <td>&nbsp;</td> </tr>");
                                out.println("<tr>");
                                out.println("<td nowrap colspan=\"3\">Area Construida</td>");
                                out.println("<td align=\"right\">Metros<sup>2</sup></td>");
                                out.println("<td>");
                                areasHelper.setId(CFolio.FOLIO_CONSMETROS);
                                areasHelper.setNombre(CFolio.FOLIO_CONSMETROS);
                                areasHelper.setSize("8");
                                areasHelper.setMaxlength("9");
                                areasHelper.setCssClase("camposformtext");
                                areasHelper.setFuncion(" onkeypress=\"return valideKeyBD(event,'"+CFolio.FOLIO_CONSMETROS+"');\" "
                                        + "onChange=\"valideDot('"+CFolio.FOLIO_CONSMETROS+"')\"  "
                                        + "onBlur=\"onlyMetrosFormatter('"+CFolio.FOLIO_CONSMETROS+"','"+CFolio.FOLIO_CONSCENTIMETROS+"')\"  "
                                        + "value=\""+(folio.getConsMetros()!=null? folio.getConsMetros():"")+"\"");
                                areasHelper.setReadonly(false);
                                areasHelper.render(request,out);
                                out.write("</td>");
                                out.println("<td align=\"right\">Centimetros<sup>2</sup></td>");
                                out.println("<td>");
                                areasHelper.setId(CFolio.FOLIO_CONSCENTIMETROS);
                                areasHelper.setNombre(CFolio.FOLIO_CONSCENTIMETROS);
                                areasHelper.setSize("8");
                                areasHelper.setMaxlength("4");
                                areasHelper.setCssClase("camposformtext");
                                areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" value=\""+(folio.getConsCentimetros()!=null? folio.getConsCentimetros():"")+"\"");
                                areasHelper.setReadonly(false);
                                areasHelper.render(request,out);
                                out.println("</tr>");
                                out.println("<tr> <td>&nbsp;</td> </tr>");
                                out.println("<tr>");
                                out.println("<td>Area </td>");
                                out.println("<td align=\"right\">Hectareas</td>");
                                out.println("<td>");
                                areasHelper.setId(CFolio.FOLIO_HECTAREAS);
                                areasHelper.setNombre(CFolio.FOLIO_HECTAREAS);
                                areasHelper.setSize("8");
                                areasHelper.setMaxlength("20");
                                areasHelper.setCssClase("camposformtext");
                                areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+CFolio.FOLIO_HECTAREAS+"');\"  "
                                        + "onChange=\"valideDot('"+CFolio.FOLIO_HECTAREAS+"')\"  "
                                        + "onBlur=\"hectareasFormatter('"+CFolio.FOLIO_HECTAREAS+"','"+CFolio.FOLIO_METROS+"','"+CFolio.FOLIO_CENTIMETROS+"')\"  "
                                        + "value=\""+(folio.getHectareas()!=null? folio.getHectareas():"")+"\" ");
                                areasHelper.setReadonly(false);
                                areasHelper.render(request,out);
                                out.write("</td>");
                                out.println("<td align=\"right\">Metros<sup>2</sup></td>");
                                out.println("<td>");
                                areasHelper.setId(CFolio.FOLIO_METROS);
                                areasHelper.setNombre(CFolio.FOLIO_METROS);
                                areasHelper.setSize("8");
                                areasHelper.setMaxlength("20");
                                areasHelper.setCssClase("camposformtext");
                                areasHelper.setFuncion(" onkeypress=\"return valideKeyDot(event,'"+CFolio.FOLIO_METROS+"');\"  "
                                        + "onChange=\"valideDot('"+CFolio.FOLIO_METROS+"')\"  "
                                        + "onBlur=\"metrosFormatter('"+CFolio.FOLIO_HECTAREAS+"','"+CFolio.FOLIO_METROS+"','"+CFolio.FOLIO_CENTIMETROS+"')\"  "
                                        + "value=\""+(folio.getMetros()!=null? folio.getMetros():"")+"\"");
                                areasHelper.setReadonly(false);
                                areasHelper.render(request,out);
                                out.write("</td>");
                                out.println("<td align=\"right\">Centimetros<sup>2</sup></td>");
                                out.println("<td>");
                                areasHelper.setId(CFolio.FOLIO_CENTIMETROS);
                                areasHelper.setNombre(CFolio.FOLIO_CENTIMETROS);
                                areasHelper.setSize("8");
                                areasHelper.setMaxlength("4");
                                areasHelper.setCssClase("camposformtext");
                                areasHelper.setFuncion(" onkeypress=\"return valideKey(event);\" value=\""+(folio.getCentimetros()!=null? folio.getCentimetros():"")+"\"");
                                areasHelper.setReadonly(false);
                                areasHelper.render(request,out);
                                out.write("</td>");
                                out.println("</tr>");
                                out.println("</table>");
                                out.println("</td>");
                                // Final de Edición de Areas
				out.println("	</tr>");
				out.newLine();
			}
                        
			out.println("</table>");
                        
                        
                        //Linderos Tecnicamente Definidos
                        out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
                        out.println("<tr>");
                        out.println("<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
                        out.println("<td>Linderos Tecnicamente<br>");
                        out.println("Definidos</td>");
                        
                          out.println("<td>");
			  area.setCols("100");
			  area.setRows("10");
                          area.setCssClase(this.eCampoTexto);
			  area.setId(CFolio.LINDEROS_DEFINIDOS);
			  area.setNombre(CFolio.LINDEROS_DEFINIDOS);
                          area.setFuncion(" value=\""+folio.getLinderosDef()+"\"");
			  area.setReadOnly(true);
			  area.render(request,out);
			out.println("</td>");
                        out.println("</tr>");
                        
                        if(this.edicionDatosLinderosDefinidos){
                                out.println("	<tr>");
				out.newLine();
				out.println("<td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
				out.newLine();
                                out.println("<td></td>");
				out.println("		<td>");
					area = new TextAreaHelper();
					area.setCols("100");
					area.setRows("10");
					if(this.edicionDatosLinderosDefinidos){
						area.setCssClase("camposformtext");
					} else {
						area.setCssClase(this.eCampoTexto);
					}
				    area.setId(CFolio.DEFINIR_LINDERO);
				    area.setNombre(CFolio.DEFINIR_LINDERO);
				    area.setReadOnly(!this.edicionDatosLinderos);
                                    area.setFuncion(" value=\""+folio.getLinderosDef()+"\"");
				    area.render(request,out);
				out.println("		</td>");
				out.newLine();
				out.println("	</tr>");
				out.newLine();
                        }
                        out.println("</table>");
                        //  Final de Linderos Tecnicamente Definidos
                        

			if(this.edicionDatosLinderos){
				out.println("</form> ");
			}


			out.newLine();
			out.println("<br>");

			/* Fin segmento Descripción: Cabida y Linderos --------------*/

	}

	/**
	 * Este método pinta los datos del segemento del encabezado de la descripcion de cabida y linderos.
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawDatosDocumentos(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {
			Documento documentoFolio = folio.getDocumento();
			TipoDocumento tipoDoc =null;
			if(documentoFolio!=null){
				tipoDoc=documentoFolio.getTipoDocumento();
			}

			String textTipoDoc;
			String textNDoc;
			String textFechaDoc;

			if(tipoDoc==null){
				textTipoDoc="&nbsp;";
			}else{
				textTipoDoc=tipoDoc.getNombre();
			}
			if(documentoFolio==null){
				textNDoc="&nbsp;";
			}else{
				textNDoc=documentoFolio.getNumero();
			}
			if(documentoFolio==null){
				textFechaDoc="&nbsp;";
			}else{
				//Arreglo del bug 7125
				if(documentoFolio.getFecha() != null)
				{
					textFechaDoc=FechaConFormato.formatear(documentoFolio.getFecha(), "dd/MM/yyyy");
				}
				else
				{
					textFechaDoc = null;
				}
			}

			out.newLine();
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.newLine();
			out.println("<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
			out.newLine();
			out.println("<tr>");
			out.newLine();
			out.println("<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("<td background=\""+request1.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\""+this.eTitulosSecciones+"\"><p>Datos del Documento</p></td>");
			out.newLine();
			out.println("<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+ this.imagenSeccionEncabezado +"\" width=\"16\" height=\"21\"></td>");
			out.newLine();
			out.println("<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("</tr>");
			out.newLine();
			out.println("</table>");
			out.newLine();
			out.println("<br>");

			out.newLine();
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
			out.newLine();
			out.println("	<tr>");






		    out.println("<td>&nbsp;</td>");
		    out.println("<td>Tipo:</td>");
		    out.println("<td class=\""+this.eCampoTexto+"\"> &nbsp;"+ (textTipoDoc != null ? textTipoDoc : "") +"</td>");
		    out.println("<td>Número:</td>");
		    out.println("<td class=\""+this.eCampoTexto+"\"> &nbsp;"+ (textNDoc != null ? textNDoc : "") +" </td>");
		    out.println("<td>Fecha:</td>");
		    out.println("<td class=\""+this.eCampoTexto+"\"> &nbsp;"+ (textFechaDoc != null ? textFechaDoc : "") +" </td>");
		    out.println("<td>&nbsp;</td>");
		    out.println("<td>&nbsp;</td>");
		    out.println("</tr>");

			out.newLine();
		    out.println("	</tr>");
		    out.newLine();
		    out.println("</table>");
		    out.newLine();
		    out.println("<br>");

	}

	/**
	 * Este método pinta los datos del segemento del encabezado de la descripcion de cabida y linderos.
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawRelaciones(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {


			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.newLine();
			out.println("<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
			out.newLine();
			out.println("	<tr>");
			out.newLine();
			out.println("	  <td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("	  <td background=\""+request1.getContextPath()+"/jsp/images/sub_bgn001.gif\" class=\""+this.eTitulosSecciones+"\"><p>Matricula abierta con base en la(s) siguientes(s) matriculas(s) (En caso de integraci&oacute;n y otros)</p></td>");
			out.newLine();
			out.println("	  <td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+ this.imagenSeccionEncabezado +"\" width=\"16\" height=\"21\"></td>");
			out.newLine();
			out.println("	  <td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.newLine();
			out.println("	</tr>");
			out.newLine();
			out.println(" </table>");
			out.newLine();
			out.println("<br>");
			out.newLine();

			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
			out.newLine();
				boolean entrar=false;
				
				if (foliosDerivadoPadre != null && foliosDerivadoPadre.size() > 0 ) {
					Log.getInstance().debug(MostrarFolioHelper.class,"tiene informacion de Padres");
					entrar = true;
				}
				
				if (entrar) {
					Iterator itPadres = foliosDerivadoPadre.iterator();
					while(itPadres.hasNext()){
						FolioDerivado folioD = (FolioDerivado)itPadres.next();
						//Anotacion padreFolioD = folioD.getPadre();
						//String numRadPadreFolioD = (padreFolioD==null)?"&nbsp;":padreFolioD.getNumRadicacion();
						String numMatricula = folioD.getIdMatricula();
						out.println("<tr>");
						out.newLine();
						out.println(" <td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
						out.newLine();
						//out.println("  <td class=\""+this.eCampoTexto+"\">&nbsp;"+  numRadPadreFolioD  +"</td>");
						out.println("  <td class=\""+this.eCampoTexto+"\">&nbsp;"+  numMatricula  +"</td>");
						out.newLine();
						out.println("</tr>");
						out.newLine();
					} 
				} else {
					out.println("<tr>");
					out.newLine();
					out.println(" <td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
					out.newLine();
					out.println(" <td class=\""+this.eCampoTexto+"\">No tiene otras matriculas asociadas a este folio</td>");
					out.newLine();
					out.println("  </tr>");
					out.newLine();
				}
				
				/*if(anotacionesDefinitivas!=null){
					if(!anotacionesDefinitivas.isEmpty()){
						Anotacion temp= (Anotacion)anotacionesDefinitivas.get(0);
						if(temp!=null){
							List tpad= temp.getAnotacionesPadre();
							if(tpad!=null){
								entrar=tpad.isEmpty();
							}
						}
					}
				}

				if(entrar ){
					Iterator itPadres = ((Anotacion)anotacionesDefinitivas.get(0)).getAnotacionesPadre().iterator();
					while(itPadres.hasNext()){
					FolioDerivado folioD = (FolioDerivado)itPadres.next();
					Anotacion padreFolioD = folioD.getPadre();
					String numRadPadreFolioD = (padreFolioD==null)?"&nbsp;":padreFolioD.getNumRadicacion();

					out.println("<tr>");
					out.newLine();
					out.println(" <td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
					out.newLine();
					out.println("  <td class=\""+this.eCampoTexto+"\">&nbsp;"+  numRadPadreFolioD  +"</td>");
					out.newLine();
					out.println("</tr>");
					out.newLine();
					}
				}else if(anotacionesDefinitivas!=null && !anotacionesDefinitivas.isEmpty()){
					Anotacion ultimaAnotacion =  (Anotacion)anotacionesDefinitivas.get(anotacionesDefinitivas.size()-1);
					if(ultimaAnotacion != null && (!ultimaAnotacion.getAnotacionesHijos().isEmpty()) ){
						int cantidadAnotaciones = anotacionesDefinitivas.size();
						//ultimaAnotacion.getAnotacionesHijos()
						//Iterator itHijos = ((Anotacion)anotaciones.get(cantidadAnotaciones)).getAnotacionesHijos().iterator();
						Iterator itHijos = ultimaAnotacion.getAnotacionesHijos().iterator();
						while(itHijos.hasNext()){
							FolioDerivado folioD = (FolioDerivado)itHijos.next();
							String textHijoRadicacion;
							if(folioD.getHijo()==null){
								textHijoRadicacion="&nbsp;";
							}else{
								textHijoRadicacion=folioD.getHijo().getNumRadicacion();
							}
							out.println(" <tr>");
							out.newLine();
							out.println("	  <td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
							out.newLine();
							out.println("  <td class=\""+this.eCampoTexto+"\">&nbsp;"+ textHijoRadicacion +"</td>");
							out.newLine();
							out.println("</tr>");
							out.newLine();
						}
					}
				} else {
					out.println("<tr>");
					out.newLine();
					out.println(" <td width=\"20\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
					out.newLine();
					out.println(" <td class=\""+this.eCampoTexto+"\">No tiene otras matriculas asociadas a este folio</td>");
					out.newLine();
					out.println("  </tr>");
					out.newLine();
			  }*/
			  out.println(" </table>");
			  out.newLine();
			  out.println("<br>");
			  out.newLine();
	}

	/**
	 * Este método pinta los datos del segemento del encabezado de la descripcion de los datos relevantes de un folio.
	 * ciertas partes de este se mostraran o no;
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 * @throws IOException
	 * @throws HelperException
	 */
	public void drawDatosRelevantes(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {

		//			inicializacion valores importantes folio (falta por codificar)
		String nTurnoTramite="&nbsp;";
		boolean enTramite=false;
		if(turnoTramite!=null){
			enTramite=true;
			if(turnoTramite.getIdWorkflow()!=null){
				  nTurnoTramite=turnoTramite.getIdWorkflow();
			}
		}
		
		boolean enTramiteFolio=false;
		if(turnosTramiteFolio!=null && turnosTramiteFolio.size()>0){
			enTramiteFolio=true;
		}
		

		boolean bloqueado=false;
		String usuarioDelBloqueo="";
		String turnoBloqueo="&nbsp;";
		Date fechaBloqueo=null;
		if(usuarioBloqueo!=null){
			bloqueado=true;
			List llavesBloqueos= usuarioBloqueo.getLlavesBloqueos();
			LlaveBloqueo lb= (LlaveBloqueo)llavesBloqueos.get(0);
			List bloqueoFolios= lb.getBloqueoFolios();
			BloqueoFolio bf= (BloqueoFolio) bloqueoFolios.get(0);
			String nombre="";
			if(usuarioBloqueo.getNombre()!=null){
				nombre=usuarioBloqueo.getNombre();
			}
			String apellido="";
			if(usuarioBloqueo.getApellido1()!=null){
				apellido=usuarioBloqueo.getApellido1();
			}
			usuarioDelBloqueo=nombre+" "+apellido;
			if(bf.getIdWorkflowBloqueo()!=null){
				turnoBloqueo=bf.getIdWorkflowBloqueo();
			}
			fechaBloqueo=bf.getFechaBloqueo();
		}

		String cantidadDinero="";
		String TurnoDebeDinero="&nbsp;";
		boolean debeDineros=false;
		if(turnoDeuda!=null){
			debeDineros=true;
			if(turnoDeuda.getIdWorkflow()!=null){
				TurnoDebeDinero=turnoDeuda.getIdWorkflow();
			}
			Solicitud sol=turnoDeuda.getSolicitud();
			List liquidaciones= sol.getLiquidaciones();
			Iterator i= liquidaciones.iterator();
			for(;i.hasNext();){
				Liquidacion l=(Liquidacion) i.next();
				if(l.getPago()==null){
					cantidadDinero=Double.toString(l.getValor());
				}
			}
		}

		if(enTramite || debeDineros || bloqueado || mostrarAlertaInconsistencia||enTramiteFolio){

			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			out.println("	<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
			out.println("	<tr>");
			out.println("	<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.println("	<td class=\""+this.eTitulosSecciones+"\"><p>Informaci&oacute;n Relevante de Folio</p></td>");
			out.println("	<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+ this.imagenSeccionEncabezado +"\" width=\"16\" height=\"21\"></td>");
			out.println("	<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
			out.println("	</tr>");
			out.println("</table>");
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
				if(mostrarAlertaInconsistencia && folio.isInconsistente()){
					out.println("		<tr>");
					out.println("			<td width=\"3%\"><img src=\""+request1.getContextPath()+ "/jsp/images/ico_alerta.gif" +"\" width=\"20\" height=\"15\"></td>");
					out.println("			<td width=\"25%\" class=\""+ this.eTitulos +"\">EL FOLIO ES INCONSISTENTE</td>");
					out.println("			<td width=\"25%\">&nbsp;</td>");
					out.println("			<td width=\"22%\">&nbsp;</td>");
					out.println("			<td width=\"25%\">&nbsp;</td>");
					out.println("		</tr>");
				}

				if(enTramite){
					out.println("		<tr>");
					out.println("			<td width=\"3%\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
					out.println("			<td width=\"25%\" class=\""+ this.eTituloFolio +"\">Folio en tramite</td>");
					out.println("			<td width=\"25%\">&nbsp;</td>");
					out.println("			<td width=\"22%\">&nbsp;</td>");
					out.println("			<td width=\"25%\">&nbsp;</td>");
					out.println("		</tr>");
					out.println("		<tr>");
					out.println("			<td>&nbsp;</td>");
					out.println("			<td>Turno:</td>");
					out.println("			<td class=\""+this.eCampoTexto+"\">"+ nTurnoTramite +" </td>");
					out.println("			<td>&nbsp;</td>");
					out.println("			<td>&nbsp;</td>");
					out.println("		</tr>");
				}
				
				if(enTramiteFolio){
					out.println("		<tr>");
					out.println("			<td width=\"3%\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
					out.println("			<td width=\"25%\" class=\""+ this.eTituloFolio +"\">Folio en tramite en el Sistema FOLIO.</td>");
					out.println("			<td width=\"25%\">&nbsp;</td>");
					out.println("			<td width=\"22%\">&nbsp;</td>");
					out.println("			<td width=\"25%\">&nbsp;</td>");
					out.println("		</tr>");
					
					Iterator it = turnosTramiteFolio.iterator();
					while(it.hasNext()){
						TurnoFolioTramiteMig turnoFolioTramite = (TurnoFolioTramiteMig)it.next();
						
						String idWFTurno = "";
						idWFTurno = turnoFolioTramite.getIdTurno().substring(0, turnoFolioTramite.getIdTurno().indexOf("-")) + "-";
						idWFTurno = idWFTurno + (folio.getZonaRegistral().getCirculo().getIdCirculo() != null ? folio.getZonaRegistral().getCirculo().getIdCirculo() : "")+ "-";
						idWFTurno = idWFTurno + ""+turnoFolioTramite.getIdProceso()+ "-";
						idWFTurno = idWFTurno + turnoFolioTramite.getIdTurno().substring((turnoFolioTramite.getIdTurno().indexOf("-") + 1), turnoFolioTramite.getIdTurno().length());

						out.println("		<tr>");
						out.println("			<td>&nbsp;</td>");
						out.println("			<td>Turno:</td>");
						out.println("			<td class=\""+this.eCampoTexto+"\">"+ idWFTurno +" </td>");
						out.println("			<td>&nbsp;</td>");
						out.println("			<td>&nbsp;</td>");
						out.println("		</tr>");

					}
					
				}				
				
				if(bloqueado){
					out.println("		<tr>");
					out.println("			<td width=\"3%\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
					out.println("			<td width=\"25%\" class=\""+ this.eTituloFolio +"\">Folio bloqueado</td>");
					out.println("			<td width=\"25%\">&nbsp;</td>");
					out.println("			<td width=\"22%\">&nbsp;</td>");
					out.println("			<td width=\"25%\">&nbsp;</td>");
					out.println("		</tr>");
					out.println("		<tr>");
					out.println("			<td>&nbsp;</td>");
					out.println("			<td>Bloqueado por:</td>");
					out.println("			<td class=\""+this.eCampoTexto+"\">"+ usuarioDelBloqueo +" </td>");
					out.println("			<td>Turno:</td>");
					out.println("			<td class=\""+this.eCampoTexto+"\">"+ turnoBloqueo +" </td>");
					out.println("		</tr>");
					out.println("		<tr>");
					out.println("			<td>&nbsp;</td>");
					out.println("			<td >Fecha del bloqueo:</td>");
					out.println("			<td class=\""+this.eCampoTexto+"\">");
							try {
								if (fechaBloqueo!=null){
									fechaHelper.setOutput(MostrarFechaHelper.CAMPO_INMODIFICABLE);
									fechaHelper.setDate(fechaBloqueo);
									fechaHelper.render(request1,out);
								}
								else{
									out.println("	");
								}

							}
								catch(HelperException re){
								out.println("ERROR " + re.getMessage());
							}
					out.println("			</td>");
                                        /**
                                        * @author      :  Julio Alcazar
                                        * @change      :  Revision: Se agreaga información en el caso de que el bloqueo de folio sea por traslado
                                        * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                                        */
                                        /*               
                                         * @author     : Carlos Torres
                                         * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                         */
                                        if(trasladoDestino != null && trasladoDestino.getFechaConfirTras()==null){
                                            out.println("<td><font color=red>Motivo:</font></td>");
                                            out.println("<td class=\""+this.eCampoTexto+"\"><font color=red>PENDIENTE CONFIRMACION TRASLADO</font></td>");
                                        }else{
                                            out.println("<td width=\"22%\">&nbsp;</td>");
                                            out.println("<td width=\"25%\">&nbsp;</td>");
                                            
                                        }

					out.println("	</tr>");
				}
				if(debeDineros){
					out.println("	<tr>");
					out.println("		<td width=\"3%\"><img src=\""+request1.getContextPath()+ this.imagenSeparador +"\" width=\"20\" height=\"15\"></td>");
					out.println("		<td width=\"25%\" class=\""+ this.eTituloFolio +"\">El folio debe dinero</td>");
					out.println("		<td width=\"25%\">&nbsp;</td>");
					out.println("		<td width=\"22%\">&nbsp;</td>");
					out.println("		<td width=\"25%\">&nbsp;</td>");
					out.println("	</tr>");
					out.println("	<tr>");
					out.println("		<td>&nbsp;</td>");
					out.println("		<td>Cantidad :</td>");
					out.println("		<td class=\""+this.eCampoTexto+"\">"+ cantidadDinero+"</td>");
					out.println("		<td>Turno :</td>");
					out.println("		<td class=\""+this.eCampoTexto+"\">"+ TurnoDebeDinero+"</td>");
					out.println("					</tr>");
				}



			out.println("			</table>");

			out.println("			<br>");
		}
	}



	/**
	 * Obtiene segun el flag seleccionado el folio del session, en caso de q ç
	 * los flags esten mal configurados(no este ninguno true, o mas de uno este setteado) devuelve null
	 * @return Folio
	 */
    private Folio obtenerFolio(){
    	return this.folio;
    }

    /**
     * Obtiene el folio asociado a este helper. Necesario para el funcionamiento de este.
     * @param nfolio
     */
    public void setFolio(Folio nfolio){
    	this.folio=nfolio;
    }

    /**
     * Obtiene el turno asociado al helper.
     * @param nturno
     */
	public void setTurno(Turno nturno){
		this.turno=nturno;
	}


	/**
	 * Coloca al helper de modo consulta.
	 * <p>
	 * Modo consulta: Solo se muestran las anotaciones definitivas. No es necesario que el usuario tenga bloqueo sobre el folio.
	 * <p>
	 * Modo normal: Se muestran ambos tipos de anotaciones. Es neceario que el usuario tenga bloqueo del folio.
	 */
	public void soloConsulta(){
		consulta=true;
	}

	/**
	 * Muestra seccion de gravamenes en el encabezado.
	 */
	public void NoMostrarEncabezado(){
		this.mostrarEncabezado=false;
	}

	/**
	 * Muestra seccion de gravamenes en el encabezado.
	 */
	public void MostrarGravamenes(){
		this.mostrarGravamenes=true;
	}

	/**
	 * Muestra la seccion de informacion apertura del folio en el encabezado.
	 */
	public void MostrarAperturaFolio(){
		this.mostrarAperturaFolio=true;
	}

	/**
	 * Muestra la seccion de cabida y linderos en el encabezado.
	 */
	public void MostrarCabidaLinderos(){
		this.mostrarCabidaLinderos=true;
	}

	/**
	 * muestra la seccion de la direccion del inmueble en el encabezado.
	 */
	public void MostrarDireccionInmueble(){
		this.mostrarDireccionInmueble=true;
	}
        
	/**
	 * @author: Cesar Ramirez
	 * @change: 1245.HABILITAR.TIPO.PREDIO
	 * Muestra la sección del tipo del predio en el encabezado.
	 **/
	public void MostrarTipoPredio() {
		this.mostrarTipoPredio = true;
	}
	/* Muestra el campo Tipo Predio DENTRO de la sección de Dirección del Inmueble */
	public void MostrarTipoPredioEnDireccionInmueble() {
		this.mostrarTipoPredioEnDireccionInmueble = true;
	}

	/**
	 * muestra la seccion de mayor extension en el encabezado.
	 */
	public void MostrarMayorExtension(){
		this.mostrarMayorExtension=true;
	}

	/**
	 * muestra la seccion de complementacion en el encabezado.
	 */
	public void MostrarComplementacion(){
		this.mostrarComplementacion=true;
	}

	/**
	 * muestra la seccion de datos documentos en el encabezado.
	 */
	public void MostrarDatosDocumentos(){
		this.mostrarDatosDocumento=true;
	}

	/**
	 * muestra la seccion de relaciones en el encabezado.
	 */
	public void MostrarRelaciones(){
		this.mostrarRelaciones=true;
	}

	/**
	 * muestra la seccion de datos relevantes en el encabezado.
	 */
	public void MostrarDatosRelevantes(){
		this.mostrarDatosRelevantes=true;
	}

	/**
	 * muestra la alerta de inconsistencia en el encabezado.
	 */
	public void MostrarAlertaInconsistencia(){
		this.mostrarAlertaInconsistencia=true;
	}

	/**
	 * No se muestra las anotaciones del folio en el cuerpo del helper.
	 */
	public void NoMostrarAnotaciones(){
		this.mostrarAnotaciones=false;
	}

	/**
	 * Usado para indicar si el folio asociado al helper es o no de mayor extension.
	 * @param value
	 */
	public void setMayorExtension(boolean value){
		this.esFolioMayorExtension=value;
	}

	/**
	 * Usado para inicializar el nombre que el paginador va tener en la session. NECESARIO
	 * @param nNombrePaginador
	 */
	public void setNombrePaginador(String nNombrePaginador){
		this.nombrePaginador=nNombrePaginador;
	}

	/**
	 * Usado para inicializar el nombre que los resultados del paginador va tener en la session. NECESARIO
	 * @param nNombreResultado
	 */
	public void setNombreResultado(String nNombreResultado){
		this.nombreResultado= nNombreResultado;
	}

	/**
	 * Usado para inicializar el nombre de la forma que tiene asociado al paginador en el helper. NECESARIO
	 * @param nNombreFormaPaginador
	 */
	public void setNombreFormaPaginador(String nNombreFormaPaginador){
		this.nombreFormaPaginador=nNombreFormaPaginador;
	}

	/**
	 * Usado para inicializar el nombre de la forma dentro del paginador en el helper. NECESARIO
	 * @param nNombreForma
	 */
	public void setNombreForma(String nNombreForma){
		this.nombreForma=nNombreForma;
	}

	/**
	 * Usado para inicializar el nombre de la forma del folio en el helper. NECESARIO
	 * @param nNombreFormaFolio
	 */
	public void setNombreFormaFolio(String nNombreFormaFolio){
		this.nombreFormaFolio=nNombreFormaFolio;
	}

	/**
	 * Usado para inicializar el nombre de la accion que tiene la funcionalidad sobre el folio (editar, cancelar,
	 * crear anotaciones). NECESARIO
	 * @param nNombreAccionFolio
	 */
	public void setNombreAccionFolio(String nNombreAccionFolio){
		this.nombreAccionFolio=nNombreAccionFolio;
	}

	/**
	 * Usado para inicializar el nombre la accion que tiene la funcionalidad sobre el paginador (cambiar pagina,
	 * obtener anotaciones). NECESARIO
	 * @param nNombreAccionPaginador
	 */
	public void setNombreAccionPaginador(String nNombreAccionPaginador){
		this.nombreAccionPaginador=nNombreAccionPaginador;
	}

	/**
	 * Usado para inicializar el nombre del ancla que tiene el helper (util para centrarse en las anotaciones
	 * despues de maximizarlas). NECESARIO
	 * @param nNombreAncla
	 */
	public void setNombreAncla(String nNombreAncla){
		this.nombreAncla=nNombreAncla;
	}

	/**
	 * Usado para inicializar el nombre del parametro en la session que va indicar si las anotaciones esta o
	 * no ocultas. NECESARIO
	 * @param nNombreOcultarAnotaciones
	 */
	public void setNombreOcultarAnotaciones(String nNombreOcultarAnotaciones){
		this.nombreOcultarAnotaciones=nNombreOcultarAnotaciones;
	}

	/**
	 * Usado para inicializar el nombre del parametro en la session que va indicar si el folio esta o no oculto.
	 * NECESARIO
	 * @param nNombreOcultarAFolio
	 */
	public void setNombreOcultarFolio(String nNombreOcultarAFolio){
		this.nombreOcultarFolio=nNombreOcultarAFolio;
	}

	/**
	 * Usado para inicializar el nombre en la session donde se va guardar el numero de la anotacion temporal
	 * para la accion correspondiente (editar o eliminar). NECESARIO
	 * @param nNombreNumAnotacionTemporal
	 */
	public void setnombreNumAnotacionTemporal(String nNombreNumAnotacionTemporal){
		this.nombreNumAnotacionTemporal=nNombreNumAnotacionTemporal;
	}

	/**
	 * Usado para inicializar el nombre en la session donde se va guardar el numero de pagina actual del helper.
	 * NECESARIO
	 * @param nNombreNumPaginaActual
	 */
	public void setnombreNumPaginaActual(String nNombreNumPaginaActual){
		this.nombreNumPaginaActual=nNombreNumPaginaActual;
	}

	/**
	 * Usado para inicializar la pagina en la que va comenzar el paginador. NECESARIO <p>
	 * Nota: tener en cuenta que la 1era pagina del paginador es 0
	 * @param valor
	 */
	public void setPaginaInicial(int valor){
		this.paginaInicial=valor;
	}

	/**
	 * Obtiene a partir de los datos dados de inicio y cantidad , se setea la pagina inicial y el tamaño del
	 * cache.
	 */
	private void definirInicioYCantidad(){
		this.inicio = ((this.paginaInicial/10)* this.tamanoCache) ;
		this.cantidad = this.tamanoCache;
	}

	/**
	 * @return
	 */
	public boolean isConsultarAnotacionesDefinitivas() {
		return consultarAnotacionesDefinitivas;
	}

	/**
	 * @param b
	 */
	public void setConsultarAnotacionesDefinitivas(boolean b) {
		consultarAnotacionesDefinitivas = b;
	}

	/**
	 * Usado para inicializar el turno que tramita actualmente el folio para mostrarse en la seccion de datos
	 * relevantes en el ancabezado del helper.
	 * @param nTurnoTramite
	 */
	public void setTurnoTramite(Turno nTurnoTramite){
		this.turnoTramite=nTurnoTramite;
	}

	/**
	 * Usado para inicializar el turno deuda de esta folio para mostrarse en la seccion de datos.
	 * relevantes en el ancabezado del helper.
	 * @param nTurnoDeuda
	 */
	public void setTurnoDeuda(Turno nTurnoDeuda){
		this.turnoDeuda=nTurnoDeuda;
	}

	/**
	 * Usado para inicializar el usuario que tiene actualmente bloqueado el folio para mostrarse en la seccion de
	 * datos relevantes en el ancabezado del helper.
	 * @param nUsuarioBloqueo
	 */
	public void setUsuarioBloqueo(Usuario nUsuarioBloqueo){
		this.usuarioBloqueo=nUsuarioBloqueo;
	}

	/**
	 * Usado para inicializar el numero total de anotaciones que tiene el folio.
	 * @param nTotalAnotaciones
	 */
	public void setTotalAnotaciones(java.math.BigDecimal nTotalAnotaciones){
		this.totalAnotaciones=nTotalAnotaciones;
	}

	/**
	 * Usado para inicializar la lista de gravamenes del folio.
	 * @param nGravamenes
	 */
	public void setGravamenes(List nGravamenes){
		this.gravamenes=nGravamenes;
	}

	/**
	 * Usado para inicializar la lista de medidas cautelares del folio.
	 * @param nMedCautelares
	 */
	public void setMedCautelares(List nMedCautelares){
		this.medCautelares=nMedCautelares;
	}

	/**
	 * @return
	 */
	public List getAnotacionesInvalidas() {
		return anotacionesInvalidas;
	}

	/**
	 * @return
	 */
	public List getFalsaTradicion() {
		return falsaTradicion;
	}

	/**
	 * @param list
	 */
	public void setAnotacionesInvalidas(List list) {
		anotacionesInvalidas = list;
	}

	/**
	 * @param list
	 */
	public void setFalsaTradicion(List list) {
		falsaTradicion = list;
	}

	/**
	 * Usado para inicializar la vista actual del helper.
	 * @param nVistaActual
	 */
	public void setVistaActual(String nVistaActual){
		this.vistaActual=nVistaActual;
	}

	/**
	 * Usado para inicializar el titulo que va tener la pestaña del helper en la pagina.
	 * @param ntitulo
	 */
	public void setTitulo(String ntitulo){
		this.titulo= ntitulo;
	}

	/**
	 * Usado para inicializar el estilo en zonas donde hayan campos de texto. NECESARIO esteticamente
	 * <p> Valor recomendado: camposform
	 * @param neCampoTexto
	 */
	/* setters de estilo*/
	public void setECampoTexto(String neCampoTexto){
		this.eCampoTexto= neCampoTexto;
	}

	/**
	 * Usado para inicializar el estilo en zonas donde hayan titulos. NECESARIO esteticamente
	 * <p> Valor recomendado: titresaltados
	 * @param neTitulos
	 */
	public void setETitulos(String neTitulos){
		this.eTitulos= neTitulos;
	}

	/**
	 * Usado para inicializar el estilo de los titulos para secciones. NECESARIO esteticamente
	 * <p> Valor recomendado: bgnsub
	 * @param neTitulosSecciones
	 */
	public void setETitulosSecciones(String neTitulosSecciones){
		this.eTitulosSecciones= neTitulosSecciones;
	}

	/**
	 * Usado para inicializar el estilo de campos en el helper. NECESARIO esteticamente
	 * <p> Valor recomendado:  camposform
	 * @param neCampos
	 */
	public void setECampos(String neCampos){
		this.eCampos= neCampos;
	}

	/**
	 * Usado para inicializar el estilo del titulo del folio en el helper. NECESARIO esteticamente.
	 * <p> Valor recomendado: titulotbcentral
	 * @param neTituloFolio
	 */
	public void setETituloFolio(String neTituloFolio){
		this.eTituloFolio= neTituloFolio;
	}

	/**
	 * Usado para incializar la imagen separadora en el helper. NECESARIO esteticamente
	 * <p> Valor recomendado: /jsp/images/ind_campotxt.gif
	 * @param nimagenSeparador
	 */
	/* setters de imagenes */
	public void setImagenSeparador(String nimagenSeparador){
		this.imagenSeparador= nimagenSeparador;
	}

	/**
	 * Usado para inicizalizar la ubicacion de la imagen del folio en el helper.
	 * <p> Valor recomendado: /jsp/images/ico_matriculas.gif
	 * NECESARIO esteticamente
	 * @param nimagenFolio
	 */
	public void setImagenFolio(String nimagenFolio){
		this.imagenFolio= nimagenFolio;
	}

	/**
	 * Usado para inicializar la ubicacion de la imagen del numero de anotaciones en el helper.
	 * NECESARIO esteticamente
	 * <p> Valor recomendado: /jsp/images/ani_folios.gif
	 * @param nimagenNAnotaciones
	 */
	public void setImagenNAnotaciones(String nimagenNAnotaciones){
		this.imagenNAnotaciones= nimagenNAnotaciones;
	}

	/**
	 * Usado para inicializar la ubicacion de la imagen de la seccion de encabezado en el helper.
	 * NECESARIO esteticamente
	 * <p> Valor recomendado: /jsp/images/ico_matriculas.gif
	 * @param nimagenSeccionEncabezado
	 */
	public void setImagenSeccionEncabezado(String nimagenSeccionEncabezado){
		this.imagenSeccionEncabezado= nimagenSeccionEncabezado;
	}
	/**
	 * @return Returns the editable.
	 */
	public boolean isEditable() {
		return editable;
	}
	/**
	 * @param editable The editable to set.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	/**
	 * @return Returns the tipoMostrarFolio.
	 */
	public int getTipoMostrarFolio() {
		return tipoMostrarFolio;
	}
	/**
	 * @param tipoMostrarFolio The tipoMostrarFolio to set.
	 */
	public void setTipoMostrarFolio(int tipoMostrarFolio) {
		this.tipoMostrarFolio = tipoMostrarFolio;
	}
	/**
	 * @return Returns the ordenCancelada.
	 */
	public String getOrdenCancelada() {
		return ordenCancelada;
	}
	/**
	 * @param ordenCancelada The ordenCancelada to set.
	 */
	public void setOrdenCancelada(String ordenCancelada) {
		this.ordenCancelada = ordenCancelada;
	}
	/**
	 * @return Returns the numOrdenCancelada.
	 */
	public String getNomOrdenCancelada() {
		return nomOrdenCancelada;
	}
	/**
	 * @param numOrdenCancelada The numOrdenCancelada to set.
	 */
	public void setNomOrdenCancelada(String numOrdenCancelada) {
		this.nomOrdenCancelada = numOrdenCancelada;
	}
	/**
	 * @return Returns the nombreAccionCancelacion.
	 */
	public String getNombreAccionCancelacion() {
		return nombreAccionCancelacion;
	}
	/**
	 * @param nombreAccionCancelacion The nombreAccionCancelacion to set.
	 */
	public void setNombreAccionCancelacion(String nombreAccionCancelacion) {
		this.nombreAccionCancelacion = nombreAccionCancelacion;
	}
	/**
	 * @return Returns the nombreFormaCancelacion.
	 */
	public String getNombreFormaCancelacion() {
		return nombreFormaCancelacion;
	}
	/**
	 * @param nombreFormaCancelacion The nombreFormaCancelacion to set.
	 */
	public void setNombreFormaCancelacion(String nombreFormaCancelacion) {
		this.nombreFormaCancelacion = nombreFormaCancelacion;
	}
	/**
	 * @return Returns the idAnotacionCancelada.
	 */
	public String getIdAnotacionCancelada() {
		return idAnotacionCancelada;
	}
	/**
	 * @param idAnotacionCancelada The idAnotacionCancelada to set.
	 */
	public void setIdAnotacionCancelada(String idAnotacionCancelada) {
		this.idAnotacionCancelada = idAnotacionCancelada;
	}

        public void setEditarAnotacionesNoTemporales( boolean editarAnotacionesNoTemporales ) {
            this.editarAnotacionesNoTemporales = editarAnotacionesNoTemporales;
        }
	/**
	 * @param nombreAcccionEdicionCodigoCatastral The nombreAcccionEdicionCodigoCatastral to set.
	 */
	public void setNombreAcccionEdicionCodigoCatastral(
			String nombreAcccionEdicionCodigoCatastral) {
		this.nombreAcccionEdicionCodigoCatastral = nombreAcccionEdicionCodigoCatastral;
	}
	/**
	 * @param nombreAccionEdicionCabidaYLinderos The nombreAccionEdicionCabidaYLinderos to set.
	 */
	public void setNombreAccionEdicionCabidaYLinderos(
			String nombreAccionEdicionCabidaYLinderos) {
		this.nombreAccionEdicionCabidaYLinderos = nombreAccionEdicionCabidaYLinderos;
	}
	/**
	 * @author: Cesar Ramirez
	 * @change: 1245.HABILITAR.TIPO.PREDIO
	 * @param nombreAccionEdicionTipoPredio The nombreAccionEdicionTipoPredio to set.
	 **/
	public void setNombreAccionEdicionTipoPredio(String nombreAccionEdicionTipoPredio) {
		this.nombreAccionEdicionTipoPredio = nombreAccionEdicionTipoPredio;
	}
	/**
	 * @param nombreAccionEdicionDireccion The nombreAccionEdicionDireccion to set.
	 */
	public void setNombreAccionEdicionDireccion(
			String nombreAccionEdicionDireccion) {
		this.nombreAccionEdicionDireccion = nombreAccionEdicionDireccion;
	}
	/**
	 * @param nombreDoEdicionDatosEncabezado The nombreDoEdicionDatosEncabezado to set.
	 */
	public void setNombreDoEdicionDatosEncabezado(
			String nombreDoEdicionDatosEncabezado) {
		this.nombreDoEdicionDatosEncabezado = nombreDoEdicionDatosEncabezado;
	}
	/**
	 * @param nombreFormaEdicionDatosEncabezado The nombreFormaEdicionDatosEncabezado to set.
	 */
	public void setNombreFormaEdicionDatosEncabezado(
			String nombreFormaEdicionDatosEncabezado) {
		this.nombreFormaEdicionDatosEncabezado = nombreFormaEdicionDatosEncabezado;
	}
	/**
	 * @param edicionDatosEncabezado The edicionDatosEncabezado to set.
	 */
/*	public void setEdicionDatosEncabezado(boolean edicionDatosEncabezado) {
		this.edicionDatosEncabezado = edicionDatosEncabezado;
	}*/
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

	/**
	 * @return
	 */
	public String getNombreFormaEdicionCodCatastral() {
		return nombreFormaEdicionCodCatastral;
	}

	/**
	 * @param string
	 */
	public void setNombreFormaEdicionCodCatastral(String string) {
		nombreFormaEdicionCodCatastral = string;
	}

	/**
	 * @return
	 */
	public String getNombreFormaEdicionLinderos() {
		return nombreFormaEdicionLinderos;
	}

	/**
	 * @param string
	 */
	public void setNombreFormaEdicionLinderos(String string) {
		nombreFormaEdicionLinderos = string;
	}
        
	/**
	 * @author: Cesar Ramirez
	 * @change: 1245.HABILITAR.TIPO.PREDIO
	 * Get y Set campo.
	 **/
	public String getNombreFormaEdicionTipoPredio() {
		return nombreFormaEdicionTipoPredio;
	}

	public void setNombreFormaEdicionTipoPredio(String string) {
		nombreFormaEdicionTipoPredio = string;
	}

	/**
	 * @return
	 */
	public String getNombreAccionEliminarDireccion() {
		return nombreAccionEliminarDireccion;
	}

	/**
	 * @param string
	 */
	public void setNombreAccionEliminarDireccion(String string) {
		nombreAccionEliminarDireccion = string;
	}

	/**
	 * @return
	 */
	public String getNombreAccionEliminarDireccionDefinitiva() {
		return nombreAccionEliminarDireccionDefinitiva;
	}

	/**
	 * @param string
	 */
	public void setNombreAccionEliminarDireccionDefinitiva(String string) {
		nombreAccionEliminarDireccionDefinitiva = string;
	}

	public void mostrarAnotacionesCancelacioMultiple(String accion){
		mostrarAnotacionesCancelacioMultiple = true;
		this.accionGuardarAnotMultCancelacion = accion;
	}

	/**
	 * @return
	 */
	public String getNombreAccionAgregarDireccion() {
		return nombreAccionAgregarDireccion;
	}

	/**
	 * @param string
	 */
	public void setNombreAccionAgregarDireccion(String string) {
		nombreAccionAgregarDireccion = string;
	}

	public void setMostrarMedidasYGravamenes(boolean mostrar, Calendar anho){
		this.mostrarMedidasYGravamenes = mostrar;
		this.anio = anho;
	}

	/**
	 * @param b
	 */
	public void setEdicionDatosCodCatastral(boolean b) {
		edicionDatosCodCatastral = b;
	}

	/**
	 * @param b
	 */
	public void setEdicionDatosDireccion(boolean b) {
		edicionDatosDireccion = b;
	}

	/**
	 * @param b
	 */
	public void setEdicionDatosLinderos(boolean b) {
		edicionDatosLinderos = b;
	}
        
        /**
	 * @param b
	 */
	public void setEdicionDatosLinderosDefinidos(boolean b) {
		edicionDatosLinderosDefinidos = b;
	}
        
	//pq
	public boolean isEdicionDigitadorMasivo() {
		return edicionDigitadorMasivo;
	}
        
	/**
	 * @author: Cesar Ramirez
	 * @change: 1245.HABILITAR.TIPO.PREDIO
	 * Get y Set campo
	 **/
	public boolean isEdicionTipoPredio() {
		return edicionTipoPredio;
	}
	
	public void setEdicionTipoPredio(boolean edicionTipoPredio) {
		this.edicionTipoPredio = edicionTipoPredio;
	}
        
	public void setEdicionDigitadorMasivo(boolean edicionDigitadorMasivo) {
		this.edicionDigitadorMasivo = edicionDigitadorMasivo;
	}


    /**
     * Obtener el atributo mostrarTurnos
     *
     * @return Retorna el atributo mostrarTurnos.
     */
    public boolean isMostrarTurnos() {
        return mostrarTurnos;
    }


    /**
     * Actualizar el valor del atributo mostrarTurnos
     * @param mostrarTurnos El nuevo valor del atributo mostrarTurnos.
     */
    public void setMostrarTurnos(boolean mostrarTurnos) {
        this.mostrarTurnos = mostrarTurnos;
    }

	/**
	 * @return
	 */
	public boolean isMostrarHistorialEstados() {
		return mostrarHistorialEstados;
	}

	public String getAnotacionEditLink() {
		return anotacionEditLink;
	}

	public boolean isTemporalConContraparteDefinitivaEnabled() {
		return temporalConContraparteDefinitivaEnabled;
	}

	public boolean isCheckboxController_MultiSelectEnabled() {
		return checkboxController_MultiSelectEnabled;
	}

	public String getCheckboxController_JsControllerName() {
		return checkboxController_JsControllerName;
	}

	public String getCheckboxController_TargetFormFieldId() {
		return checkboxController_TargetFormFieldId;
	}

	public String getCheckboxController_SourceFormFieldId() {
		return checkboxController_SourceFormFieldId;
	}

	public boolean isCheckboxController_MultiSelectDebugEnabled() {
		return checkboxController_MultiSelectDebugEnabled;
	}

	public String getCheckboxController_TargetFormId() {
              return getNombreFormaCancelacion();
	}

	/**
	 * @param b
	 */
	public void setMostrarHistorialEstados(boolean b) {
		mostrarHistorialEstados = b;
	}

	public void setAnotacionEditLink(String anotacionEditLink) {
		this.anotacionEditLink = anotacionEditLink;
	}

	public void setTemporalConContraparteDefinitivaEnabled(boolean
		 temporalConContraparteDefinitivaEnabled) {
		this.temporalConContraparteDefinitivaEnabled =
			 temporalConContraparteDefinitivaEnabled;
	}

	public void setCheckboxController_MultiSelectEnabled(boolean
		 checkboxController_MultiSelectEnabled) {
		this.checkboxController_MultiSelectEnabled =
			 checkboxController_MultiSelectEnabled;
	}

	public void setCheckboxController_JsControllerName(String
		 checkboxController_JsControllerName) {
		this.checkboxController_JsControllerName =
			 checkboxController_JsControllerName;
	}

	public void setCheckboxController_TargetFormFieldId(String
																 checkboxController_FormFieldId) {
		this.checkboxController_TargetFormFieldId = checkboxController_FormFieldId;
	}

	public void setCheckboxController_SourceFormFieldId(String
		 checkboxController_SourceFormFieldId) {
		this.checkboxController_SourceFormFieldId =
			 checkboxController_SourceFormFieldId;
	}

	public void setCheckboxController_MultiSelectDebugEnabled(boolean
		 checkboxController_MultiSelectDebugEnabled) {
		this.checkboxController_MultiSelectDebugEnabled =
			 checkboxController_MultiSelectDebugEnabled;
	}


	public boolean isCorreciones() {
		return isCorreciones;
	}


	public void setCorreciones(boolean isCorreciones) {
		this.isCorreciones = isCorreciones;
	}


	public boolean isEnabledCheckAnotacionesTemporales() {
		return enableCheckAnotacionesTemporales;
	}


	public void setEnabledCheckAnotacionesTemporales(
			boolean enableCheckAnotacionesTemporales) {
		this.enableCheckAnotacionesTemporales = enableCheckAnotacionesTemporales;
	}


	public boolean isEdicionDatosDireccionDefinitiva() {
		return edicionDatosDireccionDefinitiva;
	}


	public void setEdicionDatosDireccionDefinitiva(boolean edicionDatosDireccionDefinitiva) {
		this.edicionDatosDireccionDefinitiva = edicionDatosDireccionDefinitiva;
	}


	public List getAnotacionesAfectacionVivienda() {
		return anotacionesAfectacionVivienda;
	}


	public void setAnotacionesAfectacionVivienda(List anotacionesAfectacionVivienda) {
		this.anotacionesAfectacionVivienda = anotacionesAfectacionVivienda;
	}


	public List getAnotacionesPatrimonioFamiliar() {
		return anotacionesPatrimonioFamiliar;
	}


	public void setAnotacionesPatrimonioFamiliar(List anotacionesPatrimonioFamiliar) {
		this.anotacionesPatrimonioFamiliar = anotacionesPatrimonioFamiliar;
	}

        public List getHistorialAreas() {
            return historialAreas;
        }

        public void setHistorialAreas(List historialAreas) {
            this.historialAreas = historialAreas;
        }

        public List getFoliosDerivadoHijo() {
		return foliosDerivadoHijo;
	}


	public void setFoliosDerivadoHijo(List foliosDerivadoHijo) {
		this.foliosDerivadoHijo = foliosDerivadoHijo;
	}


	public List getFoliosDerivadoPadre() {
		return foliosDerivadoPadre;
	}


	public void setFoliosDerivadoPadre(List foliosDerivadoPadre) {
		this.foliosDerivadoPadre = foliosDerivadoPadre;
	}


	public List getTurnosTramiteFolio() {
		return turnosTramiteFolio;
	}


	public void setTurnosTramiteFolio(List turnosTramiteFolio) {
		this.turnosTramiteFolio = turnosTramiteFolio;
	}


	public boolean isEditarAnotacionesTemporales() {
		return editarAnotacionesTemporales;
	}


	public void setEditarAnotacionesTemporales(boolean editarAnotacionesTemporales) {
		this.editarAnotacionesTemporales = editarAnotacionesTemporales;
	}


	public boolean isMostrarAnotacionesFolioTemporal() {
		return mostrarAnotacionesFolioTemporal;
	}


	public void setMostrarAnotacionesFolioTemporal(
			boolean mostrarAnotacionesFolioTemporal) {
		this.mostrarAnotacionesFolioTemporal = mostrarAnotacionesFolioTemporal;
	}


	public String getNombreFormaEdicionComplementacion() {
		return nombreFormaEdicionComplementacion;
	}


	public void setNombreFormaEdicionComplementacion(
			String nombreFormaEdicionComplementacion) {
		this.nombreFormaEdicionComplementacion = nombreFormaEdicionComplementacion;
	}


	public boolean isEdicionDatosComplementacion() {
		return edicionDatosComplementacion;
	}


	public void setEdicionDatosComplementacion(boolean edicionDatosComplementacion) {
		this.edicionDatosComplementacion = edicionDatosComplementacion;
	}


	public String getNombreAccionEdicionComplementacion() {
		return nombreAccionEdicionComplementacion;
	}


	public void setNombreAccionEdicionComplementacion(
			String nombreAccionEdicionComplementacion) {
		this.nombreAccionEdicionComplementacion = nombreAccionEdicionComplementacion;
	}

	public List getDirTemporales() {
		return dirTemporales;
	}


	public void setDirTemporales(List dirTemporales) {
		this.dirTemporales = dirTemporales;
	}

        /**
        * @author      :  Julio Alcazar
        * @change      :  nuevo metodo en ver detalles de folios trasladados
        * @revision    :  Se cambio el color de los datos de la tabla y el formato
        * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
        */
       /**
        * @author      :  Edgar Lora
        * @change      :  nuevo metodo en ver detalles de folios trasladados
        * @revision    :  Se cambio el color de los datos de la tabla y el formato
        * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
        */
        public void drawDatosRelevantesTraslado(HttpServletRequest request1, JspWriter out)
		throws IOException, HelperException {
                
            /**
            * @Autor: Santiago Vásquez
            * @Change: 1478.AJUSTE TRASLADO SIR - FOLIO
            */
            //if (trasladoDestino.getFolioDestino().startsWith("FOL")) return;
                TrasladoDatos td = trasladoDestino.getTrasladoDatos();
            ArrayList fundamentos = (ArrayList)td.getFundamentosTraslados();
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
            out.println("	<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->");
            out.println("	<tr>");
            out.println("	<td width=\"12\"><img name=\"sub_r1_c1\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.println("	<td class=\""+this.eTitulosSecciones+"\"><p>Informaci&oacute;n Relevante de Traslado Folio</p></td>");
            out.println("	<td width=\"16\" class=\""+this.eTitulosSecciones+"\"><img src=\""+request1.getContextPath()+ this.imagenSeccionEncabezado +"\" width=\"16\" height=\"21\"></td>");
            out.println("	<td width=\"15\"><img name=\"sub_r1_c4\" src=\""+request1.getContextPath()+"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>");
            out.println("	</tr>");
            out.println("</table>");
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
                            out.println("	<tr>");
                            out.println("		<td>&nbsp;</td>");
                            out.println("		<td><font color=red>Circulo Origen: </font></td>");
                            if(circuloOrigen.getNombre()!= null){
                            out.println("		<td class=\""+this.eCampoTexto+"\"><font color=red>"+circuloOrigen.getNombre()+"</font></td></td>");
                            }else{
                            out.println("		<td class=\""+this.eCampoTexto+"\"><font color=red>&nbsp;</font></td></td>");
                            }
                            out.println("		<td><font color=\"red\">Numero Matricula Origen: </font></td>");
                            if(trasladoDestino.getFolioOrigen().getIdMatricula()!= null){
                            out.println("		<td class=\""+this.eCampoTexto+"\"><font color=red>"+trasladoDestino.getFolioOrigen().getIdMatricula()+"</font></td>");
                            }else{
                            out.println("		<td class=\""+this.eCampoTexto+"\"><font color=red>&nbsp;</font></td></td>");
                            }
                            out.println("   </tr>");
                            for(int i = 0; i < fundamentos.size(); i = i + 1){
                                TrasladoFundamento tf = (TrasladoFundamento) fundamentos.get(i);
                                tf.getFundamento().getNumeroFundamento();
                                tf.getFundamento().getTipoFundamento();
                                tf.getFundamento().getFechaCreacion();
                                boolean pintar = true;
                                if(trasladoDestino.getFechaConfirTras()!=null){
                                    pintar = true;
                                }else if(tf.getTipoOrigen()==0){
                                    pintar = false;
                                }
                                if(tf.getFundamento().getTipoFundamento().getIdTipoFundamento() != 1){
                                    pintar = false;
                                }
                                if(pintar){
                                    out.println("	<tr>");
                                    out.println("		<td>&nbsp;</td>");
                                    out.println("		<td><font color=red>Decreto: </font></td>");
                                    if(tf.getFundamento().getNumeroFundamento()!= null){
                                    out.println("		<td class=\""+this.eCampoTexto+"\"><font color=red>"+tf.getFundamento().getNumeroFundamento()+"</font></td></td>");
                                    }else{
                                    out.println("		<td class=\""+this.eCampoTexto+"\"><font color=red>&nbsp;</font></td></td>");
                                    }
                                    out.println("		<td><font color=\"red\">Fecha: </font></td>");
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    if(sdf.format(tf.getFundamento().getFechaCreacion())!= null){
                                    out.println("		<td class=\""+this.eCampoTexto+"\"><font color=red>"+sdf.format(tf.getFundamento().getFechaCreacion())+"</font></td>");
                                    }else{
                                    out.println("		<td class=\""+this.eCampoTexto+"\"><font color=red>&nbsp;</font></td></td>");
                                    }
                                    out.println("   </tr>");
                                }
                            }                            
            out.println("</table>");
            out.println("<br>");                
            out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"2\" class=\""+this.eCampos+"\">");
            out.println("	<tr>");
            out.println("		<td><font color=red><b>Circulo</b></font></td>");
            out.println("		<td><font color=red><b>Tipo de Fundamento</b></font></td>");
            out.println("		<td><font color=red><b>Numero de Fundamento</b></font></td>");
            out.println("		<td><font color=red><b>Fecha</b></font></td>");
            out.println("       </tr>");
            for(int i = 0; i < fundamentos.size(); i = i + 1){
                TrasladoFundamento tf = (TrasladoFundamento) fundamentos.get(i);
                tf.getFundamento().getNumeroFundamento();
                tf.getFundamento().getTipoFundamento();
                tf.getFundamento().getFechaCreacion();
                boolean pintar = true;
                if(trasladoDestino.getFechaConfirTras()!=null){
                    pintar = true;
                }else if(tf.getTipoOrigen()==0){
                    pintar = false;
                }
                if(tf.getFundamento().getTipoFundamento().getIdTipoFundamento() == 1){
                    pintar = false;
                }
                if(pintar){
                    out.println("<tr>");
                    if(tf.getTipoOrigen() == 1){
                        out.println("<td class=\""+this.eCampoTexto+"\" style=\"width=25%\"><font color=red>ORIGEN</font></td>");
                    }else{
                        out.println("<td class=\""+this.eCampoTexto+"\" style=\"width=25%\"><font color=red>DESTINO</font></td>");
                    }                        
                    if(tf.getFundamento().getTipoFundamento()!= null){
                        out.println("<td class=\""+this.eCampoTexto+"\" style=\"width=25%\"><font color=red>"+tf.getFundamento().getTipoFundamento().getNombre()+"</font></td>");
                    }else{
                        out.println("<td class=\""+this.eCampoTexto+"\" style=\"width=25%\"><font color=red>&nbsp;</font></td></td>");
                    }
                    if(tf.getFundamento().getNumeroFundamento()!= null){
                        out.println("<td class=\""+this.eCampoTexto+"\" style=\"width=25%\"><font color=red>"+tf.getFundamento().getNumeroFundamento()+"</font></td></td>");
                    }else{
                        out.println("<td class=\""+this.eCampoTexto+"\" style=\"width=25%\"><font color=red>&nbsp;</font></td></td>");
                    }
                    if(tf.getFundamento().getFechaCreacion()!= null){
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        out.println("<td class=\""+this.eCampoTexto+"\" style=\"width=25%\"><font color=red>"+sdf.format(tf.getFundamento().getFechaCreacion())+"</font></td>");
                    }else{
                        out.println("<td class=\""+this.eCampoTexto+"\" style=\"width=25%\"><font color=red>&nbsp;</font></td></td>");
                    }
                    out.println("</tr>");
                }
            }
            out.println("</table>");
            out.println("<br />");
	}
        
}

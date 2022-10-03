package gov.sir.core.web.acciones.comun;

import gov.sir.core.eventos.comun.EvnPaginadorAnotaciones;
import gov.sir.core.eventos.comun.EvnRespPaginadorAnotaciones;
//import gov.sir.core.negocio.modelo.AnotacionesFolio;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CFolio;
/**
* @autor: Edgar Lora
* @Mantis: 0010690
* @Requerimiento: 068_151
*/
import gov.sir.core.negocio.modelo.util.ComparadorAnotaciones;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.PaginadorCacheNoActualizadoException;
import gov.sir.core.web.acciones.excepciones.PaginadorPaginaNoEncontradaException;

import gov.sir.core.web.helpers.comun.DatosRespuestaPaginador;
import gov.sir.core.web.helpers.comun.JvLocalUtils;
import gov.sir.core.web.helpers.comun.MostrarFolioHelper;
import gov.sir.core.web.helpers.comun.PaginadorAvanzado;
/**
* @autor: Edgar Lora
* @Mantis: 0010690
* @Requerimiento: 068_151
*/
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Esta acción web es la encargada de recibir los parametros para la
 * consulta de anotaciones y manda un evento para que se realice.
 * @author ppabon, ddiaz
 */
public class AWPaginadorAnotaciones extends SoporteAccionWeb {

	private String accion;
	private HttpSession session;
	private PaginadorAvanzado pag;
	public static final String CONSULTAR_ANOTACIONES = "CONSULTAR_ANOTACIONES";
	public static final String CONSULTAR_ANOTACIONES_FOLIO = "CONSULTAR_ANOTACIONES_FOLIO";
	public static final String REFRESCAR_PAGINADOR = "REFRESCAR_PAGINADOR";
	public static final String VISTA_ORIGINADORA = "VISTA_ORIGINADORA";
	public static final String PAGINA_INICIAL= "PAGINA_INICIAL";
	public static final String FOLIO_HELPER = "FOLIO_HELPER";
	public static final String NUM_PAGINA_ACTUAL = "NUM_PAGINA_ACTUAL";
	public static final String TERMINAR_CONSULTA = "TERMINAR_CONSULTA";
	public static final String NOMBRE_NUM_PAGINA_ACTUAL = "NOMBRE_NUM_PAGINA_ACTUAL";
	public static final String PAGINADOR_AVANZADO = "PAGINADOR_AVANZADO";
	public static final String TIPO_CONSULTA = "TIPO_CONSULTA";
	public static final String ANOTACIONES_DEFINITIVAS= "ANOTACIONES_DEFINITIVAS";
	public static final int TAMANO_PAGINA_CACHE = 100; //para mejor funcionamiento la gracia es q el TAMANO_PAGINA/10=TAMANO_PAGINA_CACHE
	public static final int TAMANO_PAGINA = 10;

        public static final String MULTISELECT_ENABLED_SESSIONMARK = "MULTISELECT_ENABLED_SESSIONMARK";

		public static final String ANOTACIONES_FOLIO_TEMPORAL = "ANOTACIONES_FOLIO_TEMPORAL";

	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		session = request.getSession();
		accion = request.getParameter(WebKeys.ACCION);

		if ((accion == null) || (accion.length() == 0)) {
			throw new AccionWebException("Debe indicar una accion");
		}

		if (accion.equals(CONSULTAR_ANOTACIONES)) {
			return consultarAnotaciones(request);
		} else if (accion.equals(CONSULTAR_ANOTACIONES_FOLIO)) {
			return consultarAnotacionesFolio(request);
		} else if (accion.equals(REFRESCAR_PAGINADOR)) {
			return cambiarPagina(request);
		} else if (accion.equals(TERMINAR_CONSULTA)) {
			return terminarConsulta(request);
		} else {
			throw new AccionInvalidaException("Debe indicar una acción válida. La acción " + accion + " no es válida");
		}
	}

	/**
	 * Método que permite llamar a los métodos de la acción de neegocio para consultar
	 * las anotaciones de un folio
	 * @param request
	 * @return
	 */
	private Evento consultarAnotaciones(HttpServletRequest request) throws AccionWebException {

		boolean datosValidos = true;
		boolean consultarAnotacionesDefinitivas = false;

		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
		String idZonaRegistral = request.getParameter(CFolio.ID_ZONA_REGISTRAL);
		String criterio = request.getParameter(CCriterio.CRITERIO);
		String anotacionesDefinitivas = request.getParameter(AWPaginadorAnotaciones.ANOTACIONES_DEFINITIVAS);

		if(anotacionesDefinitivas!=null && anotacionesDefinitivas.equals("TRUE")){
			consultarAnotacionesDefinitivas = true;
		}

		String valorCriterio = null;
		if(criterio==null){
			criterio = CCriterio.TODAS_LAS_ANOTACIONES;
		}else{
			valorCriterio = request.getParameter(CCriterio.VALOR_CRITERIO);
		}

		String inicio =	request.getParameter(WebKeys.INICIO);
		String cantidad =	request.getParameter(WebKeys.CANTIDAD);


		if (idMatricula == null || idZonaRegistral== null) {
			datosValidos = false;
		}
		if (criterio == null) {
			datosValidos = false;
		}

		if (inicio == null) {
			datosValidos = false;
		}else{
			try{
				Integer d = new Integer(inicio);
			}catch(Exception e){
				datosValidos = false;
			}
		}

		if (cantidad == null) {
			datosValidos = false;
		}else{
			try{
				Integer d = new Integer(cantidad);
			}catch(Exception e){
				datosValidos = false;
			}
		}

		if(datosValidos==true){
			FolioPk folioID = new FolioPk();
			folioID.idMatricula= idMatricula;
			org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
			Usuario usuarioNeg=(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);

			EvnPaginadorAnotaciones evento = new EvnPaginadorAnotaciones(usuarioAuriga, folioID, criterio, valorCriterio, new Integer(inicio).intValue(), new Integer(cantidad).intValue(), EvnPaginadorAnotaciones.CONSULTAR_ANOTACIONES, consultarAnotacionesDefinitivas);
			evento.setUsuarioNeg(usuarioNeg);
			return evento;
		}else{
			return null;
		}

	}

	/**
	 * Método que permite llamar a los métodos de la acción de neegocio para consultar
	 * las anotaciones de un folio
	 * @param request
	 * @return
	 */
	private Evento consultarAnotacionesFolio(HttpServletRequest request) throws AccionWebException {

		boolean datosValidos = true;
		boolean consulta=false;
		boolean consultarAnotacionesDefinitivas = false;
		boolean anotacionDelta = false;

		//se verifica si se debe consultar únicamente las definitivas
		String anotacionesDefinitivas = request.getParameter(AWPaginadorAnotaciones.ANOTACIONES_DEFINITIVAS);

		if(anotacionesDefinitivas!=null && anotacionesDefinitivas.equals("TRUE")){
			consultarAnotacionesDefinitivas = true;
		}
		
		request.getSession().setAttribute("folioPosibleTemporal","true");

		//inicio de parametros para la consulta
		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
		String idZonaRegistral = request.getParameter(CFolio.ID_ZONA_REGISTRAL);
		String inicio =	request.getParameter(WebKeys.INICIO);
		String cantidad =	request.getParameter(WebKeys.CANTIDAD);
		String tPaginaInicial = request.getParameter(AWPaginadorAnotaciones.PAGINA_INICIAL);
		String nombrePaginador = request.getParameter(WebKeys.NOMBRE_PAGINADOR);
		request.getSession().setAttribute(WebKeys.NOMBRE_PAGINADOR, nombrePaginador);
		String nombreResultado = request.getParameter(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
		request.getSession().setAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR, nombreResultado);
		
		boolean consultarAnotacionesFolioTemporal = false;
		String consultarAnotacionesFolioTemporalStr = request.getParameter(ANOTACIONES_FOLIO_TEMPORAL);
		if(consultarAnotacionesFolioTemporalStr!=null && consultarAnotacionesFolioTemporalStr.equals("TRUE")){
			consultarAnotacionesFolioTemporal = true;
		}
		
		Boolean tempdelta =(Boolean) request.getSession().getAttribute("ANOTACION_DELTA");
		if(tempdelta!=null){
			anotacionDelta=tempdelta.booleanValue();
		}
		
		int paginaInicial=0;
		if((tPaginaInicial==null || tPaginaInicial.equals("")) && nombrePaginador != null &&
			nombrePaginador.equals("NOMBRE_PAGINADOR_CALIFICACION")){
			PaginadorAvanzado pa = (PaginadorAvanzado)request.getSession().getAttribute("NOMBRE_PAGINADOR_CALIFICACION");
			if(pa != null)
				tPaginaInicial = String.valueOf(pa.getNumeroPagina());
			
		}
		if(tPaginaInicial!=null && !tPaginaInicial.equals("")){
			paginaInicial=Integer.parseInt(tPaginaInicial);
		}
		Boolean temp=(Boolean) request.getSession().getAttribute(AWPaginadorAnotaciones.TIPO_CONSULTA);
		if(temp!=null){
			consulta=temp.booleanValue();
		}

		if (idMatricula == null || idZonaRegistral== null) {
			datosValidos = false;
		}

		if (inicio == null) {
			datosValidos = false;
		}else{
			try{
				Integer d = new Integer(inicio);
			}catch(Exception e){
				datosValidos = false;
			}
		}

		if (cantidad == null) {
			datosValidos = false;
		}else{
			try{
				Integer d = new Integer(cantidad);
			}catch(Exception e){
				datosValidos = false;
			}
		}

		if(datosValidos==true){
			FolioPk folioID = new FolioPk();
			folioID.idMatricula= idMatricula;
			org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
			Usuario usuarioNeg=(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
            //TODO
            // if que valida sí el usuario digitó la anotación desde la cual quiere visualizar.
            // Si el usuario digito el número de anotación crea el evento con esta anotación cómo inicial.
            // En caso contrario crea el evento cómo se hacia anteriormente.
            //inicio = String.valueOf(AnotacionesFolio.getNumAnotacionesFolio());
            EvnPaginadorAnotaciones e;
            // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
            // Cambio para asolucionar requerimiento 117 - Incidencia Mantis 02399.
            // Se cambió la variable estática por atributos en sesion y en el evento EvnPaginadorAnotaciones.
            Integer numAnotaDesdeV = (Integer)request.getSession().getAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_DESDE);
            Integer numAnotaTotalV = (Integer)request.getSession().getAttribute(WebKeys.CAMPO_NUM_ANOTACIONES_TOTAL);
            /**
             * @author Cesar Ramírez
             * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
             * Se obtiene por sesión el rol del usuario y se asigna a un string para enviarlo por parámetro al constructor.
             **/
            org.auriga.core.modelo.transferObjects.Rol rol = (org.auriga.core.modelo.transferObjects.Rol) request.getSession().getAttribute(WebKeys.ROL);
            String rolID = rol != null ? rol.getRolId() : "";
            //if(AnotacionesFolio.getNumAnotacionesFolio()>0 && AnotacionesFolio.getNumAnotacionesTotalV()>0){
            if(numAnotaDesdeV != null && numAnotaTotalV != null && numAnotaDesdeV>0 && numAnotaTotalV>0){
                e = new EvnPaginadorAnotaciones(usuarioAuriga, folioID, numAnotaDesdeV, numAnotaTotalV, EvnPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO, usuarioNeg, consultarAnotacionesDefinitivas, rolID);
                e.setAnotcionesDesdeV(numAnotaDesdeV);
                e.setNumAnotcionesTotalV(numAnotaTotalV);
            }else{
                e = new EvnPaginadorAnotaciones(usuarioAuriga, folioID, new Integer(inicio).intValue(), new Integer(cantidad).intValue(), EvnPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO, usuarioNeg, consultarAnotacionesDefinitivas, rolID);
            }
            
			if(consulta){
				e.setConsulta(consulta);
			}
			if (anotacionDelta){
				e.setGetAnotacionesConDeltadas(true);
			}
			
			e.setNombrePaginador(nombrePaginador);
			e.setNombreResultado(nombreResultado);
			e.setConsultarAnotacionesFolioTemporal(consultarAnotacionesFolioTemporal);
			if(paginaInicial>0){
				e.setPaginaInicial(paginaInicial);
			}

			return e;
		}else{
			return null;
		}

	}

	/**
	 * Método que permite llamar a los métodos de la acción de neegocio para consultar
	 * las anotaciones de un folio
	 * @param request
	 * @return
	 */
	private Evento cambiarPagina(HttpServletRequest request) throws AccionWebException {
		//Obtener el numero de la pagina a la que se desea cambiar.



		String nombrePaginador = (String)request.getSession().getAttribute(WebKeys.NOMBRE_PAGINADOR);
		String nombreResultado = (String)request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
		boolean consultarAnotacionesDefinitivas = false;

		//se verifica si se debe consultar únicamente las definitivas
		String anotacionesDefinitivas = request.getParameter(AWPaginadorAnotaciones.ANOTACIONES_DEFINITIVAS);

		if(anotacionesDefinitivas!=null && anotacionesDefinitivas.equals("TRUE")){
			consultarAnotacionesDefinitivas = true;
		}


		//obtener el paginadorAvanzado
		PaginadorAvanzado pag = (PaginadorAvanzado) request.getSession().getAttribute(nombrePaginador);
		if(pag==null){
			Log.getInstance().debug(AWPaginadorAnotaciones.class, "\n NO EXISTE UN PAGINADOR ACTIVO. \n");
		}

		//Obtener numeroPaginaActual
		String nombrePaginaActual = (String)request.getSession().getAttribute(AWPaginadorAnotaciones.NOMBRE_NUM_PAGINA_ACTUAL);
		String numPagina= (String)request.getParameter(nombrePaginaActual);
		request.getSession().setAttribute(AWPaginadorAnotaciones.NUM_PAGINA_ACTUAL, numPagina);
		//obtener folio
		Folio folio = (Folio) session.getAttribute(AWPaginadorAnotaciones.FOLIO_HELPER);

		List aDefinitivas=null;
		int npagina=0;
        //TODO
        // Se cambió la forma de preguntar en el if
        // colocando primero la cadena vacia y llamar al método equals sobre esta cadena
        // para evitar un NullPointerException.
		//if(numPagina!=null && numPagina!=""){
        if(numPagina!=null && !"".equals(numPagina)){
			npagina= Integer.parseInt(numPagina);
		}


		pag.setNumeroPagina(npagina);
		try{
			aDefinitivas= pag.getPaginaDesdeCache(npagina);
		}catch(PaginadorPaginaNoEncontradaException e){
			//se retorna el evento donde uno va ir por las anotaciones q no estan.
			boolean consulta=false;
			consulta=pag.getTipoConsulta();
			int inicio;
			inicio = ((npagina/10)* pag.getTamanoCacheResultados()) ;
			int cantidad= TAMANO_PAGINA_CACHE;
			FolioPk folioID = new FolioPk();
			folioID.idMatricula= folio.getIdMatricula();
			org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
			Usuario usuarioNeg=(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
			EvnPaginadorAnotaciones ev=new EvnPaginadorAnotaciones(usuarioAuriga, folioID, inicio, cantidad, EvnPaginadorAnotaciones.CARGAR_PAGINA, usuarioNeg,consultarAnotacionesDefinitivas);
			if(consulta){
				ev.setConsulta(consulta);
			}
			ev.setNombrePaginador(nombrePaginador);
			ev.setNombreResultado(nombreResultado);
			return ev;
		}catch (PaginadorCacheNoActualizadoException e) {
			e.printStackTrace(System.out);
		}
			//se coloca en el ResultadoPaginador los nuevas anotaciones a mostrar
			DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
                        /**
                         * @autor: Edgar Lora
                         * @Mantis: 0010690
                         * @Requerimiento: 068_151
                         */
                        Collections.sort(aDefinitivas, new ComparadorAnotaciones());
			RPag.setAnotacionesActual(aDefinitivas);


                // TODO:
                // bug 05042: collect the selected annotations if needed
                filter_PaginadorMultiselect_Enabled( request );







		// la logica definira si se toca ver con el negocio o no?
		return null;
	}

  // ---------------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------------

  // Bug 05042

  private void filter_PaginadorMultiselect_Enabled(HttpServletRequest request) {
    doProcess_PaginadorMultiselect_SaveState( request );
  } // end-method: filter_PaginadorMultiselect_Enabled

  private void
  doProcess_PaginadorMultiselect_SaveState( HttpServletRequest request ) {

          HttpSession session;
          session = request.getSession();
          final String PREFIX = "PAGINADOR:";
          final String CHECKBOXCONTROLLER_MULTISELECTENABLED = "CHECKBOXCONTROLLER_MULTISELECTENABLED";
          final String CHECKBOXCONTROLLER_TARGETFORMFIELDID  = "CHECKBOXCONTROLLER_TARGETFORMFIELDID";
          //TODO
          // Se cambió la forma de preguntar en el if siguiente.
          // Se estaba creando un Boolean cuando se podía preguntar directamente
          // sin necesidad de asignar a una variable y luego comparar.
          /*Boolean checkBoxController_MultiSelectEnabled;
          checkBoxController_MultiSelectEnabled = null;

          if( ( null == ( checkBoxController_MultiSelectEnabled = (Boolean)session.getAttribute( PREFIX + CHECKBOXCONTROLLER_MULTISELECTENABLED ) ) )
            // ||( !( Boolean.TRUE.equals( checkBoxController_MultiSelectEnabled ) ) )
          ) {
              return;

          } // if
          */
          if(null == session.getAttribute( PREFIX + CHECKBOXCONTROLLER_MULTISELECTENABLED )){
              return;
          }

          String checkboxController_TargetFormFieldId;
          checkboxController_TargetFormFieldId = null;
          if( ( null == ( checkboxController_TargetFormFieldId = (String)session.getAttribute( PREFIX + CHECKBOXCONTROLLER_TARGETFORMFIELDID ) ) ) ) {
             return;
          }

          // bug 5042:
          // must be collected in the paginator
          final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID   = checkboxController_TargetFormFieldId;

          String idsAnotacionesCsv = request.getParameter( LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID );

         // only the last is added
         String[] idsAnotaciones = JvLocalUtils.csvStringToStringArray( idsAnotacionesCsv, JvLocalUtils.DEFAULT_SEPARATOR, true );
         session.setAttribute( MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, idsAnotaciones );

         // remove the keys after save state
         session.removeAttribute( PREFIX + CHECKBOXCONTROLLER_MULTISELECTENABLED );
         session.removeAttribute( PREFIX + CHECKBOXCONTROLLER_TARGETFORMFIELDID  );


  } // end-method: doProcess_PaginadorMultiselect_SaveState
// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------



	/**
	 * Método que permite llamar a los métodos de la acción de neegocio para consultar
	 * las anotaciones de un folio
	 * @param request
	 * @return
	 */
	private Evento terminarConsulta(HttpServletRequest request) throws AccionWebException {

		String nombrePaginador = (String)request.getSession().getAttribute(WebKeys.NOMBRE_PAGINADOR);
		String nombreResultado = (String)request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);

		if(nombrePaginador!=null){
			request.getSession().removeAttribute(nombrePaginador);
		}
		if(nombreResultado!=null){
			request.getSession().removeAttribute(nombreResultado);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

		EvnRespPaginadorAnotaciones respuesta = (EvnRespPaginadorAnotaciones) evento;

		if (respuesta != null) {
			if (respuesta.getTipoEvento().equals(EvnRespPaginadorAnotaciones.CONSULTAR_ANOTACIONES)) {
				session.setAttribute(WebKeys.CANTIDAD_REGISTROS, "" + respuesta.getTotalRegistros());
				session.setAttribute(WebKeys.LISTA_ANOTACIONES, "" + respuesta.getAnotaciones());
			}
			if (respuesta.getTipoEvento().equals(EvnRespPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO)) {
				int tamlt = (respuesta.getAnotacionesTemporales()==null)?-1:respuesta.getAnotacionesTemporales().size();
				int tamld = (respuesta.getAnotacionesDefinitivas()==null)?-1:respuesta.getAnotacionesDefinitivas().size();
				String ScantidadRegistros=Long.toString(respuesta.getTotalRegistros());
				Log.getInstance().debug(AWPaginadorAnotaciones.class, "Datos sobre la carga \n" +
					"# de registros = "+ ScantidadRegistros+ "\n"+
					"tamaño lista definitivos"+tamld+"\n"+
					"tamaño lista temporales"+tamlt+"\n");

				//DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(respuesta.getNombreResultado());
				//if(RPag==null){
				DatosRespuestaPaginador RPag = new DatosRespuestaPaginador();
				//}
				RPag.setCantidadRegistros(respuesta.getTotalRegistros());
				RPag.setNumeroAnotacionesDefinitivas(respuesta.getNumeroAnotacionesDefinitivas());

                //iniciando el paginador
				//PaginadorAvanzado pag = (PaginadorAvanzado) request.getSession().getAttribute(respuesta.getNombrePaginador());
				//if(pag==null){
					PaginadorAvanzado  pag= new PaginadorAvanzado(respuesta.getAnotacionesDefinitivas(), Integer.parseInt(ScantidadRegistros));
				//}
				Log.getInstance().debug(AWPaginadorAnotaciones.class, "getNumeroPaginas: " + pag.getNumeroPaginas());
				Log.getInstance().debug(AWPaginadorAnotaciones.class, "getUltimaPaginaGenerada: " + pag.getUltimaPaginaGenerada());
				Log.getInstance().debug(AWPaginadorAnotaciones.class, "getNumPaginasCache: " + pag.getNumPaginasCache());
				
				try{
					int npagina= respuesta.getPaginaInicial();
					if(npagina!=0){
						RPag.setAnotacionesActual(pag.getPaginaDesdeCache(npagina));
					}else{
						RPag.setAnotacionesActual(pag.getPaginaDesdeCache(0));
					}
				}catch(PaginadorPaginaNoEncontradaException e){
					//se retorna el evento donde uno va ir por las anotaciones q no estan.
					RPag.setAnotacionesActual(new Vector());
				}catch (PaginadorCacheNoActualizadoException e) {
					e.printStackTrace(System.out);
				}
				//settear consulta
				Boolean temp=(Boolean) request.getSession().getAttribute(AWPaginadorAnotaciones.TIPO_CONSULTA);
				if(temp!=null){
					pag.setTipoConsulta(temp.booleanValue());
				}
				request.getSession().setAttribute(respuesta.getNombrePaginador(),pag);
				request.getSession().setAttribute(respuesta.getNombreResultado(),RPag);
				if (respuesta.getFoliosDerivadoPadre() != null) {
					request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_PADRE, respuesta.getFoliosDerivadoPadre());	
				}
				
				if (respuesta.getFoliosDerivadoHijo() != null) {
					request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO, respuesta.getFoliosDerivadoHijo());
				}
				
				/*borrar variable recarga */
				request.getSession().removeAttribute(WebKeys.RECARGA);
			}
			if (respuesta.getTipoEvento().equals(EvnRespPaginadorAnotaciones.CARGAR_PAGINA)) {
				int tamlt = (respuesta.getAnotacionesTemporales()==null)?-1:respuesta.getAnotacionesTemporales().size();
				int tamld = (respuesta.getAnotacionesDefinitivas()==null)?-1:respuesta.getAnotacionesDefinitivas().size();
				String ScantidadRegistros=Long.toString(respuesta.getTotalRegistros());
				Log.getInstance().debug(AWPaginadorAnotaciones.class, "Datos sobre la carga \n" +
					"# de registros = "+ ScantidadRegistros+ "\n"+
					"tamaño lista definitivos "+tamld+"\n"+
					"tamaño lista temporales "+tamlt+"\n");
				//iniciando objeto de DatosRespuestaPaginador
				DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(respuesta.getNombreResultado());
				if(RPag==null){
					RPag = new DatosRespuestaPaginador();
				}

				//iniciando el paginador
				PaginadorAvanzado pag = (PaginadorAvanzado) request.getSession().getAttribute(respuesta.getNombrePaginador());
				if(pag==null){
				  pag= new PaginadorAvanzado(respuesta.getAnotacionesDefinitivas(), Integer.parseInt(ScantidadRegistros));

				}
				RPag.setCantidadRegistros(respuesta.getTotalRegistros());
				RPag.setNumeroAnotacionesDefinitivas(respuesta.getNumeroAnotacionesDefinitivas());
				Log.getInstance().debug(AWPaginadorAnotaciones.class, "getNumeroPaginas: " + pag.getNumeroPaginas());
				Log.getInstance().debug(AWPaginadorAnotaciones.class, "getUltimaPaginaGenerada: " + pag.getUltimaPaginaGenerada());
				Log.getInstance().debug(AWPaginadorAnotaciones.class, "getNumPaginasCache: " + pag.getNumPaginasCache());
				Log.getInstance().debug(AWPaginadorAnotaciones.class, "Pagina Actual: " + pag.getIndex());
				try{
					pag.setNuevaPaginaCache(respuesta.getAnotacionesDefinitivas());
					String numPagina= (String)session.getAttribute(AWPaginadorAnotaciones.NUM_PAGINA_ACTUAL);
					int npagina=0;
					if(numPagina!=null){
						npagina=Integer.parseInt(numPagina);
					}
					List ltemp= pag.getPaginaDesdeCache(npagina);
					RPag.setAnotacionesActual(ltemp);
				}catch(PaginadorPaginaNoEncontradaException e){
					//se retorna el evento donde uno va ir por las anotaciones q no estan.
					RPag.setAnotacionesActual(new Vector());
				}catch (PaginadorCacheNoActualizadoException e) {
					e.printStackTrace(System.out);
				}
				request.getSession().setAttribute(respuesta.getNombrePaginador(),pag);
				request.getSession().setAttribute(respuesta.getNombreResultado(),RPag);
			}

              /**
              * @author: Guillermo Cabrera.
              * @change: si es segregación se sube a sesion las anotaciones definitivas.
              * Se remueve de la sesión el campo para identificar si es una segregación
              * ya que en este punto no se utiliza mas.
              * MANTIS: 1726
            **/
            if("TRUE".equals(request.getSession().getAttribute(WebKeys.CAMPO_ES_SEGREGACION))){
                request.getSession().setAttribute(WebKeys.ANOTACIONES_A_HEREDAR, respuesta.getAnotacionesDefinitivas());
            }
            request.getSession().removeAttribute(WebKeys.CAMPO_ES_SEGREGACION);
		}

	}

}

package gov.sir.core.web.acciones.registro;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;


import gov.sir.core.eventos.registro.EvnCopiaAnotacion;
import gov.sir.core.eventos.registro.EvnRespCopiaAnotacion;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CAnotacion;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.CopiaAnotacionException;
import gov.sir.core.negocio.modelo.constantes.CProceso;

 /**
 * @author ppabon
 * Acci�n Web para el manejo de todo lo relacionado a la copia de Anotaci�n entre los folios.
  */
public class AWCopiaAnotacion extends SoporteAccionWeb {

	/**
	* Acci�n solicitada y recibida dentro del request.
	*/
	private String accion;


	/**
	* Acci�n que indica que se desea copiar una anotaci�n a otros folios.
	*/
	public static final String COPIAR_ANOTACION ="COPIAR_ANOTACION";

	/**
	* Acci�n que indica que se desea seleccionar las anotaciones que son objeto de cancelaci�n.
	*/
	public static final String SELECCIONAR_CANCELADA ="SELECCIONAR_CANCELADA";

	/**
	* Acci�n que indica que se desea corregir las anotaciones que son objeto de cancelaci�n.
	*/
	public static final String CORREGIR_CANCELADA ="CORREGIR_CANCELADA";

	/**
	* Acci�n que indica que se desea regresar a la vista originadora.
	*/
	public static final String REGRESAR ="REGRESAR";

	/** Constante que identifica donde comienza el rango de anotaciones a copiar*/
	public static final String DESDE = "DESDE";

	/** Constante que identifica donde termina el rango de anotaciones a copiar*/
	public static final String HASTA = "HASTA";

	/** Constante que identifica la lista de cancelaciones que resultaron de hacer la copia de las anotaciones*/
	public static final String LISTA_COPIA_ANOTACIONES_CANCELADAS = "LISTA_COPIA_ANOTACIONES_CANCELADAS";

	/** Constante que identifica la cantidad inicial de copias de cancelacion que es necesario hacer*/
	public static final String NUM_COPIAS_CANCELACION = "NUM_COPIAS_CANCELACION";

	/**
	 * Crea una instancia de la Acci�n Web de Copia de Anotaciones
	 */
	public AWCopiaAnotacion() {
		super();
	}

	/**
	* M�todo que permite determinar la acci�n solicitada, y hacer el llamado
	* correspondiente, de acuerdo con la solicitud.
	*/
	public Evento perform(HttpServletRequest request)
		throws AccionWebException {

			accion = request.getParameter(WebKeys.ACCION);

			if ((accion == null) || (accion.trim().length() == 0)) {
				throw new AccionInvalidaException("Debe indicar una acci�n v�lida");
			}

			//ESCOGER FOLIOS ENGLOBADOS
			else if (accion.equals(AWCopiaAnotacion.COPIAR_ANOTACION)) {
				return copiarMultiplesAnotaciones(request);
			} else if(accion.equals(AWCopiaAnotacion.SELECCIONAR_CANCELADA)) {
				return seleccionarCancelada(request);
			}else if(accion.equals(AWCopiaAnotacion.CORREGIR_CANCELADA)) {
				return corregirCancelada(request);
			}else if(accion.equals(AWCopiaAnotacion.REGRESAR)) {
                            return doProcess_OpcionCopiaAnotacionPaso1Cancelar( request );
			}


			//ACCI�N INV�LIDA
			else {
				throw new AccionInvalidaException("La acci�n " + accion + " no es v�lida.");
			}
	}


	/**
	 * @param request
	 * @return
	 */
	private Evento removerInfo( HttpServletRequest request ) {

          HttpSession session;
          session = request.getSession();
          session.removeAttribute( CAnotacion.ORDEN_ANOTACION );
          return null;

	}

	private Evento copiarMultiplesAnotaciones(HttpServletRequest request) throws CopiaAnotacionException{
		HttpSession session = request.getSession();
		CopiaAnotacionException exception = new CopiaAnotacionException();
		Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);

		String strDesde = request.getParameter(AWCopiaAnotacion.DESDE);
		String strHasta = request.getParameter(AWCopiaAnotacion.HASTA);
		String idMatricula = request.getParameter(WebKeys.FOLIO_ORIGEN);
		String copiarComentario = request.getParameter(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);

		//Verificacion que la matricula exista en el turno
		boolean existe = false;
		if(idMatricula==null || idMatricula.equals("")){
			exception.addError("Debe ingresar una matr�cula.");
		}else{

			Solicitud solicitud = turno.getSolicitud();
			List solFolios = solicitud.getSolicitudFolios();
			Iterator it = solFolios.iterator();
			while(it.hasNext()){
				SolicitudFolio solFolio = (SolicitudFolio)it.next();
				if(idMatricula.equals(solFolio.getIdMatricula())) {
					existe = true;
				}
			}
			if(!existe){
				exception.addError("El folio de d�nde desea copiar la anotaci�n debe hacer parte de la calificaci�n.");
			}

		}

		if(strDesde == null || strDesde.equals("")){
			exception.addError("Debe ingresar un valor v�lido para el valor\"Desde\"");
		}
		if(strHasta == null || strHasta.equals("")){
			exception.addError("Debe ingresar un valor v�lido para el valor\"Hasta\"");
		}

		int desde=0;
		try{
			desde = Integer.parseInt(strDesde);
		} catch (NumberFormatException e){
			exception.addError("Debe ingresar un n�mero v�lido para el valor \"Desde\"");
		}
		int hasta=0;
		try{
			hasta = Integer.parseInt(strHasta);
		} catch (NumberFormatException e){
			exception.addError("Debe ingresar un n�mero v�lido para el valor \"Hasta\"");
		}

		if(hasta<desde){
			exception.addError("El valor \"Desde\" no puede ser mayor que el valor \"Hasta\"");
		}

		String[] matriculasCopiar = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
		if(matriculasCopiar == null || matriculasCopiar.length == 0){
			exception.addError("Debe seleccionar por lo menos un folio al c�al quiere copiar las anotaciones");
		}

		if(exception.getErrores().size()>0){
			throw exception;
		}


		Solicitud solicitud = turno.getSolicitud();
		List solFolios = solicitud.getSolicitudFolios();

		//SE LLENA LA LISTA DE FOLIOS A D�NDE DESEA COPIARSE LA ANOTACI�N
		List foliosACopiar = new ArrayList();
		if(matriculasCopiar !=null ){
			for (int i = 0; i < matriculasCopiar.length; i++) {
				Iterator it = solFolios.iterator();
				while(it.hasNext()){
					/**
					 * @author David Panesso
					 * @change 1253.CALIFICACION.FOLIOS.CERRADOS
					 **/
					SolicitudFolio solFolio = (SolicitudFolio)it.next();
					if(solFolio.getIdMatricula().equals(matriculasCopiar[i])){
						if (solicitud instanceof SolicitudRegistro) {
							if(solFolio.getFolio().getEstado() != null && solFolio.getFolio().getEstado().getIdEstado().equals(CEstadoFolio.CERRADO)){
								exception.addError("El folio " + solFolio.getFolio().getIdMatricula() + " se encuentra cerrado, se necesita la reapertura por parte del Coordinador Jur�dico para poder ser calificado.");
							}
							else{
								Folio folio = new Folio();
								folio.setIdMatricula(solFolio.getIdMatricula());
								folio.setDefinitivo(solFolio.getFolio().isDefinitivo());
								folio.setNombreLote(solFolio.getFolio().getNombreLote());
								foliosACopiar.add(folio);
							}
							break;
						} else {
							Folio folio = new Folio();
							folio.setIdMatricula(solFolio.getIdMatricula());
							folio.setDefinitivo(solFolio.getFolio().isDefinitivo());
							folio.setNombreLote(solFolio.getFolio().getNombreLote());
							foliosACopiar.add(folio);
						}
						break;
					}
				}
			}
		}
                
                if(exception.getErrores().size()>0){
			throw exception;
		}

		List copiasAnotacion = new ArrayList();
		//for(;desde<=hasta;desde++){


			CopiaAnotacion copiaAnotacion;
                        copiaAnotacion = (CopiaAnotacion)session.getAttribute( WebKeys.COPIA_ANOTACION );
                        if( null == copiaAnotacion ) {
                           copiaAnotacion = new CopiaAnotacion();
                        }

			Anotacion anotacion = new Anotacion();
			//anotacion.setOrden(String.valueOf(desde));

			Folio folio = new Folio();
			folio.setIdMatricula(idMatricula);

			copiaAnotacion.setFoliosACopiar(foliosACopiar);

			copiaAnotacion.setFolioOrigen(folio);
			copiaAnotacion.setAnotacionOrigen(anotacion);

			if(copiarComentario.equals(CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION)){
				copiaAnotacion.setCopiaComentario(true);
			}else{
				copiaAnotacion.setCopiaComentario(false);
			}
			//copiasAnotacion.add(copiaAnotacion);
		//}

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		//return new EvnCopiaAnotacion(usuarioAuriga, EvnCopiaAnotacion.COPIAR_ANOTACION, turno, copiasAnotacion,usuarioSIR);
		return new EvnCopiaAnotacion(usuarioAuriga, EvnCopiaAnotacion.COPIAR_ANOTACION, turno, copiaAnotacion,desde,hasta,usuarioSIR);
	}


	/**
	 * M�todo que copia una anotaci�n a otros folios.
	 * @param request
	 * @return
	 */
	private Evento copiarAnotacion(HttpServletRequest request) throws AccionWebException {

		this.preservarInfo(request);
		HttpSession sesion = request.getSession();
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);

		//SE DEFINE LA EXCEPCI�N DE VALIDACI�N DE PARAMETROS
		CopiaAnotacionException exception = new CopiaAnotacionException();

		//SE OBTIENE EL OBJETO COPIAANOTACION
		CopiaAnotacion copiaAnotacion = (CopiaAnotacion) sesion.getAttribute(WebKeys.COPIA_ANOTACION);
		if (copiaAnotacion == null) {
			copiaAnotacion = new CopiaAnotacion();
		}

		//SE RECIBE LA INFORMACI�N DEL FORMULARIO, SE VALIDA Y SE LANZA LA EXCEPCI�N SI LA INFORMACI�N ES INV�LIDA
		String idMatricula = request.getParameter(WebKeys.FOLIO_ORIGEN);
		String ordenAnotacion = request.getParameter(CAnotacion.ORDEN_ANOTACION);
		String copiarComentario = request.getParameter(CAnotacion.GUARDAR_COMENTARIO_ANOTACION);

		String[] matriculasCopiar = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
		boolean existe = false;

		if(idMatricula==null || idMatricula.equals("")){
			exception.addError("Debe ingresar una matr�cula.");
		}else{

			Solicitud solicitud = turno.getSolicitud();
			List solFolios = solicitud.getSolicitudFolios();
			Iterator it = solFolios.iterator();
			while(it.hasNext()){
				SolicitudFolio solFolio = (SolicitudFolio)it.next();
				if(idMatricula.equals(solFolio.getIdMatricula())) {
					existe = true;
				}
			}
			if(!existe){
				exception.addError("El folio de d�nde desea copiar la anotaci�n debe hacer parte de la calificaci�n.");
			}

		}
		if(ordenAnotacion==null || ordenAnotacion.equals("")){
			exception.addError("Debe ingresar una anotaci�n.");
		}
		if(matriculasCopiar== null || matriculasCopiar.length==0){
			exception.addError("Debe seleccionar los folios donde desea copiar la anotaci�n.");
		}

		if(exception.getErrores().size()>0){
			sesion.setAttribute(WebKeys.FOLIO_ORIGEN,idMatricula);
			sesion.setAttribute(CAnotacion.ORDEN_ANOTACION, ordenAnotacion);
			throw exception;
		}

		removerInfo(request);

		List foliosACopiar = copiaAnotacion.getFoliosACopiar();
		if(foliosACopiar==null){
			foliosACopiar = new ArrayList();
		}


		//SE LLENA LA LISTA DE FOLIOS A D�NDE DESEA COPIARSE LA ANOTACI�N
		//Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Solicitud solicitud = turno.getSolicitud();
		List solFolios = solicitud.getSolicitudFolios();

		Iterator it = null;
		if(matriculasCopiar !=null ){
			foliosACopiar = new ArrayList();
			for (int i = 0; i < matriculasCopiar.length; i++) {

				it = solFolios.iterator();
				while(it.hasNext()){
					SolicitudFolio solFolio = (SolicitudFolio)it.next();
					if(solFolio.getIdMatricula().equals(matriculasCopiar[i])){
						Folio folio = new Folio();
						folio.setIdMatricula(solFolio.getIdMatricula());
						folio.setDefinitivo(solFolio.getFolio().isDefinitivo());
						folio.setNombreLote(solFolio.getFolio().getNombreLote());
						foliosACopiar.add(folio);
						break;
					}
				}
			}
		}

		if(foliosACopiar==null || foliosACopiar.size()==0){
			exception.addError("Debe seleccionar por lo menos un folio a d�nde copiar la anotaci�n.");
			sesion.setAttribute(WebKeys.FOLIO_ORIGEN,idMatricula);
			sesion.setAttribute(CAnotacion.ORDEN_ANOTACION, ordenAnotacion);
			throw exception;
		}

		Anotacion anotacion = new Anotacion();
		anotacion.setOrden(ordenAnotacion);

		Folio folio = new Folio();
		folio.setIdMatricula(idMatricula);

		if(foliosACopiar!=null){
			copiaAnotacion.setFoliosACopiar(foliosACopiar);
		}
		copiaAnotacion.setFolioOrigen(folio);
		copiaAnotacion.setAnotacionOrigen(anotacion);

		if(copiarComentario.equals(CAnotacion.SI_GUARDAR_COMENTARIO_ANOTACION)){
			copiaAnotacion.setCopiaComentario(true);
		}else{
			copiaAnotacion.setCopiaComentario(false);
		}

		sesion.setAttribute(WebKeys.COPIA_ANOTACION, copiaAnotacion);

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) sesion.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) sesion.getAttribute(WebKeys.USUARIO);

		return new EvnCopiaAnotacion(usuarioAuriga, EvnCopiaAnotacion.COPIAR_ANOTACION, turno, copiaAnotacion,usuarioSIR);
	}


	/**
	 * M�todo que copia una anotaci�n canceladora a otros folios.
	 * @param request
	 * @return
	 */
	private Evento seleccionarCancelada(HttpServletRequest request) throws AccionWebException {

		this.preservarInfo(request);
		HttpSession sesion = request.getSession();
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);

		//SE DEFINE LA EXCEPCI�N DE VALIDACI�N DE PARAMETROS
		CopiaAnotacionException exception = new CopiaAnotacionException();

		//SE OBTIENE EL OBJETO COPIAANOTACION
		CopiaAnotacion copiaAnotacion = (CopiaAnotacion) sesion.getAttribute(WebKeys.COPIA_ANOTACION);
		if (copiaAnotacion == null) {
			copiaAnotacion = new CopiaAnotacion();
		}
		List foliosACopiar = copiaAnotacion.getFoliosACopiar();
		List foliosACopiarConAnotacion = new ArrayList();

		Iterator it = null;
		String numMatriculaTemp = null;

		Enumeration parametros = request.getParameterNames();
		while(parametros.hasMoreElements()){
			String key = (String)parametros.nextElement();
			if(key.startsWith(gov.sir.core.negocio.modelo.constantes.CAnotacion.ORDEN_ANOTACION)){

				String ordenAnotacion = request.getParameter(key);
				StringTokenizer st = new StringTokenizer(key, gov.sir.core.negocio.modelo.constantes.CAnotacion.ORDEN_ANOTACION);
				numMatriculaTemp = st.nextToken();

				if(ordenAnotacion==null || ordenAnotacion.equals("")){
					exception.addError("El folio " + numMatriculaTemp + " no tiene una anotaci�n asociada.");
				}else{

					//SE INGRESA EL ORDEN DE LA ANOTACI�N A COPIAR
					it = foliosACopiar.iterator();
					while(it.hasNext()){
						Folio folio = (Folio)it.next();
						if(folio.getIdMatricula().equals(numMatriculaTemp)){
							Anotacion anotacion = new Anotacion();
							anotacion.setOrden(ordenAnotacion);
							this.removerAnotacionesFromFolio(folio);
							folio.addAnotacione(anotacion);
							break;
						}
					}

				}
			}
		}

		if(exception.getErrores().size()>0){
			throw exception;
		}

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) sesion.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) sesion.getAttribute(WebKeys.USUARIO);

		return new EvnCopiaAnotacion(usuarioAuriga, EvnCopiaAnotacion.CANCELADA, turno, copiaAnotacion,usuarioSIR);
	}

	/**
	 * M�todo que permite corregir la anotaci�n que es cancelada cuando se quiere copiar una anotaci�n canceladora.
	 * @param request
	 * @return
	 */
	private Evento corregirCancelada(HttpServletRequest request) throws AccionWebException {

		this.preservarInfo(request);
		HttpSession sesion = request.getSession();
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);

		//SE DEFINE LA EXCEPCI�N DE VALIDACI�N DE PARAMETROS
		CopiaAnotacionException exception = new CopiaAnotacionException();

		//SE OBTIENE EL OBJETO COPIAANOTACION
		CopiaAnotacion copiaAnotacion = (CopiaAnotacion) sesion.getAttribute(WebKeys.COPIA_ANOTACION);
		if (copiaAnotacion == null) {
			copiaAnotacion = new CopiaAnotacion();
		}
		List foliosACopiar = copiaAnotacion.getFoliosACopiar();
		List foliosACopiarConAnotacion = new ArrayList();

		Iterator it = null;
		String numMatriculaTemp = null;

		Enumeration parametros = request.getParameterNames();
		while(parametros.hasMoreElements()){
			String key = (String)parametros.nextElement();
			if(key.startsWith(gov.sir.core.negocio.modelo.constantes.CAnotacion.ORDEN_ANOTACION)){

				String ordenAnotacion = request.getParameter(key);
				StringTokenizer st = new StringTokenizer(key, gov.sir.core.negocio.modelo.constantes.CAnotacion.ORDEN_ANOTACION);
				numMatriculaTemp = st.nextToken();

				if(ordenAnotacion==null || ordenAnotacion.equals("")){
					exception.addError("El folio " + numMatriculaTemp + " no tiene una anotaci�n asociada.");
				}else{

					//SE INGRESA EL ORDEN DE LA ANOTACI�N A COPIAR
					it = foliosACopiar.iterator();
					while(it.hasNext()){
						Folio folio = (Folio)it.next();
						if(folio.getIdMatricula().equals(numMatriculaTemp)){
							Anotacion anotacion = new Anotacion();
							anotacion.setOrden(ordenAnotacion);
							this.removerAnotacionesFromFolio(folio);
							folio.addAnotacione(anotacion);
							break;
						}
					}

				}
			}
		}

		if(exception.getErrores().size()>0){
			throw exception;
		}

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) sesion.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) sesion.getAttribute(WebKeys.USUARIO);

		return new EvnCopiaAnotacion(usuarioAuriga, EvnCopiaAnotacion.CORREGIR_CANCELADA, turno, copiaAnotacion,usuarioSIR);
	}



   private Evento
   doProcess_OpcionCopiaAnotacionPaso1Cancelar( HttpServletRequest request )
   throws AccionWebException {

      Evento local_Result;

      local_Result = removerInfo( request );

      // TODO: paso de correcciones:
      // eliminar cache

      return local_Result;


   } // end-method:doProcess_OpcionCopiaAnotacionPaso1Cancelar


       /**
	 * Remueve las anotaciones del folio.
	 * @param folio
	 */
	private void removerAnotacionesFromFolio(Folio folio)
	{
	   Anotacion anotacion;
	   List anotaciones = folio.getAnotaciones();

	   if (anotaciones!=null)
	   {
		 int max = anotaciones.size();
		 for (int i=0; i<max; i++)
		 {
			 anotacion = (Anotacion)anotaciones.get(0);
			 folio.removeAnotacione(anotacion);
		 }
	   }
	}


	private void preservarInfoBasicaCiudadano(HttpServletRequest request) {
		if (request.getParameter(WebKeys.FOLIO_ORIGEN) != null) {
			request.getSession().setAttribute(WebKeys.FOLIO_ORIGEN, request.getParameter(WebKeys.FOLIO_ORIGEN));
		}
		if (request.getParameter(CAnotacion.ORDEN_ANOTACION) != null) {
			request.getSession().setAttribute(CAnotacion.ORDEN_ANOTACION, request.getParameter(CAnotacion.ORDEN_ANOTACION));
		}
	}

	/**
	 * @param request
	 */
	private void preservarInfo(HttpServletRequest request) {

		for (java.util.Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
			String key = (String) enumeration.nextElement();
			request.getSession().setAttribute(key, request.getParameter(key));
		}

	}


	/**
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request,EventoRespuesta evento) {
              HttpSession session = request.getSession();

		if (evento instanceof EvnRespCopiaAnotacion) {
			EvnRespCopiaAnotacion respuesta = (EvnRespCopiaAnotacion) evento;

			if (respuesta.getTipoEvento().equals(EvnRespCopiaAnotacion.COPIAR_ANOTACION)) {
				String accion = request.getParameter(WebKeys.ACCION);


                                // observar flag para el caso correcciones
                                // si el flag esta fijo,
                                // se debe colocar el proceso "CORRECCIONES" en
                                // los objetoc copia anotacion
                                String local_ProcesoId = CProceso.PROCESO_REGISTRO;
                                CopiaAnotacion local_CopiaAnotacion = null;

                                local_CopiaAnotacion = (CopiaAnotacion)session.getAttribute( WebKeys.COPIA_ANOTACION );



                                if( null !=  local_CopiaAnotacion ) {
                                   if( null != local_CopiaAnotacion.getProceso() && CProceso.PROCESO_CORRECCIONES.equals( local_CopiaAnotacion.getProceso() ) ) {
                                      local_ProcesoId = local_CopiaAnotacion.getProceso();
                                   }
                                } // if






                                // iteration vars
                                CopiaAnotacion copiaAnotacion;
                                Anotacion anotacionOrigen;
				/*CopiaAnotacion copiaAnotacion = (CopiaAnotacion) respuesta.getPayload();
				request.getSession().setAttribute(WebKeys.COPIA_ANOTACION, copiaAnotacion);*/
				if(accion.equals(AWCopiaAnotacion.COPIAR_ANOTACION)){
					Iterator itCopiadas = ((List) respuesta.getPayload()).iterator();
					List canceladas = new ArrayList();
					while(itCopiadas.hasNext()){
						copiaAnotacion = (CopiaAnotacion)itCopiadas.next();
                                                copiaAnotacion.setProceso( local_ProcesoId );
						anotacionOrigen = copiaAnotacion.getAnotacionOrigen();
						if(anotacionOrigen!=null && anotacionOrigen.getTipoAnotacion()!=null&&anotacionOrigen.getTipoAnotacion().getIdTipoAnotacion().equals(CTipoAnotacion.CANCELACION)){
							canceladas.add(copiaAnotacion);
						}
					}
					request.getSession().setAttribute(AWCopiaAnotacion.LISTA_COPIA_ANOTACIONES_CANCELADAS,canceladas);
					request.getSession().setAttribute(AWCopiaAnotacion.NUM_COPIAS_CANCELACION, String.valueOf(canceladas.size()));
				}
			}
		}

	}

};

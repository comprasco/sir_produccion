package gov.sir.core.web.acciones.registro;
             /*
        * @author : CTORRES
        * @change : Se abilita el uso de las clases EvnCiudadano,EvnRespCiudadano
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import gov.sir.core.eventos.registro.EvnTestamentos;
import gov.sir.core.eventos.registro.EvnRespTestamentos;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadano;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.*;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAgregarNotaException;
import gov.sir.core.web.acciones.excepciones.ValidacionRegistroTestamentosException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Acción Web encargada de manejar todo lo relacionado con la inscripción de testamentos.
 * @author ppabon
 */
public class AWTestamentos extends SoporteAccionWeb {

	//SIR 57 R
	
	/** Constante que identifica la acción de incribir el testamento*/
	public static final String REGISTRAR = "REGISTRAR";

	/** Constante que identifica la acción de devolver el  testamento*/
	public static final String DEVOLVER = "DEVOLVER";

	/** Constante que permite colocar la descripción del tipo de nota*/
	public static final String COLOCAR_DESCRIPCION = "COLOCAR_DESCRIPCION";
	
	/** Modifica Pablo Quintana Junio 16 2008 
	 * Constante que permite devolver el testamento a correccion encabezado*/
	public static final String CORRECCION_ENCABEZADO="CORRECCION_ENCABEZADO";

	/** Constante que identifica la ccion de agregar un testador */
	public static final String AGREGAR_TESTADOR="AGREGAR_TESTADOR";
	/**Constante que identifica la accion eliminar testador*/
	public static final String ELIMINAR_TESTADOR="ELIMINAR_TESTADOR";
	/**Constante que identifica la accion devolver testamento a confrontacion**/
	public static final String DEVOLVER_A_CONFRONTACION="DEVOLVER_A_CONFRONTACION";
        
          /*
                    *  @author Carlos Torres
                    *  @chage  nueva constante
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
        /**Constante que identifica la accion devolver testamento a confrontacion**/
        public static final String REFRESCAR_EDICION ="REFRESCAR_EDICION"; 
        
        public static final String VALIDAR_CIUDADANO = "VALIDAR_CIUDADANO";
        public static final String CONSULTAR_TESTADOR_OK = "CONSULTAR_TESTADOR_OK"; 
	
	//RESPUESTAS WORKFLOW
	public static final String WF_CONFIRMAR = "CONFIRMAR";
	public static final String WF_NEGAR = "NEGAR";

	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		String accion = request.getParameter(WebKeys.ACCION).trim();
		HttpSession session = request.getSession();

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}
		if (accion.equals(AWTestamentos.REGISTRAR)) {
			return registrar(request);
		} else if (accion.equals(AWTestamentos.DEVOLVER)) {
			return devolver(request);
		}else if (accion.equals(AWTestamentos.COLOCAR_DESCRIPCION)) {
			return buscarDescripcion(request);
		}
		/** Modifica Pablo Quintana Junio 16 2008
		 *  Se envía el turno a corrección de encabezado*/
		else if(accion.equals(AWTestamentos.CORRECCION_ENCABEZADO)){
			return delegarCorreccionEncabezado(request); 
		}else if(accion.equals(AWTestamentos.AGREGAR_TESTADOR)){
			return agregaTestador(request);
		}else if(accion.equals(ELIMINAR_TESTADOR)){
			return eliminaTestador(request);
		}else if(accion.equals(DEVOLVER_A_CONFRONTACION)){
			return devolverAConfrontacion(request);
		}
                  /*
                    *  @author Carlos Torres
                    *  @chage  condicion para la accion refrescar edicion
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                    */
                else if(accion.equals(REFRESCAR_EDICION)){
			return refrescarEdicion(request);
		}
                 /*
                    *  @author Carlos Torres
                    *  @chage  condicion para la accion validar ciudadano
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
                    */
                else if(accion.equals(VALIDAR_CIUDADANO)){
			return validarCiudadano(request);
		}
		return null;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento registrar un testamento
	 * @param request
	 * @return evento <code>EvnTestamentos</code> con la información del testamento.
	 * @throws AccionWebException
	 */
	private EvnTestamentos registrar(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		preservarInfoFormulario(request);//PQ
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);

		ValidacionRegistroTestamentosException exception = new ValidacionRegistroTestamentosException();

		String tomo = request.getParameter(CTestamentos.TOMO);
		String numAnotaciones = request.getParameter(CTestamentos.NUMERO_ANOTACIONES);
		String numCopias = request.getParameter(CTestamentos.NUMERO_COPIAS);
		String revocaEscritura = request.getParameter(CTestamentos.REVOCA_ESCRITURA);
		String observacion = request.getParameter(CTestamentos.OBSERVACION);

		session.setAttribute(CTestamentos.TOMO, tomo);
		session.setAttribute(CTestamentos.NUMERO_ANOTACIONES, numAnotaciones);
		session.setAttribute(CTestamentos.NUMERO_COPIAS, numCopias);
		session.setAttribute(CTestamentos.REVOCA_ESCRITURA, revocaEscritura);
		session.setAttribute(CTestamentos.OBSERVACION, observacion);

		String tipoId = request.getParameter(CCiudadano.TIPODOC);
		String numId = request.getParameter(CCiudadano.IDCIUDADANO);
		String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
		String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
		String nombres = request.getParameter(CCiudadano.NOMBRE);
		String telefono = request.getParameter(CCiudadano.TELEFONO);

		session.setAttribute(CCiudadano.TIPODOC, tipoId);
		session.setAttribute(CCiudadano.IDCIUDADANO, numId);
		session.setAttribute(CCiudadano.APELLIDO1, apellido1);
		session.setAttribute(CCiudadano.APELLIDO2, apellido2);
		session.setAttribute(CCiudadano.NOMBRE, nombres);
		session.setAttribute(CCiudadano.TELEFONO, telefono);

		/**
		   Modifica Pablo Quintana Junio 16 2008
		 * Se pone en session el nuevo atributo de copias a imprimir. Modifica Pablo Quintana Junio 12 2008
		 */
		String copiasImprimir=request.getParameter(CTestamentos.COPIAS_IMPRIMIR);
		session.setAttribute(CTestamentos.COPIAS_IMPRIMIR,copiasImprimir);
		
		//VALIDACIONES PARA EL TESTAMENTO
		if (tomo == null || tomo.trim().equals("")) {
			exception.addError("Debe ingresar el tomo.");
		}
		if (numAnotaciones == null || numAnotaciones.trim().equals("")) {
			exception.addError("Debe ingresar el número de anotaciones.");
		}
		if (numCopias == null || numCopias.trim().equals("")) {
			exception.addError("Debe ingresar el número de copias.");
		}
		if (revocaEscritura == null || revocaEscritura.trim().equals("")) {
			exception.addError("Debe determinar si revoca escritura.");
		}
		if (observacion == null || observacion.trim().equals("")) {
			exception.addError("Debe ingresar la observación.");
		} else if (observacion.length() >= 32765 ) {
			exception.addError("La observacion contiene " + observacion.length() + " caracteres. (Longitud máxima 32.765)");	
		}
		if(copiasImprimir==null || copiasImprimir.trim().equals("")){
			exception.addError("debe ingresar el número de copias a imprimir");
		}else if(Long.parseLong(copiasImprimir)<=0){
			exception.addError("El número de copias a imprimir debe ser igual o superior a 1");
		}
		
		//VALIDACIONES PARA EL TESTADOR (CIUDADANO)
		/* a 231 modifica Pablo Quintana SIR-62
		 * if (tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe seleccionar un tipo de identificación para el ciudadano");
		} else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} else if (tipoId.equals(COPLookupCodes.NIT)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} else {
			double valorId = 0.0d;
			if (numId == null || numId.trim().equals("")) {
				exception.addError("Debe ingresar el número de identificacion del Ciudadano");
			} else {
				if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
					try {
						valorId = Double.parseDouble(numId);
						if (valorId <= 0) {
							exception.addError("El valor del documento no puede ser negativo o cero");
						}
					} catch (NumberFormatException e) {
						exception.addError("El número de identificación de la persona es inválido. No puede ser alfanumérico");
					}
				}
			}
			if (nombres == null || nombres.trim().equals("")) {
				exception.addError("Debe ingresar el nombre del Ciudadano");
			}
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		//SE INGRESA LA INFORMACIÓN DEL CAUSANTE
		Ciudadano ciudadano = new Ciudadano();
		ciudadano.setTipoDoc(tipoId);
		ciudadano.setDocumento(numId);
		ciudadano.setApellido1(apellido1);
		ciudadano.setApellido2(apellido2);
		ciudadano.setNombre(nombres);
		if (telefono != null) {
			ciudadano.setTelefono(telefono);
		}
		ciudadano.setIdCiudadano(null);
		ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);
		*/

		//SE INGRESA LA INFORMACIÓN DEL TESTAMENTO.
		//Testamento testamentoSession=(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
		//Testamento testamento = new Testamento();
		Testamento testamento = (Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
		if(testamento==null)
			testamento=new Testamento();
		testamento.setTomo(tomo);
		testamento.setNumeroAnotaciones(numAnotaciones);
		testamento.setNumeroCopias(numCopias);
		testamento.setRevocaEscritura(revocaEscritura.toUpperCase());
		testamento.setObservacion(observacion);
                     /*
        * @author : CTORRES
        * @change : Se agregan las sentencias para estableser el valor del atributo usuarioCreacion
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                testamento.setUsuarioCreacion(usuarioSIR);
		//if(testamentoSession!=null)
			//testamento.setTestadores(testamentoSession.getTestadores());
		if(testamento.getTestadores()==null || testamento.getTestadores().size()==0)
			exception.addError("Debe ingresar un testador");
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		
		EvnTestamentos evento = new EvnTestamentos(usuarioAuriga, EvnTestamentos.REGISTRAR, turno, fase, circulo, AWTestamentos.WF_CONFIRMAR, testamento, usuarioSIR);
		evento.setCopiasImprimir(Integer.parseInt(copiasImprimir));
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para devolver un turno de testamentos.
	 * @param request
	 * @return evento <code>EvnTestamentos</code>.
	 * @throws AccionWebException
	 */
	private EvnTestamentos devolver(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);

		ValidacionRegistroTestamentosException exception = new ValidacionRegistroTestamentosException();
		List notasImpresion = new Vector();		

		if (turno == null) {
			exception.addError("El turno que se encontro en la sesion es invalido");
		}
		if (circulo == null) {
			exception.addError("El circulo que se encontro en la sesion es invalido");
		}
		if (proceso == null) {
			exception.addError("El proceso que se encontro en la sesion es invalido");
		}
		if (usuarioSIR == null) {
			exception.addError("El usuario que se encontro en la sesion es invalido");
		}

		String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
		if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe escoger un tipo de nota valido");
		}
		String descripcion = request.getParameter(CNota.DESCRIPCION);
		if(descripcion!=null){
			descripcion = descripcion.toUpperCase();	
		}
		String descripcionTipo = request.getParameter((CTipoNota.DESCRIPCION));
		
		
		String valCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
		int copias = 1;
		 
		if(valCopias != null){
			try{
				copias = Integer.valueOf(valCopias).intValue();
				if(copias < 1){
					exception.addError("El número de copias es inválido");	
				}
			}catch(Exception e){
				exception.addError("El número de copias es inválido");
			}
		}



		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		TipoNota tipoNota = new TipoNota();
		tipoNota.setDescripcion(descripcionTipo);

		tipoNota.setIdTipoNota(idTipoNota);
                
                /**
                * @Autor: elora
                * @Mantis: 0012621
                * @Requerimiento: 055_453
                */
                DatosNotas datosNotas = DatosNotas.getInstance();
                tipoNota.setVersion(datosNotas.obtenerVersionTipoNota(idTipoNota));

		List tiposNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS);
		Iterator itTiposNotas = tiposNotas.iterator();
		String nombre = "";
		boolean devolutiva  = false;
		while (itTiposNotas.hasNext()) {
			TipoNota elemento = (TipoNota) itTiposNotas.next();
			if (elemento.getIdTipoNota().equals(idTipoNota)) {
				nombre = elemento.getNombre();
				devolutiva = elemento.isDevolutiva();
			}
		}

		tipoNota.setNombre(nombre);
		tipoNota.setDevolutiva(devolutiva);

		Nota nota = new Nota();

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		nota.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
		nota.setDescripcion(descripcion);
		nota.setIdCirculo(circulo.getIdCirculo());
		nota.setIdProceso(proceso.getIdProceso());
		nota.setIdTurno(turno.getIdTurno());
		nota.setTime(calendar.getTime());
		nota.setTiponota(tipoNota);
                /**
                 * @Autor: Elora
                 * @Mantis: 0012621
                 * @Requerimiento: 055_453
                 */
                nota.setIdFase("");
		nota.setTurno(turno);
		nota.setUsuario(usuarioSIR);

		turno.addNota(nota);
		session.setAttribute(WebKeys.TURNO, turno);

		String numNotas = (String) session.getAttribute(WebKeys.NUM_NOTAS_FASE);

		if (numNotas == null) {
			numNotas = new String();
			numNotas = "0";
		}
		int i = Integer.valueOf(numNotas).intValue();
		i++;
		numNotas = String.valueOf(i);
		session.setAttribute(WebKeys.NUM_NOTAS_FASE, numNotas);

		
		notasImpresion.add(nota);
		
		EvnTestamentos evento = new EvnTestamentos(usuarioAuriga, EvnTestamentos.DEVOLVER, turno, fase, circulo, AWTestamentos.WF_NEGAR, usuarioSIR);
		evento.setNota(nota);
		evento.setNotas(notasImpresion);
		evento.setNumeroCopias(copias);
		
		return evento;
	}
	
	/**
	 * @param request
	 */
	private Evento buscarDescripcion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
		String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
		if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe escoger un tipo de nota valido");
		}

		Iterator itTiposNotas = ((List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS)).iterator();
		while (itTiposNotas.hasNext()) {
			TipoNota elemento = (TipoNota) itTiposNotas.next();
			if (elemento.getIdTipoNota().equals(idTipoNota)) {
				session.setAttribute(CTipoNota.DESCRIPCION, elemento.getDescripcion());
				session.setAttribute(CTipoNota.ID_TIPO_NOTA, idTipoNota);
				session.setAttribute(CNota.DESCRIPCION, request.getParameter(CNota.DESCRIPCION));
				session.setAttribute(CNota.NOMBRE, elemento.getNombre());
			}
		}

		return null;
	}	
	

	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente 
	 * de la acción de negocio. 
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespTestamentos respuesta = null; /*(gov.sir.core.eventos.registro.EvnRespTestamentos) evento;*/
                              /*
        * @author : CTORRES
        * @change : condicion para uso de busqueda de ciudadano
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                EvnRespCiudadano respCiudadano = null;
                if(evento instanceof EvnRespTestamentos)
                {
                    respuesta = (gov.sir.core.eventos.registro.EvnRespTestamentos) evento;
                }else if(evento instanceof EvnRespCiudadano)
                {
                    respCiudadano = (gov.sir.core.eventos.comun.EvnRespCiudadano) evento;
                }

		HttpSession session = request.getSession();
		context = session.getServletContext();

		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();
			if (tipoEvento.equals(EvnRespTestamentos.REGISTRAR) || tipoEvento.equals(EvnRespTestamentos.DEVOLVER)) {
				session.setAttribute(WebKeys.TURNO, respuesta.getPayload());
				
				if(respuesta.getIdImprimible()!=0){
					request.getSession().setAttribute(WebKeys.ID_IMPRIMIBLE, new Integer(respuesta.getIdImprimible()));	
				}
				
				return;
			}
		}
                     /*
        * @author : CTORRES
        * @change : condicion para uso de busqueda de ciudadano
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                else if (respCiudadano!=null && respCiudadano.getTipoEvento().equals(CONSULTAR_TESTADOR_OK) ){
			Ciudadano ciudadano = respCiudadano.getCiudadano();
			session.setAttribute(CCiudadano.TIPODOC,ciudadano.getTipoDoc());
                        session.setAttribute("ID_CIUDADANO", ciudadano.getIdCiudadano());
			session.setAttribute(CCiudadano.IDCIUDADANO,ciudadano.getDocumento());
			session.setAttribute(CCiudadano.NOMBRE,ciudadano.getNombre());
			session.setAttribute(CCiudadano.APELLIDO1,ciudadano.getApellido1());
			session.setAttribute(CCiudadano.APELLIDO2,ciudadano.getApellido2());
			
		}
	}
	
	/**
	 * Envía turno a corrección de encabezado.
	 * @param request
	 * @return
	 */
	private Evento delegarCorreccionEncabezado (HttpServletRequest request) throws AccionWebException{
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
		(org.auriga.core.modelo.transferObjects.Usuario)
		request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		String motivoDevolucion = EvnTestamentos.ERROR_ENCABEZADO;//request.getParameter(AWCalificacion.NOTA_INFORMATIVA_CONFRONTACION);
		
		HttpSession session = request.getSession();
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		/*if (motivoDevolucion == null || motivoDevolucion.length()==0)
		{
			throw new ParametroInvalidoException("Es necesario ingresar el motivo de la delegación del turno a correccion encabezado");
		}*/
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
		Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		String accion1 = EvnTestamentos.CORRECCION;
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		return new EvnTestamentos (usuarioAuriga, turno, fase, userNeg, EvnTestamentos.ERROR_ENCABEZADO, accion1, circulo.getIdCirculo(),motivoDevolucion,usuarioSIR);
	}
	
	/**
	 * Preserva la información del formulario en el objeto testamento de la solicitud
	 * Pablo Quintana Junio 20 2008.
	 * @param request
	 */
	private void preservarInfoFormulario(HttpServletRequest request){
		HttpSession session = request.getSession();
		Testamento testamento=(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
		if(testamento!=null){
			if(request.getParameter(CTestamentos.TOMO)!=null)
				testamento.setTomo(request.getParameter(CTestamentos.TOMO));
			if(request.getParameter(CTestamentos.NUMERO_ANOTACIONES)!=null)
				testamento.setNumeroAnotaciones(request.getParameter(CTestamentos.NUMERO_ANOTACIONES));
			if(request.getParameter(CTestamentos.NUMERO_COPIAS)!=null)
				testamento.setNumeroCopias(request.getParameter(CTestamentos.NUMERO_COPIAS));
			if(request.getParameter(CTestamentos.REVOCA_ESCRITURA)!=null)
				testamento.setRevocaEscritura(request.getParameter(CTestamentos.REVOCA_ESCRITURA));
			if(request.getParameter(CTestamentos.OBSERVACION)!=null)
				testamento.setObservacion(request.getParameter(CTestamentos.OBSERVACION));
			if(request.getParameter(CTestamentos.COPIAS_IMPRIMIR)!=null)
				request.setAttribute(CTestamentos.COPIAS_IMPRIMIR,request.getParameter(CTestamentos.COPIAS_IMPRIMIR));
			session.setAttribute(WebKeys.TESTAMENTO_SESION,testamento);
		}
	}
	
	/**
	 * Agrega testador
	 * @param request
	 * @return
	 */
	private Evento agregaTestador (HttpServletRequest request) throws AccionWebException{
		preservarInfoFormulario(request);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
		(org.auriga.core.modelo.transferObjects.Usuario)
		request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		String tipoId = request.getParameter(CCiudadano.TIPODOC);
		String numId = request.getParameter(CCiudadano.IDCIUDADANO);
		String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
		String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
		String nombres = request.getParameter(CCiudadano.NOMBRE);
		String telefono = request.getParameter(CCiudadano.TELEFONO);
                              /*
        * @author : CTORRES
        * @change : Nueva variable idCiudadano
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                //String idCiudadano = request.getParameter("ID_CIUDADANO");
		ValidacionRegistroTestamentosException exception = new ValidacionRegistroTestamentosException();
		if (tipoId == null || tipoId.length()==0 || tipoId.equals("SIN_SELECCIONAR")){
			exception.addError("Debe seleccionar tipo de identificación del testador");
		}else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} else if (tipoId.equals(COPLookupCodes.NIT)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
			if (numId == null || numId.trim().equals(""))
				exception.addError("Debe ingresar el número de identificacion del Ciudadano");
		} else {
			double valorId = 0.0d;
			if (numId == null || numId.trim().equals("")) {
				exception.addError("Debe ingresar el número de identificacion del Ciudadano");
			} else {
				if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
            /*
             * @author : CTORRES
             * @change : Se implemento la validación para solo permitir caracteres alfanuméricos en 
             *           numId cuando el tipo de identificación es PASAPORTE.
             * Caso Mantis : 11056
             * No Requerimiento: 073_151
             */
                                    if(tipoId.contains("PS"))
                                    {
                                        String regexSL = "^[a-zA-Z]+$";
                                        String regexSN = "^[0-9]+$";
                                        String regexLN = "^[a-zA0-Z9]+$";
                                        java.util.regex.Pattern patternSL = java.util.regex.Pattern.compile(regexSL);
                                        java.util.regex.Pattern patternSN = java.util.regex.Pattern.compile(regexSN);
                                        java.util.regex.Pattern patternLN = java.util.regex.Pattern.compile(regexLN);
                                        boolean esC = false;
                                        if(patternSL.matcher(numId).matches()) esC = true;
                                        else if(patternSN.matcher(numId).matches()) esC = true;
                                        else if(patternLN.matcher(numId).matches()) esC = true;
                                        else exception.addError("El número de identificación de la persona es inválido. Debe ser alfanumérico");
                                        
                                    }
                                    else{
                                    try {
						valorId = Double.parseDouble(numId);
						if (valorId <= 0) {
							exception.addError("El valor del documento no puede ser negativo o cero");
						}
					} catch (NumberFormatException e) {
						exception.addError("El número de identificación de la persona es inválido. No puede ser alfanumérico");
					}
                                    }
				}
			}
			if (nombres == null || nombres.trim().equals("")) {
				exception.addError("Debe ingresar el nombre del Ciudadano");
			}
			if (apellido1 == null || apellido1.trim().equals("")) {
				throw new ParametroInvalidoException("Debe ingresar el primer apellido del Ciudadano");
			}
		}
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		Ciudadano ciudadano = new Ciudadano();
		ciudadano.setTipoDoc(tipoId);
		ciudadano.setDocumento(numId);
		ciudadano.setApellido1(apellido1);
		ciudadano.setApellido2(apellido2);
		ciudadano.setNombre(nombres);
		if (telefono != null) {
			ciudadano.setTelefono(telefono);
		}
		ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);
		TestamentoCiudadano testamentoCiudadano=new TestamentoCiudadano();
		HttpSession session = request.getSession();
		Testamento testamento=(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
		if(testamento==null)
			testamento=new Testamento();
		testamentoCiudadano.setIdTestamento(testamento.getIdTestamento());
		testamentoCiudadano.setCiudadano(ciudadano);
		testamento.getTestadores().add(testamentoCiudadano);
		session.setAttribute(WebKeys.TESTAMENTO_SESION, testamento);
       /*
        * @author : CTORRES
        * @change : se borran los datos del formulario de testadores.
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                borrarFormTestador(request);
		return new EvnTestamentos (usuarioAuriga,EvnTestamentos.AGREGAR_TESTADOR);
	}
	
	private Evento eliminaTestador (HttpServletRequest request) throws AccionWebException{
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
		(org.auriga.core.modelo.transferObjects.Usuario)
		request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnTestamentos evnTestamento=new EvnTestamentos (usuarioAuriga,EvnTestamentos.ELIMINAR_TESTADOR);
		HttpSession session = request.getSession();
		Testamento testamento=(Testamento)session.getAttribute(WebKeys.TESTAMENTO_SESION);
		int indexToEliminarTestador=Integer.parseInt(request.getParameter(WebKeys.POSICION));
		TestamentoCiudadano testamentoCiudadano=(TestamentoCiudadano) testamento.getTestadores().get(indexToEliminarTestador);
		if(testamentoCiudadano!=null && testamentoCiudadano.getCiudadano()!=null && testamentoCiudadano.getCiudadano().getIdCiudadano()!=null){
			evnTestamento.setTestamentoCiudadano(testamentoCiudadano);
		}else{
			evnTestamento.setTestamentoCiudadano(null);
		}
		evnTestamento.setTestamento(testamento);
		testamento.getTestadores().remove(indexToEliminarTestador);
		return evnTestamento;
	}
	
	/**
	 * Envía turno a corrección de encabezado.
	 * @param request
	 * @return
	 */
	private Evento devolverAConfrontacion (HttpServletRequest request) throws AccionWebException{
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
		(org.auriga.core.modelo.transferObjects.Usuario)
		request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		HttpSession session = request.getSession();
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
		Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		String accion1 = EvnTestamentos.DEVOLVER_A_CONFRONTACION;
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		return new EvnTestamentos (usuarioAuriga, turno, fase, userNeg, EvnTestamentos.DEVOLVER_A_CONFRONTACION, accion1, circulo.getIdCirculo(),"",usuarioSIR);
	}
 
                              /*
        * @author : CTORRES
        * @change : Nuevo metodo refrescarEdicion
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
        /**
         * @param request
         * @return
         */
        private Evento refrescarEdicion(HttpServletRequest request) {
                //eliminarInfoBasicaAnotacion(request);
                String ver = request.getParameter("SECUENCIA");
                request.getSession().setAttribute("SECUENCIA", ver);
                request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));
                String tipoId = request.getParameter(CCiudadano.TIPODOC);
                request.getSession().setAttribute(CCiudadano.TIPODOC, tipoId);
                preservarInfoFormulario(request);
                return null;
        }
                      /*
        * @author : CTORRES
        * @change : Nuevo metodo validarCiudadano
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
    private Evento validarCiudadano(HttpServletRequest request) throws ValidacionRegistroTestamentosException {
        //eliminarInfoBasicaAnotacion(request);

                Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
                org.auriga.core.modelo.transferObjects.Usuario usuarioArq= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
                ValidacionRegistroTestamentosException exception = new ValidacionRegistroTestamentosException();
                String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);
                preservarInfoFormulario(request);

                String tipoDoc = request.getParameter(CCiudadano.TIPODOC);

                if ((tipoDoc == null) || tipoDoc.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError("Debe escoger un tipo de identificación para la persona en la anotacion");
                }

                String numDoc = request.getParameter(CCiudadano.IDCIUDADANO);
                if ((numDoc == null) || numDoc.equals("")) {
                        exception.addError("Debe digitar un número de identificación");
                }

                if(!exception.getErrores().isEmpty()){
                        preservarInfoFormulario(request);
                        throw exception;
                }

                request.getSession().setAttribute(CCiudadano.IDCIUDADANO, numDoc);

                Ciudadano ciud = new Ciudadano();
                ciud.setDocumento(numDoc);
                ciud.setTipoDoc(tipoDoc);

           		//Se setea el circulo del ciudadano
        		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
        		ciud.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);

        		EvnCiudadano evnt = new EvnCiudadano(usuarioArq, EvnCiudadano.CONSULTAR_TESTADOR, ciud);
        		evnt.setCorrecciones(true);
        		evnt.setTurno((Turno)request.getSession().getAttribute(WebKeys.TURNO));
                return evnt;
    }
       /*
        * @author : CTORRES
        * @change : Se crea nuevo metodo borrarFormTestador
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
    public void borrarFormTestador(HttpServletRequest request)
    {
        javax.servlet.http.HttpSession session = request.getSession();
        session.setAttribute(CCiudadano.TIPODOC,"");
        session.setAttribute("ID_CIUDADANO", "");
        session.setAttribute(CCiudadano.IDCIUDADANO,"");
        session.setAttribute(CCiudadano.NOMBRE,"");
        session.setAttribute(CCiudadano.APELLIDO1,"");
        session.setAttribute(CCiudadano.APELLIDO2,"");
    }
}

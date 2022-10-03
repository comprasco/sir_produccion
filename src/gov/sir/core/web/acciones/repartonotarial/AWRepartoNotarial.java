package gov.sir.core.web.acciones.repartonotarial;

import gov.sir.core.eventos.comun.EvnNotas;
import java.util.List;
import java.util.Map;

import gov.sir.core.eventos.repartonotarial.EvnRepartoNotarial;
import gov.sir.core.eventos.repartonotarial.EvnRespRepartoNotarial;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CMinuta;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAgregarNotaException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con el
 * reparto notarial de minutas. Se encarga de realizar las validaciones primarias a nivel web y
 * por medio de eventos hace llamados a la capa de negocio para que su vez llame los servicios 
 * que se requieren.
 * @author ppabon
 */
public class AWRepartoNotarial extends SoporteAccionWeb {

	/**
	 * Constante que identifica que se desea ejecutar el reparto notarial de minutas
	 */
	public static final String EJECUTAR_REPARTO = "EJECUTAR_REPARTO";

	/**
	 * Constante que identifica que la notificación al ciudadano ha sido exitosa
	 */
	public final static String NOTIFICAR_CIUDADANO_EXITO = "NOTIFICAR_CIUDADANO_EXITO";

	/**
	 * Constante que identifica que la notificación al ciudadano ha fracasado
	 */
	public final static String NOTIFICAR_CIUDADANO_FRACASO = "NOTIFICAR_CIUDADANO_FRACASO";

	/**
	 * Constante que se desea avanzar en el workflow por la acción EXITO
	 */
	public final static String EXITO = "EXITO";
	
	
	/**
	* Constante que indica que se quieren consultar los turnos de restitución asociados con
	* una minuta.
	*/
	public final static String CARGAR_TURNOS_RESTITUCION_MINUTA = "CARGAR_TURNOS_RESTITUCION_MINUTA";
	
	
	/**
	* Constante para nombrar al identificador de la minuta.
	*/
	public final static String IDENTIFICADOR_MINUTA = "IDENTIFICADOR_MINUTA";
	
	
	/**
	* Constante para identificar el Círculo Registral en el que se radicó una minuta.
	*/
	public final static String CIRCULO_MINUTA = "CIRCULO_MINUTA";
	

    /**
     * Constante que identifica la lista en la que se almacenan los turnos de restitución
     * de reparto asociados con una minuta
     */
    public final static String LISTADO_TURNOS_RESTITUCION_MINUTA = "LISTADO_TURNOS_RESTITUCION_MINUTA";
    
    /**
	 * Constante que identifica que la notificación al ciudadano ha sido exitosa
	 */
	public final static String NOTIFICAR_CIUDADANO_EXITO_MASIVA = "NOTIFICAR_CIUDADANO_EXITO_MASIVA";

	/**
	 * Constante que identifica que la notificación al ciudadano ha fracasado
	 */
	public final static String NOTIFICAR_CIUDADANO_FRACASO_MASIVA = "NOTIFICAR_CIUDADANO_FRACASO_MASIVA";
        /**
         * @author: Diana Lora
         * @change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
         * En caso que se realice un reparto notarial extraordinario, se debe agregar una
         * nota informativa para dicho reparto.
         */
        public final static String AGREGAR_NOTA_INFORMATIVA = "AGREGAR_NOTA_INFORMATIVA";

        public final static String COLOCAR_DESCRIPCION = "COLOCAR_DESCRIPCION";

        public static final String CANCELAR_REPARTO_NOTARIAL = "CANCELAR_REPARTO_NOTARIAL";

        public static final String AGREGAR_NOTA = "AGREGAR_NOTA";

        public static final String IMPRIMIR_NOTA_INFORMATIVA = "IMPRIMIR_NOTA_INFORMATIVA";
	/**
	 * Variable donde se guarda la accion enviada en el request
	 */
	private String accion;

	/**
	 * Constructor de la clase AWRepartoNotarial
	 */
	public AWRepartoNotarial() {
		super();
	}

	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		accion = request.getParameter(WebKeys.ACCION);

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}

		HttpSession session = request.getSession();
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);

		if (accion.equals(EJECUTAR_REPARTO)) {
			return crearReparto(request);
		} else if (accion.equals(NOTIFICAR_CIUDADANO_EXITO)) {
			return notificarCiudadanoExito(request);
		} else if (accion.equals(NOTIFICAR_CIUDADANO_EXITO_MASIVA)) {
			return notificarCiudadanoExitoMasiva(request);
		} else if (accion.equals(NOTIFICAR_CIUDADANO_FRACASO)) {
			return notificarCiudadanoFracaso(request);
		} else if (accion.equals(COLOCAR_DESCRIPCION)) {
			return buscarDescripcion(request);
                } else if (accion.equals(AGREGAR_NOTA_INFORMATIVA)) {
                        return guardarTurnosARepartir(request);
                } else if (accion.equals(CANCELAR_REPARTO_NOTARIAL)) {
                        return limpiarSesionRepartoExtraordinario(request);
                } else if (accion.equals(AGREGAR_NOTA)) {
                        return agregarNotaInformativa(request);
                } else if (accion.equals(IMPRIMIR_NOTA_INFORMATIVA)) {
                    try {
                        return imprimirNotaInformativa(request);
                    } catch (ValidacionParametrosException ex) {
                        throw new AccionWebException(ex.getMessage());
                    }
		}
		//Cargar listado de Turnos de Restitución asociados con una minuta.
		if (accion.equals(AWRepartoNotarial.CARGAR_TURNOS_RESTITUCION_MINUTA))
		{
			session.removeAttribute(AWRepartoNotarial.LISTADO_TURNOS_RESTITUCION_MINUTA);
			return cargarTurnosRestitucionMinuta (request);
		}
		
		else {
			throw new AccionInvalidaException("La accion " + accion + " no es valida.");
		}
	}

        /**
         * Método carga la descripción de la nota informativa para reparto notarial extraordinario.
         * @author: Diana Lora
         * @change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
         * @param request
         * @return
         */
        private Evento buscarDescripcion(HttpServletRequest request) {
		ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
		String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
		if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe escoger un tipo de nota valido");
		}

                HttpSession session = request.getSession();
                
		Iterator itTiposNotas = ((List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS)).iterator();
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
         * Agrega notas informativas para el reparto notarial extraordinario.
         * @author: Diana Lora
         * @change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
         * @param request
         * @return
         * @throws AccionWebException
         */
        private Evento agregarNotaInformativa(HttpServletRequest request) throws AccionWebException {
                HttpSession session = request.getSession();

		ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		//CASO ESPECIAL NO EXISTE TURNO.
		if (turno == null) {
			//1. Se valida que se haya seleccionado un tipo de nota informativa.
			String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
			if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
				exception.addError("Debe escoger un tipo de nota válido");
			}

			if (exception.getErrores().size() > 0) {
				throw exception;
			}

			//2. Se crea la nota.
			Nota notaInformativa = new Nota();
			Calendar calendar = Calendar.getInstance();

			notaInformativa.setIdNota(idTipoNota);
			notaInformativa.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
			notaInformativa.setIdCirculo(circulo.getIdCirculo());
			notaInformativa.setIdProceso(proceso.getIdProceso());
			notaInformativa.setIdTurno(null);
			notaInformativa.setTime(calendar.getTime());
			notaInformativa.setUsuario(usuario);

			String descripcion = request.getParameter(CNota.DESCRIPCION);
			if(descripcion!=null){
				descripcion = descripcion.toUpperCase();
			}
			notaInformativa.setDescripcion(descripcion);
			String descripcionTipo = request.getParameter((CTipoNota.DESCRIPCION));
			String nombreTipo = (String) request.getSession().getAttribute(CNota.NOMBRE);

			TipoNota tipoNota = new TipoNota();
			tipoNota.setDescripcion(descripcionTipo);
			tipoNota.setIdTipoNota(idTipoNota);
			tipoNota.setNombre(nombreTipo);
			notaInformativa.setTiponota(tipoNota);

			//3. Se agrega la nota a la lista de notas.
			List listaNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);

			//3.1 Si la lista es vacía se crea y se agrega la nota
			if (listaNotas == null) {
				listaNotas = new ArrayList();
				listaNotas.add(notaInformativa);

			}
			//3.2 Si la lista ya tiene elementos, se agregan
			else {
				listaNotas.add(notaInformativa);
			}

			//4. Se sube la lista a la sesión
			session.setAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO, listaNotas);

			//5. Se retorna null para que no se genere ningún evento y se continue
			//en la misma jsp.
			return null;
		}

		//CASO EXISTE TURNO
		else {

			if (turno == null) {
				exception.addError("El turno que se encontro en la sesion es invalido");
			}
			if (circulo == null) {
				exception.addError("El circulo que se encontro en la sesion es invalido");
			}
			if (proceso == null) {
				exception.addError("El proceso que se encontro en la sesion es invalido");
			}
			if (usuario == null) {
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

			if (exception.getErrores().size() > 0) {
				throw exception;
			}

			TipoNota tipoNota = new TipoNota();
			tipoNota.setDescripcion(descripcionTipo);

			tipoNota.setIdTipoNota(idTipoNota);

			List tiposNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS);
			Iterator itTiposNotas = tiposNotas.iterator();
			String nombre = "";
			while (itTiposNotas.hasNext()) {
				TipoNota elemento = (TipoNota) itTiposNotas.next();
				if (elemento.getIdTipoNota().equals(idTipoNota)) {
					nombre = elemento.getNombre();
				}
			}

			tipoNota.setNombre(nombre);

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
			nota.setTurno(turno);
			nota.setUsuario(usuario);
			org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

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
			return new EvnNotas(usuarioAuriga, nota, turno);
		}
	}
        /**
         * Imprime las notas informativas ingresadas para el reparto notarial extraordinario.
         * @author: Diana Lora
         * @change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
         * @param request
         * @return
         * @throws ValidacionParametrosException
         */
        private Evento imprimirNotaInformativa(HttpServletRequest request) throws ValidacionParametrosException {

		ValidacionParametrosException exception = new ValidacionParametrosException();

		List notas = null;
		List notasImpresion = new Vector();
		Nota nota = null;
		String valPos = request.getParameter(WebKeys.POSICION);
		int pos = 0;

		if(valPos == null){
			exception.addError("La posición de la nota informativa es inválida");
		}
		try{
			pos = Integer.valueOf(valPos).intValue();
			if(pos < 0){
				exception.addError("La posición de la nota informativa es inválida");
			}
		}catch(Exception e){
			exception.addError("La posición de la nota informativa es inválida");
		}

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
		if(valCopias == null || valCopias.equals("")){
			exception.addError("Por favor ingrese el numero de copias a imprimir.");
		}


		//1. Si existe un turno, se recupera la lista de notas informativas asociada
		//con el turno.
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		if (turno != null)
		{
			notas =  ((Turno)request.getSession().getAttribute(WebKeys.TURNO)).getNotas();
		}

		//2. Si el turno no ha sido creado, se recupera de sesión la lista de notas informativas.
		else
		{
			List listaNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);

			//2.1 Si la lista no es vacía se obtienen las notas informativas.
			if (listaNotas != null)
			{
			  notas = listaNotas;
			}
		}
		//Si se requiere la impresion de notas devolutivas
		 //if(request.getParameter(WebKeys.NOTA_DEVOLUTIVA)){
		 List listaAuxNotasDevolutivas = new ArrayList();
		 List listaAuxNotasInformativas = new ArrayList();
			for  (int i=0; i< notas.size(); i++){
				Nota notaAux = (Nota) notas.get(i);
				if (notaAux != null){
					TipoNota tipoNota = notaAux.getTiponota();
					if (tipoNota != null){
						if (tipoNota.isDevolutiva()){
							String idTipoNota = notaAux.getTiponota().getIdTipoNota();
							String idTipoNotaPadre="";
							if(!idTipoNota.equals("999") && !idTipoNota.substring(1,3).equals("50")
									&& !idTipoNota.substring(1,3).equals("00")){
								int separador = Integer.parseInt(notaAux.getTiponota().getIdTipoNota().substring(1, 2));
								if(separador>=5)
									separador=5;
								else
									separador=0;
								idTipoNotaPadre = notaAux.getTiponota().getIdTipoNota().substring(0, 1) +Integer.toString(separador)+ "0";
							}
							String complemento = "("+(idTipoNotaPadre.equals("")?"":idTipoNotaPadre+" ")+idTipoNota+")";
							notaAux.getTiponota().setDescripcion(notaAux.getTiponota().getDescripcion()+complemento);
							//end daniel
							listaAuxNotasDevolutivas.add(notaAux);
					}
						else
							listaAuxNotasInformativas.add(notaAux);
				}
			}
	     }
		if(request.getParameter(WebKeys.NOTA_DEVOLUTIVA) != null){
			notas = listaAuxNotasDevolutivas;
		}
		else if(request.getParameter(WebKeys.NOTA_INFORMATIVA) != null)
			notas = listaAuxNotasInformativas;

		if(notas == null){
			notas = new Vector();
		}

		if(pos >= notas.size()){
			exception.addError("La nota informativa que desea ver esta fuera del rango");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		nota = (Nota) notas.get(pos);

		if(turno!=null){
			nota.setTurno(turno);
		}

		notasImpresion.add(nota);

		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		String UID = request.getSession().getId();

		EvnNotas evn = new EvnNotas(usuarioAuriga, EvnNotas.IMPRIMIR_NOTA_INFORMATIVA, notasImpresion, UID, circulo, turno, usuarioSIR);
		evn.setNumCopiasImpresion(copias);
		return evn;
	}
        /**
         * Toma los valores del request y los ingresa en la session para se utilizados
         * posteriormente en el reparto notarial extraordinario.
         * @author: Diana Lora
         * @change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
         * @param request
         * @return
         */
        public Evento guardarTurnosARepartir(HttpServletRequest request){

            String numActas = request.getParameter(CMinuta.NUMERO_ACTAS);
            String numResumenes = request.getParameter(CMinuta.NUMERO_RESUMENES);
            String tipoReparto = request.getParameter(CMinuta.TIPO_REPARTO);
            String[] idsTurnos = request.getParameterValues(CTurno.ID_TURNO);

            HttpSession sesion = request.getSession();

            sesion.setAttribute(CMinuta.NUMERO_ACTAS, numActas);
            sesion.setAttribute(CMinuta.NUMERO_RESUMENES, numResumenes);
            sesion.setAttribute(CMinuta.TIPO_REPARTO, tipoReparto);
            sesion.setAttribute(CTurno.ID_TURNO, idsTurnos);

            return null;
        }
        /**
         * En caso de cancelar el reparto notarial extraordinario, limpia de sesión
         * los valores setteados.
         * @author: Diana Lora
         * @change: Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes Reparto Notarial
         * @param request
         * @return
         */
        public Evento limpiarSesionRepartoExtraordinario(HttpServletRequest request){
            HttpSession session = request.getSession();

            session.removeAttribute(CMinuta.NUMERO_ACTAS);
            session.removeAttribute(CMinuta.NUMERO_RESUMENES);
            session.removeAttribute(CMinuta.TIPO_REPARTO);
            session.removeAttribute(CTurno.ID_TURNO);
            session.removeAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
            
            return null;
        }

	/**
	 * Método que se encarga de realizar el proceso de reparto de minutas y asignarlas a las 
	 * diferentes notarias dependiendo de las condiciones previemente establecidas
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnRepartoNotarial crearReparto(HttpServletRequest request) throws AccionWebException {
            HttpSession session = request.getSession();
            Estacion e = (Estacion) session.getAttribute(WebKeys.ESTACION);
            Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
            Usuario u = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
//            String UID = request.getSession().getId();
            String UID = circulo.getIdCirculo();
            gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

            //NÚMERO DE IMPRESIONES.
            String tipoReparto = request.getParameter(CMinuta.TIPO_REPARTO);
            if(tipoReparto != null){
                //REPARTO ORDINARIO
                String numActas = request.getParameter(CMinuta.NUMERO_ACTAS);
                String numResumenes = request.getParameter(CMinuta.NUMERO_RESUMENES);
                String[] idsTurnos = request.getParameterValues(CTurno.ID_TURNO);

                EvnRepartoNotarial evento = new EvnRepartoNotarial(u, usuarioSIR, circulo, e, EvnRepartoNotarial.EJECUTAR_REPARTO, numActas, numResumenes, tipoReparto, idsTurnos);
                evento.setUID(UID);
                return evento;
            }else{
                tipoReparto = (String) session.getAttribute(CMinuta.TIPO_REPARTO);
                String numActas = (String) session.getAttribute(CMinuta.NUMERO_ACTAS);
                String numResumenes = (String) session.getAttribute(CMinuta.NUMERO_RESUMENES);                
                String[] idsTurnos = (String[]) session.getAttribute(CTurno.ID_TURNO);
                List notasInformativas = (List) session.getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
                EvnRepartoNotarial evento = new EvnRepartoNotarial(u, usuarioSIR, circulo, e, EvnRepartoNotarial.EJECUTAR_REPARTO, numActas, numResumenes, tipoReparto, idsTurnos, notasInformativas);
                evento.setUID(UID);
                return evento;
            }
	}

	/**
	 * Método que permite avanzar el workflow por éxito en la fase de notificar al ciudadano,
	 * en éste caso la notaria.
	 * @param request
	 * @return
	 */
	private EvnRepartoNotarial notificarCiudadanoExito(HttpServletRequest request) {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		EvnRepartoNotarial evn = new EvnRepartoNotarial(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnRepartoNotarial.NOTIFICAR_CIUDADANO, EvnRepartoNotarial.CONFIRMAR);
		return evn;
	}

	/**
	 * Método que permite avanzar el workflow por fracaso en la fase de notificar al ciudadano,
	 * en éste caso la notaria.
	 * @param request
	 * @return
	 */
	private EvnRepartoNotarial notificarCiudadanoFracaso(HttpServletRequest request) {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		EvnRepartoNotarial evn = new EvnRepartoNotarial(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnRepartoNotarial.NOTIFICAR_CIUDADANO, EvnRepartoNotarial.NEGAR);

		return evn;
	}
	
	
	/**
	 * Método que permite cargar el listado de Turnos de Restitución asociados con una
	 * minuta.
	 * @param request
	 * @return
	 */
	private EvnRepartoNotarial cargarTurnosRestitucionMinuta(HttpServletRequest request) {
		
		
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circuloParametro = null;
		String idCirculo = request.getParameter(AWRepartoNotarial.CIRCULO_MINUTA);
		String idMinuta = request.getParameter(AWRepartoNotarial.IDENTIFICADOR_MINUTA);
		EvnRepartoNotarial evn = new EvnRepartoNotarial(usuarioAuriga,circuloParametro,EvnRepartoNotarial.CARGAR_TURNOS_RESTITUCION_MINUTA);
        evn.setIdCirculoMinutaConsultaRestitucion(idCirculo);
        evn.setIdMinutaConsultaRestitucion(idMinuta);
       

		return evn;
	}
	
	

	/*
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespRepartoNotarial respuesta = (EvnRespRepartoNotarial) evento;

		if (respuesta != null) {
			if (respuesta.getTipoEvento().equals(EvnRespRepartoNotarial.EJECUTAR_REPARTO))
			{
                                limpiarSesionRepartoExtraordinario(request);
				request.getSession().setAttribute(WebKeys.LISTA_OBSERVACIONES_REPARTO_NOTARIAL, respuesta.getResultadoReparto());
			}
			if (respuesta.getTipoEvento().equals(EvnRespRepartoNotarial.EJECUTAR_REPARTO_FAILED))
			{
				request.getSession().setAttribute(WebKeys.REPARTO_NOTARIAL_FAILED, new Boolean(true));
			}
			
			if (respuesta.getTipoEvento().equals(EvnRespRepartoNotarial.CARGAR_TURNOS_RESTITUCION_MINUTA)) 
			{
				List turnosRestitucion = (List)respuesta.getPayload();		
				request.getSession().setAttribute(AWRepartoNotarial.LISTADO_TURNOS_RESTITUCION_MINUTA, turnosRestitucion);
                         }
		}
		   
	}
	
	/**
	 * Método que permite avanzar el workflow por éxito en la fase de notificar al ciudadano,
	 * en éste caso la notaria.
	 * @param request
	 * @return
	 */
	private EvnRepartoNotarial notificarCiudadanoExitoMasiva(HttpServletRequest request) {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Map turno = (Map) request.getSession().getAttribute(WebKeys.TURNOS);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		EvnRepartoNotarial evn = new EvnRepartoNotarial(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, EvnRepartoNotarial.NOTIFICAR_CIUDADANO_MASIVA, EvnRepartoNotarial.CONFIRMAR);
		return evn;
	}
}

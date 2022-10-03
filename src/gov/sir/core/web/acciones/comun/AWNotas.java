package gov.sir.core.web.acciones.comun;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
import gov.sir.core.eventos.comun.EvnNotas;
import gov.sir.core.eventos.comun.EvnRespNotas;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CSolicitudFolio;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.devolucion.AWLiquidacionDevolucion;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAgregarNotaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.registro.AWCalificacion;

/** @author : HGOMEZ, FPADILLA
*** Caso Mantis : 12621
*/
import gov.sir.core.negocio.modelo.constantes.DatosNotas;
import gov.sir.core.web.acciones.administracion.AWReasignacionTurnos;
import java.util.*;
import org.auriga.smart.web.acciones.AccionWebException;

/**
 * @author mmunoz, ppabon, jvelez
 */
public class AWNotas extends SoporteAccionWeb {
	public static final String AGREGAR_NOTA = "AGREGAR_NOTA";
	public static final String REGRESAR = "REGRESAR";
	public static final String IMPRIMIR_NOTA_INFORMATIVA = "IMPRIMIR_NOTA_INFORMATIVA";
	public static final String IMPRIMIR_NOTA_INFORMATIVA_DETALLE = "IMPRIMIR_NOTA_INFORMATIVA_DETALLE";
	public static final String IMPRIMIR_NOTA_DEVOLUTIVA_DETALLE = "IMPRIMIR_NOTA_DEVOLUTIVA_DETALLE";	
	public static final String CARGAR_INFO_REQUEST = "CARGAR_INFO_REQUEST";

	public static final String AGREGAR_NOTA_DEVOLUTIVA = "AGREGAR_NOTA_DEVOLUTIVA";
	public static final String ELIMINAR_NOTA_DEVOLUTIVA = "ELIMINAR_NOTA_DEVOLUTIVA";
	public static final String GUARDAR_NOTAS_DEVOLUTIVAS = "GUARDAR_NOTAS_DEVOLUTIVAS";
	public static final String REGISTRAR_CALIFICACION_PARCIAL = "REGISTRAR_CALIFICACION_PARCIAL";
	public static final String AGREGAR_NOTA_DEVOLUTIVA_EDITADA = "AGREGAR_NOTA_DEVOLUTIVA_EDITADA";
	public static final String CANCELAR_AGREGAR_NOTAS_INFORMATIVAS = "CANCELAR_AGREGAR_NOTAS_INFORMATIVAS";


	private String accion;
	
	private HttpSession session;
	public static final String VISTA_ORIGINADORA = "VISTA_ORIGINADORA";
	public static final String COLOCAR_DESCRIPCION = "COLOCAR_DESCRIPCION";
	public static final String COLOCAR_DESCRIPCION_DEVOLUTIVA = "COLOCAR_DESCRIPCION_DEVOLUTIVA";
	public static final String BUSCAR_SUBTIPO_DEVOLUTIVA = "BUSCAR_SUBTIPO_DEVOLUTIVA";
	
	/**
	 * Nombre del atributo en session, donde se guarda un valor en caso de que haya una ruta especial despues de los eventos
	 */
	public static final String RUTA_ESPECIAL = "RUTA_ESPECIAL";
	
	//Constantes de rutas
	public static final String RUTA_INSCRIPCION_PARCIAL = "_INSCRIPCION_PARCIAL";
	
	//flags
	public static final String FLAG_INSCRIPCION_PARCIAL = "FLAG_INSCRIPCION_PARCIAL";
	
	
	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#perform(javax.servlet.http.HttpServletRequest)
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		session = request.getSession();
 		accion = request.getParameter(WebKeys.ACCION);
                String descripcionNota = request.getParameter(CTipoNota.DESCRIPCION);  
		if ((accion == null) || (accion.length() == 0)) {
			throw new AccionWebException("Debe indicar una accion");
		}
		if (accion.equals(AGREGAR_NOTA)) {
			return agregar(request);
		} else if (accion.equals(AWNotas.COLOCAR_DESCRIPCION)) {
			return buscarDescripcion(request);
		} else if (accion.equals(AWNotas.COLOCAR_DESCRIPCION_DEVOLUTIVA)) {
			return buscarDescripcionDevolutiva(request);
		} else if (accion.equals(AWNotas.BUSCAR_SUBTIPO_DEVOLUTIVA)) {
			return buscarSubtipoDevolutiva(request);
		} else if (accion.equals(AWNotas.REGRESAR)) {
			return regresar(request);
		} else if (accion.equals(AWNotas.CARGAR_INFO_REQUEST)) {
			return cargarInfoRequest(request);
		} else if (accion.equals(AWNotas.IMPRIMIR_NOTA_INFORMATIVA)) {
			return imprimirNotaInformativa(request);
		} else if (accion.equals(AWNotas.IMPRIMIR_NOTA_INFORMATIVA_DETALLE)) {
			return imprimirNotaInformativa(request);
		} else if (accion.equals(AWNotas.IMPRIMIR_NOTA_DEVOLUTIVA_DETALLE)) {
			return imprimirNotaInformativa(request);
		} else if (accion.equals(AWNotas.AGREGAR_NOTA_DEVOLUTIVA)) {
			return agregarNotaDevolutiva(request);
		} else if (accion.equals(AWNotas.GUARDAR_NOTAS_DEVOLUTIVAS)) {
			return guardarNotasDevolutivas(request);
		} else if (accion.equalsIgnoreCase(AWNotas.ELIMINAR_NOTA_DEVOLUTIVA)){
			return eliminarNotasDevolutivas(request);
		}else if(accion.equals(AWNotas.IMPRIMIR_NOTA_DEVOLUTIVA_DETALLE)){
			return imprimirNotaDevolutivaDetalles(request);
		}else if(accion.equals((AWNotas.AGREGAR_NOTA_DEVOLUTIVA_EDITADA))){
			return agregarNotaDevolutivaEditada(request);
		}else if(accion.equals(AWNotas.REGISTRAR_CALIFICACION_PARCIAL)){
			return registrarCalificacionParcial(request);
		}else if(accion.equals(AWNotas.CANCELAR_AGREGAR_NOTAS_INFORMATIVAS)){
			return cancelarAgregarNotasInformativas(request);
		}else {
			throw new AccionInvalidaException("Debe indicar una accion valida. La accion " + accion + " no es valida");
		}
	}

	/**
	 * @param request
	 */
	private Evento cargarInfoRequest(HttpServletRequest request) {
            System.out.println("*********** ENTRO A cargarInfoRequest ********");
		for (java.util.Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
			String key = (String) enumeration.nextElement();
			request.getSession().setAttribute(key, request.getParameter(key));
		}
		
		List checkedItems = new ArrayList();
    	for(int i=0;i<AWLiquidacionDevolucion.NUMERO_DOCUMENTOS_A_ENTREGAR;i++){
    		checkedItems.add("");
    	}
		String[] docEntregados = request.getParameterValues("DOCUMENTOS_ENTREGADOS");
        if(docEntregados!=null){
        	for(int i=0;i<checkedItems.size();i++){
            	for(int j=0;j<docEntregados.length;j++){
            		if(i==Integer.parseInt(docEntregados[j])-1){
                		checkedItems.remove(i);
                    	checkedItems.add(i,"checked");
                    	break;
                	}else{
                		checkedItems.remove(i);
                    	checkedItems.add(i,"");
                	}
            	}        		       		
            }
        	session.setAttribute(AWLiquidacionDevolucion.LISTA_DOCUMENTOS_CHECKED, checkedItems);
        }
        
		return null;
	}

	/**
	 * @param request
	 */
	private Evento buscarDescripcion(HttpServletRequest request) {
            System.out.print("Entra a buscarDescripcion");
            ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
            String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
            if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe escoger un tipo de nota valido");
            }

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
	 * @param request
	 */
	private Evento buscarDescripcionDevolutiva(HttpServletRequest request) {
                System.out.print("Entra a buscarDescripcionDevolutiva");
		ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
		String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
		if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe escoger un tipo de nota valido");
		}

		//String idVisibilidad = request.getParameter(CTipoNota.VISIBILIDAD_TIPO_NOTA);
		//if (!(idVisibilidad == null || idVisibilidad.equals(WebKeys.SIN_SELECCIONAR))) {
		//	session.setAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA, idVisibilidad);
		//}

		Iterator itTiposNotas = ((List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS)).iterator();
		while (itTiposNotas.hasNext()) {
			TipoNota elemento = (TipoNota) itTiposNotas.next();
			if (elemento.getIdTipoNota().equals(idTipoNota)) {
				session.setAttribute(CTipoNota.DESCRIPCION, elemento.getDescripcion());
                                System.out.print("Entra a getDescripcion"+elemento.getDescripcion());
				session.setAttribute(CTipoNota.ID_TIPO_NOTA, idTipoNota);
                                System.out.print("Entra a idTipoNota"+idTipoNota);
				session.setAttribute(CNota.DESCRIPCION, request.getParameter(CNota.DESCRIPCION));
                                System.out.print("Entra a request.getParameter(CNota.DESCRIPCION)"+request.getParameter(CNota.DESCRIPCION));
				session.setAttribute(CNota.NOMBRE, elemento.getNombre());
                                System.out.print("Entra a elemento.getNombre()"+elemento.getNombre());
			}
		}

		return null;
	}	
	
	/**
	 * @param request
	 */
	private Evento buscarSubtipoDevolutiva(HttpServletRequest request) {
                System.out.print("Entra a buscarSubtipoDevolutiva");
		ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
		String idTipoNota = request.getParameter(CTipoNota.ID_SUBTIPO_NOTA);
		if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe escoger un tipo de nota valido");
		}

		//String idVisibilidad = request.getParameter(CTipoNota.VISIBILIDAD_TIPO_NOTA);
		//if (!(idVisibilidad == null || idVisibilidad.equals(WebKeys.SIN_SELECCIONAR))) {
		//	session.setAttribute(CTipoNota.VISIBILIDAD_TIPO_NOTA, idVisibilidad);
		//}

		Iterator itTiposNotas = ((List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS)).iterator();
		while (itTiposNotas.hasNext()) {
			TipoNota elemento = (TipoNota) itTiposNotas.next();
			if (elemento.getIdTipoNota().equals(idTipoNota)) {
				session.setAttribute(CTipoNota.ID_SUBTIPO_NOTA, idTipoNota);
                                System.out.print("Entra a idTipoNota"+idTipoNota);
				session.setAttribute(CNota.DESCRIPCION, request.getParameter(CNota.DESCRIPCION));
                                System.out.print("Entra a request.getParameter(CNota.DESCRIPCION)"+request.getParameter(CNota.DESCRIPCION));
			}
		}
		session.removeAttribute(CTipoNota.DESCRIPCION);
		request.setAttribute(CTipoNota.ID_TIPO_NOTA,session.getAttribute(CTipoNota.ID_TIPO_NOTA));
		session.removeAttribute(CTipoNota.ID_TIPO_NOTA);
		session.removeAttribute(CNota.NOMBRE);
		return null;
	}	
	

	/**
	 * @param request
	 * @return
	 */
	private Evento agregar(HttpServletRequest request) throws AccionWebException {

        ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
        Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);

        //CASO ESPECIAL NO EXISTE TURNO.
        if (turno == null) {
            //1. Se valida que se haya seleccionado un tcircuipo de nota informativa.
            String idTipoNota = request.getParameter(CTipoNota.ID_TIPO_NOTA);
            if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe escoger un tipo de nota válido");
            }
            
            String description = request.getParameter(CNota.DESCRIPCION);
            if (description == null || description.length() < 50) {
                request.getSession().setAttribute(CNota.DESCRIPCION, description);
                exception.addError("La descripción debe ser mayor a 50 caracteres.");
            }

            if (exception.getErrores().size() > 0) {
                throw exception;
            }

            //2. Se crea la nota.
            Nota notaInformativa = new Nota();
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            notaInformativa.setIdNota(idTipoNota);
            notaInformativa.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
            notaInformativa.setIdCirculo(circulo.getIdCirculo());
            notaInformativa.setIdProceso(proceso.getIdProceso());
            notaInformativa.setIdTurno(null);
            notaInformativa.setTime(calendar.getTime());
            notaInformativa.setUsuario(usuario);

            String descripcion = request.getParameter(CNota.DESCRIPCION);
            if (descripcion != null) {
                request.getSession().removeAttribute(CNota.DESCRIPCION);
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
            List newNoteList = new ArrayList();

            //3.1 Si la lista es vacía se crea y se agrega la nota
            if (listaNotas == null || listaNotas.isEmpty()) {
                listaNotas = new ArrayList();
                listaNotas.add(notaInformativa);
                
                session.setAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO, listaNotas);

            } //3.2 Si la lista ya tiene elementos, se agregan
            else {
                Iterator itOld = listaNotas.iterator();
                while(itOld.hasNext()){
                    Nota nota = (Nota) itOld.next();
                    if(nota != null){
                        newNoteList.add(nota);                    }
                }
                session.setAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO, newNoteList);
            }

        

            //5. Se retorna null para que no se genere ningún evento y se continue
            //en la misma jsp.
            return null;
        } //CASO EXISTE TURNO
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
            
            String descripcion = request.getParameter(CNota.DESCRIPCION).trim();           

            if (descripcion.isEmpty() || (descripcion == null) || descripcion.length() < 50) {
                request.getSession().setAttribute(CNota.DESCRIPCION, descripcion);
                exception.addError("La descripción debe ser mayor a 50 caracteres.");
            }
               
                
            if (descripcion != null) {
                descripcion = descripcion.toUpperCase();
            }
            String descripcionTipo = request.getParameter((CTipoNota.DESCRIPCION));

            if (exception.getErrores().size() > 0) {
                throw exception;
            }
            
            request.getSession().removeAttribute(CNota.DESCRIPCION);

            TipoNota tipoNota = new TipoNota();
            if ((accion == null) || (accion.length() == 0)) {
                throw new AccionWebException("Debe indicar una accion");
            }
            tipoNota.setDescripcion(descripcionTipo);
            tipoNota.setIdTipoNota(idTipoNota);

            String nombre = "";
            Long version = 0L;
            
            List tiposNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS);
            Iterator itTiposNotas = tiposNotas.iterator();
            while (itTiposNotas.hasNext()) {
                TipoNota elemento = (TipoNota) itTiposNotas.next();
                if (elemento.getIdTipoNota().equals(idTipoNota)) {
                    nombre = elemento.getNombre();
                    /**
                     * @author : CTORRES
                     *** @change : Ajustes respectivos para setear versión del
                     * Tipo de Nota. ** Caso Mantis : 14370: Acta -
                     * Requerimiento No 022_589_Nota_Informativa_Mayor_Valor
                     */
                    version = elemento.getVersion();

                }
            }

            tipoNota.setNombre(nombre);
            tipoNota.setVersion(version);

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
	 * Llama al evento de eliminación de las notas informativas y regresa a la pantalla anterior
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento cancelarAgregarNotasInformativas(HttpServletRequest request)
		throws AccionWebException
	{
		limpiarSession(request);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		if(turno==null){
			session.removeAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
			return null;
		}
		EvnNotas ev = new EvnNotas(null,null,turno);
		ev.setTipoEvento(EvnNotas.ELIMINAR_NOTAS_INFORMATIVAS_ULTIMA_FASE_TURNO);
		return ev;
	}
	
	
	/**
	 * @param request
	 * @return
	 */
	private Evento regresar(HttpServletRequest request) throws AccionWebException {
		limpiarSession(request);
		return null;
	}
	
	/**
	 * @param request
	 * @return
	 */
	public void limpiarSession(HttpServletRequest request) {
		request.getSession().removeAttribute(AWNotas.RUTA_ESPECIAL);
		request.getSession().removeAttribute(AWNotas.FLAG_INSCRIPCION_PARCIAL);
		request.getSession().removeAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION);
	}
	
	/**
	 * @param request
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
                                                                        try{
                                                                            int separador = Integer.parseInt(notaAux.getTiponota().getIdTipoNota().substring(1, 2));
                                                                                if (separador >= 5) {
                                                                                    separador = 5;
                                                                                } else {
                                                                                    separador = 0;
                                                                                }
                                                                            idTipoNotaPadre = notaAux.getTiponota().getIdTipoNota().substring(0, 1) + Integer.toString(separador) + "0";

                                                                        }catch(NumberFormatException ex){

                                                                        }
                                                                    }
                                                                    String complemento = "(" + (idTipoNotaPadre.equals("") ? "" : idTipoNotaPadre + " ") + idTipoNota + ")";
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
		Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		String UID = request.getSession().getId();
		
		EvnNotas evn = new EvnNotas(usuarioAuriga, EvnNotas.IMPRIMIR_NOTA_INFORMATIVA, notasImpresion, UID, circulo, turno, usuarioSIR);
		evn.setNumCopiasImpresion(copias);
                evn.setImprimirYN(copias);
		return evn;
	}	



	//OPCIONES PARA NOTAS DEVOLUTIVAS
	/**
	 * @param request
	 * @return
	 */
	private Evento agregarNotaDevolutiva(HttpServletRequest request) throws AccionWebException {

		ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Fase fase = (Fase)session.getAttribute(WebKeys.FASE);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		List notasImpresion = (List)session.getAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION);
		String idTipoNotaPadre = request.getParameter("ID_TIPO_NOTA_PADRE");
		if(idTipoNotaPadre == null)
			idTipoNotaPadre = "";
		if(notasImpresion == null){
			notasImpresion = new Vector();
		}

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
		String descripcionTipo = new String();
		if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR) && session.getAttribute(CNota.EDITAR) == null){
			descripcionTipo = descripcionNotaPadre(idTipoNotaPadre,request);
			descripcionTipo = new String(""+descripcionTipo+"(");
			descripcionTipo = descripcionTipo + idTipoNotaPadre + ")";
		}
		else if(session.getAttribute(CNota.EDITAR)== null){
			descripcionTipo = request.getParameter((CTipoNota.DESCRIPCION));
			descripcionTipo = new String(""+descripcionTipo+"("+idTipoNotaPadre);
			descripcionTipo = descripcionTipo + " " + idTipoNota + ")";
		}
		else{
			descripcionTipo = request.getParameter((CTipoNota.DESCRIPCION));
			
		}
		
		String descripcion = request.getParameter(CNota.DESCRIPCION);
		if(descripcion!=null){
			descripcion = descripcion.toUpperCase();	
		}
		session.setAttribute(CNota.DESCRIPCION , descripcion);

		String valCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
		session.setAttribute(WebKeys.NUMERO_COPIAS_IMPRESION , valCopias);
		 
		
				
		
		//Caso especial Inscripcion parcial (Calificacion)
		fase = (Fase)session.getAttribute(WebKeys.FASE);
		String flag = (String) session.getAttribute(AWNotas.FLAG_INSCRIPCION_PARCIAL); 
		if(fase !=null && fase.getID().equals(CFase.CAL_CALIFICACION) && 
		   flag!=null && flag.equals(AWNotas.FLAG_INSCRIPCION_PARCIAL)){
			List foliosFuente = null;
			String[] matriculasImp = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);

			Solicitud solicitud = turno.getSolicitud();
			List solFolios = solicitud.getSolicitudFolios();

			Iterator it = null;
			if(matriculasImp !=null ){
				foliosFuente = new ArrayList();

				for (int indice = 0; indice < matriculasImp.length; indice++) {

					it = solFolios.iterator();
					while(it.hasNext()){
						SolicitudFolio solFolio = (SolicitudFolio)it.next();
						if(solFolio.getIdMatricula().equals(matriculasImp[indice])){
							Folio folio = new Folio();
							folio.setIdMatricula(solFolio.getIdMatricula());
							foliosFuente.add(solFolio);
							break;
						}
					}
				}
				
				session.setAttribute(AWCalificacion.FOLIOS_INSCRIPCION_PARCIAL, foliosFuente);
			}else{
				//exception.addError("Debe escoger al menos un folio.");
			}
			
			
		}


		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		TipoNota tipoNota = new TipoNota();
		tipoNota.setDescripcion(descripcionTipo);
                
                /** @author : HGOMEZ, FPADILLA
                *** @change : Ajustes respectivos para setear versión del Tipo de Nota.
                *** Caso Mantis : 12621
                */
                DatosNotas datosNotas = DatosNotas.getInstance();
                               
		if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR))
                    {
			tipoNota.setIdTipoNota(idTipoNotaPadre);
                        tipoNota.setVersion(datosNotas.obtenerVersionTipoNota(idTipoNotaPadre));
		}
			
		else{
			tipoNota.setIdTipoNota(idTipoNota);
                        tipoNota.setVersion(datosNotas.obtenerVersionTipoNota(idTipoNota));
		}

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

		tipoNota.setNombre(descripcion);
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
		nota.setTurno(turno);
		nota.setUsuario(usuario);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		

		String numNotas = (String) session.getAttribute(WebKeys.NUM_NOTAS_FASE);

		if (numNotas == null) {
			numNotas = new String();
			numNotas = "0";
		}
		int i = Integer.valueOf(numNotas).intValue();
		i++;
		numNotas = String.valueOf(i);
		session.setAttribute(WebKeys.NUM_NOTAS_FASE, numNotas);
		//Antes de agregar la nota se debe verificar que la nota no este agregada anteriormente y que pueda ser una nota editada
		Nota notaToBorrar=null;
		if(session.getAttribute(CNota.EDITAR)!=null){
			if(notasImpresionContieneNotas(notasImpresion,nota)){
				for(int j = 0; j < notasImpresion.size(); j++){
					Nota temporal = (Nota) notasImpresion.get(j);
					if((temporal.getTiponota().getIdTipoNota().equals(nota.getTiponota().getIdTipoNota())) && nota != null){
						notaToBorrar=temporal;
						notasImpresion.remove(temporal);
					}
				}
				
			}
			session.removeAttribute(CNota.EDITAR);
		}
		if(!notasImpresionContieneNotas(notasImpresion,nota))
			notasImpresion.add(nota);
		else
			exception.addError("No se puede agregar una misma causal de devolución para un mismo turno");
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		
		session.setAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION , notasImpresion);
		session.setAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO , notasImpresion);
		//turno.getNotas();
		turno.addNota(nota);
		session.setAttribute(WebKeys.TURNO, turno);
		

		if(turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO) 
				&& CFase.CAL_CALIFICACION.equals(turno.getIdFase())){
			EvnNotas evn= new EvnNotas(usuarioAuriga,nota,turno,EvnNotas.GUARDA_NOTA_DEVOLUTIVA_ADD);
			evn.setNotaToBorrar(notaToBorrar);
			return evn;
			
		}
		return null; 
		
	}	
	
	
	/**
	 * @param request
	 * @return
	 */
	private Evento guardarNotasDevolutivas(HttpServletRequest request) throws AccionWebException {

		ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Fase fase = (Fase)session.getAttribute(WebKeys.FASE);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		ArrayList matriculasNoInscritas = new ArrayList();
                 String ImprimirQuest = request.getParameter(WebKeys.ImprimirCC);
		ArrayList matriculasInscritasParcialmente = new ArrayList();
                
                Boolean haveAnotacionTMP = (Boolean) request.getSession().getAttribute(WebKeys.TIENE_ANOTACIONES_TMP);
                String inscripcionP = (String) request.getSession().getAttribute("FLAG_INSCRIPCION_PARCIAL");
                boolean isInscritoP = (inscripcionP != null && inscripcionP.equals("FLAG_INSCRIPCION_PARCIAL"));
                if(haveAnotacionTMP != null && haveAnotacionTMP && !isInscritoP){
                    exception.addError("No es posible avanzar el turno: El turno no debe tener anotaciones temporales en ninguno de los folios.");
                }

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		
		String descripcion = request.getParameter(CNota.DESCRIPCION);
		if(descripcion!=null){
			descripcion = descripcion.toUpperCase();	
		}
		
		List notasImpresion = (List)session.getAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION);
		if(notasImpresion == null){
			notasImpresion = new Vector();
		}
		if(notasImpresion.isEmpty()){
			exception.addError("Debe agregar por lo menos una causal de devolución.");
		}else{
			Iterator it = notasImpresion.iterator();
			while(it.hasNext()){
				Nota nota = (Nota)it.next();
				if (nota.getDescripcion()==null) {
					nota.setDescripcion(descripcion);
				}
			}			 
		}
		if (turno == null) {
			exception.addError("No se encuentra un turno válido");
		}
		if (circulo == null) {
			exception.addError("No se encuentra un circulo válido");
		}
		if (proceso == null) {
			exception.addError("No se encuentra un proceso válido");
		}
		if (usuario == null) {
			exception.addError("No se encuentra un usuario válido");
		}

		
		//Caso especial Inscripcion parcial (Calificacion)
		boolean inscripcionParcial = false;
		fase = (Fase)session.getAttribute(WebKeys.FASE);
		String flag = (String) session.getAttribute(AWNotas.FLAG_INSCRIPCION_PARCIAL); 
		if(fase !=null && fase.getID().equals(CFase.CAL_CALIFICACION) && 
		   flag!=null && flag.equals(AWNotas.FLAG_INSCRIPCION_PARCIAL)){
			
			inscripcionParcial = true;
			List foliosFuente = null;
			String[] matriculasImp = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);

			Solicitud solicitud = turno.getSolicitud();
			List solFolios = solicitud.getSolicitudFolios();

			Iterator it = null;
			if(matriculasImp !=null ){
				foliosFuente = new ArrayList();

				for (int indice = 0; indice < matriculasImp.length; indice++) {

					it = solFolios.iterator();
					while(it.hasNext()){
						SolicitudFolio solFolio = (SolicitudFolio)it.next();
						if(solFolio.getIdMatricula().equals(matriculasImp[indice])){
							Folio folio = new Folio();
							folio.setIdMatricula(solFolio.getIdMatricula());
							foliosFuente.add(solFolio);
							matriculasInscritasParcialmente.add(solFolio.getIdMatricula());
							break;
						}
					}
				}
				
				session.setAttribute(AWCalificacion.FOLIOS_INSCRIPCION_PARCIAL, foliosFuente);
			}else{
				exception.addError("Debe escoger al menos un folio.");
			}
		}
		
		//Se obtiene el atributo de sesión que contiene las matrículas inscritas
		Hashtable validacionAnotacionesTemporales = 
			(java.util.Hashtable) session.getAttribute(WebKeys.VALIDACION_APROBAR_CALIFICACION);
		//Se toman los ids de matriculas que están inscritas
		Solicitud solicitud = turno.getSolicitud();
		List solFolios = solicitud.getSolicitudFolios();
		Iterator itSolFolios = solFolios.iterator();
		while(itSolFolios.hasNext()){
			SolicitudFolio solFolio = (SolicitudFolio)itSolFolios.next();
			if(!matriculasInscritasParcialmente.contains(solFolio.getIdMatricula()))
			if(!this.isMatriculaInscrita(solFolio.getIdMatricula(), validacionAnotacionesTemporales) ){
				matriculasNoInscritas.add(solFolio.getIdMatricula());
			}
		}
		
		String valCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
		session.setAttribute(WebKeys.NUMERO_COPIAS_IMPRESION, valCopias);
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
                
                /**
                * @Autor: Edgar Lora
                * @Mantis: 0013038
                * @Requerimiento: 060_453
                */          
                if(request.getParameter("INSCRIPCION_PARCIAL") != null){
                    for(int i = 0; i < solFolios.size(); i = i + 1){
                        SolicitudFolio solFolio = (SolicitudFolio)solFolios.get(i);
                        Folio folio = solFolio.getFolio();
                        try {
                            ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                            String matricula = folio.getIdMatricula();
                            String lindero = validacionesSIR.getLinderoDeMatricula(matricula);
                            if(lindero == null){
                                lindero =  folio.getLindero();
                            }
                            if(validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)){
                                if(validacionesSIR.validarLinderos(matricula, CNaturalezaJuridica.NATURALEZA_PARA_VALIDAR_LINDEROS)){
                                    String articulo = ("Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012").toUpperCase();
                                    if(lindero.indexOf(articulo) != -1){
                                        int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                                        if(lindero.length() - tamArticulo < 100){
                                            exception.addError("Debe añadir minimo 100 caracteres al campo de linderos de la matricula: " + matricula);
                                        }
                                    }else{
                                        exception.addError("El lindero de la matricula " + matricula + " debe tener 100 caracteres a partir del texto \"Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012\", Porfavor no lo borre.");
                                    }
                                }
                            }
                        } catch (GeneralSIRException ex) {
                            exception.addError(ex.getMessage());
                        }
                    }
                }
                
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		String numNotas = (String) session.getAttribute(WebKeys.NUM_NOTAS_FASE);

		if (numNotas == null) {
			numNotas = new String();
			numNotas = "0";
		}
		int i = Integer.valueOf(numNotas).intValue();
		i++;
		numNotas = String.valueOf(i);
		session.setAttribute(WebKeys.NUM_NOTAS_FASE, numNotas);

		Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		String UID = request.getSession().getId();
		

		EvnNotas evn = new EvnNotas(usuarioAuriga, EvnNotas.GUARDAR_NOTAS_DEVOLUTIVAS, notasImpresion, UID, circulo, turno, usuarioSIR, fase);
		evn.setNumCopiasImpresion(copias);
		//Se añade la lista de matriculas inscritas al evento
		evn.setMatriculasNoInscritas(matriculasNoInscritas);
                if(fase.getID().equals(CFase.CAL_CALIFICACION)){
                    if(ImprimirQuest != null){
                    evn.setImprimirYN(1);
                   }else{
                    evn.setImprimirYN(copias);
                    }
                }else{
                    evn.setImprimirYN(copias);
                }
               
		if(inscripcionParcial){
			evn.setInscripcionParcial(inscripcionParcial);
			evn.setMatriculasInscritasParcialmente(matriculasInscritasParcialmente);
		}
		return evn;		

	}		
		
	/**
	 * Valida que el id matrícula esté dentro de las matrículas inscritas
	 * @author olopez 
	 * @param idMatricula
	 * @return
	 */
	private boolean isMatriculaInscrita(String idMatricula,
			Hashtable validacionAnotacionesTemporales){
		boolean isInscrita = false;
		
		if(validacionAnotacionesTemporales!=null){
			Boolean hasTemporales = (Boolean)validacionAnotacionesTemporales.get(idMatricula);
			if(hasTemporales!=null && hasTemporales.booleanValue()){
				isInscrita = true;
			}
		}
		
		return isInscrita;
	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento registrarCalificacionParcial(HttpServletRequest request) throws AccionWebException {

		Hashtable ht = new Hashtable();
		boolean ok=false;
		ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Fase fase = (Fase)session.getAttribute(WebKeys.FASE);
                Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		
		

		String descripcion = request.getParameter(CNota.DESCRIPCION);
		if(descripcion!=null){
			descripcion = descripcion.toUpperCase();	
		}
		
		List notasImpresion = (List)session.getAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION);
		if(notasImpresion == null){
			notasImpresion = new Vector();
		}
		if(notasImpresion.isEmpty()){
			exception.addError("Debe agregar por lo menos una causal de devolución.");
		}else{
			Iterator it = notasImpresion.iterator();
			while(it.hasNext()){
				Nota nota = (Nota)it.next();
				if (nota.getDescripcion()==null) {
					nota.setDescripcion(descripcion);
				}
			}			 
		}
		if (turno == null) {
			exception.addError("No se encuentra un turno válido");
		}
		if (circulo == null) {
			exception.addError("No se encuentra un circulo válido");
		}
		if (proceso == null) {
			exception.addError("No se encuentra un proceso válido");
		}
		if (usuario == null) {
			exception.addError("No se encuentra un usuario válido");
		}

		
		//Caso especial Inscripcion parcial (Calificacion)
		boolean inscripcionParcial = false;
		fase = (Fase)session.getAttribute(WebKeys.FASE);
		String flag = (String) session.getAttribute(AWNotas.FLAG_INSCRIPCION_PARCIAL); 
		if(fase !=null && fase.getID().equals(CFase.CAL_CALIFICACION) && 
		   flag!=null && flag.equals(AWNotas.FLAG_INSCRIPCION_PARCIAL)){
			
			inscripcionParcial = true;
			List foliosFuente = null;
			String[] matriculasImp = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
			

			Solicitud solicitud = turno.getSolicitud();
			List solFolios = solicitud.getSolicitudFolios();

			Iterator it = null;
			if(matriculasImp !=null ){
				foliosFuente = new ArrayList();
			
				ArrayList matriculasInscritas = new ArrayList();
				for (int indice = 0; indice < matriculasImp.length; indice++) {

					it = solFolios.iterator();
					
					while(it.hasNext()){
						SolicitudFolio solFolio = (SolicitudFolio)it.next();
						if(solFolio.getIdMatricula().equals(matriculasImp[indice])){
							Folio folio = new Folio();
							folio.setIdMatricula(solFolio.getIdMatricula());
							foliosFuente.add(solFolio);
							matriculasInscritas.add(matriculasImp[indice]);
							break;
						}
					}
				}
				session.setAttribute(""+CSolicitudFolio.ESTADO_INSCRITO,matriculasInscritas);
				session.setAttribute(AWCalificacion.FOLIOS_INSCRIPCION_PARCIAL, foliosFuente);
			}else{
				exception.addError("Debe escoger al menos un folio.");
			}
		}
		
		Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		String UID = request.getSession().getId();


		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		EvnNotas evn = new EvnNotas(usuarioAuriga, EvnNotas.REGISTRAR_CALIFICACION_PARCIAL, notasImpresion, UID, circulo, turno, usuarioSIR, fase);
		evn.setTipoEvento(AWNotas.REGISTRAR_CALIFICACION_PARCIAL);
		//evn.setValidacionAnotacionesTemporales(ht);
		return evn;
	}	
	
	
	/**
	 * @param request
	 * @return
	 */
	private Evento eliminarNotasDevolutivas(HttpServletRequest request) throws AccionWebException {

		ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Fase fase = (Fase)session.getAttribute(WebKeys.FASE);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		List notasImpresion = (List)session.getAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION);
		if(notasImpresion == null){
			notasImpresion = new Vector();
		}
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

		String idTipoNota = request.getParameter("ITEM");

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		TipoNota tipoNota = new TipoNota();
		tipoNota.setIdTipoNota(idTipoNota);

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

		String posicion=(String)request.getParameter(WebKeys.POSICION);
		Nota nota =(Nota)notasImpresion.get(Integer.parseInt(posicion));
		if(nota==null)
			nota=new Nota();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		nota.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
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
		//Antes de agregar la nota se debe verificar que la nota no este agregada anteriormente
		if(notasImpresionContieneNotas(notasImpresion,nota)){
			for(int j = 0; j < notasImpresion.size(); j++){
				Nota temporal = (Nota) notasImpresion.get(j);
				if((temporal.getTiponota().getIdTipoNota().equals(nota.getTiponota().getIdTipoNota())) && nota != null)
					notasImpresion.remove(temporal);
			}
			
		}
		session.setAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION , notasImpresion);
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		
		if(turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO) 
				&& CFase.CAL_CALIFICACION.equals(turno.getIdFase())){
			return new EvnNotas(usuarioAuriga,nota,turno,EvnNotas.ELIMINA_NOTA_DEVOLUTIVA_ADD);
		}
		return null; 
		
	}
	private Evento agregarNotaDevolutivaEditada(HttpServletRequest request) throws AccionWebException {
		ValidacionParametrosException exception = new ValidacionParametrosException();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		
		List notas = null;
		List notasImpresion = new Vector();		
		String idTipoNota = request.getParameter("ITEM");
		String idTipoNotaPadre = request.getParameter("DESCRIPCION_PADRE");
		//String idPadre = new String();
		int posFinal = idTipoNotaPadre.length();
		int posCorteUnico = posFinal;
		int posInicial = posFinal;
		int posCorte = 0;
		String idPadre=null;
		while(posInicial != 0){
			posInicial--;
			String temporal = idTipoNotaPadre.substring(posInicial,posFinal);
			if(temporal.equals(" "))
				posCorte = posInicial;
			if(temporal.equals("(")){
				if(posCorte == 0)
					posCorte = posCorteUnico - 1;
				idPadre = idTipoNotaPadre.substring(posInicial+1,posCorte);
				posInicial = 0;
			}
			posFinal--;

		}
		
		List listaNotasDevolutivas = (List)session.getAttribute(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS);
	    if(listaNotasDevolutivas==null ){
		    List listaNotasInformativas = (List)session.getAttribute(WebKeys.LISTA_TIPOS_NOTAS);    	
		    listaNotasDevolutivas = new ArrayList();
		    Iterator it = listaNotasInformativas.iterator();
		    while(it.hasNext()){
		    	TipoNota tipoNota = (TipoNota)it.next();
		    	if(tipoNota.isDevolutiva()){
		    		listaNotasDevolutivas.add(tipoNota);
		    	}
		    }
	    }
	   //List elementosTipoNota = new Vector();
	   Iterator itTipos = listaNotasDevolutivas.iterator();
	   String nota1=idTipoNota;
	   List limites=new ArrayList();
	   while (itTipos.hasNext()) {
	   		TipoNota tipoNota = (TipoNota) itTipos.next();
	   		String cadenaTipo= "";
	   		if(tipoNota!=null && tipoNota.getNombre()!=null && tipoNota.getNombre().length()>=4){
	   			cadenaTipo = tipoNota.getNombre().substring(0, 4);
	   		}
	   		String cadena="* * ";
	   		if(cadenaTipo.equals(cadena) && idTipoNota.substring(0,1).equals(tipoNota.getIdTipoNota().substring(0,1))){
	   			limites.add(tipoNota.getIdTipoNota());
	   		}
	   }
	    Collections.sort(limites);
	    for(int i=0;i<limites.size();i++){
                try{
                    if(Long.parseLong(  (String)limites.get(i) )<Long.parseLong(idTipoNota)){
                            nota1=(String) limites.get(i);
                    }
                }catch(NumberFormatException ex){
                    nota1 = idTipoNota.split("-")[0];
                }
	    }
	    idPadre=nota1;
  	    TipoNota tipoNota = new TipoNota();
		tipoNota.setIdTipoNota(idTipoNota);
		//1. Si existe un turno, se recupera la lista de notas devolutivas asociada 
		//con el turno.
		String posicion=(String)request.getParameter(WebKeys.POSICION);
		Nota nota = null;
		notasImpresion = (List)session.getAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION);
		if(notasImpresion!=null && notasImpresion.size()>0)
			nota=(Nota)notasImpresion.get(Integer.parseInt(posicion));
		if(nota!=null){
			try{
				turno.removeNota(nota);
			}catch(Exception e){}
		}else{
			nota=new Nota();
		}
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		nota.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
		nota.setIdCirculo(circulo.getIdCirculo());
		nota.setIdProceso(proceso.getIdProceso());
		nota.setIdTurno(turno.getIdTurno());
		nota.setTime(calendar.getTime());
		//nota.setTiponota(tipoNota);
		nota.setTurno(turno);
		nota.setUsuario(usuario);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		turno.addNota(nota);
		session.setAttribute(WebKeys.TURNO, turno);
		

		//Antes de imprimir la nota se debe verificar que la nota se encuentre en la lista de notas devolutivas
		Nota temporal = null;
		if(notasImpresionContieneNotas(notasImpresion,nota)){
			for(int j = 0; j < notasImpresion.size(); j++){
				temporal = (Nota) notasImpresion.get(j);
				if((temporal.getTiponota().getIdTipoNota().equals(nota.getTiponota().getIdTipoNota())) && nota != null)
					j = notasImpresion.size();
			}
			
		}
		if(notas == null){
			notas = new Vector();
		}
		
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		session.setAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION , notasImpresion);
		
		Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		String UID = request.getSession().getId();
		
		//EvnNotas evn = new EvnNotas(usuarioAuriga, EvnNotas.IMPRIMIR_NOTA_INFORMATIVA, notas, UID, circulo, turno, usuarioSIR);
		//evn.setNumCopiasImpresion(1);
		session.setAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION , notasImpresion);
		session.setAttribute(CTipoNota.NOMBRE_TIPO_NOTA, tipoNota.getNombre());
		session.setAttribute(CTipoNota.DESCRIPCION, temporal.getTiponota().getDescripcion());
		session.setAttribute(CTipoNota.DEVOLUTIVA, new Boolean(true));
		session.setAttribute(CTipoNota.ID_SUBTIPO_NOTA, idPadre);
		session.setAttribute(CTipoNota.ID_TIPO_NOTA, tipoNota.getIdTipoNota());
		session.setAttribute("NOTA_DESCRIPCION", temporal.getDescripcion());
		session.setAttribute(CTipoNota.IDENTIFICADOR, temporal.getTiponota().getIdTipoNota());
		session.setAttribute(CNota.EDITAR,CNota.EDITAR);
		return null; 

	}
	
	/**
	 * @param request
	 */
	private Evento imprimirNotaDevolutivaDetalles(HttpServletRequest request) throws ValidacionParametrosException {

		ValidacionParametrosException exception = new ValidacionParametrosException();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		
		List notas = null;
		List notasImpresion = new Vector();		
		String idTipoNota = request.getParameter("ITEM");
		TipoNota tipoNota = new TipoNota();
		tipoNota.setIdTipoNota(idTipoNota);
		//1. Si existe un turno, se recupera la lista de notas devolutivas asociada 
		//con el turno.

		Nota nota = new Nota();

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		nota.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
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
		notasImpresion = (List)session.getAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION);

		//Antes de imprimir la nota se debe verificar que la nota se encuentre en la lista de notas devolutivas
		Nota temporal = null;
		if(notasImpresionContieneNotas(notasImpresion,nota)){
			for(int j = 0; j < notasImpresion.size(); j++){
				temporal = (Nota) notasImpresion.get(j);
				if((temporal.getTiponota().getIdTipoNota().equals(nota.getTiponota().getIdTipoNota())) && nota != null)
					j = notasImpresion.size();
			}
			
		}
		if(notas == null){
			notas = new Vector();
		}
		
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		temporal.getTiponota().setDevolutiva(true);
		temporal.setIdFase("Impresion_Temporal");
		notas.add(temporal);
		Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		String UID = request.getSession().getId();
		
		EvnNotas evn = new EvnNotas(usuarioAuriga, EvnNotas.IMPRIMIR_NOTA_INFORMATIVA, notas, UID, circulo, turno, usuarioSIR);
		evn.setNumCopiasImpresion(1);
		return evn;
	}	

	public boolean notasImpresionContieneNotas(List notasImpresion, Nota nota){
		for(int i = 0; i < notasImpresion.size(); i++){
			Nota temporal = (Nota) notasImpresion.get(i);
			if((temporal.getTiponota().getIdTipoNota().equals(nota.getTiponota().getIdTipoNota())) && nota != null)
				return true;
		}
		return false;
	}
	
	public String descripcionNotaPadre(String idTipoNotaPadre, HttpServletRequest request){
		Iterator itTiposNotas = ((List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS_DEVOLUTIVAS)).iterator();
		while (itTiposNotas.hasNext()) {
			TipoNota elemento = (TipoNota) itTiposNotas.next();
			if (elemento.getIdTipoNota().equals(idTipoNotaPadre)) {
				return elemento.getDescripcion();
			}
		}
		return "";
	}
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

		EvnRespNotas respuesta = (EvnRespNotas) evento;

		if (respuesta != null) {
			if (respuesta.getTipoEvento().equals(EvnRespNotas.TURNO_NOTA_ADICIONADA)) {
				session.setAttribute(WebKeys.TURNO, respuesta.getTurno());
			}
			else if (respuesta.getTipoEvento().equals(EvnRespNotas.AGREGAR_NOTA_DEVOLUTIVA)) {
				/*session.setAttribute(WebKeys.TURNO, respuesta.getTurno());
				session.setAttribute(WebKeys.VALIDACION_NOTA_DEVOLUTIVA, new Boolean(true));
				session.setAttribute(WebKeys.NOTA_DEVOLUTIVA_IMPRESA, new Boolean(respuesta.isImpreso()));
				session.setAttribute(WebKeys.ID_IMPRIMIBLE, new Integer(respuesta.getIdImprimible()));*/
				return;
			}			
			else if (respuesta.getTipoEvento().equals(EvnRespNotas.GUARDAR_NOTAS_DEVOLUTIVAS)) {
				session.setAttribute(WebKeys.TURNO, respuesta.getTurno());
				session.setAttribute(WebKeys.VALIDACION_NOTA_DEVOLUTIVA, new Boolean(true));
				session.setAttribute(WebKeys.NOTA_DEVOLUTIVA_IMPRESA, new Boolean(respuesta.isImpreso()));
				session.setAttribute(WebKeys.ID_IMPRIMIBLE, new Integer(respuesta.getIdImprimible()));
			}
			else if(respuesta.getTipoEvento().equals(EvnRespNotas.REGISTRAR_CALIFICACION_PARCIAL)){
				session.setAttribute(WebKeys.MATRICULAS_INSCRITAS,respuesta.getValidacionAnotacionesTemporales());
				ArrayList matriculasImp = (ArrayList)session.getAttribute("ESTADO_INSCRITO");
			}else if(respuesta.getTipoEvento().equals(EvnRespNotas.ELIMINAR_NOTAS_INFORMATIVAS_ULTIMA_FASE_TURNO)){
				session.setAttribute(WebKeys.TURNO, respuesta.getTurno());
			}else if(respuesta.getTipoEvento().equals(EvnNotas.GUARDA_NOTA_DEVOLUTIVA_ADD)){
				session.setAttribute(WebKeys.TURNO, respuesta.getTurno());
                Turno turno=respuesta.getTurno();
                if(turno!=null && turno.getNotas()!=null && turno.getNotas().size()>0){
                	Nota nota=null;
                	List notasDevolutivas=new Vector();
                	for(int i=0;i<turno.getNotas().size();i++){
                		nota= (Nota)turno.getNotas().get(i);
                		if(nota!=null && nota.getTiponota()!=null && nota.getTiponota().isDevolutiva())
                			notasDevolutivas.add(nota);
                	}
                	session.setAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION, notasDevolutivas);
                }
				return;
			}
		}
	}

}

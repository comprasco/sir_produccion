package gov.sir.core.web.acciones.correccion;

import gov.sir.core.eventos.correccion.EvnActuacionAdministrativa;
import gov.sir.core.eventos.correccion.EvnRespActuacionAdministrativa;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.NotaActuacion;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CActuacionAdministrativa;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionActuacionAdministrativaException;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * @author ppabon
 */
public class AWActuacionAdministrativa extends SoporteAccionWeb {

	//CONSTANTES DE RESPUESTAS EN EL WORKFLOW PARA ACTUACIONES ADMINISTRATIVAS.
	public static final String WF_MAYOR_VALOR = "MAYOR_VALOR";
	public static final String WF_ANTIGUO_SISTEMA = "ANTIGUO_SISTEMA";
	public static final String WF_CONFIRMAR = "CONFIRMAR";
	public static final String WF_REGRESAR = "REGRESAR";
	

	//ACCIONES QUE SE EJECUTARAN EN ESTA ACCION WEB
	//OPCIONES PARA LA PÁGINA
	public static final String AGREGAR_NOTA = "AGREGAR_NOTA";
	public static final String EDITAR_NOTA = "EDITAR_NOTA";
	public final static String OCULTAR_SOLICITUD = "OCULTAR_SOLICITUD";
	public final static String OCULTAR_MAYOR_VALOR = "OCULTAR_MAYOR_VALOR";		
	public final static String OCULTAR_ANTIGUO_SISTEMA = "OCULTAR_ANTIGUO_SISTEMA";
	
	//OPCIONES PARA EL AVANCE
	public static final String APROBAR_ACTUACION = "APROBAR_ACTUACION";
	public static final String DEVOLVER_A_CORRECCION = "DEVOLVER_A_CORRECCION";
	public static final String ENVIAR_TURNO = "ENVIAR_TURNO";



	public AWActuacionAdministrativa() {
		super();
	}

	public Evento perform(HttpServletRequest request)throws AccionWebException {
		String accion = "";
		accion = request.getParameter(WebKeys.ACCION);

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}
		
		if(accion.equals(AWActuacionAdministrativa.DEVOLVER_A_CORRECCION)){
			return devolverTurnoCorreccionSimple(request);
		} else if(accion.equals(AWActuacionAdministrativa.APROBAR_ACTUACION)){
			return aprobarActuacionAdministrativa(request);
		} else if(accion.equals(AWActuacionAdministrativa.ENVIAR_TURNO)){
			return enviarTurnoOtrasDependencias(request);
		} else if(accion.equals(AWActuacionAdministrativa.AGREGAR_NOTA)){
			return agregarNotaActuacion(request);
		} else if(accion.equals(AWActuacionAdministrativa.EDITAR_NOTA)){
			return editarNotaActuacion(request);
		} else if( accion.equals( OCULTAR_SOLICITUD ) || accion.equals( OCULTAR_MAYOR_VALOR ) || accion.equals( OCULTAR_ANTIGUO_SISTEMA)) {
			return recargarPaginaActuacionAdministrativa( request );
		} else {
			throw new AccionInvalidaException("La accion " + accion + " no es valida.");
		}

		
	}
	
	//OPCION DE AVANCES 
	/**
	 * @param request
	 * @return
	 */
	private EvnActuacionAdministrativa devolverTurnoCorreccionSimple(HttpServletRequest request)throws AccionWebException {

		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		Usuario usuarioSir = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EvnActuacionAdministrativa evento = new EvnActuacionAdministrativa(usuarioAuriga,  EvnActuacionAdministrativa.DEVOLVER_A_CORRECCION, turno, usuarioSir );
		evento.setRespuestaWF(AWActuacionAdministrativa.WF_REGRESAR);
		evento.setFase(fase);
		return evento;
	}	
	
	/**
	 * @param request
	 * @return
	 */
	private EvnActuacionAdministrativa aprobarActuacionAdministrativa(HttpServletRequest request)throws AccionWebException {

		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE); 
		Usuario usuarioSir = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EvnActuacionAdministrativa evento = new EvnActuacionAdministrativa(usuarioAuriga,  EvnActuacionAdministrativa.APROBAR_ACTUACION, turno, usuarioSir );
		evento.setRespuestaWF(AWActuacionAdministrativa.WF_CONFIRMAR);
		evento.setFase(fase);
		return evento;
	}	
	
	/**
	 * @param request
	 * @return
	 */
	private EvnActuacionAdministrativa enviarTurnoOtrasDependencias(HttpServletRequest request)throws AccionWebException {

		this.preservarInfo(request);
		EvnActuacionAdministrativa evento = null;
		ValidacionActuacionAdministrativaException exception = new ValidacionActuacionAdministrativaException();
		
		String procesoAvance = request.getParameter(AWActuacionAdministrativa.ENVIAR_TURNO);
		
		if(procesoAvance == null || procesoAvance.equals(WebKeys.SIN_SELECCIONAR)){
			 exception.addError("Seleccione la dependencia a donde desea enviar el turno.");
		}else{
			if(procesoAvance.equals(AWActuacionAdministrativa.WF_MAYOR_VALOR)){
				evento = enviarTurnoMayorValor(request, exception);
			}
			if(procesoAvance.equals(AWActuacionAdministrativa.WF_ANTIGUO_SISTEMA)){
				evento = enviarTurnoAntiguoSistema(request, exception);
			}
		}
		
		if(exception.getErrores().size()>0){
			throw exception;
		}

		return evento;
	}	
	
	
	/**
	 * @param request
	 * @return
	 */
	private EvnActuacionAdministrativa enviarTurnoMayorValor(HttpServletRequest request, ValidacionActuacionAdministrativaException exception)throws AccionWebException {

		EvnActuacionAdministrativa evento = null;
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE); 
		Usuario usuarioSir = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		
		String valorDerechos = request.getParameter(CActuacionAdministrativa.MAYOR_VALOR_DERECHOS);
		String valorImpuestos = request.getParameter(CActuacionAdministrativa.MAYOR_VALOR_IMPUESTOS);
		String razonDevolucion= request.getParameter(CActuacionAdministrativa.MAYOR_VALOR_JUSTIFICACION);
		
		double dValorDerechos = 0, dValorImpuestos = 0, nvalor = 0;

		if(valorDerechos == null || valorDerechos.equals("")){			
			exception.addError("El valor por derechos es inválido.");
		}else{
			try{
				valorDerechos = valorDerechos.replaceAll(",","");								
				dValorDerechos = Double.parseDouble(valorDerechos);			
				if(dValorDerechos <= 0){
					exception.addError("El valor por derechos debe ser mayor a cero");		
				}
			}catch(NumberFormatException e){
				exception.addError("El valor por derechos es inválido");
			}
		}
		
		if(valorImpuestos == null || valorImpuestos.equals("")){			
			exception.addError("El valor de impuestos es inválido.");
		}else{
			try{
				valorImpuestos = valorImpuestos.replaceAll(",","");								
				dValorImpuestos = Double.parseDouble(valorImpuestos);			
				if(dValorImpuestos <= 0){
					exception.addError("El valor de impuestos debe ser mayor a cero");		
				}
			}catch(NumberFormatException e){
				exception.addError("El valor de impuestos es inválido");
			}
		}
		
		if(razonDevolucion==null || razonDevolucion.equals("")){
			exception.addError("La razón de la devolución esta vacia");
		}
		
		if (exception.getErrores().size() == 0) {

			nvalor = dValorDerechos + dValorImpuestos;

			Liquidacion liquidacion = null;

			//CREAR LA LIQUIDACIÓN
			Solicitud solicitud = turno.getSolicitud();
			if(solicitud instanceof SolicitudRegistro){
				LiquidacionTurnoRegistro liq = new LiquidacionTurnoRegistro();
				liq.setFecha(new Date());
				liq.setUsuario(usuarioSir);
				liq.setValor(nvalor);
				liq.setValorDerechos(dValorDerechos);
				liq.setValorImpuestos(dValorImpuestos);
				liq.setJustificacionMayorValor(razonDevolucion);
				liquidacion = liq;
			}
			if(solicitud instanceof SolicitudCorreccion){
				LiquidacionTurnoCorreccion liq = new LiquidacionTurnoCorreccion();
				liq.setFecha(new Date());
				liq.setUsuario(usuarioSir);
				liq.setValor(nvalor);
				liq.setValorDerechos(dValorDerechos);
				liq.setValorImpuestos(dValorImpuestos);
				liq.setJustificacionMayorValor(razonDevolucion);
				liquidacion = liq;
			}			


			evento = new EvnActuacionAdministrativa(usuarioAuriga, EvnActuacionAdministrativa.ENVIAR_TURNO, turno, usuarioSir);
			evento.setRespuestaWF(AWActuacionAdministrativa.WF_MAYOR_VALOR);
			evento.setFase(fase);			
			if(liquidacion!=null){
				evento.setLiquidacion(liquidacion);	
			}						
		}

		return evento;
	
	}	
	
	
	/**
	 * @param request
	 * @return
	 */
	private EvnActuacionAdministrativa enviarTurnoAntiguoSistema(HttpServletRequest request, ValidacionActuacionAdministrativaException exception)throws AccionWebException {

		EvnActuacionAdministrativa evento = null;
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE); 
		Usuario usuarioSir = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		
		DatosAntiguoSistema das = getDatosAntiguoSistema(request , exception);
		
		if (exception.getErrores().size() == 0) {

			evento = new EvnActuacionAdministrativa(usuarioAuriga, EvnActuacionAdministrativa.ENVIAR_TURNO, turno, usuarioSir);
			evento.setRespuestaWF(AWActuacionAdministrativa.WF_ANTIGUO_SISTEMA);
			evento.setFase(fase);
			if(das!=null){
				evento.setDatosAntiguoSistema(das);	
			}			
			
		}

		return evento;
	
	}	
		
		
	

	//OCULTAR CAMPOS DE ACTUACIONES ADMINISTRATIVAS
	/**
	 * @param request
	 * @return
	 */
	private Evento recargarPaginaActuacionAdministrativa(HttpServletRequest request) {

		HttpSession session = request.getSession();

		preservarInfo( request );
		
		String ocultarSolicitud = request.getParameter( CActuacionAdministrativa.OCULTAR_SOLICITUD);
		String ocultarMayorValor = request.getParameter( CActuacionAdministrativa.OCULTAR_MAYOR_VALOR);
		String ocultarDatosAntiguoSistema = request.getParameter( CActuacionAdministrativa.OCULTAR_ANTIGUO_SISTEMA);

		if(ocultarSolicitud!=null && !ocultarSolicitud.equals("")){
			session.setAttribute(CActuacionAdministrativa.OCULTAR_SOLICITUD, ocultarSolicitud);
		}		
		if(ocultarDatosAntiguoSistema!=null && !ocultarDatosAntiguoSistema.equals("")){
			session.setAttribute(CActuacionAdministrativa.OCULTAR_ANTIGUO_SISTEMA , ocultarDatosAntiguoSistema);
		}
		if(ocultarMayorValor!=null && !ocultarMayorValor.equals("")){
			session.setAttribute(CActuacionAdministrativa.OCULTAR_MAYOR_VALOR , ocultarMayorValor);
		}		
		
		return null;

	}	
	
	
	//MANEJO DE LAS NOTAS Y ESTADOS PARA LA ACTUACIÓN ADMINISTRATIVA.	
	/**
	 * Permite guardar una nueva Nota a la actuación administrativa
	 * @param request
	 * @return
	 */
	private EvnActuacionAdministrativa agregarNotaActuacion(HttpServletRequest request)throws AccionWebException {

		this.preservarInfo(request);
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Usuario usuarioSir = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		ValidacionActuacionAdministrativaException exception = new ValidacionActuacionAdministrativaException();
		
		String estado = request.getParameter(CActuacionAdministrativa.ESTADO_ACTUACION);
		String nota = request.getParameter(CActuacionAdministrativa.NOTA_ACTUACION);
		
		if(estado==null || estado.equals("")){
			exception.addError("Debe ingresar el estado en que se encuentra la actuación administrativa.");
		}
		if(nota==null || nota.equals("")){
			exception.addError("Debe ingresar una nota con respecto al estado de la actuación administrativa.");
		}	
		if(exception.getErrores().size()>0){
			throw exception;	
		}
		
		NotaActuacion notaActuacion = new NotaActuacion();
		notaActuacion.setUsuario(usuarioSir);
		notaActuacion.setEstado(estado);		
		notaActuacion.setNota(nota);		
		notaActuacion.setFechaCreacion(new Date());

		//Construir nota que desea agregarse.		

		EvnActuacionAdministrativa evento = new EvnActuacionAdministrativa(usuarioAuriga,  EvnActuacionAdministrativa.AGREGAR_NOTA, turno ,usuarioSir );
		evento.setNotaActuacion(notaActuacion);
		return evento;
	}	
	
	
	/**
	 * Permite editar una nota de actuaciones administrativas
	 * @param request
	 * @return
	 */
	private EvnActuacionAdministrativa editarNotaActuacion(HttpServletRequest request)throws AccionWebException {

		this.preservarInfo(request);
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Usuario usuarioSir = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		ValidacionActuacionAdministrativaException exception = new ValidacionActuacionAdministrativaException();
		
		String item = request.getParameter(WebKeys.ITEM);
		int i = 0;
		if(item==null || item.equals("")){
			exception.addError("Seleccione la nota que desee actualizar");
		}
		
		String estado = request.getParameter(CActuacionAdministrativa.ESTADO_ACTUACION + item);
		String nota = request.getParameter(CActuacionAdministrativa.NOTA_ACTUACION + item);
		
		if(estado==null || estado.equals("")){
			exception.addError("Debe ingresar el estado en que se encuentra la actuación administrativa.");
		}
		if(nota==null || nota.equals("")){
			exception.addError("Debe ingresar una nota con respecto al estado de la actuación administrativa.");
		}	
		if(exception.getErrores().size()>0){
			throw exception;	
		}
		
		NotaActuacion notaActuacion = new NotaActuacion();
		notaActuacion.setEstado(estado);		
		notaActuacion.setNota(nota);
		notaActuacion.setIdNotaActuacion(item);		

		//Construir nota que desea actualizarse.		
		EvnActuacionAdministrativa evento = new EvnActuacionAdministrativa(usuarioAuriga,  EvnActuacionAdministrativa.EDITAR_NOTA, turno ,usuarioSir );
		evento.setNotaActuacion(notaActuacion);
		return evento;
	}	
	
	
	//COMPLETAR LOS OBJETOS DEL FOMULARIO
	/**
	* @param request
	* @param exception
	* @return
	*/
	private DatosAntiguoSistema getDatosAntiguoSistema(HttpServletRequest request, ValidacionActuacionAdministrativaException exception) {

		// Antiguo Sistema
		String libro_tipo_as = request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS);
		libro_tipo_as = (libro_tipo_as == null) ? "" : libro_tipo_as;

		String libro_numero_as = request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS);
		libro_numero_as = (libro_numero_as == null) ? "" : libro_numero_as;

		String libro_pagina_as = request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS);
		libro_pagina_as = (libro_pagina_as == null) ? "" : libro_pagina_as;

		String libro_ano_as = request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS);
		libro_ano_as = (libro_ano_as == null) ? "" : libro_ano_as;

		String tomo_numero_as = request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS);
		tomo_numero_as = (tomo_numero_as == null) ? "" : tomo_numero_as;

		String tomo_pagina_as = request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS);
		tomo_pagina_as = (tomo_pagina_as == null) ? "" : tomo_pagina_as;

		String tomo_municipio_as = request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS);
		tomo_municipio_as = (tomo_municipio_as == null) ? "" : tomo_municipio_as;

		String tomo_ano_as = request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS);
		tomo_ano_as = (tomo_ano_as == null) ? "" : tomo_ano_as;

		String documento_tipo_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS);
		documento_tipo_as = (documento_tipo_as == null) ? "" : documento_tipo_as;

		String documento_numero_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS);
		documento_numero_as = (documento_numero_as == null) ? "" : documento_numero_as;

		String documento_comentario_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);
		documento_comentario_as = (documento_comentario_as == null) ? "" : documento_comentario_as;

		String documento_fecha_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
		documento_fecha_as = (documento_fecha_as == null) ? "" : documento_fecha_as;

		String comentario_as = request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS);
		comentario_as = (comentario_as == null) ? "" : comentario_as;

		String oficina_depto_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
		oficina_depto_id_as = (oficina_depto_id_as == null) ? "" : oficina_depto_id_as;

		String oficina_depto_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
		oficina_depto_nom_as = (oficina_depto_nom_as == null) ? "" : oficina_depto_nom_as;

		String oficina_muni_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
		oficina_muni_id_as = (oficina_muni_id_as == null) ? "" : oficina_muni_id_as;

		String oficina_muni_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
		oficina_muni_nom_as = (oficina_muni_nom_as == null) ? "" : oficina_muni_nom_as;

		String oficina_vereda_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
		oficina_vereda_id_as = (oficina_vereda_id_as == null) ? "" : oficina_vereda_id_as;

		String oficina_vereda_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
		oficina_vereda_nom_as = (oficina_vereda_nom_as == null) ? "" : oficina_vereda_nom_as;

		String oficina_oficia_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
		oficina_oficia_id_as = (oficina_oficia_id_as == null) ? "" : oficina_oficia_id_as;
                    
                    /*
                     *  @author Carlos Torres
                     *  @chage   se agrega validacion de version diferente
                     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                     */
                String oficina_oficia_version_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION);
		oficina_oficia_version_as = (oficina_oficia_version_as == null) ? "0" : oficina_oficia_version_as;

		String oficina_oficia_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
		oficina_oficia_nom_as = (oficina_oficia_nom_as == null) ? "" : oficina_oficia_nom_as;

		DatosAntiguoSistema das = null;

		das = new DatosAntiguoSistema();

		StringBuffer sb = new StringBuffer("");
		StringBuffer sbDocAs = new StringBuffer("");
		StringBuffer sbOfiAs = new StringBuffer("");
		StringBuffer sbTipoDocAS = new StringBuffer("");
		Date docFecha = null;

		sb.append(libro_tipo_as);
		sb.append(libro_numero_as);
		sb.append(libro_pagina_as);
		sb.append(libro_ano_as);
		sb.append(tomo_numero_as);
		sb.append(tomo_pagina_as);
		sb.append(tomo_municipio_as);
		sb.append(tomo_ano_as);
		sb.append(documento_tipo_as);
		sb.append(documento_numero_as);
		sb.append(documento_comentario_as);
		sb.append(documento_fecha_as);
		sb.append(oficina_depto_id_as);
		sb.append(oficina_depto_nom_as);
		sb.append(oficina_muni_id_as);
		sb.append(oficina_muni_nom_as);
		sb.append(oficina_vereda_id_as);
		sb.append(oficina_vereda_nom_as);
		sb.append(oficina_oficia_id_as);
		sb.append(oficina_oficia_nom_as);

		if (documento_fecha_as.length() > 0) {

			try {
				docFecha = DateFormatUtil.parse(documento_fecha_as);
			} catch (ParseException e) {
				exception.addError("El campo Fecha del documento de " + "antiguo sistema no es válido");
			}
		}

		das.setLibroAnio(libro_ano_as);
		das.setLibroNumero(libro_numero_as);
		das.setLibroPagina(libro_pagina_as);
		das.setLibroTipo(libro_tipo_as);
		das.setTomoAnio(tomo_ano_as);
		das.setTomoMunicipio(tomo_municipio_as);
		das.setTomoNumero(tomo_numero_as);
		das.setTomoPagina(tomo_pagina_as);
		das.setComentario(comentario_as);

		Documento docAS = new Documento();
		TipoDocumento docTipo = new TipoDocumento();
		OficinaOrigen oficinaOrigenAS = new OficinaOrigen();
		Vereda veredaAS = new Vereda();

		// veredaAS.setNombre(nomVereda);
		veredaAS.setIdVereda(oficina_vereda_id_as);
		veredaAS.setIdDepartamento(oficina_depto_id_as);
		veredaAS.setIdMunicipio(oficina_muni_id_as);

		oficinaOrigenAS.setIdOficinaOrigen(oficina_oficia_id_as);
                 /*
                     *  @author Carlos Torres
                     *  @chage   se agrega validacion de version diferente
                     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                     */
                oficinaOrigenAS.setIdOficinaOrigen(oficina_oficia_version_as);
		oficinaOrigenAS.setNombre(oficina_oficia_nom_as);
		oficinaOrigenAS.setVereda(veredaAS);

		// docTipo.setNombre(documento_tipo_as);
		docTipo.setIdTipoDocumento(documento_tipo_as);

		docAS.setComentario(documento_comentario_as);
		docAS.setFecha(docFecha);
		docAS.setNumero(documento_numero_as);

		// docAS.setTipoDocumento(docTipo);
		sbDocAs.append(documento_comentario_as);
		sbDocAs.append(docFecha);
		sbDocAs.append(documento_numero_as);
		sbDocAs.append(documento_tipo_as);

		sbOfiAs.append(oficina_oficia_id_as);
		sbOfiAs.append(oficina_oficia_nom_as);

		sbTipoDocAS.append(documento_tipo_as);

		if (sbDocAs.length() > 0) {
			if (sbTipoDocAS.length() > 0) {
				das.setDocumento(docAS);
				docAS.setTipoDocumento(docTipo);

				if (sbOfiAs.length() > 0) {
					docAS.setOficinaOrigen(oficinaOrigenAS);
				}

			}

		}

		return das;

	}	
	
	//PRESERVAR INFORMACIÓN DE LA ACTUACIÓN ADMINISTRATIVA EN LA SESIÓN.
	
	/**
	 * @param request
	 */
	private void preservarInfo(HttpServletRequest request){
		preservarInfoActuacion( request );
		preservarInfoDatosAntiguoSistema( request );
		preservarInfoMayorValor( request );
	}
	
	
	/**
	 * @param request
	 */
	private void preservarInfoActuacion(HttpServletRequest request){
		if(request.getParameter(CActuacionAdministrativa.ESTADO_ACTUACION)!=null){
			request.getSession().setAttribute(CActuacionAdministrativa.ESTADO_ACTUACION,request.getParameter(CActuacionAdministrativa.ESTADO_ACTUACION));	
		}
		if(request.getParameter(CActuacionAdministrativa.NOTA_ACTUACION)!=null){
			request.getSession().setAttribute(CActuacionAdministrativa.NOTA_ACTUACION,request.getParameter(CActuacionAdministrativa.NOTA_ACTUACION));	
		}
		
	}

	
	/**
	 * @param request
	 */
	private void preservarInfoMayorValor(HttpServletRequest request){
		if(request.getParameter(CActuacionAdministrativa.MAYOR_VALOR_DERECHOS)!=null){
			request.getSession().setAttribute(CActuacionAdministrativa.MAYOR_VALOR_DERECHOS,request.getParameter(CActuacionAdministrativa.MAYOR_VALOR_DERECHOS));	
		}
		if(request.getParameter(CActuacionAdministrativa.MAYOR_VALOR_IMPUESTOS)!=null){
			request.getSession().setAttribute(CActuacionAdministrativa.MAYOR_VALOR_IMPUESTOS,request.getParameter(CActuacionAdministrativa.MAYOR_VALOR_IMPUESTOS));	
		}	
		if(request.getParameter(CActuacionAdministrativa.MAYOR_VALOR_JUSTIFICACION)!=null){
			request.getSession().setAttribute(CActuacionAdministrativa.MAYOR_VALOR_JUSTIFICACION,request.getParameter(CActuacionAdministrativa.MAYOR_VALOR_JUSTIFICACION));	
		}		
	}	
	

	
	/**
	 * @param request
	 */
	private void preservarInfoDatosAntiguoSistema(HttpServletRequest request) {
	
		HttpSession session = request.getSession();
		
		// Salvar informacion AntiguoSistema
		session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS, request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS));
		session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS, request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS));
		session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS, request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS));
		session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS, request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS));
		session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS, request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS));
		session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS, request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS));
		session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS, request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS));
		session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS, request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS));
	
		// ------------------------------------ datos del documento
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_COMBO_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_COMBO_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS));
	
		// ------------------------ datos de documento.oficinaorigen
	
		// i.
	
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS));
	
		// ii.
	
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM));
	
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_NOM_OFICINA_HIDDEN, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_NOM_OFICINA_HIDDEN));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA_HIDDEN, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA_HIDDEN));
                /*
                     *  @author Carlos Torres
                     *  @chage   se agrega validacion de version diferente
                     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                     */
                session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUMERO_OFICINA_HIDDEN, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUMERO_OFICINA_HIDDEN));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_OFICINA_HIDDEN, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_OFICINA_HIDDEN));
	
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO));
	
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS));
		session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS, request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS));
	
		// ------- (comentario)
		session.setAttribute(CDatosAntiguoSistema.COMENTARIO_AS, request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS));
	
		// ------------------------------------ libro
		session.setAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS, request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS));
		session.setAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS, request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS));
		session.setAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS, request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS));
		session.setAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS, request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS));
	
		// ------------------------------------ tomo
		session.setAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS, request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS));
		session.setAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS, request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS));
		session.setAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS, request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS));
		session.setAttribute(CDatosAntiguoSistema.TOMO_ANO_AS, request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS));
	
	}



	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespActuacionAdministrativa ev =
			(EvnRespActuacionAdministrativa) evento;

		if (ev != null) {
			if (ev.getTipoEvento().equals(EvnRespActuacionAdministrativa.AGREGAR_NOTAS)) {
				if(ev.getTurno()!=null){
					request.getSession().setAttribute(WebKeys.TURNO, ev.getTurno());
				}
			}
			if (ev.getTipoEvento().equals(EvnRespActuacionAdministrativa.EDITAR_NOTAS)) {
				if(ev.getTurno()!=null){
					request.getSession().setAttribute(WebKeys.TURNO, ev.getTurno());
				}
			}
			

		}

	}
}

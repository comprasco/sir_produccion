package gov.sir.core.web.acciones.antiguosistema;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.antiguosistema.EvnAntiguoSistema;
import gov.sir.core.eventos.antiguosistema.EvnRespAntiguoSistema;
import gov.sir.core.eventos.antiguosistema.EvnResp_AntigSistHojaRutaSaveInfo;
import gov.sir.core.eventos.antiguosistema.Evn_AntigSistHojaRutaSaveInfo;
import gov.sir.core.eventos.certificado.EvnCertificado;
import gov.sir.core.eventos.certificado.EvnRespCertificado;
import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.eventos.correccion.Evn_CorrSimpleMain_VerAlertasOptions;
import gov.sir.core.eventos.registro.EvnConsultaFolio;
import gov.sir.core.eventos.registro.EvnFolio;
import gov.sir.core.eventos.registro.EvnRespConsultaFolio;
import gov.sir.core.eventos.registro.EvnRespFolio;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Cancelacion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.LLavesMostrarFolioHelper;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoAnotacion;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoFolio;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CDireccion;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CSalvedadAnotacion;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRegistro;
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.core.negocio.modelo.constantes.CTipoDocumento;
import gov.sir.core.negocio.modelo.constantes.CTipoPredio;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ErrorCreacionFolioException;
import gov.sir.core.web.acciones.excepciones.ErrorCrearAnotacionException;
import gov.sir.core.web.acciones.excepciones.ErrorEdicionAnotacionException;
import gov.sir.core.web.acciones.excepciones.ErrorEdicionFolioException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionAnotacionesCancelacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosAgregarNotaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCancelacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoSegregacionCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosHojaRutaException;
import gov.sir.core.web.acciones.registro.AWCalificacion;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.MostrarFolioHelper;
import gov.sir.core.web.helpers.comun.ValidacionDatosOficinaOrigen;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * @author jfrias
 *
 */
public class AWAntiguoSistema extends SoporteAccionWeb {

    //ACCIONES DE LA FASE DE REVISION INICIAL
    public static final String AVANZAR_APROBAR_REVISION_INICIAL="AVANZAR_APROBAR_REVISION_INICIAL";
    public static final String AVANZAR_NEGAR_REVISION_INICIAL="AVANZAR_NEGAR_REVISION_INICIAL";
    public static final String AVANZAR_EXISTENTE_REVISION_INICIAL="AVANZAR_EXISTENTE_REVISION_INICIAL";
    
    //ACCIONES DE LA FASE DE HOJA DE RUTA
    public static final String AVANZAR_APROBAR_HOJA_RUTA="AVANZAR_APROBAR_HOJA_RUTA";
    public static final String AVANZAR_DEVOLVER_REVISION_INICIAL="AVANZAR_DEVOLVER_REVISION_INICIAL";
    public static final String AVANZAR_FOLIO_RECHAZADO="AVANZAR_FOLIO_RECHAZADO";
    public static final String AVANZAR_FOLIO_MAS_DOCS="AVANZAR_FOLIO_MAS_DOCS";
    
    public static final String EDITAR_FOLIO="EDITAR_FOLIO";
    public static final String ELIMINAR_FOLIO="ELIMINAR_FOLIO";
    public static final String DESASOCIAR_FOLIO="DESASOCIAR_FOLIO";
    public static final String IMPRIMIR_FOLIO="IMPRIMIR_FOLIO";
    
    public static final String DIGITAR_HOJA_RUTA="DIGITAR_HOJA_RUTA";
    public static final String CREAR_FOLIO="CREAR_FOLIO";
    public static final String TERMINAR_FOLIO="TERMINAR_FOLIO";
    public static final String REGRESAR_FOLIO="REGRESAR_FOLIO";
    public static final String ASOCIAR_FOLIO="ASOCIAR_FOLIO";
    public static final String REFRESCAR_ANOTACION="REFRESCAR_ANOTACION";
    public static final String AGREGAR_DIRECCION="AGREGAR_DIRECCION";
    public static final String ELIMINAR_DIRECCION="ELIMINAR_DIRECCION";
    public static final String AGREGAR_ANOTACION="AGREGAR_ANOTACION";
    public static final String AGREGAR_MATRICULA_ASO="AGREGAR_MATRICULA_ASO";
    public static final String BORRAR_MATRICULA_ASO="BORRAR_MATRICULA_ASO";
    
    public static final String AGREGAR_MATRICULA_ASO_EDIT="AGREGAR_MATRICULA_ASO_EDIT";
    public static final String BORRAR_MATRICULA_ASO_EDIT="BORRAR_MATRICULA_ASO_EDIT";
    
    public static final String ELIMINAR_ANOTACION="ELIMINAR_ANOTACION";
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS="AGREGAR_1_REGISTRO_TABLA_CIUDADANOS";
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS="REMOVER_1_REGISTRO_TABLA_CIUDADANOS";
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION="AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION";
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION="REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION";
    public static final int DEFAULT_NUM_CIUDADANOS_TABLA = 2;
    public static final String NUM_REGISTROS_TABLA_CIUDADANOS="NUM_REGISTROS_TABLA_CIUDADANOS";
    public static final String NUM_REGISTROS_TABLA_MATRICULAS="NUM_REGISTROS_TABLA_MATRICULAS";    
    public static final String NUM_REGISTROS_TABLA_MATRICULAS_EDIT="NUM_REGISTROS_TABLA_MATRICULAS_EDIT";
    public static final String VALIDAR_CIUDADANO="VALIDAR_CIUDADANO";
    public static final String GRABAR_EDICION_ANOTACION="GRABAR_EDICION_ANOTACION";
    public static final String CANCELAR_EDICION_ANOTACION="CANCELAR_EDICION_ANOTACION";
    
    public static final String TERMINAR_EDITAR_FOLIO="TERMINAR_EDITAR_FOLIO";
    public static final String TERMINAR_EDITAR_FOLIO_ANOTACIONES="TERMINAR_EDITAR_FOLIO_ANOTACIONES";
    public static final String EDITAR_ANOTACIONES="EDITAR_ANOTACIONES";
    public static final String EDITAR_ANOTACIONES_FOLIO="EDITAR_ANOTACIONES_FOLIO";
    public static final String EDITAR_ANOTACION="EDITAR_ANOTACION";
    public static final String VALIDAR_CIUDADANO_EDICION="VALIDAR_CIUDADANO_EDICION";
    public static final String ANIO_VALIDACION_CANCELACION = "ANIO_VALIDACION_CANCELACION";
   
    public static final String EDICION="EDICION";
    public static final String EDICION_ANOTACIONES="EDICION_ANOTACIONES";
    public static final String CREACION="CREACION";
    
    public static final String TERMINAR_EDICION_FOLIO="TERMINAR_EDICION_FOLIO";
    public static final String CARGAR_DESCRIPCION_NATURALEZA_CREAR_FOLIO="CARGAR_DESCRIPCION_NATURALEZA_CREAR_FOLIO";
    
    public static final String CONSULTA_FOLIO_COMPLEMENTACION="CONSULTA_FOLIO_COMPLEMENTACION";
    
    public static final String COMENZAR_CANCELAR_ANOTACION = "COMENZAR_CANCELAR_ANOTACION";
	public static final String CANCELAR_ANOTACION = "CANCELAR_ANOTACION";
    public static final String PRESERVAR_INFO_CANCELACION = "PRESERVAR_INFO_CANCELACION";
    public static final String GUARDAR_ANOTACIONES_TEMPORALES = "GUARDAR_ANOTACIONES_TEMPORALES";
    public static final String AGREGAR_VARIOS_CIUDADANOS_CANCELACION = "AGREGAR_VARIOS_CIUDADANOS_CANCELACION";
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION";
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION";
    public static final String ELIMINAR_CIUDADANO_ANOTACION_CANCELACION = "ELIMINAR_CIUDADANO_ANOTACION_CANCELACION";
    public static final String VALIDAR_CIUDADANO_CANCELACION = "VALIDAR_CIUDADANO_CANCELACION";
    public static final String CANCELAR_CANCELACION = "CANCELAR_CANCELACION";
    public static final String REFRESCAR_EDICION = "REFRESCAR_EDICION";
    public static final String AVANZAR_CORRECCIONES = "AVANZAR_CORRECCIONES";
    public static final String AVANZAR_MAYOR_VALOR = "AVANZAR_MAYOR_VALOR";
    
    //ACCIONES DE LA FASE DE CREACION DE FOLIOS
    public static final String AVANZAR_APROBAR_CREACION_FOLIO="AVANZAR_APROBAR_CREACION_FOLIO";
    public static final String AVANZAR_NEGAR_CREACION_FOLIO="AVANZAR_NEGAR_CREACION_FOLIO";
    
    //ACCIONES DE LA FASE DE REVISION FINAL 
    public static final String REVISION_FINAL_CREADO="REVISION_FINAL_CREADO";
    public static final String REVISION_FINAL_EXISTE="REVISION_FINAL_EXISTE";
    public static final String REVISION_FINAL_RECHAZADO="REVISION_FINAL_RECHAZADO";
    public static final String REVISION_FINAL_MAS_DOCS="REVISION_FINAL_MAS_DOCS";
    public static final String REVISION_FINAL_REANALIZAR="REVISION_FINAL_REANALIZAR";
    
	public static final String COMENTARIO_NEGAR="COMENTARIO_NEGAR";
	public static final String PAGO_MAYOR_VALOR_TOTAL = "PAGO_MAYOR_VALOR_TOTAL";
	 /**
     * Constante que identifica que se quiere enviar por pago mayor valor
     */
    public final static String RAZON_MAYOR_VALOR = "RAZON_MAYOR_VALOR";
    
    
    private String accion;
	
    /**  */
    public AWAntiguoSistema() {
        super();
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.acciones.AccionWeb#perform(javax.servlet.http.HttpServletRequest)
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);
                
        if ((accion == null) || (accion.length() == 0)) {
            throw new AccionWebException("Debe indicar una accion");
        }
        
        if (accion.equals(AVANZAR_APROBAR_REVISION_INICIAL)){
            return avanzarAprobarRevisionInicial(request);
        }else if(accion.equals(AVANZAR_NEGAR_REVISION_INICIAL)){
            return avanzarNegarRevisionInicial(request);
        }else if(accion.equals(AVANZAR_EXISTENTE_REVISION_INICIAL)){
            return avanzarExistenteRevisionInicial(request);
        }else if(accion.equals(AVANZAR_APROBAR_HOJA_RUTA)){
            return avanzarAprobarHojaRuta(request);
        }else if(accion.equals(AVANZAR_DEVOLVER_REVISION_INICIAL)){
            return avanzarDevolverRevisionInicial(request);
        }else if(accion.equals(AVANZAR_APROBAR_CREACION_FOLIO)){
            return avanzarAprobarCreacionFolio(request);
        }else if(accion.equals(AVANZAR_NEGAR_CREACION_FOLIO)){
            return avanzarNegarCreacionFolio(request);
        }else if(accion.equals(EDITAR_FOLIO)){
            return editarFolio(request);
        }else if(accion.equals(ELIMINAR_FOLIO)){
            return eliminarFolio(request);
        }else if(accion.equals(DESASOCIAR_FOLIO)){
            return desasociarFolio(request);
        }else if(accion.equals(IMPRIMIR_FOLIO)){
            return imprimirFolio(request);
        }else if(accion.equals(REVISION_FINAL_CREADO)){
            return avanzarRevisionFinalCreado(request);
        }else if(accion.equals(REVISION_FINAL_EXISTE)){
            return avanzarRevisionFinalExiste(request);
        }else if(accion.equals(REVISION_FINAL_MAS_DOCS)){
            return avanzarRevisionFinalMasDocs(request);
        }else if(accion.equals(REVISION_FINAL_REANALIZAR)){
            return avanzarRevisionFinalReanalizar(request);
        }else if(accion.equals(REVISION_FINAL_RECHAZADO)){
            return avanzarRevisionFinalRechazado(request);
        }else if(accion.equals(AVANZAR_FOLIO_RECHAZADO)){
            return avanzarRechazado(request);
        }else if(accion.equals(AVANZAR_FOLIO_MAS_DOCS)){
            return avanzarMasDocumentos(request);
        }else if(accion.equals(DIGITAR_HOJA_RUTA)){
            return digitarHojaRuta(request);
        }else if(accion.equals(ASOCIAR_FOLIO)){
            return asociarFolio(request);
        }else if(accion.equals(CREAR_FOLIO)){
            return crearFolio(request);
        }else if(accion.equals(REGRESAR_FOLIO)){
            return regresarFolio(request);
        }else if(accion.equals(TERMINAR_FOLIO)){
            return terminarFolio(request);
        }else if(accion.equals(AGREGAR_DIRECCION)){
            return agregarDireccion(request);
        }else if(accion.equals(ELIMINAR_DIRECCION)){
            return eliminarDireccion(request);
        }else if(accion.equals(CONSULTA_FOLIO_COMPLEMENTACION)){
            return consultaFolioComplementacion(request);
        }else if(accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS)||accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_EDICION)){
            return aumentarNumeroVariosCiudadanos(request);
        }else if(accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS)){
            return disminuirNumeroVariosCiudadanos(request);
        }else if(accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS_EDICION)){
        	return disminuirNumeroVariosCiudadanosEdicion(request);
        }else if(accion.equals(VALIDAR_CIUDADANO)||accion.equals(VALIDAR_CIUDADANO_EDICION)){
            return validarCiudadano(request);
        }else if (accion.equals(AGREGAR_ANOTACION)){        	
            return agregarAnotacion(request);
        }else if (accion.equals(AGREGAR_MATRICULA_ASO)){
        	return aumentarNumeroMatriculas(request);
        }else if (accion.equals(BORRAR_MATRICULA_ASO)){
        	return disminuirNumeroMatricula(request);
        }else if (accion.equals(AGREGAR_MATRICULA_ASO_EDIT)){
        	return aumentarNumeroMatriculasE(request);
        }else if (accion.equals(BORRAR_MATRICULA_ASO_EDIT)){
        	return disminuirNumeroMatriculaE(request);   
        }else if (accion.equals(TERMINAR_EDITAR_FOLIO)){
            return terminarEditarFolio(request);
        }else if (accion.equals(EDITAR_ANOTACIONES_FOLIO)) {
        	return editarAnotacionesFolio(request);
        }else if (accion.equals(EDITAR_ANOTACIONES)){
            return editarAnotaciones(request);
        }else if (accion.equals(EDITAR_ANOTACION)){
            return editarAnotacion(request);
        }else if (accion.equals(GRABAR_EDICION_ANOTACION)){
            return grabarEdicionAnotacion(request);
        }else if (accion.equals(CANCELAR_EDICION_ANOTACION)){
            return cancelarEdicionAnotacion(request);
        }else if (accion.equals(TERMINAR_EDITAR_FOLIO_ANOTACIONES)){
            return terminarEditarFolioAnotaciones(request);
        }else if (accion.equals(ELIMINAR_ANOTACION)){
            return eliminarAnotacion(request);
        }else if (accion.equals(CARGAR_DESCRIPCION_NATURALEZA_CREAR_FOLIO)){
            return cargarNaturaleza(request);
        }else if (accion.equals(REFRESCAR_ANOTACION)){
            return refrescarAnotacion(request);
		}else if (accion.equals(COMENZAR_CANCELAR_ANOTACION)){
			return consultarFolio(request);
		}else if (accion.equals(PRESERVAR_INFO_CANCELACION)){
			return preservarInfoCancelacion(request);
		}else if (accion.equals(GUARDAR_ANOTACIONES_TEMPORALES)){
			return guardarAnotaciones(request);
		}else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS_CANCELACION)) {
				return agregarVariosCiudadanos(request,accion);
		}else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION)) {
				return aumentarNumeroVariosCiudadanosCancelacion(request);
		}else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS_CANCELACION)) {
				return disminuirNumeroVariosCiudadanosCancelacion(request);
		}else if (accion.equals(ELIMINAR_CIUDADANO_ANOTACION_CANCELACION)) {
			return eliminarCiudadano(request);
		}else if (accion.equals(VALIDAR_CIUDADANO_CANCELACION)) {
			return validarCiudadano(request);
		}else if (accion.equals(CANCELAR_ANOTACION)) {
			return cancelarAnotacion(request);
		} else if (accion.equals(CANCELAR_CANCELACION)) {
			return cancelarCancelacion(request);
		} else if (accion.equals(REFRESCAR_EDICION)) {
			return refrescarAnotacionEdicion(request);
		} else if(accion.equals(AVANZAR_CORRECCIONES)) {
			return avanzarCorrecciones(request);
		}else if (accion.equals(AVANZAR_MAYOR_VALOR)) {
			return avanzarMayorValor(request);
		}else{
            throw new AccionInvalidaException("La acciï¿½n " + accion + " no es vï¿½lida.");
        }
    }
    
    private Evento editarAnotacionesFolio(HttpServletRequest request) {

    	request.getSession().setAttribute(WebKeys.ACCION_SECUNDARIA, EDICION_ANOTACIONES);
    	
    	String matricula=request.getParameter(WebKeys.ITEM);
        Turno turno=(Turno)request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg=(Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        
        Folio folio = null;
        Iterator itSolFolios = (turno.getSolicitud().getSolicitudFolios()).iterator();
        while(itSolFolios.hasNext()){
            SolicitudFolio sol = (SolicitudFolio)itSolFolios.next();
            if(sol.getIdMatricula().equals(matricula)){
                folio = sol.getFolio();
            }
        }
        EvnAntiguoSistema ev= new EvnAntiguoSistema(usuarioAuriga, EvnAntiguoSistema.HOJA_RUTA_CONSULTAR_FOLIO_EDICION_ANOTACIONES, folio);
        ev.setTurno(turno);
        ev.setUsuarioNeg(usuarioNeg);
        return ev;
	}

	private Evento avanzarMayorValor(HttpServletRequest request) throws AccionWebException {

		ValidacionParametrosException exception= new ValidacionParametrosException();
		//Obtener los datos para la liquidacion
		this.preservarInfoMayorValor(request);
		String valor = request.getParameter(AWAntiguoSistema.PAGO_MAYOR_VALOR_TOTAL);
		String razonMayorValor =  request.getParameter(AWAntiguoSistema.RAZON_MAYOR_VALOR);
		Turno turno= (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
		Usuario usuario =(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = 	(org.auriga.core.modelo.transferObjects.Usuario)
			request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		
		double nvalor = 0;
		
		if(valor == null || valor.equals("")){
		    exception.addError("El valor del pago no puede ser vacï¿½o");
		}
		if(razonMayorValor == null || razonMayorValor.equals("")) {
	        	exception.addError("Debe ingresar una justificaciï¿½n para el mayor valor");
	    }
		if(valor != null){
			if(valor.equals(".00")||valor.equals("0")){
				exception.addError("El valor del pago no puede ser 0");
			}
			valor = valor.replaceAll(",","");
		}
		try{
		    nvalor = Double.parseDouble(valor);
		}catch(NumberFormatException e){
		    exception.addError("El valor del pago es invï¿½lido");
		}

		if(exception.getErrores().size()>0){
		    throw exception;
		}

		LiquidacionTurnoCertificado liquidacionAnterior = 
			(LiquidacionTurnoCertificado)turno.getSolicitud().getLiquidaciones().get(turno.getSolicitud().getLiquidaciones().size() - 1);
		
		//crear liquidacion a partir de los datos
		LiquidacionTurnoCertificado liq = new LiquidacionTurnoCertificado();
		liq.setFecha(new Date());
		liq.setUsuario(usuario);
		liq.setJustificacionMayorValor(razonMayorValor);
		liq.setValor(nvalor);
		liq.setTipoCertificado(liquidacionAnterior.getTipoCertificado());

		//crear e inicializar el evento
		EvnAntiguoSistema evento= new EvnAntiguoSistema(usuarioAuriga, EvnAntiguoSistema.AVANZAR_MAYOR_VALOR, 
				turno, fase, usuario, CRespuesta.MAYOR_VALOR);
		evento.setLiquidacion(liq);

		return evento;

	}
    
	/*private Evento avanzarMayorValor(HttpServletRequest request) {

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =(org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(WebKeys.USUARIO_AURIGA);
		Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
		
		EvnAntiguoSistema evento = new EvnAntiguoSistema(usuarioAuriga, EvnAntiguoSistema.AVANZAR_MAYOR_VALOR, 
				turno, fase, usuario, CRespuesta.MAYOR_VALOR);
		
		return evento;
	}*/

	private Evento avanzarCorrecciones(HttpServletRequest request) throws AccionWebException {

		ValidacionParametrosException exception= new ValidacionParametrosException();
		
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =(org.auriga.core.modelo.transferObjects.Usuario)request.getSession().getAttribute(WebKeys.USUARIO_AURIGA);
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
			
		
		EvnAntiguoSistema evento = new EvnAntiguoSistema(usuarioAuriga, EvnAntiguoSistema.AVANZAR_CORRECCIONES);
		evento.setTurno(turno);
		evento.setUsuarioNeg(usuario);
		evento.setFase(fase);
				
		return evento;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento cancelarCancelacion(HttpServletRequest request) {
		eliminarInfoBasicaAnotacion(request);
		eliminarInfoBasicaVariosCiudadanos(request);
		request.getSession().removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
		return null;
	}

    
	/**
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento cancelarAnotacion(HttpServletRequest request) throws AccionWebException{
		preservarInfoCancelacion(request);
		ValidacionParametrosCancelacionException exception = new ValidacionParametrosCancelacionException();
		//List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
		Folio folio=(Folio)request.getSession().getAttribute(WebKeys.FOLIO);
		List anotaciones = (List)request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_DEFINITIVAS_PAGINA);
		List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
		Usuario usuarioNeg=(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		String[] idsCanc = (String[])request.getSession().getAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
		/*if (idsCanc==null){
			exception.addError("Debe seleccionar por lo menos anotaciï¿½n a cancelar");
		}*/
		if (anotaciones == null) {
			anotaciones = new Vector();
		}
		List canceladas = new ArrayList();
		Iterator itC=anotaciones.iterator();
		int c=anotaciones.size();
		while(itC.hasNext()){
			Anotacion an=(Anotacion)itC.next();
			for(int i = 0;i<idsCanc.length;i++){
				if (an.getIdAnotacion().equals(idsCanc[i])){
					canceladas.add(an);
					//break;
				}
			}
			c--;
		}

		/*if(cancelada==null){
			exception.addError("Debe escoger una anotaciï¿½n a cancelar.");
		}*/

		String valFechaRadicacion = request.getParameter(CFolio.ANOTACION_FECHA_RADICACION);

		Calendar fechaRadicacion = Calendar.getInstance();
		if (valFechaRadicacion!=null){
			fechaRadicacion = darFecha(valFechaRadicacion);
			if (fechaRadicacion == null) {
				exception.addError("La fecha de la radicacion en la anotaciï¿½n es invï¿½lida");
			}
		}



		//Se hacen las validaciones si se requiere validar los campos del Documento, para el caso de correcciones por ejemplo.
		Documento documento = null;
		if(String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_CORRECCIONES)){
			String tipoDocumento = request.getParameter(AWModificarFolio.ANOTACION_TIPO_DOCUMENTO);
			if ((tipoDocumento == null) || tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
				exception.addError("Debe escoger un tipo para el documento de la anotaciï¿½n");
			}

			String numDocumento = request.getParameter(AWModificarFolio.ANOTACION_NUM_DOCUMENTO);

			if ((numDocumento == null) || numDocumento.equals("")) {
				exception.addError("El valor del nï¿½mero del documento en la anotaciï¿½n es invï¿½lido");
			}

			String valFechaDocumento = request.getParameter(AWModificarFolio.ANOTACION_FECHA_DOCUMENTO);
			if ((valFechaDocumento == null) || valFechaDocumento.equals("")) {
				exception.addError("La fecha del documento en la anotaciï¿½n no puede ser vacia");
			}

			Calendar fechaDocumento = darFecha(valFechaDocumento);
			if (fechaDocumento == null) {
				exception.addError("La fecha del documento en la anotaciï¿½n es invï¿½lida");
			}

			String valorDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
			String nomDepartamento = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);

			if ((valorDepartamento.length() <= 0)) {
				exception.addError("Debe seleccionar un departamento vï¿½lido para la oficina de procedencia del documento el la anotaciï¿½n");
			}

			String valorMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
			String nomMunicipio = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);

			if ((valorMunicipio.length() <= 0)) {
				exception.addError("Debe seleccionar un municipio vï¿½lido para la oficina de procedencia del documento el la anotaciï¿½n");
			}

			String valorVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
			if (( valorVereda == null || (valorVereda.trim().equals(""))) || nomDepartamento.equals("") || nomMunicipio.equals("") ) {
				valorVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS), valorDepartamento, valorMunicipio);
			}

			String nomVereda = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);

			if ((valorVereda.length() <= 0)) {
				exception.addError("Debe seleccionar una vereda vï¿½lida para la oficina de procedencia del documento el la anotaciï¿½n");
			}


			String numOficina = new String();
			String nomOficina = new String();
			String idOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
                         /*
                         *  @author Carlos Torres
                         *  @chage   se agrega validacion de version diferente
                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
                         */
                        String version = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
			if ((idOficina == null) || (idOficina.length() <= 0)) {
				exception.addError("Debe seleccionar una entidad pï¿½blica vï¿½lida asociada al reparto notarial");
			} else {
				nomOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
				numOficina = request.getParameter(AWModificarFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
			}


			if(exception.getErrores().size()<=0){
				documento = new Documento();
				TipoDocumento tipoDoc = new TipoDocumento();

				Iterator itTiposDocs = ((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO)).iterator();
				while (itTiposDocs.hasNext()) {
					ElementoLista elemento = (ElementoLista) itTiposDocs.next();
					if (elemento.getId().equals(tipoDocumento)) {
						tipoDoc.setNombre(elemento.getValor());
					}
				}
				tipoDoc.setIdTipoDocumento(tipoDocumento);
				documento.setFecha(fechaDocumento.getTime());
				documento.setNumero(numDocumento);

				OficinaOrigen oficina = new OficinaOrigen();
				oficina.setIdOficinaOrigen(idOficina);
				oficina.setNumero(numOficina);
				oficina.setNombre(nomOficina);
                                /*
                                 *  @author Carlos Torres
                                 *  @chage   se agrega validacion de version diferente
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
                                 */
                                oficina.setVersion(Long.parseLong(version));
				Vereda vereda = new Vereda();
				vereda.setIdDepartamento(valorDepartamento);
				vereda.setIdMunicipio(valorMunicipio);
				vereda.setIdVereda(valorVereda);
				vereda.setNombre(nomVereda);
				oficina.setVereda(vereda);
				documento.setTipoDocumento(tipoDoc);
				documento.setOficinaOrigen(oficina);
			}
		}

		//Fin Validaciï¿½n Campos del Documento



		String numEspecificacion = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);
		if(numEspecificacion!=null){
			numEspecificacion=numEspecificacion.replaceAll(",","");
		}
		double valorEspecificacion= 0.0d;
		if(numEspecificacion==null){
			numEspecificacion="0";
		}


		String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
		String nomNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
                 /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
         * @change     : 
	 */
		NaturalezaJuridica natJuridica = null;
                if ((numEspecificacion != null) && !numEspecificacion.equals("")) {

			if (idNaturaleza == null || idNaturaleza.length() <= 0) {
				exception.addError("Debe seleccionar una naturaleza jurï¿½dica para la especificaciï¿½n en la anotaciï¿½n");
			}

			//validacion de la idNaturalezaJuridica
			boolean esta=false;
			List grupoNaturalezas = (List) request.getSession().getAttribute("listanat");
			Iterator ig= grupoNaturalezas.iterator();
			while(ig.hasNext()){
				GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) ig.next();
				if(group.getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.ID_CANCELACION)){
					List natus= group.getNaturalezaJuridicas();
					Iterator id= natus.iterator();
                                         /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
         * @change     : Se agrega condicion al while
	 */
					while(id.hasNext() && !esta){
						NaturalezaJuridica nat = (NaturalezaJuridica)id.next();
						if(nat.getIdNaturalezaJuridica().equals(idNaturaleza)){
                                                     /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
         * @change     : Se asigna la variable natJuridica
	 */
                                                        natJuridica = nat;
							esta=true;
						}
					}
				}
			}
			if(!esta){
				exception.addError("Debe colocar un codigo de naturaleza jurï¿½dica vï¿½lido");
			}
			request.getSession().removeAttribute("listanat");
		} else {
			exception.addError("Debe seleccionar una naturaleza jurï¿½dica para la especificaciï¿½n en la anotaciï¿½n");
		}

		String comentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);




		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Anotacion anotacion = new Anotacion();
		int pos = anotaciones.size() + 1;
		anotacion.setOrden(String.valueOf(pos));

		//anotacion.setFechaRadicacion(fechaRadicacion.getTime());
		if (turno != null) {
			anotacion.setNumRadicacion(turno.getIdWorkflow());
			anotacion.setIdWorkflow(turno.getIdWorkflow());
			anotacion.setFechaRadicacion(turno.getFechaInicio());
		}


		//Se valida si se ingresa un nuevo documento para el caso de correcciones o si se ingresa el de la solicitud(registro)
		/*if(String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_CORRECCIONES)){
			anotacion.setDocumento(documento);
		}else{*/
			anotacion.setDocumento(((SolicitudRegistro)turno.getSolicitud()).getDocumento());
		//}


		NaturalezaJuridica naturalezaJuridica = new NaturalezaJuridica();
		if ((numEspecificacion != null) && !numEspecificacion.equals("")) {
			try{
				valorEspecificacion= Double.parseDouble(numEspecificacion);
				anotacion.setValor(valorEspecificacion);
			}catch(NumberFormatException e){
				ValidacionParametrosCancelacionException ex = new ValidacionParametrosCancelacionException();
				ex.addError("El valor de la naturaleza es invï¿½lido.");
				throw ex;
			}
		}

		naturalezaJuridica.setIdNaturalezaJuridica(idNaturaleza);
                 /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
         * @change     : Retorna Anotacion temporar.
	 * @throws     : DAOException.
	 */
                naturalezaJuridica.setVersion(natJuridica.getVersion());
		if (nomNatJuridica!=null){
			naturalezaJuridica.setNombre(nomNatJuridica);
		}



		anotacion.setNaturalezaJuridica(naturalezaJuridica);
		anotacion.setComentario(comentario);

		//segmento de ingresar ciudadanos
		if (ciudadanos != null) {
			Iterator itPersonas = ciudadanos.iterator();
			while (itPersonas.hasNext()) {
				AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
				ciudadano.setAnotacion(anotacion);
				//ciudadano.setIdMatricula("idMatricula");
				anotacion.addAnotacionesCiudadano(ciudadano);
			}
		}
		//fin : segmento de ingresar ciudadanos

		Iterator itCanceladas = canceladas.iterator();
		while(itCanceladas.hasNext()){
			Cancelacion cancelacion = new Cancelacion();
			cancelacion.setCancelada((Anotacion)itCanceladas.next());
			cancelacion.setCanceladora(anotacion);
			anotacion.addAnotacionesCancelacion(cancelacion);
		}
		//anotacion.setOrden(anotacion.getIdAnotacion());
		eliminarInfoBasicaAnotacion(request);
		request.getSession().removeAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION);
		TipoAnotacion tipo=new TipoAnotacion();
		tipo.setIdTipoAnotacion(CTipoAnotacion.CANCELACION);
		anotacion.setTipoAnotacion(tipo);

		EvnAntiguoSistema evento = new EvnAntiguoSistema(usuarioAuriga,folio,anotacion,usuarioNeg);
		evento.setTurno(turno);

		return evento;

	}
    
	/**
	 * Valida un ciudadano en anotaciï¿½n, recibe el request, crea el objeto ciudadano y lanza un evento
	 * de negocio
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento validarCiudadano(HttpServletRequest request) throws AccionWebException {
		//eliminarInfoBasicaAnotacion(request);

		//Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioArq= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		ValidacionParametrosCiudadanoCalificacionException exception = new ValidacionParametrosCiudadanoCalificacionException();
		String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);
		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		preservarInfoBasicaAnotacion(request);
		preservarInfoBasicaVariosCiudadanos(request);
		preservarInfoCancelacion(request);
		preservarInfoAntiguoSistemaAnotacion(request);

		String tipoDoc = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA+ver);

		if ((tipoDoc == null) || tipoDoc.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe escoger un tipo de identificaciï¿½n para la persona en la anotaciï¿½n");
		}

		String numDoc = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA+ver);
		if ((numDoc == null) || numDoc.equals("")) {
			exception.addError("Debe digitar un nï¿½mero de identificaciï¿½n");
		}

		if(!exception.getErrores().isEmpty()){
			preservarInfo(request);
			preservarInfoBasicaCiudadano(request);
			throw exception;
		}

		request.getSession().setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA+ver, numDoc);
		request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

		Ciudadano ciud = new Ciudadano();
		ciud.setDocumento(numDoc);
		ciud.setTipoDoc(tipoDoc);
		
       	//Se setea el circulo del ciudadano
        Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		ciud.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);

		//return new EvnCiudadano(usuarioArq, EvnCiudadano.CONSULTAR_CIUDADANO_ANOTACION, ciud);
		EvnCiudadano evnCiu = new EvnCiudadano(usuarioArq, EvnCiudadano.CONSULTAR_CIUDADANO_ANOTACION, ciud);
		if (turno!=null){
			if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCIONES) ) {
				evnCiu.setCorrecciones(true);
			} else {
				if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_REGISTRO) ) {
					evnCiu.setRegistro(true);
				} 
			}
		}
			
		return evnCiu;
	}    
    
	/**
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento eliminarCiudadano(HttpServletRequest request) throws AccionWebException {
		preservarInfoBasicaFolio(request);
		preservarInfoBasicaAnotacion(request);
		preservarInfoCancelacion(request);
		preservarInfoBasicaVariosCiudadanos(request);

		List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

		if (ciudadanos == null) {
			ciudadanos = new Vector();
		}

		try {
			int aplicacionNumero = Integer.parseInt(request.getParameter(AWCalificacion.POSICION));
			ciudadanos.remove(aplicacionNumero);
		} catch (NumberFormatException e) {
			throw new ParametroInvalidoException("El nï¿½mero del documento a eliminar es invï¿½lido");
		} catch (ArrayIndexOutOfBoundsException e) {
			if (ciudadanos.size() == 0) {
				throw new ParametroInvalidoException("La lista es vacï¿½a");
			}
			throw new ParametroInvalidoException("El ï¿½ndice del documento a eliminar estï¿½ fuera del rango");
		}

		request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
		return null;
	}

    
	/**
	 *
	 * @param request
	 * @return
	 */
	private Evento disminuirNumeroVariosCiudadanosCancelacion(HttpServletRequest request) {

		preservarInfoBasicaFolio(request);
		preservarInfoBasicaAnotacion(request);
		preservarInfoCancelacion(request);
		preservarInfoBasicaVariosCiudadanos(request);

		int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

		if(numCiud>1)
		  numCiud--;

		HttpSession session = request.getSession();
		Integer num = new Integer(numCiud);
		session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS,num);
		request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

	  return null;
	}
    
	/**
	 *
	 * @param request
	 * @return
	 */
	private Evento aumentarNumeroVariosCiudadanosCancelacion(HttpServletRequest request) {

	  preservarInfoBasicaFolio(request);
	  preservarInfoBasicaAnotacion(request);
	  preservarInfoCancelacion(request);
	  preservarInfoBasicaVariosCiudadanos(request);

	  int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
	  numCiud++;
	  HttpSession session = request.getSession();
	  Integer num = new Integer(numCiud);
	  session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS,num);
	  request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

	  return null;
	}
    
	/**
	 * @param request
	 * @param accion1
	 * @return
	 * @throws AccionWebException
	 */
	private Evento agregarVariosCiudadanos(HttpServletRequest request, String accion1) throws AccionWebException {

		ValidacionParametrosException exception;

		if(accion1.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS_SEGREGACION))
			exception = new ValidacionParametrosCiudadanoSegregacionCalificacionException();
		else if(accion1.equals(AWCalificacion.AGREGAR_VARIOS_CIUDADANOS))
			exception = new ValidacionParametrosCiudadanoCalificacionException();
		else
			exception = new ValidacionParametrosException();

		return agregarVariosCiudadanos(request,exception);

	}
    
	/**
	 * @param request
	 * @return
	 */
	private Evento consultarFolio(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
		session.removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
		LLavesMostrarFolioHelper llaves=(LLavesMostrarFolioHelper) request.getSession().getAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);
		if(llaves!=null){
			llaves.removeLLaves(request);
		}
		request.getSession().removeAttribute(WebKeys.NOMBRE_LLAVES_MOSTRAR_FOLIO);

		Folio folio = new Folio();

		String matricula=request.getParameter(WebKeys.ITEM);
		folio.setIdMatricula(matricula);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Usuario usuarioSIR = (Usuario)session.getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute( SMARTKeys.USUARIO_EN_SESION );
		EvnAntiguoSistema evn = new EvnAntiguoSistema(usuarioAuriga, EvnFolio.CONSULTA, folio, usuarioSIR);
		evn.setCirculo(circulo);
		Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
		evn.setTurno(turno);
		return evn;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento guardarAnotaciones(HttpServletRequest request) throws ValidacionAnotacionesCancelacionException {
		String[] idsAnotaciones = request.getParameterValues("ESCOGER_ANOTACION_CANCELACION");
		ValidacionAnotacionesCancelacionException exception = new ValidacionAnotacionesCancelacionException();
		if(idsAnotaciones == null){
			exception.addError("Debe seleccionar por lo menos una anotaciï¿½n para cancelar");
			throw exception;
		}
		request.getSession().setAttribute(MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION,idsAnotaciones);
		return null;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento preservarInfoCancelacion(HttpServletRequest request) {

		if (request.getParameter(AWCalificacion.POSICIONANOTACION) != null) {
			request.getSession().setAttribute(AWCalificacion.POSICIONANOTACION, request.getParameter(AWCalificacion.POSICIONANOTACION));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
		}
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
                 */
                if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));
		}
		if (request.getParameter(CFolio.ANOTACION_ID_ANOTACION) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_ID_ANOTACION, request.getParameter(CFolio.ANOTACION_ID_ANOTACION));
		}

		if (request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION));
		}

		if (request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO, request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO));
		}

		if (request.getParameter(CFolio.ANOTACION_FECHA_RADICACION) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_FECHA_RADICACION, request.getParameter(CFolio.ANOTACION_FECHA_RADICACION));
		}
		if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION));
		}

		if (request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO, request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO));
		}

		if (request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION));
		}

		if (request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO, request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
			request.getSession().setAttribute("tipo_oficina", request.getParameter("tipo_oficina"));
			request.getSession().setAttribute("tipo_nom_oficina", request.getParameter("tipo_nom_oficina"));
			request.getSession().setAttribute("numero_oficina", request.getParameter("numero_oficina"));
			request.getSession().setAttribute("id_oficina", request.getParameter("id_oficina"));
                        /*
                        *  @author Carlos Torres
                        *  @chage   se agrega validacion de version diferente
                        *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
                        */
                        request.getSession().setAttribute("version", request.getParameter("version"));
		}

		if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
		}

		if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
			request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
		}

		if (request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION) != null) {
			request.getSession().setAttribute(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION, request.getParameter(CSalvedadAnotacion.DESCRIPCION_SALVEDAD_ANOTACION));
		}
	
		return null;
	}

	  /**
     * Mantiene en sesion la informacion que se ingreaso para mayor valor
     * @param request
     * @return
     */
    private void preservarInfoMayorValor(HttpServletRequest request){
    	HttpSession session=request.getSession();
    	if(request.getParameter(AWAntiguoSistema.PAGO_MAYOR_VALOR_TOTAL) != null){
    		session.setAttribute(AWAntiguoSistema.PAGO_MAYOR_VALOR_TOTAL, request.getParameter(AWAntiguoSistema.PAGO_MAYOR_VALOR_TOTAL));
    	}
    	if(request.getParameter(AWAntiguoSistema.RAZON_MAYOR_VALOR)!=null){
    		session.setAttribute(AWAntiguoSistema.RAZON_MAYOR_VALOR, request.getParameter(AWAntiguoSistema.RAZON_MAYOR_VALOR));
    	}
    }
	/**
     * @param request
     * @return
     */
    private Evento cargarNaturaleza(HttpServletRequest request) throws AccionWebException{
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);
        
        //String ac = request.getParameter(WebKeys.ACCION);
        
        ErrorCrearAnotacionException exception = new ErrorCrearAnotacionException();
        
        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, idNaturaleza);
        if ((idNaturaleza == null)||(idNaturaleza.trim().equals(""))) {
            exception.addError("Debe insertar un cï¿½digo de naturaleza jurï¿½dica");
        }
        

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }
        
        //Preservar informaciï¿½n:
        
        for (java.util.Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
            String key = (String) enumeration.nextElement();
            request.getSession().setAttribute(key, request.getParameter(key));
        }
        
        List grupoNaturalezas = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
        
        
        GrupoNaturalezaJuridica grupo = new GrupoNaturalezaJuridica();
        NaturalezaJuridica nat;
        boolean found = false;
        String descripcion = "";
                                                                          /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
         * @change     : Se asigna valor a la propiedad version
*/
       long version = 0;
        for(Iterator it = grupoNaturalezas.iterator(); it.hasNext() && !found;){
            grupo = (GrupoNaturalezaJuridica)it.next();
            for(Iterator it2=grupo.getNaturalezaJuridicas().iterator(); it2.hasNext() && !found;){
                nat = (NaturalezaJuridica)it2.next();
                if(nat.getIdNaturalezaJuridica().equals(idNaturaleza)){
                    descripcion = nat.getNombre();
                                                                                             /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
         * @change     : Se asigna valor a la propiedad version
*/
                    version = nat.getVersion();
                    found = true;
                }
            }
        }
                
        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, descripcion);
                                                                                            /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
         * @change     : Se asigna valor a la propiedad version
*/

        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER,version);
        
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarAnotacion(HttpServletRequest request) {
        String posAnot=request.getParameter(WebKeys.POSICION);
        int pos=Integer.parseInt(posAnot);
        List anotaciones=(List)request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        if (((String)request.getSession().getAttribute(WebKeys.ACCION_SECUNDARIA)).equals(AWAntiguoSistema.EDICION)){
            Folio delta=(Folio)request.getSession().getAttribute(WebKeys.DELTA_FOLIO_EDICION);
            Anotacion anotacion=(Anotacion)anotaciones.get(pos);
            
            anotacion.setToDelete(true);
            Folio nuevo=new Folio();
            nuevo.setIdMatricula(delta.getIdMatricula());
            //nuevo.setZonaRegistral(delta.getZonaRegistral());
            nuevo.addAnotacione(anotacion);
            
            Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
            
            if(anotacion != null && anotacion.getAnotacionesHijos() != null
                	&& !anotacion.getAnotacionesHijos().isEmpty()){
               	EvnAntiguoSistema evn = new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_EDITAR_CONSULTAR_FOLIO,nuevo,usuario);
               	evn.setLstAnotaFolioHijoRemove(anotacion.getAnotacionesHijos());
               	return evn;
            }
            return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_EDITAR_CONSULTAR_FOLIO,nuevo,usuario);
            
        }
        //esto es un comentario
        Anotacion anotacion=(Anotacion)anotaciones.get(pos);
        if(anotacion != null && anotacion.getAnotacionesHijos() != null
        	&& !anotacion.getAnotacionesHijos().isEmpty()){
        	
        	List lstHijosRemove = anotacion.getAnotacionesHijos();        	
            anotacion.setToDelete(true);
            Folio nuevo=new Folio();
            if(anotacion.getIdMatricula() != null){
	            nuevo.setIdMatricula(anotacion.getIdMatricula());
	            //nuevo.setZonaRegistral(delta.getZonaRegistral());
	            nuevo.addAnotacione(anotacion);
	            
	            Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
	            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
	            
	            EvnAntiguoSistema evn = new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_EDITAR_CONSULTAR_FOLIO,nuevo,usuario);
	            evn.setLstAnotaFolioHijoRemove(lstHijosRemove);
	            return evn;
            }
        }
        
        List nuevaLista=new ArrayList();
        if (anotaciones!=null){
            Iterator itAno=anotaciones.iterator();
            while(itAno.hasNext()){
                Object ob=itAno.next();
                nuevaLista.add(ob);
            }
        }
        //Anotacion anotacion=(Anotacion)nuevaLista.get(pos);
        nuevaLista.remove(pos);
        
        Iterator itNuevaLista=nuevaLista.iterator();
        int orden=1;
        while(itNuevaLista.hasNext()){
            Anotacion an=(Anotacion)itNuevaLista.next();
            an.setOrden((new Integer(orden)).toString());
            orden++;
        }
        
        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO,nuevaLista);
        
        /*List difAnotacion=(List)request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_ANOTACIONES);
        if (difAnotacion==null){
            difAnotacion=new ArrayList();
        }
        
        int dif=-1;
        Iterator itDif=difAnotacion.iterator();
        int i=0;
        while(itDif.hasNext()){
            Anotacion temp=(Anotacion)itDif.next();
            if (temp.getIdAnotacion().equals(anotacion.getIdAnotacion())){
                dif=i;
            }
            i++;
        }
        
        if (dif!=-1){
            difAnotacion.remove(dif);
        }else{
            anotacion.setToDelete(true);
            difAnotacion.add(anotacion);
        }
        request.getSession().setAttribute(WebKeys.LISTA_DIFERENCIAS_ANOTACIONES, difAnotacion);*/
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento terminarEditarFolioAnotaciones(HttpServletRequest request) {
        /*Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Folio delta=(Folio)request.getSession().getAttribute(WebKeys.DELTA_FOLIO_EDICION);
        List difAnotacion=(List)request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_ANOTACIONES);
        if (difAnotacion!=null && !difAnotacion.isEmpty()){
            Iterator itDif=difAnotacion.iterator();
            while(itDif.hasNext()){
                Anotacion anota=(Anotacion)itDif.next();
                if (!anota.isToDelete()&&!anota.isToUpdateValor()){
                    anota.setIdAnotacion(null);
                }
                
                if (anota.isToUpdateValor()){
                    anota.setToUpdateValor(false);
                }
                delta.addAnotacione(anota);
            }
        }
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_EDITAR_FOLIO,delta,usuario);*/
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento cancelarEdicionAnotacion(HttpServletRequest request) {
        eliminarInfoBasicaAnotacion(request);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        request.getSession().removeAttribute("NUM_ANOTACION_TEMPORAL");
        request.getSession().removeAttribute(NUM_REGISTROS_TABLA_MATRICULAS_EDIT);
        eliminarInfoBasicaVariosCiudadanos(request);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento grabarEdicionAnotacion(HttpServletRequest request) throws AccionWebException{
    	this.preservarInfoBasicaAnotacion(request);
    	this.preservarInfoBasicaVariosCiudadanos(request);
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        
        Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
        if (anotaciones == null) {
            anotaciones = new Vector();
        }
        
        ErrorEdicionAnotacionException exception =new ErrorEdicionAnotacionException();
        
        String numEspecificacion = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);

        if (numEspecificacion != null) {
            numEspecificacion = numEspecificacion.replaceAll(",", "");
        }

        double valorEspecificacion = 0.0d;

        //if((numEspecificacion == null) || numEspecificacion.equals("")) {
        //    exception.addError("Debe ingresar el valor de la especificacion en la anotacion");
        //} else {
        try {
            valorEspecificacion = Double.parseDouble(numEspecificacion);

            if (valorEspecificacion < 0) {
                exception.addError(
                    "El valor de la especificaciï¿½n en la anotaciï¿½n no puede ser negativo o cero");
            }
        } catch (NumberFormatException e) {
            //exception.addError("El valor de la especificacion en la anotacion es invï¿½lido");
            valorEspecificacion = 0.0d;
        }

        //}EN ANTIGUO SISTEMA NO SE EXIGE VALOR*/
        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);

        String nomNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);

        if ((idNaturaleza == null) || (idNaturaleza.length() <= 0)) {
            exception.addError(
                "Debe seleccionar una naturaleza jurï¿½dica para la especificaciï¿½n en la anotaciï¿½n");
        }

        //      verificar si idNaturaleza existe en la lista de contexto
        List grupoNaturalezas = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
 /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica       
	 */
        NaturalezaJuridica natJuridica = new NaturalezaJuridica();
        if (!existeNaturaleza(idNaturaleza, grupoNaturalezas,natJuridica)) {
            exception.addError("El cï¿½digo de naturaleza jurï¿½dica no es vï¿½lido");
        }

        String comentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        this.agregarVariosCiudadanos(request);

        Anotacion anota=(Anotacion)request.getSession().getAttribute(WebKeys.ANOTACION_EDITAR);
        Anotacion anotacion = null;
        
        if (((String)request.getSession().getAttribute(WebKeys.ACCION_SECUNDARIA)).equals(AWAntiguoSistema.EDICION)||
        		((String)request.getSession().getAttribute(WebKeys.ACCION_SECUNDARIA)).equals(AWAntiguoSistema.EDICION_ANOTACIONES)){
            anotacion = new Anotacion();
        }else{
            if (anotaciones!=null){
                Iterator it = anotaciones.iterator();
                while (it.hasNext()){
                    Anotacion temp=(Anotacion)it.next();
                    if (temp.getOrden().equals(anota.getOrden())){
                         anotacion = temp;
                    }
                }
            }
            
            if (anotacion==null){
                throw new AccionWebException();
            }
        }
                
        NaturalezaJuridica naturalezaJuridica = new NaturalezaJuridica();
        anotacion.setValor(valorEspecificacion);

        naturalezaJuridica.setIdNaturalezaJuridica(idNaturaleza);
        naturalezaJuridica.setNombre(nomNatJuridica);
         /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
         */
        naturalezaJuridica.setVersion(natJuridica.getVersion());

        anotacion.setNaturalezaJuridica(naturalezaJuridica);
        anotacion.setComentario(comentario);

        //Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        
        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if ((ciudadanos == null) || ciudadanos.isEmpty()) {
            exception.addError(
                "Debe ingresar por lo menos un ciudadano en la anotaciï¿½n");
            throw exception;
        }
        
       
        List anotacionesTemp=new ArrayList();
        List anotacionesCiud=anotacion.getAnotacionesCiudadanos();
        Iterator itAnotaCiud=anotacionesCiud.iterator();
        while (itAnotaCiud.hasNext()){
            AnotacionCiudadano anotCiu=(AnotacionCiudadano)itAnotaCiud.next();
            anotacionesTemp.add(anotCiu);
        }
        
        Iterator itAnotTemp=anotacionesTemp.iterator();
        while(itAnotTemp.hasNext()){
            AnotacionCiudadano anotTemp=(AnotacionCiudadano)itAnotTemp.next();
            anotacion.removeAnotacionesCiudadano(anotTemp);
        }
        
        if (ciudadanos != null) {
            Iterator itPersonas = ciudadanos.iterator();

            while (itPersonas.hasNext()) {
                AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
                ciudadano.setAnotacion(anotacion);
                anotacion.addAnotacionesCiudadano(ciudadano);
            }
        }

        /*List diferencias=(List)request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_ANOTACIONES);
        if (diferencias==null){
            diferencias=new ArrayList();
        }
        
        Iterator itDif=diferencias.iterator();
        int pos=-1;
        int i=0;
        while(itDif.hasNext()){
            Anotacion temp=(Anotacion)itDif.next();
            if (temp.getIdAnotacion().equals(anotacion.getIdAnotacion())){
                pos=i;
            }
            i++;
        }
        if (pos!=-1){
            diferencias.remove(pos);
        }
        anotacion.setToUpdateValor(true);
        diferencias.add(anotacion);
        request.getSession().setAttribute(WebKeys.LISTA_DIFERENCIAS_ANOTACIONES, diferencias);
        */
        
        //request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotaciones);

        //eliminarInfoBasicaAnotacion(request);
        //request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        
        
        String numLastIdAnota = "0";
        if(request.getSession().getAttribute("LISTA_ANOTACIONES_FOLIO") != null ){
        	numLastIdAnota = ((Anotacion)((List)request.getSession().
        		getAttribute("LISTA_ANOTACIONES_FOLIO")).get((((List)request.getSession().
        			getAttribute("LISTA_ANOTACIONES_FOLIO")).size()-1))).getIdAnotacion();
        	if(numLastIdAnota != null){
        		numLastIdAnota = String.valueOf(Integer.valueOf(numLastIdAnota).intValue()+1);
        	}
        	if(numLastIdAnota == null){
        		numLastIdAnota = ((Anotacion)((List)request.getSession().
                		getAttribute("LISTA_ANOTACIONES_FOLIO")).get(0)).getIdAnotacion();
        		if(numLastIdAnota != null) 
        			numLastIdAnota = String.valueOf(Integer.valueOf(numLastIdAnota).intValue()+((List)request.getSession().getAttribute("LISTA_ANOTACIONES_FOLIO")).size());
        		else
        			numLastIdAnota = "0";
        	}
        }
        
        String strMatriculasP = request.getParameter("textmatriculaPadreE");
        String strAnotacionesP = request.getParameter("textanotacionPadreE");
        
        if(strMatriculasP != null && strAnotacionesP != null){
        	if(!strMatriculasP.equals("")){
        		if(strAnotacionesP.equals("")){
        			exception.addError("La Matricula Padre "+ strMatriculasP+"  tiene que tener su respectiva Anotacion");
        		}
        	}else if(!strAnotacionesP.equals("")){
        		exception.addError("La Anotacion "+ strAnotacionesP+"  tiene que tener su Matricula Padre ");
        	}
        }
        
        String[] strVectorMatriculas = request.getParameterValues("textmatriculahijaE");
        String[] strVectorAnotaciones = request.getParameterValues("textanotacionhijaE");
        
        
        if(strVectorMatriculas != null && strVectorAnotaciones != null){
	        int iMatirculas = 0;
	        //boolean swMatriculasOk = false;
	        while(iMatirculas < strVectorMatriculas.length){
	        	if(strVectorMatriculas[iMatirculas] != null && !strVectorMatriculas[iMatirculas].equals("")){
	        		if(strVectorAnotaciones[iMatirculas] != null && strVectorAnotaciones[iMatirculas].equals("")){
	        			exception.addError("La Matricula Hija "+ strVectorMatriculas[iMatirculas]+"  tiene que tener su respectiva Anotacion");
	        		}
	        	}else{
	        		if(strVectorAnotaciones[iMatirculas] != null && !strVectorAnotaciones[iMatirculas].equals("")){
	        			exception.addError("La Anotacion  "+ strVectorAnotaciones[iMatirculas]+"  tiene que tener su respectiva Matricula Hija");
	        		}
	        	}
	        	iMatirculas = iMatirculas+1;
	        }
        }
        
        if (((String)request.getSession().getAttribute(WebKeys.ACCION_SECUNDARIA)).equals(AWAntiguoSistema.EDICION)){
            Anotacion original=(Anotacion)request.getSession().getAttribute(WebKeys.ANOTACION_EDITAR);
            Anotacion deltaAnot=encontrarDiferenciasAnotacion(original,anotacion,request,false);
            Folio folio=new Folio();
            Folio deltaFol=(Folio)request.getSession().getAttribute(WebKeys.DELTA_FOLIO_EDICION);
            folio.setIdMatricula(deltaFol.getIdMatricula());
            //folio.setZonaRegistral(deltaFol.getZonaRegistral());
            folio.addAnotacione(deltaAnot);
            
            if(numLastIdAnota != null && numLastIdAnota.equalsIgnoreCase("0"))
            	numLastIdAnota= String.valueOf(folio.getLastIdAnotacion()+1);
            else if(numLastIdAnota == null)
            	numLastIdAnota= "1";
            
            if(strVectorMatriculas != null){
	            for(int i =0;i<strVectorMatriculas.length; i++){
	            	if(!strVectorMatriculas[i].equals("")){
		            	FolioDerivado folioDerivado = new FolioDerivado();
		            	
		        	    folioDerivado.setIdAnotacion1(strVectorAnotaciones[i]);
		        	    folioDerivado.setIdMatricula1(strVectorMatriculas[i]);
		
		        	    folioDerivado.setIdAnotacion(numLastIdAnota);
		        	    folioDerivado.setIdMatricula(folio.getIdMatricula());
		        	    anotacion.addAnotacionesHijo(folioDerivado);
	        	    }
	            }
            }
            if(strMatriculasP != null && !strMatriculasP.equals("")){
            	FolioDerivado folioDerivado = new FolioDerivado();
            	
        	    folioDerivado.setIdAnotacion(strAnotacionesP);
        	    folioDerivado.setIdMatricula(strMatriculasP);

        	    folioDerivado.setIdAnotacion1(numLastIdAnota);
        	    folioDerivado.setIdMatricula1(folio.getIdMatricula());
        	    anotacion.addAnotacionesPadre(folioDerivado);
            }
            
            
            
            Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
            
            EvnAntiguoSistema evn =  new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_EDITAR_CONSULTAR_FOLIO,folio,usuario);
            //evnt.setAnotaciones(vectorAnotaciones);
            evn.setTurno(turno);
            if(request.getSession() != null && request.getSession().getAttribute(NUM_REGISTROS_TABLA_MATRICULAS_EDIT) != null)
            	request.getSession().removeAttribute(NUM_REGISTROS_TABLA_MATRICULAS_EDIT);
            return evn;
        }else if (((String)request.getSession().getAttribute(WebKeys.ACCION_SECUNDARIA)).equals(AWAntiguoSistema.EDICION_ANOTACIONES)){
            Anotacion original=(Anotacion)request.getSession().getAttribute(WebKeys.ANOTACION_EDITAR);
            Anotacion deltaAnot=encontrarDiferenciasAnotacion(original,anotacion,request,true);
            Folio folio=new Folio();
            Folio folioEditado=(Folio)request.getSession().getAttribute( WebKeys.FOLIO_EDITADO );
            folio.setIdMatricula(folioEditado.getIdMatricula());
            //folio.setZonaRegistral(deltaFol.getZonaRegistral());
            folio.addAnotacione(deltaAnot);
            
            
            List lstAnotaPadre = new ArrayList();
            List lstAnotaHijo = new ArrayList();
            if(strVectorMatriculas != null){
	            for(int i =0;i<strVectorMatriculas.length; i++){
	            	if(!strVectorMatriculas[i].equals("")){
		            	FolioDerivado folioDerivado = new FolioDerivado();
		            	
		        	    folioDerivado.setIdAnotacion1(strVectorAnotaciones[i]);
		        	    folioDerivado.setIdMatricula1(strVectorMatriculas[i]);
		
		        	    folioDerivado.setIdAnotacion(original.getIdAnotacion());
		        	    folioDerivado.setIdMatricula(folio.getIdMatricula());
		        	//    anotacion.addAnotacionesHijo(folioDerivado);
		        	    lstAnotaHijo.add(folioDerivado);
	        	    }
	            }
            }
            if(strMatriculasP != null && !strMatriculasP.equals("")){
            	FolioDerivado folioDerivado = new FolioDerivado();
            	
        	    folioDerivado.setIdAnotacion(strAnotacionesP);
        	    folioDerivado.setIdMatricula(strMatriculasP);

        	    folioDerivado.setIdAnotacion1(original.getIdAnotacion());
        	    folioDerivado.setIdMatricula1(folio.getIdMatricula());
        	    lstAnotaPadre.add(folioDerivado);
        	    //anotacion.addAnotacionesPadre(folioDerivado);
        	    
            }
            
            
            Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
            
            EvnAntiguoSistema evn =  new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_EDITAR_CONSULTAR_FOLIO,folio,usuario);
            //evnt.setAnotaciones(vectorAnotaciones);
            evn.setLstAnotaFolioHijo(lstAnotaHijo);
            evn.setAnotacionUpdate(original.getIdAnotacion());
            evn.setMatruculaUpdate(folio.getIdMatricula());
            evn.setLstAnotaFolioPadre(lstAnotaPadre);
            evn.setTurno(turno);
            if(request.getSession() != null && request.getSession().getAttribute(NUM_REGISTROS_TABLA_MATRICULAS_EDIT) != null)
            	request.getSession().removeAttribute(NUM_REGISTROS_TABLA_MATRICULAS_EDIT);
            return evn;
        }
        eliminarInfoBasicaAnotacion(request);
        request.getSession().removeAttribute("NUM_ANOTACION_TEMPORAL");
        if(request.getSession() != null && request.getSession().getAttribute(NUM_REGISTROS_TABLA_MATRICULAS_EDIT) != null)
        	request.getSession().removeAttribute(NUM_REGISTROS_TABLA_MATRICULAS_EDIT);
        return null;
    }

    /**
     * @param original
     * @param anotacion
     * @return
     */
    private Anotacion encontrarDiferenciasAnotacion(Anotacion original, Anotacion nueva, HttpServletRequest request,boolean edicionAnotaciones) {
        Anotacion anotacion=null;
        if (original!=null && nueva!=null){
            anotacion=new Anotacion();
            anotacion.setIdAnotacion(original.getIdAnotacion());
            
            if (original.getValor()!=nueva.getValor()){
                anotacion.setValor(nueva.getValor());
                anotacion.setToUpdateValor(true);
            }
             
            if(edicionAnotaciones){
            	anotacion.setNaturalezaJuridica(nueva.getNaturalezaJuridica());
            }else if(!original.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(nueva.getNaturalezaJuridica().getIdNaturalezaJuridica())){
                anotacion.setNaturalezaJuridica(nueva.getNaturalezaJuridica());
            }
            
            if (original.getComentario()==null || !original.getComentario().equals(nueva.getComentario())){
                anotacion.setComentario(nueva.getComentario());
            }
            try {
                this.agregarVariosCiudadanos(request);
                List deltasCiudadanos=new ArrayList();
                List ciudadanosOriginales=((Anotacion)request.getSession().getAttribute(WebKeys.ANOTACION_EDITAR)).getAnotacionesCiudadanos();
                List ciudadanosNuevos=(List)request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
                
                Iterator itOriginales = ciudadanosOriginales.iterator();
                Iterator itNuevos=ciudadanosNuevos.iterator();
                
                while (itOriginales.hasNext()){
                    AnotacionCiudadano anotOrig=(AnotacionCiudadano)itOriginales.next();
                    boolean eliminada=true;
                    while(itNuevos.hasNext()){
                        AnotacionCiudadano anotNueva=(AnotacionCiudadano)itNuevos.next();
                        if (ciudadanosIguales(anotOrig,anotNueva)){
                            eliminada=false;
                        }
                    }
                    if (eliminada){
                        anotOrig.setToDelete(true);
                        deltasCiudadanos.add(anotOrig);
                    }
                }
                
                
                itOriginales = ciudadanosOriginales.iterator();
                itNuevos=ciudadanosNuevos.iterator();
                
                while (itNuevos.hasNext()){
                    AnotacionCiudadano anotNueva=(AnotacionCiudadano)itNuevos.next();
                    boolean agregada=true;
                    while(itOriginales.hasNext()){
                        AnotacionCiudadano anotOrig=(AnotacionCiudadano)itOriginales.next();
                        if (ciudadanosIguales(anotOrig,anotNueva)){
                            agregada=false;
                        }
                    }
                    if (agregada){
                        deltasCiudadanos.add(anotNueva);
                    }
                }
                
                Iterator itDeltas=deltasCiudadanos.iterator();
                while (itDeltas.hasNext()){
                    AnotacionCiudadano ciud=(AnotacionCiudadano)itDeltas.next();
                    anotacion.addAnotacionesCiudadano(ciud);
                }
                if(anotacion.getTipoAnotacion() == null)
                	anotacion.setTipoAnotacion(original.getTipoAnotacion());
                if(anotacion.getOrden() == null || anotacion.getOrden().equals(""))
                	anotacion.setOrden(original.getOrden());
            } catch (AccionWebException e) {
            	Log.getInstance().error(AWAntiguoSistema.class, e);
            }
        }
        return anotacion;
    }

    /**
     * @param anotOrig
     * @param anotNueva
     * @return
     */
    private boolean ciudadanosIguales(AnotacionCiudadano anotOrig, AnotacionCiudadano anotNueva) {
        
        if (anotOrig.getMarcaPropietario()!=anotNueva.getMarcaPropietario()){
            return false;
        }
        
        boolean iguales=(anotOrig.getParticipacion()==null)?(anotNueva.getParticipacion()==null):(anotNueva.getParticipacion()!=null);
        if (!iguales){
            return false;
        }
        
        if (anotOrig.getParticipacion()!=null){
            iguales=anotOrig.getParticipacion().equals(anotNueva.getParticipacion());
            if (!iguales){
                return false;
            }
        }
        
        if (!anotOrig.getRolPersona().equals(anotNueva.getRolPersona())){
            return false;
        }
        
        Ciudadano ciudOrig=anotOrig.getCiudadano();
        Ciudadano ciudNuevo=anotNueva.getCiudadano();
        
        if (!ciudOrig.getApellido1().equals(ciudNuevo.getApellido1())){
            return false;
        }
        
        iguales=(ciudOrig.getApellido2()==null)?(ciudNuevo.getApellido2()==null):(ciudNuevo.getApellido2()!=null);
        if (!iguales){
            return false;
        }
        
        if (ciudOrig.getApellido2()!=null){
            if (!ciudOrig.getApellido2().equals(ciudNuevo.getApellido2())){
                return false;
            }
        }
        
        if (!ciudOrig.getNombre().equals(ciudNuevo.getNombre())){
            return false;
        }
        
        iguales=(ciudOrig.getDocumento()==null)?(ciudNuevo.getDocumento()==null):(ciudNuevo.getDocumento()!=null);
        if (!iguales){
            return false;
        }
        
        if (ciudOrig.getDocumento()!=null){
            if (!ciudOrig.getDocumento().equals(ciudNuevo.getDocumento())){
                return false;
            }
        }
        
        if (!ciudOrig.getTipoDoc().equals(ciudNuevo.getTipoDoc())){
            return false;
        }
        
        return true;
    }

    /**.
     * @param request
     * @return
     */
    private Evento editarAnotacion(HttpServletRequest request) throws AccionWebException{
    	preservarInfoBasicaVariosCiudadanos(request);
        String orden = request.getParameter("NUM_ANOTACION_TEMPORAL");
        List anotaciones=(List)request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        List ciudadanosSet = new ArrayList();
        boolean encontrada=false;
        int numero = 0;
        if (anotaciones!=null){
            Iterator it = anotaciones.iterator();
            while(it.hasNext()){
                Anotacion anot=(Anotacion)it.next();
                if (anot.getOrden().equals(orden)){
                    encontrada = true;
                    request.getSession().setAttribute(WebKeys.ANOTACION_EDITAR,anot);
                    numero = anot.getAnotacionesCiudadanos().size();
                    ciudadanosSet = anot.getAnotacionesCiudadanos();
                   // if(anot.getAnotacionesHijos() != null) request.getSession().setAttribute(NUM_REGISTROS_TABLA_MATRICULAS_EDIT, new Integer(anot.getAnotacionesHijos().size()));
                }
            }    
        }
        
        Integer num = new Integer(numero);
        request.getSession().setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        
        /**Organizar los ciudadanos. Se debe tener en cuenta el orden ya que el
         * helper (VariosCiudadanosAnotacionAntiguoSistemaHelper)los muestra de esta manera y se necesita manejar el mismo contador
         * para poder mostrar correctamente ls informacion */
        
        List ciudadanosAux = new ArrayList(ciudadanosSet);
		if (ciudadanosAux == null) {
			ciudadanosAux = new Vector();
		}
	          
    	Iterator itCiudadanos = ciudadanosAux.iterator();
    	List losA = new ArrayList();
    	List losDe = new ArrayList();
    	while(itCiudadanos.hasNext()){
    		AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
    		String rol = anotacionCiudadano.getRolPersona();
    		if(rol.equals("DE")){
    			losDe.add(anotacionCiudadano);
    		} else if(rol.equals("A")){
    			losA.add(anotacionCiudadano);
    		}
    	}
   		losDe.addAll(losA);
   		ciudadanosAux.clear();
   		ciudadanosAux = losDe;
   		
        preservarInfoBasicaVariosCiudadanosInicial(request,ciudadanosAux);
        request.getSession().setAttribute("NUM_ANOTACION_TEMPORAL",orden);
        request.getSession().setAttribute(WebKeys.ACCION_SECUNDARIA, AWAntiguoSistema.EDICION_ANOTACIONES);
        if (!encontrada){
            throw new AccionWebException("No se encontro la anotacion");
        }
        
        Folio folioEditado=(Folio)request.getSession().getAttribute( WebKeys.FOLIO_EDITADO);
               
        Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        
        EvnAntiguoSistema evn =  new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.CONSULTAR_ANOTACION_POR_ORDEN,folioEditado,usuario);
        evn.setOrdenAnotacion(orden);
        return evn;
    }
    
    

    /**
     * @param request
     * @return
     */
    private Evento editarAnotaciones(HttpServletRequest request) throws AccionWebException{

        ErrorEdicionFolioException exception=new ErrorEdicionFolioException();
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        if (turno == null) {
            throw new AccionWebException("No se encontro el turno en la sesiï¿½n");
        }

        //SE RECUPERA LA INFORMACIï¿½N DEL FORMULARIO
        //String valorRadicacion = request.getParameter(CFolio.FOLIO_NUM_RADICACION);
        String valorDepartamento = request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO);

        if (valorDepartamento == null) {
            valorDepartamento = new String();
        }

        if (valorDepartamento.length() <= 0) {
            exception.addError("Debe seleccionar un departamento.");
        }

        String valorMunicipio = request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC);

        if (valorMunicipio == null) {
            valorMunicipio = new String();
        }

        if (valorMunicipio.length() <= 0) {
            exception.addError("Debe seleccionar un municipio vï¿½lido.");
        }

        String valorVereda = request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA);

        if (valorVereda == null) {
            valorVereda = new String();
        }

        String valorTipoPredio = request.getParameter(CFolio.FOLIO_TIPO_PREDIO);

        if ((valorTipoPredio == null) ||
                (valorTipoPredio.equals("SIN_SELECCIONAR"))) {
            exception.addError("Debe seleccionar un tipo de predio vï¿½lido");
        }

        boolean revisarVereda = true;

        if (valorTipoPredio.equals(CTipoPredio.TIPO_URBANO)) {
            revisarVereda = false;
        }

        if ((valorVereda.length() <= 0) && revisarVereda) {
            exception.addError("Debe seleccionar una vereda vï¿½lida.");
        }

        String valorComplementacion = request.getParameter(CFolio.FOLIO_COMPLEMENTACION);
        if(valorComplementacion!=null){
			valorComplementacion = valorComplementacion.toUpperCase();
        }

        String idComplementacion = request.getParameter(CFolio.FOLIO_ID_COMPLEMENTACION);
        String tipoAccion = request.getParameter(CFolio.SELECCIONAR_FOLIO +
                AWModificarFolio.FOLIO_COMPLEMENTACION);

        if ((tipoAccion != null) && tipoAccion.equals(CFolio.ASOCIAR)) {
            if (idComplementacion == null) {
                exception.addError(
                    "Debe ingresar un identificador para la complementaciï¿½n vï¿½lido");
            }
        }

        String valorLindero = request.getParameter(CFolio.FOLIO_LINDERO);

        if (valorLindero == null) {
            exception.addError("Debe ingresar informacion del lindero vï¿½lida");
        }

        if (valorLindero != null && valorLindero.trim().equals("")) {
            exception.addError("Debe ingresar informacion del lindero vï¿½lida");
        }
        
        String valorCodCatastral = request.getParameter(CFolio.FOLIO_COD_CATASTRAL);

        if (valorCodCatastral == null) {
            if(turno!=null && turno.getIdProceso()!=1 && turno.getIdFase()!=null 
            		&& !turno.getIdFase().equals("")&& !turno.getIdFase().equals(CFase.ANT_HOJA_RUTA))
        	exception.addError("Debe ingresar un codigo catastral vï¿½lido");
        }
        
        if (valorCodCatastral != null && valorCodCatastral.trim().equals("")) {
        	
            if(turno!=null && turno.getIdProceso()!=1 && turno.getIdFase()!=null 
            		&& !turno.getIdFase().equals("")&& !turno.getIdFase().equals(CFase.ANT_HOJA_RUTA))
            exception.addError("Debe ingresar un codigo catastral vï¿½lido");
        }
        
        Folio folio=new Folio();
        
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        folio.setFechaApertura(new Date(System.currentTimeMillis()));

        folio.setCodCatastral(valorCodCatastral);

        if (circulo == null) {
            circulo = new Circulo();
        }

        folio.setLindero(valorLindero);

        //SE LE INGRESA LA COMPLEMENTACIï¿½N AL FOLIO
        Complementacion comp = new Complementacion();

        String tipoComp = request.getParameter(CFolio.SELECCIONAR_FOLIO +
                AWModificarFolio.FOLIO_COMPLEMENTACION);

        if (tipoComp.equals(CFolio.NUEVA) || tipoComp.equals(CFolio.COPIAR)) {
            comp.setComplementacion(valorComplementacion);
        }

        if (tipoComp.equals(CFolio.ASOCIAR)) {
            comp.setIdComplementacion(idComplementacion);
            comp.setComplementacion(valorComplementacion);
        }

        folio.setComplementacion(comp);

        //SE INGRESA EL TIPO DE PREDIO
        List tiposPredio = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);

        if (tiposPredio != null) {
            Iterator itPredios = tiposPredio.iterator();

            while (itPredios.hasNext()) {
                ElementoLista elementoTipoPredio = (ElementoLista) itPredios.next();

                if (elementoTipoPredio.getId().equals(valorTipoPredio)) {
                    TipoPredio tipoPredio = new TipoPredio();
                    tipoPredio.setIdPredio(valorTipoPredio);
                    tipoPredio.setNombre(elementoTipoPredio.getValor());
                    folio.setTipoPredio(tipoPredio);
                }
            }
        } else {
            exception.addError("La lista de los tipos de predio no se encontro");
        }

        ZonaRegistral zona = new ZonaRegistral();
        zona.setCirculo(circulo);

        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(valorDepartamento);

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(valorMunicipio);

        municipio.setDepartamento(departamento);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(valorDepartamento);
        vereda.setIdMunicipio(valorMunicipio);
        vereda.setIdVereda(valorVereda);
        vereda.setMunicipio(municipio);
        zona.setVereda(vereda);

        /*String idZonaRegistral = folio.getZonaRegistral().getIdZonaRegistral();
        zona.setIdZonaRegistral(idZonaRegistral);*/

        folio.setZonaRegistral(zona);

        Documento documento = new Documento();
        TipoDocumento tipoDoc = new TipoDocumento();

        tipoDoc.setIdTipoDocumento(CTipoDocumento.ID_TIPO_CERTIFICADO);
        documento.setFecha(new Date());
        documento.setTipoDocumento(tipoDoc);
        folio.setDocumento(documento);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Solicitud solicitud = turno.getSolicitud();

        if (solicitud instanceof SolicitudRegistro) {
            SolicitudRegistro registro = (SolicitudRegistro) solicitud;
            folio.setDocumento(registro.getDocumento());
        }
        
        
        Folio viejo=(Folio)request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        Folio delta=encontrarDiferencias(viejo,folio);
        
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);

        if ((direcciones != null) && (direcciones.size() > 0)) {
            Iterator it = direcciones.iterator();
            while (it.hasNext()) {
                Direccion direccion = (Direccion) it.next();
                if (!direccion.isToDelete()){
                    direccion.setIdDireccion(null);    
                }
                delta.addDireccione(direccion);
            }
        }
        request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
        request.getSession().setAttribute(WebKeys.DELTA_FOLIO_EDICION, delta);
        request.getSession().setAttribute(WebKeys.ACCION_SECUNDARIA, AWAntiguoSistema.EDICION);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
        
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_EDITAR_CONSULTAR_FOLIO,delta,usuario);
    }

    /**
     * @param request
     * @return
     */
    private Evento terminarEditarFolio(HttpServletRequest request) throws AccionWebException{
    	this.preservarInfoBasicaFolio(request);
        Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ErrorEdicionFolioException exception=new ErrorEdicionFolioException();
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        if (turno == null) {
            throw new AccionWebException("No se encontro el turno en la sesiï¿½n");
        }

        //SE RECUPERA LA INFORMACIï¿½N DEL FORMULARIO
        //String valorRadicacion = request.getParameter(CFolio.FOLIO_NUM_RADICACION);
        String valorDepartamento = request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO);

        if (valorDepartamento == null) {
            valorDepartamento = new String();
        }

        if (valorDepartamento.length() <= 0) {
            exception.addError("Debe seleccionar un departamento.");
        }

        String valorMunicipio = request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC);

        if (valorMunicipio == null) {
            valorMunicipio = new String();
        }

        if (valorMunicipio.length() <= 0) {
            exception.addError("Debe seleccionar un municipio vï¿½lido.");
        }

        String valorVereda = request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA);

        if (valorVereda == null) {
            valorVereda = new String();
        }

        String valorTipoPredio = request.getParameter(CFolio.FOLIO_TIPO_PREDIO);

        if ((valorTipoPredio == null) ||
                (valorTipoPredio.equals("SIN_SELECCIONAR"))) {
            exception.addError("Debe seleccionar un tipo de predio vï¿½lido");
        }

        boolean revisarVereda = true;

        if (valorTipoPredio.equals(CTipoPredio.TIPO_URBANO)) {
            revisarVereda = false;
        }

        if ((valorVereda.length() <= 0) && revisarVereda) {
            exception.addError("Debe seleccionar una vereda vï¿½lida.");
        }

        String valorComplementacion = request.getParameter(CFolio.FOLIO_COMPLEMENTACION);
		if(valorComplementacion!=null){
			valorComplementacion = valorComplementacion.toUpperCase();
		}        

        String idComplementacion = request.getParameter(CFolio.FOLIO_ID_COMPLEMENTACION);
        String tipoAccion = request.getParameter(CFolio.SELECCIONAR_FOLIO +
                AWModificarFolio.FOLIO_COMPLEMENTACION);

        if ((tipoAccion != null) && tipoAccion.equals(CFolio.ASOCIAR)) {
            if (idComplementacion == null) {
                exception.addError(
                    "Debe ingresar un identificador para la complementaciï¿½n vï¿½lido");
            }
        }

        String valorLindero = request.getParameter(CFolio.FOLIO_LINDERO);

        if (valorLindero == null) {
            exception.addError("Debe ingresar informacion del lindero vï¿½lida");
        }
        if (valorLindero != null && valorLindero.trim().equals("")) {
            exception.addError("Debe ingresar informacion del lindero vï¿½lida");
        }
        String valorCodCatastral = request.getParameter(CFolio.FOLIO_COD_CATASTRAL);

        if (valorCodCatastral == null) {
            exception.addError("Debe ingresar un codigo catastral vï¿½lido");
        }
        if (valorCodCatastral == null && valorCodCatastral.trim().equals("")) {
            exception.addError("Debe ingresar un codigo catastral vï¿½lido");
        }
        Folio folio=new Folio();
        
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        folio.setFechaApertura(new Date(System.currentTimeMillis()));

        folio.setCodCatastral(valorCodCatastral);

        if (circulo == null) {
            circulo = new Circulo();
        }

        folio.setLindero(valorLindero);

        //SE LE INGRESA LA COMPLEMENTACIï¿½N AL FOLIO
        Complementacion comp = new Complementacion();

        String tipoComp = request.getParameter(CFolio.SELECCIONAR_FOLIO +
                AWModificarFolio.FOLIO_COMPLEMENTACION);

        if (tipoComp.equals(CFolio.NUEVA) || tipoComp.equals(CFolio.COPIAR)) {
            comp.setComplementacion(valorComplementacion);
        }

        if (tipoComp.equals(CFolio.ASOCIAR)) {
            comp.setIdComplementacion(idComplementacion);
            comp.setComplementacion(valorComplementacion);
        }

        folio.setComplementacion(comp);

        //SE INGRESA EL TIPO DE PREDIO
        List tiposPredio = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);

        if (tiposPredio != null) {
            Iterator itPredios = tiposPredio.iterator();

            while (itPredios.hasNext()) {
                ElementoLista elementoTipoPredio = (ElementoLista) itPredios.next();

                if (elementoTipoPredio.getId().equals(valorTipoPredio)) {
                    TipoPredio tipoPredio = new TipoPredio();
                    tipoPredio.setIdPredio(valorTipoPredio);
                    tipoPredio.setNombre(elementoTipoPredio.getValor());
                    folio.setTipoPredio(tipoPredio);
                }
            }
        } else {
            exception.addError("La lista de los tipos de predio no se encontro");
        }

        ZonaRegistral zona = new ZonaRegistral();
        zona.setCirculo(circulo);

        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(valorDepartamento);

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(valorMunicipio);

        municipio.setDepartamento(departamento);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(valorDepartamento);
        vereda.setIdMunicipio(valorMunicipio);
        vereda.setIdVereda(valorVereda);
        vereda.setMunicipio(municipio);
        zona.setVereda(vereda);

        folio.setZonaRegistral(zona);

        Documento documento = new Documento();
        TipoDocumento tipoDoc = new TipoDocumento();

        tipoDoc.setIdTipoDocumento(CTipoDocumento.ID_TIPO_CERTIFICADO);
        documento.setFecha(new Date());
        documento.setTipoDocumento(tipoDoc);
        folio.setDocumento(documento);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Solicitud solicitud = turno.getSolicitud();

        if (solicitud instanceof SolicitudRegistro) {
            SolicitudRegistro registro = (SolicitudRegistro) solicitud;
            folio.setDocumento(registro.getDocumento());
        }
        
        
        Folio viejo=(Folio)request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        Folio delta=encontrarDiferencias(viejo,folio);
        
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);

        if ((direcciones != null) && (direcciones.size() > 0)) {
            Iterator it = direcciones.iterator();
            while (it.hasNext()) {
                Direccion direccion = (Direccion) it.next();
                if (!direccion.isToDelete()){
                    direccion.setIdDireccion(null);    
                }
                delta.addDireccione(direccion);
            }
        }
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_EDITAR_FOLIO,delta,usuario);
    }

    /**
     * @param viejo
     * @param folio
     * @return
     */
    private Folio encontrarDiferencias(Folio viejo, Folio nuevo) {
        Folio deltas=new Folio();
        if (viejo!=null && nuevo!=null){
            deltas.setIdMatricula(viejo.getIdMatricula());
            //deltas.setZonaRegistral(viejo.getZonaRegistral());
            if (viejo.getCodCatastral()!=null && !viejo.getCodCatastral().equals(nuevo.getCodCatastral())){
                deltas.setCodCatastral(nuevo.getCodCatastral());
            }else if(viejo.getCodCatastral() == null && nuevo.getCodCatastral()!=null){
				deltas.setCodCatastral(nuevo.getCodCatastral());
            }
            if (viejo.getLindero() != null 
            		&& nuevo.getLindero() != null 
            		&& !viejo.getLindero().equals(nuevo.getLindero())){
                deltas.setLindero(nuevo.getLindero());
            }
            
            if (nuevo.getComplementacion() != null 
            		&& nuevo.getComplementacion().getComplementacion() != null 
            		&& viejo.getComplementacion() != null 
            		&& viejo.getComplementacion().getComplementacion() != null 
            		&& !viejo.getComplementacion().getComplementacion().equals(nuevo.getComplementacion().getComplementacion())){
                deltas.setComplementacion(nuevo.getComplementacion());
            }
            
            if (nuevo.getTipoPredio() != null 
            		&& nuevo.getTipoPredio().getIdPredio() != null  
            		&& viejo.getTipoPredio() != null 
					&& viejo.getTipoPredio().getIdPredio() != null
            		&& !viejo.getTipoPredio().getIdPredio().equals(nuevo.getTipoPredio().getIdPredio())){
                deltas.setTipoPredio(nuevo.getTipoPredio());
            }
            
            if (nuevo.getDocumento() != null 
            		&& nuevo.getDocumento().getTipoDocumento() != null  
            		&& viejo.getDocumento() != null 
            		&& viejo.getDocumento().getTipoDocumento() != null
            		&& !viejo.getDocumento().getTipoDocumento().getIdTipoDocumento().equals(nuevo.getDocumento().getTipoDocumento().getIdTipoDocumento())){
                deltas.setDocumento(nuevo.getDocumento());
            }
            return deltas;
        }
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento agregarAnotacion(HttpServletRequest request) throws AccionWebException{
    	this.preservarInfoBasicaAnotacion(request);
    	this.preservarInfoBasicaVariosCiudadanos(request);
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        if (anotaciones == null) {
            anotaciones = new Vector();
        }
        
        ErrorCrearAnotacionException exception = new ErrorCrearAnotacionException();
        
        /**
        * @Autor: Edgar Lora
        * @Mantis: 0013038
        * @Requerimiento: 060_453
        */
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        Folio f = (Folio)request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        String valorLindero = f.getLindero();
        if(valorLindero.toUpperCase().indexOf("Articulo 8 Parï¿½grafo 1ï¿½. de la Ley 1579 de 2012.".toUpperCase()) == -1){
            valorLindero = "Articulo 8 Parï¿½grafo 1ï¿½. de la Ley 1579 de 2012. " + valorLindero;        
        }
        String valFechaRadicacion = request.getParameter(CFolio.ANOTACION_FECHA_RADICACION);
        Calendar fechaRadicacion = null;

        if ((valFechaRadicacion == null) || valFechaRadicacion.equals("")) {
            exception.addError(
                "La fecha de la radicacion en la anotaciï¿½n no puede ser vacia");
        } else {
            fechaRadicacion = darFecha(valFechaRadicacion);
			Date dHoy = new Date();
			Calendar hoy = Calendar.getInstance();
			hoy.setTime(dHoy);
			
            if (fechaRadicacion == null) {
                exception.addError(
                    "La fecha de la radicacion en la anotaciï¿½n es invï¿½lida");
            }else{
				if(fechaRadicacion.after(hoy)){
					exception.addError("La fecha de la radicacion debe ser anterior a la fecha actual");
				}
            }
		

        }

        String numRadAnotacion = request.getParameter(CFolio.ANOTACION_NUM_RADICACION);

        if ((numRadAnotacion == null) || numRadAnotacion.equals("")) {
            numRadAnotacion = " ";
        }

        String tipoDocumento = request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO);
        List tiposDoc = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
        String id_tipoDocumento = request.getParameter(CSolicitudRegistro.ID_TIPO_ENCABEZADO);

        if ((id_tipoDocumento != null) && !id_tipoDocumento.equals("")) {
            if (!existeId(tiposDoc, id_tipoDocumento)) {
                exception.addError("El tipo de documento digitado es invï¿½lido");
            } else {
                if (!tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
                    if (!id_tipoDocumento.equals(tipoDocumento)) {
                        exception.addError(
                            "El tipo de documento digitado no correponde al tipo de documento seleccionado");
                    } else {
                        tipoDocumento = new String(id_tipoDocumento);
                    }
                }
            }
        }

        if (((tipoDocumento == null) ||
                tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) &&
                ((id_tipoDocumento == null) || id_tipoDocumento.equals(""))) {
            exception.addError(
                "Debe escojer un tipo para el documento de la anotaciï¿½n");
        }

        String numDocumento = request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO);

        String valFechaDocumento = request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO);
        Calendar fechaDocumento = null;

        if ((valFechaDocumento == null) || valFechaDocumento.equals("")) {
            exception.addError(
                "La fecha del documento en la anotaciï¿½n no puede ser vacia");
        } else {
            fechaDocumento = darFecha(valFechaDocumento);

            if (fechaDocumento == null) {
                exception.addError(
                    "La fecha del documento en la anotaciï¿½n es invï¿½lida");
            }
			Date dHoy = new Date();
			Calendar hoy = Calendar.getInstance();
			hoy.setTime(dHoy);
			
			if(fechaRadicacion!=null){
				if(fechaRadicacion.after(hoy)){
					exception.addError("La fecha del documento debe ser anterior a la fecha actual");
				}				
				
				if(fechaDocumento !=null && fechaRadicacion!=null){
					fechaRadicacion.set(Calendar.HOUR_OF_DAY, 23);
					fechaDocumento.set(Calendar.HOUR_OF_DAY, 1);
					if (fechaRadicacion.before(fechaDocumento)) {
						 exception.addError(
							 "La fecha de radicaciï¿½n no puede ser anterior a la fecha del documento");
					 }
				}
			}
 
        }

        String valorDepartamento = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String nomDepartamento = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);

        if (valorDepartamento == null) {
            valorDepartamento = new String();
        }

        if (nomDepartamento == null) {
            nomDepartamento = new String();
        }

        if ((valorDepartamento.length() <= 0)) {
            exception.addError(
                "Debe seleccionar un departamento vï¿½lido para la oficina de procedencia del documento el la anotaciï¿½n");
        }

        String valorMunicipio = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String nomMunicipio = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);

        if (valorMunicipio == null) {
            valorMunicipio = new String();
        }

        if (nomMunicipio == null) {
            nomMunicipio = new String();
        }

        if ((valorMunicipio.length() <= 0)) {
            exception.addError(
                "Debe seleccionar un municipio vï¿½lido para la oficina de procedencia del documento el la anotaciï¿½n");
        }

        String valorVereda = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        String nomVereda = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);

        if (((valorVereda == null) || (valorVereda.trim().equals(""))) ||
                nomDepartamento.equals("") || nomMunicipio.equals("")) {
            valorVereda = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession()
                                                                                   .getServletContext()
                                                                                   .getAttribute(WebKeys.LISTA_DEPARTAMENTOS),
                    valorDepartamento, valorMunicipio);
        }
        
        
        
       if (valorVereda == null) {
            valorVereda = new String();
        }

        if (nomVereda == null) {
            nomVereda = new String();
        }

        if ((valorVereda.length() <= 0)) {
            exception.addError(
                "Debe seleccionar una vereda vï¿½lida para la oficina de procedencia del documento el la anotaciï¿½n");
        }

        String idOficina = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
         /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
         */
        String version = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        //double valorIdOficina = 0.0d;
        String numOficina = new String();
        String nomOficina = new String();

        if ((idOficina == null) || (idOficina.length() <= 0)) {
            exception.addError(
                "Debe seleccionar una oficina para la oficina de procedencia del documento en la anotaciï¿½n");
        } else {
            nomOficina = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
            numOficina = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        }

        String numEspecificacion = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);

        if (numEspecificacion != null) {
            numEspecificacion = numEspecificacion.replaceAll(",", "");
        }

        double valorEspecificacion = 0.0d;

        //if((numEspecificacion == null) || numEspecificacion.equals("")) {
        //    exception.addError("Debe ingresar el valor de la especificacion en la anotacion");
        //} else {
        try {
            valorEspecificacion = Double.parseDouble(numEspecificacion);

            if (valorEspecificacion < 0) {
                exception.addError(
                    "El valor del nï¿½mero de la especificaciï¿½n en la anotaciï¿½n no puede ser negativo o cero");
            }
        } catch (NumberFormatException e) {
            //exception.addError("El valor de la especificacion en la anotacion es invï¿½lido");
            valorEspecificacion = 0.0d;
        }
        
        
        
        //VALIDACIONES PARA LAS MATRICULAS Y ANOTACIONES PADRES E HIJAS
        String strMatriculasP = request.getParameter("textmatriculaPadre");
        String strAnotacionesP = request.getParameter("textanotacionPadre");
        
        if(strMatriculasP != null && strAnotacionesP != null){
        	if(!strMatriculasP.equals("")){
        		if(strAnotacionesP.equals("")){
        			exception.addError("La Matricula Padre "+ strMatriculasP+"  tiene que tener su respectiva Anotacion");
        		}
        	}else if(!strAnotacionesP.equals("")){
        		exception.addError("La Anotacion "+ strAnotacionesP+"  tiene que tener su Matricula Padre ");
        	}
        }
        
        
        String[] strVectorMatriculas = request.getParameterValues("textmatriculahija");
        String[] strVectorAnotaciones = request.getParameterValues("textanotacionhija");
        
        
        if(strVectorMatriculas != null && strVectorAnotaciones != null){
	        int iMatirculas = 0;
	        //boolean swMatriculasOk = false;
	        while(iMatirculas < strVectorMatriculas.length){
	        	if(strVectorMatriculas[iMatirculas] != null && !strVectorMatriculas[iMatirculas].equals("")){
	        		if(strVectorAnotaciones[iMatirculas] != null && strVectorAnotaciones[iMatirculas].equals("")){
	        			exception.addError("La Matricula Hija "+ strVectorMatriculas[iMatirculas]+"  tiene que tener su respectiva Anotacion");
	        		}
	        	}else{
	        		if(strVectorAnotaciones[iMatirculas] != null && !strVectorAnotaciones[iMatirculas].equals("")){
	        			exception.addError("La Anotacion  "+ strVectorAnotaciones[iMatirculas]+"  tiene que tener su respectiva Matricula Hija");
	        		}
	        	}
	        	iMatirculas = iMatirculas+1;
	        }
        }

        //}EN ANTIGUO SISTEMA NO SE EXIGE VALOR*/

        String nomNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);

        if ((idNaturaleza == null) || (idNaturaleza.length() <= 0)) {
            exception.addError(
                "Debe seleccionar una naturaleza jurï¿½dica para la especificaciï¿½n en la anotaciï¿½n");
        }

        //      verificar si idNaturaleza existe en la lista de contexto
        List grupoNaturalezas = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);
 /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
         * @change     : 
	 * 
	 */
        NaturalezaJuridica natJuridica = new NaturalezaJuridica();
        if (!existeNaturaleza(idNaturaleza, grupoNaturalezas, natJuridica)) {
            exception.addError("El cï¿½digo de naturaleza jurï¿½dica no es vï¿½lido");
        }

        String comentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);

		
        if (exception.getErrores().size() > 0) {
			preservarInfo(request);
            throw exception;
        }
        
        /**
        * @Autor: Edgar Lora
        * @Mantis: 0013038
        * @Requerimiento: 060_453
        */
        f.setLindero(valorLindero);
        
        this.agregarVariosCiudadanos(request);

        Anotacion anotacion = new Anotacion();

        int pos = anotaciones.size() + 1;
        anotacion.setOrden(String.valueOf(pos));

        //anotacion.setIdAnotacion(idRadicacion);
        anotacion.setFechaRadicacion(fechaRadicacion.getTime());
        
        

        Documento documento = new Documento();

        TipoDocumento tipoDoc = new TipoDocumento();

        Iterator itTiposDocs = ((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO)).iterator();

        while (itTiposDocs.hasNext()) {
            ElementoLista elemento = (ElementoLista) itTiposDocs.next();

            if (elemento.getId().equals(tipoDocumento)) {
                tipoDoc.setNombre(elemento.getValor());
            }
        }

        tipoDoc.setIdTipoDocumento(tipoDocumento);

        documento.setFecha(fechaDocumento.getTime());
        documento.setNumero(numDocumento);

        OficinaOrigen oficina = new OficinaOrigen();
        oficina.setIdOficinaOrigen(idOficina);
        /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
             */
        oficina.setVersion(Long.parseLong(version));
        oficina.setNumero(numOficina);
        oficina.setNombre(nomOficina);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(valorDepartamento);
        vereda.setIdMunicipio(valorMunicipio);
        vereda.setIdVereda(valorVereda);
        vereda.setNombre(nomVereda);
        oficina.setVereda(vereda);

        //Iterator itDepartamentos=((List)request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS)).iterator();
        Departamento depto=new Departamento();
        Municipio munic=new Municipio();
        depto.setIdDepartamento(valorDepartamento);
        depto.setNombre(nomDepartamento);
        
        munic.setDepartamento(depto);
        munic.setIdMunicipio(valorMunicipio);
        munic.setNombre(nomMunicipio);
        
        vereda.setMunicipio(munic);
        
        documento.setTipoDocumento(tipoDoc);
        documento.setOficinaOrigen(oficina);

        anotacion.setDocumento(documento);

        NaturalezaJuridica naturalezaJuridica = new NaturalezaJuridica();
        anotacion.setValor(valorEspecificacion);

        naturalezaJuridica.setIdNaturalezaJuridica(idNaturaleza);
        naturalezaJuridica.setNombre(nomNatJuridica);
         /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
         */
        naturalezaJuridica.setVersion(natJuridica.getVersion());

        anotacion.setNaturalezaJuridica(naturalezaJuridica);
        anotacion.setComentario(comentario);

        //String numRadAnotacion=turno.getIdWorkflow();
        anotacion.setNumRadicacion(numRadAnotacion);
        anotacion.setIdWorkflow(turno!=null?turno.getIdWorkflow():null);

        //SE LLENAN LOS DATOS DE LAS VARIABLES DE ANTIGUO SISTEMA
        String anotLibroAno = request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS);
        String anotLibroNum = request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS);
        String anotLibroPag = request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS);
        String anotLibroTip = request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS);

        String anotTomoAno = request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS);
        String anotTomoMun = request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS);
        String anotTomoNum = request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS);
        String anotTomoPag = request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS);
        
		preservarInfoAntiguoSistemaAnotacion(request);

        
		
		
		
		if (((anotLibroAno == null) || anotLibroAno.equals("")) &&
                ((anotLibroNum == null) || anotLibroNum.equals("")) &&
                ((anotLibroPag == null) || anotLibroPag.equals("")) &&
                ((anotLibroTip == null) || anotLibroTip.equals("")) &&
                ((anotTomoAno == null) || anotTomoAno.equals("")) &&
                ((anotTomoMun == null) || anotTomoMun.equals("")) &&
                ((anotTomoNum == null) || anotTomoNum.equals("")) &&
                ((anotTomoPag == null) || anotTomoPag.equals(""))) {
            exception.addError(
                "Debe ingresar los datos relacionados al libro y tomo donde se encuentra la anotaciï¿½n");
        }

        if (exception.getErrores().size() > 0) {
			preservarInfo(request);
            throw exception;
        }

        DatosAntiguoSistema das = new DatosAntiguoSistema();
        das.setLibroAnio(anotLibroAno);
        das.setLibroNumero(anotLibroNum);
        das.setLibroPagina(anotLibroPag);
        das.setLibroTipo(anotLibroTip);

        das.setTomoAnio(anotTomoAno);
        das.setTomoMunicipio(anotTomoMun);
        das.setTomoNumero(anotTomoNum);
        das.setTomoPagina(anotTomoPag);

        anotacion.setDatosAntiguoSistema(das);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        

        if ((ciudadanos == null) || ciudadanos.isEmpty()) {
            exception.addError(
                "Debe ingresar por lo menos un ciudadano en la anotaciï¿½n");
			preservarInfo(request);
            throw exception;
        }

        if (ciudadanos != null) {
            Iterator itPersonas = ciudadanos.iterator();

            while (itPersonas.hasNext()) {
                AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
                ciudadano.setAnotacion(anotacion);
                //ciudadano.setIdMatricula("idMatricula");
                anotacion.addAnotacionesCiudadano(ciudadano);
            }
        }
       
        Vector vectorAnotaciones;

        if (anotaciones instanceof Vector) {
            vectorAnotaciones = (Vector) anotaciones;
        } else {
            vectorAnotaciones = new Vector();

            for (int i = 0; i < anotaciones.size(); i++) {
                Anotacion anota = (Anotacion) anotaciones.get(i);
                vectorAnotaciones.add(anota);
            }

           
        }
        //request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        
        //request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, vectorAnotaciones);
        
        //eliminarInfoBasicaAnotacion(request);
        
        String numLastIdAnota = "0";
        if(request.getSession().getAttribute("LISTA_ANOTACIONES_FOLIO") != null ){
        	if( ((List)request.getSession().getAttribute("LISTA_ANOTACIONES_FOLIO")).size() > 0){
        		numLastIdAnota = ((Anotacion)((List)request.getSession().
    	        		getAttribute("LISTA_ANOTACIONES_FOLIO")).get((((List)request.getSession().
    	        			getAttribute("LISTA_ANOTACIONES_FOLIO")).size()-1))).getIdAnotacion();
	        	if(numLastIdAnota != null){
	        		numLastIdAnota = String.valueOf(Integer.valueOf(numLastIdAnota).intValue()+1);
	        	}
	        	if(numLastIdAnota == null){
	        		numLastIdAnota = ((Anotacion)((List)request.getSession().
	                		getAttribute("LISTA_ANOTACIONES_FOLIO")).get(0)).getIdAnotacion();
	        		if(numLastIdAnota != null) 
	        			numLastIdAnota = String.valueOf(Integer.valueOf(numLastIdAnota).intValue()+((List)request.getSession().getAttribute("LISTA_ANOTACIONES_FOLIO")).size());
	        		else 
	        			numLastIdAnota = String.valueOf(((List)request.getSession().getAttribute("LISTA_ANOTACIONES_FOLIO")).size()+1);
	        	}
        	}
        }
        
        if (request.getSession().getAttribute(WebKeys.ACCION_SECUNDARIA) != null &&	
        		((String)request.getSession().getAttribute(WebKeys.ACCION_SECUNDARIA)).equals(AWAntiguoSistema.EDICION_ANOTACIONES)) {
        	Folio delta = new Folio();
            Folio folio = (Folio)request.getSession().getAttribute(WebKeys.FOLIO);
            
                      
            
            delta.setIdMatricula(folio.getIdMatricula());
            //delta.setZonaRegistral(folio.getZonaRegistral());
            TipoAnotacion tipoAnot=new TipoAnotacion();
            tipoAnot.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);
            anotacion.setTipoAnotacion(tipoAnot);
            
            
            if(numLastIdAnota != null && numLastIdAnota.equals("0"))
            	numLastIdAnota= String.valueOf(folio.getLastIdAnotacion()+1);
            else if(numLastIdAnota == null)
            	numLastIdAnota= "1";
            
            
            if(strVectorMatriculas != null){
	            for(int i =0;i<strVectorMatriculas.length; i++){
	            	if(!strVectorMatriculas[i].equals("")){
		            	FolioDerivado folioDerivado = new FolioDerivado();
		            	
		        	    folioDerivado.setIdAnotacion1(strVectorAnotaciones[i]);
		        	    folioDerivado.setIdMatricula1(strVectorMatriculas[i]);
		
		        	    folioDerivado.setIdAnotacion(numLastIdAnota);
		        	    folioDerivado.setIdMatricula(folio.getIdMatricula());
		        	    anotacion.addAnotacionesHijo(folioDerivado);
	            	}
	            }
            }
            if(strMatriculasP != null && !strMatriculasP.equals("")){
            	FolioDerivado folioDerivado = new FolioDerivado();
            	
        	    folioDerivado.setIdAnotacion(strAnotacionesP);
        	    folioDerivado.setIdMatricula(strMatriculasP);

        	    folioDerivado.setIdAnotacion1(numLastIdAnota);
        	    folioDerivado.setIdMatricula1(folio.getIdMatricula());
        	    anotacion.addAnotacionesPadre(folioDerivado);
            }
            
            delta.addAnotacione(anotacion);
            Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
            
            EvnAntiguoSistema evnt = new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_EDITAR_FOLIO,delta,usuario);
            evnt.setAnotaciones(vectorAnotaciones);
            evnt.setLstAnotacionesHijas(strVectorAnotaciones);
            evnt.setLstMatriculasHijas(strVectorMatriculas);
            evnt.setTurno(turno);
            return evnt;
        } else if (request.getSession().getAttribute(WebKeys.ACCION_SECUNDARIA) != null && 
        		((String)request.getSession().getAttribute(WebKeys.ACCION_SECUNDARIA)).equals(AWAntiguoSistema.EDICION)){
            Folio delta=new Folio();
            Folio folio=(Folio)request.getSession().getAttribute(WebKeys.DELTA_FOLIO_EDICION);
            
            delta.setIdMatricula(folio.getIdMatricula());
            //delta.setZonaRegistral(folio.getZonaRegistral());
            TipoAnotacion tipoAnot=new TipoAnotacion();
            tipoAnot.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);
            anotacion.setTipoAnotacion(tipoAnot);
            
            
            if(numLastIdAnota != null && numLastIdAnota.equals("0")){            	
            	numLastIdAnota= String.valueOf(folio.getLastIdAnotacion()+1);
            }
            else if(numLastIdAnota == null)
            	numLastIdAnota= "1";
            
            if(strVectorMatriculas != null){
	            for(int i =0;i<strVectorMatriculas.length; i++){
	            	if(!strVectorMatriculas[i].equals("")){
		            	FolioDerivado folioDerivado = new FolioDerivado();
		            	
		        	    folioDerivado.setIdAnotacion1(strVectorAnotaciones[i]);
		        	    folioDerivado.setIdMatricula1(strVectorMatriculas[i]);
		
		        	    folioDerivado.setIdAnotacion(numLastIdAnota);
		        	    folioDerivado.setIdMatricula(folio.getIdMatricula());
		        	    anotacion.addAnotacionesHijo(folioDerivado);
	        	    }
	            }
            }
            if(strMatriculasP != null && !strMatriculasP.equals("")){
            	FolioDerivado folioDerivado = new FolioDerivado();
            	
        	    folioDerivado.setIdAnotacion(strAnotacionesP);
        	    folioDerivado.setIdMatricula(strMatriculasP);

        	    folioDerivado.setIdAnotacion1(numLastIdAnota);
        	    folioDerivado.setIdMatricula1(folio.getIdMatricula());
        	    anotacion.addAnotacionesPadre(folioDerivado);
            }
            
            delta.addAnotacione(anotacion);
            Usuario usuario = (Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
            
            EvnAntiguoSistema evnt = new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_EDITAR_FOLIO,delta,usuario);
            evnt.setAnotaciones(vectorAnotaciones);
            evnt.setLstAnotacionesHijas(strVectorAnotaciones);
            evnt.setLstMatriculasHijas(strVectorMatriculas);
            evnt.setTurno(turno);
            return evnt;
        }
        /*request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        
        
        List diferencias=(List)request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_ANOTACIONES);
        if (diferencias==null){
            diferencias=new ArrayList();
        }
        
        diferencias.add(anotacion);
        request.getSession().setAttribute(WebKeys.LISTA_DIFERENCIAS_ANOTACIONES, diferencias);*/
        
        
        
        // bug 5369
        local_SearchAnotacion: {
        	
        	
        	HttpSession session;
        	session = request.getSession();
        	
        	Anotacion local_Anotacion;
        	local_Anotacion = anotacion;
        	
            TipoAnotacion local_Anotacion_TipoAnotacion;
            local_Anotacion_TipoAnotacion = new TipoAnotacion();
            local_Anotacion_TipoAnotacion.setIdTipoAnotacion( CTipoAnotacion.ESTANDAR );
            local_Anotacion.setTipoAnotacion( local_Anotacion_TipoAnotacion );
            
    		// additional
            final String PARAM__LOCAL_ANOTACION_ADD = "PARAM:LOCAL_ANOTACION:ADD";
            session.setAttribute( PARAM__LOCAL_ANOTACION_ADD, local_Anotacion );
        	
        	
        } // :local_SearchAnotacion 
        
        // capturar la anotacion
        
        return doProcess_AntigSistHojaRuta_SaveInfo_Step2( request, vectorAnotaciones, strVectorMatriculas, strVectorAnotaciones, strMatriculasP, strAnotacionesP, numLastIdAnota);        
        
        // return null;
    }
    
    
    private Evento agregarMatricula(HttpServletRequest request) {
    	//implemente todo el codigo aqui
    	return null;
    }
    
    private void eliminarInfoBasicaAnotacion(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.ANOTACION_NUM_RADICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
         /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
         */
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        request.getSession().removeAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NUM_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS);
        request.getSession().removeAttribute("tipo_oficina");
        request.getSession().removeAttribute("tipo_nom_oficina");
        request.getSession().removeAttribute("numero_oficina");
        request.getSession().removeAttribute("id_oficina");
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
         */
        request.getSession().removeAttribute("version");
    
    }
    
    private boolean existeNaturaleza(String idNaturaleza, List gruposNatualeza,NaturalezaJuridica natJuridica) {
        for (Iterator it = gruposNatualeza.iterator(); it.hasNext();) {
            GrupoNaturalezaJuridica grupo = (GrupoNaturalezaJuridica) it.next();

            if (grupo.getNaturalezaJuridicas().iterator() == null) {
                continue;
            }

            for (Iterator itNat = grupo.getNaturalezaJuridicas().iterator();
                    itNat.hasNext();) {
                NaturalezaJuridica naturaleza = (NaturalezaJuridica) itNat.next();

                if (naturaleza.getIdNaturalezaJuridica().equals(idNaturaleza)) {
                     /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaciï¿½n_de_Naturaleza_Jurï¿½dica
         * @change     : se asignan valores a la naturaleza guridica
	 */
                    
                    natJuridica.setDominioNaturalezaJuridica(naturaleza.getDominioNaturalezaJuridica());
                    natJuridica.setGrupoNaturalezaJuridica(naturaleza.getGrupoNaturalezaJuridica());
                    natJuridica.setIdNaturalezaJuridica(naturaleza.getIdNaturalezaJuridica());
                    natJuridica.setNombre(naturaleza.getNombre());
                    natJuridica.setVersion(naturaleza.getVersion());
                    return true;
                }
            }
        }

        return false;
    }
    
    /**
     * @param request
     * @param accion1
     * @return
     * @throws AccionWebException
     */
    private Evento agregarVariosCiudadanos(HttpServletRequest request) throws AccionWebException {
        ValidacionParametrosException exception = new ValidacionParametrosException();
        
        try {
            return agregarVariosCiudadanos(request, exception);
        } catch (AccionWebException e) {
            throw e;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new AccionWebException(t.getLocalizedMessage());
        }
    }
    
    private Evento agregarVariosCiudadanos(HttpServletRequest request,
            ValidacionParametrosException exception) throws AccionWebException {
            /*
            preservarInfoBasicaFolio(request);
            preservarInfoBasicaAnotacion(request);
            preservarInfoCancelacion(request);
            */
            preservarInfoBasicaVariosCiudadanos(request); //varios
            
            Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);

            //Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);
            int numCiudadanosOriginales = 0;
            List ciudadanosOriginales=(List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
            
            if (ciudadanosOriginales!=null){
                numCiudadanosOriginales+=ciudadanosOriginales.size();
            } else {
            	ciudadanosOriginales = new ArrayList();
            }
            
            List ciudadanos = null;//(List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

            if (ciudadanos == null) {
                ciudadanos = new Vector();
            }
            
                		
    		List ciudadanosFinales = new Vector(ciudadanos);

            int numCiudadanosTabla = this.numeroRegistrosTablaAgregarCiudadanos(request);

            for (int i = 0; i < numCiudadanosTabla+numCiudadanosOriginales; i++) {
                if (agregoPersona(request, i)) {
                    int b = i + 1;
                    boolean datosBien = true;
                    String tipoId = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA +
                            i);

                    if ((tipoId == null) || tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError(
                            "Debe escojer un tipo de identificaciï¿½n para la persona " +
                            b + " en la anotaciï¿½n");
                        datosBien = false;
                    }

                    String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA +
                            i);
                    String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA +
                            i);
                    String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA +
                            i);
                    String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA +
                            i);

                    if (tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
                        if ((apellido1 == null) || apellido1.trim().equals("")) {
                            exception.addError(
                                "Primer apellido invï¿½lido para la persona " + b);
                            datosBien = false;
                        }
                    } else {
                        if ((numId == null) || numId.trim().equals("")) {
                            exception.addError(
                                "Documento Invï¿½lido para la persona " + b);
                            datosBien = false;
                        }

                        double valorId = 0.0d;

                        if (numId == null) {
                            exception.addError(
                                "El nï¿½mero de identificaciï¿½n de la persona " + b +
                                " en la anotaciï¿½n es invï¿½lido");
                            datosBien = false;
                        } else {
                            /*
             * @author : CTORRES
             * @change : Se implemento la validaciï¿½n para solo permitir caracteres alfanumï¿½ricos en 
             *           numId cuando el tipo de identificaciï¿½n es PASAPORTE.
             * Caso Mantis : 11056
             * No Requerimiento: 073_151
             */
                            if(tipoId.equals("PS"))
                            {
                                String regexSL = "^[a-zA-Z]+$";
                                String regexSN = "^[0-9]+$";
                                String regexLN = "^[a-zA0-Z9]+$";
                                java.util.regex.Pattern patternSL = java.util.regex.Pattern.compile(regexSL);
                                java.util.regex.Pattern patternSN = java.util.regex.Pattern.compile(regexSN);
                                java.util.regex.Pattern patternLN = java.util.regex.Pattern.compile(regexLN);
                                boolean esC = false;
                                if(patternSL.matcher(numId).matches())  esC=true;
                                else if(patternSN.matcher(numId).matches())  esC=true;
                                else if(patternLN.matcher(numId).matches())  esC=true;
                                else {
                                    exception.addError("El valor del documento de la persona " + b + " en la anotaciï¿½n es invï¿½lido");
                                    datosBien = false;                                                    }
                                }
                            else{
                            if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                                try {
                                    valorId = Double.parseDouble(numId);

                                    if (valorId <= 0) {
                                        exception.addError(
                                            "El valor del documento de la persona " +
                                            b + " no puede ser negativo o cero");
                                        datosBien = false;
                                    }
                                } catch (NumberFormatException e) {
                                    exception.addError(
                                        "El nï¿½mero de identificaciï¿½n de la persona " +
                                        b + " en la anotaciï¿½n es invï¿½lido");
                                    datosBien = false;
                                }
                            }
                            }
                            
                        }

                        if (tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                            if ((apellido1 == null) || apellido1.trim().equals("")) {
                                exception.addError(
                                    "Razï¿½n social invï¿½lida del campo " + b);
                                datosBien = false;
                            }
                        } else {
                            if ((nombres == null) || nombres.equals("")) {
                                exception.addError(
                                    "Debe ingresar el nombre de la persona " + b +
                                    " en la anotaciï¿½n");
                                datosBien = false;
                            }

                            if ((apellido1 == null) || apellido1.equals("")) {
                                exception.addError(
                                    "Debe ingresar el primer apellido de la persona " +
                                    b + " en la anotaciï¿½n");
                                datosBien = false;
                            }
                        }
                    }

                    String tipoIntervencion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION +
                            i);

                    if ((tipoIntervencion == null) ||
                            tipoIntervencion.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError(
                            "Debe ingresar un tipo de intervenciï¿½n para la persona " +
                            b + " en la anotaciï¿½n");
                        datosBien = false;
                    }

                    AnotacionCiudadano c;
                    Iterator ic = ciudadanos.iterator();

                    while (ic.hasNext()) {
                        c = (AnotacionCiudadano) ic.next();

                        if ((tipoIntervencion.equals(c.getRolPersona())) &&
                                (numId.equals(c.getCiudadano().getDocumento())) &&
                                (tipoId.equals(c.getCiudadano().getTipoDoc())) &&
                                !(numId.length() == 0)) {
                            exception.addError(
                                "La persona no puede tener dos veces el mismo rol");
                            datosBien = false;
                        }
                    }

                    String participacion = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION +
                            i);
                    

                    if ((participacion != null) && !participacion.equals("")) {
                        if(participacion.length()>50){
                        	exception.addError("El campo participaciï¿½n de una persona no puede tener mas de 50 caracteres");
                        	datosBien = false;
                        }
                    }

                    if (datosBien) {
                        AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
                        Ciudadano ciudadano = new Ciudadano();
                        ciudadano.setApellido1(apellido1);

                        if (apellido2 != null) {
                            ciudadano.setApellido2(apellido2);
                        }

                        ciudadano.setNombre(nombres);

                        if (numId != null) {
                            ciudadano.setDocumento(numId);
                        }
                        
                    	//Se setea el circulo del ciudadano
                        Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
                		ciudadano.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);

                        ciudadano.setTipoDoc(tipoId);
                        anotacionCiudadano.setCiudadano(ciudadano);
                        anotacionCiudadano.setRolPersona(tipoIntervencion);
                        anotacionCiudadano.setParticipacion(participacion);

                        String marcaPropietario = request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION +
                                i);

                        if ((marcaPropietario == null) ||
                                marcaPropietario.equals("")) {
                            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
                        } else if (marcaPropietario.equals("X")) {
                            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
                        } else if (marcaPropietario.equals("I")) {
                            anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
                        }

                        ciudadanos.add(anotacionCiudadano);
                    }
                }
            }

            if (exception.getErrores().size() > 0) {
                preservarInfoBasicaCiudadano(request);
                throw exception;
            }

            eliminarInfoBasicaVariosCiudadanos(request);

            //if (!existeEnListaCiudadanos(anotacionCiudadano, ciudadanos)) {
            List listaCompletaCiudadanos = new Vector();

            listaCompletaCiudadanos.addAll(ciudadanos);

            request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION,
                listaCompletaCiudadanos);

            /*} else {
                exception.addError("El ciudadano que esta intentado ingresar ya se encuentra registrado en la anotacion");
                throw exception;
            }*/
            /*      String vista=(String)request.getSession().getAttribute(WebKeys.ULTIMA_VISTA_TEMPORAL);
                    if (vista!=null){
                        request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL,vista);
                    }
            */
            return null;
        }
    
    private void eliminarInfoBasicaVariosCiudadanos(HttpServletRequest request) {
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        for (int i = 0; i < numCiud; i++) {
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA +
                i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NUM_ID_PERSONA +
                i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA +
                i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA +
                i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA +
                i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION +
                i);
            request.getSession().removeAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION +
                i);
            request.getSession().removeAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION +
                i);
        }
    }
    
    private boolean existeId(List tiposDoc, String id_tipo_encabezado) {
        for (Iterator it = tiposDoc.iterator(); it.hasNext();) {
            ElementoLista e = (ElementoLista) it.next();

            if (e.getId().equals(id_tipo_encabezado)) {
                return true;
            }
        }

        return false;
    }

    private static Calendar darFecha(String fechaInterfaz) {
    	
		try {
			/*date = */DateFormatUtil.parse(fechaInterfaz);
			if(fechaInterfaz.indexOf("-")!=-1){
				return null;
			}
		} catch (ParseException e) {
			return null;
		} catch (Throwable t) {
			return null;
		}    	
    	
        Calendar calendar = Calendar.getInstance();
        String[] partido = fechaInterfaz.split("/");

        if (partido.length == 3) {
            int dia = Integer.parseInt(partido[0]);
            int mes = Integer.parseInt(partido[1]) - 1;
            int año = Integer.parseInt(partido[2]);
            calendar.set(año, mes, dia);

            if ((calendar.get(Calendar.YEAR) == año) &&
                    (calendar.get(Calendar.MONTH) == mes) &&
                    (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
                return calendar;
            }
        }

        return null;
    }
    
    private boolean agregoPersona(HttpServletRequest request, int i) {
        int cantDatosValidos = 0;
        String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA +
                i);

        if ((numId != null) && (numId.trim().length() > 0)) {
            cantDatosValidos++;
        }

        String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA +
                i);

        if ((apellido1 != null) && (apellido1.trim().length() > 0)) {
            cantDatosValidos++;
        }

        //Comentado dado que no es obligatorio el segundo apellido para ningun tipo de
        //Docuemento de identidad.

        /*String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
        if (apellido2 != null) {
                return true;
        }*/
        String tipoID = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA);
        String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA +
                i);
        boolean verificarNombre = false;

        if ((tipoID != null) &&
                (tipoID.equals(CCiudadano.TIPO_DOC_ID_CEDULA) ||
                tipoID.equals(CCiudadano.TIPO_DOC_ID_CEDULA_EXTRANJERIA) ||
                tipoID.equals(CCiudadano.TIPO_DOC_ID_TARJETA))) {
            verificarNombre = true;
        }

        if (verificarNombre && (nombres != null) &&
                (nombres.trim().length() > 0)) {
            cantDatosValidos++;
        }

        String valPorcentaje = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION +
                i);

        if ((valPorcentaje != null) && (valPorcentaje.trim().length() > 0)) {
            cantDatosValidos++;
        }

        if (cantDatosValidos > 0) {
            return true;
        }

        return false;
    }
    
   
    /**
     * @param request
     * @return
     */
    private Evento preservarInfo(HttpServletRequest request) {
        preservarInfoBasicaAnotacion(request);
        preservarInfoAntiguoSistemaAnotacion(request);
        preservarInfoBasicaCiudadano(request);
        return null;
    }
    
    private void preservarInfoAntiguoSistemaAnotacion(
            HttpServletRequest request) {
            request.getSession().setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS));

            request.getSession().setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS));

            request.getSession().setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS));

            request.getSession().setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS));

            request.getSession().setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS));

            request.getSession().setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS,
                request.getParameter(
                    CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS));

            request.getSession().setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS));

            request.getSession().setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS,
                request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS));
        }
    
    private void preservarInfoBasicaCiudadano(HttpServletRequest request) {
        if (request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA,
                request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA,
                request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA,
                request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA,
                request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA,
                request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION,
                request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION));
        }

        if (request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION,
                request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION));
        }
    }
    
    private void preservarInfoBasicaAnotacion(HttpServletRequest request) {
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO,
                request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,
                request.getParameter(
                    CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        }
         /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
         */
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION,
                request.getParameter(
                    CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION));
        }
        if (request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION,
                request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO,
                request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_RADICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_RADICACION,
                request.getParameter(CFolio.ANOTACION_FECHA_RADICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_RADICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_RADICACION,
                request.getParameter(CFolio.ANOTACION_NUM_RADICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION,
                request.getParameter(
                    CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION));
        }
        
        if (request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION,
                request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION));
        }
        
        if (request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO,
                request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO,
                request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO,
                request.getParameter(
                    CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,
                request.getParameter(
                    CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC,
                request.getParameter(
                    CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,
                request.getParameter(
                    CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA,
                request.getParameter(
                    CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA,
                request.getParameter(
                    CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute("tipo_oficina",
                request.getParameter("tipo_oficina"));
            request.getSession().setAttribute("tipo_nom_oficina",
                request.getParameter("tipo_nom_oficina"));
            request.getSession().setAttribute("numero_oficina",
                request.getParameter("numero_oficina"));
            request.getSession().setAttribute("id_oficina",
                request.getParameter("id_oficina"));
             /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
         */
             request.getSession().setAttribute("version",
                request.getParameter("version"));
           
        }

        if (request.getParameter(
                    CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID,
                request.getParameter(
                    CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
        }

        if (request.getParameter(
                    CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM,
                request.getParameter(
                    CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
        }
        
        preservarInfoAntiguoSistemaAnotacion(request);
    }
    
    private void preservarInfoBasicaVariosCiudadanos(HttpServletRequest request) {
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        String key = null;
        Object parametro = null;
        
        //logger.debug("Numero de Cuidadanos en  preservarInfoBasicaVariosCiudadanos " + numCiud);
        
        for (int i = 0; i < numCiud; i++) {
            key = CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i;
            parametro = request.getParameter(key);
            
            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
            parametro = request.getParameter(key);
            
            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i;
            parametro = request.getParameter(key);
            
            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_TIPO_ID_PERSONA + i;
            parametro = request.getParameter(key);
            
            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
            parametro = request.getParameter(key);
            
            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
            parametro = request.getParameter(key);
            
            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
            parametro = request.getParameter(key);
            
            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
            parametro = request.getParameter(key);
            
            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }
        }
    }
    
    
    private void preservarInfoBasicaVariosCiudadanosInicial(HttpServletRequest request, List ciudadanos) {
        
    	int numCiud = ciudadanos.size();
    	
        String key = null;
        //Object parametro = null;
        
       
        for (int i = 0; i < numCiud; i++) {
        	AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) ciudadanos.get(i);
        	
        	//String part = "&nbsp;";
            String participacion ="";
            //String rol = "&nbsp;";
            String apellido1 = "&nbsp;";
            String apellido2 = "&nbsp;";
            String nombre = "&nbsp;";
            String nombreCompleto = "&nbsp;";
            String marcaProp = "&nbsp;";
            String tipoDoc = "&nbsp;";
            String documento = "&nbsp;";

            if(anotacionCiudadano.getParticipacion()!=null){
			    participacion=anotacionCiudadano.getParticipacion();
			    /*if(participacion.length()>3){
			    	part= participacion.substring(0,3);
			    }else{
			    	part= participacion;
			    }*/
			    participacion=anotacionCiudadano.getParticipacion();
			}

            /*if (anotacionCiudadano.getRolPersona() != null) {
                rol = anotacionCiudadano.getRolPersona();
            }*/

            if (anotacionCiudadano.getCiudadano().getApellido1() != null) {
                apellido1 = anotacionCiudadano.getCiudadano()
                                              .getApellido1();
            }

            if (anotacionCiudadano.getCiudadano().getApellido2() != null) {
                apellido2 = anotacionCiudadano.getCiudadano()
                                              .getApellido2();
            }

            if (anotacionCiudadano.getCiudadano().getNombre() != null) {
                nombre = anotacionCiudadano.getCiudadano().getNombre();
            }

            nombreCompleto = apellido1 + " " + apellido2 + " " +
                nombre + " ";

            if (nombreCompleto.equals("")) {
                nombreCompleto = "&nbsp;";
            }

            if (anotacionCiudadano.getStringMarcaPropietario() != null) {
                marcaProp = anotacionCiudadano.getStringMarcaPropietario();
            }

            if (anotacionCiudadano.getCiudadano().getTipoDoc() != null) {
                tipoDoc = anotacionCiudadano.getCiudadano().getTipoDoc();
            }

            if (anotacionCiudadano.getCiudadano().getDocumento() != null) {
                documento = anotacionCiudadano.getCiudadano()
                                              .getDocumento();
            }
            
        	key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
        	request.getSession().setAttribute(key, participacion);
        	
        	/*key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
        	logger.debug("Key 2: " + request.getSession().getAttribute(key));
        	*/

            key = CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i;
            request.getSession().setAttribute(key, marcaProp);
            
            key = CFolio.ANOTACION_TIPO_ID_PERSONA + i;
            request.getSession().setAttribute(key, tipoDoc);
            
            key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
            request.getSession().setAttribute(key, documento);
            
            key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
            request.getSession().setAttribute(key, apellido1);
            
            key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
            request.getSession().setAttribute(key, apellido2);
            
            key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
            request.getSession().setAttribute(key, nombre);
        }
    }
    
    /**
    *
    * @param request
    * @return
    * @throws AccionWebException
    */
    private void eliminarInfoBasicaUltimoVariosCiudadanos(HttpServletRequest request) {
        
    	//String orden = request.getParameter("NUM_ANOTACION_TEMPORAL");
        //logger.debug("eliminarInfoBasicaUltimoVariosCiudadanos " + orden);
        
    	int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
    	
        String key = null;

        int i = numCiud - 1;
        
        /*logger.debug("eliminarInfoBasicaUltimoVariosCiudadanos");
        logger.debug("numCiud ----> " + numCiud);
        logger.debug("i ----> " + i);*/
        
        
        key = CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i;
        request.getSession().removeAttribute(key);
        
        
        key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_TIPO_ID_PERSONA + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
        request.getSession().removeAttribute(key);
                
        key = CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
        request.getSession().removeAttribute(key);
    }
    
    /**
    *
    * @param request
    * @return
    * @throws AccionWebException
    */
    private void eliminarInfoBasicaUltimoVariosCiudadanosEdicion(HttpServletRequest request) {
        
    	//Anotacion anota = (Anotacion) request.getSession().getAttribute(WebKeys.ANOTACION_EDITAR);
        /*Anotacion anotacion = null;
        if (((String)request.getSession().getAttribute(WebKeys.ACCION_SECUNDARIA)).equals(AWAntiguoSistema.EDICION)){
            anotacion = new Anotacion();
        }else{
            if (anotaciones!=null){
                Iterator it = anotaciones.iterator();
                while (it.hasNext()){
                    Anotacion temp=(Anotacion)it.next();
                    if (temp.getOrden().equals(anota.getOrden())){
                         anotacion = temp;
                    }
                }
            }
            
            if (anotacion==null){
                throw new AccionWebException();
            }
        }*/
        
    	//String orden = anota.getIdAnotacion();
    	String orden = (String) request.getSession().getAttribute(AWCalificacion.NUM_ANOTACION_TEMPORAL);
    	
    	if (orden.equals("")) {
    		orden = "-1";
    	} 
    	
        //logger.debug("eliminarInfoBasicaUltimoVariosCiudadanosEdicion " + orden);
        
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
    	
        String key = null;

        int i = numCiud - 1;
        
        /*logger.debug("eliminarInfoBasicaUltimoVariosCiudadanos");
        logger.debug("numCiud ----> " + numCiud);
        logger.debug("i ----> " + i);*/
        
        
        key = CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_TIPO_ID_PERSONA + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
        request.getSession().removeAttribute(key);
                
        key = CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
        request.getSession().removeAttribute(key);
        
        key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
        request.getSession().removeAttribute(key);
        
        List anotaciones = (List)request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        List ciudadanosSet = new ArrayList();
        
        List anotacionesCopiadas = new ArrayList();
        
        if (anotaciones != null){
            Iterator it = anotaciones.iterator();
            while(it.hasNext()){
                Anotacion anot = (Anotacion)it.next();
                if (anot.getOrden().equals(orden)){
                	ciudadanosSet = anot.getAnotacionesCiudadanos();
                	int sizeCuidadanos = ciudadanosSet.size();
                	if (sizeCuidadanos > i ) {
                		AnotacionCiudadano ac = (AnotacionCiudadano) ciudadanosSet.get(sizeCuidadanos - 1); 
                    	anot.removeAnotacionesCiudadano(ac);
                        anotacionesCopiadas.add(anot);
                	} else {
                		anotacionesCopiadas.add(anot);
                	}
                	
                } else {
                	anotacionesCopiadas.add(anot);
                }
            }    
        }
        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO,anotacionesCopiadas);

    }
    
    /**
    *
    * @param request
    * @return
    * @throws AccionWebException
    */
   private Evento disminuirNumeroVariosCiudadanos(HttpServletRequest request){
       int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
       
       preservarInfoBasicaVariosCiudadanos(request);
       eliminarInfoBasicaUltimoVariosCiudadanos(request);
       
       if (numCiud > 1) {
           numCiud--;
       }
           
       Integer num = new Integer(numCiud);
       request.getSession().setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
       request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO,
           new Boolean(true));
      
       preservarInfoBasicaAnotacion(request);
       preservarInfoBasicaVariosCiudadanos(request);
       
       return null;
   }
   
   /**
   *
   * @param request
   * @return
   * @throws AccionWebException
   */
  private Evento disminuirNumeroMatricula(HttpServletRequest request){
      int numMatri = this.numeroRegistrosTablaMatriculas(request);
      
      //preservarInfoBasicaVariosCiudadanos(request);
      //eliminarInfoBasicaUltimoVariosCiudadanos(request);
      
      if (numMatri > 1) {
    	  numMatri--;
      }
          
      Integer num = new Integer(numMatri);
      request.getSession().setAttribute(NUM_REGISTROS_TABLA_MATRICULAS, num);
      request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO,
          new Boolean(true));
     
      //preservarInfoBasicaAnotacion(request);
      //preservarInfoBasicaVariosCiudadanos(request);
      
      return null;
  }
  
  
  /**
  *
  * @param request
  * @return
  * @throws AccionWebException
  */
 private Evento disminuirNumeroMatriculaE(HttpServletRequest request){
     int numMatri = this.numeroRegistrosTablaMatriculasE(request);
     
     //preservarInfoBasicaVariosCiudadanos(request);
     //eliminarInfoBasicaUltimoVariosCiudadanos(request);
     
     if (numMatri > 1) {
   	  numMatri--;
     }
         
     Integer num = new Integer(numMatri);
     request.getSession().setAttribute(NUM_REGISTROS_TABLA_MATRICULAS_EDIT, num);
     request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO,
         new Boolean(true));
    
     //preservarInfoBasicaAnotacion(request);
     //preservarInfoBasicaVariosCiudadanos(request);
     
     return null;
 }
   
   /**
   *
   * @param request
   * @return
   * @throws AccionWebException
   */
  private Evento disminuirNumeroVariosCiudadanosEdicion(HttpServletRequest request){
      int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
      
      preservarInfoBasicaVariosCiudadanos(request);
      eliminarInfoBasicaUltimoVariosCiudadanosEdicion(request);
      
      if (numCiud > 1) {
          numCiud--;
      }
          
      Integer num = new Integer(numCiud);
      request.getSession().setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
      request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO,
          new Boolean(true));
     
      preservarInfoBasicaAnotacion(request);
      preservarInfoBasicaVariosCiudadanos(request);
      
      return null;
  }
    
    /**
    *
    * @param request
    * @return
    * @throws AccionWebException
    */
   private Evento aumentarNumeroVariosCiudadanos(HttpServletRequest request){
       
       int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
       numCiud++;
       
       preservarInfoBasicaAnotacion(request);
       preservarInfoBasicaVariosCiudadanos(request);
       
       Integer num = new Integer(numCiud);
       request.getSession().setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
       request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO,
           new Boolean(true));
       
       return null;
   }
   
   private Evento aumentarNumeroMatriculas(HttpServletRequest request){
       
       int numMatri = this.numeroRegistrosTablaMatriculas(request);
       numMatri++;
       
       preservarInfoBasicaAnotacion(request);
       preservarInfoBasicaVariosCiudadanos(request);
       
       Integer num = new Integer(numMatri);
       request.getSession().setAttribute(NUM_REGISTROS_TABLA_MATRICULAS, num);
       request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO,
           new Boolean(true));
       
       return null;
   }
   
   private Evento aumentarNumeroMatriculasE(HttpServletRequest request){
       
       int numMatri = this.numeroRegistrosTablaMatriculasE(request);
       numMatri++;
       
       preservarInfoBasicaAnotacion(request);
       preservarInfoBasicaVariosCiudadanos(request);
       
       Integer num = new Integer(numMatri);
       request.getSession().setAttribute(NUM_REGISTROS_TABLA_MATRICULAS_EDIT, num);
       request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO,
           new Boolean(true));
       
       return null;
   }
   
   private int numeroRegistrosTablaMatriculas(
       HttpServletRequest request) {
       
	   Integer num = (Integer) request.getSession().getAttribute(NUM_REGISTROS_TABLA_MATRICULAS);
       int numMatri;

       if (num == null) {
           numMatri = 1;
       } else {
           numMatri = num.intValue();
       }

       return numMatri;
   }
   
   
   private int numeroRegistrosTablaMatriculasE(
	       HttpServletRequest request) {
	       
		   Integer num = (Integer) request.getSession().getAttribute(NUM_REGISTROS_TABLA_MATRICULAS_EDIT);
	       int numMatri;

	       if (num == null) {
	           numMatri = 1;
	       } else {
	           numMatri = num.intValue();
	       }

	       return numMatri;
	   }

   private int numeroRegistrosTablaAgregarCiudadanos(
           HttpServletRequest request) {

           Integer num = (Integer) request.getSession().getAttribute(NUM_REGISTROS_TABLA_CIUDADANOS);
           int numCiud;

           if (num == null) {
               numCiud = DEFAULT_NUM_CIUDADANOS_TABLA;
           } else {
               numCiud = num.intValue();
           }

           return numCiud;
       }
   
    /**
     * @param request
     * @return
     */
    private Evento consultaFolioComplementacion(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        ErrorCreacionFolioException exception=new ErrorCreacionFolioException();
        Folio folio = new Folio();
        preservarInfoBasicaFolio(request);

        String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
        request.getSession().removeAttribute(WebKeys.MENSAJE);

        if (idMatricula == null) {
            idMatricula = (String) request.getSession().getAttribute(CFolio.ID_MATRICULA);
        }
        
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        
        //TFS 3772: SOLO SE PUEDEN CONSULTAR LAS MATRICULAS PERTENEZCAN  AL MISMO CIRCULO DEL TURNO
        
        if(!idMatricula.startsWith(turno.getIdCirculo())){
        	exception.addError("La matricula a consultar no pertenece al cï¿½rculo del turno");
            throw exception;
        }else{
        	String tipoComp = request.getParameter(CFolio.SELECCIONAR_FOLIO  + AWModificarFolio.FOLIO_COMPLEMENTACION);
            request.getSession().setAttribute(CFolio.SELECCIONAR_FOLIO  + AWModificarFolio.FOLIO_COMPLEMENTACION,tipoComp);

            folio.setIdMatricula(idMatricula);
        }

        

        return new EvnCertificado(usuarioAuriga, usuarioSIR, EvnCertificado.CONSULTA_FOLIO_COMPLEMENTACION, folio);
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarDireccion(HttpServletRequest request) {
        preservarInfoBasicaFolio(request);
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));
        
        List difDirecciones=(List)request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
        
        //if(difDirecciones != null && direcciones != null && direcciones.size() != difDirecciones.size())
        //if(aplicacionNumero > 0)
        	//aplicacionNumero = aplicacionNumero -1;
        
        if (difDirecciones == null){
            difDirecciones=new ArrayList();       
        }
        
        Direccion dir = null;
        if(direcciones != null && aplicacionNumero <=direcciones.size())
        	dir = (Direccion) direcciones.get(aplicacionNumero);
        
        if (dir == null ) {
        	dir = new Direccion();
        }
        
        int dif = -1;
        Iterator itDif = difDirecciones.iterator();
        int i=0;
                
        try {
	        while(itDif.hasNext()){
	            Direccion temp = (Direccion)itDif.next();
	            if (temp.getIdDireccion().equals(dir.getIdDireccion())){
	                dif = i;
	            }
	            i++;
	        }
        } catch (Exception e) {
        	dif = aplicacionNumero;
        }
        
        if (dif!=-1){
            difDirecciones.remove(dif);
        }else{
            dir.setToDelete(true);
            difDirecciones.add(dir);
        }
        request.getSession().setAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION, difDirecciones);
        
        direcciones.remove(aplicacionNumero);
        
        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION,
            direcciones);

        return null;
    }


    /**
     * @param request
     * @return
     */
    private Evento agregarDireccion(HttpServletRequest request) throws AccionWebException{
        preservarInfoBasicaFolio(request);
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        ErrorCreacionFolioException exception = new ErrorCreacionFolioException();

        String valorEje1 = request.getParameter(CFolio.FOLIO_EJE1);
        String valorValor1 = request.getParameter(CFolio.FOLIO_VALOR1);
        String valorEje2 = request.getParameter(CFolio.FOLIO_EJE2);
        String valorValor2 = request.getParameter(CFolio.FOLIO_VALOR2);
        String complemento = request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR);
        String especificacion = request.getParameter(AWModificarFolio.FOLIO_ESPECIFICACION_DIR);


        if ((valorEje1.length() <= 0) ||
                valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError(
                "Debe seleccionar el primer eje vï¿½lido para la direcciï¿½n");
        } else {
            if (!valorEje1.equals(CDireccion.SIN_DIRECCION)) {
                if (valorValor1.length() <= 0) {
                    exception.addError(
                        "Debe ingresar valor vï¿½lido para el primer eje de la direcciï¿½n");
                }

                if ((valorEje2.length() <= 0) ||
                        valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
                    valorEje2 = new String();
                } else {
                    if (valorValor2.length() <= 0) {
                        exception.addError(
                            "Debe ingresar valor vï¿½lido para el segundo eje  de la direcciï¿½n");
                    }
                }
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List ejes = (List) request.getSession().getServletContext()
                                  .getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        Direccion direccion = new Direccion();
        direccion.setEspecificacion((especificacion + " "+complemento).toUpperCase());

        if (ejes != null) {
            Iterator itEje = ejes.iterator();

            while (itEje.hasNext()) {
                ElementoLista elementoEje = (ElementoLista) itEje.next();
                Eje eje;

                if (elementoEje.getId().equals(valorEje1)) {
                    eje = new Eje();
                    eje.setIdEje(elementoEje.getId());
                    eje.setNombre(elementoEje.getValor());
                    direccion.setEje(eje);
                    direccion.setValorEje(valorValor1);
                }

                if (elementoEje.getId().equals(valorEje2)) {
                    eje = new Eje();
                    eje.setIdEje(elementoEje.getId());
                    eje.setNombre(elementoEje.getValor());
                    direccion.setEje1(eje);
                }

                if (valorValor2 != null) {
                    direccion.setValorEje1(valorValor2);
                }
            }
        } else {
            exception.addError("La lista de los ejes no se encontrï¿½");
        }

        List diferencias=(List)request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
        
        if (diferencias==null){
            diferencias=new ArrayList();
        }
        
        Integer idDir=(Integer)request.getSession().getAttribute(WebKeys.ID_DIRECCION);
        if (idDir==null){
            idDir=new Integer(direcciones==null?1:direcciones.size()+1);
        }
        
        direccion.setIdDireccion(idDir.toString());
        Integer nextId=new Integer(idDir.intValue()+1);
        request.getSession().setAttribute(WebKeys.ID_DIRECCION, nextId);
        diferencias.add(direccion);
        request.getSession().setAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION, diferencias);
        
        direcciones.add(direccion);

        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION,
            direcciones);
        eliminarInfoDireccion(request);
        
        
        return null;       
    }

    /**
     * @param request
     * @return
     */
    private Evento terminarFolio(HttpServletRequest request) throws AccionWebException{
    	
    	// TODO: la validacion para folios creados se debe mover 
    	// al tratar de enviar el turno a otra fase,
    	// pues es posible que el usuario cree el folio 
    	// pero no alcance a introducir anotaciones
/*    	
        ErrorCrearAnotacionException exception=new ErrorCrearAnotacionException();
        Folio editado=(Folio)request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        Folio folio=copiarFolio(editado);
        
        Turno turno=(Turno)request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

        List anotaciones = new ArrayList();
        List anotacionesTemp = null;
        anotacionesTemp = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        if (anotacionesTemp != null) {
            Iterator it = anotacionesTemp.iterator();

            while (it.hasNext()) {
                Anotacion anotacion = (Anotacion) it.next();
                anotaciones.add(anotacion);
            }
        }

        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotaciones);

        if ((anotaciones == null) || (anotaciones.size() == 0)) {
            exception.addError("No se puede crear un folio sin anotaciones");
            throw exception;
        } 
        
        int tam = anotaciones.size();

        for (int i = 0; i < tam; i++) {
            Anotacion anota = (Anotacion) anotaciones.get(i);
            TipoAnotacion tipoAnota = new TipoAnotacion();
            tipoAnota.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);
            anota.setTipoAnotacion(tipoAnota);
        }
        
        
        if (anotaciones != null) {
            Iterator itAnotaciones = anotaciones.iterator();

            while (itAnotaciones.hasNext()) {
                folio.addAnotacione((Anotacion) itAnotaciones.next());
            }
        }
        
        
        TurnoFolio tFolio = new TurnoFolio();
        tFolio.setTurno(turno);
        folio.addTurnosFolio(tFolio);
        folio.setEstado(null);
        
        return new EvnFolio(usuarioAuriga, EvnFolio.CREACION_FOLIO_CREADO,turno, fase, EvnFolio.CREACION_FOLIO_CREADO, folio, usuario);
*/        
        // TODO: finalizacion de creacion de folio
        // doProcess_AntigSistHojaRuta_SaveInfo_Step3
    	
        Evento local_Result;
        local_Result = doProcess_AntigSistHojaRuta_SaveInfo_Step3( request );
        return local_Result;
        
        // ------------------------
        
        
        
        
    }

    /**
     * @param editado
     * @return
     */
    private Folio copiarFolio(Folio editado) {
        if (editado!=null){
            Folio nuevo=new Folio();
            nuevo.setFechaApertura(editado.getFechaApertura());
            nuevo.setCodCatastral(editado.getCodCatastral());
            nuevo.setLindero(editado.getLindero());
            nuevo.setComplementacion(editado.getComplementacion());
            nuevo.setTipoPredio(editado.getTipoPredio());
            nuevo.setZonaRegistral(editado.getZonaRegistral());
            nuevo.setDocumento(editado.getDocumento());
            Iterator it = editado.getDirecciones().iterator();
            while(it.hasNext()){
                Direccion direc=(Direccion)it.next();
                nuevo.addDireccione(direc);
            }
            return nuevo;
        }
        return null;
    }

    /**
     * @param editado
     * @return
     */
    private Evento regresarFolio(HttpServletRequest request) {
        this.preservarInfoBasicaAnotacion(request);
        List anotaciones = (List)request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        if(anotaciones!=null)
        	request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO,anotaciones);
        return null;
    }
    
    
    /**
     * @param request
     * @return
     */
    private Evento crearFolio(HttpServletRequest request) throws AccionWebException{
    	
    	this.preservarInfoBasicaFolio(request);
    	if(request.getParameter(WebKeys.LISTA_ANOTACIONES_FOLIO)!=null)
			request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO,
		    request.getParameter(WebKeys.LISTA_ANOTACIONES_FOLIO));
		List anotaciones = (List)request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        ErrorCreacionFolioException exception=new ErrorCreacionFolioException();
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        if (turno == null) {
            throw new AccionWebException("No se encontro el turno en la sesiï¿½n");
        }

        //SE RECUPERA LA INFORMACIï¿½N DEL FORMULARIO
        //String valorRadicacion = request.getParameter(CFolio.FOLIO_NUM_RADICACION);
        String valorDepartamento = request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO);

        if (valorDepartamento == null) {
            valorDepartamento = new String();
        }

        if (valorDepartamento.length() <= 0) {
            exception.addError("Debe seleccionar un departamento.");
        }

        String valorMunicipio = request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC);

        if (valorMunicipio == null) {
            valorMunicipio = new String();
        }

        if (valorMunicipio.length() <= 0) {
            exception.addError("Debe seleccionar un municipio vï¿½lido.");
        }

        String valorVereda = request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA);

        if (valorVereda == null) {
            valorVereda = new String();
        }

        String valorTipoPredio = request.getParameter(CFolio.FOLIO_TIPO_PREDIO);

        if ((valorTipoPredio == null) ||
                (valorTipoPredio.equals("SIN_SELECCIONAR"))) {
            exception.addError("Debe seleccionar un tipo de predio vï¿½lido");
        }

        boolean revisarVereda = true;

        if (valorTipoPredio.equals(CTipoPredio.TIPO_URBANO)) {
            revisarVereda = false;
        }

        if ((valorVereda.length() <= 0) && revisarVereda) {
            exception.addError("Debe seleccionar una vereda vï¿½lida.");
        }

        String valorComplementacion = request.getParameter(CFolio.FOLIO_COMPLEMENTACION);
		if(valorComplementacion!=null){
			valorComplementacion = valorComplementacion.toUpperCase();
		}

        String idComplementacion = request.getParameter(CFolio.FOLIO_ID_COMPLEMENTACION);
        String tipoAccion = request.getParameter(CFolio.SELECCIONAR_FOLIO +
                AWModificarFolio.FOLIO_COMPLEMENTACION);

        if ((tipoAccion != null) && tipoAccion.equals(CFolio.ASOCIAR)) {
            if (idComplementacion == null) {
                exception.addError(
                    "Debe ingresar un identificador para la complementaciï¿½n vï¿½lido");
            }
        }

        String valorLindero = request.getParameter(CFolio.FOLIO_LINDERO);

        if (valorLindero == null) {
            exception.addError("Debe ingresar informacion del lindero vï¿½lida");
        }

        String valorCodCatastral = request.getParameter(CFolio.FOLIO_COD_CATASTRAL);

        if (valorCodCatastral == null) {
            exception.addError("Debe ingresar un codigo catastral vï¿½lido");
        }
        
        Folio folio=new Folio();
        
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        folio.setFechaApertura(new Date(System.currentTimeMillis()));

        folio.setCodCatastral(valorCodCatastral);

        if (circulo == null) {
            circulo = new Circulo();
        }

        folio.setLindero(valorLindero);

        //SE LE INGRESA LA COMPLEMENTACIï¿½N AL FOLIO
        Complementacion comp = new Complementacion();

        String tipoComp = request.getParameter(CFolio.SELECCIONAR_FOLIO +
                AWModificarFolio.FOLIO_COMPLEMENTACION);

        if (tipoComp.equals(CFolio.NUEVA) || tipoComp.equals(CFolio.COPIAR)) {
            comp.setComplementacion(valorComplementacion);
        }

        if (tipoComp.equals(CFolio.ASOCIAR)) {
            comp.setIdComplementacion(idComplementacion);
            comp.setComplementacion(valorComplementacion);
        }

        folio.setComplementacion(comp);

        //SE INGRESA EL TIPO DE PREDIO
        List tiposPredio = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);

        if (tiposPredio != null) {
            Iterator itPredios = tiposPredio.iterator();

            while (itPredios.hasNext()) {
                ElementoLista elementoTipoPredio = (ElementoLista) itPredios.next();

                if (elementoTipoPredio.getId().equals(valorTipoPredio)) {
                    TipoPredio tipoPredio = new TipoPredio();
                    tipoPredio.setIdPredio(valorTipoPredio);
                    tipoPredio.setNombre(elementoTipoPredio.getValor());
                    folio.setTipoPredio(tipoPredio);
                }
            }
        } else {
            exception.addError("La lista de los tipos de predio no se encontro");
        }

        ZonaRegistral zona = new ZonaRegistral();
        zona.setCirculo(circulo);

        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(valorDepartamento);

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(valorMunicipio);

        municipio.setDepartamento(departamento);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(valorDepartamento);
        vereda.setIdMunicipio(valorMunicipio);
        vereda.setIdVereda(valorVereda);
        vereda.setMunicipio(municipio);
        zona.setVereda(vereda);

        folio.setZonaRegistral(zona);

        Documento documento = new Documento();
        TipoDocumento tipoDoc = new TipoDocumento();

        tipoDoc.setIdTipoDocumento(CTipoDocumento.ID_TIPO_CERTIFICADO);
        documento.setFecha(new Date());
        documento.setTipoDocumento(tipoDoc);
        
        folio.setDocumento(documento);
        if (exception.getErrores().size() > 0) {
            throw exception;
        }
        
        Solicitud solicitud = turno.getSolicitud();
        
        if (solicitud instanceof SolicitudRegistro) {
            SolicitudRegistro registro = (SolicitudRegistro) solicitud;
            folio.setDocumento(registro.getDocumento());
        }else if (solicitud instanceof SolicitudCorreccion){
            String tipoDocumento = request.getParameter(CFolio.FOLIO_TIPO_DOCUMENTO);
            List tiposDoc = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
            String id_tipoDocumento = request.getParameter(CFolio.FOLIO_ID_TIPO_ENCABEZADO);

            if ((id_tipoDocumento != null) && !id_tipoDocumento.equals("")) {
                if (!existeId(tiposDoc, id_tipoDocumento)) {
                    exception.addError("El tipo de documento digitado es invï¿½lido");
                } else {
                    if (!tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
                        if (!id_tipoDocumento.equals(tipoDocumento)) {
                            exception.addError(
                                "El tipo de documento digitado no correponde al tipo de documento seleccionado");
                        } else {
                            tipoDocumento = new String(id_tipoDocumento);
                        }
                    }
                }
            }

            if (((tipoDocumento == null) ||
                    tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) &&
                    ((id_tipoDocumento == null) || id_tipoDocumento.equals(""))) {
                exception.addError(
                    "Debe escojer un tipo para el documento de la anotaciï¿½n");
            }

            String numDocumento = request.getParameter(CFolio.FOLIO_NUM_DOCUMENTO);

            String valFechaDocumento = request.getParameter(CFolio.FOLIO_FECHA_DOCUMENTO);
            Calendar fechaDocumento = null;

            if ((valFechaDocumento == null) || valFechaDocumento.equals("")) {
                exception.addError(
                    "La fecha del documento en la anotaciï¿½n no puede ser vacia");
            } else {
                fechaDocumento = darFecha(valFechaDocumento);

                if (fechaDocumento == null) {
                    exception.addError(
                        "La fecha del documento en la anotaciï¿½n es invï¿½lida");
                }
            }

            String valorDepartamentoDoc = request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO);
            String nomDepartamento = request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO);

            if (valorDepartamentoDoc == null) {
                valorDepartamentoDoc = new String();
            }

            if (nomDepartamento == null) {
                nomDepartamento = new String();
            }

            if ((valorDepartamentoDoc.length() <= 0)) {
                exception.addError(
                    "Debe seleccionar un departamento vï¿½lido para la oficina de procedencia del documento el la anotaciï¿½n");
            }

            String valorMunicipioDoc = request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC);
            String nomMunicipio = request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC);

            if (valorMunicipioDoc == null) {
                valorMunicipioDoc = new String();
            }

            if (nomMunicipio == null) {
                nomMunicipio = new String();
            }

            if ((valorMunicipioDoc.length() <= 0)) {
                exception.addError(
                    "Debe seleccionar un municipio vï¿½lido para la oficina de procedencia del documento el la anotaciï¿½n");
            }

            String valorVeredaDoc = request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA);
            String nomVereda = request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA);

            if (((valorVeredaDoc == null) || (valorVeredaDoc.trim().equals(""))) ||
                    nomDepartamento.equals("") || nomMunicipio.equals("")) {
                valorVeredaDoc = ValidacionDatosOficinaOrigen.obtenerVereda((List) request.getSession()
                                                                                       .getServletContext()
                                                                                       .getAttribute(WebKeys.LISTA_DEPARTAMENTOS),
                                                                                       valorDepartamentoDoc, valorMunicipioDoc);
            }
            
           if (valorVeredaDoc == null) {
               valorVeredaDoc = new String();
            }

            if (nomVereda == null) {
                nomVereda = new String();
            }

            if ((valorVeredaDoc.length() <= 0)) {
                exception.addError(
                    "Debe seleccionar una vereda vï¿½lida para la oficina de procedencia del documento el la anotaciï¿½n");
            }

            String idOficina = request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA);
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
             */
            String version = request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_OFICINA_VERSION);
            //double valorIdOficina = 0.0d;
            String numOficina = new String();
            String nomOficina = new String();

            if ((idOficina == null) || (idOficina.length() <= 0)) {
                exception.addError(
                    "Debe seleccionar una oficina para la oficina de procedencia del documento en la anotaciï¿½n");
            } else {
                nomOficina = request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO);
                numOficina = request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM);
            }
            if (!exception.getErrores().isEmpty()){
                throw exception;
            }
            TipoDocumento tipoDocum = new TipoDocumento();

            Iterator itTiposDocs = ((List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO)).iterator();

            while (itTiposDocs.hasNext()) {
                ElementoLista elemento = (ElementoLista) itTiposDocs.next();

                if (elemento.getId().equals(tipoDocumento)) {
                    tipoDocum.setNombre(elemento.getValor());
                }
            }

            tipoDocum.setIdTipoDocumento(tipoDocumento);

            documento.setFecha(fechaDocumento.getTime());
            documento.setNumero(numDocumento);

            OficinaOrigen oficina = new OficinaOrigen();
            oficina.setIdOficinaOrigen(idOficina);
            oficina.setNumero(numOficina);
            oficina.setNombre(nomOficina);
            /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
             */
            oficina.setVersion(Long.parseLong(version));
            Vereda veredaDoc = new Vereda();
            veredaDoc.setIdDepartamento(valorDepartamentoDoc);
            veredaDoc.setIdMunicipio(valorMunicipioDoc);
            veredaDoc.setIdVereda(valorVeredaDoc);
            veredaDoc.setNombre(nomVereda);
            oficina.setVereda(veredaDoc);

            //Iterator itDepartamentos=((List)request.getSession().getServletContext().getAttribute(WebKeys.LISTA_DEPARTAMENTOS)).iterator();
            Departamento depto=new Departamento();
            Municipio munic=new Municipio();
            depto.setIdDepartamento(valorDepartamentoDoc);
            depto.setNombre(nomDepartamento);
            
            munic.setDepartamento(depto);
            munic.setIdMunicipio(valorMunicipioDoc);
            munic.setNombre(nomMunicipio);
            
            veredaDoc.setMunicipio(munic);
            
            documento.setTipoDocumento(tipoDocum);
            documento.setOficinaOrigen(oficina);
            folio.setDocumento(documento);
        }
        
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if ((direcciones != null) && (direcciones.size() > 0)) {
            Iterator it = direcciones.iterator();

            while (it.hasNext()) {
                Direccion direccion = (Direccion) it.next();
                if((direccion.getIdMatricula()==null || direccion.getIdMatricula().trim().equals(""))){
                	direccion.setIdDireccion(null);
                }
                folio.addDireccione(direccion);
            }
        }
        
        request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, folio);
        request.getSession().setAttribute(WebKeys.ACCION_SECUNDARIA, AWAntiguoSistema.CREACION);
        
        // bug 5165
        //  guardar el estado del folio en datasource;
        //  consultar estado del folio desde datoasource.
        
        // ------------------------

        // bug 5165:
        // recuperar el estado del datasuurce
        if(request.getSession().getAttribute("PARAM:LOCAL_FOLIO_IDMATRICULA")==null){
        	return(doProcess_AntigSistHojaRuta_SaveInfo_Step1( request ));
        }else{
        	Turno param_Turno;
            param_Turno = (Turno)request.getSession().getAttribute( WebKeys.TURNO );
            
            // added
            
            Folio param_FolioEditado;
            param_FolioEditado = (Folio)request.getSession().getAttribute( WebKeys.FOLIO_EDITADO );
            param_FolioEditado.setIdMatricula((String)request.getSession().getAttribute("PARAM:LOCAL_FOLIO_IDMATRICULA"));
            
            
            // added (usado para que no genere error la validacionen negocio)
            
            TurnoFolio local_TurnoFolio;
            local_TurnoFolio = new TurnoFolio();
            local_TurnoFolio.setTurno( param_Turno );
            param_FolioEditado.addTurnosFolio(local_TurnoFolio);
            param_FolioEditado.setEstado( null ); // ?
            
            org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
            param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute( SMARTKeys.USUARIO_EN_SESION );

            gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
            param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute( WebKeys.USUARIO );
            Vector anotacion = new Vector();
            if(anotaciones!=null){
	            for(int i=0; i<anotaciones.size();i++){
	            	anotacion.add((Anotacion)anotaciones.get(i));
	            }
            }
        	Evn_AntigSistHojaRutaSaveInfo local_Result;
            local_Result = new Evn_AntigSistHojaRutaSaveInfo(Evn_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP2__EVENT);
            local_Result.setUsuarioAuriga( param_UsuarioAuriga ); // o
            local_Result.setUsuarioSir( param_UsuarioSir );       // *
            local_Result.setTurno( param_Turno );  
            local_Result.setAnotaciones(anotacion);
            local_Result.setFolioEditado(param_FolioEditado);// *
            local_Result.setCrearFolio(true);
            return local_Result;
        // ------------------------
        }
    
     //return null;
}

    private void eliminarInfoBasicaFolio(HttpServletRequest request){
        request.getSession().removeAttribute(CFolio.FOLIO_NUM_RADICACION);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA);
        request.getSession().removeAttribute(CFolio.FOLIO_ESTADO_FOLIO);
        request.getSession().removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        request.getSession().removeAttribute(CFolio.FOLIO_COMPLEMENTACION);
        request.getSession().removeAttribute(CFolio.FOLIO_LINDERO);
        request.getSession().removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        request.getSession().removeAttribute(CFolio.FOLIO_COD_CATASTRAL);
    }
    
    private void eliminarInfoDireccion(HttpServletRequest request){
        request.getSession().removeAttribute(CFolio.FOLIO_EJE1);
        request.getSession().removeAttribute(CFolio.FOLIO_EJE2);
        request.getSession().removeAttribute(CFolio.FOLIO_VALOR1);
        request.getSession().removeAttribute(CFolio.FOLIO_VALOR2);
        request.getSession().removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
    }
    
    private void preservarInfoBasicaFolio(HttpServletRequest request) {
        //Se salva temporalmente la informacion sobre el folio. Esto no hace persistente el folio, ni cumple con el requerimiento de grabar de manera temporal
        if (request.getParameter(CFolio.FOLIO_NUM_RADICACION) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_NUM_RADICACION,
                request.getParameter(CFolio.FOLIO_NUM_RADICACION));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO,
                request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO,
                request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC,
                request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC,
                request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA,
                request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA,
                request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_ESTADO_FOLIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_ESTADO_FOLIO,
                request.getParameter(CFolio.FOLIO_ESTADO_FOLIO));
        }

        if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_TIPO_PREDIO,
                request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
        }

        if (request.getParameter(CFolio.FOLIO_COMPLEMENTACION) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION,
                request.getParameter(CFolio.FOLIO_COMPLEMENTACION));
        }

        if (request.getParameter(CFolio.FOLIO_LINDERO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LINDERO,
                request.getParameter(CFolio.FOLIO_LINDERO));
        }

        if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_TIPO_PREDIO,
                request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
        }

        if (request.getParameter(CFolio.FOLIO_COD_CATASTRAL) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COD_CATASTRAL,
                request.getParameter(CFolio.FOLIO_COD_CATASTRAL));
        }

        if (request.getParameter(CFolio.FOLIO_EJE1) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_EJE1,
                request.getParameter(CFolio.FOLIO_EJE1));
        }

        if (request.getParameter(CFolio.FOLIO_EJE2) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_EJE2,
                request.getParameter(CFolio.FOLIO_EJE2));
        }

        if (request.getParameter(CFolio.FOLIO_VALOR1) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_VALOR1,
                request.getParameter(CFolio.FOLIO_VALOR1));
        }

        if (request.getParameter(CFolio.FOLIO_VALOR2) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_VALOR2,
                request.getParameter(CFolio.FOLIO_VALOR2));
        }

        if (request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR,
                request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR));
        }
        
        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM,
                request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO,
                request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO,
                request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA,
                request.getParameter(
                    CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA));
        }
        /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
             */
        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_OFICINA_VERSION) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_OFICINA_VERSION,
                request.getParameter(
                    CFolio.FOLIO_OFICINA_DOCUMENTO_OFICINA_VERSION));
        }
        if (request.getParameter(CFolio.FOLIO_TIPO_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_TIPO_DOCUMENTO,
                request.getParameter(CFolio.FOLIO_TIPO_DOCUMENTO));
        }

        if (request.getParameter(CFolio.FOLIO_NUM_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_NUM_DOCUMENTO,
                request.getParameter(CFolio.FOLIO_NUM_DOCUMENTO));
        }
        if (request.getParameter(CFolio.FOLIO_FECHA_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_FECHA_DOCUMENTO,
                request.getParameter(CFolio.FOLIO_FECHA_DOCUMENTO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO,
                request.getParameter(
                    CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO,
                request.getParameter(
                    CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC,
                request.getParameter(
                    CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC,
                request.getParameter(
                    CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA,
                request.getParameter(
                    CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA,
                request.getParameter(
                    CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute("tipo_oficina",
                request.getParameter("tipo_oficina"));
            request.getSession().setAttribute("folio_tipo_nom_oficina",
                request.getParameter("tipo_nom_oficina"));
            request.getSession().setAttribute("folio_numero_oficina",
                request.getParameter("numero_oficina"));
            request.getSession().setAttribute("folio_id_oficina",
                request.getParameter("id_oficina"));
             /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Cï¿½digo_Notaria_NC
             */
            request.getSession().setAttribute("folio_version",
                request.getParameter("version"));
            
        }
    }

    /**
     * @param request
     * @return
     */
    private Evento imprimirFolio(HttpServletRequest request) {
        String matricula=request.getParameter(WebKeys.ITEM);
        Turno turno=(Turno)request.getSession().getAttribute(WebKeys.TURNO);
        Iterator itSol=turno.getSolicitud().getSolicitudFolios().iterator();
        SolicitudFolio folio = null;
        while(itSol.hasNext()){
            SolicitudFolio temp=(SolicitudFolio)itSol.next();
            if (temp.getIdMatricula().equals(matricula)){
                folio=temp;
            }
        }
        
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        String uid = request.getSession().getId();
        return new EvnAntiguoSistema(usuarioAuriga, EvnAntiguoSistema.HOJA_RUTA_IMPRIMIR_FOLIO, folio,turno,usuarioNeg, uid);
    }
    
    /**
     * @param request
     * @return
     */
    private Evento refrescarAnotacion(HttpServletRequest request) {
        request.getSession().setAttribute(CAnotacionCiudadano.SECUENCIA, request.getParameter(CAnotacionCiudadano.SECUENCIA));
        request.getSession().setAttribute("CAMBIO", "SI");
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);
        return null;
    }
    
    /**
     * @param request
     * @return
     */
    private Evento refrescarAnotacionEdicion(HttpServletRequest request) {
    	request.getSession().setAttribute(CAnotacionCiudadano.SECUENCIA, request.getParameter(CAnotacionCiudadano.SECUENCIA));
        request.getSession().setAttribute("CAMBIO", "SI");
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento desasociarFolio(HttpServletRequest request) {
        String matricula=request.getParameter(WebKeys.ITEM);
        Turno turno=(Turno)request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg=(Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_DESASOCIAR_FOLIO,matricula,turno, usuarioNeg);
    }
    
    /**
     * @param request
     * @return
     */
    private Evento eliminarFolio(HttpServletRequest request) {
        String matricula=request.getParameter(WebKeys.ITEM);
        Turno turno=(Turno)request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg=(Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        request.getSession().removeAttribute(CFolio.FOLIO_CREADO_HOJA_RUTA);
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_ELIMINAR_FOLIO,matricula,turno, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento editarFolio(HttpServletRequest request) {
        
        request.getSession().removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
        request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        
        String matricula=request.getParameter(WebKeys.ITEM);
        Turno turno=(Turno)request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg=(Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        
        Folio folio = null;
        Iterator itSolFolios = (turno.getSolicitud().getSolicitudFolios()).iterator();
        while(itSolFolios.hasNext()){
            SolicitudFolio sol = (SolicitudFolio)itSolFolios.next();
            if(sol.getIdMatricula().equals(matricula)){
                folio = sol.getFolio();
            }
        }
        EvnConsultaFolio ev= new EvnConsultaFolio(usuarioAuriga,folio);
        ev.setTurno(turno);
        ev.setUsuarioNeg(usuarioNeg);
        return ev;
        
        // TODO: revisarlo para editar
    }

    /**
     * @param request
     * @return
     */
    private Evento asociarFolio(HttpServletRequest request) throws AccionWebException{
        
        org.auriga.core.modelo.transferObjects.Usuario usuario= (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
        String numFolio=request.getParameter(CFolio.ID_MATRICULA);
        
        ValidacionParametrosHojaRutaException exception = new ValidacionParametrosHojaRutaException();
        /**
        * @autor Edgar Lora
        * @mantis 11987
        */
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
        try {
            if(validacionesSIR.isEstadoFolioBloqueado(numFolio)){
                exception.addError("El folio que desea agregar esta en estado de 'Bloqueado'.");
                throw exception;
            }
        } catch (GeneralSIRException ex) {
            Logger.getLogger(AWAntiguoSistema.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (numFolio==null||numFolio.equals(circulo.getIdCirculo()+"-")){
            exception.addError("Debe ingresar una matrï¿½cula");
            throw exception;
        }
        
        Iterator itFolios=turno.getSolicitud().getSolicitudFolios().iterator();
        while(itFolios.hasNext()){
            SolicitudFolio sol=(SolicitudFolio)itFolios.next();
            if (sol.getIdMatricula().equals(numFolio)){
                exception.addError("La matricula ya esta asociada");
                throw exception;
            }
        }
        return new EvnAntiguoSistema(usuario,EvnAntiguoSistema.HOJA_RUTA_ASOCIAR_MATRICULA,numFolio,turno, usuarioNeg);
    }

    /**
     * @param request
     * @return
     */
    private Evento digitarHojaRuta(HttpServletRequest request) throws AccionWebException{
        eliminarInfoBasicaFolio(request);
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoDireccion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        request.getSession().removeAttribute("PARAM:LOCAL_FOLIO_IDMATRICULA");
        request.getSession().removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
        request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        
        // ------------------------

        // bug 5165:
        // recuperar el estado del datasuurce
        
        Evento local_Result;
        local_Result = doProcess_AntigSistHojaRuta_SaveInfo_Step0( request );
        
        // ------------------------
        
        return local_Result;
    }
    
    // cargar info 
    private Evento doProcess_AntigSistHojaRuta_SaveInfo_Step0(HttpServletRequest request) throws AccionWebException {
    	
        
    	
    	HttpSession session;
    	session = request.getSession();
    	
    	
        // session data -----------------------------------------------------------------

        org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
        param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute( SMARTKeys.USUARIO_EN_SESION );

        gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
        param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute( WebKeys.USUARIO );

        Circulo param_Circulo;
        param_Circulo = (Circulo) session.getAttribute( WebKeys.CIRCULO );

        Turno param_Turno;
        param_Turno = (Turno)session.getAttribute( WebKeys.TURNO );
        
        // added
        

        // build-message -----------------------------------------------------------------

        Evn_AntigSistHojaRutaSaveInfo local_Result;
        local_Result = new Evn_AntigSistHojaRutaSaveInfo( Evn_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP0__EVENT );
    	
        // get-set injection
        
        local_Result.setUsuarioAuriga( param_UsuarioAuriga ); // o
        local_Result.setUsuarioSir( param_UsuarioSir );       // *
        local_Result.setTurno( param_Turno );                 // *
        
        // ---------------------------------------------------------------------------------
        
        // send-message -----------------------------------------------------------------
        return local_Result;
        // ---------------------------------------------------------------------------------
		
	} // end-method: 
    
    // guarda info folio
    private Evento doProcess_AntigSistHojaRuta_SaveInfo_Step1(HttpServletRequest request) throws AccionWebException {
    	
    	
    	HttpSession session;
    	session = request.getSession();
    	
    	
        // session data -----------------------------------------------------------------

        org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
        param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute( SMARTKeys.USUARIO_EN_SESION );

        gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
        param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute( WebKeys.USUARIO );

        Circulo param_Circulo;
        param_Circulo = (Circulo) session.getAttribute( WebKeys.CIRCULO );

        Turno param_Turno;
        param_Turno = (Turno)session.getAttribute( WebKeys.TURNO );
        
        // added
        
        Folio param_FolioEditado;
        param_FolioEditado = (Folio)session.getAttribute( WebKeys.FOLIO_EDITADO );
        
        
        
        // added (usado para que no genere error la validacionen negocio)
        
        TurnoFolio local_TurnoFolio;
        local_TurnoFolio = new TurnoFolio();
        local_TurnoFolio.setTurno( param_Turno );
        param_FolioEditado.addTurnosFolio(local_TurnoFolio);
        param_FolioEditado.setEstado( null ); // ?
        
        
        

        // build-message -----------------------------------------------------------------

        Evn_AntigSistHojaRutaSaveInfo local_Result;
        local_Result = new Evn_AntigSistHojaRutaSaveInfo( Evn_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP1__EVENT  );
    	
        // get-set injection
        
        local_Result.setUsuarioAuriga( param_UsuarioAuriga ); // o
        local_Result.setUsuarioSir( param_UsuarioSir );       // *
        local_Result.setTurno( param_Turno );                 // *
        
        // added
        local_Result.setFolioEditado( param_FolioEditado );
        // ---------------------------------------------------------------------------------

        
        // send-message -----------------------------------------------------------------
        return local_Result;
        // ---------------------------------------------------------------------------------     
        
		
	} // end-method:    
    
    
    
    
    
    
	/*    	
    Turno turno=(Turno)request.getSession().getAttribute(WebKeys.TURNO);
    org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
    Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
    Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

    List anotaciones = new ArrayList();
    List anotacionesTemp = null;
    anotacionesTemp = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);


//------------------------------------------
    request.getSession().removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
    request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
    request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
    request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
    
    String matricula=request.getParameter(WebKeys.ITEM);
    Turno turno=(Turno)request.getSession().getAttribute(WebKeys.TURNO);
    org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
    Usuario usuarioNeg=(Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
    
    Folio folio = null;
    Iterator itSolFolios = (turno.getSolicitud().getSolicitudFolios()).iterator();
    while(itSolFolios.hasNext()){
        SolicitudFolio sol = (SolicitudFolio)itSolFolios.next();
        if(sol.getIdMatricula().equals(matricula)){
            folio = sol.getFolio();
        }
    }
    EvnConsultaFolio ev= new EvnConsultaFolio(usuarioAuriga,folio);
    ev.setTurno(turno);
    ev.setUsuarioNeg(usuarioNeg);
    return ev;


*/     	
    
    
    
    
    
    
    
    
    
    
    
    
    

    // guarda info anotacion												strVectorMatriculas, strVectorAnotaciones, strMatriculasP, strAnotacionesP, numLastIdAnota
    private Evento doProcess_AntigSistHojaRuta_SaveInfo_Step2(HttpServletRequest request, Vector anotaciones, 
    		String [] strVectorMatriculas, String [] strVectorAnotaciones, String strMatriculasP, 
    		String strAnotacionesP,String numLastIdAnota) throws AccionWebException {
    	
    	HttpSession session;
    	session = request.getSession();
    	
    	
        // session data -----------------------------------------------------------------

        org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
        param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute( SMARTKeys.USUARIO_EN_SESION );

        gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
        param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute( WebKeys.USUARIO );

        Circulo param_Circulo;
        param_Circulo = (Circulo) session.getAttribute( WebKeys.CIRCULO );

        Turno param_Turno;
        param_Turno = (Turno)session.getAttribute( WebKeys.TURNO );
        
        // additional
        // se construye un folio, se fija el key, y se adiciona la anotacion editada;
        
    	Folio param_FolioEditado;
    	param_FolioEditado = null;
    	
    	build_Folio: {
    		
    		param_FolioEditado = new Folio();
    		
            Anotacion param_Anotacion;       
            final String PARAM__LOCAL_ANOTACION_ADD = "PARAM:LOCAL_ANOTACION:ADD";
            param_Anotacion = (gov.sir.core.negocio.modelo.Anotacion)session.getAttribute( PARAM__LOCAL_ANOTACION_ADD );
            
            // load t1_state
            Folio param_t1_Folio;
            param_t1_Folio = (Folio)session.getAttribute( WebKeys.FOLIO_EDITADO );
            
            // load t2 state
            param_FolioEditado.setIdMatricula(     param_t1_Folio.getIdMatricula()     );
            //param_FolioEditado.setZonaRegistral( param_t1_Folio.getZonaRegistral() );
            
            param_FolioEditado.addAnotacione( param_Anotacion );
            
            if(numLastIdAnota != null && numLastIdAnota.equals("0") || numLastIdAnota == null)
            	numLastIdAnota= "1";
            
            
            if(strVectorMatriculas != null){
	            for(int i =0;i<strVectorMatriculas.length; i++){
	            	if(!strVectorMatriculas[i].equals("")){
		            	FolioDerivado folioDerivado = new FolioDerivado();
		            	
		        	    folioDerivado.setIdAnotacion1(strVectorAnotaciones[i]);
		        	    folioDerivado.setIdMatricula1(strVectorMatriculas[i]);
		
		        	    folioDerivado.setIdAnotacion(numLastIdAnota);
		        	    folioDerivado.setIdMatricula(param_t1_Folio.getIdMatricula());
		        	    param_Anotacion.addAnotacionesHijo(folioDerivado);
	            	}
	            }
            }
            if(strMatriculasP != null && !strMatriculasP.equals("")){
            	FolioDerivado folioDerivado = new FolioDerivado();
            	
        	    folioDerivado.setIdAnotacion(strAnotacionesP);
        	    folioDerivado.setIdMatricula(strMatriculasP);

        	    folioDerivado.setIdAnotacion1(numLastIdAnota);
        	    folioDerivado.setIdMatricula1(param_t1_Folio.getIdMatricula());
        	    param_Anotacion.addAnotacionesPadre(folioDerivado);
            }
            
            
            
            
    		
    	} // :build_Folio 
    	
        
        // build-message -----------------------------------------------------------------

    	Evn_AntigSistHojaRutaSaveInfo local_Result;
        local_Result = new Evn_AntigSistHojaRutaSaveInfo( Evn_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP2__EVENT );
    	
        // get-set injection
        
        local_Result.setUsuarioAuriga( param_UsuarioAuriga ); // o
        local_Result.setUsuarioSir( param_UsuarioSir );       // *
        local_Result.setTurno( param_Turno );                 // *
        // add
        local_Result.setFolioEditado( param_FolioEditado );
        local_Result.setAnotaciones(anotaciones);
        // ---------------------------------------------------------------------------------
        
        // send-message -----------------------------------------------------------------
        return local_Result;
        // ---------------------------------------------------------------------------------
		
		
	} // end-method:
    

    // finaliza
    private Evento doProcess_AntigSistHojaRuta_SaveInfo_Step3(HttpServletRequest request) throws AccionWebException {
    	
    	HttpSession session;
    	session = request.getSession();
    	
    	
        // session data -----------------------------------------------------------------

        org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
        param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute( SMARTKeys.USUARIO_EN_SESION );

        gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
        param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute( WebKeys.USUARIO );

        Circulo param_Circulo;
        param_Circulo = (Circulo) session.getAttribute( WebKeys.CIRCULO );

        Turno param_Turno;
        param_Turno = (Turno)session.getAttribute( WebKeys.TURNO );

        // build-message -----------------------------------------------------------------

        Evn_CorrSimpleMain_VerAlertasOptions local_Result;
        local_Result = new Evn_CorrSimpleMain_VerAlertasOptions( Evn_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP3__EVENT );
    	
        // get-set injection
        
        local_Result.setUsuarioAuriga( param_UsuarioAuriga ); // o
        local_Result.setUsuarioSir( param_UsuarioSir );       // *
        local_Result.setTurno( param_Turno );                 // *
        // ---------------------------------------------------------------------------------
        
        // send-message -----------------------------------------------------------------
        return local_Result;
        // ---------------------------------------------------------------------------------
		
		
		
	} // end-method:    
    

    
// cargar info 
    private void doEndProcess_AntigSistHojaRuta_SaveInfo_Step0(HttpServletRequest request, EvnResp_AntigSistHojaRutaSaveInfo respuesta ) {
    	
	} // end-method:     
    
//  guarda info folio
    private void doEndProcess_AntigSistHojaRuta_SaveInfo_Step1(HttpServletRequest request, EvnResp_AntigSistHojaRutaSaveInfo respuesta ) {
    	
    	if( null == respuesta ) {
    		return;
    	}
    	
    	HttpSession session;
    	session = request.getSession();
    	
    	Turno param_Turno;
    	Folio param_FolioEditado;
    	
    	param_Turno        = respuesta.getTurno();
    	param_FolioEditado = respuesta.getFolioEditado();
    	
    	if( null == param_Turno ) {
    		return;
    	}
    	
    	session.setAttribute( WebKeys.TURNO, param_Turno );
    	if(!param_FolioEditado.isDefinitivo())
    		session.setAttribute(CFolio.FOLIO_CREADO_HOJA_RUTA, CFolio.FOLIO_CREADO_HOJA_RUTA);
		session.removeAttribute( WebKeys.FOLIO_EDITADO );
		
    	if( null == param_FolioEditado ) {
    		return;
    	}
    	
		session.setAttribute( WebKeys.FOLIO_EDITADO, param_FolioEditado );
		
		session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, param_FolioEditado.getDirecciones());
		
		// additional
        final String PARAM__LOCAL_FOLIO_ID_MATRICULA = "PARAM:LOCAL_FOLIO_IDMATRICULA";
		
        String local_Folio_IdMatricula;        
        local_Folio_IdMatricula = param_FolioEditado.getIdMatricula();
		
		session.setAttribute( PARAM__LOCAL_FOLIO_ID_MATRICULA, local_Folio_IdMatricula );
		
    	
	} // end-method:     

//  guarda info anotacion 
    private void doEndProcess_AntigSistHojaRuta_SaveInfo_Step2(HttpServletRequest request, EvnResp_AntigSistHojaRutaSaveInfo respuesta ) {
    	
	} // end-method:     
    
// finaliza
    private void doEndProcess_AntigSistHojaRuta_SaveInfo_Step3(HttpServletRequest request, EvnResp_AntigSistHojaRutaSaveInfo respuesta ) {
    	
	} // end-method:     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @param request
     * @return
     */
    private Evento avanzarMasDocumentos(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        
        String razon = request.getParameter(CDatosAntiguoSistema.RAZON_DEVOLUCION_HOJA_RUTA);
        
        ValidacionParametrosHojaRutaException exception=new ValidacionParametrosHojaRutaException();
        if (razon==null){
            exception.addError("Debe escribir una razï¿½n para la devoluciï¿½n");
        }
        
        if (razon!=null&&razon.trim().equals("")){
            exception.addError("Debe escribir una razï¿½n para la devoluciï¿½n");
        }
        
        if (!exception.getErrores().isEmpty()){
            throw exception;
        }
        
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
        
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_MAS_DOCUMENTOS,turno, fase, estacion, usuario, circulo, proceso, razon, EvnAntiguoSistema.HOJA_RUTA_MAS_DOCUMENTOS_WF);
    }
    
    /**
     * @param request
     * @return
     */
    private Evento avanzarRechazado(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        
        String razon = request.getParameter(CDatosAntiguoSistema.RAZON_DEVOLUCION_HOJA_RUTA);
        
        ValidacionParametrosHojaRutaException exception=new ValidacionParametrosHojaRutaException();
        if (razon==null){
            exception.addError("Debe escribir una razï¿½n para la devoluciï¿½n");
        }
        
        if (razon!=null&&razon.trim().equals("")){
            exception.addError("Debe escribir una razï¿½n para la devoluciï¿½n");
        }
        
        if (!exception.getErrores().isEmpty()){
            throw exception;
        }
        
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
        
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_RECHAZAR,turno, fase, estacion, usuario, circulo, proceso, razon, EvnAntiguoSistema.HOJA_RUTA_RECHAZAR_WF);
    }

    /**
     * @param request
     * @return
     */
    private Evento avanzarRevisionFinalMasDocs(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.REVISION_FINAL_MAS_DOCS,turno, fase, estacion, usuario, EvnAntiguoSistema.REVISION_FINAL_MAS_DOCS_WF);
    }
    
    private Evento avanzarRevisionFinalExiste(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.REVISION_FINAL_EXISTE,turno, fase, estacion, usuario, EvnAntiguoSistema.REVISION_FINAL_EXISTE_WF);
    }
    
    private Evento avanzarRevisionFinalReanalizar(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.REVISION_FINAL_HOJA_RUTA,turno, fase, estacion, usuario, EvnAntiguoSistema.REVISION_FINAL_HOJA_RUTA_WF);
    }
    
    private Evento avanzarRevisionFinalRechazado(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.REVISION_FINAL_RECHAZADO,turno, fase, estacion, usuario, EvnAntiguoSistema.REVISION_FINAL_RECHAZADO_WF);
    }
    
    private Evento avanzarRevisionFinalCreado(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.REVISION_FINAL_CREADO,turno, fase, estacion, usuario, EvnAntiguoSistema.REVISION_FINAL_CREADO_WF);
    }

    /**
     * @param request
     * @return
     */
    private Evento avanzarNegarCreacionFolio(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.CREACION_FOLIO_NEGAR,turno, fase, estacion, usuario, EvnAntiguoSistema.CREACION_FOLIO_NEGAR_WF);
    }

    /**
     * @param request
     * @return
     */
    private Evento avanzarAprobarCreacionFolio(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.CREACION_FOLIO_APROBAR,turno, fase, estacion, usuario, EvnAntiguoSistema.CREACION_FOLIO_APROBAR_WF);
    }

    /**
     * @param request
     * @return
     */
    private Evento avanzarDevolverRevisionInicial(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        
        String razon = request.getParameter(CDatosAntiguoSistema.RAZON_DEVOLUCION_HOJA_RUTA);
        
        ValidacionParametrosHojaRutaException exception=new ValidacionParametrosHojaRutaException();
        if (razon==null){
            exception.addError("Debe escribir una razï¿½n para la devoluciï¿½n");
        }
        
        if (razon!=null&&razon.trim().equals("")){
            exception.addError("Debe escribir una razï¿½n para la devoluciï¿½n");
        }
        
        if (!exception.getErrores().isEmpty()){
            throw exception;
        }
        
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
        
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_REANALIZAR,turno, fase, estacion, usuario, circulo, proceso, razon, EvnAntiguoSistema.HOJA_RUTA_REANALIZAR_WF);
    }

    /**
     * @param request
     * @return
     */
    private Evento avanzarAprobarHojaRuta(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        request.getSession().removeAttribute(CFolio.FOLIO_CREADO_HOJA_RUTA);
        /**
        * @Autor: Edgar Lora
        * @Mantis: 0013038
        * @Requerimiento: 060_453
        */
        if(turno.getIdProceso() == 6 || turno.getIdProceso() == 3 || (turno.getIdProceso() == 1 && (((LiquidacionTurnoCertificado)turno.getSolicitud().getLiquidaciones().get(0)).getTipoCertificado().getIdTipoCertificado().compareTo("2") == 0 || ((LiquidacionTurnoCertificado)turno.getSolicitud().getLiquidaciones().get(0)).getTipoCertificado().getIdTipoCertificado().compareTo("3") == 0))){            
            ValidacionesSIR val = new ValidacionesSIR();
            String mensajeError = "";
            try{
                List matriculas = val.getMatriculaAntiguoSistemaAsociadaAlTurno(turno.getIdCirculo(), turno.getIdWorkflow());
                for(int j = 0; j < matriculas.size(); j = j + 1){
                    String matricula = (String)matriculas.get(j);
                    if(val.validarLinderos(matricula, CNaturalezaJuridica.NATURALEZA_PARA_VALIDAR_LINDEROS)){
                        String lindero = val.getLinderoDeMatricula(matricula);
                        if(lindero.length() <= 200){
                            mensajeError = "El valor de el campo lideros de la matricula " + matricula + " debe tener mas de 200 caracteres.";                            
                        }
                    }
                }
            }catch(GeneralSIRException e){
                throw new AccionWebException(e.getMessage());
            }
            if(mensajeError.length() > 0){
                throw new AccionWebException(mensajeError);
            }
        }
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.HOJA_RUTA_CREADO,turno, fase, estacion, usuario, EvnAntiguoSistema.HOJA_RUTA_CREADO_WF);
    }
    
    /**
     * @param request
     * @return
     */
    private Evento avanzarExistenteRevisionInicial(HttpServletRequest request) {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.REVISION_INICIAL_EXISTE,turno, fase, usuario, EvnAntiguoSistema.REVISION_INICIAL_EXISTE_WF);
    }

    /**
     * @param request
     * @return
     */
    private EvnAntiguoSistema avanzarNegarRevisionInicial(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		
        
        String comentario = request.getParameter(COMENTARIO_NEGAR);
        if (comentario == null || comentario.length() == 0){
        	throw new AccionWebException("Debe introducir un comentario para negar");        	
        }
		
		Nota nota = agregarNota(request);
		
		EvnAntiguoSistema evn = new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.REVISION_INICIAL_NEGAR,turno, fase, usuario, EvnAntiguoSistema.REVISION_INICIAL_NEGAR_WF);
		evn.setNota(nota);
		evn.setCirculo(circulo);
		return evn;
    }
    
    
	
	private Nota agregarNota(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		ValidacionParametrosAgregarNotaException exception = new ValidacionParametrosAgregarNotaException();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		//List notasImpresion = new Vector();		
		
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        
		if (turno == null) {
			exception.addError("El turno que se encontro en la sesiï¿½n es invï¿½lido");
		}
		if (circulo == null) {
			exception.addError("El circulo que se encontro en la sesiï¿½n es invï¿½lido");
		}
		if (proceso == null) {
			exception.addError("El proceso que se encontro en la sesiï¿½n es invï¿½lido");
		}
		if (usuario == null) {
			exception.addError("El usuario que se encontro en la sesiï¿½n es invï¿½lido");
		}

		//if (idTipoNota == null || idTipoNota.equals(WebKeys.SIN_SELECCIONAR)) {
		//	exception.addError("Debe escoger un tipo de nota valido");
		//}
		String descripcion = request.getParameter(COMENTARIO_NEGAR);

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		//TipoNota tipoNota = new TipoNota();
		//tipoNota.setIdTipoNota(idTipoNota);
		//tipoNota.setDevolutiva(devolutiva);

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
		//nota.setTiponota(tipoNota);
		nota.setIdFase(fase.getID());
		nota.setTurno(turno);
		nota.setUsuario(usuario);
		
		return nota;

	}	


    /**
     * @param request
     * @return
     */
    private EvnAntiguoSistema avanzarAprobarRevisionInicial(HttpServletRequest request) throws AccionWebException{
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
        return new EvnAntiguoSistema(usuarioAuriga,EvnAntiguoSistema.REVISION_INICIAL_CONFIRMAR,turno, fase, estacion, usuario, EvnAntiguoSistema.REVISION_INICIAL_CONFIRMAR_WF);
    }

    public void doEnd(HttpServletRequest request, EventoRespuesta eventoRespuesta) {
        if (eventoRespuesta !=null){
            if (eventoRespuesta instanceof EvnRespAntiguoSistema){
                EvnRespAntiguoSistema respuesta = (EvnRespAntiguoSistema)eventoRespuesta;
                if (respuesta.getTipoEvento().equals(EvnRespAntiguoSistema.ASOCIAR_MATRICULAS)){
                    request.getSession().setAttribute(WebKeys.TURNO, respuesta.getPayload());
                    request.getSession().removeAttribute(CFolio.ID_MATRICULA);
                }else if (respuesta.getTipoEvento().equals(EvnRespAntiguoSistema.ELIMINAR_MATRICULAS)){
                    request.getSession().setAttribute(WebKeys.TURNO, respuesta.getPayload());
                }else if (respuesta.getTipoEvento().equals(EvnRespAntiguoSistema.CONSULTAR_MATRICULA)){
                    request.getSession().setAttribute(WebKeys.FOLIO,respuesta.getPayload());                    
                    request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, ((Folio)respuesta.getPayload()).getAnotaciones());
                    eliminarInfoBasicaAnotacion(request);
                    request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
                    request.getSession().removeAttribute("NUM_ANOTACION_TEMPORAL");
                    eliminarInfoBasicaVariosCiudadanos(request);
				}else if (respuesta.getTipoEvento().equals(EvnRespAntiguoSistema.CONSULTA_FOLIO)){
					request.getSession().setAttribute(WebKeys.FOLIO,respuesta.getPayload());
					request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, ((Folio)respuesta.getPayload()).getAnotaciones());
					request.getSession().setAttribute(AWAntiguoSistema.ANIO_VALIDACION_CANCELACION, respuesta.getAnho());
				}else if (respuesta.getTipoEvento().equals(EvnRespAntiguoSistema.AVANZAR_CORRECCIONES)) {
					request.getSession().setAttribute(WebKeys.TURNO_HIJO, respuesta.getPayload());
					request.getSession().setAttribute(WebKeys.TURNO, respuesta.getTurno());
				} else if (respuesta.getTipoEvento().equals(EvnRespAntiguoSistema.VOLVER_AGREGAR_CIUDADANOS)) {
					//request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, respuesta.getListaCompletaCiudadanos());
					request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, respuesta.getAnotaciones());
					request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
					eliminarInfoBasicaAnotacion(request);
					eliminarInfoBasicaUltimoVariosCiudadanos(request);
				} else if (respuesta.getTipoEvento().equals(EvnRespAntiguoSistema.CONSULTAR_ANOTACION_POR_ORDEN)){
					request.getSession().setAttribute(WebKeys.ANOTACION_EDITAR, respuesta.getPayload());	
				}
            }else if (eventoRespuesta instanceof EvnRespConsultaFolio){
                EvnRespConsultaFolio respuesta=(EvnRespConsultaFolio)eventoRespuesta;
                if(respuesta.getFolio() != null){
                    
                    if (accion.equals(AWAntiguoSistema.EDITAR_FOLIO)){
                        Folio folio=respuesta.getFolio();
                        if ( folio.getRadicacion()!= null) {
                            request.getSession().setAttribute(CFolio.FOLIO_NUM_RADICACION, folio.getRadicacion());
                        }
                        
                        if (folio.getZonaRegistral()!=null &&  folio.getZonaRegistral().getVereda()!=null && folio.getZonaRegistral().getVereda().getIdVereda()!=null && folio.getZonaRegistral().getVereda().getIdMunicipio()!=null && folio.getZonaRegistral().getVereda().getIdDepartamento()!=null){
                            Vereda vereda=folio.getZonaRegistral().getVereda();
                            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA,vereda.getIdVereda());
                            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC,vereda.getIdMunicipio());
                            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO, vereda.getIdDepartamento());
                            if (vereda.getNombre()!=null){
                                request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA,vereda.getNombre());    
                            }
                            
                            if (vereda.getMunicipio()!=null && vereda.getMunicipio().getNombre()!=null){
                                Municipio municipio=vereda.getMunicipio();
                                request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC,municipio.getNombre());
                                if (municipio.getDepartamento()!=null && municipio.getDepartamento().getNombre()!=null){
                                    request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO,municipio.getDepartamento().getNombre());
                                }
                            }
                        }                       

                        if (folio.getTipoPredio() != null) {
                            request.getSession().setAttribute(CFolio.FOLIO_TIPO_PREDIO,folio.getTipoPredio().getIdPredio());
                        }

                        if (folio.getComplementacion() != null) {
                            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION,folio.getComplementacion().getComplementacion());
                            request.getSession().setAttribute(CFolio.FOLIO_ID_COMPLEMENTACION,folio.getComplementacion().getIdComplementacion());
                        }
                        
                        if (folio.getLindero() != null) {
                            request.getSession().setAttribute(CFolio.FOLIO_LINDERO,folio.getLindero());
                        }

                        if (folio.getCodCatastral() != null) {
                            request.getSession().setAttribute(CFolio.FOLIO_COD_CATASTRAL,folio.getCodCatastral());
                        }
                        
                        request.getSession().setAttribute(CFolio.FOLIO_ID_MATRICULA, folio.getIdMatricula());
                        
                        List direcciones=new ArrayList();
                        direcciones.addAll(folio.getDirecciones());
                        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION,direcciones);
                        
                        request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, folio);
                    }else{
                        request.getSession().setAttribute(WebKeys.FOLIO,respuesta.getFolio());
                    }
                }
            }else if (eventoRespuesta instanceof EvnRespCiudadano){
                EvnRespCiudadano respuesta=(EvnRespCiudadano)eventoRespuesta;
                String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);
                
                if(respuesta.isCiudadanoEncontrado()){
                    request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA+ver, respuesta.getCiudadano().getApellido1());
                    request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA+ver, respuesta.getCiudadano().getApellido2());
                    request.getSession().setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA+ver, respuesta.getCiudadano().getNombre());
                    request.getSession().setAttribute(CFolio.CIUDADANO_EDITABLE+ver, new Boolean(true));
                }else{
                    request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA+ver);
                    request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA+ver);
                    request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA+ver);
                    request.getSession().setAttribute(CFolio.CIUDADANO_EDITABLE+ver, new Boolean(false));
                }
            }else if (eventoRespuesta instanceof EvnRespFolio){
                EvnRespFolio respuesta=(EvnRespFolio)eventoRespuesta;
                if(respuesta.getTurno() != null){
					Turno turno=respuesta.getTurno();
					request.getSession().setAttribute(WebKeys.TURNO, turno);
					request.getSession().removeAttribute(WebKeys.FOLIO_EDITADO);                	
                }
				if(respuesta.getFolio() != null){
					request.getSession().setAttribute(WebKeys.FOLIO,respuesta.getFolio());
				}
            }else if (eventoRespuesta instanceof EvnRespCertificado){
                EvnRespCertificado respuesta=(EvnRespCertificado)eventoRespuesta;
                if (respuesta.getTipoEvento().equals(EvnRespCertificado.CONSULTA_FOLIO_COMPLEMENTACION)) {
                    if(respuesta.getFolio()!=null && respuesta.getFolio().getComplementacion()!=null){
                        request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION, respuesta.getFolio().getComplementacion().getComplementacion());
                        request.getSession().setAttribute(CFolio.FOLIO_ID_COMPLEMENTACION, respuesta.getFolio().getComplementacion().getIdComplementacion());
                    }
                }
            }
            
            // -----------------------------------------------------------
            // guardar informacion temporal
            else if( eventoRespuesta instanceof EvnResp_AntigSistHojaRutaSaveInfo ) {
            	EvnResp_AntigSistHojaRutaSaveInfo respuesta;
            	respuesta = (EvnResp_AntigSistHojaRutaSaveInfo)eventoRespuesta;
            	
            	String local_TipoEvento;
            	local_TipoEvento =  respuesta.getTipoEvento();
            		
            	if( EvnResp_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP0__EVENTRESP_OK.equals( local_TipoEvento ) ) {
            		doEndProcess_AntigSistHojaRuta_SaveInfo_Step0( request, respuesta );
            	}
            	if( EvnResp_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP1__EVENTRESP_OK.equals( local_TipoEvento ) ) {
            		doEndProcess_AntigSistHojaRuta_SaveInfo_Step1( request, respuesta );
            	}
            	if( EvnResp_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP2__EVENTRESP_OK.equals( local_TipoEvento ) ) {
            		request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, respuesta.getAnotaciones());
            		request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION,respuesta.getFolioEditado().getDirecciones());
            		doEndProcess_AntigSistHojaRuta_SaveInfo_Step2( request, respuesta );
            		if(!respuesta.isCrearFolio()){
            			eliminarInfoBasicaAnotacion(request);
            		}
            	}
            	if( EvnResp_AntigSistHojaRutaSaveInfo.ANTIGSISTHOJARUTA_STEP3__EVENTRESP_OK.equals( local_TipoEvento ) ) {
            		doEndProcess_AntigSistHojaRuta_SaveInfo_Step3( request, respuesta );
            	}
            	
            	
            	
            	
            	
            }
            // -----------------------------------------------------------
            
            
        }
    }
}

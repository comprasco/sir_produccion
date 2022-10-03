package gov.sir.core.web.acciones.correccion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
/*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se habilita el uso de las clases
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */

import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.correccion.EvnRespRevision;
import gov.sir.core.eventos.correccion.EvnRevision;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.PermisoCorreccion;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CActuacionAdministrativa;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.PermisosCorreccionException;
import gov.sir.core.web.acciones.excepciones.ValidacionColectorCorreccionesRevisionException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
import java.text.ParseException;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.ClassFunctions;

import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con la fase de confrontación
 * en el proceso de correcciones. Se encarga de realizar las validaciones primarias a nivel web y
 * por medio de eventos hace llamados a la capa de negocio para que su vez llame los servicios
 * que se requieren.
 * @author ppabon, jvelez
 */
public class AWRevision extends SoporteAccionWeb {

  public static class Local_Formats {
     public static String date(Date d, String pattern) {
        if( null == d )
           return null;
       return DateFormatUtil.format(pattern, d);
     }
  }


	/**
	 *
	 */
	public static final String RECARGAR = AwCorrecciones_Constants.PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION;
	public static final String ACT_RECARGAR = AwCorrecciones_Constants.ACT_PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION;


	/**Constante que identifica que se quiere enviar a correccion simple*/
	public final static String CORRECCION_SIMPLE = "CORRECCION_SIMPLE";

	public final static String REVISAR_APROBAR = "REVISAR_APROBAR";
	public final static String REVISAR_NEGAR = "REVISAR_NEGAR";


	/**Constante que identifica que se quiere asociar una matricula*/
	public final static String ASOCIAR_UNA_MATRICULA = "ASOCIAR_UNA_MATRICULA";

	/**Constante que identifica que se quiere asociar un rango de matriculas*/
	public final static String ASOCIAR_UN_RANGO = "ASOCIAR_UN_RANGO";

	/**Constante que identifica que se quiere pasar el caso a reparto*/
	public final static String CONFIRMAR = "CONFIRMAR";

	/**Constante que identifica que se quiere terminar el caso a reparto*/
	public final static String NEGAR = "NEGAR";

	/**Constante que identifica que se quiere eliminar una matrícula de la solicitud de correcciones*/
	public final static String ELIMINAR = "ELIMINAR";
	
		/////////////////////////
	/**
	 * Constante que identifica que se desea DELEGAR el turno de la corrección a ANTIGUO_SISTEMA
	 */
	public final static String ANTIGUO_SISTEMA = "ANTIGUO_SISTEMA";

	public final static String MAYOR_VALOR = "MAYOR_VALOR";

	public final static String ACTUACION_ADMINISTRATIVA =
		"ACTUACION_ADMINISTRATIVA";
		
	/**Constante que identifica que se quiere ocultar la solicitud*/
	public final static String OCULTAR_SOLICITUD = "OCULTAR_SOLICITUD";
	
	/**Constante que identifica que se quiere ocultar la solicitud*/
	public final static String OCULTAR_MAYOR_VALOR = "OCULTAR_MAYOR_VALOR";	

	/**
	 * Constante que identifica que se desea REDIRECCIONAR la corrección para que se trabaje por mayor valor o por especializado
	 */
	public final static String REDIRECCIONAR_CASO = "REDIRECCIONAR_CASO";
	public final static String REDIRECCIONAR_CASO_ACT = "REDIRECCIONAR_CASO_ACT";
	///////////////////////////


	public final static String COS_SOLICITUD_CORRECCION = CFase.COS_SOLICITUD_CORRECCION;

	public AWRevision() {
		super();
	}

	public Evento perform(HttpServletRequest request)
	throws AccionWebException {

		String accion = "";
		accion = request.getParameter( WebKeys.ACCION );

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}

		// validators -------------------------------------------------------

		// :: Exception Collector
		ValidacionColectorCorreccionesRevisionException exception
		= new ValidacionColectorCorreccionesRevisionException();

		// :: Conditional validators
		if( REDIRECCIONAR_CASO.equalsIgnoreCase( accion ) ) {

                    String redireccionar = request.getParameter(REDIRECCIONAR_CASO);

                    if ((null == redireccionar)
                        || ("".equalsIgnoreCase(redireccionar.trim()))
                        || ("SIN_SELECCIONAR".equalsIgnoreCase(redireccionar.trim()))) {
                        exception.addError(
                                "Al elegir redireccionar, debe seleccionar un destino para la redireccion.");
                    }
                    else if( ANTIGUO_SISTEMA.equals( redireccionar.trim() ) ) {
                        // TODO: apply validators "antiguo sistema"
                        if ( null == getDatosAntiguoSistema( request, exception ) ) {
                            exception.addError( "Si se va a ejecutar redireccion a antiguo sistema se requieren datos" );
                        }
                    }
		}


		// :: Raise
		if( exception.getErrores().size() > 0 ) {
			//logger.error("");
			throw exception;
		}

		// ------------------------------------------------------------------




		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
			(org.auriga.core.modelo.transferObjects.Usuario) request
				.getSession()
				.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		gov.sir.core.negocio.modelo.Usuario usuarioSIR =
			(gov.sir.core.negocio.modelo.Usuario) request
				.getSession()
				.getAttribute(
				WebKeys.USUARIO);


		// Proxy ---------------------------------------------------------------------

		if (accion.equals(ASOCIAR_UNA_MATRICULA)) {
			return asociarUnaMatricula(request);
		}
		else if (accion.equals(ELIMINAR)) {
			return eliminarMatricula(request);
		}
		else if (accion.equals(ASOCIAR_UN_RANGO)) {
			return asociarUnRango(request);

		}
		else if (accion.equals(CORRECCION_SIMPLE)) {
                    return doCorreccionSimple_Process( request );


		}
		else if (accion.equals(REDIRECCIONAR_CASO)) {
			return redireccionar(request);

		} else if (accion.equals(REDIRECCIONAR_CASO_ACT)) {
				return redireccionarAct(request);

		}
		else if (accion.equals(NEGAR)) {
			EvnRevision evn =
				new EvnRevision(
					usuarioAuriga,
					usuarioSIR,
					turno,
					fase,
					EvnRevision.AVANZAR,
					EvnRevision.NEGAR);

			return evn;

		}
		else if (accion.equals(ASOCIAR_UNA_MATRICULA)) {
			return asociarUnaMatricula(request);
		}
		else if( accion.equals( RECARGAR ) || accion.equals( ACT_RECARGAR )) {
			return doAntiguoSistema_PageEvent_Recargar( request );
		}
		else if( accion.equals( OCULTAR_SOLICITUD ) || accion.equals( OCULTAR_MAYOR_VALOR )) {
			return recargarPaginaActuacionAdministrativa( request );
		}
		
		else {
			throw new AccionInvalidaException(
				"La accion " + accion + " no es valida.");
		}

		// ----------------------------------------------------------------------------

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





   // template-part
   protected void saveState_DatosAntiguoSistema(HttpServletRequest request) {

          HttpSession session = request.getSession();


          String local_DisabledAntiguoSistemaRegion;
          boolean local_EnabledAntiguoSistemaRegionFlag = false;


          Find__EnabledAntiguoSistemaRegion: {
             String local_Value = request.getParameter( WebKeys.OCULTAR );
             if( null == local_Value || "".equalsIgnoreCase( local_Value ) ) {
                local_Value = (String )session.getAttribute( WebKeys.OCULTAR );
             }
             if( null == local_Value || "".equalsIgnoreCase( local_Value ) ) {
                local_Value = "FALSE";
             }
             local_DisabledAntiguoSistemaRegion = local_Value;
          } // :Find__EnabledAntiguoSistemaRegion

          if( "FALSE".equalsIgnoreCase( local_DisabledAntiguoSistemaRegion ) ) {
                 local_EnabledAntiguoSistemaRegionFlag = true;
          }

          // solo se ejecuta si esta habilitado
          // para que no pierda los otros datos
          if( local_EnabledAntiguoSistemaRegionFlag )
             return;


     /* Revisar: En lista keys no estan todos los valores

          String key, valor;
          String keyAntSistema[];
          keyAntSistema = CDatosAntiguoSistema.LISTA_KEYS_DATOS_ANTIGUO_SISTEMA;

          for (int i = 0; i < keyAntSistema.length; i++) {
                 key = keyAntSistema[i];
                 valor = request.getParameter(key);
                 if (valor != null && !valor.trim().equals("")) {
                   session.setAttribute(key, valor);
                 }
          } // :for

          keyAntSistema = CDatosAntiguoSistema.LISTA_KEYS_DOCUMENTO;

          for (int i = 0; i < keyAntSistema.length; i++) {
                 key = keyAntSistema[i];
                 valor = request.getParameter(key);
                 if (valor != null && !valor.trim().equals("")) {
                   session.setAttribute(key, valor);
                 }
          } // :for
     */


        // Salvar informacion AntiguoSistema
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS,
            request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS,
            request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS,
            request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS,
            request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS,
            request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS,
            request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS,
            request.getParameter(
                CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS));
        session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS,
            request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS));


    // ------------------------------------ datos del documento
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_COMBO_AS,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_COMBO_AS));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS,
         request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS));

       // ------------------------ datos de documento.oficinaorigen

       // i.

    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS,
        request.getParameter(
            CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS,
        request.getParameter(
            CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS,
        request.getParameter(
            CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS,
        request.getParameter(
            CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS,
        request.getParameter(
            CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS,
        request.getParameter(
            CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS,
        request.getParameter(
                CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS));

       // ii.

     session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO,
       request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO));
     session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO,
       request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM));

    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_NOM_OFICINA_HIDDEN,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_NOM_OFICINA_HIDDEN));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA_HIDDEN,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA_HIDDEN));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUMERO_OFICINA_HIDDEN,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUMERO_OFICINA_HIDDEN));
    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_OFICINA_HIDDEN,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO_OFICINA_HIDDEN));



    session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO,
        request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_TIPO));


       session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS,
           request.getParameter(
               CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS));
       session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS,
           request.getParameter(
               CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS));
       session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS,
           request.getParameter(
               CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_ID_AS));
       session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS,
           request.getParameter(
               CDatosAntiguoSistema.DOCUMENTO_OFICINA_VEREDA_AS));
       session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS,
           request.getParameter(
               CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS));
       session.setAttribute(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS,
           request.getParameter(
                CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS));


    // ------- (comentario)
       session.setAttribute(CDatosAntiguoSistema.COMENTARIO_AS,
           request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS));


    // ------------------------------------ libro
        session.setAttribute(CDatosAntiguoSistema.LIBRO_TIPO_AS,
            request.getParameter(CDatosAntiguoSistema.LIBRO_TIPO_AS));
        session.setAttribute(CDatosAntiguoSistema.LIBRO_NUMERO_AS,
            request.getParameter(CDatosAntiguoSistema.LIBRO_NUMERO_AS));
        session.setAttribute(CDatosAntiguoSistema.LIBRO_PAGINA_AS,
            request.getParameter(CDatosAntiguoSistema.LIBRO_PAGINA_AS));
        session.setAttribute(CDatosAntiguoSistema.LIBRO_ANO_AS,
            request.getParameter(CDatosAntiguoSistema.LIBRO_ANO_AS));
    // ------------------------------------ tomo

        session.setAttribute(CDatosAntiguoSistema.TOMO_NUMERO_AS,
            request.getParameter(CDatosAntiguoSistema.TOMO_NUMERO_AS));
        session.setAttribute(CDatosAntiguoSistema.TOMO_PAGINA_AS,
            request.getParameter(CDatosAntiguoSistema.TOMO_PAGINA_AS));
        session.setAttribute(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS,
            request.getParameter(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS));
        session.setAttribute(CDatosAntiguoSistema.TOMO_ANO_AS,
            request.getParameter(CDatosAntiguoSistema.TOMO_ANO_AS));

    // --------------------------------- datos de documento





    // ------------------------------------------------------------------------------------






   }

   /*@see AWLiquidacionCorreccion */
   private DatosAntiguoSistema getDatosAntiguoSistema(HttpServletRequest request, ValidacionParametrosException exception ) {
      // copiado de AWLiqudiacionRegistro


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
        documento_numero_as = (documento_numero_as == null) ? ""
                                                            : documento_numero_as;

        String documento_comentario_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS);
        documento_comentario_as = (documento_comentario_as == null) ? ""
                                                                    : documento_comentario_as;

        String documento_fecha_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
        documento_fecha_as = (documento_fecha_as == null) ? ""
                                                          : documento_fecha_as;

        String comentario_as = request.getParameter(CDatosAntiguoSistema.COMENTARIO_AS);
        comentario_as = (comentario_as == null) ? "" : comentario_as;

        String oficina_depto_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
        oficina_depto_id_as = (oficina_depto_id_as == null) ? ""
                                                            : oficina_depto_id_as;

        String oficina_depto_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
        oficina_depto_nom_as = (oficina_depto_nom_as == null) ? ""
                                                              : oficina_depto_nom_as;

        String oficina_muni_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
        oficina_muni_id_as = (oficina_muni_id_as == null) ? ""
                                                          : oficina_muni_id_as;

        String oficina_muni_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
        oficina_muni_nom_as = (oficina_muni_nom_as == null) ? ""
                                                            : oficina_muni_nom_as;

        String oficina_vereda_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
        oficina_vereda_id_as = (oficina_vereda_id_as == null) ? ""
                                                              : oficina_vereda_id_as;

        String oficina_vereda_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
        oficina_vereda_nom_as = (oficina_vereda_nom_as == null) ? ""
                                                                : oficina_vereda_nom_as;

        String oficina_oficia_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
        oficina_oficia_id_as = (oficina_oficia_id_as == null) ? ""
                                                              : oficina_oficia_id_as;

        /*
       *  @author Carlos Torres
       *  @chage   se agrega validacion de version diferente
       *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
       */
        String oficina_oficia_version_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION);
        oficina_oficia_version_as = (oficina_oficia_version_as == null) ? "0"
                                                              : oficina_oficia_version_as;
        
        String oficina_oficia_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
        oficina_oficia_nom_as = (oficina_oficia_nom_as == null) ? ""
                                                                 : oficina_oficia_nom_as;











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
                  exception.addError("El campo Fecha del documento de " +
                      "antiguo sistema no es válido");
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
          oficinaOrigenAS.setNombre(oficina_oficia_nom_as);
          oficinaOrigenAS.setVereda(veredaAS);
           /*
           *  @author Carlos Torres
           *  @chage   se agrega validacion de version diferente
           *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
           */
          oficinaOrigenAS.setVersion(Long.parseLong(oficina_oficia_version_as));
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
                      Log.getInstance().debug(AWRevision.class,
                          "[Colocando en Antiguo sistema Oficina de origen]");
                  }

                  Log.getInstance().debug(AWRevision.class,
                      "[Colocando en Antiguo sistema tipo documento]");
              }

              Log.getInstance().debug(AWRevision.class,
                  "[Colocando en datos de actiguo sistema el documento]");
          }



      return das;

      /*
          String[] listaKeysAntSistema;
          listaKeysAntSistema = CDatosAntiguoSistema.LISTA_KEYS_DATOS_ANTIGUO_SISTEMA;

          Vector keysLlenas = new Vector();

          String key, valor;
          DatosAntiguoSistema datosAntSistema = null;

          for (int i = 0; i < listaKeysAntSistema.length; i++) {
                 key = listaKeysAntSistema[i];
                 valor = request.getParameter(key);
                 if (valor != null) {
                   if (valor.trim().length() > 0) {
                          keysLlenas.add(key);
                   }
                 }
          } // for

          listaKeysAntSistema = CDatosAntiguoSistema.LISTA_KEYS_DOCUMENTO;

          for (int i = 0; i < listaKeysAntSistema.length; i++) {
                 key = listaKeysAntSistema[i];
                 valor = request.getParameter(key);
                 if (valor != null) {
                   if (valor.trim().length() > 0) {
                          keysLlenas.add(key);
                   }
                 }
          } // for


          if (keysLlenas.size() > 0) {
                 datosAntSistema = new DatosAntiguoSistema();
                 for (int i = 0; i < keysLlenas.size(); i++) {
                   key = (String) keysLlenas.get(i);
                   datosAntSistema = this.setDatoAntiguoSistema(request, key,
                                 datosAntSistema);

                 }
          }

          return datosAntSistema;
   */







  }
   /** remover datos de cache para antiguo sistema */
   protected void removeState_DatosAntiguoSistema(HttpServletRequest request) {
          HttpSession session = request.getSession();

          String key, valor;
          String[] keyDocumentos;

          keyDocumentos = CDatosAntiguoSistema.LISTA_KEYS_DATOS_ANTIGUO_SISTEMA;

          for (int i = 0; i < keyDocumentos.length; i++) {
                 key = keyDocumentos[i];
                 if (null != key) {
                   session.removeAttribute(key);
                 }
          } // for

          keyDocumentos = CDatosAntiguoSistema.LISTA_KEYS_DOCUMENTO;

          for (int i = 0; i < keyDocumentos.length; i++) {
                 key = keyDocumentos[i];
                 if (null != key) {
                   session.removeAttribute(key);
                 }
          } // for

  }





    /**
     * do_CorreccionSimple
     *
     * @param request HttpServletRequest
     * @return Evento
     */
    private Evento
    doCorreccionSimple_Process( HttpServletRequest request )
    throws AccionWebException {


      // Save State ------------------
      saveState_Permisos( request );
      saveState_DatosAntiguoSistema( request );

      // Cargar el set de permisos -------------------
      String[] permisos = request.getParameterValues(WebKeys.LISTA_PARAMETROS_PERMISOS_REVISION);
      if (permisos==null){
          throw new PermisosCorreccionException("Debe escoger por lo menos un permiso");
      }

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        List objetosPermiso=(List)request.getSession().getServletContext().getAttribute(WebKeys.LISTA_PERMISOS_REVISION_CORRECCION);
        List permisosEscogidos=new ArrayList();
        for( int i=0; i < permisos.length; i++ ) {

            PermisoCorreccion permiso=null;
            Iterator itPermisos=objetosPermiso.iterator();
            while(itPermisos.hasNext()){
                PermisoCorreccion temp=(PermisoCorreccion)itPermisos.next();
                if (temp.getIdPermiso().equals(permisos[i])){
                    permiso=temp;
                }
            }
            if (permiso==null){
                throw new PermisosCorreccionException("Error cargando los permisos");
            }
            permisosEscogidos.add(permiso);
        }

        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
                (org.auriga.core.modelo.transferObjects.Usuario) request
                        .getSession()
                        .getAttribute(SMARTKeys.USUARIO_EN_SESION);

        gov.sir.core.negocio.modelo.Usuario usuarioSIR =
                (gov.sir.core.negocio.modelo.Usuario) request
                        .getSession()
                        .getAttribute(
                        WebKeys.USUARIO);


        EvnRevision evn =
                new EvnRevision(
                        usuarioAuriga,
                        usuarioSIR,
                        turno,
                        fase,
                        permisosEscogidos,
                        EvnRevision.AVANZAR_PERMISOS,
                        EvnRevision.CORRECCION_SIMPLE);

        return evn;
    }

    // template
	private void saveState( HttpServletRequest request ) {
		saveState_DatosAntiguoSistema( request );
        saveState_Permisos( request );
        preservarInfoActuacion(request);
		preservarInfoMayorValor(request);
	}

        public static void loadState_Permisos( HttpServletRequest request ) {
              HttpSession session;
              session = request.getSession();

              String[] local_Data;

              java.util.List modelPermisosList = null;
              // get local data
              modelPermisosList=(List)  session.getAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST );

              if( null == modelPermisosList )
                return;

              // put data into cache

              java.util.Vector cache_Data; // Vector< Vector< String > >

              cache_Data = new java.util.Vector();
              String local_ElementId;

              // Column 1
              Vector column;
              column = new java.util.Vector();


              // cargar model permisos
              java.util.Iterator iterator
              = modelPermisosList.iterator();

          String modelPermisosMap_Key = null;


              for(; iterator.hasNext(); ) {
                gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion permiso
                = (gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion)iterator.next();

                modelPermisosMap_Key   = permiso.getIdPermiso();//gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants.FOLIO_NUMEROCATASTRAL_ID ;

                column.add( modelPermisosMap_Key );


              }
              cache_Data = column;

              session.setAttribute( "PARAM_CACHE__PERMISOS", cache_Data );


        }

        private static void do_PageProcessingProcess_PageSaveState_Object( HttpServletRequest request, HttpSession session, String sessionKey, Object sessionValue  ){
          session.setAttribute( sessionKey, sessionValue );
        }

        private static void do_PageProcessingProcess_PageClearState_Object( HttpSession session, String sessionKey ){
          session.removeAttribute( sessionKey );
        }


        public static
        void loadState_DatosAntoguoSistema( HttpServletRequest request ) {
              HttpSession session;
              session = request.getSession();


              DatosAntiguoSistema local_DatosAntoguoSistema = null;
              // get local data
              local_DatosAntoguoSistema= (DatosAntiguoSistema) session.getAttribute( "PARAM__MODEL_DATOSANTIGUOSISTEMA" );

              if( null == local_DatosAntoguoSistema )
                return;

              // put data into cache

              JXPathContext local_Context;
              local_Context = JXPathContext.newContext( local_DatosAntoguoSistema );
              local_Context.setLenient( true );
              local_Context.setFunctions( new ClassFunctions( Local_Formats.class, "format" ) );
              local_Context.getVariables().declareVariable( "dateConversion", new Date() );



              // :: Globales

              do_PageProcessingProcess_PageSaveState_Object(
                  request
                , session
                , CDatosAntiguoSistema.COMENTARIO_AS
                , local_Context.getValue( "comentario" )
              );

              // :: Libro

              do_PageProcessingProcess_PageSaveState_Object(
                  request
                , session
                , CDatosAntiguoSistema.LIBRO_ANO_AS
                , local_Context.getValue( "libroAnio" )
              );
              do_PageProcessingProcess_PageSaveState_Object(
                  request
                , session
                , CDatosAntiguoSistema.LIBRO_NUMERO_AS
                , local_Context.getValue( "libroNumero" )
              );
              do_PageProcessingProcess_PageSaveState_Object(
                  request
                , session
                , CDatosAntiguoSistema.LIBRO_PAGINA_AS
                , local_Context.getValue( "libroPagina" )
              );
              do_PageProcessingProcess_PageSaveState_Object(
                  request
                , session
                , CDatosAntiguoSistema.LIBRO_TIPO_AS
                , local_Context.getValue( "libroTipo" )
              );

              // :: Tomo

              do_PageProcessingProcess_PageSaveState_Object(
                 request
               , session
               , CDatosAntiguoSistema.TOMO_ANO_AS
               , local_Context.getValue( "tomoAnio" )
              );
              do_PageProcessingProcess_PageSaveState_Object(
                  request
                , session
                , CDatosAntiguoSistema.TOMO_MUNICIPIO_AS
                , local_Context.getValue( "tomoMunicipio" )
              );
              do_PageProcessingProcess_PageSaveState_Object(
                 request
                , session
                , CDatosAntiguoSistema.TOMO_NUMERO_AS
                , local_Context.getValue( "tomoNumero" )
              );
              do_PageProcessingProcess_PageSaveState_Object(
                 request
                , session
                , CDatosAntiguoSistema.TOMO_PAGINA_AS
                , local_Context.getValue( "tomoPagina" )
              );


              // :: Documento (globales)

              // -- local_Context.getValue( "documento/numero" );

              do_PageProcessingProcess_PageSaveState_Object(
                 request
                , session
                , CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS
                , local_Context.getValue( "documento/comentario" )
              );

              local_Context.setValue( "$dateConversion",  local_Context.getValue( "documento/fecha" ) );


              do_PageProcessingProcess_PageSaveState_Object(
                 request
                , session
                , CDatosAntiguoSistema.DOCUMENTO_FECHA_AS
                , (String)local_Context.getValue( "format:date( $dateConversion, 'MM/dd/yyyy' )" )
              );
              do_PageProcessingProcess_PageSaveState_Object(
                 request
                , session
                , CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS
                , local_Context.getValue( "documento/numero" )
              );

              // :: Documento/oficinaOrigen
              do_PageProcessingProcess_PageSaveState_Object(
                 request
                , session
                , CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA
                , local_Context.getValue( "documento/oficinaOrigen/idOficinaOrigen" )
              );

              do_PageProcessingProcess_PageSaveState_Object(
                 request
                , session
                , CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM
                , local_Context.getValue( "documento/oficinaOrigen/nombre" )
              );
              // -- local_Context.getValue( "documento/oficinaOrigen/numero" );


              // :: Documento/tipoDocumento

              // -- local_Context.getValue( "documento/tipoDocumento/nombre" );

              do_PageProcessingProcess_PageSaveState_Object(
                 request
                , session
                , CDatosAntiguoSistema.DOCUMENTO_TIPO_AS
                , local_Context.getValue( "documento/tipoDocumento/idTipoDocumento" )
              );

              // :: Documento/oficinaOrigen/vereda
              do_PageProcessingProcess_PageSaveState_Object(
                 request
                , session
                , CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA
                , local_Context.getValue( "documento/oficinaOrigen/vereda/idVereda" )
              );
              do_PageProcessingProcess_PageSaveState_Object(
                 request
                , session
                , CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO
                , local_Context.getValue( "documento/oficinaOrigen/vereda/idDepartamento" )
              );
              do_PageProcessingProcess_PageSaveState_Object(
                 request
                , session
                , CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC
                , local_Context.getValue( "documento/oficinaOrigen/vereda/idMunicipio" )
              );

              //
              //

               do_PageProcessingProcess_PageSaveState_Object(
                  request
                 , session
                 , CDatosAntiguoSistema.DOCUMENTO_TIPO_COMBO_AS
                 , local_Context.getValue( "documento/tipoDocumento/idTipoDocumento" )
               );
           /*
              do_PageProcessingProcess_PageSaveState_Object(
                  request
                , session
                , CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO
                , local_Context.getValue( "documento/oficinaOrigen/vereda/nombreDepartamento" )
              );
           */
              do_PageProcessingProcess_PageSaveState_Object(
                  request
                , session
                , CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_TIPO
                , local_Context.getValue( "documento/oficinaOrigen/tipoOficina/idTipoOficina" )
              );
          do_PageProcessingProcess_PageSaveState_Object(
              request
            , session
            , CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA_HIDDEN
            , local_Context.getValue( "documento/oficinaOrigen/tipoOficina/idTipoOficina" )
          );








              //           -- local_Context.getValue( "documento/oficinaOrigen/vereda/nombre" );


              // Antiguo Sistema

              /*

               String oficina_depto_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_DEPTO);
               oficina_depto_id_as = (oficina_depto_id_as == null) ? ""
                                                                   : oficina_depto_id_as;

               String oficina_depto_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_DEPTO);
               oficina_depto_nom_as = (oficina_depto_nom_as == null) ? ""
                                                                     : oficina_depto_nom_as;

               String oficina_muni_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_MUNIC);
               oficina_muni_id_as = (oficina_muni_id_as == null) ? ""
                                                                 : oficina_muni_id_as;

               String oficina_muni_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_MUNIC);
               oficina_muni_nom_as = (oficina_muni_nom_as == null) ? ""
                                                                   : oficina_muni_nom_as;

               String oficina_vereda_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_VEREDA);
               oficina_vereda_id_as = (oficina_vereda_id_as == null) ? ""
                                                                     : oficina_vereda_id_as;

               String oficina_vereda_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NOM_VEREDA);
               oficina_vereda_nom_as = (oficina_vereda_nom_as == null) ? ""
                                                                       : oficina_vereda_nom_as;

               String oficina_oficia_id_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_ID_OFICINA);
               oficina_oficia_id_as = (oficina_oficia_id_as == null) ? ""
                                                                     : oficina_oficia_id_as;

               String oficina_oficia_nom_as = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_NUM);
               oficina_oficia_nom_as = (oficina_oficia_nom_as == null) ? ""
                                                                        : oficina_oficia_nom_as;




        */






        }

        protected void removeState_Permisos( HttpServletRequest request ){
          HttpSession session;
          session = request.getSession();

          session.removeAttribute( "PARAM_CACHE__PERMISOS" );
          session.removeAttribute( "LOAD_EVENT_PARAM"      );

        }


        protected void saveState_Permisos( HttpServletRequest request ) {
          HttpSession session;
          session = request.getSession();

          String[] local_Data;

          // get local data
          local_Data = request.getParameterValues( WebKeys.LISTA_PARAMETROS_PERMISOS_REVISION );

          if( null == local_Data )
            return;

          if( local_Data.length <= 0 )
            return;

          // put data into cache

          java.util.Vector cache_Data; // Vector< Vector< String > >

          cache_Data = new java.util.Vector();
          String local_ElementId;

          // Column 1
          Vector column;
          column = new java.util.Vector();
          for( int i = 0; i < local_Data.length; i++ ) {

            // get local-data
            local_ElementId = local_Data[i];


            // set cache-data
            column.add( i, local_ElementId );

          }
          cache_Data = column;

          session.setAttribute( "PARAM_CACHE__PERMISOS", cache_Data );


        }

















	// template-part
   /*
	protected void saveState_DatosAntiguoSistema( HttpServletRequest request ) {

		HttpSession session = request.getSession();

		String keyAntSistema[] = CDatosAntiguoSistema.LISTA_KEYS_DATOS_ANTIGUO_SISTEMA;
		String key, valor;

		for( int i = 0; i < keyAntSistema.length; i++ ) {
			key = keyAntSistema[i];
			valor = request.getParameter(key);
			if (valor != null && !valor.trim().equals("")) {
				session.setAttribute(key, valor);
			}
		}

	}
   */

	/**
	 * Bind Form2Data
	 *
	Datos adicionales de antiguo sistema:
	* Libro
	  * Tipo
	  * Número - Letra
	  * Página
	  * Año
	* Tomo
	  * Número
	  * Página
	  * Municipio
	  * Año
	* Datos del documento (Opcionales)
	  * Número de documento
	  * Tipo de documento
	  * Fecha del documento
	  * Comentario
	  * Oficina origen (opcional)
	    * Tipo
	    * Número
	    * Departamento
	    * Municipio
	* Comentario
	* @param request
	* @return un objeto que representa los datos de antiguo sistema ingresados por el usuario.
	*/
  /*
	private DatosAntiguoSistema getDatosAntiguoSistema( HttpServletRequest request ) {
		String listaKeysAntSistema[] = CDatosAntiguoSistema.LISTA_KEYS_DATOS_ANTIGUO_SISTEMA;
		Vector keysLlenas = new Vector();
		String key, valor;
		for (int i = 0; i < listaKeysAntSistema.length; i++) {
			key = listaKeysAntSistema[i];
			valor = request.getParameter(key);
			if (valor != null) {
				if (valor.trim().length() > 0) {
					keysLlenas.add(key);
				}
			}
		}

		DatosAntiguoSistema datosAntSistema = null;

		if (keysLlenas.size() > 0) {
			datosAntSistema = new DatosAntiguoSistema();
			for (int i = 0; i < keysLlenas.size(); i++) {
				key = (String) keysLlenas.get(i);
				datosAntSistema = this.setDatoAntiguoSistema(request, key, datosAntSistema);

			}
		}

		return datosAntSistema;
	}
*/
    /**
    * Datos del documento (Opcionales)
      * Número de documento
      * Tipo de documento
      * Fecha del documento
      * Comentario
      * Oficina origen (opcional)
        * Tipo
        * Número
        * Departamento
        * Municipio
    * @param request
    * @return un objeto que representa los datos de antiguo sistema ingresados por el usuario.
    */
    private Documento getDocumento(HttpServletRequest request) {
        Documento documento = new Documento();

        String listaKeysDocumento[] = CDatosAntiguoSistema.LISTA_KEYS_DOCUMENTO;
        Vector keysLlenas = new Vector();
        String key, valor;
        for (int i = 0; i < listaKeysDocumento.length; i++) {
            key = listaKeysDocumento[i];
            valor = request.getParameter(key);
            if (valor != null) {
                if (valor.trim().length() > 0) {
                    keysLlenas.add(key);
                }
            }
        }

        if (keysLlenas.size() > 0) {
            for (int i = 0; i < keysLlenas.size(); i++) {
                key = (String) keysLlenas.get(i);
                documento = this.setDatoDocumento(request, key, documento);

            }
        }

        //estas llaves de los datos de la oficina no estan en el arreglo de datos de CDatosAntiguoSistema

        String idDepto=request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        String idMunic=request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        String idVereda=request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        String idTipo=request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
        String idOficina=request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
        */
        String versionOficina=request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_OFICINA_VERSION);
        String nomDepto=request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        String nomMunic=request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        String nomVereda=request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        String tipo=request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        String numero=request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);


        OficinaOrigen ofic=new OficinaOrigen();

        if (idDepto!=null&&idMunic!=null&&idVereda!=null){

            Vereda vereda=new Vereda();
            vereda.setIdVereda(idVereda);
            if (nomVereda!=null){
                vereda.setNombre(nomVereda);
            }

            Municipio municipio = new Municipio();
            municipio.setIdMunicipio(idMunic);
            if (nomMunic!=null){
                municipio.setNombre(nomMunic);
            }
            vereda.setMunicipio(municipio);

            Departamento depto=new Departamento();
            depto.setIdDepartamento(idDepto);
            if (nomDepto!=null){
                depto.setNombre(nomDepto);
            }
            municipio.setDepartamento(depto);

            ofic.setVereda(vereda);

        }
        if (idOficina!=null){
            ofic.setIdOficinaOrigen(idOficina);
        }
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
        */
        if (versionOficina!=null){
            ofic.setVersion(Long.parseLong(versionOficina));
        }
        
        TipoOficina tipoOfic=new TipoOficina();
        if (idTipo!=null){
            tipoOfic.setIdTipoOficina(idTipo);
            tipoOfic.setNombre(tipo);

        }

        ofic.setTipoOficina(tipoOfic);
        documento.setOficinaOrigen(ofic);

        return documento;
    }

    /**
     *
     * DOCUMENTO_COMENTARIO_AS,
     * DOCUMENTO_FECHA_AS,
     * DOCUMENTO_NUMERO_AS,
     * DOCUMENTO_OFICINA_DEPARTAMENTO_AS,
     * DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS,
     * DOCUMENTO_OFICINA_MUNICIPIO_AS,
     * DOCUMENTO_OFICINA_MUNICIPIO_ID_AS,
     * DOCUMENTO_OFICINA_NUMERO_AS,
     * DOCUMENTO_OFICINA_NUMERO_ID_AS,
     * DOCUMENTO_OFICINA_TIPO_AS,
     * DOCUMENTO_TIPO_AS
     * @param request
     * @param key
     * @param documento
     * @return
     */
    private Documento setDatoDocumento(HttpServletRequest request, String key, Documento documento) {
        String valor = request.getParameter(key);

        /*
         * DATOS DEL DOCUMENTO
         Datos del documento (Opcionales)
          * Número de documento
          * Tipo de documento
          * Fecha del documento
          * Comentario
          * Oficina origen (opcional)
            * Tipo
            * Número
            * Departamento
            * Municipio
            */
        if (key.equals(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS)) {
            documento.setNumero(valor);
        } else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS)) {
            TipoDocumento tipoDoc = documento.getTipoDocumento();

            if (tipoDoc == null)
                tipoDoc = new TipoDocumento();

            tipoDoc.setIdTipoDocumento(valor);
            documento.setTipoDocumento(tipoDoc);
        } else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS)) {
            Calendar calendar = darFecha(valor);
            if (calendar != null) {
                Date fecha = calendar.getTime();
                documento.setFecha(fecha);
            }
        } else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS)) {
            documento.setComentario(valor);
        } else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS)) {
            OficinaOrigen oficina = documento.getOficinaOrigen();

            if (oficina == null)
                oficina = new OficinaOrigen();

            Vereda vereda = oficina.getVereda();

            if (vereda == null)
                vereda = new Vereda();

            Municipio municipio = vereda.getMunicipio();

            if (municipio == null)
                municipio = new Municipio();

            Departamento depto = municipio.getDepartamento();
            if (depto == null)
                depto = new Departamento();

            depto.setNombre(valor);

            municipio.setDepartamento(depto);
            vereda.setMunicipio(municipio);
            oficina.setVereda(vereda);

            documento.setOficinaOrigen(oficina);
        } else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS)) {
            OficinaOrigen oficina = documento.getOficinaOrigen();

            if (oficina == null)
                oficina = new OficinaOrigen();

            Vereda vereda = oficina.getVereda();

            if (vereda == null)
                vereda = new Vereda();

            Municipio municipio = vereda.getMunicipio();

            if (municipio == null)
                municipio = new Municipio();

            Departamento depto = municipio.getDepartamento();
            if (depto == null)
                depto = new Departamento();

            depto.setIdDepartamento(valor);

            municipio.setDepartamento(depto);
            vereda.setMunicipio(municipio);
            oficina.setVereda(vereda);

            documento.setOficinaOrigen(oficina);
        } else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS)) {
            OficinaOrigen oficina = documento.getOficinaOrigen();

            if (oficina == null)
                oficina = new OficinaOrigen();

            Vereda vereda = oficina.getVereda();

            if (vereda == null)
                vereda = new Vereda();

            Municipio municipio = vereda.getMunicipio();

            if (municipio == null)
                municipio = new Municipio();

            municipio.setNombre(valor);
            vereda.setMunicipio(municipio);
            oficina.setVereda(vereda);

            documento.setOficinaOrigen(oficina);
        } else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS)) {
            OficinaOrigen oficina = documento.getOficinaOrigen();

            if (oficina == null)
                oficina = new OficinaOrigen();

            Vereda vereda = oficina.getVereda();

            if (vereda == null)
                vereda = new Vereda();

            Municipio municipio = vereda.getMunicipio();

            if (municipio == null)
                municipio = new Municipio();

            municipio.setIdMunicipio(valor);
            vereda.setMunicipio(municipio);
            oficina.setVereda(vereda);

            documento.setOficinaOrigen(oficina);
        } else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS)) {
            OficinaOrigen oficina = documento.getOficinaOrigen();

            if (oficina == null)
                oficina = new OficinaOrigen();

            oficina.setNumero(valor);

            documento.setOficinaOrigen(oficina);
        } else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS)) {
            OficinaOrigen oficina = documento.getOficinaOrigen();

            if (oficina == null)
                oficina = new OficinaOrigen();

            oficina.setIdOficinaOrigen(valor);

            documento.setOficinaOrigen(oficina);
        } /*
           *  @author Carlos Torres
           *  @chage   se agrega validacion de version diferente
           *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
           */
        else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION)) {
            OficinaOrigen oficina = documento.getOficinaOrigen();

            if (oficina == null)
                oficina = new OficinaOrigen();

            oficina.setVersion(Long.parseLong(valor));

            documento.setOficinaOrigen(oficina);
        }else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_TIPO_AS)) {
            OficinaOrigen oficina = documento.getOficinaOrigen();

            if (oficina == null)
                oficina = new OficinaOrigen();

            TipoOficina tipoOfic = oficina.getTipoOficina();
            if (tipoOfic == null)
                tipoOfic = new TipoOficina();

            tipoOfic.setIdTipoOficina(valor);
            oficina.setTipoOficina(tipoOfic);

            documento.setOficinaOrigen(oficina);
        }

        return documento;
    }

    private Calendar darFecha(String fechaInterfaz) {
        Calendar calendar = Calendar.getInstance();
        String[] partido = fechaInterfaz.split("/");

        if (partido.length == 3) {
            int dia = Integer.parseInt(partido[0]);
            int mes = Integer.parseInt(partido[1]) - 1;
            int año = Integer.parseInt(partido[2]);
            calendar.set(año, mes, dia);

            if ((calendar.get(Calendar.YEAR) == año) && (calendar.get(Calendar.MONTH) == mes) && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
                if (!calendar.after(Calendar.getInstance())) {
                    return calendar;
                }
            }
        }

        return null;
    }

	/**
	 * Retorna los datos de antiguo sistema despues de settear la propiedad correspondiente a la llave "key"
	 * en le objeto "datosAntSistema".
	  Datos adicionales de antiguo sistema:
	* Libro
	  * Tipo
	  * Número - Letra
	  * Página
	  * Año
	* Tomo
	  * Número
	  * Página
	  * Municipio
	  * Año
	* Datos del documento (Opcionales)
	  * Número de documento
	  * Tipo de documento
	  * Fecha del documento
	  * Comentario
	  * Oficina origen (opcional)
	    * Tipo
	    * Número
	    * Departamento
	    * Municipio
	 * Comentario
	 * @param request
	 * @param key
	 * @param datosAntSistema
	 * @return
	 */
	private DatosAntiguoSistema setDatoAntiguoSistema(HttpServletRequest request, String key, DatosAntiguoSistema datosAntSistema) {
		String valor = request.getParameter(key);

		/*
		DATOS DEL LIBRO:
		Libro
		  * Tipo
		  * Número - Letra
		  * Página
		  * Año
		*/
		//Libro.Tipo
		if (key.equals(CDatosAntiguoSistema.LIBRO_TIPO_AS)) {
			datosAntSistema.setLibroTipo(valor);
		}
		//Libro.Número - Letra
		else if (key.equals(CDatosAntiguoSistema.LIBRO_NUMERO_AS)) {
			datosAntSistema.setLibroNumero(valor);
		}
		//Libro.Página
		else if (key.equals(CDatosAntiguoSistema.LIBRO_PAGINA_AS)) {
			datosAntSistema.setLibroPagina(valor);
		}
		//Libro.Año
		else if (key.equals(CDatosAntiguoSistema.LIBRO_ANO_AS)) {
			datosAntSistema.setLibroAnio(valor);
		}

		/*
		 * DATOS DEL TOMO
		 Tomo
		  * Número
		  * Página
		  * Municipio
		  * Año
		 */
		else if (key.equals(CDatosAntiguoSistema.TOMO_NUMERO_AS)) {
			datosAntSistema.setTomoNumero(valor);
		} else if (key.equals(CDatosAntiguoSistema.TOMO_PAGINA_AS)) {
			datosAntSistema.setTomoPagina(valor);
		} else if (key.equals(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS)) {
			datosAntSistema.setTomoMunicipio(valor);
		} else if (key.equals(CDatosAntiguoSistema.TOMO_ANO_AS)) {
			datosAntSistema.setTomoAnio(valor);
		}

		/*
		 * DATOS DE LA OFICINA DE ORIGEN
		 Oficina origen (opcional)
		  * Tipo
		  * Número
		  * Departamento
		  * Municipio
		 */
		else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_TIPO_AS)) {
			//datosAntSistema.setDocumentoOficinaTipo(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS)) {
			//datosAntSistema.setDocumentoOficinaNumero(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS)) {
			//datosAntSistema.setDocumentoOficinaDepartamento(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS)) {
			//datosAntSistema.setDocumentoOficinaMunicipio(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS)) {
			//datosAntSistema.setDocumentoOficinaComentario(valor);
		}
		/*
		 * DATOS DEL COMENTARIO
		 */
		else if (key.equals(CDatosAntiguoSistema.COMENTARIO_AS)) {
			datosAntSistema.setComentario(valor);
		}

		return datosAntSistema;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento doAntiguoSistema_PageEvent_Recargar(HttpServletRequest request) {

		HttpSession session = request.getSession();

		saveState( request );
		session.setAttribute( WebKeys.OCULTAR, request.getParameter( WebKeys.OCULTAR ) );

		// forward to the same page
		// not bind business-layer
		return null;

	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento recargarPaginaActuacionAdministrativa(HttpServletRequest request) {

		HttpSession session = request.getSession();

		saveState( request );
		
		String ocultarSolicitud = request.getParameter( CActuacionAdministrativa.OCULTAR_SOLICITUD);
		String ocultarMayorValor = request.getParameter( CActuacionAdministrativa.OCULTAR_MAYOR_VALOR);
		
		if(ocultarSolicitud!=null && !ocultarSolicitud.equals("")){
			session.setAttribute(CActuacionAdministrativa.OCULTAR_SOLICITUD , ocultarSolicitud);
		}
		if(ocultarMayorValor!=null && !ocultarMayorValor.equals("")){
			session.setAttribute(CActuacionAdministrativa.OCULTAR_MAYOR_VALOR , ocultarMayorValor);
		}		
		
		return null;

	}	



	/**
	 * @param request
	 * @return
	 */
	private Evento redireccionar(HttpServletRequest request)
		throws AccionWebException {


		String redireccionar = request.getParameter(REDIRECCIONAR_CASO);

		String avazarWF = "";

		if (redireccionar != null) {
            if (!redireccionar.equals(MAYOR_VALOR)){

                if (redireccionar.equals(ANTIGUO_SISTEMA)) {
                    /*DatosAntiguoSistema dat=getDatosAntiguoSistema(request);
                    Documento docum=getDocumento(request);

                    Turno tur=(Turno) request.getSession().getAttribute(WebKeys.TURNO);
                    tur.getSolicitud().setDatosAntiguoSistema(dat);*/
                    return doAntiguoSistema_Redireccionar( request );
                    // avazarWF = EvnRevision.ANTIGUO_SISTEMA;
                }
                if (redireccionar.equals(ACTUACION_ADMINISTRATIVA)) {
                    avazarWF = EvnRevision.ACTUACION_ADMINISTRATIVA;
                }

                org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga =
                    (org.auriga.core.modelo.transferObjects.Usuario) request
                        .getSession()
                        .getAttribute(SMARTKeys.USUARIO_EN_SESION);

                Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
                Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

                gov.sir.core.negocio.modelo.Usuario usuarioSIR =
                    (gov.sir.core.negocio.modelo.Usuario) request
                        .getSession()
                        .getAttribute(
                        WebKeys.USUARIO);

                EvnRevision evn =
                    new EvnRevision(
                        usuarioAuriga,
                        usuarioSIR,
                        turno,
                        fase,
                        EvnRevision.REDIRECCIONAR_CASO,
                        avazarWF);

                return evn;
            }
		}
        return null;

	}

	/**
	 * @param request
	 * @return
	 */
	private Evento redireccionarAct(HttpServletRequest request)
		throws AccionWebException {


		String redireccionar = request.getParameter(REDIRECCIONAR_CASO_ACT);

		String avazarWF = "";

		if (redireccionar != null) {
			if (!redireccionar.equals(MAYOR_VALOR)){

				if (redireccionar.equals(ANTIGUO_SISTEMA)) {
					/*DatosAntiguoSistema dat=getDatosAntiguoSistema(request);
					Documento docum=getDocumento(request);

					Turno tur=(Turno) request.getSession().getAttribute(WebKeys.TURNO);
					tur.getSolicitud().setDatosAntiguoSistema(dat);*/
					return doAntiguoSistema_Redireccionar_Act( request );
					// avazarWF = EvnRevision.ANTIGUO_SISTEMA;
				}
			}
		}
		return null;

	}

	private Evento
        doAntiguoSistema_Redireccionar( HttpServletRequest request)
        throws AccionWebException {

        // :: Exception Collector
        ValidacionColectorCorreccionesRevisionException exception
        = new ValidacionColectorCorreccionesRevisionException();


            // :: Bind Form2Data

            DatosAntiguoSistema datosAntiguoSistema
            = getDatosAntiguoSistema( request, exception );



            // :: Raise
            if( exception.getErrores().size() > 0 ) {
                    //logger.error("");
                    throw exception;
            }

            // ------------------------------------------------------------------


        /*Documento docum= getDocumento(request);
        datosAntiguoSistema.setDocumento(docum);*/
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
		  = (org.auriga.core.modelo.transferObjects.Usuario) request
			.getSession()
			.getAttribute( SMARTKeys.USUARIO_EN_SESION );

		Turno turno = (Turno) request.getSession().getAttribute( WebKeys.TURNO );
		Fase fase   = (Fase) request.getSession().getAttribute( WebKeys.FASE );

		gov.sir.core.negocio.modelo.Usuario usuarioSIR
		  =	(gov.sir.core.negocio.modelo.Usuario) request
			.getSession()
			.getAttribute( WebKeys.USUARIO );




		// pasar datos de antiguo sistema
		// a business-layer a traves de evento

		EvnRevision evn =
			new EvnRevision(
				usuarioAuriga,
				usuarioSIR,
				turno,
                datosAntiguoSistema,
				fase,
				EvnRevision.REDIRECCIONAR_CASO,
				EvnRevision.ANTIGUO_SISTEMA);


		return evn;

	}

	private Evento
		doAntiguoSistema_Redireccionar_Act( HttpServletRequest request)
		throws AccionWebException {

              // :: Exception Collector
              ValidacionColectorCorreccionesRevisionException exception
              = new ValidacionColectorCorreccionesRevisionException();

		// :: Bind Form2Data

		DatosAntiguoSistema datosAntiguoSistema
		 = getDatosAntiguoSistema( request, exception );

               // Documento docum= getDocumento(request);
               // datosAntiguoSistema.setDocumento(docum);

               // :: Raise
               if( exception.getErrores().size() > 0 ) {
                       //logger.error("");
                       throw exception;
               }

               // ------------------------------------------------------------------



		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga
		  = (org.auriga.core.modelo.transferObjects.Usuario) request
			.getSession()
			.getAttribute( SMARTKeys.USUARIO_EN_SESION );

		Turno turno = (Turno) request.getSession().getAttribute( WebKeys.TURNO );
		Fase fase   = (Fase) request.getSession().getAttribute( WebKeys.FASE );

		gov.sir.core.negocio.modelo.Usuario usuarioSIR
		  =	(gov.sir.core.negocio.modelo.Usuario) request
			.getSession()
			.getAttribute( WebKeys.USUARIO );




		// pasar datos de antiguo sistema
		// a business-layer a traves de evento

		EvnRevision evn =
			new EvnRevision(
				usuarioAuriga,
				usuarioSIR,
				turno,
				datosAntiguoSistema,
				fase,
				EvnRevision.REDIRECCIONAR_CASO_ACT,
				EvnRevision.ANTIGUO_SISTEMA);


		return evn;

	}



	/**
	 * Puede asociar un rango de matriculas
	 * Garantiza que las matriculas quedan asociadas
	 * @param request con ID_MATRICULA_RL y ID_MATRICULA_RR
	 * @return Un evento de tipo ASOCIAR_UN_RANGO
	 */
	private Evento asociarUnRango(HttpServletRequest request)
		throws AccionWebException {
                
                Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		String idMatRL = request.getParameter(CFolio.ID_MATRICULA_RL);
		String idMatRR = request.getParameter(CFolio.ID_MATRICULA_RR);
                
                idMatRL = circulo.getIdCirculo() + "-" + idMatRL;
                idMatRR = circulo.getIdCirculo() + "-" + idMatRR;

		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

		Usuario usuario =
			(Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg =
			(gov.sir.core.negocio.modelo.Usuario) request
				.getSession()
				.getAttribute(
				WebKeys.USUARIO);
                /**
                        * @Author Carlos Torres
                        * @Mantis 13176
                        * @Chaged ;
                        */
                        gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
                        EvnRevision evento = new EvnRevision(usuario,EvnRevision.ASOCIAR_UN_RANGO,turno,idMatRL,idMatRR,usuarioNeg);
                        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSir.getUsername(),String.valueOf(usuarioSir.getIdUsuario()));
                        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                        evento.setInfoUsuario(infoUsuario);

		return evento;
	}

	/**
	 * Puede asociar una matrícula
	 * Garantiza que la matrícula que quede asociada, puede ser afectada por el documento bajo radicación
	 * @param request La información del formulario
	 * @return Un evento confrontación de tipo ASOCIAR_UNA_MATRICULA
	 * @throws AccionWebException
	 */
	private EvnRevision asociarUnaMatricula(HttpServletRequest request)
		throws AccionWebException {
                
                Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
                idMatricula = circulo.getIdCirculo() + "-" + idMatricula;
                /**
                 * @autor Edgar Lora
                 * @mantis 11987
                 */
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                ValidacionParametrosException exception = new ValidacionParametrosException();
                try {
                    if(validacionesSIR.isEstadoFolioBloqueado(idMatricula)){
                        exception.addError("El folio que desea consultar se encuentra en estado 'Bloqueado'.");
                    }
                } catch (GeneralSIRException ex) {
                    if(ex.getMessage() != null){
                        exception.addError(ex.getMessage());
                    }
                }
                /**
                * @Autor: Edgar Lora
                * @Mantis: 11309                                * 
                */
                TrasladoSIR trasladoSir = new TrasladoSIR();
                try {
                    if(trasladoSir.isBloqueDeTraslado(idMatricula)){
                        exception.addError("El folio " + idMatricula + " esta pendiente por confirmar traslado.");
                    }
                } catch (GeneralSIRException ex) {
                    exception.addError(ex.getMessage());
                }
                if(exception.getErrores().size() > 0){
                    throw exception;
                }
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

		Folio folio = new Folio();
		folio.setIdMatricula(idMatricula);

		Usuario usuario =
			(Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioNeg =
			(gov.sir.core.negocio.modelo.Usuario) request
				.getSession()
				.getAttribute(
				WebKeys.USUARIO);
                   /**
                        * @Author Carlos Torres
                        * @Mantis 13176
                        * @Chaged ;
                        */
                        gov.sir.core.negocio.modelo.Usuario usuarioSir = (gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
                        EvnRevision evento = new EvnRevision(
                                                            usuario,
                                                            EvnRevision.ASOCIAR_UNA_MATRICULA,
                                                            turno,
                                                            folio,
                                                            usuarioNeg);
                        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioSir.getUsername(),String.valueOf(usuarioSir.getIdUsuario()));
                        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                        evento.setInfoUsuario(infoUsuario);
                
		return evento;

	}

	/**
	 * Puede asociar matrículas como un rango de matrículas
	 * Garantiza que las matrículas que quedan asociadas son las que pueden
	 * ser afectadas por el documento bajo radicación
	 * @param request La información del formulario
	 * @return Un evento confrontación de tipo ASOCIAR_RANGO_MATRICULAS
	 * @throws AccionWebException
	 */
	private EvnRevision eliminarMatricula(HttpServletRequest request)
		throws AccionWebException {

		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Usuario usuario =
			(Usuario) request.getSession().getAttribute(
				SMARTKeys.USUARIO_EN_SESION);

		List folios = new ArrayList();
		String[] matriculasImp =
			request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
		if (matriculasImp != null) {
			for (int i = 0; i < matriculasImp.length; i++) {
				Folio folio = new Folio();
				folio.setIdMatricula(matriculasImp[i]);
				folios.add(folio);
			}
		}
		if (folios.size() > 0) {
                     /**
                * @Author Carlos Torres
                * @Mantis 13176
                * @Chaged ;
                */
		EvnRevision evento = new EvnRevision(
				usuario,
				EvnRevision.ELIMINAR_UNA_MATRICULA,
				turno,
				folios);
                gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(),String.valueOf(usuarioNeg.getIdUsuario()));
                infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                evento.setInfoUsuario(infoUsuario);
			return evento;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespRevision ev = (EvnRespRevision) evento;

		if (ev != null) {
			if( EvnRespRevision.TURNO.equals( ev.getTipoEvento() ) ) {
				Turno turno = (Turno) ev.getPayload();
				request.getSession().setAttribute(WebKeys.TURNO, turno);

                                removeState_Permisos( request );
                                removeState_DatosAntiguoSistema( request );

			}

		}

	}
}

package gov.sir.core.web.acciones.correccion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.comun.modelo.Transaccion;
/*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se habilita el uso de las clases
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import co.com.iridium.generalSIR.transacciones.TransaccionSIR;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;

import java.text.ParseException;


import gov.sir.core.negocio.acciones.registro.SerialIds;
import gov.sir.core.eventos.correccion.EvnCorreccion;
import gov.sir.core.eventos.correccion.EvnRespCorreccion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoRecepcionPeticion;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSolicitud;
import gov.sir.core.negocio.modelo.constantes.CTipoRecepcion;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionLiquidacionCorreccionException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con la liquidación
 * en el proceso de correcciones. Se encarga de realizar las validaciones primarias a nivel web y
 * por medio de eventos hace llamados a la capa de negocio para que su vez llame los servicios
 * que se requieren.
 * @author ppabon, jvelez
 */
public class AWLiquidacionCorreccion extends SoporteAccionWeb {
	
  public static final String PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION =
		"PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION";


  /**
	* Constante que identifica que se desea liquidar la
	* solicitud de una corrección
	*/
  public final static String LIQUIDAR = "LIQUIDAR";

  /**
	* Constante que identifica que se desea agregar una
	* nueva matrícula a la solicitud
	*/
  public final static String AGREGAR = "AGREGAR";

  /**
	* Constante que identifica que se desea eliminar
	* una matrícula de la solicitud
	*/
  public final static String ELIMINAR = "ELIMINAR";
  
  /**
	* Constante que identifica que se desea agregar una
	* nueva matrícula no migrada a la solicitud
	*/
  public final static String AGREGAR_MIG_INC = "AGREGAR_MIG_INC";
  
  /**
   * Constante que identifica que se desea agregar un rango de matriculas 
   * a las solicitud
   */
  
  public final static String AGREGAR_RANGO="AGREGAR_RANGO";

  /**
	* Constante que identifica que se desea eliminar
	* una matrícula no migrada de la solicitud
	*/
  public final static String ELIMINAR_MIG_INC = "ELIMINAR_MIG_INC";
  

  public final static String RECIBIDO_CORREO = "RECIBIDO_CORREO";
  public final static String INTERES_JURIDICO = "INTERES_JURIDICO";
  public final static String RECIBIDO_SIMILAR_ANTES = "RECIBIDO_SIMILAR_ANTES";
  public final static String NUMERO_COPIAS_CORRECCION="NUMERO_COPIAS_CORRECCION";
  /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  Constantes para la correccion de testamentos
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
  public final static String AGREGAR_TURNO_TESTAMENTO = "AGREGAR_TURNO_TESTAMENTO";
  public final static String ELIMINAR_TURNO_TESTAMENTO = "ELIMINAR_TURNO_TESTAMENTO";
  public final static String VALIDAR_TURNO_TESTAMENTO = "VALIDAR_TURNO_TESTAMENTO";


  /**
	* Variable donde se guarda la accion enviada en el request
	*/
  private String accion;
  
  private List turnosFolioMig = null;

  /**
	* Constructor de la clase AWTurnoCorreccion
	*/
  public AWLiquidacionCorreccion() {
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
        /*AHERRENO
         28/05/2012
         REQ 076_151 TRANSACCION*/
        request.getSession().setAttribute("TIEMPO_INICIO_TRANSACCION", new Date());
	 if ((accion == null) || (accion.trim().length() == 0)) {
		throw new AccionInvalidaException("Debe indicar una accion valida");
	 }


	 if (PAGE_REGION__DATOSANTIGUOSISTEMA_RECARGAR_ACTION.equals(accion)) {
		return doAntiguoSistema_PageEvent_Recargar(request);
	 }

	 if (accion.equals(LIQUIDAR)) {
		return liquidar(request);
	 }
	 else if (accion.equals(AGREGAR)) {
		return agregarMatricula(request);
	 }
	 else if (accion.equals(ELIMINAR)) {
		return eliminarMatricula(request);
	 }
	 else if (accion.equals(AGREGAR_MIG_INC)) {
		return agregarMatriculaSirMig(request);
	 }
	 else if (accion.equals(ELIMINAR_MIG_INC)) {
		return eliminarMatriculaSirMig(request);
	 }
	 else if(accion.equals(AGREGAR_RANGO)) {
		 return agregarRangoMatricula(request);
	 }
         /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  Condiciones en el caso de que la accion sea de testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
         else if(accion.equals(VALIDAR_TURNO_TESTAMENTO)) {
		 return validarTurnoTestamento(request);
	 }
         else if(accion.equals(ELIMINAR_TURNO_TESTAMENTO)) {
		 return eliminarTurnoTestamento(request);
	 }
	 else {
		throw new AccionInvalidaException("La accion " + accion +
													 " no es valida.");
	 }
  }


  // template-part
  protected void saveState_DatosAntiguoSistema(HttpServletRequest request) {

	 HttpSession session = request.getSession();

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
	* @param request
	* @return
	*/
  private Evento doAntiguoSistema_PageEvent_Recargar(HttpServletRequest request) {

	 HttpSession session = request.getSession();

	 preservarInfo(request);
	 // saveState( request );
	 session.setAttribute(WebKeys.OCULTAR, request.getParameter(WebKeys.OCULTAR));

	 // forward to the same page
	 // not bind business-layer
	 return null;

  }

  private DatosAntiguoSistema getDatosAntiguoSistema(HttpServletRequest request, ValidacionLiquidacionCorreccionException exception ) {
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
         /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
         oficinaOrigenAS.setVersion(Long.parseLong(oficina_oficia_version_as));

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
                     Log.getInstance().debug(AWLiquidacionCorreccion.class,
                         "[Colocando en Antiguo sistema Oficina de origen]");
                 }

                 Log.getInstance().debug(AWLiquidacionCorreccion.class,
                     "[Colocando en Antiguo sistema tipo documento]");
             }

             Log.getInstance().debug(AWLiquidacionCorreccion.class,
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


  private DatosAntiguoSistema setDatoAntiguoSistema(HttpServletRequest request,
		String key, DatosAntiguoSistema datosAntSistema) {
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
	 }
	 else if (key.equals(CDatosAntiguoSistema.TOMO_PAGINA_AS)) {
		datosAntSistema.setTomoPagina(valor);
	 }
	 else if (key.equals(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS)) {
		datosAntSistema.setTomoMunicipio(valor);
	 }
	 else if (key.equals(CDatosAntiguoSistema.TOMO_ANO_AS)) {
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
	 }
	 else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS)) {
		//datosAntSistema.setDocumentoOficinaNumero(valor);
	 }
	 else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS)) {
		//datosAntSistema.setDocumentoOficinaDepartamento(valor);
	 }
	 else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS)) {
		//datosAntSistema.setDocumentoOficinaMunicipio(valor);
	 }
	 else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS)) {
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
	* Método que permite eliminar matrículas asociadas a la corrección.
	* @param request
	* @return
	*/
  private EvnCorreccion eliminarMatricula(HttpServletRequest request) {

	 preservarInfo(request);

	 List matriculas = (List) request.getSession().getAttribute(WebKeys.
		  LISTA_MATRICULAS_SOLICITUD_CORRECCION);

	 if (matriculas == null) {
		matriculas = new ArrayList();
	 }

	 String[] matriculasElim = request.getParameterValues(WebKeys.
		  TITULO_CHECKBOX_ELIMINAR);
	 if (matriculasElim != null) {
		for (int i = 0; i < matriculasElim.length; i++) {
		  String actual = matriculasElim[i];
		  matriculas.remove(actual);
		}
	 }

	 return null;
  }

  /**
	* Método que permite asociar nuevas matrículas a la solicitud de corrección.
	* @param request
	* @return
	*/
  private EvnCorreccion agregarMatricula(HttpServletRequest request) throws
		ValidacionLiquidacionCorreccionException {

	 preservarInfo(request);

	 List matriculas = (List) request.getSession().getAttribute(WebKeys.
		  LISTA_MATRICULAS_SOLICITUD_CORRECCION);
	 if (matriculas == null) {
		matriculas = new ArrayList();
	 }
         
         Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
	 String matricula = request.getParameter(CFolio.ID_MATRICULA);
         
         matricula = circulo.getIdCirculo() + "-" + matricula;
         
	 if (matricula == null || matricula.trim().equals("")) {
		ValidacionLiquidacionCorreccionException e = new
			 ValidacionLiquidacionCorreccionException();
		e.addError("Matrícula inválida");
		throw e;
	 }
	 if (matriculas.contains(matricula)) {
		ValidacionLiquidacionCorreccionException e = new
			 ValidacionLiquidacionCorreccionException();
		e.addError("La matrícula que quiere ingresar, ya fue ingresada");
		throw e;
	 }
        /**
        * @autor Edgar Lora
        * @mantis 11987
        */
        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
        ValidacionLiquidacionCorreccionException exception = new
			 ValidacionLiquidacionCorreccionException();
        try {
            if(validacionesSIR.isEstadoFolioBloqueado(matricula)){
                exception.addError("La matricula que desea agregar tiene como estado 'Bloqueado'.");
                throw exception;
            }
        } catch (GeneralSIRException ex) {
            if(ex.getMessage() != null){
                exception.addError(ex.getMessage());
            }
            throw exception;
        }

	 Folio folio = new Folio();
	 folio.setIdMatricula(matricula);

	 org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.
		  modelo.transferObjects.Usuario) request.getSession().getAttribute(
		  SMARTKeys.USUARIO_EN_SESION);
	 return new EvnCorreccion(usuario, folio, EvnCorreccion.VALIDAR_MATRICULA_EX);

  }
  
  /**
   * Metodo que permite asociar un rango de matriculas a un turno de Correccion
   * en etapa inicial
   */
  
    private EvnCorreccion agregarRangoMatricula(HttpServletRequest request) throws ValidacionLiquidacionCorreccionException
    {
    	List matriculas = (List) request.getSession().getAttribute(WebKeys.
    			  LISTA_MATRICULAS_SOLICITUD_CORRECCION);
    	 if (matriculas == null) {
            matriculas = new ArrayList();                         
         }
    	 Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
    	 String matriculainicial = request.getParameter(CFolio.ID_MATRICULA_RL);
    	 String matriculafinal=request.getParameter(CFolio.ID_MATRICULA_RR);
         
         matriculainicial = circulo.getIdCirculo() + "-" + matriculainicial;
         matriculafinal = circulo.getIdCirculo() + "-" + matriculafinal;
         
    	 if (matriculainicial == null || matriculainicial.trim().equals("") || matriculafinal== null || matriculafinal.trim().equals("")) 
    	 {
    			ValidacionLiquidacionCorreccionException e = new ValidacionLiquidacionCorreccionException();
    			e.addError("Alguna de las Matriculas es Invalida");
    			throw e;
    	 }
    	 if (matriculas.contains(matriculainicial) || matriculas.contains(matriculafinal)) {
    			ValidacionLiquidacionCorreccionException e = new
    				 ValidacionLiquidacionCorreccionException();
    			e.addError("La matrícula que quiere ingresar, ya fue ingresada");
    			throw e;
    		 }
        /**
        * @autor Edgar Lora
        * @mantis 11987
        */
         if(matriculas.isEmpty()){
             ValidacionesSIR validacionesSIR = new ValidacionesSIR();
            /**
            * @Autor: Edgar Lora
            * @Mantis: 11309
            */
             TrasladoSIR trasladoSir = new TrasladoSIR();
             
             ValidacionLiquidacionCorreccionException e = new ValidacionLiquidacionCorreccionException();
             SerialIds si = new SerialIds(matriculainicial,matriculafinal);
            if(si.isSerialExists()){                                                                              
                while(si.hasNext()){
                    String matriculaLeida=si.nextValue();
                    try {
                        if(validacionesSIR.isEstadoFolioBloqueado(matriculaLeida)){
                            e.addError("La matricula " + matriculaLeida + " se encuentra en estado 'Bloqueado'.");
                            /**
                            * @Autor: Edgar Lora
                            * @Mantis: 11309
                            */
                        } else if(trasladoSir.isBloqueDeTraslado(matriculaLeida)){
                            e.addError("El folio " + matriculaLeida + " esta pendiente por confirmar traslado.");
                            /**
                            * @autor Carlos Torres
                            * @mantis 11309
                            */
                        }else if(trasladoSir.trasladadoFolio(matriculaLeida))
                        {
                            e.addError("El folio " + matriculaLeida + " esta en estado Trasladado");
                        }
                        else{
                            matriculas.add(matriculaLeida);
                        }
                    } catch (GeneralSIRException ex) {
                        if(ex.getMessage() != null){
                            e.addError(ex.getMessage());
                        }
                    }
                }
                if(e.getErrores().size() > 0){
                    throw e;
                }
                if(!matriculas.isEmpty()){
                    request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION, matriculas);
                }
            }
         }
    	
    	 java.util.List listaFolios=new ArrayList();
    	 listaFolios.add(matriculainicial);
    	 listaFolios.add(matriculafinal);
    	 
    	 org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.
    			  modelo.transferObjects.Usuario) request.getSession().getAttribute(
    			  SMARTKeys.USUARIO_EN_SESION);
    	 
    	 Folio fol=new Folio();
    	 fol.setIdMatricula(matriculainicial);
    	 
    	 EvnCorreccion evento= new EvnCorreccion(usuario, fol, EvnCorreccion.VALIDAR_RANGO_MATRICULAS_EX);
    	 evento.setFolios(listaFolios);
    	 return evento;
    }
    /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  nuevo metodo para validar el turno de testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
    private EvnCorreccion validarTurnoTestamento(HttpServletRequest request) throws ValidacionLiquidacionCorreccionException
    {
        
    	 String idturno = (String) request.getParameter("TURNO_TESTAMENTO");
         ValidacionLiquidacionCorreccionException e = new ValidacionLiquidacionCorreccionException();
         List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
    	 if (idturno == null) {
             e.addError("Debe digitar un turno de Testamento");
         }else
         {
             String[] pturno = idturno.split("-");
             Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
             if(pturno.length==4){
                if(!circulo.getIdCirculo().equals(pturno[1]))
                {
                    e.addError("El turno de testamento debe pertenecer al mismo circulo del usuario");
                }
                if(!CProceso.PROCESO_REGISTRO.equals(pturno[2]))
                {
                    e.addError("El turno debe ser del proceso registro");
                }
             }else
             {
                    e.addError("El numero de turno de testamento no tienen el formato correcto");
             }
         }
         if(matriculas!=null && !matriculas.isEmpty())
         {
             e.addError("No se puede agregar turno de testamento. El turno ya tiene matriculas asociadas");
         }
         if(e.getErrores().size()>0){
             throw e;
         }
             
    	 Turno turnoTestamento = new Turno();
         turnoTestamento.setIdWorkflow(idturno);
    	 org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.
    			  modelo.transferObjects.Usuario) request.getSession().getAttribute(
    			  SMARTKeys.USUARIO_EN_SESION);
 	 
    	 EvnCorreccion evento= new EvnCorreccion(usuario,turnoTestamento, EvnCorreccion.VALIDAR_TURNO_TESTAMENTO);

    	 return evento;
    }
    /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  Nuevo Metodo para des asociar turno de testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
    private EvnCorreccion eliminarTurnoTestamento(HttpServletRequest request) throws ValidacionLiquidacionCorreccionException
    {
         request.getSession().removeAttribute("TURNO_TESTAMENTO");
         request.getSession().removeAttribute("TURNO_TESTAMENTO_OBJ");
    	 return null;
    }
  /**
	* Método que permite eliminar matrículas asociadas a la corrección.
	* @param request
	* @return
	*/
	private EvnCorreccion eliminarMatriculaSirMig(HttpServletRequest request) {

		preservarInfo(request);

		List matriculas = (List) request.getSession().getAttribute(
				WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_CORRECCION);

		if (matriculas == null) {
			matriculas = new ArrayList();
		}

		String[] matriculasElim = request
				.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR_SIR_MIG);
		if (matriculasElim != null) {
			for (int i = 0; i < matriculasElim.length; i++) {
				String actual = matriculasElim[i];
				matriculas.remove(actual);
			}
		}

		return null;
	}

	/**
	 * Método que permite asociar nuevas matrículas a la solicitud de
	 * corrección.
	 * 
	 * @param request
	 * @return
	 */
	private EvnCorreccion agregarMatriculaSirMig(HttpServletRequest request)
			throws ValidacionLiquidacionCorreccionException {

		preservarInfo(request);

		List matriculasSirMig = (List) request.getSession().getAttribute(
				WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_CORRECCION);
		if (matriculasSirMig == null) {
			matriculasSirMig = new ArrayList();
		}

		String matricula = request.getParameter(CFolio.ID_MATRICULA_SIR_MIG);
		if (matricula == null || matricula.trim().equals("")) {
			ValidacionLiquidacionCorreccionException e = new ValidacionLiquidacionCorreccionException();
			e.addError("Matrícula inválida");
			throw e;
		}
		if (matriculasSirMig.contains(matricula)) {
			ValidacionLiquidacionCorreccionException e = new ValidacionLiquidacionCorreccionException();
			e.addError("La matrícula que quiere ingresar, ya fue ingresada");
			throw e;
		}
		
		Folio folio = new Folio();
		folio.setIdMatricula(matricula);

		org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.
			  modelo.transferObjects.Usuario) request.getSession().getAttribute(
			  SMARTKeys.USUARIO_EN_SESION);
		 return new EvnCorreccion(usuario, folio, EvnCorreccion.VALIDAR_MATRICULA_MIG);
		 
		 
		//request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_CORRECCION,matriculasSirMig);
		//return null;

	}
  
  /**
	* Método que permite liquidar el valor por la solicitu de corrección.
	* Por defecto esta solicitud no tiene costo para el ciudadano por lo que
	* el valor de la liquidación es cero.
	* @param request
	* @return
	*/
  private EvnCorreccion liquidar(HttpServletRequest request) throws
		AccionWebException {

	 preservarInfo(request);

	 SolicitudCorreccion solicitudCorreccion = new SolicitudCorreccion();
	 String tipoId = request.getParameter(CCiudadano.TIPODOC);
	 String numId = request.getParameter(CCiudadano.IDCIUDADANO);
	 String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
	 String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
	 String nombres = request.getParameter(CCiudadano.NOMBRE);
	 String telefono = request.getParameter(CCiudadano.TELEFONO);

	 String turno_anterior = request.getParameter(CTurno.TURNO_ANTERIOR);
	 String descripcion = request.getParameter(CTurno.DESCRIPCION);
	 String comentario = request.getParameter(CSolicitud.COMENTARIO);
	 String direccion = request.getParameter(CTurno.DIRECCION);
	 String peticion = request.getParameter(CTurno.PETICION);
	 String interesJur = request.getParameter(INTERES_JURIDICO);
	 String recibidoSimilar = request.getParameter(RECIBIDO_SIMILAR_ANTES);

	 String numeroCopias=request.getParameter(NUMERO_COPIAS_CORRECCION);

	 ValidacionLiquidacionCorreccionException exception = new
		  ValidacionLiquidacionCorreccionException();

	 if ((peticion == null) || peticion.equals("")) {
		exception.addError("Derecho de petición inválido");
	 }
	 
	 //TFS 4394: El comentario de la solicitud de correccion
	 //no puede exceder de 255 caracteres
	 if(comentario!=null && comentario.length() > Integer.parseInt(CSolicitud.TAMANIO_COMENTARIO_CORRECCIONES)){
		 exception.addError(
		 	"El comentario de la solicitud no puede exceder los 255 caracteres");
	 }

	 if (tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
		exception.addError(
			 "Debe seleccionar un tipo de identificación para el ciudadano");
	 }
	 else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
		if (apellido1 == null || apellido1.trim().equals("")) {
		  exception.addError("Debe ingresar el primer apellido del Ciudadano");
		}
	 }
	 else if (tipoId.equals(COPLookupCodes.NIT)) {
		if (apellido1 == null || apellido1.trim().equals("")) {
		  exception.addError("Debe ingresar el primer apellido del Ciudadano");
		}
	 }
	 else {
		double valorId = 0.0d;
		if (numId == null || numId.trim().equals("")) {
		  exception.addError(
				"Debe ingresar el número de identificacion del Ciudadano");
		}
		else {
                     /* @author : CTORRES
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
                        if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
                                try {
                                        valorId = Double.parseDouble(numId);
                                        if (valorId <= 0) {
                                        exception.addError(
                                                        "El valor del documento no puede ser negativo o cero");
                                        }
                                }
                                catch (NumberFormatException e) {
                                        exception.addError("El número de identificación de la persona es inválido. No puede ser alfanumérico");
                                }
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

	 if (turno_anterior != null && !turno_anterior.trim().equals(""))
	 {
		 String [] turnoSplit = turno_anterior.split("-");
		 if (turnoSplit.length == 4)
		 {
			 String idProceso = turnoSplit[2];
			 if (!idProceso.equals(CProceso.PROCESO_CORRECCION))
			 {
				 exception.addError("El turno asociado debe ser de Correcciones");
			 }
		 } else {
			 exception.addError("Formato de turno asociado inválido");
		 }
	 }

	 // Bug 3799
         HttpSession session;
         session = request.getSession();


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


		  List matriculas = (List) request.getSession().getAttribute(WebKeys.
		  LISTA_MATRICULAS_SOLICITUD_CORRECCION);

	 if (!local_EnabledAntiguoSistemaRegionFlag) {
		if (matriculas == null || matriculas.size() <= 0) {
		  exception.addError("Ingrese por lo menos una matricula");
		}

	 } // :if
	 int copias = 1;
	if(numeroCopias==null || numeroCopias.trim().equals("")){
		exception.addError("debe ingresar el número de copias a imprimir");
	}else{
		try{
			copias = Integer.valueOf(numeroCopias).intValue();
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

	 if (matriculas != null && !matriculas.isEmpty()) {
		Iterator iter = matriculas.iterator();
		while (iter.hasNext()) {
		  String matricula = (String) iter.next();
		  SolicitudFolio solFolio = new SolicitudFolio();
		  Folio folio = new Folio();
		  folio.setIdMatricula(matricula);
		  solFolio.setFolio(folio);
		  solicitudCorreccion.addSolicitudFolio(solFolio);
		}
	 }

	 Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.
		  CIRCULO);

	 Ciudadano ciudadano = new Ciudadano();
	 ciudadano.setApellido1(apellido1);
	 ciudadano.setApellido2(apellido2);
	 if (telefono != null) {
		ciudadano.setTelefono(telefono);
	 }
	 ciudadano.setNombre(nombres);
	 ciudadano.setTipoDoc(tipoId);
	 ciudadano.setDocumento(numId);
	 ciudadano.setIdCiudadano(null);
	 ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

	 Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.
		  USUARIO);
	 org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.
		  core.modelo.transferObjects.Usuario) request.getSession().getAttribute(
		  SMARTKeys.USUARIO_EN_SESION);
	 //Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
	 Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.
		  PROCESO);
	 Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.
		  ESTACION);
	 Turno turnoanterior = null;

	 if ((turno_anterior != null) && !turno_anterior.equals("")) {
		turnoanterior = new Turno();
		turnoanterior.setIdWorkflow(turno_anterior);
	 }
         /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  nueva variable turnoTestamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
         Turno turnoTestamento = null;
         if(request.getSession().getAttribute("TURNO_TESTAMENTO_OBJ")!=null)
         {
             turnoTestamento = (Turno)request.getSession().getAttribute("TURNO_TESTAMENTO_OBJ");
         }
	 //Llenar la Solicitud de Corrección
	 solicitudCorreccion.setCirculo(circulo);
	 solicitudCorreccion.setCiudadano(ciudadano);
	 solicitudCorreccion.setProceso(proceso);
	 solicitudCorreccion.setTurno(null);
	 solicitudCorreccion.setTurnoAnterior(turnoanterior);
	 solicitudCorreccion.setUsuario(usuario);
	 solicitudCorreccion.setDescripcion(descripcion);
	 solicitudCorreccion.setComentario((comentario != null) ? comentario : "");
	 solicitudCorreccion.setDireccionEnvio((direccion != null) ? direccion : "");
	 solicitudCorreccion.setInteresJuridico((interesJur != null) ? interesJur :
														 "");

	 if ((peticion != null) && peticion.equals("SI")) {
		solicitudCorreccion.setDerechoPeticion(true);
	 }
	 else {
		solicitudCorreccion.setDerechoPeticion(false);
	 }

	 if ((recibidoSimilar != null) && recibidoSimilar.equals("SI")) {
		solicitudCorreccion.setSolicitadaAnteriormente(true);
	 }
	 else {
		solicitudCorreccion.setSolicitadaAnteriormente(false);
	 }

	 TipoRecepcionPeticion tipoRecPet = new TipoRecepcionPeticion();
	 if ((direccion != null) && !direccion.equals("")) {
		tipoRecPet.setIdTipoRecepcion(CTipoRecepcion.IDSOLICITUDCORREO);
		tipoRecPet.setNombre(CTipoRecepcion.POR_CORREO);
	 }
	 else {
		tipoRecPet.setIdTipoRecepcion(CTipoRecepcion.IDSOLICITUDPERSONAL);
		tipoRecPet.setNombre(CTipoRecepcion.PERSONALMENTE);
	 }


	 Load__Datos_Antiguo_Sistema: {

		// :: Bind Form2Data
		if( local_EnabledAntiguoSistemaRegionFlag ) {

		  // Antiguo Sistema
		  DatosAntiguoSistema datosAntiguoSistema
                    = getDatosAntiguoSistema(request, exception);


		  // Set DatosAntiguoSistema for solicitud
		  SolicitudCorreccion local_Solicitud;
		  local_Solicitud = solicitudCorreccion;

		  local_Solicitud.setDatosAntiguoSistema(datosAntiguoSistema);


		} // if

	 } // :Load__Datos_Antiguo_Sistema:


	 solicitudCorreccion.setTipoRecepcionPeticion(tipoRecPet);
	 
	 List turnosFolioMig = (List)session.getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_CORRECCION);

	 EvnCorreccion evn = new EvnCorreccion(usuarioAuriga,
            (Usuario) request.getSession().
            getAttribute(WebKeys.USUARIO),
            solicitudCorreccion, estacion,
            EvnCorreccion.CREAR_SOLICITUD);
	 evn.setUID(request.getSession().getId());
	 evn.setCirculo(circulo);
	 evn.setTurnosFolioMig(turnosFolioMig);
	 evn.setNumeroCopias(copias);
         /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se asigna el turno de testamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
         evn.setTurnoTestamento(turnoTestamento);
	 return evn;
  }


  /**
	* Guarda la información del formulario en la sesión del usuario para evitar que esta se pierda.
	* @param request
	*/
  private void preservarInfo(HttpServletRequest request) {

	 String tipoId = request.getParameter(CCiudadano.TIPODOC);
	 String numId = request.getParameter(CCiudadano.DOCUMENTO);
	 String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
	 String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
	 String nombres = request.getParameter(CCiudadano.NOMBRE);
	 String telefono = request.getParameter(CCiudadano.TELEFONO);

	 String turno_anterior = request.getParameter(CTurno.TURNO_ANTERIOR);
	 String descripcion = request.getParameter(CTurno.DESCRIPCION);
	 String comentario = request.getParameter(CSolicitud.COMENTARIO);
	 String direccion = request.getParameter(CTurno.DIRECCION);
	 String peticion = request.getParameter(CTurno.PETICION);

	 String interesJur = request.getParameter(INTERES_JURIDICO);
	 String recibidoSimilar = request.getParameter(RECIBIDO_SIMILAR_ANTES);


	 request.getSession().setAttribute(CTurno.DESCRIPCION, descripcion.trim());
	 request.getSession().setAttribute(CSolicitud.COMENTARIO, comentario.trim());
	 request.getSession().setAttribute(CTurno.PETICION, peticion);
	 request.getSession().setAttribute(CCiudadano.TIPODOC, tipoId);
	 request.getSession().setAttribute(CCiudadano.IDCIUDADANO, numId);
	 request.getSession().setAttribute(CCiudadano.APELLIDO1, apellido1);
	 request.getSession().setAttribute(CCiudadano.NOMBRE, nombres);
	 request.getSession().setAttribute(CCiudadano.APELLIDO2, apellido2);
	 request.getSession().setAttribute(CCiudadano.TELEFONO, telefono);
	 request.getSession().setAttribute(CTurno.TURNO_ANTERIOR, turno_anterior);
	 request.getSession().setAttribute(CTurno.DIRECCION, direccion);

	 request.getSession().setAttribute(INTERES_JURIDICO, interesJur);
	 request.getSession().setAttribute(RECIBIDO_SIMILAR_ANTES, recibidoSimilar);

	 saveState_DatosAntiguoSistema(request);

  }


  /*
	* @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	*/
  public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
	 EvnRespCorreccion respuesta = (EvnRespCorreccion) evento;

	 if (respuesta != null) {
		if (respuesta.getTipoEvento().equalsIgnoreCase(EvnRespCorreccion.
			 VALIDAR_MATRICULA)) {
		  String matricula = (String) respuesta.getMatricula();

		  if (matricula != null) {
			 List matriculas = (List) request.getSession().getAttribute(WebKeys.
				  LISTA_MATRICULAS_SOLICITUD_CORRECCION);
			 if (matriculas == null) {
				matriculas = new ArrayList();
			 }
			 matriculas.add(matricula);
			 request.getSession().setAttribute(WebKeys.
				  LISTA_MATRICULAS_SOLICITUD_CORRECCION, matriculas);
			 //String ultimo = request.getParameter("ultimo_campo_editado");
			 //request.getSession().setAttribute("ultimo_campo_editado", ultimo);
		  }
		  else
		  {
                        /**
                        * @autor Edgar Lora
                        * @mantis 11987
                        */
                        java.util.List listaRangoFolios = respuesta.getRangoFolio();
                        if (listaRangoFolios != null && listaRangoFolios.size() > 0) {
                            String matriculaInicial = (String) listaRangoFolios.get(0);
                            String matriculaFinal = (String) listaRangoFolios.get(1);
                            List matriculasasociadas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION);
                            if (matriculasasociadas == null) {
                                matriculasasociadas = new ArrayList();
                                SerialIds si = new SerialIds(matriculaInicial,matriculaFinal);
                                if(si.isSerialExists()){                                                                              
                                    while(si.hasNext()){
                                        String matriculaLeida=si.nextValue();
                                        matriculasasociadas.add(matriculaLeida);
                                    }
                                }                            
                            }
                        request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_SOLICITUD_CORRECCION, matriculasasociadas);
                        }
		  }
		}
		
		if (respuesta.getTipoEvento().equalsIgnoreCase(EvnRespCorreccion.
				 VALIDAR_MATRICULA_MIG)) {
			  String matricula = (String) respuesta.getMatricula();

			  if (matricula != null) {
				 List matriculasSirMig = (List) request.getSession().getAttribute(WebKeys.
						 LISTA_MATRICULAS_SIR_MIG_SOLICITUD_CORRECCION);
				 if (matriculasSirMig == null) {
					 matriculasSirMig = new ArrayList();
				 }
				 matriculasSirMig.add(matricula);
				 request.getSession().setAttribute(WebKeys.
						 LISTA_MATRICULAS_SIR_MIG_SOLICITUD_CORRECCION, matriculasSirMig);
				 //String ultimo = request.getParameter("ultimo_campo_editado");
				 //request.getSession().setAttribute("ultimo_campo_editado", ultimo);
			  }

			}
                /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  condicion para el tipo de evento VALIDAR_TURNO_TESTAMENTO_OK
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
                if (respuesta.getTipoEvento().equalsIgnoreCase(EvnRespCorreccion.VALIDAR_TURNO_TESTAMENTO_OK)) {
			  String idturno = (String) respuesta.getTurno().getIdWorkflow();
			  if (idturno != null) {
                              request.getSession().setAttribute("TURNO_TESTAMENTO", idturno);
                              request.getSession().setAttribute("TURNO_TESTAMENTO_OBJ",respuesta.getTurno());
                          }

			}
		if (respuesta.getTurno() != null) {
		  request.getSession().setAttribute(WebKeys.TURNO, respuesta.getTurno());
		}
	 }
                /* AHERRENO
                 28/05/2012
                 REQ 076_151 TRANSACCION*/
                Date fechaIni =  (Date) request.getSession().getAttribute("TIEMPO_INICIO_TRANSACCION");
                double tiempoSession =  (Double) request.getSession().getAttribute("TIEMPO_TRANSACCION");
                Date fechaFin =  new Date();
                TransaccionSIR transaccion = new TransaccionSIR();
                List <Transaccion> acciones = (List <Transaccion>) request.getSession().getAttribute("LISTA_TRANSACCION");
                long calTiempo = 0;
        try {
            calTiempo = transaccion.calculoTransaccion(fechaIni, fechaFin);
        } catch (Exception ex) {
            Logger.getLogger(AWLiquidacionCorreccion.class.getName()).log(Level.SEVERE, null, ex);
        }                
                DecimalFormat df = new DecimalFormat("0.000"); 
                double calculo = Double.valueOf(df.format(tiempoSession+((double)calTiempo/1000)).replace(',', '.'));
                System.out.println("El tiempo de la accion "+request.getParameter("ACCION")+" en milisegundos " +calTiempo );
                request.getSession().setAttribute("TIEMPO_TRANSACCION",calculo);
                Transaccion transaccionReg = new Transaccion();
                transaccionReg.setFechaTransaccion(fechaFin);
                transaccionReg.setAccionWeb(request.getParameter("ACCION"));
                transaccionReg.setTiempo(calTiempo);
                acciones.add(transaccionReg);
                request.getSession().setAttribute("LISTA_TRANSACCION",acciones);
                
                if(request.getParameter("ACCION").equals("LIQUIDAR")){
                EvnRespCorreccion turno = (EvnRespCorreccion) evento;
                    /*Se recorre la lista para almacenar la informacion del turno*/
                if(turno != null){        
                    for (Transaccion transacion: acciones) {
                            transacion.setAnio(turno.getTurno().getAnio());
                            transacion.setIdCirculo(turno.getTurno().getIdCirculo());
                            transacion.setIdProceso(turno.getTurno().getIdProceso());
                            transacion.setIdTurno(turno.getTurno().getIdTurno());                    
                        }
                    transaccion.guardarTransaccion(acciones);
                    acciones.clear();
                    request.getSession().setAttribute("LISTA_TRANSACCION",acciones);
                    request.getSession().setAttribute("TIEMPO_TRANSACCION",Double.valueOf(0));                    
                    }             
                }
  }

public List getTurnosFolioMig() {
	return turnosFolioMig;
}

public void setTurnosFolioMig(List turnosFolioMig) {
	this.turnosFolioMig = turnosFolioMig;
}
}

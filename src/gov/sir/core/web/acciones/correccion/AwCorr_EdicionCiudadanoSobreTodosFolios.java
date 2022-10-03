package gov.sir.core.web.acciones.correccion;

import gov.sir.core.eventos.correccion.EvnMsgEdicionCiudadanoSobreTodosFolios;
import gov.sir.core.eventos.correccion.EvnRespMsgEdicionCiudadanoSobreTodosFolios;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.CorreccionesEdicionCiudadanoSobreTodosFolios_PageValidatorExceptionCollector;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringMaxLengthValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringNotNullOrEmptyValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicStringMinLengthValidatorWrapper;
import gov.sir.core.web.helpers.correccion.validate.utils.validators.BasicConditionalValidator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 */
public
class AwCorr_EdicionCiudadanoSobreTodosFolios
extends SoporteAccionWeb {

  // PAGERENDERING-PROCESSES-IDS -----------------------------------------------------------------------

  public static final String EDITCIUDADANOONLOAD__PAGERENDERING_PROCESS_ACTION
      = "EDITCIUDADANOONLOAD__PAGERENDERING_PROCESS_ACTION";
  public static final String EDITCIUDADANOONCANCEL__PAGEPROCESSING_PROCESS_ACTION
      = "EDITCIUDADANOONCANCEL__PAGEPROCESSING_PROCESS_ACTION";
  public static final String EDITCIUDADANOONBACK__PAGEPROCESSING_PROCESS_ACTION
      = "EDITCIUDADANOONBACK__PAGEPROCESSING_PROCESS_ACTION";
  public static final String EDITCIUDADANOONAPPLY__PAGEPROCESSING_PROCESS_ACTION
      = "EDITCIUDADANOONAPPLY__PAGEPROCESSING_PROCESS_ACTION";
  // public static final String EDITCIUDADANOONCLOSE__PAGEPROCESSING_PROCESS_ACTION
  //    = "EDITCIUDADANOONCLOSE__PAGEPROCESSING_PROCESS_ACTION";
  public static final String EDITCIUDADANOONLOCKFOLIOS__PAGEPROCESSING_PROCESS_ACTION
      = "EDITCIUDADANOONLOCKFOLIOS__PAGEPROCESSING_PROCESS_ACTION";

  // ITEMS --------------------------------------------------------------------------------------------

  public static final String PAGEITEM__CIUDADANO_IDCIUDADANO
      = "PAGEITEM__CIUDADANO_IDCIUDADANO";
  public static final String PAGEITEM__CIUDADANO_DOCUMENTO
      = "PAGEITEM__CIUDADANO_DOCUMENTO";
  public static final String PAGEITEM__CIUDADANO_NOMBRE
      = "PAGEITEM__CIUDADANO_NOMBRE";
  public static final String PAGEITEM__CIUDADANO_TIPODOC
      = "PAGEITEM__CIUDADANO_TIPODOC";
  public static final String PAGEITEM__CIUDADANO_APELLIDO1
      = "PAGEITEM__CIUDADANO_APELLIDO1";
  public static final String PAGEITEM__CIUDADANO_APELLIDO2
      = "PAGEITEM__CIUDADANO_APELLIDO2";
  public static final String PAGEITEM__CIUDADANO_TELEFONO
      = "PAGEITEM__CIUDADANO_TELEFONO";
  //
  public static final String PAGEITEM__SALVEDAD_TX
      = "PAGEITEM__SALVEDAD_TX";
  //
  public static final String PAGEITEM__REGIONEDIT_VERIFICACIONBLOQUEO
      = "PAGEITEM__REGIONEDIT_VERIFICACIONBLOQUEO";
  public static final String PAGEITEM__REGIONEDIT_UPDATERESULTS
      = "PAGEITEM__REGIONEDIT_UPDATERESULTS";

  // query items
  public static final String PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_FOLIOS
      = "PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_FOLIOS";
  public static final String PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_USUARIOSBLOQUEO
      = "PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_USUARIOSBLOQUEO";


  // hidden session item
  public static final String PAGEITEM__REGIONEDIT_ENABLED
      = "PAGEITEM__REGIONEDIT_ENABLED";

  // --------------------------------------------------------------------------------------------------


	public static String DIARIO_RADICADOR_MATRICULAS =
		"DIARIO_RADICADOR_MATRICULAS";

	public static String REPORTE_25 = "REPORTE_25";
	public static String REPORTE_09 = "REPORTE_09";
	public static String REPORTE_08 = "REPORTE_08";
	public static String REPORTE_07 = "REPORTE_07";

	public static String TERMINA = "TERMINA";

	public static String REPORTSSERVICES_REPORTURI = "REPORTSSERVICES_REPORTURI";
	public static String REPORTSSERVICES_REPORTDISPLAYENABLED = "REPORTSSERVICES_REPORTDISPLAYENABLED";


	/** Constante que identifica el campo fecha inicio para la listar por fecha*/
	public static final String FECHA_INICIO = "FECHA_INICIO";

	/** Constante que identifica el campo fecha fin para la listar por fecha*/
	public static final String FECHA_FIN = "FECHA_FIN";
	public static final String USUARIO_REPORTE = "USUARIO_REPORTE";

	public static final String USUARIOS_CONSULTAR_POR_CIRCULO =
		"USUARIOS_CONSULTAR_POR_CIRCULO";

	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro.
	 */
public Evento
perform(HttpServletRequest request)
throws AccionWebException {

        String action = request.getParameter( WebKeys.ACCION );

        HttpSession session = request.getSession();

        if( ( null == action )
            ||( "".equalsIgnoreCase( action.trim() ) ) ) {

          throw new AccionInvalidaException( "No se ha indicado una accion valida" );

        }


        // PAGERENDERING-PROCESSES ---------------------------------------------
        if( EDITCIUDADANOONLOAD__PAGERENDERING_PROCESS_ACTION.equals( action ) ) {
          return do_PageRenderingProcess_EditCiudadanoOnLoad( request );
        }
        if( EDITCIUDADANOONLOCKFOLIOS__PAGEPROCESSING_PROCESS_ACTION.equals( action ) ) {
          return do_PageProcessingProcess_EditCiudadanoOnLockFolios( request );
        }
        if( EDITCIUDADANOONAPPLY__PAGEPROCESSING_PROCESS_ACTION.equals( action ) ) {
          return do_PageProcessingProcess_EditCiudadanoOnApply( request );
        }
        if( EDITCIUDADANOONCANCEL__PAGEPROCESSING_PROCESS_ACTION.equals( action ) ) {
          return do_PageProcessingProcess_EditCiudadanoOnCancel( request );
        }
        if( EDITCIUDADANOONBACK__PAGEPROCESSING_PROCESS_ACTION.equals( action ) ) {
          return do_PageProcessingProcess_EditCiudadanoOnBack( request );
        }


        // ---------------------------------------------------------------------
        return null;
}




// -------------------------------------------------------------------------------------------------------------
private EvnMsgEdicionCiudadanoSobreTodosFolios
do_PageRenderingProcess_EditCiudadanoOnLoad( HttpServletRequest request )
throws ValidacionParametrosException {

  HttpSession session = request.getSession();

  ValidacionParametrosException exception
   = new CorreccionesEdicionCiudadanoSobreTodosFolios_PageValidatorExceptionCollector();

  String param_PageItem_Ciudadano_Id
   = request.getParameter( PAGEITEM__CIUDADANO_IDCIUDADANO );

  // -----------------------------------------------------------------------------------------------
  EvnMsgEdicionCiudadanoSobreTodosFolios result = null;

  org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
  gov.sir.core.negocio.modelo.Usuario            param_Usuario_Turno;

  String param_EvnType;



  param_UsuarioAuriga
      = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute( SMARTKeys.USUARIO_EN_SESION );
  param_EvnType
      = EvnMsgEdicionCiudadanoSobreTodosFolios.EVNTYPE_EDITCIUDADANO_ONLOAD;
  param_Usuario_Turno
      = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute( WebKeys.USUARIO );




  // -----------------------------
  EvnMsgEdicionCiudadanoSobreTodosFolios resultWrap;
  resultWrap = new EvnMsgEdicionCiudadanoSobreTodosFolios(
      param_UsuarioAuriga
    , param_EvnType
  );

  resultWrap.setItemToFind_CiudadanoId( param_PageItem_Ciudadano_Id );
  resultWrap.setItemToFind_Usuario( param_Usuario_Turno );
  // -----------------------------
  result = resultWrap;
  // -----------------------------------------------------------------------------------------------

  return result;

} //:do_PageRenderingProcess_EditCiudadanoOnLoad
// -------------------------------------------------------------------------------------------------------------

private EvnMsgEdicionCiudadanoSobreTodosFolios
do_PageProcessingProcess_EditCiudadanoOnLockFolios( HttpServletRequest request )
throws ValidacionParametrosException {

  // el usuario debe tener los
  // folios bloqueados

  // se envia un mensaje sin folios (para que se bloqueen todos)
  // el parametro es el usuario que se intenta modificar

  HttpSession session = request.getSession();

  ValidacionParametrosException exception
   = new CorreccionesEdicionCiudadanoSobreTodosFolios_PageValidatorExceptionCollector();

  String param_PageItem_Ciudadano_Id
   = (String)session.getAttribute( PAGEITEM__CIUDADANO_IDCIUDADANO ); // =




  List param_FoliosToBlock;
  param_FoliosToBlock
      = (List)session.getAttribute( PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_FOLIOS );

  if( null == param_FoliosToBlock ){
    // no hay folios para bloquear
  }


  // -----------------------------------------------------------------------------------------------
  EvnMsgEdicionCiudadanoSobreTodosFolios result = null;

  org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
  gov.sir.core.negocio.modelo.Usuario param_FoliosToBlock_Usuario;
  gov.sir.core.negocio.modelo.Turno param_FoliosToBlock_Turno;

  String param_EvnType;


  param_UsuarioAuriga
      = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute( SMARTKeys.USUARIO_EN_SESION );
  param_EvnType
      = EvnMsgEdicionCiudadanoSobreTodosFolios.EVNTYPE_EDITCIUDADANO_ONLOCKFOLIOS;

  param_FoliosToBlock_Usuario
      = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute( WebKeys.USUARIO );
  param_FoliosToBlock_Turno
      = (gov.sir.core.negocio.modelo.Turno)session.getAttribute( WebKeys.TURNO );


  // -----------------------------
  EvnMsgEdicionCiudadanoSobreTodosFolios resultWrap;

  resultWrap = new EvnMsgEdicionCiudadanoSobreTodosFolios(
      param_UsuarioAuriga
    , param_EvnType
  );

  resultWrap.setFoliosToBlock_CiudadanoId( param_PageItem_Ciudadano_Id );
  resultWrap.setFoliosToBlock_Usuario( param_FoliosToBlock_Usuario );
  resultWrap.setFoliosToBlock_Turno( param_FoliosToBlock_Turno );

  // resultWrap.setFoliosToBlock_FoliosList( param_FoliosToBlock );

/*
  gov.sir.core.negocio.modelo.Usuario t0_User
                  = (gov.sir.core.negocio.modelo.Usuario )session.getAttribute( WebKeys.USUARIO );

*/

  // -----------------------------
  result = resultWrap;
  // -----------------------------------------------------------------------------------------------
  /**
    * @Author Carlos Torres
    * @Mantis 13176
    * @Chaged ;
    */
    gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
    gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(),String.valueOf(usuarioNeg.getIdUsuario()));
    infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
    result.setInfoUsuario(infoUsuario);
  return result;

} //:do_PageProcessingProcess_EditCiudadanoOnBlockFolios


// -------------------------------------------------------------------------------

private EvnMsgEdicionCiudadanoSobreTodosFolios
do_PageProcessingProcess_EditCiudadanoOnApply( HttpServletRequest request )
throws ValidacionParametrosException {

  HttpSession session = request.getSession();

  Turno turno = (Turno) session.getAttribute( WebKeys.TURNO );
  
  ValidacionParametrosException exception
   = new CorreccionesEdicionCiudadanoSobreTodosFolios_PageValidatorExceptionCollector();

  // revisar marca de habilitacion de bolqueos
  Boolean flag_HabilitacionBoqueos = (Boolean)session.getAttribute( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__REGIONEDIT_VERIFICACIONBLOQUEO );
  if( null == flag_HabilitacionBoqueos) {
    flag_HabilitacionBoqueos = Boolean.FALSE;
  }

  if( !flag_HabilitacionBoqueos.booleanValue() ) {
    exception.addError( "Debe verificar primero los bloqueos antes de editar los datos del ciudadano" );
  }
  // -----------------------------------------------------------------------------------------------

  // guardar el estado de los datos editados
  do_PageProcessingProcess_PageSaveState( request );


  String param_PageItem_Ciudadano_Id
   = (String)session.getAttribute( PAGEITEM__CIUDADANO_IDCIUDADANO ); //


  // aplicar validators para los datos de ciduadano
  gov.sir.core.negocio.modelo.Ciudadano ciudadano;
  String param_salvedadTx;

  ciudadano        = do_PageProcessingProcess_EditCiudadanoApplyValidations( request, exception );
  param_salvedadTx = do_PageProcessingProcess_EditCiudadanoSalvedadApplyValidations( request, exception );


  // raise application error --------------------------------
  if( exception.getErrores().size() > 0 ) {
    throw exception;
  }
  // --------------------------------------------------------


  List param_FoliosToBlock;
  param_FoliosToBlock
      = (List)session.getAttribute( PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_FOLIOS );

  if( null == param_FoliosToBlock ){
    // no hay folios para bloquear
  }


  // -----------------------------------------------------------------------------------------------
  EvnMsgEdicionCiudadanoSobreTodosFolios result = null;

  org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;

  gov.sir.core.negocio.modelo.Usuario param_CiudadanoToUpdate_Usuario;
  gov.sir.core.negocio.modelo.Turno param_FoliosToBlock_Turno;
  gov.sir.core.negocio.modelo.Ciudadano param_CiudadanoToUpdate_Ciudadano_t2;

  String param_EvnType;


  param_UsuarioAuriga
      = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute( SMARTKeys.USUARIO_EN_SESION );
  param_EvnType
      = EvnMsgEdicionCiudadanoSobreTodosFolios.EVNTYPE_EDITCIUDADANO_ONAPPLY;
  param_CiudadanoToUpdate_Usuario
      = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute( WebKeys.USUARIO );
  param_CiudadanoToUpdate_Ciudadano_t2
      = ciudadano;

  // -----------------------------
  EvnMsgEdicionCiudadanoSobreTodosFolios resultWrap;

  resultWrap = new EvnMsgEdicionCiudadanoSobreTodosFolios(
      param_UsuarioAuriga
    , param_EvnType
  );

  resultWrap.setCiudadanoToUpdate_Ciudadano_t2( param_CiudadanoToUpdate_Ciudadano_t2 );
  resultWrap.setCiudadanoToUpdate_Usuario( param_CiudadanoToUpdate_Usuario );
  resultWrap.setCiudadanoToUpdate_SalvedadTx( param_salvedadTx );
  resultWrap.setTurno(turno);

  // resultWrap.setFoliosToBlock_FoliosList( param_FoliosToBlock );

/*
  gov.sir.core.negocio.modelo.Usuario t0_User
                  = (gov.sir.core.negocio.modelo.Usuario )session.getAttribute( WebKeys.USUARIO );

*/

  // -----------------------------
  result = resultWrap;
  // -----------------------------------------------------------------------------------------------

  return result;

} //:do_PageProcessingProcess_EditCiudadanoOnBlockFolios




// -------------------------------------------------------------------------------

private EvnMsgEdicionCiudadanoSobreTodosFolios
do_PageProcessingProcess_EditCiudadanoOnBack( HttpServletRequest request )
throws ValidacionParametrosException {

  // back ahora es manejado por AwModificarFolio


  // al hacer cancel, se deben actualizar los datos del folio
  // si la edicion se alteró.

  HttpSession session = request.getSession();

  ValidacionParametrosException exception
   = new CorreccionesEdicionCiudadanoSobreTodosFolios_PageValidatorExceptionCollector();

  // revisar marca de habilitacion de bolqueos
  Boolean flag_HabilitacionRefresh = (Boolean)session.getAttribute( AwCorr_EdicionCiudadanoSobreTodosFolios.PAGEITEM__REGIONEDIT_VERIFICACIONBLOQUEO );
  if( null == flag_HabilitacionRefresh ) {
    flag_HabilitacionRefresh = Boolean.FALSE;
  }

  if( flag_HabilitacionRefresh.booleanValue() ) {
  }

  // -----------------------------------------------------------------------------------------------
  EvnMsgEdicionCiudadanoSobreTodosFolios result = null;

  org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
  String param_EvnType;


  param_UsuarioAuriga
      = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute( SMARTKeys.USUARIO_EN_SESION );
  param_EvnType
      = EvnMsgEdicionCiudadanoSobreTodosFolios.EVNTYPE_EDITCIUDADANO_ONAPPLY;

  // -----------------------------
  EvnMsgEdicionCiudadanoSobreTodosFolios resultWrap;

  resultWrap = new EvnMsgEdicionCiudadanoSobreTodosFolios(
      param_UsuarioAuriga
    , param_EvnType
  );

  // -----------------------------
  result = resultWrap;
  // -----------------------------------------------------------------------------------------------

  return result;

} //:do_PageProcessingProcess_EditCiudadanoOnCancel












// -------------------------------------------------------------------------------

private EvnMsgEdicionCiudadanoSobreTodosFolios
do_PageProcessingProcess_EditCiudadanoOnCancel( HttpServletRequest request )
throws ValidacionParametrosException {


  // al hacer cancel, se deben actualizar los datos del folio
  // si la edicion se alteró.

  HttpSession session = request.getSession();

  // ValidacionParametrosException exception
  //  = new CorreccionesEdicionCiudadanoSobreTodosFolios_PageValidatorExceptionCollector();

  do_PageProcessingProcess_PageClearState( request );

  return null;

} //:do_PageProcessingProcess_EditCiudadanoOnClose


private java.lang.String
do_PageProcessingProcess_EditCiudadanoSalvedadApplyValidations(
    HttpServletRequest request
  , ValidacionParametrosException exception
) {

  HttpSession session = request.getSession();


  String salvedad = request.getParameter( PAGEITEM__SALVEDAD_TX );


  BasicConditionalValidator stage1_validator;


  // 1: validar salvedad

  // 1:A not null
  stage1_validator = new BasicStringNotNullOrEmptyValidatorWrapper();
  stage1_validator.setObjectToValidate( salvedad );
  stage1_validator.validate();

  if( stage1_validator.getResult() != true ) {
      exception.addError( "salvedad; debe escribir un valor en la salvedad" );
  }

  // 1:B maxima longitud
  stage1_validator = new BasicStringMaxLengthValidatorWrapper( 1024 );
  stage1_validator.setObjectToValidate( salvedad );
  stage1_validator.validate();

  if( stage1_validator.getResult() != true ) {
      exception.addError( "salvedad; la salvedad debe tener maximo de 1024 caracteres" );
  }

  // 1:C minima longitud
  stage1_validator = new BasicStringMinLengthValidatorWrapper( 30 );
  stage1_validator.setObjectToValidate( salvedad );
  stage1_validator.validate();

  if( stage1_validator.getResult() != true ) {
      exception.addError( "salvedad; la salvedad debe tener mas de 30 caracteres" );
  }

  return salvedad;
} //:do_PageProcessingProcess_EditCiudadanoSalvedadApplyValidations




  /**
   * do_PageProcessingProcess_EditCiudadanoApplyValidations
   *
   * @param request HttpServletRequest
   * @param exception ValidacionParametrosException
   */
  private gov.sir.core.negocio.modelo.Ciudadano
  do_PageProcessingProcess_EditCiudadanoApplyValidations(
      HttpServletRequest request
    , ValidacionParametrosException exception
  ) {

    Ciudadano ciudadano = null;

    // referencia: AWLiquidacionCertificado.getSolicitudCertificadoValidada
    HttpSession session = request.getSession();

    String ciudadanoId = (String)session.getAttribute( PAGEITEM__CIUDADANO_IDCIUDADANO );//request.getParameter(  PAGEITEM__CIUDADANO_IDCIUDADANO );
    String numId       = request.getParameter(  PAGEITEM__CIUDADANO_DOCUMENTO   );
    String nombres     = request.getParameter(  PAGEITEM__CIUDADANO_NOMBRE      );
    String tipoId      = request.getParameter(  PAGEITEM__CIUDADANO_TIPODOC     );
    String apellido1   = request.getParameter(  PAGEITEM__CIUDADANO_APELLIDO1   );
    String apellido2   = request.getParameter(  PAGEITEM__CIUDADANO_APELLIDO2   );
    String telefono    = request.getParameter( PAGEITEM__CIUDADANO_TELEFONO     );

    if (ciudadanoId == null || ciudadanoId.trim().equals("")) {
            exception.addError("No existe un id de ciudadano para editar");
    }


    if (tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
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
                    exception.addError("Debe ingresar el número de identificación del Ciudadano");
            } else {
                /*
             * @author : CTORRES
             * @change : Se implemento la validación para solo permitir caracteres alfanuméricos en 
             *           numId cuando el tipo de identificación es PASAPORTE.
             * Caso Mantis : 11056
             * No Requerimiento: 022_453
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
                    exception.addError("Debe ingresar el primer apellido del Ciudadano");
            }
    }

    if (ciudadanoId != null) {
            if (ciudadano == null) {
                    ciudadano = new Ciudadano();
            }
            ciudadano.setIdCiudadano( ciudadanoId );
    }

    if (telefono != null) {
            if (ciudadano == null) {
                    ciudadano = new Ciudadano();
            }
            ciudadano.setTelefono(telefono);
    }

    if (tipoId != null) {
            if (ciudadano == null) {
                    ciudadano = new Ciudadano();
            }
            ciudadano.setTipoDoc(tipoId);
    }

    if (numId != null) {
            if (ciudadano == null) {
                    ciudadano = new Ciudadano();
            }
            ciudadano.setDocumento(numId);
    }

    if (nombres != null) {
            if (ciudadano == null) {
                    ciudadano = new Ciudadano();
            }
            ciudadano.setNombre(nombres);
    }

    if (apellido1 != null) {
            if (ciudadano == null) {
                    ciudadano = new Ciudadano();
            }
            ciudadano.setApellido1(apellido1);
    }

    if (apellido2 != null) {
            if (ciudadano == null) {
                    ciudadano = new Ciudadano();
            }
            ciudadano.setApellido2(apellido2);
    }
    
  	//Se setea el circulo del ciudadano
    Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
    ciudadano.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);
    
    return ciudadano;



  }

  /**
   * do_PageProcessingProcess_PageSaveState
   *
   * @param request HttpServletRequest
   */
  private void do_PageProcessingProcess_PageSaveState( HttpServletRequest request ) {

    HttpSession session;
    session = request.getSession();

    // save ciudadano state
    // do_PageProcessingProcess_PageSaveState_Object( request, session, PAGEITEM__CIUDADANO_IDCIUDADANO );
    do_PageProcessingProcess_PageSaveState_Object( request, session, PAGEITEM__CIUDADANO_DOCUMENTO   );
    do_PageProcessingProcess_PageSaveState_Object( request, session, PAGEITEM__CIUDADANO_NOMBRE      );
    do_PageProcessingProcess_PageSaveState_Object( request, session, PAGEITEM__CIUDADANO_TIPODOC     );
    do_PageProcessingProcess_PageSaveState_Object( request, session, PAGEITEM__CIUDADANO_APELLIDO1   );
    do_PageProcessingProcess_PageSaveState_Object( request, session, PAGEITEM__CIUDADANO_APELLIDO2   );
    do_PageProcessingProcess_PageSaveState_Object( request, session, PAGEITEM__CIUDADANO_TELEFONO    );

    // save salvedad anotacion tx
    do_PageProcessingProcess_PageSaveState_Object( request, session, PAGEITEM__SALVEDAD_TX           );



  }

  private void do_PageProcessingProcess_PageClearState( HttpServletRequest request ) {

    HttpSession session;
    session = request.getSession();

    // del+ ciudadano state
    // do_PageProcessingProcess_PageClearState_Object( request, session, PAGEITEM__CIUDADANO_IDCIUDADANO );
    do_PageProcessingProcess_PageClearState_Object( session, PAGEITEM__CIUDADANO_DOCUMENTO   );
    do_PageProcessingProcess_PageClearState_Object( session, PAGEITEM__CIUDADANO_NOMBRE      );
    do_PageProcessingProcess_PageClearState_Object( session, PAGEITEM__CIUDADANO_TIPODOC     );
    do_PageProcessingProcess_PageClearState_Object( session, PAGEITEM__CIUDADANO_APELLIDO1   );
    do_PageProcessingProcess_PageClearState_Object( session, PAGEITEM__CIUDADANO_APELLIDO2   );
    do_PageProcessingProcess_PageClearState_Object( session, PAGEITEM__CIUDADANO_TELEFONO    );

    // del salvedad anotacion tx
    do_PageProcessingProcess_PageClearState_Object( session, PAGEITEM__SALVEDAD_TX           );

  }


  // -------------------------------------------------------------------------------
  private void do_PageProcessingProcess_PageSaveState_Object( HttpServletRequest request, HttpSession session, String sessionKey ){
    Object sessionValue = request.getParameter( sessionKey );
    session.setAttribute( sessionKey, sessionValue);
  }

  private void do_PageProcessingProcess_PageClearState_Object( HttpSession session, String sessionKey ){
    session.removeAttribute( sessionKey );
  }



















      public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

        if( null == evento )
          return;

        EvnRespMsgEdicionCiudadanoSobreTodosFolios thisEventRespMsg
            = (EvnRespMsgEdicionCiudadanoSobreTodosFolios)evento;

        if( EvnRespMsgEdicionCiudadanoSobreTodosFolios.EVNRESPOK_EDITCIUDADANO_ONLOAD.equals( thisEventRespMsg.getTipoEvento() ) ) {
          do_PageRenderingProcess_EditCiudadanoOnLoad_DoEnd( request, thisEventRespMsg );
        }
        if( EvnRespMsgEdicionCiudadanoSobreTodosFolios.EVNRESPOK_EDITCIUDADANO_ONLOCKFOLIOS.equals( thisEventRespMsg.getTipoEvento() ) ) {
          do_PageRenderingProcess_EditCiudadanoOnLockFolios_DoEnd( request, thisEventRespMsg );
        }
        if( EvnRespMsgEdicionCiudadanoSobreTodosFolios.EVNRESPOK_EDITCIUDADANO_ONAPPLY.equals( thisEventRespMsg.getTipoEvento() ) ) {
          do_PageRenderingProcess_EditCiudadanoOnApply_DoEnd( request, thisEventRespMsg );
        }

      } // :doEnd


      public void
      do_PageRenderingProcess_LoadStateCiudadano( HttpSession session, gov.sir.core.negocio.modelo.Ciudadano ciudadano ) {
          session.setAttribute( PAGEITEM__CIUDADANO_IDCIUDADANO       , ciudadano.getIdCiudadano() );
          session.setAttribute( PAGEITEM__CIUDADANO_DOCUMENTO         , ciudadano.getDocumento()   );
          session.setAttribute( PAGEITEM__CIUDADANO_NOMBRE            , ciudadano.getNombre()      );
          session.setAttribute( PAGEITEM__CIUDADANO_TIPODOC           , ciudadano.getTipoDoc()     );
          session.setAttribute( PAGEITEM__CIUDADANO_APELLIDO1         , ciudadano.getApellido1()   );
          session.setAttribute( PAGEITEM__CIUDADANO_APELLIDO2         , ciudadano.getApellido2()   );
          session.setAttribute( PAGEITEM__CIUDADANO_TELEFONO          , ciudadano.getTelefono()    );

      }

// -------------------------------------------------------------------------------
      public void
      do_PageRenderingProcess_EditCiudadanoOnLoad_DoEnd( HttpServletRequest request, EvnRespMsgEdicionCiudadanoSobreTodosFolios evento ) {

        HttpSession session = request.getSession();

        // bind data2form1 ( action -> cache )


        // clear previous flag state // for visualiztion purposes
        session.removeAttribute( PAGEITEM__REGIONEDIT_ENABLED );
        session.removeAttribute( PAGEITEM__REGIONEDIT_VERIFICACIONBLOQUEO );


        gov.sir.core.negocio.modelo.Ciudadano ciudadano;

        if( null == evento )
          return;

        ciudadano = evento.getCiudadano();

        if( null == ciudadano )
          return;

        // clear or set default values for salvedad
        session.removeAttribute( PAGEITEM__SALVEDAD_TX );


        // -----------------------------------------------------------------------------------------------
        do_PageRenderingProcess_LoadStateCiudadano( session, ciudadano );
        // -----------------------------------------------------------------------------------------------

        // Crear la tabla para desplegar los datos en folios y bloqueos

        List representation2_FolioList          = evento.getListFolios();
        List representation2_UsuarioBloqueoList = evento.getListUsuarioBloqueo();

        session.setAttribute( PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_FOLIOS         , representation2_FolioList          );
        session.setAttribute( PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_USUARIOSBLOQUEO, representation2_UsuarioBloqueoList );

        // -----------------------------------------------------------------------------------------------

      }



      // -------------------------------------------------------------------------------
      public void
      do_PageRenderingProcess_EditCiudadanoOnLockFolios_DoEnd( HttpServletRequest request, EvnRespMsgEdicionCiudadanoSobreTodosFolios evento ) {

        HttpSession session = request.getSession();

        // bind data2form1 ( action -> cache )


        // clear previous flag state // for visualiztion purposes
        session.removeAttribute( PAGEITEM__REGIONEDIT_ENABLED );


        gov.sir.core.negocio.modelo.Ciudadano ciudadano;

        if( null == evento )
          return;

        // Crear la tabla para desplegar los datos en folios y bloqueos

        List representation2_FolioList          = evento.getListFolios();
        List representation2_UsuarioBloqueoList = evento.getListUsuarioBloqueo();

        session.setAttribute( PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_FOLIOS         , representation2_FolioList          );
        session.setAttribute( PAGEITEM__FOLIOSQUERELACIONANCIUDADANO_USUARIOSBLOQUEO, representation2_UsuarioBloqueoList );

        // colocar marca de habilitacion de bolqueos
        Boolean flag_HabilitacionBoqueos = new Boolean( evento.getVerificacionBloqueoFolios() );
        session.setAttribute( PAGEITEM__REGIONEDIT_VERIFICACIONBLOQUEO, flag_HabilitacionBoqueos );
        // -----------------------------------------------------------------------------------------------

      } // :do_PageRenderingProcess_EditCiudadanoOnLockFolios_DoEnd


      // -------------------------------------------------------------------------------
      public void
      do_PageRenderingProcess_EditCiudadanoOnApply_DoEnd( HttpServletRequest request, EvnRespMsgEdicionCiudadanoSobreTodosFolios evento ) {

        HttpSession session = request.getSession();

        // bind data2form1 ( action -> cache )


        gov.sir.core.negocio.modelo.Ciudadano ciudadano;

        if( null == evento )
          return;

        ciudadano = evento.getCiudadano();

        boolean updateResults;
        updateResults = evento.getRespuestaActualizacion();

        session.setAttribute( PAGEITEM__REGIONEDIT_UPDATERESULTS, new Boolean( updateResults ) );

        // if( null == ciudadano )
        //  return;

        // realizar actualizacion de los datos de ciudadano ?
        // -----------------------------------------------------------------------------------------------
        // do_PageRenderingProcess_LoadStateCiudadano( session, ciudadano );

        // // -----------------------------------------------------------------------------------------------

        // Crear la tabla para desplegar los datos en folios y bloqueos
        // -----------------------------------------------------------------------------------------------

      } // :do_PageRenderingProcess_EditCiudadanoOnApply_DoEnd


}

package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.web.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.web.acciones.correccion.AwCorr_CorrSimpleMain_CopiaSalvedadOptions;


public class ControlNavegacion_CorrSimpleMain_CopiaSalvedadOptions implements ControlNavegacion {

    // -------------------------------------------------------------------------------
    // :: [step0]
    // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENTRESP_OK";
   // -------------------------------------------------------------------------------
   // :: [step1]
   // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1CANCEL__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1CANCEL__EVENTRESP_OK";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT__EVENTRESP_OK";
   // -------------------------------------------------------------------------------
   // :: [step2]
   // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2CANCEL__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2CANCEL__EVENTRESP_OK";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2BACK__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2BACK__EVENTRESP_OK";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT__EVENTRESP_OK";

   // -------------------------------------------------------------------------------
   // :: [step3]
   // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3CANCEL__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3CANCEL__EVENTRESP_OK";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3BACK__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3BACK__EVENTRESP_OK";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENTRESP_OK";

   // -------------------------------------------------------------------------------
   // :: [step4]
   // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP4CONFIRM__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP4CONFIRM__EVENTRESP_OK";

   // -------------------------------------------------------------------------------
   // -------------------------------------------------------------------------------


  public ControlNavegacion_CorrSimpleMain_CopiaSalvedadOptions() {
    super();
  }



  public String
  procesarNavegacion( HttpServletRequest request )
  throws ControlNavegacionException {

    String accion = (String) request.getParameter( WebKeys.ACCION );
    HttpSession session = request.getSession();

    if ( ( null == accion ) || "".equals( accion ) )  {
            throw new ControlNavegacionException("La accion enviada por la accion web no es válida");
    }

    // step0 ----------------------
    if( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART_ACTION.equals( accion ) ) {
        return CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENTRESP_OK;
    }
    // ----------------------------
    // step1 ----------------------
    if( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1CANCEL_ACTION.equals( accion ) ) {
        return CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1CANCEL__EVENTRESP_OK;
    }
    if( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT_ACTION.equals( accion ) ) {
        return CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT__EVENTRESP_OK;
    }
    // ----------------------------
    // step2 ----------------------
    if( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2CANCEL_ACTION.equals( accion ) ) {
        return CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2CANCEL__EVENTRESP_OK;
    }
    if( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2BACK_ACTION.equals( accion ) ) {
        return CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2BACK__EVENTRESP_OK;
    }
    if( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT_ACTION.equals( accion ) ) {
        return CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT__EVENTRESP_OK;
    }
    // ----------------------------
    // step3 ----------------------
    if( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3CANCEL_ACTION.equals( accion ) ) {
        return CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3CANCEL__EVENTRESP_OK;
    }
    if( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3BACK_ACTION.equals( accion ) ) {
        return CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3BACK__EVENTRESP_OK;
    }
    if( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS_ACTION.equals( accion ) ) {
        return CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENTRESP_OK;
    }
    // ----------------------------
    // step4 ----------------------
    if( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP4CONFIRM_ACTION.equals( accion ) ) {
        return CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP4CONFIRM__EVENTRESP_OK;
    }

    // ----------------------------

    return null;

  } // end-method: procesarNavegacion

  public void doStart( HttpServletRequest request ) {

  } // end-method: doStart

  public void doEnd( HttpServletRequest arg0 ) {

  } // end-method: doEnd

} // end-class

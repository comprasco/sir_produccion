package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.web.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.web.acciones.correccion.AwCorr_CorrSimpleMain_VerAlertasOptions;


public class ControlNavegacion_CorrSimpleMain_VerAlertasOptions implements ControlNavegacion {

    // -------------------------------------------------------------------------------
    // :: [step0]
    // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENTRESP_OK";
   // -------------------------------------------------------------------------------
   // :: [step1]
   // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK__EVENTRESP_OK
       = "CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK__EVENTRESP_OK";
   // -------------------------------------------------------------------------------
   // -------------------------------------------------------------------------------
   // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK__EVENTRESP_OK1
   	   = "CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK__EVENTRESP_OK1";

  public ControlNavegacion_CorrSimpleMain_VerAlertasOptions() {
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
    if( AwCorr_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART_ACTION.equals( accion ) ) {
        return CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENTRESP_OK;
    }

    // ----------------------------
    // step1 ----------------------
    if( AwCorr_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK_ACTION.equals( accion ) ) {
    	if(session.getAttribute("REVISAR_APROBAR")!=null){
    		session.removeAttribute("REVISAR_APROBAR");
    		return CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK__EVENTRESP_OK1;
    	}else{
    		return CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK__EVENTRESP_OK;
    	}
    }
    // ----------------------------

    return null;

  } // end-method: procesarNavegacion

  public void doStart( HttpServletRequest request ) {

  } // end-method: doStart

  public void doEnd( HttpServletRequest arg0 ) {

  } // end-method: doEnd

} // end-class

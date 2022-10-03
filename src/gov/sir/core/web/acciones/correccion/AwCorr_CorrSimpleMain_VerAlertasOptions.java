package gov.sir.core.web.acciones.correccion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import gov.sir.core.web.WebKeys;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.web.acciones.SoporteAccionWeb;
import gov.sir.core.negocio.modelo.Circulo;
import org.auriga.smart.SMARTKeys;
import gov.sir.core.eventos.correccion.Evn_CorrSimpleMain_CopiaSalvedadOptions;
import gov.sir.core.eventos.correccion.EvnResp_CorrSimpleMain_VerAlertasOptions;
import java.util.List;
import gov.sir.core.negocio.modelo.Turno;
import java.util.Vector;
import gov.sir.core.web.acciones.excepciones.CorreccionesCorrSimpleMainVerAlertasOptionsGenericCollectorException;
import gov.sir.core.eventos.correccion.Evn_CorrSimpleMain_VerAlertasOptions;

public class AwCorr_CorrSimpleMain_VerAlertasOptions extends SoporteAccionWeb {

   // PAGE-ID: 001

   // -------------------------------------------------------------------------------
   // -------------------------------------------------------------------------------

   // REGION-01: COMPLEMENTACION
   // -------------------------------------------------------------------------------

   // REGION-02: LINDERO
   // -------------------------------------------------------------------------------

   // REGION-03: DIRECCION
   // -------------------------------------------------------------------------------

   // REGION-04: PAGE-GLOBAL-ACTIONS

   // -------------------------------------------------------------------------------
   // :: [step0]
   public static final String CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART_ACTION
       = "CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART_ACTION";
   // -------------------------------------------------------------------------------
   // :: [step1]
   // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK_ACTION
       = "CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK_ACTION";
   // -------------------------------------------------------------------------------
   // -------------------------------------------------------------------------------

   // -------------------------------------------------------------------------------
   // :: [step1]: items

   public static final String PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_IDVALUES
       = "PARAM:LOCAL:STEP1FOLIOSINTURNOSINCAMBIOS_IDVALUES";
   public static final String PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_DISPLAYVALUES
       = "PARAM:LOCAL:STEP1FOLIOSINTURNOSINCAMBIOS_DISPLAYVALUES";
   public static final String PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS
       = "PARAM:LOCAL:STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS";

   public static final String PARAM__ITEM_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS
       = "PARAM__ITEM_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS";



   public static final String PARAM__LOCAL_STEP1FOLIOSINTURNO_IDVALUES
       = "PARAM:LOCAL:STEP1FOLIOSINTURNO_IDVALUES";
   public static final String PARAM__LOCAL_STEP1FOLIOSINTURNO_DISPLAYVALUES
       = "PARAM:LOCAL:STEP1FOLIOSINTURNO_DISPLAYVALUES";
   public static final String PARAM__LOCAL_STEP1FOLIOSINTURNO_SELECTEDIDS
       = "PARAM:LOCAL:STEP1FOLIOSINTURNO_SELECTEDIDS";

   public static final String PARAM__ITEM_STEP1FOLIOSINTURNO_SELECTEDIDS
       = "PARAM__ITEM_STEP1FOLIOSINTURNO_SELECTEDIDS";



   // :: [step2]: items
   // -------------------------------------------------------------------------------
   // -------------------------------------------------------------------------------

   // [step1]: session keys

   public static final String PARAM__OBJS_STEP1FOLIOSINTURNO
   		= "PARAM:OBJS:STEP1FOLIOSINTURNO";
   public static final String PARAM__OBJS_STEP1FOLIOSINTURNOSINCAMBIOS
   		= "PARAM:OBJS:STEP1FOLIOSINTURNOSINCAMBIOS";

   // -------------------------------------------------------------------------------

    public Evento
    perform( HttpServletRequest request )
    throws AccionWebException {

      String accion = request.getParameter( WebKeys.ACCION );
      HttpSession session = request.getSession();

      if( null == accion  || "".equals( accion.trim() ) )
         throw new AccionWebException( "Accion invalida especificada" );

      // [btn-map-actions]


      // :: [step0]


      if( CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-veralertas
         return doProcess_CorreccionSimpleMain_VerAlertasOptions_Step0_BtnStart( request );
      }

      // :: [step1]

      if( CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP1_BTNBACK_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-copiarsalvedadfolio
         return doProcess_CorreccionSimpleMain_VerAlertasOptions_Step1_BtnBack( request );
      }

      return null;

  } // end-method: perform





// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------



// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------


private Evento
doProcess_CorreccionSimpleMain_VerAlertasOptions_Step0_BtnStart( HttpServletRequest request )
throws AccionWebException {

      HttpSession session = request.getSession();

      // the exception collector
      CorreccionesCorrSimpleMainVerAlertasOptionsGenericCollectorException exception;
      exception = new CorreccionesCorrSimpleMainVerAlertasOptionsGenericCollectorException();


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
      local_Result = new Evn_CorrSimpleMain_VerAlertasOptions(  Evn_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENT );

      // get-set injection
      local_Result.setUsuarioAuriga( param_UsuarioAuriga ); // o
      local_Result.setUsuarioSir( param_UsuarioSir );       // *
      local_Result.setTurno( param_Turno );                 // *
      // ---------------------------------------------------------------------------------

      // send-message -----------------------------------------------------------------
      return local_Result;
      // ---------------------------------------------------------------------------------

} // end-method: doProcess_CorreccionSimpleMain_VerAlertasOptions_Step0_BtnStart









   private void save_PageItemState_Simple( String itemId, HttpServletRequest request, HttpSession session ) {

     Object itemValue;
     itemValue = request.getParameter( itemId );
     session.setAttribute( itemId, itemValue );

   } // save_PageItemState


   private void delete_PageItemState_Simple( String itemId, HttpServletRequest request, HttpSession session ) {

     session.removeAttribute( itemId );

   } // save_PageItemState


   private void save_PageItemsState( String[] itemIds, HttpServletRequest request, HttpSession session ) {
     if( null == itemIds )
       return;

     for( int i=0; i < itemIds.length; i++  ) {

       if( null == itemIds[i] )
         continue;
       if( "".equals( itemIds[i] ) )
         continue;

       save_PageItemState_Simple( itemIds[i], request, session );
     } // :for
   } // :save_PageItemsState

   private void delete_PageItemsState( String[] itemIds, HttpServletRequest request, HttpSession session ) {
     if( null == itemIds )
       return;

     for( int i=0; i < itemIds.length; i++  ) {

       if( null == itemIds[i] )
         continue;
       if( "".equals( itemIds[i] ) )
         continue;

       delete_PageItemState_Simple( itemIds[i], request, session );
     } // :for
   } // :save_PageItemsState


   private void save_PageItemsState_TablaHelper( String requestKey, String sessionKey , HttpServletRequest request, HttpSession session ) {

      String[] local_Data;
      local_Data = request.getParameterValues( requestKey );

      if( null == local_Data )
        return;

      if( local_Data.length <= 0 )
        return;

      // put data into cache

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

      session.setAttribute( sessionKey, cache_Data );

   }


// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------


// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

private void
doProcess_CorreccionSimpleMain_VerAlertasOptions_Step1_RemoveState( HttpServletRequest request ) {

   HttpSession session;
   session = request.getSession();

   String[] itemIds;

   itemIds = new String[] {

        PARAM__OBJS_STEP1FOLIOSINTURNO
      , PARAM__OBJS_STEP1FOLIOSINTURNOSINCAMBIOS

      , PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_IDVALUES
      , PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_DISPLAYVALUES
      , PARAM__LOCAL_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS

      , PARAM__LOCAL_STEP1FOLIOSINTURNO_IDVALUES
      , PARAM__LOCAL_STEP1FOLIOSINTURNO_DISPLAYVALUES
      , PARAM__LOCAL_STEP1FOLIOSINTURNO_SELECTEDIDS


      // , PARAM__ITEM_STEP1FOLIOSINTURNOSINCAMBIOS_SELECTEDIDS
   };

   delete_PageItemsState( itemIds, request, session );

} // end-method: doProcess_CorreccionSimpleMain_VerAlertasOptions_Step1_RemoveState

// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

  private Evento
  doProcess_CorreccionSimpleMain_VerAlertasOptions_Step1_BtnBack( HttpServletRequest request )
  throws AccionWebException {


         // al cancelar solo se elimina valor y no se va a sesion

         HttpSession session = request.getSession();



         // session data -----------------------------------------------------------------
         doProcess_CorreccionSimpleMain_VerAlertasOptions_Step1_RemoveState( request );

         // build-message -----------------------------------------------------------------

         Evn_CorrSimpleMain_CopiaSalvedadOptions local_Result;

         local_Result = null;

         // send-message -----------------------------------------------------------------
         return local_Result;
         // ---------------------------------------------------------------------------------

  } // end-method: doProcess_CorreccionSimpleMain_VerAlertasOptions_Step1_BtnBack

// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------



    private void
    doEndProcess_CorreccionSimpleMain_VerAlertasOptions_Step0_BtnStart( HttpServletRequest request, EvnResp_CorrSimpleMain_VerAlertasOptions evento ) {

        HttpSession session;
        session = request.getSession();

        // session data -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------

        // List< Folio >: lista de folios en el turno
        List local_Result_FoliosInTurno;

        // List< Folio >: lista de folios en el turno, con salvedades
        List local_Result_FoliosInTurnoSinCambios;


        local_Result_FoliosInTurno           = evento.getFoliosInTurnoList();
        local_Result_FoliosInTurnoSinCambios = evento.getFoliosInTurnoSinCambiosList();

        session.setAttribute( PARAM__OBJS_STEP1FOLIOSINTURNO           , local_Result_FoliosInTurno           );
        session.setAttribute( PARAM__OBJS_STEP1FOLIOSINTURNOSINCAMBIOS , local_Result_FoliosInTurnoSinCambios );


        // request data -----------------------------------------------------------------
        // send-message -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------

    } // end-method: doEndProcess_CorreccionSimpleMain_VerAlertasOptions_BtnStart

    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------




	public void doEnd(HttpServletRequest request, EventoRespuesta e) {
            if( null == e  ) {
               return;
            }
            if( !(  e instanceof EvnResp_CorrSimpleMain_VerAlertasOptions ) ) {
               throw new RuntimeException( "Mensaje Inesperado" + AwCorr_CorrSimpleMain_VerAlertasOptions.class.getName() + ":" + e.getClass().getName()  );
            }

            EvnResp_CorrSimpleMain_VerAlertasOptions evento = (EvnResp_CorrSimpleMain_VerAlertasOptions)e;

            String local_TipoEvento = evento.getTipoEvento();

            // :: [step0]
            if( EvnResp_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENTRESP_OK.equals( local_TipoEvento )  ){
               doEndProcess_CorreccionSimpleMain_VerAlertasOptions_Step0_BtnStart( request, evento );
            }


	} // end method: doEnd

} // end-class

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
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.eventos.correccion.Evn_CorrSimpleMain_CopiaSalvedadOptions;
import java.util.List;
import gov.sir.core.negocio.modelo.Turno;
import java.util.Iterator;
import gov.sir.core.negocio.modelo.SalvedadFolio;
import java.util.Vector;
import gov.sir.core.web.acciones.excepciones.CorreccionesCorrSimpleMainCopiaSalvedadOptionsGenericCollectorException;
import gov.sir.core.eventos.correccion.EvnResp_CorrSimpleMain_CopiaSalvedadOptions;

public class AwCorr_CorrSimpleMain_CopiaSalvedadOptions extends SoporteAccionWeb {

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

 		// :: [opcion copiar salvedades de folio ]
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART_ACTION
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART_ACTION";
   // -------------------------------------------------------------------------------
   // :: [step1]
   // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1CANCEL_ACTION
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1CANCEL_ACTION";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT_ACTION
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT_ACTION";
   // public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1BACK_ACTION
   //    = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1BACK_ACTION";

   // -------------------------------------------------------------------------------
   // :: [step2]
   // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2CANCEL_ACTION
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2CANCEL_ACTION";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2BACK_ACTION
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1BACK_ACTION";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT_ACTION
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT_ACTION";

   // -------------------------------------------------------------------------------
   // :: [step3]
   // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3CANCEL_ACTION
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3CANCEL_ACTION";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3BACK_ACTION
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3BACK_ACTION";
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS_ACTION
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS_ACTION";
   // -------------------------------------------------------------------------------
   // :: [step4]
   // -------------------------------------------------------------------------------
   public static final String CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP4CONFIRM_ACTION
       = "CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP4CONFIRM_ACTION";
   // -------------------------------------------------------------------------------
   // -------------------------------------------------------------------------------



   // -------------------------------------------------------------------------------
   // :: [step1]: items
   public static final String PARAM__ITEM_STEP1FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS
       = "PARAM__ITEM_FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS";
   // :: [step2]: items
   public static final String PARAM__ITEM_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS
       = "PARAM__ITEM_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS";
   public static final String PARAM__ITEM_STEP2SALVEDADTXID
       = "PARAM__ITEM_STEP2SALVEDADTXID";
   // -------------------------------------------------------------------------------
   // -------------------------------------------------------------------------------


   // [step2]: session keys
   public static final String PARAM__OBJS_STEP2POSSIBLEFOLIOSTARGETLIST
       = "PARAM:OBJS:STEP2POSSIBLEFOLIOSTARGETLIST";
   public static final String PARAM__OBJS_STEP2FOLIOSOURCE
       = "PARAM:OBJS:STEP2FOLIOSOURCE";

   // [step3]: session keys
   public static final String PARAM__OBJS_STEP3TARGETFOLIOLIST
       = "PARAM:OBJS:STEP3TARGETFOLIOLIST";
   public static final String PARAM__OBJS_STEP3TARGETFOLIOLISTCOUNTSALVEDADES
       = "PARAM:OBJS:STEP3TARGETFOLIOLISTCOUNTSALVEDADES";
   public static final String PARAM__OBJS_STEP3SOURCEFOLIO
       = "PARAM:OBJS:STEP3SOURCEFOLIO";


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

      if( CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-copiarsalvedadfolio
         return doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStart( request );
      }

      // :: [step1]

      if( CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1CANCEL_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-copiarsalvedadfolio
         return doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep1Cancel( request );
      }
      if( CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-copiarsalvedadfolio
         return doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep1Next( request );
      }

      // :: [step2]
      if( CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2CANCEL_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-copiarsalvedadfolio
         return doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Cancel( request );
      }
      if( CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2BACK_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-copiarsalvedadfolio
         return doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Back( request );
      }
      if( CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-copiarsalvedadfolio
         return doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Next( request );
      }

      // :: [step3]
      if( CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3CANCEL_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-copiarsalvedadfolio
         return doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep3Cancel( request );
      }
      if( CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3BACK_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-copiarsalvedadfolio
         return doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep3Back( request );
      }
      if( CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-copiarsalvedadfolio
         return doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep3Process( request );
      }


      // :: [step4]
      if( CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP4CONFIRM_ACTION.equals( accion ) ) {
         // correccion.corrsimple-main.opc-copiarsalvedadfolio
         return doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep4Confirm( request );
      }

      return null;

  } // end-method: perform





// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------



// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------


private Evento
doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStart( HttpServletRequest request )
throws AccionWebException {

      HttpSession session = request.getSession();

      // the exception collector
      CorreccionesCorrSimpleMainCopiaSalvedadOptionsGenericCollectorException exception;
      exception = new CorreccionesCorrSimpleMainCopiaSalvedadOptionsGenericCollectorException();


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

      Evn_CorrSimpleMain_CopiaSalvedadOptions local_Result;
      local_Result = new Evn_CorrSimpleMain_CopiaSalvedadOptions(  Evn_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENT );

      // get-set injection
      local_Result.setUsuarioAuriga( param_UsuarioAuriga ); // o
      local_Result.setUsuarioSir( param_UsuarioSir );       // *
      local_Result.setTurno( param_Turno );                 // *
      // ---------------------------------------------------------------------------------

      // send-message -----------------------------------------------------------------
      return local_Result;
      // ---------------------------------------------------------------------------------

} // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStart









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
private void
doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step1_SaveState( HttpServletRequest request ) {

   HttpSession session;
   session = request.getSession();

   // para el caso del helper de mostrar tabla se debe hacer un trabajo adicional;
   final String PARAM__LOCAL_FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS   = "PARAM:LOCAL:FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS";


   String requestKey = PARAM__ITEM_STEP1FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS;
   String sessionKey = PARAM__LOCAL_FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS;

   save_PageItemsState_TablaHelper( requestKey, sessionKey, request, session );




} // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step1_SaveState


private void
doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step2_SaveState( HttpServletRequest request ) {

       HttpSession session;
       session = request.getSession();

       // para el caso del helper de mostrar tabla se debe hacer un trabajo adicional;
       final String PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS   = "PARAM:LOCAL:STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS";


       String requestKey = PARAM__ITEM_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS;
       String sessionKey = PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS;

       save_PageItemsState_TablaHelper( requestKey, sessionKey, request, session );

       String[] itemIds = new String[] {
           PARAM__ITEM_STEP2SALVEDADTXID
       };

       save_PageItemsState( itemIds, request, session );

} // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step1_SaveState
// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

private void
doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step1_RemoveState( HttpServletRequest request ) {

   HttpSession session;
   session = request.getSession();

   final String PARAM__LOCAL_FOLIOSINTURNOCONSALVEDADES_IDVALUES      = "PARAM:LOCAL:FOLIOSINTURNOCONSALVEDADES_IDVALUES";
   final String PARAM__LOCAL_FOLIOSINTURNOCONSALVEDADES_DISPLAYVALUES = "PARAM:LOCAL:FOLIOSINTURNOCONSALVEDADES_DISPLAYVALUES";
   final String PARAM__LOCAL_FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS   = "PARAM:LOCAL:FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS";

   String[] itemIds;

   itemIds = new String[] {
       PARAM__ITEM_STEP1FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS
       ,PARAM__LOCAL_FOLIOSINTURNOCONSALVEDADES_IDVALUES
       ,PARAM__LOCAL_FOLIOSINTURNOCONSALVEDADES_DISPLAYVALUES
       ,PARAM__LOCAL_FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS
   };
   delete_PageItemsState( itemIds, request, session );

   final String PARAM__OBJS_FOLIOSINTURNOCONSALVEDADES = "PARAM:OBJS:FOLIOSINTURNOCONSALVEDADES";

   itemIds = new String[] {
       PARAM__OBJS_FOLIOSINTURNOCONSALVEDADES
   };
   delete_PageItemsState( itemIds, request, session );


} // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step1_RemoveState


// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

private void
doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_StepX_RemoveState( HttpServletRequest request ) {

   doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step3_RemoveState( request );
   doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step2_RemoveState( request );
   doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step1_RemoveState( request );

} // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_StepX_RemoveState

// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

private void
doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step2_RemoveState( HttpServletRequest request ) {

   HttpSession session;
   session = request.getSession();

   String[] itemIds;

   final String PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_IDVALUES      = "PARAM:LOCAL:STEP2POSSIBLEFOLIOSTARGETLIST_IDVALUES";
   final String PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_DISPLAYVALUES = "PARAM:LOCAL:STEP2POSSIBLEFOLIOSTARGETLIST_DISPLAYVALUES";
   final String PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS   = "PARAM:LOCAL:STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS";

   itemIds = new String[] {
        PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_IDVALUES
      , PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_DISPLAYVALUES
      , PARAM__LOCAL_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS
      , PARAM__ITEM_STEP2SALVEDADTXID
   };

   delete_PageItemsState( itemIds, request, session );

   itemIds = new String[] {
        PARAM__OBJS_STEP2POSSIBLEFOLIOSTARGETLIST
      , PARAM__OBJS_STEP2FOLIOSOURCE
   };

   delete_PageItemsState( itemIds, request, session );

} // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step1_RemoveState

// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

private void
doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step3_RemoveState( HttpServletRequest request ) {

   HttpSession session;
   session = request.getSession();

   String[] itemIds;

   itemIds = new String[] {
        PARAM__OBJS_STEP3TARGETFOLIOLIST
      , PARAM__OBJS_STEP3TARGETFOLIOLISTCOUNTSALVEDADES
      , PARAM__OBJS_STEP3SOURCEFOLIO
   };

   delete_PageItemsState( itemIds, request, session );

} // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step3_RemoveState

// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------
   private Evento
   doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep1Next( HttpServletRequest request )
   throws AccionWebException {

         HttpSession session = request.getSession();

         // the exception collector
         CorreccionesCorrSimpleMainCopiaSalvedadOptionsGenericCollectorException exception;
         exception = new CorreccionesCorrSimpleMainCopiaSalvedadOptionsGenericCollectorException();



         // capture data -----------------------------------------------------------------

         String[] local_ParamFoliosSelected;
         local_ParamFoliosSelected = request.getParameterValues( PARAM__ITEM_STEP1FOLIOSINTURNOCONSALVEDADES_SELECTEDIDS );


         if( ( null == local_ParamFoliosSelected  )
             ||( local_ParamFoliosSelected.length == 0 )) {

            exception.addError( "No se ha seleccionado ningun folio origen." );

         }
         else if( local_ParamFoliosSelected.length != 1 ) {


            exception.addError( "Debe seleccionar un único folio origen." );

         }


         // ----------------------------------------------------------------------

         // save state -----------------------------------------------------------------
         doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step1_SaveState( request );
         // ----------------------------------------------------------------------

         // raise if errors -----------------------------------------------------------------
         if( exception.getErrores().size() > 0 )  {
            throw exception;
         }
         // ----------------------------------------------------------------------

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

         Evn_CorrSimpleMain_CopiaSalvedadOptions local_Result;
         local_Result = new Evn_CorrSimpleMain_CopiaSalvedadOptions(  Evn_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT__EVENT );

         // get-set injection
         local_Result.setUsuarioAuriga( param_UsuarioAuriga ); // o
         local_Result.setUsuarioSir( param_UsuarioSir );       // *
         local_Result.setTurno( param_Turno );                 // *
         local_Result.setSelectedSource_FolioIdMatricula( local_ParamFoliosSelected[0] );
         // ---------------------------------------------------------------------------------

         // send-message -----------------------------------------------------------------
         return local_Result;
         // ---------------------------------------------------------------------------------

   } // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep1Next

   // ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

   // ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------


  private Evento
  doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep3Process( HttpServletRequest request )
  throws AccionWebException {

          HttpSession session = request.getSession();

          // the exception collector
          CorreccionesCorrSimpleMainCopiaSalvedadOptionsGenericCollectorException exception;
          exception = new CorreccionesCorrSimpleMainCopiaSalvedadOptionsGenericCollectorException();


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

          Evn_CorrSimpleMain_CopiaSalvedadOptions local_Result;
          local_Result = new Evn_CorrSimpleMain_CopiaSalvedadOptions(  Evn_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENT );

          // get-set injection
          local_Result.setUsuarioAuriga( param_UsuarioAuriga ); // o
          local_Result.setUsuarioSir( param_UsuarioSir );       // *
          local_Result.setTurno( param_Turno );                 // *

          // List< Folio>
          List local_SelectedTarget_FolioList;
          // Folio
          Folio local_SelectedSource_FolioItem;
          // String
          String local_SelectedsalvedadTx;

          local_SelectedTarget_FolioList = (List)   session.getAttribute( PARAM__OBJS_STEP3TARGETFOLIOLIST );
          local_SelectedSource_FolioItem = (Folio)  session.getAttribute( PARAM__OBJS_STEP3SOURCEFOLIO );
          local_SelectedsalvedadTx       = (String) session.getAttribute( PARAM__ITEM_STEP2SALVEDADTXID );


          local_Result.setSelectedTarget_FolioList( local_SelectedTarget_FolioList );
          local_Result.setSelectedSource_FolioItem( local_SelectedSource_FolioItem );
          local_Result.setSelectedSalvedadTx( local_SelectedsalvedadTx );


          // ---------------------------------------------------------------------------------

          // send-message -----------------------------------------------------------------
          return local_Result;
          // ---------------------------------------------------------------------------------

 } // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep3Process
// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

  private Evento
  doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Next( HttpServletRequest request )
  throws AccionWebException {

   HttpSession session = request.getSession();

   // the exception collector
   CorreccionesCorrSimpleMainCopiaSalvedadOptionsGenericCollectorException exception;
   exception = new CorreccionesCorrSimpleMainCopiaSalvedadOptionsGenericCollectorException();



   // capture data -----------------------------------------------------------------

   String[] local_ParamFoliosSelected;
   local_ParamFoliosSelected = request.getParameterValues( PARAM__ITEM_STEP2POSSIBLEFOLIOSTARGETLIST_SELECTEDIDS );

   String param_SalvedadTx;
   param_SalvedadTx = request.getParameter( PARAM__ITEM_STEP2SALVEDADTXID );

   if( ( null == local_ParamFoliosSelected  )
       ||( local_ParamFoliosSelected.length == 0 )) {

      exception.addError( "No se ha seleccionado ningun folio destino." );

   }

   if( ( null == param_SalvedadTx  )
       ||( "".equals( param_SalvedadTx.trim() ) )) {

      exception.addError( "Texto de salvedad no puede estar vacio." );

   }


   // ----------------------------------------------------------------------

   // save state -----------------------------------------------------------------
   doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step2_SaveState( request );
   // ----------------------------------------------------------------------

   // raise if errors -----------------------------------------------------------------
   if( exception.getErrores().size() > 0 )  {
      throw exception;
   }
   // ----------------------------------------------------------------------

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

   Evn_CorrSimpleMain_CopiaSalvedadOptions local_Result;
   local_Result = new Evn_CorrSimpleMain_CopiaSalvedadOptions(  Evn_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT__EVENT );


   // obtener folio seleccionado step1
   Folio param_Step2_FolioSource;
   param_Step2_FolioSource =(Folio)session.getAttribute( PARAM__OBJS_STEP2FOLIOSOURCE );


   // get-set injection
   local_Result.setUsuarioAuriga( param_UsuarioAuriga ); // o
   local_Result.setUsuarioSir( param_UsuarioSir );       // *
   local_Result.setTurno( param_Turno );                 // *
   local_Result.setSelectedTarget_FolioIdMatriculaArray( local_ParamFoliosSelected );
   local_Result.setSalvedadTx( param_SalvedadTx ) ;
   local_Result.setSelectedSource_FolioIdMatricula( param_Step2_FolioSource.getIdMatricula() );


   // ---------------------------------------------------------------------------------

   // send-message -----------------------------------------------------------------
   return local_Result;
         // ---------------------------------------------------------------------------------

 } // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Next
// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------



  private Evento
  doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep1Cancel( HttpServletRequest request )
  throws AccionWebException {


     // al cancelar solo se elimina valor y no se va a sesion

           HttpSession session = request.getSession();

           doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_StepX_RemoveState( request );
     /*
           // the exception collector
           CorreccionesCorrSimpleMainCopiaSalvedadOptionsGenericCollectorException exception;
           exception = new CorreccionesCorrSimpleMainCopiaSalvedadOptionsGenericCollectorException();


           // session data -----------------------------------------------------------------

           org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
           param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute( SMARTKeys.USUARIO_EN_SESION );

           gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
           param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario)session.getAttribute( WebKeys.USUARIO );

           Circulo param_Circulo;
           param_Circulo = (Circulo) session.getAttribute( WebKeys.CIRCULO );

           Turno param_Turno;
           param_Turno = (Turno)session.getAttribute( WebKeys.TURNO );
     */
           // build-message -----------------------------------------------------------------

           Evn_CorrSimpleMain_CopiaSalvedadOptions local_Result;

           local_Result = null;

  /*
           local_Result = new Evn_CorrSimpleMain_CopiaSalvedadOptions(  Evn_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENT );

           // get-set injection
           local_Result.setUsuarioAuriga( param_UsuarioAuriga ); // o
           local_Result.setUsuarioSir( param_UsuarioSir );       // *
           local_Result.setTurno( param_Turno );                 // *
           // ---------------------------------------------------------------------------------
  */
           // send-message -----------------------------------------------------------------
           return local_Result;
           // ---------------------------------------------------------------------------------

  } // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep1Cancel

// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------


  private Evento
  doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Cancel( HttpServletRequest request )
  throws AccionWebException {


         // al cancelar solo se elimina valor y no se va a sesion

         HttpSession session = request.getSession();



         // session data -----------------------------------------------------------------
         doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_StepX_RemoveState( request );

         // build-message -----------------------------------------------------------------

         Evn_CorrSimpleMain_CopiaSalvedadOptions local_Result;

         local_Result = null;

         // send-message -----------------------------------------------------------------
         return local_Result;
         // ---------------------------------------------------------------------------------

  } // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Cancel

// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------


  private Evento
  doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep3Cancel( HttpServletRequest request )
  throws AccionWebException {


         // al cancelar solo se elimina valor y no se va a sesion

         HttpSession session = request.getSession();



         // session data -----------------------------------------------------------------
        doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_StepX_RemoveState( request );

         // build-message -----------------------------------------------------------------

         Evn_CorrSimpleMain_CopiaSalvedadOptions local_Result;

         local_Result = null;

         // send-message -----------------------------------------------------------------
         return local_Result;
         // ---------------------------------------------------------------------------------

  } // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep3Cancel

// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

  private Evento
  doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep3Back( HttpServletRequest request )
  throws AccionWebException {


         // al cancelar solo se elimina valor y no se va a sesion

         HttpSession session = request.getSession();



         // session data -----------------------------------------------------------------
         doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step3_RemoveState( request );

         // build-message -----------------------------------------------------------------

         Evn_CorrSimpleMain_CopiaSalvedadOptions local_Result;

         local_Result = null;

         // send-message -----------------------------------------------------------------
         return local_Result;
         // ---------------------------------------------------------------------------------

  } // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep3Back

// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------

  private Evento
  doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Back( HttpServletRequest request )
  throws AccionWebException {


         // al cancelar solo se elimina valor y no se va a sesion

         HttpSession session = request.getSession();



         // session data -----------------------------------------------------------------
         doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_Step2_RemoveState( request );


         // build-message -----------------------------------------------------------------

         Evn_CorrSimpleMain_CopiaSalvedadOptions local_Result;

         local_Result = null;

         // send-message -----------------------------------------------------------------
         return local_Result;
         // ---------------------------------------------------------------------------------

  } // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Back


// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------



  private Evento
  doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep4Confirm( HttpServletRequest request )
  throws AccionWebException {


         // al cancelar solo se elimina valor y no se va a sesion

         HttpSession session = request.getSession();



         // session data -----------------------------------------------------------------


         // build-message -----------------------------------------------------------------

         Evn_CorrSimpleMain_CopiaSalvedadOptions local_Result;

         local_Result = null;

         // send-message -----------------------------------------------------------------
         return local_Result;
         // ---------------------------------------------------------------------------------

  } // end-method: doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep4Confirm


// ---------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------



    private void
    doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStart( HttpServletRequest request, EvnResp_CorrSimpleMain_CopiaSalvedadOptions evento ) {

        HttpSession session;
        session = request.getSession();

        // session data -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------

        // List< Folio >: lista de folios en el turno
        List local_Result_FoliosInTurno;

        // List< Folio >: lista de folios en el turno, con salvedades
        List local_Result_FoliosInTurnoConSalvedades;


        local_Result_FoliosInTurno = evento.getFoliosInTurnoList();
        local_Result_FoliosInTurnoConSalvedades = evento.getFoliosInTurnoConSalvedadesList();

        final String PARAM__OBJS_FOLIOSINTURNOCONSALVEDADES = "PARAM:OBJS:FOLIOSINTURNOCONSALVEDADES";

        session.setAttribute( PARAM__OBJS_FOLIOSINTURNOCONSALVEDADES , local_Result_FoliosInTurnoConSalvedades );


        // request data -----------------------------------------------------------------
        // send-message -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------

    } // end-method: doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStart

    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    private void
    doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep1Next( HttpServletRequest request, EvnResp_CorrSimpleMain_CopiaSalvedadOptions evento ) {

        HttpSession session;
        session = request.getSession();

        // session data -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------

        // List< Folio >: lista de folios en turno distintos al seleccionado
        List local_Result_PossibleFoliosTargetList;

        // Folio: folio seleccionado con salvedad.
        Folio local_Result_FolioSource;


        local_Result_PossibleFoliosTargetList = evento.getPossibleFoliosTargetList();
        local_Result_FolioSource   = evento.getFolioSource();


        session.setAttribute( PARAM__OBJS_STEP2POSSIBLEFOLIOSTARGETLIST , local_Result_PossibleFoliosTargetList );
        session.setAttribute( PARAM__OBJS_STEP2FOLIOSOURCE , local_Result_FolioSource );


        // load salvedad tx

        block_LoadSalvedadTx: {

           List local_FolioSalvedades; // List< SalvedadFolio >
           local_FolioSalvedades = local_Result_FolioSource.getSalvedades();

           // obtener la ultima Salvedad, fijar los atributos respectivos
           Iterator local_FolioSalvedadesIterator;
           local_FolioSalvedadesIterator = local_FolioSalvedades.iterator();
           SalvedadFolio local_FolioSalvedadesElement = null;

           for( ;local_FolioSalvedadesIterator.hasNext(); ){
             local_FolioSalvedadesElement = (SalvedadFolio)local_FolioSalvedadesIterator.next();
           } // for

           String local_SalvedadTx = null;
           String local_SalvedadId = null;

           if( null != local_FolioSalvedadesElement ) {
             local_SalvedadTx = local_FolioSalvedadesElement.getDescripcion();
             local_SalvedadId = local_FolioSalvedadesElement.getIdSalvedad();

             session.setAttribute( AwCorr_CorrSimpleMain_CopiaSalvedadOptions.PARAM__ITEM_STEP2SALVEDADTXID, local_SalvedadTx  );
             // session.setAttribute( ITEMID_FOLIOEDICION_SALVEDADFOLIOID         ,   );

          } // if

        } // :block_LoadSalvedadTx



        // request data -----------------------------------------------------------------
        // send-message -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------

    } // end-method: doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep1Next

    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    private void
    doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Next( HttpServletRequest request, EvnResp_CorrSimpleMain_CopiaSalvedadOptions evento ) {

        HttpSession session;
        session = request.getSession();

        // session data -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------

        // List< Folio >: lista de folios destino
        List local_Result_TargetFolioList;
        // List< Folio >: suma de salvedades para folio destino
        List local_Result_TargetFolioListCountSalvedades;
        // // Folio: folio seleccionado con salvedad.
        Folio local_Result_SourceFolio;

        local_Result_TargetFolioList                = evento.getTargetFolioList();
        local_Result_TargetFolioListCountSalvedades = evento.getTargetFolioListCountSalvedades();
        local_Result_SourceFolio                    = evento.getFolioSource();

        session.setAttribute( PARAM__OBJS_STEP3TARGETFOLIOLIST                , local_Result_TargetFolioList                );
        session.setAttribute( PARAM__OBJS_STEP3TARGETFOLIOLISTCOUNTSALVEDADES , local_Result_TargetFolioListCountSalvedades );
        session.setAttribute( PARAM__OBJS_STEP3SOURCEFOLIO                    , local_Result_SourceFolio );

        // request data -----------------------------------------------------------------
        // send-message -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------

    } // end-method: doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Next

    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    private void
    doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep3Process( HttpServletRequest request, EvnResp_CorrSimpleMain_CopiaSalvedadOptions evento ) {

        HttpSession session;
        session = request.getSession();

        // session data -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------
        String tipoEvento;
        tipoEvento = evento.getTipoEvento();
        if( EvnResp_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENTRESP_OK.equals( tipoEvento ) ) {
          doProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_StepX_RemoveState( request );
        };



        // request data -----------------------------------------------------------------
        // send-message -----------------------------------------------------------------
        // ---------------------------------------------------------------------------------

    } // end-method: doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Next



	public void doEnd(HttpServletRequest request, EventoRespuesta e) {
            if( null == e  ) {
               return;
            }
            if( !(  e instanceof EvnResp_CorrSimpleMain_CopiaSalvedadOptions  ) ) {
               throw new RuntimeException( "Mensaje Inesperado" + AwCorr_CorrSimpleMain_CopiaSalvedadOptions.class.getName() + ":" + e.getClass().getName()  );
            }

            EvnResp_CorrSimpleMain_CopiaSalvedadOptions evento = (EvnResp_CorrSimpleMain_CopiaSalvedadOptions)e;

            String local_TipoEvento = evento.getTipoEvento();
            // :: [step0]
            if( EvnResp_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENTRESP_OK.equals( local_TipoEvento )  ){
               doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStart( request, evento );
            }
            // :: [step1]
            if( EvnResp_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT__EVENTRESP_OK.equals( local_TipoEvento )  ){
               doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep1Next( request, evento );
            }
            // :: [step2]
            if( EvnResp_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT__EVENTRESP_OK.equals( local_TipoEvento )  ){
               doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep2Next( request, evento );
            }
            // :: [step3]
            if( EvnResp_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENTRESP_OK.equals( local_TipoEvento )  ){
               doEndProcess_CorreccionSimpleMain_OpcionCopiarSalvedadTmpFolio_BtnStep3Process( request, evento );
            }

	} // end method: doEnd

} // end-class

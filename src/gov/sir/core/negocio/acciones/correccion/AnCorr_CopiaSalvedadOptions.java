package gov.sir.core.negocio.acciones.correccion;


import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;

import gov.sir.hermod.interfaz.HermodServiceInterface;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import gov.sir.core.eventos.correccion.Evn_CorrSimpleMain_CopiaSalvedadOptions;
import gov.sir.core.eventos.correccion.EvnResp_CorrSimpleMain_CopiaSalvedadOptions;
import gov.sir.core.negocio.acciones.excepciones.CopiaSalvedadException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;

import java.util.ArrayList;
import java.util.Iterator;
import gov.sir.core.negocio.modelo.Folio;
import java.util.List;

import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import org.auriga.util.ExceptionPrinter;
import gov.sir.core.negocio.modelo.SalvedadFolio;
import gov.sir.core.negocio.modelo.util.Log;

import org.apache.commons.jxpath.JXPathContext;

public class
AnCorr_CopiaSalvedadOptions
extends SoporteAccionNegocio {

	/**
	 * ServicesLayer: forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * ServicesLayer: hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 * ServiceLocatorInstance
	 */
	private ServiceLocator service = null;

	/**
	 *Constructor de la clase ANCorrección.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public AnCorr_CopiaSalvedadOptions() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
                buildServices( service );


	}



   public void
   buildServices( ServiceLocator locator )
   throws EventoException {

      try {
              hermod =
                      (HermodServiceInterface) service.getServicio("gov.sir.hermod");

              forseti =
                      (ForsetiServiceInterface) service.getServicio(
                              "gov.sir.forseti");

      }
      catch (ServiceLocatorException e) {
              throw new ServicioNoEncontradoException(
                      "Error instanciando el servicio Hermod",
                      e);
      }

      if( null == hermod ) {
              throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
      }

      if( null == forseti ) {
              throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
      }
   } // :buildServices



    public EventoRespuesta
    perform( Evento e )
    throws EventoException {

          Evn_CorrSimpleMain_CopiaSalvedadOptions evento = (Evn_CorrSimpleMain_CopiaSalvedadOptions) e;


          if( ( null == evento )
              || ( null == evento.getTipoEvento() ) ) {

            return null;

          }


          String local_TipoEvento;
          local_TipoEvento = evento.getTipoEvento();

          if( Evn_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENT.equals( local_TipoEvento ) ) {
              return processCopiaSalvedadTmpFolio_Iniciar( evento );
          }
          if( Evn_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT__EVENT.equals( local_TipoEvento ) ) {
              return processCopiaSalvedadTmpFolio_ObtenerFoliosTurno_ObtenerSalvedadFolioSeleccionado( evento );
          }
          if( Evn_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT__EVENT.equals( local_TipoEvento ) ) {
              return processCopiaSalvedadTmpFolio_PreviewAccionesARealizar( evento );
          }
          if( Evn_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENT.equals( local_TipoEvento ) ) {
              return processCopiaSalvedadTmpFolio_RealizarCopiaSalvedad( evento );
          }


          return null;

    } // :perform


	private EventoRespuesta
	processCopiaSalvedadTmpFolio_Iniciar( Evn_CorrSimpleMain_CopiaSalvedadOptions evento )
	throws EventoException {


          // unwrap ---------------------------------
          gov.sir.core.negocio.modelo.Usuario local_ParamUsuarioSir;
          gov.sir.core.negocio.modelo.Turno   local_ParamTurno;

          // get params
          local_ParamUsuarioSir = evento.getUsuarioSir();
          local_ParamTurno      = evento.getTurno();
          // -----------------------------------------

          EvnResp_CorrSimpleMain_CopiaSalvedadOptions local_Result = null;

          // process ---------------------------------
          FolioPk local_FolioID;

          String local_Folio_IdMatricula = null;

          // tmpvalriables to getvalues

          // List< Folio >: lista de folios en el turno
          List local_Result_FoliosInTurno;
          local_Result_FoliosInTurno = new ArrayList();

          // List< Folio >: lista de folios en el turno, con salvedades
          List local_Result_FoliosInTurnoConSalvedades;
          local_Result_FoliosInTurnoConSalvedades = new ArrayList();

          try {


             //
             // TODO: calls
             //
             // 1. obtener folios con deltas
             // 2. para cada uno de los folios observar si tiene salvedades temporales
             // 3. devolver el listado de folios para el turno
             // 4. devlover el listado de folios con anotaciones temporales

             // CALL: List< SolicitudFolio > lst = forseti.getFoliosInTurno
             // for( SolicitudFolio elem: lst )
             //  add List< folio >: result_List1
             //  observar si tiene salvedades; add to result_List2: List< folio > with salvedadtx

             //

             Turno local_Turno;
             Folio local_Folio;
             FolioPk local_FolioId;

             // list1







             local_Turno = hermod.getTurnobyWF( local_ParamTurno.getIdWorkflow() );

             // List< SolicitudFolio >
             List local_TurnoSolicitudSolicitudFolios;
             Iterator local_TurnoSolicitudSolicitudFoliosIterator;
             SolicitudFolio local_TurnoSolicitudSolicitudFoliosElement;

             local_TurnoSolicitudSolicitudFolios = local_Turno.getSolicitud().getSolicitudFolios();
             local_TurnoSolicitudSolicitudFoliosIterator = local_TurnoSolicitudSolicitudFolios.iterator();

             List local_FilteredSalvedades;

             for( ;local_TurnoSolicitudSolicitudFoliosIterator.hasNext(); ) {
                local_TurnoSolicitudSolicitudFoliosElement = (SolicitudFolio)local_TurnoSolicitudSolicitudFoliosIterator.next();
                
                local_Folio_IdMatricula   = local_TurnoSolicitudSolicitudFoliosElement.getIdMatricula();

                // get some Folio
                local_FolioId = new FolioPk();
                local_FolioId.idMatricula = local_Folio_IdMatricula;

                local_Folio = forseti.getFolioByIDSinAnotaciones( local_FolioId, local_ParamUsuarioSir );

                // ------------------------------------------------------------
                // Bug 3565

                local_FilteredSalvedades
                    = jxSearch_ExtractSalvedadesByTurno(
                        local_Folio.getSalvedades()
                      , local_ParamTurno.getIdWorkflow()
                      );

                local_Folio.setSalvedades( local_FilteredSalvedades );
                // ------------------------------------------------------------


                // add to list1
                local_Result_FoliosInTurno.add( local_Folio );

                if( local_Folio.getSalvedades().size() > 0 ) {
                  // add to list 2
                  local_Result_FoliosInTurnoConSalvedades.add( local_Folio );

                } // if


             } // for

            // eof:replace :

          }
          catch( ForsetiException fe ) {
                   List l;
                   l = fe.getErrores();
                   CopiaSalvedadException cfe = new CopiaSalvedadException();
                   cfe.addError(fe.getMessage());
                   throw cfe;
          }
          catch( Throwable e ) {
                   ExceptionPrinter printer = new ExceptionPrinter(e);
                   Log.getInstance().error(AnCorr_CopiaSalvedadOptions.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
                   throw new EventoException(e.getMessage(), e);
           }

           // wrap ------------------------------------
           local_Result = new EvnResp_CorrSimpleMain_CopiaSalvedadOptions( EvnResp_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTART__EVENTRESP_OK );

           // set results
           local_Result.setFoliosInTurnoList( local_Result_FoliosInTurno );
           local_Result.setFoliosInTurnoConSalvedadesList( local_Result_FoliosInTurnoConSalvedades );

          // -----------------------------------------
          // send-message ---------------------------------
          return local_Result;

          // -----------------------------------------

	} // end-method: processCopiaSalvedadTmpFolio_Iniciar

// -----------------------------------------
// -----------------------------------------



   private EventoRespuesta
   processCopiaSalvedadTmpFolio_ObtenerFoliosTurno_ObtenerSalvedadFolioSeleccionado( Evn_CorrSimpleMain_CopiaSalvedadOptions evento )
   throws EventoException {


     // unwrap ---------------------------------
     gov.sir.core.negocio.modelo.Usuario local_ParamUsuarioSir;
     gov.sir.core.negocio.modelo.Turno   local_ParamTurno;
     java.lang.String                    local_ParamSelectedFolioIdMatricula;

     // get params
     local_ParamUsuarioSir = evento.getUsuarioSir();
     local_ParamTurno      = evento.getTurno();
     local_ParamSelectedFolioIdMatricula = evento.getSelectedSource_FolioIdMatricula();
     // -----------------------------------------

     EvnResp_CorrSimpleMain_CopiaSalvedadOptions local_Result = null;

     // process ---------------------------------
     FolioPk local_FolioID;


     String local_Folio_IdMatricula = null;

     // tmpvalriables to getvalues

     // List< Folio >: lista folios destino
     List local_Result_PossibleTargetFoliosList;
     local_Result_PossibleTargetFoliosList = new ArrayList();

     // List< Folio >: lista de folio seleccionado del turno, con salvedades
     Folio local_Result_SourceFolio;

     try {


        //
        // TODO: calls
        //
        // 1. obtener folios con deltas
        // 2. para cada uno de los folios observar si tiene salvedades temporales
        // 3. devolver el listado de folios para el turno
        // 4. devlover el listado de folios con anotaciones temporales

        // CALL: List< SolicitudFolio > lst = forseti.getFoliosInTurno
        // for( SolicitudFolio elem: lst )
        //  add List< folio >: result_List1
        //  observar si tiene salvedades; add to result_List2: List< folio > with salvedadtx

        //

        Turno local_Turno;
        Folio local_Folio;
        FolioPk local_FolioId;


        String local_TmpSelectedFolioIdMatricula;
        String local_TmpSelectedFolioIdZonaRegistral;

        //
        Folio local_SelectedFolio;
        FolioPk local_SelectedFolioId;

        local_TmpSelectedFolioIdMatricula     = local_ParamSelectedFolioIdMatricula;
        local_TmpSelectedFolioIdZonaRegistral = forseti.getZonaRegistral( local_TmpSelectedFolioIdMatricula );

        local_SelectedFolioId = new FolioPk();
        local_SelectedFolioId.idMatricula = local_TmpSelectedFolioIdMatricula;


        // obtener el folio seleccionado
        local_SelectedFolio = forseti.getFolioByIDSinAnotaciones( local_SelectedFolioId, local_ParamUsuarioSir );

        // filtrar solo salvedades del turno
        // ------------------------------------------------------------
        // Bug 3565

        List local_FilteredSalvedades;


        local_FilteredSalvedades
            = jxSearch_ExtractSalvedadesByTurno(
                local_SelectedFolio.getSalvedades()
              , local_ParamTurno.getIdWorkflow()
              );

        local_SelectedFolio.setSalvedades( local_FilteredSalvedades );
        // ------------------------------------------------------------




        local_Result_SourceFolio = local_SelectedFolio;

        local_Turno = hermod.getTurnobyWF( local_ParamTurno.getIdWorkflow() );



        // List< SolicitudFolio >
        List local_TurnoSolicitudSolicitudFolios;
        Iterator local_TurnoSolicitudSolicitudFoliosIterator;
        SolicitudFolio local_TurnoSolicitudSolicitudFoliosElement;

        local_TurnoSolicitudSolicitudFolios = local_Turno.getSolicitud().getSolicitudFolios();
        local_TurnoSolicitudSolicitudFoliosIterator = local_TurnoSolicitudSolicitudFolios.iterator();

        for( ;local_TurnoSolicitudSolicitudFoliosIterator.hasNext(); ) {
           local_TurnoSolicitudSolicitudFoliosElement = (SolicitudFolio)local_TurnoSolicitudSolicitudFoliosIterator.next();

           local_Folio_IdMatricula   = local_TurnoSolicitudSolicitudFoliosElement.getIdMatricula();

           // get some Folio
           local_FolioId = new FolioPk();
           local_FolioId.idMatricula = local_Folio_IdMatricula;

           local_Folio = forseti.getFolioByIDSinAnotaciones( local_FolioId, local_ParamUsuarioSir );

           if( local_SelectedFolioId.equals( local_FolioId ) ) {
              continue;
           }

           local_Result_PossibleTargetFoliosList.add( local_Folio );

        } // for

       // eof:replace :

     }
     catch( ForsetiException fe ) {
              List l;
              l = fe.getErrores();
			  CopiaSalvedadException cfe = new CopiaSalvedadException();
              cfe.addError(fe.getMessage());
              throw cfe;
     }
     catch( Throwable e ) {
              ExceptionPrinter printer = new ExceptionPrinter(e);
              Log.getInstance().error(AnCorr_CopiaSalvedadOptions.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
              throw new EventoException(e.getMessage(), e);
      }

      // wrap ------------------------------------
      local_Result = new EvnResp_CorrSimpleMain_CopiaSalvedadOptions( EvnResp_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP1NEXT__EVENTRESP_OK );

      // set results
      local_Result.setPossibleFoliosTargetList( local_Result_PossibleTargetFoliosList );
      local_Result.setFolioSource( local_Result_SourceFolio );

      // TODO: here i am

     // -----------------------------------------
     // send-message ---------------------------------
     return local_Result;

     // -----------------------------------------

   } // end-method: processCopiaSalvedadTmpFolio_ObtenerFoliosTurno_ObtenerSalvedadFolioSeleccionado

   // -----------------------------------------
   // -----------------------------------------





  // TODO: modificar
   private EventoRespuesta
   processCopiaSalvedadTmpFolio_PreviewAccionesARealizar( Evn_CorrSimpleMain_CopiaSalvedadOptions evento )
   throws EventoException {


     // unwrap ---------------------------------
     gov.sir.core.negocio.modelo.Usuario local_ParamUsuarioSir;
     gov.sir.core.negocio.modelo.Turno   local_ParamTurno;
     java.lang.String                    local_ParamSelectedSourceFolioIdMatricula;
     java.lang.String[]                  local_ParamSelectedTargetFolioIdMatriculaArray;

     // get params
     local_ParamUsuarioSir = evento.getUsuarioSir();
     local_ParamTurno      = evento.getTurno();
     local_ParamSelectedSourceFolioIdMatricula      = evento.getSelectedSource_FolioIdMatricula();
     local_ParamSelectedTargetFolioIdMatriculaArray = evento.getSelectedTarget_FolioIdMatriculaArray();
     // -----------------------------------------

     EvnResp_CorrSimpleMain_CopiaSalvedadOptions local_Result = null;

     // process ---------------------------------
     FolioPk local_FolioID;

     String local_Folio_IdMatricula = null;

     // tmpvalriables to getvalues

     Folio local_Result_SourceFolio = null;
     List  local_Result_TargetFolioList = null;
     List  local_Result_TargetFolioListCountSalvedades = null;

     try {


          // verificar que el folio seleccionado origen
          // no esta dentro del destino
          // validar los folios dentro del turno.

          // contar el numero de salvedades que tiene cada folio

        Turno local_Turno;
        Folio local_Folio;
        FolioPk local_FolioId;


        String local_TmpSelectedFolioIdMatricula;
        String local_TmpSelectedFolioIdZonaRegistral;

        //
        Folio local_SelectedFolio;
        FolioPk local_SelectedFolioId;

        local_TmpSelectedFolioIdMatricula     = local_ParamSelectedSourceFolioIdMatricula;
        local_TmpSelectedFolioIdZonaRegistral = forseti.getZonaRegistral( local_TmpSelectedFolioIdMatricula );

        local_SelectedFolioId = new FolioPk();
        local_SelectedFolioId.idMatricula = local_TmpSelectedFolioIdMatricula;


        // obtener el folio seleccionado
        local_SelectedFolio = forseti.getFolioByIDSinAnotaciones( local_SelectedFolioId, local_ParamUsuarioSir );

        local_Result_SourceFolio = local_SelectedFolio;

        local_Turno = hermod.getTurnobyWF( local_ParamTurno.getIdWorkflow() );

        local_Result_TargetFolioList = new ArrayList();
        local_Result_TargetFolioListCountSalvedades = new ArrayList();

        // List< SolicitudFolio >
        List local_TurnoSolicitudSolicitudFolios;
        Iterator local_TurnoSolicitudSolicitudFoliosIterator;
        SolicitudFolio local_TurnoSolicitudSolicitudFoliosElement;

        local_TurnoSolicitudSolicitudFolios = local_Turno.getSolicitud().getSolicitudFolios();
        local_TurnoSolicitudSolicitudFoliosIterator = local_TurnoSolicitudSolicitudFolios.iterator();

        boolean founded;

        for( int i=0; i < local_ParamSelectedTargetFolioIdMatriculaArray.length; i++ ) {

           local_Folio_IdMatricula   = local_ParamSelectedTargetFolioIdMatriculaArray[i];

           // get some Folio
           local_FolioId = new FolioPk();
           local_FolioId.idMatricula = local_Folio_IdMatricula;

           if( local_SelectedFolioId.equals( local_FolioId ) ) {
              throw new ForsetiException( "Folio Origen no puede estar dentro de los folios destino: " + local_Folio_IdMatricula );
           }

           // buscar si existe en el folio
           founded = false;
           for( ;local_TurnoSolicitudSolicitudFoliosIterator.hasNext(); ) {
              local_TurnoSolicitudSolicitudFoliosElement = (SolicitudFolio)local_TurnoSolicitudSolicitudFoliosIterator.next();
              if( local_Folio_IdMatricula.equals( local_TurnoSolicitudSolicitudFoliosElement.getIdMatricula() ) ) {
                 founded = true;
                 break;
              }

           } // for

           if( !founded ) {
              throw new ForsetiException( "Folio no encontrado en turno: " + local_Folio_IdMatricula );
           }

           local_Folio = forseti.getFolioByIDSinAnotaciones( local_FolioId, local_ParamUsuarioSir );

           // ------------------------------------------------------------------------
           // Bug 3565

           List local_FilteredSalvedades;

           local_FilteredSalvedades
               = jxSearch_ExtractSalvedadesByTurno(
                   local_Folio.getSalvedades()
                 , local_ParamTurno.getIdWorkflow()
                 );

           local_Folio.setSalvedades( local_FilteredSalvedades );
           // ------------------------------------------------------------------------

           local_Result_TargetFolioList.add( local_Folio );
           local_Result_TargetFolioListCountSalvedades.add( new Integer( local_Folio.getSalvedades().size() ) );

        } // for

       // eof:replace :

     }
     catch( ForsetiException fe ) {
              List l;
              l = fe.getErrores();
			  CopiaSalvedadException cfe = new CopiaSalvedadException();
              cfe.addError(fe.getMessage());
              throw cfe;
     }
     catch( Throwable e ) {
              ExceptionPrinter printer = new ExceptionPrinter(e);
              Log.getInstance().error(AnCorr_CopiaSalvedadOptions.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
              throw new EventoException(e.getMessage(), e);
      }

      // wrap ------------------------------------
      local_Result = new EvnResp_CorrSimpleMain_CopiaSalvedadOptions( EvnResp_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP2NEXT__EVENTRESP_OK );

      // set results
      local_Result.setTargetFolioList( local_Result_TargetFolioList );
      local_Result.setTargetFolioListCountSalvedades( local_Result_TargetFolioListCountSalvedades );
      local_Result.setFolioSource( local_Result_SourceFolio );

     // -----------------------------------------
     // send-message ---------------------------------
     return local_Result;

     // -----------------------------------------

   } // end-method: processCopiaSalvedadTmpFolio_ObtenerFoliosTurno_ObtenerSalvedadFolioSeleccionado




   // TODO: modificar
    private EventoRespuesta
    processCopiaSalvedadTmpFolio_RealizarCopiaSalvedad( Evn_CorrSimpleMain_CopiaSalvedadOptions evento )
    throws EventoException {


      // unwrap ---------------------------------
      gov.sir.core.negocio.modelo.Usuario local_ParamUsuarioSir;
      gov.sir.core.negocio.modelo.Turno   local_ParamTurno;
      java.lang.String                    local_ParamSelectedSourceFolioIdMatricula;
      java.lang.String[]                  local_ParamSelectedTargetFolioIdMatriculaArray;

      // get params
      local_ParamUsuarioSir = evento.getUsuarioSir();
      local_ParamTurno      = evento.getTurno();

      // List< Folio>
      List local_SelectedTarget_FolioList;
      // Folio
      Folio local_SelectedSource_FolioItem;
      // String
      String local_SelectedsalvedadTx;

      local_SelectedTarget_FolioList = evento.getSelectedTarget_FolioList();
      local_SelectedSource_FolioItem = evento.getSelectedSource_FolioItem();
      local_SelectedsalvedadTx       = evento.getSelectedSalvedadTx();

      local_ParamSelectedSourceFolioIdMatricula      = evento.getSelectedSource_FolioIdMatricula();
      local_ParamSelectedTargetFolioIdMatriculaArray = evento.getSelectedTarget_FolioIdMatriculaArray();
      // -----------------------------------------

      EvnResp_CorrSimpleMain_CopiaSalvedadOptions local_Result = null;

      // process ---------------------------------
      FolioPk local_FolioID;

      String local_Folio_IdMatricula = null;

      // tmpvalriables to getvalues

      Folio local_Result_SourceFolio = null;
      List  local_Result_TargetFolioList = null;
      List  local_Result_TargetFolioListCountSalvedades = null;

      try {


           // verificar que el folio seleccionado origen
           // no esta dentro del destino
           // validar los folios dentro del turno.

           // contar el numero de salvedades que tiene cada folio

         Folio local_Folio;
         SalvedadFolio local_FolioSalvedadItem;
         FolioPk local_FolioId;


         // construir la salvedad
         local_FolioSalvedadItem = new SalvedadFolio();
         local_FolioSalvedadItem.setDescripcion( local_SelectedsalvedadTx );
         local_FolioSalvedadItem.setNumRadicacion( local_ParamTurno.getIdWorkflow() );
         local_FolioSalvedadItem.setUsuarioCreacion( local_ParamUsuarioSir );
         local_FolioSalvedadItem.setUsuarioCreacionTMP( local_ParamUsuarioSir );


         Iterator local_SelectedTarget_FolioListIterator;
         Folio    local_SelectedTarget_FolioListElement;

         local_SelectedTarget_FolioListIterator = local_SelectedTarget_FolioList.iterator();
         for( ;local_SelectedTarget_FolioListIterator.hasNext(); ) {
            local_SelectedTarget_FolioListElement = (Folio)local_SelectedTarget_FolioListIterator.next();

            // construir el folio con la salvedad
            local_Folio = new Folio();
            local_Folio.setIdMatricula( local_SelectedTarget_FolioListElement.getIdMatricula() ) ;
            //local_Folio.setZonaRegistral( local_SelectedTarget_FolioListElement.getZonaRegistral() ) ;

            local_Folio.addSalvedade( local_FolioSalvedadItem );

            forseti.updateFolio( local_Folio, local_ParamUsuarioSir, null, false );


         } // for

        // eof:replace :

      }
      catch( ForsetiException fe ) {
               List l;
               l = fe.getErrores();
		       CopiaSalvedadException cfe = new CopiaSalvedadException();
               cfe.addError(fe.getMessage());
               throw cfe;
      }
      catch( Throwable e ) {
               ExceptionPrinter printer = new ExceptionPrinter(e);
               Log.getInstance().error(AnCorr_CopiaSalvedadOptions.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
               throw new EventoException(e.getMessage(), e);
       }

       // wrap ------------------------------------
       local_Result = new EvnResp_CorrSimpleMain_CopiaSalvedadOptions( EvnResp_CorrSimpleMain_CopiaSalvedadOptions.CORRECCIONSIMPLEMAIN_OPCIONCOPIARSALVEDADTMPFOLIO_BTNSTEP3PROCESS__EVENTRESP_OK );

       // set results

      // -----------------------------------------
      // send-message ---------------------------------
      return local_Result;

      // -----------------------------------------

   } // end-method: processCopiaSalvedadTmpFolio_RealizarCopiaSalvedad


  //


// Salvedad debe tener atributo numRadicacion
// a traves del cual se filtra
// Se usa el mismo para salvedades de folio y anotacion
public List
jxSearch_ExtractSalvedadesByTurno(
 List   t0_ListSalvedades
, String local_Param_TurnoId
) {

      // zero-value cases
      if( null == t0_ListSalvedades ) {
         return null;
      } // if

      if( 0 == t0_ListSalvedades.size() ) {
         return t0_ListSalvedades;
      } // if

      local_SearchImpl_jx: {

                 // :: local variables ----------------------------------------------
                 // ...... source variables
                 JXPathContext 	local_JxContext;
                 Object 			local_JxContextSource;
                 String 			local_JxSearchString;

                 // ...... target variables
                 List local_TargetSalvedadesList;

                 // target jx-object
                 local_JxContextSource = t0_ListSalvedades;

                 // :: initialize
                 local_JxContext = JXPathContext.newContext( local_JxContextSource );
                 local_JxContext.setLenient( true );

                 // (declare variables)
                 local_JxContext.getVariables().declareVariable( "local_NumRadicacion", "" );

                 local_JxSearchString = " .[ (@numRadicacion = $local_NumRadicacion ) ]";
                 local_JxContext.setValue( "$local_NumRadicacion", local_Param_TurnoId );


                 // :: get the results
                 // single object   : local_JxContext.getValue
                 // multiple object : local_JxContext.iterate

                 Iterator local_TargetIterator;
                 local_TargetIterator = local_JxContext.iterate( local_JxSearchString );


                 local_TargetSalvedadesList = new ArrayList();

                 for( ; local_TargetIterator.hasNext() ; )  {
                     // consume
                     local_TargetSalvedadesList.add( local_TargetIterator.next() );
                 } // for





                 // -----------------------------------------------------------------
                 // fix the results
                 return local_TargetSalvedadesList;

      } // :local_SearchImpl_jx


} // jxSearch_ExtractSalvedadesFolioByTurno











} // end-class

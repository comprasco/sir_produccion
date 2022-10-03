package gov.sir.core.negocio.acciones.correccion;


import co.com.iridium.generalSIR.comun.GeneralSIRException;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import gov.sir.core.eventos.correccion.EvnMsgEdicionCiudadanoSobreTodosFolios;
import gov.sir.core.eventos.correccion.EvnRespMsgEdicionCiudadanoSobreTodosFolios;
import gov.sir.core.negocio.acciones.excepciones.NegocioCorreccionesCorreccionSimpleCollectorException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.TurnoPk;

import java.util.Iterator;
import gov.sir.core.negocio.modelo.Folio;
import java.util.List;
import gov.sir.core.negocio.modelo.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.hermod.HermodException;
import org.apache.commons.jxpath.JXPathContext;
import java.util.logging.Level;
import java.util.logging.Logger;

public class
AnEdicionCiudadanoSobreTodosFolios
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
	public AnEdicionCiudadanoSobreTodosFolios() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

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

	}
	/**
	 *
	 *
	 */
	public EventoRespuesta
	perform( Evento e )
	throws EventoException {


		EvnMsgEdicionCiudadanoSobreTodosFolios evento = (EvnMsgEdicionCiudadanoSobreTodosFolios) e;

                if( ( null == evento )
                    || ( null == evento.getTipoEvento() ) ) {

                  return null;

                }

                if( EvnMsgEdicionCiudadanoSobreTodosFolios.EVNTYPE_EDITCIUDADANO_ONLOAD.equals( evento.getTipoEvento() ) ){

                  return editCiudadanoOnLoad( evento );

                }
                if( EvnMsgEdicionCiudadanoSobreTodosFolios.EVNTYPE_EDITCIUDADANO_ONLOCKFOLIOS.equals( evento.getTipoEvento() ) ){

                  return editCiudadanoBloquearFolios( evento );

                }
                if( EvnMsgEdicionCiudadanoSobreTodosFolios.EVNTYPE_EDITCIUDADANO_ONAPPLY.equals( evento.getTipoEvento() ) ){

                  return editCiudadanoOnApply( evento );

                }

		return null;

	} // :perform


  public static Folio_Key getFolioKey( Anotacion representation1_Anotacion ) {
      // buscar el id del folio de esta anotacion
      String local1_Folio_IdMatricula      = representation1_Anotacion.getIdMatricula();//representation1_Anotacion.getFolio().getIdMatricula();

      Folio_Key representation1_FolioKey;
      // construir el key
      representation1_FolioKey = new Folio_Key( local1_Folio_IdMatricula);
      return representation1_FolioKey;

  }



  protected List editCiudadano_GetFoliosQueRelacionanCiudadano( gov.sir.core.negocio.modelo.CiudadanoPk ciudadano_Id ) throws
      EventoException {
    gov.sir.core.negocio.modelo.Ciudadano ciudadano = null;
    java.util.List listAnotacionesQueRelacionanCiudadano = null;
    try {
      // : buildParams
        listAnotacionesQueRelacionanCiudadano
            = forseti.getAnotacionesQueRelacionanCiudadano( ciudadano_Id );

      }
      catch( ForsetiException e ) {
          throw new EventoException( e.getMessage(), e );
      }
      catch( Throwable e ) {
          throw new EventoException( e.getMessage(), e );
      }

      // crear una lista vacia, si no existen anotaciones
      if( null == listAnotacionesQueRelacionanCiudadano ){
        listAnotacionesQueRelacionanCiudadano = new java.util.ArrayList();
      }



      // crear un hash para las busquedas

      HashMap representation2_Folio_Map = new HashMap();


      // temporal cycle variables
      Anotacion representation1_Anotacion;
      Folio_Key representation1_FolioKey;
      Folio representation2_Folio_Map_Item;

      java.util.Iterator iterator_listAnotacionesQueRelacionanCiudadano;// temporal cycle iterator 1

      iterator_listAnotacionesQueRelacionanCiudadano = listAnotacionesQueRelacionanCiudadano.iterator();
      // crear el arbol para la representacion
      //  de la forma (Folio -- + Anotaciones) (Usuario):
      // List<Folio>, List<Usuario>



      for( ;iterator_listAnotacionesQueRelacionanCiudadano.hasNext(); ) {

        representation1_Anotacion = (Anotacion)iterator_listAnotacionesQueRelacionanCiudadano.next();

        representation1_FolioKey = getFolioKey( representation1_Anotacion );

        // si no esta, lo agrega; si esta lo actualiza
        updateHash : {

          if (representation2_Folio_Map.containsKey( representation1_FolioKey ) ) {

            representation2_Folio_Map_Item = (Folio) representation2_Folio_Map.get( representation1_FolioKey);

          }
          else {

            representation2_Folio_Map_Item = new Folio();

            representation2_Folio_Map_Item.setIdMatricula(representation1_FolioKey.getIdFolio());
          
          } // :if
          representation2_Folio_Map_Item.addAnotacione(representation1_Anotacion);
          representation2_Folio_Map.put(representation1_FolioKey,representation2_Folio_Map_Item);

        } // :updateHash

      } // :for

      // crear una lista a partir del hash
      List representation2_Folio_List = buildRepresentation2( representation2_Folio_Map );


    return representation2_Folio_List;

  }
  
 /**
     * @author     : Carlos Mario Torres Urina
     * @casoMantis : 11767.
     * @actaReq    : Requerimiento No 089_151_Proceso_correcciones_permitir_varias_correcciones.
     * @change     : retorna la lista de folios que seran editados
     * @param      : ciudadano_id.
     * @param      : turno.
     * @throws     : DAOException.*/

 protected List editCiudadano_GetFoliosQueRelacionanCiudadano( gov.sir.core.negocio.modelo.CiudadanoPk ciudadano_Id,Turno turno ) throws
      EventoException{
    gov.sir.core.negocio.modelo.Ciudadano ciudadano = null;
    java.util.List listAnotacionesQueRelacionanCiudadano = null;
    java.util.List folios = new ArrayList();
    try
    {
        java.util.List solicitudfolios = turno.getSolicitud().getSolicitudFolios();
        for(Object f : solicitudfolios)
        {
            Folio folio = ((gov.sir.core.negocio.modelo.SolicitudFolio)f).getFolio();
            folios.add(folio);
        }
    }
    catch(Exception exp)
    {
        folios = new ArrayList();
    }
    
    try {
      // : buildParams
        listAnotacionesQueRelacionanCiudadano
            = forseti.getAnotacionesQueRelacionanCiudadano( ciudadano_Id );

      }
      catch( ForsetiException e ) {
          throw new EventoException( e.getMessage(), e );
      }
      catch( Throwable e ) {
          throw new EventoException( e.getMessage(), e );
      }

      // crear una lista vacia, si no existen anotaciones
      if( null == listAnotacionesQueRelacionanCiudadano ){
        listAnotacionesQueRelacionanCiudadano = new java.util.ArrayList();
      }



      // crear un hash para las busquedas

      HashMap representation2_Folio_Map = new HashMap();


      // temporal cycle variables
      Anotacion representation1_Anotacion;
      Folio_Key representation1_FolioKey;
      Folio representation2_Folio_Map_Item;

      java.util.Iterator iterator_listAnotacionesQueRelacionanCiudadano;// temporal cycle iterator 1

      iterator_listAnotacionesQueRelacionanCiudadano = listAnotacionesQueRelacionanCiudadano.iterator();
      // crear el arbol para la representacion
      //  de la forma (Folio -- + Anotaciones) (Usuario):
      // List<Folio>, List<Usuario>



      for( ;iterator_listAnotacionesQueRelacionanCiudadano.hasNext(); ) {

        representation1_Anotacion = (Anotacion)iterator_listAnotacionesQueRelacionanCiudadano.next();

        representation1_FolioKey = getFolioKey( representation1_Anotacion );

        // si no esta, lo agrega; si esta lo actualiza
        updateHash : {

          if (representation2_Folio_Map.containsKey( representation1_FolioKey ) ) {

            representation2_Folio_Map_Item = (Folio) representation2_Folio_Map.get( representation1_FolioKey);

          }
          else {

            representation2_Folio_Map_Item = new Folio();

            representation2_Folio_Map_Item.setIdMatricula(representation1_FolioKey.getIdFolio());
          
          } // :if
          if(!folios.contains(representation2_Folio_Map_Item)){
                representation2_Folio_Map_Item.addAnotacione(representation1_Anotacion);
                representation2_Folio_Map.put(representation1_FolioKey,representation2_Folio_Map_Item);
          }else
          {
                    Anotacion ant = new Anotacion();
                    try {
                        FolioPk fpk = new FolioPk();
                        fpk.idMatricula = representation2_Folio_Map_Item.getIdMatricula();
                        Anotacion anotacion = forseti.getAnotacionTMP(fpk,representation1_Anotacion.getIdAnotacion());
                        ant = anotacion;
                    }catch (Exception exp)
                     {
                        ant = representation1_Anotacion;
                        Logger.getLogger(AnEdicionCiudadanoSobreTodosFolios.class.getName()).log(Level.SEVERE, null, exp);
                     }
                     catch (Throwable e) 
                     {
                        Logger.getLogger(AnEdicionCiudadanoSobreTodosFolios.class.getName()).log(Level.SEVERE, null, e);
                     }
                     
                    representation2_Folio_Map_Item.addAnotacione(ant);
                    representation2_Folio_Map.put(representation1_FolioKey,representation2_Folio_Map_Item);
          }

        } // :updateHash

      } // :for

      // crear una lista a partir del hash
      List representation2_Folio_List = buildRepresentation2( representation2_Folio_Map );


    return representation2_Folio_List;

  }

  public EvnRespMsgEdicionCiudadanoSobreTodosFolios
  editCiudadanoOnLoad( EvnMsgEdicionCiudadanoSobreTodosFolios evento )
  throws EventoException {

    EvnMsgEdicionCiudadanoSobreTodosFolios thisEvent = evento;

    String param_CiudadanoId;

    param_CiudadanoId = thisEvent.getItemToFind_CiudadanoId();


    if( null == param_CiudadanoId ) {

      throw new EventoException( ":Debe especificarse un id para el ciudadano" );

    }

    gov.sir.core.negocio.modelo.Usuario param_Usuario_Turno;
    param_Usuario_Turno = thisEvent.getItemToFind_Usuario(); // para obtener ciudadanos modificados por esta persona

    gov.sir.core.negocio.modelo.Ciudadano ciudadano = null;
    java.util.List listAnotacionesQueRelacionanCiudadano = null;

    gov.sir.core.negocio.modelo.CiudadanoPk ciudadano_Id = null;

    try {

      // -----------------------------------------------------------
      // call services-layer (GetCiudadano)
      // : buildParams

      ciudadano_Id
          =  new gov.sir.core.negocio.modelo.CiudadanoPk( param_CiudadanoId );

      ciudadano = forseti.getCiudadanoByIdTMP( ciudadano_Id ); // TODO: ( ciudadano_Id, param_Usuario_Turno )

    // -----------------------------------------------------------


    }
    catch( ForsetiException e ) {
        throw new EventoException( e.getMessage(), e );
    }
    catch( Throwable e ) {
	throw new EventoException( e.getMessage(), e );
    }

    List representation2_Folio_List;

    representation2_Folio_List = editCiudadano_GetFoliosQueRelacionanCiudadano( ciudadano_Id );

    // crear una lista a partir del hash
    //= buildRepresentation2( representation2_Folio_Map );


    // segun el conjunto de folios distintos encontrados,
    // se consultan los bloqueos
    Usuario local2_UsuarioBloqueo;

    List representation2_UsuarioBloqueoList = new ArrayList();

    Iterator representation2_Folio_List_Iterator;
    representation2_Folio_List_Iterator = representation2_Folio_List.iterator();

    Folio local2_Folio;

    for( ;representation2_Folio_List_Iterator.hasNext() ;) {
      local2_Folio = (Folio)representation2_Folio_List_Iterator.next();
      FolioPk folio_Id = new FolioPk();
      folio_Id.idMatricula     = local2_Folio.getIdMatricula();
      //
      local2_UsuarioBloqueo = null;
      try {
        local2_UsuarioBloqueo = forseti.getBloqueoFolio( folio_Id );
      }
      catch( Throwable e ) {
        // ignore
        // lanza excepcion si no tiene bloqueo;
        // se envia un usuario nulo.
      }

      if( null == local2_UsuarioBloqueo ) {
        local2_UsuarioBloqueo = new Usuario();
        local2_UsuarioBloqueo.setUsername("");
      }

      representation2_UsuarioBloqueoList.add( local2_UsuarioBloqueo );

    }

    // la respuesta del procesamiento se devuelve en
    EvnRespMsgEdicionCiudadanoSobreTodosFolios result = null;
    result = new EvnRespMsgEdicionCiudadanoSobreTodosFolios( EvnRespMsgEdicionCiudadanoSobreTodosFolios.EVNRESPOK_EDITCIUDADANO_ONLOAD );

    result.setCiudadano( ciudadano );
    result.setListFolios( representation2_Folio_List );
    result.setListUsuarioBloqueo( representation2_UsuarioBloqueoList );
    // result.setAnotacionesQueRelacionanCiudadano( listAnotacionesQueRelacionanCiudadano );

    return result;

    // -----------------------------------------------------------

  } // :editCiudadanoOnLoad



  public EvnRespMsgEdicionCiudadanoSobreTodosFolios
  editCiudadanoBloquearFolios( EvnMsgEdicionCiudadanoSobreTodosFolios evento )
  throws EventoException {


    EvnMsgEdicionCiudadanoSobreTodosFolios thisEvent = evento;

    String param_CiudadanoId;

    param_CiudadanoId = thisEvent.getFoliosToBlock_CiudadanoId();


    if( null == param_CiudadanoId ) {

      throw new EventoException( ":Debe especificarse un id para el ciudadano" );

    }

    gov.sir.core.negocio.modelo.Ciudadano ciudadano = null;
    java.util.List listAnotacionesQueRelacionanCiudadano = null;

    gov.sir.core.negocio.modelo.CiudadanoPk ciudadano_Id = null;

    try {

      // -----------------------------------------------------------
      // call services-layer (GetCiudadano)
      // : buildParams

      ciudadano_Id
          =  new gov.sir.core.negocio.modelo.CiudadanoPk( param_CiudadanoId );

      ciudadano = forseti.getCiudadanoByIdTMP( ciudadano_Id );

    // -----------------------------------------------------------


    }
    catch( ForsetiException e ) {
        throw new EventoException( e.getMessage(), e );
    }
    catch( Throwable e ) {
        throw new EventoException( e.getMessage(), e );
    }

    List representation2_Folio_List;

    representation2_Folio_List = editCiudadano_GetFoliosQueRelacionanCiudadano( ciudadano_Id );


    // luego de tener la lista de los folios que relacionan el ciudadano
    // se debe incluir cada uno de los folios en el turno actual
    // y luego delegar el bloqueo al usuario
    gov.sir.core.negocio.modelo.Usuario param_UsuarioQueBloquea;
    gov.sir.core.negocio.modelo.Turno param_TurnoQueManejaBloqueo;
    gov.sir.core.negocio.modelo.TurnoPk param_TurnoQueManejaBloqueoId;

    param_UsuarioQueBloquea = thisEvent.getFoliosToBlock_Usuario();
    param_TurnoQueManejaBloqueo = thisEvent.getFoliosToBlock_Turno();

    param_TurnoQueManejaBloqueoId = new TurnoPk( param_TurnoQueManejaBloqueo.getIdWorkflow() );

    Iterator representation2_Folio_List_Iterator = null;

    // obtener lista de folios atados al turno actual
    List local_TurnoFoliosList = null;
    //local_TurnoFoliosList = param_TurnoQueManejaBloqueo.getSolicitud().getSolicitudFolios();

    try {
      Turno local_TurnoObjQueManejaBloqueoId = hermod.getTurno(
          param_TurnoQueManejaBloqueoId);

      local_TurnoFoliosList = local_TurnoObjQueManejaBloqueoId.getSolicitud().getSolicitudFolios();
    }
    catch (Throwable e) {

      throw new EventoException( e.getMessage(), e );

    }


    JXPathContext context = JXPathContext.newContext( local_TurnoFoliosList );
    context.getVariables().declareVariable( "idMatricula", "" );


    // tx.start -------------------------


    try {

      // -----------------------------------------------------------
      // call services-layer (GetCiudadano)
      // Se valida que no tenga folios cerrados
      Folio local_Folio;
      String mensajeCerrados = "";
      int contadorCerrados = 0;
      representation2_Folio_List_Iterator = representation2_Folio_List.iterator();
      for(;representation2_Folio_List_Iterator.hasNext();) {
    	  	local_Folio = (Folio)representation2_Folio_List_Iterator.next();
       		if (forseti.cerradoFolio(local_Folio.getIdMatricula())){
       			mensajeCerrados = mensajeCerrados + ", "  + local_Folio.getIdMatricula();
       			contadorCerrados++;
       		}
      }
      
      if (contadorCerrados>0){
    	  throw new EventoException( "Existen folios Cerrados relacionados con el Ciudadano, Debe realizar el proceso de apertura para realizar el procedimiento" + mensajeCerrados );
      }
          
      representation2_Folio_List_Iterator = representation2_Folio_List.iterator();
      /**
        * @Author Carlos Torres
        * @Mantis 13176
        * @Chaged ;
        */
        java.util.Map infoUsuario = new java.util.HashMap();
        if(evento.getInfoUsuario() !=null){
            infoUsuario.put("user",evento.getInfoUsuario().getUser());
            infoUsuario.put("host",evento.getInfoUsuario().getHost());
            infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
            infoUsuario.put("address",evento.getInfoUsuario().getAddress());
            infoUsuario.put("idTurno", param_TurnoQueManejaBloqueo.getIdWorkflow());
        }
        co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
        try {
            auditoria.guardarDatosTerminal(infoUsuario);
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
        }
      for(;representation2_Folio_List_Iterator.hasNext();) {
        local_Folio = (Folio)representation2_Folio_List_Iterator.next();

        context.setValue( "$idMatricula",local_Folio.getIdMatricula() );
        // verificar si el turno ya esta asociado
        Double foundedElelementsCount = (Double)context.getValue( "count( .[@idMatricula=$idMatricula] )");
        if( foundedElelementsCount.intValue() <= 0 ) {
          hermod.addFolioToTurno( local_Folio.getIdMatricula() , param_TurnoQueManejaBloqueoId, param_UsuarioQueBloquea );
        }
        try {
            auditoria.borrarDatosTerminal(param_TurnoQueManejaBloqueo.getIdWorkflow());
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
        }

      }

      forseti.delegarBloqueoFolios( param_TurnoQueManejaBloqueoId, param_UsuarioQueBloquea );
      /**
        * @Author Carlos Torres
        * @Mantis 13176
        * @Chaged ;
        */
      auditoria.borrarDatosTerminal(param_TurnoQueManejaBloqueo.getIdWorkflow());
    // -----------------------------------------------------------


    }
    catch( ForsetiException e ) {
		List l;
		l = e.getErrores();

		if (l.size() > 0) {
			NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
			throw cfe;
		}

		if (e.getHashErrores() != null  && !e.getHashErrores().isEmpty()) {
			Hashtable ht = e.getHashErrores();
			ValidacionParametrosHTException vpe = new ValidacionParametrosHTException(e);
			throw vpe;
		}
		
		if (e.getCause()!=null) {
			NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
			cfe.addError(e.getCause().getMessage());
			throw cfe;
		}

		if (e.getMessage() != null) {
			NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
			cfe.addError(e.getMessage());
			throw cfe;
		}
    }
    catch( HermodException e ) {
        throw new EventoException( e.getMessage(), e );
    }
    catch( Throwable e ) {
        throw new EventoException("Error validando Bloqueos: " + e.getMessage());
    }


    // luego de este procedimiento se debe recosntruir la lista de bloqueos
    // de los folios;


    // segun el conjunto de folios distintos encontrados,
    // se consultan los bloqueos
    Usuario local2_UsuarioBloqueo;

    List representation2_UsuarioBloqueoList;
    representation2_UsuarioBloqueoList = new ArrayList();

    representation2_Folio_List_Iterator = representation2_Folio_List.iterator();

    Folio local2_Folio;

    for( ;representation2_Folio_List_Iterator.hasNext() ;) {
      local2_Folio = (Folio)representation2_Folio_List_Iterator.next();
      FolioPk folio_Id = new FolioPk();
      folio_Id.idMatricula     = local2_Folio.getIdMatricula();
      //
      local2_UsuarioBloqueo = null;
      try {
        local2_UsuarioBloqueo = forseti.getBloqueoFolio( folio_Id );
      }
      catch( Throwable e ) {
        // ignore
        // lanza excepcion si no tiene bloqueo;
        // se envia un usuario nulo.
      }

      if( null == local2_UsuarioBloqueo ) {
        local2_UsuarioBloqueo = new Usuario();
        local2_UsuarioBloqueo.setUsername("");
      }

      representation2_UsuarioBloqueoList.add( local2_UsuarioBloqueo );

    }

    // la respuesta del procesamiento se devuelve en
    EvnRespMsgEdicionCiudadanoSobreTodosFolios result = null;
    result = new EvnRespMsgEdicionCiudadanoSobreTodosFolios( EvnRespMsgEdicionCiudadanoSobreTodosFolios.EVNRESPOK_EDITCIUDADANO_ONLOCKFOLIOS );

    // se envia de nuevo el ciudadano ?
    result.setCiudadano( ciudadano );
    result.setListFolios( representation2_Folio_List );
    result.setListUsuarioBloqueo( representation2_UsuarioBloqueoList );

    // si se efectuo este paso satisfactoriamente
    // significa que se tiene bloqueada toda la lista de folios
    // por el usuario que tiene el turno
    result.setVerificacionBloqueoFolios( true );

    return result;
    // -----------------------------------------------------------

  } // :editCiudadanoBloquearFolios













  public EvnRespMsgEdicionCiudadanoSobreTodosFolios
  editCiudadanoOnApply( EvnMsgEdicionCiudadanoSobreTodosFolios evento )
  throws EventoException {


    EvnMsgEdicionCiudadanoSobreTodosFolios thisEvent = evento;

    String param_CiudadanoId;
    gov.sir.core.negocio.modelo.Ciudadano param_Ciudadano;
    gov.sir.core.negocio.modelo.Usuario  param_CiudadanoUpdatedBy;

    String param_SalvedadTx;

    param_Ciudadano   = thisEvent.getCiudadanoToUpdate_Ciudadano_t2();
    param_CiudadanoId = (null != param_Ciudadano )?( param_Ciudadano.getIdCiudadano() ) :( null );
    param_CiudadanoUpdatedBy  = thisEvent.getCiudadanoToUpdate_Usuario();
    param_SalvedadTx  = thisEvent.getCiudadanoToUpdate_SalvedadTx();
    
    Turno turno = thisEvent.getTurno();




    if( null == param_CiudadanoId ) {

      throw new EventoException( ":Debe especificarse un id para el ciudadano" );

    }
    boolean updateResult = false;


    // construir la salvedad
    SalvedadAnotacion salvedadAnotacion;
    salvedadAnotacion = new SalvedadAnotacion();
    salvedadAnotacion.setDescripcion( param_SalvedadTx );
    salvedadAnotacion.setNumRadicacion(turno.getIdWorkflow());



    // se debe obtener de nuevo la lista de folios,
    // para poder escribir las salvedades de cambio.

    List representation2_Folio_List;
    gov.sir.core.negocio.modelo.CiudadanoPk localCiudadanoId;
    localCiudadanoId =  new gov.sir.core.negocio.modelo.CiudadanoPk( param_CiudadanoId );

    representation2_Folio_List = editCiudadano_GetFoliosQueRelacionanCiudadano( localCiudadanoId,turno);

    try {

      // 1. realizar la actualizacion del ciudadano

      updateResult
        = forseti.updateCiudadano( param_Ciudadano, param_CiudadanoUpdatedBy, turno.getIdWorkflow() );


      // 2. para cada uno de los folios que lo relacionaban, crear su correspondiente salvedad
      Iterator representation2_Folio_List_Iterator;
      Folio representation2_Folio_List_Item;

      List representation2_Folio_List_Item_AnotacionList;
      Iterator representation2_Folio_List_Item_AnotacionList_Iterator;
      Anotacion representation2_Anotacion_List_Item;
      representation2_Folio_List_Iterator = representation2_Folio_List.iterator();
      // foreach folio in representation2_Folio_List_Iterator
      for(;representation2_Folio_List_Iterator.hasNext();){

        representation2_Folio_List_Item = (Folio)representation2_Folio_List_Iterator.next();

        // foreach anotacion in representation2_Folio_List_Item
        representation2_Folio_List_Item_AnotacionList = representation2_Folio_List_Item.getAnotaciones();
        representation2_Folio_List_Item_AnotacionList_Iterator = representation2_Folio_List_Item_AnotacionList.iterator();

        for(;representation2_Folio_List_Item_AnotacionList_Iterator.hasNext();){
          representation2_Anotacion_List_Item = (Anotacion)representation2_Folio_List_Item_AnotacionList_Iterator.next();

          representation2_Anotacion_List_Item.addSalvedade( salvedadAnotacion );
        } // end for

        /*
        * @author      :   Henry Gómez Rocha
        * @change      :   Seteando turno para enviarlo en lugar de enviar null.
        * Caso Mantis  :   0004503
        */

        TurnoPk tid = new TurnoPk();
        tid.anio = evento.getTurno().getAnio();
        tid.idCirculo = evento.getTurno().getIdCirculo();
        tid.idProceso = evento.getTurno().getIdProceso();
        tid.idTurno = evento.getTurno().getIdTurno();

        // actualizar el folio
        forseti.updateFolio( representation2_Folio_List_Item, param_CiudadanoUpdatedBy, tid, false );
      } // end for

      // llamar a actualizar folio

    }
    catch( ForsetiException e ) {
        throw new EventoException( e.getMessage(), e );
    }
    catch( Throwable e ) {
        throw new EventoException( e.getMessage(), e );
    }



    // la respuesta del procesamiento se devuelve en
    EvnRespMsgEdicionCiudadanoSobreTodosFolios result = null;
    result = new EvnRespMsgEdicionCiudadanoSobreTodosFolios( EvnRespMsgEdicionCiudadanoSobreTodosFolios.EVNRESPOK_EDITCIUDADANO_ONAPPLY );


    // TODO: realizar la consulta de nuevo ?
    gov.sir.core.negocio.modelo.Ciudadano newCiudadano = param_Ciudadano;

    // se envia de nuevo el ciudadano ?
    result.setCiudadano( newCiudadano );
    result.setRespuestaActualizacion( updateResult );

    // si se efectuo este paso satisfactoriamente
    // significa que se tiene bloqueada toda la lista de folios
    // por el usuario que tiene el turno

    return result;
    // -----------------------------------------------------------

  } // :editCiudadanoOnApply








  /**
   * buildRepresentation2
   *
   * @param representation2_Folio_Map HashMap
   * @return List
   */
  private List buildRepresentation2(HashMap hashMap) {

    List result = new ArrayList();

    Iterator iterator = hashMap.entrySet().iterator();
    Map.Entry mapEntry;
    Folio item;

    for(;iterator.hasNext();) {
      mapEntry = (Map.Entry) iterator.next();
      item = (Folio) mapEntry.getValue();

      if (null == item)
        continue;

      result.add( item );
    }

    return result;

  }

  // inner class :: immutable
  public static class Folio_Key implements Comparable {

    String idFolio;
    

    public String getIdFolio() {
      return idFolio;
    }
   
    public Folio_Key( String newIdFolio) {
      idFolio         = newIdFolio;
    }

    public int compareTo( Object o ) {
      if( o instanceof Folio_Key ) {
        Folio_Key folio_Key = (Folio_Key) o;

        return idFolio.compareTo( folio_Key.idFolio );

      }
      else if( o instanceof String ) {
        return idFolio.compareTo( (String)o );
      }
      else {
        return -2;
      }


    } // :compareTo

    public boolean equals( Object obj ) {
      return( this.compareTo( obj ) == 0 );
    } // :equals

    public int hashCode() {
      // TODO: revisar consejos para construir hashCodes y revisar si es apropiado
      // dejarlo asi
      // return 0; // se usa en un hashmap con el equals de Folio_Key; por esto se deja 0, no por otra condicion

      int hashCode1 = ( null == idFolio         )?( 0 ):( idFolio.hashCode()         );
      return ( hashCode1);
    }



  } // :inner class



} // :class

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
import org.auriga.util.ExceptionPrinter;


import gov.sir.core.eventos.correccion.EvnCorreccion;
import gov.sir.core.eventos.correccion.EvnMsgMesaControl;
import gov.sir.core.negocio.acciones.excepciones.CorreccionImpresionCertificadoException;
import gov.sir.core.negocio.acciones.excepciones.Correccion_ImprimirCertificados_InvalidPrintDateException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.SalvedadFolio;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import gov.sir.core.negocio.modelo.Folio;
import java.util.List;

import java.util.Calendar;
import java.util.ArrayList;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.hermod.HermodException;
import org.apache.commons.jxpath.JXPathContext;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;

import java.util.Hashtable;

import javax.imageio.ImageIO;

import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicDateFormatComparator;
import gov.sir.core.web.helpers.correccion.diff.utils.jxpath.FolioAbstractFactory;
import gov.sir.core.eventos.correccion.EvnRespMsgMesaControl;
import gov.sir.core.is21.Encriptador;
   /**
    * @author      :   Carlos Torres
    * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
    */              
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CFase;
/**
* @Autor: Edgar Lora
* @Mantis: 0006493
*/
import gov.sir.print.server.PrintJobsProperties;

public class
AnCorr_MesaControl
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
	 * Instancia de Hermod
	 */
	private PrintJobsInterface printJobs;

	/**
	 * ServiceLocatorInstance
	 */
	private ServiceLocator service = null;

	/**
	 *Constructor de la clase ANCorrección.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public AnCorr_MesaControl() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
                buildServices( service );


	}



   public void buildServices( ServiceLocator locator ) throws EventoException {

      try {
              hermod =
                      (HermodServiceInterface) service.getServicio("gov.sir.hermod");

              forseti =
                      (ForsetiServiceInterface) service.getServicio(
                              "gov.sir.forseti");
              
              printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

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
      
		if (printJobs == null) {
			throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
		}
   } // :buildServices


	/**
	 *
	 *
	 */
	public EventoRespuesta
	perform( Evento e )
	throws EventoException {


		EvnMsgMesaControl evento = (EvnMsgMesaControl) e;

                if( ( null == evento )
                    || ( null == evento.getTipoEvento() ) ) {

                  return null;

                }

                if( EvnMsgMesaControl.EVNTYPE_MESACONTROL_ONOK.equals( evento.getTipoEvento() ) ){

                  return mesaControlOnOk( evento );

                } else if( EvnMsgMesaControl.IMPRIMIR_MESA.equals( evento.getTipoEvento() ) ){

                    return imprimirCertificadosMesa( evento );

                } else if( EvnMsgMesaControl.IMPRIMIR.equals( evento.getTipoEvento() ) ){

                    return imprimirCertificadosIndividual( evento );

                }

		return null;

	} // :perform

   public EvnRespMsgMesaControl
   mesaControlOnOk( EvnMsgMesaControl thisEvent )
   throws EventoException {

      Turno local_Turno = null;
      Fase local_Fase = null;
      Hashtable local_Parameters = null;
      int local_TipoAvance = 0;

      boolean local_Result;

      // turno
      TurnoPk local_TurnoId = null;
      local_TurnoId  = new TurnoPk( thisEvent.getTurno().getIdWorkflow() );

      try {
        local_Turno = hermod.getTurno( local_TurnoId );
      }
      catch( HermodException e ) {
        throw new EventoException ("Error localizando el turno en el servicio hermod.",e);
      }
      catch (Throwable e) {
        throw new EventoException ("Error localizando el turno en el servicio hermod.",e);
      }


      // fase
      local_Fase = new Fase();
      local_Fase.setID( local_Turno.getIdFase() );

      // parameters
      String param_Result     = thisEvent.getWorkflowMessageId();
      String param_UsuarioSir = ( null != thisEvent.getUsuarioTurno() )?(""+thisEvent.getUsuarioTurno().getUsername()):("");


      local_Parameters = new Hashtable();
      local_Parameters.put( Processor.RESULT            , param_Result     );
      local_Parameters.put( CInfoUsuario.USUARIO_SIR    , param_UsuarioSir );

      try {
           /**
            * @author      :   Carlos Torres
            * @change      :   Se crea la relacion
            * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
            */              
        List turnos = new ArrayList();
        turnos.add(local_Turno);
        Circulo circulo = new Circulo();
        circulo.setIdCirculo(local_Turno.getIdCirculo());
        TipoRelacion tr = new TipoRelacion();
        /*
        * @autor         : JATENCIA 
        * @mantis        : 0011604 
        * @Requerimiento : 004_589_Funcionario_Fase_ Entregado  
        * @descripción   : Se modifica el Id de Tipo Relación y se incluye Id Fase
        * para que el turno siga el flujo de corrección que se ha generado.
        */
        tr.setIdTipoRelacion("26");
        tr.setIdFase(CFase.COR_MESA_CONTROL);
        /* Fin del bloque */
        
        local_Result = hermod.avanzarTurnoNuevoNormal( local_Turno, local_Fase, local_Parameters, thisEvent.getUsuarioTurno() );
        if(local_Result){
            hermod.crearRelacionNuevo(tr, thisEvent.getUsuarioTurno(),circulo, turnos,"");
        }
        // assert local_Result;
        // if( false == local_Result ) {
        // }
      }
      catch( HermodException e ) {
        throw new EventoException ("Error avanzando el turno en el servicio hermod.",e);
      }
      catch (Throwable e) {
        throw new EventoException ("Error avanzando el turno en el servicio hermod.",e);
      }

      // build resp.

      EvnRespMsgMesaControl result = null;
      result = new EvnRespMsgMesaControl( EvnRespMsgMesaControl.EVNRESPOK_MESACONTROL_ONOK );

      return result;

   } // mesaControlOnOk
   
	/**
	 * Método que permite al corrector imprimir uno o varios certificados de libertad cuando se ha aprobado el
	 * caso de corrección de documentos.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EvnRespMsgMesaControl imprimirCertificadosMesa(EvnCorreccion evento) throws EventoException {

         //OBTENER INFORMACIÓN A PARTIR DEL TURNO
         Turno turno = evento.getTurno();
         
         //TFS 5432 NO PERMITIR IMPRIMIR CERTIFICADOS ASOCIADOS SI LA CORRECCION ES INTERNA
         
         Turno turnoPadre=evento.getTurnoPadre();
         if(turnoPadre!=null)
         {
       	  //la correccion es interna imposible imprimir
        	 throw new  Correccion_ImprimirCertificados_InvalidPrintDateException( "Imposible consultar folios a imprimir, debido a que la correccion es de tipo INTERNA");
         }
         
    
         //FIN TFS 5432
         
         // bug 3589: solo se permite la impresion de certificados el mismo dia en que se aprueba correccion

         java.util.Date local_Date;
         local_Date = new java.util.Date(System.currentTimeMillis());

         // buscar la fecha en la cual se realiza la aprobacion
         // de la correccion
         java.util.Date local_TurnoHistoriaDate = null;


         boolean founded_Date = false;

         searchImpl_jx: {


            if( !founded_Date ) {

               // caso1: correccion normal; buscar idFaseAprobacion

               Turno local_Turno;

               // buscar turno en la db?
               local_Turno = turno;

               // por el momento se observa el registro historico que tiene el turno
               // List< TurnoHistoria >
               List local_TurnoHistoria;

               local_TurnoHistoria = local_Turno.getHistorials();

               JXPathContext local_TurnoSearchContext = jx_GetContext( local_TurnoHistoria );
               local_TurnoSearchContext.getVariables().declareVariable("idFase", "");
               /*JAlcazar 04/05/2010 caso mantis 2754 Requerimiento 132 - Impresion en la fase Mesa Control Correcciones
                * La fecha de aprobación corresponde a la fecha en que el turno pasa a Mesa de Control CFase.COR_MESA_CONTROL
                */
               local_TurnoSearchContext.setValue("$idFase", CFase.COR_MESA_CONTROL);

               // search matches
               // apply first

               Iterator local_TurnoSearchContextIterator = local_TurnoSearchContext.iterate(
                   ".[@fase = $idFase]");

               for (; local_TurnoSearchContextIterator.hasNext(); ) {
                 TurnoHistoria element = (TurnoHistoria) local_TurnoSearchContextIterator.
                     next();
                 local_TurnoHistoriaDate = element.getFecha();
                 founded_Date = true;
               } // for


               } // if


            // Bug X075
            if( !founded_Date ) {
               String raise_Tx = "";
               raise_Tx += "No se ha podido raastrear la fecha de aprobacion de correccion";

               throw new Correccion_ImprimirCertificados_InvalidPrintDateException( raise_Tx );
            } //



         } // :searchImpl_jx


         // Double foundedElelementsCount = (Double)local_TurnoSearchContext.getValue( "count( .[@fase = $idFase] )");
         // if( foundedElelementsCount.intValue() > 0 ) {
         // }

         // realizar verificacion; si no pasa la prueba
         // raise appError: no se aprueba impresion
         //  se supero el limite de tiempo para realizar la impresion
         // TODO: hacer algo para comparadores en capa de negocio
         // por el momento, usar los comparadores realizados


         String local_TurnoHistoriaDate_String = "";
         String local_Date_String = "";

         local_TurnoHistoriaDate_String = DateFormatUtil.format( local_TurnoHistoriaDate );
         local_Date_String              = DateFormatUtil.format( local_Date              );

         BasicDateFormatComparator comparator = new BasicDateFormatComparator( DateFormatUtil.DEFAULT_FORMAT );
         if( comparator.compare( local_Date, local_TurnoHistoriaDate ) > 0 ){
           String raise_Tx = "";
           raise_Tx += "Se supero el limite de tiempo para realizar la impresion: <br />";
           raise_Tx += "Solo se permite impresión de certificados por esta pantalla ";
           raise_Tx += " en el mismo día en que se aprobó la corrección. ";
           raise_Tx += " <br />Fecha Aprobacion: " + local_TurnoHistoriaDate_String;
           raise_Tx += " <br />Fecha Actual    : " + local_Date_String ;

           throw new Correccion_ImprimirCertificados_InvalidPrintDateException( raise_Tx );
         }

         EvnRespMsgMesaControl ev = new EvnRespMsgMesaControl(EvnRespMsgMesaControl.IMPRIMIR_MESA);
         ev.setTurno(turno);
         ev.setUsuario(evento.getUsuario());

		//IMPRIMIR LOS CERTIFICADOS SOLICITADOS
		try {
			List noImpresos = imprimirCertificados(turno, turno.getSolicitud().getSolicitudFolios(), evento.getCirculoId(), evento.getImpresora());
			ev.setFoliosNoImpresos(noImpresos);
		} catch (Throwable t2) {
			t2.printStackTrace();
			throw new EventoException(t2.getMessage());
		}

		return ev;
	}
	
	/**
	 * Método que imprime los certificados que se solicitan.
	 * @param turno
	 * @param parametros
	 * @param folios
	 * @return
	 * @throws Throwable
	 */
	private List imprimirCertificados(Turno turno, List folios, String cid, String impresora) throws Throwable {

		List numcopias = obtenerNumeroCopias(turno, folios);
		for (int i = 0; i < folios.size(); i++) {

			int copias = ((Integer)numcopias.get(i)).intValue();

			for (int j = 0; j < copias; j++){
				Folio folio = ((SolicitudFolio) folios.get(i)).getFolio();
				folio = forseti.getFolioByMatricula(folio.getIdMatricula());

				FolioPk fid = new FolioPk();
				fid.idMatricula = folio.getIdMatricula();
				
				List anotaciones = folio.getAnotaciones();
				if (anotaciones == null || anotaciones.isEmpty())
				{
					anotaciones = forseti.getAnotacionesFolio(fid);
					folio.setAnotaciones(anotaciones);
				}

				List padres = forseti.getFoliosPadre(fid);
				List hijos = forseti.getFolioHijosEnAnotacionesConDireccion(fid);
                                /**
                                * @author: David Panesso
                                * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                                * Nuevo listado de folios derivados
                                **/
                                List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

				String fechaImpresion= this.getFechaImpresion();
				//obtener textos base de los separadores
				String tbase1 ="";
				String tbase2 = "";
				List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
				for(Iterator it= variablesImprimibles.iterator(); it.hasNext();){
					OPLookupCodes op = (OPLookupCodes) it.next();
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
						tbase1= op.getValor();
					}
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
						tbase2 = op.getValor();
					}
				}
				ImprimibleCertificado imprimibleCertificado = new ImprimibleCertificado(folio, turno, padres, hijos, foliosDerivadoHijos, fechaImpresion, CTipoImprimible.CERTIFICADO_CORRECCIONES, tbase1, tbase2);
				imprimibleCertificado.setPrintWatermarkEnabled(true);
                                /**
                                 * @author     : Carlos Torres
                                 * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                 */
                                Traslado traslado = hermod.getFolioDestinoTraslado(folio.getIdMatricula());
                                imprimibleCertificado.setInfoTraslado(traslado);
				imprimir(imprimibleCertificado, cid, impresora);
			}
		}
		List foliosNoImpresos = new ArrayList();
		for (int i = 0; i < folios.size(); i++) {
			int copias = ((Integer)numcopias.get(i)).intValue();
			if (copias == -1){
				//TFS 3790: NO SE PUEDEN IMPRIMIR FOLIOS ANULADOS
				SolicitudFolio solFolio = (SolicitudFolio)folios.get(i);
				Folio folio = forseti.getFolioByMatricula(solFolio.getFolio().getIdMatricula());
				if(!folio.getEstado().getIdEstado().equals(CEstadoFolio.ANULADO)){
					boolean tieneSalvedades = false;
					//TFS 4578: SOLO SE PUEDEN IMPRIMIR LOS FOLIOS QUE TIENEN SALVEDADES
					if(folio.getSalvedades()!=null){
						Iterator itSalvedadesFolio = folio.getSalvedades().iterator();
						//SE REVISAN LAS SALVEDADES DEL FOLIO
						while(itSalvedadesFolio.hasNext()){
							SalvedadFolio salvedadFolio = (SalvedadFolio)itSalvedadesFolio.next();
                            /**
                             *@author: Guillermo Cabrera.
                             * @change: Se adiciona la condición salvedadFolio.getNumRadicacion()!=null en el if para evitar que se haga el equals()
                             * cuando el NumRadicacion tenga valor nulo. ya que al realizar esta comparación se presenta un nullpointerexception.
                             * CASO MANTIS 2935
                             **/
							if(salvedadFolio.getNumRadicacion()!=null && salvedadFolio.getNumRadicacion().equals(turno.getIdWorkflow())){
								tieneSalvedades = true;
								break;
							}
						}
					}
					//SI NO HAY SALVEDADES DEL FOLIO, SE REVISAN LAS DE LAS ANOTACIONES
					if(!tieneSalvedades){
						FolioPk fid = new FolioPk();
						fid.idMatricula = folio.getIdMatricula();
						List anotacionesSalvedades = forseti.getAnotacionesConSalvedades(fid);
						if (anotacionesSalvedades != null)
						{
							for (Iterator anotaSalvItera = anotacionesSalvedades.iterator();
									anotaSalvItera.hasNext(); )
							{
								Anotacion anotacion = (Anotacion)anotaSalvItera.next();
								List salvedades = anotacion.getSalvedades();
								if (salvedades != null)
								{
									Iterator itSalvedadesAnotaciones = salvedades.iterator();
									while(itSalvedadesAnotaciones.hasNext()){
										SalvedadAnotacion salvedadAnotacion = 
											(SalvedadAnotacion)itSalvedadesAnotaciones.next();
                                        /**
                                         *@author: Guillermo Cabrera.
                                         * @change: Se adiciona la condición salvedadAnotacion.getNumRadicacion() en el if para evitar que se haga el equals()
                                         * cuando el NumRadicacion tenga valor nulo. ya que al realizar esta comparación se presenta un nullpointerexception.
                                         * CASO MANTIS 2935
                                         **/
										if(salvedadAnotacion.getNumRadicacion()!=null && salvedadAnotacion.getNumRadicacion().equals(turno.getIdWorkflow())){
											tieneSalvedades = true;
											break;
										}
									}
								}
							}
							
						}
					}
					if(tieneSalvedades){
						foliosNoImpresos.add(folios.get(i));
					}
				}
			}
		}

		return foliosNoImpresos;

	}
	
	/**
	 * Método que permite al corrector imprimir uno o varios certificados de libertad cuando se ha aprobado el
	 * caso de corrección de documentos.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EvnRespMsgMesaControl imprimirCertificadosIndividual(EvnCorreccion evento) throws EventoException {

		//OBTENER INFORMACIÓN A PARTIR DEL TURNO
		Turno turno = evento.getTurno();
		EvnRespMsgMesaControl ev = new EvnRespMsgMesaControl( EvnRespMsgMesaControl.IMPRIMIR_MESA);
		ev.setTurno(turno);
		ev.setUsuario(evento.getUsuario());

                // extract parameters from event

                List folios = evento.getFolios();
                String uid = evento.getUID();

                // ------------------------------------------------------------
                // Bug 3570:
                // no se puede dejar imprimir
                // un certificado seleccionado si no
                // tiene salvedades.

                // Salvedades ya han pasado a defitivo:
                // se debe buscar sobre las salvedades de folio y de anotacion

                validate_FolioHasSalvedades: {

                   try {

                    String param_IdWorkflow;
                    String param_IdMatricula;

                    long result_CountSalvedadesFolio;
                    long result_CountSalvedadesAnotacion;

                    param_IdWorkflow = turno.getIdWorkflow();
                    param_IdMatricula = null;

                    Turno local_Turno;
                    Folio local_Folio;

                    local_Turno = hermod.getTurnobyWF( param_IdWorkflow );

                    if( null == local_Turno ) {
                       throw new CorreccionImpresionCertificadoException( "Turno Identificado con id " + param_IdWorkflow + " no encontrado" );
                    }



                    List local_TurnoSolicitudFoliosList;
                    Iterator local_TurnoSolicitudFoliosListIterator;
                    Folio local_TurnoSolicitudFoliosListElement;

                    local_TurnoSolicitudFoliosList = folios;//local_Turno.getSolicitud().getSolicitudFolios();
                    if( null == local_TurnoSolicitudFoliosList ) {
                       local_TurnoSolicitudFoliosList = new ArrayList();
                    }

                    local_TurnoSolicitudFoliosListIterator = local_TurnoSolicitudFoliosList.iterator();

                    for( ;local_TurnoSolicitudFoliosListIterator.hasNext();){
                       local_TurnoSolicitudFoliosListElement = (Folio)local_TurnoSolicitudFoliosListIterator.next();
                       param_IdMatricula = local_TurnoSolicitudFoliosListElement.getIdMatricula();
                       local_Folio = forseti.getFolioByMatricula( param_IdMatricula );
// ok sin anotaciones
                       if( null == local_Folio ) {
                         throw new CorreccionImpresionCertificadoException( "Folio Identificado con matricula " + param_IdMatricula + " no encontrado" );
                       }

                       result_CountSalvedadesFolio = forseti.countSalvedadesFolio( param_IdMatricula,param_IdWorkflow,null );
                       result_CountSalvedadesAnotacion = forseti.countSalvedadesAnotacion( param_IdMatricula,param_IdWorkflow,null );


                       if( 0L == ( result_CountSalvedadesFolio + result_CountSalvedadesAnotacion )  ) {
                          throw new CorreccionImpresionCertificadoException( "Folio no tiene salvedades registradas, no se permite impresion: (Folio:" + param_IdMatricula + ", Turno:" + param_IdWorkflow + ")." );
                       } // if

                    } // for

                  }
                  catch( CorreccionImpresionCertificadoException e ){
                    throw e;
                  }
                  catch( ForsetiException e ){
                    throw new CorreccionImpresionCertificadoException( e.getMessage() );
                  }
                  catch( Throwable t2 ) {
                    t2.printStackTrace();
                    throw new EventoException(t2.getMessage());
                  } // try

                } // :validate_FolioHasSalvedades

                // ------------------------------------------------------------





		try {
			for (int i = 0; i < folios.size(); i++) {
				Folio folio = (Folio) folios.get(i);
				folio = forseti.getFolioByMatricula(folio.getIdMatricula());
// se necesitan todas las anotaciones debido a que es para certificado
				
				FolioPk fid = new FolioPk();
				fid.idMatricula = folio.getIdMatricula();
				
				List anotaciones = folio.getAnotaciones();
				if (anotaciones == null || anotaciones.isEmpty())
				{
					anotaciones = forseti.getAnotacionesFolio(fid);
					folio.setAnotaciones(anotaciones);
				}

				List padres = forseti.getFoliosPadre(fid);
				List hijos = forseti.getFolioHijosEnAnotacionesConDireccion(fid);
                                /**
                                * @author: David Panesso
                                * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                                * Nuevo listado de folios derivados
                                **/
                                List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

				//Obtener la solicitud de correccion completa
				SolicitudCorreccion solCorreccion = (SolicitudCorreccion) hermod.getSolicitudById(turno.getSolicitud().getIdSolicitud());

				//Antes de imprimir se mira si existe una solcitudHija  (SolicitudCertificado) con la matricula a imprimir
				boolean yaImpreso = false;
				SolicitudCertificado solCertificado = null;
				SolicitudFolio solFolio = null;
				List sHijas = solCorreccion.getSolicitudesHijas();
				for(Iterator itHijas = sHijas.iterator(); itHijas.hasNext();){
					SolicitudAsociada solAsoc = (SolicitudAsociada) itHijas.next();
					Solicitud sol = hermod.getSolicitudById(solAsoc.getIdSolicitud1());
					if(sol instanceof SolicitudCertificado){
						SolicitudCertificado solCertificadoTemp =  (SolicitudCertificado)sol;
						SolicitudFolio solFolioTemp = (SolicitudFolio)solCertificadoTemp.getSolicitudFolios().get(0);
						if(solFolioTemp.getIdMatricula().equals(fid.idMatricula)){
							yaImpreso = true;
							solCertificado = solCertificadoTemp;
							solFolio = solFolioTemp;
							break;
						}
					}
				}

				if(yaImpreso){
					//si ya se imprimio validar que no se haya extendido el numero de impresiones validas
					if(solCertificado.getNumImpresiones()>Integer.parseInt(hermod.getNumeroImpresionesCertificadosEnCorrecciones())){
					  	throw new CorreccionImpresionCertificadoException("La matricula  "+ solFolio.getIdMatricula() +" ya sobrepaso el numero de impresiones permitidas");
					}else{
					  	solCertificado.setNumImpresiones(solCertificado.getNumImpresiones()+1);
					  	hermod.updateSolicitudCertificado(solCertificado);
					}

				}else{
					SolicitudCertificado solCertificadoACrear = new SolicitudCertificado();
					solCertificadoACrear.setNumImpresiones(1);
					solCertificadoACrear.setUsuario(evento.getUsuarioSIR());
					solCertificadoACrear.setCirculo(solCorreccion.getCirculo());
					ProcesoPk pid = new ProcesoPk();
					pid.idProceso = Long.parseLong(CProceso.PROCESO_CERTIFICADOS);
					Proceso pro= hermod.getProcesoById(pid);
					solCertificadoACrear.setProceso(pro);
					solCertificadoACrear.setCiudadano(solCorreccion.getCiudadano());
					//crear la solicitudFolio de la matricula
					SolicitudFolio solFolioACrear= new SolicitudFolio();
					solFolioACrear.setFolio(folio);
					solCertificadoACrear.addSolicitudFolio(solFolioACrear);
					Solicitud sol = hermod.crearSolicitud(solCertificadoACrear);

					/*servicio de añadir solicittudCertificado a SolicitudHija.*/
					solCorreccion = (SolicitudCorreccion)hermod.addSolicitudHija(solCorreccion, sol);
					if(solCorreccion==null){
						throw new Exception("fallo en el servicio de añadir solicitud Hija");
					}

				}

				String fechaImpresion= this.getFechaImpresion();
//				obtener textos base de los separadores
				String tbase1 ="";
				String tbase2 = "";
				List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
				for(Iterator it= variablesImprimibles.iterator(); it.hasNext();){
					OPLookupCodes op = (OPLookupCodes) it.next();
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
						tbase1= op.getValor();
					}
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
						tbase2 = op.getValor();
					}
				}
				ImprimibleCertificado imprimibleCertificado = new ImprimibleCertificado(folio, turno, padres, hijos, foliosDerivadoHijos, fechaImpresion, CTipoImprimible.CERTIFICADO_CORRECCIONES,tbase1, tbase2);
				imprimibleCertificado.setPrintWatermarkEnabled(true);
				imprimibleCertificado.setImprimirUsuarioSalvedad(true);
                                /**
                                 * @author     : Carlos Torres
                                 * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                 */
                                Traslado traslado = hermod.getFolioDestinoTraslado(folio.getIdMatricula());
                                imprimibleCertificado.setInfoTraslado(traslado);
                                /**
                                 * @Autor: Edgar Lora
                                 * @Mantis: 0006493
                                 */
                                String text = turno.getIdWorkflow() +"/"+ turno.getSolicitud().getIdSolicitud();
                                byte [] key  = new byte [8];
                                key[0] = 5;
                                imprimibleCertificado.setNis(Encriptador.encriptar(text, key, "DES"));
                                PrintJobsProperties prop = PrintJobsProperties.getInstancia();
                                imprimibleCertificado.setIpverificacion(prop.getProperty(PrintJobsProperties.VERIFICACION_CERTIFICADO_IP));



                // datos para firmas -----------------
                CirculoPk cid = new CirculoPk();
                cid.idCirculo = turno.getIdCirculo();


                String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;

                FirmaRegistrador firmaRegistrador = null;

                String nombre = "";
                String archivo = "";
                String cargoToPrint = "";
                String rutaFisica = null;

                try
                {
                            firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);

                            if(firmaRegistrador==null){
                                    cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
                                    firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
                            }
                            if(firmaRegistrador==null){
                                    cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
                                    firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
                            }
                            if(firmaRegistrador==null){
                                    cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
                                    firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
                            }

                            rutaFisica =  hermod.getPathFirmasRegistradores();

                            nombre = firmaRegistrador.getNombreRegistrador();
                            archivo = firmaRegistrador.getIdArchivo();

                            //Se recupera el verdadero cargo para definir si es ENCARGADO o
                            //no lo es.
                            cargo = firmaRegistrador.getCargoRegistrador();

                            //Se saca el valor del cargo para imprimirlo en el certificado
                            List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

                            cargoToPrint = "";
                            OPLookupCodes lookUp;
                            for(Iterator it = cargos.iterator(); it.hasNext();){
                                    lookUp = (OPLookupCodes) it.next();
                                    if(lookUp.getCodigo().equals(cargo)){
                                            cargoToPrint = lookUp.getValor();
                                    }
                            }
                } catch(CorreccionImpresionCertificadoException e){
                	throw e;
                } catch(Throwable t)
                {
                    t.printStackTrace();
                }
                // fix data to paper

                if (nombre==null)
                  nombre="";


                imprimibleCertificado.setCargoRegistrador(cargoToPrint);
                imprimibleCertificado.setNombreRegistrador(nombre);


                if(rutaFisica!=null)
                {
                        BufferedImage imagenFirmaRegistrador=getImage(rutaFisica,archivo);

                        byte pixeles[]=null;
                        try
                        {
                                pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                        }
                        catch (Throwable e1) {

                                e1.printStackTrace();
                        }
                    imprimibleCertificado.setPixelesImagenFirmaRegistrador(pixeles);
                }


                // ----------------------------------------------


                imprimir( imprimibleCertificado, evento.getCirculoId(), evento.getImpresora());
			}

			//IMPRIMIR LOS CERTIFICADOS SOLICITADOS
			try {
				List noImpresos = imprimirCertificados(turno, turno.getSolicitud().getSolicitudFolios(), evento.getCirculoId(), evento.getImpresora());
				ev.setFoliosNoImpresos(noImpresos);
			} catch (Throwable t2) {
				t2.printStackTrace();
				throw new EventoException(t2.getMessage());
			}

		} catch(CorreccionImpresionCertificadoException e){
        	throw e;
        } catch (Throwable t2) {
			t2.printStackTrace();
			throw new EventoException(t2.getMessage());
		}

		return ev;
	}
	
    public static BufferedImage getImage(String path, String nombreArchivo)
    {
            String nombreCompleto = getNombreCompleto(path,nombreArchivo);
            BufferedImage buf = null;

            try
            {
                    File file = new File(nombreCompleto);
                    buf = ImageIO.read(file);
            }
            catch (IOException e)
            {
                    Log.getInstance().error(AnCorr_MesaControl.class,e);
                    Log.getInstance().debug(AnCorr_MesaControl.class,"Error imprimiendo el gráfico en la ruta: "+nombreCompleto);

            }

            return buf;
}

    public static String getNombreCompleto(String path, String nombreArchivo)
    {

            String nombreCompleto=null;

            if (!path.trim().equals(CHermod.CADENA_VACIA))
              nombreCompleto = path + nombreArchivo;
            else
              nombreCompleto = nombreArchivo;



      return nombreCompleto;
}




	//DEPURADO ENERO 17 2006
	private void imprimir(ImprimibleBase imprimible, String cid, String impresora) {

		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
		int maxIntentos;
		int espera;

		//INGRESO DE INTENTOS DE IMPRESION
		try {

			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null) {
				Integer intentos = new Integer(intentosImpresion);
				maxIntentos = intentos.intValue();
			} else {
				Integer intentosDefault =
					new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
				maxIntentos = intentosDefault.intValue();
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null) {
				Integer esperaInt = new Integer(esperaImpresion);
				espera = esperaInt.intValue();
			} else {
				Integer esperaDefault =
					new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}


		} catch (Throwable t) {
			Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();

		}

		Bundle bundle = new Bundle(imprimible);

		//INGRESO IMPRESORA SELECCIONADA
	    if(impresora!=null){
	       	bundle.setNombreImpresora(impresora);
	    }

		try {
			//se manda a imprimir el recibo por el identificador unico de usuario
			printJobs.enviar(cid, bundle, maxIntentos, espera);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	private List obtenerNumeroCopias(Turno turno, List folios) throws EventoException {

		List rtn = new ArrayList();
		for(int i = 0; i < folios.size(); i++){
				rtn.add(i, new Integer(-1));	// inicializado en -1
		}

		Turno turnoAnt = turno.getSolicitud().getTurnoAnterior();
		if (turnoAnt == null){
			return rtn;
		}

		try {
			turnoAnt = hermod.getTurnobyWF(turnoAnt.getIdWorkflow());
			if (turnoAnt == null) {
				throw new EventoException("No se pudo obtener la informacion del turno");
			}
		} catch (HermodException e) {
			throw new EventoException(
				"Hermod: El turno asociado no es válido" + e.getMessage(),
				e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(AnCorr_MesaControl.class,
				"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		Solicitud solAnt = turnoAnt.getSolicitud();

		if (solAnt instanceof SolicitudCertificado) {
			SolicitudCertificado solcert = (SolicitudCertificado) solAnt;
			List foliosAnt = solcert.getSolicitudFolios();
			int numImp = solcert.getNumeroCertificados();

			for(int i = 0; i < folios.size(); i++){
				for(int j = 0; j < foliosAnt.size(); j++){
					SolicitudFolio folioAnt = (SolicitudFolio) foliosAnt.get(j);
					SolicitudFolio folioActual = (SolicitudFolio) folios.get(i);
					if (folioAnt.getIdMatricula().equals(folioActual.getIdMatricula())){
						rtn.add(i, new Integer(numImp));
					}
				}
			}
		}

		if (solAnt instanceof SolicitudRegistro) {
			SolicitudRegistro solreg = (SolicitudRegistro) solAnt;
			List solAsociadas = solreg.getSolicitudesHijas();

			for(int k = 0; k < solAsociadas.size(); k++){
				SolicitudAsociada solAs = (SolicitudAsociada) solAsociadas.get(k);
				Solicitud solHija = solAs.getSolicitudHija();

				if (solHija instanceof SolicitudCertificado) {
					SolicitudCertificado solcert = (SolicitudCertificado) solHija;

					try{
						solcert = (SolicitudCertificado) hermod.getSolicitudById(solcert.getIdSolicitud());
					} catch (HermodException e) {
						throw new EventoException(
							"Hermod: La solicitud no es válida" + e.getMessage(),
							e);
					} catch (Throwable e) {
						ExceptionPrinter printer = new ExceptionPrinter(e);
						Log.getInstance().error(AnCorr_MesaControl.class,
							"Ha ocurrido una excepcion inesperada " + printer.toString());
						throw new EventoException(e.getMessage(), e);
					}

					List foliosAnt = solcert.getSolicitudFolios();
					int numImp = solcert.getNumeroCertificados();

					for(int i = 0; i < folios.size(); i++){
						for(int j = 0; j < foliosAnt.size(); j++){
							SolicitudFolio folioAnt = (SolicitudFolio) foliosAnt.get(j);
							SolicitudFolio folioActual = (SolicitudFolio) folios.get(i);
							if (folioAnt.getIdMatricula().equals(folioActual.getIdMatricula())){
								if ( ! rtn.get(i).equals(new Integer(-1)) ){
									String matricula = ((Folio) folios.get(i)).getIdMatricula();
									throw new EventoException("El folio " + matricula +
										" esta incluido en dos Solicitudes" +
										" de Certificados" +
										" Hijas de la Solicitud de registro Anterior");
								}
								rtn.add(i, new Integer(numImp));
							}
						}
					}
				}
			}
		}
		return rtn;
	}

	protected String getFechaImpresion()
	{

		Calendar c = Calendar.getInstance();
		int dia, ano, hora;
		String min, seg, mes;

		dia = c.get(Calendar.DAY_OF_MONTH);
		mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
		ano = c.get(Calendar.YEAR);

		hora = c.get(Calendar.HOUR_OF_DAY);
		if (hora > 12)
			hora -= 12;

		min = formato(c.get(Calendar.MINUTE));
		seg = formato(c.get(Calendar.SECOND));

		String fechaImp =
			"Impreso el "
				+ dia
				+ " de "
				+ mes
				+ " de "
				+ ano
				+ " a las "
				+ formato(hora)
				+ ":"
				+ min
				+ ":"
				+ seg
				+ " "
				+ DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

		return fechaImp;
	}

	/**
	 * Metodo que retorna un numero con un "0" antes en caso de ser menor
	 * que 10.
	 * @param i el numero.
	 * @return
	 */
	protected String formato(int i) {
		if (i < 10) {
			return "0" + (new Integer(i)).toString();
		}
        return (new Integer(i)).toString();
	}


	   private JXPathContext
	   jx_GetContext( Object local_ContextObject ) {

	      JXPathContext local_Context;
	      local_Context = JXPathContext.newContext( local_ContextObject );

	      if( local_ContextObject instanceof gov.sir.core.negocio.modelo.Folio ) {
	         // TODO: remove cohesion
	         local_Context.setFactory( new FolioAbstractFactory() );
	      } // if

	      local_Context.setLenient( true );

	      return local_Context;
	   } // end-method: jx_GetContext_Folio




















} // :class

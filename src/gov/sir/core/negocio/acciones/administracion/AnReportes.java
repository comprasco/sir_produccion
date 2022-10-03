package gov.sir.core.negocio.acciones.administracion;

import java.util.List;
 
import gov.sir.hermod.HermodException;
import gov.sir.core.eventos.administracion.EvnReportes;
import gov.sir.core.eventos.administracion.EvnRespReportes;
import gov.sir.core.negocio.acciones.excepciones.ANReportesException;
import gov.sir.core.negocio.acciones.excepciones.AdministracionReportesGenericCollectorException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.fenrir.FenrirException;
import gov.sir.fenrir.dao.impl.jdogenie.OracleFenrirJDO;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;

import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import gov.sir.core.negocio.modelo.MunicipioPk;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.core.negocio.modelo.RelacionPk;
import gov.sir.core.negocio.modelo.RepartoNotarial;
import gov.sir.core.negocio.modelo.RepartoNotarialPk;
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.eventos.administracion.EvnListaReportes;
import gov.sir.core.eventos.administracion.EvnRespListaReportes;
 /**
    * @author      :  Carlos Torres
    * @Caso Mantis :  14371: Acta - Requerimiento No 070_453_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
    */
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.forseti.ForsetiException;


/**
 * Acción de negocio encargada de manejar los eventos de tipo
 * <code>EvnAdministracionFenrir</code> destinados a manejar
 * la administración de los objetos del servicio Forseti
 *
 * @author jmendez
 */
public class AnReportes extends SoporteAccionNegocio {

	/** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Forseti  */
	private ForsetiServiceInterface forseti;

	/** Instancia de la intefaz de Fenrir  */
	private FenrirServiceInterface fenrir;

	/**
	 * Constructor encargado de inicializar los servicios a ser utilizados por la
	 * acción de Negocio
	 * @throws EventoException
	 */
	public AnReportes() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}

		try {
			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

			if (fenrir == null) {
				throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Fenrir");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
		}
	}

	/**
	 * Manejador de eventos de tipo <code>EvnAdministracionFenrir</code>.
	 * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
	 * al tipo de evento que llegue a la acción de negocio.  Este método redirige
	 * la acción a otros métodos en la clase de acuerdo al tipo de evento
	 * que llega como parámetro.
	 *
	 * @param evento <code>EvnAdministracionFenrir</code> evento con los parámetros
	 * de la acción a realizar utilizando los servicios disponibles en la clase.
	 *
	 * @return <code>EventoRespuesta</code> con la información resultante de la
	 * ejecución de la acción sobre los servicios
	 *
	 * @throws <code>EventoException</code>
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		gov.sir.core.eventos.comun.EvnSIR evento = (gov.sir.core.eventos.comun.EvnSIR) e;

		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}

                String tipoEvento;
                tipoEvento = evento.getTipoEvento();

                if( EvnReportes.CONSULTA_CIRCULOS_BY_USUARIO.equals(tipoEvento)){
                  return consultaCirculosByUsuario((EvnReportes)evento);
                }

                if( EvnReportes.DO_FINDRELATEDFASESBYRELACIONID_EVENT.equals( tipoEvento ) ) {
                        return buscarFasesRelacionadasPorRelacionId( (EvnReportes)evento );
                }
                if( EvnReportes.DO_GENERATERELACIONREPORT_EVENT.equals( tipoEvento ) ) {
                        return obtenerParametrosGeneracionReporte( (EvnReportes)evento );
                }

                if (EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO.equals(tipoEvento)) {
                    return consultaUsuariosPorCirculo((EvnReportes) evento);
                }
		if ( EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL.equals( tipoEvento ) ) {
			return consultaUsuariosPorCirculoRol( (EvnReportes)evento);
		}
		
		if ( EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROLES.equals( tipoEvento ) ) {
			return consultaUsuariosPorCirculoRoles( (EvnReportes)evento);
		}		

                if ( EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_REL02.equals( tipoEvento ) ) {
                        return consultaUsuariosPorCirculoRel02( (EvnReportes)evento);
                }
                if( EvnReportes.CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO.equals( tipoEvento ) ) {
                  return consultaOficinasOrigenByMunicipio( (EvnReportes) evento );
                }
                if( EvnReportes.CONSULTA_CIRCULOS_BY_USUARIO.equals( tipoEvento ) ) {
                  return consultaCirculosByUsuario( (EvnReportes) evento );
                }
                if( EvnReportes.VALIDAR_NUMERO_REPARTO.equals( tipoEvento ) ) {
                    return validarNumeroRepartoNotarial( (EvnReportes) evento );
                }                

                if( EvnListaReportes.PAGE_REGION__REPORTE00_FINDREPORTLIST__EVENT.equals( tipoEvento ) ) {
                  if( e instanceof EvnListaReportes ) {
                    EvnListaReportes local_Event;
                    local_Event = (EvnListaReportes)e;
                    return buscarListaReportesPorRolesUsuario( local_Event );
                  }
                }
                
		if ( EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL_CALIFICADOR.equals( tipoEvento ) ) {
			return consultaUsuariosPorCirculoRoles( (EvnReportes)evento);
		}/**
                 * @author: Edgar Lora
                 * @date: 2012-01-25
                 * @description:    se agregó manejo de nuevo evento para consultar los usuarios
                 *                  pertenecientes al rol de correcciones.
                 */
                if( EvnReportes.USUARIOS_CONSULTAR_POR_CIRCULO_PROCESO.equals( tipoEvento ) ) {
                    return consultaUsuariosPorCirculoRoles( (EvnReportes) evento );
                }


		return null;
	}



        private EvnRespListaReportes
        buscarListaReportesPorRolesUsuario( EvnListaReportes evento )
        throws EventoException {

            // in :: ----------------------

            // get data:
            // List <org.auriga.core.modelo.transferObjects.Rol>
            List local_Param_ListRolesUsuario = evento.getRolesList();

            // result objects
            List local_Result_ListPantallasVisibles = null;

            // ----------------------------

            // pr :: ----------------------

            try {
              /**
                * @author      :  Carlos Torres
                * @change      :  Obtener pantallas administrativas del usuario
                * @Caso Mantis :  14371: Acta - Requerimiento No 070_453_Rol_Registrador_Inhabilitar_Pantallas_Administrativas
                */
              org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();
              long id = fenrir.darIdUsuario(user.getUsuarioId());
              UsuarioPk uid = new UsuarioPk();
				uid.idUsuario = id;
				Usuario todo = fenrir.getUsuarioById(uid);
              local_Result_ListPantallasVisibles
                  = hermod.obtenerPantallasPaginaReportesPorRol( todo);
            }
            catch( Throwable t ) { // ?
              t.printStackTrace();
              throw new EventoException( "Error obteniendo lista de pantallas para el rol", t );
            }

            // ----------------------------


            // ou :: ----------------------

            EvnRespListaReportes local_Result;
            local_Result = new EvnRespListaReportes( EvnRespListaReportes.PAGE_REGION__REPORTE00_FINDREPORTLIST__EVENTRESP_OK );

            local_Result.setListPantallasVisibles( local_Result_ListPantallasVisibles );

            // ----------------------------

            return local_Result;

	} //buscarListaReportesPorRolesUsuario


	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
		 * Este método consulta la lista de objetos de tipo <code>Usuario</code>
		 * existentes en el sistema
		 *
		 * @param evento de tipo <code>EvnAdministracionFenrir</code> con la información
		 * del <code>Usuario</code> a ser consultado.
		 *
		 * @return <code>EvnRespAdministracionFenrir</code> con la lista  los usuarios
		 * existentes en el sistema.
		 *
		 * @throws <code>EventoException</code>
		 */
	private EvnRespReportes consultaUsuariosPorCirculoRol(EvnReportes evento)
		throws EventoException {

		List datos = null;

		try {
			datos = fenrir.consultarUsuariosPorCirculoRol(evento.getCirculo(), evento.getRol());
			if (datos == null) {
				throw new EventoException(
					"Ocurrió un error al consultar la lista de usuarios en el sistema",
					null);
			}

		} catch (FenrirException e) {
			Log.getInstance().error(AnReportes.class,"Ocurrió un error al consultar la lista de usuarios en el sistema", e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(AnReportes.class,"Ocurrió un error al consultar la lista de usuarios en el sistema", e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespReportes evRespuesta =
			new EvnRespReportes(
				datos,
				EvnRespReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL_OK);
		return evRespuesta;
	}
	
	private EvnRespReportes consultaUsuariosPorCirculoRoles(EvnReportes evento)throws EventoException {
	List datos = null;
	try {
		datos = fenrir.consultarUsuariosPorCirculoRoles(evento.getCirculo(), evento.getRoles());
		if (datos == null) {
			throw new EventoException(
				"Ocurrió un error al consultar la lista de usuarios en el sistema",
				null);
		}
	} catch (FenrirException e) {
		Log.getInstance().error(OracleFenrirJDO.class, "Ocurrió un error al consultar la lista de usuarios en el sistema", e);
		throw new EventoException(e.getMessage(), e);
	} catch (Throwable e) {
		Log.getInstance().error(OracleFenrirJDO.class, "Ocurrió un error al consultar la lista de usuarios en el sistema", e);
		throw new EventoException(e.getMessage(), e);
	}
	EvnRespReportes evRespuesta =new EvnRespReportes(datos,EvnRespReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL_OK);
	return evRespuesta;
}

        private EvnRespReportes consultaUsuariosPorCirculo(EvnReportes evento) throws EventoException {
            List datos = null;
            try {
                datos = fenrir.consultarUsuariosPorCirculo(evento.getCirculo());
                if (datos == null) {
                    throw new EventoException(
                            "Ocurrió un error al consultar la lista de usuarios en el sistema",
                            null);
                }
            } catch (FenrirException e) {
                Log.getInstance().error(OracleFenrirJDO.class, "Ocurrió un error al consultar la lista de usuarios en el sistema", e);
                throw new EventoException(e.getMessage(), e);
            } catch (Throwable e) {
                Log.getInstance().error(OracleFenrirJDO.class, "Ocurrió un error al consultar la lista de usuarios en el sistema", e);
                throw new EventoException(e.getMessage(), e);
            }
            EvnRespReportes evRespuesta = new EvnRespReportes(datos, EvnRespReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL_OK);
            return evRespuesta;
        }


        private EvnRespReportes
        consultaOficinasOrigenByMunicipio( EvnReportes evento )
        throws EventoException {



          // extraer entradas del evento
          gov.sir.core.negocio.modelo.Municipio municipioSeleccionado;
          municipioSeleccionado = evento.getMunicipioSeleccionado();

          // resultados
          //List< OficinaOrigen>
          List result_OficinasOrigenByMunicipio;



          // procesar

          MunicipioPk municipioID = new MunicipioPk();
          municipioID.idDepartamento = municipioSeleccionado.getIdDepartamento();
          municipioID.idMunicipio    = municipioSeleccionado.getIdMunicipio();

          try {
            result_OficinasOrigenByMunicipio = forseti.
                getOficinasOrigenByMunicipio(municipioID);
          }
          catch( ForsetiException e ) {
            Log.getInstance().error(AnReportes.class, "Ocurrió un error al consultar la lista de oficinas en el sistema", e );
            throw new EventoException( e.getMessage(), e );
          }
          catch( Throwable e ) {
            Log.getInstance().error(AnReportes.class, "Ocurrió un error al consultar la lista de oficinas en el sistema", e );
            throw new EventoException( e.getMessage(), e );
          }



          // regresar evento respuesta
          EvnRespReportes evRespuesta;
          evRespuesta = new EvnRespReportes( EvnRespReportes.CONSULTA_OFICINA_ORIGEN_BY_MUNICIPIO_EVENTRESPOK );
          evRespuesta.setOficinasOrigenByMunicipioList( result_OficinasOrigenByMunicipio );

          return evRespuesta;
        }




        private EvnRespReportes
        consultaCirculosByUsuario( EvnReportes evento )
        throws EventoException {

          // extraer entradas del evento
          gov.sir.core.negocio.modelo.Usuario usuarioSeleccionado;
          usuarioSeleccionado = evento.getUsuarioSir();

          // resultados
          //List< OficinaOrigen>
          List result_CirculosByUsuario;


          // procesar

          UsuarioPk usuarioID = new UsuarioPk();
          usuarioID.idUsuario = usuarioSeleccionado.getIdUsuario();

          try {
            result_CirculosByUsuario = forseti.getCirculosByUsuario(usuarioID);
          }
          catch( ForsetiException e ) {
            Log.getInstance().error(AnReportes.class, "Ocurrió un error al consultar los circulos para el usuario", e );
            throw new EventoException( e.getMessage(), e );
          }
          catch( Throwable e ) {
            Log.getInstance().error(AnReportes.class, "Ocurrió un error al consultar los circulos para el usuario", e );
            throw new EventoException( e.getMessage(), e );
          }

          // regresar evento respuesta
          EvnRespReportes evRespuesta;
          evRespuesta = new EvnRespReportes( EvnRespReportes.CONSULTA_CIRCULOS_BY_USUARIO_EVENTRESPOK );
          evRespuesta.setCirculosByUsuarioList( result_CirculosByUsuario );

          return evRespuesta;
        }

        
	private EvnRespReportes validarNumeroRepartoNotarial(EvnReportes evento) throws EventoException {

		// extraer entradas del evento
		String numReparto = evento.getNumeroRepartoNotarial();
		RepartoNotarial repartoNotarial = new RepartoNotarial();

		try {
			
			RepartoNotarialPk id = new RepartoNotarialPk();
			id.idRepartoNotarial = numReparto;
			System.out.println("NUMREPARTO" + numReparto);
			repartoNotarial = hermod.getRepartoNotarialById(id);
			System.out.println("NUMREPARTO" + numReparto);

		} catch (ForsetiException e) {
			Log.getInstance().error(AnReportes.class,"Ocurrió un error al consultar consultar el reparto notarial", e);
			throw new ANReportesException(e.getMessage(), e);
		} catch (Throwable e) {
			e.printStackTrace();
			Log.getInstance().error(AnReportes.class,"Ocurrió un error al consultar consultar el reparto notarial", e);
			throw new ANReportesException(e.getMessage(), e);
		}
		
		if(repartoNotarial==null || repartoNotarial.getIdRepartoNotarial() == null){
			throw new ANReportesException("El número de reparto " + numReparto + " es inválido");
		}
		
		EvnRespReportes evRespuesta;
		evRespuesta = new EvnRespReportes(repartoNotarial, EvnRespReportes.VALIDAR_NUMERO_REPARTO_NOTARIAL);

		return evRespuesta;
	}        

        


        // ////////////////////////////////////////////////////////////////
        // ////////////////////////////////////////////////////////////////
        /**
		 * Este método consulta la lista de objetos de tipo <code>Usuario</code>
		 * existentes en el sistema
		 * 
		 * @param evento
		 *            de tipo <code>EvnAdministracionFenrir</code> con la
		 *            información del <code>Usuario</code> a ser consultado.
		 * 
		 * @return <code>EvnRespAdministracionFenrir</code> con la lista los
		 *         usuarios existentes en el sistema.
		 * 
		 * @throws <code>EventoException</code>
		 */
        private EvnRespReportes consultaUsuariosPorCirculoRel02(EvnReportes evento)
                throws EventoException {

                List datos = null;

                try {
                        datos = fenrir.consultarUsuariosPorCirculo(evento.getCirculo());
                        if (datos == null) {
                                throw new EventoException(
                                        "Ocurrió un error al consultar la lista de usuarios en el sistema",
                                        null);
                        }

                } catch (FenrirException e) {
                        Log.getInstance().error(AnReportes.class,"Ocurrió un error al consultar la lista de usuarios en el sistema", e);
                        throw new EventoException(e.getMessage(), e);
                } catch (Throwable e) {
                        Log.getInstance().error(AnReportes.class,"Ocurrió un error al consultar la lista de usuarios en el sistema", e);
                        throw new EventoException(e.getMessage(), e);
                }

                EvnRespReportes evRespuesta =
                        new EvnRespReportes(
                                datos,
                                EvnRespReportes.USUARIOS_CONSULTAR_POR_CIRCULO_ROL_OK);
                return evRespuesta;
        }


        private EvnRespReportes
        buscarFasesRelacionadasPorRelacionId( EvnReportes evento )
        throws EventoException {


                List datos = null;
                // List< Fase >

                String relacionId = evento.getRelacionId();

                try {


                  /*
                  FaseProceso defaultElement = new FaseProceso();
                  defaultElement.setIdFase("REG_CONFRONTAR");
                  defaultElement.setIdProceso("CORRECCIONES");
                  defaultElement.setNombre("FASE DE  PEPITO");

                  datos.add( defaultElement );
                  */

                  // TODO
                  //
                  
                  datos = hermod.buscarFasesRelacionadasPorRelacionId( relacionId );

                    if (datos == null) {

                      throw new EventoException(
                                    "Ocurrió un error al consultar la lista de fases relacionadas en el sistema",
                                    null);
                    }
                    
                }
                catch( HermodException e ) {
                        Log.getInstance().error(AnReportes.class,"Ocurrió un error al consultar la lista de fases relacionadas en el sistema", e);
                        throw new EventoException(e.getMessage(), e);
                }
                catch( Throwable e ) {
                        Log.getInstance().error(AnReportes.class,"Ocurrió un error al consultar la lista de fases relacionadas en el sistema", e);
                        throw new EventoException(e.getMessage(), e);
                }
                
                if( datos.size() == 0 ) {
                	AdministracionReportesGenericCollectorException local_Exception;
                	local_Exception = new AdministracionReportesGenericCollectorException();
                	local_Exception.addError(  "No se han encontrado items con este indentificador de relacion." );
                    throw local_Exception;
                	
                } // if 



                

                EvnRespReportes evRespuesta =
                        new EvnRespReportes(
                                datos,
                                EvnRespReportes.DO_FINDRELATEDFASESBYRELACIONID_EVENTRESP_OK );
                return evRespuesta;
	}



         private EvnRespReportes
         obtenerParametrosGeneracionReporte( EvnReportes evento )
         throws EventoException {

                 String relacionId = evento.getRelacionId();
                 String faseId = evento.getFaseId();

                 Relacion relacion = null;

                 try {
                         RelacionPk idRelacion = new RelacionPk();
                         idRelacion.idRelacion = relacionId;
                         idRelacion.idFase = faseId;

                         relacion = hermod.getRelacion(idRelacion);


                         if( null == relacion.getTipoRelacion() ) {
                           throw new Exception( "No se puede enviar a generar este reporte porque no se conoce el tipo de relacion. (" + relacion.getIdRelacion() + ")." );
                         } // if

                 }
                 catch(Throwable th) {
                         th.printStackTrace();
                         throw new EventoException("Error al imprimir la relación: " + th.getMessage());
                 }

                 EvnRespReportes evRespuesta;
                 evRespuesta =
                         new EvnRespReportes(
                                 null,
                                 EvnRespReportes.DO_GENERATERELACIONREPORT_EVENTRESP_OK );
                 evRespuesta.setRelacion( relacion );
                 return evRespuesta;
	}



}

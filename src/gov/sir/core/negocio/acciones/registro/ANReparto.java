package gov.sir.core.negocio.acciones.registro;

import gov.sir.core.eventos.registro.EvnReparto;
import gov.sir.core.eventos.registro.EvnRespReparto;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoReparto;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.core.negocio.modelo.Reparto;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudVinculada;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.TipoRelacion;
import gov.sir.core.negocio.modelo.TipoRelacionPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSubtipoAtencion;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CTipoRelacion;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.gdocumental.integracion.PublisherIntegracion;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
/**
 * Esta accion de negocio se encarga de realizar el reparto de turnos de registro a abogados.
 * Devuelve en un evento de respuesta el resultado del proceso de reparto.
 * Aqui se colocarán los métodos que permitan hacer consultas sobre un proceso de reparto
 * especifico.
 *
 * @author Ing. Diego Cantor. 
 * @author Ing. Fabián Ceballos
 */
public class ANReparto extends SoporteAccionNegocio {
    /**
     * Instancia de fenrir
     */
    private FenrirServiceInterface fenrir;
    /**
     * Instancia de Hermod
     */
    private HermodServiceInterface hermod;
    /**
     * Instancia de Forseti
     */
    private ForsetiServiceInterface forseti;
    /**
     * Instancia del service locator
     */
    private ServiceLocator service = null;
    
    /**
     * Número máximo de turnos por abogado
     */
    private long turnosMaximosPorAbogado = 9999;

    /*
     * @author     :   Julio Alcazar Rivas
     * @change     :   Se define el Set ListaSubtipos como HashSet que guarda los subtipos que no tiene calificadores asignados
     * Caso Mantis :   0005012
     */
    private Set ListaSubtipos;
    
    /**
     * Constructor de la clase ANSReparto
     * Es el encargado de invocar al Service Locator y obtener
     * las instancias de los servicios requeridos para que esta accion realice su trabajo.
     */
    public ANReparto() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {
			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");
			
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");


		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod", e);
		}
		

		if (fenrir == null) {
			throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}
    }
    
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnReparto evento = (EvnReparto)e;
        if ((evento == null) || (evento.getTipoEvento() == null)) {
            return null;
        }
        if (evento.getTipoEvento().equals(EvnReparto.CREAR_REPARTO)) {
            return crearReparto(evento);
        }
        else   if (evento.getTipoEvento().equals(EvnReparto.CREAR_REPARTO_RANGO)) {
			return crearReparto(evento);
		}
        return null;
    }
    
    
    /**
     * 
     * @param evento
     * @return
     * @throws EventoException
     */
    private  EvnRespReparto crearReparto(EvnReparto evento) throws EventoException {
    	
        Estacion estacion = evento.getEstacion();
        
        List turnosRepartir = evento.getTurnosRepartir();

        /*
         * @author     :   Julio Alcazar Rivas
         * @change     :   Se define el Set ListaSubtipos como HashSet que guarda los subtipos que no tiene calificadores asignados
         * Caso Mantis :   0005012
         */
        ListaSubtipos = new HashSet();
        
        //long numDesde = evento.getTurnoDesde();
        //long numHasta = evento.getTurnoHasta();
        
        boolean porRango = (evento.getTipoEvento().equals(EvnReparto.CREAR_REPARTO_RANGO));
        
        if (estacion == null) {
            throw new EventoException("No se indico la estacion que contiene los turnos a repartir");
        }
        
        Circulo c = null;
        //List abogadosAll = null;
        List abogadosReparto = null;
        Map mapUsuarioTurnos = new HashMap();
        Map mapUsuarioEstacion = new HashMap();
        List observaciones = new ArrayList();
        
        
        try {
        	
        	//Se obtiene el usuario sir a partir del usuario de auriga
        	Usuario usuarioGenerador = fenrir.getUsuario(evento.getUsuario());
        	
        	//Se captura la variable máxima de reparto por abogado:
			String tMax = hermod.getValor(COPLookupTypes.REGISTRO_TYPE, COPLookupCodes.MAXIMO_TURNOS_POR_CALIFICADOR_CODE);
			if(tMax!=null){
				turnosMaximosPorAbogado = Long.parseLong(tMax);
			}
			
			
            
            // Se crea un objeto de ROL abogado
			Rol rolAbogado = new Rol();
			rolAbogado.setRolId(CRol.ROL_ABOGADO);

            // Se Recupera el circulo asociado a la estacion de reparto
            c = fenrir.darCirculoEstacion(estacion);
            
            
            
            //1. Se selecciona y se rotan los abogados que están asociados al círculo
            Map mapSubtipoUsuarios = hermod.getUsuariosPorSubtiposDeAtencionRotados(c);
            
            //Bug 4740: Solo se deben usar abogados con cuyas estaciones esten activas
            Map mapSubtipoUsuariosToClean = new HashMap();
            Iterator llavesToClean = mapSubtipoUsuarios.keySet().iterator();
			
			while(llavesToClean.hasNext())
			{
				  SubtipoAtencion subtipo = (SubtipoAtencion)llavesToClean.next();
				  List abogados = (List)mapSubtipoUsuarios.get(subtipo);
				  List abogadosAux = new ArrayList();
				  Iterator tr = abogados.iterator();
				  while(tr.hasNext())
				  {
					  Usuario usu = (Usuario)tr.next();
					  if (fenrir.getEstacionByEstado(usu, rolAbogado, true) != null)
					  {
						  abogadosAux.add(usu);
					  }
				  }
				  mapSubtipoUsuariosToClean.put(subtipo ,abogadosAux);
			}
			
			mapSubtipoUsuarios = mapSubtipoUsuariosToClean;
            
           
            
            //2. Se determina para cada funcionario la estacion a la cual se va a efectuar el reparto.
       		abogadosReparto = new ArrayList();

			//2.1 Recorremos el mapa para sacar todos los usuarios y determinar su estación
			Iterator llaves = mapSubtipoUsuarios.keySet().iterator();
			while(llaves.hasNext()){
				  SubtipoAtencion subtipo = (SubtipoAtencion)llaves.next();
				  List abogados = (List)mapSubtipoUsuarios.get(subtipo);
				  Iterator tr = abogados.iterator();
				  while(tr.hasNext()){
					  Usuario usu = (Usuario)tr.next();
					  if (fenrir.getEstacionByEstado(usu, rolAbogado, true) != null)
					  {
						  abogadosReparto.add(usu);
						  
					  }
						  
				  }
			  }
       		
			
			
       		//2.2 Se recorre la lista de todos los usuarios para determinar su estación
            Iterator i = abogadosReparto.iterator();
            while (i.hasNext()) {
                Usuario u = (Usuario)i.next();
                //2.2.1 Consultar las estaciones de un usuario
                long id = fenrir.darIdUsuario(u.getUsername());
                List estaciones =  fenrir.getEstacionesUsuarioByEstadoRol(u, rolAbogado, true);
                
                if(estaciones == null || estaciones.isEmpty()){
                	throw new EventoException("No existe una estación para el usuario "+u.getUsername());
                }
                
                //Busca la estacion para asignarla al turno.
                int flag = 0;
                Estacion estacionParaAsignar = null;
                for (int j = 0 ; j < estaciones.size() && flag == 0; j++ ) 
                {
                	estacionParaAsignar = (Estacion) estaciones.get(j);
                	if (estacionParaAsignar.getEstacionId().equals("X-"+u.getUsername())) {
                		flag = 1;
                		
                	}
                }
                
                if (flag == 0) {
                	/**
                        * @author Daniel Forero
                        * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
                        */
                        estacionParaAsignar = fenrir.getEstacionByEstado(u, rolAbogado, true);
                }
                
                //e =  fenrir.getEstacionByEstado(u, rolAbogado, true);
                
                //if (e != null){
                	mapUsuarioEstacion.put(u, estacionParaAsignar);
                	//logger.debug("@@@e: "+e.getEstacionId());
                //}
                	
            }
            
                        
            /*3. Recuperar la lista de turnos que se encuentran en la estacion asociada al
             evento de reparto  se deben recuperar solo los turnos que se encuentren en la
             actividad REG_REPARTO*/
            Rol rolReparto = new Rol();               rolReparto.setRolId("SIR_ROL_REPARTO");
            Proceso procesoRegistro = new Proceso();  procesoRegistro.setIdProceso(Long.parseLong(CProceso.PROCESO_REGISTRO));
            Fase faseReparto = new Fase();            faseReparto.setID("REG_REPARTO");
            Usuario usuarioReparto = new Usuario();
            usuarioReparto.setUsername(evento.getUsuario().getUsuarioId());
            List turnos = hermod.getTurnos(estacion,rolReparto, usuarioReparto, procesoRegistro, faseReparto, evento.getCirculo());
            List turnosSirMig = hermod.getTurnosSirMig(estacion,rolReparto, usuarioReparto, procesoRegistro, faseReparto, evento.getCirculo());
            List turnosTempInicial = new ArrayList();
            List turnosTemp = new ArrayList();
            
            //Se dejan unicamente los turnos que vienen de la lista completa Sea de Rango o no
            Iterator itValidacion = turnos.iterator();
            while(itValidacion.hasNext()){
            	Turno turno = (Turno)itValidacion.next();
            	if(turnosRepartir.contains(turno.getIdWorkflow())){
            		turnosTempInicial.add(turno);
            	}
            }
            
            //Se dejan en la lista únicamente los turnos que están sin folios por migrar.
            Iterator itValidacionSirMig = turnosTempInicial.iterator();
            while(itValidacionSirMig.hasNext()){
            	Turno turno = (Turno)itValidacionSirMig.next();
            	if(!turnosSirMig.contains(turno.getIdWorkflow())){
            		turnosTemp.add(turno);
            	}
            }
            turnos = turnosTemp;
            
            //3.1 Determinar las nuevas entradas y los turnos nuevos y si se estableció un rango
            //    sólo habilitar para reparto las del rango indicado, y si se pasa del máximo
            //    número de turnos a repartir no seguir añadiendo turnos a las listas
            List allTurnos = new ArrayList();
            List turnosAntiguos = new ArrayList();
            List turnosNuevos = new ArrayList();
            Iterator it = turnos.iterator();
            boolean insertarTurno;
            
			int numeroTurnosRepartidos = 0;
            while(it.hasNext()){
                Turno t = (Turno)it.next();
                if(t.getAnulado()== null || !t.getAnulado().equals(CTurno.TURNO_ANULADO)){
	                insertarTurno = true;
	                if(insertarTurno){
						numeroTurnosRepartidos++;
						TurnoPk TID = new TurnoPk();
						TID.anio = t.getAnio();
						TID.idCirculo = t.getIdCirculo();
						TID.idProceso = t.getIdProceso();
						TID.idTurno = t.getIdTurno();
						//Se refresca el subtipo de atención en caso de que haya habido cambios en confrontación o en otras
						//fases posteriores a liquidación y anteriores a reparto
						hermod.refrescarSubtipoAtencionTurno(TID);
						
						Turno t2 = hermod.getTurno(TID); //Se consulta el turno en hermod para recuperar todas sus dependencias
						allTurnos.add(t2);
						if (t2.getSolicitud().getTurnoAnterior()!=null){
							turnosAntiguos.add(t2); //nueva entrada
						}
						else{
							turnosNuevos.add(t2); //primera entrada
						}
	                }
                }

            }
            
                        
            //5. Calcular promedios por subtipo de atención:
            Map mapSubtipoPromedio = this.calcularPromedioPorSubtipoAtencion(allTurnos, mapSubtipoUsuarios);
            
            //6. Repartir segun reglas de negocio
            
            //6.1 Repartir nuevas entradas
            Iterator itTurnosAntiguos = turnosAntiguos.iterator();
            while(itTurnosAntiguos.hasNext()){
                Turno t = (Turno)itTurnosAntiguos.next();
                
                //Bug: 3778 Se revisa primero si el abogado al que se le hizo el reparto anterior
                //          puede atender el turno antes de repartir por lista rotada si el turno
                //			es de reglamentos
                
                
                //6.1.1 Si el turno es de reglamentos o copias se pasa a repartir siguiendo
                //      el orden por subtipo de atención (rotación)
				if(t.getSolicitud()==null){
					throw new EventoException("El turno "+t.getIdWorkflow()+" NO tiene solicitud");
				}
				SolicitudRegistro sr = (SolicitudRegistro) t.getSolicitud();
				if(sr.getSubtipoAtencion()==null){
					throw new EventoException("El turno "+t.getIdWorkflow()+" NO tiene subtipo de atención");
				}
				

				//else{
					//6.1.1.2 No es de tipo reglamentos o copia, se obtiene el abogado del turno anterior
				Turno t2 = t.getSolicitud().getTurnoAnterior();
				Turno ta = hermod.getTurnobyWF(t2.getIdWorkflow());
            	
            	//6.1.1.3 Se valida que el subtipo de atención del turno anterior sea igual al del turno actual
				if(ta.getSolicitud()==null){
					throw new EventoException("El turno anterior"+t.getIdWorkflow()+" NO tiene solicitud");
				}
				SolicitudRegistro sra = (SolicitudRegistro) t.getSolicitud();
				if(sra.getSubtipoAtencion()==null){
					throw new EventoException("El turno anterior"+t.getIdWorkflow()+" NO tiene subtipo de atención");
				}
			
				if(!sra.getSubtipoAtencion().equals(sr.getSubtipoAtencion())){
					 //6.1.1.4 Si el turno anterior no es del mismo subtipo de atención, se reparte con las
					 //		   reglas de la lista rotada 
					 this.repartirPorListaRotada(t, mapSubtipoUsuarios, mapSubtipoPromedio, mapUsuarioTurnos);
				}
				else{
					//6.1.1.5 Obtener el abogado que atendio el turno anterior
					TurnoPk ID_ANTERIOR = new TurnoPk(ta.getIdWorkflow());
					Reparto reparto = hermod.getLastReparto(ID_ANTERIOR);
					
					if(reparto==null){
						//No encontró un reparto asociado con el turno anterior
						
						this.repartirPorListaRotada(t, mapSubtipoUsuarios, mapSubtipoPromedio, mapUsuarioTurnos);
					}
					else{
						Usuario abogadoAnterior = reparto.getUsuario();
						
						if(this.abogadoPuedeAtender(abogadoAnterior, t, mapSubtipoUsuarios)){
							//6.1.1.6 Se da el turno al abogado que atendió el turnio anterior
							this.darTurno(abogadoAnterior, t, mapUsuarioTurnos, mapSubtipoUsuarios);
						}
						else{
							//6.1.1.6 Si el abogado del anterior turno no puede atender el subtipo de atención, 
							//        se revisan los bloqueos:
							
							boolean mirarUsuarioCalificacion = true;
							if(!t.getSolicitud().getSolicitudFolios().isEmpty()){
								FolioPk fid = new FolioPk();
								fid.idMatricula = ((SolicitudFolio)t.getSolicitud().getSolicitudFolios().get(0)).getIdMatricula();
								Usuario usuBloqueo = forseti.getBloqueoFolio(fid);
								if(usuBloqueo!=null){
									//Si es folio está bloqueado se revisa el usuario y se está entre la lista
									//de usuarios del subtipo de atención
									if(this.abogadoPuedeAtender(usuBloqueo, t, mapSubtipoUsuarios)){
										this.darTurno(usuBloqueo, t, mapUsuarioTurnos, mapSubtipoUsuarios);
										mirarUsuarioCalificacion = false;
									}
								}
							
								if(mirarUsuarioCalificacion){
									//Si el turno no fue asignado por bloqueos se mira si ya lo tiene un usuario en
									//calificacion
									Usuario usCalificacion = hermod.getUsuarioConTurnoEnCalificacionConFolioAsociado(fid);
									if(usCalificacion!=null){
										if(this.abogadoPuedeAtender(usCalificacion, t, mapSubtipoUsuarios)){
											//El abogado puede atender el turno
											this.darTurno(usCalificacion, t, mapUsuarioTurnos, mapSubtipoUsuarios);
										}
										else{
											//El abogado No puede atender el turno
											this.repartirPorListaRotada(t, mapSubtipoUsuarios, mapSubtipoPromedio, mapUsuarioTurnos);
										}
									}else{
										//El usuario no tiene turnos repartidos en calificación para la misma matrícula
										this.repartirPorListaRotada(t, mapSubtipoUsuarios, mapSubtipoPromedio, mapUsuarioTurnos);
									}
									
								}
							}
							else{
								//El turno no tiene folios asociados, se reparte por lista rotada:
								this.repartirPorListaRotada(t, mapSubtipoUsuarios, mapSubtipoPromedio, mapUsuarioTurnos);
							}
						}
					}
				}
				//}
            }
            
                        
            //6.2 Repartir turnos nuevos
            Iterator itTurnosNuevos = turnosNuevos.iterator();
            while(itTurnosNuevos.hasNext()){
                Turno t = (Turno)itTurnosNuevos.next();
                               
				//6.2.1 Si el turno es de reglamentos o copias se pasa a repartir siguiendo
				//      el orden por subtipo de atención (rotación)
				if(t.getSolicitud()==null){
					throw new EventoException("El turno "+t.getIdWorkflow()+" NO tiene solicitud");
				}
				SolicitudRegistro sr = (SolicitudRegistro) t.getSolicitud();
				if(sr.getSubtipoAtencion()==null){
					throw new EventoException("El turno "+t.getIdWorkflow()+" NO tiene subtipo de atención");
				}
				
				
				if(!t.getSolicitud().getSolicitudesVinculadas().isEmpty()){
					//TIENE TURNO VINCULADO
					SolicitudVinculada sv = (SolicitudVinculada)t.getSolicitud().getSolicitudesVinculadas().get(0);
					SolicitudPk solID = new SolicitudPk();
					solID.idSolicitud = sv.getIdSolicitud();
					Turno turnoVinculado = hermod.getTurnoBySolicitud(solID);
					if(turnoVinculado!=null){
						Usuario usuarioTurnoVinculado = this.getUsuarioAsignadoATurno(turnoVinculado, mapUsuarioTurnos);
						if(usuarioTurnoVinculado!=null){
							if(this.abogadoPuedeAtender(usuarioTurnoVinculado, t, mapSubtipoUsuarios)){
								this.darTurno(usuarioTurnoVinculado, t, mapUsuarioTurnos, mapSubtipoUsuarios);
								continue;
							}
						}
					}
				}
				
				
				if((sr.getSubtipoAtencion().getIdSubtipoAtencion().equals(CSubtipoAtencion.SUBTIPO_REGLAMENTO_ID)||sr.getSubtipoAtencion().getIdSubtipoAtencion().equals(CSubtipoAtencion.SUBTIPO_COPIA_ID))){
					//6.2.1.1 En turno es de tipo reglamento o copia, se reparte con las reglas de la lista rotada
					
					this.repartirPorListaRotada(t, mapSubtipoUsuarios, mapSubtipoPromedio, mapUsuarioTurnos);
				}
				else{
					//6.2.1.2 En turno no es de tipo reglamento ni copia, se revisa si existe un turno repartido
					//        que tenga la misma matrícula
					
					Usuario abogadoAsignadoAlMismoFolio = this.getUsuarioAsignadoAlMismoFolio(t, mapUsuarioTurnos, mapSubtipoUsuarios);
					
					if(abogadoAsignadoAlMismoFolio!=null){
						//Se reparte el turno al abogado asignado a algún turno anterior asignado que tenga
						//el mismo subtipo de atención del turno que está siendo repartido
						
						this.darTurno(abogadoAsignadoAlMismoFolio, t, mapUsuarioTurnos, mapSubtipoUsuarios);
					}
					else{
						
						//6.2.1.3 Si no existe una matrícula compartida se revisan lo bloqueos:
						boolean mirarUsuarioCalificacion = true;
						if(!t.getSolicitud().getSolicitudFolios().isEmpty()){
							
							FolioPk fid = new FolioPk();
							fid.idMatricula = ((SolicitudFolio)t.getSolicitud().getSolicitudFolios().get(0)).getIdMatricula();
							Usuario usuBloqueo = forseti.getBloqueoFolio(fid);
							if(usuBloqueo!=null){
								
								//Si es folio está bloqueado se revisa el usuario y se está entre la lista
								//de usuarios del subtipo de atención
								if(this.abogadoPuedeAtender(usuBloqueo, t, mapSubtipoUsuarios)){
									this.darTurno(usuBloqueo, t, mapUsuarioTurnos, mapSubtipoUsuarios);
									mirarUsuarioCalificacion = false;
								}
							}
							
							
							if(mirarUsuarioCalificacion){
								
								//Si el turno no fue asignado por bloqueos se mira si ya lo tiene un usuario en
								//calificacion
								Usuario usCalificacion = hermod.getUsuarioConTurnoEnCalificacionConFolioAsociado(fid);
								
								if(usCalificacion!=null){
									if(this.abogadoPuedeAtender(usCalificacion, t, mapSubtipoUsuarios)){
										//El abogado puede atender el turno
										
										this.darTurno(usCalificacion, t, mapUsuarioTurnos, mapSubtipoUsuarios);
									}
									else{
										//El abogado No puede atender el turno
										
										this.repartirPorListaRotada(t, mapSubtipoUsuarios, mapSubtipoPromedio, mapUsuarioTurnos);
									}
								}else{
									
									//El usuario no tiene turnos repartidos en calificación para la misma matrícula
									this.repartirPorListaRotada(t, mapSubtipoUsuarios, mapSubtipoPromedio, mapUsuarioTurnos);
								}
									
							}
						}
						else{
							
							//El turno no tiene folios asociados, se reparte por lista rotada:
							this.repartirPorListaRotada(t, mapSubtipoUsuarios, mapSubtipoPromedio, mapUsuarioTurnos);
						}
						
					}
					
				}
            }


            /*
             * @author     :   Julio Alcazar Rivas
             * @change     :   Se lanza una excepcion de los tipos de atencion que no tienen calificador agrupados en el HashSet ListaSubtipos
             * Caso Mantis :   0005012
             */
            if (!ListaSubtipos.isEmpty()){
                    Iterator ite = ListaSubtipos.iterator();
                    String subtipos = " ";
                    while (ite.hasNext()){
                        if (!" ".equals(subtipos)){
                           subtipos += ", ";
                        }
                        subtipos += (String) ite.next();
                    }
                    throw new EventoException("Los subtipos de atencion"+subtipos+" NO tiene calificadores con estaciones disponibles, se debe habilitar por lo menos un calificador con su respectiva estación");

                }
            
            
            //7. Si no hubo ningun problema, entonces crear el proceso de reparto e informar a hermod la lista de asociaciones
            //8. Crear el proceso de reparto en el modelo operativo
            ProcesoReparto pr = new ProcesoReparto();
            pr.setCirculo(c);
            //List repartos = new ArrayList();
            llaves = mapUsuarioTurnos.keySet().iterator();
            while(llaves.hasNext()){
                Usuario abogado = (Usuario)llaves.next();
                List turnosRepartidos = (List)mapUsuarioTurnos.get(abogado);
                Iterator tr = turnosRepartidos.iterator();
                while(tr.hasNext()){
                    Turno turno = (Turno)tr.next();
                    Reparto reparto = new Reparto();
                    reparto.setUsuario(abogado);
                    reparto.setTurno(turno);
                    reparto.setFechaCreacion(new Date());
                    reparto.setIdProceso(Long.parseLong(CProceso.PROCESO_REGISTRO));
                    reparto.setProcesoReparto(pr);
                    pr.addReparto(reparto);
                    
                }
            }
            ProcesoReparto procesoReparto = hermod.addProcesoReparto(pr);
            
                       
            //8b. Se crean las relaciones para el reparto
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = evento.getUsuario();
            Usuario usuario = fenrir.getUsuario(usuarioAuriga);
            
            TipoRelacionPk idTipoRelacion = new TipoRelacionPk();
            idTipoRelacion.idTipoRelacion = CTipoRelacion.ID_REPARTO_A_CALIFICACION;
            TipoRelacion tipoRelacion = hermod.getTipoRelacion(idTipoRelacion);
            
            Map mapRelacionesUsuario = new HashMap();
            
            for(llaves = mapUsuarioTurnos.keySet().iterator(); llaves.hasNext();) {
                Usuario abogado = (Usuario)llaves.next();
                List turnosRepartidos = (List)mapUsuarioTurnos.get(abogado);
                Relacion relacion = hermod.crearRelacion(tipoRelacion, usuario, c, turnosRepartidos);
                mapRelacionesUsuario.put(abogado, relacion);
            }
            
            
            //9. Avanzar cada uno de los turnos
            llaves = mapUsuarioTurnos.keySet().iterator();
            Map mapUsuarioEstacionRepartidos = new HashMap();
            while(llaves.hasNext()){
                Usuario abogado = (Usuario)llaves.next();
                List turnosRepartidos = (List)mapUsuarioTurnos.get(abogado);
                Iterator tr = turnosRepartidos.iterator();
                while(tr.hasNext()) {
                    Turno turno = (Turno)tr.next();
                    Hashtable parametros = new Hashtable();
                    Estacion e = (Estacion)mapUsuarioEstacion.get(abogado);
                    //Actualizamos la lista de estaciones por usuario, esto se hace para
                    //NO poner registros de usuarios NO asignados
					mapUsuarioEstacionRepartidos.put(abogado, e);
					
					
					/**********************************************************/
					/*            ACTUALIZACION ELIMINAR SAS                  */
					/**********************************************************/
					
					
                    //parametros.put(SASKeys.SAS_ESTACION,e.getEstacionId());
                    //String USUARIO_INICIADOR = usuarioReparto.getUsername();
                    //if( "".equals( USUARIO_INICIADOR ) || null == USUARIO_INICIADOR )
                    //    throw new TurnoNoAvanzadoException( "El usuario no se ha regostrado en la capa web." + this.getClass().getName() );
            		
                    //parametros.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);		
                    //logger.debug("Avanzando turno "+turno.getIdWorkflow()+" a la estacion "+e.getEstacionId());
                    //hermod.avanzarTurno(turno,faseReparto,parametros,CAvanzarTurno.AVANZAR_NORMAL);
                    
                        Fase faseRepartoAvance = new Fase();            
                        faseRepartoAvance.setID("REG_REPARTO");

        		      	Hashtable tablaAvanzar = new Hashtable();
        				tablaAvanzar.put(Processor.RESULT,CRespuesta.CONFIRMAR);
        				tablaAvanzar.put(Processor.ESTACION,e.getEstacionId());
                        
        				try 
        				{
        					hermod.avanzarTurnoNuevoNormal(turno,faseRepartoAvance,tablaAvanzar,usuarioGenerador);
 						  /**
							* @author Fernando Padilla Velez
							* @change Mantis 5383 Acta - Requerimiento No 200 - Reparto Sir SGD ETB2,
							*         Se agrega esta fragmento de codigo para la publicacion del mensaje del reparto.
							*/
							PublisherIntegracion  clienteJMS = new PublisherIntegracion();
							clienteJMS.setUsuario(abogado.getUsername());
							clienteJMS.setTurno(turno);
							clienteJMS.enviarReparto();
        				
        				}
        		        catch (Throwable exception) {
        				    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
        			    }
                        
                }
            }
            
            //10. Devolver la informacion de reparto
            EvnRespReparto respuesta = new EvnRespReparto();
            respuesta.setAbogadoTurno(mapUsuarioTurnos);
            respuesta.setAbogadoEstacion(mapUsuarioEstacionRepartidos);
            respuesta.setIdProcesoReparto(procesoReparto.getIdProcesoReparto());
            respuesta.setObservaciones(observaciones);
            respuesta.setRelacionesUsuario(mapRelacionesUsuario);
            
            return respuesta;
            
        }
        catch (Throwable t) {
             Log.getInstance().fatal(ANReparto.class,t);
             /*
             * @author     :   Henry Gómez Rocha
             * @change     :   Se modifica el número de parametros enviados para evitar que se imprima dos veces
                               el mismo mensaje.
             * Caso Mantis :   0005012
             */
             throw new EventoException("No se pudo hacer el reparto: " + t.getMessage());
        }
    }
    
	/**
	 * Dado un turno devuelve el usuario al cuál fue asignado, si el turno no está asignado a un
	 * usuario en este reparto devuelve null
	 * @param turnoVinculado
	 * @param mapUsuarioTurnos
	 * @return
	 */
	private Usuario getUsuarioAsignadoATurno(Turno turnoVinculado, Map mapUsuarioTurnos) {
		Usuario rta = null;
		
		//Se recorre las listas de turnos asignados por usuario
		//hasta que se encuentre el turno especificado
		Iterator llaves = mapUsuarioTurnos.keySet().iterator();
		boolean found = false;
		while(llaves.hasNext() && !found){
			Usuario usuario = (Usuario)llaves.next();
			List turnosRepartidos = (List)mapUsuarioTurnos.get(usuario);
			Iterator tr = turnosRepartidos.iterator();
			//Se recorre la lista de turnos
			while(tr.hasNext() && !found){
				Turno turno = (Turno)tr.next();
				//Se revisa si el turno
				if(turno.getIdWorkflow().equals(turnoVinculado.getIdWorkflow())){
					found = true;
					rta = usuario;
				}
			}
		}
		return rta;
	}

	/**
	 * @param t
	 * @param mapUsuarioTurnos
	 * @return
	 */
	private Usuario getUsuarioAsignadoAlMismoFolio(Turno t, Map mapUsuarioTurnos, Map mapSubtipoUsuarios) throws EventoException {
		Usuario rta = null;
   
		//Se recorre las listas de turnos asignados por usuario
		//hasta que se encuentre una matrícula compartida
		Iterator llaves = mapUsuarioTurnos.keySet().iterator();
		boolean found = false;
		while(llaves.hasNext() && !found){
			Usuario usuario = (Usuario)llaves.next();
			List turnosRepartidos = (List)mapUsuarioTurnos.get(usuario);
			Iterator tr = turnosRepartidos.iterator();
			//Se recorre la lista de turnos
			while(tr.hasNext() && !found){
				Turno turno = (Turno)tr.next();
				//Se revisa si el turno comparte la matrícula con el turno que llega por parámetros
				if(this.compartenFolio(turno, t)){
					//En caso que compartan la matrícula se revisa si el usuario
					//puede atender el caso
					if(this.abogadoPuedeAtender(usuario, t, mapSubtipoUsuarios)){
						//Si puede atender el caso se asigna la respuesta y se finaliza los ciclos
						found = true;
						rta = usuario;
					}

				}
			}
		}
     	
     	
		return rta;
	}


	/**
	 * Método que reparte los turnos según el orden asignado
	 * @param t
	 * @param mapSubtipoUsuarios
	 * @param mapSubtipoPromedio
	 * @param mapUsuarioTurnos
	 */
	private void repartirPorListaRotada(Turno t, Map mapSubtipoUsuarios, Map mapSubtipoPromedio, Map mapUsuarioTurnos) throws EventoException {
		                             
		if(t.getSolicitud()==null){
			throw new EventoException("El turno "+t.getIdWorkflow()+" NO tiene solicitud");
		}
		SolicitudRegistro sr = (SolicitudRegistro) t.getSolicitud();
		if(sr.getSubtipoAtencion()==null){
			throw new EventoException("El turno "+t.getIdWorkflow()+" NO tiene subtipo de atención");
		}
		Long promedio = (Long)mapSubtipoPromedio.get(sr.getSubtipoAtencion());
		if(promedio==null){
			throw new EventoException("El turno "+t.getIdWorkflow()+" NO se pudo repartir, no hay disponible un promedio");
		}
		
		List abogados = (List)mapSubtipoUsuarios.get(sr.getSubtipoAtencion());
		/*
                 * @author     :   Henry Gómez Rocha
                 * @change     :   Se unen dos if y así mismo, se modifica el mensaje evitando que se muestre en la
                                   excepción el turno y a su ves se hace más claro el mensaje final para el usuario.
                 * Caso Mantis :   0005012
                 */
		if(abogados==null || abogados.size()==0){
                    /*
                     * @author     :   Julio Alcazar Rivas
                     * @change     :   a)Se agregan los subtipos de atencion que no tienen calificador a un HashSet
                                         para luego enviar una excepcion global de los turnos en reparto.
                                       b)Se crea un else para que continue el camino en el caso que el subtipo de atencion
                                         tenga calificador configurado.
                     * Caso Mantis :   0005012
                     */
                    ListaSubtipos.add(sr.getSubtipoAtencion().getNombre());
		}
		else {
                    Usuario us = null;
                    boolean found = false;
                    for(Iterator it = abogados.iterator(); it.hasNext();){
                            us = (Usuario)it.next();
                            if(us.getNumeroTurnosAsignados()+1<=promedio.longValue())
                            {
                                    //:
                                    found = true;
                                    break;
                            }
                    }

                    if(!found){
                            throw new EventoException("El turno "+t.getIdWorkflow()+" NO pudo ser repartido");
                    }

                    this.darTurno(us, t, mapUsuarioTurnos, mapSubtipoUsuarios);

                    //Se rota la lista en memoria:
                    Usuario primero = (Usuario)abogados.get(0);
                    abogados.remove(0);
                    abogados.add(primero);

                    //Se actualiza la lista en el mapa:
                    mapSubtipoUsuarios.put(sr.getSubtipoAtencion(), abogados);

                }
	}

	/**
	 * Calcula el promedio de número de turnos de subtipo de atención por número de abogados asociados
	 * al subtipo de atención indicado
	 * @param allTurnos
	 * @param usuariosPorSubtipo
	 * @return Map mapa con el [SubtipoAtencion subtipo, Long promedio]
	 */
	private Map calcularPromedioPorSubtipoAtencion(List allTurnos, Map mapSubtipoUsuarios) throws EventoException {
		Map rta = new HashMap();
		//Se recorre la lista de turnos para contar los diferentes subtipos
		//de atención, con el fin de calcular el promedio con respecto a los usuarios
		//disponibles para el subtipo de atención
		Turno turno;	
	
		Map numeroTurnosPorSubtipo = new HashMap();
		for(Iterator it = allTurnos.iterator(); it.hasNext();){
			turno = (Turno)it.next();
			if(turno.getSolicitud()==null){
				throw new EventoException("El turno "+turno.getIdWorkflow()+" NO tiene solicitud");
			}
			
			
			
			SolicitudRegistro sr = (SolicitudRegistro) turno.getSolicitud();
			
			if(sr.getSubtipoAtencion()==null){
				throw new EventoException("El turno "+turno.getIdWorkflow()+" NO tiene subtipo de atención");
			}
			
			//Si el subtipo de atención está en el mapa se suma en uno su registro
			/*
                         * @author     :   Julio Alcazar Rivas
                         * @change     :   Se modifica el if para realizar el balanceo del reparto de acuerdo al numero de calificadores por subtipo
                         * Caso Mantis :   0002593
                         */
                        if(mapSubtipoUsuarios.containsKey(sr.getSubtipoAtencion())){
                            Long nuevoNum = new Long(0);
                            if (numeroTurnosPorSubtipo.containsKey(sr.getSubtipoAtencion()))
                            {
                            nuevoNum = (Long) numeroTurnosPorSubtipo.get(sr.getSubtipoAtencion());
                            }
                            Long nuevoNumero = new Long(nuevoNum.longValue() + 1);
                            numeroTurnosPorSubtipo.put(sr.getSubtipoAtencion(), nuevoNumero);
                        }
			
			//En caso que no esté en el mapa se crea un registro nuevo con el número 1
			else{
				Long nuevoNumero = new Long(1);
				numeroTurnosPorSubtipo.put(sr.getSubtipoAtencion(), nuevoNumero);
			}
		}
		
		//En este punto se tiene en un mapa el número de turnos por subtipo de atención
		//y la lista de abogados por subtipo de atención, entonces para cada subtipo de
		//atención en el primer mapa se saca el promedio:
		

		//Recorremos el mapa para sacar todos los subtipos de atención y sacar 
		//el promedio
		Iterator llaves = numeroTurnosPorSubtipo.keySet().iterator();
		long numeroAbogados;
		/*
                 * @author     :   Julio Alcazar Rivas
                 * @change     :   Se inicializa la variable promedio = 0
                 * Caso Mantis :   0005012
                 */
                long promedio = 0;
                /*
                 * @author     :   Henry Gómez Rocha
                 * @change     :   Se crea la variable evitaDosImpresiones con el fin de evitar
                                   que el mismo mensaje sea presentado al usuario final dos veces.
                 * Caso Mantis :   0005012
                 */
                boolean evitaDosImpresiones = true;
		while(llaves.hasNext()){
			  SubtipoAtencion subtipo = (SubtipoAtencion)llaves.next();
			  Long turnosPorSubtipo = (Long)numeroTurnosPorSubtipo.get(subtipo);
			  if(mapSubtipoUsuarios.containsKey(subtipo)){
			  	 List abogados =  (List)mapSubtipoUsuarios.get(subtipo);
			  	 numeroAbogados = abogados.size();
			  	 if(numeroAbogados==0 ){
                                    /*
                                     * @author     :   Julio Alcazar Rivas
                                     * @change     :   a)Se agregan los subtipos de atencion que no tienen calificador a un HashSet
                                                       para luego enviar una excepcion global de los turnos en reparto.
                                                       b)Se crea un else para que continue el camino en el caso que el subtipo de atencion
                                                         tenga calificador configurado.
                                     * Caso Mantis :   0005012
                                     */
                                     ListaSubtipos.add(subtipo.getNombre());
                                 }
                                 else{
                                     promedio = turnosPorSubtipo.longValue() / numeroAbogados;
                                     if(turnosPorSubtipo.longValue()%numeroAbogados!=0){
                                            promedio++;
                                     }
                                 }
			  }
			  else{
			  	//El -1 indica que no existen usuarios del subtipo de atención indicado
			  	//que puedan atender turnos de ese subtipo de atención
			  	 promedio = -1;
			  }
			  
			  //Se ingresa el promedio en la tabla
			  rta.put(subtipo, new Long(promedio));
			  
		  }

               return rta;
	}

    
    /**
     * Decide si un turno comparte un mismo folio el otro turno
     * @param a
     * @param b
     * @return
     */
    private boolean compartenFolio(Turno a, Turno b){
        SolicitudRegistro sa = (SolicitudRegistro)a.getSolicitud();
        SolicitudRegistro sb = (SolicitudRegistro)b.getSolicitud();
        List solicitudFolioA = sa.getSolicitudFolios();
        List solicitudFolioB = sb.getSolicitudFolios();
        Iterator itA = solicitudFolioA.iterator();
        
        while(itA.hasNext()){
            SolicitudFolio sfA = (SolicitudFolio)itA.next();
            Iterator itB = solicitudFolioB.iterator();
            while(itB.hasNext()){
                SolicitudFolio sfB = (SolicitudFolio)itB.next();
                if (sfA.getIdMatricula().equals(sfB.getIdMatricula())){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Este metodo revisa que el abogado pueda atender el turno, verificando que el subtipo
     * de atencion asociado a la solicitud registro de este turno sea uno de los subtipos
     * de atencion que puede atender el abogado.
     */
    private boolean abogadoPuedeAtender(gov.sir.core.negocio.modelo.Usuario abogado, Turno t, Map mapSubtipoUsuarios) throws EventoException{
        boolean rta = false;
        
		if(t.getSolicitud()==null){
			throw new EventoException("El turno "+t.getIdWorkflow()+" NO tiene solicitud");
		}
		SolicitudRegistro sr = (SolicitudRegistro) t.getSolicitud();
		if(sr.getSubtipoAtencion()==null){
			throw new EventoException("El turno "+t.getIdWorkflow()+" NO tiene subtipo de atención");
		}
		
		SubtipoAtencion sb = sr.getSubtipoAtencion();
        /*
         * @author: fpadilla
         * @chages: mantis 2705, no se válidaba la existencia de subtipos.
         */
        List abogados = (List)mapSubtipoUsuarios.get(sb);
        /*
         * @author     :   Henry Gómez Rocha
         * @change     :   Se corrige la palabra calicadores por calificadores.
         * Caso Mantis :   0005012
         */
        if(abogados == null){
            /*
             * @author     :   Julio Alcazar Rivas
             * @change     :   a)Se agregan los subtipos de atencion que no tienen calificador a un HashSet
                                 para luego enviar una excepcion global de los turnos en reparto.
                               b)Se crea un else para que continue el camino en el caso que el subtipo de atencion
                                 tenga calificador configurado.
             * Caso Mantis :   0005012
             */
            ListaSubtipos.add(sr.getSubtipoAtencion().getNombre());
        }
        else {
            Iterator i = abogados.iterator();
            while (i.hasNext() && rta==false){
                Usuario u = (Usuario)i.next();
                if (u.equals(abogado)){
                    rta = true;
                }
            }
        }
        return rta;
    }
    
    
 
    /**
     * Si en el mapa de reparto ya existen turnos que afecten la misma matricula del turno actual
     * se reparte al mismo abogado verificando primero que el subtipo de atencion coincida.
     * Si no existen turnos con la misma matricula o el subtipo de atencion no coincide, se
     * reparte a cualquier abogado que pueda atender el caso, es decir que pueda atender el
     * subtipo de atencion del caso
     * @param t turno a repartir
     * @param subtiposAtencion lista de subtipos de atencion y abogados relacionados
     * @param mapAbogadoTurno es el mapa que contiene el reparto actual
     * @param observaciones es la lista donde se anotan las observaciones del proceso de reparto
     
    private void repartirTurno(Turno t, Map mapSubtiposAtencion, Map mapAbogadoTurno, List observaciones){
        Iterator i = mapAbogadoTurno.keySet().iterator();
        while(i.hasNext()){
            String abogado = (String)i.next();
            List turnosRepartidos = (List)mapAbogadoTurno.get(abogado);
            Iterator tr = turnosRepartidos.iterator();
            while(tr.hasNext()){
                Turno turnoRepartido = (Turno)tr.next();
                if (compartenFolio(t,turnoRepartido) && mismoSubtipoAtencion(t,turnoRepartido)){
                    darTurno(abogado,t,mapAbogadoTurno);
                    return;
                }
            }
        }
    
       
        //si llego aqui es porque no encontro ninguna coincidencia
        SolicitudRegistro sr = (SolicitudRegistro)t.getSolicitud();
        SubtipoAtencion sa = sr.getSubtipoAtencion();
        SubtipoAtencion sb = null;
        Iterator j = mapSubtiposAtencion.keySet().iterator();
        while(sb == null && j.hasNext()){
            SubtipoAtencion s = (SubtipoAtencion)j.next();
            if (s.getIdSubtipoAtencion().equals(sa.getIdSubtipoAtencion())){
                sb = s;
            }
        }
        
        if (sb == null) {
            logger.warn("El turno "+t.getIdWorkflow()+" no se pudo repartir porque el " +
            "subtipo de atencion "+ sa.getNombre()+" no existe");
            observaciones.add("El turno "+t.getIdWorkflow()+" no se pudo repartir porque el " +
            "subtipo de atencion "+ sa.getNombre()+" no existe");
            return; //el turno no se pudo repartir.
        }
        
        List abogados = (List)mapSubtiposAtencion.get(sb);
        if (abogados.size() == 0){
            logger.warn("El turno "+t.getIdWorkflow()+" no se pudo repartir porque no hay " +
            "ningun abogado que atienda turnos con subtipo de atencion:"+ sa.getNombre());
            observaciones.add("El turno "+t.getIdWorkflow()+" no se pudo repartir porque no hay " +
            "ningun abogado que atienda turnos con subtipo de atencion:"+ sa.getNombre());
            return; //el turno no se pudo repartir.
        }
        Random r = new Random();
        int index = r.nextInt(abogados.size());
        Usuario abogado = (Usuario)abogados.get(index);
        darTurno(abogado.getUsername(),t,mapAbogadoTurno);
    }
    */
    
    
    /**
     * Asigna el turno al abogado especificado, actualiza el número de turnos asignados al usuario
     * en el subtipo de atención determinado.
     * @param abogado
     * @param turno
     * @param mapAbogadoTurno
     */
    private void darTurno(Usuario abogado, Turno turno, Map mapUsuarioTurnos, Map mapSubtipoUsuarios) throws EventoException{
		if(turno.getSolicitud()==null){
			throw new EventoException("El turno "+turno.getIdWorkflow()+" NO tiene solicitud");
		}
			
		SolicitudRegistro sr = (SolicitudRegistro) turno.getSolicitud();
			
		if(sr.getSubtipoAtencion()==null){
			throw new EventoException("El turno "+turno.getIdWorkflow()+" NO tiene subtipo de atención");
		}
		
		//Se verifican los turnos asignados al abogado para que no pasen del máximo número de turnos que se pueden asignar
		List turnos = (List)mapUsuarioTurnos.get(abogado);
		if (turnos == null){
			turnos = new ArrayList();
		}
		
		if(turnos.size()<this.turnosMaximosPorAbogado){
			//Se actualiza el número de turnos asignados al usuario en el subtipo de atención indicado
			List usuarios = (List)mapSubtipoUsuarios.get(sr.getSubtipoAtencion());
			if(usuarios==null){
				throw new EventoException("El turno "+turno.getIdWorkflow()+" NO puede asignarse al usuario "+abogado.getUsername()+ " puesto que no está asignado al subtipo de atención del turno");
			}
			
			
			Usuario us;
			boolean found = false;
			for(Iterator it = usuarios.iterator(); it.hasNext();){
				us = (Usuario) it.next();
				if(us.equals(abogado)){
					us.setNumeroTurnosAsignados(us.getNumeroTurnosAsignados()+1);
					found = true;
					break;
				}
			}
			if(!found){
				throw new EventoException("El turno "+turno.getIdWorkflow()+" NO puede asignarse al usuario "+abogado.getUsername()+ " puesto que no está asignado al subtipo de atención del turno");
			}
			
			turnos.add(turno);
			mapUsuarioTurnos.put(abogado,turnos);
			
		}
		
	

	
		
        
    }
}
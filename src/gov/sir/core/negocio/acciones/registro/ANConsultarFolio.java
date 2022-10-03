package gov.sir.core.negocio.acciones.registro;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

import gov.sir.core.eventos.registro.EvnConsultaFolio;
import gov.sir.core.eventos.registro.EvnRespConsultaFolio;
import gov.sir.core.negocio.acciones.excepciones.ConsultaFolioAdministracionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Anotacion;
/**
 * @author     : Carlos Torres
 * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
 */
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;

/**
 * @author mmunoz
 */
public class ANConsultarFolio extends SoporteAccionNegocio {


	/* (non-Javadoc)
	 * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		ForsetiServiceInterface forseti;
		HermodServiceInterface hermod;
		ServiceLocator service = ServiceLocator.getInstancia();
		EvnConsultaFolio evento = (EvnConsultaFolio)e;
		Folio fol=evento.getFolio();
		FolioPk id= new FolioPk();
		id.idMatricula= fol.getIdMatricula();
		String matricula = fol.getIdMatricula();
		Usuario usuarioNeg= evento.getUsuarioNeg();
		List foliosDerivadoPadre = null;
		List foliosDerivadoHijo = null;
		
		
		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
			}
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e1) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti",e1);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}
		
		//VARIBLES DE RETORNO 		
		Folio folio = null;
		List foliosHijos = null;
		List foliosPadre = null;
                List historialAreas = null;
		long numeroGravamenes = 0;
		List gravamenes = null;
		long numeroMedidasCautelares = 0;
		List medidasCautelares = null;
		long numeroFalsaTradicion = 0;
		List falsaTradicion = null;
		List ordenFalsaTradicion = new ArrayList();
		List anotacionesInvalidas = null;
		List ordenAnotacionesInvalidas = new ArrayList();
		List salvedadesAnotaciones = null;
		List cancelaciones = null;
		Usuario usuarioBloqueo = null;
		Turno turnoTramite = null;
		Turno turnoDeuda = null;
		List anotacionesPatrimonioFamiliar = null;
		long numanotacionesPatrimonioFamiliar = 0;
		List anotacionesAfectacionVivienda = null;
		long munanotacionesAfectacionVivienda = 0;
		List anotacionesAfectacionVivienda2 = null;
		long munanotacionesAfectacionVivienda2 = 0;
		List turnosFolioTramite = null;

		try {
			boolean puedeConsultarTurno = true;


			//Consulta si el folio esta asociado a un turno activo que se encuentre en calificación

			boolean estaEnCalificacion = forseti.isFolioInTurnoCalificacion(id);

			//CONSULTA DEL FOLIO
			if (estaEnCalificacion) {
				folio = forseti.getFolioByIDSinAnotaciones(id, null);
			} else {
				folio = forseti.getFolioByIDSinAnotaciones(id, null);
			}
			

			//CONSULTA DE FOLIO DE MAYOR EXTENSIÓN? Y NÚMERO DE ANOTACIONES DEL FOLIO
			boolean esMayorExtension = forseti.mayorExtensionFolio(folio.getIdMatricula());
			long numeroAnotaciones = forseti.getCountAnotacionesFolio(folio.getIdMatricula());

			//CONSULTA DE LOS FOLIOS HIJOS
			if (estaEnCalificacion) {
				foliosHijos = forseti.getFoliosHijos(id, null);
			} else {
				foliosHijos = forseti.getFoliosHijos(id, null);
			}

			//CONSULTA DE LOS FOLIOS PADRE
			if (estaEnCalificacion) {
				foliosPadre = forseti.getFoliosPadre(id, null);
			} else {
				foliosPadre = forseti.getFoliosPadre(id, null);
			}

			//CONSULTA DE GRAVAMENES
			numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN);
			if (estaEnCalificacion) {
				gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN, 0, (int) numeroGravamenes, null, true);
			} else {
				gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN, 0, (int) numeroGravamenes, null, true);
			}

			//CONSULTA DE MEDIDAS CAUTELARES
			numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
			if (estaEnCalificacion) {
				medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR, 0, (int) numeroMedidasCautelares, null, true);
			} else {
				medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR, 0, (int) numeroMedidasCautelares, null, true);
			}
			
			//CONSULTA ANOTACIONES DE FALSE TRADICION
			numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
			if (estaEnCalificacion) {
				falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, null, true);
			} else {
				falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, null, true);
			}
			if(falsaTradicion != null){
				Iterator it = falsaTradicion.iterator();
				while(it.hasNext()){
					Anotacion anotTemp = (Anotacion)it.next();
					ordenFalsaTradicion.add(anotTemp.getOrden());	
				}
			}
			
			//CONSULTA ANOTACIONES INVALIDAS
			if (estaEnCalificacion) {
				anotacionesInvalidas = forseti.getAnotacionesInvalidas(id);
			} else {
				anotacionesInvalidas = forseti.getAnotacionesInvalidas(id);
			}
			if(anotacionesInvalidas != null){
				Iterator it = anotacionesInvalidas.iterator();
				while(it.hasNext()){
					Anotacion anotTemp = (Anotacion)it.next();
					ordenAnotacionesInvalidas.add(anotTemp.getOrden());	
				}
			}	
			
			//CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
			numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
			anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
			
			//	CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
			munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
			anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
			
//			 CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
			munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
			anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
			
			anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);
			

			//CONSULTA DE SALVEDADES
			if (estaEnCalificacion) {
				salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, null);
			} else {
				salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, null);
			}
			

			//CONSULTA DE CANCELACIONES
			if (estaEnCalificacion) {
				cancelaciones = forseti.getAnotacionesConCancelaciones(id, null);
			} else {
				cancelaciones = forseti.getAnotacionesConCancelaciones(id, null);
			}

			//OBTENER INFORMACIÓN DEL TURNO QUE TIENE EN TRAMITE UN FOLIO
			turnoTramite = forseti.getTurnoTramiteFolio(id.idMatricula);

			//OBTENER INFORMACIÓN DEL USUARIO QUE TIENE BLOQUEADO UN TURNO			
			usuarioBloqueo = forseti.getBloqueoFolio(id);

			//OBTENER INFORMACIÓN DEL TURNO QUE TIENE DEUDAS PENDIENTES, REFERENTES A UN FOLIO			
			turnoDeuda = forseti.getTurnoDeudaFolio(id);
			
			//OBTENER TURNOS EN TRAMITE PARA EL FOLIO EN EL SISTEMA FOLIO
			if(matricula.split("-").length < 3){
				Folio folio2 = new Folio();
				folio2.setIdMatricula(matricula);
				turnosFolioTramite = hermod.getTurnosFolioTramite(folio2);
			}else{
				turnosFolioTramite = new ArrayList();
			}
                        
                        /**
                        *@Autor: Edgar Lora
                        *@Mantis: 11599
                        *@Requerimiento: 085_151
                        */
                        boolean usuarioTieneBloqueo = false;
                        if(usuarioBloqueo!=null && usuarioNeg!=null && usuarioBloqueo.getUsername().equals(usuarioNeg.getUsername())){
                                usuarioTieneBloqueo = true;				
                        }
                        
                        if (usuarioTieneBloqueo) {
                                foliosDerivadoPadre = forseti.getFoliosDerivadoPadre(id, usuarioNeg);
                        } else {
                                foliosDerivadoPadre = forseti.getFoliosDerivadoPadre(id);
                        }

                        if (usuarioTieneBloqueo) {
                                foliosDerivadoHijo = forseti.getFoliosDerivadoHijos(id, usuarioNeg);
                        } else {
                                foliosDerivadoHijo = forseti.getFoliosDerivadoHijos(id);
                        }
                        /**
                         * @author     : Carlos Torres
                         * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                         */
                        Traslado trasladoOrigen =  hermod.getFolioOrigenTraslado(matricula);
                        Traslado trasladoDestino =  hermod.getFolioDestinoTraslado(matricula);
                         Circulo circuloorigen = null;
                         Circulo circulodestino = null;
                        if(trasladoOrigen != null){
                            CirculoPk circuloPk = null;
                            if(!trasladoOrigen.getFolioDestino().substring(0, 3).equals("FOL")){
                                circuloPk = new CirculoPk(trasladoOrigen.getFolioDestino().substring(0, 3));
                            }else{
                                String idCirculo = trasladoDestino.getFolioOrigen().getCirculo().substring(trasladoDestino.getFolioOrigen().getCirculo().length() - 3,trasladoDestino.getFolioOrigen().getCirculo().length());
                                circuloPk = new CirculoPk(idCirculo);
                            }
                            circulodestino = forseti.getCirculo(circuloPk);
                        }                                 
                        if(trasladoDestino != null){
                            String idCirculo = trasladoDestino.getFolioOrigen().getCirculo().substring(trasladoDestino.getFolioOrigen().getCirculo().length() - 3,trasladoDestino.getFolioOrigen().getCirculo().length());
                            CirculoPk circuloPk = new CirculoPk(idCirculo);
                            circuloorigen = forseti.getCirculo(circuloPk);
                        }
                        
                        String idMatricula = folio.getIdMatricula();
                        historialAreas = forseti.getHistorialArea(idMatricula);
			//return new EvnRespConsultaFolio(folio, null, null, null, null, null, null, numeroAnotaciones, esMayorExtension, EvnRespConsultaFolio.CONSULTAR_FOLIO);
			EvnRespConsultaFolio resp = new EvnRespConsultaFolio(folio, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion , ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, EvnRespConsultaFolio.CONSULTAR_FOLIO, turnoDeuda, turnoTramite, usuarioBloqueo);
			resp.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
			resp.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
                        resp.setHistorialAreas(historialAreas);
                        /**
                        *@Autor: Edgar Lora
                        *@Mantis: 11599
                        *@Requerimiento: 085_151
                        */
                        resp.setFoliosDerivadoPadre(foliosDerivadoPadre);
                        resp.setFoliosDerivadoHijo(foliosDerivadoHijo);
			resp.setTurnosFolioTramite(turnosFolioTramite);
                        /**
                         * @author     : Carlos Torres
                         * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                         */
                        resp.setTrasladoDestino(trasladoDestino);
                        resp.setTrasladoOrigen(trasladoOrigen);
                        resp.setCirculoDestino(circulodestino);
                        resp.setCirculoOrigen(circuloorigen);
			return resp;
		} catch (ConsultaFolioAdministracionException c) {
			ExceptionPrinter printer = new ExceptionPrinter(c);
			throw c;
		} catch (Throwable t) {
			ExceptionPrinter printer = new ExceptionPrinter(t);
			throw new EventoException(t.getMessage(), t);
		}
		
		
		/*try {
			folio = forseti.getFolioByIDSinAnotaciones(fid, null);
			if (folio == null) {
				throw new FolioNoEncontradoException("No fue posible obtener el folio");
			}
			
//			Se obtiene la lista de los folios Padres de un Folio.
			foliosDerivadoPadre = forseti.getFoliosDerivadoPadre(fid);
			foliosDerivadoHijo = forseti.getFoliosDerivadoHijos(fid);
			
		} catch (ForsetiException e2) {
			throw new EventoException(e2.getMessage(),e2);
		} catch (Throwable e2) {
			throw new EventoException(e2.getMessage(),e2);
		}

		EvnRespConsultaFolio resp = new EvnRespConsultaFolio(folio);
		resp.setFoliosDerivadoHijo(foliosDerivadoHijo);
		resp.setFoliosDerivadoPadre(foliosDerivadoPadre);
		return new EvnRespConsultaFolio(folio);*/
	}

}

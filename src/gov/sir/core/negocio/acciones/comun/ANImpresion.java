/*
 * Created on 05-may-2005
 *
 */
package gov.sir.core.negocio.acciones.comun;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import gov.sir.core.eventos.comun.EvnImpresorasCirculo;
import gov.sir.core.eventos.comun.EvnRespImpresorasCirculo;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Impresion;
import gov.sir.core.negocio.modelo.Imprimible;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.TipoImprimible;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioCirculo;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacion;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;
import gov.sir.print.server.dao.FactoryException;
import gov.sir.print.server.dao.PrintFactory;


/**
 * @author Dcantor
 *
 */
public class ANImpresion extends SoporteAccionNegocio {

    private ForsetiServiceInterface forseti;
    private FenrirServiceInterface fenrir;
    private HermodServiceInterface hermod; 
	private PrintJobsInterface printJobs;    
	private PrintFactory impresion;

    public ANImpresion() throws EventoException, PrintJobsException {
        ServiceLocator sl = ServiceLocator.getInstancia();
        try {
            forseti = (ForsetiServiceInterface)sl.getServicio("gov.sir.forseti");
            hermod = (HermodServiceInterface)sl.getServicio("gov.sir.hermod");
            fenrir = (FenrirServiceInterface)sl.getServicio("gov.sir.fenrir");
			printJobs = (PrintJobsInterface) sl.getServicio("gov.sir.print");            
			impresion = PrintFactory.getFactory();            
        }
        catch (ServiceLocatorException sle) {
            throw new ServicioNoEncontradoException(sle);
        }catch (FactoryException fe) {
			PrintJobsException pje = new PrintJobsException("No fue posible obtener la fábrica concreta de PrintFactory", fe);
			throw pje;
		}
		
    }

    public EventoRespuesta perform(Evento ev) throws EventoException {
        EvnImpresorasCirculo evento = (EvnImpresorasCirculo)ev;
        
        if (evento == null){
			throw new EventoException("No llego ningun evento");
        }
        
        if(evento.getTipoEvento().equals(EvnImpresorasCirculo.REGISTRAR_IMPRESORAS_CIRCULO)){
			return registrarImpresoras(evento);
        }else if(evento.getTipoEvento().equals(EvnImpresorasCirculo.REGISTRAR_CLIENTE)){
			return registrarCliente(evento);
		}else if(evento.getTipoEvento().equals(EvnImpresorasCirculo.ELIMINAR_CLIENTE)){
			return eliminarCliente(evento);
		}else if(evento.getTipoEvento().equals(EvnImpresorasCirculo.DESCARGAR_IMPRIMIBLE)){
			long tiempoIni = System.currentTimeMillis();
			EventoRespuesta evnResp =descargarImprimible(evento);
			ANPago.escribirPrintLog("ANImpresion.descargarImprimible", tiempoIni);
			return evnResp;
		}else if(evento.getTipoEvento().equals(EvnImpresorasCirculo.CONSULTAR_IMPRESIONES_FALLIDAS)){
			return consultarImpresionesFallidas(evento);
		}else if(evento.getTipoEvento().equals(EvnImpresorasCirculo.DISMINUIR_NUMERO_IMPRESIONES)){
			return disminuirNumeroImpresiones(evento);
		}else if(evento.getTipoEvento().equals(EvnImpresorasCirculo.CONSULTAR_LISTA_REGLAS)){
			return consultarListaReglas(evento);
		}else if(evento.getTipoEvento().equals(EvnImpresorasCirculo.ACTUALIZAR_LISTA_IMPRESORAS)){
			return actualizarListaImpresoras(evento);
		}else if(evento.getTipoEvento().equals(EvnImpresorasCirculo.CARGAR_CONFIGURACION_ACTUAL)){
			return cargarConfiguracion(evento);
		}else if(evento.getTipoEvento().equals(EvnImpresorasCirculo.CARGAR_PARAMETROS_CONFIGURACION)){
			return cargarParametrosConfiguracion(evento);
		}
        
        return null;
            
    }
    
    private EventoRespuesta cargarParametrosConfiguracion(EvnImpresorasCirculo evento) throws EventoException{
    	Map map = new HashMap();
    	try {
			List parametrosCliente = hermod.getOPLookupCodes(COPLookupTypes.CONFIGURACION_IMPRESION_CLIENTE);
			for (Iterator paramItera = parametrosCliente.iterator();
					paramItera.hasNext();)
			{
				OPLookupCodes lookupCode = (OPLookupCodes)paramItera.next();
				map.put(lookupCode.getCodigo(), lookupCode.getValor());
				
			}
		} catch (Throwable e) {
			throw new EventoException(e.getMessage(),e);
		}
		return new EvnRespImpresorasCirculo(map,EvnRespImpresorasCirculo.CONSULTAR_PARAMETROS);
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta cargarConfiguracion(EvnImpresorasCirculo evento) throws EventoException{
		Map configuracion=null;
		try {
			configuracion=forseti.getConfiguracionImpresoras(evento.getIdCirculo());
		} catch (Throwable e) {
			throw new EventoException("Error en forseti",e);
		}
		Iterator itConf=configuracion.keySet().iterator();
		while(itConf.hasNext()){
			TipoImprimible tipoImp=(TipoImprimible)itConf.next();
			List listaImpresoras=(List)configuracion.get(tipoImp);
			TipoImprimible nuevo=new TipoImprimible();
			nuevo.setFechaCreacion(tipoImp.getFechaCreacion());
			nuevo.setIdTipoImprimible(tipoImp.getIdTipoImprimible());
			nuevo.setNombre(tipoImp.getIdTipoImprimible());
			List nuevasImp=(List)configuracion.get(nuevo);
		}
		return new EvnRespImpresorasCirculo(configuracion,EvnRespImpresorasCirculo.CONSULTAR_CONFIGURACION);
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta actualizarListaImpresoras(EvnImpresorasCirculo evento) throws EventoException{
		Map configuracion=evento.getConfiguracion();
		String idCirculo = evento.getIdCirculo();
		try {
			forseti.setConfiguracionImpresoras((Hashtable) configuracion,idCirculo);
		} catch (Throwable e) {
			throw new EventoException("Error en forseti",e);
		}
		return null;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta consultarListaReglas(EvnImpresorasCirculo evento) throws EventoException{
		List reglas=null;
		try {
			reglas=forseti.getTiposImprimible();
		} catch (Throwable e) {
			throw new EventoException("Error en forseti",e);
		}
		return new EvnRespImpresorasCirculo(reglas,EvnRespImpresorasCirculo.CONSULTAR_LISTA_REGLAS);
	}
    
	public EventoRespuesta registrarImpresoras(Evento ev) throws EventoException {
		EvnImpresorasCirculo evento = (EvnImpresorasCirculo)ev;
        
		try {
			forseti.eliminarCirculoImpresoras(evento.getIdCirculo());
			forseti.addListaCirculoImpresoras(evento.getIdCirculo(), evento.getImpresoras());
		}
		catch (Throwable t) {
			throw new EventoException("Error en forseti",t);
		}
		return null;
	}    
	
	public EventoRespuesta registrarCliente(Evento ev) throws EventoException {
		EvnImpresorasCirculo evento = (EvnImpresorasCirculo)ev;
        Impresion imp = evento.getImpresion();
        Usuario usuarioSIR = evento.getUsuarioSIR();
        
        
		try {
			org.auriga.core.modelo.transferObjects.Usuario   usuarioAuriga = 
				new org.auriga.core.modelo.transferObjects.Usuario();
			usuarioAuriga.setUsuarioId(usuarioSIR.getUsername());
			usuarioSIR = fenrir.getUsuario(usuarioAuriga);
			List temp = usuarioSIR.getUsuarioCirculos();
			if(temp.size()>0){
				UsuarioCirculo usuarioCirculo = (UsuarioCirculo)temp.get(0);
				imp.setCirculo(usuarioCirculo.getIdCirculo());
			}
			impresion.getImpresionDAO().addSesionImpresion(imp);
		}
		catch (Throwable t) {
			throw new EventoException("Excepción agregando el cliente de impresión",t);
		}
		return null;
	}   
	
	public EventoRespuesta eliminarCliente(Evento ev) throws EventoException {
		EvnImpresorasCirculo evento = (EvnImpresorasCirculo)ev;
		Impresion imp = evento.getImpresion();
        
		try {
			impresion.getImpresionDAO().deleteSesionImpresion(imp);
		}
		catch (Throwable t) {
			throw new EventoException("Excepción eliminando el cliente de impresión",t);
		}
		return null;
	}   
	
	public EventoRespuesta descargarImprimible(Evento ev) throws EventoException {
            System.out.println("************ ENTRO A descargarImprimible ************");
            System.out.println("************ ENTRO A descargarImprimible ************");
            System.out.println("************ ENTRO A descargarImprimible ************");
            System.out.println("************ ENTRO A descargarImprimible ************");

            EvnImpresorasCirculo evento = (EvnImpresorasCirculo) ev;
            Imprimible imp = evento.getImprimible();
            Imprimible temp = null;

            try {
                temp = printJobs.getImprimibleNoImpreso(imp.getIdImprimible());
                //printJobs.eliminarImprimible(imp.getIdImprimible());
            } catch (Throwable t) {
                throw new EventoException("Excepci?n eliminando el cliente de impresi?n", t);
            } finally {
            imp = null;
        }
		
		 //PARA BORRAR
                boolean notaDevolutiva = false;
                if (temp != null) {
                    ObjectInputStream ois = null;
                    gov.sir.print.common.Bundle bundle = null;
                                 try {
                        ByteArrayInputStream bais = new ByteArrayInputStream(temp.getDatosImprimible());
                        //ois = new ObjectInputStream(bais);
                        //bundle = (gov.sir.print.common.Bundle) ois.readObject();
                        /**
                         * ** LLEGA NULL***
                         */

                        //if (bundle.getImprimible() instanceof ImprimibleNotaDevolutivaCalificacion) {
                        //    notaDevolutiva = true;
                        //}

                    } catch (Exception e) {
                        System.out.println("------ error ------- " + e.toString());
                        System.out.println("------ error ------- " + e.toString());
                        System.out.println("------ error ------- " + e.toString());
                        System.out.println("------ error ------- " + e.toString());
                    } finally {
                try {
                    if(ois != null){
                        ois.close();
                    }
                    bundle = null;
                } catch (IOException ex) {
                    Logger.getLogger(ANImpresion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
		  }

                //?NICAMENTE SE RETORNA EL IMPRIMIBLE SI ESTE NUNCA SE HA IMPRESO SINO NO. SE RETORNA UNA BOOLEAN
                //CON EL VALOR DE FALSE PARA INDICAR QUE NO PUEDE SER IMPRESO.
                //HAY QUE QUITAR LA OPCI?N DE QUE SI ES NOTA DEVOLUTIVA SI REIMPRIMA, CUANDO EXISTA UNA OPCI?N PARA REIMPRIMIR DE DICHAS NOTAS.
                EvnRespImpresorasCirculo evnRta = null;
                if (temp != null || notaDevolutiva) {
                    evnRta = new EvnRespImpresorasCirculo(temp, EvnRespImpresorasCirculo.DESCARGAR_IMPRIMIBLE);
                } else {
                    evnRta = new EvnRespImpresorasCirculo(new Boolean(false), EvnRespImpresorasCirculo.DESCARGAR_IMPRIMIBLE);
                }

                
		
		return evnRta;
	}  
	
	
	public EventoRespuesta consultarImpresionesFallidas(Evento ev) throws EventoException {
		EvnImpresorasCirculo evento = (EvnImpresorasCirculo)ev;
		Imprimible imp = evento.getImprimible();
		List idImpresionesFallidas = null;
		boolean isAdministrador = evento.isAdministrador();
		        
		try {
			idImpresionesFallidas = printJobs.getImpresionesFallidas(imp,isAdministrador);
		}
		catch (Throwable t) {
			throw new EventoException("Excepción eliminando el cliente de impresión",t);
		}
		
		return new EvnRespImpresorasCirculo(idImpresionesFallidas,EvnRespImpresorasCirculo.CONSULTAR_IMPRESIONES_FALLIDAS);
	}  
	
	public EventoRespuesta disminuirNumeroImpresiones(Evento ev) throws EventoException {
		EvnImpresorasCirculo evento = (EvnImpresorasCirculo)ev;
		Imprimible imp = evento.getImprimible();
		Imprimible temp = null;
		Imprimible actualizar = new Imprimible();
		boolean actualizacion = false;
		        
		try {
			temp = printJobs.getImprimible(imp.getIdImprimible());
			actualizar.setIdImprimible( temp.getIdImprimible());
			actualizar.setNumeroImpresiones( temp.getNumeroImpresiones() >0 ? (temp.getNumeroImpresiones() -1):  temp.getNumeroImpresiones() );
			actualizacion = printJobs.updateImprimible(actualizar);	
		}
		catch (Throwable t) {
			throw new EventoException("Excepción eliminando el cliente de impresión",t);
		}
		
		return new EvnRespImpresorasCirculo(new Boolean(actualizacion), EvnRespImpresorasCirculo.DISMINUIR_NUMERO_IMPRESIONES);
	}  	
    
}
/*
 * Created on 23-mar-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.sir.print.client.plus;

import gov.sir.core.negocio.modelo.CirculoImpresora;
import gov.sir.core.negocio.modelo.TipoImprimible;

import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.print.PrintService;

/**
 * @author Administrador
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImpresorasListModel extends Observable {
	
	private List impresorasActuales=new ArrayList();
	private List impresorasDisponibles=new ArrayList();
	private String impresoraPredeterminada=null;
	private Map configuracionImpresoras=new Hashtable();
	
	/**
	 * @param configuracionImpresoras The configuracionImpresoras to set.
	 */
	public void setConfiguracionImpresoras(Map configuracionImpresoras) {
		this.configuracionImpresoras = configuracionImpresoras;
		setChanged();
    	notifyObservers();
	}
	/**
	 * @param impresoraPredeterminada The impresoraPredeterminada to set.
	 */
	public void setImpresoraPredeterminada(String impresoraPredeterminada) {
		this.impresoraPredeterminada = impresoraPredeterminada;
		setChanged();
    	notifyObservers();
	}
	/**
	 * 
	 */
	public ImpresorasListModel() {
		super();
	}
	
	
	public void cargarImpresorasDisponibles(){
		List todasImpresoras=new ArrayList();
		impresorasDisponibles=new ArrayList();
		PrintService[] services = PrinterJob.lookupPrintServices();
		
    	for (int i=0; i<services.length; i++){
    		PrintService servicio=services[i];
    		todasImpresoras.add(servicio);
    		if (!estaImpresoraEnLista(servicio,impresorasActuales)){
    			impresorasDisponibles.add(servicio);
    		}
    	}
    	
    	Iterator itImpresorasActuales=impresorasActuales.iterator();
    	while(itImpresorasActuales.hasNext()){
    		PrintService impresora=(PrintService)itImpresorasActuales.next();
    		if (!estaImpresoraEnLista(impresora,todasImpresoras)){
    			System.out.println("ERROR: La impresora "+impresora.getName()+"ya no esta en la lista de impresoras");
    		}
    	}
    	setChanged();
    	notifyObservers();
	}

	/**
	 * @param servicio
	 * @param impresorasActuales
	 * @return
	 */
	private boolean estaImpresoraEnLista(PrintService servicio, List impresorasActuales) {
		Iterator itImpresoras=impresorasActuales.iterator();
		while(itImpresoras.hasNext()){
			PrintService impresora=(PrintService)itImpresoras.next();
			if (impresora.getName().equals(servicio.getName())){
				return true;
			}
		}
		return false;
	}
	
	public void agregarImpresora(String nombreImpresora){
		//TODO: MIRAR SI PUEDEN SER VARIAS IMPRESORAS DE UN SOLO TOTAZO
		Iterator itImpresorasDisponibles=impresorasDisponibles.iterator();
		while (itImpresorasDisponibles.hasNext()){
			PrintService impresora=(PrintService)itImpresorasDisponibles.next();
			if (nombreImpresora.equals(impresora.getName())){
				impresorasActuales.add(impresora);
				impresorasDisponibles.remove(impresora);
				break;
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public void eliminarImpresora(String nombreImpresora, String predeterminada){
		Iterator itImpresorasActuales=impresorasActuales.iterator();
		while (itImpresorasActuales.hasNext()){
			PrintService impresora=(PrintService)itImpresorasActuales.next();
			if (nombreImpresora.equals(impresora.getName())){
				impresorasDisponibles.add(impresora);
				impresorasActuales.remove(impresora);
				if (nombreImpresora.equals(predeterminada)){
					this.impresoraPredeterminada=null;
				}
				break;
			}
		}
		setChanged();
		notifyObservers();
	}
	

	/**
	 * @return Returns the impresorasActuales.
	 */
	public List getImpresorasActuales() {
		return impresorasActuales;
	}
	/**
	 * @return Returns the impresorasDisponibles.
	 */
	public List getImpresorasDisponibles() {
		return impresorasDisponibles;
	}
	
	
	public void setImpresorasActuales(List impresorasActuales) {
		this.impresorasActuales=new ArrayList();
		this.impresorasDisponibles=new ArrayList();
		Iterator itImpresorasAct=impresorasActuales.iterator();
		PrintService[] services = PrinterJob.lookupPrintServices();
		boolean hayPredeterminada=false;
		while(itImpresorasAct.hasNext()){
			CirculoImpresora impresora=(CirculoImpresora)itImpresorasAct.next();
			for (int i=0; i<services.length; i++){
	    		PrintService servicio=services[i];
	    		if (servicio.getName().equals(impresora.getIdImpresora())){
	    			this.impresorasActuales.add(servicio);
	    		}
	    	}
			System.out.println("****IMPRESORA:"+impresora.getIdImpresora());
			if (impresora.isPredeterminada()){
				System.out.println("****hay predeterminada");
				hayPredeterminada=true;
				this.impresoraPredeterminada=impresora.getIdImpresora();
			}
		}
		
		if (!hayPredeterminada){
			this.impresoraPredeterminada=null;
		}
		
		for (int i=0; i<services.length; i++){
    		PrintService servicio=services[i];
    		if (!estaImpresoraEnLista(servicio,this.impresorasActuales)){
    			this.impresorasDisponibles.add(servicio);
    		}
    	}
    	
		setChanged();
		notifyObservers();
	}
	/**
	 * @return Returns the impresoraPredeterminada.
	 */
	public String getImpresoraPredeterminada() {
		return impresoraPredeterminada;
	}
	
	public List getImprimiblesSinConfiguracion(){
		List imprimiblesSinConfig=new ArrayList();
		Iterator tiposImp=configuracionImpresoras.keySet().iterator();
		while(tiposImp.hasNext()){
			TipoImprimible tipoImp=(TipoImprimible)tiposImp.next();
			List impresoras=(List)configuracionImpresoras.get(tipoImp);
			if (impresoras.isEmpty()){
				imprimiblesSinConfig.add(tipoImp.getNombre());
			}
		}
		return imprimiblesSinConfig;
	}
}

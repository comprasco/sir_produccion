/*
 * Created on 23-mar-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.sir.print.client.plus;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.print.PrintService;

/**
 * @author Administrador
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImpresorasToListAdaptor implements Observer {
	private java.awt.List impresorasActualesList;
	private java.awt.List impresorasDisponiblesList;
	private java.awt.Choice impresoraPredeterminada;
	private java.awt.Label mensajeErrorConfiguracion;
	private java.awt.List imprimiblesSinConfigurar;
	private java.awt.Label peticionLabel;
	private ImpresorasListModel model;
	/**
	 * 
	 */
	public ImpresorasToListAdaptor(ImpresorasListModel observable,java.awt.List impresorasActuales,java.awt.List impresorasDisponibles, java.awt.Choice impresoraPredeterminada, java.awt.Label mensajeErrorConfiguracion, java.awt.List imprimiblesSinConfigurar, java.awt.Label peticionLabel) {
		this.impresorasActualesList=impresorasActuales;
		this.impresorasDisponiblesList=impresorasDisponibles;
		this.impresoraPredeterminada=impresoraPredeterminada;
		this.mensajeErrorConfiguracion=mensajeErrorConfiguracion;
		this.imprimiblesSinConfigurar=imprimiblesSinConfigurar;
		this.peticionLabel=peticionLabel;
		this.model=observable;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (model == o){
			impresorasActualesList.removeAll();
			impresorasDisponiblesList.removeAll();
			impresoraPredeterminada.removeAll();
			impresoraPredeterminada.add("--Seleccione una Opción--");
			
			Iterator itImpresorasDisponibles=model.getImpresorasDisponibles().iterator();
			Iterator itImpresorasActuales=model.getImpresorasActuales().iterator();
			
			while(itImpresorasDisponibles.hasNext()){
				PrintService service=(PrintService)itImpresorasDisponibles.next();
				impresorasDisponiblesList.add(service.getName());
			}
			
			while(itImpresorasActuales.hasNext()){
				PrintService service=(PrintService)itImpresorasActuales.next();
				impresorasActualesList.add(service.getName());
				impresoraPredeterminada.add(service.getName());
			}
			
			if (model.getImpresoraPredeterminada()!=null){
				System.out.println("****impresora predeterminada:"+model.getImpresoraPredeterminada());
				impresoraPredeterminada.select(model.getImpresoraPredeterminada());
			}
			
			imprimiblesSinConfigurar.removeAll();
			if (model.getImprimiblesSinConfiguracion().isEmpty()){	
				imprimiblesSinConfigurar.setVisible(false);
				mensajeErrorConfiguracion.setVisible(false);
				peticionLabel.setVisible(false);
			}else{
				mensajeErrorConfiguracion.setVisible(true);
				imprimiblesSinConfigurar.setVisible(true);
				peticionLabel.setVisible(true);
				Iterator itImprErr=model.getImprimiblesSinConfiguracion().iterator();
				while(itImprErr.hasNext()){
					String impr=(String)itImprErr.next();
					imprimiblesSinConfigurar.add(impr);
				}
			}
		}
	}
}

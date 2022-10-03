/*
 * Created on 21 de septiembre de 2004
 * SolicitudCorreccion.java
 */
package gov.sir.core.negocio.modelo;

import gov.sir.core.negocio.modelo.jdogenie.ConsignacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolCheckedItemDevEnhanced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela la información de las solicitudes de devolución de dinero
 * @author dsalas 
 */
public class SolicitudDevolucion extends Solicitud implements TransferObject {
	
	 /**
	  * El ciudadano que solicitó la devolucion
	  */
	private Ciudadano ciudadano;
	 private static final long serialVersionUID = 1L;
	 /**
	  * La descripción de la devolucion
	  */
	private String descripcion;
	
	
	/**
	 * Indica si la solicitud es aprobada
	 */
	private boolean aprobada;
	 
	
	private int numeroFolios;
	
	
	/** Retorna el identificador del ciudadano solicitante
	 * @return el ciudadano que solicitó la devolucion
	 */
	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	/** Cambia el identificador del ciudadano solicitante
	 * @param el ciudadano que solicitó la devolucion
	 */
	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}
	
	/** Retorna la descripción de la solicitud
	 * @return descripcion de la devolucion
	 */
	public String getDescripcion() {
		return this.descripcion;
	}

	/** Cambia la descripción de la solicitud
	 * @param descripcion de la devolucion
	 */
	public void setDescripcion (String descr) {
		this.descripcion = descr;
	}

	/** Indica si una solicitud es aprobada en determinado momento del flujo
	 * @return
	 */
	public boolean isAprobada() {
		return aprobada;
	}

	/** Modifica la marca que indica si una solicitud es aprobada 
	 * en determinado momento del flujo
	 * @param b
	 */
	public void setAprobada(boolean b) {
		aprobada = b;
	}
	
	/**
	 * @return
	 */
	public int getNumeroFolios() {
		return numeroFolios;
	}

	/**
	 * @param i
	 */
	public void setNumeroFolios(int i) {
		numeroFolios = i;
	}
	
//	 ...
    private List checkedItems = new ArrayList(); // contains SolCheckedItemDev  inverse SolCheckedItemDevEnhanced.solicitud
// ...
    
    public List getCheckedItems() {
        return Collections.unmodifiableList(checkedItems);
    }

    public boolean addCheckedItem(SolCheckedItemDev newCheckedItem) {
        return checkedItems.add(newCheckedItem);
    }

    public boolean removeCheckedItem(SolCheckedItemDev oldCheckedItem) {
        return checkedItems.remove(oldCheckedItem);
    }
    
    private List solicitantes = new ArrayList(); 
    
    public List getSolicitantes() {
        return Collections.unmodifiableList(solicitantes);
    }

    public boolean addSolicitante(Solicitante newSolicitante) {
        return solicitantes.add(newSolicitante);
    }

    public boolean removeSolicitante(Solicitante oldSolicitante) {
        return solicitantes.remove(oldSolicitante);
    }
    
    private List consignaciones = new ArrayList(); 
    
    public List getConsignaciones() {
        return Collections.unmodifiableList(consignaciones);
    }

    public boolean addConsignacion(Consignacion newConsignacion) {
        return consignaciones.add(newConsignacion);
    }
    
    public void setConsignaciones(List consignaciones) {
        this.consignaciones=consignaciones;
    }

    public boolean removeConsignacion(Consignacion oldConsignacion) {
        return consignaciones.remove(oldConsignacion);
    }
}

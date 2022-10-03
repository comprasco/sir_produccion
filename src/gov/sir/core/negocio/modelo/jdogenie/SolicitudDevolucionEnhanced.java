/*
 * Created on 21 de septiembre de 2004
 * SolicitudCorreccion.java
 */
package gov.sir.core.negocio.modelo.jdogenie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gov.sir.core.negocio.modelo.SolicitudDevolucion;

/**
 * @author dsalas
 * 
 */
public class SolicitudDevolucionEnhanced extends SolicitudEnhanced {

	/**
	 * El ciudadano que solicitó la corrección. 
	 */
	private CiudadanoEnhanced ciudadano;
	
	/**
	 * La descripción de la corrección
	 */
	private String descripcion;
	
	private int numeroFolios;
	
	/**
	 * Indica si la solicitud es aprobada
	 */
	private boolean aprobada;

	/**
	 * @return el ciudadano que solicitó la corrección
	 */
	public CiudadanoEnhanced getCiudadano() {
		return ciudadano;
	}

	/**
	 * @param el ciudadano que solicitó la corrección
	 */
	public void setCiudadano(CiudadanoEnhanced ciudadano) {
		this.ciudadano = ciudadano;
	}
	
	
	/**
	 * @return descripcion de la correccion. 
	 */
	public String getDescripcion() {
		return this.descripcion;
	}

	/**
	 * @param descripcion de la correccion. 
	 */
	public void setDescripcion(String descr) {
		this.descripcion = descr;
	}

	public static SolicitudDevolucionEnhanced enhance(SolicitudDevolucion solicitud) {
		return (SolicitudDevolucionEnhanced) Enhanced.enhance(solicitud);
	}

	/**
	 * @return
	 */
	public boolean isAprobada() {
		return aprobada;
	}

	/**
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
    private List checkedItems = new ArrayList(); // contains SolCheckedItemDevEnhanced  inverse SolCheckedItemDevEnhanced.solicitud
// ... 
    
    public List getCheckedItems() {
        return Collections.unmodifiableList(checkedItems);
    }

    public boolean addCheckedItem(SolCheckedItemDevEnhanced newCheckedItem) {
        return checkedItems.add(newCheckedItem);
    }

    public boolean removeCheckedItem(SolCheckedItemDevEnhanced oldCheckedItem) {
        return checkedItems.remove(oldCheckedItem);
    }
    
    private List solicitantes = new ArrayList(); 
    
    public List getSolicitantes() {
        return Collections.unmodifiableList(solicitantes);
    }

    public boolean addSolicitante(SolicitanteEnhanced newSolicitante) {
        return solicitantes.add(newSolicitante);
    }

    public boolean removeSolicitante(SolicitanteEnhanced oldSolicitante) {
        return solicitantes.remove(oldSolicitante);
    }
    
    private List consignaciones = new ArrayList(); 
    
    public List getConsignaciones() {
        return Collections.unmodifiableList(consignaciones);
    }

    public boolean addConsignacion(ConsignacionEnhanced newConsignacion) {
        return consignaciones.add(newConsignacion);
    }

    public boolean removeConsignacion(ConsignacionEnhanced oldConsignacion) {
        return consignaciones.remove(oldConsignacion);
    }

	public void setConsignaciones(List consignaciones) {
		this.consignaciones = consignaciones;
	}
    
    

}

package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ResultadoFolio;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Generated by JDO Genie (version:3.2.0beta2 (28 Jun 2004))
 */
public class ResultadoFolioEnhanced extends Enhanced {

	private String idBusqueda; // pk 
	private String idMatricula; // pk 
	private String idSolicitud; // pk 
	private String estadoFolio;
	private BusquedaEnhanced busqueda; // inverse Busqueda.resultadosFolio
	private FolioEnhanced folio;
	private List resultadosAnotacion = new ArrayList();
	// contains ResultadoAnotacion  inverse ResultadoAnotacion.resultadoFolio
	private boolean isMayorExtension;
	private String lastDireccion;

	public ResultadoFolioEnhanced() {
	}

	public String getIdBusqueda() {
		return idBusqueda;
	}

	public void setIdBusqueda(String idBusqueda) {
		this.idBusqueda = idBusqueda;
	}

	public String getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(String idMatricula) {
		this.idMatricula = idMatricula;
	}

	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public BusquedaEnhanced getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(BusquedaEnhanced busqueda) {
		this.busqueda = busqueda;
		setIdBusqueda(busqueda.getIdBusqueda());
		setIdSolicitud(busqueda.getIdSolicitud());
	}

	public FolioEnhanced getFolio() {
		return folio;
	}

	public void setFolio(FolioEnhanced folio) {
		this.folio = folio;
		setIdMatricula(folio.getIdMatricula());
	}

	public List getResultadosAnotacions() {
		return Collections.unmodifiableList(resultadosAnotacion);
	}

	public boolean addResultadosAnotacion(ResultadoAnotacionEnhanced newResultadosAnotacion) {
		return resultadosAnotacion.add(newResultadosAnotacion);
	}

	public boolean removeResultadosAnotacion(ResultadoAnotacionEnhanced oldResultadosAnotacion) {
		return resultadosAnotacion.remove(oldResultadosAnotacion);
	}

	public static ResultadoFolioEnhanced enhance(ResultadoFolio resultado) {
		return (ResultadoFolioEnhanced) Enhanced.enhance(resultado);
	}


	/**
	 * @return
	 */
	public boolean isMayorExtension() {
		return isMayorExtension;
	}

	/**
	 * @return
	 */
	public String getLastDireccion() {
		return lastDireccion;
	}

	/**
	 * @param b
	 */
	public void setMayorExtension(boolean b) {
		isMayorExtension = b;
	}

	/**
	 * @param string
	 */
	public void setLastDireccion(String string) {
		lastDireccion = string;
	}

	public String getEstadoFolio() {
		return estadoFolio;
	}

	public void setEstadoFolio(String estadoFolio) {
		this.estadoFolio = estadoFolio;
	}

}
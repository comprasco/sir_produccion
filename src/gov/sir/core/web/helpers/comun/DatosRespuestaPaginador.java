/*
 * Created on 29/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.comun;


import java.io.Serializable;
import java.util.List;

/**
 * @author jmendez, ddiaz
 *
 */
public class DatosRespuestaPaginador implements Serializable{

	
	/*
	 * Lista donde se encuentran las anotaciones Definitvas guardadas actualmente en el cache;
	 */
	private List anotacionesActual;
	
	/*
	 * Numero de anotaciones en el folio (definitivas)
	 */
	private int numeroPagina;
	
	/*
	 * Numero de anotaciones en el folio (definitivas)
	 */
	private long cantidadRegistros;

	private long numeroAnotacionesDefinitivas;

	

	

	/**
	 *  Se necesita depurar.
	 * @param paginaResultados
	 * @param total
	 * @param tamanoPagCache
	 * @param tamanoPag
	 */
/*	public PaginadorAvanzado(List paginaResultados, int total, int tamanoPagCache, int tamanoPag) {
		super(paginaResultados, tamanoPag);
		tamanoCacheResultados = tamanoPagCache;
		numTotalResultados = total;
		indexActualCache = 0;
		calcularNumeroPaginas();
	}*/

	/**
	 * 
	 * @param paginaResultados
	 * @param total
	 */
	public DatosRespuestaPaginador() {
		anotacionesActual=new LinkedListAdapter();
	}

	public void setAnotacionesActual(List nPaginaActual){
		this.anotacionesActual=nPaginaActual;
	}
	
	public List getAnotacionesActual(){
		return this.anotacionesActual;
	} 
		
	public void setNumeroPagina(int nNumeroPagina){
		this.numeroPagina=nNumeroPagina;
	}

	public int getNumeroPagina(){
		return this.numeroPagina;
	}
	
	public void setCantidadRegistros(long nCantidadRegistros){
		this.cantidadRegistros=nCantidadRegistros;
	}

	public long getCantidadRegistros(){
		return this.cantidadRegistros;
	}

	/**
	 * @param numeroAnotacionesDefinitivas
	 */
	public void setNumeroAnotacionesDefinitivas(long numeroAnotacionesDefinitivas) {
		this.numeroAnotacionesDefinitivas = numeroAnotacionesDefinitivas;
	}
	

	/**
	 * @return Returns the numeroAnotacionesDefinitivas.
	 */
	public long getNumeroAnotacionesDefinitivas() {
		return numeroAnotacionesDefinitivas;
	}

}

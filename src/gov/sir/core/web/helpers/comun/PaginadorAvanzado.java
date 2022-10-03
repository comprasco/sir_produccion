/*
 * Created on 29/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.comun;

import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.acciones.excepciones.PaginadorCacheNoActualizadoException;
import gov.sir.core.web.acciones.excepciones.PaginadorPaginaNoEncontradaException;

import java.io.Serializable;
import java.util.List;


/**
 * @author jmendez, ddiaz
 *
 */
public class PaginadorAvanzado extends Paginador implements Serializable{

	public static final int TAMANO_PAGINA_CACHE_DEFAULT = 100;
	private int tamanoCacheResultados;
	private int numTotalResultados;
	private int indexActualCache;
	/*
	 * numero de paginas q se encuentran en cache.
	 */
	private int numPaginasCache;
	private int numeroPagina;
	private boolean tipoConsulta;
	private String nombreNumPagina;
	

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
	public PaginadorAvanzado(List paginaResultados, int total) {
		super(paginaResultados, total);
		tamanoCacheResultados = TAMANO_PAGINA_CACHE_DEFAULT;
		numTotalResultados = total;
		indexActualCache = 0;
		calcularNumeroPaginas();
	}

	/**
	* Calcula el número de páginas individuales de visualización en el cliente
	* La información de visualización se toma de las páginas de caché de datos
	* 
	* Calcula el número de páginas necesarias de caché para la información
	* Cada solicitud de página de caché debe generar una llamada a negocio
	*/
	private void calcularNumeroPaginas() {
		setNumeroPaginas(Math.round(numTotalResultados / getTamanoPagina()));
		if ((numTotalResultados % getTamanoPagina()) != 0) {
			int num = getNumeroPaginas();
			num++;
			setNumeroPaginas(num);
		}

		numPaginasCache = Math.round(numTotalResultados / tamanoCacheResultados);
		if ((numTotalResultados % tamanoCacheResultados) != 0) {
			numPaginasCache++;
		}
	}

	/**
	 * 
	 * @param pagina
	 * @return
	 */
	private boolean paginaEnCache(int pagina) {
		int indexPagSolicitada = getIndexPagina(pagina);
		int ultimoIndiceCache = indexActualCache + tamanoCacheResultados;

		if (indexPagSolicitada >= indexActualCache && indexPagSolicitada < ultimoIndiceCache) {
			return true;
		}
        //mover el indice del cache al primero de la página requerida
        int inicioNuevaPagCache =
        	Math.round(indexPagSolicitada / tamanoCacheResultados) * tamanoCacheResultados;

        //avanza el indice a la siguiente página de cache donde se encuentre la pagina de
        //visualizacion solicitada
        indexActualCache = inicioNuevaPagCache;

        //Reset del caché de la página actual
        setResultados(null);
        return false;
	}

	/**
	 * 
	 */
	public List getPaginaDesdeCache(int pagina)
		throws PaginadorPaginaNoEncontradaException, PaginadorCacheNoActualizadoException {
		List resultadosPagina = null;
		if (paginaEnCache(pagina)) {
			if (getResultados() == null) {
				//excepcion: no se ha actualizado el caché de páginas
				throw new PaginadorCacheNoActualizadoException();
			}
			//índice de pagina normalizado
			int indexPagSolicitada = getIndexPagina(pagina);
			int paginaNormalizada =
				pagina - (Math.round(indexPagSolicitada / tamanoCacheResultados) * getTamanoPagina());
			resultadosPagina = super.getPagina(paginaNormalizada, pagina);
		} else {
			//excepcion: debe solicitar la página al negocio
			throw new PaginadorPaginaNoEncontradaException();
		}
		return resultadosPagina;
	}

	/**
	 * 
	 * @param resultados
	 */
	public void setNuevaPaginaCache(List resultados) {
		setResultados(resultados);
	}

	/**
	 * @return
	 */
	public int getNumeroTotalResultados() {
		return numTotalResultados;
	}

	/**
	 * @return
	 */
	public int getTamanoCacheResultados() {
		return tamanoCacheResultados;
	}

	/**
	 * @param i
	 */
	public void setNumeroTotalResultados(int i) {
		numTotalResultados = i;
	}

	/**
	 * @param i
	 */
	public void setTamanoCacheResultados(int i) {
		tamanoCacheResultados = i;
	}

	/*
		 Debe verificar si la pagina esta en memoria
		 si no.. 
		 informar pñara la generacion del evento
	 
		 cuando llega la resp del neg
		 asociar lista del negocio al paginador
		  (mover el indice a la pagina que se necesita
			internamente lo hace?)
	  	
		 */

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		List lista = new java.util.Vector();
		for (int i = 0; i < 100; i++) {
			gov.sir.core.negocio.modelo.ResultadoAnotacion anota =
				new gov.sir.core.negocio.modelo.ResultadoAnotacion();

			anota.setIdAnotacion("id_" + i);
			anota.setIdMatricula("matricula_" + i);
			lista.add(anota);
		}

		PaginadorAvanzado avanzado = new PaginadorAvanzado(lista, 1011);

		Log.getInstance().debug(PaginadorAvanzado.class,"getNumeroPaginas: " + avanzado.getNumeroPaginas());
		Log.getInstance().debug(PaginadorAvanzado.class,"getUltimaPaginaGenerada: " + avanzado.getUltimaPaginaGenerada());
		Log.getInstance().debug(PaginadorAvanzado.class,"getNumPaginasCache: " + avanzado.getNumPaginasCache());

		List listaResultadosAMostrar = null;

		try {
			//la listaResultadosAMostrar debería ponerse en la sesión
			//para pasarse como parámetro List al helper requerido
			//(el helper no interactúa directametne con el paginador, solo recibe el list)
			listaResultadosAMostrar = avanzado.getPaginaDesdeCache(1);
			Log.getInstance().debug(PaginadorAvanzado.class,"getUltimaPaginaGenerada: " + avanzado.getUltimaPaginaGenerada());

			listaResultadosAMostrar = avanzado.getPaginaDesdeCache(3);
			Log.getInstance().debug(PaginadorAvanzado.class,"getUltimaPaginaGenerada: " + avanzado.getUltimaPaginaGenerada());

			//esta página no está en el cache
			listaResultadosAMostrar = avanzado.getPaginaDesdeCache(53);
			Log.getInstance().debug(PaginadorAvanzado.class,"getUltimaPaginaGenerada: " + avanzado.getUltimaPaginaGenerada());

		} catch (PaginadorPaginaNoEncontradaException e) {
			//la pagina no está en el caché se debe generar una llamada al negocio que retorna datos
			//simulación de datos
			Log.getInstance().debug(PaginadorAvanzado.class,"**** Cargando desde capa  de negocio");
			lista = new java.util.Vector();
			for (int i = 500; i < 600; i++) {
				gov.sir.core.negocio.modelo.ResultadoAnotacion anota =
					new gov.sir.core.negocio.modelo.ResultadoAnotacion();

				anota.setIdAnotacion("id_" + i);
				anota.setIdMatricula("matricula_" + i);
				lista.add(anota);
			}

		} catch (PaginadorCacheNoActualizadoException e) {
			e.printStackTrace(System.out);
		}

		try {
			listaResultadosAMostrar = avanzado.getPaginaDesdeCache(53);
			Log.getInstance().debug(PaginadorAvanzado.class,"getUltimaPaginaGenerada: " + avanzado.getUltimaPaginaGenerada());

		} catch (PaginadorPaginaNoEncontradaException e) {
			e.printStackTrace(System.out);
		} catch (PaginadorCacheNoActualizadoException e) {
			//aquí no se ha actualizado el caché de los datos cargados desde el negocio
			//esto debería hacerse en el doEnd de la acción web
			Log.getInstance().debug(PaginadorAvanzado.class,"-------Actualizando caché en el paginador");
			avanzado.setNuevaPaginaCache(lista);
		}

		try {
			listaResultadosAMostrar = avanzado.getPaginaDesdeCache(53);
			Log.getInstance().debug(PaginadorAvanzado.class,"getUltimaPaginaGenerada: " + avanzado.getUltimaPaginaGenerada());

			listaResultadosAMostrar = avanzado.getPaginaDesdeCache(56);
			Log.getInstance().debug(PaginadorAvanzado.class,"getUltimaPaginaGenerada: " + avanzado.getUltimaPaginaGenerada());
			
			//esta página ya no está en el caché 
			//se debe solicitar nuevameente	
			listaResultadosAMostrar = avanzado.getPaginaDesdeCache(1);
			Log.getInstance().debug(PaginadorAvanzado.class,"getUltimaPaginaGenerada: " + avanzado.getUltimaPaginaGenerada());

		} catch (PaginadorPaginaNoEncontradaException e) {
			e.printStackTrace(System.out);
		} catch (PaginadorCacheNoActualizadoException e) {
			e.printStackTrace(System.out);
		}

	}

	/**
	 * @return
	 */
	public int getNumPaginasCache() {
		return numPaginasCache;
	}

	/**
	 * @param i
	 */
	public void setNumPaginasCache(int i) {
		numPaginasCache = i;
	}
		
	public void setNumeroPagina(int nNumeroPagina){
		this.numeroPagina=nNumeroPagina;
	}

	public int getNumeroPagina(){
		return this.numeroPagina;
	}

	public void setTipoConsulta(boolean nTipoConsulta){
		this.tipoConsulta=nTipoConsulta;
	}

	public boolean getTipoConsulta(){
		return this.tipoConsulta;
	}

	public void setNombreNumPagina(String nNombreNumPagina){
		this.nombreNumPagina=nNombreNumPagina;
	}

	public String getNombreNumPagina(){
		return this.nombreNumPagina;
	}
	
	

	

}

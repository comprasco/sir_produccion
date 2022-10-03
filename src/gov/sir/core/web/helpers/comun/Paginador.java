package gov.sir.core.web.helpers.comun;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Clase que maneja la l�gica de paginaci�n paginaci�n de un  List  de registros.
 *  El tama�o de la p�gina se controla con la constante de la clase TAMANO_PAGINA.
 *  Se debe usar en conjunto con un Helper para la generaci�n del c�digo HTML (Ejemplo: ResultadoHelper
 */
public class Paginador implements Serializable{

	public final static int TAMANO_PAGINA_DEFAULT = 10;

	private int tamanoPagina;
	private List resultados;
	private int index;
	private int numeroPaginas;
	private int ultimaPaginaGenerada;
	private int numAnotaciones;

	/**
	*
	*/
	public Paginador(List resultados) {
		tamanoPagina = TAMANO_PAGINA_DEFAULT;
		this.resultados = resultados;
		index = 0;
		ultimaPaginaGenerada = 0;
		calcularNumeroPaginas();
	}
	
	/**
	*
	*/
	public Paginador(List resultados, int numAnotaciones) {
		tamanoPagina = TAMANO_PAGINA_DEFAULT;
		this.resultados = resultados;
		index = 0;
		this.numAnotaciones=numAnotaciones;
		ultimaPaginaGenerada = 0;
		calcularNumeroPaginas();
	}


	/**
	* Adiciona un item a la lista de registros a paginar
	* @param item Objeto a ser adicionado a la lista de registros paginables
	*/
	public void adicionarItem(Object item) {
		resultados.add(item);
		calcularNumeroPaginas();
	}

	/**
	* Adiciona una lista de items a la lista de registros a paginar
	* @param List de items  a ser adicionados a la lista de registros paginables
	*/
	public void adicionarItems(List items) {
		resultados.addAll(items);
		calcularNumeroPaginas();
	}

	/**
	*
	*/
	private void calcularNumeroPaginas() {
		numeroPaginas = Math.round(resultados.size() / tamanoPagina);

		if ((resultados.size() % tamanoPagina) != 0) {
			numeroPaginas++;
		}
	}

	/**
	 * @param i
	 */
	public void setNumeroPaginas(int i) {
		numeroPaginas = i;
	}

	/**
	* @return n�mero de p�ginas disponibles en el paginador
	*/
	public int getNumeroPaginas() {
		return this.numeroPaginas;
	}

	/**
	* @return �nt indica el n�mero de la �ltima p�gina que se gener�
	*/
	public int getUltimaPaginaGenerada() {
		return this.ultimaPaginaGenerada;
	}

	/**
	* @return indice actual en la paginacion
	*/
	public int getIndex() {
		return this.index;
	}

	/**
	* Retorna el �ndice inicial de la p�gina solicitada
	* @param n�mero de la p�gina
	* @return indice actual en la paginacion
	*/
	public int getIndexPagina(int pagina) {
		return pagina * tamanoPagina;
	}

	/**
	* Reinicializa el indice inicial de la paginaci�n
	*/
	public void reset() {
		this.index = 0;
	}

	/**
	* Retorna una p�gina del conjunto de registros y avanza el �ndice del 
	* paginador a la �ltima p�gina solicitada
	* (El valor del avance del �ndice es posteriormente utilizado por el 
	* paginador helper)
	*  
	* @param pagina n�mero de la p�gina que desea ser visualizada
	* @List sublista con los registros de la p�gina solicitada
	*/
	public List getPagina(int pagina) {
		index = getIndexPagina(pagina);

		ultimaPaginaGenerada = pagina;

		return siguientePagina();
	}
	
	/**
	* Retorna una p�gina del conjunto de registros y avanza el �ndice del 
	* paginador a la �ltima p�gina solicitada
	* (El valor del avance del �ndice es posteriormente utilizado por el 
	* paginador helper)
	*  
	* @param pagina n�mero de la p�gina que desea ser visualizada
	* @List sublista con los registros de la p�gina solicitada
	*/
	public List getPagina(int paginaNormalizada, int pagina) {
		index = getIndexPagina(paginaNormalizada);

		ultimaPaginaGenerada = pagina;

		return siguientePagina();
	}

	/**
	* Retorna la p�gina del conjunto de registros de paginaci�n
	* @List sublista con los registros de la siguiente p�gina
	*/
	private List siguientePagina() {
		List sublist = null;

		int last_index = index + tamanoPagina;

		if (resultados.isEmpty() || (index > resultados.size())) {
			sublist = null;
		} else {
			if (last_index <= resultados.size()) {
				sublist = resultados.subList(index, last_index);
			} else {
				last_index = index + (resultados.size() - index);
				sublist = resultados.subList(index, last_index);
			}
		}

		index = last_index;
		
		//return sublist;
		List tempDefinitiva = new ArrayList();
		
		if(sublist!=null){
			Iterator it = sublist.iterator();
			while (it.hasNext()){
				tempDefinitiva.add(it.next());
			}
		}
		return tempDefinitiva;		
		
	}

	/**
	 * @return
	 */
	public int getTamanoPagina() {
		return tamanoPagina;
	}

	/**
	 * @param i
	 */
	public void setTamanoPagina(int i) {
		tamanoPagina = i;
	}

	/**
	 * @return
	 */
	protected List getResultados() {
		return resultados;
	}

	/**
	 * @param list
	 */
	protected void setResultados(List list) {
		resultados = list;
	}

	/**
	 * @return Returns the numAnotaciones.
	 */
	public int getNumAnotaciones() {
		return numAnotaciones;
	}
	/**
	 * @param numAnotaciones The numAnotaciones to set.
	 */
	public void setNumAnotaciones(int numAnotaciones) {
		this.numAnotaciones = numAnotaciones;
	}
}

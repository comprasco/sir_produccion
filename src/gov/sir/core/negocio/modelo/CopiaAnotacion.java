package gov.sir.core.negocio.modelo;

import gov.sir.core.negocio.modelo.constantes.CProceso;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

  /**
  * @author  ppabon
  * Clase en dónde se guardará la información relacionada con la copia de una anotación a otro folio.
  */
  public class CopiaAnotacion implements Serializable{

      /**
       * Cadena que identifica el id del proceso desde el cual se hace la segregación.
       */
       private String proceso = CProceso.PROCESO_REGISTRO;

       private static final long serialVersionUID = 1L;

	/**
	* Lista que contiene los folios a donde se copiará la anotación dada.
	*/
	private List foliosACopiar = new ArrayList();

	/**
	* Lista que contiene los folios con las anotaciones que han sido validadas y que pueden cancelar una anotación.
	*/
	private List foliosACopiarOk = new ArrayList();

	/**
	 * Objeto que contiene la anotación que se desea copiar.
	 */
	private Anotacion anotacionOrigen = null;

	/**
	 * Boolean que permite determinar si se necesita copiar el comentario de la otra anotación.
	 */
	private boolean copiaComentario = true;


	/**
	 * Folio dónde se encuentra la anotación que desea copiarse.
	 */
	private gov.sir.core.negocio.modelo.Folio folioOrigen = null;
	/**
	 * @return
	 */
	public Anotacion getAnotacionOrigen() {
		return anotacionOrigen;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Folio getFolioOrigen() {
		return folioOrigen;
	}

	/**
	 * @return
	 */
	public List getFoliosACopiar() {
		return foliosACopiar;
	}

	/**
	 * @param anotacion
	 */
	public void setAnotacionOrigen(Anotacion anotacion) {
		anotacionOrigen = anotacion;
	}

	/**
	 * @param folio
	 */
	public void setFolioOrigen(gov.sir.core.negocio.modelo.Folio folio) {
		folioOrigen = folio;
	}

	/**
	 * @param list
	 */
	public void setFoliosACopiar(List list) {
		foliosACopiar = list;
	}

	/**
	 * @return
	 */
	public List getFoliosACopiarOk() {
		return foliosACopiarOk;
	}

	/**
	 * @param list
	 */
	public void setFoliosACopiarOk(List list) {
		foliosACopiarOk = list;
	}

	/**
	 * @return
	 */
	public boolean isCopiaComentario() {
		return copiaComentario;
	}

	public String getProceso() {
		return proceso;
	}

	/**
	 * @param b
	 */
	public void setCopiaComentario(boolean b) {
		copiaComentario = b;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

}

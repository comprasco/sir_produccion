package gov.sir.core.eventos.registro;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EvnCalificacionReproduccionSellos extends EvnCalificacion {

    public static final String CALIFICACION_REIMPRESIONSELLOS_SEARCH_EVENT         = "CALIFICACION_REIMPRESIONSELLOS_SEARCH_EVENT";
    public static final String CALIFICACION_REIMPRESIONSELLOS_RESET_EVENT          = "CALIFICACION_REIMPRESIONSELLOS_RESET_EVENT";
    public static final String CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENT  = "CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENT";
	public static final String CALIFICACION_REIMPRESIONSELLOS_MATRICULA  = "CALIFICACION_REIMPRESIONSELLOS_MATRICULA";    

    String itemSelected;
    String itemToFind;
    
    String matricula;
    int inicio = 0;
    int fin = 0;

    public EvnCalificacionReproduccionSellos(Usuario usuario, String tipoEvento) {
        super(usuario, tipoEvento);
    }

    public String getItemSelected() {
        return itemSelected;
    }

    public String getItemToFind() {
        return itemToFind;
    }

    public void setItemSelected(String itemSelected) {
        this.itemSelected = itemSelected;
    }

    public void setItemToFind(String itemToFind) {
        this.itemToFind = itemToFind;
    }

	/**
	 * @return
	 */
	public int getFin() {
		return fin;
	}

	/**
	 * @return
	 */
	public int getInicio() {
		return inicio;
	}

	/**
	 * @return
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @param i
	 */
	public void setFin(int i) {
		fin = i;
	}

	/**
	 * @param i
	 */
	public void setInicio(int i) {
		inicio = i;
	}

	/**
	 * @param string
	 */
	public void setMatricula(String string) {
		matricula = string;
	}

}

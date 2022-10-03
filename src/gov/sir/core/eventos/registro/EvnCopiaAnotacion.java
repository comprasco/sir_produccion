package gov.sir.core.eventos.registro;

import java.util.List;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.CopiaAnotacion;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.eventos.comun.EvnSIR;

/**
 * Evento para llamar las acciones necesarias para el proceso de copia de anotaciones. 
 * @author ppabon
 */
public class EvnCopiaAnotacion extends EvnSIR {

	/**
	* Acci�n que indica que se desea copiar una anotaci�n a otros folios.
	*/
	public static final String COPIAR_ANOTACION ="COPIAR_ANOTACION";
	
	/**
	* Acci�n que indica que se desea copiar una anotaci�n que es canceladora y se debe pedir cu�l es la cancelada.
	*/
	public static final String CANCELADA ="CANCELADA";	
	
	/**
	* Acci�n que indica que se desea copiar una anotaci�n que es canceladora y se debe pedir cu�l es la cancelada.
	* Es similar a la acci�n de cancelada, pero se usa para corregir la anotaci�n cancelada cuando inicialmente
	* se hab�a ingresado incorrectamente.
	*/
	public static final String CORREGIR_CANCELADA ="CORREGIR_CANCELADA";	
	
	/**
	* Objeto donde se almacenar� toda la informaci�n referente a la copia de la anotaci�n
	*/
	private CopiaAnotacion copiaAnotacion;

	/**
	* Turno con el que se va a realizar el englobe.
	*/
	private Turno turno;	

	/**
	* Objeto para guardar el usuario del negocio
	*/
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	
	private List copiasAnotaciones;
	
	private int desde;
	
	private int hasta;





	//CONSTRUCTORES
	/**
	* Constructor EvnEnglobe
	* @param turno Turno asociado con el proceso de englobe.
	* @param 
	*/
	public EvnCopiaAnotacion(Usuario usuario, String tipoEvento, Turno turno, CopiaAnotacion copiaAnotacion, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.copiaAnotacion = copiaAnotacion;
		this.usuarioSIR = usuarioSIR;
		
	}
	
	/**
	 * @param usuarioAuriga
	 * @param string
	 * @param turno
	 * @param copiasAnotacion
	 * @param usuarioSIR
	 */
	public EvnCopiaAnotacion(Usuario usuarioAuriga, String tipoEvento, Turno turno, List copiasAnotacion, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuarioAuriga, tipoEvento);
		this.turno = turno;
		this.copiasAnotaciones = copiasAnotacion;
		this.usuarioSIR = usuarioSIR;
	}

	/**
	 * @param usuarioAuriga
	 * @param string
	 * @param turno
	 * @param copiaAnotacion
	 * @param desde
	 * @param hasta
	 * @param usuarioSIR
	 */
	public EvnCopiaAnotacion(Usuario usuarioAuriga, String tipoEvento, Turno turno, CopiaAnotacion copiaAnotacion, int desde, int hasta, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuarioAuriga, tipoEvento);
		this.turno = turno;
		this.usuarioSIR = usuarioSIR;
		this.desde = desde;
		this.hasta = hasta;
		this.copiaAnotacion = copiaAnotacion;
	}

	


	/**
	 * @return
	 */
	public CopiaAnotacion getCopiaAnotacion() {
		return copiaAnotacion;
	}

	/**
	 * @return
	 */
	public Turno getTurno() {
		return turno;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

	/**
	 * @return
	 */
	public List getCopiasAnotaciones() {
		return copiasAnotaciones;
	}

	/**
	 * @return
	 */
	public int getDesde() {
		return desde;
	}

	/**
	 * @return
	 */
	public int getHasta() {
		return hasta;
	}

}

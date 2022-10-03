package gov.sir.core.eventos.comun;

import java.util.ArrayList;
import java.util.List;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Turno;

/**
 * @author mmunoz, ppabon
 */
public class EvnNotas extends EvnSIR {

	public static final String AGREGAR_NOTA = "AGREGAR_NOTA";
	public static final String IMPRIMIR_NOTA_INFORMATIVA = "IMPRIMIR_NOTA_INFORMATIVA";
	public static final String AGREGAR_NOTA_DEVOLUTIVA = "AGREGAR_NOTA_DEVOLUTIVA";
	public static final String GUARDAR_NOTAS_DEVOLUTIVAS = "GUARDAR_NOTAS_DEVOLUTIVAS";
	public static final String REGISTRAR_CALIFICACION_PARCIAL = "REGISTRAR_CALIFICACION_PARCIAL";
	public static final String ELIMINAR_NOTAS_INFORMATIVAS_ULTIMA_FASE_TURNO = 
		"ELIMINAR_NOTAS_INFORMATIVAS_ULTIMA_FASE_TURNO";
	
	public static final String GUARDA_NOTA_DEVOLUTIVA_ADD="GUARDA_NOTA_DEVOLUTIVA_ADD";
	public static final String ELIMINA_NOTA_DEVOLUTIVA_ADD="ELIMINA_NOTA_DEVOLUTIVA_ADD";
	
	private Fase fase; 
	private Nota nota;
	private Turno turno;
	private List notas;
	private Circulo circulo;
	private String UID; 
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;
	private boolean inscripcionParcial = false;
	private ArrayList matriculasNoInscritas = new ArrayList();
	private ArrayList matriculasInscritasParcialmente = new ArrayList();
	private int ImprimirYN = 0;

	private int numCopiasImpresion = 1;
	//private Hashtable validacionAnotacionesTemporales;
	
	/**
	 * @param usuario
	 * @param nota
	 */
	public EvnNotas(Usuario usuario, Nota nota, Turno turno) {
		super(usuario,AGREGAR_NOTA);
		this.nota = nota;
		this.turno =  turno;
	}
	
	public EvnNotas(Usuario usuario, Nota nota, Turno turno,String tipoEvento) {
		super(usuario,tipoEvento);
		this.nota = nota;
		this.turno =  turno;
	}
	
	
	
	
	/**
	 * Constructor llamado para la impresión de notas informativas.
	 * @param usuario
	 * @param nota
	 */
	public EvnNotas(Usuario usuario, String tipoEvento, List notas, String UID, Circulo circulo, Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.notas = notas;
		this.circulo = circulo;
		this.turno =  turno;
		this.UID = UID;
		this.usuarioSIR = usuarioSIR;		
	}	
	
	/**
	 * Constructor llamado para la impresión de notas informativas.
	 * @param usuario
	 * @param nota
	 */
	public EvnNotas(Usuario usuario, String tipoEvento, Nota nota, List notas, String UID, Circulo circulo, Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Fase fase) {
		super(usuario, tipoEvento);
		this.notas = notas;
		this.circulo = circulo;
		this.turno =  turno;
		this.UID = UID;
		this.nota = nota;
		this.usuarioSIR = usuarioSIR;		
		this.fase = fase;
	}	
	
	/**
	 * Constructor llamado para la impresión de notas informativas.
	 * @param usuario
	 * @param nota
	 */
	public EvnNotas(Usuario usuario, String tipoEvento, List notas, String UID, Circulo circulo, Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioSIR, Fase fase) {
		super(usuario, tipoEvento);
		this.notas = notas;
		this.circulo = circulo;
		this.turno =  turno;
		this.UID = UID;
		this.usuarioSIR = usuarioSIR;		
		this.fase = fase;
	}		

	public ArrayList getMatriculasInscritasParcialmente() {
		return matriculasInscritasParcialmente;
	}

	public void setMatriculasInscritasParcialmente(
			ArrayList matriculasInscritasParcialmente) {
		this.matriculasInscritasParcialmente = matriculasInscritasParcialmente;
	}

	public ArrayList getMatriculasNoInscritas() {
		return matriculasNoInscritas;
	}

	public void setMatriculasNoInscritas(ArrayList matriculasInscritas) {
		this.matriculasNoInscritas = matriculasInscritas;
	}

        public int getImprimirYN() {
            return ImprimirYN;
        }

        public void setImprimirYN(int ImprimirYN) {
            this.ImprimirYN = ImprimirYN;
        }
	/**
	 * @return
	 */
	public Nota getNota() {
		return nota;
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
	public Circulo getCirculo() {
		return circulo;
	}

	/**
	 * @return
	 */
	public List getNotas() {
		return notas;
	}

	/**
	 * @return
	 */
	public String getUID() {
		return UID;
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
	public int getNumCopiasImpresion() {
		return numCopiasImpresion;
	}

	/**
	 * @param i
	 */
	public void setNumCopiasImpresion(int i) {
		numCopiasImpresion = i;
	}

	/**
	 * @return
	 */
	public Fase getFase() {
		return fase;
	}

	public boolean isInscripcionParcial() {
		return inscripcionParcial;
	}

	public void setInscripcionParcial(boolean inscripcionParcial) {
		this.inscripcionParcial = inscripcionParcial;
	}
	
	/**
	 * @return
	 */
	/*public Hashtable getValidacionAnotacionesTemporales() {
		return validacionAnotacionesTemporales;
	}*/

	/**
	 * @param hashtable
	 */
	/*public void setValidacionAnotacionesTemporales(Hashtable hashtable) {
		validacionAnotacionesTemporales = hashtable;
	}*/
	
	private Nota notaToBorrar;

	public Nota getNotaToBorrar() {
		return notaToBorrar;
	}

	public void setNotaToBorrar(Nota notaToBorrar) {
		this.notaToBorrar = notaToBorrar;
	}

}

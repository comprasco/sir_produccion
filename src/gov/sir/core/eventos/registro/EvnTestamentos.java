package gov.sir.core.eventos.registro;

import java.util.List;
import java.util.Vector;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadano;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas
 * con la inscripción de testamentos.
 * @author ppabon
 */
public class EvnTestamentos extends EvnSIR {

	/** Constante que identifica la acción de incribir el testamento*/
	public static final String REGISTRAR = "REGISTRAR";

	/** Constante que identifica la acción de devolver el  testamento*/
	public static final String DEVOLVER = "DEVOLVER";

	private Turno turno;

	private Fase fase;

	private String respuestaWF;

	private Testamento testamento;

	private Circulo circulo;
	
	private int numeroCopias = 1;
	
	private List notas = new Vector(); 
	
	private Nota nota = new Nota();
	
	/* Número de copias a imprimir por defecto */
	private int copiasImprimir = 1;
	
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;

	private TestamentoCiudadano testamentoCiudadano;
	
	/**
	 * Constructor para el envio de datos a la capa de negocio con el turno incluido.
	 * @param usuario
	 * @param tipoEvento
	 * @param turno
	 * @param usuarioSIR
	 */
	public EvnTestamentos(Usuario usuario, String tipoEvento, Turno turno, Fase fase, Circulo circulo, String respuestaWF, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.fase = fase;
		this.circulo = circulo;
		this.respuestaWF = respuestaWF;
		this.usuarioSIR = usuarioSIR;
	}

	/**
	 * Constructor para el envio de datos a la capa de negocio con el turno y el testamento incluido.
	 * @param usuario
	 * @param tipoEvento
	 * @param turno
	 * @param usuarioSIR
	 */
	public EvnTestamentos(Usuario usuario, String tipoEvento, Turno turno, Fase fase, Circulo circulo, String respuestaWF, Testamento testamento, gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.turno = turno;
		this.fase = fase;
		this.circulo = circulo;
		this.respuestaWF = respuestaWF;
		this.usuarioSIR = usuarioSIR;
		this.testamento = testamento;
	}

	/**
	 * Constructor para el envio de datos a la capa de negocio con la acción a ejecutar y el usuario
	 * @param usuario
	 * @param tipoEvento
	 */
	public EvnTestamentos(Usuario usuario, String tipoEvento) {
		super(usuario, tipoEvento);
	}
	
	/**
	  Modifica Pablo Quintana Junio 16 2008
	* Usado para avanzar el turno hacia la fase de confrontación.
	* @param usuario
	* @param turno
	* @param fase
	* @param usuarioNeg
	* @param respuestaWf
	* @param accion
	* @param idCirculo
	* @param motivoDevolucion motivo delegación del turno hacia correccion de encabezado.
	*/
   public EvnTestamentos(Usuario usuario, Turno turno, Fase fase,
	   gov.sir.core.negocio.modelo.Usuario usuarioNeg,String respuestaWf, String accion, String idCirculo, String motivoDevolucion,gov.sir.core.negocio.modelo.Usuario usuarioSIR)
	   {
		   super(usuario,accion);
		   this.turno = turno;
		   this.fase = fase;
		   this.usuarioNeg = usuarioNeg;
		   this.respuestaWF = respuestaWf;
		   this.idCirculo = idCirculo;
		   this.motivoDevolucion = motivoDevolucion;
		   this.usuarioSIR=usuarioSIR;
	   }
   
   private gov.sir.core.negocio.modelo.Usuario usuarioNeg;
   private String idCirculo = null;
   private String motivoDevolucion;
   /** Constante para delegar el turno hacia corrección*/
	public static final String CORRECCION = "CORRECCION";
	public static final String ERROR_ENCABEZADO="ERROR_ENCABEZADO";
   
	/** Constantes que identifica la accion de negocio agregar testador y eliminar */
	public static final String AGREGAR_TESTADOR="AGREGAR_TESTADOR";
	public static final String ELIMINAR_TESTADOR="ELIMINAR_TESTADOR";
	
	/**Constante para devolver el turno a confrontación*/
	public static final String DEVOLVER_A_CONFRONTACION="DEVOLVER_A_CONFRONTACION";
	
	public String getIdCirculo() {
		return idCirculo;
	}
	
	public void setIdCirculo(String idCirculo) {
		this.idCirculo = idCirculo;
	}
	
	public String getMotivoDevolucion() {
		return motivoDevolucion;
	}
	
	public void setMotivoDevolucion(String motivoDevolucion) {
		this.motivoDevolucion = motivoDevolucion;
	}
	
	
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}
	
	public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		this.usuarioNeg = usuarioNeg;
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
	public Testamento getTestamento() {
		return testamento;
	}

	/**
	 * @param testamento
	 */
	public void setTestamento(Testamento testamento) {
		this.testamento = testamento;
	}

	/**
	 * @return
	 */
	public Fase getFase() {
		return fase;
	}

	/**
	 * @return
	 */
	public String getRespuestaWF() {
		return respuestaWF;
	}

	/**
	 * @param fase
	 */
	public void setFase(Fase fase) {
		this.fase = fase;
	}

	/**
	 * @param string
	 */
	public void setRespuestaWF(String string) {
		respuestaWF = string;
	}

	/**
	 * @return
	 */
	public Circulo getCirculo() {
		return circulo;
	}

	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	/**
	 * @return
	 */
	public int getNumeroCopias() {
		return numeroCopias;
	}

	/**
	 * @param i
	 */
	public void setNumeroCopias(int i) {
		numeroCopias = i;
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
	public List getNotas() {
		return notas;
	}

	/**
	 * @param nota
	 */
	public void setNota(Nota nota) {
		this.nota = nota;
	}

	/**
	 * @param vector
	 */
	public void setNotas(List list) {
		notas = list;
	}

	public int getCopiasImprimir() {
		return copiasImprimir;
	}

	public void setCopiasImprimir(int copiasImprimir) {
		this.copiasImprimir = copiasImprimir;
	}

	public TestamentoCiudadano getTestamentoCiudadano() {
		return testamentoCiudadano;
	}

	public void setTestamentoCiudadano(TestamentoCiudadano testamentoCiudadano) {
		this.testamentoCiudadano = testamentoCiudadano;
	}
}

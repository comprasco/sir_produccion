/*
 * Created on 20-sep-2004
 */
package gov.sir.core.eventos.registro;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * @author jfrias, dsalas, ppabon
 */
public class EvnConfrontacion extends EvnSIR {
	
	/**Tipo de evento para asociar un rango de matriculas*/
	public static final String ASOCIAR_UN_RANGO = "ASOCIAR_UN_RANGO";

	/**Tipo de evento que se utiliza para asociar una matrícula*/
	public static final String ASOCIAR_UNA_MATRICULA = "ASOCIAR_UNA_MATRICULA ";
	
	/**Tipo de evento que se utiliza para asociar una matrícula*/
	public static final String ELIMINAR_UNA_MATRICULA = "ELIMINAR_UNA_MATRICULA ";
	
	/**Tipo de envento que se utiliza para confirmar la confrontacion*/
	public static final String CONFIRMAR = "CONFIRMAR";
	
	/**Constante que indica que se quiere actualizar los datos de antiguo sistema*/
	public final static String ACTUALIZAR_DATOS_ANTIGUO_SISTEMA = "ACTUALIZAR_DATOS_ANTIGUO_SISTEMA";	
	
	/**Constante que indica que se necesita guardar el subtipo de atencion*/
	public static final String GURDAR_CAMBIOS_CONFRONTACION = "GURDAR_CAMBIOS_CONFRONTACION";
	
	private Turno turno;
	private Fase fase;
	private Folio folio;
	private List folios;
	private String subTipoAtencion; 
	private String idMatriculaRL;
	private String idMatriculaRR;
        private String respuestaWF;
	private gov.sir.core.negocio.modelo.Usuario usuarioSir;
	private int tipoAvance;
	private DatosAntiguoSistema datosAntiguoSistema;
	
	/**
	 * Evento para añadir y quitar folios
	 * @param usuario
	 * @param folio
	 */
	public EvnConfrontacion(Usuario usuario, String tipo, Turno turno, Folio folio, gov.sir.core.negocio.modelo.Usuario userSir) {
		super(usuario, tipo);
		this.turno=turno;
		this.folio=folio;
		this.usuarioSir = userSir;
	}
	
	/** 
	 * Evento para actualizar los datos de antiguo sistema
	 * @param usuario
	 * @param folio
	 */
	public EvnConfrontacion(Usuario usuario, String tipo, Turno turno, DatosAntiguoSistema das , gov.sir.core.negocio.modelo.Usuario userSir) {
		super(usuario, tipo);
		this.turno=turno;
		this.datosAntiguoSistema=das;
		this.usuarioSir = userSir;
	}	
	
	
	/**
	 * Evento para añadir y quitar folios
	 * @param usuario
	 * @param folio
	 */
	public EvnConfrontacion(Usuario usuario, String tipo, Turno turno, List folios) {
		super(usuario, tipo);
		this.turno=turno;
		this.folios=folios;
	}
	
	/**
	 * @param usuario
	 * @param string
	 * @param turno
	 * @param idMatRL
	 * @param idMatRR
	 */
	public EvnConfrontacion(Usuario usuario, String tipo, Turno turno, String idMatriculaRL, String idMatriculaRR,
	gov.sir.core.negocio.modelo.Usuario userSir) {
		super(usuario,tipo);
		this.turno=turno;
		this.idMatriculaRL=idMatriculaRL;
		this.idMatriculaRR=idMatriculaRR;
		this.usuarioSir = userSir;
	}

	/**
	 * Evento para avanzar
	 * @param usuario
	 * @param tipo
	 * @param turno
	 */
	public EvnConfrontacion(Usuario usuario, String tipo, Turno turno, Fase fase, String subTipoAtencion, gov.sir.core.negocio.modelo.Usuario usuarioSIR, int tipoAvance) {
		super(usuario,tipo);
		this.turno=turno;
		this.fase=fase;
		this.subTipoAtencion=subTipoAtencion;
		this.usuarioSir = usuarioSIR;
		this.tipoAvance = tipoAvance;
	}

	/**
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @return
	 */
	public String getSubTipoAtencion() {
		return subTipoAtencion;
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
	public Fase getFase() {
		return fase;
	}
        
        public void setFase(Fase fase){
            this.fase = fase;
        }

	/**
	 * @return
	 */
	public String getIdMatriculaRL() {
		return idMatriculaRL;
	}

	/**
	 * @return
	 */
	public String getIdMatriculaRR() {
		return idMatriculaRR;
	}

	/**
	 * @return
	 */
	public List getFolios() {
		return folios;
	}
	
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSir()
	{
		return this.usuarioSir;
	}
	/**
	 * @return
	 */
	public int getTipoAvance() {
		return tipoAvance;
	}

	/**
	 * @return
	 */
	public DatosAntiguoSistema getDatosAntiguoSistema() {
		return datosAntiguoSistema;
	}

	/**
	 * @param sistema
	 */
	public void setDatosAntiguoSistema(DatosAntiguoSistema sistema) {
		datosAntiguoSistema = sistema;
	}

        public String getRespuestaWF() {
            return respuestaWF;
        }

        public void setRespuestaWF(String respuestaWF) {
            this.respuestaWF = respuestaWF;
        }
        
}

/*
 * Created on 23-feb-2005
 *
 */
package gov.sir.core.eventos.certificado;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.TurnoPk;

/**
 * @author gvillal
 * Esta clase representa el evento de cambio de una matricula de un folio.
 */
public class EvnCambioMatricula extends EvnSIR{

    public String matriculaActual;
	public String matriculaNueva;
    
	private String tipoCertificado;
	private Usuario usuario;
	private TurnoPk turno_id;
	private gov.sir.core.negocio.modelo.Usuario usuarioNeg;
	private boolean nacional;
		
	/** 
	 * Crea una nueva instancia de EvnValidacionMatricula 
	 * @param usuario el usuario que genera el evento
	 * @param matricula la matricula asociada al evento
	 **/
	public EvnCambioMatricula(Usuario usuario, String matriculaActual,String matriculaNueva, String tipoCertificado, TurnoPk tid, gov.sir.core.negocio.modelo.Usuario usurioNeg) {		
		super(usuario);
		this.usuarioNeg = usurioNeg;
		this.matriculaActual = matriculaActual;
		this.matriculaNueva = matriculaNueva;
		
		this.tipoCertificado = tipoCertificado;
		this.turno_id = tid;
	}



	/**
	 * @return
	 */
	public String getTipoCertificado() {
		return tipoCertificado;
	}

	/**
	 * @param string
	 */
	public void setTipoCertificado(String string) {
		tipoCertificado = string;
	}

	/**
	 * @return
	 */
	public Usuario getUsuario() {
		return this.usuario;
	}

	/**
	 * @return
	 */
	public TurnoPk getTurno_id() {
		return turno_id;
	}

	/**
	 * @param id
	 */
	public void setTurno_id(TurnoPk id) {
		turno_id = id;
	}

	/**
	 * @return
	 */
	public String getMatriculaActual() {
		return matriculaActual;
	}

	/**
	 * @return
	 */
	public String getMatriculaNueva() {
		return matriculaNueva;
	}

	/**
	 * @param string
	 */
	public void setMatriculaActual(String string) {
		matriculaActual = string;
	}

	/**
	 * @param string
	 */
	public void setMatriculaNueva(String string) {
		matriculaNueva = string;
	}

	/**
	 * @return
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
		return usuarioNeg;
	}

	public boolean isNacional() {
		return this.nacional;
	}

	public void setNacional(boolean nacional) {
		this.nacional = nacional;
	}

}

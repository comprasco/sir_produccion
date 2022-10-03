/*
 * EvnValidacionMatricula.java
 *
 * Created on 3 de enero de 2005, 19:10
 */

package gov.sir.core.eventos.certificado;

import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.eventos.comun.EvnSIR;
/**
 * Este evento envia una matricula a la capa de negocio para hacer la validacion 
 * respectiva.
 * @author  dcantor
 */
public class EvnValidacionMatricula extends EvnSIR {
    
    public static final String CERTIFICADO_ESPECIAL = "CERTIFICADO_ESPECIAL"; 
    
    private String matricula;
    private String tipoCertificado;
	private boolean turnoNacional;



	/** 
	 * Crea una nueva instancia de EvnValidacionMatricula 
	 * @param usuario el usuario que genera el evento
	 * @param matricula la matricula asociada al evento
	 *
	public EvnValidacionMatricula(Usuario usuario, String matricula) {
		super(usuario);
		this.matricula = matricula;
	}*/
    
    public void setTipoCertificado(String tipoCertificado) {
		this.tipoCertificado = tipoCertificado;
	}

	/** 
     * Crea una nueva instancia de EvnValidacionMatricula 
     * @param usuario el usuario que genera el evento
     * @param matricula la matricula asociada al evento
     * @param tipoCertificado el tipo de certificado asociado a este evento
     */
    public EvnValidacionMatricula(Usuario usuario, String matricula, String tipoCertificado) {
        super(usuario);
        this.matricula = matricula;
        this.tipoCertificado = tipoCertificado;
    }
    
	/** 
	 * Crea una nueva instancia de EvnValidacionMatricula 
	 * @param usuario el usuario que genera el evento
	 * @param matricula la matricula asociada al evento
	 * @param tipoCertificado el tipo de certificado asociado a este evento
     * @param tipoEvento el tipo de evento
	 */
	public EvnValidacionMatricula(Usuario usuario, String matricula, String tipoCertificado, String tipoEvento) {
		super(usuario, tipoEvento);
		this.matricula = matricula;
		this.tipoCertificado = tipoCertificado;
	}
	
    /**
     *Devuelve la matricula asociada a este evento
     *@return la matricula asociada
     */
    public String getMatricula(){
        return matricula;
    }
    
    /**
     *Devuelve el tipo de certificado asociado a este evento
     *@return el tipo de certificado asociado
     */
    public String getTipoCertificado(){
        return tipoCertificado;
    }

	public void setTurnoNacional(boolean turnoNacional) {
		this.turnoNacional = turnoNacional;
	}

	public boolean isTurnoNacional() {
		return turnoNacional;
	}
    
    
    
}
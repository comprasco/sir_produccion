package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */

/**
 * Clase que modela la auditoria de accesos de usuario al sistema
 */
public class Auditoria implements TransferObject{
private static final long serialVersionUID = 1L;
    private int idAuditoria; // pk 
    private String direccion;
    private Date fechaLogin;
    private Date fechaLogout;
    private String host;
    private String usuarioWeb;
    private Usuario usuario; // inverse Usuario.Auditoria
    private Circulo circulo; // inverse Circulo.auditoria

    /** Constructor por defecto */
    public Auditoria() {
    }

    /**
     * Retorna el identificador de auditoria
     * @return idAuditoria
     */
    public int getIdAuditoria() {
        return idAuditoria;
    }

    /**
     * Cambia el identificador de auditoria
     * @paranm idAuditoria
     */
    public void setIdAuditoria(int idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    /**
     * Retorna la direcci�n IP remota
     * @return direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Cambia la direcci�n IP remota
     * @paranm direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Retorna la fecha de loggin del usuario
     * @return fechaLogin
     */
    public Date getFechaLogin() {
        return fechaLogin;
    }

    /**
     * Cambia la fecha de loggin del usuario
     * @paranm fechaLogin
     */
    public void setFechaLogin(Date fechaLogin) {
        this.fechaLogin = fechaLogin;
    }

    /**
     * Retorna la fecha de logout del usuario
     * @return fechaLogout
     */
    public Date getFechaLogout() {
        return fechaLogout;
    }

    /**
     * Cambia la fecha de logout del usuario
     * @paranm fechaLogout
     */
    public void setFechaLogout(Date fechaLogout) {
        this.fechaLogout = fechaLogout;
    }

    /**
     * Retorna la direcci�n IP del cliente
     * @return host
     */
    public String getHost() {
        return host;
    }

    /**
     * Cambia la direcci�n IP del cliente
     * @paranm host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Retorna el identificador del usuario en la sesi�n
     * @return usuarioWeb
     */
    public String getUsuarioWeb() {
        return usuarioWeb;
    }

    /**
     * Cambia el identificador del usuario en la sesi�n
     * @paranm usuarioWeb
     */
    public void setUsuarioWeb(String usuarioWeb) {
        this.usuarioWeb = usuarioWeb;
    }

    /**
     * Retorna el identificador de usuario
     * @return usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Cambia el identificador de usuario
     * @paranm usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna el identificador de circulo
     * @return usuario
     */
    public Circulo getCirculo() {
		return circulo;
	}

    /**
     * Cambia el identificador de circulo
     * @paranm usuario
     */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}
}
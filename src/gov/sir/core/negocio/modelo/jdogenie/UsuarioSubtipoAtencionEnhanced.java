package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion;

import java.util.Date;

/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class UsuarioSubtipoAtencionEnhanced extends Enhanced {

    private String idSubtipoAtencion; // pk 
    private long idUsuario; // pk 
    private Date fechaCreacion;
    private SubtipoAtencionEnhanced subtipoatencion;
    private UsuarioEnhanced usuario; // inverse UsuarioEnhanced.subtiposAtencion
	private long orden;
	
    public UsuarioSubtipoAtencionEnhanced() {
    }

    public String getIdSubtipoAtencion() {
        return idSubtipoAtencion;
    }

    public void setIdSubtipoAtencion(String idSubtipoAtencion) {
        this.idSubtipoAtencion = idSubtipoAtencion;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public SubtipoAtencionEnhanced getSubtipoatencion() {
        return subtipoatencion;
    }

    public void setSubtipoatencion(SubtipoAtencionEnhanced subtipoatencion) {
        this.subtipoatencion = subtipoatencion;
        setIdSubtipoAtencion(subtipoatencion.getIdSubtipoAtencion());
    }

    public UsuarioEnhanced getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEnhanced usuario) {
        this.usuario = usuario;
        setIdUsuario(usuario.getIdUsuario());
    }
    
  
	public static UsuarioSubtipoAtencionEnhanced enhance(UsuarioSubtipoAtencion subtipoAtencion) {
		return (UsuarioSubtipoAtencionEnhanced)Enhanced.enhance(subtipoAtencion);
	}

    /**
	 * @return
	 */
	public long getOrden() {
		return orden;
	}

	/**
	 * @param l
	 */
	public void setOrden(long l) {
		orden = l;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
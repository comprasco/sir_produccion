package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela la informacion de las relaciones de los turnos SIR */
/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class Relacion implements TransferObject {

    private String idFase; // pk
    private String idRelacion; // pk
    private Date fecha;
    private TipoRelacion tipoRelacion;
    private Usuario usuario;
    private String nota;
    private String respuesta;
    private static final long serialVersionUID = 1L;
    /** Metodo constructor por defecto  */
    public Relacion() {
    }

    /** Obtiene el identificador de la fase en la que se creo la relacion */
    
    public String getIdFase() {
        return idFase;
    }

    /** Modifica el identificador de la fase en la que se creo la relacion */
    
    public void setIdFase(String idFase) {
        this.idFase = idFase;
    }

    /** Obtiene el identificador de la relacion */
    
    public String getIdRelacion() {
        return idRelacion;
    }

    /** Modifica el identificador de la relacion */
    
    public void setIdRelacion(String idRelacion) {
        this.idRelacion = idRelacion;
    }

    /** Obtiene la fecha de creacion de la relacion  */
    
    public Date getFecha() {
        return fecha;
    }

    /** Modifica la fecha de creacion de la relacion  */
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /** Obtiene el identificador del tipo de relacion  */
    
    public TipoRelacion getTipoRelacion() {
        return tipoRelacion;
    }

    /** Modifica el identificador del tipo de relacion  */
    
    public void setTipoRelacion(TipoRelacion tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    /** Obtiene el identificador del usuario  */
    
    public Usuario getUsuario() {
        return usuario;
    }

    /** Modifica el identificador del usuario  */
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String getNota() {
    	return nota;
    }
    
    public void setNota(String nota) {
    	this.nota = nota;
    }

    public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}

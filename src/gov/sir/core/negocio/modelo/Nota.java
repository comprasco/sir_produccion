package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/* Generated by JDO Genie (version:3.0.0 (08 Jun 2004)) */

/** Clase que modela las notas informativas asociadas al turno  */

public class Nota  implements TransferObject, Cloneable{

    private String anio; // pk 
    private String idCirculo; // pk 
    private String idNota; // pk 
    private long idProceso; // pk 
    private String idTurno; // pk 
    private String descripcion;
    private Date time;
    private TipoNota tiponota;
    private Turno turno;
    private Usuario usuario;
    private String idFase;
    private static final long serialVersionUID = 1L;
    private String idTurnoHistoria;

    /** Constructor por defecto */
    public Nota() {
    }

    /** Retorna el identificador del turno correspondiente al a?o  */
    public String getAnio() {
        return anio;
    }

    /** Cambia el identificador del turno correspondiente al a?o  */
    public void setAnio(String anio) {
        this.anio = anio;
    }

    /** Retorna el identificador del turno correspondiente al c?rculo  */
    public String getIdCirculo() {
        return idCirculo;
    }

    /** Cambia el identificador del turno correspondiente al c?rculo  */
    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    /** Retorna el identificador de la nota informativa asociada al turno */
    public String getIdNota() {
        return idNota;
    }

    /** Cambia el identificador de la nota informativa asociada al turno */
    public void setIdNota(String idNota) {
        this.idNota = idNota;
    }

    /** Retorna el identificador del turno correspondiente al proceso */
    public long getIdProceso() {
        return idProceso;
    }

    /** Cambia el identificador del turno correspondiente al proceso */
    public void setIdProceso(long idProceso) {
        this.idProceso = idProceso;
    }

    /** Retorna el identificador del turno  */
    public String getIdTurno() {
        return idTurno;
    }

    /** Cambia el identificador del turno  */
    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    /** Retorna la descripci?n de la nota informativa  */
    public String getDescripcion() {
        return descripcion;
    }

    /** Cambia la descripci?n de la nota informativa  */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** Retorna la fecha en que se realiza la nota informativa  */
    public Date getTime() {
        return time;
    }

    /** Cambia la fecha en que se realiza la nota informativa  */
    public void setTime(Date time) {
        this.time = time;
    }

    /** Retorna el identificador del tipo de nota asociado  */
    public TipoNota getTiponota() {
        return tiponota;
    }

    /** Modifica el identificador del tipo de nota asociado  */
    public void setTiponota(TipoNota tiponota) {
        this.tiponota = tiponota;
    }

    /** Retorna el identificador del turno  */
    public Turno getTurno() {
        return turno;
    }

    /** Cambia el identificador del turno  */
    public void setTurno(Turno turno) {
        this.turno = turno;
        setAnio(turno.getAnio());
        setIdCirculo(turno.getIdCirculo());
        setIdProceso(turno.getIdProceso());
        setIdTurno(turno.getIdTurno());
    }

    /** Retorna el identificador del usuario que crea la nota  */
    public Usuario getUsuario() {
        return usuario;
    }

    /** Cambia el identificador del usuario que crea la nota  */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /** Retorna el identificador de la fase en la que se realiza la nota informativa 
	 * @return idfase
	 */
	public String getIdFase() {
		return idFase;
	}

	/** Cambia el identificador de la fase en la que se realiza la nota informativa
	 * @param string
	 */
	public void setIdFase(String string) {
		idFase = string;
	}

	/** Retorna el identificador informativo del turno historia en que se registra la nota
	 * @return idTurnoHistoria
	 */
	public String getIdTurnoHistoria() {
		return idTurnoHistoria;
	}

	/** Cambia el identificador informativo del turno historia en que se registra la nota
	 * @param string
	 */
	public void setIdTurnoHistoria(String string) {
		idTurnoHistoria = string;
	}
        
         @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }   

}
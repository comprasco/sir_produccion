package gov.sir.core.negocio.modelo.jdogenie;

import java.util.Date;
import gov.sir.core.negocio.modelo.Nota;



/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class NotaEnhanced  extends Enhanced {

    private String anio; // pk 
    private String idCirculo; // pk 
    private String idNota; // pk 
    private long idProceso; // pk 
    private String idTurno; // pk 
    private String descripcion;
    private Date time;
    private TipoNotaEnhanced tiponota;
    private TurnoEnhanced turno;
    private UsuarioEnhanced usuario;
    private String idFase;
    private String idTurnoHistoria;

    public NotaEnhanced() {
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    public String getIdNota() {
        return idNota;
    }

    public void setIdNota(String idNota) {
        this.idNota = idNota;
    }

    public long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(long idProceso) {
        this.idProceso = idProceso;
    }

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public TipoNotaEnhanced getTiponota() {
        return tiponota;
    }

    public void setTiponota(TipoNotaEnhanced tiponota) {
        this.tiponota = tiponota;
    }

    public TurnoEnhanced getTurno() {
        return turno;
    }

    public void setTurno(TurnoEnhanced turno) {
        this.turno = turno;
        setAnio(turno.getAnio());
        setIdCirculo(turno.getIdCirculo());
        setIdProceso(turno.getIdProceso());
        setIdTurno(turno.getIdTurno());
    }

    public UsuarioEnhanced getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEnhanced usuario) {
        this.usuario = usuario;
    }
    
    public static NotaEnhanced enhance(Nota nota){
        return (NotaEnhanced)Enhanced.enhance(nota);
    }
    
    /**
	 * @return
	 */
	public String getIdFase() {
		return idFase;
	}

	/**
	 * @param string
	 */
	public void setIdFase(String string) {
		idFase = string;
	}

	/**
	 * @return
	 */
	public String getIdTurnoHistoria() {
		return idTurnoHistoria;
	}

	/**
	 * @param string
	 */
	public void setIdTurnoHistoria(String string) {
		idTurnoHistoria = string;
	}

}
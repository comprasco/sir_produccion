package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela el registro de los turnos en que se crean los folios  */
/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class TurnoFolio implements TransferObject{

    private String anio; // pk 
    private String idCirculo; // pk 
    private String idMatricula; // pk 
    private long idProceso; // pk 
    private String idTurno; // pk 
    private Date fechaApertura;
    private Folio folio; // inverse Folio.turnosFolio
    private Turno turno;
    private boolean nacional;
    private static final long serialVersionUID = 1L;
    public TurnoFolio() {
    }

    /** Retorna el identificador del turno correspondiente al anio */
    
    public String getAnio() {
        return anio;
    }
    /** Modifica el identificador del turno correspondiente al anio */
    
    public void setAnio(String anio) {
        this.anio = anio;
    }

    /** Retorna el identificador del turno correspondiente al circulo  */
    
    public String getIdCirculo() {
        return idCirculo;
    }

    /** Modifica el identificador del turno correspondiente al circulo  */
    
    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    /** Retorna el identificador del folio */
    
    public String getIdMatricula() {
        return idMatricula;
    }

    /** Modifica el identificador del folio */
    
    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }

    /** Retorna el identificador del turno correspondiente al proceso  */
    
    public long getIdProceso() {
        return idProceso;
    }

    /** Modifica el identificador del turno correspondiente al proceso  */
    
    public void setIdProceso(long idProceso) {
        this.idProceso = idProceso;
    }

    /** Retorna el identificador del turno  */
    
    public String getIdTurno() {
        return idTurno;
    }

    /** Modifica el identificador del turno  */
    
    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    /** Retorna la fecha de creacion del registro en la base de datos  */
    
    public Date getFechaApertura() {
        return fechaApertura;
    }

    /** Modifica la fecha de creacion del registro en la base de datos  */
    
    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    /** Retorna el identificador del folio  */
    
    public Folio getFolio() {
        return folio;
    }

    /** Modifica el identificador del folio  */
    
    public void setFolio(Folio folio) {
        this.folio = folio;
        setIdMatricula(folio.getIdMatricula());
    }

    /** Retorna el identificador del turno relativo al anio, circulo y proceso  */
    
    public Turno getTurno() {
        return turno;
    }

    /** Modifica el identificador del turno relativo al anio, circulo y proceso  */
    
    public void setTurno(Turno turno) {
        this.turno = turno;
        setAnio(turno.getAnio());
        setIdCirculo(turno.getIdCirculo());
        setIdProceso(turno.getIdProceso());
        setIdTurno(turno.getIdTurno());
    }

	public boolean isNacional() {
		return nacional;
	}

	public void setNacional(boolean nacional) {
		this.nacional = nacional;
	}
}
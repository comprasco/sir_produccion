package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela el registro de los turnos en que se crean las salvedades de anotaciones  */ 
/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class TurnoSalvedadAnotacion implements TransferObject{

    private String anio; // pk 
    private String idAnotacion; // pk 
    private String idCirculo; // pk 
    private String idMatricula; // pk 
    private long idProceso; // pk 
    private String idSalvedadAn; // pk 
    private String idTurno; // pk 
    private Date fechaCreacion;
    private SalvedadAnotacion salvedad; // inverse SalvedadAnotacion.turnoSalvedadAnotacion
    private Turno turno;
    private static final long serialVersionUID = 1L;
    /** Metodo constructor por defecto  */
    
    public TurnoSalvedadAnotacion() {
    }

    /** Retorna el identificador del turno correspondiente al anio   */
    
    public String getAnio() {
        return anio;
    }

    /** Modifica el identificador del turno correspondiente al anio   */
    
    public void setAnio(String anio) {
        this.anio = anio;
    }

    /** Retorna el identificador de la salvedad de la anotacion correspondiente a la anotacion  */
    
    public String getIdAnotacion() {
        return idAnotacion;
    }

    /** Modifica el identificador de la salvedad de la anotacion correspondiente a la anotacion  */
    
    public void setIdAnotacion(String idAnotacion) {
        this.idAnotacion = idAnotacion;
    }

    /**Retorna el identificador del turno correspondiente al circulo  */
    
    public String getIdCirculo() {
        return idCirculo;
    }

    /**Modifica el identificador del turno correspondiente al circulo  */
    
    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    /** Retorna el identificador de la salvedad de la anotacion correspondiente a la matricula  */
    
    public String getIdMatricula() {
        return idMatricula;
    }

    /** Modifica el identificador de la salvedad de la anotacion correspondiente a la matricula  */
    
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

    /** Retorna el identificador de la salvedad de la anotacion  */
    
    public String getIdSalvedadAn() {
        return idSalvedadAn;
    }

    /** Modifica el identificador de la salvedad de la anotacion  */
    
    public void setIdSalvedadAn(String idSalvedadAn) {
        this.idSalvedadAn = idSalvedadAn;
    }

    /** Retorna el identificador del turno  */
    
    public String getIdTurno() {
        return idTurno;
    }

    /** Modifica el identificador del turno  */
    
    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    /** Retorna la fecha de creaci�n del registro en la base de datos  */
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /** Modifica la fecha de creaci�n del registro en la base de datos  */
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** Retorna el identificador de la salvedad relativa a la anotacion  */
    
    public SalvedadAnotacion getSalvedad() {
        return salvedad;
    }

    /** Modifica el identificador de la salvedad relativa a la anotacion  */
    
    public void setSalvedad(SalvedadAnotacion salvedad) {
        this.salvedad = salvedad;
        setIdMatricula(salvedad.getIdMatricula());
        setIdSalvedadAn(salvedad.getIdSalvedad());
        setIdAnotacion(salvedad.getIdAnotacion());
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
}
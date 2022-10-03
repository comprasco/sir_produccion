package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ControlMatricula;


/**
 ** @Author Cristian David Garcia
 ** @Requerimiento Agregar Eliminar Matriculas
 */
public class ControlMatriculaEnhanced extends Enhanced {

    private String idMatricula; //pk
    private String accion;
    private String rol;
    private String turno;

    public ControlMatriculaEnhanced() {
    }

    public String getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public static ControlMatriculaEnhanced enhance(ControlMatricula controlMatricula) {
        return (ControlMatriculaEnhanced) Enhanced.enhance(controlMatricula);
    }

}

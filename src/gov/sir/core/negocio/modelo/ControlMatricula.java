package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 ** @Author Cristian David Garcia
 ** @Requerimiento Agregar Eliminar Matriculas
 */
public class ControlMatricula implements TransferObject {

    private String idMatricula; //pk
    private String accion;
    private String rol;
    private String turno;

    public ControlMatricula() {
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

}

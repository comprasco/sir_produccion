package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ControlReasignacion;


/**
 ** @Author Cristian David Garcia
 ** @Requerimiento Agregar Eliminar Matriculas
 */
public class ControlReasignacionEnhanced extends Enhanced {

    private String idWorkflow; //pk
    private String fase;
    private String usuarioOrigen;
    private String usuarioDestino;

    public ControlReasignacionEnhanced() {
    }

    public String getIdWorkflow() {
        return idWorkflow;
    }

    public void setIdWorkflow(String idWorkflow) {
        this.idWorkflow = idWorkflow;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getUsuarioOrigen() {
        return usuarioOrigen;
    }

    public void setUsuarioOrigen(String usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
    }

    public String getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(String usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    

    public static ControlReasignacionEnhanced enhance(ControlReasignacion controlReasignacion) {
        return (ControlReasignacionEnhanced) Enhanced.enhance(controlReasignacion);
    }

}

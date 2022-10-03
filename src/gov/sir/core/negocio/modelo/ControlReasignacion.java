package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
 ** @Author Cristian David Garcia
 ** @Requerimiento Agregar Eliminar Matriculas
 */
public class ControlReasignacion implements TransferObject {

    private String idWorkflow; //pk
    private String fase;
    private String usuarioOrigen;
    private String usuarioDestino;

    public ControlReasignacion() {
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

    

}

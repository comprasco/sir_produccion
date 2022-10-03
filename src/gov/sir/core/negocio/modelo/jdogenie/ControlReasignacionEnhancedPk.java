package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ControlReasignacionPk;


/**
** @Author Cristian David Garcia
** @Requerimiento Catastro Multiproposito
**  Application identity objectid-class.
*/
public class ControlReasignacionEnhancedPk implements java.io.Serializable{
    
    public String idWorkflow;

    public ControlReasignacionEnhancedPk() {
    }

    public ControlReasignacionEnhancedPk(ControlReasignacionPk id) {
        idWorkflow = id.idWorkflow;
    }

    public ControlReasignacionEnhancedPk(String s) {
    	idWorkflow = s;
    }

    public ControlReasignacionPk getControlMatriculaID() {
        ControlReasignacionPk id = new ControlReasignacionPk();
        id.idWorkflow = idWorkflow;
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ControlReasignacionEnhancedPk)) {
            return false;
        }

        final ControlReasignacionEnhancedPk id = (ControlReasignacionEnhancedPk) o;

        if ((this.idWorkflow != null)
                ? (!idWorkflow.equals(id.idWorkflow))
                    : (id.idWorkflow != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idWorkflow != null) ? idWorkflow.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idWorkflow);
        return buffer.toString();
    }
    
}

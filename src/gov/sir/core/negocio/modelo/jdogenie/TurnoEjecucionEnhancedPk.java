package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TurnoEjecucionPk;

/**
 * Application identity objectid-class.
 */
public class TurnoEjecucionEnhancedPk implements java.io.Serializable {
    public String idWorkflow;

    public TurnoEjecucionEnhancedPk() {
    }

    public TurnoEjecucionEnhancedPk(TurnoEjecucionPk id) {
    	idWorkflow = id.idWorkflow;
    }

    public TurnoEjecucionEnhancedPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idWorkflow = s.substring(p, i);
    }

    public TurnoEjecucionPk getTurnoEjecucionID() {
        TurnoEjecucionPk rta = new TurnoEjecucionPk();
        rta.idWorkflow = idWorkflow;
        return rta;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TurnoEjecucionEnhancedPk)) {
            return false;
        }

        final TurnoEjecucionEnhancedPk id = (TurnoEjecucionEnhancedPk) o;

        if ((this.idWorkflow != null) ? (!idWorkflow.equals(id.idWorkflow)) : (id.idWorkflow != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + ((idWorkflow != null) ? idWorkflow.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idWorkflow);
        return buffer.toString();
    }
}
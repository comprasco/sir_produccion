package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Turno */

public class TurnoEjecucionPk implements java.io.Serializable {
	
    public String idWorkflow;


    public TurnoEjecucionPk() {
    }

    public TurnoEjecucionPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idWorkflow = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TurnoEjecucionPk)) {
            return false;
        }

        final TurnoEjecucionPk id = (TurnoEjecucionPk) o;

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
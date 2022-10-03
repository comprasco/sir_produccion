package gov.sir.hermod.dao.impl.jdogenie;

/**
* Application identity objectid-class.
*/
public class Procesos_VPk implements java.io.Serializable {
    public String id_proceso;
    public String id_fase;

    public Procesos_VPk() {
    }

    public Procesos_VPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        id_proceso = s.substring(p, i);
        p = i + 1;
        id_fase = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Procesos_VPk)) {
            return false;
        }

        final Procesos_VPk id = (Procesos_VPk) o;

        if ((this.id_proceso != null)
                ? (!id_proceso.equals(id.id_proceso))
                    : (id.id_proceso != null)) {
            return false;
        }
                    
        if ((this.id_fase != null)
                ? (!id_fase.equals(id.id_fase))
                    : (id.id_fase != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((id_proceso != null) ? id_proceso.hashCode() : 0);
        result = (29 * result) +
            ((id_fase != null) ? id_fase.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(id_proceso);
        buffer.append('-');
        buffer.append(id_fase);

        return buffer.toString();
    }
}
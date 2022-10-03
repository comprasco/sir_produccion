package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Proceso	  */
public class ProcesoRepartoPk implements java.io.Serializable {

    public String idProcesoReparto;

    public ProcesoRepartoPk() {
    }

    public ProcesoRepartoPk(String s) {
        int i, p = 0;
        idProcesoReparto = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcesoRepartoPk)) return false;

        final ProcesoRepartoPk id = (ProcesoRepartoPk) o;

        if (this.idProcesoReparto != null ? !idProcesoReparto.equals(id.idProcesoReparto) : id.idProcesoReparto != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idProcesoReparto != null ? idProcesoReparto.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idProcesoReparto);
        return buffer.toString();
    }
}
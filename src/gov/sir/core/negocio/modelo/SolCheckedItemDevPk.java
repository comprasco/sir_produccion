package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class SolCheckedItemDevPk implements java.io.Serializable {

    public String idCheckItemDev;
    public String idSolicitud;

    public SolCheckedItemDevPk() {
    }

    public SolCheckedItemDevPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCheckItemDev = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolCheckedItemDevPk)) return false;

        final SolCheckedItemDevPk id = (SolCheckedItemDevPk) o;

        if (this.idCheckItemDev != null ? !idCheckItemDev.equals(id.idCheckItemDev) : id.idCheckItemDev != null) return false;
        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCheckItemDev != null ? idCheckItemDev.hashCode() : 0);
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCheckItemDev);
        buffer.append('-');
        buffer.append(idSolicitud);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class WebSegEngPk implements java.io.Serializable {

    public String idSolicitud;
    public String idWebSegeng;

    public WebSegEngPk() {
    }

    public WebSegEngPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSolicitud = s.substring(p, i);
        p = i + 1;
        idWebSegeng = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebSegEngPk)) return false;

        final WebSegEngPk id = (WebSegEngPk) o;

        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        if (this.idWebSegeng != null ? !idWebSegeng.equals(id.idWebSegeng) : id.idWebSegeng != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        result = 29 * result + (idWebSegeng != null ? idWebSegeng.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSolicitud);
        buffer.append('-');
        buffer.append(idWebSegeng);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class WebFolioHeredadoPk implements java.io.Serializable {

    public String idMatricula;
    public String idSolicitud;
    public String idWebSegeng;

    public WebFolioHeredadoPk() {
    }

    public WebFolioHeredadoPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idMatricula = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idSolicitud = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idWebSegeng = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebFolioHeredadoPk)) return false;

        final WebFolioHeredadoPk id = (WebFolioHeredadoPk) o;

        if (this.idMatricula != null ? !idMatricula.equals(id.idMatricula) : id.idMatricula != null) return false;
        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        if (this.idWebSegeng != null ? !idWebSegeng.equals(id.idWebSegeng) : id.idWebSegeng != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idMatricula != null ? idMatricula.hashCode() : 0);
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        result = 29 * result + (idWebSegeng != null ? idWebSegeng.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idMatricula);
        buffer.append('-');
        buffer.append(idSolicitud);
        buffer.append('-');
        buffer.append(idWebSegeng);
        return buffer.toString();
    }
}
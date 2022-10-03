package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class WebDireccionEnhancedPk implements java.io.Serializable {

    public String idSolicitud;
    public String idWebDireccion;
    public String idWebSegeng;

    public WebDireccionEnhancedPk() {
    }

    public WebDireccionEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSolicitud = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idWebDireccion = s.substring(p, i);
        p = i + 1;
        idWebSegeng = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebDireccionEnhancedPk)) return false;

        final WebDireccionEnhancedPk id = (WebDireccionEnhancedPk) o;

        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        if (this.idWebDireccion != null ? !idWebDireccion.equals(id.idWebDireccion) : id.idWebDireccion != null) return false;
        if (this.idWebSegeng != null ? !idWebSegeng.equals(id.idWebSegeng) : id.idWebSegeng != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        result = 29 * result + (idWebDireccion != null ? idWebDireccion.hashCode() : 0);
        result = 29 * result + (idWebSegeng != null ? idWebSegeng.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSolicitud);
        buffer.append('-');
        buffer.append(idWebDireccion);
        buffer.append('-');
        buffer.append(idWebSegeng);
        return buffer.toString();
    }
}
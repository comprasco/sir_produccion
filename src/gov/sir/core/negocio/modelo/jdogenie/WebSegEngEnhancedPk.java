package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.WebSegEngPk;

/**
 * Application identity objectid-class.
 */
public class WebSegEngEnhancedPk implements java.io.Serializable {

    public String idSolicitud;
    public String idWebSegeng;

    public WebSegEngEnhancedPk() {
    }

    public WebSegEngEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSolicitud = s.substring(p, i);
        p = i + 1;
        idWebSegeng = s.substring(p);
    }
    
    public WebSegEngEnhancedPk(WebSegEngPk oid){
    	idSolicitud = oid.idSolicitud;
    	idWebSegeng = oid.idWebSegeng;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebSegEngEnhancedPk)) return false;

        final WebSegEngEnhancedPk id = (WebSegEngEnhancedPk) o;

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
package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class CiudadanoTMPPk implements java.io.Serializable {

    public String idCiudadanoTmp;

    public CiudadanoTMPPk() {
    }

    public CiudadanoTMPPk(String s) {
        int i, p = 0;
        idCiudadanoTmp = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CiudadanoTMPPk)) return false;

        final CiudadanoTMPPk id = (CiudadanoTMPPk) o;

        if (this.idCiudadanoTmp != null ? !idCiudadanoTmp.equals(id.idCiudadanoTmp) : id.idCiudadanoTmp != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCiudadanoTmp != null ? idCiudadanoTmp.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCiudadanoTmp);
        return buffer.toString();
    }
}
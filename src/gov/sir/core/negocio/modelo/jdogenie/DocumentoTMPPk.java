package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 */
public class DocumentoTMPPk implements java.io.Serializable {

    public String idDocumentoTmp;

    public DocumentoTMPPk() {
    }

    public DocumentoTMPPk(String s) {
        int i, p = 0;
        idDocumentoTmp = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentoTMPPk)) return false;

        final DocumentoTMPPk id = (DocumentoTMPPk) o;

        if (this.idDocumentoTmp != null ? !idDocumentoTmp.equals(id.idDocumentoTmp) : id.idDocumentoTmp != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idDocumentoTmp != null ? idDocumentoTmp.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDocumentoTmp);
        return buffer.toString();
    }
}
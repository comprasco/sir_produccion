package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Documento */
public class DocumentoPk implements java.io.Serializable {
    public String idDocumento;

    public DocumentoPk() {
    }

    public DocumentoPk(String s) {
        int i;
        int p = 0;
        idDocumento = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DocumentoPk)) {
            return false;
        }

        final DocumentoPk id = (DocumentoPk) o;

        if ((this.idDocumento != null)
                ? (!idDocumento.equals(id.idDocumento))
                    : (id.idDocumento != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idDocumento != null) ? idDocumento.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDocumento);

        return buffer.toString();
    }
}
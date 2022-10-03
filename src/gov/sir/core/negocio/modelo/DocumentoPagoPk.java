package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a DocumentoPago   */

public class DocumentoPagoPk implements java.io.Serializable {
    public String idDocumentoPago;

    public DocumentoPagoPk() {
    }

    public DocumentoPagoPk(String s) {
        int i;
        int p = 0;
        idDocumentoPago = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DocumentoPagoPk)) {
            return false;
        }

        final DocumentoPagoPk id = (DocumentoPagoPk) o;

        if ((this.idDocumentoPago != null)
                ? (!idDocumentoPago.equals(id.idDocumentoPago))
                    : (id.idDocumentoPago != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idDocumentoPago != null) ? idDocumentoPago.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDocumentoPago);

        return buffer.toString();
    }
}
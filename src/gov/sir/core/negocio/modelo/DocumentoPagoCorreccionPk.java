package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a DocumentoPago   */

public class DocumentoPagoCorreccionPk implements java.io.Serializable {
    public String idDocumentoPagoCorreccion;

    public DocumentoPagoCorreccionPk() {
    }

    public DocumentoPagoCorreccionPk(String s) {
        int i;
        int p = 0;
        idDocumentoPagoCorreccion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DocumentoPagoCorreccionPk)) {
            return false;
        }

        final DocumentoPagoCorreccionPk id = (DocumentoPagoCorreccionPk) o;

        if ((this.idDocumentoPagoCorreccion != null)
                ? (!idDocumentoPagoCorreccion.equals(id.idDocumentoPagoCorreccion))
                    : (id.idDocumentoPagoCorreccion != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idDocumentoPagoCorreccion != null) ? idDocumentoPagoCorreccion.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDocumentoPagoCorreccion);

        return buffer.toString();
    }
}
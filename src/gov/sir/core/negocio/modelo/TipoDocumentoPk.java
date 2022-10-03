package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a TipoDocumento
 */
public class TipoDocumentoPk implements java.io.Serializable {

    public String idTipoDocumento;

    public TipoDocumentoPk() {
    }

    public TipoDocumentoPk(String s) {
        int i;
        int p = 0;
        idTipoDocumento = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TipoDocumentoPk)) {
            return false;
        }

        final TipoDocumentoPk id = (TipoDocumentoPk) o;

        if ((this.idTipoDocumento != null)
                ? (!idTipoDocumento.equals(id.idTipoDocumento))
                : (id.idTipoDocumento != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result)
                + ((idTipoDocumento != null) ? idTipoDocumento.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoDocumento);

        return buffer.toString();
    }
}

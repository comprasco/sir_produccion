package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoOficina */

public class TipoOficinaPk implements java.io.Serializable {
    public String idTipoOficina;

    public TipoOficinaPk() {
    }

    public TipoOficinaPk(String s) {
        int i;
        int p = 0;
        idTipoOficina = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TipoOficinaPk)) {
            return false;
        }

        final TipoOficinaPk id = (TipoOficinaPk) o;

        if ((this.idTipoOficina != null)
                ? (!idTipoOficina.equals(id.idTipoOficina))
                    : (id.idTipoOficina != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idTipoOficina != null) ? idTipoOficina.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoOficina);

        return buffer.toString();
    }
}
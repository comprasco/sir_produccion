package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoPredio */

public class TipoPredioPk implements java.io.Serializable {
    public String idPredio;

    public TipoPredioPk() {
    }

    public TipoPredioPk(String s) {
        int i;
        int p = 0;
        idPredio = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TipoPredioPk)) {
            return false;
        }

        final TipoPredioPk id = (TipoPredioPk) o;

        if ((this.idPredio != null) ? (!idPredio.equals(id.idPredio))
                                        : (id.idPredio != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idPredio != null) ? idPredio.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idPredio);

        return buffer.toString();
    }
}
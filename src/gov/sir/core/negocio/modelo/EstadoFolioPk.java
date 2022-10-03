package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a EstadoFolio  */
public class EstadoFolioPk implements java.io.Serializable {
    public String idEstado;

    public EstadoFolioPk() {
    }

    public EstadoFolioPk(String s) {
        int i;
        int p = 0;
        idEstado = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof EstadoFolioPk)) {
            return false;
        }

        final EstadoFolioPk id = (EstadoFolioPk) o;

        if ((this.idEstado != null) ? (!idEstado.equals(id.idEstado))
                                        : (id.idEstado != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idEstado != null) ? idEstado.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idEstado);

        return buffer.toString();
    }
}
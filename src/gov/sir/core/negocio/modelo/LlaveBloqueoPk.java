package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a LlaveBloqueo  */
public class LlaveBloqueoPk implements java.io.Serializable {
    public String idLlave;

    public LlaveBloqueoPk() {
    }

    public LlaveBloqueoPk(String s) {
        int i;
        int p = 0;
        idLlave = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof LlaveBloqueoPk)) {
            return false;
        }

        final LlaveBloqueoPk id = (LlaveBloqueoPk) o;

        if ((this.idLlave != null) ? (!idLlave.equals(id.idLlave))
                                       : (id.idLlave != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idLlave != null) ? idLlave.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idLlave);

        return buffer.toString();
    }
}
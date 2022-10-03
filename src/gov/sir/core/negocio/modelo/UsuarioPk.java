package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Usuario */

public class UsuarioPk implements java.io.Serializable {
    public long idUsuario;

    public UsuarioPk() {
    }

    public UsuarioPk(String s) {
        int i;
        int p = 0;
        idUsuario = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof UsuarioPk)) {
            return false;
        }

        final UsuarioPk id = (UsuarioPk) o;

        if (this.idUsuario != id.idUsuario) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) idUsuario;

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idUsuario);

        return buffer.toString();
    }
}
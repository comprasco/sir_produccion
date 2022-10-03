package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.UsuarioPk;

/**
* Application identity objectid-class.
*/
public class UsuarioEnhancedPk implements java.io.Serializable {
    public long idUsuario;

    public UsuarioEnhancedPk() {
    }

    public UsuarioEnhancedPk(UsuarioPk id) {
        idUsuario = id.idUsuario;
    }

    public UsuarioEnhancedPk(String s) {
        int i;
        int p = 0;
        idUsuario = Long.parseLong(s.substring(p));
    }

    public UsuarioPk getUsuarioID() {
        UsuarioPk rta = new UsuarioPk();
        rta.idUsuario = idUsuario;

        return rta;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof UsuarioEnhancedPk)) {
            return false;
        }

        final UsuarioEnhancedPk id = (UsuarioEnhancedPk) o;

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
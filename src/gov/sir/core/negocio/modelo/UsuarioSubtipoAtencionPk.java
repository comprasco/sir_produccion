package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a UsuarioSubtipoAtencion */

public class UsuarioSubtipoAtencionPk implements java.io.Serializable {

    public String idSubtipoAtencion;
    public long idUsuario;

    public UsuarioSubtipoAtencionPk() {
    }

    public UsuarioSubtipoAtencionPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSubtipoAtencion = s.substring(p, i);
        p = i + 1;
        idUsuario = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioSubtipoAtencionPk)) return false;

        final UsuarioSubtipoAtencionPk id = (UsuarioSubtipoAtencionPk) o;

        if (this.idSubtipoAtencion != null ? !idSubtipoAtencion.equals(id.idSubtipoAtencion) : id.idSubtipoAtencion != null) return false;
        if (this.idUsuario != id.idUsuario) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSubtipoAtencion != null ? idSubtipoAtencion.hashCode() : 0);
        result = 29 * result + (int) (idUsuario ^ (idUsuario >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSubtipoAtencion);
        buffer.append('-');
        buffer.append(idUsuario);
        return buffer.toString();
    }
}
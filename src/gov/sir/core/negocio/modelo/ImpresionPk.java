package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Impresion  */
public class ImpresionPk implements java.io.Serializable {

    public String idSesion;

    public ImpresionPk() {
    }

    public ImpresionPk(String s) {
        int i, p = 0;
        idSesion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImpresionPk)) return false;

        final ImpresionPk id = (ImpresionPk) o;

        if (this.idSesion != null ? !idSesion.equals(id.idSesion) : id.idSesion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSesion != null ? idSesion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSesion);
        return buffer.toString();
    }
}
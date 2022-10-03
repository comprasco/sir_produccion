package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a Categoria
 */
public class CategoriaPk implements java.io.Serializable {

    public String idCategoria;

    public CategoriaPk() {
    }

    public CategoriaPk(String s) {
        int i, p = 0;
        idCategoria = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaPk)) return false;

        final CategoriaPk id = (CategoriaPk) o;

        if (this.idCategoria != null ? !idCategoria.equals(id.idCategoria) : id.idCategoria != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCategoria != null ? idCategoria.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCategoria);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a Categoria
 */
public class CategoriaNotariaPk implements java.io.Serializable {

    public String idCategoria;

    public CategoriaNotariaPk() {
    }

    public CategoriaNotariaPk(String s) {
        int i, p = 0;
        idCategoria = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaNotariaPk)) return false;

        final CategoriaNotariaPk id = (CategoriaNotariaPk) o;

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
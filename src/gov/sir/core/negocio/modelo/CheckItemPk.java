package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a CheckItem
 */
public class CheckItemPk implements java.io.Serializable {

    public String idCheckItem;
    public String idSubtipoSol;

    public CheckItemPk() {
    }

    public CheckItemPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCheckItem = s.substring(p, i);
        p = i + 1;
        idSubtipoSol = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckItemPk)) return false;

        final CheckItemPk id = (CheckItemPk) o;

        if (this.idCheckItem != null ? !idCheckItem.equals(id.idCheckItem) : id.idCheckItem != null) return false;
        if (this.idSubtipoSol != null ? !idSubtipoSol.equals(id.idSubtipoSol) : id.idSubtipoSol != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCheckItem != null ? idCheckItem.hashCode() : 0);
        result = 29 * result + (idSubtipoSol != null ? idSubtipoSol.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCheckItem);
        buffer.append('-');
        buffer.append(idSubtipoSol);
        return buffer.toString();
    }
}
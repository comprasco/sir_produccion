package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class CheckItemDevPk implements java.io.Serializable {

    public String idCheckItemDev;

    public CheckItemDevPk() {
    }

    public CheckItemDevPk(String s) {
        int i, p = 0;
        idCheckItemDev = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckItemDevPk)) return false;

        final CheckItemDevPk id = (CheckItemDevPk) o;

        if (this.idCheckItemDev != null ? !idCheckItemDev.equals(id.idCheckItemDev) : id.idCheckItemDev != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCheckItemDev != null ? idCheckItemDev.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCheckItemDev);
        return buffer.toString();
    }
}
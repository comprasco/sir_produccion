package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Secuencias	  */

public class SecuenciasPk implements java.io.Serializable {

    public String tableName;

    public SecuenciasPk() {
    }

    public SecuenciasPk(String s) {
        int i, p = 0;
        tableName = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecuenciasPk)) return false;

        final SecuenciasPk id = (SecuenciasPk) o;

        if (this.tableName != null ? !tableName.equals(id.tableName) : id.tableName != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (tableName != null ? tableName.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tableName);
        return buffer.toString();
    }
}
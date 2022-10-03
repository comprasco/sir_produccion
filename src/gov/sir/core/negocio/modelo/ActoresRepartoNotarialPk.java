package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoTarifa */

public class ActoresRepartoNotarialPk implements java.io.Serializable {

    public String idCirculo;

    public ActoresRepartoNotarialPk() {
    }

    public ActoresRepartoNotarialPk(String s) {
        int i, p = 0;
        idCirculo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActoresRepartoNotarialPk)) return false;

        final ActoresRepartoNotarialPk id = (ActoresRepartoNotarialPk) o;

        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCirculo);
        return buffer.toString();
    }
}
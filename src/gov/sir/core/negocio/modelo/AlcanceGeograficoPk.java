package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a AlcanceGeografico
 */
public class AlcanceGeograficoPk implements java.io.Serializable {

    public String idAlcanceGeografico;

    public AlcanceGeograficoPk() {
    }

    public AlcanceGeograficoPk(String s) {
        int i, p = 0;
        idAlcanceGeografico = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlcanceGeograficoPk)) return false;

        final AlcanceGeograficoPk id = (AlcanceGeograficoPk) o;

        if (this.idAlcanceGeografico != null ? !idAlcanceGeografico.equals(id.idAlcanceGeografico) : id.idAlcanceGeografico != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idAlcanceGeografico != null ? idAlcanceGeografico.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAlcanceGeografico);
        return buffer.toString();
    }
}
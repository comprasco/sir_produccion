package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a OtorganteNatural	  */

public class OtorganteNaturalPk implements java.io.Serializable {

    public String idMinuta;
    public String idOtorgante;

    public OtorganteNaturalPk() {
    }

    public OtorganteNaturalPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idMinuta = s.substring(p, i);
        p = i + 1;
        idOtorgante = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OtorganteNaturalPk)) return false;

        final OtorganteNaturalPk id = (OtorganteNaturalPk) o;

        if (this.idMinuta != null ? !idMinuta.equals(id.idMinuta) : id.idMinuta != null) return false;
        if (this.idOtorgante != null ? !idOtorgante.equals(id.idOtorgante) : id.idOtorgante != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idMinuta != null ? idMinuta.hashCode() : 0);
        result = 29 * result + (idOtorgante != null ? idOtorgante.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idMinuta);
        buffer.append('-');
        buffer.append(idOtorgante);
        return buffer.toString();
    }
}
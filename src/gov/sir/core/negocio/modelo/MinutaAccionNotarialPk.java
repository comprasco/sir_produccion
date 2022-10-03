package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a MinutaEntidadPublica  */
public class MinutaAccionNotarialPk implements java.io.Serializable {

    public String idAccionNotarial;
    public String idMinuta;

    public MinutaAccionNotarialPk() {
    }

    public MinutaAccionNotarialPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idAccionNotarial = s.substring(p, i);
        p = i + 1;
        idMinuta = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MinutaAccionNotarialPk)) return false;

        final MinutaAccionNotarialPk id = (MinutaAccionNotarialPk) o;

        if (this.idAccionNotarial != null ? !idAccionNotarial.equals(id.idAccionNotarial) : id.idAccionNotarial != null) return false;
        if (this.idMinuta != null ? !idMinuta.equals(id.idMinuta) : id.idMinuta != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idAccionNotarial != null ? idAccionNotarial.hashCode() : 0);
        result = 29 * result + (idMinuta != null ? idMinuta.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAccionNotarial);
        buffer.append('-');
        buffer.append(idMinuta);
        return buffer.toString();
    }
}
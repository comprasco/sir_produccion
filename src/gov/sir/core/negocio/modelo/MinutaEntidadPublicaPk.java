package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a MinutaEntidadPublica  */
public class MinutaEntidadPublicaPk implements java.io.Serializable {

    public String idEntidadPublica;
    public String idMinuta;

    public MinutaEntidadPublicaPk() {
    }

    public MinutaEntidadPublicaPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idEntidadPublica = s.substring(p, i);
        p = i + 1;
        idMinuta = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MinutaEntidadPublicaPk)) return false;

        final MinutaEntidadPublicaPk id = (MinutaEntidadPublicaPk) o;

        if (this.idEntidadPublica != null ? !idEntidadPublica.equals(id.idEntidadPublica) : id.idEntidadPublica != null) return false;
        if (this.idMinuta != null ? !idMinuta.equals(id.idMinuta) : id.idMinuta != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idEntidadPublica != null ? idEntidadPublica.hashCode() : 0);
        result = 29 * result + (idMinuta != null ? idMinuta.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idEntidadPublica);
        buffer.append('-');
        buffer.append(idMinuta);
        return buffer.toString();
    }
}
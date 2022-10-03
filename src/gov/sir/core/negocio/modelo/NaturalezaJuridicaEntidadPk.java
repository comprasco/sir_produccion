package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a NaturalezaJuridicaEntidad  */

public class NaturalezaJuridicaEntidadPk implements java.io.Serializable {

    public String idNatJuridicaEntidad;

    public NaturalezaJuridicaEntidadPk() {
    }

    public NaturalezaJuridicaEntidadPk(String s) {
        int i, p = 0;
        idNatJuridicaEntidad = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NaturalezaJuridicaEntidadPk)) return false;

        final NaturalezaJuridicaEntidadPk id = (NaturalezaJuridicaEntidadPk) o;

        if (this.idNatJuridicaEntidad != null ? !idNatJuridicaEntidad.equals(id.idNatJuridicaEntidad) : id.idNatJuridicaEntidad != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idNatJuridicaEntidad != null ? idNatJuridicaEntidad.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idNatJuridicaEntidad);
        return buffer.toString();
    }
}
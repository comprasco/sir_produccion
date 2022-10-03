package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a GrupoNaturalezaJuridica  */
public class GrupoNaturalezaJuridicaPk implements java.io.Serializable {

    public String idGrupoNatJuridica;

    public GrupoNaturalezaJuridicaPk() {
    }

    public GrupoNaturalezaJuridicaPk(String s) {
        int i, p = 0;
        idGrupoNatJuridica = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrupoNaturalezaJuridicaPk)) return false;

        final GrupoNaturalezaJuridicaPk id = (GrupoNaturalezaJuridicaPk) o;

        if (this.idGrupoNatJuridica != null ? !idGrupoNatJuridica.equals(id.idGrupoNatJuridica) : id.idGrupoNatJuridica != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idGrupoNatJuridica != null ? idGrupoNatJuridica.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idGrupoNatJuridica);
        return buffer.toString();
    }
}
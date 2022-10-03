package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a DominioNaturalezaJuridica  */
public class DominioNaturalezaJuridicaPk implements java.io.Serializable {

    public String idDominioNatJur;

    public DominioNaturalezaJuridicaPk() {
    }

    public DominioNaturalezaJuridicaPk(String s) {
        int i, p = 0;
        idDominioNatJur = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DominioNaturalezaJuridicaPk)) return false;

        final DominioNaturalezaJuridicaPk id = (DominioNaturalezaJuridicaPk) o;

        if (this.idDominioNatJur != null ? !idDominioNatJur.equals(id.idDominioNatJur) : id.idDominioNatJur != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idDominioNatJur != null ? idDominioNatJur.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDominioNatJur);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.DominioNaturalezaJuridicaPk;

/**
 * Application identity objectid-class.
 */
public class DominioNaturalezaJuridicaEnhancedPk implements java.io.Serializable {

    public String idDominioNatJur;

    public DominioNaturalezaJuridicaEnhancedPk() {
    }

    public DominioNaturalezaJuridicaEnhancedPk(String s) {
        int i, p = 0;
        idDominioNatJur = s.substring(p);
    }
    

	public DominioNaturalezaJuridicaEnhancedPk(DominioNaturalezaJuridicaPk id){
		idDominioNatJur = id.idDominioNatJur;
	}
	
	public DominioNaturalezaJuridicaPk getDominioNaturalezaJuridicaID(){
		DominioNaturalezaJuridicaPk id = new DominioNaturalezaJuridicaPk();
		id.idDominioNatJur = idDominioNatJur;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DominioNaturalezaJuridicaEnhancedPk)) return false;

        final DominioNaturalezaJuridicaEnhancedPk id = (DominioNaturalezaJuridicaEnhancedPk) o;

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
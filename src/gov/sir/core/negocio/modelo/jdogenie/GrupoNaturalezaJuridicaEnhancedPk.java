package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridicaPk;

/**
 * Application identity objectid-class.
 */
public class GrupoNaturalezaJuridicaEnhancedPk implements java.io.Serializable {

    public String idGrupoNatJuridica;

    public GrupoNaturalezaJuridicaEnhancedPk() {
    }

    public GrupoNaturalezaJuridicaEnhancedPk(String s) {
        int i, p = 0;
        idGrupoNatJuridica = s.substring(p);
    }

	public GrupoNaturalezaJuridicaEnhancedPk(GrupoNaturalezaJuridicaPk id){
		idGrupoNatJuridica = id.idGrupoNatJuridica;
	}
	
	public GrupoNaturalezaJuridicaPk getGrupoNaturalezaJuridicaID(){
		GrupoNaturalezaJuridicaPk id = new GrupoNaturalezaJuridicaPk();
		id.idGrupoNatJuridica = idGrupoNatJuridica;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrupoNaturalezaJuridicaEnhancedPk)) return false;

        final GrupoNaturalezaJuridicaEnhancedPk id = (GrupoNaturalezaJuridicaEnhancedPk) o;

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
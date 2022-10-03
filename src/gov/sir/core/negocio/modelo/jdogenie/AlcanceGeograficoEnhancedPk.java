package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.AlcanceGeograficoPk;

/**
 * Application identity objectid-class.
 */
public class AlcanceGeograficoEnhancedPk implements java.io.Serializable {

    public String idAlcanceGeografico;

    public AlcanceGeograficoEnhancedPk() {
    }

	public AlcanceGeograficoEnhancedPk(AlcanceGeograficoPk id){
		idAlcanceGeografico = id.idAlcanceGeografico;
	}
	
	public AlcanceGeograficoPk getAlcanceGeograficoID(){
		AlcanceGeograficoPk id = new AlcanceGeograficoPk();
		id.idAlcanceGeografico = idAlcanceGeografico;
		return id;
	}

    public AlcanceGeograficoEnhancedPk(String s) {
        int i, p = 0;
        idAlcanceGeografico = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlcanceGeograficoEnhancedPk)) return false;

        final AlcanceGeograficoEnhancedPk id = (AlcanceGeograficoEnhancedPk) o;

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
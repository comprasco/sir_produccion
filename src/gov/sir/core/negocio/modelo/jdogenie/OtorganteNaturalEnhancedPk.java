package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.OtorganteNaturalPk;

/**
 * Application identity objectid-class.
 */
public class OtorganteNaturalEnhancedPk implements java.io.Serializable {

    public String idMinuta;
    public String idOtorgante;

    public OtorganteNaturalEnhancedPk() {
    }

    public OtorganteNaturalEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idMinuta = s.substring(p, i);
        p = i + 1;
        idOtorgante = s.substring(p);
    }
    

	public OtorganteNaturalEnhancedPk(OtorganteNaturalPk id){
		idMinuta = id.idMinuta;
		idOtorgante = id.idOtorgante;
	}
            
	public OtorganteNaturalPk getOtorganteNaturalID(){
		OtorganteNaturalPk id = new OtorganteNaturalPk();
		id.idMinuta = idMinuta;
		id.idOtorgante = idOtorgante;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OtorganteNaturalEnhancedPk)) return false;

        final OtorganteNaturalEnhancedPk id = (OtorganteNaturalEnhancedPk) o;

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
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ProcesoRepartoPk;

/**
 * Application identity objectid-class.
 */
public class ProcesoRepartoEnhancedPk implements java.io.Serializable {

    public String idProcesoReparto;

    public ProcesoRepartoEnhancedPk() {
    }
    
    public ProcesoRepartoEnhancedPk(ProcesoRepartoPk id){
    	idProcesoReparto = id.idProcesoReparto;
    }
    
    public ProcesoRepartoPk getProcesoRepartoID(){
		ProcesoRepartoPk id = new ProcesoRepartoPk();
		id.idProcesoReparto = idProcesoReparto;
		return id;
    }

    public ProcesoRepartoEnhancedPk(String s) {
        int i, p = 0;
        idProcesoReparto = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcesoRepartoEnhancedPk)) return false;

        final ProcesoRepartoEnhancedPk id = (ProcesoRepartoEnhancedPk) o;

        if (this.idProcesoReparto != null ? !idProcesoReparto.equals(id.idProcesoReparto) : id.idProcesoReparto != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idProcesoReparto != null ? idProcesoReparto.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idProcesoReparto);
        return buffer.toString();
    }
}
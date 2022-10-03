package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ImprimiblePdfPk;

/**
 * Application identity objectid-class.
 */
public class ImprimiblePdfEnhancedPk implements java.io.Serializable {

    public int idImprimible;

    public ImprimiblePdfEnhancedPk() {
    }
    
	public ImprimiblePdfEnhancedPk(ImprimiblePdfPk id){
		idImprimible = id.idImprimible;
	}
	
	public ImprimiblePdfPk getImprimiblePdfID(){
		ImprimiblePdfPk id = new ImprimiblePdfPk();
		id.idImprimible = idImprimible;
		return id;
	}

    public ImprimiblePdfEnhancedPk(String s) {
        int i, p = 0;
        idImprimible = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImprimiblePdfEnhancedPk)) return false;

        final ImprimiblePdfEnhancedPk id = (ImprimiblePdfEnhancedPk) o;

        if (this.idImprimible != id.idImprimible) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) (idImprimible ^ (idImprimible >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idImprimible);
        return buffer.toString();
    }
}
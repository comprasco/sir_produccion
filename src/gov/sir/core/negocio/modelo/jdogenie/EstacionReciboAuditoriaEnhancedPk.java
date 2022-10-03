package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.EstacionReciboAuditoriaPk;

/**
 * Application identity objectid-class.
 */
public class EstacionReciboAuditoriaEnhancedPk implements java.io.Serializable {

    public int idAuditoria;

    public EstacionReciboAuditoriaEnhancedPk() {
    }

	public EstacionReciboAuditoriaEnhancedPk(EstacionReciboAuditoriaPk id){
		idAuditoria = id.idAuditoria;
	}
	
	public EstacionReciboAuditoriaPk getActoID(){
		EstacionReciboAuditoriaPk id = new EstacionReciboAuditoriaPk();
		id.idAuditoria= idAuditoria;
		return id;
	}

    public EstacionReciboAuditoriaEnhancedPk(String s) {
        int i, p = 0;
        idAuditoria = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstacionReciboAuditoriaEnhancedPk)) return false;

        final EstacionReciboAuditoriaEnhancedPk id = (EstacionReciboAuditoriaEnhancedPk) o;

        if (this.idAuditoria != id.idAuditoria) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) idAuditoria;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAuditoria);
        return buffer.toString();
    }
}
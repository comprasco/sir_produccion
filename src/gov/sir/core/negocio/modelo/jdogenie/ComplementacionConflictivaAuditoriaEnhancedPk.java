package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ComplementacionConflictivaComplementacionPk;

/**
 * Application identity objectid-class.
 */
public class ComplementacionConflictivaAuditoriaEnhancedPk implements java.io.Serializable {

    public String idAuditoria;

    public ComplementacionConflictivaAuditoriaEnhancedPk() {
    }

	public ComplementacionConflictivaAuditoriaEnhancedPk(ComplementacionConflictivaComplementacionPk id){
		idAuditoria = id.idAuditoria;
	}
	
	public ComplementacionConflictivaComplementacionPk getComplementacionConflictivaComplementacionID(){
		ComplementacionConflictivaComplementacionPk id = new ComplementacionConflictivaComplementacionPk();
		id.idAuditoria = idAuditoria;
		return id;
	}
	
    public ComplementacionConflictivaAuditoriaEnhancedPk(String s) {
        int i, p = 0;
        idAuditoria = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplementacionConflictivaAuditoriaEnhancedPk)) return false;

        final ComplementacionConflictivaAuditoriaEnhancedPk id = (ComplementacionConflictivaAuditoriaEnhancedPk) o;

        if (this.idAuditoria != null ? !idAuditoria.equals(id.idAuditoria) : id.idAuditoria != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idAuditoria != null ? idAuditoria.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAuditoria);
        return buffer.toString();
    }
}
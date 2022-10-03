package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.FaseAlertaPk;

/**
 * Application identity objectid-class.
 */
public class FaseAlertaEnhancedPk implements java.io.Serializable {

    public String idFase;
    public long idProceso;

    public FaseAlertaEnhancedPk() {
    }

    public FaseAlertaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idFase = s.substring(p, i);
        p = i + 1;
        idProceso = Long.parseLong(s.substring(p));
    }
    
	public FaseAlertaEnhancedPk(FaseAlertaPk id) {
		idFase = id.idFase;
		idProceso = id.idProceso;
	}

	public FaseAlertaPk getFaseAlertaID() {
		FaseAlertaPk id = new FaseAlertaPk();
		id.idFase = idFase;
		id.idProceso = idProceso;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FaseAlertaEnhancedPk)) return false;

        final FaseAlertaEnhancedPk id = (FaseAlertaEnhancedPk) o;

        if (this.idFase != null ? !idFase.equals(id.idFase) : id.idFase != null) return false;
        if (this.idProceso != id.idProceso) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idFase != null ? idFase.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFase);
        buffer.append('-');
        buffer.append(idProceso);
        return buffer.toString();
    }
}
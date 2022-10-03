package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoHorarioNotarialPk;

/**
 * Application identity objectid-class.
 */
public class TipoHorarioNotarialEnhancedPk implements java.io.Serializable {

    public String idTipoHorarioNotarial;

    public TipoHorarioNotarialEnhancedPk() {
    }
    
	public TipoHorarioNotarialEnhancedPk(TipoHorarioNotarialPk id){
		idTipoHorarioNotarial = id.idTipoHorarioNotarial;        	
	}
    
	public TipoHorarioNotarialPk getTipoHorarioNotarialID(){
		TipoHorarioNotarialPk id = new TipoHorarioNotarialPk();
		id.idTipoHorarioNotarial  = idTipoHorarioNotarial;
		return id;
	}    

    public TipoHorarioNotarialEnhancedPk(String s) {
        int i, p = 0;
        idTipoHorarioNotarial = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoCertificadoEnhancedPk)) return false;

        final TipoHorarioNotarialEnhancedPk id = (TipoHorarioNotarialEnhancedPk) o;

        if (this.idTipoHorarioNotarial != null ? !idTipoHorarioNotarial.equals(id.idTipoHorarioNotarial) : id.idTipoHorarioNotarial != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoHorarioNotarial != null ? idTipoHorarioNotarial.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoHorarioNotarial);
        return buffer.toString();
    }
}
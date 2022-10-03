package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TurnoFolioTramiteMigPk;

/**
 * Application identity objectid-class.
 */
public class TurnoFolioTramiteMigEnhancedPk implements java.io.Serializable {

    public String idTurno;
    public long idFolio;
    public long idProceso;
    public String idCirculo;
    public long secProceso;

    public TurnoFolioTramiteMigEnhancedPk() {
    }

	public TurnoFolioTramiteMigEnhancedPk(TurnoFolioTramiteMigPk id){
		idTurno = id.idTurno;
		idFolio = id.idFolio;
		idProceso = id.idProceso;
		idCirculo = id.idCirculo;
		secProceso = id.secProceso;
	}
	
	public TurnoFolioTramiteMigPk getTurnoFolioTramiteMigID(){
		TurnoFolioTramiteMigPk id = new TurnoFolioTramiteMigPk();
		id.idTurno= idTurno;
		id.idFolio = idFolio;
		id.idProceso = idProceso;	
		id.idCirculo = idCirculo;
		id.secProceso = secProceso;
		return id;
	}

    public TurnoFolioTramiteMigEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idTurno = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idFolio = Long.parseLong(s.substring(p, i));
        p = i + 1;
        i= s.indexOf('-', p);
        idProceso = Long.parseLong(s.substring(p, i));
        p = i + 1;
        i= s.indexOf('-', p);
        idCirculo = s.substring(p, i);
        p = i + 1;
        secProceso = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TurnoFolioTramiteMigEnhancedPk)) return false;

        final TurnoFolioTramiteMigEnhancedPk id = (TurnoFolioTramiteMigEnhancedPk) o;

        if (this.idTurno != null ? !idTurno.equals(id.idTurno) : id.idTurno != null) return false;
        if (this.idFolio != id.idFolio) {
            return false;
        }
        if (this.idProceso != id.idProceso) {
            return false;
        }
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.secProceso != id.secProceso) {
            return false;
        }            
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTurno != null ? idTurno.hashCode() : 0);
        result = (29 * result) + (int) (idFolio ^ (idFolio >>> 32));
        result = (29 * result) + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = (29 * result) + (int) (secProceso ^ (secProceso >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTurno);
        buffer.append('-');
        buffer.append(idFolio);
        buffer.append('-');
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idCirculo);
        buffer.append('-');
        buffer.append(secProceso);
        return buffer.toString();
    }
}
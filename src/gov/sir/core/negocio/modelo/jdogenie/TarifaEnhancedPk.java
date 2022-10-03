package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TarifaPk;

/**
 * Application identity objectid-class.
 */
public class TarifaEnhancedPk implements java.io.Serializable {

    public String idCodigo;
    public String idTipo;
    public long idVersion;

    public TarifaEnhancedPk() {
    }

	public TarifaEnhancedPk(TarifaPk id){
		idCodigo  = id.idCodigo;
		idTipo    = id.idTipo;
		idVersion = id.idVersion;        	
	}
    
	public TarifaPk getTarifaID(){
		TarifaPk id = new TarifaPk();
		id.idCodigo  = idCodigo;
		id.idTipo    = idTipo;
		id.idVersion = idVersion;       
		return id;
	}        


    public TarifaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCodigo = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idTipo = s.substring(p, i);
        p = i + 1;
        idVersion = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TarifaEnhancedPk)) return false;

        final TarifaEnhancedPk id = (TarifaEnhancedPk) o;

        if (this.idCodigo != null ? !idCodigo.equals(id.idCodigo) : id.idCodigo != null) return false;
        if (this.idTipo != null ? !idTipo.equals(id.idTipo) : id.idTipo != null) return false;
        if (this.idVersion != id.idVersion) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCodigo != null ? idCodigo.hashCode() : 0);
        result = 29 * result + (idTipo != null ? idTipo.hashCode() : 0);
        result = 29 * result + (int) (idVersion ^ (idVersion >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCodigo);
        buffer.append('-');
        buffer.append(idTipo);
        buffer.append('-');
        buffer.append(idVersion);
        return buffer.toString();
    }
}
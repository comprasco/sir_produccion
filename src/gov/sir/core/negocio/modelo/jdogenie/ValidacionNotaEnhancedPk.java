package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ValidacionNotaPk;

/**
 * Application identity objectid-class.
 */
public class ValidacionNotaEnhancedPk implements java.io.Serializable {

    public String idFase;
    public long idProceso;
    public String idRespuesta;

    public ValidacionNotaEnhancedPk() {
    }

    public ValidacionNotaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idFase = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idProceso = Long.parseLong(s.substring(p, i));
        p = i + 1;
        idRespuesta = s.substring(p);
    }
    

	public ValidacionNotaEnhancedPk(ValidacionNotaPk id){
		idFase = id.idFase;
		idProceso = id.idProceso;
		idRespuesta = id.idRespuesta;
	}
            
	public ValidacionNotaPk getValidacionNotaID(){
		ValidacionNotaPk id = new ValidacionNotaPk();
		id.idFase = idFase;
		id.idProceso = idProceso;
		id.idRespuesta = idRespuesta;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidacionNotaEnhancedPk)) return false;

        final ValidacionNotaEnhancedPk id = (ValidacionNotaEnhancedPk) o;

        if (this.idFase != null ? !idFase.equals(id.idFase) : id.idFase != null) return false;
        if (this.idProceso != id.idProceso) return false;
        if (this.idRespuesta != null ? !idRespuesta.equals(id.idRespuesta) : id.idRespuesta != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idFase != null ? idFase.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idRespuesta != null ? idRespuesta.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFase);
        buffer.append('-');
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idRespuesta);
        return buffer.toString();
    }
}
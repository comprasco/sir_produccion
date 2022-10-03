package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.RelacionPk;

/**
 * Application identity objectid-class.
 */
public class RelacionEnhancedPk implements java.io.Serializable {

    public String idFase;
    public String idRelacion;

    public RelacionEnhancedPk() {
    }
    

	public RelacionEnhancedPk(RelacionPk id) {
		idFase = id.idFase;
		idRelacion = id.idRelacion;
	}
	

	public RelacionPk getRelacionID() {
		RelacionPk rta = new RelacionPk();
		rta.idFase = idFase;
		rta.idRelacion = idRelacion;
		return rta;
	}

    public RelacionEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idFase = s.substring(p, i);
        p = i + 1;
        idRelacion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelacionEnhancedPk)) return false;

        final RelacionEnhancedPk id = (RelacionEnhancedPk) o;

        if (this.idFase != null ? !idFase.equals(id.idFase) : id.idFase != null) return false;
        if (this.idRelacion != null ? !idRelacion.equals(id.idRelacion) : id.idRelacion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idFase != null ? idFase.hashCode() : 0);
        result = 29 * result + (idRelacion != null ? idRelacion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFase);
        buffer.append('-');
        buffer.append(idRelacion);
        return buffer.toString();
    }
}
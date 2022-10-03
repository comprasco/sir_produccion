package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.AumentoReciboPk;

/**
 * Application identity objectid-class.
 */
public class AumentoReciboEnhancedPk implements java.io.Serializable {

    public String idAumentoRecibo;

    public AumentoReciboEnhancedPk() {
    }

    public AumentoReciboEnhancedPk(String s) {
        int i, p = 0;
        idAumentoRecibo = s.substring(p);
    }
    

	public AumentoReciboEnhancedPk(AumentoReciboPk id){
		idAumentoRecibo = id.idAumentoRecibo;
	}
	
	public AumentoReciboPk getAumentoReciboID(){
		AumentoReciboPk id = new AumentoReciboPk();
		id.idAumentoRecibo= idAumentoRecibo;
		return id;
	}


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AumentoReciboEnhancedPk)) return false;

        final AumentoReciboEnhancedPk id = (AumentoReciboEnhancedPk) o;

        if (this.idAumentoRecibo != null ? !idAumentoRecibo.equals(id.idAumentoRecibo) : id.idAumentoRecibo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idAumentoRecibo != null ? idAumentoRecibo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAumentoRecibo);
        return buffer.toString();
    }
}
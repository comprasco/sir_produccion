package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoCertificadoPk;

/**
 * Application identity objectid-class.
 */
public class TipoCertificadoEnhancedPk implements java.io.Serializable {

    public String idTipoCertificado;

    public TipoCertificadoEnhancedPk() {
    }
    
	public TipoCertificadoEnhancedPk(TipoCertificadoPk id){
		idTipoCertificado = id.idTipoCertificado;        	
	}
    
	public TipoCertificadoPk getTipoCertificadoID(){
		TipoCertificadoPk id = new TipoCertificadoPk();
		id.idTipoCertificado  = idTipoCertificado;
		return id;
	}    

    public TipoCertificadoEnhancedPk(String s) {
        int i, p = 0;
        idTipoCertificado = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoCertificadoEnhancedPk)) return false;

        final TipoCertificadoEnhancedPk id = (TipoCertificadoEnhancedPk) o;

        if (this.idTipoCertificado != null ? !idTipoCertificado.equals(id.idTipoCertificado) : id.idTipoCertificado != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoCertificado != null ? idTipoCertificado.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoCertificado);
        return buffer.toString();
    }
}
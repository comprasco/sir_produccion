package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ActoPk;

/**
 * Application identity objectid-class.
 */
public class ActoEnhancedPk implements java.io.Serializable {

    public String idActo;
    public String idLiquidacion;
    public String idSolicitud;

    public ActoEnhancedPk() {
    }

	public ActoEnhancedPk(ActoPk id){
		idActo = id.idActo;
		idLiquidacion = id.idLiquidacion;
		idSolicitud = id.idSolicitud;
	}
	
	public ActoPk getActoID(){
		ActoPk id = new ActoPk();
		id.idActo= idActo;
		id.idLiquidacion = idLiquidacion;
		id.idSolicitud = idSolicitud;
		return id;
	}

    public ActoEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idActo = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idLiquidacion = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActoEnhancedPk)) return false;

        final ActoEnhancedPk id = (ActoEnhancedPk) o;

        if (this.idActo != null ? !idActo.equals(id.idActo) : id.idActo != null) return false;
        if (this.idLiquidacion != null ? !idLiquidacion.equals(id.idLiquidacion) : id.idLiquidacion != null) return false;
        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idActo != null ? idActo.hashCode() : 0);
        result = 29 * result + (idLiquidacion != null ? idLiquidacion.hashCode() : 0);
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idActo);
        buffer.append('-');
        buffer.append(idLiquidacion);
        buffer.append('-');
        buffer.append(idSolicitud);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolCheckedItemDevPk;

/**
 * Application identity objectid-class.
 */
public class SolCheckedItemDevEnhancedPk implements java.io.Serializable {

    public String idCheckItemDev;
    public String idSolicitud;

    public SolCheckedItemDevEnhancedPk() {
    }

    public SolCheckedItemDevEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCheckItemDev = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);
    }
    
	public SolCheckedItemDevEnhancedPk(SolCheckedItemDevPk id) {
		idSolicitud = id.idSolicitud;
		idCheckItemDev= id.idCheckItemDev;
	}

	public SolCheckedItemDevPk getSolCheckedItemDevID() {
		SolCheckedItemDevPk id = new SolCheckedItemDevPk();
		idSolicitud = id.idSolicitud;
		idCheckItemDev = id.idCheckItemDev;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolCheckedItemDevEnhancedPk)) return false;

        final SolCheckedItemDevEnhancedPk id = (SolCheckedItemDevEnhancedPk) o;

        if (this.idCheckItemDev != null ? !idCheckItemDev.equals(id.idCheckItemDev) : id.idCheckItemDev != null) return false;
        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCheckItemDev != null ? idCheckItemDev.hashCode() : 0);
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCheckItemDev);
        buffer.append('-');
        buffer.append(idSolicitud);
        return buffer.toString();
    }
}
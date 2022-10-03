package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.RangoFolioPk;

/**
 * Application identity objectid-class.
 */
public class RangoFolioEnhancedPk implements java.io.Serializable {

    public String idRangoFolio;
    public String idSolicitud;

    public RangoFolioEnhancedPk() {
    }
    

	public RangoFolioEnhancedPk(RangoFolioPk id){
		idRangoFolio = id.idRangoFolio;
		idSolicitud = id.idSolicitud;
	}
    
	public RangoFolioPk getRangoFolioID(){
		RangoFolioPk id = new RangoFolioPk();
		id.idRangoFolio = idRangoFolio;
		id.idSolicitud = idSolicitud;
		return id;
	}

    public RangoFolioEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idRangoFolio = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RangoFolioEnhancedPk)) return false;

        final RangoFolioEnhancedPk id = (RangoFolioEnhancedPk) o;

        if (this.idRangoFolio != null ? !idRangoFolio.equals(id.idRangoFolio) : id.idRangoFolio != null) return false;
        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idRangoFolio != null ? idRangoFolio.hashCode() : 0);
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idRangoFolio);
        buffer.append('-');
        buffer.append(idSolicitud);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.SolicitudChecedItemPk;

/**
 * Application identity objectid-class.
 */
public class SolicitudCheckedItemEnhancedPk implements java.io.Serializable {

    public String idCheckItem;
    public String idSolicitud;
    public String idSubtipoSol;

    public SolicitudCheckedItemEnhancedPk() {
    }
    
    public SolicitudCheckedItemEnhancedPk(SolicitudChecedItemPk oid){
		idCheckItem = oid.idCheckItem;
		idSolicitud = oid.idSolicitud;
		idSubtipoSol = oid.idSubtipoSol;
    }
    
    public SolicitudChecedItemPk getSolicitudCheckedItemID(){
		SolicitudChecedItemPk rta = new SolicitudChecedItemPk();
		rta.idCheckItem = idCheckItem;
		rta.idSolicitud = idSolicitud;
		rta.idSubtipoSol = idSubtipoSol;
		return rta;
    }

    public SolicitudCheckedItemEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCheckItem = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idSolicitud = s.substring(p, i);
        p = i + 1;
        idSubtipoSol = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolicitudCheckedItemEnhancedPk)) return false;

        final SolicitudCheckedItemEnhancedPk id = (SolicitudCheckedItemEnhancedPk) o;

        if (this.idCheckItem != null ? !idCheckItem.equals(id.idCheckItem) : id.idCheckItem != null) return false;
        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        if (this.idSubtipoSol != null ? !idSubtipoSol.equals(id.idSubtipoSol) : id.idSubtipoSol != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCheckItem != null ? idCheckItem.hashCode() : 0);
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        result = 29 * result + (idSubtipoSol != null ? idSubtipoSol.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCheckItem);
        buffer.append('-');
        buffer.append(idSolicitud);
        buffer.append('-');
        buffer.append(idSubtipoSol);
        return buffer.toString();
    }
}
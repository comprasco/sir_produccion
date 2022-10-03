package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.BusquedaPk;

/**
 * Application identity objectid-class.
 */
public class BusquedaEnhancedPk implements java.io.Serializable {

    public String idBusqueda;
    public String idSolicitud;

    public BusquedaEnhancedPk() {
    }

	public BusquedaEnhancedPk(BusquedaPk id){
		idBusqueda = id.idBusqueda;
		idSolicitud = id.idSolicitud;
	}
	
	public BusquedaPk getBusquedaID(){
		BusquedaPk id = new BusquedaPk();
		id.idBusqueda = idBusqueda;
		id.idSolicitud = idSolicitud;
		return id;
	}

    public BusquedaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idBusqueda = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusquedaEnhancedPk)) return false;

        final BusquedaEnhancedPk id = (BusquedaEnhancedPk) o;

        if (this.idBusqueda != null ? !idBusqueda.equals(id.idBusqueda) : id.idBusqueda != null) return false;
        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idBusqueda != null ? idBusqueda.hashCode() : 0);
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idBusqueda);
        buffer.append('-');
        buffer.append(idSolicitud);
        return buffer.toString();
    }
}
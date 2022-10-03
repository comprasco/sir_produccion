package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a TurnoFolioMig */
public class SolicitudFolioMigPk implements java.io.Serializable {

    public String idSolicitud;
    public String idFolio;
    public long idProceso;
    public String idCirculo;

    public SolicitudFolioMigPk() {
    }

    public SolicitudFolioMigPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idSolicitud = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idFolio = s.substring(p, i);
        p = i + 1;
        idProceso = Long.parseLong(s.substring(p, i));
        p = i + 1;
        idCirculo = (s.substring(p, i));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolicitudFolioMigPk)) return false;

        final SolicitudFolioMigPk id = (SolicitudFolioMigPk) o;

        if (this.idSolicitud != null ? !idSolicitud.equals(id.idSolicitud) : id.idSolicitud != null) return false;
        if (this.idFolio != null ? !idFolio.equals(id.idFolio) : id.idFolio != null) return false;
        if (this.idProceso != id.idProceso) {
            return false;
        }
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idSolicitud != null ? idSolicitud.hashCode() : 0);
        result = 29 * result + (idFolio != null ? idFolio.hashCode() : 0);
        result = (29 * result) + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSolicitud);
        buffer.append('-');
        buffer.append(idFolio);
        buffer.append('-');
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idCirculo);
        return buffer.toString();
    }
}
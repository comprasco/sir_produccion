package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a TurnoFolioMig */
public class TurnoFolioMigPk implements java.io.Serializable {

    public String idTurno;
    public String idFolio;
    public long idProceso;
    public String idCirculo;

    public TurnoFolioMigPk() {
    }

    public TurnoFolioMigPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idTurno = s.substring(p, i);
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
        if (!(o instanceof TurnoFolioMigPk)) return false;

        final TurnoFolioMigPk id = (TurnoFolioMigPk) o;

        if (this.idTurno != null ? !idTurno.equals(id.idTurno) : id.idTurno != null) return false;
        if (this.idFolio != null ? !idFolio.equals(id.idFolio) : id.idFolio != null) return false;
        if (this.idProceso != id.idProceso) {
            return false;
        }
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTurno != null ? idTurno.hashCode() : 0);
        result = 29 * result + (idFolio != null ? idFolio.hashCode() : 0);
        result = (29 * result) + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTurno);
        buffer.append('-');
        buffer.append(idFolio);
        buffer.append('-');
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idCirculo);
        return buffer.toString();
    }
}
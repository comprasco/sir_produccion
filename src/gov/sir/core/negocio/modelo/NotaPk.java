package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Nota  */

public class NotaPk implements java.io.Serializable {

    public String anio;
    public String idCirculo;
    public String idNota;
    public long idProceso;
    public String idTurno;

    public NotaPk() {
    }

    public NotaPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        anio = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idCirculo = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idNota = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idProceso = Long.parseLong(s.substring(p, i));
        p = i + 1;
        idTurno = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotaPk)) return false;

        final NotaPk id = (NotaPk) o;

        if (this.anio != null ? !anio.equals(id.anio) : id.anio != null) return false;
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.idNota != null ? !idNota.equals(id.idNota) : id.idNota != null) return false;
        if (this.idProceso != id.idProceso) return false;
        if (this.idTurno != null ? !idTurno.equals(id.idTurno) : id.idTurno != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (anio != null ? anio.hashCode() : 0);
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = 29 * result + (idNota != null ? idNota.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idTurno != null ? idTurno.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(anio);
        buffer.append('-');
        buffer.append(idCirculo);
        buffer.append('-');
        buffer.append(idNota);
        buffer.append('-');
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idTurno);
        return buffer.toString();
    }
}
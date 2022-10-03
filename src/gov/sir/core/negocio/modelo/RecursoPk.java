package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Recurso	  */

public class RecursoPk implements java.io.Serializable {

    public String anio;
    public String idCirculo;
    public long idProceso;
    public String idRecurso;
    public String idTurno;
    public String idTurnoHistoria;

    public RecursoPk() {
    }

    public RecursoPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        anio = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idCirculo = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idProceso = Long.parseLong(s.substring(p, i));
        p = i + 1;
        i= s.indexOf('-', p);
        idRecurso = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idTurno = s.substring(p, i);
        p = i + 1;
        idTurnoHistoria = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecursoPk)) return false;

        final RecursoPk id = (RecursoPk) o;

        if (this.anio != null ? !anio.equals(id.anio) : id.anio != null) return false;
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.idProceso != id.idProceso) return false;
        if (this.idRecurso != null ? !idRecurso.equals(id.idRecurso) : id.idRecurso != null) return false;
        if (this.idTurno != null ? !idTurno.equals(id.idTurno) : id.idTurno != null) return false;
        if (this.idTurnoHistoria != null ? !idTurnoHistoria.equals(id.idTurnoHistoria) : id.idTurnoHistoria != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (anio != null ? anio.hashCode() : 0);
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idRecurso != null ? idRecurso.hashCode() : 0);
        result = 29 * result + (idTurno != null ? idTurno.hashCode() : 0);
        result = 29 * result + (idTurnoHistoria != null ? idTurnoHistoria.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(anio);
        buffer.append('-');
        buffer.append(idCirculo);
        buffer.append('-');
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idRecurso);
        buffer.append('-');
        buffer.append(idTurno);
        buffer.append('-');
        buffer.append(idTurnoHistoria);
        return buffer.toString();
    }
}
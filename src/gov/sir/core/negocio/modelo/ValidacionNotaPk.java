package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a ValidacionNota */

public class ValidacionNotaPk implements java.io.Serializable {

    public String idFase;
    public long idProceso;
    public String idRespuesta;

    public ValidacionNotaPk() {
    }

    public ValidacionNotaPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idFase = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idProceso = Long.parseLong(s.substring(p, i));
        p = i + 1;
        idRespuesta = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidacionNotaPk)) return false;

        final ValidacionNotaPk id = (ValidacionNotaPk) o;

        if (this.idFase != null ? !idFase.equals(id.idFase) : id.idFase != null) return false;
        if (this.idProceso != id.idProceso) return false;
        if (this.idRespuesta != null ? !idRespuesta.equals(id.idRespuesta) : id.idRespuesta != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idFase != null ? idFase.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idRespuesta != null ? idRespuesta.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFase);
        buffer.append('-');
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idRespuesta);
        return buffer.toString();
    }
}
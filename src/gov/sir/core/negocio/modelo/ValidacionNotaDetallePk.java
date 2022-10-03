package gov.sir.core.negocio.modelo;

/**
 * Application identity objectid-class.
 */
public class ValidacionNotaDetallePk implements java.io.Serializable {

    public String idFase;
    public long idProceso;
    public String idRespuesta;
    public String idTipoNota;

    public ValidacionNotaDetallePk() {
    }

    public ValidacionNotaDetallePk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idFase = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idProceso = Long.parseLong(s.substring(p, i));
        p = i + 1;
        i= s.indexOf('-', p);
        idRespuesta = s.substring(p, i);
        p = i + 1;
        idTipoNota = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidacionNotaDetallePk)) return false;

        final ValidacionNotaDetallePk id = (ValidacionNotaDetallePk) o;

        if (this.idFase != null ? !idFase.equals(id.idFase) : id.idFase != null) return false;
        if (this.idProceso != id.idProceso) return false;
        if (this.idRespuesta != null ? !idRespuesta.equals(id.idRespuesta) : id.idRespuesta != null) return false;
        if (this.idTipoNota != null ? !idTipoNota.equals(id.idTipoNota) : id.idTipoNota != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idFase != null ? idFase.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idRespuesta != null ? idRespuesta.hashCode() : 0);
        result = 29 * result + (idTipoNota != null ? idTipoNota.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFase);
        buffer.append('-');
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idRespuesta);
        buffer.append('-');
        buffer.append(idTipoNota);
        return buffer.toString();
    }
}
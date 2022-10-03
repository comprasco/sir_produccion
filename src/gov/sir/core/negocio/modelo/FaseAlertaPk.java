package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a FaseAlerta  */
public class FaseAlertaPk implements java.io.Serializable {

    public String idFase;
    public long idProceso;

    public FaseAlertaPk() {
    }

    public FaseAlertaPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idFase = s.substring(p, i);
        p = i + 1;
        idProceso = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FaseAlertaPk)) return false;

        final FaseAlertaPk id = (FaseAlertaPk) o;

        if (this.idFase != null ? !idFase.equals(id.idFase) : id.idFase != null) return false;
        if (this.idProceso != id.idProceso) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idFase != null ? idFase.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFase);
        buffer.append('-');
        buffer.append(idProceso);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Proceso	  */

public class ProcesoPk implements java.io.Serializable {

    public long idProceso;

    public ProcesoPk() {
    }

    public ProcesoPk(String s) {
        int i, p = 0;
        idProceso = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcesoPk)) return false;

        final ProcesoPk id = (ProcesoPk) o;

        if (this.idProceso != id.idProceso) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idProceso);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a EstacionRecibo  */
public class EstacionReciboPk implements java.io.Serializable {

    public String idEstacion;
    public long numeroProceso;

    public EstacionReciboPk() {
    }

    public EstacionReciboPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idEstacion = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        numeroProceso = Long.parseLong(s.substring(p, i));
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstacionReciboPk)) return false;

        final EstacionReciboPk id = (EstacionReciboPk) o;

        if (this.idEstacion != null ? !idEstacion.equals(id.idEstacion) : id.idEstacion != null) return false;
        if (this.numeroProceso != id.numeroProceso) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idEstacion != null ? idEstacion.hashCode() : 0);
        result = 29 * result + (int) (numeroProceso ^ (numeroProceso >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idEstacion);
        buffer.append('-');
        buffer.append(numeroProceso);
        return buffer.toString();
    }
}
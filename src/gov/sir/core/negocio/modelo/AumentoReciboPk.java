package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a AumentoRecibo */
public class AumentoReciboPk implements java.io.Serializable {

    public String idAumentoRecibo;

    public AumentoReciboPk() {
    }

    public AumentoReciboPk(String s) {
        int i, p = 0;
        idAumentoRecibo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AumentoReciboPk)) return false;

        final AumentoReciboPk id = (AumentoReciboPk) o;

        if (this.idAumentoRecibo != null ? !idAumentoRecibo.equals(id.idAumentoRecibo) : id.idAumentoRecibo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idAumentoRecibo != null ? idAumentoRecibo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAumentoRecibo);
        return buffer.toString();
    }
}
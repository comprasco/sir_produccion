package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Tarifa     */
public class TarifaPk implements java.io.Serializable {

    public String idCodigo;
    public String idTipo;
    public long idVersion;

    public TarifaPk() {
    }

    public TarifaPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCodigo = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idTipo = s.substring(p, i);
        p = i + 1;
        idVersion = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TarifaPk)) return false;

        final TarifaPk id = (TarifaPk) o;

        if (this.idCodigo != null ? !idCodigo.equals(id.idCodigo) : id.idCodigo != null) return false;
        if (this.idTipo != null ? !idTipo.equals(id.idTipo) : id.idTipo != null) return false;
        if (this.idVersion != id.idVersion) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCodigo != null ? idCodigo.hashCode() : 0);
        result = 29 * result + (idTipo != null ? idTipo.hashCode() : 0);
        result = 29 * result + (int) (idVersion ^ (idVersion >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCodigo);
        buffer.append('-');
        buffer.append(idTipo);
        buffer.append('-');
        buffer.append(idVersion);
        return buffer.toString();
    }
}
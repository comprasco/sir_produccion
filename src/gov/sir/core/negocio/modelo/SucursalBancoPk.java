package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a SucursalBanco
 */
public class SucursalBancoPk implements java.io.Serializable {

    public String idBanco;
    public String idSucursal;

    public SucursalBancoPk() {
    }

    public SucursalBancoPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idBanco = s.substring(p, i);
        p = i + 1;
        idSucursal = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SucursalBancoPk)) return false;

        final SucursalBancoPk id = (SucursalBancoPk) o;

        if (this.idBanco != null ? !idBanco.equals(id.idBanco) : id.idBanco != null) return false;
        if (this.idSucursal != null ? !idSucursal.equals(id.idSucursal) : id.idSucursal != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idBanco != null ? idBanco.hashCode() : 0);
        result = 29 * result + (idSucursal != null ? idSucursal.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idBanco);
        buffer.append('-');
        buffer.append(idSucursal);
        return buffer.toString();
    }
}
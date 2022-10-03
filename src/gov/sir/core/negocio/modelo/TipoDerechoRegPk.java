package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TipoDerechoReg */

public class TipoDerechoRegPk implements java.io.Serializable {

    public String idTipoDerechoReg;

    public TipoDerechoRegPk() {
    }

    public TipoDerechoRegPk(String s) {
        int i, p = 0;
        idTipoDerechoReg = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoDerechoRegPk)) return false;

        final TipoDerechoRegPk id = (TipoDerechoRegPk) o;

        if (this.idTipoDerechoReg != null ? !idTipoDerechoReg.equals(id.idTipoDerechoReg) : id.idTipoDerechoReg != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoDerechoReg != null ? idTipoDerechoReg.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoDerechoReg);
        return buffer.toString();
    }
}
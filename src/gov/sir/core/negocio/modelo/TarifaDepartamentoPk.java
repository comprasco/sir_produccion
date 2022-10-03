package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a TarifaDepartamento */

public class TarifaDepartamentoPk implements java.io.Serializable {

    public String idDepartamento;
    public String idTipoActo;

    public TarifaDepartamentoPk() {
    }

    public TarifaDepartamentoPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idDepartamento = s.substring(p, i);
        p = i + 1;
        idTipoActo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TarifaDepartamentoPk)) return false;

        final TarifaDepartamentoPk id = (TarifaDepartamentoPk) o;

        if (this.idDepartamento != null ? !idDepartamento.equals(id.idDepartamento) : id.idDepartamento != null) return false;
        if (this.idTipoActo != null ? !idTipoActo.equals(id.idTipoActo) : id.idTipoActo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idDepartamento != null ? idDepartamento.hashCode() : 0);
        result = 29 * result + (idTipoActo != null ? idTipoActo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDepartamento);
        buffer.append('-');
        buffer.append(idTipoActo);
        return buffer.toString();
    }
}
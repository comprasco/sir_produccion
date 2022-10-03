package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a DatosAntiguoSistema
 */
public class DatosAntiguoSistemaPk implements java.io.Serializable {

    public String idDatosAntiguoSistema;

    public DatosAntiguoSistemaPk() {
    }

    public DatosAntiguoSistemaPk(String s) {
        int i, p = 0;
		idDatosAntiguoSistema = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatosAntiguoSistemaPk)) return false;

        final DatosAntiguoSistemaPk id = (DatosAntiguoSistemaPk) o;

        if (this.idDatosAntiguoSistema != null ? !idDatosAntiguoSistema.equals(id.idDatosAntiguoSistema) : id.idDatosAntiguoSistema != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idDatosAntiguoSistema != null ? idDatosAntiguoSistema.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDatosAntiguoSistema);
        return buffer.toString();
    }
}
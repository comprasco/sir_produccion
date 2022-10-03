package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Relacion	  */

public class RelacionPk implements java.io.Serializable {

    public String idFase;
    public String idRelacion;

    public RelacionPk() {
    }

    public RelacionPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idFase = s.substring(p, i);
        p = i + 1;
        idRelacion = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelacionPk)) return false;

        final RelacionPk id = (RelacionPk) o;

        if (this.idFase != null ? !idFase.equals(id.idFase) : id.idFase != null) return false;
        if (this.idRelacion != null ? !idRelacion.equals(id.idRelacion) : id.idRelacion != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idFase != null ? idFase.hashCode() : 0);
        result = 29 * result + (idRelacion != null ? idRelacion.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFase);
        buffer.append('-');
        buffer.append(idRelacion);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Departamento  */
public class DepartamentoPk implements java.io.Serializable {
    public String idDepartamento;

    public DepartamentoPk() {
    }

    public DepartamentoPk(String s) {
        int i;
        int p = 0;
        idDepartamento = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DepartamentoPk)) {
            return false;
        }

        final DepartamentoPk id = (DepartamentoPk) o;

        if ((this.idDepartamento != null)
                ? (!idDepartamento.equals(id.idDepartamento))
                    : (id.idDepartamento != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idDepartamento != null) ? idDepartamento.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDepartamento);

        return buffer.toString();
    }
}
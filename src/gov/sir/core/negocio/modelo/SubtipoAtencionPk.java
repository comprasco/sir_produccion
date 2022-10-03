package gov.sir.core.negocio.modelo;

/**
 * Clase que define los atributos que identifican a SubtipoAtencion
 */
public class SubtipoAtencionPk implements java.io.Serializable {
    public String idSubtipoAtencion;

    public SubtipoAtencionPk() {
    }

    public SubtipoAtencionPk(SubtipoAtencionPk id) {
        idSubtipoAtencion = id.idSubtipoAtencion;
    }

    public SubtipoAtencionPk(String s) {
        int i;
        int p = 0;
        idSubtipoAtencion = s.substring(p);
    }

    public SubtipoAtencionPk getSubtipoAtencionID() {
        SubtipoAtencionPk id = new SubtipoAtencionPk();
        id.idSubtipoAtencion = idSubtipoAtencion;

        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SubtipoAtencionPk)) {
            return false;
        }

        final SubtipoAtencionPk id = (SubtipoAtencionPk) o;

        if ((this.idSubtipoAtencion != null)
                ? (!idSubtipoAtencion.equals(id.idSubtipoAtencion))
                    : (id.idSubtipoAtencion != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idSubtipoAtencion != null) ? idSubtipoAtencion.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idSubtipoAtencion);

        return buffer.toString();
    }
}
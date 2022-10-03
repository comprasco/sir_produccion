package gov.sir.core.negocio.modelo;

/**
* Clase que define los atributos que identifican a AnotaciónCiudadano
*/
public class AnotacionCiudadanoPk implements java.io.Serializable {
    public String idAnotacion;
    public String idCiudadano;
    public String idMatricula;
    public String rolPersona;

    public AnotacionCiudadanoPk() {
    }

    public AnotacionCiudadanoPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idAnotacion = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idCiudadano = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idMatricula = s.substring(p, i);
        p = i + 1;
        rolPersona = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AnotacionCiudadanoPk)) {
            return false;
        }

        final AnotacionCiudadanoPk id = (AnotacionCiudadanoPk) o;

        if ((this.idAnotacion != null)
                ? (!idAnotacion.equals(id.idAnotacion))
                    : (id.idAnotacion != null)) {
            return false;
        }

        if ((this.idCiudadano != null)
                ? (!idCiudadano.equals(id.idCiudadano))
                    : (id.idCiudadano != null)) {
            return false;
        }

        if ((this.idMatricula != null)
                ? (!idMatricula.equals(id.idMatricula))
                    : (id.idMatricula != null)) {
            return false;
        }


        if ((this.rolPersona != null)
                ? (!rolPersona.equals(id.rolPersona))
                    : (id.rolPersona != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idAnotacion != null) ? idAnotacion.hashCode() : 0);
        result = (29 * result) +
            ((idCiudadano != null) ? idCiudadano.hashCode() : 0);
        result = (29 * result) +
            ((idMatricula != null) ? idMatricula.hashCode() : 0);
		result = (29 * result) +
					   ((rolPersona != null) ? rolPersona.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idAnotacion);
        buffer.append('-');
        buffer.append(idCiudadano);
        buffer.append('-');
        buffer.append(idMatricula);
		buffer.append('-');
		buffer.append(rolPersona);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo.jdogenie;

/**
* Clase que define los atributos de un prefabricado
*/
public class PrefabricadoPk implements java.io.Serializable {
    public String idCirculo;
    public String idMatricula;

    public PrefabricadoPk() {
    }

    public PrefabricadoPk(String s) {
        int i;

        i = s.indexOf('-', 0);
        idCirculo = s.substring(0, i);
        idMatricula = s;
    }
    
    public PrefabricadoPk(String circulo, String matricula) {
    	idCirculo = circulo;
        idMatricula = matricula;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PrefabricadoPk)) {
            return false;
        }

        final PrefabricadoPk id = (PrefabricadoPk) o;

        if ((this.idCirculo != null)
                ? (!idCirculo.equals(id.idCirculo))
                    : (id.idCirculo != null)) {
            return false;
        }

        if ((this.idMatricula != null)
                ? (!idMatricula.equals(id.idMatricula))
                    : (id.idMatricula != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idCirculo != null) ? idCirculo.hashCode() : 0);
        result = (29 * result) +
            ((idMatricula != null) ? idMatricula.hashCode() : 0);

        return result;
    }

    public String toString() {
        return idMatricula;
    }
}
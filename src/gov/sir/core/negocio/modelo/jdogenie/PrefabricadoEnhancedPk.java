package gov.sir.core.negocio.modelo.jdogenie;

/**
* Application identity objectid-class.
*/
public class PrefabricadoEnhancedPk implements java.io.Serializable {
    public String idMatricula;
    public String idCirculo;
    
    public PrefabricadoEnhancedPk() {
    }

    public PrefabricadoEnhancedPk(String s) {
        idMatricula = s;
        idCirculo = s.substring(0, s.indexOf('-', 0));

    }

    public PrefabricadoEnhancedPk(PrefabricadoPk id) {
        this.idMatricula = id.idMatricula;
        this.idCirculo = id.idCirculo;

    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PrefabricadoEnhancedPk)) {
            return false;
        }

        final PrefabricadoEnhancedPk id = (PrefabricadoEnhancedPk) o;

        if ((this.idMatricula != null) ? (!idMatricula.equals(id.idMatricula)) : (id.idMatricula != null)) {
            return false;
        }

        if ((this.idCirculo != null)
                ? (!idCirculo.equals(id.idCirculo))
                    : (id.idCirculo != null)) {
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
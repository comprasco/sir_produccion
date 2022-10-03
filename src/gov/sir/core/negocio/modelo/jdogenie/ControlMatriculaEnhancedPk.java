package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ControlMatriculaPk;
import gov.sir.core.negocio.modelo.HistorialAreasPk;


/**
** @Author Cristian David Garcia
** @Requerimiento Catastro Multiproposito
**  Application identity objectid-class.
*/
public class ControlMatriculaEnhancedPk implements java.io.Serializable{
    
    public String idMatricula;

    public ControlMatriculaEnhancedPk() {
    }

    public ControlMatriculaEnhancedPk(ControlMatriculaPk id) {
        idMatricula = id.idMatricula;
    }

    public ControlMatriculaEnhancedPk(String s) {
    	idMatricula = s;
    }

    public ControlMatriculaPk getControlMatriculaID() {
        ControlMatriculaPk id = new ControlMatriculaPk();
        id.idMatricula = idMatricula;
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ControlMatriculaEnhancedPk)) {
            return false;
        }

        final ControlMatriculaEnhancedPk id = (ControlMatriculaEnhancedPk) o;

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
            ((idMatricula != null) ? idMatricula.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idMatricula);
        return buffer.toString();
    }
    
}

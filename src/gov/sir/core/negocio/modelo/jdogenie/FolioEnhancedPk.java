package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.FolioPk;

/**
* Application identity objectid-class.
*/
public class FolioEnhancedPk implements java.io.Serializable {
    public String idMatricula;

    public FolioEnhancedPk() {
    }

    public FolioEnhancedPk(FolioPk id) {
        idMatricula = id.idMatricula;
    }

    public FolioEnhancedPk(String s) {
    	idMatricula = s;
    }

    public FolioPk getFolioID() {
        FolioPk id = new FolioPk();
        id.idMatricula = idMatricula;
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof FolioEnhancedPk)) {
            return false;
        }

        final FolioEnhancedPk id = (FolioEnhancedPk) o;

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
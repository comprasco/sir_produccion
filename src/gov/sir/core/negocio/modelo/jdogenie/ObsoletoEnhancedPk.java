package gov.sir.core.negocio.modelo.jdogenie;

/**
 * Application identity objectid-class.
 * @author juan.rave
 */
public class ObsoletoEnhancedPk implements java.io.Serializable {
	public String idMatricula;
    public String idCirculo;

    public ObsoletoEnhancedPk() {
    }

    public ObsoletoEnhancedPk(ObsoletoPk id) {
    	idMatricula = id.idMatricula;
        idCirculo = id.idCirculo;
    }
    
    public ObsoletoEnhancedPk(String m) {
        int i;
        
        idMatricula = m;
        i = idMatricula.indexOf('-', 0);
        idCirculo = m.substring(0, i);
        
    }
    
    public ObsoletoEnhancedPk(String m, String c) {
    	idMatricula = m;
        idCirculo = c;
    }

    public ObsoletoPk getObsoletoID() {
    	ObsoletoPk id = new ObsoletoPk();
    	
    	id.idMatricula = idMatricula;
        id.idCirculo = idCirculo;

        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ObsoletoEnhancedPk)) {
            return false;
        }

        final ObsoletoEnhancedPk idObsoleto = (ObsoletoEnhancedPk) o;

        if ((this.idMatricula != null)
                ? (!idMatricula.equals(idObsoleto.idMatricula))
                    : (idObsoleto.idMatricula != null)) {
            return false;
        }
        
        if ((this.idCirculo != null)
                ? (!idCirculo.equals(idObsoleto.idCirculo))
                    : (idObsoleto.idCirculo != null)) {
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

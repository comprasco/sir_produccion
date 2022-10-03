package gov.sir.core.negocio.modelo.jdogenie;

public class CertificadoPk implements java.io.Serializable {
	public String idMatricula;
    public String idCirculo;

    public CertificadoPk() {
    }

    public CertificadoPk(String m, String c) {
    	idMatricula = m;
        idCirculo = c;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CertificadoPk)) return false;
        
        final CertificadoPk id = (CertificadoPk) o;

        if ((this.idMatricula != null) ? (!idMatricula.equals(id.idMatricula)) : (id.idMatricula != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = ((idMatricula != null) ? idMatricula.hashCode() : 0);

        return result;
    }

    public String toString() {
        return idMatricula;
    }
}

package gov.sir.core.negocio.modelo.jdogenie;

public class ObsoletoPk implements java.io.Serializable {
	public String idMatricula;
    public String idCirculo;

    public ObsoletoPk() {
    }

    public ObsoletoPk(String m, String c) {
    	idMatricula = m;
        idCirculo = c;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObsoletoPk)) return false;

        final ObsoletoPk id = (ObsoletoPk) o;

        if ((this.idMatricula != null) ? (!idMatricula.equals(id.idMatricula)) : (id.idMatricula != null)) {
            return false;
        }
        
        if ((this.idCirculo != null) ? (!idCirculo.equals(id.idCirculo)) : (id.idCirculo != null)) {
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

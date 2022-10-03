package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a FirmaRegistrador  */
public class FirmaRegistradorPk implements java.io.Serializable {

	public Long idFirmaRegistrador;

    public FirmaRegistradorPk() {
    }

    public FirmaRegistradorPk(String s) {
    	idFirmaRegistrador=Long.getLong(s);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FirmaRegistradorPk)) return false;

        final FirmaRegistradorPk id = (FirmaRegistradorPk) o;

        if (this.idFirmaRegistrador != null ? !idFirmaRegistrador.equals(id.idFirmaRegistrador) : id.idFirmaRegistrador != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idFirmaRegistrador != null ? idFirmaRegistrador.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFirmaRegistrador);
        return buffer.toString();
    }
}
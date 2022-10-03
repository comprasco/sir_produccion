package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.FirmaRegistradorPk;

/**
 * Application identity objectid-class.
 */
public class FirmaRegistradorEnhancedPk implements java.io.Serializable {

	public Long idFirmaRegistrador;

    public FirmaRegistradorEnhancedPk() {
    }
    

	public FirmaRegistradorEnhancedPk(FirmaRegistradorPk id) {
		idFirmaRegistrador = id.idFirmaRegistrador;
	}


	public FirmaRegistradorPk getFolioID() {
		FirmaRegistradorPk id = new FirmaRegistradorPk();
		id.idFirmaRegistrador = idFirmaRegistrador;
		return id;
	}

    public FirmaRegistradorEnhancedPk(String s) {
        /*int i, p = 0;
        i= s.indexOf('-', p);
        idArchivo = s.substring(p, i);
        p = i + 1;
        idCirculo = s.substring(p);*/
    	idFirmaRegistrador=Long.getLong(s);

    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FirmaRegistradorEnhancedPk)) return false;

        final FirmaRegistradorEnhancedPk id = (FirmaRegistradorEnhancedPk) o;

        if (this.idFirmaRegistrador != null ? !idFirmaRegistrador.equals(id.idFirmaRegistrador) : id.idFirmaRegistrador != null) return false;
       // if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idFirmaRegistrador != null ? idFirmaRegistrador.hashCode() : 0);
        //result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFirmaRegistrador);
        /*buffer.append('-');
        buffer.append(idCirculo);*/
        return buffer.toString();
    }
}
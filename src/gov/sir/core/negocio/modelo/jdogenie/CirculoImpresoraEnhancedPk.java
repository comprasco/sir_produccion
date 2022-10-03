package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CirculoImpresoraPk;

/**
 * Application identity objectid-class.
 */
public class CirculoImpresoraEnhancedPk implements java.io.Serializable {

    public String idCirculo;
    public String idImpresora;
    public String idTipoImprimible;

    public CirculoImpresoraEnhancedPk() {
    }

    public CirculoImpresoraEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCirculo = s.substring(p, i);
        i = s.indexOf('-', p);
        idImpresora = s.substring(p, i);
        p = i + 1;
        idTipoImprimible = s.substring(p);
    }
    

	public CirculoImpresoraEnhancedPk(CirculoImpresoraPk oid){
		idImpresora = oid.idImpresora;
		idCirculo = oid.idCirculo;
		idTipoImprimible = oid.idTipoImprimible;
	}
    
	public CirculoImpresoraPk getCirculoImpresoraID(){
		CirculoImpresoraPk rta = new CirculoImpresoraPk();
		rta.idImpresora = idImpresora;
		rta.idCirculo = idCirculo;
		rta.idTipoImprimible = idTipoImprimible;
		return rta;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CirculoImpresoraEnhancedPk)) return false;

        final CirculoImpresoraEnhancedPk id = (CirculoImpresoraEnhancedPk) o;

        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.idImpresora != null ? !idImpresora.equals(id.idImpresora) : id.idImpresora != null) return false;
        if (this.idTipoImprimible != null ? !idTipoImprimible.equals(id.idTipoImprimible) : id.idTipoImprimible != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = 29 * result + (idImpresora != null ? idImpresora.hashCode() : 0);
        result = 29 * result + (idTipoImprimible != null ? idTipoImprimible.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCirculo);
        buffer.append('-');
        buffer.append(idImpresora);
        buffer.append('-');
        buffer.append(idTipoImprimible);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.OPLookupTypesPk;

/**
 * Application identity objectid-class.
 */
public class OPLookupTypesEnhancedPk implements java.io.Serializable {

    public String tipo;

    public OPLookupTypesEnhancedPk() {
    }

    public OPLookupTypesEnhancedPk(String s) {
        int i, p = 0;
        tipo = s.substring(p);
    }
    
    public OPLookupTypesEnhancedPk(OPLookupTypesPk  id){
        tipo = id.tipo;
    }
    
    public OPLookupTypesPk getLookupTypesID(){
        return new OPLookupTypesPk(tipo);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OPLookupTypesEnhancedPk)) return false;

        final OPLookupTypesEnhancedPk id = (OPLookupTypesEnhancedPk) o;

        if (this.tipo != null ? !tipo.equals(id.tipo) : id.tipo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(tipo);
        return buffer.toString();
    }
}
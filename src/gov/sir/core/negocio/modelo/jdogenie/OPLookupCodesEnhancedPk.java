package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.OPLookupCodesPk;

/**
 * Application identity objectid-class.
 */
public class OPLookupCodesEnhancedPk implements java.io.Serializable {

    public String codigo;
    public String tipo;

    public OPLookupCodesEnhancedPk() {
    }

    public OPLookupCodesEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        codigo = s.substring(p, i);
        p = i + 1;
        tipo = s.substring(p);
    }
    
    public OPLookupCodesEnhancedPk(OPLookupCodesPk id){
        codigo = id.codigo;
        tipo = id.tipo;
    }
    
    public OPLookupCodesPk getOPLookupCodesID(){
        OPLookupCodesPk id = new OPLookupCodesPk();
        id.codigo = codigo;
        id.tipo = tipo;
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OPLookupCodesEnhancedPk)) return false;

        final OPLookupCodesEnhancedPk id = (OPLookupCodesEnhancedPk) o;

        if (this.codigo != null ? !codigo.equals(id.codigo) : id.codigo != null) return false;
        if (this.tipo != null ? !tipo.equals(id.tipo) : id.tipo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (codigo != null ? codigo.hashCode() : 0);
        result = 29 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(codigo);
        buffer.append('-');
        buffer.append(tipo);
        return buffer.toString();
    }
}
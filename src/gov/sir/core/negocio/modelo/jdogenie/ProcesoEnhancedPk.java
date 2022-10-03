package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ProcesoPk;

/**
 * Application identity objectid-class.
 */
public class ProcesoEnhancedPk implements java.io.Serializable {

    public long idProceso;

    public ProcesoEnhancedPk() {
    }

    public ProcesoEnhancedPk(String s) {
        int i, p = 0;
        idProceso = Long.parseLong(s.substring(p));
    }
    
    public ProcesoEnhancedPk(ProcesoPk id){
        idProceso = id.idProceso;
    }
    
    public ProcesoPk getProcesoID(){
        ProcesoPk id = new ProcesoPk();
        id.idProceso = idProceso;
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcesoEnhancedPk)) return false;

        final ProcesoEnhancedPk id = (ProcesoEnhancedPk) o;

        if (this.idProceso != id.idProceso) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idProceso);
        return buffer.toString();
    }
}
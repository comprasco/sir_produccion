package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.PagoPk;

/**
 * Application identity objectid-class.
 */
public class PagoEnhancedPk implements java.io.Serializable {
    public String idLiquidacion;
    public String idSolicitud;
    
    public PagoEnhancedPk() {
    }
    
    public PagoEnhancedPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idLiquidacion = s.substring(p, i);
        p = i + 1;
        idSolicitud = s.substring(p);
    }
    
    public PagoEnhancedPk(PagoPk id){
        idLiquidacion = id.idLiquidacion;
        idSolicitud = id.idSolicitud;
    }
    
    public PagoPk getPagoID(){
        PagoPk id = new PagoPk();
        id.idLiquidacion = idLiquidacion;
        id.idSolicitud = idSolicitud;
        return id;
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (!(o instanceof PagoEnhancedPk)) {
            return false;
        }
        
        final PagoEnhancedPk id = (PagoEnhancedPk) o;
        
        
        if ((this.idLiquidacion != null)
        ? (!idLiquidacion.equals(id.idLiquidacion))
        : (id.idLiquidacion != null)) {
            return false;
        }
        
        if ((this.idSolicitud != null)
        ? (!idSolicitud.equals(id.idSolicitud))
        : (id.idSolicitud != null)) {
            return false;
        }
        
        return true;
    }
    
    public int hashCode() {
        int result = 0;
        result = (29 * result) +
        ((idLiquidacion != null) ? idLiquidacion.hashCode() : 0);
        result = (29 * result) +
        ((idSolicitud != null) ? idSolicitud.hashCode() : 0);
        
        return result;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idLiquidacion);
        buffer.append('-');
        buffer.append(idSolicitud);
        
        return buffer.toString();
    }
}
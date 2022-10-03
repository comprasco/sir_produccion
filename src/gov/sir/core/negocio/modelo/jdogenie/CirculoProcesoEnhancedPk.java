package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CirculoProcesoPk;

/**
 * Application identity objectid-class.
 */
public class CirculoProcesoEnhancedPk implements java.io.Serializable {

    public String anio;
    public String idCirculo;
    public long idProceso;

    public CirculoProcesoEnhancedPk() {
    }
    
	public CirculoProcesoEnhancedPk(CirculoProcesoPk id){
		idCirculo = id.idCirculo;
		idProceso = id.idProceso;
	}
	
	public CirculoProcesoPk getCirculoProcesoID(){
		CirculoProcesoPk id = new CirculoProcesoPk();
		id.idCirculo = idCirculo;
		id.idProceso = idProceso;
		return id;
	}

    public CirculoProcesoEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        anio = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idCirculo = s.substring(p, i);
        p = i + 1;
        idProceso = Long.parseLong(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CirculoProcesoEnhancedPk)) return false;

        final CirculoProcesoEnhancedPk id = (CirculoProcesoEnhancedPk) o;

        if (this.anio != null ? !anio.equals(id.anio) : id.anio != null) return false;
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.idProceso != id.idProceso) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (anio != null ? anio.hashCode() : 0);
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(anio);
        buffer.append('-');
        buffer.append(idCirculo);
        buffer.append('-');
        buffer.append(idProceso);
        return buffer.toString();
    }
}
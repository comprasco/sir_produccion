package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.RepartoPk;

/**
 * Application identity objectid-class.
 */
public class RepartoEnhancedPk implements java.io.Serializable {

    public String anio;
    public String idCirculo;
    public long idProceso;
    public String idProcesoReparto;
    public String idTurno;
    public long idUsuario;

    public RepartoEnhancedPk() {
    }

    public RepartoEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        anio = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idCirculo = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idProceso = Long.parseLong(s.substring(p, i));
        p = i + 1;
        i= s.indexOf('-', p);
        idProcesoReparto = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idTurno = s.substring(p, i);
        p = i + 1;
        idUsuario = Long.parseLong(s.substring(p));
    }
    

	public RepartoEnhancedPk (RepartoPk id){
		anio = id.anio;
		idCirculo = id.idCirculo;
		idUsuario = id.idUsuario;	
		idProceso = id.idProceso;
		idTurno = id.idTurno;
		idProcesoReparto = id.idProcesoReparto;
	}
	
	public RepartoPk getRepartoID(){
		RepartoPk id = new RepartoPk();
		id.anio = anio;
		id.idCirculo = idCirculo;
		id.idUsuario = idUsuario;	
		id.idProceso = idProceso;
		id.idTurno = idTurno;
		id.idProcesoReparto = idProcesoReparto;
		return id;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepartoEnhancedPk)) return false;

        final RepartoEnhancedPk id = (RepartoEnhancedPk) o;

        if (this.anio != null ? !anio.equals(id.anio) : id.anio != null) return false;
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.idProceso != id.idProceso) return false;
        if (this.idProcesoReparto != null ? !idProcesoReparto.equals(id.idProcesoReparto) : id.idProcesoReparto != null) return false;
        if (this.idTurno != null ? !idTurno.equals(id.idTurno) : id.idTurno != null) return false;
        if (this.idUsuario != id.idUsuario) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (anio != null ? anio.hashCode() : 0);
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idProcesoReparto != null ? idProcesoReparto.hashCode() : 0);
        result = 29 * result + (idTurno != null ? idTurno.hashCode() : 0);
        result = 29 * result + (int) (idUsuario ^ (idUsuario >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(anio);
        buffer.append('-');
        buffer.append(idCirculo);
        buffer.append('-');
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idProcesoReparto);
        buffer.append('-');
        buffer.append(idTurno);
        buffer.append('-');
        buffer.append(idUsuario);
        return buffer.toString();
    }
}
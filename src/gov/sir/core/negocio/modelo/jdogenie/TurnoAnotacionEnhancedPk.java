package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TurnoAnotacionPk;

/**
 * Application identity objectid-class.
 */
public class TurnoAnotacionEnhancedPk implements java.io.Serializable {

    public String anio;
    public String idAnotacion;
    public String idCirculo;
    public String idMatricula;
    public long idProceso;
    public String idTurno;

    public TurnoAnotacionEnhancedPk() {
    }
    

	public TurnoAnotacionEnhancedPk(TurnoAnotacionPk id) {
		anio = id.anio;
		idCirculo = id.idCirculo;
		idProceso = id.idProceso;
		idTurno = id.idTurno;
		idMatricula = id.idMatricula;
		idAnotacion = id.idAnotacion;
	}
	

	public TurnoAnotacionPk getTurnoAnotacionID() {
		TurnoAnotacionPk rta = new TurnoAnotacionPk();
		rta.anio = anio;
		rta.idCirculo = idCirculo;
		rta.idProceso = idProceso;
		rta.idTurno = idTurno;
		rta.idMatricula = idMatricula;
		rta.idAnotacion = idAnotacion;
		return rta;
	}

    public TurnoAnotacionEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        anio = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idAnotacion = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idCirculo = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idMatricula = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idProceso = Long.parseLong(s.substring(p, i));
        p = i + 1;
        i= s.indexOf('-', p);
        idTurno = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TurnoAnotacionEnhancedPk)) return false;

        final TurnoAnotacionEnhancedPk id = (TurnoAnotacionEnhancedPk) o;

        if (this.anio != null ? !anio.equals(id.anio) : id.anio != null) return false;
        if (this.idAnotacion != null ? !idAnotacion.equals(id.idAnotacion) : id.idAnotacion != null) return false;
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.idMatricula != null ? !idMatricula.equals(id.idMatricula) : id.idMatricula != null) return false;
        if (this.idProceso != id.idProceso) return false;
        if (this.idTurno != null ? !idTurno.equals(id.idTurno) : id.idTurno != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (anio != null ? anio.hashCode() : 0);
        result = 29 * result + (idAnotacion != null ? idAnotacion.hashCode() : 0);
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = 29 * result + (idMatricula != null ? idMatricula.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idTurno != null ? idTurno.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(anio);
        buffer.append('-');
        buffer.append(idAnotacion);
        buffer.append('-');
        buffer.append(idCirculo);
        buffer.append('-');
        buffer.append(idMatricula);
        buffer.append('-');
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idTurno);
        return buffer.toString();
    }
}
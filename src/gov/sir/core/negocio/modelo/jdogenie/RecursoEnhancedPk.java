package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.RecursoPk;

/**
 * Application identity objectid-class.
 */
public class RecursoEnhancedPk implements java.io.Serializable {

    public String anio;
    public String idCirculo;
    public long idProceso;
    public String idRecurso;
    public String idTurno;
    public String idTurnoHistoria;

    public RecursoEnhancedPk() {
    }
    

	public RecursoEnhancedPk(RecursoPk id) {
		anio = id.anio;
		idCirculo = id.idCirculo;
		idProceso = id.idProceso;
		idTurno = id.idTurno;
		idTurnoHistoria = id.idTurnoHistoria;
		idRecurso = id.idRecurso;
	}
	

	public RecursoPk getRecursoID() {
		RecursoPk rta = new RecursoPk();
		rta.anio = anio;
		rta.idCirculo = idCirculo;
		rta.idProceso = idProceso;
		rta.idTurno = idTurno;
		rta.idTurnoHistoria = idTurnoHistoria;
		rta.idRecurso = idRecurso;
		return rta;
	}

    public RecursoEnhancedPk(String s) {
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
        idRecurso = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idTurno = s.substring(p, i);
        p = i + 1;
        idTurnoHistoria = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecursoEnhancedPk)) return false;

        final RecursoEnhancedPk id = (RecursoEnhancedPk) o;

        if (this.anio != null ? !anio.equals(id.anio) : id.anio != null) return false;
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.idProceso != id.idProceso) return false;
        if (this.idRecurso != null ? !idRecurso.equals(id.idRecurso) : id.idRecurso != null) return false;
        if (this.idTurno != null ? !idTurno.equals(id.idTurno) : id.idTurno != null) return false;
        if (this.idTurnoHistoria != null ? !idTurnoHistoria.equals(id.idTurnoHistoria) : id.idTurnoHistoria != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (anio != null ? anio.hashCode() : 0);
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idRecurso != null ? idRecurso.hashCode() : 0);
        result = 29 * result + (idTurno != null ? idTurno.hashCode() : 0);
        result = 29 * result + (idTurnoHistoria != null ? idTurnoHistoria.hashCode() : 0);
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
        buffer.append(idRecurso);
        buffer.append('-');
        buffer.append(idTurno);
        buffer.append('-');
        buffer.append(idTurnoHistoria);
        return buffer.toString();
    }
}
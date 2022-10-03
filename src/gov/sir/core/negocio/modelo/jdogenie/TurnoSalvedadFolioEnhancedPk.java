package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TurnoSalvedadFolioPk;

/**
 * Application identity objectid-class.
 */
public class TurnoSalvedadFolioEnhancedPk implements java.io.Serializable {

    public String anio;
    public String idCirculo;
    public String idMatricula;
    public long idProceso;
    public String idSalvedadFo;
    public String idTurno;

    public TurnoSalvedadFolioEnhancedPk() {
    }
    

	public TurnoSalvedadFolioEnhancedPk(TurnoSalvedadFolioPk id) {
		anio = id.anio;
		idCirculo = id.idCirculo;
		idProceso = id.idProceso;
		idTurno = id.idTurno;
		idMatricula = id.idMatricula;
		idSalvedadFo = id.idSalvedadFo;
	}
	

	public TurnoSalvedadFolioPk getTurnoSalvedadFolioID() {
		TurnoSalvedadFolioPk rta = new TurnoSalvedadFolioPk();
		rta.anio = anio;
		rta.idCirculo = idCirculo;
		rta.idProceso = idProceso;
		rta.idTurno = idTurno;
		rta.idMatricula = idMatricula;
		rta.idSalvedadFo = idSalvedadFo;
		return rta;
	}

    public TurnoSalvedadFolioEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        anio = s.substring(p, i);
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
        idSalvedadFo = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idTurno = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TurnoSalvedadFolioEnhancedPk)) return false;

        final TurnoSalvedadFolioEnhancedPk id = (TurnoSalvedadFolioEnhancedPk) o;

        if (this.anio != null ? !anio.equals(id.anio) : id.anio != null) return false;
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.idMatricula != null ? !idMatricula.equals(id.idMatricula) : id.idMatricula != null) return false;
        if (this.idProceso != id.idProceso) return false;
        if (this.idSalvedadFo != null ? !idSalvedadFo.equals(id.idSalvedadFo) : id.idSalvedadFo != null) return false;
        if (this.idTurno != null ? !idTurno.equals(id.idTurno) : id.idTurno != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (anio != null ? anio.hashCode() : 0);
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = 29 * result + (idMatricula != null ? idMatricula.hashCode() : 0);
        result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
        result = 29 * result + (idSalvedadFo != null ? idSalvedadFo.hashCode() : 0);
        result = 29 * result + (idTurno != null ? idTurno.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(anio);
        buffer.append('-');
        buffer.append(idCirculo);
        buffer.append('-');
        buffer.append(idMatricula);
        buffer.append('-');
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idSalvedadFo);
        buffer.append('-');
        buffer.append(idTurno);
        return buffer.toString();
    }
}
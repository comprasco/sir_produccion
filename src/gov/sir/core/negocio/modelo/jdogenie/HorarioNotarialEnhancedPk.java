package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.HorarioNotarialPk;

/**
 * Application identity objectid-class.
 */
public class HorarioNotarialEnhancedPk implements java.io.Serializable {

    public String idCirculo;
    public String idTipoHorarioNotarial;
    public String idDia;
    public String idConsecutivo;

    public HorarioNotarialEnhancedPk() {
    }

	public HorarioNotarialEnhancedPk(HorarioNotarialPk id){
		idCirculo = id.idCirculo;
		idTipoHorarioNotarial = id.idTipoHorarioNotarial;
		idDia = id.idDia;
		idConsecutivo = id.idConsecutivo;
	}
	
	public HorarioNotarialPk getHorarioNotarialID(){
		HorarioNotarialPk id = new HorarioNotarialPk();
		id.idCirculo= idCirculo;
		id.idTipoHorarioNotarial = idTipoHorarioNotarial;
		id.idDia = idDia;
		id.idConsecutivo = idConsecutivo;
		return id;
	}

    public HorarioNotarialEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCirculo = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idTipoHorarioNotarial = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idDia = s.substring(p);
        p = i + 1;
        i= s.indexOf('-', p);
        idConsecutivo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HorarioNotarialEnhancedPk)) return false;

        final HorarioNotarialEnhancedPk id = (HorarioNotarialEnhancedPk) o;

        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        if (this.idTipoHorarioNotarial != null ? !idTipoHorarioNotarial.equals(id.idTipoHorarioNotarial) : id.idTipoHorarioNotarial != null) return false;
        if (this.idDia != null ? !idDia.equals(id.idDia) : id.idDia != null) return false;
        if (this.idConsecutivo != null ? !idConsecutivo.equals(id.idConsecutivo) : id.idConsecutivo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        result = 29 * result + (idTipoHorarioNotarial != null ? idTipoHorarioNotarial.hashCode() : 0);
        result = 29 * result + (idDia != null ? idDia.hashCode() : 0);
        result = 29 * result + (idConsecutivo != null ? idConsecutivo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCirculo);
        buffer.append('-');
        buffer.append(idTipoHorarioNotarial);
        buffer.append('-');
        buffer.append(idDia);
        buffer.append('-');
        buffer.append(idConsecutivo);
        return buffer.toString();
    }
}
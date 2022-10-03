package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.HorarioNotarial;

/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class HorarioNotarialEnhanced extends Enhanced
{
	private String idCirculo;
	private String idTipoHorarioNotarial;
	private String idDia;
	private String idConsecutivo;
	private String horaInicio;
	private String minInicio;
	private String horaFin;
	private String minFin;
    
    public String getIdDia() {
		return idDia;
	}

	public void setIdDia(String idDia) {
		this.idDia = idDia;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getIdCirculo() {
		return idCirculo;
	}

	public void setIdCirculo(String idCirculo) {
		this.idCirculo = idCirculo;
	}

	public String getIdTipoHorarioNotarial() {
		return idTipoHorarioNotarial;
	}

	public void setIdTipoHorarioNotarial(String idTipoHorarioNotarial) {
		this.idTipoHorarioNotarial = idTipoHorarioNotarial;
	}

	public String getMinFin() {
		return minFin;
	}

	public void setMinFin(String minFin) {
		this.minFin = minFin;
	}

	public String getMinInicio() {
		return minInicio;
	}

	public void setMinInicio(String minInicio) {
		this.minInicio = minInicio;
	}

	public String getIdConsecutivo() {
		return idConsecutivo;
	}

	public void setIdConsecutivo(String idConsecutivo) {
		this.idConsecutivo = idConsecutivo;
	}
	
	public static HorarioNotarialEnhanced enhance(HorarioNotarial horarioNotarial) {
		return (HorarioNotarialEnhanced) Enhanced.enhance(horarioNotarial);
	}
}
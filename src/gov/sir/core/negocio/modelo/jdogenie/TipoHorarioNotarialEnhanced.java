package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoHorarioNotarial;

/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class TipoHorarioNotarialEnhanced extends Enhanced{

    private String idTipoHorarioNotarial; // pk 
    private String nombre;
    private String descripcion;
    
    public TipoHorarioNotarialEnhanced() {
    }
    
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdTipoHorarioRepartoNotarial() {
		return idTipoHorarioNotarial;
	}

	public void setIdTipoHorarioRepartoNotarial(String idTipoHorarioRepartoNotarial) {
		this.idTipoHorarioNotarial = idTipoHorarioRepartoNotarial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static TipoHorarioNotarialEnhanced enhance(TipoHorarioNotarial tipoHorarioNotarial) 
	{
		return (TipoHorarioNotarialEnhanced) Enhanced.enhance(tipoHorarioNotarial);
	}
}
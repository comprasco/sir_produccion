package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Validacion;




/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class ValidacionEnhanced extends Enhanced{

    private String idValidacion; // pk 
    private String nombre;

    public ValidacionEnhanced() {
    }

    public String getIdValidacion() {
        return idValidacion;
    }

    public void setIdValidacion(String idValidacion) {
        this.idValidacion = idValidacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
 
	public static ValidacionEnhanced enhance(Validacion validacion) {
		return (ValidacionEnhanced) Enhanced.enhance(validacion);
	}
}
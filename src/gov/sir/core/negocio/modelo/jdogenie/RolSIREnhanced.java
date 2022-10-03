package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.RolSIR;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class RolSIREnhanced extends Enhanced{

    private String idRol; // pk
    private String descripcion;
    private String nombre;
    private List pantallas = new ArrayList(); // contains RolPantallaEnhanced  inverse RolPantallaEnhanced.rol

    public RolSIREnhanced() {
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List getPantallas() {
        return Collections.unmodifiableList(pantallas);
    }

    public boolean addPantalla(RolPantallaEnhanced newPantalla) {
        return pantallas.add(newPantalla);
    }

    public boolean removePantalla(RolPantallaEnhanced oldPantalla) {
        return pantallas.remove(oldPantalla);
    }


	public static RolSIREnhanced enhance (
	RolSIR rol)
	{
		return (RolSIREnhanced)Enhanced.enhance(rol);
	}
}
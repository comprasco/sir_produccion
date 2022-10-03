package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoDerechoReg;

import java.util.Date;



/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class TipoDerechoRegEnhanced extends Enhanced{

    private String idTipoDerechoReg; // pk 
    private Date fechaCreacion;
    private String nombre;

    public TipoDerechoRegEnhanced() {
    }

    public String getIdTipoDerechoReg() {
        return idTipoDerechoReg;
    }

    public void setIdTipoDerechoReg(String idTipoDerechoReg) {
        this.idTipoDerechoReg = idTipoDerechoReg;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
	public static TipoDerechoRegEnhanced enhance (
	TipoDerechoReg tipoDerechoReg)
	{
		return (TipoDerechoRegEnhanced)Enhanced.enhance(tipoDerechoReg);
	}
}
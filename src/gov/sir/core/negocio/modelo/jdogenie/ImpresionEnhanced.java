package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Impresion;

import java.util.Date;

/*
 * Generated by Versant Open Access (version:3.2.6 (17 Nov 2004))
 */
public class ImpresionEnhanced extends Enhanced{

    private String idSesion; // pk 
    private boolean administrador;
    private String direccionIP;
    private Date fechaCreacion;
    private String puerto;
    private String circulo;

    public ImpresionEnhanced() {
    }

    public String getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }

    public String getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }
    
  
	public static ImpresionEnhanced enhance(Impresion x){
		return (ImpresionEnhanced) Enhanced.enhance(x);
	}

    /**
	 * @return
	 */
	public boolean isAdministrador() {
		return administrador;
	}

	/**
	 * @param b
	 */
	public void setAdministrador(boolean b) {
		administrador = b;
	}

	public String getCirculo() {
		return circulo;
	}

	public void setCirculo(String circulo) {
		this.circulo = circulo;
	}

}
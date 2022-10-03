package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.FirmaRegistrador;

import java.util.Date;

/*
 * Generated by Versant Open Access (version:3.2.6 (17 Nov 2004))
 */
public class FirmaRegistradorEnhanced extends Enhanced{

	private Long idFirmaRegistrador;// pk 
	
    private String idArchivo; 
    private String idCirculo;
    private int activo; 
    private Date fechaCreacion;
    private CirculoEnhanced circulo; // inverse Circulo.firmas
    private String nombreRegistrador;
    private String cargoRegistrador;

    public FirmaRegistradorEnhanced() {
    }

    public String getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(String idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

  

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public CirculoEnhanced getCirculo() {
        return circulo;
    }

    public void setCirculo(CirculoEnhanced circulo) {
        this.circulo = circulo;
        setIdCirculo(circulo.getIdCirculo());
    }
    

	public static FirmaRegistradorEnhanced enhance(FirmaRegistrador firma) {
		return (FirmaRegistradorEnhanced) Enhanced.enhance(firma);
	}

    /**
	 * @return
	 */
	public int getActivo() {
		return activo;
	}

	/**
	 * @param b
	 */
	public void setActivo(int b) {
		activo = b;
	}

	/**
	 * @return
	 */
	public String getCargoRegistrador() {
		return cargoRegistrador;
	}

	/**
	 * @return
	 */
	public String getNombreRegistrador() {
		return nombreRegistrador;
	}

	/**
	 * @param string
	 */
	public void setCargoRegistrador(String string) {
		cargoRegistrador = string;
	}

	/**
	 * @param string
	 */
	public void setNombreRegistrador(String string) {
		nombreRegistrador = string;
	}

	public Long getIdFirmaRegistrador() {
		return idFirmaRegistrador;
	}

	public void setIdFirmaRegistrador(Long idFirmaRegistrador) {
		this.idFirmaRegistrador = idFirmaRegistrador;
	}
	
	

}
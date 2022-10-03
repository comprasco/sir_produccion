package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Vereda;




/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class VeredaEnhanced extends Enhanced {
    private String idVereda; // pk 
    private String idMunicipio; //pk
    private String idDepartamento; //pk
    private String nombre;
    private MunicipioEnhanced municipio; // inverse Municipio.veredas
    private boolean cabecera;

    public VeredaEnhanced() {
    }

    public String getIdVereda() {
        return idVereda;
    }

    public void setIdVereda(String idVereda) {
        this.idVereda = idVereda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MunicipioEnhanced getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioEnhanced municipio) {
        this.municipio = municipio;
        this.setIdMunicipio(municipio.getIdMunicipio());
        this.setIdDepartamento(municipio.getIdDepartamento());
    }

  
	public static VeredaEnhanced enhance(Vereda v){
		return (VeredaEnhanced)Enhanced.enhance(v);
	}

    /**
	 * @return
	 */
	public String getIdDepartamento() {
		return idDepartamento;
	}

	/**
	 * @return
	 */
	public String getIdMunicipio() {
		return idMunicipio;
	}

	/**
	 * @param string
	 */
	public void setIdDepartamento(String string) {
		idDepartamento = string;
	}

	/**
	 * @param string
	 */
	public void setIdMunicipio(String string) {
		idMunicipio = string;
	}

	/**
	 * @return
	 */
	public boolean isCabecera() {
		return cabecera;
	}

	/**
	 * @param b
	 */
	public void setCabecera(boolean b) {
		cabecera = b;
	}

}
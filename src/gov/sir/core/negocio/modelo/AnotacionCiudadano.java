package gov.sir.core.negocio.modelo;

import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;

import org.auriga.core.modelo.TransferObject;


/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */

/**
 * Clase que representa la relaci�n ciudadano con anotacion y registra 
 * sus respectivos atributos
 */
public class AnotacionCiudadano implements TransferObject {
    public static final String ROL_PERSONA_DE = "DE";
    public static final String ROL_PERSONA_A = "A";
    private String idAnotacion; // pk 
    private String idCiudadano; // pk 
    private String idMatricula; // pk 
    private String descripcion;
    private int marcaPropietario;
    private String participacion;
    private String rolPersona;
    private Anotacion anotacion; // inverse Anotacion.anotacionesCiudadano
    private Ciudadano ciudadano;
    private boolean toDelete;
    private boolean toUpdate;
    private String circulo;
private static final long serialVersionUID = 1L;
    /**
     * Constructor por defecto     */
    public AnotacionCiudadano() {
    }

    /**
     * Retorna el identificador de la anotaci�n
     * @return idAnotacion
     */
    public String getIdAnotacion() {
        return idAnotacion;
    }

    /**
     * Cambia el identificador de la anotaci�n
     * @paranm idAnotacion
     */
    public void setIdAnotacion(String idAnotacion) {
        this.idAnotacion = idAnotacion;
    }

    /**
     * Retorna el identificador del ciudadano que hace parte de la anotaci�n
     * @return idCiudadano
     */
    public String getIdCiudadano() {
        return idCiudadano;
    }

    /**
     * Retorna el identificador del ciudadano que hace parte de la anotaci�n
     * @return idCiudadano
     */
    public void setIdCiudadano(String idCiudadano) {
        this.idCiudadano = idCiudadano;
    }

    /**
     * Retorna el identificador del folio
     * @return idMatricula
     */
    public String getIdMatricula() {
        return idMatricula;
    }

    /**
     * Cambia el identificador del folio
     * @paranm idMatricula
     */
    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }


    /**Retorna la descripci�n del ciudadano en la anotaci�n
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Cambia la descripci�n del ciudadano en la anotaci�n
     * @paranm descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Retorna el rol de la persona en la anotaci�n (DE, A)
     * @return rolPersona
     */
    public String getRolPersona() {
        return rolPersona;
    }

    /**
     * Cambia el rol de la persona en la anotaci�n (DE, A)
     * @paranm rolPersona
     */
    public void setRolPersona(String rolPersona) {
        this.rolPersona = rolPersona;
    }

    /**
     * Retorna el identificador de la anotaci�n
     * @paranm anotacion
     */
    public Anotacion getAnotacion() {
        return anotacion;
    }

    /**
     * Cambia el identificador de la anotaci�n
     */
    public void setAnotacion(Anotacion anotacion) {
        this.anotacion = anotacion;
        setIdAnotacion(anotacion.getIdAnotacion());
        setIdMatricula(anotacion.getIdMatricula());
    }

    /**
     * Retorna el identificador del ciudadano que hace parte de la anotaci�n
     * @return ciudadano
     */
    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    /**
     * Cambia el identificador del ciudadano que hace parte de la anotaci�n
     * @paranm ciudadano
     */
    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
        setIdCiudadano(ciudadano.getIdCiudadano());
    }

    /**
	 * @return
	 */
	public boolean isToDelete() {
		return toDelete;
	}

	/**
	 * @param b
	 */
	public void setToDelete(boolean b) {
		toDelete = b;
	}
	
	

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof AnotacionCiudadano)) {
			return false;
		}

		final AnotacionCiudadano id = (AnotacionCiudadano) o;

		if ((this.idAnotacion != null)
				? (!idAnotacion.equals(id.idAnotacion))
					: (id.idAnotacion != null)) {
			return false;
		}

		if ((this.idCiudadano != null)
				? (!idCiudadano.equals(id.idCiudadano))
					: (id.idCiudadano != null)) {
			return false;
		}

		if ((this.idMatricula != null)
				? (!idMatricula.equals(id.idMatricula))
					: (id.idMatricula != null)) {
			return false;
		}

		if ((this.rolPersona != null)
				? (!rolPersona.equals(id.rolPersona))
					: (id.rolPersona != null)) {
			return false;
		}

		return true;
	}

	/**
	 * Retorna la marca que indica si el ciudadano en la anotaci�n es propietario
	 * @return marcaPropietario
	 */
	public int getMarcaPropietario() {
		return marcaPropietario;
	}

	/**
	 * Cambia la marca que indica si el ciudadano en la anotaci�n es propietario
	 * @param i
	 */
	public void setMarcaPropietario(int i) {
		marcaPropietario = i;
	}
	
	/**
	 * Retorna el String que representa la marca del propietario:
	 * "X": TITULAR_DERECHO_REAL_DOMINIO
	 * "I": TITULAR_DOMINIO_INCOMPLETO
	 * "": NO_PROPIETARIO
	 */
	public String getStringMarcaPropietario(){
		String rta = "";
		if(this.marcaPropietario==CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO){
			rta = "X";
		}
		else if(this.marcaPropietario==CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO){
			rta = "I";
		}
		return rta;
	}

	/**
	 * Retorna el string que representa la participacion del propietario.
	 * @return
	 */
	public String getParticipacion() {
		return participacion;
	}

	/**
	 * Cambia la participacion del propietario
	 * @param participacion
	 */
	public void setParticipacion(String participacion) {
		this.participacion = participacion;
	}

	public String getCirculo() {
		return circulo;
	}

	public void setCirculo(String circulo) {
		this.circulo = circulo;
	}

	public boolean isToUpdate() {
		return toUpdate;
	}

	public void setToUpdate(boolean toUpdate) {
		this.toUpdate = toUpdate;
	}

}
/*
 * Procesos_V.java
 *
 * Created on 12 de agosto de 2004, 14:30
 */

package gov.sir.hermod.dao.impl.jdogenie;

/**
 *
 * @author  mrios
 */
public class Procesos_V {
    
    private String id_proceso; // pk
    private String nombre_proceso;
    private String desc_proceso;
    private String id_fase; // pk
    private String nombre_fase;
    private String desc_fase;
    private String rol;
    private String rol_arranque;
    private String visibilidad;

    /**
     * Atributo que indica si la fase es automática o manual.
     */
    private String tipo_fase;
	

    /** Creates a new instance of Procesos_V */
    public Procesos_V() {
    }
    
    /**
     * Getter for property desc_fase.
     * @return Value of property desc_fase.
     */
    public java.lang.String getDesc_fase() {
        return desc_fase;
    }
    
    /**
     * Setter for property desc_fase.
     * @param desc_fase New value of property desc_fase.
     */
    public void setDesc_fase(java.lang.String desc_fase) {
        this.desc_fase = desc_fase;
    }
    
    /**
     * Getter for property desc_proceso.
     * @return Value of property desc_proceso.
     */
    public java.lang.String getDesc_proceso() {
        return desc_proceso;
    }
    
    /**
     * Setter for property desc_proceso.
     * @param desc_proceso New value of property desc_proceso.
     */
    public void setDesc_proceso(java.lang.String desc_proceso) {
        this.desc_proceso = desc_proceso;
    }

    /**
     * Getter for property id_fase.
     * @return Value of property id_fase.
     */
    public java.lang.String getId_fase() {
        return id_fase;
    }
    
    /**
     * Setter for property id_fase.
     * @param id_fase New value of property id_fase.
     */
    public void setId_fase(java.lang.String id_fase) {
        this.id_fase = id_fase;
    }
    
    /**
     * Getter for property id_proceso.
     * @return Value of property id_proceso.
     */
    public java.lang.String getId_proceso() {
        return id_proceso;
    }
    
    /**
     * Setter for property id_proceso.
     * @param id_proceso New value of property id_proceso.
     */
    public void setId_proceso(java.lang.String id_proceso) {
        this.id_proceso = id_proceso;
    }
    
    /**
     * Getter for property nombre_fase.
     * @return Value of property nombre_fase.
     */
    public java.lang.String getNombre_fase() {
        return nombre_fase;
    }
    
    /**
     * Setter for property nombre_fase.
     * @param nombre_fase New value of property nombre_fase.
     */
    public void setNombre_fase(java.lang.String nombre_fase) {
        this.nombre_fase = nombre_fase;
    }
    
    /**
     * Getter for property nombre_proceso.
     * @return Value of property nombre_proceso.
     */
    public java.lang.String getNombre_proceso() {
        return nombre_proceso;
    }
    
    /**
     * Setter for property nombre_proceso.
     * @param nombre_proceso New value of property nombre_proceso.
     */
    public void setNombre_proceso(java.lang.String nombre_proceso) {
        this.nombre_proceso = nombre_proceso;
    }
    
    
    
    /**
	 * @return
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param string
	 */
	public void setRol(String string) {
		rol = string;
	}

	/**
	 * @return
	 */
	public String getRol_arranque() {
		return rol_arranque;
	}

	/**
	 * @param string
	 */
	public void setRol_arranque(String string) {
		rol_arranque = string;
	}

	/**
	 * @return
	 */
	public String getVisibilidad() {
		return visibilidad;
	}

	/**
	 * @param string
	 */
	public void setVisibilidad(String string) {
		visibilidad = string;
	}

	/**
	 * Retorna el atributo tipo_fase que indica si la fase es automática o manual.
	 * @return El atributo tipo_fase asociado con la fase (indica si es automática o manual).
	 */
	public String getTipo_fase() {
		return tipo_fase;
	}

	/**
	 * Asigna al atributo tipo_fase el valor recibido como parámetro. 
	 * @param tipo El valor que va a ser asignado al atributo tipo_fase
	 */
	public void setTipo_fase(String tipo) {
		tipo_fase = tipo;
	}

}
package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/**
 * Clase que modela los alcances geograficos de consultas configuradas en el sistema */
public class AlcanceGeografico implements TransferObject {

	public static final String TIPO_LOCAL = "0";
	public static final String TIPO_NACIONAL = "1";
    
    private String idAlcanceGeografico; // pk 
    private static final long serialVersionUID = 1L;
    /**
     * Nombre del alcance geografico
     */
    private String nombre;

    /** Constructor por defecto */
    public AlcanceGeografico() {
    }

    /**
     * Retorna el identificador del alcance geografico
     * @return idAlcanceGeografico
     */
    public String getIdAlcanceGeografico() {
        return idAlcanceGeografico;
    }

    /**
     * Cambia el identificador del alcance geografico
     * @param idAlcanceGeografico
     */
    public void setIdAlcanceGeografico(String idAlcanceGeografico) {
        this.idAlcanceGeografico = idAlcanceGeografico;
    }
    /**
     * Obtiene el Nombre del alcance geografico
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el Nombre del alcance geografico
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
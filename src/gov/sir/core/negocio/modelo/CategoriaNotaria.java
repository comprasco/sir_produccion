package gov.sir.core.negocio.modelo;


import org.auriga.core.modelo.TransferObject;


/**
 * Clase que modela las categorias en la que se pueden clasificar las Notarías
 */
public class CategoriaNotaria implements TransferObject{

    private String idCategoria; // pk 
    private String nombre;
    private String descripcion;
   
    private static final long serialVersionUID = 1L;
    /** Constructor por defecto */
    public CategoriaNotaria() {
    }
    

    /**
     * Retorna el identificador de la categoría
     * @return idCategoria  */
    public String getIdCategoria() {
        return idCategoria;
    }

    /**
     * Cambia el identificador de la categoría
     * @param idCategoria
     */
    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }


    /**
     * Retorna el nombre de la categoría
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre de la categoría
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    /**
     * Retorna la descripción de la categoría
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Cambia la descripción de la categoría
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
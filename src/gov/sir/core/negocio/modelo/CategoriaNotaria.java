package gov.sir.core.negocio.modelo;


import org.auriga.core.modelo.TransferObject;


/**
 * Clase que modela las categorias en la que se pueden clasificar las Notar�as
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
     * Retorna el identificador de la categor�a
     * @return idCategoria  */
    public String getIdCategoria() {
        return idCategoria;
    }

    /**
     * Cambia el identificador de la categor�a
     * @param idCategoria
     */
    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }


    /**
     * Retorna el nombre de la categor�a
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre de la categor�a
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    /**
     * Retorna la descripci�n de la categor�a
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Cambia la descripci�n de la categor�a
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
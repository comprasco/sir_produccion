package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CategoriaNotaria;


public class CategoriaNotariaEnhanced extends Enhanced{

    private String idCategoria; // pk 
    private String nombre;
    private String descripcion;
        
    
    public CategoriaNotariaEnhanced() {
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    public String getDescripcion() {
        return descripcion;
    }

    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    


	public static CategoriaNotariaEnhanced enhance(CategoriaNotaria catNotaria) {
		return (CategoriaNotariaEnhanced) Enhanced.enhance(catNotaria);
	}


	

}
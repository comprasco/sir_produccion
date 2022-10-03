package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CategoriaPk;

/**
 * Application identity objectid-class.
 */
public class CategoriaEnhancedPk implements java.io.Serializable {

    public String idCategoria;

    public CategoriaEnhancedPk() {
    }

    public CategoriaEnhancedPk(String s) {
        int i, p = 0;
        idCategoria = s.substring(p);
    }
    

	public CategoriaEnhancedPk(CategoriaPk id){
		idCategoria = id.idCategoria;
	}
    
	public CategoriaPk getCategoriaID(){
		CategoriaPk rta = new CategoriaPk();
		rta.idCategoria = idCategoria;
		return rta;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaEnhancedPk)) return false;

        final CategoriaEnhancedPk id = (CategoriaEnhancedPk) o;

        if (this.idCategoria != null ? !idCategoria.equals(id.idCategoria) : id.idCategoria != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCategoria != null ? idCategoria.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCategoria);
        return buffer.toString();
    }
}
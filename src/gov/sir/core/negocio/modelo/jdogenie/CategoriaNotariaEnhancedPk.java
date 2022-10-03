package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.CategoriaNotariaPk;

/**
 * Application identity objectid-class.
 */
public class CategoriaNotariaEnhancedPk implements java.io.Serializable {

    public String idCategoria;

    public CategoriaNotariaEnhancedPk() {
    }

    public CategoriaNotariaEnhancedPk(String s) {
        int i, p = 0;
        idCategoria = s.substring(p);
    }
    

	public CategoriaNotariaEnhancedPk(CategoriaNotariaPk id){
		idCategoria = id.idCategoria;
	}
    
	public CategoriaNotariaPk getCategoriaID(){
		CategoriaNotariaPk rta = new CategoriaNotariaPk();
		rta.idCategoria = idCategoria;
		return rta;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaNotariaEnhancedPk)) return false;

        final CategoriaNotariaEnhancedPk id = (CategoriaNotariaEnhancedPk) o;

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
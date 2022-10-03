package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoRecursoPk;

/**
 * Application identity objectid-class.
 */
public class TipoRecursoEnhancedPk implements java.io.Serializable {

    public String idTipoRecurso;

    public TipoRecursoEnhancedPk() {
    }
    

	public TipoRecursoEnhancedPk (TipoRecursoPk id){
		idTipoRecurso = id.idTipoRecurso;
	}

    
	public TipoRecursoPk getTipoRecursoD()
	{
		TipoRecursoPk id = new TipoRecursoPk ();
		id.idTipoRecurso = idTipoRecurso;
		return id;
	}

    public TipoRecursoEnhancedPk(String s) {
        int i, p = 0;
        idTipoRecurso = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoRecursoEnhancedPk)) return false;

        final TipoRecursoEnhancedPk id = (TipoRecursoEnhancedPk) o;

        if (this.idTipoRecurso != null ? !idTipoRecurso.equals(id.idTipoRecurso) : id.idTipoRecurso != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoRecurso != null ? idTipoRecurso.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoRecurso);
        return buffer.toString();
    }
}
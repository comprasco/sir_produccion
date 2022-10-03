package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoFotocopiaPk;

/**
 * Application identity objectid-class.
 */
public class TipoFotocopiaEnhancedPk implements java.io.Serializable {

    public String idTipoFotocopia;

    public TipoFotocopiaEnhancedPk() {
    }

	public TipoFotocopiaEnhancedPk (TipoFotocopiaPk id)
	{
		idTipoFotocopia = id.idTipoFotocopia;
	}

	public TipoFotocopiaPk getTipoFotocopiaID()
	{
		TipoFotocopiaPk id = new TipoFotocopiaPk();
		id.idTipoFotocopia = idTipoFotocopia;
		return id;
	}

    public TipoFotocopiaEnhancedPk(String s) {
        int i, p = 0;
        idTipoFotocopia = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoFotocopiaEnhancedPk)) return false;

        final TipoFotocopiaEnhancedPk id = (TipoFotocopiaEnhancedPk) o;

        if (this.idTipoFotocopia != null ? !idTipoFotocopia.equals(id.idTipoFotocopia) : id.idTipoFotocopia != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoFotocopia != null ? idTipoFotocopia.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoFotocopia);
        return buffer.toString();
    }
}
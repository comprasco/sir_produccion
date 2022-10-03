package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TurnoFolioMigPk;

/**
 * Application identity objectid-class.
 */
public class FolioValidoMigEnhancedPk implements java.io.Serializable {

    public String idFolio;
    public String idCirculo;

    public FolioValidoMigEnhancedPk() {
    }

	public FolioValidoMigEnhancedPk(TurnoFolioMigPk id){
		idFolio = id.idFolio;
		idCirculo = id.idCirculo;
	}
	
	public TurnoFolioMigPk getTurnoFolioMigID(){
		TurnoFolioMigPk id = new TurnoFolioMigPk();
		id.idFolio = idFolio;
		id.idCirculo = idCirculo;
		return id;
	}

    public FolioValidoMigEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idFolio = s.substring(p, i);
        p = i + 1;
        idCirculo = s.substring(p, i);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FolioValidoMigEnhancedPk)) return false;

        final FolioValidoMigEnhancedPk id = (FolioValidoMigEnhancedPk) o;

        if (this.idFolio != null ? !idFolio.equals(id.idFolio) : id.idFolio != null) return false;
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idFolio != null ? idFolio.hashCode() : 0);
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idFolio);
        buffer.append('-');
        buffer.append(idCirculo);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.HistorialAreasPk;


/**
** @Author Cristian David Garcia
** @Requerimiento Catastro Multiproposito
**  Application identity objectid-class.
*/
public class HistorialAreasEnhancedPk implements java.io.Serializable{
    
    public String idHistorial;

    public HistorialAreasEnhancedPk() {
    }

    public HistorialAreasEnhancedPk(HistorialAreasPk id) {
        idHistorial = id.idHistorial;
    }

    public HistorialAreasEnhancedPk(String s) {
    	idHistorial = s;
    }

    public HistorialAreasPk getHistorialAreasID() {
        HistorialAreasPk id = new HistorialAreasPk();
        id.idHistorial = idHistorial;
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof HistorialAreasEnhancedPk)) {
            return false;
        }

        final HistorialAreasEnhancedPk id = (HistorialAreasEnhancedPk) o;

        if ((this.idHistorial != null)
                ? (!idHistorial.equals(id.idHistorial))
                    : (id.idHistorial != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idHistorial != null) ? idHistorial.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idHistorial);
        return buffer.toString();
    }
    
}

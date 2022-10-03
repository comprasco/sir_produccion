package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.NotificacionNotaPk;


/**
** @Author Cristian David Garcia
** @Requerimiento Catastro Multiproposito
**  Application identity objectid-class.
*/
public class NotificacionNotaEnhancedPk implements java.io.Serializable{
    
    public String idNotificacion;

    public NotificacionNotaEnhancedPk() {
    }

    public NotificacionNotaEnhancedPk(NotificacionNotaPk id) {
        idNotificacion = id.idNotificacion;
    }

    public NotificacionNotaEnhancedPk(String s) {
    	idNotificacion = s;
    }

    public NotificacionNotaPk getNotificacionNotaID() {
        NotificacionNotaPk id = new NotificacionNotaPk();
        id.idNotificacion = idNotificacion;
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof NotificacionNotaEnhancedPk)) {
            return false;
        }

        final NotificacionNotaEnhancedPk id = (NotificacionNotaEnhancedPk) o;

        if ((this.idNotificacion != null)
                ? (!idNotificacion.equals(id.idNotificacion))
                    : (id.idNotificacion != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idNotificacion != null) ? idNotificacion.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idNotificacion);
        return buffer.toString();
    }
    
}

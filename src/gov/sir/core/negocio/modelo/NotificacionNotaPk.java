package gov.sir.core.negocio.modelo;


/**
** @Author Cristian David Garcia
** @Requerimiento Catastro Multiproposito
** Clase que define los atributos que identifican al historial de areas
*/
public class NotificacionNotaPk implements java.io.Serializable {
    public String idNotificacion;
    
    public NotificacionNotaPk(){
    }
    
    public NotificacionNotaPk(String s){
        idNotificacion = s;
    }
    
    public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof NotificacionNotaPk)) {
			return false;
		}

		final NotificacionNotaPk id = (NotificacionNotaPk) o;

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

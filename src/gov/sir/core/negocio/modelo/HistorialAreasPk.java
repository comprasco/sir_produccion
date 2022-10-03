package gov.sir.core.negocio.modelo;


/**
** @Author Cristian David Garcia
** @Requerimiento Catastro Multiproposito
** Clase que define los atributos que identifican al historial de areas
*/
public class HistorialAreasPk implements java.io.Serializable {
    public String idHistorial;
    
    public HistorialAreasPk(){
    }
    
    public HistorialAreasPk(String s){
        idHistorial = s;
    }
    
    public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof HistorialAreasPk)) {
			return false;
		}

		final HistorialAreasPk id = (HistorialAreasPk) o;

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

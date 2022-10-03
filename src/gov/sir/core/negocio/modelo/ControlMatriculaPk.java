package gov.sir.core.negocio.modelo;


/**
** @Author Cristian David Garcia
** @Requerimiento Catastro Multiproposito
** Clase que define los atributos que identifican al historial de areas
*/
public class ControlMatriculaPk implements java.io.Serializable {
    public String idMatricula;
    
    public ControlMatriculaPk(){
    }
    
    public ControlMatriculaPk(String s){
        idMatricula = s;
    }
    
    public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof ControlMatriculaPk)) {
			return false;
		}

		final ControlMatriculaPk id = (ControlMatriculaPk) o;

		if ((this.idMatricula != null)
				? (!idMatricula.equals(id.idMatricula))
					: (id.idMatricula != null)) {
			return false;
		}
		return true;
    }
    
    public int hashCode() {
		int result = 0;
		result = (29 * result) +
			((idMatricula != null) ? idMatricula.hashCode() : 0);
		return result;
    }

    public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idMatricula);
            return buffer.toString();
    }
    
}

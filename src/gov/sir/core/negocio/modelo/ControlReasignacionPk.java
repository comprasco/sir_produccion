package gov.sir.core.negocio.modelo;


/**
** @Author Cristian David Garcia
** @Requerimiento Catastro Multiproposito
** Clase que define los atributos que identifican al historial de areas
*/
public class ControlReasignacionPk implements java.io.Serializable {
    public String idWorkflow;
    
    public ControlReasignacionPk(){
    }
    
    public ControlReasignacionPk(String s){
        idWorkflow = s;
    }
    
    public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof ControlReasignacionPk)) {
			return false;
		}

		final ControlReasignacionPk id = (ControlReasignacionPk) o;

		if ((this.idWorkflow != null)
				? (!idWorkflow.equals(id.idWorkflow))
					: (id.idWorkflow != null)) {
			return false;
		}
		return true;
    }
    
    public int hashCode() {
		int result = 0;
		result = (29 * result) +
			((idWorkflow != null) ? idWorkflow.hashCode() : 0);
		return result;
    }

    public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idWorkflow);
            return buffer.toString();
    }
    
}

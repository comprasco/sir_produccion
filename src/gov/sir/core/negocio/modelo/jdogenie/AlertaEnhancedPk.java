package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.AlertaPk;

/**
 * Application identity objectid-class.
 */
public class AlertaEnhancedPk implements java.io.Serializable {

   public String idEstacion;
   public String idFase;
   public long idProceso;
   public String idWorkflow; // pk 

   public AlertaEnhancedPk() {
   }

   public AlertaEnhancedPk(String s) {
	   int i, p = 0;
	   i= s.indexOf('-', p);
	   idEstacion = s.substring(p, i);
	   p = i + 1;
	   i= s.indexOf('-', p);
	   idFase = s.substring(p, i);
	   p = i + 1;
	   i= s.indexOf('-', p);
	   idProceso = Long.parseLong(s.substring(p, i));
	   p = i + 1;
	   idWorkflow = s.substring(p);
   }

   public boolean equals(Object o) {
	   if (this == o) return true;
	   if (!(o instanceof AlertaPk)) return false;

	   final AlertaPk id = (AlertaPk) o;

	   if (this.idEstacion != null ? !idEstacion.equals(id.idEstacion) : id.idEstacion != null) return false;
	   if (this.idFase != null ? !idFase.equals(id.idFase) : id.idFase != null) return false;
	   if (this.idProceso != id.idProceso) return false;
	   if (this.idWorkflow != null ? !idWorkflow.equals(id.idWorkflow) : id.idWorkflow != null) return false;
	   return true;
   }

   public int hashCode() {
	   int result = 0;
	   result = 29 * result + (idEstacion != null ? idEstacion.hashCode() : 0);
	   result = 29 * result + (idFase != null ? idFase.hashCode() : 0);
	   result = 29 * result + (int) (idProceso ^ (idProceso >>> 32));
	   result = 29 * result + (idWorkflow != null ? idWorkflow.hashCode() : 0);
	   return result;
   }

   public String toString() {
	   StringBuffer buffer = new StringBuffer();
	   buffer.append(idEstacion);
	   buffer.append('-');
	   buffer.append(idFase);
	   buffer.append('-');
	   buffer.append(idProceso);
	   buffer.append('-');
		buffer.append(idWorkflow);
	   return buffer.toString();
   }
    
	public AlertaEnhancedPk(AlertaPk id){
		idEstacion = id.idEstacion;
		idFase = id.idFase;
		idProceso = id.idProceso;
		idWorkflow = id.idWorkflow;
	}
	
	public AlertaPk getAlertaID(){
		AlertaPk id = new AlertaPk();
		id.idEstacion= idEstacion;
		id.idFase = idFase;
		id.idProceso = idProceso;
		id.idWorkflow = idWorkflow;
		return id;
	}

}
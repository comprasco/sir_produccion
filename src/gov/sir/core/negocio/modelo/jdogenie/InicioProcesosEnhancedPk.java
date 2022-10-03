package gov.sir.core.negocio.modelo.jdogenie;


public class InicioProcesosEnhancedPk implements java.io.Serializable {
	public int idProceso;
	public String idFase;
	public String idRol;
	
	public InicioProcesosEnhancedPk(){
		
	}
	
	public InicioProcesosEnhancedPk(String s){
		int i, p = 0;
        i= s.indexOf('-', p);
        idProceso = Integer.parseInt(s.substring(p, i));
        p = i + 1;
        i= s.indexOf('-', p);
        idFase = s.substring(p, i);
        p = i + 1;
        idRol = s.substring(p);
	}
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InicioProcesosEnhancedPk)) return false;

        final InicioProcesosEnhancedPk id = (InicioProcesosEnhancedPk) o;

        if (this.idProceso != id.idProceso) return false;
        if (this.idFase != null ? !idFase.equals(id.idFase) : id.idFase != null) return false;
        if (this.idRol != null ? !idRol.equals(id.idRol) : id.idRol != null) return false;
        return true;
    }
	
	public int hashCode() {
        int result = 0;
        result = (29 * result) + (idProceso ^ (idProceso >>> 32));
        result = (29 * result) +
            ((idFase != null) ? idFase.hashCode() : 0);
        result = (29 * result) +
        	((idRol != null) ? idRol.hashCode() : 0);

        return result;
    }
	
	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idFase);
        buffer.append('-');
        buffer.append(idRol);

        return buffer.toString();
    }
}

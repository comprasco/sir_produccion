package gov.sir.core.negocio.modelo;

public class WfProcesosPk {
	
	private int idProceso;
	private String idFaseInicial;
	private String respuesta;
	private String idFaseFinal;
	private String idRol;
	
	public WfProcesosPk(){
		
	}
	
	public WfProcesosPk(String s){
		int i, p = 0;
        i= s.indexOf('-', p);
        idProceso = Integer.parseInt(s.substring(p, i));
        p = i + 1;
        i= s.indexOf('-', p);
        idFaseInicial = s.substring(p, i);
        p = i + 1;
        respuesta = s.substring(p, i);
        p = i + 1;
        idFaseFinal = s.substring(p, i);
        p = i + 1;
        idRol = s.substring(p);
	}
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WfProcesosPk)) return false;

        final WfProcesosPk id = (WfProcesosPk) o;

        if (this.idProceso != id.idProceso) return false;
        if (this.idFaseInicial != null ? !idFaseInicial.equals(id.idFaseInicial) : id.idFaseInicial != null) return false;
        if (this.respuesta != null ? !respuesta.equals(id.respuesta) : id.respuesta != null) return false;
        if (this.idFaseFinal != null ? !idFaseFinal.equals(id.idFaseFinal) : id.idFaseFinal != null) return false;
        if (this.idRol != null ? !idRol.equals(id.idRol) : id.idRol != null) return false;
        return true;
    }
	
	public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) (idProceso ^ (idProceso >>> 32));
        result = (29 * result) +
            ((idFaseInicial != null) ? idFaseInicial.hashCode() : 0);
        result = (29 * result) +
        	((idFaseFinal != null) ? idFaseFinal.hashCode() : 0);
        result = (29 * result) +
        	((idRol != null) ? idRol.hashCode() : 0);

        return result;
    }
	
	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idProceso);
        buffer.append('-');
        buffer.append(idFaseInicial);
        buffer.append('-');
        buffer.append(respuesta);
        buffer.append('-');
        buffer.append(idFaseFinal);
        buffer.append('-');
        buffer.append(idRol);

        return buffer.toString();
    }
}

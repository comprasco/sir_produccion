package gov.sir.core.negocio.modelo;

public class InicioProcesosPk {
	private int idProceso;
	private String idFase;
	private String idRol;
	
	public InicioProcesosPk(){
		
	}
	
	public InicioProcesosPk(String s){
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
        if (!(o instanceof InicioProcesosPk)) return false;

        final InicioProcesosPk id = (InicioProcesosPk) o;

        if (this.idProceso != id.idProceso) return false;
        if (this.idFase != null ? !idFase.equals(id.idFase) : id.idFase != null) return false;
        if (this.idRol != null ? !idRol.equals(id.idRol) : id.idRol != null) return false;
        return true;
    }
	
	public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) (idProceso ^ (idProceso >>> 32));
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

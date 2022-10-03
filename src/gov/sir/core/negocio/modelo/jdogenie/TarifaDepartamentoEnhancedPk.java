package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TarifaDepartamentoPk;

/**
 * Application identity objectid-class.
 */
public class TarifaDepartamentoEnhancedPk implements java.io.Serializable {

    public String idDepartamento;
    public String idTipoActo;

    public TarifaDepartamentoEnhancedPk() {
    }
    
	public TarifaDepartamentoEnhancedPk(TarifaDepartamentoPk id){
		idDepartamento = id.idDepartamento;
		idTipoActo     = id.idTipoActo;        	
	}
    
	public TarifaDepartamentoPk getTarifaDepartamentoID(){
		TarifaDepartamentoPk id = new TarifaDepartamentoPk();
		id.idDepartamento = idDepartamento;
		id.idTipoActo     = idTipoActo;
		return id;
	}        

    public TarifaDepartamentoEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idDepartamento = s.substring(p, i);
        p = i + 1;
        idTipoActo = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TarifaDepartamentoEnhancedPk)) return false;

        final TarifaDepartamentoEnhancedPk id = (TarifaDepartamentoEnhancedPk) o;

        if (this.idDepartamento != null ? !idDepartamento.equals(id.idDepartamento) : id.idDepartamento != null) return false;
        if (this.idTipoActo != null ? !idTipoActo.equals(id.idTipoActo) : id.idTipoActo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idDepartamento != null ? idDepartamento.hashCode() : 0);
        result = 29 * result + (idTipoActo != null ? idTipoActo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idDepartamento);
        buffer.append('-');
        buffer.append(idTipoActo);
        return buffer.toString();
    }
}
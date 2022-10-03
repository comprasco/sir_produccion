package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TestamentoPk;

/**
 * Application identity objectid-class.
 */
public class TestamentoEnhancedPk implements java.io.Serializable {

    public String idTestamento;

    public TestamentoEnhancedPk() {
    }

    public TestamentoEnhancedPk(String s) {
        int i, p = 0;
        idTestamento = s.substring(p);
    }
    

	public TestamentoEnhancedPk(TestamentoPk id){
		idTestamento = id.idTestamento;    	
	}
    
	public TestamentoPk getTestamentoID(){
		TestamentoPk id = new TestamentoPk();
		id.idTestamento = idTestamento;
		return id;
	}    

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestamentoEnhancedPk)) return false;

        final TestamentoEnhancedPk id = (TestamentoEnhancedPk) o;

        if (this.idTestamento != null ? !idTestamento.equals(id.idTestamento) : id.idTestamento != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTestamento != null ? idTestamento.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTestamento);
        return buffer.toString();
    }
}
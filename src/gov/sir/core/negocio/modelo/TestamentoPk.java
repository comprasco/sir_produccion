package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Testamento */
public class TestamentoPk implements java.io.Serializable {

    public String idTestamento;

    public TestamentoPk() {
    }

    public TestamentoPk(String s) {
        int i, p = 0;
        idTestamento = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestamentoPk)) return false;

        final TestamentoPk id = (TestamentoPk) o;

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
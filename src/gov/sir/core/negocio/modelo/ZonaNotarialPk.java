package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a ZonaNotarial */

public class ZonaNotarialPk implements java.io.Serializable {

    public String idCirculoNotarial;
    public String idDepartamento;
    public String idMunicipio;
    public String idVereda;

    public ZonaNotarialPk() {
    }

    public ZonaNotarialPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCirculoNotarial = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idDepartamento = s.substring(p, i);
        p = i + 1;
        i= s.indexOf('-', p);
        idMunicipio = s.substring(p, i);
        p = i + 1;
        idVereda = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZonaNotarialPk)) return false;

        final ZonaNotarialPk id = (ZonaNotarialPk) o;

        if (this.idCirculoNotarial != null ? !idCirculoNotarial.equals(id.idCirculoNotarial) : id.idCirculoNotarial != null) return false;
        if (this.idDepartamento != null ? !idDepartamento.equals(id.idDepartamento) : id.idDepartamento != null) return false;
        if (this.idMunicipio != null ? !idMunicipio.equals(id.idMunicipio) : id.idMunicipio != null) return false;
        if (this.idVereda != null ? !idVereda.equals(id.idVereda) : id.idVereda != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCirculoNotarial != null ? idCirculoNotarial.hashCode() : 0);
        result = 29 * result + (idDepartamento != null ? idDepartamento.hashCode() : 0);
        result = 29 * result + (idMunicipio != null ? idMunicipio.hashCode() : 0);
        result = 29 * result + (idVereda != null ? idVereda.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idCirculoNotarial);
        buffer.append('-');
        buffer.append(idDepartamento);
        buffer.append('-');
        buffer.append(idMunicipio);
        buffer.append('-');
        buffer.append(idVereda);
        return buffer.toString();
    }
}
package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a Vereda */

public class VeredaPk implements java.io.Serializable {
    public String idVereda;
	public String idMunicipio;
	public String idDepartamento;

    public VeredaPk() {
    }

    public VeredaPk(String s) {
		int i;
		int p = 0;
		i = s.indexOf('-', p);
		idVereda = s.substring(p, i);
		p = i + 1;
		i = s.indexOf('-', p);
		idMunicipio = s.substring(p, i);
		p = i + 1;
		idDepartamento = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof VeredaPk)) {
            return false;
        }

        final VeredaPk id = (VeredaPk) o;

        if ((this.idVereda != null) ? (!idVereda.equals(id.idVereda))
                                        : (id.idVereda != null)) {
            return false;
        }

		if ((this.idMunicipio != null) ? (!idMunicipio.equals(id.idMunicipio))
												   : (id.idMunicipio != null)) {
			 return false;
		}
		

		if ((this.idDepartamento != null) ? (!idDepartamento.equals(id.idDepartamento))
												   : (id.idDepartamento != null)) {
			 return false;
		}
		
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idVereda != null) ? idVereda.hashCode() : 0);
		result = (29 * result) +
			((idMunicipio != null) ? idMunicipio.hashCode() : 0);
		result = (29 * result) +
			 ((idDepartamento != null) ? idDepartamento.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idVereda);
		buffer.append('-');
		buffer.append(idMunicipio);
		buffer.append('-');
		buffer.append(idDepartamento);
        return buffer.toString();
    }
}
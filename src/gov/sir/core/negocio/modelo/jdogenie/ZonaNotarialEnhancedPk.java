package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ZonaNotarialPk;

/**
 * Application identity objectid-class.
 */
public class ZonaNotarialEnhancedPk implements java.io.Serializable {
    public String idCirculoNotarial;
    public String idDepartamento;
    public String idMunicipio;
    public String idVereda;

    public ZonaNotarialEnhancedPk() {
    }

    public ZonaNotarialEnhancedPk(ZonaNotarialPk id) {
        idCirculoNotarial = id.idCirculoNotarial;
        idDepartamento = id.idDepartamento;
        idMunicipio = id.idMunicipio;
        idVereda = id.idVereda;
    }

    public ZonaNotarialEnhancedPk(String s) {
        int i;
        int p = 0;
        i = s.indexOf('-', p);
        idCirculoNotarial = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idDepartamento = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-', p);
        idMunicipio = s.substring(p, i);
        p = i + 1;
        idVereda = s.substring(p);
    }

    public ZonaNotarialPk getZonaRegistralID() {
        ZonaNotarialPk id = new ZonaNotarialPk();
		id.idCirculoNotarial = idCirculoNotarial;
		id.idDepartamento = idDepartamento;
		id.idMunicipio = idMunicipio;
		id.idVereda = idVereda;
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ZonaNotarialEnhancedPk)) {
            return false;
        }

        final ZonaNotarialEnhancedPk id = (ZonaNotarialEnhancedPk) o;

        if ((this.idCirculoNotarial != null)
                ? (!idCirculoNotarial.equals(id.idCirculoNotarial))
                    : (id.idCirculoNotarial != null)) {
            return false;
        }

        if ((this.idDepartamento != null)
                ? (!idDepartamento.equals(id.idDepartamento))
                    : (id.idDepartamento != null)) {
            return false;
        }

        if ((this.idMunicipio != null)
                ? (!idMunicipio.equals(id.idMunicipio))
                    : (id.idMunicipio != null)) {
            return false;
        }

        if ((this.idVereda != null) ? (!idVereda.equals(id.idVereda))
                                        : (id.idVereda != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idCirculoNotarial != null) ? idCirculoNotarial.hashCode() : 0);
        result = (29 * result) +
            ((idDepartamento != null) ? idDepartamento.hashCode() : 0);
        result = (29 * result) +
            ((idMunicipio != null) ? idMunicipio.hashCode() : 0);
        result = (29 * result) +
            ((idVereda != null) ? idVereda.hashCode() : 0);

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
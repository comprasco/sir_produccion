package gov.sir.core.negocio.modelo;

import java.util.Date;

/**
 * Clase que define los atributos que identifican a CirculoFestivo
 */
public class CirculoFestivoPk implements java.io.Serializable {

    public Date fechaFestivo;
    public String idCirculo;

    public CirculoFestivoPk() {
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CirculoFestivoPk)) return false;

        final CirculoFestivoPk id = (CirculoFestivoPk) o;

        if (this.fechaFestivo != null ? !fechaFestivo.equals(id.fechaFestivo) : id.fechaFestivo != null) return false;
        if (this.idCirculo != null ? !idCirculo.equals(id.idCirculo) : id.idCirculo != null) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (fechaFestivo != null ? fechaFestivo.hashCode() : 0);
        result = 29 * result + (idCirculo != null ? idCirculo.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(fechaFestivo);
        buffer.append('-');
        buffer.append(idCirculo);
        return buffer.toString();
    }
}
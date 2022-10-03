package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a OficinaOrigen  */

public class OficinaOrigenPk implements java.io.Serializable {
    public String idOficinaOrigen;
     /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis
        */
    public long version;
    public OficinaOrigenPk() {
    }

    public OficinaOrigenPk(String s) {
        int i;
        int p = 0;
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
        i= s.indexOf("-",p);
        idOficinaOrigen = s.substring(p,i);
        i++;
        version = Long.parseLong(s.substring(i));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof OficinaOrigenPk)) {
            return false;
        }

        final OficinaOrigenPk id = (OficinaOrigenPk) o;

        if ((this.idOficinaOrigen != null)
                ? (!idOficinaOrigen.equals(id.idOficinaOrigen))
                    : (id.idOficinaOrigen != null)) {
            return false;
        }
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
        if (this.version !=  id.version ) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idOficinaOrigen != null) ? idOficinaOrigen.hashCode() : 0);
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
        result = (29 * result) + (int)(version ^ (version >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idOficinaOrigen);
          /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
        buffer.append("-");
        buffer.append(version);
        return buffer.toString();
    }
}

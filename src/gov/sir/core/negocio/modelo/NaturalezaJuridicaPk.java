package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a NaturalezaJuridica  */
public class NaturalezaJuridicaPk implements java.io.Serializable {

    public String idNaturalezaJuridica;
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiaci�n_de_Naturaleza_Jur�dica
        */
    public long version;

    public NaturalezaJuridicaPk() {
    }

    public NaturalezaJuridicaPk(String s) {
        int i, p = 0;
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiaci�n_de_Naturaleza_Jur�dica
        */
        i= s.indexOf("-",p);
        idNaturalezaJuridica = s.substring(p,i);
        i++;
        version = Long.parseLong(s.substring(i)); 
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NaturalezaJuridicaPk)) return false;

        final NaturalezaJuridicaPk id = (NaturalezaJuridicaPk) o;

        if (this.idNaturalezaJuridica != null ? !idNaturalezaJuridica.equals(id.idNaturalezaJuridica) : id.idNaturalezaJuridica != null) return false;
          /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiaci�n_de_Naturaleza_Jur�dica
        */
        if (this.version !=  id.version ) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idNaturalezaJuridica != null ? idNaturalezaJuridica.hashCode() : 0);
       /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiaci�n_de_Naturaleza_Jur�dica
        */
        result = (29 * result) + (int)(version ^ (version >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idNaturalezaJuridica);
         /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiaci�n_de_Naturaleza_Jur�dica
        */
        buffer.append("-");
        buffer.append(version);
        return buffer.toString();
    }
}

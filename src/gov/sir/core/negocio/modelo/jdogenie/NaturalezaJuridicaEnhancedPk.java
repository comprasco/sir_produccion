package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.NaturalezaJuridicaPk;

/**
 * Application identity objectid-class.
 */
public class NaturalezaJuridicaEnhancedPk implements java.io.Serializable {

    public String idNaturalezaJuridica;
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
    public long version;

    public NaturalezaJuridicaEnhancedPk() {
    }

    public NaturalezaJuridicaEnhancedPk(String s) {
        int i, p = 0;
        /*
        *  @author Carlos Torres
        *  @chage   se agrega instrucciones para tener en cuenta el nuevo campo version
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
        i= s.indexOf("-",p);
        idNaturalezaJuridica = s.substring(p, i);
        i++;
        version = Long.parseLong(s.substring(i));
    }
    
    public NaturalezaJuridicaEnhancedPk(NaturalezaJuridicaPk id){
        idNaturalezaJuridica = id.idNaturalezaJuridica;
        /*
        *  @author Carlos Torres
        *  @chage   se agrega instrucciones para tener en cuenta el nuevo campo version
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
        version = id.version;
    }
    
    public NaturalezaJuridicaPk getNaturalezaJuridicaID(){
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
        NaturalezaJuridicaPk pk =new NaturalezaJuridicaPk(idNaturalezaJuridica+"-"+version);
        return pk;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NaturalezaJuridicaEnhancedPk)) return false;

        final NaturalezaJuridicaEnhancedPk id = (NaturalezaJuridicaEnhancedPk) o;

        if (this.idNaturalezaJuridica != null ? !idNaturalezaJuridica.equals(id.idNaturalezaJuridica) : id.idNaturalezaJuridica != null) return false;
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
        result = 29 * result + (idNaturalezaJuridica != null ? idNaturalezaJuridica.hashCode() : 0);
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
        buffer.append(idNaturalezaJuridica);
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

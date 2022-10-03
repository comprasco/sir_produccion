package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoNotaPk;

/**
 * Application identity objectid-class.
 */
public class TipoNotaEnhancedPk implements java.io.Serializable {

    public String idTipoNota;
    /**
     * * @author : HGOMEZ, FPADILLA ** @change : Se agregan los campos versio y activo.
     * version de cada una de ellas. ** Caso Mantis : 12621
     */
    public long version;
    public int activo;

    public TipoNotaEnhancedPk() {
    }
    
    public TipoNotaEnhancedPk (TipoNotaPk id)
    {
        idTipoNota = id.idTipoNota;
         /*
        *  @fecha 30/10/2012
        *  @author Carlos Torres
        *  @chage   se establece el valor del atributo version
        *  @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
    */
        version = id.version;
    }
    /*
        *  @fecha 30/10/2012
        *  @author Carlos Torres
        *  @chage   se modifica el constructor para que resiva el dentificador en el formato idTipoNota-version
        *  @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
    */
    public TipoNotaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf("-",p);
        idTipoNota = s.substring(p, i);
        i++;
        version = Long.parseLong(s.substring(i)); 
    }
    

    public TipoNotaPk getTipoNotaID()
    {
		TipoNotaPk id = new TipoNotaPk();
		id.idTipoNota = idTipoNota;
       /*
        *  @fecha 30/10/2012
        *  @author Carlos Torres
        *  @chage   se establese el valor para el atributo version.
        *  @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
        */
                id.version = version;
		return id;
    }


    public boolean equals(Object o) {
         if (this == o) return true;
        if (!(o instanceof TipoNotaPk)) return false;

        final TipoNotaPk id = (TipoNotaPk) o;

        if (this.idTipoNota != null ? !idTipoNota.equals(id.idTipoNota) : id.idTipoNota != null) return false;
         /*
        *  @fecha 30/10/2012
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
    */
        if (this.version !=  id.version ) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idTipoNota != null ? idTipoNota.hashCode() : 0);
           /*
        *  @fecha 30/10/2012
        *  @author Carlos Torres
        *  @chage   se modifica el metodo para tener en ecuenta la version
        *  @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
    */
        result = (29 * result) + (int)(version ^ (version >>> 32));
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoNota);
       /*
        *  @fecha 30/10/2012
        *  @author Carlos Torres
        *  @chage   se modifica el metodo para tener en ecuenta la version
        *  @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
        */
        buffer.append("-");
        buffer.append(version);
        return buffer.toString();
    }
}

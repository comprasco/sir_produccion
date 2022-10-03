package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.OficinaOrigenPk;

/**
 * Application identity objectid-class.
 */
public class OficinaOrigenEnhancedPk implements java.io.Serializable {
    public String idOficinaOrigen;
     /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis
        */
    public Long version;
    
        /*
        *  @author Fernando Padilla Velez
        *  @chage  1203.AJUSTE.VERSION.CATEGORIA.REPARTO.NOTARIAL,
        *          Se crea el campo ID_UNICO de la tabla SIR_NE_OFICINA_ORIGEN
        */
        private String idUnico;
    public OficinaOrigenEnhancedPk() {
    }

    public OficinaOrigenEnhancedPk(String s) {
        int i;
        int p = 0;
        /*
        *  @author Carlos Torres
        *  @chage   se agrega instrucciones para tener en cuenta el nuevo campo version
        *  @mantis
        */
        i= s.indexOf("-",p);
        idOficinaOrigen = s.substring(p, i);
        i++;
        version = Long.parseLong(s.substring(i));
    }

    public OficinaOrigenEnhancedPk(OficinaOrigenPk id) {
        idOficinaOrigen = id.idOficinaOrigen;
         /*
        *  @author Carlos Torres
        *  @chage   se agrega instrucciones para tener en cuenta el nuevo campo version
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
        version = id.version;
    }

    public OficinaOrigenPk getOficinaOrigenID() {
        return new OficinaOrigenPk(idOficinaOrigen+"-"+version);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof OficinaOrigenEnhancedPk)) {
            return false;
        }

        final OficinaOrigenEnhancedPk id = (OficinaOrigenEnhancedPk) o;

        if ((this.idOficinaOrigen != null)
                ? (!idOficinaOrigen.equals(id.idOficinaOrigen))
                    : (id.idOficinaOrigen != null)) {
            return false;
        }
        /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis
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
        *  @mantis
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
        *  @mantis
        */
        buffer.append("-");
        buffer.append(version);
        return buffer.toString();
    }
}

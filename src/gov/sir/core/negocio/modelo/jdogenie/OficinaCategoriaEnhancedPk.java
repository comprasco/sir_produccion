package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.OficinaCategoriaPk;

/**
 * Application identity objectid-class.
 */
public class OficinaCategoriaEnhancedPk implements java.io.Serializable {

    public String idCategoria;
    public String idOficinaOrigen;
     /*
     *  @author Carlos Torres
     *  @chage   se agrega validacion de version diferente
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     */
    public Long version;

    public OficinaCategoriaEnhancedPk() {
    }

    public OficinaCategoriaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCategoria = s.substring(p, i);
        p = i + 1;
        i = s.indexOf('-',p);
        idOficinaOrigen = s.substring(p,i);
         /*
          *  @author Carlos Torres
          *  @chage   se agrega validacion de version diferente
          *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
          */
        p = i + 1;
        i = s.indexOf('-',p);
        version = Long.parseLong(s.substring(p,i));
    }
    

	public OficinaCategoriaEnhancedPk(OficinaCategoriaPk id){
		idCategoria = id.idCategoria;
		idOficinaOrigen = id.idOficinaOrigen;
                version = id.version;
	}
    
	public OficinaCategoriaPk getCategoriaID(){
		OficinaCategoriaPk rta = new OficinaCategoriaPk();
		rta.idCategoria = idCategoria;
		rta.idOficinaOrigen = idOficinaOrigen;
                /*
                  *  @author Carlos Torres
                  *  @chage   se agrega validacion de version diferente
                  *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                  */
                rta.version = version;
		return rta;
	}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OficinaCategoriaEnhancedPk)) return false;

        final OficinaCategoriaEnhancedPk id = (OficinaCategoriaEnhancedPk) o;

        if (this.idCategoria != null ? !idCategoria.equals(id.idCategoria) : id.idCategoria != null) return false;
        if (this.idOficinaOrigen != null ? !idOficinaOrigen.equals(id.idOficinaOrigen) : id.idOficinaOrigen != null) return false;
         /*
          *  @author Carlos Torres
          *  @chage   se agrega validacion de version diferente
          *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
          */
        if (this.version !=  id.version ) return false;
        return true;
    }

    public int hashCode() {
        int result = 0;
        result = 29 * result + (idCategoria != null ? idCategoria.hashCode() : 0);
        result = 29 * result + (idOficinaOrigen != null ? idOficinaOrigen.hashCode() : 0);
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
        buffer.append(idCategoria);
        buffer.append('-');
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

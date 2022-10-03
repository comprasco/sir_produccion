package gov.sir.core.negocio.modelo;

/** Clase que define los atributos que identifican a OficioCategoria  */
public class OficinaCategoriaPk implements java.io.Serializable {

    public String idCategoria;
    public String idOficinaOrigen;
     /*
     *  @author Carlos Torres
     *  @chage   se agrega validacion de version diferente
     *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
     */
    public Long version;

    public OficinaCategoriaPk() {
    }

    public OficinaCategoriaPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idCategoria = s.substring(p, i);
        p = i + 1;
         /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OficinaCategoriaPk)) return false;

        final OficinaCategoriaPk id = (OficinaCategoriaPk) o;

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
          *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
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
          *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
          */
        buffer.append("-");
        buffer.append(version);
        return buffer.toString();
    }
}

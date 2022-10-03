/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo Banco Franquica del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo;

public class BancoFranquiciaPk implements java.io.Serializable {
    public String idBanco;         //pk
    public int idTipoFranquicia;   //pk

    public BancoFranquiciaPk() {
    }

    public BancoFranquiciaPk(String idBanco, int idTipoFranquicia) {
        this.idBanco = idBanco;
        this.idTipoFranquicia = idTipoFranquicia;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BancoFranquiciaPk other = (BancoFranquiciaPk) obj;
        if ((this.idBanco == null) ? (other.idBanco != null) : !this.idBanco.equals(other.idBanco)) {
            return false;
        }
        if (this.idTipoFranquicia != other.idTipoFranquicia) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.idBanco != null ? this.idBanco.hashCode() : 0);
        hash = 67 * hash + this.idTipoFranquicia;
        return hash;
    }

    @Override
    public String toString() {
        return "BancoFranquiciaPk{" + "idBanco=" + idBanco + ", idTipoFranquicia=" + idTipoFranquicia + '}';
    }
}

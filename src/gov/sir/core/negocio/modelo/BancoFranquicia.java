/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo Banco Franquica del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo;
import org.auriga.core.modelo.TransferObject;

public class BancoFranquicia implements TransferObject {

    private String idBanco;         //pk
    private int idTipoFranquicia;   //pk
private static final long serialVersionUID = 1L;
    public BancoFranquicia() {
    }

    public BancoFranquicia(String idBanco, int idTipoFranquicia) {
        this.idBanco = idBanco;
        this.idTipoFranquicia = idTipoFranquicia;
    }

    public String getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(String idBanco) {
        this.idBanco = idBanco;
    }

    public int getIdTipoFranquicia() {
        return idTipoFranquicia;
    }

    public void setIdTipoFranquicia(int idTipoFranquicia) {
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
        final BancoFranquicia other = (BancoFranquicia) obj;
        if ((this.idBanco == null) ? (other.idBanco != null) : !this.idBanco.equals(other.idBanco)) {
            return false;
        }
        if (this.idTipoFranquicia != other.idTipoFranquicia) {
            return false;
        }
        return true;
    }
}

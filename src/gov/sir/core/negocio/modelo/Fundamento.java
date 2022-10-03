/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;
import java.util.Date;
import org.auriga.core.modelo.TransferObject;
/**
 *
 * @author ctorres
 */
public class Fundamento implements TransferObject{
    Long idFundamento;
    TipoFundamento tipoFundamento;
    Long numeroFundamento;
    Date fechaCreacion;
    private static final long serialVersionUID = 1L;
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getNumeroFundamento() {
        return numeroFundamento;
    }

    public void setNumeroFundamento(Long numeroFundamento) {
        this.numeroFundamento = numeroFundamento;
    }

    public TipoFundamento getTipoFundamento() {
        return tipoFundamento;
    }

    public void setTipoFundamento(TipoFundamento tipoFundamento) {
        this.tipoFundamento = tipoFundamento;
    }

    public Long getIdFundamento() {
        return idFundamento;
    }

    public void setIdFundamento(Long idFundamento) {
        this.idFundamento = idFundamento;
    }
    
}

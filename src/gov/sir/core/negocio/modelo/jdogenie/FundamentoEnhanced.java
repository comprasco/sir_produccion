/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;
import java.util.Date;
import org.auriga.core.modelo.TransferObject;
/**
 *
 * @author ctorres
 */
public class FundamentoEnhanced extends  Enhanced{
    Long idFundamento;
    TipoFundamentoEnhanced tipoFundamento;
    Long numeroFundamento;
    Date fechaCreacion;

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

    public TipoFundamentoEnhanced getTipoFundamento() {
        return tipoFundamento;
    }

    public void setTipoFundamento(TipoFundamentoEnhanced tipoFundamento) {
        this.tipoFundamento = tipoFundamento;
    }

    public Long getIdFundamento() {
        return idFundamento;
    }

    public void setIdFundamento(Long idFundamento) {
        this.idFundamento = idFundamento;
    }
    
    
}

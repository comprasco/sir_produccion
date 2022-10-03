/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;
import org.auriga.core.modelo.TransferObject;
/**
 *
 * @author ctorres
 */
public class TrasladoFundamentoEnhanced extends Enhanced{
    Long tipoOrigen;
    Long numeroTraslado;
    Long idFundamento;
    FundamentoEnhanced fundamento;
    TrasladoDatosEnhanced trasladoDatos;

    public FundamentoEnhanced getFundamento() {
        return fundamento;
    }

    public void setFundamento(FundamentoEnhanced fundamento) {
        this.fundamento = fundamento;
    }

    public Long getNumeroTraslado() {
        return numeroTraslado;
    }

    public void setNumeroTraslado(Long numeroTraslado) {
        this.numeroTraslado = numeroTraslado;
    }

    public Long getTipoOrigen() {
        return tipoOrigen;
    }

    public void setTipoOrigen(Long tipoOrigen) {
        this.tipoOrigen = tipoOrigen;
    }

    public TrasladoDatosEnhanced getTrasladoDatos() {
        return trasladoDatos;
    }

    public void setTrasladoDatos(TrasladoDatosEnhanced trasladoDatos) {
        this.trasladoDatos = trasladoDatos;
    }

    public Long getIdFundamento() {
        return idFundamento;
    }

    public void setIdFundamento(Long idFundamento) {
        this.idFundamento = idFundamento;
    }
    
}

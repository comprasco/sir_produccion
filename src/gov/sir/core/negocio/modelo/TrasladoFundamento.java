/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;
import org.auriga.core.modelo.TransferObject;
/**
 *
 * @author ctorres
 */
public class TrasladoFundamento implements TransferObject{
    Long tipoOrigen;
    Long numeroTraslado;
    Long idFundamento;
    Fundamento fundamento;
    TrasladoDatos trasladoDatos;
    private static final long serialVersionUID = 1L;
    public Fundamento getFundamento() {
        return fundamento;
    }

    public void setFundamento(Fundamento fundamento) {
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

    public TrasladoDatos getTrasladoDatos() {
        return trasladoDatos;
    }

    public void setTrasladoDatos(TrasladoDatos trasladoDatos) {
        this.trasladoDatos = trasladoDatos;
    }

    public Long getIdFundamento() {
        return idFundamento;
    }

    public void setIdFundamento(Long idFundamento) {
        this.idFundamento = idFundamento;
    }
    
}

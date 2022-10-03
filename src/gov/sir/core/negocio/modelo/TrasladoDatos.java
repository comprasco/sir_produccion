/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.auriga.core.modelo.TransferObject;

/**
 *
 * @author ctorres
 */
public class TrasladoDatos implements TransferObject {
    long numeroTraslado;
    Date fechaCreacion;
    List traslados = new ArrayList();
    List fundamentosTraslados = new ArrayList();
    private static final long serialVersionUID = 1L;
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List getFundamentosTraslados() {
        return fundamentosTraslados;
    }

    public void setFundamentosTraslados(List fundamentosTraslados) {
        this.fundamentosTraslados = fundamentosTraslados;
    }

    public long getNumeroTraslado() {
        return numeroTraslado;
    }

    public void setNumeroTraslado(long numeroTraslado) {
        this.numeroTraslado = numeroTraslado;
    }

    public List getTraslados() {
        return traslados;
    }

    public void setTraslados(List traslados) {
        this.traslados = traslados;
    }
     public void addTraslado(Traslado traslado){
        this.traslados.add(traslado);
    }
    
    public void addTrasladoFundamento(TrasladoFundamento tf){
        this.fundamentosTraslados.add(tf);
    }
}

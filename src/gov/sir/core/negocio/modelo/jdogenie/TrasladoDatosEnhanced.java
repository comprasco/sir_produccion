/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import java.util.Date;
import java.util.List;


/**
 *
 * @author ctorres
 */
public class TrasladoDatosEnhanced extends Enhanced {
    Long numeroTraslado;
    Date fechaCreacion;
    List traslados;
    List trasladosFundamentos;

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List getTrasladosFundamentos() {
        return trasladosFundamentos;
    }

    public void setTrasladosFundamentos(List trasladosFundamentos) {
        this.trasladosFundamentos = trasladosFundamentos;
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
    public void addTraslado(TrasladoEnhanced traslado){
        this.traslados.add(traslado);
    }
    
    public void addTrasladoFundamento(TrasladoFundamentoEnhanced tf){
        this.trasladosFundamentos.add(tf);
    }
    
}

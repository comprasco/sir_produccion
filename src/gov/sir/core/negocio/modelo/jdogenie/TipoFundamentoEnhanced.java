/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;
import java.util.Date;


/**
 *
 * @author ctorres
 */
public class TipoFundamentoEnhanced extends Enhanced { 
    Long idTipoFundamento;
    String nombre;
    Date fechaCreacion;

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdTipoFundamento() {
        return idTipoFundamento;
    }

    public void setIdTipoFundamento(Long idTipoFundamento) {
        this.idTipoFundamento = idTipoFundamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

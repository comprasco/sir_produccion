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
public class TipoFundamento implements TransferObject {
    Long idTipoFundamento;
    String nombre;
    Date fechaCreacion;
    private static final long serialVersionUID = 1L;
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

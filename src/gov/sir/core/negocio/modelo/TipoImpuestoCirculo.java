package gov.sir.core.negocio.modelo;

import java.util.Date;
import org.auriga.core.modelo.TransferObject;

/**
 * @author Fernando Padilla Velez
 * @version 1.0, 28/12/2009
 * @change Clase creada para el caso MANTIS 135_141_Impuesto Meta.
 */
public class TipoImpuestoCirculo implements TransferObject {

    private Integer idTipoImpuestoCirculo;
    private String idTipoImpuesto;
    private String idCirculo;
    private Date fechaInicioVigencia;
    private Date fechaFinVigencia;
    private String soporte;
    private static final long serialVersionUID = 1L;
    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }


    public Date getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public String getIdTipoImpuesto() {
        return idTipoImpuesto;
    }

    public void setIdTipoImpuesto(String idTipoImpuesto) {
        this.idTipoImpuesto = idTipoImpuesto;
    }

    public Integer getIdTipoImpuestoCirculo() {
        return idTipoImpuestoCirculo;
    }

    public void setIdTipoImpuestoCirculo(Integer idTipoImpuestoCirculo) {
        this.idTipoImpuestoCirculo = idTipoImpuestoCirculo;
    }

    public String getSoporte() {
        return soporte;
    }

    public void setSoporte(String soporte) {
        this.soporte = soporte;
    }
}
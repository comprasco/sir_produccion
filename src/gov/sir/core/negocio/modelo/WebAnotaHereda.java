package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class WebAnotaHereda implements TransferObject{

    private String idAnotacion; // pk
    private String idMatricula; // pk
    private String idSolicitud; // pk
    private String idWebSegeng; // pk
    private Date fechaCreacion;
    private WebFolioHeredado folio; // inverse WebFolioHeredadoEnhanced.anotacionesHeredadas
    private static final long serialVersionUID = 1L;
    public WebAnotaHereda() {
    }

    public String getIdAnotacion() {
        return idAnotacion;
    }

    public void setIdAnotacion(String idAnotacion) {
        this.idAnotacion = idAnotacion;
    }

    public String getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(String idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIdWebSegeng() {
        return idWebSegeng;
    }

    public void setIdWebSegeng(String idWebSegeng) {
        this.idWebSegeng = idWebSegeng;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public WebFolioHeredado getFolio() {
        return folio;
    }

    public void setFolio(WebFolioHeredado folio) {
        this.folio = folio;
        setIdMatricula(folio.getIdMatricula());
        setIdSolicitud(folio.getIdSolicitud());
        setIdWebSegeng(folio.getIdWebSegeng());
    }
}

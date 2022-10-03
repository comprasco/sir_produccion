package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.WebFolioEnglobe;

import java.util.Date;

/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class WebFolioEnglobeEnhanced extends Enhanced{

    private String idMatricula; // pk
    private String idSolicitud; // pk
    private String idWebSegeng; // pk
    private Date fechaCreacion;
    private WebEnglobeEnhanced englobe; // inverse WebEnglobeEnhanced.foliosEnglobar

    public WebFolioEnglobeEnhanced() {
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

    public WebEnglobeEnhanced getEnglobe() {
        return englobe;
    }

    public void setEnglobe(WebEnglobeEnhanced englobe) {
        this.englobe = englobe;
        setIdSolicitud(englobe.getIdSolicitud());
        setIdWebSegeng(englobe.getIdWebSegeng());
    }
    

	public static WebFolioEnglobeEnhanced enhance(WebFolioEnglobe v){
		return (WebFolioEnglobeEnhanced)Enhanced.enhance(v);
	}
}
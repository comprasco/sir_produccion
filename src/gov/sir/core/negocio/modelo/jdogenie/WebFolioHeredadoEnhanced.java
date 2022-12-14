package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.WebFolioHeredado;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class WebFolioHeredadoEnhanced extends Enhanced{

    private String idMatricula; // pk
    private String idSolicitud; // pk
    private String idWebSegeng; // pk
    private Date fechaCreacion;
    private WebSegEngEnhanced webSegEng; // inverse WebSegEngEnhanced.foliosHeredados
    private List anotacionesHeredadas = new ArrayList(); // contains WebAnotaHeredaEnhanced  inverse WebAnotaHeredaEnhanced.folio
    private boolean copiaComentario;

    public boolean isCopiaComentario() {
		return copiaComentario;
	}

	public void setCopiaComentario(boolean copiaComentario) {
		this.copiaComentario = copiaComentario;
	}

	public WebFolioHeredadoEnhanced() {
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

    public WebSegEngEnhanced getWebSegEng() {
        return webSegEng;
    }

    public void setWebSegEng(WebSegEngEnhanced webSegEng) {
        this.webSegEng = webSegEng;
        setIdSolicitud(webSegEng.getIdSolicitud());
        setIdWebSegeng(webSegEng.getIdWebSegeng());
    }

    public List getAnotacionesHeredadas() {
        return Collections.unmodifiableList(anotacionesHeredadas);
    }

    public boolean addAnotacionesHeredada(WebAnotaHeredaEnhanced newAnotacionesHeredada) {
        return anotacionesHeredadas.add(newAnotacionesHeredada);
    }

    public boolean removeAnotacionesHeredada(WebAnotaHeredaEnhanced oldAnotacionesHeredada) {
        return anotacionesHeredadas.remove(oldAnotacionesHeredada);
    }
    
    
	public static WebFolioHeredadoEnhanced enhance(WebFolioHeredado v){
		return (WebFolioHeredadoEnhanced)Enhanced.enhance(v);
	}
}

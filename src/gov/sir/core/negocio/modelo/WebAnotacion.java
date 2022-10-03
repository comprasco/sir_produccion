package gov.sir.core.negocio.modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.auriga.core.modelo.TransferObject;

/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class WebAnotacion implements TransferObject{

    private String idSolicitud; // pk
    private String idWebSegeng; // pk
    private String comentario;
    private long copiaComentario;
    private String idDocumento;
    private String idNaturalezaJuridica;
     /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaci�n_de_Naturaleza_Jur�dica
         * @change     : se agrega nuevo campo version. 
	 */
    private long version;
    private String valor;
    private String modalidad;
    private static final long serialVersionUID = 1L;
    private WebSegEng webSegEng; // inverse WebSegEngEnhanced.anotaciones
    private List ciudadanos = new ArrayList(); // contains WebCiudadanoEnhanced  inverse WebCiudadanoEnhanced.anotacion
    private List documentos = new ArrayList(); // contains WebDocumentoEnhanced  inverse WebDocumentoEnhanced.anotacion
    private String numeroRadicacion;
    private Date fechaRadicacion;
    private String idAnotacionDefinitiva;

    public Date getFechaRadicacion() {
		return fechaRadicacion;
	}

	public void setFechaRadicacion(Date fechaRadicacion) {
		this.fechaRadicacion = fechaRadicacion;
	}

	public String getNumeroRadicacion() {
		return numeroRadicacion;
	}

	public void setNumeroRadicacion(String numeroRadicacion) {
		this.numeroRadicacion = numeroRadicacion;
	}

	public WebAnotacion() {
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public long getCopiaComentario() {
        return copiaComentario;
    }

    public void setCopiaComentario(long copiaComentario) {
        this.copiaComentario = copiaComentario;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getIdNaturalezaJuridica() {
        return idNaturalezaJuridica;
    }

    public void setIdNaturalezaJuridica(String idNaturalezaJuridica) {
        this.idNaturalezaJuridica = idNaturalezaJuridica;
    }

    public String getValor() {
        return valor;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
    
    public void setValor(String valor) {
        this.valor = valor;
    }

    public WebSegEng getWebSegEng() {
        return webSegEng;
    }

    public void setWebSegEng(WebSegEng webSegEng) {
        this.webSegEng = webSegEng;
        setIdSolicitud(webSegEng.getIdSolicitud());
        setIdWebSegeng(webSegEng.getIdWebSegeng());
    }

    public List getCiudadanos() {
        return Collections.unmodifiableList(ciudadanos);
    }

    public boolean addCiudadano(WebCiudadano newCiudadano) {
        return ciudadanos.add(newCiudadano);
    }

    public boolean removeCiudadano(WebCiudadano oldCiudadano) {
        return ciudadanos.remove(oldCiudadano);
    }


    public WebDocumento getDocumento(){
    	WebDocumento rta = null;
    	if(!documentos.isEmpty()){
    		rta = (WebDocumento)this.documentos.get(0);
    	}
    	return rta;
    }
    
    public void setDocumento(WebDocumento documento){
    	if(!documentos.isEmpty()){
    		documentos.remove(0);
    	}
    	documentos.add(documento);
    }

	public String getIdAnotacionDefinitiva() {
		return idAnotacionDefinitiva;
	}

	public void setIdAnotacionDefinitiva(String idAnotacionDefinitiva) {
		this.idAnotacionDefinitiva = idAnotacionDefinitiva;
	}
 /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiaci�n_de_Naturaleza_Jur�dica
	 */
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
        
}
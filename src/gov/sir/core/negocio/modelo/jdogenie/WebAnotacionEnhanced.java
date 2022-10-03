package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.WebAnotacion;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class WebAnotacionEnhanced extends Enhanced{

    private String idSolicitud; // pk
    private String idWebSegeng; // pk
    private String comentario;
    private long copiaComentario;
    private String idDocumento;
    private String idNaturalezaJuridica;
    private String valor;
    private String modalidad;
    private WebSegEngEnhanced webSegEng; // inverse WebSegEngEnhanced.anotaciones
    private List ciudadanos = new ArrayList(); // contains WebCiudadanoEnhanced  inverse WebCiudadanoEnhanced.anotacion
    private List documentos = new ArrayList(); // contains WebDocumentoEnhanced  inverse WebDocumentoEnhanced.anotacion
    private String numeroRadicacion;
    private Date fechaRadicacion;
    private String idAnotacionDefinitiva;
     /*
        *  @author Carlos Torres
        *  @chage   se agrega nuevo campo version a la clase
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiaci�n_de_Naturaleza_Jur�dica
        */
    private long version;

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

	public WebAnotacionEnhanced() {
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

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public WebSegEngEnhanced getWebSegEng() {
        return webSegEng;
    }

    public void setWebSegEng(WebSegEngEnhanced webSegEng) {
        this.webSegEng = webSegEng;
        setIdSolicitud(webSegEng.getIdSolicitud());
        setIdWebSegeng(webSegEng.getIdWebSegeng());
    }

    public List getCiudadanos() {
        return Collections.unmodifiableList(ciudadanos);
    }

    public boolean addCiudadano(WebCiudadanoEnhanced newCiudadano) {
        return ciudadanos.add(newCiudadano);
    }

    public boolean removeCiudadano(WebCiudadanoEnhanced oldCiudadano) {
        return ciudadanos.remove(oldCiudadano);
    }


    public WebDocumentoEnhanced getDocumento(){
    	WebDocumentoEnhanced rta = null;
    	if(!documentos.isEmpty()){
    		rta = (WebDocumentoEnhanced)this.documentos.get(0);
    	}
    	return rta;
    }
    
    public void setDocumento(WebDocumentoEnhanced doc){
    	if(!documentos.isEmpty()){
    		documentos.remove(0);
    	}
    	documentos.add(doc);
    }
    /*
    public List getDocumentos() {
        return Collections.unmodifiableList(documentos);
    }

    public boolean addDocumento(WebDocumentoEnhanced newDocumento) {
        return documentos.add(newDocumento);
    }

    public boolean removeDocumento(WebDocumentoEnhanced oldDocumento) {
        return documentos.remove(oldDocumento);
    }*/


	public static WebAnotacionEnhanced enhance(WebAnotacion v){
		return (WebAnotacionEnhanced)Enhanced.enhance(v);
	}

	public String getIdAnotacionDefinitiva() {
		return idAnotacionDefinitiva;
	}

	public void setIdAnotacionDefinitiva(String idAnotacionDefinitiva) {
		this.idAnotacionDefinitiva = idAnotacionDefinitiva;
	}
 /*
        *  @author Carlos Torres
        *  @chage  metodos accesores para la propiedad version
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiaci�n_de_Naturaleza_Jur�dica
        */
        public long getVersion() {
            return version;
        }

        public void setVersion(long version) {
            this.version = version;
        }
        
}
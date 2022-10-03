package gov.sir.core.negocio.modelo;

import gov.sir.core.negocio.modelo.jdogenie.WebAnotacionEnhanced;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.auriga.core.modelo.TransferObject;

/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public abstract class WebSegEng implements TransferObject {

    private String idSolicitud; // pk
    private String idWebSegeng; // pk
    private Date fechaCreacion;
    private static final long serialVersionUID = 1L;
    private long tipoOperacion;
    private Solicitud solicitud;
    private Usuario usuario;
    private List anotaciones = new ArrayList(); // contains WebAnotacionEnhanced  inverse WebAnotacionEnhanced.webSegEng
    private List foliosHeredados = new ArrayList(); // contains WebFolioHeredadoEnhanced  inverse WebFolioHeredadoEnhanced.webSegEng
    private int idProceso;
    
    public int getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}

	public WebSegEng() {
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

    public long getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(long tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
        setIdSolicitud(solicitud.getIdSolicitud());
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    

    public WebAnotacion getAnotacion(){
    	WebAnotacion rta = null;
    	if(!anotaciones.isEmpty()){
    		rta = (WebAnotacion)this.anotaciones.get(0);
    	}
    	return rta;
    }
    
    public void setAnotacion(WebAnotacion anota){
    	while(!anotaciones.isEmpty()){
    		anotaciones.remove(0);
    	}
    	anotaciones.add(anota);
    }

    /*
    private List getAnotaciones() {
        return Collections.unmodifiableList(anotaciones);
    }

    
    public boolean addAnotacione(WebAnotacion newAnotacione) {
        return anotaciones.add(newAnotacione);
    }

    public boolean removeAnotacione(WebAnotacion oldAnotacione) {
        return anotaciones.remove(oldAnotacione);
    }*/

    public List getFoliosHeredados() {
        return Collections.unmodifiableList(foliosHeredados);
    }

    public boolean addFoliosHeredado(WebFolioHeredado newFoliosHeredado) {
        return foliosHeredados.add(newFoliosHeredado);
    }

    public boolean removeFoliosHeredado(WebFolioHeredado oldFoliosHeredado) {
        return foliosHeredados.remove(oldFoliosHeredado);
    }
}
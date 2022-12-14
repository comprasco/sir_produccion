package gov.sir.core.negocio.modelo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.auriga.core.modelo.TransferObject;

/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class WebFolioNuevo implements TransferObject{
    private static final long serialVersionUID = 1L;
    private String idSolicitud; // pk
    private String idWebSegeng; // pk
    private String area;
    private String hectareas;
    private String metros;
    private String centimetros;
    private String privMetros;
    private String privCentimetros;
    private String consMetros;
    private String consCentimetros;
    private String determinaInm;
    private String descripcion;
    private Date fechaCreacion;
    private String nombre;
    private WebEnglobe englobe; // inverse WebEnglobeEnhanced.foliosNuevos
    private List direcciones = new ArrayList(); // contains WebDireccionEnhanced  inverse WebDireccionEnhanced.folioNuevo

    public WebFolioNuevo() {
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

    public WebEnglobe getEnglobe() {
        return englobe;
    }

    public void setEnglobe(WebEnglobe englobe) {
        this.englobe = englobe;
        setIdSolicitud(englobe.getIdSolicitud());
        setIdWebSegeng(englobe.getIdWebSegeng());
    }

    public List getDirecciones() {
        return Collections.unmodifiableList(direcciones);
    }

    public boolean addDireccione(WebDireccion newDireccione) {
        return direcciones.add(newDireccione);
    }

    public boolean removeDireccione(WebDireccion oldDireccione) {
        return direcciones.remove(oldDireccione);
    }

    public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

        public String getHectareas() {
            return hectareas;
        }

        public void setHectareas(String hectareas) {
            this.hectareas = hectareas;
        }

        public String getMetros() {
            return metros;
        }

        public void setMetros(String metros) {
            this.metros = metros;
        }

        public String getCentimetros() {
            return centimetros;
        }

        public void setCentimetros(String centimetros) {
            this.centimetros = centimetros;
        }
        
        public String getPrivMetros() {
            return privMetros;
        }

        public void setPrivMetros(String privMetros) {
            this.privMetros = privMetros;
        }

        public String getPrivCentimetros() {
            return privCentimetros;
        }

        public void setPrivCentimetros(String privCentimetros) {
            this.privCentimetros = privCentimetros;
        }

        public String getConsMetros() {
            return consMetros;
        }

        public void setConsMetros(String consMetros) {
            this.consMetros = consMetros;
        }

        public String getConsCentimetros() {
            return consCentimetros;
        }

        public void setConsCentimetros(String consCentimetros) {
            this.consCentimetros = consCentimetros;
        }

        public String getDeterminaInm() {
            return determinaInm;
        }

        public void setDeterminaInm(String determinaInm) {
            this.determinaInm = determinaInm;
        }
        
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

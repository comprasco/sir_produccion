package gov.sir.core.negocio.modelo.jdogenie;



import gov.sir.core.negocio.modelo.Anotacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class AnotacionEnhanced extends Enhanced{
    private String idAnotacion; // pk 
    private String idMatricula; // pk 
    private String comentario;
    private Date fecha;
    private double valor;
    private String modalidad;
    private DocumentoEnhanced documento;
    private FolioEnhanced folio; // inverse Folio.anotaciones
    private NaturalezaJuridicaEnhanced naturalezaJuridica;
    private TipoAnotacionEnhanced tipoAnotacion;
    private List anotacionesCancelacion = new ArrayList(); // contains Cancelacion  inverse Cancelacion.canceladora
    private List anotacionesCiudadano = new ArrayList(); // contains AnotacionCiudadano  inverse AnotacionCiudadano.anotacion
    private List anotacionesHijos = new ArrayList(); // contains FolioDerivado  inverse FolioDerivado.padre
    private List anotacionesPadre = new ArrayList(); // contains FolioDerivado  inverse FolioDerivado.hijo
    private List salvedades = new ArrayList(); // contains SalvedadAnotacion  inverse SalvedadAnotacion.anotacion
    private String numRadicacion;
    private Date fechaRadicacion;
    private EstadoAnotacionEnhanced estado;
    private String orden;
    private String especificacion;
    private long lastIdSalvedad;
    private UsuarioEnhanced usuarioCreacion;
	private List turnoAnotacion = new ArrayList(); // contains TurnoAnotacionEnhanced  inverse TurnoAnotacionEnhanced.anotacion
	private String ordenLPAD;
	private boolean toDelete;
	private String idAnotacionModificada;
	private boolean toUpdateValor;
	private UsuarioEnhanced usuarioCreacionTMP;
	private boolean temporal;
	private boolean temporalConContraparteDefinitiva;
	private DatosAntiguoSistemaEnhanced datosAntiguoSistema;
	private boolean heredada;
	private boolean link;
	private String idWorkflow;
	private String circulo;

    public String getIdWorkflow() {
		return idWorkflow;
	}

	public void setIdWorkflow(String idWorkflow) {
		this.idWorkflow = idWorkflow;
	}

	public AnotacionEnhanced() {
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public DocumentoEnhanced getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoEnhanced documento) {
        this.documento = documento;
    }

    public FolioEnhanced getFolio() {
        return folio;
    }

    public void setFolio(FolioEnhanced folio) {
        this.folio = folio;
        setIdMatricula(folio.getIdMatricula());
    }

    public NaturalezaJuridicaEnhanced getNaturalezaJuridica() {
        return naturalezaJuridica;
    }

    public void setNaturalezaJuridica(NaturalezaJuridicaEnhanced naturalezaJuridica) {
        this.naturalezaJuridica = naturalezaJuridica;
    }

    public TipoAnotacionEnhanced getTipoAnotacion() {
        return tipoAnotacion;
    }

    public void setTipoAnotacion(TipoAnotacionEnhanced tipoAnotacion) {
        this.tipoAnotacion = tipoAnotacion;
    }

    public List getAnotacionesCancelacions() {
        return Collections.unmodifiableList(anotacionesCancelacion);
    }

    public boolean addAnotacionesCancelacion(
        CancelacionEnhanced newAnotacionesCancelacion) {
        return anotacionesCancelacion.add(newAnotacionesCancelacion);
    }

    public boolean removeAnotacionesCancelacion(
        CancelacionEnhanced oldAnotacionesCancelacion) {
        return anotacionesCancelacion.remove(oldAnotacionesCancelacion);
    }

    public List getAnotacionesCiudadanos() {
        return Collections.unmodifiableList(anotacionesCiudadano);
    }

    public boolean addAnotacionesCiudadano(
        AnotacionCiudadanoEnhanced newAnotacionesCiudadano) {
        return anotacionesCiudadano.add(newAnotacionesCiudadano);
    }

    public boolean removeAnotacionesCiudadano(
        AnotacionCiudadanoEnhanced oldAnotacionesCiudadano) {
        return anotacionesCiudadano.remove(oldAnotacionesCiudadano);
    }

    public List getAnotacionesHijos() {
        return Collections.unmodifiableList(anotacionesHijos);
    }

    public boolean addAnotacionesHijo(FolioDerivadoEnhanced newAnotacionesHijo) {
        return anotacionesHijos.add(newAnotacionesHijo);
    }

    public boolean removeAnotacionesHijo(FolioDerivadoEnhanced oldAnotacionesHijo) {
        return anotacionesHijos.remove(oldAnotacionesHijo);
    }

    public List getAnotacionesPadre() {
        return Collections.unmodifiableList(anotacionesPadre);
    }

    public boolean addAnotacionesPadre(FolioDerivadoEnhanced newAnotacionesPadre) {
        return anotacionesPadre.add(newAnotacionesPadre);
    }

    public boolean removeAnotacionesPadre(FolioDerivadoEnhanced oldAnotacionesPadre) {
        return anotacionesPadre.remove(oldAnotacionesPadre);
    }

    public List getSalvedades() {
        return Collections.unmodifiableList(salvedades);
    }

    public boolean addSalvedade(SalvedadAnotacionEnhanced newSalvedade) {
        return salvedades.add(newSalvedade);
    }

    public boolean removeSalvedade(SalvedadAnotacionEnhanced oldSalvedade) {
        return salvedades.remove(oldSalvedade);
    }

    /**
     * @return
     */
    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    /**
     * @return
     */
    public String getNumRadicacion() {
        return numRadicacion;
    }

    /**
     * @param date
     */
    public void setFechaRadicacion(Date date) {
        fechaRadicacion = date;
    }

    /**
     * @param string
     */
    public void setNumRadicacion(String string) {
        numRadicacion = string;
    }

    /**
	 * @return
	 */
	public EstadoAnotacionEnhanced getEstado() {
		return estado;
	}

	/**
	 * @param anotacion
	 */
	public void setEstado(EstadoAnotacionEnhanced anotacion) {
		estado = anotacion;
	}

	/**
	 * @return
	 */
	public String getOrden() {
		return orden;
	}

	/**
	 * @param string
	 */
	public void setOrden(String string) {
		orden = string;
	}

	/**
	 * @return
	 */
	public String getEspecificacion() {
		return especificacion;
	}

	/**
	 * @param string
	 */
	public void setEspecificacion(String string) {
		especificacion = string;
	}

	public static AnotacionEnhanced enhance(Anotacion anotacion) {
	return (AnotacionEnhanced) Enhanced.enhance(anotacion);
}

	/**
	 * @return
	 */
	public long getLastIdSalvedad() {
		return lastIdSalvedad;
	}

	/**
	 * @param l
	 */
	public void setLastIdSalvedad(long l) {
		lastIdSalvedad = l;
	}

	/**
	 * @return
	 */
	public UsuarioEnhanced getUsuarioCreacion() {
		return usuarioCreacion;
	}

	/**
	 * @param enhanced
	 */
	public void setUsuarioCreacion(UsuarioEnhanced enhanced) {
		usuarioCreacion = enhanced;
	}
	
	
	 public List getTurnoAnotacions() {
		 return Collections.unmodifiableList(turnoAnotacion);
	 }

	 public boolean addTurnoAnotacion(TurnoAnotacionEnhanced newTurnoAnotacionEnhanced) {
		 return turnoAnotacion.add(newTurnoAnotacionEnhanced);
	 }

	 public boolean removeTurnoAnotacion(TurnoAnotacionEnhanced oldTurnoAnotacionEnhanced) {
		 return turnoAnotacion.remove(oldTurnoAnotacionEnhanced);
	 }

	/**
	 * @return
	 */
	public String getOrdenLPAD() {
		return ordenLPAD;
	}

	/**
	 * @param string
	 */
	public void setOrdenLPAD(String string) {
		ordenLPAD = string;
	}

	/**
	 * @return
	 */
	public boolean isToDelete() {
		return toDelete;
	}

	/**
	 * @param b
	 */
	public void setToDelete(boolean b) {
		toDelete = b;
	}

	/**
	 * @return
	 */
	public String getIdAnotacionModificada() {
		return idAnotacionModificada;
	}

	/**
	 * @param string
	 */
	public void setIdAnotacionModificada(String string) {
		idAnotacionModificada = string;
	}

	/**
	 * @return
	 */
	public boolean isToUpdateValor() {
		return toUpdateValor;
	}

	/**
	 * @param b
	 */
	public void setToUpdateValor(boolean b) {
		toUpdateValor = b;
	}

	/**
	 * @return
	 */
	public UsuarioEnhanced getUsuarioCreacionTMP() {
		return usuarioCreacionTMP;
	}

	/**
	 * @param enhanced
	 */
	public void setUsuarioCreacionTMP(UsuarioEnhanced enhanced) {
		usuarioCreacionTMP = enhanced;
	}

	/**
	 * @return
	 */
	public boolean isTemporal() {
		return temporal;
	}

	/**
	 * @param b
	 */
	public void setTemporal(boolean b) {
		temporal = b;
	}
	
	
	/**
	 * @return
	 */
	public boolean isTemporalConContraparteDefinitiva() {
		return temporalConContraparteDefinitiva;
	}

	/**
	 * @param b
	 */
	public void setTemporalConContraparteDefinitiva(boolean b) {
		temporalConContraparteDefinitiva = b;
	}
		
	

	/**
	 * @return
	 */
	public DatosAntiguoSistemaEnhanced getDatosAntiguoSistema() {
		return datosAntiguoSistema;
	}

	/**
	 * @param enhanced
	 */
	public void setDatosAntiguoSistema(DatosAntiguoSistemaEnhanced enhanced) {
		datosAntiguoSistema = enhanced;
	}

	/**
	 * @return
	 */
	public boolean isHeredada() {
		return heredada;
	}

	/**
	 * @param b
	 */
	public void setHeredada(boolean b) {
		heredada = b;
	}

	/**
	 * @return
	 */
	public boolean isLink() {
		return link;
	}

	/**
	 * @param b
	 */
	public void setLink(boolean b) {
		link = b;
	}

	public String getCirculo() {
		return circulo;
	}

	public void setCirculo(String circulo) {
		this.circulo = circulo;
	}

}

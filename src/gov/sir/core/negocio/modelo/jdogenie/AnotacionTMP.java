package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Anotacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class AnotacionTMP {
    private String idAnotacionTmp; // pk 
    private String idMatricula; // pk 
    private String comentario;
    private String especificacion;
    private Date fecha;
    private Date fechaCreacion;
    private Date fechaRadicacion;
    private long lastIdCancelacion;
    private long lastIdSalvedad;
    private String numRadicacion;
    private String orden;
    private double valor;
    private String modalidad;
    private DocumentoEnhanced documento;
    private EstadoAnotacionEnhanced estado;
    private FolioEnhanced folio;
    private NaturalezaJuridicaEnhanced naturalezaJuridica;
    private TipoAnotacionEnhanced tipoAnotacion;
    private List anotacionesCancelacionTMP = new ArrayList(); // contains CancelacionTMP  inverse CancelacionTMP.canceladora
    private List anotacionesCiudadanoTMP = new ArrayList(); // contains AnotacionCiudadanoTMP  inverse AnotacionCiudadanoTMP.anotacionTmp
    private List anotacionesHijosTMP = new ArrayList(); // contains FolioDerivadoTMP  inverse FolioDerivadoTMP.padreTmp
    private List anotacionesPadreTMP = new ArrayList(); // contains FolioDerivadoTMP  inverse FolioDerivadoTMP.hijoTmp
    private List salvedadesTMP = new ArrayList(); // contains SalvedadAnotacionTMP  inverse SalvedadAnotacionTMP.anotacionTmp
    private String ordenLPAD;
    private boolean toDelete;
	private String idAnotacionModificada;
	private boolean toUpdateValor;
	private UsuarioEnhanced usuario;
    
	// Atributo no persistente que indica si
	// se debe crear o no las anotaciones canceladas por
	// esta anotación
    private boolean crearCancelada = false;
    
	private DocumentoTMP documentoTMP;
	
	private DatosAntiguoSistemaEnhanced datosAntiguoSistema;
	
	private boolean heredada;
	
	private String idWorkflow;
    
    

    public String getIdWorkflow() {
		return idWorkflow;
	}

	public void setIdWorkflow(String idWorkflow) {
		this.idWorkflow = idWorkflow;
	}

	/**
         * @param enhanced
         */
    public AnotacionTMP(AnotacionEnhanced auxAn, List cache) {
        if (!cache.contains(cache)) {
        	cache.add(auxAn);
            idAnotacionTmp = auxAn.getIdAnotacion();
            idMatricula = auxAn.getIdMatricula();
            comentario = auxAn.getComentario();
            especificacion = auxAn.getEspecificacion();
            fecha = auxAn.getFecha();
            fechaRadicacion = auxAn.getFechaRadicacion();
            lastIdSalvedad = auxAn.getLastIdSalvedad();
            numRadicacion = auxAn.getNumRadicacion();
            orden = auxAn.getOrden();
            valor = auxAn.getValor();
            modalidad = auxAn.getModalidad();
            documento = auxAn.getDocumento();
            estado = auxAn.getEstado();
            folio = auxAn.getFolio();
            naturalezaJuridica = auxAn.getNaturalezaJuridica();
            tipoAnotacion = auxAn.getTipoAnotacion();
			toDelete = auxAn.isToDelete();
			idAnotacionModificada = auxAn.getIdAnotacionModificada();
            toUpdateValor = auxAn.isToUpdateValor();
            usuario = auxAn.getUsuarioCreacionTMP();
            datosAntiguoSistema = auxAn.getDatosAntiguoSistema();
			ordenLPAD = auxAn.getOrdenLPAD();
			idWorkflow = auxAn.getIdWorkflow();
		
            for (Iterator it = auxAn.getSalvedades().iterator(); it.hasNext();) {
                this.addSalvedadesTMP(new SalvedadAnotacionTMP(
                        (SalvedadAnotacionEnhanced) it.next()));
            }

            for (Iterator it = auxAn.getAnotacionesCancelacions().iterator();
                    it.hasNext();) {
                this.addAnotacionesCancelacionTMP(new CancelacionTMP(
                        (CancelacionEnhanced) it.next()));
            }

            for (Iterator it = auxAn.getAnotacionesCiudadanos().iterator();
                    it.hasNext();) {
                this.addAnotacionesCiudadanoTMP(new AnotacionCiudadanoTMP(
                        (AnotacionCiudadanoEnhanced) it.next()));
            }

			//Las anotaciones hijas y padre NUNCA se copian a anotaciones
			//temporales a menos que sean segregaciones o englobes que en tal caso
			//se crean explícitamente las relaciones en sus métodos. 
			//La razón de esto es que el modelo no permite tener en la tabla de
			//FOLIO_DERIVADO_TMP anotaciones temporales que apunten a anotaciones
			//definitivas o viceversa:
			
			/*
            for (Iterator it = auxAn.getAnotacionesHijos().iterator();
                    it.hasNext();) {
                this.addAnotacionesHijosTMP(new FolioDerivadoTMP(
                        (FolioDerivadoEnhanced) it.next(), cache));
            }

            for (Iterator it = auxAn.getAnotacionesPadre().iterator();
                    it.hasNext();) {
                this.addAnotacionesPadreTMP(new FolioDerivadoTMP(
                        (FolioDerivadoEnhanced) it.next(), cache));
            }
            */
            
        }
    }
    
    
    public AnotacionTMP(AnotacionEnhanced auxAn, List cache, boolean folioDerive) {
        if (!cache.contains(cache) && folioDerive) {
        	cache.add(auxAn);
            idAnotacionTmp = auxAn.getIdAnotacion();
            idMatricula = auxAn.getIdMatricula();
            comentario = auxAn.getComentario();
            especificacion = auxAn.getEspecificacion();
            fecha = auxAn.getFecha();
            fechaRadicacion = auxAn.getFechaRadicacion();
            lastIdSalvedad = auxAn.getLastIdSalvedad();
            numRadicacion = auxAn.getNumRadicacion();
            orden = auxAn.getOrden();
            valor = auxAn.getValor();
            modalidad = auxAn.getModalidad();
            documento = auxAn.getDocumento();
            estado = auxAn.getEstado();
            folio = auxAn.getFolio();
            naturalezaJuridica = auxAn.getNaturalezaJuridica();
            tipoAnotacion = auxAn.getTipoAnotacion();
			toDelete = auxAn.isToDelete();
			idAnotacionModificada = auxAn.getIdAnotacionModificada();
            toUpdateValor = auxAn.isToUpdateValor();
            usuario = auxAn.getUsuarioCreacionTMP();
            datosAntiguoSistema = auxAn.getDatosAntiguoSistema();
			ordenLPAD = auxAn.getOrdenLPAD();
			idWorkflow = auxAn.getIdWorkflow();
		
            for (Iterator it = auxAn.getSalvedades().iterator(); it.hasNext();) {
                this.addSalvedadesTMP(new SalvedadAnotacionTMP(
                        (SalvedadAnotacionEnhanced) it.next()));
            }

            for (Iterator it = auxAn.getAnotacionesCancelacions().iterator();
                    it.hasNext();) {
                this.addAnotacionesCancelacionTMP(new CancelacionTMP(
                        (CancelacionEnhanced) it.next()));
            }

            for (Iterator it = auxAn.getAnotacionesCiudadanos().iterator();
                    it.hasNext();) {
                this.addAnotacionesCiudadanoTMP(new AnotacionCiudadanoTMP(
                        (AnotacionCiudadanoEnhanced) it.next()));
            }

			//Las anotaciones hijas y padre NUNCA se copian a anotaciones
			//temporales a menos que sean segregaciones o englobes que en tal caso
			//se crean explícitamente las relaciones en sus métodos. 
			//La razón de esto es que el modelo no permite tener en la tabla de
			//FOLIO_DERIVADO_TMP anotaciones temporales que apunten a anotaciones
			//definitivas o viceversa:
			
			/*
            for (Iterator it = auxAn.getAnotacionesHijos().iterator();
                    it.hasNext();) {
                this.addAnotacionesHijosTMP(new FolioDerivadoTMP(
                        (FolioDerivadoEnhanced) it.next(), cache));
            } */

            for (Iterator it = auxAn.getAnotacionesPadre().iterator();
                    it.hasNext();) {
                this.addAnotacionesPadreTMP(new FolioDerivadoTMP(
                        (FolioDerivadoEnhanced) it.next(), cache));
            }
           
            
        }
    }
    
    public AnotacionTMP(Anotacion anotacion, List cache) {
    	AnotacionEnhanced auxAn = AnotacionEnhanced.enhance(anotacion);
        if (!cache.contains(cache)) {
        	cache.add(auxAn);
            idAnotacionTmp = auxAn.getIdAnotacion();
            idMatricula = auxAn.getIdMatricula();
            comentario = auxAn.getComentario();
            especificacion = auxAn.getEspecificacion();
            fecha = auxAn.getFecha();
            fechaRadicacion = auxAn.getFechaRadicacion();
            lastIdSalvedad = auxAn.getLastIdSalvedad();
            numRadicacion = auxAn.getNumRadicacion();
            orden = auxAn.getOrden();
            valor = auxAn.getValor();
            modalidad = auxAn.getModalidad();
            documento = auxAn.getDocumento();
            estado = auxAn.getEstado();
            folio = auxAn.getFolio();
            naturalezaJuridica = auxAn.getNaturalezaJuridica();
            tipoAnotacion = auxAn.getTipoAnotacion();
			toDelete = auxAn.isToDelete();
			idAnotacionModificada = auxAn.getIdAnotacionModificada();
            toUpdateValor = auxAn.isToUpdateValor();
            usuario = auxAn.getUsuarioCreacionTMP();
            datosAntiguoSistema = auxAn.getDatosAntiguoSistema();
			ordenLPAD = auxAn.getOrdenLPAD();
			idWorkflow = auxAn.getIdWorkflow();
		
            for (Iterator it = auxAn.getSalvedades().iterator(); it.hasNext();) {
                this.addSalvedadesTMP(new SalvedadAnotacionTMP(
                        (SalvedadAnotacionEnhanced) it.next()));
            }

            for (Iterator it = auxAn.getAnotacionesCancelacions().iterator();
                    it.hasNext();) {
                this.addAnotacionesCancelacionTMP(new CancelacionTMP(
                        (CancelacionEnhanced) it.next()));
            }

            /*for (Iterator it = auxAn.getAnotacionesCiudadanos().iterator();
                    it.hasNext();) {
                this.addAnotacionesCiudadanoTMP(new AnotacionCiudadanoTMP(
                        (AnotacionCiudadanoEnhanced) it.next()));
            }*/

			//Las anotaciones hijas y padre NUNCA se copian a anotaciones
			//temporales a menos que sean segregaciones o englobes que en tal caso
			//se crean explícitamente las relaciones en sus métodos. 
			//La razón de esto es que el modelo no permite tener en la tabla de
			//FOLIO_DERIVADO_TMP anotaciones temporales que apunten a anotaciones
			//definitivas o viceversa:
            /*for (Iterator it = auxAn.getAnotacionesHijos().iterator();
                    it.hasNext();) {
                this.addAnotacionesHijosTMP(new FolioDerivadoTMP(
                        (FolioDerivadoEnhanced) it.next(), cache));
            }

            for (Iterator it = auxAn.getAnotacionesPadre().iterator();
                    it.hasNext();) {
                this.addAnotacionesPadreTMP(new FolioDerivadoTMP(
                        (FolioDerivadoEnhanced) it.next(), cache));
            }*/
            
            
        }
    }

    public AnotacionTMP() {
    }

    public AnotacionTMP(Anotacion anotacion) {
        AnotacionEnhanced auxAn = AnotacionEnhanced.enhance(anotacion);
        idAnotacionTmp = auxAn.getIdAnotacion();
        idMatricula = auxAn.getIdMatricula();
        comentario = auxAn.getComentario();
        especificacion = auxAn.getEspecificacion();
        fecha = auxAn.getFecha();
        fechaRadicacion = auxAn.getFechaRadicacion();
        lastIdSalvedad = auxAn.getLastIdSalvedad();
        numRadicacion = auxAn.getNumRadicacion();
        orden = auxAn.getOrden();
        valor = auxAn.getValor();
        modalidad = auxAn.getModalidad();
        documento = auxAn.getDocumento();
        estado = auxAn.getEstado();
        folio = auxAn.getFolio();
        naturalezaJuridica = auxAn.getNaturalezaJuridica();
        tipoAnotacion = auxAn.getTipoAnotacion();
        toDelete = auxAn.isToDelete();
		idAnotacionModificada = auxAn.getIdAnotacionModificada();
		toUpdateValor = auxAn.isToUpdateValor();
		usuario = auxAn.getUsuarioCreacionTMP();
		datosAntiguoSistema = auxAn.getDatosAntiguoSistema();
		ordenLPAD = auxAn.getOrdenLPAD();
		idWorkflow = auxAn.getIdWorkflow();
		
        for (Iterator it = auxAn.getSalvedades().iterator(); it.hasNext();) {
            this.addSalvedadesTMP(new SalvedadAnotacionTMP(
                    (SalvedadAnotacionEnhanced) it.next()));
        }

        for (Iterator it = auxAn.getAnotacionesCancelacions().iterator();
                it.hasNext();) {
            this.addAnotacionesCancelacionTMP(new CancelacionTMP(
                    (CancelacionEnhanced) it.next()));
        }

        for (Iterator it = auxAn.getAnotacionesCiudadanos().iterator();
                it.hasNext();) {
            this.addAnotacionesCiudadanoTMP(new AnotacionCiudadanoTMP(
                    (AnotacionCiudadanoEnhanced) it.next()));
        }
    }

    public String getIdAnotacionTmp() {
        return idAnotacionTmp;
    }

    public void setIdAnotacionTmp(String idAnotacionTmp) {
        this.idAnotacionTmp = idAnotacionTmp;
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

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    public long getLastIdCancelacion() {
        return lastIdCancelacion;
    }

    public void setLastIdCancelacion(long lastIdCancelacion) {
        this.lastIdCancelacion = lastIdCancelacion;
    }

    public long getLastIdSalvedad() {
        return lastIdSalvedad;
    }

    public void setLastIdSalvedad(long lastIdSalvedad) {
        this.lastIdSalvedad = lastIdSalvedad;
    }

    public String getNumRadicacion() {
        return numRadicacion;
    }

    public void setNumRadicacion(String numRadicacion) {
        this.numRadicacion = numRadicacion;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
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

    public EstadoAnotacionEnhanced getEstado() {
        return estado;
    }

    public void setEstado(EstadoAnotacionEnhanced estado) {
        this.estado = estado;
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

    public void setNaturalezaJuridica(
        NaturalezaJuridicaEnhanced naturalezaJuridica) {
        this.naturalezaJuridica = naturalezaJuridica;
    }

    public TipoAnotacionEnhanced getTipoAnotacion() {
        return tipoAnotacion;
    }

    public void setTipoAnotacion(TipoAnotacionEnhanced tipoAnotacion) {
        this.tipoAnotacion = tipoAnotacion;
    }

    public List getAnotacionesCancelacionTMPs() {
        return Collections.unmodifiableList(anotacionesCancelacionTMP);
    }

    public boolean addAnotacionesCancelacionTMP(
        CancelacionTMP newAnotacionesCancelacionTMP) {
        return anotacionesCancelacionTMP.add(newAnotacionesCancelacionTMP);
    }

    public boolean removeAnotacionesCancelacionTMP(
        CancelacionTMP oldAnotacionesCancelacionTMP) {
        return anotacionesCancelacionTMP.remove(oldAnotacionesCancelacionTMP);
    }

    public List getAnotacionesCiudadanoTMPs() {
        return Collections.unmodifiableList(anotacionesCiudadanoTMP);
    }

    public boolean addAnotacionesCiudadanoTMP(
        AnotacionCiudadanoTMP newAnotacionesCiudadanoTMP) {
        return anotacionesCiudadanoTMP.add(newAnotacionesCiudadanoTMP);
    }

    public boolean removeAnotacionesCiudadanoTMP(
        AnotacionCiudadanoTMP oldAnotacionesCiudadanoTMP) {
        return anotacionesCiudadanoTMP.remove(oldAnotacionesCiudadanoTMP);
    }

    public List getAnotacionesHijosTMPs() {
        return Collections.unmodifiableList(anotacionesHijosTMP);
    }

    public boolean addAnotacionesHijosTMP(
        FolioDerivadoTMP newAnotacionesHijosTMP) {
        return anotacionesHijosTMP.add(newAnotacionesHijosTMP);
    }

    public boolean removeAnotacionesHijosTMP(
        FolioDerivadoTMP oldAnotacionesHijosTMP) {
        return anotacionesHijosTMP.remove(oldAnotacionesHijosTMP);
    }

    public List getAnotacionesPadreTMPs() {
        return Collections.unmodifiableList(anotacionesPadreTMP);
    }

    public boolean addAnotacionesPadreTMP(
        FolioDerivadoTMP newAnotacionesPadreTMP) {
        return anotacionesPadreTMP.add(newAnotacionesPadreTMP);
    }

    public boolean removeAnotacionesPadreTMP(
        FolioDerivadoTMP oldAnotacionesPadreTMP) {
        return anotacionesPadreTMP.remove(oldAnotacionesPadreTMP);
    }

    public List getSalvedadesTMPs() {
        return Collections.unmodifiableList(salvedadesTMP);
    }

    public boolean addSalvedadesTMP(SalvedadAnotacionTMP newSalvedadesTMP) {
        return salvedadesTMP.add(newSalvedadesTMP);
    }

    public boolean removeSalvedadesTMP(SalvedadAnotacionTMP oldSalvedadesTMP) {
        return salvedadesTMP.remove(oldSalvedadesTMP);
    }

    public AnotacionEnhanced getDefinitivo(List cache) {
        if (!cache.contains(this)) {
            cache.add(this);

            AnotacionEnhanced anota = new AnotacionEnhanced();
            anota.setComentario(this.getComentario());
            anota.setDocumento(this.getDocumento());
            anota.setEspecificacion(this.getEspecificacion());
            anota.setEstado(this.getEstado());
            anota.setFecha(this.getFecha());
            anota.setFechaRadicacion(this.getFechaRadicacion());
            anota.setIdAnotacion(this.getIdAnotacionTmp());
            anota.setIdMatricula(this.getIdMatricula());
            anota.setLastIdSalvedad(this.getLastIdSalvedad());
            anota.setNaturalezaJuridica(this.getNaturalezaJuridica());
            anota.setNumRadicacion(this.getNumRadicacion());
            anota.setOrden(this.getOrden());
            anota.setTipoAnotacion(this.getTipoAnotacion());
            anota.setValor(this.getValor());
            anota.setModalidad(this.getModalidad());
            anota.setIdAnotacionModificada(this.getIdAnotacionModificada());
			anota.setToUpdateValor(this.isToUpdateValor());
			anota.setUsuarioCreacionTMP(this.getUsuario());
			anota.setDatosAntiguoSistema(this.getDatosAntiguoSistema());
			anota.setOrdenLPAD(this.getOrdenLPAD());
			anota.setHeredada(this.isHeredada());
			anota.setIdWorkflow(this.getIdWorkflow());
	
            for (Iterator it = this.getSalvedadesTMPs().iterator();
                    it.hasNext();) {
                anota.addSalvedade(((SalvedadAnotacionTMP) it.next()).getDefinitivo());
            }

            for (Iterator it = this.getAnotacionesCancelacionTMPs().iterator();
                    it.hasNext();) {
                anota.addAnotacionesCancelacion(((CancelacionTMP) it.next()).getDefinitivo());
            }

            for (Iterator it = this.getAnotacionesCiudadanoTMPs().iterator();
                    it.hasNext();) {
                anota.addAnotacionesCiudadano(((AnotacionCiudadanoTMP) it.next()).getDefinitivo());
            }

            for (Iterator it = this.getAnotacionesHijosTMPs().iterator();
                    it.hasNext();) {
                anota.addAnotacionesHijo(((FolioDerivadoTMP) it.next()).getDefinitivo(
                        cache));
            }
			
			//No se retornan las anotaciones padre temporales
			/*
            for (Iterator it = this.getAnotacionesPadreTMPs().iterator();
                    it.hasNext();) {
                anota.addAnotacionesPadre(((FolioDerivadoTMP) it.next()).getDefinitivo(
                        cache));
            }*/

            return anota;
        } else {
            return new AnotacionEnhanced();
        }
    }
    
    
    public AnotacionEnhanced getDefinitivoFD(List cache) {
        if (!cache.contains(this)) {
            cache.add(this);

            AnotacionEnhanced anota = new AnotacionEnhanced();
            anota.setComentario(this.getComentario());
            anota.setDocumento(this.getDocumento());
            anota.setEspecificacion(this.getEspecificacion());
            anota.setEstado(this.getEstado());
            anota.setFecha(this.getFecha());
            anota.setFechaRadicacion(this.getFechaRadicacion());
            anota.setIdAnotacion(this.getIdAnotacionTmp());
            anota.setIdMatricula(this.getIdMatricula());
            anota.setLastIdSalvedad(this.getLastIdSalvedad());
            anota.setNaturalezaJuridica(this.getNaturalezaJuridica());
            anota.setNumRadicacion(this.getNumRadicacion());
            anota.setOrden(this.getOrden());
            anota.setTipoAnotacion(this.getTipoAnotacion());
            anota.setValor(this.getValor());
            anota.setModalidad(this.getModalidad());
            anota.setIdAnotacionModificada(this.getIdAnotacionModificada());
			anota.setToUpdateValor(this.isToUpdateValor());
			anota.setUsuarioCreacionTMP(this.getUsuario());
			anota.setDatosAntiguoSistema(this.getDatosAntiguoSistema());
			anota.setOrdenLPAD(this.getOrdenLPAD());
			anota.setHeredada(this.isHeredada());
			anota.setIdWorkflow(this.getIdWorkflow());
	
            for (Iterator it = this.getSalvedadesTMPs().iterator();
                    it.hasNext();) {
                anota.addSalvedade(((SalvedadAnotacionTMP) it.next()).getDefinitivo());
            }

            for (Iterator it = this.getAnotacionesCancelacionTMPs().iterator();
                    it.hasNext();) {
                anota.addAnotacionesCancelacion(((CancelacionTMP) it.next()).getDefinitivo());
            }

            for (Iterator it = this.getAnotacionesCiudadanoTMPs().iterator();
                    it.hasNext();) {
                anota.addAnotacionesCiudadano(((AnotacionCiudadanoTMP) it.next()).getDefinitivo());
            }

            for (Iterator it = this.getAnotacionesHijosTMPs().iterator();
                    it.hasNext();) {
                anota.addAnotacionesHijo(((FolioDerivadoTMP) it.next()).getDefinitivo(
                        cache));
            }
			
			//No se retornan las anotaciones padre temporales
			
            for (Iterator it = this.getAnotacionesPadreTMPs().iterator();
                    it.hasNext();) {
                anota.addAnotacionesPadre(((FolioDerivadoTMP) it.next()).getDefinitivo(
                        cache));
            }

            return anota;
        } else {
            return new AnotacionEnhanced();
        }
    }

    /**
	 * @return
	 */
	public boolean isCrearCancelada() {
		return crearCancelada;
	}

	/**
	 * @param b
	 */
	public void setCrearCancelada(boolean b) {
		crearCancelada = b;
	}

	/**
	 * 
	 */
	public boolean removeAllAnotacionesCiudadanoTMP() {
		return this.anotacionesCiudadanoTMP.removeAll(anotacionesCiudadanoTMP);
		
	}

	/**
	 * 
	 */
	public boolean removeAllAnotacionesCancelacionTMP() {
		return this.anotacionesCancelacionTMP.removeAll(anotacionesCancelacionTMP);
		
	}

	/**
	 * 
	 */
	public boolean removeAllSalvedadesTMP() {
		return this.salvedadesTMP.removeAll(salvedadesTMP);
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
	public UsuarioEnhanced getUsuario() {
		return usuario;
	}

	/**
	 * @param enhanced
	 */
	public void setUsuario(UsuarioEnhanced enhanced) {
		usuario = enhanced;
	}

	/**
	 * @return
	 */
	public DocumentoTMP getDocumentoTMP() {
		return documentoTMP;
	}

	/**
	 * @param documentoTMP
	 */
	public void setDocumentoTMP(DocumentoTMP documentoTMP) {
		this.documentoTMP = documentoTMP;
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

}
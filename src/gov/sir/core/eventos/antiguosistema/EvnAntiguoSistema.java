package gov.sir.core.eventos.antiguosistema;

import java.util.List;
import java.util.Vector;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * @author jfrias
 *
 */
public class EvnAntiguoSistema extends EvnSIR {
    public static final String REVISION_INICIAL_CONFIRMAR="REVISION_INICIAL_CONFIRMAR";
    public static final String REVISION_INICIAL_NEGAR="REVISION_INICIAL_NEGAR";
    public static final String REVISION_INICIAL_EXISTE="REVISION_INICIAL_EXISTE";

	public static final String REVISION_INICIAL_CONFIRMAR_WF="CONFIRMAR";
	public static final String REVISION_INICIAL_NEGAR_WF="NEGAR";
	public static final String REVISION_INICIAL_EXISTE_WF="EXISTE";
	public static final String ANTIGUO_SISTEMA_PERTENENCIA_WF="PERTENENCIA";

	public static final String HOJA_RUTA_ASOCIAR_MATRICULA="HOJA_RUTA_ASOCIAR_MATRICULA";
	public static final String HOJA_RUTA_DESASOCIAR_FOLIO="HOJA_RUTA_DESASOCIAR_FOLIO";
	public static final String HOJA_RUTA_ELIMINAR_FOLIO="HOJA_RUTA_ELIMINAR_FOLIO";
	public static final String HOJA_RUTA_IMPRIMIR_FOLIO="HOJA_RUTA_IMPRIMIR_FOLIO";
	public static final String HOJA_RUTA_EDITAR_FOLIO="HOJA_RUTA_EDITAR_FOLIO";
	public static final String HOJA_RUTA_CONSULTAR_FOLIO="HOJA_RUTA_CONSULTAR_FOLIO";
	public static final String HOJA_RUTA_EDITAR_CONSULTAR_FOLIO="HOJA_RUTA_EDITAR_CONSULTAR_FOLIO";

    public static final String HOJA_RUTA_CREADO="HOJA_RUTA_CREADO";
	public static final String HOJA_RUTA_EXISTE="HOJA_RUTA_EXISTE";
    public static final String HOJA_RUTA_REANALIZAR="HOJA_RUTA_REANALIZAR";
    public static final String HOJA_RUTA_MAS_DOCUMENTOS="HOJA_RUTA_MAS_DOCUMENTOS";
    public static final String HOJA_RUTA_RECHAZAR="HOJA_RUTA_RECHAZAR";
    
    public static final String HOJA_RUTA_CREADO_WF="CREADO";
	public static final String HOJA_RUTA_EXISTE_WF="EXISTE";    
    public static final String HOJA_RUTA_REANALIZAR_WF="REANALIZAR";
    public static final String HOJA_RUTA_MAS_DOCUMENTOS_WF="MAS_DOCS";
    public static final String HOJA_RUTA_RECHAZAR_WF="RECHAZADO";
    
    public static final String CREACION_FOLIO_APROBAR="CREACION_FOLIO_APROBAR";
    public static final String CREACION_FOLIO_NEGAR="CREACION_FOLIO_NEGAR";
    
    public static final String CREACION_FOLIO_APROBAR_WF="CREADO";
    public static final String CREACION_FOLIO_NEGAR_WF="REANALIZAR";
        
    public static final String REVISION_FINAL_CREADO="REVISION_FINAL_CREADO";
    public static final String REVISION_FINAL_RECHAZADO="REVISION_FINAL_RECHAZADO";
    public static final String REVISION_FINAL_HOJA_RUTA="REVISION_FINAL_HOJA_RUTA";
    public static final String REVISION_FINAL_EXISTE="REVISION_FINAL_EXISTE";
    public static final String REVISION_FINAL_MAS_DOCS="REVISION_FINAL_MAS_DOCS";
    
    public static final String REVISION_FINAL_CREADO_WF="CREADO";
    public static final String REVISION_FINAL_RECHAZADO_WF="RECHAZADO";
    public static final String REVISION_FINAL_HOJA_RUTA_WF="HOJA_RUTA";
    public static final String REVISION_FINAL_EXISTE_WF="EXISTE";
    public static final String REVISION_FINAL_MAS_DOCS_WF="MAS_DOCS";
    
    public static final String CANCELAR_ANOTACION = "CANCELAR_ANOTACION";
	public static final String CONSULTAR_CANCELACION = "CONSULTAR_CANCELACION";
	public static final String AVANZAR_CORRECCIONES = "AVANZAR_CORRECCIONES";
	public static final String AVANZAR_MAYOR_VALOR = "AVANZAR_MAYOR_VALOR";
	public static final String HOJA_RUTA_CONSULTAR_FOLIO_EDICION_ANOTACIONES = "HOJA_RUTA_CONSULTAR_FOLIO_EDICION_ANOTACIONES";
	
	public static final String AGREGAR_CIUDADANO_ANOTACION = "AGREGAR_CIUDADANO_ANOTACION";
	
	public static final String CONSULTAR_ANOTACION_POR_ORDEN = "CONSULTAR_ANOTACION_POR_ORDEN";
    private Turno turno;
    private gov.sir.core.negocio.modelo.Usuario usuarioNeg;
    private String respuestaWF;
    private Fase fase;
    private Estacion estacion;
    private SolicitudFolio solicitudFolio;
    private String numMatricula;
    private String uid;
    private Folio folio;
    private Circulo circulo;
    private Proceso proceso;
    private String descripcionNota;
    private Anotacion anotacion;
    private String anho;
    private Liquidacion liquidacion;
    
    private List anotacionCiudadanos;
    
    private List lstAnotaFolioPadre;
    private List lstAnotaFolioHijo;
    
    private List lstAnotaFolioHijoRemove;
	
	private List listaCompletaCiudadanos;
	
	
	private Vector anotaciones;
    
    private Nota nota;
    
    private String ordenAnotacion;
    
    private String[] lstMatriculasHijas;
    
    private String[] lstAnotacionesHijas;
    
    private String matruculaUpdate;
    
    private String anotacionUpdate;
    
    
    public String getOrdenAnotacion() {
		return ordenAnotacion;
	}

	public void setOrdenAnotacion(String ordenAnotacion) {
		this.ordenAnotacion = ordenAnotacion;
	}

	/** @param usuario */
    public EvnAntiguoSistema(Usuario usuario) {
        super(usuario);
        // TODO Auto-generated constructor stub
    }

    /** @param usuario
     /** @param tipoEvento */
    public EvnAntiguoSistema(Usuario usuario, String tipoEvento) {
        super(usuario, tipoEvento);
        // TODO Auto-generated constructor stub
    }

    /**
     *  @param usuario
    /** @param tipoEvento
    /** @param turno
    /** @param fase
    /** @param usuarioNeg
    /** @param respuestaWF
     */
    public EvnAntiguoSistema(Usuario usuario, String tipoEvento, Turno turno, Fase fase, gov.sir.core.negocio.modelo.Usuario usuarioNeg, String respuestaWF) {
        super(usuario,tipoEvento);
        this.turno=turno;
        this.usuarioNeg=usuarioNeg;
        this.respuestaWF=respuestaWF;
        this.fase=fase;
    }

    
    /**
     *  @param usuario
    /** @param tipoEvento
    /** @param turno
    /** @param fase
    /** @param estacion
    /** @param usuarioNeg
    /** @param respuestaWF
     */
    public EvnAntiguoSistema(Usuario usuario, String tipoEvento, Turno turno, Fase fase, Estacion estacion, gov.sir.core.negocio.modelo.Usuario usuarioNeg, String respuestaWF) {
        super(usuario,tipoEvento);
        this.turno=turno;
        this.usuarioNeg=usuarioNeg;
        this.estacion=estacion;
        this.respuestaWF=respuestaWF;
        this.fase=fase;
    }

    public EvnAntiguoSistema(Usuario usuario, String tipoEvento, Turno turno, Fase fase, Estacion estacion, gov.sir.core.negocio.modelo.Usuario usuarioNeg, Circulo circulo, Proceso proceso, String descripcionNota, String respuestaWF) {
        super(usuario,tipoEvento);
        this.turno=turno;
        this.usuarioNeg=usuarioNeg;
        this.estacion=estacion;
        this.respuestaWF=respuestaWF;
        this.fase=fase;
        this.circulo=circulo;
        this.descripcionNota=descripcionNota;
        this.proceso=proceso;
    }
    
    /**
     *  @param usuario
    /** @param tipoEvento
    /** @param numMatricula
    /** @param turno
    /** @param usuarioNeg
     */
    public EvnAntiguoSistema(Usuario usuario, String tipoEvento, String numMatricula, Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
        super(usuario, tipoEvento);
        this.numMatricula=numMatricula;
        this.turno=turno;
        this.usuarioNeg=usuarioNeg;
    }

    /**
     *  @param usuario
    /** @param tipoEvento
    /** @param numMatricula
    /** @param turno
    /** @param usuarioNeg
    /** @param uid
     */
    public EvnAntiguoSistema(Usuario usuario, String tipoEvento, String numMatricula, Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioNeg, String uid) {
        super(usuario, tipoEvento);
        this.numMatricula=numMatricula;
        this.turno=turno;
        this.usuarioNeg=usuarioNeg;
        this.uid=uid;
    }
    
    /**
     *  @param usuario
    /** @param tipoEvento
    /** @param solicitudFolio
    /** @param turno
    /** @param usuarioNeg
    /** @param uid
     */
    public EvnAntiguoSistema(Usuario usuario, String tipoEvento, SolicitudFolio solicitudFolio, Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioNeg, String uid) {
        super(usuario, tipoEvento);
        this.solicitudFolio=solicitudFolio;
        this.turno=turno;
        this.usuarioNeg=usuarioNeg;
        this.uid=uid;
    }

    /**
     *  @param usuario
    /** @param tipoEvento
    /** @param folio
    /** @param usuarioNeg
     */
    public EvnAntiguoSistema(Usuario usuario, String tipoEvento, Folio folio, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
        super(usuario, tipoEvento);
        this.usuarioNeg=usuarioNeg;
        this.folio=folio;
    }
    
	public EvnAntiguoSistema(Usuario usuario , Folio folio, Anotacion anotacion, gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
		super(usuario,CANCELAR_ANOTACION);
		this.folio=folio;
		this.anotacion=anotacion;
		this.usuarioNeg=usuarioNeg;
	}
    
    public EvnAntiguoSistema(Usuario usuario, String tipoEvento, Folio folio) {
        super(usuario, tipoEvento);
        this.folio=folio;
    }
    
	/**
	 *  @param usuario
	/** @param tipoEvento
	/** @param folio
	/** @param usuarioNeg
	 */
	public EvnAntiguoSistema(Usuario usuario, String tipoEvento, Folio folio, String anho) {
		super(usuario, tipoEvento);
		this.anho = anho;
		this.folio=folio;
	}

    public String getRespuestaWF() {
        return respuestaWF;
    }

    public Turno getTurno() {
        return turno;
    }

    public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
        return usuarioNeg;
    }
    
    public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
    	this.usuarioNeg = usuarioNeg;
    }

    public Fase getFase() {
        return fase;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public String getNumMatricula() {
        return numMatricula;
    }

    public String getUid() {
        return uid;
    }

    public SolicitudFolio getSolicitudFolio() {
        return solicitudFolio;
    }

    public Folio getFolio() {
        return folio;
    }

    public Circulo getCirculo() {
        return circulo;
    }

    public String getDescripcionNota() {
        return descripcionNota;
    }

    public Proceso getProceso() {
        return proceso;
    }

	/**
	 * @return
	 */
	public Anotacion getAnotacion() {
		return anotacion;
	}

	/**
	 * @param turno
	 */
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	/**
	 * @return
	 */
	public String getAnho() {
		return anho;
	}

	/**
	 * @param circulo
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
		
	}

	/**
	 * @return
	 */
	public Nota getNota() {
		return nota;
	}

	/**
	 * @param nota
	 */
	public void setNota(Nota nota) {
		this.nota = nota;
	}

	/**
	 * @param string
	 */
	public void setRespuestaWF(String string) {
		respuestaWF = string;
	}

	public void setLiquidacion(Liquidacion liq) {
		liquidacion = liq;
	}

	public Liquidacion getLiquidacion() {
		return liquidacion;
	}
	
	public void setFase(Fase fase) {
        this.fase = fase;
    }

	public List getAnotacionCiudadanos() {
		return anotacionCiudadanos;
	}

	public void setAnotacionCiudadanos(List anotacionCiudadanos) {
		this.anotacionCiudadanos = anotacionCiudadanos;
	}

	public List getListaCompletaCiudadanos() {
		return listaCompletaCiudadanos;
	}

	public void setListaCompletaCiudadanos(List listaCompletaCiudadanos) {
		this.listaCompletaCiudadanos = listaCompletaCiudadanos;
	}

	public Vector getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(Vector anotaciones) {
		this.anotaciones = anotaciones;
	}

	public String[] getLstAnotacionesHijas() {
		return lstAnotacionesHijas;
	}

	public void setLstAnotacionesHijas(String[] lstAnotacionesHijas) {
		this.lstAnotacionesHijas = lstAnotacionesHijas;
	}

	public String[] getLstMatriculasHijas() {
		return lstMatriculasHijas;
	}

	public void setLstMatriculasHijas(String[] lstMatriculasHijas) {
		this.lstMatriculasHijas = lstMatriculasHijas;
	}

	public List getLstAnotaFolioHijo() {
		return lstAnotaFolioHijo;
	}

	public void setLstAnotaFolioHijo(List lstAnotaFolioHijo) {
		this.lstAnotaFolioHijo = lstAnotaFolioHijo;
	}

	public List getLstAnotaFolioPadre() {
		return lstAnotaFolioPadre;
	}

	public void setLstAnotaFolioPadre(List lstAnotaFolioPadre) {
		this.lstAnotaFolioPadre = lstAnotaFolioPadre;
	}

	public String getAnotacionUpdate() {
		return anotacionUpdate;
	}

	public void setAnotacionUpdate(String anotacionUpdate) {
		this.anotacionUpdate = anotacionUpdate;
	}

	public String getMatruculaUpdate() {
		return matruculaUpdate;
	}

	public void setMatruculaUpdate(String matruculaUpdate) {
		this.matruculaUpdate = matruculaUpdate;
	}

	
	public List getLstAnotaFolioHijoRemove() {
		return lstAnotaFolioHijoRemove;
	}

	public void setLstAnotaFolioHijoRemove(List lstAnotaFolioHijoRemove) {
		this.lstAnotaFolioHijoRemove = lstAnotaFolioHijoRemove;
	}
}

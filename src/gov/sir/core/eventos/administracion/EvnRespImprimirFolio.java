package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Circulo;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas
 * con el traslado de turnos entre usuarios de un mismo círculo
 * @author jmendez
 */
public class EvnRespImprimirFolio extends EvnSIRRespuesta {

	/**Esta constante se utiliza  para identificar el evento de impresión satisfactoria de 
	 * un folio */
	public static final String IMPRESION_FOLIO_OK = "IMPRESION_FOLIO_OK";

	/**Esta constante se utiliza  para identificar el evento de impresión satisfactoria de 
		 * un CERTIFICADO */
	public static final String IMPRESION_CERTIFICADO_OK = "IMPRESION_CERTIFICADO_OK";
	
	/** Constante que identifica la acción de reimprimir un  RECIBO  */
	public static final String REIMPRIMIR_RECIBO = "REIMPRIMIR_RECIBO";

	/** Constante que identifica la acción de reimprimir un  RECIBO  */
	public static final String REIMPRIMIR_CONSULTA_OK = "REIMPRIMIR_CONSULTA_OK";
	
	public static final String OBTENER_IMPRESORAS_CIRCULO = "OBTENER_IMPRESORAS_CIRCULO";
    
    public static final String OBTENER_ULTIMO_TURNO_IMPRESO_OK = "OBTENER_ULTIMO_TURNO_IMPRESO_OK";
    
	public static final String DETERMINAR_ESTACION = "DETERMINAR_ESTACION";    
	
	public static final String ULTIMO_TURNO_PROCESO = "ULTIMO_TURNO_PROCESO";
        
        public static final String ULTIMA_SOLICITUD_LIQUIDACION = "ULTIMA_SOLICITUD_LIQUIDACION";
        
    /** Constante que identifica la accion agregar_de_archivo_ok */
    public static final String AGREGAR_DE_ARCHIVO = "AGREGAR_DE_ARCHIVO";
    
    /** Constante que identifica la accion programar tarea */
    public static final String PROGRAMAR_TAREA_OK = "PROGRAMAR_TAREA_OK";
    
    private String nombreArchivo;
    
    private StringBuffer listaFolios;
    
    String exito;
    
    String imp_masiva_simple_folio_ftp;
	/** Circulo asociado */
    private Circulo cir;
    private String turno;
    private List turnosAsociados;
    
    /**
    * Lista de notas informativas asociadas con la reimpresión de certificados.
    */
    private List listaNotasReimpresion;
    
    /**Lista con las posibles esación para reimprimir*/
    List estaciones = null;
	
    /**
	 * @param payload
	 */
	public EvnRespImprimirFolio(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespImprimirFolio(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}

        /**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespImprimirFolio(Object payload, String exito, String tipoEvento) {
            super(payload, tipoEvento);
            this.exito = exito;
	}
        
        /**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespImprimirFolio(Object payload, StringBuffer listaFolios, String nombreArchvio, String tipoEvento) {
            super(payload, tipoEvento);
            this.nombreArchivo = nombreArchvio;
            this.listaFolios = listaFolios;
	}

        public String getExito() {
            return exito;
        }

        public void setExito(String exito) {
            this.exito = exito;
        }

    public String getImp_masiva_simple_folio_ftp() {
        return imp_masiva_simple_folio_ftp;
    }

    public void setImp_masiva_simple_folio_ftp(String imp_masiva_simple_folio_ftp) {
        this.imp_masiva_simple_folio_ftp = imp_masiva_simple_folio_ftp;
    }
	
	public Circulo getCirculo() {
		return cir;
	}

	public void setCirculo(Circulo cir) {
		this.cir = cir;
	}

	/**
	 * @return
	 */
	public List getListaNotasReimpresion() {
		return listaNotasReimpresion;
	}

	/**
	 * @param list
	 */
	public void setListaNotasReimpresion(List list) {
		listaNotasReimpresion = list;
	}

	/**
	 * @return
	 */
	public List getEstaciones() {
		return estaciones;
	}

	/**
	 * @param list
	 */
	public void setEstaciones(List list) {
		estaciones = list;
	}

	/**
	 * @return Returns the turno.
	 */
	public String getTurno() {
		return turno;
	}
	/**
	 * @param turno The turno to set.
	 */
	public void setTurno(String turno) {
		this.turno = turno;
	}
	/**
	 * @return Returns the turnosAsociados.
	 */
	public List getTurnosAsociados() {
		return turnosAsociados;
	}
	/**
	 * @param turnosAsociados The turnosAsociados to set.
	 */
	public void setTurnosAsociados(List turnosAsociados) {
		this.turnosAsociados = turnosAsociados;
	}

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public StringBuffer getListaFolios() {
        return listaFolios;
    }

    public void setListaFolios(StringBuffer listaFolios) {
        this.listaFolios = listaFolios;
    }
    }

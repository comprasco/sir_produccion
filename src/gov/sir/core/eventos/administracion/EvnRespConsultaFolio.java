package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas
 * con la consulta de folios.
 * @author ppabon
 */
public class EvnRespConsultaFolio extends EvnSIRRespuesta {

	/**Esta constante se utiliza  para identificar el evento de impresión satisfactoria de 
	 * un folio */
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";
	
	/**Esta constante se utiliza  para identificar el evento de una anulacion satisfactoria de 
	 * un folio */
	public static final String ANULAR_FOLIO = "ANULAR_FOLIO";
	

	/**Esta constante se utiliza  para identificar el evento de impresión satisfactoria de 
	 * un folio */
	public static final String CONSULTAR_FOLIO_REABRIR_OK = "CONSULTAR_FOLIO_REABRIR_OK";
	
	/**Esta constante se utiliza  para indicar que se desbloqueo el folio ok */
	public static final String DESBLOQUEO_FOLIO_RESTIRUCION_OK = "DESBLOQUEO_FOLIO_RESTIRUCION_OK";


	private List foliosPadre;
	private List foliosHijo;
        private List historialAreas;
	private List gravamenes;
	private List medidasCautelares;
	private List salvedadesAnotaciones;
	private List cancelaciones;
	private long numeroAnotaciones;
	private Folio folio;
	private boolean esMayorExtension; 
	private Turno TurnoTramite;
	private Turno TurnoDeuda;
	private Usuario usuarioBloqueo;
	private List falsaTradicion;
	private List anotacionesInvalidas;
	private String mensaje;
	private List anotacionesPatrimonioFamiliar;
	private List anotacionesAfectacionVivienda;
	private List turnosFolioTramite;
        /*
         * @author      : Julio Alcázar Rivas
         * @change      : nuevos atributos para el manejo del proceso traslado folio
         * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
         */
        private Traslado trasladoDestino;
        private Traslado trasladoOrigen;
        Circulo circuloDestino;
        Circulo circuloOrigen;

        public Traslado getTrasladoDestino() {
            return trasladoDestino;
        }

        public void setTrasladoDestino(Traslado trasladoDestino) {
            this.trasladoDestino = trasladoDestino;
        }

        public Traslado getTrasladoOrigen() {
            return trasladoOrigen;
        }

        public void setTrasladoOrigen(Traslado trasladoOrigen) {
            this.trasladoOrigen = trasladoOrigen;
        }

        public Circulo getCirculoDestino() {
            return circuloDestino;
        }

        public void setCirculoDestino(Circulo circuloDestino) {
            this.circuloDestino = circuloDestino;
        }

        public Circulo getCirculoOrigen() {
            return circuloOrigen;
        }

        public void setCirculoOrigen(Circulo circuloOrigen) {
            this.circuloOrigen = circuloOrigen;
        }

        public List getHistorialAreas() {
            return historialAreas;
        }

        public void setHistorialAreas(List historialAreas) {
            this.historialAreas = historialAreas;
        }
        
        
	
    /**
     * Información de segregaciones y matrices Objeto FolioDerivado
     */
    private List foliosDerivadoPadre;
    private List foliosDerivadoHijo;


	/**
	 * @param payload
	 */
	public EvnRespConsultaFolio(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespConsultaFolio(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}

	public EvnRespConsultaFolio(List historialAreas, Folio folio, List foliosPadre, List foliosHijo, List gravamenes, List medidasCautelares, List falsaTradicion, List anotacionesInvalidas, List salvedadesAnotaciones , List cancelaciones, long numeroAnotaciones, boolean esMayorExtension, String tipoEvento, Turno turnoDeuda, Turno turnoTramite, Usuario usuarioBloqueo) {
		this(folio,tipoEvento);
                this.historialAreas = historialAreas;
		this.folio = folio;
		this.foliosHijo = foliosHijo;
		this.foliosPadre = foliosPadre;
		this.gravamenes = gravamenes;
		this.medidasCautelares = medidasCautelares;
		this.falsaTradicion = falsaTradicion;
		this.anotacionesInvalidas = anotacionesInvalidas;
		this.salvedadesAnotaciones = salvedadesAnotaciones;
		this.cancelaciones = cancelaciones;		
		this.numeroAnotaciones = numeroAnotaciones;
		this.esMayorExtension = esMayorExtension;
		this.TurnoDeuda = turnoDeuda;
		this.TurnoTramite = turnoTramite;
		this.usuarioBloqueo = usuarioBloqueo;
	}	
	
	

	/**
	 * @return
	 */
	public List getCancelaciones() {
		return cancelaciones;
	}

	/**
	 * @return
	 */
	public boolean isEsMayorExtension() {
		return esMayorExtension;
	}

	/**
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @return
	 */
	public List getFoliosHijo() {
		return foliosHijo;
	}

	/**
	 * @return
	 */
	public List getFoliosPadre() {
		return foliosPadre;
	}

	/**
	 * @return
	 */
	public List getGravamenes() {
		return gravamenes;
	}

	/**
	 * @return
	 */
	public List getMedidasCautelares() {
		return medidasCautelares;
	}

	/**
	 * @return
	 */
	public long getNumeroAnotaciones() {
		return numeroAnotaciones;
	}

	/**
	 * @return
	 */
	public List getSalvedadesAnotaciones() {
		return salvedadesAnotaciones;
	}

	/**
	 * @return
	 */
	public Turno getTurnoDeuda() {
		return TurnoDeuda;
	}

	/**
	 * @return
	 */
	public Turno getTurnoTramite() {
		return TurnoTramite;
	}

	/**
	 * @return
	 */
	public Usuario getUsuarioBloqueo() {
		return usuarioBloqueo;
	}

	/**
	 * @return
	 */
	public List getFalsaTradicion() {
		return falsaTradicion;
	}

	/**
	 * @param list
	 */
	public void setFalsaTradicion(List list) {
		falsaTradicion = list;
	}

	/**
	 * @return
	 */
	public List getAnotacionesInvalidas() {
		return anotacionesInvalidas;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List getAnotacionesAfectacionVivienda() {
		return anotacionesAfectacionVivienda;
	}

	public void setAnotacionesAfectacionVivienda(List anotacionesAfectacionVivienda) {
		this.anotacionesAfectacionVivienda = anotacionesAfectacionVivienda;
	}

	public List getAnotacionesPatrimonioFamiliar() {
		return anotacionesPatrimonioFamiliar;
	}

	public void setAnotacionesPatrimonioFamiliar(List anotacionesPatrimonioFamiliar) {
		this.anotacionesPatrimonioFamiliar = anotacionesPatrimonioFamiliar;
	}

	public List getFoliosDerivadoHijo() {
		return foliosDerivadoHijo;
	}

	public void setFoliosDerivadoHijo(List foliosDerivadoHijo) {
		this.foliosDerivadoHijo = foliosDerivadoHijo;
	}

	public List getFoliosDerivadoPadre() {
		return foliosDerivadoPadre;
	}

	public void setFoliosDerivadoPadre(List foliosDerivadoPadre) {
		this.foliosDerivadoPadre = foliosDerivadoPadre;
	}

	public List getTurnosFolioTramite() {
		return turnosFolioTramite;
	}

	public void setTurnosFolioTramite(List turnosFolioTramite) {
		this.turnosFolioTramite = turnosFolioTramite;
	}

}

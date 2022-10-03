package gov.sir.core.eventos.registro;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Folio;
/**
 * @author     : Carlos Torres
 * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
 */
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

/**
 * @author mmunoz
 */
public class EvnRespConsultaFolio extends EvnSIRRespuesta {

	private Folio folio;

    /**
     * Información de segregaciones y matrices Objeto FolioDerivado
     */
    private List foliosDerivadoPadre;
    private List foliosDerivadoHijo;
    private List historialAreas;
    private List anotacionesPatrimonioFamiliar;
    private List anotacionesAfectacionVivienda;
    private List turnosFolioTramite;

	private List foliosHijo;

	private List foliosPadre;

	private List gravamenes;

	private List medidasCautelares;

	private List falsaTradicion;

	private List anotacionesInvalidas;

	private List salvedadesAnotaciones;

	private List cancelaciones;

	private long numeroAnotaciones;

	private boolean esMayorExtension;

	private Turno TurnoDeuda;

	private Turno TurnoTramite;

	private Usuario usuarioBloqueo;
	
	public static final String CONSULTAR_FOLIO = "CONSULTAR_FOLIO";
        
         /**
          * @author     : Carlos Torres
          * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
          */
        private Traslado trasladoDestino;
        private Traslado trasladoOrigen;
        Circulo circuloDestino;
        Circulo circuloOrigen;
    
	/**
	 * @param folio
	 */
	public EvnRespConsultaFolio(Folio folio) {
		super(folio);
		this.folio = folio;
	}
	
	public EvnRespConsultaFolio(Folio folio, List foliosPadre, List foliosHijo, List gravamenes, List medidasCautelares, List falsaTradicion, List anotacionesInvalidas, List salvedadesAnotaciones , List cancelaciones, long numeroAnotaciones, boolean esMayorExtension, String tipoEvento, Turno turnoDeuda, Turno turnoTramite, Usuario usuarioBloqueo) {
		super(folio,tipoEvento);
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
	public Folio getFolio() {
		return folio;
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

	public List getAnotacionesPatrimonioFamiliar() {
		return anotacionesPatrimonioFamiliar;
	}

	public void setAnotacionesPatrimonioFamiliar(List anotacionesPatrimonioFamiliar) {
		this.anotacionesPatrimonioFamiliar = anotacionesPatrimonioFamiliar;
	}

	public List getAnotacionesAfectacionVivienda() {
		return anotacionesAfectacionVivienda;
	}

	public void setAnotacionesAfectacionVivienda(List anotacionesAfectacionVivienda) {
		this.anotacionesAfectacionVivienda = anotacionesAfectacionVivienda;
	}

	public List getTurnosFolioTramite() {
		return turnosFolioTramite;
	}

	public void setTurnosFolioTramite(List turnosFolioTramite) {
		this.turnosFolioTramite = turnosFolioTramite;
	}

	public List getFoliosHijo() {
		return foliosHijo;
	}

	public void setFoliosHijo(List foliosHijo) {
		this.foliosHijo = foliosHijo;
	}

        public List getHistorialAreas() {
            return historialAreas;
        }

        public void setHistorialAreas(List historialAreas) {
            this.historialAreas = historialAreas;
        }
        
	public List getFoliosPadre() {
		return foliosPadre;
	}

	public void setFoliosPadre(List foliosPadre) {
		this.foliosPadre = foliosPadre;
	}

	public List getGravamenes() {
		return gravamenes;
	}

	public void setGravamenes(List gravamenes) {
		this.gravamenes = gravamenes;
	}

	public List getMedidasCautelares() {
		return medidasCautelares;
	}

	public void setMedidasCautelares(List medidasCautelares) {
		this.medidasCautelares = medidasCautelares;
	}

	public List getFalsaTradicion() {
		return falsaTradicion;
	}

	public void setFalsaTradicion(List falsaTradicion) {
		this.falsaTradicion = falsaTradicion;
	}

	public List getSalvedadesAnotaciones() {
		return salvedadesAnotaciones;
	}

	public void setSalvedadesAnotaciones(List salvedadesAnotaciones) {
		this.salvedadesAnotaciones = salvedadesAnotaciones;
	}

	public long getNumeroAnotaciones() {
		return numeroAnotaciones;
	}

	public void setNumeroAnotaciones(long numeroAnotaciones) {
		this.numeroAnotaciones = numeroAnotaciones;
	}

	public Turno getTurnoDeuda() {
		return TurnoDeuda;
	}

	public void setTurnoDeuda(Turno turnoDeuda) {
		TurnoDeuda = turnoDeuda;
	}

	public Turno getTurnoTramite() {
		return TurnoTramite;
	}

	public void setTurnoTramite(Turno turnoTramite) {
		TurnoTramite = turnoTramite;
	}

	public Usuario getUsuarioBloqueo() {
		return usuarioBloqueo;
	}

	public void setUsuarioBloqueo(Usuario usuarioBloqueo) {
		this.usuarioBloqueo = usuarioBloqueo;
	}

	public List getAnotacionesInvalidas() {
		return anotacionesInvalidas;
	}

	public void setAnotacionesInvalidas(List anotacionesInvalidas) {
		this.anotacionesInvalidas = anotacionesInvalidas;
	}

	public List getCancelaciones() {
		return cancelaciones;
	}

	public void setCancelaciones(List cancelaciones) {
		this.cancelaciones = cancelaciones;
	}

	public boolean isEsMayorExtension() {
		return esMayorExtension;
	}

	public void setEsMayorExtension(boolean esMayorExtension) {
		this.esMayorExtension = esMayorExtension;
	}

	public void setFolio(Folio folio) {
		this.folio = folio;
	}
        /**
         * @author     : Carlos Torres
         * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
         */
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
        
}

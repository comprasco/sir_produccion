package gov.sir.core.eventos.registro;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.WebSegEng;

import java.util.Hashtable;
import java.util.List;

/**
 * @author mmunoz, jfrias
 */
public class EvnRespCalificacion extends EvnSIRRespuesta {

	public static final String SEGREGACION_MASIVA = "SEGREGACION_MASIVA";
	public static final String DESASOCIAR_FOLIOS = "DESASOCIAR_FOLIOS";
	public static final String FOLIO_TEMPORAL = "FOLIO_TEMPORAL";
	public static final String DELEGAR_CONFRONTACION = "DELEGAR_CONFRONTACION";
	public static final String CONFRONTACION = "CONFRONTACION";
	public static final String APROBAR_MAYOR_VALOR = "APROBAR_MAYOR_VALOR";
	public static final String REGISTRAR_PAGO_EXCESO = "REGISTRAR_PAGO_EXCESO";
        public static final String VISUALIZAR_PDF = "VISUALIZAR_PDF";
	public static final String CERRAR_FOLIO = "CERRAR_FOLIO";
	public static final String AVANZAR_ENTREGA = "AVANZAR_ENTREGA";
	public static final String VERIFICACION_ACTOS_REGISTRO = "VERIFICACION_ACTOS_REGISTRO";
	public static final String CONSULTA_TEMPORAL = "CONSULTA_TEMPORAL";
	public static final String AVANZAR_CORRECCIONES = "AVANZAR_CORRECCIONES";
	public static final String CONSULTAR_SEG_ENG_EXISTENTES ="CONSULTAR_SEG_ENG_EXISTENTES";
	public static final String VALIDAR_TURNO_PARA_CALIFICACION="VALIDAR_TURNO_PARA_CALIFICACION";	
	public static final String CONSULTAR_PROPIETARIOS_FOLIO ="CONSULTAR_PROPIETARIOS_FOLIO";
	public static final String VOLVER_AGREGAR_CIUDADANOS ="VOLVER_AGREGAR_CIUDADANOS";
	public static final String PAGE_CALIFICACION_BTNDESHACERCAMBIOS_ACTION ="PAGE_CALIFICACION_BTNDESHACERCAMBIOS_ACTION";
	public static final String ENVIAR_RESPUESTA = "ENVIAR_RESPUESTA";
	private List foliosDerivados;
	private List foliosPadre;
	private List foliosHijo;
        private List historialAreas;
	private List gravamenes;
	private List medidasCautelares;
	private List salvedadesAnotaciones;
	private List cancelaciones;
	private List dirTemporales=null;
	private long numeroAnotaciones;
	private Folio folio;
	private boolean esMayorExtension;
	private boolean validoAprobarCalificacion = false;
	private List aTemporales;
	private Hashtable validacionAnotacionesTemporales;
	private Turno turno;
	private boolean validacionVerificacionActos;
	private String plazoVencimiento;
	private String linderoTemporal;
	private List foliosSinActualizacionNomenclatura;
	private List falsaTradicion;
	private List anotacionesInvalidas;
	private String nextOrden;	
	private WebSegEng webSegEng;
	private List propietariosFolios;
	private List anotacionesPatrimonioFamiliar;
	private List anotacionesAfectacionVivienda;
	private List lstFoliosCerrados = null;
        private List listaRespuestaTrams = null;
	/**Anotaciones que se adicionaron en el folio*/
	private List anotaciones = null;
	/**Lista de anotaciones agregadas de un folio. Por cada una que se agregue, se muestra
	 * el listado de anotaciones en la parte superior de la patanlla de creacion de anotaciones*/
	private List anotacionesAgregadas = null;
	long numeroSegregacionesVacias;

	
	//private boolean habilitarEdicionNomenclatura;
	
	private boolean habilitarEdicionCodCatastral;
	private boolean habilitarEdicionDireccion;
	private boolean habilitarEdicionLinderos;
	
	private List listaCompletaCiudadanos;
	/**lista de direcciones resultante en caso de que se modifiquen o se eliminen*/
	private boolean eliminarDireccionesTMP = false;
	
	
	

        // habilitar la opcion de reimpresion-sellos
        protected boolean optionReproduccionSellos_Enabled;
        
    private List foliosDerivadoHijo;
    
    private List foliosDerivadoPadre;
    
	/**
	 * @param object
	 */
	public EvnRespCalificacion(List foliosDerivados) {
		super(foliosDerivados, EvnRespCalificacion.SEGREGACION_MASIVA);
		this.foliosDerivados = foliosDerivados;
	}

	public EvnRespCalificacion(Folio folio) {
		super(folio, EvnRespCalificacion.FOLIO_TEMPORAL);
		this.folio = folio;
	}

	public EvnRespCalificacion(Folio folio,List aTemporales) {
		super(folio, EvnRespCalificacion.FOLIO_TEMPORAL);
		this.folio = folio;
		this.aTemporales=aTemporales;
	}

	public EvnRespCalificacion(Object payload, String tipoEvento) {
		super(payload,tipoEvento);

	}


	public EvnRespCalificacion(String mensaje) {
		super((mensaje.equals(CONFRONTACION)? DELEGAR_CONFRONTACION:""),(mensaje.equals(CONFRONTACION)? DELEGAR_CONFRONTACION:""));

	}

	public EvnRespCalificacion(boolean valida) {
		super(EvnCalificacion.VALIDAR_APROBAR_CALIFICACION);
		this.setValidoAprobarCalificacion(valida);
	}

	public EvnRespCalificacion(List historialAreas, Folio folio, List foliosPadre, List foliosHijo, List gravamenes, List medidasCautelares,  List falsaTradicion, List anotacionesInvalidas, List salvedadesAnotaciones , List cancelaciones, long numeroAnotaciones, boolean esMayorExtension, String linderoTemporal) {
		this(folio);
                this.historialAreas = historialAreas;
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
		this.linderoTemporal = linderoTemporal;	
	}

	public EvnRespCalificacion(List historialAreas, Folio folio, List foliosPadre, List foliosHijo, List gravamenes, List medidasCautelares,  List falsaTradicion, List anotacionesInvalidas, List salvedadesAnotaciones , List cancelaciones, long numeroAnotaciones, boolean esMayorExtension, List aTemporal, String linderoTemporal) {
		this(folio);
                this.historialAreas = historialAreas;
		this.foliosHijo = foliosHijo;
		this.foliosPadre = foliosPadre;
		this.gravamenes = gravamenes;
		this.medidasCautelares = medidasCautelares;
		this.salvedadesAnotaciones = salvedadesAnotaciones;
		this.falsaTradicion = falsaTradicion;
		this.anotacionesInvalidas = anotacionesInvalidas;
		this.cancelaciones = cancelaciones;
		this.numeroAnotaciones = numeroAnotaciones;
		this.esMayorExtension = esMayorExtension;
		this.aTemporales = aTemporal;
		this.linderoTemporal = linderoTemporal;
	}

        public List getListaRespuestaTrams() {
            return listaRespuestaTrams;
        }

        public void setListaRespuestaTrams(List listaRespuestaTrams) {
            this.listaRespuestaTrams = listaRespuestaTrams;
        }

        /**
         * @return
         */
        public List getFoliosDerivados() {
            return foliosDerivados;
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
	public List getListATemp() {
		return aTemporales;
	}

	public boolean getMayorExtensionFolio() {
		return esMayorExtension;
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
	public List getFoliosHijo() {
		return foliosHijo;
	}

        public List getHistorialAreas() {
            return historialAreas;
        }
        /**
         * 
         * @param historialAreas 
         */
        public void setHistorialAreas(List historialAreas) {
            this.historialAreas = historialAreas;
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
	public boolean isValidoAprobarCalificacion() {
		return validoAprobarCalificacion;
	}

	/**
	 * @param b
	 */
	public void setValidoAprobarCalificacion(boolean b) {
		validoAprobarCalificacion = b;
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
	public List getSalvedadesAnotaciones() {
		return salvedadesAnotaciones;
	}

	/**
	 * @return
	 */
	public Hashtable getValidacionAnotacionesTemporales() {
		return validacionAnotacionesTemporales;
	}

	/**
	 * @param hashtable
	 */
	public void setValidacionAnotacionesTemporales(Hashtable hashtable) {
		validacionAnotacionesTemporales = hashtable;
	}

	/**
	 * @return
	 */
	public Turno getTurno() {
		return turno;
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
	public boolean isValidacionVerificacionActos() {
		return validacionVerificacionActos;
	}

    public boolean isOptionReproduccionSellos_Enabled() {
        return optionReproduccionSellos_Enabled;
    }

    /**
	 * @param b
	 */
	public void setValidacionVerificacionActos(boolean b) {
		validacionVerificacionActos = b;
	}

	/**
	 * @return
	 */
/*	public boolean isHabilitarEdicionNomenclatura() {
		return habilitarEdicionNomenclatura;
	}*/

	/**
	 * @param b
	 */
/*	public void setHabilitarEdicionNomenclatura(boolean b) {
		habilitarEdicionNomenclatura = b;
	}*/

    public void setOptionReproduccionSellos_Enabled(boolean
            optionReproduccionSellos_Enabled) {
        this.optionReproduccionSellos_Enabled =
                optionReproduccionSellos_Enabled;
    }

	/**
	 * @return
	 */
	public String getPlazoVencimiento() {
		return plazoVencimiento;
	}

	/**
	 * @param string
	 */
	public void setPlazoVencimiento(String string) {
		plazoVencimiento = string;
	}

	/**
	 * @return
	 */
	public boolean isHabilitarEdicionCodCatastral() {
		return habilitarEdicionCodCatastral;
	}

	/**
	 * @param b
	 */
	public void setHabilitarEdicionCodCatastral(boolean b) {
		habilitarEdicionCodCatastral = b;
	}

	/**
	 * @return
	 */
	public boolean isHabilitarEdicionDireccion() {
		return habilitarEdicionDireccion;
	}

	/**
	 * @param b
	 */
	public void setHabilitarEdicionDireccion(boolean b) {
		habilitarEdicionDireccion = b;
	}

	/**
	 * @return
	 */
	public boolean isHabilitarEdicionLinderos() {
		return habilitarEdicionLinderos;
	}

	/**
	 * @param b
	 */
	public void setHabilitarEdicionLinderos(boolean b) {
		habilitarEdicionLinderos = b;
	}

	/**
	 * @return
	 */
	public String getLinderoTemporal() {
		return linderoTemporal;
	}

	/**
	 * @param string
	 */
	public void setLinderoTemporal(String string) {
		linderoTemporal = string;
	}

	/**
	 * @return
	 */
	public List getFoliosSinActualizacionNomenclatura() {
		return foliosSinActualizacionNomenclatura;
	}

	/**
	 * @param list
	 */
	public void setFoliosSinActualizacionNomenclatura(List list) {
		foliosSinActualizacionNomenclatura = list;
	}

	/**
	 * @return
	 */
	public List getAnotacionesInvalidas() {
		return anotacionesInvalidas;
	}

	/**
	 * @return
	 */
	public List getFalsaTradicion() {
		return falsaTradicion;
	}

	/**
	 * @return
	 */
	public String getNextOrden() {
		return nextOrden;
	}

	/**
	 * @param string
	 */
	public void setNextOrden(String string) {
		nextOrden = string;
	}

	/**
	 * @return
	 */
	public WebSegEng getWebSegEng() {
		return webSegEng;
	}

	/**
	 * @param eng
	 */
	public void setWebSegEng(WebSegEng eng) {
		webSegEng = eng;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	public List getPropietariosFolios() {
		return propietariosFolios;
	}

	public void setPropietariosFolios(List propietariosFolios) {
		this.propietariosFolios = propietariosFolios;
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

	public List getListaCompletaCiudadanos() {
		return listaCompletaCiudadanos;
	}

	public void setListaCompletaCiudadanos(List listaCompletaCiudadanos) {
		this.listaCompletaCiudadanos = listaCompletaCiudadanos;
	}

	public List getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(List anotaciones) {
		this.anotaciones = anotaciones;
	}

	public List getAnotacionesAgregadas() {
		return anotacionesAgregadas;
	}

	public void setAnotacionesAgregadas(List anotacionesAgregadas) {
		this.anotacionesAgregadas = anotacionesAgregadas;
	}

	public boolean isEliminarDireccionesTMP() {
		return eliminarDireccionesTMP;
	}

	public void setEliminarDireccionesTMP(boolean eliminarDireccionesTMP) {
		this.eliminarDireccionesTMP = eliminarDireccionesTMP;
	}

	public List getDirTemporales() {
		return dirTemporales;
	}

	public void setDirTemporales(List dirTemporales) {
		this.dirTemporales = dirTemporales;
	}

	public List getLstFoliosCerrados() {
		return lstFoliosCerrados;
	}

	public void setLstFoliosCerrados(List lstFoliosCerrados) {
		this.lstFoliosCerrados = lstFoliosCerrados;
	}
	
	private Folio folioDef;


	public Folio getFolioDef() {
		return folioDef;
	}

	public void setFolioDef(Folio folioDef) {
		this.folioDef = folioDef;
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
	
	public long getNumeroSegregacionesVacias() {
		return numeroSegregacionesVacias;
	}

	public void setNumeroSegregacionesVacias(long numeroSegregacionesVacias) {
		this.numeroSegregacionesVacias = numeroSegregacionesVacias;
	}
        

}

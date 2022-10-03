package gov.sir.core.eventos.comun;

import java.util.List;


/**
 * Esta clase devuelve una lista de anotaciones para que puedan ser mostradas en los diferentes procesos,
 * también el número de anotaciones del folio.
 * @author ppabon
 */
public class EvnRespPaginadorAnotaciones extends EvnSIRRespuesta{

	private List anotaciones;
	private List anotacionesDefinitivas;
	private List anotacionesTemporales;
        private List historialAreas;
	private long totalRegistros;
	private String nombrePaginador;
	private String nombreResultado;
	private int paginaInicial=0;
	
	public static final String CONSULTAR_ANOTACIONES = "CONSULTAR_ANOTACIONES";	
	public static final String CONSULTAR_ANOTACIONES_FOLIO = "CONSULTAR_ANOTACIONES_FOLIO";	
	public static final String CARGAR_PAGINA = "CARGAR_PAGINA";
	private long numeroAnotacionesDefinitivas;
	
	  /**
     * Información de segregaciones y matrices Objeto FolioDerivado
     */
    private List foliosDerivadoPadre;
    private List foliosDerivadoHijo;


	/**
	 * @param payload
	 */
	public EvnRespPaginadorAnotaciones(Object payload) {
		super(payload);
	}

	/**
	 * @param payload
	 * @param tipoEvento
	 */
	public EvnRespPaginadorAnotaciones(Object payload, String tipoEvento) {
		super(payload, tipoEvento);
	}

	public EvnRespPaginadorAnotaciones(List anotaciones, long totalRegistros, String tipoEvento, String nNombrePaginador, String nNombreResultado) {
		super(tipoEvento);
                this.historialAreas = historialAreas;
		this.anotaciones = anotaciones;
		this.totalRegistros = totalRegistros;	
		this.nombrePaginador= nNombrePaginador;
		this.nombreResultado= nNombreResultado;
	}
	
	/**
	 * Evento respuesta donde se guardan llos valores de CONSULTA_ANOTACIONES_FOLIO
         * @param historialAreas
	 * @param anotacionesDefinitivas 
	 * @param anotacionesTemporales
	 * @param totalRegistros
	 * @return
	*/
	public EvnRespPaginadorAnotaciones(List anotacionesDefinitivas, List anotacionesTemporales, long totalRegistros, String tipoEvento, String nNombrePaginador, String nNombreResultado) {
		super(tipoEvento,tipoEvento);
                this.historialAreas = historialAreas;
		this.anotacionesDefinitivas = anotacionesDefinitivas;
		this.anotacionesTemporales = anotacionesTemporales;
		this.totalRegistros = totalRegistros;
		this.nombrePaginador= nNombrePaginador;
		this.nombreResultado= nNombreResultado;	
	}


	/**
	 * @return
	 */
	public List getAnotaciones() {
		return anotaciones;
	}
	
	/**
	 * @return
	 */
	public List getAnotacionesTemporales() {
		return anotacionesTemporales;
	}
		
	/**
	 * @return
	 */
	public List getAnotacionesDefinitivas() {
		return anotacionesDefinitivas;
	}

	/**
	 * @return
	 */
	public long getTotalRegistros() {
		return totalRegistros;
	}
	
	/**
	 * @return
	 */
	public void setNombreResultado(String valor) {
		nombreResultado=valor;
	}

	/**
	 * @return
	 */
	public String getNombreResultado() {
		return nombreResultado;
	}

	/**
	 * @return
	 */
	public void setNombrePaginador(String valor) {
		nombrePaginador=valor;
	}

	/**
	 * @return
	 */
	public String getNombrePaginador() {
		return nombrePaginador;
	}
	
	/**
	 * @return
	 */
	public void setPaginaInicial(int valor) {
		paginaInicial=valor;
	}

	/**
	 * @return
	 */
	public int getPaginaInicial() {
		return paginaInicial;
	}

	/**
	 * @param numeroAnotacionesDefinitivas
	 */
	public void setNumeroAnotacionesDefinitivas(long numeroAnotacionesDefinitivas) {
		this.numeroAnotacionesDefinitivas = numeroAnotacionesDefinitivas;
	}

	/**
	 * @return Returns the numeroAnotacionesDefinitivas.
	 */
	public long getNumeroAnotacionesDefinitivas() {
		return numeroAnotacionesDefinitivas;
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

        public List getHistorialAreas() {
            return historialAreas;
        }

        public void setHistorialAreas(List historialAreas) {
            this.historialAreas = historialAreas;
        }
        
        

}

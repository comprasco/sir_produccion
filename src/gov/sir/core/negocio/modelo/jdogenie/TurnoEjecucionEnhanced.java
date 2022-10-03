package gov.sir.core.negocio.modelo.jdogenie;


/**
 * Almacena los datos correspondientes a la definicion de Turno
 * @author I.Siglo21
 */
public class TurnoEjecucionEnhanced extends Enhanced {
    private String idWorkflow; // pk 
    private String notificacionWF;  
    private String fase; 
    private String estacion;
    private String estado;
	private boolean hasWF=true;
	
    /**
     * Método constructor por defecto de la clase Turno. Crea una nueva instancia de la clase Turno.
     */
    public TurnoEjecucionEnhanced() {

    }
    

    /**
     * Metodo constructor con valores especificos. Crea una nueva instancia de la clase Turno
     * @param id tiene la informacion del identificador del turno
     * @param solicitante tiene la informacion del solicitante que le pertenece el turno
     * @param notasInf tiene todas la informacion acerca de la notas informativas asociadas al turno
     */
    public TurnoEjecucionEnhanced(String idWorkflow, String notificacionWF, String fase, String estacion, String estado, boolean hasWF) {
       this.idWorkflow = idWorkflow;
       this.notificacionWF = notificacionWF;
       this.fase = fase;
       this.estacion = estacion;
       /**
        * @autor Edgar Lora
        * @mantis 0009842
        * @Requerimiento 053_151
        */
       this.estado = estado;
       this.hasWF = hasWF;
    }

    
    public String getEstacion() {
		return estacion;
	}


	public void setEstacion(String estacion) {
		this.estacion = estacion;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getFase() {
		return fase;
	}


	public void setFase(String fase) {
		this.fase = fase;
	}


	public boolean isHasWF() {
		return hasWF;
	}


	public void setHasWF(boolean hasWF) {
		this.hasWF = hasWF;
	}


	public String getIdWorkflow() {
		return idWorkflow;
	}


	public void setIdWorkflow(String idWorkflow) {
		this.idWorkflow = idWorkflow;
	}


	public String getNotificacionWF() {
		return notificacionWF;
	}


	public void setNotificacionWF(String notificacionWF) {
		this.notificacionWF = notificacionWF;
	}
	

}

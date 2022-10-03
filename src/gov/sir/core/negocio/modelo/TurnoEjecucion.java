package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/** Clase que modela la informacion de turnos creados en el sistema  */

public class TurnoEjecucion implements TransferObject {
    private String idWorkflow; // pk 
    private String notificacionWF;  
    private String fase; 
    private String estacion;
    private String estado;
	private boolean hasWF;
	private static final long serialVersionUID = 1L;

	/**
     * Método constructor por defecto de la clase Turno. Crea una nueva instancia de la clase Turno.
     */
    public TurnoEjecucion() {

    }
    

    /**
     * Metodo constructor con valores especificos. Crea una nueva instancia de la clase Turno
     * @param id tiene la informacion del identificador del turno
     * @param solicitante tiene la informacion del solicitante que le pertenece el turno
     * @param notasInf tiene todas la informacion acerca de la notas informativas asociadas al turno
     */
    public TurnoEjecucion(String idWorkflow, String notificacionWF, String fase, String estacion, String estado, boolean hasWF) {
       this.idWorkflow = idWorkflow;
       this.notificacionWF = notificacionWF;
       this.fase = fase;
       this.estacion = estacion;
       this.estacion = estado;
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

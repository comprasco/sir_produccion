package gov.sir.core.eventos.devolucion;

import java.util.ArrayList;
import java.util.List;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Turno;

/**
 * @author mmunoz, jvelez
 */
public class EvnRespDevolucion extends EvnSIRRespuesta {

	/**Esta constante guarda el tipo evento cuando es avanzado un turno*/
	public static final String TURNO_AVANZADO = "TURNO_AVANZADO";

	/**Esta variable guarda la respuesta del avance del turno, true si fue avanzado */
	private boolean fueAvanzado = false;

    /**
    * Turno asociado con el evento de respuesta
    */
    private Turno turnoRespuesta;
	
	/**
	 * Lista que guarda los identificadores de los turnos de certificados asociados con 
	 * la devolución.
	 */
	private List listaCertificadosAsociados = new ArrayList();
	
	
	/**
	 * Lista que guarda los items de chequeo definidos para el proceso de devoluciones.
	 */
	private List listaItemsChequeo = new ArrayList();
	
	/**
	 * Lista que guarda los items de chequeo definidos para el proceso de devoluciones.
	 */
	private List documentoPago;
	
	private List resoluciones;
        
        private String currentStateNotaNotificada;

	
        //    CARLOS
        private List canalRecaudoYcuentas;

	
	/**
	 * Este es el metodo constructor del evento
	 * @param avanzado boolean
	 */
	public EvnRespDevolucion(boolean avanzado) {
		super(new Boolean(avanzado),EvnRespDevolucion.TURNO_AVANZADO);
		this.fueAvanzado = avanzado;
	}

        public EvnRespDevolucion() {
            super(new Object());
        }


	/**
	 * Retorna si fue avanzado o no el turno, true si fue avanzado.
	 * @return boolean
	 */
	public boolean isFueAvanzado() {
		return fueAvanzado;
	}

	/**
	 * @return
	 */
	public List getListaCertificadosAsociados() {
		return listaCertificadosAsociados;
	}

	/**
	 * @param list
	 */
	public void setListaCertificadosAsociados(List list) {
		listaCertificadosAsociados = list;
	}

	/**
	 * @return
	 */
	public List getListaItemsChequeo() {
		return listaItemsChequeo;
	}

	/**
	 * @param list
	 */
	public void setListaItemsChequeo(List list) {
		listaItemsChequeo = list;
	}

	/**
	 * @return
	 */
	public Turno getTurnoRespuesta() {
		return turnoRespuesta;
	}

	/**
	 * @param turno
	 */
	public void setTurnoRespuesta(Turno turno) {
		turnoRespuesta = turno;
	}

	/**
	 * @return
	 */
	public List getDocumentoPago() {
		return documentoPago;
	}

	/**
	 * @param turnoAnterior (Devolucion)
	 */
	public void setDocumentoPago(List docPago) {
		documentoPago = docPago;
	}
	
    /**
	    * Turno asociado con el evento de respuesta
	    */
	    private Turno turnoRespuestaAnterior;


	public Turno getTurnoRespuestaAnterior() {
		return turnoRespuestaAnterior;
	}

	public void setTurnoRespuestaAnterior(Turno turnoRespuestaAnterior) {
		this.turnoRespuestaAnterior = turnoRespuestaAnterior;
	}

	public List getResoluciones() {
		return resoluciones;
	}

	public void setResoluciones(List resoluciones) {
		this.resoluciones = resoluciones;
	}
	    
                
        //    CARLOS
    public List getCanalRecaudoYcuentas(){
        return canalRecaudoYcuentas;
}
    
    public void setCanalRecaudoYcuentas(List canalRecaudoYcuentas){
        this.canalRecaudoYcuentas = canalRecaudoYcuentas;
    }

    public String getCurrentStateNotaNotificada() {
        return currentStateNotaNotificada;
    }

    public void setCurrentStateNotaNotificada(String currentStateNotaNotificada) {
        this.currentStateNotaNotificada = currentStateNotaNotificada;
    }
    
    
	    
}

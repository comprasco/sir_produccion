/*
 * Created on 03-nov-2004
 *
 */
package gov.sir.core.eventos.registro;

import java.util.List;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;



/**
 * Evento de Reparto en el proceso de registro de documentos.
 * @author dcantor
 *
 */
public class EvnReparto extends EvnSIR {
    
    public static final String CREAR_REPARTO = "CREAR_REPARTO";
	public static final String CREAR_REPARTO_RANGO = "CREAR_REPARTO_RANGO";
    
    private Estacion estacion;
    
    private long turnoDesde;
    
    private long turnoHasta;
    
    private Circulo circulo;
    
    private List turnosRepartir;

    /**
     * Constructor de este evento.
     */
    public EvnReparto(Usuario usuario, Estacion estacion, String accion, Circulo circulo) {
        super(usuario,accion);
    	this.estacion = estacion;
    	this.circulo = circulo;
    }
    
    
    
    /**
     * Obtiene la estacion a la que se le va a hacer reparto
     * @return la estacion respectiva
     */
    public Estacion getEstacion(){
    	return estacion;
    }
	/**
	 * @return
	 */
	public long getTurnoDesde() {
		return turnoDesde;
	}

	/**
	 * @return
	 */
	public long getTurnoHasta() {
		return turnoHasta;
	}

	/**
	 * @param string
	 */
	public void setTurnoDesde(long l) {
		turnoDesde = l;
	}

	/**
	 * @param string
	 */
	public void setTurnoHasta(long l) {
		turnoHasta = l;
	}



	public Circulo getCirculo() {
		return circulo;
	}



	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}



	public List getTurnosRepartir() {
		return turnosRepartir;
	}



	public void setTurnosRepartir(List turnosRepartir) {
		this.turnosRepartir = turnosRepartir;
	}

}
/*
 * Created on 04-nov-2004
 *
 */
package gov.sir.core.eventos.registro;

import java.util.Map;
import java.util.List;
import gov.sir.core.eventos.comun.EvnSIRRespuesta;

/**
 * @author dcantor
 *
 */
public class EvnRespReparto extends EvnSIRRespuesta {
    
    /**
     * Holds value of property abogadoTurno.
     */
    private Map abogadoTurno;    
     
    /**
     * Holds value of property abogadoEstacion.
     */
    private Map abogadoEstacion; 
    
    private Map relacionUsuario;
    
    /**
     * Holds value of property idProcesoReparto.
     */
    private String idProcesoReparto; 

    /**
     * Holds value of property observaciones.
     */
    private List observaciones;    

	public EvnRespReparto() {
		super(null);
	}
        
        /**
         * Getter for property abogadoTurno.
         * @return Value of property abogadoTurno.
         */
        public Map getAbogadoTurno() {
            return this.abogadoTurno;
        }        
      
        /**
         * Setter for property abogadoTurno.
         * @param abogadoTurno New value of property abogadoTurno.
         */
        public void setAbogadoTurno(Map abogadoTurno) {
            this.abogadoTurno = abogadoTurno;
        }        

        /**
         * Getter for property abogadoEstacion.
         * @return Value of property abogadoEstacion.
         */
        public Map getAbogadoEstacion() {
            return this.abogadoEstacion;
        }
        
        /**
         * Setter for property abogadoEstacion.
         * @param abogadoEstacion New value of property abogadoEstacion.
         */
        public void setAbogadoEstacion(Map abogadoEstacion) {
            this.abogadoEstacion = abogadoEstacion;
        }
        
        /**
         * Getter for property observaciones.
         * @return Value of property observaciones.
         */
        public List getObservaciones() {
            return this.observaciones;
        }
        
        /**
         * Setter for property observaciones.
         * @param observaciones New value of property observaciones.
         */
        public void setObservaciones(List observaciones) {
            this.observaciones = observaciones;
        }
        
	/**
	 * @return
	 */
	public String getIdProcesoReparto() {
		return idProcesoReparto;
	}

	/**
	 * @param string
	 */
	public void setIdProcesoReparto(String string) {
		idProcesoReparto = string;
	}

	public void setRelacionesUsuario(Map mapRelacionesUsuario) {
		this.relacionUsuario = mapRelacionesUsuario;
	}

	public Map getRelacionesUsuario() {
		return relacionUsuario;
	}
}

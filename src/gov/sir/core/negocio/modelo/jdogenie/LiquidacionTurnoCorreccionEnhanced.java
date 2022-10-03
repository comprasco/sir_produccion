package gov.sir.core.negocio.modelo.jdogenie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;


/**
 * @author dlopez
 * Liquidación turno corrección
 */
public class LiquidacionTurnoCorreccionEnhanced extends LiquidacionEnhanced {

	private List actos = new ArrayList(); // contains Acto inverse Acto.liquidacion
	private long lastIdActo;
	private double valorImpuestos;
	private double valorDerechos;
	private String justificacionMayorValor;
        private double valorConservacionDoc;
		
	public LiquidacionTurnoCorreccionEnhanced(){
	}

	public static LiquidacionTurnoCorreccionEnhanced enhance(LiquidacionTurnoCorreccion liquidacion){
		return (LiquidacionTurnoCorreccionEnhanced) Enhanced.enhance(liquidacion);
	}
	
	public List getActos() {
		   return Collections.unmodifiableList(actos);
	   }

	  public boolean addActo(ActoEnhanced newActo) {
		   return actos.add(newActo);
	  }

	   public boolean removeActo(ActoEnhanced oldActo) {
		   return actos.remove(oldActo);
	   }
	   

	/**
	 * @return
	 */
	public long getLastIdActo() {
		return lastIdActo;
	}

	/**
	 * @param l
	 */
	public void setLastIdActo(long l) {
		lastIdActo = l;
	}

	/**
	 * @return
	 */
	public double getValorDerechos() {
		return valorDerechos;
	}

	/**
	 * @return
	 */
	public double getValorImpuestos() {
		return valorImpuestos;
	}

	/**
	 * @param d
	 */
	public void setValorDerechos(double d) {
		valorDerechos = d;
	}

	/**
	 * @param d
	 */
	public void setValorImpuestos(double d) {
		valorImpuestos = d;
	}

	/** Retorna la justificación de la liquidación por pago de mayor valor
	 * @return justificacionMayorValor
	 */
	public String getJustificacionMayorValor() {
		return justificacionMayorValor;
	}

	/** Cambia la justificación de la liquidación por pago de mayor valor
	 * @param string
	 */
	public void setJustificacionMayorValor(String string) {
		justificacionMayorValor = string;
	}

    public double getValorConservacionDoc() {
        return valorConservacionDoc;
    }

    public void setValorConservacionDoc(double valorConservacionDoc) {
        this.valorConservacionDoc = valorConservacionDoc;
    }
        
        
}
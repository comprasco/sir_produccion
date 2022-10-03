package gov.sir.core.negocio.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela los datos de liquidaciones del proceso correcciones
 * @author dlopez
 */
public class LiquidacionTurnoCorreccion extends Liquidacion implements TransferObject{

	private List actos = new ArrayList(); // contains Acto inverse Acto.liquidacion
	private long lastIdActo;
	private double valorImpuestos;
	private double valorDerechos;
	private String justificacionMayorValor;
        private double valorConservacionDoc;
	private static final long serialVersionUID = 1L;	
	/** Constructor por defecto */
	public LiquidacionTurnoCorreccion(){
	}

	/** Retorna la lista actos */
	  public List getActos() {
		  return Collections.unmodifiableList(actos);
	  }

	  /** Añade un acto a la lista actos */
	  public boolean addActo(Acto newActo) {
		  return actos.add(newActo);
	  }

	  /** Elimina un acto a la lista actos */
	  public boolean removeActo(Acto oldActo) {
		  return actos.remove(oldActo);
	  }
	  
	/** Retorna la secuencia de actos
	 * @return lastIdActo 
	 */
	public long getLastIdActo() {
		return lastIdActo;
	}
	
	/** Cambia la secuencia de actos
	* @param l
	*/
	public void setLastIdActo(long l) {
		lastIdActo = l;
	}
	/** Retorna el valor por concepto de derechos registrales
	 * @return valorDerechos
	 */
	public double getValorDerechos() {
		return valorDerechos;
	}

	/** Retorna el valor por concepto de impuestos
	 * @return valorImpuestos	 */
	public double getValorImpuestos() {
		return valorImpuestos;
	}

	/** Cambia el valor por concepto de derechos registrales
	 * @param d
	 */
	public void setValorDerechos(double d) {
		valorDerechos = d;
	}

	/** Cambia el valor por concepto de impuestos
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


package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela los datos de liquidaciones del proceso consultas
 * @author fceballos   */
public class LiquidacionTurnoConsulta extends Liquidacion implements TransferObject{

	private AlcanceGeografico alcanceGeografico;
        private static final long serialVersionUID = 1L;
	/** Metodo constructor por defecto  */
	public LiquidacionTurnoConsulta(){
	}

	/** Retorna el identificador del alcance geográfico en consultas (Consultas)
	 * @return alcanceGeografico
	 */
	public AlcanceGeografico getAlcanceGeografico() {
		return alcanceGeografico;
	}

	/** Cambia el identificador del alcance geográfico en consultas (Consultas)
	 * @param geografico
	 */
	public void setAlcanceGeografico(AlcanceGeografico geografico) {
		alcanceGeografico = geografico;
	}

}
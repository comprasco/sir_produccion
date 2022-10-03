package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion;


/**
 * @author dsalas
 * Liquidación turno devolucion
 */
public class LiquidacionTurnoDevolucionEnhanced extends LiquidacionEnhanced{
	
	private double valorImpuestos;
	private double valorDerechos;
	private double valorImpuestosMayorValor;
	private double valorDerechosMayorValor;
	private double valorMayorValor;
	private double valorSaldoFavor;
        private double valorConservacionDoc;
		
	public LiquidacionTurnoDevolucionEnhanced(){
	}
	
	public static LiquidacionTurnoDevolucionEnhanced enhance(LiquidacionTurnoDevolucion liquidacion){
		return (LiquidacionTurnoDevolucionEnhanced) Enhanced.enhance(liquidacion);
	}

	public double getValorImpuestos() {
		return valorImpuestos;
	}

	public void setValorImpuestos(double valorImpuestos) {
		this.valorImpuestos = valorImpuestos;
	}

	public double getValorDerechos() {
		return valorDerechos;
	}

	public void setValorDerechos(double valorDerechos) {
		this.valorDerechos = valorDerechos;
	}

	public double getValorImpuestosMayorValor() {
		return valorImpuestosMayorValor;
	}

	public void setValorImpuestosMayorValor(double valorImpuestosMayorValor) {
		this.valorImpuestosMayorValor = valorImpuestosMayorValor;
	}

	public double getValorDerechosMayorValor() {
		return valorDerechosMayorValor;
	}

	public void setValorDerechosMayorValor(double valorDerechosMayorValor) {
		this.valorDerechosMayorValor = valorDerechosMayorValor;
	}

	public double getValorMayorValor() {
		return valorMayorValor;
	}

	public void setValorMayorValor(double valorMayorValor) {
		this.valorMayorValor = valorMayorValor;
	}

	public double getValorSaldoFavor() {
		return valorSaldoFavor;
	}

	public void setValorSaldoFavor(double valorSaldoFavor) {
		this.valorSaldoFavor = valorSaldoFavor;
	}
        
        public double getValorConservacionDoc() {
            return valorConservacionDoc;
        }

        public void setValorConservacionDoc(double valorConservacionDoc) {
            this.valorConservacionDoc = valorConservacionDoc;
        }

}
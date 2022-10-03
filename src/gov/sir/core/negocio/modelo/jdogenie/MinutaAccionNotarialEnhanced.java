package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.MinutaAccionNotarial;

public class MinutaAccionNotarialEnhanced extends Enhanced {

	private String idMinuta;//pk
	private String idAccionNotarial;//pk
	private MinutaEnhanced minuta;
	private AccionNotarialEnhanced accionNotarial; // inverse MinutaEnhanced.accionesNotariales
    private long unidades;
    private double valor;
    private long conCuantia;
	
	public AccionNotarialEnhanced getAccionNotarial() {
		return accionNotarial;
	}
	
	public void setAccionNotarial(AccionNotarialEnhanced accionNotarial)
	{
		this.accionNotarial = accionNotarial;
		setIdAccionNotarial(accionNotarial.getIdAccionNotarial());
	}
	
	public String getIdAccionNotarial() {
		return idAccionNotarial;
	}
	
	public void setIdAccionNotarial(String idAccionNotarial) {
		this.idAccionNotarial = idAccionNotarial;
	}
	
	public String getIdMinuta() {
		return idMinuta;
	}
	
	public void setIdMinuta(String idMinuta) {
		this.idMinuta = idMinuta;
	}
	
	public MinutaEnhanced getMinuta() {
		return minuta;
	}
	
	public void setMinuta(MinutaEnhanced minutaEnhanced) {
		this.minuta = minutaEnhanced;
		setIdMinuta(minutaEnhanced.getIdMinuta());
	}
 
	public static MinutaAccionNotarialEnhanced enhance(MinutaAccionNotarial m){
		return (MinutaAccionNotarialEnhanced)Enhanced.enhance(m);
	}
	
	public long getUnidades() {
		return unidades;
	}

	public void setUnidades(long unidades) {
		this.unidades = unidades;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public long getConCuantia() {
		return conCuantia;
	}

	public void setConCuantia(long conCuantia) {
		this.conCuantia = conCuantia;
	}
	
}

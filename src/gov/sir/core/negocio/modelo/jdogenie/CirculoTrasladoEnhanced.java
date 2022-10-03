package gov.sir.core.negocio.modelo.jdogenie;


public class CirculoTrasladoEnhanced extends Enhanced {
	private CirculoEnhanced circuloOrigen;
	private CirculoEnhanced circuloDestino;
	private String idTraslado;
	
	public CirculoTrasladoEnhanced() {
    }

	/**
	 * @return Returns the idTraslado.
	 */
	public String getIdTraslado() {
		return idTraslado;
	}
	/**
	 * @param idTraslado The idTraslado to set.
	 */
	public void setIdTraslado(String idTraslado) {
		this.idTraslado = idTraslado;
	}
	public CirculoEnhanced getCirculoDestino() {
		return circuloDestino;
	}

	public void setCirculoDestino(CirculoEnhanced circuloDestino) {
		this.circuloDestino = circuloDestino;
	}

	public CirculoEnhanced getCirculoOrigen() {
		return circuloOrigen;
	}

	public void setCirculoOrigen(CirculoEnhanced circuloOrigen) {
		this.circuloOrigen = circuloOrigen;
	}
}

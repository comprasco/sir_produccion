package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

public class CirculoTraslado implements TransferObject {

	private Circulo circuloOrigen;
	private Circulo circuloDestino;
	private String idTraslado;
	
	private static final long serialVersionUID = 1L;
	public CirculoTraslado() {
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
	
	public Circulo getCirculoDestino() {
		return circuloDestino;
	}

	public void setCirculoDestino(Circulo circuloDestino) {
		this.circuloDestino = circuloDestino;
	}

	public Circulo getCirculoOrigen() {
		return circuloOrigen;
	}

	public void setCirculoOrigen(Circulo circuloOrigen) {
		this.circuloOrigen = circuloOrigen;
	}
}

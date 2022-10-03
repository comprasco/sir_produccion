/*
 * Created on 01-may-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.sir.core.negocio.modelo.jdogenie;


/**
 * @author jfrias
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TextoImprimibleEnhanced extends Enhanced {
	private String idTexto;
	private String valor;
	private String descripcion;
	
	public TextoImprimibleEnhanced(){
		
	}
	
	
	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return Returns the idTexto.
	 */
	public String getIdTexto() {
		return idTexto;
	}
	/**
	 * @param idTexto The idTexto to set.
	 */
	public void setIdTexto(String idTexto) {
		this.idTexto = idTexto;
	}
	/**
	 * @return Returns the valor.
	 */
	public String getValor() {
		return valor;
	}
	/**
	 * @param valor The valor to set.
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
}

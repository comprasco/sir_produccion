package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

public class BancosXCirculo implements TransferObject{
	
	public BancosXCirculo() {
	}
	
	
	private String idCirculo;
	private String idBanco;
	private Circulo circulo;//inverse Circulo.circulos
	private Banco banco; //inverse Banco.bancos
	private String principal;
	private static final long serialVersionUID = 1L;
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	public Circulo getCirculo() {
		return circulo;
	}
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}
	public String getIdBanco() {
		return idBanco;
	}
	public void setIdBanco(String idBanco) {
		this.idBanco = idBanco;
	}
	public String getIdCirculo() {
		return idCirculo;
	}
	public void setIdCirculo(String idCirculo) {
		this.idCirculo = idCirculo;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	
	

}

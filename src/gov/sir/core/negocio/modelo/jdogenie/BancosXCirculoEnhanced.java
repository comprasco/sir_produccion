package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.BancosXCirculo;

/**
 * 
 * @author Pablo Quintana
 * Nov 04-2008. relacionar bancos y círculos
 */
public class BancosXCirculoEnhanced extends Enhanced{
	
	private String idCirculo;
	private String idBanco;
	private CirculoEnhanced circulo;//inverse CirculoEnhanced.circulos
	private BancoEnhanced banco; //inverse BancoEnhanced.bancos
	private String principal;
	
	public BancoEnhanced getBanco() {
		return banco;
	}
	public void setBanco(BancoEnhanced banco) {
		this.banco = banco;
	}
	public CirculoEnhanced getCirculo() {
		return circulo;
	}
	public void setCirculo(CirculoEnhanced circulo) {
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
	
	public static BancosXCirculoEnhanced enhance(BancosXCirculo bancosXCirculoEnhanced){
		return (BancosXCirculoEnhanced) Enhanced.enhance(bancosXCirculoEnhanced);
	}

}

package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Banco;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class BancoEnhanced extends Enhanced{

    private String idBanco; // pk 
    private String nombre;
    private List sucursales = new ArrayList(); // contains Sucursal  inverse Sucursal.banco

	public BancoEnhanced() {
	}

	public BancoEnhanced(String idBanco, String nombre) {
		this.idBanco = idBanco;
		this.nombre = nombre;
	}
	
	/**
	 * Este m�todo sobreescribe el m�todo en Object y retorna true
	 * solo si el numero del banco es iguales.
	 */
	public boolean equals(Object other) {
		if (other==null || !(other instanceof BancoEnhanced)) return false;
		BancoEnhanced p = (BancoEnhanced) other;
		return idBanco.equals(p.idBanco);
	}

    public String getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(String idBanco) {
        this.idBanco = idBanco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List getSucursales() {
        return Collections.unmodifiableList(sucursales);
    }

    public boolean addSucursal(SucursalBancoEnhanced newSucursal) {
        return sucursales.add(newSucursal);
    }

    public boolean removeSucursal(SucursalBancoEnhanced oldSucursal) {
        return sucursales.remove(oldSucursal);
    }

	public static BancoEnhanced enhance(Banco banco) {
		return (BancoEnhanced) Enhanced.enhance(banco);
	}
	
	private List bancos=new ArrayList();//contains BancosXCirculoEnhanced  inverse BancosXCirculoEnhanced.banco

	public List getBancos() {
		return bancos;
	}

	public void setBancos(List bancos) {
		this.bancos = bancos;
	}
	
	public boolean addBancoEnhanced(BancosXCirculoEnhanced newBancosXCirculoEnhanced){
		return bancos.add(newBancosXCirculoEnhanced);
	}
	
	public boolean removeBancoEnhanced(BancosXCirculoEnhanced oldBancosXCirculoEnhanced){
		return bancos.remove(oldBancosXCirculoEnhanced);
	}

}
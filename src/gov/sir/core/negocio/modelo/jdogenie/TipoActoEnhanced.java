package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoActo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class TipoActoEnhanced extends Enhanced{

    private String idTipoActo; // pk
    private UsuarioEnhanced usuario;
    private boolean impPorCuantia;
    private String nombre;
    private TipoCalculoEnhanced tipoCalculo;
    private List tiposDerechosRegistrales = new ArrayList(); // contains TipoActoDerechoRegistral  inverse TipoActoDerechoRegistral.tipoActo

    /*
     *@autor          : JATENCIA 
     * @mantis        : 0015082 
     * @Requerimiento : 027_589_Acto_liquidaci?n_copias 
     * @descripcion   : Se adiciona una variable que representa el atributo en
     * la tabla SIR_OP_TIPO_ACTO, para determinar si se encuentra activo el Tipo de Acto.
     */
    private boolean activo;
    
    /**
    * @Autor: Santiago V?squez
    * @Change: 2062.TARIFAS.REGISTRALES.2017
    */
    private boolean incentivoRegistral;

    public boolean isIncentivoRegistral() {
        return incentivoRegistral;
    }

    public void setIncentivoRegistral(boolean incentivoRegistral) {
        this.incentivoRegistral = incentivoRegistral;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    /* - Fin del bloque - */

    public TipoActoEnhanced() {
    }

    public String getIdTipoActo() {
        return idTipoActo;
    }

    public void setIdTipoActo(String idTipoActo) {
        this.idTipoActo = idTipoActo;
    }

	public UsuarioEnhanced getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEnhanced usuario) {
		this.usuario = usuario;
	}
	
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoCalculoEnhanced getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(TipoCalculoEnhanced tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public List getTiposDerechosRegistrales() {
        return Collections.unmodifiableList(tiposDerechosRegistrales);
    }

    public boolean addTiposDerechosRegistrale(TipoActoDerechoRegistralEnhanced newTiposDerechosRegistrale) {
        return tiposDerechosRegistrales.add(newTiposDerechosRegistrale);
    }

    public boolean removeTiposDerechosRegistrale(TipoActoDerechoRegistralEnhanced oldTiposDerechosRegistrale) {
        return tiposDerechosRegistrales.remove(oldTiposDerechosRegistrale);
    }
    
	public static TipoActoEnhanced enhance(TipoActo tipoActo) {
		return (TipoActoEnhanced) Enhanced.enhance(tipoActo);
	}


    /**
	 * @return
	 */
	public boolean isImpPorCuantia() {
		return impPorCuantia;
	}

	/**
	 * @param b
	 */
	public void setImpPorCuantia(boolean b) {
		impPorCuantia = b;
	}


}

package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.EstacionRecibo;

import java.util.Date;

/*
 * Generated by Versant Open Access (version:3.2.6 (17 Nov 2004))
 */
public class EstacionReciboEnhanced extends Enhanced{

    private String idEstacion; // pk 
    private Date fechaCreacion;
    private long numeroFinal;
    private long numeroInicial;
    private long ultimoNumero;
    private long numeroProceso; // pk

    public EstacionReciboEnhanced() {
    }

    public String getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getNumeroFinal() {
        return numeroFinal;
    }

    public void setNumeroFinal(long numeroFinal) {
        this.numeroFinal = numeroFinal;
    }

    public long getNumeroInicial() {
        return numeroInicial;
    }

    public void setNumeroInicial(long numeroInicial) {
        this.numeroInicial = numeroInicial;
    }

    public long getUltimoNumero() {
        return ultimoNumero;
    }

    public void setUltimoNumero(long ultimoNumero) {
        this.ultimoNumero = ultimoNumero;
    }
    
    public long getNumeroProceso() {
		return numeroProceso;
	}

	public void setNumeroProceso(long numeroProceso) {
		this.numeroProceso = numeroProceso;
	}
    
	public static EstacionReciboEnhanced enhance(EstacionRecibo estacionRecibo){
		return (EstacionReciboEnhanced) Enhanced.enhance(estacionRecibo);
	}
}
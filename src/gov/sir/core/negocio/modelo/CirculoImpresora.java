package gov.sir.core.negocio.modelo;


import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela la informacion de impresoras por circulo registral*/
/*
 * Generated by Versant Open Access (version:3.2.6 (17 Nov 2004)) */
public class CirculoImpresora implements TransferObject{

    private String idCirculo; // pk 
    private String idImpresora; // pk 
    private String idTipoImprimible; //pk
    private Date fechaCreacion;
    private Circulo circulo;
    private boolean predeterminada;
    private static final long serialVersionUID = 1L;
    public boolean isPredeterminada() {
		return predeterminada;
	}

	public void setPredeterminada(boolean predeterminada) {
		this.predeterminada = predeterminada;
	}

	/** Constructor por defecto */
    public CirculoImpresora() {
    }

    /** Retorna el identificador del circulo
     * @return idCirculo
     */
    public String getIdCirculo() {
        return idCirculo;
    }

    /**
     * Cambia el identificador del circulo
     * @param idCirculo
     */
    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    /**
     * Retorna el identificador de la impresora
     * @return idImpresora
     */
    public String getIdImpresora() {
        return idImpresora;
    }

    /**
     * Cambia el identificador de la impresora
     * @param idImpresora
     */
    public void setIdImpresora(String idImpresora) {
        this.idImpresora = idImpresora;
    }

    /**
     * Retorna la fecha de creaci?n del regsitro en la base de datos
     * @return fechaCreacion  */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Cambia la fecha de creaci?n del regsitro en la base de datos
     * @param fechaCreacion
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** Retorna el identificador del circulo
     * @return circulo
     */
    public Circulo getCirculo() {
        return circulo;
    }

    /** Cambia el identificador del circulo
     * @param circulo  */
    public void setCirculo(Circulo circulo) {
        this.circulo = circulo;
        setIdCirculo(circulo.getIdCirculo());
    }

    public String getIdTipoImprimible() {
		return idTipoImprimible;
	}

	public void setIdTipoImprimible(String idTipoImprimible) {
		this.idTipoImprimible = idTipoImprimible;
	}
}
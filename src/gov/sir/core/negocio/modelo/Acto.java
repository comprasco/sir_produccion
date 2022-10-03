package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/**
 * Clase que define los actos asociados a las liquidaciones de registro de
 * documentos
 *
 * @author fceballos
 */
public class Acto implements TransferObject {

    /**
     * Identificador de acto relativo a liquidación
     */
    private String idActo; // pk 
private static final long serialVersionUID = 1L;
    /**
     * Identificador de la liquidación asociada
     */
    private String idLiquidacion; // pk 

    /**
     * Identificador de la liquidación correspondiente a la solicitud asociada
     */
    private String idSolicitud; // pk 

    /**
     * Flag que indica si el acto cobra impuestos
     */
    private boolean cobroImpuestos;

    /**
     * Cuantía del acto
     */
    private double cuantia;

    /**
     * Valor del acto
     */
    private double valor;

    /**
     * Objeto liquidación padre asociado
     */
    private Liquidacion liquidacion;

    /**
     * Tipo de acto asociado
     */
    private TipoActo tipoActo;

    /**
     * Objeto que indica el tipo de derecho registral asociado al acto
     */
    private TipoActoDerechoRegistral tipoDerechoReg;

    /**
     * Tipo de impuesto del acto
     */
    private TipoImpuesto tipoImpuesto;

    /**
     * Valor de impuestos del acto
     */
    private double valorImpuestos;

    /**
     * Fecha de creación del acto
     */
    private Date fechaCreacion;

    private String circulo;

    private double valorMora;

    /**
     * @Autor: Santiago Vásquez
     * @Change: 2062.TARIFAS.REGISTRALES.2017
     */
    private boolean incentivoRegistral;

    public boolean isIncentivoRegistral() {
        return incentivoRegistral;
    }

    public void setIncentivoRegistral(boolean incentivoRegistral) {
        this.incentivoRegistral = incentivoRegistral;
    }

    /**
     * Constructor por defecto
     */
    public Acto() {
    }

    /**
     * Retorna el identificador de acto relativo a liquidación
     *
     * @return idActo
     */
    public String getIdActo() {
        return idActo;
    }

    /**
     * Cambia el identificador de acto relativo a liquidación
     *
     * @param idActo
     */
    public void setIdActo(String idActo) {
        this.idActo = idActo;
    }

    /**
     * Retorna el identificador de la liquidación asociada
     *
     * @return idLiquidacion
     */
    public String getIdLiquidacion() {
        return idLiquidacion;
    }

    /**
     * Cambia el identificador de la liquidación asociada
     *
     * @param idLiquidacion
     */
    public void setIdLiquidacion(String idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }

    /**
     * Retorna el identificador de la liquidación correspondiente a la solicitud
     * asociada
     *
     * @return idSolicitud
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Cambia el identificador de la liquidación correspondiente a la solicitud
     * asociada
     *
     * @param idSolicitud
     */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Retorna el objeto liquidación padre asociado
     *
     * @return liquidacion
     */
    public Liquidacion getLiquidacion() {
        return liquidacion;
    }

    /**
     * Cambia el objeto liquidación padre asociado, y sus llaves asociadas
     *
     * @param liquidacion
     */
    public void setLiquidacion(Liquidacion liquidacion) {
        this.liquidacion = liquidacion;
        setIdLiquidacion(liquidacion.getIdLiquidacion());
        setIdSolicitud(liquidacion.getIdSolicitud());
    }

    /**
     * Retorna el identificador del tipo de acto asociado
     *
     * @return el tipo de acto
     */
    public TipoActo getTipoActo() {
        return tipoActo;
    }

    /**
     * Cambia el identificador del tipo de acto asociado
     *
     * @param tipoActo
     */
    public void setTipoActo(TipoActo tipoActo) {
        this.tipoActo = tipoActo;
    }

    /**
     * Retorna el identificador del tipo de impuesto del acto
     *
     * @return tipoImpuesto
     */
    public TipoImpuesto getTipoImpuesto() {
        return tipoImpuesto;
    }

    /**
     * Cambia el identificador del tipo de impuesto del acto
     *
     * @param tipoImpuesto
     */
    public void setTipoImpuesto(TipoImpuesto tipoImpuesto) {
        this.tipoImpuesto = tipoImpuesto;
    }

    /**
     * Retorna la cuantía del acto
     */
    public double getCuantia() {
        return cuantia;
    }

    /**
     * Retorna el valor del acto
     *
     * @return
     */
    public double getValor() {
        return valor;
    }

    /**
     * Cambia la cuantía del acto
     */
    public void setCuantia(double d) {
        cuantia = d;
    }

    /**
     * Cambia el valor del acto
     *
     * @param d
     */
    public void setValor(double d) {
        valor = d;
    }

    /**
     * Retorna un valor booleano que indica si el acto cobra o no impuesto
     *
     * @return
     */
    public boolean isCobroImpuestos() {
        return cobroImpuestos;
    }

    /**
     * Cambia el valor que indica si el acto cobra o no impuestos
     */
    public void setCobroImpuestos(boolean cobroImpuestos) {
        this.cobroImpuestos = cobroImpuestos;
    }

    /**
     * Retorna el identificador del tipo de derecho registral asociado al acto
     *
     * @return
     */
    public TipoActoDerechoRegistral getTipoDerechoReg() {
        return tipoDerechoReg;
    }

    /**
     * Cambia el identificador del tipo de derecho registral asociado al acto
     *
     * @param registral
     */
    public void setTipoDerechoReg(TipoActoDerechoRegistral registral) {
        tipoDerechoReg = registral;
    }

    /**
     * Retorna el valor del impuesto del acto
     *
     * @return
     */
    public double getValorImpuestos() {
        return valorImpuestos;
    }

    /**
     * Cambia el valor del impuesto del acto
     *
     * @param d
     */
    public void setValorImpuestos(double d) {
        valorImpuestos = d;
    }

    public double getValorMora() {
        return valorMora;
    }

    public void setValorMora(double valorMora) {
        this.valorMora = valorMora;
    }

    /**
     * Retorna la fecha de creación de registro en la base de datos
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Cambia la fecha de creación de registro en la base de datos
     *
     * @param date
     */
    public void setFechaCreacion(Date date) {
        fechaCreacion = date;
    }

    public String getCirculo() {
        return circulo;
    }

    public void setCirculo(String circulo) {
        this.circulo = circulo;
    }

}

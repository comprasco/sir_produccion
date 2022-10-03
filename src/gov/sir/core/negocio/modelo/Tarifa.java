package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela las tarifas configuradas en el sistema */ 
/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class Tarifa  implements TransferObject {

    private String idCodigo; // pk 
    private String idTipo; // pk 
    private long idVersion; // pk 
    private Date fechaCreacion;
    private Date fechaInicial;
    private double valor;
    private TipoTarifa tipoTarifa; // inverse TipoTarifa.tarifas
    private static final long serialVersionUID = 1L;
    /** Metodo constructor por defecto  */
    public Tarifa() {
    }

    /** Retorna el codigo de la tarifa  */
    public String getIdCodigo() {
        return idCodigo;
    }

    /** Modifica el codigo de la tarifa  */
    public void setIdCodigo(String idCodigo) {
        this.idCodigo = idCodigo;
    }

    /** Retorna el tipo de tarifa */
    public String getIdTipo() {
        return idTipo;
    }

    /** Modifica el tipo de tarifa */
    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }

    /** Retorna la version de la tarifa */
    public long getIdVersion() {
        return idVersion;
    }

    /** Modifica la version de la tarifa */
    public void setIdVersion(long idVersion) {
        this.idVersion = idVersion;
    }

    /** Retorna la fecha de creacion del registro en la base de datos  */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /** Modifica la fecha de creacion del registro en la base de datos  */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** Retorna la fecha en la que se crea la tarifa  */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /** Modifica la fecha en la que se crea la tarifa  */
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /** Retorna el valor de la tarifa  */
    public double getValor() {
        return valor;
    }

    /** Modifica el valor de la tarifa  */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /** Retorna el tipo de tarifa  */
    public TipoTarifa getTipoTarifa() {
        return tipoTarifa;
    }

    /** Modifica el tipo de tarifa  */
    public void setTipoTarifa(TipoTarifa tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
        setIdTipo(tipoTarifa.getIdTipo());
    }
}
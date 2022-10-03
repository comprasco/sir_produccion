package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

/**
* @Autor: Santiago Vásquez
* @Change: 2062.TARIFAS.REGISTRALES.2017
*/
public class TarifaRegistral implements TransferObject {
    
    public static final String PORCENTUAL = "p";
    public static final String ABSOLUTO = "a";
    private static final long serialVersionUID = 1L;
    private String idTarifa;
    private Integer inicioCuantia;
    private boolean inicioInclusive;
    private Integer finalCuantia;
    private boolean finalInclusive;
    private String tipoAplicacionTarifa;
    private double valorTarifa;

    public Integer getFinalCuantia() {
        return finalCuantia;
    }

    public void setFinalCuantia(Integer finalCuantia) {
        this.finalCuantia = finalCuantia;
    }

    public boolean isFinalInclusive() {
        return finalInclusive;
    }

    public void setFinalInclusive(boolean finalInclusive) {
        this.finalInclusive = finalInclusive;
    }

    public String getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(String idTarifa) {
        this.idTarifa = idTarifa;
    }

    public Integer getInicioCuantia() {
        return inicioCuantia;
    }

    public void setInicioCuantia(Integer inicioCuantia) {
        this.inicioCuantia = inicioCuantia;
    }

    public boolean isInicioInclusive() {
        return inicioInclusive;
    }

    public void setInicioInclusive(boolean inicioInclusive) {
        this.inicioInclusive = inicioInclusive;
    }

    public String getTipoAplicacionTarifa() {
        return tipoAplicacionTarifa;
    }

    public void setTipoAplicacionTarifa(String tipoAplicacionTarifa) {
        this.tipoAplicacionTarifa = tipoAplicacionTarifa;
    }

    public double getValorTarifa() {
        return valorTarifa;
    }

    public void setValorTarifa(double valorTarifa) {
        this.valorTarifa = valorTarifa;
    }
}

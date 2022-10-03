package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class TarifaDepartamento implements TransferObject {

    private String idDepartamento; // pk 
    private String idTipoActo; // pk 
    private double perven;
    private double valimp;
    private Departamento departamento;
    private TipoActo tipoacto; // inverse TipoActo.tarifaDepartamentos
    private static final long serialVersionUID = 1L;
    /** Metodo constructor por defecto  */
    public TarifaDepartamento() {
    }

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getIdTipoActo() {
        return idTipoActo;
    }

    public void setIdTipoActo(String idTipoActo) {
        this.idTipoActo = idTipoActo;
    }

    public double getPerven() {
        return perven;
    }

    public void setPerven(double perven) {
        this.perven = perven;
    }

    public double getValimp() {
        return valimp;
    }

    public void setValimp(double valimp) {
        this.valimp = valimp;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
        setIdDepartamento(departamento.getIdDepartamento());
    }

    public TipoActo getTipoacto() {
        return tipoacto;
    }

    public void setTipoacto(TipoActo tipoacto) {
        this.tipoacto = tipoacto;
        setIdTipoActo(tipoacto.getIdTipoActo());
    }
}
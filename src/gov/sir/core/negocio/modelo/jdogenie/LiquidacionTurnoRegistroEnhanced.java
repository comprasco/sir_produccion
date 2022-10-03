/*
 * Created on 06-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.jdogenie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LiquidacionTurnoRegistroEnhanced extends LiquidacionEnhanced {

    private List actos = new ArrayList(); // contains Acto inverse Acto.liquidacion
    private String otroImpuesto;
    /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Cambio tipo de la variable valorOtroImp de float a double
     */
    private double valorOtroImp;
    private double valorImpuestos;
    private double valorDerechos;
    private double valorConservacionDoc;
    private double valorMora;
    private long lastIdActo;
    private String justificacionMayorValor;

    public LiquidacionTurnoRegistroEnhanced() {
    }

    public List getActos() {
        return Collections.unmodifiableList(actos);
    }

    public boolean addActo(ActoEnhanced newActo) {
        return actos.add(newActo);
    }

    public boolean removeActo(VeredaEnhanced oldActo) {
        return actos.remove(oldActo);
    }

    /**
     * @return
     */
    public String getOtroImpuesto() {
        return otroImpuesto;
    }

    /**
     * @return
     */
    /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Cambio tipo de la variable valorOtroImp de float a double
     */
    public double getValorOtroImp() {
        return valorOtroImp;
    }

    /**
     * @param string
     */
    public void setOtroImpuesto(String string) {
        otroImpuesto = string;
    }

    /**
     * @param f
     */
    /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Cambio tipo de la variable valorOtroImp de float a double
     */
    public void setValorOtroImp(double f) {
        valorOtroImp = f;
    }

    public static LiquidacionTurnoRegistroEnhanced enhance(LiquidacionTurnoRegistro liquidacion) {
        return (LiquidacionTurnoRegistroEnhanced) Enhanced.enhance(liquidacion);
    }

    /**
     * @return
     */
    public long getLastIdActo() {
        return lastIdActo;
    }

    /**
     * @param l
     */
    public void setLastIdActo(long l) {
        lastIdActo = l;
    }

    /**
     * @return
     */
    public double getValorDerechos() {
        return valorDerechos;
    }

    /**
     * @return
     */
    public double getValorImpuestos() {
        return valorImpuestos;
    }

    /**
     * @return
     */
    public double getValorMora() {
        return valorMora;
    }

    /**
     * @param d
     */
    public void setValorDerechos(double d) {
        valorDerechos = d;
    }

    /**
     * @param d
     */
    public void setValorImpuestos(double d) {
        valorImpuestos = d;
    }

    /**
     * @param d
     */
    public void setValorMora(double d) {
        valorMora = d;
    }

    /**
     * @return
     */
    public String getJustificacionMayorValor() {
        return justificacionMayorValor;
    }

    /**
     * @param string
     */
    public void setJustificacionMayorValor(String string) {
        justificacionMayorValor = string;
    }

    public double getValorConservacionDoc() {
        return valorConservacionDoc;
    }

    public void setValorConservacionDoc(double valorConservacionDoc) {
        this.valorConservacionDoc = valorConservacionDoc;
    }

}

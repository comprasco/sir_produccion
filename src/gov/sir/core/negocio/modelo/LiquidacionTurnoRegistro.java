/*
 * Created on 06-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.auriga.core.modelo.TransferObject;

/**
 * Clase que modela los datos de liquidaciones del proceso de registro de
 * documentos
 *
 * @author fceballos
 */
public class LiquidacionTurnoRegistro extends Liquidacion implements TransferObject {

    private List actos = new ArrayList(); // contains Acto inverse Acto.liquidacion
    private String otroImpuesto;
    /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Cambio tipo de la variable valorOtroImp de float a double
     */
    private static final long serialVersionUID = 1L;
    private double valorOtroImp;
    private double valorImpuestos;
    private double valorDerechos;
    private double valorMora;
    private double valorConservacionDoc;
    private long lastIdActo;
    private String justificacionMayorValor;
    private String uid;

    //flag que indica si es preliquidacion o no
    private boolean preliquidacion = false;

    /**
     * Constructor por defecto
     */
    public LiquidacionTurnoRegistro() {
    }

    /**
     * Retorna la lista actos
     */
    public List getActos() {
        return Collections.unmodifiableList(actos);
    }

    /**
     * Añade un acto a la lista actos
     */
    public boolean addActo(Acto newActo) {
        return actos.add(newActo);
    }

    /**
     * Elimina un acto a la lista actos
     */
    public boolean removeActo(Acto oldActo) {
        return actos.remove(oldActo);
    }

    /**
     * Retorna el nombre del otro impuesto que se puede cobrar en registro de
     * documentos
     *
     * @return otroImpuesto
     */
    public String getOtroImpuesto() {
        return otroImpuesto;
    }

    /**
     * Retorna el valor del otro impuesto que se puede cobrar en registro de
     * documentos
     *
     * @return valorOtroImp
     */
    /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Cambio tipo de la variable valorOtroImp de float a double
     */
    public double getValorOtroImp() {
        return valorOtroImp;
    }

    /**
     * Cambia el nombre del otro impuesto que se puede cobrar en registro de
     * documentos
     *
     * @param string
     */
    public void setOtroImpuesto(String string) {
        otroImpuesto = string;
    }

    /**
     * Cambia el valor del otro impuesto que se puede cobrar en registro de
     * documentos
     *
     * @param f
     */
    /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
         * Cambio tipo de la variable valorOtroImp de float a double
     */
    public void setValorOtroImp(double f) {
        valorOtroImp = f;
    }

    /**
     * Retorna la secuencia de actos
     *
     * @return lastIdActo
     */
    public long getLastIdActo() {
        return lastIdActo;
    }

    /**
     * Cambia la secuencia de actos
     *
     * @param l
     */
    public void setLastIdActo(long l) {
        lastIdActo = l;
    }

    /**
     * Retorna el valor por concepto de derechos registrales
     *
     * @return valorDerechos
     */
    public double getValorDerechos() {
        return valorDerechos;
    }

    /**
     * Retorna el valor por concepto de impuestos
     *
     * @return valorImpuestos
     */
    public double getValorImpuestos() {
        return valorImpuestos;
    }

    /**
     * Retorna el valor por concepto de mora
     *
     * @return valorMora
     */
    public double getValorMora() {
        return valorMora;
    }

    /**
     * Cambia el valor por concepto de derechos registrales
     *
     * @param d
     */
    public void setValorDerechos(double d) {
        valorDerechos = d;
    }

    /**
     * Cambia el valor por concepto de impuestos
     *
     * @param d
     */
    public void setValorImpuestos(double d) {
        valorImpuestos = d;
    }

    /**
     * Cambia el valor por concepto de mora
     *
     * @param d
     */
    public void setValorMora(double d) {
        valorMora = d;
    }

    /**
     * Retorna la justificación de la liquidación por pago de mayor valor
     *
     * @return justificacionMayorValor
     */
    public String getJustificacionMayorValor() {
        return justificacionMayorValor;
    }

    /**
     * Cambia la justificación de la liquidación por pago de mayor valor
     *
     * @param string
     */
    public void setJustificacionMayorValor(String string) {
        justificacionMayorValor = string;
    }

    /**
     * Retorna el identificador de sesión para impresión de recibos asociados
     *
     * @return uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Cambia el identificador de sesión para impresión de recibos asociados
     *
     * @param string
     */
    public void setUid(String string) {
        uid = string;
    }

    public double getValorConservacionDoc() {
        return valorConservacionDoc;
    }

    public void setValorConservacionDoc(double valorConservacionDoc) {
        this.valorConservacionDoc = valorConservacionDoc;
    }

    public boolean isPreliquidacion() {
        return preliquidacion;
    }

    public void setPreliquidacion(boolean preliquidacion) {
        this.preliquidacion = preliquidacion;
    }

}

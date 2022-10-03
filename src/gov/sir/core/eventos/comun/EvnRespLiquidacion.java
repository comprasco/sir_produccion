package gov.sir.core.eventos.comun;

import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.Turno;

import java.util.List;

/**
 * Esta clase encapsula todo el conocimiento generado como respuesta a una
 * solicitud de tipo Liquidación
 *
 * @author eacosta
 */
public class EvnRespLiquidacion extends EvnSIRRespuesta {

    /**
     * Esta constante indica que la respuesta es a una solicitud de liquidación.
     */
    public static final String LIQUIDACION = "LIQUIDACION";
    public static final String LIQUIDACION_SIMPLIFICADO = "LIQUIDACION_SIMPLIFICADO";
    public static final String PRELIQUIDACION = "PRELIQUIDACION";
    public static final String VALIDACION = "VALIDACION";
    public static final String VALIDACION_MATRICULA = "VALIDACION_MATRICULA";
    public static final String VALIDACION_MATRICULA_MIG = "VALIDACION_MATRICULA_MIG";
    public static final String VALIDACION_MATRICULA_ASOCIADA = "VALIDACION_MATRICULA_ASOCIADA";
    public static final String TURNO_ANTERIOR_VALIDADO = "TURNO_ANTERIOR_VALIDADO";

    /**
     * Constante que indica que la liuidacion y el pago ya se realizo
     */
    public static final String RADICAR = "RADICAR";

    double totalOtroImp = 0;

    private int idImprimible = 0;

    /**
     * Liquidación efectuada para la solicitud atendida
     */
    private Liquidacion liquidacion;

    /**
     * Determina si el usuario que hace la liquidación es cajero, para evitar
     * hacer otro paso, entonces el mismo usuario registra el pago.
     */
    private Boolean esCajero;

    /**
     * Determina si el usuario que hace la liquidación es cajero de registro
     * para habilitar o no el botón de siguiente.
     */
    private Boolean esCajeroRegistro;

    /**
     * Lista de turnosFolioMig, representa registros de la tabla
     * SIR_MIG_REL_TURNO_FOLIO, que relacionan turnos con folios que no están en
     * la tabla SIR_OP_SOLICITUD_FOLIO
     */
    private List turnosFolioMig;
    
    private boolean isCertificadoEspecial;
    
    private boolean isCertificadoTramite;
    
    private boolean isCertificadoActuacion;
    
    private List turnoTramite;

    /**
     * Turno de la liquidacion
     */
    private Turno turno;

    private double valorSecuencial;

    private List canalesXCirculo;

    public double getValorSecuencial() {
        return valorSecuencial;
    }

    public void setValorSecuencial(double valorSecuencial) {
        this.valorSecuencial = valorSecuencial;
    }

    /**
     * @param payload
     */
    public EvnRespLiquidacion(Liquidacion liquidacion, String tipoRespuesta) {
        super(liquidacion, tipoRespuesta);
        this.liquidacion = liquidacion;
    }

    public EvnRespLiquidacion(Object payload) {
        super(payload, VALIDACION);
    }

    public EvnRespLiquidacion(Object payload, String tipoRespuesta) {
        super(payload, tipoRespuesta);
    }

    public EvnRespLiquidacion(Turno turno) {
        super(turno, TURNO_ANTERIOR_VALIDADO);
    }

    public EvnRespLiquidacion(String matricula, String tipoRespuesta) {
        super(matricula, tipoRespuesta);
    }

    /**
     * @return
     */
    public Liquidacion getLiquidacion() {
        return liquidacion;
    }

    /**
     * @return
     */
    public Boolean getEsCajero() {
        return esCajero;
    }

    /**
     * @param boolean1
     */
    public void setEsCajero(Boolean boolean1) {
        esCajero = boolean1;
    }

    /**
     * @return
     */
    public Boolean getEsCajeroRegistro() {
        return esCajeroRegistro;
    }

    /**
     * @param boolean1
     */
    public void setEsCajeroRegistro(Boolean boolean1) {
        esCajeroRegistro = boolean1;
    }

    /**
     * @return
     */
    public double getTotalOtroImp() {
        return totalOtroImp;
    }

    /**
     * @param d
     */
    public void setTotalOtroImp(double d) {
        totalOtroImp = d;
    }

    /**
     * @return
     */
    public int getIdImprimible() {
        return idImprimible;
    }

    /**
     * @param i
     */
    public void setIdImprimible(int i) {
        idImprimible = i;
    }

    public List getTurnosFolioMig() {
        return turnosFolioMig;
    }

    public void setTurnosFolioMig(List turnosFolioMig) {
        this.turnosFolioMig = turnosFolioMig;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    private boolean alertasDocumentos;

    public boolean isAlertasDocumentos() {
        return alertasDocumentos;
    }

    public void setAlertasDocumentos(boolean alertasDocumentos) {
        this.alertasDocumentos = alertasDocumentos;
    }

    public List getCanalesXCirculo() {
        return canalesXCirculo;
    }

    public void setCanalesXCirculo(List canalesXCirculo) {
        this.canalesXCirculo = canalesXCirculo;
    }

    public boolean isCertificadoEspecial() {
        return isCertificadoEspecial;
    }

    public void setCertificadoEspecial(boolean isCertificadoEspecial) {
        this.isCertificadoEspecial = isCertificadoEspecial;
    }

    public boolean isCertificadoTramite() {
        return isCertificadoTramite;
    }

    public void setCertificadoTramite(boolean isCertificadoTramite) {
        this.isCertificadoTramite = isCertificadoTramite;
    }

    public List getTurnoTramite() {
        return turnoTramite;
    }

    public void setTurnoTramite(List turnoTramite) {
        this.turnoTramite = turnoTramite;
    }
    
    public boolean isCertificadoActuacion(){
        return isCertificadoActuacion;
    }
    
    public void setCertificadoActuacion(boolean isCertificadoActuacion){
        this.isCertificadoActuacion = isCertificadoActuacion;
    }

}

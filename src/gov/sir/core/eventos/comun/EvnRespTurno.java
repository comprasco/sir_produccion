package gov.sir.core.eventos.comun;

import java.util.List;
import java.util.Map;

import com.is21.core.web.paginador.IListaPorRangos;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Relacion;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.Turno;

/**
 * Este evento de respuesta trae información sobre turnos, de la capa de negocio
 *
 * @author dsalas
 */
public class EvnRespTurno extends EvnSIRRespuesta {

    //SIR-57 R
    /**
     * Constante que identifica que se desea crear un turno
     */
    public static final String CREAR = "CREAR";

    /**
     * Constante que identifica que se desea obtener una lista de turnos
     */
    public static final String LISTAR = "LISTAR";

    /**
     * Constante que identifica que se desea obtener una lista de turnos
     */
    public static final String LISTAR_TURNOS_ENTREGA_DOCUMENTOS = "LISTAR_TURNOS_ENTREGA_DOCUMENTOS";

    /**
     * Constante que identifica que se desea marcar el turno como entregado
     */
    public static final String ENTREGAR = "ENTREGAR";

    /**
     * Constante que identifica que se desea cancelar el turno a solicitud del
     * ciudadano
     */
    public static final String CANCELAR = "CANCELAR";

    /**
     * Constante que identifica que se desea obtener los detalles del turno
     */
    public static final String CONSULTAR = "CONSULTAR";

    /**
     * Constante que identifica que se desea consultar los turnos que pertenecen
     * a una relación
     */
    public static final String CONSULTAR_RELACION = "CONSULTAR_RELACION";

    /**
     *
     *
     *
     * /** Constante que identifica que se desea obtener los detalles del turno
     */
    public static final String CONSULTAR_CONFIRMACION = "CONSULTAR_CONFIRMACION";

    /**
     * Constante que identifica que se desea obtener los datos para iniciar una
     * fase
     */
    public static final String FASE_INICIAL = "FASE_INICIAL";

    public static final String ENTREGA_MASIVA_REPARTO_NOTARIAL = "ENTREGA_MASIVA_REPARTO_NOTARIAL";

    private List canalRecaudoYcuentas;
    
    /**
     * yeferson
     */
    private double valorLiquidaConserva;
    private double valorSinConservacion;
    private double valorConservaMayorValor ;
    private double valorSinConservaMayorValor ;
//fin
    private Turno turno;

    private String nombreFase;

    private List usuarios;
    
    private int notasInfomativasCal;

    private List turnoSirMig;

    private Folio folio;

    private Folio folioDefinitivo = null;

    private Map mapaTurnosHijos;

    private Map turnosCertificadosValidos;

    private Relacion relacion;

    private Turno turnoHijo;
    
    private int notasInformativasCal;
    
    private int notasICon;
    
    private int notasIConD;
    
    private int notasIConCo;
    
    private int notasIConCoD;
    
    private boolean turnoDependiente;

    private Turno turnoPadre;

    private IListaPorRangos listadoPorRangos;
    /**
     * Modifica Pablo Quintana Junio 20 2008 Variable para manejar el testamento
     * asociado a un turno
     */
    private Testamento testamento = null;

    private List turnosPosterioresConfTestamento;

    private List OficiosDevoluciones;
    /**
     * @author : Edgar Lora
     * @change : Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes
     * Reparto Notarial
     */
    private boolean enHorario;

    public IListaPorRangos getListadoPorRangos() {
        return listadoPorRangos;
    }

    public void setListadoPorRangos(IListaPorRangos listadoPorRangos) {
        this.listadoPorRangos = listadoPorRangos;
    }

    /**
     * Crea una nueva instancia de esta clase
     *
     * @param turnos la lista de turnos consultados
     */
    public EvnRespTurno(List turnos) {
        super(turnos, EvnRespTurno.LISTAR);
        this.valorLiquidaConserva = 0;
        this.valorSinConservacion =0;
        this. valorConservaMayorValor =0 ;
        this.valorSinConservaMayorValor=0 ;
        
    }

    /**
     * Crea una nueva instancia de esta clase
     *
     */
    public EvnRespTurno() {
        super(null, EvnRespTurno.FASE_INICIAL);
        this.valorLiquidaConserva = 0;
        this.valorSinConservacion =0;
        this. valorConservaMayorValor =0 ;
        this.valorSinConservaMayorValor=0 ;
    }

    /**
     * @param folio
     */
    public EvnRespTurno(Turno turno, Folio folio) {
        super(folio, EvnRespTurno.CONSULTAR);
        this.folio = folio;
        this.turno = turno;
        this.valorLiquidaConserva = 0;
        this.valorSinConservacion =0;
        this. valorConservaMayorValor =0 ;
        this.valorSinConservaMayorValor=0 ;
    }

    /**
     * @param folio
     */
    public EvnRespTurno(Map mapaTurnosHijos, Folio folio) {
        super(folio, EvnRespTurno.ENTREGA_MASIVA_REPARTO_NOTARIAL);
        this.folio = folio;
        this.mapaTurnosHijos = mapaTurnosHijos;
        this.valorLiquidaConserva = 0;
        this.valorSinConservacion =0;
        this. valorConservaMayorValor =0 ;
        this.valorSinConservaMayorValor=0 ;
    }

    /**
     * @param folio
     */
    public EvnRespTurno(Turno turno, List userNames, String faseId) {
        super(null, EvnRespTurno.CONSULTAR_CONFIRMACION);
        this.turno = turno;
        this.usuarios = userNames;
        this.nombreFase = faseId;
        this.valorLiquidaConserva = 0;
        this.valorSinConservacion =0;
        this. valorConservaMayorValor =0 ;
        this.valorSinConservaMayorValor=0 ;
    }

    /**
     * Devuelve la lista de turnos solicitados
     *
     * @return la lista de turnos para la fase que se solicitó
     */
    public List getTurnos() {
        return (List) getPayload();
    }

    /**
     * Devuelve el folio
     *
     * @return Folio
     */
    public Folio getFolio() {
        return folio;
    }

    /**
     * Devuelve el turno
     *
     * @return Turno
     */
    public Turno getTurno() {
        return turno;
    }

    /**
     * @return
     */
    public List getUsuarios() {
        return usuarios;
    }

    /**
     * @return
     */
    public String getNombreFase() {
        return nombreFase;
    }

    /**
     * @param string
     */
    public void setUsuarios(List listaUsuarios) {
        this.usuarios = listaUsuarios;
    }

    /**
     * @param folio
     */
    public EvnRespTurno(List turnos, String tipoEvento) {
        super(turnos, tipoEvento);
    }

    /**
     * @param object
     */
    public EvnRespTurno(Object object, String tipoEvento) {
        super(object, tipoEvento);
    }

    /**
     * @param string
     */
    public void setNombreFase(String string) {
        nombreFase = string;
    }

    public void setTurnoDependiente(boolean turnoDependiente) {
        this.turnoDependiente = turnoDependiente;
    }

    public boolean isTurnoDependiente() {
        return turnoDependiente;
    }

    public void setTurnoHijo(Turno turnoHijo) {
        this.turnoHijo = turnoHijo;
    }

    public Turno getTurnoHijo() {
        return turnoHijo;
    }

    public Map getMapaTurnosHijos() {
        return mapaTurnosHijos;
    }

    public void setMapaTurnosHijos(Map mapaTurnosHijos) {
        this.mapaTurnosHijos = mapaTurnosHijos;
    }

    public Map getTurnosCertificadosValidos() {
        return turnosCertificadosValidos;
    }

    public void setTurnosCertificadosValidos(Map turnosCertificadosValidos) {
        this.turnosCertificadosValidos = turnosCertificadosValidos;
    }

    public Relacion getRelacion() {
        return relacion;
    }

    public void setRelacion(Relacion relacion) {
        this.relacion = relacion;
    }

    public List getTurnoSirMig() {
        return turnoSirMig;
    }

    public void setTurnoSirMig(List turnoSirMig) {
        this.turnoSirMig = turnoSirMig;
    }

    public Turno getTurnoPadre() {
        return turnoPadre;
    }

    public void setTurnoPadre(Turno turnoPadre) {
        this.turnoPadre = turnoPadre;
    }

    public Testamento getTestamento() {
        return testamento;
    }

    public void setTestamento(Testamento testamento) {
        this.testamento = testamento;
    }

    public List getTurnosPosterioresConfTestamento() {
        return turnosPosterioresConfTestamento;
    }

    public void setTurnosPosterioresConfTestamento(
            List turnosPosterioresConfTestamento) {
        this.turnosPosterioresConfTestamento = turnosPosterioresConfTestamento;
    }

    public void setFolio(Folio folio) {
        this.folio = folio;
    }

    public Folio getFolioDefinitivo() {
        return folioDefinitivo;
    }

    public void setFolioDefinitivo(Folio folioDefinitivo) {
        this.folioDefinitivo = folioDefinitivo;
    }

    public List getOficiosDevoluciones() {
        return OficiosDevoluciones;
    }

    public void setOficiosDevoluciones(List oficiosDevoluciones) {
        OficiosDevoluciones = oficiosDevoluciones;
    }

    /**
     * @author : Edgar Lora
     * @change : Mantis 0010397: Acta - Requerimiento No 063_151 - Ajustes
     * Reparto Notarial
     */
    public boolean isEnHorario() {
        return enHorario;
    }

    public void setEnHorario(boolean enHorario) {
        this.enHorario = enHorario;
    }

    /**
     * @return the valorLiquidaConserva
     */
    public double getValorLiquidaConserva() {
        return valorLiquidaConserva;
    }

    /**
     * @param valorLiquidaConserva the valorLiquidaConserva to set
     */
    public void setValorLiquidaConserva(double valorLiquidaConserva) {
        this.valorLiquidaConserva = valorLiquidaConserva;
    }

    /**
     * @return the valorSinConservacion
     */
    public double getValorSinConservacion() {
        return valorSinConservacion;
    }

    /**
     * @param valorSinConservacion the valorSinConservacion to set
     */
    public void setValorSinConservacion(double valorSinConservacion) {
        this.valorSinConservacion = valorSinConservacion;
    }

    /**
     * @return the valorConservaMayorValor
     */
    public double getValorConservaMayorValor() {
        return valorConservaMayorValor;
    }

    /**
     * @param valorConservaMayorValor the valorConservaMayorValor to set
     */
    public void setValorConservaMayorValor(double valorConservaMayorValor) {
        this.valorConservaMayorValor = valorConservaMayorValor;
    }

    /**
     * @return the valorSinConservaMayorValor
     */
    public double getValorSinConservaMayorValor() {
        return valorSinConservaMayorValor;
    }

    /**
     * @param valorSinConservaMayorValor the valorSinConservaMayorValor to set
     */
    public void setValorSinConservaMayorValor(double valorSinConservaMayorValor) {
        this.valorSinConservaMayorValor = valorSinConservaMayorValor;
    }
    
    //    CARLOS
    public List getCanalRecaudoYcuentas(){
        return canalRecaudoYcuentas;
}
    
    public void setCanalRecaudoYcuentas(List canalRecaudoYcuentas){
        this.canalRecaudoYcuentas = canalRecaudoYcuentas;
    }

    public int getNotasInformativasCal() {
        return notasInformativasCal;
    }

    public void setNotasInformativasCal(int notasInformativasCal) {
        this.notasInformativasCal = notasInformativasCal;
    }

    public int getNotasICon() {
        return notasICon;
    }

    public void setNotasICon(int notasICon) {
        this.notasICon = notasICon;
    }

    public int getNotasIConD() {
        return notasIConD;
    }

    public void setNotasIConD(int notasIConD) {
        this.notasIConD = notasIConD;
    }

    public int getNotasIConCo() {
        return notasIConCo;
    }

    public void setNotasIConCo(int notasIConCo) {
        this.notasIConCo = notasIConCo;
    }

    public int getNotasIConCoD() {
        return notasIConCoD;
    }

    public void setNotasIConCoD(int notasIConCoD) {
        this.notasIConCoD = notasIConCoD;
    }   
    
}

package gov.sir.core.eventos.administracion;

import java.util.List;
import java.util.Map;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Folio;
/* @author : CTORRES
        * @change : import necesario
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
 */
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Turno;

/**
 * Evento de Respuesta de solicitudes a la capa de negocio relacionadas con
 * operaciones sobre turnos entre usuarios de un mismo círculo
 *
 * @author jmendez
 */
public class EvnRespTrasladoTurno extends EvnSIRRespuesta {

    
    public static final String CONSULTAR_CANALES_Y_CUENTAS_POR_CIRCULO_OK
            = "CONSULTAR_CANALES_Y_CUENTAS_POR_CIRCULO_OK";

    /* detecta error en evento de respuesta
    dev 1 tsg
     */
//    private boolean existe_error_evento;

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de turnos y usuarios por círculo
     */
    public static final String CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO_OK
            = "CONSULTAR_TURNOS_Y_USUARIOS_POR_CIRCULO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de turnos por identificador
     */
    public static final String CONSULTAR_TURNOS_POR_IDENTIFICADOR_OK
            = "CONSULTAR_TURNOS_POR_IDENTIFICADOR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de anulacion
     * satisfactoria de turnos por identificador
     */
    public static final String ANULAR_TURNO_POR_IDENTIFICADOR_OK
            = "ANULAR_TURNO_POR_IDENTIFICADOR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de habilitar
     * satisfactoria de turnos por identificador
     */
    public static final String HABILITAR_TURNO_POR_IDENTIFICADOR_OK
            = "HABILITAR_TURNO_POR_IDENTIFICADOR_OK";

    /**
     * Esta constante se utiliza para identificar el evento de consulta
     * satisfactoria de turnos por identificador
     */
    public static final String CONSULTAR_TURNOS_POR_MATRICULA_OK = "CONSULTAR_TURNOS_POR_MATRICULA_OK";

    /**
     * Esta constante se utiliza para identificar el evento de ver los detalles
     * de un turno
     */
    public static final String VER_DETALLES_TURNO_OK = "VER_DETALLES_TURNO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de reasignación de
     * un turno
     */
    public static final String REASIGNAR_TURNO_OK = "REASIGNAR_TURNO_OK";

    /**
     * Esta constante se utiliza para identificar el evento de cambiar matricula
     * de certificado
     */
    public static final String TURNO_CERTIFICADO_CAMBIO_MATRICULA = "TURNO_CERTIFICADO_CAMBIO_MATRICULA";

    /**
     * Esta constante se utiliza para identificar el evento de cancelar cambiar
     * matricula de certificado
     */
    public static final String CANCELAR_CAMBIO_MATRICULA = "CANCELAR_CAMBIO_MATRICULA";

    /**
     * La respuesta de ciudadano para la edicion
     */
    public static final String VALIDAR_CIUDADANO_EDICION_OK = "VALIDAR_CIUDADANO_EDICION_OK";

    public static final String REALIZAR_CIUDADANO_EDICION_OK = "REALIZAR_CIUDADANO_EDICION_OK";

    public static final String REANOTAR_TURNO_ESPECIFICACION_OK = "REANOTAR_TURNO_ESPECIFICACION_OK";
    /* @author : CTORRES
        * @change : agregar constante CARGAR_TESTAMENTO_OK
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */
    public static final String CARGAR_TESTAMENTO_OK = "CARGAR_TESTAMENTO_OK";

    private List canalRecaudoYcuentas;
    /**
     * @autor:yeferson martinez
     * @change: caso 317097
     */
    private String obsPertenencia;

    /**
     * @autor:yeferson martinez
     * @change: caso 351144 consevacion documental
     */
    private String SumaConservacion;
    private String liquidaConservacionMayorvalor;

    //
    private List turnos;

    private List turnosAnteriores;

    private List turnosSiguientes;

    private List turnosAsociados;

    private Turno turno;

    private Turno turnoDerivado;

    private Turno turnoPadre;

    private Folio folio;

    private List listaTipoNota;

    private List listaTurnoFolioMig;

    private Ciudadano ciudadanoToEdit;

    private String mensajeEdicion;

    // Estacion de un turno asociado (Certificados asociados)
    private String idAdministradorSAS;

    // En caso de ser un turno de reparto notarial se debe guardar la categoria de la minuta
    private String categoriaMinuta;

    private List resoluciones;

    public List calificadores;

    /*
         * Se agrega al evento la propiedad turnosDevoluciones y sus metodos get y set
         * @author: Julio Alcazar
         * @change: 7393: Acta - Requerimiento No 215 - Ver Detalles de Turno - Turnos Devolucion Negados
     */
    private List turnosDevoluciones;

    /* @author : CTORRES
        * @change : se agregan atributos testamento y salvedadesTest
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */
    private Testamento testamento;
    /* @author : CTORRES
        * @change : se agregan atributo documento
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */
    private Documento documento;

    private Map salvedasTest;

    public List getTurnosDevoluciones() {
        return turnosDevoluciones;
    }

    public void setTurnosDevoluciones(List turnosDevoluciones) {
        this.turnosDevoluciones = turnosDevoluciones;
    }

    public String getCategoriaMinuta() {
        return categoriaMinuta;
    }

    public void setCategoriaMinuta(String categoriaMinuta) {
        this.categoriaMinuta = categoriaMinuta;
    }

    /**
     * @param payload
     */
    public EvnRespTrasladoTurno(Object payload) {
        super(payload);
    }

    /**
     * @param payload
     * @param tipoEvento
     */
    public EvnRespTrasladoTurno(Object payload, String tipoEvento) {
        super(payload, tipoEvento);
    }

    /**
     * @param payload
     * @param tipoEvento
     */
    public EvnRespTrasladoTurno(Turno turno, List turnosAnteriores, List turnosSiguientes, String tipoEvento) {
        super(tipoEvento);
        this.turno = turno;
        this.turnosAnteriores = turnosAnteriores;
        this.turnosSiguientes = turnosSiguientes;
        this.setTipoEvento(tipoEvento);
    }

    /**
     * @param payload
     * @param tipoEvento
     */
    public EvnRespTrasladoTurno(Turno turno, String tipoEvento) {
        super(tipoEvento);
        this.turno = turno;
        this.setTipoEvento(tipoEvento);
    }

    /**
     * @return
     */
    public List getTurnos() {
        return turnos;
    }

    /**
     * @return
     */
    public List getTurnosAnteriores() {
        return turnosAnteriores;
    }

    /**
     * @return
     */
    public List getTurnosSiguientes() {
        return turnosSiguientes;
    }

    /**
     * @return
     */
    public Turno getTurno() {
        return turno;
    }

    /**
     * @param list
     */
    public void setTurnos(List list) {
        turnos = list;
    }

    /**
     * @return
     */
    public List getTurnosAsociados() {
        return turnosAsociados;
    }

    /**
     * @param list
     */
    public void setTurnosAsociados(List list) {
        turnosAsociados = list;
    }

    private Map imprimiblesPendientesTurnoPadre;
    private Map imprimiblesPendientesTurnoHijos;
    private int imprimiblesPendientesTurnoPadreCount;
    private int imprimiblesPendientesTurnoHijosCount;

    private boolean turnoInternet;

    public Map getImprimiblesPendientesTurnoHijos() {
        return imprimiblesPendientesTurnoHijos;
    }

    public void setTurnoPadre(Turno turnoPadre) {
        this.turnoPadre = turnoPadre;
    }

    public Turno getTurnoPadre() {
        return turnoPadre;
    }

    public void setTurnoDerivado(Turno turnoDerivado) {
        this.turnoDerivado = turnoDerivado;
    }

    public Turno getTurnoDerivado() {
        return turnoDerivado;
    }

    public void setImprimiblesPendientesTurnoHijos(
            Map imprimiblesPendientesTurnoHijos) {
        this.imprimiblesPendientesTurnoHijos = imprimiblesPendientesTurnoHijos;
    }

    public Map getImprimiblesPendientesTurnoPadre() {
        return imprimiblesPendientesTurnoPadre;
    }

    public void setImprimiblesPendientesTurnoPadre(
            Map imprimiblesPendientesTurnoPadre) {
        this.imprimiblesPendientesTurnoPadre = imprimiblesPendientesTurnoPadre;
    }

    public int getImprimiblesPendientesTurnoHijosCount() {
        return imprimiblesPendientesTurnoHijosCount;
    }

    public void setImprimiblesPendientesTurnoHijosCount(
            int imprimiblesPendientesTurnoHijosCount) {
        this.imprimiblesPendientesTurnoHijosCount = imprimiblesPendientesTurnoHijosCount;
    }

    public int getImprimiblesPendientesTurnoPadreCount() {
        return imprimiblesPendientesTurnoPadreCount;
    }

    public void setImprimiblesPendientesTurnoPadreCount(
            int imprimiblesPendientesTurnoPadreCount) {
        this.imprimiblesPendientesTurnoPadreCount = imprimiblesPendientesTurnoPadreCount;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Folio getFolio() {
        return folio;
    }

    public void setFolio(Folio folio) {
        this.folio = folio;
    }

    public List getListaTipoNota() {
        return listaTipoNota;
    }

    public void setListaTipoNota(List listaTipoNota) {
        this.listaTipoNota = listaTipoNota;
    }

    public List getListaTurnoFolioMig() {
        return listaTurnoFolioMig;
    }

    public void setListaTurnoFolioMig(List listaTurnoFolioMig) {
        this.listaTurnoFolioMig = listaTurnoFolioMig;
    }

    /*Obtiene la estacion de un turno asociado (Certificados Asociados)*/
    public String getIdAdministradorSAS() {
        return idAdministradorSAS;
    }

    /*Modifica la estacion de un turno asociado (Certificados Asociados)*/
    public void setIdAdministradorSAS(String idAdministradorSAS) {
        this.idAdministradorSAS = idAdministradorSAS;
    }

    public Ciudadano getCiudadanoToEdit() {
        return ciudadanoToEdit;
    }

    public void setCiudadanoToEdit(Ciudadano ciudadanoToEdit) {
        this.ciudadanoToEdit = ciudadanoToEdit;
    }

    public String getMensajeEdicion() {
        return mensajeEdicion;
    }

    public void setMensajeEdicion(String mensajeEdicion) {
        this.mensajeEdicion = mensajeEdicion;
    }

    public void setTurnoInternet(boolean turnoInternet) {
        this.turnoInternet = turnoInternet;
    }

    public boolean isTurnoInternet() {
        return this.turnoInternet;
    }

    public List getResoluciones() {
        return resoluciones;
    }

    public void setResoluciones(List resoluciones) {
        this.resoluciones = resoluciones;
    }

    public List getCalificadores() {
        return calificadores;
    }

    public void setCalificadores(List calificadores) {
        this.calificadores = calificadores;
    }

    /* @author : CTORRES
        * @change : se agregan metodos accesores para los atributos testamento y salvedadesTest
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
     */
    public Map getSalvedadTest() {
        return salvedasTest;
    }

    public void setSalvedasTest(Map salvedasTest) {
        this.salvedasTest = salvedasTest;
    }

    public Testamento getTestamento() {
        return testamento;
    }

    public void setTestamento(Testamento testamento) {
        this.testamento = testamento;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    /**
     * @return the obsPertenencia
     */
    public String getObsPertenencia() {
        return obsPertenencia;
    }

    /**
     * @param obsPertenencia the obsPertenencia to set
     */
    public void setObsPertenencia(String obsPertenencia) {
        this.obsPertenencia = obsPertenencia;
    }

    /**
     * @autor:yeferson martinez
     * @change: caso 351144 conservacion documental
     */
    /**
     * @return the SumaConservacion
     */
    public String getSumaConservacion() {
        return SumaConservacion;
    }

    /**
     * @param SumaConservacion
     */
    public void setSumaConservacion(String SumaConservacion) {
        this.SumaConservacion = SumaConservacion;
    }

//    public boolean isExiste_error_evento() {
//        return existe_error_evento;
//    }

    /**
     * @return the liquidaConservacionMayorvalor
     */
    public String getLiquidaConservacionMayorvalor() {
        return liquidaConservacionMayorvalor;
    }

    /**
     * @param liquidaConservacionMayorvalor the liquidaConservacionMayorvalor to
     * set
     */
    public void setLiquidaConservacionMayorvalor(String liquidaConservacionMayorvalor) {
        this.liquidaConservacionMayorvalor = liquidaConservacionMayorvalor;
    }

//    public void setExiste_error_evento(boolean existe_error_evento) {
//        this.existe_error_evento = existe_error_evento;
//    }

    public List getCanalRecaudoYcuentas(){
        return canalRecaudoYcuentas;
}
    
    public void setCanalRecaudoYcuentas(List canalRecaudoYcuentas){
        this.canalRecaudoYcuentas = canalRecaudoYcuentas;
    }
}

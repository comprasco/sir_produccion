package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CModoBloqueo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Almacena los datos correspondientes a la definicion de Turno
 *
 * @author I.Siglo21
 */
public class TurnoEnhanced extends Enhanced {

    private SolicitudEnhanced solicitud;
    private String anio; // pk 
    private String idCirculo; // pk 
    private long idProceso; // pk 
    private String idTurno; // pk 
    private String ultimaRespuesta;
    private String turnoREL;
    private String reasignacion;
    private CirculoProcesoEnhanced circuloproceso;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean error;
    private boolean revocatoria;
    private UsuarioEnhanced usuarioDestino;
    private int modoBloqueo = CModoBloqueo.NORMAL;
    private String idRelacionActual;
    private String idFaseRelacionActual;
    private String observacionesAnulacion;
    private String observacionesHabilitar;
    private String anulado = "N";
    private UsuarioEnhanced usuarioAnulacion;
    private boolean nacional;
    private String idMatriculaUltima;

    /**
     * @link aggregation
     * @associates <{gov.sir.core.negocio.modelo.Nota}> @supplierCardinality
     * 0..* @clientCardinality 1
     */
    private List notas = new ArrayList(); // contains Nota  inverse Nota.turno

    //	...
    private List historial = new ArrayList(); // contains TurnoHistoria  inverse TurnoHistoria.turno

    //	...
    private String idWorkflow;
    private long lastIdHistoria;
    private long lastIdNota;

    /**
     * Variable que almacena el identificador unico para la fase en la cual se
     * encuentra el turno
     */
    private String idFase;

    /**
     * Variable que almacena la descripcion del turno
     */
    private String descripcion;

    private String relStat;
    private String relEndpoint;
    /**
     * Método constructor por defecto de la clase Turno. Crea una nueva
     * instancia de la clase Turno.
     */
    public TurnoEnhanced() {
        this.notas = new ArrayList();
    }

    /**
     * Indica la consistencia del folio con respecto a WF (0,1,2) 0=tiene
     * wokflow consistente, 1=tiene workflow inconsistente, 2=No tiene Workflow
     */
    private int consistenciaWF;

    /**
     * Metodo constructor con valores especificos. Crea una nueva instancia de
     * la clase Turno
     *
     * @param id tiene la informacion del identificador del turno
     * @param solicitante tiene la informacion del solicitante que le pertenece
     * el turno
     * @param notasInf tiene todas la informacion acerca de la notas
     * informativas asociadas al turno
     */
    public TurnoEnhanced(String idTurno, long idProceso, String idFase,
            List notasInf, String descripcion) {
        this.idTurno = idTurno;
        this.idProceso = idProceso;
        this.idFase = idFase;

        if (notasInf != null) {
            this.notas = notasInf;
        } else {
            this.notas = new ArrayList();
        }

        this.descripcion = descripcion;
    }

    public String getIdMatriculaUltima() {
        return idMatriculaUltima;
    }

    public void setIdMatriculaUltima(String idMatriculaUltima) {
        this.idMatriculaUltima = idMatriculaUltima;
    }

    /**
     * Este método retorna el valor del identificador del turno.
     *
     * @return Un número (long) positivo, diferente de 0
     */
    public String getIdTurno() {
        return idTurno;
    }

    /**
     * Este método cambia el valor que tenga el identificador del turno.
     *
     * @param idTurno variable tipo long que tiene el nuevo identificador del
     * turno
     */
    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    /**
     * Este método retorna el valor del identificador de la fase asocioada al
     * turno.
     *
     * @return Un número (long) positivo, diferente de 0
     */
    public String getIdFase() {
        return idFase;
    }

    /**
     * Este método cambia el valor que tenga el identificador de la fase
     * asocioada al turno.
     *
     * @param idFase variable tipo long que tiene el nuevo identificador del
     * turno
     */
    public void setIdFase(String idFase) {
        this.idFase = idFase;
    }

    /**
     * Este método retorna el valor de la descripcion del turno.
     *
     * @return Una cadena (String) no vacia, diferente de nulo.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Este método cambia el valor que tenga la descripcion del turno.
     *
     * @param descripcion variable tipo String que tiene la nueva descripcion
     * del turno
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return
     */
    public SolicitudEnhanced getSolicitud() {
        return solicitud;
    }

    /**
     * @param solicitud
     */
    public void setSolicitud(SolicitudEnhanced solicitud) {
        this.solicitud = solicitud;
    }

    public List getNotas() {
        return Collections.unmodifiableList(notas);
    }

    public boolean addNota(NotaEnhanced newNota) {
        return notas.add(newNota);
    }

    public boolean removeNota(NotaEnhanced oldNota) {
        return notas.remove(oldNota);
    }

    public List getHistorials() {
        return Collections.unmodifiableList(historial);
    }

    public boolean addHistorial(TurnoHistoriaEnhanced newHistorial) {
        return historial.add(newHistorial);
    }

    public boolean removeHistorial(TurnoHistoriaEnhanced oldHistorial) {
        return historial.remove(oldHistorial);
    }

    public CirculoProcesoEnhanced getCirculoproceso() {
        return circuloproceso;
    }

    public void setCirculoproceso(CirculoProcesoEnhanced circuloproceso) {
        this.circuloproceso = circuloproceso;
        setIdCirculo(circuloproceso.getIdCirculo());
        setIdProceso(circuloproceso.getIdProceso());
        setAnio(circuloproceso.getAnio());
    }

    /**
     * @return
     */
    public String getIdCirculo() {
        return idCirculo;
    }

    /**
     * @param string
     */
    public void setIdCirculo(String string) {
        idCirculo = string;
    }

    /**
     * @return
     */
    public String getUltimaRespuesta() {
        return ultimaRespuesta;
    }

    /**
     * @param string
     */
    public void setUltimaRespuesta(String string) {
        ultimaRespuesta = string;
    }

    public String getTurnoREL() {
        return turnoREL;
    }

    public void setTurnoREL(String turnoREL) {
        this.turnoREL = turnoREL;
    }

    /**
     * @return
     */
    public String getAnio() {
        return anio;
    }

    /**
     * @param string
     */
    public void setAnio(String string) {
        anio = string;
    }

    /**
     * @param l
     */
    public void setIdProceso(long l) {
        idProceso = l;
    }

    /**
     * @return
     */
    public long getIdProceso() {
        return idProceso;
    }

    /**
     * @return
     */
    public String getIdWorkflow() {
        return idWorkflow;
    }

    /**
     * @return
     */
    public long getLastIdHistoria() {
        return lastIdHistoria;
    }

    /**
     * @param string
     */
    public void setIdWorkflow(String string) {
        idWorkflow = string;
    }

    /**
     * @param l
     */
    public void setLastIdHistoria(long l) {
        lastIdHistoria = l;
    }

    /**
     * @return
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @return
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param date
     */
    public void setFechaFin(Date date) {
        fechaFin = date;
    }

    /**
     * @param date
     */
    public void setFechaInicio(Date date) {
        fechaInicio = date;
    }

    public static TurnoEnhanced enhance(Turno turno) {
        return (TurnoEnhanced) Enhanced.enhance(turno);
    }

    /**
     * @return
     */
    public long getLastIdNota() {
        return lastIdNota;
    }

    /**
     * @param l
     */
    public void setLastIdNota(long l) {
        lastIdNota = l;
    }

    /**
     * @return
     */
    public boolean isError() {
        return error;
    }

    /**
     * @param b
     */
    public void setError(boolean b) {
        error = b;
    }

    /**
     * @return
     */
    public UsuarioEnhanced getUsuarioDestino() {
        return usuarioDestino;
    }

    /**
     * @param enhanced
     */
    public void setUsuarioDestino(UsuarioEnhanced enhanced) {
        usuarioDestino = enhanced;
    }

    /**
     * @return
     */
    public int getModoBloqueo() {
        return modoBloqueo;
    }

    /**
     * @param i
     */
    public void setModoBloqueo(int i) {
        modoBloqueo = i;
    }

    /**
     * @return
     */
    public int getConsistenciaWF() {
        return consistenciaWF;
    }

    /**
     * @param i
     */
    public void setConsistenciaWF(int i) {
        consistenciaWF = i;
    }

    public String getIdFaseRelacionActual() {
        return idFaseRelacionActual;
    }

    public void setIdFaseRelacionActual(String idFaseRelacionActual) {
        this.idFaseRelacionActual = idFaseRelacionActual;
    }

    public String getIdRelacionActual() {
        return idRelacionActual;
    }

    public void setIdRelacionActual(String idRelacionActual) {
        this.idRelacionActual = idRelacionActual;
    }

    public String getObservacionesAnulacion() {
        return observacionesAnulacion;
    }

    public void setObservacionesAnulacion(String observacionesAnulacion) {
        this.observacionesAnulacion = observacionesAnulacion;
    }

    public String getAnulado() {
        return anulado;
    }

    public void setAnulado(String anulado) {
        this.anulado = anulado;
    }

    public UsuarioEnhanced getUsuarioAnulacion() {
        return usuarioAnulacion;
    }

    public void setUsuarioAnulacion(UsuarioEnhanced usuarioAnulacion) {
        this.usuarioAnulacion = usuarioAnulacion;
    }

    /**
     * @return
     */
    public boolean isRevocatoria() {
        return revocatoria;
    }

    /**
     * @param b
     */
    public void setRevocatoria(boolean b) {
        revocatoria = b;
    }

    public boolean isNacional() {
        return nacional;
    }

    public void setNacional(boolean nacional) {
        this.nacional = nacional;
    }

    public String getObservacionesHabilitar() {
        return observacionesHabilitar;
    }

    public void setObservacionesHabilitar(String observacionesHabilitar) {
        this.observacionesHabilitar = observacionesHabilitar;
    }

    public String getReasignacion() {
        return reasignacion;
    }

    public void setReasignacion(String reasignacion) {
        this.reasignacion = reasignacion;
    }

    public String getRelStat() {
        return relStat;
    }

    public void setRelStat(String relStat) {
        this.relStat = relStat;
    }

    public String getRelEndpoint() {
        return relEndpoint;
    }

    public void setRelEndpoint(String relEndpoint) {
        this.relEndpoint = relEndpoint;
    }
        
}

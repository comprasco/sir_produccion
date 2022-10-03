package gov.sir.core.eventos.administracion;

import java.util.List;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas con la
 * impresión de un folio
 *
 * @author jmendez
 */
public class EvnImprimirFolio extends EvnSIR {

    /**
     * Esta constante se utiliza para identificar el evento de impresión de un
     * folio
     */
    public static final String IMPRIMIR_FOLIO = "IMPRIMIR_FOLIO";

    /**
     * Esta constante se utiliza para identificar el evento de impresión de un
     * CERTIFICADO
     */
    public static final String IMPRIMIR_CERTIFICADO = "IMPRIMIR_CERTIFICADO";

    /**
     * Esta constante se utiliza para identificar el evento de impresión de un
     * CERTIFICADO
     */
    public static final String IMPRIMIR_CERTIFICADO_PERTENENCIA = "IMPRIMIR_CERTIFICADO_PERTENENCIA";
    /**
     * Constante que identifica la acción de reimprimir un RECIBO
     */
    public static final String REIMPRIMIR_RECIBO = "REIMPRIMIR_RECIBO";

    /**
     * Constante que identifica la acción de reimprimir una CONSULTA
     */
    public static final String REIMPRIMIR_CONSULTA = "REIMPRIMIR_CONSULTA";

    public static final String OBTENER_IMPRESORAS_CIRCULO = "OBTENER_IMPRESORAS_CIRCULO";

    public static final String OBTENER_IMPRESORAS_CIRCULO_PERTENENCIA = "OBTENER_IMPRESORAS_CIRCULO_PERTENENCIA";

    public static final String OBTENER_ULTIMO_TURNO_IMPRESO = "OBTENER_ULTIMO_TURNO_IMPRESO";

    public static final String OBTENER_ULTIMO_TURNO_IMPRESO_PROCESO = "OBTENER_ULTIMO_TURNO_IMPRESO_PROCESO";

    //******* SE AGREGA NUEVA LINEA PARA OBTENER LA ULTIMA SOLICITUD DE REIMPRESION DE LIQUIDACION *********
    public static final String OBTENER_ULTIMA_SOLICITUD_LIQUIDACION = "OBTENER_ULTIMA_SOLICITUD_LIQUIDACION";

    public static final String REIMPRIMIR_RECIBOS = "REIMPRIMIR_RECIBOS";
    
    public static final String REIMPRIMIR_SOLICITUD_LIQUIDACION= "REIMPRIMIR_SOLICITUD_LIQUIDACION";

    /**
     * Constante que identifica la acción de programar tarea
     */
    public static final String AGREGAR_DE_ARCHIVO = "AGREGAR_DE_ARCHIVO";

    /**
     * Constante que identifica la acción de programar tarea
     */
    public static final String PROGRAMAR_TAREA = "PROGRAMAR_TAREA";

    /**
     * Tipo de Nota
     */
    private String tipoNota;

    /**
     * Descripción de la nota
     */
    private String descripcionNota;

    /**
     * Objeto folio que se debe imprimir
     */
    private Folio folio;

    /**
     * UID del cliente para enviar a impresión
     */
    private String uid;

    /**
     * Turno asociado
     */
    private Turno turno;

    /**
     * Circulo asociado
     */
    private Circulo cir;

    /**
     * Usuario que genera la impresión
     */
    private gov.sir.core.negocio.modelo.Usuario usuarioNeg;

    /**
     * Idetificador del turno
     */
    private String idTurno;

    /**
     * Idetificador de la impresoara
     */
    private String impresora;

    /**
     * Idetificador de la impresoara
     */
    private int numeroImpresiones;

    /**
     * Lista de roles del usuario
     */
    private List roles;

    private long proceso;

    private String[] turnosReimprimir;    

    private boolean mayorValor;

    private String nombreTarea;

    private String fechaTarea;

    private String urlArchivo;

    private String tipoTarea;

    private String nombreArchivo;
    
    private boolean isCertificadoEspecial;
    
    private StringBuffer listaFolios;

    public boolean isMayorValor() {
        return mayorValor;
    }

    public void setMayorValor(boolean mayorValor) {
        this.mayorValor = mayorValor;
    }

    /**
     * @param usuario
     */
    public EvnImprimirFolio(Usuario usuario) {
        super(usuario);
    }

    /**
     * @param usuario
     * @param tipoEvento
     */
    public EvnImprimirFolio(Usuario usuario, String tipoEvento) {
        super(usuario, tipoEvento);
    }

    /**
     * @param usuario
     * @param nombreArchvio
     * @param listaFolios
     * @param tipoEvento
     */
    public EvnImprimirFolio(Usuario usuario, StringBuffer listaFolios, String nombreArchvio, String tipoEvento) {
        super(usuario, tipoEvento);
        this.nombreArchivo = nombreArchvio;
        this.listaFolios = listaFolios;
    }

    /**
     * @param usuario
     * @param tipoEvento
     */
    public EvnImprimirFolio(Usuario usuario, String nombreTarea, String fechaTarea, StringBuffer listaFolios, String tipoTarea, String tipoEvento) {
        super(usuario, tipoEvento);
        this.nombreTarea = nombreTarea;
        this.fechaTarea = fechaTarea;
        this.tipoTarea = tipoTarea;
        this.listaFolios = listaFolios;
    }

    /**
     * Obtener el atributo folio
     *
     * @return Retorna el atributo folio.
     */
    public Folio getFolio() {
        return folio;
    }

    /**
     * Actualizar el valor del atributo folio
     *
     * @param folio El nuevo valor del atributo folio.
     */
    public void setFolio(Folio folio) {
        this.folio = folio;
    }

    /**
     * Obtener el atributo idTurno
     *
     * @return Retorna el atributo idTurno.
     */
    public String getIdTurno() {
        return idTurno;
    }

    /**
     * Actualizar el valor del atributo idTurno
     *
     * @param idTurno El nuevo valor del atributo idTurno.
     */
    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    /**
     * Obtener el atributo turno
     *
     * @return Retorna el atributo turno.
     */
    public Turno getTurno() {
        return turno;
    }

    /**
     * Actualizar el valor del atributo turno
     *
     * @param turno El nuevo valor del atributo turno.
     */
    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    /**
     * Obtener el atributo uid
     *
     * @return Retorna el atributo uid.
     */
    public String getUid() {
        return uid;
    }

    /**
     * Actualizar el valor del atributo uid
     *
     * @param uid El nuevo valor del atributo uid.
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Obtener el atributo usuarioNeg
     *
     * @return Retorna el atributo usuarioNeg.
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuarioNeg() {
        return usuarioNeg;
    }

    /**
     * Actualizar el valor del atributo usuarioNeg
     *
     * @param usuarioNeg El nuevo valor del atributo usuarioNeg.
     */
    public void setUsuarioNeg(gov.sir.core.negocio.modelo.Usuario usuarioNeg) {
        this.usuarioNeg = usuarioNeg;
    }

    public Circulo getCirculo() {
        return cir;
    }

    public void setCirculo(Circulo cir) {
        this.cir = cir;
    }

    public String getImpresoraSeleccionada() {
        return impresora;
    }

    public void setImpresoraSeleccionada(String impresora) {
        this.impresora = impresora;
    }

    public int getNumeroImpresiones() {
        return numeroImpresiones;
    }

    public void setNumeroImpresiones(int numeroImpresiones) {
        this.numeroImpresiones = numeroImpresiones;
    }

    /**
     * @return
     */
    public String getDescripcionNota() {
        return descripcionNota;
    }

    /**
     * @return
     */
    public String getTipoNota() {
        return tipoNota;
    }

    /**
     * @param string
     */
    public void setDescripcionNota(String string) {
        descripcionNota = string;
    }

    /**
     * @param string
     */
    public void setTipoNota(String string) {
        tipoNota = string;
    }

    public List getRoles() {
        return roles;
    }

    public void setRoles(List roles) {
        this.roles = roles;
    }

    private Estacion estacion;

    /**
     * @param estacion
     */
    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;

    }

    /**
     * @return
     */
    public Estacion getEstacion() {

        return estacion;
    }

    /**
     * @return Returns the proceso.
     */
    public long getProceso() {
        return proceso;
    }

    /**
     * @param proceso The proceso to set.
     */
    public void setProceso(long proceso) {
        this.proceso = proceso;
    }

    /**
     * @return Returns the turnosReimprimir.
     */
    public String[] getTurnosReimprimir() {
        return turnosReimprimir;
    }

    /**
     * @param turnosReimprimir The turnosReimprimir to set.
     */
    public void setTurnosReimprimir(String[] turnosReimprimir) {
        this.turnosReimprimir = turnosReimprimir;
    }

    public String getFechaTarea() {
        return fechaTarea;
    }

    public void setFechaTarea(String fechaTarea) {
        this.fechaTarea = fechaTarea;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public StringBuffer getListaFolios() {
        return listaFolios;
    }

    public void setListaFolios(StringBuffer listaFolios) {
        this.listaFolios = listaFolios;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public String getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(String tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public boolean isCertificadoEspecial() {
        return isCertificadoEspecial;
    }

    public void setCertificadoEspecial(boolean isCertificadoEspecial) {
        this.isCertificadoEspecial = isCertificadoEspecial;
    }
    
    
}

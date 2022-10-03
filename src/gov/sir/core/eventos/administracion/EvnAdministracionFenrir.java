package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.FormaPagoCampos;
import gov.sir.core.negocio.modelo.TipoPago;
import java.util.Date;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Nivel;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

import java.util.List;
import org.apache.commons.fileupload.FileItem;

/**
 * Evento de Envio de solicitudes a la capa de negocio relacionadas con la
 * administración de objetos de Fenrir.
 *
 * @author jmendez
 */
public class EvnAdministracionFenrir extends EvnSIR {

    /**
     * Esta constante se utiliza para identificar el evento de consulta de
     * usuarios
     */
    public static final String CONSULTAR_USUARIOS = "CONSULTAR_USUARIOS";

    /**
     * Esta constante se utiliza para identificar el evento de consulta de
     * usuarios por circulo
     */
    public static final String CONSULTAR_USUARIOS_POR_CIRCULO = "CONSULTAR_USUARIOS_POR_CIRCULO";

    /**
     * Esta constante se utiliza para identificar el evento de creación de
     * responsabilidades
     */
    public static final String RESPONSABILIDAD_CREAR = "RESPONSABILIDAD_CREAR";

    /**
     * Esta constante se utiliza para identificar el evento de creación de roles
     */
    public static final String ROL_CREAR = "ROL_CREAR";

    /**
     * Esta constante se utiliza para identificar el evento de creación de
     * niveles
     */
    public static final String NIVEL_CREAR = "NIVEL_CREAR";

    /**
     * Esta constante se utiliza para identificar el evento de eliminación de
     * niveles
     */
    public static final String NIVEL_ELIMINAR = "NIVEL_ELIMINAR";

    /**
     * Esta constante se utiliza para identificar el evento de cambio de clave
     * para un usuario
     */
    public static final String USUARIO_CAMBIAR_CLAVE = "USUARIO_CAMBIAR_CLAVE";

    /**
     * Esta constante se utiliza para identificar el evento para la habilitación
     * de un usuario en el sistema
     */
    public static final String USUARIO_HABILITAR = "USUARIO_HABILITAR";

    /**
     * Esta constante se utiliza para identificar el evento para actualizar los
     * datos que se modificaron a un ciudadano
     */
    public static final String USUARIOS_ACTUALIZAR_INFORMACION = "USUARIOS_ACTUALIZAR_INFORMACION";

    /**
     * Esta constante se utiliza para identificar el evento de creación de un
     * usuario
     */
    public static final String USUARIO_CREAR = "USUARIO_CREAR";

    /**
     * Constante que se utuliza para identificar el evento consultar rol para
     * editarlo
     */
    public static final String CONSULTAR_ROL_EDITAR = "CONSULTAR_ROL_EDITAR";

    /**
     * Constante que se utuliza para identificar el evento consultar rol para
     * editarlo
     */
    public static final String EDITAR_ROL = "EDITAR_ROL";

    public static final String CONSULTAR_RELACIONES_USUARIO = "CONSULTAR_RELACIONES_USUARIO";

    public static final String CARGAR_ESTACIONES_ROL = "CARGAR_ESTACIONES_ROL";

    public static final String CARGAR_ESTACIONES_ROL_CIRCULO = "CARGAR_ESTACIONES_ROL_CIRCULO";

    public static final String CARGAR_ESTACIONES_CIRCULO = "CARGAR_ESTACIONES_CIRCULO";

    public static final String ELIMINAR_ROL_ESTACION_USUARIO = "ELIMINAR_ROL_ESTACION_USUARIO";

    public static final String ACTUALIZAR_ESTADO_REL_ROL_ESTACION = "ACTUALIZAR_ESTADO_REL_ROL_ESTACION";

    public static final String AGREGAR_ROL_ESTACION_USUARIO = "AGREGAR_ROL_ESTACION_USUARIO";

    public static final String AGREGAR_ROL_ESTACION = "AGREGAR_ROL_ESTACION";

    public static final String ELIMINAR_ROL_ESTACION = "ELIMINAR_ROL_ESTACION";

    public static final String CARGAR_USUARIOS_ROL_ESTACION = "CARGAR_USUARIOS_ROL_ESTACION";

    public static final String AGREGAR_NUEVO_USUARIO_ROL_ESTACION = "AGREGAR_NUEVO_USUARIO_ROL_ESTACION";

    public static final String ELIMINAR_USUARIO_ROL_ESTACION_ACTUAL = "ELIMINAR_USUARIO_ROL_ESTACION_ACTUAL";

    public static final String CONSULTAR_USUARIO_CON_RELACIONES = "CONSULTAR_USUARIO_CON_RELACIONES";

    public static final String MOSTRAR_CIRCULO_USUARIO = "MOSTRAR_CIRCULO_USUARIO";

    public static final String CONSULTAR_JUSTIFICACIONES_USUARIO = "CONSULTAR_JUSTIFICACIONES_USUARIO";
    
    public static final String CONSULTAR_TIPO_JUSTIFICACIONES = "CONSULTAR_TIPO_JUSTIFICACIONES";
    
    public static final String CONSULTAR_CAMPOS_CAPTURA = "CONSULTAR_CAMPOS_CAPTURA";
    
    public static final String ACTUALIZAR_ESTADO_CAMPOS_CAPTURA = "ACTUALIZAR_ESTADO_CAMPOS_CAPTURA";
    
    public static final String CARGAR_CAMPOS_CAPTURA = "CARGAR_CAMPOS_CAPTURA";
    
    public static final String CREAR_FORMA_PAGO = "CREAR_FORMA_PAGO";
    

    /**
     * Nombre del usuario
     */
    private String nombreUsuario;
    private Rol rol;
    private Nivel nivel;
    private gov.sir.core.negocio.modelo.Usuario usuarioNegocio;
    private gov.sir.core.negocio.modelo.ArchivosJustifica archivosJustifica;
    private gov.sir.core.negocio.modelo.JustificaAdm justificaAdm;
    private gov.sir.core.negocio.modelo.JustificaTipos justificaTipos;
    private List roles;
    private Circulo circulo;
    private String idRol;
    private Estacion estacion;
    private Usuario usuarioPerfil;
    private String estadoRolEstacion;
    private String oldPassword;
    private FileItem fileItem;
    private List estaciones;
    private List estados;
    private String fechaIni;
    private String fechaFin;
    private String tipoJust;
    private TipoPago tipoPago;
    private FormaPagoCampos formaPagoCampos;
    private String nombreFormaPago;
    private String camposCaptura;

    /**
     * @param usuario
     */
    public EvnAdministracionFenrir(Usuario usuario) {
        super(usuario);
    }

    /**
     * @param usuario
     * @param tipoEvento
     */
    public EvnAdministracionFenrir(Usuario usuario, String tipoEvento) {
        super(usuario, tipoEvento);
    }

    /**
     * Obtener el atributo circulo
     *
     * @return Retorna el atributo circulo.
     */
    public Circulo getCirculo() {
        return circulo;
    }

    /**
     * Actualizar el valor del atributo circulo
     *
     * @param circulo El nuevo valor del atributo circulo.
     */
    public void setCirculo(Circulo circulo) {
        this.circulo = circulo;
    }

    /**
     * Obtener el atributo nivel
     *
     * @return Retorna el atributo nivel.
     */
    public Nivel getNivel() {
        return nivel;
    }

    /**
     * Actualizar el valor del atributo nivel
     *
     * @param nivel El nuevo valor del atributo nivel.
     */
    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    /**
     * Obtener el atributo nombreUsuario
     *
     * @return Retorna el atributo nombreUsuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Actualizar el valor del atributo nombreUsuario
     *
     * @param nombreUsuario El nuevo valor del atributo nombreUsuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtener el atributo rol
     *
     * @return Retorna el atributo rol.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Actualizar el valor del atributo rol
     *
     * @param rol El nuevo valor del atributo rol.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtener el atributo roles
     *
     * @return Retorna el atributo roles.
     */
    public List getRoles() {
        return roles;
    }

    /**
     * Actualizar el valor del atributo roles
     *
     * @param roles El nuevo valor del atributo roles.
     */
    public void setRoles(List roles) {
        this.roles = roles;
    }

    /**
     * Obtener el atributo usuarioNegocio
     *
     * @return Retorna el atributo usuarioNegocio.
     */
    public gov.sir.core.negocio.modelo.Usuario getUsuarioNegocio() {
        return usuarioNegocio;
    }

    /**
     * Actualizar el valor del atributo usuarioNegocio
     *
     * @param usuarioNegocio El nuevo valor del atributo usuarioNegocio.
     */
    public void setUsuarioNegocio(
            gov.sir.core.negocio.modelo.Usuario usuarioNegocio) {
        this.usuarioNegocio = usuarioNegocio;
    }

    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    public Usuario getUsuarioPerfil() {
        return usuarioPerfil;
    }

    public void setUsuarioPerfil(Usuario usuarioPerfil) {
        this.usuarioPerfil = usuarioPerfil;
    }

    public String getEstadoRolEstacion() {
        return estadoRolEstacion;
    }

    public void setEstadoRolEstacion(String estadoRolEstacion) {
        this.estadoRolEstacion = estadoRolEstacion;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public gov.sir.core.negocio.modelo.ArchivosJustifica getArchivosJustifica() {
        return archivosJustifica;
    }

    public void setArchivosJustifica(
            gov.sir.core.negocio.modelo.ArchivosJustifica archivosJustifica) {
        this.archivosJustifica = archivosJustifica;
    }

    public gov.sir.core.negocio.modelo.JustificaAdm getJustificaAdm() {
        return justificaAdm;
    }

    public void setJustificaAdm(
            gov.sir.core.negocio.modelo.JustificaAdm justificaAdm) {
        this.justificaAdm = justificaAdm;
    }

    public void setFileItem(FileItem fileItem) {
        this.fileItem = fileItem;
    }

    public FileItem getFileItem() {
        return fileItem;
    }

    public gov.sir.core.negocio.modelo.JustificaTipos getJustificaTipos() {
        return justificaTipos;
    }

    public void setJustificaTipos(
            gov.sir.core.negocio.modelo.JustificaTipos justificaTipos) {
        this.justificaTipos = justificaTipos;
    }

    /**
     * Obtener el atributo estaciones
     *
     * @return Retorna el atributo estaciones.
     */
    public List getEstaciones() {
        return estaciones;
    }

    /**
     * Actualizar el valor del atributo estaciones
     *
     * @param estaciones El nuevo valor del atributo estaciones.
     */
    public void setEstaciones(List estaciones) {
        this.estaciones = estaciones;
    }

    /**
     * Obtener el atributo estados
     *
     * @return Retorna el atributo estados.
     */
    public List getEstados() {
        return estados;
    }

    /**
     * Actualizar el valor del atributo estados
     *
     * @param estados El nuevo valor del atributo estados.
     */
    public void setEstados(List estados) {
        this.estados = estados;
    }
    
    public String getFechaIni() {
        return fechaIni;
    }
    
    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }
    
    public String getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public String getTipoJust() {
        return tipoJust;
    }
    
    public void setTipoJust(String tipoJust) {
        this.tipoJust = tipoJust;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public FormaPagoCampos getFormaPagoCampos() {
        return formaPagoCampos;
    }

    public void setFormaPagoCampos(FormaPagoCampos formaPagoCampos) {
        this.formaPagoCampos = formaPagoCampos;
    }

    public String getNombreFormaPago() {
        return nombreFormaPago;
    }

    public void setNombreFormaPago(String nombreFormaPago) {
        this.nombreFormaPago = nombreFormaPago;
    }

    public String getCamposCaptura() {
        return camposCaptura;
    }

    public void setCamposCaptura(String camposCaptura) {
        this.camposCaptura = camposCaptura;
    }
    
    
    
}

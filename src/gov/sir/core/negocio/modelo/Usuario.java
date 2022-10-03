package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase que modela la informacion de los usuarios registrados en el sistema
 */
/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class Usuario implements TransferObject {

    /**
     * @param enhanced
     */
    public Usuario(long idUsuario, String nombre, String password, String username, String loginID, String apellido1, String apellido2, boolean activo) {
        this.idUsuario = idUsuario; // pk 
        this.nombre = nombre;
        this.password = password;
        this.username = username;
        this.loginID = loginID;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.activo = activo;
    }
    private static final long serialVersionUID = 1L;
    private long idUsuario = 0; // pk 
    private String nombre;
    private String password;
    private String username;
    private String loginID;
    private List llavesBloqueo = new ArrayList(); // contains LlaveBloqueo
    private List usuarioCirculos = new ArrayList(); // contains UsuarioCirculo  inverse UsuarioCirculo.usuario
    private List subtiposAtencion = new ArrayList(); // contains UsuarioSubtipoAtencion  inverse UsuarioSubtipoAtencion.usuario
    private List repartos = new ArrayList(); // contains Reparto  inverse Reparto.usuario
    private String apellido1;
    private String apellido2;
    private boolean activo;

    /**
     * @author Cesar Ram�rez
     * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA Tipo de
     * Identificaci�n y N�mero de Identificaci�n del Usuario
	 *
     */
    private String tipoIdentificacion;
    private long numIdentificacion;

    /**
     * Atributo transiente que guarda los turnos asignados en un momento dado a
     * un usuario (Reparto de abogados en registro)
     */
    private long numeroTurnosAsignados;

    /**
     * Metodo constructor por defecto
     */
    public Usuario() {
        super();
    }

    public String getNombreCompletoUsuario() {
        return ((nombre == null) ? " " : nombre) + " " + ((apellido1 == null) ? " " : apellido1) + " " + ((apellido2 == null) ? " " : apellido2) + " ";
    }

    public Usuario(String username, String nombre, String pass, String loginID) {
        super();
        this.nombre = nombre;
        this.password = pass;
        this.loginID = loginID;
        this.username = username;
    }

    /**
     * Retorna el identificador del usuario
     */
    public long getIdUsuario() {
        return idUsuario;
    }

    /**
     * Modifica el identificador del usuario
     */
    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Retorna el nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retorna el login del usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Modifica el login del usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return
     */
    public String getLoginID() {
        return loginID;
    }

    /**
     * Este m�todo devuelve el usuario de Auriga a partir del usuario de sir
     *
     * @return org.auriga.core.modelo.transferObjects.Usuario con el id
     */
    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioAuriga() {
        org.auriga.core.modelo.transferObjects.Usuario usuario = new org.auriga.core.modelo.transferObjects.Usuario();
        usuario.setUsuarioId(this.getUsername());
        if (this.getIdUsuario() != 0) {
            usuario.setUsuarioNum(this.getIdUsuario());
        }
        return usuario;
    }

    /**
     * @param string
     */
    public void setLoginID(String string) {
        loginID = string;
    }

    /**
     * Retorna la lista llavesBloqueo
     */
    public List getLlavesBloqueos() {
        return Collections.unmodifiableList(llavesBloqueo);
    }

    /**
     * A�ade una llave bloqueo a la lista llavesBloqueo
     */
    public boolean addLlavesBloqueo(LlaveBloqueo newLlavesBloqueo) {
        return llavesBloqueo.add(newLlavesBloqueo);
    }

    /**
     * Elimina una llave bloqueo a la lista llavesBloqueo
     */
    public boolean removeLlavesBloqueo(LlaveBloqueo oldLlavesBloqueo) {
        return llavesBloqueo.remove(oldLlavesBloqueo);
    }

    /**
     * Retorna la lista usuarioCirculos
     */
    public List getUsuarioCirculos() {
        return Collections.unmodifiableList(usuarioCirculos);
    }

    /**
     * A�ade un UsuarioCirculo a la lista usuarioCirculos
     */
    public boolean addUsuarioCirculo(UsuarioCirculo newUsuarioCirculo) {
        return usuarioCirculos.add(newUsuarioCirculo);
    }

    /**
     * Elimina un UsuarioCirculo a la lista usuarioCirculos
     */
    public boolean removeUsuarioCirculo(UsuarioCirculo oldUsuarioCirculo) {
        return usuarioCirculos.remove(oldUsuarioCirculo);
    }

    /**
     * Retorna la lista repartos
     */
    public List getRepartos() {
        return Collections.unmodifiableList(repartos);
    }

    /**
     * A�ade un Reparto a la lista repartos
     */
    public boolean addReparto(Reparto newReparto) {
        return repartos.add(newReparto);
    }

    /**
     * Elimina un Reparto a la lista repartos
     */
    public boolean removeReparto(Reparto oldReparto) {
        return repartos.remove(oldReparto);
    }

    /**
     * Retorna la lista subtiposAtencion
     */
    public List getSubtiposAtencions() {
        return Collections.unmodifiableList(subtiposAtencion);
    }

    /**
     * A�ade un SubtipoAtencion a la lista subtiposAtencion
     */
    public boolean addSubtiposAtencion(
            UsuarioSubtipoAtencion newSubtiposAtencion) {
        return subtiposAtencion.add(newSubtiposAtencion);
    }

    public boolean addListaSubtiposAtencion(List newSubtiposAtencion) {
        return subtiposAtencion.addAll(newSubtiposAtencion);
    }

    /**
     * Elimina un SubtipoAtencion a la lista subtiposAtencion
     */
    public boolean removeSubtiposAtencion(
            UsuarioSubtipoAtencion oldSubtiposAtencion) {
        return subtiposAtencion.remove(oldSubtiposAtencion);
    }

    /**
     * Indica si el usuario se encuentra activo en el sistema
     *
     * @return
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Retorna el primer apellido del usuario
     *
     * @return
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Retorna el segundo apellido del usuario
     *
     * @return
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Indica si el usuario se encuentra activo en el sistema
     *
     * @param b
     */
    public void setActivo(boolean b) {
        activo = b;
    }

    /**
     * Modifica el primer apellido del usuario
     *
     * @param string
     */
    public void setApellido1(String string) {
        apellido1 = string;
    }

    /**
     * Modifica el segundo apellido del usuario
     *
     * @param string
     */
    public void setApellido2(String string) {
        apellido2 = string;
    }

    /**
     * @author Cesar Ram�rez
     * @change: 1247.ASIGNACION.SOLICITANTE.CORRECCION.INTERNA Metodos getter y
     * setter.
	 *
     */
    /**
     * @return the tipoIdentificacion *
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set *
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numIdentificacion *
     */
    public long getNumIdentificacion() {
        return numIdentificacion;
    }

    /**
     * @param numIdentificacion the numIdentificacion to set *
     */
    public void setNumIdentificacion(long numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    /**
     * @return
     */
    public long getNumeroTurnosAsignados() {
        return numeroTurnosAsignados;
    }

    /**
     * @param l
     */
    public void setNumeroTurnosAsignados(long l) {
        numeroTurnosAsignados = l;
    }

    /**
     *
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Usuario)) {
            return false;
        }

        final Usuario id = (Usuario) o;

        if (this.idUsuario != id.getIdUsuario()) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) idUsuario;

        return result;
    }

}

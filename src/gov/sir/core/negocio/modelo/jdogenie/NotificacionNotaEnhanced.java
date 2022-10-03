package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.NotificacionNota;
import java.util.Date;


/**
** @Author Cristian David Garcia
** @Requerimiento Catastro Multiproposito
*/
public class NotificacionNotaEnhanced extends Enhanced{
    private String idNotificacion; //pk
    private String destino;
    private String tipo;
    private int apoderado;
    private String nombres;
    private String apellidos;
    private String correo;
    private String tipoDocumento;
    private String documento;
    private String direccion;
    private String telefono;
    private Date fechaNotificacion;
    private String idOficinaOrigen;
    private String turnoWF;
    
    public NotificacionNotaEnhanced() {
    }

    public String getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getApoderado() {
        return apoderado;
    }

    public void setApoderado(int apoderado) {
        this.apoderado = apoderado;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public String getIdOficinaOrigen() {
        return idOficinaOrigen;
    }

    public void setIdOficinaOrigen(String idOficinaOrigen) {
        this.idOficinaOrigen = idOficinaOrigen;
    }

    public String getTurnoWF() {
        return turnoWF;
    }

    public void setTurnoWF(String turnoWF) {
        this.turnoWF = turnoWF;
    }
    
    public static NotificacionNotaEnhanced enhance(NotificacionNota notificacionNota) {
		return (NotificacionNotaEnhanced) Enhanced.enhance(notificacionNota);
	}
    
}

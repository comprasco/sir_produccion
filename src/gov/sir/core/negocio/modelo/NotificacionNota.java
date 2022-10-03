package gov.sir.core.negocio.modelo;

import java.util.Date;
import org.auriga.core.modelo.TransferObject;

/**
** @Author Cristian David Garcia
** @Requerimiento Catastro Multiproposito
*/
public class NotificacionNota implements TransferObject{
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
    private String turnoWF;
    private Date fechaNotificacion;
    private String idOficinaOrigen;
    
    public NotificacionNota() {
    }

    public NotificacionNota(String destino, String tipo, int apoderado, String nombres, String apellidos, String tipoDocumento, String documento, Date fechaNotificacion, String turnoWF) {
        this.destino = destino;
        this.tipo = tipo;
        this.apoderado = apoderado;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.fechaNotificacion = fechaNotificacion;
        this.turnoWF = turnoWF;
    }

    public NotificacionNota(String destino, String tipo, String turnoWF, Date fechaNotificacion, String idOficinaOrigen) {
        this.destino = destino;
        this.tipo = tipo;
        this.turnoWF = turnoWF;
        this.fechaNotificacion = fechaNotificacion;
        this.idOficinaOrigen = idOficinaOrigen;
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
    
}

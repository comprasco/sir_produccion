/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;

import java.util.Date;
import org.auriga.core.modelo.TransferObject;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class JustificaAdm implements TransferObject {
    private static final long serialVersionUID = 1L;
    private int admId; //pk
    private String admDescripcion;
    private String admIpPcFuncLog;
    private int admIdUsuario;
    private ArchivosJustifica infoArchivo;
    private JustificaTipos infoJustificacion;
    private Date admFecha;
    private String admUsuarioModifica;
    private String admCirculoUsrModificado;

    public JustificaAdm() {
    }

    public int getAdmId() {
        return admId;
    }

    public void setAdmId(int admId) {
        this.admId = admId;
    }

    public String getAdmDescripcion() {
        return admDescripcion;
    }

    public void setAdmDescripcion(String admDescripcion) {
        this.admDescripcion = admDescripcion;
    }

    public String getAdmIpPcFuncLog() {
        return admIpPcFuncLog;
    }

    public void setAdmIpPcFuncLog(String admIpPcFuncLog) {
        this.admIpPcFuncLog = admIpPcFuncLog;
    }

    public int getAdmIdUsuario() {
        return admIdUsuario;
    }

    public void setAdmIdUsuario(int admIdUsuario) {
        this.admIdUsuario = admIdUsuario;
    }

    public ArchivosJustifica getArchivosJustifica() {
        return infoArchivo;
    }

    public void setArchivosJustifica(ArchivosJustifica infoArchivo) {
        this.infoArchivo = infoArchivo;
    }

    public JustificaTipos getJustificaTipos() {
        return infoJustificacion;
    }

    public void setJustificaTipos(JustificaTipos infoJustificacion) {
        this.infoJustificacion = infoJustificacion;
    }
    
    public Date getAdmFecha() {
        return admFecha;
    }

    public void setAdmFecha(Date admFecha) {
        this.admFecha = admFecha;
    }
    
    public String getAdmUsuarioModifica() {
        return admUsuarioModifica;
    }

    public void setAdmUsuarioModifica(String admUsuarioModifica) {
        this.admUsuarioModifica = admUsuarioModifica;
    }
    
    public String getAdmCirculoUsrModificado() {
        return admCirculoUsrModificado;
    }

    public void setAdmCirculoUsrModificado(String admCirculoUsrModificado) {
        this.admCirculoUsrModificado = admCirculoUsrModificado;
    }
    
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.JustificaAdm;
import java.util.Date;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class JustificaAdmEnhanced extends Enhanced {

    private int admId; //pk
    private String admDescripcion;
    private JustificaTiposEnhanced admTipIdTipo;
    private ArchivosJustificaEnhanced admJusIdArchivo;
    private String admIpPcFuncLog;
    private int admIdUsuario;
    private Date admFecha;
    private String admUsuarioModifica;
    private String admCirculoUsrModificado;

    public JustificaAdmEnhanced() {
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

    public ArchivosJustificaEnhanced getArchivosJustifica() {
        return admJusIdArchivo;
    }

    public void setArchivosJustifica(ArchivosJustificaEnhanced admJusIdArchivo) {
        this.admJusIdArchivo = admJusIdArchivo;
    }

    public JustificaTiposEnhanced getJustificaTipos() {
        return admTipIdTipo;
    }

    public void setJustificaTipos(JustificaTiposEnhanced admTipIdTipo) {
        this.admTipIdTipo = admTipIdTipo;
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

    public static JustificaAdmEnhanced enhance(
            JustificaAdm justificaAdm) {
        return (JustificaAdmEnhanced) Enhanced.enhance(justificaAdm);
    }

}

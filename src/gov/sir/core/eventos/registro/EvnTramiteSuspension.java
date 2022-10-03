/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.eventos.registro;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.TramiteSuspension;
import gov.sir.core.negocio.modelo.Turno;
import java.util.Date;
import org.apache.commons.fileupload.FileItem;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 *
 * @author developer5
 */
public class EvnTramiteSuspension extends EvnSIR {

    public static final String AGREGAR_RESPUESTA_PREV = "AGREGAR_RESPUESTA_PREV";
    public static final String AGREGAR_RESPUESTA_TEMP = "AGREGAR_RESPUESTA_TEMP";
    public static final String CANCELAR_TRAMITE_SUSP_TEMP = "CANCELAR_TRAMITE_SUSP_TEMP";
    public static final String CANCELAR_TRAMITE_SUSP_PREV = "CANCELAR_TRAMITE_SUSP_PREV";
    public static final String AVANZAR = "AVANZAR";

    private gov.sir.core.negocio.modelo.TramiteSuspension tramSuspension;
    private gov.sir.core.negocio.modelo.ArchivosJustifica archivosJustifica;
    private gov.sir.core.negocio.modelo.Usuario usuario;
    private FileItem fileItem;
    private Turno turnoAvance;
    private Fase faseAvance;

    private String nombreFase;
    private Date fecha;
    private String descripcion;
    private String turno;

    public EvnTramiteSuspension(Usuario usuario, String tipoEvento) {
        super(usuario, tipoEvento);
    }
  
    public TramiteSuspension getTramSuspension() {
        return tramSuspension;
    }

    public void setTramSuspension(TramiteSuspension tramSuspension) {
        this.tramSuspension = tramSuspension;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public FileItem getFileItem() {
        return fileItem;
    }

    public void setFileItem(FileItem fileItem) {
        this.fileItem = fileItem;
    }

    public String getNombreFase() {
        return nombreFase;
    }

    public void setNombreFase(String nombreFase) {
        this.nombreFase = nombreFase;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public gov.sir.core.negocio.modelo.Usuario getUsuarioNegocio() {
        return usuario;
    }

    public void setUsuarioNegocio(
            gov.sir.core.negocio.modelo.Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public gov.sir.core.negocio.modelo.ArchivosJustifica getArchivosJustifica() {
        return archivosJustifica;
    }

    public void setArchivosJustifica(
            gov.sir.core.negocio.modelo.ArchivosJustifica archivosJustifica) {
        this.archivosJustifica = archivosJustifica;
    }

    public Turno getTurnoAvance() {
        return turnoAvance;
    }

    public void setTurnoAvance(Turno turnoAvance) {
        this.turnoAvance = turnoAvance;
    }

    public Fase getFaseAvance() {
        return faseAvance;
    }

    public void setFaseAvance(Fase faseAvance) {
        this.faseAvance = faseAvance;
    }

}

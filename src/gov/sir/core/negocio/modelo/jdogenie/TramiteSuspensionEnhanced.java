/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TramiteSuspension;
import java.util.Date;

/**
 *
 * @author developer5
 */
public class TramiteSuspensionEnhanced extends Enhanced {

    private int tramsId;
    private String tramsNombreFase;
    private Date tramsFecha;
    private String tramsUsuario;
    private int tramsIdUsuario;
    private String tramsDescripcion;
    private String tramsTurno;
    private ArchivosJustificaEnhanced tramsIdArchivoJustifica;

    public TramiteSuspensionEnhanced() {
    }

    public int getTramsId() {
        return tramsId;
    }

    public void setTramsId(int tramsId) {
        this.tramsId = tramsId;
    }

    public String getTramsNombreFase() {
        return tramsNombreFase;
    }

    public void setTramsNombreFase(String tramsNombreFase) {
        this.tramsNombreFase = tramsNombreFase;
    }

    public Date getTramsFecha() {
        return tramsFecha;
    }

    public void setTramsFecha(Date tramsFecha) {
        this.tramsFecha = tramsFecha;
    }

    public String getTramsUsuario() {
        return tramsUsuario;
    }

    public void setTramsUsuario(String tramsUsuario) {
        this.tramsUsuario = tramsUsuario;
    }

    public int getTramsIdUsuario() {
        return tramsIdUsuario;
    }

    public void setTramsIdUsuario(int tramsIdUsuario) {
        this.tramsIdUsuario = tramsIdUsuario;
    }

    public String getTramsDescripcion() {
        return tramsDescripcion;
    }

    public void setTramsDescripcion(String tramsDescripcion) {
        this.tramsDescripcion = tramsDescripcion;
    }

    public String getTramsTurno() {
        return tramsTurno;
    }

    public void setTramsTurno(String tramsTurno) {
        this.tramsTurno = tramsTurno;
    }

    public ArchivosJustificaEnhanced getTramsIdArchivoJustifica() {
        return tramsIdArchivoJustifica;
    }

    public void setTramsIdArchivoJustifica(ArchivosJustificaEnhanced tramsIdArchivoJustifica) {
        this.tramsIdArchivoJustifica = tramsIdArchivoJustifica;
    }

    public static TramiteSuspensionEnhanced enhance(
            TramiteSuspension tramiteSuspension) {
        return (TramiteSuspensionEnhanced) Enhanced.enhance(tramiteSuspension);
    }

}

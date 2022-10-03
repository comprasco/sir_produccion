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
public class ArchivosJustifica implements TransferObject {
private static final long serialVersionUID = 1L;
    private int jusIdArchivo; //pk
    private Date jusFechaDeSubida;
    private String jusNombreOriginal;
    private String jusTipoArchivo;
    private int jusTamanoArchivo;
    private String jusNombreProceso;
    private String jusRutaFisica;

    public ArchivosJustifica() {
    }

    public int getJusIdArchivo() {
        return jusIdArchivo;
    }

    public void setJusIdArchivo(int jusIdArchivo) {
        this.jusIdArchivo = jusIdArchivo;
    }

    public Date getJusFechaDeSubida() {
        return jusFechaDeSubida;
    }

    public void setJusFechaDeSubida(Date jusFechaDeSubida) {
        this.jusFechaDeSubida = jusFechaDeSubida;
    }

    public String getJusNombreOriginal() {
        return jusNombreOriginal;
    }

    public void setJusNombreOriginal(String jusNombreOriginal) {
        this.jusNombreOriginal = jusNombreOriginal;
    }

    public String getJusTipoArchivo() {
        return jusTipoArchivo;
    }

    public void setJusTipoArchivo(String jusTipoArchivo) {
        this.jusTipoArchivo = jusTipoArchivo;
    }

    public int getJusTamanoArchivo() {
        return jusTamanoArchivo;
    }

    public void setJusTamanoArchivo(int jusTamanoArchivo) {
        this.jusTamanoArchivo = jusTamanoArchivo;
    }

    public String getJusNombreProceso() {
        return jusNombreProceso;
    }

    public void setJusNombreProceso(String jusNombreProceso) {
        this.jusNombreProceso = jusNombreProceso;
    }

    public String getJusRutaFisica() {
        return jusRutaFisica;
    }

    public void setJusRutaFisica(String jusRutaFisica) {
        this.jusRutaFisica = jusRutaFisica;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ArchivosJustifica;
import java.util.Date;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class ArchivosJustificaEnhanced extends Enhanced {

    private int jusIdArchivo; //pk
    private Date jusFechaDeSubida;
    private String jusNombreOriginal;
    private String jusTipoArchivo;
    private int jusTamanoArchivo;
    private String jusNombreProceso;
    private String jusRutaFisica;

    public ArchivosJustificaEnhanced() {
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

    public static ArchivosJustificaEnhanced enhance(
            ArchivosJustifica archivosJustifica) {
        return (ArchivosJustificaEnhanced) Enhanced.enhance(archivosJustifica);
    }

}

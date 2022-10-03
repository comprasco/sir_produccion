/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ArchivosJustificaPk;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class ArchivosJustificaEnhancedPk implements java.io.Serializable {

    public int jusIdArchivo;

    public ArchivosJustificaEnhancedPk() {
    }

    public ArchivosJustificaEnhancedPk(ArchivosJustificaPk id) {
        jusIdArchivo = id.jusIdArchivo;
    }

    public ArchivosJustificaEnhancedPk(String s) {
        int i;
        int p = 0;
        jusIdArchivo = Integer.parseInt(s.substring(p));
    }

    public ArchivosJustificaPk getJusIdArchivo() {
        ArchivosJustificaPk rta = new ArchivosJustificaPk();
        rta.jusIdArchivo = jusIdArchivo;
        return rta;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ArchivosJustificaEnhancedPk)) {
            return false;
        }
        final ArchivosJustificaEnhancedPk id = (ArchivosJustificaEnhancedPk) o;

        if (this.jusIdArchivo != id.jusIdArchivo) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) + (int) jusIdArchivo;
        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(jusIdArchivo);

        return buffer.toString();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;

/**
 *
 * @author Geremias Ortiz Lozano
 */
public class ArchivosJustificaPk implements java.io.Serializable {

    public int jusIdArchivo;

    public ArchivosJustificaPk() {
    }

    public ArchivosJustificaPk(String s) {
        int i;
        int p = 0;
        jusIdArchivo = Integer.parseInt(s.substring(p));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ArchivosJustificaPk)) {
            return false;
        }

        final ArchivosJustificaPk id = (ArchivosJustificaPk) o;

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

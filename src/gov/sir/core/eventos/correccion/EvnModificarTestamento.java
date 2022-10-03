/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.eventos.correccion;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadano;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Documento;
import org.auriga.core.modelo.transferObjects.Usuario;

/**
 *
 * @author Carlos Torres
 */
public class EvnModificarTestamento extends EvnCorreccion{
    
    public static final String GUARDAR_DATOS_TEMPORALES_TESTAMENTO = "GUARDAR_DATOS_TEMPORALES_TESTAMENTO";
    public static final String CARGAR_TESTAMENTO = "CARGAR_TESTAMENTO";
    public static final String ELIMINAR_TESTADOR = "ELIMINAR_TESTADOR";
    public static final String AGREGAR_TESTADOR = "AGREGAR_TESTADOR";
    
    public Testamento testamento;
    public String salvedad;
    public String copias_imp;
    public TestamentoCiudadano testamentoCiudadano;
    public Documento encabezadoDoc;
    
    public EvnModificarTestamento(Usuario usuario,gov.sir.core.negocio.modelo.Usuario usuarioSIR, Turno turno, String tipoAccion) {
        super(usuario, usuarioSIR, new Folio(), turno, tipoAccion);
    }

    public Testamento getTestamento() {
        return testamento;
    }

    public void setTestamento(Testamento testamento) {
        this.testamento = testamento;
    }

    public String getSalvedad() {
        return salvedad;
    }

    public void setSalvedad(String salvedad) {
        this.salvedad = salvedad;
    }

    public String getCopias_imp() {
        return copias_imp;
    }

    public void setCopias_imp(String copias_imp) {
        this.copias_imp = copias_imp;
    }

    public TestamentoCiudadano getTestamentoCiudadano() {
        return testamentoCiudadano;
    }

    public void setTestamentoCiudadano(TestamentoCiudadano testamentoCiudadano) {
        this.testamentoCiudadano = testamentoCiudadano;
    }

    public Documento getEncabezadoDoc() {
        return encabezadoDoc;
    }

    public void setEncabezadoDoc(Documento encabezadoDoc) {
        this.encabezadoDoc = encabezadoDoc;
    }
    
}

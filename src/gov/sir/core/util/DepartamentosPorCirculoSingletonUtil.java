/**
 * @autor HGOMEZ 
 * @mantis 13407 
 * @Requerimiento 064_453_Duplicidad_Nombre_Circulo 
 * @descripcion Clase singleton para obtener la lista de departamentos por
 * circulo.
 */
package gov.sir.core.util;

import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import co.com.iridium.generalSIR.comun.GeneralSIRException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Vector;
import java.util.List;

public class DepartamentosPorCirculoSingletonUtil {
    private static DepartamentosPorCirculoSingletonUtil instanciaUnica = null;
    List listaCirculoDepartamento = new Vector();
    ValidacionesSIR validacion = new ValidacionesSIR();

    private DepartamentosPorCirculoSingletonUtil() {
        try {
            listaCirculoDepartamento = validacion.getDepartamentosPorCirculo();
        } catch (GeneralSIRException ex) {
            Logger.getLogger(DepartamentosPorCirculoSingletonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static synchronized DepartamentosPorCirculoSingletonUtil getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new DepartamentosPorCirculoSingletonUtil();
        }
        return instanciaUnica;
    }
    
    public String getNombreCirculoDepartamento(List circulosPorDepartamento, int idCirculo){
        int inicio = 0;
        int fin = circulosPorDepartamento.size() - 1;
        int pos;
        while (inicio <= fin) {
            pos = (inicio+fin) / 2;

            String informacion = (String)circulosPorDepartamento.get(pos);
            String[] datos = informacion.split(",");
            int idCirculoLista = Integer.parseInt(datos[0]);

            if (idCirculoLista == idCirculo)
                return datos[3];
            else if (idCirculoLista < idCirculo) {
                inicio = pos+1;
            } else {
                fin = pos-1;
            }
        }
        return "";
    }

    public boolean isNumber(String cadena)
    {
        char[] c = cadena.toCharArray();
        for(int i=0; i < cadena.length(); i++)
        {
            if (!Character.isDigit(c[i]))
            {
                return false;
            }
        }
        return true;
    }
    
    public List getDepartamentosPorCirculo()
    {
        return listaCirculoDepartamento;
    }     
}





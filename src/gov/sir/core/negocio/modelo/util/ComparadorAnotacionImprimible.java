/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced;
import java.util.Comparator;

/**
 *
 * @author Edgar
 */
public class ComparadorAnotacionImprimible implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        int resultado = 0;
        if(o1 instanceof Anotacion){
            Anotacion a1 = (Anotacion) o1;
            Anotacion a2 = (Anotacion) o2;

            resultado = Integer.valueOf(a1.getOrden()).compareTo(Integer.valueOf(a2.getOrden()));
        }else{
            AnotacionEnhanced a1 = (AnotacionEnhanced) o1;
            AnotacionEnhanced a2 = (AnotacionEnhanced) o2;

            resultado = Integer.valueOf(a1.getOrden()).compareTo(Integer.valueOf(a2.getOrden()));
        }
        return resultado;
    }
}

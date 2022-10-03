/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.SalvedadFolio;
import java.util.Comparator;

/**
 *
 * @author Edgar
 */
public class ComparadorSalvedad implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        int resultado = 0;
        if(o1 instanceof SalvedadFolio){
            SalvedadFolio s1 = (SalvedadFolio) o1;
            SalvedadFolio s2 = (SalvedadFolio) o2;
            resultado = Integer.parseInt(s1.getIdSalvedad()) - Integer.parseInt(s2.getIdSalvedad());
        }
        return resultado;
    }

}

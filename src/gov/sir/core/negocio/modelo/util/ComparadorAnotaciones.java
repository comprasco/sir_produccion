/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.util;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edgar Lora
 */
public class ComparadorAnotaciones implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        int resultado = 0;
        if(o1 instanceof Anotacion){
            Anotacion a1 = (Anotacion) o1;
            Anotacion a2 = (Anotacion) o2;
            
            if(a1.getOrden() == null){
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                String r = "";
                try {
                    r = validacionesSIR.getAnotacionNtcnOrden(a1.getIdAnotacion(), a1.getIdMatricula());
                } catch (GeneralSIRException ex) {
                    Logger.getLogger(ComparadorAnotaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
                a1.setOrden(r);
            }
            if(a2.getOrden() == null){
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                String r = "";
                try {
                    r = validacionesSIR.getAnotacionNtcnOrden(a2.getIdAnotacion(), a2.getIdMatricula());
                } catch (GeneralSIRException ex) {
                    Logger.getLogger(ComparadorAnotaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
                a2.setOrden(r);
            }
            resultado = Integer.valueOf(a1.getOrden()).compareTo(Integer.valueOf(a2.getOrden()));
        }else{
            AnotacionEnhanced a1 = (AnotacionEnhanced) o1;
            AnotacionEnhanced a2 = (AnotacionEnhanced) o2;

            resultado = Integer.valueOf(a1.getOrden()).compareTo(Integer.valueOf(a2.getOrden()));
        }
        return resultado;
    }
    
}

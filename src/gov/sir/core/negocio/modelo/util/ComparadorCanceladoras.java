/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.util;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Cancelacion;
import java.util.Comparator;

/**
 *
 * @author Edgar
 */
public class ComparadorCanceladoras implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        int r;
        ValidacionesSIR valSir = new ValidacionesSIR();
        if(o1 instanceof Anotacion){
             Anotacion a1 = (Anotacion) o1;
            Anotacion a2 = (Anotacion) o2;
            if(a1.getOrden() == null){
                try {
                    a1.setOrden(valSir.getAnotacionNtcnOrden(a1.getIdAnotacion(), a1.getIdMatricula()));
                } catch (GeneralSIRException ex) {
                    a1.setOrden(null);
                }
            }
            if(a2.getOrden() == null){
                try {
                    a2.setOrden(valSir.getAnotacionNtcnOrden(a2.getIdAnotacion(), a2.getIdMatricula()));
                } catch (GeneralSIRException ex) {
                    a2.setOrden(null);
                }
            }
            r = Integer.parseInt(a1.getOrden()) - Integer.parseInt(a2.getOrden());
        }else if(o1 instanceof Cancelacion){
            Cancelacion c1 = (Cancelacion) o1;
            Cancelacion c2 = (Cancelacion) o2;
            if(c1.getCancelada().getOrden() == null){
                try {
                    c1.getCancelada().setOrden(valSir.getAnotacionNtcnOrden(c1.getCancelada().getIdAnotacion(), c1.getCancelada().getIdMatricula()));
                } catch (GeneralSIRException ex) {
                    c1.getCancelada().setOrden(null);
                }
            }
            if(c2.getCancelada().getOrden() == null){
                try {
                    c2.getCancelada().setOrden(valSir.getAnotacionNtcnOrden(c2.getCancelada().getIdAnotacion(), c2.getCancelada().getIdMatricula()));
                } catch (GeneralSIRException ex) {
                    c2.getCancelada().setOrden(null);
                }
            }
            r = Integer.parseInt(c1.getCancelada().getOrden()) - Integer.parseInt(c2.getCancelada().getOrden());
        }else{
            r = -1;
        }
        return r;
    }
    
}

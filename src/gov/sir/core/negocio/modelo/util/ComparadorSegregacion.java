/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.SegregacionParaComparar;
import gov.sir.core.web.helpers.comun.ElementoLista;
import java.util.Comparator;

/**
 *
 * @author Edgar
 */
public class ComparadorSegregacion implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        int r = 0;
        String matricula1 = "";
        String matricula2 = "";
        if(o1 instanceof String && o2 instanceof String){
            matricula1 = o1.toString();
            matricula2 = o2.toString();
        }else if(o1 instanceof ElementoLista && o2 instanceof ElementoLista){
            matricula1 = ((ElementoLista)o1).getId();
            matricula2 = ((ElementoLista)o2).getId();
        } else if(o1 instanceof Folio && o2 instanceof Folio){
            matricula1 = ((Folio)o1).getIdMatricula();
            matricula2 = ((Folio)o2).getIdMatricula();
        }
        SegregacionParaComparar s1 = new SegregacionParaComparar(matricula1);
        SegregacionParaComparar s2 = new SegregacionParaComparar(matricula2);
        if(s1.getIdOrden() != -1 && s2.getIdOrden() != -1){
            r = s1.getIdOrden() - s2.getIdOrden();
            if(r == 0){
                r = Integer.parseInt(s1.getIdMatricula().split("-")[1]) - Integer.parseInt(s2.getIdMatricula().split("-")[1]);
            }
        }else if(s1.getIdOrden() == -1 && s2.getIdOrden() == -1){
            r = Integer.parseInt(s1.getIdMatricula().split("-")[1]) - Integer.parseInt(s2.getIdMatricula().split("-")[1]);
        } else if(s1.getIdOrden() == -1 && s2.getIdOrden() != -1){
            r = 1;
        } else if(s1.getIdOrden() != -1 && s2.getIdOrden() == -1){
            r = -1;
        }
        return r;
    }
    
}

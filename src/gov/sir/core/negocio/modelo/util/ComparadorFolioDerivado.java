/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.FolioDerivado;
import java.util.Comparator;

/**
 *
 * @author Edgar
 */
public class ComparadorFolioDerivado implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        FolioDerivado fd1 = (FolioDerivado) o1;
        FolioDerivado fd2 = (FolioDerivado) o2;
        int r = Integer.parseInt(fd1.getIdMatricula().split("-")[1]) - Integer.parseInt(fd2.getIdMatricula().split("-")[1]);
        return r;
    }
    
}

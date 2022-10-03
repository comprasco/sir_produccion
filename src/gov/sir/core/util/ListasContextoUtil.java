/**
 * @author Cristian David Garcia - Pear Solutions
 * Metodos alternativos para recargar listas de contexto específicas.
 */
package gov.sir.core.util;

import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Cristian Garcia PS
 */
public final class ListasContextoUtil {
    
    public static final String MODALIDAD = "MODALIDAD";
    
    public static List getModalidades() {
        List modalidad;
        List modalidades = new ArrayList();
        try{
            HermodService hs = HermodService.getInstance();
            modalidad = hs.getValorLookupCodesByTipo(MODALIDAD);

        Iterator itDest = modalidad.iterator();
        while(itDest.hasNext()){
            OPLookupCodes code = (OPLookupCodes) itDest.next();
            ElementoLista el = new ElementoLista();
            el.setId(code.getCodigo());
            el.setValor(code.getValor());
            modalidades.add(el); 
        }

        } catch(HermodException he){
            System.out.println("ERROR: No fue posible obtener el listado");
        } 
		return modalidades;
	}
}

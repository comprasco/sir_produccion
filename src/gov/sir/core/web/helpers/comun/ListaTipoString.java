/*
 * Created on 02-ago-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.comun;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;



/**
 * @author jfrias
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ListaTipoString extends ListaElementoHelper {
    /**
     *
     */
    public ListaTipoString() {
        super();

    }

    /* (non-Javadoc)
     * @see gov.sir.core.web.helpers.certificados.ListaElementoHelper#setListaTipos(java.util.List)
     */
    public void setListaTipos(List lista) {
        List tipos1 = new Vector();
        Iterator it = lista.iterator();

        while (it.hasNext()) {
            String cad = (String) it.next();
            ElementoLista elemento = new ElementoLista(cad, cad);
            tipos1.add(elemento);
        }

        setTipos(tipos1);
    }
}

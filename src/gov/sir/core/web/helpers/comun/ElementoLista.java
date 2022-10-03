/*
 * Created on 02-ago-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.comun;


/**
 * @author jfrias
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ElementoLista implements java.io.Serializable {
    private String id;
    private String valor;

    /**
     *
     */
    public ElementoLista() {
        super();

    }

    public ElementoLista(String id, String valor) {
        this.id = id;
        this.valor = valor;
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @return
     */
    public String getValor() {
        return valor;
    }
    
    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public boolean equals(Object objeto) {
        if(!(objeto instanceof ElementoLista))
            return false;
        if(objeto == null)
            return false;
        ElementoLista elemento = (ElementoLista)objeto;
        
        if(elemento.id == id)
            return true;
        if(id != null && id.equals(elemento.id))
            return true;
        
        return false;
    }
}

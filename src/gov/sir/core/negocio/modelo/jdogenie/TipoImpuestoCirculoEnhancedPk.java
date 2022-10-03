package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TipoImpuestoCirculoPk;

/**
 * @author Fernando Padilla Velez
 * @version 1.0, 28/12/2009
 * @change Clase creada para el caso MANTIS 135_141_Impuesto Meta.
 */
public class TipoImpuestoCirculoEnhancedPk implements java.io.Serializable {

    public Integer idTipoImpuestoCirculo;

    public TipoImpuestoCirculoEnhancedPk() {
    }
    

    public TipoImpuestoCirculoEnhancedPk (TipoImpuestoCirculoPk id)
    {
    	idTipoImpuestoCirculo = id.idTipoImpuestoCirculo;
    }

    public TipoImpuestoCirculoEnhancedPk(String s) {
        idTipoImpuestoCirculo = Integer.parseInt(s);
    }
    
    public TipoImpuestoCirculoPk getTipoImpuestoCirculoID(){
		TipoImpuestoCirculoPk id = new TipoImpuestoCirculoPk();
		id.idTipoImpuestoCirculo = idTipoImpuestoCirculo;
		return id;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTipoImpuestoCirculo);
        return buffer.toString();
    }
}
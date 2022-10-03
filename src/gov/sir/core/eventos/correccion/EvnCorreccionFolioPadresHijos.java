package gov.sir.core.eventos.correccion;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;

import org.auriga.core.modelo.transferObjects.Usuario;

import java.util.List;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EvnCorreccionFolioPadresHijos extends EvnCorreccion {


    /**
     * Identificador para procesar acciones de enlace entre
     * folios padre y folios hijo
     * */
    public static final String FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM_EVENT = "FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM_EVENT";
    public static final String FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM_EVENT = "FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM_EVENT";

    public static final String FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM_EVENT = "FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM_EVENT";
    public static final String FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM_EVENT = "FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM_EVENT";

    public static final String ACTION_ADDITEM = "ADDITEM";
    public static final String ACTION_DELITEM = "DELITEM";

    public static final String RELATION_PADRESHIJOS_HIJO  = "RELATION_PADRESHIJOS_HIJO";
    public static final String RELATION_PADRESHIJOS_PADRE = "RELATION_PADRESHIJOS_PADRE";
	public static final String FOLIOEDIT_CONVERTIR_CANCELADOR_A_ESTANDAR = "FOLIOEDIT_CONVERTIR_CANCELADOR_A_ESTANDAR";

    // -------------------------------------------------------------------------


    public EvnCorreccionFolioPadresHijos(Usuario usuario,
                                         gov.sir.core.negocio.modelo.Usuario
                                         usuarioSIR, Folio folio, Turno turno,
                                         String tipoAccion) {
        super(usuario, usuarioSIR, folio, turno, tipoAccion);
    }

    // -------------------------------------------------------------------------

    public Anotacion getSource() {
        return source;
    }

    public Anotacion getTarget() {
        return target;
    }

    public String getActionName() {
        return actionName;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setSource(Anotacion source) {
        this.source = source;
    }

    public void setTarget(Anotacion target) {
        this.target = target;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    // -------------------------------------------------------------------------

    protected Anotacion source;
    protected Anotacion target;
    protected String relationName;
    protected String actionName;
	private List sources;

	public void setSources(List sources) {
		this.sources = sources;
	}

	public List getSources() {
		return sources;
	}

    // -------------------------------------------------------------------------

}

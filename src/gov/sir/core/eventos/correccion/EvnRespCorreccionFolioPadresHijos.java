package gov.sir.core.eventos.correccion;

import gov.sir.core.negocio.modelo.Folio;

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
public class EvnRespCorreccionFolioPadresHijos extends EvnRespCorreccion {

    public static final String FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM__EVENTRESP_OK
    = "FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM__EVENTRESP_OK";

    public static final String FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM__EVENTRESP_OK
    = "FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM__EVENTRESP_OK";

    public static final String FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM__EVENTRESP_OK
    = "FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM__EVENTRESP_OK";

    public static final String FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM__EVENTRESP_OK
    = "FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM__EVENTRESP_OK";

    public EvnRespCorreccionFolioPadresHijos(Folio folio, String tipoAccion) {
        super(folio, tipoAccion);
        // al parecer el parametro folio no esta llegando al objeto folio
        // en el contructor de la superclase;
        // por compatibilidad con otros metodos se mantiene,
        // y aparte de ello se fija el folio:

        super.setFolio( folio );
    }
    // setFoliosPadre
    // setFoliosHijo

    // solo referencian a la superclase por el momento ()
    // se hace por claridad

    public void setFoliosPadre( java.util.List list ){
        super.setFoliosPadre( list );
    }

    public void setFoliosHijo( java.util.List list ){
        super.setFoliosHijo( list );
    }

    public void setNumeroAnotaciones( long numeroAnotaciones ) {
        super.setNumeroAnotaciones( numeroAnotaciones );
    }

}

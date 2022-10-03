package gov.sir.core.eventos.registro;

import gov.sir.core.negocio.modelo.Turno;

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
public class EvnRespCalificacionReproduccionSellos extends EvnRespCalificacion {

    public static final String CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_OK
    = "CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_OK";

    public static final String CALIFICACION_REIMPRESIONSELLOS_RESET__EVENTRESP_OK
    = "CALIFICACION_REIMPRESIONSELLOS_RESET__EVENTRESP_OK";

    public static final String CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_ERROR
    = "CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_ERROR";

    public static final String CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENTRESP_OK
    = "CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENTRESP_OK";


    java.util.List searchResults;

    gov.sir.core.negocio.modelo.Turno itemToPrint;

    public EvnRespCalificacionReproduccionSellos( String tipoAccion ) {
        super( null, tipoAccion );
    }

    public List getSearchResults() {
        return searchResults;
    }

    public Turno getItemToPrint() {
        return itemToPrint;
    }

    public void setSearchResults(List searchResults) {
        this.searchResults = searchResults;
    }

    public void setItemToPrint(Turno itemToPrint) {
        this.itemToPrint = itemToPrint;
    }
}

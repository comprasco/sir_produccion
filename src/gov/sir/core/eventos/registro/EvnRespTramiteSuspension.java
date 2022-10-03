/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.eventos.registro;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import java.util.List;

/**
 *
 * @author developer5
 */
public class EvnRespTramiteSuspension extends EvnSIRRespuesta {

    public static final String CONSULTA_RESPUESTA_OK = "CONSULTA_RESPUESTA_OK";
    
    private List listaRespuesta = null;

    /**
     * @param payload
     */
    public EvnRespTramiteSuspension(Object payload) {
        super(payload);
    }

    /**
     * @param payload
     * @param tipoEvento
     */
    public EvnRespTramiteSuspension(Object payload, String tipoEvento) {
        super(payload, tipoEvento);
    }

    public List getListaRespuesta() {
        return listaRespuesta;
    }

    public void setListaRespuesta(List listaRespuesta) {
        this.listaRespuesta = listaRespuesta;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;

import java.util.Date;
import org.auriga.core.modelo.TransferObject;

/**
 *
 * @author desarrollo3
 */
public class DevolucionConservacionDoc implements TransferObject{
        private int idDevolucionConservaDoc;
    private String turnoDevolucionConservadoc;
    private Date fechaDevolucionConservaDoc;
    private double tarifaBaseDevolucionConservaDoc;
    private double valorDevolucionLiquidaConserva;
    private String idSolicituDevolucionConservacion;
    private int mayorvalor;
    private String turnoAntLiquidacionConserva;
    private static final long serialVersionUID = 1L;
}

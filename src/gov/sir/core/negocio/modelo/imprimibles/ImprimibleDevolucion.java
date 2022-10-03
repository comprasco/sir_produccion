package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocPagoGeneral;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Solicitante;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
import java.awt.print.PageFormat;
import java.util.List;
import java.util.Iterator;

/**
 * @author jvelez
 * Clase que representa el Imprimible de una Resolución.
 */
public class ImprimibleDevolucion extends ImprimibleBase {
    private Oficio oficio;
    private Turno turno;

    private Pago pago;
    private String fechaImpresion = null;
    private String fechaOficio = null;

    private static final long serialVersionUID = 1L;
    /**
     * Constructor de la clase
     */
    public ImprimibleDevolucion(Oficio oficio, Turno turno, Pago pago, String fechaImpresion,String tipoImprimible) {
        super(tipoImprimible);
        this.oficio = oficio;
        this.turno = turno;
        this.pago = pago;
        this.fechaImpresion  = fechaImpresion;
    }

    /**
     *Genera el vector de hojas imprimibles con toda la información que se va a imprimir.
     */
    public void generate(PageFormat pageFormat) {
        super.generate(pageFormat);

        System.out.println("Imprimir encabezado de la constancia.....");

//Encabezado del recibo igual en todos los recibos.
        this.imprimirEncabezado();

        System.out.println("Imprimir cuerpo de la constancia.....");

//imprime el cuerpo del recibo
        this.imprimirCuerpoConstancia();

        System.out.println(".....Constancia Generada");

    }

    /**
     * imprimirCuerpoConstancia
     */
    private void imprimirCuerpoConstancia() {

        String linea = "";
        String texto = "";

/////////////////////////
        SolicitudDevolucion sol = (SolicitudDevolucion) turno.getSolicitud();
        String descripcion = ((sol.getDescripcion() != null)
                ? sol.getDescripcion() : "");
        String turnoAnterior = (((sol.getTurnoAnterior() != null) &&
                                 (sol.getTurnoAnterior().getIdWorkflow() != null))
                                ? sol.getTurnoAnterior().getIdWorkflow() :
                                "");

        this.imprimirLinea(ImprimibleConstantes.TITULO2, "DATOS SOLICITUD:");
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "TURNO ASOCIADO: " + turnoAnterior);
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "DESCRIPCIÓN: " + descripcion);

        List solicitantes = sol.getSolicitantes();
        for(int i=0;i<solicitantes.size();i++){
        	Solicitante solicitante = (Solicitante)solicitantes.get(i);
            Ciudadano ciud = solicitante.getCiudadano();
            String completo = this.getNombreCompletoConId(ciud);
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                    "NOMBRE DEL SOLICITANTE: " + completo);
            this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        }

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

////////////////////////

        linea =
                "Hago constar que recibo el dinero solicitado por concepto de devolución, " +
                "de acuerdo con la resolución No. "
                + oficio.getNumero() + " del " + this.fechaOficio +
                ", por medio de la forma de pago detallada a continuación, ";

        this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        imprimirFormaPago();

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

/////////////////////////////////////

        linea = "Firma,  ";
        this.imprimirLinea(ImprimibleConstantes.PLANO, linea);

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        linea = "_____________________________________";
        this.imprimirLinea(ImprimibleConstantes.PLANO, linea);

        linea = "NOMBRE:";
        this.imprimirLinea(ImprimibleConstantes.PLANO, linea);

        linea = "C.C.";
        this.imprimirLinea(ImprimibleConstantes.PLANO, linea);

    }

    /**
     * Imprime el encabezado del oficio de pertenencia.
     */
    protected void imprimirEncabezado() {
        String titulo = "RECIBO DE PAGO DEVOLUCIONES";
        this.imprimirLinea(ImprimibleConstantes.TITULO1,
                ImprimibleConstantes.MARGEN_IZQ * 4, titulo);

        //String tituloSecuencial ="No. Constancia de Pago";        
		this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,ImprimibleConstantes.MARGEN_IZQ * 4,pago.getNumRecibo() );           

        String fechaImp = this.fechaImpresion;
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                ImprimibleConstantes.MARGEN_IZQ * 4, fechaImp);

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                ImprimibleConstantes.MARGEN_IZQ * 4,
                "No. RADICACIÓN: ", false);
        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                ImprimibleConstantes.MARGEN_IZQ * 6,
                this.turno.getIdWorkflow());

        this.imprimirLinea(ImprimibleConstantes.PLANO, " ");

    }

    protected void imprimirCanalRecaudo() {
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                "CANAL DE RECAUDO: " + "PRUEBA LUNES 22 ABRIL");
    }

    protected void imprimirFormaPago() {
        //Imprimir las aplicaciones de pago.
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "FORMA DE PAGO:");

        List aplicaciones = pago.getAplicacionPagos();
//        List aplicaciones = pago.getLiquidacion().getPago().getAplicacionPagos();
        DocumentoPago docPago;
        String line = "";

        if (aplicaciones != null) {
            Iterator it = aplicaciones.iterator();

            while (it.hasNext()) {
                AplicacionPago ap = (AplicacionPago) it.next();
                docPago = ap.getDocumentoPago();
                line = "     " + docPago.getTipoPago();
                if (docPago instanceof DocPagoGeneral) {
                    DocPagoGeneral doc = (DocPagoGeneral) docPago;
                    if (!doc.getNombreCanal().equalsIgnoreCase("") && doc.getNombreCanal() != null) {
                        line += "     " + doc.getNombreCanal() + "-" + ((DocPagoGeneral) docPago).getNombreFormaPago();
                    } else if (!doc.getNombreFormaPago().equalsIgnoreCase(docPago.getTipoPago())) {
                        line += "     " + ((DocPagoGeneral) docPago).getNombreFormaPago();
                    }

                    if (doc.getBanco() != null) {
                        line += ("     BANCO: " + doc.getBanco().getNombre());
                    }

                    //------------------ nueva linea para mostrar el numero PIN --------------- 
                    if (doc.getNoAprobacion() != null && !doc.getNoAprobacion().equals("")) {
                        line += ("     Nro DOC: " + doc.getNoAprobacion());
                    }

                    if (doc.getNoConsignacion() != null && !doc.getNoConsignacion().equals("")) {
                        line += ("     Nro DOC: " + doc.getNoConsignacion());
                    }
                }
                
                else if (docPago instanceof DocPagoCheque) {
                    DocPagoCheque doc = (DocPagoCheque) docPago;
                    line += ("     BANCO: " + doc.getBanco().getNombre());
                    line += ("     No Cheque: " + doc.getNoCheque());
                }

                else if (docPago instanceof DocPagoConsignacion) {
                    DocPagoConsignacion doc = (DocPagoConsignacion) docPago;
                    line += ("     BANCO: " + doc.getBanco().getNombre());
                    line += ("     No Consignacion: " + doc.getNoConsignacion());
                } else{
                    if (docPago.getTipoPago() != null) {
                        line += ("     TIPO PAGO: " + docPago.getTipoPago());
                    }
                    if (docPago.getNombreCanal() != null && !docPago.getNombreCanal().equals("")) {
                            line += ("     NOMBRE CANAL: "  + docPago.getNombreCanal());
                    }
                    if (docPago.getFechaCreacion() != null && !docPago.getFechaCreacion().equals("")) {
                            line += ("     FECHA: "  + docPago.getFechaCreacion());
                    }
                    if (docPago.getSaldoDocumento() != 0) {
                            line += ("     SALDO: "  + docPago.getSaldoDocumento());
                    }
                    if (docPago.getValorDocumento() != 0) {
                            line += ("     VALOR DOCUMENTO: "  + docPago.getValorDocumento());
                    }
                }
                if(ap.getValorAplicado() != 0){
                            line += ("     VALOR: $ "
                        + StringFormat.getNumeroFormateado(ap.getValorAplicado()));
                }


                this.imprimirLinea(ImprimibleConstantes.PLANO, line);
            }
        }

    }

    public String getFechaOficio() {
        return fechaOficio;
    }

    public void setFechaOficio(String fechaOficio) {
        this.fechaOficio = fechaOficio;
    }


}

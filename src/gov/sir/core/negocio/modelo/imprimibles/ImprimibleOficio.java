package gov.sir.core.negocio.modelo.imprimibles;

import java.awt.print.PageFormat;

import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.Ciudadano;

/**
 * @author jvelez
 * Clase que representa el Imprimible de un Oficio.
 */
public class ImprimibleOficio extends ImprimibleBase {

    private Oficio oficio;
    private Turno turno;
	private String fechaImpresion;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la clase
     */
    public ImprimibleOficio(Oficio oficio, Turno turno, String fechaImpresion,String tipoImprimible) {
        super(tipoImprimible);
        this.oficio = oficio;
        this.turno = turno;
        this.fechaImpresion = fechaImpresion;
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
        this.imprimirCuerpoOficio();

        System.out.println(".....Constancia Generada");

    }

    /**
     * imprimirCuerpoConstancia
     */
    private void imprimirCuerpoOficio() {

        String linea = "";
        String texto = "";

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

        Ciudadano ciud = sol.getCiudadano();
        String completo = this.getNombreCompletoConId(ciud);
        this.imprimirLinea(ImprimibleConstantes.PLANO,
            "NOMBRE DEL SOLICITANTE: " + completo);
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        linea = "RESOLUCIÓN:";
        this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);

        texto = "" + oficio.getTextoOficio();
        this.imprimirLinea(ImprimibleConstantes.PLANO, texto);
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        this.imprimirFirma();

    }

    /**
     * Imprime el encabezado del oficio de pertenencia.
     */
    protected void imprimirEncabezado() {


            /*        oficio.setIdOficio("idof");
                    linea = "Oficio Número: " +
                            oficio.getIdOficio();
                    this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
             */


        String numOficio = "";

        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, 470, numOficio);

        //imprimir el tipo de solicitud de recibo.
        String titulo = "Oficio";
        this.imprimirLinea(ImprimibleConstantes.TITULO1,
                           ImprimibleConstantes.MARGEN_IZQ * 4, titulo);

        //imprime la fecha y hora de impresion del recibo.
        String fechaImp = this.fechaImpresion;
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                           ImprimibleConstantes.MARGEN_IZQ * 4, fechaImp);

        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                           ImprimibleConstantes.MARGEN_IZQ * 4,
                           "No. RADICACIÓN: ", false);
        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,
                           ImprimibleConstantes.MARGEN_IZQ * 6,
                           this.turno.getIdWorkflow());

        this.imprimirLinea(ImprimibleConstantes.PLANO, " ");

    }
}

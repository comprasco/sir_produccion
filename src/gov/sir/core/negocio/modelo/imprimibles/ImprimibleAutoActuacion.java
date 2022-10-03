package gov.sir.core.negocio.modelo.imprimibles;

import java.awt.print.PageFormat;

import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.Turno;

/**
 * @author jvelez
 * Clase que representa el Imprimible de una Resolución.
 */
public class ImprimibleAutoActuacion extends ImprimibleBase {

    private String personaFirma = "";
	private Oficio oficio;
    private Turno turno;
    private boolean esBorrador;
   private static final long serialVersionUID = 1L;
	public static final String DIRECTOR_REGISTRO = "Director de Registro";
	public static final String REGISTRADOR = "Registrador";

   


    /**
     * Constructor de la clase
     */
    public ImprimibleAutoActuacion(Oficio oficio, Turno turno,String tipoImprimible) {
        super(tipoImprimible);
        this.oficio = oficio;
        this.turno = turno;
    }


    public ImprimibleAutoActuacion(Oficio oficio, Turno turno, boolean esBorrador,String tipoImprimible) {
        super(tipoImprimible);
        this.oficio = oficio;
        this.turno = turno;
        this.esBorrador = esBorrador;
    }


    /**
     *Genera el vector de hojas imprimibles con toda la información que se va a imprimir.
     */
    public void generate(PageFormat pageFormat) {
        super.generate(pageFormat);

 
//Encabezado del recibo igual en todos los recibos.
        this.imprimirEncabezado();

 
//imprime el cuerpo del recibo
        this.imprimirCuerpoResolucion();


    }

    /**
     * imprimirCuerpoConstancia
     */
    private void imprimirCuerpoResolucion() {

        String linea = "";
        String texto = "";

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        texto = "" + oficio.getTextoOficio();
        this.imprimirLinea(ImprimibleConstantes.PLANO, texto);
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        linea = "Firma " + personaFirma + ",  ";
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                           ImprimibleConstantes.MARGEN_IZQ * 6, linea);

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        linea = "No. RADICACIÓN: " + this.turno.getIdWorkflow();
        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                           ImprimibleConstantes.MARGEN_IZQ * 7,
                           linea);

        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        if (esBorrador){
            linea = "NOTA: ESTE ES UN BORRADOR";
            this.imprimirLinea(ImprimibleConstantes.TITULO1,
                               ImprimibleConstantes.MARGEN_IZQ * 2,
                               linea);
        }
    }

    /**
     * Imprime el encabezado del oficio de pertenencia.
     */
    protected void imprimirEncabezado() {

        String titulo = "AUTO No. "; // + oficio.getNum();
        this.imprimirLinea(ImprimibleConstantes.TITULO1, 150, titulo);

        this.imprimirLinea(ImprimibleConstantes.PLANO, " ");

    }
	/**
	 * @return
	 */
	public String getPersonaFirma() {
		return personaFirma;
	}

	/**
	 * @param string
	 */
	public void setPersonaFirma(String string) {
		personaFirma = string;
	}

}

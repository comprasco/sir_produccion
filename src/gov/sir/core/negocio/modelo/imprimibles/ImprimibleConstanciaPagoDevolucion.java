package gov.sir.core.negocio.modelo.imprimibles;

import java.awt.print.PageFormat;
import java.util.Vector;

import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;

/**
 * @author jvelez
 * Clase que representa el Imprimible de una Resolución.
 */
public class ImprimibleConstanciaPagoDevolucion extends ImprimibleBase {
    /**Texto del Oficio de Pertenencia.**/
    private String texto;
    private static final long serialVersionUID = 1L;
    /**
     * Constructor de la clase
     */
    public ImprimibleConstanciaPagoDevolucion(String texto,String tipoImprimible) {
        super(tipoImprimible);
        this.texto = texto;

    }

    /**
     *Genera el vector de hojas imprimibles con toda la información que se va a imprimir.
     */
    public void generate(PageFormat pageFormat) {
        super.generate(pageFormat);
        this.imprimirEncabezado();

        Vector lineas = this.getVectorLineas(this.texto,
                                             ImprimibleConstantes.PLANO);
        for (int i = 0; i < lineas.size(); i++) {
            String linea = (String) lineas.get(i);
            this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
        }
    }

    /**
     * Imprime el encabezado del oficio de pertenencia.
     */
    protected void imprimirEncabezado() {
        String linea;
        linea = StringFormat.getCentrada("CONSTANCIA PAGO DE DEVOLUCION",
                                         ImprimibleConstantes.
                                         MAX_NUM_CHAR_TITULO1,
                                         ImprimibleConstantes.LONG_LOGO);
        this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);

        this.imprimirLinea(ImprimibleConstantes.PLANO,
                           ImprimibleConstantes.MARGEN_IZQ * 4, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO,
                           ImprimibleConstantes.MARGEN_IZQ * 4, "");
    }

}

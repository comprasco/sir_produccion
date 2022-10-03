package gov.sir.core.negocio.modelo;

/**
 * Clase que guarda las variables comunes entre folios y anotaciones
 * @author OSBERT LINERO - IRIDIUM
 */
public class AnotacionesFolio {

    /**
     * Atributo que almacena el número de anotaciones que serán visualizados.
     */
    private static int numAnotacionesFolioV = 0;

    /**
     * Atributo que almacena el número de anotaciones total del folio.
     */
    private static int numAnotacionesTotalV = 0;
private static final long serialVersionUID = 1L;
    /**
     * Método que recupera el número de anotaciones que serán visualizados
     * @return entero con el valor del atributo numAnotacionesFolio
     */
    public static int getNumAnotacionesFolio() {
        return numAnotacionesFolioV;
    }

    /**
     * Método que asigna valor al atributo numAnotacionesFolioV
     * @param numero - Entero con el nuevo número de anotaciones que serán visualizados.
     */
    public static void setNumAnotacionesFolio(int numero) {
        numAnotacionesFolioV = numero;
    }

    /**
     * Método que recupera el número total de anotaciones del folio.
     * @return entero con el valor del atributo numAnotacionesTotalV.
     */
    public static int getNumAnotacionesTotalV() {
        return numAnotacionesTotalV;
    }

    /**
     * Método que asigna valor al atributo numAnotacionesTotalV.
     * @param numeroTotal Entero con el nuevo número de anotaciones total.
     */
    public static void setNumAnotacionesTotalV(int numeroTotal) {
        numAnotacionesTotalV = numeroTotal;
    }
}

package gov.sir.core.negocio.modelo;

/**
 * Clase que guarda las variables comunes entre folios y anotaciones
 * @author OSBERT LINERO - IRIDIUM
 */
public class AnotacionesFolio {

    /**
     * Atributo que almacena el n�mero de anotaciones que ser�n visualizados.
     */
    private static int numAnotacionesFolioV = 0;

    /**
     * Atributo que almacena el n�mero de anotaciones total del folio.
     */
    private static int numAnotacionesTotalV = 0;
private static final long serialVersionUID = 1L;
    /**
     * M�todo que recupera el n�mero de anotaciones que ser�n visualizados
     * @return entero con el valor del atributo numAnotacionesFolio
     */
    public static int getNumAnotacionesFolio() {
        return numAnotacionesFolioV;
    }

    /**
     * M�todo que asigna valor al atributo numAnotacionesFolioV
     * @param numero - Entero con el nuevo n�mero de anotaciones que ser�n visualizados.
     */
    public static void setNumAnotacionesFolio(int numero) {
        numAnotacionesFolioV = numero;
    }

    /**
     * M�todo que recupera el n�mero total de anotaciones del folio.
     * @return entero con el valor del atributo numAnotacionesTotalV.
     */
    public static int getNumAnotacionesTotalV() {
        return numAnotacionesTotalV;
    }

    /**
     * M�todo que asigna valor al atributo numAnotacionesTotalV.
     * @param numeroTotal Entero con el nuevo n�mero de anotaciones total.
     */
    public static void setNumAnotacionesTotalV(int numeroTotal) {
        numAnotacionesTotalV = numeroTotal;
    }
}

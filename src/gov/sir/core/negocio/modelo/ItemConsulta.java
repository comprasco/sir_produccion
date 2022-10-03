package gov.sir.core.negocio.modelo;

/**
 * Esta clase representa cada una de las consultas que un ciudadano 
 * puede solicitar en un WORKFLOW DE CONSULTA
 */
public class ItemConsulta {
	/**
	 * @link aggregation
	 * @supplierCardinality 1
	 * @clientCardinality 1
	 */
    private static final long serialVersionUID = 1L;
	private gov.sir.core.negocio.modelo.Busqueda busqueda;
}

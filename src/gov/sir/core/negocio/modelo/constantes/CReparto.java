/*
 * Created on 11-nov-2004
 */
package gov.sir.core.negocio.modelo.constantes;

/**
 * @author dlopez
 * Clase que define las constantes para opciones de reparto notarial.
 */
public final class CReparto {

	public static final String ORDEN_MONTO = "valorMax";
	public static final String ORDEN_UNIDADES = "unidadMax";
	public static final String TEXTO_ACTA_REP_NOTARIAL = "ACTA_REP_NOTARIAL";

	/**
	* Constante que indica que una minuta no ha sido repartida
	*/
	public static long  MINUTA_NO_REPARTIDA = 1;


	/**
	* Constante que indica que una minuta  ha sido repartida
	*/
	public static long  MINUTA_REPARTIDA = 2;


	/**
	* Constante que indica que una minuta ha sido anulada
	*/
	public static long  MINUTA_ANULADA = 3;

	/**
	* Constante que indica que se desea consultar todas las naturalezas juridicas de una entidad.
	*/
	public static String  CONSULTAR_TODAS_NATURALEZAS_ENTIDAD = "CONSULTAR_TODAS_NATURALEZAS_ENTIDAD";
}

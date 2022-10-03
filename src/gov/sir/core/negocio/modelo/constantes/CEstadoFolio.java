/*
 * Created on 04-oct-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.constantes;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CEstadoFolio {
	
	public static final String NOMBRE_ESTADO_FOLIO = "NOMBRE_ESTADO_FOLIO";
	public static final String ID_ESTADO_FOLIO = "ID_ESTADO_FOLIO";
	public static final String COMENTARIO_ESTADO_FOLIO = "COMENTARIO_ESTADO_FOLIO";

	public static final String ACTIVO = "S";
	public static final String EN_CUSTODIA = "Q";
	public static final String ANULADO = "A";
	public static final String CERRADO = "C";
	public static final String OBSOLETO = "O";
        public static final String TRASLADADO = "T";
        /**
        * @Autor: Fernando Padilla Velez, 30/06/2015
        * @change:1209.AJUSTE.IMPRIMIBLE.CERTIFICADO.BP.SIR,
        *         Se agrega el estado bloqueado.
        * */
        public static final String BLOQUEADO = "B";
}

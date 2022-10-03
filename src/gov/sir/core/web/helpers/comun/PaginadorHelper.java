package gov.sir.core.web.helpers.comun;

import org.auriga.core.web.Helper;
import org.auriga.core.web.HelperException;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

/**
 * Esta clase se encarga de generar el código HTML que describe la barra de paginación
 * (Botones Ir al comienzo, Ir al fin, siguiente, anterior y los enlaces a los números de página)
 * Se utiliza en conjunto con un objeto de tipo Paginador que se toma de la sesión del usuario gracias
 * al id del paginador.
 *  
 * @author jmendez
 *  @see gov.sir.core.web.helpers.consulta.Paginador
 */
public class PaginadorHelper extends Helper {

	private final static int ENLACES_MAXIMOS_EN_PAGINADOR = 10;
	private String id;
	protected String vista;

	/** Constante que identifica el campo del jsp donde se indica el número de la página a visualizar  **/
	public static final String PAGINA = "PAGINA";

	private int paginaActual;
	private int paginaAnterior;
	private int desde;
	private int hasta;
	private int ultimaPagina;
	private int paginaSiguiente;
	private int numAnotacionesCache;
	private int numAnotacionesPagina;
	private int numAnotacionesTotal;
	
	/**
	 * Método constructor por defecto de la clase FasesHelper
	 */
	public PaginadorHelper() {
	}

	/**
	 * @param id identificador del paginador a ser desplegado.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param vista nombre de la vista a la que se pasará el número de la página a ser
	 * desplegada
	 */
	public void setVista(String vista) {
		this.vista = vista;
	}

	/**
	 * 
	 */
	public void setProperties(HttpServletRequest request) throws HelperException {
		HttpSession session = request.getSession();
		Paginador paginador = null;

		try {
			paginador = (Paginador) session.getAttribute(id);
		} catch (java.lang.ClassCastException e) {
			throw new HelperException("No es un paginador válido");
		}

		int numero_paginas = paginador.getNumeroPaginas();
		paginaActual = paginador.getUltimaPaginaGenerada();
		paginaSiguiente = paginaActual + 1;
		paginaAnterior = paginaActual - 1;
		ultimaPagina = numero_paginas - 1;
		numAnotacionesPagina = Paginador.TAMANO_PAGINA_DEFAULT;
		numAnotacionesCache = PaginadorAvanzado.TAMANO_PAGINA_CACHE_DEFAULT;
		numAnotacionesTotal = paginador.getNumAnotaciones();

		desde = paginaActual - Math.round(ENLACES_MAXIMOS_EN_PAGINADOR / 2);
		desde = (desde < 0) ? 0 : desde;

		hasta = (desde + ENLACES_MAXIMOS_EN_PAGINADOR);
		hasta = (hasta > numero_paginas) ? numero_paginas : hasta;

		int delta = hasta - desde; // reajuste para que siempre salgan el mismo número de págs.

		if (delta < ENLACES_MAXIMOS_EN_PAGINADOR) {
			desde = hasta - ENLACES_MAXIMOS_EN_PAGINADOR;
			desde = (desde < 0) ? 0 : desde;
		}

	}

	/**
	 * Este método pinta en la página las tablas de los datos de resultado
	 * de una consulta
	 * @param request Trae toda la informacion que ha sido guardada del usuario
	 * @param out Se utilizar para poder escribir el codigo HTML de manera dinamica
	 */
	public void drawGUI(HttpServletRequest request, JspWriter out) throws IOException, HelperException {
		setProperties(request);
		out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
		out.write(
			"<!-- fwtable fwsrc=\"SIR_central_pantallasinternas_subsecciones.png\" fwbase=\"sub.gif\" fwstyle=\"Dreamweaver\" fwdocid = \"742308039\" fwnested=\"1\" -->\r\n");
		out.write("<tr>\r\n");
		out.write("<td width=\"12\"><img name=\"sub_r1_c1\" src=\"");
		out.print(request.getContextPath());
		out.write(
			"/jsp/images/sub_r1_c1.gif\" width=\"12\" height=\"22\" border=\"0\" alt=\"\"></td>\r\n");
		out.write("<td class=\"bgnsub\">Paginador</td>\r\n");
		out.write("<td width=\"16\" class=\"bgnsub\"><img src=\"");
		out.print(request.getContextPath());
		out.write("/jsp/images/ico_paginador.gif\" width=\"16\" height=\"21\"></td>\r\n");
		out.write("<td width=\"15\"><img name=\"sub_r1_c4\" src=\"");
		out.print(request.getContextPath());
		out.write(
			"/jsp/images/sub_r1_c4.gif\" width=\"15\" height=\"22\" border=\"0\" alt=\"\"></td>\r\n");
		out.write("</tr>\r\n");
		out.write("</table>\r\n");
		out.write("<br>\r\n");
		out.write("<table width=\"100%\" class=\"camposform\">\r\n");
		out.write("<tr>\r\n");
		out.write("<td width=\"20\"><img src=\"");
		out.print(request.getContextPath());
		out.write("/jsp/images/ind_paginas.gif\" width=\"20\" height=\"15\"></td>\r\n");
		out.write("<td align=\"center\"><table >\r\n");
		out.write("<tr>\r\n");

		if (paginaActual > 0) {
			out.write("<td width=\"20\" align=\"center\">\r\n");
			out.write("<a href=\"" + vista + "?" + PaginadorHelper.PAGINA + "=" + 0 + "\"><img src=\"");
			out.print(request.getContextPath());
			out.write(
				"/jsp/images/btn_inicio.gif\" width=\"25\" height=\"21\" border=\"0\"></a></td>\r\n");
		}

		if (paginaAnterior > -1) {
			out.write("<td width=\"20\" align=\"center\">\r\n");
			out.write(
				"<a href=\""
					+ vista
					+ "?"
					+ PaginadorHelper.PAGINA
					+ "="
					+ paginaAnterior
					+ "\"><img src=\"");
			out.print(request.getContextPath());
			out.write(
				"/jsp/images/btn_anterior.gif\" width=\"20\" height=\"21\" border=\"0\"></a></td>\r\n");
		}

		for (int i = desde; i < hasta; i++) {
			out.write(
				"<td align=\"center\" ><a href=\""
					+ vista
					+ "?"
					+ PaginadorHelper.PAGINA
					+ "="
					+ i
					+ "\" class=\""
					+ ((paginaActual == i) ? "paginadoractivo" : "paginadorinactivo")
					+ "\">"
					+ (i + 1)
					+ "</a></td>\r\n");
		}

		if (paginaActual < ultimaPagina) {
			out.write(
				"<td><a href=\""
					+ vista
					+ "?"
					+ PaginadorHelper.PAGINA
					+ "="
					+ paginaSiguiente
					+ "\"><img src=\"");
			out.print(request.getContextPath());
			out.write(
				"/jsp/images/btn_siguiente.gif\" width=\"20\" height=\"21\" border=\"0\"></a></td>\r\n");
			out.write(
				"<td><a href=\""
					+ vista
					+ "?"
					+ PaginadorHelper.PAGINA
					+ "="
					+ ultimaPagina
					+ "\"><img src=\"");
			out.print(request.getContextPath());
			out.write("/jsp/images/btn_fin.gif\" width=\"25\" height=\"21\" border=\"0\"></a></td>\r\n");
		}

		out.write("</tr>\r\n");
		out.write("</table></td>\r\n");
		out.write("</tr>\r\n");
		out.write("</table>\r\n");
		out.write("<hr class=\"linehorizontal\">\r\n");
	}
	/**
	 * @return
	 */
	protected int getPaginaActual() {
		return paginaActual;
	}

	/**
	 * @param i
	 */
	protected void setPaginaActual(int i) {
		paginaActual = i;
	}

	/**
	 * @return
	 */
	protected int getPaginaAnterior() {
		return paginaAnterior;
	}

	/**
	 * @param i
	 */
	protected void setPaginaAnterior(int i) {
		paginaAnterior = i;
	}

	/**
	 * @return
	 */
	protected int getDesde() {
		return desde;
	}

	/**
	 * @param i
	 */
	protected void setDesde(int i) {
		desde = i;
	}

	/**
	 * @return
	 */
	protected int getHasta() {
		return hasta;
	}

	/**
	 * @param i
	 */
	protected void setHasta(int i) {
		hasta = i;
	}

	/**
	 * @return
	 */
	protected int getUltimaPagina() {
		return ultimaPagina;
	}

	/**
	 * @param i
	 */
	protected void setUltimaPagina(int i) {
		ultimaPagina = i;
	}

	/**
	 * @return
	 */
	protected int getPaginaSiguiente() {
		return paginaSiguiente;
	}

	/**
	 * @param i
	 */
	protected void setPaginaSiguiente(int i) {
		paginaSiguiente = i;
	}

	/**
	 * @return Returns the numAnotacionesPagina.
	 */
	public int getNumAnotacionesPagina() {
		return numAnotacionesPagina;
	}
	/**
	 * @param numAnotacionesPagina The numAnotacionesPagina to set.
	 */
	public void setNumAnotacionesPagina(int numAnotacionesPagina) {
		this.numAnotacionesPagina = numAnotacionesPagina;
	}
	/**
	 * @return Returns the numAnotacionesCache.
	 */
	public int getNumAnotacionesCache() {
		return numAnotacionesCache;
	}
	/**
	 * @param numAnotacionesCache The numAnotacionesCache to set.
	 */
	public void setNumAnotacionesCache(int numAnotacionesCache) {
		this.numAnotacionesCache = numAnotacionesCache;
	}
	/**
	 * @return Returns the numAnotacionesTotal.
	 */
	public int getNumAnotacionesTotal() {
		return numAnotacionesTotal;
	}
	/**
	 * @param numAnotacionesTotal The numAnotacionesTotal to set.
	 */
	public void setNumAnotacionesTotal(int numAnotacionesTotal) {
		this.numAnotacionesTotal = numAnotacionesTotal;
	}
}
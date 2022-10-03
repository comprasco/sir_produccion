package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFase;

import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import java.awt.print.PageFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author ppabon
 * Clase que representa el imprimible de una nota informativa.
 */
public class ImprimibleNotaInformativa extends ImprimibleBase {

	private Vector notas;

	private String nombreCirculo;
	private String turno;
	private String matricula;
	private Hashtable tipoFases;
	private Usuario usuarioSIR;
	private String fechaImpresion;
        private static final long serialVersionUID = 1L;
	/**
	 * Constructor de la clase.
	 * @param nota
	 */
	public ImprimibleNotaInformativa(Vector notas, String nombreCirculo, String turno, String matricula, Hashtable tipoFases, Usuario usuarioSIR, String fechaImpresion, String tipoImprimible) {
		super(tipoImprimible);
		//setTransferObject(notas);
		this.notas = notas;
		this.nombreCirculo = nombreCirculo;
		this.turno = turno;
		this.matricula = matricula;
		this.tipoFases = tipoFases;
		this.usuarioSIR = usuarioSIR;
		this.fechaImpresion = fechaImpresion;
	}

	/**
	 * Genera el imprimible.
	 */
	public void generate(PageFormat pageFormat) {

		super.generate(pageFormat);
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");

		for (int i = 0; i < this.notas.size(); i++) {
			Nota nota = (Nota) notas.get(i);
			TipoNota tipoNota = nota.getTiponota();
			String nombre = tipoNota.getNombre();
			String descripcionTipo = tipoNota.getDescripcion();
			String descripcion = nota.getDescripcion();

			String line = (i + 1) + ": " + descripcionTipo + ":";

			Vector lineas = this.getLineas(line);
			for (int j = 0; j < lineas.size(); j++) {
				String linea = (String) lineas.get(j);
				this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);
			}

			if (descripcion == null) {
				descripcion = "";
			}

			//IMPRIME LA NOTA INFORMTATIVA
			lineas = this.getLineas(descripcion);
			for (int j = 0; j < lineas.size(); j++) {
				String linea = (String) lineas.get(j);
				this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
			}

			//IMPRIME LA FECHA DE CREACIÓN
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nota.getTime());
			String fecha = "";
			String mes = "";
			if (calendar.get(Calendar.MONTH) < 10) {
				mes = 0 + String.valueOf(calendar.get(Calendar.MONTH) + 1);
			} else {
				mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
			}

			fecha = calendar.get(Calendar.DAY_OF_MONTH) + "/" + mes + "/" + calendar.get(Calendar.YEAR);

			this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
			this.imprimirLinea(ImprimibleConstantes.TITULO2, "FECHA CREACIÓN     : " + fecha);

			//IMPRIME EL USUARIO DE CREACIÓN
			this.imprimirLinea(ImprimibleConstantes.TITULO2, "USUARIO CREACIÓN : " + (nota!=null && nota.getUsuario()!=null && ""+nota.getUsuario().getIdUsuario()!=null?""+nota.getUsuario().getIdUsuario():""));

			//DETERMINAR CUÁL ES LA FASE DE ORIGEN Y CUÁL ES LA FASE FINAL.
			TurnoHistoria thEnvio = null;
			TurnoHistoria thTemp = null;
			TurnoHistoria thUltima = null;

			if (turno != null) {
				Turno turnoNota = nota.getTurno();
				if (turnoNota != null) {

					List historia = turnoNota.getHistorials();
					if (historia != null && historia.size() > 0) {
						thUltima = (TurnoHistoria) historia.get(historia.size() - 1);
					}

					Iterator it = historia.iterator();
					while (it.hasNext()) {
						TurnoHistoria th = (TurnoHistoria) it.next();


						System.out.println("\n\n");

						System.out.println("FASE" + th.getNombreFase());
						if(th.getUsuario()!=null){
							System.out.println("USUARIO" + ""+th.getUsuario().getIdUsuario());
						}else{
							System.out.println("USUARIO ES NULL");
						}

						if(th.getUsuarioAtiende()!=null){
							System.out.println("USUARIO ATIENDE" + ""+th.getUsuarioAtiende().getIdUsuario());
						}else{
							System.out.println("USUARIO ATIENDE ES NULL");
						}

						System.out.println("0");

						//CUANDO EL ID DE LA FASE DE LA NOTA COINCIDE CON EL DE TURNO HISTORIA SE COLOCA EL NOMBRE DE LA FASE.
						if (th != null && nota != null && th.getFase().equals(nota.getIdFase()) && th.getIdTurnoHistoria().equals(nota.getIdTurnoHistoria()) ) {

							System.out.println("1");
							thEnvio = th;

							this.imprimirLinea(ImprimibleConstantes.TITULO2, "FASE ORIGEN             : " + th.getNombreFase());

							//CUANDO EL ID DE LA FASE DE LA NOTA NO COINCIDE CON EL DE TURNO HISTORIA.
						} else if (thEnvio != null) {

							System.out.println("2");

							//SI LA FASE ANTERIOR DEL CONTADOR ES IGUAL AL NOMBRE DE LA FASE DE LA NOTA.
							//SE INTENTA IMPRIMIR SÓLO SI LA FASE ES MANUAL SINO SE GUARDA HASTA ENCONTRAR UNA MANUAL
                                                        /*
                                                         * @author     :   Henry Gómez Rocha y Fernando Padilla
                                                         * @change     :   Se reordena la pregunta del if para que no genere
                                                                           un NullPointerException.
                                                         * Caso Mantis :   0004957
                                                         */
                                                        if (thEnvio.getFase().equals(th.getFaseAnterior())) {
								System.out.println("2.1");
								String tipoFase = (String) this.tipoFases.get(th.getFase());

								if (tipoFase != null && tipoFase.equals(CFase.TIPO_FASE_MANUAL)) {
									System.out.println("2.1.1");
									this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
									this.imprimirLinea(ImprimibleConstantes.TITULO2, "FASE DESTINO          : " + th.getNombreFase());
									if (th.getUsuarioAtiende() != null && ""+th.getUsuarioAtiende().getIdUsuario() != null) {
										System.out.println("2.1.1.1");
										this.imprimirLinea(ImprimibleConstantes.TITULO2, "USUARIO DESTINO   : " + (th!=null && th.getUsuarioAtiende()!=null && ""+th.getUsuarioAtiende().getIdUsuario()!=null?""+th.getUsuarioAtiende().getIdUsuario():""));
									} else if (th.getUsuarioAtiende() == null && thUltima != null && th.getFase().equals(thUltima.getFase())) {
										System.out.println("2.1.1.2");
										this.imprimirLinea(ImprimibleConstantes.TITULO2, "USUARIO DESTINO   : " + (usuarioSIR!=null && ""+usuarioSIR.getIdUsuario()!=null?""+usuarioSIR.getIdUsuario():""));
									}
									System.out.println("2.1.1.3");
									break;
								} else {
									System.out.println("2.1.2");
									thTemp = th;
								}

							    //SINO SE SE BUSCA LA FASE HASTA QUE SE ENCUENTRE UNA FASE MANUAL
							} else if (thTemp != null && th.getFaseAnterior().equals(thTemp.getFase())) {
								System.out.println("2.2");
								String tipoFase = (String) this.tipoFases.get(th.getFase());

								if (tipoFase != null && tipoFase.equals(CFase.TIPO_FASE_MANUAL)) {
									System.out.println("2.2.1");
									this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
									this.imprimirLinea(ImprimibleConstantes.TITULO2, "FASE DESTINO          : " + th.getNombreFase());
									if (th.getUsuarioAtiende() != null && ""+th.getUsuarioAtiende().getIdUsuario() != null) {
										System.out.println("2.2.1.1");
										this.imprimirLinea(ImprimibleConstantes.TITULO2, "USUARIO DESTINO   : " + (th!=null &&  th.getUsuarioAtiende()!=null && ""+th.getUsuarioAtiende().getIdUsuario()!=null? ""+th.getUsuarioAtiende().getIdUsuario():""));
									} else if (th.getUsuarioAtiende() == null && thUltima != null && th.getFase().equals(thUltima.getFase())) {
										System.out.println("2.2.1.2");
										this.imprimirLinea(ImprimibleConstantes.TITULO2, "USUARIO DESTINO   : " + (usuarioSIR!=null && ""+usuarioSIR.getIdUsuario()!=null?""+usuarioSIR.getIdUsuario():""));
									}
									System.out.println("2.2.1.3");
									break;
								} else {
									System.out.println("2.2.2");
									thTemp = th;
								}

							}

						} //FIN DE, SI YA SE ENCONTRO LA FASE EN QUE SE CREO LA NOTA INFORMATIVA

					} //FIN DEL CICLO

				} //FIN DE SI EL TURNO NO ES NULO

			} //FIN DE SI EL ID DEL TURNO NO ES NULO

		} //FIN DE LA IMPRESIÓN DE LAS NOTAS

	}

	/**
	 * Imprime el encabezado de la nota devolutiva.
	 */
	protected void makeNewPage() {
		super.makeNewPage();

		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "OFICINA DE REGISTRO DE INSTRUMENTOS PÚBLICOS DE " + nombreCirculo);
		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "");

		String titulo = "NOTA INFORMATIVA";

		int tam = this.notas.size();
		Nota nota = null;
		if (tam > 0) {
			nota = (Nota) this.notas.get(0);
			if (nota.getTiponota().isDevolutiva()) {
				titulo = "NOTA DEVOLUTIVA";
			}
		}

		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, titulo);

		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 10, "");

		String fechaImp = this.fechaImpresion;

		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, fechaImp);

		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "Página: " + this.getNumberOfPages());

		if (this.turno != null) {
			if(nota!=null && nota.getTiponota().isDevolutiva()){
				this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "TURNO: ",false);
				this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,(ImprimibleConstantes.MARGEN_IZQ+12)*4, this.turno);
			}
			else{
				this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "TURNO: " + this.turno);
			}
		}

		if (this.matricula != null) {
			if(nota!=null && nota.getTiponota().isDevolutiva()){
				this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "MATRICULA: ", false);
				this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,(ImprimibleConstantes.MARGEN_IZQ+8)*5, this.matricula);
			}else{
				this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "MATRICULA: " + this.matricula);
			}
		}

		//this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,"El documento con No. de radicación:" +"xxxx" +" y matrícula Inmobiliaria: "+"yyyyy");
		//this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,"ESCRITURA Nro. "+"del "+"dd-mm-aaaa" +" de "+" NOTARIA XX "+"Y CERTIFICADO ASOCIADO:"+"YYYYYYYY");

	}

	/**
	 * Funcion que recibe una cadena de texto que contiene caracteres especiales de fin de linea '\r' y '\n'
	 * indicando que se debe generar una nueva linea en un texto.
	 * @param line Linea con todo el texto.
	 * @return un vector de Strings con el mismo texto original, pero cada elemento del vector de respuesta
	 * representa un renglón en el texto.
	 */
	private Vector getLineas(String line) {

		String cad = "";
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) != '\r')
				cad += line.charAt(i);
		}

		String cadenas[] = cad.split("\n");

		int tam = cadenas.length;
		String cad2 = "";

		Vector vectorCad = new Vector();
		for (int i = 0; i < tam; i++) {
			vectorCad.add(cadenas[i]);

		}

		return vectorCad;
	}

}

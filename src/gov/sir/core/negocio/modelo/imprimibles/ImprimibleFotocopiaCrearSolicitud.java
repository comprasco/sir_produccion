package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.DocumentoFotocopia;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;

import java.util.List;
import java.util.Iterator;

import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLogger;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLoggerImp1;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
import java.awt.print.PageFormat;

public class ImprimibleFotocopiaCrearSolicitud extends ImprimibleBase {
        private static final long serialVersionUID = 1L;
	public static final String DEFAULT_IMPRIMIBEFOTOCOPIASCREARSOLICITUD_TITLE = "Comprobante de Solicitud de Documentos a ser Fotocopiados";
	public static final String DEFAULT_EMPRESA_NOMBRE = "OFICINA DE REGISTRO DE INSTRUMENTOS PÚBLICOS";

	protected static AppletLogger logger;

	protected Turno turno;
	protected SolicitudFotocopia solicitud;
	protected Circulo circulo;
	protected Usuario usuario;

	protected String fechaImpresionServidorFormatted;
	protected String titulo;
	protected String empresaNombre;
	
	/**Determina si el tamaño del papel es carta.**/
	private boolean tamanoCarta = true;	

	private String[] footLegendText;

        /* JAlcazar caso Mantis 05014 Acta - Requerimiento No 192 - Reimpresión de Recibo de Fotocopias
         * Constante que indica que es una reimpresión.
         */
        private boolean reimpresion;

        protected Pago pago;

	private ImprimibleFotocopiaCrearSolicitud(String tipoImprimible) {
		super(tipoImprimible);
		logger = AppletLoggerImp1.getAppletLogger();
		setImprimirMargen(false);
	}

	public ImprimibleFotocopiaCrearSolicitud(Turno turno, SolicitudFotocopia solicitud, Circulo circulo,String tipoImprimible) {
		this(tipoImprimible);
		this.turno = turno;
		this.circulo = circulo;
		this.solicitud = solicitud;
	}

	/**
	 * Configura las opciones de la página
	 */
	public void configure() {
		logger = AppletLoggerImp1.getAppletLogger();
		setTitulo(DEFAULT_IMPRIMIBEFOTOCOPIASCREARSOLICITUD_TITLE);
		setEmpresaNombre(DEFAULT_EMPRESA_NOMBRE);
		setImprimirLogoEnabled(false);
	}

	/* (non-Javadoc)
	 * @see gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase#makeNewPage()
	 */
	protected void makeNewPage() {

		super.makeNewPage();
		final int MAX_SIZE_HEADER_LINE = 80; //ImprimibleConstantes.MAX_NUM_CHAR_TITULO1
		final int MAX_SIZE_HEADER_LINE__NIT = MAX_SIZE_HEADER_LINE;

		String linea;
		linea = StringFormat.getCentrada(getEmpresaNombre(), ImprimibleConstantes.MAX_NUM_CHAR_TITULO1, ImprimibleConstantes.LONG_LOGO);
		this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);

		String circuloReg = this.circulo.getNombre();
		linea = StringFormat.getCentrada(circuloReg.toUpperCase(), ImprimibleConstantes.MAX_NUM_CHAR_TITULO1, ImprimibleConstantes.LONG_LOGO);
		this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);

		//imprimir el NIT
		imprimirNitRecibo(this.circulo, MAX_SIZE_HEADER_LINE__NIT);

	}

	/**
	 * Permite imprimir el NIT en el recibo
	 * @param thisCirculo
	 * @param startPosition
	 */
	public void imprimirNitRecibo(Circulo thisCirculo, int startPosition) {

		if (null != thisCirculo) {
			String nitOficina = thisCirculo.getNit();

			if (nitOficina != null) {
				String textoNit = "NIT: " + nitOficina;
				textoNit = StringFormat.getCentrada(textoNit, startPosition, 12);
				this.imprimirLinea(ImprimibleConstantes.TITULO2, textoNit);
				this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
			}
		}
	}

	// template method
	public void generate(PageFormat pageFormat) {
		configure();

		logger.info("Start-Method generate" + ":ImprimibleFotocopiaCrearSolicitud.generate");

		logger.info(" Start-Call super.generate");
		super.generate(pageFormat);
		logger.info(" End-Call super.generate");

		logger.info(" Start-Call imprimirEncabezado");
		imprimirEncabezado(pageFormat);
		logger.info(" End-Call imprimirEncabezado");


		logger.info(" Y ppal ES " + this.getI());
		logger.info(" Start-Call imprimirCuerpo");
		imprimirCuerpo(pageFormat);
		logger.info(" End-Call imprimirCuerpo");


		logger.info(" Start-Call imprimirPie");
		logger.info(" Y ppal ES " + this.getI());
		imprimirPie(pageFormat);
		logger.info(" End-Call imprimirPie");

		logger.info("End-Method generate." + ":ImprimibleFotocopiaCrearSolicitud.generate");
                
                /* Ellery Robles caso Mantis 07114 Acta - Requerimiento No 218 - Reimpresión de Recibo de Fotocopias
                 * Imprime nota si es una reimpresión.
                 */
                if(this.reimpresion){
                    this.textoReimpresion();
                }
	}
	
	/**
	 * Genera el documento sin los datos del usuario
	 * @param pageFormat
	 */
	public void generate2(PageFormat pageFormat) {
		configure();

		logger.info("Start-Method generate" + ":ImprimibleFotocopiaCrearSolicitud.generate");

		logger.info(" Start-Call super.generate");
		super.generate(pageFormat);
		logger.info(" End-Call super.generate");

		logger.info(" Start-Call imprimirEncabezado");
		imprimirEncabezado(pageFormat);
		logger.info(" End-Call imprimirEncabezado");


		logger.info(" Y ppal ES " + this.getI());
		logger.info(" Start-Call imprimirCuerpo");
		imprimirCuerpo2(pageFormat);
		logger.info(" End-Call imprimirCuerpo");

		logger.info("End-Method generate." + ":ImprimibleFotocopiaCrearSolicitud.generate");
	}

	//OPCIONES DE IMPRESIÓN DEL ENCABEZADO
	/**
	 * @param pageFormat
	 */
	protected void imprimirEncabezado(PageFormat pageFormat) {

		imprimirEncabezado_NumRecibo(pageFormat);
		//imprimir el tipo de solicitud de recibo.
		String _titulo = this.getTitulo();
		this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, ImprimibleConstantes.MARGEN_IZQ * 2, _titulo);

		//imprime la fecha y hora de impresion del recibo.
		String _fechaImp = this.getFechaImpresionServidorFormatted();
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 2, _fechaImp);

		//imprime el número de radicación.
		this.imprimirLinea(ImprimibleConstantes.TITULO2, ImprimibleConstantes.MARGEN_IZQ * 2, "No. RADICACIÓN: ", false);
		logger.debug("turno.getIdWorkflow()" + turno.getIdWorkflow());
		this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, ImprimibleConstantes.MARGEN_IZQ * 5, turno.getIdWorkflow());

		//deja una linea en blanco.
		//this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
	}

	protected void imprimirEncabezado_NumRecibo(PageFormat pageFormat) {
		// for subclass compatibility
	}

	//OPCIONES DE IMPRESIÓN DEL CUERPO
	/**
	 * @param pageFormat
	 */
	protected void imprimirCuerpo(PageFormat pageFormat) {

		logger.info("  Start-Call:" + "imprimirCuerpo_Items");
		imprimirCuerpo_Items(pageFormat);
		logger.info("  End-Call:" + "imprimirCuerpo_Items");
		
		logger.info(" Start-Method:" + "imprimirCuerpo");

		logger.info("  Start-Call:" + "imprimirCuerpo_Generales");
		imprimirCuerpo_Generales(pageFormat);
		logger.info("  End-Call:" + "imprimirCuerpo_Generales");

		logger.info(" End-Method:" + "imprimirCuerpo");

	}
	
	/**
	 * Genera el cuerpo sin los datos del usuario
	 * @param pageFormat
	 */
	protected void imprimirCuerpo2(PageFormat pageFormat) {

		logger.info("  Start-Call:" + "imprimirCuerpo_Items");
		imprimirCuerpo_Items(pageFormat);
		logger.info("  End-Call:" + "imprimirCuerpo_Items");
		
		logger.info(" End-Method:" + "imprimirCuerpo2");

	}

	/**
	 * @param pageFormat
	 */
	protected void imprimirCuerpo_Generales(PageFormat pageFormat) {

		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.SEPARADOR2);
		//TFS 3820: Se quita la línea Datos Generales:
		//this.imprimirLinea(ImprimibleConstantes.TITULO1, "Datos Generales:", true);

		logger.info("# Solicitud:" + solicitud);
		logger.info("# Solicitud.Ciudadano:" + solicitud.getCiudadano());

		this.imprimirSolicitante(solicitud);

                String local_RadicadoPor = null;


                // Bug 0522
                // Colocar Id de usuario en lugar de nombre
                // local_RadicadoPor = solicitud.getUsuario().getNombreCompletoUsuario();
                local_RadicadoPor = "" + solicitud.getUsuario().getIdUsuario(); // Long.toString

                logger.info("@@@ Radicado Por" + local_RadicadoPor );

                // Bug #0003493
                // temporal change
                if( this instanceof ImprimibleFotocopiaLiquidarSolicitud ) {
                   // sir_op_usuario.srio_nombre_de_usuario
                   // local_RadicadoPor = solicitud.getUsuario().getUsername();
                   // logger.info("@@@ Radicado Por" + local_RadicadoPor );
                }

		printLine_Indentation2(ImprimibleConstantes.TITULO2, "Radicado Por: " + local_RadicadoPor );

	}

	/**
	 * @param sol
	 */
	protected void imprimirSolicitante(Solicitud sol) {

        Ciudadano ciud  = sol.getCiudadano();
        String telefono = null;

        logger.info("# Ciudadano" + ciud);
        
		if (ciud != null){
			printLine_Indentation2(ImprimibleConstantes.TITULO2, "Solicitante:  " 
                    + this.getNombreCompleto(ciud));

            telefono = ciud.getTelefono();

            if(telefono != null){
    			printLine_Indentation2(ImprimibleConstantes.TITULO2, "Telefono:     "
                        + telefono);
            }
		}
	}

	/**
	 * @param ciudadano
	 * @return
	 */
	protected String getNombreCompleto(Ciudadano ciudadano) {
		String completo = "";
		String ape1;
		String ape2;

		if (ciudadano != null) {
			completo = ciudadano.getNombre();
			ape1 = ciudadano.getApellido1();
			ape2 = ciudadano.getApellido2();
			System.out.println("Apellido 1: " + ape1);
			System.out.println("Apellido 2: " + ape2);

			//es una empresa.
			if (CCiudadano.TIPO_DOC_ID_NIT.equalsIgnoreCase(ciudadano.getTipoDoc())) {
				if (ape1 == null) {
					ape1 = ImprimibleConstantes.RAZON_SOCIAL_NULL;
				}

				completo = ape1;
			} else {
				if (completo == null) {
					completo = ImprimibleConstantes.NOMBRE_NULL;
				}

				if (ape1 == null) {
					ape1 = ImprimibleConstantes.PRIMER_APELLIDO_NULL;
				}

				if (ape2 == null) {
					ape2 = ImprimibleConstantes.SEGUNDO_APELLIDO_NULL;
				}

				completo += (" " + ape1);
				completo += (" " + ape2);
			}
		} else {
			System.out.println("[ImprimibleReciboCertificado.getNombreCompleto]:Ciudadano es null");
		}

		return completo;
	}

	/**
	 * @param pageFormat
	 */
	protected void imprimirCuerpo_Items(PageFormat pageFormat) {
		// -----------------------------------------------------------------------

		// docuentos asociados ---------------------------------------------------
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.SEPARADOR2);
		this.imprimirLinea(ImprimibleConstantes.TITULO1, "Documentos Asociados a esta solicitud:", true);
		// this.imprimirLinea( ImprimibleConstantes.PLANO, ImprimibleConstantes.SEPARADOR2 );

		int totalCopias = 0;
		foreach : {
			SolicitudFotocopia solicitudFotocopia = solicitud;
			List documentosAsociados = solicitudFotocopia.getDocumentoFotocopia();

			Iterator iterator = documentosAsociados.iterator();
			long numHojasTotales = 0;
			int index = 0;
			for (; iterator.hasNext(); index++) {
				DocumentoFotocopia documento = (DocumentoFotocopia) iterator.next();
				imprimirCuerpo_Items_Node(documento, index + 1);
				totalCopias += (documento.getNumCopias());
			}
			this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.SEPARADOR2);

		} //:foreach

		this.imprimirLinea(ImprimibleConstantes.TITULO2, "Total Juegos de Copias: " + totalCopias, false);

	}

	/**
	 * @param documento
	 * @param index
	 */
	protected void imprimirCuerpo_Items_Node(DocumentoFotocopia documento, int index) {

		printLine_Indentation2(ImprimibleConstantes.PLANO, "" + index + ".");
		String descripcion = imprimirCuerpoReciboFotocopia_PrintNode_GetDescripcionSegment(documento.getDescripcion());
		printLine_Indentation3(ImprimibleConstantes.PLANO, "Tipo de Documento: " + documento.getTipoDocumento().getNombre());
		printLine_Indentation3(ImprimibleConstantes.PLANO, "Número de copias:  " + documento.getNumCopias());
		printLine_Indentation3(ImprimibleConstantes.PLANO, "Descripción:        " + descripcion);

	}

	/**
	 * @param descripcion
	 * @return
	 */
	protected String imprimirCuerpoReciboFotocopia_PrintNode_GetDescripcionSegment(String descripcion) {

		if (null == descripcion) {
			return "";
		}
		return descripcion;

	}

	//	OPCIONES DE IMPRESIÓN DEL PIE
	/**
	 * @param pageFormat
	 */
	protected void imprimirPie(PageFormat pageFormat) {
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.SEPARADOR2);
		System.out.println("Y ES : "+ this.getI());

		imprimirPie_Normatividad(pageFormat);
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		//this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.SEPARADOR2);
	}

	/**
	 * @param pageFormat
	 */
	protected void imprimirPie_Normatividad(PageFormat pageFormat) {

		// 5. Modificar el comentario de la normatividad
		boolean upperCase = false;

		String[] _legendText = getFootLegendText();

		if (null == (_legendText))
			return;

		if (upperCase) {
			int i = 0;
			for (; i < _legendText.length; i++) {
				_legendText[i] = _legendText[i].toUpperCase();
			}
		}

		imprimirStringArray(_legendText);
	}

	/**
	 * @param _legendText
	 */
	protected void imprimirStringArray(String[] _legendText) {

		if (null == (_legendText))
			return;

		logger.info("imprimiendo con " + ImprimibleConstantes.TAM_LETRAMENUDA);

		for (int i = 0; i < _legendText.length; i++) {
			printLine_Indentation3_LetraMenuda(_legendText[i]);
		}

	}

	//FUNCIONES DE COMPLEMENTO.
	protected void printLine_Indentation2(int estilo, String line) {
		int x = (int) (ImprimibleConstantes.MARGEN_IZQ + ImprimibleConstantes.MARGEN_IZQ * 0.25f);
		this.imprimirLinea(estilo, x, line, true);
	}

	protected void printLine_Indentation3(int estilo, String line) {
		int x = (int) (ImprimibleConstantes.MARGEN_IZQ + ImprimibleConstantes.MARGEN_IZQ * 0.50f);
		this.imprimirLinea(estilo, x, line, true);
	}

	protected void printLine_Indentation3_LetraMenuda(String line) {
		int x;
		String[] result = line.split("<br />");
		for (int xIndex = 0; xIndex < result.length; xIndex++) {
			x = (int) (ImprimibleConstantes.MARGEN_IZQ + ImprimibleConstantes.MARGEN_IZQ * 0.50f);
			//TFS 3824: IMPRIMIR EL TEXTO CON TILDE (SOLUCION DE CODIGO YA QUE LA BD EN PRODUCCION NO SOPORTA TILDES)
			this.imprimirLinea(ImprimibleConstantes.TAM_LETRAMENUDA, x, 
					imprimirLineaTildes(result[xIndex]), 
					ImprimibleConstantes.TAM_LETRAMENUDA_FOTOCOPIAS);
		}
	}
	
	private String imprimirLineaTildes(String linea){
		StringBuffer textoLinea = new StringBuffer(linea);
		
		//Se itera sobre el texto que posea el delimitador para tildes (#)
		while(textoLinea.indexOf(CTipoImprimible.DELIMITADOR_TILDE)!=-1){
			int indice = textoLinea.indexOf(CTipoImprimible.DELIMITADOR_TILDE);
			int indiceVocal = indice+1;
			char vocal = textoLinea.charAt(indiceVocal);
			switch(vocal){
				case 'a':
					textoLinea.replace(indiceVocal,indiceVocal+1, "á");
					break;
				case 'e':
					textoLinea.replace(indiceVocal,indiceVocal+1, "é");
					break;
				case 'i':
					textoLinea.replace(indiceVocal,indiceVocal+1, "í");
					break;
				case 'o':
					textoLinea.replace(indiceVocal,indiceVocal+1, "ó");
					break;
				case 'u':
					textoLinea.replace(indiceVocal,indiceVocal+1, "ú");
					break;
			}
			textoLinea.delete(indice, indice+1);
		}
		
		return textoLinea.toString();
	}

	public void setFootLegendText(String[] footLegendText) {
		this.footLegendText = footLegendText;
	}

	public String[] getFootLegendText() {
		return this.footLegendText;
	}

	public void setFechaImpresionServidorFormatted(String fechaImpresion) {
		this.fechaImpresionServidorFormatted = fechaImpresion;
	}

	protected void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	protected void setEmpresaNombre(String empresaNombre) {
		this.empresaNombre = empresaNombre;
	}

	public String getFechaImpresionServidorFormatted() {
		return fechaImpresionServidorFormatted;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getEmpresaNombre() {
		return empresaNombre;
	}
	
	public boolean isTamanoCarta() {
		return tamanoCarta;
	}

	public void setTamanoCarta(boolean tamanoCarta) {
		this.tamanoCarta = tamanoCarta;
	}

        /* Ellery Robles caso Mantis 07114 Acta - Requerimiento No 218 - Reimpresión de Recibo de Fotocopias
         * getTurno()
         */
        public Turno getTurno() {
		return turno;
	}

        /* Ellery Robles caso Mantis 07114 Acta - Requerimiento No 218 - Reimpresión de Recibo de Fotocopias
         * void textoReimpresion() Imprime nota reimpresión.
         */
        private void textoReimpresion() {
            String textoReimpresion = "Esta es una reimpresión ";
            String recibo = "";
            if (!(this.pago == null)){
                recibo = "del recibo " + this.pago.getLastNumRecibo();
            }
            this.imprimirLinea(ImprimibleConstantes.PLANO, textoReimpresion + recibo);
        }

        /* Ellery Robles caso Mantis 07114 Acta - Requerimiento No 218 - Reimpresión de Recibo de Fotocopias
         * Set reimpresion.
         */
        public void setReimpresion(boolean b) {
            reimpresion = b;
        }
}
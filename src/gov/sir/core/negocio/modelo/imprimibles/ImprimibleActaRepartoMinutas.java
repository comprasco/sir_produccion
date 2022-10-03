package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.constantes.CMinuta;
import gov.sir.core.negocio.modelo.constantes.CTipoEncabezado;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLogger;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLoggerImp1;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
import gov.sir.core.util.DateFormatUtil;
import java.awt.print.PageFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ppabon
 * Clase que representa el acta del reparto de minutas.
 */
public class ImprimibleActaRepartoMinutas extends ImprimibleBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected AppletLogger logger;

	/** Minuta */
	private Minuta minuta;
	
	/** Circulo asociado al imprimible */
	private Circulo circulo;
	
	/** Fecha de impresión del imprimible */
	private Date fechaReparto;
	
	
	String ciudad;
	String fecha;
	String hora;
	String coordinadorReparto;
	String registradorReparto;
	String directorReparto;
	
	String textoReparto = null;
	String textoObservaciones = "Observaciones: ";
	
	/*Identificador de la minuta sin Repartir*/
	String idMinutaSinRepartir;
	
	/*Identificador del tipo de la minuta sin Repartir*/
	String tipoSinRepartir;
	
	

	/**
	 * @param minuta
	 * @param circulo
	 * @param fechaImpresion 
	 */
	public ImprimibleActaRepartoMinutas(Minuta minuta, Circulo circulo, Date fechaReparto, String ciudad, String coordinador, String director,String tipoImprimible) {
		super(tipoImprimible);

		//SE CREA EL LOGGER PARA LOS MENSAJES EN LA EJECUCIÓN
		AppletLogger loggerImpl = AppletLoggerImp1.getAppletLogger();
		if (null == this.logger) {
			this.logger = loggerImpl;
		}
		
		this.minuta = minuta;
		this.circulo = circulo;
		this.fechaReparto = fechaReparto;

		if(this.fechaReparto!=null){
			this.fecha = this.getFechaTexto(this.fechaReparto);		
			this.hora = this.getHoraTexto(this.fechaReparto);
		}else{
			this.fecha = " ";		
			this.hora = " ";
		}
		
		if(ciudad==null){
			this.ciudad = " CIUDAD ";	
		}
		else
		{
			this.ciudad = ciudad;
		}
		if(director==null){
			this.directorReparto = " DIRECTOR ";	
		}
		if(coordinador==null){
			this.coordinadorReparto = " COORDINADOR ";	
		}
		
		if(registradorReparto==null){
			this.registradorReparto = " REGISTRADOR ";	
		}
				
		//NO PINTAR EL MARGEN EN EL IMPRIMIBLE
		this.setImprimirMargen(false);

	}


	/**
	 * Este metodo se invoca para generar el contenido del imprimible.
	 * Genera el vector de hojas imprimibles con toda la información que se va a imprimir.
	 */
	public void generate(PageFormat pageFormat) {

		logger.debug("Start Executing Super Method");
		super.generate(pageFormat);

		logger.debug("ExecutedSuper");
		logger.debug("Minuta.class = " + this.minuta);

		System.out.println("Imprimir datos del reparto.....");
		//ENCABEZADO DEL IMPRIMIBLE
		this.imprimirEncabezadoActa();

		System.out.println("Imprimir datos de las minutas.....");
		//DATOS DE LA MINUTA
		this.imprimirCuerpoActaReparto();

	}
	
	/**
	 * Imprime los datos del reparto.
	 */
	private void imprimirEncabezadoActa() {
		
		//deja una linea en blanco.
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		
		// Se debe imprimir encabezado
		String encabezado1 = StringFormat.getCentrada("MINISTERIO DE JUSTICIA Y DEL DERECHO",
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);
		String encabezado2 = StringFormat.getCentrada("SUPERINTENDECIA DE NOTARIADO Y REGISTRO",
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);
		String encabezado3 = StringFormat.getCentrada("OFICINA DE REGISTRO DE INSTRUMENTOS PUBLICOS",
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);
		
		if (this.circulo.getTipoTextoEncabezado().equals(CTipoEncabezado.ID_ENCABEZADO_NORMAL)) {
			this.imprimirLinea(ImprimibleConstantes.TITULO1,encabezado2, true);
			this.imprimirLinea(ImprimibleConstantes.TITULO1,encabezado3, true);
		} else {
			if (this.circulo.getTipoTextoEncabezado().equals(CTipoEncabezado.ID_ENCABEZADO_PRINCIPAL)) {
				this.imprimirLinea(ImprimibleConstantes.TITULO1,encabezado1, true);
				this.imprimirLinea(ImprimibleConstantes.TITULO1,encabezado2, true);
			} 
		}
		
		String nombreCirculo = StringFormat.getCentrada(this.circulo.getNombre(),
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);
		int tam = nombreCirculo.length();
		
		int estandar = 250;
		
		if (tam <= 15) {
				estandar = estandar + ((15 - tam) * 5);
		} else {
			    estandar = estandar - ((15 - tam) * 5);
		}
		
		this.imprimirLinea(ImprimibleConstantes.TITULO1, nombreCirculo, true);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		
		String informacionReparto = "ACTA DE REPARTO NOTARIAL ";
		
		//this.imprimirLinea(ImprimibleConstantes.TITULO2, 200, "Fecha del reparto : " + (fechaReparto!=null  ?(this.formatear(fechaReparto,"dd/MM/yyyy")):""), false);
		
		if(this.minuta.isNormal()){
			informacionReparto =  informacionReparto + CMinuta.ORDINARIO;
		}else{
			if(minuta.getCirculoNotarial() != null ) {
				informacionReparto =  informacionReparto + CMinuta.EXTRAORDINARIO;
			} else {
				if (this.getTipoSinRepartir() != null) {
					informacionReparto =  informacionReparto + this.getTipoSinRepartir();
				} else {
					informacionReparto =  informacionReparto + CMinuta.ORDINARIO;	
				}
			}
				
		}
		
		if(minuta!=null && minuta.getRepartoNotarial()!=null && minuta.getRepartoNotarial().getIdRepartoNotarial()!=null){
			informacionReparto = informacionReparto + " NÚMERO: " + minuta.getRepartoNotarial().getIdRepartoNotarial();
		}else{
			if (this.getIdMinutaSinRepartir() != null) {
				informacionReparto = informacionReparto + " NÚMERO: " + this.getIdMinutaSinRepartir();
			} else {
				informacionReparto = informacionReparto + " NÚMERO: " + "";	
			}
			
		}
		
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "Pagina 1");
		
		if(minuta.getCirculoNotarial()!=null && minuta.getCirculoNotarial().getNombre()!=null){
			informacionReparto = informacionReparto + " MUNICIPIO: " + minuta.getCirculoNotarial().getNombre();
		}
		informacionReparto=StringFormat.getCentrada(informacionReparto, 
                									ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                									ImprimibleConstantes.LONG_LOGO);
		this.imprimirLinea(ImprimibleConstantes.TITULO2, informacionReparto);
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
	}	
	

	/**
	 * Imprime los datos de la minuta.
	 */
	private void imprimirCuerpoActaReparto() {
		
		/*//SE DEFINEN EL TEXTO POR DEFECTO DEL IMPRIMIBLE
		if(minuta.getCirculoNotarial() != null)
			this.imprimirLinea(ImprimibleConstantes.PLANO, "OFICINA REPARTO :                " + minuta.getCirculoNotarial().getNombre());
		else
			this.imprimirLinea(ImprimibleConstantes.PLANO, "OFICINA REPARTO :                " + this.circulo.getNombre());
		*/
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		
		if (textoReparto == null)
		{
			textoReparto = "<p>En la ciudad de "+this.ciudad+", el " + this.fecha+ " a las  " + this.hora + " " +
			" en el Despacho del(la) Registrador(a) Principal de la oficina de Registro de Instrumentos Publicos, se reunieron " +
			" el(la) Doctor(a) " + this.registradorReparto + ", Registrador(a) Principal de la mencionada oficina y el(la) Funcionaria(a) " +
			" encargado(a) del Reparto, Señor(a) " + this.directorReparto+  ", con el fin de realizar la" +
			" asignación de las minutas radicadas hasta la fecha, diligencia de la que se obtuvo el resultado " +
			" contenido en el listado (diario radicador) que se anexa y que forma parte integral del presente Reparto Notarial.</p>";
		}
		
		else
		{
			textoReparto = textoReparto.replaceAll("LACIUDAD", this.ciudad.toUpperCase());
			textoReparto = textoReparto.replaceAll("LAFECHA", this.fecha.toUpperCase());
			textoReparto = textoReparto.replaceAll("LAHORA", this.hora.toUpperCase());
			textoReparto = textoReparto.replaceAll("ELDIRECTORREPARTO", this.directorReparto.toUpperCase());
			textoReparto = textoReparto.replaceAll("ELCOORDINADORREPARTO", this.coordinadorReparto.toUpperCase());
			textoReparto = textoReparto.replaceAll("ELREGISTRADORREPARTO", this.registradorReparto.toUpperCase());
		}
		
		String textoFin = "No siendo otro el objeto de la presente diligencia se termina y firma por quienes en ella intervinieron:";
		
		String textoDelegados = "Delegados asistentes:";
		
		String textoLinea = "_______________________________";
		
		String textoLineaLarga = "_________________________________________________";
		
		String elRegistrador = "El(la) Registrador(a)";
		String finElRegistrador = "de la Oficina de Registro:";
		
		String elDirector = "El(la) Funcionario(a)";
		String finElDirector = "Encargado(a) del Reparto:";
			
			
		//SE IMPRIME EL ACTA DE REPARTO NOTARIAL
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, textoReparto.toUpperCase());
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, textoObservaciones);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, textoFin);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, textoDelegados,false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 150, textoLinea, false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 310, textoLinea);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");		
		this.imprimirLinea(ImprimibleConstantes.PLANO, 150, textoLinea, false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 310, textoLinea);

		this.imprimirLinea(ImprimibleConstantes.PLANO, "");		
		this.imprimirLinea(ImprimibleConstantes.PLANO, 150, textoLinea, false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 310, textoLinea);


		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, elRegistrador);
		this.imprimirLinea(ImprimibleConstantes.PLANO, finElRegistrador,false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 200, textoLineaLarga);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 280, this.registradorReparto);	
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");				
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, elDirector);
		this.imprimirLinea(ImprimibleConstantes.PLANO, finElDirector,false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 200, textoLineaLarga);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 280, this.directorReparto);		
		
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "OBSERVACIONES");
		this.imprimirLinea(ImprimibleConstantes.PLANO, minuta.getComentario()!=null?minuta.getComentario():"");	
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "Fecha y Hora de Entrega: ______________________");
				
	}

	
	/**
	 * @param fecha
	 * @param formato
	 * @return
	 */
	public  String formatear(Date fecha, String formato){
		if (formato != null){
			return DateFormatUtil.format(formato, fecha);
		}
		else{
			return fecha.toString();
		}
	}	
	
	/**
	 * Método que retorna la cadena con la fecha en formato de texto.
	 * @param fechaActual
	 * @return String
	 */
	private String getFechaTexto(Date fechaActual) {
		Calendar c = Calendar.getInstance();
		c.setTime(fechaActual);
		
		int dia;
		int ano;
		int hora;
		String min;
		String seg;
		String mes;
	
		dia = c.get(Calendar.DAY_OF_MONTH);
		mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
		ano = c.get(Calendar.YEAR);
	
		hora = c.get(Calendar.HOUR_OF_DAY);
	
		if (hora > 12) {
			hora -= 12;
		}
	
		min = formato(c.get(Calendar.MINUTE));
		seg = formato(c.get(Calendar.SECOND));
	
		String fechaImp = "" + dia + " de " + mes + " de " + ano;
	
		return fechaImp;
	}
	
	/**
	 * Método que retorna la cadena con la la hora a partir de un objeto Date.
	 * @param fechaActual
	 * @return String
	 */
	private String getHoraTexto(Date fechaActual) {
		Calendar c = Calendar.getInstance();
		c.setTime(fechaActual);		

		int hora;
		String min;
		String seg;
		
		hora = c.get(Calendar.HOUR_OF_DAY);
		
		if (hora > 12) {
			hora -= 12;
		}
		
		min = formato(c.get(Calendar.MINUTE));
		seg = formato(c.get(Calendar.SECOND));
		
		String horaImp = "" + formato(hora) + ":" + min + ":" + seg + " " + DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));
		
		return horaImp;
	}


	public String getTextoReparto() {
		return textoReparto;
	}


	public void setTextoReparto(String textoReparto) {
		this.textoReparto = textoReparto;
	}


	public String getCoordinadorReparto() {
		return coordinadorReparto;
	}


	public void setCoordinadorReparto(String coordinadorReparto) {
		this.coordinadorReparto = coordinadorReparto;
	}


	public String getDirectorReparto() {
		return directorReparto;
	}


	public void setDirectorReparto(String directorReparto) {
		this.directorReparto = directorReparto;
	}


	public String getRegistradorReparto() {
		return registradorReparto;
	}


	public void setRegistradorReparto(String registradorReparto) {
		this.registradorReparto = registradorReparto;
	}


	public String getTextoObservaciones() {
		return textoObservaciones;
	}


	public void setTextoObservaciones(String textoObservaciones) {
		this.textoObservaciones = this.textoObservaciones + textoObservaciones;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getIdMinutaSinRepartir() {
		return idMinutaSinRepartir;
	}


	public void setIdMinutaSinRepartir(String idMinutaSinRepartir) {
		this.idMinutaSinRepartir = idMinutaSinRepartir;
	}


	public String getTipoSinRepartir() {
		return tipoSinRepartir;
	}


	public void setTipoSinRepartir(String tipoSinRepartir) {
		this.tipoSinRepartir = tipoSinRepartir;
	}
}

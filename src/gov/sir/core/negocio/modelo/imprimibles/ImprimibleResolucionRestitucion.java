package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLogger;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLoggerImp1;
import gov.sir.core.util.DateFormatUtil;
import java.awt.print.PageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author ppabon
 * Clase que representa el acta del reparto de minutas.
 */
public class ImprimibleResolucionRestitucion extends ImprimibleBase {

	protected AppletLogger logger;
        private static final long serialVersionUID = 1L;
	/** Circulo asociado al imprimible */
	private Circulo circulo;
	
	/** Fecha en que se hace el análsis de restitución.*/
	private Date fechaAnalisis;
	
	/** Fecha de la resolución */
	private Date fechaResolucion;
	
	/** Turno reparto */
	private String turnoReparto;
	
	/** Turno restitucion*/
	private String turnoRestitucion;
	
	/** Resolución*/
	private String resolucion;
	
	/** Causal de restitución*/
	private String causalRestitucion;	

	/** Turno Anterior*/
	private Turno turnoAnterior;	
	
	/** Resumen de la resolución*/
	private String resumenResolucion;

	/** Observaciones de la resolución*/
	private String observacionesResolucion;
	
	private List listaDatosTurno;
	

	/**
	 * @param minuta
	 * @param circulo
	 * @param fechaImpresion 
	 */
	public ImprimibleResolucionRestitucion(List turnoRestitucion, Circulo circulo,Date fechaAnalisis,  String resolucion,  Date fechaResolucion, String observacionesResolucion, String resumenResolucion, String tipoImprimible) {
		super(tipoImprimible);

		//SE CREA EL LOGGER PARA LOS MENSAJES EN LA EJECUCIÓN
		AppletLogger loggerImpl = AppletLoggerImp1.getAppletLogger();
		if (null == this.logger) {
			this.logger = loggerImpl;
		}
		
		this.listaDatosTurno = turnoRestitucion;
		
		this.circulo = circulo;
		
		//this.turnoReparto = turnoReparto;
		//this.turnoRestitucion = turnoRestitucion;
		//this.turnoAnterior = turnoAnterior;		
		//this.causalRestitucion = causalResolucion;		

		this.fechaAnalisis = fechaAnalisis;		

		this.resolucion = resolucion;
		this.fechaResolucion = fechaResolucion;
		this.observacionesResolucion = observacionesResolucion;
		this.resumenResolucion = resumenResolucion;


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

		System.out.println("Imprimir datos del reparto.....");
		
		//Aca se recorre la lista de los turnos
		
		Iterator iListaTurnos = listaDatosTurno.iterator();

		while(iListaTurnos.hasNext()) {
			List datosTurno = (List)iListaTurnos.next();
			this.turnoReparto = (String) datosTurno.get(0);// turnoReparto;
			this.turnoRestitucion = (String) datosTurno.get(1);//turnoRestitucion;
			this.turnoAnterior = (Turno) datosTurno.get(2);//turnoAnterior;		
			this.causalRestitucion = (String) datosTurno.get(3);//causalResolucion;		
			
			//ENCABEZADO DEL IMPRIMIBLE
			this.imprimirEncabezadoResolucion();

			System.out.println("Imprimir datos de las minutas.....");
			//DATOS DE LA MINUTA
			this.imprimirCuerpoActaReparto();
		}
        
		//DATOS DE LA RESOLUCION
		this.imprimirCuerpoResolucion();

	}
	
	/**
	 * Imprime el encabezado de la resolución .
	 */
	private void imprimirEncabezadoResolucion() {
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO2,  "Turno reparto : " + (turnoReparto!=null  ?turnoReparto:""), false);
		this.imprimirLinea(ImprimibleConstantes.TITULO2, 200, "Turno restitución : " + (turnoRestitucion!=null  ?turnoRestitucion:""), false);		
		this.imprimirLinea(ImprimibleConstantes.TITULO2, 400, "Fecha análisis : " + (fechaAnalisis!=null  ?(this.formatear(fechaAnalisis,"dd/MM/yyyy")):""), false);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
	}	
	

	/**
	 * Imprime los datos de la resolución.
	 */
	private void imprimirCuerpoActaReparto() {
		
		SolicitudRepartoNotarial solReparto = (SolicitudRepartoNotarial)turnoAnterior.getSolicitud();
		Minuta minuta = null;
		String categoria = " ";
		if(solReparto!=null){
			minuta = solReparto.getMinuta();
		}
		 
		if(minuta!=null){
			
			if (minuta.getCategoria() != null) {
				categoria = minuta.getCategoria().getNombre();
			}
			
			
			if(minuta.getOficinaCategoriaAsignada()!=null){
				if(minuta.getOficinaCategoriaAsignada().getOficinaOrigen()!=null){
					if(minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getNombre()!=null &&
					   minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getNumero()!=null
					){
						String notaria = minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getNumero() + " - ";
						notaria = notaria + minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getNombre();
						this.imprimirLinea(ImprimibleConstantes.TITULO2, "NOTARIA QUE SOLICITA RESTITUCIÓN :  " + notaria);
						this.imprimirLinea(ImprimibleConstantes.TITULO2, "CATEGORIA DE LA MINUTA :  " + categoria);
					}
				}
			}
			
			
		}
		
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "CAUSAL DE RESTITUCIÓN :                        " + (causalRestitucion!=null?causalRestitucion.toUpperCase():""));
		
	//	this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "==========================================================================================================================================================");
	
	}
	
	
	/**
	 * Imprime los datos de la resolución.
	 */
	private void imprimirCuerpoResolucion() {
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "RESOLUCIÓN");
		this.imprimirLinea(ImprimibleConstantes.PLANO, (resolucion!=null?resolucion.toUpperCase():"") );
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "FECHA RESOLUCIÓN");
		this.imprimirLinea(ImprimibleConstantes.PLANO, (fechaResolucion!=null  ?(this.formatear(fechaResolucion,"dd/MM/yyyy")):"") );	
	
		if(this.resumenResolucion!=null && !this.resumenResolucion.equals("")){
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");
			this.imprimirLinea(ImprimibleConstantes.TITULO2, "RESPUESTA");		
			this.imprimirLinea(ImprimibleConstantes.TITULO2, this.resumenResolucion);
				
		}
		
		if(this.observacionesResolucion!=null && !this.observacionesResolucion.equals("")){
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");
			this.imprimirLinea(ImprimibleConstantes.TITULO2, "OBSERVACIONES DE LA RESOLUCIÓN");		
			this.imprimirLinea(ImprimibleConstantes.TITULO2, this.observacionesResolucion);
		}
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


	public List getListaDatosTurno() {
		return listaDatosTurno;
	}


	public void setListaDatosTurno(List listaDatosTurno) {
		this.listaDatosTurno = listaDatosTurno;
	}
			
		
	
}

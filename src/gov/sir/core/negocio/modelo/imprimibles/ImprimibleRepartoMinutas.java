package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.MinutaAccionNotarial;
import gov.sir.core.negocio.modelo.MinutaEntidadPublica;
import gov.sir.core.negocio.modelo.OtorganteNatural;
import java.text.NumberFormat;
import gov.sir.core.negocio.modelo.constantes.CMinuta;
import gov.sir.core.negocio.modelo.constantes.CTipoEncabezado;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLogger;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLoggerImp1;
import gov.sir.core.util.DateFormatUtil;
import java.awt.print.PageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * @author ppabon
 * Clase que representa el imprimible de una constancia de reparto notarial.
 */
public class ImprimibleRepartoMinutas extends ImprimibleBase {

	protected AppletLogger logger;

	/** Minutas */
	private Hashtable minutas;

	/** Minuta */
	private Minuta minuta;
	
	/** Circulo asociado al imprimible */
	private Circulo circulo;
	
	/** Fecha de impresión del imprimible */
	private String fechaImpresion;
	
	/** Fecha de impresión del imprimible */
	private Date fechaReparto;
	private static final long serialVersionUID = 1L;
	/** Identificador de la minuta sin Repartir */
	private String idMinutaSinRepartir;
	
	/** Identificador del tipo de la minuta sin Repartir */
	private String tipoSinRepartir;
	
	/** Listado de las minutas en orden*/
	private ArrayList minutasOrden;
	
	private String nombreCirculoRegistral;
	

	/**
	 * @param minuta
	 * @param circulo
	 * @param fechaImpresion 
	 */
	public ImprimibleRepartoMinutas(Hashtable minutas, Circulo circulo, String fechaImpresion, Date fechaReparto, String tipoImprimible) {
		super(tipoImprimible);

		//SE CREA EL LOGGER PARA LOS MENSAJES EN LA EJECUCIÓN
		AppletLogger loggerImpl = AppletLoggerImp1.getAppletLogger();
		if (null == this.logger) {
			this.logger = loggerImpl;
		}
		
		this.minutas = minutas;
		
		if(minutas!=null){
			Enumeration enumeration = minutas.keys();
			if(enumeration.hasMoreElements()){
				String key = (String)enumeration.nextElement();
				this.minuta = (Minuta)minutas.get(key);				
			}
		}else{
			minutas = new Hashtable();
			this.minuta = null;
		}
		
		this.fechaReparto = fechaReparto;
		
		
		
		this.circulo = circulo;
		this.fechaImpresion = fechaImpresion;

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
		this.imprimirDatosReparto();

		System.out.println("Imprimir datos de las minutas.....");
		//DATOS DE LA MINUTA
		this.imprimirDatosMinuta();

	}
	
	/**
	 * Imprime los datos del reparto.
	 */
	private void imprimirDatosReparto() {
		
		//deja una linea en blanco.
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		
		//Se debe imprimir encabezado
		String encabezado1 = "    MINISTERIO DE JUSTICIA Y DEL DERECHO";
		String encabezado2 = "   SUPERINTENDECIA DE NOTARIADO Y REGISTRO";
		String encabezado3 = "OFICINA DE REGISTRO DE INSTRUMENTOS PUBLICOS";
		
		if (this.circulo.getTipoTextoEncabezado().equals(CTipoEncabezado.ID_ENCABEZADO_NORMAL)) {
			this.imprimirLinea(ImprimibleConstantes.TITULO1, 180 , encabezado2, true);
			this.imprimirLinea(ImprimibleConstantes.TITULO1, 175 , encabezado3, true);
		} else {
			if (this.circulo.getTipoTextoEncabezado().equals(CTipoEncabezado.ID_ENCABEZADO_PRINCIPAL)) {
				this.imprimirLinea(ImprimibleConstantes.TITULO1, 180 , encabezado1, true);
				this.imprimirLinea(ImprimibleConstantes.TITULO1, 180 , encabezado2, true);
			} 
		}
		
		//Se realizar algorimo de centrado el estandar es 15
		
		String nombreCirculo = this.circulo.getNombre();
		int tam = nombreCirculo.length();
		
		int estandar = 250;
		
		if (tam <= 15) {
				estandar = estandar + ((15 - tam) * 5);
		} else {
			    estandar = estandar - ((15 - tam) * 5);
		}
		 
		this.imprimirLinea(ImprimibleConstantes.TITULO1, estandar ,this.circulo.getNombre(), true);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		
		if(minuta!=null && minuta.getCirculoNotarial()!=null) {
			this.imprimirLinea(ImprimibleConstantes.TITULO1, 180  ,"RADICADOR DE MINUTAS DE : "+ minuta.getCirculoNotarial().getNombre());
		} else {
			if (nombreCirculoRegistral!=null){
				this.imprimirLinea(ImprimibleConstantes.TITULO1, 180  ,"RADICADOR DE MINUTAS DE : "+ nombreCirculoRegistral);	
			} else {
				this.imprimirLinea(ImprimibleConstantes.TITULO1, 180  ,"RADICADOR DE MINUTAS DE : "+ this.circulo.getNombre());
			}
		}
			
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		
		if(minuta!=null && minuta.getRepartoNotarial()!=null && minuta.getRepartoNotarial().getIdRepartoNotarial()!=null){
			this.imprimirLinea(ImprimibleConstantes.TITULO2, "Reparto número: " + minuta.getRepartoNotarial().getIdRepartoNotarial(), false);	
		}else{
			if (this.getIdMinutaSinRepartir()!=null) {
				this.imprimirLinea(ImprimibleConstantes.TITULO2, "Reparto número: " + this.getIdMinutaSinRepartir(), false);
			} else {
				this.imprimirLinea(ImprimibleConstantes.TITULO2, "Reparto número : ", false);
			}
		}
		
		this.imprimirLinea(ImprimibleConstantes.TITULO2, 200, "Fecha del reparto : " + (fechaReparto!=null  ?(this.formatear(fechaReparto,"dd/MM/yyyy K:mm:ss a")):""), false);
		
		if(this.minuta!=null && this.minuta.isNormal()){
			this.imprimirLinea(ImprimibleConstantes.TITULO2, 400, "Tipo de reparto : " + CMinuta.ORDINARIO);	
		}else{
			if (this.getTipoSinRepartir() != null) {
				this.imprimirLinea(ImprimibleConstantes.TITULO2, 400, "Tipo de reparto : " + this.getTipoSinRepartir());	
			} else {
				this.imprimirLinea(ImprimibleConstantes.TITULO2, 400, "Tipo de reparto : " + CMinuta.EXTRAORDINARIO);	
			}
						
		}
		
		
				
		String fechaImp = this.fechaImpresion;
		this.imprimirLinea(ImprimibleConstantes.PLANO, fechaImp);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
	}	
	

	/**
	 * Imprime los datos de la minuta.
	 */
	private void imprimirDatosMinuta() {
		
		if(this.minutas.size()>0){

			this.imprimirLinea(ImprimibleConstantes.PEQUE, "RADICACIÓN",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 100, "CONTRATO",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 200, "CATEG.",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 240, "OTORGANTE-1",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 370, "OTORGANTE-2",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 450, "UNID.",false);		
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 480, "VALOR",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 525, "NOTARIA ASIGNADA",true);
			
			this.imprimirLinea(ImprimibleConstantes.PEQUE, "============",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 100, "========",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 200, "======",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 240, "============",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 370, "============",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 450, "=====",false);		
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 480, "=====",false);
			this.imprimirLinea(ImprimibleConstantes.PEQUE, 525, "=======",true);
		
		
			for (int i = 0; i< minutasOrden.size(); i++) {
			
				String key = (String) minutasOrden.get(i);
				Minuta min = (Minuta)minutas.get(key);				
				
				//CARGAR LA NOTARIA ASIGNADA
				String notariaAsignada =" ";
				if(min.getOficinaCategoriaAsignada()!=null&&min.getOficinaCategoriaAsignada().getOficinaOrigen()!=null){
					notariaAsignada = min.getOficinaCategoriaAsignada().getOficinaOrigen().getNombre();
				}	
				if(notariaAsignada !=null && notariaAsignada.length()>25){
					notariaAsignada = notariaAsignada.substring(0,25);
				}			
				
				//CARGAR EL ACTO
				String accionNotarial = " ";
				if(min.getAccionesNotariales()!=null && min.getAccionesNotariales().size()>0){
					List accionesNotariales = min.getAccionesNotariales();
					Iterator iterator = accionesNotariales.iterator();
					if(iterator.hasNext()){
						MinutaAccionNotarial man = (MinutaAccionNotarial)iterator.next();
						AccionNotarial an = man.getAccionNotarial();
						if(an!=null){
							accionNotarial = an.getNombre();
						}
					}
				}
				
				if(accionNotarial !=null && accionNotarial.length()>21){
					accionNotarial = accionNotarial.substring(0,21);
				}
				
				
				//CARGAR LA ENTIDAD PÚBLICA
				String entPublica ="";
				if(min.getEntidadesPublicas()!=null && min.getEntidadesPublicas().size()>0){
						
					List entidadesPublicas = min.getEntidadesPublicas();
					Iterator iterator = entidadesPublicas.iterator();
					if (iterator.hasNext()){
						MinutaEntidadPublica mep = (MinutaEntidadPublica)iterator.next();
						EntidadPublica ep = mep.getEntidadPublica();
						if(ep!=null){
							entPublica = ep.getNombre();
						}				
					}
				
				}
				if(entPublica !=null && entPublica.length()>27){
					entPublica = entPublica.substring(0,27);
				}
		
				//CARGAR EL OTORGANTE
				String otorgante ="";
				if(min.getOtorgantesNaturales()!=null && min.getOtorgantesNaturales().size()>0){
					List otorgantes = min.getOtorgantesNaturales();
					Iterator iterator = otorgantes.iterator();
					while (iterator.hasNext()){
						OtorganteNatural oto = (OtorganteNatural)iterator.next();
						if(oto!=null){
							otorgante=oto.getNombre();	
						}				
					}
				
				}	

				if(otorgante !=null && otorgante.length()>15){
					otorgante = otorgante.substring(0,15);
				}							
				
				String municipio = "";
				if(min.getCirculoNotarial()!=null && min.getCirculoNotarial().getNombre()!=null){
					municipio = " MUNICIPIO: " + min.getCirculoNotarial().getNombre();
				}
				
				this.imprimirLinea(ImprimibleConstantes.PEQUE, key ,false);
				this.imprimirLinea(ImprimibleConstantes.PEQUE, 100, accionNotarial,false);
				this.imprimirLinea(ImprimibleConstantes.PEQUE, 200, min.getCategoria().getNombre(),false);
				this.imprimirLinea(ImprimibleConstantes.PEQUE, 240, entPublica,false);
				this.imprimirLinea(ImprimibleConstantes.PEQUE, 370, otorgante,false);
				this.imprimirLinea(ImprimibleConstantes.PEQUE, 450, " " + min.getUnidades(),false);		
				this.imprimirLinea(ImprimibleConstantes.PEQUE, 480, NumberFormat.getInstance().format(min.getValor())  ,false);
				this.imprimirLinea(ImprimibleConstantes.PEQUE, 525, notariaAsignada, true);
				this.imprimirLinea(ImprimibleConstantes.PEQUE, 525, municipio, true);
							
							
			}
		}					

	}

	
	
	/**
	 * Imprime el encabezado del imprimible.
	 */
	protected void makeNewPage() {
		super.makeNewPage();

	}


	/**
	 * Metodo que retorna la cadena con la fecha actual de impresión.
	 * @return String con la fecha de impresión
	 */
	protected String getFechaImpresion() {
		return this.fechaImpresion;
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
	 * @return
	 */
	public Hashtable getMinutas() {
		return minutas;
	}

	/**
	 * @param hashtable
	 */
	public void setMinutas(Hashtable hashtable) {
		minutas = hashtable;
	}

	/**
	 * @return
	 */
	public Date getFechaReparto() {
		return fechaReparto;
	}

	/**
	 * @param date
	 */
	public void setFechaReparto(Date date) {
		fechaReparto = date;
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


	public ArrayList getMinutasOrden() {
		return minutasOrden;
	}


	public void setMinutasOrden(ArrayList minutasOrden) {
		this.minutasOrden = minutasOrden;
	}


	public String getNombreCirculoRegistral() {
		return nombreCirculoRegistral;
	}


	public void setNombreCirculoRegistral(String nombreCirculoRegistral) {
		this.nombreCirculoRegistral = nombreCirculoRegistral;
	}

}

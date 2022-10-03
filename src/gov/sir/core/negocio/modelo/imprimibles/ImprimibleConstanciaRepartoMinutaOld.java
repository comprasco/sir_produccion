package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.MinutaAccionNotarial;
import gov.sir.core.negocio.modelo.MinutaEntidadPublica;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.OtorganteNatural;
import java.text.NumberFormat;
import gov.sir.core.negocio.modelo.constantes.CMinuta;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLogger;
import gov.sir.core.negocio.modelo.imprimibles.util.AppletLoggerImp1;
import gov.sir.core.util.DateFormatUtil;
import java.awt.print.PageFormat;
import java.util.Iterator;
import java.util.List;

/**
 * @author ppabon
 * Clase que representa el imprimible de una constancia de reparto notarial.
 */
public class ImprimibleConstanciaRepartoMinutaOld extends ImprimibleBase {

	protected AppletLogger logger;

	/** Minuta */
	private Minuta minuta;
        private static final long serialVersionUID = 1L;
	/** Circulo asociado al imprimible */
	private Circulo circulo;
	
	/** Fecha de impresión del imprimible */
	private String fechaImpresion;
	
	/** Número del turno para la impreción de la radicación */
	private String radicacion = " ";	
	

	/**
	 * @param minuta
	 * @param circulo
	 * @param fechaImpresion 
	 */
	public ImprimibleConstanciaRepartoMinutaOld(Minuta minuta, Circulo circulo, String fechaImpresion, String radicacion,String tipoImprimible) {
		super(tipoImprimible);

		//SE CREA EL LOGGER PARA LOS MENSAJES EN LA EJECUCIÓN
		AppletLogger loggerImpl = AppletLoggerImp1.getAppletLogger();
		if (null == this.logger) {
			this.logger = loggerImpl;
		}
		
		this.minuta = minuta;
		this.circulo = circulo;
		this.fechaImpresion = fechaImpresion;
		this.radicacion= radicacion;

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

		System.out.println("Imprimir datos de la minuta.....");
		//DATOS DE LA MINUTA
		this.imprimirDatosMinuta();

		System.out.println("Imprimir datos de la superintendencia.....");
		//DATOS DE LA MINUTA
		this.imprimirDatosSuperintendencia();
		
		System.out.println("Imprimir datos de la notaria.....");
		//DATOS DE LA MINUTA
		this.imprimirDatosNotaria();		
		
	}
	
	/**
	 * Imprime los datos del reparto.
	 */
	private void imprimirDatosReparto() {
		//deja una linea en blanco.
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");		
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		if(minuta!=null && minuta.getRepartoNotarial()!=null && minuta.getRepartoNotarial().getIdRepartoNotarial()!=null){
			this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, "REPARTO NÚMERO : " + minuta.getRepartoNotarial().getIdRepartoNotarial(), false);	
		}else{
			this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, "REPARTO NÚMERO : ", false);	
		}
				
		
		this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, 300, "RADICACIÓN : " + this.radicacion);				

		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		
	}	
	

	/**
	 * Imprime los datos de la minuta.
	 */
	private void imprimirDatosMinuta() {

		this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, "CATEGORIA : " + minuta.getCategoria().getIdCategoria() + " - " + minuta.getCategoria().getNombre());
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");		
		
		if(minuta.getOficinaCategoriaAsignada()!=null && minuta.getOficinaCategoriaAsignada().getOficinaOrigen()!=null){
			OficinaOrigen oficinaOrigen = minuta.getOficinaCategoriaAsignada().getOficinaOrigen();
			this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, "NOTARIA ASIGNADA : " +  (oficinaOrigen.getNumero()!=null?oficinaOrigen.getNumero():"")  + " - " + (oficinaOrigen.getNumero()!=null?oficinaOrigen.getNombre():"") );		
		}
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "OFICINA REPARTO :", false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + minuta.getCirculoNotarial().getNombre());

		if(minuta.isNormal()){
			this.imprimirLinea(ImprimibleConstantes.PLANO, "TIPO REPARTO :", false);
			this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + CMinuta.ORDINARIO);
		}else{
			this.imprimirLinea(ImprimibleConstantes.PLANO, "TIPO REPARTO :", false);
			this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "");
		}		
		

	    String stringFechaReparto = DateFormatUtil.format("dd-MM-yyyy", minuta.getRepartoNotarial().getFechaCreacion());
	    this.imprimirLinea(ImprimibleConstantes.PLANO, "FECHA REPARTO :", false);
	    this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + stringFechaReparto);	    
		
		String stringHoraReparto = DateFormatUtil.format("K:mm:ss a", minuta.getRepartoNotarial().getFechaCreacion());
		this.imprimirLinea(ImprimibleConstantes.PLANO, "HORA REPARTO :", false);	
		this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + stringHoraReparto);

		this.imprimirLinea(ImprimibleConstantes.PLANO, "CONTRATO :" ,true);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "NÚMERO DE FOLIOS :" ,false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 150 ,"" + minuta.getNumeroFolios());		
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "CUANTÍA O VALOR :", false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + NumberFormat.getInstance().format(minuta.getValor()) );
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "NÚMERO DE UNIDADES :" ,false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, "NÚMERO DE UNIDADES :  " + minuta.getUnidades());
		
		if(minuta.getAccionesNotariales()!=null && minuta.getAccionesNotariales().size()>0){
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");
			this.imprimirLinea(ImprimibleConstantes.TITULO2, "CONTRATO(S) RELACIONADO(S)");
			
			List accionesNotariales = minuta.getAccionesNotariales();
			Iterator it = accionesNotariales.iterator();
			int a = 0;
			while(it.hasNext()){
				MinutaAccionNotarial man = (MinutaAccionNotarial)it.next();
				AccionNotarial an = man.getAccionNotarial();
				if(an!=null){
					this.imprimirLinea(ImprimibleConstantes.PLANO, "CONTRATO ("+(a+1)+") : " + an.getNombre());
				}
				a++;
			}
		}
		
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "CONTRATO :                       " + minuta.getAccionNotarial().getIdAccionNotarial() + " - " +  minuta.getAccionNotarial().getNombre());		
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "NÚMERO DE FOLIOS :       " + minuta.getNumeroFolios());		
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "CUANTÍA O VALOR :           " + NumberFormat.getInstance().format(minuta.getValor()) );
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "NÚMERO DE UNIDADES :  " + minuta.getUnidades());
		
		if(minuta.getEntidadesPublicas()!=null && minuta.getEntidadesPublicas().size()>0){
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");			
			this.imprimirLinea(ImprimibleConstantes.TITULO2, "ENTIDAD(ES) PÚBLICA(S) RELACIONADA(S)");
						
			List entidadesPublicas = minuta.getEntidadesPublicas();
			Iterator iterator = entidadesPublicas.iterator();
			int a = 0;
			while (iterator.hasNext()){
				MinutaEntidadPublica mep = (MinutaEntidadPublica)iterator.next();
				EntidadPublica ep = mep.getEntidadPublica();
				if(ep!=null){
					this.imprimirLinea(ImprimibleConstantes.PLANO, "ENTIDAD PÚBLICA ("+(a+1)+") : " + ep.getNombre());	
				}				
				a++;
			}
				
		}
		
		if(minuta.getOtorgantesNaturales()!=null && minuta.getOtorgantesNaturales().size()>0){
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");			
			this.imprimirLinea(ImprimibleConstantes.TITULO2, "OTORGANTES");
						
			List otorgantes = minuta.getOtorgantesNaturales();
			Iterator iterator = otorgantes.iterator();
			int a = 0;
			while (iterator.hasNext()){
				OtorganteNatural oto = (OtorganteNatural)iterator.next();
				if(oto!=null){
					this.imprimirLinea(ImprimibleConstantes.PLANO, "OTORGANTE ("+(a+1)+") : " + oto.getNombre());	
				}				
				a++;
			}
				
		}		
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "OBSERVACIONES");
		this.imprimirLinea(ImprimibleConstantes.PLANO, minuta.getComentario());		
		
		//IMPRIME LA FECHA DEL REPARTO
		String fechaImp = this.fechaImpresion;
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");		
		this.imprimirLinea(ImprimibleConstantes.PLANO, fechaImp);
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");		
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "");				
	}

	/**
	 * Imprime los datos de entrega por parte de la s.
	 */
	private void imprimirDatosSuperintendencia() {

		this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, "SUPERINTENDENCIA DE NOTARIADO Y REGISTRO");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "GRUPO DE ACTIVIDADES NOTARIALES SUPERINTENDENCIA DE NOTARIADO Y REGISTRO");

		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "ENTREGADO POR:__________________________________________________________");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "(NOMBRE COMPLETO)     GRUPO DE REPARTO NOTARIAL");


		this.imprimirLinea(ImprimibleConstantes.PLANO, "",false);
		this.imprimirLinea(ImprimibleConstantes.PEQUE, 405, "HUELLA DACTILAR");		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "CARGO:___________________________________________________________________");
		

				
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");				
		this.imprimirLinea(ImprimibleConstantes.PLANO, "__________________________________________________________________________");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "                                               FIRMA COMPLETA");

		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "CC._______________________________________________________________________");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		
		this.imprimirCuadro(400, (this.getI()-130), 72, 82);
		
		
	}
	
	
	/**
	 * Imprime el cuerpo del recibo dependiendo del tipo de solicitud del mismo.
	 */
	private void imprimirDatosNotaria() {
		this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, "NOTARIA");
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "RECIBIDO POR:____________________________________________________________");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "(NOMBRE COMPLETO)");


		this.imprimirLinea(ImprimibleConstantes.PLANO, "", false);
		this.imprimirLinea(ImprimibleConstantes.PEQUE, 405, "HUELLA DACTILAR");		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "CARGO:__________________________________________________________________");
		

		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "_________________________________________________________________________");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "                                               FIRMA COMPLETA");

		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "CC._______________________________________________________________________");
		
		this.imprimirCuadro(400, (this.getI()-95), 72, 82);
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "Fecha y Hora de Entrega: ______________________");
		
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

}

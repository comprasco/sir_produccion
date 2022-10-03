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
import gov.sir.core.negocio.modelo.constantes.CTipoEncabezado;
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
public class ImprimibleConstanciaRepartoMinuta extends ImprimibleBase {

	protected AppletLogger logger;
        private static final long serialVersionUID = 1L;
	/** Minuta */
	private Minuta minuta;

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
	public ImprimibleConstanciaRepartoMinuta(Minuta minuta, Circulo circulo, String fechaImpresion, String radicacion,String tipoImprimible) {
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

		System.out.println("Imprimir datos del encabezado.....");
		//ENCABEZADO DEL IMPRIMIBLE
		this.imprimirEncabezadoCaratula();
		
		System.out.println("Imprimir datos del reparto.....");
		//ENCABEZADO DEL IMPRIMIBLE
		this.imprimirDatosReparto();

		System.out.println("Imprimir datos de la minuta.....");
		//DATOS DE LA MINUTA
		this.imprimirDatosMinuta();

		System.out.println("Imprimir datos de la superintendencia.....");
		//DATOS DE LA MINUTA
		//this.imprimirDatosSuperintendencia();
		
		System.out.println("Imprimir datos de la notaria.....");
		//DATOS DE LA MINUTA
		//this.imprimirDatosNotaria();		
		
	}
	
	/**
	 * Imprime los datos del reparto.
	 */
	private void imprimirEncabezadoCaratula() {
		
		//deja una linea en blanco.
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		
		// Se debe imprimir encabezado
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
	}
	
	/**
	 * Imprime los datos del reparto.
	 */
	private void imprimirDatosReparto() {

		this.imprimirLinea(ImprimibleConstantes.PLANO, "");	
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");	
		
		if(minuta!=null && minuta.getRepartoNotarial()!=null && minuta.getRepartoNotarial().getIdRepartoNotarial()!=null){
			this.imprimirLinea(ImprimibleConstantes.PLANO, 70, "REPARTO NÚMERO : " + minuta.getRepartoNotarial().getIdRepartoNotarial(), false);	
		}else{
			this.imprimirLinea(ImprimibleConstantes.PLANO, 50,  "REPARTO NÚMERO : ", false);	
		}

		String stringFechaReparto = DateFormatUtil.format("dd-MM-yyyy", minuta.getRepartoNotarial().getFechaCreacion());
	    this.imprimirLinea(ImprimibleConstantes.PLANO, 230, "FECHA REPARTO :", false);
	    this.imprimirLinea(ImprimibleConstantes.PLANO, 305, "" + stringFechaReparto, false);
	    
	    if(minuta.isNormal()){
			this.imprimirLinea(ImprimibleConstantes.PLANO, 355, "TIPO REPARTO :", false);
			this.imprimirLinea(ImprimibleConstantes.PLANO, 430, "" + CMinuta.ORDINARIO);
		}else{
			this.imprimirLinea(ImprimibleConstantes.PLANO, 355, "TIPO REPARTO :", false);
			this.imprimirLinea(ImprimibleConstantes.PLANO, 430, "");
		}
	    
	    // IMPRIME LA FECHA DEL REPARTO
		String fechaImp = this.fechaImpresion;
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");		
		this.imprimirLinea(ImprimibleConstantes.PLANO, 70, fechaImp);
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");	
	    
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		
	}	
	

	/**
	 * Imprime los datos de la minuta.
	 */
	private void imprimirDatosMinuta() {

		String municipio = "";
                /* JAlcazar caso Mantis 0009055: Acta - Requerimiento No 019_151_Caratula - Reparto Notarial
                 * Se coloca en la la Caratula de Reparto Notarial el municicpio al cual corresponde la minuta.
                 */
		if (this.minuta!=null && this.minuta.getOficinaCategoriaAsignada()!=null &&
                                this.minuta.getOficinaCategoriaAsignada().getOficinaOrigen()!=null &&
				this.minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda()!=null &&
				this.minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda().getMunicipio()!=null &&
				this.minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda().getMunicipio().getNombre()!=null){
			municipio = this.minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda().getMunicipio().getIdMunicipio() + " " + this.minuta.getOficinaCategoriaAsignada().getOficinaOrigen().getVereda().getMunicipio().getNombre();
		}
		
		this.imprimirLinea(ImprimibleConstantes.TITULO2, 200, "MUNICIPIO : " + municipio);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");	
		
		this.imprimirLinea(ImprimibleConstantes.TITULO2, 200, "RADICACIÓN : " + this.radicacion);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");	
		
		this.imprimirLinea(ImprimibleConstantes.TITULO2, 200, "ANEXOS    : __________________________________________");
		this.imprimirLinea(ImprimibleConstantes.PLANO, 220,   "            __________________________________________");
		this.imprimirLinea(ImprimibleConstantes.PLANO, 220,   "            __________________________________________");
		
		
		if(minuta.getAccionesNotariales()!=null && minuta.getAccionesNotariales().size()>0){
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");
			this.imprimirLinea(ImprimibleConstantes.TITULO2,200, "CONTRATO(S) RELACIONADO(S)");
			
			List accionesNotariales = minuta.getAccionesNotariales();
			Iterator it = accionesNotariales.iterator();
			int a = 0;
			while(it.hasNext()){
				MinutaAccionNotarial man = (MinutaAccionNotarial)it.next();
				AccionNotarial an = man.getAccionNotarial();
				if(an!=null){
					this.imprimirLinea(ImprimibleConstantes.PLANO, 200, "CONTRATO ("+(a+1)+") : " + an.getNombre());
				}
				a++;
			}
		}
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");	
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, 200, "CUANTÍA O VALOR :", false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 280, "" + NumberFormat.getInstance().format(minuta.getValor()) );
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");	
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, 200, "NÚMERO DE UNIDADES :  " + minuta.getUnidades());
			
		if(minuta.getEntidadesPublicas()!=null && minuta.getEntidadesPublicas().size()>0){
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");			
			this.imprimirLinea(ImprimibleConstantes.TITULO2, 200, "ENTIDAD(ES) PÚBLICA(S) RELACIONADA(S)");
						
			List entidadesPublicas = minuta.getEntidadesPublicas();
			Iterator iterator = entidadesPublicas.iterator();
			int a = 0;
			while (iterator.hasNext()){
				MinutaEntidadPublica mep = (MinutaEntidadPublica)iterator.next();
				EntidadPublica ep = mep.getEntidadPublica();
				if(ep!=null){
					this.imprimirLinea(ImprimibleConstantes.PLANO, 200, "ENTIDAD PÚBLICA ("+(a+1)+") : " + ep.getNombre());	
				}				
				a++;
			}
				
		}
		
		if(minuta.getOtorgantesNaturales()!=null && minuta.getOtorgantesNaturales().size()>0){
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");			
			this.imprimirLinea(ImprimibleConstantes.TITULO2, 200,  "OTORGANTES");
						
			List otorgantes = minuta.getOtorgantesNaturales();
			Iterator iterator = otorgantes.iterator();
			int a = 0;
			while (iterator.hasNext()){
				OtorganteNatural oto = (OtorganteNatural)iterator.next();
				if(oto!=null){
					this.imprimirLinea(ImprimibleConstantes.PLANO, 200, "OTORGANTE ("+(a+1)+") : " + oto.getNombre());	
				}				
				a++;
			}
				
		}
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");	
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, 200,  "CATEGORIA : " + minuta.getCategoria().getIdCategoria() + " - " + minuta.getCategoria().getNombre());
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");		
		
		if(minuta.getOficinaCategoriaAsignada()!=null && minuta.getOficinaCategoriaAsignada().getOficinaOrigen()!=null){
			OficinaOrigen oficinaOrigen = minuta.getOficinaCategoriaAsignada().getOficinaOrigen();
			this.imprimirLinea(ImprimibleConstantes.PLANO, 200, "NOTARIA ASIGNADA : " +  (oficinaOrigen.getNumero()!=null?oficinaOrigen.getNumero():"")  + " - " + (oficinaOrigen.getNumero()!=null?oficinaOrigen.getNombre():"") );		
		}
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");	
		
		/*this.imprimirLinea(ImprimibleConstantes.PLANO, "OFICINA REPARTO :", false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 150, "" + minuta.getCirculoNotarial().getNombre());

		this.imprimirLinea(ImprimibleConstantes.PLANO, "");	
				
		this.imprimirLinea(ImprimibleConstantes.PLANO, "CONTRATO :" ,true);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");	
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "NÚMERO DE FOLIOS :" ,false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 150 ,"" + minuta.getNumeroFolios());		*/
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, 200, "Oficina de Registro",false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 300, ": ___________________________________");
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, 200, "Recibido por",false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 300, ": ___________________________________");
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, 200, "Fecha y Hora de Entrega",false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, 300, ": ___________________________________");
		
		
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "CONTRATO :                       " + minuta.getAccionNotarial().getIdAccionNotarial() + " - " +  minuta.getAccionNotarial().getNombre());		
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "NÚMERO DE FOLIOS :       " + minuta.getNumeroFolios());		
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "CUANTÍA O VALOR :           " + NumberFormat.getInstance().format(minuta.getValor()) );
		//this.imprimirLinea(ImprimibleConstantes.PLANO, "NÚMERO DE UNIDADES :  " + minuta.getUnidades());
		
				
		
		/*this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "OBSERVACIONES");
		this.imprimirLinea(ImprimibleConstantes.PLANO, minuta.getComentario());	
		*/	
		
			
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

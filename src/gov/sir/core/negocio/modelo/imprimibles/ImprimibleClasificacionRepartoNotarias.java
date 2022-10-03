package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.constantes.CMinuta;
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
 * Clase que representa la clasificación de las próximas notarias a repartir.
 */
public class ImprimibleClasificacionRepartoNotarias extends ImprimibleBase {

	protected AppletLogger logger;
        private static final long serialVersionUID = 1L;
	/** Minuta */
	private Minuta minuta;
	
	/** Circulo asociado al imprimible */
	private Circulo circulo;
	
	/** Fecha de impresión del imprimible */
	private Date fechaReparto;
	
	/** Clasificación de las próximas notarias a repartir por categoria.*/
	private Hashtable clasificacion;

	private String fechaImpresion;
	
	

	/**
	 * @param minuta
	 * @param circulo
	 * @param fechaImpresion 
	 */
	public ImprimibleClasificacionRepartoNotarias(Minuta minuta, Circulo circulo, Date fechaReparto, Hashtable clasificacion, String fechaImpresion,String tipoImprimible) {
		super(tipoImprimible);

		//SE CREA EL LOGGER PARA LOS MENSAJES EN LA EJECUCIÓN
		AppletLogger loggerImpl = AppletLoggerImp1.getAppletLogger();
		if (null == this.logger) {
			this.logger = loggerImpl;
		}
		
		this.minuta = minuta;
		this.circulo = circulo;
		this.fechaReparto = fechaReparto;
		this.clasificacion = clasificacion;
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
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");		
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		if(minuta!=null && minuta.getRepartoNotarial()!=null && minuta.getRepartoNotarial().getIdRepartoNotarial()!=null){
			this.imprimirLinea(ImprimibleConstantes.TITULO2, "Reparto número: " + minuta.getRepartoNotarial().getIdRepartoNotarial(), false);	
		}else{
			this.imprimirLinea(ImprimibleConstantes.TITULO2, "Reparto número : ", false);	
		}
				
		this.imprimirLinea(ImprimibleConstantes.TITULO2, 200, "Fecha del reparto : " + (fechaReparto!=null  ?(this.formatear(fechaReparto,"dd/MM/yyyy")):""), false);
		
		if(this.minuta.isNormal()){
			this.imprimirLinea(ImprimibleConstantes.TITULO2, 400, "Tipo de reparto : " + CMinuta.ORDINARIO);	
		}else{
			this.imprimirLinea(ImprimibleConstantes.TITULO2, 400, "Tipo de reparto : " + CMinuta.EXTRAORDINARIO);			
		}
		
		String fechaImp = this.fechaImpresion;
		this.imprimirLinea(ImprimibleConstantes.PLANO, fechaImp);		
				
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
		this.imprimirLinea(ImprimibleConstantes.PLANO, " ");
	}	
	

	/**
	 * Imprime los datos de la minuta.
	 */
	private void imprimirCuerpoActaReparto() {

		List notarias = new ArrayList();
		Enumeration en = clasificacion.keys();
		
		int x = 0;		

		if(en.hasMoreElements()){
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");
			
			String lineaEncabezado1 = "NOTARIAS HABILES PARA CONCURSAR EN EL PRÓXIMO REPARTO";
			String lineaEncabezado2 = "MUNICIPIO DE:";
			
			this.imprimirLinea(ImprimibleConstantes.TITULO1,  "",false);
			this.imprimirLinea(ImprimibleConstantes.TITULO1, 150, lineaEncabezado1,true);
			
			this.imprimirLinea(ImprimibleConstantes.TITULO1,  "",false);
			this.imprimirLinea(ImprimibleConstantes.TITULO1, 150, lineaEncabezado2,true);			
			
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");			

			this.imprimirLinea(ImprimibleConstantes.TITULO1,  "CATEGORIA",false);
			this.imprimirLinea(ImprimibleConstantes.TITULO1, 150, "NOTARIAS",true);			

			this.imprimirLinea(ImprimibleConstantes.TITULO1,  "=========",false);
			this.imprimirLinea(ImprimibleConstantes.TITULO1, 150, "=========",true);			
			
		}

		while(en.hasMoreElements()){
			Categoria key = (Categoria)en.nextElement();
			notarias = (List)clasificacion.get(key);
			
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");			
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");
			this.imprimirLinea(ImprimibleConstantes.TITULO1, key.getNombre(), false);
			
			x = 150;
			
			Iterator it = notarias.iterator();
			while (it.hasNext()){
				OficinaOrigen of = (OficinaOrigen)it.next();

	
				if(of!=null){
					if(x >= 500){
						this.imprimirLinea(ImprimibleConstantes.TITULO1, x, of.getNumero(),true);
						x = 150;	
					}else{
						this.imprimirLinea(ImprimibleConstantes.TITULO1, x, of.getNumero(),false);
						x += 50;
					}
						
				}else{
					this.imprimirLinea(ImprimibleConstantes.PLANO, "OF NULA");
				}
				
				
				
			}
			
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
	


			
		
	
}

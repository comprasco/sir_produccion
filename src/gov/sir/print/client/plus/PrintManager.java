/*
 * Created on 26-ene-2005
 *
 */
package gov.sir.print.client.plus;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoImpresora;
import gov.sir.core.negocio.modelo.ImprimiblePdf;
import gov.sir.core.negocio.modelo.TipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.util.PdfCreator;
import gov.sir.print.client.ImpNoConfiguradaException;
import gov.sir.print.client.Log;
import gov.sir.print.client.Processor;
import gov.sir.print.client.Receiver;
import gov.sir.print.common.Bundle;
import gov.sir.print.common.Imprimible;

import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.print.PrintService;

/**
 * Determina las impresoras que tiene la oficina
 * 
 * @author jfrias
 * 
 */
public class PrintManager extends Processor {

	private List reglas;

	private Map configuracion;

	private Receiver receiver;
	
	private Log logger;
	
	private Map parametros;

	/**
	 * @return Returns the configuracion.
	 */
	public Map getConfiguracion() {
		return configuracion;
	}

	/**
	 * @param configuracion
	 *            The configuracion to set.
	 */
	public void setConfiguracion(Map configuracion) {
		Iterator itTipo = configuracion.keySet().iterator();
		List elimImpresoras = new ArrayList();
		
		logger = new Log(PrintManager.class);
		logger.print("PrintManager Constructor");
		
		while (itTipo.hasNext()) {
			TipoImprimible tipoImp = (TipoImprimible) itTipo.next();
			List impresoras = (List) configuracion.get(tipoImp);
			Iterator itImpr = impresoras.iterator();
			logger.print("***CONFIGURANDO TIPO IMPRIMIBLE:"
					+ tipoImp.getIdTipoImprimible());
			while (itImpr.hasNext()) {
				CirculoImpresora circImp = (CirculoImpresora) itImpr.next();
				if (!estaImpresora(circImp.getIdImpresora())) {
					logger.print("SE VA A ELIMINAR LA IMPRESORA:"
							+ circImp.getIdImpresora());
					elimImpresoras.add(circImp);
				}
			}
			logger.print("IMPRESORAS ANTES DE ELIMINACION:"
					+ impresoras.size());
			impresoras.removeAll(elimImpresoras);
			logger.print("IMPRESORAS DESPUES DE ELIMINACION:"
					+ impresoras.size());

		}

		this.configuracion = configuracion;
	}

	/**
	 * @param idImpresora
	 * @return
	 */
	private boolean estaImpresora(String idImpresora) {
		PrintService[] servicios = PrinterJob.lookupPrintServices();
		for (int i = 0; i < servicios.length; i++) {
			if (idImpresora.equals(servicios[i].getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 */
	public PrintManager() {
		logger = new Log(PrintManager.class);
	}

	public List getReglas() {
		return reglas;
	}

	public void setReglas(List rules) {
		this.reglas = rules;
	}

	public void procesar(ByteArrayOutputStream baos) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		Bundle b = Bundle.decode(bais);
		procesar(b);
	}

	protected void procesar(Bundle b) throws Exception {

		PrintService servicio = null;
		
		if(b.getDatosComprimidos()!=null){
			
			logger.print("METODO	ZIP");
			
			//Se obtiene un nombre único para colocarle al archivo
			//que estara compuesto por los últimos cinco digitos del millisegundo
			//actual + un número aleatorio entre 0 y 100000
			long time = System.currentTimeMillis();
			String timeString = String.valueOf(time);
			int temp = (int)Math.ceil(Math.random()*100000);
			String nombreArchivo = timeString.substring((timeString.length()- 6), timeString.length()) + temp;        		

			Imprimible imprimible = getImprimibleFromBytes(b.getDatosComprimidos(), nombreArchivo);
			b.setImprimible(imprimible);
			
			logger.print("SE DESCOMPRIMIO EL ZIP");
		}
		
		if (b.getNombreImpresora() != null
				&& !b.getNombreImpresora().equals("SIN_SELECCIONAR")) {
			logger.print("LA IMPRESORA FUE SELECCIONADA");
			servicio = obtenerImpresora(b.getNombreImpresora());
		} else {
			Iterator itReglas = configuracion.keySet().iterator();
			TipoImprimible tipoImp = null;
			while (itReglas.hasNext()) {
				TipoImprimible temp = (TipoImprimible) itReglas.next();
				if (temp.getIdTipoImprimible().equals(
						b.getImprimible().getTipoImprimible())) {
					tipoImp = temp;
					break;
				}
			}
			if (tipoImp == null) {
				Iterator itReglas2 = configuracion.keySet().iterator();
				while (itReglas2.hasNext()) {
					TipoImprimible temp = (TipoImprimible) itReglas2.next();
					if (temp.getIdTipoImprimible().equals(
							CTipoImprimible.OTROS_IMPRIMIBLES)) {
						tipoImp = temp;
						System.out
								.println("***NO SE ENCONTRO EL TIPO DE IMPRIMIBLE, SE IMPRIME POR OTROS IMPRIMIBLES");
						break;
					}
				}
			}
			List circImpresoras = (List) configuracion.get(tipoImp);
			if (circImpresoras != null && !circImpresoras.isEmpty()) {
				Iterator itCircImp = circImpresoras.iterator();
				CirculoImpresora predet = null;
				while (itCircImp.hasNext()) {
					CirculoImpresora circ = (CirculoImpresora) itCircImp.next();
					if (circ.isPredeterminada()) {
						predet = circ;
						break;
					}
				}

				if (predet != null) {
					System.out
							.println("SE VA A IMPRIMIR POR LA PREDETERMINADA");
					servicio = obtenerImpresora(predet.getIdImpresora());
				} else {
					Random r = new Random();
					int index = r.nextInt(circImpresoras.size());
					CirculoImpresora cualquiera = (CirculoImpresora) circImpresoras
							.get(index);
					System.out
							.println("SE VA A IMPRIMIR POR CUALQUIER IMPRESORA");
					servicio = obtenerImpresora(cualquiera.getIdImpresora());
				}
			} else {
				throw new ImpNoConfiguradaException("No hay impresoras configuradas para el tipo de imprimible: "
						+ tipoImp.getIdTipoImprimible());
				/*throw new Exception(
						"No hay impresoras configuradas para el tipo de imprimible: "
								+ tipoImp.getIdTipoImprimible());*/
			}
		}
		if (servicio != null) {
			logger.print("VA A IMPRIMIR POR " + servicio.getName());
			int numCopias = b.getNumeroCopias();

			int a = 0;
			for (a = 0; a < numCopias; a++) {
				if (b.getImprimible() != null) {
					Imprimible tempImprimible = (Imprimible) copy(b.getImprimible());
					tempImprimible.imprimir(servicio);
					tempImprimible = null;
					logger.print("METODO	NORMAL");
				} else if (b.getImprimiblePdf()!=null){
					ImprimiblePdf impPdf = b.getImprimiblePdf();
	        		PdfCreator pdfCreator = new PdfCreator();
	        		pdfCreator.generarProcesoImpresion(impPdf.getDatosImprimible(),servicio);
	        		logger.print("METODO	PDF");
				}else if (b.getDatosComprimidos()!=null){
					//Se obtiene un nombre único para colocarle al archivo
					//que estara compuesto por los últimos cinco digitos del millisegundo
					//actual + un número aleatorio entre 0 y 100000
					long time = System.currentTimeMillis();
					String timeString = String.valueOf(time);
					int temp = (int)Math.ceil(Math.random()*100000);
					String nombreArchivo = timeString.substring((timeString.length()- 6), timeString.length()) + temp;
					
					Imprimible imprimible = getImprimibleFromBytes(b.getDatosComprimidos(), nombreArchivo);
					Imprimible tempImprimible = (Imprimible) copy(imprimible);					
					tempImprimible.imprimir(servicio);
					tempImprimible = null;		
					logger.print("METODO	ZIP");
				}
			}
		} else {
			logger.print("NO SE ENCONTRO SERVICIO DE IMPRESION");
		}
	}

	/**
	 * @param nombreImpresora
	 * @return
	 */
	private PrintService obtenerImpresora(String nombreImpresora) {
		PrintService[] servicios = PrinterJob.lookupPrintServices();
		for (int i = 0; i < servicios.length; i++) {
			if (nombreImpresora.equals(servicios[i].getName())) {
				return servicios[i];
			}
		}
		return null;
	}

	/**
	 * @param selectedItem
	 * @param impresora
	 */
	public void eliminarImpresoraRegla(String regla, String impresora) {
		Iterator itReglas = reglas.iterator();
		boolean eliminado = false;
		while (itReglas.hasNext() && !eliminado) {
			TipoImprimible tipoImp = (TipoImprimible) itReglas.next();
			if (tipoImp.getNombre().equals(regla)) {
				List impresoras = (List) configuracion.get(tipoImp);
				Iterator itImpresoras = impresoras.iterator();
				while (itImpresoras.hasNext() && !eliminado) {
					CirculoImpresora circImp = (CirculoImpresora) itImpresoras
							.next();
					if (circImp.getIdImpresora().equals(impresora)) {
						impresoras.remove(circImp);
						eliminado = true;
					}
				}
			}
		}
	}

	/**
	 * @param selectedItem
	 * @param impresora
	 */
	public void agregarImpresoraRegla(String regla, String impresora,
			String circulo) {
		Iterator itReglas = reglas.iterator();
		while (itReglas.hasNext()) {
			TipoImprimible tipoImp = (TipoImprimible) itReglas.next();
			if (tipoImp.getNombre().equals(regla)) {
				List impresoras = (List) configuracion.get(tipoImp);
				if (!estaImpresora(impresora, impresoras)) {
					CirculoImpresora nueva = new CirculoImpresora();
					Circulo circ = new Circulo();
					circ.setIdCirculo(circulo);
					nueva.setCirculo(circ);
					nueva.setIdCirculo(circulo);
					nueva.setIdImpresora(impresora);
					nueva.setIdTipoImprimible(tipoImp.getIdTipoImprimible());
					impresoras.add(nueva);
				}
			}
		}
	}

	/**
	 * @param impresora
	 * @param impresoras
	 * @return
	 */
	private boolean estaImpresora(String impresora, List impresoras) {
		Iterator itImpresoras = impresoras.iterator();
		while (itImpresoras.hasNext()) {
			CirculoImpresora circImp = (CirculoImpresora) itImpresoras.next();
			if (circImp.getIdImpresora().equals(impresora)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param tipoImprimible
	 * @param predeterminada
	 */
	public void setImpresoraPredeterminada(String tipoImprimible,
			String predeterminada) {
		Iterator itImpr = configuracion.keySet().iterator();
		while (itImpr.hasNext()) {
			TipoImprimible impr = (TipoImprimible) itImpr.next();
			if (impr.getNombre().equals(tipoImprimible)) {

				Iterator itImpre = ((List) configuracion.get(impr)).iterator();
				while (itImpre.hasNext()) {
					CirculoImpresora circuloImp = (CirculoImpresora) itImpre
							.next();
					if (circuloImp.getIdImpresora().equals(predeterminada)) {
						circuloImp.setPredeterminada(true);
					} else {
						circuloImp.setPredeterminada(false);
					}
				}
				break;
			}
		}
	}

	public Map getParametros() {
		return parametros;
	}

	public void setParametros(Map parametros) {
		this.parametros = parametros;
	}
}
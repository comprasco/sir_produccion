package gov.sir.core.negocio.modelo.imprimibles;

import java.awt.print.PageFormat;
import java.util.Vector;

import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author gvillal
 * Clase que representa el Imprimible de un Oficio de Pertenencia.
 */
public class ImprimibleOficioPertenencia extends ImprimibleBase 
{
	/**Texto del Oficio de Pertenencia.**/
	private String texto;
	private byte[] pixelesImagenFirmaRegistrador = null;
        private String nombreRegistrador = null;
	private String cargoRegistrador = null;
        private static final long serialVersionUID = 1L;
	/**
	 * Constructor de la clase
	 */
	public ImprimibleOficioPertenencia(String texto,String tipoImprimible) 
	{
		super(tipoImprimible);
		this.texto = texto;

	}

	  /**
	   *Genera el vector de hojas imprimibles con toda la información que se va a imprimir. 
	   */
	  public void generate(PageFormat pageFormat) 
	  {    	
		   super.generate(pageFormat);
		   this.imprimirEncabezado();
		   
		   Vector lineas = this.getLineas(this.texto);
		   for(int i=0; i<lineas.size();i++)
		   {
		   	  String linea = (String)lineas.get(i);
			  this.imprimirLinea(ImprimibleConstantes.PLANO,linea);
		   }
                   this.imprimirFirma();
	  }	
          /* (non-Javadoc)
	 * @see gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase#imprimirFirma()
	 */
	protected void imprimirFirma() {
		//BufferedImage imagenFirmaRegistrador
		BufferedImage imagen = null;

		try {
			if (this.pixelesImagenFirmaRegistrador != null) {
				imagen = UIUtils.loadImage(this.pixelesImagenFirmaRegistrador);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (imagen == null) {
			super.imprimirFirma();
		} else {
			this.imprimirFirmaDigital(imagen);
		}
	}
        /**
	 * Método que imprime la firma al final del documento
	 * @param imagenFirmaRegistrador
	 */
	private void imprimirFirmaDigital(BufferedImage imagenFirmaRegistrador) {
		int altura = imagenFirmaRegistrador.getHeight();

		int h = imagenFirmaRegistrador.getHeight();
		h = h + (ImprimibleConstantes.SEPARACION_LINE * 5);

		int hActual = this.getI();

		if((hActual + h) > ImprimibleConstantes.MAXIMO_VERTICAL){
			this.goPageEnd();
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		}

		this.imprimirLinea(6, ImprimibleConstantes.SEPARADOR3_PEQ);
		this.imprimirGrafico(imagenFirmaRegistrador);

		int i = this.getI();
		i += altura;
		this.setI(i);
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
                String linea = "";
                linea = "REGISTRADOR DE INSTRUMENTOS PUBLICOS";
		this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
		this.imprimirLinea(6, ImprimibleConstantes.SEPARADOR3_PEQ);
	}
	
	/**
	 * Imprime el encabezado del oficio de pertenencia.
	 */	
	protected void imprimirEncabezado()
	{
		String linea; 
		linea = StringFormat.getCentrada("CERTIFICACION PARA PROCESO DE PERTENENCIA",ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,ImprimibleConstantes.LONG_LOGO);
		this.imprimirLinea(ImprimibleConstantes.TITULO1,linea);     
                    
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
	}

        public byte[] getPixelesImagenFirmaRegistrador() {
            return pixelesImagenFirmaRegistrador;
        }

        public void setPixelesImagenFirmaRegistrador(byte[] pixelesImagenFirmaRegistrador) {
            this.pixelesImagenFirmaRegistrador = pixelesImagenFirmaRegistrador;
        }

        public String getNombreRegistrador() {
            return nombreRegistrador;
        }

        public void setNombreRegistrador(String nombreRegistrador) {
            this.nombreRegistrador = nombreRegistrador;
        }

        public String getCargoRegistrador() {
            return cargoRegistrador;
        }

        public void setCargoRegistrador(String cargoRegistrador) {
            this.cargoRegistrador = cargoRegistrador;
        }

	/**
	 * Funcion que recibe una cadena de texto que contiene caracteres especiales de fin de linea '\r' y '\n'
	 * indicando que se debe generar una nueva linea en un texto.
	 * @param line Linea con todo el texto.
	 * @return un vector de Strings con el mismo texto original, pero cada elemento del vector de respuesta
	 * representa un renglón en el texto.
	 */	private Vector getLineas(String line)
	 {
		
		String cad = "";
		for (int i=0; i<line.length();i++)
		{
			if (line.charAt(i)!='\r')
			  cad+= line.charAt(i);
		}
		
		String cadenas[] = cad.split("\n");
		
		int tam = cadenas.length;
		String cad2 = "";
		   
		Vector vectorCad= new Vector();   
		for (int i = 0; i < tam; i++) 
		{
			vectorCad.add(cadenas[i]);
			
	
		}
		
		return vectorCad;
	 }	
	
	

}

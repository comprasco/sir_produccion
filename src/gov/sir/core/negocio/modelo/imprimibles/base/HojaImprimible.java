package gov.sir.core.negocio.modelo.imprimibles.base;

import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import java.io.InputStream;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import java.io.IOException;


import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;


/**
 * @author gvillal
 * Clase que representa una hoja de un imprimible.
 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
 */
public class HojaImprimible implements Printable {
    /**Margen izquierdo.**/
    private static final int margenIzq = 36;
    private static final long serialVersionUID = 1L;
    /**Margen superior.**/
    private static final int margenSup = 50;

    /** Constante que define el separador del archivo */
    private static final String SEP_ARCHIVO = "/";

    /**Vector de las lineas. **/
    private Vector lineas;

    /**Vector de los graficos. **/
    private Vector graficos;

	/**Vector de los cuadros. **/
	private Vector cuadros;

    /** Indica si la margen se imprime */
    private boolean imprMargen = true;

    /** Indica si la marca de agua se imprime */
    private boolean printWatermarkEnabled = false;

    /**Constructor de la clase.**/
    public HojaImprimible() {
        lineas = new Vector();
        graficos = new Vector();
        cuadros = new Vector();
    }

    /**
     * Imprime una linea.
     * @param estilo
     * @param tam
     * @param texto
     * @param x
     * @param y
     */
    public void imprimirLinea(int estilo, int tam, String texto, int x, int y) {
        TextLine linea = new TextLine();
        linea.setFormato(estilo);
        linea.setTam(tam);
        String textoFiltrado = FiltrarCaracteres(texto);
        linea.setTexto(textoFiltrado);
        linea.setX(x);
        linea.setY(y);

        lineas.add(linea);
    }

    /**
     * Imprime una linea.
     * @param estilo
     * @param tam
     * @param texto
     * @param x
     * @param y
     */
    public void imprimirLinea(int estilo, int tam, String texto, int x, int y, int ancho) {
        TextLine linea = new TextLine();
        linea.setFormato(estilo);
        linea.setTam(tam);
        String textoFiltrado = FiltrarCaracteres(texto);
        linea.setTexto(textoFiltrado);
        linea.setX(x);
        linea.setY(y);
        linea.setAncho(ancho);

        lineas.add(linea);
    }

    private String FiltrarCaracteres(String cadena){

		String tmp = "";

		if(cadena == null || cadena.equals(""))
			return tmp;

    	char car;
    	for (int i=0; i<cadena.length(); i++){
			car=cadena.charAt(i);
    		if (car=='\n')
    		  car=' ';
    		else
			if (car=='\t')
				  car=' ';
			else
			if (car=='\r')
				  car=' ';

    		tmp+= car;
    	}

    	char c = 0;
		char[] aux = new char[]{c};
    	String toReplace = new String(aux);
    	tmp = tmp.replaceAll(toReplace, "");

    	return tmp;
    }


    /**
     * Imprime un grafico
     * @param x
     * @param y
     * @param buf
     */
    public void imprimirGrafico(int x, int y, BufferedImage buf) {
        Grafico grafico = new Grafico();
        grafico.setX(x);
        grafico.setY(y);
        grafico.setBuf(buf);

        graficos.add(grafico);
    }

	/**
	 * Imprime un cuadro en la hoja.
	 * @param graphics2D
	 * @param page
	 */
	public void imprimirCuadro( int x, int y, int w, int h) {
		GraficoCuadro  graficoCuadro = new GraficoCuadro();
		graficoCuadro.setX(x);
		graficoCuadro.setY(y);
		graficoCuadro.setW(w);
		graficoCuadro.setH(h);

		cuadros.add(graficoCuadro);
	}

	private boolean imprimirLogoEnabled = true;

    public boolean isImprimirLogoEnabled() {
		return imprimirLogoEnabled;
	}

	public void setImprimirLogoEnabled(boolean imprimirLogoEnabled) {
		this.imprimirLogoEnabled = imprimirLogoEnabled;
	}

	/* (non-Javadoc)
     * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
     */
    public int print(Graphics graphics, PageFormat pageFormat, int index)
        throws PrinterException {
        Graphics2D graphics2D = (Graphics2D) graphics;
        boolean textoEmpezo = false;

        if (this.imprMargen) {
            this.imprimirMargen(graphics2D, pageFormat);
        }

		// impresion opcional de marca de agua
		if(isPrintWatermarkEnabled())
		{
			process_PrintWatermark (graphics2D, pageFormat);
		}

		// impresion opcional del logo
		if( isImprimirLogoEnabled() ) {
			imprimirLogo( graphics2D, pageFormat );
		}

        int tipoFuente;
        int tamFuente;

        TextLine linea;
		Iterator iLineas = lineas.iterator();

		while(iLineas.hasNext()) {
            linea = (TextLine)iLineas.next();
			if(linea == null || linea.getTexto() == null)
				continue;

            //graphics2D.setFont(new Font("SansSerif", linea.getFormato(), linea.getTam()));
            graphics2D.setFont(new Font(null, linea.getFormato(), linea.getTam()));


            String texto = linea.getTexto();

            if (texto.indexOf("<b>")!=-1){
            	String[] subTextos = texto.split("<b>");
            	int veces = subTextos.length;
            	int offset = linea.getX();
            	for(int i=0;i<veces;i++){
            		if(subTextos[i]!=null && !subTextos[i].equals("")){
            			if(i%2==0){
                			graphics2D.setFont(new Font(null, 0, linea.getTam()));
                		}else{
                			graphics2D.setFont(new Font(null, 1, linea.getTam()));
                		}

                		FontRenderContext frc = graphics2D.getFontRenderContext();
                		TextLayout layout = new TextLayout(subTextos[i],graphics2D.getFont(),frc);

                		graphics2D.drawString(subTextos[i], offset, linea.getY());
                		offset = offset + (int)layout.getAdvance();
            		}
            	}

            }else{
            	graphics2D.setFont(new Font(null, linea.getFormato(), linea.getTam()));
            	if (linea.getAncho() > 0)
                {
                	int anchoLinea = graphics2D.getFontMetrics().stringWidth(texto);
                	while (anchoLinea > linea.getAncho())
                	{
                		texto = texto.substring(0,texto.length() -1);
                		anchoLinea = graphics2D.getFontMetrics().stringWidth(texto);
                	}

                }
                graphics2D.drawString(texto, linea.getX(), linea.getY());
            }
        }

        Grafico grafico;
        String path;
        String archivo;
        int x;
        int y;

        for (int i = 0; i < this.graficos.size(); i++) {
            grafico = (Grafico) this.graficos.elementAt(i);

            BufferedImage buf = grafico.getBuf();
            x = grafico.getX();
            y = grafico.getY();
            this.imprimirGrafico(graphics2D, buf, x, y);
        }


		GraficoCuadro graficoCuadro;
		for (int i = 0; i < this.cuadros.size(); i++) {
			graficoCuadro = (GraficoCuadro) this.cuadros.elementAt(i);
			graphics2D.drawRect(graficoCuadro.getX() , graficoCuadro.getY(), graficoCuadro.getW(), graficoCuadro.getH());
		}

        return PAGE_EXISTS;
    }

    /**
     * Imprime el logo de la página.
     * @param graphics2D
     * @param page
     */
    private void imprimirLogo(Graphics2D graphics2D, PageFormat page) {
        ClassLoader loader = ImprimibleCertificado.class.getClassLoader();
        System.out.println("ClassLoader");

        try {
            //URL url = loader.getResource();
            InputStream stream = loader.getResourceAsStream("icono3.gif");
            BufferedImage buf = ImageIO.read(stream);
            graphics2D.drawImage(buf,
                new java.awt.image.AffineTransformOp(
                    new java.awt.geom.AffineTransform(),
                    java.awt.image.AffineTransformOp.TYPE_BILINEAR), margenIzq,
                margenSup - 12);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable th) {
        	th.printStackTrace();
        }
    }

    /**
     * Imprime el logo de la página.
     * @param graphics2D
     * @param page
     */
    protected void imprimirMargen(Graphics2D graphics2D, PageFormat page) {
        double x;
        double y;
        double w;
        double h;
        x = ImprimibleConstantes.MARGEN_IZQ / 2;
        y = ImprimibleConstantes.MARGEN_IZQ / 2;
        w = page.getImageableWidth() - 5;
        h = page.getImageableHeight() - 5;
        System.out.println("******IMPRIMIENDO MARGEN -x:"+x+"-y:"+y+"-w:"+w+"h:"+h);
        graphics2D.drawRect((int) x, (int) y, (int) w, (int) h);
    }


	Rectangle2D.Double dimension = null;

	public void setDimension( Rectangle2D.Double newDimension ) {
		dimension = newDimension;
	}

	public Rectangle2D.Double getDimension() {
		return dimension;
	}


        /*Adiciona Funcionalidad Boton de Pago
         * Author: Ingeniero Diego Hernadez
         * Modificacion en: 20/04/2010 by jvenegas
         */
    /**
     * Imprime un grafico en la página.
     * @param graphics2D
     * @param buf
     * @param x
     * @param y
     */
    private void imprimirGrafico(Graphics2D graphics2D, BufferedImage buf,
        int x, int y) {
        try {
        	java.awt.image.AffineTransformOp op = new java.awt.image.AffineTransformOp(
                    new java.awt.geom.AffineTransform(),
                    java.awt.image.AffineTransformOp.TYPE_BILINEAR);
        	BufferedImage bufDest = op.filter(buf, null);
            graphics2D.drawImage(bufDest,op, x, y);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error imprimiendo el gráfico");
        }
    }

    /**
     * Obtener el atributo imprMargen
     *
     * @return Retorna el atributo imprMargen.
     */
    public boolean isImprMargen() {
        return imprMargen;
    }

    /**
     * Actualizar el valor del atributo imprMargen
     * @param imprMargen El nuevo valor del atributo imprMargen.
     */
    public void setImprMargen(boolean imprMargen) {
        this.imprMargen = imprMargen;
    }

            /*Adiciona Funcionalidad Boton de Pago
         * Author: Ingeniero Diego Hernadez
         * Modificacion en: 20/04/2010 by jvenegas
         */

    /**
     * Obtener el atributo printWatermarkEnabled
     *
     * @return Retorna el atributo printWatermarkEnabled.
     */
	public boolean isPrintWatermarkEnabled() {
		return printWatermarkEnabled;
	}

    /**
     * Actualizar el valor del atributo printWatermarkEnabled
     * @param printWatermarkEnabled El nuevo valor del atributo printWatermarkEnabled.
     */
	public void setPrintWatermarkEnabled(boolean printWatermarkEnabled) {
		this.printWatermarkEnabled = printWatermarkEnabled;
	}

	private BufferedImage generate_PrintWatermark()
	{
		ClassLoader loader = ImprimibleCertificado.class.getClassLoader();
		BufferedImage local_WatermarkImageT0 = null;

		try
		{
			InputStream stream = loader.getResourceAsStream( "waterMark.gif" );
			if (stream != null)
				local_WatermarkImageT0 = ImageIO.read(stream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return local_WatermarkImageT0;
    }

	private void process_PrintWatermark( Graphics2D graphics2D, PageFormat page )
	{
		Graphics2D g2d;
		g2d = graphics2D;

		BufferedImage local_WatermarkImageT0 = generate_PrintWatermark();

		if (local_WatermarkImageT0 != null)
			g2d.drawImage(local_WatermarkImageT0,58,350,520,193,null);
	}
}

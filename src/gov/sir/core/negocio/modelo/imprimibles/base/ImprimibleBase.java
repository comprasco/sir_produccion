package gov.sir.core.negocio.modelo.imprimibles.base;

import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConsulta;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFotocopiaCrearSolicitud;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleResolucionRestitucion;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;

import gov.sir.print.common.Imprimible;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;


/**
 * @author gvillal
 * Clase Base de los Imprimibles
 */
public abstract class ImprimibleBase extends Imprimible implements Pageable {
    /**Variable que tiene la coordenada vertical de la linea de impresión en la
     * hoja actual**/
    private int i;
    private static final long serialVersionUID = 1L;
    /**Variable que indica si se paso la pagina o no para el relleno**/
    private int flagpagina;
    
    /**Variable que indica si se quiere crear una nueva pagina o no**/
    private int flagNuevapagina;

    /**Formato de la página **/
    private PageFormat pageFormat;
    
	/**Determina si alguna de las anotaciones tiene salvedades*/
	protected boolean hasSalvedadesAnotaciones = false;    

    /**Variable que sirve para saber si se esta imprimiendo una anotacion*/
    protected boolean esAnotacion = false;

    /**Indica si se debe imprimir el mensaje de "Continua en el siguiente recibo" en un recibo de registro. */
    private boolean validarMensajeReciboRegistro = false;
    
    /**Indica si el mensaje "Continua en el siguiente recibo" ya fue impreso. */
    private boolean mensajeImpreso = false;
    
    /**Vector de las pagina imprimibles de tipo <HojaImprimible>**/
    private Vector paginas;
    private boolean imprimirMargen = true;
    private String tipoImprimible;
    
    private boolean printWatermarkEnabled = false;

    private int numCaracteres = 0;

	private int anchoLinea;
	
	private boolean isPdf;
    /**
     * Constructor de la clase.
     *
     */
    public ImprimibleBase(String tipoImprimible) {
    	super(tipoImprimible);
        this.paginas = new Vector();
        this.esAnotacion = false;
        this.tipoImprimible=tipoImprimible;
        
        this.flagNuevapagina = 0;
    }

    /**Returns the number of pages in the set.**/
    public int getNumberOfPages() {
    	
    	return this.paginas.size();
    
    }

    /**Returns the PageFormat of the page specified by pageIndex.**/
    public PageFormat getPageFormat(int pageIndex) {
        return this.pageFormat;
    }

    /**Returns the Printable instance responsible**/
    public Printable getPrintable(int pageIndex) {
        return (Printable) paginas.elementAt(pageIndex);
    }

    /**
     * Metodo que avanza el cursor de impresión al final de la página.
     */
    protected void goPageEnd() {
        this.i = ImprimibleConstantes.MAXIMO_VERTICAL + 1;
//      Se cambia la hoja
    }

    /**
     * Imprime una linea con el estilo de texto asociado.
     * @param estilo Constante que representa el estilo del texto.
     * @param line texto a imprimir.
     */
    protected void imprimirLinea(int estilo, String line) {
        int x = ImprimibleConstantes.MARGEN_IZQ;
        imprimirLinea(estilo, x, line);
    }
    
    /**
     * Imprime una linea con el estilo de texto asociado.
     * @param estilo Constante que representa el estilo del texto.
     * @param line texto a imprimir.
     * @param incrementarLinea constante que indica si se genera una nueva
     * linea despues de imprimir el texto.
     */
    protected void imprimirLinea(int estilo, String line,
        boolean incrementarLinea) {
        int x = ImprimibleConstantes.MARGEN_IZQ;
        this.imprimirLinea(estilo, x, line, incrementarLinea);
    }

    /**
     *
     * Imprime una linea con el estilo de texto asociado.
     * @param estilo Constante que representa el estilo del texto.
     * @param x coordenada horizontal (en 1/72 pulgadas) del texto.
     * @param line texto a imprimir.
     */
    protected void imprimirLinea(int estilo, int x, String line) {
    	this.imprimirLinea(estilo, x, line, true);
    }

    /**
     * Imprime una linea con el estilo de texto asociado.
     * @param estilo Constante que representa el estilo del texto.
     * @param line texto a imprimir.
     * @param numCaracteres numero de caracteres permitidos en una linea.
     */
    protected void imprimirLinea(int estilo, int x, String line, int numCaracteres) {
        this.numCaracteres = numCaracteres;
    	this.imprimirLinea(estilo, x, line, true);
    }
    
    /**
     * Imprime una linea con el estilo de texto asociado.
     * @param estilo Constante que representa el estilo del texto.
     * @param x coordenada horizontal (en 1/72 pulgadas) del texto.
     * @param line texto a imprimir.ç
    * @param incrementarLinea constante que indica si se genera una nueva
    * linea despues de imprimir el texto.
    */
    protected void imprimirLinea(int estilo, int x, String line,
        boolean incrementarLinea) {
        //System.out.println("[ImprimibleBase.imprimirLinea]: line=" + line);
		line = line == null ? line = "" : line;

        

        int tamFuente = 1;
        int tipoFuente = 1;

        if (estilo == ImprimibleConstantes.TITULO1) {
            tamFuente = ImprimibleConstantes.TAM_TITULO_1;
            tipoFuente = ImprimibleConstantes.TIPO_TEXTO_TITULO1;
        } else if (estilo == ImprimibleConstantes.TITULO2) {
            tamFuente = ImprimibleConstantes.TAM_TITULO_2;
            tipoFuente = ImprimibleConstantes.TIPO_TEXTO_TITULO2;
        } else if (estilo == ImprimibleConstantes.PLANO) {
            tamFuente = ImprimibleConstantes.TAM_PLANO;
            tipoFuente = ImprimibleConstantes.TIPO_TEXTO_PLANO;
        } else if (estilo == ImprimibleConstantes.TITULO_GRANDE) {
            tamFuente = ImprimibleConstantes.TAM_GRANDE;
            tipoFuente = ImprimibleConstantes.TIPO_TEXTO_TITULO1;
        } else if (estilo == ImprimibleConstantes.TITULO_GRANDE2) {
            tamFuente = ImprimibleConstantes.TAM_GRANDE2;
            tipoFuente = ImprimibleConstantes.TIPO_TEXTO_TITULO1;
        } else if (estilo == ImprimibleConstantes.TITULO_MICRO) {
            tamFuente = ImprimibleConstantes.TAM_MICRO;
            tipoFuente = ImprimibleConstantes.TIPO_TEXTO_PLANO;
        } else if( ImprimibleConstantes.TAM_LETRAMENUDA == estilo ) {
            tamFuente = ImprimibleConstantes.TAM_LETRAMENUDA_FONT;
            tipoFuente = java.awt.Font.BOLD & java.awt.Font.ITALIC;
        }else if( ImprimibleConstantes.PEQUE == estilo ) {
			tamFuente = ImprimibleConstantes.TAM_PEQUE;
			tipoFuente = ImprimibleConstantes.TIPO_TEXTO_PLANO;
		} else if( ImprimibleConstantes.PLANO2 == estilo ) {
			tamFuente = ImprimibleConstantes.TAM_PLANO2;
            tipoFuente = ImprimibleConstantes.TIPO_TEXTO_PLANO2;
		}

        int permitidos = ImprimibleConstantes.MAX_NUM_CHAR;
		
		if (estilo == ImprimibleConstantes.PEQUE) {
			permitidos = ImprimibleConstantes.MAX_NUM_CHAR_PEQUE;
		}
        if (estilo == ImprimibleConstantes.TITULO_MICRO) {
            permitidos = ImprimibleConstantes.MAX_NUM_CHAR_MICRO;
        }
        if( ImprimibleConstantes.TAM_LETRAMENUDA == estilo ) {
            permitidos = ImprimibleConstantes.TAM_LETRAMENUDA_NUMLINECHARS;
        }
        if( this.numCaracteres!=0 ){
        	permitidos = numCaracteres;
        }

        int blancos = 0;

        for (int i1 = 0; i1 < line.length(); i1++) {
            if (line.charAt(i1) == ' ') {
                blancos++;
            }
        }

        int tamano = line.length() - (blancos / 2);

		HojaImprimible hoja = this.getHojaActual();

        if (tamano > permitidos) {
			
            String cad = "";
            Vector lineas = this.getVectorLineas(line, x, estilo);

            for (int j = 0; j < lineas.size(); j++) {
				hoja = this.getHojaActual();
                cad = (String) lineas.get(j);
                if (this.getAnchoLinea() > 0)
                {
                	if((validarMensajeReciboRegistro)&&(!mensajeImpreso)){
                    	if(this.isLimiteVertical(ImprimibleConstantes.SEPARACION_LINE)){
                    		mensajeImpreso = true;
                    		this.imprimirLinea(ImprimibleConstantes.PLANO,170,ImprimibleConstantes.SIGUIENTE_RECIBO);
                    		this.imprimirLinea(estilo, x, line, incrementarLinea);
                    	}else{
                        	hoja.imprimirLinea(tipoFuente, tamFuente, cad, x, this.i, this.getAnchoLinea());
                        }
                    	
                    }else{
                    	hoja.imprimirLinea(tipoFuente, tamFuente, cad, x, this.i, this.getAnchoLinea());
                    }  
                } else
                {
                	if((validarMensajeReciboRegistro)&&(!mensajeImpreso)){
                    	if(this.isLimiteVertical(ImprimibleConstantes.SEPARACION_LINE)){
                    		mensajeImpreso = true;
                    		this.imprimirLinea(ImprimibleConstantes.PLANO,170,ImprimibleConstantes.SIGUIENTE_RECIBO);
                    		this.imprimirLinea(estilo, x, line, incrementarLinea);
                    	}else{
                        	hoja.imprimirLinea(tipoFuente, tamFuente, cad, x, this.i);
                        }
                    	
                    }else{
                    	hoja.imprimirLinea(tipoFuente, tamFuente, cad, x, this.i);
                    }   
                }
                
				if( estilo == ImprimibleConstantes.TAM_LETRAMENUDA ){
					this.incrementarI(ImprimibleConstantes.TAM_LETRAMENUDA_SEP);
	
				}else if(estilo == ImprimibleConstantes.PEQUE ){
					this.incrementarI(ImprimibleConstantes.SEPARACION_LINE_PEQUE);
				}else{
					this.incrementarI(ImprimibleConstantes.SEPARACION_LINE);
				}
					                   
            }
        } else {
        	if (this.getAnchoLinea() > 0)
        	{
        		if((validarMensajeReciboRegistro)&&(!mensajeImpreso)){
                	if(this.isLimiteVertical(ImprimibleConstantes.SEPARACION_LINE)){
                		//i++;
                		mensajeImpreso = true;
                		this.imprimirLinea(ImprimibleConstantes.PLANO,170,ImprimibleConstantes.SIGUIENTE_RECIBO);
                		this.imprimirLinea(estilo, x, line, incrementarLinea);
                	}else{
                    	hoja.imprimirLinea(tipoFuente, tamFuente, line, x, this.i, this.getAnchoLinea());
                    }
                }else{
                	hoja.imprimirLinea(tipoFuente, tamFuente, line, x, this.i, this.getAnchoLinea());
                }    		
        	}
        	else 
        	{
        		if((validarMensajeReciboRegistro)&&(!mensajeImpreso)){
                	if(this.isLimiteVertical(ImprimibleConstantes.SEPARACION_LINE)){
                		mensajeImpreso = true;
                		this.imprimirLinea(ImprimibleConstantes.PLANO,170,ImprimibleConstantes.SIGUIENTE_RECIBO);
                		this.imprimirLinea(estilo, x, line, incrementarLinea);
                	}else{
                    	hoja.imprimirLinea(tipoFuente, tamFuente, line, x, this.i, this.getAnchoLinea());
                    }
                	
                }else{
                	hoja.imprimirLinea(tipoFuente, tamFuente, line, x, this.i);
                }    		
        	}
        	numCaracteres = 0;

            if( incrementarLinea ) {
            	
				if( estilo == ImprimibleConstantes.TAM_LETRAMENUDA ){
					this.incrementarI(ImprimibleConstantes.TAM_LETRAMENUDA_SEP);
				}else if(estilo == ImprimibleConstantes.PEQUE ){
					this.incrementarI(ImprimibleConstantes.SEPARACION_LINE_PEQUE);
				}else{
					this.incrementarI(ImprimibleConstantes.SEPARACION_LINE);
				}            	

            }
            mensajeImpreso = false;
        }
        this.anchoLinea = 0;
    }
    

    /**
     * Imprime una linea con el estilo de texto asociado.
     * @param imagen imagen que se va a imprimir
     */
    protected void imprimirGrafico(BufferedImage imagen) {
        HojaImprimible hoja = (HojaImprimible) this.paginas.elementAt(this.getNumberOfPages() - 1);

        hoja.imprimirGrafico(50, i, imagen);
    }
    
	/**
	 * Imprime una linea con el estilo de texto asociado.
	 * @param imagen imagen que se va a imprimir
	 */
	protected void imprimirGrafico(BufferedImage imagen, int x) {
		HojaImprimible hoja = (HojaImprimible) this.paginas.elementAt(this.getNumberOfPages() -
				1);

		hoja.imprimirGrafico(x, i, imagen);
	}   
	
	/**
	 * Método que imprime un cuadro, recibe como parametros el x , y y la altura y ancho.
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	protected void imprimirCuadro(int x, int y, int w, int h) {
		HojaImprimible hoja = (HojaImprimible) this.paginas.elementAt(this.getNumberOfPages() -	1);
		hoja.imprimirCuadro(x,y,w,h);
	}   
    /**
     * Funcion que retorna el vector de lineas.
     * @param line texto
     * @param estilo estilo del texto.
     * @return un vector de cadenas de texto que caben
     * en este imprimible con el estilo dado.
     */
    protected Vector getVectorLineas(String line, int estilo) {
    	return getVectorLineas(line, ImprimibleConstantes.MARGEN_IZQ, estilo);
    }
	
    /**
     * Funcion que retorna el vector de lineas.
     * @param line texto
     * @param x justificación
     * @param estilo estilo del texto.
     * @return un vector de cadenas de texto que caben
     * en este imprimible con el estilo dado.
     */
    protected Vector getVectorLineas(String line, int x, int estilo) {
    	
    	
    	
    	int tamano = line.length();
        int permitidos = ImprimibleConstantes.MAX_NUM_CHAR;
		if (estilo == ImprimibleConstantes.TITULO1) {
			permitidos = ImprimibleConstantes.MAX_NUM_CHAR_TITULO1;
		} else if (estilo == ImprimibleConstantes.TITULO_MICRO) {
            permitidos = ImprimibleConstantes.MAX_NUM_CHAR_MICRO;
        } else if (estilo == ImprimibleConstantes.PEQUE) {
			permitidos = ImprimibleConstantes.MAX_NUM_CHAR_PEQUE;
		} else if( this.numCaracteres!=0 ){
        	permitidos = numCaracteres;
        	numCaracteres = 0;
        }       

        Vector lineas = new Vector();

        if (tamano > permitidos) {
            String[] cadenas = line.split(" ");
            String cad = "";
            int tam = cadenas.length;

            for (int i1 = 0; i1 < tam; i1++) {
            	// obtenemos la nueva longitud de la linea con base a longitud de la cadena existe, mas la nueva palabra sin contar con los
            	// comandos de linea <b>, <P>
                if ((cad.length() + 1 + cadenas[i1].toUpperCase().replaceAll("<B>", "").replaceAll("<P>", "").replaceAll("</P>", "").length()) <= permitidos) {
                    cad = cad + " " + cadenas[i1];
                } else {
               		lineas.add(cad.trim());
                    cad = cadenas[i1];
                }
            }

            lineas.add(cad.trim());
        }

        return lineas;
    }

    /**(non-Javadoc)
     * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
     * Genera el vector de hojas imprimibles
     */
    /** @author : HGOMEZ
        *** @change : Para mejorar el debug de SIR se declara el Override del
        * siguiente método generate.
        *** Caso Mantis : 11598
        */
    @Override
    public void generate(PageFormat pageFormat1) {
        //System.out.println("Entrando a ImprimibleCertificado.print");
        this.pageFormat = pageFormat1;

        //System.out.println("INFO: PageFormat.class " + this.pageFormat );

        this.makeNewPage();
    }

    /**
     * Metodo que retorna un numero con un "0" antes en caso de ser menor
     * que 10.
     * @param i el numero.
     * @return  cadena
     */
    protected String formato(int i1) {
        if (i1 < 10) {
            return "0" + (new Integer(i1)).toString();
        } else {
            return (new Integer(i1)).toString();
        }
    }

	
	private boolean imprimirLogoEnabled = true;

	public void setImprimirLogoEnabled(boolean imprimirLogoEnabled) {
		this.imprimirLogoEnabled = imprimirLogoEnabled;
	}
	
	private Rectangle2D.Double areaImprimibleDimension = null;
	
	public void setAreaImprimibleDimension( Rectangle2D.Double newDimension ) {
		areaImprimibleDimension = newDimension;
	}
	
    /**
     * Imprime el encabezado del Imprimible.
     *
     */
    protected void makeNewPage() {
        
		HojaImprimible hoja = null;
		
		// se utiliza el tipo especializado de hoja, que
		// está habilitada para imprimir pdfs
		if (this.isPdf())
		{
			hoja = new HojaImprimiblePDF();
    	} else 
    	{
			hoja = new HojaImprimible();
    	}
        
		hoja.setImprMargen(this.imprimirMargen);
		hoja.setImprimirLogoEnabled(imprimirLogoEnabled);
		hoja.setPrintWatermarkEnabled(this.isPrintWatermarkEnabled());
		hoja.setDimension( areaImprimibleDimension );
        this.paginas.add(hoja);
        //System.out.println("num paginas = " + this.getNumberOfPages());

        //this.i =ImprimibleConstantes.margenSup;
        if (this instanceof ImprimibleResolucionRestitucion ){
        	this.i = ImprimibleConstantes.MARGEN_SUP + 40;
        } else {
        	this.resetPosicionVertical();
        }
        

        // Integer ancho = new Integer(new Double(
        //            pageFormat.getImageableWidth() / 2).intValue());
        
    }

    /**
     * Imprime el encabezado del Imprimible.
     *
     */
    protected void imprimirEncabezado() {
    }

    /**Fija la posicion del curos r de impresion en el margen superior de l página.**/
    private void resetPosicionVertical() {
        this.i = ImprimibleConstantes.MARGEN_SUP;
    }

    /**Método que imprime la firma al final del documento**/
    protected void imprimirFirma() {
        //this.imprimirLinea(ImprimibleConstantes.plano, "");
    	this.setFlagNuevapagina(1);
        this.imprimirLinea(ImprimibleConstantes.TITULO_MICRO,
            ImprimibleConstantes.SEPARADOR3_PEQ);
	    this.imprimirLinea(ImprimibleConstantes.PLANO,
	        "|Fecha:                 | El registrador(a)                                                        |");
	    this.imprimirLinea(ImprimibleConstantes.PLANO,
			"|Día  |Mes  |Año   | Firma                                                                         |");
	    this.imprimirLinea(ImprimibleConstantes.TITULO_MICRO,
	        ImprimibleConstantes.SEPARADOR3_PEQ);
	    this.imprimirLinea(ImprimibleConstantes.PLANO,
	        "|       |         |          |                                                                                   |");
	    this.imprimirLinea(ImprimibleConstantes.PLANO,
	        "|       |         |          |                                                                                   |");
	    this.imprimirLinea(ImprimibleConstantes.PLANO,
	        "|       |         |          |                                                                                   |");

        this.imprimirLinea(ImprimibleConstantes.TITULO_MICRO,
            ImprimibleConstantes.SEPARADOR3_PEQ);
        this.setFlagNuevapagina(0);
    }

    /**
     * Metodo que retorna la cadena con la fecha actual de impresión.
     * @return fecha de impresión
     */
    protected String getFechaImpresion() {
        Calendar c = Calendar.getInstance();
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

        String fechaImp = "Impreso el " + dia + " de " + mes + " de " + ano +
            " a las " + formato(hora) + ":" + min + ":" + seg + " " +
            DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

        return fechaImp;
    }

    /**
     * Retorna una cadena de caracteres que representa la fecha dada.
     * @param fecha
     * @return cadena fecha actual en formato d/M/yyyy
     */
    protected String getFecha(Date fecha) {
        String fechaString = " ";
        if(fecha != null){
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("d/M/yyyy");
            fechaString = formatoDeFecha.format(fecha);
        }
        return fechaString;
    }

    /**
     * Retorna un String con la fecha dada.
     * @param fecha
     * @return cadena con fecha en formato d/M/yyyy a las H:m:s
     */
    protected String getFechaHora(Date fecha) {

        
        SimpleDateFormat formatoDeFecha = null;
        String              fechaString = null;

        formatoDeFecha = new SimpleDateFormat("d/M/yyyy");
        fechaString    = formatoDeFecha.format(fecha);

        formatoDeFecha = new SimpleDateFormat("hh:m:ss");
        fechaString    = fechaString + " a las " + formatoDeFecha.format(fecha);
        
        return fechaString;
    }

    /**
     * @param format
     */
    public void setPageFormat(PageFormat format) {
        pageFormat = format;
    }

    /**
     * @return i
     */
    public int getI() {
        return i;
    }

    /**
     * @param i
     */
    public void setI(int i) {
        this.i = i;
    }

    /**
     * Imprime las direcciones del folio.
     * @param folio
     * @param formulario
     */
    protected void imprimirDirecciones(Folio folio, boolean formulario) {
        if (formulario) {
            this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO2,
                "DIRECCION DEL INMUEBLE");
        }

        List direcciones = folio.getDirecciones();

        if (direcciones.size() > 0) {
            Direccion direccion;
            int n = direcciones.size();

            for (int j = 1; j <= n; j++) {
                direccion = (Direccion) direcciones.get(j - 1);
				String linea = "   " + j + ") ";
                if(direccion.getEspecificacion()!=null){
					linea += ((direccion.getEspecificacion() != null) ? direccion.getEspecificacion() : "");
                }
                else{
                  linea += (((direccion.getEje() != null) && (direccion.getEje().getNombre() != null)
                             && !(direccion.getEje().getNombre().equals(WebKeys.SIN_SELECCIONAR))) ?
                            direccion.getEje().getNombre() : "");
                  linea += " ";
                  linea += ((direccion.getValorEje() != null) ? direccion.getValorEje() : "");
                  linea += " ";
                  linea += (((direccion.getEje1() != null) && (direccion.getEje1().getNombre() != null)
                             && !(direccion.getEje1().getNombre().equals(WebKeys.SIN_SELECCIONAR))) ?
                            direccion.getEje1().getNombre() : "");
                  linea += " ";
                  linea += ((direccion.getValorEje1() != null) ? direccion.getValorEje1() : "");
                }
                linea = linea.trim();
                if (!linea.equals("")){ 
					this.imprimirLinea(ImprimibleConstantes.PLANO, linea); 
				} 
            }
        }
        this.imprimirLinea(ImprimibleConstantes.PLANO, "");
    }

    /**
     * Mueve el cursor de impresion a la linea anterior.
     *
     */
    protected void moveToLineaAnterior() {
        this.i -= ImprimibleConstantes.SEPARACION_LINE;
    }

    /**
     * Mueve el cursor numLineas veces a la linea anterior.
     * @param numLineas
     */
    protected void moveToLineaAnterior(int numLineas) {
        for (int i1 = 0; i1 < numLineas; i1++)
            this.moveToLineaAnterior();
    }

    /**
             * Retorna el nombre completo del ciudadano, tiene en cuenta el caso en que alguno de los
             * apellidos o el nombre es null, si el ciudadano es un empresa --> solo imprime el apellido1 que
             * corresponde a la razon social de la empresa.
             * @param ciudadano
             * @return una cadena con el nombre completo del ciudadano, en caso de que alguno de los parametros
             * del nombre sea null incluye una nota indicando que no fue registrado el nombre o apellido.
             */
    protected String getNombreCompleto2(Ciudadano ciudadano) {
        String completo = "";
        String ape1 = "";
        String ape2 = "";
        String nombre = "";

        if (ciudadano != null) {
            nombre = ciudadano.getNombre();
            ape1 = ciudadano.getApellido1();
            ape2 = ciudadano.getApellido2();
            //System.out.println("Apellido 1: " + ape1);
            //System.out.println("Apellido 2: " + ape2);
            //System.out.println("Nombre : " + nombre);

            //BUG 10714 mantis - Se comentarea. 
            //es una empresa.
            /*if (CCiudadano.TIPO_DOC_ID_NIT.equalsIgnoreCase(
                        ciudadano.getTipoDoc())) {
                if (ape1 == null) {
                    ape1 = ImprimibleConstantes.RAZON_SOCIAL_NULL;
                }

                completo = ape1;
            } else {*/
                if (nombre == null) {
                    nombre = ""; //ImprimibleConstantes.nombreNull;
                }

                if (ape1 == null) {
                    ape1 = ""; //ImprimibleConstantes.primerApellidoNull;
                }

                if (ape2 == null) {
                    ape2 = ""; //ImprimibleConstantes.segundoApellidoNull;
                }

                completo = ape1 + " " + ape2 + " " + nombre;
            //}
        } else {
            //System.out.println("[ImprimibleReciboCertificado.getNombreCompleto]:Ciudadano es null");
        }

        return completo;
    }

    /**
         * Retorna el nombre completo del ciudadano, tiene en cuenta el caso en que alguno de los
         * apellidos o el nombre es null, si el ciudadano es un empresa --> solo imprime el apellido1 que
         * corresponde a la razon social de la empresa.
         * @param ciudadano
         * @return una cadena con el nombre completo del ciudadano, en caso de que alguno de los parametros
         * del nombre sea null incluye una nota indicando que no fue registrado el nombre o apellido.
         */
    protected String getNombreCompletoConId(Ciudadano ciudadano) {
        String resp = getNombreCompleto2(ciudadano);
		
		if(ciudadano != null) {
	        String tipoId = ciudadano.getTipoDoc();
	        String numId = ciudadano.getDocumento();
	
	        if ((tipoId != null) && (numId != null)) {
	            if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {
	                resp += ("           " + tipoId);
	                resp += ("# " + numId);
	            }
	        }
		}

        return resp;
    }

    /**
     * Retorna un parametro, si es null retorna cadena vacia.*
     * @param param
     * @return "" si es nulo param
     * */
    protected String getString(String param) {
        if (param == null) {
            return "";
        }

        return param;
    }

    /**
     * Retorna la direccion con el ID más alto.
     * @param direcciones
     * @return dirección con ID mayor
     */
    protected Direccion getUltimaDireccion(List direcciones) {
        if (direcciones.size() == 0) {
            return null;
        }

        int max = 0;
        int pos = 0;

        for (int i1 = 0; i1 < direcciones.size(); i1++) {
            Direccion dir = (Direccion) direcciones.get(i1);
            String id = dir.getIdDireccion();
            int x = Integer.parseInt(id);

            if (x > max) {
                max = x;
                pos = i1;
            }
        }

        return (Direccion) direcciones.get(pos);
    }

    /**
     * @return true/false
     */
    public boolean isImprimirMargen() {
        return imprimirMargen;
    }

    /**
     * @param b
     */
    public void setImprimirMargen(boolean b) {
        imprimirMargen = b;
    }
    
    private void incrementarI(int cantidad){
    	this.i+= cantidad;
    	
    	HojaImprimible hoja = this.getHojaActual();
    	
		// descubrir limite vertical ---------------------------------------------
			int limiteVertical;

			Rectangle2D.Double dimension = null;
		
			dimension = hoja.getDimension(); // unidades: Paper.units
		
			if( null != dimension ) {
				final int INCH = 72;
				final int aproxMargin = INCH / 4; 
				
				limiteVertical = (int)( dimension.getHeight() - ( 2 * aproxMargin ) + ( aproxMargin * 0.75 ) );
				//System.out.println("---->A: limiteVertical="+limiteVertical);
			}
			else {
				limiteVertical = ImprimibleConstantes.MAXIMO_VERTICAL;
				//System.out.println("---->B: limiteVertical="+limiteVertical);
			}
	

		//System.out.println("---->limiteVertical="+limiteVertical);
		//System.out.println("---->MAXIMO_VERTICAL="+ImprimibleConstantes.MAXIMO_VERTICAL);
	
			// descubrir limite vertical ---------------------------------------------
		

			if (this.esAnotacion) {
				limiteVertical = limiteVertical - 72;
			}

			if (this instanceof ImprimibleConsulta) {
				limiteVertical = 395;
			}
			
			if (this instanceof ImprimibleFotocopiaCrearSolicitud) {
				limiteVertical = 395;
			}			
			
			if ((this.i > limiteVertical) && (this.flagNuevapagina == 0)) {
				int numPages = this.getNumberOfPages();
				numPages++;
				this.setNumberOfPages(numPages);
				this.makeNewPage();
				this.imprimirEncabezado();

				//System.out.println("------------------------numPages=" + numPages);
//				Se cambia la hoja
	            //System.out.println("Paso la pagina");
	            this.setFlagPagina(1);
			}
		
    }
    
    
    public boolean isLimiteVertical(int cantidad){
    	this.i+= cantidad;
    	//System.out.println("NUMERO DE I: "+i);
    	HojaImprimible hoja = this.getHojaActual();
    	
		// descubrir limite vertical ---------------------------------------------
			int limiteVertical;
			Rectangle2D.Double dimension = null;
			dimension = hoja.getDimension(); // unidades: Paper.units
			if( null != dimension ) {
				final int INCH = 72;
				final int aproxMargin = INCH / 4; 
				limiteVertical = (int)( dimension.getHeight() - ( 2 * aproxMargin ) + ( aproxMargin * 0.75 ) );
			}
			else {
				limiteVertical = ImprimibleConstantes.MAXIMO_VERTICAL;
			}
	
			// descubrir limite vertical ---------------------------------------------
		
			if (this.esAnotacion) {
				limiteVertical = limiteVertical - 72;
			}
			if (this instanceof ImprimibleConsulta) {
				limiteVertical = 395;
			}			
			if (this instanceof ImprimibleFotocopiaCrearSolicitud) {
				limiteVertical = 395;
			}	
			if ((this.i > limiteVertical) && (this.flagNuevapagina == 0)) {
				this.i-= cantidad;
				return true;
			}
			this.i-= cantidad;
			return false;
    }
    
    private HojaImprimible getHojaActual() {
		HojaImprimible hoja = (HojaImprimible) this.paginas.elementAt(this.getNumberOfPages() -1);
		return hoja;

    }
    
    /*
    private void printLine(){
    
    }
    */
	/**
	 * @return Returns the tipoImprimible.
	 */
	public String getTipoImprimible() {
		return tipoImprimible;
	}
	
	public void setTipoImprimible(String tipoImprimible) {
		this.tipoImprimible = tipoImprimible;
	}
	
	/**
	 * @return Returns the flagpagina
	 */
	public int getFlagPagina() {
		return flagpagina;
	}
	
	public void setFlagPagina(int pFlagPagina) {
		this.flagpagina = pFlagPagina;
	}

	public int getFlagNuevapagina() {
		return flagNuevapagina;
	}

	public void setFlagNuevapagina(int flagNuevapagina) {
		this.flagNuevapagina = flagNuevapagina;
	}

	public boolean isPrintWatermarkEnabled() {
		return printWatermarkEnabled;
	}

	public void setPrintWatermarkEnabled(boolean printWatermarkEnabled) {
		this.printWatermarkEnabled = printWatermarkEnabled;
	}

	public int getAnchoLinea() {
		return anchoLinea;
	}

	public void setAnchoLinea(int anchoLinea) {
		this.anchoLinea = anchoLinea;
	}
	
	public int getNumCaracteres() {
		return numCaracteres;
	}

	public void setNumCaracteres(int numCaracteres) {
		this.numCaracteres = numCaracteres;
	}

	public boolean isPdf() {
		return isPdf;
	}

	public void setPdf(boolean isPdf) {
		this.isPdf = isPdf;
	}

	public boolean isValidarMensajeReciboRegistro() {
		return validarMensajeReciboRegistro;
	}

	public void setValidarMensajeReciboRegistro(boolean validarMensajeReciboRegistro) {
		this.validarMensajeReciboRegistro = validarMensajeReciboRegistro;
	}
	
	public String justificarLinea(String linea, int tamañoLinea){
		boolean izquierda = true;
		StringBuffer lineaJustificada = new StringBuffer(linea);
		int i = lineaJustificada.length() / 2, l = 0;
		l = i;
		while (lineaJustificada.length() < tamañoLinea){
			if (izquierda){
				i = lineaJustificada.indexOf(" ", i); 
			    if (i < 0) {
			    	i = lineaJustificada.indexOf(" ", lineaJustificada.length() / 2);
			    	if (i < 0) { break; }
			    }
				lineaJustificada.insert(i, ' ');
				i = i + 3;
				izquierda = false;
			}else {
				l = lineaJustificada.lastIndexOf(" ", l);
			    if (l < 0) {
			    	l = lineaJustificada.indexOf(" ", lineaJustificada.length() / 2);
			    	if (l < 0) { break; }
			    }
				lineaJustificada.insert(l, ' ');
				l = l - 3;
				izquierda = true;
			}
		}
		return lineaJustificada.toString();
	}
}


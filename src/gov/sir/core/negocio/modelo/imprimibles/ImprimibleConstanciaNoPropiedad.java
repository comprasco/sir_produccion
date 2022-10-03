package gov.sir.core.negocio.modelo.imprimibles;

import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
import gov.sir.core.util.DateFormatUtil;
import java.text.SimpleDateFormat;

/**
 * @author gvillal
 * Clase que representa el Imprimible de un Oficio de Pertenencia.
 */
public class ImprimibleConstanciaNoPropiedad extends ImprimibleBase 
{
	/**Texto del Oficio de Pertenencia.**/
	private String fechaImpresion;
	private static final long serialVersionUID = 1L;
	/**El turno con el cual se hace la consulta*/
	private Turno turno;
	
	/**La solicitud de la consulta*/
	private SolicitudConsulta solicitud;
	
	private boolean tamanoCarta = true;

	/**La imagen de la firma de registrador*/
	private BufferedImage imagen = null;
	
	/**El usuario que realiza la consulta*/
	private String usuario;
	
	/**tamano en pixeles de la imagen de firma de registrador*/
	private byte[] pixelesImagenFirmaRegistrador = null;
	
	/**Nombre y cargo del registrador encargado*/
	private String nombreRegistrador = null;
    
    private String cargoRegistrador = null;
    
    private Ciudadano ciudadano;
    
    private boolean esPropietario = false; 
    
    
	/**
	 * Constructor de la clase
	 */
	public ImprimibleConstanciaNoPropiedad(Turno turno,SolicitudConsulta solicitud, String tipoImprimible) 
	{
		super(tipoImprimible);
		this.fechaImpresion = this.getFechaImpresion();
		this.turno = turno;
		this.solicitud = solicitud;
	}

	  /**
	   *Genera el vector de hojas imprimibles con toda la información que se va a imprimir. 
	   */
	  public void generate(PageFormat pageFormat) 
	  {    	
		   super.generate(pageFormat);
		   this.imprimirEncabezado();
		   
		   String nombreCompleto = "";
		   String nombre_o_razon_social = "PARA EL(LA) SEÑOR(A)";
		   String identificacion = "";
		   
		   if(ciudadano.getTipoDoc()!=null && ciudadano.getTipoDoc().equals(CCiudadano.TIPO_DOC_ID_NIT))
			   nombre_o_razon_social = "PARA EL(LA) REPRESENTANTE DE";
		   
		   if(ciudadano.getNombre()!=null && !ciudadano.getNombre().trim().equals(""))
			   nombreCompleto += " " + ciudadano.getNombre();
		   if(ciudadano.getApellido1()!=null && !ciudadano.getApellido1().trim().equals(""))
			   nombreCompleto += " " + ciudadano.getApellido1();
		   if(ciudadano.getApellido2()!=null && !ciudadano.getApellido2().trim().equals(""))
			   nombreCompleto += " " + ciudadano.getApellido2();
		   
		   nombreCompleto = "<b>" + nombreCompleto + "<b>";
		   
		   if(!ciudadano.getTipoDoc().equals("SIN_SELECCIONAR") && !ciudadano.getDocumento().trim().equals("")){
			   identificacion = " IDENTIFICADO(A) CON DOCUMENTO DE IDENTIDAD <b>" + ciudadano.getTipoDoc() + 
			   "<b> No. <b>" + ciudadano.getDocumento() + "<b>,";
		   }
		   
		   String texto = "EL(LA) REGISTRADOR(A) DE INSTRUMENTOS PUBLICOS, HACE CONSTAR QUE CONSULTADOS LOS "+
		   		"INDICES DE PROPIETARIOS EN ESTA OFICINA, " + nombre_o_razon_social + nombreCompleto + "," +
		   		identificacion + " SE OBTUVIERON LOS SIGUIENTES RESULTADOS:";
		   
		   this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ,texto);
		   this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "");
		   this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "");
		   this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "");
		   
		   String linea = "";
		   
		   if(esPropietario){
			   linea = "ES PROPIETARIO DE LOS BIENES INMUEBLES O TITULAR DE DERECHOS INSCRITOS";
		   }else{
			   linea = "NO ES PROPIETARIO DE LOS BIENES INMUEBLES NI TITULAR DE DERECHOS INSCRITOS";
		   }
		   
		   this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ,linea);
		   
		   this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "");
		   this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "");
		   
		   if(solicitud.getSolicitudFolios()!=null && solicitud.getSolicitudFolios().size()>0){
			   this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,"DETALLE DE LOS INMUEBLES: ");
			   this.imprimirResultadosMatriculas(this.turno);
		   }else{
			   this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,"NO SE OBTUVIERON RESULTADOS ");
		   }
		   
		   this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "");
		   this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "");
		   this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "");
		   this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "");
		   
		   int tamFinDoc = getTamanoFinDocumento();
		   this.imprimirFirma();
		   
		   this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "");
		   this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "");
		   
		   this.imprimirPie();
	  }		
	
	  /**Imprime el pie de pagina de la constancia*/
	private void imprimirPie() {
		String pie = "El interesado debe comunicar cualquier falla o error en la constancia.";
		this.imprimirLinea(ImprimibleConstantes.TITULO2, ImprimibleConstantes.MARGEN_IZQ, pie);
		
		String linea = "USUARIO: " + usuario + " IMPRESO POR: " + usuario;
		this.imprimirLinea(ImprimibleConstantes.TITULO2, ImprimibleConstantes.MARGEN_IZQ, linea);
		
		linea = "TURNO: " + turno.getIdWorkflow() +  " FECHA: " + this.getFecha(turno.getFechaInicio());
		this.imprimirLinea(ImprimibleConstantes.TITULO2, ImprimibleConstantes.MARGEN_IZQ, linea);
		
	}

	/**
	 * Imprime el encabezado del oficio de pertenencia.
	 */	
	protected void imprimirEncabezado()
	{
		String linea; 
		linea = StringFormat.getCentrada("OFICINA DE REGISTRO DE INSTRUMENTOS PUBLICOS",ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,ImprimibleConstantes.LONG_LOGO);
		this.imprimirLinea(ImprimibleConstantes.TITULO1,linea);
		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		
		linea = StringFormat.getCentrada(turno.getSolicitud().getCirculo().getNombre(),ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,ImprimibleConstantes.LONG_LOGO);
		this.imprimirLinea(ImprimibleConstantes.TITULO1,linea);
		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		
		linea = StringFormat.getCentrada("CONSTANCIA DE NO PROPIEDAD O TITULAR DE DERECHOS INSCRITOS",ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,7);
		this.imprimirLinea(ImprimibleConstantes.TITULO1,linea);
		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		
		this.imprimirLinea(ImprimibleConstantes.PLANO,(ImprimibleConstantes.MARGEN_IZQ * 5)+18, fechaImpresion);
		
		linea = "No tiene validez sin la firma del Registrador";
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 6,linea);
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
	}

	/**Imprime la lista de matriculas encontradas en la consulta*/
	private void imprimirResultadosMatriculas(Turno turnoConsul)
	{
		System.out.println("Imprimiendo los resultados de la busqueda.....");

		List solicitudesFolio=turnoConsul.getSolicitud().getSolicitudFolios();
		
		for (int i=0; i<solicitudesFolio.size(); i++ ){

			SolicitudFolio solFol = (SolicitudFolio)solicitudesFolio.get(i);
			String idmatricula = solFol.getIdMatricula();
			List direcciones = solFol.getFolio().getDirecciones();
			
			this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO1,"");
			this.imprimirLinea(ImprimibleConstantes.PLANO,idmatricula,false);
			
			if(direcciones!=null && direcciones.size()>0){
				Direccion dir = (Direccion)direcciones.get(direcciones.size()-1);
				this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ * 4,dir.getEspecificacion(),true);
			}else{
				this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ * 4,"",true);
			}                                              	
		}
	}
	
	/**
     * Obtener el atributo pixelesImagenFirmaRegistrador
     *
     * @return Retorna el atributo pixelesImagenFirmaRegistrador.
     */
    public byte[] getPixelesImagenFirmaRegistrador() {
        return pixelesImagenFirmaRegistrador;
    }

    /**
     * Actualizar el valor del atributo pixelesImagenFirmaRegistrador
     * @param pixelesImagenFirmaRegistrador El nuevo valor del atributo pixelesImagenFirmaRegistrador.
     */
    public void setPixelesImagenFirmaRegistrador(
        byte[] pixelesImagenFirmaRegistrador) {
        this.pixelesImagenFirmaRegistrador = pixelesImagenFirmaRegistrador;
    }

    protected int getTamanoFinDocumento(){
		//-----------------------------------------------
		   int tamFirma = this.loadImagenFirma();
		   System.out.println("tamFirma="+tamFirma);
		   System.out.println("I="+this.getI());

		   int tamFinDoc = tamFirma + ImprimibleConstantes.TAMANO_ADICIONAL_FIRMA;		
		 
		 return tamFinDoc;
	}
    
    private int loadImagenFirma() {
		
		try {
			if (this.pixelesImagenFirmaRegistrador != null) {
				imagen = UIUtils.loadImage(this.pixelesImagenFirmaRegistrador);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int tam = ImprimibleConstantes.TAMANO_FIRMA_ESTATICA;
		if (imagen!=null){
			tam =imagen.getHeight();
		}

		return tam;
    }
    
    protected void imprimirFirma() {

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
		this.setFlagNuevapagina(1);
        this.imprimirGrafico(imagenFirmaRegistrador);

        int i = this.getI();
        i += altura;
        this.setI(i);
        this.imprimirLinea(ImprimibleConstantes.PLANO, "________________________________________________");

        String linea ="";

        if (this.cargoRegistrador != null) {
            linea += (this.cargoRegistrador + " ");
        }
        
        linea += ": ";

        if (this.nombreRegistrador != null) {
            linea += this.nombreRegistrador;
        }

        this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
        this.setFlagNuevapagina(0);
    }
    
    
    /**
     * Obtener el atributo nombreRegistrador
     *
     * @return Retorna el atributo nombreRegistrador.
     */
    public String getNombreRegistrador() {
        return nombreRegistrador;
    }

    /**
     * Actualizar el valor del atributo nombreRegistrador
     * @param nombreRegistrador El nuevo valor del atributo nombreRegistrador.
     */
    public void setNombreRegistrador(String nombreRegistrador) {
        this.nombreRegistrador = nombreRegistrador;
    }
    
    /**
     * Obtener el atributo cargoRegistrador
     *
     * @return Retorna el atributo cargoRegistrador.
     */
    public String getCargoRegistrador() {
        return cargoRegistrador;
    }

    /**
     * Actualizar el valor del atributo cargoRegistrador
     * @param cargoRegistrador El nuevo valor del atributo cargoRegistrador.
     */
    public void setCargoRegistrador(String cargoRegistrador) {
        this.cargoRegistrador = cargoRegistrador;
    }
    
    /**
     * Obtener el atributo usuario
     *
     * @return Retorna el atributo usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Actualizar el valor del atributo usuario
     * @param usuario El nuevo valor del atributo usuario.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Retorna una cadena de caracteres que representa la fecha dada.
     * @param fecha
     * @return cadena fecha actual en formato d/M/yyyy
     */
    protected String getFecha(Date fecha) {
        
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("d/M/yyyy");
        return formatoDeFecha.format(fecha);
    }
    
    
    protected String getFechaImpresion()
	{

		Calendar c = Calendar.getInstance();
		int dia, ano, hora;
		String min, seg, mes;

		dia = c.get(Calendar.DAY_OF_MONTH);
		mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
		ano = c.get(Calendar.YEAR);

		hora = c.get(Calendar.HOUR_OF_DAY);
		if (hora > 12)
			hora -= 12;

		min = formato(c.get(Calendar.MINUTE));
		seg = formato(c.get(Calendar.SECOND));

		String fechaImp =
			"Impreso el "
				+ dia
				+ " de "
				+ mes
				+ " de "
				+ ano
				+ " a las "
				+ formato(hora)
				+ ":"
				+ min
				+ ":"
				+ seg
				+ " "
				+ DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

		return fechaImp;
	}
    
    
        /* JAlcazar caso Mantis 04493 Acta - Requerimiento No 197 - Reimpresion de consultas
         * se crea el metodo get de la propiedad turno
         
        public Turno getTurno() {
		return turno;
	}*/
        
	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	public void setEsPropietario(boolean esPropietario) {
		this.esPropietario = esPropietario;
	}
}

package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.core.negocio.modelo.constantes.CTipoFormulario;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBaseFolio;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.imprimibles.util.Rupta;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
/**
* @Autor: Edgar Lora
* @Mantis 11599
* @Requerimiento 085_151
*/
import gov.sir.core.negocio.modelo.util.ComparadorCanceladoras;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.io.IOException;
/**
* @Autor: Edgar Lora
* @Mantis 11599
* @Requerimiento 085_151
*/
import java.util.*;

/**
 * @author gvillal
 * Clase que representa un Formulario de Calificacion o un Formulario de Corrección.
 */
public class ImprimibleFormulario  extends ImprimibleBaseFolio {
        private static final long serialVersionUID = 1L;
	/**Objeto que representa el turno con la información de los folios calificados o corregidos.**/
	//private Turno turno;

	/**Variable que identifica si el formulario es de calificacion o de correccción.**/
	private String tipo;
        private boolean changetextforregistrador = false;

	private boolean calificacionTemporal = false;
        private boolean formularioNoOficialCorreccionesEnabled = false;

	/**Username del usuario de SIR que realizo la ultima modificacion sobre el turno*/
	private String username;

        // Formulario Correcciones, usuario que realiza correccion
        private String formularioCorrecciones_UsuarioRealizaCorreccion;

	private byte[] pixelesImagenFirmaRegistrador = null;

	private String nombreRegistrador = null;
	private String cargoRegistrador = null;

	private String fechaImpresion = null;
	
	private String idUsuario = null;

	/**
	 * Constructor de la clase.
	 */
	public ImprimibleFormulario(Turno turno, String username ,String tipo, String fechaImpresion,String tipoImprimible)
	{
		super(tipoImprimible);
                /**
                * @Autor: Edgar Lora
                * @Mantis 11599
                * @Requerimiento 085_151
                */
                this.ordenarMatriculasAnotaciones(turno);
		setTransferObject(turno);
		this.turno = turno;
		this.tipo = tipo;
		this.simple = false;
		this.username=username;
		this.fechaImpresion = fechaImpresion;
	}

	/**
	 * Cambiar el id del usuario
	 */
	public void setIdUsuario(String idUsuario){
		this.idUsuario=idUsuario;
	}
   // // Bug 3436
   // :: Generaria opcion de impresion
   // protected String getMicroTexto( Folio folio, int numCaracteres ){
   //
   //       System.out.println( "@@ [Getting Micro from Subclass](" + CTipoFormulario.TIPO_CALIFICACION + "-" + this.tipo + ")" );
   //
   //       if( CTipoFormulario.TIPO_CALIFICACION.equals( this.tipo ) ) {
   //          return ImprimibleConstantes.SEPARADOR2;
   //       }
   //
   //       return super.getMicroTexto( folio, numCaracteres );
   //    }



	/**
	 * Imprime el encabezado del certificado, con un titulo predeterminado.
	 */
	protected void imprimirEncabezado()
	{

		String linea;
		String local_Msg = "";
		if (this.tipo.equals(CTipoFormulario.TIPO_CALIFICACION))
		{
			this.setImprimirDerivados(true);
			if(this.isCalificacionTemporal()){
				linea = StringFormat.getCentrada("FORMULARIO DE CALIFICACION TEMPORAL",ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,0);
				local_Msg   = ImprimibleConstantes.CVALIDEZ_TEMP; 
				this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,linea);
				this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,"");
			}else{
				linea = StringFormat.getCentrada("FORMULARIO DE CALIFICACION",ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,0);
				this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,linea);

				linea = StringFormat.getCentrada("CONSTANCIA DE INSCRIPCION",ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,0);
				this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,linea);
			}
		}
		if (this.tipo.equals(CTipoFormulario.TIPO_CORRECCION))
		{
                        String local_Title = "";
                        /**
                        * @author: David Panesso
                        * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                        * Impresión de los folios derivados de la anotación
                        **/
                        this.setImprimirDerivados(true);
                        if( !isFormularioNoOficialCorreccionesEnabled() ) {
                           local_Title = "FORMULARIO DE CORRECCION";
                        }
                        else {
                           local_Title = "FORMULARIO DE CORRECCION TEMPORAL";
                           local_Msg   = ImprimibleConstantes.CVALIDEZ_TEMP;
                        } // if

			linea = StringFormat.getCentrada( local_Title,ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,0);
			this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,linea);
			this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,"");
		 }

		if (this.tipo.equals(CTipoFormulario.TIPO_REPRODUCCION_SELLOS))
		{
			linea = StringFormat.getCentrada("REPRODUCCION DE CONSTANCIA DE INSCRIPCIÓN",ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,0);
			this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,linea);
			this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,"");
			this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,"");
		 }


		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO,"Página: " +this.getNumberOfPages());

		String fechaImp =this.fechaImpresion;
		fechaImp = StringFormat.getCentrada(fechaImp,ImprimibleConstantes.MAX_NUM_CHAR,10);
		this.imprimirLinea(ImprimibleConstantes.PLANO,fechaImp);
		
		/**Imprimir la frase que corresponde a el formulario de correciones y calificacion que son temporales*/
		if(local_Msg!=null || local_Msg!=""){
			linea = StringFormat.getCentrada(local_Msg,ImprimibleConstantes.MAX_NUM_CHAR,12);
		}else{
			linea = StringFormat.getCentrada(ImprimibleConstantes.CVALIDEZ,ImprimibleConstantes.MAX_NUM_CHAR,15);
		}
		
		this.imprimirLinea(ImprimibleConstantes.PLANO,linea);

		//this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		//this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");

	 }

    public boolean isChangetextforregistrador() {
        return changetextforregistrador;
    }

    public void setChangetextforregistrador(boolean changetextforregistrador) {
        this.changetextforregistrador = changetextforregistrador;
    }
	/**
	 * Metodo que Genera el vector de hojas imprimibles.
	 */
	public void generate(PageFormat pageFormat)
	{
        super.generate(pageFormat);
        this.imprimirEncabezado();


		this.imprimirCuerpo();
	}

	/**
	 * Metodo que imprime el cuerpo del formulario.
	 */
	private void imprimirCuerpo()
	{
		if (this.tipo.equals(CTipoFormulario.TIPO_CALIFICACION))
		  this.imprimirMatriculas(" se calificaron las siguientes matrículas:");

		if (this.tipo.equals(CTipoFormulario.TIPO_CORRECCION))
		  this.imprimirMatriculas(" se corrigieron las siguientes matrículas:");

	  	if (this.tipo.equals(CTipoFormulario.TIPO_CALIFICACION))
		  this.imprimirInfoFoliosCalificados(this.getListaFolios());
		else if (this.tipo.equals(CTipoFormulario.TIPO_CORRECCION))
		  this.imprimirInfoFoliosCorregidos(this.getListaFolios());

		if (this.tipo.equals(CTipoFormulario.TIPO_REPRODUCCION_SELLOS)){
			String tid = this.turno.getIdWorkflow();
			
			final List liquidaciones = this.turno.getSolicitud().getLiquidaciones();
			boolean esRupta = false;
			
			for(int i = 0, j = liquidaciones.size();i < j; i++){
				esRupta = Rupta.liquidacionEsRupta((Liquidacion)liquidaciones.get(i));
				if (esRupta) break;
			}
			
			String linea = "Con el turno "+tid + (esRupta ? Rupta.SIGLA : "");
			
			this.imprimirLinea(ImprimibleConstantes.TITULO2,linea);
			this.imprimirInfoFoliosCalificados(this.getListaFolios());
		}

		int tamFinDoc = getTamanoFinDocumento();
		if(!this.isCalificacionTemporal()&&!isFormularioNoOficialCorreccionesEnabled()){
			this.imprimirFinDocumento(tamFinDoc);
		}else{
			imprimirFinDocumentoCalificacionCorrecion(tamFinDoc);
		}

        //this.imprimirFinDocumento();

		if(!this.isCalificacionTemporal()&&!isFormularioNoOficialCorreccionesEnabled()){			
			this.imprimirFirma();
		}

		this.setFlagNuevapagina(1);
		this.imprimirInfoModificador(this.username);
		this.setFlagNuevapagina(0);
	}

/**Imprime los el final de los formularios de calificacion o correccion*/
	 protected void imprimirFinDocumentoCalificacionCorrecion( int tamFinDocumento ) {
		 imprimirFinDocumentoCalificacionCorrecion( tamFinDocumento, true );
	   }
		/**
		 * Imprime las ultimas lineas del Certificado.
		 */
		protected void imprimirFinDocumentoCalificacionCorrecion(int tamFinDocumento, boolean checkPageBreak) {		
			if( ( checkPageBreak )
	                  &&( this.getI() >= (ImprimibleConstantes.MAXIMO_VERTICAL - tamFinDocumento) ) ) {
	                    //System.out.println(" @@ " + "Break Page");
	                    //System.out.println( "this.getI():" + this.getI() );
	                    //System.out.println( "tamFinDocumento:" + tamFinDocumento );
	                    //System.out.println( "ImprimibleConstantes.MAXIMO_VERTICAL" + ImprimibleConstantes.MAXIMO_VERTICAL );
	                    this.goPageEnd();
			} // if
			this.imprimirLinea(ImprimibleConstantes.PLANO, "");
			this.imprimirLinea(this.tituloMicro, this.microTexto);
			//this.imprimirLinea(ImprimibleConstantes.plano,"");
			//this.imprimirLinea(this.tituloMicro, this.microTexto);
	 		this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.SEPARADOR1);
			this.imprimirLinea(
				ImprimibleConstantes.TITULO1,
				"                    F I N   D E  E S T E  D O C U M E N T O");
	}



	/**
	 * Imprime el usuario que realizo la ultima modificacion.
	 * usuario que suario que realizo la calificacion:
	 * @param string
	 */
	private void imprimirInfoModificador(String string) {

                String usuarioActividad;
		String linea="";

		if( this.tipo.equals(CTipoFormulario.TIPO_CALIFICACION) ) {

                  usuarioActividad = this.username;
                  linea = "Usuario que realizo la calificacion: " + idUsuario;
		}
		else if (this.tipo.equals(CTipoFormulario.TIPO_CORRECCION)) {

                  // bug 3578
                  // cambiar el usuario que realiza actividad de correccion

                  // usuarioActividad = this.username ;
                  usuarioActividad = formularioCorrecciones_UsuarioRealizaCorreccion ;
				  linea = "Usuario que realizo la correccion: " + idUsuario;
                  

		}
		else if (this.tipo.equals(CTipoFormulario.TIPO_REPRODUCCION_SELLOS)) {

                  // bug 3422
                  // cambiar el mensaje que aparece en la parte inferior

                  usuarioActividad = this.username;
                  linea = "Usuario que realizó la reproducción: " + usuarioActividad;
                } // :if



		this.imprimirLinea(ImprimibleConstantes.TITULO2,linea);

	}



	/**
	 * Retorna la lista de folio asociados al turno.
	 * @return
	 */        
        private List getListaFolios()
	{
		Vector folios = new Vector();
		List solicitudesFolios = turno.getSolicitud().getSolicitudFolios();
		for(int i=0; i<solicitudesFolios.size(); i++)
		{
			SolicitudFolio solFolio = (SolicitudFolio)solicitudesFolios.get(i);
			Folio folio = solFolio.getFolio();
			folios.add(folio);
		}

		return folios;
	}

	/**
	 * Metodo que imprime la información de los folios.
	 * @param folios lista de folios de tipo <Folio>.
	 */
	private void imprimirInfoFoliosCalificados(List folios)
	{
		this.imprimirLinea(ImprimibleConstantes.TITULO2,"");

	    for(int i=0; i<folios.size(); i++)
	    {
	    	Folio folio = (Folio)folios.get(i);
	    	this.imprimirFolioTopResumen(folio);
			this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.SEPARADOR1);
			this.imprimirLinea(ImprimibleConstantes.PLANO,"");
			this.imprimirDirecciones(folio,true);
			this.imprimirAnotaciones(folio);
	    }

	}

	/**
	 * Metodo que imprime la información de los folios.
	 * @param folios lista de folios de tipo <Folio>.
	 */
	private void imprimirInfoFoliosCorregidos(List folios)
	{
		this.imprimirLinea(ImprimibleConstantes.TITULO2,"");

		String line = "";
		for(int i=0; i<folios.size(); i++)
		{
			Folio folio = (Folio)folios.get(i);
			//this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.SEPARADOR1);
			this.imprimirFolioTopResumen(folio);
			this.imprimirEstadoFolio(folio);
			//this.imprimirCodCatastralFolio(folio);
			line =
				this.getFechaAperturaFolio(folio)+
				this.getRadicacionFolio(folio)+
				this.getTipoDocumentoFolio(folio)+
				this.getFechaTipoDocumentoFolio(folio);
		    this.imprimirLinea(ImprimibleConstantes.PLANO,line);


			// this.imprimirLinea(ImprimibleConstantes.PLANO,"");
			// this.imprimirSalvedadesFolio(folio);
			// this.imprimirLinea(ImprimibleConstantes.PLANO,"");

			this.imprimirLinderoFolio(folio);
			
			//this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.SEPARADOR2);
			this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.SEPARADOR1);
			this.imprimirLinea(ImprimibleConstantes.PLANO,"");
			this.imprimirDirecciones(folio,true);
			this.imprimirComplementacionFolio(folio);
			this.setAnotacionToCorrecion(folio.getAnotaciones());
			this.imprimirAnotacionesFormulario(folio, false);

                        // se imprimen las salvedades al final
                        // @see reporte 13 (imprimible)
                        this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.SEPARADOR2);

                        // bug 3565
                        this.imprimirSalvedadesFolioCorreccion( folio,true,false );

                        this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.SEPARADOR2);

                        //this.imprimirLinea(ImprimibleConstantes.PLANO,line);

			//this.imprimirLinea(ImprimibleConstantes.TITULO2,"");
		}

	}

	/**
	 * Imprime las salvedades del folio en caso de que sea un formulario de correcciones.
	 * @param folio
	 */
	protected void imprimirSalvedadesFolioCorreccion(Folio folio, boolean imprimirSalvedadesAnotacion, boolean print_SalvedadUsuarioEnabled) {
		// TODO: arreglar

		List salvedades = folio.getSalvedades();
		String linea = "";


		//SI EL FOLIO TENIA SALVEDADES O ALGUNA DE SUS ANOTACIONES
		if( ( ( null != salvedades ) && ( salvedades.size() > 0 ) ) || hasSalvedadesAnotaciones ) {
			linea = "SALVEDADES: (Información Anterior o Corregida)";
			this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);
		}

		if( ( null != salvedades )
			&&( salvedades.size() > 0 ) ) {


			// variables para mantener cache de cada salvedad --

			SalvedadFolio salvedad;

			String ordenAnota;
			String salvedadId;
			String radicacion;
			String fechaSalve;
			String usuarioCreacionSalvedad = null;
            String local_SalvedadUsuario;

			// ----------------------------------------------------

			for (int i = 0; i < salvedades.size(); i++) {

				salvedad = (SalvedadFolio) salvedades.get(i);

				// para que no genere errores al imporimir datos de salvedad.
				if( null == salvedad )
					break;

				ordenAnota = "0";
				salvedadId = "";
				radicacion = "";
				fechaSalve = "";
				usuarioCreacionSalvedad = "";


				salvedadId = salvedad.getIdSalvedad();
				fechaSalve = this.getFecha(salvedad.getFechaCreacion());
				radicacion = salvedad.getNumRadicacion();
				
                local_SalvedadUsuario = getSalvedadUsuario( salvedad );
                if(salvedad!=null){
	                if(salvedad.getUsuarioCreacion()!=null && salvedad.getUsuarioCreacion().getIdUsuario()!=0){
	                	usuarioCreacionSalvedad = new Long(salvedad.getUsuarioCreacion().getIdUsuario()).toString();
	                }
	                if(usuarioCreacionSalvedad==null||usuarioCreacionSalvedad.equals("")){
	                	if(salvedad.getUsuarioCreacionTMP()!=null && salvedad.getUsuarioCreacionTMP().getIdUsuario()!=0){
	                		usuarioCreacionSalvedad =  new Long(salvedad.getUsuarioCreacionTMP().getIdUsuario()).toString();
	                	}
	                }
	            }
                
				if( null == fechaSalve )
					fechaSalve = "";

				if( null == salvedadId )
					salvedadId = "";

				if( null == radicacion )
					radicacion = "";

					linea="["+usuarioCreacionSalvedad+"]: "+
					"Anotación Nro: "
						+ ordenAnota
						+ " No. corrección: "
						+ salvedadId
						+ " Radicación: "
						+ radicacion
						+ " Fecha: "
						+ fechaSalve;
				
				this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);

				String descripcion = salvedad.getDescripcion();
				this.imprimirLinea(ImprimibleConstantes.PLANO, descripcion);
				//this.imprimirLinea(ImprimibleConstantes.plano,"");
			}
			//TFS 4419: SE DEBE IMPRIMIR EL ID DEL USUARIO QUE REALIZO LA CORRECCION, NO EL QUE LA APROBO
			if(usuarioCreacionSalvedad!=null && !usuarioCreacionSalvedad.equals(""))
				this.idUsuario = usuarioCreacionSalvedad;
		}

		// para cada anotacion, imprime el conjunto de
		// salvedades

        if( imprimirSalvedadesAnotacion ){

          List anotaciones = folio.getAnotaciones();
          for (int i = 0; i < anotaciones.size(); i++) {
            Anotacion anota = (Anotacion) anotaciones.get(i);
            if (null != anota)
              this.imprimirSalvedadesAnotacionCorreccion(anota, print_SalvedadUsuarioEnabled);
          }
        }
	}

	/**
	 * Imprime las salvedades de la anotación en caso de que sea un formulario de correcciones.
	 * @param anota
	 */
	protected void imprimirSalvedadesAnotacionCorreccion(Anotacion anota, boolean print_SalvedadUsuarioEnabled) {

		List salvedades = anota.getSalvedades();

                //System.out.println( "// # Salvedades: " + ( (null!=salvedades) ? (""+salvedades.size()):("null")) );


		if (salvedades == null)
			return;

		if (salvedades.size() <= 0)
			return;


		// MemPtrs:

		String ordenAnota;
		String salvedadId;
		String radicacion;
		String fechaSalve;
		String descripcion;
		String usuarioCreacionSalvedad = null;
        String local_SalvedadUsuario;



                SalvedadAnotacion salvedad;

		for (int i = 0; i < salvedades.size(); i++) {
            salvedad = (SalvedadAnotacion) salvedades.get(i);

            //System.out.println( "// # Salvedad("+i+"): " + ( (null!=salvedad) ? (""+salvedad.getDescripcion()):("null")) );


			ordenAnota = null;
			salvedadId = null;
			radicacion = null;
			fechaSalve = null;
			usuarioCreacionSalvedad = "";
            local_SalvedadUsuario = null;

			ordenAnota = anota.getOrden();
			salvedadId = salvedad.getIdSalvedad();
			radicacion = salvedad.getNumRadicacion();
			fechaSalve = this.getFecha(salvedad.getFechaCreacion());
			descripcion = salvedad.getDescripcion();
            local_SalvedadUsuario = getSalvedadUsuario( salvedad );

            if(salvedad!=null){
                if(salvedad.getUsuarioCreacion()!=null && salvedad.getUsuarioCreacion().getIdUsuario()!=0){
                	usuarioCreacionSalvedad = new Long(salvedad.getUsuarioCreacion().getIdUsuario()).toString();
                }
                if(usuarioCreacionSalvedad==null||usuarioCreacionSalvedad.equals("")){
                	if(salvedad.getUsuarioCreacionTMP()!=null && salvedad.getUsuarioCreacionTMP().getIdUsuario()!=0){
                		usuarioCreacionSalvedad =  new Long(salvedad.getUsuarioCreacionTMP().getIdUsuario()).toString();
                	}
                }
            }

			if( null == ordenAnota )
				ordenAnota = "";

			if( null == salvedadId )
				salvedadId = "";

			if( null == radicacion )
				radicacion = "";

			if( null == fechaSalve )
				fechaSalve = "";

			if( null == descripcion )
				descripcion = "";

			String linea ="["+usuarioCreacionSalvedad+"]: "
					+" Anotación Nro: "
					+ ordenAnota
					+ " No. corrección: "
					+ salvedadId
					+ " Radicación: "
					+ radicacion
					+ " Fecha: "
					+ fechaSalve;

			if(this.isImprimirUsuarioSalvedad()){
				linea = linea + usuarioCreacionSalvedad;
			}			
			
			this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);
			this.imprimirLinea(ImprimibleConstantes.PLANO, descripcion);
			//this.imprimirLinea(ImprimibleConstantes.plano,"");
			//if (i<(salvedades.size()-1))
			//this.imprimirLinea(ImprimibleConstantes.plano,"");
		}
		
		//TFS 4419: SE DEBE IMPRIMIR EL ID DEL USUARIO QUE REALIZO LA CORRECCION, NO EL QUE LA APROBO
		if(usuarioCreacionSalvedad!=null && !usuarioCreacionSalvedad.equals(""))
			this.idUsuario = usuarioCreacionSalvedad;

	}
	
	
	/**
	 * Imprime el estado del folio.
	 * @param folio
	 */
	private void imprimirEstadoFolio(Folio folio)
	{
		String estado = "";
		try
		{
			estado=folio.getEstado().getNombre();
		}
		catch (Throwable t)
		{
		  estado = "";
		}
		if (estado==null)
		  estado="";

        if (!estado.equals(""))
		{

			this.imprimirLinea(ImprimibleConstantes.PLANO,"ESTADO DEL FOLIO:",false);
			this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE,130,estado);
		}

	}

    /**
     * Imprime el codigo catastral del folio.
     * @param folio
     */
	private void imprimirCodCatastralFolio(Folio folio)
	{
		String codCatas = "";
		try
		{
			codCatas=folio.getCodCatastral();
		}
		catch (Throwable t)
		{
			codCatas = "";
		}
		if (codCatas==null)
		  codCatas = "";

		if (!codCatas.equals(""))
		{
			this.imprimirLinea(ImprimibleConstantes.PLANO,"COD CATASTRAL: "+codCatas);
		}

	}


    /**
     * Imprime la fecha de apertura del folio.
     * @param folio
     * @return
     */
	private String getFechaAperturaFolio(Folio folio)
	{
		String fecha = "";
		Date fechaAper;
		String line = "";
		try
		{
			fechaAper=folio.getFechaApertura();
			fecha=this.getFecha(fechaAper);
		}
		catch (Throwable t)
		{
			fecha = "";
		}
		if (fecha==null)
		  fecha="";

		if (!fecha.equals(""))
		{
			line =	"FECHA APERTURA: "+	fecha;
			//this.imprimirLinea(ImprimibleConstantes.plano,line);
		}

		return line;

	}


    /**
     * Retorna el numero de radicacion del folio.
     * @param folio
     * @return
     */
	private String getRadicacionFolio(Folio folio)
	{
		String radica = "";
		String line = "";
		try
		{
			radica=folio.getRadicacion();
		}
		catch (Throwable t)
		{
			radica = "";
		}

		if (radica==null)
		  radica = "";

		line =	" RADICACION: "+	radica;

		return line;

	}

    /**
     * Retorna el nombre del tipo de documento del folio.
     * @param folio
     * @return
     */
	private String getTipoDocumentoFolio(Folio folio)
	{
		String tipoDoc = "";
		String line = "";
		try
		{
			tipoDoc=folio.getDocumento().getTipoDocumento().getNombre();
		}
		catch (Throwable t)
		{
			tipoDoc = "";
		}
        if (tipoDoc==null)
          tipoDoc= "";

		line =	" CON: "+tipoDoc;

		return line;

	}

    /**
     * Retorna la fecha del tipo de documento del folio.
     * @param folio
     * @return
     */
	private String getFechaTipoDocumentoFolio(Folio folio)
	{
		String fecha = "";
		String line = "";
		try
		{
			Date fechaDoc =folio.getDocumento().getFecha();
			fecha= this.getFecha(fechaDoc);
		}
		catch (Throwable t)
		{
			fecha = "";
		}

		if (fecha==null)
		  fecha= "";

		if (!fecha.equals(""))
		{
			line =	" DE: "+fecha;
		}

		return line;

	}

    /**
     * Imprime el lindero del folio.
     * @param folio
     */
	private void imprimirLinderoFolio(Folio folio)
	{
		String lindero = "";
		try
		{
			lindero=folio.getLindero();
		}
		catch (Throwable t)
		{
			lindero = "";
		}
        if (lindero==null)
          lindero="";

		if (!lindero.equals(""))
		{
			this.imprimirLinea(ImprimibleConstantes.TITULO2,"DESCRIPCION: CABIDA Y LINDEROS ");
			this.imprimirLinea(ImprimibleConstantes.PLANO,lindero);

		}

	}

    /**
     * Imprime la complementacion del folio.
     * @param folio
     */
	private void imprimirComplementacionFolio(Folio folio)
	{
		String complementacion = "";
		try
		{
			complementacion=folio.getComplementacion().getComplementacion();
		}
		catch (Throwable t)
		{
			complementacion = "";
		}

		if(complementacion != null)
		{
			if (!complementacion.equals(""))
			{
				this.imprimirLinea(ImprimibleConstantes.TITULO2,"COMPLEMENTACION: ");
				this.imprimirLinea(ImprimibleConstantes.PLANO,complementacion);
	
			}
		}
	}




	/**
	 * Imprime la lista de matrículas que se fueron corregidas o calificadas en el turno.
	 * @param complemento cadena de texto que hace parte del formulario, depende del tipo
	 * de formulario.
	 */
	protected void imprimirMatriculas(String complemento)
	{
		this.imprimirLinea(ImprimibleConstantes.TITULO2,"");

		String tid = this.turno.getIdWorkflow();
		String linea = "Con el turno "+tid+complemento;
		this.imprimirLinea(ImprimibleConstantes.TITULO2,linea);

		Solicitud solicitud = (Solicitud)this.turno.getSolicitud();
		if (solicitud==null)
		{
			System.out.println("Solicitud es NULL");
			return;
		}


		List folios=solicitud.getSolicitudFolios();

		if (folios==null)
		{
			System.out.println("Folios es NULL");
			return;
		}

		String matriculas="";
                /**
                * @author: David Panesso
                * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                * Solo se mostraran las matriculas originales, y no las sergregadas o englobadas
                * Folios que sean de tipo derivados no serán mostrados
                **/
                for (int i = 0; i < folios.size(); i++) {
                    SolicitudFolio solFolio = (SolicitudFolio) folios.get(i);
                    Folio folio = solFolio.getFolio();
                    if (folio != null) {
                        List anotaciones = folio.getAnotaciones();
                        boolean esFolioDerivado = false;
                        for (int k = 0; k < anotaciones.size(); k++) {
                            Anotacion a = (Anotacion) anotaciones.get(k);
                            if(a.getTipoAnotacion().getIdTipoAnotacion().equals(CTipoAnotacion.DERIVADO)){
                                esFolioDerivado = true;
                            }
                        }
                        if (!esFolioDerivado) {
                            matriculas += folio.getIdMatricula() + "     ";
                        }
                    }
                }
		this.imprimirLinea(ImprimibleConstantes.TITULO2,matriculas);
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

		this.imprimirLinea(this.tituloMicro, ImprimibleConstantes.SEPARADOR3_PEQ);
		this.imprimirGrafico(imagenFirmaRegistrador);

		int i = this.getI();
		i += altura;
		this.setI(i);
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
                String linea = "";
                if(this.isChangetextforregistrador()){
                         linea = "REGISTRADOR DE INSTRUMENTOS PUBLICOS";

                }else{
                             linea = "El registrador ";

                        if (this.cargoRegistrador != null) {
                                linea += (this.cargoRegistrador + " ");
                        }

                        if (this.nombreRegistrador != null) {
                                linea += this.nombreRegistrador;
                        }
                }
		

		this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
		this.imprimirLinea(this.tituloMicro, ImprimibleConstantes.SEPARADOR3_PEQ);
	}

	/**
	 * Actualizar el valor del atributo cargoRegistrador
	 * @param cargoRegistrador El nuevo valor del atributo cargoRegistrador.
	 */
	public void setCargoRegistrador(String cargoRegistrador) {
		this.cargoRegistrador = cargoRegistrador;
	}

	/**
	 * @return
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @return
	 */
	public boolean isCalificacionTemporal() {
		return calificacionTemporal;
	}

	public String getFormularioCorrecciones_UsuarioRealizaCorreccion() {
		return formularioCorrecciones_UsuarioRealizaCorreccion;
	}

	public boolean isFormularioNoOficialCorreccionesEnabled() {
		return formularioNoOficialCorreccionesEnabled;
	}

	/**
	 * @param b
	 */
	public void setCalificacionTemporal(boolean b) {
		calificacionTemporal = b;
	}

	public void setFormularioCorrecciones_UsuarioRealizaCorreccion(String
		 formularioCorrecciones_UsuarioRealizaCorreccion) {
		this.formularioCorrecciones_UsuarioRealizaCorreccion =
			 formularioCorrecciones_UsuarioRealizaCorreccion;
	}

	public void setFormularioNoOficialCorreccionesEnabled(boolean
		 formularioNoOficialCorreccionesEnabled) {
		this.formularioNoOficialCorreccionesEnabled =
			 formularioNoOficialCorreccionesEnabled;
	}
        
        /**
         * @Autor: Edgar Lora
         * @Mantis 11599
         * @Requerimiento 085_151
         * @param t 
         */
        private void ordenarMatriculasAnotaciones(Turno t){
            Solicitud s = t.getSolicitud();
            List solicitudesFolio = s.getSolicitudFolios();
            for(int i = 0; i < solicitudesFolio.size(); i = i + 1){
                SolicitudFolio sf = (SolicitudFolio) solicitudesFolio.get(i);
                Folio f = sf.getFolio();
                if(f != null){                    
                    List anotaciones = f.getAnotaciones();
                    Collections.sort(anotaciones, new ComparadorCanceladoras());
                    for(int k = 0; k < anotaciones.size(); k = k + 1){                        
                        Anotacion a = (Anotacion) anotaciones.get(k);                        
                        List canceladoras = a.getAnotacionesCancelacions();
                        Collections.sort(canceladoras, new ComparadorCanceladoras());
                        
                        List foliosHijos = a.getAnotacionesHijos();
                        Collections.sort(foliosHijos, new ComparadorCanceladoras());
                        
                        List foliosPadres = a.getAnotacionesPadre();
                        Collections.sort(foliosPadres, new ComparadorCanceladoras());
                    }
                }
            }
        }
}

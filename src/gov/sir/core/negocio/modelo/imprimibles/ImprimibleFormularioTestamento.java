package gov.sir.core.negocio.modelo.imprimibles;

import java.awt.print.PageFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadano;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CTipoFormulario;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
import gov.sir.core.util.DateFormatUtil;
import java.util.Map;

/**
 * @author ppabon
 * Clase que representa el Imprimible de una constancia de inscripción de testamentos.
 */
public class ImprimibleFormularioTestamento extends ImprimibleBase {
        private static final long serialVersionUID = 1L;
	protected int tituloMicro = ImprimibleConstantes.TITULO_MICRO;
        private String idTurnoTestamento;
	private Testamento testamento;
	private String turno;
	private String nombreCirculo;
	private String fechaImpresion;
	private Date fecha;
	private String nombreRegistrador = null;
	private String cargoRegistrador = null;
	private Usuario usuarioSIR = null;
	private Documento documento=null;
        private String tipo;
        private boolean formularioNoOficialCorreccionesEnabled;
        private String idCirculo;
        private List salvedades;

	/**
	 * Constructor de la clase.
	 * @param nota
	 */
	public ImprimibleFormularioTestamento(Testamento testamento, Documento documento , String turno, String nombreCirculo, String fechaImpresion, Usuario usuarioSIR , Date fecha, String tipoImprimible) {
		super(tipoImprimible);
		this.testamento = testamento;
		this.turno = turno;
		this.documento = documento;
		this.usuarioSIR = usuarioSIR;
		this.nombreCirculo = nombreCirculo;
		this.fechaImpresion = fechaImpresion;
		this.fecha = fecha;
	}
        public ImprimibleFormularioTestamento(Testamento testamento, Documento documento , String turno, String nombreCirculo, String fechaImpresion, Usuario usuarioSIR , Date fecha, String tipoImprimible,String tipo) {
		super(tipoImprimible);
		this.testamento = testamento;
		this.turno = turno;
		this.documento = documento;
		this.usuarioSIR = usuarioSIR;
		this.nombreCirculo = nombreCirculo;
		this.fechaImpresion = fechaImpresion;
		this.fecha = fecha;
                this.tipo = tipo;
	}

	/**
	 * Genera el imprimible.
	 */
	public void generate(PageFormat pageFormat) {

		super.generate(pageFormat);
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");

		String sDoc = this.crearLineaDocumento(this.documento);
		this.imprimirLinea(ImprimibleConstantes.TITULO2, sDoc );
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
                this.imprimirLinea(ImprimibleConstantes.TITULO2, "TURNO TESTAMENTO: "+idTurnoTestamento);
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
                
		List testadores = testamento.getTestadores();
		for(int i=0; i<testadores.size(); i++){
			Ciudadano ciudadano = ((Ciudadano)testadores.get(i));
			this.imprimirTestador(ciudadano);
		}
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "");

		this.imprimirLinea(ImprimibleConstantes.TITULO2, "El documento fue registrado en el libro de testamentos" , false);

		this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "");
		this.imprimirLinea(ImprimibleConstantes.TITULO2, "");

		this.imprimirLinea(ImprimibleConstantes.TITULO2, "TOMO :" , false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, testamento.getTomo());

		this.imprimirLinea(ImprimibleConstantes.TITULO2, "NÚMERO ANOTACION :" , false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, testamento.getNumeroAnotaciones());

		this.imprimirLinea(ImprimibleConstantes.TITULO2, "FECHA :" , false);
		String fechaActual  = this.formatear(fecha , "dd/MM/yyyy");
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4,fechaActual);

		this.imprimirLinea(ImprimibleConstantes.TITULO2, "REVOCA ESCRITURA :" , false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, testamento.getRevocaEscritura());

		this.imprimirLinea(ImprimibleConstantes.TITULO2, "OBSERVACIONES");
		Vector lineas = this.getLineas(this.testamento.getObservacion());
		for (int i = 0; i < lineas.size(); i++) {
			String linea = (String) lineas.get(i);
			this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
		}
                this.imprimirLinea(ImprimibleConstantes.PLANO, "");
                this.imprimirSalvedadesTestamentoCorreccion();
		this.imprimirFirmaNotaDevolutiva();

	}

	/**
	 * Imprime el nombre del testador.
	 * @param sol La solicitud.
	 */
	protected void imprimirTestador(Ciudadano ciudadano) {
		String completo = this.getNombreCompletoConId(ciudadano);

		this.imprimirLinea(ImprimibleConstantes.TITULO2, "TESTADOR :", false);
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, completo);

	}

	/**
	 * Imprime el encabezado de la nota devolutiva.
	 */
	protected void makeNewPage() {
		super.makeNewPage();
                this.imprimirEncabezado();
		this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "TURNO: " + this.turno);

	}

	/**
	 * Funcion que recibe una cadena de texto que contiene caracteres especiales de fin de linea '\r' y '\n'
	 * indicando que se debe generar una nueva linea en un texto.
	 * @param line Linea con todo el texto.
	 * @return un vector de Strings con el mismo texto original, pero cada elemento del vector de respuesta
	 * representa un renglón en el texto.
	 */
	private Vector getLineas(String line) {

		String cad = "";
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) != '\r')
				cad += line.charAt(i);
		}

		String cadenas[] = cad.split("\n");

		int tam = cadenas.length;
		String cad2 = "";

		Vector vectorCad = new Vector();
		for (int i = 0; i < tam; i++) {
			vectorCad.add(cadenas[i]);

		}

		return vectorCad;
	}

	/**
	 * Método que imprime la firma al final del documento
	 * @param imagenFirmaRegistrador
	 */
	private String crearLineaDocumento(Documento doc) {
		String tDoc = "";

		if(doc!=null){

			TipoDocumento tipoDoc = doc.getTipoDocumento();
			OficinaOrigen oficinaOrigen = doc.getOficinaOrigen();
			String oficinaInternacional = doc.getOficinaInternacional();

			tDoc =  (tipoDoc!=null&&tipoDoc.getNombre()!=null?tipoDoc.getNombre():" ") + " " +doc.getNumero() + " del "+ (doc.getFecha()!=null? this.formatear(doc.getFecha(),"dd/MM/yyyy"):"") ;

			if(oficinaInternacional!=null && !oficinaInternacional.equals("")){
				 tDoc = tDoc + " de "+  oficinaInternacional ;
			}else{

				if(oficinaOrigen!=null){

					Vereda vereda= oficinaOrigen.getVereda();
					String ubicacion = "";

					if(vereda!=null){
						Municipio mun = vereda.getMunicipio();

						if(mun!=null){
							Departamento depto = mun.getDepartamento();
							if(depto !=null){
								ubicacion = " de " + mun.getNombre() + " - " + depto.getNombre();
							}else{
								ubicacion = " de " + mun.getNombre();
							}

						}else{
							ubicacion = " de " + vereda.getNombre();
						}

					}

					tDoc =  tDoc + " de "+ (oficinaOrigen!=null&&oficinaOrigen.getNombre()!=null?oficinaOrigen.getNombre():" ") + ubicacion;
				}


			}


		}


		return tDoc!=null?tDoc.toUpperCase():tDoc;

	}


	public  String formatear(Date fecha, String formato){
		if (formato != null){
			return DateFormatUtil.format(formato, fecha);
		}
		else{
			return fecha.toString();
		}
	}

	protected void imprimirFirmaNotaDevolutiva() {

		String calificador = "FUNCIONARIO QUE EFECTUO EL REGISTRO";
		String registrador = "REGISTRADOR DE INSTRUMENTOS PUBLICOS";
		String linea ="==============================================================================================================";
		String finDocumento ="                      FIN DE ESTE DOCUMENTO";


		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,"");

		this.imprimirLinea(ImprimibleConstantes.TITULO2, calificador, false);
		this.imprimirLinea(ImprimibleConstantes.TITULO2, 270 , registrador, true);

		String nombreCalificador = "  " + (usuarioSIR!=null&&""+usuarioSIR.getIdUsuario()!=null?""+usuarioSIR.getIdUsuario():"");
		this.imprimirLinea(ImprimibleConstantes.TITULO2, nombreCalificador);

		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,linea);
		this.imprimirLinea(ImprimibleConstantes.TITULO1,finDocumento);

	}
/**
	 * Imprime el encabezado del certificado, con un titulo predeterminado.
	 */
	protected void imprimirEncabezado()
	{

		String linea;
		String local_Msg = "";
		if (this.tipo.equals(CTipoFormulario.TIPO_CORRECCION))
		{
                        String local_Title = "";
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
        /**
	 * Imprime las salvedades del folio en caso de que sea un formulario de correcciones.
	 * @param folio
	 */
	protected void imprimirSalvedadesTestamentoCorreccion() {
		// TODO: arreglar

		
		String linea = "";


		//SI EL FOLIO TENIA SALVEDADES O ALGUNA DE SUS ANOTACIONES
		if( ( ( null != salvedades ) && ( salvedades.size() > 0 ) ) || hasSalvedadesAnotaciones ) {
			linea = "SALVEDADES: (Información Anterior o Corregida)";
			this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);
		}

		if( ( null != salvedades )
			&&( salvedades.size() > 0 ) ) {


			// variables para mantener cache de cada salvedad --

			Map salvedad;

			String salvedadId;
			String radicacion;
			String fechaSalve;
			String usuarioCreacionSalvedad = null;
            String local_SalvedadUsuario;

			// ----------------------------------------------------

			for (int i = 0; i < salvedades.size(); i++) {

				salvedad = (Map) salvedades.get(i);

				// para que no genere errores al imporimir datos de salvedad.
				if( null == salvedad )
					break;

				salvedadId = "";
				radicacion = "";
				fechaSalve = "";
				usuarioCreacionSalvedad = "";


				salvedadId = (String)salvedad.get("ID_SALVEDAD_TS");
				fechaSalve = this.getFecha((Date)salvedad.get("SLTS_FECHA_CREACION"));
				radicacion = (String)salvedad.get("SLTS_RADICACION");
				
                if(salvedad!=null){
	                if(salvedad.get("ID_USUARIO")!=null && !salvedad.get("ID_USUARIO").equals(0)){
	                	usuarioCreacionSalvedad = salvedad.get("ID_USUARIO").toString();
	                }
	                if(usuarioCreacionSalvedad==null||usuarioCreacionSalvedad.equals("")){
	                	if(salvedad.get("ID_USUARIO_TMP")!=null && !salvedad.get("ID_USUARIO_TMP").equals(0)){
	                		usuarioCreacionSalvedad =  salvedad.get("ID_USUARIO_TMP").toString();
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
					" No. corrección: "
						+ salvedadId
						+ " Radicación: "
						+ radicacion
						+ " Fecha: "
						+ fechaSalve;
				
				this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);

				String descripcion = (String)salvedad.get("SLTS_DESCRIPCION");
				this.imprimirLinea(ImprimibleConstantes.PLANO, descripcion);
				//this.imprimirLinea(ImprimibleConstantes.plano,"");
			}
		}

		// para cada anotacion, imprime el conjunto de
		// salvedades

	}
	/**
	 * @return
	 */
	public String getCargoRegistrador() {
		return cargoRegistrador;
	}

	/**
	 * @return
	 */
	public String getNombreRegistrador() {
		return nombreRegistrador;
	}


	/**
	 * @param string
	 */
	public void setCargoRegistrador(String string) {
		cargoRegistrador = string;
	}

	/**
	 * @param string
	 */
	public void setNombreRegistrador(String string) {
		nombreRegistrador = string;
	}
        
        public boolean isFormularioNoOficialCorreccionesEnabled() {
		return formularioNoOficialCorreccionesEnabled;
	}
        
        public void setFormularioNoOficialCorreccionesEnabled(boolean	 formularioNoOficialCorreccionesEnabled) {
		this.formularioNoOficialCorreccionesEnabled = formularioNoOficialCorreccionesEnabled;
	}

        public String getTurno() {
            return turno;
        }

        public void setTurno(String turno) {
            this.turno = turno;
        }

        public String getIdCirculo() {
            return idCirculo;
        }

        public void setIdCirculo(String idCirculo) {
            this.idCirculo = idCirculo;
        }

        public String getIdTurnoTestamento() {
            return idTurnoTestamento;
        }

        public void setIdTurnoTestamento(String idTurnoTestamento) {
            this.idTurnoTestamento = idTurnoTestamento;
        }

        public List getSalvedades() {
            return salvedades;
        }

        public void setSalvedades(List salvedades) {
            this.salvedades = salvedades;
        }

}

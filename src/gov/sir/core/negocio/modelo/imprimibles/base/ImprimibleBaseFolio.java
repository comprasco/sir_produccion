package gov.sir.core.negocio.modelo.imprimibles.base;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Cancelacion;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.EstadoAnotacion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.SalvedadFolio;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFolioSimple;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormulario;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleHojaDeRuta;

import gov.sir.core.negocio.modelo.util.RolAnotaCiudadanoComparator;
import gov.sir.core.negocio.modelo.constantes.CEstadoAnotacion;
import gov.sir.core.negocio.modelo.constantes.CTipoFormulario;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author gvillal
 * Clase que representa el imprimible de la información de un folio.
 *
 */
public abstract class ImprimibleBaseFolio extends ImprimibleBase {

	/**
	 * @param tipoImprimible
	 */
	public ImprimibleBaseFolio(String tipoImprimible) {
		super(tipoImprimible);
	}
        private static final long serialVersionUID = 1L;
	protected String microTexto = "";
	/////protected String microTexto2 = "";
	
	protected String TEXTO_ASTERISCO =
		"***************************************************************************" +
		"***************************************************************************" +
		"**********";

	private String TEXTO_BASE1;

	private String TEXTO_BASE2;

	protected Turno turno = null;

	protected boolean simple = false;
	
    boolean isRegistro = false;
    
	private boolean imprimirDerivados = false;
	
	private boolean imprimirUsuarioSalvedad = false;

	protected int tituloMicro = ImprimibleConstantes.TITULO_MICRO;
	
	private List anotacionToCorrecion = null;

	protected void imprimirFolioTop(Folio folio, String tbase1, String tbase2) {
		setTEXTO_BASE1(tbase1);
		setTEXTO_BASE2(tbase2);
		this.microTexto = this.getMicroTexto(folio);

		String circuloRegNom =
			(folio.getZonaRegistral() != null
				&& folio.getZonaRegistral().getCirculo() != null
				&& folio.getZonaRegistral().getCirculo().getNombre() != null
					? folio.getZonaRegistral().getCirculo().getNombre()
					: "");
		String circuloRegId =
			(folio.getZonaRegistral() != null
				&& folio.getZonaRegistral().getCirculo() != null
				&& folio.getZonaRegistral().getCirculo().getIdCirculo() != null
					? folio.getZonaRegistral().getCirculo().getIdCirculo()
					: "");

		if (circuloRegNom == null)
			circuloRegNom = "";

		if (circuloRegId == null)
			circuloRegId = "";

		String depto =
			(String) ImprimibleHelper.getDatoFromFolio(
				folio,
				IGlosarioImprimibles.departamento);
		String municipio =
			(String) ImprimibleHelper.getDatoFromFolio(
				folio,
				IGlosarioImprimibles.municipio);
		String vereda =
			(String) ImprimibleHelper.getDatoFromFolio(
				folio,
				IGlosarioImprimibles.vereda);

		if (!circuloRegNom.equals("")) {
			this.imprimirLinea(
				ImprimibleConstantes.PLANO,
				"CIRCULO REGISTRAL: "
					+ circuloRegId
					+ " "
					+ circuloRegNom
					+ ImprimibleConstantes.ESPACIOS
					+ "DEPTO: "
					+ depto
					+ ImprimibleConstantes.ESPACIOS
					+ "MUNICIPIO: "
					+ municipio
					+ ImprimibleConstantes.ESPACIOS
					+ "VEREDA: "
					+ vereda);
		}

		Calendar c = Calendar.getInstance();
		String fechaAper = "";
		try {
			Date fechaApertura = folio.getFechaApertura();
			fechaAper = super.getFecha(fechaApertura);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String radicacion = radicacion = folio.getRadicacion();

		if (radicacion == null)
			radicacion = "";

		Documento doc = folio.getDocumento();
		String docum = "";

		if (doc != null) {
			TipoDocumento tipoDoc = doc.getTipoDocumento();
			if (tipoDoc != null)
				docum = tipoDoc.getNombre();
				/*
			OficinaOrigen oficinaOri = doc.getOficinaOrigen();
			if (oficinaOri!=null)
			{
				String nombreOficina = oficinaOri.getNombre();
				String idOficina = oficinaOri.getIdOficinaOrigen();
			}*/

			String numero = doc.getNumero();
			String fecha = this.getFecha(doc.getFecha());

			if (fecha != null) {
				docum = "CON: " + docum + " DE " + fecha;
			}

		} else
			System.out.println(".....el documento es null");

		//String escritura = (String)	ImprimibleHelper.getDatoFromFolio(folio,ImprimibleConstantes.)

		this.imprimirLinea(
			ImprimibleConstantes.PLANO,
			"FECHA APERTURA: "
				+ fechaAper
				+ ImprimibleConstantes.ESPACIOS
				+ "RADICACION: "
				+ radicacion
				+ " "
				+ docum);
                
                String nupre = folio.getNupre();
                
                if(nupre != null && !nupre.isEmpty()){
                    this.imprimirLinea(
				ImprimibleConstantes.PLANO,
				280,
				"NUPRE: " + nupre);
                } else{
                    this.imprimirLinea(
				ImprimibleConstantes.PLANO,
				280,
				"NUPRE: SIN INFORMACION");
                }
                

		String codCatastral =
			(String) ImprimibleHelper.getDatoFromFolio(
				folio,
				IGlosarioImprimibles.codCatastral);
		//if (!codCatastral.equals(""))
		{
                        /**
                        * @author: Daniel Forero
                        * @change: Caso 904.ACTUALIZACION.FICHAS.CATASTRALES, se modifica la posición en la que se imprime el código catastral
                        */
			this.imprimirLinea(
				ImprimibleConstantes.PLANO,
				280,
				"COD CATASTRAL: " + codCatastral);
		}
                

		String estado =
			(String) ImprimibleHelper.getDatoFromFolio(
				folio,
				IGlosarioImprimibles.estado);

		if (!estado.equals("")) {
        		if (! (this instanceof ImprimibleHojaDeRuta)) {
        			if(!folio.isDefinitivo()){
        					this.imprimirLinea(
                                  ImprimibleConstantes.TITULO_GRANDE2,
                                  "FOLIO TEMPORAL ",
                                  false);
        			}else{
                          this.imprimirLinea(
                              ImprimibleConstantes.PLANO,
                              "ESTADO DEL FOLIO: ",
                              false);
                          this.imprimirLinea(
                                  ImprimibleConstantes.TITULO_GRANDE2,
                                  120,
                                  estado,
                                  false);
        			}
                          
                }
		}
                                
                
                
		String codCatastralAnt =
			(String) ImprimibleHelper.getDatoFromFolio(
				folio,
				IGlosarioImprimibles.codCatastralAnterior);

		if(codCatastralAnt == null || codCatastralAnt.equals(""))
			codCatastralAnt = "SIN INFORMACION";

		//if (!codCatastralAnt.equals(""))
		{
                        /**
                        * @author: Daniel Forero
                        * @change: Caso 904.ACTUALIZACION.FICHAS.CATASTRALES, se modifica la posición en la que se imprime el código catastral
                        */
			this.imprimirLinea(
				ImprimibleConstantes.PLANO,
				280,
				"COD CATASTRAL ANT: " + codCatastralAnt);
		}

	}

	/**
	 * Imprime la informacion del encabezado del folio de manera resumida.
	 * @param folio
	 */
	protected void imprimirFolioTopResumen(Folio folio) {
		String circuloRegNom =
			(folio.getZonaRegistral() != null
				&& folio.getZonaRegistral().getCirculo() != null
				&& folio.getZonaRegistral().getCirculo().getNombre() != null
					? folio.getZonaRegistral().getCirculo().getNombre()
					: "");
		String circuloRegId =
			(folio.getZonaRegistral() != null
				&& folio.getZonaRegistral().getCirculo() != null
				&& folio.getZonaRegistral().getCirculo().getIdCirculo() != null
					? folio.getZonaRegistral().getCirculo().getIdCirculo()
					: "");

		if (circuloRegNom == null)
			circuloRegNom = "";

		if (circuloRegId == null)
			circuloRegId = "";

		String depto =
			(String) ImprimibleHelper.getDatoFromFolio(
				folio,
				IGlosarioImprimibles.departamento);
		String municipio =
			(String) ImprimibleHelper.getDatoFromFolio(
				folio,
				IGlosarioImprimibles.municipio);
		String vereda =
			(String) ImprimibleHelper.getDatoFromFolio(
				folio,
				IGlosarioImprimibles.vereda);


                imprimirFolioTopResumen_Matricula( folio.getIdMatricula() );

		String linea;

		// linea = "Nro Matricula: " + folio.getIdMatricula();
		// linea =
		//	StringFormat.getCentrada(
		//		linea,
		//		ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
		//		0);
		// this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, linea);

		String codCatastral = folio.getCodCatastral();
		if (codCatastral == null)
			codCatastral = " ";

		if (!circuloRegNom.equals("")) {
			linea =
				"CIRCULO DE REGISTRO: "
					+ circuloRegId
					+ " "
					+ circuloRegNom
					+ ImprimibleConstantes.ESPACIOS
					+ "No. Catastro: "
					+ codCatastral;
			this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
		}

		String tipoPredio = this.getTipoPredio(folio);
		linea =
			"MUNICIPIO: "
				+ municipio
				+ ImprimibleConstantes.ESPACIOS
				+ "DEPARTAMENTO: "
				+ depto
				+ ImprimibleConstantes.ESPACIOS
		+ "VEREDA: "
			+ vereda
			+ ImprimibleConstantes.ESPACIOS
				+ "TIPO PREDIO: "
				+ tipoPredio;
		linea = linea.trim();
		this.imprimirLinea(ImprimibleConstantes.PLANO, linea);

		Documento doc = folio.getDocumento();
		String escrit = "";

		if (doc != null) {

			//String nombreOficina = doc.getOficinaOrigen().getNombre();
			//String idOficina = doc.getOficinaOrigen().getIdOficinaOrigen();
			String numero = doc.getNumero();
			String fecha = this.getFecha(doc.getFecha());
			TipoDocumento tipoDocum = doc.getTipoDocumento();
			String tipoDoc = "";
            if (tipoDocum!=null)
              tipoDoc=tipoDocum.getNombre();
			if (fecha != null) {
				escrit = "CON: "+tipoDoc+" DE " + fecha;
			}

		} else
			System.out.println(".....el documento es null");

	}

   // imprime el numero de la matricula, todo el texto del mismo tamaño
   private void
   imprimirFolioTopResumen_Matricula_Old( String folio_IdMatricula ) {

      String linea;

      linea = "Nro Matricula: " + folio_IdMatricula;
      linea =
              StringFormat.getCentrada(
                      linea,
                      ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                      0);
      this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, linea);


   }  // :


       // imprime el numero de la matricula
       private void
       imprimirFolioTopResumen_Matricula( String folio_IdMatricula ) {

          String linea;
          final boolean REPLACE_TEXT_WITH_WHITESPACES = false;

          // Paso 1: imprimir la primera parte del texto (Nro MAtricula)

          // linea sin id matricula
          String line_Fragment1;
          line_Fragment1 = "Nro Matricula: " + getLineFromModo( folio_IdMatricula, REPLACE_TEXT_WITH_WHITESPACES );

          String line_Fragment2;
          line_Fragment2 = getLineFromModo( "Nro Matricula: ", REPLACE_TEXT_WITH_WHITESPACES ) + folio_IdMatricula;


          // linea = "Nro Matricula: " + folio_IdMatricula;
          linea =
                  StringFormat.getCentrada(
                          line_Fragment1,
                          ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                          0);

          this.imprimirLinea(ImprimibleConstantes.TITULO2,"");
          this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, linea, false );

          // factor para que no se separe tanto el texto "nro matricula" y el numero relacionado "###-#####"
          final double local_Factor =  0.925d;

          // Paso 2: imprimir el resto de la linea con el tamaño
          // del numero del recibo

          linea =
                  StringFormat.getCentrada(
                          line_Fragment2,
                          (int)( ImprimibleConstantes.MAX_NUM_CHAR_TITULO1 * local_Factor ),
                          0);

          this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, linea, true );

          //this.imprimirLinea(ImprimibleConstantes.TITULO2,
          //                                   (int)(ImprimibleConstantes.MARGEN_IZQ * 4.0d),
          //                                   "No. RADICACIÓN: ", false);




       }  // :


	/**
	 * Retorna el tipo de predio de un folio.
	 * @param folio.
	 * @return tipo de predio del folio.
	 */
	private String getTipoPredio(Folio folio) {

		if (folio == null)
			return "";

		String tipoPredio = " ";

		TipoPredio tipoPred = folio.getTipoPredio();

		if (tipoPred == null)
			tipoPredio = " ";
		else {
			tipoPredio = tipoPred.getNombre();
			if (tipoPredio == null)
				tipoPredio = " ";
		}

		return tipoPredio;
	}

	/**
	  * Imprime las anotaciones de un folio.
	  * @return número de anotaciones del folio.
	  */
	protected int imprimirAnotaciones(Folio folio) {
		List anotaciones = folio.getAnotaciones();
		List anotacionesPadre;
		Anotacion anota;
		FolioDerivado anoPadre;
		String idMatriculaPadre;

		int n = anotaciones.size();
		//BUG 3473
		//Se coloca el siguiente condicional, para que no coloque las
		//anotaciones padre en el Certificado, ya que esta información ya se colocó en la linea anterior.
		//Para el caso del Formulario de calificación se deja que muestre las anotaciones padre.
                //(Implica el conocimiento de la clase hija!!)
		if(this instanceof ImprimibleFormulario){

			if (n > 0) {

				for (int j = 0; j < n; j++) {
					anota = (Anotacion) anotaciones.get(j);

					anotacionesPadre = anota.getAnotacionesPadre();

					for (int k = 0; k < anotacionesPadre.size(); k++) {
						anoPadre = (FolioDerivado) anotacionesPadre.get(k);
						try {
							idMatriculaPadre = anoPadre.getPadre().getIdMatricula();

							this.imprimirLinea(
								ImprimibleConstantes.PLANO,
								idMatriculaPadre);

						} catch (Exception e) {
                                                  // TODO: add
							this.imprimirLinea(ImprimibleConstantes.PLANO, "");
						}
					}
				}
			}

		}

		this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
		this.imprimirLinea(this.tituloMicro, this.getMicroTexto(folio));

		n = anotaciones.size();
		System.out.println("....................ANOTACIONES=" + n);
		for (int j = 0; j < n; j++)
		{
			anota = (Anotacion) anotaciones.get(j);

			if(anota.getSalvedades()!=null && anota.getSalvedades().size()>0){
				hasSalvedadesAnotaciones = true;
			}

			/*
			int numPaginas1 = this.getNumberOfPages();
			int numLineas = this.imprimirAnotacion(anota, false);
			int numPaginas2 = this.getNumberOfPages();

			if (numPaginas1 == numPaginas2) {
				this.moveToLineaAnterior(numLineas);
				this.imprimirAnotacion(anota, true);
			} else {
				*/

			// TODO: corregir
            //System.out.println("@@ PrintAnotacion - Start (Certificado)");

			//mriveros: Fix para el Bug 4413
			/*if(this instanceof ImprimibleFormulario)
			{
				if (anota.getNumRadicacion() != null &&
						anota.getNumRadicacion().equals(this.turno.getIdWorkflow()))
					this.imprimirAnotacion(anota, true);
			}
			else*/
			//{
			    /////this.microTexto2 = this.getMicroTexto(folio);
				this.imprimirAnotacion(anota, true);
			//}
            //System.out.println("@@ PrintAnotacion - End (Certificado)");
			//}

		}

		return n;
	}

        /**
         * backward compatibility
         * */
        protected int imprimirAnotacionesFormulario(Folio folio ) {
          return imprimirAnotacionesFormulario( folio, true );
        }


	/**
	  * Imprime las anotaciones de un folio.
	  * @return número de anotaciones del folio.
          * @param imprimirSalvedadesActive preference1: si se imprimen las salvedades de la anotacion
          * @see bug:3727
	  */
	protected int imprimirAnotacionesFormulario(Folio folio, boolean imprimirSalvedadesActive ) {
		List anotaciones = folio.getAnotaciones();
		List anotacionesPadre;
		Anotacion anota;
		FolioDerivado anoPadre;
		String idMatriculaPadre;

		int n = anotaciones.size();
		if (n > 0) {

			for (int j = 0; j < n; j++) {
				anota = (Anotacion) anotaciones.get(j);

				anotacionesPadre = anota.getAnotacionesPadre();

				for (int k = 0; k < anotacionesPadre.size(); k++) {
					anoPadre = (FolioDerivado) anotacionesPadre.get(k);
					try {
						idMatriculaPadre = anoPadre.getPadre().getIdMatricula();

						this.imprimirLinea(
							ImprimibleConstantes.PLANO,
							idMatriculaPadre);

					} catch (Exception e) {
						this.imprimirLinea(ImprimibleConstantes.PLANO, "");
					}
				}
			}

		}
		this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.SEPARADOR2);
		n = anotaciones.size();
		System.out.println("....................ANOTACIONES=" + n);
		int numSalve;
		for (int j = 0; j < n; j++) {
			anota = (Anotacion) anotaciones.get(j);


                        //System.out.println("@@ PrintAnotacion - Start (Formulario)");
			this.imprimirAnotacion(anota, true);
                        //System.out.println("@@ PrintAnotacion - End (Formulario)");



			//this.imprimirLinea(ImprimibleConstantes.titulo2, "");
                        if( imprimirSalvedadesActive ) {
                          numSalve = anota.getSalvedades().size();
                          if (numSalve > 0) {
                            this.imprimirLinea(ImprimibleConstantes.TITULO2,
                                               "SALVEDADES:");
                            this.imprimirSalvedadesAnotacion(anota, false);

                          }
                          this.imprimirLinea(ImprimibleConstantes.PLANO, "");
                        } // :if
		}

		return n;
	}




	/**
	 * Imprime la información de una anotación.
	 * @param anota es la anotacion.
	 * @param modo constante que indica si se va a imprimir o solo a verificar la distribución del texto
	 * en las páginas del Imprimible. (Esto se hace con el fin de en lo posible no dejar segmentos de la información
	 * de una anotación en otra página.)
	 * @return
	 */
	protected int imprimirAnotacion(Anotacion anota, boolean modo) {
		AnotacionCiudadano anoCiud;
		Ciudadano ciudadano;
		String nro = "",
			fecha = "",
			radica = "",
			doc = "",
			fechaDoc = "",
			nombreOficDoc = "",
			nombresCiudadano = "",
			porcentaje = "",
			role = "",
                        modalidad="";
		double valor;
		Float porc = new Float(0);


		nro = anota.getOrden();
        if( null == nro )
           nro = " ";

		fecha = this.getFecha(anota.getFechaRadicacion());

		int lineas = 0;

		radica = anota.getNumRadicacion();
                modalidad = anota.getModalidad();
		String linea ="";

		if (radica == null)
		{
			  radica = "";
		}



		//System.out.println( "@@ anotacion.nro = " + ( (null==nro)?( "null" ):( nro ) ) );
		//System.out.println( "" );




		//if (!radica.equals("") && !fecha.equals("") && !nro.equals(""))
		  int limite = ImprimibleConstantes.MAXIMO_VERTICAL - ImprimibleConstantes.SEPARACION_LINE;

        // bug 3538
        EstadoAnotacion local_Anotacion_Estado;
        if( ( null != ( local_Anotacion_Estado = anota.getEstado() )  ) ) {

           final String INVALID_STATE = CEstadoAnotacion.INVALIDO;

           if( INVALID_STATE.equals( local_Anotacion_Estado.getIdEstadoAn() ) ) {

              linea = "***** Esta Anotación no tiene validez *****";
              linea = linea.toUpperCase();
              this.imprimirLinea( ImprimibleConstantes.TITULO2, linea );

           } // :if

        } // :if

        linea =this.getLineFromModo(
                                              "ANOTACIÓN: Nro: "
                                                      + nro
                                                      + ImprimibleConstantes.ESPACIOS
                                                      + "Fecha "
                                                      + fecha
                                                      + ImprimibleConstantes.ESPACIOS
                                                      + "Radicación "
                                                      + radica,
						modo);



          linea =this.getLineFromModo(
						"ANOTACIÓN: Nro: "
							+ nro
							+ ImprimibleConstantes.ESPACIOS
							+ "Fecha "
							+ fecha
							+ ImprimibleConstantes.ESPACIOS
							+ "Radicación "
							+ radica,
						modo);
				this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);
				lineas += this.getNumLinea(linea, ImprimibleConstantes.TITULO2);


		doc = " ";
		fechaDoc = " ";
		nombreOficDoc = " ";

		String nombreGrupoNaturaleza = "";
		String idnat = "";
		String comentario = "";
		String nombreNat = "";
		String especificacion = "";
		String comentarioDoc = "";

		if (anota != null) {
			Documento documento = anota.getDocumento();
			if (documento != null) {

				doc = documento.getTipoDocumento().getNombre();
				if (documento.getNumero()!=null)
				{
					doc+= " "+documento.getNumero();
				}

				comentarioDoc = documento.getComentario();
				if (comentarioDoc == null)
					comentarioDoc = "";

				NaturalezaJuridica naturaleza = anota.getNaturalezaJuridica();
				if (naturaleza != null) {
					idnat = naturaleza.getIdNaturalezaJuridica();
					comentario = documento.getComentario();
					nombreGrupoNaturaleza = naturaleza.getGrupoNaturalezaJuridica().getNombre();

					// filter comentario
					// comentario = doFilter_AnotacionDocumentoComentario( comentario );

					nombreNat = naturaleza.getNombre();
					especificacion = anota.getEspecificacion();
					if (especificacion == null)
						especificacion = nombreNat;
				}
				
				if(anota.getComentario()!=null && !anota.getComentario().trim().equals("")
						&& especificacion.indexOf(anota.getComentario().trim())==-1){
					especificacion += " - " + anota.getComentario();
				}

				if (idnat == null)
					idnat = "";

				if (comentario == null)
					comentario = "";

				if (nombreNat == null)
					nombreNat = "";

				if (nombreGrupoNaturaleza == null)
					nombreGrupoNaturaleza = "";

				String especif = "";

				fechaDoc = this.getFecha(documento.getFecha());
				OficinaOrigen oficina = documento.getOficinaOrigen();

				if (oficina != null) {
					if(oficina.getNombre()!=null){

						// filtro para dejar NOTARIA UNICA-LETICIA en lugar de LETICIA-NOTARIA UNICA
						// nombreOficDoc = doFilter_NombreOficinaDoc( oficina.getNombre() );

						nombreOficDoc = oficina.getNombre();
						//System.out.println( "@@XX" + nombreOficDoc );


					}
					String municipio = "";
					Vereda vereda = oficina.getVereda();

					if (vereda != null) {
						Municipio munic = vereda.getMunicipio();
						if (munic != null) {
							municipio = munic.getNombre();
							if (municipio == null)
								municipio = "";
						}
					}
					if (!municipio.trim().equals(""))
						nombreOficDoc += " DE " + municipio;
				}
                                /**
                                 * @author: Fernando Padilla
                                 * @change: Caso mantis 2653, se imprime el nombre de la oficina internacional.
                                 */
                                else{
                                        if(documento.getOficinaInternacional() != null){
                                            nombreOficDoc += documento.getOficinaInternacional();
                                        }
                                    	
                                }

			}
		}

		valor = anota.getValor();

		String val = StringFormat.getNumeroFormateado(valor);


                // imprimir oficina Origen y/o comentario
                // doGetOficinaOrigenPrintTx( nombreOficDoc, comentarioDoc );
                String normalizedOficinaOrigenTx = doGetOficinaOrigenPrintTx( nombreOficDoc, comentarioDoc );

		linea =
			"DOC: "
				+ doc
				+ ImprimibleConstantes.ESPACIOS
				+ "DEL: "
				+ fechaDoc
				+ ImprimibleConstantes.ESPACIOS
				// + nombreOficDoc //
				+ ImprimibleConstantes.ESPACIOS
				+ normalizedOficinaOrigenTx // -- bug
				+ ImprimibleConstantes.ESPACIOS
				+ "VALOR ACTO: $ "
				+ val;

		linea = this.getLineFromModo(linea, modo);
		this.imprimirLinea(ImprimibleConstantes.PLANO, linea);

		//cancelacion de anotaciones:
		String canceladas = "                   ";
		List cancelaciones = anota.getAnotacionesCancelacions();
		
		String cance[] = new String[cancelaciones.size()];
		int contador = 0;
		boolean entroCanceladora = false;
		
		if (cancelaciones != null) {
			for (int i = 0; i < cancelaciones.size(); i++) {
				Cancelacion cancel = (Cancelacion) cancelaciones.get(i);
				Anotacion anotaCancelada = cancel.getCancelada();
				String orden = "";
				if (this.getTipoImprimible().equals(CTipoImprimible.FORMULARIO_CORRECCION)) {
					Anotacion anotacionCanceladaCorrecta = getAnotacion(anotaCancelada.getIdAnotacion(), this.getAnotacionToCorrecion());
					if (anotacionCanceladaCorrecta != null) {
						orden = anotacionCanceladaCorrecta.getOrden();
					} else {
						orden = anotaCancelada.getOrden();
					}					
				} else {
					orden = anotaCancelada.getOrden();
				}
				//String orden = anotaCancelada.getOrden();
				//canceladas += "Se cancela la anotación No, " + orden + ",     ";
				cance[i] = orden;
				entroCanceladora = true;
			}
		}
		if (entroCanceladora){
			ordenarIdsCancelacion(cance);
		}
			
		if (cancelaciones != null) {
			for(int i = 0;i<cance.length;i++){
				if (i == 0){
					canceladas += "Se cancela la anotación No, " + cance[i];
				} else {
					canceladas += " ,     ";
					canceladas += "Se cancela la anotación No, " + cance[i];
				}
			}
			if (!canceladas.trim().equals("")) {
				linea = this.getLineFromModo(canceladas, modo);
				this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
				lineas += this.getNumLinea(linea, ImprimibleConstantes.PLANO);
			}
		}

		lineas += this.getNumLinea(linea, ImprimibleConstantes.PLANO);

		linea =
			"ESPECIFICACION: "
				+ ImprimibleConstantes.ESPACIOS
				+ nombreGrupoNaturaleza
				+ "   :   "
				+ idnat
				+ " "
				+ especificacion
                                + (modalidad!=null&&!modalidad.isEmpty()?" MODALIDAD ":"")
                                + (modalidad!=null&&!modalidad.isEmpty()?modalidad:"");

		linea = this.getLineFromModo(linea, modo);
		this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
		lineas += this.getNumLinea(linea, ImprimibleConstantes.PLANO);

		linea =
			this.getLineFromModo(
				"PERSONAS QUE INTERVIENEN EN EL ACTO (X-Titular de derecho real del dominio, I-Titular de dominio incompleto)",
				modo);
		this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);
		lineas += this.getNumLinea(linea, ImprimibleConstantes.TITULO2);

		ArrayList anotacionesCiudadano = new ArrayList(anota.getAnotacionesCiudadanos());

        try
        {
        	 //ordenar las anotaciones por rol
        	 Collections.sort(anotacionesCiudadano, new RolAnotaCiudadanoComparator());
        }
        catch (ClassCastException e)
        {
        	System.out.println("@@@No fue posible ordenar las anotaciones por rol");
        }

		//System.out.println("personas = " + anotacionesCiudadano.size());

		for (int k = 0; k < anotacionesCiudadano.size(); k++) {
			anoCiud = (AnotacionCiudadano) anotacionesCiudadano.get(k);
			ciudadano = anoCiud.getCiudadano();
			porcentaje = anoCiud.getParticipacion();
			if(porcentaje==null){
				porcentaje="";
			}
			nombresCiudadano = this.getNombreCompletoConId(ciudadano);

			//ciudadano.getApellido1()+" "+ciudadano.getApellido2()+" "+ciudadano.getNombre();
            String marca = "";
			role = anoCiud.getRolPersona();
			if (role != null) {
				if (role.equalsIgnoreCase(AnotacionCiudadano.ROL_PERSONA_A))
				{
					marca = anoCiud.getStringMarcaPropietario();
					if ( !marca.equals("") )
						linea =
							this.getLineFromModo(
								role
									+ ": "
									+ nombresCiudadano
									+ ImprimibleConstantes.ESPACIOS
									+ marca
									+ ImprimibleConstantes.ESPACIOS
									+ porcentaje,
								modo);
					else
						linea =
							this.getLineFromModo(
								role + ": " + nombresCiudadano + ImprimibleConstantes.ESPACIOS + porcentaje,
								modo);

					this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);
					lineas
						+= this.getNumLinea(linea, ImprimibleConstantes.TITULO2);
				} else {
					marca = anoCiud.getStringMarcaPropietario();
					if ( !marca.equals("") )
						linea =
							this.getLineFromModo(
								role
									+ ": "
									+ nombresCiudadano
									+ ImprimibleConstantes.ESPACIOS
									+ marca
									+ ImprimibleConstantes.ESPACIOS
									+ porcentaje,
								modo);
					else
						linea =
							this.getLineFromModo(
								role + ": " + nombresCiudadano + ImprimibleConstantes.ESPACIOS+porcentaje,
								modo);

					this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
					lineas
						+= this.getNumLinea(linea, ImprimibleConstantes.PLANO);
				}
			} else
				System.out.println("-------------------->role es null");

		}

        //SE QUITA LO ANTERIOR POR SOLICITUD BUG 471
		if (imprimirDerivados) {
		// Se imprimen los folios derivados
        List foliosDerivados = anota.getAnotacionesHijos();

        if(foliosDerivados != null && !foliosDerivados.isEmpty()) {

            linea = this.getLineFromModo(
                    "FOLIOS DERIVADOS DE LA ANOTACION",
                    modo);
            this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);

            linea = "";
            String izq = "";
            String der = "";
            int iter;
            /**
                * @author: David Panesso
                * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                * Impresión de los folios derivados a dos columnas. La columna esta compuesta por la
                * matrícula y finalmente el nombre del inmueble. Para el orden y la matrícula se utilizan 15 caracteres y para
                * el nombre del inmueble se limita a 40.
                * Formato Ej: 300-128829 LOTE INMUEBLE 1
                **/
            Object[] foliosDerivadosArray = foliosDerivados.toArray();
            
            for (int i = 0 ; i < foliosDerivadosArray.length-1 ; i++) {
                FolioDerivado folioI = (FolioDerivado) foliosDerivadosArray[i];
                String[] splitI = folioI.getIdMatricula1().split("-");
                int matriculaI = Integer.parseInt(splitI[1]);
                for (int j = i+1 ; j < foliosDerivadosArray.length ; j++) {
                    FolioDerivado folioJ = (FolioDerivado) foliosDerivadosArray[j];
                    String[] splitD = folioJ.getIdMatricula1().split("-");
                    int matriculaD = Integer.parseInt(splitD[1]);
                    if (matriculaI > matriculaD) {
                        FolioDerivado aux = (FolioDerivado) foliosDerivadosArray[i];
                        int auxNum = matriculaI;
                        foliosDerivadosArray[i] = foliosDerivadosArray[j];
                        matriculaI = matriculaD;
                        foliosDerivadosArray[j] = aux;
                        matriculaD = auxNum;
                    }
                }
            }
            
            foliosDerivados = new ArrayList(Arrays.asList(foliosDerivadosArray));
            for (iter = 0; iter + 1 < foliosDerivados.size(); iter++) {
                FolioDerivado folioHijoIzq = (FolioDerivado) foliosDerivados.get(iter);
                FolioDerivado folioHijoDer = (FolioDerivado) foliosDerivados.get(++iter);
                
                String idMatriculaIzq = folioHijoIzq.getIdMatricula1() == null ? "" : folioHijoIzq.getIdMatricula1();
                String nombreLoteIzq = folioHijoIzq.getLote() == null ? "" : folioHijoIzq.getLote();
                String idMatriculaDer = folioHijoDer.getIdMatricula1() == null ? "" : folioHijoDer.getIdMatricula1();
                String nombreLoteDer = folioHijoDer.getLote() == null ? "" : folioHijoDer.getLote();
                
                izq = idMatriculaIzq.length() > 15 ? idMatriculaIzq.substring(0, 14) : idMatriculaIzq;
                izq = String.format("%-15s", izq).replace(' ', ' ');
                izq = String.format("%-55s", (izq + " " + nombreLoteIzq)).replace(' ', ' ');
                izq = izq.length() > 55 ? izq.substring(0, 54) : izq;
                izq = this.getLineFromModo(izq, modo);
                this.imprimirLinea(ImprimibleConstantes.PLANO, izq, false);
                
                der = idMatriculaDer.length() > 15 ? idMatriculaDer.substring(0, 14) : idMatriculaDer;
                der = String.format("%-15s", der).replace(' ', ' ');
                der = String.format("%-55s", (der + " " + nombreLoteDer)).replace(' ', ' ');
                der = der.length() > 55 ? der.substring(0, 54) : der;
                der = this.getLineFromModo(der, modo);
                this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.ANCHO_CARTA/2), der, true);              
            }
            if (iter < foliosDerivados.size()) {
                FolioDerivado folioHijoIzq = (FolioDerivado) foliosDerivados.get(iter);
                
                String idMatriculaIzq = folioHijoIzq.getIdMatricula1() == null ? "" : folioHijoIzq.getIdMatricula1();
                String nombreLoteIzq = folioHijoIzq.getLote() == null ? "" : folioHijoIzq.getLote();
                
                izq = idMatriculaIzq.length() > 15 ? idMatriculaIzq.substring(0, 14) : idMatriculaIzq;
                izq = String.format("%-15s", izq).replace(' ', ' ');
                izq = String.format("%-55s", (izq + " " + nombreLoteIzq)).replace(' ', ' ');
                izq = izq.length() > 55 ? izq.substring(0, 54) : izq;
                linea = this.getLineFromModo(izq, modo);
                this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
            }
        }
		}

		//lineas=this.imprimirSalvedadesAnotacion(anota,modo,lineas);

      //System.out.println( "@@ Print MidLine (Start)" );

		linea = this.getLineFromModo(microTexto, modo);

      // Bug 3436 (Linea divisoria formulario muy corta)
      int defaultPlainSeparatorSize;
      defaultPlainSeparatorSize = this.tituloMicro;


      // Bug 3436
      // (Implica Conocimiento de la clase hija)
      ImprimibleBaseFolio localInstance = this;
      if( localInstance instanceof ImprimibleFormulario ) {


         // reglas para realizar la impresion de
         // separador de linea

         // parametros:
         // 1. existen especiso en blanco en el separador
         // 2. tipo de imprimible formulario

         ImprimibleFormulario local_ImprimibleFormulario;

         local_ImprimibleFormulario = (ImprimibleFormulario)localInstance;

         String local_TipoImprimibleFormulario;
         local_TipoImprimibleFormulario = local_ImprimibleFormulario.getTipo();

         if( null == local_TipoImprimibleFormulario )
            local_TipoImprimibleFormulario = "";

         boolean whiteSpacesEnabled;
         whiteSpacesEnabled = "".equals( linea.trim() ) ;



         // Bug 3422; metodo reformado

         if( whiteSpacesEnabled && CTipoFormulario.TIPO_CALIFICACION.equals( local_TipoImprimibleFormulario ) ) {
            linea = getDefaultPlainSeparator( localInstance );
            /////linea = microTexto2;
            defaultPlainSeparatorSize = getDefaultPlainSeparatorSize( localInstance );
         }
         if( whiteSpacesEnabled && CTipoFormulario.TIPO_CORRECCION.equals( local_TipoImprimibleFormulario ) ) {
             linea = ImprimibleConstantes.SEPARADOR2;
             /////linea = microTexto2;
             defaultPlainSeparatorSize = getDefaultPlainSeparatorSize( localInstance );
          }
         if( whiteSpacesEnabled && CTipoFormulario.TIPO_REPRODUCCION_SELLOS.equals( local_TipoImprimibleFormulario ) ) {
            linea = getDefaultPlainSeparator( localInstance );
            /////linea = microTexto2; 
            defaultPlainSeparatorSize = getDefaultPlainSeparatorSize( localInstance );
         }


      } // :if

      printSeparator( defaultPlainSeparatorSize, linea);
      lineas += 1; //this.getNumLinea(linea);

      /*

      if( localInstance instanceof ImprimibleFormulario ) {
        final String SEPARADOR1_LOWLINE = "__________________________________________________________________________________________________________________________";
        this.imprimirLinea( ImprimibleConstantes.PLANO,SEPARADOR1_LOWLINE );
        this.imprimirLinea( ImprimibleConstantes.PLANO,ImprimibleConstantes.SEPARADOR1 );
      }

      */


    // System.out.println( "@@ Print MidLine (End)" );

    return lineas;

  } // end-method

	public static void ordenarIdsCancelacion(String a[ ]) {
		int numerosArray [] = new int [a.length];
        for(int k = 0; k < a.length; k++) {
            //Se valida que que el valor no sea null.
            if(a[k]!= null){
                numerosArray [k] = Integer.valueOf(a[k]).intValue();
            }
        	
        }
        
        Arrays.sort(numerosArray);

        //Se redefine el array en caso de existir valores nulos dentro a[].
        a = new String[numerosArray.length];

        for (int k = 0; k < numerosArray.length; k++) {
        	a[k] = String.valueOf(numerosArray[k]);
        }
    }
	
	private Anotacion getAnotacion(String idAnotacion, List anotacionesA){
		try{
	    	Iterator itAnotacion = anotacionesA.iterator(); //aqui se pide en vez folio.getAnotaciones la anotaciones definitivas
			while(itAnotacion.hasNext()){
				Anotacion anotacion = (Anotacion)itAnotacion.next();
				if (anotacion.getIdAnotacion().equals(idAnotacion)){
					return anotacion;
				}
			}
		}catch(Exception eAnotacion){
			eAnotacion.printStackTrace();
			return null;
		}
		return null;
	}
	
   private void printSeparator( int size, String text ){
	 if (text.equals(this.microTexto))
	 {
		 this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
	 }
     imprimirLinea( size, text );
     //lineas += 1;
   } // end-method;

   private int getDefaultPlainSeparatorSize( ImprimibleBaseFolio localInstance ) {
     if( localInstance instanceof ImprimibleFormulario ) {
       return ImprimibleConstantes.PLANO;
     }
     return tituloMicro;
   }

   private String getDefaultPlainSeparator( ImprimibleBaseFolio localInstance ) {
      //System.out.println( "@@ [Getting Default Plain Separator ]" );
      // TODO: modify
      if( localInstance instanceof ImprimibleFormulario ) {
        final String SEPARADOR1_LOWLINE = "______________________________________________________________________________________________________________________";
        return SEPARADOR1_LOWLINE;
      }
      return ImprimibleConstantes.SEPARADOR2;

   } // :

  /**
   * doGetOficinaOrigenPrintTxt
   *
   * @param nombreOficDoc String
   * @param comentarioDoc String
   */
  private String doGetOficinaOrigenPrintTx(
      String nombreOficDoc,
      String comentarioDoc
  ) {

    // Build replace String : a travez de comentario
    String replaceTx = (null == comentarioDoc) ? ("") : (comentarioDoc);
    replaceTx = doFilter_AnotacionDocumentoComentario( replaceTx );

    // filter 1: si es nulo, colocar texto de reemplazo

    if( null == nombreOficDoc)
      return replaceTx;

    String filteredText = nombreOficDoc.trim();

    // filter 2: si es vacio, colocar texto de reemplazo

    if( "".equals( filteredText ) )
      return replaceTx;

    // otherwise: original

    return nombreOficDoc;
  }

  public static String
        doReverseTokenizedString( String comentario, String separator ) {
      return doReverseTokenizedString( comentario, separator, separator );
    }

  public static String
	doReverseTokenizedString( String comentario, String separator, String separatorReplace ) {

		if( null == comentario )
			return null;

		String sourceString = comentario;
		String[] result = sourceString.split( separator );
		StringBuffer buffer = new StringBuffer( comentario.length() + 10 );

		for( int i= result.length - 1 ; i >=0;i--) {
			buffer.append( separatorReplace );
			buffer.append( result[i] );
		}
		if( 0 == buffer.length() )
			return null;

		// System.out.println( buffer.length() );
		// System.out.println( separator.length() );

		return buffer.substring(separatorReplace.length(),buffer.length());


	}

	private String doFilter_AnotacionDocumentoComentario( String comentario ) {
		return doReverseTokenizedString( comentario, "-", " DE " );
	}

        protected void imprimirSalvedadesFolio( Folio folio ) {
          imprimirSalvedadesFolio( folio, true, false );
        }

        protected void imprimirSalvedadesFolio(Folio folio, boolean imprimirSalvedadesAnotacion ) {
          imprimirSalvedadesFolio( folio, imprimirSalvedadesAnotacion, false );
        }

	/**
	 * Imprime las salvedades del folio.
	 * @param folio
	 */
	protected void imprimirSalvedadesFolio(Folio folio, boolean imprimirSalvedadesAnotacion, boolean print_SalvedadUsuarioEnabled) {
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
			String usuarioCreacionSalvedad;
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
                
                if(salvedad!=null && salvedad.getUsuarioCreacion()!=null && salvedad.getUsuarioCreacion().getIdUsuario()!=0){
                	usuarioCreacionSalvedad = " Usuario: " + salvedad.getUsuarioCreacion().getIdUsuario();
                }

				if( null == fechaSalve )
					fechaSalve = "";

				if( null == salvedadId )
					salvedadId = "";

				if( null == radicacion )
					radicacion = "";
				
				


				linea = formatSalvedadUsuario( local_SalvedadUsuario, print_SalvedadUsuarioEnabled ) +
					"Anotación Nro: "
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
				
				//linea = "Folio: "+folio.getIdMatricula()+" No. Salve: "+salvedadId+" Fecha Salve: "+fechaSalve;
				//linea = "Folio: "+folio.getIdMatricula()+" No. Salve: "+salvedadId+" Fecha Salve: "+fechaSalve;

				this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);

				String descripcion = salvedad.getDescripcion();
				this.imprimirLinea(ImprimibleConstantes.PLANO, descripcion);
				//this.imprimirLinea(ImprimibleConstantes.plano,"");
			}
		}

		// para cada anotacion, imprime el conjunto de
		// salvedades

        if( imprimirSalvedadesAnotacion ){

          List anotaciones = folio.getAnotaciones();
          for (int i = 0; i < anotaciones.size(); i++) {
            Anotacion anota = (Anotacion) anotaciones.get(i);
            if (null != anota)
              this.imprimirSalvedadesAnotacion(anota, print_SalvedadUsuarioEnabled);
          }
        }
	}


protected String
getSalvedadUsuario( SalvedadFolio local_Salvedad ) {
   if( null == local_Salvedad ) {
      return null;
   }
   String result;
   gov.sir.core.negocio.modelo.Usuario local_User;
   local_User = null;

   // assign
   if( null == local_User ) {
      local_User = local_Salvedad.getUsuarioCreacionTMP();
   }
   if( null == local_User ) {
      local_User = local_Salvedad.getUsuarioCreacion();
   }
   if( null == local_User ) {
      return null;
   }
   return( local_User.getUsername() );
} // end-method: getSalvedadUsuario

protected String
getSalvedadUsuario( SalvedadAnotacion local_Salvedad ) {
   if( null == local_Salvedad ) {
      return null;
   }
   String result;
   gov.sir.core.negocio.modelo.Usuario local_User;
   local_User = null;

   // assign
   if( null == local_User ) {
      local_User = local_Salvedad.getUsuarioCreacionTMP();
   }
   if( null == local_User ) {
      local_User = local_Salvedad.getUsuarioCreacion();
   }
   if( null == local_User ) {
      return null;
   }
   return( local_User.getUsername() );

} // end-method: getSalvedadUsuario

protected String
formatSalvedadUsuario( String local_SalvedadUsuarioTx, boolean enablePrint ) {

   if( !enablePrint ){
      return "";
   }
   if( null == local_SalvedadUsuarioTx ) {
      return "";
   }

   StringBuffer local_FormattedBufffer
       = new StringBuffer( 1024 );

   local_FormattedBufffer.append( "[" );
   local_FormattedBufffer.append( local_SalvedadUsuarioTx );
   local_FormattedBufffer.append( "]" );
   local_FormattedBufffer.append( ": " );

   return local_FormattedBufffer.toString();

} // end-method: formatSalvedadUsuario



	/**
	 * Imprime las salvedades de la anotación.
	 * @param anota
	 */
	protected void imprimirSalvedadesAnotacion(Anotacion anota, boolean print_SalvedadUsuarioEnabled) {

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
		String usuarioCreacionSalvedad;
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

            if(salvedad!=null && salvedad.getUsuarioCreacion()!=null && salvedad.getUsuarioCreacion().getIdUsuario()!=0){
            	usuarioCreacionSalvedad = " Usuario: " + salvedad.getUsuarioCreacion().getIdUsuario();
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

			String linea = formatSalvedadUsuario( local_SalvedadUsuario, print_SalvedadUsuarioEnabled ) + " " +
				" Anotación Nro: "
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

	}

	/**
	 * Función que retorna una cadena de texto dependiendo del modo,
	 * si el modo es <true> retorna la linea tal como llegó, pero si es <false>
	 * se retorna una cadena de texto con espacios.
	 * @param line
	 * @param modo
	 * @return
	 */
	protected String getLineFromModo(String line, boolean modo) {
		if (modo)
			return line;

		int tam = line.length();

		String cad = "";
		for (int i = 0; i < tam; i++)
			cad += " ";

		return cad;
	}

	/**
	 * Metodo quer retorna el número de renglones que ocupa
	 * una cadena de texto.
	 * @param linea Texto
	 * @param estilo constante que representa el estilo del texto.
	 * @return
	 */
	protected int getNumLinea(String linea, int estilo) {
		int permitido = ImprimibleConstantes.MAX_NUM_CHAR;

		if (estilo == this.tituloMicro)
			permitido = ImprimibleConstantes.MAX_NUM_CHAR_MICRO;

		int num = 1;

		if (linea.length() > permitido) {
			Vector lineas = this.getVectorLineas(linea, estilo);
			num = lineas.size();
		}

		return num;
	}

   protected void imprimirFinDocumento( int tamFinDocumento ) {
      imprimirFinDocumento( tamFinDocumento, true );
   }
	/**
	 * Imprime las ultimas lineas del Certificado.
	 */
	protected void imprimirFinDocumento(int tamFinDocumento, boolean checkPageBreak) {

		if( ( checkPageBreak )
                  &&( this.getI() >= (ImprimibleConstantes.MAXIMO_VERTICAL - tamFinDocumento) ) ) {
                    System.out.println(" @@ " + "Break Page");
                    System.out.println( "this.getI():" + this.getI() );
                    System.out.println( "tamFinDocumento:" + tamFinDocumento );
                    System.out.println( "ImprimibleConstantes.MAXIMO_VERTICAL" + ImprimibleConstantes.MAXIMO_VERTICAL );

                    this.goPageEnd();

		} // if

		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
		this.imprimirLinea(this.tituloMicro, this.microTexto);
		//this.imprimirLinea(ImprimibleConstantes.plano,"");
		//this.imprimirLinea(this.tituloMicro, this.microTexto);

                this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
 		this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.SEPARADOR1);

		this.imprimirLinea(
			ImprimibleConstantes.TITULO1,
			"                    F I N   D E  E S T E  D O C U M E N T O");

		if(! (this instanceof ImprimibleFolioSimple)){
			this.imprimirLinea(
				ImprimibleConstantes.TITULO2,
				"El interesado debe comunicar cualquier falla o error en el registro de los documentos");

		}
   }

	protected int getTamanoFinDocumento(){
		//-----------------------------------------------
		   int tamFirma = ImprimibleConstantes.TAMANO_FIRMA_ESTATICA;
		   int posY = this.getI() + tamFirma;

		   if(posY >= (ImprimibleConstantes.MAXIMO_VERTICAL)){
			   this.imprimirRellenoFinalPagina();
		   }
		  //------------------------------------------------

		 return tamFirma; //+ ImprimibleConstantes.TAMANO_ADICIONAL_FIRMA;
	}

	/**
	 * Imprime las ultimas lineas del Certificado.
	 */
	protected void imprimirFinDocumento( ) {

		if (this.getI() >= (ImprimibleConstantes.MAXIMO_ANTES_FIRMA))
			this.goPageEnd();

		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(this.tituloMicro, this.microTexto);
		//this.imprimirLinea(ImprimibleConstantes.plano,"");
		this.imprimirLinea(this.tituloMicro, this.microTexto);

		this.imprimirLinea(
			ImprimibleConstantes.TITULO1,
			"                    F I N   D E  E S T E  D O C U M E N T O");
		this.imprimirLinea(
			ImprimibleConstantes.TITULO2,
			"El interesado debe comunicar cualquier falla o error en el registro de los documentos");

	}


    /**
     * Imprime la lista de matriculas
     * @param folios_ID lista de tipo <Folio.ID>
     */
	protected void imprimirFoliosX(List folios_ID) {
		String matriculas = "";
		for (int i = 0; i < folios_ID.size(); i++) {
			Folio fid = (Folio) folios_ID.get(i);
			matriculas += fid.getIdMatricula() + "     ";

		}

		if (!matriculas.trim().equals(""))
			this.imprimirLinea(ImprimibleConstantes.PLANO, matriculas);

	}

    /**
     * Retorna el texto pequeñito que sirve de separador de secciones en un folio.
     * @param folio
     * @return
     */
	protected String getMicroTexto(Folio folio) {
		return this.getMicroTexto(folio,950);
	}

	/**
	 * Retorna el texto pequeñito que sirve de separador de secciones en un folio.
	 * @param folio
	 * @return
	 */
	protected String getMicroTexto(Folio folio, int numCaracteres) {

                //System.out.println( "@@ [Getting Micro from ImprimibleBase]" );

		if(this.isRegistro){
			return ImprimibleConstantes.SEPARADOR2;
		}

		String circuloReg = "";
		if(folio.getZonaRegistral()!=null &&  folio.getZonaRegistral().getCirculo()!=null && folio.getZonaRegistral().getCirculo().getNombre()!=null){
			circuloReg = folio.getZonaRegistral().getCirculo().getNombre();
		}

		//Obtener variables de loolupcodes
		String texto1 = TEXTO_BASE1;
		String texto2 = TEXTO_BASE2;

		String micro = texto1 + " " + texto2 + " DE " + circuloReg + " ";
		String microTexto1 = "";
		int tam = micro.length();
		for (int i = 0; i < numCaracteres; i++) {
			microTexto1 += micro.charAt(i % tam);
		}

		return microTexto1;
	}

	protected void imprimirRellenoFinalPagina(){

          System.out.println("@@ " + "SOF:PT05 bug3424-trace" );


		int numLineasAster = 10;
		int tamFirma = ImprimibleConstantes.TAMANO_FIRMA_ESTATICA;
		int maxi = ImprimibleConstantes.MAXIMO_VERTICAL - 12;
		int count = 0;

		this.setFlagPagina(0);

		while ((this.getI() < maxi) && this.getFlagPagina() == 0 /*(count < numLineasAster)*/) {
			String textoAsterisco = TEXTO_ASTERISCO;
			if (this instanceof ImprimibleCertificado)
			{
				textoAsterisco += "********";
			}
                        this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
			this.imprimirLinea(ImprimibleConstantes.PLANO, textoAsterisco);
			count++;
		}

          System.out.println("@@ " + "EOF:PT05 bug3424-trace" );

	}



	/**
	 * @return
	 */
	public boolean isSimple() {
		return simple;
	}

	/**
	 * @param b
	 */
	public void setSimple(boolean b) {
		simple = b;
	}

	/**
	 * @return
	 */
	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno t) {
		this.turno = t;
	}
	
	public String getTEXTO_BASE1() {
		return TEXTO_BASE1;
	}

	public void setTEXTO_BASE1(String texto_base1) {
		TEXTO_BASE1 = texto_base1;
	}

	public String getTEXTO_BASE2() {
		return TEXTO_BASE2;
	}

	public void setTEXTO_BASE2(String texto_base2) {
		TEXTO_BASE2 = texto_base2;
	}

	public boolean isImprimirDerivados() {
		return imprimirDerivados;
	}

	public void setImprimirDerivados(boolean imprimirDerivados) {
		this.imprimirDerivados = imprimirDerivados;
	}

	public boolean isImprimirUsuarioSalvedad() {
		return imprimirUsuarioSalvedad;
	}

	public void setImprimirUsuarioSalvedad(boolean imprimirUsuarioSalvedad) {
		this.imprimirUsuarioSalvedad = imprimirUsuarioSalvedad;
	}

	public List getAnotacionToCorrecion() {
		return anotacionToCorrecion;
	}

	public void setAnotacionToCorrecion(List anotacionToCorrecion) {
		this.anotacionToCorrecion = anotacionToCorrecion;
	}
	
	public boolean isRegistro() {
		return isRegistro;
	}
	public void setRegistro(boolean isRegistro) {
		if(!isRegistro){
        	this.tituloMicro = ImprimibleConstantes.TITULO_MICRO;
        }else{
        	this.tituloMicro = ImprimibleConstantes.PLANO;
        }
		this.isRegistro = isRegistro;
	}

}

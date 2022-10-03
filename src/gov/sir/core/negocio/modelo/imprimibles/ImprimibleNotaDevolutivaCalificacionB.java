package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;
import gov.sir.core.util.DateFormatUtil;
import java.awt.image.BufferedImage;

import java.awt.print.PageFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;


/**
 * @author Fernando Padilla
 * Se realiza refactoring a esta clase para los casos de mantis:
 * 1. 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA
 * 2. 2841: Acta - Requerimiento No 122 - Nota Devolutiva no salio impresa
 */
public class ImprimibleNotaDevolutivaCalificacionB extends ImprimibleBase{


    private static final long serialVersionUID = 1L;

    private Vector notas;

    private String nombreCirculo;
    private String turno;
    private String matricula;
    private byte[] pixelesImagenFirmaRegistrador = null;
    private boolean changetextforregistrador = false;
    private String nombreRegistrador = null;
    private String cargoRegistrador = null;
    private String textoCertificadosAsociados = null;
    private Documento documento = null;
    Usuario usuarioSIR = null;
    private String justificacionMayorValor = null;
    private ArrayList matriculasNoInscritas = new ArrayList();
    private ArrayList matriculasInscritasParcialmente = new ArrayList();

    private boolean imprimirMatricula = true;
    private String fechaImpresion;

    private String nombreDepartamento;

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

		
    public ArrayList getMatriculasInscritasParcialmente() {
			return matriculasInscritasParcialmente;
	}

	public void setMatriculasInscritasParcialmente(
			ArrayList matriculasInscritasParcialmente) {
		this.matriculasInscritasParcialmente = matriculasInscritasParcialmente;
	}

	public ArrayList getMatriculasNoInscritas() {
			return matriculasNoInscritas;
		}

		public void setMatriculasNoInscritas(ArrayList matriculasNoInscritas) {
			this.matriculasNoInscritas = matriculasNoInscritas;
		}

	/**
     * Constructor de la clase.
     * @param nota
     */
	public ImprimibleNotaDevolutivaCalificacionB(Vector notas, String nombreCirculo, String turno, String matricula, Documento documento, Usuario usuarioSIR, String textoCertificadosAsociados, String fechaImpresion,String tipoImprimible) {
		super(tipoImprimible);
		//setTransferObject(notas);
		this.notas = notas;
		this.nombreCirculo= nombreCirculo;
		this.turno = turno;
		this.matricula = matricula;
		this.documento = documento;
		this.usuarioSIR = usuarioSIR;
		this.textoCertificadosAsociados = textoCertificadosAsociados;
		this.fechaImpresion = fechaImpresion;
	}

        /**
         * @author Fernando Padilla
         * metodo que imprime las matriculas no inscritas
         */
        private void imprimirMatriculasNoInscritas(){

            String t = "El documento "+this.documento.getTipoDocumento().getNombre()+
                    " Nro "+this.documento.getNumero() +" del " +this.formatear(this.documento.getFecha(),"dd-MM-yyyy")+
                    crearOficinaDocumento()+ " fue presentado para su inscripción como solicitud de registro de documentos con Radicacion:" + this.turno;
//            this.imprimirLinea(ImprimibleConstantes.TITULO1,"El documento "+this.documento.getTipoDocumento().getNombre()+" Nro "+this.documento.getNumero() +" del " +this.formatear(this.documento.getFecha(),"dd-MM-yyyy")+crearOficinaDocumento());
  //          this.imprimirLinea(ImprimibleConstantes.TITULO1,"fue presentado para su inscripción como solicitud de registro de documentos con Radicacion:");

            if(this.matriculasNoInscritas.size()>1){
                t = t + " vinculado a las Matriculas Inmobiliarias:";
                this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ, t,130);
                this.imprimirLinea(ImprimibleConstantes.TITULO1, "",true);
                String line = this.concatenarMatriculas(matriculasNoInscritas);
                this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ, line,140);
                return;
            }

            if(this.matriculasNoInscritas.size()>0){
                t = t + " vinculado a la Matricula Inmobiliaria: "+ this.matriculasNoInscritas.get(0);
                this.imprimirLinea(ImprimibleConstantes.TITULO2,t,true);
                this.imprimirLinea(ImprimibleConstantes.TITULO1, "",true);
                return;
            }

            if(this.matriculasNoInscritas.size() == 0){
                t = t + " vinculado a la Matricula Inmobiliaria:";
                this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ, t,130);
            }

        }

        /**
         * @author Fernando Padilla
         * Este metodo imprime las maatriculas inscritas parcialmente
         */
        private void imprimirMatriculasInscriParcialmente(){

            String fraseMatriculas = "";

            if(this.matriculasInscritasParcialmente.size()>1){
                    fraseMatriculas = " y matrículas inscritas parcialmente: ";
            }else if(this.matriculasInscritasParcialmente.size()>0){
                    fraseMatriculas = " y matrícula inscrita parcialmente: ";
            }

            if(this.matriculasInscritasParcialmente.size()>0){
                String line = this.concatenarMatriculas(matriculasInscritasParcialmente);

                this.setNumCaracteres(22);
                Vector lineas = this.getVectorLineas(line, ImprimibleConstantes.TITULO_GRANDE2);
                /**
                * @author Fernando Padilla
                * Se modifica segun caso mantis: 6252: error en nota devolutiva
                */
                if(lineas.size()!=0){
                    this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,fraseMatriculas + line,true);
                }else{
                    this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,fraseMatriculas + line,true);
                }

            }else{
                this.imprimirLinea(ImprimibleConstantes.TITULO1,ImprimibleConstantes.MARGEN_IZQ,"", true);
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

		this.imprimirLinea(ImprimibleConstantes.TITULO_MICRO, ImprimibleConstantes.SEPARADOR3_PEQ);
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
		this.imprimirLinea(ImprimibleConstantes.TITULO_MICRO, ImprimibleConstantes.SEPARADOR3_PEQ);

	}
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
			
		} else {
			this.imprimirFirmaDigital(imagen);
		}
	}
/**
         * @author Fernando Padilla
         * Este metodo imprime las maatriculas inscritas parcialmente
         */
        private void imprimirNotas(){

            String descripcion = null;

            for(int i=0; i<this.notas.size();i++)
            {
                    Nota nota =(Nota)notas.get(i);
                    TipoNota tipoNota = nota.getTiponota();
                    String descripcionTipo= tipoNota.getDescripcion();
                    descripcion=  nota.getDescripcion();

                    String line = (i+1)+": "+descripcionTipo+"";

                    Vector lineas = this.getLineas(line);
                    for(int j=0; j<lineas.size();j++)
                    {
                       String linea = (String)lineas.get(j);
                       this.imprimirLinea(ImprimibleConstantes.TITULO2,linea);
                    }

                    if(descripcion!=null){
                            Vector lineas2 = this.getLineas(descripcion);
                            for(int j=0; j<lineas2.size();j++)
                            {
                               String linea2 = (String)lineas2.get(j);
                               this.imprimirLinea(ImprimibleConstantes.PLANO,linea2);
                            }
                               this.imprimirLinea(ImprimibleConstantes.PLANO,"");
                    }

            }
        }

    public boolean isChangetextforregistrador() {
        return changetextforregistrador;
    }

    public void setChangetextforregistrador(boolean changetextforregistrador) {
        this.changetextforregistrador = changetextforregistrador;
    }

	/**
	 * Genera el imprimible.
	 */
	public void generate(PageFormat pageFormat)
	{

            
            super.generate(pageFormat);
            this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,"");

            imprimirMatriculasNoInscritas();

            imprimirMatriculasInscriParcialmente();

            this.imprimirLinea(ImprimibleConstantes.TITULO2, (textoCertificadosAsociados!=null?textoCertificadosAsociados:""));


            this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,"");
            /**
             * @Autor: elora
             * @Mantis: 0012621
             * @Requerimiento: 055_453
             */
            this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,"Conforme con el principio de Legalidad previsto en el literal d) del articulo 3 y en el articulo 22 de la Ley 1579 de 2012 (Estatuto de Registro de Instrumentos Publicos) se inadmite y por lo tanto se devuelve sin registrar el presente documento por las siguientes razones y fundamentos de derecho:",135);

            this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ*4,"");
            
            imprimirNotas();

            if(justificacionMayorValor!=null)
                    this.imprimirLinea(ImprimibleConstantes.PLANO,justificacionMayorValor);

            /**
            * @author Fernando Padilla Velez
            * @change Mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA.
            * Se modifican los parrafos segun acata de requerimiento y se agrega un nuevo parrafo para su impresion
            * String descDevolutiva4 la cual se imprime tambien.
            */
            String descDevolutiva = "UNA VEZ SUBSANADA(S) LA(S) CAUSAL(ES) QUE MOTIVO " +
                    "LA NEGATIVA DE INSCRIPCION, FAVOR RADICAR NUEVAMENTE EN ESTA OFICINA, " +
                    "EL DOCUMENTO PARA SU CORRESPONDIENTE TRAMITE, ADJUNTANDO LA PRESENTE NOTA DEVOLUTIVA.";

            String descDevolutiva1 = "CUANDO LA " +
                    "CAUSAL O LAS CAUSALES QUE RECHAZA (N) LA INSCRIPCION DEL DOCUMENTO " +
                    "NO SEA (N) SUBSANABLE (S), SE CONFIGURE EL PAGO DE LO NO DEBIDO, SE PRODUZCAN " +
                    "PAGOS EN EXCESO O SE DESISTA DEL TRAMITE, EL TERMINO PARA SOLICITAR LA DEVOLUCION " +
                    "DE DINERO PARA LOS DERECHOS DE REGISTRO ES DE CUATRO  (4)  MESES CALENDARIO, CONTADOS A " +
                    "PARTIR DE LA FECHA DE EJECUTORIA DEL ACTO ADMINISTRATIVO QUE NIEGA EL REGISTRO O SE " +
                    "DESANOTE EL DOCUMENTO INSCRITO.";

            String descDevolutiva2 = "PARA SOLICITAR LA DEVOLUCION DE DINERO POR CONCEPTO DE IMPUESTO DE " +
                    "REGISTRO, DEBE DIRIGIRSE A LA GOBERNACION DEL DEPARTAMENTO DE "+this.getNombreDepartamento()+", EN LOS " +
                    "TERMINOS DEFINIDOS POR EL ARTICULO 15 DEL DECRETO 650 DE 1996.";
            
            /**
             * @Autor: elora
             * @Mantis: 0012621
             * @Requerimiento: 055_453
             */
            String descDevolutiva3 = "LOS ACTOS O NEGOCIOS JURIDICOS A QUE SE REFIERE EL ARTICULO 4 " +
                    "DE LA LEY 1579 DE 2012, DEBERAN PRESENTARSE PARA SU INSCRIPCION, " +
                    "DENTRO DE LOS DOS (2) MESES CALENDARIO, SIGUIENTES A LA FECHA DE SU OTORGAMIENTO PARA " +
                    "ACTOS NOTARIALES O LA FECHA DE EJECUTORIA PARA PROVIDENCIAS JUDICIALES O ADMINISTRATIVAS; " +
                    "VENCIDOS LOS CUALES, SE COBRARAN INTERESES MORATORIOS POR IMPUESTO DE REGISTRO, PREVISTOS " +
                    "EN LA LEY 223 DE 1995 Y SU DECRETO REGLAMENTARIO 695 DE 1996 ARTICULO 14. ";

            String descDevolutiva4 = "EXCEPTUESE DE LO ANTERIOR, LOS CASOS RELACIONADOS CON EL NEGOCIO JURIDICO " +
                    /**
                    * @Autor: elora
                    * @Mantis: 0012621
                    * @Requerimiento: 055_453
                    */
                    "DE HIPOTECA Y EL ACTO DE CONSTITUCION DE PATRIMONIO DE FAMILIA DE QUE TRATA EL ARTICULO 28 " +
                    "DE LA LEY 1579 DE 2012, LOS CUALES SE DEBEN REGISTRAR DENTRO DE LOS NOVENTA (90)  " +
                    "DIAS HABILES SIGUIENTES A SU AUTORIZACION. VENCIDO EL TERMINO REGISTRAL ANTES SENALADO, " +
                    /**
                    * @Autor: elora
                    * @Mantis: 0012621
                    * @Requerimiento: 055_453
                    */
                    "DEBERAN CONSTITUIRSE DE CONFORMIDAD CON EL PRECITADO ARTICULO.";

            String descDevolutiva5 = "CONTRA EL PRESENTE ACTO ADMINISTRATIVO, PROCEDE EL RECURSO DE REPOSICION ANTE"
                    + " EL REGISTRADOR DE INSTRUMENTOS PUBLICOS Y EN SUBSIDIO, EL DE APELACION ANTE EL"
                    + " DIRECTOR DE REGISTRO DE LA SUPERINTENDENCIA DE NOTARIADO Y REGISTRO DENTRO DE"
                    + " LOS DIEZ(10) DIAS HABILES SIGUIENTES A SU NOTIFICACION, EN VIRTUD DE LO PREVISTO"
                    /**
                    * @Autor: elora
                    * @Mantis: 0012621
                    * @Requerimiento: 055_453
                    */
                    + " POR EL NUMERAL DOS (2) DEL ARTICULO 24 DEL DECRETO 2163 DEL 17 DE JUNIO DE 2011, PREVIO"
                    + " EL CUMPLIMIENTO DE LOS REQUISITOS ESTABLECIDOS EN LOS ARTICULOS 76 Y 77, LEY 1437"
                    + " DE 2011 (CODIGO DE PROCEDIMIENTO ADMINISTRATIVO Y DE LO CONTENCIOSO ADMINISTRATIVO).";

            this.imprimirLinea(ImprimibleConstantes.PLANO,"");
            this.imprimirLinea(ImprimibleConstantes.PLANO,"");
            this.imprimirLinea(ImprimibleConstantes.PLANO,"");

            this.imprimirLinea(ImprimibleConstantes.PEQUE,descDevolutiva);

            this.imprimirLinea(ImprimibleConstantes.PEQUE,"");

            this.imprimirLinea(ImprimibleConstantes.PEQUE,descDevolutiva1);

            this.imprimirLinea(ImprimibleConstantes.PEQUE,"");

            this.imprimirLinea(ImprimibleConstantes.PEQUE,descDevolutiva2);

            this.imprimirLinea(ImprimibleConstantes.PEQUE,"");

            this.imprimirLinea(ImprimibleConstantes.PEQUE,descDevolutiva3);

            this.imprimirLinea(ImprimibleConstantes.PEQUE,"");

            /**
            * @author Fernando Padilla Velez
            * @change Mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA, se agrega otra parrafo.
            */
            this.imprimirLinea(ImprimibleConstantes.PEQUE,descDevolutiva4);

            this.imprimirLinea(ImprimibleConstantes.PEQUE,"");

            this.imprimirLinea(ImprimibleConstantes.PEQUE,descDevolutiva5);

            this.imprimirLinea(ImprimibleConstantes.PEQUE,"");

            this.imprimirFirmaNotaDevolutiva();

            this.imprimirEntregado();

	}

	/**
	 * Imprime el encabezado de la nota devolutiva.
	 */
        protected void makeNewPage() {

            super.makeNewPage();

            String oficinaReg = StringFormat.getCentrada("OFICINA DE REGISTRO DE INSTRUMENTOS PÚBLICOS DE " + nombreCirculo,
            ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
            ImprimibleConstantes.LONG_LOGO);

            this.imprimirLinea(ImprimibleConstantes.TITULO1, oficinaReg);

            this.imprimirLinea(ImprimibleConstantes.TITULO1, ImprimibleConstantes.MARGEN_IZQ * 4, "");


            String titulo = StringFormat.getCentrada("NOTA DEVOLUTIVA",
                    ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,ImprimibleConstantes.LONG_LOGO);

            int tam = this.notas.size();
            if (tam > 0) {
                Nota nota = (Nota) this.notas.get(0);
                if (nota.getTiponota().isDevolutiva()) {
                    titulo = StringFormat.getCentrada("NOTA DEVOLUTIVA",
                            ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,ImprimibleConstantes.LONG_LOGO);
                }
            }

            this.imprimirLinea(ImprimibleConstantes.TITULO1, titulo);
            this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 10, "");
            this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ, "Página: " + this.getNumberOfPages());

            /**
            * @author Fernando Padilla Velez
            * @change Mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
            *         Se modifica para que la fecha de impresion este centrada.
            */
            String fechaImp = this.fechaImpresion;
            fechaImp = StringFormat.getCentrada(fechaImp,ImprimibleConstantes.MAX_NUM_CHAR, 15);

            this.imprimirLinea(ImprimibleConstantes.PLANO, fechaImp);
            this.imprimirLinea(ImprimibleConstantes.TITULO2, ImprimibleConstantes.MARGEN_IZQ, "");

        }
	
	/**
	 * Imprime el listado de matrículas
	 * @param matriculas
	 */
	private String concatenarMatriculas(ArrayList matriculas){
		Iterator itMatriculas = matriculas.iterator();
		String idMatriculas = "";
		while(itMatriculas.hasNext()){
			idMatriculas = idMatriculas + "  " + itMatriculas.next();
		}
		return idMatriculas;
	}
	
	/**
	 * Imprime el listado de matrículas
	 * @param matriculas
	 */
	private void imprimirMatriculas(ArrayList matriculas){
		Iterator itMatriculas = matriculas.iterator();
		String idMatriculas = "";
		while(itMatriculas.hasNext()){
			idMatriculas += "  " + itMatriculas.next();
		}
		this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,ImprimibleConstantes.MARGEN_IZQ,idMatriculas);
	}

	/**
	 * Funcion que recibe una cadena de texto que contiene caracteres especiales de fin de linea '\r' y '\n'
	 * indicando que se debe generar una nueva linea en un texto.
	 * @param line Linea con todo el texto.
	 * @return un vector de Strings con el mismo texto original, pero cada elemento del vector de respuesta
	 * representa un renglón en el texto.
	 */
        private Vector getLineas(String line){

            String cad = "";
            for (int i=0; i<line.length();i++){
                if (line.charAt(i)!='\r')
                  cad+= line.charAt(i);
            }

            String cadenas[] = cad.split("\n");

            int tam = cadenas.length;
            String cad2 = "";

            Vector vectorCad= new Vector();
            for (int i = 0; i < tam; i++){
                vectorCad.add(cadenas[i]);
            }

            return vectorCad;
	 }

    public boolean isImprimirMatricula() {
        return imprimirMatricula;
    }

    public void setImprimirMatricula(boolean imprimirMatricula) {
        this.imprimirMatricula = imprimirMatricula;
    }


	protected void imprimirFirmaNotaDevolutiva() {
                this.imprimirFirma();
                
		String calificador = "FUNCIONARIO CALIFICADOR";
		String registrador = "REGISTRADOR DE INSTRUMENTOS PUBLICOS";
                String linea ="==============================================================================================================";
		/**
                * @author Fernando Padilla Velez
                * @change Mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA.
                *         Se modifica el fin del documento.
                */
                String finDocumento ="                      FIN DE ESTE ACTO ADMINISTRATIVO";


		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,"");

                this.imprimirLinea(ImprimibleConstantes.TITULO2, calificador, false);
                if(!this.isChangetextforregistrador()){
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, 270 , registrador, true);
                }else{
                    this.imprimirLinea(ImprimibleConstantes.TITULO2, 270 , "", true);
                }
                
		String nombreCalificador = "  " + (usuarioSIR!=null&&""+usuarioSIR.getIdUsuario()!=null?""+usuarioSIR.getIdUsuario():"");
		this.imprimirLinea(ImprimibleConstantes.TITULO2, nombreCalificador);

		this.imprimirLinea(ImprimibleConstantes.PLANO,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,linea);
		this.imprimirLinea(ImprimibleConstantes.TITULO1,finDocumento);

	}

        protected void imprimirEntregado() {

            int h = (ImprimibleConstantes.SEPARACION_LINE * 13);

            int hActual = this.getI();

            if((hActual + h) > ImprimibleConstantes.MAXIMO_VERTICAL){
                    this.goPageEnd();
                    this.imprimirLinea(ImprimibleConstantes.PLANO, "");
            }

            this.imprimirLinea(ImprimibleConstantes.PLANO,"");
            this.imprimirLinea(ImprimibleConstantes.PLANO,"");

            String notificacion = "      N O T I F I C A C I Ó N     P E R S O N A L ";

            /**
            * @author Fernando Padilla Velez
            * @change Mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA.
            * Se modifica el parrafo de notificacion personal y se elmina la variable String linea4 asi como su impresion.
            */
             
            /**
            * @author Carlos Torres
            * @change Mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
            * Se modifica el parrafo de notificacion personal.
            */
            String linea1 = "CONFORME LO DISPUESTO EN EL ARTICULO 67 DEL CODIGO DE PROCEDIMIENTO ADMINISTRATIVO Y DE LO CONTENCIOSO ADMINISTRATIVO, EN LA FECHA ____________________";
            String linea2 = "SE NOTIFICO PERSONALMENTE EL PRESENTE ACTO ADMINISTRATIVO A ________________________________________________,";
            String linea3 = "QUIEN SE IDENTIFICÓ CON _____________________ No. _____________________________________.";

            String lineas = "_____________________";

            String notificador = "FUNCIONARIO NOTIFICADOR";
            String notificado = "EL NOTIFICADO";

            this.imprimirLinea(ImprimibleConstantes.PLANO,"");
            this.imprimirLinea(ImprimibleConstantes.PLANO,"");
            this.imprimirLinea(ImprimibleConstantes.PLANO,"");
            this.imprimirLinea(ImprimibleConstantes.PLANO,"");

            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, 160 , notificacion);

            this.imprimirLinea(ImprimibleConstantes.PLANO,"");
            this.imprimirLinea(ImprimibleConstantes.PLANO,linea1);

            this.imprimirLinea(ImprimibleConstantes.PLANO,linea2);

            this.imprimirLinea(ImprimibleConstantes.PLANO,linea3);

            this.imprimirLinea(ImprimibleConstantes.PLANO,"");
            this.imprimirLinea(ImprimibleConstantes.PLANO,"");
            this.imprimirLinea(ImprimibleConstantes.PLANO,"");
            this.imprimirLinea(ImprimibleConstantes.PLANO,"");

            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, lineas, false);
            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, 270 , lineas, true);

            this.imprimirLinea(ImprimibleConstantes.TITULO2, notificador, false);
            this.imprimirLinea(ImprimibleConstantes.TITULO2, 270 , notificado, true);

            this.imprimirLinea(ImprimibleConstantes.PLANO,"");
            
            this.imprimirLinea(ImprimibleConstantes.TITULO2,this.documento.getTipoDocumento().getNombre()+
                    " Nro "+this.documento.getNumero() +" del " +this.formatear(this.documento.getFecha(),"dd-MM-yyyy")+
                    crearOficinaDocumento(), true);
            this.imprimirLinea(ImprimibleConstantes.TITULO2,"RADICACION: "+this.turno, true);

	}


        private String crearOficinaDocumento(){
            String oficinaDocumento = "";

            if(this.documento.getOficinaInternacional() != null){
                return " de " + this.documento.getOficinaInternacional();
            }
            OficinaOrigen oficinaOrigen = this.documento.getOficinaOrigen();
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

                oficinaDocumento =  oficinaDocumento + " de "+ (oficinaOrigen!=null&&oficinaOrigen.getNombre()!=null?oficinaOrigen.getNombre():" ") + ubicacion;
            }
            return oficinaDocumento;
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
	 * @return
	 */
	public byte[] getPixelesImagenFirmaRegistrador() {
		return pixelesImagenFirmaRegistrador;
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

	/**
	 * @param bs
	 */
	public void setPixelesImagenFirmaRegistrador(byte[] bs) {
		pixelesImagenFirmaRegistrador = bs;
	}

	public  String formatear(Date fecha, String formato){
		if (formato != null){
			return DateFormatUtil.format(formato, fecha);
		}
		else{
			return fecha.toString();
		}
	}

	/**
	 * @return
	 */
	public String getTurno() {
		return turno;
	}
	/**
	 * @return LA JUSTIFICACION DE MAYOR VALOR
	 */
	public String getJustificacionMayorValor() {
		return justificacionMayorValor;
	}
	/**
	 * @ CAMBIA LA JUSTIFICACION DE MAYOR VALOR 
	 */
	public void setJustificacionMayorValor(String justificacionMayorValor) {
		this.justificacionMayorValor = justificacionMayorValor;
	}

}

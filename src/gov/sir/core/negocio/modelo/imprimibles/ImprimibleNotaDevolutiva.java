package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.Usuario;

import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import java.awt.print.PageFormat;

import java.util.Vector;

/**
 * @author gvillal
 * Clase que representa el imprimible de una nota devolutiva.
 */
public class ImprimibleNotaDevolutiva extends ImprimibleBase{

    private Vector notas;
        private static final long serialVersionUID = 1L;
	private String nombreCirculo;
	private String turno;
	private String turnoPadre = null;
	private String matricula;
	Usuario usuarioSIR = null;
	private boolean certMasivos = false;
	private boolean imprimirMatricula = true;
	private String fechaImpresion;
	private boolean rolSuperDoc= false;
	//private String nombreUsuario;

    /**
     * Constructor de la clase.
     * @param nota
     */
	public ImprimibleNotaDevolutiva(Vector notas, String nombreCirculo, String turno, String matricula, String fechaImpresion, String tipoImprimible)
	{
		super(tipoImprimible);
		//setTransferObject(notas);
		this.notas = notas;
		this.nombreCirculo= nombreCirculo;
		this.turno = turno;
		this.matricula = matricula;
		this.fechaImpresion = fechaImpresion;
		//this.nombreUsuario = nombreUsuario;
		}

	/**
	 * Genera el imprimible.
	 */
	public void generate(PageFormat pageFormat)
	{

		super.generate(pageFormat);
		//this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ*4,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ*4,"");

		for(int i=0; i<this.notas.size();i++)
		{
		  	Nota nota =(Nota)notas.get(i);
		  	TipoNota tipoNota = nota.getTiponota();
		  	String nombre = tipoNota.getNombre();
		  	String descripcionTipo = tipoNota.getDescripcion();
			String descripcion = nota.getDescripcion();

		  	String line = (i+1)+": "+(descripcionTipo == null ? "" : descripcionTipo) + ":";

			Vector lineas = this.getLineas(line);
			for(int j=0; j<lineas.size();j++)
			{
			   String linea = (String)lineas.get(j);
			   this.imprimirLinea(ImprimibleConstantes.TITULO2,linea);
			}

			if(descripcion==null){
				descripcion = "";
			}

			lineas = this.getLineas(descripcion);
			for(int j=0; j<lineas.size();j++)
			{
			   String linea = (String)lineas.get(j);
			   this.imprimirLinea(ImprimibleConstantes.PLANO,linea);
			}

			this.imprimirLinea(ImprimibleConstantes.PLANO,"");
			this.imprimirLinea(ImprimibleConstantes.PLANO,"");
			this.imprimirLinea(ImprimibleConstantes.PLANO,"");
			this.imprimirLinea(ImprimibleConstantes.PLANO,"Usuario: " + nota.getUsuario().getNombreCompletoUsuario());
			this.imprimirLinea(ImprimibleConstantes.PLANO,"Fase de Generacion: " + nota.getIdFase());
			

		}
		if (this.certMasivos)
		{
			this.imprimirLinea(ImprimibleConstantes.PEQUE,"");
			this.imprimirLinea(ImprimibleConstantes.PEQUE,"");
			
			if (this.rolSuperDoc)
			{
				String descDevolutiva3 = "LA DEVOLUCION DE LOS DINEROS CORRESPONDIENTES A LA SOLICITUD DE CERTIFICADOS DE TRADICIÓN NO EXPEDIDOS, SE EFECTUARA UNICAMENTE EN LAS OFICINAS PAGADORAS DE RED EFECTIVO S.A. " +
				"(CONVENIO No. 02 DE 2002 SUSCRITO ENTRE LA SUPERINTENDENCIA DE NOTARIADO Y REGISTRO Y LA UNION TEMPORAL SUPERDOCUMENTO).";
				this.imprimirLinea(ImprimibleConstantes.PEQUE,descDevolutiva3);
				this.imprimirLinea(ImprimibleConstantes.PEQUE,"");
			}

			String nombreCalificador = (usuarioSIR!=null&&""+usuarioSIR.getIdUsuario()!=null?""+usuarioSIR.getIdUsuario():"");
			this.imprimirLinea(ImprimibleConstantes.TITULO2, nombreCalificador);

			String linea ="==============================================================================================================";
			String finDocumento ="                      FIN DE ESTE DOCUMENTO";
			this.imprimirLinea(ImprimibleConstantes.PLANO,linea);
			this.imprimirLinea(ImprimibleConstantes.TITULO1,finDocumento);
		}
	}

	/**
	 * Imprime el encabezado de la nota devolutiva.
	 */
	protected void makeNewPage()
	  {
		  super.makeNewPage();

		  this.imprimirLinea(ImprimibleConstantes.TITULO1,ImprimibleConstantes.MARGEN_IZQ*4,"OFICINA DE REGISTRO DE INSTRUMENTOS PÚBLICOS DE "+nombreCirculo);
		  this.imprimirLinea(ImprimibleConstantes.TITULO1,ImprimibleConstantes.MARGEN_IZQ*4,"");


		  String titulo = "NOTA INFORMATIVA";

		  int tam = this.notas.size();
		  if (tam>0)
		  {
			    Nota nota = (Nota)this.notas.get(0);
			    if (nota.getTiponota().isDevolutiva())
			    {
			    	titulo = "NOTA DEVOLUTIVA";
			    }
			    if(nota.getTiponota().isDevolutiva() && nota.getIdFase().equals("ANT_REVISION_INICIAL"))
			    	titulo = "NOTA INFORMATIVA DEVOLUCION CERTIFICADOS DE ANTIGUO SISTEMA";
		  }

		  this.imprimirLinea(ImprimibleConstantes.TITULO1,ImprimibleConstantes.MARGEN_IZQ*4,titulo);

		  this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ*10,"");


		  String fechaImp = this.fechaImpresion;


		  this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ*4, fechaImp);


		  this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ,"Página: " +this.getNumberOfPages());

		  //this.imprimirLinea(ImprimibleConstantes.TITULO1,ImprimibleConstantes.MARGEN_IZQ*4,"TURNO: "+this.turno);

		  String textoSinExpedir = "El Certificado con No. de Radicación: " + this.turno;
		  if (this.turnoPadre != null)
			  textoSinExpedir = textoSinExpedir + " y turno de Certificados Masivos No.: "+this.turnoPadre;

		  String textoSinExpedirConMatricula = " y Matrícula Inmobiliaria: " + this.matricula;

                  if( isImprimirMatricula() )
                  {

                	  textoSinExpedir = textoSinExpedir + textoSinExpedirConMatricula;

                    // Bug 3814
		    // this.imprimirLinea(ImprimibleConstantes.TITULO1,ImprimibleConstantes.MARGEN_IZQ*4,"MATRICULA: "+this.matricula);
                    //imprimirPagina_Encabezado_Matricula( this.matricula );
                  }
                  this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,textoSinExpedir);

                  if (this.certMasivos)
                  {
                	  this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,"Se devuelve SIN EXPEDIR por las siguientes razones: ");
                  }

		  //this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,"ESCRITURA Nro. "+"del "+"dd-mm-aaaa" +" de "+" NOTARIA XX "+"Y CERTIFICADO ASOCIADO:"+"YYYYYYYY");

	  }



     private void imprimirPagina_Encabezado_Matricula( String matricula ) {

        System.out.println("@@ Printing IdFolio (Start)");

        this.imprimirLinea( ImprimibleConstantes.TITULO1,
                            (int)( ImprimibleConstantes.MARGEN_IZQ * 4.0d ), "MATRICULA: ", false );
        this.imprimirLinea( ImprimibleConstantes.TITULO_GRANDE,
                            (int)( ImprimibleConstantes.MARGEN_IZQ * 6.0d ), this.matricula );
        System.out.println("@@ Printing IdFolio (End)");

     } // :




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

    public boolean isImprimirMatricula() {
        return imprimirMatricula;
    }

    public void setImprimirMatricula(boolean imprimirMatricula) {
        this.imprimirMatricula = imprimirMatricula;
    }

	public boolean isCertMasivos() {
		return certMasivos;
	}

	public void setCertMasivos(boolean certMasivos) {
		this.certMasivos = certMasivos;
	}

	public Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

	public void setUsuarioSIR(Usuario usuarioSIR) {
		this.usuarioSIR = usuarioSIR;
	}

	public String getTurnoPadre() {
		return turnoPadre;
	}

	public void setTurnoPadre(String turnoPadre) {
		this.turnoPadre = turnoPadre;
	}

	public boolean isRolSuperDoc() {
		return rolSuperDoc;
	}

	public void setRolSuperDoc(boolean rolSuperDoc) {
		this.rolSuperDoc = rolSuperDoc;
	}


}

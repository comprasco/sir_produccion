package gov.sir.core.negocio.modelo.imprimibles;

import java.util.Vector;

/**
 * @author ahurtado
 */
public class
ImprimibleFotocopiaNoExpedirSolicitudFotocopiasAlLiquidar
extends ImprimibleNotaDevolutiva {
        private static final long serialVersionUID = 1L;
    /**
     * Constructor de la clase.
     * @param nota
     */
	public ImprimibleFotocopiaNoExpedirSolicitudFotocopiasAlLiquidar( Vector notas, String nombreCirculo, String turno, String matricula, String fechaImpresion ,String tipoImprimible) {
		super( notas, nombreCirculo, turno, matricula, fechaImpresion,tipoImprimible);
                setImprimirMatricula( false );
	}

	/**
	 * Genera el imprimible.
	 */
        /*
	public void generate(PageFormat pageFormat)
	{

		super.generate(pageFormat);
		this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ*4,"");
		this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ*4,"");

		for(int i=0; i<this.notas.size();i++)
		{
		  	Nota nota =(Nota)notas.get(i);
		  	TipoNota tipoNota = nota.getTiponota();
		  	String nombre= tipoNota.getNombre();
		  	String descripcionTipo= tipoNota.getDescripcion();
			String descripcion=  nota.getDescripcion();

		  	String line = (i+1)+": "+descripcionTipo+":";

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


		}

	}
	*/
	/**
	 * Imprime el encabezado de la nota devolutiva.
	 */
        /*
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
		  }

		  this.imprimirLinea(ImprimibleConstantes.TITULO1,ImprimibleConstantes.MARGEN_IZQ*4,titulo);

		  this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ*10,"");


		  String fechaImp = this.getFechaImpresion();


		  this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ*4, fechaImp);


		  this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ,"Página: " +this.getNumberOfPages());

		  this.imprimirLinea(ImprimibleConstantes.TITULO1,ImprimibleConstantes.MARGEN_IZQ*4,"TURNO: "+this.turno);
		  this.imprimirLinea(ImprimibleConstantes.TITULO1,ImprimibleConstantes.MARGEN_IZQ*4,"MATRICULA: "+this.matricula);

    	  //this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,"El documento con No. de radicación:" +"xxxx" +" y matrícula Inmobiliaria: "+"yyyyy");
		  //this.imprimirLinea(ImprimibleConstantes.TITULO2,ImprimibleConstantes.MARGEN_IZQ,"ESCRITURA Nro. "+"del "+"dd-mm-aaaa" +" de "+" NOTARIA XX "+"Y CERTIFICADO ASOCIADO:"+"YYYYYYYY");

	  }
	  */
	/**
	 * Funcion que recibe una cadena de texto que contiene caracteres especiales de fin de linea '\r' y '\n'
	 * indicando que se debe generar una nueva linea en un texto.
	 * @param line Linea con todo el texto.
	 * @return un vector de Strings con el mismo texto original, pero cada elemento del vector de respuesta
	 * representa un renglón en el texto.
	 */


}

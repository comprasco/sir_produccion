package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.imprimibles.base.IGlosarioImprimibles;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleHelper;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;

import java.awt.print.PageFormat;

/**
 * @author gvillal
 *  Imprimible de una constancia de inscripcion.
 */
public class ImprimibleConstanciaInscripcion extends ImprimibleCertificado
{
    private static final long serialVersionUID = 1L;
	/**
	 * Constructor de la clase
	 * @param folio 
	 * @param turno 
	 */
	public ImprimibleConstanciaInscripcion(Folio folio,Turno turno, String fechaImpresion,String tipoImprimible, String tbase1, String tbase2) 
	{
		super(folio,turno,null,null,null,fechaImpresion,tipoImprimible,tbase1, tbase2);
		setTransferObject(folio);
		
	}

    /**
     * Genera el vector de hojas imprimibles con toda la información que se va a imprimir.
     */
	public void generate(PageFormat pageFormat) {


	    this.setPageFormat(pageFormat);
		this.imprimirEncabezado();
	
		String turno = (String)ImprimibleHelper.getDatoFromFolio(folio,IGlosarioImprimibles.radicacion);
		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		this.imprimirLinea(ImprimibleConstantes.PLANO, "Con el turno "+turno);
		this.imprimirLinea(ImprimibleConstantes.PLANO, "");
		
		
	
		String depto = (String)ImprimibleHelper.getDatoFromFolio(folio,IGlosarioImprimibles.departamento);	
		String municipio = (String)ImprimibleHelper.getDatoFromFolio(folio,IGlosarioImprimibles.municipio);	
		String tipoPred = (String)ImprimibleHelper.getDatoFromFolio(folio,IGlosarioImprimibles.tipoPredio);	
		
		String circuloReg =(String)ImprimibleHelper.getDatoFromFolio(folio,IGlosarioImprimibles.circuloRegistral);
		
		if (!circuloReg.equals("")){
			this.imprimirLinea(ImprimibleConstantes.PLANO,"CIRCULO DE REGISTRO: " + circuloReg);
			this.imprimirLinea(ImprimibleConstantes.PLANO,"MUNICIPIO: "+ municipio+ImprimibleConstantes.ESPACIOS+"DEPARTAMENTO: "+depto+ImprimibleConstantes.ESPACIOS+"TIPO PREDIO: "+tipoPred);
		}
		
		this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.SEPARADOR1);

		this.imprimirLinea(ImprimibleConstantes.PLANO, "");		
		this.imprimirLinea(ImprimibleConstantes.PLANO, "DIRECCION DEL IMMUEBLE");
		this.imprimirDirecciones(this.folio,false);
	
		this.imprimirAnotaciones(this.folio);
	
		this.imprimirLinea(ImprimibleConstantes.PLANO, ImprimibleConstantes.MARGEN_IZQ * 4, "");
		
		this.imprimirFinDocumento();
		this.imprimirFirma();
		this.imprimirLinea(ImprimibleConstantes.PLANO, "ABOGAXXX,");
		
	}
	

	/**
	 * Imprime el encabezado de la constancia de inscripción.
	 */
	protected void imprimirEncabezado() {

		
		String linea; 
		linea = StringFormat.getCentrada("REPRODUCCION DE CONSTANCIA DE INSCRIPCION",ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,ImprimibleConstantes.LONG_LOGO);
		this.imprimirLinea(ImprimibleConstantes.TITULO1,linea); 
		
		String matricula = (String)ImprimibleHelper.getDatoFromFolio(folio,IGlosarioImprimibles.matricula);			
		this.imprimirLinea(ImprimibleConstantes.TITULO1,ImprimibleConstantes.MARGEN_IZQ*10,"Nro Matrícula: " + matricula);
		
		
	}	

}

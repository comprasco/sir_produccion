/*
 * Created on 08-mar-2005
 */
package gov.sir.core.negocio.modelo.imprimibles;

import java.util.List;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.imprimibles.base.IGlosarioImprimibles;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleHelper;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;


/**
 * @author gvillal
 * Clase que imprime un Folio Simple, sin firma ni texto en miniatura.
 */
public class ImprimibleFolioSimple extends ImprimibleCertificado {
    public static final String NOTA_NO_ES_CERTIFICADO = "No es un certificado, solo sirve de consulta";
   private static final long serialVersionUID = 1L; 
    public ImprimibleFolioSimple(Folio folio, List padres, List hijos, List<FolioDerivado> foliosDerivadoHijos, String fechaImpresion,String tipoImprimible, String tbase1, String tbase2) {
        /**
        * @author: David Panesso
        * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
        * Nuevo listado de folios derivados
        **/
        super(folio, null, padres, hijos, foliosDerivadoHijos, fechaImpresion,tipoImprimible,tbase1, tbase2);
        this.tituloMicro = ImprimibleConstantes.TITULO_MICRO;
		super.setImprimirMargen(false);
		super.setImprimirLogoEnabled(false);
        //this.tituloMicro = ImprimibleConstantes.PLANO;
        this.simple = true;
    }

	public ImprimibleFolioSimple(Folio folio,String tipoImprimible, String tbase1, String tbase2) {
		super(folio, null, null, null, null, null,tipoImprimible, tbase1, tbase2);
		this.tituloMicro = ImprimibleConstantes.TITULO_MICRO;

		//this.tituloMicro = ImprimibleConstantes.PLANO;
		this.simple = true;
	}


    /**
     * Imprime el encabezado del certificado, con un titulo predeterminado.
     */
    protected void imprimirEncabezadoTitulo(String titulo) {
        String linea;

        linea = StringFormat.getCentrada(titulo,
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);
        this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);

        String circuloReg = (String) ImprimibleHelper.getDatoFromFolio(folio,
                IGlosarioImprimibles.circuloRegistral);
        linea = StringFormat.getCentrada("DE " + circuloReg,
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);
        this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);

        if (this.isRegistro()) {
        	linea = StringFormat.getCentrada("IMPRESION FOLIO NUEVO",
        			ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
        			ImprimibleConstantes.LONG_LOGO);
        	this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);
        } else {
        	linea = StringFormat.getCentrada("IMPRESION SIMPLE DE FOLIO",
        			ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
        			ImprimibleConstantes.LONG_LOGO);
        	this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);
        }
        String matricula = (String) ImprimibleHelper.getDatoFromFolio(folio,
                IGlosarioImprimibles.matricula);

        this.imprimirLinea(ImprimibleConstantes.PLANO,
            "Página: " + this.getNumberOfPages());

        this.imprimirLinea(ImprimibleConstantes.TITULO1, "");

        if(!this.isRegistro()){
        	this.imprimirLinea(ImprimibleConstantes.TITULO1,
        			ImprimibleConstantes.MARGEN_IZQ * 10,
                    "Nro Matrícula: ", false);
        	this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,
                    (ImprimibleConstantes.MARGEN_IZQ + 7) * 10,
                    matricula);
        }else{
	        this.imprimirLinea(ImprimibleConstantes.TITULO1,
	            ImprimibleConstantes.MARGEN_IZQ * 10, "Nro Matrícula: " +
	            matricula);
	    }
        
        this.imprimirLinea(ImprimibleConstantes.TITULO1, "");
        
        String texto = null;
        
        /**BUG 3488*/
        if(!this.isRegistro()){
	        texto= NOTA_NO_ES_CERTIFICADO;
        }else{
         	texto = ImprimibleConstantes.CVALIDEZ_TEMP;
        }
	        texto = texto.toUpperCase();
	        linea = StringFormat.getCentrada(texto,
	                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
	                ImprimibleConstantes.LONG_LOGO);
	        this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);
       
	    String fechaImp = this.fechaImpresion;

        linea = StringFormat.getCentrada(fechaImp,
                ImprimibleConstantes.MAX_NUM_CHAR, 15);
        this.imprimirLinea(ImprimibleConstantes.PLANO, linea);

        this.imprimirLinea(ImprimibleConstantes.PLANO,
            ImprimibleConstantes.MARGEN_IZQ * 4, "");
    }

    /**Método que imprime la firma al final del documento**/
    protected void imprimirFirma() {
    }

    /**
     * Imprime las ultimas lineas del Certificado.
     */
    protected void imprimirFinDocumento() {
    }

    protected void imprimirInfoTurno() {
    }

}


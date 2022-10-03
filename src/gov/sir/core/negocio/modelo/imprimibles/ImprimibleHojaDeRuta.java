/*
 * Created on 17-mar-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.imprimibles.base.IGlosarioImprimibles;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleHelper;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;

import java.awt.print.PageFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author gvillal
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ImprimibleHojaDeRuta extends ImprimibleFolioSimple {
    // private Turno turno;

    private static final long serialVersionUID = 1L;
    /** @param folio
    /** @param turno
    /** @param usuario */
    public ImprimibleHojaDeRuta(Folio folio, Turno turno, Usuario usuario, String fechaImpresion,String tipoImprimible, String tbase1, String tbase2) {
        super(folio,tipoImprimible,tbase1,tbase2);
        this.turno = turno;
        this.usuario = usuario;
        this.fechaImpresion=fechaImpresion;
    }

    /* (non-Javadoc)
     * @see gov.sir.print.common.Imprimible#generate(java.awt.print.PageFormat)
     */
    public void generate(PageFormat pageFormat) {
        super.generate(pageFormat);
        this.imprimirElaboracion();
    }

    /* (non-Javadoc)
     * @see gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado#imprimirElaboracion()
     */
    public void imprimirElaboracion() {
        String userName = "";

        try {
            if (this.usuario == null) {
                List historiaTurno = this.turno.getHistorials();
                TurnoHistoria turnoHist = (TurnoHistoria) (historiaTurno.get(historiaTurno.size() -
                        1));
                Usuario usuarioTemp = turnoHist.getUsuario();
                userName = ""+usuarioTemp.getIdUsuario();
            } else {
                userName = ""+this.usuario.getIdUsuario();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        this.imprimirLinea(ImprimibleConstantes.PLANO, "Elaboró: " + userName);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado#imprimirEncabezadoTitulo(java.lang.String)
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

        linea = StringFormat.getCentrada("HOJA DE RUTA",
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);
        this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);

        this.imprimirLinea(ImprimibleConstantes.PLANO,
            "Página: " + this.getNumberOfPages());

        this.imprimirLinea(ImprimibleConstantes.TITULO1, "");

        this.imprimirLinea(ImprimibleConstantes.PLANO,
            ImprimibleConstantes.MARGEN_IZQ * 10, "");
        this.imprimirLinea(ImprimibleConstantes.PLANO,
            ImprimibleConstantes.MARGEN_IZQ * 10, "");

        String fechaImp = this.fechaImpresion;

        linea = StringFormat.getCentrada(fechaImp,
                ImprimibleConstantes.MAX_NUM_CHAR, 15);
        this.imprimirLinea(ImprimibleConstantes.PLANO, linea);

        this.imprimirLinea(ImprimibleConstantes.PLANO,
            ImprimibleConstantes.MARGEN_IZQ * 4, "");
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
        int lineas = super.imprimirAnotacion(anota, modo);

        DatosAntiguoSistema datosAntSistema = anota.getDatosAntiguoSistema();

        if (datosAntSistema == null) {
            return lineas;
        }

        this.imprimirDatosAntiguoSistema(datosAntSistema, modo);

        return lineas;
    }

    /**
     * Imprime los datos de antiguo sistema.
     * @param datos
     * @param modo
     */
    private void imprimirDatosAntiguoSistema(DatosAntiguoSistema datos,
        boolean modo) {
        if (datos != null) {
            String linea = this.getLineFromModo("", modo);
            this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO2, linea);

            linea = this.getLineFromModo("DATOS ANTIGUO SISTEMA: ", modo);
            this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO2, linea);

            this.imprimirDatosLibro(datos, modo);
            this.imprimirDatosTomo(datos, modo);

            String comentario = datos.getComentario();

            /*
            if (comentario!=null)
            {
                    linea =this.getLineFromModo("COMENTARIO: ",modo);
                    this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO2,linea,false);

                    linea =this.getLineFromModo(comentario,modo);
                    this.imprimirLinea(ImprimibleConstantes.PLANO,120,linea);
            }*/
            linea = this.getLineFromModo("", modo);
            this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO2, linea);
        }
    }

    /**
     * Imprimir los datos del libro
     * @param datos
     * @param modo
     */
    private void imprimirDatosLibro(DatosAntiguoSistema datos, boolean modo) {
        String libroAno = datos.getLibroAnio();
        String libroNumero = datos.getLibroNumero();
        String libroPagina = datos.getLibroPagina();
        String libroTipo = datos.getLibroTipo();

        Date fecha = datos.getFechaCreacion();
        String fechaCreacion = ((fecha == null) ? null : this.getFecha(fecha));

        String line;

        if ((libroAno != null) || (libroNumero != null) ||
                (libroPagina != null) || (libroTipo != null)) {
            line = this.getLineFromModo("LIBRO: ", modo);
            this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO2, line,
                false);
        }

        if (libroAno != null) {
            line = this.getLineFromModo("Año: " + libroAno, modo);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 100, line, false);
        }

        if (libroNumero != null) {
            line = this.getLineFromModo("Número: " + libroNumero, modo);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 200, line, false);
        }

        if (libroPagina != null) {
            line = this.getLineFromModo("Página: " + libroPagina, modo);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 300, line, false);
        }

        if (libroTipo != null) {
            line = this.getLineFromModo("Tipo: " + libroTipo, modo);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 400, line, false);
        }

        line = this.getLineFromModo("", modo);
        this.imprimirLinea(ImprimibleConstantes.PLANO, line);
    }

    /**
     * Imprimir los datos del Tomo
     * @param datos
     * @param modo
     */
    private void imprimirDatosTomo(DatosAntiguoSistema datos, boolean modo) {
        String tomoAno = datos.getTomoAnio();
        String tomoMunicipio = datos.getTomoMunicipio();
        String tomoNumero = datos.getTomoNumero();
        String tomoPagina = datos.getTomoPagina();

        String line;

        if ((tomoAno != null) || (tomoNumero != null) || (tomoPagina != null) ||
                (tomoMunicipio != null)) {
            line = this.getLineFromModo("TOMO: ", modo);
            this.imprimirLinea(ImprimibleConstantes.TIPO_TEXTO_TITULO2, line,
                false);
        }

        if (tomoAno != null) {
            line = this.getLineFromModo("Año: " + tomoAno, modo);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 100, line, false);
        }

        if (tomoNumero != null) {
            line = this.getLineFromModo("Número: " + tomoNumero, modo);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 200, line, false);
        }

        if (tomoPagina != null) {
            line = this.getLineFromModo("Página: " + tomoPagina, modo);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 300, line, false);
        }

        if (tomoMunicipio != null) {
            line = this.getLineFromModo("Tipo: " + tomoMunicipio, modo);
            this.imprimirLinea(ImprimibleConstantes.PLANO, 400, line, false);
        }

        line = this.getLineFromModo("", modo);
        this.imprimirLinea(ImprimibleConstantes.PLANO, line);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBaseFolio#imprimirFolioTop(gov.sir.core.negocio.modelo.Folio)
     */
    protected void imprimirFolioTop(Folio folioImprimir) {
        this.microTexto = this.getMicroTexto(folioImprimir);

        String circuloRegNom = (((folioImprimir.getZonaRegistral() != null) &&
            (folioImprimir.getZonaRegistral().getCirculo() != null) &&
            (folioImprimir.getZonaRegistral().getCirculo().getNombre() != null))
            ? folioImprimir.getZonaRegistral().getCirculo().getNombre() : "");
        String circuloRegId = (((folioImprimir.getZonaRegistral() != null) &&
            (folioImprimir.getZonaRegistral().getCirculo() != null) &&
            (folioImprimir.getZonaRegistral().getCirculo().getIdCirculo() != null))
            ? folioImprimir.getZonaRegistral().getCirculo().getIdCirculo() : "");

        if (circuloRegNom == null) {
            circuloRegNom = "";
        }

        if (circuloRegId == null) {
            circuloRegId = "";
        }

        String depto = (String) ImprimibleHelper.getDatoFromFolio(folioImprimir,
                IGlosarioImprimibles.departamento);
        String municipio = (String) ImprimibleHelper.getDatoFromFolio(folioImprimir,
                IGlosarioImprimibles.municipio);
        String vereda = (String) ImprimibleHelper.getDatoFromFolio(folioImprimir,
                IGlosarioImprimibles.vereda);

        if (!circuloRegNom.equals("")) {
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                "CIRCULO REGISTRAL: " + circuloRegId + " " + circuloRegNom +
                ImprimibleConstantes.ESPACIOS + "DEPTO: " + depto +
                ImprimibleConstantes.ESPACIOS + "MUNICIPIO: " + municipio +
                ImprimibleConstantes.ESPACIOS + "VEREDA: " + vereda);
        }

        Calendar c = Calendar.getInstance();
        String fechaAper = "";

        try {
        	Date fechaApertura = null;
        	if(this.turno!=null){
        		fechaApertura = this.turno.getFechaInicio();
        	}else{
        		fechaApertura = this.folio.getFechaApertura();
        	}
            
            fechaAper = super.getFecha(fechaApertura);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //String radicacion = radicacion = folio.getRadicacion();
        //if (radicacion == null)
        //	radicacion = "";
        Documento doc = folioImprimir.getDocumento();
        String docum="CERTIFICADO";
        if (doc.getTipoDocumento()!=null && doc.getTipoDocumento().getNombre()!=null){
            docum=doc.getTipoDocumento().getNombre();
        }

        if (doc.getNumero()!=null){
            docum=docum.concat("  "+doc.getNumero());
        }

        if (doc != null) {
           docum = "CON: " + docum;
        }
        // Bug 3443:
        // No imprimir el documento
        docum = "";


        this.imprimirLinea(ImprimibleConstantes.PLANO,
            "FECHA ELABORACIÓN: " + fechaAper + ImprimibleConstantes.ESPACIOS +
            " " + docum);

        String codCatastral = (String) ImprimibleHelper.getDatoFromFolio(folioImprimir,
                IGlosarioImprimibles.codCatastral);
        //if (!codCatastral.equals(""))
        {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 400,
                "COD CATASTRAL: " + codCatastral);
        }

        String estado = (String) ImprimibleHelper.getDatoFromFolio(folioImprimir,
                IGlosarioImprimibles.estado);

        if (!estado.equals("")) {
            this.imprimirLinea(ImprimibleConstantes.PLANO,
                "ESTADO DEL FOLIO: ", false);
            this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2, 120,
                estado, false);
        }

        String codCatastralAnt = (String) ImprimibleHelper.getDatoFromFolio(folioImprimir,
                IGlosarioImprimibles.codCatastralAnterior);
        //if (!codCatastralAnt.equals(""))
        {
            this.imprimirLinea(ImprimibleConstantes.PLANO, 400,
                "COD CATASTRAL ANT: " + codCatastralAnt);
        }
    }

    /* (non-Javadoc)
     * @see gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado#imprimirFoliosPadre()
     */
    protected void imprimirFoliosPadre() {
    }
}

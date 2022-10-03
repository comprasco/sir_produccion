package gov.sir.core.negocio.modelo.imprimibles;

import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.imprimibles.base.IGlosarioImprimibles;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBaseFolio;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleHelper;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.imprimibles.util.StringFormat;

import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;

import java.io.IOException;

import java.util.Date;
import java.util.List;

/**
* @Autor: Edgar Lora
* @Mantis 11599
* @Requerimiento 085_151
*/
import java.util.Collections;
import gov.sir.core.negocio.modelo.util.ComparadorCanceladoras;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.ForsetiService;
import gov.sir.hermod.HermodService;
import java.text.SimpleDateFormat;
import java.util.*;

/**util
 * @author gvillal
 * Clase que representa al imprimible de un certificado.
*/
public class ImprimibleCertificado extends ImprimibleBaseFolio {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**Folio del cual se obtiene la información del certificado.**/
    protected Folio folio;

    /** Lista de los Folio.ID de los padres de este folio**/
    protected List foliosPadre = null;

    private BufferedImage imagen = null;
    private boolean changetextforregistrador = false;
    private boolean isCertificadoEspecial = false;
    private boolean isCertificadoTramite = false;
    private boolean isCertificadoActuacion = false;
    private List turnoTramite = null;
    /** Lista de los Anotaciones de los hijos de este folio**/
    protected List foliosHijo = null;
    /** Lista de los Folios hijos de este folio**/
    protected List<FolioDerivado> foliosDerivadoHijos = null;
    protected Usuario usuario;
    private String nombreRegistrador = null;
    private String cargoRegistrador = null;
    private byte[] pixelesImagenFirmaRegistrador = null;
    private boolean exento = false;
    private String textoExento = "";
    private String textoBase1 = "";
    private String textoBase2 = "";
    /*Adiciona Funcionalidad Boton de Pago Author: Ingeniero Diego Hernadez Modificacion en: 20/04/2010 by jvenegas */
    private String pinCertificado = "";
    private String nis = "";
    /**
    * @author     : Julio Alcazar
    * @change     : creación de la variable ipverificacion.
    * Caso Mantis : 0006493: Acta - Requerimiento No 027 - Caracteristicas Impresión certificados
    */
    private String ipverificacion = "";
    
    /**
    * @author     : Carlos Torres
    * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
    */
    private Traslado  infoTraslado;



	protected String fechaImpresion = null;
	public static final String AMPLIACION_DE_TRADICION = "AMPLIACION DE TRADICION:";

        
	public ImprimibleCertificado(String tipoImprimible) {
		super(tipoImprimible);
	}
    /**
     * Constructor de la clase
     *
     * @param folio el folio de donde proviene la información del certificado
     * @param turno el turno con el que se generó
     * @param foliosPadre la lista de <Folio.ID> con las matriculas de los
     * padres de este folio
     * @param foliosHijo la lista de <Folio.ID> con las matriculas de los hijos
     * de este folio
     */
    public ImprimibleCertificado(Folio folio, Turno turno, List foliosPadre,
            List foliosHijo, String fechaImpresion, String tipoImprimible, String tbase1, String tbase2) {
        super(tipoImprimible);
        /**
         * @Autor: Edgar Lora
         * @Mantis 11599
         * @Requerimiento 085_151
         */
        if (turno != null) {
            this.ordenarMatriculasAnotaciones(turno);
            if (folio != null) {
                this.ordenarMatriculasAnotaciones(folio);
            }
        } else {
            this.ordenarMatriculasAnotaciones(folio);
        }
        setTransferObject(folio);
        this.folio = folio;
        this.turno = turno;
        this.foliosPadre = foliosPadre;
        this.foliosHijo = foliosHijo;
        this.simple = false;
        this.fechaImpresion = fechaImpresion;
        this.textoBase1 = tbase1;
        this.textoBase2 = tbase2;
    }
        
    /**
     * Constructor de la clase
     * @param folio el folio de donde proviene la información del certificado
     * @param turno el turno con el que se generó
     * @param foliosPadre la lista de <Folio.ID> con las matriculas de los padres de este folio
     * @param foliosHijo la lista de <Folio.ID> con las matriculas de los hijos de este folio
     */
    public ImprimibleCertificado(Folio folio, Turno turno, List foliosPadre,
        List foliosHijo, List<FolioDerivado> foliosDerivadoHijos, String fechaImpresion, String tipoImprimible, String tbase1, String tbase2) {
        super(tipoImprimible);
        /**
        * @Autor: Edgar Lora
        * @Mantis 11599
        * @Requerimiento 085_151
        */
        if(turno != null){
            this.ordenarMatriculasAnotaciones(turno);
            if(folio != null){
                this.ordenarMatriculasAnotaciones(folio);
            }
        }else{
            this.ordenarMatriculasAnotaciones(folio);
        }
        setTransferObject(folio);
        this.folio = folio;
        this.turno = turno;
        this.foliosPadre = foliosPadre;
        this.foliosHijo = foliosHijo;
        this.foliosDerivadoHijos = foliosDerivadoHijos;
        this.simple = false;
        this.fechaImpresion  = fechaImpresion;
        this.textoBase1 = tbase1;
        this.textoBase2 = tbase2;
    }


	/* (non-Javadoc)
     * @see gov.sir.print.common.Imprimible#generate(java.awt.print.PageFormat)
     */
    public void generate(PageFormat pageFormat) {
        super.generate(pageFormat);
        this.imprimirEncabezado();

        this.imprimirFolioTop(this.folio, this.textoBase1, this.textoBase2);
        /**
        * @author     : Carlos Torres
        * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
        */
        this.imprimirInfoTraslado();
        this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
        this.imprimirLinea(ImprimibleConstantes.PLANO,
            ImprimibleConstantes.SEPARADOR1);
        this.imprimirLinea(ImprimibleConstantes.TITULO2,
            "DESCRIPCIÓN: CABIDA Y LINDEROS: ");

        String lindero = (String) ImprimibleHelper.getDatoFromFolio(folio,
                IGlosarioImprimibles.lindero);

        if (lindero != null && !lindero.equals("")) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, lindero);
        }

        if (this instanceof ImprimibleHojaDeRuta)
        {
            this.imprimirLinea(this.tituloMicro, this.microTexto);
        }
        
        String linderosDef = folio.getLinderosDef();
        
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "LINDEROS TECNICAMENTE DEFINIDOS: ");
        
        if(linderosDef != null && !linderosDef.isEmpty()){
            this.imprimirLinea(ImprimibleConstantes.PLANO, linderosDef);
        }
        
        
        String coeficiente = folio.getCoeficiente();
        
        String hectareas = folio.getHectareas();
        if(hectareas == null || hectareas.isEmpty()){
            hectareas = "0";
        }
        
        String metros = folio.getMetros();
        if(metros == null || metros.isEmpty()){
            metros = "0";
        }
        
        String centimetros = folio.getCentimetros();
        if(centimetros == null || centimetros.isEmpty()){
            centimetros = "0";
        }
        
        String privMetros = folio.getPrivMetros();
        if(privMetros == null || privMetros.isEmpty()){
            privMetros = "0";
        }
        
        String privCentimetros = folio.getPrivCentimetros();
        if(privCentimetros == null || privCentimetros.isEmpty()){
            privCentimetros = "0";
        }
        
        String consMetros = folio.getConsMetros();
        if(consMetros == null || consMetros.isEmpty()){
            consMetros = "0";
        }
        
        String consCentimetros = folio.getConsCentimetros();
        if(consCentimetros == null || consCentimetros.isEmpty()){
            consCentimetros = "0";
        }
        
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "\nAREA Y COEFICIENTE ");
        
        if(hectareas.equals("0") && metros.equals("0") && centimetros.equals("0")
           && privMetros.equals("0") && privCentimetros.equals("0")
                && consMetros.equals("0") && consCentimetros.equals("0")){
           this.imprimirLinea(ImprimibleConstantes.PLANO, "AREA: "); 
           
           this.imprimirLinea(ImprimibleConstantes.PLANO, "AREA PRIVADA:   " 
                + " - "
                + "AREA CONSTRUIDA:    ");
           
        } else{
            
        this.imprimirLinea(ImprimibleConstantes.PLANO, "AREA: " 
                 + hectareas + " HECTAREAS " 
                + metros + " METROS CUADRADOS " 
                + centimetros + " CENTIMETROS CUADRADOS " 
        );
        
        this.imprimirLinea(ImprimibleConstantes.PLANO, "AREA PRIVADA: " 
                +(privMetros!=null&&!privMetros.isEmpty()?privMetros:"0") + " METROS CUADRADOS " 
                +(privCentimetros!=null&&!privCentimetros.isEmpty()?privCentimetros:"0") + " CENTIMETROS CUADRADOS ");
        
        
        this.imprimirLinea(ImprimibleConstantes.PLANO, "AREA CONSTRUIDA: "
                +(consMetros!=null&&!consMetros.isEmpty()?consMetros:"0") + " METROS CUADRADOS " 
                +(consCentimetros!=null&&!consCentimetros.isEmpty()?consCentimetros:"0") + " CENTIMETROS CUADRADOS " 
        );
        
        }
        
        this.imprimirLinea(ImprimibleConstantes.PLANO, "COEFICIENTE: " + (coeficiente!=null&&!coeficiente.isEmpty()?coeficiente:"")+"\n");
        
        this.imprimirLinea(ImprimibleConstantes.TITULO2, "COMPLEMENTACIÓN: ");

        String complementacion = (String) ImprimibleHelper.getDatoFromFolio(folio,
                IGlosarioImprimibles.complementacion);

        if (complementacion != null && !complementacion.trim().equals("")) {
                  if(complementacion.indexOf(AMPLIACION_DE_TRADICION)!=-1){
                        String[] complementaciones = complementacion.split(AMPLIACION_DE_TRADICION);
                        for(int i=0; i< complementaciones.length; i++){
                             if(complementaciones[i]!=null && !complementaciones[i].trim().equals("")){
                                   if(i>0){
                                         complementacion = "<b>"+AMPLIACION_DE_TRADICION+"<b>";
                                         this.imprimirLinea(ImprimibleConstantes.PLANO, complementacion);
                                   }
                                   complementacion = complementaciones[i].trim();
                                   this.imprimirLinea(ImprimibleConstantes.PLANO, complementacion);
                             }
                        }
                  }else{
                        complementacion = complementacion.trim();
                  this.imprimirLinea(ImprimibleConstantes.PLANO, complementacion);
                  }
        }

        this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
        this.imprimirLinea(this.tituloMicro, this.microTexto);

        String tipoPredio = (String) ImprimibleHelper.getDatoFromFolio(folio,
                IGlosarioImprimibles.tipoPredio);
        
        String determinaInm = folio.getDeterminaInm();
        String destEconomica = folio.getDestEconomica();
        
        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "DIRECCIÓN DEL INMUEBLE: ");
        
        this.imprimirLinea(ImprimibleConstantes.PLANO, "TIPO DE PREDIO: " 
                + (tipoPredio!=null&&!tipoPredio.isEmpty()?tipoPredio:"SIN INFORMACIÓN"));
        
        this.imprimirLinea(ImprimibleConstantes.PLANO, "DETERMINACION DE INMUEBLE: " 
                + (determinaInm!=null&&!determinaInm.isEmpty()?determinaInm:"SIN DETERMINAR"));
        
        this.imprimirLinea(ImprimibleConstantes.PLANO, "DESTINACION ECONOMICA: " 
                + (destEconomica!=null&&!destEconomica.isEmpty()?destEconomica:"SIN DETERMINAR"));
        

        this.imprimirDirecciones(this.folio, false);

        this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
        this.imprimirLinea(this.tituloMicro, this.microTexto);

        this.imprimirFoliosPadre();

        int n = this.imprimirAnotaciones(this.folio);

        this.imprimirLinea(ImprimibleConstantes.TITULO2,
            "NRO TOTAL DE ANOTACIONES: *" + n + "*");

        this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
        this.imprimirLinea(this.tituloMicro, this.microTexto);

        this.imprimirFoliosHijos();


            this.imprimirSalvedadesFolio(this.folio);





          int tamFinDoc = getTamanoFinDocumento();
        this.imprimirFinDocumento(tamFinDoc);

        this.imprimirLinea(this.tituloMicro, this.microTexto);

        this.imprimirInfoTurno();

        this.imprimirTextoEstado(false);

        this.imprimirFirma();
    }


    public boolean isChangetextforregistrador() {
        return changetextforregistrador;
    }

    public void setChangetextforregistrador(boolean changetextforregistrador) {
        this.changetextforregistrador = changetextforregistrador;
    }
    
	protected int getTamanoFinDocumento(){
		//-----------------------------------------------
		   int tamFirma = this.loadImagenFirma();
		   System.out.println("tamFirma="+tamFirma);
		   System.out.println("I="+this.getI());

		   int tamFinDoc = tamFirma + ImprimibleConstantes.TAMANO_ADICIONAL_FIRMA;	
		   int posY = this.getI() + tamFinDoc;
		   
		
		   System.out.println("posY="+posY);
		   System.out.println("MAXIMO_VERTICAL="+ImprimibleConstantes.MAXIMO_VERTICAL);
		
		   if((posY > ImprimibleConstantes.MAXIMO_VERTICAL) ){
			   System.out.println("ENTRANDO A RELLENAR");
			   this.imprimirRellenoFinalPagina();
			   System.out.println("SALIENDO DE RELLENAR");
		   }	
		   else
			 System.out.println("NO HAY QUE RELLENAR");
		 //------------------------------------------------	
		 
		 return tamFinDoc;
	}


    /**
     * Imprimir elaboración
     */
    public void imprimirElaboracion() {
        String userName = "";

        try {
            if (this.usuario == null) {
                List historiaTurno = this.turno.getHistorials();
                TurnoHistoria turnoHist = (TurnoHistoria) (historiaTurno.get(historiaTurno.size() -
                        1));
                Usuario usuario1 = turnoHist.getUsuario();
                userName = ""+usuario1.getIdUsuario();
            } else {
                userName = ""+this.usuario.getIdUsuario();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        this.imprimirLinea(ImprimibleConstantes.PLANO, "Elaboró: " + userName);
    }

    /**
     * Imprimir la información del turno
     */
    protected void imprimirInfoTurno() {
        String idTurno = "";
        String fechaIniTurno = "";

        String circulo = null;        
        
        if (this.turno != null) {
        	circulo = this.turno.getSolicitud().getCirculo().getNombre();
        	
            idTurno = this.turno.getIdWorkflow();

            if (idTurno == null) {
                idTurno = "";
            }

            Date fechaIni = this.turno.getFechaInicio();

            if (fechaIni != null) {
                fechaIniTurno = this.getFecha(fechaIni);
            }
        }

        String usuarioImp = this.getUsuarioImpresion();
        String usuarioGen = this.getUsuarioGen();

        this.imprimirLinea(ImprimibleConstantes.TITULO2,
            "USUARIO: " + usuarioGen + "  impreso por: " + usuarioImp);

        this.imprimirLinea(ImprimibleConstantes.TITULO1,
            "TURNO: " + idTurno + "   FECHA:" + fechaIniTurno);

        /**
        * @author     : Julio Alcazar
        * @change     : Impresión en el certificado del codigo NIS y de la direccion-IP de verificación.
        * Caso Mantis : 0006493: Acta - Requerimiento No 027 - Caracteristicas Impresión certificados
        */
        this.imprimirLinea(ImprimibleConstantes.TITULO1,
            "NIS: " + this.nis );       
        this.imprimirLinea(ImprimibleConstantes.TITULO1,
            "Verificar en: "+this.ipverificacion);
        
        if (circulo != null)
        	this.imprimirLinea(ImprimibleConstantes.TITULO1, "EXPEDIDO EN: " + circulo);
    }

    /**
     * Obtener el usario que genero el turno historia
     * @return usario
     */
    private String getUsuarioGen() {
        String userName = "";
        List historia = null;
        TurnoHistoria turnoHistoria = null;
        Usuario usuario1 = null;

        try {

        	if (this.turno != null)
        	{
        		historia = this.turno.getHistorials();
        	}

        	if (historia != null && !historia.isEmpty() )
        	{
        		turnoHistoria = (TurnoHistoria) historia.get(0);
        	}

        	if (turnoHistoria != null)
        	{
        		usuario1 = turnoHistoria.getUsuario();
        	}

        	if (usuario1 != null)
        	{
        		userName = ""+usuario1.getIdUsuario();
        	}
        	
        } catch (Throwable t) {
            t.printStackTrace();
        }
        
        if((userName==null || userName.equals("")) && usuario!=null ){
        	userName = ""+this.usuario.getIdUsuario();
        }
        return userName;
    }

    /**
     * Obtener el usuario que realiza la impresión
     * @return Nombre del usuario
     */
    private String getUsuarioImpresion() {
        String userName = "";
        List historia = null;
        TurnoHistoria turnoHistoria = null;
        Usuario usuario1 = null;

        try {
            if (this.usuario == null)
            {
            	if (this.turno != null)
            	{
            		historia = this.turno.getHistorials();
            	}

            	if (historia != null && !historia.isEmpty())
            	{
            		turnoHistoria = (TurnoHistoria) historia.get(historia.size() - 1);
            	}

            	if (turnoHistoria != null)
            	{
            		usuario1 = turnoHistoria.getUsuario();
            	}

            	if (usuario1 != null)
            	{
            		userName = ""+usuario1.getIdUsuario();
            	}


            } else {
                userName = ""+this.usuario.getIdUsuario();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return userName;
    }

    /**
     * Metodo que imprime las matrículas de los folios padre del folio asociado a este imprimible.
     */
    protected void imprimirFoliosPadre() {
        this.imprimirLinea(ImprimibleConstantes.TITULO2,
            "MATRÍCULA ABIERTA CON BASE EN LA(s) SIGUIENTE(s) MATRICULA(s)",
            false);
        this.imprimirLinea(ImprimibleConstantes.PLANO, 340,
            "(En caso de Integración y otros)");

        if (this.foliosPadre == null) {
            return;
        }

        this.imprimirFoliosX(this.foliosPadre);
    }

    /**
     * Metodo que imprime las matrículas de los folios hijos del folio asociado a este imprimible.
     * Modigicado para el REQ 1155
     */
    private void imprimirFoliosHijos() {
        if (this.foliosHijo == null || this.foliosHijo.isEmpty()) {
            return;
        }
        if (this.foliosDerivadoHijos == null || this.foliosDerivadoHijos.isEmpty()) {
            /**
             * @author: Fernando Padilla
             * @change: 1540.AJUSTE.IMPRIMIBLE.CTL.FOLIO
             * Se hace llamado al metodo imprimirFoliosHijosFolio() para que imprimima
             * las folios hijos desde el sistema folio.
             *
             */
            imprimirFoliosHijosFolio();
            return;
        }

        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "CON BASE EN LA PRESENTE SE ABRIERON LAS SIGUIENTES MATRICULAS");

        String izq = "";
        String der = "";
        int iter;
        
        /**
        * @author: David Panesso
        * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
        * Impresión de los folios derivados a dos columnas. La columna esta compuesta por orden de la anotación seguido de 
        * la matrícula y finalmente el nombre del inmueble. Para el orden y la matrícula se utilizan 15 caracteres y para 
        * el nombre del inmueble se limita a 40.
        * Formato Ej: 10->300-128829 LOTE INMUEBLE 1
        **/
        Object[] foliosDerivadosArray = foliosDerivadoHijos.toArray();
            
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
            
        foliosDerivadoHijos = new ArrayList(Arrays.asList(foliosDerivadosArray));
        boolean isIzq = true;
        for (iter = 0; iter + 1 < foliosDerivadoHijos.size(); iter++) {
            FolioDerivado folioHijoIzq = (FolioDerivado) foliosDerivadoHijos.get(iter);
            FolioDerivado folioHijoDer = (FolioDerivado) foliosDerivadoHijos.get(++iter);

            String idMatriculaIzq = folioHijoIzq.getIdMatricula1() == null ? "" : folioHijoIzq.getIdMatricula1();
            String nombreLoteIzq = folioHijoIzq.getLote() == null ? "" : folioHijoIzq.getLote();
            String idMatriculaDer = folioHijoDer.getIdMatricula1() == null ? "" : folioHijoDer.getIdMatricula1();
            String nombreLoteDer = folioHijoDer.getLote() == null ? "" : folioHijoDer.getLote();

            for (int j = 0; j < foliosHijo.size(); j++) {
                Anotacion anotacionHijo = (Anotacion) foliosHijo.get(j);

                if (isIzq) {
                    if (folioHijoIzq.getIdAnotacion().equals(anotacionHijo.getIdAnotacion())
                            && folioHijoIzq.getIdMatricula1().equals(anotacionHijo.getIdMatricula())) {

                        String ordenIzq = anotacionHijo.getOrden() == null ? "" : anotacionHijo.getOrden();
                        izq = String.format("%-15s", (ordenIzq + "->" + idMatriculaIzq)).replace(' ', ' ');
                        izq = String.format("%-55s", (izq + " " + nombreLoteIzq)).replace(' ', ' ');
                        izq = izq.length() > 55 ? izq.substring(0, 54) : izq;
                        this.imprimirLinea(ImprimibleConstantes.PLANO, izq, false);
                        isIzq = false;
                    }
                } else {
                    if (folioHijoDer.getIdAnotacion().equals(anotacionHijo.getIdAnotacion())
                            && folioHijoDer.getIdMatricula1().equals(anotacionHijo.getIdMatricula())) {

                        String ordenDer = anotacionHijo.getOrden() == null ? "" : anotacionHijo.getOrden();
                        der = String.format("%-15s", (ordenDer + "->" + idMatriculaDer)).replace(' ', ' ');
                        der = String.format("%-55s", (der + " " + nombreLoteDer)).replace(' ', ' ');
                        der = der.length() > 55 ? der.substring(0, 54) : der;                    
                        this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.ANCHO_CARTA/2), der, true);
                        isIzq = true;
                    }
                }
            }            
        }
        if (iter < foliosDerivadoHijos.size()) {
            FolioDerivado folioHijo = (FolioDerivado) foliosDerivadoHijos.get(iter);

            String idMatricula = folioHijo.getIdMatricula1() == null ? "" : folioHijo.getIdMatricula1();
            String nombreLote = folioHijo.getLote() == null ? "" : folioHijo.getLote();

            for (int j = 0; j < foliosHijo.size(); j++) {
                Anotacion anotacionHijo = (Anotacion) foliosHijo.get(j);

                if (isIzq) {
                    if (folioHijo.getIdAnotacion().equals(anotacionHijo.getIdAnotacion())
                            && folioHijo.getIdMatricula1().equals(anotacionHijo.getIdMatricula())) {

                        String ordenIzq = anotacionHijo.getOrden() == null ? "" : anotacionHijo.getOrden();
                        izq = String.format("%-15s", (ordenIzq + "->" + idMatricula)).replace(' ', ' ');
                        izq = String.format("%-55s", (izq + " " + nombreLote)).replace(' ', ' ');
                        izq = izq.length() > 55 ? izq.substring(0, 54) : izq;
                        this.imprimirLinea(ImprimibleConstantes.PLANO, izq, false);
                        isIzq = false;
                    }
                } else {
                    if (folioHijo.getIdAnotacion().equals(anotacionHijo.getIdAnotacion())
                            && folioHijo.getIdMatricula1().equals(anotacionHijo.getIdMatricula())) {

                        String ordenDer = anotacionHijo.getOrden() == null ? "" : anotacionHijo.getOrden();
                        der = String.format("%-15s", (ordenDer + "->" + idMatricula)).replace(' ', ' ');
                        der = String.format("%-55s", (der + " " + nombreLote)).replace(' ', ' ');
                        der = der.length() > 55 ? der.substring(0, 54) : der;                    
                        this.imprimirLinea(ImprimibleConstantes.PLANO, (ImprimibleConstantes.ANCHO_CARTA/2), der, true);
                        isIzq = true;
                    }
                }
            }
            this.imprimirLinea(ImprimibleConstantes.PLANO, izq);
        }
        this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
        this.imprimirLinea(this.tituloMicro, this.getMicroTexto(folio));
    }

    /**
     * @author: Fernando Padilla
     * @change: 1540.AJUSTE.IMPRIMIBLE.CTL.FOLIO Se crea el metodo
     * imprimirFoliosHijosFolio() para que imprimima los folios hijos desde el
     * sistema folio.
     *
     */

    private void imprimirFoliosHijosFolio() {
        if (this.foliosHijo == null) {
            return;
        }

        if (this.foliosHijo.size() == 0) {
            return;
        }

        this.imprimirLinea(ImprimibleConstantes.TITULO2,
                "CON BASE EN LA PRESENTE SE ABRIERON LAS SIGUIENTES MATRICULAS");

        String linea = "";

        for (int i = 0; i < foliosHijo.size(); i++) {
            Anotacion anota = (Anotacion) foliosHijo.get(i);
            String orden = anota.getOrden();
            String idMatricula = anota.getFolio().getIdMatricula();
            String direccion = "";

            Folio folio1 = anota.getFolio();

            if (folio1 != null) {
                List direcciones = folio1.getDirecciones();

                if (direcciones != null) {
                    if (!direcciones.isEmpty()) {
                        Direccion dir = (Direccion) direcciones.get(0);
                        direccion = dir.getEspecificacion();

                        if (direccion == null) {
                            direccion = "";
                        }
                    }
                }
            }

            if (orden == null) {
                orden = "";
            }

            linea = orden + "->" + idMatricula + " " + direccion;
            this.imprimirLinea(ImprimibleConstantes.PLANO, linea);
        }

        this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
        this.imprimirLinea(this.tituloMicro, this.getMicroTexto(folio));
    }

/**
     * Imprime el encabezado del certificado, con un titulo predeterminado.
     * @param titulo
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

        linea = StringFormat.getCentrada("CERTIFICADO DE TRADICIÓN",
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);
        this.imprimirLinea(ImprimibleConstantes.TITULO1, linea);

        linea = StringFormat.getCentrada("MATRÍCULA INMOBILIARIA",
                ImprimibleConstantes.MAX_NUM_CHAR_TITULO1,
                ImprimibleConstantes.LONG_LOGO);
        this.imprimirLinea(ImprimibleConstantes.TITULO1, linea, false);

        String matricula = (String) ImprimibleHelper.getDatoFromFolio(folio,
                IGlosarioImprimibles.matricula);

        String idTurno = "";

        if (this.turno != null) {
            idTurno = this.turno.getIdWorkflow();
            if (idTurno == null) {
                idTurno = "";
            }
        }
        
        this.imprimirLinea(ImprimibleConstantes.PLANO,
            "Página: " + this.getNumberOfPages() + " - Turno " + idTurno);

        this.imprimirLinea(ImprimibleConstantes.TITULO1, "");

        /*Adiciona Funcionalidad Boton de Pago Author: Ingeniero Diego Hernadez Modificacion en: 20/04/2010 by jvenegas */
        if(pinCertificado!=null && !pinCertificado.trim().equals("")){
            this.imprimirLinea(ImprimibleConstantes.TITULO1,
                    //(ImprimibleConstantes.MARGEN_IZQ - 2)* 4,
                    "Certificado Generado con el Pin No: " + pinCertificado, false);
        }

        /*this.imprimirLinea(ImprimibleConstantes.TITULO1,
            ImprimibleConstantes.MARGEN_IZQ * 10,
            "Nro Matrícula: " + matricula, false);*/

        this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,
                (ImprimibleConstantes.MARGEN_IZQ - 2) * 10,
                "Nro Matrícula: " + matricula, false);

        //this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE2,
          //      (ImprimibleConstantes.MARGEN_IZQ + 5) * 10,
            //    matricula);

        this.imprimirTextoEstado();

            this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        //exento = true;

        if (exento) {
                  this.imprimirLinea(ImprimibleConstantes.TITULO1, "");
            linea = StringFormat.getCentrada(textoExento,55,
                    ImprimibleConstantes.LONG_LOGO);
            this.imprimirLinea(ImprimibleConstantes.TITULO1, linea, false);
            this.imprimirLinea(ImprimibleConstantes.PLANO, "");

        }

        //this.imprimirLinea(ImprimibleConstantes.PLANO,ImprimibleConstantes.MARGEN_IZQ*10,"");
        this.imprimirLinea(ImprimibleConstantes.PLANO,
            ImprimibleConstantes.MARGEN_IZQ * 10, "");

        String fechaImp = this.fechaImpresion;

        linea = StringFormat.getCentrada(fechaImp,
                ImprimibleConstantes.MAX_NUM_CHAR, 15);
        this.imprimirLinea(ImprimibleConstantes.PLANO, linea);

        /**
         * @author     : Ellery David Robles Gómez.
         * @change     : Impresión de nota certificado de tradicion.
         * Caso_Mantis : 010207 : Acta - Requerimiento No 060_151 - Adición de nota al Certificado de Tradición y Libertad.
         */
        String notaCertofocadoTradicion1;
        String notaCertofocadoTradicion2 = "";
        String footTramite = StringFormat.getCentrada("Y SU ESTADO JURIDICO PUEDE CAMBIAR\"", 61, 15);
        List printTurnoTramite = new ArrayList();
        String buildTurnoTramite = "";
        String lineTurnoTramite;
        int count = 0;
        if(!isCertificadoEspecial){
            notaCertofocadoTradicion1 = StringFormat.getCentrada("\"ESTE CERTIFICADO REFLEJA LA SITUACION JURIDICA DEL INMUEBLE", 61, 15);
            notaCertofocadoTradicion2 = StringFormat.getCentrada("HASTA LA FECHA Y HORA DE SU EXPEDICION\"", 61, 15);
        } else{
            if (isCertificadoActuacion){
                notaCertofocadoTradicion1 = StringFormat.getCentrada("\"ESTE CERTIFICADO NO REFLEJA LA SITUACION JURIDICA DEL INMUEBLE TENIENDO EN CUENTA QUE", 61, 15);
                notaCertofocadoTradicion2 = StringFormat.getCentrada("SE ENCUENTRA CON UNA ACTUACION ADMINISTRATIVA Y SU ESTADO JURIDICO PUEDE CAMBIAR\"", 61, 15); 
            } else{
            if(isCertificadoTramite && turnoTramite != null){
                notaCertofocadoTradicion1 = StringFormat.getCentrada("\"ESTE CERTIFICADO NO REFLEJA LA SITUACION JURIDICA DEL INMUEBLE TENIENDO EN CUENTA QUE SE", 61, 15);
                if(turnoTramite.size() <= 1){
                Iterator it = turnoTramite.iterator();
                    if(it.hasNext()){
                        String trno = (String) it.next();
                        notaCertofocadoTradicion2 = StringFormat.getCentrada("ENCUENTRA EN TRAMITE CON EL TURNO "+trno+", Y SU ESTADO JURIDICO PUEDE CAMBIAR\"", 61, 15); 
                    }
                } else{
                   Iterator it = turnoTramite.iterator();
                    while(it.hasNext()){
                        String trno = (String) it.next();
                        count++;
                        if (count <= 5){ 
                        buildTurnoTramite += trno + ", ";
                        } else{
                         count = 1;
                         lineTurnoTramite = StringFormat.getCentrada(buildTurnoTramite, 61, 15); 
                         printTurnoTramite.add(lineTurnoTramite);
                         buildTurnoTramite="";
                         buildTurnoTramite += trno + ", ";
                        } 
                    }
                    if(count <= 5){
                        lineTurnoTramite = StringFormat.getCentrada(buildTurnoTramite, 61, 15);
                        printTurnoTramite.add(lineTurnoTramite);
                    }
                }
            } else{
                notaCertofocadoTradicion1 = StringFormat.getCentrada("\"ESTE CERTIFICADO NO REFLEJA LA SITUACION JURIDICA DEL INMUEBLE TENIENDO EN CUENTA QUE", 61, 15);
                notaCertofocadoTradicion2 = StringFormat.getCentrada("SE ENCUENTRA CON UNA ACTUACION ADMINISTRATIVA Y SU ESTADO JURIDICO PUEDE CAMBIAR\"", 61, 15); 
            } 
           } 
        }
        
         this.imprimirLinea(ImprimibleConstantes.TITULO1, notaCertofocadoTradicion1);
         if(turnoTramite == null || turnoTramite.size() <= 1 || isCertificadoActuacion || !isCertificadoEspecial){
            this.imprimirLinea(ImprimibleConstantes.TITULO1, notaCertofocadoTradicion2);
         } else{
            if(printTurnoTramite != null && !printTurnoTramite.isEmpty()){
              notaCertofocadoTradicion2 = StringFormat.getCentrada("ENCUENTRA EN TRAMITE CON LOS TURNOS", 61, 15);  
              this.imprimirLinea(ImprimibleConstantes.TITULO1, notaCertofocadoTradicion2);
              Iterator itPrint = printTurnoTramite.iterator();
              while(itPrint.hasNext()){
                  String printLine = (String) itPrint.next();
                  this.imprimirLinea(ImprimibleConstantes.TITULO1, printLine);
              }
              this.imprimirLinea(ImprimibleConstantes.TITULO1, footTramite);
            } 
         }

        linea = StringFormat.getCentrada(ImprimibleConstantes.CVALIDEZ,
                ImprimibleConstantes.MAX_NUM_CHAR, 15);
        this.imprimirLinea(ImprimibleConstantes.PLANO, linea);

        this.imprimirLinea(ImprimibleConstantes.PLANO,
            ImprimibleConstantes.MARGEN_IZQ * 4, "");

        //this.imprimirLinea(ImprimibleConstantes.plano,ImprimibleConstantes.margenIzq*4,"");
    }



    /**
     * Imprimir el estaod del Folio, e indicar si esta cerrado
     * @return Folio cerrado (true)
     */
    private boolean imprimirTextoEstado() {
        return this.imprimirTextoEstado(true);
    }

    /**
     * Imprimir el estaod del Folio, e indicar si esta cerrado
     * @param imprimirMicroTexto
     * @return Folio cerrado (true)
     */
    private boolean imprimirTextoEstado(boolean imprimirMicroTexto) {
        boolean cerrado = false;
        String estado = (String) ImprimibleHelper.getDatoFromFolio(folio,
                IGlosarioImprimibles.estado);
        System.out.println("ESTADO:" + estado);

        if (estado.equals("CERRADO")) {
            this.imprimirLinea(ImprimibleConstantes.PLANO, "");

            if (imprimirMicroTexto) {
                int numCar = 340;
                String micro = this.getMicroTexto(this.folio, numCar);
                this.imprimirLinea(ImprimibleConstantes.TITULO_MICRO, 240, micro);
                this.imprimirLinea(ImprimibleConstantes.TITULO_MICRO, 240, "");
                this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, 250,
                    "F O L I O  C E R R A D O");
                this.imprimirLinea(ImprimibleConstantes.TITULO_MICRO, 240, micro);
            } else {
                this.imprimirLinea(ImprimibleConstantes.TITULO_MICRO, 240, "");
                this.imprimirLinea(ImprimibleConstantes.TITULO_GRANDE, 250,
                    "F O L I O  C E R R A D O");
            }

            cerrado = true;
        }

        return cerrado;
    }

    /* (non-Javadoc)
     * @see gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase#imprimirEncabezado()
     */
    protected void imprimirEncabezado() {
        super.imprimirEncabezado();

        String titulo = "OFICINA DE REGISTRO DE INSTRUMENTOS PÚBLICOS";
        this.imprimirEncabezadoTitulo(titulo);
    }

    /* (non-Javadoc)
     * @see gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase#imprimirFirma()
     */
    protected void imprimirFirma() {


        if (imagen == null) {
            super.imprimirFirma();
        } else {
            this.imprimirFirmaDigital(imagen);
        }
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
        this.setFlagNuevapagina(0);
    }

 /*Adiciona Funcionalidad Boton de Pago
 * Author: Ingeniero Diego Hernandez
 * Modificacion en: 2010/02/23 by jvenegas
 */

    public String getPinCertificado() {
		return pinCertificado;
	}

    public void setPinCertificado(String pinCertificado) {
		this.pinCertificado = pinCertificado;
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
    
    public boolean isCertificadoActuacion(){
        return isCertificadoTramite;
    }
    
    public void setCertificadoActuacion(boolean isCertificadoActuacion){
        this.isCertificadoActuacion = isCertificadoActuacion;
    }

    public boolean isCertificadoEspecial() {
        return isCertificadoEspecial;
    }

    public void setIsCertificadoEspecial(boolean isCertificadoEspecial) {
        this.isCertificadoEspecial = isCertificadoEspecial;
    }

    public boolean isCertificadoTramite() {
        return isCertificadoTramite;
    }

    public void setIsCertificadoTramite(boolean isCertificadoTramite) {
        this.isCertificadoTramite = isCertificadoTramite;
    }
    
    /**
     * Obtener el atributo exento
     *
     * @return Retorna el atributo exento.
     */
    public boolean isExento() {
        return exento;
    }

    /**
     * Actualizar el valor del atributo exento
     * @param exento El nuevo valor del atributo exento.
     */
    public void setExento(boolean exento) {
        this.exento = exento;
    }

    /**
     * Obtener el atributo folio
     *
     * @return Retorna el atributo folio.
     */
    public Folio getFolio() {
        return folio;
    }

    /**
     * Actualizar el valor del atributo folio
     * @param folio El nuevo valor del atributo folio.
     */
    public void setFolio(Folio folio) {
        this.folio = folio;
    }

    /**
     * Obtener el atributo foliosHijo
     *
     * @return Retorna el atributo foliosHijo.
     */
    public List getFoliosHijo() {
        return foliosHijo;
    }

    /**
     * Actualizar el valor del atributo foliosHijo
     * @param foliosHijo El nuevo valor del atributo foliosHijo.
     */
    public void setFoliosHijo(List foliosHijo) {
        this.foliosHijo = foliosHijo;
    }

    /**
     * Obtener el atributo foliosPadre
     *
     * @return Retorna el atributo foliosPadre.
     */
    public List getFoliosPadre() {
        return foliosPadre;
    }

    /**
     * Actualizar el valor del atributo foliosPadre
     * @param foliosPadre El nuevo valor del atributo foliosPadre.
     */
    public void setFoliosPadre(List foliosPadre) {
        this.foliosPadre = foliosPadre;
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
     * Obtener el atributo textoExento
     *
     * @return Retorna el atributo textoExento.
     */
    public String getTextoExento() {
        return textoExento;
    }

    /**
     * Actualizar el valor del atributo textoExento
     * @param textoExento El nuevo valor del atributo textoExento.
     */
    public void setTextoExento(String textoExento) {
        this.textoExento = textoExento;
    }

    /**
     * Obtener el atributo usuario
     *
     * @return Retorna el atributo usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Actualizar el valor del atributo usuario
     * @param usuario El nuevo valor del atributo usuario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


	public String getTextoBase1() {
		return textoBase1;
	}


	public void setTextoBase1(String textoBase1) {
		this.textoBase1 = textoBase1;
	}


	public String getTextoBase2() {
		return textoBase2;
	}


	public void setTextoBase2(String textoBase2) {
		this.textoBase2 = textoBase2;
	}
	
	public void generar(PageFormat pageFormat){
		super.generate(pageFormat);
	}
	
	public void setFechaImpresion(String fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

        public String getNis() {
        return nis;
        }

        public void setNis(String nis) {
            this.nis = nis;
        }

        /**
        * @author     : Julio Alcazar
        * @change     : metodos set y get de la variable ipverificacion.
        * Caso Mantis : 0006493: Acta - Requerimiento No 027 - Caracteristicas Impresión certificados
        */
        public void setIpverificacion(String ipverificacion) {
            this.ipverificacion = ipverificacion;
        }
        
        public String getIpverificacion() {
        return ipverificacion;
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
        
        /**
         * @Autor: Edgar Lora
         * @Mantis 11599
         * @Requerimiento 085_151
         * @param t 
         */
        private void ordenarMatriculasAnotaciones(Folio f){
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
    /**
    * @author     : Carlos Torres
    * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
    */
    private void imprimirInfoTraslado() {
        String linea = "";
        List fundamentosO = new ArrayList();
        List fundamentosD = new ArrayList();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (this.infoTraslado != null) {
            this.setAnchoLinea(ImprimibleConstantes.ANCHO_TEXTO_MICRO1);
            this.imprimirLinea(ImprimibleConstantes.PLANO,
            ImprimibleConstantes.SEPARADOR1);
            this.imprimirLinea(this.tituloMicro, this.microTexto);
            this.imprimirLinea(ImprimibleConstantes.PLANO,
            ImprimibleConstantes.SEPARADOR1);

            linea = StringFormat.getCentrada("INFORMACION TRASLADO DE MATRICULA",61,15);
            
            this.imprimirLinea(ImprimibleConstantes.TITULO1,linea);
            this.imprimirLinea(ImprimibleConstantes.TITULO2,"FUNDAMENTOS LEGALES");

            if (this.infoTraslado.getTrasladoDatos() != null) {
                this.imprimirLinea(ImprimibleConstantes.TITULO2, "Decreto(s): ",false);
                linea = "";
                for (Object row : this.infoTraslado.getTrasladoDatos().getFundamentosTraslados()) {
                    TrasladoFundamento ft = (TrasladoFundamento) row;
                    if(ft.getFundamento().getTipoFundamento().getIdTipoFundamento()==1){
                        linea += " Número: "+ft.getFundamento().getNumeroFundamento()+"   Fecha : "+df.format(ft.getFundamento().getFechaCreacion())+",";
                    }else if (ft.getTipoOrigen() == 1) {
                        fundamentosO.add(row);
                    } else {
                        fundamentosD.add(row);
                    }

                }
                if(linea.length()>0){
                    linea = linea.substring(0,linea.length()-1);   
                }
                this.imprimirLinea(ImprimibleConstantes.PLANO,80,linea,false);
                this.imprimirLinea(ImprimibleConstantes.PLANO, "");
                
                this.imprimirLinea(ImprimibleConstantes.TITULO2, "Resolución(es) de Traslado Circulo Origen:",false);
                linea = "";
                for (Object l : fundamentosO) {
                    Fundamento tf = ((TrasladoFundamento) l).getFundamento();
                    linea += " Número: " + tf.getNumeroFundamento() + " Fecha " + df.format(tf.getFechaCreacion())+",";
                }
                if(linea.length() > 0){
                    linea = linea.substring(0,linea.length()-1);
                }
                this.imprimirLinea(ImprimibleConstantes.PLANO,210, linea,false);
                this.imprimirLinea(ImprimibleConstantes.PLANO, "");
                
                this.imprimirLinea(ImprimibleConstantes.TITULO2, "Resolución(es) de Circulo Destino:",false);
                linea = "";
                for (Object l : fundamentosD) {
                    Fundamento tf = ((TrasladoFundamento) l).getFundamento();
                    linea += " Número: " + tf.getNumeroFundamento() + " Fecha " + df.format(tf.getFechaCreacion())+",";
                }
                if(linea.length() > 0){
                    linea = linea.substring(0,linea.length()-1);
                }
                this.imprimirLinea(ImprimibleConstantes.PLANO,180,linea,false);
                this.imprimirLinea(ImprimibleConstantes.PLANO, "");
            }
            String circuloS = this.infoTraslado.getFolioOrigen().getCirculo();
            String idCirculo = circuloS.substring(circuloS.length()-3, circuloS.length());
            String circuloN = circuloS.substring(0, circuloS.length() - 3);
            linea = "Circulo Registral Origen: " + idCirculo+" "+circuloN;
            linea += "    Matricula Origen: " + this.infoTraslado.getFolioOrigen().getIdMatricula();
            this.imprimirLinea(ImprimibleConstantes.TITULO2, linea);

        }
    }
    public Traslado getInfoTraslado() {
        return infoTraslado;
    }

    public void setInfoTraslado(Traslado infoTraslado) {
        this.infoTraslado = infoTraslado;
    }

    public List getTurnoTramite() {
        return turnoTramite;
    }

    public void setTurnoTramite(List turnoTramite) {
        this.turnoTramite = turnoTramite;
    }
    
    
        
    }

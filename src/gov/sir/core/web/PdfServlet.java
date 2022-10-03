/*
 * PdfServlet.java
 *
 * Created on 17 de febrero de 2005, 14:55
 */
package gov.sir.core.web;

import java.awt.Graphics2D;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Image;
import com.lowagie.text.Watermark;

import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import gov.sir.core.eventos.comun.EvnNotas;

import gov.sir.core.eventos.comun.EvnReportesJasper;
import gov.sir.core.eventos.comun.EvnRespReporteJasper;
import gov.sir.core.eventos.correccion.EvnCorreccion;
import gov.sir.core.eventos.registro.EvnCalificacion;
import gov.sir.core.negocio.acciones.comun.ANNotas;
import gov.sir.core.negocio.modelo.Fase;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.acciones.registro.ANCalificacion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CTipoFormulario;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormulario;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.core.util.DateFormatUtil;

import gov.sir.core.web.acciones.administracion.AWReportes;
import gov.sir.core.web.acciones.consulta.AWConsulta;
import gov.sir.core.web.acciones.registro.AWCalificacion;

import gov.sir.print.server.PrintJobsProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PropertyResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.ProcesadorEventosNegocioProxy;
import org.auriga.smart.web.acciones.AccionWebException;

/**
 *
 * @author  jalvarez
 */
public class PdfServlet extends HttpServlet {
	
    /** Constante que define el tipo de documento */
    public static final String TIPO_DOCUMENTO = "TIPO_DOCUMENTO";

    /** Constatne que define el tamaño de pulgada */
    private static final int INCH = 72;

    /** Constante que define el ancho de una hoja carta */
    private static final double LETTER_WIDTH = 8.5 * INCH;

    /** Constante que define el alto de una hoja carta */
    private static final double LETTER_HEIGHT = 11 * INCH;
    public static final String Serv = "Serv";
    public static final String ServC = "ServC";
    public static final String DevloIns = "DevloIns";
    public static final String RSM = "RSM";
    public static final String RST = "RST";
   public static final String Calf= "Calf";
   public static final String ServRE = "ServRE";
    /** Creates a new instance of PdfServlet */
    public PdfServlet() {
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        if(request.getParameter(RSM)!= null){
           try {  
               HttpSession session = request.getSession();
              ANCalificacion Logica;
              Logica = new ANCalificacion();
              org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
              String Matricula = request.getParameter("RSM");
              String iduser= request.getParameter("ID");
              String desde= request.getParameter("desde");
              String hasta= request.getParameter("hasta");
              Turno turno = Logica.GetTurnoWK(request.getParameter("TR"));
              Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
              Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
              EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_FIRMA);
              ev.setTurno(turno);
              ev.setFase(fase);
              ev.setUsuarioNeg(userNeg);
              gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
              infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
              ev.setInfoUsuario(infoUsuario);
              Logica.VisualizarpdfRSM(ev,request,response,Matricula,iduser,desde,hasta,1);
             } catch (EventoException ex) {

             }
       }else if(request.getParameter(RST)!= null){
               try {  
               HttpSession session = request.getSession();
              ANCalificacion Logica;
              Logica = new ANCalificacion();
              org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
              Turno turno = Logica.GetTurnoWK(request.getParameter(RST));
              String iduser= request.getParameter("ID");
              Turno turno1 = (Turno) session.getAttribute(WebKeys.TURNO);
              Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
              Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
              EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_FIRMA);
              ev.setTurno(turno);
              ev.setFase(fase);
              ev.setUsuarioNeg(userNeg);
              gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
              infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
              ev.setInfoUsuario(infoUsuario);
              Logica.VisualizarpdfRST(ev,request,response,turno1,iduser,1);
             } catch (EventoException ex) {

             }
       }else if(request.getParameter(DevloIns)!= null){
                HttpSession session = request.getSession();
        	Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Fase fase = (Fase)session.getAttribute(WebKeys.FASE);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		ArrayList matriculasNoInscritas = new ArrayList();
                 String ImprimirQuest = request.getParameter(WebKeys.ImprimirCC);
		ArrayList matriculasInscritasParcialmente = new ArrayList();
                int erro=0;
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		
		String descripcion = request.getParameter(CNota.DESCRIPCION);
		if(descripcion!=null){
			descripcion = descripcion.toUpperCase();	
		}
		
		List notasImpresion = (List)session.getAttribute(WebKeys.LISTA_CAUSALES_DEVOLUCION);
		if(notasImpresion == null){
			notasImpresion = new Vector();
		}
		
			Iterator it = notasImpresion.iterator();
			while(it.hasNext()){
				Nota nota = (Nota)it.next();
				if (nota.getDescripcion()==null) {
					nota.setDescripcion(descripcion);
				}
			}			 
		
		
		
		//Caso especial Inscripcion parcial (Calificacion)
		boolean inscripcionParcial = false;
		fase = (Fase)session.getAttribute(WebKeys.FASE);
		String flag = (String) session.getAttribute("FLAG_INSCRIPCION_PARCIAL"); 
		if(fase !=null && fase.getID().equals(CFase.CAL_CALIFICACION) && 
		   flag!=null && flag.equals("FLAG_INSCRIPCION_PARCIAL")){
			
			inscripcionParcial = true;
			List foliosFuente = null;
			String[] matriculasImp = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);

			Solicitud solicitud = turno.getSolicitud();
			List solFolios = solicitud.getSolicitudFolios();

			Iterator it1 = null;
			if(matriculasImp !=null ){
				foliosFuente = new ArrayList();

				for (int indice = 0; indice < matriculasImp.length; indice++) {

					it1 = solFolios.iterator();
					while(it1.hasNext()){
						SolicitudFolio solFolio = (SolicitudFolio)it1.next();
						if(solFolio.getIdMatricula().equals(matriculasImp[indice])){
							Folio folio = new Folio();
							folio.setIdMatricula(solFolio.getIdMatricula());
							foliosFuente.add(solFolio);
							matriculasInscritasParcialmente.add(solFolio.getIdMatricula());
							break;
						}
					}
				}
				
				session.setAttribute(AWCalificacion.FOLIOS_INSCRIPCION_PARCIAL, foliosFuente);
			}
		}
		Hashtable validacionAnotacionesTemporales = 
			(java.util.Hashtable) session.getAttribute(WebKeys.VALIDACION_APROBAR_CALIFICACION);
		Solicitud solicitud = turno.getSolicitud();
		List solFolios = solicitud.getSolicitudFolios();
		Iterator itSolFolios = solFolios.iterator();
		while(itSolFolios.hasNext()){
			SolicitudFolio solFolio = (SolicitudFolio)itSolFolios.next();
			if(!matriculasInscritasParcialmente.contains(solFolio.getIdMatricula()))
			if(!this.isMatriculaInscrita(solFolio.getIdMatricula(), validacionAnotacionesTemporales) ){
				matriculasNoInscritas.add(solFolio.getIdMatricula());
			}
		}
		
		String valCopias = request.getParameter(WebKeys.NUMERO_COPIAS_IMPRESION);
		session.setAttribute(WebKeys.NUMERO_COPIAS_IMPRESION, valCopias);
		int copias = 1;
		
		String numNotas = (String) session.getAttribute(WebKeys.NUM_NOTAS_FASE);

		if (numNotas == null) {
			numNotas = new String();
			numNotas = "0";
		}
		int i = Integer.valueOf(numNotas).intValue();
		i++;
		numNotas = String.valueOf(i);
		session.setAttribute(WebKeys.NUM_NOTAS_FASE, numNotas);

		Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		String UID = request.getSession().getId();
		

		EvnNotas evn = new EvnNotas(usuarioAuriga, EvnNotas.GUARDAR_NOTAS_DEVOLUTIVAS, notasImpresion, UID, circulo, turno, usuarioSIR, fase);
		evn.setNumCopiasImpresion(copias);
		//Se añade la lista de matriculas inscritas al evento
		evn.setMatriculasNoInscritas(matriculasNoInscritas);
                if(ImprimirQuest != null){
                evn.setImprimirYN(1);
               }
		if(inscripcionParcial){
			evn.setInscripcionParcial(inscripcionParcial);
			evn.setMatriculasInscritasParcialmente(matriculasInscritasParcialmente);
		}
                ANNotas anc = null;
                     try {
                      anc = new ANNotas();
                          anc.VisualizarpdfNota(evn, request, response);
                      
                      
                     } catch (EventoException ex) {

                   }		

       }else if(request.getParameter(Calf)!= null){
        try {
           org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usu = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Turno turno ;
        ANCalificacion anc = null;
        anc = new ANCalificacion();
               
        if(!request.getParameter(Calf).equals("1")){
            turno = anc.GetTurnoWK((String) request.getParameter(Calf));
        }else{
            turno =  (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        }
        
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        List sols = turno.getSolicitud().getSolicitudFolios();
        HttpSession session = request.getSession();
        Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
        String idCirculo = circulo.getIdCirculo();
        EvnCalificacion resp = new EvnCalificacion(usuario, turno, fase, usu, EvnCalificacion.WF_CONFIRMAR_CALIFICACION, "AVANZAR_PUSH", idCirculo);
        resp.setUID(request.getSession().getId());
        anc.VisualizarpdfCalificacion1(resp, request, response);
            } catch (EventoException ex) {
           
           }
       }else if(request.getParameter(ServC) != null){
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);
        String accion = EvnCorreccion.APROBAR;
        String accionWf = EvnCorreccion.CONFIRMAR_WF;
        EvnCorreccion evn = new EvnCorreccion(usuarioAuriga, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO), turno, fase, accion, accionWf);
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(), String.valueOf(usuarioNeg.getIdUsuario()));
        infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
        evn.setInfoUsuario(infoUsuario);
        evn.setUID(request.getSession().getId());
        ANCalificacion anc = null;
        try {
               anc = new ANCalificacion();
               anc.VisualizarFormularioCalificacion(evn, response, request);
            } catch (EventoException ex) {
           
           }
        }else if(request.getParameter(ServRE)!= null){
           try {  
               HttpSession session = request.getSession();
              ANCalificacion Logica;
              Logica = new ANCalificacion();
              org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
              Turno turno = Logica.GetTurnoWK(request.getParameter(ServRE));
              Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
              Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
              EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_FIRMA);
              ev.setTurno(turno);
              ev.setFase(fase);
              ev.setUsuarioNeg(userNeg);
              ev.setIsFirma(1);
              gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
              infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
              ev.setInfoUsuario(infoUsuario);
              Logica.Visualizarpdf(ev,request,response);
             } catch (EventoException ex) {

             }
        }else if(request.getParameter(Serv) != null){
           HttpSession session = request.getSession();
            org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

            Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
            Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
            Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
            EvnCalificacion ev = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AVANZAR_FIRMA);
            ev.setTurno(turno);
            ev.setFase(fase);
            ev.setUsuarioNeg(userNeg);
            ev.setIsFirma(1);
            gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioAuriga.getUsuarioId(), String.valueOf(userNeg.getIdUsuario()));
            infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
            ev.setInfoUsuario(infoUsuario);
            ANCalificacion Logica;
           try {
               Logica = new ANCalificacion();
               Logica.Visualizarpdf(ev,request,response);
           } catch (EventoException ex) {
           
           }
            
       }else{
           String tipoDocumento = request.getParameter(TIPO_DOCUMENTO);

        if (tipoDocumento == null) {
        	String cmdkey = (String) request.getAttribute(AWReportes.REPORTSSERVICES_REPORTURI);
        	if (cmdkey != null || request.getSession().getAttribute(AWReportes.REPORTSSERVICES_REPORTURI) != null) {
                makeReporteJasper(request, response);
            } else {
            	response.getWriter().write("<HTML><BODY>NO HA SELECCIONADO UN TIPO DE DOCUMENTO VÁLIDO<BODY><HTML>");
            }
        } else {
                if (tipoDocumento.equals(CTipoImprimible.TIPO_CALIFICACION)) {
                    makeCalificacion(request, response);
                } else if (tipoDocumento.equals(CTipoImprimible.TIPO_CERTIFICADO)) {
                    makeCertificado(request, response);
                } else {
                    response.getWriter().write("<HTML><BODY>NO HA SELECCIONADO UN TIPO DE DOCUMENTO VÁLIDO<BODY><HTML>");
                }
            }
       }
        
    }
    private boolean isMatriculaInscrita(String idMatricula,
			Hashtable validacionAnotacionesTemporales){
		boolean isInscrita = false;
		
		if(validacionAnotacionesTemporales!=null){
			Boolean hasTemporales = (Boolean)validacionAnotacionesTemporales.get(idMatricula);
			if(hasTemporales!=null && hasTemporales.booleanValue()){
				isInscrita = true;
			}
		}
		
		return isInscrita;
	}
    private void makeReporteJasper(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap paramsJasper = new HashMap();
		String reportsUri = (String)request.getAttribute(AWReportes.REPORTSSERVICES_REPORTURI);
		if (reportsUri == null) {
			reportsUri = (String)request.getSession().getAttribute(AWReportes.REPORTSSERVICES_REPORTURI);
		}
		
		//se elimina de la uri la ruta del servidor y el primer parámetro
		int pos = reportsUri.indexOf("?");
		reportsUri = reportsUri.substring(++pos, reportsUri.length());
		pos = 0;
		while (reportsUri.length() > 0)
		{
			int posAux = reportsUri.indexOf("&");
			if (posAux < 0) {
				posAux = reportsUri.length();
			}
			String parametro = reportsUri.substring(pos,posAux);
			int posIgual = parametro.indexOf("=");
			String nombre = parametro.substring(0, posIgual);
			parametro = parametro.substring(posIgual+1, parametro.length());
			paramsJasper.put(nombre.toUpperCase(), parametro);
			if (posAux >= reportsUri.length())
				reportsUri = "";
			else
				reportsUri = reportsUri.substring(posAux+1, reportsUri.length());
			pos = 0;
		}
		
		String nombreReporte = (String)paramsJasper.get(AWReportes.REPORTE_XX__PARAM_CMDKEY.toUpperCase());
		ProcesadorEventosNegocioProxy proxy = new ProcesadorEventosNegocioProxy();
		
		EvnReportesJasper evn = new EvnReportesJasper(null,EvnReportesJasper.GENERAR_PDF_REPORTE,nombreReporte, paramsJasper);
		EvnRespReporteJasper envResp;
		try {
            envResp = (EvnRespReporteJasper)proxy.manejarEvento(evn);
            byte [] pdfBytes = (byte[]) envResp.getPayload();
            /*AHERRENO 28/09/2009*/
            // Se lee el fichero de propiedades "gov.sir.print.properties" con los nombres de los reportes en excel.
            // El listado de reportes debe estar separado por comas en el fichero.
            // Se verifica si el nombre del reporte a ejecutar se encuentra en el fichero.
            // Si el nombre del reporte se encuentra en el fichero se renderisa el reporte en formato excel
            // de lo contrario en formato PDF.

            PrintJobsProperties prop;
            prop = PrintJobsProperties.getInstancia();
            String[] reportesExcel = (prop.getProperty(PrintJobsProperties.REPORTES_EXCEL)).split(",");
            int tam = reportesExcel.length;
            boolean isExcel = false;

            for(int i = 0; i< tam; i++){
                if(nombreReporte.equals(reportesExcel[i]))
                    isExcel = true;
            }
            if (isExcel) {
                response.setContentType ("application/vnd.ms-excel"); 
            }else{
                response.setContentType("application/pdf");
            }
                ServletOutputStream out = response.getOutputStream(); 
                out.write(pdfBytes);
                out.flush();
                out.close();                
            
		} catch (EventoException e) {
			Log.getInstance().error(PdfServlet.class, e);
		} catch (IOException e) {
			Log.getInstance().error(PdfServlet.class, e);
		}						
	}

    /**
     * Generar pdf de un certificado
     * @param request
     * @param response
     */
    private void makeCertificado(HttpServletRequest request,
        HttpServletResponse response) {
        Folio folio = (Folio) request.getSession().getAttribute(AWConsulta.FOLIO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        String fechaImpresion= this.getFechaImpresion();
        // obtener textos base de los separadores
		String tbase1 ="SUPERINTENDENCIA DE NOTARIADO Y REGISTRO LA GUARDA DE LA FE PUBLICA";
		String tbase2 = "OFICINA DE REGISTRO DE INSTRUMENTOS PUBLICOS";

        // BUG 5778
        ImprimibleCertificado imprimible = new ImprimibleCertificado(folio,
                turno, (List)request.getSession().getAttribute(AWConsulta.FOLIO_PADRES),
                (List)request.getSession().getAttribute(AWConsulta.FOLIO_HIJOS),
                (List)request.getSession().getAttribute(AWConsulta.FOLIOS_DERIVADO_HIJO),
                fechaImpresion, CTipoImprimible.CERTIFICADO_INMEDIATO, tbase1, tbase2);
        /**
        * @author     : Carlos Torres
        * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
        */
        imprimible.setInfoTraslado((gov.sir.core.negocio.modelo.Traslado)request.getSession().getAttribute("INFO_TRASLADO"));
        imprimible.setPrintWatermarkEnabled(true);
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pf = printJob.defaultPage();
        Paper papel = new Paper();
        double mWidth = LETTER_WIDTH;
        double mHeight = LETTER_HEIGHT;

        papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
            mHeight - (0.5 * INCH));
        pf.setPaper(papel);

        imprimible.generate(pf);
        generar(imprimible, request, response);
    }

    /**
     * Generar pdf de una Calificación
     * @param request
     * @param response
     */
    private void makeCalificacion(HttpServletRequest request,
        HttpServletResponse response) {
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		Usuario usuarioNeg=(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
		String fechaImpresion= this.getFechaImpresion();
        ImprimibleFormulario imprimible = new ImprimibleFormulario(turno,usuarioNeg.getUsername(),
                CTipoFormulario.TIPO_CALIFICACION, fechaImpresion, CTipoImprimible.FORMULARIO_CALIFICACION);
        PrinterJob printJob = PrinterJob.getPrinterJob();
        imprimible.setPrintWatermarkEnabled(true);
        PageFormat pf = printJob.defaultPage();
        Paper papel = new Paper();

        double mWidth = LETTER_WIDTH;
        double mHeight = LETTER_HEIGHT;

        papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
            mHeight - (0.5 * INCH));
        pf.setPaper(papel);

        imprimible.generate(pf);
        generar(imprimible, request, response);
    }

    /**
     * Generar un pdf
     * @param imprimible
     * @param request
     * @param response
     */
    private void generar(Pageable imprimible, HttpServletRequest request,
        HttpServletResponse response) {
        try {
            com.lowagie.text.Document pdfDocument = new com.lowagie.text.Document();
            response.setContentType("application/pdf");

            PdfWriter writer = PdfWriter.getInstance(pdfDocument,
                    response.getOutputStream());
            writer.setViewerPreferences(PdfWriter.HideMenubar |
                PdfWriter.HideToolbar);
            writer.setEncryption(PdfWriter.STRENGTH128BITS, null, null, 0);

            InputStream is =  PdfServlet.class.getResourceAsStream( "watermark.jpg" );
            byte[] bytes =inputStreamToBytes(is) ;
			
			/**
			* @author: Fernando Padilla Velez, 22.06.2015
			* @change: Incidencia No 180870 Generación PDF- Resultado de la consulta
			* 		   No permitia visulizar el pdf, se intercambian las lineas de codigo
			*          para que primero se abra el documento y luego si se agrega la imagen.
			*/
            pdfDocument.open();
			
			Image img = Image.getInstance(  bytes );
            pdfDocument.add(new Watermark(img, 50, 320));

            PdfContentByte content = writer.getDirectContent();

            int num_pages = imprimible.getNumberOfPages();

            for (int i = 0; i < num_pages; i++) {
                Printable page = imprimible.getPrintable(i);

                // we create a template and a Graphics2D object that
                // corresponds with it
                PageFormat pageformat = imprimible.getPageFormat(i);
                int pageWidth = (int) pageformat.getWidth();
                int pageHeight = (int) pageformat.getHeight();
                PdfTemplate template = content.createTemplate(pageWidth,
                        pageHeight);
                template.setWidth(pageWidth);
                template.setHeight(pageHeight);

                Graphics2D g2d = template.createGraphics(pageWidth, pageHeight);
                page.print(g2d, pageformat, 0);

                if (pageformat.getOrientation() == PageFormat.LANDSCAPE) {
                    content.addTemplate(template, 0, 1, -1, 0, pageHeight, 0);
                } else {
                    content.addTemplate(template, 0, 0);
                }

                pdfDocument.newPage();
            }

            pdfDocument.close();
        } catch (Exception e) {
        	Log.getInstance().error(PdfServlet.class,e);
        }
    }
   
    /**
     * @param in
     * @return
     * @throws IOException
     */
    public byte[] inputStreamToBytes(InputStream in) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
		return out.toByteArray();
	} 
    
    /**
	 * Metodo que retorna la cadena con la fecha actual de impresión.
	 * 
	 * @return
	 */
	protected String getFechaImpresion()
	{

		Calendar c = Calendar.getInstance();
		int dia, ano, hora;
		String min, seg, mes;

		dia = c.get(Calendar.DAY_OF_MONTH);
		mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
		ano = c.get(Calendar.YEAR);

		hora = c.get(Calendar.HOUR_OF_DAY);
		if (hora > 12)
			hora -= 12;

		min = formato(c.get(Calendar.MINUTE));
		seg = formato(c.get(Calendar.SECOND));

		String fechaImp =
			"Impreso el "
				+ dia
				+ " de "
				+ mes
				+ " de "
				+ ano
				+ " a las "
				+ formato(hora)
				+ ":"
				+ min
				+ ":"
				+ seg
				+ " "
				+ DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

		return fechaImp;
	}

	/**
	 * Metodo que retorna un numero con un "0" antes en caso de ser menor
	 * que 10.
	 * @param i el numero.
	 * @return
	 */
	protected String formato(int i) {
		if (i < 10) {
			return "0" + (new Integer(i)).toString();
		}
		return (new Integer(i)).toString();
	}
}

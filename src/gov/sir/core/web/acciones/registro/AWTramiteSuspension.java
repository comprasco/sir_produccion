/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.web.acciones.registro;

import gov.sir.core.eventos.registro.EvnRespTramiteSuspension;
import gov.sir.core.eventos.registro.EvnTramiteSuspension;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TramiteSuspension;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CArchivosJustifica;
import gov.sir.core.negocio.modelo.constantes.CTramiteSuspension;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.CargarArchivoExcepcion;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 *
 * @author developer5
 */
public class AWTramiteSuspension extends SoporteAccionWeb {

    public static final String AGREGAR_RESPUESTA_PREV = "AGREGAR_RESPUESTA_PREV";
    public static final String AGREGAR_RESPUESTA_TEMP = "AGREGAR_RESPUESTA_TEMP";
    public static final String CANCELAR_TRAMITE_SUSP_TEMP = "CANCELAR_TRAMITE_SUSP_TEMP";
    public static final String CANCELAR_TRAMITE_SUSP_PREV = "CANCELAR_TRAMITE_SUSP_PREV";
    public static final String AVANZAR = "AVANZAR";
    public static final String LISTA_RESPUESTAS_USUARIO = "LISTA_RESPUESTAS_USUARIO";
    public String RUTA_DESTINO_ARCHIVO_JUSTIFICACION = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO_JUSTIFICACION);

    public AWTramiteSuspension() {
        super();
    }

    /**
     * Este método se encarga de procesar la solicitud del
     * <code>HttpServletRequest</code> de acuerdo al tipo de accion que tenga
     * como parámetro
     *
     * @param request
     * @return
     * @throws org.auriga.smart.web.acciones.AccionWebException
     */
    @Override
    public Evento perform(HttpServletRequest request) throws AccionWebException {

        boolean isMultipart = FileUpload.isMultipartContent(request);
        String accion = "";
        if (isMultipart) {
            return resolverMultipart(request);
        } else {
            accion = request.getParameter(WebKeys.ACCION).trim();
        }

        return null;
    }

    private EvnTramiteSuspension cancelarTramiteSuspension(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        ValidacionParametrosException e = new ValidacionParametrosException();
        EvnTramiteSuspension evento = null;
        String bandera = (String) session.getAttribute(CTramiteSuspension.FLAG_LISTA);
        String accion = (String) session.getAttribute(WebKeys.ACCION);

        if (session.getAttribute(AWTramiteSuspension.LISTA_RESPUESTAS_USUARIO) == null || bandera.equals("false")) {
            e.addError("Es necesario agregar una respuesta.");
        } else {
            Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
            Usuario usuarioAuriga = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
            if (accion.equals(AWTramiteSuspension.CANCELAR_TRAMITE_SUSP_PREV)) {
                evento = new EvnTramiteSuspension(usuarioAuriga, EvnTramiteSuspension.CANCELAR_TRAMITE_SUSP_PREV);
            } else if (accion.equals(AWTramiteSuspension.CANCELAR_TRAMITE_SUSP_TEMP)) {
                evento = new EvnTramiteSuspension(usuarioAuriga, EvnTramiteSuspension.CANCELAR_TRAMITE_SUSP_TEMP);
            }
            evento.setTurno(turno.getIdWorkflow());
            limpiarSession(request);
        }

        if (e.getErrores().size() > 0) {
            throw e;
        }

        return evento;
    }

    public String validarIdTipoNota(HttpServletRequest request, Nota ultimaNota) {
        List tiposNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_TIPOS_NOTAS);
        Iterator itTiposNotas = tiposNotas.iterator();
        String nombreNota = "";
        
        while (itTiposNotas.hasNext()) {
            TipoNota elemento = (TipoNota) itTiposNotas.next();
            if (elemento.getIdTipoNota().equals(ultimaNota.getTiponota().getIdTipoNota())) {
                nombreNota = elemento.getNombre();
            }
        }
        return nombreNota;        
    }

    public boolean validarPDF(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List docAdjunto = (List) session.getAttribute(AWTramiteSuspension.LISTA_RESPUESTAS_USUARIO);
        TramiteSuspension dato = (TramiteSuspension) docAdjunto.get(0);        
        
        boolean band = false;
        if (dato.getTramsIdArchivoJustifica() == null) {
            band = true;
        }

        return band;
    }

    private Evento avanzar(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosException e = new ValidacionParametrosException();
        String bandera = (String) session.getAttribute(CTramiteSuspension.FLAG_LISTA);
        EvnTramiteSuspension ev = null;
        
        if (session.getAttribute(AWTramiteSuspension.LISTA_RESPUESTAS_USUARIO) == null || bandera.equals("false")) {
            e.addError("Es necesario agregar una respuesta para avanzar.");
        } else {

            Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
            Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
            Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
            gov.sir.core.negocio.modelo.Usuario userNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

            List notas = (List) turno.getNotas();           
            Nota ultimaNota =  (Nota) notas.get(notas.size()-1);
            
            boolean band = false;           
            if (proceso.getIdProceso() == 6 && !validarPDF(request) && validarIdTipoNota(request, ultimaNota).equals(CTramiteSuspension.VALOR_NOTA_INFO_1) && ultimaNota != null) {
                band = true;
            } else if (proceso.getIdProceso() == 6 && validarPDF(request) && validarIdTipoNota(request, ultimaNota).equals(CTramiteSuspension.VALOR_NOTA_INFO_2) && ultimaNota != null) {
                band = true;
            }            
          
            if (!band) {
                e.addError("Debe adicionar la nota informativa correspondiente.");
            } else {
                ev = new EvnTramiteSuspension(usuarioAuriga, EvnTramiteSuspension.AVANZAR);
                ev.setTurnoAvance(turno);
                ev.setFaseAvance(fase);
                ev.setUsuarioNegocio(userNeg);
            }
        }

        if (e.getErrores().size() > 0) {
            throw e;
        }
        return ev;
    }

    private Evento resolverMultipart(HttpServletRequest request)
            throws AccionWebException {

        HttpSession session = request.getSession();
        Evento evento = null;
        FileItem fileItem = cargarArchivo(request);
        String accion = (String) session.getAttribute(WebKeys.ACCION);

        if (accion.equals(AWTramiteSuspension.AGREGAR_RESPUESTA_PREV)
                || accion.equals(AWTramiteSuspension.AGREGAR_RESPUESTA_TEMP)) {
            session.setAttribute(CArchivosJustifica.NOMBRE_PROCESO, CArchivosJustifica.PROCESO_AGREGAR_RESP);
            evento = agregarRespuesta(request, fileItem);
        } else if (accion.equals(AWTramiteSuspension.AVANZAR)) {
            evento = avanzar(request);
        } else if (accion.equals(CANCELAR_TRAMITE_SUSP_TEMP) || accion.equals(CANCELAR_TRAMITE_SUSP_PREV)) {
            return cancelarTramiteSuspension(request);
        }

        return evento;
    }

    public EvnTramiteSuspension limpiarSession(HttpServletRequest request) {

        HttpSession session = request.getSession();

        session.removeAttribute(AWTramiteSuspension.LISTA_RESPUESTAS_USUARIO);
        session.removeAttribute(CTramiteSuspension.DESC_RESPUESTA);
        session.removeAttribute(CTramiteSuspension.FASE_TRAMITE_SUSP_PREV);
        session.removeAttribute(CTramiteSuspension.FILE_EXTENSION);
        session.removeAttribute(CTramiteSuspension.FILE_NAME);
        session.removeAttribute(CTramiteSuspension.FILE_SIZE);
        session.removeAttribute(CTramiteSuspension.FILE_TYPE);

        session.removeAttribute(CArchivosJustifica.FILE_SIZE);
        session.removeAttribute(CArchivosJustifica.FILE_TYPE);
        session.removeAttribute(CArchivosJustifica.FILE_EXTENSION);
        session.setAttribute(CTramiteSuspension.FLAG_LISTA, "false");
        return null;
    }

    private EvnTramiteSuspension agregarRespuesta(HttpServletRequest request, FileItem fileItem)
            throws AccionWebException {

        HttpSession session = request.getSession();
        ValidacionParametrosException e = new ValidacionParametrosException();

        Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
        Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
        String accion = (String) session.getAttribute(WebKeys.ACCION);

        session.setAttribute(CTramiteSuspension.FLAG_LISTA, "true");
        String fileName = (String) session.getAttribute(CTramiteSuspension.FILE_NAME);
        String fileSize = (String) session.getAttribute(CArchivosJustifica.FILE_SIZE);
        String fileFormat = (String) session.getAttribute(CArchivosJustifica.FILE_EXTENSION);
        String fileProceso = (String) session.getAttribute(CArchivosJustifica.NOMBRE_PROCESO);

        Usuario usuarioAuriga = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
        gov.sir.core.negocio.modelo.Usuario usuarioActual = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

        String descripcionNovedad = (String) session.getAttribute(CTramiteSuspension.DESC_RESPUESTA);
        if ((descripcionNovedad == null) || (descripcionNovedad.trim().equals(""))) {
            e.addError("Debe proporcionar una descripción para la respuesta de tramite. ");
        }

        String descripcionTamano = (String) session.getAttribute(CTramiteSuspension.TRAMS_MAX_LENGTH);
        if ((!descripcionTamano.trim().equals("0")) || (descripcionTamano == null) || (descripcionTamano.trim().equals(""))) {
            e.addError("La descripción debe tener minimo 20 carácteres. ");
        }

        gov.sir.core.negocio.modelo.ArchivosJustifica archivo = null;
        if (!fileName.trim().equals("")) {
            archivo = new gov.sir.core.negocio.modelo.ArchivosJustifica();

            if ((fileSize == null) || (fileSize.trim().equals(""))) {
                e.addError("No fue posible determinar el tamaño del archivo");
            }

            if (Integer.parseInt(fileSize) > 2000000) {
                e.addError("El tamaño del archivo supera el tamaño permitido (2MB)");
            }
            if ((fileFormat == null) || (fileFormat.trim().equals(""))) {
                e.addError("No fue posible determinar la extension del archivo");
            }

            archivo.setJusNombreOriginal(fileName);
            archivo.setJusTipoArchivo(fileFormat);
            archivo.setJusTamanoArchivo(Integer.parseInt(fileSize));
            archivo.setJusNombreProceso(fileProceso);
            archivo.setJusRutaFisica(RUTA_DESTINO_ARCHIVO_JUSTIFICACION);
            archivo.setJusFechaDeSubida(new Date());

        }

        if (e.getErrores().size() > 0) {
            throw e;
        }

        gov.sir.core.negocio.modelo.TramiteSuspension tramiteSuspension = new gov.sir.core.negocio.modelo.TramiteSuspension();

        tramiteSuspension.setTramsFecha(new Date());
        tramiteSuspension.setTramsDescripcion(descripcionNovedad);
        tramiteSuspension.setTramsUsuario(usuarioActual.getUsername());
        tramiteSuspension.setTramsIdUsuario((int) usuarioActual.getIdUsuario());
        tramiteSuspension.setTramsTurno(turno.getIdWorkflow());
        tramiteSuspension.setTramsNombreFase(fase.getNombre());

        EvnTramiteSuspension evento = null;
        if (accion.equals(AWTramiteSuspension.AGREGAR_RESPUESTA_PREV)) {
            evento = new EvnTramiteSuspension(usuarioAuriga, EvnTramiteSuspension.AGREGAR_RESPUESTA_PREV);
        } else if (accion.equals(AWTramiteSuspension.AGREGAR_RESPUESTA_TEMP)) {
            evento = new EvnTramiteSuspension(usuarioAuriga, EvnTramiteSuspension.AGREGAR_RESPUESTA_TEMP);
        }

        evento.setTurno(turno.getIdWorkflow());
        evento.setArchivosJustifica(archivo);
        evento.setFileItem(fileItem);
        evento.setTramSuspension(tramiteSuspension);
        evento.setUsuarioNegocio(usuarioActual);

        session.removeAttribute(CTramiteSuspension.DESC_RESPUESTA);
        return evento;
    }

    public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

        EvnRespTramiteSuspension respuesta = (EvnRespTramiteSuspension) evento;
        HttpSession session = request.getSession();
        context = session.getServletContext();

        if (respuesta != null) {
            String tipoEvento = respuesta.getTipoEvento();
            if (tipoEvento.equals(EvnRespTramiteSuspension.CONSULTA_RESPUESTA_OK)) {
                session.setAttribute(AWTramiteSuspension.LISTA_RESPUESTAS_USUARIO, respuesta.getListaRespuesta());
            }
        }
    }

    private FileItem cargarArchivo(HttpServletRequest request) throws AccionWebException {

        HttpSession session = request.getSession();
        CargarArchivoExcepcion exceptionArchivo = new CargarArchivoExcepcion();

        int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
        int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
        FileItem fileItem = null;

        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //Umbral de memoria
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            //ubicación temporal
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(factory);

            //tamaño maximo solicitud (data + archivo)
            upload.setSizeMax(MAX_REQUEST_SIZE);
            String uploadPath = RUTA_DESTINO_ARCHIVO_JUSTIFICACION;

            //se crea el directorio si no existe
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            // Analiza el contenido de la solicitud para extraer datos de archivo
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            String accion = "";
            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {

                    if (item.getFieldName().equals(WebKeys.ACCION)) {
                        accion = item.getString().trim();
                        session.removeAttribute(WebKeys.ACCION);
                        session.setAttribute(WebKeys.ACCION, accion);
                    }

                    if (item.getFieldName().equals(CTramiteSuspension.DESC_RESPUESTA)) {
                        session.removeAttribute(CTramiteSuspension.DESC_RESPUESTA);
                        session.setAttribute(CTramiteSuspension.DESC_RESPUESTA, item.getString());
                    } else if (item.getFieldName().equals(CTramiteSuspension.TRAMS_MAX_LENGTH)) {
                        session.removeAttribute(CTramiteSuspension.TRAMS_MAX_LENGTH);
                        session.setAttribute(CTramiteSuspension.TRAMS_MAX_LENGTH, item.getString());
                    }
                    // Sólo procesa campos que no son campos de formulario
                    if (!item.isFormField()) {

                        String fileName = new File(item.getName()).getName();
                        String fileSize = item.getSize() + "";
                        String fileType = item.getContentType();

                        session.removeAttribute(CTramiteSuspension.FILE_NAME);
                        session.setAttribute(CTramiteSuspension.FILE_NAME, fileName);

                        session.removeAttribute(CArchivosJustifica.FILE_SIZE);
                        session.setAttribute(CArchivosJustifica.FILE_SIZE, fileSize);

                        session.removeAttribute(CArchivosJustifica.FILE_TYPE);
                        session.setAttribute(CArchivosJustifica.FILE_TYPE, fileType);

                        String format = "none";

                        format = FilenameUtils.getExtension(fileName);
                        session.removeAttribute(CArchivosJustifica.FILE_EXTENSION);
                        session.setAttribute(CArchivosJustifica.FILE_EXTENSION, format);

                        fileItem = item;
                    }
                }
            }
        } catch (FileUploadException e) {
            exceptionArchivo.addError("Ocurrió un error cargando el archivo");
        }
        return fileItem;
    }
}

package gov.sir.core.web;

import gov.sir.core.eventos.comun.EvnRespSistema;
import gov.sir.core.eventos.comun.EvnSistema;
import gov.sir.core.negocio.modelo.AlcanceGeografico;
import gov.sir.core.negocio.modelo.CategoriaNotaria;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.DominioNaturalezaJuridica;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.EstadoAnotacion;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.PermisoCorreccion;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoFotocopia;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.Validacion;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.ListaElementoHelper;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.web.ProcesadorEventosNegocioProxy;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @autor HGOMEZ
 * @mantis 12422
 * @Requerimiento 049_453
 * @descripcion Se importa ValidacionesSIR para tener acceso a las formas de
 * pago.
 */
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;

import java.util.Collections;

/**
 * @author mmunoz, dsalas, jfrias, dcantor, ppabon Esta es el Servlet encargado
 * de hacer la carga en el contexto de todos los atributos que se necesitan para
 * el funcionamiento del programa.
 */
public class InitParametrosServlet extends HttpServlet {

    /**
     * Encargado de mantener los logs utilizando log4j
     */
    private static ServletLogger logger = new InitParametrosServlet.ServletLogger();

    /**
     * Variable estatica que identifica si el servlet ya fue cargado, de esta
     * manera solo se puede cargar una vez.
     */
    private static boolean CARGA_OK = false;

    /**
     * Instancia del contexto compartido por los Servlets
     */
    private ServletContext context;
    private ProcesadorEventosNegocioProxy proxy = null;
    private Usuario usuario = null;

    /**
     * Método que llama el contenedor al ser cargado.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        logger.debug("*************LOG INITPARAMETERSERVLET 1/5");
        this.context = config.getServletContext();

        logger.debug("*************LOG INITPARAMETERSERVLET 2/5");
        proxy = new ProcesadorEventosNegocioProxy();

        logger.debug("*************LOG INITPARAMETERSERVLET 3/5");
        usuario = new Usuario();
        usuario.setUsuarioId("SIR");

        logger.debug("*************LOG INITPARAMETERSERVLET 4/5");
        initParameters();

        logger.debug("*************LOG INITPARAMETERSERVLET 5/5");
    }

    /**
     * Este método es el que hace la inicializacion de todos los atributos. Para
     * los atributos que solo se necesitan la pareja identificador, valor se
     * utilizara el objeto ElementoLista, para los que contienen mas informacion
     * se se dejaran iguales.
     */
    private void initParameters() {
        if (CARGA_OK) {
            logger.info("Ya se han cargado los atributos");

            return;
        }

        logger.info("-------------- InitParametrosServlet --------------");

        try {
            logger.debug("*************LOG METODO 1/3");
            EvnRespSistema respuesta = (EvnRespSistema) proxy.manejarEvento(new EvnSistema(usuario, EvnSistema.INIT_PARAMETROS));
            logger.debug("*************LOG METODO 2/3");

            if (respuesta != null) {
                Hashtable mapa = (Hashtable) respuesta.getPayload();
                InitParametrosServlet.cargarListasEnContexto(mapa, context);

                String ipBalanceador = (String) mapa.get(EvnRespSistema.IP_BALANCEADOR);
                if (ipBalanceador == null) {
                    logger.warn("La ip del balanceador no se ha encontrado. Se colocara una cadena vaia vacia");
                    context.setAttribute(CImpresion.IP_BALANCEADOR, "");
                    logger.info("La ip del balanceador  ha sido cargada al contexto.");
                } else {
                    context.setAttribute(CImpresion.IP_BALANCEADOR, ipBalanceador);
                    logger.info("El ip del balanceador ha sido colocado en el contexto.");
                }

                String usarBalanceador = (String) mapa.get(EvnRespSistema.USAR_BALANCEADOR);
                if (usarBalanceador == null) {
                    logger.warn("No se ha podido determinar si se debe usar el balanceador. Se colocara una cadena vaia vacia");
                    context.setAttribute(CImpresion.USAR_BALANCEADOR, "");
                    logger.info("Se ha determinado si se debe usar el balanceador y la variable ha sido cargada al contexto.");
                } else {
                    context.setAttribute(CImpresion.USAR_BALANCEADOR, usarBalanceador);
                    logger.info("Se ha determinado si se debe usar el balanceador y la variable ha sido cargada al contexto.");
                }

                ////////////////////////////////////
                try {
                    String rutaFisica = (String) mapa.get(EvnRespSistema.FIRMAS_PATH);

                    //				Configuracion de la carpeta para almacenar las imagenes de firmas
                    //	String firmasPath = config.getInitParameter(WebKeys.FIRMAS_PATH);
                    if ((rutaFisica == null) || rutaFisica.trim().equals("")) {
                        logger.error("Debe proporcionar la ruta de la carpeta para almacenar los archivos graficos de firmas.");
                    } else {
                        File carpetaFirmas = new File(rutaFisica);

                        if (!carpetaFirmas.exists()) {
                            try {
                                if (carpetaFirmas.mkdir()) {
                                    logger.debug("Carpeta de imagenes de firmas creada satisfactoriamente");
                                } else {
                                    logger.error("No se pudo crear la carpeta para almacenar las  imagenes de firmas");
                                }
                            } catch (java.lang.SecurityException e) {
                                logger.error("El directorio proporcionado para almacenar los archivos graficos de firmas no tiene permisos de acceso");
                            }
                        }

                        if (!carpetaFirmas.isDirectory()) {
                            logger.error("Debe proporcionar una ruta de Directorio para almacenar los archivos graficos de firmas");
                        }

                        if (!carpetaFirmas.canWrite()) {
                            logger.error("No se pueden escribir archivos en el Directorio proporcionado para almacenar los archivos graficos de firmas");
                        }

                        if (!carpetaFirmas.canRead()) {
                            logger.error("No se pueden leer archivos del Directorio proporcionado para almacenar los archivos graficos de firmas");
                        }

                        context.setAttribute(WebKeys.FIRMAS_DIRECTORY, carpetaFirmas);
                        logger.info("El directorio de almacenamiento de imagenes de firmas ha sido cargado al contexto.");
                        System.out.println("-------------------------------------------------------------------");
                        System.out.println("-------------------------------------------------------------------");
                        System.out.println("");
                        System.out.println("FIRMAS_DIRECTORY = " + carpetaFirmas);
                        System.out.println("");
                        System.out.println("-------------------------------------------------------------------");
                        System.out.println("-------------------------------------------------------------------");
                    }

                    String firmasContentType = (String) mapa.get(EvnRespSistema.FIRMAS_CONTENT_TYPE);
                    if ((rutaFisica == null) || rutaFisica.trim().equals("")) {
                        logger.error("Debe proporcionar un tipo de contenido para los archivos graficos de firmas.");
                    } else {
                        context.setAttribute(WebKeys.FIRMAS_CONTENT_TYPE, firmasContentType);
                        logger.info("El tipo de contenido de las imagenes de firmas ha sido cargado al contexto.");
                    }

                    String reportesServletUrl = (String) mapa.get(EvnRespSistema.REPORTES_SERVLET_URL);
                    if ((reportesServletUrl == null) || reportesServletUrl.trim().equals("")) {
                        logger.error("Debe proporcionar la url del servlet de reportes.");
                    } else {
                        context.setAttribute(WebKeys.REPORTES_SERVLET_URL, reportesServletUrl);
                        logger.info("la url del servlet de reportes ha sido cargada al contexto.");
                    }

                    String maximoNumCertificados = (String) mapa.get(EvnRespSistema.MAXIMO_COPIAS_CERTIFICADO);
                    if ((maximoNumCertificados == null) || maximoNumCertificados.trim().equals("")) {
                        logger.error("Debe proporcionar un valor correcto para el número máximo de copias de certificados");
                    } else {
                        context.setAttribute(WebKeys.MAXIMO_COPIAS_CERTIFICADO, maximoNumCertificados);
                        logger.info("El número máximo de copias de certificados ha sido cargado al contexto: " + maximoNumCertificados);
                    }
                } catch (Exception e) {
                    logger.error("Falló la consulta sobre HermodProperties.  " + e.getMessage());
                }

                ////
                logger.info("Se inicio la carga de los atributos de SIR correctamente.");
                CARGA_OK = true;
            } else {
                logger.warn("No se pudo iniciar la carga de los atributos de SIR correctamente.");
            }
            logger.debug("*************LOG METODO INICIO 3/3");
            logger.debug("*************TERMINO EL CARGUE SATISFACTORIAMENTE*************");
        } catch (EventoException e) {
            logger.warn("No se pudo iniciar la carga de los atributos de SIR correctamente.");
        }
    }

    /**
     *
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String key = request.getParameter("SIRKEY");

        if (key.equals("UP")) {
            proxy = new ProcesadorEventosNegocioProxy();
            usuario = new Usuario();
            usuario.setUsuarioId("SIR");
            initParameters();
            pw.println("SIR: UP");
        } else if (key.equals("DOWN")) {
            try {
                proxy.manejarEvento(new EvnSistema(usuario, EvnSistema.FINALIZAR_SERVICIOS));
                pw.println("SIR: DOWN");
            } catch (EventoException e) {
                logger.warn("No se pudo destruir los servicios de SIR correctamente.");
            }

            logger.info("Se ha detenido correctamente InitParametrosServlet");
        } else if (key.equals("LOG")) {

            Iterator itMensajes = logger.getMensajes().iterator();
            pw.println("Mensajes originados por la clase InitParamsServlet<BR><BR>");
            pw.println("<table>");

            while (itMensajes.hasNext()) {
                pw.println("<tr>");
                pw.println("<td>");
                pw.println((String) itMensajes.next());
                pw.println("</td>");
                pw.println("</tr>");
            }

            pw.println("<table>");
        } else if (key.equals("CLEARLOG")) {
            logger.clear();
            pw.println("SIR: CLEARLOG");
        }
    }

    /**
     *
     */
    public void destroy() {
        super.destroy();

    }

    public static class ServletLogger {

        private List mensajes = new Vector();

        public void info(String mensaje) {
            Log.getInstance().info(InitParametrosServlet.class, mensaje);
            mensajes.add(mensaje);
        }

        /**
         *
         */
        public void clear() {
            mensajes.clear();
        }

        public void warn(String mensaje) {
            Log.getInstance().warn(InitParametrosServlet.class, mensaje);
            mensajes.add(mensaje);
        }

        public void error(String mensaje) {
            Log.getInstance().error(InitParametrosServlet.class, mensaje);
            mensajes.add(mensaje);
        }

        public void debug(String mensaje) {
            Log.getInstance().debug(InitParametrosServlet.class, mensaje);
            mensajes.add(mensaje);
        }

        public void fatal(String mensaje) {
            Log.getInstance().fatal(InitParametrosServlet.class, mensaje);
            mensajes.add(mensaje);
        }

        /**
         * @return
         */
        public List getMensajes() {
            return mensajes;
        }
    }

    public static void cargarListasEnContexto(Hashtable mapa, ServletContext context) {
        try {
            List tiposID = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_ID);

            if (tiposID == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_ID) == null) {
                    logger.warn("La lista de los tipos de identificacion no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_ID, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de identificacion ha sido cargada al contexto.");
                }
            } else {
                Iterator itTiposId = tiposID.iterator();
                List elementoTiposId = new ArrayList();

                while (itTiposId.hasNext()) {
                    OPLookupCodes tipoId = (OPLookupCodes) itTiposId.next();
                    elementoTiposId.add(new ElementoLista(tipoId.getCodigo(), tipoId.getValor()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementoTiposId, 0, elementoTiposId.size() - 1);
                logger.info("La lista de los tipos de identificacion ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TIPOS_ID, Collections.unmodifiableList(elementoTiposId));
                logger.info("La lista de los tipos de identificacion ha sido cargada al contexto.");
            }
            List tiposIDNatural = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_ID_NATURAL);

            if (tiposIDNatural == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_ID_NATURAL) == null) {
                    logger.warn("La lista de los tipos de identificacion no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_ID_NATURAL, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de identificacion ha sido cargada al contexto.");
                }
            } else {
                Iterator itTiposIdNatural = tiposIDNatural.iterator();
                List elementoTiposIdNatural = new ArrayList();

                while (itTiposIdNatural.hasNext()) {
                    OPLookupCodes tipoIdNatural = (OPLookupCodes) itTiposIdNatural.next();
                    elementoTiposIdNatural.add(new ElementoLista(tipoIdNatural.getCodigo(), tipoIdNatural.getValor()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementoTiposIdNatural, 0, elementoTiposIdNatural.size() - 1);
                logger.info("La lista de los tipos de identificacion ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TIPOS_ID_NATURAL, Collections.unmodifiableList(elementoTiposIdNatural));
                logger.info("La lista de los tipos de identificacion ha sido cargada al contexto.");
            }
            
            List tiposIDJuridica = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_ID_JURIDICA);

            if (tiposIDJuridica == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_ID_JURIDICA) == null) {
                    logger.warn("La lista de los tipos de identificacion para personas juridicas no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_ID_JURIDICA, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de identificacion para personas juridicas ha sido cargada al contexto.");
                }
            } else {
                Iterator itTiposIdJuridica = tiposIDJuridica.iterator();
                List elementoTiposIdJuridica = new ArrayList();

                while (itTiposIdJuridica.hasNext()) {
                    OPLookupCodes tipoIdJuridica = (OPLookupCodes) itTiposIdJuridica.next();
                    elementoTiposIdJuridica.add(new ElementoLista(tipoIdJuridica.getCodigo(), tipoIdJuridica.getValor()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementoTiposIdJuridica, 0, elementoTiposIdJuridica.size() - 1);
                logger.info("La lista de los tipos de identificacion para personas juridicas ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TIPOS_ID_JURIDICA, Collections.unmodifiableList(elementoTiposIdJuridica));
                logger.info("La lista de los tipos de identificacion para personas juridicas ha sido cargada al contexto.");
            }
            
            List modalidad = (List) mapa.get(EvnRespSistema.LISTA_MODALIDAD);

            if (modalidad == null) {
                if (context.getAttribute(WebKeys.LISTA_MODALIDAD) == null) {
                    logger.warn("La lista de los tipos de modalidad no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_MODALIDAD, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de modalidad ha sido cargada al contexto.");
                }
            } else {
                Iterator itTiposMod = modalidad.iterator();
                List elementoTiposMod = new ArrayList();

                while (itTiposMod.hasNext()) {
                    OPLookupCodes tipoMod = (OPLookupCodes) itTiposMod.next();
                    elementoTiposMod.add(new ElementoLista(tipoMod.getCodigo(), tipoMod.getValor()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementoTiposMod, 0, elementoTiposMod.size() - 1);
                logger.info("La lista de los tipos de modalidad ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_MODALIDAD, Collections.unmodifiableList(elementoTiposMod));
                logger.info("La lista de los tipos de modalidad ha sido cargada al contexto.");
            }
            
            List tiposDetermInm = (List) mapa.get(EvnRespSistema.LISTA_DETERMINACION_INM);

            if (tiposDetermInm == null) {
                if (context.getAttribute(WebKeys.LISTA_DETERMINACION_INM) == null) {
                    logger.warn("La lista de los tipos de determinacion del inmueble no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_DETERMINACION_INM, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de determinacion del inmueble ha sido cargada al contexto.");
                }
            } else {
                Iterator itTiposDeterm = tiposDetermInm.iterator();
                List elementoTiposDeterm = new ArrayList();

                while (itTiposDeterm.hasNext()) {
                    OPLookupCodes tipoDeterm = (OPLookupCodes) itTiposDeterm.next();
                    elementoTiposDeterm.add(new ElementoLista(tipoDeterm.getCodigo(), tipoDeterm.getValor()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementoTiposDeterm, 0, elementoTiposDeterm.size() - 1);
                logger.info("La lista de los tipos de determinacion del inmueble ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_DETERMINACION_INM, Collections.unmodifiableList(elementoTiposDeterm));
                logger.info("La lista de los tipos de determinacion del inmueble ha sido cargada al contexto.");
            }
            
            List tiposPersona = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_PERSONA);

            if (tiposPersona == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_PERSONA) == null) {
                    logger.warn("La lista de los tipos de persona no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_PERSONA, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de persona ha sido cargada al contexto.");
                }
            } else {
                Iterator itTiposPer = tiposPersona.iterator();
                List elementoTiposPer = new ArrayList();

                while (itTiposPer.hasNext()) {
                    OPLookupCodes tipoPersona = (OPLookupCodes) itTiposPer.next();
                    elementoTiposPer.add(new ElementoLista(tipoPersona.getCodigo(), tipoPersona.getValor()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementoTiposPer, 0, elementoTiposPer.size() - 1);
                logger.info("La lista de los tipos de persona ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TIPOS_PERSONA, Collections.unmodifiableList(elementoTiposPer));
                logger.info("La lista de los tipos de persona ha sido cargada al contexto.");
            }
            
            List tiposSexo = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_SEXO);

            if (tiposSexo == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_SEXO) == null) {
                    logger.warn("La lista de los tipos de sexo no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_SEXO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de sexo ha sido cargada al contexto.");
                }
            } else {
                Iterator itTiposSexo = tiposSexo.iterator();
                List elementoTiposSexo = new ArrayList();

                while (itTiposSexo.hasNext()) {
                    OPLookupCodes tipoSexo = (OPLookupCodes) itTiposSexo.next();
                    elementoTiposSexo.add(new ElementoLista(tipoSexo.getCodigo(), tipoSexo.getValor()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementoTiposSexo, 0, elementoTiposSexo.size() - 1);
                logger.info("La lista de los tipos de sexo ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TIPOS_SEXO, Collections.unmodifiableList(elementoTiposSexo));
                logger.info("La lista de los tipos de sexo ha sido cargada al contexto.");
            }

            List tiposCert = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_CERTIFICADOS);

            if (tiposCert == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_CERTIFICADOS) == null) {
                    logger.warn("La lista de los tipos de certificado no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_CERTIFICADOS, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de certificado ha sido cargada al contexto.");
                }
            } else {
                Iterator itTiposCertificado = tiposCert.iterator();
                List elementosTiposCertificado = new ArrayList();

                while (itTiposCertificado.hasNext()) {
                    TipoCertificado tipoCertificado = (TipoCertificado) itTiposCertificado.next();
                    elementosTiposCertificado.add(new ElementoLista(tipoCertificado.getIdTipoCertificado(), tipoCertificado.getNombre()));
                }

                // ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosTiposCertificado, 0, elementosTiposCertificado.size() - 1);
                logger.info("La lista de los tipos de certificado ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TIPOS_CERTIFICADOS, Collections.unmodifiableList(elementosTiposCertificado));
                logger.info("La lista de los tipos de certificado ha sido cargada al contexto.");
            }

            List bancos = (List) mapa.get(EvnRespSistema.LISTA_BANCOS);

            if (bancos == null) {
                if (context.getAttribute(WebKeys.LISTA_BANCOS) == null) {
                    logger.warn("La lista de los bancos no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_BANCOS, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los bancos ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_BANCOS, Collections.unmodifiableList(bancos));
                logger.info("La lista de los bancos ha sido cargada al contexto.");
            }
            
            List canales = (List) mapa.get(EvnRespSistema.LISTA_CANALES_RECAUDO);

            if (canales == null) {
                if (context.getAttribute(WebKeys.LISTA_CANALES_RECAUDO) == null) {
                    logger.warn("La lista de los canales de recaudo no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_CANALES_RECAUDO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los canales de recaudo ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_CANALES_RECAUDO, Collections.unmodifiableList(canales));
                logger.info("La lista de los canales de recaudo ha sido cargada al contexto.");
            }

            /**
             * @autor HGOMEZ
             * @mantis 12422
             * @Requerimiento 049_453
             * @descripcion Se hace el cargue de los bancos y sus respectivas
             * franquicias.
             */
            ValidacionesSIR validacion = new ValidacionesSIR();

            List bancosFranquicias = null;

            try {
                bancosFranquicias = validacion.getBancoFranquicia();
            } catch (Exception e) {//(GeneralSIRException ex) {
                //Logger.getLogger(InitParametrosServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (bancosFranquicias == null) {
                if (context.getAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS) == null) {
                    logger.warn("La lista de los bancos no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los bancos ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_BANCOS_FRANQUICIAS, Collections.unmodifiableList(bancosFranquicias));
                logger.info("La lista de los bancos ha sido cargada al contexto.");
            }
            List ejes = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_EJES);

            if (ejes == null) {
                if (context.getAttribute(WebKeys.LISTA_EJES_DIRECCION) == null) {
                    logger.warn("La lista de los tipos de ejes no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_EJES_DIRECCION, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de ejes ha sido cargada al contexto.");
                }
            } else {
                Iterator itEjes = ejes.iterator();
                List elementosEjes = new ArrayList();

                while (itEjes.hasNext()) {
                    Eje eje = (Eje) itEjes.next();
                    elementosEjes.add(new ElementoLista(eje.getIdEje(), eje.getNombre()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosEjes, 0, elementosEjes.size() - 1);
                logger.info("La lista de los tipos de ejes ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_EJES_DIRECCION, Collections.unmodifiableList(elementosEjes));
                logger.info("La lista de los tipos de ejes ha sido cargada al contexto.");
            }

            List tiposPredio = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_PREDIO);

            if (tiposPredio == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_PREDIO) == null) {
                    logger.warn("La lista de los tipos de predio no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_PREDIO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de predio ha sido cargada al contexto.");
                }
            } else {
                Iterator itPredio = tiposPredio.iterator();
                List elementosPredio = new ArrayList();

                while (itPredio.hasNext()) {
                    TipoPredio predio = (TipoPredio) itPredio.next();
                    elementosPredio.add(new ElementoLista(predio.getIdPredio(), predio.getNombre()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosPredio, 0, elementosPredio.size() - 1);
                logger.info("La lista de los tipos de predio sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TIPOS_PREDIO, Collections.unmodifiableList(elementosPredio));
                logger.info("La lista de los tipos de predio ha sido cargada al contexto.");
            }

            List tiposDocumento = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_DOCUMENTO);

            if (tiposDocumento == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO) == null) {
                    logger.warn("La lista de los tipos de documento no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de documento ha sido cargada al contexto.");
                }
            } else {
                Iterator itTipoDocumento = tiposDocumento.iterator();
                List elementosTipoDoc = new ArrayList();

                while (itTipoDocumento.hasNext()) {
                    TipoDocumento tipoDoc = (TipoDocumento) itTipoDocumento.next();
                    elementosTipoDoc.add(new ElementoLista(tipoDoc.getIdTipoDocumento(), tipoDoc.getNombre()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.setCampoOrdenamiento(ListaElementoHelper.ORDENAMIENTO_POR_ID);
                qs.quickSort(elementosTipoDoc, 0, elementosTipoDoc.size() - 1);
                logger.info("La lista de los tipos de documento sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO, Collections.unmodifiableList(elementosTipoDoc));
                logger.info("La lista de los tipos de documento ha sido cargada al contexto.");
            }

            List gruposNat = (List) mapa.get(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);

            if (gruposNat == null) {
                if (context.getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS) == null) {
                    logger.warn("La lista de los grupos de la naturaleza juridica no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los grupos de la naturaleza juridica ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS, Collections.unmodifiableList(gruposNat));
                logger.info("La lista de los grupos de la naturaleza juridica ha sido cargada al contexto.");
            }
            /*
        *  @author Carlos Torres
        *  @chage   se agrega nueva lista de contexto
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
             */
            List gruposNatV = (List) mapa.get(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V);

            if (gruposNatV == null) {
                if (context.getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V) == null) {
                    logger.warn("La lista de los grupos de la naturaleza juridica no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los grupos de la naturaleza juridica ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V, Collections.unmodifiableList(gruposNatV));
                logger.info("La lista de los grupos de la naturaleza juridica ha sido cargada al contexto.");
            }

            List interesesJuridicos = (List) mapa.get(EvnRespSistema.LISTA_INTERES_JURIDICO);

            if (interesesJuridicos == null) {
                if (context.getAttribute(WebKeys.LISTA_INTERES_JURIDICO) == null) {
                    logger.warn("La lista de interés jurídico no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_INTERES_JURIDICO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de interés jurídico ha sido cargada al contexto.");
                }
            } else {
                Iterator it = interesesJuridicos.iterator();
                List elementoTiposId = new ArrayList();

                while (it.hasNext()) {
                    OPLookupCodes tipoId = (OPLookupCodes) it.next();
                    elementoTiposId.add(new ElementoLista(tipoId.getCodigo(), tipoId.getValor()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementoTiposId, 0, elementoTiposId.size() - 1);
                logger.info("La lista de interes juridico sido ordenada.");

                context.setAttribute(WebKeys.LISTA_INTERES_JURIDICO, Collections.unmodifiableList(elementoTiposId));
                logger.info("La lista de interes juridico ha sido cargada al contexto.");
            }

            List gruposNatCalificacion = (List) mapa.get(EvnRespSistema.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION);

            if (gruposNatCalificacion == null) {
                if (context.getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION) == null) {
                    logger.warn("La lista de los grupos de la naturaleza juridica de calificacion no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los grupos de la naturaleza juridica de calificación ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_CALIFICACION, Collections.unmodifiableList(gruposNatCalificacion));
                logger.info("La lista de los grupos de la naturaleza juridica ha sido cargada al contexto.");
            }

            List natJuridicasEntidad = (List) mapa.get(EvnRespSistema.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD);

            if (natJuridicasEntidad == null) {
                if (context.getAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD) == null) {
                    logger.warn("La lista de las naturalezas juridicas para reparto no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de las naturalezas juridicas para reparto ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD, Collections.unmodifiableList(natJuridicasEntidad));
                logger.info("La lista de las naturalezas juridicas para reparto ha sido cargada al contexto.");
            }

            List natJuridicasEntidadActivas = (List) mapa.get(EvnRespSistema.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS);

            if (natJuridicasEntidadActivas == null) {
                if (context.getAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS) == null) {
                    logger.warn("La lista de las naturalezas juridicas activas para reparto no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de las naturalezas juridicas activas para reparto ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_NATURALEZAS_JURIDICAS_ENTIDAD_ACTIVAS, Collections.unmodifiableList(natJuridicasEntidadActivas));
                logger.info("La lista de las naturalezas juridicas activas para reparto ha sido cargada al contexto.");
            }

            List entidadesPublicas = (List) mapa.get(EvnRespSistema.LISTA_ENTIDADES_PUBLICAS);

            if (entidadesPublicas == null) {
                if (context.getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS) == null) {
                    logger.warn("La lista de las entidades publicas para reparto no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de las entidades publicas para reparto ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS, Collections.unmodifiableList(entidadesPublicas));
                logger.info("La lista de las entidades publicas para reparto ha sido cargada al contexto.");
            }

            List entidadesPublicasActivas = (List) mapa.get(EvnRespSistema.LISTA_ENTIDADES_PUBLICAS_ACTIVAS);

            if (entidadesPublicasActivas == null) {
                if (context.getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS_ACTIVAS) == null) {
                    logger.warn("La lista de las entidades publicas activas para reparto no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS_ACTIVAS, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de las entidades publicas activas para reparto ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS_ACTIVAS, Collections.unmodifiableList(entidadesPublicasActivas));
                logger.info("La lista de las entidades publicas activas para reparto ha sido cargada al contexto.");
            }

            List causalesReImp = (List) mapa.get(EvnRespSistema.LISTA_CAUSALES_REIMPRESION);

            if (causalesReImp == null) {
                if (context.getAttribute(WebKeys.LISTA_CAUSALES_REIMPRESION) == null) {
                    logger.warn("La lista de los causales de reimpresion no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_CAUSALES_REIMPRESION, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los causales de reimpresion ha sido cargada al contexto.");
                }
            } else {
                Iterator itCausales = causalesReImp.iterator();
                List elementosCausales = new ArrayList();

                while (itCausales.hasNext()) {
                    OPLookupCodes causal = (OPLookupCodes) itCausales.next();
                    elementosCausales.add(new ElementoLista(causal.getCodigo(), causal.getValor()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosCausales, 0, elementosCausales.size() - 1);
                logger.info("La lista de los causales de reimpresion sido ordenada.");

                context.setAttribute(WebKeys.LISTA_CAUSALES_REIMPRESION, Collections.unmodifiableList(elementosCausales));
                logger.info("La lista de los causales de reimpresion ha sido cargada al contexto.");
            }

            //TIPOS DE VISIBILIDAD NOTAS INFORMATIVAS
            List visibilidades = (List) mapa.get(EvnRespSistema.LISTA_VISIBILIDADES);

            if (visibilidades == null) {
                if (context.getAttribute(WebKeys.LISTA_VISIBILIDAD_NOTAS) == null) {
                    logger.warn("La lista de visibilidades de notas informativas no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_VISIBILIDAD_NOTAS, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de visibilidades de notas informativas ha sido cargada al contexto.");
                }
            } else {
                Iterator itVisibilidades = visibilidades.iterator();
                List elementosVisibilidad = new ArrayList();

                while (itVisibilidades.hasNext()) {
                    OPLookupCodes visibilidad = (OPLookupCodes) itVisibilidades.next();
                    elementosVisibilidad.add(new ElementoLista(visibilidad.getCodigo(), visibilidad.getValor()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosVisibilidad, 0, elementosVisibilidad.size() - 1);
                logger.info("La lista de visibilidades de notas informativas ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_VISIBILIDADES, Collections.unmodifiableList(elementosVisibilidad));
                logger.info("La lista de visibilidades de notas informativas ha sido cargada al contexto.");
            }

            //TIPOS DE TARIFAS DE CERTIFICADOS
            List tarifasCertificados = (List) mapa.get(EvnRespSistema.LISTA_TARIFAS_CERTIFICADOS);

            if (tarifasCertificados == null) {
                if (context.getAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS) == null) {
                    logger.warn("La lista de tarifas de certificados no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de tarifas de certificados ha sido cargada al contexto.");
                }
            } else {
                Iterator itTarifasCert = tarifasCertificados.iterator();
                List elementosTarifasCert = new ArrayList();

                while (itTarifasCert.hasNext()) {
                    OPLookupCodes tarifaCert = (OPLookupCodes) itTarifasCert.next();
                    elementosTarifasCert.add(new ElementoLista(tarifaCert.getCodigo(), tarifaCert.getCodigo()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosTarifasCert, 0, elementosTarifasCert.size() - 1);
                logger.info("La lista de tarifas de certificados ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TARIFAS_CERTIFICADOS, Collections.unmodifiableList(elementosTarifasCert));
                logger.info("La lista de tarifas de certificados ha sido cargada al contexto.");
            }

            List alcanceGeografico = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_ALCANCE_GEOGRAFICO);

            if (alcanceGeografico == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_ALCANCE_GEOGRAFICO) == null) {
                    logger.warn("La lista de los alcances geográficos no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_ALCANCE_GEOGRAFICO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los alcances geográficos ha sido cargada al contexto.");
                }
            } else {
                Iterator itAlcance = alcanceGeografico.iterator();
                List elementosAlcance = new ArrayList();

                while (itAlcance.hasNext()) {
                    AlcanceGeografico alcance = (AlcanceGeografico) itAlcance.next();
                    elementosAlcance.add(new ElementoLista(alcance.getIdAlcanceGeografico(), alcance.getNombre()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosAlcance, 0, elementosAlcance.size() - 1);
                logger.info("La lista de los alcances geográficos ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TIPOS_ALCANCE_GEOGRAFICO, Collections.unmodifiableList(elementosAlcance));
                logger.info("La lista de los alcances geográficos ha sido cargada al contexto.");
            }

            List tiposFotocopia = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_FOTOCOPIA);

            if (tiposFotocopia == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_FOTOCOPIA) == null) {
                    logger.warn("La lista de los tipos de fotocopia no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_FOTOCOPIA, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de fotocopia ha sido cargada al contexto.");
                }

            } else {
                Iterator itTipoFotocopia = tiposFotocopia.iterator();
                List elementosTipoFotocopia = new ArrayList();

                while (itTipoFotocopia.hasNext()) {
                    TipoFotocopia tipoFotocopia = (TipoFotocopia) itTipoFotocopia.next();
                    elementosTipoFotocopia.add(new ElementoLista(tipoFotocopia.getIdTipoFotocopia(), tipoFotocopia.getNombre()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosTipoFotocopia, 0, elementosTipoFotocopia.size() - 1);
                logger.info("La lista de los tipos de fotocopia ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TIPOS_FOTOCOPIA, Collections.unmodifiableList(elementosTipoFotocopia));
                logger.info("La lista de los tipos de fotocopia ha sido cargada al contexto.");
            }

            List subtiposAtencion = (List) mapa.get(EvnRespSistema.LISTA_SUBTIPOS_ATENCION);

            if (subtiposAtencion == null) {
                if (context.getAttribute(WebKeys.LISTA_SUBTIPOS_ATENCION) == null) {
                    logger.warn("La lista de los subtipos de atencion no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_SUBTIPOS_ATENCION, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los subtipos de atencion ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_SUBTIPOS_ATENCION, Collections.unmodifiableList(subtiposAtencion));
                logger.info("La lista de los subtipos de atencion ha sido cargada al contexto.");
            }

            List subtiposSolicitud = (List) mapa.get(EvnRespSistema.LISTA_SUBTIPOS_SOLICITUD);

            if (subtiposSolicitud == null) {
                if (context.getAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD) == null) {
                    logger.warn("La lista de los subtipos de solicitud no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los subtipos de solicitud ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_SUBTIPOS_SOLICITUD, Collections.unmodifiableList(subtiposSolicitud));
                logger.info("La lista de los subtipos de solicitud ha sido cargada al contexto.");
            }

            List tiposActo = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_ACTO);

            if (tiposActo == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_ACTO) == null) {
                    logger.warn("La lista de los tipos de acto no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_ACTO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de acto ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_TIPOS_ACTO, Collections.unmodifiableList(tiposActo));
                logger.info("La lista de los tipos de acto ha sido cargada al contexto.");
            }

            /*
                         *@autor          : JATENCIA
                         * @mantis        : 0015082 
                         * @Requerimiento : 027_589_Acto_liquidación_copias 
                         * @descripcion   : Se realiza el llamado al metodo que se 
                         * creo para cargar la lista de Tipos de Actos, sin filtro.
             */
            List tiposActoDos = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_ACTO_DOS);

            if (tiposActoDos == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_ACTO_DOS) == null) {
                    logger.warn("La lista de los tipos de acto no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_ACTO_DOS, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de acto ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_TIPOS_ACTO_DOS, Collections.unmodifiableList(tiposActoDos));
                logger.info("La lista de los tipos de acto ha sido cargada al contexto.");
            }
            /* - Fin del bloque - */

            List tipoImpuestos = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_IMPUESTO);

            if (tipoImpuestos == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_IMPUESTO) == null) {
                    logger.warn("La lista de los tipos de impuesto no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_IMPUESTO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de impuesto ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_TIPOS_IMPUESTO, Collections.unmodifiableList(tipoImpuestos));
                logger.info("La lista de los tipos de impuesto ha sido cargada al contexto.");
            }

            /**
             * @author: Fernando Padilla Velez
             * @change: Modificado para el caso MANTIS 135_141_Impuesto Meta, se
             * capturan la lista de TipoImpuestoCirculo y se agrega en el
             * contaxto de la aplicacion.
             */
            List tipoImpuestosCirculo = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_IMPUESTO_CIRCULO);

            if (tipoImpuestosCirculo == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_IMPUESTO_CIRCULO) == null) {
                    logger.warn("La lista de los tipos de impuesto por circulo no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_IMPUESTO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de impuesto por circulo ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_TIPOS_IMPUESTO_CIRCULO, Collections.unmodifiableList(tipoImpuestosCirculo));
                logger.info("La lista de los tipos de impuesto por circulo ha sido cargada al contexto.");
            }

            List departamentos = (List) mapa.get(EvnRespSistema.LISTA_DEPARTAMENTOS);
            Map mapDeptos = Collections.synchronizedMap(new TreeMap());

            if (departamentos == null) {
                if (context.getAttribute(WebKeys.MAPA_DEPARTAMENTOS) == null) {
                    logger.warn("La lista de departamentos no pudo ser cargada. Se colocara un mapa vacio");
                    context.setAttribute(WebKeys.MAPA_DEPARTAMENTOS, mapDeptos);
                    logger.info("El mapa de departamentos ha sido cargada al contexto.");
                }
            } else {
                for (Iterator itDepto = departamentos.iterator(); itDepto.hasNext();) {
                    Departamento depto = (Departamento) itDepto.next();
                    String key = depto.getNombre() + "-" + depto.getIdDepartamento();
                    mapDeptos.put(key, depto);
                }

                context.setAttribute(WebKeys.MAPA_DEPARTAMENTOS, mapDeptos);
                logger.info("El mapa de departamentos ha sido cargada al contexto.");
            }

            if (departamentos == null) {
                if (context.getAttribute(WebKeys.LISTA_DEPARTAMENTOS) == null) {
                    logger.warn("La lista de los departamentos no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_DEPARTAMENTOS, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de tarifas ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_DEPARTAMENTOS, Collections.unmodifiableList(departamentos));
                logger.info("La lista de los departamentos ha sido cargada al contexto.");
            }

            List tipoTarifas = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_TARIFA);

            if (tipoTarifas == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_TARIFA) == null) {
                    logger.warn("La lista de los tipos de tarifas no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_TARIFA, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de tarifas ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_TIPOS_TARIFA, Collections.unmodifiableList(tipoTarifas));
                logger.info("La lista de los tipos de tarifas ha sido cargada al contexto.");
            }

            List opLookupTypes = (List) mapa.get(EvnRespSistema.LISTA_OPLOOKUP_TYPE);

            if (opLookupTypes == null) {
                if (context.getAttribute(WebKeys.LISTA_OPLOOKUP_TYPES) == null || context.getAttribute(WebKeys.LISTA_OPLOOKUP_TYPES_OBJETOS) == null) {
                    logger.warn("La lista de Lookup Types no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_OPLOOKUP_TYPES, Collections.unmodifiableList(new ArrayList()));
                    context.setAttribute(WebKeys.LISTA_OPLOOKUP_TYPES_OBJETOS, Collections.unmodifiableList(new ArrayList()));

                    logger.info("La lista de Lookup Types ha sido cargada al contexto.");
                }
            } else {
                List elementos = new ArrayList();

                for (Iterator iter = opLookupTypes.iterator(); iter.hasNext();) {
                    OPLookupTypes dato = (OPLookupTypes) iter.next();
                    elementos.add(new ElementoLista(dato.getTipo(), dato.getTipo()));
                }

                context.setAttribute(WebKeys.LISTA_OPLOOKUP_TYPES, Collections.unmodifiableList(elementos));

                context.setAttribute(WebKeys.LISTA_OPLOOKUP_TYPES_OBJETOS, Collections.unmodifiableList(opLookupTypes));

                logger.info("La lista de Lookup Types ha sido cargada al contexto.");
            }

            List tiposCalculo = (List) mapa.get(EvnRespSistema.LISTA_TIPO_CALCULO);

            if (tiposCalculo == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_CALCULO) == null) {
                    logger.warn("La lista de tipos de cálculo no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_CALCULO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de tipos de cálculo ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_TIPOS_CALCULO, Collections.unmodifiableList(tiposCalculo));
                logger.info("La lista de tipos de cálculo ha sido cargada al contexto.");
            }

            List tiposDerechoRegistral = (List) mapa.get(EvnRespSistema.LISTA_DERECHOS_REGISTRALES);

            if (tiposDerechoRegistral == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_DERECHO_REGISTRAL) == null) {
                    logger.warn("La lista de tipos de derecho registral no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_DERECHO_REGISTRAL, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de tipos de derecho registral ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_TIPOS_DERECHO_REGISTRAL, Collections.unmodifiableList(tiposDerechoRegistral));
                logger.info("La lista de derecho registral ha sido cargada al contexto.");
            }

            List estadosFolio = (List) mapa.get(EvnRespSistema.LISTA_ESTADOS_FOLIO);

            if (estadosFolio == null) {
                if (context.getAttribute(WebKeys.LISTA_ESTADOS_FOLIO) == null) {
                    logger.warn("La lista de tipos de estados de folio no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_ESTADOS_FOLIO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de estados de folio ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_ESTADOS_FOLIO, Collections.unmodifiableList(estadosFolio));

                Iterator itEstado = estadosFolio.iterator();
                List elementosEstadoFolio = new ArrayList();

                while (itEstado.hasNext()) {
                    EstadoFolio estado = (EstadoFolio) itEstado.next();

                    if (!estado.getIdEstado().equals(CEstadoFolio.OBSOLETO)) {
                        elementosEstadoFolio.add(new ElementoLista(estado.getIdEstado(), estado.getNombre()));
                    }
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosEstadoFolio, 0, elementosEstadoFolio.size() - 1);
                logger.info("La lista de estados de folio ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_ESTADOS_FOLIO_ELEMENTO_LISTA, Collections.unmodifiableList(elementosEstadoFolio));

                boolean esnulo = (context.getAttribute(WebKeys.LISTA_ESTADOS_FOLIO) == null);
                logger.info("La lista de estados de folio ha sido cargada al contexto.");
            }

            List circulos = (List) mapa.get(EvnRespSistema.LISTA_CIRCULOS_REGISTRALES);

            if (circulos == null) {
                if (context.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES) == null) {
                    logger.warn("La lista de circulos registrales no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES, new ArrayList());
                    logger.info("La lista de circulos registrales ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES, Collections.unmodifiableList(circulos));
                logger.info("La lista de circulos registrales  ha sido cargada al contexto.");
            }

            List circulosFechaProd = (List) mapa.get(EvnRespSistema.LISTA_CIRCULOS_REGISTRALES_FECHA);

            if (circulosFechaProd == null) {
                if (context.getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES_FECHA) == null) {
                    logger.warn("La lista de circulos registrales por fecha no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES_FECHA, new ArrayList());
                    logger.info("La lista de circulos registrales por fecha ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES_FECHA, Collections.unmodifiableList(circulosFechaProd));
                logger.info("La lista de circulos registrales por fecha ha sido cargada al contexto.");
            }

            List estadosAnotacion = (List) mapa.get(EvnRespSistema.LISTA_ESTADOS_ANOTACION);

            if (estadosAnotacion == null) {
                if (context.getAttribute(WebKeys.LISTA_ESTADOS_ANOTACION) == null) {
                    logger.warn("La lista de estados de anotación no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_ESTADOS_ANOTACION, new ArrayList());
                    logger.info("La lista de estados de anotación ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_ESTADOS_ANOTACION, Collections.unmodifiableList(estadosAnotacion));

                Iterator itEstado = estadosAnotacion.iterator();
                List elementosEstadoAnotacion = new ArrayList();

                while (itEstado.hasNext()) {
                    EstadoAnotacion estado = (EstadoAnotacion) itEstado.next();

                    //*if (!estado.getIdEstadoAn().equals(CEstadoAnotacion.OBSOLETA)) {
                    elementosEstadoAnotacion.add(new ElementoLista(estado.getIdEstadoAn(), estado.getNombre()));
                    //*}
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosEstadoAnotacion, 0, elementosEstadoAnotacion.size() - 1);
                logger.info("La lista de estados de anotación ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_ESTADOS_ANOTACION_ELEMENTO_LISTA, Collections.unmodifiableList(elementosEstadoAnotacion));
                logger.info("La lista de estados de anotación  ha sido cargada al contexto.");
            }

            List tiposOficina = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_OFICINA);

            if (tiposOficina == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_OFICINA) == null) {
                    logger.warn("La lista de tipos de oficina no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_OFICINA, new ArrayList());
                    logger.info("La lista de tipos de oficina  ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_TIPOS_OFICINA, tiposOficina);
                logger.info("La lista de tipos de oficina  ha sido cargada al contexto.");
            }

            List dominiosNatJuridica = (List) mapa.get(EvnRespSistema.LISTA_DOMINIOS_NATURALEZA_JURIDICA);

            if (dominiosNatJuridica == null) {
                if (context.getAttribute(WebKeys.LISTA_DOMINIOS_NATURALEZA_JURIDICA) == null) {
                    logger.warn("La lista de dominios de naturaleza jurídica no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_DOMINIOS_NATURALEZA_JURIDICA, new ArrayList());
                    logger.info("La lista de dominios de naturaleza jurídica  ha sido cargada al contexto.");
                }
            } else {
                List elementosDominios = new ArrayList();

                for (Iterator iter = dominiosNatJuridica.iterator(); iter.hasNext();) {
                    DominioNaturalezaJuridica dominio = (DominioNaturalezaJuridica) iter.next();
                    elementosDominios.add(new ElementoLista(dominio.getIdDominioNatJur(), dominio.getNombre()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosDominios, 0, elementosDominios.size() - 1);
                logger.info("La lista de dominios de naturaleza jurídica ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_DOMINIOS_NATURALEZA_JURIDICA, Collections.unmodifiableList(elementosDominios));
                logger.info("La lista de dominios de naturaleza jurídica ha sido cargada al contexto.");
            }

            List accionesNotariales = (List) mapa.get(EvnRespSistema.LISTA_ACCIONES_NOTARIALES);

            if (accionesNotariales == null) {
                if (context.getAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES) == null) {
                    logger.warn("La lista de acciones notariales no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES, new ArrayList());
                    logger.info("La lista de acciones notariales ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES, accionesNotariales);
                logger.info("La lista de acciones notariales ha sido cargada al contexto.");
            }

            List categorias = (List) mapa.get(EvnRespSistema.LISTA_CATEGORIAS);

            if (categorias == null) {
                if (context.getAttribute(WebKeys.LISTA_CATEGORIAS) == null) {
                    logger.warn("La lista de categorias no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_CATEGORIAS, new ArrayList());
                    logger.info("La lista de categorias ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_CATEGORIAS, categorias);
                logger.info("La lista de categorias ha sido cargada al contexto.");
            }

            List causalesRestitucion = (List) mapa.get(EvnRespSistema.LISTA_CAUSALES_RESTITUCION);

            if (causalesRestitucion == null) {
                if (context.getAttribute(WebKeys.LISTA_CAUSALES_RESTITUCION) == null) {
                    logger.warn("La lista de causales de restitución no pudo ser cargada. Se colocará una lista vacia");
                    context.setAttribute(WebKeys.LISTA_CAUSALES_RESTITUCION, new ArrayList());
                    logger.info("La lista de categorias ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_CAUSALES_RESTITUCION, causalesRestitucion);
                logger.info("La lista de categorias ha sido cargada al contexto.");
            }

            List unidadesMedida = (List) mapa.get(EvnRespSistema.LISTA_UNIDADES_MEDIDA);

            if (unidadesMedida == null) {
                if (context.getAttribute(WebKeys.LISTA_CAUSALES_RESTITUCION) == null) {
                    logger.warn("La lista de las unidades de medida no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_UNIDADES_MEDIDA, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de las unidades de medida ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_UNIDADES_MEDIDA, Collections.unmodifiableList(unidadesMedida));
                logger.info("La lista de las unidades de medida ha sido cargada al contexto.");
            }

            List roles = (List) mapa.get(EvnRespSistema.LISTA_ROLES);

            if (roles == null) {
                if (context.getAttribute(WebKeys.LISTA_ROLES_SISTEMA) == null) {
                    logger.warn("La lista de los Roles no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_ROLES_SISTEMA, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de roles ha sido cargada al contexto.");
                }
            } else {
                Iterator itRoles = roles.iterator();
                List elementosTipoDoc = new ArrayList();

                while (itRoles.hasNext()) {
                    Rol rol = (Rol) itRoles.next();
                    elementosTipoDoc.add(new ElementoLista(rol.getRolId(), rol.getNombre()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosTipoDoc, 0, elementosTipoDoc.size() - 1);
                logger.info("La lista de roles ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_ROLES_SISTEMA, Collections.unmodifiableList(elementosTipoDoc));
                logger.info("La lista de roles ha sido cargada al contexto.");
            }

            List tiposPago = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_PAGO);

            if (tiposPago == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_PAGO) == null) {
                    logger.warn("La lista de los tipos de pago no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_PAGO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los tipos de pago  ha sido cargada al contexto.");
                }
            } else {
                Iterator itTiposCertificado = tiposPago.iterator();
                List elementosTiposCertificado = new ArrayList();

                while (itTiposCertificado.hasNext()) {
                    TipoPago tipoPago = (TipoPago) itTiposCertificado.next();
                    elementosTiposCertificado.add(new ElementoLista(tipoPago.getIdTipoDocPago() + "", tipoPago.getNombre()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementosTiposCertificado, 0, elementosTiposCertificado.size() - 1);
                logger.info("La lista de estaciones ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_TIPOS_PAGO, Collections.unmodifiableList(elementosTiposCertificado));
                context.setAttribute(WebKeys.TIPOS_PAGO_LISTA, Collections.unmodifiableList(tiposPago));
                logger.info("La lista de los tipos de pago ha sido cargada al contexto.");
            }

            List estaciones = (List) mapa.get(EvnRespSistema.LISTA_ESTACIONES);

            if (estaciones == null) {
                if (context.getAttribute(WebKeys.LISTA_ESTACIONES_SISTEMA) == null) {
                    logger.warn("La lista de estaciones no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_ESTACIONES_SISTEMA, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de estaciones ha sido cargada al contexto.");
                }
            } else {
                Iterator itResp = estaciones.iterator();
                List elementos = new ArrayList();

                while (itResp.hasNext()) {
                    Estacion dato = (Estacion) itResp.next();
                    elementos.add(new ElementoLista(dato.getEstacionId(), dato.getNombre()));
                }

                //ordenar la lista con quicksort
                ListaElementoHelper qs = new ListaElementoHelper();
                qs.quickSort(elementos, 0, elementos.size() - 1);
                logger.info("La lista de estaciones ha sido ordenada.");

                context.setAttribute(WebKeys.LISTA_ESTACIONES_SISTEMA, Collections.unmodifiableList(elementos));
                logger.info("La lista de estaciones ha sido cargada al contexto.");
            }

            List niveles = (List) mapa.get(EvnRespSistema.LISTA_NIVELES);

            if (niveles == null) {
                if (context.getAttribute(WebKeys.LISTA_NIVELES_SISTEMA) == null) {
                    logger.warn("La lista de niveles no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_NIVELES_SISTEMA, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de niveles ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_NIVELES_SISTEMA, Collections.unmodifiableList(niveles));
                logger.info("La lista de niveles  ha sido cargada al contexto.");
            }

            List validacionesNota = (List) mapa.get(EvnRespSistema.LISTA_VALIDACIONES_NOTA);

            if (validacionesNota == null) {
                if (context.getAttribute(WebKeys.LISTA_VALIDACIONES_NOTA) == null) {
                    logger.warn("La lista de Validaciones Nota no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_VALIDACIONES_NOTA, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de Validaciones Nota ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_VALIDACIONES_NOTA, Collections.unmodifiableList(validacionesNota));
                logger.info("La lista de Validaciones Nota ha sido cargada al contexto.");
            }

            List procesos = (List) mapa.get(EvnRespSistema.LISTA_PROCESOS);

            if (procesos == null) {
                if (context.getAttribute(WebKeys.LISTA_PROCESOS_SISTEMA) == null) {
                    logger.warn("La lista de procesos no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_PROCESOS_SISTEMA, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de procesos ha sido cargada al contexto.");
                }
            } else {
                Iterator itResp = procesos.iterator();
                List elementos = new ArrayList();
                List elementosRecibo = new ArrayList();
                while (itResp.hasNext()) {
                    Proceso dato = (Proceso) itResp.next();
                    elementos.add(new ElementoLista("" + dato.getIdProceso(), dato.getNombre()));
                    if (dato.isGeneraRecibo()) {
                        elementosRecibo.add(new ElementoLista("" + dato.getIdProceso(), dato.getNombre()));
                    }
                }

                context.setAttribute(WebKeys.LISTA_PROCESOS_SISTEMA, Collections.unmodifiableList(elementos));
                context.setAttribute(WebKeys.LISTA_PROCESOS_RECIBO_SISTEMA, Collections.unmodifiableList(elementosRecibo));
                logger.info("La lista de procesos  ha sido cargada al contexto.");
            }

            List validaciones = (List) mapa.get(EvnRespSistema.LISTA_VALIDACIONES);

            if (validaciones == null) {
                if (context.getAttribute(WebKeys.LISTA_VALIDACIONES) == null) {
                    logger.warn("La lista de procesos no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_VALIDACIONES, new ArrayList());
                    logger.info("La lista de procesos ha sido cargada al contexto.");
                }
            } else {
                Iterator itResp = validaciones.iterator();
                List elementos = new ArrayList();

                while (itResp.hasNext()) {
                    Validacion dato = (Validacion) itResp.next();
                    elementos.add(new ElementoLista(dato.getIdValidacion(), dato.getNombre()));
                }

                context.setAttribute(WebKeys.LISTA_VALIDACIONES, elementos);
                logger.info("La lista de validaciones ha sido cargada al contexto.");
            }

            List prohibiciones = (List) mapa.get(EvnRespSistema.LISTA_PROHIBICIONES);

            if (prohibiciones == null) {
                if (context.getAttribute(WebKeys.LISTA_VALIDACIONES) == null) {
                    logger.warn("La lista de Prohibiciones no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_PROHIBICIONES, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de Prohibiciones ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_PROHIBICIONES, Collections.unmodifiableList(prohibiciones));
                logger.info("La lista de Prohibiciones ha sido cargada al contexto.");
            }

            List tiposTarifaConfigurablesPorCirculo = (List) mapa.get(EvnRespSistema.LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO);

            if (tiposTarifaConfigurablesPorCirculo == null) {
                if (context.getAttribute(WebKeys.LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO) == null) {
                    logger.warn("La lista de tipos de tarifa configurables por círculo no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de tipos de tarifa configurables por círculo ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_TIPOS_TARIFA_CONFIGURABLES_POR_CIRCULO, Collections.unmodifiableList(tiposTarifaConfigurablesPorCirculo));
                logger.info("La lista de tipos de tarifa configurables por círculo ha sido cargada al contexto.");
            }

            List permisosCorreccion = (List) mapa.get(EvnRespSistema.LISTA_PERMISOS_CORRECCION);
            if (permisosCorreccion == null) {
                if (context.getAttribute(WebKeys.LISTA_PERMISOS_REVISION_CORRECCION) == null) {
                    logger.warn("La lista de permisos para correccion no pudo ser cargada. Se colocará una lista vacía");
                    context.setAttribute(WebKeys.LISTA_PERMISOS_REVISION_CORRECCION, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de permisos de correccion ha sido cargada al contexto");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_PERMISOS_REVISION_CORRECCION, Collections.unmodifiableList(permisosCorreccion));
                logger.info("La lista de permisos de correccion ha sido cargada al contexto");
                Iterator itPermisos = permisosCorreccion.iterator();
                List permisosElementoLista = new ArrayList();
                while (itPermisos.hasNext()) {
                    PermisoCorreccion permiso = (PermisoCorreccion) itPermisos.next();
                    permisosElementoLista.add(new ElementoLista(permiso.getIdPermiso(), permiso.getDescripcion()));
                }
                context.setAttribute(WebKeys.LISTA_ELEMENTOS_PERMISOS_REVISION_CORRECCION, permisosElementoLista);
            }

            List categoriasNotarias = (List) mapa.get(EvnRespSistema.LISTA_CATEGORIAS_NOTARIAS);

            if (categoriasNotarias == null) {
                if (context.getAttribute(WebKeys.LISTA_CATEGORIAS_NOTARIA) == null) {
                    logger.warn("La lista de categorías de notarías no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_CATEGORIAS_NOTARIA, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de categorías de notarías ha sido cargada al contexto.");
                }
            } else {
                Iterator itCategoriaNotaria = categoriasNotarias.iterator();
                List elementosCategoriaNotaria = new ArrayList();

                while (itCategoriaNotaria.hasNext()) {
                    CategoriaNotaria catNot = (CategoriaNotaria) itCategoriaNotaria.next();
                    elementosCategoriaNotaria.add(new ElementoLista(catNot.getIdCategoria(), catNot.getNombre()));
                }
                context.setAttribute(WebKeys.LISTA_CATEGORIAS_NOTARIA, Collections.unmodifiableList(elementosCategoriaNotaria));
            }

            List relacionBancosCirculo = (List) mapa.get(EvnRespSistema.LISTA_RELACION_BANCOS_CIRCULO);
            if (relacionBancosCirculo == null) {
                if (context.getAttribute(WebKeys.LISTA_RELACION_BANCOS_CIRCULO) == null) {
                    logger.warn("La lista de los bancos no pudo ser cargada. Se colocara una lista vacia");
                    context.setAttribute(WebKeys.LISTA_RELACION_BANCOS_CIRCULO, Collections.unmodifiableList(new ArrayList()));
                    logger.info("La lista de los bancos ha sido cargada al contexto.");
                }
            } else {
                context.setAttribute(WebKeys.LISTA_RELACION_BANCOS_CIRCULO, Collections.unmodifiableList(relacionBancosCirculo));
                logger.info("La lista de relacion bancos circulo ha sido cargada al contexto.");
            }

        } catch (Exception e) {
            logger.error("Falló la consulta sobre HermodProperties.  " + e.getMessage());
            e.printStackTrace();
        }
    }

}

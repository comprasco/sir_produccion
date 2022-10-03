package gov.sir.core.web.acciones.registro;

/**
 * @Autor: Edgar Lora
 * @Mantis: 0013038
 * @Requerimiento: 060_453
 */
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.certificado.EvnCertificado;
import gov.sir.core.eventos.certificado.EvnRespCertificado;
import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.eventos.registro.EvnCalificacion;
import gov.sir.core.eventos.registro.EvnCrearFolio;
import gov.sir.core.eventos.registro.EvnRespCalificacion;
import gov.sir.core.eventos.registro.EvnRespConsultaFolio;
import gov.sir.core.eventos.registro.EvnRespCrearFolio;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.TipoAnotacion;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoFolio;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.constantes.CAnotacionCiudadano;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CDireccion;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.core.negocio.modelo.constantes.CTipoDocumento;
import gov.sir.core.negocio.modelo.constantes.CTipoPredio;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ErrorCreacionFolioException;
import gov.sir.core.web.acciones.excepciones.ErrorCrearAnotacionException;
import gov.sir.core.web.acciones.excepciones.NaturalezaInvalidaException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosCiudadanoCalificacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.helpers.comun.ElementoLista;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * @author ppabon Clase para manejar la capa Web de la opción de crear un nuevo
 * folio
 */
public class AWCrearFolio extends SoporteAccionWeb {

    //ACCION EJECUTADA CUANDO SE HACE CLIC EN EL BOTON CREAR NUEVO FOLIO.
    public static final String INICIO_CREAR_NUEVO_FOLIO = "INICIO_CREAR_NUEVO_FOLIO";

    //OPCIONES PARA CARGAR LAS DIRECCIONES AL INGRESAR LA INFORMACIÓN DEL FOLIO
    public static final String AGREGAR_DIRECCION = "AGREGAR_DIRECCION";
    public static final String ELIMINAR_DIRECCION = "ELIMINAR_DIRECCION";

    //CREAR EL FOLIO CUANDO SE TIENE LA INFORMACIÓN NECESARIA
    public static final String CREAR_FOLIO = "CREAR_FOLIO";

    //OPCIONES PARA LA CARGA DE LOS CIUDADANOS	
    public static final String AGREGAR_1_REGISTRO_TABLA_CIUDADANOS = "AGREGAR_1_REGISTRO_TABLA_CIUDADANOS";
    public static final String REMOVER_1_REGISTRO_TABLA_CIUDADANOS = "REMOVER_1_REGISTRO_TABLA_CIUDADANOS";
    public static final String AGREGAR_VARIOS_CIUDADANOS = "AGREGAR_VARIOS_CIUDADANOS";
    public static final String ELIMINAR_CIUDADANO_ANOTACION = "ELIMINAR_CIUDADANO_ANOTACION";
    public static final String VALIDAR_CIUDADANO = "VALIDAR_CIUDADANO";

    //OPCIONES PARA LA CARGA DE LA NATURALEZA JURÍDICA DE LA ANOTACIÓN
    public static final String CARGAR_DESCRIPCION_NATURALEZA = "CARGAR_DESCRIPCION_NATURALEZA";
    public static final String CARGAR_DESCRIPCION_NATURALEZA_CALIFICACION = "CARGAR_DESCRIPCION_NATURALEZA_CALIFICACION";

    //AGREGAR LA ANOTACIÓN AL FOLIO QUE SE ESTA CREANDO
    public static final String AGREGAR_ANOTACION = "AGREGAR_ANOTACION";

    //CREAR EL NUEVO FOLIO
    public static final String TERMINAR_CREACION_FOLIO = "TERMINAR_CREACION_FOLIO";

    //OTRAS ACCIONES WEB COMPLEMENTARIAS
    public static final String REFRESCAR_ANOTACION = "REFRESCAR_ANOTACION";
    public static final String CONSULTA_FOLIO_COMPLEMENTACION = "CONSULTA_FOLIO_COMPLEMENTACION";

    //CONSTANTES PARA LA ACCIÓN WEB
    public static final String NUM_REGISTROS_TABLA_CIUDADANOS = "NUM_REGISTROS_TABLA_CIUDADANOS";
    public static final int DEFAULT_NUM_CIUDADANOS_TABLA = 2;

    //PERMITE ELIMINAR UN FOLIO QUE SE CREO Y QUE ESTA EN TEMPORAL
    public static final String ELIMINAR_FOLIO_TEMPORAL = "ELIMINAR_FOLIO_TEMPORAL";

    private String accion;

    /**
     *
     */
    public AWCrearFolio() {
        super();
    }

    /* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#perform(javax.servlet.http.HttpServletRequest)
     */
    public Evento perform(HttpServletRequest request) throws AccionWebException {
        accion = request.getParameter(WebKeys.ACCION);
        
        request.getSession().setAttribute(WebKeys.TIENE_ANOTACIONES_TMP, true);
        if ((accion == null) || (accion.length() == 0)) {
            throw new AccionWebException("Debe indicar una accion");
        }

        if (accion.equals(INICIO_CREAR_NUEVO_FOLIO)) {
            return crearNuevoFolio(request);
        } else if (accion.equals(AGREGAR_DIRECCION)) {
            return agregarDireccion(request);
        } else if (accion.equals(ELIMINAR_DIRECCION)) {
            return eliminarDireccion(request);
        } else if (accion.equals(CREAR_FOLIO)) {
            return crearFolio(request);
        } else if (accion.equals(AGREGAR_1_REGISTRO_TABLA_CIUDADANOS)) {
            return aumentarNumeroVariosCiudadanos(request);
        } else if (accion.equals(REMOVER_1_REGISTRO_TABLA_CIUDADANOS)) {
            return disminuirNumeroVariosCiudadanos(request);
        } else if (accion.equals(AGREGAR_VARIOS_CIUDADANOS)) {
            return agregarVariosCiudadanos(request);
        } else if (accion.equals(ELIMINAR_CIUDADANO_ANOTACION)) {
            return eliminarCiudadano(request);
        } else if (accion.equals(VALIDAR_CIUDADANO)) {
            return validarCiudadano(request);
        } else if (accion.equals(CARGAR_DESCRIPCION_NATURALEZA)) {
            return cargarDescripcionNaturaleza(request);
        } else if (accion.equals(AGREGAR_ANOTACION)) {
            return agregarAnotacion(request);
        } else if (accion.equals(TERMINAR_CREACION_FOLIO)) {
            return terminarFolio(request);
        } else if (accion.equals(CONSULTA_FOLIO_COMPLEMENTACION)) {
            return consultaFolioComplementacion(request);
        } else if (accion.equals(REFRESCAR_ANOTACION)) {
            return refrescarAnotacion(request);
        } else if (accion.equals(ELIMINAR_FOLIO_TEMPORAL)) {
            return eliminarFolioTemporal(request);
        } else {
            throw new AccionInvalidaException("La acción " + accion + " no es válida.");
        }
    }

    //SE LLAMA AL BOTON QUE INICIA LA CREACIÓN DEL FOLIO
    /**
     * @param request
     * @return
     */
    private Evento crearNuevoFolio(HttpServletRequest request) throws AccionWebException {
        eliminarInfoBasicaFolio(request);
        eliminarInfoBasicaAnotacion(request);
        eliminarInfoDireccion(request);
        eliminarInfoBasicaVariosCiudadanos(request);
        request.getSession().removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
        request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
        request.getSession().removeAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        request.getSession().removeAttribute(CFolio.NEXT_ORDEN_ANOTACION);
        return null;
    }

    //ACCIONES PARA GUARDAR EN LA SESIÓN LAS DIRECCIONES DEL FOLIO
    /**
     * @param request
     * @return
     */
    private Evento agregarDireccion(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        ErrorCreacionFolioException exception = new ErrorCreacionFolioException();

        String valorEje1 = request.getParameter(CFolio.FOLIO_EJE1);
        String valorValor1 = request.getParameter(CFolio.FOLIO_VALOR1);
        String valorEje2 = request.getParameter(CFolio.FOLIO_EJE2);
        String valorValor2 = request.getParameter(CFolio.FOLIO_VALOR2);
        String complemento = request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR);
        String especificacion = request.getParameter(AWModificarFolio.FOLIO_ESPECIFICACION_DIR);

        if ((valorEje1.length() <= 0) || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe seleccionar el primer eje válido para la dirección");
        } else {
            if (!valorEje1.equals(CDireccion.SIN_DIRECCION)) {
                if (valorValor1.length() <= 0) {
                    exception.addError("Debe ingresar valor válido para el primer eje de la dirección");
                }

                if ((valorEje2.length() <= 0) || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
                    valorEje2 = new String();
                } else {
                    if (valorValor2.length() <= 0) {
                        exception.addError("Debe ingresar valor válido para el segundo eje  de la dirección");
                    }
                }
            }
        }

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        List ejes = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
        Direccion direccion = new Direccion();
        direccion.setEspecificacion((especificacion + " " + complemento).toUpperCase());

        if (ejes != null) {
            Iterator itEje = ejes.iterator();

            while (itEje.hasNext()) {
                ElementoLista elementoEje = (ElementoLista) itEje.next();
                Eje eje;

                if (elementoEje.getId().equals(valorEje1)) {
                    eje = new Eje();
                    eje.setIdEje(elementoEje.getId());
                    eje.setNombre(elementoEje.getValor());
                    direccion.setEje(eje);
                    direccion.setValorEje(valorValor1);
                }

                if (elementoEje.getId().equals(valorEje2)) {
                    eje = new Eje();
                    eje.setIdEje(elementoEje.getId());
                    eje.setNombre(elementoEje.getValor());
                    direccion.setEje1(eje);
                }

                if (valorValor2 != null) {
                    direccion.setValorEje1(valorValor2);
                }
            }
        } else {
            exception.addError("La lista de los ejes no se encontró");
        }

        List diferencias = (List) request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);

        if (diferencias == null) {
            diferencias = new ArrayList();
        }

        Integer idDir = (Integer) request.getSession().getAttribute(WebKeys.ID_DIRECCION);
        if (idDir == null) {
            idDir = new Integer(direcciones == null ? 1 : direcciones.size() + 1);
        }

        direccion.setIdDireccion(idDir.toString());
        Integer nextId = new Integer(idDir.intValue() + 1);
        request.getSession().setAttribute(WebKeys.ID_DIRECCION, nextId);
        diferencias.add(direccion);
        request.getSession().setAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION, diferencias);

        direcciones.add(direccion);

        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
        eliminarInfoDireccion(request);

        return null;
    }

    /**
     * @param request
     * @return
     */
    private Evento eliminarDireccion(HttpServletRequest request) {
        preservarInfoBasicaFolio(request);
        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if (direcciones == null) {
            direcciones = new Vector();
        }

        int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));

        List difDirecciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION);
        if (difDirecciones == null) {
            difDirecciones = new ArrayList();
        }
        Direccion dir = (Direccion) direcciones.get(aplicacionNumero);

        int dif = -1;
        Iterator itDif = difDirecciones.iterator();
        int i = 0;
        while (itDif.hasNext()) {
            Direccion temp = (Direccion) itDif.next();
            if (temp != null && temp.getIdDireccion() != null && dir.getIdDireccion() != null
                    && temp.getIdDireccion().equals(dir.getIdDireccion())) {
                dif = i;
            }
            i++;
        }

        if (dif != -1) {
            difDirecciones.remove(dif);
        } else {
            dir.setToDelete(true);
            difDirecciones.add(dir);
        }
        request.getSession().setAttribute(WebKeys.LISTA_DIFERENCIAS_DIRECCION, difDirecciones);

        direcciones.remove(aplicacionNumero);

        request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);

        return null;
    }

    //GUARDAR LA INFORMACIÓN INGRESADA AL DILIGENCIAR EL FORMULARIO DEL FOLIO
    /**
     * @param request
     * @return
     */
    private Evento crearFolio(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaFolio(request);
        ErrorCreacionFolioException exception = new ErrorCreacionFolioException();
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        if (turno == null) {
            throw new AccionWebException("No se encontro el turno en la sesión");
        }

        //SE RECUPERA LA INFORMACIÓN DEL FORMULARIO
        //String valorRadicacion = request.getParameter(CFolio.FOLIO_NUM_RADICACION);
        String valorDepartamento = request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO);

        if (valorDepartamento == null) {
            valorDepartamento = new String();
        }

        if (valorDepartamento.length() <= 0) {
            exception.addError("Debe seleccionar un departamento.");
        }

        String valorMunicipio = request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC);

        if (valorMunicipio == null) {
            valorMunicipio = new String();
        }

        if (valorMunicipio.length() <= 0) {
            exception.addError("Debe seleccionar un municipio válido.");
        }

        String valorVereda = request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA);

        if (valorVereda == null) {
            valorVereda = new String();
        }

        String valorTipoPredio = request.getParameter(CFolio.FOLIO_TIPO_PREDIO);

        if ((valorTipoPredio == null) || (valorTipoPredio.equals("SIN_SELECCIONAR"))) {
            exception.addError("Debe seleccionar un tipo de predio válido");
        }

        String valorNupre = request.getParameter(CFolio.FOLIO_NUPRE);

        if (valorNupre != null && valorNupre.length() > 14) {
            exception.addError("Debe digitar menos de 15 digitos para el campo NUPRE");
        }

        String valorDeterminaInm = request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE);

        if ((valorDeterminaInm == null) || (valorTipoPredio.equals(WebKeys.SIN_SELECCIONAR))) {
            exception.addError("Debe seleccionar una determinaci&oacute;n de inmuble validad");
        }

        String valorPrivMetros = request.getParameter(CFolio.FOLIO_PRIVMETROS);

        String valorPrivCentimetros = request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS);

        String valorConsMetros = request.getParameter(CFolio.FOLIO_CONSMETROS);

        String valorConsCentimetros = request.getParameter(CFolio.FOLIO_CONSCENTIMETROS);

        String valorCoeficiente = request.getParameter(CFolio.FOLIO_COEFICIENTE);

        String valorHectareas = request.getParameter(CFolio.FOLIO_HECTAREAS);
        String valorMetros = request.getParameter(CFolio.FOLIO_METROS);
        String valorCentimetros = request.getParameter(CFolio.FOLIO_CENTIMETROS);
        String hectareasT = "0";
        String metrosT = "0";
        String centimetrosT = "0";
        int unidadmedidaexception = 3;

        if (valorHectareas == null || valorHectareas.length() <= 0) {
            unidadmedidaexception--;
            valorHectareas = "0";
        } else {
            hectareasT = valorHectareas;
        }

        if (valorMetros == null || valorMetros.length() <= 0) {
            unidadmedidaexception--;
            valorMetros = "0";
        } else {
            metrosT = valorMetros;
        }

        if (valorCentimetros == null || valorCentimetros.length() <= 0) {
            unidadmedidaexception--;
            valorCentimetros = "0";
        } else {
            centimetrosT = valorCentimetros;
        }

        if (unidadmedidaexception != 0) {

            boolean datosPrivBien = true;
            boolean privArea = false;

            String metrosP = "0";
            String centimetrosP = "0";

            if (valorPrivMetros != null && !valorPrivMetros.isEmpty()) {
                privArea = true;
                metrosP = valorPrivMetros;
            } else {
                valorPrivMetros = "0";
            }

            if (valorPrivCentimetros != null && !valorPrivCentimetros.isEmpty()) {
                privArea = true;
                centimetrosP = valorPrivCentimetros;
            } else {
                valorPrivCentimetros = "0";
            }

            if (privArea) {
                unidadmedidaexception = 2;
                String metrosC = "0";
                String centimetrosC = "0";

                if (valorConsMetros == null || valorConsMetros.length() <= 0) {
                    unidadmedidaexception--;
                    valorConsMetros = "0";
                } else {
                    metrosC = valorConsMetros;
                }

                if (valorConsCentimetros == null || valorConsCentimetros.length() <= 0) {
                    unidadmedidaexception--;
                    valorConsCentimetros = "0";
                } else {
                    centimetrosC = valorConsCentimetros;
                }

                if (unidadmedidaexception == 0) {
                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
                    throw exception;
                }

                //Area Privada para Validar
                int pMetros = Integer.parseInt(metrosP);
                int pCentimetros = Integer.parseInt(centimetrosP);
                //Area Construida para Validar
                int cMetros = Integer.parseInt(metrosC);
                int cCentimetros = Integer.parseInt(centimetrosC);
                //Area del Terreno para Validar
                int tHectareas = Integer.parseInt(hectareasT);
                int tMetros = Integer.parseInt(metrosT);
                int tCentimetros = Integer.parseInt(centimetrosT);

                if (pMetros > cMetros) {
                    datosPrivBien = false;
                } else {
                    if (pMetros == cMetros) {
                        if (pCentimetros > cCentimetros) {
                            datosPrivBien = false;
                        }
                    }
                }

                if (!datosPrivBien) {
                    exception.addError("El area privada no puede ser mayor al area construida");
                    throw exception;
                }
                boolean datosConsBien = true;

                if (tHectareas <= 0) {
                    if (cMetros > tMetros) {
                        datosConsBien = false;
                    } else {
                        if (cMetros == tMetros) {
                            if (cCentimetros > tCentimetros) {
                                datosConsBien = false;
                            }
                        }
                    }
                }

                if (!datosConsBien) {
                    exception.addError("El area construida no puede ser mayor al area del terreno");
                    throw exception;
                }

            } else {

                boolean consArea = false;

                String metrosC = "0";
                String centimetrosC = "0";

                if (valorConsMetros != null && !valorConsMetros.isEmpty()) {
                    consArea = true;
                    metrosC = valorConsMetros;
                } else {
                    valorConsMetros = "0";
                }

                if (valorConsCentimetros != null && !valorConsCentimetros.isEmpty()) {
                    consArea = true;
                    centimetrosC = valorConsCentimetros;
                } else {
                    valorConsCentimetros = "0";
                }

                if (consArea) {
                    //Area Construida para Validar
                    int cMetros = Integer.parseInt(metrosC);
                    int cCentimetros = Integer.parseInt(centimetrosC);
                    //Area Construida para Validar
                    int tHectareas = Integer.parseInt(hectareasT);
                    int tMetros = Integer.parseInt(metrosT);
                    int tCentimetros = Integer.parseInt(centimetrosT);

                    boolean datosConsBien = true;
                    if (tHectareas <= 0) {
                        if (cMetros > tMetros) {
                            datosConsBien = false;
                        } else {
                            if (cMetros == tMetros) {
                                if (cCentimetros > tCentimetros) {
                                    datosConsBien = false;
                                }
                            }
                        }
                    }

                    if (!datosConsBien) {
                        exception.addError("El area construida no puede ser mayor al area del terreno");
                        throw exception;
                    }
                }
            }

        } else {
            boolean privArea = false;

            if (valorPrivMetros != null && !valorPrivMetros.isEmpty()) {
                privArea = true;
            }

            if (valorPrivCentimetros != null && !valorPrivCentimetros.isEmpty()) {
                privArea = true;
            }

            if (privArea) {
                boolean consArea = false;

                if (valorConsMetros != null && !valorConsMetros.isEmpty()) {
                    consArea = true;
                }

                if (valorConsCentimetros != null && !valorConsCentimetros.isEmpty()) {
                    consArea = true;
                }

                if (consArea) {
                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area del Terreno");
                } else {
                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
                }
            } else {
                boolean consArea = false;

                if (valorConsMetros != null && !valorConsMetros.isEmpty()) {
                    consArea = true;
                }

                if (valorConsCentimetros != null && !valorConsCentimetros.isEmpty()) {
                    consArea = true;
                }

                if (consArea) {
                    exception.addError("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
                }
            }
        }

        boolean revisarVereda = true;

        if (valorTipoPredio.equals(CTipoPredio.TIPO_URBANO)) {
            revisarVereda = false;
        }

        if ((valorVereda.length() <= 0) && revisarVereda) {
            exception.addError("Debe seleccionar una vereda válida.");
        }

        String valorComplementacion = request.getParameter(CFolio.FOLIO_COMPLEMENTACION);
        if (valorComplementacion != null) {
            valorComplementacion = valorComplementacion.toUpperCase();
        }

        String idComplementacion = request.getParameter(CFolio.FOLIO_ID_COMPLEMENTACION);
        String tipoAccion = request.getParameter(CFolio.SELECCIONAR_FOLIO + AWModificarFolio.FOLIO_COMPLEMENTACION);

        if ((tipoAccion != null) && tipoAccion.equals(CFolio.ASOCIAR)) {
            if (idComplementacion == null) {
                exception.addError("Debe ingresar un identificador para la complementación válido");
            }
        }

        String valorLindero = request.getParameter(CFolio.FOLIO_LINDERO);

        if (valorLindero == null) {
            exception.addError("Debe ingresar informacion del lindero válida");
        }

        String valorCodCatastral = request.getParameter(CFolio.FOLIO_COD_CATASTRAL);

        if (valorCodCatastral == null) {
            exception.addError("Debe ingresar un codigo catastral válido");
        }

        Folio folio = new Folio();

        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        folio.setFechaApertura(new Date(System.currentTimeMillis()));

        folio.setCodCatastral(valorCodCatastral);
        folio.setNupre(valorNupre);
        folio.setDeterminaInm(valorDeterminaInm);
        folio.setPrivMetros(valorPrivMetros);
        folio.setPrivCentimetros(valorPrivCentimetros);
        folio.setConsMetros(valorConsMetros);
        folio.setConsCentimetros(valorConsCentimetros);
        folio.setCoeficiente(valorCoeficiente);
        folio.setHectareas(valorHectareas);
        folio.setMetros(valorMetros);
        folio.setCentimetros(valorCentimetros);

        if (circulo == null) {
            circulo = new Circulo();
        }

        folio.setLindero(valorLindero);

        //SE LE INGRESA LA COMPLEMENTACIÓN AL FOLIO
        Complementacion comp = new Complementacion();

        String tipoComp = request.getParameter(CFolio.SELECCIONAR_FOLIO + AWModificarFolio.FOLIO_COMPLEMENTACION);

        if (tipoComp.equals(CFolio.NUEVA) || tipoComp.equals(CFolio.COPIAR)) {
            comp.setComplementacion(valorComplementacion);
        }

        if (tipoComp.equals(CFolio.ASOCIAR)) {
            comp.setIdComplementacion(idComplementacion);
            comp.setComplementacion(valorComplementacion);
        }

        folio.setComplementacion(comp);

        //SE INGRESA EL TIPO DE PREDIO
        List tiposPredio = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_TIPOS_PREDIO);

        if (tiposPredio != null) {
            Iterator itPredios = tiposPredio.iterator();

            while (itPredios.hasNext()) {
                ElementoLista elementoTipoPredio = (ElementoLista) itPredios.next();

                if (elementoTipoPredio.getId().equals(valorTipoPredio)) {
                    TipoPredio tipoPredio = new TipoPredio();
                    tipoPredio.setIdPredio(valorTipoPredio);
                    tipoPredio.setNombre(elementoTipoPredio.getValor());
                    folio.setTipoPredio(tipoPredio);
                }
            }
        } else {
            exception.addError("La lista de los tipos de predio no se encontro");
        }

        ZonaRegistral zona = new ZonaRegistral();
        zona.setCirculo(circulo);

        Departamento departamento = new Departamento();
        departamento.setIdDepartamento(valorDepartamento);

        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(valorMunicipio);

        municipio.setDepartamento(departamento);

        Vereda vereda = new Vereda();
        vereda.setIdDepartamento(valorDepartamento);
        vereda.setIdMunicipio(valorMunicipio);
        vereda.setIdVereda(valorVereda);
        vereda.setMunicipio(municipio);
        zona.setVereda(vereda);

        folio.setZonaRegistral(zona);

        Documento documento = new Documento();
        TipoDocumento tipoDoc = new TipoDocumento();

        tipoDoc.setIdTipoDocumento(CTipoDocumento.ID_TIPO_CERTIFICADO);
        documento.setFecha(new Date());
        documento.setTipoDocumento(tipoDoc);

        folio.setDocumento(documento);
        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        Solicitud solicitud = turno.getSolicitud();

        if (solicitud instanceof SolicitudRegistro) {
            SolicitudRegistro registro = (SolicitudRegistro) solicitud;
            folio.setDocumento(registro.getDocumento());
        }

        List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        if ((direcciones != null) && (direcciones.size() > 0)) {
            Iterator it = direcciones.iterator();

            while (it.hasNext()) {
                Direccion direccion = (Direccion) it.next();
                direccion.setIdDireccion(null);
                folio.addDireccione(direccion);
            }
        }

        request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, folio);
        return null;
    }

    //OPCIONES PARA INGRESAR LOS CIUDADANOS
    /**
     *
     * @param request
     * @return
     */
    private Evento aumentarNumeroVariosCiudadanos(HttpServletRequest request) {

        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        numCiud++;
        HttpSession session = request.getSession();
        Integer num = new Integer(numCiud);
        session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        return null;
    }

    /**
     * @param request
     * @return
     */
    private int numeroRegistrosTablaAgregarCiudadanos(HttpServletRequest request) {

        Integer num = (Integer) request.getSession().getAttribute(NUM_REGISTROS_TABLA_CIUDADANOS);
        int numCiud;

        if (num == null) {
            numCiud = DEFAULT_NUM_CIUDADANOS_TABLA;
        } else {
            numCiud = num.intValue();
        }

        return numCiud;
    }

    /**
     *
     * @param request
     * @return
     */
    private Evento disminuirNumeroVariosCiudadanos(HttpServletRequest request) {

        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        if (numCiud > 1) {
            numCiud--;
        }

        HttpSession session = request.getSession();
        Integer num = new Integer(numCiud);
        session.setAttribute(NUM_REGISTROS_TABLA_CIUDADANOS, num);
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        return null;
    }

    /**
     * @param request
     * @param exception
     * @return
     * @throws AccionWebException
     *
     *
     */
    private Evento agregarVariosCiudadanos(HttpServletRequest request) throws AccionWebException {
        ValidacionParametrosException exception = new ValidacionParametrosException();
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request); //varios
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }

        List ciudadanosFinales = new Vector(ciudadanos);

        int numCiudadanosTabla = this.numeroRegistrosTablaAgregarCiudadanos(request);
        for (int i = 0; i < numCiudadanosTabla; i++) {

            if (agregoPersona(request, i)) {
                int b = i + 1;
                boolean datosBien = true;
                String tipoPersona = request.getParameter(CFolio.ANOTACION_TIPO_PERSONA + i);

                if ((tipoPersona == null) || tipoPersona.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de persona para el ciudadano " + b + " en la anotacion");
                    datosBien = false;
                }
                String tipoId = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA + i);

                if ((tipoId == null) || tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe escojer un tipo de identificación para la persona " + b + " en la anotacion");
                    datosBien = false;
                }

                String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + i);
                String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
                String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
                String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA + i);

                String sexo = request.getParameter(CFolio.ANOTACION_SEXO_PERSONA + i);

                if (!tipoPersona.equals(CCiudadano.TIPO_PERSONA_JURIDICA)) {
                    if ((sexo == null) || sexo.equals(WebKeys.SIN_SELECCIONAR)) {
                        exception.addError("Debe escojer un tipo de sexo para la persona en la anotacion");
                        datosBien = false;
                    }
                }

                if (tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA)) {

                    if (tipoPersona.equals(CCiudadano.TIPO_PERSONA_NATURAL)) {
                        if (apellido1 == null || apellido1.trim().equals("")) {
                            exception.addError("Primer apellido invalido para la persona " + b);
                            datosBien = false;
                        }
                    } else {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razon social invalida para el ciudadano " + b);
                            datosBien = false;
                        }
                    }

                } else {
                    if ((numId == null) || numId.trim().equals("")) {
                        exception.addError("Documento Inválido para la persona " + b);
                        datosBien = false;
                    }
                    double valorId = 0.0d;
                    if (numId == null) {
                        exception.addError("El número de identificación de la persona " + b + " en la anotación es inválido");
                        datosBien = false;
                    } else {
                        /*
                                            * @author : CTORRES
                                            * @change : Se implemento la validación para solo permitir caracteres alfanuméricos en 
                                            *           numId cuando el tipo de identificación es PASAPORTE.
                                            * Caso Mantis : 11056
                                            * No Requerimiento: 073_151
                         */
                        if (tipoId.contains("PS")) {
                            String regexSL = "^[a-zA-Z]+$";
                            String regexSN = "^[0-9]+$";
                            String regexLN = "^[a-zA0-Z9]+$";
                            java.util.regex.Pattern patternSL = java.util.regex.Pattern.compile(regexSL);
                            java.util.regex.Pattern patternSN = java.util.regex.Pattern.compile(regexSN);
                            java.util.regex.Pattern patternLN = java.util.regex.Pattern.compile(regexLN);
                            boolean esC = false;
                            if (patternSL.matcher(numId).matches()) {
                                esC = true;
                            } else if (patternSN.matcher(numId).matches()) {
                                esC = true;
                            } else if (patternLN.matcher(numId).matches()) {
                                esC = true;
                            } else {
                                datosBien = false;
                                exception.addError("El número de identificación de la persona es inválido. Debe ser alfanumérico");
                            }
                        } else {
                            if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                                try {
                                    valorId = Double.parseDouble(numId);
                                    if (valorId <= 0) {
                                        exception.addError("El valor del documento de la persona " + b + " no puede ser negativo o cero");
                                        datosBien = false;
                                    }
                                } catch (NumberFormatException e) {
                                    exception.addError("El número de identificación de la persona " + b + " en la anotación es inválido");
                                    datosBien = false;
                                }
                            }
                        }
                    }
                    if (tipoId.equals(CCiudadano.TIPO_DOC_ID_NIT)) {
                        if (nombres == null || nombres.trim().equals("")) {
                            exception.addError("Razón social inválida del campo " + b);
                            datosBien = false;
                        }
                    } else {
                        if ((nombres == null) || nombres.equals("")) {
                            exception.addError("Debe ingresar el nombre de la persona " + b + " en la anotación");
                            datosBien = false;
                        }

                        if ((apellido1 == null) || apellido1.equals("")) {
                            exception.addError("Debe ingresar el primer apellido de la persona " + b + " en la anotación");
                            datosBien = false;
                        }
                    }
                }

                String tipoIntervencion = request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);

                if ((tipoIntervencion == null) || tipoIntervencion.equals(WebKeys.SIN_SELECCIONAR)) {
                    exception.addError("Debe ingresar un tipo de intervención para la persona " + b + " en la anotación");
                    datosBien = false;
                }

                AnotacionCiudadano c;
                Iterator ic = ciudadanosFinales.iterator();
                while (ic.hasNext()) {
                    c = (AnotacionCiudadano) ic.next();
                    if ((tipoIntervencion.equals(c.getRolPersona())) && (numId.equals(c.getCiudadano().getDocumento())) && (tipoId.equals(c.getCiudadano().getTipoDoc())) && (!tipoId.equals(CCiudadano.TIPO_DOC_ID_SECUENCIA))) {
                        exception.addError("La persona no puede tener dos veces el mismo rol");
                        datosBien = false;
                    }
                }

                String participacion = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);
                if ((participacion != null) && !participacion.equals("")) {
                    if (participacion.length() > 50) {
                        exception.addError("El campo participación de una persona no puede tener mas de 50 caracteres");
                        datosBien = false;
                    }
                }

                if (datosBien) {
                    AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
                    Ciudadano ciudadano = new Ciudadano();

                    if (apellido1 != null) {
                        ciudadano.setApellido1(apellido1);
                    }

                    if (apellido2 != null) {
                        ciudadano.setApellido2(apellido2);
                    }

                    ciudadano.setNombre(nombres);

                    if (numId != null) {
                        ciudadano.setDocumento(numId);
                    }

                    //Se setea el circulo del ciudadano
                    Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                    ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

                    ciudadano.setTipoDoc(tipoId);
                    ciudadano.setTipoPersona(tipoPersona);
                    ciudadano.setSexo(sexo);
                    anotacionCiudadano.setCiudadano(ciudadano);
                    anotacionCiudadano.setRolPersona(tipoIntervencion);
                    anotacionCiudadano.setParticipacion(participacion);

                    String marcaPropietario = request.getParameter(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i);

                    if (marcaPropietario == null || marcaPropietario.equals("")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.NO_PROPIETARIO);
                    } else if (marcaPropietario.equals("X")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DERECHO_REAL_DOMINIO);
                    } else if (marcaPropietario.equals("I")) {
                        anotacionCiudadano.setMarcaPropietario(CAnotacionCiudadano.TITULAR_DOMINIO_INCOMPLETO);
                    }

                    ciudadanosFinales.add(anotacionCiudadano);
                }

            }
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        /*eliminarInfoBasicaVariosCiudadanos(request);

		List listaCompletaCiudadanos = new Vector();

		listaCompletaCiudadanos.addAll(ciudadanos);

		request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, listaCompletaCiudadanos);

		return null;*/
        eliminarInfoBasicaVariosCiudadanos(request);

        List listaCompletaCiudadanos = new Vector();

        listaCompletaCiudadanos.addAll(ciudadanosFinales);

        //request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, listaCompletaCiudadanos);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.AGREGAR_CIUDADANO_ANOTACION);
        evento.setAnotacionCiudadanos(ciudadanosFinales);
        evento.setListaCompletaCiudadanos(listaCompletaCiudadanos);
        evento.setTurno(turno);
        return evento;
    }

    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento eliminarCiudadano(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);

        if (ciudadanos == null) {
            ciudadanos = new Vector();
        }
        /**
         * Organizar los ciudadanos. Se debe tener en cuenta el orden ya que el
         * helper (VariosCiuddanosAnotacionHelper)los muestra de esta manera y
         * se necesita manejar el mismo contador para eliminar el que realmente
         * se esta indicando
         */

        Iterator itCiudadanos = ciudadanos.iterator();
        List losA = new ArrayList();
        List losDe = new ArrayList();
        while (itCiudadanos.hasNext()) {
            AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
            String rol = anotacionCiudadano.getRolPersona();
            if (rol.equals("DE")) {
                losDe.add(anotacionCiudadano);
            } else if (rol.equals("A")) {
                losA.add(anotacionCiudadano);
            }
        }
        losDe.addAll(losA);
        ciudadanos.clear();
        ciudadanos = losDe;
        try {
            int aplicacionNumero = Integer.parseInt(request.getParameter(AWCalificacion.POSICION));
            ciudadanos.remove(aplicacionNumero);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (ciudadanos.size() == 0) {
                throw new ParametroInvalidoException("La lista es vacía");
            }
            throw new ParametroInvalidoException("El índice del documento a eliminar está fuera del rango");
        }

        request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
        return null;
    }

    /**
     * Valida un ciudadano en anotación, recibe el request, crea el objeto
     * ciudadano y lanza un evento de negocio
     *
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento validarCiudadano(HttpServletRequest request) throws AccionWebException {
        //eliminarInfoBasicaAnotacion(request);

        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioArq = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        ValidacionParametrosCiudadanoCalificacionException exception = new ValidacionParametrosCiudadanoCalificacionException();
        String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        preservarInfoBasicaVariosCiudadanos(request);
        preservarInfoBasicaAnotacion(request);

        String tipoDoc = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA + ver);

        if ((tipoDoc == null) || tipoDoc.equals(WebKeys.SIN_SELECCIONAR)) {
            exception.addError("Debe escoger un tipo de identificación para la persona en la anotacion");
        }

        String numDoc = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + ver);
        if ((numDoc == null) || numDoc.equals("")) {
            exception.addError("Debe digitar un número de identificación");
        }

        if (!exception.getErrores().isEmpty()) {
            preservarInfo(request);
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        request.getSession().setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA + ver, numDoc);
        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        Ciudadano ciud = new Ciudadano();
        ciud.setDocumento(numDoc);
        ciud.setTipoDoc(tipoDoc);

        //Se setea el circulo del ciudadano
        Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
        ciud.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);

        EvnCiudadano evnt = new EvnCiudadano(usuarioArq, EvnCiudadano.CONSULTAR_CIUDADANO_ANOTACION, ciud);
        evnt.setRegistro(true);
        evnt.setTurno(turno);
        return evnt;
    }

    //OPCIONES PARA LA CARGA DE LA NATURALEZA JURÍDICA DE LA ANOTACIÓN
    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento cargarDescripcionNaturaleza(HttpServletRequest request) throws AccionWebException {
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);

        ValidacionParametrosException exception = new ValidacionParametrosException();

        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, idNaturaleza);
        if ((idNaturaleza == null) || (idNaturaleza.trim().equals(""))) {
            exception.addError("Debe insertar un código de naturaleza jurídica");
        }

        if (exception.getErrores().size() > 0) {
            preservarInfoBasicaCiudadano(request);
            throw exception;
        }

        //Preservar información:
        for (java.util.Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
            String key = (String) enumeration.nextElement();
            request.getSession().setAttribute(key, request.getParameter(key));
        }

        List grupoNaturalezas = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS);

        GrupoNaturalezaJuridica grupo = new GrupoNaturalezaJuridica();
        NaturalezaJuridica nat;
        boolean found = false;
        String descripcion = "";
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         */
        long version = 0;
        for (Iterator it = grupoNaturalezas.iterator(); it.hasNext() && !found;) {
            grupo = (GrupoNaturalezaJuridica) it.next();
            for (Iterator it2 = grupo.getNaturalezaJuridicas().iterator(); it2.hasNext() && !found;) {
                nat = (NaturalezaJuridica) it2.next();
                if (nat.getIdNaturalezaJuridica().equals(idNaturaleza)) {
                    descripcion = nat.getNombre();
                    /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiación_de_Naturaleza_Jurídica
                     */
                    version = nat.getVersion();
                    if (!nat.isHabilitadoCalificacion()) {
                        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
                        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
                        /**
                         * @author : Carlos Mario Torres Urina
                         * @casoMantis : 0012705.
                         * @actaReq : Acta - Requerimiento No
                         * 056_453_Modificiación_de_Naturaleza_Jurídica
                         */

                        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
                        throw new NaturalezaInvalidaException("La naturaleza jurídica (" + idNaturaleza + " - " + descripcion + ") no es válida para la calificación.");
                    } else {
                        found = true;
                    }
                }
            }
        }

        if (!found) {
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             */
            request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER);
            throw new NaturalezaInvalidaException("La naturaleza jurídica (" + idNaturaleza + ") no existe.");
        } else {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, descripcion);
            /**
             * @author : Carlos Mario Torres Urina
             * @casoMantis : 0012705.
             * @actaReq : Acta - Requerimiento No
             * 056_453_Modificiación_de_Naturaleza_Jurídica
             */
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_VER, version);
        }

        request.getSession().setAttribute(AWCalificacion.HAY_REFRESCO, new Boolean(true));

        return null;

    }

    //AGREGAR UNA NUEVA ANOTACIÓN AL FOLIO
    /**
     * @param request
     * @return
     * @throws AccionWebException
     */
    private Evento agregarAnotacion(HttpServletRequest request) throws AccionWebException {

        preservarInfoBasicaAnotacion(request);

        List ciudadanos = (List) request.getSession().getAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
        if (ciudadanos != null) {
            request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, ciudadanos);
        }
        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);
        if (anotaciones == null) {
            anotaciones = new Vector();
        }

        ValidacionParametrosException exception = exception = new ValidacionParametrosException();

        /**
         * @Autor: Edgar Lora
         * @Mantis: 0013038
         * @Requerimiento: 060_453
         */
        String idNaturaleza = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        String modalidad = request.getParameter(CFolio.ANOTACION_MODALIDAD);

        ValidacionesSIR val = new ValidacionesSIR();
        Folio f = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        String valorLindero = f.getLindero();
        if (valorLindero != null && valorLindero.length() < 100) {
            try {
                if (val.validarNaturalezaJuridica(idNaturaleza, CNaturalezaJuridica.NATURALEZA_PARA_VALIDAR_LINDEROS)) {
                    exception.addError("Debe ingresar al menos 100 caracteres en el campo de linderos");
                }
            } catch (Exception e) {
                exception.addError("Error validando Naturaleza Juridica: " + e.getMessage());
            }
        }
        valorLindero = "Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012. " + valorLindero;

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

        String numEspecificacion = request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        if (numEspecificacion != null) {
            numEspecificacion = numEspecificacion.replaceAll(",", "");
        }
        double valorEspecificacion = 0.0d;

        if ((numEspecificacion != null) && !numEspecificacion.equals("")) {
            try {
                valorEspecificacion = Double.parseDouble(numEspecificacion);
                if (valorEspecificacion < 0) {
                    exception.addError("El valor de la especificación no puede ser negativo o cero");
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor de la especificación en la anotación es inválido");
            }
        }

        if (ciudadanos == null || ciudadanos.isEmpty()) {
            exception.addError("Debe ingresar por lo menos un ciudadano");
        }

        String nomNatJuridica = request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change :
         * @throws :
         */
        NaturalezaJuridica natJuridica = null;
        if (idNaturaleza == null || idNaturaleza.length() <= 0) {
            exception.addError("Debe seleccionar una naturaleza juridica para la especificación en la anotación");
        }
        if (idNaturaleza.equals("0125") || idNaturaleza.equals("0126")) {
            if (modalidad == null || modalidad.equals(WebKeys.SIN_SELECCIONAR)) {
                exception.addError("Debe seleccionar una modalidad para este código de naturaleza jurídica");
            }
        }
        boolean esta = false;
        List grupoNaturalezas = (List) request.getSession().getAttribute("listanat");

        Iterator ig = grupoNaturalezas.iterator();
        while (ig.hasNext()) {
            GrupoNaturalezaJuridica group = (GrupoNaturalezaJuridica) ig.next();
            List natus = group.getNaturalezaJuridicas();
            Iterator id = natus.iterator();
            while (id.hasNext()) {
                NaturalezaJuridica nat = (NaturalezaJuridica) id.next();

                if (nat.getIdNaturalezaJuridica().equals(idNaturaleza)) {
                    /**
                     * @author : Carlos Mario Torres Urina
                     * @casoMantis : 0012705.
                     * @actaReq : Acta - Requerimiento No
                     * 056_453_Modificiación_de_Naturaleza_Jurídica
                     * @change : Retorna Anotacion temporar.
                     */
                    natJuridica = nat;
                    esta = true;
                }
            }
        }
        if (!esta) {
            exception.addError("Debe colocar un codigo de naturaleza juridica válido");
        }

        String comentario = request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);

        String idMatricula = (String) request.getSession().getAttribute(CFolio.ID_MATRICULA);
        String fechaAnota = (String) request.getSession().getAttribute(CFolio.ANOTACION_FECHA_RADICACION);

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        /**
         * @Autor: Edgar Lora
         * @Mantis: 0013038
         * @Requerimiento: 060_453
         */
        f.setLindero(valorLindero);
        request.getSession().setAttribute(WebKeys.FOLIO_EDITADO, f);

        Anotacion anotacion = new Anotacion();
        int pos = 1;

        if (anotaciones != null) {
            pos += anotaciones.size();
        }

        anotacion.setOrden(String.valueOf(pos));
        anotacion.setNumRadicacion(turno.getIdWorkflow());
        anotacion.setIdWorkflow(turno.getIdWorkflow());
        anotacion.setFechaRadicacion(turno.getFechaInicio());
        anotacion.setModalidad(modalidad);
        anotacion.setDocumento(((SolicitudRegistro) turno.getSolicitud()).getDocumento());
        NaturalezaJuridica naturalezaJuridica = new NaturalezaJuridica();
        if ((numEspecificacion != null) && !numEspecificacion.equals("")) {
            try {
                valorEspecificacion = Double.parseDouble(numEspecificacion);
                if (valorEspecificacion <= 0) {
                    exception.addError("El valor de la especificación no puede ser negativo o cero");
                }
            } catch (NumberFormatException e) {
                exception.addError("El valor de la especificación en la anotación es inválido");
            }
            valorEspecificacion = Double.parseDouble(numEspecificacion);
        }

        anotacion.setValor(valorEspecificacion);

        naturalezaJuridica.setIdNaturalezaJuridica(idNaturaleza);
        /**
         * @author : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq : Acta - Requerimiento No
         * 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change :
         */
        naturalezaJuridica.setVersion(natJuridica.getVersion());
        if (nomNatJuridica != null) {
            naturalezaJuridica.setNombre(nomNatJuridica);
        }

        anotacion.setNaturalezaJuridica(naturalezaJuridica);
        anotacion.setEspecificacion(naturalezaJuridica.getNombre());
        anotacion.setComentario(comentario);

        if (ciudadanos != null) {
            Iterator itPersonas = ciudadanos.iterator();
            while (itPersonas.hasNext()) {
                AnotacionCiudadano ciudadano = (AnotacionCiudadano) itPersonas.next();
                if (!ciudadano.isToDelete()) {
                    ciudadano.setAnotacion(anotacion);
                    anotacion.addAnotacionesCiudadano(ciudadano);
                }
            }
        }

        TipoAnotacion tipoAn = new TipoAnotacion();
        tipoAn.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);
        anotacion.setTipoAnotacion(tipoAn);
        if (turno.getIdProceso() == 6 && turno.getIdFase().equals("CAL_CALIFICACION")) {
            anotacion.setTemporal(true);
        }

        anotacion.setOrden("" + pos);

        String vista = (String) request.getSession().getAttribute("ULITIMA_VISTA_TEMPORAL");

        if (vista != null) {
            request.getSession().setAttribute(SMARTKeys.VISTA_ACTUAL, vista);
        }

        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        EvnCrearFolio evn = new EvnCrearFolio(usuario, EvnCrearFolio.VALIDAR_ANOTACION_CIUDADANO);
        evn.setGenerarNextOrden(true);
        evn.setAnotacionesAgregadas(anotaciones);
        evn.setAnotacion(anotacion);
        evn.setTurno(turno);

        return evn;
    }

    //CREAR EL NUEVO FOLIO
    /**
     * @param request
     * @return
     */
    private Evento terminarFolio(HttpServletRequest request) throws AccionWebException {
        ErrorCrearAnotacionException exception = new ErrorCrearAnotacionException();

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO_EDITADO);
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        Fase fase = (Fase) request.getSession().getAttribute(WebKeys.FASE);

        List anotaciones = (List) request.getSession().getAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO);

        if ((anotaciones == null) || (anotaciones.size() == 0)) {
            exception.addError("No se puede crear un folio sin anotaciones");
            throw exception;
        }

        int tam = anotaciones.size();

        for (int i = 0; i < tam; i++) {
            Anotacion anota = (Anotacion) anotaciones.get(i);
            TipoAnotacion tipoAnota = new TipoAnotacion();
            tipoAnota.setIdTipoAnotacion(CTipoAnotacion.ESTANDAR);
            anota.setTipoAnotacion(tipoAnota);
        }

        removerAnotacionesFromFolio(folio);
        if (anotaciones != null) {
            Iterator itAnotaciones = anotaciones.iterator();

            while (itAnotaciones.hasNext()) {
                folio.addAnotacione((Anotacion) itAnotaciones.next());
            }
        }

        removerTurnosFolios(folio);
        TurnoFolio tFolio = new TurnoFolio();
        tFolio.setTurno(turno);
        folio.addTurnosFolio(tFolio);
        folio.setEstado(null);

        return new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.CREACION_FOLIO, turno, fase, folio, usuario);
    }

    /**
     * Remueve turnosFolio del folio.
     *
     * @param folio
     */
    private void removerTurnosFolios(Folio folio) {
        TurnoFolio turnoFolio;

        List turnosFolio = null;

        if (folio != null) {
            turnosFolio = folio.getTurnosFolios();
        }

        if (turnosFolio != null) {
            int max = turnosFolio.size();

            for (int i = 0; i < max; i++) {
                turnoFolio = (TurnoFolio) turnosFolio.get(0);
                folio.removeTurnosFolio(turnoFolio);
            }
        }
    }

    /**
     * Remueve las anotaciones del folio.
     *
     * @param folio
     */
    private void removerAnotacionesFromFolio(Folio folio) {
        Anotacion anotacion;

        List anotaciones = null;

        if (folio != null) {
            anotaciones = folio.getAnotaciones();
        }

        if (anotaciones != null) {
            int max = anotaciones.size();

            for (int i = 0; i < max; i++) {
                anotacion = (Anotacion) anotaciones.get(0);
                folio.removeAnotacione(anotacion);
            }
        }
    }

    //CONSULTAR LA COMPLEMENTACIÓN
    /**
     * @param request
     * @return
     */
    private Evento consultaFolioComplementacion(HttpServletRequest request) throws AccionWebException {
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        preservarInfoBasicaFolio(request);

        String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
        request.getSession().removeAttribute(WebKeys.MENSAJE);

        if (idMatricula == null) {
            idMatricula = (String) request.getSession().getAttribute(CFolio.ID_MATRICULA);
        }

        String tipoComp = request.getParameter(CFolio.SELECCIONAR_FOLIO + AWModificarFolio.FOLIO_COMPLEMENTACION);
        request.getSession().setAttribute(CFolio.SELECCIONAR_FOLIO + AWModificarFolio.FOLIO_COMPLEMENTACION, tipoComp);

        Folio folio = new Folio();
        folio.setIdMatricula(idMatricula);

        return new EvnCertificado(usuarioAuriga, usuarioSIR, EvnCertificado.CONSULTA_FOLIO_COMPLEMENTACION, folio);
    }

    //BORRAR UN FOLIO TEMPORAL
    /**
     * @param request
     * @return
     */
    private Evento eliminarFolioTemporal(HttpServletRequest request) throws AccionWebException {
        ErrorCrearAnotacionException exception = new ErrorCrearAnotacionException();

        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

        String matricula = request.getParameter(CFolio.ID_MATRICULA);
        String zonaRegistral = request.getParameter(CFolio.ID_ZONA_REGISTRAL);
        Folio folio = new Folio();
        folio.setIdMatricula(matricula);

        return new EvnCrearFolio(usuarioAuriga, EvnCrearFolio.ELIMINAR_FOLIO_TEMPORAL, turno, folio, usuarioNeg);
    }

    //REFRESCAR LA ANOTACIÓN
    /**
     * @param request
     * @return
     */
    private Evento refrescarAnotacion(HttpServletRequest request) {
        request.getSession().setAttribute(CAnotacionCiudadano.SECUENCIA, request.getParameter(CAnotacionCiudadano.SECUENCIA));
        request.getSession().setAttribute("CAMBIO", "SI");
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaVariosCiudadanos(request);
        return null;
    }

    //FUNCIONES PARA MANEJO DE LOS CIUDADANOS
    /**
     * @param request
     * @param i
     * @return
     */
    private boolean agregoPersona(HttpServletRequest request, int i) {
        int cantDatosValidos = 0;

        String numId = request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA + i);
        if (numId != null && numId.trim().length() > 0) {
            cantDatosValidos++;
        }
        String apellido1 = request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
        if (apellido1 != null && apellido1.trim().length() > 0) {
            cantDatosValidos++;
        }

        //Comentado dado que no es obligatorio el segundo apellido para ningun tipo de
        //Docuemento de identidad.
        /*String apellido2 = request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
		if (apellido2 != null) {
			return true;
		}*/
        String tipoID = request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA);
        String nombres = request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA + i);

        if (nombres != null && nombres.trim().length() > 0) {
            cantDatosValidos++;
        }

        boolean verificarNombre = false;
        if (tipoID != null && (tipoID.equals(CCiudadano.TIPO_DOC_ID_CEDULA)
                || tipoID.equals(CCiudadano.TIPO_DOC_ID_CEDULA_EXTRANJERIA)
                || tipoID.equals(CCiudadano.TIPO_DOC_ID_TARJETA))) {
            verificarNombre = true;
        }

        if (verificarNombre && nombres != null && nombres.trim().length() > 0) {
            cantDatosValidos++;
        }

        String valPorcentaje = request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);
        if (valPorcentaje != null && valPorcentaje.trim().length() > 0) {
            cantDatosValidos++;
        }

        if (cantDatosValidos > 0) {
            return true;
        }

        return false;
    }

    /**
     * @param request
     */
    private void preservarInfoBasicaCiudadano(HttpServletRequest request) {
        if (request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA, request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_PERSONA, request.getParameter(CFolio.ANOTACION_TIPO_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_COD_VERIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_COD_VERIFICACION, request.getParameter(CFolio.ANOTACION_COD_VERIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_SEXO_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_SEXO_PERSONA, request.getParameter(CFolio.ANOTACION_SEXO_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA, request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA, request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA, request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA, request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION, request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION));
        }

        if (request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION, request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION));
        }
    }

    /**
     * @param request
     */
    private void preservarInfoBasicaVariosCiudadanos(HttpServletRequest request) {
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);
        String key = null;
        Object parametro = null;

        for (int i = 0; i < numCiud; i++) {
            key = CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_TIPO_ID_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_TIPO_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_SEXO_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_COD_VERIFICACION + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NUM_ID_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_1_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_APELLIDO_2_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }

            key = CFolio.ANOTACION_NOMBRES_PERSONA + i;
            parametro = request.getParameter(key);

            if (parametro != null) {
                request.getSession().setAttribute(key, parametro);
            }
        }
    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaVariosCiudadanos(HttpServletRequest request) {
        int numCiud = this.numeroRegistrosTablaAgregarCiudadanos(request);

        for (int i = 0; i < numCiud; i++) {
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_COD_VERIFICACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NUM_ID_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION + i);
            request.getSession().removeAttribute(CFolio.ANOTACION_ES_PROPIETARIO_ASOCIACION + i);
        }
    }

    //FUNCIONES PRESERVAR LA INFORMACIÓN DEL FOLIO EN LA SESIÓN.	
    /**
     * @param request
     */
    private void preservarInfoBasicaFolio(HttpServletRequest request) {
        //Se salva temporalmente la informacion sobre el folio. Esto no hace persistente el folio, ni cumple con el requerimiento de grabar de manera temporal
        if (request.getParameter(CFolio.FOLIO_NUM_RADICACION) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_NUM_RADICACION, request.getParameter(CFolio.FOLIO_NUM_RADICACION));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO, request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO, request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC, request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC, request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA, request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA, request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_ESTADO_FOLIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_ESTADO_FOLIO, request.getParameter(CFolio.FOLIO_ESTADO_FOLIO));
        }

        if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_TIPO_PREDIO, request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
        }

        if (request.getParameter(CFolio.FOLIO_NUPRE) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_NUPRE, request.getParameter(CFolio.FOLIO_NUPRE));
        }

        if (request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_DETERMINACION_INMUEBLE, request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE));
        }

        if (request.getParameter(CFolio.FOLIO_AVALUO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_AVALUO, request.getParameter(CFolio.FOLIO_AVALUO));
        }

        if (request.getParameter(CFolio.FOLIO_FECHA_AVALUO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_FECHA_AVALUO, request.getParameter(CFolio.FOLIO_FECHA_AVALUO));
        }

        if (request.getParameter(CFolio.FOLIO_DESTINACION_ECONOMICA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_DESTINACION_ECONOMICA, request.getParameter(CFolio.FOLIO_DESTINACION_ECONOMICA));
        }

        if (request.getParameter(CFolio.FOLIO_PRIVMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_PRIVMETROS, request.getParameter(CFolio.FOLIO_PRIVMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_PRIVCENTIMETROS, request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_CONSMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_CONSMETROS, request.getParameter(CFolio.FOLIO_CONSMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_CONSCENTIMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_CONSCENTIMETROS, request.getParameter(CFolio.FOLIO_CONSCENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_COEFICIENTE) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COEFICIENTE, request.getParameter(CFolio.FOLIO_COEFICIENTE));
        }

        if (request.getParameter(CFolio.FOLIO_HECTAREAS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_HECTAREAS, request.getParameter(CFolio.FOLIO_HECTAREAS));
        }

        if (request.getParameter(CFolio.FOLIO_METROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_METROS, request.getParameter(CFolio.FOLIO_METROS));
        }

        if (request.getParameter(CFolio.FOLIO_CENTIMETROS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_CENTIMETROS, request.getParameter(CFolio.FOLIO_CENTIMETROS));
        }

        if (request.getParameter(CFolio.FOLIO_LINDEROS_DEFINIDOS) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LINDEROS_DEFINIDOS, request.getParameter(CFolio.FOLIO_LINDEROS_DEFINIDOS));
        }

        if (request.getParameter(CFolio.FOLIO_COMPLEMENTACION) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION, request.getParameter(CFolio.FOLIO_COMPLEMENTACION));
        }

        if (request.getParameter(CFolio.FOLIO_LINDERO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_LINDERO, request.getParameter(CFolio.FOLIO_LINDERO));
        }

        if (request.getParameter(CFolio.FOLIO_COD_CATASTRAL) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COD_CATASTRAL, request.getParameter(CFolio.FOLIO_COD_CATASTRAL));
        }

        if (request.getParameter(CFolio.FOLIO_EJE1) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_EJE1, request.getParameter(CFolio.FOLIO_EJE1));
        }

        if (request.getParameter(CFolio.FOLIO_EJE2) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_EJE2, request.getParameter(CFolio.FOLIO_EJE2));
        }

        if (request.getParameter(CFolio.FOLIO_VALOR1) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_VALOR1, request.getParameter(CFolio.FOLIO_VALOR1));
        }

        if (request.getParameter(CFolio.FOLIO_VALOR2) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_VALOR2, request.getParameter(CFolio.FOLIO_VALOR2));
        }

        if (request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR, request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NUM));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_TIPO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_TIPO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_OFICINA));
        }

        if (request.getParameter(CFolio.FOLIO_TIPO_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_TIPO_DOCUMENTO, request.getParameter(CFolio.FOLIO_TIPO_DOCUMENTO));
        }

        if (request.getParameter(CFolio.FOLIO_NUM_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_NUM_DOCUMENTO, request.getParameter(CFolio.FOLIO_NUM_DOCUMENTO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.FOLIO_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute("tipo_oficina", request.getParameter("tipo_oficina"));
            request.getSession().setAttribute("folio_tipo_nom_oficina", request.getParameter("tipo_nom_oficina"));
            request.getSession().setAttribute("folio_numero_oficina", request.getParameter("numero_oficina"));
            request.getSession().setAttribute("folio_id_oficina", request.getParameter("id_oficina"));
        }
    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaFolio(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.FOLIO_NUM_RADICACION);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA);
        request.getSession().removeAttribute(CFolio.FOLIO_ESTADO_FOLIO);
        request.getSession().removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        request.getSession().removeAttribute(CFolio.FOLIO_NUPRE);
        request.getSession().removeAttribute(CFolio.FOLIO_DETERMINACION_INMUEBLE);
        request.getSession().removeAttribute(CFolio.FOLIO_AVALUO);
        request.getSession().removeAttribute(CFolio.FOLIO_FECHA_AVALUO);
        request.getSession().removeAttribute(CFolio.FOLIO_DESTINACION_ECONOMICA);
        request.getSession().removeAttribute(CFolio.FOLIO_PRIVMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_PRIVCENTIMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_CONSMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_CONSCENTIMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_COEFICIENTE);
        request.getSession().removeAttribute(CFolio.FOLIO_HECTAREAS);
        request.getSession().removeAttribute(CFolio.FOLIO_METROS);
        request.getSession().removeAttribute(CFolio.FOLIO_CENTIMETROS);
        request.getSession().removeAttribute(CFolio.FOLIO_LINDEROS_DEFINIDOS);
        request.getSession().removeAttribute(CFolio.FOLIO_COMPLEMENTACION);
        request.getSession().removeAttribute(CFolio.FOLIO_LINDERO);
        request.getSession().removeAttribute(CFolio.FOLIO_TIPO_PREDIO);
        request.getSession().removeAttribute(CFolio.FOLIO_COD_CATASTRAL);
    }

    //FUNCIONES PARA PRESERVAR LA INFORMACIÓN DE LA ANOTACIÓN
    /**
     * @param request
     */
    private void preservarInfoBasicaAnotacion(HttpServletRequest request) {
        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
        }

        if (request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO, request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_FECHA_RADICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_FECHA_RADICACION, request.getParameter(CFolio.ANOTACION_FECHA_RADICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_RADICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_RADICACION, request.getParameter(CFolio.ANOTACION_NUM_RADICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_MODALIDAD) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_MODALIDAD, request.getParameter(CFolio.ANOTACION_MODALIDAD));
        }

        if (request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION, request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION));
        }

        if (request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO, request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO, request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA, request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
        }

        if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
            request.getSession().setAttribute("tipo_oficina", request.getParameter("tipo_oficina"));
            request.getSession().setAttribute("tipo_nom_oficina", request.getParameter("tipo_nom_oficina"));
            request.getSession().setAttribute("numero_oficina", request.getParameter("numero_oficina"));
            request.getSession().setAttribute("id_oficina", request.getParameter("id_oficina"));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
        }

        if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
            request.getSession().setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM, request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
        }

    }

    /**
     * @param request
     */
    private void eliminarInfoBasicaAnotacion(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.ANOTACION_NUM_RADICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA);
        request.getSession().removeAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_FECHA_RADICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_MODALIDAD);
        request.getSession().removeAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION);
        request.getSession().removeAttribute(CFolio.ANOTACION_NUM_DOCUMENTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID);
        request.getSession().removeAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS);
        request.getSession().removeAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS);
        request.getSession().removeAttribute("tipo_oficina");
        request.getSession().removeAttribute("tipo_nom_oficina");
        request.getSession().removeAttribute("numero_oficina");
        request.getSession().removeAttribute("id_oficina");
    }

    //FUNCIONES COMPLEMENTARIOS
    /**
     * @param request
     * @return
     */
    private Evento preservarInfo(HttpServletRequest request) {
        preservarInfoBasicaAnotacion(request);
        preservarInfoBasicaCiudadano(request);
        return null;
    }

    /**
     * @param request
     */
    private void eliminarInfoDireccion(HttpServletRequest request) {
        request.getSession().removeAttribute(CFolio.FOLIO_EJE1);
        request.getSession().removeAttribute(CFolio.FOLIO_EJE2);
        request.getSession().removeAttribute(CFolio.FOLIO_VALOR1);
        request.getSession().removeAttribute(CFolio.FOLIO_VALOR2);
        request.getSession().removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
    }

    /**
     * @param fechaInterfaz
     * @return
     */
    private static Calendar darFecha(String fechaInterfaz) {
        java.util.Date date = null;

        try {
            date = DateFormatUtil.parse(fechaInterfaz);
            if (fechaInterfaz.indexOf("-") != -1) {
                return null;
            }
        } catch (ParseException e) {
            return null;
        } catch (Throwable t) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        String[] partido = fechaInterfaz.split("/");

        if (partido.length == 3) {
            int dia = Integer.parseInt(partido[0]);
            int mes = Integer.parseInt(partido[1]) - 1;
            int año = Integer.parseInt(partido[2]);
            calendar.set(año, mes, dia);

            if ((calendar.get(Calendar.YEAR) == año) && (calendar.get(Calendar.MONTH) == mes) && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
                return calendar;
            }
        }

        return null;
    }

    /* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
     */
    public void doEnd(HttpServletRequest request, EventoRespuesta eventoRespuesta) {
        if (eventoRespuesta != null) {
            if (eventoRespuesta instanceof EvnRespCrearFolio) {
                EvnRespCrearFolio respuesta = (EvnRespCrearFolio) eventoRespuesta;
                if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.CREACION_FOLIO)) {
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getPayload());
                    request.getSession().setAttribute(WebKeys.TURNO, respuesta.getTurno());
                }
                if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.ELIMINAR_FOLIO_TEMPORAL)) {
                    request.getSession().setAttribute(WebKeys.TURNO, respuesta.getPayload());
                }
                if (respuesta.getTipoEvento().equals(EvnRespCrearFolio.VALIDAR_ANOTACION_CIUDADANO)) {
                    request.getSession().removeAttribute("listanat");
                    eliminarInfoBasicaAnotacion(request);
                    eliminarInfoBasicaVariosCiudadanos(request);
                    request.getSession().removeAttribute(AWCalificacion.HAY_REFRESCO);
                    request.getSession().removeAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION);
                    if (respuesta.getAnotacionesAgregadas() != null) {
                        List anotaciones = respuesta.getAnotacionesAgregadas();
                        if (anotaciones == null) {
                            anotaciones = new Vector();
                        }
                        anotaciones.add(respuesta.getAnotacion());
                        request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FOLIO, anotaciones);
                    }
                    if ((respuesta.getNextOrden() != null) && (!respuesta.getNextOrden().equals(""))) {
                        request.getSession().setAttribute(CFolio.NEXT_ORDEN_ANOTACION, respuesta.getNextOrden());
                    }
                }

            } else if (eventoRespuesta instanceof EvnRespConsultaFolio) {
                EvnRespConsultaFolio respuesta = (EvnRespConsultaFolio) eventoRespuesta;
                if (respuesta.getFolio() != null) {
                    request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
                }
            } else if (eventoRespuesta instanceof EvnRespCiudadano) {
                EvnRespCiudadano evn = (EvnRespCiudadano) eventoRespuesta;
                String ver = request.getParameter(CAnotacionCiudadano.SECUENCIA);

                if (evn.isCiudadanoEncontrado()) {
                    request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + ver, evn.getCiudadano().getApellido1());
                    request.getSession().setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + ver, evn.getCiudadano().getApellido2());
                    request.getSession().setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + ver, evn.getCiudadano().getNombre());
                    request.getSession().setAttribute(CFolio.ANOTACION_SEXO_PERSONA + ver, evn.getCiudadano().getSexo());
                    if (evn.isMostrarCiudadano()) {
                        request.getSession().setAttribute(CFolio.CIUDADANO_EDITABLE + ver, new Boolean(true));
                    } else {
                        request.getSession().removeAttribute(CFolio.CIUDADANO_EDITABLE + ver);
                    }
                } else {
                    request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA + ver);
                    request.getSession().removeAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA + ver);
                    request.getSession().removeAttribute(CFolio.ANOTACION_NOMBRES_PERSONA + ver);
                    request.getSession().removeAttribute(CFolio.ANOTACION_SEXO_PERSONA + ver);
                    request.getSession().removeAttribute(CFolio.CIUDADANO_EDITABLE + ver);
                }
            } else if (eventoRespuesta instanceof EvnRespCertificado) {
                EvnRespCertificado respuesta = (EvnRespCertificado) eventoRespuesta;
                if (respuesta.getTipoEvento().equals(EvnRespCertificado.CONSULTA_FOLIO_COMPLEMENTACION)) {
                    if (respuesta.getFolio() != null && respuesta.getFolio().getComplementacion() != null) {
                        request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION, respuesta.getFolio().getComplementacion().getComplementacion());
                        request.getSession().setAttribute(CFolio.FOLIO_ID_COMPLEMENTACION, respuesta.getFolio().getComplementacion().getIdComplementacion());
                    }
                }
            } else if (eventoRespuesta instanceof EvnRespCalificacion) {
                EvnRespCalificacion respuesta = (EvnRespCalificacion) eventoRespuesta;
                if (respuesta.getTipoEvento().equals(EvnRespCalificacion.VOLVER_AGREGAR_CIUDADANOS)) {
                    request.getSession().setAttribute(WebKeys.LISTA_CIUDADANOS_ANOTACION, respuesta.getListaCompletaCiudadanos());
                }
            }
        }
    }

}

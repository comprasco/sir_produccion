package gov.sir.core.web.acciones.administracion;

import java.util.Arrays;
import gov.sir.core.eventos.administracion.EvnAdministracionFenrir;
import gov.sir.core.eventos.administracion.EvnAdministracionForseti;
import gov.sir.core.eventos.administracion.EvnRespAdministracionFenrir;
import gov.sir.core.eventos.administracion.EvnRespAdministracionForseti;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.CategoriaNotaria;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoFestivo;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.DominioNaturalezaJuridica;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.EstadoAnotacion;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.TipoPredio;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.ZonaRegistral;
import gov.sir.core.negocio.modelo.constantes.CCategoria;
import gov.sir.core.negocio.modelo.PlantillaPertenencia;
import gov.sir.core.negocio.modelo.constantes.CCirculo;
import gov.sir.core.negocio.modelo.constantes.CCirculoFestivo;
import gov.sir.core.negocio.modelo.constantes.CCirculoTraslado;
import gov.sir.core.negocio.modelo.constantes.CDepartamento;
import gov.sir.core.negocio.modelo.constantes.CDominioNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CEje;
import gov.sir.core.negocio.modelo.constantes.CEstadoAnotacion;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CMunicipio;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.COficinaOrigen;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CTipoDocumento;
import gov.sir.core.negocio.modelo.constantes.CTipoOficina;
import gov.sir.core.negocio.modelo.constantes.CTipoPredio;
import gov.sir.core.negocio.modelo.constantes.CUsuario;
import gov.sir.core.negocio.modelo.constantes.CVereda;
import gov.sir.core.negocio.modelo.constantes.CZonaRegistral;
/**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : se abilita el uso de la clase VersionNaturalezaJuridicaComparador
	 */
import gov.sir.core.negocio.modelo.util.VersionNaturalezaJuridicaComparador;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.OficinaNitException;
import gov.sir.core.web.acciones.excepciones.PlantillaPertenenciaException;
import gov.sir.core.web.acciones.excepciones.ReabrirFolioException;
import gov.sir.core.web.acciones.excepciones.ValidacionAdicionCirculoException;
import gov.sir.core.web.acciones.excepciones.ValidacionOficinaOrigenException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionZonaRegistralException;
import gov.sir.core.web.acciones.excepciones.ValidacionTrasladoException;
import gov.sir.core.web.helpers.comun.ElementoLista;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se abilita el uso de las clases
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import gov.sir.core.negocio.modelo.CirculoPk;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Acción Web encargada de manejar solicitudes para generar eventos
 * provenientes de solicitudes realizadas a través del protocolo HTTP
 * para administración de objetos de Forseti 
 * @author jmendez
 */
public class AWAdministracionForseti extends SoporteAccionWeb {

	/** Constante que identifica la acción de crear un departamento */
	public static final String ADICIONA_DEPARTAMENTO = "ADICIONA_DEPARTAMENTO";

	/** Constante que identifica la acción de eliminar un departamento */
	public static final String ELIMINA_DEPARTAMENTO = "ELIMINA_DEPARTAMENTO";

	/** Constante que identifica la acción de seleccionar un departamento */
	public static final String SELECCIONA_DEPARTAMENTO = "SELECCIONA_DEPARTAMENTO";

	/** Constante que identifica la acción de adicionar un municipio */
	public static final String ADICIONA_MUNICIPIO = "ADICIONA_MUNICIPIO";

	/** Constante que identifica la acción de eliminar un municipio */
	public static final String ELIMINA_MUNICIPIO = "ELIMINA_MUNICIPIO";

	/** Constante que identifica la acción de seleccionar  un municipio */
	public static final String SELECCIONA_MUNICIPIO = "SELECCIONA_MUNICIPIO";

	/** Constante que identifica la acción de terminar la edición de municipios */
	public static final String TERMINA_MUNICIPIO = "TERMINA_MUNICIPIO";

	/** Constante que identifica la acción de crear una vereda */
	public static final String ADICIONA_VEREDA = "ADICIONA_VEREDA";

	/** Constante que identifica la acción de eliminar una vereda */
	public static final String ELIMINA_VEREDA = "ELIMINA_VEREDA";

	/** Constante que identifica la acción de terminar la edición de veredas */
	public static final String TERMINA_VEREDA = "TERMINA_VEREDA";

	/** Constante que identifica la acción de crear un estado de folio */
	public static final String ADICIONA_ESTADO_FOLIO = "ADICIONA_ESTADO_FOLIO";

	/** Constante que identifica la acción de eliminar un estado de folio */
	public static final String ELIMINA_ESTADO_FOLIO = "ELIMINA_ESTADO_FOLIO";

	/** Constante que identifica la acción de crear un tipo de oficina */
	public static final String ADICIONA_TIPO_OFICINA = "ADICIONA_TIPO_OFICINA";

	/** Constante que identifica la acción de eliminar un tipo de oficina */
	public static final String ELIMINA_TIPO_OFICINA = "ELIMINA_TIPO_OFICINA";

	/** Constante que identifica la acción de crear un tipo de predio */
	public static final String ADICIONA_TIPO_PREDIO = "ADICIONA_TIPO_PREDIO";

	/** Constante que identifica la acción de eliminar un tipo de predio */
	public static final String ELIMINA_TIPO_PREDIO = "ELIMINA_TIPO_PREDIO";

	/** Constante que identifica la acción de crear un tipo de documento */
	public static final String ADICIONA_TIPO_DOCUMENTO = "ADICIONA_TIPO_DOCUMENTO";

	/** Constante que identifica la acción de eliminar un tipo de documento */
	public static final String ELIMINA_TIPO_DOCUMENTO = "ELIMINA_TIPO_DOCUMENTO";

	/** Constante que identifica la acción de crear un grupo de naturaleza jurídica */
	public static final String ADICIONA_GRUPO_NAT_JURIDICA_ANOTACIONES =
		"ADICIONA_GRUPO_NAT_JURIDICA_ANOTACIONES";

	/** Constante que identifica la acción de eliminar un grupo de naturaleza jurídica */
	public static final String ELIMINA_GRUPO_NAT_JURIDICA_ANOTACIONES =
		"ELIMINA_GRUPO_NAT_JURIDICA_ANOTACIONES";

	/** Constante que identifica la acción de seleccionar un grupo de naturaleza jurídica */
	public static final String SELECCIONA_GRUPO_NAT_JURIDICA_ANOTACIONES =
		"SELECCIONA_GRUPO_NAT_JURIDICA_ANOTACIONES";

	/** Constante que identifica la acción de adicionar un tipo de naturaleza jurídica */
	public static final String ADICIONA_TIPO_NATURALEZA_JURIDICA = "ADICIONA_TIPO_NATURALEZA_JURIDICA";

	/** Constante que identifica la acción de eliminar un tipo de naturaleza jurídica */
	public static final String ELIMINA_TIPO_NATURALEZA_JURIDICA = "ELIMINA_TIPO_NATURALEZA_JURIDICA";
	
	/** Constante que identifica la acción de editar un tipo de naturaleza jurídica */
	public static final String EDITA_TIPO_NATURALEZA_JURIDICA = "EDITA_TIPO_NATURALEZA_JURIDICA";
	
	/** Constante que identifica que terminó la edición de un tipo de naturaleza jurídica */
	public static final String TERMINA_EDICION_NATURALEZA = "TERMINA_EDICION_NATURALEZA";
	
	/** Constante que identifica la acción de editar atributos de un tipo de naturaleza jurídica */
	public static final String EDITA_DETALLES_NATURALEZA_JURIDICA = "EDITA_DETALLES_NATURALEZA_JURIDICA";

	/** Constante que identifica la acción de terminar la edicion de tipos de naturaleza jurídica */
	public static final String TERMINA_TIPO_NATURALEZA_JURIDICA = "TERMINA_TIPO_NATURALEZA_JURIDICA";

	/** Constante que identifica la acción de adicionar un   círculo registral */
	public static final String ADICIONA_CIRCULO_REGISTRAL = "ADICIONA_CIRCULO_REGISTRAL";

	/** Constante que identifica la acción de editar el NIT  de círculo registral */
	public static final String EDITA_CIRCULO_NIT = "EDITA_CIRCULO_NIT";
	
	/** Constante que identifica la acción de editar el círculo registral */
	public static final String EDITA_CIRCULO= "EDITA_CIRCULO";

	/** Constante que identifica la acción de eliminar un   círculo registral */
	public static final String ELIMINA_CIRCULO_REGISTRAL = "ELIMINA_CIRCULO_REGISTRAL";

	/** Constante que identifica la acción de adicionar un estado de anotación */
	public static final String ADICIONA_ESTADO_ANOTACION = "ADICIONA_ESTADO_ANOTACION";

	/** Constante que identifica la acción de eliminar un estado de anotación */
	public static final String ELIMINA_ESTADO_ANOTACION = "ELIMINA_ESTADO_ANOTACION";

	/** Constante que identifica la acción de adicionar un dominio de naturaleza jurídica */
	public static final String ADICIONA_DOMINIO_NAT_JURIDICA = "ADICIONA_DOMINIO_NAT_JURIDICA";

	/** Constante que identifica la acción de eliminar un dominio de naturaleza jurídica */
	public static final String ELIMINA_DOMINIO_NAT_JURIDICA = "ELIMINA_DOMINIO_NAT_JURIDICA";

	/** Constante que identifica la acción de adicionar un circulo festivo */
	public static final String ADICIONA_CIRCULO_FESTIVO = "ADICIONA_CIRCULO_FESTIVO";

	/** Constante que identifica la acción de eliminar un circulo festivo */
	public static final String ELIMINA_CIRCULO_FESTIVO = "ELIMINA_CIRCULO_FESTIVO";

	/** Constante que identifica la acción de seleccionar un circulo festivo */
	public static final String SELECCIONA_CIRCULO_FESTIVO = "SELECCIONA_CIRCULO_FESTIVO";

	/** Constante que identifica la acción de adicionar un eje */
	public static final String ADICIONA_EJE = "ADICIONA_EJE";

	/** Constante que identifica la acción de eliminar un eje */
	public static final String ELIMINA_EJE = "ELIMINA_EJE";

	/** Constante que identifica la acción de adicionar una zona registral  */
	public static final String ADICIONA_ZONA_REGISTRAL = "ADICIONA_ZONA_REGISTRAL";

	/** Constante que identifica la acción de eliminar una zona registral  */
	public static final String ELIMINA_ZONA_REGISTRAL = "ELIMINA_ZONA_REGISTRAL";

	/** Constante que identifica la acción de seleccionar una zona registral  */
	public static final String SELECCIONA_ZONA_REGISTRAL = "SELECCIONA_ZONA_REGISTRAL";
	
	/** Constante que identifica la acción de seleccionar una naturaleza juridica  */
	public static final String SELECCIONA_NATURALEZA_JURIDICA = "SELECCIONA_NATURALEZA_JURIDICA";

	/** Constante que identifica la acción de seleccionar una zona registral de departamento  */
	public static final String SELECCIONA_ZONA_REGISTRAL_DEPTO = "SELECCIONA_ZONA_REGISTRAL_DEPTO";

	/** Constante que identifica la acción de adicionar una zona registral de municipio  */
	public static final String SELECCIONA_ZONA_REGISTRAL_MUNICIPIO =
		"SELECCIONA_ZONA_REGISTRAL_MUNICIPIO";

	/** Constante que identifica la acción de adicionar una zona registral para un traslado  */
	public static final String SELECCIONA_ZONA_REGISTRAL_TRASLADO = "SELECCIONA_ZONA_REGISTRAL_TRASLADO";

	/** Constante que identifica la acción de adicionar una zona registral departamento para traslado */
	public static final String SELECCIONA_ZONA_REGISTRAL_TRASLADO_DEPTO =
		"SELECCIONA_ZONA_REGISTRAL_TRASLADO_DPTO";

	/** Constante que identifica la acción de adicionar una zona registral municipio para traslado */
	public static final String SELECCIONA_ZONA_REGISTRAL_TRASLADO_MUNICIPIO =
		"SELECCIONA_ZONA_REGISTRAL_TRASLADO_MUNICIPIO";

	/** Constante que identifica la acción de trasladar un folio */
	public static final String TRASLADAR = "TRASLADAR";

	/** Constante que identifica la acción de trasladar un folio específico */
	public static final String FOLIO_TRASLADADO = "FOLIO_TRASLADADO";

	/** Constante que identifica la acción de adicionar una oficina origen */
	public static final String ADICIONA_OFICINA_ORIGEN = "ADICIONA_OFICINA_ORIGEN";
	
	/** Constante que identifica la acción de adicionar una oficina origen */
	public static final String ADICIONA_OFICINA_ORIGEN_NOTARIA = "ADICIONA_OFICINA_ORIGEN_NOTARIA";
	
	/** Constante que identifica la acción de consultar una oficina origen por vereda */
	public static final String CONSULTA_OFICINA_ORIGEN_POR_VEREDA = "CONSULTA_OFICINA_ORIGEN_POR_VEREDA";
	/** Constante que identifica la acción de consultar una oficina origen por vereda */
	public static final String CONSULTA_OFICINA_ORIGEN_POR_VEREDA_NOTARIA = "CONSULTA_OFICINA_ORIGEN_POR_VEREDA_NOTARIA";
	/** Constante que identifica la acción de seleccionar una oficina origen */
	public static final String SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO =
		"SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO";

	/** Constante que identifica la acción de seleccionar una oficina origen */
	public static final String SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO_NOTARIA =
		"SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO_NOTARIA";
	
	/** Constante que identifica la acción de seleccionar una oficina origen */
	public static final String SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MUNICIPIO =
		"SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MUNICIPIO";

	/** Constante que identifica las acción de terminar la utilización de los servicios 
	 * de la acción WEB (Para limpiar la sesión y redirigir a la página principal de páginas
	 * administrativas */
	public static final String TERMINA = "TERMINA";

	/** Constantes que identifican las variables de sesión que maneja la accion web */

	/** Constante que identifica las variable del HttpSession donde se almacena una lista de Circulos    */
	public static final String LISTA_CIRCULOS_ELEMENTO = "LISTA_CIRCULOS_ELEMENTO";
	
	/** Constante que identifica las variable del HttpSession donde se almacena una lista de Circulos del usuario
	 * para ser desplegados en un helper de combobox    */
	public static final String LISTA_CIRCULOS_USUARIO_ELEMENTO = "LISTA_CIRCULOS_USUARIO_ELEMENTO";

	/** Constante que identifica las variable del HttpSession donde se almacena un Departamento */
	public static final String DEPARTAMENTO_SELECCIONADO = "DEPARTAMENTO_SELECCIONADO";

	/** Constante que identifica las variable del HttpSession donde se almacena un Map con los municipios de un departamento  */
	public static final String MAP_MUNICIPIOS = "MAP_MUNICIPIOS";

	/** Constante que identifica las variable del HttpSession donde se almacena un Municipio */
	public static final String MUNICIPIO_SELECCIONADO = "MUNICIPIO_SELECCIONADO";

	/** Constante que identifica las variable del HttpSession donde se almacena un Map con las veredas de un municipio  */
	public static final String MAP_VEREDAS = "MAP_VEREDAS";

	/** Constante que identifica las variable del HttpSession donde se almacena una Selección de grupo de naturaleza jurídica */
	public static final String SELECCION_GRUPO_NAT_JURIDICA = "SELECCION_GRUPO_NAT_JURIDICA";

	/** Constante que identifica las variable del HttpSession donde se almacena una lista de Circulos para ser  
		 * desplegados en un  componente de tipo ListaElementoHelper */
	public static final String LISTA_ELEMENTOS_CIRCULO = "LISTA_ELEMENTOS_CIRCULO";

	/** Constante que identifica las variable del HttpSession donde se almacena una lista de circulos festivos */
	public static final String LISTA_CIRCULO_FESTIVO = "LISTA_CIRCULO_FESTIVO";

	/** Constante que identifica las variable del HttpSession donde se almacena una lista de zonas registrales */
	public static final String LISTA_ZONAS_REGISTRALES = "LISTA_ZONAS_REGISTRALES";

	/** Constante que identifica las variable del HttpSession donde se almacena una lista de departamentos */
	public static final String LISTA_DEPARTAMENTOS = "LISTA_DEPARTAMENTOS";

	/** Constante que identifica las variable del HttpSession donde se almacena una lista de municipios */
	public static final String LISTA_MUNICIPIOS = "LISTA_MUNICIPIOS";

	/** Constante que identifica las variable del HttpSession donde se almacena una lista de veredas */
	public static final String LISTA_VEREDAS = "LISTA_VEREDAS";

	/** Constante que identifica las variable del HttpSession donde se almacena una lista de Fotocopias */
	public static final String LISTA_FOTOCOPIAS = "LISTA_FOTOCOPIAS";

	/** Constante que identifica las variable del HttpSession donde se almacena una lista de oficinas por vereda */
	public static final String LISTA_OFICINAS_POR_VEREDA = "LISTA_OFICINAS_POR_VEREDA";

	/** Constante que identifica las variable del HttpSession donde se almacena una lista de categorias */
	public static final String LISTA_CATEGORIAS = "LISTA_CATEGORIAS";

	/** Constante que identifica las variable del HttpSession donde se almacena el identificador de  la matrícula a trasladar */
	public static final String ID_MATRICULA_TRASLADO = "ID_MATRICULA_TRASLADO";

	/** Constante que identifica las variable del HttpSession donde se almacena el círculo al cual se va a trasladar una matricula */
	public static final String CIRCULO_TRASLADO = "CIRCULO_TRASLADO";

	/** Constante que identifica la acción de proceso de catastro */
	public static final String PROCESO_CATASTRO = "PROCESO_CATASTRO";

	/** Constante que identifica la fecha inicial para el proceso de catastro */
	public static final String FECHINI = "FECHINI";

	/** Constante que identifica la fecha inicial para el proceso de catastro */
	public static final String FECHFIN = "FECHFIN";

	/** Constante que identifica la fecha inicial para el proceso de editar plantilla */
	public static final String EDITAR_PLANTILLAS_CERTIFICADOS = "EDITAR_PLANTILLAS_CERTIFICADOS";
	
	/** Constante que identifica la fecha inicial para identificar los tipos de plantilla */
	public static final String TIPO_PLANTILLAS = "TIPO_PLANTILLAS";
	
	/** Constante que identifica la fecha inicial para identificar los tipos de plantilla */
	public static final String LISTA_PLANTILLAS = "LISTA_PLANTILLAS";
	
	/** Constante que identifica la fecha inicial para el proceso de cargar los tipos de plantillas */
	public static final String CARGAR_PLANTILLAS_CERTIFICADOS = "CARGAR_PLANTILLAS_CERTIFICADOS";
	
	/** Constante que identifica la fecha inicial para el proceso de cargar los tipos de plantillas */
 	public static final String CARGAR_PLANTILLA = "CARGAR_PLANTILLA";
 	
	/** Constante que identifica la acción de editar una naturaleza jurídica  */
	public static final String EDITA_NATURALEZA_JURIDICA = "EDITA_NATURALEZA_JURIDICA";

	/** Constante que identifica la acción de editar una naturaleza jurídica  */
	public static final String REABRIR_FOLIO = "REABRIR_FOLIO";
	
	/** Constante que identifica que se quiere editar una oficina de origen*/
	public static final String EDITAR_OFICINA_ORIGEN = "EDITAR_OFICINA_ORIGEN";
	
	/** Constante que identifica que se quiere editar una oficina de origen*/
	public static final String EDITAR_OFICINA_ORIGEN_NOTARIA = "EDITAR_OFICINA_ORIGEN_NOTARIA";
	
	
	/** Constante que identifica que se quiere editar una oficina de origen*/
	public static final String ELIMINAR_OFICINA_ORIGEN = "ELIMINAR_OFICINA_ORIGEN";
	
	/** Constante que identifica que se quiere editar una oficina de origen*/
	public static final String ELIMINAR_OFICINA_ORIGEN_NOTARIA = "ELIMINAR_OFICINA_ORIGEN_NOTARIA";
	
	/** Constante que identifica el objeto oficina de origen que se quiere editar*/
	public static final String OFICINA_PARA_EDITAR = "OFICINA_PARA_EDITAR";
	
	/** Constante que identifica que se quiere enviar a edicion una oficina*/
	public static final String ENVIAR_OFICINA_EDICION = "ENVIAR_OFICINA_EDICION";
	
	/** Constante que identifica que se quiere enviar a edicion una oficina*/
	public static final String ENVIAR_OFICINA_EDICION_NOTARIA = "ENVIAR_OFICINA_EDICION_NOTARIA";
	
	
	/** Constante que identifica que se quiere enviar a edicion una oficina*/
	public static final String LISTA_DEPARTAMENTOS_CIRCULO= "LISTA_DEPARTAMENTOS_CIRCULO";
	
	public static final String CONSULTA_OFICINA_ORIGEN_POR_TIPO_OFICINA="CONSULTA_OFICINA_ORIGEN_POR_TIPO_OFICINA";
	
	public static final String ADMINISTRADOR_NACIONAL="ADMINISTRADOR_NACIONAL";

	public static final String RESOLUCION_TRASLADO = "RESOLUCION_TRASLADO";
	
	public static final String TIPO_ASOCIACION_CIRCULO="TIPO_ASOCIACION_CIRCULO";
	public static final String TIPO_ASOCIACION_CIRCULO_ASOCIAR="ASOCIAR";
	public static final String TIPO_ASOCIACION_CIRCULO_CREAR="CREAR";
	public static final String INHABILITAR_CIRCULO_REGISTRAL="INHABILITAR_CIRCULO_REGISTRAL";
	public static final String CIRCULO_INHABILITADO="CIRCULO_INHABILITADO";
	public static final String CIRCULO_DESTINO_ASOCIAR="CIRCULO_DESTINO_ASOCIAR";
	public static final String TERMINAR_CIRCULO_INHABILITAR="AGREGAR_ZONAS_CIRCULO_INHABILITAR";
	public static final String VOLVER_CIRCULO_INHABILITAR="VOLVER_CIRCULO_INHABILITAR";
	public static final String CIRCULO_DESTINO="CIRCULO_DESTINO";
	public static final String VOLVER_INHAB_CIRCULO_ZONAS="VOLVER_INHAB_CIRCULO_ZONAS";
	public static final String ADICIONA_ZONA_REGISTRAL_CIRC_INHAB="ADICIONA_ZONA_REGISTRAL_CIRC_INHAB";
	public static final String TERMINA_ZONA_CIRC_INHAB="TERMINA_ZONA_CIRC_INHAB";
	public static final String SELECCIONA_ZONA_REGISTRAL_DEPTO_INHAB="SELECCIONA_ZONA_REGISTRAL_DEPTO_INHAB";
	public static final String SELECCIONA_ZONA_REGISTRAL_MUNICIPIO_INHAB="SELECCIONA_ZONA_REGISTRAL_MUNICIPIO_INHAB";
	public static final String LISTA_ZONAS_REGISTRALES_INHABILITA="LISTA_ZONAS_REGISTRALES_INHABILITA";
	public static final String ELIMINA_ZONA_REGISTRAL_INHAB="ELIMINA_ZONA_REGISTRAL_INHAB";
	public static final String USUARIOS_MOVER="USUARIOS_MOVER";
	public static final String USUARIOS_CREAR_INHAB="USUARIOS_CREAR_INHAB";
	public static final String VOLVER_USUARIO_CIRCULO_INHAB="VOLVER_USUARIO_CIRCULO_INHAB";
	public static final String LISTA_USUARIOS_TRASLADAR="LISTA_USUARIOS_TRASLADAR";
	public static final String LISTA_USUARIOS_CREADOS="LISTA_USUARIOS_CREADOS";
	public static final String ELIMINAR_USUARIOS_TRASLADADOS="ELIMINAR_USUARIOS_TRASLADADOS";
	public static final String ELIMINAR_USUARIOS_CREADOS="ELIMINAR_USUARIOS_CREADOS";
	public static final String TERMINAR_USUARIO_CIRCULO_INHAB="TERMINAR_USUARIO_CIRCULO_INHAB";
	public static final String VOLVER_USUARIO_ROLCONSULTA="VOLVER_USUARIO_ROLCONSULTA";
	public static final String TERMINAR_PROCESO_CIRCULO_INHAB="TERMINAR_PROCESO_CIRCULO_INHAB";
	public static final String USUARIO_CONSULTA="USUARIO_CONSULTA";
	public static final String ROLES_USUARIOS_CIRCULO_INHAB="ROLES_USUARIOS_CIRCULO_INHAB";
	public static final String LISTA_ZONAS_REGISTRALES_CIRC_INHAB = "LISTA_ZONAS_REGISTRALES_CIRC_INHAB";
	public static final String CARGAR_CIRCULOS_INHABILITADOS = "CARGAR_CIRCULOS_INHABILITADOS";
	public static final String LISTA_ELEMENTOS_CIRCULO_DESTINO="LISTA_ELEMENTOS_CIRCULO_DESTINO";
	public static final String LISTA_ELEMENTOS_CIRCULO_INHABILITADO="LISTA_ELEMENTOS_CIRCULO_INHABILITADO";
	public static final String TRASLADAR_FOLIOS="TRASLADAR_FOLIOS";
        /*
         * @author      :   Julio Alcázar Rivas
         * @change      :   se agregan las constantes AGREGAR,ELIMINAR y CIRCULOS_SIR para el proceso traslado de folio
         * Caso Mantis  :   07123
         */
        public static final String AGREGAR = "AGREGAR";
        public static final String ELIMINAR = "ELIMINAR";
        /**
         * @author     : Ellery David Robles Gómez
         * @change     : Se agregan constantes AGREGAR_FUNDAMENTO, ELIMINAR_FUNDAMENTO, TIPOS_FUNDAMENTO_SIR
         * Caso Mantis : 14985
         */
        public static final String AGREGAR_FUNDAMENTO = "AGREGAR_FUNDAMENTO";
        public static final String ELIMINAR_FUNDAMENTO = "ELIMINAR_FUNDAMENTO";
        public static final String TIPOS_FUNDAMENTO_SIR = "TIPOS_FUNDAMENTO_SIR";
        public static final String CIRCULOS_SIR= "CIRCULOS_SIR";
        
        /**
         * @author     : Ellery David Robles Gómez
         * @change     : Se agregan constantes AGREGAR_FUNDAMENTO, ELIMINAR_FUNDAMENTO, TIPOS_FUNDAMENTO_SIR
         * Caso Mantis : 14985
         */
        public static final String AGREGAR_FUNDAMENTO_MASIVO = "AGREGAR_FUNDAMENTO_MASIVO";
        public static final String ELIMINAR_FUNDAMENTO_MASIVO = "ELIMINAR_FUNDAMENTO_MASIVO";
        public static final String AGREGAR_FUNDAMENTO_CONFIRMACION = "AGREGAR_FUNDAMENTO_CONFIRMACION";
        public static final String ELIMINAR_FUNDAMENTO_CONFIRMACION = "ELIMINAR_FUNDAMENTO_CONFIRMACION";

        /*
         * @author      : Julio Alcázar Rivas
         * @change      : nuevas constantes del proceso traslados
         * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
         */
        public static final String CIRCULOS_NOSIR= "CIRCULOS_NOSIR";
        public static final String CIRCULO_OFICINA= "CIRCULO_OFICINA";
        public static final String FOLIO_CONFIRMACION = "FOLIO_CONFIRMACION";
        public static final String RESOLUCION_DESTINO = "RESOLUCION_DESTINO";
        public static final String TRASLADO_CONFIRMACION = "TRASLADO_CONFIRMACION";
        public static final String CANCELAR_SOLICITUD = "CANCELAR_SOLICITUD";
	 /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se agregan constantes para el proceso de traslado de folios masivos
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
        public static final String DEPARTAMENTO_ID = "DEPARTAMENTO_ID";
        public static final String MUNICIPIO_ID = "MUNICIPIO_ID";
        public static final String VEREDA_ID= "VEREDA_ID";
        public static final String CONTENT_TYPE ="application/vnd.ms-excel";
        public static final String ONSELECT_CIRCULO_ORIGEN ="ONSELECT_CIRCULO_ORIGEN";
        public static final String ONSELECT_DEPARTAMENTO_ORIGEN = "ONSELECT_DEPARTAMENTO_ORIGEN";
        public static final String ONSELECT_MUNICIPIO_ORIGEN = "ONSELECT_MUNICIPIO_ORIGEN";
        public static final String ONSELECT_VEREDA_ORIGEN="ONSELECT_VEREDA_ORIGEN";
        public static final String ONSELECT_CIRCULO_DESTINO ="ONSELECT_CIRCULO_DESTINO";
        public static final String ONSELECT_DEPARTAMENTO_DESTINO = "ONSELECT_DEPARTAMENTO_DESTINO";
        public static final String ONSELECT_MUNICIPIO_DESTINO = "ONSELECT_MUNICIPIO_DESTINO";
        public static final String AGREGAR_MASIVO = "AGREGAR_MASIVO";
        public static final String CARGAR_ARCHIVO = "CARGAR_ARCHIVO";
        public static final String FOLIO_CONFIRMACION_MASIVO = "FOLIO_CONFIRMACION_MASIVO";
        public static final String TRASLADO_CONFIRMACION_MASIVO = "TRASLADO_CONFIRMACION_MASIVO";
        public static final String TRASLADAR_MASIVO = "TRASLADAR_MASIVO";
        public static final String ARCHIVO_ORIGEN = "ARCHIVO_ORIGEN";
        public static final String CONSULTAR_ARCHIVOS = "CONSULTAR_ARCHIVOS";
        public static final String DESCARGAR_ARCHIVO = "DESCARGAR_ARCHIVO";
        public String RUTA_DESTINO_ARCHIVO = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO);
        public String SO = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.SO);
        private ServiceLocator service = null;
        private ForsetiServiceInterface forseti;
        
	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
             /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se agregan constantes para el proceso de traslado de folios masivos
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
                boolean isMultipart = FileUpload.isMultipartContent(request);
                String accion = "";
                if(isMultipart)
                {
                    return agregarMatriculasMasivo(request);
                }
                else{
                    accion = request.getParameter(WebKeys.ACCION).trim();
                }
		HttpSession session = request.getSession();

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_DEPARTAMENTO)) {
			return adicionaDepartamento(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_DEPARTAMENTO)) {
			return eliminaDepartamento(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_DEPARTAMENTO)) {
			return seleccionaDepartamento(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_MUNICIPIO)) {
			return adicionaMunicipio(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_MUNICIPIO)) {
			return eliminaMunicipio(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_MUNICIPIO)) {
			return seleccionaMunicipio(request);
		} else if (accion.equals(AWAdministracionForseti.TERMINA_MUNICIPIO)) {
			session.removeAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
			session.removeAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
			return null;
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_VEREDA)) {
			return adicionaVereda(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_VEREDA)) {
			return eliminaVereda(request);
		} else if (accion.equals(AWAdministracionForseti.TERMINA_VEREDA)) {
			session.removeAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);
			session.removeAttribute(AWAdministracionForseti.MAP_VEREDAS);
			return null;
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_CIRCULO_REGISTRAL)) {
			return adicionaCirculoRegistral(request);
		} else if (accion.equals(AWAdministracionForseti.EDITA_CIRCULO)) {
			return editaCirculo(request);
		} else if (accion.equals(AWAdministracionForseti.EDITA_CIRCULO_NIT)) {
			return editaNITCirculo(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_ESTADO_FOLIO)) {
			return adicionaEstadoFolio(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_ESTADO_FOLIO)) {
			return eliminaEstadoFolio(request);
		} else if (accion.equals(AWAdministracionForseti.TERMINA)) {
			return limpiarSesion(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_TIPO_OFICINA)) {
			return adicionaTipoOficina(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_TIPO_OFICINA)) {
			return eliminaTipoOficina(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_TIPO_DOCUMENTO)) {
			return adicionaTipoDocumento(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_TIPO_DOCUMENTO)) {
			return eliminaTipoDocumento(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_TIPO_PREDIO)) {
			return adicionaTipoPredio(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_TIPO_PREDIO)) {
			return eliminaTipoPredio(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_GRUPO_NAT_JURIDICA_ANOTACIONES)) {
			return adicionaGrupoNatJuridica(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_GRUPO_NAT_JURIDICA_ANOTACIONES)) {
			return eliminaGrupoNatJuridica(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_GRUPO_NAT_JURIDICA_ANOTACIONES)) {
			return seleccionaGrupoNatJuridica(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_NATURALEZA_JURIDICA)) {
			return seleccionaNatJuridica(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_TIPO_NATURALEZA_JURIDICA)) {
			return adicionaTipoNatJuridica(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_TIPO_NATURALEZA_JURIDICA)) {
			return eliminaTipoNatJuridica(request);
		}
		
		//Edita tipo de naturaleza jurídica
		else if (accion.equals(AWAdministracionForseti.EDITA_TIPO_NATURALEZA_JURIDICA)) {
			return editaTipoNatJuridica(request);
		}
		
		//Edita atributos de un tipo de naturaleza jurídica
		else if (accion.equals(AWAdministracionForseti.EDITA_DETALLES_NATURALEZA_JURIDICA)) {
			return editaDetallesNatJuridica(request);
		}
		
		//Terminar edición naturalezas jurídica
		else if (accion.equals(AWAdministracionForseti.TERMINA_EDICION_NATURALEZA)) {
			return null;
		}
		
		
		 else if (accion.equals(AWAdministracionForseti.TERMINA_TIPO_NATURALEZA_JURIDICA)) {
			return limpiarSesion(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_CIRCULO_REGISTRAL)) {
			return adicionaCirculoRegistral(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_CIRCULO_REGISTRAL)) {
			return eliminaCirculoRegistral(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_ESTADO_ANOTACION)) {
			return adicionaEstadoAnotacion(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_ESTADO_ANOTACION)) {
			return eliminaEstadoAnotacion(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_DOMINIO_NAT_JURIDICA)) {
			return adicionaDominioNaturalezaJuridica(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_DOMINIO_NAT_JURIDICA)) {
			return eliminaDominioNaturalezaJuridica(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_CIRCULO_FESTIVO)) {
			return adicionaCirculoFestivo(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_CIRCULO_FESTIVO)) {
			return eliminaCirculoFestivo(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_CIRCULO_FESTIVO)) {
			return seleccionaCirculoFestivo(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_EJE)) {
			return adicionaEje(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_EJE)) {
			return eliminaEje(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_ZONA_REGISTRAL)) {
			return adicionaZonaRegistral(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_ZONA_REGISTRAL)) {
			return eliminaZonaRegistral(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL)) {
			return seleccionaZonaRegistral(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_TRASLADO)) {
			return seleccionaZonaRegistral(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_DEPTO)) {
			return seleccionaZonaRegistralDepto(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_TRASLADO_DEPTO)) {
			return seleccionaZonaRegistralDepto(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO)||
			(accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_DEPTO_NOTARIA))) {
			session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
			salvarDatosOficinaOrigenEnSesion(request);
			return seleccionaZonaRegistralDepto(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_MUNICIPIO)) {
			return seleccionaZonaRegistralMunicipio(request);
		} else if (
			accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_TRASLADO_MUNICIPIO)) {
			return seleccionaZonaRegistralMunicipio(request);
		} else if (
			accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_OFICINA_ORIGEN_MUNICIPIO)) {
			session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
			salvarDatosOficinaOrigenEnSesion(request);
			return seleccionaZonaRegistralMunicipio(request);
		} else if (accion.equals(AWAdministracionForseti.TRASLADAR)) {
			return trasladar(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_OFICINA_ORIGEN)) {
			return adicionaOficinaOrigen(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_OFICINA_ORIGEN_NOTARIA)) {
			return adicionaOficinaOrigenNotaria(request);
		} else if (accion.equals(AWAdministracionForseti.CONSULTA_OFICINA_ORIGEN_POR_TIPO_OFICINA)){
			return consultaOficinasOrigenCategoria(request);
		} else if (accion.equals(AWAdministracionForseti.CONSULTA_OFICINA_ORIGEN_POR_VEREDA)||
				(accion.equals(AWAdministracionForseti.CONSULTA_OFICINA_ORIGEN_POR_VEREDA_NOTARIA))) {
			session.removeAttribute(LISTA_OFICINAS_POR_VEREDA);
			return consultaOficinaOrigenByVereda(request);
		} else if (accion.equals(AWAdministracionForseti.PROCESO_CATASTRO)) {
			return catastro(request);
		} else if (accion.equals(AWAdministracionForseti.CARGAR_PLANTILLAS_CERTIFICADOS)) {
			return cargarTiposPlantilla(request);
		} else if (accion.equals(AWAdministracionForseti.CARGAR_PLANTILLA)) {
			return cargarPlantilla(request);
		} else if (accion.equals(AWAdministracionForseti.EDITAR_PLANTILLAS_CERTIFICADOS)) {
			return editarPlantilla(request);
		}  else if (accion.equals(AWAdministracionForseti.REABRIR_FOLIO)) {
			return reabrirFolio(request);
		}  else if ((accion.equals(AWAdministracionForseti.EDITAR_OFICINA_ORIGEN)) || (accion.equals(AWAdministracionForseti.EDITAR_OFICINA_ORIGEN_NOTARIA))) {
			return consultarOficinaEdicion(request);
		}  else if (accion.equals(AWAdministracionForseti.ELIMINAR_OFICINA_ORIGEN)||(accion.equals(AWAdministracionForseti.ELIMINAR_OFICINA_ORIGEN_NOTARIA))) {
			return eliminarOficinaOrigen(request);
		}  else if (accion.equals(AWAdministracionForseti.ENVIAR_OFICINA_EDICION)||
				(accion.equals(AWAdministracionForseti.ENVIAR_OFICINA_EDICION_NOTARIA))) {
			return edicionOficinaOrigen(request);
		}	else if (accion.equals(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO)){
			return listarDepartamentosCirculo(request);
		} else if(accion.equals(AWAdministracionForseti.INHABILITAR_CIRCULO_REGISTRAL)){
			return inhabilitarCirculo(request);
		} else if (accion.equals(AWAdministracionForseti.TERMINAR_CIRCULO_INHABILITAR)){
			return terminarCirculoInhabilitar(request);
		} else if (accion.equals(AWAdministracionForseti.VOLVER_CIRCULO_INHABILITAR)){
			return volverCirculoInhabilitar(request);
		} else if (accion.equals(AWAdministracionForseti.VOLVER_INHAB_CIRCULO_ZONAS)){
			return volverInhabCirculoZonas(request);
		} else if (accion.equals(AWAdministracionForseti.ADICIONA_ZONA_REGISTRAL_CIRC_INHAB)){
			return adicionaZonaCirculoInhab(request);
		} else if (accion.equals(AWAdministracionForseti.TERMINA_ZONA_CIRC_INHAB)){
			return terminaZonaCircInhab(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_DEPTO_INHAB)){
			return seleccionaZonaRegistralDepto(request);
		} else if (accion.equals(AWAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_MUNICIPIO_INHAB)){
			return seleccionaZonaRegistralMunicipio(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINA_ZONA_REGISTRAL_INHAB)){
			return eliminaZonaRegistralInhab(request);
		} else if (accion.equals(AWAdministracionForseti.USUARIOS_CREAR_INHAB)){
			return usuariosCrearInhab(request);
		} else if (accion.equals(AWAdministracionForseti.USUARIOS_MOVER)){
			return usuariosMover(request);
		} else if (accion.equals(AWAdministracionForseti.VOLVER_USUARIO_CIRCULO_INHAB)){
			return volverUsuarioCirculoInhab(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINAR_USUARIOS_CREADOS)){
			return eliminarUsuariosCreados(request);
		} else if (accion.equals(AWAdministracionForseti.ELIMINAR_USUARIOS_TRASLADADOS)){
			return eliminarUsuariosTrasladados(request);
		} else if (accion.equals(AWAdministracionForseti.TERMINAR_USUARIO_CIRCULO_INHAB)){
			return terminarUsuarioCirculoInhabilitar(request);
		} else if (accion.equals(AWAdministracionForseti.VOLVER_USUARIO_ROLCONSULTA)){
			return volverUsuarioRolConsulta(request);
		} else if (accion.equals(AWAdministracionForseti.TERMINAR_PROCESO_CIRCULO_INHAB)){
			return terminarProcesoCirculoInhabilitar(request);
		} else if (accion.equals(AWAdministracionForseti.CARGAR_CIRCULOS_INHABILITADOS)){
			return cargarCirculosInhabilitados(request);
		} else if (accion.equals(AWAdministracionForseti.TRASLADAR_FOLIOS)){
			return trasladarFolios(request);
		}
                 /*
                  * @author      :   Julio Alcázar Rivas
                  * @change      :   Nuevo navegacion del metodo de traslado
                  * Caso Mantis  :   07123
                  */
                else if (accion.equals(AWAdministracionForseti.AGREGAR)) {
		return agregarMatricula(request);
                }
                /**
                 * @author     : Ellery David Robles Gómez
                 * @change     : Metodo que agrega los fundamentos seleccionados a una lista
                 * Caso Mantis : 14985
                 */
                else if (accion.equals(AWAdministracionForseti.AGREGAR_FUNDAMENTO) || 
                         accion.equals(AWAdministracionForseti.AGREGAR_FUNDAMENTO_MASIVO) ||
                         accion.equals(AWAdministracionForseti.AGREGAR_FUNDAMENTO_CONFIRMACION)) {
                    agregarFundamento(request);
                }
                else if (accion.equals(AWAdministracionForseti.AGREGAR_FUNDAMENTO_MASIVO+"_DESTINO")) {
                    agregarFundamentoDestino(request);
                }
                /**
                 * @author     : Ellery David Robles Gómez
                 * @change     : Metodo que elimina fundamentos seleccionados a una lista
                 * Caso Mantis : 14985
                 */
                else if (accion.equals(AWAdministracionForseti.ELIMINAR_FUNDAMENTO) || 
                         accion.equals(AWAdministracionForseti.ELIMINAR_FUNDAMENTO_MASIVO)||
                         accion.equals(AWAdministracionForseti.ELIMINAR_FUNDAMENTO_CONFIRMACION)) {
                    eliminarFundamento(request);
                }else if (accion.equals(AWAdministracionForseti.ELIMINAR_FUNDAMENTO_MASIVO+"_DESTINO")) {
                    eliminarFundamentoDestino(request);
                }
                /*
                  * @author      :   Carlos Mario Torres Urina
                  * @change      :   Nuevo navegacion del metodo de traslado masivo
                  * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
                  */
                else if (accion.equals(AWAdministracionForseti.AGREGAR_MASIVO)) {
                    return agregarMatriculasMasivo(request);
                }
                else if (accion.equals(AWAdministracionForseti.ONSELECT_CIRCULO_ORIGEN) || accion.equals(AWAdministracionForseti.ONSELECT_CIRCULO_DESTINO)) {
                    try {
                        salvarDatosTrasladoMasivo(request);
                        return seleccionaMunicipioPor(request);
                    } catch (ServicioNoEncontradoException ex) {
                        Logger.getLogger(AWAdministracionForseti.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Throwable ex) {
                        Logger.getLogger(AWAdministracionForseti.class.getName()).log(Level.SEVERE, null, ex);
                    }  
                }
                else if (accion.equals(AWAdministracionForseti.ONSELECT_MUNICIPIO_ORIGEN) || accion.equals(AWAdministracionForseti.ONSELECT_MUNICIPIO_DESTINO)) {
                    try {
                        salvarDatosTrasladoMasivo(request);
                        return seleccionaVeredaPor(request);
                    } catch (ServicioNoEncontradoException ex) {
                        Logger.getLogger(AWAdministracionForseti.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Throwable ex) {
                        Logger.getLogger(AWAdministracionForseti.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if (accion.equals(AWAdministracionForseti.ONSELECT_VEREDA_ORIGEN)) {
                        salvarDatosTrasladoMasivo(request);
                        return null;
                }else if (accion.equals(AWAdministracionForseti.TRASLADO_CONFIRMACION_MASIVO)) {
                        return trasladoConfirmacionMasivo(request);
                }                
                else if (accion.equals(AWAdministracionForseti.TRASLADAR_MASIVO)) {
                        return trasladarMasivo(request);
                }
                else if (accion.equals(AWAdministracionForseti.CONSULTAR_ARCHIVOS)) {
                        salvarDatosTrasladoMasivo(request);
                        return consultarArchivosMasivo(request);
                }
                else if (accion.equals(AWAdministracionForseti.DESCARGAR_ARCHIVO)) {
                        salvarDatosTrasladoMasivo(request);
                        return descargarArchivoMasivo(request);
                }
                else if (accion.equals(AWAdministracionForseti.ELIMINAR)) {
                        return eliminarMatricula(request);
                }
                /*
                 * @author      : Julio Alcázar Rivas
                 * @change      : nuevos procesos de traslados
                 * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                 */
                else if (accion.equals(AWAdministracionForseti.FOLIO_CONFIRMACION)) {
                        return folioConfirmacion(request);
                }else if (accion.equals(AWAdministracionForseti.TRASLADO_CONFIRMACION)) {
                        return trasladoConfirmacion(request);
                }else if (accion.equals(AWAdministracionForseti.CANCELAR_SOLICITUD)) {
                        return cancelarsolicitud();
                }


		return null;
	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento trasladarFolios(HttpServletRequest request) throws AccionWebException{
		ValidacionParametrosException exception=new ValidacionParametrosException();
		String idCirculoOrigen = request.getParameter(CCirculoTraslado.CIRCULO_ORIGEN);
		String idCirculoDestino = request.getParameter(CCirculoTraslado.CIRCULO_DESTINO);
		String desde=request.getParameter(CCirculoTraslado.CIRCULO_ORIGEN_DESDE);
		String hasta=request.getParameter(CCirculoTraslado.CIRCULO_ORIGEN_HASTA);
		
		
		int fin=0;
		int inicio=0;
		try {
			inicio=Integer.parseInt(desde);
		} catch (NumberFormatException e) {
			exception.addError("El campo \"Desde\" debe ser numérico");
		}
		
		try {
			fin=Integer.parseInt(hasta);
		} catch (NumberFormatException e) {
			exception.addError("El campo \"Hasta\" debe ser numérico");
		}
		if (inicio>fin){
			exception.addError("El campo \"Desde\" debe ser menor o igual al campo \"Hasta\"");
		}
		
		if (idCirculoOrigen.equals(WebKeys.SIN_SELECCIONAR)){
			exception.addError("El círculo origen es inválido");
		}
		
		if (idCirculoDestino.equals(WebKeys.SIN_SELECCIONAR)){
			exception.addError("El círculo destino es inválido");
		}
		
		if (!exception.getErrores().isEmpty()){
			throw exception;
		}
		
		Circulo circuloOrigen=new Circulo();
		circuloOrigen.setIdCirculo(idCirculoOrigen);
		
		Circulo circuloDestino=new Circulo();
		circuloDestino.setIdCirculo(idCirculoDestino);
		
		Usuario usuario=(Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnAdministracionForseti evento=new EvnAdministracionForseti(usuario,EvnAdministracionForseti.TRASLADAR_FOLIOS_MASIVO);
		evento.setCirculoOrigen(circuloOrigen);
		evento.setCirculoDestino(circuloDestino);
		
		evento.setTrasladoMasivoInicio(Integer.parseInt(desde));
		evento.setTrasladoMasivoFin(Integer.parseInt(hasta));
		
		return evento;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento cargarCirculosInhabilitados(HttpServletRequest request) throws AccionWebException{
		String circulo=request.getParameter(CCirculoTraslado.CIRCULO_DESTINO);
		Circulo circ=new Circulo();
		circ.setIdCirculo(circulo);
		Usuario usuario=(Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnAdministracionForseti evento=new EvnAdministracionForseti(usuario,EvnAdministracionForseti.CARGAR_CIRCULOS_INHABILITADOS);
		evento.setCirculoDestino(circ);
		request.getSession().setAttribute(CCirculoTraslado.CIRCULO_DESTINO,circulo);
		return evento;
	}

	private EvnAdministracionForseti terminarProcesoCirculoInhabilitar(HttpServletRequest request) throws AccionWebException{
		List usuariosTrasladados=(List)request.getSession().getAttribute(AWAdministracionForseti.LISTA_USUARIOS_TRASLADAR);
		List usuariosCreados=(List)request.getSession().getAttribute(AWAdministracionForseti.LISTA_USUARIOS_CREADOS);
		Map rolesUsu=(Map)request.getSession().getAttribute(AWAdministracionForseti.ROLES_USUARIOS_CIRCULO_INHAB);
		List zonas=(List)request.getSession().getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES_INHABILITA);
		Circulo circuloInhab=(Circulo)request.getSession().getAttribute(AWAdministracionForseti.CIRCULO_INHABILITADO);
		Circulo circuloDestino=(Circulo)request.getSession().getAttribute(AWAdministracionForseti.CIRCULO_DESTINO);
		
		String[] usuariosRol = request.getParameterValues(AWAdministracionForseti.USUARIO_CONSULTA);
		List crearRolUsuarios=new ArrayList();
		if (usuariosRol!=null){
			for (int i=0; i<usuariosRol.length; i++){
				String username = usuariosRol[i];
				boolean encontrado=false;
				if (usuariosTrasladados!=null){
					Iterator itTrasl=usuariosTrasladados.iterator();
					while(itTrasl.hasNext()&&!encontrado){
						gov.sir.core.negocio.modelo.Usuario usu=(gov.sir.core.negocio.modelo.Usuario)itTrasl.next();
						if (usu.getUsername().equals(username)){
							crearRolUsuarios.add(usu);
							encontrado=true;
						}
					}	
				}
				
				if (!encontrado && usuariosCreados!=null){
					Iterator itCrear=usuariosCreados.iterator();
					while(itCrear.hasNext()&&!encontrado){
						gov.sir.core.negocio.modelo.Usuario usu=(gov.sir.core.negocio.modelo.Usuario)itCrear.next();
						if (usu.getUsername().equals(username)){
							crearRolUsuarios.add(usu);
							encontrado=true;
						}
					}	
				}
			}	
		}
		
		Usuario usuario=(Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnAdministracionForseti evento=new EvnAdministracionForseti(usuario,EvnAdministracionForseti.INHABILITAR_CIRCULO);
		evento.setUsuariosTrasladar(usuariosTrasladados);
		evento.setUsuariosCrear(rolesUsu);
		evento.setZonasRegistrales(zonas);
		evento.setUsuariosAsignarRolConsulta(crearRolUsuarios);
		evento.setCirculoOrigen(circuloInhab);
		evento.setCirculoDestino(circuloDestino);
		return evento;
	}
	
	private EvnAdministracionForseti terminarUsuarioCirculoInhabilitar(HttpServletRequest request) throws AccionWebException{
		request.getSession().removeAttribute(CUsuario.LOGIN_USUARIO);
		request.getSession().removeAttribute(CUsuario.CLAVE_USUARIO);
		request.getSession().removeAttribute(CUsuario.NOMBRE_USUARIO);
		request.getSession().removeAttribute(CUsuario.APELLIDO1_USUARIO);
		request.getSession().removeAttribute(CUsuario.APELLIDO2_USUARIO);
		
		return null;
	}
	
	private EvnAdministracionForseti volverUsuarioRolConsulta(HttpServletRequest request) throws AccionWebException{
		return null;
	}
	
	private EvnAdministracionForseti eliminarUsuariosCreados(HttpServletRequest request) throws AccionWebException{
		List usuariosTras=(List)request.getSession().getAttribute(AWAdministracionForseti.LISTA_USUARIOS_CREADOS);
		String user=request.getParameter(AWAdministracionForseti.USUARIOS_MOVER);
		Iterator itUsu=usuariosTras.iterator();
		gov.sir.core.negocio.modelo.Usuario toDel=null;
		while(itUsu.hasNext()&&toDel==null){
			gov.sir.core.negocio.modelo.Usuario usuario=(gov.sir.core.negocio.modelo.Usuario)itUsu.next();
			if (usuario.getUsername().equals(user)){
				toDel=usuario;
			}
		}
		if (toDel!=null){
			usuariosTras.remove(toDel);
		}
		return null;
	}
	
	private EvnAdministracionForseti eliminarUsuariosTrasladados(HttpServletRequest request) throws AccionWebException{
		List usuariosTras=(List)request.getSession().getAttribute(AWAdministracionForseti.LISTA_USUARIOS_TRASLADAR);
		String user=request.getParameter(AWAdministracionForseti.USUARIOS_MOVER);
		Iterator itUsu=usuariosTras.iterator();
		gov.sir.core.negocio.modelo.Usuario toDel=null;
		while(itUsu.hasNext()&&toDel==null){
			gov.sir.core.negocio.modelo.Usuario usuario=(gov.sir.core.negocio.modelo.Usuario)itUsu.next();
			if (usuario.getUsername().equals(user)){
				toDel=usuario;
			}
		}
		if (toDel!=null){
			List usuarios = (List)request.getSession().getAttribute(AWAdministracionFenrir.LISTA_USUARIOS);
			if (usuarios==null){
				usuarios=new ArrayList();
				request.getSession().setAttribute(AWAdministracionFenrir.LISTA_USUARIOS,usuarios);
			}
			usuariosTras.remove(toDel);
			usuarios.add(toDel);
		}
		return null;
	}
	
	private void validarClave(String clave, ValidacionParametrosException exception) {
		//Pattern p = Pattern.compile("[\\w|\\W]*[0-9]+[\\w\\W]*");
		Pattern p = Pattern.compile(".*[0-9]+.*");
		Matcher m = p.matcher(clave);
		boolean esValida = m.matches();

		if (!esValida) {
			exception.addError("La clave debe tener al menos un número");
		}

		if (clave.length() < 6) {
			exception.addError("La clave debe tener mínimo 6 caracteres.");
		}
	}
	
	private EvnAdministracionForseti usuariosCrearInhab(HttpServletRequest request) throws AccionWebException{
		HttpSession session=request.getSession();
		ValidacionParametrosException exception=new ValidacionParametrosException();
		String login = request.getParameter(CUsuario.LOGIN_USUARIO);
		if ((login == null) || (login.trim().equals(""))) {
			exception.addError("Debe Proporcionar un nombre de usuario.");
		}else{
			session.setAttribute(CUsuario.LOGIN_USUARIO, login);
		}
		String clave = request.getParameter(CUsuario.CLAVE_USUARIO);
		if ((clave == null) || (clave.trim().equals(""))) {
			exception.addError("Debe Proporcionar una clave para el usuario.");		
		}else{
			session.setAttribute(CUsuario.CLAVE_USUARIO, clave);
		}

		validarClave(clave, exception);

		String nombre = request.getParameter(CUsuario.NOMBRE_USUARIO);
		if ((nombre == null) || (nombre.trim().equals(""))) {
			exception.addError("Debe Proporcionar un Nombre.");
		} else{
			session.setAttribute(CUsuario.NOMBRE_USUARIO, nombre);
		}

		String apellido1 = request.getParameter(CUsuario.APELLIDO1_USUARIO);
		if ((apellido1 == null) || (apellido1.trim().equals(""))) {
			exception.addError("Debe Proporcionar el primer Apellido.");
		} else{
			session.setAttribute(CUsuario.APELLIDO1_USUARIO, apellido1);
		}

		String apellido2 = request.getParameter(CUsuario.APELLIDO2_USUARIO);
		if ((apellido2 == null) || (apellido2.trim().equals(""))) {
			exception.addError("Debe Proporcionar el Segundo Apellido.");
		} else{
			session.setAttribute(CUsuario.APELLIDO2_USUARIO, apellido2);
		}
		
		List roles = new ArrayList();
		String[] rolesArray = request.getParameterValues(CRol.ID_ROL);
		if (rolesArray == null || rolesArray.length == 0) {
			exception.addError("Debe Seleccionar al menos un Rol.");
		} else {
			for (int i = 0; i < rolesArray.length; i++) {
				if (rolesArray[i].equals(WebKeys.SIN_SELECCIONAR)) {
					continue;
				}
				Rol rol = new Rol();
				rol.setRolId(rolesArray[i]);
				roles.add(rol);
			}
		}


		List usuariosCreados = (List)session.getAttribute(AWAdministracionForseti.LISTA_USUARIOS_CREADOS);
		if (usuariosCreados==null){
			usuariosCreados=new ArrayList();
			session.setAttribute(AWAdministracionForseti.LISTA_USUARIOS_CREADOS,usuariosCreados);
		}
		
		if (login!=null && !login.trim().equals("")){
			Iterator itUsuarios=usuariosCreados.iterator();
			while(itUsuarios.hasNext()){
				gov.sir.core.negocio.modelo.Usuario usu=(gov.sir.core.negocio.modelo.Usuario)itUsuarios.next();
				if (login.equals(usu.getUsername())){
					exception.addError("Ese nombre de usuario ya está");
					break;
				}
			}	
		}
		
		
		if (!exception.getErrores().isEmpty()) {
			throw exception;
		}

		gov.sir.core.negocio.modelo.Usuario usuario = new gov.sir.core.negocio.modelo.Usuario();
		usuario.setLoginID(login);
		usuario.setUsername(login);
		usuario.setNombre(nombre);
		usuario.setApellido1(apellido1);
		usuario.setApellido2(apellido2);
		usuario.setPassword(clave);
		usuario.setActivo(true);
		
		usuariosCreados.add(usuario);
		
		Map rolesUsu=(Map)request.getSession().getAttribute(AWAdministracionForseti.ROLES_USUARIOS_CIRCULO_INHAB);
		if (rolesUsu==null){
			rolesUsu=new Hashtable();
			request.getSession().setAttribute(AWAdministracionForseti.ROLES_USUARIOS_CIRCULO_INHAB,rolesUsu);
		}
		rolesUsu.put(usuario,roles);
		
		request.getSession().removeAttribute(CUsuario.LOGIN_USUARIO);
		request.getSession().removeAttribute(CUsuario.CLAVE_USUARIO);
		request.getSession().removeAttribute(CUsuario.NOMBRE_USUARIO);
		request.getSession().removeAttribute(CUsuario.APELLIDO1_USUARIO);
		request.getSession().removeAttribute(CUsuario.APELLIDO2_USUARIO);
		
		return null;
	}
	
	private EvnAdministracionForseti usuariosMover(HttpServletRequest request) throws AccionWebException{
		String usuMov = request.getParameter(AWAdministracionForseti.USUARIOS_MOVER);
		List usuarios = (List)request.getSession().getAttribute(AWAdministracionFenrir.LISTA_USUARIOS);
		List usuariosTrasladar=(List)request.getSession().getAttribute(AWAdministracionForseti.LISTA_USUARIOS_TRASLADAR);
		if (usuariosTrasladar==null){
			usuariosTrasladar=new ArrayList();
			request.getSession().setAttribute(AWAdministracionForseti.LISTA_USUARIOS_TRASLADAR,usuariosTrasladar);
		}
		Iterator itUsu=usuarios.iterator();
		gov.sir.core.negocio.modelo.Usuario trasladar=null;
		while(itUsu.hasNext()){
			gov.sir.core.negocio.modelo.Usuario usuario=(gov.sir.core.negocio.modelo.Usuario)itUsu.next();
			if (usuario.getUsername().equals(usuMov)){
				trasladar=usuario;
			}
		}
		if (trasladar!=null){
			usuariosTrasladar.add(trasladar);
			usuarios.remove(trasladar);
		}
		
		return null;
	}
	
	private EvnAdministracionForseti volverUsuarioCirculoInhab(HttpServletRequest request) throws AccionWebException{
		request.getSession().removeAttribute(AWAdministracionForseti.LISTA_USUARIOS_CREADOS);
		request.getSession().removeAttribute(AWAdministracionForseti.LISTA_USUARIOS_TRASLADAR);
		request.getSession().removeAttribute(CUsuario.LOGIN_USUARIO);
		request.getSession().removeAttribute(CUsuario.CLAVE_USUARIO);
		request.getSession().removeAttribute(CUsuario.NOMBRE_USUARIO);
		request.getSession().removeAttribute(CUsuario.APELLIDO1_USUARIO);
		request.getSession().removeAttribute(CUsuario.APELLIDO2_USUARIO);
		return null;
	}
		
	private EvnAdministracionForseti eliminaZonaRegistralInhab(HttpServletRequest request) throws AccionWebException{
		String idVereda = request.getParameter(CVereda.ID_VEREDA);
		String idMunicipio=request.getParameter(CMunicipio.ID_MUNICIPIO);
		String idDepto=request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		
		List zonasAsociadas=(List)request.getSession().getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES_INHABILITA);
		if (zonasAsociadas==null){
			zonasAsociadas=new ArrayList();
		}
		
		List zonas=(List)request.getSession().getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES);
		Iterator itZonas=zonas.iterator();
		ZonaRegistral toDelete=null;
		while(itZonas.hasNext()&&toDelete==null){
				ZonaRegistral zona=(ZonaRegistral)itZonas.next();
				if (zona.getVereda().getIdVereda().equals(idVereda)
					&& zona.getVereda().getIdMunicipio().equals(idMunicipio)
					&& zona.getVereda().getIdDepartamento().equals(idDepto)) {
							
					toDelete=zona;
			}
		}
		
		if (toDelete!=null){
			zonas.remove(toDelete);
			zonasAsociadas.remove(toDelete);
		}
		
		return null;
	}
	
	private Evento volverInhabCirculoZonas(HttpServletRequest request) throws AccionWebException{
		request.getSession().removeAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES);
		request.getSession().removeAttribute(CZonaRegistral.ID_ZONA_REGISTRAL);
		request.getSession().removeAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);
		request.getSession().removeAttribute(AWAdministracionForseti.MAP_VEREDAS);
		request.getSession().removeAttribute(AWAdministracionForseti.LISTA_VEREDAS);
		request.getSession().removeAttribute(CMunicipio.ID_MUNICIPIO);
		request.getSession().removeAttribute(RESOLUCION_TRASLADO);
		return null;
	}
	
	private Evento adicionaZonaCirculoInhab(HttpServletRequest request) throws AccionWebException{
		HttpSession session = request.getSession();

		ValidacionParametrosException exception = new ValidacionParametrosException();

		String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		if ((idDepto == null)
			|| (idDepto.trim().length() == 0)
			|| idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Departamento.");
		} else {
			session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
		}

		String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
		if ((idMunicipio == null)
			|| (idMunicipio.trim().length() == 0)
			|| idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Municipio.");
		} else {
			session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
		}

		String idVereda = request.getParameter(CVereda.ID_VEREDA);
		if ((idVereda == null)
			|| (idVereda.trim().length() == 0)
			|| idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar una Vereda.");
		} else {
			session.setAttribute(CVereda.ID_VEREDA, idVereda);
		}

		String soloIdMunicipio=idMunicipio.substring(idMunicipio.indexOf("-")+1,idMunicipio.length());
		String soloIdDepartamento=idDepto.substring(idDepto.indexOf("-")+1,idDepto.length());
		List regZon = (List)session.getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES);
		
		Iterator itZonas=regZon.iterator();
		while(itZonas.hasNext()){
			ZonaRegistral dato = (ZonaRegistral)itZonas.next();
			if (dato.getVereda().getIdVereda().equals(idVereda)&&dato.getVereda().getIdMunicipio().equals(soloIdMunicipio)&&dato.getVereda().getIdDepartamento().equals(soloIdDepartamento)){
				exception.addError("Esa zona registral ya existe");
				break;
			}
		}
		
		List zonasCircInhab=(List)session.getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES_CIRC_INHAB);
		if (zonasCircInhab!=null){
			for (Iterator itZ=zonasCircInhab.iterator(); itZ.hasNext();){
				ZonaRegistral dato = (ZonaRegistral)itZ.next();
				if (dato.getVereda().getIdVereda().equals(idVereda)&&dato.getVereda().getIdMunicipio().equals(soloIdMunicipio)&&dato.getVereda().getIdDepartamento().equals(soloIdDepartamento)){
					exception.addError("Esa zona registral ya existe");
					break;
				}
			}
		}
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Departamento deptoSeleccionado =
			(Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
		Municipio munSeleccionado =
			(Municipio) session.getAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);

		Vereda vereda = new Vereda();
		
		
		List listaDeptos = (List)session.getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS);
		List listaMunic = (List)session.getAttribute(AWAdministracionForseti.LISTA_MUNICIPIOS);
		Departamento depto=new Departamento();
		Iterator itDeptos=listaDeptos.iterator();
		while(itDeptos.hasNext()){
			ElementoLista elem=(ElementoLista)itDeptos.next();
			if (elem.getId().equals(idDepto)){
				depto.setNombre(elem.getValor());
				depto.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
			}
		}
		
		Municipio municipio=new Municipio();
		Iterator itMunic=listaMunic.iterator();
		while(itMunic.hasNext()){
			ElementoLista elem=(ElementoLista)itMunic.next();
			if (elem.getId().equals(idMunicipio)){
				municipio.setNombre(elem.getValor());
				municipio.setIdMunicipio(munSeleccionado.getIdMunicipio());
			}
		}
		
		Iterator itVereda=((List)session.getAttribute(AWAdministracionForseti.LISTA_VEREDAS)).iterator();
		while(itVereda.hasNext()){
			ElementoLista elem=(ElementoLista)itVereda.next();
			if (elem.getId().equals(idVereda)){
				vereda.setNombre(elem.getValor());
			}
		}
		municipio.setDepartamento(depto);
		vereda.setMunicipio(municipio);
		vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
		vereda.setIdMunicipio(munSeleccionado.getIdMunicipio());
		vereda.setIdVereda(idVereda);

		ZonaRegistral dato = new ZonaRegistral();
		dato.setVereda(vereda);
		regZon.add(dato);
		List zonasAsociadas=(List)request.getSession().getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES_INHABILITA);
		if (zonasAsociadas==null){
			zonasAsociadas=new ArrayList();
		}
		zonasAsociadas.add(dato);
		request.getSession().setAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES_INHABILITA,zonasAsociadas);
		return null;
	}
	
	private Evento terminaZonaCircInhab(HttpServletRequest request) throws AccionWebException{
		Circulo circulo=(Circulo)request.getSession().getAttribute(AWAdministracionForseti.CIRCULO_INHABILITADO);
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnAdministracionFenrir evento =
			new EvnAdministracionFenrir(usuario, EvnAdministracionFenrir.CONSULTAR_USUARIOS_POR_CIRCULO);
		evento.setCirculo(circulo);
		return evento;
	}

	private Evento terminarCirculoInhabilitar(HttpServletRequest request) throws AccionWebException{
		String tipoAsoc=request.getParameter(AWAdministracionForseti.TIPO_ASOCIACION_CIRCULO);
		Circulo circulo=null;
		
		if (tipoAsoc.equals(AWAdministracionForseti.TIPO_ASOCIACION_CIRCULO_ASOCIAR)){
			String idCirculo=request.getParameter(AWAdministracionForseti.CIRCULO_DESTINO_ASOCIAR);
			Iterator itCirculos=((List)request.getSession().getServletContext().getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES)).iterator();
			while(itCirculos.hasNext()){
				Circulo circ=(Circulo)itCirculos.next();
				if (circ.getIdCirculo().equals(idCirculo)){
					circulo = circ;
					break;
				}
			}
			
		}else {
			String id = request.getParameter(CCirculo.ID_CIRCULO);
			if ((id == null) || (id.trim().length() == 0)) {
				throw new AccionInvalidaException("Debe Proporcionar un Código para el Círculo Registral.");
			}

			String nombre = request.getParameter(CCirculo.NOMBRE_CIRCULO);
			if ((nombre == null) || (nombre.trim().length() == 0)) {
				throw new AccionInvalidaException("Debe Proporcionar un nombre para el Círculo Registral.");
			}

			String impuesto = request.getParameter(CCirculo.IMPUESTO_CIRCULO);
			if ((impuesto == null) || (impuesto.trim().length() == 0)) {
				throw new AccionInvalidaException("Debe Proporcionar el tipo de impuesto para el Círculo Registral.");
			}
			
			Circulo dato = new Circulo();
			dato.setIdCirculo(id);
			dato.setNombre(nombre);
			dato.setCobroImpuesto(impuesto.equals(CCirculo.IMPUESTO_CIRCULO_SI) ? true : false);
			String nit = request.getParameter(CCirculo.NIT_CIRCULO);
			if (nit!=null && !nit.equals("")){
				dato.setNit(nit);
			}
			circulo = dato;
		}
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnAdministracionForseti evento=new EvnAdministracionForseti(usuario,EvnAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_INHAB);
		if (tipoAsoc.equals(AWAdministracionForseti.TIPO_ASOCIACION_CIRCULO_ASOCIAR)){
			evento.setCirculo(circulo);	
		}else{
			evento.setCirculo(null);
		}
		evento.setCirculoInhabilitado((Circulo)request.getSession().getAttribute(AWAdministracionForseti.CIRCULO_INHABILITADO));
		request.getSession().setAttribute(AWAdministracionForseti.CIRCULO_DESTINO,circulo);
		return evento;
		
	}
	
	private Evento volverCirculoInhabilitar(HttpServletRequest request) throws AccionWebException{
		request.getSession().removeAttribute(AWAdministracionForseti.CIRCULO_INHABILITADO);
		request.getSession().removeAttribute(AWAdministracionForseti.CIRCULO_DESTINO_ASOCIAR);
		request.getSession().removeAttribute(CCirculo.ID_CIRCULO);
		request.getSession().removeAttribute(CCirculo.NOMBRE_CIRCULO);
		request.getSession().removeAttribute(CCirculo.NIT_CIRCULO);
		request.getSession().removeAttribute(CCirculo.IMPUESTO_CIRCULO); 
		return null;
	}
	
	private Evento inhabilitarCirculo(HttpServletRequest request) throws AccionWebException{
		String id = request.getParameter(CCirculo.ID_CIRCULO);

		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Circulo dato = new Circulo();
		dato.setIdCirculo(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_CIRCULO_REGISTRAL);
		evento.setCirculo(dato);
		
		request.getSession().setAttribute(AWAdministracionForseti.CIRCULO_INHABILITADO,dato);
		return null;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento adicionaOficinaOrigenNotaria(HttpServletRequest request) throws AccionWebException{

		HttpSession session = request.getSession();
		salvarDatosOficinaOrigenEnSesion(request);
		String idCategoriaNotaria=null;

		ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();

		String nombre = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_NOMBRE);
		if ((nombre == null)||(nombre.equals(""))) {
			exception.addError("Debe Proporcionar un Nombre para la oficina.");
		}

		String numero = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_NUMERO);
		if ((numero == null)||(numero.equals(""))) {
			exception.addError("Debe Proporcionar un Número para la oficina.");
		}

		String exento =
			(String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_EXENTO_DERECHO_NOTARIAL);

		String descripcion =
			(String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_DESCRIPCION_NAT_JURIDICA);

		String telefono = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_TELEFONO);

		String direccion = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_DIRECCION);

		String fax = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_FAX);

		String email = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_EMAIL);

		String funcionario = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_FUNCIONARIO);

		String idTipoOficina = (String) session.getAttribute(CTipoOficina.ID_TIPO_OFICINA);
		if ((idTipoOficina == null)
			|| (idTipoOficina.trim().length() == 0)
			|| idTipoOficina.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Tipo de oficina.");
		} else {
			session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, idTipoOficina);
		}

		List listaCategorias = new ArrayList();
		if (idTipoOficina.equals(CTipoOficina.TIPO_OFICINA_NOTARIA)) {
			//TODO: VALIDACION DATOS OBLIGATORIOS OFICINA
			String [] categorias = request.getParameterValues(CCategoria.ID_CATEGORIA);
			if (categorias != null) {
				for (int i = 0; i < categorias.length; i++) {
					Categoria categoria = new Categoria();
					categoria.setIdCategoria(categorias[i]);
					listaCategorias.add(categoria);
				}
			}
			
			
			idCategoriaNotaria = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_ID_CATEGORIA);
			if ((idCategoriaNotaria == null)
				|| (idCategoriaNotaria.trim().length() == 0)
				|| idCategoriaNotaria.equals(WebKeys.SIN_SELECCIONAR)) {
				exception.addError("Debe Seleccionar una Categoría para la Notaría.");
			} else {
				session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_ID_CATEGORIA, idCategoriaNotaria);
			}
			
			
		}

		String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		if ((idDepto == null)
			|| (idDepto.trim().length() == 0)
			|| idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Departamento.");
		} else {
			session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
		}

		String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
		session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
		if ((idMunicipio == null)
			|| (idMunicipio.trim().length() == 0)
			|| idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Municipio.");
		} else {
			session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
		}
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		Map municipios = (Map) session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
		
		Municipio municipio = (Municipio) municipios.get(idMunicipio);
		
		List veredas=municipio.getVeredas();
		Iterator itVeredas=veredas.iterator();
		String idVereda=null;
		while(itVeredas.hasNext()){
			Vereda vereda=(Vereda)itVeredas.next();
			if (vereda.isCabecera()){
				idVereda=vereda.getIdVereda();
			}
		}
		
		if ((idVereda == null)
			|| (idVereda.trim().length() == 0)
			|| idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("No se encontró la cabecera municipal");
		} else {
			session.setAttribute(CVereda.ID_VEREDA, idVereda);
		}
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Departamento deptoSeleccionado =
			(Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
		

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Vereda vereda = new Vereda();
		vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
		vereda.setIdMunicipio(municipio.getIdMunicipio());
		vereda.setIdVereda(idVereda);

		TipoOficina tipoOficina = new TipoOficina();
		tipoOficina.setIdTipoOficina(idTipoOficina);

		OficinaOrigen dato = new OficinaOrigen();
		dato.setNombre(nombre);
		dato.setNumero(numero);
		dato.setExentoDerechoNotarial((exento == null) ? false : true);
		dato.setNaturalezaJuridicaEntidad(descripcion);
		dato.setTipoOficina(tipoOficina);
		dato.setVereda(vereda);
		dato.setTelefono(telefono);
		dato.setDireccion(direccion);
		dato.setFax(fax);
		dato.setEncargado(funcionario);
		CategoriaNotaria notCat = new CategoriaNotaria();
		notCat.setIdCategoria(idCategoriaNotaria);
		dato.setCategoriaNotaria(notCat);
		dato.setEmail(email);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_OFICINA_ORIGEN_NOTARIA);
		evento.setOficinaOrigen(dato);
		evento.setVereda(vereda);
		evento.setCategorias(listaCategorias);
		return evento;

	}


	/**
	 * @param request
	 * @return
	 */
	private Evento eliminarOficinaOrigen(HttpServletRequest request) throws AccionWebException{
		
		request.getSession().getAttribute(AWAdministracionForseti.OFICINA_PARA_EDITAR);
		
		ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();

		String idDepto = (String)request.getSession().getAttribute(CDepartamento.ID_DEPARTAMENTO);
		if ((idDepto == null)
			|| (idDepto.trim().length() == 0)
			|| idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Departamento.");
		} 

		String idMunicipio = (String)request.getSession().getAttribute(CMunicipio.ID_MUNICIPIO);
		if ((idMunicipio == null)
			|| (idMunicipio.trim().length() == 0)
			|| idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Municipio.");
		}

		Map municipios = (Map) request.getSession().getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
		Municipio municipio = (Municipio) municipios.get(idMunicipio);
		
		List veredas=municipio.getVeredas();
		Iterator itVeredas=veredas.iterator();
		String idVereda=null;
		while(itVeredas.hasNext()){
			Vereda vereda=(Vereda)itVeredas.next();
			if (vereda.isCabecera()){
				idVereda=vereda.getIdVereda();
			}
		}
		
		if ((idVereda == null)
			|| (idVereda.trim().length() == 0)
			|| idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("No se encontró la cabecera municipal");
		} 

		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		
		Departamento deptoSeleccionado =
			(Departamento) request.getSession().getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
		

		Vereda vereda = new Vereda();
		vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
		vereda.setIdMunicipio(municipio.getIdMunicipio());
		vereda.setIdVereda(idVereda);
		
		String idOficina = request.getParameter(CTipoOficina.ID_TIPO_OFICINA);
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                String oficinaVersion = request.getParameter(CTipoOficina.OFICINA_ORIGEN_VERSION);
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnAdministracionForseti evento = new EvnAdministracionForseti(usuario,EvnAdministracionForseti.ELIMINAR_OFICINA_ORIGEN);
		evento.setIdOficinaOrigen(idOficina);
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                evento.setVersion(Long.parseLong(oficinaVersion));
		evento.setVereda(vereda);
		return evento;
	}


	/**
	 * @param request
	 * @return
	 */
	private Evento consultaOficinasOrigenCategoria(HttpServletRequest request)throws AccionWebException {
		HttpSession session = request.getSession();
		salvarDatosOficinaOrigenEnSesion(request);
		String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
		String idCategoria = request.getParameter(CTipoOficina.ID_TIPO_OFICINA);
		ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();
		if (idCategoria != null && !idCategoria.equals(WebKeys.SIN_SELECCIONAR) && idDepto!=null && !idDepto.equals(WebKeys.SIN_SELECCIONAR)&& idMunicipio != null && !idMunicipio.equals(WebKeys.SIN_SELECCIONAR)){
			session.setAttribute(CTipoOficina.ID_TIPO_OFICINA,idCategoria);
			return consultaOficinaOrigenByVereda(request);
		}
		return null;
	}


	private Evento listarDepartamentosCirculo(HttpServletRequest request) {
		Circulo circulo=(Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		Usuario usuario=(Usuario)request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnAdministracionForseti evento=new EvnAdministracionForseti(usuario, EvnAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO);
		Boolean esNacional=new Boolean(false);
		evento.setCirculo(circulo);
		List roles = (List)request.getSession().getAttribute(WebKeys.LISTA_ROLES);
		Iterator itRoles=roles.iterator();
		while(itRoles.hasNext()){
			Rol rol=(Rol)itRoles.next();
			if (rol.getRolId().equals(CRoles.ADMINISTRADOR_NACIONAL)){
				evento.setCirculo(null);
				esNacional=new Boolean(true);
				break;
			}
		}
		request.getSession().setAttribute(AWAdministracionForseti.ADMINISTRADOR_NACIONAL,esNacional);
		return evento;
	}


	/**
	 * @param request
	 * @return
	 */
	private Evento edicionOficinaOrigen(HttpServletRequest request) throws ValidacionOficinaOrigenException {
		HttpSession session = request.getSession();
		salvarDatosOficinaOrigenEnSesion(request);
		String idCategoriaNotaria=null; 

		ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();

		String nombre = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_NOMBRE);
		if (nombre == null || nombre.equals("")) {
			exception.addError("Debe Proporcionar un Nombre para la oficina.");
		}

		String numero = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_NUMERO);


		String exento =
			(String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_EXENTO_DERECHO_NOTARIAL);

		String descripcion =
			(String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_DESCRIPCION_NAT_JURIDICA);

		String telefono = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_TELEFONO);
		
		String direccion = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_DIRECCION);

		String fax = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_FAX);

		String email = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_EMAIL);

		String funcionario = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_FUNCIONARIO);

		String idTipoOficina = (String) session.getAttribute(CTipoOficina.ID_TIPO_OFICINA);
		
		String idOficina = (String)session.getAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);
                 /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
		String oficinaVersion = (String)session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_VERSION);
                /*
                *  @author Fernando Padilla Velez
                *  @chage  1203.AJUSTE.VERSION.CATEGORIA.REPARTO.NOTARIAL,
                */
                String oficinaIdUnico = (String)session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_ID_UNICO);
		if ((idTipoOficina == null)
			|| (idTipoOficina.trim().length() == 0)
			|| idTipoOficina.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Tipo de oficina.");
		} else {
			session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, idTipoOficina);
		}

		List listaCategorias = new ArrayList();
		if (idTipoOficina.equals(CTipoOficina.TIPO_OFICINA_NOTARIA)) {
			//TODO: VALIDACION DATOS OBLIGATORIOS OFICINA
			String [] categorias = request.getParameterValues(CCategoria.ID_CATEGORIA);
			if (categorias != null) {
				for (int i = 0; i < categorias.length; i++) {
					Categoria categoria = new Categoria();
					categoria.setIdCategoria(categorias[i]);
					listaCategorias.add(categoria);
				}
			}
		}
		//Si es la edición de notaría, hace la validación de la categoria
		if(request.getParameter(WebKeys.ACCION).
				equals(AWAdministracionForseti.ENVIAR_OFICINA_EDICION_NOTARIA)){
			idCategoriaNotaria = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_ID_CATEGORIA);
			if ((idCategoriaNotaria == null)
				|| (idCategoriaNotaria.trim().length() == 0)
				|| idCategoriaNotaria.equals(WebKeys.SIN_SELECCIONAR)) {
				exception.addError("Debe Seleccionar una Categoría para la Notaría.");
			} else {
				session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_ID_CATEGORIA, idCategoriaNotaria);
			}		
		}
		

		String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		if ((idDepto == null)
			|| (idDepto.trim().length() == 0)
			|| idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Departamento.");
		} else {
			session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
		}

		String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
		if ((idMunicipio == null)
			|| (idMunicipio.trim().length() == 0)
			|| idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Municipio.");
		} else {
			session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
		}

		Map municipios = (Map) session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
		Municipio municipio = (Municipio) municipios.get(idMunicipio);
		
		List veredas=municipio.getVeredas();
		Iterator itVeredas=veredas.iterator();
		String idVereda=null;
		while(itVeredas.hasNext()){
			Vereda vereda=(Vereda)itVeredas.next();
			if (vereda.isCabecera()){
				idVereda=vereda.getIdVereda();
			}
		}
		
		if ((idVereda == null)
			|| (idVereda.trim().length() == 0)
			|| idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar una Vereda.");
		} else {
			session.setAttribute(CVereda.ID_VEREDA, idVereda);
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Departamento deptoSeleccionado =
			(Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
		
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Vereda vereda = new Vereda();
		vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
		vereda.setIdMunicipio(municipio.getIdMunicipio());
		vereda.setIdVereda(idVereda);

		TipoOficina tipoOficina = new TipoOficina();
		tipoOficina.setIdTipoOficina(idTipoOficina);

		OficinaOrigen dato = new OficinaOrigen();
		dato.setIdOficinaOrigen(idOficina);
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
		dato.setVersion(Long.parseLong(oficinaVersion));
                /*
                *  @author Fernando Padilla Velez
                *  @chage  1203.AJUSTE.VERSION.CATEGORIA.REPARTO.NOTARIAL,
                *          Se agrega en sesion el id unico de la oficina origen.
                */
                dato.setIdUnico(oficinaIdUnico);
                dato.setNombre(nombre);
		dato.setNumero(numero);
                /*
                *  @author Fernando Padilla Velez
                *  @chage  1203.AJUSTE.VERSION.CATEGORIA.REPARTO.NOTARIAL,
                *          Se corrige el error, siempre guardaba true.
                */
		dato.setExentoDerechoNotarial((exento == null) ? false : 
                        (exento.equals("TRUE")? true : false));
		dato.setNaturalezaJuridicaEntidad(descripcion);
		dato.setTipoOficina(tipoOficina);
		dato.setVereda(vereda);
		dato.setTelefono(telefono);
		dato.setDireccion(direccion);
		dato.setFax(fax);
		dato.setEncargado(funcionario);
		dato.setEmail(email);
		CategoriaNotaria notCat = new CategoriaNotaria();
		notCat.setIdCategoria(idCategoriaNotaria);
		dato.setCategoriaNotaria(notCat);
		dato.setIdOficinaOrigen((String)session.getAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN));

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.EDITAR_OFICINA_ORIGEN);
		evento.setOficinaOrigen(dato);
		evento.setVereda(vereda);
		evento.setCategorias(listaCategorias);
		evento.setVereda(vereda);
		return evento;
	}


	/**
	 * @param request
	 * @return
	 */
	private Evento consultarOficinaEdicion(HttpServletRequest request) {
		String idOficina = request.getParameter(CTipoOficina.ID_TIPO_OFICINA);
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                String version =  request.getParameter(CTipoOficina.OFICINA_ORIGEN_VERSION);
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		
		EvnAdministracionForseti evento = new EvnAdministracionForseti(usuario,EvnAdministracionForseti.CONSULTAR_OFICINA_EDITAR);
		evento.setIdOficinaOrigen(idOficina);
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                evento.setVersion(Long.parseLong(version));
		return evento;
	}


	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la creación de un departamento
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del departamento
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaDepartamento(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		
		Circulo circulo=(Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		
		String id = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Departamento.");
		}

		String nombre = request.getParameter(CDepartamento.NOMBRE_DEPARTAMENTO);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre de Departamento.");
		}
		
		List listaDeptos=(List)request.getSession().getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO);
		Iterator itDeptos = listaDeptos.iterator();
		while(itDeptos.hasNext()){
			Departamento depto=(Departamento)itDeptos.next();
			if (depto.getNombre().equalsIgnoreCase(nombre)){
				throw new AccionInvalidaException("El nombre de ese departamento ya existe");
			}
		}
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Departamento departamento = new Departamento();
		departamento.setNombre(nombre);
		departamento.setIdDepartamento(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_DEPARTAMENTO);
		evento.setDepartamento(departamento);
		evento.setCirculo(circulo);
		List roles = (List)request.getSession().getAttribute(WebKeys.LISTA_ROLES);
		Iterator itRoles=roles.iterator();
		while(itRoles.hasNext()){
			Rol rol=(Rol)itRoles.next();
			if (rol.getRolId().equals(CRoles.ADMINISTRADOR_NACIONAL)){
				evento.setCirculo(null);
				break;
			}
		}
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un departamento
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del departamento
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaDepartamento(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		String key = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		if ((key == null) || (key.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código de Departamento.");
		}
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo=(Circulo)session.getAttribute(WebKeys.CIRCULO);
		
		Map departamentos = (Map) context.getAttribute(WebKeys.MAPA_DEPARTAMENTOS);
		Departamento depto = (Departamento) departamentos.get(key);
		
		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_DEPARTAMENTO);
		evento.setDepartamento(depto);
		evento.setCirculo(circulo);
		List roles = (List)request.getSession().getAttribute(WebKeys.LISTA_ROLES);
		Iterator itRoles=roles.iterator();
		while(itRoles.hasNext()){
			Rol rol=(Rol)itRoles.next();
			if (rol.getRolId().equals(CRoles.ADMINISTRADOR_NACIONAL)){
				evento.setCirculo(null);
				break;
			}
		}

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la selección de un departamento
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del departamento
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti seleccionaDepartamento(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		ServletContext contexto = session.getServletContext();

		String key = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		if ((key == null) || (key.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código de Departamento.");
		}
		List departamentos=(List)session.getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO);
		
		Departamento depto = null;
		
		//SE COLOCÓ ESTA CONDICIÓN PORQUE A VECES EL KEY DE DEPARTAMENTO ESTA ASI: "AMAZONAS-91" Y A VECES SOLO ESTA ASI "91"
		//LA IDEA ES QUE SE HAGA LA CONSULTA SIN IMPORTAR EL TIPO DE LLAVE QUE VENGA.
		boolean keyAlfaNumerica = false;
		try{
			int i = new Integer(key).intValue();			
		}catch(Exception e){
			keyAlfaNumerica = true;
		}
		
		
		if(keyAlfaNumerica){
			String ktemp=null;
			Iterator itDeptos=departamentos.iterator();
			while(itDeptos.hasNext()){
				Departamento temp=(Departamento)itDeptos.next();
				ktemp = temp.getNombre()+"-"+temp.getIdDepartamento();
				if (ktemp.equals(key)){
					depto=temp;
				}
			}
		}else{
			Iterator itDeptos=departamentos.iterator();
			while(itDeptos.hasNext()){
				Departamento temp=(Departamento)itDeptos.next();
				if (temp.getIdDepartamento().equals(key)){
					depto=temp;
				}
			}			
		}
		
		seleccionarDepartamentoEnSesion(depto, session);
		return null;
	}

	/**
	 * Este método se encarga de seleccionar un departamento en la <code>HttpSession</code>
	 * @param request
	 * @throws AccionWebException
	 */
	private void seleccionarDepartamentoEnSesion(Departamento depto, HttpSession session) {
		List municipios = depto.getMunicipios();
		//		  Organizar alfabeticamente
		Map mapMunicipios = Collections.synchronizedMap(new TreeMap());
		for (Iterator itMun = municipios.iterator(); itMun.hasNext();) {
			Municipio municipio = (Municipio) itMun.next();
			String llave = municipio.getNombre() + "-" + municipio.getIdMunicipio();
			mapMunicipios.put(llave, municipio);
		}

		session.setAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO, depto);
		session.setAttribute(AWAdministracionForseti.MAP_MUNICIPIOS, mapMunicipios);

	}
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un Municipio 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del municipio
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaMunicipio(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		Departamento depto =
			(Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);

		if (depto == null) {
			throw new AccionInvalidaException("Debe seleccionar un departamento.");
		}

		String id = request.getParameter(CMunicipio.ID_MUNICIPIO);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Municipio.");
		}

		String nombre = request.getParameter(CMunicipio.NOMBRE_MUNICIPIO);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre de Municipio.");
		}

		Municipio municipio = new Municipio();
		municipio.setNombre(nombre);
		municipio.setIdMunicipio(id);

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_MUNICIPIO);
		evento.setDepartamento(depto);
		evento.setMunicipio(municipio);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un Municipio 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del municipio
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaMunicipio(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		String key = request.getParameter(CMunicipio.ID_MUNICIPIO);
		if ((key == null) || (key.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código de municipio.");
		}
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Map municipios = (Map) session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
		Municipio municipio = (Municipio) municipios.get(key);

		if (municipio == null) {
			throw new AccionInvalidaException("El municipio seleccionado no existe.");
		}

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_MUNICIPIO);
		evento.setMunicipio(municipio);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la selección de un Municipio 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> 
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti seleccionaMunicipio(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();

		String key = request.getParameter(CMunicipio.ID_MUNICIPIO);
		if ((key == null) || (key.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe seleccionar un municipio.");
		}

		Map municipios = (Map) session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
		Municipio municipio = (Municipio) municipios.get(key);

		if (municipio == null) {
			throw new AccionInvalidaException("Debe seleccionar un municipio.");
		}

		seleccionarMunicipioEnSesion(municipio, session);
		return null;
	}

	/**
	 * Este método se encarga de seleccionar un municipio en la <code>HttpSession</code> 
	 * @param municipio
	 * @param session
	 * @throws AccionWebException
	 */
	private void seleccionarMunicipioEnSesion(Municipio municipio, HttpSession session) {
		List veredas = municipio.getVeredas();
		//		  Organizar alfabeticamente
		Map mapVeredas = Collections.synchronizedMap(new TreeMap());
		for (Iterator iter = veredas.iterator(); iter.hasNext();) {
			Vereda vereda = (Vereda) iter.next();
			String llave = vereda.getNombre() + "-" + vereda.getIdVereda();
			mapVeredas.put(llave, vereda);
		}
		session.setAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO, municipio);
		session.setAttribute(AWAdministracionForseti.MAP_VEREDAS, mapVeredas);
	}
	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de una Vereda 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información de la Vereda
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaVereda(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		Municipio municipio =
			(Municipio) session.getAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);

		if (municipio == null) {
			throw new AccionInvalidaException("Debe seleccionar un Municipio.");
		}

		String nombre = request.getParameter(CVereda.NOMBRE_VEREDA);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre de Vereda.");
		}

		String cabecera = request.getParameter(CVereda.ES_VEREDA);
		boolean esCabecera = false;
		if (cabecera == null || cabecera.trim().equals("")) {
			throw new AccionInvalidaException("Debe determinar si es o no cabecera municipal.");
		} 

		esCabecera = (cabecera.equals(CVereda.SI)) ? true : false;

		Vereda dato = new Vereda();
		dato.setNombre(nombre);
		dato.setCabecera(esCabecera);

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_VEREDA);
		evento.setMunicipio(municipio);
		evento.setVereda(dato);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de una Vereda 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información de la Vereda
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaVereda(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		Departamento depto =
			(Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
		if (depto == null) {
			throw new AccionInvalidaException("Debe seleccionar un Departamento.");
		}

		Municipio municipio =
			(Municipio) session.getAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);

		if (municipio == null) {
			throw new AccionInvalidaException("Debe seleccionar un Municipio.");
		}

		String id = request.getParameter(CVereda.ID_VEREDA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para la Vereda.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Vereda dato = new Vereda();
		dato.setIdVereda(id);
		dato.setIdDepartamento(depto.getIdDepartamento());
		dato.setIdMunicipio(municipio.getIdMunicipio());

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_VEREDA);
		evento.setMunicipio(municipio);
		evento.setVereda(dato);
		evento.setDepartamento(depto);
		return evento;
	}

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un Estado de folio  
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del estado de folio
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaEstadoFolio(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		
		ValidacionParametrosException vpe = new ValidacionParametrosException(); 

		String id = request.getParameter(CEstadoFolio.ID_ESTADO_FOLIO);
		if ((id == null) || (id.trim().length() == 0)) {
			vpe.addError("Debe Proporcionar un Código para el Estado de Folio.");
		}

		String nombre = request.getParameter(CEstadoFolio.NOMBRE_ESTADO_FOLIO);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			vpe.addError("Debe Proporcionar un nombre de Estado de Folio.");			
		}
		
		if(vpe.getErrores().size()>0){
			throw vpe;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EstadoFolio dato = new EstadoFolio();
		dato.setIdEstado(id);
		dato.setNombre(nombre);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_ESTADO_FOLIO);
		evento.setEstadoFolio(dato);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un Estado de folio  
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del estado de folio
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaEstadoFolio(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CEstadoFolio.ID_ESTADO_FOLIO);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Estado de Folio.");
		}
		
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EstadoFolio dato = new EstadoFolio();
		dato.setIdEstado(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_ESTADO_FOLIO);
		evento.setEstadoFolio(dato);

		return evento;
	}

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un tipo de oficina   
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del tipo de oficina
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaTipoOficina(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		String nombre = request.getParameter(CTipoOficina.NOMBRE_TIPO_OFICINA);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre de Tipo de Oficina.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		TipoOficina dato = new TipoOficina();
		dato.setNombre(nombre);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_TIPO_OFICINA);
		evento.setTipoOficina(dato);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un tipo de oficina   
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del tipo de oficina
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaTipoOficina(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CTipoOficina.ID_TIPO_OFICINA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Tipo de Oficina.");
		}
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		TipoOficina dato = new TipoOficina();
		dato.setIdTipoOficina(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_TIPO_OFICINA);
		evento.setTipoOficina(dato);

		return evento;
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un tipo de documento   
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del tipo de documento
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaTipoDocumento(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		String id = request.getParameter(CTipoDocumento.ID_TIPO_DOCUMENTO);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Tipo de Documento.");
		}

		String nombre = request.getParameter(CTipoDocumento.NOMBRE_TIPO_DOCUMENTO);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre de Tipo de Documento.");
		}
		
		//Validar que ni el nombre ni el codigo ya existen
		List listadoDocumentos = (List) session.getAttribute(WebKeys.LISTA_TIPOS_DOCUMENTO);
		Iterator it= listadoDocumentos.iterator();
		while(it.hasNext()){
			ElementoLista elem = (ElementoLista) it.next();
			//Validacion codigo
			if(elem.getId().equals(id)){
				throw new AccionInvalidaException("El codigo del tipo de documento ya se encuentra en el sistema.");
			}
			
			//Validacion nombre
			if(elem.getValor().equals(nombre)){
				throw new AccionInvalidaException("El nombre del tipo de documento ya se encuentra en el sistema.");
			}
		}
		

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		TipoDocumento dato = new TipoDocumento();
		dato.setIdTipoDocumento(id);
		dato.setNombre(nombre);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_TIPO_DOCUMENTO);
		evento.setTipoDocumento(dato);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un tipo de documento   
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del tipo de documento
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaTipoDocumento(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CTipoDocumento.ID_TIPO_DOCUMENTO);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Tipo de Documento.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		TipoDocumento dato = new TipoDocumento();
		dato.setIdTipoDocumento(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_TIPO_DOCUMENTO);
		evento.setTipoDocumento(dato);

		return evento;
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un tipo de predio   
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del tipo de predio
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaTipoPredio(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		String id = request.getParameter(CTipoPredio.ID_TIPO_PREDIO);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Tipo de Predio.");
		}
		
		String nombre = request.getParameter(CTipoPredio.NOMBRE_TIPO_PREDIO);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre de Tipo de Predio.");
		}

		String descripcion = request.getParameter(CTipoPredio.DESCRIPCION_TIPO_PREDIO);
		if ((descripcion == null) || (descripcion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar una descripción del Tipo de Predio.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		TipoPredio dato = new TipoPredio();
		dato.setIdPredio(id);
		dato.setNombre(nombre);
		dato.setDescripcion(descripcion);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_TIPO_PREDIO);
		evento.setTipoPredio(dato);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un tipo de predio   
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del tipo de predio
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaTipoPredio(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CTipoPredio.ID_TIPO_PREDIO);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Tipo de Predio.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		TipoPredio dato = new TipoPredio();
		dato.setIdPredio(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_TIPO_PREDIO);
		evento.setTipoPredio(dato);

		return evento;
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un grupo de naturaleza jurídica    
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del grupo de naturaleza jurídica 
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaGrupoNatJuridica(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		String id = request.getParameter(CGrupoNaturalezaJuridica.ID_GRUPO_NATURALEZA_JURIDICA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Grupo de Naturaleza Jurídica.");
		}

		String nombre = request.getParameter(CGrupoNaturalezaJuridica.NOMBRE_GRUPO_NATURALEZA_JURIDICA);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre para el Grupo de Naturaleza Jurídica.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		GrupoNaturalezaJuridica dato = new GrupoNaturalezaJuridica();
		dato.setIdGrupoNatJuridica(id);
		dato.setNombre(nombre);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_GRUPO_NAT_JURIDICA);
		evento.setGrupoNatJuridica(dato);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un grupo de naturaleza jurídica    
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del grupo de naturaleza jurídica 
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaGrupoNatJuridica(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CGrupoNaturalezaJuridica.ID_GRUPO_NATURALEZA_JURIDICA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Grupo de Naturaleza Jurídica.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		GrupoNaturalezaJuridica dato = new GrupoNaturalezaJuridica();
		dato.setIdGrupoNatJuridica(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_GRUPO_NAT_JURIDICA);
		evento.setGrupoNatJuridica(dato);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la selección de un grupo de naturaleza jurídica    
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code>  
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti seleccionaGrupoNatJuridica(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CGrupoNaturalezaJuridica.ID_GRUPO_NATURALEZA_JURIDICA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Seleccionar un Grupo de Naturaleza Jurídica.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
                /*
                *  @author Carlos Torres
                *  @chage   se agrega validacion de version diferente
                *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
                */
		List listado = (List) context.getAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V);
		for (Iterator iter = listado.iterator(); iter.hasNext();) {
			GrupoNaturalezaJuridica dato = (GrupoNaturalezaJuridica) iter.next();
			if (dato.getIdGrupoNatJuridica().equals(id)) {
				session.setAttribute(SELECCION_GRUPO_NAT_JURIDICA, dato);
				session.removeAttribute(WebKeys.NATURALEZA_JURIDICA);
				return null;
			}
		}

		throw new AccionInvalidaException("Debe Seleccionar un Grupo de Naturaleza Jurídica.");
	}
	
	

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la selección de una naturaleza jurídica    
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code>  
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti seleccionaNatJuridica(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Seleccionar una Naturaleza Jurídica.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		NaturalezaJuridica dato = new NaturalezaJuridica();
		dato.setIdNaturalezaJuridica(id);
		
		EvnAdministracionForseti evento =
					new EvnAdministracionForseti(usuario, EvnAdministracionForseti.SELECCIONA_NATURALEZA_JURIDICA);
				evento.setNaturalezaJuridica(dato);
		return evento;

	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un tipo de naturaleza jurídica    
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del tipo de naturaleza jurídica 
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaTipoNatJuridica(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		String id = request.getParameter(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Tipo de Naturaleza Jurídica.");
		}

		String nombre = request.getParameter(CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre para el Tipo de Naturaleza Jurídica.");
		}

		String idDom = request.getParameter(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA);
		DominioNaturalezaJuridica dominio = new DominioNaturalezaJuridica();
		/* Según nuevo requerimiento ya no es obligatorio asociar un dominio de naturaleza jurídica (Bug 5026)*/
		if (idDom == null || idDom.equals("") || idDom.equals(WebKeys.SIN_SELECCIONAR)) {
		}
		else
		{
			
			dominio.setIdDominioNatJur(idDom.toUpperCase());
		}
						
		

		String habilitado=request.getParameter(CNaturalezaJuridica.HABILIDATO_CALIFICACION);
		
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		NaturalezaJuridica dato = new NaturalezaJuridica();
		dato.setIdNaturalezaJuridica(id);
		dato.setNombre(nombre);
		dato.setDominioNaturalezaJuridica(dominio);

		GrupoNaturalezaJuridica grupo =
			(GrupoNaturalezaJuridica) session.getAttribute(SELECCION_GRUPO_NAT_JURIDICA);
		if (grupo == null) {
			throw new AccionInvalidaException("Debe Proporcionar un Grupo de Naturaleza Jurídica.");
		}
		dato.setGrupoNaturalezaJuridica(grupo);
		
		if (habilitado!=null){
			dato.setHabilitadoCalificacion(true);
		}

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_TIPO_NAT_JURIDICA);
		evento.setNaturalezaJuridica(dato);
		evento.setGrupoNatJuridica(grupo);
		return evento;
	}



	/**
	* Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	* generar un evento para la adición de un tipo de naturaleza jurídica    
	* @param request
	* @return evento <code>EvnAdministracionForseti</code> con la información del tipo de naturaleza jurídica 
	* @throws AccionWebException
	*/
	private EvnAdministracionForseti editaTipoNatJuridica(HttpServletRequest request)
		throws AccionWebException {
			
		HttpSession session = request.getSession();

		String id = request.getParameter(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Tipo de Naturaleza Jurídica.");
		}
		else{
			session.setAttribute(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA, id);
		}
		
			
    	String nombre = request.getParameter(CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre para el Nombre de Naturaleza Jurídica.");
		}
		else{
			session.setAttribute(CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA, nombre);
		}
		
		
		String idDom = request.getParameter(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA);
		if (idDom != null)
		{
			session.setAttribute(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA, idDom);
		}
								
		
		String habilitado=request.getParameter(CNaturalezaJuridica.HABILIDATO_CALIFICACION);
		if (habilitado != null)
		{
			session.setAttribute (CNaturalezaJuridica.HABILIDATO_CALIFICACION,habilitado);
		}
                        /*
                *  @author Carlos Torres
                *  @chage   se agrega validacion de version diferente
                *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
                */
                String version = request.getParameter(CNaturalezaJuridica.VERSION);
		if(version==null && "".equals(version))
                {
                    session.setAttribute(CNaturalezaJuridica.VERSION, version);
                }
				
				
		return null;
		}





	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un tipo de naturaleza jurídica    
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del tipo de naturaleza jurídica 
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaTipoNatJuridica(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Tipo de Naturaleza Jurídica.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		NaturalezaJuridica dato = new NaturalezaJuridica();
		dato.setIdNaturalezaJuridica(id);

		GrupoNaturalezaJuridica grupo =
			(GrupoNaturalezaJuridica) session.getAttribute(SELECCION_GRUPO_NAT_JURIDICA);
		if (grupo == null) {
			throw new AccionInvalidaException("Debe Proporcionar un Grupo de Naturaleza Jurídica.");
		}

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_TIPO_NAT_JURIDICA);
		evento.setNaturalezaJuridica(dato);
		evento.setGrupoNatJuridica(grupo);

		return evento;
	}
	
	
	
	
	

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un círculo registral
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del círculo registral
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaCirculoRegistral(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();

		String id = request.getParameter(CCirculo.ID_CIRCULO);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Círculo Registral.");
		}

		String nombre = request.getParameter(CCirculo.NOMBRE_CIRCULO);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre para el Círculo Registral.");
		}

		String impuesto = request.getParameter(CCirculo.IMPUESTO_CIRCULO);
		if ((impuesto == null) || (impuesto.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar el tipo de impuesto para el Círculo Registral.");
		}
		
		

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Circulo dato = new Circulo();
		dato.setIdCirculo(id);
		dato.setNombre(nombre);
		dato.setCobroImpuesto(impuesto.equals(CCirculo.IMPUESTO_CIRCULO_SI) ? true : false);
		String nit = request.getParameter(CCirculo.NIT_CIRCULO);
		if (nit!=null && !nit.equals("")){
			dato.setNit(nit);
		}
		
		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_CIRCULO_REGISTRAL);
		evento.setCirculo(dato);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un círculo registral
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del círculo registral
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaCirculoRegistral(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CCirculo.ID_CIRCULO);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Círculo Registral.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Circulo dato = new Circulo();
		dato.setIdCirculo(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_CIRCULO_REGISTRAL);
		evento.setCirculo(dato);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento destinado a editar el nit de un Círculo
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del círculo registral
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti editaNITCirculo(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		OficinaNitException exception = new OficinaNitException();

		String id = request.getParameter(CCirculo.ID_CIRCULO);
		if ((id == null) || (id.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un Código para el Círculo Registral.");
		}

		String nit = request.getParameter(CCirculo.NIT_CIRCULO);
		if ((nit == null) || (nit.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un NIT para el Círculo Registral.");
		}

		if (!exception.getErrores().isEmpty()) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Circulo dato = new Circulo();
		dato.setIdCirculo(id);
		dato.setNit(nit);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.EDITA_NIT_CIRCULO_REGISTRAL);
		evento.setCirculo(dato);

		return evento;
	}
	

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento destinado a editar el Círculo
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del círculo registral
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti editaCirculo(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		ValidacionAdicionCirculoException exception = new ValidacionAdicionCirculoException();

		String id = request.getParameter(CCirculo.ID_CIRCULO);
		if ((id == null) || (id.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un Código para el Círculo Registral.");
		}
		
		String nombre = request.getParameter(CCirculo.NOMBRE_CIRCULO);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un nombre para el Círculo Registral.");
		}

		String nit = request.getParameter(CCirculo.NIT_CIRCULO);
		if ((nit == null) || (nit.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un NIT para el Círculo Registral.");
		}
		
		String impuestos = request.getParameter(CCirculo.IMPUESTO_CIRCULO);
		
		if(impuestos==null){
			exception.addError("Debe seleccionar si se cobra impuestos en el Círculo Registral.");
		}
		
		if (!exception.getErrores().isEmpty()) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Circulo dato = new Circulo();
		dato.setIdCirculo(id);
		dato.setNombre(nombre);
		dato.setNit(nit);
		dato.setToUpdateCobroImpuesto(true);
		
		if(impuestos.equals(CCirculo.IMPUESTO_CIRCULO_SI)){
			dato.setCobroImpuesto(true);
		}	
		else{
			dato.setCobroImpuesto(false);
		}
			
		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.EDITA_CIRCULO_REGISTRAL);
		evento.setCirculo(dato);

		return evento;
	}
	
	


	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un Estado de Anotación 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del Estado de Anotación
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaEstadoAnotacion(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();

		String id = request.getParameter(CEstadoAnotacion.ID_ESTADO_ANOTACION);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Estado de anotación.");
		}

		String nombre = request.getParameter(CEstadoAnotacion.NOMBRE_ESTADO_ANOTACION);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre para el Estado de anotación.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EstadoAnotacion dato = new EstadoAnotacion();
		dato.setIdEstadoAn(id);
		dato.setNombre(nombre);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_ESTADO_ANOTACION);
		evento.setEstadoAnotacion(dato);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un Estado de Anotación 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del Estado de Anotación
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaEstadoAnotacion(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CEstadoAnotacion.ID_ESTADO_ANOTACION);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Estado de anotación.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EstadoAnotacion dato = new EstadoAnotacion();
		dato.setIdEstadoAn(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_ESTADO_ANOTACION);
		evento.setEstadoAnotacion(dato);

		return evento;
	}

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un dominio de naturaleza jurídica 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del Estado de Anotación
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaDominioNaturalezaJuridica(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();

		String id = request.getParameter(CDominioNaturalezaJuridica.ID_DOMINIO_NAT_JURIDICA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Dominio de naturaleza jurídica.");
		}

		String nombre = request.getParameter(CDominioNaturalezaJuridica.NOMBRE_DOMINIO_NAT_JURIDICA);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre para el Dominio de naturaleza jurídica.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		DominioNaturalezaJuridica dato = new DominioNaturalezaJuridica();
		dato.setIdDominioNatJur(id);
		dato.setNombre(nombre);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(
				usuario,
				EvnAdministracionForseti.ADICIONA_DOMINIO_NAT_JURIDICA);
		evento.setDominioNatJuridica(dato);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un dominio de naturaleza jurídica 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del Estado de Anotación
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaDominioNaturalezaJuridica(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CDominioNaturalezaJuridica.ID_DOMINIO_NAT_JURIDICA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Dominio de naturaleza jurídica.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		DominioNaturalezaJuridica dato = new DominioNaturalezaJuridica();
		dato.setIdDominioNatJur(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_DOMINIO_NAT_JURIDICA);
		evento.setDominioNatJuridica(dato);

		return evento;
	}

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un círculo festivo 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del círculo festivo 
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaCirculoFestivo(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		String fechaStr = request.getParameter("CALENDAR");		
		String descripcion = request.getParameter(CCirculoFestivo.DESCRIPCION_FESTIVO);		

		String id = request.getParameter(CCirculoFestivo.ID_CIRCULO);
		if ((id == null) || (id.trim().length() == 0) || id.equals(WebKeys.SIN_SELECCIONAR)) {
			session.setAttribute("CALENDAR",fechaStr);
			session.setAttribute(CCirculoFestivo.DESCRIPCION_FESTIVO,descripcion);
			throw new AccionInvalidaException("Debe Seleccionar un Círculo Registral.");
		}
		id= id.toUpperCase();


		if ((fechaStr == null) || (fechaStr.trim().length() == 0)) {
			session.setAttribute("CALENDAR",fechaStr);
			session.setAttribute(CCirculoFestivo.DESCRIPCION_FESTIVO,descripcion);
			throw new AccionInvalidaException("Debe Proporcionar una fecha.");
		}

		if ((descripcion == null) || (descripcion.trim().length() == 0)) {
			session.setAttribute("CALENDAR",fechaStr);
			session.setAttribute(CCirculoFestivo.DESCRIPCION_FESTIVO,descripcion);
			throw new AccionInvalidaException("Debe Proporcionar una Descripción.");
		}
		session.setAttribute("CALENDAR",fechaStr);
		session.setAttribute(CCirculoFestivo.DESCRIPCION_FESTIVO,descripcion);

		Date fecha = null;
		fecha = darFecha(fechaStr);
		if (fecha == null) {
			session.setAttribute("CALENDAR",fechaStr);
			session.setAttribute(CCirculoFestivo.DESCRIPCION_FESTIVO,descripcion);
			throw new AccionInvalidaException("El formato de fecha no es válido.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		CirculoFestivo dato = new CirculoFestivo();
		dato.setIdCirculo(id);
		dato.setFechaFestivo(fecha);
		dato.setDescripcion(descripcion);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_CIRCULO_FESTIVO);
		evento.setCirculoFestivo(dato);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un círculo festivo 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del círculo festivo 
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaCirculoFestivo(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CCirculoFestivo.ID_CIRCULO);
		if ((id == null) || (id.trim().length() == 0) || id.equals(WebKeys.SIN_SELECCIONAR)) {
			throw new AccionInvalidaException("Debe Seleccionar un Círculo Registral.");
		}

		String fechaStr = request.getParameter("CALENDAR");
		if ((fechaStr == null) || (fechaStr.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar una fecha.");
		}

		Date fecha = null; 
		try {
			fecha = new Date (new Long (fechaStr).longValue());
		} catch (Exception e) {
			throw new AccionInvalidaException("El formato de fecha no es válido.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		CirculoFestivo dato = new CirculoFestivo();
		dato.setIdCirculo(id);
		dato.setFechaFestivo(fecha);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_CIRCULO_FESTIVO);
		evento.setCirculoFestivo(dato);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la selección de un círculo festivo 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del círculo festivo 
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti seleccionaCirculoFestivo(HttpServletRequest request) {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CCirculoFestivo.ID_CIRCULO);
		if ((id == null) || (id.trim().length() == 0) || id.equals(WebKeys.SIN_SELECCIONAR)) {
			//throw new AccionInvalidaException("Debe Seleccionar un Círculo Registral.");
			return null;
		}
		id= id.toUpperCase();

		session.setAttribute(CCirculoFestivo.ID_CIRCULO, id);

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		CirculoFestivo dato = new CirculoFestivo();
		dato.setIdCirculo(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.SELECCIONA_CIRCULO_FESTIVO);
		evento.setCirculoFestivo(dato);

		return evento;
	}

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un eje 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del eje 
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti adicionaEje(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		String id = request.getParameter(CEje.ID_EJE);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Eje.");
		}

		String nombre = request.getParameter(CEje.NOMBRE_EJE);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre para el Eje.");
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Eje dato = new Eje();
		dato.setIdEje(id);
		dato.setNombre(nombre);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_EJE);
		evento.setEje(dato);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de un eje 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del eje 
	 * @throws AccionWebException
	 */
	private EvnAdministracionForseti eliminaEje(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		context = session.getServletContext();

		String id = request.getParameter(CEje.ID_EJE);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Eje.");
		}
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Eje dato = new Eje();
		dato.setIdEje(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_EJE);
		evento.setEje(dato);

		return evento;
	}

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de una zona registral 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información de la zona registral 
	 * @throws AccionWebException 
	 */
	private EvnAdministracionForseti adicionaZonaRegistral(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();

		ValidacionZonaRegistralException exception = new ValidacionZonaRegistralException();

		String idcirculo = request.getParameter(CCirculo.ID_CIRCULO);
		if ((idcirculo == null)
			|| (idcirculo.trim().length() == 0)
			|| idcirculo.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un círculo.");
		} else {
			session.setAttribute(CCirculo.ID_CIRCULO, idcirculo);
		}

		String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		if ((idDepto == null)
			|| (idDepto.trim().length() == 0)
			|| idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Departamento.");
		} else {
			session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
		}

		String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
		if ((idMunicipio == null)
			|| (idMunicipio.trim().length() == 0)
			|| idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Municipio.");
		} else {
			session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
		}

		String idVereda = request.getParameter(CVereda.ID_VEREDA);
		if ((idVereda == null)
			|| (idVereda.trim().length() == 0)
			|| idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar una Vereda.");
		} else {
			session.setAttribute(CVereda.ID_VEREDA, idVereda);
		}

		String soloIdMunicipio=idMunicipio.substring(idMunicipio.indexOf("-")+1,idMunicipio.length());
		String soloIdDepartamento=idDepto.substring(idDepto.indexOf("-")+1,idDepto.length());
		List regZon = (List)session.getAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES);
		
		Iterator itZonas=regZon.iterator();
		while(itZonas.hasNext()){
			ZonaRegistral dato = (ZonaRegistral)itZonas.next();
			if (dato.getVereda().getIdVereda().equals(idVereda)&&dato.getVereda().getIdMunicipio().equals(soloIdMunicipio)&&dato.getVereda().getIdDepartamento().equals(soloIdDepartamento)){
				exception.addError("Esa zona registral ya existe");
				break;
			}
		}
		
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Departamento deptoSeleccionado =
			(Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
		Municipio munSeleccionado =
			(Municipio) session.getAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);

		Circulo circulo = new Circulo();
		circulo.setIdCirculo(idcirculo);

		Vereda vereda = new Vereda();
		vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
		vereda.setIdMunicipio(munSeleccionado.getIdMunicipio());
		vereda.setIdVereda(idVereda);

		ZonaRegistral dato = new ZonaRegistral();
		dato.setVereda(vereda);
		dato.setCirculo(circulo);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_ZONA_REGISTRAL);
		evento.setZonaRegistral(dato);
		evento.setCirculo(circulo);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la eliminación de una zona registral 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información de la zona registral 
	 * @throws AccionWebException 
	 */
	private EvnAdministracionForseti eliminaZonaRegistral(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		ValidacionZonaRegistralException exception = new ValidacionZonaRegistralException();

		String id = request.getParameter(CZonaRegistral.ID_ZONA_REGISTRAL);
		if ((id == null) || (id.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un Código para la zona registral.");
		} else {
			session.setAttribute(CZonaRegistral.ID_ZONA_REGISTRAL, id);
		}

		String idcirculo = request.getParameter(CCirculo.ID_CIRCULO);
		if ((idcirculo == null)
			|| (idcirculo.trim().length() == 0)
			|| idcirculo.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Proporcionar un círculo.");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Circulo circulo = new Circulo();
		circulo.setIdCirculo(idcirculo);

		ZonaRegistral dato = new ZonaRegistral();
		dato.setIdZonaRegistral(id);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ELIMINA_ZONA_REGISTRAL);
		evento.setZonaRegistral(dato);
		evento.setCirculo(circulo);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la selección de una zona registral 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información de la zona registral 
	 * @throws AccionWebException 
	 */
	private EvnAdministracionForseti seleccionaZonaRegistral(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		context = session.getServletContext();

		ValidacionZonaRegistralException exception = new ValidacionZonaRegistralException();

		String id = request.getParameter(CZonaRegistral.ID_ZONA_REGISTRAL);
		if ((id != null) && (id.trim().length() != 0)) {
			session.setAttribute(CZonaRegistral.ID_ZONA_REGISTRAL, id);
		}

		String idcirculo = request.getParameter(CCirculo.ID_CIRCULO);
		if ((idcirculo == null)
			|| (idcirculo.trim().length() == 0)
			|| idcirculo.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un círculo.");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Circulo dato = new Circulo();
		dato.setIdCirculo(idcirculo);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.SELECCIONA_ZONA_REGISTRAL);
		evento.setCirculo(dato);

		session.setAttribute(CCirculo.ID_CIRCULO, idcirculo);
		session.setAttribute(RESOLUCION_TRASLADO, request.getParameter(RESOLUCION_TRASLADO));
		session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
		session.removeAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS);
		session.removeAttribute(AWAdministracionForseti.LISTA_MUNICIPIOS);
		session.removeAttribute(AWAdministracionForseti.LISTA_VEREDAS);
		session.removeAttribute(CMunicipio.ID_MUNICIPIO);

		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de una zona registral por departametno
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> 
	 * @throws AccionWebException 
	 */
	private EvnAdministracionForseti seleccionaZonaRegistralDepto(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		//ServletContext context = session.getServletContext();

		ValidacionZonaRegistralException exception = new ValidacionZonaRegistralException();

		String id = request.getParameter(CZonaRegistral.ID_ZONA_REGISTRAL);
		if ((id != null) && (id.trim().length() != 0)) {
			session.setAttribute(CZonaRegistral.ID_ZONA_REGISTRAL, id);
		}

		String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		if ((idDepto == null)
			|| (idDepto.trim().length() == 0)
			|| idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Departamento.");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Map deptos=(Map)request.getSession().getServletContext().getAttribute(WebKeys.MAPA_DEPARTAMENTOS);
		
		Departamento depto =(Departamento)deptos.get(idDepto);
		
		/*seleccionaDepartamento(request);
		Departamento depto =
			(Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);

		Map mapMunicipios = (Map) session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
		List listaMunicipios = new ArrayList();
		for (Iterator itMun = mapMunicipios.keySet().iterator(); itMun.hasNext();) {
			String key = (String) itMun.next();
			Municipio municipio = (Municipio) mapMunicipios.get(key);
			listaMunicipios.add(new ElementoLista(key, municipio.getNombre()));
		}

		session.setAttribute(AWAdministracionForseti.LISTA_MUNICIPIOS, listaMunicipios);
		session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
		*/
		
		session.setAttribute(RESOLUCION_TRASLADO, request.getParameter(RESOLUCION_TRASLADO));

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		EvnAdministracionForseti evento=new EvnAdministracionForseti(usuario,EvnAdministracionForseti.CONSULTA_DEPARTAMENTO);
		evento.setDepartamento(depto);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de una zona registral por municipio 
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code>  
	 * @throws AccionWebException 
         */
	private EvnAdministracionForseti seleccionaZonaRegistralMunicipio(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		//ServletContext context = session.getServletContext();

		ValidacionZonaRegistralException exception = new ValidacionZonaRegistralException();

		String id = request.getParameter(CZonaRegistral.ID_ZONA_REGISTRAL);
		if ((id != null) && (id.trim().length() != 0)) {
			session.setAttribute(CZonaRegistral.ID_ZONA_REGISTRAL, id);
		}

		String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
		if ((idMunicipio == null)
			|| (idMunicipio.trim().length() == 0)
			|| idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Municipio.");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		seleccionaMunicipio(request);

		Municipio municipio =
			(Municipio) session.getAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);
		List veredas = municipio.getVeredas();
		List listaVeredas = new ArrayList();
		for (Iterator itMun = veredas.iterator(); itMun.hasNext();) {
			Vereda vereda = (Vereda) itMun.next();
			listaVeredas.add(new ElementoLista(vereda.getIdVereda(), vereda.getNombre()));
		}

		session.setAttribute(AWAdministracionForseti.LISTA_VEREDAS, listaVeredas);
		session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
		session.removeAttribute(CVereda.ID_VEREDA);
		session.setAttribute(RESOLUCION_TRASLADO, request.getParameter(RESOLUCION_TRASLADO));
		return null;
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de traslado  
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code>  
	 * @throws AccionWebException
         * @author      :   Julio Alcázar Rivas
         * @change      :   modificaciones sustanciales al comportamiento del metodo
         * Caso Mantis  :   07123
 	 */
	private EvnAdministracionForseti trasladar(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		ValidacionTrasladoException exception = new ValidacionTrasladoException();
		
		List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
                if (matriculas == null) {
                    exception.addError("Debe ingresar al menos una matricula");
                }else{
                    session.setAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO, matriculas);
                }
                /**
                 * @author     : Ellery David Robles Gómez
                 * @change     : Guardar en sesion fundamentos de traslado
                 * Caso Mantis : 14985
                 */

                List fundamentos = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
                if (fundamentos == null) {
                    exception.addError("Debe ingresar al menos un fundamento");
                }else{
                    session.setAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS, fundamentos);
                }
                                

                String circulo_oficina = request.getParameter(AWAdministracionForseti.CIRCULO_OFICINA);
                if ((circulo_oficina == null) || (circulo_oficina.trim().length() == 0)) {
			exception.addError("Debe ingresar un tipo de oficinas SIR o FOLIO para el traslado");
		} else {
			session.setAttribute(CIRCULO_OFICINA, circulo_oficina);
		}

                String cirdestino = null;
                if (circulo_oficina.equals("S")){
                    cirdestino = request.getParameter(AWAdministracionForseti.CIRCULOS_SIR);
                    if ((cirdestino == null) || (cirdestino.trim().length() == 0) || cirdestino.equals(WebKeys.SIN_SELECCIONAR)) {
                            exception.addError("Debe Seleccionar un círculo.");
                    } else {
                            session.setAttribute(AWAdministracionForseti.CIRCULOS_SIR, cirdestino);
                    }
                /*
                 * @author      : Julio Alcázar Rivas
                 * @change      : nuevos parametros de traslados
                 * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                 */
                }else{
                    cirdestino = request.getParameter(AWAdministracionForseti.CIRCULOS_NOSIR);
                    if ((cirdestino == null) || (cirdestino.trim().length() == 0) || cirdestino.equals(WebKeys.SIN_SELECCIONAR)) {
                            exception.addError("Debe Seleccionar un círculo.");
                    } else {
                            session.setAttribute(AWAdministracionForseti.CIRCULOS_NOSIR, cirdestino);
                    }
                }

                
                String cirorigen =null;
                if ( request.getSession().getAttribute(WebKeys.CIRCULO) != null ) {
                    Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
                    cirorigen = ((gov.sir.core.negocio.modelo.Circulo) request.getSession().getAttribute(WebKeys.CIRCULO)).getIdCirculo();
                    if ((cirorigen == null) || (cirorigen.trim().length() == 0) ) {
                            exception.addError("No se tiene circulo origen.");
                    } else {
                            session.setAttribute(WebKeys.CIRCULO, circulo);
                    }
                }

                
         	if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		
		Circulo circuloorigen = new Circulo();
		circuloorigen.setIdCirculo(cirorigen);

                Circulo circulodestino = new Circulo();
		circulodestino.setIdCirculo(cirdestino);
		

		gov.sir.core.negocio.modelo.Usuario usuarioSIR =
			(gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.TRASLADAR, usuarioSIR);
                evento.setCirculoOrigen(circuloorigen);
                evento.setCirculoDestino(circulodestino);
                evento.setMatriculas(matriculas);
                /**
                 * @author     : Ellery David Robles Gómez
                 * @change     : Se agrega fundamento al evento y se comento codigo anterior
                 * Caso Mantis : 14985
                 */
                evento.setFundamentos(fundamentos);
//		evento.setResolucion(resolucion);
                /*
                 * @author      : Julio Alcázar Rivas
                 * @change      : nueva
                 * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                 */
//                evento.setFechaResolucionOrigen(dFechIni);
                evento.setTipo_circulo(circulo_oficina);

		return evento;
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la adición de un tipo de oficina origen  
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información del tipo de oficina origen 
	 * @throws AccionWebException 
	 */
	private EvnAdministracionForseti adicionaOficinaOrigen(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		salvarDatosOficinaOrigenEnSesion(request);

		ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();
		
		String numero = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_NUMERO);
		String nombre = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_NOMBRE);		
		if (nombre == null || nombre.equals("")) {
			exception.addError("Debe Proporcionar un Nombre para la oficina.");
		}
		if (numero == null || numero.equals("")) {
			exception.addError("Debe Proporcionar un Número para la oficina.");
		}
		String exento =
			(String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_EXENTO_DERECHO_NOTARIAL);

		String descripcion =
			(String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_DESCRIPCION_NAT_JURIDICA);

		String telefono = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_TELEFONO);
		
		String direccion = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_DIRECCION);

		String fax = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_FAX);

		String email = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_EMAIL);

		String funcionario = (String) session.getAttribute(COficinaOrigen.OFICINA_ORIGEN_FUNCIONARIO);

		String idTipoOficina = (String) session.getAttribute(CTipoOficina.ID_TIPO_OFICINA);
		if ((idTipoOficina == null)
			|| (idTipoOficina.trim().length() == 0)
			|| idTipoOficina.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Tipo de oficina.");
		} else {
			session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, idTipoOficina);
		}

		List listaCategorias = new ArrayList();
		if (idTipoOficina.equals(CTipoOficina.TIPO_OFICINA_NOTARIA)) {
			//TODO: VALIDACION DATOS OBLIGATORIOS OFICINA
			String [] categorias = request.getParameterValues(CCategoria.ID_CATEGORIA);
			if (categorias != null) {
				for (int i = 0; i < categorias.length; i++) {
					Categoria categoria = new Categoria();
					categoria.setIdCategoria(categorias[i]);
					listaCategorias.add(categoria);
				}
			}
		}

		String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		if ((idDepto == null)
			|| (idDepto.trim().length() == 0)
			|| idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Departamento.");
		} else {
			session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
		}

		String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
		session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
		if ((idMunicipio == null)
			|| (idMunicipio.trim().length() == 0)
			|| idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Municipio.");
		} else {
			session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
		}
				
		Map municipios = (Map) session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);

		if(municipios==null && exception.getErrores().size()==0){			
			exception.addError("Debe seleccionar el departamento y municipio donde se encuentra la oficina origen a agregar.");
		}
		if (exception.getErrores().size() > 0) {
			throw exception;
		}		
		
		Municipio municipio = (Municipio) municipios.get(idMunicipio);
		
		List veredas=municipio.getVeredas();
		Iterator itVeredas=veredas.iterator();
		String idVereda=null;
		while(itVeredas.hasNext()){
			Vereda vereda=(Vereda)itVeredas.next();
			if (vereda.isCabecera()){
				idVereda=vereda.getIdVereda();
			}
		}
		
		if ((idVereda == null)
			|| (idVereda.trim().length() == 0)
			|| idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("No se encontró la cabecera municipal");
		} else {
			session.setAttribute(CVereda.ID_VEREDA, idVereda);
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Departamento deptoSeleccionado =
			(Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
		

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Vereda vereda = new Vereda();
		vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
		vereda.setIdMunicipio(municipio.getIdMunicipio());
		vereda.setIdVereda(idVereda);

		TipoOficina tipoOficina = new TipoOficina();
		tipoOficina.setIdTipoOficina(idTipoOficina);

		OficinaOrigen dato = new OficinaOrigen();
		dato.setNombre(nombre);
		dato.setNumero(numero);
		dato.setExentoDerechoNotarial((exento == null) ? false : true);
		dato.setNaturalezaJuridicaEntidad(descripcion);
		dato.setTipoOficina(tipoOficina);
		dato.setVereda(vereda);
		dato.setTelefono(telefono);
		dato.setDireccion(direccion);
		dato.setFax(fax);
		dato.setEncargado(funcionario);
		dato.setEmail(email);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.ADICIONA_OFICINA_ORIGEN);
		evento.setOficinaOrigen(dato);
		evento.setVereda(vereda);
		evento.setCategorias(listaCategorias);
		return evento;

	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la consulta oficinas origen por vereda  
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información de la vereda  
	 * @throws AccionWebException 
	 */
	private EvnAdministracionForseti consultaOficinaOrigenByVereda(HttpServletRequest request)
		throws AccionWebException {

		HttpSession session = request.getSession();
		salvarDatosOficinaOrigenEnSesion(request);

		ValidacionOficinaOrigenException exception = new ValidacionOficinaOrigenException();

		String idDepto = request.getParameter(CDepartamento.ID_DEPARTAMENTO);
		if ((idDepto == null)
			|| (idDepto.trim().length() == 0)
			|| idDepto.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Departamento.");
		} else {
			session.setAttribute(CDepartamento.ID_DEPARTAMENTO, idDepto);
		}

		String idMunicipio = request.getParameter(CMunicipio.ID_MUNICIPIO);
		if ((idMunicipio == null)
			|| (idMunicipio.trim().length() == 0)
			|| idMunicipio.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe Seleccionar un Municipio.");
		} else {
			session.setAttribute(CMunicipio.ID_MUNICIPIO, idMunicipio);
		}

		Map municipios = (Map) session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
		Municipio municipio = (Municipio) municipios.get(idMunicipio);
		
		List veredas=municipio.getVeredas();
		Iterator itVeredas=veredas.iterator();
		String idVereda=null;
		while(itVeredas.hasNext()){
			Vereda vereda=(Vereda)itVeredas.next();
			if (vereda.isCabecera()){
				idVereda=vereda.getIdVereda();
			}
		}
		
		if ((idVereda == null)
			|| (idVereda.trim().length() == 0)
			|| idVereda.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("No se encontró la cabecera municipal");
		} else {
			session.setAttribute(CVereda.ID_VEREDA, idVereda);
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		session.setAttribute(CTipoOficina.ID_TIPO_OFICINA,request.getParameter(CTipoOficina.ID_TIPO_OFICINA));

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Departamento deptoSeleccionado =
			(Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
		

		Vereda vereda = new Vereda();
		vereda.setIdDepartamento(deptoSeleccionado.getIdDepartamento());
		vereda.setIdMunicipio(municipio.getIdMunicipio());
		vereda.setIdVereda(idVereda);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(
				usuario,
				EvnAdministracionForseti.CONSULTA_OFICINA_ORIGEN_POR_VEREDA);
		evento.setVereda(vereda);
		return evento;

	}

	/**
	 * Metodo para salvar los datos de una oficina origen en la <code>HttpSession</code>
	 * del usuario
	 * @param request
	 */
	private void salvarDatosOficinaOrigenEnSesion(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String nombre = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_NOMBRE);
		//if (nombre != null && !nombre.trim().equals("")) {
		if (nombre != null ) {
			session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_NOMBRE, nombre);
		}
		
		String idCategoriaNotaria = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_ID_CATEGORIA);
		if (idCategoriaNotaria != null ) {
			session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_ID_CATEGORIA, idCategoriaNotaria);
		}
		

		String numero = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_NUMERO);
		//if (numero != null && !numero.trim().equals("")) {
		if (numero != null ) {
			session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_NUMERO, numero);
		}

		String exento = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_EXENTO_DERECHO_NOTARIAL);
		//if (exento != null && !exento.trim().equals("")) {
		if (exento != null ) {			
			session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_EXENTO_DERECHO_NOTARIAL, exento);
		}

		String descripcionNatJuridica =
			request.getParameter(COficinaOrigen.OFICINA_ORIGEN_DESCRIPCION_NAT_JURIDICA);
		//if (descripcionNatJuridica != null && !descripcionNatJuridica.trim().equals("")) {		
		if (descripcionNatJuridica != null ) {
			session.setAttribute(
				COficinaOrigen.OFICINA_ORIGEN_DESCRIPCION_NAT_JURIDICA,
				descripcionNatJuridica);
		}

		String telefono = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_TELEFONO);
		//if (telefono != null && !telefono.trim().equals("")) {
		if (telefono != null ) {
			session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_TELEFONO, telefono);
		}

		String direccion = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_DIRECCION);
		//if (direccion != null && !direccion.trim().equals("")) {
		if (direccion != null ) {
			session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_DIRECCION, direccion);
		}

		String fax = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_FAX);
		//if (fax != null && !fax.trim().equals("")) {
		if (fax != null ) {
			session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_FAX, fax);
		}

		String email = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_EMAIL);
		//if (email != null && !email.trim().equals("")) {
		if (email != null ) {
			session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_EMAIL, email);
		}

		String funcionario = request.getParameter(COficinaOrigen.OFICINA_ORIGEN_FUNCIONARIO);
		//if (funcionario != null && !funcionario.trim().equals("")) {
		if (funcionario != null ) {			
			session.setAttribute(COficinaOrigen.OFICINA_ORIGEN_FUNCIONARIO, funcionario);
		}

		String idTipoOficina = request.getParameter(CTipoOficina.ID_TIPO_OFICINA);
		//if (idTipoOficina != null && !idTipoOficina.trim().equals("")) {
		if (idTipoOficina != null ) {
			session.setAttribute(CTipoOficina.ID_TIPO_OFICINA, idTipoOficina);
		}

	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la ejecución del proceso de catasto  
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información de ejecución del proceso de catastro
	 * @throws AccionWebException 
	 */
	private EvnAdministracionForseti catastro(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();

		ValidacionTrasladoException exception = new ValidacionTrasladoException();

		String fechIni = request.getParameter(FECHINI);
		Date dFechIni = null;
		if ((fechIni == null) || (fechIni.trim().length() == 0)) {
			exception.addError("Debe seleccionar una fecha inicial.");
		} else {
			try {
				dFechIni = DateFormatUtil.parse(fechIni);
			} catch (ParseException pe) {
				exception.addError("Fecha de inicio tiene formato inválido");
			}
		}
		String fechFin = request.getParameter(FECHFIN);
		Date dFechFin = null;
		if ((fechFin == null) || (fechFin.trim().length() == 0)) {
			exception.addError("Debe seleccionar una fecha final.");
		} else {
			try {
				dFechFin = DateFormatUtil.parse(fechFin);
			} catch (ParseException pe) {
				exception.addError("Fecha de inicio tiene formato inválido");
			}
		}
		String idCirculo = request.getParameter(CCirculo.ID_CIRCULO);
		if ((idCirculo == null) || (idCirculo.trim().length() == 0)) {
			exception.addError("Debe seleccionar un círculo registral.");
		}
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		gov.sir.core.negocio.modelo.Usuario usuarioSIR =
			(gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		Circulo circulo = new Circulo();
		circulo.setIdCirculo(idCirculo);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.CATASTRO, usuarioSIR);
		evento.setFechaInicialCatastro(dFechIni);
		evento.setFechaFinalCatastro(dFechFin);
		evento.setCirculo(circulo);

		return evento;
	}
	
	  ////////////////////////////////////////////////////////////////////////
	  ////////////////////////////////////////////////////////////////////////
	  /**
	   * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	   * generar un evento para la ejecución del proceso de catasto  
	   * @param request
	   * @return evento <code>EvnAdministracionForseti</code> con la información de ejecución del proceso de catastro
	   * @throws AccionWebException 
	   */
	  private EvnAdministracionForseti cargarTiposPlantilla(HttpServletRequest request) {

		  Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		  
		  EvnAdministracionForseti evento =
			  new EvnAdministracionForseti(usuario, EvnAdministracionForseti.CARGAR_PLANTILLAS_CERTIFICADOS);
		 

		  return evento;
	  }
	  
	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la ejecución del proceso de catasto  
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información de ejecución del proceso de catastro
	 * @throws AccionWebException 
	 */
	private EvnAdministracionForseti cargarPlantilla(HttpServletRequest request) {

		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		
		String descripcionPlantilla="";
		String tipoPlantilla = request.getParameter("RESPUESTA"); 
		request.getSession().setAttribute("RESPUESTA",tipoPlantilla);
		List listaPlantilla= (List)request.getSession().getAttribute(AWAdministracionForseti.LISTA_PLANTILLAS); 
		Iterator iplant=listaPlantilla.iterator();
		for(;iplant.hasNext();){
			PlantillaPertenencia pp=(PlantillaPertenencia) iplant.next();
			if(tipoPlantilla.equals(pp.getRespuesta())){
				descripcionPlantilla=pp.getDescripcion();
			}
		}
		request.getSession().setAttribute("DESCRIPCION", descripcionPlantilla);
	 

		return null;
	}
	  
	  ////////////////////////////////////////////////////////////////////////
	  ////////////////////////////////////////////////////////////////////////
	  /**
	   * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	   * generar un evento para la ejecución del proceso de catasto  
	   * @param request
	   * @return evento <code>EvnAdministracionForseti</code> con la información de ejecución del proceso de catastro
	   * @throws AccionWebException 
	   */
	  private EvnAdministracionForseti editarPlantilla(HttpServletRequest request) throws AccionWebException {
		  Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		  
		  String tipoPlantilla = (String) request.getSession().getAttribute("RESPUESTA"); 
		  if (tipoPlantilla==null){
			  throw new PlantillaPertenenciaException("Debe ingresar un nombre de la plantilla");
		  }
		  String descripcion = request.getParameter("DESCRIPCION");
		  			
		removerDatosTipoPlantilla(request);

		  EvnAdministracionForseti evento =
			  new EvnAdministracionForseti(usuario, EvnAdministracionForseti.EDITAR_PLANTILLA, tipoPlantilla, descripcion);

		  return evento;
	  }
	  
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para reabrir un folio  
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code> con la información de ejecución del proceso de catastro
	 * @throws AccionWebException 
	 */
	private EvnAdministracionForseti reabrirFolio(HttpServletRequest request) throws AccionWebException {

		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		gov.sir.core.negocio.modelo.Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		
		
		ReabrirFolioException exception = new ReabrirFolioException();
		String comentario = request.getParameter(CEstadoFolio.COMENTARIO_ESTADO_FOLIO);
		if ((comentario == null)||(comentario.equals(""))) {
			exception.addError("Debe Proporcionar un comentario para que pueda reabrir el folio");
		}
		
		Folio folio = (Folio)request.getSession().getAttribute(WebKeys.FOLIO);
		if(folio==null){
			exception.addError("No hay folio a reabrir");
		}
		
		if(!exception.getErrores().isEmpty()){
			throw exception;
		}
		
		Folio fol = new Folio();
		fol.setIdMatricula(folio.getIdMatricula());
		fol.setZonaRegistral(folio.getZonaRegistral());
		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.REABRIR_FOLIO, usuarioSIR);
		evento.setFolio(fol);
		evento.setComentario(comentario);
		 /**
                * @Author Carlos Torres
                * @Mantis 13176
                */
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(),String.valueOf(usuarioNeg.getIdUsuario()));
                infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                evento.setInfoUsuario(infoUsuario);
		return evento;
	}

	/**
	 * Este método se encarga de limpiar la sesión de los datos de una oficina origen
	 */
	private void removerDatosTipoPlantilla(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("PREGUNTA");
		session.removeAttribute("DESCRIPCION");
		session.removeAttribute(TIPO_PLANTILLAS);
		session.removeAttribute(LISTA_PLANTILLAS);
		request.removeAttribute("PREGUNTA");
		request.removeAttribute("DESCRIPCION");
	}

	/**
	 * Este método se encarga de limpiar la sesión de los datos de una oficina origen
	 */
	private void removerDatosOficinaOrigenDeSesion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(COficinaOrigen.OFICINA_ORIGEN_NOMBRE);
		session.removeAttribute(COficinaOrigen.OFICINA_ORIGEN_NUMERO);
		session.removeAttribute(COficinaOrigen.OFICINA_ORIGEN_EXENTO_DERECHO_NOTARIAL);
		session.removeAttribute(COficinaOrigen.OFICINA_ORIGEN_DESCRIPCION_NAT_JURIDICA);
		session.removeAttribute(COficinaOrigen.OFICINA_ORIGEN_TELEFONO);
		session.removeAttribute(COficinaOrigen.OFICINA_ORIGEN_DIRECCION);
		session.removeAttribute(COficinaOrigen.OFICINA_ORIGEN_FAX);
		session.removeAttribute(COficinaOrigen.OFICINA_ORIGEN_EMAIL);
		session.removeAttribute(COficinaOrigen.OFICINA_ORIGEN_FUNCIONARIO);
		session.removeAttribute(CCategoria.ID_CATEGORIA);
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param fechaInterfaz
	 * @return
	 */
	private Date darFecha(String fechaInterfaz) {
		java.util.Date date = null;

		try {
			date = DateFormatUtil.parse(fechaInterfaz);
			if(fechaInterfaz.indexOf("-")!=-1){
				return null;
			}
		} catch (ParseException e) {
			return null;
		} catch (Throwable t) {
			return null;
		}		
		
		Calendar calendar = Calendar.getInstance();
		String[] partido = fechaInterfaz.split("/");

		Date fecha = null;

		if (partido.length == 3) {
			int dia = Integer.parseInt(partido[0]);
			int mes = Integer.parseInt(partido[1]) - 1;
			int año = Integer.parseInt(partido[2]);
			calendar.set(año, mes, dia);
			fecha = calendar.getTime();
		}
		return fecha;
	}
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de limpiar la sesión luego que el usuario ha terminado de 
	 * usar las pantallas administrativas relacionadas con Forseti
	 */
	private EvnAdministracionForseti limpiarSesion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
		session.removeAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO);
		session.removeAttribute(AWAdministracionForseti.LISTA_CIRCULO_FESTIVO);
		session.removeAttribute(AWAdministracionForseti.LISTA_CIRCULOS_USUARIO_ELEMENTO);
		session.removeAttribute(AWAdministracionForseti.LISTA_CIRCULOS_ELEMENTO);
		session.removeAttribute(AWAdministracionForseti.LISTA_FOTOCOPIAS);
		session.removeAttribute(AWAdministracionForseti.LISTA_MUNICIPIOS);
		session.removeAttribute(AWAdministracionForseti.LISTA_OFICINAS_POR_VEREDA);
		session.removeAttribute(AWAdministracionForseti.LISTA_VEREDAS);
		session.removeAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES);
		session.removeAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
		session.removeAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);
		session.removeAttribute(AWAdministracionForseti.MAP_VEREDAS);
		session.removeAttribute(AWAdministracionForseti.SELECCION_GRUPO_NAT_JURIDICA);
		session.removeAttribute(AWAdministracionForseti.ADMINISTRADOR_NACIONAL);
		session.removeAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO);
		
		session.removeAttribute(CCirculo.ID_CIRCULO);
		session.removeAttribute(CCirculo.NIT_CIRCULO);
		session.removeAttribute(CCirculo.NOMBRE_CIRCULO);
		session.removeAttribute(CDepartamento.ID_DEPARTAMENTO);
		session.removeAttribute(CMunicipio.ID_MUNICIPIO);
		session.removeAttribute(CZonaRegistral.ID_ZONA_REGISTRAL);
		
		session.removeAttribute(WebKeys.NATURALEZA_JURIDICA);
		session.removeAttribute(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
		session.removeAttribute(CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA);
		session.removeAttribute(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA);
		session.removeAttribute(CNaturalezaJuridica.HABILIDATO_CALIFICACION);
		session.removeAttribute(AWAdministracionForseti.SELECCION_GRUPO_NAT_JURIDICA);
		

		removerDatosOficinaOrigenDeSesion(request);
		session.removeAttribute(CTipoOficina.ID_TIPO_OFICINA);
		session.removeAttribute(AWAdministracionForseti.OFICINA_PARA_EDITAR);
		session.removeAttribute(CCategoria.ID_CATEGORIA);
		session.removeAttribute(COficinaOrigen.OFICINA_ID_OFICINA_ORIGEN);
		
		session.removeAttribute(RESOLUCION_TRASLADO);
		
		session.removeAttribute(AWAdministracionForseti.LISTA_USUARIOS_TRASLADAR);
		session.removeAttribute(AWAdministracionForseti.LISTA_USUARIOS_CREADOS);
		session.removeAttribute(AWAdministracionForseti.ROLES_USUARIOS_CIRCULO_INHAB);
		session.removeAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES_INHABILITA);
		session.removeAttribute(AWAdministracionForseti.CIRCULO_INHABILITADO);
		session.removeAttribute(AWAdministracionForseti.CIRCULO_DESTINO);
		session.removeAttribute(AWAdministracionForseti.LISTA_ZONAS_REGISTRALES_CIRC_INHAB);
		
		session.removeAttribute(CUsuario.LOGIN_USUARIO);
		session.removeAttribute(CUsuario.CLAVE_USUARIO);
		session.removeAttribute(CUsuario.NOMBRE_USUARIO);
		session.removeAttribute(CUsuario.APELLIDO1_USUARIO);
		session.removeAttribute(CUsuario.APELLIDO2_USUARIO);
                /*
                 * @author      : Julio Alcázar Rivas
                 * @change      : se elimina de la session LISTA_MATRICULAS_TRASLADO
                 * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                 */
                session.removeAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
                                /*
                 * @author      : Carlos mario Torres Urina
                 * @change      : se elimina las variables de session del proceso Traslado Masivo
                 * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas*/
                session.removeAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
                session.removeAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR);
                session.removeAttribute("FOLIOS_CONFIRMACION");
                
                /*
                 * @author      : Carlos mario Torres Urina
                 * @change      : se elimina las variables de session del proceso Traslado Masivo
                 * Caso Mantis  : 11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
                 */
                session.removeAttribute(MUNICIPIO_ID+"_ORIGEN");
                session.removeAttribute(MUNICIPIO_ID+"_DESTINO");
                session.removeAttribute(MUNICIPIO_ID+"_NOSIR_DESTINO");
                session.removeAttribute(CIRCULOS_SIR+"_ORIGEN");
                session.removeAttribute(CIRCULOS_SIR);
                session.removeAttribute(CIRCULOS_NOSIR);
                session.removeAttribute(ARCHIVO_ORIGEN);
                session.removeAttribute(RESOLUCION_DESTINO);
                session.removeAttribute("CALENDAR");
                session.removeAttribute("CALENDAR_DESTINO");
                session.removeAttribute(AWReportes.LISTA_MUNICIPIOS+"_ORIGEN");
                session.removeAttribute(AWReportes.LISTA_MUNICIPIOS+"_DESTINO");
                session.removeAttribute("CIRCULO_OFICINA");
		
		return null;
	}



	/**
	* Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	* generar un evento para la edición de un tipo de naturaleza jurídica    
	* @param request
	* @return evento <code>EvnAdministracionForseti</code> con la información del tipo de naturaleza jurídica 
	* @throws AccionWebException
	*/
	private EvnAdministracionForseti editaDetallesNatJuridica(HttpServletRequest request)
		throws AccionWebException {
		
		HttpSession session = request.getSession();

		String id = request.getParameter(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
		if ((id == null) || (id.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un Código para el Tipo de Naturaleza Jurídica.");
		}
		
		String nombre = request.getParameter(CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe Proporcionar un nombre para el Tipo de Naturaleza Jurídica.");
		}

		String idDom = request.getParameter(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA);
		DominioNaturalezaJuridica dominio = new DominioNaturalezaJuridica();
		/* Según nuevo requerimiento ya no es obligatorio asociar un dominio de naturaleza jurídica. */
		if (idDom == null || idDom.equals("") || idDom.equals(WebKeys.SIN_SELECCIONAR)) {
		}
		else
		{
			dominio.setIdDominioNatJur(idDom.toUpperCase());
		}
						
		String habilitado=request.getParameter(CNaturalezaJuridica.HABILIDATO_CALIFICACION);
		
		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		NaturalezaJuridica dato = new NaturalezaJuridica();
		dato.setIdNaturalezaJuridica(id);
		dato.setNombre(nombre);
		dato.setDominioNaturalezaJuridica(dominio);
                /*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
                dato.setVersion(getMaxNaturalezaJuridica(id, session));

		
		String idGrupo = request.getParameter(CGrupoNaturalezaJuridica.ID_GRUPO_NATURALEZA_JURIDICA);
				
		if (idGrupo == null) {
			throw new AccionInvalidaException("Debe Proporcionar un Grupo de Naturaleza Jurídica.");
		}
		GrupoNaturalezaJuridica grupo = new GrupoNaturalezaJuridica();
		grupo.setIdGrupoNatJuridica(idGrupo);
		dato.setGrupoNaturalezaJuridica(grupo);
		
		if (habilitado!=null){
			dato.setHabilitadoCalificacion(true);
		}

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, AWAdministracionForseti.EDITA_DETALLES_NATURALEZA_JURIDICA);
			evento.setNaturalezaJuridica(dato);
			evento.setGrupoNatJuridica(grupo);
			                /**
                * @Author Edgar Lora
                * @Mantis 13176
                */
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(),String.valueOf(usuarioNeg.getIdUsuario()));
                infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                evento.setInfoUsuario(infoUsuario);			
			return evento;
		}
/*
        *  @author Carlos Torres
        *  @chage   se agrega validacion de version diferente
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
    private long getMaxNaturalezaJuridica(String idNaturalezaJuridica, HttpSession session) {
        GrupoNaturalezaJuridica dato = (GrupoNaturalezaJuridica) session.getAttribute(AWAdministracionForseti.SELECCION_GRUPO_NAT_JURIDICA);
        List njs = dato.getNaturalezaJuridicas();
        List filtro = new ArrayList<NaturalezaJuridica>();
        Iterator iNj = njs.iterator();
        while (iNj.hasNext()) {
            NaturalezaJuridica nj = (NaturalezaJuridica) iNj.next();
            if (idNaturalezaJuridica.equals(nj.getIdNaturalezaJuridica())) {
                filtro.add(nj);
            }
        }
        NaturalezaJuridica maxNJ = (NaturalezaJuridica) Collections.max(filtro, new VersionNaturalezaJuridicaComparador());
        if (maxNJ != null) {
            return maxNJ.getVersion();
        } else {
            return 0;
        }
    }



	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente 
	 * de la acción de negocio. 
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

		HttpSession session = request.getSession();
		if (evento instanceof EvnRespAdministracionForseti){
			EvnRespAdministracionForseti respuesta = (EvnRespAdministracionForseti) evento;

			context = session.getServletContext();

			if (respuesta != null) {
				String tipoEvento = respuesta.getTipoEvento();
				if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_DEPARTAMENTO_OK)
					|| tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_MUNICIPIO_OK)
					|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_MUNICIPIO_OK)
					|| tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_VEREDA_OK)
					|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_VEREDA_OK)) {
					Departamento depto = (Departamento) respuesta.getPayload();
					String key = depto.getNombre() + "-" + depto.getIdDepartamento();
					Map mapDeptos = (Map) context.getAttribute(WebKeys.MAPA_DEPARTAMENTOS);
					mapDeptos.put(key, depto);
					if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_DEPARTAMENTO_OK)){
						request.getSession().setAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO,respuesta.getDepartamentos());
					}
					if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_MUNICIPIO_OK)) {
						List departamentos=(List)session.getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO);
						Departamento deptoSel =(Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
						Departamento deptoLista=null;
						Iterator itDeptos=departamentos.iterator();
						while(itDeptos.hasNext()){
							Departamento temp=(Departamento)itDeptos.next();
							if (temp.getIdDepartamento().equals(deptoSel.getIdDepartamento())){
								deptoLista=temp;
							}
						}
						if (deptoLista!=null){
							String id = request.getParameter(CMunicipio.ID_MUNICIPIO);
							String nombre = request.getParameter(CMunicipio.NOMBRE_MUNICIPIO);

							Municipio municipio = new Municipio();
							municipio.setNombre(nombre.toUpperCase());
							municipio.setIdMunicipio(id.toUpperCase());
							municipio.setIdDepartamento(deptoSel.getIdDepartamento());
							deptoLista.addMunicipio(municipio);
							session.setAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO,deptoLista);
						}
						
						seleccionarDepartamentoEnSesion(depto, session);
					}else if(tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_MUNICIPIO_OK)){
						List departamentos=(List)session.getAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO);
						Departamento deptoSel =(Departamento) session.getAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO);
						Departamento deptoLista=null;
						Iterator itDeptos=departamentos.iterator();
						while(itDeptos.hasNext()){
							Departamento temp=(Departamento)itDeptos.next();
							if (temp.getIdDepartamento().equals(deptoSel.getIdDepartamento())){
								deptoLista=temp;
							}
						}
						if (deptoLista!=null){
							String idAlfaNum = request.getParameter(CMunicipio.ID_MUNICIPIO);
							StringTokenizer st=new StringTokenizer(idAlfaNum,"-");
							String nombre=st.nextToken();
							String id=st.nextToken();
							
							Iterator itMunic=deptoLista.getMunicipios().iterator();
							Municipio elim=null;
							while(itMunic.hasNext()){
								Municipio mun=(Municipio)itMunic.next();
								if (mun.getIdMunicipio().equals(id)){
									elim=mun;
									break;
								}
							}
							if (elim!=null){
								deptoLista.removeMunicipio(elim);
							}	
							session.setAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO,deptoLista);
						}
						
						seleccionarDepartamentoEnSesion(depto, session);
						
					}else if (
						tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_VEREDA_OK)
							|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_VEREDA_OK)) {
						seleccionarDepartamentoEnSesion(depto, session);
						Municipio municipioAnterior =
							(Municipio) session.getAttribute(AWAdministracionForseti.MUNICIPIO_SELECCIONADO);
						Map mapMunicipiosActualizados =
							(Map) session.getAttribute(AWAdministracionForseti.MAP_MUNICIPIOS);
						String llave =
							municipioAnterior.getNombre() + "-" + municipioAnterior.getIdMunicipio();
						Municipio municipioActualizado = (Municipio) mapMunicipiosActualizados.get(llave);
						seleccionarMunicipioEnSesion(municipioActualizado, session);
					}
					return;
				} else if (tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_DEPARTAMENTO_OK)) {
					List elementos = (List) respuesta.getPayload();
					Map mapDeptos = Collections.synchronizedMap(new TreeMap());
					for (Iterator itDepto = elementos.iterator(); itDepto.hasNext();) {
						Departamento depto = (Departamento) itDepto.next();
						String key = depto.getNombre() + "-" + depto.getIdDepartamento();
						mapDeptos.put(key, depto);
					}
					context.setAttribute(WebKeys.MAPA_DEPARTAMENTOS, mapDeptos);
					request.getSession().setAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO,respuesta.getPayload());
					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_ESTADO_FOLIO_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_ESTADO_FOLIO_OK)) {
					List elementos = (List) respuesta.getPayload();
					context.setAttribute(WebKeys.LISTA_ESTADOS_FOLIO, elementos);
					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_TIPO_OFICINA_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_TIPO_OFICINA_OK)) {
					List elementos = (List) respuesta.getPayload();
					context.setAttribute(WebKeys.LISTA_TIPOS_OFICINA, elementos);
					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_TIPO_DOCUMENTO_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_TIPO_DOCUMENTO_OK)) {
					List elementos = (List) respuesta.getPayload();
					List elementosTipoDoc = new ArrayList();
					Iterator itTipoDocumento = elementos.iterator();
					while (itTipoDocumento.hasNext()) {
						TipoDocumento tipoDoc = (TipoDocumento) itTipoDocumento.next();
						elementosTipoDoc.add(
							new ElementoLista(tipoDoc.getIdTipoDocumento(), tipoDoc.getNombre()));
					}
					context.setAttribute(
						WebKeys.LISTA_TIPOS_DOCUMENTO,
						Collections.unmodifiableList(elementosTipoDoc));

					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_TIPO_PREDIO_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_TIPO_PREDIO_OK)) {
					List elementos = (List) respuesta.getPayload();
					List elementosTipoDoc = new ArrayList();
					Iterator itPredio = elementos.iterator();
					List elementosPredio = new ArrayList();
					while (itPredio.hasNext()) {
						TipoPredio predio = (TipoPredio) itPredio.next();
						elementosPredio.add(new ElementoLista(predio.getIdPredio(), predio.getNombre()));
					}
					context.setAttribute(
						WebKeys.LISTA_TIPOS_PREDIO,
						Collections.unmodifiableList(elementosPredio));
					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_GRUPO_NAT_JURIDICA_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_GRUPO_NAT_JURIDICA_OK)) {
					List elementos = (List) respuesta.getPayload();
					context.setAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS, elementos);
					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_TIPO_NAT_JURIDICA_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_TIPO_NAT_JURIDICA_OK)) {

					GrupoNaturalezaJuridica grupoActual =
						(GrupoNaturalezaJuridica) session.getAttribute(SELECCION_GRUPO_NAT_JURIDICA);
					
					List elementos = (List) respuesta.getPayload();
					GrupoNaturalezaJuridica grupoActualizado = null;
					for (Iterator iter = elementos.iterator(); iter.hasNext();) {
						GrupoNaturalezaJuridica dato = (GrupoNaturalezaJuridica) iter.next();
						if (dato.getIdGrupoNatJuridica().equals(grupoActual.getIdGrupoNatJuridica())) {
							grupoActualizado = dato;
							break;
						}
					}
                                       /*
                                        *  @author Carlos Torres
                                        *  @chage  
                                        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
                                        */
					context.setAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS_V, elementos);
                                        context.setAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS,respuesta.getListaNatJuridica());
					session.setAttribute(SELECCION_GRUPO_NAT_JURIDICA, grupoActualizado);
					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_CIRCULO_REGISTRAL_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_CIRCULO_REGISTRAL_OK)) {
					List elementos = (List) respuesta.getPayload();
					context.setAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES, elementos);
					return;
				} else if (tipoEvento.equals(EvnRespAdministracionForseti.EDITA_CIRCULO_NIT_OK)) {
					List elementos = (List) respuesta.getPayload();
					context.setAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES, elementos);
					session.removeAttribute(CCirculo.ID_CIRCULO);
					session.removeAttribute(CCirculo.NIT_CIRCULO);
					session.removeAttribute(CCirculo.NOMBRE_CIRCULO);
					return;
				} else if (tipoEvento.equals(EvnRespAdministracionForseti.EDITA_CIRCULO_OK)) {
					List elementos = (List) respuesta.getPayload();
					context.setAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES, elementos);
					session.removeAttribute(CCirculo.ID_CIRCULO);
					session.removeAttribute(CCirculo.NIT_CIRCULO);
					session.removeAttribute(CCirculo.NOMBRE_CIRCULO);
					session.removeAttribute(CCirculo.IMPUESTO_CIRCULO);
					return;
				} else if (tipoEvento.equals(EvnRespAdministracionForseti.EDITA_NATURALEZA_JURIDICA_OK)) {
					
					session.removeAttribute(WebKeys.NATURALEZA_JURIDICA);
					session.removeAttribute(CNaturalezaJuridica.ID_NATURALEZA_JURIDICA);
					session.removeAttribute(CNaturalezaJuridica.NOMBRE_NATURALEZA_JURIDICA);
					session.removeAttribute(CNaturalezaJuridica.HABILIDATO_CALIFICACION);
					session.removeAttribute(CNaturalezaJuridica.DOMINIO_NATURALEZA_JURIDICA);

					List elementos = (List) respuesta.getPayload();

					GrupoNaturalezaJuridica grupoActualizado = null;
					for (Iterator iter = elementos.iterator(); iter.hasNext();) {
						GrupoNaturalezaJuridica dato = (GrupoNaturalezaJuridica) iter.next();
						if (dato.getIdGrupoNatJuridica().equals(request.getParameter(CGrupoNaturalezaJuridica.ID_GRUPO_NATURALEZA_JURIDICA))) {
							grupoActualizado = dato;
							break;
						}
					}
					context.setAttribute(WebKeys.LISTA_GRUPOS_NATURALEZAS_JURIDICAS, elementos);
					session.setAttribute(SELECCION_GRUPO_NAT_JURIDICA, grupoActualizado);
				return;
			}  
				else if (tipoEvento.equals(EvnRespAdministracionForseti.SELECCIONA_NATURALEZA_JURIDICA_OK)) {
					NaturalezaJuridica nat = (NaturalezaJuridica) respuesta.getPayload();
					session.setAttribute(WebKeys.NATURALEZA_JURIDICA, nat);
					return;
			}  
				else if (
					tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_ESTADO_ANOTACION_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_ESTADO_ANOTACION_OK)) {
					List elementos = (List) respuesta.getPayload();
					context.setAttribute(WebKeys.LISTA_ESTADOS_ANOTACION, elementos);
					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_DOMINIO_NAT_JURIDICA_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_DOMINIO_NAT_JURIDICA_OK)) {
					List elementos = (List) respuesta.getPayload();
					List elementosDominios = new ArrayList();
					for (Iterator iter = elementos.iterator(); iter.hasNext();) {
						DominioNaturalezaJuridica dominio = (DominioNaturalezaJuridica) iter.next();
						elementosDominios.add(
							new ElementoLista(dominio.getIdDominioNatJur(), dominio.getNombre()));
					}
					context.setAttribute(
						WebKeys.LISTA_DOMINIOS_NATURALEZA_JURIDICA,
						Collections.unmodifiableList(elementosDominios));
					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.SELECCIONA_CIRCULO_FESTIVO_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_CIRCULO_FESTIVO_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_CIRCULO_FESTIVO_OK)) {
					List elementos = (List) respuesta.getPayload();
					session.setAttribute(LISTA_CIRCULO_FESTIVO, elementos);
					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_EJE_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_EJE_OK)) {
					List elementos = (List) respuesta.getPayload();

					Iterator itEjes = elementos.iterator();
					List elementosEjes = new ArrayList();
					while (itEjes.hasNext()) {
						Eje eje = (Eje) itEjes.next();
						elementosEjes.add(new ElementoLista(eje.getIdEje(), eje.getNombre()));
					}
					context.setAttribute(WebKeys.LISTA_EJES_DIRECCION, elementosEjes);
					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_ZONA_REGISTRAL_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_ZONA_REGISTRAL_OK)) {
					List elementos = (List) respuesta.getPayload();
					session.setAttribute(LISTA_ZONAS_REGISTRALES, elementos);
					if (tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_ZONA_REGISTRAL_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_ZONA_REGISTRAL_OK)) {
						session.removeAttribute(CZonaRegistral.ID_ZONA_REGISTRAL);
					}
					if (elementos.size() > 0 && (elementos.get(elementos.size() - 1) instanceof Circulo)) {
						session.setAttribute(
							CIRCULO_TRASLADO,
							elementos.remove(elementos.size() - 1));
					}
					return;
				} else if (tipoEvento.equals(EvnRespAdministracionForseti.TRASLADO_OK)) {
                                         /*
                                          * @author      :   Julio Alcázar Rivas
                                          * @change      :   Se cambia el resultado en session del proceso traslado
                                          * Caso Mantis  :   07123
                                          */
					HashMap resultado = (HashMap) respuesta.getPayload();
					session.setAttribute(FOLIO_TRASLADADO, resultado);
                                        session.removeAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
                                        session.removeAttribute(RESOLUCION_TRASLADO);
                                        session.removeAttribute(CFolio.ID_MATRICULA);
                                        session.removeAttribute(CIRCULOS_SIR);
                                        /**
                                         * @Autor: Edgar Lora
                                         * @Mantis: 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                         */ 
                                        session.removeAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
                                        /*
                                         * @author      : Julio Alcázar Rivas
                                         * @change      : elimina atributos de la session
                                         * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                                         */
                                        session.removeAttribute(CIRCULOS_NOSIR);
                                        session.removeAttribute(CIRCULO_OFICINA);
					return;
				}else if (tipoEvento.equals(EvnRespAdministracionForseti.REABRIR_FOLIO_OK)) {
					Folio folio = (Folio) respuesta.getPayload();
					session.setAttribute(WebKeys.FOLIO, folio);
					session.setAttribute(WebKeys.REABIERTO_FOLIO, new Boolean(true));
					return;
				}  
				else if (tipoEvento.equals(EvnRespAdministracionForseti.CARGAR_PLANTILLAS_CERTIFICADOS)) {
					session.setAttribute(LISTA_PLANTILLAS, respuesta.getListPlantillas());
					return;
				} else if (
					tipoEvento.equals(EvnRespAdministracionForseti.ADICIONA_OFICINA_ORIGEN_OK)
						|| tipoEvento.equals(EvnRespAdministracionForseti.ELIMINA_OFICINA_ORIGEN_OK)
						|| tipoEvento.equals(
							EvnRespAdministracionForseti.CONSULTA_OFICINA_ORIGEN_BY_VEREDA_OK)) {
					List elementos = (List) respuesta.getPayload();
					String tipoOf = (String) session.getAttribute(CTipoOficina.ID_TIPO_OFICINA);
					if(!tipoOf.equals(WebKeys.SIN_SELECCIONAR)){
						Iterator itElm = elementos.iterator();
						List nElementos = new ArrayList();
						while(itElm.hasNext()){
							OficinaOrigen dato = (OficinaOrigen) itElm.next();
							if(dato.getTipoOficina().getIdTipoOficina().equals(tipoOf)){
								nElementos.add(dato);
							}
						}
						session.setAttribute(LISTA_OFICINAS_POR_VEREDA, nElementos);
					} else {
						session.setAttribute(LISTA_OFICINAS_POR_VEREDA, elementos);					
					}
					if (!(tipoEvento
						.equals(EvnRespAdministracionForseti.CONSULTA_OFICINA_ORIGEN_BY_VEREDA_OK)) || request.getParameter(WebKeys.ACCION).equals(ENVIAR_OFICINA_EDICION)) {
						removerDatosOficinaOrigenDeSesion(request);
					}				
					return;
				} else if(tipoEvento.equals(EvnRespAdministracionForseti.OFICINA_ORIGEN_EDICION)){
					session.setAttribute(AWAdministracionForseti.OFICINA_PARA_EDITAR,respuesta.getPayload());
				} else if (tipoEvento.equals(EvnRespAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO)){
					session.setAttribute(AWAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO, respuesta.getPayload());
				} else if (tipoEvento.equals(EvnRespAdministracionForseti.CONSULTA_DEPARTAMENTO)){
					Departamento depto=(Departamento)respuesta.getPayload();
					List municipios = depto.getMunicipios();
					List municElemLista=new ArrayList();
					//		  Organizar alfabeticamente
					Map mapMunicipios = Collections.synchronizedMap(new TreeMap());
					for (Iterator itMun = municipios.iterator(); itMun.hasNext();) {
						Municipio municipio = (Municipio) itMun.next();
						String llave = municipio.getNombre() + "-" + municipio.getIdMunicipio();
						mapMunicipios.put(llave, municipio);
						ElementoLista elem=new ElementoLista();
						elem.setId(municipio.getNombre()+"-"+municipio.getIdMunicipio());
						elem.setValor(municipio.getNombre());
						municElemLista.add(elem);
					}

					session.setAttribute(AWAdministracionForseti.DEPARTAMENTO_SELECCIONADO, depto);
					session.setAttribute(AWAdministracionForseti.MAP_MUNICIPIOS, mapMunicipios);
					session.setAttribute(AWAdministracionForseti.LISTA_MUNICIPIOS, municElemLista);
					session.setAttribute(CDepartamento.ID_DEPARTAMENTO, depto.getNombre()+"-"+depto.getIdDepartamento());
					session.removeAttribute(AWAdministracionForseti.LISTA_VEREDAS);
					session.removeAttribute(CMunicipio.ID_MUNICIPIO);
				}else if (tipoEvento.equals(EvnRespAdministracionForseti.INHABILITAR_CIRCULO_OK)){
					limpiarSesion(request);
					if (respuesta.getPayload()!=null){
						request.getSession().getServletContext().setAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES,respuesta.getPayload());	
					}
				}else if (tipoEvento.equals(EvnRespAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_INHAB_OK)) {
					List elementos = (List) respuesta.getPayload();
					session.setAttribute(LISTA_ZONAS_REGISTRALES, elementos);
					List zonasInhab=respuesta.getListaZonasInhabilitar();
					if (zonasInhab!=null){
						session.setAttribute(LISTA_ZONAS_REGISTRALES_CIRC_INHAB,zonasInhab);
					}
					return;
				}else if (tipoEvento.equals(EvnRespAdministracionForseti.CARGAR_CIRCULOS_INHABILITADOS_OK)) {
					List elementos = (List) respuesta.getPayload();
					if (elementos!=null){
						session.setAttribute(AWAdministracionForseti.LISTA_ELEMENTOS_CIRCULO_INHABILITADO,elementos);
					}
					return;
				}
                                /*
                                 * @author      :   Julio Alcázar Rivas
                                 * @change      :   Se agrega el resultado en session del proceso VALIDAR_MATRICULA
                                 * Caso Mantis  :   07123
                                 */
                                else if (respuesta.getTipoEvento().equalsIgnoreCase(EvnRespAdministracionForseti.VALIDAR_MATRICULA)) {
                                      String matricula = (String) respuesta.getMatricula();

                                      if (matricula != null) {
                                             List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
                                             if (matriculas == null) {
                                                    matriculas = new ArrayList();
                                             }
                                             matriculas.add(matricula);
                                             request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO, matriculas);
                                             
                                      }                                   

                                }else if (respuesta.getTipoEvento().equalsIgnoreCase(EvnRespAdministracionForseti.VALIDAR_MATRICULA_MASIVO)) {
                                     /*
                                 * @author      :   Carlos Mario Torres Urina
                                 * @change      :   Se agrega el resultado en session del proceso VALIDAR_MATRICULA_MASIVO
                                 * Caso Mantis  :    11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
                                 */
                                      List<String> matricula = respuesta.getListaMatirculas();

                                      if (matricula != null) {
                                             List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
                                             if (matriculas == null) {
                                                    matriculas = new ArrayList();
                                             }
                                             matriculas.clear();
                                             matriculas.addAll(matricula);
                                             request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO, matriculas);
                                      }

                                } else if (tipoEvento.equals(EvnRespAdministracionForseti.TRASLADO_MASIVO_OK)) {
                                        
                			HashMap resultado = (HashMap) respuesta.getPayload();
					session.setAttribute(FOLIO_TRASLADADO, resultado);
                                        session.setAttribute("ERRORES",""+respuesta.getErrores().size());
                                        session.removeAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
                                        session.removeAttribute(RESOLUCION_TRASLADO);
                                        session.removeAttribute(CFolio.ID_MATRICULA);
                                        session.removeAttribute(CIRCULOS_SIR);
                                        session.removeAttribute(CIRCULOS_NOSIR);
                                        session.removeAttribute(CIRCULO_OFICINA);
                                           /*
                                            * @author      : Carlos Mario Torres Urina
                                            * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                            */
                                        session.removeAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR+"_DESTINO");
                                        session.removeAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR);
					session.removeAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
                                        return;
				} else if (tipoEvento.equals(EvnRespAdministracionForseti.TRASLADO_CONFIRMACION_MASIVO)) {
                                        session.removeAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
                                        session.removeAttribute(RESOLUCION_TRASLADO);
                                        session.removeAttribute(RESOLUCION_DESTINO);
                                        session.removeAttribute(CFolio.ID_MATRICULA);
                                        session.removeAttribute(CIRCULOS_SIR);
                                        session.removeAttribute(CIRCULOS_NOSIR);
                                        session.removeAttribute(CIRCULO_OFICINA);
                                         /*
                                            * @author      : Carlos Mario Torres Urina
                                            * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                            */
                                        session.removeAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS+"_DESTINO");
                                        session.removeAttribute("FOLIOS_CONFIRMACION");
                                        return;
				}
                                /*
                                 * @author      : Julio Alcázar Rivas
                                 * @change      : manejo de la respuesta en los procesos de traslado
                                 * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                                 */
                                else if (tipoEvento.equals(EvnRespAdministracionForseti.FOLIO_CONFIRMACION)) {
                                        Folio resultado = (Folio) respuesta.getPayload();
					session.setAttribute(AWAdministracionForseti.FOLIO_CONFIRMACION, resultado);

					return;
                                } else if (tipoEvento.equals(EvnRespAdministracionForseti.TRASLADO_CONFIRMACION)) {
                                        /*
                                         * @author      : Julio Alcázar Rivas
                                         * @change      : elimina atributos de la session
                                         * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                                         */
                                        session.removeAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
                                        session.removeAttribute(RESOLUCION_TRASLADO);
                                        session.removeAttribute(RESOLUCION_DESTINO);
                                        session.removeAttribute(CFolio.ID_MATRICULA);
                                        session.removeAttribute(CIRCULOS_SIR);
                                        session.removeAttribute(CIRCULOS_NOSIR);
                                        session.removeAttribute(CIRCULO_OFICINA);
                                        /**
                                         * @Autor: Edgar Lora
                                         * @Mantis: 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                         */ 
                                        session.removeAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
                                        return;
                                }

			}
		}else if (evento instanceof EvnRespAdministracionFenrir){
			EvnRespAdministracionFenrir respuesta = (EvnRespAdministracionFenrir) evento;
			if (respuesta != null) {
				String tipoEvento = respuesta.getTipoEvento();
				if (tipoEvento.equals(EvnRespAdministracionFenrir.CONSULTA_USUARIOS_POR_CIRCULO_OK)) {
					List elementos = (List) respuesta.getPayload();
					session.setAttribute(AWAdministracionFenrir.LISTA_USUARIOS, elementos);
					return;
				}
			}
		}
		
	}


/*
 * @author      :   Julio Alcázar Rivas
 * @change      :   Se agrega el metodo agregarMatricula del proceso VALIDAR_MATRICULA
 * Caso Mantis  :   07123
 */
private EvnAdministracionForseti agregarMatricula(HttpServletRequest request) throws
		ValidacionTrasladoException {

	 ValidacionTrasladoException e = new
			 ValidacionTrasladoException();
         List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
	 if (matriculas == null) {
		matriculas = new ArrayList();
	 }

	 String matricula = request.getParameter(CFolio.ID_MATRICULA);

	 if (matricula == null || matricula.trim().equals("")) {
		e.addError("Matrícula inválida");
		
	 }

	 if (matriculas.contains(matricula)) {
		e.addError("La matrícula que quiere ingresar, ya fue ingresada");
		
	 }        

         if(!e.getErrores().isEmpty()){
             throw e;
         }

         Folio folio = new Folio();
	 folio.setIdMatricula(matricula);

	 org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.
		  modelo.transferObjects.Usuario) request.getSession().getAttribute(
		  SMARTKeys.USUARIO_EN_SESION);
         EvnAdministracionForseti evento = new EvnAdministracionForseti(usuario, EvnAdministracionForseti.VALIDAR_MATRICULA);
         evento.setFolio(folio);
         return evento;

  }

    /**
     * @author     : Ellery David Robles Gómez
     * @change     : Metodo que agrega fundamentos seleccionados a una lista
     * Caso Mantis : 14985
     */
    private void agregarFundamento(HttpServletRequest request) throws ValidacionTrasladoException
    {
        ValidacionTrasladoException e = new ValidacionTrasladoException();
        List tiposFundamentos = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR);
        
        List fundamentos = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
        if (fundamentos == null) {
            fundamentos = new ArrayList();
        }
        
        String fundamento = new String();
        String tipoFundamento = request.getParameter(AWAdministracionForseti.TIPOS_FUNDAMENTO_SIR);
        String nombreFundamento = null;
        Iterator item = tiposFundamentos.iterator();
        while (item.hasNext()) {
            ElementoLista f = (ElementoLista) item.next();
            if (f.getId().equals(tipoFundamento)) {
                nombreFundamento = f.getValor();
            }
        }
        String numeroFundamento = request.getParameter(CFolio.NUMERO_FUNDAMENTO);
        String fechaFundamento = request.getParameter(CFolio.CALENDAR);
        
        Date dFechIni = null;
        if ((fechaFundamento == null) || (fechaFundamento.trim().length() == 0)) {
                e.addError("Debe seleccionar una fecha de fundamento.");
        } else {
                try {
                        dFechIni = DateFormatUtil.parse(fechaFundamento);
                } catch (ParseException pe) {
                        e.addError("Fecha del fundamento tiene formato inválido");
                }
                if(dFechIni.after(new Date())){
                    e.addError("La fecha del fundamento no puede ser superior a la fecha actual.");
                }
        }
        if(numeroFundamento!=null && numeroFundamento.trim().length() > 0){
            try{
                int num = Integer.parseInt(numeroFundamento);
            }
            catch(Throwable en){
                e.addError("El numero de fundamento debe ser un entero valido");
            }
        }
        if ((!"SIN_SELECCIONAR".equals(tipoFundamento)) && (numeroFundamento != null) && (tipoFundamento != null) && (fechaFundamento != null) ) {
            fundamento = tipoFundamento+";"+nombreFundamento+";"+numeroFundamento+";"+fechaFundamento;
        }
        
        if (fundamentos.contains(fundamento)) {
            e.addError("El fundamento que deseas ingresar, ya fue ingresado");
        }
        
        if (!e.getErrores().isEmpty()) {
            throw e;
        }
        
        if (!fundamento.isEmpty()) {
            fundamentos.add(fundamento);
        }
        
        request.getSession().setAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS, fundamentos);
    }
        /**
     * @author     : Ctorres
     * @change     : Metodo que elimina fundamentos seleccionados de una lista
     * Caso Mantis : 14985
     */
    private void agregarFundamentoDestino(HttpServletRequest request) throws ValidacionTrasladoException
    {
        ValidacionTrasladoException e = new ValidacionTrasladoException();
        List tiposFundamentos = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_SIR+"_DESTINO");
        
        List fundamentos = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS+"_DESTINO");
        if (fundamentos == null) {
            fundamentos = new ArrayList();
        }
        
        String fundamento = new String();
        String tipoFundamento = request.getParameter(AWAdministracionForseti.TIPOS_FUNDAMENTO_SIR+"_DESTINO");
        String nombreFundamento = null;
        Iterator item = tiposFundamentos.iterator();
        while (item.hasNext()) {
            ElementoLista f = (ElementoLista) item.next();
            if (f.getId().equals(tipoFundamento)) {
                nombreFundamento = f.getValor();
            }
        }
        String numeroFundamento = request.getParameter(CFolio.NUMERO_FUNDAMENTO+"_DESTINO");
        String fechaFundamento = request.getParameter(CFolio.CALENDAR+"_DESTINO");
        Date dFechIni = null;
        if ((fechaFundamento == null) || (fechaFundamento.trim().length() == 0)) {
                e.addError("Debe seleccionar una fecha de fundamento.");
        } else {
                try {
                        dFechIni = DateFormatUtil.parse(fechaFundamento);
                } catch (ParseException pe) {
                        e.addError("Fecha del fundamento tiene formato inválido");
                }
                if(dFechIni.after(new Date())){
                    e.addError("La fecha del fundamento no puede ser superior a la fecha actual.");
                }
        }
        if(numeroFundamento!=null && numeroFundamento.trim().length() > 0){
            try{
                int num = Integer.parseInt(numeroFundamento);
            }
            catch(Throwable en){
                e.addError("El numero de fundamento debe ser un entero valido");
            }
        }
        if ((!"SIN_SELECCIONAR".equals(tipoFundamento)) && (numeroFundamento != null) && (tipoFundamento != null) && (fechaFundamento != null) ) {
            fundamento = tipoFundamento+";"+nombreFundamento+";"+numeroFundamento+";"+fechaFundamento;
        }
        
        if (fundamentos.contains(fundamento)) {
            e.addError("El fundamento que deseas ingresar, ya fue ingresado");
        }
        
        if (!e.getErrores().isEmpty()) {
            throw e;
        }
        
        if (!fundamento.isEmpty()) {
            fundamentos.add(fundamento);
        }
        
        request.getSession().setAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS+"_DESTINO", fundamentos);
        if(request.getParameterValues("FOLIOS_CONFIRMACION")!=null){
            request.getSession().setAttribute("FOLIOS_CONFIRMACION", Arrays.asList(request.getParameterValues("FOLIOS_CONFIRMACION")));
        }
    }
    
    /**
     * @author     : Ellery David Robles Gómez
     * @change     : Metodo que elimina fundamentos seleccionados de una lista
     * Caso Mantis : 14985
     */
    private void eliminarFundamento(HttpServletRequest request) throws ValidacionTrasladoException
    {
        ValidacionTrasladoException e = new ValidacionTrasladoException();
        List fundamentos = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
        
        if (fundamentos == null) {
            fundamentos = new ArrayList();
        }
        
        String[] fundamentosEliminados = request.getParameterValues(WebKeys.ELIMINAR_FUNDAMENTOS_CHECKBOX);
        
        if (fundamentosEliminados != null) {
            for (int i = 0; i < fundamentosEliminados.length; i++) {
                String actual = fundamentosEliminados[i];
                fundamentos.remove(actual);
            }
        }
        
        if (!e.getErrores().isEmpty()) {
            throw e;
        }
        
        request.getSession().setAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS, fundamentos);
    }
    
    private void eliminarFundamentoDestino(HttpServletRequest request) throws ValidacionTrasladoException
    {
        ValidacionTrasladoException e = new ValidacionTrasladoException();
        List fundamentos = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS+"_DESTINO");
        
        if (fundamentos == null) {
            fundamentos = new ArrayList();
        }
        
        String[] fundamentosEliminados = request.getParameterValues(WebKeys.ELIMINAR_FUNDAMENTOS_CHECKBOX+"_DESTINO");
        
        if (fundamentosEliminados != null) {
            for (int i = 0; i < fundamentosEliminados.length; i++) {
                String actual = fundamentosEliminados[i];
                fundamentos.remove(actual);
            }
        }
        
        if (!e.getErrores().isEmpty()) {
            throw e;
        }
        
        request.getSession().setAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS+"_DESTINO", fundamentos);
         if(request.getParameterValues("FOLIOS_CONFIRMACION")!=null){
            request.getSession().setAttribute("FOLIOS_CONFIRMACION", Arrays.asList(request.getParameterValues("FOLIOS_CONFIRMACION")));
        }
    }
    
        /**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de trasladoMasivo
	 * @param request
	 * @return evento <code>EvnAdministracionForseti</code>
	 * @throws AccionWebException
         * @author      :   Carlos Mario Torres Urina
         * @change      :   modificaciones sustanciales al comportamiento del metodo
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
 	 */
	private EvnAdministracionForseti trasladarMasivo(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		ValidacionTrasladoException exception = new ValidacionTrasladoException();

		List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
                if (matriculas == null) {
                    exception.addError("Debe ingresar al menos una matricula");
                }else{
                    session.setAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO, matriculas);
                }
                 /* @author      :   Carlos Mario Torres Urina
                 * @change      :   modificaciones sustanciales al comportamiento del metodo
                 * Caso Mantis  :  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                 */                
                List fundamentos = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
                if (fundamentos == null) {
                    exception.addError("Debe ingresar al menos un fundamento");
                }else{
                    session.setAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS, fundamentos);
                }
                
                String circulo_oficina = request.getParameter(AWAdministracionForseti.CIRCULO_OFICINA);
                
                if ((circulo_oficina == null) || (circulo_oficina.trim().length() == 0)) {
			exception.addError("Debe ingresar un tipo de oficinas SIR o FOLIO para el traslado");
		} else {
			session.setAttribute(CIRCULO_OFICINA, circulo_oficina);
		}

                String cirdestino = null;
                String mundestino = null;
                String depdestino = null;
                if (circulo_oficina.equals("S")){
                    cirdestino = request.getParameter(AWAdministracionForseti.CIRCULOS_SIR);
                    if ((cirdestino == null) || (cirdestino.trim().length() == 0) || cirdestino.equals(WebKeys.SIN_SELECCIONAR)) {
                            exception.addError("Debe Seleccionar un círculo de destino.");
                    } else {
                            session.setAttribute(AWAdministracionForseti.CIRCULOS_SIR, cirdestino);
                    }
                    depdestino = request.getParameter(AWAdministracionForseti.DEPARTAMENTO_ID+"_DESTINO");
                    if((depdestino==null) || (depdestino.trim().length()==0) || depdestino.equals(WebKeys.SIN_SELECCIONAR))
                    {
                                exception.addError("Debe Seleccionar un municipio de destino.");
                    }else
                    {
                        session.setAttribute(AWAdministracionForseti.DEPARTAMENTO_ID+"_DESTINO", depdestino);
                    }
                    mundestino = request.getParameter(AWAdministracionForseti.MUNICIPIO_ID+"_DESTINO");
                    if((mundestino==null) || (mundestino.trim().length()==0) || mundestino.equals(WebKeys.SIN_SELECCIONAR))
                    {
                                exception.addError("Debe Seleccionar un municipio de destino.");
                    }else
                    {
                        session.setAttribute(AWAdministracionForseti.MUNICIPIO_ID+"_DESTINO", mundestino);
                    }
                    
                }else{
                    cirdestino = request.getParameter(AWAdministracionForseti.CIRCULOS_NOSIR);
                    if ((cirdestino == null) || (cirdestino.trim().length() == 0) || cirdestino.equals(WebKeys.SIN_SELECCIONAR)) {
                            exception.addError("Debe Seleccionar un círculo.");
                    } else {
                            session.setAttribute(AWAdministracionForseti.CIRCULOS_NOSIR, cirdestino);
                    }
                }


                String cirorigen =null;
                if ( request.getSession().getAttribute(CIRCULOS_SIR+"_ORIGEN") != null ) {
                    
                    cirorigen = (String)request.getSession().getAttribute(CIRCULOS_SIR+"_ORIGEN");
                    if ((cirorigen == null) || (cirorigen.trim().length() == 0) ) {
                            exception.addError("No se tiene circulo origen.");
                    }
                }

               String municipioOrigen =null;
                if ( request.getSession().getAttribute(MUNICIPIO_ID+"_ORIGEN") != null ) {
                    municipioOrigen = request.getSession().getAttribute(MUNICIPIO_ID+"_ORIGEN").toString();
                    if ((municipioOrigen == null) || (municipioOrigen.trim().length() == 0) ) {
                            exception.addError("No se tiene circulo origen.");
                    }
                }
                /*------------------Archivo de Origen------------------------------------*/
                FileItem archivoorigen = (FileItem) session.getAttribute(ARCHIVO_ORIGEN);
                /*---------------------------------------------------------------------------------*/


         	if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);


		Circulo circuloorigen = new Circulo();
		circuloorigen.setIdCirculo(cirorigen);

                Circulo circulodestino = new Circulo();
		circulodestino.setIdCirculo(cirdestino);
                
                Municipio municipiodestino = new Municipio();
                municipiodestino.setIdMunicipio(mundestino);
                
                Departamento departamentoDestino = new Departamento();
                departamentoDestino.setIdDepartamento(depdestino);

		gov.sir.core.negocio.modelo.Usuario usuarioSIR =
			(gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		EvnAdministracionForseti evento =
			new EvnAdministracionForseti(usuario, EvnAdministracionForseti.TRASLADAR_MASIVO, usuarioSIR);
                evento.setCirculoOrigen(circuloorigen);
                evento.setCirculoDestino(circulodestino);
                evento.setMatriculas(matriculas);
                 /* @author      :   Carlos Mario Torres Urina
                 * @change      :   modificaciones sustanciales al comportamiento del metodo
                 * Caso Mantis  :  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                 */    
		//evento.setResolucion(resolucion);
                //evento.setFechaResolucionOrigen(dFechIni);
                evento.setFundamentos(fundamentos);
                evento.setTipo_circulo(circulo_oficina);
                evento.setMunicipioOrigen(municipioOrigen);
                evento.setMunicipio(municipiodestino);
                evento.setIdsession(session.getId());
                evento.setArchivoOrigen(archivoorigen);
                evento.setDepartamento(departamentoDestino);
                return evento;
	}
        
/*
 * @author      :   Carlos Mario Torres Uirina
 * @change      :   Se agrega el metodo agregarMatriculaMasivo del proceso VALIDAR_MATRICULA_MASIVO
 * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
 */
private EvnAdministracionForseti agregarMatriculasMasivo(HttpServletRequest request) throws
		ValidacionTrasladoException,AccionWebException {

	 ValidacionTrasladoException e = new ValidacionTrasladoException();
         
      
         uploadFileMatriculas(request);
         
         List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
         if(matriculas == null)
         {
             e.addError("No se cargo el archivo o el archivo esta vasio");
             throw e;
         }
         request.getSession().removeAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);
	 org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.
		  modelo.transferObjects.Usuario) request.getSession().getAttribute(
		  SMARTKeys.USUARIO_EN_SESION);
         EvnAdministracionForseti evento = new EvnAdministracionForseti(usuario, EvnAdministracionForseti.VALIDAR_MATRICULA_MASIVO);
         evento.setMatriculas(matriculas);
         return evento;

  }
/*
 * @author      :   Carlos Mario Torres Uirina
 * @change      :   Procesa el archivo con las matriculas que se quieren trasladar
 * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
 */
private void uploadFileMatriculas(HttpServletRequest request) throws AccionWebException {
        ValidacionTrasladoException exception = new ValidacionTrasladoException();
        boolean errorFormato = false, errorMatriculasInvalidas = false;

        String strErrorFormato = "El formato de los registros debe ser MATRICULA,ANOTACIONES,ID MUNICIPIO,MUNICIPIO,ID_VEREDA,VEREDA,TURNOS,ESTADO. Se presentaron errores en:";
        String strErrorMatriculasInvalidas = "Las matriculas a liquidar deben pertenecer al mismo circulo. Se presentaron errores para las matrículas:";
        List lstMatirculas = new ArrayList(), lstFormato = new ArrayList();
        List matriculasInvalidas = new ArrayList();
        Object idCirculo = request.getSession().getAttribute(AWAdministracionForseti.CIRCULOS_SIR + "_ORIGEN");
        Object idMunicipio = request.getSession().getAttribute(AWAdministracionForseti.MUNICIPIO_ID + "_ORIGEN");
        
        if (idCirculo == null && idMunicipio == null) {
            exception.addError("Debe seleccionar Circulo, Municipio");
        } else {

            try {
                DiskFileUpload upload = new DiskFileUpload();
                List list = upload.parseRequest(request);
                Iterator it = list.iterator();
                HttpSession session = request.getSession();

                boolean sw = true;
                while (it.hasNext()) {
                    FileItem fi = (FileItem) it.next();

                    if (!fi.isFormField()) {
                        String fileName = fi.getName();
                        sw=false;
                        if (!fileName.endsWith(".csv")) {
                            exception.addError("El archivo debe tener extensión .csv, y el procesado no corresponde: " + fileName);
                        } else {
                            if (fi.getContentType().equals(CONTENT_TYPE)) {
                                BufferedReader in = new BufferedReader(new InputStreamReader(fi.getInputStream()));

                                String record = "", recArray[] = null, strMat = "";

                                Circulo circ = null;
                                boolean isfirst = true;
                                while ((record = in.readLine()) != null) {
                                    if(!isfirst){
                                    recArray = record.split(",");

                                    if (recArray.length != 8) {
                                        lstFormato.add(record);
                                        errorFormato = true;
                                    } else {
                                        String[] s = recArray[0].split("-");
                                        if (s.length == 2) {
                                            if (!lstMatirculas.contains(recArray[0])) {
                                                if(s[0].equals(idCirculo)){
                                                    if(recArray[2].equals(idMunicipio)){
                                                        lstMatirculas.add(recArray[0]);
                                                    }else{
                                                            exception.addError("La matricula "+recArray[0]+" no pertenece al Municipo de origen");
                                                    }
                                                }else{
                                                     exception.addError("La matricula "+recArray[0]+" no pertenece al Circulo de origen");   
                                                }
                                            } else {
                                                exception.addError("La matricula " + recArray[0] + " se encuentra repetida en el archivo");
                                            }
                                        } else {
                                             matriculasInvalidas.add(recArray[0]);
                                        }
                                    }
                                 }
                                    isfirst = false;
                                }
                                session.setAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO, lstMatirculas);
                                if (matriculasInvalidas.size() > 0) {
                                    errorMatriculasInvalidas = true;
                                }else
                                {
                                session.setAttribute("ARCHIVO_ORIGEN", fi);
                                }
                                in.close();
                                
                            } else {
                                exception.addError("El archivo debe tener formato de texto plano.");
                            }
                        }
                    }
                }
                if(sw)
                {
                    exception.addError("Debe seleccionar un archivo con los folios a trasladar");
                }
            } catch (Exception e) {
                exception.addError("Ocurrió un error en el procesamiento del archivo.");
            }
            
            if (errorFormato) {
                exception.addError(strErrorFormato);

                Iterator it = lstFormato.iterator();

                while (it.hasNext()) {
                    exception.addError((String) it.next());
                }
            }

            if (errorMatriculasInvalidas) {
                exception.addError(strErrorMatriculasInvalidas);
                Iterator it = matriculasInvalidas.iterator();

                while (it.hasNext()) {
                    exception.addError((String) it.next());
                }
            }
        }
        if (exception.getErrores().size() > 0) {
           request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO,new ArrayList());
            throw exception;
        }
    }
private void salvarDatosTrasladoMasivo(HttpServletRequest request)
{
        String circulo_actual_origen = request.getParameter(CIRCULOS_SIR+"_ORIGEN");
        if(circulo_actual_origen!=null)
        {
            request.getSession().setAttribute(CIRCULOS_SIR+"_ORIGEN", circulo_actual_origen);
        }
        String municipio_actual_origen = request.getParameter(MUNICIPIO_ID+"_ORIGEN");
        if(municipio_actual_origen!=null)
        {
            request.getSession().setAttribute(MUNICIPIO_ID+"_ORIGEN", municipio_actual_origen);
        }
        String circulo_actual_destino = request.getParameter(CIRCULOS_SIR); 
        if(circulo_actual_destino!=null)
        {
            request.getSession().setAttribute(CIRCULOS_SIR, circulo_actual_destino);
        }
        String circulo_actual_nosir_destino = request.getParameter(CIRCULOS_NOSIR); 
        if(circulo_actual_nosir_destino!=null)
        {
            request.getSession().setAttribute(CIRCULOS_NOSIR, circulo_actual_nosir_destino);
        }
        String municipio_actual_nosir_destino = request.getParameter(MUNICIPIO_ID+"_NOSIR_DESTINO"); 
        if(municipio_actual_nosir_destino!=null)
        {
            request.getSession().setAttribute(MUNICIPIO_ID+"_NOSIR_DESTINO", municipio_actual_nosir_destino);
        }
        String municipio_actual_destino = request.getParameter(MUNICIPIO_ID+"_DESTINO"); 
        if(municipio_actual_destino!=null)
        {
            request.getSession().setAttribute(MUNICIPIO_ID+"_DESTINO", municipio_actual_destino);
        }
        String fechaResolucionTraslado = request.getParameter("CALENDAR");
        if(fechaResolucionTraslado!=null)
        {
            request.getSession().setAttribute("CALENDAR",fechaResolucionTraslado);
        }
        String resolucionTraslado = request.getParameter(RESOLUCION_TRASLADO);
        if(resolucionTraslado!=null)
        {
            request.getSession().setAttribute(RESOLUCION_TRASLADO,resolucionTraslado);
        }
        String tipoDestino = request.getParameter("CIRCULO_OFICINA");
        if(tipoDestino!=null)
        {
            request.getSession().setAttribute("CIRCULO_OFICINA", tipoDestino);
        }
}
/*
 * @author      :   Carlos Mario Torres Uirina
 * @change      :  Selecciona los municipios dependiendo el departamento seleccionado
 * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
 */
private EvnAdministracionForseti seleccionaMunicipioPor(HttpServletRequest request)
            throws AccionWebException, ServicioNoEncontradoException, Throwable {

        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();

        Vereda idVereda = null;
        String idMunicipio = null;
        String idDepartamento = null;
        Map<String,Municipio> municipios = null;
        String accion = request.getParameter(WebKeys.ACCION);
        String destino = (request.getParameter(WebKeys.ACCION)!=null)?request.getParameter(CIRCULO_OFICINA):"";
        String circuloCap = "";

        if(accion.contains("_ORIGEN")){
            circuloCap = request.getParameter(CIRCULOS_SIR+"_ORIGEN");
        }else
        {
            if(destino.equals("S")){
                circuloCap = request.getParameter(CIRCULOS_SIR);
            }else
            {
                circuloCap = request.getParameter(CIRCULOS_NOSIR);
            }
        }
        List zonaR = null;
        if (accion.contains("_ORIGEN") || destino.equals("S")) {
        if ((circuloCap == null) || (circuloCap.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Circulo");
        }

        service = ServiceLocator.getInstancia();
        try {
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
            if (forseti == null) {
                throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
            }            
            CirculoPk circuloPk = new CirculoPk();
            circuloPk.idCirculo = circuloCap;
            zonaR = forseti.getZonaRegistralesCirculo(circuloPk);
            municipios = new HashMap<String, Municipio>();
            List esta = new ArrayList();


            for (Iterator itZona = zonaR.iterator(); itZona.hasNext();) {
                ZonaRegistral zonaRegis = (ZonaRegistral) itZona.next();
                idVereda = zonaRegis.getVereda();
                idMunicipio = idVereda.getIdMunicipio();
                idDepartamento = idVereda.getIdDepartamento();
                Municipio depto = idVereda.getMunicipio();
                if (!esta.contains(idMunicipio)) {
                    municipios.put(idMunicipio, depto);
                    esta.add(idMunicipio);
                }
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio forseti");
        }


        List listaDept = new ArrayList();
        if (municipios != null) {
            for (Iterator itdep = municipios.keySet().iterator(); itdep.hasNext();) {
                String key = (String) itdep.next();
                Municipio municipio = (Municipio) municipios.get(key);
                listaDept.add(new ElementoLista(key, municipio.getNombre()));
            }

        }
        
        session.removeAttribute(CMunicipio.ID_MUNICIPIO);
        if (accion.contains("_ORIGEN")) {
            session.removeAttribute(AWReportes.LISTA_VEREDAS + "_ORIGEN");
            session.setAttribute(LISTA_MUNICIPIOS + "_ORIGEN", listaDept);
        } else {
            session.removeAttribute(AWReportes.LISTA_VEREDAS + "_DESTINO");
            session.setAttribute(LISTA_MUNICIPIOS + "_DESTINO", listaDept);
        }
    }
        return null;
    }
private EvnAdministracionForseti seleccionaVeredaPor(HttpServletRequest request)
            throws AccionWebException, ServicioNoEncontradoException, Throwable {

        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();

        Vereda idVereda = null;
        String idMunicipio = null;
        String idDepartamento = null;
        Map<String,Vereda> veredas = null;
        String accion = request.getParameter(WebKeys.ACCION);
        String destino = (request.getParameter(WebKeys.ACCION)!=null)?request.getParameter(CIRCULO_OFICINA):"";
        String circuloCap = "";
        String dept = "";
        String munic = "";
        if(accion.contains("_ORIGEN")){
            circuloCap = request.getParameter(CIRCULOS_SIR+"_ORIGEN");
            munic = request.getParameter(MUNICIPIO_ID+"_ORIGEN");
            
        }else
        {
            if(destino.equals("S")){
                circuloCap = request.getParameter(CIRCULOS_SIR);
            }else
            {
                circuloCap = request.getParameter(CIRCULOS_NOSIR);
            }
            munic = request.getParameter(MUNICIPIO_ID+"_DESTINO");
        }
        List zonaR = null;

        if ((circuloCap == null) || (circuloCap.trim().length() == 0)) {
            throw new AccionInvalidaException("Debe Proporcionar un Código de Circulo");
        }

        service = ServiceLocator.getInstancia();
        Departamento dep = null;
        try {
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
            if (forseti == null) {
                throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
            }            
            CirculoPk circuloPk = new CirculoPk();
            circuloPk.idCirculo = circuloCap;
            zonaR = forseti.getZonaRegistralesCirculo(circuloPk);
            veredas = new HashMap<String,Vereda>();
            List esta = new ArrayList();

            
            for(Iterator itZona = zonaR.iterator(); itZona.hasNext();){
                ZonaRegistral zonaRegis = (ZonaRegistral) itZona.next();                
                idVereda = zonaRegis.getVereda();
                idMunicipio = idVereda.getIdMunicipio();
                idDepartamento = idVereda.getIdDepartamento();
               
                    if(idMunicipio.equals(munic)){
                        dep = zonaRegis.getVereda().getMunicipio().getDepartamento();
                        if(!esta.contains(idVereda)){
                            veredas.put(idVereda.getIdVereda(), idVereda);
                            esta.add(idVereda);
                        }
                    }
                
            }
            
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio forseti");
        }

       
        List listaDept = new ArrayList();
        if(veredas!=null){
        for (Iterator itdep = veredas.keySet().iterator(); itdep.hasNext();) {
            String key = (String) itdep.next();
            Vereda vereda = (Vereda) veredas.get(key);
            listaDept.add(new ElementoLista(key, vereda.getNombre()));
        }           
        
        }
        session.removeAttribute(AWReportes.LISTA_VEREDAS);
        session.removeAttribute(CMunicipio.ID_MUNICIPIO);
        if(accion.contains("_ORIGEN")){
            session.setAttribute(LISTA_VEREDAS+"_ORIGEN", listaDept);
        }else
        {
            session.setAttribute(LISTA_VEREDAS, listaDept);
            session.setAttribute(DEPARTAMENTO_ID+"_DESTINO", dep.getIdDepartamento());
        }
        return null;
    }
/*
 * @author      : Carlos Mario Torres Urina
 * @change      : Confirma el traslado de las matriculas
 * Caso Mantis  : 11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
 */
private EvnAdministracionForseti trasladoConfirmacionMasivo(HttpServletRequest request) throws
		ValidacionTrasladoException {

	 HttpSession session = request.getSession();
         ValidacionTrasladoException e = new ValidacionTrasladoException();
         String[] idmatricula = request.getParameterValues("FOLIOS_CONFIRMACION");
         if(idmatricula==null)
         {
             e.addError("Debe seleccionar minimo un folio");
         }else if(idmatricula.length==0)
         {
             e.addError("Debe seleccionar minimo un folio");
         }
         
//         String fechIni = request.getParameter("CALENDAR_DESTINO");
//	 Date dFechIni = null;
//	 if ((fechIni == null) || (fechIni.trim().length() == 0)) {
//		e.addError("Debe seleccionar una fecha inicial.");
//	 } else {
//	 try {
//	    dFechIni = DateFormatUtil.parse(fechIni);
//	 } catch (ParseException pe) {
//	   e.addError("Fecha de inicio tiene formato inválido");
//	 }
//	 }
//
//         String resolucion = request.getParameter(RESOLUCION_DESTINO);
//	 if ((resolucion == null) || (resolucion.trim().length() == 0)) {
//             e.addError("Debe ingresar una Resolución para el traslado");
//	 } else {
//             session.setAttribute(RESOLUCION_TRASLADO, resolucion);
//	 }
         List fundamentos = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS+"_DESTINO");
                if (fundamentos == null) {
                    e.addError("Debe ingresar al menos un fundamento");
                }else{
                    session.setAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS+"_DESTINO", fundamentos);
         }
         String idDep = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
         String idMun = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
         String idVer = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);

         Vereda vereda = null;
         if (   (idDep != null && !(idDep.equals("")))
             && (idMun != null && !(idDep.equals("")))
             && (idVer != null && !(idVer.equals("")))){
              vereda = new Vereda();
              vereda.setIdDepartamento(idDep);
              vereda.setIdMunicipio(idMun);
              vereda.setIdVereda(idVer);              
         }

         if(!e.getErrores().isEmpty()){
             throw e;
         }

         List<String> folios = new ArrayList();
         for(String a : idmatricula)
         {
             folios.add(a);
         }

	 org.auriga.core.modelo.transferObjects.Usuario usuario =
                 (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
         gov.sir.core.negocio.modelo.Usuario usuarioNeg =(gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
         EvnAdministracionForseti evento = new EvnAdministracionForseti(usuario, EvnAdministracionForseti.TRASLADO_CONFIRMACION_MASIVO,usuarioNeg);
         evento.setMatriculas(folios);
//         evento.setResolucionDestino(resolucion);
//         evento.setFechaResolucionDestino(dFechIni);
         evento.setFundamentos(fundamentos);
         evento.setVereda(vereda);
         return evento;

  }
/*
 * @author      : Carlos Mario Torres Urina
 * @change      : Consultar archivos de traslado
 * Caso Mantis  : 11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
 */
 private EvnAdministracionForseti consultarArchivosMasivo(HttpServletRequest request) throws ValidacionTrasladoException{
         ValidacionTrasladoException e = new ValidacionTrasladoException();
         HttpSession session = request.getSession();
         HashMap resultado = new LinkedHashMap();
         SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm");
         String circulo = request.getParameter(CIRCULOS_SIR);
         if(circulo!=null && !circulo.equals("SIN_SELECCIONAR") && !circulo.equals("")){
            java.io.File saveTo = new java.io.File(RUTA_DESTINO_ARCHIVO);
            if(saveTo.exists())
            {
                try{
                    java.io.File[] directorios = saveTo.listFiles();
                    if(directorios!=null){
                        java.io.File dir = null;
                        for(int i = 0;i<directorios.length;i++)
                        {
                            if(directorios[i].getName().contains(circulo)){
                                dir = directorios[i];
                                break;
                            }
                        }
                        if(dir!=null)
                        {
                            java.io.File[] archivos = dir.listFiles();
                            if(archivos!=null){
                                for(int i = 0;i<archivos.length; i++)
                                {
                                   resultado.put(archivos[i].getName(),f.format(new Date(archivos[i].lastModified()))); 
                                }
                            }
                            
                        }else
                        {
                            session.setAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO,new ArrayList());
                        }
                    }
                
                }catch(Exception exp)
                {
                    e.addError(exp.getMessage());
                }
                
            }else
            {
             e.addError("La ruta de archivos no exite");
            }
         }else
         {
             e.addError("Se debe seleccionar un circulo");
         }
         if(e.getErrores().size()>0)
         {
             session.setAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO,new ArrayList());
             throw e;
         }
         session.setAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO,resultado);
         return null;
}
 /*
 * @author      : Carlos Mario Torres Urina
 * @change      : Descargar archivos trasladados
 * Caso Mantis  : 11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
 */
  private EvnAdministracionForseti descargarArchivoMasivo(HttpServletRequest request) throws ValidacionTrasladoException {
      ValidacionTrasladoException e = new ValidacionTrasladoException();
      String archivo = request.getParameter(ARCHIVO_ORIGEN);
      if(archivo == null) e.addError("Debe seleccionar un archivo");
      String circulo = (String)request.getSession().getAttribute(CIRCULOS_SIR);
      if(circulo==null || circulo.equals(WebKeys.SIN_SELECCIONAR)) e.addError("Debe seleccionar un circulo");
      java.io.File file = new java.io.File(RUTA_DESTINO_ARCHIVO+SO+circulo+SO+archivo);
      if(!file.exists()) e.addError("El archivo no existe");
      
      if(!e.getErrores().isEmpty())
      {
          throw e;
      }
      request.getSession().setAttribute(ARCHIVO_ORIGEN, file);
      
      return null;
    }
/*
 * @author      :   Julio Alcázar Rivas
 * @change      :   Se agrega el metodo eliminarMatricula del proceso VALIDAR_MATRICULA
 * Caso Mantis  :   07123
 */
private EvnAdministracionForseti eliminarMatricula(HttpServletRequest request) {

	 List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_TRASLADO);

	 if (matriculas == null) {
		matriculas = new ArrayList();
	 }

	 String[] matriculasElim = request.getParameterValues(WebKeys.
		  TITULO_CHECKBOX_ELIMINAR);
	 if (matriculasElim != null) {
		for (int i = 0; i < matriculasElim.length; i++) {
		  String actual = matriculasElim[i];
		  matriculas.remove(actual);
		}
	 }
         request.getSession().removeAttribute(CFolio.ID_MATRICULA);
        

	 return null;
  }

/*
 * @author      : Julio Alcázar Rivas
 * @change      : nuevo metodo folioConfirmacion()
 * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
 */
private EvnAdministracionForseti folioConfirmacion(HttpServletRequest request) throws
		ValidacionTrasladoException {

	 HttpSession session = request.getSession();
         ValidacionTrasladoException e = new ValidacionTrasladoException();
         String idmatricula = (String) request.getParameter(AWAdministracionForseti.FOLIO_CONFIRMACION);
	 

	

	 if ((idmatricula == null) || (idmatricula.trim().length() == 0)) {
             e.addError("Debe seleccionar una matricula para la confirmación de traslado");
	 } else {
             session.setAttribute(AWAdministracionForseti.FOLIO_CONFIRMACION, idmatricula);
             session.removeAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
	 }

         if(!e.getErrores().isEmpty()){
             throw e;
         }

         Folio folio = new Folio();
	 folio.setIdMatricula(idmatricula);

	 org.auriga.core.modelo.transferObjects.Usuario usuario =
                 (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
         EvnAdministracionForseti evento = new EvnAdministracionForseti(usuario, EvnAdministracionForseti.FOLIO_CONFIRMACION);
         evento.setFolio(folio);
         return evento;

  }

/*
 * @author      : Julio Alcázar Rivas
 * @change      : nuevo metodo trasladoConfirmacion()
 * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
 */
private EvnAdministracionForseti trasladoConfirmacion(HttpServletRequest request) throws
		ValidacionTrasladoException {

	 HttpSession session = request.getSession();
         ValidacionTrasladoException e = new ValidacionTrasladoException();
         String idmatricula = (String) request.getParameter("ID_MATRICULA");

//         String fechIni = request.getParameter("CALENDAR");
//	 Date dFechIni = null;
//	 if ((fechIni == null) || (fechIni.trim().length() == 0)) {
//		e.addError("Debe seleccionar una fecha inicial.");
//	 } else {
//	 try {
//	    dFechIni = DateFormatUtil.parse(fechIni);
//	 } catch (ParseException pe) {
//	   e.addError("Fecha de inicio tiene formato inválido");
//	 }
//	 }

//         String resolucion = request.getParameter(RESOLUCION_DESTINO);
//	 if ((resolucion == null) || (resolucion.trim().length() == 0)) {
//             e.addError("Debe ingresar una Resolución para el traslado");
//	 } else {
//             session.setAttribute(RESOLUCION_TRASLADO, resolucion);
//	 }
         
         List fundamentos = (List) request.getSession().getAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS);
            if (fundamentos == null) {
                e.addError("Debe ingresar al menos un fundamento");
            } else {
                session.setAttribute(WebKeys.LISTADO_FUNDAMENTOS_ASOCIADOS, fundamentos);
            }
            
         String idDep = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO);
         String idMun = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC);
         String idVer = request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA);

         Vereda vereda = null;
         if (   (idDep != null && !(idDep.equals("")))
             && (idMun != null && !(idDep.equals("")))
             && (idVer != null && !(idVer.equals("")))){
              vereda = new Vereda();
              vereda.setIdDepartamento(idDep);
              vereda.setIdMunicipio(idMun);
              vereda.setIdVereda(idVer);              
         }

         if(!e.getErrores().isEmpty()){
             throw e;
         }

         Folio folio = new Folio();
	 folio.setIdMatricula(idmatricula);

	 org.auriga.core.modelo.transferObjects.Usuario usuario =
                 (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
         /*
          * @author      : Carlos Torres
          * @change      : se establese la propiedad usuarioSir
          * Caso Mantis  : 11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
          */
         gov.sir.core.negocio.modelo.Usuario usuarioNeg =(gov.sir.core.negocio.modelo.Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
         EvnAdministracionForseti evento = new EvnAdministracionForseti(usuario, EvnAdministracionForseti.TRASLADO_CONFIRMACION,usuarioNeg);
         evento.setFolio(folio);
         //evento.setResolucionDestino(resolucion);
         //evento.setFechaResolucionDestino(dFechIni);
         evento.setFundamentos(fundamentos);
         evento.setVereda(vereda);
         return evento;

  }
/*
 * @author      : Julio Alcázar Rivas
 * @change      : nuevo metodo trasladoConfirmacion()
 * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
 */
private EvnAdministracionForseti cancelarsolicitud() {
    return null;
}
}

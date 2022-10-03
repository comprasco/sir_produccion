package gov.sir.core.negocio.acciones.administracion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;

import gov.sir.core.eventos.administracion.EvnAdministracionForseti;
import gov.sir.core.eventos.administracion.EvnRespAdministracionForseti;
import gov.sir.core.negocio.acciones.correccion.ANCorreccion;
import gov.sir.core.negocio.acciones.excepciones.AdicionCirculoFestivoException;
import gov.sir.core.negocio.acciones.excepciones.AdministracionOficinasOrigenException;
import gov.sir.core.negocio.acciones.excepciones.CreacionMunicipioException;
import gov.sir.core.negocio.acciones.excepciones.EstadoNoAgregadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.modelo.BloqueoFolio;
import gov.sir.core.negocio.modelo.Categoria;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoFestivo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.DepartamentoPk;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.GrupoNaturalezaJuridicaPk;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.MunicipioPk;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaPk;
import gov.sir.core.negocio.modelo.OficinaCategoria;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.OficinaOrigenPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.VeredaPk;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTipoOficina;
import gov.sir.core.negocio.modelo.constantes.CValidacion;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhancedPk;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.acciones.administracion.AWAdministracionForseti;
import gov.sir.fenrir.FenrirException;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
  /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se abilita el uso de la clase BufferedReader,InputStreamReader,PrintWriter
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
/*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Se abilita el uso de la clase SimpleDateFormat,
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */
import java.text.SimpleDateFormat;
import org.apache.commons.fileupload.FileItem;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * Acción de negocio encargada de manejar los eventos de tipo
 * <code>EvnAdministracionForseti</code> destinados a manejar
 * la administración de los objetos del servicio Forseti
 *
 * @author jmendez
 *
 */
public class ANAdministracionForseti extends SoporteAccionNegocio {

	/** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Forseti  */
	private ForsetiServiceInterface forseti;
	
	/** Instancia de la intefaz de Forseti  */
	private FenrirServiceInterface fenrir;
        /*
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Contantes para el manejo del archivo csv de traslado de folio
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
         */  
        public String RUTA_DESTINO_ARCHIVO = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO);
        public String SO = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.SO); 

	/**
	 *Constructor de la clase <code>ANAdministracionForseti</code>.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANAdministracionForseti() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}
		
		try {
			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

			if (fenrir == null) {
				throw new ServicioNoEncontradoException("Servicio fenrir no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio fenrir");
		}

		if (fenrir == null) {
			throw new ServicioNoEncontradoException("Servicio fenrir no encontrado");
		}

	}

	/**
	 * Manejador de eventos de tipo <code>EvnAdministracionForseti</code>.
	 * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
	 * al tipo de evento que llegue a la acción de negocio.  Este método redirige
	 * la acción a otros métodos en la clase de acuerdo al tipo de evento
	 * que llega como parámetro.
	 *
	 * @param evento <code>EvnAdministracionForseti</code> evento con los parámetros
	 * de la acción a realizar utilizando los servicios disponibles en la clase.
	 *
	 * @return <code>EventoRespuesta</code> con la información resultante de la
	 * ejecución de la acción sobre los servicios
	 *
	 * @throws <code>EventoException</code>
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnAdministracionForseti evento = (EvnAdministracionForseti) e;

		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}

		String tipoEvento = evento.getTipoEvento();

		if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_DEPARTAMENTO)) {
			return adicionaDepartamento(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_DEPARTAMENTO)) {
			return eliminaDepartamento(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_MUNICIPIO)) {
			return adicionaMunicipio(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_MUNICIPIO)) {
			return eliminaMunicipio(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_VEREDA)) {
			return adicionaVereda(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_VEREDA)) {
			return eliminaVereda(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_ESTADO_FOLIO)) {
			return adicionaEstadoFolio(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_ESTADO_FOLIO)) {
			return eliminaEstadoFolio(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_TIPO_OFICINA)) {
			return adicionaTipoOficina(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_TIPO_OFICINA)) {
			return eliminaTipoOficina(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_TIPO_DOCUMENTO)) {
			return adicionaTipoDocumento(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_TIPO_DOCUMENTO)) {
			return eliminaTipoDocumento(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_TIPO_PREDIO)) {
			return adicionaTipoPredio(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_TIPO_PREDIO)) {
			return eliminaTipoPredio(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_GRUPO_NAT_JURIDICA)) {
			return adicionaGrupoNatJuridica(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_GRUPO_NAT_JURIDICA)) {
			return eliminaGrupoNatJuridica(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_TIPO_NAT_JURIDICA)) {
			return adicionaTipoNatJuridica(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_TIPO_NAT_JURIDICA)) {
			return eliminaTipoNatJuridica(evento);
		}

		//Edita atributos de un tipo de Naturaleza Jurídica
		else if (tipoEvento.equals(AWAdministracionForseti.EDITA_DETALLES_NATURALEZA_JURIDICA)) {
			return editarDetallesNatJuridica(evento);

		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_CIRCULO_REGISTRAL)) {
			return adicionaCirculoRegistral(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_CIRCULO_REGISTRAL)) {
			return eliminaCirculoRegistral(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.EDITA_NATURALEZA_JURIDICA)) {
			return actualizaNaturalezaJuridica(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.EDITA_CIRCULO_REGISTRAL)) {
			return actualizaCirculo(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.EDITA_NIT_CIRCULO_REGISTRAL)) {
			return actualizaNITCirculo(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_ESTADO_ANOTACION)) {
			return adicionaEstadoAnotacion(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_ESTADO_ANOTACION)) {
			return eliminaEstadoAnotacion(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_DOMINIO_NAT_JURIDICA)) {
			return adicionaDominioNaturalezaJuridica(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_DOMINIO_NAT_JURIDICA)) {
			return eliminaDominioNaturalezaJuridica(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_CIRCULO_FESTIVO)) {
			return adicionaCirculoFestivo(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_CIRCULO_FESTIVO)) {
			return eliminaCirculoFestivo(evento);
		}  else if (tipoEvento.equals(EvnAdministracionForseti.SELECCIONA_NATURALEZA_JURIDICA)) {
			return seleccionaNaturalezaJuridica(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.SELECCIONA_CIRCULO_FESTIVO)) {
			return seleccionaCirculoFestivo(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_EJE)) {
			return adicionaEje(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_EJE)) {
			return eliminaEje(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_ZONA_REGISTRAL)) {
			return adicionaZonaRegistral(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINA_ZONA_REGISTRAL)) {
			return eliminaZonaRegistral(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.SELECCIONA_ZONA_REGISTRAL)) {
			return seleccionaZonaRegistral(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.TRASLADAR)) {
			return trasladar(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_OFICINA_ORIGEN)) {
			return adicionaOficinaOrigen(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.ADICIONA_OFICINA_ORIGEN_NOTARIA)) {
			return adicionaOficinaOrigenNotaria(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.CONSULTA_OFICINA_ORIGEN_POR_VEREDA)) {
			return consultaOficinasOrigenPorVereda(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.CONSULTA_OFICINA_POR_VEREDA)) {
			return consultaOficinasOrigenPorVereda(evento);
		}else if (tipoEvento.equals(EvnAdministracionForseti.CONSULTA_NOTARIAS_POR_MUNICIPIO)) {
			return consultaNotariasPorMunicipio(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.CATASTRO)) {
            return catastro(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.CARGAR_PLANTILLAS_CERTIFICADOS)) {
			return cargarTiposPlantilla(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.EDITAR_PLANTILLA)) {
			return editarPlantilla(evento);
        } else if (tipoEvento.equals(EvnAdministracionForseti.REABRIR_FOLIO)) {
			return reabrirFolio(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.CONSULTAR_OFICINA_EDITAR)) {
			return consultarOficinaEditar(evento);
		} else if (tipoEvento.equals(EvnAdministracionForseti.EDITAR_OFICINA_ORIGEN)) {
			return editarOficinaOrigen(evento);
		}else if (tipoEvento.equals(EvnAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO)) {
			return listarDepartamentosCirculo(evento);
		}else if (tipoEvento.equals(EvnAdministracionForseti.CONSULTA_DEPARTAMENTO)){
			return listaDepartamentos(evento);
		}else if (tipoEvento.equals(EvnAdministracionForseti.ELIMINAR_OFICINA_ORIGEN)) {
			return eliminarOficinaOrigen(evento);
		}else if (tipoEvento.equals(EvnAdministracionForseti.INHABILITAR_CIRCULO)) {
			return inhabilitarCirculo(evento);
		}else if (tipoEvento.equals(EvnAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_INHAB)) {
			return seleccionaZonaRegistralInhab(evento);
		}else if (tipoEvento.equals(EvnAdministracionForseti.CARGAR_CIRCULOS_INHABILITADOS)) {
			return cargarCirculosInhabilitados(evento);
		}else if (tipoEvento.equals(EvnAdministracionForseti.TRASLADAR_FOLIOS_MASIVO)) {
			return trasladoMasivoFolios(evento);
		}
                /*
                 * @author      :   Carlos Mario Torres Urina
                 * @change      :   Manejo de acciones para traslado de folio masivo
                 * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
                 */
                else if (tipoEvento.equals(EvnAdministracionForseti.VALIDAR_MATRICULA_MASIVO)) {
			return validarMatriculasMasivo(evento);
		}else if (tipoEvento.equals(EvnAdministracionForseti.TRASLADAR_MASIVO)) {
			return trasladarMasivo(evento);
		}else if (tipoEvento.equals(EvnAdministracionForseti.TRASLADO_CONFIRMACION_MASIVO)) {
			return trasladoConfirmacionMasivo(evento);
		}
                
                /*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   validar matricula en el proceso de traslado de folio
                 * Caso Mantis  :   07123
                 */
                else if (tipoEvento.equals(EvnAdministracionForseti.VALIDAR_MATRICULA)) {
			return validarMatricula(evento);
		}
                /*
                 * @author      : Julio Alcázar Rivas
                 * @change      : nuevos procesos traslado
                 * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                 */
                else if (tipoEvento.equals(EvnAdministracionForseti.FOLIO_CONFIRMACION)) {
			return folioConfirmacion(evento);
		}else if (tipoEvento.equals(EvnAdministracionForseti.TRASLADO_CONFIRMACION)) {
			return trasladoConfirmacion(evento);
		}

		return null;
	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta trasladoMasivoFolios(EvnAdministracionForseti evento) throws EventoException{
		
		try {
			forseti.trasladarFolios(evento.getCirculoOrigen(), evento.getCirculoDestino(), evento.getTrasladoMasivoInicio(), evento.getTrasladoMasivoFin());
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta cargarCirculosInhabilitados(EvnAdministracionForseti evento) throws EventoException{

		List datos = null;
		Circulo circulo=evento.getCirculoDestino();
		try {
			datos = forseti.getCirculosInhabilitados(circulo);
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.CARGAR_CIRCULOS_INHABILITADOS_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de traer la información de una <code>ZonaRegistral</code> específica.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>ZonaRegistral</code> a ser consultado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la <code>ZonaRegistral</code> consultada.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti seleccionaZonaRegistralInhab(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		List datosInhab = null;
		try {
			Circulo circulo = evento.getCirculo();
			if (circulo!=null){
				CirculoPk cid = new CirculoPk();
				cid.idCirculo = circulo.getIdCirculo();
				datos = forseti.getZonaRegistralesCirculo(cid);	
			}else{
				datos=new ArrayList();
			}
			Circulo circuloInhab = evento.getCirculoInhabilitado();
			CirculoPk cidInhab=new CirculoPk();
			cidInhab.idCirculo = circuloInhab.getIdCirculo();
			datosInhab=forseti.getZonaRegistralesCirculo(cidInhab);
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_INHAB_OK);
		evRespuesta.setListaZonasInhabilitar(datosInhab);
		return evRespuesta;
	}

	private EventoRespuesta inhabilitarCirculo(EvnAdministracionForseti evento) throws EventoException{
		Circulo circuloOrigen=evento.getCirculoOrigen();
		Circulo circuloDestino=evento.getCirculoDestino();
		List circulos=null;
		try {
			forseti.inhabilitarCirculo(circuloOrigen,circuloDestino, evento.getZonasRegistrales(),evento.getUsuariosCrear(),evento.getUsuariosTrasladar(), evento.getUsuariosAsignarRolConsulta(), fenrir.getUsuario(evento.getUsuario()));
			circulos=forseti.getCirculos();
		} catch (FenrirException e) {
			throw new EventoException(e.getMessage(),e);
		} catch (Throwable e) {
			throw new EventoException(e.getMessage(),e);
		}
		return new EvnRespAdministracionForseti(circulos,EvnRespAdministracionForseti.INHABILITAR_CIRCULO_OK);
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta adicionaOficinaOrigenNotaria(EvnAdministracionForseti evento) throws EventoException{

		List datos = new ArrayList();
		
		try {
			Vereda vereda = evento.getVereda();

			if (vereda == null) {
				throw new EventoException("Debe proporcionar una vereda", null);
			}

			VeredaPk vid = new VeredaPk();
			vid.idDepartamento = vereda.getIdDepartamento();
			vid.idMunicipio = vereda.getIdMunicipio();
			vid.idVereda = vereda.getIdVereda();

			Iterator itCate = evento.getCategorias().iterator();
			OficinaOrigen oficina = evento.getOficinaOrigen();
			while(itCate.hasNext()){
				Categoria cate = (Categoria)itCate.next();
				OficinaCategoria ofCate = new OficinaCategoria();
				ofCate.setCategoria(cate);
				oficina.addCategoria(ofCate);
			}

			OficinaOrigenPk idOfic=forseti.addOficinaOrigen(oficina);

			Iterator itDatos = forseti.getOficinasOrigenByVereda(vid).iterator();
			while(itDatos.hasNext()){
				OficinaOrigen nOff = (OficinaOrigen)itDatos.next();
				OficinaOrigenPk id = new OficinaOrigenPk();
				id.idOficinaOrigen = nOff.getIdOficinaOrigen();
                                /*
                                 *  @author Carlos Torres
                                 *  @chage   se agrega validacion de version diferente
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                 */
                                id.version = nOff.getVersion();
				datos.add(forseti.getOficinaOrigen(id));
			}
			oficina.setIdOficinaOrigen(idOfic.idOficinaOrigen);
                        /*
                                 *  @author Carlos Torres
                                 *  @chage   se agrega validacion de version diferente
                                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                 */
                        oficina.setVersion(idOfic.version);
			hermod.agregarNotariaReparto(oficina);

		} catch (ForsetiException e) {
			ValidacionParametrosException exception = null;
			if (e.getErrores()!=null && !e.getErrores().isEmpty()){
				exception = new ValidacionParametrosException(e.getErrores());
				throw exception;
			}
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new AdministracionOficinasOrigenException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new AdministracionOficinasOrigenException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_OFICINA_ORIGEN_OK);
		return evRespuesta;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta eliminarOficinaOrigen(EvnAdministracionForseti evento) throws EventoException{

		OficinaOrigenPk id=new OficinaOrigenPk();
		id.idOficinaOrigen=evento.getIdOficinaOrigen();
                 /*
                *  @author Carlos Torres
                *  @chage   se agrega validacion de version diferente
                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                */
                id.version = evento.getVersion();

		try {
			if (forseti.eliminarOficinaOrigen(id)){
				return consultaOficinasOrigenPorVereda(evento);
			}else{
				throw new AdministracionOficinasOrigenException("No se pudo eliminar la Oficina Origen" );
			}
		} catch (AdministracionOficinasOrigenException e) {
			throw e;
		}catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,"No se pudo la oficina",e);
			throw new AdministracionOficinasOrigenException(e.getMessage(), e);
		}
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta listaDepartamentos(EvnAdministracionForseti evento) throws EventoException{

		Departamento depto=evento.getDepartamento();
		DepartamentoPk oid=new DepartamentoPk();
		oid.idDepartamento=depto.getIdDepartamento();
		try {
			Departamento deptoComp=forseti.getDepartamento(oid);
			EvnRespAdministracionForseti respuesta=new EvnRespAdministracionForseti(deptoComp,EvnRespAdministracionForseti.CONSULTA_DEPARTAMENTO);
			return respuesta;

		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,
					"No se pudo obtener el Departamento (El código proporcionado ya existe en el sistema)",
					e);
				throw new EventoException(e.getMessage(), e);
		}
	}

	private EventoRespuesta listarDepartamentosCirculo(EvnAdministracionForseti evento) throws EventoException{
		Circulo circulo=evento.getCirculo();
		CirculoPk oid=null;
		if (circulo!=null){
			oid=new CirculoPk();
			oid.idCirculo=circulo.getIdCirculo();
		}

		try {
			List deptos=forseti.getDepartamentos(oid);
			EvnRespAdministracionForseti respuesta=new EvnRespAdministracionForseti(deptos,EvnRespAdministracionForseti.LISTA_DEPARTAMENTOS_CIRCULO);
			return respuesta;

		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,
					"No se pudo crear el Departamento (El código proporcionado ya existe en el sistema)",
					e);
				throw new EventoException(e.getMessage(), e);
		}
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta editarOficinaOrigen(EvnAdministracionForseti evento) throws EventoException {

		try {
			Iterator itCate = evento.getCategorias().iterator();
			OficinaOrigen oficina = evento.getOficinaOrigen();
			while(itCate.hasNext()){
				Categoria cate = (Categoria)itCate.next();
				OficinaCategoria ofCate = new OficinaCategoria();
				ofCate.setCategoria(cate);
				oficina.addCategoria(ofCate);
			}
                        /*
                        *  @author Carlos Torres
                        *  @chage   se agrega validacion de version diferente
                        *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                        */
			forseti.addOficinaOrigen(oficina);
		} catch (ForsetiException e) {
			ValidacionParametrosException exception = null;
			if (e.getErrores()!=null && !e.getErrores().isEmpty()){
				exception = new ValidacionParametrosException(e.getErrores());
				throw exception;
			}
			Log.getInstance().error(ANAdministracionForseti.class,"No se pudo crear el Departamento (El código proporcionado ya existe en el sistema)",e);
			exception = new ValidacionParametrosException(e.getMessage(),e);
			exception.addError(e.getMessage());
			throw exception;
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,
				"No se pudo crear el Departamento (El código proporcionado ya existe en el sistema)",
				e);
			ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(),e);
			exception.addError(e.getMessage());
			throw exception;
		}
		return this.consultaOficinasOrigenPorVereda(evento);
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta consultarOficinaEditar(EvnAdministracionForseti evento) throws EventoException {
		OficinaOrigenPk id = new OficinaOrigenPk();
		id.idOficinaOrigen = evento.getIdOficinaOrigen();
                 /*
                        *  @author Carlos Torres
                        *  @chage   se agrega validacion de version diferente
                        *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                        */
                id.version = evento.getVersion();
		OficinaOrigen oficina;
		try {
			oficina = forseti.getOficinaOrigen(id);
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,
				"No se pudo crear el Departamento (El código proporcionado ya existe en el sistema)",
				e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,
				"No se pudo crear el Departamento (El código proporcionado ya existe en el sistema)",
				e);
			throw new EventoException(e.getMessage(), e);
		}
		EvnRespAdministracionForseti respuesta = new EvnRespAdministracionForseti(oficina,EvnRespAdministracionForseti.OFICINA_ORIGEN_EDICION);
		return respuesta;
	}

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>Departamento</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>Departamento</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con el nuevo
	 * <code>Departamento</code> creado en la base de datos.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaDepartamento(EvnAdministracionForseti evento)
		throws EventoException {

		Departamento depto = null;
		List deptos=null;
		try {
			DepartamentoPk oid = forseti.addDepartamento(evento.getDepartamento(), fenrir.getUsuario(evento.getUsuario()));
			depto = forseti.getDepartamento(oid);
			if (depto == null) {
				throw new EventoException(
					"No se pudo crear el Departamento (El código proporcionado ya existe en el sistema)",
					null);
			}

			Circulo circulo=evento.getCirculo();
			CirculoPk oidC=null;
			if (circulo!=null){
				oidC=new CirculoPk();
				oidC.idCirculo=circulo.getIdCirculo();
			}
			deptos=forseti.getDepartamentos(oidC);

		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,
				"No se pudo crear el Departamento (El código proporcionado ya existe en el sistema)",
				e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,
				"No se pudo crear el Departamento (El código proporcionado ya existe en el sistema)",
				e);
			throw new EventoException(e.getMessage(), e);
		}
		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				depto,
				EvnRespAdministracionForseti.ADICIONA_DEPARTAMENTO_OK);
		evRespuesta.setDepartamentos(deptos);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un departamento en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del departamento a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de todos los
	 * departamentos disponibles en el sistema.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaDepartamento(EvnAdministracionForseti evento)
		throws EventoException {

		List deptos=null;
		try {
			if (forseti.eliminarDepartamento(evento.getDepartamento(), fenrir.getUsuario(evento.getUsuario()))) {
				Circulo circulo=evento.getCirculo();
				CirculoPk oidC=null;
				if (circulo!=null){
					oidC=new CirculoPk();
					oidC.idCirculo=circulo.getIdCirculo();
				}
				deptos=forseti.getDepartamentos(oidC);
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Departamento",
					null);
			}



		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Departamento", null);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,
				"Debido a restricciones de integridad de datos no se pudo eliminar el Departamento",
				e);
			throw new EventoException(e.getMessage(), null);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				deptos,
				EvnRespAdministracionForseti.ELIMINA_DEPARTAMENTO_OK);
		return evRespuesta;
	}
	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 *Este método se encarga de adicionar un nuevo Municipio en la base de datos.
		 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo Municipio a creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la información actualizada
	 * del departamento asociado al municipio.
	 * departamento creado en la base de datos.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaMunicipio(EvnAdministracionForseti evento)
		throws EventoException {

		Departamento departamento = null;

		try {
			Departamento depto = evento.getDepartamento();
			DepartamentoPk did = new DepartamentoPk();
			did.idDepartamento = depto.getIdDepartamento();

			MunicipioPk oid = forseti.addMunicipioToDepartamento(evento.getMunicipio(), did, fenrir.getUsuario(evento.getUsuario()));
			departamento = forseti.getDepartamento(did);

			if (departamento == null) {
				throw new CreacionMunicipioException(
					"No se pudo crear el Municipio (El código proporcionado ya existe en el sistema)",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				departamento,
				EvnRespAdministracionForseti.ADICIONA_MUNICIPIO_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un Municipio en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del Municipio a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la información actualizada
	 * del departamento relacionado al municipio eliminado
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaMunicipio(EvnAdministracionForseti evento)
		throws EventoException {

		Departamento departamento = null;

		try {
			DepartamentoPk did = new DepartamentoPk();
			did.idDepartamento = evento.getMunicipio().getIdDepartamento();
			if (forseti.eliminarMunicipio(evento.getMunicipio())) {
				departamento = forseti.getDepartamento(did);
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Municipio",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Municipio", null);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Municipio", null);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				departamento,
				EvnRespAdministracionForseti.ELIMINA_MUNICIPIO_OK);
		return evRespuesta;
	}
	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar una <code>Vereda</code>  en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * de la nueva  <code>Vereda</code> a ser creada.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con el
	 * <code>Departamento</code> relacionado con la vereda creada.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaVereda(EvnAdministracionForseti evento)
		throws EventoException {

		Departamento departamento = null;

		try {
			Municipio municipio = evento.getMunicipio();
			MunicipioPk mid = new MunicipioPk();
			mid.idDepartamento = municipio.getIdDepartamento();
			mid.idMunicipio = municipio.getIdMunicipio();

			VeredaPk vid = forseti.addVeredaToMunicipio(evento.getVereda(), mid);

			DepartamentoPk did = new DepartamentoPk();
			did.idDepartamento = municipio.getIdDepartamento();
			departamento = forseti.getDepartamento(did);

		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				departamento,
				EvnRespAdministracionForseti.ADICIONA_VEREDA_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar una <code>Vereda</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * de la  code>Vereda</code> a ser eliminada.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de todos los
	 * departamentos disponibles en el sistema.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaVereda(EvnAdministracionForseti evento)
		throws EventoException {

		Departamento departamento = null;

		try {
			MunicipioPk mid = new MunicipioPk();
			mid.idDepartamento = evento.getDepartamento().getIdDepartamento();
			mid.idMunicipio = evento.getMunicipio().getIdMunicipio();

			if (forseti.eliminarVereda(evento.getVereda())) {
				DepartamentoPk did = new DepartamentoPk();
				did.idDepartamento = evento.getDepartamento().getIdDepartamento();
				departamento = forseti.getDepartamento(did);
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar la Vereda",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,"Debido a restricciones de integridad de datos no se pudo eliminar la Vereda", e);
			throw new EventoException(e.getMessage(), null);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar la Vereda", null);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				departamento,
				EvnRespAdministracionForseti.ELIMINA_VEREDA_OK);
		return evRespuesta;
	}

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	* Este método se encarga de adicionar un nuevo <code>EstadoFolio</code> en la base de datos.
	*
	* @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	* del nuevo <code>EstadoFolio</code> a ser creado.
	*
	* @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de Estados de folio
	* para que sea desplegada por las capas superiores.
	*
	* @throws <code>EventoException</code>
	*/
	private EvnRespAdministracionForseti adicionaEstadoFolio(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.addEstadoFolio(evento.getEstadoFolio(), fenrir.getUsuario(evento.getUsuario())) != null) {
				datos = forseti.getEstadosFolio();
			} else {
				throw new EstadoNoAgregadoException("No se pudo crear el Tipo de Estado de Folio", null);
			}
		}catch (EstadoNoAgregadoException e) {
		   throw e;
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EstadoNoAgregadoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EstadoNoAgregadoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_ESTADO_FOLIO_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un <code>EstadoFolio</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>EstadoFolio</code> a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Estados de folio
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaEstadoFolio(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;

		try {
			if (forseti.eliminarEstadoFolio(evento.getEstadoFolio(), fenrir.getUsuario(evento.getUsuario()))) {
				datos = forseti.getEstadosFolio();
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Estado de Folio",
					null);
			}

		} catch (ForsetiException e) {
			throw new EventoException(
								"Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Estado de Folio",
								e);

		} catch (Throwable e) {
			throw new EventoException(
								"Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Estado de Folio",
								e);

		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ELIMINA_ESTADO_FOLIO_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>TipoOficina</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>TipoOficina</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista de Estados de tipos de oficina actualizada
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaTipoOficina(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.addTipoOficina(evento.getTipoOficina(), fenrir.getUsuario(evento.getUsuario())) != null) {
				datos = forseti.getTiposOficina();
			} else {
				throw new EventoException("No se pudo crear el Tipo de Oficina", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_TIPO_OFICINA_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un <code>TipoOficina</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>TipoOficina</code> a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista de Tipos de oficina actualizada
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaTipoOficina(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.eliminarTipoOficina(evento.getTipoOficina(),fenrir.getUsuario(evento.getUsuario()))) {
				datos = forseti.getTiposOficina();
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Oficina",
					null);
			}
		} catch (ForsetiException e) {

			throw new EventoException(
								"Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Oficina",
								e);
		} catch (Throwable e) {
			throw new EventoException(
								"Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Oficina",
								e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ELIMINA_TIPO_OFICINA_OK);
		return evRespuesta;
	}

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>TipoDocumento</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>TipoDocumento</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de tipos documento
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaTipoDocumento(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.addTipoDocumento(evento.getTipoDocumento(), fenrir.getUsuario(evento.getUsuario())) != null) {
				datos = forseti.getTiposDocumento();
			} else {
				throw new EventoException("No se pudo crear el Tipo de Documento", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_TIPO_DOCUMENTO_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un <code>TipoDocumento</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>TipoDocumento</code> a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de Tipos de documento
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaTipoDocumento(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.eliminarTipoDocumento(evento.getTipoDocumento(),fenrir.getUsuario(evento.getUsuario()))) {
				datos = forseti.getTiposDocumento();
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Documento",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ELIMINA_TIPO_DOCUMENTO_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>TipoPredio</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>TipoPredio</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de Tipos de predio
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaTipoPredio(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.addTipoPredio(evento.getTipoPredio()) != null) {
				datos = forseti.getTiposPredio();
			} else {
				throw new EventoException("No se pudo crear el Tipo de Predio", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_TIPO_PREDIO_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un <code>TipoPredio</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>TipoPredio</code> a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Tipos de predio
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaTipoPredio(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.eliminarTipoPredio(evento.getTipoPredio())) {
				datos = forseti.getTiposPredio();
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Predio",
					null);
			}
		} catch (ForsetiException e) {

			throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Predio",e);
		} catch (Throwable e) {

			throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Predio",e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(datos, EvnRespAdministracionForseti.ELIMINA_TIPO_PREDIO_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>GrupoNaturalezaJuridica</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>GrupoNaturalezaJuridica</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de Grupos de Naturaleza Jurídica
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaGrupoNatJuridica(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.addGrupoNaturalezaJuridica(evento.getGrupoNatJuridica()) != null) {
				datos = forseti.getGruposNaturalezaJuridica();
			} else {
				throw new EventoException("No se pudo crear el Grupo de Naturaleza Jurídica", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_GRUPO_NAT_JURIDICA_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un <code>GrupoNaturalezaJuridica</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>GrupoNaturalezaJuridica</code> a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Estados de folio
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaGrupoNatJuridica(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.eliminarGrupoNaturalezaJuridica(evento.getGrupoNatJuridica())) {
				datos = forseti.getGruposNaturalezaJuridica();
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Grupo de Naturaleza Jurídica",
					null);
			}
		} catch (ForsetiException e) {
			throw new EventoException(
								"Debido a restricciones de integridad de datos no se pudo eliminar el Grupo de Naturaleza Jurídica",
								e);

		} catch (Throwable e) {

			throw new EventoException(
								"Debido a restricciones de integridad de datos no se pudo eliminar el Grupo de Naturaleza Jurídica",
								e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.EDITA_DETALLES_NATURALEZA_JURIDICA_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>TipoNaturalezaJuridica</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>TipoNaturalezaJuridica</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de Tipos de Naturaleza jurídica
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaTipoNatJuridica(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;

		try {
			NaturalezaJuridica naturaleza = evento.getNaturalezaJuridica();
                        /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : Se establese el valor de la propiedad version en 1
	 */
                        naturaleza.setVersion(1);
			GrupoNaturalezaJuridica grupo = evento.getGrupoNatJuridica();
			GrupoNaturalezaJuridicaPk gid = new GrupoNaturalezaJuridicaPk();
			gid.idGrupoNatJuridica = grupo.getIdGrupoNatJuridica();

			if (forseti.addNaturalezaJuridicaToGrupo(naturaleza, gid, fenrir.getUsuario(evento.getUsuario())) != null) {
				datos = forseti.getGruposNaturalezaJuridica();
			} else {
				throw new EventoException("No se pudo crear el Tipo de Naturaleza Jurídica", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_TIPO_NAT_JURIDICA_OK);
		return evRespuesta;
	}




	/**
	* Este método se encarga de adicionar un nuevo <code>TipoNaturalezaJuridica</code> en la base de datos.
	* @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	* del nuevo <code>TipoNaturalezaJuridica</code> a ser creado.
	* @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de Tipos de Naturaleza jurídica
	* para que sea desplegada por las capas superiores.
	* @throws <code>EventoException</code>
	*/
	private EvnRespAdministracionForseti editarDetallesNatJuridica(EvnAdministracionForseti evento)
			throws EventoException {

	List datos = null;
        List datosFiltrados = null;

	try
	{
		NaturalezaJuridica naturaleza = evento.getNaturalezaJuridica();
		GrupoNaturalezaJuridica grupo = evento.getGrupoNatJuridica();
		GrupoNaturalezaJuridicaPk gid = new GrupoNaturalezaJuridicaPk();
		gid.idGrupoNatJuridica = grupo.getIdGrupoNatJuridica();

		
		/*  @author Carlos Torres
        *  @chage   se establece el valor de la propiedad version
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
                naturaleza.setVersion(naturaleza.getVersion() + 1 );
				
                /**
                * @Autor: Edgar Lora
                * @Mantis: 13176
                */
                java.util.Map infoUsuario = new java.util.HashMap();
                if(evento.getInfoUsuario() !=null){
                    infoUsuario.put("user",evento.getInfoUsuario().getUser());
                    infoUsuario.put("host",evento.getInfoUsuario().getHost());
                    infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                    infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                    infoUsuario.put("idTurno",naturaleza.getIdNaturalezaJuridica());
                }
                co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                try {
                    auditoria.guardarDatosTerminal(infoUsuario);
                } catch (GeneralSIRException ex) {
                    Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                }                
                NaturalezaJuridicaEnhancedPk njId = new NaturalezaJuridicaEnhancedPk();
                njId.idNaturalezaJuridica = naturaleza.getIdNaturalezaJuridica();

                njId.version = naturaleza.getVersion() - 1;  

                NaturalezaJuridicaEnhanced naturalezaSinDatosActualizados = forseti.getNaturalezaJuridicaById(njId);
                NaturalezaJuridicaEnhanced naturalezaDatosActualizados = NaturalezaJuridicaEnhanced.enhance(naturaleza);
                
		try {
                    /*
                    *  @author Carlos Torres
                    *  @chage   se hace una llamada al nuevo metodo que inclulle  el id del grupo entre los paramatros
                    *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
                    */
                    boolean resultUpdate = (forseti.addNaturalezaJuridicaToGrupo(naturaleza,gid, fenrir.getUsuario(evento.getUsuario()))!=null);
                    /*
                    *  @author Carlos Torres
                    *  @chage   se cambia el metodo que consulta la lista de grupos de naturaleza juridica
                    *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
                    */
                    datos = forseti.getGruposNaturalezaJuridicaAll();
                    datosFiltrados = forseti.getGruposNaturalezaJuridica();
		}catch (ForsetiException t){
			throw new EventoException("No se pudo editar el Tipo de Naturaleza Jurídica", t);
		}
                
                /**
                * @Autor: Edgar Lora
                * @Mantis: 13176
                */
                auditoria.guardarAuditoriaNaturalezaJuridica(naturalezaDatosActualizados, naturalezaSinDatosActualizados, naturaleza.getIdNaturalezaJuridica());
	}
	catch (ForsetiException e) {
		Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
		throw new EventoException(e.getMessage(), e);
	}

	catch (Throwable e) {
		Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
		throw new EventoException(e.getMessage(), e);
	}

	EvnRespAdministracionForseti evRespuesta = 	new EvnRespAdministracionForseti(datos,EvnRespAdministracionForseti.ADICIONA_TIPO_NAT_JURIDICA_OK);
        /*
        *  @author Carlos Torres
        *  @chage   se cambia el metodo que consulta la lista de grupos de naturaleza juridica
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
        evRespuesta.setListaNatJuridica(datosFiltrados);
		return evRespuesta;
	}




	/**
	 * Este método se encarga de eliminar un <code>TipoNaturalezaJuridica</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>TipoNaturalezaJuridica</code> a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Tipos de Naturaleza Jurídica
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaTipoNatJuridica(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			GrupoNaturalezaJuridica grupo = evento.getGrupoNatJuridica();
			GrupoNaturalezaJuridicaPk gid = new GrupoNaturalezaJuridicaPk();
			gid.idGrupoNatJuridica = grupo.getIdGrupoNatJuridica();

			if (forseti.eliminarNaturalezaJuridica(evento.getNaturalezaJuridica(), fenrir.getUsuario(evento.getUsuario()))) {
				datos = forseti.getGruposNaturalezaJuridica();
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Naturaleza Jurídica",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ELIMINA_TIPO_NAT_JURIDICA_OK);
		return evRespuesta;
	}

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>Circulo</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>Circulo</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de Círculos
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaCirculoRegistral(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;

		try {
			if (forseti.addCirculo(evento.getCirculo(),fenrir.getUsuario(evento.getUsuario())) != null) {
				datos = forseti.getCirculos();
			} else {
				throw new EventoException("No se pudo crear el Circulo Registral", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_CIRCULO_REGISTRAL_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un <code>Circulo</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>Circulo</code> a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Círculos
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaCirculoRegistral(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.eliminarCirculo(evento.getCirculo(),fenrir.getUsuario(evento.getUsuario()))) {
				datos = forseti.getCirculos();
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Circulo Registral ",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ELIMINA_CIRCULO_REGISTRAL_OK);
		return evRespuesta;
	}



	/**
	 * Este método se encarga de actualizar un <code>Circulo</code> de la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>Circulo</code> a ser modificado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Círculos
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti actualizaCirculo(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			Circulo circulo = evento.getCirculo();
			CirculoPk cid = new CirculoPk();
			cid.idCirculo = circulo.getIdCirculo();

			if (forseti.updateCirculo(cid, circulo,fenrir.getUsuario(evento.getUsuario()))) {
				datos = forseti.getCirculos();
			} else {
				throw new EventoException(
					"No se pudo actualizar el círculo ",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.EDITA_CIRCULO_OK);
		return evRespuesta;
	}


	/**
	 * Este método se encarga de actualizar una <code>NaturalezaJuridica</code> de la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>NaturalezaJuridica</code> a ser modificado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Grupos
	 * de naturaleza Juridica para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti actualizaNaturalezaJuridica(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			NaturalezaJuridica naturaleza = evento.getNaturalezaJuridica();
                        /*
        *  @author Carlos Torres
        *  @chage   se agregan instrucciones para tner en cuenta la version
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
                        naturaleza.setVersion(naturaleza.getVersion()+1);
                        GrupoNaturalezaJuridicaPk gid = new GrupoNaturalezaJuridicaPk();
                        gid.idGrupoNatJuridica = naturaleza.getGrupoNaturalezaJuridica().getIdGrupoNatJuridica();
			if (forseti.addNaturalezaJuridicaToGrupo(naturaleza,gid, fenrir.getUsuario(evento.getUsuario()))!=null) {
				datos = forseti.getGruposNaturalezaJuridica();
			} else {
				throw new EventoException(
					"No se pudo actualizarla naturaleza jurídica ",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.EDITA_NATURALEZA_JURIDICA_OK);
		return evRespuesta;
	}



	/**
	 * Este método se encarga de actualizar un <code>Circulo</code> de la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>Circulo</code> a ser modificado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Círculos
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti actualizaNITCirculo(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			Circulo circulo = evento.getCirculo();
			CirculoPk cid = new CirculoPk();
			cid.idCirculo = circulo.getIdCirculo();

			if (forseti.updateCirculo(cid, circulo,fenrir.getUsuario(evento.getUsuario()))) {
				datos = forseti.getCirculos();
			} else {
				throw new EventoException(
					"No se pudo actualizar el círculo ",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.EDITA_CIRCULO_NIT_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>EstadoAnotacion</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>EstadoAnotacion</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Estados de Anotación
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaEstadoAnotacion(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;

		try {
			if (forseti.addEstadoAnotacion(evento.getEstadoAnotacion()) != null) {
				datos = forseti.getEstadosAnotacion();
			} else {
				throw new EventoException("No se pudo crear el Estado de Anotación", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_ESTADO_ANOTACION_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un <code>EstadoAnotacion</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>EstadoAnotacion</code> a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Estados de anotación
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaEstadoAnotacion(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.eliminarEstadoAnotacion(evento.getEstadoAnotacion())) {
				datos = forseti.getEstadosAnotacion();
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Estado de Anotación ",
					null);
			}
		} catch (ForsetiException e) {
			throw new EventoException(
								"Debido a restricciones de integridad de datos no se pudo eliminar el Estado de Anotación ",
								e);

		} catch (Throwable e) {

			throw new EventoException(
								"Debido a restricciones de integridad de datos no se pudo eliminar el Estado de Anotación ",
								e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ELIMINA_ESTADO_ANOTACION_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>DominioNaturalezaJuridica</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>DominioNaturalezaJuridica</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de los dominios
	 * de naturaleza jurídica para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaDominioNaturalezaJuridica(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;

		try {
			if (forseti.addDominioNaturalezaJuridica(evento.getDominioNatJuridica()) != null) {
				datos = forseti.getDominiosNaturalezaJuridica();
			} else {
				throw new EventoException("No se pudo crear el Dominio de Naturaleza Jurídica", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_DOMINIO_NAT_JURIDICA_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un <code>DominioNaturalezaJuridica</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>DominioNaturalezaJuridica</code> a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Dominios de naturaleza jurídica
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaDominioNaturalezaJuridica(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.eliminarDominioNaturalezaJuridica(evento.getDominioNatJuridica())) {
				datos = forseti.getDominiosNaturalezaJuridica();
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Dominio de Naturaleza Jurídica ",
					null);
			}
		} catch (ForsetiException e) {
			throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Dominio de Naturaleza Jurídica ",
								e);

		} catch (Throwable e) {

			throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Dominio de Naturaleza Jurídica ",
											e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ELIMINA_DOMINIO_NAT_JURIDICA_OK);
		return evRespuesta;
	}

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>CirculoFestivo</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>CirculoFestivo</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de Circulos / Festivos
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaCirculoFestivo(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;

		try {
			CirculoFestivo circuloFestivo = evento.getCirculoFestivo();
			CirculoPk cid = new CirculoPk();
			cid.idCirculo = circuloFestivo.getIdCirculo();

			if (forseti.addCirculoFestivo(cid, circuloFestivo) != null) {
				datos = forseti.getFestivosCirculo(cid);
			} else {
				throw new AdicionCirculoFestivoException(
					"No se pudo crear el Círculo - Festivo  (El día festivo ya existe en el sistema)",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new AdicionCirculoFestivoException("No se pudo crear el día festivo al Círculo. "+e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new AdicionCirculoFestivoException("No se pudo crear el día festivo al Círculo. "+e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_CIRCULO_FESTIVO_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un <code>CirculoFestivo</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>CirculoFestivo</code> a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Círculos / Festivos
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaCirculoFestivo(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			CirculoFestivo circuloFestivo = evento.getCirculoFestivo();
			CirculoPk cid = new CirculoPk();
			cid.idCirculo = circuloFestivo.getIdCirculo();
			
			if (forseti.eliminarCirculoFestivo(evento.getCirculoFestivo())) {
				datos = forseti.getFestivosCirculo(cid);
			} else {
				throw new AdicionCirculoFestivoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Círculo - Festivo ",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ELIMINA_CIRCULO_FESTIVO_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de traer la información de un <code>CirculoFestivo</code> específico.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>CirculoFestivo</code> a ser consultado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con el <code>CirculoFestivo</code> consultado.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti seleccionaCirculoFestivo(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			CirculoFestivo circuloFestivo = evento.getCirculoFestivo();
			CirculoPk cid = new CirculoPk();
			cid.idCirculo = circuloFestivo.getIdCirculo();
			datos = forseti.getFestivosCirculo(cid);
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.SELECCIONA_CIRCULO_FESTIVO_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de traer la información de un <code>CirculoFestivo</code> específico.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>CirculoFestivo</code> a ser consultado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con el <code>CirculoFestivo</code> consultado.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti seleccionaNaturalezaJuridica(EvnAdministracionForseti evento)
		throws EventoException {

		NaturalezaJuridica datos = null;
		try {
			NaturalezaJuridica nat = evento.getNaturalezaJuridica();
			NaturalezaJuridicaPk cid = new NaturalezaJuridicaPk();
			cid.idNaturalezaJuridica = nat.getIdNaturalezaJuridica();
                        /*
        *  @author Carlos Torres
        *  @chage   se asigna la propiedad version
        *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
        */
                        cid.version = nat.getVersion();
			datos = forseti.getNaturalezaJuridica(cid);
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.SELECCIONA_NATURALEZA_JURIDICA_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>Eje</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>Eje</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de Ejes
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaEje(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;

		try {
			if (forseti.addEje(evento.getEje(), fenrir.getUsuario(evento.getUsuario())) != null) {
				datos = forseti.getEjes();
			} else {
				throw new EventoException(
					"No se pudo crear el Eje (El código proporcionado ya existe en el sistema)",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(datos, EvnRespAdministracionForseti.ADICIONA_EJE_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un <code>Eje</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>Eje</code> a ser eliminado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Ejes
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaEje(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			if (forseti.eliminarEje(evento.getEje(),fenrir.getUsuario(evento.getUsuario()))) {
				datos = forseti.getEjes();
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar el Eje",
					null);
			}
		} catch (ForsetiException e) {

			throw new  EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Eje",e);
		} catch (Throwable e) {

			throw new  EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Eje",e);
		}
		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(datos, EvnRespAdministracionForseti.ELIMINA_EJE_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar un nuevo <code>ZonaRegistral</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del nuevo <code>ZonaRegistral</code> a ser creado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de zonas registrales
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaZonaRegistral(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;

		try {
			Circulo circulo = evento.getCirculo();
			CirculoPk cid = new CirculoPk();
			cid.idCirculo = circulo.getIdCirculo();

			if (forseti.addZonaRegistralToCirculo(cid, evento.getZonaRegistral()) != null) {
				datos = forseti.getZonaRegistralesCirculo(cid);
			} else {
				throw new EventoException(
					"La vereda ya se encuentra asignada en una zona registral",
					null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_ZONA_REGISTRAL_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de eliminar un <code>ZonaRegistral</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>ZonaRegistral</code> a ser eliminada.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la lista actualizada de Zonas Registrales
	 * para que sea desplegada por las capas superiores.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti eliminaZonaRegistral(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			Circulo circulo = evento.getCirculo();
			CirculoPk cid = new CirculoPk();
			cid.idCirculo = circulo.getIdCirculo();
			if (forseti.eliminarZonaRegistral(evento.getZonaRegistral())) {
				datos = forseti.getZonaRegistralesCirculo(cid);
			} else {
				throw new EventoException(
					"Debido a restricciones de integridad de datos no se pudo eliminar la Zona Registral ",
					null);
			}
		} catch (ForsetiException e) {
			throw new EventoException(
								"Debido a restricciones de integridad de datos no se pudo eliminar la Zona Registral ",
								e);
		} catch (Throwable e) {
			throw new EventoException(
								"Debido a restricciones de integridad de datos no se pudo eliminar la Zona Registral ",
								e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ELIMINA_ZONA_REGISTRAL_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de traer la información de una <code>ZonaRegistral</code> específica.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * del <code>ZonaRegistral</code> a ser consultado.
	 *
	 * @return <code>EvnRespAdministracionForseti</code> con la <code>ZonaRegistral</code> consultada.
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti seleccionaZonaRegistral(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = null;
		try {
			Circulo circulo = evento.getCirculo();
			CirculoPk cid = new CirculoPk();
			cid.idCirculo = circulo.getIdCirculo();
			datos = forseti.getZonaRegistralesCirculo(cid);
			datos.add(forseti.getCirculo(cid));
		} catch (ForsetiException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.SELECCIONA_ZONA_REGISTRAL_OK);
		return evRespuesta;
	}

	/**
	 * Manejo del evento para el traslado de Folios
	 * @param evento
	 * @return
	 * @throws EventoException
         * @author      :   Julio Alcázar Rivas
         * @change      :   Cambios comportamiento del metodo
         * Caso Mantis  :   07123
	 */
       	private EvnRespAdministracionForseti trasladar(EvnAdministracionForseti evento)
		throws EventoException {

		HashMap resultado = new LinkedHashMap();
                TrasladoSIR traslado = new TrasladoSIR();
                String matricula = null;
                List matriculas = evento.getMatriculas();
		String cirorigen  = evento.getCirculoOrigen().getIdCirculo();
                String cirdestino = evento.getCirculoDestino().getIdCirculo();
                List fundamentos = evento.getFundamentos();
                String idUsuario = evento.getUsuario().getUsuarioId();
                /*
                 * @author      : Julio Alcázar Rivas
                 * @change      : nuevas variables para ejecutar el traslado
                 * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                 */
                String tipo_circulo = evento.getTipo_circulo();
                    try {
                        /*
                         * @author      : Ellery Robles
                         * @change      : cambios parametros del metodo TrasladoFolio()
                         * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                         */
                          List resultados = traslado.TrasladoFolio(matriculas, cirorigen, cirdestino, fundamentos, idUsuario, tipo_circulo);
                          for(Object result : resultados){
                              Object[] matriculaorigen = (Object[])result;
                               resultado.put(matriculaorigen[0], matriculaorigen[1]);
                          }
                    }
                    catch (GeneralSIRException e) {
                        Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
                        throw new EventoException(e.getMessage(), e);
                    }
                    catch (Throwable e) {
                        Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
                        throw new EventoException(e.getMessage(), e);
                    }


		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(resultado, EvnRespAdministracionForseti.TRASLADO_OK);
                return evRespuesta;
	}


        /**
	 * Manejo del evento para el traslado de Folios Masivo
	 * @param evento
	 * @return EvnRespAdministracionForseti
	 * @throws EventoException
         * @author      :   Carlos Mario Torres Urina
         * @change      :   Cambios comportamiento del metodo
         * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
	 */
       	private EvnRespAdministracionForseti trasladarMasivo(EvnAdministracionForseti evento)
		throws EventoException {

		HashMap resultado = new LinkedHashMap();
                TrasladoSIR traslado = new TrasladoSIR();
                
                 List matriculas = evento.getMatriculas();
		String cirorigen  = evento.getCirculoOrigen().getIdCirculo();
                String cirdestino = evento.getCirculoDestino().getIdCirculo();
                String idUsuario = evento.getUsuario().getUsuarioId();
                String municipioOrigen = evento.getMunicipioOrigen();
                String municipioDestino = evento.getMunicipio().getIdMunicipio();
                String departamentoDestino = evento.getDepartamento().getIdDepartamento();
                
                List fundamentos = evento.getFundamentos();
                
                
                String tipo_circulo = evento.getTipo_circulo();
                CirculoPk pk = new CirculoPk(cirorigen);
                String nombrearchivo = "";
                List<String> errores = new ArrayList<String>();
                Circulo circulo = null;
                    try {
                        SimpleDateFormat f = new SimpleDateFormat("ddMMyyyy");
                        SimpleDateFormat f1 = new SimpleDateFormat("HHss");
                        circulo = forseti.getCirculo(pk);
                        Date c = new Date();
                        nombrearchivo = circulo.getIdCirculo()+municipioOrigen+f.format(c)+"-"+circulo.getNombre()+"-"+f1.format(c)+".csv";
                    } catch (Throwable ex) {
                        Logger.getLogger(ANAdministracionForseti.class.getName()).log(Level.SEVERE, null, ex);
                        throw new EventoException(ex.getMessage(), ex);
                    }

                    try {
                        java.io.File saveTo = new java.io.File(RUTA_DESTINO_ARCHIVO+circulo.getIdCirculo());
                        if(!saveTo.exists())
                        {
                            saveTo.mkdir();
                        }
                        List<Object[]> matriculasDestino = traslado.TrasladoFolioMasivo(matriculas, cirorigen,municipioOrigen, cirdestino,departamentoDestino,municipioDestino,fundamentos,idUsuario,tipo_circulo);
                        FileItem fi = (FileItem)evento.getArchivoOrigen();
                        BufferedReader br = new BufferedReader(new InputStreamReader(fi.getInputStream()));
                        saveTo = new java.io.File(RUTA_DESTINO_ARCHIVO+circulo.getIdCirculo()+SO+nombrearchivo);
                        saveTo.createNewFile();
                        PrintWriter pw = new PrintWriter(saveTo);
                        String namecolums = br.readLine();
                        namecolums = namecolums+ ",DESTINO";
                        pw.println(namecolums);
                        for(Object[] matricula:matriculasDestino){
                            String row = br.readLine();
                                               
                            if(matricula[1].toString().contains("{Error}")){
                                resultado.put(matricula[0],matricula[1] + " " + matricula[2]);
                                errores.add("Folio: " + matricula[0] + " " + matricula[1]);
                            }else{
                                resultado.put(matricula[0],matricula[1]);
                            }
                            if(row!=null){
                                    pw.println(row+","+matricula[1]);
                            }
                            
                        }
                        pw.close();
                        br.close();
                        
                      
                    }
                    catch (GeneralSIRException e) {
                        resultado.put("Error", e.getMessage());
                    }
                    catch (Throwable e) {
                        Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
                        throw new EventoException(e.getMessage(), e);
                    }
                
		EvnRespAdministracionForseti evRespuesta = new EvnRespAdministracionForseti(resultado, EvnRespAdministracionForseti.TRASLADO_MASIVO_OK);
                evRespuesta.setErrores(errores);
                return evRespuesta;
	}
	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de adicionar una nueva <code>OficinaOrigen</code> en la base de datos.
	 *
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * de la nueva <code>OficinaOrigen</code> a ser creado.
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de los objetos de
	 * tipo <code>OficinaOrigen</code>
	 *
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti adicionaOficinaOrigen(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = new ArrayList();

		try {
			Vereda vereda = evento.getVereda();

			if (vereda == null) {
				throw new AdministracionOficinasOrigenException("Debe proporcionar una vereda", null);
			}

			VeredaPk vid = new VeredaPk();
			vid.idDepartamento = vereda.getIdDepartamento();
			vid.idMunicipio = vereda.getIdMunicipio();
			vid.idVereda = vereda.getIdVereda();

			Iterator itCate = evento.getCategorias().iterator();
			OficinaOrigen oficina = evento.getOficinaOrigen();
			while(itCate.hasNext()){
				Categoria cate = (Categoria)itCate.next();
				OficinaCategoria ofCate = new OficinaCategoria();
				ofCate.setCategoria(cate);
				oficina.addCategoria(ofCate);
			}

			forseti.addOficinaOrigen(oficina);

			Iterator itDatos = forseti.getOficinasOrigenByVereda(vid).iterator();
			while(itDatos.hasNext()){
				OficinaOrigen nOff = (OficinaOrigen)itDatos.next();
				OficinaOrigenPk id = new OficinaOrigenPk();
				id.idOficinaOrigen = nOff.getIdOficinaOrigen();
                                /*
                                *  @author Carlos Torres
                                *  @chage   se agrega validacion de version diferente
                                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                */
                                id.version = nOff.getVersion();
				datos.add(forseti.getOficinaOrigen(id));
			}

		} catch (ForsetiException e) {
			ValidacionParametrosException exception = null;
			if (e.getErrores()!=null && !e.getErrores().isEmpty()){
				exception = new ValidacionParametrosException(e.getErrores());
				throw exception;
			}
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new AdministracionOficinasOrigenException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new AdministracionOficinasOrigenException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.ADICIONA_OFICINA_ORIGEN_OK);
		return evRespuesta;
	}

	/**
	 * Este método se encarga de consultar la lista de objetos <code>OficinaOrigen</code> existentes
	 * en  la base de datos.
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * de la  <code>Vereda</code>
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de los objetos de
	 * tipo <code>OficinaOrigen</code>
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti consultaOficinasOrigenPorVereda(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = new ArrayList();

		try {
			Vereda vereda = evento.getVereda();

			if (vereda == null) {
				throw new EventoException("Debe proporcionar una vereda", null);
			}

			VeredaPk vid = new VeredaPk();
			vid.idDepartamento = vereda.getIdDepartamento();
			vid.idMunicipio = vereda.getIdMunicipio();
			vid.idVereda = vereda.getIdVereda();

			Iterator itDatos = forseti.getOficinasOrigenByVereda(vid).iterator();
			while(itDatos.hasNext()){
				OficinaOrigen nOff = (OficinaOrigen)itDatos.next();
				OficinaOrigenPk id = new OficinaOrigenPk();
				id.idOficinaOrigen = nOff.getIdOficinaOrigen();
                                 /*
                                *  @author Carlos Torres
                                *  @chage   se agrega validacion de version diferente
                                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                */
                                id.version = nOff.getVersion();
				datos.add(forseti.getOficinaOrigen(id));
			}
		} catch (HermodException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.CONSULTA_OFICINA_ORIGEN_BY_VEREDA_OK);
		/**Si el evento es generado por el reporte 31 se cambia el tipo de evento*/
		if(evento.getTipoEvento().equals("CONSULTA_OFICINA_POR_VEREDA")){
			evRespuesta.setTipoEvento(EvnRespAdministracionForseti.CONSULTA_OFICINA_BY_VEREDA_OK);
		}
		return evRespuesta;
	}
	
	/**
	 * Este método se encarga de consultar la lista de objetos <code>OficinaOrigen</code> existentes
	 * en  la base de datos, que seab de tipo notaria.
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * de la  <code>Vereda</code>
	 * @return <code>EvnRespAdministracionForseti</code> con la lista  actualizada de los objetos de
	 * tipo <code>OficinaOrigen</code>
	 * @throws <code>EventoException</code>
	 */
	private EvnRespAdministracionForseti consultaNotariasPorMunicipio(EvnAdministracionForseti evento)
		throws EventoException {

		List datos = new ArrayList();

		try {
			Vereda vereda = evento.getVereda();

			if (vereda == null) {
				throw new EventoException("Debe proporcionar una vereda", null);
			}

			MunicipioPk mid = new MunicipioPk();
			mid.idDepartamento = vereda.getIdDepartamento();
			mid.idMunicipio = vereda.getIdMunicipio();

			Iterator itDatos = forseti.getOficinasOrigenByMunicipio(mid).iterator();
			while(itDatos.hasNext()){
				OficinaOrigen nOff = (OficinaOrigen)itDatos.next();
				OficinaOrigenPk id = new OficinaOrigenPk();
				id.idOficinaOrigen = nOff.getIdOficinaOrigen();
                                 /*
                                *  @author Carlos Torres
                                *  @chage   se agrega validacion de version diferente
                                *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                */
                                id.version = nOff.getVersion();
				
				OficinaOrigen ofiOrig = forseti.getOficinaOrigen(id);
				if(ofiOrig.getTipoOficina()!=null && ofiOrig.getTipoOficina().getIdTipoOficina().equals(CTipoOficina.TIPO_OFICINA_NOTARIA)){
					datos.add(forseti.getOficinaOrigen(id));	
				}
				
			}
		} catch (HermodException e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionForseti evRespuesta =
			new EvnRespAdministracionForseti(
				datos,
				EvnRespAdministracionForseti.CONSULTA_OFICINA_ORIGEN_BY_VEREDA_OK);
		return evRespuesta;
	}	

	/**
	 * Este método se encarga de utilizar un <code>Circulo</code>, una
         * fecha inicial de tipo <code>Date</code> y una fecha final de tipo
         * <code>Date</code> para ejecutar el proceso de catastro
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * de ejecución del proceso de catastro
	 * @return <code>EvnRespAdministracionForseti</code>
	 * @throws <code>EventoException</code>
	 */
        private EvnRespAdministracionForseti catastro(EvnAdministracionForseti evento)
		throws EventoException  {

            boolean resp = false;

            try {
                Date fechIni = evento.getFechaInicialCatastro();
                Date fechFin = evento.getFechaFinalCatastro();
                Circulo circuloProceso = evento.getCirculo();

                // Aqui se debe insertar el llamado al procedimiento de catastro
                resp = forseti.catastro(fechIni, fechFin, circuloProceso,  evento.getUsuarioSIR());

            } catch (Throwable e) {
                Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
                throw new EventoException(e.getMessage(), e);
            }

            EvnRespAdministracionForseti evRespuesta =
                    new EvnRespAdministracionForseti(
                            new Boolean(resp),
                            EvnRespAdministracionForseti.CATASTRO_OK);
            return evRespuesta;
        }

	/**
	 * Este método se encarga de utilizar un <code>Circulo</code>, una
		 * fecha inicial de tipo <code>Date</code> y una fecha final de tipo
		 * <code>Date</code> para ejecutar el proceso de catastro
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * de ejecución del proceso de cargar los tipos de plantillas de certificados
	 * @return <code>EvnRespAdministracionForseti</code>
	 * @throws <code>EventoException</code>
	 */
		private EvnRespAdministracionForseti cargarTiposPlantilla(EvnAdministracionForseti evento)
		throws EventoException  {

			boolean resp = false;
            List listPlantillas=null;

			try {
				/*Date fechIni = evento.getFechaInicialCatastro();
				Date fechFin = evento.getFechaFinalCatastro();
				Circulo circuloProceso = evento.getCirculo();

				// Aqui se debe insertar el llamado al procedimiento de catastro
				resp = forseti.catastro(fechIni, fechFin, circuloProceso,  evento.getUsuarioSIR());*/
            	listPlantillas = forseti.getPlantillaPertenencias();

			} catch (Throwable e) {
				Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
				throw new EventoException(e.getMessage(), e);
			}

			EvnRespAdministracionForseti evRespuesta =
					new EvnRespAdministracionForseti(
							new Boolean(resp), listPlantillas,
							EvnRespAdministracionForseti.CARGAR_PLANTILLAS_CERTIFICADOS);
			return evRespuesta;
		}

	/**
	 * Este método se encarga de utilizar un <code>Circulo</code>, una
		 * fecha inicial de tipo <code>Date</code> y una fecha final de tipo
		 * <code>Date</code> para ejecutar el proceso de catastro
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * de ejecución del proceso de cargar los tipos de plantillas de certificados
	 * @return <code>EvnRespAdministracionForseti</code>
	 * @throws <code>EventoException</code>
	 */
		private EvnRespAdministracionForseti editarPlantilla(EvnAdministracionForseti evento)
		throws EventoException  {

			boolean resp = false;
            String respuesta=null;
            String descripcion=null;
			try {
				/*Date fechIni = evento.getFechaInicialCatastro();
				Date fechFin = evento.getFechaFinalCatastro();
				Circulo circuloProceso = evento.getCirculo();

				// Aqui se debe insertar el llamado al procedimiento de catastro
				resp = forseti.catastro(fechIni, fechFin, circuloProceso,  evento.getUsuarioSIR());*/
        		respuesta=evento.getRespuesta();
        		descripcion=evento.getDescripcion();
        		forseti.updatePlantillaPertenenciaByRespuesta(respuesta,descripcion);

			} catch (Throwable e) {
				Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
				throw new EventoException(e.getMessage(), e);
			}

			EvnRespAdministracionForseti evRespuesta =
					new EvnRespAdministracionForseti(
							new Boolean(resp),
							EvnRespAdministracionForseti.EDITAR_PLANTILLA);
			return evRespuesta;
		}



	/**
	 * Este método se encarga de utilizar un <code>Circulo</code>, una
	 * fecha inicial de tipo <code>Date</code> y una fecha final de tipo
     * <code>Date</code> para ejecutar el proceso de catastro
	 * @param evento de tipo <code>EvnAdministracionForseti</code> con la información
	 * de ejecución del proceso de cargar los tipos de plantillas de certificados
	 * @return <code>EvnRespAdministracionForseti</code>
	 * @throws <code>EventoException</code>
	 */
		private EvnRespAdministracionForseti reabrirFolio(EvnAdministracionForseti evento)
		throws EventoException  {

			boolean resp = false;
			Folio folio=null;
			String comentario=null;
                        /**
                        * @Author Carlos Torres
                        * @Mantis 13176
                        * @Chaged
                        */
                        java.util.Map infoUsuario = new java.util.HashMap();
                        if(evento.getInfoUsuario() !=null){
                            infoUsuario.put("user",evento.getInfoUsuario().getUser());
                            infoUsuario.put("host",evento.getInfoUsuario().getHost());
                            infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                            infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                            infoUsuario.put("idTurno",evento.getFolio().getIdMatricula());
                        }
                        co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR aud = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
			try {
                            aud.guardarDatosTerminal(infoUsuario);
                        } catch (GeneralSIRException ex) {
                            Logger.getLogger(ANAdministracionForseti.class.getName()).log(Level.SEVERE, null, ex);
                        }
			try {

				folio=evento.getFolio();
				FolioPk id = new FolioPk();
				id.idMatricula = folio.getIdMatricula();
				comentario = evento.getComentario();
				Usuario usuario = evento.getUsuarioSIR();


				//Se verifica si el folio tiene bloqueos
				Usuario usuarioBloqueo = forseti.getBloqueoFolio(id);
				Turno turnoTemp = null;

				if(usuarioBloqueo!=null){
					//Se desbloquea el folio si estaba bloqueado.
					forseti.desbloquearFolio(folio);
					List llavesBloqueos= usuarioBloqueo.getLlavesBloqueos();
					LlaveBloqueo lb= (LlaveBloqueo)llavesBloqueos.get(0);
					List bloqueoFolios= lb.getBloqueoFolios();
					BloqueoFolio bf= (BloqueoFolio) bloqueoFolios.get(0);
					if(bf!=null&&bf.getIdWorkflowBloqueo()!=null){
						turnoTemp = hermod.getTurnobyWF(bf.getIdWorkflowBloqueo());
					}
				}

				//1. Se intenta bloquear el folio:

				List folios = new ArrayList();
				folios.add(folio.getIdMatricula());
				LlaveBloqueo llave = forseti.bloquearFolios(folios, usuario, null);

				//2. Se hace el cambio de estado a activo

				EstadoFolio estado = new EstadoFolio();
				estado.setIdEstado(CEstadoFolio.ACTIVO);
				estado.setComentario(comentario);

				folio.setEstado(estado);
				forseti.updateFolio(folio, usuario, null, false);

                                //3. Se hacen definitivos los cambios
				if(turnoTemp==null || turnoTemp.getIdProceso()!=Long.parseLong(CProceso.PROCESO_REGISTRO))
					forseti.hacerDefinitivoFolio(folio, usuario, null, false);

				/*
				//4. Se desbloquea el folio (Ya se desbloquea en hacerDefinitivo folio)
				 */
				forseti.desbloquearFolio(folio);
				

				//Se deja el bloqueo con el usuario que lo tenia bloqueado.
				if(usuarioBloqueo!=null){
					TurnoPk tid = null;
					if(turnoTemp!=null){
						tid = new TurnoPk();
						tid.anio = turnoTemp.getAnio();
						tid.idCirculo = turnoTemp.getIdCirculo();
						tid.idProceso = turnoTemp.getIdProceso();
						tid.idTurno = turnoTemp.getIdTurno();
					}

					llave = forseti.bloquearFolios(folios, usuarioBloqueo, tid);
				}

				//CONSULTA DEL FOLIO PARA ACTUALIZARLO

				folio = forseti.getFolioByIDSinAnotaciones(id);

			} catch (Throwable e) {
				Log.getInstance().error(ANAdministracionForseti.class,e.getMessage(), e);
				throw new EventoException(e.getMessage(), e);
			}

			EvnRespAdministracionForseti evRespuesta =
					new EvnRespAdministracionForseti(
							folio,
							EvnRespAdministracionForseti.REABRIR_FOLIO_OK);
			return evRespuesta;
		}


  /*
   * @author      :   Julio Alcázar Rivas
   * @change      :   Nuevo metodo validarMatricula
   * Caso Mantis  :   07123
   */
   private EventoRespuesta validarMatricula(EvnAdministracionForseti evento) throws EventoException {

      String matricula = evento.getFolio().getIdMatricula().trim();


      ValidacionesSIR validacion = new ValidacionesSIR();
		try {
		    boolean buzz_ValidateInconsistenteFolio = forseti.inconsistenteFolio( matricula );
                    if(buzz_ValidateInconsistenteFolio){
                        throw new EventoException("El folio identificado con matrícula:" + matricula + " es inconsistente.");
                    }
                    if (forseti.existeFolio(matricula)){
                       if(forseti.trasladadoFolio(matricula.trim())){
                           throw new EventoException("El folio identificado con matrícula:" + matricula + " ha sido trasladado.");
			}
                        Folio folio = forseti.getFolioByID(matricula);
                        if(!folio.isDefinitivo()){
                           throw new EventoException("El folio identificado con matrícula:" + matricula + " no se encuentra en estado definitivo.");
                        }
                        if (forseti.bloqueadoFolio(matricula)) {
                           throw new EventoException(CValidacion.FOLIO_BLOQUEADO_MSG);
                        }                        
                        if(validacion.isMatriculaTemporales(matricula)){
                           throw new EventoException("El folio identificado con matrícula:" + matricula + " tiene datos temporales.");
                        }
                        if (forseti.tieneDeudaFolio(matricula)) {
                        throw new EventoException(CValidacion.FOLIO_DEBE_DINERO_MSG);
                        }
                        if (forseti.enCustodiaFolio(matricula)) {
                            throw new EventoException(CValidacion.FOLIO_EN_CUSTODIA_MSG);
                        }
                        if (forseti.enTramiteFolio(matricula)) {
                            throw new EventoException(CValidacion.FOLIO_EN_TRAMITE_MSG);
                        }
                        if (forseti.anuladoFolio(matricula)) {
                            throw new EventoException(CValidacion.FOLIO_ANULADO_MSG);
                        }
                        /*
                        * @author      :   Julio Alcázar Rivas
                        * @change      :   se elimina la validacion de folios cerrados
                        * Caso Mantis  :   07676
                        */
                        if (forseti.inconsistenteFolio(matricula)) {
                            throw new EventoException(CValidacion.FOLIO_INCONSISTENTE_MSG);
			}
			EvnRespAdministracionForseti evn = new EvnRespAdministracionForseti(evento.getUsuario(), EvnRespAdministracionForseti.VALIDAR_MATRICULA);
                        evn.setMatricula(matricula);
                        return evn;
	            }
                    throw new EventoException("La matrícula " + matricula + " no existe");
		} catch (EventoException e) {
			throw e;
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANAdministracionForseti.class,"No se pudo encontrar el servicio:" + ep.toString());
			throw new EventoException("Servicio no encontrado", e);
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANAdministracionForseti.class,"Error en el servicio para validar la matrícula:" + ep.toString());
			throw new EventoException("Error en el servicio para validar la matrícula", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANAdministracionForseti.class,"Se produjo un error validando la matrícula:" + ep.toString());
			throw new EventoException("Se produjo un error validando la matrícula", e);
		}
	}
    /*
   * @author      :   Carlos Mario Torres Uirna
   * @change      :   Nuevo metodo validarMatriculasMasivo
   * Caso Mantis  :   11309: Acta - Requerimiento No 023_453 - Traslado_Masivo_Folios
   */
   private EventoRespuesta validarMatriculasMasivo(EvnAdministracionForseti evento) throws EventoException {

      List<String> matriculas = (List<String>)evento.getMatriculas();
      EvnRespAdministracionForseti evn = new EvnRespAdministracionForseti(evento.getUsuario(), EvnRespAdministracionForseti.VALIDAR_MATRICULA_MASIVO);
      List<String> exps = new ArrayList<String>();
      List<String> matriR = new ArrayList<String>();
      String inimensaje = "El/Los folios identificados con matrícula: ";
      String errores = "";
      String[] excepciones = new String[]{"%s son inconsistente.",
                                          "%s han sido trasladados.",
                                          "%s no se encuentra en estado definitivo.",
                                          "%s Los/"+ CValidacion.FOLIO_BLOQUEADO_MSG,
                                          "%s tienen datos temporales.",
                                          "%s Los/"+ CValidacion.FOLIO_DEBE_DINERO_MSG,
                                          "%s Los/"+ CValidacion.FOLIO_EN_CUSTODIA_MSG,
                                          "%s Los/"+ CValidacion.FOLIO_EN_TRAMITE_MSG,
                                          "%s Los/"+ CValidacion.FOLIO_ANULADO_MSG,
                                          "%s Los/"+ CValidacion.FOLIO_INCONSISTENTE_MSG,
                                          "%s no existen"};
      String[] excepcionesMatriculas = new String[]{"","","","","","","","","","",""};
      int count = 0;
      for(String matricula: matriculas){
      ValidacionesSIR validacion = new ValidacionesSIR();
		try {

		    boolean buzz_ValidateInconsistenteFolio = forseti.inconsistenteFolio( matricula );
                    if(buzz_ValidateInconsistenteFolio){
                        excepcionesMatriculas[0] +=matricula+","; 
                        throw new EventoException("El folio identificado con matrícula:" + matricula + " es inconsistente.");
                    }
                    if (forseti.existeFolio(matricula)){
                       if(forseti.trasladadoFolio(matricula.trim())){
                           excepcionesMatriculas[1] +=matricula+", ";
                           throw new EventoException("El folio identificado con matrícula:" + matricula + " ha sido trasladado.");
			}
                        Folio folio = forseti.getFolioByID(matricula);
                        if(!folio.isDefinitivo()){
                            excepcionesMatriculas[2] +=matricula+", ";
                           throw new EventoException("El folio identificado con matrícula:" + matricula + " no se encuentra en estado definitivo.");
                        }
                        if (validacion.enTramiteFolio(matricula)) {
                            excepcionesMatriculas[7] +=matricula+", ";
                            throw new EventoException("El folio identificado con matrícula:"+matricula+" " + CValidacion.FOLIO_EN_TRAMITE_MSG);
                        }
                        if (forseti.inconsistenteFolio(matricula)) {
                            excepcionesMatriculas[9] +=matricula+", ";
                            throw new EventoException("El folio identificado con matrícula:"+matricula+" " + CValidacion.FOLIO_INCONSISTENTE_MSG);
			}
                        matriR.add(matricula);
	            }else{
                        excepcionesMatriculas[10] +=matricula+", ";
                        throw new EventoException("La matrícula " + matricula + " no existe");
                    }
		} catch (EventoException e) {
                   errores +=e.getMessage()+", ";
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANAdministracionForseti.class,"No se pudo encontrar el servicio:" + ep.toString());
			throw new EventoException("Servicio no encontrado", e);
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANAdministracionForseti.class,"Error en el servicio para validar la matrícula:" + ep.toString());
			throw new EventoException("Error en el servicio para validar la matrícula", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANAdministracionForseti.class,"Se produjo un error validando la matrícula:" + ep.toString());
			throw new EventoException("Se produjo un error validando la matrícula", e);
		}
                Log.getInstance().info(ANAdministracionForseti.class,"Validada "+matricula +":"+ (count+1));
       }
      if(errores.length()>0)
      {
          errores = "";
          for(int i = 0;i<excepciones.length;i++)
          {
              if(!excepcionesMatriculas[i].equals("")){
                errores += inimensaje + String.format(excepciones[i],excepcionesMatriculas[i].substring(0,excepcionesMatriculas[i].lastIndexOf(",")))+", "; 
              }
          } 
          throw new EventoException(errores);
      }
      evn.setListaMatriculas(matriR);
      evn.setErrores(exps);
      return evn;
   }
   /*
    * @author      : Carlos Mario Torres Urina
    * @change      : nuevo metodo trasladoConfirmacion()
    * @revision    : Se lanza la excepcion en el caso que ocurra
    * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
    */
   private EventoRespuesta trasladoConfirmacionMasivo(EvnAdministracionForseti evento) throws EventoException {

      List<String> matriculas = evento.getMatriculas();
      List<Folio> folios = new ArrayList<Folio>();
      Folio folio = evento.getFolio();
     
      Vereda vereda = evento.getVereda();
       /*
    * @author      : Carlos Mario Torres Urina
    * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
    */
      List resolucioDestino = evento.getFundamentos();
      Usuario usuario = evento.getUsuarioSIR();
      for(String m:matriculas)
      {
            Folio f = null;
            try {
                f = forseti.getFolioByID(m);
                folios.add(f);

        } catch (Throwable e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
	    Log.getInstance().error(ANAdministracionForseti.class,"Se produjo un error validando la matrícula:" + ep.toString());
	    throw new EventoException("Se produjo un error validando la matrícula", e);
        }
      
      
      }
       
       TrasladoSIR trasladoSIR = new TrasladoSIR();
       
        try {
            if (vereda != null){
                   /*
                    * @author      : Carlos Mario Torres Urina
                    * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                    */
                trasladoSIR.ConfirmacionTrasladoMasivo(vereda.getIdDepartamento(), vereda.getIdMunicipio(), vereda.getIdVereda(), matriculas, resolucioDestino,usuario.getIdUsuario());                
            }else {
                 /*
                    * @author      : Carlos Mario Torres Urina
                    * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                    */
                trasladoSIR.ConfirmacionTrasladoMasivo(null, null, null, matriculas, resolucioDestino,usuario.getIdUsuario());
            }

        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANAdministracionForseti.class.getName()).log(Level.SEVERE, null, ex);
            throw new EventoException(ex.getMessage(), ex);
        }
       
      EvnRespAdministracionForseti evn = new EvnRespAdministracionForseti(folio,EvnRespAdministracionForseti.TRASLADO_CONFIRMACION_MASIVO);
      return evn;

   }
   
   /*
    * @author      : Julio Alcázar Rivas
    * @change      : nuevo metodo folioConfirmacion()
    * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
    */
   private EventoRespuesta folioConfirmacion(EvnAdministracionForseti evento) throws EventoException {

      String matricula = evento.getFolio().getIdMatricula().trim();

      Folio folio = null;
        try {
            folio = forseti.getFolioByID(matricula);
            
            

        } catch (Throwable e) {
            ExceptionPrinter ep = new ExceptionPrinter(e);
	    Log.getInstance().error(ANAdministracionForseti.class,"Se produjo un error validando la matrícula:" + ep.toString());
	    throw new EventoException("Se produjo un error validando la matrícula", e);
        }

      EvnRespAdministracionForseti evn = new EvnRespAdministracionForseti(folio, EvnRespAdministracionForseti.FOLIO_CONFIRMACION);
      return evn;

   }

   /*
    * @author      : Julio Alcázar Rivas
    * @change      : nuevo metodo trasladoConfirmacion()
    * @revision    : Se lanza la excepcion en el caso que ocurra
    * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
    */
   private EventoRespuesta trasladoConfirmacion(EvnAdministracionForseti evento) throws EventoException {


      Folio folio = evento.getFolio();
      String matricula = evento.getFolio().getIdMatricula().trim();
      Vereda vereda = evento.getVereda();
       /*
                    * @author      : Carlos Mario Torres Urina
                    * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                    */
      List resolucioDestino = evento.getFundamentos();
      Usuario usuario = evento.getUsuarioSIR();

       TrasladoSIR trasladoSIR = new TrasladoSIR();
        try {
            if (vereda != null){
                 /*
                    * @author      : Carlos Mario Torres Urina
                    * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                    */
                trasladoSIR.ConfirmacionTraslado(vereda.getIdDepartamento(), vereda.getIdMunicipio(), vereda.getIdVereda(), matricula, resolucioDestino,usuario.getIdUsuario());                
            }else {
                 /*
                    * @author      : Carlos Mario Torres Urina
                    * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                    */
                trasladoSIR.ConfirmacionTraslado(null, null, null, matricula, resolucioDestino,usuario.getIdUsuario());
            }

        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANAdministracionForseti.class.getName()).log(Level.SEVERE, null, ex);
            throw new EventoException(ex.getMessage(), ex);
        }
      EvnRespAdministracionForseti evn = new EvnRespAdministracionForseti(folio,EvnRespAdministracionForseti.TRASLADO_CONFIRMACION);
      return evn;

   }
}

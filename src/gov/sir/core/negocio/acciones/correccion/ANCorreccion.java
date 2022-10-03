package gov.sir.core.negocio.acciones.correccion;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
/*
* @author : CTORRES
* @change : se agregan imports
* Caso Mantis : 12291
* No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
import gov.sir.core.eventos.correccion.EvnModificarTestamento;
import gov.sir.core.eventos.registro.EvnRespTestamentos;
import co.com.iridium.generalSIR.negocio.testamento.TestamentoSIR;
import gov.sir.core.negocio.acciones.excepciones.RegistroTestamentosException;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.eventos.correccion.EvnCorreccion;
import gov.sir.core.eventos.correccion.EvnCorreccionFolioPadresHijos;
import gov.sir.core.eventos.correccion.EvnRespCorreccion;
import gov.sir.core.eventos.correccion.EvnRespCorreccionFolioPadresHijos;
import gov.sir.core.eventos.registro.EvnEnglobe;
import java.util.ListIterator;
import gov.sir.core.negocio.acciones.excepciones.ANCorreccionException;
import gov.sir.core.negocio.acciones.excepciones.AnotacionTemporalRegistroException;
import gov.sir.core.negocio.acciones.excepciones.CorreccionFolioEditInfoInvalidParameterException;
import gov.sir.core.negocio.acciones.excepciones.CorreccionFolioEditPadresHijosErrorsOnOpperationException;
import gov.sir.core.negocio.acciones.excepciones.CorreccionFolioEditPadresHijosInvalidParameterException;
import gov.sir.core.negocio.acciones.excepciones.CorreccionImpresionCertificadoException;
import gov.sir.core.negocio.acciones.excepciones.Correccion_ImprimirCertificados_InvalidPrintDateException;
import gov.sir.core.negocio.acciones.excepciones.ErrorImpresionException;
import gov.sir.core.negocio.acciones.excepciones.FolioNoCreadoException;
import gov.sir.core.negocio.acciones.excepciones.LiquidacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.MatriculaInvalidaConfrontacionCorrecionException;
import gov.sir.core.negocio.acciones.excepciones.MatriculaInvalidaCorreccionException;
import gov.sir.core.negocio.acciones.excepciones.NegocioCorreccionesCorreccionSimpleCollectorException;
import gov.sir.core.negocio.acciones.excepciones.PagoInvalidoException;
import gov.sir.core.negocio.acciones.excepciones.PagoNoProcesadoException;
import gov.sir.core.negocio.acciones.excepciones.SegregacionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TomarFoliosException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.acciones.registro.SerialIds;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.AnotacionPk;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Cancelacion;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.DeltaFolio;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.EstadoHistoria;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.OficinaOrigenPk;
import gov.sir.core.negocio.modelo.OficioPk;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.SalvedadFolio;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoFolio;
import gov.sir.core.negocio.modelo.TurnoFolioMig;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.WebAnotacion;
import gov.sir.core.negocio.modelo.WebFolioHeredado;
import gov.sir.core.negocio.modelo.WebSegEng;
import gov.sir.core.negocio.modelo.WebSegEngPk;
import gov.sir.core.negocio.modelo.WebSegregacion;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CDigitacionMasiva;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CSolicitud;
import gov.sir.core.negocio.modelo.constantes.CTipoFormulario;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormulario;
/*
        * @author : CTORRES
        * @change : Se abilita el uso de las clases ImprimibleFormularioTestamento
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormularioTestamento;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.DeltaTestamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadanoPk;
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.util.CiudadanoComparator;
/**
* @autor:Edgar Lora
* @mantis: 0011599
* @requerimiento: 085_151
*/
import gov.sir.core.negocio.modelo.util.ComparadorAnotaciones;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.acciones.excepciones.BloqueoFoliosDigitacionHTException;
import gov.sir.core.web.acciones.excepciones.BloqueoFoliosEspecializadoHTException;
import gov.sir.core.web.acciones.excepciones.BloqueoFoliosHTException;
import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicDateFormatComparator;
import gov.sir.core.web.helpers.correccion.diff.utils.jxpath.FolioAbstractFactory;

import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;

import gov.sir.hermod.HermodException;
import gov.sir.hermod.gdocumental.integracion.SGD;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;

import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/*
        * @author : CTORRES
        * @change : Se abilita el uso del paquete java.util.Date
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.jxpath.JXPathContext;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;
/**
 * Esta acción de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnCorreccion</CODE> y generar eventos de respuesta del tipo <CODE>EvnRespCorreccion</CODE>
 * Esta acción de negocio se encarga de atender todas las solicitudes relacionadas
 * con el avance del workflow del proceso de correcciones y llamar a los servicios
 * que se requieren en cada fase del proceso.
 * @author ppabon, jvelez
 */
public class ANCorreccion extends SoporteAccionNegocio {


   static final String JXPATH_FOLIO_COMPLEMENTACION_COMPLEMENTACION
       = "complementacion/complementacion";

	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 * Instancia de Hermod
	 */
	private PrintJobsInterface printJobs;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;




    //DEPURADO ENERO 17 2006
	/**
	 *Constructor de la clase ANCorrección.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANCorreccion() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el Service Locator", e);
		}


		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}

		if (printJobs == null) {
			throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
		}

	}



             /**
            * Recibe un evento de correciones y devuelve un evento de respuesta
            *
            * @param e el evento recibido. Este evento debe ser del tipo
            * <CODE>EvnCorreccion</CODE>
            * @throws EventoException cuando ocurre un problema que no se pueda manejar
            * @return el evento de respuesta. Este evento debe ser del tipo
            * <CODE>EvnRespCorreccion</CODE>
            * @see gov.sir.core.eventos.comun.EvnCorreccion
            * @see gov.sir.core.eventos.comun.EvnRespCorreccion
            */
            public EventoRespuesta perform(Evento e) throws EventoException {
                EvnCorreccion evento = (EvnCorreccion) e;


		//SE OBTIENE UN EVENTO INVALIDO.
		if ((evento == null) || (evento.getTipoEvento() == null)) {
			return null;
		}

        String local_TipoEvento;
        local_TipoEvento = evento.getTipoEvento();

        if( EvnCorreccion.CORRECCIONSIMPLEMAIN_LOADFOLIOANOTACIONXOPTION_BTNSTART__EVENT.equals( local_TipoEvento ) ) {
           return processCorreccionSimple_LoadFolioAnotacionX( evento );
        }

        if( EvnCorreccion.ELIMINARCAMBIOSANOTACIONDEFINITIVATEMPORAL_EVENT.equals( local_TipoEvento ) ) {
           return processCorreccionSimple_EliminarCambiosAnotacionDefinitivaConCambiosTemporales( evento );
        }

        if (EvnCorreccion.ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION_EVENT.equals(local_TipoEvento)) {
            return ejecutarCorreccionSimple_EnviarARevisarAprobar(evento);
        } //SI SE ESTA TERMINANDO LA EJECUCI?N DE LA ACTUACI?N ADMINISTRATIVA.
        else if (EvnCorreccion.TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA.equals(local_TipoEvento)) {
            return terminarEjecucionActuacionAdministrativa(evento);
        } //ENVIAR UN TURNO DE CORRECCION SIMPLE A REVISION Y ANALISIS.
        else if (EvnCorreccion.ENVIARCORRECCIONSIMPLEAREVISIONANALISIS_EVENT.equals(local_TipoEvento)) {
            return ejecutarCorreccionSimple_EnviarARevisarAnalisis(evento);
        } else if (EvnCorreccion.CORR_REVISIONAPROBACION__PRINTFORM_EVENT.equals(local_TipoEvento)) {

            /*
            * @author : CTORRES
            * @change : se agrega variable turno anterior y se agrega condicion para turno de correccion de testamento
            * Caso Mantis : 12291
            * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
             */
            Turno turnoAnterior = evento.getTurno().getSolicitud().getTurnoAnterior();
            if (turnoAnterior == null || (turnoAnterior != null && turnoAnterior.getIdProceso() != 6)) {
                tomarFolios(evento);
            }
            return ejecutarRevisionAprobacion_AprobarCorreccion_ImprimirFormulario(evento);
        } else if (EvnCorreccion.APROBAR.equals(local_TipoEvento)) {
            Turno turnoAnterior = evento.getTurno().getSolicitud().getTurnoAnterior();
	   if (turnoAnterior == null || (turnoAnterior != null && turnoAnterior.getIdProceso() != 6)) {
                    tomarFolios(evento);
            }
            return ejecutarRevisionAprobacion_AprobarCorreccion( evento );
            // return aprobarCorreccion(evento);
		}

        else if( EvnCorreccionFolioPadresHijos.FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM_EVENT.equals( local_TipoEvento )) {
             return foliosPadreHijo_addFolioPadre( evento );
        }


        else if( EvnCorreccionFolioPadresHijos.FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM_EVENT.equals( local_TipoEvento )) {
             return foliosPadreHijo_delFolioPadre( evento );
        }

        else if( EvnCorreccionFolioPadresHijos.FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM_EVENT.equals( local_TipoEvento )) {
             return foliosPadreHijo_addFolioHijo( evento );
         }

        else if( EvnCorreccionFolioPadresHijos.FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM_EVENT.equals( local_TipoEvento )) {
             return foliosPadreHijo_delFolioHijo( evento );
        }

        // bug: 3536
        // deshacer cambios en modelo temporal
        else if( EvnCorreccion.PAGEEVENT_CORRECCIONREVISARAPROBAR_DESHACERCAMBIOS.equals( local_TipoEvento ) ) {
           EventoRespuesta local_Result;
           tomarFolios( evento );
           local_Result = correccionRevisarAprobar_DeshacerCambios( evento );
           return local_Result;
        }

		else if (evento.getTipoEvento().equals(EvnCorreccion.VALIDAR_MATRICULA)) {
			return validarMatricula(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.VALIDAR_MATRICULA_EX)) {
                    // bug 3593
                    return validarMatriculaEx( evento );

		} else if (evento.getTipoEvento().equals(EvnCorreccion.CREAR_SOLICITUD)) {
			return crearSolicitud(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.TOMAR_FOLIO_CORRECCION) || evento.getTipoEvento().equals(EvnCorreccion.TOMAR_FOLIO_ESPECIALIZADO) || evento.getTipoEvento().equals(EvnCorreccion.TOMAR_FOLIO_DIGITACION)) {
			return tomarFolios(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.CONSULTAR_FOLIO)) {
			return consultaFolio(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.CONSULTAR_FOLIO_USUARIO)) {
			return consultarFolioUsuario(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.FORZAR_APROBACION)) {
			return forzarAprobacion(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.NEGAR)) {
			return negarCorreccion(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.CARGAR_CAMBIOS_PROPUESTOS)) {
			tomarFolios(evento);
            return cargarCambiosPropuestos(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.APROBAR_ESPECIALIZADO)) {
			return aprobarCorreccionEspecializado(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.NEGAR_ESPECIALIZADO)) {
			return negarCorreccionEspecializado(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.APROBAR_DIGITACION)) {
			return aprobarCorreccionEspecializadoNuevo(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.NEGAR_DIGITACION)) {
			return negarCorreccionDigitacion(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.APROBAR_MICROFILMACION)) {
			return aprobarMicrofilmacion(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.NEGAR_MICROFILMACION)) {
			return negarMicrofilmacion(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.EDITAR_FOLIO)) {
			return editarFolio(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.EDITAR_FOLIO_ANOTACION)) {
			return editarFolioAnotacion(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.EDITAR_FOLIOS)) {
			return editarFolios(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.DELEGAR_CASO)) {
			return delegarCorreccion(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.DEVOLVER_CASO)) {
			return devolverCorreccion(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.REDIRECCIONAR_CASO)) {
			return redireccionarCorreccion(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.IMPRIMIR)) {
			return imprimirCertificados(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.IMPRIMIR_INDIVIDUAL)) {
			return imprimirCertificadosIndividual(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.AVANZAR_IMPRIMIR)) {
			return avanzarImprimir(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.NOTIFICAR_CIUDADANO)) {
			return notificarCiudadano(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.APROBAR_MAYOR_VALOR)) {
            return aprobarMayorValor(evento);
        } else if (evento.getTipoEvento().equals(EvnCorreccion.CONSULTAR_SEG_ENG_EXISTENTES)) {
			return consultarSegEngExistentes(evento);
		} else if(evento.getTipoEvento().equals(EvnCorreccion.CONSULTAR_ULTIMOS_PROPIETARIOS)) {
			return consultarUltimosPropietarios(evento);
		} else if(evento.getTipoEvento().equals(EvnEnglobe.AGREGAR_CIUDADANO_ANOTACION)) {
			return validarCiudadanos(evento);
		} else if (evento.getTipoEvento().equals(EvnCorreccion.VALIDAR_MATRICULA_MIG)) {
			return validarMatriculaMig(evento);
		} 
		else if(evento.getTipoEvento().equals(EvnCorreccion.VALIDAR_RANGO_MATRICULAS_EX)){
		  return validarRangoMatriculaEx(evento);	
		}
		else if( EvnCorreccionFolioPadresHijos.FOLIOEDIT_CONVERTIR_CANCELADOR_A_ESTANDAR.equals( local_TipoEvento ) ) {
           return null;
        }
        else if(evento.getTipoEvento().equals(AWModificarFolio.CAMBIAR_ANOTACIONES_CORRECCION)){
			return cambiarAnotacionesCorreccion(evento);
		}
        else if( EvnCorreccion.ANOTACIONES_ASOCIADAS.equals( local_TipoEvento ) ) {
           return anotacionesAsociadas( evento );
          /*
            * @author : CTORRES
            * @change : se agregan dondiciones para las acciones que tienen que ver con correccion de testamento
            * Caso Mantis : 12291
            * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
            */
        }else if (evento.getTipoEvento().equals(EvnCorreccion.VALIDAR_TURNO_TESTAMENTO)) {
			return validarTurnoTestamento(evento);
	}else if (evento.getTipoEvento().equals(EvnCorreccion.TOMAR_TURNO_TESTAMENTO)) {
			return tomarTurnoTestamento(evento);
        }else if(evento.getTipoEvento().equals(EvnModificarTestamento.GUARDAR_DATOS_TEMPORALES_TESTAMENTO))
        {
            EvnModificarTestamento evn = (EvnModificarTestamento)e;
            return guardarDatosTmpTestamento(evn);
        }else if(evento.getTipoEvento().equals(EvnModificarTestamento.CARGAR_TESTAMENTO))
        {
            return cargarTestamento(evento);
        }else if(evento.getTipoEvento().equals(EvnModificarTestamento.ELIMINAR_TESTADOR))
        {
            EvnModificarTestamento evn = (EvnModificarTestamento)e;
            return eliminarTestador(evn);
        }else if(evento.getTipoEvento().equals(EvnModificarTestamento.AGREGAR_TESTADOR))
        {
            EvnModificarTestamento evn = (EvnModificarTestamento)e;
            return eliminarTestador(evn);
        }

		return null;

	} // end-method: perform

   private EventoRespuesta
   processCorreccionSimple_LoadFolioAnotacionX( EvnCorreccion evento )
   throws EventoException {


   // unwrap ---------------------------------
   gov.sir.core.negocio.modelo.Usuario    local_ParamUsuarioSir;
   gov.sir.core.negocio.modelo.Turno      local_ParamTurno;
   gov.sir.core.negocio.modelo.Anotacion  local_ParamAnotacion;

   // get params
   local_ParamUsuarioSir = evento.getUsuarioSir();
   local_ParamTurno      = evento.getTurno();
   local_ParamAnotacion  = evento.getT1_Anotacion();
   // -----------------------------------------

   EvnRespCorreccion local_Result = null;


   // process ---------------------------------
   Anotacion local_Result_t0Anotacion = null;


   try {
      AnotacionPk local_AnotacionId;
      local_AnotacionId = new AnotacionPk();

      local_AnotacionId.idAnotacion = local_ParamAnotacion.getIdAnotacion();
      local_AnotacionId.idMatricula = local_ParamAnotacion.getIdMatricula();

      search_SelectedAnotacionId: {

         FolioPk local_FolioId;
         local_FolioId = new FolioPk();
         local_FolioId.idMatricula     = local_AnotacionId.idMatricula;

         java.util.List local_AnotacionesFolioList;

         local_AnotacionesFolioList = forseti.getAnotacionesFolio( local_FolioId, CCriterio.POR_ID_ANOTACION, local_AnotacionId.idAnotacion, 0, 1, local_ParamUsuarioSir, false  );;

         // regresa la primera que encuentre si es el caso
         Iterator local_AnotacionesFolioListIterator = local_AnotacionesFolioList.iterator();
         for( ; local_AnotacionesFolioListIterator.hasNext(); ){
            local_Result_t0Anotacion = (Anotacion)local_AnotacionesFolioListIterator.next();
            break;
         } // for


       // local_AnotacionId
      } // :search_SelectedAnotacionId

      // Puede haber una anotacion definitiva nula
      // if( null == local_Result_t0Anotacion ) {
      //    throw new ForsetiException( "No se encuentra la anotacion " + "[Folio:" +local_ParamAnotacion.getIdMatricula() + ",Anotacion:" + local_ParamAnotacion.getOrden() + "]"  );
      // }

   }
   catch( ForsetiException fe ) {

		List l;
		l = fe.getErrores();

		if (l.size() > 0) {
			NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
			throw cfe;
		}

		if (fe.getHashErrores() != null) {
			Hashtable ht = fe.getHashErrores();
			ValidacionParametrosHTException vpe = new ValidacionParametrosHTException(fe);
			throw vpe;
		}

		if (fe.getMessage() != null) {
			NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
			cfe.addError(fe.getMessage());
			throw cfe;
		}


   }
   catch( Throwable e ) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
    }

    // wrap ------------------------------------
    local_Result = new EvnRespCorreccion( EvnRespCorreccion.CORRECCIONSIMPLEMAIN_LOADFOLIOANOTACIONXOPTION_BTNSTART__EVENTRESP_OK );


    // set results
    local_Result.setT0_Anotacion( local_Result_t0Anotacion );

   // -----------------------------------------
   // send-message ---------------------------------
   return local_Result;

   // -----------------------------------------




   } // end-method: processCorreccionSimple_LoadFolioAnotacionX





	protected EventoRespuesta
    foliosPadreHijo_addFolioHijo( EvnCorreccion evento )
    throws EventoException {

    // el evento debe ser de tipo EvnCorreccionFoliosPadreHijo
    // TODO: check;

    EvnCorreccionFolioPadresHijos thisEvent = (EvnCorreccionFolioPadresHijos)evento;

    gov.sir.core.negocio.modelo.Folio   param_Folio;
    gov.sir.core.negocio.modelo.Usuario param_UsuarioSIR;

    // datos basicos para procesamiento
    param_Folio      = thisEvent.getFolio();
    param_UsuarioSIR = thisEvent.getUsuarioSIR();

    if( ( null == param_Folio )
      ||( null == param_Folio.getIdMatricula() ) ) {

      throw new EventoException( ":Debe especificarse un folio con un id-matricula" );
    }



    // id-folios sobre los cuales se va a eliminar la relacion
    gov.sir.core.negocio.modelo.FolioPk source_FolioID = null;
    gov.sir.core.negocio.modelo.FolioPk target_FolioID = null;

    try {

        // parametros del evento para procesar la existencia de folios:
        gov.sir.core.negocio.modelo.Anotacion source = thisEvent.getSource();
        gov.sir.core.negocio.modelo.Anotacion target = thisEvent.getTarget();


        // 1. ver que ambos folios existan

        source_FolioID = new gov.sir.core.negocio.modelo.FolioPk();
        target_FolioID = new gov.sir.core.negocio.modelo.FolioPk();

        gov.sir.core.negocio.modelo.Folio source_Folio;
        gov.sir.core.negocio.modelo.Folio target_Folio;

        source_FolioID.idMatricula     = source.getIdMatricula();
        target_FolioID.idMatricula     = target.getIdMatricula();

        source_Folio = forseti.getFolioByIDSinAnotaciones( source_FolioID, param_UsuarioSIR );
        target_Folio = forseti.getFolioByIDSinAnotaciones( target_FolioID, param_UsuarioSIR );

        if( null == source_Folio ) {
            throw new ForsetiException( "El folio origen " +  source_FolioID.idMatricula + "no existe" );
        }
        if( null == target_Folio ) {
            throw new ForsetiException( "El folio destino " +  target_FolioID.idMatricula + "no existe" );
        }


        // anotacion1 = target
        // anotacion  = source

        // solo se colectan los parametros para poder hacer el llamado

        // hijo : source_Folio // param
        // padre: target_Folio // this

    }
    catch( ForsetiException e ) {
        throw new CorreccionFolioEditPadresHijosInvalidParameterException( e.getMessage(), e );
    }
    catch (Throwable e) {
       ExceptionPrinter printer = new ExceptionPrinter(e);
       Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
       throw new EventoException(e.getMessage(), e);
    }

    gov.sir.core.negocio.modelo.Anotacion source_FolioAnotacion = null;
    gov.sir.core.negocio.modelo.Anotacion target_FolioAnotacion = null;

    // no se hace busqueda; todo llega como parametro
    // se debe tener en cuenta que el id que llega no es el verdadero id, sino el orden;

    try {

        // parametros del evento para procesar la existencia de folios:
        gov.sir.core.negocio.modelo.Anotacion source = thisEvent.getSource();
        gov.sir.core.negocio.modelo.Anotacion target = thisEvent.getTarget();

        source_FolioAnotacion = forseti.getAnotacionByOrden( source_FolioID, source.getIdAnotacion(), param_UsuarioSIR );
        target_FolioAnotacion = forseti.getAnotacionByOrden( target_FolioID, target.getIdAnotacion(), param_UsuarioSIR );


        if( null == source_FolioAnotacion ) {
            throw new ForsetiException( "La anotacion " +  source.getIdAnotacion() + " en el folio " + source_FolioID.idMatricula + "no existe" );
        }
        if( null == target_FolioAnotacion ) {
            throw new ForsetiException( "La anotacion " +  target.getIdAnotacion() + " en el folio " + target_FolioID.idMatricula + "no existe" );
        }


    }
    catch( ForsetiException e ) {
        throw new CorreccionFolioEditPadresHijosInvalidParameterException( e.getMessage(), e );
    }
    catch (Throwable e) {
       ExceptionPrinter printer = new ExceptionPrinter(e);
       Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
       throw new EventoException(e.getMessage(), e);
    }


    FolioDerivado bindBridge = null;

    bindBridge = new FolioDerivado();
    bindBridge.setIdAnotacion(       source_FolioAnotacion.getIdAnotacion() );
    bindBridge.setIdMatricula(       source_FolioAnotacion.getIdMatricula() );

    bindBridge.setIdAnotacion1(      target_FolioAnotacion.getIdAnotacion() );
    bindBridge.setIdMatricula1(      target_FolioAnotacion.getIdMatricula() );


    try {


        // hijo : source_Folio
        // padre: target_Folio

        gov.sir.core.negocio.modelo.AnotacionPk source_Folio_AnotacionID;
        gov.sir.core.negocio.modelo.AnotacionPk target_Folio_AnotacionID;

        source_Folio_AnotacionID = new gov.sir.core.negocio.modelo.AnotacionPk();
        target_Folio_AnotacionID = new gov.sir.core.negocio.modelo.AnotacionPk();

        source_Folio_AnotacionID.idAnotacion     = bindBridge.getIdAnotacion();
        source_Folio_AnotacionID.idMatricula     = bindBridge.getIdMatricula();

        target_Folio_AnotacionID.idAnotacion     = bindBridge.getIdAnotacion1();
        target_Folio_AnotacionID.idMatricula     = bindBridge.getIdMatricula1();

        boolean result;
        /**
         * @author: Fernando Padilla
         * @change: Se asocia el idWorkFlow en la anotacion. No se estaba
         *          guardando.
         **/
        result
            = forseti.asociarFolios( target_Folio_AnotacionID, source_Folio_AnotacionID, param_UsuarioSIR, thisEvent.getTurno());

    }
    catch( ForsetiException e ) {
        throw new CorreccionFolioEditPadresHijosErrorsOnOpperationException( e.getMessage(), e );
    }
    catch (Throwable e) {
       ExceptionPrinter printer = new ExceptionPrinter(e);
       Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
       throw new CorreccionFolioEditPadresHijosErrorsOnOpperationException(e.getMessage(), e);
    }


//  verificar las salvedades del folio
    // por cada salvedada:
    //		si existe se actualiza, sino se inserta
    try
    {
    	forseti.updateFolioSalvedades(param_Folio, param_UsuarioSIR);
    }
    catch( ForsetiException e ) {
        throw new CorreccionFolioEditPadresHijosErrorsOnOpperationException( e.getMessage(), e );
    }
    catch (Throwable e) {
       ExceptionPrinter printer = new ExceptionPrinter(e);
       Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
       throw new EventoException(e.getMessage(), e);
    }
                        // ver que ambas anotaciones existan
                        // las anotaciones estan referenciadas por el orden

                        // 2. obtener los id's reales de la anotacion;
                        //    los id's que llegan de web son realmente los ordenes;
                        //    se debe validar que exista al anotacion con el orden especificado.


          // procesar validaciones de existencia de anotaciones
          // y permitir la busqueda de los parametros que en capa web no se pueden validar


          // source es

              // 1. ver que ambos folios existan

              // ver que ambas anotaciones existan
              // las anotaciones estan referenciadas por el orden

              // 2. obtener los id's reales de la anotacion;
              //    los id's que llegan de web son realmente los ordenes;
              //    se debe validar que exista al anotacion con el orden especificado.

          // procesar creacion de enlace entre folios


              // hacer cambio en folio hijo: se le agrega un padre

              // en este caso el target (el objeto al que se le haran los cambios) es el hijo;
              // al hijo se le agregara un padre

              // forseti.bindAnotaciones( source, target );




              // la respuesta del procesamiento se devuelve en
               EvnRespCorreccionFolioPadresHijos result = null;

               try {

                   FolioPk id = new FolioPk();
                   id.idMatricula = param_Folio.getIdMatricula();

                   param_Folio = forseti.getFolioByID( id, param_UsuarioSIR );

                   List foliosHijos = forseti.getFoliosHijos( id, param_UsuarioSIR );
                   List foliosPadre = forseti.getFoliosPadre( id, param_UsuarioSIR );

                   long numeroAnotaciones
                   = forseti.getCountAnotacionesFolio( param_Folio.getIdMatricula() );

                   result = new EvnRespCorreccionFolioPadresHijos( param_Folio,
                           EvnRespCorreccionFolioPadresHijos.
                           FOLIOEDIT_PADRESHIJOS_HIJO_ADDITEM__EVENTRESP_OK );
                   result.setFoliosHijo( foliosHijos );
                   result.setFoliosPadre( foliosPadre );
                   result.setNumeroAnotaciones( numeroAnotaciones );

                   return result;
               }
               catch (Throwable e) {
                   ExceptionPrinter printer = new ExceptionPrinter(e);
                   Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
                   throw new EventoException(e.getMessage(), e);
        }



    } //:foliosPadreHijo_addFolioHijo

    protected EventoRespuesta
    foliosPadreHijo_delFolioHijo( EvnCorreccion evento )
    throws EventoException {

        // el evento debe ser de tipo EvnCorreccionFoliosPadreHijo
        // TODO: check;

        EvnCorreccionFolioPadresHijos thisEvent = (EvnCorreccionFolioPadresHijos)evento;


        gov.sir.core.negocio.modelo.Folio   param_Folio;
        gov.sir.core.negocio.modelo.Usuario param_UsuarioSIR;

        // datos basicos para procesamiento
        param_Folio      = thisEvent.getFolio();
        param_UsuarioSIR = thisEvent.getUsuarioSIR();

        if( ( null == param_Folio )
          ||( null == param_Folio.getIdMatricula() ) ) {

          throw new EventoException( ":Debe especificarse un folio con un id-matricula" );
        }



        // id-folios sobre los cuales se va a eliminar la relacion
        gov.sir.core.negocio.modelo.FolioPk source_FolioID = null;
        gov.sir.core.negocio.modelo.FolioPk target_FolioID = null;

        try {

            // parametros del evento para procesar la existencia de folios:
            List sources = thisEvent.getSources();
            gov.sir.core.negocio.modelo.Anotacion target = thisEvent.getTarget();
            
            /**
             * Se itera sobre la lista de folios a desasociar,
             * se hacen las validaciones necesarias para luego,
             * en caso de pasar las validaciones, proceder a desasociar
             * los folios
             */
            List foliosDesasociar = new ArrayList();
            // Compatibilidad con el método cuando sólo se eliminaba una relación
            if (sources == null)
            {
            	sources = new ArrayList();
            	sources.add(thisEvent.getSource());
            }
            for (Iterator sourcesItera = sources.iterator();
            		sourcesItera.hasNext();)
            {
	            
            	gov.sir.core.negocio.modelo.Anotacion source = (Anotacion)sourcesItera.next();
	
	            // 1. ver que ambos folios existan
	
	            source_FolioID = new gov.sir.core.negocio.modelo.FolioPk();
	            target_FolioID = new gov.sir.core.negocio.modelo.FolioPk();
	
	            gov.sir.core.negocio.modelo.Folio source_Folio;
	            gov.sir.core.negocio.modelo.Folio target_Folio;
	
	            source_FolioID.idMatricula     = source.getIdMatricula();
	            target_FolioID.idMatricula     = target.getIdMatricula();
	
	
	            source_Folio = forseti.getFolioByIDSinAnotaciones( source_FolioID, param_UsuarioSIR );
	            target_Folio = forseti.getFolioByIDSinAnotaciones( target_FolioID, param_UsuarioSIR );
	
	            if( null == source_Folio ) {
	                throw new ForsetiException( "El folio origen " +  source_FolioID.idMatricula + "no existe" );
	            }
	            if( null == target_Folio ) {
	                throw new ForsetiException( "El folio destino " +  target_FolioID.idMatricula + "no existe" );
	            }
	
	            // 2. realizar la busqueda de las anotaciones
	            // por medio de las cuales los folios se relacionaban;
	
	            FolioDerivado bindBridge = null;
	
	            // hijo : source_Folio // anotacion hijo (se asume derivada)
	            // padre: target_Folio // anotacion padre (se asume generadora)
	
	            // con este metodo, se asegura la existencia de las anotaciones, por
	            // lo cual no se encesita verifcar de nuevo la existencia de las anotaciones.
	
	            bindBridge = forseti.getFolioDerivadoEnlace( target_FolioID, source_FolioID, param_UsuarioSIR );
	
	            if( null == bindBridge ) {
	                throw new ForsetiException( "No se encontro relacion entre los folios "
	                                          + "(" +  source_FolioID.idMatricula +"," +  target_FolioID.idMatricula + ")"
	                                          + " "
	                                          + "(padre,hijo)" );
	            }
	
	            gov.sir.core.negocio.modelo.AnotacionPk source_Folio_AnotacionID;
	            gov.sir.core.negocio.modelo.AnotacionPk target_Folio_AnotacionID;
	
	            source_Folio_AnotacionID = new gov.sir.core.negocio.modelo.AnotacionPk();
	            target_Folio_AnotacionID = new gov.sir.core.negocio.modelo.AnotacionPk();
	
	            source_Folio_AnotacionID.idAnotacion     = bindBridge.getIdAnotacion1();
	            source_Folio_AnotacionID.idMatricula     = bindBridge.getIdMatricula1();
	
	            target_Folio_AnotacionID.idAnotacion     = bindBridge.getIdAnotacion();
	            target_Folio_AnotacionID.idMatricula     = bindBridge.getIdMatricula();
	
	            
	            foliosDesasociar.add(new AnotacionPk[] {target_Folio_AnotacionID, source_Folio_AnotacionID});
	

            }
            
            boolean result = false;
            for (Iterator foliosDesItera = foliosDesasociar.iterator();
            		foliosDesItera.hasNext();)
            {
	            
	            AnotacionPk []anotacionesDerivadas = (AnotacionPk []) foliosDesItera.next();
	            boolean r = forseti.desasociarFolios( anotacionesDerivadas[0], anotacionesDerivadas[1], param_UsuarioSIR ) ;
	            result = result && r;
            }
        }
        catch( ForsetiException e ) {
            throw new CorreccionFolioEditPadresHijosErrorsOnOpperationException( e.getMessage(), e );
        }
        catch (Throwable e) {
           ExceptionPrinter printer = new ExceptionPrinter(e);
           Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
           throw new CorreccionFolioEditPadresHijosErrorsOnOpperationException(e.getMessage(), e);
        }


//      verificar las salvedades del folio
        // por cada salvedada:
        //		si existe se actualiza, sino se inserta
        try
        {
        	forseti.updateFolioSalvedades(param_Folio, param_UsuarioSIR);
        }
        catch( ForsetiException e ) {
            throw new CorreccionFolioEditPadresHijosErrorsOnOpperationException( e.getMessage(), e );
        }
        catch (Throwable e) {
           ExceptionPrinter printer = new ExceptionPrinter(e);
           Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
           throw new EventoException(e.getMessage(), e);
        }


        // datos para refresh en la vista:


        // la respuesta del procesamiento se devuelve en
        EvnRespCorreccionFolioPadresHijos result = null;

        try {

            FolioPk id = new FolioPk();
            id.idMatricula = param_Folio.getIdMatricula();

            param_Folio = forseti.getFolioByID( id, param_UsuarioSIR );

            List foliosHijos = forseti.getFoliosHijos( id, param_UsuarioSIR );
            List foliosPadre = forseti.getFoliosPadre( id, param_UsuarioSIR );

            long numeroAnotaciones
            = forseti.getCountAnotacionesFolio( param_Folio.getIdMatricula() );

            result = new EvnRespCorreccionFolioPadresHijos( param_Folio,
                    EvnRespCorreccionFolioPadresHijos.
                    FOLIOEDIT_PADRESHIJOS_HIJO_DELITEM__EVENTRESP_OK);
            result.setFoliosHijo( foliosHijos );
            result.setFoliosPadre( foliosPadre );
            result.setNumeroAnotaciones( numeroAnotaciones );

            return result;
        }
        catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
            throw new CorreccionFolioEditPadresHijosErrorsOnOpperationException(e.getMessage(), e);
        }



        // hacer cambio en folio hijo

        // en este caso el target (el objeto al que se le haran los cambios) es el padre;
        // al padre se le eliminara un hijo

        // forseti.unbindAnotaciones( source, target );

        // construir parametros
            // para llamar al servicio de forseti

            // crear objetos que se usaran para actualizar la vista
            // en este caso, solo se actualizan padres e hijos

            // la respuesta del procesamiento se devuelve en

    } // :foliosPadreHijo_delFolioHijo

    protected EventoRespuesta
    foliosPadreHijo_addFolioPadre( EvnCorreccion evento )
    throws EventoException {


    // el evento debe ser de tipo EvnCorreccionFoliosPadreHijo
    // TODO: check;

    EvnCorreccionFolioPadresHijos thisEvent = (EvnCorreccionFolioPadresHijos)evento;

    gov.sir.core.negocio.modelo.Folio   param_Folio;
    gov.sir.core.negocio.modelo.Usuario param_UsuarioSIR;

    // datos basicos para procesamiento
    param_Folio      = thisEvent.getFolio();
    param_UsuarioSIR = thisEvent.getUsuarioSIR();

    if( ( null == param_Folio )
      ||( null == param_Folio.getIdMatricula() ) ) {

      throw new EventoException( ":Debe especificarse un folio con un id-matricula" );
    }



    // id-folios sobre los cuales se va a eliminar la relacion
    gov.sir.core.negocio.modelo.FolioPk source_FolioID = null;
    gov.sir.core.negocio.modelo.FolioPk target_FolioID = null;

    try {

        // parametros del evento para procesar la existencia de folios:
        gov.sir.core.negocio.modelo.Anotacion source = thisEvent.getSource();
        gov.sir.core.negocio.modelo.Anotacion target = thisEvent.getTarget();


        // 1. ver que ambos folios existan

        source_FolioID = new gov.sir.core.negocio.modelo.FolioPk();
        target_FolioID = new gov.sir.core.negocio.modelo.FolioPk();

        gov.sir.core.negocio.modelo.Folio source_Folio;
        gov.sir.core.negocio.modelo.Folio target_Folio;

        source_FolioID.idMatricula     = source.getIdMatricula();
        target_FolioID.idMatricula     = target.getIdMatricula();

        source_Folio = forseti.getFolioByIDSinAnotaciones( source_FolioID, param_UsuarioSIR );
        target_Folio = forseti.getFolioByIDSinAnotaciones( target_FolioID, param_UsuarioSIR );

        if( null == source_Folio ) {
            throw new ForsetiException( "El folio origen " +  source_FolioID.idMatricula + "no existe" );
        }
        if( null == target_Folio ) {
            throw new ForsetiException( "El folio destino " +  target_FolioID.idMatricula + "no existe" );
        }


        // anotacion1 = source
        // anotacion  = target

        // solo se colectan los parametros para poder hacer el llamado

        // padre: source_Folio
        // hijo : target_Folio


    }
    catch( ForsetiException e ) {
        throw new CorreccionFolioEditPadresHijosInvalidParameterException( e.getMessage(), e );
    }
    catch (Throwable e) {
       ExceptionPrinter printer = new ExceptionPrinter(e);
       Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
       throw new EventoException(e.getMessage(), e);
    }

    gov.sir.core.negocio.modelo.Anotacion source_FolioAnotacion = null;
    gov.sir.core.negocio.modelo.Anotacion target_FolioAnotacion = null;

    // no se hace busqueda; todo llega como parametro
    // se debe tener en cuenta que el id que llega no es el verdadero id, sino el orden;

    try {

        // parametros del evento para procesar la existencia de folios:
        gov.sir.core.negocio.modelo.Anotacion source = thisEvent.getSource();
        gov.sir.core.negocio.modelo.Anotacion target = thisEvent.getTarget();

        source_FolioAnotacion = forseti.getAnotacionByOrden( source_FolioID, source.getIdAnotacion(), param_UsuarioSIR );
        target_FolioAnotacion = forseti.getAnotacionByOrden( target_FolioID, target.getIdAnotacion(), param_UsuarioSIR );


        if( null == source_FolioAnotacion ) {
            throw new ForsetiException( "La anotacion " +  source.getIdAnotacion() + " en el folio " + source_FolioID.idMatricula + "no existe" );
        }
        if( null == target_FolioAnotacion ) {
            throw new ForsetiException( "La anotacion " +  target.getIdAnotacion() + " en el folio " + target_FolioID.idMatricula + "no existe" );
        }


    }
    catch( ForsetiException e ) {
        throw new CorreccionFolioEditPadresHijosInvalidParameterException( e.getMessage(), e );
    }
    catch (Throwable e) {
       ExceptionPrinter printer = new ExceptionPrinter(e);
       Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
       throw new EventoException(e.getMessage(), e);
    }


    FolioDerivado bindBridge = null;

    bindBridge = new FolioDerivado();
    bindBridge.setIdAnotacion1(     source_FolioAnotacion.getIdAnotacion() );
    bindBridge.setIdMatricula1(     source_FolioAnotacion.getIdMatricula() );

    bindBridge.setIdAnotacion(      target_FolioAnotacion.getIdAnotacion() );
    bindBridge.setIdMatricula(      target_FolioAnotacion.getIdMatricula() );


    try {


        // padre: source_Folio
        // hijo : target_Folio

        gov.sir.core.negocio.modelo.AnotacionPk source_Folio_AnotacionID;
        gov.sir.core.negocio.modelo.AnotacionPk target_Folio_AnotacionID;

        source_Folio_AnotacionID = new gov.sir.core.negocio.modelo.AnotacionPk();
        target_Folio_AnotacionID = new gov.sir.core.negocio.modelo.AnotacionPk();

        source_Folio_AnotacionID.idAnotacion     = bindBridge.getIdAnotacion1();
        source_Folio_AnotacionID.idMatricula     = bindBridge.getIdMatricula1();

        target_Folio_AnotacionID.idAnotacion     = bindBridge.getIdAnotacion();
        target_Folio_AnotacionID.idMatricula     = bindBridge.getIdMatricula();

        boolean result;
        /**
         * @author: Fernando Padilla
         * @change: Se asocia el idWorkFlow en la anotacion. No se estaba
         *          guardando.
         **/
        result
            = forseti.asociarFolios( source_Folio_AnotacionID, target_Folio_AnotacionID, param_UsuarioSIR,thisEvent.getTurno());

    }
    catch( ForsetiException e ) {
        throw new CorreccionFolioEditPadresHijosErrorsOnOpperationException( e.getMessage(), e );
    }
    catch (Throwable e) {
       ExceptionPrinter printer = new ExceptionPrinter(e);
       Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
       throw new EventoException(e.getMessage(), e);
    }
    
    
    // verificar las salvedades del folio
    // por cada salvedada:
    //		si existe se actualiza, sino se inserta
    try
    {
    	forseti.updateFolioSalvedades(param_Folio, param_UsuarioSIR);
    }
    catch( ForsetiException e ) {
        throw new CorreccionFolioEditPadresHijosErrorsOnOpperationException( e.getMessage(), e );
    }
    catch (Throwable e) {
       ExceptionPrinter printer = new ExceptionPrinter(e);
       Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
       throw new EventoException(e.getMessage(), e);
    }


                        // ver que ambas anotaciones existan
                        // las anotaciones estan referenciadas por el orden

                        // 2. obtener los id's reales de la anotacion;
                        //    los id's que llegan de web son realmente los ordenes;
                        //    se debe validar que exista al anotacion con el orden especificado.


          // procesar validaciones de existencia de anotaciones
          // y permitir la busqueda de los parametros que en capa web no se pueden validar


          // source es

              // 1. ver que ambos folios existan

              // ver que ambas anotaciones existan
              // las anotaciones estan referenciadas por el orden

              // 2. obtener los id's reales de la anotacion;
              //    los id's que llegan de web son realmente los ordenes;
              //    se debe validar que exista al anotacion con el orden especificado.

          // procesar creacion de enlace entre folios


              // hacer cambio en folio hijo: se le agrega un padre

              // en este caso el target (el objeto al que se le haran los cambios) es el hijo;
              // al hijo se le agregara un padre

              // forseti.bindAnotaciones( source, target );




              // la respuesta del procesamiento se devuelve en
               EvnRespCorreccionFolioPadresHijos result = null;

               try {

                   FolioPk id = new FolioPk();
                   id.idMatricula = param_Folio.getIdMatricula();

                   param_Folio = forseti.getFolioByID( id, param_UsuarioSIR );

                   List foliosHijos = forseti.getFoliosHijos( id, param_UsuarioSIR );
                   List foliosPadre = forseti.getFoliosPadre( id, param_UsuarioSIR );

                   long numeroAnotaciones
                   = forseti.getCountAnotacionesFolio( param_Folio.getIdMatricula() );

                   result = new EvnRespCorreccionFolioPadresHijos( param_Folio,
                           EvnRespCorreccionFolioPadresHijos.
                           FOLIOEDIT_PADRESHIJOS_PADRE_ADDITEM__EVENTRESP_OK );
                   result.setFoliosHijo( foliosHijos );
                   result.setFoliosPadre( foliosPadre );
                   result.setNumeroAnotaciones( numeroAnotaciones );

                   return result;
               }
               catch (Throwable e) {
                   ExceptionPrinter printer = new ExceptionPrinter(e);
                   Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
                   throw new EventoException(e.getMessage(), e);
        }




    } // :foliosPadreHijo_addFolioPadre

    protected EventoRespuesta
    foliosPadreHijo_delFolioPadre( EvnCorreccion evento )
    throws EventoException {

        // el evento debe ser de tipo EvnCorreccionFoliosPadreHijo
        // TODO: check;

        EvnCorreccionFolioPadresHijos thisEvent = (EvnCorreccionFolioPadresHijos)evento;

        gov.sir.core.negocio.modelo.Folio   param_Folio;
        gov.sir.core.negocio.modelo.Usuario param_UsuarioSIR;

        // datos basicos para procesamiento
        param_Folio      = thisEvent.getFolio();
        param_UsuarioSIR = thisEvent.getUsuarioSIR();

        if( ( null == param_Folio )
          ||( null == param_Folio.getIdMatricula() ) ) {

          throw new EventoException( ":Debe especificarse un folio con un id-matricula" );
        }



        // id-folios sobre los cuales se va a eliminar la relacion
        gov.sir.core.negocio.modelo.FolioPk source_FolioID = null;
        gov.sir.core.negocio.modelo.FolioPk target_FolioID = null;

        try {

            // parametros del evento para procesar la existencia de folios:
        	List sources = thisEvent.getSources();
            gov.sir.core.negocio.modelo.Anotacion target = thisEvent.getTarget();


            /**
             * Se itera sobre la lista de folios a desasociar,
             * se hacen las validaciones necesarias para luego,
             * en caso de pasar las validaciones, proceder a desasociar
             * los folios
             */
            List foliosDesasociar = new ArrayList();
            // Compatibilidad con el método cuando sólo se eliminaba una relación
            if (sources == null)
            {
            	sources = new ArrayList();
            	sources.add(thisEvent.getSource());
            }
            for (Iterator sourcesItera = sources.iterator();
            		sourcesItera.hasNext();)
            {
            	
            	gov.sir.core.negocio.modelo.Anotacion source = (Anotacion)sourcesItera.next();
            	
            	// 1. ver que ambos folios existan

                source_FolioID = new gov.sir.core.negocio.modelo.FolioPk();
                target_FolioID = new gov.sir.core.negocio.modelo.FolioPk();

                gov.sir.core.negocio.modelo.Folio source_Folio;
                gov.sir.core.negocio.modelo.Folio target_Folio;

                source_FolioID.idMatricula     = source.getIdMatricula();
                target_FolioID.idMatricula     = target.getIdMatricula();

                source_Folio = forseti.getFolioByIDSinAnotaciones( source_FolioID, param_UsuarioSIR );
                target_Folio = forseti.getFolioByIDSinAnotaciones( target_FolioID, param_UsuarioSIR );

                if( null == source_Folio ) {
                    throw new ForsetiException( "El folio origen " +  source_FolioID.idMatricula + "no existe" );
                }
                if( null == target_Folio ) {
                    throw new ForsetiException( "El folio destino " +  target_FolioID.idMatricula + "no existe" );
                }

                // 2. realizar la busqueda de las anotaciones
                // por medio de las cuales los folios se relacionaban;

                FolioDerivado bindBridge = null;

                // padre: source_Folio
                // hijo : target_Folio

                // con este metodo, se asegura la existencia de las anotaciones, por
                // lo cual no se encesita verifcar de nuevo la existencia de las anotaciones.

                bindBridge = forseti.getFolioDerivadoEnlace( source_FolioID, target_FolioID, param_UsuarioSIR );

                if( null == bindBridge ) {
                    throw new ForsetiException( "No se encontro relacion entre los folios "
                                              + "(" +  source_FolioID.idMatricula +"," +  target_FolioID.idMatricula + ")"
                                              + " "
                                              + "(padre,hijo)" );
                }

                gov.sir.core.negocio.modelo.AnotacionPk source_Folio_AnotacionID;
                gov.sir.core.negocio.modelo.AnotacionPk target_Folio_AnotacionID;

                source_Folio_AnotacionID = new gov.sir.core.negocio.modelo.AnotacionPk();
                target_Folio_AnotacionID = new gov.sir.core.negocio.modelo.AnotacionPk();

                source_Folio_AnotacionID.idAnotacion     = bindBridge.getIdAnotacion();
                source_Folio_AnotacionID.idMatricula     = bindBridge.getIdMatricula();

                target_Folio_AnotacionID.idAnotacion     = bindBridge.getIdAnotacion1();
                target_Folio_AnotacionID.idMatricula     = bindBridge.getIdMatricula1();
                
	            foliosDesasociar.add(new AnotacionPk[] {source_Folio_AnotacionID, target_Folio_AnotacionID});

            }
            

            boolean result = false;
            for (Iterator foliosDesItera = foliosDesasociar.iterator();
            		foliosDesItera.hasNext();)
            {
	            
	            AnotacionPk []anotacionesDerivadas = (AnotacionPk []) foliosDesItera.next();
	            
	            result = forseti.desasociarFolios( anotacionesDerivadas[0], anotacionesDerivadas[1], param_UsuarioSIR );
            }

        }
        catch( ForsetiException e ) {
            throw new EventoException( e.getMessage(), e );
        }
        catch (Throwable e) {
           ExceptionPrinter printer = new ExceptionPrinter(e);
           Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
           throw new EventoException(e.getMessage(), e);
        }

//      verificar las salvedades del folio
        // por cada salvedada:
        //		si existe se actualiza, sino se inserta
        try
        {
        	forseti.updateFolioSalvedades(param_Folio, param_UsuarioSIR);
        }
        catch( ForsetiException e ) {
            throw new CorreccionFolioEditPadresHijosErrorsOnOpperationException( e.getMessage(), e );
        }
        catch (Throwable e) {
           ExceptionPrinter printer = new ExceptionPrinter(e);
           Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
           throw new EventoException(e.getMessage(), e);
        }

        // datos para refresh en la vista:


        // la respuesta del procesamiento se devuelve en
        EvnRespCorreccionFolioPadresHijos result = null;

        try {

            FolioPk id = new FolioPk();
            id.idMatricula = param_Folio.getIdMatricula();

            param_Folio = forseti.getFolioByID( id, param_UsuarioSIR );

            List foliosHijos = forseti.getFoliosHijos( id, param_UsuarioSIR );
            List foliosPadre = forseti.getFoliosPadre( id, param_UsuarioSIR );

            long numeroAnotaciones
            = forseti.getCountAnotacionesFolio( param_Folio.getIdMatricula() );

            result = new EvnRespCorreccionFolioPadresHijos( param_Folio,
                    EvnRespCorreccionFolioPadresHijos.
                    FOLIOEDIT_PADRESHIJOS_PADRE_DELITEM__EVENTRESP_OK);
            result.setFoliosHijo( foliosHijos );
            result.setFoliosPadre( foliosPadre );
            result.setNumeroAnotaciones( numeroAnotaciones );

            return result;
        }
        catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
            throw new EventoException(e.getMessage(), e);
        }




        // obtener los datos del enlace
        // (probar si existe alguna anotacion dentro de thisFolio
        // que referencie al folio recibido como parametro dentro de los folios padre)
        // completar:
        // procesar eliminacion de enlace entre folios
        // crear objetos que se usaran para actualizar la vista
        // en este caso, solo se actualizan padres e hijos





    } // :foliosPadreHijo_delFolioPadre






    // DEPURADO Enero 17 2006
	/**
     * @param evento
     * @return
     * Método para delegar un turno a la fase de pagos de mayor valor.
     */
    private EventoRespuesta aprobarMayorValor(EvnCorreccion evento) throws EventoException{

        //Obtener parámetros desde el evento.
        Turno turno=evento.getTurno();
        LiquidacionTurnoCorreccion liq= evento.getLiquidacion();
        Fase fase= evento.getFase();

        //Obtener el servicio de hermod.
        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
        } catch (ServiceLocatorException e) {
            ExceptionPrinter ep=new ExceptionPrinter(e);
            Log.getInstance().error(ANCorreccion.class,"No se encontró el servicio hermod:"+ep.toString());
        }

        Hashtable parametros=new Hashtable();

        //inicializar tabla hashing
        parametros.put("RESULT", CRespuesta.PAGO_MAYOR_VALOR);
        parametros.put("USUARIO_SIR",evento.getUsuarioSIR().getUsername());



        //3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try
		{
		   Hashtable tablaAvance = new Hashtable(2);
		   tablaAvance.put(Processor.RESULT, CRespuesta.PAGO_MAYOR_VALOR);
		   tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
		   hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}



        //Llamado a los servicios de hermod para realizar el avance del turno.
        try {

            Solicitud sol= turno.getSolicitud();
            //se añade la liquidacion al turno
            hermod.addLiquidacionToSolicitud(sol, liq);
            //se avanza el turno
            hermod.avanzarTurnoNuevoPush(turno,fase,parametros,evento.getUsuarioSIR());


        } catch (Throwable e1) {
            ExceptionPrinter ep=new ExceptionPrinter(e1);
            Log.getInstance().error(ANCorreccion.class,"No fue posible avanzar el turno:"+ep.toString());
            throw new EventoException("No fue posible avanzar el turno",e1);
        }

        return null;

    }


    /**
	 * Método que crea una instancia del proceso de correcciones.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EvnRespCorreccion crearSolicitud(EvnCorreccion evento) throws EventoException {

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		Solicitud solicitud = null;
		Pago pago = null;
		Turno turno = null;
		LiquidacionTurnoCorreccion liquidacion = new LiquidacionTurnoCorreccion();

		try {
			//TFS 4394: El comentario de la solicitud de correccion
			//no puede exceder de 255 caracteres
			if(evento.getSolicitudCorreccion().getComentario()!=null &&
					evento.getSolicitudCorreccion().getComentario().length() > 
						Integer.parseInt(CSolicitud.TAMANIO_COMENTARIO_CORRECCIONES)){
				throw new LiquidacionNoEfectuadaException("El comentario de la solicitud no puede exceder los 255 caracteres");
			}
                        if(evento.getTurnoTestamento()!=null){
                            
                            Turno turnoTestamento =hermod.getTurnobyWF(evento.getTurnoTestamento().getIdWorkflow());
                            evento.getSolicitudCorreccion().setTurnoAnterior(turnoTestamento);
                        }
			
                        solicitud = hermod.crearSolicitud(evento.getSolicitudCorreccion());
                       
			if (solicitud == null) {
				throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
			}

			List solFolio = solicitud.getSolicitudFolios();
			List matriculas = new Vector();
			Iterator itSolFolio = solFolio.iterator();

			while (itSolFolio.hasNext()) {
				SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
				matriculas.add(sol.getFolio().getIdMatricula());
			}

			forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud), matriculas);
			liquidacion.setSolicitud(solicitud);
			liquidacion.setUsuario(evento.getUsuarioSIR());
			LiquidacionTurnoCorreccion auxLiquidacion = (LiquidacionTurnoCorreccion) hermod.liquidar(liquidacion);

			DocPagoEfectivo docPago = new DocPagoEfectivo(liquidacion.getValor());

			AplicacionPago appEfectivo = new AplicacionPago();
			appEfectivo.setIdLiquidacion(liquidacion.getIdLiquidacion());
			appEfectivo.setIdSolicitud(solicitud.getIdSolicitud());
			appEfectivo.setValorAplicado(liquidacion.getValor());
			appEfectivo.setDocumentoPago(docPago);

			pago = new Pago(auxLiquidacion, null);
			pago.addAplicacionPago(appEfectivo);
			pago.setIdLiquidacion(liquidacion.getIdLiquidacion());
			pago.setIdSolicitud(solicitud.getIdSolicitud());
			pago.setLiquidacion(auxLiquidacion);
			pago.setUsuario(evento.getUsuarioSIR());

		} catch (HermodException e) {
			e.printStackTrace(System.out);
                        // para que no aparezca mensaje en otra ventana
                        throw new LiquidacionNoEfectuadaException( e.getMessage(), e );
			// throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		try {
			if (validarPago(pago, hermod.getRangoAceptacionPago(solicitud.getProceso().getNombre()))) {
				pago = hermod.validarPago(pago);
			}
		} catch (HermodException e) {
			throw new PagoInvalidoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		try {
			Estacion estacion = evento.getEstacion();
			turno = hermod.procesarPago(pago, estacion.getEstacionId(), "imp", pago.getUsuario(), null, false);

                        /**
                        * @author Fernando Padilla Velez
                        * @change Acta - Requerimiento No 178 - Integración Gestión Documental y SIR,
                        *         Se agrega esta fragmento de codigo para la publicacion del mensaje del turno creado.
                        */
                        /**
                         * @author Fernando Padilla Velez
                         * @change 6760: Acta - Requerimiento No 191 - Pantalla Administrativa SGD,
                         *         Se realizó refactoring al proceso y ya no son necesarias.
                         */
                        SGD sgd = new SGD(turno, pago.getUsuario().getUsername());
                        sgd.enviarTurnoCorreccion();
			
			if(evento.getTurnosFolioMig()!=null && evento.getTurnosFolioMig().size()>0){
				List turnoFolioMig = evento.getTurnosFolioMig();
				List turnosFoliosMig = new ArrayList();
				Iterator it = turnoFolioMig.iterator();
				while (it.hasNext()){
					String fol = (String)it.next();
					TurnoFolioMig tfm = new TurnoFolioMig();
					tfm.setIdFolio(fol);
					tfm.setIdTurno("C"+turno.getAnio()+"-"+turno.getIdTurno());
					tfm.setIdProceso(turno.getIdProceso());
					tfm.setIdCirculo(turno.getIdCirculo());
					tfm.setAnulado(false);
					tfm.setCreadoSir(true);
					turnosFoliosMig.add(tfm);
				}
				
				hermod.addTurnosFolioMigToTurno(turno, turnosFoliosMig);	
			}			


			/*
			pago.getLiquidacion().getSolicitud().setTurno(turno);

			//List liquidaciones = turno.getSolicitud().getLiquidaciones();
			String numRecibo = ((Liquidacion) liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();
			pago.setNumRecibo(numRecibo);
			String fechaImp = this.getFechaImpresion();
			ImprimibleRecibo iRec = new ImprimibleRecibo(pago,fechaImp);
			Bundle bundle = new Bundle(iRec);
			printJobs.enviar(evento.getUID(), bundle);
			*/



				List liquidaciones = turno.getSolicitud().getLiquidaciones();
				//String numRecibo = ((Liquidacion)liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();

                //EstacionRecibo.ID estacionReciboID = new EstacionRecibo.ID();
                //estacionReciboID.idEstacion = estacion.getEstacionId();
                //String numRecibo = String.valueOf(hermod.getNextNumeroReciboSinAvanzar(estacionReciboID));
				//pago.setNumRecibo(numRecibo);

                //Pago.ID pagoID = new Pago.ID();
                //pagoID.idLiquidacion = pago.getIdLiquidacion();
                //pagoID.idSolicitud = pago.getIdSolicitud();
                //hermod.setNumeroReciboPago(pagoID, numRecibo);

				pago.getLiquidacion().getSolicitud().setTurno(turno);
				Circulo circulo = evento.getCirculo();
				String fechaImpresion =  this.getFechaImpresion();


                                // Bug 3526

				ImprimibleRecibo impRec;
                                impRec = new ImprimibleRecibo(pago,circulo,fechaImpresion, CTipoImprimible.RECIBO);
                                impRec.setImprimirFirma_SolicitudCorreccion_Enabled( true );




                                // Bug 3479
                                // :: ProcId: Correcciones
                                // (usuario que genera solicitud)
                                // jxpath-search:./solicitud/usuario/nombre

                                ImprimibleRecibo imprimible;
                                imprimible = impRec;


                                // realizar busqueda de usuario
                                String local_UsuarioGeneraRecibo = "";


                                local_SearchImpl_jx: {

                                          // :: local variables ----------------------------------------------
                                          Turno local_Turno;
                                          JXPathContext local_TurnoSearchContext;

                                          // // List< TurnoHistoria >
                                          List local_TurnoHistoria;
                                          Iterator local_TurnoSearchContextIterator;

                                          // final String SEARCHED__FASE_CORRECCIONES_CORRECCIONSIMPLE = "COR_CORRECCION_SIMPLE";

                                          Object local_FoundedValue; // elemento encontrado

                                          // initialize context & variables
                                          local_Turno = turno;
                                          //local_TurnoHistoria = local_Turno.getHistorials(); //
                                          local_TurnoSearchContext = jx_GetContext( local_Turno );

                                          // evaluateNextRule = true;
                                          // local_TipoCorreccion = null;

                                          // declare jxpath variables
                                          // local_TurnoSearchContext.getVariables().declareVariable( "local_FaseId"            , "" );

                                          // -----------------------------------------------------------------
                                          // simple queries
                                          // apply first

                                          // ruleA: search-rule:
                                          //  buscar en el historial
                                          //  el usuario que realizo la correccion simple en ultimo lugar
                                          // Bug 5223 / Correcciones
                                          String searchRule = "solicitud/usuario/idUsuario";

                                          // local_TurnoSearchContext.setValue( "$local_FaseId"     , SEARCHED__FASE_CORRECCIONES_CORRECCIONSIMPLE  );



                                          local_FoundedValue = local_TurnoSearchContext.getValue( searchRule );
                                          if( null != local_FoundedValue) {
                                            local_UsuarioGeneraRecibo = print_FootUtils_BuildUserName( (Long)local_FoundedValue );
                                          } // if

                                } // :searchImpl_jx


                                imprimible.setUsuarioGeneraRecibo( local_UsuarioGeneraRecibo );


								String uid =evento.getUID();

								//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
								int maxIntentos;
								int espera;

								//INGRESO DE INTENTOS DE IMPRESION
								try {

									String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
									if (intentosImpresion != null) {
										Integer intentos = new Integer(intentosImpresion);
										maxIntentos = intentos.intValue();
									} else {
										Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
										maxIntentos = intentosDefault.intValue();
									}

									//INGRESO TIEMPO DE ESPERA IMPRESION
									String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
									if (intentosImpresion != null) {
										Integer esperaInt = new Integer(esperaImpresion);
										espera = esperaInt.intValue();
									} else {
										Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
										espera = esperaDefault.intValue();
									}
								} catch (Throwable t) {
									Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
									maxIntentos = intentosDefault.intValue();

									Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
									espera = esperaDefault.intValue();

								}



								// Bug 3528
								// Enviar 2 veces a imprimir
								int nTimes = 2;
								nTimes=evento.getNumeroCopias();
                                Bundle bundle = new Bundle( impRec,  nTimes);


                                print2Copies:

                                try {
                                       //se manda a imprimir el recibo por el identificador unico de usuario
                                       printJobs.enviar(uid, bundle, maxIntentos, espera);
                                }
                                catch (Throwable t) {
                                       t.printStackTrace();
                                } // :try



                                 // :print2Copies








		} catch (HermodException e1) {
			throw new PagoNoProcesadoException(e1.getMessage(), e1);
		} catch (PrintJobsException e2) {
			if (turno != null) {
				throw new ErrorImpresionException("No se pudo imprimir el recibo:" + e2.getMessage(), turno.getIdWorkflow());
			}
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespCorreccion eventoRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.CREAR_SOLICITUD);

		return eventoRespuesta;
	}

	/**
	 * Método que válida que una matrícula exista en la base de datos.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta validarMatricula(EvnCorreccion evento) throws EventoException {
		String matricula = evento.getFolio().getIdMatricula();

		try {
			if (forseti.existeFolio(matricula.trim())) {
				if(forseti.trasladadoFolio(matricula.trim())) {
					throw new MatriculaInvalidaCorreccionException("El folio identificado con matrícula:" + matricula + " ha sido trasladado.");
				}
				return new EvnRespCorreccion(evento.getUsuario(), matricula, EvnRespCorreccion.VALIDAR_MATRICULA);
			}
            throw new MatriculaInvalidaCorreccionException("La matrícula " + matricula + " no existe");
		} catch (MatriculaInvalidaCorreccionException e) {
			throw e;
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"No se pudo encontrar el servicio:" + ep.toString());
			throw new MatriculaInvalidaCorreccionException("Servicio no encontrado", e);
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Error en el servicio para validar la matrícula:" + ep.toString());
			throw new MatriculaInvalidaCorreccionException("Error en el servicio para validar la matrícula", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Se produjo un error validando la matrícula:" + ep.toString());
			throw new MatriculaInvalidaCorreccionException("Se produjo un error validando la matrícula", e);
		}
	}
	
	/**
	 * Metodo que valida la existencia en base de datos de un conjunto de matriculas
	 */
	
	private EventoRespuesta validarRangoMatriculas(EvnCorreccion evento) throws EventoException
	{
		 java.util.List errores=new ArrayList();
		 java.util.List listaMatriculas=(List)evento.getFolios();
		 if(listaMatriculas!=null)
		  {
		    String matriculainicial=(String)listaMatriculas.get(0);
			String matriculafinal=(String)listaMatriculas.get(1);
		    SerialIds si = new SerialIds(matriculainicial,matriculafinal);
		    if(!si.isSerialExists())
		    {
		    	errores.add("El rango de matrículas es inválido");
				throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
		    }
		    else
		    {
		    	String ultimamatricula=null;
		      while(si.hasNext())
		      {
		    	 String matricula=si.nextValue();
		    	 try {
		 			if (forseti.existeFolio(matricula.trim())) {
		 				if(forseti.trasladadoFolio(matricula.trim())) {
		 					throw new MatriculaInvalidaCorreccionException("El folio identificado con matrícula:" + matricula + " ha sido trasladado.");
		 				}
		 				
		 			}
		 			else
		 			{		
		             throw new MatriculaInvalidaCorreccionException("La matrícula " + matricula + " no existe");
		 			}
		 		} catch (MatriculaInvalidaCorreccionException e) {
		 			throw e;
		 		} catch (ServiceLocatorException e) {
		 			ExceptionPrinter ep = new ExceptionPrinter(e);
		 			Log.getInstance().error(ANCorreccion.class,"No se pudo encontrar el servicio:" + ep.toString());
		 			throw new MatriculaInvalidaCorreccionException("Servicio no encontrado", e);
		 		} catch (ForsetiException e) {
		 			ExceptionPrinter ep = new ExceptionPrinter(e);
		 			Log.getInstance().error(ANCorreccion.class,"Error en el servicio para validar la matrícula:" + ep.toString());
		 			throw new MatriculaInvalidaCorreccionException("Error en el servicio para validar la matrícula", e);
		 		} catch (Throwable e) {
		 			ExceptionPrinter ep = new ExceptionPrinter(e);
		 			Log.getInstance().error(ANCorreccion.class,"Se produjo un error validando la matrícula:" + ep.toString());
		 			throw new MatriculaInvalidaCorreccionException("Se produjo un error validando la matrícula", e);
		 		} 
		      }
		      EvnRespCorreccion evt=new EvnRespCorreccion(evento.getUsuario(),ultimamatricula, EvnRespCorreccion.VALIDAR_MATRICULA);
		      evt.setRangoFolio(listaMatriculas);
		      return evt;
		    }
		  }
		 else
		 {
			 errores.add("El rango de matrículas es inválido");
			 throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
		     
		 }
	}
	

   /**
    * valida que una matrícula exista y que no este cerrada.
    */
   private EventoRespuesta validarMatriculaEx(EvnCorreccion evento) throws EventoException {

          EventoRespuesta result;
          result = validarMatricula( evento );

          /**
          * @author      :  Julio Alcazar
          * @change      :  Revision: Validacion bloqueo por traslado.
          * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
          */
          TrasladoSIR trasladoSIR = new TrasladoSIR();
          try {
              if (trasladoSIR.isTrasladoSinConf(evento.getFolio().getIdMatricula())) {
                  throw new MatriculaInvalidaCorreccionException("El folio "+evento.getFolio().getIdMatricula()+" se encuentra bloqueado.");
              }
          } catch (GeneralSIRException ex) {
              Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
              throw new EventoException(ex.getMessage(), ex);
          }
         
          String matricula = evento.getFolio().getIdMatricula();

          //TFS 3788:Se comentarea el siguiente codigo, en la solicitud de correcciones,
          //se pueden agregar folios cerrados
          
          /*boolean buzz_ValidateCerradoFolio = isCerradoFolio( matricula );

          if( buzz_ValidateCerradoFolio ) {
             throw new MatriculaInvalidaCorreccionException("El folio identificado con matrícula:" + matricula + " esta cerrado.");
          }*/
          
          boolean buzz_ValidateInconsistenteFolio = isInconsistenteFolio( matricula );

          if( buzz_ValidateInconsistenteFolio ) {
             throw new MatriculaInvalidaCorreccionException("El folio identificado con matrícula:" + matricula + " es inconsistente.");
          }

          return result;

    }
   
   
   /**
    * valida que un rango de matriculas exista y no esten cerradas
    */
   
   private EventoRespuesta validarRangoMatriculaEx(EvnCorreccion evento) throws EventoException
   {
	   java.util.List errores=new ArrayList();
	   java.util.List listaMatriculas=(List)evento.getFolios();
	   if(listaMatriculas!=null)
	   {
		String matriculainicial=(String)listaMatriculas.get(0);
		String matriculafinal=(String)listaMatriculas.get(1);
	    SerialIds si = new SerialIds(matriculainicial,matriculafinal);
	    if(!si.isSerialExists())
	    {
	    	errores.add("El rango de matrículas es inválido");
			throw new MatriculaInvalidaConfrontacionCorrecionException(errores);
	    }
	    else
	    {
	    	//la lista de seriales esta bien hecha 
	    	 EventoRespuesta result;
	    	 result=this.validarRangoMatriculas(evento);
	    	 System.out.println("EL RANGO DE EVENTOS ES "+result.getNombre());
                 /**
                 * @author      :  Julio Alcazar
                 * @change      :  Revision: Validacion bloqueo por traslado.
                 * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                 */
                 TrasladoSIR trasladoSIR = new TrasladoSIR();
	    	 while(si.hasNext())
	    	 {
	    		 String matricula=si.nextValue();
	    		 boolean buzz_ValidateInconsistenteFolio = isInconsistenteFolio( matricula );

	             if( buzz_ValidateInconsistenteFolio ) {
	                throw new MatriculaInvalidaCorreccionException("El folio identificado con matrícula:" + matricula + " es inconsistente.");
	             }
                     /**
                     * @author      :  Julio Alcazar
                     * @change      :  Revision: Validacion bloqueo por traslado.
                     * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                     */
                     try {
                          if (trasladoSIR.isTrasladoSinConf(matricula)) {
                              throw new MatriculaInvalidaCorreccionException("El folio "+matricula+" se encuentra bloqueado.");
                          }
                      } catch (GeneralSIRException ex) {
                          Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                          throw new EventoException(ex.getMessage(), ex);
                      }
	    	 }
	    	 return result;
	    }
	   }
	   else
	   {
		   errores.add("El rango de matrículas es inválido");
		   throw new MatriculaInvalidaConfrontacionCorrecionException(errores); 
	   }
   }
   
   /**
	 * Método que válida que una matrícula exista en la base de datos.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta validarMatriculaMig(EvnCorreccion evento) throws EventoException {
		String matricula = evento.getFolio().getIdMatricula();

		try {
			Folio folio = new Folio();
			folio.setIdMatricula(matricula.trim());
			
			if (hermod.isFolioValidoSirMig(folio)) {
				return new EvnRespCorreccion(evento.getUsuario(), matricula, EvnRespCorreccion.VALIDAR_MATRICULA_MIG);
			}
			throw new MatriculaInvalidaCorreccionException("La matrícula " + matricula + " no es valida para el proceso de Migración");
		} catch (MatriculaInvalidaCorreccionException e) {
			throw e;
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"No se pudo encontrar el servicio:" + ep.toString());
			throw new MatriculaInvalidaCorreccionException("Servicio no encontrado", e);
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Error en el servicio para validar la matrícula:" + ep.toString());
			throw new MatriculaInvalidaCorreccionException("Error en el servicio para validar la matrícula", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Se produjo un error validando la matrícula:" + ep.toString());
			throw new MatriculaInvalidaCorreccionException("Se produjo un error validando la matrícula", e);
		}
	}

    /**
     * valida que una matrícula no este cerrada.
     */
    private boolean isCerradoFolio(String matricula) throws EventoException {

           boolean buzz_ValidateCerradoFolio = true;
           try {

              buzz_ValidateCerradoFolio = forseti.cerradoFolio( matricula );

           }
           catch (ServiceLocatorException e) {
                  ExceptionPrinter ep = new ExceptionPrinter(e);
                  Log.getInstance().error(ANCorreccion.class,"No se pudo encontrar el servicio:" + ep.toString());
                  throw new EventoException("Servicio no encontrado", e);
           }
           catch (ForsetiException e) {
                  ExceptionPrinter ep = new ExceptionPrinter(e);
                  Log.getInstance().error(ANCorreccion.class,"Error en el servicio para validar la matrícula:" + ep.toString());
                  throw new EventoException("Error en el servicio para validar la matrícula", e);
           }
           catch (Throwable e) {
                  ExceptionPrinter ep = new ExceptionPrinter(e);
                  Log.getInstance().error(ANCorreccion.class,"Se produjo un error validando la matrícula:" + ep.toString());
                  throw new EventoException("Se produjo un error validando la matrícula", e);

           } // try

           return buzz_ValidateCerradoFolio;

    }
    
    /**
     * valida que una matrícula no este inconsistente.
     */
    private boolean isInconsistenteFolio(String matricula) throws EventoException {

           boolean buzz_ValidateInconsistenteFolio = true;
           try {

              buzz_ValidateInconsistenteFolio = forseti.inconsistenteFolio( matricula );

           }
           catch (ServiceLocatorException e) {
                  ExceptionPrinter ep = new ExceptionPrinter(e);
                  Log.getInstance().error(ANCorreccion.class,"No se pudo encontrar el servicio:" + ep.toString());
                  throw new EventoException("Servicio no encontrado", e);
           }
           catch (ForsetiException e) {
                  ExceptionPrinter ep = new ExceptionPrinter(e);
                  Log.getInstance().error(ANCorreccion.class,"Error en el servicio para validar la matrícula:" + ep.toString());
                  throw new EventoException("Error en el servicio para validar la matrícula", e);
           }
           catch (Throwable e) {
                  ExceptionPrinter ep = new ExceptionPrinter(e);
                  Log.getInstance().error(ANCorreccion.class,"Se produjo un error validando la matrícula:" + ep.toString());
                  throw new EventoException("Se produjo un error validando la matrícula", e);

           } // try

           return buzz_ValidateInconsistenteFolio;

    }
    
    
	/**
	 * Este método valida que todas las aplicaciones de pago tengan saldo válido
	 * @param pago EL pago que se va a validar
	 * @param precision El rango de tolerancia
	 * @return boolean true si el pago es válido
	 * @throws PagoInvalidoException si el pago no es valido
	 */
	private boolean validarPago(Pago pago, double precision) throws PagoInvalidoException {
		List pagos = pago.getAplicacionPagos();
		Liquidacion liquidacion = pago.getLiquidacion();
		double valorLiquidado = liquidacion.getValor();
		double valorPagado = 0;

		Iterator it = pagos.iterator();

		while (it.hasNext()) {
			AplicacionPago apl = (AplicacionPago) it.next();
			valorPagado += apl.getValorAplicado();
		}

		if ((valorPagado + precision) < valorLiquidado) {
			throw new PagoInvalidoException("El valor a pagar no coincide con el valor Liquidado");
		}

		return true;
	}

	/**
	 * Método que permite bloquear los folios que hacen parte del turno al usuario que
	 * esta trabajando con el turno.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta tomarFolios(EvnCorreccion evento) throws EventoException {

		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}

		List solFolio = solicitud.getSolicitudFolios();
		List matriculas = new Vector();
		LlaveBloqueo llaveBloqueo = null;
		Iterator itSolFolio = solFolio.iterator();

		FolioPk fid;
		Usuario usuarioBloqueo;
		boolean folioBloqueo=false;
                // bug 3593
                String local_IdMatricula;

                StringBuffer local_ListFoliosCerradosCsvRepresentation = new StringBuffer( 1024 );
                final String SEPARATOR = ", ";
                List local_ListFoliosCerrados = new ArrayList();

		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
                        local_IdMatricula = sol.getFolio().getIdMatricula();
                        // Se lanza excepcion si esta cerrado
                        if( isCerradoFolio( local_IdMatricula ) ) {
                           local_ListFoliosCerrados.add( local_IdMatricula );
                           local_ListFoliosCerradosCsvRepresentation.append( SEPARATOR + local_IdMatricula );
                           continue;
                        } // if

			matriculas.add( local_IdMatricula );
			
			/*
			 * Modifica PQ - DM Mayo 28 id 41
			 * Se busca si el folio está bloquedao, tiene datos temporales y el usuario es dueño del bloqueo,
			 * además que la fase sea ejecutar corrección simple:
			 * Sí se da continúa el flujo sino se lanza excepcion.
			 */
			fid=new FolioPk();
			fid.idMatricula=local_IdMatricula;
			try{
				usuarioBloqueo=forseti.getBloqueoFolio(fid);
				if(usuarioBloqueo!=null){
					if(usuarioBloqueo.getIdUsuario()!=usr.getIdUsuario()){
						if(forseti.hasDatosTemporalesFolio(fid) && turno.getIdFase().equals(CFase.COR_CORRECCION_SIMPLE)){
							folioBloqueo=true;
						}
					}
				}
			}catch (Throwable e) {
				throw new EventoException(e.getMessage(),e);
			}
			if(folioBloqueo){
				throw new ANCorreccionException("El folio "+local_IdMatricula+" tiene cambios temporales realizados por el usuario "+usuarioBloqueo.getNombre()+"."+usuarioBloqueo.getApellido1());				
			}
				
			/*
			 * Fin modifica Pablo Quintana.
			 */
		}

                // raise si existen folios cerrados
                if( local_ListFoliosCerrados.size() > 0 ){
                   throw new ANCorreccionException ("Existen folios en el rango que aparecen como cerrados " + local_ListFoliosCerradosCsvRepresentation.toString() );
                } // if

		llaveBloqueo = new LlaveBloqueo();

		try {
			TurnoPk turnoID = new TurnoPk();
			turnoID.idCirculo = turno.getIdCirculo();
			turnoID.idProceso = turno.getIdProceso();
			turnoID.idTurno = turno.getIdTurno();
			turnoID.anio = turno.getAnio();

            forseti.validarPrincipioPrioridadCorreccion(turnoID);

			llaveBloqueo = forseti.delegarBloqueoFolios(turnoID, usr);
			
		} catch (ForsetiException e) {
			if(e.getHashErrores() != null)
			{
				if(!e.getHashErrores().isEmpty())
				{
					if (evento.getTipoEvento().equals(EvnCorreccion.TOMAR_FOLIO_CORRECCION)) {
						throw new BloqueoFoliosHTException(e);
					}

					if (evento.getTipoEvento().equals(EvnCorreccion.TOMAR_FOLIO_DIGITACION)) {
						throw new BloqueoFoliosDigitacionHTException(e);
					}

					if (evento.getTipoEvento().equals(EvnCorreccion.TOMAR_FOLIO_ESPECIALIZADO)) {
						throw new BloqueoFoliosEspecializadoHTException(e);
					}
				}
			}

			if (evento.getTipoEvento().equals(EvnCorreccion.CARGAR_CAMBIOS_PROPUESTOS)) {
				throw new EventoException(e.getMessage(), e);
			}
			if (evento.getTipoEvento().equals(EvnCorreccion.APROBAR)) {
				throw new EventoException(e.getMessage(), e);
			}

			throw new TomarFoliosException(e.getMessage(), e);

		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada bloqueando los folios : " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, llaveBloqueo, EvnRespCorreccion.TOMAR_FOLIO);

		return evRespuesta;
	}

	/**
	 * Método que permite consultar un folio para que sobre él se consulten o realicen los cambios
	 * que se solicitan.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta consultaFolio(EvnCorreccion evento) throws EventoException {


		Folio folio = evento.getFolio();
		gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioSIR();


                // Bug 3552: Load DefinitiveData

                Folio local_Folio_TDefinitivo = null;


		try {
			if (folio != null && folio.getIdMatricula() != null) {

				FolioPk id = new FolioPk();
				id.idMatricula = folio.getIdMatricula();

                                // cambiado para probar uso de paginador
				// folio = forseti.getFolioByID(id, u);
                                folio = forseti.getFolioByIDSinAnotaciones(id,u);


                                // Bug 3552
                                local_Folio_TDefinitivo = forseti.getFolioByIDSinAnotaciones( id );


                                // bug 3530/3560:
                                // las salvedades-folio se devielven con el metodo: getFolioByIDSinAnotaciones
                                // pasando el usuario


				boolean esMayorExtension = forseti.mayorExtensionFolio(folio.getIdMatricula());

                                // long numeroAnotaciones = forseti.getCountAnotacionesFolio(folio.getIdMatricula());
                                long numeroAnotaciones = forseti.getCountAnotacionesFolio( id , CCriterio.TODAS_LAS_ANOTACIONES, null, (Usuario)null );


				List foliosHijos = forseti.getFoliosHijos(id, u);
				List foliosPadre = forseti.getFoliosPadre(id, u);

				long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN);
				List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN, 0, (int) numeroGravamenes, u,true);

				long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
				List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR, 0, (int) numeroMedidasCautelares, u,true);

				long numeroFalsaTradicion = 0;
				List falsaTradicion = null;
				List ordenFalsaTradicion = new ArrayList();
				List anotacionesInvalidas = null;
				List ordenAnotacionesInvalidas = new ArrayList();
				List anotacionesPatrimonioFamiliar = null;
				long numanotacionesPatrimonioFamiliar = 0;
				List anotacionesAfectacionVivienda = null;
				long munanotacionesAfectacionVivienda = 0;
				List anotacionesAfectacionVivienda2 = null;
				long munanotacionesAfectacionVivienda2 = 0;
				//sir-169
				long numeroSegregaciones = 0;
				long numeroSegregacionesVacias = 0;
				List anotacionesSegregadorasVacias = null;

				//CONSULTA ANOTACIONES DE FALSA TRADICION
				numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
				falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, u, true);
				if(falsaTradicion != null){
					Iterator it = falsaTradicion.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenFalsaTradicion.add(anotTemp.getOrden());
					}
				}

				//CONSULTA ANOTACIONES INVALIDAS
				anotacionesInvalidas = forseti.getAnotacionesInvalidas(id, u);
				if(anotacionesInvalidas != null){
					Iterator it = anotacionesInvalidas.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenAnotacionesInvalidas.add(anotTemp.getOrden());
					}
				}
				
				//	CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
				numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
				anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
				
				//	CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
				munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
				anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
				
				// CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
				munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
				anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
				
				anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);
				
				//sir-169
				numeroSegregaciones = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.LOTEO, null);
				anotacionesSegregadorasVacias = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.LOTEO,0, (int)numeroSegregaciones,null,true);
				numeroSegregaciones += forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.RELOTEO, null);
				anotacionesSegregadorasVacias.addAll(forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.RELOTEO,0, (int)numeroSegregaciones,null,true));
				numeroSegregacionesVacias = (long)anotacionesSegregadorasVacias.size();
				
				List salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, u);
				List cancelaciones = forseti.getAnotacionesConCancelaciones(id, u);


                                Turno local_Turno;
                                local_Turno = evento.getTurno();

                                EvnRespCorreccion local_Result;
                                local_Result = new EvnRespCorreccion( folio, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion, ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, EvnRespCorreccion.CONSULTAR_FOLIO );
                                local_Result.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
                                local_Result.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
                                local_Result.setNumSegregacionesVacias(numeroSegregacionesVacias);

                                boolean ruleComplementacionAdicionaEnabled;
                                ruleComplementacionAdicionaEnabled = getRuleComplementacionAdicionaEnabled( local_Turno );

                                if( ruleComplementacionAdicionaEnabled ) {

                                   // se calculan los datos en los tiempos
                                   String[] tComplementacion = jx_GetFolioComplementacionComplementacion( local_Folio_TDefinitivo, folio );

                                    // segun los permisos que se tienen se decide

                                    // se fija en el evento

                                    local_Result.setRuleComplementacionAdicionaEnabled( ruleComplementacionAdicionaEnabled );

                                    local_Result.setFolioT0_complementacion_complementacion( tComplementacion[0] );
                                    local_Result.setFolioT1_complementacion_complementacion( tComplementacion[1] );
                                    local_Result.setFolioT2_complementacion_complementacion( tComplementacion[2] );

                                    filter_RuleComplementacionAdicionaEnabled_SetField( folio, JXPATH_FOLIO_COMPLEMENTACION_COMPLEMENTACION, tComplementacion[2] ) ;

                                    local_Result.setFolio( folio );


                                } // if

                                // bug 03541
                                local_Result.setT0_Folio( local_Folio_TDefinitivo );


                                // -------------------------------------------------------------------------
                                local_Result.setCargarSalvedad(evento.isCargarSalvedad());
				List foliosDerivadoHijo=null;
				if(id!=null && u !=null){
					foliosDerivadoHijo=forseti.getFoliosDerivadoHijos(id, u);
					if(foliosDerivadoHijo!=null)
						local_Result.setFoliosDerivadoHijo(foliosDerivadoHijo);
				}
				List foliosDerHijosTmp =forseti.getFoliosDerivadosTMPByMatricula(folio.getIdMatricula());
				if(foliosDerHijosTmp!=null && foliosDerHijosTmp.size()>0)
					local_Result.setFoliosDerHijosTmp(foliosDerHijosTmp);
				
				
				return local_Result;
			}
                        throw new EventoException("Se necesita saber la matrícula a consultar");
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepción al consultar datos del folio " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
	} // end-method: consultaFolio

   private JXPathContext
   jx_GetContext( Object local_ContextObject ) {

      JXPathContext local_Context;
      local_Context = JXPathContext.newContext( local_ContextObject );

      if( local_ContextObject instanceof gov.sir.core.negocio.modelo.Folio ) {
         // TODO: remove cohesion
         local_Context.setFactory( new FolioAbstractFactory() );
      } // if

      local_Context.setLenient( true );

      return local_Context;
   } // end-method: jx_GetContext_Folio


	/**
	 * jx_CalculateFolioComplementacionComplementacion
	 *
	 * @param local_Folio_T0 Folio
	 * @param local_Folio_T1 Folio
	 * @return String[]
	 */
	private String[]
        jx_GetFolioComplementacionComplementacion( Folio local_T0_Folio, Folio local_T1_Folio ) {


            // 2. se hace la carga de los datos de complementacion
            // es un caso especial

            String t0Complementacion = null;
            String t1Complementacion = null;
            String t2Complementacion = null; // delta-complementacion

            load_JxData_FolioComplementacion: {

               JXPathContext local_T0_Context;
               JXPathContext local_T1_Context;
               Object        local_T0_ContextObject;
               Object        local_T1_ContextObject;

               String       local_JxSearchString;

               local_JxSearchString = JXPATH_FOLIO_COMPLEMENTACION_COMPLEMENTACION;

               local_T0_ContextObject = local_T0_Folio;
               local_T1_ContextObject = local_T1_Folio;

               local_T0_Context = jx_GetContext( local_T0_ContextObject );
               local_T1_Context = jx_GetContext( local_T1_ContextObject );

               t0Complementacion = (String)local_T0_Context.getValue( local_JxSearchString );
               t1Complementacion = (String)local_T1_Context.getValue( local_JxSearchString );

               t2Complementacion = (String)minusOp( local_T1_Context, local_T0_Context, local_JxSearchString, true );

            } // :load_JxData_FolioComplementacion

            String[] local_Result;
            local_Result = new String[] {
                  t0Complementacion
                , t1Complementacion
                , t2Complementacion  // delta-complementacion

            };

	    return local_Result;
	}

	/**
	 * getRuleComplementacionAdicionaEnabled
	 *
	 * @param local_Turno Turno
	 * @return boolean
	 */
	private boolean getRuleComplementacionAdicionaEnabled( Turno local_Turno ) throws EventoException {


           // Bug 03552 ---------------------------------------------------------------

           // verificar si esta incluido el caso de adicion de complementacion
           // solamente en este caso, se carga la informacion t0,t1,t2


           // cuadro de reglas:

           //    A: habilitado-permiso-complementacion
           //    B: habilitado-permiso-complementacionadicion
           //
           //    ------------------
           //     AB | respuesta
           //    ----+-------------
           //     00 | no-modifica
           //     01 | adiciona
           //     10 | modifica
           //     11 | modifica
           //    ----+-------------

           boolean ruleComplementacionAdicionaEnabled = false;

           String param_IdWorkflow;

           param_IdWorkflow = local_Turno.getIdWorkflow();

           Folio local_Folio;

           local_Turno = null;

           try {

             local_Turno = hermod.getTurnobyWF( param_IdWorkflow );

           }
           catch( HermodException e  ) {
              Log.getInstance().error(ANCorreccion.class,e);
              throw new EventoException("Errores al adquirir el turno" + e.getMessage() );

           }
           catch( Throwable e  ) {
              Log.getInstance().error(ANCorreccion.class,e);
              throw new EventoException("Errores al adquirir el turno" + e.getMessage() );

           }

           // 1. ver si tiene permiso para complementacion/adicion complementacion
           load_JxData_Permisos: {

              JXPathContext local_Context;
              Object        local_ContextObject;

              String        local_JxSearchString;

              local_JxSearchString = "count( /solicitud/permisos[@idPermiso=$param_IdPermiso] ) > 0 ";

              local_ContextObject = local_Turno;

              local_Context = jx_GetContext( local_ContextObject );
              local_Context.getVariables().declareVariable( "param_IdPermiso", "" );


              // TODO: decoupling
              final String FOLIO_COMPLEMENTACION_ID    = gov.sir.core.web.helpers.correccion.region.model.PermisosCorreccionConstants.FOLIO_COMPLEMENTACION_ID;
              final String FOLIO_COMPLEMENTACIONADD_ID = gov.sir.core.web.helpers.correccion.region.model.PermisosCorreccionConstants.FOLIO_COMPLEMENTACIONADD_ID;

              Boolean local_PermisoComplementacionEnabled = null;
              Boolean local_PermisoComplementacionAddEnabled = null;

              local_Context.setValue( "$param_IdPermiso", FOLIO_COMPLEMENTACION_ID );
              local_PermisoComplementacionEnabled = (Boolean)local_Context.getValue( local_JxSearchString );

              local_Context.setValue( "$param_IdPermiso", FOLIO_COMPLEMENTACIONADD_ID );
              local_PermisoComplementacionAddEnabled = (Boolean)local_Context.getValue( local_JxSearchString );


              ruleComplementacionAdicionaEnabled =    (!local_PermisoComplementacionEnabled.booleanValue())
                                                   && ( local_PermisoComplementacionAddEnabled.booleanValue());

           } // :load_JxData_Permisos

           return ruleComplementacionAdicionaEnabled;
	}


	// computes ( T1 - T0 ) for Strings
   public static Object minusOp( JXPathContext t1_Context, JXPathContext t0_Context, String jxPathExpression, boolean treatNullAsBlank ) {
           Object t0_Value = t0_Context.getValue( jxPathExpression );
           Object t1_Value = t1_Context.getValue( jxPathExpression );

           String t0_ValueAsString = (String)t0_Value;
           String t1_ValueAsString = (String)t1_Value;

           if( null == t1_ValueAsString ) {
              return Nvl( null, getNullableString( treatNullAsBlank ) );
           }
           if( null == t0_ValueAsString ) {
              return Nvl( t1_ValueAsString, getNullableString( treatNullAsBlank ) );
           }

           // si tiene la subcadena, restar
           if( t1_ValueAsString.startsWith( t0_ValueAsString ) ) {
                   return t1_ValueAsString.substring( t0_ValueAsString.length(), t1_ValueAsString.length() );
           } // if

           return getNullableString( treatNullAsBlank );

	} // end-method:minusOp

   // -----------------------------------------------------------------------------
   private String
   print_FootUtils_BuildUserName( long userId ) {
      return "" + userId;
   } // end method

   private String
   print_FootUtils_BuildUserName( Long userId ) {
      if( null == userId ) {
         return getNullableString( true );
      }
      return print_FootUtils_BuildUserName( userId.longValue() ) ;
   } // end method

   private String
   print_FootUtils_BuildUserName( gov.sir.core.negocio.modelo.Usuario user ) {
      if( null == user ) {
         return getNullableString( true );
      }
      return print_FootUtils_BuildUserName( user.getIdUsuario() ) ;
   } // end method


   public static String
   Nvl( String string , String replaceIfNull ) {
      return ( null == string )?( replaceIfNull ):( string );
   } // end-method: Nvl

   public static Long
   Nvl( Long num , Long replaceIfNull ) {
      return ( null == num )?( replaceIfNull ):( num );
   } // end-method: Nvl
   
   public static String
   getNullableString( boolean treatBlankAsNull ) {
      return( ( treatBlankAsNull )?( "" ):( null ) );
   } // end-method: Nvl

   public static Long
   getNullableLong( boolean treatBlankAsNull ) {
      return( ( treatBlankAsNull )?( new Long (0) ):( null ) );
   } // end-method: Nvl
// -----------------------------------------------------------------------------

	/**
	 * Método que permite consultar un folio para que sobre él se consulten o realicen los cambios
	 * que se solicitan. Se le debe pasar el usuario para que pueda retornar los cambios de las tablas temporales.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta consultarFolioUsuario(EvnCorreccion evento) throws EventoException {
		Folio folio = evento.getFolio();
		gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioSIR();


		try {
			if (folio != null && folio.getIdMatricula() != null) {

				FolioPk id = new FolioPk();
				id.idMatricula = folio.getIdMatricula();

				folio = forseti.getFolioByID(id, u);

				return new EvnRespCorreccion(folio, EvnRespCorreccion.CONSULTAR_FOLIO_USUARIO);
			}
            throw new EventoException("Se necesita saber la matrícula a consultar");
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepción al consultar datos del folio " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
	}



        /**
         * Imprimir aparte el formulario de correcciones
         * */
        private EventoRespuesta
        ejecutarRevisionAprobacion_AprobarCorreccion_ImprimirFormulario( EvnCorreccion evento )
        throws EventoException {

      EvnRespCorreccion evRespuesta = null;
      Fase fase = evento.getFase();
      Turno turno = evento.getTurno();
      Turno turnoTemp = (Turno)copy(turno);  /**Se usa un temporal para no perder la información original */
      Solicitud solicitud = turno.getSolicitud();
       /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agregan la variables turnoAnterior, testamento, circulo.
        */
      Turno turnoAnterior = solicitud.getTurnoAnterior();
      Testamento testamento = null;
      List<Map> salvedadesTest = new ArrayList();
      Circulo circulo = evento.getCirculo();
      gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
      List folios = null;

      if (solicitud == null) {
              throw new ANCorreccionException("No existe solicitud asociada");
      }

      // Identificador del turno
      TurnoPk turnoID = new TurnoPk();
      turnoID.idCirculo = turno.getIdCirculo();
      turnoID.idProceso = turno.getIdProceso();
      turnoID.idTurno = turno.getIdTurno();
      turnoID.anio = turno.getAnio();
        /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agrega validacion de turno de testamento
        */
      if(turnoAnterior ==null ||(turnoAnterior!=null && turnoAnterior.getIdProceso()!=6)){
      // LISTA DE LOS IDENTIFICADORES DE LOS FOLIOS INVOLUCRADOS EN LA CORRECCIÓN.
      List solFolio = solicitud.getSolicitudFolios();
      List foliosID = new Vector();
      Iterator itSolFolio = solFolio.iterator();
      while (itSolFolio.hasNext()) {
              SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
              FolioPk folioID = new FolioPk();
              folioID.idMatricula = sol.getFolio().getIdMatricula();
              foliosID.add(folioID);
      }

      // SE IMPRIME EL FORMULARIO, SE HACEN DEFINITIVOS LOS CAMBIOS
      // Y SE DESBLOQUEAN LOS FOLIOS.


      List            t0_ListSolicitudFolio;
      List            t2_ListSolicitudFolio; // Lista de solicitudes folio


      //SE CARGA LA LISTA DE FOLIOS QUE SUFRIERON ALGUNA MODIFICACIÓN
      try {
              folios = forseti.getDeltaFolios(turnoID, usr);



              // Luego de obtener los delta,
              // Construir un objeto folio tomando como guia los cambios-delta
              // --------------------------------------------------------------
              SolicitudFolio  t0_SolicitudFolio;     // elemento de la lista
              Folio           t0_Folio;              // folio a enviar a imprimir
              List            t0_Folio_ListAnotacion;
              Anotacion       t0_Folio_Anotacion;

              // initialize -
              t0_ListSolicitudFolio = folios;
              // --------------------------------------------------------------
              SolicitudFolio  t2_SolicitudFolio;     // elemento de la lista
              Folio           t2_Folio;              // folio a enviar a imprimir
              FolioPk        t2_Folio_Id;           // id de folio
              List            t2_Folio_ListAnotacion;
              Anotacion       t2_Folio_Anotacion;
              // --------------------------------------------------------------
              // initialize -
              t2_ListSolicitudFolio = new ArrayList();

              // --------------------------------------------------------------



              Iterator t0_ListSolicitudFolioIterator = t0_ListSolicitudFolio.iterator();

              for( ; t0_ListSolicitudFolioIterator.hasNext(); ){

                // Consultar los datos anteriores

                t0_SolicitudFolio = (SolicitudFolio)t0_ListSolicitudFolioIterator.next();
                t0_Folio = t0_SolicitudFolio.getFolio();

                t2_Folio_Id = new FolioPk() ;
                t2_Folio_Id.idMatricula     = t0_Folio.getIdMatricula();

                t2_Folio = forseti.getFolioByIDSinAnotaciones( t2_Folio_Id, usr );


                // filtros

                // 1: complementacion
                if( null == t0_Folio.getComplementacion() ){
                  t2_Folio.setComplementacion( null );
                }
                // 2: lindero
                if( null == t0_Folio.getLindero() ){
                  t2_Folio.setLindero( null );
                }
                // 3: estado
                if( null == t0_Folio.getEstado() ){
                  t2_Folio.setEstado( null );
                }

                // recorrer los cambios en anotaciones y crear la lista de anotaciones nueva
                t2_Folio_ListAnotacion = new ArrayList();
                t0_Folio_ListAnotacion = t0_Folio.getAnotaciones();

                Iterator t0_Folio_ListAnotacionIterator;
                t0_Folio_ListAnotacion = filter_SortAnotacionesByOrden( t0_Folio_ListAnotacion );

                t0_Folio_ListAnotacionIterator = t0_Folio_ListAnotacion.iterator();

                for( ;t0_Folio_ListAnotacionIterator.hasNext();){

                  t0_Folio_Anotacion = (Anotacion)t0_Folio_ListAnotacionIterator.next();

                  t2_Folio_Anotacion = forseti_GetFolioAnotacion( t2_Folio_Id, t0_Folio_Anotacion, usr );

                  // para filtrar anotaciones link
                  if( null == t2_Folio_Anotacion ) {
                	  continue;
                  }

                  // eliminar las salvedades originales, colocar solo las de los delta
                  t2_Folio_Anotacion.setSalvedades( null );

                  List t0_Folio_Anotacion_SalvedadList = t0_Folio_Anotacion.getSalvedades();

                  // Bug 3565 ---------------------------
                  List local_TargetSalvedadesList; // TODO: externalize

                  local_TargetSalvedadesList
                      = jxSearch_ExtractSalvedadesByTurno(
                            t0_Folio_Anotacion_SalvedadList
                          , turno.getIdWorkflow()
                        );

                  // fix the results
                  t2_Folio_Anotacion.setSalvedades( local_TargetSalvedadesList );
                  // -----------------------------------

                  /*

                  for( Iterator t0_Folio_Anotacion_SalvedadListIterator = t0_Folio_Anotacion_SalvedadList.iterator(); t0_Folio_Anotacion_SalvedadListIterator.hasNext(); ){
                    SalvedadAnotacion localSalvedadAnotacion = (SalvedadAnotacion)t0_Folio_Anotacion_SalvedadListIterator.next();
                    // Bug 03565: filtrar a mano las salvedades del folio
                    localSalvedadAnotacion.setNumRadicacion( turnoID.toString() );
                  }

                  t2_Folio_Anotacion.setSalvedades( t0_Folio_Anotacion_SalvedadList );
                  */
                  /**Si las anotaciones las realizo el usuario con otro  rol no debe ser impresas*/
                  if(t2_Folio_Anotacion.getIdWorkflow()==null||t2_Folio_Anotacion.getIdWorkflow().equals("")||
                  t2_Folio_Anotacion.getIdWorkflow().equals(turno.getIdWorkflow())){
                	  t2_Folio_ListAnotacion.add( t2_Folio_Anotacion );
                  }
                }

                try
                {
                	 //ordenar las anotaciones por numero
                        /**
                        * @autor:Edgar Lora
                        * @mantis: 0011599
                        * @requerimiento: 085_151
                        */
                	 Collections.sort(t2_Folio_ListAnotacion, new ComparadorAnotaciones());
                }
                catch (ClassCastException e)
                {
                	Log.getInstance().debug(ANCorreccion.class,"@@@No fue posible ordenar las anotaciones por orden LPAD");
                	Log.getInstance().error(ANCorreccion.class,e);
                }

                // incluir las anotaciones al folio
                t2_Folio.setAnotaciones( t2_Folio_ListAnotacion );


                // Bug 03565
                // observar como estan llegando las salvedades de folio:
                // el servicio getFolioByIdSinAnotaciones
                // regresa las salvedades en temporal.

                //  Se debe tener en cuenta filtrar por el id del turno



                // // eliminar las salvedades originales, colocar solo las de los delta
                // t2_Folio.setSalvedades( null );

                // List t0_Folio_SalvedadList =  t0_Folio.getSalvedades();

                // for( Iterator t0_Folio_SalvedadListIterator = t0_Folio_SalvedadList.iterator(); t0_Folio_SalvedadListIterator.hasNext(); ){
                //   SalvedadFolio localSalvedadFolio = (SalvedadFolio)t0_Folio_SalvedadListIterator.next();

                //  // Bug 03565
                //  localSalvedadFolio.setNumRadicacion( turnoID.toString() );
                //}
                // t2_Folio.setSalvedades( t0_Folio_SalvedadList );

                // -----------------------------------------------------------------
                // ...... target variables
                List local_TargetFolioSalvedadesList;

                local_TargetFolioSalvedadesList
                    = jxSearch_ExtractSalvedadesByTurno(
                          t2_Folio.getSalvedades()
                        , turno.getIdWorkflow()
                      );

                // fix the results
                t2_Folio.setSalvedades( local_TargetFolioSalvedadesList );
                // -----------------------------------------------------------------

                // Construir el nuevo objeto y adicionarlo
                t2_SolicitudFolio = new SolicitudFolio();
                t2_SolicitudFolio.setFolio( t2_Folio );
                t2_ListSolicitudFolio.add( t2_SolicitudFolio );
              } // for


      }
      catch (ForsetiException e) {
    	  	  Log.getInstance().error(ANCorreccion.class,e);
              throw new EventoException(e.getMessage(), e);
      }
      catch (Throwable e) {
    	  	  Log.getInstance().error(ANCorreccion.class,e);
              ExceptionPrinter printer = new ExceptionPrinter(e);
              Log.getInstance().error(ANCorreccion.class,"Error trayendo los folios que fueron cambiados. " + printer.toString());
              throw new EventoException(e.getMessage(), e);
      }

      //COLOCARLE AL TURNO LA LISTA DE FOLIOS QUE FUERON OBJETO DE MODIFICACIÓN
      Solicitud solic = new Solicitud();
      solic = turnoTemp.getSolicitud();
      // solic.setSolicitudFolios(folios);
      solic.setSolicitudFolios( t2_ListSolicitudFolio );
      turnoTemp.setSolicitud(solic);
      }else
      {
            /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: setencias en caso de que le turno sea de correccion de testamento
        */
            try {
                TestamentoSIR test = new TestamentoSIR();
                testamento = getTestamentoFromMap(test.getTestamento(turnoAnterior.getSolicitud().getIdSolicitud(),turno.getIdWorkflow()));
                testamento.setTestadores(getTestadoresFromList(test.getTestadoresTMP(turnoAnterior.getSolicitud().getIdSolicitud())));
                salvedadesTest.add(test.getSalvedad(testamento.getIdTestamento(),turno.getIdWorkflow())); 
            } catch (GeneralSIRException ex) {
                Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                throw new EventoException(ex.getMessage(), ex);
            }
      }


      
      
      
      // realizar busqueda de usuario
      String formularioCorrecciones_UsuarioRealizaCorreccion = "";
      Long formularioCorrecciones_UsuarioRealizaCorreccionId = null;
      boolean founded_FormularioCorrecciones_UsuarioRealizaCorreccion;
      boolean isImpresionTemporalDeAuxiliarEnabled = evento.isImpresionTemporalDeAuxiliarEnabled();

      founded_FormularioCorrecciones_UsuarioRealizaCorreccion = false;

      if( !founded_FormularioCorrecciones_UsuarioRealizaCorreccion ) {

        if( isImpresionTemporalDeAuxiliarEnabled ) {
           gov.sir.core.negocio.modelo.Usuario local_User = usr;
           formularioCorrecciones_UsuarioRealizaCorreccionId = Nvl( new Long(local_User.getIdUsuario()), getNullableLong( true ) );
           founded_FormularioCorrecciones_UsuarioRealizaCorreccion = true;
        } // if

      } // if

      if( !founded_FormularioCorrecciones_UsuarioRealizaCorreccion ) {

        local_SearchImpl_jx: {

                  // :: local variables ----------------------------------------------
                  Turno local_Turno;
                  JXPathContext local_TurnoSearchContext;

                  // // List< TurnoHistoria >
                  List local_TurnoHistoria;
                  Iterator local_TurnoSearchContextIterator;

                  final String SEARCHED__FASE_CORRECCIONES_CORRECCIONSIMPLE = "COR_CORRECCION_SIMPLE";

                  Object local_FoundedValue; // elemento encontrado

                  // initialize context & variables
                  local_Turno = turno;
                  local_TurnoHistoria = local_Turno.getHistorials(); // por el momento se observa el registro historico que tiene el turno
                  local_TurnoSearchContext = jx_GetContext( local_TurnoHistoria );

                  // evaluateNextRule = true;
                  // local_TipoCorreccion = null;

                  // declare jxpath variables
                  local_TurnoSearchContext.getVariables().declareVariable( "local_FaseId"            , "" );

                  // -----------------------------------------------------------------
                  // simple queries
                  // apply first

                  // ruleA: search-rule:
                  //  buscar en el historial
                  //  el usuario que realizo la correccion simple en ultimo lugar
                  //String searchRule = " .[ (@fase = $local_FaseId ) ]/usuarioAtiende/username[last()]";
                  String searchRule = " .[ (@fase = $local_FaseId ) ]/usuarioAtiende/idUsuario[last()]";

                  local_TurnoSearchContext.setValue( "$local_FaseId"     , SEARCHED__FASE_CORRECCIONES_CORRECCIONSIMPLE  );

                  local_FoundedValue = local_TurnoSearchContext.getValue( searchRule );

                  //formularioCorrecciones_UsuarioRealizaCorreccion = Nvl( (String)local_FoundedValue, getNullableString( true ) );
                  formularioCorrecciones_UsuarioRealizaCorreccionId = Nvl( (Long)local_FoundedValue, getNullableLong( true ) );

        } // :searchImpl_jx

      } // if


      //TODO: depurar el objeto que se envia al imprimible



      //IMPRIMIR EL FORMULARIO DE CORRECCIONES.
      String fechaImpresion= this.getFechaImpresion();
        /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agregan dos variambles mas instacian de la nueva clase ImprimibleFormularioTestamento 
        */
      ImprimibleFormularioTestamento imprimibleT = null;
      ImprimibleFormularioTestamento imprimibleTpdf = null;
      ImprimibleFormulario imprimible= null;
      ImprimibleFormulario imprimiblepdf= null;
        /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agrega una condicion para comprobar si el turno es de correccion de testamento
        */
      if(turnoAnterior ==null ||(turnoAnterior!=null && turnoAnterior.getIdProceso()!=6)){  
        imprimible = new ImprimibleFormulario(turnoTemp, evento.getUsuarioSIR().getUsername(), CTipoFormulario.TIPO_CORRECCION, fechaImpresion,CTipoImprimible.FORMULARIO_CORRECCION);
        imprimible.setPrintWatermarkEnabled(true);
        imprimible.setFormularioNoOficialCorreccionesEnabled( isImpresionTemporalDeAuxiliarEnabled );
        
        imprimiblepdf = new ImprimibleFormulario(turnoTemp, evento.getUsuarioSIR().getUsername(), CTipoFormulario.TIPO_CORRECCION, fechaImpresion,CTipoImprimible.FORMULARIO_CORRECCION);
        imprimiblepdf.setPrintWatermarkEnabled(true);
        imprimiblepdf.setFormularioNoOficialCorreccionesEnabled( isImpresionTemporalDeAuxiliarEnabled );
        imprimiblepdf.setPdf(true);
        /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agregan sentecias para instanciar los imprimibles de correccion de testamentos.
        */
      }else
      {
      
          Date fechaRadicacion = null;
		if(turnoAnterior.getSolicitud().getFecha()!=null){
			fechaRadicacion = turnoAnterior.getSolicitud().getFecha(); 
		}
                try {
                    circulo = forseti.getCirculo(new CirculoPk(evento.getCirculoId()));
                } catch (Throwable ex) {
                    Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                }
                /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agregan sentecias para cargar documento con las correcciones realizadas
        */
          TestamentoSIR testSir = new TestamentoSIR();
          String idSolicitud = turnoAnterior.getSolicitud().getIdSolicitud();
          SolicitudRegistro sr = ((SolicitudRegistro)turnoAnterior.getSolicitud());
            try {
                Documento documentoR = this.getDocumentoFromMap(testSir.getEncabezado(idSolicitud,turno.getIdWorkflow()));
                sr.setDocumento(documentoR);
                
            } catch (GeneralSIRException ex) {
                Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
            }
          imprimibleT = new ImprimibleFormularioTestamento(testamento,
                                                          ((SolicitudRegistro)turnoAnterior.getSolicitud()).getDocumento(),
                                                          turno.getIdWorkflow(),
                                                          circulo.getNombre(),fechaImpresion,evento.getUsuarioSIR(),
                                                          fechaRadicacion,CTipoImprimible.FORMULARIO_CORRECCION,CTipoFormulario.TIPO_CORRECCION);
          
          imprimibleT.setPrintWatermarkEnabled(true);
          imprimibleT.setFormularioNoOficialCorreccionesEnabled(isImpresionTemporalDeAuxiliarEnabled);
          imprimibleT.setIdCirculo(circulo.getIdCirculo());
          imprimibleT.setIdTurnoTestamento(turnoAnterior.getIdWorkflow());
          imprimibleT.setSalvedades(salvedadesTest);
          
          imprimibleTpdf = new ImprimibleFormularioTestamento(testamento,
                                                          ((SolicitudRegistro)turnoAnterior.getSolicitud()).getDocumento(),
                                                          turno.getIdWorkflow(),
                                                          circulo.getNombre(),fechaImpresion,evento.getUsuarioSIR(),
                                                          fechaRadicacion,CTipoImprimible.FORMULARIO_CORRECCION,CTipoFormulario.TIPO_CORRECCION);
          imprimibleTpdf.setPrintWatermarkEnabled(true);
          imprimibleTpdf.setFormularioNoOficialCorreccionesEnabled(isImpresionTemporalDeAuxiliarEnabled);
          imprimibleTpdf.setPdf(true);
          imprimibleTpdf.setIdCirculo(circulo.getIdCirculo());
          imprimibleTpdf.setIdTurnoTestamento(turnoAnterior.getIdWorkflow());
          imprimibleTpdf.setSalvedades(salvedadesTest);
      }


      // TODO: enviar nombre de ultimo auxiliar de correcciones
      /*imprimible.setFormularioCorrecciones_UsuarioRealizaCorreccion(
        formularioCorrecciones_UsuarioRealizaCorreccion
      );*/
      String valorIdUsuario = formularioCorrecciones_UsuarioRealizaCorreccionId.toString();
        /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agregan se agrega condicon para instanciar los objetos Bundle
        */
                    String nombre = "";
                    String archivo = "";
                    String cargoToPrint = "";
                    String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
                    FirmaRegistrador firmaRegistrador = null;
                    CirculoPk cid = new CirculoPk();
                    cid.idCirculo = turno.getIdCirculo();
                    String rutaFisica = null;
                    String sNombre = "";
                    try {
                        firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);

                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }
                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }
                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }

                        rutaFisica = hermod.getPathFirmasRegistradores();

                        sNombre = firmaRegistrador.getNombreRegistrador();
                        archivo = firmaRegistrador.getIdArchivo();

                        if (turno.isNacional()) {
                            if (firmaRegistrador == null || archivo.toUpperCase().equals("SINFIRMA.GIF") || archivo.toUpperCase().equals("SINFIRMA.JPG")
                                    || archivo == null || archivo.equals("")) {
                                throw new Exception("No se permiten certificados de orden nacional sin firma");
                            }
                        }

                        //Se recupera el verdadero cargo para definir si es ENCARGADO o
                        //no lo es.
                        cargo = firmaRegistrador.getCargoRegistrador();

                        //Se saca el valor del cargo para imprimirlo en el certificado
                        List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

                        cargoToPrint = "";
                        OPLookupCodes lookUp;
                        for (Iterator it = cargos.iterator(); it.hasNext();) {
                            lookUp = (OPLookupCodes) it.next();
                            if (lookUp.getCodigo().equals(cargo)) {
                                cargoToPrint = lookUp.getValor();
                            }
                        }
                    } catch (Exception e) {
                        
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }

                    if (sNombre == null) {
                        sNombre = "";
                    }

                    //+++
                    imprimible.setCargoRegistrador(cargoToPrint);
                    imprimible.setNombreRegistrador(sNombre);
                    imprimiblepdf.setCargoRegistrador(cargoToPrint);
                    imprimiblepdf.setNombreRegistrador(sNombre);
                    imprimiblepdf.setChangetextforregistrador(true);
                    imprimible.setChangetextforregistrador(true);
                    
                    if (rutaFisica != null) {
                        BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                         byte pixeles[] = null;
                        try {
                            pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                        } catch (Throwable e1) {

                            e1.printStackTrace();
                        }
                            imprimible.setPixelesImagenFirmaRegistrador(pixeles);
                            imprimiblepdf.setPixelesImagenFirmaRegistrador(pixeles);
                        
                    }  
        
        
      Bundle bundle = null;
      Bundle bundle2 = null;
      if(turnoAnterior ==null ||(turnoAnterior!=null && turnoAnterior.getIdProceso()!=6)){
        imprimible.setIdUsuario(valorIdUsuario);
        imprimiblepdf.setIdUsuario(valorIdUsuario);
        bundle = new Bundle(imprimible);
        bundle2 = new Bundle(imprimiblepdf);
      }else{
          bundle = new Bundle(imprimibleT);
          bundle2 = new Bundle(imprimibleTpdf);
      }
      bundle.setNumeroCopias(evento.getNumeroCopias());
      bundle2.setNumeroCopias(evento.getNumeroCopias());
      String local_PrintId;

      local_PrintId = evento.getCirculoId();
      //local_PrintId = evento.getCirculoId();


	  //OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
	  int maxIntentos;
	  int espera;

	  //INGRESO DE INTENTOS DE IMPRESION
	  try {

		  String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
		  if (intentosImpresion != null) {
			  Integer intentos = new Integer(intentosImpresion);
			  maxIntentos = intentos.intValue();
		  } else {
			  Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
			  maxIntentos = intentosDefault.intValue();
		  }

		  //INGRESO TIEMPO DE ESPERA IMPRESION
		  String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
		  if (intentosImpresion != null) {
			  Integer esperaInt = new Integer(esperaImpresion);
			  espera = esperaInt.intValue();
		  } else {
			  Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
			  espera = esperaDefault.intValue();
		  }
	  } catch (Throwable t) {
		  Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
		  maxIntentos = intentosDefault.intValue();

		  Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
		  espera = esperaDefault.intValue();

	  }


          try {
        	  Log.getInstance().debug(ANCorreccion.class,"*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo enviar*");
        	  Log.getInstance().debug(ANCorreccion.class,"*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo enviar*");
        	  Log.getInstance().debug(ANCorreccion.class,"*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo enviar*");
              printJobs.enviar( local_PrintId, bundle , maxIntentos, espera);
              Log.getInstance().debug(ANCorreccion.class,"*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo agregarImprimiblePDF*");
        	  Log.getInstance().debug(ANCorreccion.class,"*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo agregarImprimiblePDF*");
        	  Log.getInstance().debug(ANCorreccion.class,"*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo agregarImprimiblePDF*");
              printJobs.agregarImprimiblePDF(bundle2,!isImpresionTemporalDeAuxiliarEnabled);
              Log.getInstance().debug(ANCorreccion.class,"*************LOGS IMPRIMIBLE PDF :AnCorrecion FIN IMPRESION DEL METODO agregarImprimiblePDF*");
          }
          catch (Throwable t) {
              t.printStackTrace();
              throw new EventoException(t.getMessage(), t);
          }


      // Avanzar turno de correcciones al subflujo siguiente (impresion)

       /////////////////////////////////////////////////////////////////////////////////
       /*   VERIFICAR PARA QUE SON LAS LINEAS SIGUIENTES PORQUE NO SE LES ENCUENTRA
            NINGUNA UTILIDAD.  DEBERIAN SER ELIMINADAS. ???????????????????????????
            (I'm Agree)
       */

      String respuestaWf = null;

      turno       = evento.getTurno();
      fase        = evento.getFase();
      respuestaWf = EvnCorreccion.CONFIRMAR_WF;


      String USUARIO_INICIADOR = evento.getUsuarioSIR().getUsername();

      Hashtable parameters = new Hashtable();
      parameters.put( Processor.RESULT            , respuestaWf );
      parameters.put( CInfoUsuario.USUARIO_SIR    , USUARIO_INICIADOR );
      //parameters.put( Processor.USUARIO_INICIADOR , USUARIO_INICIADOR );


      // ???????????????????????????????????????????????????????????????????????????????  //
      ///////////////////////////////////////////////////////////////////////////////////////

      evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.CORR_REVISIONAPROBACION__PRINTFORM_EVENTRESP_OK);
                return evRespuesta;
        } // end-method

    	/**
    	 * Hace una copia de un objeto, para evitar referencias de apuntadores en memoria.
    	 * be serialized.
    	 * @param orig
    	 * @return
    	 **/
    	protected static Object copy(Object orig) {
    		Object obj = null;

    		try {
    			// Write the object out to a byte array
    			ByteArrayOutputStream bos = new ByteArrayOutputStream();
    			ObjectOutputStream out = new ObjectOutputStream(bos);
    			out.writeObject(orig);
    			out.flush();
    			out.close();

    			// Make an input stream from the byte array and read
    			// a copy of the object back in.
    			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
    			obj = in.readObject();
    		} catch (IOException e) {
    			Log.getInstance().error(ANCorreccion.class,e);
    		} catch (ClassNotFoundException cnfe) {
    			Log.getInstance().error(ANCorreccion.class,cnfe);
    		}

    		return obj;
    	}	 

 // Salvedad debe tener atributo numRadicacion
 // a traves del cual se filtra
 // Se usa el mismo para salvedades de folio y anotacion
 public List
 jxSearch_ExtractSalvedadesByTurno(
   List   t0_ListSalvedades
 , String local_Param_TurnoId
 ) {

  // zero-value cases
  if( null == t0_ListSalvedades ) {
     return null;
  } // if

  if( 0 == t0_ListSalvedades.size() ) {
     return t0_ListSalvedades;
  } // if

  local_SearchImpl_jx: {

             // :: local variables ----------------------------------------------
             // ...... source variables
             JXPathContext 	local_JxContext;
             Object 			local_JxContextSource;
             String 			local_JxSearchString;

             // ...... target variables
             List local_TargetSalvedadesList;

             // target jx-object
             local_JxContextSource = t0_ListSalvedades;

             // :: initialize
             local_JxContext = jx_GetContext( local_JxContextSource );

             // (declare variables)
             local_JxContext.getVariables().declareVariable( "local_NumRadicacion", "" );

             local_JxSearchString = " .[ (@numRadicacion = $local_NumRadicacion ) ]";
             local_JxContext.setValue( "$local_NumRadicacion", local_Param_TurnoId );


             // :: get the results
             // single object   : local_JxContext.getValue
             // multiple object : local_JxContext.iterate

             Iterator local_TargetIterator;
             local_TargetIterator = local_JxContext.iterate( local_JxSearchString );


             local_TargetSalvedadesList = new ArrayList();

             for( ; local_TargetIterator.hasNext() ; )  {
                 // consume
                 local_TargetSalvedadesList.add( local_TargetIterator.next() );
             } // for





             // -----------------------------------------------------------------
             // fix the results
             return local_TargetSalvedadesList;

  } // :local_SearchImpl_jx


} // jxSearch_ExtractSalvedadesFolioByTurno


















  /**
   * filter_SortAnotacionesByOrden
   *
   * @param t0_Folio_ListAnotacion List
   * @return List
   */
  private List filter_SortAnotacionesByOrden(List t0_Folio_ListAnotacion) {

    if( null == t0_Folio_ListAnotacion )
      return null;

    List result = new ArrayList();

    Anotacion[] t0_Folio_ListAnotacionArray = new Anotacion[t0_Folio_ListAnotacion.size()];


    t0_Folio_ListAnotacionArray = (Anotacion[]) t0_Folio_ListAnotacion.toArray( t0_Folio_ListAnotacionArray );

    String elementInner1_ToCompare;
    String elementInner2_ToCompare;

    Anotacion elementAux;

    for( int i=0; i < t0_Folio_ListAnotacionArray.length; i++ ){
      elementInner1_ToCompare = t0_Folio_ListAnotacionArray[i].getOrden();
      elementInner1_ToCompare = (null==elementInner1_ToCompare)?(""):(elementInner1_ToCompare);
      for( int j=0; j < t0_Folio_ListAnotacionArray.length; j++ ){
        elementInner2_ToCompare = t0_Folio_ListAnotacionArray[j].getOrden();
        elementInner2_ToCompare = (null==elementInner2_ToCompare)?(""):(elementInner2_ToCompare);
        if( elementInner2_ToCompare.compareTo( elementInner1_ToCompare ) > 0 ) {
          elementAux = t0_Folio_ListAnotacionArray[i];
          t0_Folio_ListAnotacionArray[i] = t0_Folio_ListAnotacionArray[j];
          t0_Folio_ListAnotacionArray[j] = elementAux;
        } // end if
      } // end for
    } // end for
    // build the list again
    for( int i=0; i < t0_Folio_ListAnotacionArray.length; i++ ) {
      Log.getInstance().debug(ANCorreccion.class,""+t0_Folio_ListAnotacionArray[i].getOrden());
      result.add( t0_Folio_ListAnotacionArray[i] );
    }

    return result;
  }

  /**
   * forseti_GetFolioAnotacion
   *
   * @param t0_Folio_Anotacion Anotacion
   * @return Anotacion
   */
  private Anotacion forseti_GetFolioAnotacion( FolioPk folio_Id , Anotacion t0_Folio_Anotacion, gov.sir.core.negocio.modelo.Usuario usuario ) {
    Anotacion result = null;
    java.util.List temporalResultList = null;

    String searchCriteria_Value = t0_Folio_Anotacion.getIdAnotacion();

    try {
      temporalResultList
          = forseti.getAnotacionesFolio(folio_Id, CCriterio.POR_ID_ANOTACION,
                                        searchCriteria_Value, 0, 1, usuario, false);
    }
    catch (Throwable ex) {
      Log.getInstance().error(ANCorreccion.class,"Error trayendo los folios que fueron cambiados. " + ex.getMessage() );
    }

    if( null == temporalResultList )
      return null;
    Iterator temporalResultListIterator = temporalResultList.iterator();
    for( ;temporalResultListIterator.hasNext(); ){
      result = (Anotacion)temporalResultListIterator.next();
      break;
    }
    return result;
  }



      //DEPURADO ENERO 17 2006
      /**
       * Aprueba la solicitud de corrección y hace definitivos los cambios que estan en
       * las tablas temporales.
       * @param evento
       * @return
       * @throws EventoException
       */
        private EventoRespuesta ejecutarRevisionAprobacion_AprobarCorreccion( EvnCorreccion evento )
        throws EventoException {


      //1. Se obtienen los parámetros desde el evento de correcciones.
      EvnRespCorreccion evRespuesta = null;
      Fase fase = evento.getFase();
      Turno turno = evento.getTurno();
         /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agrega variable turnoAnterior
        */
      Turno turnoAnterior = turno.getSolicitud().getTurnoAnterior();
      //Solicitud solicitud = turno.getSolicitud();
      Solicitud solicitud = null;
      gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
      List folios = null;

 	  // 2. Se valida que no sea necesario exigir una nota informativa para permitir el avance
	  //del turno desde esta fase.
	  try {
			   Hashtable tablaAvance = new Hashtable(2);
			   tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
			   tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
			   hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
			   solicitud = hermod.getSolicitudById(turno.getSolicitud().getIdSolicitud());
	  }
	  catch(Throwable t)
	  {
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
	  }




      if (solicitud == null) {
              throw new ANCorreccionException("No existe solicitud asociada");
      }

      // Identificador del turno
      TurnoPk turnoID = new TurnoPk();
      turnoID.idCirculo = turno.getIdCirculo();
      turnoID.idProceso = turno.getIdProceso();
      turnoID.idTurno = turno.getIdTurno();
      turnoID.anio = turno.getAnio();
   /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agrega validacion para turnoAnterio
        */
      if(turnoAnterior ==null ||(turnoAnterior!=null && turnoAnterior.getIdProceso()!=6)){
      // LISTA DE LOS IDENTIFICADORES DE LOS FOLIOS INVOLUCRADOS EN LA CORRECCIÓN.
      List solFolio = solicitud.getSolicitudFolios();
      List foliosID = new Vector();
      Iterator itSolFolio = solFolio.iterator();
      while (itSolFolio.hasNext()) {
              SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
              FolioPk folioID = new FolioPk();
              folioID.idMatricula = sol.getFolio().getIdMatricula();
              foliosID.add(folioID);
      }

      // SE IMPRIME EL FORMULARIO, SE HACEN DEFINITIVOS LOS CAMBIOS
      // Y SE DESBLOQUEAN LOS FOLIOS.

      //SE CARGA LA LISTA DE FOLIOS QUE SUFRIERON ALGUNA MODIFICACIÓN
      try {
              folios = forseti.getDeltaFolios(turnoID, usr);
      }
      catch (ForsetiException e) {
              throw new EventoException(e.getMessage(), e);
      }
      catch (Throwable e) {
              ExceptionPrinter printer = new ExceptionPrinter(e);
              Log.getInstance().error(ANCorreccion.class,"Error trayendo los folios que fueron cambiados. " + printer.toString());
              throw new EventoException(e.getMessage(), e);
      }

      //COLOCARLE AL TURNO LA LISTA DE FOLIOS QUE FUERON OBJETO DE MODIFICACIÓN
      Solicitud solic = new Solicitud();
      solic = turno.getSolicitud();
      solic.setSolicitudFolios(folios);
      turno.setSolicitud(solic);

                      //TODO: depurar el objeto que se envia al imprimible



      //IMPRIMIR EL FORMULARIO DE CORRECCIONES.
      // @see: ejecutarRevisionAprobacion_AprobarCorreccion_ImprimirFormulario
      /*
      ImprimibleFormulario imprimible = new ImprimibleFormulario(turno, evento.getUsuarioSIR().getUsername(), CTipoFormulario.TIPO_CORRECCION);
      Bundle bundle = new Bundle(imprimible);
                  bundle.setNumeroCopias(evento.getNumeroCopias());

      int numIntentos = 10;
      int espera = 200;
      for (int i = 0; i < numIntentos; i++) {
              try {
                      Thread.sleep(espera);
                      printJobs.enviar(evento.getUID(), bundle);
                      break;
              }
              catch (Throwable t) {
                      t.printStackTrace();
                      if (i == (numIntentos - 1)) {
                              throw new EventoException(t.getMessage(), t);
                      }
              }
      }
      */

      Iterator itCR = foliosID.iterator();

		
//		HACER crear los folios que cumplen con el - a folios nuevos
		while (itCR.hasNext()) {
			FolioPk folioID = (FolioPk) itCR.next();
			try {
				//Folio folioCCrear;
				Folio folio = new Folio();
				folio.setIdMatricula(folioID.idMatricula);
				String matriTempFl = folioID.idMatricula;
				if(folio != null && folio.getIdMatricula() != null){
					if((folio.getIdMatricula().split("-")).length == 3){
						folio = crearFolio(folio,evento,evento.getUsuarioSIR());
						folioID.idMatricula = folio.getIdMatricula();
						Turno turnoVolver = evento.getTurno();
						if(turnoVolver != null && turnoVolver.getSolicitud() != null 
							&& turnoVolver.getSolicitud().getSolicitudFolios() != null){
							List lstSolicitudFl = turnoVolver.getSolicitud().getSolicitudFolios();
							Iterator iterasol = lstSolicitudFl.iterator();
							while(iterasol.hasNext()){
								SolicitudFolio solFl = (SolicitudFolio)iterasol.next();
								if(solFl != null && solFl.getIdMatricula().equals(matriTempFl)){
									solFl.setIdMatricula(folio.getIdMatricula());
									solFl.setFolio(folio);
								}
							}
						}
					}
				}
				
			}catch (Throwable e) {

				throw new EventoException(e.getMessage(), e);
			}
		}
      
                                ListIterator itID = foliosID.listIterator(foliosID.size());
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
                                    infoUsuario.put("idTurno",turno.getIdWorkflow());
                                }
                            co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                                try {
                                    auditoria.guardarDatosTerminal(infoUsuario);
                                } catch (GeneralSIRException ex) {
                                    Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                                }


      //HACER DEFINITIVOS LOS CAMBIOS
      //CODIGO ANTERIOR PARA HACER DEFINITIVOS LOS FOLIOS POR ISSUE.
     /*  while (itID.hasPrevious()) {
              FolioPk folioID = (FolioPk) itID.previous();
              try {
                      Folio folio = new Folio();
                      folio.setIdMatricula(folioID.idMatricula);
                      forseti.hacerDefinitivoFolio(folio, usr, turnoID, true);
              } catch (HermodException e) {

                      throw new EventoException(e.getMessage(), e);
              } catch (Throwable e) {

                      throw new EventoException(e.getMessage(), e);
              }
      }*/
     Iterator iteraFolio = folios.iterator();
     
     while (iteraFolio.hasNext()) {
              
              try {
                      SolicitudFolio sf=(SolicitudFolio) iteraFolio.next();
                      Folio f = sf.getFolio();
                      forseti.hacerDefinitivoFolio(f, usr, turnoID, true);
              } catch (HermodException e) {

                      throw new EventoException(e.getMessage(), e);
              } catch (Throwable e) {

                      throw new EventoException(e.getMessage(), e);
              }
      }
      /**
        * @Author Carlos Torres
        * @Mantis 13176
        * @Chaged 
        */
            try {
                auditoria.borrarDatosTerminal(turno.getIdWorkflow());
            } catch (GeneralSIRException ex) {
                Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
            }
      }else{
          TestamentoSIR testamentoSir = new TestamentoSIR();
            try {
                testamentoSir.hacerDefinitivoTestamento(turnoAnterior.getSolicitud().getIdSolicitud(),usr.getIdUsuario(),turno.getIdWorkflow());
            } catch (GeneralSIRException ex) {
                Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                throw new EventoException(ex.getMessage());
            }
      }
      
      /* El servicio de hacer definitivo desbloquea los folios
      //DESBLOQUEAR LOS FOLIOS
      try {
              forseti.desbloquearFolios(turnoID, usr);
      } catch (ForsetiException e) {
              throw new EventoException(e.getMessage(), e);
      } catch (Throwable e) {
              ExceptionPrinter printer = new ExceptionPrinter(e);
              Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada desbloqueadno los folios. " + printer.toString());
              throw new EventoException(e.getMessage(), e);
      }*/

      // Avanzar turno de correcciones al subflujo siguiente (impresion)

      String respuestaWf = null;

      turno       = evento.getTurno();
      fase        = evento.getFase();
      respuestaWf = EvnCorreccion.CONFIRMAR_WF;

      try {
		Turno turnoPadre = hermod.getTurnoPadre(turnoID);
		if (turnoPadre != null)
		{
			respuestaWf = EvnCorreccion.CONFIRMAR_COR_INT_WF;
		}
	} catch (Throwable e1) {
		e1.printStackTrace();
	}

      String USUARIO_INICIADOR = evento.getUsuarioSIR().getUsername();

      Hashtable parameters = new Hashtable();
      parameters.put( Processor.RESULT            , respuestaWf );
      parameters.put( CInfoUsuario.USUARIO_SIR    , USUARIO_INICIADOR );

      try {
              hermod.avanzarTurnoNuevoNormal( turno, fase, parameters, evento.getUsuarioSIR());
      }
      catch( HermodException e ) {
              throw new ANCorreccionException("No se pudo APROBAR la solicitud de corrección", e);
      }
      catch (Throwable e) {
              ExceptionPrinter printer = new ExceptionPrinter(e);
              Log.getInstance().error(ANCorreccion.class,"No se pudo APROBAR la solicitud de corrección " + printer.toString());
              throw new EventoException(e.getMessage(), e);
      }
      
            /**
             * @author: Daniel Forero 
             * @change: 1159.111.LIBERACION.FOLIOS.TURNOS.FINALIZADOS.
             * La invocación de este método permite liberar todos los folios del turno que no fueron liberados.
             */
            try {
                int foliosLiberados = hermod.desbloquearFolios(turno);
                
                // Cuando el número de folios liberados es mayor a 0, quiere decir que por alguna 
                // razón durante el avance del turno, algunos de los folios no fueron liberados.
                // Esto verifica que la liberación de folios en este punto es necesaria y que el 
                // error asociado al requerimiento 1159, que hasta la fecha (17/09/2015) no ha sido 
                // identificado, sigue ocurriendo cuando el turno avanza de la fase REVISAR Y APROBAR.
                if(foliosLiberados > 0){
                    // Imprimier el turno y el número de folios liberados para llevar un control del error.
                    Log.getInstance().info(ANCorreccion.class, "REQ-1159: "
                            + "Folios liberados => " + foliosLiberados + ", "
                            + "Turno => " + turno.getIdWorkflow());
                }
            } catch (HermodException e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().error(ANCorreccion.class, "Ha ocurrido un error inesperado durante la liberación de los folios del turno "
                        + turno.getIdWorkflow() + ": "
                        + printer.toString());
            }

		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.APROBAR);
		return evRespuesta;
	}
	
	
	/**
	 * Método que se encarga de la creación del nuevo folio
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private Folio crearFolio(Folio folioFict, EvnCorreccion evento ,Usuario usuarioSIR) throws EventoException {

		/*Folio folio = evento.getFolio();*/
		Turno t = evento.getTurno();
		//Turno.ID tid = new Turno
		
		TurnoPk oidTurno = new TurnoPk();
		
		oidTurno.anio = t.getAnio();
		oidTurno.idCirculo = t.getIdCirculo();
		oidTurno.idProceso = t.getIdProceso();
		oidTurno.idTurno = t.getIdTurno();
			
		String idSolicitud = "";
		if(evento.getTurno() != null && evento.getTurno().getSolicitud() != null 
			&& evento.getTurno().getSolicitud().getIdSolicitud() != null){
			 idSolicitud = evento.getTurno().getSolicitud().getIdSolicitud();
		}
		if (folioFict == null) {
			List l = new ArrayList();
			l.add("El folio asociado es inválido");
			throw new FolioNoCreadoException(l);
		}
		Folio auxFolio = null;
		//Se valida que toda la información del folio sea correcta.
		Folio folioFullSinAnota = null;
		try {
			folioFullSinAnota = forseti.getFolioByID(folioFict.getIdMatricula());
			
			folioFullSinAnota.setIdMatricula(null);
			
			
			if(folioFullSinAnota != null && folioFullSinAnota.getSalvedades() != null
				&& !folioFullSinAnota.getSalvedades().isEmpty()){
				Iterator iteraSalvedades = folioFullSinAnota.getSalvedades().iterator();
				while (iteraSalvedades.hasNext()){
					SalvedadFolio salvedadFolio = (SalvedadFolio)iteraSalvedades.next();
					salvedadFolio.setIdMatricula(null);
				}
			}			
			if(folioFullSinAnota != null && folioFullSinAnota.getDirecciones() != null
				&& !folioFullSinAnota.getDirecciones().isEmpty()){
				Iterator iteraDirecciones = folioFullSinAnota.getDirecciones().iterator();
				while (iteraDirecciones.hasNext()){
					Direccion direccion = (Direccion)iteraDirecciones.next();
					direccion.setIdMatricula(null);
				}
			}
			
			
			if(folioFullSinAnota == null || folioFullSinAnota.getTurnosFolios() != null){
				TurnoFolio tFolio = new TurnoFolio();
				tFolio.setTurno(evento.getTurno());
				folioFullSinAnota.addTurnosFolio(tFolio);
				
			}
					
					
					
			if(folioFullSinAnota != null && folioFullSinAnota.getHistorialEstados() != null
				&& !folioFullSinAnota.getHistorialEstados().isEmpty()){
				Iterator iteraEstadosHisto = folioFullSinAnota.getHistorialEstados().iterator();
				while (iteraEstadosHisto.hasNext()){
					EstadoHistoria estadoHistoria = (EstadoHistoria)iteraEstadosHisto.next();
					estadoHistoria.setIdMatricula(null);
				}
			}
			
			
			List lstAnotaciones = forseti.getAnotacionesFolioTMP(new FolioPk(folioFict.getIdMatricula()));
			Iterator iteralstAnota = lstAnotaciones.iterator();
								
			/* validaciones para las anotaciones del nuevo folio a guardar
			 * */
			while (iteralstAnota.hasNext()){
				Anotacion anotacion = (Anotacion)iteralstAnota.next();
				
				List lstFolioDerivado = forseti.getFolioDerivePadre(anotacion.getIdMatricula(), anotacion.getIdAnotacion());
				
				if(anotacion != null && lstFolioDerivado != null
					&& !lstFolioDerivado.isEmpty()){
					Iterator iteralstFolioDerive = lstFolioDerivado.iterator();
					while (iteralstFolioDerive.hasNext()){
						FolioDerivado folioDerivado = (FolioDerivado)iteralstFolioDerive.next();
						folioDerivado.setIdMatricula1(null);
						anotacion.addAnotacionesPadre(folioDerivado);
					}
				}
				
				anotacion.setIdMatricula(null);
				if(anotacion != null && anotacion.getAnotacionesCancelacions() != null
					&& !anotacion.getAnotacionesCancelacions().isEmpty()){
					Iterator iteralstAnotaCancel = anotacion.getAnotacionesCancelacions().iterator();
					while (iteralstAnotaCancel.hasNext()){
						Cancelacion cancelacion = (Cancelacion)iteralstAnotaCancel.next();
						cancelacion.setIdMatricula(null);
					}
				}
				if(anotacion != null && anotacion.getAnotacionesCiudadanos() != null
					&& !anotacion.getAnotacionesCiudadanos().isEmpty()){
					Iterator iteralstAnotaCiudad = anotacion.getAnotacionesCiudadanos().iterator();
					while (iteralstAnotaCiudad.hasNext()){
						AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano)iteralstAnotaCiudad.next();
						anotacionCiudadano.setIdMatricula(null);
					}
				}
				if(anotacion != null && anotacion.getAnotacionesHijos() != null
					&& !anotacion.getAnotacionesHijos().isEmpty()){
					Iterator iteralstAnotaHijo = anotacion.getAnotacionesHijos().iterator();
					while (iteralstAnotaHijo.hasNext()){
						FolioDerivado flDerivado = (FolioDerivado)iteralstAnotaHijo.next();
						flDerivado.setIdMatricula(null);
					}
				}
				if(anotacion != null && anotacion.getAnotacionesPadre() != null
					&& !anotacion.getAnotacionesPadre().isEmpty()){
					Iterator iteralstAnotaPadre = anotacion.getAnotacionesPadre().iterator();
					while (iteralstAnotaPadre.hasNext()){
						FolioDerivado flDerivado = (FolioDerivado)iteralstAnotaPadre.next();
						flDerivado.setIdMatricula1(null);
					}
				}
				if(anotacion != null && anotacion.getSalvedades() != null
					&& !anotacion.getSalvedades().isEmpty()){
					Iterator iteralstSalvedades = anotacion.getSalvedades().iterator();
					while (iteralstSalvedades.hasNext()){
						SalvedadAnotacion salveadAnotacion = (SalvedadAnotacion)iteralstSalvedades.next();
						salveadAnotacion.setIdMatricula(null);
					}
				}
			}
			
			folioFullSinAnota.setAnotaciones(lstAnotaciones);
			
			this.forseti.validarCrearFolio(folioFullSinAnota, true);

		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			throw new FolioNoCreadoException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"No se ha podido validar el nuevo folio " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		//Si la información del folio es válida se crea el folio.
		//Usuario usuarioSIR = evento.getUsuarioSir();

		try {
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
                                                                }
			this.forseti.crearFolioSegeng(folioFullSinAnota, usuarioSIR, oidTurno, true,folioFict);
		} catch (ForsetiException fe) {
			List l = null;
			l = fe.getErrores();
			if(l.size() > 0){
				throw new FolioNoCreadoException(l);
			}
			if(fe.getHashErrores() != null && !fe.getHashErrores().isEmpty()){
				throw new ValidacionParametrosHTException(fe.getHashErrores());
			}
			if(fe.getMessage() != null){
				FolioNoCreadoException fne = new FolioNoCreadoException(l);
				fne.addError(fe.getMessage() + ", " +  fe.getCause().getMessage());
				throw fne;
			}
			
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"No se ha podido crear el folio " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		return folioFullSinAnota;

	}	





        // DEPURADO ENERO 17 2006
        /**
         * Envio de folio con cambios temporales al revisor
         * para que de el visto bueno.
         * @param evento
         * @return
         * @throws EventoException
         */

        private EventoRespuesta
        ejecutarCorreccionSimple_EnviarARevisarAprobar( EvnCorreccion evento )
        throws EventoException {


              //1. Obtener el listado de parámetros desde el evento de correcciones.
              EvnRespCorreccion evRespuesta = null;
              Fase fase = evento.getFase();
              Turno turno = evento.getTurno();

              Solicitud solicitud = turno.getSolicitud();

              gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

              List folios = null;

              if( null == solicitud ) {
                      throw new ANCorreccionException("No existe solicitud asociada");
              }


              // Bug 0003543
              // validacion para que no se permita dejar el orden repetido

              Solicitud local_Solicitud;
              // List< Folio >
              List local_SolicitudFoliosList;


              local_Solicitud = solicitud;
              local_SolicitudFoliosList = solicitud.getSolicitudFolios();
              SolicitudFolio local_ElementSolicitudFolio;
              FolioPk local_ElementFolioId;

              gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException exception;
              exception = new gov.sir.core.negocio.acciones.excepciones.NegocioCorreccionesCorreccionSimpleCollectorException();



              // para cada uno de los folios en el turno:
              Iterator local_SolicitudFoliosListIterator;
              local_SolicitudFoliosListIterator = local_SolicitudFoliosList.iterator();
              for( ;local_SolicitudFoliosListIterator.hasNext(); ) {
                 local_ElementSolicitudFolio = (SolicitudFolio)local_SolicitudFoliosListIterator.next();
                 local_ElementFolioId = new FolioPk();

                 local_ElementFolioId.idMatricula     = local_ElementSolicitudFolio.getIdMatricula();
                 try {
                  forseti.validarFolioTieneAnotacionesconOrdenRepetido( local_ElementFolioId );
                }
                catch( Throwable e ) {
                  exception.addError(" validacion: " + e.getMessage() );
                }

              } // for

              // raise if necessary
              if( exception.getErrores().size() > 0  ) {
                  throw exception;
              }




			// 2. Se valida que no sea necesario exigir una nota informativa para permitir el avance
			//del turno desde esta fase.
			try {
					 Hashtable tablaAvance = new Hashtable(2);
					 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
					 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
					 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
			}
			catch(Throwable t)
			{
				  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}


              // se deja este bloque comentado
              // por si se necesita la generacion de algun informe en este punto.
              // los cambios ya han sido observados pero queda la duda si se necesita
              // un impreso aparte del que se hará en la revisión / aprobación.

              /*

              //SE CREA UN IDENTIFICADOR DEL TURNO
              Turno.ID turnoID = new Turno.ID();
              turnoID.idCirculo = turno.getIdCirculo();
              turnoID.idProceso = turno.getIdProceso();
              turnoID.idTurno = turno.getIdTurno();
              turnoID.anio = turno.getAnio();

              //SE CARGA LA LISTA DE LOS IDENTIFICADORES DE LOS FOLIOS INVOLUCRADOS EN LA CORRECCIÓN.
              List solFolio = solicitud.getSolicitudFolios();
              List foliosID = new Vector();
              Iterator itSolFolio = solFolio.iterator();
              while (itSolFolio.hasNext()) {
                      SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
                      Folio.ID folioID = new Folio.ID();
                      folioID.idMatricula = sol.getFolio().getIdMatricula();
                      folioID.idZonaRegistral = sol.getFolio().getIdZonaRegistral();
                      foliosID.add(folioID);
              }

              //SE CARGA LA LISTA DE FOLIOS QUE SUFRIERON ALGUNA MODIFICACIÓN
              try {
                      folios = forseti.getDeltaFolios(turnoID, usr);
              } catch (ForsetiException e) {
                      throw new EventoException(e.getMessage(), e);
              } catch (Throwable e) {
                      ExceptionPrinter printer = new ExceptionPrinter(e);
                      Log.getInstance().error(ANCorreccion.class,"Error trayendo los folios que fueron cambiados. " + printer.toString());
                      throw new EventoException(e.getMessage(), e);
              }

              //COLOCARLE AL TURNO LA LISTA DE FOLIOS QUE FUERON OBJETO DE MODIFICACIÓN
              Solicitud solic = new Solicitud();
              solic = turno.getSolicitud();
              solic.setSolicitudFolios(folios);
              turno.setSolicitud(solic);
              */

                      /*

                      //IMPRIMIR EL FORMULARIO DE CORRECCIONES.
                      ImprimibleFormulario imprimible = new ImprimibleFormulario(turno, evento.getUsuarioSIR().getUsername(), CTipoFormulario.TIPO_CORRECCION);
                      Bundle bundle = new Bundle(imprimible);

                      int numIntentos = 10;
                      int espera = 200;
                      for (int i = 0; i < numIntentos; i++) {
                              try {
                                      Thread.sleep(espera);
                                      printJobs.enviar(evento.getUID(), bundle);
                                      break;
                              } catch (Throwable t) {
                                      t.printStackTrace();
                                      if (i == (numIntentos - 1)) {
                                              throw new EventoException(t.getMessage(), t);
                                      }
                              }
                      }

                      //HACER DEFINITIVOS LOS CAMBIOS
                      while (itID.hasNext()) {
                              Folio.ID folioID = (Folio.ID) itID.next();
                              try {
                                      Folio folio = new Folio();
                                      folio.setIdMatricula(folioID.idMatricula);
                                      folio.setIdZonaRegistral(folioID.idZonaRegistral);
                                      forseti.hacerDefinitivoFolio(folio, usr, turnoID);
                              } catch (HermodException e) {
                                      ExceptionPrinter printer = new ExceptionPrinter(e);
                                      Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
                                      throw new EventoException(e.getMessage(), e);
                              } catch (Throwable e) {
                                      ExceptionPrinter printer = new ExceptionPrinter(e);
                                      Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
                                      throw new EventoException(e.getMessage(), e);
                              }
                      }*/
                      /* El servicio de hacer definitivo desbloquea los folios
                      //DESBLOQUEAR LOS FOLIOS
                      try {
                              forseti.desbloquearFolios(turnoID, usr);
                      } catch (ForsetiException e) {
                              throw new EventoException(e.getMessage(), e);
                      } catch (Throwable e) {
                              ExceptionPrinter printer = new ExceptionPrinter(e);
                              Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada desbloqueadno los folios. " + printer.toString());
                              throw new EventoException(e.getMessage(), e);
                      }*/


              // AVANZAR EL TURNO DE CORRECCIONES A LA FASE DE REVISION-APROBACION.

              String respuestaWf = null;

              turno       = evento.getTurno();
              fase        = evento.getFase();
              respuestaWf = EvnCorreccion.CONFIRMAR_WF;

              String USUARIO_INICIADOR = evento.getUsuarioSIR().getUsername();

              Hashtable parameters = new Hashtable();
              parameters.put( Processor.RESULT            , respuestaWf );
              parameters.put( CInfoUsuario.USUARIO_SIR    , USUARIO_INICIADOR );

              try {

					SolicitudPk sid = new SolicitudPk();
					sid.idSolicitud = solicitud.getIdSolicitud();
					List segEngTemporales =  hermod.getWebSegEngBySolicitud(sid);
					if(segEngTemporales!=null){
						Iterator itSegEng = segEngTemporales.iterator();
						while(itSegEng.hasNext()){
							WebSegEng webSegEng = (WebSegEng)itSegEng.next();
							WebSegEngPk wid = new WebSegEngPk();
							wid.idSolicitud = webSegEng.getIdSolicitud();
							wid.idWebSegeng = webSegEng.getIdWebSegeng();
							hermod.eliminarWebSegEng(wid);
						}
					}

					
					
					//BUG 3962 Si hay más de un responsable de correcciones en la oficina
					//el turno debe ser enviado a la parsona que realizò la fase de revisión
					//y análisis.

					
					//1. Obtener el usuario que realizó la fase de revisión y análisis.

                    List listaHistorials = turno.getHistorials();
                    String estacionRevisionInicial = null;
                    if (listaHistorials != null)
                    {
                    	for (int hist=(listaHistorials.size()-1); hist >= 0; hist--)
                    	{
                    		TurnoHistoria turnoHistoriaTemp = (TurnoHistoria)listaHistorials.get(hist);
                    		if (turnoHistoriaTemp != null)
                    		{
                    			if (turnoHistoriaTemp.getFase().equals(CFase.COR_REVISION_ANALISIS))
                    			{
                    				estacionRevisionInicial = turnoHistoriaTemp.getIdAdministradorSAS();
                    				Log.getInstance().debug(ANCorreccion.class,"Administrador SAS: "+estacionRevisionInicial);
                    				break;
                    			}
                    		
                    			
                    		}
                    	}
                    } 
                    
                    //Se envía el parámetro de avance hacia esta estación. (Si se recuperó esta estación)
                    //Se colocó la marca CONDICION_AVANCE, para no afectar los avances de otros procesos
                    //en la clase JDOTurnosNuevosDAO, (Se afectaban otros avances).
                    
                    if (estacionRevisionInicial != null)
                    {
                    	parameters.put(Processor.ESTACION,estacionRevisionInicial);
                    	parameters.put(Processor.CONDICION_AVANCE,CFase.COR_REVISION_ANALISIS);
                    }
                    
					
					
                    hermod.avanzarTurnoNuevoCualquiera( turno, fase, parameters, evento.getUsuarioSIR() );
              }
              catch( HermodException e ) {
                      throw new ANCorreccionException("No se pudo APROBAR la solicitud de corrección." +
                      	                               "No fue posible avanzar el turno.", e);
              }
              catch (Throwable e) {
				throw new ANCorreccionException("No se pudo APROBAR la solicitud de corrección." +
												"No fue posible avanzar el turno.", e);
              }

              evRespuesta = new EvnRespCorreccion( evento.getUsuario(), turno, EvnRespCorreccion.ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION__EVENTRESP_OK );

              return evRespuesta;
      }




// -----------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------
      private EventoRespuesta
      processCorreccionSimple_EliminarCambiosAnotacionDefinitivaConCambiosTemporales( EvnCorreccion evento )
      throws EventoException {

          return null;

      /*

          // unwrap ---------------------------------
          gov.sir.core.negocio.modelo.Usuario   local_ParamUsuarioSir;
          gov.sir.core.negocio.modelo.Turno     local_ParamTurno;
          gov.sir.core.negocio.modelo.Folio     local_ParamFolio;
          gov.sir.core.negocio.modelo.Anotacion local_ParamAnotacion;


          // get params
          local_ParamUsuarioSir = evento.getUsuarioSir();
          local_ParamTurno      = evento.getTurno();
          local_ParamFolio      = evento.getFolio();
          local_ParamAnotacion  = evento.getAnotacion();
          // -----------------------------------------

          // process ---------------------------------
          //------------------------------------------------------------------------

          try {

             forseti.eliminarDeltaSegunAnotacionDefinitiva( local_ParamFolio, local_ParamAnotacion, local_ParamUsuarioSir );
          }
          catch( Throwable e ) {
             // TODO: modificar (no deberia ser throwable)
             throw new NegocioCorreccionesCorreccionSimpleException( "No se pudieron eliminar los cambios", e );

          }


          // get data for update
          // @ see AnCalificacion.eliminarAnotacionTemporal

          Folio devuelve=null;
          List listaATemp=null;

          Usuario usuarioNeg= local_ParamUsuarioSir;
          Folio nuevo=new Folio();
          Turno turno = local_ParamTurno;

          nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
          nuevo.setIdZonaRegistral(evento.getFolio().getIdZonaRegistral());
          Anotacion anotacion= evento.getAnotacion();
          nuevo.addAnotacione(anotacion);
          try {
                  Folio.ID id=new Folio.ID();
                  id.idMatricula=evento.getFolio().getIdMatricula();
                  id.idZonaRegistral=evento.getFolio().getIdZonaRegistral();
                  devuelve = forseti.getFolioByIDSinAnotaciones(id);
                  listaATemp= forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg);

                          Turno.ID idTurno = new Turno.ID();
                          idTurno.anio = turno.getAnio();
                          idTurno.idCirculo = turno.getIdCirculo();
                          idTurno.idProceso = turno.getIdProceso();
                          idTurno.idTurno = turno.getIdTurno();

                          turno = hermod.getTurno(idTurno);

          }
          catch (Throwable e1) {
                  ExceptionPrinter ep=new ExceptionPrinter(e1);
                  Log.getInstance().error(ANCorreccion.class,"No se pudo eliminar la anotacion:"+ep.toString());
                  throw new NegocioCorreccionesCorreccionSimpleException("No se pudo eliminar la anotacion",e1);
          }


          //SE CONSULTA EL FOLIO PARA MOSTRAR LA INFORMACIÓN DEL MISMO
          try {
                  if(nuevo!=null && nuevo.getIdMatricula()!=null){

                          Folio.ID id=new Folio.ID();
                          id.idMatricula=nuevo.getIdMatricula();
                          id.idZonaRegistral=forseti.getZonaRegistral(nuevo.getIdMatricula());

                          nuevo = forseti.getFolioByIDSinAnotaciones(id,usuarioNeg);
                          Folio folioDef = forseti.getFolioByIDSinAnotaciones(id);
                          //forseti.getDatosDefinitivosDeDatosTemporales(id,usuarioNeg);

                          boolean esMayorExtension = forseti.mayorExtensionFolio(nuevo.getIdMatricula());

                          long numeroAnotaciones = forseti.getCountAnotacionesFolio(nuevo.getIdMatricula());

                          List foliosHijos = forseti.getFoliosHijos(id,usuarioNeg);
                          List foliosPadre = forseti.getFoliosPadre(id,usuarioNeg);

                          long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.GRAVAMEN);
                          List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN,0, (int)numeroGravamenes,usuarioNeg,true);

                          long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
                          List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR,0, (int)numeroMedidasCautelares,usuarioNeg,true);

                          long numeroFalsaTradicion = 0;
                          List falsaTradicion = null;
                          List ordenFalsaTradicion = new ArrayList();
                          List anotacionesInvalidas = null;
                          List ordenAnotacionesInvalidas = new ArrayList();

                          //CONSULTA ANOTACIONES DE FALSA TRADICION
                          numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
                          falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, usuarioNeg, true);
                          if(falsaTradicion != null){
                                  Iterator it = falsaTradicion.iterator();
                                  while(it.hasNext()){
                                          Anotacion anotTemp = (Anotacion)it.next();
                                          ordenFalsaTradicion.add(anotTemp.getOrden());
                                  }
                          }

                          //CONSULTA ANOTACIONES INVALIDAS
                          anotacionesInvalidas = forseti.getAnotacionesInvalidas(id, usuarioNeg);
                          if(anotacionesInvalidas != null){
                                  Iterator it = anotacionesInvalidas.iterator();
                                  while(it.hasNext()){
                                          Anotacion anotTemp = (Anotacion)it.next();
                                          ordenAnotacionesInvalidas.add(anotTemp.getOrden());
                                  }
                          }

                          List salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, usuarioNeg);
                          List cancelaciones = forseti.getAnotacionesConCancelaciones(id, usuarioNeg);

                          String linderoTemporal = "";
                          if(nuevo!=null && nuevo.getLindero()!=null && !nuevo.getLindero().equals("")){
                                  if(folioDef!=null){
                                          if(folioDef.getLindero()!=null){
                                                  linderoTemporal = nuevo.getLindero().substring(folioDef.getLindero().length(),nuevo.getLindero().length());
                                          }else{
                                                  linderoTemporal = nuevo.getLindero();
                                          }
                                  }else{
                                          linderoTemporal = nuevo.getLindero();
                                  }
                          }

                          EvnRespCalificacion evnResp = new EvnRespCalificacion (nuevo, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion , ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, listaATemp, linderoTemporal);
                          evnResp.setTurno(turno);
                          return evnResp;
                  }
                  throw new EventoException();
          }
          catch (Throwable e){
                  ExceptionPrinter printer = new ExceptionPrinter(e);
                  Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
                  throw new EventoException(e.getMessage(),e);
          }

          //-----------------------------------------------------------------------------------------

          //
          // Se usa el mismo evento de respuesta de la
          // eliminacion de una anotacion temporal pare el refresh de
          // la pantalla.
          //
          // // -----------------------------------------
          //
          // // wrap ------------------------------------
          // EvnRespCorreccion local_Result;
          // local_Result = new EvnRespCorreccion( EvnRespCorreccion.ELIMINARCAMBIOSANOTACIONDEFINITIVATEMPORAL_EVENTRESPOK );
          //
          // // set results
          //
          // // -----------------------------------------
          // // send-message ----------------------------
          // return local_Result;
          // // -----------------------------------------
*/



      } // end-method: processCorreccionSimple_EliminarCambiosAnotacionDefinitivaConCambiosTemporales

// -----------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------


        
             






            /**
     * Guarda los cambios realizados en la actuación administrativa y avanza.
     *
     * @param evento
     * @return
     * @throws EventoException
     */
    /**
     * @autor: Edgar Lora @mantis: 0011623 @requerimiento: 028_453
     */
    /*
     * @autor : HGOMEZ @mantis : 11631 @Requerimiento :
     * 036_453_Correccion_Actuaciones_Administrativas 
     * @descripcion : Modificaciones generales al método para.
     */
    private EventoRespuesta terminarEjecucionActuacionAdministrativa(EvnCorreccion evento) throws EventoException {

        //1. OBTENER PARAMETROS DESDE EL EVENTO
        EvnRespCorreccion evRespuesta = null;
        Fase fase = evento.getFase();
        Turno turno = evento.getTurno();

        Turno turnoTemp = (Turno) copy(turno);
        /**
         * Se usa un temporal para no perder la información original
         */
        Solicitud solicitud = turno.getSolicitud();
        gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
        List folios = null;

        if (null == solicitud) {
            throw new ANCorreccionException("No existe solicitud asociada");
        }

        // Bug 0003543
        // validacion para que no se permita dejar el orden repetido
        Solicitud local_Solicitud;
        List local_SolicitudFoliosList;

        local_Solicitud = solicitud;
        local_SolicitudFoliosList = solicitud.getSolicitudFolios();
        SolicitudFolio local_ElementSolicitudFolio;
        FolioPk local_ElementFolioId;

        gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException exception;
        exception = new gov.sir.core.negocio.acciones.excepciones.NegocioCorreccionesCorreccionSimpleCollectorException();

        // para cada uno de los folios en el turno:
        Iterator local_SolicitudFoliosListIterator;
        local_SolicitudFoliosListIterator = local_SolicitudFoliosList.iterator();
        for (; local_SolicitudFoliosListIterator.hasNext();) {
            local_ElementSolicitudFolio = (SolicitudFolio) local_SolicitudFoliosListIterator.next();
            local_ElementFolioId = new FolioPk();

            local_ElementFolioId.idMatricula = local_ElementSolicitudFolio.getIdMatricula();
            try {
                forseti.validarFolioTieneAnotacionesconOrdenRepetido(local_ElementFolioId);
            } catch (Throwable e) {
                exception.addError(" validacion: " + e.getMessage());
            }

        }

        //HACER DEFINITIVO LOS CAMBIOS.
        TurnoPk turnoID = new TurnoPk();
        turnoID.idCirculo = turno.getIdCirculo();
        turnoID.idProceso = turno.getIdProceso();
        turnoID.idTurno = turno.getIdTurno();
        turnoID.anio = turno.getAnio();

        try {
            folios = forseti.getDeltaFolios(turnoID, usr);
            Hashtable tablaAvance = new Hashtable(2);
            tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
            tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

            if (folios.size() <= 0) {
                evento.setRespuestaWF("SIN_DATOS");
                tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
            }

            hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
        } catch (Throwable t) {
            exception.addError("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
        }

        // 2. SE VALIDA SI ES NECESARIO AGREGAR NOTAS INFORMATIVAS
//        try {
//            Hashtable tablaAvance = new Hashtable(2);
//            tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
//            tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
//            hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
//        } catch (Throwable t) {
//            throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
//        }

        //EN ESTE PUNTO PODRÍA SER NECESARIO AGREGAR LA IMPRESIÓN DE LAS DIFERENCIAS,
        //PARA ESTO VER LA FUNCIÓN ejecutarCorreccionSimple_EnviarARevisarAprobar

        if (exception.getErrores().size() > 0) {
            throw exception;
        }

        // LISTA DE LOS IDENTIFICADORES DE LOS FOLIOS INVOLUCRADOS EN LA CORRECCIÓN.
        List solFolio = solicitud.getSolicitudFolios();
        List foliosID = new Vector();
        Iterator itSolFolio = solFolio.iterator();
        while (itSolFolio.hasNext()) {
            SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
            FolioPk folioID = new FolioPk();
            folioID.idMatricula = sol.getFolio().getIdMatricula();
            foliosID.add(folioID);
        }

        // SE IMPRIME EL FORMULARIO, SE HACEN DEFINITIVOS LOS CAMBIOS
        // Y SE DESBLOQUEAN LOS FOLIOS.


        List t0_ListSolicitudFolio;
        List t2_ListSolicitudFolio; // Lista de solicitudes folio


        //SE CARGA LA LISTA DE FOLIOS QUE SUFRIERON ALGUNA MODIFICACIÓN
        try {


            // Luego de obtener los delta,
            // Construir un objeto folio tomando como guia los cambios-delta
            // --------------------------------------------------------------
            SolicitudFolio t0_SolicitudFolio;     // elemento de la lista
            Folio t0_Folio;              // folio a enviar a imprimir
            List t0_Folio_ListAnotacion;
            Anotacion t0_Folio_Anotacion;

            // initialize -
            t0_ListSolicitudFolio = folios;
            // --------------------------------------------------------------
            SolicitudFolio t2_SolicitudFolio;     // elemento de la lista
            Folio t2_Folio;              // folio a enviar a imprimir
            FolioPk t2_Folio_Id;           // id de folio
            List t2_Folio_ListAnotacion;
            Anotacion t2_Folio_Anotacion;
            // --------------------------------------------------------------
            // initialize -
            t2_ListSolicitudFolio = new ArrayList();

            // --------------------------------------------------------------



            Iterator t0_ListSolicitudFolioIterator = t0_ListSolicitudFolio.iterator();

            for (; t0_ListSolicitudFolioIterator.hasNext();) {

                // Consultar los datos anteriores

                t0_SolicitudFolio = (SolicitudFolio) t0_ListSolicitudFolioIterator.next();
                t0_Folio = t0_SolicitudFolio.getFolio();

                t2_Folio_Id = new FolioPk();
                t2_Folio_Id.idMatricula = t0_Folio.getIdMatricula();

                t2_Folio = forseti.getFolioByIDSinAnotaciones(t2_Folio_Id, usr);


                // filtros

                // 1: complementacion
                if (null == t0_Folio.getComplementacion()) {
                    t2_Folio.setComplementacion(null);
                }
                // 2: lindero
                if (null == t0_Folio.getLindero()) {
                    t2_Folio.setLindero(null);
                }
                // 3: estado
                if (null == t0_Folio.getEstado()) {
                    t2_Folio.setEstado(null);
                }

                // recorrer los cambios en anotaciones y crear la lista de anotaciones nueva
                t2_Folio_ListAnotacion = new ArrayList();
                t0_Folio_ListAnotacion = t0_Folio.getAnotaciones();

                Iterator t0_Folio_ListAnotacionIterator;
                t0_Folio_ListAnotacion = filter_SortAnotacionesByOrden(t0_Folio_ListAnotacion);

                t0_Folio_ListAnotacionIterator = t0_Folio_ListAnotacion.iterator();

                for (; t0_Folio_ListAnotacionIterator.hasNext();) {

                    t0_Folio_Anotacion = (Anotacion) t0_Folio_ListAnotacionIterator.next();

                    t2_Folio_Anotacion = forseti_GetFolioAnotacion(t2_Folio_Id, t0_Folio_Anotacion, usr);

                    // para filtrar anotaciones link
                    if (null == t2_Folio_Anotacion) {
                        continue;
                    }

                    // eliminar las salvedades originales, colocar solo las de los delta
                    t2_Folio_Anotacion.setSalvedades(null);

                    List t0_Folio_Anotacion_SalvedadList = t0_Folio_Anotacion.getSalvedades();

                    // Bug 3565 ---------------------------
                    List local_TargetSalvedadesList; // TODO: externalize

                    local_TargetSalvedadesList = jxSearch_ExtractSalvedadesByTurno(
                            t0_Folio_Anotacion_SalvedadList, turno.getIdWorkflow());

                    // fix the results
                    t2_Folio_Anotacion.setSalvedades(local_TargetSalvedadesList);
                    // -----------------------------------

                            try
                            {
                                    //ordenar las anotaciones por numero
                                    /**
                                    * @autor:Edgar Lora
                                    * @mantis: 0011599
                                    * @requerimiento: 085_151
                                    */
                                    Collections.sort(t2_Folio_ListAnotacion, new ComparadorAnotaciones());
                            }
                            catch (ClassCastException e)
                            {
                                    Log.getInstance().debug(ANCorreccion.class,"@@@No fue posible ordenar las anotaciones por orden LPAD");
                                    Log.getInstance().error(ANCorreccion.class,e);
                            }
                    /*
                     *
                     * for( Iterator t0_Folio_Anotacion_SalvedadListIterator =
                     * t0_Folio_Anotacion_SalvedadList.iterator();
                     * t0_Folio_Anotacion_SalvedadListIterator.hasNext(); ){
                     * SalvedadAnotacion localSalvedadAnotacion =
                     * (SalvedadAnotacion)t0_Folio_Anotacion_SalvedadListIterator.next();
                     * // Bug 03565: filtrar a mano las salvedades del folio
                     * localSalvedadAnotacion.setNumRadicacion(
                     * turnoID.toString() ); }
                     *
                     * t2_Folio_Anotacion.setSalvedades(
                     * t0_Folio_Anotacion_SalvedadList );
                     */
                    /**
                     * Si las anotaciones las realizo el usuario con otro rol no
                     * debe ser impresas
                     */
                    if (t2_Folio_Anotacion.getIdWorkflow() == null || t2_Folio_Anotacion.getIdWorkflow().equals("")
                            || t2_Folio_Anotacion.getIdWorkflow().equals(turno.getIdWorkflow())) {
                        t2_Folio_ListAnotacion.add(t2_Folio_Anotacion);
                    }
                }

                try {
                    //ordenar las anotaciones por numero
                    /**
                    * @autor:Edgar Lora
                    * @mantis: 0011599
                    * @requerimiento: 085_151
                    */
                    Collections.sort(t2_Folio_ListAnotacion, new ComparadorAnotaciones());
                } catch (ClassCastException e) {
                    Log.getInstance().debug(ANCorreccion.class, "@@@No fue posible ordenar las anotaciones por orden LPAD");
                    Log.getInstance().error(ANCorreccion.class, e);
                }

                // incluir las anotaciones al folio
                t2_Folio.setAnotaciones(t2_Folio_ListAnotacion);


                // Bug 03565
                // observar como estan llegando las salvedades de folio:
                // el servicio getFolioByIdSinAnotaciones
                // regresa las salvedades en temporal.

                //  Se debe tener en cuenta filtrar por el id del turno



                // // eliminar las salvedades originales, colocar solo las de los delta
                // t2_Folio.setSalvedades( null );

                // List t0_Folio_SalvedadList =  t0_Folio.getSalvedades();

                // for( Iterator t0_Folio_SalvedadListIterator = t0_Folio_SalvedadList.iterator(); t0_Folio_SalvedadListIterator.hasNext(); ){
                //   SalvedadFolio localSalvedadFolio = (SalvedadFolio)t0_Folio_SalvedadListIterator.next();

                //  // Bug 03565
                //  localSalvedadFolio.setNumRadicacion( turnoID.toString() );
                //}
                // t2_Folio.setSalvedades( t0_Folio_SalvedadList );

                // -----------------------------------------------------------------
                // ...... target variables
                List local_TargetFolioSalvedadesList;

                local_TargetFolioSalvedadesList = jxSearch_ExtractSalvedadesByTurno(
                        t2_Folio.getSalvedades(), turno.getIdWorkflow());

                // fix the results
                t2_Folio.setSalvedades(local_TargetFolioSalvedadesList);
                // -----------------------------------------------------------------

                // Construir el nuevo objeto y adicionarlo
                t2_SolicitudFolio = new SolicitudFolio();
                t2_SolicitudFolio.setFolio(t2_Folio);
                t2_ListSolicitudFolio.add(t2_SolicitudFolio);
            } // for


        } catch (ForsetiException e) {
            Log.getInstance().error(ANCorreccion.class, e);
            throw new EventoException(e.getMessage(), e);
        } catch (Exception e) {
            Log.getInstance().error(ANCorreccion.class, e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANCorreccion.class, e);
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANCorreccion.class, "Error trayendo los folios que fueron cambiados. " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        //COLOCARLE AL TURNO LA LISTA DE FOLIOS QUE FUERON OBJETO DE MODIFICACIÓN
        Solicitud solic = new Solicitud();
        solic = turnoTemp.getSolicitud();
        // solic.setSolicitudFolios(folios);
        solic.setSolicitudFolios(t2_ListSolicitudFolio);
        turnoTemp.setSolicitud(solic);

        // realizar busqueda de usuario
        String formularioCorrecciones_UsuarioRealizaCorreccion = "";
        Long formularioCorrecciones_UsuarioRealizaCorreccionId = null;
        boolean founded_FormularioCorrecciones_UsuarioRealizaCorreccion;
        boolean isImpresionTemporalDeAuxiliarEnabled = evento.isImpresionTemporalDeAuxiliarEnabled();

        founded_FormularioCorrecciones_UsuarioRealizaCorreccion = false;

        if (!founded_FormularioCorrecciones_UsuarioRealizaCorreccion) {

            if (isImpresionTemporalDeAuxiliarEnabled) {
                gov.sir.core.negocio.modelo.Usuario local_User = usr;
                formularioCorrecciones_UsuarioRealizaCorreccionId = Nvl(new Long(local_User.getIdUsuario()), getNullableLong(true));
                founded_FormularioCorrecciones_UsuarioRealizaCorreccion = true;
            } // if

        } // if

        if (!founded_FormularioCorrecciones_UsuarioRealizaCorreccion) {

            local_SearchImpl_jx:
            {

                // :: local variables ----------------------------------------------
                Turno local_Turno;
                JXPathContext local_TurnoSearchContext;

                // // List< TurnoHistoria >
                List local_TurnoHistoria;
                Iterator local_TurnoSearchContextIterator;

                final String SEARCHED__FASE_CORRECCIONES_CORRECCIONSIMPLE = "COR_CORRECCION_SIMPLE";

                Object local_FoundedValue; // elemento encontrado

                // initialize context & variables
                local_Turno = turno;
                local_TurnoHistoria = local_Turno.getHistorials(); // por el momento se observa el registro historico que tiene el turno
                local_TurnoSearchContext = jx_GetContext(local_TurnoHistoria);

                // evaluateNextRule = true;
                // local_TipoCorreccion = null;

                // declare jxpath variables
                local_TurnoSearchContext.getVariables().declareVariable("local_FaseId", "");

                // -----------------------------------------------------------------
                // simple queries
                // apply first

                // ruleA: search-rule:
                //  buscar en el historial
                //  el usuario que realizo la correccion simple en ultimo lugar
                //String searchRule = " .[ (@fase = $local_FaseId ) ]/usuarioAtiende/username[last()]";
                String searchRule = " .[ (@fase = $local_FaseId ) ]/usuarioAtiende/idUsuario[last()]";

                local_TurnoSearchContext.setValue("$local_FaseId", SEARCHED__FASE_CORRECCIONES_CORRECCIONSIMPLE);

                local_FoundedValue = local_TurnoSearchContext.getValue(searchRule);

                //formularioCorrecciones_UsuarioRealizaCorreccion = Nvl( (String)local_FoundedValue, getNullableString( true ) );
                formularioCorrecciones_UsuarioRealizaCorreccionId = Nvl((Long) local_FoundedValue, getNullableLong(true));

            } // :searchImpl_jx

        } // if


        //TODO: depurar el objeto que se envia al imprimible


        //IMPRIMIR EL FORMULARIO DE CORRECCIONES.
        String fechaImpresion = this.getFechaImpresion();

        ImprimibleFormulario imprimible = null;
        ImprimibleFormulario imprimiblepdf = null;

        imprimible = new ImprimibleFormulario(turnoTemp, evento.getUsuarioSIR().getUsername(), CTipoFormulario.TIPO_CORRECCION, fechaImpresion, CTipoImprimible.FORMULARIO_CORRECCION);
        imprimible.setPrintWatermarkEnabled(true);
        imprimible.setFormularioNoOficialCorreccionesEnabled(isImpresionTemporalDeAuxiliarEnabled);

        imprimiblepdf = new ImprimibleFormulario(turnoTemp, evento.getUsuarioSIR().getUsername(), CTipoFormulario.TIPO_CORRECCION, fechaImpresion, CTipoImprimible.FORMULARIO_CORRECCION);
        imprimiblepdf.setPrintWatermarkEnabled(true);
        imprimiblepdf.setFormularioNoOficialCorreccionesEnabled(isImpresionTemporalDeAuxiliarEnabled);
        imprimiblepdf.setPdf(true);

        String valorIdUsuario = formularioCorrecciones_UsuarioRealizaCorreccionId.toString();

        Bundle bundle = null;
        Bundle bundle2 = null;

        imprimible.setIdUsuario(valorIdUsuario);
        imprimiblepdf.setIdUsuario(valorIdUsuario);
        bundle = new Bundle(imprimible);
        bundle2 = new Bundle(imprimiblepdf);

        bundle.setNumeroCopias(evento.getNumeroCopias());
        bundle2.setNumeroCopias(evento.getNumeroCopias());
        String local_PrintId;

        local_PrintId = evento.getCirculoId();

        //OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
        int maxIntentos;
        int espera;

        //INGRESO DE INTENTOS DE IMPRESION
        try {

            String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
            if (intentosImpresion != null) {
                Integer intentos = new Integer(intentosImpresion);
                maxIntentos = intentos.intValue();
            } else {
                Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
                maxIntentos = intentosDefault.intValue();
            }

            //INGRESO TIEMPO DE ESPERA IMPRESION
            String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
            if (intentosImpresion != null) {
                Integer esperaInt = new Integer(esperaImpresion);
                espera = esperaInt.intValue();
            } else {
                Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
                espera = esperaDefault.intValue();
            }
        } catch (Throwable t) {
            Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
            maxIntentos = intentosDefault.intValue();

            Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
            espera = esperaDefault.intValue();

        }

        //AVANZAR EL TURNO A LA SIGUIENTE FASE.
        String respuestaWf = null;

        turno = evento.getTurno();
        fase = evento.getFase();
        respuestaWf = EvnCorreccion.CONFIRMAR_WF;
        String USUARIO_INICIADOR = evento.getUsuarioSIR().getUsername();

        Hashtable parameters = new Hashtable();
        parameters.put(Processor.RESULT, respuestaWf);
        parameters.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);
        
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
            infoUsuario.put("idTurno",turno.getIdWorkflow());
        }
        co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
        try {
            auditoria.guardarDatosTerminal(infoUsuario);
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        //HACER DEFINITIVOS LOS CAMBIOS
        Iterator itID = foliosID.iterator();
        while (itID.hasNext()) {
            FolioPk folioID = (FolioPk) itID.next();
            try {
                Folio folio = new Folio();
                folio.setIdMatricula(folioID.idMatricula);
                forseti.hacerDefinitivoFolio(folio, usr, turnoID, true);
            } catch (HermodException e) {

                throw new EventoException(e.getMessage(), e);
            } catch (Throwable e) {

                throw new EventoException(e.getMessage(), e);
            }
        }
        /**
            * @Author Carlos Torres
            * @Mantis 13176
            * @Chaged 
            */
        try
        {
            auditoria.borrarDatosTerminal(turno.getIdWorkflow());
        }catch (GeneralSIRException ex) {
            Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            hermod.avanzarTurnoNuevoCualquiera(turno, fase, parameters, evento.getUsuarioSIR());
        } catch (HermodException e) {
            throw new ANCorreccionException("No se pudo APROBAR la actuación administrativa." + "No fue posible avanzar el turno.", e);
        } catch (Throwable e) {
            throw new ANCorreccionException("No se pudo APROBAR la actuación administrativa." + "No fue posible avanzar el turno.", e);
        }

        try {
            Log.getInstance().debug(ANCorreccion.class, "*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo enviar*");
            Log.getInstance().debug(ANCorreccion.class, "*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo enviar*");
            Log.getInstance().debug(ANCorreccion.class, "*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo enviar*");
            /*
             * @autor : HGOMEZ @mantis : 11631 @Requerimiento :
             * 036_453_Correccion_Actuaciones_Administrativas @descripcion : Se
             * comenta la siguiente linea para que no imprima una copia de más.
             */
            //printJobs.enviar( local_PrintId, bundle , maxIntentos, espera);
            Log.getInstance().debug(ANCorreccion.class, "*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo agregarImprimiblePDF*");
            Log.getInstance().debug(ANCorreccion.class, "*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo agregarImprimiblePDF*");
            Log.getInstance().debug(ANCorreccion.class, "*************LOGS IMPRIMIBLE PDF :AnCorrecion Se va a enviar a imprimir con el metodo agregarImprimiblePDF*");
            printJobs.agregarImprimiblePDF(bundle2, !isImpresionTemporalDeAuxiliarEnabled);
            Log.getInstance().debug(ANCorreccion.class, "*************LOGS IMPRIMIBLE PDF :AnCorrecion FIN IMPRESION DEL METODO agregarImprimiblePDF*");
        } catch (Throwable t) {
            t.printStackTrace();
            throw new EventoException(t.getMessage(), t);
        }

        evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA);

        return evRespuesta;
    }


      //DEPURADO ENERO 17 2006
      /**
       * regresar el turno a revision para adicionar mas folios o para cambiar los permisos
       * @param evento
       * @return
       * @throws EventoException
       */

      private EventoRespuesta
      ejecutarCorreccionSimple_EnviarARevisarAnalisis( EvnCorreccion evento )
      throws EventoException {


            //1. Obtención de parámetros desde el evento de correcciones.
            EvnRespCorreccion evRespuesta = null;
            Fase fase = evento.getFase();
            Turno turno = evento.getTurno();

            Solicitud solicitud = turno.getSolicitud();

            gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

            List folios = null;

            if( null == solicitud ) {
                    throw new ANCorreccionException("No existe solicitud asociada");
            }

            // DEVOLVER EL TURNO DE CORRECCIONES A LA FASE DE REVISION-ANALISIS.

            String respuestaWf = null;

            turno       = evento.getTurno();
            fase        = evento.getFase();
            respuestaWf = EvnCorreccion.DEVOLVERAEVISIONANALISIS_WF;


            String USUARIO_INICIADOR = evento.getUsuarioSIR().getUsername();

            Hashtable parameters = new Hashtable();
            parameters.put( Processor.RESULT            , respuestaWf );
            parameters.put( CInfoUsuario.USUARIO_SIR    , USUARIO_INICIADOR );


		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, respuestaWf);
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}
                
            //4. Realizar el avance del turno (Servicio Hermod).
            try {

				SolicitudPk sid = new SolicitudPk();
				sid.idSolicitud = solicitud.getIdSolicitud();
				List segEngTemporales =  hermod.getWebSegEngBySolicitud(sid);
				if(segEngTemporales!=null){
					Iterator itSegEng = segEngTemporales.iterator();
					while(itSegEng.hasNext()){
						WebSegEng webSegEng = (WebSegEng)itSegEng.next();
						WebSegEngPk wid = new WebSegEngPk();
						wid.idSolicitud = webSegEng.getIdSolicitud();
						wid.idWebSegeng = webSegEng.getIdWebSegeng();
						hermod.eliminarWebSegEng(wid);
					}
				}

                hermod.avanzarTurnoNuevoCualquiera( turno, fase, parameters, evento.getUsuarioSIR() );
            }
            catch( HermodException e ) {
                    throw new ANCorreccionException("No se pudo APROBAR la solicitud de corrección" +
                    	                             "No fue posible avanzar el turno. ", e);
            }
            catch (Throwable e) {
				throw new ANCorreccionException("No se pudo APROBAR la solicitud de corrección" +
												 "No fue posible avanzar el turno. ", e);
            }

            evRespuesta = new EvnRespCorreccion( evento.getUsuario(), turno, EvnRespCorreccion.ENVIARCORRECCIONSIMPLEAREVISIONANALISIS__EVENTRESP_OK );

            return evRespuesta;
    }



    //DEPURADO ENERO 17 DE 2006
	/**
	 * Método que aprueba la solicitud de corrección y hace definitivos los cambios que estan en
	 * las tablas temporales.
	 * @param evento
	 * @return
	 * @throws EventoException
     * @deprecated usar ejecutarRevisionAprobacion_AprobarCorreccion si se quiere realizar la aprobacion de correccion;
	 */
	private EventoRespuesta aprobarCorreccion(EvnCorreccion evento) throws EventoException {

		//1. Obtención de parámetros desde el evento de correcciones.
		EvnRespCorreccion evRespuesta = null;
		Fase fase = evento.getFase();
		Turno turno = evento.getTurno();
		Solicitud solicitud = turno.getSolicitud();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
		List folios = null;

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}


		// 2. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		//SE CREA UN IDENTIFICADOR DEL TURNO
		TurnoPk turnoID = new TurnoPk();
		turnoID.idCirculo = turno.getIdCirculo();
		turnoID.idProceso = turno.getIdProceso();
		turnoID.idTurno = turno.getIdTurno();
		turnoID.anio = turno.getAnio();

		//SE CARGA LA LISTA DE LOS IDENTIFICADORES DE LOS FOLIOS INVOLUCRADOS EN LA CORRECCIÓN.
		List solFolio = solicitud.getSolicitudFolios();
		List foliosID = new Vector();
		Iterator itSolFolio = solFolio.iterator();
		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
			FolioPk folioID = new FolioPk();
			folioID.idMatricula = sol.getFolio().getIdMatricula();
			foliosID.add(folioID);
		}

		//SE DETERMINA SI EL FOLIO DEBE IRSE POR MAYOR, EN CASO DE QUE TENGA NUEVAS ANOTACIONES.
		boolean mayorValor = false;
		Iterator itID = foliosID.iterator();

		while (itID.hasNext()) {
			FolioPk folioID = (FolioPk) itID.next();
			try {
				if (forseti.hasAnotacionesTMP(folioID)) {
                                    // TODO: revisar esta condicion;
                                    // cuando se ejecuta una correccion simple,
                                    // ya no puede irse por mayor valor
					mayorValor = true;
				}
			} catch (HermodException e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}
		}

		//SI ES DE MAYOR VALOR SE DETERMINA SI YA PASO POR ALGÚN PAGO POR MAYOR VALOR
		//PARA QUE EN LA CAPA WEB SE LE PREGUNTE AL CORRECTOR SI RELAMENTE LO
		//DESEA VOLVER A MANDAR A PAGO POR MAYOR VALOR, SI NUNCA HA PASADO, AUTOMATICAMENTE
		//EL CASO ES ENVIADO AL PROCESO DE PAGO POR MAYOR VALOR.
		if (mayorValor) {
			boolean pasoPagoMayor = false;
			List historia = turno.getHistorials();
			Iterator it = historia.iterator();

			while (it.hasNext()) {
				TurnoHistoria turnoHistoria = (TurnoHistoria) it.next();
				if (turnoHistoria.getFase() != null && turnoHistoria.getFase().length() >= 3 && turnoHistoria.getFase().substring(0, 3).equals(CFase.PREFIJO_MAYOR_VALOR)) {
					pasoPagoMayor = true;
				}
			}

			if (pasoPagoMayor) {
				evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.PREGUNTAR_APROBACION);
				return evRespuesta;
			}
		}

		//SI NO HAY PAGO POR MAYOR VALOR SE IMPRIME EL FORMULARIO, SE HACEN DEFINITIVOS LOS CAMBIOS
		//Y SE DESBLOQUEAN LOS FOLIOS.
		if (!mayorValor) {
			//SE CARGA LA LISTA DE FOLIOS QUE SUFRIERON ALGUNA MODIFICACIÓN
			try {
				folios = forseti.getDeltaFolios(turnoID, usr);
			} catch (ForsetiException e) {
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Error trayendo los folios que fueron cambiados. " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}

			//COLOCARLE AL TURNO LA LISTA DE FOLIOS QUE FUERON OBJETO DE MODIFICACIÓN
			Solicitud solic = new Solicitud();
			solic = turno.getSolicitud();
			solic.setSolicitudFolios(folios);
			turno.setSolicitud(solic);

			//IMPRIMIR EL FORMULARIO DE CORRECCIONES.
			String fechaImpresion = this.getFechaImpresion();
			ImprimibleFormulario imprimible = new ImprimibleFormulario(turno, evento.getUsuarioSIR().getUsername(), CTipoFormulario.TIPO_CORRECCION, fechaImpresion,CTipoImprimible.FORMULARIO_CORRECCION);
			imprimible.setPrintWatermarkEnabled(true);
			Bundle bundle = new Bundle(imprimible);


			//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
			int maxIntentos;
			int espera;

			//INGRESO DE INTENTOS DE IMPRESION
			try {

				String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
				if (intentosImpresion != null) {
					Integer intentos = new Integer(intentosImpresion);
					maxIntentos = intentos.intValue();
				} else {
					Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
					maxIntentos = intentosDefault.intValue();
				}

				//INGRESO TIEMPO DE ESPERA IMPRESION
				String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
				if (intentosImpresion != null) {
					Integer esperaInt = new Integer(esperaImpresion);
					espera = esperaInt.intValue();
				} else {
					Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
					espera = esperaDefault.intValue();
				}
			} catch (Throwable t) {
				Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
				maxIntentos = intentosDefault.intValue();

				Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();

			}


			try {
				printJobs.enviar(evento.getUID(), bundle, maxIntentos, espera);
			} catch (Throwable t) {
				t.printStackTrace();
				throw new EventoException(t.getMessage(), t);

			}
                                java.util.Map infoUsuario = new java.util.HashMap();
                                if(evento.getInfoUsuario() !=null){
                                    infoUsuario.put("user",evento.getInfoUsuario().getUser());
                                    infoUsuario.put("host",evento.getInfoUsuario().getHost());
                                    infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                                    infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                                }
			//HACER DEFINITIVOS LOS CAMBIOS
			while (itID.hasNext()) {
				FolioPk folioID = (FolioPk) itID.next();
				try {
					Folio folio = new Folio();
					folio.setIdMatricula(folioID.idMatricula);
					forseti.hacerDefinitivoFolio(folio, usr, turnoID, true);
				} catch (HermodException e) {
					ExceptionPrinter printer = new ExceptionPrinter(e);
					Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
					throw new EventoException(e.getMessage(), e);
				} catch (Throwable e) {
					ExceptionPrinter printer = new ExceptionPrinter(e);
					Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
					throw new EventoException(e.getMessage(), e);
				}
			}
			/* El servicio de hacer definitivo desbloquea los folios
			//DESBLOQUEAR LOS FOLIOS
			try {
				forseti.desbloquearFolios(turnoID, usr);
			} catch (ForsetiException e) {
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada desbloqueadno los folios. " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}*/

                        // AVANZAR EL TURNO DE CORRECCIONES A LA FASE DE IMPRESIÓN.

                        String respuestaWf = null;

                        turno       = evento.getTurno();
                        fase        = evento.getFase();
                        respuestaWf = EvnCorreccion.CONFIRMAR_WF;


                        String USUARIO_INICIADOR = ( null != evento.getUsuarioSIR() )?(""+evento.getUsuarioSIR().getUsername()):("");

                        Hashtable parameters = new Hashtable();
                        parameters.put( Processor.RESULT            , respuestaWf );
                        //parameters.put( Processor.USUARIO_INICIADOR , USUARIO_INICIADOR );
                        parameters.put( CInfoUsuario.USUARIO_SIR    , USUARIO_INICIADOR );
		
		}

		if (mayorValor) {
			evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.REDIRECCIONAR_CASO);
		} else {
			evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.APROBAR);
		}

		return evRespuesta;
	}






    //DEPURADO ENERO 17 DE 2006
	/**
	 * Método que es llamado cuando en la corrección se agregaron anotaciones y el corrector  decide no
	 * enviar el caso por mayor valor sino hacer definitivos los cambios y cerrar el caso.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta forzarAprobacion(EvnCorreccion evento) throws EventoException {

		//1. Obtención de parámetros desde el evento de corrección.
		EvnRespCorreccion evRespuesta = null;
		Fase fase = evento.getFase();
		Turno turno = evento.getTurno();
		Solicitud solicitud = turno.getSolicitud();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
		List folios = null;


		// 2. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		//SE CREA UN IDENTIFICAR DEL TURNO
		TurnoPk turnoID = new TurnoPk();
		turnoID.idCirculo = turno.getIdCirculo();
		turnoID.idProceso = turno.getIdProceso();
		turnoID.idTurno = turno.getIdTurno();
		turnoID.anio = turno.getAnio();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}

		//SE CARGA LA LISTA DE LOS IDENTIFICADORES DE LOS FOLIOS INVOLUCRADOS EN LA CORRECCIÓN.
		List solFolio = solicitud.getSolicitudFolios();
		List foliosID = new Vector();
		Iterator itSolFolio = solFolio.iterator();
		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
			FolioPk folioID = new FolioPk();
			folioID.idMatricula = sol.getFolio().getIdMatricula();
			foliosID.add(folioID);
		}

		//SE DETERMINA SI EL FOLIO DEBE IRSE POR MAYOR, EN CASO DE QUE TENGA NUEVAS ANOTACIONES.
		boolean mayorValor = false;
		Iterator itID = foliosID.iterator();
		while (itID.hasNext()) {
			FolioPk folioID = (FolioPk) itID.next();

			try {
				if (forseti.hasAnotacionesTMP(folioID)) {
					mayorValor = true;
				}

			} catch (HermodException e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}
		}

		//SI HAY PAGO POR MAYOR VALOR SE VERIFICA SI SE REALIZÓ CORRECTAMENTE EL PAGO
		//Y SE GUARDA EN LA VARIABLE 'PASOPAGOMAYORVALOR'
		boolean pasoPagoMayorValor = false;
		if (mayorValor) {
			List historia = turno.getHistorials();
			Iterator it = historia.iterator();

			while (it.hasNext()) {
				TurnoHistoria turnoHistoria = (TurnoHistoria) it.next();
				if (turnoHistoria.getFase() != null && turnoHistoria.getFase().equals(CFase.PMY_NOTIFICAR_FUNCIONARIO)) {
					pasoPagoMayorValor = true;
				}
			}
		}

		//SI EN ALGÚN MOMENTO EL TURNO PASÓ POR PAGO POR MAYOR VALOR SE IMPRIME EL FORMULARIO DE CORRECCIONES.
		if (pasoPagoMayorValor) {

			//SE CARGA LA LISTA DE FOLIOS QUE SUFRIERON ALGUNA MODIFICACIÓN
			try {
				folios = forseti.getDeltaFolios(turnoID, usr);
			} catch (ForsetiException e) {
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Error trayendo los folios que fueron cambiados. " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}

			//COLOCARLE AL TURNO LA LISTA DE FOLIOS QUE FUERON OBJETO DE MODIFICACIÓN
			Solicitud solic = new Solicitud();
			solic = turno.getSolicitud();
			solic.setSolicitudFolios(folios);
			turno.setSolicitud(solic);

			//IMPRIMIR EL FORMULARIO DE CORRECCIONES.
			String fechaImpresion= this.getFechaImpresion();
			ImprimibleFormulario imprimible = new ImprimibleFormulario(turno,evento.getUsuarioSIR().getUsername(), CTipoFormulario.TIPO_CORRECCION, fechaImpresion, CTipoImprimible.FORMULARIO_CORRECCION);
			imprimible.setPrintWatermarkEnabled(true);
			Bundle bundle = new Bundle(imprimible);

			//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
			int maxIntentos;
			int espera;

			//INGRESO DE INTENTOS DE IMPRESION
			try {

				String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
				if (intentosImpresion != null) {
					Integer intentos = new Integer(intentosImpresion);
					maxIntentos = intentos.intValue();
				} else {
					Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
					maxIntentos = intentosDefault.intValue();
				}

				//INGRESO TIEMPO DE ESPERA IMPRESION
				String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
				if (intentosImpresion != null) {
					Integer esperaInt = new Integer(esperaImpresion);
					espera = esperaInt.intValue();
				} else {
					Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
					espera = esperaDefault.intValue();
				}
			} catch (Throwable t) {
				Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
				maxIntentos = intentosDefault.intValue();

				Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();

			}

			try {
				printJobs.enviar(evento.getUID(), bundle, maxIntentos, espera);
			} catch (Throwable t) {
				t.printStackTrace();
				throw new EventoException(t.getMessage(), t);
			}
		}
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
                                    infoUsuario.put("idTurno",turno.getIdWorkflow());
                                }
                                co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                                try {
                                    auditoria.guardarDatosTerminal(infoUsuario);
                                } catch (GeneralSIRException ex) {
                                    Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                                }
		//SI SE EFECTUÓ EL PAGO EN EL WORKFLOW DE PAGO POR MAYOR VALOR. SE HACEN DEFINITIVOS LOS CAMBIOS PARA
		//LA SOLICITUD, SINO SE DESHACEN.
		itID = foliosID.iterator();
		while (itID.hasNext()) {
			FolioPk folioID = (FolioPk) itID.next();

			try {
				if (forseti.hasAnotacionesTMP(folioID)) {
					Folio folio = new Folio();
					folio.setIdMatricula(folioID.idMatricula);

					if (pasoPagoMayorValor) {
						forseti.hacerDefinitivoFolio(folio, usr, turnoID, true);
					} else {
						forseti.deshacerCambiosFolio(folio, usr);
					}
				}
			} catch (HermodException e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}
		}
                  /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                try {
                    auditoria.borrarDatosTerminal(turno.getIdWorkflow());
                } catch (GeneralSIRException ex) {
                    Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                }
                
		/* El servicio de hacer definitivo desbloquea los folios
		//SE DESBLOQUEAN LOS FOLIOS DE LA CORRECCIÓN.
		try {
			forseti.desbloquearFolios(turnoID, usr);
		} catch (ForsetiException e) {
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}*/

		//SE AVANZA EL TURNO A IMPRESIÓN.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", EvnCorreccion.CONFIRMAR_WF);
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.APROBAR);
		return evRespuesta;
	}





    //DEPURADO ENERO 17 2006
	/**
	 * Método que niega la solictud de corrección y deshace los cambios que hay en las tablas temporales.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta negarCorreccion(EvnCorreccion evento) throws EventoException {

		//1. Obtención de parámetros desde el evento.
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		//2. Construcción de tabla de parámetros para realizar el avance del turno.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());


		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}

		List solFolio = solicitud.getSolicitudFolios();
		List matriculas = new Vector();
		List folios = new Vector();
		Iterator itSolFolio = solFolio.iterator();

		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
			matriculas.add(sol.getFolio().getIdMatricula());

			Folio folio = new Folio();
			folio.setIdMatricula(sol.getFolio().getIdMatricula());
			folio.setZonaRegistral(sol.getFolio().getZonaRegistral());
			folios.add(folio);
		}

		Iterator it = null;

                // --------------------------------------
                // comentado: para que los cambios
                // todavia queden para
                // que el auxiliar de correcciones
                // pueda seguirlos editando

                /*

                // VERIFY: si se quiere implementar una opcion de deshacer,
                // se deben deshacer primero
                // algunos cambios en los folios padre o folios hijo
                // especificamente, las anotaciones temporales creadas
                // para especificar los folios derivados padre o folios derivados hijo (creados o a eliminar).


                it = folios.iterator();

		while (it.hasNext()) {
			Folio folio = (Folio) it.next();

			try {
				forseti.deshacerCambiosFolio(folio, usr);
			} catch (HermodException e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios" + printer.toString());
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios" + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}
		}

                */

		/* Se desbloquea en el deshacerCambios
		//Se desbloquean los folios de la corrección.
		try {
			Turno.ID turnoID = new Turno.ID();
			turnoID.idCirculo = turno.getIdCirculo();
			turnoID.idProceso = turno.getIdProceso();
			turnoID.idTurno = turno.getIdTurno();
			turnoID.anio = turno.getAnio();
			forseti.desbloquearFolios(turnoID, usr);
		} catch (ForsetiException e) {
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada desbloqueando los folios" + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}*/

		//Realizar el avance del turno (Servicio Hermod)
		try {

			hermod.avanzarTurnoNuevoCualquiera(turno, fase, tabla,evento.getUsuarioSIR());
		} catch (HermodException e) {

		} catch (Throwable e) {

			throw new ANCorreccionException("No se pudo NEGAR la solicitud de corrección. No fue" +
											 "posible avanzar el turno.", e);
		}

		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.NEGAR);

		return evRespuesta;
	}






	/**
	 * Método que retorno los cambios que se han realizado sobre turno, en cuanto a la modificación de los folios
	 * Se genera observando los datos que hay definitivos con respecto a los temporales de los folios.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta cargarCambiosPropuestos(EvnCorreccion evento) throws EventoException {

		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
                  /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: variable de turnoAnterior
        */
                Turno turnoAnterior = turno.getSolicitud().getTurnoAnterior();
        
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		Solicitud solicitud = turno.getSolicitud();
		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}

		List folios = solicitud.getSolicitudFolios();
		Iterator it = folios.iterator();

		List deltasFolio = new ArrayList();
		FolioPk id = new FolioPk();
		DeltaFolio delta = null;
                         /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: variable de delta test
        */
                DeltaTestamento deltaTest = null;
		boolean validarActualizacionCiudadano = false;
                     /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: validacion de turno de correccion de testamento        */
                if(turnoAnterior ==null ||(turnoAnterior!=null && turnoAnterior.getIdProceso()!=6)){
		try {
			
			Turno t = new Turno();
			t.setIdWorkflow(turno.getIdWorkflow());
			//se debe validar si el turno modifico algun ciudadano
			validarActualizacionCiudadano = forseti.validarActualizacionCiudadanoTurno(t);

			while (it.hasNext()) {

				SolicitudFolio sol = (SolicitudFolio) it.next();
				id.idMatricula = sol.getIdMatricula();

				// filtro:
				// el servicio esta devolviendo tambien las salvedades
				// hechas en otros turnos;
				// modificado, devuelve las que estan en temporal; se debe filtrar el conjunto de salvedades
				// solo del turno

				long tiempoInicial =  System.currentTimeMillis();
				Log.getInstance().debug(ANCorreccion.class,"\n*******************************************************");
				Log.getInstance().debug(ANCorreccion.class,"((AN Correccion) ANTES LLAMADO cargarCambiosPropuestos) > "+id.idMatricula+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
				Log.getInstance().debug(ANCorreccion.class,"\n*******************************************************\n");

				boolean incluirSalvedades=false;
				if(turno!=null && turno.getIdFase()!=null && turno.getIdFase().equals(CFase.COR_REVISAR_APROBAR)
						&& turno.getIdProceso()== Long.parseLong(CProceso.PROCESO_CORRECCION) )
					incluirSalvedades=true;
				delta = forseti.getCambiosPropuestos(id, usr, false, false );

				
				Log.getInstance().debug(ANCorreccion.class,"\n*******************************************************");
				Log.getInstance().debug(ANCorreccion.class,"((AN Correccion) DESPUES LLAMADO cargarCambiosPropuestos) > "+id.idMatricula+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
				Log.getInstance().debug(ANCorreccion.class,"\n*******************************************************\n");

				filter_Salvedades: {

					String local_TurnoId = turno.getIdWorkflow();

					Folio local_t1_Folio;
					List  local_t1_BufferSalvedades;
					List  local_t1_FolioAnontaciones;

					local_t1_Folio = delta.getTemporal();

					// PipelineFilter: extraer-transformar-cargar (folio/salvedades)

					local_t1_BufferSalvedades = local_t1_Folio.getSalvedades();
					local_t1_BufferSalvedades = jxSearch_ExtractSalvedadesByTurno( local_t1_BufferSalvedades, local_TurnoId );
					local_t1_Folio.setSalvedades( local_t1_BufferSalvedades );

					// for( Anotacion element : folio.anotaciones )

					local_t1_FolioAnontaciones = local_t1_Folio.getAnotaciones();

					if( ( null != local_t1_FolioAnontaciones )
					  &&( local_t1_FolioAnontaciones.size() > 0 ) ) {

						Iterator  local_t1_FolioAnontacionesIterator;
						Anotacion local_t1_FolioAnontacionesElement;

						local_t1_FolioAnontacionesIterator = local_t1_FolioAnontaciones.iterator();


						for( ;local_t1_FolioAnontacionesIterator.hasNext(); ){
							local_t1_FolioAnontacionesElement = (gov.sir.core.negocio.modelo.Anotacion)local_t1_FolioAnontacionesIterator.next();

							// PipelineFilter: extraer-transformar-cargar (folio/anotaciones[i]/salvedades )

							local_t1_BufferSalvedades = local_t1_FolioAnontacionesElement.getSalvedades();
							local_t1_BufferSalvedades = jxSearch_ExtractSalvedadesByTurno( local_t1_BufferSalvedades, local_TurnoId );
							local_t1_FolioAnontacionesElement.setSalvedades( local_t1_BufferSalvedades );


						} // for


					} // if







				} // :filter_Salvedades


				delta.startProcess();


				deltasFolio.add(delta);
			}
		} catch (ForsetiException e) {
			throw new ANCorreccionException("No se pudo cargar los cambios que se han realizado en la corrección ", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"No se pudo cargar los cambios que se han realizado en la corrección " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
                }else{
                             /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: stencias en caso de ser un turno de testamento
        */
                    try {
                        Testamento testamentoDef = getTestamentoFromMap(new TestamentoSIR().getTestamentoDEF(turnoAnterior.getSolicitud().getIdSolicitud()));
                        Testamento testamentoTmp = getTestamentoFromMap(new TestamentoSIR().getTestamentoTMP(turnoAnterior.getSolicitud().getIdSolicitud(),turno.getIdWorkflow()));
                        Documento documentoDef = ((SolicitudRegistro)turnoAnterior.getSolicitud()).getDocumento();
                        Documento documentoTmp = getDocumentoFromMap(new TestamentoSIR().getEncabezadoTMP(turnoAnterior.getSolicitud().getIdSolicitud(),turno.getIdWorkflow()));
                                         
                        
                        if(testamentoDef!=null && testamentoTmp!=null)
                        {
                           List<Ciudadano> testadoresDef = getTestadoresFromList(new TestamentoSIR().getTestadoresDEF(turnoAnterior.getSolicitud().getIdSolicitud()));
                           List<Ciudadano> testadoresTmp = getTestadoresFromList(new TestamentoSIR().getTestadoresTMPConEliminados(turnoAnterior.getSolicitud().getIdSolicitud()));
                           testamentoDef.setTestadores(testadoresDef);
                           testamentoTmp.setTestadores(testadoresTmp);
                           
                           deltaTest = new DeltaTestamento(testamentoDef, testamentoTmp, false,false);
                           deltaTest.setTurno(turnoAnterior.getIdWorkflow());
                           deltaTest.setDocDefinitivo(documentoDef);
                           deltaTest.setDocTemporal(documentoTmp);
                           deltaTest.startProcess();
                           deltasFolio.add(deltaTest);
                        }
                    } catch (GeneralSIRException ex) {
                        Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), deltasFolio, EvnRespCorreccion.CARGAR_CAMBIOS_PROPUESTOS);
		evRespuesta.setModificoCiudadanoTmp(validarActualizacionCiudadano);

		return evRespuesta;
	}


	//DEPURADO ENERO 17 2006
	/**
	 * Método que aprueba la corrección, desde el rol de usuario especializado y hace
	 * definitivo los cambios que hay en las tablas temporales.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta aprobarCorreccionEspecializado(EvnCorreccion evento) throws EventoException {

		//1. Obtener parámetros desde el evento de corrección
		EvnRespCorreccion evRespuesta = null;
		Fase fase = evento.getFase();
		Turno turno = evento.getTurno();
		Solicitud solicitud = turno.getSolicitud();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}


		// 2. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		//3. Se carga la lista de folios involucrados en la corrección.
		List solFolio = solicitud.getSolicitudFolios();

		List foliosID = new Vector();
		Iterator itSolFolio = solFolio.iterator();

		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
			FolioPk folioID = new FolioPk();
			folioID.idMatricula = sol.getFolio().getIdMatricula();
			foliosID.add(folioID);
		}

		//SI EL USUARIO ESPECIALIZADO PUEDE HACER DEFINITIVOS LOS CAMBIOS SE DEBE DESCOMENTAREAR EL CICLO.
		//Actualiza los folios.
/*		Iterator itID = foliosID.iterator();

		while (itID.hasNext()) {
			Folio.ID folioID = (Folio.ID) itID.next();

			try {
				Folio folio = new Folio();
				folio.setIdMatricula(folioID.idMatricula);
				folio.setIdZonaRegistral(folioID.idZonaRegistral);

				Turno.ID turnoID = new Turno.ID();
				turnoID.idCirculo = turno.getIdCirculo();
				turnoID.idProceso = turno.getIdProceso();
				turnoID.idTurno = turno.getIdTurno();
				turnoID.anio = turno.getAnio();
				forseti.hacerDefinitivoFolio(folio, usr, turnoID);
			} catch (HermodException e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando los cambios" + printer.toString());
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardnado los cambios" + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}
		}*/



		// 4. Se avanza el turno por Confirmar Especializado.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", EvnCorreccion.CONFIRMAR_WF_ESP);
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
		
		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.APROBAR_ESPECIALIZADO);

		return evRespuesta;
	}




    //DEPURADO ENERO 17 2006
	/**
	 * Método que niega la corrección, desde el rol de usuario especializado y deshace
	 * los cambios que hay en las tablas temporales.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta negarCorreccionEspecializado(EvnCorreccion evento) throws EventoException {

		//1. Obtener parámetros desde el evento de Corrección.
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		//2. Construir tabla de parámetros para realizar el avance del turno.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", EvnCorreccion.NEGAR_WF_ESP);
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());


		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}

		List solFolio = solicitud.getSolicitudFolios();
		List matriculas = new Vector();
		List folios = new Vector();
		Iterator itSolFolio = solFolio.iterator();

		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
			matriculas.add(sol.getFolio().getIdMatricula());

			Folio folio = new Folio();
			folio.setIdMatricula(sol.getFolio().getIdMatricula());
			folio.setZonaRegistral(sol.getFolio().getZonaRegistral());
			folios.add(folio);
		}

		Iterator it = folios.iterator();

		while (it.hasNext()) {
			Folio folio = (Folio) it.next();

			try {
				forseti.deshacerCambiosFolio(folio, usr);
			} catch (HermodException e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios" + printer.toString());
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios" + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}
		}

		//Se avanza el turno, negando la correción.
		
		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.NEGAR_ESPECIALIZADO);

		return evRespuesta;
	}




    //DEPURADO ENERO 17 2006
	/**
	 * Método que aprueba los cambios que se han realizado desde el rol de microfilmador y
	 * retorna el turno para que siga con el flujo normal.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta aprobarMicrofilmacion(EvnCorreccion evento) throws EventoException {


		//1. Obtención de parámetros desde el evento.
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		//2. Construir tabla de parámetros para realizar el avance del turno.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());


		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}
	
		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.APROBAR);

		return evRespuesta;
	}




    //DEPURADO ENERO 17 2006
	/**
	 * Método que indica que no se realizaron cambios desde el perfil de microfilmador y que
	 * retorna el turno para que siga con el flujo normal.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta negarMicrofilmacion(EvnCorreccion evento) throws EventoException {

		//1. Obtener parámetros desde el evento.
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		//2. Construir tabla de parámetros para realizar el avance del turno.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());


		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}

		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.NEGAR);

		return evRespuesta;
	}





    //DEPURADO ENERO 17 2006
	/**
	 * Método que aprueba los cambios que se han realizado desde el rol de digitador y
	 * retorna el turno para que siga con el flujo normal.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta aprobarCorreccionDigitacion(EvnCorreccion evento) throws EventoException {

		//1. Obtener los parámetros desde el evento.
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		//2. Construir tabla de parámetros para realizar el avance del turno.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());


		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}
		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.APROBAR);

		return evRespuesta;
	}




    //DEPURADO ENERO 17 2006
	/**
	 * Método que retorna el turno desde el rol de digitador para que siga con el flujo normal.
	 * En este método no se guarda ningún cambio, sólo se devuelve el turno a quién lo había delegado.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta negarCorreccionDigitacion(EvnCorreccion evento) throws EventoException {

		//1. Obtener parámetros desde el evento.
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		//2. Crear tabla de parámetros para realizar el avance del turno.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}

		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.NEGAR);

		return evRespuesta;
	}





	/**
	 * Método que guarda los cambios que se han solicitado.
	 * Estos cambios quedan guardados en tablas temporales hasta que no se hagan defintivos, es decir se apruebe
	 * la solicitud o se deshagan cuando se niegue la corrección.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta editarFolio(EvnCorreccion evento) throws EventoException {
		EvnRespCorreccion evRespuesta = null;

		gov.sir.core.negocio.modelo.Turno local_Turno;
                gov.sir.core.negocio.modelo.Folio local_Folio;
                gov.sir.core.negocio.modelo.Usuario local_UsuarioSir;

                local_Folio      = evento.getFolio();
                local_UsuarioSir = evento.getUsuarioSIR();
                local_Turno      = evento.getTurno();

                // Bug 3552
                // filtro, adicion a complementacion

                filter_RuleComplementacionAdicionaEnabled( local_Turno, local_Folio );

                Turno turno = evento.getTurno();
        		TurnoPk turnoId = new TurnoPk();
        		turnoId.anio = turno.getAnio();
        		turnoId.idCirculo = turno.getIdCirculo();
        		turnoId.idProceso = turno.getIdProceso();
        		turnoId.idTurno = turno.getIdTurno();
        		
       
		// TFS 5347
        // Si se va a actualizar en número de radicación 
        // o la fecha de apertura del folio, 
        //  se debe validar que el turno no haya sido 
        // creado en SIR
                		
		Folio folio = evento.getFolio();
		
		if ((folio.getRadicacion() != null 
				&& !folio.getRadicacion().equals(""))
				|| folio.getFechaApertura() != null)
		{
			FolioPk fpk = new FolioPk();
			fpk.idMatricula = folio.getIdMatricula();
			Folio folioOrig = null;
			try {
				folioOrig = forseti.getFolioByID(fpk);
			} catch (Throwable e) {
				throw new CorreccionFolioEditInfoInvalidParameterException(e.getMessage(), e);
			}
			
			if (folioOrig != null
					&& (folioOrig.getRadicacion() != null
					&& !folioOrig.getRadicacion().equals(""))
					|| (folioOrig.getFechaApertura() != null))
			{
				Turno turnoValida = null;
				try {
					turnoValida = hermod.getTurnobyWF(folioOrig.getRadicacion());
				} catch (Throwable e) {
					;
				}
				
				if (turnoValida != null 
						&& (turnoValida.getDescripcion() == null
						|| !turnoValida.getDescripcion().equals(CTurno.DESCRIPCION_MIGRADO)))
				{
					if (folio.getRadicacion() != null)
					{
						throw new CorreccionFolioEditInfoInvalidParameterException("El numero de radicación a modificar corresponde a un turno creado en SIR");
					}
					if (folio.getFechaApertura() != null)
					{
						throw new CorreccionFolioEditInfoInvalidParameterException("La fecha de apertura a modificar corresponde a una radicación creada en SIR");
					}
				}
				
			}
		}
        		
		try {
		  forseti.updateFolio( local_Folio, local_UsuarioSir, turnoId, true );
		}
                catch (ForsetiException e) {
			throw new EventoException(e.getMessage(), e);
		}
                catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		return evRespuesta;
	}

      private void filter_RuleComplementacionAdicionaEnabled_SetField( JXPathContext local_T1_Context, String jxPath, String value ) throws EventoException {
         local_T1_Context.setValue( jxPath, value );
      } // filter_RuleComplementacionAdicionaEnabled_SetField

      private void filter_RuleComplementacionAdicionaEnabled_SetField( gov.sir.core.negocio.modelo.Folio local_T1_ContextObject, String jxPath, String value ) throws EventoException {

         Object local_ContextObject = local_T1_ContextObject;
         JXPathContext local_Context;

         local_Context = jx_GetContext( local_ContextObject );

         local_Context.createPathAndSetValue( jxPath, value );
      }


	/**
	 * filter_RuleComplementacionAdicionaEnabled
	 *
	 * @param local_Folio Folio
	 * @return Folio
	 */
	private void filter_RuleComplementacionAdicionaEnabled( Turno local_Turno, Folio local_T1_Folio ) throws EventoException {

            if( this.getRuleComplementacionAdicionaEnabled( local_Turno ) ) {
               Folio    local_T0_Folio;
               FolioPk local_T0_FolioId;

               local_T0_FolioId = new FolioPk();
               local_T0_FolioId.idMatricula = local_T1_Folio.getIdMatricula();

               try {
                  local_T0_Folio = forseti.getFolioByIDSinAnotaciones( local_T0_FolioId );
               }
               catch (Throwable ex) {
                  throw new EventoException( ex.getMessage() );
               }


               String t0Complementacion;
               String t1Complementacion;
               String t2Complementacion;


               load_JxData_FolioComplementacion: {

                  JXPathContext local_T0_Context;
                  JXPathContext local_T1_Context;
                  Object        local_T0_ContextObject;
                  Object        local_T1_ContextObject;

                  String       local_JxSearchString;

                  local_JxSearchString = JXPATH_FOLIO_COMPLEMENTACION_COMPLEMENTACION;

                  local_T0_ContextObject = local_T0_Folio;
                  local_T1_ContextObject = local_T1_Folio;

                  local_T0_Context = jx_GetContext( local_T0_ContextObject );
                  local_T1_Context = jx_GetContext( local_T1_ContextObject );

                  t0Complementacion = (String)local_T0_Context.getValue( local_JxSearchString );
                  t1Complementacion = (String)local_T1_Context.getValue( local_JxSearchString );

                  // Si no es nulo significa que ha variado,
                  // se debe actualizar
                  if( null != t1Complementacion ) {

                    t2Complementacion = Nvl( t0Complementacion, "" ) + Nvl( t1Complementacion, "" );
                    filter_RuleComplementacionAdicionaEnabled_SetField( local_T1_Context, JXPATH_FOLIO_COMPLEMENTACION_COMPLEMENTACION, t2Complementacion );

                  } // if

               } // :load_JxData_FolioComplementacion




            } // if

	}

	/**
	 * Método que guarda los cambios que se han realizado de una anotación y recarga la información
	 * con los cambios que han sido guardado.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta editarFolioAnotacion(EvnCorreccion evento) throws EventoException {
		Turno turno = evento.getTurno();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
		
		TurnoPk local_TurnoId;
        local_TurnoId = new TurnoPk();
        local_TurnoId.anio      = turno.getAnio();
        local_TurnoId.idCirculo = turno.getIdCirculo();
        local_TurnoId.idProceso = turno.getIdProceso();
        local_TurnoId.idTurno   = turno.getIdTurno();
        
        // TFS 5345
        // Si se va a actualizar en número de radicación de la
        // anotación se debe validar que el turno no haya sido 
        // creado en SIR
        
        /*// se quita validación según SIR-322
        List anotaciones = evento.getFolio().getAnotaciones();
        if (anotaciones != null)
        {
        	for (Iterator anotaItera = anotaciones.iterator();
        			anotaItera.hasNext();)
        	{
        		
        		Anotacion anotacion = (Anotacion)anotaItera.next();
        		
        		if (anotacion.getNumRadicacion() != null 
        				&& !anotacion.getNumRadicacion().equals(""))
        		{
        		
	        		AnotacionPk apk = new AnotacionPk();
					apk.idAnotacion = anotacion.getIdAnotacion();
					apk.idMatricula = evento.getFolio().getIdMatricula();
					Anotacion anotacionOrig = null;
					try {
						anotacionOrig = forseti.getAnotacionById(apk);
					} catch (Throwable e) {
						throw new CorreccionFolioEditInfoInvalidParameterException(e.getMessage(), e);
					}
					
	        		if (anotacionOrig != null
							&& anotacionOrig.getNumRadicacion() != null
							&& !anotacionOrig.getNumRadicacion().equals(""))
	        		{
	        			Turno turnoValida = null;
	        			try {
							turnoValida = hermod.getTurnobyWF(anotacionOrig.getNumRadicacion());
						} catch (Throwable e) {
							;
						}
						
						if (turnoValida != null 
								&& (turnoValida.getDescripcion() == null
								|| !turnoValida.getDescripcion().equals(CTurno.DESCRIPCION_MIGRADO)))
						{
							throw new CorreccionFolioEditInfoInvalidParameterException("El numero de radicación a modificar corresponde a un turno creado en SIR");
						}
	        		}
        		}
        	}
        }*/
        	
		try {   
			forseti.updateFolio(evento.getFolio(), usr, local_TurnoId, false);
		} catch (ForsetiException e) {
			throw new CorreccionFolioEditInfoInvalidParameterException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		try {
			Folio folio = evento.getFolio();
			gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioSIR();
			if (folio != null && folio.getIdMatricula() != null) {

				FolioPk id = new FolioPk();
				id.idMatricula = folio.getIdMatricula();

				folio = forseti.getFolioByID(id, u);

				//boolean esMayorExtension = forseti.mayorExtensionFolio(folio.getIdMatricula());

				// long numeroAnotaciones = forseti.getCountAnotacionesFolio(folio.getIdMatricula());
                                long numeroAnotaciones = forseti.getCountAnotacionesFolio( id , CCriterio.TODAS_LAS_ANOTACIONES, null, (Usuario)null );

				List foliosHijos = forseti.getFoliosHijos(id, u);
				List foliosPadre = forseti.getFoliosPadre(id, u);

				long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN);
				List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN, 0, (int) numeroGravamenes, u, true);

				long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
				List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR, 0, (int) numeroMedidasCautelares, u, true);

				long numeroFalsaTradicion = 0;
				List falsaTradicion = null;
				List ordenFalsaTradicion = new ArrayList();
				List anotacionesInvalidas = null;
				List ordenAnotacionesInvalidas = new ArrayList();
				List anotacionesPatrimonioFamiliar = null;
				long numanotacionesPatrimonioFamiliar = 0;
				List anotacionesAfectacionVivienda = null;
				long munanotacionesAfectacionVivienda = 0;
				List anotacionesAfectacionVivienda2 = null;
				long munanotacionesAfectacionVivienda2 = 0;
				
				//sir-169
				long numeroSegregaciones = 0;
				long numeroSegregacionesVacias = 0;
				List anotacionesSegregadorasVacias = null;

				//CONSULTA ANOTACIONES DE FALSA TRADICION
				numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
				falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, u, true);
				if(falsaTradicion != null){
					Iterator it = falsaTradicion.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenFalsaTradicion.add(anotTemp.getOrden());
					}
				}

				//CONSULTA ANOTACIONES INVALIDAS
				anotacionesInvalidas = forseti.getAnotacionesInvalidas(id, u);
				if(anotacionesInvalidas != null){
					Iterator it = anotacionesInvalidas.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenAnotacionesInvalidas.add(anotTemp.getOrden());
					}
				}
				
				//	CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
				numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
				anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
				
				//	CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
				munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
				anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
				
				// CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
				munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
				anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
				
				anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);
				
				//sir-169
				numeroSegregaciones = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.LOTEO, null);
				anotacionesSegregadorasVacias = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.LOTEO,0, (int)numeroSegregaciones,null,true);
				numeroSegregaciones += forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.RELOTEO, null);
				anotacionesSegregadorasVacias.addAll(forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.RELOTEO,0, (int)numeroSegregaciones,null,true));
				numeroSegregacionesVacias = (long)anotacionesSegregadorasVacias.size();

				List salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, u);
				List cancelaciones = forseti.getAnotacionesConCancelaciones(id, u);
				
				EvnRespCorreccion resp = new EvnRespCorreccion(folio, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion, ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, EvnRespCorreccion.CONSULTAR_FOLIO);
				/**BUG 3275*/
				resp.setCargarSalvedad(evento.isCargarSalvedad());
				resp.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
				resp.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
				resp.setNumSegregacionesVacias(numeroSegregacionesVacias);
				return resp;
			}
            throw new EventoException("Se necesita saber la matrícula a consultar");
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString());
			Log.getInstance().error(ANCorreccion.class,e);
			throw new EventoException(e.getMessage(), e);
		}

		//return evRespuesta;
	}

	/**
	 * Método que guarda los cambios que se han realizado de una anotación y recarga la información
	 * con los cambios que han sido guardado.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta segregarFolioAnotacionDefinitiva(EvnCorreccion evento) throws EventoException {
		Turno turno = evento.getTurno();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		TurnoPk local_TurnoId;
		local_TurnoId = new TurnoPk();
		local_TurnoId.anio      = turno.getAnio();
		local_TurnoId.idCirculo = turno.getIdCirculo();
		local_TurnoId.idProceso = turno.getIdProceso();
		local_TurnoId.idTurno   = turno.getIdTurno();

		// TFS 5345
		// Si se va a actualizar en número de radicación de la
		// anotación se debe validar que el turno no haya sido 
		// creado en SIR
		List anotaciones = evento.getFolio().getAnotaciones();
		if (anotaciones != null)
		{
			for (Iterator anotaItera = anotaciones.iterator();
			anotaItera.hasNext();)
			{

				Anotacion anotacion = (Anotacion)anotaItera.next();

				if (anotacion.getNumRadicacion() != null 
						&& !anotacion.getNumRadicacion().equals(""))
				{

    //DEPURADO ENERO 17 2006
					AnotacionPk apk = new AnotacionPk();
					apk.idAnotacion = anotacion.getIdAnotacion();
					apk.idMatricula = evento.getFolio().getIdMatricula();
					Anotacion anotacionOrig = null;
					try {
						anotacionOrig = forseti.getAnotacionById(apk);
					} catch (Throwable e) {
						throw new CorreccionFolioEditInfoInvalidParameterException(e.getMessage(), e);
					}

					if (anotacionOrig != null
							&& anotacionOrig.getNumRadicacion() != null
							&& !anotacionOrig.getNumRadicacion().equals(""))
					{
						Turno turnoValida = null;
						try {
							turnoValida = hermod.getTurnobyWF(anotacionOrig.getNumRadicacion());
						} catch (Throwable e) {
							;
						}

						if (turnoValida != null 
								&& (turnoValida.getDescripcion() == null
										|| !turnoValida.getDescripcion().equals(CTurno.DESCRIPCION_MIGRADO)))
						{
							throw new CorreccionFolioEditInfoInvalidParameterException("El numero de radicación a modificar corresponde a un turno creado en SIR");
						}
					}
				}
			}
		}

		try {
			forseti.updateFolio(evento.getFolio(), usr, local_TurnoId, false);
		} catch (ForsetiException e) {
			throw new CorreccionFolioEditInfoInvalidParameterException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		//EvnRespCorreccion resp = new EvnRespCorreccion(folio, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion, ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, EvnRespCorreccion.CONSULTAR_FOLIO);
		EvnRespCorreccion resp = null;
		/**BUG 3275*/
		//resp.setCargarSalvedad(evento.isCargarSalvedad());
		//resp.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
		//resp.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
		return resp;
	}
	
	//DEPURADO ENERO 17 2006
	/**
	 * Método que guarda los cambios que se solicitan en varios folios.
	 * Estos cambios y los folios a los que se aplican vienen en el evento de correcciones.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta editarFolios(EvnCorreccion evento) throws EventoException {

		//1. Obtener parámetros desde el evento.
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		List matriculasSinComplementacion = new ArrayList();
		List validaciones = new ArrayList();
		String tipoActualizacion = evento.getTipoActualizacion();

		try {

			List matriculas = evento.getFolios();
			Iterator itMatriculas = matriculas.iterator();
			Folio temp = null;
			Folio def = null;


			//SI SE ESTA ACTUALIZANDO LA COMPLEMENTACIÓN SE VERIFICA QUE EL FOLIO NO TENGA, SINO, NO LO HACE.
			//SI SE DESEA ACTUALIZAR ALGUNA OTRA COSA DEL FOLIO, NO SE HACE NINGUNA VALIDACIÓN.
			if(tipoActualizacion.equals(CDigitacionMasiva.OPCION_COMPLEMENTACION))
			{
				while(itMatriculas.hasNext())
				{

					FolioPk id = (FolioPk)itMatriculas.next();
					temp = forseti.getFolioByIDSinAnotaciones(id,usr);
					def = forseti.getFolioByIDSinAnotaciones(id);

					//SI EL FOLIO ES DEFINITIVO SE DEBE PREGUNTAR SI NO TIENE COMPLEMENTACIÓN PARA PODER GUARDAR LA
					//NUEVA COMPLEMENTACIÓN, SI EL FOLIO ES TEMPORAL, SI PERMITE GUARDAR LA NUEVA COMPLEMENTACIÓN.
					if(temp!=null && temp.isDefinitivo())
					{
						if( (temp.getComplementacion()==null
								|| temp.getComplementacion().getComplementacion().trim().equals("")))
						{
							matriculasSinComplementacion.add(id);
						}
						else
						{
							if (def.getComplementacion() != null &&
									!def.getComplementacion().getComplementacion().trim().equals(""))
							{
								validaciones.add("El folio " + id.idMatricula + ", " +
										"ya tiene complementación y no ha sido actualizado.");
							}
							else
							{
								matriculasSinComplementacion.add(id);
							}
						}
					}
					else
					{
						matriculasSinComplementacion.add(id);
					}
				}

				//ACTUALIZA LOS FOLIOS QUE SON TEMPORALES O QUE NO TIENEN UNA COMPLEMENTACIÓN ASOCIADA.
				forseti.updateFolios(evento.getFolio(), matriculasSinComplementacion, usr, true);

				if(validaciones.size()>0){
					throw new ValidacionParametrosException(validaciones);
				}
			}else if(tipoActualizacion.equals(CDigitacionMasiva.OPCION_LINDERO) || tipoActualizacion.equals(CDigitacionMasiva.OPCION_DIRECCION)){
				forseti.updateFolios(evento.getFolio(), evento.getFolios(), usr, true);
			}else if(tipoActualizacion.equals(CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL) ){
				OficioPk idOf= hermod.crearOficio(evento.getOficio());

			}

		} catch (ValidacionParametrosException e) {
			throw e;
		} catch (ForsetiException e) {
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada guardando cambios de los folios" + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.EDITAR_FOLIOS, validaciones);
		return evRespuesta;
	}




     //DEPURADO ENERO 17 2006
	/**
	 * Método que permite delegar el turno a otros roles.
	 * Estos roles pueden ser microfilmador, digitador, usuario especializado, antiguo sistema.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta delegarCorreccion(EvnCorreccion evento) throws EventoException {


		//1 Obtener parámetros desde el evento.
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		//2. Generar tabla para realizar el avance del turno.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}
		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.DELEGAR_CASO);

		return evRespuesta;
	}



	/**
	 * Método que retorna un turno determinado a quién lo delegó.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta devolverCorreccion(EvnCorreccion evento) throws EventoException {
		EvnRespCorreccion evRespuesta = null;

		return evRespuesta;
	}



     // DEPURADO ENERO 17 2006
	/**
	 * Método que permite a un turno ser enviado al proceso de pago por mayor valor
	 * para que le sea cobrado al ciudadano un valor adicional.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta redireccionarCorreccion(EvnCorreccion evento) throws EventoException {

		//1. Obtener los parámetros desde el evento.
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		//2. Construir la tabla de parámetros para realizar el avance.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());


		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}

		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.REDIRECCIONAR_CASO);

		return evRespuesta;
	}






	/**
	 * Método que permite al corrector imprimir uno o varios certificados de libertad cuando se ha aprobado el
	 * caso de corrección de documentos.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta imprimirCertificados(EvnCorreccion evento) throws EventoException {

          //OBTENER INFORMACIÓN A PARTIR DEL TURNO
          Turno turno = evento.getTurno();

          
          //TFS 5432 NO PERMITIR IMPRESION EN CORRECCIONES INTERNAS
          
          Turno turnoPadre=evento.getTurnoPadre();
          if(turnoPadre!=null)
          {
        	  //la correccion es interna imposible imprimir
        	  throw new Correccion_ImprimirCertificados_InvalidPrintDateException( "Imposible consultar folios a imprimir, debido a que la correccion es de tipo INTERNA");
          }
          
          //FIN TFS 5432
          
          // bug 3589: solo se permite la impresion de certificados el mismo dia en que se aprueba correccion

          java.util.Date local_Date;
          local_Date = new java.util.Date(System.currentTimeMillis());

          // buscar la fecha en la cual se realiza la aprobacion
          // de la correccion
          java.util.Date local_TurnoHistoriaDate = null;


          boolean founded_Date = false;

          searchImpl_jx: {


             if( !founded_Date ) {

                // caso1: correccion normal; buscar idFaseAprobacion

                Turno local_Turno;

                // buscar turno en la db?
                local_Turno = turno;

                // por el momento se observa el registro historico que tiene el turno
                // List< TurnoHistoria >
                List local_TurnoHistoria;

                local_TurnoHistoria = local_Turno.getHistorials();

                JXPathContext local_TurnoSearchContext = jx_GetContext( local_TurnoHistoria );
                local_TurnoSearchContext.getVariables().declareVariable("idFase", "");
                local_TurnoSearchContext.setValue("$idFase", "COR_REVISAR_APROBAR");

                // search matches
                // apply first

                Iterator local_TurnoSearchContextIterator = local_TurnoSearchContext.iterate(
                    ".[@fase = $idFase]");

                for (; local_TurnoSearchContextIterator.hasNext(); ) {
                  TurnoHistoria element = (TurnoHistoria) local_TurnoSearchContextIterator.
                      next();
                  local_TurnoHistoriaDate = element.getFecha();
                  founded_Date = true;
                } // for


             } // if


             if( !founded_Date ) {

                // caso2: actuacion administrativa; buscar idFaseAprobacion

                Turno local_Turno;

                // buscar turno en la db?
                local_Turno = turno;

                // por el momento se observa el registro historico que tiene el turno
                // List< TurnoHistoria >
                List local_TurnoHistoria;

                local_TurnoHistoria = local_Turno.getHistorials();

                JXPathContext local_TurnoSearchContext = jx_GetContext( local_TurnoHistoria );
                local_TurnoSearchContext.getVariables().declareVariable("idFase", "");
                local_TurnoSearchContext.setValue("$idFase", "COR_ACT_EJECUTAR");

                // search matches
                // apply first

                Iterator local_TurnoSearchContextIterator = local_TurnoSearchContext.iterate(
                    ".[@fase = $idFase]");

                for (; local_TurnoSearchContextIterator.hasNext(); ) {
                  TurnoHistoria element = (TurnoHistoria) local_TurnoSearchContextIterator.
                      next();
                  local_TurnoHistoriaDate = element.getFecha();
                  founded_Date = true;
                } // for


             } // if

             // Bug X075
             if( !founded_Date ) {
                String raise_Tx = "";
                raise_Tx += "No se ha podido raastrear la fecha de aprobacion de correccion";

                throw new Correccion_ImprimirCertificados_InvalidPrintDateException( raise_Tx );
             } //



          } // :searchImpl_jx


          // Double foundedElelementsCount = (Double)local_TurnoSearchContext.getValue( "count( .[@fase = $idFase] )");
          // if( foundedElelementsCount.intValue() > 0 ) {
          // }

          // realizar verificacion; si no pasa la prueba
          // raise appError: no se aprueba impresion
          //  se supero el limite de tiempo para realizar la impresion
          // TODO: hacer algo para comparadores en capa de negocio
          // por el momento, usar los comparadores realizados

          // formato usado para comparacion
        String local_TurnoHistoriaDate_String = "";
		String local_Date_String = "";

		local_TurnoHistoriaDate_String = DateFormatUtil.format( local_TurnoHistoriaDate );
		local_Date_String              = DateFormatUtil.format( local_Date              );

          BasicDateFormatComparator comparator = new BasicDateFormatComparator( DateFormatUtil.DEFAULT_FORMAT );
          if( comparator.compare( local_Date, local_TurnoHistoriaDate ) > 0 ){
            String raise_Tx = "";
            raise_Tx += "Se supero el limite de tiempo para realizar la impresion: <br />";
            raise_Tx += "Solo se permite impresión de certificados por esta pantalla ";
            raise_Tx += " en el mismo día en que se aprobó la corrección. ";
            raise_Tx += " <br />Fecha Aprobacion: " + local_TurnoHistoriaDate_String;
            raise_Tx += " <br />Fecha Actual    : " + local_Date_String ;

            throw new Correccion_ImprimirCertificados_InvalidPrintDateException( raise_Tx );
          }






		EvnRespCorreccion ev = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.IMPRIMIR);

		//IMPRIMIR LOS CERTIFICADOS SOLICITADOS
		try {
			List noImpresos = imprimirCertificados(turno, turno.getSolicitud().getSolicitudFolios(), evento.getCirculoId(), evento.getImpresora());
			ev.setFoliosNoImpresos(noImpresos);
		} catch (Throwable t2) {
			t2.printStackTrace();
			throw new EventoException(t2.getMessage());
		}

		return ev;
	}


	/**
	 * Método que permite al corrector imprimir uno o varios certificados de libertad cuando se ha aprobado el
	 * caso de corrección de documentos.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta imprimirCertificadosIndividual(EvnCorreccion evento) throws EventoException {

		//OBTENER INFORMACIÓN A PARTIR DEL TURNO
		Turno turno = evento.getTurno();
		EvnRespCorreccion ev = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.IMPRIMIR_INDIVIDUAL);


                // extract parameters from event

                List folios = evento.getFolios();
                String uid = evento.getUID();

                // ------------------------------------------------------------
                // Bug 3570:
                // no se puede dejar imprimir
                // un certificado seleccionado si no
                // tiene salvedades.

                // Salvedades ya han pasado a defitivo:
                // se debe buscar sobre las salvedades de folio y de anotacion

                validate_FolioHasSalvedades: {

                   try {

                    String param_IdWorkflow;
                    String param_IdMatricula;

                    long result_CountSalvedadesFolio;
                    long result_CountSalvedadesAnotacion;

                    param_IdWorkflow = turno.getIdWorkflow();
                    param_IdMatricula = null;

                    Turno local_Turno;
                    Folio local_Folio;

                    local_Turno = hermod.getTurnobyWF( param_IdWorkflow );

                    if( null == local_Turno ) {
                       throw new CorreccionImpresionCertificadoException( "Turno Identificado con id " + param_IdWorkflow + " no encontrado" );
                    }



                    List local_TurnoSolicitudFoliosList;
                    Iterator local_TurnoSolicitudFoliosListIterator;
                    Folio local_TurnoSolicitudFoliosListElement;

                    local_TurnoSolicitudFoliosList = folios;//local_Turno.getSolicitud().getSolicitudFolios();
                    if( null == local_TurnoSolicitudFoliosList ) {
                       local_TurnoSolicitudFoliosList = new ArrayList();
                    }

                    local_TurnoSolicitudFoliosListIterator = local_TurnoSolicitudFoliosList.iterator();

                    for( ;local_TurnoSolicitudFoliosListIterator.hasNext();){
                       local_TurnoSolicitudFoliosListElement = (Folio)local_TurnoSolicitudFoliosListIterator.next();
                       param_IdMatricula = local_TurnoSolicitudFoliosListElement.getIdMatricula();
                       local_Folio = forseti.getFolioByMatricula( param_IdMatricula );
// ok sin anotaciones
                       if( null == local_Folio ) {
                         throw new CorreccionImpresionCertificadoException( "Folio Identificado con matricula " + param_IdMatricula + " no encontrado" );
                       }

                       result_CountSalvedadesFolio = forseti.countSalvedadesFolio( param_IdMatricula,param_IdWorkflow,null );
                       result_CountSalvedadesAnotacion = forseti.countSalvedadesAnotacion( param_IdMatricula,param_IdWorkflow,null );


                       if( 0L == ( result_CountSalvedadesFolio + result_CountSalvedadesAnotacion )  ) {
                          throw new CorreccionImpresionCertificadoException( "Folio no tiene salvedades registradas, no se permite impresion: (Folio:" + param_IdMatricula + ", Turno:" + param_IdWorkflow + ")." );
                       } // if

                    } // for

                  }
                  catch( CorreccionImpresionCertificadoException e ){
                    throw e;
                  }
                  catch( ForsetiException e ){
                    throw new CorreccionImpresionCertificadoException( e.getMessage() );
                  }
                  catch( Throwable t2 ) {
                    t2.printStackTrace();
                    throw new EventoException(t2.getMessage());
                  } // try

                } // :validate_FolioHasSalvedades

                // ------------------------------------------------------------





		try {
			for (int i = 0; i < folios.size(); i++) {
				Folio folio = (Folio) folios.get(i);
				folio = forseti.getFolioByMatricula(folio.getIdMatricula());
// se necesitan todas las anotaciones debido a que es para certificado
				
				FolioPk fid = new FolioPk();
				fid.idMatricula = folio.getIdMatricula();
				
				List anotaciones = folio.getAnotaciones();
				if (anotaciones == null || anotaciones.isEmpty())
				{
					anotaciones = forseti.getAnotacionesFolio(fid);
					folio.setAnotaciones(anotaciones);
				}

				List padres = forseti.getFoliosPadre(fid);
				List hijos = forseti.getFolioHijosEnAnotacionesConDireccion(fid);
                                /**
                                * @author: David Panesso
                                * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                                * Nuevo listado de folios derivados
                                **/
                                List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

				//Obtener la solicitud de correccion completa
				SolicitudCorreccion solCorreccion = (SolicitudCorreccion) hermod.getSolicitudById(turno.getSolicitud().getIdSolicitud());

				//Antes de imprimir se mira si existe una solcitudHija  (SolicitudCertificado) con la matricula a imprimir
				boolean yaImpreso = false;
				SolicitudCertificado solCertificado = null;
				SolicitudFolio solFolio = null;
				List sHijas = solCorreccion.getSolicitudesHijas();
				for(Iterator itHijas = sHijas.iterator(); itHijas.hasNext();){
					SolicitudAsociada solAsoc = (SolicitudAsociada) itHijas.next();
					Solicitud sol = hermod.getSolicitudById(solAsoc.getIdSolicitud1());
					if(sol instanceof SolicitudCertificado){
						SolicitudCertificado solCertificadoTemp =  (SolicitudCertificado)sol;
						SolicitudFolio solFolioTemp = (SolicitudFolio)solCertificadoTemp.getSolicitudFolios().get(0);
						if(solFolioTemp.getIdMatricula().equals(fid.idMatricula)){
							yaImpreso = true;
							solCertificado = solCertificadoTemp;
							solFolio = solFolioTemp;
							break;
						}
					}
				}

				if(yaImpreso){
					//si ya se imprimio validar que no se haya extendido el numero de impresiones validas
					if(solCertificado.getNumImpresiones()>Integer.parseInt(hermod.getNumeroImpresionesCertificadosEnCorrecciones())){
					  	throw new CorreccionImpresionCertificadoException("La matricula  "+ solFolio.getIdMatricula() +" ya sobrepaso el numero de impresiones permitidas");
					}else{
					  	solCertificado.setNumImpresiones(solCertificado.getNumImpresiones()+1);
					  	hermod.updateSolicitudCertificado(solCertificado);
					}

				}else{
					SolicitudCertificado solCertificadoACrear = new SolicitudCertificado();
					solCertificadoACrear.setNumImpresiones(1);
					solCertificadoACrear.setUsuario(evento.getUsuarioSIR());
					solCertificadoACrear.setCirculo(solCorreccion.getCirculo());
					ProcesoPk pid = new ProcesoPk();
					pid.idProceso = Long.parseLong(CProceso.PROCESO_CERTIFICADOS);
					Proceso pro= hermod.getProcesoById(pid);
					solCertificadoACrear.setProceso(pro);
					solCertificadoACrear.setCiudadano(solCorreccion.getCiudadano());
					//crear la solicitudFolio de la matricula
					SolicitudFolio solFolioACrear= new SolicitudFolio();
					solFolioACrear.setFolio(folio);
					solCertificadoACrear.addSolicitudFolio(solFolioACrear);
					Solicitud sol = hermod.crearSolicitud(solCertificadoACrear);

					/*servicio de añadir solicittudCertificado a SolicitudHija.*/
					solCorreccion = (SolicitudCorreccion)hermod.addSolicitudHija(solCorreccion, sol);
					if(solCorreccion==null){
						throw new Exception("fallo en el servicio de añadir solicitud Hija");
					}

				}

				String fechaImpresion= this.getFechaImpresion();
//				obtener textos base de los separadores
				String tbase1 ="";
				String tbase2 = "";
				List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
				for(Iterator it= variablesImprimibles.iterator(); it.hasNext();){
					OPLookupCodes op = (OPLookupCodes) it.next();
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
						tbase1= op.getValor();
					}
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
						tbase2 = op.getValor();
					}
				}
				ImprimibleCertificado imprimibleCertificado = new ImprimibleCertificado(folio, turno, padres, hijos, foliosDerivadoHijos, fechaImpresion, CTipoImprimible.CERTIFICADO_INMEDIATO,tbase1, tbase2);
				imprimibleCertificado.setPrintWatermarkEnabled(true);
				imprimibleCertificado.setImprimirUsuarioSalvedad(true);
                                  /**
                                 * @author     : Carlos Torres
                                 * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                 */
                                Traslado traslado = hermod.getFolioDestinoTraslado(folio.getIdMatricula());
                                imprimibleCertificado.setInfoTraslado(traslado);



                // datos para firmas -----------------
                CirculoPk cid = new CirculoPk();
                cid.idCirculo = turno.getIdCirculo();


                String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;

                FirmaRegistrador firmaRegistrador = null;

                String nombre = "";
                String archivo = "";
                String cargoToPrint = "";
                String rutaFisica = null;

                try
                {
                            firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);

                            if(firmaRegistrador==null){
                                    cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
                                    firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
                            }
                            if(firmaRegistrador==null){
                                    cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
                                    firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
                            }
                            if(firmaRegistrador==null){
                                    cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
                                    firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
                            }

                            rutaFisica =  hermod.getPathFirmasRegistradores();

                            nombre = firmaRegistrador.getNombreRegistrador();
                            archivo = firmaRegistrador.getIdArchivo();

                            //Se recupera el verdadero cargo para definir si es ENCARGADO o
                            //no lo es.
                            cargo = firmaRegistrador.getCargoRegistrador();

                            //Se saca el valor del cargo para imprimirlo en el certificado
                            List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

                            cargoToPrint = "";
                            OPLookupCodes lookUp;
                            for(Iterator it = cargos.iterator(); it.hasNext();){
                                    lookUp = (OPLookupCodes) it.next();
                                    if(lookUp.getCodigo().equals(cargo)){
                                            cargoToPrint = lookUp.getValor();
                                    }
                            }
                } catch(CorreccionImpresionCertificadoException e){
                	throw e;
                } catch(Throwable t)
                {
                    t.printStackTrace();
                }
                // fix data to paper

                if (nombre==null)
                  nombre="";


                imprimibleCertificado.setCargoRegistrador(cargoToPrint);
                imprimibleCertificado.setNombreRegistrador(nombre);


                if(rutaFisica!=null)
                {
                        BufferedImage imagenFirmaRegistrador=getImage(rutaFisica,archivo);

                        byte pixeles[]=null;
                        try
                        {
                                pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                        }
                        catch (Throwable e1) {

                                e1.printStackTrace();
                        }
                    imprimibleCertificado.setPixelesImagenFirmaRegistrador(pixeles);
                }


                // ----------------------------------------------


                imprimir( imprimibleCertificado, evento.getCirculoId(), evento.getImpresora());
			}


		} catch(CorreccionImpresionCertificadoException e){
        	throw e;
        } catch (Throwable t2) {
			t2.printStackTrace();
			throw new EventoException(t2.getMessage());
		}

		return ev;
	}



        public static BufferedImage getImage(String path, String nombreArchivo)
        {
                String nombreCompleto = getNombreCompleto(path,nombreArchivo);
                BufferedImage buf = null;

                try
                {
                        File file = new File(nombreCompleto);
                        buf = ImageIO.read(file);
                }
                catch (IOException e)
                {
                        Log.getInstance().error(ANCorreccion.class,e);
                        Log.getInstance().debug(ANCorreccion.class,"Error imprimiendo el gráfico en la ruta: "+nombreCompleto);

                }

                return buf;
	}

        public static String getNombreCompleto(String path, String nombreArchivo)
        {

                String nombreCompleto=null;

                if (!path.trim().equals(CHermod.CADENA_VACIA))
                  nombreCompleto = path + nombreArchivo;
                else
                  nombreCompleto = nombreArchivo;



          return nombreCompleto;
	}







	private List obtenerNumeroCopias(Turno turno, List folios) throws EventoException {

		List rtn = new ArrayList();
		for(int i = 0; i < folios.size(); i++){
				rtn.add(i, new Integer(-1));	// inicializado en -1
		}

		Turno turnoAnt = turno.getSolicitud().getTurnoAnterior();
		if (turnoAnt == null){
			return rtn;
		}

		try {
			turnoAnt = hermod.getTurnobyWF(turnoAnt.getIdWorkflow());
			if (turnoAnt == null) {
				throw new EventoException("No se pudo obtener la informacion del turno");
			}
		} catch (HermodException e) {
			throw new EventoException(
				"Hermod: El turno asociado no es válido" + e.getMessage(),
				e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,
				"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		Solicitud solAnt = turnoAnt.getSolicitud();

		if (solAnt instanceof SolicitudCertificado) {
			SolicitudCertificado solcert = (SolicitudCertificado) solAnt;
			List foliosAnt = solcert.getSolicitudFolios();
			int numImp = solcert.getNumeroCertificados();

			for(int i = 0; i < folios.size(); i++){
				for(int j = 0; j < foliosAnt.size(); j++){
					SolicitudFolio folioAnt = (SolicitudFolio) foliosAnt.get(j);
					SolicitudFolio folioActual = (SolicitudFolio) folios.get(i);
					if (folioAnt.getIdMatricula().equals(folioActual.getIdMatricula())){
						rtn.add(i, new Integer(numImp));
					}
				}
			}
		}

		if (solAnt instanceof SolicitudRegistro) {
			SolicitudRegistro solreg = (SolicitudRegistro) solAnt;
			List solAsociadas = solreg.getSolicitudesHijas();

			for(int k = 0; k < solAsociadas.size(); k++){
				SolicitudAsociada solAs = (SolicitudAsociada) solAsociadas.get(k);
				Solicitud solHija = solAs.getSolicitudHija();

				if (solHija instanceof SolicitudCertificado) {
					SolicitudCertificado solcert = (SolicitudCertificado) solHija;

					try{
						solcert = (SolicitudCertificado) hermod.getSolicitudById(solcert.getIdSolicitud());
					} catch (HermodException e) {
						throw new EventoException(
							"Hermod: La solicitud no es válida" + e.getMessage(),
							e);
					} catch (Throwable e) {
						ExceptionPrinter printer = new ExceptionPrinter(e);
						Log.getInstance().error(ANCorreccion.class,
							"Ha ocurrido una excepcion inesperada " + printer.toString());
						throw new EventoException(e.getMessage(), e);
					}

					List foliosAnt = solcert.getSolicitudFolios();
					int numImp = solcert.getNumeroCertificados();

					for(int i = 0; i < folios.size(); i++){
						for(int j = 0; j < foliosAnt.size(); j++){
							SolicitudFolio folioAnt = (SolicitudFolio) foliosAnt.get(j);
							SolicitudFolio folioActual = (SolicitudFolio) folios.get(i);
							if (folioAnt.getIdMatricula().equals(folioActual.getIdMatricula())){
								if ( ! rtn.get(i).equals(new Integer(-1)) ){
									String matricula = ((Folio) folios.get(i)).getIdMatricula();
									throw new EventoException("El folio " + matricula +
										" esta incluido en dos Solicitudes" +
										" de Certificados" +
										" Hijas de la Solicitud de registro Anterior");
								}
								rtn.add(i, new Integer(numImp));
							}
						}
					}
				}
			}
		}
		return rtn;
	}


	/**
	 * Método que imprime los certificados que se solicitan.
	 * @param turno
	 * @param parametros
	 * @param folios
	 * @return
	 * @throws Throwable
	 */
	private List imprimirCertificados(Turno turno, List folios, String cid, String impresora) throws Throwable {

		List numcopias = obtenerNumeroCopias(turno, folios);
		for (int i = 0; i < folios.size(); i++) {

			int copias = ((Integer)numcopias.get(i)).intValue();

			for (int j = 0; j < copias; j++){
				Folio folio = ((SolicitudFolio) folios.get(i)).getFolio();
				folio = forseti.getFolioByMatricula(folio.getIdMatricula());

				FolioPk fid = new FolioPk();
				fid.idMatricula = folio.getIdMatricula();
				
				List anotaciones = folio.getAnotaciones();
				if (anotaciones == null || anotaciones.isEmpty())
				{
					anotaciones = forseti.getAnotacionesFolio(fid);
					folio.setAnotaciones(anotaciones);
				}

				List padres = forseti.getFoliosPadre(fid);
				List hijos = forseti.getFolioHijosEnAnotacionesConDireccion(fid);
                                /**
                                * @author: David Panesso
                                * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                                * Nuevo listado de folios derivados
                                **/
                                List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

				String fechaImpresion= this.getFechaImpresion();
				//obtener textos base de los separadores
				String tbase1 ="";
				String tbase2 = "";
				List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
				for(Iterator it= variablesImprimibles.iterator(); it.hasNext();){
					OPLookupCodes op = (OPLookupCodes) it.next();
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
						tbase1= op.getValor();
					}
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
						tbase2 = op.getValor();
					}
				}
				ImprimibleCertificado imprimibleCertificado = new ImprimibleCertificado(folio, turno, padres, hijos, foliosDerivadoHijos, fechaImpresion, CTipoImprimible.CERTIFICADO_INMEDIATO, tbase1, tbase2);
				imprimibleCertificado.setPrintWatermarkEnabled(true);
				imprimir(imprimibleCertificado, cid, impresora);
			}
		}
		List foliosNoImpresos = new ArrayList();
		for (int i = 0; i < folios.size(); i++) {
			int copias = ((Integer)numcopias.get(i)).intValue();
			if (copias == -1){
				//TFS 3790: NO SE PUEDEN IMPRIMIR FOLIOS ANULADOS
				SolicitudFolio solFolio = (SolicitudFolio)folios.get(i);
				Folio folio = forseti.getFolioByMatricula(solFolio.getFolio().getIdMatricula());
				if(!folio.getEstado().getIdEstado().equals(CEstadoFolio.ANULADO)){
					boolean tieneSalvedades = false;
					//TFS 4578: SOLO SE PUEDEN IMPRIMIR LOS FOLIOS QUE TIENEN SALVEDADES
					if(folio.getSalvedades()!=null){
						Iterator itSalvedadesFolio = folio.getSalvedades().iterator();
						//SE REVISAN LAS SALVEDADES DEL FOLIO
						while(itSalvedadesFolio.hasNext()){
							SalvedadFolio salvedadFolio = (SalvedadFolio)itSalvedadesFolio.next();
							if(salvedadFolio.getNumRadicacion().equals(turno.getIdWorkflow())){
								tieneSalvedades = true;
								break;
							}
						}
					}
					//SI NO HAY SALVEDADES DEL FOLIO, SE REVISAN LAS DE LAS ANOTACIONES
					if(!tieneSalvedades){
						FolioPk fid = new FolioPk();
						fid.idMatricula = folio.getIdMatricula();
						List anotacionesSalvedades = forseti.getAnotacionesConSalvedades(fid);
						if (anotacionesSalvedades != null)
						{
							for (Iterator anotaSalvItera = anotacionesSalvedades.iterator();
									anotaSalvItera.hasNext(); )
							{
								Anotacion anotacion = (Anotacion)anotaSalvItera.next();
								List salvedades = anotacion.getSalvedades();
								if (salvedades != null)
								{
									Iterator itSalvedadesAnotaciones = salvedades.iterator();
									while(itSalvedadesAnotaciones.hasNext()){
										SalvedadAnotacion salvedadAnotacion = 
											(SalvedadAnotacion)itSalvedadesAnotaciones.next();
										if(salvedadAnotacion.getNumRadicacion().equals(turno.getIdWorkflow())){
											tieneSalvedades = true;
											break;
										}
									}
								}
							}
							
						}
					}
					if(tieneSalvedades){
						foliosNoImpresos.add(folios.get(i));
					}
				}
			}
		}

		return foliosNoImpresos;

	}



	//DEPURADO ENERO 17 2006
	private void imprimir(ImprimibleBase imprimible, String cid, String impresora) {

		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
		int maxIntentos;
		int espera;

		//INGRESO DE INTENTOS DE IMPRESION
		try {

			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null) {
				Integer intentos = new Integer(intentosImpresion);
				maxIntentos = intentos.intValue();
			} else {
				Integer intentosDefault =
					new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
				maxIntentos = intentosDefault.intValue();
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null) {
				Integer esperaInt = new Integer(esperaImpresion);
				espera = esperaInt.intValue();
			} else {
				Integer esperaDefault =
					new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}


		} catch (Throwable t) {
			Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();

		}

		Bundle bundle = new Bundle(imprimible);

		//INGRESO IMPRESORA SELECCIONADA
	    if(impresora!=null){
	       	bundle.setNombreImpresora(impresora);
	    }

		try {
			//se manda a imprimir el recibo por el identificador unico de usuario
			printJobs.enviar(cid, bundle, maxIntentos, espera);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}


    //DEPURADO ENERO 17 2006
	/**
	 * Avanza el turno de la fase de impresión en el proceso de pago por mayor valor.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta avanzarImprimir(EvnCorreccion evento) throws EventoException {

		//1. Obtener parámetros desde el evento.
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		//2. Construir tabla de parámetros para realizar el avance del turno.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}


		//4. Realizar el avance del turno (Servicio Hermod).
		try {
			hermod.avanzarTurnoNuevoNormal(turno, fase, tabla,evento.getUsuarioSIR());
		} catch (HermodException e) {
			throw new ANCorreccionException("No se pudo avanzar el turno en la impresión", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"No se pudo avanzar el turno en la impresión " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.AVANZAR_IMPRIMIR);

		return evRespuesta;
	}





    //DEPURADO ENERO 17 DE 2006
	/**
	 * Notifica la ciudadano el resultado del proceso de correcciones
	 * ya sea que haya sido aprobada o negada la solicitud. Realiza el correspondiente
	 * avance del turno.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta notificarCiudadano(EvnCorreccion evento) throws EventoException {

		//1. Obtener parámetros desde el evento.
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		//2. Construir tabla para realizar el avance del turno.
		Hashtable tabla = new Hashtable();
		tabla.put("RESULT", evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}


		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}

		//4. Realizar el avance del turno (Hermod).
		try {
			hermod.avanzarTurnoNuevoNormal(turno, fase, tabla,usr);
                        /**
                        * @author Fernando Padilla Velez
                        * @change Acta - Requerimiento No 178 - Integración Gestión Documental y SIR,
                        *         Se agrega esta fragmento de codigo para la publicacion del mensaje del turno creado.
                        */
                        /**
                         * @author Fernando Padilla Velez
                         * @change 6760: Acta - Requerimiento No 191 - Pantalla Administrativa SGD,
                         *         Se realizó refactoring al proceso y ya no son necesarias.
                         */
                        SGD sgd = new SGD(turno, usr.getUsername());
                        sgd.enviarEstadoTurno();
		} catch (HermodException e) {
			throw new ANCorreccionException("No fue posible realizar el avance del turno.", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCorreccion.class,"No fue posible realizar el avance del turno. " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.NOTIFICAR_CIUDADANO);

		return evRespuesta;
	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta consultarUltimosPropietarios(EvnCorreccion evento) throws EventoException {
	
		Usuario usuarioNeg = evento.getUsuarioSIR();
		Folio folio = evento.getFolio();
		List listaUltimosPropietarios = new ArrayList();

		
		try {
			listaUltimosPropietarios = forseti.getCiudadanoUltimosFolio(folio.getIdMatricula());
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCorreccion.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new ANCorreccionException("No se pudieron grabar las anotaciones temporales", e1);

		}
		
		Map mapaPropietarios = new HashMap();
		
		for (int i = 0; i < listaUltimosPropietarios.size(); i++ ) {
			AnotacionCiudadano anotacionciu = (AnotacionCiudadano)listaUltimosPropietarios.get(i);
		    Ciudadano ciu = anotacionciu.getCiudadano();    
		    String key = ciu.getTipoDoc() + "-" + ciu.getDocumento();
		    if (!mapaPropietarios.containsKey(key)){
		    	mapaPropietarios.put(key,anotacionciu);	
		    }		    
		}
		
		Collection col = (Collection)mapaPropietarios.values();
		
		listaUltimosPropietarios = new ArrayList();
		
		for (Iterator iter = col.iterator(); iter.hasNext();) {
			listaUltimosPropietarios.add((AnotacionCiudadano) iter.next());
		}
		
		//listaUltimosPropietarios = (List) mapaPropietarios.values();
		Collections.sort(listaUltimosPropietarios, new CiudadanoComparator());
		Collections.reverse(listaUltimosPropietarios);
		
		EvnRespCorreccion evn = new EvnRespCorreccion(EvnRespCorreccion.CONSULTAR_PROPIETARIOS_FOLIO);
		evn.setTipoEvento(EvnRespCorreccion.CONSULTAR_PROPIETARIOS_FOLIO);
		evn.setPropietariosFolios(listaUltimosPropietarios);
		return evn;
	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta validarCiudadanos(EvnCorreccion evento) throws EventoException {
	
		List anotacionCiudadanos = evento.getAnotacionCiudadanos();
		Turno turno = evento.getTurno();
		
		try {
			Iterator itt = anotacionCiudadanos.iterator();
			while(itt.hasNext()){
				AnotacionCiudadano anotaCiudadano = (AnotacionCiudadano)itt.next();
				Ciudadano ciu = anotaCiudadano.getCiudadano();
				CiudadanoTMP ciudTemp = null;
				try {
					ciudTemp = forseti.getCiudadanoTmpByDocumento(ciu.getTipoDoc(), ciu.getDocumento(), ciu.getIdCirculo());	
				} catch (Throwable ee) {}
				
				if (ciudTemp!=null) {
					if (ciudTemp.getNumeroRadicacion()!=null && !ciudTemp.getNumeroRadicacion().equals(turno.getIdWorkflow())) {
						throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
					} 
				}
			}
			
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCorreccion.class,"No se pudieron agregar los ciudadanos: "+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron agregar los ciudadanos",e1);

		}
		
		EvnRespCorreccion evn = new EvnRespCorreccion(EvnRespCorreccion.VOLVER_AGREGAR_CIUDADANOS);
		evn.setTipoEvento(EvnRespCorreccion.VOLVER_AGREGAR_CIUDADANOS);
		evn.setListaCompletaCiudadanos(evento.getListaCompletaCiudadanos());
		return evn;
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

	/**
	 * Metodo que retorna la cadena con la fecha actual de impresión.
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




   protected EventoRespuesta
   correccionRevisarAprobar_DeshacerCambios( EvnCorreccion evento )
   throws EventoException {



      // in ----------------------------------
      // :: unwrap message

      // turno
      Turno local_Turno;
      local_Turno = evento.getTurno();

      // turno.solicitud
      Solicitud local_TurnoSolicitud;
      local_TurnoSolicitud = local_Turno.getSolicitud();
      
       /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: variable de turnoAnterior
        */
      Turno turnoAnterior = local_TurnoSolicitud.getTurnoAnterior();

      // turno.solicitud.solicitudFolios
      // List< Folio >
      List local_TurnoSolicitudFolios;
      local_TurnoSolicitudFolios = local_TurnoSolicitud.getSolicitudFolios();

      // usuario
      gov.sir.core.negocio.modelo.Usuario local_UsuarioSir;

      local_UsuarioSir = evento.getUsuarioSir();

      // -------------------------------------

      // pr ----------------------------------
               /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: validacion de turno de testamento
        */
      if(turnoAnterior ==null ||(turnoAnterior!=null && turnoAnterior.getIdProceso()!=6)){
      Iterator local_TurnoSolicitudFoliosIterator = local_TurnoSolicitudFolios.iterator();

      Folio local_ElementFolio;
      SolicitudFolio local_ElementSolicitudFolio;
      for( ;local_TurnoSolicitudFoliosIterator.hasNext(); ){

         local_ElementSolicitudFolio = (SolicitudFolio) local_TurnoSolicitudFoliosIterator.next();
         local_ElementFolio = local_ElementSolicitudFolio.getFolio();

         try {
        	 	forseti.deshacerCambiosFolio( local_ElementFolio, local_UsuarioSir);
        	 	
        	 	if(!local_ElementFolio.isDefinitivo()){
         	 		FolioPk folioPk = new FolioPk();
         	 		folioPk.idMatricula = local_ElementFolio.getIdMatricula();
         	 		forseti.deleteFolio(folioPk,"DESHACER CAMBIOS TEMPORALES");
         	 	}
         }
         catch( HermodException e ) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios " + "del folio " + local_ElementFolio.getIdMatricula() + ": " +  printer.toString());
            throw new EventoException(e.getMessage(), e);
         }
         catch (Throwable e) {
              ExceptionPrinter printer = new ExceptionPrinter(e);
              Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios " + "del folio " + local_ElementFolio.getIdMatricula() + ": " +  printer.toString());
              throw new EventoException(e.getMessage(), e);
         }

      } // for
      
      //Se eliminan los ciudadanos temporales hechos con el turno
      try {
    	  TurnoPk turnoid = new TurnoPk();
    	  turnoid.anio = local_Turno.getAnio();
    	  turnoid.idCirculo = local_Turno.getIdCirculo();
    	  turnoid.idProceso = local_Turno.getIdProceso();
    	  turnoid.idTurno = local_Turno.getIdTurno();
    	  
  	 	  forseti.deshacerCambiosCiudadanosTurno(turnoid, local_UsuarioSir, false);
	   }
	   catch( HermodException e ) {
	      ExceptionPrinter printer = new ExceptionPrinter(e);
	      Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios " + "del turno " + local_Turno.getIdWorkflow() + ": " +  printer.toString());
	      throw new EventoException(e.getMessage(), e);
	   }
	   catch (Throwable e) {
	        ExceptionPrinter printer = new ExceptionPrinter(e);
	        Log.getInstance().error(ANCorreccion.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios " + "del turno " + local_Turno.getIdWorkflow() + ": " +  printer.toString());
	        throw new EventoException(e.getMessage(), e);
	   }
      }else{
                   /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: sentencia en caso de ser turno de correccion
        */
            try {
                TestamentoSIR testamentoSir = new TestamentoSIR();
                testamentoSir.deshacerCambiosTestamento(turnoAnterior.getSolicitud().getIdSolicitud(),local_Turno.getIdWorkflow());
            }
            catch (GeneralSIRException ex) {
                throw new EventoException(ex.getMessage());
            }
      }
      //DEVOLVER A REVISION
      
	  Fase fase = evento.getFase();
	  gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

	  //2. Construcción de tabla de parámetros para realizar el avance del turno.
	  Hashtable tabla = new Hashtable();
	  tabla.put("RESULT", evento.getRespuestaWF());
	  tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
	  
	  List listaHistorials = local_Turno.getHistorials();
      String estacionRevisionInicial = null;
 
      if (listaHistorials != null)
      {
      	for (int hist=(listaHistorials.size()-1); hist >= 0; hist--)
      	{
      		TurnoHistoria turnoHistoriaTemp = (TurnoHistoria)listaHistorials.get(hist);
      		if (turnoHistoriaTemp != null)
      		{
      			if (turnoHistoriaTemp.getFase().equals(CFase.COR_REVISION_ANALISIS))
      			{
      				estacionRevisionInicial = turnoHistoriaTemp.getIdAdministradorSAS();
      				Log.getInstance().debug(ANCorreccion.class, "Administrador SAS: "+estacionRevisionInicial);
      				break;
      			}
      		}
      	}
      }
      
      if (estacionRevisionInicial != null)
      {
    	  tabla.put(Processor.ESTACION,estacionRevisionInicial);
    	  tabla.put(Processor.CONDICION_AVANCE,CFase.COR_REVISION_ANALISIS);
      }
		
      try {

			hermod.avanzarTurnoNuevoCualquiera(local_Turno, fase, tabla,evento.getUsuarioSIR());
		} catch (HermodException e) {

		} catch (Throwable e) {

			throw new ANCorreccionException("No se pudo Devolver a Revisión la solicitud de corrección. No fue" +
											 "posible avanzar el turno.", e);
		}
      // -------------------------------------

      // ou ----------------------------------
      /*EvnRespCorreccion local_Result;

      local_Result = new EvnRespCorreccion( EvnRespCorreccion.PAGEEVENT_CORRECCIONREVISARAPROBAR_DESHACERCAMBIOS_EVENTRESPOK );

      return local_Result;*/
      
      EvnRespCorreccion evRespuesta = null;
      evRespuesta = new EvnRespCorreccion(evento.getUsuario(), local_Turno, EvnRespCorreccion.NEGAR);

	 return evRespuesta;
		
      // -------------------------------------

   } // end-method: correccionRevisarAprobar_DeshacerCambios



   /**
    * Método que consulta una lista con Objetos WEBSEGENG, que son segregaciones o englobes en trámite.
	* @param evento
	* @return
	* @throws EventoException
	*/
   private EventoRespuesta consultarSegEngExistentes(EvnCorreccion evento) throws EventoException
   {
	   Turno turno=evento.getTurno();
	   List rta = null;

	   try {
		   Solicitud solicitud = turno.getSolicitud();
		   SolicitudPk idSolicitud = new SolicitudPk();
		   idSolicitud.idSolicitud = solicitud.getIdSolicitud();
		   rta = hermod.getWebSegEngBySolicitud(idSolicitud);
	   } catch(HermodException he){
		   throw new SegregacionException("No se pudo consultar si hay englobes o segregaciones en curso.",he);
	   } catch (Throwable te) {
		   throw new SegregacionException("No se pudo consultar si hay englobes o segregaciones en curso.",te);
	   }

	   Folio folio = evento.getFolio();
	   OficinaOrigen oficinaOrigen = null;
	   String sOrden = "";
	   if(folio!=null){
		   FolioPk folioID = new FolioPk();
		   folioID.idMatricula=folio.getIdMatricula();
		   long orden = 0;

		   //SOLICITAR AL SERVICIO
		   try {
			   orden = forseti.getNextOrdenAnotacion(folioID);
		   } catch(ForsetiException he){
			   throw new SegregacionException("No se pudo consultar si hay englobes o segregaciones en curso.",he);
		   } catch (Throwable te) {
			   throw new SegregacionException("No se pudo consultar si hay englobes o segregaciones en curso.",te);
		   }


		   if(rta!=null){
				Iterator it = rta.iterator();
				while(it.hasNext()){
					WebSegEng segEng = (WebSegEng)it.next();

					if(segEng instanceof WebSegregacion){

						WebSegregacion webSegregacion = (WebSegregacion)segEng;
						if(webSegregacion.getFoliosHeredados()!=null && webSegregacion.getFoliosHeredados().size()>0){
							WebFolioHeredado webFolioHeredado = (WebFolioHeredado)webSegregacion.getFoliosHeredados().get(0);
							if(folio.getIdMatricula().equals(webFolioHeredado.getIdMatricula())){
								WebAnotacion webAnotacion = webSegregacion.getAnotacion();
								if(webAnotacion!=null&& webAnotacion.getDocumento()!=null && webAnotacion.getDocumento().getIdOficinaOrigen()!=null){
									OficinaOrigenPk oid = new OficinaOrigenPk();
									oid.idOficinaOrigen = webAnotacion.getDocumento().getIdOficinaOrigen();
                                                                           /*
                                                                            *  @author Carlos Torres
                                                                            *  @chage   se agrega validacion de version diferente
                                                                            *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                                                            */
                                                                        oid.version = webAnotacion.getDocumento().getVersion();
									try{
										oficinaOrigen = forseti.getOficinaOrigen(oid);
									}catch(Throwable t){
									}
								}
							}
						}

					}
				}
		   }

		   //CAST
		   sOrden=String.valueOf(orden);
	   }

           // bug 05370
           // turno/solicitud[]/solicitudfolio[]/folio/estado debe existir para poderse chequear
           //
           Map querySolicitudFolioEstadoMap = null;

           local_Block_PopulateSolicutudFolioEstado: {

             Turno local_Turno;
             gov.sir.core.negocio.modelo.Usuario local_UsuarioSir;

             local_Turno = turno;
             local_UsuarioSir = evento.getUsuarioSir();

             Map local_Result;
             local_Result = new HashMap();

             try {

               TurnoPk local_TurnoId;
               local_TurnoId = new TurnoPk();
               local_TurnoId.anio      = local_Turno.getAnio();
               local_TurnoId.idCirculo = local_Turno.getIdCirculo();
               local_TurnoId.idProceso = local_Turno.getIdProceso();
               local_TurnoId.idTurno   = local_Turno.getIdTurno();

               // estar seguro que el turno ahora tiene esa info
               // se envia a web un mapa para que se pueda consultar;
               // Map< String idMatricula, Folio folioObj >

               local_Turno = hermod.getTurno( local_TurnoId );

               // iterate over turno/solicitud/solicitudfolios[]
               List local_TSSolicitudFolios = null;
               try {
                 local_TSSolicitudFolios = local_Turno.getSolicitud().getSolicitudFolios();
               }
               catch( Exception e ) {
               }

               if( null == local_TSSolicitudFolios ) {
                 local_TSSolicitudFolios = new ArrayList();
               }
               SolicitudFolio local_TSSolicitudFoliosElement;
               String local_TSSolicitudFoliosElement_IdMatricula;
               Folio local_FolioElement;
               for( Iterator local_TSSolicitudFoliosIterator = local_TSSolicitudFolios.iterator(); local_TSSolicitudFoliosIterator.hasNext(); ){
                 local_TSSolicitudFoliosElement = (SolicitudFolio)local_TSSolicitudFoliosIterator.next();
                 local_TSSolicitudFoliosElement_IdMatricula = local_TSSolicitudFoliosElement.getIdMatricula();
                 local_FolioElement = forseti.getFolioByIDSinAnotaciones( new FolioPk( local_TSSolicitudFoliosElement_IdMatricula ) , local_UsuarioSir );

                 // revisar el estado ------------------------

                 EstadoFolio est = new EstadoFolio();
                 if(forseti.cerradoFolio(local_TSSolicitudFoliosElement_IdMatricula, null)){
                         est.setIdEstado(CEstadoFolio.CERRADO);
                 }
                 else{
                         est.setIdEstado(CEstadoFolio.ACTIVO);
                 }
                 
                 // local_FolioElement es nulo cuando el folio es temporal
                 if (local_FolioElement != null)
                 {
	                 local_FolioElement.setEstado( est );
	                 // -------------------------------------------
	                 local_Result.put( local_TSSolicitudFoliosElement_IdMatricula, local_FolioElement );
                 }
               } // for

             }
             catch(HermodException he){
                     throw new SegregacionException("No se pudo consultar el conjunto de solicitudes del turno.",he);
             }
             catch (Throwable te) {
                     throw new SegregacionException("No se pudo consultar el conjunto de solicitudes del turno.",te);
	     } // try

             querySolicitudFolioEstadoMap = local_Result;

           } // :local_Block_PopulateSolicutudFolioEstado


	   EvnRespCorreccion eventoResp = new EvnRespCorreccion(rta, EvnRespCorreccion.CONSULTAR_SEG_ENG_EXISTENTES);
	   eventoResp.setNextOrden(sOrden);
	   eventoResp.setOficinaOrigen(oficinaOrigen);
           eventoResp.setQuerySolicitudFolioEstadoMap( querySolicitudFolioEstadoMap );
	   return eventoResp;
   }

   
   
   
	
	/**
	 * Método que aprueba la corrección, desde el rol de usuario especializado y hace
	 * definitivo los cambios que hay en las tablas temporales.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta aprobarCorreccionEspecializadoNuevo(EvnCorreccion evento) throws EventoException {

		//1. Obtener parámetros desde el evento de corrección
		EvnRespCorreccion evRespuesta = null;
		Fase fase = evento.getFase();
		Turno turno = evento.getTurno();
		Solicitud solicitud = turno.getSolicitud();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		if (solicitud == null) {
			throw new ANCorreccionException("No existe solicitud asociada");
		}


		// 2. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		//3. Se carga la lista de folios involucrados en la corrección.
		List solFolio = solicitud.getSolicitudFolios();

		List foliosID = new Vector();
		Iterator itSolFolio = solFolio.iterator();

		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
			FolioPk folioID = new FolioPk();
			folioID.idMatricula = sol.getFolio().getIdMatricula();
			foliosID.add(folioID);
		}
		
		
		/*****************************************/
		/*        ELIMINAR SAS                   */
		/*****************************************/
		Fase faseAvance = evento.getFase();    
		Hashtable parametrosAvance = new Hashtable();
	  	parametrosAvance.put(Processor.RESULT,evento.getRespuestaWF());
		             
		try 
		{
			hermod.avanzarTurnoNuevoPop(turno,faseAvance,parametrosAvance,usr);
		
		}
	    catch (Throwable exception) {
		    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
	    }


		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.APROBAR_ESPECIALIZADO);

		return evRespuesta;
	}
	
	
	private EventoRespuesta cambiarAnotacionesCorreccion(EvnCorreccion evento) throws EventoException {
		EvnRespCorreccion evRespuesta = null;
		Turno turno = evento.getTurno();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
		EvnCorreccionFolioPadresHijos thisEvent = (EvnCorreccionFolioPadresHijos)evento;
		List foliosDerivadosModificados=thisEvent.getSources();
		Folio folioPadre=evento.getFolio();
		gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioSIR();
		if(foliosDerivadosModificados!=null && foliosDerivadosModificados.size()>0){
			try {
				forseti.updateFolioDerivado(foliosDerivadosModificados,folioPadre,usr);
			} catch (Throwable e) {
				throw new EventoException ("Error Actualizando Folio Derivado.",e);
			}
		}
		evRespuesta = new EvnRespCorreccion(evento.getUsuario(), turno, EvnRespCorreccion.APROBAR_ESPECIALIZADO);
		
		List foliosDerivadoHijo=null;
		FolioPk id = new FolioPk();
		id.idMatricula = folioPadre.getIdMatricula();
		if(id!=null && u !=null){
			try {
				foliosDerivadoHijo=forseti.getFoliosDerivadoHijos(id, u);
			} catch (Throwable e) {
				throw new EventoException ("Error Consultando Folio Derivado.",e);
			}
			if(foliosDerivadoHijo!=null)
				evRespuesta.setFoliosDerivadoHijo(foliosDerivadoHijo);
		}
		List foliosDerHijosTmp;
		try {
			foliosDerHijosTmp = forseti.getFoliosDerivadosTMPByMatricula(folioPadre.getIdMatricula());
		} catch (Throwable e) {
			throw new EventoException ("Error Consultando Folio Derivado.",e);
		}
		if(foliosDerHijosTmp!=null && foliosDerHijosTmp.size()>0)
			evRespuesta.setFoliosDerHijosTmp(foliosDerHijosTmp);
		
		return evRespuesta;
	}

        /**
         * @param evento
         * @return
         * Caso mantis: 7275 Autor: Ellery Robles Gómez*/
        private EventoRespuesta anotacionesAsociadas(EvnCorreccion evento) throws EventoException {
            String existen = null;

            ValidacionesSIR validacion = new ValidacionesSIR();
            try {
                existen = validacion.NumeroAnotacionAsociadas(evento.getIdFolio(), evento.getIdAnotacion());
            } catch (GeneralSIRException ex) {
                Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
            }
            EvnRespCorreccion evRespuesta =
                    new EvnRespCorreccion(existen, EvnRespCorreccion.ANOTACIONES_ASOCIADAS_OK);
            return evRespuesta;
        }
       /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: validacion de turno de tetamento
        */
        
        /**
	 * Método que válida si un turno de testamento cumple con las codiciones para ser correjido
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta validarTurnoTestamento(EvnCorreccion evento) throws EventoException {
		String idTurno = evento.getTurnoTestamento().getIdWorkflow();
                ValidacionesSIR validar = new ValidacionesSIR();
		try {
			if (validar.isTurnoTestamentoValido(idTurno)) {
                                
				return new EvnRespCorreccion(evento.getUsuario(), evento.getTurnoTestamento(), EvnRespCorreccion.VALIDAR_TURNO_TESTAMENTO_OK);
			}
			throw new MatriculaInvalidaCorreccionException("El turno " + idTurno + " no cumple con alguna de las siguientes condiciones Acto testamento y/o Revocatoria de testamento, Resolución Inscrita, Estado Desanotado, Subtipo de solicitud TESTAMENTO.");
		} catch (MatriculaInvalidaCorreccionException e) {
			throw e;
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANLiquidacionCorreccion.class,"Se produjo un error validando la matrícula:" + ep.toString());
			throw new MatriculaInvalidaCorreccionException("Se produjo un error validando la matrícula", e);
		}
	}
       /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: tomar turno de testamento
        */
          /**
	 * Método que toma turno de testamento
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta tomarTurnoTestamento(EvnCorreccion evento) throws EventoException {
		String idTurno = evento.getTurnoTestamento().getIdWorkflow();
                ValidacionesSIR validar = new ValidacionesSIR();
		try {
			if (validar.isTurnoTestamentoValido(idTurno)) {
                                
				return new EvnRespCorreccion(evento.getUsuario(), evento.getTurnoTestamento(), EvnRespCorreccion.VALIDAR_TURNO_TESTAMENTO_OK);
			}
			throw new MatriculaInvalidaCorreccionException("El turno " + idTurno + " no no cumple con alguna de las siguientes condiciones Acto testamento, Resolución Inscrita, Estado Desanotado, Subtipo de solicitud TESTAMENTO");
		} catch (MatriculaInvalidaCorreccionException e) {
			throw e;
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANLiquidacionCorreccion.class,"Se produjo un error validando la matrícula:" + ep.toString());
			throw new MatriculaInvalidaCorreccionException("Se produjo un error validando la matrícula", e);
		}
	}
         /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: metodo que guarda los datos temporales de testamento
        */
    private EventoRespuesta guardarDatosTmpTestamento(EvnModificarTestamento evento) throws EventoException {
        try {
            Turno turno = evento.getTurno();
            Documento documentoEncabezado = evento.getEncabezadoDoc();
            Testamento testamento = evento.getTestamento();
            Usuario usuario = evento.getUsuarioSIR();
            String salvedad = evento.getSalvedad();
            TestamentoSIR tmpsir = new TestamentoSIR();
/*******************ENCABEZADO DOCUMENTO*******************************************************/
            Map documentoNew = new HashMap();
            documentoNew.put("fecha",documentoEncabezado.getFecha());
            documentoNew.put("numero",documentoEncabezado.getNumero());
            documentoNew.put("idOficina",documentoEncabezado.getOficinaOrigen().getIdOficinaOrigen());
            documentoNew.put("idTipoDoc",documentoEncabezado.getTipoDocumento().getIdTipoDocumento());
            documentoNew.put("comentario",documentoEncabezado.getComentario());
            documentoNew.put("oficinaInternacional",documentoEncabezado.getOficinaInternacional());
            documentoNew.put("circulo",documentoEncabezado.getCirculo());
            documentoNew.put("idDocumento",documentoEncabezado.getIdDocumento());
             /*
             *  @author Carlos Torres
             *  @chage   se agrega validacion de version diferente
             *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
             */
            documentoNew.put("version",documentoEncabezado.getOficinaOrigen().getVersion());
            tmpsir.setEncabezadoTMP(documentoNew, testamento.getIdTestamento(),turno.getIdWorkflow());
/*******************TESTAMENTO************************************************************/            
            Map testamentoNew = new HashMap();
            testamentoNew.put("idTestamento", testamento.getIdTestamento());
            testamentoNew.put("numeroAnotaciones",testamento.getNumeroAnotaciones());
            testamentoNew.put("numeroCopias",testamento.getNumeroCopias());
            testamentoNew.put("numeroRadicacion",testamento.getNumeroRadicacion());
            testamentoNew.put("observacion",testamento.getObservacion());
            testamentoNew.put("tomo",testamento.getTomo());
            testamentoNew.put("revocaEscritura",testamento.getRevocaEscritura());
            testamentoNew.put("fechaCreacion",testamento.getFechaCreacion());
            testamentoNew.put("copiasImprimir",evento.getCopias_imp());
            
            tmpsir.setTestamentoTMP(testamentoNew,turno.getIdWorkflow());
           
            List<Map> ciudadanos = new ArrayList<Map>();
            for(Object ciudadano:testamento.getTestadores())
            {
             
                        Map ciu = new HashMap();
                        ciu.put("idCiudadano", ((Ciudadano)ciudadano).getIdCiudadano());
                        ciu.put("documento", ((Ciudadano)ciudadano).getDocumento());
                        ciu.put("nombre", ((Ciudadano)ciudadano).getNombre());
                        ciu.put("tipoDoc", ((Ciudadano)ciudadano).getTipoDoc());
                        ciu.put("apellido1", ((Ciudadano)ciudadano).getApellido1());
                        ciu.put("apellido2", ((Ciudadano)ciudadano).getApellido2());
                        ciu.put("telefono", ((Ciudadano)ciudadano).getTelefono());
                        ciu.put("radicacion", turno.getIdWorkflow());
                        ciu.put("idCirculo", ((Ciudadano)ciudadano).getIdCirculo());
                        ciu.put("idCiudadano", ((Ciudadano)ciudadano).getIdCiudadano());
                        ciudadanos.add(ciu);
             
            }
           tmpsir.setTestadoresTMP(testamento.getIdTestamento(), ciudadanos,turno.getIdWorkflow());
           
           Map salvedadMap = new HashMap();
           salvedadMap.put("idTestamento",testamento.getIdTestamento());
           salvedadMap.put("descripcion",salvedad);
           salvedadMap.put("fechaCreacion",new Date());
           salvedadMap.put("idUsuarioTmp",usuario.getIdUsuario());
           salvedadMap.put("radicacion",turno.getIdWorkflow());
           
           tmpsir.setSalvedadTMP(salvedadMap);
           
           EvnRespCorreccion evn = new EvnRespCorreccion(EvnRespCorreccion.GUARDAR_TEMPORAL_OK);
           return evn;
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
            throw new EventoException(ex.getMessage());
        }
        
    }
    /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: elimina testador
        */
    private EvnRespTestamentos eliminarTestador(EvnModificarTestamento evento) throws EventoException {
        Turno turno = evento.getTurno();
		if(evento.getTestamentoCiudadano()!=null){
			TestamentoCiudadanoPk testamentoCiudadanoID;
			try {
				testamentoCiudadanoID=new TestamentoCiudadanoPk();
				testamentoCiudadanoID.idTestamento=evento.getTestamento().getIdTestamento();
				testamentoCiudadanoID.idCiudadano=evento.getTestamentoCiudadano().getIdCiudadano();
                                TestamentoSIR tmpsir = new TestamentoSIR();
				tmpsir.removeTestadorFromTestamento(testamentoCiudadanoID.idTestamento,testamentoCiudadanoID.idCiudadano,turno.getIdWorkflow());
			} catch (Throwable e) {
				throw new RegistroTestamentosException("No se pudo eliminar el testador.", e);
			}
		}
		return null;
    }
       /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: cargar turno de testamento
        */
    private EventoRespuesta cargarTestamento(EvnCorreccion evento) throws EventoException {
        Solicitud solicitud = evento.getTurno().getSolicitud();
        Turno turnoAnterior = solicitud.getTurnoAnterior();
        Documento testDoc = null;
        SolicitudRegistro solicitudCorreccion = null;
        if(turnoAnterior==null)
        {
            throw new EventoException("No se encontro el turno de testamento");
        }
        
        EvnRespCorreccion evn = new EvnRespCorreccion(EvnRespCorreccion.CARGAR_TESTAMENTO_OK);
        try {
            
            co.com.iridium.generalSIR.negocio.testamento.TestamentoSIR testamentoSir = new co.com.iridium.generalSIR.negocio.testamento.TestamentoSIR ();
            Map testamento = testamentoSir.getTestamento(turnoAnterior.getSolicitud().getIdSolicitud(),evento.getTurno().getIdWorkflow());
            Map documento = new co.com.iridium.generalSIR.negocio.testamento.TestamentoSIR ().getEncabezadoTMP(turnoAnterior.getSolicitud().getIdSolicitud(),evento.getTurno().getIdWorkflow()); 
            if(testamento == null)
            {
                throw new EventoException("No se encontro tentamento en el turno asociado");
            }
            if(documento!=null)
            {
                testDoc = getDocumentoFromMap(documento);
                solicitudCorreccion = new SolicitudRegistro();
                solicitudCorreccion.setIdSolicitud(turnoAnterior.getSolicitud().getIdSolicitud());
                solicitudCorreccion.setDocumento(testDoc);
            }
            Testamento test = getTestamentoFromMap(testamento);
            List<Map> testadores = testamentoSir.getTestadores(test.getIdTestamento(),evento.getTurno().getIdWorkflow());
            List<Ciudadano> ciudadanos = new ArrayList<Ciudadano>();
            for(Map testador : testadores)
            {
                Ciudadano ciudadano = getTestadorFromMap(testador);
                ciudadanos.add(ciudadano);
            }
            test.setTestadores(ciudadanos);
            if(testamento.containsKey("TSTM_NUMERO_COP_IMP"))
            {
                evn.setNextOrden((String)testamento.get("TSTM_NUMERO_COP_IMP"));
            }
            Map salvedad = testamentoSir.getSalvedad(test.getIdTestamento(),evento.getTurno().getIdWorkflow());
            evn.setTestamento(test);
            evn.setSalvedad(salvedad);
            evn.setSolicitudCorreccion(solicitudCorreccion);
            
        } catch (Throwable ex) {
            Logger.getLogger(ANCorreccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return evn;
    }
       /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: combierte uun map en Testamento
        */
    private Testamento getTestamentoFromMap(Map testamento)
    {
        if(testamento !=null)
        {
            Testamento test = new Testamento();
            test.setTomo((String)testamento.get("TSTM_TOMO"));
            test.setIdTestamento((String)testamento.get("ID_TESTAMENTO"));
            test.setNumeroAnotaciones((String)testamento.get("TSTM_NUMERO_ANOTACIONES"));
            test.setNumeroCopias((String)testamento.get("TSTM_NUMERO_COPIAS"));
            test.setNumeroRadicacion((String)testamento.get("TSTM_NUM_RADICACION"));
            test.setObservacion(testamento.get("TSTM_OBSERVACION").toString());
            test.setRevocaEscritura((String)testamento.get("TSTM_REVOCA_ESCRITURA"));
            test.setFechaCreacion((Date)testamento.get("TSTM_FECHA_CREACION"));
            return test;
        }
        return null;
    }
        /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: combierte map en ciudadano
        */
    private Ciudadano getTestadorFromMap(Map testador)
    {
        if(testador !=null){
                Ciudadano ciudadano = new Ciudadano();
                ciudadano.setIdCiudadano((String)testador.get("ID_CIUDADANO"));
                ciudadano.setDocumento((String)testador.get("CDDN_DOCUMENTO"));
                ciudadano.setNombre((String)testador.get("CDDN_NOMBRE"));
                ciudadano.setTipoDoc((String)testador.get("CDDN_TIPO_DOCUMENTO"));
                ciudadano.setTelefono((String)testador.get("CDDN_TELEFONO"));
                ciudadano.setApellido1((String)testador.get("CDDN_APELLIDO1"));
                ciudadano.setApellido2((String)testador.get("CDDN_APELLIDO2"));
                ciudadano.setIdCirculo((String)testador.get("ID_CIRCULO"));
                return ciudadano;
        }
        return null;
    }
             /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: convierte una lista de maps en lista de ciudadanos
        */
    private List<Ciudadano> getTestadoresFromList(List<Map> testadores)
    {
        List<Ciudadano> ciudadanos = new ArrayList<Ciudadano>();
        for(Map testador : testadores)
        {
            Ciudadano ciudadano = getTestadorFromMap(testador);
            ciudadanos.add(ciudadano);
        }
    
        return ciudadanos;
    }
        /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed:Convierte Map a documento
        */
    private Documento getDocumentoFromMap(Map documento)
    {
        if(documento!=null)
        {
            Documento doc = new Documento();
            doc.setIdDocumento((String)documento.get("ID_DOCUMENTO"));
            doc.setComentario((String)documento.get("DCMN_COMENTARIO"));
            doc.setFecha((Date)documento.get("DCMN_FECHA"));
            doc.setCirculo((String)documento.get("ID_CIRCULO"));
            doc.setNumero((String)documento.get("DCMN_NUMERO"));
            
            TipoDocumento tipoDoc = new TipoDocumento();
            tipoDoc.setIdTipoDocumento((String)documento.get("ID_TIPO_DOCUMENTO"));
            tipoDoc.setNombre((String)documento.get("tpdc_nombre".toUpperCase()));
            doc.setTipoDocumento(tipoDoc);
            
            doc.setOficinaInternacional((String)documento.get("DCMN_OFICINA_INTERNACIONAL"));
                OficinaOrigen oficeO = new OficinaOrigen();
                oficeO.setIdOficinaOrigen((String)documento.get("ID_OFICINA_ORIGEN"));
                oficeO.setNombre((String)documento.get("FCRG_NOMBRE"));
                oficeO.setNumero((String)documento.get("FCRG_NUMERO"));
                /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                oficeO.setVersion((Long)documento.get("VERSION"));
                
                TipoOficina tipo = new TipoOficina();
                tipo.setIdTipoOficina((String)documento.get("id_tipo_oficina".toUpperCase()));
                tipo.setNombre((String)documento.get("tpfc_nombre".toUpperCase()));
                oficeO.setTipoOficina(tipo);
                
                Vereda vereda = new Vereda();
                vereda.setIdVereda((String)documento.get("id_vereda".toUpperCase()));
                vereda.setIdMunicipio((String)documento.get("id_municipio".toUpperCase()));
                vereda.setIdDepartamento((String)documento.get("id_departamento".toUpperCase()));
                vereda.setNombre((String)documento.get("vrda_nombre".toUpperCase()));
                vereda.setMunicipio(new Municipio());
                    vereda.getMunicipio().setIdMunicipio((String)documento.get("id_municipio".toUpperCase()));
                    vereda.getMunicipio().setNombre((String)documento.get("mncp_nombre".toUpperCase()));
                    vereda.getMunicipio().setDepartamento(new Departamento());
                        vereda.getMunicipio().getDepartamento().setIdDepartamento((String)documento.get("id_departamento".toUpperCase()));
                        vereda.getMunicipio().getDepartamento().setNombre((String)documento.get("dprt_nombre".toUpperCase()));
                oficeO.setVereda(vereda);
            doc.setOficinaOrigen(oficeO);
             
            return doc;
        }
        return null;
    }
}

package gov.sir.core.negocio.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnAdministracionHermod;
import gov.sir.core.eventos.administracion.EvnRespAdministracionHermod;
import gov.sir.core.eventos.comun.EvnRespSistema;
import gov.sir.core.negocio.acciones.excepciones.ANInitParametrosException;
import gov.sir.core.negocio.acciones.excepciones.AdministracionEntidadesPublicasRepartoException;
import gov.sir.core.negocio.acciones.excepciones.AdministracionAccionNotarialException;
import gov.sir.core.negocio.acciones.excepciones.AdministracionNaturalezaJuridicaRepartoException;
import gov.sir.core.negocio.acciones.excepciones.CreacionBancoException;
import gov.sir.core.negocio.acciones.excepciones.EliminacionBancoException;
import gov.sir.core.negocio.acciones.excepciones.LoginNoProcesadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TipoDePagoPorCirculoException;
import gov.sir.core.negocio.acciones.excepciones.TipoTarifaNoEliminadaException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionCategoriaException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionFirmaRegistradorException;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BancoPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoNotarial;
import gov.sir.core.negocio.modelo.CirculoNotarialPk;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.FirmaRegistradorPk;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.ImprimiblePdf;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.NaturalezaJuridicaEntidad;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.OPLookupTypes;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.RolPantalla;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.SubtipoAtencion;
import gov.sir.core.negocio.modelo.SucursalBanco;
import gov.sir.core.negocio.modelo.Tarifa;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoNota;
import gov.sir.core.negocio.modelo.TipoNotaPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.UsuarioCirculo;
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.core.negocio.modelo.UsuarioSubtipoAtencion;
import gov.sir.core.negocio.modelo.Validacion;
import gov.sir.core.negocio.modelo.ZonaNotarial;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CEstacionRecibo;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CPantallaAdministrativa;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CTipoFormulario;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoNota;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormulario;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleInscripcionTestamento;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutiva;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacion;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.IDidTurnoComparator;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.acciones.administracion.AWAdministracionHermod;
import gov.sir.fenrir.FenrirException;
import gov.sir.fenrir.dao.DAOException;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.forseti.ForsetiException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
//import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.smart.web.acciones.AccionWebException;

import org.auriga.util.ExceptionPrinter;

/**
 * @author : HGOMEZ, FPADILLA ** Caso Mantis : 12621
 */
import gov.sir.core.negocio.modelo.constantes.DatosNotas;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConsulta;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormularioTestamento;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleResolucion;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.util.ComparadorAnotaciones;
import gov.sir.core.web.helpers.correccion.diff.utils.jxpath.FolioAbstractFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.jxpath.JXPathContext;

/**
 * Acción de negocio encargada de manejar los eventos de tipo
 * <code>EvnAdministracionHermod</code> destinados a manejar la administración
 * de los objetos del servicio Hermod
 *
 * @author jmendez
 * @author dsalas
 *
 */
public class ANAdministracionHermod extends SoporteAccionNegocio {

    /**
     * Instancia del ServiceLocator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de la interfaz de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     * Instancia de la intefaz de Forseti
     */
    private ForsetiServiceInterface forseti;

    /**
     * Instancia de la intefaz de Fenrir
     */
    private FenrirServiceInterface fenrir;

    /**
     * Instancia de PrintJobs
     */
    private PrintJobsInterface printJobs;

    /**
     * Constructor de la clase <code>ANAdministracionHermod</code>. Es el
     * encargado de invocar al Service Locator y pedirle una instancia del
     * servicio que necesita
     *
     * @throws EventoException
     */
    public ANAdministracionHermod() throws EventoException {
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
                throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Fenrir");
        }

        if (fenrir == null) {
            throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
        }

        try {
            printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

            if (printJobs == null) {
                throw new ServicioNoEncontradoException("Error instanciando el servicio PrintJbos");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
        }

    }

    /**
     * Manejador de eventos de tipo <code>EvnAdministracionHermod</code>. Se
     * encarga de procesar las acciones solicitadas por la capa Web de acuerdo
     * al tipo de evento que llegue a la acción de negocio. Este método redirige
     * la acción a otros métodos en la clase de acuerdo al tipo de evento que
     * llega como parámetro.
     *
     * @param e <code>EvnAdministracionHermod</code> evento con los parámetros
     * de la acción a realizar utilizando los servicios disponibles en la clase.
     *
     * @return <code>EventoRespuesta</code> con la información resultante de la
     * ejecución de la acción sobre los servicios
     *
     * @throws EventoException
     */
    public EventoRespuesta perform(Evento e) throws EventoException {
        EvnAdministracionHermod evento = (EvnAdministracionHermod) e;
        String tipoEvento = evento.getTipoEvento();

        if (evento == null || tipoEvento == null) {
            return null;
        }
        if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_BANCO)) {
            return adicionaBanco(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_BANCO)) {
            return eliminaBanco(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_SUCURSAL_BANCO)) {
            return adicionaSucursalBanco(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_SUCURSAL_BANCO)) {
            return eliminaSucursalBanco(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.LISTAR_SUCURSALES)) {
            return listarSucursales(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_ALCANCE_GEOGRAFICO)) {
            return adicionaAlcanceGeografico(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_ALCANCE_GEOGRAFICO)) {
            return eliminaAlcanceGeografico(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_TARIFA)) {
            return adicionaTarifa(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_TARIFA)) {
            return eliminaTarifa(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_TIPO_TARIFA)) {
            return adicionaTipoTarifa(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_TIPO_TARIFA)) {
            return eliminaTipoTarifa(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.SELECCIONA_TIPO_TARIFA)) {
            return seleccionaTipoTarifa(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_TIPO_FOTOCOPIA)) {
            return adicionaTipoFotocopia(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_TIPO_FOTOCOPIA)) {
            return eliminaTipoFotocopia(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_SUBTIPO_SOLICITUD)) {
            return adicionaSubtipoSolicitud(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_SUBTIPO_SOLICITUD)) {
            return eliminaSubtipoSolicitud(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.EDITA_SUBTIPO_SOLICITUD)) {
            return editarSubtipoSolicitud(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_OPLOOKUP_TYPE)) {
            return consultaOPLookupType(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_OPLOOKUP_TYPE)) {
            return adicionaOPLookupType(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.UPDATE_OPLOOKUP_TYPE)) {
            return updateOPLookupType(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_OPLOOKUP_TYPE)) {
            return eliminaOPLookupType(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.LISTAR_OPLOOKUP_CODES)) {
            return listarOPLookupCodes(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_OPLOOKUP_CODE)) {
            return adicionaOPLookupCode(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.UPDATE_OPLOOKUP_CODE)) {
            return updateOPLookupCode(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_OPLOOKUP_CODE)) {
            return eliminaOPLookupCode(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_TIPO_CALCULO)) {
            return adicionaTipoCalculo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_TIPO_CALCULO)) {
            return eliminaTipoCalculo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_TIPO_IMPUESTO)) {
            return adicionaTipoImpuesto(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_TIPO_IMPUESTO)) {
            return eliminaTipoImpuesto(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CARGAR_SUBTIPO_ATENCION)) {
            return cargarSubtipoAtencion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_SUBTIPO_ATENCION)) {
            return adicionaSubtipoAtencion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.EDITAR_SUBTIPO_ATENCION)) {
            return editarSubtipoAtencion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_SUBTIPO_ATENCION)) {
            return eliminaSubtipoAtencion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_TIPO_ACTO)) {
            return adicionaTipoActo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.EDITA_TIPO_ACTO)) {
            return editaTipoActo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_TIPO_ACTO)) {
            return eliminaTipoActo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_TIPO_DERECHO_REGISTRAL)) {
            return adicionaTipoDerechoRegistral(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_TIPO_DERECHO_REGISTRAL)) {
            return eliminaTipoDerechoRegistral(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_ACCION_NOTARIAL)) {
            return adicionaAccionNotarial(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.EDITA_ACCION_NOTARIAL)) {
            return editarAccionNotarial(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_ACCION_NOTARIAL)) {
            return eliminaAccionNotarial(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_CATEGORIA)) {
            return adicionaCategoria(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.EDITA_CATEGORIA)) {
            return editaCategoria(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_CATEGORIA)) {
            return eliminaCategoria(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.MOSTRAR_CIRCULO_ESTACION_RECIBO)) {
            return mostrarCirculoEstacionRecibo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.SET_ESTACION_RECIBO)) {
            return setEstacionRecibo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.SET_AGREGAR_ESTACION_RECIBO)) {
            setEstacionRecibo(evento);
            return consultaEstacionesReciboPorCirculo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_ESTACION_RECIBO)) {
            return eliminaEstacionRecibo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.TRASPASO_ESTACION_RECIBO)) {
            return traspasarEstacionRecibo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_ESTACIONES_RECIBO_POR_CIRCULO)) {
            return consultaEstacionesReciboPorCirculo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.RESET_ULTIMO_VALOR_ESTACION_RECIBO)) {
            return resetUltimoValorEstacionRecibo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_ESTACION_RECIBO)) {
            return consultaEstacionRecibo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_MINUTA)) {
            return consultaMinuta(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.EDITAR_MINUTA)) {
            return actualizarMinuta(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_CIRCULO_TIPO_PAGO)) {
            return adicionaCirculoTipoPago(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_CIRCULO_TIPO_PAGO)) {
            return eliminaCirculoTipoPago(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.SELECCIONA_CIRCULO_TIPO_PAGO)) {
            return seleccionaCirculoTipoPago(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_FIRMA_REGISTRADOR)) {
            return adicionaFirmaRegistrador(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ACTIVA_FIRMA_REGISTRADOR)) {
            return activaFirmaRegistrador(evento, CFirmaRegistrador.ACTIVA);
        } else if (tipoEvento.equals(EvnAdministracionHermod.INACTIVA_FIRMA_REGISTRADOR)) {
            return activaFirmaRegistrador(evento, CFirmaRegistrador.INACTIVA);
        } else if (tipoEvento.equals(EvnAdministracionHermod.INACTIVA_DEFINITIVAMENTE_FIRMA_REGISTRADOR)) {
            return activaFirmaRegistrador(evento, CFirmaRegistrador.INACTIVA_DEFINITIVA);
        } else if (tipoEvento.equals(EvnAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR)) {
            return seleccionaCirculoFirmaRegistrador(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.BUSCAR_FIRMA_REGISTRADOR_CIRCULO)) {
            return buscarFirmasRegistradorPorCirculo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.GUARDAR_FIRMA_REGISTRADOR)) {
            return guardarFirmaRegistrador(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_ABOGADOS_POR_CIRCULO)) {
            return consultarAbogadosPorCirculo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ACTUALIZA_SUBTIPOS_ATENCION_USUARIO)) {
            return actualizarSubtiposAtencionDeAbogados(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_VALIDACION_NOTA)) {
            return adicionaValidacionNota(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_VALIDACION_NOTA)) {
            return eliminaValidacionNota(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_FASE_PROCESO)) {
            return consultaFasesProceso(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_CAUSAL_RESTITUCION)) {
            return adicionaCausalRestitucion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_CAUSAL_RESTITUCION)) {
            return eliminaCausalRestitucion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.EDITAR_DETALLES_CAUSAL_RESTITUCION)) {
            return editarDetallesCausalRestitucion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_TIPO_CERTIFICADO_VALIDACION)) {
            return adicionaTipoCertificadoValidacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_TIPO_CERTIFICADO_VALIDACION)) {
            return eliminaTipoCertificadoValidacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_VALIDACIONES_DE_TIPO_CERTIFICADO)) {
            return consultaValidacionesDeTipoCertificado(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_TIPO_NOTA)) {
            return adicionaTipoNota(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_TIPO_NOTA)) {
            return eliminaTipoNota(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.MODIFICA_TIPO_NOTA)) {
            /*
                            @author Carlos Torres
                            @change Se cambia el metodo modificar por adicicionaTipoNota
                            @mantis 12621: Acta - Requerimiento No 055_453_Notas_Devolutivas_Causales_Texto_e_Histórico
             */
            return adicionaTipoNota(evento);
            //return modificaTipoNota(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_TIPO_NOTA_INF_POR_PROCESO)) {
            return consultaTipoNotaInfPorProceso(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_TIPO_NOTA_DEV_POR_PROCESO)) {
            return consultaTipoNotaDevPorProceso(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_CHECK_ITEM)) {
            return adicionaCheckItem(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_CHECK_ITEM)) {
            return eliminaCheckItem(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.EDITA_CHECK_ITEM)) {
            return editaCheckItem(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD)) {
            return consultaCheckItemPorSubtipoSolicitud(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_TURNOS_CER_ENTREGA_POR_FECHA)) {
            return consultaTurnosPorFaseYFecha(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_TURNOS_CON_ENTREGA_POR_FECHA)) {
            return consultaTurnosAEntregarConsultas(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ENTREGA_MASIVA_CERTIFICADOS)) {
            return entregaMasivaDeCertificados(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ENTREGA_MASIVA_CONSULTAS)) {
            return entregaMasivaDeConsultas(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTAR_PROB_REV_CALIFICACION)) {
            return consultarProbRevisionCalificacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.MODIFICAR_PROB_REV_CALIFICACION)) {
            return modificarProbRevisionCalificacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_SOLICITUDES_NO_PAGADAS)) {
            return consultarSolicitudesNoPagadas(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS)) {
            return eliminarSolicitudesNoPagadas(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINAR_TODAS_LAS_SOLICITUDES_NO_PAGADAS)) {
            return eliminarTodasLasSolicitudesNoPagadas(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_TURNOS_CALIFICACION)) {
            return consultaTurnosCalificacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.SOLICITAR_CORRECCION_CALIFICACION)) {
            return solicitarCorreccionCalificacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_ESTACIONES_CIRCULO)) {
            return consultaEstacionesCirculo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_TIPOS_TARIFA_POR_CIRCULO)) {
            return consultaTiposTarifaPorCirculo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_TARIFA_POR_CIRCULO)) {
            return adicionaTarifaPorCirculo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADMIN_UNA_ESTACION)) {
            return adminUnaEstacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADD_ROLES_ESTACION)) {
            return addRolesEstacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.REMOVE_ROLES_ESTACION)) {
            return removeRolesEstacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADD_USUARIOS_ESTACION)) {
            return addUsuariosEstacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.REMOVE_USUARIOS_ESTACION)) {
            return removeUsuariosEstacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_NAT_JURIDICA_ENTIDAD)) {
            return adicionarNatJuridicaEntidad(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.EDITAR_NAT_JURIDICA_ENTIDAD)) {
            return editarNatJuridicaEntidad(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_NAT_JURIDICA_ENTIDAD)) {
            return eliminarNatJuridicaEntidad(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_ENTIDAD_PUBLICA)) {
            return adicionarEntidadPublica(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.EDITA_ENTIDAD_PUBLICA)) {
            return editarEntidadPublica(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CONSULTA_ENTIDAD_PUBLICA_BY_NATURALEZA)) {
            return consultarEntidadPublicaByNaturaleza(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINA_ENTIDAD_PUBLICA)) {
            return eliminarEntidadPublica(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CARGAR_NAT_JURIDICAS)) {
            return naturalezasJuridicas(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.REIMPRIMIR_FORMULARIO_CALIFICACION)) {
            return reimprimirFormularioCalificacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.REIMPRIMIR_FORMULARIO_CORRECCIONES)) {
            return reimprimirFormularioCorrecciones(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO)) {
            return obtenerImpresorasCirculo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.OBTENER_VENCIDOS_PAGO_MAYOR_VALOR)) {
            return consultaTurnosConPagoMayorValorVencido(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.DEVOLVER_VENCIDOS_PAGO_MAYOR_VALOR)) {
            return agregarNotaDevolutivaYAvanzarTurnosPagoMayorVencido(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_TIPO_VALIDACION)) {
            return adicionaTipoValidacion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINAR_TURNOS_PAGO_MAYOR_VALOR_PENDIENTES)) {
            return eliminarTurnosPagoMayorValorPendiente(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.OBTENER_PENDIENTES_PAGO_MAYOR_VALOR)) {
            return consultaTurnosConPagoMayorValorPendiente(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CARGAR_CALIFICADORES_CIRCULO)) {
            return cargarCalificadoresCirculo(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CARGAR_CALIFICADORES_SUBTIPOATENCION)) {
            return cargarCalificadoresSubtipoAtencion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ADICIONA_ORDEN_SUBTIPOATENCION)) {
            return adicionarOrdenSubtipoAtencion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.REMOVE_ORDEN_SUBTIPOATENCION)) {
            return removerOrdenSubtipoAtencion(evento);
        } //Seleccionar turno para modificar valor de la devolución
        else if (tipoEvento.equals(EvnAdministracionHermod.SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION)) {
            return modificarTurnoDevolucion(evento);
        } //Modificar el valor devuelto
        else if (tipoEvento.equals(AWAdministracionHermod.MODIFICAR_VALOR_LIQUIDACION_DEVOLUCION_SELECCIONADA)) {
            return modificarValorTurnoDevolucion(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CARGAR_CIRCULOS_NOTARIALES)) {
            return cargarCirculosNotariales(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.AGREGAR_CIRCULO_NOTARIAL)) {
            return agregarCirculoNotarial(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINAR_CIRCULO_NOTARIAL)) {
            return eliminarCirculoNotarial(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.EDITAR_CIRCULO_NOTARIAL)) {
            return editarCirculoNotarial(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CARGAR_ZONAS_NOTARIALES)) {
            return cargarZonasRegistrales(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.AGREGAR_ZONA_NOTARIAL)) {
            return agregarZonaNotarial(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINAR_ZONA_NOTARIAL)) {
            return eliminarZonaNotarial(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.CARGAR_ROLES_PANTALLAS_ADMINISTRATIVAS)) {
            return cargarRolesPantallasAdministrativas(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.ELIMINAR_ROL_PANTALLA_ADMINISTRATIVA)) {
            return eliminarRolPantallaAdministrativa(evento);
        } else if (tipoEvento.equals(EvnAdministracionHermod.AGREGAR_ROL_PANTALLA_ADMINISTRATIVA)) {
            return agregarRolPantallaAdministrativa(evento);
        } /**
         * Pablo Quintana Se reimprime una constancia de testamento
         */
        else if (tipoEvento.equals(EvnAdministracionHermod.REIMPRIMIR_FORMULARIO_TESTAMENTOS)) {
            return reimprimirFormularioTestamentos(evento);
        } else if (tipoEvento.equals(AWAdministracionHermod.ADICIONA_BANCO_CIRCULO)) {
            return adicionaBancoCirculo(evento);
        } else if (tipoEvento.equals(AWAdministracionHermod.ELIMINA_BANCO_CIRCULO)) {
            return eliminaBancoCirculo(evento);
        } else if (tipoEvento.equals(AWAdministracionHermod.SELECCIONA_BANCOS_X_CIRCULO)) {
            return seleccionaBancoCirculo(evento);
        } else if (tipoEvento.equals(AWAdministracionHermod.ACTIVAR_BANCO_PRINCIPAL)) {
            return activarBancoPrincipal(evento);
        } else if (tipoEvento.equals(AWAdministracionHermod.ADICIONA_CUENTA_BANCO_CIRCULO)) {
            return adicionaCuentaBancoCirculo(evento);
        } else if (tipoEvento.equals(AWAdministracionHermod.CUENTAS_X_CIRCULO_BANCO)) {
            return getCuentasXBancoCirculo(evento);
        } else if (tipoEvento.equals(AWAdministracionHermod.ACT_INACT_CTA_BANCARIA)) {
            return activarInactivarCtaBancaria(evento);
        } else if (tipoEvento.equals(AWAdministracionHermod.ADICIONA_CANAL_RECAUDO)) {
            return adicionaCanalRecaudo(evento);
        } else if (tipoEvento.equals(AWAdministracionHermod.ACT_INACT_CANAL_RECAUDO)) {
            return activarInactivarCanalRecaudo(evento);
        } else if (tipoEvento.equals(AWAdministracionHermod.SELECCIONA_CUENTA_BANCARIA)) {
            return seleccionaCuentasBancarias(evento);
        } else if (tipoEvento.equals(AWAdministracionHermod.ACT_INACT_CTP)) {
            return activarInactivarCtp(evento);
        }

        return null;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta eliminarZonaNotarial(EvnAdministracionHermod evento) throws EventoException {
        ZonaNotarial zona = evento.getZonaNotarial();
        try {
            hermod.eliminarZonaNotarial(zona);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        }
        CirculoNotarial circ = new CirculoNotarial();
        circ.setIdCirculo(zona.getIdCirculoNotarial());
        evento.setCirculoNotarial(circ);
        return cargarZonasRegistrales(evento);
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta agregarZonaNotarial(EvnAdministracionHermod evento) throws EventoException {
        ZonaNotarial zona = evento.getZonaNotarial();
        try {
            hermod.agregarZonaNotarial(zona);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        }
        CirculoNotarial circ = new CirculoNotarial();
        circ.setIdCirculo(zona.getIdCirculoNotarial());
        evento.setCirculoNotarial(circ);
        return cargarZonasRegistrales(evento);
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta cargarZonasRegistrales(EvnAdministracionHermod evento) throws EventoException {
        CirculoNotarial circulo = evento.getCirculoNotarial();
        CirculoNotarialPk idCirculo = new CirculoNotarialPk();
        idCirculo.idCirculo = circulo.getIdCirculo();
        List listaZonas = null;
        try {
            CirculoNotarial circuloCompleto = hermod.consultarCirculoNotarial(idCirculo);
            if (circuloCompleto != null) {
                listaZonas = circuloCompleto.getZonasNotariales();
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        }
        return new EvnRespAdministracionHermod(listaZonas, EvnRespAdministracionHermod.CARGAR_ZONAS_NOTARIALES_OK);
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta editarCirculoNotarial(EvnAdministracionHermod evento) throws EventoException {
        CirculoNotarial circulo = evento.getCirculoNotarial();
        try {
            hermod.editarCirculoNotarial(circulo);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        }

        evento.setCirculo(circulo.getCirculoRegistral());
        return cargarCirculosNotariales(evento);
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta eliminarCirculoNotarial(EvnAdministracionHermod evento) throws EventoException {

        CirculoNotarial circulo = evento.getCirculoNotarial();
        try {
            hermod.eliminarCirculoNotarial(circulo);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        }

        return cargarCirculosNotariales(evento);
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta agregarCirculoNotarial(EvnAdministracionHermod evento) throws EventoException {
        CirculoNotarial circulo = evento.getCirculoNotarial();
        try {
            hermod.agregarCirculoNotarial(circulo);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        }

        evento.setCirculo(circulo.getCirculoRegistral());
        return cargarCirculosNotariales(evento);
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta cargarCirculosNotariales(EvnAdministracionHermod evento) throws EventoException {
        Circulo circulo = evento.getCirculo();
        List lista = new ArrayList();
        try {
            lista = hermod.getCirculosNotarialesByCirculoRegistral(circulo);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            ValidacionParametrosException exception = new ValidacionParametrosException(e.getMessage(), e);
            exception.addError(e.getMessage());
            throw exception;
        }
        return new EvnRespAdministracionHermod(lista, EvnRespAdministracionHermod.CARGAR_CIRCULOS_NOTARIALES_OK);
    }

    private EventoRespuesta adicionaTipoValidacion(EvnAdministracionHermod evento) {
        Validacion validacion = evento.getValidacion();
        return new EvnRespAdministracionHermod(validacion, EvnRespAdministracionHermod.ADICIONA_TIPO_VALIDACION_OK);
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta reimprimirFormularioCalificacion(EvnAdministracionHermod evento) throws EventoException {

        try {
            //Verificar si es sujeto de reimpresion
            Turno turno = hermod.getTurnobyWF(evento.getIdTurnoCalificacion());
            TurnoPk id = new TurnoPk();
            id.anio = turno.getAnio();
            id.idCirculo = turno.getIdCirculo();
            id.idProceso = turno.getIdProceso();
            id.idTurno = turno.getIdTurno();

            //comprobar que el turno no esta anulado
            if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                throw new EventoException("El turno esta anulado");
            }

            //int numCopias = evento.getNumeroImpresiones();
            if (turno.getSolicitud().getProceso().getIdProceso() != Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                throw new EventoException("El turno no pertence a Registro");
            }
            int sab =0;
            String clasificacionreg = "";
            try{
                SolicitudRegistro solReg1 = (SolicitudRegistro) turno.getSolicitud();
                SolicitudRegistro Final = (SolicitudRegistro) hermod.getSolicitudById(solReg1.getIdSolicitud());
                clasificacionreg = Final.getClasificacionRegistro();
                sab = 1;
                if(clasificacionreg == null){
                    sab = 0;
                }
                if(clasificacionreg.equals("")){
                    sab = 0;
                }
            }catch(Throwable e){
                sab =0;
            }
            if(sab == 1){
                if(turno.getNotas() != null){
                if(turno.getNotas().size() > 0){
                    List notas = turno.getNotas();
                        for(int i = 0 ; i < notas.size() ; i++){
                            Nota n = (Nota) notas.get(i);
                            if(n.getTiponota().isDevolutiva()){
                                if("INSCRITO PARCIALMENTE".equals(clasificacionreg) || "REGISTRO PARCIAL".equals(clasificacionreg)){
                                    
                                }else{
                                    throw new EventoException("Este turno es de devolucion imprimir por detalles de turno ");
                                }
                            }
                        }

                    }
                }
            }
                 
            
            List historia = turno.getHistorials();
            int i = 0;
            boolean esReimprimible = false;
            boolean pasoFirma = false;
            Usuario usuario = null;
            while (i < historia.size()) {
                TurnoHistoria historial = (TurnoHistoria) historia.get(i);
                if (historial.getFase().equals(CFase.CAL_CALIFICACION)) {
                    if (i != historia.size() - 1) {
                        esReimprimible = true;
                    }
                    usuario = historial.getUsuarioAtiende();
                }
                if (historial.getFase().equals(CFase.REG_FIRMAR) && i != historia.size() - 1) {
                    pasoFirma = true;
                }
                i++;
            }

            if (!esReimprimible) {
                throw new EventoException("El turno " + turno.getIdWorkflow() + " no puede ser impreso porque no ha pasado la fase de Calificacion");
            }

            /*Iterator itFolios = turno.getSolicitud().getSolicitudFolios().iterator();
			List nSolFolio = new ArrayList();
			while(itFolios.hasNext()){
				SolicitudFolio solFolio = (SolicitudFolio)itFolios.next();
				Folio folio = solFolio.getFolio();
				Folio.ID id = new Folio.ID();
				id.idMatricula = folio.getIdMatricula();
				id.idZonaRegistral = folio.getZonaRegistral().getIdZonaRegistral();
				Folio nFolio = forseti.getFolioByIDSinAnotaciones(id);
				solFolio.setFolio(nFolio);
				nSolFolio.add(solFolio);
			}

			turno.getSolicitud().setSolicitudFolios(nSolFolio);*/
            // bug: 3838
            //actualizar los folios segun los modificados en la solicitud
            try {
                List solicitudesFoliosCalificados = null;
                solicitudesFoliosCalificados = getSolicitudesFolioCalificadas(forseti, id, usuario, pasoFirma);
                turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
            } catch (Throwable e2) {
                ExceptionPrinter ep = new ExceptionPrinter(e2);
                Log.getInstance().error(ANAdministracionHermod.class, "No se pudieron obtener los folios calificados:" + ep.toString());
                throw new EventoException("No se pudieron obtener los folios calificados:" + e2.getMessage(), e2);
            }

            //obtener textos base de los separadores
            String tbase1 = "";
            String tbase2 = "";
            List variablesImprimibles = hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
            for (Iterator j = variablesImprimibles.iterator(); j.hasNext();) {
                OPLookupCodes op = (OPLookupCodes) j.next();
                if (op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)) {
                    tbase1 = op.getValor();
                }
                if (op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)) {
                    tbase2 = op.getValor();
                }
            }
            //Codigo para reimprimir
            String nombreUsuario = "";
            if (usuario == null) {
                nombreUsuario = "";
            } else {
                nombreUsuario = usuario.getUsername();
            }
            String fechaImpresion = this.getFechaImpresion();
            ImprimibleFormulario impFormulario = new ImprimibleFormulario(turno, nombreUsuario, CTipoFormulario.TIPO_CALIFICACION, fechaImpresion, CTipoImprimible.FORMULARIO_CALIFICACION);
            impFormulario.setTEXTO_BASE1(tbase1);
            impFormulario.setTEXTO_BASE2(tbase2);

            impFormulario.setPrintWatermarkEnabled(true);
            impFormulario.setIdUsuario(usuario != null ? "" + usuario.getIdUsuario() : "");

            //Seteo de todo lo relacionado con la imagen de la firma
            String nombre = "";
            String archivo = "";
            String cargoToPrint = "";
            String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
            FirmaRegistrador firmaRegistrador = null;
            CirculoPk cid = new CirculoPk();
            cid.idCirculo = turno.getIdCirculo();
            String rutaFisica = null;

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

                nombre = firmaRegistrador.getNombreRegistrador();
                archivo = firmaRegistrador.getIdArchivo();

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
            } catch (Throwable t) {
                t.printStackTrace();
            }

            if (nombre == null) {
                nombre = "";
            }

            impFormulario.setCargoRegistrador(cargoToPrint);
            impFormulario.setNombreRegistrador(nombre);
                    Turno rta = null;
                   try {
                            rta = hermod.getTurnobyWF(turno.getIdWorkflow());
                    } catch (HermodException e) {
                
                    }
                    List historia1 = rta.getHistorials();
                    boolean isFirma= false;
                    Iterator iti = historia1.iterator();
                    String resp1 = "";
                    if(historia1==null || historia1.size()==0){
                    
                    }else{
                        Boolean control =true;
                        while(iti.hasNext()){
                                TurnoHistoria th = (TurnoHistoria)iti.next();
                                if(th.getFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)
                                || th.getFase().equals(CFase.NOT_NOTA_DEVOLUTIVA)){
                                        isFirma = true;
                                }
                        }
                    }
            //Se quita la firma de la reimpreison del formulario de Calificacion
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
                                if(isFirma){
                                    impFormulario.setChangetextforregistrador(true);
                                    impFormulario.setPixelesImagenFirmaRegistrador(pixeles);
                                }
                                
			}
            //Fin del procesamiento de la imagen
            //llenar parametros hashtable
            Hashtable tabla = new Hashtable();
            tabla.put(Processor.IMPRIMIBLE, impFormulario);
            if (evento.getImpresoraSeleccionada() != null) {
                tabla.put(Processor.IMPRESORA_SELECCIONADA, evento.getImpresoraSeleccionada());
            }
            tabla.put(Processor.NUM_COPIAS_IMPRIMIBLE, new Integer(evento.getNumeroImpresiones()));
            tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
            tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
            tabla.put(Processor.IMPRIMIBLE, impFormulario);

            //obtener imprimible base
            ImprimibleBase imprimibleb = (ImprimibleBase) tabla.get(Processor.IMPRIMIBLE);
            imprimirAdministrador(id.idCirculo, imprimibleb, tabla);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta reimprimirFormularioCorrecciones(EvnAdministracionHermod evento) throws EventoException {

        try {
            //Verificar si es sujeto de reimpresion
            Turno turno = hermod.getTurnobyWF(evento.getIdTurnoCalificacion());
            TurnoPk id = new TurnoPk();
            id.anio = turno.getAnio();
            id.idCirculo = turno.getIdCirculo();
            id.idProceso = turno.getIdProceso();
            id.idTurno = turno.getIdTurno();
           
            //comprobar que el turno no esta anulado
            if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                throw new EventoException("El turno esta anulado");
            }

            //TFS 3166: NO SE PUEDEN REIMPRIMIR TURNOS FINALIZADOS
            if (turno.getFechaFin() != null) {
                throw new EventoException("El turno esta finalizado");
            }

            //int numCopias = evento.getNumeroImpresiones();
            if (turno.getSolicitud().getProceso().getIdProceso() != Long.parseLong(CProceso.PROCESO_CORRECCION)) {
                throw new EventoException("El turno no pertence a Correcciones");
            }
            List historia = turno.getHistorials();
            int i = 0;
            boolean esReimprimible = false;
            boolean pasoFirma = false;
            Usuario usuario = null;
            while (i < historia.size()) {
                TurnoHistoria historial = (TurnoHistoria) historia.get(i);
                /**
                 * @autor: Edgar Lora
                 * @mantis: 0011623
                 * @requerimiento: 028_453
                 */
                if (historial.getFase().equals(CFase.COR_REVISAR_APROBAR) || historial.getFase().equals(CFase.COR_ACT_EJECUTAR)) {
                    if (i != historia.size() - 1) {
                        esReimprimible = true;
                    }
                    usuario = historial.getUsuarioAtiende();
                }
                i++;
            }

            if (!esReimprimible) {
                /**
                 * @autor: Edgar Lora
                 * @mantis: 0011623
                 * @requerimiento: 028_453
                 */
                throw new EventoException("El turno " + turno.getIdWorkflow() + " no puede ser impreso porque no ha pasado la fase de Revisar Aprobar o Ejecutar Actuacion Administrativa");
            }
                Turno rta = null;
                   try {
                            rta = hermod.getTurnobyWF(turno.getIdWorkflow());
                    } catch (HermodException e) {
                
                    }
                    List historia1 = rta.getHistorials();
                    boolean isFirma= false;
                    Iterator iti = historia1.iterator();
                    String resp1 = "";
                    if(historia1==null || historia1.size()==0){
                    
                    }else{
                        Boolean control =true;
                        while(iti.hasNext()){
                                TurnoHistoria th = (TurnoHistoria)iti.next();
                                if(th.getFase().equals(CFase.COR_MESA_CONTROL)){
                                        isFirma = true;
                                }
                        }
                    }
            ImprimiblePdf imprimiblepdf = null;
            try {
                //Se hace una espera de acuerdo con el tiempo de espera recibido en esperado.
                imprimiblepdf =  printJobs.getImprimiblePdf(turno.getIdWorkflow());
                //si imprime exitosamente sale del ciclo.
                //resultadoImpresion = true;
                //parametros=new Hashtable();
                if (imprimiblepdf == null) {
                    throw new EventoException("El turno " + turno.getIdWorkflow() + " no puede ser impreso no tiene el registro de Impresión.");
                }
            } catch (Throwable t) {
                Log.getInstance().error(ANAdministracionHermod.class, t.getMessage(), t);
                throw new EventoException(t.getMessage(), t);
            }
            String fechaImpresion= this.getFechaImpresion();
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
                    /*imprimible.setCargoRegistrador(cargoToPrint);
                    imprimible.setNombreRegistrador(sNombre);
                    imprimiblepdf.setCargoRegistrador(cargoToPrint);
                    imprimiblepdf.setNombreRegistrador(sNombre);
                    imprimiblepdf.setChangetextforregistrador(true);
                    imprimible.setChangetextforregistrador(true);*/
                    
                    if (rutaFisica != null) {
                        BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                         byte pixeles[] = null;
                        try {
                            pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                         } catch (Throwable e1) {

                            e1.printStackTrace();
                        }
                         /*   imprimible.setPixelesImagenFirmaRegistrador(pixeles);
                            imprimiblepdf.gsetPixelesImagenFirmaRegistrador(pixeles);¨*/
                        
                  }
            
            //llenar parametros hashtable
            Hashtable tabla = new Hashtable();
            tabla.put(Processor.IMPRIMIBLE, imprimiblepdf);
            if (evento.getImpresoraSeleccionada() != null) {
                tabla.put(Processor.IMPRESORA_SELECCIONADA, evento.getImpresoraSeleccionada());
            }
            tabla.put(Processor.NUM_COPIAS_IMPRIMIBLE, new Integer(evento.getNumeroImpresiones()));
            tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
            tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);

            //obtener imprimible base
                
                 imprimirAdministradorPdf(id.idCirculo, imprimiblepdf, tabla);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        return null;
    }
/**
     * @param imprimible
     * @param uid
     */
    private void imprimir(ImprimibleBase imprimible, String uid) throws PrintJobsException, EventoException {


		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
        int maxIntentos;
        int espera;

		//INGRESO DE INTENTOS DE IMPRESION
		try
		{

			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null)
			{
				 Integer intentos = new Integer (intentosImpresion);
				 maxIntentos = intentos.intValue();
			}
			else
			{
				 Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
				 maxIntentos = intentosDefault.intValue();
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null)
			{
				 Integer esperaInt = new Integer(esperaImpresion);
				 espera = esperaInt.intValue();
			}
			else
			{
				Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}
		}
		catch (Throwable t)
		{
			Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();


		}


        Bundle bundle = new Bundle(imprimible);

        try {
            //se manda a imprimir el recibo por el identificador unico de usuario
            printJobs.enviar(uid, bundle, maxIntentos, espera);
        }catch (PrintJobsException pe) {
			throw pe;
		}catch (Throwable t) {
            t.printStackTrace();
        }
    }
    private List getSolicitudesFolioCalificadas(
            ForsetiServiceInterface forseti,
            TurnoPk oid,
            Usuario u,
            boolean pasoFirma
    )
            throws Throwable {
        List deltaFolios = null;

        if (!pasoFirma) {
            deltaFolios = forseti.getDeltaFolios(oid, u);
        } else {
            deltaFolios = forseti.getCalificacionTurno(oid /*,u*/);
        }
        Vector solicitudesFoliosCalificados = new Vector();
        for (int i = 0; i < deltaFolios.size(); i++) {
            SolicitudFolio solFolioDelta = (SolicitudFolio) deltaFolios.get(i);
            Folio folioDelta = solFolioDelta.getFolio();

            FolioPk fid = new FolioPk();
            fid.idMatricula = solFolioDelta.getIdMatricula();

            Folio folioCal = forseti.getFolioByIDSinAnotaciones(fid, null);

            // verified: bug 03838
            if (null != folioDelta.getCodCatastral()) {
                folioCal.setCodCatastral(folioDelta.getCodCatastral());
            }
            if (null != folioDelta.getTipoPredio()) {
                folioCal.setTipoPredio(folioDelta.getTipoPredio());
            }
            // // added: bug 03838
            // if(folioDelta.getCodCatastral()!=null){
            //        folioCal.setCodCatastral(folioDelta.getCodCatastral());
            //}
            //if(folioDelta.getTipoPredio()!=null){
            //        folioCal.setTipoPredio(folioDelta.getTipoPredio());
            //}

            List anotaciones = folioDelta.getAnotaciones();
            for (int j = 0; j < anotaciones.size(); j++) {
                Anotacion anota = (Anotacion) anotaciones.get(j);
                if (anota.getNumRadicacion() != null && anota.getNumRadicacion().equals(oid.anio + "-" + oid.idCirculo + "-" + oid.idProceso + "-" + oid.idTurno)) {
                    folioCal.addAnotacione(anota);
                }
                /**
                 * @author: David Panesso
                 * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                 * Organiza los folios derivados de forma ascendente para su
                 * correcta visualización
                 *
                 */
                if (!anota.getAnotacionesHijos().isEmpty()) {

                    Object[] foliosDerivadosArray = anota.getAnotacionesHijos().toArray();

                    for (int m = 0; m < foliosDerivadosArray.length - 1; m++) {
                        FolioDerivado folioI = (FolioDerivado) foliosDerivadosArray[m];
                        String[] splitI = folioI.getIdMatricula1().split("-");
                        int matriculaI = Integer.parseInt(splitI[1]);
                        for (int n = m + 1; n < foliosDerivadosArray.length; n++) {
                            FolioDerivado folioJ = (FolioDerivado) foliosDerivadosArray[n];
                            String[] splitD = folioJ.getIdMatricula1().split("-");
                            int matriculaD = Integer.parseInt(splitD[1]);
                            if (matriculaI > matriculaD) {
                                FolioDerivado aux = (FolioDerivado) foliosDerivadosArray[m];
                                int auxNum = matriculaI;
                                foliosDerivadosArray[m] = foliosDerivadosArray[n];
                                matriculaI = matriculaD;
                                foliosDerivadosArray[n] = aux;
                                matriculaD = auxNum;
                            }
                        }
                    }

                    anota.getAnotacionesHijos().clear();
                    anota.getAnotacionesHijos().addAll(new ArrayList(Arrays.asList(foliosDerivadosArray)));
                }
            }

            SolicitudFolio solFolioCal = new SolicitudFolio();
            solFolioCal.setFolio(folioCal);
            solicitudesFoliosCalificados.add(solFolioCal);
        }

        return solicitudesFoliosCalificados;

    }

    public static BufferedImage getImage(String path, String nombreArchivo) {
        String nombreCompleto = getNombreCompleto(path, nombreArchivo);
        BufferedImage buf = null;

        try {
            File file = new File(nombreCompleto);
            buf = ImageIO.read(file);
        } catch (IOException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e);
        }

        return buf;
    }

    public static String getNombreCompleto(String path, String nombreArchivo) {

        String nombreCompleto = null;

        if (!path.trim().equals("")) {
            nombreCompleto = path + nombreArchivo;
        } else {
            nombreCompleto = nombreArchivo;
        }
        return nombreCompleto;
    }

    /**
     * @param imprimible
     * @param uid
     */
    /*private void imprimir(ImprimibleBase imprimible, String uid, int numCopias) throws EventoException {
		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
		int maxIntentos;
		int espera;

		//INGRESO DE INTENTOS DE IMPRESION
		try{

			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null){
				 Integer intentos = new Integer (intentosImpresion);
				 maxIntentos = intentos.intValue();
			}else{
				 Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
				 maxIntentos = intentosDefault.intValue();
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null){
				 Integer esperaInt = new Integer(esperaImpresion);
				 espera = esperaInt.intValue();
			}else{
				Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}
		} catch (Throwable t){
			Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();
		}


		Bundle bundle = new Bundle(imprimible);
		bundle.setNumeroCopias(numCopias);

		try {
			printJobs = (PrintJobsInterface)service.getServicio("gov.sir.print");

			if (printJobs == null) {
				try {
					throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
				} catch (ServicioNoEncontradoException e1) {

					e1.printStackTrace();
				}
			}
		}
		catch (ServiceLocatorException e) {
			try {
				throw new ServicioNoEncontradoException("Error instanciando el servicio PrintJobs",e);
			} catch (ServicioNoEncontradoException e1) {

				e1.printStackTrace();
			}
		}

			if (printJobs == null) {
				try {
					throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
				} catch (ServicioNoEncontradoException e1) {

					e1.printStackTrace();
				}
			}



		for (int i = 0; i < maxIntentos; i++) {
			try {
				Thread.sleep(espera);

				//se manda a imprimir el recibo por el identificador unico de usuario
				printJobs.enviar(uid, bundle);

				//si ok-->termina el ciclo.
				i = maxIntentos + 1;
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}*/
    /**
     * Imprime el objeto imprimible en la estacion asociada al circulo del turno
     * dado con los parametros asignados.
     *
     * @param turno
     * @param imprimible
     * @param parametros
     * @return
     */
    private void imprimirAdministrador(String circulo, ImprimibleBase imprimible, Hashtable parametros) {

        Bundle b = new Bundle(imprimible);

        String intentosImpresion;
        String esperaImpresion;

        ServiceLocator sl = ServiceLocator.getInstancia();

        //INGRESO DE INTENTOS DE IMPRESION
        try {

            hermod = (HermodServiceInterface) sl.getServicio("gov.sir.hermod");
            printJobs = (PrintJobsInterface) sl.getServicio("gov.sir.print");

            intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
            if (intentosImpresion != null) {
                parametros.put(Processor.INTENTOS, intentosImpresion);
            } else {
                intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
                parametros.put(Processor.INTENTOS, intentosImpresion);
            }

            //INGRESO TIEMPO DE ESPERA IMPRESION
            esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
            if (intentosImpresion != null) {
                parametros.put(Processor.ESPERA, esperaImpresion);
            } else {
                esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
                parametros.put(Processor.ESPERA, esperaImpresion);
            }
        } catch (Throwable t) {
            intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
            parametros.put(Processor.INTENTOS, intentosImpresion);
            esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
            parametros.put(Processor.ESPERA, esperaImpresion);
        }

        Integer intIntentos = new Integer(intentosImpresion);
        int intentos = intIntentos.intValue();

        Integer longEspera = new Integer(esperaImpresion);
        int espera = longEspera.intValue();

        ////////////////////Número de copias
        Integer nCopias = (Integer) parametros.get(Processor.NUM_COPIAS_IMPRIMIBLE);
        int numCopias = 1;
        if (nCopias != null) {
            numCopias = nCopias.intValue();
        }

        boolean resultadoImpresion = false;
        b.setNumeroCopias(numCopias);

        String impresoraSeleccionada = (String) parametros.get(Processor.IMPRESORA_SELECCIONADA);
        if (impresoraSeleccionada != null) {
            b.setNombreImpresora(impresoraSeleccionada);
        }

        //Ciclo que se ejecuta de acuerdo al valor recibido en intentos (Imprime 1 copia)
        try {
            //Se hace una espera de acuerdo con el tiempo de espera recibido en esperado.
            printJobs.enviar(circulo, b, intentos, espera);
            //si imprime exitosamente sale del ciclo.
            resultadoImpresion = true;
            parametros = new Hashtable();
        } catch (Throwable t) {
            t.printStackTrace();
            resultadoImpresion = false;

        }

    }

    /**
     * Imprime el objeto imprimible en la estacion asociada al circulo del turno
     * dado con los parametros asignados.
     *
     * @param turno
     * @param imprimible
     * @param parametros
     * @return
     */
    private void imprimirAdministradorPdf(String circulo, ImprimiblePdf imprimiblePdf, Hashtable parametros) {

        Bundle b = new Bundle(null);
        b.setImprimiblePdf(imprimiblePdf);
        
        String intentosImpresion;
        String esperaImpresion;

        ServiceLocator sl = ServiceLocator.getInstancia();

        //INGRESO DE INTENTOS DE IMPRESION
        try {

            hermod = (HermodServiceInterface) sl.getServicio("gov.sir.hermod");
            printJobs = (PrintJobsInterface) sl.getServicio("gov.sir.print");

            intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
            if (intentosImpresion != null) {
                parametros.put(Processor.INTENTOS, intentosImpresion);
            } else {
                intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
                parametros.put(Processor.INTENTOS, intentosImpresion);
            }

            //INGRESO TIEMPO DE ESPERA IMPRESION
            esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
            if (intentosImpresion != null) {
                parametros.put(Processor.ESPERA, esperaImpresion);
            } else {
                esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
                parametros.put(Processor.ESPERA, esperaImpresion);
            }
        } catch (Throwable t) {
            intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
            parametros.put(Processor.INTENTOS, intentosImpresion);
            esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
            parametros.put(Processor.ESPERA, esperaImpresion);
        }

        Integer intIntentos = new Integer(intentosImpresion);
        int intentos = intIntentos.intValue();

        Integer longEspera = new Integer(esperaImpresion);
        int espera = longEspera.intValue();

        ////////////////////Número de copias
        Integer nCopias = (Integer) parametros.get(Processor.NUM_COPIAS_IMPRIMIBLE);
        int numCopias = 1;
        if (nCopias != null) {
            numCopias = nCopias.intValue();
        }

        boolean resultadoImpresion = false;
        b.setNumeroCopias(numCopias);

        String impresoraSeleccionada = (String) parametros.get(Processor.IMPRESORA_SELECCIONADA);
        if (impresoraSeleccionada != null) {
            b.setNombreImpresora(impresoraSeleccionada);
        }

        //Ciclo que se ejecuta de acuerdo al valor recibido en intentos (Imprime 1 copia)
        try {
            //Se hace una espera de acuerdo con el tiempo de espera recibido en esperado.
            printJobs.enviar(circulo, b, intentos, espera);
            //si imprime exitosamente sale del ciclo.
            resultadoImpresion = true;
            parametros = new Hashtable();
        } catch (Throwable t) {
            t.printStackTrace();
            resultadoImpresion = false;

        }

    }
    /**
     * @param evento
     * @return
     */
    private EventoRespuesta naturalezasJuridicas(EvnAdministracionHermod evento) throws EventoException {
        List natsJuridicasEntidad;
        List naturalezas = new ArrayList();
        try {
            natsJuridicasEntidad = hermod.getNaturalezasJuridicasEntidades();
            Iterator itNatJuridicas = natsJuridicasEntidad.iterator();

            while (itNatJuridicas.hasNext()) {
                NaturalezaJuridicaEntidad temp = (NaturalezaJuridicaEntidad) itNatJuridicas.next();
                if (temp.isActivo()) {
                    naturalezas.add(temp);
                }
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(natsJuridicasEntidad, EvnRespAdministracionHermod.NAT_JURIDICAS_ENTIDAD);
        respuesta.setNatsJuridicasEntidad(naturalezas);
        return respuesta;

    }

    private EvnRespAdministracionHermod removeRolesEstacion(EvnAdministracionHermod evento)
            throws ValidacionParametrosException {

        ValidacionParametrosException vpe = new ValidacionParametrosException();

        //OBTENER ELEMENTOS DEL EVENTO
        Estacion estacion = evento.getEstacion();
        List roles = evento.getRoles();

        //REMOVER ROLES A LA ESTACION
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            String aux = (String) it.next();
            Rol rol = new Rol();
            rol.setRolId(aux);
            try {
                fenrir.removeRolEstacion(estacion, rol);
            } catch (Throwable e) {
                vpe.addError("No se pudo remover el rol " + rol.getRolId() + "\n"
                        + "de la estación " + estacion.getEstacionId());
            }
        }

        //SI HUBO ERRORES, MOSTRARLOS
        if (vpe.getErrores().size() > 0) {
            evento.setVpe(vpe);
        }

        //CONSULTAR NUEVO ESTADO GENERAL DE LA ESTACION
        return adminUnaEstacion(evento);

    }

    /**
     *
     * @param evento
     * @return
     */
    private EvnRespAdministracionHermod addRolesEstacion(EvnAdministracionHermod evento)
            throws ValidacionParametrosException {

        ValidacionParametrosException vpe = new ValidacionParametrosException();

        //OBTENER ELEMENTOS DEL EVENTO
        Estacion estacion = evento.getEstacion();
        List roles = evento.getRoles();

        //ANADIR ROLES A LA ESTACION
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            String aux = (String) it.next();
            Rol rol = new Rol();
            rol.setRolId(aux);
            try {
                fenrir.addRolEstacion(estacion, rol, fenrir.getUsuario(evento.getUsuario()));
            } catch (DAOException e) {
                vpe.addError("No se pudo agregar el rol " + rol.getRolId() + "\n"
                        + "a la estación " + estacion.getEstacionId());
            } catch (Throwable t) {
            }
        }

        //SI HUBO ERRORES, MOSTRARLOS
        if (vpe.getErrores().size() > 0) {
            evento.setVpe(vpe);
        }

        //CONSULTAR NUEVO ESTADO GENERAL DE LA ESTACION
        return adminUnaEstacion(evento);

    }

    private EvnRespAdministracionHermod removeUsuariosEstacion(EvnAdministracionHermod evento)
            throws ValidacionParametrosException {

        ValidacionParametrosException vpe = new ValidacionParametrosException();

        //OBTENER ELEMENTOS DEL EVENTO
        //Estacion estacion=evento.getEstacion();
        List usuarios = evento.getUsuarios();

        //REMOVER ROLES A LA ESTACION
        Iterator it = usuarios.iterator();
        while (it.hasNext()) {
            String aux = (String) it.next();
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(Long.parseLong(aux));
            /*try {
				fenrir.removeUsuarioEstacion(estacion,usuario);
			} catch (Throwable e) {
				vpe.addError(	"No se pudo remover el usuario "+usuario.getIdUsuario()+"\n"+
								"de la estación "+estacion.getEstacionId());
			} */
        }

        //SI HUBO ERRORES, MOSTRARLOS
        if (vpe.getErrores().size() > 0) {
            evento.setVpe(vpe);
        }

        //CONSULTAR NUEVO ESTADO GENERAL DE LA ESTACION
        return adminUnaEstacion(evento);

    }

    /**
     *
     * @param evento
     * @return
     */
    private EvnRespAdministracionHermod addUsuariosEstacion(EvnAdministracionHermod evento)
            throws ValidacionParametrosException {

        ValidacionParametrosException vpe = new ValidacionParametrosException();

        //OBTENER ELEMENTOS DEL EVENTO
        //Estacion estacion=evento.getEstacion();
        List usuarios = evento.getUsuarios();

        //ANADIR ROLES A LA ESTACION
        Iterator it = usuarios.iterator();
        while (it.hasNext()) {
            String aux = (String) it.next();
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(Long.parseLong(aux));
            /*try {
				fenrir.addUsuarioEstacion(estacion,usuario);
			} catch (DAOException e) {
				vpe.addError(	"No se pudo agregar el usuario "+usuario.getIdUsuario()+"\n"+
								"a la estación "+estacion.getEstacionId());
			} catch (Throwable t) {}*/
        }

        //SI HUBO ERRORES, MOSTRARLOS
        if (vpe.getErrores().size() > 0) {
            evento.setVpe(vpe);
        }

        //CONSULTAR NUEVO ESTADO GENERAL DE LA ESTACION
        return adminUnaEstacion(evento);

    }

    /**
     * @param evento
     * @return EvnRespuesta con Estacion con recibo inicial y final
     */
    private EvnRespAdministracionHermod adminUnaEstacion(EvnAdministracionHermod evento) {

        //OBTENER ELEMENTOS DEL EVENTO
        Estacion estacion = evento.getEstacion();

        //OBTENER SERIAL DE RECIBOS
        EstacionRecibo er = null;
        List potRoles = null;
        List usuarios = null;
        List potUsuarios = null;
        try {
            er = fenrir.getRecibosEstacion(estacion);
            fenrir.loadRolesEstacion(estacion);
            potRoles = fenrir.getRolesPotencialesEstacion(estacion);
            usuarios = fenrir.getUsuariosEstacion(estacion);
            potUsuarios = fenrir.getUsuariosPotencialesEstacion(estacion);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e);
        }

        //CREAR EL EVENTO DE RESPUESTA
        EvnRespAdministracionHermod erah = new EvnRespAdministracionHermod(null, EvnRespAdministracionHermod.ADMIN_UNA_ESTACION_OK);
        erah.setEstacion(estacion);
        erah.setEstacionRecibo(er);
        erah.setRolesPotenciales(potRoles);
        erah.setUsuariosEstacion(usuarios);
        erah.setUsuariosPotenciales(potUsuarios);
        if (evento.getVpe() != null) {
            erah.setVpe(evento.getVpe());
        }

        return erah;
    }

    /**
     * Consulta las estaciones de un círculo
     *
     * @param evento
     * @return listado de estaciones
     * @throws EventoException
     */
    private EventoRespuesta consultaEstacionesCirculo(EvnAdministracionHermod evento) throws EventoException {

        Circulo circulo = evento.getCirculo();
        List estaciones = null;

        try {
            estaciones = fenrir.consultarEstacionesPorCirculo(circulo);

        } catch (FenrirException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(estaciones, EvnRespAdministracionHermod.CONSULTA_ESTACIONES_CIRCULO_OK);

        return evRespuesta;

    }

    /**
     * Consulta las estaciones de un círculo
     *
     * @param evento
     * @return listado de estaciones
     * @throws EventoException
     */
    private EventoRespuesta consultaTurnosConPagoMayorValorVencido(EvnAdministracionHermod evento) throws EventoException {

        Circulo circulo = evento.getCirculo();
        org.auriga.core.modelo.transferObjects.Usuario usuarioEnSesion = evento.getUsuario();

        List turnos = null;

        try {
            long id = fenrir.darIdUsuario(usuarioEnSesion.getUsuarioId());
            if (id == -1) {
                throw new LoginNoProcesadoException("Usuario inválido");
            }

            UsuarioPk uid = new UsuarioPk();
            uid.idUsuario = id;
            Usuario todo = fenrir.getUsuarioById(uid);
            UsuarioCirculo usCir = (UsuarioCirculo) todo.getUsuarioCirculos().get(0);
            Circulo circuloUsuario = usCir.getCirculo();

            if (circulo == null && !circulo.getIdCirculo().equals(circuloUsuario.getIdCirculo())) {
                circulo = circuloUsuario;
            }

            turnos = hermod.getTurnosConPagoMayorValorVencido(circulo);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(null, EvnRespAdministracionHermod.CONSULTAR_TURNOS_PMY_VENCIDOS_OK);
        evRespuesta.setTurnos(turnos);
        return evRespuesta;

    }

    /**
     * Consulta las estaciones de un círculo
     *
     * @param evento
     * @return listado de estaciones
     * @throws EventoException
     */
    private EventoRespuesta consultaTurnosConPagoMayorValorPendiente(EvnAdministracionHermod evento) throws EventoException {

        Circulo circulo = evento.getCirculo();
        List turnos = null;
        List notasInf = null;
        String[] fasesIds = {CFase.PMY_REGISTRAR, CFase.PMY_CUSTODIA, CFase.PMY_NOTIFICAR_CIUDADANO};
        ProcesoPk id = new ProcesoPk();
        id.idProceso = Integer.parseInt(CProceso.PROCESO_PAGO_MAYOR_VALOR);

        try {
            turnos = hermod.getTurnosConPagoMayorValorPendiente(circulo);

            notasInf = new ArrayList();
            for (int i = 0; i < fasesIds.length; i++) {
                notasInf.addAll(hermod.getTiposNotaProceso(id, fasesIds[i]));
            }

            Turno turnoActual;
            Fase faseObtenida;
            for (Iterator iterTurnos = turnos.iterator(); iterTurnos.hasNext();) {
                turnoActual = (Turno) iterTurnos.next();
                faseObtenida = hermod.getFase(turnoActual.getIdFase());

                if (faseObtenida != null) {
                    turnoActual.setIdFase(faseObtenida.getNombre());
                }
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(null, EvnRespAdministracionHermod.CONSULTAR_TURNOS_PMY_PENDIENTES_OK);
        evRespuesta.setTurnos(turnos);
        evRespuesta.setNotas(notasInf);
        return evRespuesta;

    }

    /**
     * Consulta las estaciones de un círculo
     *
     * @param evento
     * @return listado de estaciones
     * @throws EventoException
     */
    private EventoRespuesta agregarNotaDevolutivaYAvanzarTurnosPagoMayorVencido(EvnAdministracionHermod evento) throws EventoException {

        Circulo circulo = evento.getCirculo();
        Usuario usuario = evento.getUsuarioSIR();
        List turnos = null;
        TipoNota tipoNota;
        //tipoNota.setIdTipoNota(CTipoNota.NOTA_DEVOLUTIVA_VENCIMIENTO_MAYOR_VALOR);
        TipoNotaPk tipoNotaId = new TipoNotaPk();
        tipoNotaId.idTipoNota = CTipoNota.NOTA_DEVOLUTIVA_VENCIMIENTO_MAYOR_VALOR;

        /**
         * @author : HGOMEZ, FPADILLA
         *** @change : Ajustes respectivos para setear versión del Tipo de
         * Nota. ** Caso Mantis : 12621
         */
        DatosNotas datosNotas = DatosNotas.getInstance();
        tipoNotaId.version = datosNotas.obtenerVersionTipoNota(CTipoNota.NOTA_DEVOLUTIVA_VENCIMIENTO_MAYOR_VALOR);

        Fase fase = new Fase();

        Vector notasDevolutivas = new Vector();

        int numCopy = evento.getNumeroImpresiones();

        if (numCopy < 1) {
            numCopy = 1;
        }

        try {
            turnos = hermod.getTurnosConPagoMayorValorVencido(circulo);
            tipoNota = hermod.getTipoNota(tipoNotaId);
            String descripcion = tipoNota.getDescripcion();

            Turno turno;
            //Se recorre la lista para agregar la nota y avanzar el turno
            for (Iterator it = turnos.iterator(); it.hasNext();) {
                turno = (Turno) it.next();
                try {
                    Nota nota = new Nota();
                    nota.setTiponota(tipoNota);
                    nota.setUsuario(usuario);

                    TurnoPk tid = new TurnoPk();
                    tid.anio = turno.getAnio();
                    tid.idCirculo = turno.getIdCirculo();
                    tid.idProceso = turno.getIdProceso();
                    tid.idTurno = turno.getIdTurno();

                    hermod.addNotaToTurno(nota, tid);

                    Hashtable tabla = new Hashtable();
                    boolean esActuacion = esActuacionAdministrativa(turno);
                    if (!esActuacion) {
                        tabla.put(Processor.RESULT, CRespuesta.FRACASO);
                    } else {
                        String ACTUACION_ADMINISTRATIVA = "ACTUACION_ADMINISTRATIVA";
                        tabla.put(Processor.RESULT, ACTUACION_ADMINISTRATIVA);
                    }

                    tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

                    if (tid.idProceso == Long.parseLong(CProceso.PROCESO_CERTIFICADOS)) {
                        fase.setID(CFase.ANT_REVISION_INICIAL);
                    } else if (tid.idProceso == Long.parseLong(CProceso.PROCESO_CORRECCIONES)) {
                        fase.setID(CFase.COR_REVISION_ANALISIS);
                    } else if (tid.idProceso == Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                        fase.setID(CFase.CAL_CALIFICACION);
                    } else {
                        throw new EventoException("El proceso del turno " + turno.getIdWorkflow() + " no está soportado");
                    }
                    nota.setIdFase(fase.getID());

                    hermod.modificarRespuestaUltimaFase(turno, fase, (String) CRespuesta.DEVOLUCION);

                    fase.setID(turno.getIdFase());

                    // Se crea el imprimible
                    notasDevolutivas.clear();
                    notasDevolutivas.add(nota);

                    String nombreCirculo = circulo.getNombre();

                    List solicitudesFolio = turno.getSolicitud().getSolicitudFolios();

                    String matricula = "";

                    // Pueden existir turnos sin matrículas asociadas, así que primero se hace la validación
                    if (solicitudesFolio.size() > 0) {
                        SolicitudFolio solicitudFolio = (SolicitudFolio) solicitudesFolio.get(solicitudesFolio.size() - 1);
                        matricula = solicitudFolio.getIdMatricula();
                    }

                    /*SE DEBE IMPRIMIR LA JUSTIFICACION DE MAYOR VALOR EN LA NOTA DEVOLUTIVA (EN CASO DE QUE TENGA)*/
                    if (turno.getSolicitud() instanceof SolicitudRegistro) {

                        turno = hermod.getTurnobyWF(turno.getIdWorkflow());
                        notasDevolutivas.clear();
                        nota.setTurno(turno);
                        notasDevolutivas.add(nota);
                        ImprimibleNotaDevolutivaCalificacion imprimible = null;
                        String certAsociados = this.obtenerTextoCertificadosAsociados(turno);

                        String justificacionMayorValor = null;
                        List liquidaciones = turno.getSolicitud().getLiquidaciones();
                        if (liquidaciones.size() > 0) {
                            if (liquidaciones.get(liquidaciones.size() - 1) instanceof LiquidacionTurnoRegistro) {
                                justificacionMayorValor = ((LiquidacionTurnoRegistro) liquidaciones.get(liquidaciones.size() - 1)).getJustificacionMayorValor();

                            }
                        }

                        SolicitudRegistro solR = (SolicitudRegistro) turno.getSolicitud();
                        imprimible = new ImprimibleNotaDevolutivaCalificacion(notasDevolutivas, nombreCirculo, turno.getIdWorkflow(), matricula, solR.getDocumento(), usuario, certAsociados, this.getFechaImpresion(), CTipoImprimible.NOTAS_DEVOLUTIVAS);
                        imprimible.setJustificacionMayorValor(justificacionMayorValor);
                        /**
                         * @author Fernado Padila
                         * @change mantis 5423: Acta - Requerimiento No 206 -
                         * FORMATO NOTA DEVOLUTIVA, Se setea el nombre del
                         * departamento de la oficina que trabaja el turno.
                         */
                        imprimible.setNombreDepartamento(solR.getCirculo().getOficinaOrigen().
                                getVereda().getMunicipio().getDepartamento().getNombre());

                        imprimible.setPrintWatermarkEnabled(true);

                        //PageFormat pg = new PageFormat();
                        //imprimible.generate(pg);
                        tabla.put(Processor.IMPRIMIBLE, imprimible);

                        /*
						SolicitudFolio sf = null;
						List folios = turno.getSolicitud().getSolicitudFolios();
						
						for (Iterator itr = folios.iterator(); itr.hasNext();) {
			                sf = (SolicitudFolio) itr.next();
			                matricula = sf.getIdMatricula();
			                hermod.get
                         */
                        //TFS 3814: BORRAR EL ID DE RELACION AL DEVOLVER EL TURNO A CALIFICACION
                        hermod.eliminarRelacionTurno(tid);

                        hermod.avanzarTurnoNuevoCualquiera(turno, fase, tabla, evento.getUsuarioSIR());

                        String idCirculo = circulo.getIdCirculo();

                        imprimir(idCirculo, imprimible, numCopy);

                        hermod.removeLiquidacionesSinPagoFromTurno(tid);

                    } else {
                        ImprimibleNotaDevolutiva imprimible = null;
                        imprimible = new ImprimibleNotaDevolutiva(notasDevolutivas, nombreCirculo,
                                turno.getIdWorkflow(), matricula, this.getFechaImpresion(), CTipoImprimible.NOTAS_DEVOLUTIVAS);

                        imprimible.setPrintWatermarkEnabled(true);

                        tabla.put(Processor.IMPRIMIBLE, imprimible);

                        hermod.avanzarTurnoNuevoNormal(turno, fase, tabla, evento.getUsuarioSIR());

                        String idCirculo = circulo.getIdCirculo();

                        imprimir(idCirculo, imprimible, numCopy);

                    }
                    Thread.sleep(333);
                } catch (Throwable e) {
                    Log.getInstance().error(ANAdministracionHermod.class, e);
                }

            }

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(null, EvnRespAdministracionHermod.AVANZAR_TURNOS_PMY_VENCIDOS_OK);
        evRespuesta.setTurnos(turnos);
        return evRespuesta;

    }

    /**
     * @param string
     */
    private String obtenerTextoCertificadosAsociados(Turno turno) {

        String retorno = " Y CERTIFICADO ASOCIADO: 0";
        String numeroCertificados = "";

        SolicitudAsociada solicitudAsociada = null;
        Solicitud solicitudHija = null;
        Turno turnoHijo = null;

        if (turno != null) {
            Solicitud solicitud = turno.getSolicitud();
            if (solicitud != null) {
                List solicitudesHijas = solicitud.getSolicitudesHijas();
                if (solicitudesHijas != null && solicitudesHijas.size() > 0) {
                    Iterator itSolHija = solicitudesHijas.iterator();
                    while (itSolHija.hasNext()) {
                        solicitudAsociada = (SolicitudAsociada) itSolHija.next();
                        solicitudHija = solicitudAsociada.getSolicitudHija();
                        turnoHijo = solicitudHija.getTurno();
                        numeroCertificados = numeroCertificados + turnoHijo.getIdWorkflow() + ", ";
                    }
                }
            }
        }
        if (numeroCertificados.length() > 0) {
            numeroCertificados = numeroCertificados.substring(0, (numeroCertificados.length() - 2));
            retorno = " Y CERTIFICADOS ASOCIADOS: " + numeroCertificados;
        }
        return retorno;
    }

    private void imprimir(String idCirculo, ImprimibleNotaDevolutiva imprimible, int numeroCopias) {

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

        Bundle bundle = new Bundle(imprimible);
        bundle.setNumeroCopias(numeroCopias);

        try {
            //se manda a imprimir el recibo por el identificador unico de usuario
            printJobs.enviar(idCirculo, bundle, maxIntentos, espera);

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    private void imprimir(String idCirculo, ImprimibleNotaDevolutivaCalificacion imprimible, int numeroCopias) {

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

        Bundle bundle = new Bundle(imprimible);
        bundle.setNumeroCopias(numeroCopias);

        try {
            //se manda a imprimir el recibo por el identificador unico de usuario
            printJobs.enviar(idCirculo, bundle, maxIntentos, espera);

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    /**
     * Consulta las estaciones de un círculo
     *
     * @param evento
     * @return listado de estaciones
     * @throws EventoException
     */
    private EventoRespuesta eliminarTurnosPagoMayorValorPendiente(EvnAdministracionHermod evento) throws EventoException {

        //Circulo circulo = evento.getCirculo();
        //Usuario usuario = evento.getUsuarioSIR();
        // La mejor alternativa para obtener los turnos es contar con los que se cargaron
        // previamente
        //List turnos = evento.getTurnos();
        String idTurno = evento.getIdTurno();
        Nota nota = evento.getNotaInformativaCorreccion();
        //TipoNota tipoNota = new TipoNota();
        //tipoNota.setIdTipoNota(CTipoNota.INFORMATIVA);

        //String descripcion = "PAGO DE MAYOR VALOR ELIMINADO";
        Fase fase = new Fase();

        try {
            Turno turno = hermod.getTurnobyWF(idTurno);

            try {
                TurnoPk tid = new TurnoPk();
                tid.anio = turno.getAnio();
                tid.idCirculo = turno.getIdCirculo();
                tid.idProceso = turno.getIdProceso();
                tid.idTurno = turno.getIdTurno();

                //TFS 3814: BORRAR LA RELACION
                if (turno.getSolicitud() instanceof SolicitudRegistro) {
                    hermod.eliminarRelacionTurno(tid);
                }

                hermod.removeLiquidacionesSinPagoFromTurno(tid);
                hermod.addNotaToTurno(nota, tid);

                Hashtable tabla = new Hashtable();
                boolean esActuacion = esActuacionAdministrativa(turno);
                if (!esActuacion) {
                    tabla.put(Processor.RESULT, CRespuesta.ELIMINACION);
                } else {
                    String ACTUACION_ADMINISTRATIVA = "ACTUACION_ADMINISTRATIVA";
                    tabla.put(Processor.RESULT, ACTUACION_ADMINISTRATIVA);
                }

                tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

                fase.setID(turno.getIdFase());

                hermod.avanzarTurnoNuevoPop(turno, fase, tabla, evento.getUsuarioSIR());
            } catch (Throwable e) {
                Log.getInstance().error(ANAdministracionHermod.class, e);
            }

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(null, EvnRespAdministracionHermod.AVANZAR_TURNOS_PMY_PENDIENTES_OK);
        return evRespuesta;

    }

    /**
     * Permite determinar la respuesta para el avance, si el proceso de pago
     * mayor valor fue llamado desde actuaciones administrativas. retorna TRUE
     * sino retorna FALSE.
     *
     * @param evento
     * @return
     */
    private boolean esActuacionAdministrativa(Turno turno) throws EventoException {

        boolean esActuacion = false;

        List turnosHistoria = turno.getHistorials();

        TurnoHistoria thUltimo = null;
        if (turnosHistoria != null && turnosHistoria.size() > 0) {
            Iterator it = turnosHistoria.iterator();

            while (it.hasNext()) {
                thUltimo = (TurnoHistoria) it.next();

                if (thUltimo.getFase() != null && thUltimo.getFase().equals(CFase.PMY_CUSTODIA)) {
                    if (thUltimo.getFaseAnterior() != null && thUltimo.getFaseAnterior().equals(CFase.COR_ACT_ADMIN)) {
                        esActuacion = true;
                    } else {
                        esActuacion = false;
                    }

                }
            }

        }

        return esActuacion;
    }

    /**
     * Consulta los tipos de tarifa del círculo
     *
     * @param evento
     * @return listado de estaciones
     * @throws EventoException
     */
    private EventoRespuesta consultaTiposTarifaPorCirculo(EvnAdministracionHermod evento) throws EventoException {

        Circulo circulo = evento.getCirculo();
        List tipos = null;

        try {
            tipos = hermod.getTiposTarifasPorCirculo(circulo);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(tipos, EvnRespAdministracionHermod.CONSULTA_TIPOS_TARIFA_POR_CIRCULO_OK);

        return evRespuesta;

    }

    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar un nuevo <code>Banco</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>Banco</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los bancos para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaBanco(EvnAdministracionHermod evento)
            throws EventoException {

        List bancos = null;

        try {
            if (hermod.addBanco(evento.getBanco())) {
                bancos = hermod.getBancos();
            } else {
                throw new CreacionBancoException("No se pudo crear el Banco", null);
            }

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(bancos, EvnRespAdministracionHermod.ADICIONA_BANCO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>Banco</code> en la base de
     * datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>Banco</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los Bancos para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaBanco(EvnAdministracionHermod evento)
            throws EventoException {

        List bancos = null;
        try {
            if (hermod.eliminarBanco(evento.getBanco())) {
                bancos = hermod.getBancos();
            } else {
                throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Banco", null);
            }
        } catch (HermodException e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Banco", e);
        } catch (Throwable e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Banco", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(bancos, EvnRespAdministracionHermod.ADICIONA_BANCO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de traer la información de las sucursales
     * asociadas a un <code>Banco</code>
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>Banco</code> a ser consultado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista de
     * sucursales del <code>Banco</code> consultado
     * @throws EventoException
     *
     */
    private EvnRespAdministracionHermod listarSucursales(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            Banco banco = evento.getBanco();
            BancoPk bid = new BancoPk();
            bid.idBanco = banco.getIdBanco();
            datos = hermod.getSucursalesBanco(bid);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.LISTADO_SUCURSALES_OK);
        return evRespuesta;
    }
    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de adicionar una nueva <code>SucursalBanco</code>
     * en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>SucursalBanco</code> a ser creada.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las sucursales del banco, para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaSucursalBanco(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            SucursalBanco sucursalBanco = evento.getSucursalBanco();
            if (hermod.addSucursalBanco(sucursalBanco)) {
                Banco banco = evento.getBanco();
                BancoPk bid = new BancoPk();
                bid.idBanco = banco.getIdBanco();
                datos = hermod.getSucursalesBanco(bid);
            } else {
                throw new EventoException("No se pudo crear la sucursal", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_SUCURSAL_BANCO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar una <code>SucursalBanco</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información de la <code>SucursalBanco</code> a ser eliminada.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las Sucursales - Banco para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaSucursalBanco(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            SucursalBanco sucursalBanco = evento.getSucursalBanco();
            if (hermod.eliminarSucursalBanco(sucursalBanco)) {
                Banco banco = evento.getBanco();
                BancoPk bid = new BancoPk();
                bid.idBanco = banco.getIdBanco();
                datos = hermod.getSucursalesBanco(bid);
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar la sucursal",
                        null);
            }
        } catch (HermodException e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar la sucursal del Banco", e);
        } catch (Throwable e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar la sucursal del Banco", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_SUCURSAL_BANCO_OK);
        return evRespuesta;
    }
    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de adicionar un nuevo
     * <code>AlcanceGeografico</code> en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>AlcanceGeografico</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de alcances geográficos para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaAlcanceGeografico(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.addTipoAlcanceGeografico(evento.getAlcanceGeografico())) {
                datos = hermod.getTiposAlcanceGeografico();
            } else {
                throw new EventoException("No se pudo crear el Tipo de Alcance Geográfico", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_ALCANCE_GEOGRAFICO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>AlcanceGeografico</code> en
     * la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>AlcanceGeografico</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los alcances geográficos para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaAlcanceGeografico(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.eliminarAlcanceGeografico(evento.getAlcanceGeografico())) {
                datos = hermod.getTiposAlcanceGeografico();
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Alcance Geográfico",
                        null);
            }

        } catch (HermodException e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Alcance Geografico", e);
        } catch (Throwable e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Alcance Geografico", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_ALCANCE_GEOGRAFICO_OK);
        return evRespuesta;
    }
    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de adicionar una nueva <code>Tarifa</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>Tarifa</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las tarifas para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaTarifa(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.addTarifa(evento.getTarifa())) {
                datos = hermod.getTarifas(evento.getTipoTarifa());
            } else {
                throw new EventoException("No se pudo crear la tarifa", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_TARIFA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de adicionar una nueva <code>Tarifa</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>Tarifa</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las tarifas para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaTarifaPorCirculo(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            Circulo c = evento.getCirculo();
            CirculoPk cID = new CirculoPk();
            cID.idCirculo = c.getIdCirculo();

            c = forseti.getCirculo(cID);
            if (c == null) {
                throw new EventoException("No se pudo crear la tarifa: el círculo No existe " + cID.idCirculo);
            }

            Tarifa t = evento.getTarifa();
            t.setIdCodigo(c.getNombre());
            if (hermod.addTarifa(t)) {
                datos = hermod.getTiposTarifasPorCirculo(c);
            } else {
                throw new EventoException("No se pudo crear la tarifa", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_TARIFA_POR_CIRCULO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar una <code>Tarifa</code> en la base de
     * datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>Tarifa</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las tarifas para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaTarifa(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.eliminarTarifa(evento.getTarifa())) {
                datos = hermod.getTarifas(evento.getTipoTarifa());
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar la Tarifa",
                        null);
            }
        } catch (HermodException e) {
            throw new EventoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar la Tarifa",
                    e);
        } catch (Throwable e) {
            throw new EventoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar la Tarifa",
                    e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ELIMINA_TARIFA_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar un nuevo <code>TipoTarifa</code> en
     * la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>TipoTarifa</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de tarifa para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaTipoTarifa(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.addTipoTarifa(evento.getTarifa())) {
                datos = hermod.getTiposTarifas();
            } else {
                throw new EventoException("No se pudo crear el tipo de tarifa", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_TIPO_TARIFA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de traer lalista de Tarifas de un tipo específico
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con el
     * <code>TipoTarifa</code> ser consultado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista de tarifas
     * del tipo requerido
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod seleccionaTipoTarifa(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            datos = hermod.getTarifas(evento.getTipoTarifa());
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.SELECCIONA_TIPO_TARIFA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>TipoTarifa</code> en la base
     * de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>TipoTarifa</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de tipos de tarifa para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaTipoTarifa(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.eliminarTipoTarifa(evento.getTipoTarifa())) {
                datos = hermod.getTiposTarifas();
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar el tipo de Tarifa",
                        null);
            }
        } catch (DAOException e) {
            throw new TipoTarifaNoEliminadaException("No se puede eliminar un tipo de tarifa que tenga tarifas asociadas.");
        } catch (HermodException e) {

            throw new TipoTarifaNoEliminadaException("No se puede eliminar un tipo de tarifa que tenga tarifas asociadas.");
        } catch (Throwable e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el tipo de Tarifa",
                    null);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ELIMINA_TIPO_TARIFA_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar un nuevo <code>TipoFotocopia</code>
     * en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>TipoFotocopia</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de fotocopia para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaTipoFotocopia(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.addTipoFotocopia(evento.getTipoFotocopia())) {
                datos = hermod.getTiposFotocopia();
            } else {
                throw new EventoException("No se pudo crear el tipo de fotocopia", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_TIPO_FOTOCOPIA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>TipoFotocopia</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>Banco</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de fotocopia para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaTipoFotocopia(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.eliminarTipoFotocopia(evento.getTipoFotocopia())) {
                datos = hermod.getTiposFotocopia();
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar el tipo de Fotocopia",
                        null);
            }

        } catch (HermodException e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Fotocopia", e);
        } catch (Throwable e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Fotocopia", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_TIPO_FOTOCOPIA_OK);
        return evRespuesta;
    }
    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de adicionar un nuevo
     * <code>SubtipoSolicitud</code> en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>SubtipoSolicitud</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los subtipos de solicitud para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaSubtipoSolicitud(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.addSubTipoSolicitud(evento.getSubtipoSolicitud())) {
                datos = hermod.getSubTiposSolicitud();
            } else {
                throw new EventoException("No se pudo crear el subtipo de solicitud", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_SUBTIPO_SOLICITUD_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>SubtipoSolicitud</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>SubtipoSolicitud</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los subtipos de solicitud para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaSubtipoSolicitud(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.eliminarSubtipoSolicitud(evento.getSubtipoSolicitud())) {
                datos = hermod.getSubTiposSolicitud();
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar el subtipo de solicitud",
                        null);
            }
        } catch (HermodException e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Subtipo de Solicitud", e);
        } catch (Throwable e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Subtipo de Solicitud", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_SUBTIPO_SOLICITUD_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de editar un <code>SubtipoSolicitud</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>SubtipoSolicitud</code> a ser editado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los subtipos de solicitud para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod editarSubtipoSolicitud(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.updateSubTipoSolicitud(evento.getSubtipoSolicitud())) {
                datos = hermod.getSubTiposSolicitud();
            } else {
                throw new EventoException("No se pudo crear el subtipo de solicitud", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_SUBTIPO_SOLICITUD_OK);
        return evRespuesta;
    }
    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de adicionar un nuevo <code>OPLookupTyp</code> en
     * la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>OPLookupTyp</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los OPLookupTypes para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaOPLookupType(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {

            datos = hermod.getOPLookupTypes();

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_OPLOOKUP_TYPE_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de adicionar un nuevo <code>OPLookupTyp</code> en
     * la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>OPLookupTyp</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los OPLookupTypes para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaOPLookupType(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.addOPLookupType(evento.getOpLookupType())) {
                datos = hermod.getOPLookupTypes();
            } else {
                throw new ValidacionParametrosException("No se pudo crear el Lookup Type", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_OPLOOKUP_TYPE_OK);
        recargarContext(evRespuesta);

        return evRespuesta;
    }

    /**
     * Este método se encarga de editar un  <code>OPLookupTyp</code> en la base
     * de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>OPLookupTyp</code> a ser editado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los OPLookupTypes para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod updateOPLookupType(EvnAdministracionHermod evento)
            throws ValidacionParametrosException {

        List datos = null;

        OPLookupTypes datoEditar = evento.getOpLookupTypeAEditar();
        OPLookupTypes dato = evento.getOpLookupType();

        try {
            if (hermod.updateOPLookupType(datoEditar, dato)) {
                datos = hermod.getOPLookupTypes();
            } else {
                throw new EventoException("No se pudo crear el Lookup Type", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new ValidacionParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new ValidacionParametrosException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_OPLOOKUP_TYPE_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>OPLookupType</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>OPLookupType</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los OPLookupTypes para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaOPLookupType(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.eliminarLookupType(evento.getOpLookupType())) {
                datos = hermod.getOPLookupTypes();
            } else {
                throw new EliminacionBancoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar el Lookup Type",
                        null);
            }

        } catch (HermodException e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Lookup Type", e);
        } catch (Throwable e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Lookup Type", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ELIMINA_OPLOOKUP_TYPE_OK);
        recargarContext(evRespuesta);
        return evRespuesta;
    }

    /**
     * Este método se encarga de traer la lista de OPLookupCodes de un tipo
     * específico de <code>OPLookupCode</code>
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con el
     * <code>TipoTarifa</code> ser consultado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista de tarifas
     * de los OPLookupCodes
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod listarOPLookupCodes(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            datos = hermod.getOPLookupCodes(evento.getOpLookupType().getTipo());
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.LISTADO_LOOKUP_CODES_OK);
        return evRespuesta;
    }

    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar un nuevo <code>OPLookupCode</code> en
     * la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>OPLookupCode</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los OPLookupCodes para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaOPLookupCode(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.addOPLookupCode(evento.getOpLookupCodes())) {
                datos = hermod.getOPLookupCodes(evento.getOpLookupType().getTipo());
            } else {
                throw new EventoException("No se pudo crear el Lookup Code", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_OPLOOKUP_CODE_OK);
        recargarContext(evRespuesta);
        return evRespuesta;
    }

    /**
     * Este método se encarga de editar un <code>OPLookupCode</code> en la base
     * de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>OPLookupCode</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los OPLookupCodes para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod updateOPLookupCode(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.updateOPLookupCode(evento.getOpLookupCodesAEditar(), evento.getOpLookupCodes())) {
                datos = hermod.getOPLookupCodes(evento.getOpLookupType().getTipo());
            } else {
                throw new EventoException("No se pudo crear el Lookup Code", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_OPLOOKUP_CODE_OK);
        recargarContext(evRespuesta);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>OPLookupCode</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>OPLookupCode</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los OPLookupCodes para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaOPLookupCode(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        OPLookupTypes tipo = evento.getOpLookupType();
        try {
            if (hermod.eliminarLookupCode(evento.getOpLookupCodes())) {
                datos = hermod.getOPLookupCodes(tipo.getTipo());
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar el Lookup Type",
                        null);
            }
        } catch (HermodException e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Lookup Code", e);
        } catch (Throwable e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Lookup Code", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ELIMINA_OPLOOKUP_CODE_OK);
        recargarContext(evRespuesta);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar un nuevo <code>TipoCalculo</code> en
     * la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>TipoCalculo</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de cálculo para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaTipoCalculo(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.addTipoCalculo(evento.getTipoCalculo())) {
                datos = hermod.getTiposCalculo();
            } else {
                throw new EventoException("No se pudo crear el Tipo de Cálculo", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_TIPO_CALCULO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>TipoCalculo</code> en la base
     * de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>TipoCalculo</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de cálculo para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaTipoCalculo(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.eliminarTipoCalculo(evento.getTipoCalculo())) {
                datos = hermod.getTiposCalculo();
            } else {
                throw new EliminacionBancoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Cálculo",
                        null);
            }
        } catch (HermodException e) {
            throw new EliminacionBancoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Cálculo",
                    e);
        } catch (Throwable e) {
            throw new EliminacionBancoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Cálculo",
                    e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ELIMINA_TIPO_CALCULO_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar un nuevo <code>TipoImpuesto</code> en
     * la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>TipoImpuesto</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de impuesto para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaTipoImpuesto(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.addTipoImpuesto(evento.getTipoImpuesto())) {
                datos = hermod.getTiposImpuesto();
            } else {
                throw new EventoException("No se pudo crear el Tipo de impuesto", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_TIPO_IMPUESTO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>TipoImpuesto</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>TipoImpuesto</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de impuesto para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaTipoImpuesto(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.eliminarTipoImpuesto(evento.getTipoImpuesto())) {
                datos = hermod.getTiposImpuesto();
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de impuesto",
                        null);
            }
        } catch (HermodException e) {
            throw new EventoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de impuesto",
                    e);
        } catch (Throwable e) {
            throw new EventoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de impuesto",
                    e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ELIMINA_TIPO_IMPUESTO_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de cargar los <code>SubtipoAtencion</code>
     * completos de la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>SubtipoAtencion</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los subtipos de atención para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod cargarSubtipoAtencion(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {

            datos = hermod.getSubTiposAtencionCompleto();

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_SUBTIPO_ATENCION_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de adicionar un nuevo <code>SubtipoAtencion</code>
     * en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>SubtipoAtencion</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los subtipos de atención para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaSubtipoAtencion(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.addSubTipoAtencion(evento.getSubtipoAtencion())) {
                datos = hermod.getSubTiposAtencionCompleto();
            } else {
                throw new EventoException("No se pudo crear el Subtipo de Atención", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_SUBTIPO_ATENCION_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de adicionar un nuevo <code>SubtipoAtencion</code>
     * en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>SubtipoAtencion</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los subtipos de atención para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod editarSubtipoAtencion(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.updateSubTipoAtencion(evento.getSubtipoAtencion())) {
                datos = hermod.getSubTiposAtencionCompleto();
            } else {
                throw new EventoException("No se pudo crear el Subtipo de Atención", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_SUBTIPO_ATENCION_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>SubtipoAtencio</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>SubtipoAtencio</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los subtipos de atención para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaSubtipoAtencion(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.eliminarSubtipoAtencion(evento.getSubtipoAtencion())) {
                datos = hermod.getSubTiposAtencionCompleto();
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar el Subtipo de Atención",
                        null);
            }
        } catch (HermodException e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Subtipo de Atención", e);
        } catch (Throwable e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Subtipo de Atención", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_SUBTIPO_ATENCION_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar un nuevo <code>TipoActo</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>TipoActo</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de acto para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaTipoActo(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.addTipoActo(evento.getTipoActo(), fenrir.getUsuario(evento.getUsuario()))) {
                datos = hermod.getTiposActoDos();
            } else {
                throw new EventoException("No se pudo crear el Tipo de Acto", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_TIPO_ACTO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de adicionar un nuevo <code>TipoActo</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>TipoActo</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de acto para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod editaTipoActo(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.updateTipoActo(evento.getTipoActo(), fenrir.getUsuario(evento.getUsuario()))) {
                /*
                            * @autor          : JATENCIA
                            * @mantis        : 0015082 
                            * @Requerimiento : 027_589_Acto_liquidación_copias 
                            * @descripcion   : Se redirige al metodo que no filtra. 
                 */
                datos = hermod.getTiposActoDos();
                /* - Fin del bloque - */
            } else {
                throw new EventoException("No se pudo crear el Tipo de Acto", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_TIPO_ACTO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>TipoActo</code> en la base de
     * datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>TipoActo</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de acto para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaTipoActo(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.eliminarTipoActo(evento.getTipoActo(), fenrir.getUsuario(evento.getUsuario()))) {
                /*
                            * @autor          : JATENCIA
                            * @mantis        : 0015082 
                            * @Requerimiento : 027_589_Acto_liquidación_copias 
                            * @descripcion   : Se redirige al metodo que no filtra. 
                 */
                datos = hermod.getTiposActoDos();
                /* - Fin del bloque - */
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Acto",
                        null);
            }
        } catch (HermodException e) {
            throw new EventoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Acto",
                    e);

        } catch (Throwable e) {
            throw new EventoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Acto",
                    e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ELIMINA_TIPO_ACTO_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar un nuevo
     * <code>TipoDerechoRegistral</code> en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>TipoDerechoRegistral</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de derecho registral para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaTipoDerechoRegistral(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.addTipoDerechoRegistral(evento.getTipoDerechoRegistral())) {
                datos = hermod.getTipoDerechoRegistral();
            } else {
                throw new EventoException("No se pudo crear el Tipo de Derecho Registral", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_TIPO_DERECHO_REGISTRAL_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>TipoDerechoRegistral</code>
     * en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>TipoDerechoRegistral</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de derecho registral para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaTipoDerechoRegistral(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.eliminarTipoDerechoRegistral(evento.getTipoDerechoRegistral())) {
                datos = hermod.getTipoDerechoRegistral();
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Derecho Registral ",
                        null);
            }
        } catch (HermodException e) {
            throw new EventoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Derecho Registral ",
                    e);
        } catch (Throwable e) {
            throw new EventoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Derecho Registral ",
                    e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_TIPO_DERECHO_REGISTRAL_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar un nuevo <code>AccionNotarial</code>
     * en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>AccionNotarial</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las acciones notariales para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaAccionNotarial(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.addAccionNotarial(evento.getAccionNotarial(), fenrir.getUsuario(evento.getUsuario()))) {
                datos = hermod.getTiposAccionNotarial();
            } else {
                throw new EventoException(
                        "No se pudo crear la Acción Notarial (El código proporcionado ya existe en el sistema) ",
                        null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionAccionNotarialException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionAccionNotarialException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_ACCION_NOTARIAL_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de editar una <code>AccionNotarial</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información de la <code>AccionNotarial</code> a ser editada.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las acciones notariales para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod editarAccionNotarial(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.editarAccionNotarial(evento.getAccionNotarial(), fenrir.getUsuario(evento.getUsuario()))) {
                datos = hermod.getTiposAccionNotarial();
            } else {
                throw new EventoException(
                        "No se pudo editar la Acción Notarial",
                        null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionAccionNotarialException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionAccionNotarialException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_ACCION_NOTARIAL_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>AccionNotarial</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>AccionNotarial</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las acciones notariales para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaAccionNotarial(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.eliminarAccionNotarial(evento.getAccionNotarial(), fenrir.getUsuario(evento.getUsuario()))) {
                datos = hermod.getTiposAccionNotarial();
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar la Acción Notarial",
                        null);
            }
        } catch (HermodException e) {
            throw new AdministracionAccionNotarialException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar la Acción Notarial",
                    e);
        } catch (Throwable e) {
            throw new AdministracionAccionNotarialException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar la Acción Notarial",
                    e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_ACCION_NOTARIAL_OK);
        return evRespuesta;
    }

    //	////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar una nueva <code>Categoria</code> en
     * la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>Categoria</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las categorias para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaCategoria(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.addCategoriaReparto(evento.getCategoria(), fenrir.getUsuario(evento.getUsuario()))) {
                datos = hermod.getCategoriasReparto("nombre");
            } else {
                throw new EventoException(
                        "No se pudo crear la Categoría(El código proporcionado ya existe en el sistema) ",
                        null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new ValidacionCategoriaException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new ValidacionCategoriaException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_CATEGORIA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de editar una <code>Categoria</code> en la base de
     * datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>Categoria</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las categorias para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod editaCategoria(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            if (hermod.updateCategoriaReparto(evento.getCategoria(), fenrir.getUsuario(evento.getUsuario()))) {
                datos = hermod.getCategoriasReparto("nombre");
            } else {
                throw new EventoException(
                        "No se pudo crear la Categoría(El código proporcionado ya existe en el sistema) ",
                        null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new ValidacionCategoriaException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new ValidacionCategoriaException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_CATEGORIA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>Categoria</code> en la base
     * de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>Categoria</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las categorias para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaCategoria(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;

        try {
            if (hermod.eliminarCategoria(evento.getCategoria(), fenrir.getUsuario(evento.getUsuario()))) {
                datos = hermod.getCategoriasReparto("nombre");
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar la Categoria",
                        null);
            }
        } catch (HermodException e) {

            throw new EventoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar la Categoria",
                    e);
        } catch (Throwable e) {
            throw new EventoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar la Categoria",
                    e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ELIMINA_CATEGORIA_OK);
        return evRespuesta;
    }

    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de eliminar un <code>Categoria</code> en la base
     * de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>Categoria</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las categorias para que sea desplegada por las capas superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod mostrarCirculoEstacionRecibo(EvnAdministracionHermod evento)
            throws EventoException {

        org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();
        boolean administradorNacional = false;
        List resultado;
        List resultadoProcesos = new ArrayList();

        try {
            //SE CONSULTAN LOS ROLES QUE PUEDEN CONSULTAR TURNOS DE CUALQUIER CIRCULO
            long idUsuario = fenrir.darIdUsuario(user.getUsuarioId());
            List roles = fenrir.darRolUsuario(idUsuario);
            List rolesString = new ArrayList();

            Iterator itRoles = roles.iterator();
            while (itRoles.hasNext()) {
                Rol rol = (Rol) itRoles.next();
                rolesString.add(rol.getRolId());
            }
            if (rolesString.contains(CRoles.ADMINISTRADOR_NACIONAL)) {
                administradorNacional = true;
            }

            if (administradorNacional) {
                resultado = forseti.getCirculos();
            } else {
                resultado = new ArrayList();
            }

            //Se realiza la construccion de los procesos a seleccionar que generan recibo
            List result = hermod.getOPLookupCodes(CEstacionRecibo.PROCESO_RECIBO_ESTACION);

            Iterator itProcesoRecibo = result.iterator();
            while (itProcesoRecibo.hasNext()) {
                OPLookupCodes lookUp;
                lookUp = (OPLookupCodes) itProcesoRecibo.next();
                Proceso p = new Proceso(Long.parseLong(lookUp.getValor()), lookUp.getCodigo(), null);
                resultadoProcesos.add(p);
            }
        } catch (FenrirException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(resultado, EvnRespAdministracionHermod.MOSTRAR_CIRCULO_ESTACION_RECIBO_OK);
        evRespuesta.setProcesosValidosRecibo(resultadoProcesos);
        return evRespuesta;
    }

    /**
     * Este método se encarga de adicionar un nuevo <code>EstacionRecibo</code>
     * en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>EstacionRecibo</code> a ser creado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las estaciones recibo para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod setEstacionRecibo(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            Circulo circulo = evento.getCirculo();
            CirculoPk cid = new CirculoPk();
            cid.idCirculo = circulo.getIdCirculo();
            Usuario user = evento.getUsuarioNeg();

            hermod.setEstacionRecibo(cid, evento.getEstacionRecibo(), user);
            datos = hermod.consultarEstacionesReciboPorCirculo(circulo);

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.SET_ESTACION_RECIBO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de actualizar una <code>EstacionRecibo</code> en
     * la base de datos segun los datos de otro serial de recibo.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información de la <code>EstacionRecibo/code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las estaciones recibo para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod traspasarEstacionRecibo(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {

            Usuario user = evento.getUsuarioNeg();

            Circulo circulo = evento.getCirculo();
            CirculoPk cid = new CirculoPk();
            cid.idCirculo = circulo.getIdCirculo();

            EstacionRecibo estacionReciboDe = evento.getEstacionRecibo();
            EstacionReciboPk eidDe = new EstacionReciboPk();
            eidDe.idEstacion = estacionReciboDe.getIdEstacion();
            eidDe.numeroProceso = estacionReciboDe.getNumeroProceso();

            EstacionRecibo estacionReciboA = evento.getEstacionReciboA();
            EstacionReciboPk eidA = new EstacionReciboPk();
            eidA.idEstacion = estacionReciboA.getIdEstacion();
            eidA.numeroProceso = estacionReciboA.getNumeroProceso();

            estacionReciboDe = hermod.getEstacionRecibo(eidDe, eidDe.numeroProceso);

            if (estacionReciboDe == null) {
                throw new EventoException(
                        "No se pudo hacer el transpaso de seriales Recibo (la estacion de recibo de donde viene los seriales no existe) ",
                        null);
            }

            long ultimoNumero = estacionReciboDe.getUltimoNumero();
            long ultimoNumeroA = estacionReciboDe.getNumeroFinal();
            long primerNumero = 0;

            EstacionRecibo eRecibo = new EstacionRecibo();
            eRecibo.setNumeroInicial(primerNumero);
            eRecibo.setNumeroFinal(primerNumero);
            eRecibo.setUltimoNumero(CEstacionRecibo.NUMERO_INVALIDO_RECIBO);
            eRecibo.setIdEstacion(eidDe.idEstacion);
            eRecibo.setNumeroProceso(eidDe.numeroProceso);

            // Se debe actualizar la EstacionRecibo Origen
            hermod.setEstacionRecibo(cid, eRecibo, user);

            //Se debe ingresar el nuevo Registro
            EstacionRecibo eReciboAux = new EstacionRecibo();
            eReciboAux.setIdEstacion(eidA.idEstacion);
            eReciboAux.setNumeroProceso(eidA.numeroProceso);
            estacionReciboA.setNumeroInicial(ultimoNumero);
            estacionReciboA.setNumeroFinal(ultimoNumeroA);
            estacionReciboA.setUltimoNumero(ultimoNumero);
            hermod.setEstacionRecibo(cid, estacionReciboA, user);

            //EstacionRecibo aux = hermod.getEstacionRecibo(eidA,eidA.numeroProceso);

            /*if(aux!=null && aux.getNumeroProceso() == eidA.numeroProceso ){
				eRecibo=new EstacionRecibo();
				eRecibo.setNumeroInicial(ultimoNumero);
				eRecibo.setNumeroFinal(ultimoNumeroA);
				eRecibo.setUltimoNumero(CEstacionRecibo.NUMERO_INVALIDO_RECIBO);
				eRecibo.setIdEstacion(eidA.idEstacion);
				eRecibo.setNumeroProceso(eidA.numeroProceso);
				hermod.setEstacionRecibo(cid,eRecibo);
			}else{
				estacionReciboA.setNumeroInicial(ultimoNumero);
				estacionReciboA.setNumeroFinal(ultimoNumeroA);
				estacionReciboA.setUltimoNumero(ultimoNumero);
				hermod.setEstacionRecibo(cid,estacionReciboA);
			}*/
            datos = hermod.consultarEstacionesReciboPorCirculo(circulo);

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.TRASPASO_ESTACION_RECIBO_OK);
        return evRespuesta;
    }

    /**
     * Consulta la lista de estaciones pertenecientes a un círculo.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del  <code>Circulo/code> a ser consultado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las estaciones recibo para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaEstacionesReciboPorCirculo(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        Circulo circulo = evento.getCirculo();
        try {
            datos = hermod.consultarEstacionesReciboPorCirculo(circulo);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        List estaciones = null;

        try {
            estaciones = fenrir.consultarEstacionesPorCirculo(circulo);
        } catch (gov.sir.fenrir.FenrirException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.CONSULTA_ESTACIONES_RECIBO_OK);
        evRespuesta.setEstaciones(estaciones);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>EstacionRecibo</code> en la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>EstacionRecibo</code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las estaciones recibo para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaEstacionRecibo(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            Circulo circulo = evento.getCirculo();
            if (hermod.eliminarEstacionRecibo(evento.getEstacionRecibo())) {
                datos = hermod.consultarEstacionesReciboPorCirculo(circulo);
            } else {
                throw new EventoException(
                        "Debido a restricciones de integridad de datos no se pudo eliminar la Acción Notarial",
                        null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_ESTACION_RECIBO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de actualizar el último número de recibo de una
     * <code>EstacionRecibo</code> en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información de la <code>EstacionRecibo/code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las estaciones recibo para que sea desplegada por las capas
     * superiores.
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod resetUltimoValorEstacionRecibo(EvnAdministracionHermod evento)
            throws EventoException {

        try {
            EstacionRecibo estacionRecibo = evento.getEstacionRecibo();
            EstacionReciboPk eid = new EstacionReciboPk();
            eid.idEstacion = estacionRecibo.getIdEstacion();

            if (!hermod.resetUltimoNumeroEstacionRecibo(eid, estacionRecibo.getUltimoNumero())) {
                throw new EventoException(
                        "No se pudo modificar el último valor del recibo para la estación",
                        null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        null,
                        EvnRespAdministracionHermod.RESET_ULTIMO_VALOR_ESTACION_RECIBO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de consultar una <code>EstacionRecibo</code> de la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información de la <code>EstacionRecibo/code> a ser eliminado.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la
     * <code>EstacionRecibo</code> consultada
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaEstacionRecibo(EvnAdministracionHermod evento)
            throws EventoException {

        EstacionRecibo estacion = null;

        try {
            EstacionRecibo estacionRecibo = evento.getEstacionRecibo();
            EstacionReciboPk eid = new EstacionReciboPk();
            eid.idEstacion = estacionRecibo.getIdEstacion();

            estacion = hermod.getEstacionRecibo(eid);
            if (estacion == null) {
                throw new EventoException("No se pudo obtener la Estación / Recibo", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        estacion,
                        EvnRespAdministracionHermod.CONSULTA_VALOR_ESTACION_RECIBO_OK);
        return evRespuesta;
    }

    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de consultar una <code>Minuta</code> de acuerdo al
     * número de turno que se le pasa como parámetro.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información de la <code>Minuta</code> a ser consultada.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la
     * <code>Minuta</code>
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaMinuta(EvnAdministracionHermod evento)
            throws EventoException {

        Minuta datos = null;
        try {
            datos = hermod.getMinutaByTurnoWF(evento.getTurnoMinutaId());
            if (datos == null) {
                throw new EventoException(
                        "No se encontró la minuta asociada con el número de turno ingresado : "
                        + evento.getTurnoMinutaId(),
                        null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.CONSULTA_MINUTA_OK);
        return evRespuesta;
    } //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de actualizar una <code>Minuta</code>
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información de la <code>Minuta</code> a ser consultada.
     *
     * @return <code>EvnRespAdministracionHermod</code> con la
     * <code>Minuta</code>
     *
     * @throws EventoException
     */
    private EvnRespAdministracionHermod actualizarMinuta(EvnAdministracionHermod evento)
            throws EventoException {

        Minuta datos = null;
        try {

            datos = hermod.modificarMinuta(evento.getMinuta(), false, null);

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.EDITA_MINUTA_OK);
        return evRespuesta;
    } //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    /**
     * Este método se encarga de adicionar una nueva
     * <code>CirculoTipoPago</code> en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>CirculoTipoPago</code> a ser creado.
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de pago relacionados con el círculo.
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaCirculoTipoPago(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            Circulo circulo = evento.getCirculoTipoPago().getCirculo();
            CirculoPk cid = new CirculoPk();
            cid.idCirculo = circulo.getIdCirculo();
            hermod.addCirculoTipoPago(evento.getCirculoTipoPago());
            datos = hermod.getCirculoTiposPago(cid);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new TipoDePagoPorCirculoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new TipoDePagoPorCirculoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_CIRCULO_TIPO_PAGO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>CirculoTipoPago</code> de la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>CirculoTipoPago</code> a ser eliminado.
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las tipos de pago del círculo
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaCirculoTipoPago(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            Circulo circulo = evento.getCirculoTipoPago().getCirculo();
            CirculoPk cid = new CirculoPk();
            cid.idCirculo = circulo.getIdCirculo();
            hermod.removeCirculoTipoPago(evento.getCirculoTipoPago());
            datos = hermod.getCirculoTiposPago(cid);
        } catch (HermodException e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Pago"
                    + "para el Círculo", e);
        } catch (Throwable e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de PAgo"
                    + "para el Círculo", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_CIRCULO_TIPO_PAGO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de seleccionar la lista de Tipos de pago asociados
     * a un <code>Circulo</code>.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>Categoria</code> a ser creado.
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de pago del círculo consultado
     * @throws EventoException
     */
    private EvnRespAdministracionHermod seleccionaCirculoTipoPago(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            Circulo circulo = evento.getCirculo();
            CirculoPk cid = new CirculoPk();
            cid.idCirculo = circulo.getIdCirculo();
            datos = hermod.getCirculoTiposPago(cid);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.SELECCIONA_CIRCULO_TIPO_PAGO_OK);
        return evRespuesta;
    }

    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar una nueva
     * <code>CirculoTipoPago</code> en la base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>CirculoTipoPago</code> a ser creado.
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de pago relacionados con el círculo.
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaFirmaRegistrador(EvnAdministracionHermod evento)
            throws EventoException {

        Circulo datos = null;
        try {
            FirmaRegistrador dato = evento.getFirmaRegistrador();
            Circulo circulo = dato.getCirculo();
            CirculoPk cid = new CirculoPk();
            cid.idCirculo = circulo.getIdCirculo();
            if (forseti.addFirmaRegistradorToCirculo(dato, cid)) {
                //consulta para obtener la lista actualizada de firmas
                datos = forseti.getCirculo(cid);
            } else {
                throw new ValidacionFirmaRegistradorException("No se pudo adicionar la firma del Registrador.", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new ValidacionFirmaRegistradorException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new ValidacionFirmaRegistradorException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_FIRMA_REGISTRADOR_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>CirculoTipoPago</code> de la
     * base de datos.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del <code>CirculoTipoPago</code> a ser eliminado.
     * @param estado Firma del registrador activo (true), inactivo (false)
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de las tipos de pago del círculo
     * @throws EventoException
     */
    private EvnRespAdministracionHermod activaFirmaRegistrador(
            EvnAdministracionHermod evento,
            int estado)
            throws EventoException {

        Circulo datos = null;
        try {
            FirmaRegistrador dato = evento.getFirmaRegistrador();
            Circulo circulo = dato.getCirculo();
            CirculoPk cid = new CirculoPk();
            cid.idCirculo = circulo.getIdCirculo();
            FirmaRegistradorPk fid = new FirmaRegistradorPk();
            fid.idFirmaRegistrador = dato.getIdFirmaRegistrador();
            //fid.idCirculo = dato.getIdCirculo();
            if (forseti.setActivoFirmaRegistrador(fid, estado)) {
                //consulta para obtener la lista actualizada de firmas
                datos = forseti.getCirculo(cid);
            } else {
                throw new EventoException(
                        "No se pudo actualizar el estado de la firma del Registrador.",
                        null);
            }
        } catch (ForsetiException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new ValidacionFirmaRegistradorException(e.getMessage(), e);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ACTIVA_FIRMA_REGISTRADOR_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de seleccionar la lista de Tipos de pago asociados
     * a un <code>Circulo</code>.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>Categoria</code> a ser creado.
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de pago del círculo consultado
     * @throws EventoException
     */
    private EvnRespAdministracionHermod seleccionaCirculoFirmaRegistrador(EvnAdministracionHermod evento)
            throws EventoException {

        Circulo datos = null;
        try {
            Circulo circulo = evento.getCirculo();
            CirculoPk cid = new CirculoPk();
            cid.idCirculo = circulo.getIdCirculo();
            //circulo actualizado
            datos = forseti.getCirculo(cid);
            //	datos = circActualizado.getFirmas();
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class,
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo.",
                    e);
            throw new EventoException(
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo.",
                    e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class,
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo. ",
                    e);
            throw new EventoException(
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo.",
                    e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.SELECCIONA_CIRCULO_FIRMA_REGISTRADOR_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de seleccionar la lista de firmas registrador de
     * un <code>Circulo</code>.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>Categoria</code> a ser creado.
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de pago del círculo consultado
     * @throws EventoException
     */
    private EvnRespAdministracionHermod buscarFirmasRegistradorPorCirculo(EvnAdministracionHermod evento)
            throws EventoException {

        File firmas[];
        List firmasCirculo = new ArrayList();
        try {
            Circulo circulo = evento.getCirculo();
            CirculoPk cid = new CirculoPk();
            cid.idCirculo = circulo.getIdCirculo();

            String ruta = hermod.getPathFirmasRegistradoresAAsociar();
            ruta += circulo.getIdCirculo() + File.separator;

            //obtener la lista de firmas tipo File
            File f = new File(ruta);
            firmas = f.listFiles();

            Log.getInstance().info(ANAdministracionHermod.class, "ruta = \" " + ruta + "\"");

            if (firmas != null) {
                for (int i = 0; firmas.length > i; i++) {
                    firmasCirculo.add(firmas[i]);
                }
            } else {
                throw new Exception("no se encontro el directorio con la ruta : " + ruta + " .");
            }

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class,
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo.",
                    e);
            throw new EventoException(
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo.",
                    e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class,
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo. ",
                    e);
            throw new EventoException(
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo.",
                    e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        firmasCirculo,
                        EvnRespAdministracionHermod.BUSCAR_FIRMA_REGISTRADOR_CIRCULO);
        return evRespuesta;
    }

    /**
     * Este método se encarga de seleccionar de copiar la firma de registrador
     * al directorio especifico de un <code>Circulo</code>.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code> con la
     * información del nuevo <code>Categoria</code> a ser creado.
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los tipos de pago del círculo consultado
     * @throws EventoException
     */
    private EvnRespAdministracionHermod guardarFirmaRegistrador(EvnAdministracionHermod evento)
            throws EventoException {

        File firma = evento.getFileFirmaRegistrador();

        try {

            String ruta = hermod.getPathFirmasRegistradores();
            ruta += firma.getName();
            FileInputStream fis = new FileInputStream(firma);

            File dest = new File(ruta);

            //Mirar si ya existe un archivo con ese nombre;	
            if (dest.exists()) {
                //si ya existe agregarle al nombre un consecutivo hasta que no exista.
                for (int i = 0; dest.exists(); i++) {
                    dest = new File(ruta + "_" + i);
                }
            }

            FileOutputStream fos = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int bytes_read = 0;

            while ((bytes_read = fis.read(buffer, 0, buffer.length)) > 0) {
                fos.write(buffer, 0, bytes_read);
            }

            //Fin de la copia de archivo
            fos.close();
            fis.close();

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class,
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo.",
                    e);
            throw new EventoException(
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo.",
                    e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class,
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo. ",
                    e);
            throw new EventoException(
                    "No se pudo Consultar la lista actualizada de Firmas de Registrador para el círculo.",
                    e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        null,
                        EvnRespAdministracionHermod.GUARDAR_FIRMA_REGISTRADOR);
        return evRespuesta;
    }

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
    /**
     * Este método se encarga de consultar la lista de Abogados pertenecientes a
     * un círculo determinado.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista de los
     * usuarios que cumplen la condición buscada de <code>Circulo</code> y
     * <code>Rol</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultarAbogadosPorCirculo(EvnAdministracionHermod evento)
            throws EventoException {

        List usuariosConSubtipo = null;
        try {
            List datos = null;
            Circulo circulo = evento.getCirculo();
            Rol rol = new Rol();
            rol.setRolId(CRoles.ABOGADOS);
            datos = fenrir.darUsuariosRol(rol);
            usuariosConSubtipo = hermod.getSubtiposAtencionByUsuarios(datos, circulo);
            //datos = fenrir.consultarUsuariosPorCirculoRol(circulo, rol);
        } catch (FenrirException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        usuariosConSubtipo,
                        EvnRespAdministracionHermod.CONSULTA_ABOGADOS_POR_CIRCULO_ROL_OK);
        return evRespuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta modificarTurnoDevolucion(EvnAdministracionHermod evento) throws EventoException {

        String circulo = null;
        String proceso = null;
        String year = null;
        String idTurnoEvento = null;
        Turno turnoAnterior = null;
        Turno turnoDevolucion = null;

        try {

            //Recuperar el turno de Devolucion.
            String idTurno = evento.getIdentificadorTurnoDevolucion();

            //Validar integridad del turno.
            StringTokenizer st = new StringTokenizer(idTurno, "-");
            year = st.nextToken();
            if (st.hasMoreTokens()) {
                circulo = st.nextToken();
                if (st.hasMoreTokens()) {
                    proceso = st.nextToken();
                }
                if (st.hasMoreTokens()) {
                    idTurnoEvento = st.nextToken();
                }
            }

            //1. Validar Proceso Devolucion
            if (proceso == null) {
                throw new EventoException("El proceso del turno ingresado es inválido");
            }

            if (!proceso.equals(CProceso.PROCESO_DEVOLUCIONES)) {
                throw new EventoException("El proceso del turno no corresponde a devoluciones");
            }

            //2. Validar Círculo del Usuario.
            if (circulo == null) {
                throw new EventoException("El círculo del turno ingresado es inválido");
            }

            Circulo circuloSesion = evento.getCirculo();
            if (circuloSesion == null) {
                throw new EventoException("No se encontró usuario que va a realizar la modificación");
            }

            String idCirculo = circuloSesion.getIdCirculo();
            if (!idCirculo.equals(circulo)) {
                throw new EventoException("El círculo del usuario, no corresponde con el círculo del turno ingresado");
            }

            //3. Validar identificador del turno
            if (idTurnoEvento == null) {
                throw new EventoException("El número identificador del turno ingresado es inválido");
            }

            TurnoPk idTurnoDevolucion = new TurnoPk();
            idTurnoDevolucion.anio = year;
            idTurnoDevolucion.idCirculo = circulo;
            Long longProceso = new Long(proceso);
            idTurnoDevolucion.idProceso = longProceso.longValue();
            idTurnoDevolucion.idTurno = idTurnoEvento;

            turnoDevolucion = hermod.getTurno(idTurnoDevolucion);

            if (turnoDevolucion == null) {
                throw new EventoException("No fue posible obtener información del turno: " + idTurno);
            }

            //Obtener información del turno de certificados o de registro sobre el cual se solicita la 
            //devolución.
            Solicitud sol = turnoDevolucion.getSolicitud();
            turnoAnterior = sol.getTurnoAnterior();

            if (turnoAnterior == null) {
                throw new EventoException("No fue posible obtener información del turno asociado al turno:  " + idTurno);
            }

        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(null, EvnAdministracionHermod.SELECCIONAR_TURNO_MODIFICAR_DEVOLUCION);
        List listaTurnos = new ArrayList();
        listaTurnos.add(turnoAnterior);
        listaTurnos.add(turnoDevolucion);
        respuesta.setTurnos(listaTurnos);

        return respuesta;

    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta modificarValorTurnoDevolucion(EvnAdministracionHermod evento) throws EventoException {

        //Recuperar valores de la nueva liquidación.
        double derechos = evento.getDevolucionDerechos();
        double impuestos = evento.getDevolucionImpuestos();
        double certificados = evento.getDevolucionCertificados();
        String turnoDevolucionString = evento.getIdentificadorTurnoDevolucion();
        double absValorDevolver = 0d;
        LiquidacionTurnoCertificado liqCertificados = new LiquidacionTurnoCertificado();
        LiquidacionTurnoRegistro liqRegistro = new LiquidacionTurnoRegistro();
        TurnoPk idTurnoAnterior = new TurnoPk();

        try {

            Turno turnoDevolucion = null;
            turnoDevolucion = hermod.getTurnobyWF(turnoDevolucionString);

            if (turnoDevolucion == null) {
                throw new EventoException("El turno sobre el que se intenta modificar la devolución es nulo");
            }

            TurnoPk idTurnoDevolucion = new TurnoPk();
            idTurnoDevolucion.anio = turnoDevolucion.getAnio();
            idTurnoDevolucion.idCirculo = turnoDevolucion.getIdCirculo();
            idTurnoDevolucion.idTurno = turnoDevolucion.getIdTurno();
            idTurnoDevolucion.idProceso = turnoDevolucion.getIdProceso();

            //	SE NECESITA VALIDAR QUE NO SE QUIERA INGRESAR UN VALOR SUPERIOR AL QUE EL CIUDADANO HA PAGADO.
            Turno turnoCompleto = hermod.getTurno(idTurnoDevolucion);

            if (turnoCompleto == null) {
                throw new EventoException("El turno sobre el que se intenta modificar la devolución es nulo");
            }

            Solicitud solicitud = turnoCompleto.getSolicitud();
            Turno turnoAnterior = solicitud.getTurnoAnterior();
            if (turnoAnterior != null) {

                idTurnoAnterior.idCirculo = turnoAnterior.getIdCirculo();
                idTurnoAnterior.idProceso = turnoAnterior.getIdProceso();
                idTurnoAnterior.idTurno = turnoAnterior.getIdTurno();
                idTurnoAnterior.anio = turnoAnterior.getAnio();

                Solicitud solicitudAnterior = turnoAnterior.getSolicitud();
                List liquidaciones = solicitudAnterior.getLiquidaciones();
                double totalPagado = 0;

                if (liquidaciones != null && liquidaciones.size() > 0) {
                    Iterator itLiquidaciones = liquidaciones.iterator();
                    while (itLiquidaciones.hasNext()) {
                        Liquidacion liq = (Liquidacion) itLiquidaciones.next();
                        if (liq.getPago() != null) {
                            totalPagado = totalPagado + liq.getValor();
                        }
                    }
                } else {
                    throw new EventoException("No se han podido encontrar las liquidaciones del turno anterior");
                }

                //Turno de Certificados
                if (certificados > 0) {

                    liqCertificados.setValor(certificados * -1);
                    absValorDevolver = Math.abs(liqCertificados.getValor());

                } //Turno de Registro
                else if (derechos > 0 || impuestos > 0) {

                    liqRegistro.setValorDerechos(derechos * -1);
                    liqRegistro.setValorImpuestos(impuestos * -1);
                    liqRegistro.setValor(derechos + impuestos);
                    absValorDevolver = Math.abs(liqRegistro.getValor());
                }

                if (absValorDevolver > totalPagado) {
                    throw new EventoException("No se puede devolver un valor superior al pagado.");
                }

            } else {
                throw new EventoException("No se pudo obtener información del turno asociado a la devolución");
            }

            //Realizar la modificación del valor de la liquidación.
            if (certificados > 0) {

                liqCertificados.setFecha(new Date());
                liqCertificados.setUsuario(evento.getUsuarioNeg());
                hermod.updateUltimaLiquidacion(idTurnoAnterior, liqCertificados);
            } else if (derechos > 0 || impuestos > 0) {

                liqRegistro.setFecha(new Date());
                liqRegistro.setUsuario(evento.getUsuarioNeg());
                hermod.updateUltimaLiquidacion(idTurnoAnterior, liqRegistro);
            }

        } catch (Throwable e) {
            throw new EventoException("Error modificando valor de la devolución: ", e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(null, AWAdministracionHermod.TERMINA);

        return respuesta;

    }

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
    /**
     * Este método se encarga de Actualizar los subtipos de atención en una
     * lista de usuarios que llega en el evento determinado.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de los abogados
     * @throws EventoException
     */
    private EvnRespAdministracionHermod actualizarSubtiposAtencionDeAbogados(EvnAdministracionHermod evento)
            throws EventoException {

        List usuariosConSubtipo = null;
        try {
            List usuarios = evento.getUsuariosNegocio();
            for (Iterator iter = usuarios.iterator(); iter.hasNext();) {

                List datos = null;
                Circulo circulo = evento.getCirculo();
                Rol rol = new Rol();
                rol.setRolId(CRoles.ABOGADOS);
                datos = fenrir.darUsuariosRol(rol);

                Usuario usuario = (Usuario) iter.next();
                hermod.updateSubtiposAtencionEnUsuario(usuario, datos, circulo);
            }

            List datos = null;
            Circulo circulo = evento.getCirculo();
            Rol rol = new Rol();
            rol.setRolId(CRoles.ABOGADOS);
            datos = fenrir.darUsuariosRol(rol);
            usuariosConSubtipo = hermod.getSubtiposAtencionByUsuarios(datos, circulo);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        usuariosConSubtipo,
                        EvnRespAdministracionHermod.ACTUALIZA_SUBTIPOS_ATENCION_USUARIO_OK);
        return evRespuesta;
    }

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar una <code>ValidacionNota</code> al
     * sistema que llega en el evento determinado.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de validaciones nota.
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaValidacionNota(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            hermod.addValidacionNota(evento.getValidacionNota());
            datos = hermod.getValidacionNotas();
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_VALIDACION_NOTA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar una <code>ValidacionNota</code> al
     * sistema que llega en el evento determinado.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de validaciones nota.
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaValidacionNota(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            hermod.eliminarValidacionNota(evento.getValidacionNota());
            datos = hermod.getValidacionNotas();
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_VALIDACION_NOTA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de consultar la lista de Fases relacionadas con un
     * proceso que llega en el evento determinado.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de validaciones nota.
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaFasesProceso(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            datos = hermod.getFasesDeProceso(evento.getProceso());
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.CONSULTA_FASE_PROCESO_OK);
        return evRespuesta;
    }

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar una <code>CausalRestitucion</code> al
     * sistema que llega en el evento determinado.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de Causales Restitucion.
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaCausalRestitucion(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            hermod.addCausalRestitucion(evento.getCausalRestitucion());
            datos = hermod.getCausalesRestitucion();
        } catch (HermodException e) {
            throw new EventoException("Error de integridad agregando el causal de restitución", e);
        } catch (Throwable e) {
            throw new EventoException("Error de integridad agregando el causal de restitución", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_CAUSAL_RESTITUCION_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de modificar un <code>CausalRestitucion</code> en
     * la configuración del sistema.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de Causales Restitucion.
     * @throws EventoException
     */
    private EvnRespAdministracionHermod editarDetallesCausalRestitucion(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            hermod.editCausalRestitucion(evento.getCausalRestitucion());
            datos = hermod.getCausalesRestitucion();
        } catch (HermodException e) {
            throw new EventoException("Error de integridad modificando el causal de restitución", e);
        } catch (Throwable e) {
            throw new EventoException("Error de integridad modificando el causal de restitución", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnAdministracionHermod.EDITAR_DETALLES_CAUSAL_RESTITUCION);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>CausalRestitucion</code> al
     * sistema que llega en el evento determinado.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de Causales Restitucion.
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaCausalRestitucion(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            hermod.eliminarCausalRestitucion(evento.getCausalRestitucion());
            datos = hermod.getCausalesRestitucion();
        } catch (HermodException e) {
            throw new EventoException("Debido_a restricciones de integridad de datos no se pudo eliminar el causal de restituciój de reparto",
                    e);

        } catch (Throwable e) {
            throw new EventoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar el causal de restituciój de reparto",
                    e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_CAUSAL_RESTITUCION_OK);
        return evRespuesta;
    }

    //	/////////////////////////////////////////////
    ///////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar una
     * <code>TipoCertificadoValidacion</code> al sistema que llega en el evento
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de <code>TipoCertificadoValidacion</code> para el
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaTipoCertificadoValidacion(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            TipoCertificado tipoCert = evento.getTipoCertificadoValidacion().getTipoCertificado();
            hermod.addTipoCertificadoValidacion(evento.getTipoCertificadoValidacion());
            datos = hermod.getTiposCertificadosValidacionByTipoCertificado(tipoCert);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ADICIONA_VALIDACION_TIPO_CERTIFICADO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>CausalRestitucion</code> al
     * sistema que llega en el evento determinado.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de Causales Restitucion.
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaTipoCertificadoValidacion(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            TipoCertificado tipoCert = evento.getTipoCertificadoValidacion().getTipoCertificado();
            hermod.eliminarTipoCertificadoValidacion(evento.getTipoCertificadoValidacion());
            datos = hermod.getTiposCertificadosValidacionByTipoCertificado(tipoCert);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.ELIMINA_VALIDACION_TIPO_CERTIFICADO_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>CausalRestitucion</code> al
     * sistema que llega en el evento determinado.
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de Causales Restitucion.
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaValidacionesDeTipoCertificado(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            TipoCertificado tipoCert = evento.getTipoCertificado();
            datos = hermod.getTiposCertificadosValidacionByTipoCertificado(tipoCert);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.CONSULTA_VALIDACION_TIPO_CERTIFICADO_OK);
        return evRespuesta;
    }

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar un <code>TipoNota</code> al sistema
     * que llega en el evento
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de objetos <code>TipoNota</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaTipoNota(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        boolean devolutiva = false;
        try {
            Proceso proc = evento.getTipoNota().getProceso();
            ProcesoPk poid = new ProcesoPk();
            poid.idProceso = proc.getIdProceso();
            hermod.addTipoNota(evento.getTipoNota(), fenrir.getUsuario(evento.getUsuario()));
            if (evento.getTipoNota().isDevolutiva()) {
                devolutiva = true;
            } else {
                devolutiva = false;
            }
            datos = hermod.getTiposNotaProcesoByTpnaDevolutiva(poid, devolutiva);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_TIPO_NOTA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de adicionar un <code>TipoNota</code> al sistema
     * que llega en el evento
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de objetos <code>TipoNota</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod modificaTipoNota(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        boolean devolutiva = false;
        try {
            hermod.updateTipoNota(evento.getTipoNota(), fenrir.getUsuario(evento.getUsuario()));

            Proceso proc = evento.getTipoNota().getProceso();
            ProcesoPk poid = new ProcesoPk();
            poid.idProceso = proc.getIdProceso();

            if (evento.getTipoNota().isDevolutiva()) {
                devolutiva = true;
            } else {
                devolutiva = false;
            }
            datos = hermod.getTiposNotaProcesoByTpnaDevolutiva(poid, devolutiva);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_TIPO_NOTA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>TipoNota</code> al sistema
     * que llega en el evento
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de objetos <code>TipoNota</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaTipoNota(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        boolean devolutiva = false;
        try {
            Proceso proc = evento.getTipoNota().getProceso();
            if (evento.getTipoNota().isDevolutiva()) {
                devolutiva = true;
            }
            ProcesoPk poid = new ProcesoPk();
            poid.idProceso = proc.getIdProceso();
            hermod.eliminarTipoNota(evento.getTipoNota(), fenrir.getUsuario(evento.getUsuario()));
            datos = hermod.getTiposNotaProcesoByTpnaDevolutiva(poid, devolutiva);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ELIMINA_TIPO_NOTA_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de consultar los objetos <code>TipoNota</code> que
     * estén asociados al proceso pasado como parámetro
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de objetos <code>TipoNota</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaTipoNotaInfPorProceso(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        List fases = null;
        try {
            Proceso proc = evento.getProceso();
            ProcesoPk poid = new ProcesoPk();
            poid.idProceso = proc.getIdProceso();
            datos = hermod.getTiposNotaProcesoByTpnaDevolutiva(poid, false);
            fases = hermod.getFasesDeProceso(proc);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.CONSULTA_TIPO_NOTA_INF_POR_PROCESO_OK);
        evRespuesta.setFases(fases);
        return evRespuesta;
    }

    /**
     * Este método se encarga de consultar los objetos <code>TipoNota</code> que
     * estén asociados al proceso pasado como parámetro
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de objetos <code>TipoNota</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaTipoNotaDevPorProceso(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        List fases = null;
        try {
            Proceso proc = evento.getProceso();
            ProcesoPk poid = new ProcesoPk();
            poid.idProceso = proc.getIdProceso();
            datos = hermod.getTiposNotaProcesoByTpnaDevolutiva(poid, true);
            fases = hermod.getFasesDeProceso(proc);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.CONSULTA_TIPO_NOTA_DEV_POR_PROCESO_OK);
        evRespuesta.setFases(fases);
        return evRespuesta;
    }

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
    /**
     * Este método se encarga de adicionar un <code>CheckItem</code> al sistema
     * que llega en el evento
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de objetos <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod adicionaCheckItem(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            SubtipoSolicitud sub = evento.getSubtipoSolicitud();
            hermod.addCheckItem(evento.getCheckItem());
            datos = hermod.getCheckItemsBySubtipoSolicitud(sub);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_CHECK_ITEM_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar un <code>CheckItem</code> al sistema
     * que llega en el evento
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de objetos <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminaCheckItem(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            SubtipoSolicitud sub = evento.getSubtipoSolicitud();
            hermod.eliminarCheckItem(evento.getCheckItem());
            datos = hermod.getCheckItemsBySubtipoSolicitud(sub);
        } catch (HermodException e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Item de Chequeo", e);
        } catch (Throwable e) {

            throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Item de Chequeo", e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ELIMINA_CHECK_ITEM_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de editar un <code>CheckItem</code> al sistema que
     * llega en el evento
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de objetos <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod editaCheckItem(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            SubtipoSolicitud sub = evento.getSubtipoSolicitud();
            hermod.updateCheckItem(evento.getCheckItem());
            datos = hermod.getCheckItemsBySubtipoSolicitud(sub);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(datos, EvnRespAdministracionHermod.ADICIONA_CHECK_ITEM_OK);
        return evRespuesta;
    }

    /**
     * Este método se consultar la lista de objetos <code>CheckItem</code>
     * filtrada por el <code>SubtipoSolicitud</code> con el que estén
     * relacionados que llega en el evento
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de objetos <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaCheckItemPorSubtipoSolicitud(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            SubtipoSolicitud sub = evento.getSubtipoSolicitud();
            datos = hermod.getCheckItemsBySubtipoSolicitud(sub);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.CONSULTA_CHECK_ITEM_POR_SUBTIPO_SOLICITUD_OK);
        return evRespuesta;
    }

    ///////////////////////////////////////////////////
    /**
     * Este método se consultar la lista de objetos <code>CheckItem</code>
     * filtrada por el <code>SubtipoSolicitud</code> con el que estén
     * relacionados que llega en el evento
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de objetos <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaTurnosPorFaseYFecha(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        try {
            Fase fase = evento.getFase();
            Proceso proceso = evento.getProceso();
            Date fecha = evento.getFecha();
            Circulo circulo = evento.getCirculo();
            //datos = hermod.getTurnosByFechaYCirculo(proceso, fase, fecha, circulo);
            datos = hermod.getTurnosByFechaAndCirculoMinusMasivos(proceso, fase, fecha, circulo);
            /*Iterator it = datos.iterator();
            Turno turno = null;
            List solicitudesPadres = null;
            while(it.hasNext()){
                turno = (Turno)it.next();
                solicitudesPadres = turno.getSolicitud().getSolicitudesPadres();
                if(solicitudesPadres != null && !solicitudesPadres.isEmpty()){
                    datos.remove(turno);
                }
                
            }*/
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.CONSULTA_TURNOS_CER_ENTREGAR_OK);
        return evRespuesta;
    }

///////////////////////////////////////////////////
    /**
     * Este método se consultar la lista de objetos <code>Turno</code> a
     * entregar de consultas que llega en el evento
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> con la lista actualizada
     * de objetos <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaTurnosAEntregarConsultas(EvnAdministracionHermod evento)
            throws EventoException {

        List datos = null;
        List datos2 = null;
        try {
            Fase fase = new Fase();
            fase.setID(CFase.CON_ENTREGAR_SIMPLE);

            Proceso proceso = evento.getProceso();
            Date fecha = evento.getFecha();
            Circulo circulo = evento.getCirculo();
            datos = hermod.getTurnosByFechaYCirculo(proceso, fase, fecha, circulo);

            fase.setID(CFase.CON_ENTREGAR_COMPLEJA);
            datos2 = hermod.getTurnosByFechaYCirculo(proceso, fase, fecha, circulo);

            //unir ambas listas
            datos.addAll(datos2);

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        datos,
                        EvnRespAdministracionHermod.CONSULTA_TURNOS_CON_ENTREGAR_OK);
        return evRespuesta;
    }

    //	/////////////////////////////////////////////////
    /**
     * Este método se encarga de realizar la entrega masiva de una serie de
     * turnos
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod entregaMasivaDeCertificados(EvnAdministracionHermod evento)
            throws EventoException {

        //boolean ok = false;
        try {
            Fase fase = evento.getFase();
            List turnos = evento.getTurnos();

            Hashtable parametros = new Hashtable();
            parametros.put("RESULT", "OK");
            parametros.put("USUARIO_SIR", evento.getUsuario().getUsuarioId());

            /*ok = */
            hermod.avanzarMasivoTurnosNuevo(turnos, fase, parametros, evento.getUsuarioSIR());

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        null,
                        EvnRespAdministracionHermod.ENTREGA_MASIVA_CERTIFICADOS_OK);
        return evRespuesta;
    }

//	/////////////////////////////////////////////////
    /**
     * Este método se encarga de realizar la entrega masiva de una serie de
     * turnos
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod entregaMasivaDeConsultas(EvnAdministracionHermod evento)
            throws EventoException {

        List turnosSimples = new Vector();
        List turnosComplejos = new Vector();
        //boolean ok = false;
        try {
            Fase fase = new Fase();
            fase.setID(CFase.CON_ENTREGAR_SIMPLE);
            List turnos = evento.getTurnos();

            Hashtable parametros = new Hashtable();
            parametros.put("RESULT", "CONFIRMAR");
            parametros.put("USUARIO_SIR", evento.getUsuario().getUsuarioId());

            for (Iterator i = turnos.iterator(); i.hasNext();) {
                //ver si son complejos o son simples
                Turno temp = (Turno) i.next();
                temp = hermod.getTurnobyWF(temp.getIdWorkflow());
                if (temp.getIdFase().equals(CFase.CON_ENTREGAR_SIMPLE)) {
                    turnosSimples.add(temp);
                } else if (temp.getIdFase().equals(CFase.CON_ENTREGAR_COMPLEJA)) {
                    turnosComplejos.add(temp);
                }
            }

            if (turnosSimples.size() != 0) {
                hermod.avanzarMasivoTurnosNuevo(turnosSimples, fase, parametros, evento.getUsuarioSIR());
            }

            //entregar las complejas
            fase.setID(CFase.CON_ENTREGAR_COMPLEJA);
            if (turnosComplejos.size() != 0) {
                hermod.avanzarMasivoTurnosNuevo(turnosComplejos, fase, parametros, evento.getUsuarioSIR());
            }

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        null,
                        EvnRespAdministracionHermod.ENTREGA_MASIVA_CONSULTAS_OK);
        return evRespuesta;
    }

//	/////////////////////////////////////////////////
    /**
     * Este método se encarga de modificar la probabilidad de revision de
     * calificacion
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod modificarProbRevisionCalificacion(EvnAdministracionHermod evento)
            throws EventoException {

        String valorProb = evento.getValorProbRevCalificion();
        try {

            hermod.updateProbabilidadRevisionCalificacion(valorProb);

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        valorProb,
                        EvnRespAdministracionHermod.MODIFICAR_PROB_REV_CALIFICACION_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de consultar la probabilidad de revision de
     * calificacion
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultarProbRevisionCalificacion(EvnAdministracionHermod evento)
            throws EventoException {

        String valorProb = "50";
        try {
            /*
            // Se obtienen los roles del usuario que está intentando hacer las
            // modificaciones
            Usuario usuario = evento.getUsuarioSIR();

            List roles = fenrir.darRolUsuario(usuario.getIdUsuario());
            boolean registradorNacional = false;

            for(Iterator iteradorRoles = roles.iterator(); iteradorRoles.hasNext();) {
                Rol rol = (Rol)iteradorRoles.next();
                if(rol.getRolId().equals(CRoles.ADMINISTRADOR_NACIONAL)) {
                    registradorNacional = true;
                    break;
                }
            }

            List circulos = null;

            // Primero se obtienen todos los círculos a los que el usuario tiene
            // acceso para modificar.
            if(registradorNacional)
                circulos = forseti.getCirculos();

            Map tabla = new Hashtable();

            tabla.put("LISTA_CIRCULOS", circulos);
            tabla.put("PROBABILIDAD_REV_CALIFICACION", valorProb
             */

            // La probabilidad ya no es genérica para todos los círculos, sino
            // para cada círculo registral existe una probabilidad de revisión
            // de calificación.
            valorProb = hermod.getProbabilidadRevisionCalificacion();

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        valorProb,
                        EvnRespAdministracionHermod.CONSULTAR_PROB_REV_CALIFICACION_OK);
        return evRespuesta;
    }

    /**
     * Este método se encarga de consultar las solicitudes no pagadas dado un
     * rango de fechas
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultarSolicitudesNoPagadas(EvnAdministracionHermod evento)
            throws EventoException {

        Date fechaDesde = evento.getValorFechaDesde();
        Date fechaHasta = evento.getValorFechaHasta();
        long numeroPagina = evento.getNumeroPagina();
        long numeroResultadosPorPagina = evento.getNumeroResultadosPorPagina();
        String filtro = evento.getFiltro();

        long indiceInicial = (numeroPagina - 1) * numeroResultadosPorPagina;

        Circulo circulo = evento.getCirculo();

        List solicitudes = null;
        long totalResultados = 0;
        try {
            Map mapa = hermod.getSolicitudesSinTurno(circulo, fechaDesde,
                    fechaHasta, filtro, (int) indiceInicial,
                    (int) numeroResultadosPorPagina);
            solicitudes = (List) mapa.get("resultados");
            totalResultados = ((Long) mapa.get("numeroResultados")).longValue();
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        solicitudes,
                        EvnRespAdministracionHermod.CONSULTA_SOLICITUDES_NO_PAGADAS_OK);
        evRespuesta.setTotalResultados(totalResultados);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar las solicitudes no pagadas dado un
     * rango de fechas
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminarSolicitudesNoPagadas(EvnAdministracionHermod evento)
            throws EventoException {

        List solicitudes = evento.getSolicitudes();
        Date fechaDesde = evento.getValorFechaDesde();
        Date fechaHasta = evento.getValorFechaHasta();
        String filtro = evento.getFiltro();
        long numeroResultadosPorPagina = evento.getNumeroResultadosPorPagina();

        long numeroPagina = evento.getNumeroPagina();

        long indiceInicial = (numeroPagina - 1) * numeroResultadosPorPagina;

        Circulo circulo = evento.getCirculo();

        long totalResultados = 0;

        boolean excepcion = false;
        try {

            Iterator it = solicitudes.iterator();
            for (; it.hasNext();) {
                SolicitudRegistro sol = (SolicitudRegistro) it.next();
                try {
                    hermod.deleteSolicitud(sol);
                } catch (HermodException e1) {
                    excepcion = true;
                    Log.getInstance().error(ANAdministracionHermod.class, e1.getMessage(), e1);
                }
            }

            Map mapa = hermod.getSolicitudesSinTurno(circulo, fechaDesde, fechaHasta,
                    filtro, (int) indiceInicial,
                    (int) numeroResultadosPorPagina);
            solicitudes = (List) mapa.get("resultados");
            totalResultados = ((Long) mapa.get("numeroResultados")).longValue();

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        solicitudes,
                        EvnRespAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS_OK);
        evRespuesta.setTotalResultados(totalResultados);
        evRespuesta.setHayExcepcion(excepcion);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar las solicitudes no pagadas dado un
     * rango de fechas
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod eliminarTodasLasSolicitudesNoPagadas(EvnAdministracionHermod evento)
            throws EventoException {

        List solicitudes = evento.getSolicitudes();
        Date fechaDesde = evento.getValorFechaDesde();
        Date fechaHasta = evento.getValorFechaHasta();
        String filtro = evento.getFiltro();
        long numeroResultadosPorPagina = evento.getNumeroResultadosPorPagina();

        Circulo circulo = evento.getCirculo();

        long totalResultados = 0;

        boolean excepcion = false;
        try {

            boolean exito = hermod.removeSolicitudesSinTurno(circulo, fechaDesde, fechaHasta,
                    filtro, 0,
                    (int) numeroResultadosPorPagina);

            Map mapa = hermod.getSolicitudesSinTurno(circulo, fechaDesde, fechaHasta,
                    filtro, 0,
                    (int) numeroResultadosPorPagina);
            solicitudes = (List) mapa.get("resultados");
            totalResultados = ((Long) mapa.get("numeroResultados")).longValue();

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        solicitudes,
                        EvnRespAdministracionHermod.ELIMINAR_SOLICITUDES_NO_PAGADAS_OK);
        evRespuesta.setTotalResultados(totalResultados);
        evRespuesta.setHayExcepcion(excepcion);
        return evRespuesta;
    }

    /**
     * Este método se encarga de eliminar las solicitudes no pagadas dado un
     * rango de fechas
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod consultaTurnosCalificacion(EvnAdministracionHermod evento)
            throws EventoException {

        List turnos = new LinkedList();
        List notasInf = new LinkedList();
        Proceso proceso = new Proceso();
        proceso.setIdProceso(6);
        //settear proceso como registro id = 6
        ProcesoPk id = new ProcesoPk();
        id.idProceso = proceso.getIdProceso();
        //settear fase como "REG_DECIDIR_SOL_AJUSTE"
        String faseId = "REG_DECIDIR_SOL_AJUSTE";
        Usuario usuarioNeg = evento.getUsuarioNeg();
        Circulo circulo = evento.getCirculo();

        try {

            //llamar srevicio hermod para obtener lista de turnos
            turnos = hermod.getTurnosByUsuarioCalificador(usuarioNeg.getIdUsuario(), circulo);
            //llamar servicio hermod para obtener tipos notas infomativas.
            notasInf = hermod.getTiposNotaProceso(id, faseId);

            // Se deben ordenar los turnos correctamente
            try {
                Collections.sort(turnos, new IDidTurnoComparator());
            } catch (Exception e) {
                Log.getInstance().error(ANAdministracionHermod.class, "No se pudieron ordenar los Turnos: " + e.getMessage());
            }

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        turnos,
                        EvnRespAdministracionHermod.CONSULTA_TURNOS_CALIFICACION_OK);
        evRespuesta.setNotas(notasInf);
        return evRespuesta;
    }

    /**
     * Este método se encarga de solicitar la correccion de un turno para ser
     * nuevamente calificado
     *
     * @param evento de tipo <code>EvnAdministracionHermod</code>
     * @return <code>EvnRespAdministracionHermod</code> <code>CheckItem</code>
     * @throws EventoException
     */
    private EvnRespAdministracionHermod solicitarCorreccionCalificacion(EvnAdministracionHermod evento)
            throws EventoException {

        //obtener datos de los eventos
        String idTurno = evento.getTurnoCorreccionId();
        Nota notaInformativa = evento.getNotaInformativaCorreccion();
        //org.auriga.core.modelo.transferObjects.Usuario usuario= evento.getUsuario();
        Turno turno = null;

        //crear variables de idFase
        String revisarCalificacionFaseId = CFase.REG_REVISION_CALIFICACION;
        String mesaControlFaseId = CFase.REG_MESA_CONTROL;
        String firmaRegistradorFaseId = CFase.REG_FIRMAR;
        String faseId = "";
        Fase fase = null;

        Hashtable datos = new Hashtable();

        List turnos = new ArrayList();
        List notas = new ArrayList();

        try {
            //llamar servicio de hermod para obtener el turno dado el id
            turno = hermod.getTurnobyWF(idTurno);
            if (turno == null) {
                throw new EventoException("No se pudo Realizar la solicitud de correccion, turno nulo");
            }
            //setear el turno id
            TurnoPk tid = new TurnoPk();
            tid.anio = turno.getAnio();
            tid.idCirculo = turno.getIdCirculo();
            tid.idProceso = turno.getIdProceso();
            tid.idTurno = turno.getIdTurno();

            //llamar servicio de hermod para settear la nota informativa al turno respectivo
            hermod.addNotaToTurno(notaInformativa, tid);

            //llamar el servicio para obtener la fase dada el id
            faseId = turno.getIdFase();
            if (faseId != null) {
                fase = hermod.getFase(faseId);
            }

            if (fase == null) {
                throw new EventoException("No se pudo Realizar la solicitud de correccion, fase nula");
            }

            //settear el atributo de que hay ajuste
            hermod.updateAjusteInTurnoRegistro(turno, true);
            String USUARIO_INICIADOR = (null != evento.getUsuarioSIR()) ? ("" + evento.getUsuarioSIR().getUsername()) : ("");
            if ("".equals(USUARIO_INICIADOR) || null == USUARIO_INICIADOR) {
                throw new HermodException("El usuario no se ha registrado en la capa web." + this.getClass().getName());
            }

            datos.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);

            //llamar servicio de hermod para avanzar al turno y llevarlo a calificacion
            if (faseId.equals(revisarCalificacionFaseId)) {
                //inicializar tabla hashing
                datos.put("RESULT", CRespuesta.AJUSTAR_CALIFICACION);

                //avanzar de la fase revision calificacion
                hermod.avanzarTurnoNuevoNormal(turno, fase, datos, evento.getUsuarioSIR());

            } else if (faseId.equals(mesaControlFaseId)) {
                //inicializar tabla hashing
                datos.put("RESULT", CRespuesta.AJUSTAR_MESA_CONTROL);

                //avanzar de la fase mesa control
                hermod.avanzarTurnoNuevoNormal(turno, fase, datos, evento.getUsuarioSIR());
            } else if (faseId.equals(firmaRegistradorFaseId)) {
                //inicializar tabla hashing
                datos.put("RESULT", CRespuesta.AJUSTAR);

                //avanzar de la fase mesa control
                hermod.avanzarTurnoNuevoNormal(turno, fase, datos, evento.getUsuarioSIR());

            } else {
                throw new EventoException("No se pudo Realizar la solicitud de correccion, fases invalidas");
            }

            EvnRespAdministracionHermod evTemp = this.consultaTurnosCalificacion(evento);
            turnos = (List) evTemp.getPayload();
            notas = evTemp.getNotas();

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        if (turnos == null) {
            turnos = new ArrayList();
        }
        if (notas == null) {
            notas = new ArrayList();
        }

        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(
                        turnos,
                        EvnRespAdministracionHermod.SOLICITAR_CORRECCION_CALIFICACION_OK);
        evRespuesta.setNotas(notas);
        return evRespuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta adicionarNatJuridicaEntidad(EvnAdministracionHermod evento) throws EventoException {

        //SE VERIFICA QUE NO EXISTA LA NATURALEZA JURÍDICA DE REPARTO, Y DE LO CONTRARIO SE INGRESA.
        try {
            List naturalezasExistentes = hermod.getNaturalezasJuridicasEntidades();
            if (verificarNaturalezasExistentes(naturalezasExistentes, evento.getNatJuridicaEntidad())) {
                throw new AdministracionNaturalezaJuridicaRepartoException("La naturaleza jurídica, que desea ingresar ya se encuentra registrada.");
            }
            hermod.agregarNaturalezaJuridicaEntidadPublica(evento.getNatJuridicaEntidad());
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionNaturalezaJuridicaRepartoException(e.getMessage(), e);
        } catch (AdministracionNaturalezaJuridicaRepartoException anje) {
            Log.getInstance().error(ANAdministracionHermod.class, anje.getMessage());
            throw anje;
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionNaturalezaJuridicaRepartoException(e.getMessage(), e);
        }

        List natsJuridicasEntidad;
        try {
            natsJuridicasEntidad = hermod.getNaturalezasJuridicasEntidades();
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionNaturalezaJuridicaRepartoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(natsJuridicasEntidad, EvnRespAdministracionHermod.CAMBIO_NAT_JURIDICA_ENTIDAD);
        respuesta.setNatsJuridicasEntidad(natsJuridicasEntidad);
        return respuesta;
    }

    /**
     * Este método se encarga de verificar si la Naturaleza jurídica para
     * reparto que se intenta agregar existe o no en la base datos.
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code>.
     * @throws AccionWebException
     */
    private boolean verificarNaturalezasExistentes(List naturalezas, NaturalezaJuridicaEntidad nombre) {
        boolean existe = false;

        if (naturalezas == null) {
            naturalezas = new ArrayList();
        }

        Iterator itNaturalezas = naturalezas.iterator();
        while (itNaturalezas.hasNext()) {
            NaturalezaJuridicaEntidad nje = (NaturalezaJuridicaEntidad) itNaturalezas.next();
            if (nje.getNombre().equals(nombre.getNombre())) {
                existe = true;
            }
        }
        return existe;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta editarNatJuridicaEntidad(EvnAdministracionHermod evento) throws EventoException {
        //SE VERIFICA QUE NO EXISTA LA NATURALEZA JURÍDICA DE REPARTO, Y DE LO CONTRARIO SE INGRESA.
        try {
            List naturalezasExistentes = hermod.getNaturalezasJuridicasEntidades();
            if (verificarActualizacionNaturalezaJuridica(naturalezasExistentes, evento.getNatJuridicaEntidad())) {
                throw new AdministracionNaturalezaJuridicaRepartoException("La naturaleza jurídica, que desea ingresar ya se encuentra registrada.");
            }
            hermod.editarNaturalezaJuridicaEntidadPublica(evento.getNatJuridicaEntidad());

            List entidades = hermod.getEntidadesReparto();
            if (tieneEntidades(entidades, evento.getNatJuridicaEntidad()) && !evento.getNatJuridicaEntidad().isActivo()) {
                throw new AdministracionNaturalezaJuridicaRepartoException("No se puede desactivar la Naturaleza Jurídica "
                        + evento.getNatJuridicaEntidad().getIdNatJuridicaEntidad() + " porque tiene entidades públicas asociadas.");
            } else {
                hermod.actualizarEstadoNaturalezaJuridicaEntidadPublica(evento.getNatJuridicaEntidad());
            }

        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionNaturalezaJuridicaRepartoException(e.getMessage(), e);
        } catch (AdministracionNaturalezaJuridicaRepartoException anje) {
            Log.getInstance().error(ANAdministracionHermod.class, anje.getMessage());
            throw anje;
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionNaturalezaJuridicaRepartoException(e.getMessage(), e);
        }

        List natsJuridicasEntidad;
        try {
            natsJuridicasEntidad = hermod.getNaturalezasJuridicasEntidades();
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionNaturalezaJuridicaRepartoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionNaturalezaJuridicaRepartoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(natsJuridicasEntidad, EvnRespAdministracionHermod.CAMBIO_NAT_JURIDICA_ENTIDAD);
        respuesta.setNatsJuridicasEntidad(natsJuridicasEntidad);
        return respuesta;
    }

    /**
     * Este método se encarga de verificar si la Naturaleza jurídica para
     * reparto que se intenta agregar existe o no en la base datos.
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code>.
     * @throws AccionWebException
     */
    private boolean verificarActualizacionNaturalezaJuridica(List naturalezas, NaturalezaJuridicaEntidad nombre) {
        boolean existe = false;

        if (naturalezas == null) {
            naturalezas = new ArrayList();
        }

        Iterator itNaturalezas = naturalezas.iterator();
        while (itNaturalezas.hasNext()) {
            NaturalezaJuridicaEntidad nje = (NaturalezaJuridicaEntidad) itNaturalezas.next();
            if (!nje.getIdNatJuridicaEntidad().equals(nombre.getIdNatJuridicaEntidad())) {
                if (nje.getNombre().equals(nombre.getNombre())) {
                    existe = true;
                }
            }
        }
        return existe;
    }

    /**
     * Este método se encarga de verificar si la Naturaleza jurídica esta
     * asociada con alguna entidad pública.
     *
     * @param request
     * @return evento <code>EvnAdministracionForseti</code>.
     * @throws AccionWebException
     */
    private boolean tieneEntidades(List entidades, NaturalezaJuridicaEntidad nombre) {
        boolean tieneEntidadAsociada = false;

        if (entidades == null) {
            entidades = new ArrayList();
        }

        Iterator itEntidades = entidades.iterator();
        while (itEntidades.hasNext()) {
            EntidadPublica entidad = (EntidadPublica) itEntidades.next();
            if (entidad.getNaturalezaJuridica().getIdNatJuridicaEntidad().equals(nombre.getIdNatJuridicaEntidad())) {
                tieneEntidadAsociada = true;
                break;
            }
        }
        return tieneEntidadAsociada;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta eliminarNatJuridicaEntidad(EvnAdministracionHermod evento) throws EventoException {
        List natsJuridicasEntidad;
        try {
            NaturalezaJuridicaEntidad entidad = new NaturalezaJuridicaEntidad();
            entidad.setIdNatJuridicaEntidad(evento.getIdNatJuridicaEntidad());
            hermod.eliminarNaturalezaJuridicaEntidadPublica(entidad);
            natsJuridicasEntidad = hermod.getNaturalezasJuridicasEntidades();
        } catch (HermodException e) {
            throw new AdministracionNaturalezaJuridicaRepartoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar la Naturaleza Jurídica de la Entidad Pública",
                    e);
        } catch (Throwable e) {
            throw new AdministracionNaturalezaJuridicaRepartoException(
                    "Debido a restricciones de integridad de datos no se pudo eliminar la Naturaleza Jurídica de la Entidad Pública",
                    e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(natsJuridicasEntidad, EvnRespAdministracionHermod.CAMBIO_NAT_JURIDICA_ENTIDAD);
        respuesta.setNatsJuridicasEntidad(natsJuridicasEntidad);
        return respuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta adicionarEntidadPublica(EvnAdministracionHermod evento) throws EventoException {

        //SE VERIFICA SI EXISTE UNA ENTIDAD CON EL MISMO NOMBRE,
        //SI EXISTE SE INFORMA DE LO CONTRARIO SE INGRESA.
        try {
            hermod.agregarEntidadPublica(evento.getEntidadPublica());
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionEntidadesPublicasRepartoException("Debido a restricciones de integridad de datos no se pudo agregar la Entidad Pública", e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionEntidadesPublicasRepartoException("Debido a restricciones de integridad de datos no se pudo eliminar la Entidad Pública", e);
        }

        //SE CONSULTA EL LISTADO DE ENTIDADES PUBLICAS.
        List entidadesPublicas;
        try {
            entidadesPublicas = hermod.getEntidadesReparto();
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionEntidadesPublicasRepartoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionEntidadesPublicasRepartoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(entidadesPublicas, EvnRespAdministracionHermod.CAMBIO_ENTIDAD_PUBLICA);
        respuesta.setEntidadesPublicas(entidadesPublicas);
        return respuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta editarEntidadPublica(EvnAdministracionHermod evento) throws EventoException {

        //SE VERIFICA SI EXISTE UNA ENTIDAD CON EL MISMO NOMBRE,
        //SI EXISTE SE INFORMA DE LO CONTRARIO SE INGRESA.
        try {
            hermod.editarEntidadPublica(evento.getEntidadPublica());
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionEntidadesPublicasRepartoException("Debido a restricciones de integridad de datos no se pudo editar la Entidad Pública", e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionEntidadesPublicasRepartoException("Debido a restricciones de integridad de datos no se pudo editar la Entidad Pública", e);
        }

        //SE CONSULTA EL LISTADO DE ENTIDADES PUBLICAS.
        List entidadesPublicas;
        try {
            entidadesPublicas = hermod.getEntidadesReparto();
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionEntidadesPublicasRepartoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionEntidadesPublicasRepartoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(entidadesPublicas, EvnRespAdministracionHermod.CAMBIO_ENTIDAD_PUBLICA);
        respuesta.setEntidadesPublicas(entidadesPublicas);
        return respuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta consultarEntidadPublicaByNaturaleza(EvnAdministracionHermod evento) throws EventoException {

        EntidadPublica entidadPublica = evento.getEntidadPublica();
        NaturalezaJuridicaEntidad naturaleza = entidadPublica.getNaturalezaJuridica();

        //SE CONSULTA EL LISTADO DE ENTIDADES PUBLICAS.
        List entidadesPublicas;
        try {
            if (naturaleza != null && naturaleza.getIdNatJuridicaEntidad() != null) {
                entidadesPublicas = hermod.getEntidadesRepartoByNaturaleza(naturaleza);
            } else {
                entidadesPublicas = hermod.getEntidadesReparto();
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionEntidadesPublicasRepartoException("No se pudo consultar la Entidad Pública", e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new AdministracionEntidadesPublicasRepartoException("No se pudo consultar la Entidad Pública", e);
        }
        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(entidadesPublicas, EvnRespAdministracionHermod.CAMBIO_ENTIDAD_PUBLICA);
        respuesta.setEntidadesPublicas(entidadesPublicas);
        return respuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta eliminarEntidadPublica(EvnAdministracionHermod evento) throws EventoException {
        try {
            hermod.eliminarEntidadPublica(evento.getEntidadPublica());
        } catch (HermodException e) {
            throw new AdministracionEntidadesPublicasRepartoException("Debido a restricciones de integridad de datos no se pudo eliminar la Entidad Pública", e);

        } catch (Throwable e) {
            throw new AdministracionEntidadesPublicasRepartoException("Debido a restricciones de integridad de datos no se pudo eliminar la Entidad Pública", e);
        }
        List entidadesPublicas;
        try {
            entidadesPublicas = hermod.getEntidadesReparto();
        } catch (HermodException e) {
            throw new AdministracionEntidadesPublicasRepartoException("Debido a restricciones de integridad de datos no se pudo eliminar la Entidad Pública", e);
        } catch (Throwable e) {
            throw new AdministracionEntidadesPublicasRepartoException("Debido a restricciones de integridad de datos no se pudo eliminar la Entidad Pública", e);
        }
        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(entidadesPublicas, EvnRespAdministracionHermod.CAMBIO_ENTIDAD_PUBLICA);
        respuesta.setEntidadesPublicas(entidadesPublicas);
        return respuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta cargarCalificadoresCirculo(EvnAdministracionHermod evento) throws EventoException {
        Circulo cir = evento.getCirculo();
        List calificadoresCirculo;
        List subtiposatencion;
        String RolID = CRoles.SIR_ROL_CALIFICADOR;
        try {
            Rol rol = fenrir.getRolByID(RolID);
            calificadoresCirculo = fenrir.consultarUsuariosPorCirculoRol(cir, rol);

            subtiposatencion = hermod.getSubTiposAtencion();

        } catch (FenrirException e) {
            throw new EventoException("fallo en el servicio de obtener calificadores.", e);
        } catch (Throwable e) {
            throw new EventoException("fallo en el servicio de obtener calificadores.", e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(calificadoresCirculo, EvnRespAdministracionHermod.CARGAR_CALIFICADORES_ORDEN_SUBTIPOATENCION_OK);
        respuesta.setSubtiposAtencion(subtiposatencion);
        return respuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta cargarCalificadoresSubtipoAtencion(EvnAdministracionHermod evento) throws EventoException {
        Circulo cir = evento.getCirculo();
        String idSubtipoAtencion = evento.getIdSubtipoAtencion();
        List calificadoresSubtiposAtencion;

        SubtipoAtencion sub = new SubtipoAtencion();
        sub.setIdSubtipoAtencion(idSubtipoAtencion);

        try {

            calificadoresSubtiposAtencion = hermod.getCalificadoresSubtipoAtencion(cir, sub);

        } catch (HermodException e) {
            throw new EventoException("fallo en el servicio de obtener calificadores.", e);
        } catch (Throwable e) {
            throw new EventoException("fallo en el servicio de obtener calificadores.", e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(calificadoresSubtiposAtencion, EvnRespAdministracionHermod.CARGAR_CALIFICADORES_SUBTIPOATENCION_OK);
        return respuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta adicionarOrdenSubtipoAtencion(EvnAdministracionHermod evento) throws EventoException {
        Circulo cir = evento.getCirculo();
        String idSubtipoAtencion = evento.getIdSubtipoAtencion();
        String idUsuario = evento.getIdUsuario();
        String orden = evento.getOrden();
        List calificadoresSubtiposAtencion = new Vector();

        SubtipoAtencion sub = new SubtipoAtencion();
        sub.setIdSubtipoAtencion(idSubtipoAtencion);

        Usuario usu = new Usuario();
        usu.setIdUsuario(Long.parseLong(idUsuario));

        try {
            if (hermod.addOrdenSubtipoAtencion(sub, usu, orden, cir)) {
                calificadoresSubtiposAtencion = hermod.getCalificadoresSubtipoAtencion(cir, sub);
            }
        } catch (HermodException e) {
            throw new EventoException("fallo en el servicio de adicionar un orden.", e);
        } catch (Throwable e) {
            throw new EventoException("fallo en el servicio de adicionar un orden.", e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(calificadoresSubtiposAtencion, EvnRespAdministracionHermod.CARGAR_CALIFICADORES_SUBTIPOATENCION_OK);
        return respuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta removerOrdenSubtipoAtencion(EvnAdministracionHermod evento) throws EventoException {

        Circulo cir = evento.getCirculo();
        UsuarioSubtipoAtencion usuSubtipoAtencion = evento.getUsuSubtipoAtencion();
        List calificadoresSubtiposAtencion = new Vector();

        String idSubtipoAtencion = evento.getIdSubtipoAtencion();
        SubtipoAtencion sub = new SubtipoAtencion();
        sub.setIdSubtipoAtencion(idSubtipoAtencion);

        try {
            if (hermod.removeOrdenSubtipoAtencion(usuSubtipoAtencion, cir)) {
                calificadoresSubtiposAtencion = hermod.getCalificadoresSubtipoAtencion(cir, sub);
            }
        } catch (HermodException e) {
            throw new EventoException("fallo en el servicio de remover el orden", e);
        } catch (Throwable e) {
            throw new EventoException("fallo en el servicio de remover el orden.", e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(calificadoresSubtiposAtencion, EvnRespAdministracionHermod.CARGAR_CALIFICADORES_SUBTIPOATENCION_OK);
        return respuesta;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta obtenerImpresorasCirculo(EvnAdministracionHermod evento) throws EventoException {
        Map impresoras = null;
        Circulo cir = evento.getCirculo();

        try {
            if (cir != null) {
                impresoras = forseti.getConfiguracionImpresoras(cir.getIdCirculo());
            }
        } catch (HermodException e1) {
            throw new EventoException(e1.getMessage(), e1);
        } catch (FenrirException e) {
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(impresoras, EvnRespAdministracionHermod.OBTENER_IMPRESORAS_CIRCULO);
        respuesta.setCirculo(cir);
        return respuesta;
    }

    /**
     * Metodo que retorna la cadena con la fecha actual de impresión.
     *
     * @return
     */
    protected String getFechaImpresion() {

        Calendar c = Calendar.getInstance();
        int dia, ano, hora;
        String min, seg, mes;

        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
        ano = c.get(Calendar.YEAR);

        hora = c.get(Calendar.HOUR_OF_DAY);
        if (hora > 12) {
            hora -= 12;
        }

        min = formato(c.get(Calendar.MINUTE));
        seg = formato(c.get(Calendar.SECOND));

        String fechaImp
                = "Impreso el "
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
     * Metodo que retorna un numero con un "0" antes en caso de ser menor que
     * 10.
     *
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
     * Metodo que obtiene los valores de los oplookupcodes y los oplooktypes,
     * para subirlos al contexto
     *
     * @param i el numero.
     * @return
     */
    protected EventoRespuesta recargarContext(EvnRespAdministracionHermod evento) throws EventoException {

        List tiposID = null;
        List interesJuridico = null;
        List causalesReImp = null;
        List lookUpType = null;
        List unidadesMedida = null;
        List visibilidades = null;
        List tarifasCertificados = null;
        String ipBalanceador = "";
        String usarBalanceador = "";

        Hashtable ht = new Hashtable();

        try {
            tarifasCertificados = hermod.getTiposTarifaCertificados();
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        try {
            tiposID = hermod.getTiposDocumento();
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        try {
            interesJuridico = hermod.getInteresesJuridicos();
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        try {
            causalesReImp = hermod.getCausalesReimpresion();
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        try {
            lookUpType = hermod.getOPLookupTypes();
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        try {
            unidadesMedida = hermod.getOPLookupCodes(COPLookupTypes.MEDIDAS_AREA);
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        try {
            visibilidades = hermod.getTiposVisibilidad();
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        try {
            ipBalanceador = hermod.getValor(COPLookupTypes.CONFIGURACION_IMPRESION, CImpresion.IP_BALANCEADOR);
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepción al consultar la ip del balanceador "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
        try {
            usarBalanceador = hermod.getValor(COPLookupTypes.CONFIGURACION_IMPRESION, CImpresion.USAR_BALANCEADOR);
        } catch (HermodException e) {
            throw new ANInitParametrosException(e.getMessage(), e);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepción al consultar si se debe usar el balanceador "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        ////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////	
        try {
            ht.put(EvnRespSistema.LISTA_TARIFAS_CERTIFICADOS, tarifasCertificados);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.LISTA_TIPOS_ID, tiposID);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.LISTA_INTERES_JURIDICO, interesJuridico);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.LISTA_CAUSALES_REIMPRESION, causalesReImp);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.LISTA_OPLOOKUP_TYPE, lookUpType);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.LISTA_UNIDADES_MEDIDA, unidadesMedida);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.LISTA_VISIBILIDADES, visibilidades);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.IP_BALANCEADOR, ipBalanceador);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        try {
            ht.put(EvnRespSistema.USAR_BALANCEADOR, usarBalanceador);
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada "
                    + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }

        evento.setHt(ht);

        return evento;
    }

    private EventoRespuesta cargarRolesPantallasAdministrativas(EvnAdministracionHermod evento) throws EventoException {

        List roles = evento.getRoles();
        List pantallas = null;
        List rolesPantallas = null;

        /**
         * se cargan todas las pantallas de reportes*
         */
        try {
            pantallas = hermod.getPantallasAdministrativasByPagina(CPantallaAdministrativa.PG_REPORTES);
        } catch (HermodException e1) {
            throw new EventoException(e1.getMessage(), e1);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new EventoException(e.getMessage(), e);
        }

        /**
         * se cargan todas las pantallas de reportes asignadas a roles*
         */
        try {
            roles = fenrir.consultarRoles();

            if (roles != null) {
                rolesPantallas = hermod.obtenerRolPantallasAdministrativasPorRolPagina(roles, "PG_REPORTES");
            }
        } catch (HermodException e1) {
            throw new EventoException(e1.getMessage(), e1);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(pantallas, EvnRespAdministracionHermod.CARGAR_ROLES_PANTALLAS_ADMINISTRATIVAS_OK);
        respuesta.setRolesPantallas(rolesPantallas);
        return respuesta;
    }

    private EventoRespuesta eliminarRolPantallaAdministrativa(EvnAdministracionHermod evento) throws EventoException {
        RolPantalla rolPantalla = null;

        /**
         * se cargan todas las pantallas de reportes*
         */
        try {
            rolPantalla = evento.getRolPantallaAEliminar();

            boolean eliminadoExito = false;

            if (rolPantalla != null) {
                eliminadoExito = hermod.deleteRolPantallasAdministrativa(rolPantalla);
            }

            if (!eliminadoExito) {
                throw new EventoException("No se pudo deasignar la pantalla al rol");
            }
        } catch (HermodException e1) {
            throw new EventoException(e1.getMessage(), e1);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new EventoException(e.getMessage(), e);
        }

        /**
         * se cargan todas las pantallas de reportes asignadas a roles*
         */
        List roles = evento.getRoles();
        List rolesPantallas = null;

        try {
            if (roles != null) {
                rolesPantallas = hermod.obtenerAdministrativasPorRol(roles);
            }
        } catch (HermodException e1) {
            throw new EventoException(e1.getMessage(), e1);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(rolesPantallas, EvnRespAdministracionHermod.ELIMINAR_ROL_PANTALLA_ADMINISTRATIVA_OK);
        return respuesta;
    }

    private EventoRespuesta agregarRolPantallaAdministrativa(EvnAdministracionHermod evento) throws EventoException {

        RolPantalla rolPantalla = null;

        /**
         * se cargan todas las pantallas de reportes*
         */
        try {
            rolPantalla = evento.getRolPantallaAAgregar();

            if (rolPantalla != null) {
                hermod.addRolPantallasAdministrativa(rolPantalla);
            }

        } catch (HermodException e1) {
            throw new EventoException(e1.getMessage(), e1);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new EventoException(e.getMessage(), e);
        }

        /**
         * se cargan todas las pantallas de reportes asignadas a roles*
         */
        List roles = evento.getRoles();
        List rolesPantallas = null;

        try {
            if (roles != null) {
                rolesPantallas = hermod.obtenerAdministrativasPorRol(roles);
            }
        } catch (HermodException e1) {
            throw new EventoException(e1.getMessage(), e1);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, "Ha ocurrido una excepcion inesperada ", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespAdministracionHermod respuesta = new EvnRespAdministracionHermod(rolesPantallas, EvnRespAdministracionHermod.AGREGAR_ROL_PANTALLA_ADMINISTRATIVA_OK);
        return respuesta;
    }

    /**
     * @param evento
     * @return Pablo Quintana Reimprime desde las pantallas administrativas una
     * constancia de testamento siempre y cuando el turno no esté finalizado y
     * haya pasado por la fase Registro de testamento
     * @throws
     */
    private EventoRespuesta reimprimirFormularioTestamentos(EvnAdministracionHermod evento) throws EventoException {
        try {
            //Verificar si es sujeto de reimpresion
            Turno turno = hermod.getTurnobyWF(evento.getIdTurnoCalificacion());
            TurnoPk id = new TurnoPk();
            id.anio = turno.getAnio();
            id.idCirculo = turno.getIdCirculo();
            id.idProceso = turno.getIdProceso();
            id.idTurno = turno.getIdTurno();
            //comprobar que el turno no esta anulado
            if (turno.getAnulado() != null && turno.getAnulado().equals(CTurno.TURNO_ANULADO)) {
                throw new EventoException("El turno esta anulado");
            }
            //TFS 3166: NO SE PUEDEN REIMPRIMIR TURNOS FINALIZADOS
            if (turno.getFechaFin() != null) {
                throw new EventoException("El turno esta finalizado");
            }
            if (turno.getSolicitud().getProceso().getIdProceso() != Long.parseLong(CProceso.PROCESO_REGISTRO)) {
                throw new EventoException("El turno no pertence a Registro");
            }
            List historia = turno.getHistorials();
            int i = 0;
            boolean esReimprimible = false;
            while (i < historia.size()) {
                TurnoHistoria historial = (TurnoHistoria) historia.get(i);
                if (historial.getFase().equals(CFase.REG_TESTAMENTO)) {
                    if (i != historia.size() - 1) {
                        esReimprimible = true;
                    }
                }
                i++;
            }
            if (!esReimprimible) {
                throw new EventoException("El turno " + turno.getIdWorkflow() + " no puede ser impreso porque no ha pasado la fase " + CFase.REG_TESTAMENTO);
            }
            Testamento testamento = hermod.getTestamentoByID(turno.getSolicitud().getIdSolicitud());
            if (testamento != null) {
                SolicitudRegistro solRegistro = (SolicitudRegistro) turno.getSolicitud();
                Date fechaRadicacion = null;
                if (turno.getSolicitud() != null && turno.getSolicitud().getFecha() != null) {
                    fechaRadicacion = turno.getSolicitud().getFecha();
                }
                //Se crea el imprimible de testamento
                ImprimibleInscripcionTestamento impTestamento = new ImprimibleInscripcionTestamento(testamento, solRegistro.getDocumento(), turno.getIdWorkflow(), turno.getSolicitud().getCirculo().getNombre(), this.getFechaImpresion(), evento.getUsuarioNeg(), fechaRadicacion, CTipoImprimible.INSCRIPCION_TESTAMENTOS);
                           Turno rta = null;
                   try {
                            rta = hermod.getTurnobyWF(turno.getIdWorkflow());
                    } catch (HermodException e) {
                
                    }
                    List historia1 = rta.getHistorials();
                    boolean isFirma= false;
                    Iterator iti = historia1.iterator();
                    String resp1 = "";
                    if(historia1==null || historia1.size()==0){
                    
                    }else{
                        Boolean control =true;
                        while(iti.hasNext()){
                                TurnoHistoria th = (TurnoHistoria)iti.next();
                                if(th.getFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
                                        isFirma = true;
                                }
                        }
                    }
                    Circulo circulo = null;
                CirculoPk cid = new CirculoPk();
                cid.idCirculo = turno.getIdCirculo();
                
                  String nombre = "";
                String archivo = "";
                String cargoToPrint = "";
                String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
                FirmaRegistrador firmaRegistrador = null;
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
                                impTestamento.setCargoRegistrador(cargoToPrint);
                                impTestamento.setNombreRegistrador(sNombre);

                if (rutaFisica != null) {
                    BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                     byte pixeles[] = null;
                    try {
                        pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                    } catch (Throwable e1) {

                        e1.printStackTrace();
                    }
                    if(isFirma) {
                        impTestamento.setImpFirma(true);
                        impTestamento.setChangetextforregistrador(true);
                        impTestamento.setPixelesImagenFirmaRegistrador(pixeles);
                    }
                    
                }
		if (nombre==null){
                    
		  nombre="";
                }
		
		
		impTestamento.setCargoRegistrador(cargoToPrint);  
		impTestamento.setNombreRegistrador(nombre);
	
		
                Hashtable tabla = new Hashtable();
                String INTENTOS = "INTENTOS";
                String ESPERA = "ESPERA";
                tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
                tabla.put(ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
                for (int j = 0; j < evento.getNumeroImpresiones(); j++) {
                    this.imprimir(impTestamento, tabla, turno.getCirculoproceso().getIdCirculo(), 1, false);
                }
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage());
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage());
        }
        return null;
    }

    /**
     * Imprime el objeto imprimible en la estacion asociada al circulo del turno
     * dado con los parametros asignados.
     *
     * @param turno
     * @param imprimible
     * @param parametros
     * @return
     * @throws EventoException Pablo Quintana Junio 19 2008
     */
    private int imprimir(ImprimibleBase imprimible, Hashtable parametros, String ID, int numCopias, boolean lanzarExcepcion) throws EventoException {
        boolean impresion_ok = false;
        String mensaje_error = "";
        //CONSTANTES PARA LA IMPRESIÓN.
        String INTENTOS = "INTENTOS";
        String ESPERA = "ESPERA";
        Bundle b = new Bundle(imprimible);
        b.setNumeroCopias(numCopias);
        String numIntentos = (String) parametros.get(INTENTOS);
        String espera = (String) parametros.get(ESPERA);
        Integer intentosInt = new Integer(numIntentos);
        int intentos = intentosInt.intValue();
        Integer esperaInt = new Integer(espera);
        int esperado = esperaInt.intValue();
        int idImprimible = 0;
        //Ciclo que se ejecuta de acuerdo al valor recibido en intentos
        try {
            //se manda a imprimir el recibo por el identificador unico de usuario o id del circulo (caso especial CORRECCIONES)
            printJobs.enviar(ID, b, intentos, esperado);
            impresion_ok = true;
        } catch (PrintJobsException t) {
            idImprimible = t.getIdImpresion();
            if (idImprimible == 0) {
                throw new EventoException("Se genero una excepción al imprimir la Constancia de testamentos. " + t.getMessage());
            }
        } catch (Throwable t) {
            t.printStackTrace();
            mensaje_error = t.getMessage();
            if (lanzarExcepcion && !impresion_ok) {
                throw new EventoException(mensaje_error);
            }
        }
        return idImprimible;
    }

    /**
     * Adiciona una relación banco-círculo (SIR_OP_BANCOS_X_CIRCULO)
     *
     * @param evento
     * @return
     * @throws EventoException Pablo Quintana Nov07-2008
     */
    private EvnRespAdministracionHermod adicionaBancoCirculo(EvnAdministracionHermod evento)
            throws EventoException {
        List bancosXCirculo = null;
        try {
            if (hermod.addBancoCirculo(evento.getBancoXCirculo())) {
                bancosXCirculo = hermod.getBancosXCirculo(evento.getBancoXCirculo().getIdCirculo());
            } else {
                throw new CreacionBancoException("No se pudo crear el Banco", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(bancosXCirculo, EvnRespAdministracionHermod.ADICIONA_BANCO_CIRCULO_OK);
        evRespuesta.setBancosXCirculo(evento.getBancoXCirculo());
        return evRespuesta;
    }

    /**
     * Elimina un banco relacionado con un círculo (SIR_OP_BANCOS_X_CIRCULO)
     *
     * @param evento
     * @return
     * @throws EventoException Pablo Quintana Nov07-2008
     */
    private EvnRespAdministracionHermod eliminaBancoCirculo(EvnAdministracionHermod evento) throws EventoException {
        List bancosXCirculo = null;
        try {
            if (hermod.eliminaBancoCirculo(evento.getBancoXCirculo())) {
                bancosXCirculo = hermod.getBancosXCirculo(evento.getBancoXCirculo().getIdCirculo());
            } else {
                throw new CreacionBancoException("No se pudo crear el Banco", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(bancosXCirculo, EvnRespAdministracionHermod.ADICIONA_BANCO_CIRCULO_OK);
        evRespuesta.setBancosXCirculo(evento.getBancoXCirculo());
        return evRespuesta;
    }

    /**
     * Consulta los bancos configurados para un determinado círculo
     *
     * @param evento
     * @return
     * @throws EventoException Pablo Quintana Nov07-2008
     */
    private EvnRespAdministracionHermod seleccionaBancoCirculo(EvnAdministracionHermod evento) throws EventoException {
        List bancosXCirculo = null;
        try {
            bancosXCirculo = hermod.getBancosXCirculo(evento.getCirculo().getIdCirculo());
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(bancosXCirculo, EvnRespAdministracionHermod.ADICIONA_BANCO_CIRCULO_OK);
        evRespuesta.setBancosXCirculo(evento.getBancoXCirculo());
        return evRespuesta;
    }

    /**
     * Método para activar un banco como principal (defecto) para una oficina
     *
     * @param evento
     * @return
     * @throws EventoException Pablo Quintana Nov07-2008
     */
    private EvnRespAdministracionHermod activarBancoPrincipal(EvnAdministracionHermod evento) throws EventoException {
        List bancosXCirculo = null;
        try {
            if (hermod.activarBancoPrincipal(evento.getBancoXCirculo())) {
                bancosXCirculo = hermod.getBancosXCirculo(evento.getBancoXCirculo().getIdCirculo());
            } else {
                throw new CreacionBancoException("No se pudo crear el Banco", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(bancosXCirculo, EvnRespAdministracionHermod.ADICIONA_BANCO_CIRCULO_OK);
        evRespuesta.setBancosXCirculo(evento.getBancoXCirculo());
        return evRespuesta;
    }

    /**
     * Adiciona una relación circulo-banco-cuenta (SIR_OP_CUENTAS_BANCARIAS)
     *
     * @param evento
     * @return
     * @throws EventoException
     * @autor Geremias Ortiz Lozano
     */
    private EvnRespAdministracionHermod adicionaCuentaBancoCirculo(EvnAdministracionHermod evento)
            throws EventoException {
        List cuentasXCirculoBanco = null;
        List cuentasBancarias = null;
        try {
            if (hermod.addCuentaBancaria(evento.getCuentasBancarias())) {
                cuentasBancarias = hermod.getCuentasBancarias(evento.getCirculo().getIdCirculo(), evento.getBanco().getIdBanco());
                //bancosXCirculo = hermod.getBancosXCirculo(evento.getBancoXCirculo().getIdCirculo());
            } else {
                throw new CreacionBancoException("No se pudo crear la Cuenta Bancaria", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(cuentasBancarias, EvnRespAdministracionHermod.CUENTAS_X_CIRCULO_BANCO_OK);
//        evRespuesta.setBancosXCirculo(evento.getBancoXCirculo());
//        evRespuesta.setCuentasBancarias(cuentasBancarias);
        return evRespuesta;
    }

    /**
     * Consulta las cuentas bancarias configurados para un determinado círculo y
     * banco
     *
     * @param evento
     * @return
     * @throws EventoException
     *
     */
    private EvnRespAdministracionHermod getCuentasXBancoCirculo(EvnAdministracionHermod evento) throws EventoException {
        System.out.println("putito 2");
        List cuentasBancarias = null;
        try {
            cuentasBancarias = hermod.getCuentasBancarias(evento.getCirculo().getIdCirculo(), evento.getBanco().getIdBanco());
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(cuentasBancarias, EvnRespAdministracionHermod.CUENTAS_X_CIRCULO_BANCO_OK);
        return evRespuesta;
    }

    private EvnRespAdministracionHermod activarInactivarCtaBancaria(EvnAdministracionHermod evento) throws EventoException {

        System.out.println("VAMOS A CAMBIAR ESTADOSSSS");
        List cuentasBancarias = null;
        String estadosCtasBancarias = evento.getEstadosCtasBancarias();

        List estados = new ArrayList();
        String[] estadoArray = estadosCtasBancarias.split(",");
        for (int i = 0; i < estadoArray.length; i++) {

            String[] estadoAdd = estadoArray[i].split("&");
            boolean estado = Boolean.parseBoolean(estadoAdd[3]);

            try {
                hermod.actualizarEstadoCtaBancaria(evento.getCirculo().getIdCirculo(),
                        evento.getBanco().getIdBanco(), estadoAdd[2], estado);
            } catch (HermodException e) {
                Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
                throw new EventoException(e.getMessage(), e);
            } catch (Throwable e) {
                Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
                throw new EventoException(e.getMessage(), e);
            }
        }

        try {
            cuentasBancarias = hermod.getCuentasBancarias(evento.getCirculo().getIdCirculo(), evento.getBanco().getIdBanco());
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(cuentasBancarias, EvnRespAdministracionHermod.CUENTAS_X_CIRCULO_BANCO_OK);
        return evRespuesta;
    }
    
    /**
     * Adiciona una relación circulo-banco-cuenta (SIR_OP_CUENTAS_BANCARIAS)
     *
     * @param evento
     * @return
     * @throws EventoException
     * @autor Geremias Ortiz Lozano
     */
    private EvnRespAdministracionHermod adicionaCanalRecaudo(EvnAdministracionHermod evento)
            throws EventoException {
        List canalesRecaudo = null;
        try {
            if (hermod.addCanalRecaudo(evento.getCanalesRecaudo())) {
                canalesRecaudo = hermod.getCanalesRecaudo(false);
            } else {
                throw new CreacionBancoException("No se pudo crear el Canal de Recaudo", null);
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(canalesRecaudo, EvnRespAdministracionHermod.CANAL_RECAUDO_OK);
//        evRespuesta.setBancosXCirculo(evento.getBancoXCirculo());
//        evRespuesta.setCuentasBancarias(cuentasBancarias);
        return evRespuesta;
    }
    
    private EvnRespAdministracionHermod activarInactivarCanalRecaudo(EvnAdministracionHermod evento) throws EventoException {

        System.out.println("VAMOS A CAMBIAR ESTADOS CANALES");
        List canalesRecaudo = null;
        String estadosCanalesRecaudo = evento.getEstadosCanalesRecaudo();

        List estados = new ArrayList();
        String[] estadoArray = estadosCanalesRecaudo.split(",");
        for (int i = 0; i < estadoArray.length; i++) {

            String[] estadoAdd = estadoArray[i].split("&");
            int idCanal = Integer.parseInt(estadoAdd[0]);
            boolean estado = Boolean.parseBoolean(estadoAdd[1]);

            try {
                hermod.actualizarEstadoCanalRecaudo(idCanal, estado);
            } catch (HermodException e) {
                Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
                throw new EventoException(e.getMessage(), e);
            } catch (Throwable e) {
                Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
                throw new EventoException(e.getMessage(), e);
            }
        }

        try {
            canalesRecaudo = hermod.getCanalesRecaudo(false);
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(canalesRecaudo, EvnRespAdministracionHermod.CANAL_RECAUDO_OK);
        return evRespuesta;
    }
    
    private EvnRespAdministracionHermod seleccionaCuentasBancarias(EvnAdministracionHermod evento) throws EventoException {

        System.out.println("VAMOS A BUSCAR CUENTAS");
        List cuentasBancarias = null;
        try {
            if(hermod.validaCampoBancoFranquicia(evento.getTipoPago())){
                cuentasBancarias = hermod.getCuentasBancariasXCirculo(evento.getCirculo());
            }
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(cuentasBancarias, EvnRespAdministracionHermod.CUENTAS_X_CIRCULO_OK);
        return evRespuesta;
    }
    
    private EvnRespAdministracionHermod activarInactivarCtp(EvnAdministracionHermod evento) throws EventoException {

        List circuloCanalTipoPago = null;
        String estadosCtp = evento.getEstadosCtp();

        String[] estadoArray = estadosCtp.split(",");
        for (int i = 0; i < estadoArray.length; i++) {

            String[] estadoAdd = estadoArray[i].split("&");
            String idCtp = estadoAdd[0];
            boolean estado = Boolean.parseBoolean(estadoAdd[1]);

            try {
                hermod.actualizarEstadoCtp(idCtp, estado);
            } catch (HermodException e) {
                Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
                throw new EventoException(e.getMessage(), e);
            } catch (Throwable e) {
                Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
                throw new EventoException(e.getMessage(), e);
            }
        }

        try {
            
            Circulo circulo = evento.getCirculo();
            CirculoPk cid = new CirculoPk();
            cid.idCirculo = circulo.getIdCirculo();
            circuloCanalTipoPago = hermod.getCirculoTiposPago(cid);
            
        } catch (HermodException e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        } catch (Throwable e) {
            Log.getInstance().error(ANAdministracionHermod.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }
        EvnRespAdministracionHermod evRespuesta
                = new EvnRespAdministracionHermod(circuloCanalTipoPago, EvnRespAdministracionHermod.ACTUALIZA_CIRCULO_TIPO_PAGO_OK);
        return evRespuesta;
    }
    
}

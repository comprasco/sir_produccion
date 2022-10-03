/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.acciones.registro;

import gov.sir.core.eventos.comun.EvnRespNotas;
import gov.sir.core.eventos.registro.EvnRespTramiteSuspension;
import gov.sir.core.eventos.registro.EvnTramiteSuspension;
import gov.sir.core.negocio.acciones.comun.ANNotas;
import gov.sir.core.negocio.acciones.excepciones.NotaNoAgregadaException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TramiteSuspensionException;
import gov.sir.core.negocio.modelo.ArchivosJustifica;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.fenrir.FenrirException;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import java.io.File;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import gov.sir.core.negocio.modelo.TramiteSuspension;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.CTramiteSuspension;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import java.util.Hashtable;
import org.auriga.util.ExceptionPrinter;

/**
 *
 * @author developer5
 */
public class ANTramiteSuspension extends SoporteAccionNegocio {

    private ServiceLocator service = null;
    private FenrirServiceInterface fenrir;
    private HermodServiceInterface hermod;

    public ANTramiteSuspension() throws EventoException {
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
            fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

            if (fenrir == null) {
                throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio Fenrir" + e);
        }

        if (fenrir == null) {
            throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
        }
    }

    public EventoRespuesta perform(Evento e) throws EventoException {

        EvnTramiteSuspension evento = (EvnTramiteSuspension) e;
        if (evento == null || evento.getTipoEvento() == null) {
            return null;
        }

        if (evento.getTipoEvento().equals(EvnTramiteSuspension.AGREGAR_RESPUESTA_PREV)
                || evento.getTipoEvento().equals(EvnTramiteSuspension.AGREGAR_RESPUESTA_TEMP)) {
            return agregarRespuesta(evento);
        } else if (evento.getTipoEvento().equals(EvnTramiteSuspension.AVANZAR)) {
            return avanzarCalificacion(evento);
        } else if (evento.getTipoEvento().equals(EvnTramiteSuspension.CANCELAR_TRAMITE_SUSP_TEMP) ||
                evento.getTipoEvento().equals(EvnTramiteSuspension.CANCELAR_TRAMITE_SUSP_PREV)) {
            return cancelarTramiteSuspension(evento);
        }
        return null;
    }

    private EvnRespTramiteSuspension cancelarTramiteSuspension(EvnTramiteSuspension evento)
            throws EventoException {
        
        List datos = null;
        Usuario usuario = null;
        try {
            int usuarioId = 0;

            if (evento.getTramSuspension() != null) {
                usuarioId = evento.getTramSuspension().getTramsIdUsuario();
            } else {
                usuario = evento.getUsuario();
                usuarioId = (int) fenrir.darIdUsuario(usuario.getUsuarioId());
            }

            fenrir.eliminarTramiteSuspensionTurno(usuarioId, evento.getTurno());
            datos = fenrir.consultarRespuestasUsuarios(usuarioId, evento.getTurno());
        } catch (FenrirException e) {
            throw new TramiteSuspensionException("Ocurrió un error al cancelar la transacción.", e);

        } catch (Throwable e) {
            throw new TramiteSuspensionException("Ocurrió un error al cancelar la transacción.", e);
        }
        
        EvnRespTramiteSuspension evRespuesta
                = new EvnRespTramiteSuspension(usuario, EvnRespTramiteSuspension.CONSULTA_RESPUESTA_OK);

        evRespuesta.setListaRespuesta(datos);
        return evRespuesta; 
    }

    private EventoRespuesta avanzarCalificacion(Evento ev) throws EventoException {

        EvnTramiteSuspension evento = (EvnTramiteSuspension) ev;
        Turno turno = evento.getTurnoAvance();
        Fase fase = evento.getFaseAvance();
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNegocio();
        String idUsuario = usuarioNeg.getUsername();
        String respuestaWF = null;
        
        if (fase.getID().equals(CTramiteSuspension.FASE_TRAMITE_SUSP_PREV)) {
            respuestaWF = CTramiteSuspension.RESPUESTA_SUSP_PREV;
        } else {
            respuestaWF = CTramiteSuspension.RESPUESTA_SUSP_TEMP;
        }

        try {

            Hashtable parametros = new Hashtable();
            parametros.put(CInfoUsuario.USUARIO_SIR, idUsuario);
            parametros.put("RESULT", respuestaWF);

            try {
                hermod.avanzarTurnoNuevoNormal(turno, fase, parametros, usuarioNeg);
            } catch (Throwable e) {
                throw new TramiteSuspensionException("No se pudo avanzar el turno.", e);
            }
        } catch (Exception e) {
            throw new TramiteSuspensionException("No se pudo avanzar el turno.", e);

        } catch (Throwable e) {
            throw new TramiteSuspensionException("No se pudo avanzar el turno.", e);

        }
        return null;
    }

    private EvnRespTramiteSuspension agregarRespuesta(EvnTramiteSuspension evento)
            throws EventoException {

        List datos = null;
        Usuario usuario = null;

        try {
            int usuarioId = 0;

            if (evento.getTramSuspension() != null) {
                usuarioId = evento.getTramSuspension().getTramsIdUsuario();
            } else {
                usuario = evento.getUsuario();
                usuarioId = (int) fenrir.darIdUsuario(usuario.getUsuarioId());
            }

            String uploadPath = gov.sir.fenrir.FenrirProperties.getInstancia().getProperty(gov.sir.fenrir.FenrirProperties.RUTA_DESTINO_ARCHIVO_JUSTIFICACION);

            if (evento.getArchivosJustifica() != null) {
                ArchivosJustifica archivosJustifica = fenrir.nuevoArchivo(evento.getArchivosJustifica());
                ArchivosJustifica infoArchivo = evento.getArchivosJustifica();

                String filePath = uploadPath + archivosJustifica.getJusIdArchivo() + "." + archivosJustifica.getJusTipoArchivo();
                if (archivosJustifica.getJusIdArchivo() > 0) {
                    FileItem fileToSave = evento.getFileItem();
                    File storeFile = new File(filePath);
                    fileToSave.write(storeFile);

                    TramiteSuspension tramiteSuspension = evento.getTramSuspension();
                    fenrir.crearRespuestasUsuarios(tramiteSuspension, infoArchivo);
                } else {
                    throw new TramiteSuspensionException("No fue posible realizar los cambios solicitados, intente de nuevo");
                }
            } else {
                ArchivosJustifica infoArchivo = null;
                TramiteSuspension tramiteSuspension = evento.getTramSuspension();
                fenrir.crearRespuestasUsuarios(tramiteSuspension, infoArchivo);
            }

            datos = fenrir.consultarRespuestasUsuarios(usuarioId, evento.getTurno());
        } catch (FenrirException e) {
            throw new TramiteSuspensionException("Ocurrió un error al actualizar los datos del usuario.", e);

        } catch (Throwable e) {
            throw new TramiteSuspensionException("Ocurrió un error al actualizar los datos del usuario.", e);

        }

        EvnRespTramiteSuspension evRespuesta
                = new EvnRespTramiteSuspension(usuario, EvnRespTramiteSuspension.CONSULTA_RESPUESTA_OK);

        evRespuesta.setListaRespuesta(datos);

        return evRespuesta;
    }

}

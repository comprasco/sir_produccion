package gov.sir.hermod.gdocumental.integracion;

import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Solicitante;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.util.DateFormatUtil;

import gov.sir.gdocumental.comun.Constantes;
import gov.sir.gdocumental.comun.entidades.Anulado;
import gov.sir.gdocumental.comun.entidades.Certificado;
import gov.sir.gdocumental.comun.entidades.Estado;
import gov.sir.gdocumental.comun.entidades.Matricula;
import gov.sir.gdocumental.comun.entidades.Turno;
import gov.sir.gdocumental.negocio.SGDocumentalJMS;

import gov.sir.hermod.HermodProperties;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Fernando Padilla Velez
 */
public class SGD {

    private gov.sir.core.negocio.modelo.Turno turno;

    private List turnos;
    
    private String usuario;

    private static final int ID_FASE_SOLICITUD  = 1;
    private static final int ID_FASE_FINALIZADO = 2;

    private static final String ACCION_ADICION     = "1";
    private static final String ACCION_EDICION     = "2";
    private static final String ACCION_ELIMINACION = "3";
    
    public SGD(gov.sir.core.negocio.modelo.Turno turno, String usuario) {
        this.turno   = turno;
        this.usuario = usuario;
        turnos = new ArrayList();
    }

    private boolean isCirculoValido(){
        HermodProperties hp = HermodProperties.getInstancia();
        String circulos     = hp.getProperty(HermodProperties.HERMOD_SGD_CIRCULOS);
        if(circulos.indexOf(this.turno.getIdCirculo()) != -1){
            return true;
        }
        return false;
    }

    public void enviarTurnoRegistro(){
        try{
            if(isCirculoValido()){
                addTurnoRegistro();
                addEstado(this.turno);
                crearHiloMensaje();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void enviarTurnoDevolucion(){
        try{
            if(isCirculoValido()){
                addTurnoDevolucion();
                addEstado(this.turno);
                crearHiloMensaje();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void enviarTurnoCorreccion(){
        try{
            if(isCirculoValido()){
                addTurnoCorreccion();
                addEstado(this.turno);
                crearHiloMensaje();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void enviarTurnoReparto(){
        try{
            if(isCirculoValido()){
                addTurnoReparto();
                addEstadoReparto(20);
                crearHiloMensaje();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void enviarTurnoConsulta(){
        try{
            if(isCirculoValido()){
                addTurnoConsulta();
                addEstado(this.turno);
                crearHiloMensaje();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void enviarEstadoTurno(){
        try{
            if(isCirculoValido()){
                addEstado(this.turno);
                if(turno.getSolicitud().getSolicitudesHijas() != null &&
                   turno.getSolicitud().getSolicitudesHijas().size() > 0){

                    for(int i = 0; i < turno.getSolicitud().getSolicitudesHijas().size();i ++){

                        SolicitudAsociada sa    = (SolicitudAsociada) turno.getSolicitud().getSolicitudesHijas().get(i);
                        if(sa.getSolicitudHija() instanceof SolicitudCertificado){
                            SolicitudCertificado sc = (SolicitudCertificado) sa.getSolicitudHija();
                            addEstado(sc.getTurno());
                        }
                    }
                }

            crearHiloMensaje();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

public void enviarEstadoTurnoReparto(){
        try{
            if(isCirculoValido()){
                addEstadoReparto(20);
                crearHiloMensaje();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void enviarAnulado(){
    try{
        if(isCirculoValido()){
            addEstado(ID_FASE_FINALIZADO,  this.turno);
            addAnulado();
            crearHiloMensaje();
        }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void enviarHabilitado(){
        try{
            if(isCirculoValido()){
                addEstado(ID_FASE_SOLICITUD,  this.turno);
                crearHiloMensaje();
            }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
    }

    public void enviarTurnoCertificado(){
        try{
            if(isCirculoValido()){
                addTurnoCertificado();
                addEstado(this.turno);
                crearHiloMensaje();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }

     /**
     * @author Fernando Padilla Velez
     * @see metodo encargado de establecer el estado de la fase del turno.
     */
    private void addEstado(gov.sir.core.negocio.modelo.Turno turno){
        if(turno.getIdFase().indexOf("SOLICITUD") != -1 ||
           turno.getIdFase().indexOf("CONFRONTAR") != -1 ||
           turno.getIdFase().indexOf("COR_REVISION_ANALISIS") != -1 ||
           turno.getIdFase().indexOf("CON_CONSULTA") != -1 ||
           turno.getIdFase().indexOf("REP_REPARTO") != -1 ||
           turno.getIdFase().indexOf("RES_ANALISIS") != -1 ||
           turno.getIdFase().indexOf("DEV_ANALISIS") != -1){

            addEstado(ID_FASE_SOLICITUD,  turno);

        }else if(turno.getIdFase().indexOf("FINALIZADO") != -1 ||
                 turno.getIdFase().indexOf("ENTREGA") != -1){
            addEstado(ID_FASE_FINALIZADO,  turno);
        }
    }

/**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un estado a lista de turnos.
     */
    @SuppressWarnings("unchecked")
    private void addEstado(int aIdFase,gov.sir.core.negocio.modelo.Turno turno){
        Estado estado = new Estado();
        estado.setAno(turno.getAnio());
        estado.setEnviado(Constantes.NO_ENVIADO);
        estado.setFase(aIdFase);
        estado.setFechaCambio(this.turno.getSolicitud().getFecha());
        estado.setIdCirculo(turno.getIdCirculo());
        estado.setIdProceso(turno.getIdProceso());
        estado.setIdTurno(turno.getIdTurno());
        estado.setUsuario(usuario);
        estado.setTipoMensaje(1);
        turnos.add(estado);
    }

    /**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un turno anulado a la lista de turnos.
     */
    @SuppressWarnings("unchecked")
    private void addAnulado(){
        if("S".equals(this.turno.getAnulado())){
            Anulado anulado = new Anulado();
            anulado.setAno(this.turno.getAnio());
            anulado.setAnulado(this.turno.getAnulado());
            anulado.setEnviado(Constantes.NO_ENVIADO);
            anulado.setFechaAnulacion(this.turno.getFechaFin());
            anulado.setIdCirculo(this.turno.getIdCirculo());
            anulado.setIdProceso(this.turno.getIdProceso());
            anulado.setIdTurno(this.turno.getIdTurno());
            anulado.setTipoMensaje(1);
            turnos.add(anulado);
        }
    }

/**
     * @author Fernando Padilla Velez
     * @see metodo encargado de la creacion del hilo para la publicación del mensaje.
     * @change Mantis 5383 Acta - Requerimiento No 200 - Reparto Sir SGD ETB2,
     *         Se agrega esta fragmento de codigo para la publicacion del mensaje.
     */
    private void crearHiloMensaje(){
        if(turnos.size() > 0){
            SGDocumentalJMS sGDocumentalJMS = new SGDocumentalJMS();
            sGDocumentalJMS.setDatosEnviar(turnos);
            Thread thread = new Thread(sGDocumentalJMS);
            thread.start();
        }
    }

/**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un turno de certificado a lista de turnos.
     */
    @SuppressWarnings("unchecked")
    private void addCertificadoAsociado(){
        
        if(turno.getSolicitud().getSolicitudesHijas() != null &&
           turno.getSolicitud().getSolicitudesHijas().size() > 0){
            
            for(int i = 0; i < turno.getSolicitud().getSolicitudesHijas().size();i ++){

                SolicitudAsociada sa    = (SolicitudAsociada) turno.getSolicitud().getSolicitudesHijas().get(i);
                SolicitudCertificado sc = (SolicitudCertificado) sa.getSolicitudHija();

                Certificado certificado = new Certificado();
                certificado.setAno(turno.getAnio());
                certificado.setIdCirculo(turno.getIdCirculo());
                certificado.setIdProceso(turno.getIdProceso());
                certificado.setIdTurno(turno.getIdTurno());

                certificado.setAnoTurno(sc.getTurno().getAnio());
                certificado.setIdCirculoTurno(sc.getTurno().getIdCirculo());
                certificado.setIdProcesoTurno(sc.getTurno().getIdProceso());
                certificado.setIdTurnoTurno(sc.getTurno().getIdTurno());

                certificado.setEnviado(Constantes.NO_ENVIADO);
                certificado.setFechaEvento(turno.getFechaInicio());
                certificado.setTipoMensaje(1);
                certificado.setAccion(ACCION_ADICION);

                addEstado(sc.getTurno());

                turnos.add(certificado);
            }
        }
   }

/**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un turno para el mensaje.
     */
    @SuppressWarnings("unchecked")
    private void addTurnoRegistro(){
        try{
            Turno turno = new Turno();
            turno.setAno(this.turno.getAnio());
            turno.setCiudad(this.turno.getSolicitud().getCirculo().getOficinaOrigen().getVereda().getMunicipio().getNombre());
            turno.setDescripcionInstrumento(this.turno.getDescripcion());
            turno.setEnviado(Constantes.NO_ENVIADO);
            turno.setSolicitante(this.turno.getSolicitud().getCiudadano().getApellido1());
            turno.setFechaDocumento(this.turno.getSolicitud().getFecha());
            turno.setFechaRadicacion(this.turno.getSolicitud().getFecha());
            turno.setIdCirculo(this.turno.getIdCirculo());
            turno.setIdCirculoPadre(this.turno.getIdCirculo());
            turno.setIdProceso(this.turno.getIdProceso());
            turno.setIdProcesoPadre(this.turno.getIdProceso());
            turno.setIdTuno(this.turno.getIdTurno());
            turno.setIdTurnoPadre(this.turno.getIdTurno());

            SolicitudRegistro sr = (SolicitudRegistro)this.turno.getSolicitud();
            if(sr.getDocumento() != null){
                turno.setFechaDocumento(sr.getDocumento().getFecha());
                turno.setInstrumento(sr.getDocumento().getTipoDocumento().getIdTipoDocumento());
                turno.setDescripcionInstrumento(sr.getDocumento().getComentario());
                turno.setNumeroDocumento(sr.getDocumento().getNumero());

                if(sr.getDocumento().getOficinaInternacional() == null){
                    turno.setOficinaOrigen(sr.getDocumento().getOficinaOrigen().getNombre());
                }else{
                    turno.setOficinaOrigen(sr.getDocumento().getOficinaInternacional());
                }
            }

            Pago pago = UtilidadesBD.consultarPago(this.turno.getSolicitud().getIdSolicitud());
            long numeroRecibo = 0;
            if(pago != null){
                if (pago.getLastNumRecibo() != null && !pago.getLastNumRecibo().equals(pago.getNumRecibo())) {
                    numeroRecibo = new Long(pago.getLastNumRecibo());
                } else {
                    numeroRecibo = new Long(pago.getNumRecibo()!=null?pago.getNumRecibo():"0");
                }
            }
            turno.setNumeroRecibo(numeroRecibo);
            turno.setTipoMensaje(1);
            turno.setUsuario(this.usuario);

            List<SolicitudFolio> ss = (List<SolicitudFolio>)this.turno.getSolicitud().getSolicitudFolios();

            for(SolicitudFolio s: ss){

                Matricula matricula = new Matricula();
                matricula.setAccion(ACCION_EDICION);
                matricula.setAno(this.turno.getAnio());
                matricula.setEnviado(Constantes.NO_ENVIADO);
                matricula.setFechaEvento(this.turno.getSolicitud().getFecha());
                matricula.setIdCirculo(this.turno.getIdCirculo());
                matricula.setIdProceso(this.turno.getIdProceso());
                matricula.setIdTurno(this.turno.getIdTurno());
                matricula.setNumeroMatricula(Long.parseLong(s.getIdMatricula().split("-")[1]));
                matricula.setTipoMensaje(1);

                turno.addMatricula(matricula);
            }
            addCertificadoAsociado();

            turnos.add(turno);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un turno para el mensaje.
     */
    @SuppressWarnings("unchecked")
    private void addTurnoCertificado(){
        try{
            Turno turno = new Turno();
            turno.setAno(this.turno.getAnio());
            turno.setCiudad(this.turno.getSolicitud().getCirculo().getOficinaOrigen().getVereda().getMunicipio().getNombre());
            turno.setDescripcionInstrumento(this.turno.getDescripcion());
            turno.setEnviado(Constantes.NO_ENVIADO);

            turno.setFechaDocumento(this.turno.getSolicitud().getFecha());
            turno.setSolicitante(this.turno.getSolicitud().getCiudadano().getApellido1());
            turno.setIdCirculo(this.turno.getIdCirculo());
            turno.setIdCirculoPadre(this.turno.getIdCirculo());
            turno.setIdProceso(this.turno.getIdProceso());
            turno.setIdProcesoPadre(this.turno.getIdProceso());
            turno.setIdTuno(this.turno.getIdTurno());
            turno.setIdTurnoPadre(this.turno.getIdTurno());

            SolicitudCertificado sc = (SolicitudCertificado)this.turno.getSolicitud();
            if(sc.getDocumento() != null){
                turno.setFechaDocumento(sc.getDocumento().getFecha());
                turno.setInstrumento(sc.getDocumento().getTipoDocumento().toString());
                turno.setDescripcionInstrumento(sc.getDocumento().getComentario());
                turno.setNumeroDocumento(sc.getDocumento().getNumero());
                if(sc.getDocumento().getOficinaInternacional() == null){
                    turno.setOficinaOrigen(sc.getDocumento().getOficinaOrigen().getNombre());
                }else{
                    turno.setOficinaOrigen(sc.getDocumento().getOficinaInternacional());
                }
            }

            Pago pago = UtilidadesBD.consultarPago(this.turno.getSolicitud().getIdSolicitud());
            long numeroRecibo = 0;
            if(pago != null){
                if (pago.getLastNumRecibo() != null && !pago.getLastNumRecibo().equals(pago.getNumRecibo())) {
                    numeroRecibo = new Long(pago.getLastNumRecibo());
                } else {
                    numeroRecibo = new Long(pago.getNumRecibo()!=null?pago.getNumRecibo():"0");
                }
            }
            turno.setNumeroRecibo(numeroRecibo);
            turno.setTipoMensaje(1);
            turno.setUsuario(this.usuario);

            List<SolicitudFolio> ss = (List<SolicitudFolio>)this.turno.getSolicitud().getSolicitudFolios();

            for(SolicitudFolio s: ss){

                Matricula matricula = new Matricula();
                matricula.setAccion(ACCION_EDICION);
                matricula.setAno(this.turno.getAnio());
                matricula.setEnviado(Constantes.NO_ENVIADO);
                matricula.setFechaEvento(this.turno.getSolicitud().getFecha());
                matricula.setIdCirculo(this.turno.getIdCirculo());
                matricula.setIdProceso(this.turno.getIdProceso());
                matricula.setIdTurno(this.turno.getIdTurno());
                matricula.setNumeroMatricula(Long.parseLong(s.getIdMatricula().split("-")[1]));
                matricula.setTipoMensaje(1);

                turno.addMatricula(matricula);
            }

            turnos.add(turno);
        }catch(Exception e){
        }
    }

    /**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un turno para el mensaje.
     */
    @SuppressWarnings("unchecked")
    private void addTurnoCorreccion(){
        try{
            Turno turno = new Turno();
            turno.setAno(this.turno.getAnio());
            turno.setCiudad(this.turno.getSolicitud().getCirculo().getOficinaOrigen().getVereda().getMunicipio().getNombre());
            turno.setDescripcionInstrumento(this.turno.getDescripcion());
            turno.setEnviado(Constantes.NO_ENVIADO);

            turno.setFechaDocumento(this.turno.getSolicitud().getFecha());

            turno.setIdCirculo(this.turno.getIdCirculo());
            turno.setIdCirculoPadre(this.turno.getIdCirculo());
            turno.setIdProceso(this.turno.getIdProceso());
            turno.setIdProcesoPadre(this.turno.getIdProceso());
            turno.setIdTuno(this.turno.getIdTurno());
            turno.setIdTurnoPadre(this.turno.getIdTurno());
            turno.setSolicitante(this.turno.getSolicitud().getCiudadano().getApellido1());
            turno.setNumeroRecibo(0);
            turno.setTipoMensaje(1);
            turno.setUsuario(this.usuario);

            List<SolicitudFolio> ss = (List<SolicitudFolio>)this.turno.getSolicitud().getSolicitudFolios();

            for(SolicitudFolio s: ss){

                Matricula matricula = new Matricula();
                matricula.setAccion(ACCION_EDICION);
                matricula.setAno(this.turno.getAnio());
                matricula.setEnviado(Constantes.NO_ENVIADO);
                matricula.setFechaEvento(this.turno.getSolicitud().getFecha());
                matricula.setIdCirculo(this.turno.getIdCirculo());
                matricula.setIdProceso(this.turno.getIdProceso());
                matricula.setIdTurno(this.turno.getIdTurno());
                matricula.setNumeroMatricula(Long.parseLong(s.getIdMatricula().split("-")[1]));
                matricula.setTipoMensaje(1);

                turno.addMatricula(matricula);
            }

            turnos.add(turno);
        }catch(Exception e){
        }
    }

/**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un turno para el mensaje.
     */
    @SuppressWarnings("unchecked")
    private void addTurnoDevolucion(){
        try{
            Turno turno = new Turno();
            turno.setAno(this.turno.getAnio());
            turno.setCiudad(this.turno.getSolicitud().getCirculo().getOficinaOrigen().getVereda().getMunicipio().getNombre());
            turno.setDescripcionInstrumento(this.turno.getDescripcion());
            turno.setEnviado(Constantes.NO_ENVIADO);

            turno.setFechaDocumento(this.turno.getSolicitud().getFecha());

            turno.setIdCirculo(this.turno.getIdCirculo());
            turno.setIdCirculoPadre(this.turno.getIdCirculo());
            turno.setIdProceso(this.turno.getIdProceso());
            turno.setIdProcesoPadre(this.turno.getIdProceso());
            turno.setIdTuno(this.turno.getIdTurno());
            turno.setIdTurnoPadre(this.turno.getIdTurno());
            if(this.turno.getSolicitud().getCiudadano() == null){
                SolicitudDevolucion sd = (SolicitudDevolucion) this.turno.getSolicitud();
                if(sd.getSolicitantes() != null){
                    Solicitante s = (Solicitante) sd.getSolicitantes().get(0);
                    turno.setSolicitante(s.getCiudadano().getApellido1());
                }
            }else{
                turno.setSolicitante(this.turno.getSolicitud().getCiudadano().getApellido1());
            }
            
            turno.setNumeroRecibo(0);
            turno.setTipoMensaje(1);
            turno.setUsuario(this.usuario);

            List<SolicitudFolio> ss = (List<SolicitudFolio>)this.turno.getSolicitud().getSolicitudFolios();

            for(SolicitudFolio s: ss){

                Matricula matricula = new Matricula();
                matricula.setAccion(ACCION_EDICION);
                matricula.setAno(this.turno.getAnio());
                matricula.setEnviado(Constantes.NO_ENVIADO);
                matricula.setFechaEvento(this.turno.getSolicitud().getFecha());
                matricula.setIdCirculo(this.turno.getIdCirculo());
                matricula.setIdProceso(this.turno.getIdProceso());
                matricula.setIdTurno(this.turno.getIdTurno());
                matricula.setNumeroMatricula(Long.parseLong(s.getIdMatricula().split("-")[1]));
                matricula.setTipoMensaje(1);

                turno.addMatricula(matricula);
            }

            turnos.add(turno);
        }catch(Exception e){
        }
    }

/**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un turno para el mensaje.
     */
    @SuppressWarnings("unchecked")
    private void addTurnoConsulta(){
        try{
            Turno turno = new Turno();
            turno.setAno(this.turno.getAnio());
            turno.setCiudad(this.turno.getSolicitud().getCirculo().getOficinaOrigen().getVereda().getMunicipio().getNombre());
            turno.setDescripcionInstrumento(this.turno.getDescripcion());
            turno.setEnviado(Constantes.NO_ENVIADO);

            turno.setFechaDocumento(this.turno.getSolicitud().getFecha());

            turno.setIdCirculo(this.turno.getIdCirculo());
            turno.setIdCirculoPadre(this.turno.getIdCirculo());
            turno.setIdProceso(this.turno.getIdProceso());
            turno.setIdProcesoPadre(this.turno.getIdProceso());
            turno.setIdTuno(this.turno.getIdTurno());
            turno.setIdTurnoPadre(this.turno.getIdTurno());
            turno.setSolicitante(this.turno.getSolicitud().getCiudadano().getApellido1());
            turno.setNumeroRecibo(0);
            turno.setTipoMensaje(1);
            turno.setUsuario(this.usuario);

            List<SolicitudFolio> ss = (List<SolicitudFolio>)this.turno.getSolicitud().getSolicitudFolios();

            for(SolicitudFolio s: ss){

                Matricula matricula = new Matricula();
                matricula.setAccion(ACCION_EDICION);
                matricula.setAno(this.turno.getAnio());
                matricula.setEnviado(Constantes.NO_ENVIADO);
                matricula.setFechaEvento(this.turno.getSolicitud().getFecha());
                matricula.setIdCirculo(this.turno.getIdCirculo());
                matricula.setIdProceso(this.turno.getIdProceso());
                matricula.setIdTurno(this.turno.getIdTurno());
                matricula.setNumeroMatricula(Long.parseLong(s.getIdMatricula().split("-")[1]));
                matricula.setTipoMensaje(1);

                turno.addMatricula(matricula);
            }

            turnos.add(turno);
        }catch(Exception e){
        }
    }

    /**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un turno para el mensaje.
     */
    @SuppressWarnings("unchecked")
    private void addTurnoReparto(){
        try{
            Turno turno = new Turno();
            turno.setAno(this.turno.getAnio());
            turno.setCiudad(this.turno.getSolicitud().getCirculo().getOficinaOrigen().getVereda().getMunicipio().getNombre());
            turno.setDescripcionInstrumento(this.turno.getDescripcion());
            turno.setEnviado(Constantes.NO_ENVIADO);

            turno.setFechaDocumento(this.turno.getSolicitud().getFecha());

            turno.setIdCirculo(this.turno.getIdCirculo());
            turno.setIdCirculoPadre(this.turno.getIdCirculo());
            turno.setIdProceso(this.turno.getIdProceso());
            turno.setIdProcesoPadre(this.turno.getIdProceso());
            turno.setIdTuno(this.turno.getIdTurno());
            turno.setIdTurnoPadre(this.turno.getIdTurno());
            turno.setSolicitante(this.turno.getSolicitud().getCiudadano().getApellido1());
            turno.setNumeroRecibo(0);
            turno.setTipoMensaje(1);
            turno.setUsuario(this.usuario);

            List<SolicitudFolio> ss = (List<SolicitudFolio>)this.turno.getSolicitud().getSolicitudFolios();

            for(SolicitudFolio s: ss){

                Matricula matricula = new Matricula();
                matricula.setAccion(ACCION_EDICION);
                matricula.setAno(this.turno.getAnio());
                matricula.setEnviado(Constantes.NO_ENVIADO);
                matricula.setFechaEvento(this.turno.getSolicitud().getFecha());
                matricula.setIdCirculo(this.turno.getIdCirculo());
                matricula.setIdProceso(this.turno.getIdProceso());
                matricula.setIdTurno(this.turno.getIdTurno());
                matricula.setNumeroMatricula(Long.parseLong(s.getIdMatricula().split("-")[1]));
                matricula.setTipoMensaje(1);

                turno.addMatricula(matricula);
            }

            turnos.add(turno);
        }catch(Exception e){
        }
    }

/**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un estado para repartos a lista de turnos.
     */
    @SuppressWarnings("unchecked")
    private void addEstadoReparto(int aIdFase) throws ParseException{
        Estado estado = new Estado();
        estado.setAno(this.turno.getAnio());
        estado.setEnviado(Constantes.NO_ENVIADO);
        estado.setFase(aIdFase);

        estado.setFechaCambio(DateFormatUtil.parse(DateFormatUtil.LONG_DATETIME_FORMAT,
                DateFormatUtil.format(DateFormatUtil.LONG_DATETIME_FORMAT,new Date())));

        estado.setIdCirculo(this.turno.getIdCirculo());
        estado.setIdProceso(this.turno.getIdProceso());
        estado.setIdTurno(this.turno.getIdTurno());
        estado.setUsuario(usuario);
        estado.setTipoMensaje(1);
        turnos.add(estado);
    }

}

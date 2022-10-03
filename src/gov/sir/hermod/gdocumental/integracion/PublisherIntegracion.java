/*
 * @author Fernando Padilla Velez
 * @change Acta - Requerimiento No 178 - Integración Gestión Documental y SIR,
 *         Clase para el envio de mensajes al sistema de gestion documental.
 */

package gov.sir.hermod.gdocumental.integracion;

import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.Turno;

import gov.sir.core.util.DateFormatUtil;
import gov.sir.gdocumental.comun.Constantes;
import gov.sir.gdocumental.comun.entidades.Anulado;
import gov.sir.gdocumental.comun.entidades.Certificado;
import gov.sir.gdocumental.comun.entidades.Estado;
import gov.sir.gdocumental.comun.entidades.Matricula;
import gov.sir.gdocumental.negocio.SGDocumentalJMS;

import gov.sir.hermod.HermodProperties;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PublisherIntegracion {

    private Turno turno;

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    private List turnos;

    private String usuario;

    /**
    * @author Fernando Padilla Velez
    * @change Mantis 5383 Acta - Requerimiento No 200 - Reparto Sir SGD ETB2,
    *         Se agrega esta fragmento de codigo para la publicacion del mensaje del reparto.
    */
    String circulos;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    private static final int ID_FASE_SOLICITUD  = 1;
    private static final int ID_FASE_FINALIZADO = 2;

    private static final String ACCION_ADICION     = "1";
    private static final String ACCION_EDICION     = "2";
    private static final String ACCION_ELIMINACION = "3";

    public PublisherIntegracion(){
        turnos = new ArrayList();
        /**
        * @author Fernando Padilla Velez
        * @change Mantis 5383 Acta - Requerimiento No 200 - Reparto Sir SGD ETB2,
        *         Se agrega esta fragmento de codigo para la publicacion del mensaje del reparto.
        */
        HermodProperties hp = HermodProperties.getInstancia();
        circulos = hp.getProperty(HermodProperties.HERMOD_SGD_CIRCULOS);
    }


    /**
     * @author Fernando Padilla Velez
     * @see metodo encargado de la creacion del hilo para la publicación del mensaje.
     */
    public void enviar(){

        if(circulos.indexOf(this.turno.getIdCirculo()) != -1){
            boolean banderaProcesoCertificados = true;
            if(this.turno != null){
                if(this.turno.getIdProceso() == 1 &&
                   (this.getTurno().getSolicitud().getSolicitudesPadres() == null ||
                    this.getTurno().getSolicitud().getSolicitudesPadres().size() == 0)){

                    if("INMEDIATO".equals(this.turno.getUltimaRespuesta())){
                        banderaProcesoCertificados = false;
                    }
                }
                if(banderaProcesoCertificados){
                    addTurno();
                    if(this.turno.getIdProceso() == 1 &&
                       this.getTurno().getSolicitud().getSolicitudesPadres() != null &&
                        this.getTurno().getSolicitud().getSolicitudesPadres().size() > 0){
                            addCertificado();
                        }

                        addEstado();
                        addAnulado();
                        /**
                        * @author Fernando Padilla Velez
                        * @change Mantis 5383 Acta - Requerimiento No 200 - Reparto Sir SGD ETB2,
                        *         Se agrega esta fragmento de codigo para la publicacion del mensaje del reparto.
                        */
                        crearHiloMensaje();
                }
            }
        }
    }


    /**
    * @author Fernando Padilla Velez
    * @see Metodo encargado de enviar un mensaje de estado solo para reparto.
    * @change Mantis 5383 Acta - Requerimiento No 200 - Reparto Sir SGD ETB2,
    *         Se agrega esta fragmento de codigo para la publicacion del mensaje del reparto.
    */
    public void enviarReparto(){
        try {
            if(circulos.indexOf(this.turno.getIdCirculo()) != -1){
                addEstadoReparto(20);
                crearHiloMensaje();
            }
        } catch (ParseException ex) {
            Logger.getLogger(PublisherIntegracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @author Fernando Padilla Velez
     * @see metodo encargado de la creacion del hilo para la publicación del mensaje.
     * @change Mantis 5383 Acta - Requerimiento No 200 - Reparto Sir SGD ETB2,
     *         Se agrega esta fragmento de codigo para la publicacion del mensaje del reparto.
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
     * @see metodo encargado de agregar un turno para el mensaje.
     */
    @SuppressWarnings("unchecked")
    private void addTurno(){
        try{
            gov.sir.gdocumental.comun.entidades.Turno turno = new gov.sir.gdocumental.comun.entidades.Turno();
            turno.setAno(this.turno.getAnio());
            //turno.setAnoPadre(aTurno.get);proceso segregacion
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
            if(!(this.turno.getSolicitud() instanceof SolicitudRepartoNotarial || this.turno.getSolicitud() instanceof SolicitudRestitucionReparto)){
                turno.setSolicitante(this.turno.getSolicitud().getCiudadano().getApellido1());
            }
            

            if(this.turno.getSolicitud() instanceof SolicitudCertificado){
                SolicitudCertificado sc = (SolicitudCertificado)this.turno.getSolicitud();
                if(sc.getDocumento() != null){
                    turno.setFechaDocumento(sc.getDocumento().getFecha());
                    turno.setInstrumento(sc.getDocumento().getTipoDocumento().toString());
                    turno.setDescripcionInstrumento(sc.getDocumento().getComentario());
                    turno.setNumeroDocumento(sc.getDocumento().getNumero());
                    /*
                     * @author: Fernando Padilla Velez
                     * @change: caso mantis: 5357: Acta - Requerimiento No 205,
                     *          Se modifica por el nombre de la oficina origen, ya que, se estaba seteando el id del circulo.
                     */
                    if(sc.getDocumento().getOficinaInternacional() == null){
                        turno.setOficinaOrigen(sc.getDocumento().getOficinaOrigen().getNombre());
                    }else{
                        turno.setOficinaOrigen(sc.getDocumento().getOficinaInternacional());
                    }
                }
            }

            if(this.turno.getSolicitud() instanceof SolicitudRegistro){
                SolicitudRegistro sr = (SolicitudRegistro)this.turno.getSolicitud();
                if(sr.getDocumento() != null){
                    turno.setFechaDocumento(sr.getDocumento().getFecha());
                    turno.setFechaRadicacion(sr.getDocumento().getFecha());
                    turno.setInstrumento(sr.getDocumento().getTipoDocumento().getIdTipoDocumento());
                    turno.setDescripcionInstrumento(sr.getDocumento().getComentario());
                    turno.setNumeroDocumento(sr.getDocumento().getNumero());
                     /*
                     * @author: Fernando Padilla Velez
                     * @change: caso mantis: 5357: Acta - Requerimiento No 205,
                     *          Se modifica por el nombre de la oficina origen, ya que, se estaba seteando el id del circulo.
                     */
                    if(sr.getDocumento().getOficinaInternacional() == null){
                        turno.setOficinaOrigen(sr.getDocumento().getOficinaOrigen().getNombre());
                    }else{
                        turno.setOficinaOrigen(sr.getDocumento().getOficinaInternacional());
                    }
                }
            }

            /*
             * @author: Fernando Padilla Velez
             * @change: caso mantis: 5357: Acta - Requerimiento No 205,
             *          Se modifica, ya que, no se estaba seteando el numero de recibo
             *          en caso que no exista se seteará 0.
             */
            Pago pago = UtilidadesBD.consultarPago(this.turno.getSolicitud().getIdSolicitud());
            long numeroRecibo;
            if(pago != null){
                if (pago.getLastNumRecibo() != null && !pago.getLastNumRecibo().equals(pago.getNumRecibo())) {
                    numeroRecibo = new Long(pago.getLastNumRecibo());
                } else {
                    numeroRecibo = new Long(pago.getNumRecibo()!=null?pago.getNumRecibo():"0");
                }
            }else{
                numeroRecibo = new Long("0");
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
     * @see metodo encargado de establecer el estado de la fase del turno.
     */
    private void addEstado(){
        if(this.turno.getIdFase().indexOf("SOLICITUD") != -1 || 
           this.turno.getIdFase().indexOf("CONFRONTAR") != -1 ||
           this.turno.getIdFase().indexOf("COR_REVISION_ANALISIS") != -1 ||
           this.turno.getIdFase().indexOf("CON_CONSULTA") != -1 ||
           this.turno.getIdFase().indexOf("REP_REPARTO") != -1 ||
           this.turno.getIdFase().indexOf("RES_ANALISIS") != -1){

            addEstado(PublisherIntegracion.ID_FASE_SOLICITUD);

        }else if(this.turno.getIdFase().indexOf("FINALIZADO") != -1 ||
                 this.turno.getIdFase().indexOf("ENTREGA") != -1){
            addEstado(PublisherIntegracion.ID_FASE_FINALIZADO);
        }
    }

    /**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un estado a lista de turnos.
     */
    @SuppressWarnings("unchecked")
    private void addEstado(int aIdFase){
        Estado estado = new Estado();
        estado.setAno(this.turno.getAnio());
        estado.setEnviado(Constantes.NO_ENVIADO);
        estado.setFase(aIdFase);
        estado.setFechaCambio(this.turno.getSolicitud().getFecha());
        estado.setIdCirculo(this.turno.getIdCirculo());
        estado.setIdProceso(this.turno.getIdProceso());
        estado.setIdTurno(this.turno.getIdTurno());
        estado.setUsuario(usuario);
        estado.setTipoMensaje(1);
        turnos.add(estado);
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

    /**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un turno de certificado a lista de turnos.
     */
    @SuppressWarnings("unchecked")
    private void addCertificado(){

        if(turno.getIdProceso() == 1){
            Certificado certificado = new Certificado();
            certificado.setAccion(ACCION_ADICION);
            certificado.setAno(turno.getAnio());
            certificado.setAnoTurno(turno.getAnio());
            certificado.setEnviado(Constantes.NO_ENVIADO);
            certificado.setFechaEvento(turno.getFechaInicio());

            certificado.setIdCirculo(turno.getIdCirculo());
            certificado.setIdCirculoTurno(turno.getIdCirculo());
            certificado.setIdProceso(turno.getIdProceso());
            certificado.setIdProcesoTurno(turno.getIdProceso());
            certificado.setIdTurno(turno.getIdTurno());
            certificado.setIdTurnoTurno(turno.getIdTurno());
            certificado.setTipoMensaje(1);

            turnos.add(certificado);            
        }
   }        

     /**
     * @author Fernando Padilla Velez
     * @see metodo encargado de agregar un turno anulado a la lista de turnos.
     */
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

}

package gov.sir.core.negocio.acciones.administracion;


import gov.sir.core.eventos.administracion.EvnAdministracionFotocopiasLiquidCancelVencimiento;
import gov.sir.core.eventos.administracion.EvnRespAdministracionFotocopiasLiquidCancelVencimiento;
import gov.sir.core.negocio.acciones.excepciones.ANTurnoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.ProcesoPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFotocopiaCancelacionNotaInformativa;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

/**
 */
public class AnAdministracionFotocopiasLiquidCancelVencimiento extends SoporteAccionNegocio {

    /**
     * PrintService
     * @since 2005/05/25
     */
          private PrintJobsInterface printJobs;


    /**
     * Instancia de Fenrir
     */
    private FenrirServiceInterface fenrir;

    /**
     * Instancia de Forseti
     */
    private ForsetiServiceInterface forseti;

    /**
     * Instancia del service locator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     *Constructor de la clase ANTurno
     *Es el encargado de invocar al Service Locator y pedirle una instancia
     *del servicio que necesita
     */
    public AnAdministracionFotocopiasLiquidCancelVencimiento() throws EventoException {
        super();
        service = ServiceLocator.getInstancia();

        try {
            hermod = (HermodServiceInterface) service.getServicio(
                    "gov.sir.hermod");

            if (hermod == null) {
                throw new ServicioNoEncontradoException(
                    "Servicio Hermod no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException(
                "Error instanciando el servicio Hermod",e);
        }

        if (hermod == null) {
            throw new ServicioNoEncontradoException(
                "Servicio Hermod no encontrado");
        }

        try {
            fenrir = (FenrirServiceInterface) service.getServicio(
                    "gov.sir.fenrir");

            if (fenrir == null) {
                throw new ServicioNoEncontradoException(
                    "Servicio fenrir no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException(
                "Error instanciando el servicio fenrir",e);
        }

        if (fenrir == null) {
            throw new ServicioNoEncontradoException(
                "Servicio fenrir no encontrado");
        }

        try {
            forseti = (ForsetiServiceInterface) service.getServicio(
                    "gov.sir.forseti");

            if (forseti == null) {
                throw new ServicioNoEncontradoException(
                    "Servicio forseti no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException(
                "Error instanciando el servicio forseti",e);
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException(
                "Servicio forseti no encontrado");
        }

        // ------------- PrintingService Activation
        try {
            printJobs = (PrintJobsInterface) service.getServicio(
                    "gov.sir.print");

            if (printJobs == null) {
                throw new ServicioNoEncontradoException(
                    "Servicio PrintJobs no encontrado");
            }
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio PrintJobs",
                e);
        }

        if (printJobs == null) {
            throw new ServicioNoEncontradoException(
                "Servicio PrintJobs no encontrado");
        }
      // ------------- ----------------------------------

    }

    /**
     * Recibe un evento sobre un turno y devuelve un evento de respuesta
     * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnTurno</CODE>
     * @throws EventoException cuando ocurre un problema que no se pueda manejar
     * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespTurno</CODE>
     * @see gov.sir.core.eventos.comun.EvnTurno
     * @see gov.sir.core.eventos.comun.EvnRespTurno
     */
    public EventoRespuesta perform(Evento e) throws EventoException {

      EvnAdministracionFotocopiasLiquidCancelVencimiento evento
          = (EvnAdministracionFotocopiasLiquidCancelVencimiento) e;

      if( EvnAdministracionFotocopiasLiquidCancelVencimiento.ACCION_LISTAR
          .equals( evento.getTipoEvento() ) ) {
          return listar( evento );
      } else if( EvnAdministracionFotocopiasLiquidCancelVencimiento.ACCION_VERDETALLES
         .equals( evento.getTipoEvento() ) ) {
          return verDetalles( evento );
      } else if( EvnAdministracionFotocopiasLiquidCancelVencimiento.ACCION_NEGAR
        .equals( evento.getTipoEvento() ) ) {
         return negar( evento );
      }

        return null;
    }

    /**
     * Se listan los turnos de acuerdo a la fase, estacion y proceso
     * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnTurno</CODE>
     * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespTurno</CODE>
     * @throws ANTurnoException
     */
    private EvnRespAdministracionFotocopiasLiquidCancelVencimiento
    listar(EvnAdministracionFotocopiasLiquidCancelVencimiento evento)
    throws EventoException {

       final double DAY_DELTATIME = ( 1.0d ) * ( 24 * 60 * 60 ) * 1000;
       final String REFERENCE_DATE_FORMAT = "dd/MM/yyyy";


       long currentDateInMilliseconds   = 0L;
       long referenceDateInMilliseconds = 0L;

       currentDateInMilliseconds   = System.currentTimeMillis();
       
       Date referenceDate = null;
       
       //Se busca en lookType  y se saca el # de dias para que se declare vencido
       List listCodes = null;
       try {
    	   listCodes= hermod.getOPLookupCodes(COPLookupTypes.FOTOCOPIAS);
	   } catch (Throwable e1) {
		   Log.getInstance().debug(AnAdministracionFotocopiasLiquidCancelVencimiento.class, e1.getMessage() );
	       throw new EventoException( e1.getMessage() );
	   }
	   
	   //recorrer los codes buscando el que buscamos que es
	   String diasStr ="0.0d";
	   OPLookupCodes lookUp;
       for(Iterator it = listCodes.iterator(); it.hasNext();){
               lookUp = (OPLookupCodes) it.next();
               if(lookUp.getCodigo().equals(COPLookupCodes.DIAS_VENCIMIENTO)){
            	   diasStr = lookUp.getValor();
               }
       }
       
       double dias = Double.parseDouble(diasStr);
       
       //Se hace la conversion para que quede en miliseconds
       
       double initialThreshold = 0d;
       double currentTimeMillis = System.currentTimeMillis();
       initialThreshold  = ( dias ) * DAY_DELTATIME; // 2 meses antes aprox

       long milliseconds = (long)( currentTimeMillis - initialThreshold ) ;
       java.util.Date defaultSelectedDate = new java.util.Date( milliseconds );
       String dateAsString = DateFormatUtil.format( defaultSelectedDate );

       try {
         referenceDate = DateFormatUtil.parse( dateAsString );
       }
       catch (Exception e) {
         Log.getInstance().error(AnAdministracionFotocopiasLiquidCancelVencimiento.class,e);
         throw new EventoException("Verifique que el formato de la fecha tomada como referencia tiene el \n"
        		 				+ "siguiente formato: " + REFERENCE_DATE_FORMAT + ".");

       }	
        
       referenceDateInMilliseconds = referenceDate.getTime();

       // sumar lo de un dia, ya que el componente calendario pasa 0 en las horas del día
       referenceDateInMilliseconds = (long)( referenceDateInMilliseconds + DAY_DELTATIME );

       double deltaTimeInDays = 0d;
       deltaTimeInDays = ( currentDateInMilliseconds - referenceDateInMilliseconds ) * ( 1/1000.0f ) * ( 1/60.0f ) * ( 1 /60.0f ) * ( 1/24f ) ;

    // 2 minutos // 2 *( 1/24d ) * (1/60d)
    // 12 horas //12d * ( 1 / 24.0d ) ; // horas * frac-hora
        // declare parameters
        Circulo circulo = null;
        circulo = evento.getCirculo();


        // declare results
        List turnosFotocopiasConPagoVencido = null;


        try {
          turnosFotocopiasConPagoVencido = hermod.getTurnosFotocopiasConPagoVencido( circulo, deltaTimeInDays );
        }
        catch (Throwable ex) {
          Log.getInstance().debug(AnAdministracionFotocopiasLiquidCancelVencimiento.class, ex.getMessage() );
          throw new EventoException( ex.getMessage() );
        }

        // luego se pasa la consulta por un filtro,
        // porque hay solicitudes con estado indeseable
        turnosFotocopiasConPagoVencido = filtrarTurnosFotocopiasConPagoVencido( turnosFotocopiasConPagoVencido );

        EvnRespAdministracionFotocopiasLiquidCancelVencimiento eventoResp
            = new EvnRespAdministracionFotocopiasLiquidCancelVencimiento( null, turnosFotocopiasConPagoVencido, EvnRespAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_LISTAR_OK );
        eventoResp.setDateString(dateAsString);
        
        return eventoResp;
    }

    /**
     * filtrarTurnosFotocopiasConPagoVencido
     *
     * @param turnosFotocopiasConPagoVencido List
     * @return List
     */
    private List
    filtrarTurnosFotocopiasConPagoVencido( List turnosFotocopiasConPagoVencido ) {

        Iterator iterator;
        iterator = turnosFotocopiasConPagoVencido.iterator();
        boolean test = false;
        for(;iterator.hasNext();) {
            Turno turno = (Turno)iterator.next();
            try {
                test = hermod.isValidTurnoSAS( turno.getIdWorkflow() );
                if( !test ) {
                    iterator.remove();
                }
            }
            catch (Throwable ex) {
                Log.getInstance().error(AnAdministracionFotocopiasLiquidCancelVencimiento.class,"Errores al realizar consulta solicitud fotoc. pago vencido" + ex.getMessage() );
                ex.printStackTrace();
            }

        }
        return turnosFotocopiasConPagoVencido;
    }


    private EvnRespAdministracionFotocopiasLiquidCancelVencimiento
    negar( EvnAdministracionFotocopiasLiquidCancelVencimiento evento )
    throws EventoException {
        // declare parameters
        Circulo circulo = null;
        circulo = evento.getCirculo();


        // declare results
        Turno turnoSelected = null;


        try {
            // obtener turno + fase ------------------------------------------
          turnoSelected = evento.getTurno();
           // TODO: cambiar: evento.getFase()
          String faseId = turnoSelected.getIdFase();
          Fase fase = new Fase();
          fase.setID( turnoSelected.getIdFase() );

          // ---------------------------------------------------------------

          // avanzar el turno ------------------------------------------

          avanzarTurno: {
          	
          	
          	
			//3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
			//del turno desde esta fase. 
			try
			{
			   Hashtable tablaAvance = new Hashtable(2);
			   tablaAvance.put(Processor.RESULT, "NEGAR");
			   tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
			   hermod.validarNotaInformativaAvanceTurno(fase,tablaAvance,evento.getTurno());
			}
			catch(Throwable t)
			{
				throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}
        
          	
          	
              try {

                final String respuestaWf = "NEGAR";
                String USUARIO_INICIADOR = ( null != evento.getUsuarioSIR() )?(""+evento.getUsuarioSIR().getUsername()):("");
                // call hermod service
                        //Se crea la tabla para el paso de los parámetros.

                if( "".equals( USUARIO_INICIADOR ) || null == USUARIO_INICIADOR )
                    throw new HermodException( "El usuario no se ha regostrado en la capa web." + this.getClass().getName() );


                // Estacion estacion = evento.getEstacion();
                Hashtable parameters = new Hashtable();
                parameters.put( Processor.RESULT            , respuestaWf               );
                parameters.put( CInfoUsuario.USUARIO_SIR    , USUARIO_INICIADOR         );
                // parameters.put( Processor.USUARIO_INICIADOR , USUARIO_INICIADOR         );

                 hermod.avanzarTurnoNuevoNormal( turnoSelected, fase, parameters, evento.getUsuarioSIR() );
            }
            catch (HermodException e) {
                    Log.getInstance().error(AnAdministracionFotocopiasLiquidCancelVencimiento.class,e.getMessage(), e);
                    throw new EventoException(e.getMessage(), e);
            }
            catch (Throwable e) {
                    Log.getInstance().error(AnAdministracionFotocopiasLiquidCancelVencimiento.class,e.getMessage(), e);
                    throw new EventoException(e.getMessage(), e);
            }


          } // :avanzarTurno

          //TODO: si se debe o no imprimir


          printNotas: {

            List listOfNotes = turnoSelected.getNotas();
            Iterator listOfNotesIterator = null;
            listOfNotesIterator = listOfNotes.iterator();

            Vector notas = new Vector();
            for(;listOfNotesIterator.hasNext();) {
              notas.add( listOfNotesIterator.next() );
            }
            String nombreCirculo = ( null != evento.getCirculo() )?( evento.getCirculo().getNombre() ):("");
            String turnoNombre = turnoSelected.getIdWorkflow();
            String sessionId = evento.getSessionId();
            turnoFocotopia_ImprimirNotaInformativa( notas, nombreCirculo,turnoNombre,"", sessionId );

          } // :printNotas


        }
        catch (Throwable ex) {
          Log.getInstance().error(AnAdministracionFotocopiasLiquidCancelVencimiento.class, ex.getMessage() ) ;
          throw new EventoException( ex.getMessage() );
        }

        EvnRespAdministracionFotocopiasLiquidCancelVencimiento eventoResp
            = new EvnRespAdministracionFotocopiasLiquidCancelVencimiento( turnoSelected, EvnRespAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_VERDETALLES_OK );

        return eventoResp;
    }


    private EvnRespAdministracionFotocopiasLiquidCancelVencimiento
    verDetalles( EvnAdministracionFotocopiasLiquidCancelVencimiento evento )
    throws EventoException {
        // declare parameters
        Circulo circulo = null;
        circulo = evento.getCirculo();

        // declare list4helpers
        List notasInf=new ArrayList();


        // declare results
        Turno turnoSelected = null;
        Fase  faseSelected  = null;



        Proceso proceso = new Proceso();
        proceso.setIdProceso( Integer.parseInt( CProceso.PROCESO_FOTOCOPIAS ) );
        //settear proceso como registro id = 6
        ProcesoPk id = new ProcesoPk();
        id.idProceso = proceso.getIdProceso();



        try {
          TurnoPk tID = new TurnoPk( evento.getTurnoId() );
          turnoSelected = hermod.getTurno( tID );

          // obtiene la fase en la cual esta actualmente;
          // en este caso está en LIQIDACION (FOT_LIQUIDACION)
          faseSelected = hermod.getFase( turnoSelected.getIdFase() );

          //llamar servicio hermod para obtener tipos notas infomativas.
          notasInf = hermod.getTiposNotaProceso( id, faseSelected.getID() );

        }
        catch( HermodException e ) {
           Log.getInstance().error(AnAdministracionFotocopiasLiquidCancelVencimiento.class,e.getMessage(), e);
           throw new EventoException( e.getMessage(), e );
        }
        catch( Throwable e ) {
           Log.getInstance().error(AnAdministracionFotocopiasLiquidCancelVencimiento.class,e.getMessage(), e);
           throw new EventoException( e.getMessage(), e );
        }

        // Obtener el numero de dias threshold para
        // consultar las solicitudes retrasadas
        // TODO: extraer de la db
        double initialThreshold = 0.2d;


        EvnRespAdministracionFotocopiasLiquidCancelVencimiento eventoResp
            = new EvnRespAdministracionFotocopiasLiquidCancelVencimiento(
                    turnoSelected
                  , faseSelected
                  , proceso
                  , EvnRespAdministracionFotocopiasLiquidCancelVencimiento.RESULTADO_VERDETALLES_OK
              );
        eventoResp.setTiposNotasInformativas( notasInf ); // utilizado por el helper de notas informativas
        eventoResp.setInitialThreshold( initialThreshold );
        return eventoResp;
    }





    private void
    turnoFocotopia_ImprimirNotaInformativa (
          Vector notas
        , String nombreCirculo
        , String turnoNombre
        , String matriculaNombre
        , String sessionId
    )
    throws Throwable {

             String fechaImpresion = this.getFechaImpresion();
             // fechaImpresion

             ImprimibleFotocopiaCancelacionNotaInformativa impRec;
             impRec = new ImprimibleFotocopiaCancelacionNotaInformativa( notas, nombreCirculo, turnoNombre, matriculaNombre, fechaImpresion , CTipoImprimible.NOTA_INFORMATIVA);
             // impRec = new ImprimibleRecibo( evento.getTurno(), circulo, fechaImpresion );

              String uid = sessionId;

           //IMPRIMIR EL RECIBO DE PAGO CORRESPONDIENTE

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

                   Bundle bundle = new Bundle(impRec);

                     try {
                       //se manda a imprimir el recibo por el identificador unico de usuario
                       printJobs.enviar(uid, bundle, maxIntentos, espera);
                     }
                     catch (Throwable t) {
                       t.printStackTrace();
                     }
           }
      // block:eof
      // block:sof
      protected String getFechaImpresion() {
          Calendar c = Calendar.getInstance();
          int dia;
          int ano;
          int hora;
          String min;
          String seg;
          String mes;

          dia = c.get(Calendar.DAY_OF_MONTH);
          mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
          ano = c.get(Calendar.YEAR);

          hora = c.get(Calendar.HOUR_OF_DAY);

          if (hora > 12) {
              hora -= 12;
          }

          min = formato(c.get(Calendar.MINUTE));
          seg = formato(c.get(Calendar.SECOND));

          String fechaImp = "Impreso el " + dia + " de " + mes + " de " + ano +
              " a las " + formato(hora) + ":" + min + ":" + seg + " " +
              DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

          return fechaImp;
  }
  protected String formato(int i) {
      if (i < 10) {
          return "0" + (new Integer(i)).toString();
      }

      return (new Integer(i)).toString();
  }

      // block: eof

}

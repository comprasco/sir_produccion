package gov.sir.core.negocio.acciones.registro;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.testamento.TestamentoSIR;
import co.com.iridium.generalSIR.negocio.validaciones.CalificacionSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionImprimibleSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import com.lowagie.text.Image;
import com.lowagie.text.Watermark;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import gov.sir.core.eventos.comun.EvnNotas;
import gov.sir.core.eventos.correccion.EvnCorreccion;
import gov.sir.core.eventos.comun.EvnRespPago;
import gov.sir.core.eventos.comun.EvnRespSeguridad;
import gov.sir.core.eventos.comun.EvnSeguridad;
import gov.sir.core.eventos.registro.EvnCalificacion;
import gov.sir.core.eventos.registro.EvnCalificacionReproduccionSellos;
import gov.sir.core.eventos.registro.EvnConfrontacion;
import gov.sir.core.eventos.registro.EvnRespCalificacion;
import gov.sir.core.eventos.registro.EvnRespCalificacionReproduccionSellos;
import gov.sir.core.eventos.registro.EvnRespConfrontacion;
import gov.sir.core.eventos.registro.EvnRespFolio;
import gov.sir.core.is21.Encriptador;
import static gov.sir.core.negocio.acciones.comun.ANNotas.getImage;
import gov.sir.core.negocio.acciones.comun.ANPago;
import static gov.sir.core.negocio.acciones.comun.ANPago.getImage;
import gov.sir.core.negocio.acciones.excepciones.AnotacionTemporalRegistroException;
import gov.sir.core.negocio.acciones.excepciones.AvanzarCalificacionException;
import gov.sir.core.negocio.acciones.excepciones.CancelarAnotacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.CerrarFolioException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaCalificacionFolioException;
import gov.sir.core.negocio.acciones.excepciones.CorreccionEncabezadoNoEfectuadoException;
import gov.sir.core.negocio.acciones.excepciones.ImpresionException;
import gov.sir.core.negocio.acciones.excepciones.ReproduccionSellosException;
import gov.sir.core.negocio.acciones.excepciones.SegregacionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TomarFoliosException;
import gov.sir.core.negocio.acciones.excepciones.TomarTurnoCalificacionHTException;
import gov.sir.core.negocio.acciones.excepciones.TomarTurnoRegistroException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.acciones.excepciones.AvanzarSinInformacionTemporalException;
import gov.sir.core.negocio.acciones.excepciones.ConfrontacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaTurnoExceptionException;
import gov.sir.core.negocio.acciones.excepciones.TramiteSuspensionException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CActo;
import gov.sir.core.negocio.modelo.constantes.CConfrontacion;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.CModoBloqueo;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CNota;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CQueries;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CRespuestaCalificacion;
import gov.sir.core.negocio.modelo.constantes.CSolicitudFolio;
import gov.sir.core.negocio.modelo.constantes.CSubtipoSolicitud;
import gov.sir.core.negocio.modelo.constantes.CTipoFormulario;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoRelacion;
import gov.sir.core.negocio.modelo.constantes.CTipoRevisionCalificacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFolioSimple;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormulario;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormularioTestamento;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleInscripcionTestamento;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacionA;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacionB;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.jdogenie.ActoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMP;
import gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMPPk;
import gov.sir.core.negocio.modelo.jdogenie.PrefabricadoPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoActoEnhanced;
import gov.sir.core.negocio.modelo.util.CiudadanoComparator;
import gov.sir.core.negocio.modelo.util.ComparadorAnotaciones;
import gov.sir.core.negocio.modelo.util.IDMatriculaFolioComparator;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.util.PdfCreator;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.util.TLSHttpClientComponent;
import gov.sir.core.web.PdfServlet;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.BloqueoFoliosHTException;
import gov.sir.core.web.acciones.registro.AWCalificacion;
import gov.sir.core.web.helpers.correccion.diff.utils.jxpath.FolioAbstractFactory;
import gov.sir.fenrir.FenrirProperties;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.ForsetiProperties;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodProperties;
import gov.sir.hermod.dao.impl.jdogenie.JDOLiquidacionesDAO;
import gov.sir.hermod.gdocumental.integracion.PublisherIntegracion;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;
import gov.sir.print.server.PrintJobsProperties;
import gov.sir.print.server.dao.FactoryException;
import gov.sir.print.server.dao.PrintFactory;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import org.apache.commons.jxpath.JXPathContext;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.smart.web.ProcesadorEventosNegocioProxy;
import org.auriga.util.ExceptionPrinter;

/**
 * @author jfrias
 * @author mmunoz
*/
public class ANCalificacion extends SoporteAccionNegocio {
	private ServiceLocator service = null;
        private PrintFactory impresion;
	private static final String REVISION_CALIFICACION = "REVISION_CALIFICACION";
        private boolean isREL = false;
	/**
	 * Instancia de PrintJobs
	 */
	private PrintJobsInterface printJobs;

	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;
   /** Constatne que define el tamaño de pulgada */
    private static final int INCH = 72;

    /** Constante que define el ancho de una hoja carta */
    private static final double LETTER_WIDTH = 8.5 * INCH;

    /** Constante que define el alto de una hoja carta */
    private static final double LETTER_HEIGHT = 11 * INCH;
	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;


	/**
	 * Instancia de Fenrir
	 */
	private FenrirServiceInterface fenrir;

	/**
	 *
	 */
	public ANCalificacion() throws EventoException {
		service = ServiceLocator.getInstancia();


		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");
			printJobs = (PrintJobsInterface)service.getServicio("gov.sir.print");
                        try {
                            impresion = PrintFactory.getFactory();
                        } catch (FactoryException e) {
                            PrintJobsException fe = new PrintJobsException("No fue posible obtener la fábrica concreta", e);
                            Log.getInstance().error(ANPago.class, fe.getMessage(), e);
                        }

		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio", e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio hermod no encontrado");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}

		if (fenrir == null) {
			throw new ServicioNoEncontradoException("Servicio fenrir no encontrado");
		}

		if (printJobs == null) {
			throw new ServicioNoEncontradoException("Servicio printJobs no encontrado");
		}

	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
	 */
    public EventoRespuesta perform(Evento ev) throws EventoException {
        EvnCalificacion evento = (EvnCalificacion) ev;

        if (evento.getTipoEvento().equals(EvnCalificacion.VALIDAR_APROBAR_CALIFICACION)) {
            return validarAprobarCalificacion(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.AVANZAR)) {
                return avanzar(evento, false);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.AVANZAR_DESBLOQUEAR)) {
                return avanzarInscripcionNuevo(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.AVANZAR_FIRMA)) {
                return avanzarFirmaNuevo(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.AVANZAR_REVISOR)) {
                return avanzarRevisor(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.DEVOLVER_FIRMA)) {
                return devolverFirmaNuevo(evento);
            }else if (evento.getTipoEvento().equals(EvnCalificacion.AVANZAR_ENTREGA)) {
                return avanzarEntrega(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.AVANZAR_TRAMITE_SUSP)) {
                //Se crea nuevo evento para requerimiento de tramite suspension
                return avanzarTramiteSuspension(evento); 
            }/*else if (evento.getTipoEvento().equals(EvnCalificacion.VISUALIZAR_PDF)) {
                return Visualizarpdf(evento);
            } */else if (evento.getTipoEvento().equals(EvnCalificacion.GRABAR_ANOTACIONES_TEMPORALMENTE)) {
                return grabarAnotacionesTemporal(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.AGREGAR_FOLIO_DIRECCION)) {
                return grabarDireccionTemporal(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.ELIMINAR_FOLIO_DIRECCION)) {
                return eliminarDireccionTemporal(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.CANCELAR_ANOTACION)) {
                return cancelarAnotacionTemporal(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.ELIMINAR_ANOTACION)) {
                return eliminarAnotacionTemporal(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.CREAR_FOLIO_ENGLOBE)) {
                return crearFolioEnglobe(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.TOMAR_TURNO)) {
                return tomarTurno(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.VALIDAR_TURNO_PARA_CALIFICACION)) {
                return validarTurnoParaCalificacion(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.DESASOCIAR_FOLIOS)) {
                return desasociarFolios(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.CONSULTA)) {
                return consultaTemporal(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.SEGREAR_MASIVO)) {
                return segregacionMasiva(ev);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.WF_CONFIRMAR_CORRECCION)) {
                return confirmarCorreccion(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.DEVOLVER)) {
                return devolverCalificacionNuevo(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.INSCRIPCION_PARCIAL)) {
                return inscripcionParcialNuevo(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.APROBAR_MAYOR_VALOR)) {
                return aprobarMayorValorNuevo(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.REGISTRAR_PAGO_EXCESO)) {
                return registrarPagoExceso(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.IMPRIMIR_FORMULARIO_CALIFICACION)) {
                return imprimirFormularioCalificacion(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.IMPRIMIR_FOLIO_TEMPORAL)) {
                return imprimirFolioTemporal(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.OBTENER_BLOQUEO_FOLIOS)) {
                return null;
            } else if (evento.getTipoEvento().equals(EvnCalificacion.CERRAR_FOLIOS)) {
                return cerrarFolios(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.ACTUALIZAR_FOLIO_CABIDA_LINDEROS)) {
                return actualizarFolioCabidaLinderos(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.ACTUALIZAR_FOLIO_COD_CATASTRAL)) {
                return actualizarFolioCodCatastral(evento);
            } else if (evento.getTipoEvento().equals(EvnCalificacion.ACTUALIZAR_FOLIO_DIRECCION)) {
                return actualizarFolioDireccion(evento);
        /**
         * @author: Cesar Ramirez
         * @change: 1245.HABILITAR.TIPO.PREDIO
         * Se asocia el tipo de evento para actualizar el tipo de predio.
         **/
        } else if(evento.getTipoEvento().equals(EvnCalificacion.ACTUALIZAR_FOLIO_TIPO_PREDIO)){
			return actualizarFolioTipoPredio(evento);
		} else if(evento.getTipoEvento().equals(EvnCalificacion.AVANZAR_CORRECCIONES)) {
			return avanzarCorrecciones(evento);
		} else if(evento.getTipoEvento().equals(EvnCalificacion.AVANZAR_ANTIGUO_SISTEMA)) {
			return avanzarAntiguoSistemaNuevo(evento);
		}  else if(evento.getTipoEvento().equals(EvnCalificacion.AVANZAR_ESPECIALIZADO)) {
			return avanzarEspecializado(evento);
		}  else if(evento.getTipoEvento().equals(EvnCalificacion.CONSULTAR_ULTIMOS_PROPIETARIOS)) {
			return consultarUltimosPropietarios(evento);
		} else if(evento.getTipoEvento().equals(EvnCalificacion.AGREGAR_CIUDADANO_ANOTACION)) {
			return validarCiudadanos(evento);
		}

		//Delegar el turno a confrontación
		else if(evento.getTipoEvento().equals(EvnCalificacion.CONFRONTACION)){
			return delegarConfrontacionNuevo(evento);
		}

		//Consulta si existen segregaciones en trámite
		else if (evento.getTipoEvento().equals(EvnCalificacion.CONSULTAR_SEG_ENG_EXISTENTES)) {
			return consultarSegEngExistentes(evento);
		}

		//Delegar el turno a correccion de encabezado
		else if (evento.getTipoEvento().equals(EvnCalificacion.WF_ENVIAR_CORRECCION_ENCABEZADO))
		{
			return delegarCorreccionEncabezado (evento);
		}

                // reproduccion sellos -------------------------------------------------------------
                else if( EvnCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENT.equals( evento.getTipoEvento() ) ) {
                    return calificacion_ReimpresionSellos_PrintSelected( evento );
                }
                else if( EvnCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_SEARCH_EVENT.equals( evento.getTipoEvento() ) ) {
                    return calificacion_ReimpresionSellos_Search( evento );
                }
				else if( EvnCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_MATRICULA.equals( evento.getTipoEvento() ) ) {
					return imprimirReproduccionMatricula( evento );
				}
                // ---------------------------------------------------------------------------------

		
		/**************************************************/
		/*          ELIMINACION SAS                       */
		/**************************************************/
		else if (evento.getTipoEvento().equals(EvnCalificacion.NEGAR_CORRECCION_ENCABEZADO))
		{
			return nuevoNegarCorreccionEncabezado (evento);
		}
		
		else if (evento.getTipoEvento().equals(EvnCalificacion.REALIZAR_CORRECCION_ENCABEZADO))
		{
			return nuevoRealizarCorreccionEncabezado (evento);
		}
		
		else if (evento.getTipoEvento().equals(AWCalificacion.DELEGAR_DIGITACION_MASIVA))
		{
			return delegarDigitacionMasivaNuevo (evento);
		}
		
		else if (evento.getTipoEvento().equals(AWCalificacion.ENTREGAR_REGISTRO))
		{
			return entregarRegistroNuevo (evento);
		}
          /* JALCAZAR Requerimiento MANTIS  0002225
           * se consulta si el evento es de tipo AWCalificacion.ENTREGAR_REGISTRO_MULT
           * si lo es, se procede a utilizar la funcion entregarRegistroNuevoMult (evento)
           */
        else if (evento.getTipoEvento().equals(AWCalificacion.ENTREGAR_REGISTRO_MULT))
		{
			return entregarRegistroNuevoMult (evento);
		}else if (evento.getTipoEvento().equals(AWCalificacion.PAGE_CALIFICACION_BTNDESHACERCAMBIOS_ACTION))
		{
			EventoRespuesta local_Result;
	        tomarFolios( evento );
	        local_Result = calificacion_DeshacerCambios( evento );
	        return local_Result;
		}/**Devolver testamento*/
		else if(evento.getTipoEvento().equals(EvnCalificacion.DEVOLVER_FIRMA_TESTAMENTO)){
			return devolverTestamento(ev);
		}
		
		return null;
	}
       
            public List ObtenerReproduccionSellos(EvnCalificacion evento) throws EventoException {
            List Reproduccion  = null ;
            try{
                Turno turno = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());
                Reproduccion = hermod.getListReproduccionSellos(turno.getIdWorkflow(),turno.getIdCirculo(),0);
            }catch(Throwable ex){
                Reproduccion = new ArrayList();
            }
            if(Reproduccion == null){
                Reproduccion = new ArrayList();
            }
            return Reproduccion;
            }
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
            result.add( t0_Folio_ListAnotacionArray[i] );
          }

          return result;
        }
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
    		
                } catch (ClassNotFoundException cnfe) {
    		
                }

    		return obj;
    	}	
    public static Long
    getNullableLong( boolean treatBlankAsNull ) {
      return( ( treatBlankAsNull )?( new Long (0) ):( null ) );
    }
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

     public static String
   Nvl( String string , String replaceIfNull ) {
      return ( null == string )?( replaceIfNull ):( string );
   } // end-method: Nvl

   public static Long
   Nvl( Long num , Long replaceIfNull ) {
      return ( null == num )?( replaceIfNull ):( num );
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
        /**
         * Imprimir aparte el formulario de correcciones
         * */
        public void  VisualizarFormularioCalificacion( EvnCorreccion evento ,HttpServletResponse response,HttpServletRequest request)
        throws EventoException {

      Fase fase = evento.getFase();
      Turno turno = evento.getTurno();
      Turno turnoTemp = (Turno)copy(turno);  /**Se usa un temporal para no perder la información original */
      Solicitud solicitud = turno.getSolicitud();
     Turno turnoAnterior = solicitud.getTurnoAnterior();
      Testamento testamento = null;
      List<Map> salvedadesTest = new ArrayList();
      Circulo circulo = evento.getCirculo();
      gov.sir.core.negocio.modelo.Usuario usr = null;
      List historia = turno.getHistorials();
        int i = 0;
        while (i < historia.size()) {
            TurnoHistoria historial = (TurnoHistoria) historia.get(i);
            if (historial.getFase().equals(CFase.COR_CORRECCION_SIMPLE)) {
                if (i != historia.size() - 1) {
                    usr = historial.getUsuarioAtiende();
                }

            }
            i++;
        }
      List folios = null;

     
      // Identificador del turno
      TurnoPk turnoID = new TurnoPk();
      turnoID.idCirculo = turno.getIdCirculo();
      turnoID.idProceso = turno.getIdProceso();
      turnoID.idTurno = turno.getIdTurno();
      turnoID.anio = turno.getAnio();
     
      if(turnoAnterior ==null ||(turnoAnterior!=null && turnoAnterior.getIdProceso()!=6)){
      List solFolio = solicitud.getSolicitudFolios();
      List foliosID = new Vector();
      Iterator itSolFolio = solFolio.iterator();
      while (itSolFolio.hasNext()) {
              SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
              FolioPk folioID = new FolioPk();
              folioID.idMatricula = sol.getFolio().getIdMatricula();
              foliosID.add(folioID);
      }

      

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
                  /**Si las anotaciones las realizo el usuario con otro  rol no debe ser impresas*/
                  if(t2_Folio_Anotacion.getIdWorkflow()==null||t2_Folio_Anotacion.getIdWorkflow().equals("")||
                  t2_Folio_Anotacion.getIdWorkflow().equals(turno.getIdWorkflow())){
                	  t2_Folio_ListAnotacion.add( t2_Folio_Anotacion );
                  }
                }

                try
                {
                	 Collections.sort(t2_Folio_ListAnotacion, new ComparadorAnotaciones());
                }
                catch (ClassCastException e)
                {
                
                }

                // incluir las anotaciones al folio
                t2_Folio.setAnotaciones( t2_Folio_ListAnotacion );
                List local_TargetFolioSalvedadesList;
                local_TargetFolioSalvedadesList
                    = jxSearch_ExtractSalvedadesByTurno(
                          t2_Folio.getSalvedades()
                        , turno.getIdWorkflow()
                      );

                t2_Folio.setSalvedades( local_TargetFolioSalvedadesList );
                // -----------------------------------------------------------------
                t2_SolicitudFolio = new SolicitudFolio();
                t2_SolicitudFolio.setFolio( t2_Folio );
                t2_ListSolicitudFolio.add( t2_SolicitudFolio );
              } // for


      }
      catch (ForsetiException e) {
              throw new EventoException(e.getMessage(), e);
      }
      catch (Throwable e) {
              throw new EventoException(e.getMessage(), e);
      }

      //COLOCARLE AL TURNO LA LISTA DE FOLIOS QUE FUERON OBJETO DE MODIFICACIÓN
      Solicitud solic = new Solicitud();
      solic = turnoTemp.getSolicitud();
      // solic.setSolicitudFolios(folios);
      solic.setSolicitudFolios( t2_ListSolicitudFolio );
      turnoTemp.setSolicitud(solic);
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
      //TODO: depurar el objeto que se envia al imprimible
      //IMPRIMIR EL FORMULARIO DE CORRECCIONES.
      String fechaImpresion= this.getFechaImpresion();
      ImprimibleFormulario imprimible= null;
      ImprimibleFormulario imprimiblepdf= null;
      if(turnoAnterior ==null ||(turnoAnterior!=null && turnoAnterior.getIdProceso()!=6)){  
        imprimible = new ImprimibleFormulario(turnoTemp, usr.getUsername(), CTipoFormulario.TIPO_CORRECCION, fechaImpresion,CTipoImprimible.FORMULARIO_CORRECCION);
        imprimible.setPrintWatermarkEnabled(true);
        imprimible.setFormularioNoOficialCorreccionesEnabled( isImpresionTemporalDeAuxiliarEnabled );
        
        imprimiblepdf = new ImprimibleFormulario(turnoTemp, usr.getUsername(), CTipoFormulario.TIPO_CORRECCION, fechaImpresion,CTipoImprimible.FORMULARIO_CORRECCION);
        imprimiblepdf.setPrintWatermarkEnabled(true);
        imprimiblepdf.setFormularioNoOficialCorreccionesEnabled( isImpresionTemporalDeAuxiliarEnabled );
        imprimiblepdf.setPdf(true);
        /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: se agregan sentecias para instanciar los imprimibles de correccion de testamentos.
        */
      }

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
            
              
            PrinterJob printJob = PrinterJob.getPrinterJob();
            PageFormat pf = printJob.defaultPage();
            Paper papel = new Paper();
            double mWidth = LETTER_WIDTH;
            double mHeight = LETTER_HEIGHT;

            papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                mHeight - (0.5 * INCH));
            pf.setPaper(papel);
            imprimible.generate(pf);
            this.generarVisualizacion(imprimible, request, response);
        
        } // end-method
	
	private EventoRespuesta devolverTestamento(Evento ev)throws EventoException{
		EvnCalificacion evento = (EvnCalificacion)ev;
		Turno turno= evento.getTurno();
	    gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioNeg();
		Hashtable parametros=new Hashtable();
        /**
         * @author Cesar Ramírez
         * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
         * Excepción a visualizar en la ventana de "Error en la validación de datos del formulario".
         **/
        boolean exc_testamentos = false;
		//Se valida que los turnos posteriores que comparten la misma matricula que tengan anotacionesTemporal 
        //tienen que ser devueltos sino estan en calificacion
        try {
			List turnos = new ArrayList();
			TurnoPk oid=new TurnoPk();
			oid.anio=turno.getAnio();
			oid.idCirculo=turno.getIdCirculo();
			oid.idProceso=turno.getIdProceso();
			oid.idTurno=turno.getIdTurno();
			turnos.add(oid);
			forseti.validarPrincipioPrioridadDevolucion(turnos);
		} catch (ForsetiException e1) {
			throw new ValidacionParametrosHTException(e1);
		} catch (Throwable e2){
			throw new EventoException("No se pudieron obtener validacion de Devolución:"+e2.getMessage(),e2);
		}
		try{
		    
			/** Modifica Pablo Quintana Junio 20 2008
			 *  Sólo se devuelve a testamento si no ha avanzado a fase certificados asociados
			 */
			List historia = turno.getHistorials();
			String estacionTestamentos = null;
			//daniel
			int i = historia.size()-1;
			boolean avanzoFaseCertAsociados = false;
			while(i>=0){
				TurnoHistoria historial = (TurnoHistoria) historia.get(i);
				if(historial.getFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
					if(i!= historia.size()-1){
						avanzoFaseCertAsociados = true;
					}
				}
				//daniel
				if (historial.getFase().equals(CFase.REG_TESTAMENTO))
      			{
					estacionTestamentos = historial.getIdAdministradorSAS();
      				Log.getInstance().debug(ANCalificacion.class, "Administrador SAS: "+estacionTestamentos);
      				break;
      			}
				--i;
			}
			if(avanzoFaseCertAsociados){
				throw new EventoException("El turno " + turno.getIdWorkflow() + " no puede ser devuelto a testamentos porque ya avanzó a la fase  "+CFase.REG_CERTIFICADOS_ASOCIADOS);
			}
			
			
			
			String USUARIO_INICIADOR = ( null != evento.getUsuarioNeg() )?(""+evento.getUsuarioNeg().getUsername()):("");
	        if( "".equals( USUARIO_INICIADOR ) || null == USUARIO_INICIADOR )
	            throw new TurnoNoAvanzadoException( "El usuario no se ha regostrado en la capa web." + this.getClass().getName() );
	        parametros.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);
			// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
			//del turno desde esta fase.
			try {
					 Hashtable tablaAvance = new Hashtable(2);
					 tablaAvance.put(Processor.RESULT, ANCalificacion.REVISION_CALIFICACION);
					 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
					 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
            /**
             * @author Cesar Ramírez
             * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
             * Excepción a visualizar en la ventana de "Error en la validación de datos del formulario".
             **/
            } catch(HermodException e1) {
                if(!e1.toString().isEmpty())
                    exc_testamentos = true;
                throw e1;
            }catch(Throwable t){
                throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}
			/*****************************************/
			/*        ELIMINAR SASS                  */
			/*****************************************/
			Fase faseAvance = evento.getFase();    
			Hashtable parametrosAvance = new Hashtable();
			  	parametrosAvance.put(Processor.RESULT,CRespuesta.DEVOLVER_FIRMA_TESTAMENTO);
			  	//daniel
			  	parametrosAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
			  	if (estacionTestamentos != null)
			      {
			  		parametrosAvance.put(Processor.ESTACION,estacionTestamentos);
			  		parametrosAvance.put(Processor.CONDICION_AVANCE,CFase.REG_TESTAMENTO);
			      }
			  	
				try{
					//TFS 3582: SI UN TURNO SE VA A DEVOLVER, SE ELIMINAN LAS NOTAS DEVOLUTIVAS
			    	TurnoPk oid=new TurnoPk();
					oid.anio=turno.getAnio();
					oid.idCirculo=turno.getIdCirculo();
					oid.idProceso=turno.getIdProceso();
					oid.idTurno=turno.getIdTurno();
					hermod.removeDevolutivasFromTurno(oid);
					//hermod.avanzarTurnoNuevoNormal(turno,faseAvance,parametrosAvance,u);
					hermod.avanzarTurnoNuevoCualquiera(turno, faseAvance, parametrosAvance, u);
				}
			    catch (Throwable exception) {
			    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
			    }
		} catch (Throwable e) {
			Log.getInstance().error(ANCalificacion.class, e.getMessage(), e);
            /**
             * @author Cesar Ramírez
             * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
             * Excepción a visualizar en la ventana de "Error en la validación de datos del formulario". - Testamentos
             **/
            if(exc_testamentos)
                throw new TurnoNoAvanzadoException("No es posible devolver el turno a la fase. Se requiere una nota informativa.");
            else
                throw new EventoException(e.getMessage(), e);
		}
		EvnRespCalificacion respuesta = new EvnRespCalificacion(null, EvnRespCalificacion.ENVIAR_RESPUESTA);
		return respuesta;
	}

	
	public ProcesadorEventosNegocioProxy getProcesadorEventosNegocioProxy() {
    	if( null == proxy ) {
    		proxy =  new ProcesadorEventosNegocioProxy();
    	}
    	return proxy; 
    }
    
    private static ProcesadorEventosNegocioProxy proxy = null;
	/**
	 * Método que permite bloquear los folios que hacen parte del turno al usuario que
	 * esta trabajando con el turno.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta tomarFolios(EvnCalificacion evento) throws EventoException {

		Turno turno = evento.getTurno();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioNeg();
		Solicitud solicitud = turno.getSolicitud();

		if (solicitud == null) {
			throw new EventoException("No existe solicitud asociada");
		}

		List solFolio = solicitud.getSolicitudFolios();
		List matriculas = new Vector();
		LlaveBloqueo llaveBloqueo = null;
		Iterator itSolFolio = solFolio.iterator();

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
		}
                // raise si existen folios cerrados
                if( local_ListFoliosCerrados.size() > 0 ){
                   throw new EventoException ("Existen folios en el rango que aparecen como cerrados " + local_ListFoliosCerradosCsvRepresentation.toString() );
                } // if

		try {
			TurnoPk turnoID = new TurnoPk();
			turnoID.idCirculo = turno.getIdCirculo();
			turnoID.idProceso = turno.getIdProceso();
			turnoID.idTurno = turno.getIdTurno();
			turnoID.anio = turno.getAnio();

            llaveBloqueo = forseti.delegarBloqueoFolios(turnoID, usr);
		} catch (ForsetiException e) {
			if(e.getHashErrores() != null)
			{
				if(!e.getHashErrores().isEmpty())
				{
					throw new TomarTurnoCalificacionHTException(e);
				}
			}
			throw new EventoException(e.getMessage(), e);
		}catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada bloqueando los folios : " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
				 
		return null;
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
                  Log.getInstance().error(ANCalificacion.class,"No se pudo encontrar el servicio:" + ep.toString());
                  throw new EventoException("Servicio no encontrado", e);
           }
           catch (ForsetiException e) {
                  ExceptionPrinter ep = new ExceptionPrinter(e);
                  Log.getInstance().error(ANCalificacion.class,"Error en el servicio para validar la matrícula:" + ep.toString());
                  throw new EventoException("Error en el servicio para validar la matrícula", e);
           }
           catch (Throwable e) {
                  ExceptionPrinter ep = new ExceptionPrinter(e);
                  Log.getInstance().error(ANCalificacion.class,"Se produjo un error validando la matrícula:" + ep.toString());
                  throw new EventoException("Se produjo un error validando la matrícula", e);

           } // try

           return buzz_ValidateCerradoFolio;

    }
    
    protected EventoRespuesta
    calificacion_DeshacerCambios( EvnCalificacion evento )
    throws EventoException {



       // in ----------------------------------
       // :: unwrap message

       // turno
       Turno local_Turno;
       local_Turno = evento.getTurno();

       // turno.solicitud
       Solicitud local_TurnoSolicitud;
       local_TurnoSolicitud = local_Turno.getSolicitud();

       // turno.solicitud.solicitudFolios
       // List< Folio >
       List local_TurnoSolicitudFolios;
       local_TurnoSolicitudFolios = local_TurnoSolicitud.getSolicitudFolios();

       // usuario
       gov.sir.core.negocio.modelo.Usuario local_UsuarioSir;

       local_UsuarioSir = evento.getUsuarioNeg();

       // -------------------------------------

       // pr ----------------------------------

       Iterator local_TurnoSolicitudFoliosIterator = local_TurnoSolicitudFolios.iterator();

       Folio local_ElementFolio;
       SolicitudFolio local_ElementSolicitudFolio;
       for( ;local_TurnoSolicitudFoliosIterator.hasNext(); ){

          local_ElementSolicitudFolio = (SolicitudFolio) local_TurnoSolicitudFoliosIterator.next();
          local_ElementFolio = local_ElementSolicitudFolio.getFolio();

          try {
        	  	/**Validacion para deshacer cambios temporales*/
        	  TurnoPk turnoPk =  new TurnoPk();
        	  turnoPk.anio = local_Turno.getAnio();
        	  turnoPk.idCirculo = local_Turno.getIdCirculo();
        	  turnoPk.idProceso = local_Turno.getIdProceso();
        	  turnoPk.idTurno = local_Turno.getIdTurno();
        	  
        	  forseti.validarPrincipioPrioridadDeshacerCambiosTemporales(turnoPk);
        	  
        	  	/**Deshacer los cambios del folio*/
        	  //forseti.deshacerCambiosFolio( local_ElementFolio, local_UsuarioSir);
              forseti.deshacerCambiosFolioByTurno(turnoPk, local_UsuarioSir, false);
         	 	
        	  	/**Si el folio es temporal se debe eliminar*/
        	  if(!local_ElementFolio.isDefinitivo()){
         	 		FolioPk folioPk = new FolioPk();
         	 		folioPk.idMatricula = local_ElementFolio.getIdMatricula();
         	 		forseti.deleteFolio(folioPk,"DESHACER CAMBIOS TEMPORALES");
         	 	}
          }
          catch( HermodException e ) {
             ExceptionPrinter printer = new ExceptionPrinter(e);
             Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios " + "del folio " + local_ElementFolio.getIdMatricula() + ": " +  printer.toString());
             throw new EventoException(e.getMessage(), e);
          }
          catch (ForsetiException e1) {
				Hashtable errores=e1.getHashErrores();
				if (errores!=null && !errores.isEmpty()){
					String mensaje="No fue posible bloquear las matrículas:\n";
					Enumeration enumeration=errores.keys();
					while(enumeration.hasMoreElements()){
						String matricula=(String)enumeration.nextElement();
						String razon = (String)errores.get(matricula);
						mensaje.concat(matricula+" : "+razon+"\n");
					}
					ExceptionPrinter ep=new ExceptionPrinter(e1);
					Log.getInstance().error(ANCalificacion.class,mensaje+ep.toString());
					throw new TomarTurnoCalificacionHTException(e1);
				}
              ExceptionPrinter ep=new ExceptionPrinter(e1);
              Log.getInstance().error(ANCalificacion.class,"No fué posible tomar el turno:"+ep.toString());
              throw new TomarTurnoRegistroException("No fué posible tomar el turno:"+e1.getMessage(),e1);
          }catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANCalificacion.class,"No fue posible tomar el turno:"+ep.toString());
				throw new TomarTurnoRegistroException("No fué posible tomar el turno:"+e1.getMessage(),e1);
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
 	      Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios " + "del turno " + local_Turno.getIdWorkflow() + ": " +  printer.toString());
 	      throw new EventoException(e.getMessage(), e);
 	   }
 	   catch (Throwable e) {
 	        ExceptionPrinter printer = new ExceptionPrinter(e);
 	        Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada deshaciendo los cambios " + "del turno " + local_Turno.getIdWorkflow() + ": " +  printer.toString());
 	        throw new EventoException(e.getMessage(), e);
 	   }
    
       //DEVOLVER A REVISION
       
 	  /*Fase fase = evento.getFase();
 	  gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

 	  //2. Construcción de tabla de parámetros para realizar el avance del turno.
 	  Hashtable tabla = new Hashtable();
 	  tabla.put("RESULT", evento.getRespuestaWF());
 	  tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
 		
       try {

 			hermod.avanzarTurnoNuevoCualquiera(local_Turno, fase, tabla,evento.getUsuarioSIR());
 		} catch (HermodException e) {

 		} catch (Throwable e) {

 			throw new ANCorreccionException("No se pudo Devolver a Revisión la solicitud de corrección. No fue" +
 											 "posible avanzar el turno.", e);
 		}/
       // -------------------------------------

       // ou ----------------------------------
       /*EvnRespCorreccion local_Result;

       local_Result = new EvnRespCorreccion( EvnRespCorreccion.PAGEEVENT_CORRECCIONREVISARAPROBAR_DESHACERCAMBIOS_EVENTRESPOK );

       return local_Result;*/
 	   	EvnSeguridad        local_TmpParameters  = null;
		EvnRespSeguridad    local_TmpResult      = null;
		
		local_TmpParameters = new EvnSeguridad( evento.getUsuario(),evento.getRol(),evento.getProceso()); 
		
		local_TmpResult = (EvnRespSeguridad)( getProcesadorEventosNegocioProxy().manejarEvento( local_TmpParameters ) );
		
		return local_TmpResult;
 		
       // -------------------------------------

    } // end-method: correccionRevisarAprobar_DeshacerCambios
    
    private EventoRespuesta avanzarCorrecciones(EvnCalificacion evento) throws EventoException {

    	//Se realiza la validacion de que no tenga informacion Temporal

		Turno turno = evento.getTurno();
		Usuario usuario = evento.getUsuarioNeg();
		
		SolicitudRegistro solicitud1 = (SolicitudRegistro)turno.getSolicitud();
		List folios1= solicitud1.getSolicitudFolios();
		this.metodoGeneralTurnosCorreecion(folios1,turno);
		
		TurnoPk oid = new TurnoPk();
		oid.anio = turno.getAnio();
		oid.idCirculo = turno.getIdCirculo();
		oid.idProceso = turno.getIdProceso();
		oid.idTurno = turno.getIdTurno();

		SolicitudPk sid = new SolicitudPk();
		Solicitud solicitud = turno.getSolicitud();
		sid.idSolicitud = solicitud.getIdSolicitud();


		boolean temporalTurno = false;
		String matriculasInfoTemporal = "";
		int i = 0;
		List segEngTemporales = null;


		try{
			SolicitudRegistro sol= (SolicitudRegistro)turno.getSolicitud();
			List folios=sol.getSolicitudFolios();
			Iterator ifol= folios.iterator();
			while(ifol.hasNext()){

				SolicitudFolio sfol=(SolicitudFolio) ifol.next();
				Folio fol = sfol.getFolio();
				FolioPk fid= new FolioPk();
				fid.idMatricula = fol.getIdMatricula();
				
				//SE OBTIENE LA INFORMACIÓN PARA DETERMINAR SI SE PUEDE O NO DESASOCIAR LAS MATRÍCULAS.
				//NO SE PUEDE CUANDO EL FOLIO TIENE COSAS TEMPORALES
				temporalTurno = false;

				temporalTurno = forseti.hasDatosTemporalesTurno(oid, fid);

				if(temporalTurno){
					if (i==0) {
						matriculasInfoTemporal = fol.getIdMatricula();
						i++;
					} else {
						matriculasInfoTemporal = matriculasInfoTemporal + ", " + fol.getIdMatricula();
						i++;
					}
				}

			}

			segEngTemporales =  hermod.getWebSegEngBySolicitud(sid);

		} catch (Throwable e1) {
			e1.printStackTrace();
			throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti",e1);
		}

		String exc = "";
		//SI HAY FOLIOS CON DATOS TEMPORALES NO SE PUEDE AVANZAR EL TURNO
		if (i!=0) {
			if (i==1) {
				exc = "No se puede avanzar el turno; la matrícula " + matriculasInfoTemporal + " tiene modificaciones temporales.";
			} else {
				exc = "No se puede avanzar el turno; las matrículas " + matriculasInfoTemporal + " tienen modificaciones temporales.";
			}
			AvanzarSinInformacionTemporalException excepcion = new AvanzarSinInformacionTemporalException(exc);
			throw excepcion;
		}

		if(segEngTemporales!=null && segEngTemporales.size()>0){
			exc = "No se puede avanzar el turno; existen englobes o segregaciones temporales. Debe eliminarlas si desea enviar el turno a otras dependencias.";
			AvanzarSinInformacionTemporalException excepcion = new AvanzarSinInformacionTemporalException(exc);
			throw excepcion;
		}
		
		//Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
			Hashtable tablaAvance = new Hashtable(2);
			tablaAvance.put(Processor.RESULT, CRespuesta.CORRECCION);
			tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
			Fase fase = new Fase();
			fase.setID(CFase.CAL_CALIFICACION);
			hermod.validarNotaInformativaAvanceTurno(fase,tablaAvance,turno);
		} catch(Throwable t) {
			throw new TurnoNoAvanzadoException("No es posible generar el turno derivado. Se requiere una nota informativa.");
		}

		//Si no tiene informacion Temporal se puede avanzar sin problema
		// Se crea el nuevo turno
		Turno turnoNuevo;

		try {
			turnoNuevo = hermod.crearTurnoDependiente(turno, evento.getUsuarioNeg(), Long.parseLong(CProceso.PROCESO_CORRECCION));
			/*Fase fase = hermod.getFase(turnoNuevo.getIdFase());
			Hashtable tabla = new Hashtable();
			tabla.put(Processor.RESULT, CRespuesta.CONFIRMAR);
	        tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
			hermod.avanzarTurno(turnoNuevo, fase, tabla, CAvanzarTurno.AVANZAR_NORMAL);*/
		} catch(Throwable th) {
			throw new ValidacionParametrosException(th.getMessage());
		}
                /**
                * @author Fernando Padilla Velez
                * @change Acta - Requerimiento No 178 - Integración Gestión Documental y SIR,
                *         Se agrega esta fragmento de codigo para la publicacion del mensaje del turno creado.
                */
                PublisherIntegracion  clienteJMS = new PublisherIntegracion();
                clienteJMS.setUsuario(usuario.getUsername());
                clienteJMS.setTurno(turnoNuevo);
                clienteJMS.enviar();

		EvnRespCalificacion respuesta = new EvnRespCalificacion(turnoNuevo,
				EvnRespCalificacion.AVANZAR_CORRECCIONES);
		respuesta.setTurno(turno);

		return respuesta;
	}

    private EventoRespuesta avanzarAntiguoSistema(EvnCalificacion evento) throws EventoException {

    	//Se realiza la validacion de que no tenga informacion Temporal

		Turno turno = evento.getTurno();
		Usuario usuario = evento.getUsuarioNeg();

		TurnoPk oid = new TurnoPk();
		oid.anio = turno.getAnio();
		oid.idCirculo = turno.getIdCirculo();
		oid.idProceso = turno.getIdProceso();
		oid.idTurno = turno.getIdTurno();

		Fase fase = evento.getFase();
		Hashtable tabla = new Hashtable();
		Usuario u = evento.getUsuarioNeg();
		tabla.put(Processor.RESULT, evento.getRespuestaWf());
		tabla.put(CInfoUsuario.USUARIO_SIR, u.getUsername());

		boolean temporalTurno = false;
		String matriculasInfoTemporal = "";
		int i = 0;

		try{
			SolicitudRegistro sol= (SolicitudRegistro)turno.getSolicitud();
			List folios=sol.getSolicitudFolios();
			Iterator ifol= folios.iterator();
			while(ifol.hasNext()){

				SolicitudFolio sfol=(SolicitudFolio) ifol.next();
				Folio fol = sfol.getFolio();
				FolioPk fid= new FolioPk();
				fid.idMatricula = fol.getIdMatricula();
				
				//SE OBTIENE LA INFORMACIÓN PARA DETERMINAR SI SE PUEDE O NO DESASOCIAR LAS MATRÍCULAS.
				//NO SE PUEDE CUANDO EL FOLIO TIENE COSAS TEMPORALES
				temporalTurno = false;

				temporalTurno = forseti.hasDatosTemporalesTurno(oid, fid);

				if(temporalTurno){
					if (i==0) {
						matriculasInfoTemporal = fol.getIdMatricula();
						i++;
					} else {
						matriculasInfoTemporal = matriculasInfoTemporal + ", " + fol.getIdMatricula();
						i++;
					}
				}
			}

		} catch (Throwable e1) {
			e1.printStackTrace();
			throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti",e1);
		}

		//		SI HAY FOLIOS CON DATOS TEMPORALES NO SE PUEDE AVANZAR EL TURNO
		String exc = "";
		if (i!=0) {
			if (i==1) {
				exc = "No se puede avanzar el turno; la matrícula " + matriculasInfoTemporal + " tiene modificaciones temporales.";
			} else {
				exc = "No se puede avanzar el turno; las matrículas " + matriculasInfoTemporal + " tienen modificaciones temporales.";
			}
			AvanzarSinInformacionTemporalException excepcion = new AvanzarSinInformacionTemporalException(exc);
			throw excepcion;
		}

		//Si no tiene informacion Temporal se puede avanzar sin problema
		try {
			//Se borran las notas devolutivas una vez impreso el formulario de calificacion,
			//esto se hace únicamente si el avance es de calificación.
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();


		} catch (Throwable e1) {
			ExceptionPrinter ep = new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"El turno no se ha podido avanzar:" + ep.toString());
			throw new AvanzarCalificacionException("El turno no se ha podido avanzar.", e1);
		}
		return null;
	}

    private EventoRespuesta avanzarEspecializado(EvnCalificacion evento) throws EventoException {
        //Se realiza la validacion de que no tenga informacion Temporal

		Turno turno = evento.getTurno();
		Usuario usuario = evento.getUsuarioNeg();

		TurnoPk oid = new TurnoPk();
		oid.anio = turno.getAnio();
		oid.idCirculo = turno.getIdCirculo();
		oid.idProceso = turno.getIdProceso();
		oid.idTurno = turno.getIdTurno();

		Fase fase = evento.getFase();
		Hashtable tabla = new Hashtable();
		Usuario u = evento.getUsuarioNeg();
		tabla.put(Processor.RESULT, evento.getRespuestaWf());
		tabla.put(CInfoUsuario.USUARIO_SIR, u.getUsername());

		boolean temporalTurno = false;
		String matriculasInfoTemporal = "";
		int i = 0;

		try{
			SolicitudRegistro sol= (SolicitudRegistro)turno.getSolicitud();
			List folios=sol.getSolicitudFolios();
			Iterator ifol= folios.iterator();
			while(ifol.hasNext()){

				SolicitudFolio sfol=(SolicitudFolio) ifol.next();
				Folio fol = sfol.getFolio();
				FolioPk fid= new FolioPk();
				fid.idMatricula = fol.getIdMatricula();
				
				//SE OBTIENE LA INFORMACIÓN PARA DETERMINAR SI SE PUEDE O NO DESASOCIAR LAS MATRÍCULAS.
				//NO SE PUEDE CUANDO EL FOLIO TIENE COSAS TEMPORALES
				temporalTurno = false;

				temporalTurno = forseti.hasDatosTemporalesTurno(oid, fid);

				if(temporalTurno){
					if (i==0) {
						matriculasInfoTemporal = fol.getIdMatricula();
						i++;
					} else {
						matriculasInfoTemporal = matriculasInfoTemporal + ", " + fol.getIdMatricula();
						i++;
					}
				}

			}

		} catch (Throwable e1) {
			e1.printStackTrace();
			throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti",e1);
		}

		//		SI HAY FOLIOS CON DATOS TEMPORALES NO SE PUEDE AVANZAR EL TURNO
		String exc = "";
		if (i!=0) {
			if (i==1) {
				exc = "No se puede avanzar el turno; la matrícula " + matriculasInfoTemporal + " tiene modificaciones temporales.";
			} else {
				exc = "No se puede avanzar el turno; las matrículas " + matriculasInfoTemporal + " tienen modificaciones temporales.";
			}
			AvanzarSinInformacionTemporalException excepcion = new AvanzarSinInformacionTemporalException(exc);
			throw excepcion;
		}

		//Si no tiene informacion Temporal se puede avanzar sin problema
		try {
			//Se borran las notas devolutivas una vez impreso el formulario de calificacion,
			//esto se hace únicamente si el avance es de calificación.
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();


		} catch (Throwable e1) {
			ExceptionPrinter ep = new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"El turno no se ha podido avanzar:" + ep.toString());
			throw new AvanzarCalificacionException("El turno no se ha podido avanzar.", e1);
		}
		return null;
	}

	private EventoRespuesta
    calificacion_ReimpresionSellos_Search( EvnCalificacion evento )
    throws EventoException {

        // el evento debe ser de tipo
        // TODO: check;

        EvnCalificacionReproduccionSellos thisEvent
        = (EvnCalificacionReproduccionSellos)evento;

        String param_SearchString = thisEvent.getItemToFind();


        gov.sir.core.negocio.modelo.Turno param_ThisTurno;

        // datos basicos para procesamiento
        param_ThisTurno       = thisEvent.getTurno();

        if( ( null == param_ThisTurno ) ) {
          throw new EventoException( ":Turno no seleccionado" );
        }

        if( ( null == param_SearchString ) ) {
          throw new EventoException( ":Debe especificarse un parametro de busqueda" );
        }


        java.util.List searchResults = null;


        // buscar turno a imprimir
        gov.sir.core.negocio.modelo.Turno param_TurnoToPrint = null;

        try {

            param_TurnoToPrint = hermod.getTurnobyWF( param_SearchString );


            // todo: verificar que es un turno de registro-calificacion

            if( null == param_TurnoToPrint ) {
                // AvanzarCalificacionException
                throw new HermodException( "El turno " +  param_SearchString + "no existe" );
            }


        }
        catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
            throw new EventoException(e.getMessage(), e);
        }

        // process

        prcs: {

          searchResults = new java.util.ArrayList();
          gov.sir.core.negocio.modelo.Turno element = param_TurnoToPrint;
          searchResults.add( element );

        }

        // la respuesta del procesamiento se devuelve en
        EvnRespCalificacionReproduccionSellos result = null;

        result = new EvnRespCalificacionReproduccionSellos( EvnRespCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_SEARCH__EVENTRESP_OK );
        result.setSearchResults( searchResults );

        return result;
    }

private EventoRespuesta
calificacion_ReimpresionSellos_PrintSelected( EvnCalificacion evento )
throws EventoException {

        // el evento debe ser de tipo
        // TODO: check;

        EvnCalificacionReproduccionSellos thisEvent = (EvnCalificacionReproduccionSellos)evento;

        String param_TurnoSelected = thisEvent.getItemSelected();

        gov.sir.core.negocio.modelo.Turno param_ThisTurno;


        // datos basicos para procesamiento
        param_ThisTurno       = thisEvent.getTurno();

        if( ( null == param_ThisTurno ) ) {
          throw new ReproduccionSellosException( "Turno no seleccionado" );
        }

        if( ( null == param_TurnoSelected ) ) {
          throw new ReproduccionSellosException( "Debe especificarse un parametro de busqueda" );
        }


        // buscar turno a imprimir
        gov.sir.core.negocio.modelo.Turno param_TurnoToPrint = null;

        try {

            param_TurnoToPrint = hermod.getTurnobyWF( param_TurnoSelected );


            // todo: verificar que es un turno de registro-calificacion

            if( null == param_TurnoToPrint ) {
                throw new HermodException( "El turno " +  param_TurnoSelected + "no existe" );
            }


        }
        catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
            throw new ReproduccionSellosException(e.getMessage(), e);
        }

        try{
            ReproduccionSellos Reproduccion = new ReproduccionSellos();
            Reproduccion.setCirculo(param_ThisTurno.getIdCirculo());
            Reproduccion.setIdTurnoRaiz(param_ThisTurno.getIdWorkflow());
            Reproduccion.setCode(param_TurnoToPrint.getIdWorkflow());
            Reproduccion.setDesde(0);
            Reproduccion.setHasta(0);
            Reproduccion.setFase_reg(0);
            Reproduccion.setOpcion(1);
            Reproduccion.setNcopiasSello(String.valueOf(evento.getNumeroImpresiones()));
            Reproduccion.setUsuariosir(String.valueOf(evento.getUsuarioNeg().getIdUsuario()));
            boolean guardar = hermod.CreateReproduccionSellosReg(Reproduccion);
        }catch(Throwable e){
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion en el servicio de almacenar reproduccion sellos hacia registrador");
            throw new ReproduccionSellosException(e.getMessage(), e);
        }

        // actual --------------------------------------------------


        gov.sir.core.negocio.modelo.Turno turno = param_TurnoToPrint;
        String idCirculo=thisEvent.getIdCirculo();
        TurnoPk oid=new TurnoPk();
        oid.anio=turno.getAnio();
        oid.idCirculo=turno.getIdCirculo();
        oid.idProceso=turno.getIdProceso();
        oid.idTurno=turno.getIdTurno();
        Usuario u=evento.getUsuarioNeg();

        try {

                //actualizar los folios segun los modificados en la solicitud
                try {
                     List solicitudesFoliosCalificados = null;
                     solicitudesFoliosCalificados =this.calificacion_ReimpresionSellos_PrintSelected_GetSolicitudesFolioCalificadas( forseti, oid, u );

                     turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
                     turno.setIdWorkflow(param_ThisTurno.getIdWorkflow());
                }
                catch (Throwable e2){
                         ExceptionPrinter ep=new ExceptionPrinter(e2);
                         Log.getInstance().error(ANCalificacion.class,"No se pudieron obtener los folios calificados:"+ep.toString());
                         throw new ReproduccionSellosException("No se pudieron obtener los folios calificados:"+e2.getMessage(),e2);
                }


               
                String localUserName=evento.getUsuarioNeg().getUsername();
                localUserName = ( null != localUserName )?( localUserName ):( "" );

                //crear el imprimible de formulario
                String fechaImpresion= this.getFechaImpresion();
                ImprimibleFormulario impFormulario = new ImprimibleFormulario(turno , localUserName, CTipoFormulario.TIPO_REPRODUCCION_SELLOS, fechaImpresion, CTipoImprimible.REPRODUCCION_SELLOS);
                impFormulario.setPrintWatermarkEnabled(true);
                //mandar a imprimir documento
                this.imprimir(impFormulario, idCirculo, 1);


        }
        catch (Throwable e1) {
                ExceptionPrinter ep=new ExceptionPrinter(e1);
                Log.getInstance().error(ANCalificacion.class,"No se pudo imprimir el formulario:"+ep.toString());
                throw new ReproduccionSellosException("No se pudo imprimir el formulario",e1);
        }

        // la respuesta del procesamiento se devuelve en
        EvnRespCalificacionReproduccionSellos result = null;

        result = new EvnRespCalificacionReproduccionSellos( EvnRespCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENTRESP_OK );
        result.setItemToPrint( param_TurnoToPrint ); // se coloca el item que se imprimio

        return result;

} // :calificacion_ReimpresionSellos_PrintSelected






	/**
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta imprimirReproduccionMatricula( EvnCalificacion evento )throws EventoException {

		EvnCalificacionReproduccionSellos eventoReproduccion = (EvnCalificacionReproduccionSellos)evento;

		String matricula =  eventoReproduccion.getMatricula();
		String UID = eventoReproduccion.getIdCirculo();
		Turno turnoReproduccion = evento.getTurno();

		int inicio =  eventoReproduccion.getInicio();
		int fin  =  eventoReproduccion.getFin();

		if( matricula == null ) {
		  throw new ReproduccionSellosException( "Debe seleccionar la matrícula sobre la cuál se quiere realizar la reproducción de sellos." );
		}
		if( inicio == 0) {
		  throw new ReproduccionSellosException( "Debe ingresar la anotación inicial del folio sobre la cuál desea realizar la reproducción de sellos." );
		}
		if( fin == 0) {
			throw new ReproduccionSellosException( "Debe ingresar la anotación final del folio sobre la cuál desea realizar la reproducción de sellos." );
		}

		if(inicio >0){
			inicio = inicio - 1;
		}
		if(fin >0){
			fin = fin - 1;
		}

		//OBTENER EL FOLIO A IMPRIMIR
		gov.sir.core.negocio.modelo.Turno turno = new Turno();
		turno.setIdWorkflow(turnoReproduccion.getIdWorkflow());
		Folio folio = new Folio();

		try {
			FolioPk id = new FolioPk();
			id.idMatricula = matricula;

			folio = forseti.getFolioByIDSinAnotaciones(id);
			List anotaciones =  forseti.getAnotacionesFolio(id, CCriterio.TODAS_LAS_ANOTACIONES,null,inicio,((fin-inicio)+1),false);
			folio.setAnotaciones(anotaciones);
		}
		catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
			throw new ReproduccionSellosException(e.getMessage(), e);
		}

                try{
                           ReproduccionSellos Reproduccion = new ReproduccionSellos();
                           Reproduccion.setCirculo(eventoReproduccion.getIdCirculo());
                           Reproduccion.setIdTurnoRaiz(evento.getTurno().getIdWorkflow());
                           Reproduccion.setCode(eventoReproduccion.getMatricula());
                           Reproduccion.setDesde(eventoReproduccion.getInicio());
                           Reproduccion.setHasta(eventoReproduccion.getFin());
                           Reproduccion.setFase_reg(0);
                           Reproduccion.setOpcion(2);
                           Reproduccion.setNcopiasSello(String.valueOf(evento.getNumeroImpresiones()));
                           Reproduccion.setUsuariosir(String.valueOf(evento.getUsuarioNeg().getIdUsuario()));
                           boolean guardar = hermod.CreateReproduccionSellosReg(Reproduccion);
                       }catch(Throwable e){
                           ExceptionPrinter printer = new ExceptionPrinter(e);
                           Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion en el servicio de almacenar reproduccion de sellos hacia registrador");
                           throw new ReproduccionSellosException(e.getMessage(), e);
                       }
		try {
			 SolicitudFolio solFolio = new SolicitudFolio();
			 solFolio.setFolio(folio);

			 List solicitudesFoliosCalificados = new ArrayList();
			 solicitudesFoliosCalificados.add(solFolio);

			 turno.setSolicitud(new Solicitud());
			 turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);

			 String localUserName = String.valueOf(evento.getUsuarioNeg().getIdUsuario());
			 localUserName = ( null != localUserName )?( localUserName ):( "" );

			 //crear el imprimible de formulario
			 String fechaImpresion= this.getFechaImpresion();
			 ImprimibleFormulario impFormulario = new ImprimibleFormulario(turno , localUserName, CTipoFormulario.TIPO_REPRODUCCION_SELLOS, fechaImpresion, CTipoImprimible.REPRODUCCION_SELLOS);
			 impFormulario.setPrintWatermarkEnabled(true);
			 
			 // Obtener textos base de los separadores
				String tbase1 ="";
				String tbase2 = "";
				List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
				for(Iterator j= variablesImprimibles.iterator(); j.hasNext();){
					OPLookupCodes op = (OPLookupCodes) j.next();
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
						tbase1= op.getValor();
					}
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
						tbase2 = op.getValor();
					}
				}		
				
				impFormulario.setTEXTO_BASE1(tbase1);
				impFormulario.setTEXTO_BASE2(tbase2);
				
			 //mandar a imprimir documento
			 this.imprimir(impFormulario, UID, 1);
		}catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudo imprimir el formulario:"+ep.toString());
			throw new ReproduccionSellosException("No se pudo imprimir el formulario",e1);
		}

		// la respuesta del procesamiento se devuelve en
		EvnRespCalificacionReproduccionSellos result = null;

		result = new EvnRespCalificacionReproduccionSellos( EvnRespCalificacionReproduccionSellos.CALIFICACION_REIMPRESIONSELLOS_PRINTSELECTED_EVENTRESP_OK );
		//result.setItemToPrint( param_TurnoToPrint ); // se coloca el item que se imprimio

		return result;

}




    /**
     * calificacion_ReimpresionSellos_PrintSelected_FindUsuarioCalificadorInTurno
     *
     * @param param_TurnoToPrint Turno
     * @return String
     */
    private String
    calificacion_ReimpresionSellos_PrintSelected_FindUsuarioCalificadorInTurno( Turno param_TurnoToPrint ) {

        if( null == param_TurnoToPrint )
            return null;

        java.util.List historyItems = param_TurnoToPrint.getHistorials();

        if( ( null == historyItems )
          ||( historyItems.size() == 0 ) ) {
            return null;
        }


        // searhc params:

        final String searchedIdFase = gov.sir.core.negocio.modelo.constantes.CFase.CAL_CALIFICACION;


        // buscar de adelante hacia atras
        // java.util.Collections.reverse( historyItems );

        boolean searchHasItem = false;
        String searchResult = null;;

        java.util.Iterator iterator = historyItems.iterator();
        for(; iterator.hasNext();) {
            gov.sir.core.negocio.modelo.TurnoHistoria element
              = (gov.sir.core.negocio.modelo.TurnoHistoria)iterator.next();

            if( null == element )
                continue;

            // search compare param
            if( searchedIdFase.equalsIgnoreCase( element.getFase() ) ){
                searchHasItem = true;

                // extract username
                gov.sir.core.negocio.modelo.Usuario localUser = element.getUsuarioAtiende();
                if( null == localUser ) {
                    searchResult = null;
                    continue;
                }
                searchResult = localUser.getUsername();
            }
        }
        // evaluate hasItem ?

        return searchResult;
    }




    /**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta actualizarFolioDireccion(EvnCalificacion evento) throws EventoException {
		Folio devuelve=null;
		List listaATemp=null;
		
		List anotacionesPatrimonioFamiliar = null;
		long numanotacionesPatrimonioFamiliar = 0;
		List anotacionesAfectacionVivienda = null;
		long munanotacionesAfectacionVivienda = 0;
		List anotacionesAfectacionVivienda2 = null;
		long munanotacionesAfectacionVivienda2 = 0;

		Usuario usuarioNeg=evento.getUsuarioNeg();
		Folio nuevo=new Folio();
                nuevo.setDeterminaInm(evento.getFolio().getDeterminaInm());

		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		//nuevo.setZonaRegistral(evento.getFolio().getZonaRegistral());

		List nuevasDirecciones = evento.getNuevasDirecciones() != null ? evento.getNuevasDirecciones() : new ArrayList();
		nuevo.setDirecciones(nuevasDirecciones);
                
		//SE ACTUALIZA EL FOLIO
		try {
			if (forseti.updateFolio(nuevo,usuarioNeg, null, false)){
				FolioPk id=new FolioPk();
				id.idMatricula=evento.getFolio().getIdMatricula();
				devuelve = forseti.getFolioByIDSinAnotaciones(id);
				listaATemp= forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg);
			}
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);

		}

		//SE CONSULTA EL FOLIO PARA MOSTRAR LA INFORMACIÓN DEL MISMO
		try {
			if(nuevo!=null && nuevo.getIdMatricula()!=null){

				FolioPk id=new FolioPk();
				id.idMatricula=nuevo.getIdMatricula();

				nuevo = forseti.getFolioByIDSinAnotaciones(id,usuarioNeg);
				Folio folioDef = forseti.getFolioByIDSinAnotaciones(id);

				boolean esMayorExtension = forseti.mayorExtensionFolio(nuevo.getIdMatricula());

				long numeroAnotaciones = forseti.getCountAnotacionesFolio(nuevo.getIdMatricula());

				List foliosHijos = forseti.getFoliosHijos(id,usuarioNeg);
				List foliosPadre = forseti.getFoliosPadre(id,usuarioNeg);
                                List historialAreas = forseti.getHistorialArea(nuevo.getIdMatricula());

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
				
//				CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
				numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
				anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
				
				//	CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
				munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
				anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
				
				// CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
				munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
				anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
				
				anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);
				

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

				EvnRespCalificacion evn = new EvnRespCalificacion(historialAreas, nuevo, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion , ordenAnotacionesInvalidas,  salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, listaATemp, linderoTemporal);
				evn.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
				evn.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
				habilitarEdicionCampos(evn, evento.getTurno(), forseti, id, foliosHijos, foliosPadre, usuarioNeg);

				return evn;
			}
			throw new EventoException();
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}


	}

    /**
     * @author: Cesar Ramirez
     * @change: 1245.HABILITAR.TIPO.PREDIO
     * Se crea un método que actualiza el tipo predio del folio si cumple con la condición previamente evaluada en el JSP
     * @param evento
     * @return
     * @throw EventoException
     **/
    private EventoRespuesta actualizarFolioTipoPredio(EvnCalificacion evento) throws EventoException {
        Folio devuelve = null;
        List listaATemp = null;
        
        Usuario usuarioNeg = evento.getUsuarioNeg();
        Folio nuevo = new Folio();
        
        nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
        
        /**
         * @author: Cesar Ramirez
         * @change: 1245.HABILITAR.TIPO.PREDIO Método
         * Asigna como nuevo valor a actualizar el objeto TipoPredio
         **/
        if (evento.getNuevoTipoPredio() != null) {
            nuevo.setTipoPredio(evento.getNuevoTipoPredio());
        }
        
        Turno turno = evento.getTurno();
        TurnoPk turnoId = new TurnoPk();
        turnoId.anio = turno.getAnio();
        turnoId.idCirculo = turno.getIdCirculo();
        turnoId.idProceso = turno.getIdProceso();
        turnoId.idTurno = turno.getIdTurno();
        
        /**
         * @author: Cesar Ramirez
         * @change: 1245.HABILITAR.TIPO.PREDIO Método
         * Obtiene los datos actuales del usuario para almacenarlos en AUDITORIA_SIR.AUD_DATOS_TERMINAL
         **/
        co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoriaSir = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
        java.util.Map infoUsuario = new java.util.HashMap();
        if (evento.getInfoUsuario() != null) {
            infoUsuario.put("user", evento.getInfoUsuario().getUser());
            infoUsuario.put("host", evento.getInfoUsuario().getHost());
            infoUsuario.put("logonTime", evento.getInfoUsuario().getLogonTime());
            infoUsuario.put("address", evento.getInfoUsuario().getAddress());
            infoUsuario.put("idTurno", turno.getIdWorkflow());
        }
        try {
            auditoriaSir.guardarDatosTerminal(infoUsuario);
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANCalificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //SE ACTUALIZA EL FOLIO
        try {
            if (forseti.updateFolio(nuevo, usuarioNeg, turnoId, true)) {
                FolioPk id = new FolioPk();
                id.idMatricula = evento.getFolio().getIdMatricula();
                devuelve = forseti.getFolioByIDSinAnotaciones(id);
                listaATemp = forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg);
            }
        } catch (Throwable e1) {
            ExceptionPrinter ep = new ExceptionPrinter(e1);
            Log.getInstance().error(ANCalificacion.class, "No se pudieron grabar las anotaciones temporales : " + ep.toString());
            throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);
        }
        
        //SE CONSULTA EL FOLIO PARA MOSTRAR LA INFORMACIÓN DEL MISMO
        try {
            if (nuevo != null && nuevo.getIdMatricula() != null) {
                
                FolioPk id = new FolioPk();
                id.idMatricula = nuevo.getIdMatricula();
                
                nuevo = forseti.getFolioByIDSinAnotaciones(id, usuarioNeg);
                Folio folioDef = forseti.getFolioByIDSinAnotaciones(id);
                
                boolean esMayorExtension = forseti.mayorExtensionFolio(nuevo.getIdMatricula());
                
                long numeroAnotaciones = forseti.getCountAnotacionesFolio(nuevo.getIdMatricula());
                
                List foliosHijos = forseti.getFoliosHijos(id,usuarioNeg);
                List foliosPadre = forseti.getFoliosPadre(id,usuarioNeg);
                List historialAreas = forseti.getHistorialArea(nuevo.getIdMatricula());
                
                long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN);
                List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN, 0, (int) numeroGravamenes, usuarioNeg, true);
                
                long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
                List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR, 0, (int) numeroMedidasCautelares, usuarioNeg, true);
                
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
                
                //CONSULTA ANOTACIONES DE FALSA TRADICION
                numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
                falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, usuarioNeg, true);
                if (falsaTradicion != null) {
                    Iterator it = falsaTradicion.iterator();
                    while (it.hasNext()) {
                        Anotacion anotTemp = (Anotacion) it.next();
                        ordenFalsaTradicion.add(anotTemp.getOrden());
                    }
                }
                
                //CONSULTA ANOTACIONES INVALIDAS
                anotacionesInvalidas = forseti.getAnotacionesInvalidas(id, usuarioNeg);
                if (anotacionesInvalidas != null) {
                    Iterator it = anotacionesInvalidas.iterator();
                    while (it.hasNext()) {
                        Anotacion anotTemp = (Anotacion) it.next();
                        ordenAnotacionesInvalidas.add(anotTemp.getOrden());
                    }
                }
                
                //CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
                numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
                anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, 0, (int) numanotacionesPatrimonioFamiliar, null, true);
                
                //CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
                munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
                anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, 0, (int) munanotacionesAfectacionVivienda, null, true);
                
                //CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
                munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
                anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, 0, (int) munanotacionesAfectacionVivienda2, null, true);
                
                anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);
                
                List salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, usuarioNeg);
                List cancelaciones = forseti.getAnotacionesConCancelaciones(id, usuarioNeg);
                
                String linderoTemporal = "";
                if (nuevo != null && nuevo.getLindero() != null && !nuevo.getLindero().equals("")) {
                    if (folioDef != null) {
                        if (folioDef.getLindero() != null) {
                            linderoTemporal = nuevo.getLindero().substring(folioDef.getLindero().length(), nuevo.getLindero().length());
                        } else {
                            linderoTemporal = nuevo.getLindero();
                        }
                    } else {
                        linderoTemporal = nuevo.getLindero();
                    }
                }
                
                EvnRespCalificacion evn = new EvnRespCalificacion(historialAreas, nuevo, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion, ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, listaATemp, linderoTemporal);
                evn.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
                evn.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
                habilitarEdicionCampos(evn, evento.getTurno(), forseti, id, foliosHijos, foliosPadre, usuarioNeg);
                
                return evn;
            }
            throw new EventoException();
        } catch (Throwable e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(ANCalificacion.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
            throw new EventoException(e.getMessage(), e);
        }
    }
	
	 /**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta consultarUltimosPropietarios(EvnCalificacion evento) throws EventoException {
	
		Usuario usuarioNeg = evento.getUsuarioNeg();
		Folio folio = evento.getFolio();
		List listaUltimosPropietarios = null;

		
		try {
			listaUltimosPropietarios = forseti.getCiudadanoUltimosFolio(folio.getIdMatricula());
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);

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
		
		EvnRespCalificacion evn = new EvnRespCalificacion(EvnRespCalificacion.CONSULTAR_PROPIETARIOS_FOLIO);
		evn.setTipoEvento(EvnRespCalificacion.CONSULTAR_PROPIETARIOS_FOLIO);
		evn.setPropietariosFolios(listaUltimosPropietarios);
		return evn;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta validarCiudadanos(EvnCalificacion evento) throws EventoException {
	
		List anotacionCiudadanos  = evento.getAnotacionCiudadanos();
		
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
					if (ciudTemp.getNumeroRadicacion()!=null) {
						throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
					} else {
						throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con un turno de Correciones en Trámite.");	
					}
				}
				
			}
			
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudieron agregar los ciudadanos: "+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron agregar los ciudadanos",e1);

		}
		
		EvnRespCalificacion evn = new EvnRespCalificacion(EvnRespCalificacion.VOLVER_AGREGAR_CIUDADANOS);
		evn.setTipoEvento(EvnRespCalificacion.VOLVER_AGREGAR_CIUDADANOS);
		evn.setListaCompletaCiudadanos(evento.getListaCompletaCiudadanos());
		return evn;
	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta actualizarFolioCodCatastral(EvnCalificacion evento) throws EventoException {
		Folio devuelve=null;
		List listaATemp=null;

		Usuario usuarioNeg=evento.getUsuarioNeg();
		Folio nuevo=new Folio();

		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		//nuevo.setZonaRegistral(evento.getFolio().getZonaRegistral());
		nuevo.setCodCatastral(evento.getNuevoCodCatastral());
		nuevo.setCodCatastralAnterior(evento.getFolio().getCodCatastral());
		
		Turno turno = evento.getTurno();
		TurnoPk turnoId = new TurnoPk();
		turnoId.anio = turno.getAnio();
		turnoId.idCirculo = turno.getIdCirculo();
		turnoId.idProceso = turno.getIdProceso();
		turnoId.idTurno = turno.getIdTurno();

		//SE ACTUALIZA EL FOLIO
		try {
			if (forseti.updateFolio(nuevo,usuarioNeg, turnoId, true)){
				FolioPk id=new FolioPk();
				id.idMatricula=evento.getFolio().getIdMatricula();
				devuelve = forseti.getFolioByIDSinAnotaciones(id);
				listaATemp= forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg);
			}
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);

		}

		//SE CONSULTA EL FOLIO PARA MOSTRAR LA INFORMACIÓN DEL MISMO
		try {
			if(nuevo!=null && nuevo.getIdMatricula()!=null){

				FolioPk id=new FolioPk();
				id.idMatricula=nuevo.getIdMatricula();

				nuevo = forseti.getFolioByIDSinAnotaciones(id,usuarioNeg);
				Folio folioDef = forseti.getFolioByIDSinAnotaciones(id);

				boolean esMayorExtension = forseti.mayorExtensionFolio(nuevo.getIdMatricula());

				long numeroAnotaciones = forseti.getCountAnotacionesFolio(nuevo.getIdMatricula());

				List foliosHijos = forseti.getFoliosHijos(id,usuarioNeg);
				List foliosPadre = forseti.getFoliosPadre(id,usuarioNeg);
                                List historialAreas = forseti.getHistorialArea(nuevo.getIdMatricula());

				long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.GRAVAMEN);
				List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN,0, (int)numeroGravamenes,usuarioNeg,true);

				long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
				List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR,0, (int)numeroMedidasCautelares,usuarioNeg,true);

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
				
//				CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
				numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
				anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
				
				//	CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
				munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
				anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
				
				// CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
				munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
				anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
				
				anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);

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

				EvnRespCalificacion evn = new EvnRespCalificacion(historialAreas, nuevo, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion , ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, listaATemp, linderoTemporal);
				evn.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
				evn.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
				habilitarEdicionCampos(evn, evento.getTurno(), forseti, id, foliosHijos, foliosPadre, usuarioNeg);

				return evn;
			}
			throw new EventoException();
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}
	}







	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta actualizarFolioCabidaLinderos(EvnCalificacion evento) throws EventoException {
		Folio devuelve=null;
		List listaATemp=null;
		Usuario usuarioNeg=evento.getUsuarioNeg();
                /**
                 * @author      :   Henry Gómez Rocha
                 * @change      :   Se crea la variable validarTurno la cual evitará
                 *                  que se verique el bloqueo del folio.
                 * Caso Mantis  :   0004967
                 */
                boolean validarTurno = false;
		
		Turno turno = evento.getTurno();
		TurnoPk turnoId = new TurnoPk();
		turnoId.anio = turno.getAnio();
		turnoId.idCirculo = turno.getIdCirculo();
		turnoId.idProceso = turno.getIdProceso();
		turnoId.idTurno = turno.getIdTurno();
		

		//SE OBTIENE EL FOLIO, PARA CONSULTAR EL LINDERO COMO ESTA ORIGINALMENTE
		Folio folioDef = null;
		try{
			FolioPk folioId = new FolioPk();
			folioId.idMatricula = evento.getFolio().getIdMatricula();
			folioDef = forseti.getFolioByIDSinAnotaciones(folioId);
			//forseti.getDatosDefinitivosDeDatosTemporales(folioId,usuarioNeg);
		}catch(Throwable t){
			throw new EventoException("No se pudo consultar el folio." + t.getMessage());
		}

		//SE OBTIENE LO QUE EL USUARIO QUIERE AGREGARLE AL LINDERO
		String lindero = "";
		if (folioDef !=null){
			lindero = folioDef.getLindero();
		}else{
			if(turno!=null && turno.getIdFase()!=null && turno.getIdFase().equals(CFase.CAL_DIGITACION)){
				if(folioDef==null && evento.getNuevoLindero()!=null && !evento.getNuevoLindero().equals("")){
					lindero="";
				}else if(evento.getFolio()!=null && evento.getFolio().getLindero()!=null){
					lindero=evento.getFolio().getLindero();
				}
			}else{
				//se verifica que si es un folio creado permita editar lindero
				if(folioDef==null && turno!=null && turno.getIdFase()!=null && turno.getIdFase().equals(CFase.CAL_CALIFICACION)){
					lindero="";
				}else{
					throw new EventoException("No se pudo consultar el folio para guardar el lindero.");					
				}
			}
		}
		//SE DEJA EL LINDERO CON LA INFORMACIÓN QUE TENÍA MÁS LO QUE EL CALIFICADOR LE AGREGÓ.
		Folio nuevo=new Folio();
		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
                nuevo.setLinderosDef(evento.getFolio().getLinderosDef());
		nuevo.setLindero(lindero + evento.getNuevoLindero());
                nuevo.setCoeficiente(evento.getFolio().getCoeficiente());
                nuevo.setPrivMetros(evento.getFolio().getPrivMetros());
                nuevo.setPrivCentimetros(evento.getFolio().getPrivCentimetros());
                nuevo.setConsMetros(evento.getFolio().getConsMetros());
                nuevo.setConsCentimetros(evento.getFolio().getConsCentimetros());
                nuevo.setHectareas(evento.getFolio().getHectareas());
                nuevo.setMetros(evento.getFolio().getMetros());
                nuevo.setCentimetros(evento.getFolio().getCentimetros());
                
		//SE ACTUALIZA EL FOLIO
		try {
                        /**
                        * @author      :   Henry Gómez Rocha
                        * @change      :   Se llama a los métodos forseti.updateFolio() y forseti.getAnotacionesTMPFolioToInsert()
                        *                  con el último parametro con la variable validarTurno la cual está seteada a false para
                        *                  que cuando el proceso vaya del role Calificador a Digitador no verifique que usuario
                        *                  tiene el bloqueo.
                        * Caso Mantis  :   0004967
                        */
			if (forseti.updateFolio(nuevo,usuarioNeg, turnoId, validarTurno)){
				FolioPk id=new FolioPk();
				id.idMatricula=evento.getFolio().getIdMatricula();
				devuelve = forseti.getFolioByIDSinAnotaciones(id);
                                listaATemp= forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg, validarTurno);
			}
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);

		}

		//SE CONSULTA EL FOLIO PARA MOSTRAR LA INFORMACIÓN DEL MISMO

		try {
                        /**
                         * @author      :   Henry Gómez Rocha
                         * @change      :   Se actualiza las firmas de los métodos getFolioByIDSinAnotaciones,
                         *                  getFoliosHijos, getFoliosPadre, getAnotacionesFolio, getAnotacionesInvalidas,
                         *                  getAnotacionesConSalvedades, getAnotacionesConCancelaciones y habilitarEdicionCampos
                         *                  para que reciban el parámetro validarTurno.
                         * Caso Mantis  :   0004967
                         */
			if(nuevo!=null && nuevo.getIdMatricula()!=null){

				FolioPk id=new FolioPk();
				id.idMatricula=nuevo.getIdMatricula();

                                nuevo = forseti.getFolioByIDSinAnotaciones(id,usuarioNeg,validarTurno);
				boolean esMayorExtension = forseti.mayorExtensionFolio(nuevo.getIdMatricula());

				long numeroAnotaciones = forseti.getCountAnotacionesFolio(nuevo.getIdMatricula());

				List foliosHijos = forseti.getFoliosHijos(id,usuarioNeg,validarTurno);
				List foliosPadre = forseti.getFoliosPadre(id,usuarioNeg,validarTurno);

				long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.GRAVAMEN);
                                List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN,0, (int)numeroGravamenes,usuarioNeg,true,validarTurno);

				long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
				List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR,0, (int)numeroMedidasCautelares,usuarioNeg,true, validarTurno);

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

				//CONSULTA ANOTACIONES DE FALSA TRADICION
				numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
				falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, usuarioNeg, true, validarTurno);
				if(falsaTradicion != null){
					Iterator it = falsaTradicion.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenFalsaTradicion.add(anotTemp.getOrden());
					}
				}

				//CONSULTA ANOTACIONES INVALIDAS
				anotacionesInvalidas = forseti.getAnotacionesInvalidas(id, usuarioNeg, validarTurno);
				if(anotacionesInvalidas != null){
					Iterator it = anotacionesInvalidas.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenAnotacionesInvalidas.add(anotTemp.getOrden());
					}
				}
				
//				CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
				numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
				anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true, validarTurno);
				
				//	CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
				munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
				anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true, validarTurno);
				
				// CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
				munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
				anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true, validarTurno);
				
				anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);


                                List salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, usuarioNeg, validarTurno);
				List cancelaciones = forseti.getAnotacionesConCancelaciones(id, usuarioNeg, validarTurno);

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
				if(evento.getNuevoLindero()!=null && turno!=null && turno.getIdFase()!=null && turno.getIdFase().equals(CFase.CAL_DIGITACION))
					linderoTemporal=evento.getNuevoLindero();
                                
				List historialAreas = null;
                                String idMatricula = nuevo.getIdMatricula();
                                historialAreas = forseti.getHistorialArea(idMatricula);
				EvnRespCalificacion evn = new EvnRespCalificacion(historialAreas, nuevo, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion , ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, listaATemp, linderoTemporal);
				evn.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
				evn.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
				evn.setFolioDef(folioDef);
                                habilitarEdicionCampos(evn, evento.getTurno(), forseti, id, foliosHijos, foliosPadre, usuarioNeg, validarTurno);
                                
				return evn;
			}
			throw new EventoException();
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}
	}




	/**
	 * @param ev
	 * @return
	 */
	private EventoRespuesta segregacionMasiva(Evento ev) throws EventoException {
		EvnCalificacion evento = (EvnCalificacion)ev;
		List foliosDerivados = new Vector();

		Folio folio = evento.getFolio();
		Folio folio2 = new Folio();
		folio2.setIdMatricula(folio.getIdMatricula());
		//folio2.setZonaRegistral(folio.getZonaRegistral());
		TurnoFolio tFolio = new TurnoFolio();
		tFolio.setTurno(evento.getTurno());
		folio2.addTurnosFolio(tFolio);
		Iterator itAnotaciones = evento.getAnotaciones().iterator();
		while(itAnotaciones.hasNext()){
			folio2.addAnotacione((Anotacion)itAnotaciones.next());
		}

		/*try {
			//de este método la anotación que queda en el padre
			//y los hijos con sus datos básicos
			foliosDerivados = forseti.segregarFolio(folio2,evento.getFoliosDerivados(),evento.getUsuarioNeg(), null);
		} catch (ForsetiException e) {
			throw new SegregacionException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}*/
		return new EvnRespCalificacion(foliosDerivados);
	}




	/**
		 * @param evento
		 * @return
		 */
		private EventoRespuesta crearFolioEnglobe(EvnCalificacion evento)throws EventoException {
			Folio folio = evento.getFolio();
			Usuario u = evento.getUsuarioNeg();

			/*try {
				List foliosFuente=evento.getFoliosFuente();
				Folio folioNuevo=evento.getFolio();
				Usuario usuario=evento.getUsuarioNeg();
				Hashtable tabla=evento.getTabla();
				//una vez se tengan definidos
				Folio folioCreado = forseti.englobarFolio(foliosFuente,folioNuevo,usuario,tabla, null, null);

			} catch (Throwable e){
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EnglobeNoEfectuadoException("No se pudo realizar el englobe",e);
			}*/
			//return new EvnRespCalificacion(folio);
			return null;
		}




		/**
		 * @param evento
		 * @return
		 */
		private EventoRespuesta consultaTemporal(EvnCalificacion evento) throws EventoException{
			Folio folio = evento.getFolio();
			Usuario u = evento.getUsuarioNeg();
			long local_EofTime = 0;
			long local_Duration = 0;
			long local_SofTime = 0;
			String msg = "";
			

			try {
				if(folio!=null && folio.getIdMatricula()!=null){

					FolioPk id=new FolioPk();
					id.idMatricula=folio.getIdMatricula();

					folio = forseti.getFolioByIDSinAnotaciones(id,u);

					if(folio.getEstado().getIdEstado().equals(CEstadoFolio.CERRADO)){
						throw new ConsultaCalificacionFolioException("El folio " + folio.getIdMatricula() + " se encuentra cerrado, se necesita la reapertura por parte del Coordinador Jurídico para poder ser calificado.");
					}

					Folio folioDef = forseti.getFolioByIDSinAnotaciones(id);
					//forseti.getDatosDefinitivosDeDatosTemporales(id,u);
					
					local_SofTime = System.currentTimeMillis();

					boolean esMayorExtension = forseti.mayorExtensionFolio(folio.getIdMatricula());
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION MAYOR EXTENSION FOLIO, (forseti.mayorExtensionFolio(): " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );
					
					local_SofTime = System.currentTimeMillis();
					
					long numeroAnotaciones = forseti.getCountAnotacionesFolio(folio.getIdMatricula());
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getCountAnotacionesFolio(: " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );

					
					local_SofTime = System.currentTimeMillis();
					
					List foliosHijos = forseti.getFoliosHijosOrdenAnotacion(id,u);
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getFoliosHijos(: " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );
					
					
					local_SofTime = System.currentTimeMillis();
					
					List foliosPadre = forseti.getFoliosPadre(id,u);
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getFoliosPadre(: " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );
					
					local_SofTime = System.currentTimeMillis();

					long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.GRAVAMEN);
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getCountAnotacionesFolio(GRAVAMEN): " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );
					
					local_SofTime = System.currentTimeMillis();
					
					List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN,0, (int)numeroGravamenes,u,true);
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getanotacionesFolio(GRAVAMEN, O)): " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );
					
					local_SofTime = System.currentTimeMillis();

					//long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getCountanotacionesFolio(MEDIDA CAUTELAR): " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );
					
					local_SofTime = System.currentTimeMillis();
					
                                        List medidasCautelares = forseti.getAnotacionesFolioNJ(id, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);

					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getAnotacionesFolio(MEDIDA CAUTELAR,0): " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );

					
					local_SofTime = System.currentTimeMillis();
					
					List salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, u);
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getAnotacionesConSalvedades(: " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );
					
					local_SofTime = System.currentTimeMillis();
					
					List cancelaciones = forseti.getAnotacionesConCancelaciones(id, u);
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getAnotacionesConCancelaciones(: " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );

					long numeroFalsaTradicion = 0;
					List falsaTradicion = null;
					List ordenFalsaTradicion = new ArrayList();
					List anotacionesInvalidas = null;
					List ordenAnotacionesInvalidas = new ArrayList();

					//CONSULTA ANOTACIONES DE FALSA TRADICION
					local_SofTime = System.currentTimeMillis();
					
					numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getCountAnotacionesFolio(FALSA TRADICION): " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );
					
					local_SofTime = System.currentTimeMillis();
					
					falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, u, true);
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getAnotacionesFolio(FALSA TRADICION): " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );
					
					if(falsaTradicion != null){
						Iterator it = falsaTradicion.iterator();
						while(it.hasNext()){
							Anotacion anotTemp = (Anotacion)it.next();
							ordenFalsaTradicion.add(anotTemp.getOrden());
						}
					}

					//CONSULTA ANOTACIONES INVALIDAS
					local_SofTime = System.currentTimeMillis();
					
					anotacionesInvalidas = forseti.getAnotacionesInvalidas(id, u);
					
					local_EofTime = System.currentTimeMillis();
					local_Duration = local_EofTime - local_SofTime;
					
					msg = "@@ DURACION forseti.getAnotacionesInvalidas(: " + local_Duration/1000;
					Log.getInstance().debug(ANCalificacion.class, msg );
					
					if(anotacionesInvalidas != null){
						Iterator it = anotacionesInvalidas.iterator();
						while(it.hasNext()){
							Anotacion anotTemp = (Anotacion)it.next();
							ordenAnotacionesInvalidas.add(anotTemp.getOrden());
						}
					}
					
					List anotacionesPatrimonioFamiliar = null;
					long numanotacionesPatrimonioFamiliar = 0;
					List anotacionesAfectacionVivienda = null;
					long munanotacionesAfectacionVivienda = 0;
					List anotacionesAfectacionVivienda2 = null;
					long munanotacionesAfectacionVivienda2 = 0;
					
					
//					CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
					numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
					anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
					
					//	CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
					munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
					anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
					
					// CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
					munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
					anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
					
					anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);

					String linderoTemporal = "";
					boolean linderoDigitacion=false;
					Turno turno=evento.getTurno();
					if(turno!=null && turno.getHistorials()!=null){
						List historia=turno.getHistorials();
						TurnoHistoria historial;
						for(int i=0;i<historia.size();i++){
							historial = (TurnoHistoria) historia.get(i);
							if(historial.getFase().equals(CFase.CAL_DIGITACION)){
								linderoDigitacion=true;
								break;
							}
						}
					}
					List dirTemporales = null;
					if(folio!=null && folio.getLindero()!=null && !folio.getLindero().equals("")){
						if(folioDef!=null){
							if(folioDef.getLindero()!=null){
								if(linderoDigitacion){
									linderoTemporal = folio.getLindero();
								}else{
									try{
										linderoTemporal = folio.getLindero().substring(folioDef.getLindero().length(),folio.getLindero().length());
									}catch(IndexOutOfBoundsException idx){
										linderoTemporal = folio.getLindero();	
									}
								}
							}else{
								linderoTemporal = folio.getLindero();
							}
							
							if(folioDef.getDirecciones()!=null && folio.getDirecciones()!=null){
								dirTemporales = new ArrayList();
								for(int i=folioDef.getDirecciones().size(); i<folio.getDirecciones().size(); i++){
									dirTemporales.add(new Boolean(true));
								}
							}
							
						}else{
							linderoTemporal = folio.getLindero();
							if(folio.getDirecciones()!=null){
								dirTemporales = new ArrayList();
								for(int i=0; i<folio.getDirecciones().size(); i++){
									dirTemporales.add(new Boolean(true));
								}
							}
						}
					}
                                        List historialAreas = null;
                                        String idMatricula = folio.getIdMatricula();
                                        historialAreas = forseti.getHistorialArea(idMatricula);
					EvnRespCalificacion evn = new EvnRespCalificacion(historialAreas, folio, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion, ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, linderoTemporal);
					evn.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
					evn.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
					evn.setDirTemporales(dirTemporales);
					habilitarEdicionCampos(evn, evento.getTurno(), forseti, id, foliosHijos, foliosPadre, u);
					List foliosDerivadoHijo=null;
					List foliosDerivadoPadre = null;
					if(id!=null && u !=null){
						foliosDerivadoHijo=forseti.getFoliosDerivadoHijos(id, u);
						if(foliosDerivadoHijo!=null)
							evn.setFoliosDerivadoHijo(foliosDerivadoHijo);
						foliosDerivadoPadre = forseti.getFoliosDerivadoPadre(id, u);
						if(foliosDerivadoPadre!=null)
							evn.setFoliosDerivadoPadre(foliosDerivadoPadre);
					}
                                        
					
					return evn;
				}
                throw new EventoException();
			}catch (ConsultaCalificacionFolioException exc){
				throw exc;
			} catch (Throwable e){
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(),e);
			}
		}

		/**
		 * @param evento
		 * @param folio
		 * @param foliosHijos
		 */
		private void habilitarEdicionCampos(EvnRespCalificacion evn, Turno turno, ForsetiServiceInterface forseti, FolioPk id, List foliosHijos, List foliosPadre, Usuario u,
				long numeroNatLinderos, long numeroNatLinderos2, long numeroNatLinderos3, long numeroNatLinderos4, long numeroNatDireccion, long numeroArea,long numeroAclaracion, 
				long numeroAnotacionesTemporal) throws Throwable {

			//long numeroNatCodCatatral = forseti.getCountAnotacionesFolio(id, CCriterio.TODAS_LAS_ANOTACIONES,null, u);
			//List natCodCatastral = forseti.getAnotacionesFolio(id, CCriterio.TODAS_LAS_ANOTACIONES, null,0, (int)numeroNatCodCatatral,u,true);
			
			List natDireccion = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA,0, (int)numeroNatDireccion,u,true);

			List natLinderos = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACTUALIZACION_DE_LINDEROS,0, (int)numeroNatLinderos,u,true);

			List natLinderos2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_CON_SUBSIDIO_FAMILIAR_DE_VIVIENDA,0, (int)numeroNatLinderos2,u,true);
			
			List natLinderos3 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_EN_SUELO_PROPIO,0, (int)numeroNatLinderos3,u,true);
			
			List natLinderos4 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.DECLARACION_PARTE_RESTANTE,0, (int)numeroNatLinderos4,u,true);
			
			List natArea = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACTUALIZACION_AREA,0, (int)numeroArea,u,true);
			
			List natAclaracion = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACLARACION,0, (int)numeroAclaracion,u,true);			

			// boolean tieneActo = (verificarActos(turno, CTipoActo.ID_ACTUALIZACION_DE_NOMENCLATURA)  || verificarActos(turno, CTipoActo.ID_ACLARACION));

			boolean segregado = esSegregadoTemporal(foliosHijos);
			boolean habilitarCodCatastral = false;
			if (numeroAnotacionesTemporal > 0) {
				habilitarCodCatastral = true;
			}
			//boolean habilitarCodCatastral = verificarHabilitarCodCatastral(natCodCatastral);
			boolean habilitarDireccion = verificarHabilitarDireccion(natDireccion);
			boolean habilitarLinderos = verificarHabilitarLinderos(natLinderos);
			
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natLinderos2);
			}
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natLinderos3);
			}
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natLinderos4);
			}
			
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natDireccion);
			}
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natArea);
			}
			
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natAclaracion);
			}				
			if(!habilitarCodCatastral){
				habilitarCodCatastral = verificarHabilitarCodCatastral(natAclaracion);
			}			
			if(!habilitarDireccion){
				habilitarDireccion = verificarHabilitarDireccion(natAclaracion);
			}
			
			boolean isCreadoEnCalificacion = this.isFolioCreadoCalificacion(evn.getFolio(), foliosHijos, foliosPadre);


			evn.setHabilitarEdicionCodCatastral(segregado || ( habilitarCodCatastral) || isCreadoEnCalificacion);
			evn.setHabilitarEdicionDireccion(segregado || ( habilitarDireccion) || isCreadoEnCalificacion);
			evn.setHabilitarEdicionLinderos(segregado || ( habilitarLinderos)|| isCreadoEnCalificacion);

		}

	/**
		 * @param evento
		 * @param folio
		 * @param foliosHijos
		 */
		private void habilitarEdicionCampos(EvnRespCalificacion evn, Turno turno, ForsetiServiceInterface forseti, FolioPk id, List foliosHijos, List foliosPadre, Usuario u) throws Throwable {

			//long numeroNatCodCatatral = forseti.getCountAnotacionesFolio(id, CCriterio.TODAS_LAS_ANOTACIONES,null, u);
			//List natCodCatastral = forseti.getAnotacionesFolio(id, CCriterio.TODAS_LAS_ANOTACIONES, null,0, (int)numeroNatCodCatatral,u,true);
			
			long numeroAnotacionesTemporal = forseti.getCountAnotacionesTMPFolio(id,u);
			
			long numeroNatDireccion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA, u);
			List natDireccion = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA,0, (int)numeroNatDireccion,u,true);

			long numeroNatLinderos = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_DE_LINDEROS, u);
			List natLinderos = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACTUALIZACION_DE_LINDEROS,0, (int)numeroNatLinderos,u,true);
			
			long numeroNatLinderos2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_EN_SUELO_PROPIO, u);
			List natLinderos2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_EN_SUELO_PROPIO,0, (int)numeroNatLinderos2,u,true);
			
			long numeroNatLinderos3 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_CON_SUBSIDIO_FAMILIAR_DE_VIVIENDA, u);
			List natLinderos3 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_CON_SUBSIDIO_FAMILIAR_DE_VIVIENDA,0, (int)numeroNatLinderos3,u,true);
			
			long numeroNatLinderos4 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_PARTE_RESTANTE, u);
			List natLinderos4 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.DECLARACION_PARTE_RESTANTE,0, (int)numeroNatLinderos4,u,true);


			long numeroArea = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_AREA, u);
			List natArea = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACTUALIZACION_AREA,0, (int)numeroArea,u,true);
			
			long numeroAclaracion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACLARACION, u);
			List natAclaracion = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACLARACION,0, (int)numeroAclaracion,u,true);			

			// boolean tieneActo = (verificarActos(turno, CTipoActo.ID_ACTUALIZACION_DE_NOMENCLATURA)  || verificarActos(turno, CTipoActo.ID_ACLARACION));

			boolean segregado = esSegregadoTemporal(foliosHijos);
			boolean habilitarCodCatastral = false;
			if (numeroAnotacionesTemporal > 0) {
				habilitarCodCatastral = true;
			}
			//boolean habilitarCodCatastral = verificarHabilitarCodCatastral(natCodCatastral);
			boolean habilitarDireccion = verificarHabilitarDireccion(natDireccion);
			boolean habilitarLinderos = verificarHabilitarLinderos(natLinderos);
			
			if(!habilitarLinderos){
                            habilitarLinderos = verificarHabilitarLinderos(natLinderos2);
			}
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natLinderos3);
			}
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natLinderos4);
			}
			
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natDireccion);
			}
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natArea);
			}
			
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natAclaracion);
			}				
			if(!habilitarCodCatastral){
				habilitarCodCatastral = verificarHabilitarCodCatastral(natAclaracion);
			}			
			if(!habilitarDireccion){
				habilitarDireccion = verificarHabilitarDireccion(natAclaracion);
			}
			
			boolean isCreadoEnCalificacion = this.isFolioCreadoCalificacion(evn.getFolio(), foliosHijos, foliosPadre);


			evn.setHabilitarEdicionCodCatastral(segregado || ( habilitarCodCatastral) || isCreadoEnCalificacion);
			evn.setHabilitarEdicionDireccion(segregado || ( habilitarDireccion) || isCreadoEnCalificacion);
			evn.setHabilitarEdicionLinderos(segregado || ( habilitarLinderos)|| isCreadoEnCalificacion);

		}

                /**
                 * @author      :   Henry Gómez Rocha
                 * @change      :   Se llama a los métodos forseti.getCountAnotacionesFolio(), forseti.getAnotacionesFolio(),  y forseti.getAnotacionesTMPFolioToInsert()
                 *                  con el último parametro con la variable validarTurno la cual está seteada a false para
                 *                  que cuando el proceso vaya del role Calificador a Digitador no verifique que usuario
                 *                  tiene el bloqueo.
                 * Caso Mantis  :   0004967
                 */
		private void habilitarEdicionCampos(EvnRespCalificacion evn, Turno turno, ForsetiServiceInterface forseti, FolioPk id, List foliosHijos, List foliosPadre, Usuario u, boolean validarTurno) throws Throwable {

			long numeroAnotacionesTemporal = forseti.getCountAnotacionesTMPFolio(id,u, validarTurno);

			long numeroNatDireccion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA, u, validarTurno);
			List natDireccion = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA,0, (int)numeroNatDireccion,u,true, validarTurno);

			long numeroNatLinderos = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_DE_LINDEROS, u, validarTurno);
			List natLinderos = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACTUALIZACION_DE_LINDEROS,0, (int)numeroNatLinderos,u,true, validarTurno);

			long numeroNatLinderos2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_EN_SUELO_PROPIO, u, validarTurno);
			List natLinderos2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_EN_SUELO_PROPIO,0, (int)numeroNatLinderos2,u,true, validarTurno);

			long numeroNatLinderos3 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_CON_SUBSIDIO_FAMILIAR_DE_VIVIENDA, u, validarTurno);
			List natLinderos3 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_CON_SUBSIDIO_FAMILIAR_DE_VIVIENDA,0, (int)numeroNatLinderos3,u,true, validarTurno);

			long numeroNatLinderos4 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_PARTE_RESTANTE, u, validarTurno);
			List natLinderos4 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.DECLARACION_PARTE_RESTANTE,0, (int)numeroNatLinderos4,u,true, validarTurno);


			long numeroArea = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_AREA, u, validarTurno);
			List natArea = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACTUALIZACION_AREA,0, (int)numeroArea,u,true, validarTurno);

			long numeroAclaracion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACLARACION, u, validarTurno);
			List natAclaracion = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.ACLARACION,0, (int)numeroAclaracion,u,true, validarTurno);

			// boolean tieneActo = (verificarActos(turno, CTipoActo.ID_ACTUALIZACION_DE_NOMENCLATURA)  || verificarActos(turno, CTipoActo.ID_ACLARACION));

			boolean segregado = esSegregadoTemporal(foliosHijos);
			boolean habilitarCodCatastral = false;
			if (numeroAnotacionesTemporal > 0) {
				habilitarCodCatastral = true;
			}
			//boolean habilitarCodCatastral = verificarHabilitarCodCatastral(natCodCatastral);
			boolean habilitarDireccion = verificarHabilitarDireccion(natDireccion);
			boolean habilitarLinderos = verificarHabilitarLinderos(natLinderos);

			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natLinderos2);
			}
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natLinderos3);
			}
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natLinderos4);
			}

			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natDireccion);
			}
			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natArea);
			}

			if(!habilitarLinderos){
				habilitarLinderos = verificarHabilitarLinderos(natAclaracion);
			}
			if(!habilitarCodCatastral){
				habilitarCodCatastral = verificarHabilitarCodCatastral(natAclaracion);
			}
			if(!habilitarDireccion){
				habilitarDireccion = verificarHabilitarDireccion(natAclaracion);
			}

			boolean isCreadoEnCalificacion = this.isFolioCreadoCalificacion(evn.getFolio(), foliosHijos, foliosPadre);


			evn.setHabilitarEdicionCodCatastral(segregado || ( habilitarCodCatastral) || isCreadoEnCalificacion);
			evn.setHabilitarEdicionDireccion(segregado || ( habilitarDireccion) || isCreadoEnCalificacion);
			evn.setHabilitarEdicionLinderos(segregado || ( habilitarLinderos)|| isCreadoEnCalificacion);

		}


		/**
	 * @param natLinderos
	 * @return
	 */
	private boolean verificarHabilitarLinderos(List lst) {
		ListIterator iter = lst.listIterator();
		while (iter.hasNext()){
			Anotacion next = (Anotacion) iter.next();
			if (next.isTemporal()){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param natDireccion
	 * @return
	 */
	private boolean verificarHabilitarDireccion(List lst) {
		ListIterator iter = lst.listIterator();
		while (iter.hasNext()){
			Anotacion next = (Anotacion) iter.next();
			if (next.isTemporal()){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param natCodCatastral
	 * @return
	 */
	private boolean verificarHabilitarCodCatastral(List lst) {
		ListIterator iter = lst.listIterator();
		while (iter.hasNext()){
			Anotacion next = (Anotacion) iter.next();
			if (next.isTemporal()){
				return true;
			}
		}
		return false;
	}

	/**
		 * @param folio
		 * @return
		 */
		private boolean esSegregadoTemporal(List hijos) {
			if(hijos == null){
				return false;
			}
			ListIterator iter = hijos.listIterator();
			while(iter.hasNext()){
				Folio hijo = (Folio) iter.next();
				if(!hijo.isDefinitivo()){
					return true;
				}
			}
			return false;
		}

		/**
		 * @param turno
		 * @return
		 */
		private boolean verificarActos(Turno turno, String idTipoActo) {
			SolicitudRegistro solicitud = ((SolicitudRegistro)turno.getSolicitud());
			List liqs = solicitud.getLiquidaciones();
			ListIterator iter = liqs.listIterator();
			while(iter.hasNext()){
				LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro) iter.next();
				List actos = liq.getActos();
				ListIterator iterActos = actos.listIterator();
				while(iterActos.hasNext()){
					Acto acto = (Acto) iterActos.next();
					String tmpIdTipoActo = acto.getTipoActo().getIdTipoActo();
					if (tmpIdTipoActo.equals(idTipoActo)){
						return true;
					}
				}
			}
			return false;
		}



	//VALIDACION NOTAS INFORMATIVAS. ENERO 19 2006
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta avanzarDesbloquear(EvnCalificacion evento) throws EventoException{
		Turno turno = evento.getTurno();
		TurnoPk oid=new TurnoPk();
		oid.anio=turno.getAnio();
		oid.idCirculo=turno.getIdCirculo();
		oid.idProceso=turno.getIdProceso();
		oid.idTurno=turno.getIdTurno();
		Usuario u=evento.getUsuarioNeg();
		if(u==null){
			throw new EventoException("el usuario es nulo");
		}

		boolean Anotmp;

		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}


		/*
		boolean okFinalizacion= false;
		try {
			okFinalizacion=forseti.validarTerminacionCalificacion(oid,CTipoRevisionCalificacion.TODOS_FOLIOS_UNA_ANOTACION);
		} catch (Throwable e1) {

			e1.printStackTrace();
			throw new EventoException("No se pudo validar la finalización de la Calificacion",e1);
		}

		if (!okFinalizacion)
		  throw new EventoException("Hay folios sin anotaciones temporales, no se puede Finalizar la calificación.");
		*/

		try{
		    List solicitudesFoliosCalificados = null;
		    solicitudesFoliosCalificados =this.getSolicitudesFolioCalificadas(forseti, oid, u);
		    Collections.sort(solicitudesFoliosCalificados, new IDMatriculaFolioComparator());
			turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
		}catch (Throwable e2){
			ExceptionPrinter ep=new ExceptionPrinter(e2);
			Log.getInstance().error(ANCalificacion.class,"No se pudieron desbloquear los folios:"+ep.toString());
			throw new EventoException("No se pudieron desbloquear los folios:"+e2.getMessage(),e2);
		}

		return avanzar(evento, true);
	}





	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta avanzarFirma(EvnCalificacion evento) throws EventoException{

		Turno turno = evento.getTurno();
		TurnoPk oid=new TurnoPk();
		oid.anio=turno.getAnio();
		oid.idCirculo=turno.getIdCirculo();
		oid.idProceso=turno.getIdProceso();
		oid.idTurno=turno.getIdTurno();
		Usuario u=evento.getUsuarioNeg();
		boolean Anotmp;

		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}



		try {
			List turnos = new ArrayList();
			turnos.add(oid);
			forseti.validarPrincipioPrioridadFirma(turnos);
		} catch (ForsetiException e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			throw new ValidacionParametrosHTException(e1);
		} catch (Throwable e2){
			ExceptionPrinter ep=new ExceptionPrinter(e2);
			throw new EventoException("No se pudieron desbloquear los folios:"+e2.getMessage(),e2);
		}

		/*
		boolean okFinalizacion= false;
		try {
			okFinalizacion=forseti.validarTerminacionCalificacion(oid,CTipoRevisionCalificacion.TODOS_FOLIOS_UNA_ANOTACION);
		} catch (Throwable e1) {

			e1.printStackTrace();
			throw new EventoException("No se pudo validar la finalización de la Calificacion",e1);
		}

		if (!okFinalizacion)
		  throw new EventoException("Hay folios sin anotaciones temporales, no se puede Finalizar la calificación.");
		*/



		SolicitudRegistro solReg = (SolicitudRegistro)turno.getSolicitud();
		if(solReg.getLiquidaciones().size()>0){
			LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro)solReg.getLiquidaciones().get(0);
			List actos = liquidacion.getActos();
			boolean testamento = tieneTestamentos(actos);

			if(!testamento){

				SolicitudRegistro solicitud = (SolicitudRegistro)turno.getSolicitud();
				List folios= solicitud.getSolicitudFolios();

				try{
				    List solicitudesFoliosCalificados = null;
				    solicitudesFoliosCalificados =this.getSolicitudesFolioCalificadas(forseti, oid, u);
					turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
					Iterator itf = folios.iterator();
					for(;itf.hasNext();){
						SolicitudFolio sf=(SolicitudFolio) itf.next();
						Folio f=sf.getFolio();
						forseti.hacerDefinitivoFolio(f, u, oid, true);
					}

				} catch (ForsetiException e1) {
					ExceptionPrinter ep=new ExceptionPrinter(e1);
					Log.getInstance().error(ANCalificacion.class,"No se pudieron hacer definitivos los folios:"+ep.toString());
					throw new EventoException("No se pudieron hacer definitivos los folios:"+e1.getMessage(),e1);
				}catch (Throwable e2){
					ExceptionPrinter ep=new ExceptionPrinter(e2);
					Log.getInstance().error(ANCalificacion.class,"No se pudieron desbloquear los folios:"+ep.toString());
					throw new EventoException("No se pudieron desbloquear los folios:"+e2.getMessage(),e2);
				}
			}
		}

		return avanzar(evento, false);
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta devolverFirma(EvnCalificacion evento) throws EventoException{
		Turno turno = evento.getTurno();
		TurnoPk oid=new TurnoPk();
		oid.anio=turno.getAnio();
		oid.idCirculo=turno.getIdCirculo();
		oid.idProceso=turno.getIdProceso();
		oid.idTurno=turno.getIdTurno();
		Usuario u=evento.getUsuarioNeg();
		boolean Anotmp;


		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}catch(HermodException t)
		{
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para devolver el turno a calificación");
		}catch (Throwable e2){
			ExceptionPrinter ep=new ExceptionPrinter(e2);
			Log.getInstance().error(ANCalificacion.class,"No se pudieron desbloquear los folios:"+ep.toString());
			throw new EventoException("No se pudieron desbloquear los folios:"+e2.getMessage(),e2);
		}

		return avanzar(evento,false);
	}

    private EventoRespuesta avanzarTramiteSuspension(Evento ev) throws EventoException {

        EvnCalificacion evento = (EvnCalificacion) ev;
        Turno turno = evento.getTurno();
        Fase fase = evento.getFase();
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNeg();
        String idUsuario = usuarioNeg.getUsername();
        String respuestaWF = evento.getRespuestaWf();

        try {
            
            Hashtable parametros = new Hashtable();
            parametros.put(CInfoUsuario.USUARIO_SIR, idUsuario);
            parametros.put("RESULT", respuestaWF);

            try {
                hermod.avanzarTurnoNuevoNormal(turno, fase, parametros, usuarioNeg);
            } catch (Throwable e) {
                throw new TramiteSuspensionException("Error avanzando el turno en el servicio hermod.", e);
            }

        } catch (Exception e) {
            throw new TramiteSuspensionException("No se pudo confirmar el tramite", e);
        }
        return null;
    }


    //DEPURADO ENERO 19 2006
	/**
	* Avanza un turno de registro desde la fase de entrega.
	* Adicionalmente si el turno tiene certificados asociados, se encarga de finalizar también dichos
	* turnos.
	* Genera excepciones si:
	* 1. No pudo instanciarse el servicio hermod.
	*/
    private EventoRespuesta avanzarEntrega(Evento ev) throws EventoException {

        //1. Se obtienen los parámetros desde el evento de calificación.
        EvnCalificacion evento = (EvnCalificacion) ev;
        Turno turno = evento.getTurno();
        Fase fase = evento.getFase();
        gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNeg();
        String idUsuario = usuarioNeg.getUsername();
        String respuestaWF = evento.getRespuestaWf();

        // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
        //del turno desde esta fase.
        try {
            Hashtable tablaAvance = new Hashtable(2);
            tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
            tablaAvance.put(CInfoUsuario.USUARIO_SIR, usuarioNeg.getUsername());
            hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
        } catch (Throwable t) {
            throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
        }

        // 2. Avanzar el turno de registro.
        Hashtable parametros = new Hashtable();

        //2.1 Asociar parámetros de avance en la Hashtable.
        parametros.put(CInfoUsuario.USUARIO_SIR, idUsuario);
        parametros.put("RESULT", respuestaWF);

        // 3. Avanzar los turnos de certificados asociados (Si aplica).
        // Constantes para identificador de fase y proceso de los certificados asociados
        String idFaseCertificados = "CER_ENTREGAR";
        long idProceso = 1;

        //Sacar las solicitudes de certificados de la solicitud de registro
        List solicitudesHijas = null;
        SolicitudRegistro solicitudRegistro = (SolicitudRegistro) turno.getSolicitud();

        if (solicitudRegistro != null) {
            //Obtener listado de solicitudes hijas.
            solicitudesHijas = solicitudRegistro.getSolicitudesHijas();
        }

        List Valido = new Vector();
        List solCerts = new Vector();
        Iterator isolh = null;

        if (solicitudesHijas != null) {
            isolh = solicitudesHijas.iterator();
        }

        //Obtener las solicitudes Asociadas
        for (; isolh.hasNext();) {

            //Se agregan todas las solicitudes de certificados a la lista solCerts.
            SolicitudAsociada solA = (SolicitudAsociada) isolh.next();
            SolicitudCertificado solC = null;
            if (solA != null) {
                solC = (SolicitudCertificado) solA.getSolicitudHija();
                if (solC != null) {
                    solCerts.add(solC);
                }

            }
        }

        // Avanzar los turnos de los Certificados Asociados
        try {

            Iterator icerts = solCerts.iterator();

            //Creación de Tabla para paso de parámetros.
            Hashtable tabla = new Hashtable();
            tabla.put(Processor.RESULT, EvnCalificacion.WF_CERTIFICADOS_ENTREGA_OK);
            tabla.put(CInfoUsuario.USUARIO_SIR, idUsuario);

            while (icerts.hasNext()) {

                try {
                    //obtener solicitud Certificado
                    SolicitudCertificado solCerti = (SolicitudCertificado) icerts.next();
                    if (solCerti != null) {
                        SolicitudPk sid = new SolicitudPk();
                        sid.idSolicitud = solCerti.getIdSolicitud();

                        //obtener turno , circulo y fase
                        turno = hermod.getTurnoBySolicitud(sid);
                        String idFase = turno.getIdFase();
                        fase = hermod.getFase(idFase);
                        String idCirculo = turno.getIdCirculo();

                        //validar que el turno se encuentre en la fase y procesos correctos.
                    }
                } catch (Throwable t) {
                    Log.getInstance().error(ANCalificacion.class, t.getMessage(), t);
                }
            }

        } catch (Throwable e) {
            Log.getInstance().error(ANCalificacion.class, e.getMessage(), e);
            throw new EventoException(e.getMessage(), e);
        }

        return null;

    }


        /**
         *
         * @see getSolicitudesFolioCalificadas
         * */
        private java.util.List
        calificacion_ReimpresionSellos_PrintSelected_GetSolicitudesFolioCalificadas(
              ForsetiServiceInterface forseti
            , TurnoPk oid
            , Usuario u
        )
        throws Throwable {
                List deltaFolios = null;
                // VERIFY: cambiar por nuevo servicio de fabian
                // deltaFolios = forseti.getDeltaFolios(oid,u);
                // deltaFolios = forseti.getCalificacionTurno( oid,u );
                deltaFolios = forseti.getCalificacionTurno( oid /*,u*/ );


                Vector solicitudesFoliosCalificados = new Vector();
                for( int i=0; i < deltaFolios.size();i++ ) {
                        SolicitudFolio solFolioDelta =(SolicitudFolio)deltaFolios.get(i);
                        Folio folioDelta = solFolioDelta.getFolio();

                        FolioPk fid = new FolioPk();
                        fid.idMatricula = solFolioDelta.getIdMatricula();

                        Folio folioCal=forseti.getFolioByIDSinAnotaciones(fid, null);
                        folioCal.setCodCatastral(folioDelta.getCodCatastral());
                        folioCal.setTipoPredio(folioDelta.getTipoPredio());

                        List anotaciones =folioDelta.getAnotaciones();
                        for( int j=0; j<anotaciones.size();j++ ) {
                                Anotacion anota = (Anotacion)anotaciones.get(j);
                                folioCal.addAnotacione(anota);

                        }

                        SolicitudFolio solFolioCal = new SolicitudFolio();
                        solFolioCal.setFolio(folioCal);
                        solicitudesFoliosCalificados.add(solFolioCal);
                }

                return solicitudesFoliosCalificados;

        }



	private List getSolicitudesFolioCalificadas(ForsetiServiceInterface forseti,TurnoPk oid, Usuario u) throws Throwable
		{
			List deltaFolios = forseti.getDeltaFolios(oid,u);
			Vector solicitudesFoliosCalificados = new Vector();
			for (int i=0; i<deltaFolios.size();i++)
			{
				SolicitudFolio solFolioDelta =(SolicitudFolio)deltaFolios.get(i);
				Folio folioDelta = solFolioDelta.getFolio();
				
				FolioPk fid = new FolioPk();
				fid.idMatricula = solFolioDelta.getIdMatricula();

				Folio folioCal=forseti.getFolioByIDSinAnotaciones(fid, u);
				
				FolioDatosTMPPk fdtId = new FolioDatosTMPPk();
				fdtId.idMatricula = solFolioDelta.getIdMatricula();
				
				FolioDatosTMP folioDatosTMP = forseti.getFolioDatosTMP(fdtId);
				
				if(folioDelta.getCodCatastral()!=null && 
						folioDatosTMP.getNumRadicaCodCatastral() !=null && 
						folioDatosTMP.getNumRadicaCodCatastral().equals(oid.anio + "-" + oid.idCirculo + "-" + oid.idProceso + "-" + oid.idTurno)){
					folioCal.setCodCatastral(folioDelta.getCodCatastral());
				}
				if(folioDelta.getTipoPredio()!=null && 
						folioDatosTMP.getNumRadicaTipoPredio() != null &&
						folioDatosTMP.getNumRadicaTipoPredio().equals(oid.anio + "-" + oid.idCirculo + "-" + oid.idProceso + "-" + oid.idTurno)){
					folioCal.setTipoPredio(folioDelta.getTipoPredio());
				}
				List anotaciones =folioDelta.getAnotaciones();
				for(int j=0; j<anotaciones.size();j++) {
					Anotacion anota = (Anotacion)anotaciones.get(j);
					if (anota.getNumRadicacion() != null && anota.getNumRadicacion().equals(oid.anio + "-" + oid.idCirculo + "-" + oid.idProceso + "-" + oid.idTurno))
					{
						folioCal.addAnotacione(anota);
					}
				}

				SolicitudFolio solFolioCal = new SolicitudFolio();
				solFolioCal.setFolio(folioCal);
				solicitudesFoliosCalificados.add(solFolioCal);
			}
			return solicitudesFoliosCalificados;
		}

protected boolean calificacion_ReimpresionSellos_IsActiveOption( TurnoPk turno_Id, int typeId  )throws Throwable {


    boolean optionReproduccionSellos_Enabled = false;

    try {

        gov.sir.core.negocio.modelo.TipoActoPk tipoActo_ID = new gov.sir.core.negocio.modelo.TipoActoPk();
        tipoActo_ID.idTipoActo = Integer.toString( typeId );


        // TODO: llamar servicio fabian
        optionReproduccionSellos_Enabled
        = hermod.hasActoTurnoRegistro( turno_Id , tipoActo_ID );

        // optionReproduccionSellos_Enabled = true;
        /*
        if( !optionReproduccionSellos_Enabled ){
            throw new Exception( "Verificacion de permiso sobre reproduccion de sellos ha fallado; " );
        }
        */


    }
    catch (Throwable e1) {
        throw new Exception( "Verificacion de permiso sobre reproduccion de sellos ha fallado; ", e1 );
    }

    return optionReproduccionSellos_Enabled;
    }

    /**
     * @param evento
     * @return
     */
    private EventoRespuesta tomarTurno(EvnCalificacion evento) throws EventoException {

        Usuario usuarioNeg = evento.getUsuarioNeg();
        Turno turno = evento.getTurno();
        SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();
        List folios = solicitud.getSolicitudFolios();
        List matriculas = new ArrayList();
        List datosRespuestaTrams = null;
        //forseti.validarNuevasAnotacionesTurno(turno.getIdTurno);
        Iterator it = folios.iterator();
        //List TurnosCorreccion = new ArrayList();
        while (it.hasNext()) {
            SolicitudFolio solF = (SolicitudFolio) it.next();
            matriculas.add(solF.getFolio().getIdMatricula());

            /*try {
						List TurnosCorreccionAux = forseti.getTurnosCorreccionActivosFolio(solF.getFolio(), turno);
						if(TurnosCorreccionAux != null && TurnosCorreccionAux.size() > 0)
							TurnosCorreccion.addAll(TurnosCorreccionAux);
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new TomarTurnoRegistroException(e.getMessage());
					}*/
        }

        // opciones para permiso reimpresion sellos
        final int REPRODUCCIONSELLOS_ACTOTYPEID = 12;
        boolean optionReproduccionSellos_Enabled = false;

        try {
            TurnoPk turnoID = new TurnoPk();
            turnoID.idCirculo = turno.getIdCirculo();
            turnoID.idProceso = turno.getIdProceso();
            turnoID.idTurno = turno.getIdTurno();
            turnoID.anio = turno.getAnio();

            //List solfolios
            forseti.validarPrincipioPrioridadCorreccion(turnoID);
            datosRespuestaTrams = fenrir.consultarRespuestasUsuarios((int)usuarioNeg.getIdUsuario(), turno.getIdWorkflow());

            /*Hashtable tablaAnotacionesTemporales = new Hashtable();
				tablaAnotacionesTemporales = forseti.validarNuevasAnotacionesTurno(turnoID);*/
            //Boolean tieneAnotacionesTemporales = (Boolean)tablaAnotacionesTemporales.get(solFolio.getIdMatricula());
            /*Iterator it2=folios.iterator();
				Boolean tieneAnotacionesTemporales = new Boolean(false);
				while(it2.hasNext() && !tieneAnotacionesTemporales.booleanValue()){
						SolicitudFolio solFTieneAnotaciones=(SolicitudFolio)it2.next();
						tieneAnotacionesTemporales = (Boolean)tablaAnotacionesTemporales.get(solFTieneAnotaciones.getIdMatricula());
				}*/
            //forseti.validarPrincipioPrioridadCalificacion(turnoID, usuarioNeg);
            /**
             * ***
             * esta es la funcion que sirve pero por el momento 
				*
             */
            boolean isTurnoValidoCalificacion = forseti.isTurnoValidoCalificacion(turnoID, usuarioNeg);
            /*String strEstaEnCorreccion = metodoGeneralTurnosCorreecion(folios,turno);
				if(strEstaEnCorreccion != null && !strEstaEnCorreccion.equals("")){
					Throwable e1 = new Throwable(); 					
					throw new TomarTurnoRegistroException(strEstaEnCorreccion,e1);
				}*/

 /*String msgTurnosCorreccion = "\n Turnos de correcciones relacionados con uno o mas folios de este turno";
				
				if(TurnosCorreccion != null){
					Iterator iterCorrecciones  = TurnosCorreccion.iterator();
					while(iterCorrecciones.hasNext()){
						Turno turnoCorrec = (Turno)iterCorrecciones.next();
						if(turnoCorrec.getIdTurno() != null){
							msgTurnosCorreccion +=turnoCorrec.getIdTurno()+"  "; 
						}
					}
				}
				
			
				String msgAnotaciones = "";
				if(tieneAnotacionesTemporales.booleanValue()  && TurnosCorreccion != null && TurnosCorreccion.size()>0){
					msgAnotaciones = "\n el turno contiene folios  los cuales tienen anotaciones temporales y debe" +
								     "\n borrar los temporales del Boton 'Deshacer Cambios' para que pueda seguir " +
								     "\n -el turno en la fase de correcciones";
					
					msgAnotaciones += msgTurnosCorreccion;
					
					Throwable e1 = new Throwable(); 
					ExceptionPrinter ep=new ExceptionPrinter(e1);
					//logger.error("No fue posible tomar el turno:"+ep.toString());
					throw new TomarTurnoRegistroException("el turno no puede seguir por que hay folio(s) relacionados con turno(s) de Correccion"+msgAnotaciones,e1);
				}else if(TurnosCorreccion != null && TurnosCorreccion.size()>0){

					Throwable e1 = new Throwable(); 
					ExceptionPrinter ep=new ExceptionPrinter(e1);
					//logger.error("No fue posible tomar el turno:"+ep.toString());
					throw new TomarTurnoRegistroException("el turno no es valido por que esta en la fase correccion. "+msgTurnosCorreccion,e1);
				}*/
            if (isTurnoValidoCalificacion) {
                boolean desbloqueoFolio = forseti.desbloquearFoliosEntradaCalificacion(matriculas, usuarioNeg);
                if (desbloqueoFolio) {
                    forseti.delegarBloqueoFolios(turnoID, usuarioNeg);
                }

                /* Se 
					//Si los folios estan bloqueados por el mismo pero con un turno posterior, se le debe quitar el bloqueo
					forseti.delegarBloqueoFoliosObligatorio(turnoID,usuarioNeg);
                 */
            }

            optionReproduccionSellos_Enabled = calificacion_ReimpresionSellos_IsActiveOption(turnoID, REPRODUCCIONSELLOS_ACTOTYPEID);
        } catch (ForsetiException e1) {
            Hashtable errores = e1.getHashErrores();
            if (errores != null && !errores.isEmpty()) {
                String mensaje = "No fue posible bloquear las matrículas:\n";
                Enumeration enumeration = errores.keys();
                while (enumeration.hasMoreElements()) {
                    String matricula = (String) enumeration.nextElement();
                    String razon = (String) errores.get(matricula);
                    mensaje.concat(matricula + " : " + razon + "\n");
                }
                ExceptionPrinter ep = new ExceptionPrinter(e1);
                Log.getInstance().error(ANCalificacion.class, mensaje + ep.toString());
                throw new TomarTurnoCalificacionHTException(e1);
            }
            ExceptionPrinter ep = new ExceptionPrinter(e1);
            Log.getInstance().error(ANCalificacion.class, "No fué posible tomar el turno:" + ep.toString());
            throw new TomarTurnoRegistroException("No fué posible tomar el turno: " + e1.getMessage(), e1);

        } catch (Throwable e1) {
            ExceptionPrinter ep = new ExceptionPrinter(e1);
            Log.getInstance().error(ANCalificacion.class, "No fue posible tomar el turno:" + ep.toString());
            throw new TomarTurnoRegistroException("No fué posible tomar el turno: " + e1.getMessage(), e1);
        }

        CirculoPk id = new CirculoPk();
        id.idCirculo = turno.getIdCirculo();
        EvnRespCalificacion respuesta = new EvnRespCalificacion(EvnRespCalificacion.VERIFICACION_ACTOS_REGISTRO);
        respuesta.setTipoEvento(EvnRespCalificacion.VERIFICACION_ACTOS_REGISTRO);
        respuesta.setValidacionVerificacionActos(verificarAlertaActos(id, turno));
        respuesta.setListaRespuestaTrams(datosRespuestaTrams);        

        try {
            respuesta.setPlazoVencimiento(hermod.getPlazoVencimientoRegistroActos());
        } catch (Throwable e2) {
            ExceptionPrinter ep = new ExceptionPrinter(e2);
            Log.getInstance().error(ANCalificacion.class, "No fue posible tomar el turno: " + ep.toString());
            throw new TomarTurnoRegistroException("No fué posible tomar el turno:" + e2.getMessage(), e2);
        }

        // opcion de visualizacion
        // "reproduccion de sellos"
        
        respuesta.setOptionReproduccionSellos_Enabled(optionReproduccionSellos_Enabled);
        return respuesta;
    }
		
		
		
		
		private void metodoGeneralTurnosCorreecion(List folios,Turno turno) throws EventoException {
			Hashtable ht = new Hashtable();
			boolean tieneTemporales = false;
			Iterator it=folios.iterator();
			List TurnosCorreccion = new ArrayList();
			String StrFolio = "";
			while(it.hasNext()){
					SolicitudFolio solF=(SolicitudFolio)it.next();
					try {
						List TurnosCorreccionAux = forseti.getTurnosCorreccionActivosFolio(solF.getFolio(), turno);
						if(TurnosCorreccionAux != null && TurnosCorreccionAux.size() > 0){
							TurnosCorreccion.addAll(TurnosCorreccionAux);
							StrFolio = solF.getFolio().getIdMatricula();
							ht.put(StrFolio, this.listaTurnosWF(TurnosCorreccionAux));
						}
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new TomarTurnoRegistroException(e.getMessage());
					}
			}

			TurnoPk turnoID = new TurnoPk();
			turnoID.idCirculo = turno.getIdCirculo();
			turnoID.idProceso = turno.getIdProceso();
			turnoID.idTurno = turno.getIdTurno();
			turnoID.anio = turno.getAnio();
			
			Hashtable tablaAnotacionesTemporales = new Hashtable();
			try {
					tablaAnotacionesTemporales = forseti.validarNuevasAnotacionesTurno(turnoID);
								
				
				Iterator it2=folios.iterator();
				Boolean tieneAnotacionesTemporales = new Boolean(false);
				while(it2.hasNext() && !tieneAnotacionesTemporales.booleanValue()){
						SolicitudFolio solFTieneAnotaciones=(SolicitudFolio)it2.next();
						tieneAnotacionesTemporales = (Boolean)tablaAnotacionesTemporales.get(solFTieneAnotaciones.getIdMatricula());
				}
				if(tieneAnotacionesTemporales.booleanValue()  && TurnosCorreccion != null && TurnosCorreccion.size()>0){
					tieneTemporales = true;
				}
			} catch (Throwable e) {				
				e.printStackTrace();
			}
			
			if(ht.size()>0){
				ValidacionParametrosHTException ex = new ValidacionParametrosHTException(ht);
				String mensaje = "El turno no puede avanzar por que hay folio(s) asociados a turno(s) de Correccion.";
				if(tieneTemporales){
					mensaje += " Debe eliminar los datos temporales.";
				}
				ex.setMensajeFormulario(mensaje);
				
				throw ex;
			}
		}


		/**
		 * Método que lanza una excepción si el turno no cumple con el principio de prioridad de la fase 
		 * de calificación
		 * 
		 * @param evento
		 * @return
		 */
		private EventoRespuesta validarTurnoParaCalificacion(EvnCalificacion evento) throws EventoException {

		Usuario usuarioNeg = evento.getUsuarioNeg();
		Turno turno = evento.getTurno();
		
		

		try {
			TurnoPk turnoID = new TurnoPk();
			turnoID.idCirculo = turno.getIdCirculo();
			turnoID.idProceso = turno.getIdProceso();
			turnoID.idTurno = turno.getIdTurno();
			turnoID.anio = turno.getAnio();

			 forseti.validarPrincipioPrioridadCalificacion(turnoID, usuarioNeg);

		} catch (ForsetiException e1) {

			ValidacionParametrosHTException ex = new ValidacionParametrosHTException(e1);

			throw new ValidacionParametrosHTException(e1);

		} catch (Throwable e1) {
			throw new TomarTurnoRegistroException("No fué posible tomar el turno:" + e1.getMessage(), e1);
		}

		EvnRespCalificacion respuesta = new EvnRespCalificacion(EvnRespCalificacion.VALIDAR_TURNO_PARA_CALIFICACION, EvnRespCalificacion.VALIDAR_TURNO_PARA_CALIFICACION);

		return respuesta;
	}



		/**
		 * @param evento
		 * @return
		 */
		private EventoRespuesta desasociarFolios(EvnCalificacion evento) throws EventoException {

			// OBTENER PARAMETROS DEL EVENTO
			Turno turno = evento.getTurno();

			//OBTENER ID DEL TURNO Y DEL FOLIO
			TurnoPk id = new TurnoPk();
			id.anio = turno.getAnio();
			id.idCirculo = turno.getIdCirculo();
			id.idProceso = turno.getIdProceso();
			id.idTurno = turno.getIdTurno();


			Hashtable ht = new Hashtable();
			boolean temporalTurno = false;
			FolioPk idFolio = new FolioPk();

			List folios = evento.getFoliosFuente();
			Iterator it = folios.iterator();

			while (it.hasNext()) {
				Folio folio = (Folio)it.next();

				try {
					//SE OBTIENE LA INFORMACIÓN PARA DETERMINAR SI SE PUEDE O NO DESASOCIAR LAS MATRÍCULAS.
					//NO SE PUEDE CUANDO EL FOLIO TIENE COSAS TEMPORALES.
					idFolio.idMatricula = folio.getIdMatricula();
					temporalTurno = false;

					temporalTurno = forseti.hasDatosTemporalesTurno(id, idFolio);

					if(temporalTurno){
						ht.put(folio.getIdMatricula(), "No se puede desasociar el folio " + folio.getIdMatricula() + " porque tiene modificaciones temporales.");
					}

				} catch (Throwable e) {
					ExceptionPrinter ep = new ExceptionPrinter(e);
					Log.getInstance().error(ANCalificacion.class,"No se pudo desasociar la matrícula" + ep.toString());
					throw new TomarTurnoRegistroException("No se pudo desasociar la matrícula",e);
				}

			}


			//SI HAY FOLIOS QUE NO PUEDEN DESASOCIARSE SE LANZA LA EXCEPCIÓN.
			if(ht.size()>0){
				TomarTurnoCalificacionHTException excepcion = new TomarTurnoCalificacionHTException(ht);
				throw excepcion;
			}

			String idMatricula = null;
			try {
				//SE REMUEVEN LOS FOLIOS, SI LOS FOLIOS ESCOGIDOS PUEDEN SER DESASOCIADOS DEL TURNO
				it = folios.iterator();
				while (it.hasNext()) {
					Folio folio = (Folio)it.next();
					idMatricula = folio.getIdMatricula();
					hermod.removeFolioFromTurno(idMatricula, id);
					forseti.desbloquearFolio(folio);
				}
				turno = hermod.getTurno(id);
				//TFS 5351: Registrar la ultima matricula desasociada del turno
				hermod.registrarMatriculaEliminadaTurno(idMatricula, id);
			}catch (Throwable e) {
				ExceptionPrinter ep = new ExceptionPrinter(e);
				Log.getInstance().error(ANCalificacion.class,"No se pudo eliminar la matrícula:" + ep.toString());
				throw new TomarTurnoRegistroException("No se pudo eliminar la matrícula",e);
			}

			return new EvnRespCalificacion(turno,EvnRespCalificacion.DESASOCIAR_FOLIOS);

		}






		/**
		 * @param evento
		 * @return
		 */
		private EventoRespuesta cancelarAnotacionTemporal(EvnCalificacion evento) throws EventoException {

			Folio devuelve=null;
			List listaATemp=null;

			Usuario usuarioNeg=evento.getUsuarioNeg();
			Folio nuevo=new Folio();
			Turno turno = evento.getTurno();


			nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
			//nuevo.setZonaRegistral(evento.getFolio().getZonaRegistral());
			Anotacion anotacion= evento.getAnotacion();
			nuevo.addAnotacione(anotacion);
			try {
				if(forseti.updateFolio(nuevo,usuarioNeg, null, false)){
				FolioPk id=new FolioPk();
				id.idMatricula=evento.getFolio().getIdMatricula();
				devuelve = forseti.getFolioByIDSinAnotaciones(id);
				listaATemp= forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg);

				if(turno!=null){
					TurnoPk idTurno = new TurnoPk();
					idTurno.anio = turno.getAnio();
					idTurno.idCirculo = turno.getIdCirculo();
					idTurno.idProceso = turno.getIdProceso();
					idTurno.idTurno = turno.getIdTurno();

					turno = hermod.getTurno(idTurno);
					/**
					 * @author David Panesso
					 * @change 1253.CALIFICACION.FOLIOS.CERRADOS
					 * Se toma el estado del folio para manejar la excepción null para que guarde si es el folio está activo o cerrado.
					 **/
					try {
						Solicitud sol = turno.getSolicitud();
						List folios = sol.getSolicitudFolios();
						Iterator ifol = folios.iterator();
						while (ifol.hasNext()) {
							SolicitudFolio sfol = (SolicitudFolio) ifol.next();
							Folio fol = sfol.getFolio();
							FolioPk fid = new FolioPk();
							fid.idMatricula = fol.getIdMatricula();
							EstadoFolio est = new EstadoFolio();
							if (forseti.cerradoFolio(fid.idMatricula, null)) {
								est.setIdEstado(CEstadoFolio.CERRADO);
							} else {
								est.setIdEstado(CEstadoFolio.ACTIVO);
							}
							fol.setEstado(est);
							sfol.setFolio(fol);
						}
					} catch (Throwable e1) {
						e1.printStackTrace();
						throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti", e1);
					}
				}
				}
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANCalificacion.class,"No se pudo cancelar la anotacion:"+ep.toString());
				throw new CancelarAnotacionNoEfectuadaException("No se pudo cancelar la anotacion",e1);
			}


			//SE CONSULTA EL FOLIO PARA MOSTRAR LA INFORMACIÓN DEL MISMO
			try {
				if(nuevo!=null && nuevo.getIdMatricula()!=null){

					FolioPk id=new FolioPk();
					id.idMatricula=nuevo.getIdMatricula();

					nuevo = forseti.getFolioByIDSinAnotaciones(id,usuarioNeg);
					Folio folioDef = forseti.getFolioByIDSinAnotaciones(id);
					//forseti.getDatosDefinitivosDeDatosTemporales(id,usuarioNeg);

					boolean esMayorExtension = forseti.mayorExtensionFolio(nuevo.getIdMatricula());

					long numeroAnotaciones = forseti.getCountAnotacionesFolio(nuevo.getIdMatricula());

					List foliosHijos = forseti.getFoliosHijos(id,usuarioNeg);
					List foliosPadre = forseti.getFoliosPadre(id,usuarioNeg);
                                        List historialAreas = forseti.getHistorialArea(nuevo.getIdMatricula());

					long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.GRAVAMEN);
					List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN,0, (int)numeroGravamenes,usuarioNeg, true);

					long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
					List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR,0, (int)numeroMedidasCautelares,usuarioNeg,true);

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
					
//					CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
					numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
					anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
					
					//	CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
					munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
					anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
					
					// CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
					munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
					anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
					
					anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);

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

					EvnRespCalificacion evnResp = new EvnRespCalificacion (historialAreas, nuevo, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion , ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, listaATemp, linderoTemporal);
					evnResp.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
					evnResp.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
					evnResp.setTurno(turno);
					return evnResp;
				}
				throw new EventoException();
			} catch (Throwable e){
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(),e);
			}


				//return new EvnRespCalificacion(devuelve, listaATemp);
		}

		/**
		 * @param evento
		 * @return
		 */
		private EventoRespuesta eliminarAnotacionTemporal(EvnCalificacion evento) throws EventoException {

			Folio devuelve=null;
			List listaATemp=null;

			Usuario usuarioNeg=evento.getUsuarioNeg();
			Folio nuevo=new Folio();
			Turno turno = evento.getTurno();
			List lstAnotaciones = evento.getAnotaciones();
			boolean eliminarAnotaciones = false;
			boolean eliminarAnotacionesDir = false;
			Iterator itAnotaciones = lstAnotaciones.iterator();
			int countAnotaciones = 0;
			int countAnotacionesDir = 0;
			while(itAnotaciones.hasNext()){
				Anotacion anotTemp = (Anotacion)itAnotaciones.next();
				if(anotTemp != null && anotTemp.getNaturalezaJuridica() != null && anotTemp.getNaturalezaJuridica().getIdNaturalezaJuridica() != null 
					&&	(anotTemp.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA) || 
					anotTemp.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACLARACION) ||
					anotTemp.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_CON_SUBSIDIO_FAMILIAR_DE_VIVIENDA)||
					anotTemp.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_EN_SUELO_PROPIO) ||
					anotTemp.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.DECLARACION_PARTE_RESTANTE)
					)){
					countAnotaciones = countAnotaciones+1;
				}
				if(anotTemp != null && anotTemp.getNaturalezaJuridica() != null && anotTemp.getNaturalezaJuridica().getIdNaturalezaJuridica() != null 
					&&	(anotTemp.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA) || 
					anotTemp.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACLARACION)		
					)){
					countAnotacionesDir = countAnotacionesDir+1;
				}
			}
			if(countAnotaciones < 2){
				eliminarAnotaciones = true;
			}
			if(countAnotacionesDir < 2){
				eliminarAnotacionesDir = true;
			}
			
			TurnoPk idTTurno = new TurnoPk();
			idTTurno.anio = turno.getAnio();
			idTTurno.idCirculo = turno.getIdCirculo();
			idTTurno.idProceso = turno.getIdProceso();
			idTTurno.idTurno = turno.getIdTurno();
			
			FolioPk folioPk = new FolioPk();
			folioPk.idMatricula = evento.getFolio().getIdMatricula();

			nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
			//nuevo.setZonaRegistral(evento.getFolio().getZonaRegistral());
			Anotacion anotacion= evento.getAnotacion();
			nuevo.addAnotacione( anotacion );
			boolean eliminadasDireccionesTMP = false;
			long numeroNatLinderos = 0;
			long numeroNatLinderos2 = 0;
			long numeroNatLinderos3 = 0;
			long numeroNatLinderos4 = 0;
			long numeroNatDireccion = 0;
			long numeroArea = 0;
			long numeroAclaracion = 0;
			long numeroAnotacionesTemporal = 0;
			boolean procesoRegistro = false;
			
			try {
				boolean resultOk = forseti.updateFolio( nuevo, usuarioNeg, idTTurno, false );
                               /**
                                * @author: Julio Alcazar
                                * @change: 5306:  Requerimiento No 203 - Desfase de Id anotacion temporales,
                                * Llamado al metodo OrderIdAnotacion para realizar el ordenamiento.
                                */
                                CalificacionSIR calificacionSIR = new CalificacionSIR();
                                calificacionSIR.OrderIdAnotacion(anotacion.getIdMatricula(), anotacion.getIdAnotacion());
			if(evento.getProceso().getIdProceso() == new Long(CProceso.PROCESO_REGISTRO).longValue()){
				procesoRegistro = true;
				
				/**SE VALIDA EL TIPO DE ANOTACION PARA SABER SI SE ELIMINAN LOS DATOS TEMPORALES DEL FOLIO
				 * (Cod catastral, linderos y direccion) Bug 2773*/
				numeroNatLinderos = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_DE_LINDEROS, usuarioNeg);
				numeroNatLinderos2 = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_CON_SUBSIDIO_FAMILIAR_DE_VIVIENDA, usuarioNeg);
				numeroNatLinderos3 = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_EN_SUELO_PROPIO, usuarioNeg);
				numeroNatLinderos4 = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_PARTE_RESTANTE, usuarioNeg);
				
				numeroNatDireccion = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA, usuarioNeg);
				numeroArea = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_AREA, usuarioNeg);
				numeroAclaracion = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACLARACION, usuarioNeg);
				numeroAnotacionesTemporal = forseti.getCountAnotacionesTMPFolio(folioPk,usuarioNeg);
				
				FolioDatosTMPPk folioDatosTMPPk = new FolioDatosTMPPk();
				 folioDatosTMPPk.idMatricula = folioPk.idMatricula;
				 FolioDatosTMP folioDatosTMP= forseti.getFolioDatosTMP(folioDatosTMPPk);
				if(anotacion.getNaturalezaJuridica()!=null && folioDatosTMP != null){
					 
					 FolioDatosTMP folioDatosAux = new FolioDatosTMP();
					 folioDatosAux.setIdMatricula(folioPk.idMatricula);
					 folioDatosAux.setCodCatastral(folioDatosTMP.getCodCatastral());
					 folioDatosAux.setCodCatastralAnterior(folioDatosTMP.getCodCatastralAnterior());
					 folioDatosAux.setLindero(folioDatosTMP.getLindero());

                                        /*
                                        * @author      :   Henry Gómez Rocha
                                        * @change      :   Comentareando en los siguientes metodos  el seteo a null de los datos.
                                        * Caso Mantis  :   0004503
                                        */

					if(anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACTUALIZACION_DE_LINDEROS)||
							anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACTUALIZACION_AREA)||
							anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_CON_SUBSIDIO_FAMILIAR_DE_VIVIENDA)||
							anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_EN_SUELO_PROPIO) ||
							anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.DECLARACION_PARTE_RESTANTE)){
						/**LINDERO*/
						 if(numeroNatLinderos==0 && numeroNatLinderos2==0 && numeroNatLinderos3==0 && numeroNatLinderos4==0){
							 if(numeroNatDireccion == 0){
								 if(numeroArea == 0){
									 if(numeroAclaracion == 0 && eliminarAnotaciones){
										 //folioDatosAux.setLindero(null);
										 forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,false);
									 }
								 }
							 }
							
						 }
					}
					if(anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA)){
						/**LINDEROS*/
						if(numeroNatLinderos==0 && numeroNatLinderos2==0 && numeroNatLinderos3==0 && numeroNatLinderos4==0){
							 if(numeroNatDireccion == 0){
								 if(numeroArea == 0){
									 if(numeroAclaracion == 0 && eliminarAnotaciones){
										 //folioDatosAux.setLindero(null);
										 forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,false);
									 }
								 }
							 }
							
						 }
						/**DIRECCION*/
						if(numeroAclaracion==0){
							 if(numeroNatDireccion == 0){
								 if(eliminarAnotacionesDir){
									 eliminadasDireccionesTMP = true;
								 	forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,true);
								 }
							  }
						}
						
					}
					
					if(anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACLARACION)){
						 /**LINDEROS*/
						if(numeroNatLinderos==0 && numeroNatLinderos2==0 && numeroNatLinderos3==0 && numeroNatLinderos4==0){
							if(numeroNatDireccion == 0){
								if(numeroArea == 0 && eliminarAnotaciones){
									//folioDatosAux.setLindero(null);
									forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,false);
								}
							}
						}
						/**COD CATASTRAL*/
						if(numeroAnotacionesTemporal == 0){
							//folioDatosAux.setCodCatastral(null);
							//folioDatosAux.setCodCatastralAnterior(null);
							forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,false);
						}
						/**DIRECCION*/
						if(numeroNatDireccion == 0){
							if(eliminarAnotacionesDir){
								 eliminadasDireccionesTMP = true;
							 	forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,true);
							 }
//							eliminadasDireccionesTMP = true;
//							forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,true);
						}
					}if(numeroAnotacionesTemporal == 0){
						/**COD CATASTRAL*/
						//folioDatosAux.setCodCatastral(null);
						//folioDatosAux.setCodCatastralAnterior(null);
						forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,false);
					}
				}
			}
			    if( resultOk ){
				FolioPk id=new FolioPk();
				id.idMatricula=evento.getFolio().getIdMatricula();
				devuelve = forseti.getFolioByIDSinAnotaciones(id);
				listaATemp= forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg);

				if(turno!=null){
					TurnoPk idTurno = new TurnoPk();
					idTurno.anio = turno.getAnio();
					idTurno.idCirculo = turno.getIdCirculo();
					idTurno.idProceso = turno.getIdProceso();
					idTurno.idTurno = turno.getIdTurno();

					turno = hermod.getTurno(idTurno);
				}

			     }
			}
                        catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANCalificacion.class,"No se pudo eliminar la informacion de la anotacion:"+ep.toString());
				throw new CancelarAnotacionNoEfectuadaException("No se pudo eliminar la informacion de la anotacion" + ":"+e1.getMessage() ,e1);
			}


			//SE CONSULTA EL FOLIO PARA MOSTRAR LA INFORMACIÓN DEL MISMO
			try {
				if(nuevo!=null && nuevo.getIdMatricula()!=null){

					FolioPk id=new FolioPk();
					id.idMatricula=nuevo.getIdMatricula();

					nuevo = forseti.getFolioByIDSinAnotaciones(id,usuarioNeg);
					Folio folioDef = forseti.getFolioByIDSinAnotaciones(id);
					//forseti.getDatosDefinitivosDeDatosTemporales(id,usuarioNeg);

					boolean esMayorExtension = forseti.mayorExtensionFolio(nuevo.getIdMatricula());

					long numeroAnotaciones = forseti.getCountAnotacionesFolio(nuevo.getIdMatricula());

					List foliosHijos = forseti.getFoliosHijos(id,usuarioNeg);
					List foliosPadre = forseti.getFoliosPadre(id,usuarioNeg);
                                        List historialAreas = forseti.getHistorialArea(nuevo.getIdMatricula());

					long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.GRAVAMEN);
					List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN,0, (int)numeroGravamenes,usuarioNeg,true);

					long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
					List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR,0, (int)numeroMedidasCautelares,usuarioNeg,true);

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

					//		CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
					numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
					anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
					
					//	CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
					munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
					anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
					
					// CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
					munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
					anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
					
					anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);
					
					List salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, usuarioNeg);
					List cancelaciones = forseti.getAnotacionesConCancelaciones(id, usuarioNeg);
					
					//sir-169
					numeroSegregaciones = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.LOTEO, null);
					anotacionesSegregadorasVacias = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.LOTEO,0, (int)numeroSegregaciones,null,true);
					numeroSegregaciones += forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.RELOTEO, null);
					anotacionesSegregadorasVacias.addAll(forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.RELOTEO,0, (int)numeroSegregaciones,null,true));
					numeroSegregacionesVacias = (long)anotacionesSegregadorasVacias.size();

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

					EvnRespCalificacion evnResp = new EvnRespCalificacion (historialAreas, nuevo, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion , ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, listaATemp, linderoTemporal);

					/**En caso de que sea del proceso de registro se envia las cantidades de los diferentes 
					 * tipos de anotaciones para evitar una nueva consulta del numero de anotaciones*/
					
					if(procesoRegistro){
						this.habilitarEdicionCampos(evnResp, turno, forseti, folioPk, foliosHijos, foliosPadre, usuarioNeg,
								numeroNatLinderos,numeroNatLinderos2,numeroNatLinderos3,numeroNatLinderos4, numeroNatDireccion, numeroArea,numeroAclaracion, numeroAnotacionesTemporal);
					}else{
						this.habilitarEdicionCampos(evnResp, turno, forseti, folioPk, foliosHijos, foliosPadre, usuarioNeg);
					}

					evnResp.setEliminarDireccionesTMP(eliminadasDireccionesTMP);
					evnResp.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
					evnResp.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
					evnResp.setTurno(turno);
					//sir-169
					evnResp.setNumeroSegregacionesVacias(numeroSegregacionesVacias);
					return evnResp;
				}
				throw new EventoException();
			} catch (Throwable e){
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(),e);
			}


			//return new EvnRespCalificacion(devuelve, listaATemp);
		}

            public void VisualizarpdfCalificacion1(EvnCalificacion evento,HttpServletRequest request, HttpServletResponse response){
                    Turno turno = evento.getTurno();
                    Fase fase = evento.getFase();
                    TurnoPk oid=new TurnoPk();
                    oid.anio=turno.getAnio();
                    oid.idCirculo=turno.getIdCirculo();
                    oid.idProceso=turno.getIdProceso();
                    oid.idTurno=turno.getIdTurno();
                    Usuario u=evento.getUsuarioNeg();
                    boolean Anotmp;

                    try{
                            turno = hermod.getTurno(oid);
                    }catch(Throwable t)
                    {
                 
                    }
                   String impresion = "";
                    List historia = turno.getHistorials();
                    int i = 0;
                    Usuario usuario = null;
                    while (i < historia.size()) {
                        TurnoHistoria historial = (TurnoHistoria) historia.get(i);
                        if (historial.getFase().equals(CFase.CAL_CALIFICACION)) {
                            if (i != historia.size() - 1) {
                                usuario = historial.getUsuarioAtiende();
                                impresion = historial.getNcopias();
                            }

                        }
                        i++;
                    }
                    try {
                        evento.setUID(turno.getIdCirculo());
                         Pageable peg = null;
                        String UID=evento.getUID();
                        Hashtable parametros = new Hashtable();
                        SolicitudRegistro solicitud = (SolicitudRegistro)turno.getSolicitud();
                        List folios= solicitud.getSolicitudFolios();    
                        try {
                                //actualizar los folios segun los modificados en la solicitud
                                try{
                                    List solicitudesFoliosCalificados = null;
                                    solicitudesFoliosCalificados =this.getSolicitudesFolioCalificadas(forseti, oid, usuario);
                                    Collections.sort(solicitudesFoliosCalificados, new IDMatriculaFolioComparator());
                                    turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
                                }catch (Throwable e2){
                                         //actualizar los folios segun los modificados en la solicitud
                                        try{
                                           
                                            turno = hermod.getTurno(oid);
                                            //FolioPk Llave = new FolioPk();
                                            //List ListaAnotacionDefinitovs = null;
                                            ArrayList<Anotacion> Anotacionescalificadas = new ArrayList<Anotacion>();
                                            List FoliosSolc = new ArrayList();
                                            List anotaciones = null;              
                                            if(!folios.isEmpty()){
                                                Iterator itSolFolios = folios.iterator();
                                                while(itSolFolios.hasNext()){         
                                                      SolicitudFolio solFolio = (SolicitudFolio)itSolFolios.next();
                                                      Folio f = solFolio.getFolio();
                                                      boolean ok = false;
                                                      FolioPk Pkfolio = new FolioPk();
                                                      Pkfolio.idMatricula = f.getIdMatricula();
                                                      f = forseti.getFolioByID(Pkfolio);
                                                      if(f == null){
                                                          f = forseti.getDeltaFolio(Pkfolio, usuario);
                                                           int count = 0;
                                                            anotaciones = f.getAnotaciones();
                                                            f.getAnotaciones().clear();
                                                            for(i = 0 ; i < anotaciones.size(); i++){
                                                                Anotacion anotmp = (Anotacion) (Anotacion) anotaciones.get(i);
                                                                try{
                                                                    if(anotmp.getNumRadicacion().equals(oid.anio + "-" + oid.idCirculo + "-" + oid.idProceso + "-" + oid.idTurno)){
                                                                    f.addAnotacion(count, anotmp);
                                                                    count = count +1;
                                                                    }
                                                                }catch(Throwable ex){

                                                                }
                                                            }
                                                            solFolio.setFolio(f);
                                                            FoliosSolc.add(solFolio);
                                                            anotaciones.clear();
                                                      }else{
                                                          f.getAnotaciones().clear();
                                                            int count = 0;
                                                            anotaciones = forseti.getAnotacionesFolioTMP(Pkfolio);
                                                            for(i = 0 ; i < anotaciones.size(); i++){
                                                                Anotacion anotmp = (Anotacion) (Anotacion) anotaciones.get(i);
                                                                try{
                                                                    if(anotmp.getNumRadicacion().equals(oid.anio + "-" + oid.idCirculo + "-" + oid.idProceso + "-" + oid.idTurno)){
                                                                    f.addAnotacion(count, anotmp);
                                                                    count = count +1;
                                                                    }
                                                                }catch(Throwable ex){

                                                                }
                                                            }
                                                             if(f.getAnotaciones().isEmpty()){

                                                              }else{
                                                              solFolio.setFolio(f);
                                                              FoliosSolc.add(solFolio);
                                                              anotaciones.clear();
                                                              }
                                                      }
                                                      
                                            }
                                            turno.getSolicitud().setSolicitudFolios(FoliosSolc);        
                                          }
                                    }catch (Throwable e12){
                                    
                                    }
                                }
        //			obtener textos base de los separadores
                                String tbase1 ="";
                                String tbase2 = "";
                                List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
                                for(Iterator j= variablesImprimibles.iterator(); j.hasNext();){
                                        OPLookupCodes op = (OPLookupCodes) j.next();
                                        if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
                                                tbase1= op.getValor();
                                        }
                                        if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
                                                tbase2 = op.getValor();
                                        }
                                }
                                
                                //crear el imprimible de formulario
                                String fechaImpresion = this.getFechaImpresion();
                                ImprimibleFormulario impFormulario = new ImprimibleFormulario(turno,u.getUsername(),CTipoFormulario.TIPO_CALIFICACION, fechaImpresion, CTipoImprimible.FORMULARIO_CALIFICACION);
                                if (evento.getFase().getID().equals(CFase.CAL_CALIFICACION)) {
                                    impFormulario.setIdUsuario(Long.toString(u.getIdUsuario()));
                                }else{
                                    impFormulario.setIdUsuario(Long.toString(usuario.getIdUsuario()));
                                }
                                impFormulario.setPrintWatermarkEnabled(true);
                                impFormulario.setTurno(turno);
                                impFormulario.setTEXTO_BASE1(tbase1);
                                impFormulario.setTEXTO_BASE2(tbase2);
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
                                    throw e;
                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }

                                if (sNombre == null) {
                                    sNombre = "";
                                }

                                //+++
                                impFormulario.setCargoRegistrador(cargoToPrint);
                                impFormulario.setNombreRegistrador(sNombre);

                                if (rutaFisica != null) {
                                    BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                                     byte pixeles[] = null;
                                    try {
                                        pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                                    } catch (Throwable e1) {

                                        e1.printStackTrace();
                                    }
                                    
                                }

                                            PrinterJob printJob = PrinterJob.getPrinterJob();
                                            PageFormat pf = printJob.defaultPage();
                                            Paper papel = new Paper();
                                            double mWidth = LETTER_WIDTH;
                                            double mHeight = LETTER_HEIGHT;

                                            papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                                                mHeight - (0.5 * INCH));
                                            pf.setPaper(papel);
                                            impFormulario.generate(pf);
                                            this.generarVisualizacion(impFormulario, request, response);
                                        
                               
                        } catch (Throwable e1) {
                                ExceptionPrinter ep=new ExceptionPrinter(e1);
                                Log.getInstance().error(ANCalificacion.class,"No se pudo imprimir el formulario:"+ep.toString());
                                throw new AvanzarCalificacionException("No se pudo imprimir el formulario",e1);
                        }
                        //this.imprimirCertificado(turno, u, turno.getIdCirculo(), evento.getImpresoraSeleccionada());
                    } catch (Throwable ex) {
                  
                    }  
                
                
               }
                public Turno GetTurnoWK(String idWorkflow){
                    Turno turno= null;
                     try{
                            turno = hermod.getTurnobyWF(idWorkflow);
                    }catch(Throwable t)
                    {
                 
                    }
                    return turno;
                }
                public void VisualizarpdfRSM(EvnCalificacion evento,HttpServletRequest request, HttpServletResponse response,String Matricula,String iduser,String desde,String hasta,int isvisua){
                
		String matricula =  Matricula;
		String UID = evento.getTurno().getIdCirculo();
		Turno turnoReproduccion = evento.getTurno();

		int inicio =  Integer.parseInt(desde.trim());
		int fin  =  Integer.parseInt(hasta.trim());

		if(inicio >0){
			inicio = inicio - 1;
		}
		if(fin >0){
			fin = fin - 1;
		}

		//OBTENER EL FOLIO A IMPRIMIR
		gov.sir.core.negocio.modelo.Turno turno = new Turno();
		turno.setIdWorkflow(turnoReproduccion.getIdWorkflow());
		Folio folio = new Folio();

		try {
			FolioPk id = new FolioPk();
			id.idMatricula = matricula;

			folio = forseti.getFolioByIDSinAnotaciones(id);
			List anotaciones =  forseti.getAnotacionesFolio(id, CCriterio.TODAS_LAS_ANOTACIONES,null,inicio,((fin-inicio)+1),false);
			folio.setAnotaciones(anotaciones);
		}
		catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
			
		}

		try {
			 SolicitudFolio solFolio = new SolicitudFolio();
			 solFolio.setFolio(folio);

			 List solicitudesFoliosCalificados = new ArrayList();
			 solicitudesFoliosCalificados.add(solFolio);

			 turno.setSolicitud(new Solicitud());
			 turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);

			 String localUserName = String.valueOf(evento.getUsuarioNeg().getIdUsuario());
			 localUserName = ( null != localUserName )?( localUserName ):( "" );

			 //crear el imprimible de formulario
			 String fechaImpresion= this.getFechaImpresion();
			 ImprimibleFormulario impFormulario = new ImprimibleFormulario(turno , iduser, CTipoFormulario.TIPO_REPRODUCCION_SELLOS, fechaImpresion, CTipoImprimible.REPRODUCCION_SELLOS);
			 impFormulario.setPrintWatermarkEnabled(true);
			 
			 // Obtener textos base de los separadores
				String tbase1 ="";
				String tbase2 = "";
				List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
				for(Iterator j= variablesImprimibles.iterator(); j.hasNext();){
					OPLookupCodes op = (OPLookupCodes) j.next();
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
						tbase1= op.getValor();
					}
					if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
						tbase2 = op.getValor();
					}
				}		
				
				impFormulario.setTEXTO_BASE1(tbase1);
				impFormulario.setTEXTO_BASE2(tbase2);
				
			 //mandar a imprimir documento
                         if(isvisua == 1){
                             PrinterJob printJob = PrinterJob.getPrinterJob();
                                   PageFormat pf = printJob.defaultPage();
                                   Paper papel = new Paper();
                                   double mWidth = LETTER_WIDTH;
                                   double mHeight = LETTER_HEIGHT;

                                   papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                                       mHeight - (0.5 * INCH));
                                   pf.setPaper(papel);
                                   impFormulario.generate(pf);
                                   this.generarVisualizacion(impFormulario, request, response);
                         }else{
                                impFormulario.setChangetextforregistrador(true);
                                String nombre = "";
                                String archivo = "";
                                String cargoToPrint = "";
                                String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
                                FirmaRegistrador firmaRegistrador = null;
                                CirculoPk cid = new CirculoPk();
                                cid.idCirculo = UID;
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
                                    throw e;
                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }

                                if (sNombre == null) {
                                    sNombre = "";
                                }

                                //+++
                                impFormulario.setCargoRegistrador(cargoToPrint);
                                impFormulario.setNombreRegistrador(sNombre);

                                if (rutaFisica != null) {
                                    BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                                     byte pixeles[] = null;
                                    try {
                                        pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                                    } catch (Throwable e1) {

                                        e1.printStackTrace();
                                    }
                                    impFormulario.setPixelesImagenFirmaRegistrador(pixeles);
                                    
                                }
                                this.imprimir(impFormulario, UID, evento.getNumeroImpresiones());
                             
                         }
                                
		}catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudo imprimir el formulario:"+ep.toString());
		}

                
                }
                public void VisualizarpdfRST(EvnCalificacion evento,HttpServletRequest request, HttpServletResponse response,Turno turno1,String iduser,int Isvisua){
                    Turno turno = evento.getTurno();
                    TurnoPk oid=new TurnoPk();
                    oid.anio=turno.getAnio();
                    oid.idCirculo=turno.getIdCirculo();
                    oid.idProceso=turno.getIdProceso();
                    oid.idTurno=turno.getIdTurno();
                    Usuario u=evento.getUsuarioNeg();
                      try {

                            //actualizar los folios segun los modificados en la solicitud
                            try {
                                 List solicitudesFoliosCalificados = null;
                                 solicitudesFoliosCalificados =this.calificacion_ReimpresionSellos_PrintSelected_GetSolicitudesFolioCalificadas( forseti, oid, u );

                                 turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
                                 turno.setIdWorkflow(turno1.getIdWorkflow());
                            }
                            catch (Throwable e2){
                                     ExceptionPrinter ep=new ExceptionPrinter(e2);
                                     Log.getInstance().error(ANCalificacion.class,"No se pudieron obtener los folios calificados:"+ep.toString());
                                     throw new ReproduccionSellosException("No se pudieron obtener los folios calificados:"+e2.getMessage(),e2);
                            }



                            String localUserName=evento.getUsuarioNeg().getUsername();
                            localUserName = ( null != localUserName )?( localUserName ):( "" );

                            //crear el imprimible de formulario
                            String fechaImpresion= this.getFechaImpresion();
                            ImprimibleFormulario impFormulario = new ImprimibleFormulario(turno , iduser, CTipoFormulario.TIPO_REPRODUCCION_SELLOS, fechaImpresion, CTipoImprimible.REPRODUCCION_SELLOS);
                            impFormulario.setPrintWatermarkEnabled(true);
                            impFormulario.setIdUsuario(iduser);
                            //mandar a imprimir documento
                            if(Isvisua == 1){
                                PrinterJob printJob = PrinterJob.getPrinterJob();
                                PageFormat pf = printJob.defaultPage();
                                Paper papel = new Paper();
                                double mWidth = LETTER_WIDTH;
                                double mHeight = LETTER_HEIGHT;

                                papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                                    mHeight - (0.5 * INCH));
                                pf.setPaper(papel);
                                impFormulario.generate(pf);
                                this.generarVisualizacion(impFormulario, request, response);
                            }else{
                                impFormulario.setChangetextforregistrador(true);
                                String nombre = "";
                                String archivo = "";
                                String cargoToPrint = "";
                                String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
                                FirmaRegistrador firmaRegistrador = null;
                                CirculoPk cid = new CirculoPk();
                                cid.idCirculo = turno1.getIdCirculo();
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
                                    throw e;
                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }

                                if (sNombre == null) {
                                    sNombre = "";
                                }

                                //+++
                                impFormulario.setCargoRegistrador(cargoToPrint);
                                impFormulario.setNombreRegistrador(sNombre);

                                if (rutaFisica != null) {
                                    BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                                     byte pixeles[] = null;
                                    try {
                                        pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                                    } catch (Throwable e1) {

                                        e1.printStackTrace();
                                    }
                                    impFormulario.setPixelesImagenFirmaRegistrador(pixeles);
                                    
                                }
                                this.imprimir(impFormulario, turno1.getIdCirculo(), evento.getNumeroImpresiones());

                            }
                            


                    } catch (Throwable e1) {
                            ExceptionPrinter ep=new ExceptionPrinter(e1);
                            Log.getInstance().error(ANCalificacion.class,"No se pudo imprimir el formulario:"+ep.toString());
                    }
                }
                public void Visualizarpdf(EvnCalificacion evento,HttpServletRequest request, HttpServletResponse response){
                    Turno turno = evento.getTurno();
                    Fase fase = evento.getFase();
                    TurnoPk oid=new TurnoPk();
                    oid.anio=turno.getAnio();
                    oid.idCirculo=turno.getIdCirculo();
                    oid.idProceso=turno.getIdProceso();
                    oid.idTurno=turno.getIdTurno();
                    Usuario u=evento.getUsuarioNeg();
                    boolean Anotmp;

                    try{
                            turno = hermod.getTurno(oid);
                    }catch(Throwable t)
                    {
                 
                    }
                  
                    try {
                        evento.setUID(turno.getIdCirculo());
                        int resp = evento.getIsFirma();
                        int saber = 4;
                        try{
                        Testamento testamento = hermod.getTestamentoByID(evento.getTurno().getSolicitud().getIdSolicitud());
                        if(testamento != null){
                            this.imprimirTestamento(evento,testamento,1,response,request);
                            saber = 0;
                        }    
                        }catch (Throwable ex) {

                        }  
                        if(saber != 0){
                            if(evento.getTurno().getNotas() != null){
                            if(evento.getTurno().getNotas().size() > 0){
                                List notas = evento.getTurno().getNotas();
                                Nota captura = null;
                                for(int i = 0 ; i < notas.size() ; i++){
                                    Nota n = (Nota) notas.get(i);
                                    if(n.getTiponota().isDevolutiva()){
                                        captura = n;
                                        saber=1;
                                    }
                                }
                                 if(saber == 1){
                                          try{
                                           SolicitudRegistro solReg1 = (SolicitudRegistro) turno.getSolicitud();
                                           SolicitudRegistro finalS =(SolicitudRegistro) hermod.getSolicitudById(solReg1.getIdSolicitud());
                                                RelacionPk rel = new RelacionPk();
                                                rel.idFase = turno.getIdFaseRelacionActual();
                                                rel.idRelacion = turno.getIdRelacionActual();
                                                Relacion relacion = hermod.getRelacion(rel);
                                                TipoRelacion tipoRelacion = relacion.getTipoRelacion();
                                                if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_CON_INSCRIPCION_PARCIAL_PARA_FIRMA)){
                                                    this.imprimirFormularioCalificacionReg(evento,resp,1,response,request);
                                                    this.imprimirNotaDevolutiva(evento,captura,3,response,request,null);
                                                 }else{
                                                        this.imprimirNotaDevolutiva(evento,captura,1,response,request,null); 
                                                }
                                       }catch(Throwable ex){
                                           this.imprimirNotaDevolutiva(evento,captura,1,response,request,null);   
                                       }   
                                 }
                            }
                        }      
                            if(saber == 4 ){
                                if(evento.getFase().getID().equals(CFase.REG_FIRMAR)){
                                this.imprimirFormularioCalificacionReg(evento,resp,1,response,request);   
                                    
                                }else{
                                this.VisualizarpdfCalificacion1(evento, request, response);
                                }
                              
                            }
                        }

                        //this.imprimirCertificado(turno, u, turno.getIdCirculo(), evento.getImpresoraSeleccionada());
                    } catch (Throwable ex) {
                  
                    }   
                   
  
               }


                            /**
                * Generar un pdf
                * @param imprimible
                * @param request
                * @param response
                */
               private void generarVisualizacion(Pageable imprimible, HttpServletRequest request,
                   HttpServletResponse response) {
                   try {
                       
                       com.lowagie.text.Document pdfDocument = new com.lowagie.text.Document();
                       response.setContentType("application/pdf");
                       // Se agrega condicion para 
                       // Visualizar Formulario Imprimible en SIR
                       // add by DNilson Olaya Gómez --- desarrollo3@tsg.net.co
                       ByteArrayOutputStream baos = new ByteArrayOutputStream();
                       response.addHeader("Content-Disposition", "inline; filename=FormularioImprimible.pdf");
                       PdfWriter writer = PdfWriter.getInstance(pdfDocument,
                               response.getOutputStream());
                       writer.setViewerPreferences(PdfWriter.HideMenubar |
                           PdfWriter.HideToolbar);
                       writer.setEncryption(PdfWriter.STRENGTH128BITS, null, null, 0);

                       InputStream is =  PdfServlet.class.getResourceAsStream( "watermark.jpg" );
                       byte[] bytes =this.inputStreamToBytes(is) ;

                                   /**
                                   * @author: Fernando Padilla Velez, 22.06.2015
                                   * @change: Incidencia No 180870 Generación PDF- Resultado de la consulta
                                   * 		   No permitia visulizar el pdf, se intercambian las lineas de codigo
                                   *          para que primero se abra el documento y luego si se agrega la imagen.
                                   */
                       pdfDocument.open();

                                   Image img = Image.getInstance(  bytes );
                       pdfDocument.add(new Watermark(img, 50, 320));

                       PdfContentByte content = writer.getDirectContent();

                       int num_pages = imprimible.getNumberOfPages();

                       for (int i = 0; i < num_pages; i++) {
                           Printable page = imprimible.getPrintable(i);

                           // we create a template and a Graphics2D object that
                           // corresponds with it
                           PageFormat pageformat = imprimible.getPageFormat(i);
                           int pageWidth = (int) pageformat.getWidth();
                           int pageHeight = (int) pageformat.getHeight();
                           PdfTemplate template = content.createTemplate(pageWidth,
                                   pageHeight);
                           template.setWidth(pageWidth);
                           template.setHeight(pageHeight);

                           Graphics2D g2d = template.createGraphics(pageWidth, pageHeight);
                           page.print(g2d, pageformat, 0);

                           if (pageformat.getOrientation() == PageFormat.LANDSCAPE) {
                               content.addTemplate(template, 0, 1, -1, 0, pageHeight, 0);
                           } else {
                               content.addTemplate(template, 0, 0);
                           }

                           pdfDocument.newPage();
                       }

                       pdfDocument.close();
                   } catch (Exception e) {
                           Log.getInstance().error(PdfServlet.class,e);
                   }
               }
                /**
            * @param in
            * @return
            * @throws IOException
            */
           public byte[] inputStreamToBytes(InputStream in) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
		return out.toByteArray();
    	}
		/**
		 * @param evento
		 * @return
		 */
		private EventoRespuesta grabarAnotacionesTemporal(EvnCalificacion evento) throws EventoException {
			
			/*modificacion n1 */
			boolean eliminadasDireccionesTMP = false;
			long numeroNatDireccion = 0;
			long numeroNatLinderos = 0;
			long numeroNatLinderos2 = 0;
			long numeroNatLinderos3 = 0;
			long numeroNatLinderos4 = 0;

			long numeroArea = 0;
			long numeroAclaracion = 0;
			long numeroAnotacionesTemporal = 0;
			
			Folio devuelve=null;
			List listaATemp=null;

			Usuario usuarioNeg=evento.getUsuarioNeg();
			Folio nuevo=new Folio();

			nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
			//nuevo.setZonaRegistral(evento.getFolio().getZonaRegistral());
			List anotaciones = evento.getAnotaciones();
			Iterator it=anotaciones.iterator();
			Anotacion anota = null;
			while(it.hasNext()){
				anota=(Anotacion)it.next();
				nuevo.addAnotacione(anota);
			}
			
			//SE ACTUALIZA EL FOLIO
			try {
				if (forseti.updateFolio(nuevo,usuarioNeg, null, false)){
					FolioPk id=new FolioPk();
					id.idMatricula=evento.getFolio().getIdMatricula();
					devuelve = forseti.getFolioByIDSinAnotaciones(id);
				    listaATemp= forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg);
				    
				    FolioPk folioPk = id;
				    
				    
				    List lstAnotacionesTem = evento.getAnotacionesTem();
				    if(lstAnotacionesTem != null){
					    Iterator itAnota = lstAnotacionesTem.iterator(); 
					    boolean elimiAnota = true;
					    boolean elimiAnotaDir = true;
					    while(itAnota.hasNext() && (elimiAnota || elimiAnotaDir)){
					    	Anotacion anotacion = (Anotacion)itAnota.next();
					    	if(elimiAnota && anotacion != null && anotacion.getNaturalezaJuridica() != null && anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() != null &&
					    		(anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA) ||
					    			anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACLARACION) ||
					    			anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_CON_SUBSIDIO_FAMILIAR_DE_VIVIENDA)||
					    			anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_EN_SUELO_PROPIO) ||
					    			anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.DECLARACION_PARTE_RESTANTE)
					    			)){
					    		elimiAnota = false;
					    	}
					    	if(elimiAnotaDir && anotacion != null && anotacion.getNaturalezaJuridica() != null && anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica() != null &&
					    		(anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA) ||
					    			anotacion.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACLARACION) 
					    			)){
					    		elimiAnotaDir = false;
					    	}
					    }
					    	
					    numeroNatLinderos = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_DE_LINDEROS, usuarioNeg);
					    
					    numeroNatLinderos2 = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_CON_SUBSIDIO_FAMILIAR_DE_VIVIENDA, usuarioNeg);
						numeroNatLinderos3 = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_DE_CONSTRUCCION_EN_SUELO_PROPIO, usuarioNeg);
						numeroNatLinderos4 = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.DECLARACION_PARTE_RESTANTE, usuarioNeg);
						
						numeroNatDireccion = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_DE_NOMENCLATURA, usuarioNeg);
						numeroArea = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACTUALIZACION_AREA, usuarioNeg);
						numeroAclaracion = forseti.getCountAnotacionesFolio(folioPk, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.ACLARACION, usuarioNeg);
						numeroAnotacionesTemporal = forseti.getCountAnotacionesTMPFolio(folioPk,usuarioNeg);
						
						FolioDatosTMPPk folioDatosTMPPk = new FolioDatosTMPPk();
						 folioDatosTMPPk.idMatricula = folioPk.idMatricula;
						 FolioDatosTMP folioDatosTMP= forseti.getFolioDatosTMP(folioDatosTMPPk);
						
						  
						 
						if(anota.getNaturalezaJuridica()!=null && folioDatosTMP != null){
							 
							 FolioDatosTMP folioDatosAux = new FolioDatosTMP();
							 folioDatosAux.setIdMatricula(folioPk.idMatricula);
							 folioDatosAux.setCodCatastral(folioDatosTMP.getCodCatastral());
							 folioDatosAux.setCodCatastralAnterior(folioDatosTMP.getCodCatastralAnterior());
							 folioDatosAux.setLindero(folioDatosTMP.getLindero());
							
							if(elimiAnota){					
								/**LINDEROS*/
								if(numeroNatLinderos==0 && numeroNatLinderos2==0 && numeroNatLinderos3==0 && numeroNatLinderos4==0){
									 if(numeroNatDireccion == 0){
										 if(numeroArea == 0){
											 if(numeroAclaracion == 0){
												 folioDatosAux.setLindero(null);
												 forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,false);
											 }
										 }
									 }
								 }
							}
							if(elimiAnotaDir){
								/**DIRECCION*/
								if(numeroAclaracion==0){
									 if(numeroNatDireccion == 0){
										eliminadasDireccionesTMP = true;
										forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,true);
									  }
								}else{
									if(numeroNatDireccion == 0){
										eliminadasDireccionesTMP = true;
										forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,true);
									}	
								}
							}
							
	//						if(anota.getNaturalezaJuridica().getIdNaturalezaJuridica().equals(CNaturalezaJuridica.ACLARACION)){					
	//							
	//							/**DIRECCION*/
	//							if(numeroNatDireccion == 0){
	//								eliminadasDireccionesTMP = true;
	//								forseti.actualizarFolioDatosTMP(folioPk.idMatricula,folioDatosAux,true);
	//							}
	//						}
						}
					}
				    
				    
				}
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANCalificacion.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
				throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);

			}

			//SE CONSULTA EL FOLIO PARA MOSTRAR LA INFORMACIÓN DEL MISMO
			try {
				if(nuevo!=null && nuevo.getIdMatricula()!=null){

					FolioPk id=new FolioPk();
					id.idMatricula=nuevo.getIdMatricula();

					nuevo = forseti.getFolioByIDSinAnotaciones(id,usuarioNeg);
					Folio folioDef = forseti.getFolioByIDSinAnotaciones(id);
					//forseti.getDatosDefinitivosDeDatosTemporales(id,usuarioNeg);

					boolean esMayorExtension = forseti.mayorExtensionFolio(nuevo.getIdMatricula());

					long numeroAnotaciones = forseti.getCountAnotacionesFolio(nuevo.getIdMatricula());

					List foliosHijos = forseti.getFoliosHijos(id,usuarioNeg);
					List foliosPadre = forseti.getFoliosPadre(id,usuarioNeg);
                                        List historialAreas = forseti.getHistorialArea(nuevo.getIdMatricula());

					long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.GRAVAMEN);
					List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN,0, (int)numeroGravamenes,usuarioNeg,true);

					long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
					List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR,0, (int)numeroMedidasCautelares,usuarioNeg,true);

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

					//CONSULTA ANOTACIONES DE FALSA TRADICION
					numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
					falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, usuarioNeg, true);
					if(falsaTradicion != null){
						Iterator itAnota = falsaTradicion.iterator();
						while(itAnota.hasNext()){
							Anotacion anotTemp = (Anotacion)itAnota.next();
							ordenFalsaTradicion.add(anotTemp.getOrden());
						}
					}

					//CONSULTA ANOTACIONES INVALIDAS
					anotacionesInvalidas = forseti.getAnotacionesInvalidas(id, usuarioNeg);
					if(anotacionesInvalidas != null){
						Iterator itAnota = anotacionesInvalidas.iterator();
						while(itAnota.hasNext()){
							Anotacion anotTemp = (Anotacion)itAnota.next();
							ordenAnotacionesInvalidas.add(anotTemp.getOrden());
						}
					}
					
					// CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
					numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
					anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
					
					//	CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
					munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
					anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
					
					// CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
					munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
					anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
					
					anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);

					List salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, usuarioNeg);
					List cancelaciones = forseti.getAnotacionesConCancelaciones(id, usuarioNeg);

					String linderoTemporal = "";
					boolean linderoDigitacion=false;
					Turno turno=evento.getTurno();
					if(turno!=null && turno.getHistorials()!=null){
						List historia=turno.getHistorials();
						TurnoHistoria historial;
						for(int i=0;i<historia.size();i++){
							historial = (TurnoHistoria) historia.get(i);
							if(historial.getFase().equals(CFase.CAL_DIGITACION)){
								linderoDigitacion=true;
								break;
							}
						}
					}
					if(nuevo!=null && nuevo.getLindero()!=null && !nuevo.getLindero().equals("")){
						if(folioDef!=null){
							if(folioDef.getLindero()!=null){
								if(linderoDigitacion){
									linderoTemporal = nuevo.getLindero();
								}else{
									try{
										linderoTemporal = nuevo.getLindero().substring(folioDef.getLindero().length(),nuevo.getLindero().length());
									}catch(IndexOutOfBoundsException idx){
										linderoTemporal = nuevo.getLindero();	
									}
								}
							}else{
								linderoTemporal = nuevo.getLindero();
							}
						}else{
							linderoTemporal = nuevo.getLindero();
						}
					}

					EvnRespCalificacion evn = new EvnRespCalificacion(historialAreas, nuevo, foliosPadre, foliosHijos, gravamenes, medidasCautelares, salvedadesAnotaciones, ordenFalsaTradicion , ordenAnotacionesInvalidas, cancelaciones, numeroAnotaciones, esMayorExtension, listaATemp, linderoTemporal);
					if((evento.isGenerarNextOrden())&&(anotaciones.size()==1)){
						Anotacion anotacion = (Anotacion)anotaciones.iterator().next();
						String norder = anotacion.getOrden();
						int torder=Integer.parseInt(anotacion.getOrden());
						torder++;
						norder=Integer.toString(torder);
						evn.setNextOrden(norder);
					}
					
					evn.setEliminarDireccionesTMP(eliminadasDireccionesTMP);
					evn.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
					evn.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
					evn.setAnotaciones(anotaciones);
					evn.setAnotacionesAgregadas(evento.getAnotacionesAgregadas());
					habilitarEdicionCampos(evn, evento.getTurno(), forseti, id, foliosHijos, foliosPadre, usuarioNeg);
					return evn;
				}
				throw new EventoException();
			} catch (Throwable e){
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCalificacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(),e);
			}


		}


		
		/** Graba la direccion al temporal de un folio 
		 * @param evento
		 * @return EvnRespuesta
		 */
		private EventoRespuesta grabarDireccionTemporal(EvnCalificacion evento) throws EventoException {
			Usuario usuarioNeg=evento.getUsuarioNeg();
			EvnRespFolio evnResp= null;
			Folio nuevo=new Folio();
			
			List dirTemporales = evento.getDirTemporales();
			
			nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
			List direcciones = evento.getFolio().getDirecciones();
			Iterator it=direcciones.iterator();
			while(it.hasNext()){
				Direccion direccion=(Direccion)it.next();
				nuevo.addDireccione(direccion);
			}
			//SE ACTUALIZA EL FOLIO
			try {
				forseti.updateFolio(nuevo,usuarioNeg, null, false);
				FolioPk fid = new FolioPk();
				fid.idMatricula = nuevo.getIdMatricula();
				Folio folio = forseti.getFolioByID(fid, usuarioNeg);
				Folio folioDef = forseti.getFolioByIDSinAnotaciones(fid);
				evnResp = new EvnRespFolio(folio,EvnRespFolio.AGREGAR_DIRECCION);
					
				/*se corrigio el error cuando se grababan las direcciones
				temporales y no habia datos llegaba la lista nula lo que generaba 
				un error en su adicion linea 3744 ANCalificacion*/
				if(dirTemporales == null)
					dirTemporales = new ArrayList();

				dirTemporales.add(new Boolean(true));
				evnResp.setDirTemporales(dirTemporales);
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANCalificacion.class,"No se pudieron grabar las direcciones temporales:"+ep.toString());
				throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);
			}
			return evnResp;
		}

		/** Graba la direccion al temporal de un folio 
		 * @param evento
		 * @return EvnRespuesta
		 */
		private EventoRespuesta eliminarDireccionTemporal(EvnCalificacion evento) throws EventoException {
			Usuario usuarioNeg=evento.getUsuarioNeg();
			Folio nuevo=new Folio();
			
			nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
			List direcciones = evento.getFolio().getDirecciones();
			List dirTemporales = evento.getDirTemporales();
			Iterator it=direcciones.iterator();
			int i=0;
			int borrar = 0;
			while(it.hasNext()){
				Direccion direccion=(Direccion)it.next();
				nuevo.addDireccione(direccion);
				if(direccion.isToDelete())
					borrar = i;
				i++;
			}
			//SE ACTUALIZA EL FOLIO
			try {
				forseti.updateFolio(nuevo,usuarioNeg, null, false);
				dirTemporales.remove(borrar);
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANCalificacion.class,"No se pudieron grabar las direcciones temporales:"+ep.toString());
				throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);
			}
			EvnRespFolio evnResp = new EvnRespFolio(nuevo,EvnRespFolio.ELIMINAR_DIRECCION);
			evnResp.setDirTemporales(dirTemporales);
			
			return evnResp;
		}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta grabarEdicionAnotacionTemporal(EvnCalificacion evento) throws EventoException {

		Folio devuelve=null;
		List listaATemp=null;
		Usuario usuarioNeg=evento.getUsuarioNeg();
		Folio nuevo=new Folio();

		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		//nuevo.setZonaRegistral(evento.getFolio().getZonaRegistral());
		List anotaciones = evento.getAnotaciones();
		Iterator it=anotaciones.iterator();
		while(it.hasNext()){
			Anotacion anota=(Anotacion)it.next();
			nuevo.addAnotacione(anota);
		}

		//la parte de grabar las anotaciones en temporal
		try {
			if (forseti.updateFolio(nuevo,usuarioNeg, null, false)){
				FolioPk id=new FolioPk();
				id.idMatricula=evento.getFolio().getIdMatricula();
				devuelve = forseti.getFolioByIDSinAnotaciones(id);
				listaATemp= forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg);
			}
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales",e1);

		}
		return new EvnRespCalificacion(devuelve, listaATemp);
	}


	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta avanzar(EvnCalificacion evento, boolean generarImprimible) throws EventoException {

		Turno turno = evento.getTurno();
		Solicitud solicitud = turno.getSolicitud();
		Fase fase = evento.getFase();
		Hashtable tabla = new Hashtable();
		Usuario u = evento.getUsuarioNeg();
		tabla.put(Processor.RESULT, evento.getRespuestaWf());
		tabla.put(CInfoUsuario.USUARIO_SIR, u.getUsername());

		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance.
		try {
			Hashtable tablaAvance = new Hashtable(2);
			tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
			tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
			hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
		} catch (Throwable t) {
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		//Configurar los parametros de impresión del Certificado de calificacion
		String idCirculo = evento.getIdCirculo();
		if (idCirculo != null) {
			tabla.put(Processor.CIRCULO, idCirculo);
		}
		tabla.put(Processor.RESULT, evento.getRespuestaWf());
		String usuarioId = evento.getUsuario().getUsuarioId();
		tabla.put(CInfoUsuario.USUARIO_SIR, usuarioId);

		if (generarImprimible) {
			String fechaImpresion = this.getFechaImpresion();
			ImprimibleFormulario impFormulario = new ImprimibleFormulario(turno, evento.getUsuarioNeg().getUsername(), CTipoFormulario.TIPO_CALIFICACION, fechaImpresion, CTipoImprimible.FORMULARIO_CALIFICACION);
			impFormulario.setPrintWatermarkEnabled(true);

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

				if (nombre == null)
					nombre = "";

				impFormulario.setCargoRegistrador(cargoToPrint);
				impFormulario.setNombreRegistrador(nombre);

				//BUG 5098 Se elimina la carga de la imagen
				/*if (rutaFisica != null) {
					BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);

					byte pixeles[] = null;
					try {
						pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
					} catch (Throwable e1) {

						e1.printStackTrace();
					}
					impFormulario.setPixelesImagenFirmaRegistrador(pixeles);
				}
				//Fin del procesamiento de la imagen
				 */

				tabla.put(Processor.IMPRIMIBLE, impFormulario);

				//INGRESO DE INTENTOS DE IMPRESION
				String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
				if (intentosImpresion != null) {
					tabla.put(Processor.INTENTOS, intentosImpresion);
				} else {
					intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
					tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
				}

				//INGRESO TIEMPO DE ESPERA IMPRESION
				String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
				if (intentosImpresion != null) {
					tabla.put(Processor.ESPERA, esperaImpresion);
				} else {
					esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
					tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
				}

				//// Número de copias
				tabla.put(Processor.NUM_COPIAS_IMPRIMIBLE, new Integer(evento.getNumeroCopias()));

				//// Impresora Seleccionada
				if (evento.getImpresoraSeleccionada() != null) {
					tabla.put(Processor.IMPRESORA_SELECCIONADA, evento.getImpresoraSeleccionada());
				}

			} catch (Throwable t) {
				t.printStackTrace();
			}

		}

		try {
			//Se borran las notas devolutivas una vez impreso el formulario de calificacion,
			//esto se hace únicamente si el avance es de calificación.
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();

			if (fase != null && fase.getID().equals(CFase.CAL_CALIFICACION)) {
				hermod.removeDevolutivasFromTurno(tid);

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

			}

			//Casos especiales segun la respuesta del wf
			//CASO WF_DIGITACION
			if(evento.getRespuestaWf().equals(EvnCalificacion.WF_DIGITACION)){
				//Validar si tiene anotaciones temporales
				TurnoPk oid = new TurnoPk();
				oid.anio = turno.getAnio();
				oid.idCirculo = turno.getIdCirculo();
				oid.idProceso = turno.getIdProceso();
				oid.idTurno = turno.getIdTurno();
				boolean ok=false;
				ok=forseti.validarTerminacionCalificacion(oid,CTipoRevisionCalificacion.UN_FOLIO_UNA_ANOTACION);
				if(!ok){
					throw new ForsetiException("El turno debe tener al menos una anotacion temporal en alguno de los folios");
				}

				//bug 4991: Si el usuario tiene el rol de digitador se debe asignar
				//          a ese mismo usuario
				this.asignarSiguienteEstacionRol(u, CRol.ROL_DIGITADOR, tabla);

			}

		} catch (Throwable e1) {
			ExceptionPrinter ep = new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"El turno no se ha podido avanzar:" + ep.toString());
			throw new AvanzarCalificacionException("El turno no se ha podido avanzar.", e1);
		}
		return null;
	}


		/**
		 * Establece en la tabla de parámetros de avance la estación en caso que el usuario tenga una estación
		 * disponible en el rol indicado
		 * @param u
		 * @param rol_digitador
		 * @param tabla
		 * @throws Throwable
		 */
		private void asignarSiguienteEstacionRol(Usuario u, String rol, Hashtable tabla) throws Throwable {
			String estacionPrivadaUsuario = "X-"+u.getUsername();
			Rol rolDigitador = new Rol();
			rolDigitador.setRolId(rol);

			Estacion estacionSeleccionada = null;
			List estaciones = fenrir.getEstaciones(u, rolDigitador);
			if((estaciones!=null) && (!estaciones.isEmpty())){
				Estacion estacion;
				//Se busca preferiblemente la estación privada
				for(Iterator it = estaciones.iterator(); it.hasNext();){
					estacion = (Estacion)it.next();
					if(estacion.getEstacionId()!=null){
						if(estacion.getEstacionId().equals(estacionPrivadaUsuario)){
							estacionSeleccionada = estacion;
						}
					}
				}
				//Si no encontró la estación privada se asigna la primera de la lista
				if(estacionSeleccionada == null){
					estacionSeleccionada = (Estacion)estaciones.get(0);
				}
				tabla.put(Processor.SIGUIENTE_ESTACION, estacionSeleccionada.getEstacionId());
			}
	}

		/**
		 * @param evento
		 * @return
		 */
		private EventoRespuesta devolver(EvnCalificacion evento) throws EventoException {

			//SE OBTIENEN LOS PARAMETROS
			Turno turno=evento.getTurno();
			Solicitud solicitud = turno.getSolicitud();
			Fase fase=evento.getFase();
			Usuario u=evento.getUsuarioNeg();
			TurnoPk idTurno = new TurnoPk();
			idTurno.anio = turno.getAnio();
			idTurno.idCirculo = turno.getIdCirculo();
			idTurno.idProceso = turno.getIdProceso();
			idTurno.idTurno = turno.getIdTurno();

			// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
			//del turno desde esta fase.
			try {
					 Hashtable tablaAvance = new Hashtable(2);
					 tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
					 tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
					 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
			}
			catch(Throwable t)
			{
				  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}




			//SE DESHACEN LOS CAMBIOS TEMPORALES Y SE VUELVE A BLOQUEARLOS
			try {
				forseti.deshacerCambiosTurno(idTurno,u,false);
				//forseti.delegarBloqueoFolios(idTurno, u);
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANCalificacion.class,"No se pudieron deshacer los cambios:"+ep.toString());
				throw new AvanzarCalificacionException("Se presento una excepción al deshacer los cambios temporales",e1);
			}


			//SE CREA EL HASHTABLE DE PARAMETROS
			Hashtable tabla=new Hashtable();
			tabla.put(Processor.RESULT,evento.getRespuestaWf());
			tabla.put(CInfoUsuario.USUARIO_SIR, u.getUsername());

			//AVANZAR EL TURNO BORRANDO SEGREGACIONES O ENGLOBES PENDIENTES
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

				//hermod.avanzarTurno(turno,fase,tabla,CAvanzarTurno.AVANZAR_PUSH);
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANCalificacion.class,"No se pudo avanzar el turno:"+ep.toString());
				throw new AvanzarCalificacionException("No se pudo avanzar el turno se necesita agregar notas informativas",e1);
			}
			return null;
		}


		/**
		 * @param evento
		 * @return
		 */
		private EventoRespuesta inscripcionParcial(EvnCalificacion evento) throws EventoException {

			//SE OBTIENEN LOS PARAMETROS
			Turno turno=evento.getTurno();
			Solicitud solicitud = turno.getSolicitud();
			Fase fase=evento.getFase();
			Usuario u=evento.getUsuarioNeg();
			TurnoPk idTurno = new TurnoPk();
			idTurno.anio = turno.getAnio();
			idTurno.idCirculo = turno.getIdCirculo();
			idTurno.idProceso = turno.getIdProceso();
			idTurno.idTurno = turno.getIdTurno();

			Hashtable tabla=new Hashtable();

			List foliosInscripcionParcial = evento.getSolicitudFoliosInscripcionParcial();

			//Se valida que no hayan anotaciones inscritas
			boolean ok=false;
			try {
				ok=forseti.validarTerminacionCalificacion(idTurno,CTipoRevisionCalificacion.UN_FOLIO_UNA_ANOTACION);

				if(!ok){
					throw new ForsetiException("El turno debe tener anotaciones temporal en alguno de los folios");
				}
			}
			catch (Throwable e3) {
				throw new EventoException("",e3);
			}

			/** COLOCAR EL ESTADO A LA SOLICITUD FOLIO DE INSCRIPCION PARCIAL **/

			Hashtable tablaAnotacionesTemporales = new Hashtable();
			try {
				tablaAnotacionesTemporales = forseti.validarNuevasAnotacionesTurno(idTurno);
			} catch (Throwable e2) {
				e2.printStackTrace();
			}

			List solFolios = solicitud.getSolicitudFolios();
			boolean esta = false;

			for(Iterator it = solFolios.iterator(); it.hasNext();){
				SolicitudFolio solFolio = (SolicitudFolio) it.next();
				esta = false;
				for(Iterator it2 = foliosInscripcionParcial.iterator(); it2.hasNext() && !esta;){
					SolicitudFolio solTemp = (SolicitudFolio) it2.next();
					if(solTemp.getIdMatricula().equals(solFolio.getIdMatricula())){
						esta = true;
					}
				}
				if(esta){
					FolioPk idFolio = new FolioPk();
					idFolio.idMatricula = solFolio.getIdMatricula();
					
					//Modificar las solicitud del folio.
					Boolean tieneAnotacionesTemporales = (Boolean)tablaAnotacionesTemporales.get(solFolio.getIdMatricula());
					if(tieneAnotacionesTemporales.booleanValue()){
						solFolio.setEstado(CSolicitudFolio.ESTADO_INSCRITO_PARCIALMENTE);
						try {
							hermod.updateEstadoSolicitudFolio(solFolio);
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							Log.getInstance().error(ANCalificacion.class, e);
						}
					}else{
						solFolio.setEstado(CSolicitudFolio.ESTADO_INSCRITO_PARCIAL_NO_TMP);
						try {
							hermod.updateEstadoSolicitudFolio(solFolio);
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							Log.getInstance().error(ANCalificacion.class, e);
						}					
					}
				}else{
					//Si el folio no esta seleccionado es necesario ver si tiene anotaciones temporales
					Boolean tieneAnotacionesTemporales = (Boolean)tablaAnotacionesTemporales.get(solFolio.getIdMatricula());
					if(tieneAnotacionesTemporales.booleanValue()){
						//Modificar las solicitud del folio.
						solFolio.setEstado(CSolicitudFolio.ESTADO_INSCRITO);
						try {
							hermod.updateEstadoSolicitudFolio(solFolio);
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							Log.getInstance().error(ANCalificacion.class,e);
						}
					}else{
						//Modificar las solicitud del folio.
						solFolio.setEstado(CSolicitudFolio.ESTADO_DEVUELTO);
						try {
							hermod.updateEstadoSolicitudFolio(solFolio);
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							Log.getInstance().error(ANCalificacion.class,e);
						}
					}

				}
			}



			/** VALIDAR QUE SE NECESITE NOTA INFORMATIVA PARA AVANZAR EL TURNO **/
			try {
					 Hashtable tablaAvance = new Hashtable(2);
					 tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
					 tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
					 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
			}
			catch(Throwable t)
			{
				  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}


			/** GENERAR EL IMPRIMIBLE DE CALIFICACION **/

			//Obtener las solciitudes folios calificadas
			try{
			    List solicitudesFoliosCalificados = null;
			    solicitudesFoliosCalificados =this.getSolicitudesFolioCalificadas(forseti, idTurno, u);
			    Collections.sort(solicitudesFoliosCalificados, new IDMatriculaFolioComparator());
				turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
			}catch (Throwable e2){
				ExceptionPrinter ep=new ExceptionPrinter(e2);
				Log.getInstance().error(ANCalificacion.class,"No se pudieron desbloquear los folios:"+ep.toString());
				throw new EventoException("No se pudieron desbloquear los folios:"+e2.getMessage(),e2);
			}

			//			Configurar los parametros de impresión del Certificado de calificacion
			String idCirculo = evento.getIdCirculo();
			if (idCirculo != null) {
				tabla.put(Processor.CIRCULO, idCirculo);
			}
			tabla.put(Processor.RESULT, evento.getRespuestaWf());
			String usuarioId = evento.getUsuario().getUsuarioId();
			tabla.put(CInfoUsuario.USUARIO_SIR, usuarioId);

			String fechaImpresion = this.getFechaImpresion();
			ImprimibleFormulario impFormulario = new ImprimibleFormulario(turno, evento.getUsuarioNeg().getUsername(), CTipoFormulario.TIPO_CALIFICACION, fechaImpresion, CTipoImprimible.FORMULARIO_CALIFICACION);
			impFormulario.setPrintWatermarkEnabled(true);

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

				if (nombre == null)
					nombre = "";

				impFormulario.setCargoRegistrador(cargoToPrint);
				impFormulario.setNombreRegistrador(nombre);

				//BUG 5098 Se elimina la carga de la imagen
				/*if (rutaFisica != null) {
					BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);

					byte pixeles[] = null;
					try {
						pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
					} catch (Throwable e1) {

						e1.printStackTrace();
					}
					impFormulario.setPixelesImagenFirmaRegistrador(pixeles);
				}
				//Fin del procesamiento de la imagen
				 */

				tabla.put(Processor.IMPRIMIBLE, impFormulario);

				//INGRESO DE INTENTOS DE IMPRESION
				String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
				if (intentosImpresion != null) {
					tabla.put(Processor.INTENTOS, intentosImpresion);
				} else {
					intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
					tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
				}

				//INGRESO TIEMPO DE ESPERA IMPRESION
				String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
				if (intentosImpresion != null) {
					tabla.put(Processor.ESPERA, esperaImpresion);
				} else {
					esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
					tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
				}

				//// Número de copias
				tabla.put(Processor.NUM_COPIAS_IMPRIMIBLE, new Integer(evento.getNumeroCopias()));

				//// Impresora Seleccionada
				if (evento.getImpresoraSeleccionada() != null) {
					tabla.put(Processor.IMPRESORA_SELECCIONADA, evento.getImpresoraSeleccionada());
				}

			} catch (Throwable t) {
				t.printStackTrace();
			}




			/*
			//SE DESHACEN LOS CAMBIOS TEMPORALES Y SE VUELVE A BLOQUEARLOS
			try {

				forseti.delegarBloqueoFolios(idTurno, u);
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANCalificacion.class,"No se pudieron deshacer los cambios:"+ep.toString());
				throw new AvanzarCalificacionException("Se presento una excepción al deshacer los cambios temporales",e1);
			}*/


			//SE CREA EL HASHTABLE DE PARAMETROS

			tabla.put(Processor.RESULT,evento.getRespuestaWf());
			tabla.put(CInfoUsuario.USUARIO_SIR, u.getUsername());

			return null;
		}




	/**
		 * @param evento
		 * @return
		 */
		private EventoRespuesta delegarCorreccionEncabezado(EvnCalificacion evento) throws EventoException {
			
			
			Turno turno=evento.getTurno();
			Fase fase=evento.getFase();
			Hashtable tablaParametros =new Hashtable();
			Usuario u=evento.getUsuarioNeg();
			
			String USUARIO_INICIADOR = ( null != evento.getUsuarioNeg() )?(""+evento.getUsuarioNeg().getUsername()):("");
	        if( "".equals( USUARIO_INICIADOR ) || null == USUARIO_INICIADOR )
	            throw new TurnoNoAvanzadoException( "El usuario no se ha regostrado en la capa web." + this.getClass().getName() );

	        
			//CONFIGURAR NOTA INFORMATIVA
			String motivo = evento.getMotivoDevolucion();

			//GENERAR LA NOTA INFORMATIVA.
			//ES REQUERIDO QUE EXISTA UNA NOTA INFORMATIVA PARA ESTA FASE EN LA BD.
			ProcesoPk procesoId = new ProcesoPk();
			Long longProceso = new Long(CProceso.PROCESO_CALIFICACIONES);
			procesoId.idProceso = longProceso.longValue();
			try
			{

				boolean condicionNota=false;
				TipoNota tipoNota = null;
				List listaInformativas = hermod.getTiposNotaProceso(procesoId, CFase.CAL_CALIFICACION);
				if (listaInformativas!=null)
				{
					for (int i=0; i<listaInformativas.size(); i++)
					{
						tipoNota = (TipoNota) listaInformativas.get(i);
						if (tipoNota.getNombre().equals(CNota.NOMBRE_DELEGAR_CORRECCION_ENCABEZADO))
						{
							condicionNota = true;
							i = listaInformativas.size()+1;

						}

					}
					if (condicionNota)
					{
						Nota nuevaNota = new Nota();
						nuevaNota.setTiponota(tipoNota);
						nuevaNota.setDescripcion(motivo);
						nuevaNota.setUsuario(evento.getUsuarioNeg());
						nuevaNota.setIdCirculo(evento.getIdCirculo());
						Date fecha = new Date();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(fecha);
						nuevaNota.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
						nuevaNota.setIdFase(evento.getFase().getID());
						nuevaNota.setIdProceso(longProceso.longValue());
						nuevaNota.setTime(calendar.getTime());
						nuevaNota.setTurno(evento.getTurno());

						TurnoPk turnoId = new TurnoPk();
						turnoId.anio = turno.getAnio();
						turnoId.idCirculo = turno.getIdCirculo();
						turnoId.idProceso = turno.getIdProceso();
						turnoId.idTurno = turno.getIdTurno();

						hermod.addNotaToTurno(nuevaNota,turnoId);
						turno.addNota(nuevaNota);
					}

					else throw new AvanzarCalificacionException("No se encontró tipo de nota informativa para avanzar desde la fase de calificacion",null);

				}
				else
				{
					throw new AvanzarCalificacionException("No se encontró tipo de nota informativa para avanzar desde la fase de calificacion",null);
				}

			}
			catch(Throwable t)
			{
				throw new AvanzarCalificacionException("Error avanzando turno desde la fase de calificación",t);
			}

			
			/****************************************************************/
			/*                       ELIMINACION SAS                        */
			/****************************************************************/
			 Fase faseAvance = evento.getFase();    
             

		      	Hashtable tablaAvanzar = new Hashtable();
				tablaAvanzar.put(Processor.RESULT,evento.getRespuestaWf());
				             
				try 
				{
					hermod.avanzarTurnoNuevoPush(turno,faseAvance,tablaAvanzar,u);
				
				}
		        catch (Throwable exception) {
				    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
			    }
					
					
			
			return null;
		}


	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta confirmarCorreccion(EvnCalificacion evento) throws EventoException {
		
		SolicitudRegistro sol=evento.getSolicitud();
		Fase fase=evento.getFase();
		Hashtable tabla=new Hashtable();
		Usuario u=evento.getUsuarioNeg();
		tabla.put(Processor.RESULT,evento.getRespuestaWf());

		try {
			/**
			 * @author Cesar Ramírez
			 * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
			 * Se envía el usuario como parámetro al modificar el método updateEncabezadoInSolicitud()
			 **/
			hermod.updateEncabezadoInSolicitud(sol, u);
			this.avanzar(evento, false);
		}
		catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudo corregir el encabezado y avanzar el turno:"+ep.toString());
			throw new CorreccionEncabezadoNoEfectuadoException("No se pudo corregir el encabezado y avanzar el turno", e1);
		}

		return null;
	}


	private EventoRespuesta validarAprobarCalificacion(EvnCalificacion evento) throws EventoException
	{
		boolean ok=false;
		Turno turno = evento.getTurno();
		Usuario usuario = evento.getUsuarioNeg();
		
		SolicitudRegistro solicitud1 = (SolicitudRegistro)turno.getSolicitud();
		List folios1= solicitud1.getSolicitudFolios();
		this.metodoGeneralTurnosCorreecion(folios1,turno);

		TurnoPk oid = new TurnoPk();
		oid.anio = turno.getAnio();
		oid.idCirculo = turno.getIdCirculo();
		oid.idProceso = turno.getIdProceso();
		oid.idTurno = turno.getIdTurno();
		Hashtable ht = new Hashtable();

		try {
			ok=forseti.validarTerminacionCalificacion(oid,CTipoRevisionCalificacion.TODOS_FOLIOS_UNA_ANOTACION);
			ht = forseti.validarNuevasAnotacionesTurno(oid);

		} catch (Throwable e1) {

			e1.printStackTrace();
			throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti",e1);
		}

		try{
			SolicitudRegistro sol= (SolicitudRegistro)turno.getSolicitud();
			List folios=sol.getSolicitudFolios();
			Iterator ifol= folios.iterator();
			while(ifol.hasNext()){
				SolicitudFolio sfol=(SolicitudFolio) ifol.next();
				Folio fol = sfol.getFolio();
				FolioPk fid= new FolioPk();
				fid.idMatricula=fol.getIdMatricula();
				EstadoFolio est = new EstadoFolio();
				if(forseti.cerradoFolio(fid.idMatricula, null)){
					est.setIdEstado(CEstadoFolio.CERRADO);
				}else{
					est.setIdEstado(CEstadoFolio.ACTIVO);
				}
				fol.setEstado(est);
				sfol.setFolio(fol);
			}

		} catch (Throwable e1) {
			e1.printStackTrace();
			throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti",e1);
		}


		//CONSULTA PARA DETERMINAR SI EL FOLIO TIENE ANOTACIONES DE ACTUALIZACION DE DIRECCIONES O NOMENCLATURA
		//Y NO SE HA ACTUALIZADO EL FOLIO.
		List foliosSinActualizar = null;

		try{
			SolicitudRegistro sol= (SolicitudRegistro)turno.getSolicitud();
			SolicitudPk sid= new SolicitudPk();
			sid.idSolicitud = sol.getIdSolicitud();
			foliosSinActualizar = forseti.getFoliosAnotaActNomenclaturaSinActualizarBySolicitud(sid, usuario);
		} catch (Throwable e1) {
			e1.printStackTrace();
			throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti",e1);
		}

		//SE CREA EL EVENTO DE RESPUESTA
		EvnRespCalificacion respuesta = new EvnRespCalificacion(ok);
		respuesta.setValidacionAnotacionesTemporales(ht);
		respuesta.setFoliosSinActualizacionNomenclatura(foliosSinActualizar);


		//CONSULTA PARA DETERMINAR SI EL FOLIO TIENE SEGREGACIONES O ENGLOBES EN TRAMITE.
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

		if(rta!=null && !rta.isEmpty()){

			Iterator itSegEng = rta.iterator();
			if(itSegEng.hasNext()){
				WebSegEng webSegEng = (WebSegEng)itSegEng.next();
				if(webSegEng instanceof WebSegregacion){

					String sOrden = "";
					WebSegregacion webSegregacion = (WebSegregacion)webSegEng;
					if(webSegregacion.getFoliosHeredados()!=null && !webSegregacion.getFoliosHeredados().isEmpty()){
						WebFolioHeredado webFolioHeredado = (WebFolioHeredado)webSegregacion.getFoliosHeredados().get(0);
						FolioPk folioID = new FolioPk();
						folioID.idMatricula=webFolioHeredado.getIdMatricula();
						Folio folio = null;

						long orden = 0;

						//SOLICITAR AL SERVICIO
						try {
							orden = forseti.getNextOrdenAnotacion(folioID);
							folio = forseti.getFolioByIDSinAnotaciones(folioID);

							if(folio ==null ){
								folio = forseti.getFolioByIDSinAnotaciones(folioID, usuario);
							}
						} catch(ForsetiException he){
							throw new SegregacionException("No se pudo consultar si hay englobes o segregaciones en curso.",he);
						} catch (Throwable te) {
							throw new SegregacionException("No se pudo consultar si hay englobes o segregaciones en curso.",te);
						}

						//CAST
						sOrden=String.valueOf(orden);
						respuesta.setNextOrden(sOrden);
						respuesta.setFolio(folio);
					}

				}
				respuesta.setWebSegEng(webSegEng);

			}


		}

		return respuesta;
	}

	/**
	* @param evento
	* @return
	*/
	private EventoRespuesta delegarConfrontacion(EvnCalificacion evento) throws EventoException {

			Turno turno=evento.getTurno();

			String motivo = evento.getMotivoDevolucion();
			Fase fase=evento.getFase();
			Hashtable tabla=new Hashtable();
			Usuario u=evento.getUsuarioNeg();
			tabla.put(Processor.RESULT,evento.getRespuestaWf());
			String USUARIO_INICIADOR = ( null != evento.getUsuarioNeg() )?(""+evento.getUsuarioNeg().getUsername()):("");
	        if( "".equals( USUARIO_INICIADOR ) || null == USUARIO_INICIADOR )
	            throw new TurnoNoAvanzadoException( "El usuario no se ha regostrado en la capa web." + this.getClass().getName() );

	        tabla.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);

			//GENERAR LA NOTA INFORMATIVA.
			//ES REQUERIDO QUE EXISTA UNA NOTA INFORMATIVA PARA ESTA FASE EN LA BD.
			ProcesoPk procesoId = new ProcesoPk();
			Long longProceso = new Long(CProceso.PROCESO_CALIFICACIONES);
			procesoId.idProceso = longProceso.longValue();
			try
			{
				boolean condicionNota=false;
				TipoNota tipoNota = null;
				List listaInformativas = hermod.getTiposNotaProceso(procesoId, CFase.CAL_CALIFICACION);
				if (listaInformativas!=null)
				{
					for (int i=0; i<listaInformativas.size(); i++)
					{
						tipoNota = (TipoNota) listaInformativas.get(i);
						if (tipoNota.getNombre().equals(CNota.NOMBRE_DELEGAR_CONFRONTACION_CORRECTIVA))
						{
							condicionNota = true;
							i = listaInformativas.size()+1;

						}

					}
					if (condicionNota)
					{
						Nota nuevaNota = new Nota();
						nuevaNota.setTiponota(tipoNota);
						nuevaNota.setDescripcion(motivo);
						nuevaNota.setUsuario(evento.getUsuarioNeg());
						nuevaNota.setIdCirculo(evento.getIdCirculo());
						Date fecha = new Date();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(fecha);
						nuevaNota.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
						nuevaNota.setIdFase(evento.getFase().getID());
						nuevaNota.setIdProceso(longProceso.longValue());
						nuevaNota.setTime(calendar.getTime());
						nuevaNota.setTurno(evento.getTurno());

						TurnoPk turnoId = new TurnoPk();
						turnoId.anio = turno.getAnio();
						turnoId.idCirculo = turno.getIdCirculo();
						turnoId.idProceso = turno.getIdProceso();
						turnoId.idTurno = turno.getIdTurno();

						hermod.addNotaToTurno(nuevaNota,turnoId);
						turno.addNota(nuevaNota);
					}

					else throw new AvanzarCalificacionException("No se encontró tipo de nota informativa para avanzar desde la fase de calificacion",null);

				}
				else
				{
					throw new AvanzarCalificacionException("No se encontró tipo de nota informativa para avanzar desde la fase de calificacion",null);
				}

			}
			catch(Throwable t)
			{
				throw new AvanzarCalificacionException("Error avanzando turno desde la fase de calificación",t);
			}


			//Configurar los parametros de impresión del Certificado de calificacion
			String idCirculo = evento.getIdCirculo();
			if (idCirculo!=null)
			{
				tabla.put(Processor.CIRCULO,idCirculo);
				tabla.put(Processor.RESULT,evento.getRespuestaWf());
				String usuarioId=evento.getUsuarioNeg().getUsername();
				tabla.put(CInfoUsuario.USUARIO_SIR,usuarioId);


			}
			
			return new EvnRespCalificacion(EvnRespCalificacion.CONFRONTACION);
		}

	private EventoRespuesta aprobarMayorValor(EvnCalificacion evento) throws EventoException
	{
	    Turno turno=evento.getTurno();
	    Solicitud solicitud = turno.getSolicitud();
		SolicitudPk sid = new SolicitudPk();
		sid.idSolicitud = solicitud.getIdSolicitud();

	    LiquidacionTurnoRegistro liq= evento.getLiquidacion();
	    Fase fase= evento.getFase();

		Hashtable parametros=new Hashtable();

		//inicializar tabla hashing
		parametros.put("RESULT", CRespuesta.PAGO_MAYOR_VALOR);
		parametros.put("USUARIO_SIR",evento.getUsuarioNeg().getUsername());


		try {

			TurnoPk oid = new TurnoPk();
			oid.anio = turno.getAnio();
			oid.idCirculo = turno.getIdCirculo();
			oid.idProceso = turno.getIdProceso();
			oid.idTurno = turno.getIdTurno();

			//Se valida que no hayan notas devolutivas.
			turno = hermod.getTurno(oid);
			List notas = turno.getNotas();
			for(Iterator it= notas.iterator(); it.hasNext();){
				Nota nota = (Nota) it.next();
				TipoNota tipo= nota.getTiponota();
				if(tipo.isDevolutiva()){
					throw new HermodException("El turno no puede tener notas devolutivas");
				}
			}

			//Se valida que no hayan anotaciones inscritas
			boolean ok=false;
			ok=forseti.validarTerminacionCalificacion(oid,CTipoRevisionCalificacion.UN_FOLIO_UNA_ANOTACION);
			if(ok){
				throw new ForsetiException("El turno no debe tener anotaciones temporal en alguno de los folios");
			}

			//SE VALIDA QUE NO EXISTAN ENGLOBES O SEGREGACIONES TEMPORALES.
			String exc = "";
			List segEngTemporales = null;
			segEngTemporales =  hermod.getWebSegEngBySolicitud(sid);
			if(segEngTemporales!=null && segEngTemporales.size()>0){
				exc = "No se puede avanzar el turno; existen englobes o segregaciones temporales. Debe eliminarlas si desea enviar el turno a otras dependencias.";
				TurnoNoAvanzadoException excepcion = new TurnoNoAvanzadoException(exc);
				throw excepcion;
			}


		}catch(TurnoNoAvanzadoException t){
			throw t;
  		}catch(ForsetiException t){
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno: " + t.getMessage());
		}catch(HermodException t){
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno: " + t.getMessage());
		}catch(Throwable t) {
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno");
		}




		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase.
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, CRespuesta.PAGO_MAYOR_VALOR);
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}





		try {


			SolicitudRegistro sol= (SolicitudRegistro)turno.getSolicitud();
			//se añade la liquidacion al turno
		    hermod.addLiquidacionToSolicitud(sol, liq);
		    //se avanza el turno
		    //hermod.avanzarTurno(turno,fase,parametros,CAvanzarTurno.AVANZAR_PUSH);


		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudo avanzar el turno:"+ep.toString());
			throw new AvanzarCalificacionException("No se pudo avanzar el turno. ",e1);
		}

		return new EvnRespCalificacion(turno, EvnRespCalificacion.APROBAR_MAYOR_VALOR);
	}

	/**
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta registrarPagoExceso(EvnCalificacion evento) throws EventoException
	{
		Turno turno=evento.getTurno();
		LiquidacionTurnoRegistro liq= evento.getLiquidacion();
		Fase fase= evento.getFase();
		boolean confirmacion = false;

		try {
			SolicitudRegistro sol= (SolicitudRegistro)turno.getSolicitud();
			//se añade la liquidacion al turno
			confirmacion = hermod.addLiquidacionToSolicitud(sol, liq);
		} catch(HermodException he){
			throw new AvanzarCalificacionException("No se pudo registrar el pago en exceso.",he);
		} catch (Throwable te) {
			ExceptionPrinter ep=new ExceptionPrinter(te);
			Log.getInstance().error(ANCalificacion.class,"No se pudo registrar el pago en exceso. "+ep.toString());
			throw new AvanzarCalificacionException("No se pudo registrar el pago en exceso. ",te);
		}

		return new EvnRespCalificacion(new Boolean(confirmacion), EvnRespCalificacion.REGISTRAR_PAGO_EXCESO);
	}


	/**
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta imprimirFormularioCalificacion(EvnCalificacion evento) throws EventoException
	{
	    Turno turno=evento.getTurno();
	    String UID=evento.getUID();

	    SolicitudRegistro solicitud = (SolicitudRegistro)turno.getSolicitud();
		List folios= solicitud.getSolicitudFolios();
		this.metodoGeneralTurnosCorreecion(folios,turno);
	    
	    TurnoPk oid=new TurnoPk();
		oid.anio=turno.getAnio();
		oid.idCirculo=turno.getIdCirculo();
		oid.idProceso=turno.getIdProceso();
		oid.idTurno=turno.getIdTurno();
		Usuario u=evento.getUsuarioNeg();
		Turno turnoTemp = null;

		try {
			//actualizar los folios segun los modificados en la solicitud
			try{
				turnoTemp = hermod.getTurno(oid);
			    List solicitudesFoliosCalificados = null;
			    solicitudesFoliosCalificados =this.getSolicitudesFolioCalificadas(forseti, oid, u);
			    Collections.sort(solicitudesFoliosCalificados, new IDMatriculaFolioComparator());
			    //turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
			    turnoTemp.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
			}catch (Throwable e2){
				ExceptionPrinter ep=new ExceptionPrinter(e2);
				Log.getInstance().error(ANCalificacion.class,"No se pudieron obtener los folios calificados:"+ep.toString());
				throw new EventoException("No se pudieron obtener los folios calificados:"+e2.getMessage(),e2);
			}

//			obtener textos base de los separadores
			String tbase1 ="";
			String tbase2 = "";
			List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
			for(Iterator j= variablesImprimibles.iterator(); j.hasNext();){
				OPLookupCodes op = (OPLookupCodes) j.next();
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
					tbase1= op.getValor();
				}
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
					tbase2 = op.getValor();
				}
			}
			
			//crear el imprimible de formulario
			String fechaImpresion = this.getFechaImpresion();
			ImprimibleFormulario impFormulario = new ImprimibleFormulario(turnoTemp,evento.getUsuarioNeg().getUsername(),CTipoFormulario.TIPO_CALIFICACION, fechaImpresion, CTipoImprimible.FORMULARIO_CALIFICACION);
			impFormulario.setIdUsuario(Long.toString(u.getIdUsuario()));
			impFormulario.setPrintWatermarkEnabled(true);
			impFormulario.setCalificacionTemporal(true);
			impFormulario.setTEXTO_BASE1(tbase1);
			impFormulario.setTEXTO_BASE2(tbase2);
			// bug:  0002580
			impFormulario.setImprimirMargen( false );

			//mandar a imprimir documento
			try{
				this.imprimir(impFormulario, UID);
			}catch(PrintJobsException exc){
				int idImprimible = exc.getIdImpresion();
				if(idImprimible!=0){
					throw new ImpresionException("Hubo problemas de comunicación al intentar realizar la impresión <SPAN class='botontextual'><FONT  size='2'><b>("+idImprimible+")</b></FONT></SPAN>, por favor ingrese este número en el aplicativo de impresión SIR, para realizar esta impresión.");
				}
			}



		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudo imprimir el formulario:"+ep.toString());
			throw new AvanzarCalificacionException("No se pudo imprimir el formulario",e1);
		}

		return new EvnRespCalificacion(turno, EvnRespCalificacion.APROBAR_MAYOR_VALOR);
	}
            private Pageable imprimirFormularioCalificacionReg(EvnCalificacion evento, int isfirma, int isvisua, HttpServletResponse response,HttpServletRequest request) throws EventoException
                {
                    Pageable peg = null;
                    Turno turno=evento.getTurno();
                    String UID=evento.getUID();
                    Hashtable parametros = new Hashtable();
                    SolicitudRegistro solicitud = (SolicitudRegistro)turno.getSolicitud();
                        List folios= solicitud.getSolicitudFolios();
                        TurnoPk oid=new TurnoPk();
                        oid.anio=turno.getAnio();
                        oid.idCirculo=turno.getIdCirculo();
                        oid.idProceso=turno.getIdProceso();
                        oid.idTurno=turno.getIdTurno();
                        Usuario u=evento.getUsuarioNeg();
                        Turno turnoTemp = null;
                        Usuario usuario = null;
                        String impresion = "";
                        List historia = turno.getHistorials();
                        int ef = 0;
                        while (ef < historia.size()) {
                            TurnoHistoria historial = (TurnoHistoria) historia.get(ef);
                            if (historial.getFase().equals(CFase.CAL_CALIFICACION)) {
                                if (ef != historia.size() - 1) {
                                    usuario = historial.getUsuarioAtiende();
                                    impresion = historial.getNcopias();
                                }

                            }
                            ef++;
                        }
                        try {
                                //actualizar los folios segun los modificados en la solicitud
                                try{
                                    turnoTemp = hermod.getTurno(oid);
                                   List solicitudesFoliosCalificados = null;
                                   boolean pasoFirma;
                                   if(isvisua == 0){
                                       pasoFirma = true;
                                   }else{
                                       pasoFirma = false;
                                   }
                                    solicitudesFoliosCalificados = getSolicitudesFolioCalificadas(forseti, oid, usuario, pasoFirma);
                                    turnoTemp.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
                                }catch (Throwable e2){
                                        ExceptionPrinter ep=new ExceptionPrinter(e2);
                                        Log.getInstance().error(ANCalificacion.class,"No se pudieron obtener los folios calificados:"+ep.toString());
                                        throw new EventoException("No se pudieron obtener los folios calificados:"+e2.getMessage(),e2);
                                }

        //			obtener textos base de los separadores
                                String tbase1 ="";
                                String tbase2 = "";
                                List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
                                for(Iterator j= variablesImprimibles.iterator(); j.hasNext();){
                                        OPLookupCodes op = (OPLookupCodes) j.next();
                                        if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
                                                tbase1= op.getValor();
                                        }
                                        if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
                                                tbase2 = op.getValor();
                                        }
                                }
                              
                                
                                //crear el imprimible de formulario
                                String fechaImpresion = this.getFechaImpresion();
                                ImprimibleFormulario impFormulario = new ImprimibleFormulario(turnoTemp,usuario.getUsername(),CTipoFormulario.TIPO_CALIFICACION, fechaImpresion, CTipoImprimible.FORMULARIO_CALIFICACION);
                                impFormulario.setIdUsuario(Long.toString(usuario.getIdUsuario()));
                                impFormulario.setPrintWatermarkEnabled(true);
                                impFormulario.setTurno(turnoTemp);
                                impFormulario.setTEXTO_BASE1(tbase1);
                                impFormulario.setTEXTO_BASE2(tbase2);
                                if(isvisua == 0){
                                impFormulario.setChangetextforregistrador(true);
                                 
                                }
                                //PageFormat pageFormat = new PageFormat();
                                //imprimibleCertificado.generate(pageFormat);
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
                                    throw e;
                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }

                                if (sNombre == null) {
                                    sNombre = "";
                                }

                                //+++
                                impFormulario.setCargoRegistrador(cargoToPrint);
                                impFormulario.setNombreRegistrador(sNombre);

                                if (rutaFisica != null) {
                                    BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                                     byte pixeles[] = null;
                                    try {
                                        pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                                    } catch (Throwable e1) {

                                        e1.printStackTrace();
                                    }
                                    if(isvisua == 0){
                                        impFormulario.setPixelesImagenFirmaRegistrador(pixeles);
                                    }
                                    
                                }
                                
                                String idWorkFlow = evento.getTurno().getIdWorkflow();
                                isREL = hermod.isTurnoREL(idWorkFlow);
                                //mandar a imprimir documento
                                try{
                                        if(isvisua == 0){
                                            String impresoraSeleccionadaNuevo = "";
                                            if (evento.getImpresoraSeleccionada() != null) {
                                                    impresoraSeleccionadaNuevo = evento.getImpresoraSeleccionada();
                                            }
                                            int impl = 1;
                                            if(impresion != null){
                                                if(!impresion.equals("") && !impresion.equals("NONE")){
                                                impresion = impresion.trim();
                                                impl = Integer.parseInt(impresion);
                                                }
                                            }
                                            for(int e =0; e < impl; e++){
                                                this.imprimirC(impFormulario, UID,impresoraSeleccionadaNuevo);  
                                            }
                                        }else{
                                            PrinterJob printJob = PrinterJob.getPrinterJob();
                                            PageFormat pf = printJob.defaultPage();
                                            Paper papel = new Paper();
                                            double mWidth = LETTER_WIDTH;
                                            double mHeight = LETTER_HEIGHT;

                                            papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                                                mHeight - (0.5 * INCH));
                                            pf.setPaper(papel);
                                            impFormulario.generate(pf);
                                            this.generarVisualizacion(impFormulario, request, response);
                                        }
                                }catch(PrintJobsException exc){
                                        int idImprimible = exc.getIdImpresion();
                                        if(idImprimible!=0){
                                                throw new ImpresionException("Hubo problemas de comunicación al intentar realizar la impresión <SPAN class='botontextual'><FONT  size='2'><b>("+idImprimible+")</b></FONT></SPAN>, por favor ingrese este número en el aplicativo de impresión SIR, para realizar esta impresión.");
                                        }
                                }


                        return impFormulario;
                        } catch (Throwable e1) {
                                ExceptionPrinter ep=new ExceptionPrinter(e1);
                                Log.getInstance().error(ANCalificacion.class,"No se pudo imprimir el formulario:"+ep.toString());
                                throw new AvanzarCalificacionException("No se pudo imprimir el formulario",e1);
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
	/**
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	/*private EventoRespuesta imprimirFolioTemporal(EvnCalificacion evento) throws EventoException
	{
		Turno turno=evento.getTurno();
		String UID=evento.getUID();

		Turno.ID oid=new Turno.ID();
		oid.anio=turno.getAnio();
		oid.idCirculo=turno.getIdCirculo();
		oid.idProceso=turno.getIdProceso();
		oid.idTurno=turno.getIdTurno();
		Usuario u=evento.getUsuarioNeg();

		try {

			//actualizar los folios segun los modificados en la solicitud
			try{
				List solicitudesFoliosCalificados = null;
				solicitudesFoliosCalificados =this.getSolicitudesFolioCalificadas(forseti, oid, u);
				turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
			}catch (Throwable e2){
				ExceptionPrinter ep=new ExceptionPrinter(e2);
				Log.getInstance().error(ANCalificacion.class,"No se pudieron obtener los folios calificados:"+ep.toString());
				throw new EventoException("No se pudieron obtener los folios calificados:"+e2.getMessage(),e2);
			}


			//crear el imprimible de formulario
			ImprimibleFormulario impFormulario = new ImprimibleFormulario(turno,evento.getUsuarioNeg().getUsername(),CTipoFormulario.TIPO_CALIFICACION);
			impFormulario.setCalificacionTemporal(true);

			// bug:  0002580
			impFormulario.setImprimirMargen( false );

			//mandar a imprimir documento
			this.imprimir(impFormulario, UID);


		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudo imprimir el formulario:"+ep.toString());
			throw new AvanzarCalificacionException("No se pudo imprimir el formulario",e1);
		}

		return new EvnRespCalificacion(turno, EvnRespCalificacion.APROBAR_MAYOR_VALOR);
	}*/


	/**
	 * Este método se encarga de invocar los servicios necesarios para la
	 * impresión de un folio específico
	 * @return <code>EvnRespTrasladoTurno</code>
	 * @throws <code>EventoException</code>
	 */
	private EvnRespCalificacion imprimirFolioTemporal(EvnCalificacion evento) throws EventoException {

		Turno turno=evento.getTurno();
		ImprimibleFolioSimple impFolioSimple = null;
		Usuario usuarioSIR = evento.getUsuarioNeg();
		String UID=evento.getUID();

		try {
			Folio folio = evento.getFolio();

			String matricula = folio.getIdMatricula();

			FolioPk fid = new FolioPk();
			fid.idMatricula = matricula;

			Folio folioSimple = forseti.getFolioByID(fid,usuarioSIR);
			if(folioSimple.getAnotaciones() == null || folioSimple.getAnotaciones().size()== 0){
				folioSimple.setAnotaciones(forseti.getAnotacionesFolioTMP(fid));
			}
			List padres=forseti.getFoliosPadre(fid,usuarioSIR);
			List hijos=forseti.getFolioHijosEnAnotacionesConDireccion(fid);
                        /**
                        * @author: David Panesso
                        * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                        * Nuevo listado de folios derivados
                        **/
                        List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

			String fechaImpresion = this.getFechaImpresion();
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
			impFolioSimple = new ImprimibleFolioSimple(folioSimple, padres, hijos, foliosDerivadoHijos, fechaImpresion, CTipoImprimible.FOLIO_SIMPLE, tbase1, tbase2);
                        /* @autor          : JATENCIA 
                         * @mantis         : 0014985 
                         * @Requerimiento  : 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                         */
                        Traslado traslado = hermod.getFolioDestinoTraslado(matricula);
                        impFolioSimple.setInfoTraslado(traslado);
                        /* Fin del bloque */
			impFolioSimple.setRegistro(true);

			//se manda a imprimir el recibo por el identificador unico de usuario
		} catch (Throwable t) {
			t.printStackTrace();
			throw new EventoException("Error al imprimir el folio: " + t.getMessage());
		}


		if (impFolioSimple == null)
			throw new EventoException("Error al imprimir el folio: " + "el imprimible es null");


		try{
			this.imprimir(impFolioSimple,UID);
		}catch(PrintJobsException exc){
			int idImprimible = exc.getIdImpresion();
			if(idImprimible!=0){
				throw new ImpresionException("Hubo problemas de comunicación al intentar realizar la impresión <SPAN class='botontextual'><FONT  size='2'><b>("+idImprimible+")</b></FONT></SPAN>, por favor ingrese este número en el aplicativo de impresión SIR, para realizar esta impresión.");
			}
		}



		return new EvnRespCalificacion(turno, EvnRespCalificacion.APROBAR_MAYOR_VALOR);
	}


	/**
     * @param imprimible
     * @param uid
     */
    private void imprimirC(ImprimibleBase imprimible, String uid, String Impresora) throws PrintJobsException, EventoException {


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
        if(isREL){
            bundle.setNumeroCopias(0);
        } else{
            bundle.setNombreImpresora(Impresora);
        }
        try{
                    if(Impresora != null){
                        bundle.setNombreImpresora(Impresora);
                    }else{
                        bundle.setNombreImpresora("Microsoft Print to PDF");
                    }
                }catch(Throwable T){
                    bundle.setNombreImpresora("Microsoft Print to PDF");
                }
        try {
            //se manda a imprimir el recibo por el identificador unico de usuario
            printJobs.enviar(uid, bundle, maxIntentos, espera);
        }catch (PrintJobsException pe) {
			throw pe;
		}catch (Throwable t) {
            t.printStackTrace();
        }
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
	private Pageable imprimirNotaDevolutiva(EvnCalificacion evento, Nota nota,int isvisua,HttpServletResponse response, HttpServletRequest request, TurnoHistoria historiaaaa) throws EventoException {
		Hashtable tabla = new Hashtable();
                int rta = -1;
		Pageable peg = null;
		Vector notas1 = new Vector(evento.getTurno().getNotas());
		
		Turno turno = evento.getTurno();
		if(turno==null){
			try{
				TurnoPk tid = new TurnoPk();
				tid.anio = nota.getAnio();
				tid.idCirculo = nota.getIdCirculo();
				tid.idProceso = nota.getIdProceso();
				tid.idTurno = nota.getIdTurno();
				
				turno = hermod.getTurno(tid);
			} catch (Throwable e) {
			
                        }
			
			
		}
		if(turno != null){
			for(int i=0; i<notas1.size();i++){
				((Nota)notas1.get(i)).setTurno(turno);
			}
		}
                List notasss = new ArrayList();
                for(int e=0; e<notas1.size();e++){
			Nota n =(Nota)notas1.get(e);
                        if(n.getTiponota().isDevolutiva()){
                            notasss.add(n);
                            n.setTurno(turno);
                        }
                }
                Vector notas = new Vector(notasss);
                Circulo circulo = null;
                CirculoPk tid = new CirculoPk();
		tid.idCirculo = evento.getTurno().getIdCirculo();
                    try {
                        circulo = forseti.getCirculo(tid);
                    } catch (Throwable ex) {
                        
                    }
		String matricula = null;
		String idWorkflow = null;
		Usuario usuarioSIR = null;
                int i = 0;
                String impresion1 = "";
                List historia = turno.getHistorials();
                while (i < historia.size()) {
                    TurnoHistoria historial = (TurnoHistoria) historia.get(i);
                    if (historial.getFase().equals(CFase.CAL_CALIFICACION)) {
                        if (i != historia.size() - 1) {
                            usuarioSIR = historial.getUsuarioAtiende();
                            impresion1 =  historial.getNcopias();
                        }

                    }
                    i++;
                }
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";
		int maxIntentos;
		int espera;

                if(historiaaaa != null){
                    usuarioSIR = historiaaaa.getUsuarioAtiende();
                    impresion1 =  historiaaaa.getNcopias();
                }
		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS. 

		//INGRESO DE INTENTOS DE IMPRESION
		try{
		
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
			if (intentosImpresion != null){
				 tabla.put(INTENTOS,intentosImpresion);
			}else{
				tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			}
			
			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null){
				tabla.put(ESPERA,esperaImpresion);
			}
			else{
				tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);
			}
		}catch (Throwable t){			
			tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);			
		}

		if (turno != null) {
			idWorkflow = turno.getIdWorkflow();
			List solicitudesFolio = turno.getSolicitud().getSolicitudFolios();
			if (solicitudesFolio != null && !solicitudesFolio.isEmpty()) {
				SolicitudFolio solicitudFolio = (SolicitudFolio) solicitudesFolio.get(0);
				matricula = solicitudFolio.getIdMatricula();
			}

		}
               SolicitudRegistro  solRegistro = null;
                try {
                    solRegistro = (SolicitudRegistro) hermod.getSolicitudById(turno.getSolicitud().getIdSolicitud());
                } catch (Throwable ex) {
                solRegistro = (SolicitudRegistro)turno.getSolicitud();
		
                }
		String certAsociados = this.obtenerTextoCertificadosAsociados(turno);
		String fechaImpresion = this.getFechaImpresion();
                
                ValidacionImprimibleSIR iR = new ValidacionImprimibleSIR();
                String clase = null;
                try {
                    clase = iR.buscarVersionImprimible("NOTA_DEVOLUTIVA",nota.getTime());
                    isREL = hermod.isTurnoREL(idWorkflow);
                } catch (Throwable ex) {
                
                }
                
                
                
                if("ImprimibleNotaDevolutivaCalificacionB".equals(clase)){
                    peg = imprimibleNotaDevolutivaCalificacionB(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla, isvisua,response,request,impresion1);
                } else if("ImprimibleNotaDevolutivaCalificacionA".equals(clase)){
                    peg = imprimibleNotaDevolutivaCalificacionA(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla, isvisua,response,request,impresion1);
                } else if("ImprimibleNotaDevolutivaCalificacion".equals(clase)){
                    peg = imprimibleNotaDevolutivaCalificacion(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla, isvisua,response,request,impresion1);
                }
                
                return peg;
	}
        private Pageable imprimibleNotaDevolutivaCalificacionB(EvnCalificacion evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla, int isvisua,HttpServletResponse response, HttpServletRequest request, String impresion1) throws EventoException{
                    Pageable peg = null;
                    ImprimibleNotaDevolutivaCalificacionB impNota = new ImprimibleNotaDevolutivaCalificacionB(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    ArrayList matriculasNoInscritas = new ArrayList();
                    List solicitudFolios = turno.getSolicitud().getSolicitudFolios();
			ArrayList matriculasInscritasParcialmente = new ArrayList();
			for(int i=0; i<solicitudFolios.size(); i++){
				SolicitudFolio solFolio = (SolicitudFolio) solicitudFolios.get(i);
				if(solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIALMENTE ||
						solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIAL_NO_TMP){
					matriculasInscritasParcialmente.add(solFolio.getIdMatricula());
				}else {
					matriculasNoInscritas.add(solFolio.getIdMatricula());
				}
			}
			impNota.setMatriculasInscritasParcialmente(matriculasInscritasParcialmente);
			impNota.setMatriculasNoInscritas(matriculasNoInscritas);
                    if(isvisua == 0){
                impNota.setChangetextforregistrador(true);
		                 
                                }
                    /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
                    //OPCIONES PARA COLOCAR LA FIRMA DEL REGISTRADOR
                    
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
                    impNota.setCargoRegistrador(cargoToPrint);
                    impNota.setNombreRegistrador(sNombre);

                    if (rutaFisica != null) {
                        BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                         byte pixeles[] = null;
                        try {
                            pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                        } catch (Throwable e1) {

                            e1.printStackTrace();
                        }
                        if(isvisua == 0){
                            impNota.setPixelesImagenFirmaRegistrador(pixeles);
                        
                        }
                       
                    }		
		
		
                        if(isvisua == 0){
                            
                            int impl = 2;
                            if(impresion1 != null){
                                                if(!impresion1.equals("") && !impresion1.equals("NONE")){
                                                impresion1 = impresion1.trim();
                                                impl = Integer.parseInt(impresion1);
                                                }
                                            }
                                  String impresoraSeleccionadaNuevo = "";
                            if (evento.getImpresoraSeleccionada() != null) {
                                    impresoraSeleccionadaNuevo = evento.getImpresoraSeleccionada();
                            }
                            tabla.put("Impresora", impresoraSeleccionadaNuevo);
                          int impresa =  this.imprimirN(impNota, tabla, circulo.getIdCirculo(), impl, false);
                           
                            }else if(isvisua == 3){
                                 impNota.setPrintWatermarkEnabled(false);
                        impNota.setImprimirLogoEnabled(false);
                        int impresa =  this.imprimirN(impNota, tabla, circulo.getIdCirculo(), 1, false);
                        }else{
                                PrinterJob printJob = PrinterJob.getPrinterJob();
                            PageFormat pf = printJob.defaultPage();
                            Paper papel = new Paper();
                            double mWidth = LETTER_WIDTH;
                            double mHeight = LETTER_HEIGHT;

                            papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                                mHeight - (0.5 * INCH));
                            pf.setPaper(papel);
                            impNota.generate(pf);
                            this.generarVisualizacion(impNota, request, response);
                        }
		
                    return impNota;
        }
        private Pageable imprimibleNotaDevolutivaCalificacionA(EvnCalificacion evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla, int isvisua,HttpServletResponse response, HttpServletRequest request,String impresion1) throws EventoException{
                    ImprimibleNotaDevolutivaCalificacionA impNota = new ImprimibleNotaDevolutivaCalificacionA(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    Pageable peg = null;
                    if(isvisua == 0){
                impNota.setChangetextforregistrador(true);
		                 
                                }
                     ArrayList matriculasNoInscritas = new ArrayList();
                    List solicitudFolios = turno.getSolicitud().getSolicitudFolios();
			ArrayList matriculasInscritasParcialmente = new ArrayList();
			for(int i=0; i<solicitudFolios.size(); i++){
				SolicitudFolio solFolio = (SolicitudFolio) solicitudFolios.get(i);
				if(solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIALMENTE ||
						solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIAL_NO_TMP){
					matriculasInscritasParcialmente.add(solFolio.getIdMatricula());
				}else {
					matriculasNoInscritas.add(solFolio.getIdMatricula());
				}
			}
			impNota.setMatriculasInscritasParcialmente(matriculasInscritasParcialmente);
			impNota.setMatriculasNoInscritas(matriculasNoInscritas);
                    /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
                    //OPCIONES PARA COLOCAR LA FIRMA DEL REGISTRADOR
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
                    impNota.setCargoRegistrador(cargoToPrint);
                    impNota.setNombreRegistrador(sNombre);

                    if (rutaFisica != null) {
                        BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                         byte pixeles[] = null;
                        try {
                            pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                        } catch (Throwable e1) {

                            e1.printStackTrace();
                        }
                        if(isvisua == 0){
                            impNota.setPixelesImagenFirmaRegistrador(pixeles);
                        }
                            
                       
                    }		
		
		
                if(isvisua == 0){
                      
                            int impl = 2;
                             if(impresion1 != null){
                                                if(!impresion1.equals("") && !impresion1.equals("NONE")){
                                                impresion1 = impresion1.trim();
                                                impl = Integer.parseInt(impresion1);
                                                }
                                            }
                                String impresoraSeleccionadaNuevo = "";
                         if (evento.getImpresoraSeleccionada() != null) {
                                 impresoraSeleccionadaNuevo = evento.getImpresoraSeleccionada();
                         }
                            tabla.put("Impresora", impresoraSeleccionadaNuevo);
                          int impresa =  this.imprimirN(impNota, tabla, circulo.getIdCirculo(), impl, false);
                           
                }else if(isvisua == 3){
                     impNota.setPrintWatermarkEnabled(false);
                        impNota.setImprimirLogoEnabled(false);
                    int impresa =  this.imprimirN(impNota, tabla, circulo.getIdCirculo(), 1, false);
                }else{
                    PrinterJob printJob = PrinterJob.getPrinterJob();
                            PageFormat pf = printJob.defaultPage();
                            Paper papel = new Paper();
                            double mWidth = LETTER_WIDTH;
                            double mHeight = LETTER_HEIGHT;

                            papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                                mHeight - (0.5 * INCH));
                            pf.setPaper(papel);
                            impNota.generate(pf);
                            this.generarVisualizacion(impNota, request, response);
                }
		
                    return impNota;
        }
        
         private Pageable imprimibleNotaDevolutivaCalificacion(EvnCalificacion evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla,int isvisua,HttpServletResponse response, HttpServletRequest request, String impresion1) throws EventoException{
            ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    if(isvisua == 0){
                impNota.setChangetextforregistrador(true);
		                 
                                }
                 ArrayList matriculasNoInscritas = new ArrayList();
                    List solicitudFolios = turno.getSolicitud().getSolicitudFolios();
			ArrayList matriculasInscritasParcialmente = new ArrayList();
			for(int i=0; i<solicitudFolios.size(); i++){
				SolicitudFolio solFolio = (SolicitudFolio) solicitudFolios.get(i);
				if(solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIALMENTE ||
						solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIAL_NO_TMP){
					matriculasInscritasParcialmente.add(solFolio.getIdMatricula());
				}else {
					matriculasNoInscritas.add(solFolio.getIdMatricula());
				}
			}
			impNota.setMatriculasInscritasParcialmente(matriculasInscritasParcialmente);
			impNota.setMatriculasNoInscritas(matriculasNoInscritas);
                    Pageable peg = null;
                    /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
                    //OPCIONES PARA COLOCAR LA FIRMA DEL REGISTRADOR
                    //PageFormat pageFormat = new PageFormat();
                    //imprimibleCertificado.generate(pageFormat);
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
                    impNota.setCargoRegistrador(cargoToPrint);
                    impNota.setNombreRegistrador(sNombre);

                    if (rutaFisica != null) {
                        BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                         byte pixeles[] = null;
                        try {
                            pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                        } catch (Throwable e1) {

                            e1.printStackTrace();
                        }
                           if(isvisua == 0){
                            impNota.setPixelesImagenFirmaRegistrador(pixeles);
                        }
                            
                            
                    }
                    if(isvisua == 0){
                             
                            int impl = 2;
                            if(impresion1 != null){
                                                if(!impresion1.equals("") && !impresion1.equals("NONE")){
                                                impresion1 = impresion1.trim();
                                                impl = Integer.parseInt(impresion1);
                                                }
                                            }
                           String impresoraSeleccionadaNuevo = "Microsoft Print to PDF";
                            if (evento.getImpresoraSeleccionada() != null) {
                                    impresoraSeleccionadaNuevo = evento.getImpresoraSeleccionada();
                            }
                            tabla.put("Impresora", impresoraSeleccionadaNuevo);
                          int impresa =  this.imprimirN(impNota, tabla, circulo.getIdCirculo(), impl, false);
               
                    }else if(isvisua == 3){
                        impNota.setPrintWatermarkEnabled(false);
                        impNota.setImprimirLogoEnabled(false);
                    int impresa =  this.imprimirN(impNota, tabla, circulo.getIdCirculo(), 1, false);
                    }else{
                        PrinterJob printJob = PrinterJob.getPrinterJob();
                            PageFormat pf = printJob.defaultPage();
                            Paper papel = new Paper();
                            double mWidth = LETTER_WIDTH;
                            double mHeight = LETTER_HEIGHT;

                            papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                                mHeight - (0.5 * INCH));
                            pf.setPaper(papel);
                            impNota.generate(pf);
                            this.generarVisualizacion(impNota, request, response);
                    }
                    
                    return impNota;
        }
         /**
	 * Imprime el objeto imprimible en la estacion asociada al circulo del turno dado
	 * con los parametros asignados.
	 * @param turno
	 * @param imprimible
	 * @param parametros
	 * @return
	 * @throws EventoException
	 */
	private int imprimirN(ImprimibleBase imprimible, Hashtable parametros, String ID, int numCopias, boolean lanzarExcepcion) throws EventoException {

		boolean impresion_ok = false;
		String mensaje_error = "";

		//CONSTANTES PARA LA IMPRESIÓN.
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";

		Bundle b = new Bundle(imprimible);
                if(isREL){
                    b.setNumeroCopias(0);		
                } else{
                    b.setNumeroCopias(numCopias);
                }

		String numIntentos = (String) parametros.get(INTENTOS);
		String espera = (String) parametros.get(ESPERA);
                String impresora = (String) parametros.get("Impresora");
		Integer intentosInt = new Integer(numIntentos);
		int intentos = intentosInt.intValue();
		Integer esperaInt = new Integer(espera);
		int esperado = esperaInt.intValue();
		int idImprimible = 0;
                try{
                    if(impresora != null){
                        b.setNombreImpresora(impresora);
                    }else{
                        b.setNombreImpresora("Microsoft Print to PDF");
                    }
                }catch(Throwable T){
                    b.setNombreImpresora("Microsoft Print to PDF");
                }
		//Ciclo que se ejecuta de acuerdo al valor recibido en intentos
		try {
			//se manda a imprimir el recibo por el identificador unico de usuario o id del circulo (caso especial CORRECCIONES)
			printJobs.enviar(ID, b, intentos, esperado);
			impresion_ok = true;
		}
		catch (PrintJobsException t) {
			idImprimible = t.getIdImpresion();
			if(idImprimible == 0){
				throw new EventoException("Se genero una excepción al imprimir la Nota. " + t.getMessage());
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
			mensaje_error = t.getMessage();
			
			if (lanzarExcepcion && !impresion_ok) {
				throw new EventoException(mensaje_error);
			}
			
		}


		return idImprimible;
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
		
		if(turno!=null){
			Solicitud solicitud = turno.getSolicitud();
			if(solicitud!=null){
				List solicitudesHijas = solicitud.getSolicitudesHijas();
				if(solicitudesHijas !=null && solicitudesHijas.size()>0){
					Iterator itSolHija = solicitudesHijas.iterator();
					while (itSolHija.hasNext()){
						solicitudAsociada = (SolicitudAsociada)itSolHija.next();
						solicitudHija = solicitudAsociada.getSolicitudHija();
						turnoHijo = solicitudHija.getTurno(); 		
						numeroCertificados = numeroCertificados + turnoHijo.getIdWorkflow() + ", ";					
					}					 
				}
			}			 
		}
		if(numeroCertificados.length()>0){
			numeroCertificados = numeroCertificados.substring(0,(numeroCertificados.length()-2));
			retorno = " Y CERTIFICADOS ASOCIADOS: " + numeroCertificados;
		}
		return retorno;
	}
    /**
     * @param imprimible
     * @param uid
     */
    private void imprimir(ImprimibleBase imprimible, String uid, int ncopias) throws EventoException {


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
        bundle.setNumeroCopias(ncopias);

        try {
            //se manda a imprimir el recibo por el identificador unico de usuario
            printJobs.enviar(uid, bundle,maxIntentos, espera);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private EventoRespuesta obtenerBloqueoFolios(EvnCalificacion evento) throws EventoException
	{
	    Turno turno=evento.getTurno();

	    TurnoPk oid = new TurnoPk();
		oid.anio=turno.getAnio();
		oid.idCirculo=turno.getIdCirculo();
		oid.idProceso=turno.getIdProceso();
		oid.idTurno=turno.getIdTurno();
		Usuario usuarioNeg=evento.getUsuarioNeg();
		
		boolean isPrimeroFirma = false;

		try{
			turno = hermod.getTurno(oid);
		}catch (Throwable e1) {
			throw new AvanzarCalificacionException("No se pudo consultar el turno",e1);
		}

		SolicitudRegistro solReg = (SolicitudRegistro)turno.getSolicitud();

		if(solReg.getLiquidaciones().size()>0){
			LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro)solReg.getLiquidaciones().get(0);
			List actos = liquidacion.getActos();
			boolean testamento = tieneTestamentos(actos);
			if(!testamento){
				try {
					// si es el primero que lo deje bloquear
					List turnos = new ArrayList();
					turnos.add((TurnoPk)oid);
					isPrimeroFirma = forseti.validarTurnoFirmaPrincipioPrioridad(turnos);
					if (isPrimeroFirma && !turno.getIdFase().equals(CFase.REG_FIRMAR)) {
						if(turno.getModoBloqueo()!=CModoBloqueo.DELEGAR_CUALQUIERA){
							turno.setModoBloqueo(CModoBloqueo.DELEGAR_CUALQUIERA);
							hermod.actualizarTurnoModoBloqueo(turno);
						}
						forseti.delegarBloqueoFolios(oid,usuarioNeg);
					}
				} catch (Throwable e1) {
					ExceptionPrinter ep=new ExceptionPrinter(e1);
					Log.getInstance().error(ANCalificacion.class,"No se pudo imprimir el formulario:"+ep.toString());
					throw new AvanzarCalificacionException("No se pudo imprimir el formulario",e1);
				}
			}
		}else{
			try {
				// si es el primero que lo deje bloquear
				List turnos = new ArrayList();
				turnos.add((TurnoPk)oid);
				isPrimeroFirma = forseti.validarTurnoFirmaPrincipioPrioridad(turnos);
				if (isPrimeroFirma  && !turno.getIdFase().equals(CFase.REG_FIRMAR)) {
					forseti.delegarBloqueoFolios(oid,usuarioNeg);
				}
				
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANCalificacion.class,"No se pudo imprimir el formulario:"+ep.toString());
				throw new AvanzarCalificacionException("No se pudo imprimir el formulario",e1);
			}
		}

		return null;
	}

	/**
	 * @param actos
	 * @return
	 */
	private boolean tieneTestamentos(List actos){
		boolean tieneTestamento = false;
		if(actos !=null){
			Iterator it = actos.iterator();
			while(it.hasNext()){
				Acto acto = (Acto)it.next();
				if(acto.getTipoActo().getIdTipoActo().equals(CActo.ID_TESTAMENTOS)||acto.getTipoActo().getIdTipoActo().equals(CActo.ID_REVOCATORIA_TESTAMENTO)){
					tieneTestamento = true;
					break;
				}
			}
		}

		return tieneTestamento;
	}



    private EventoRespuesta cerrarFolio(EvnCalificacion evento) throws EventoException
	{

	    String idMatricula = evento.getIdMatricula();
	    String razonCierre = evento.getRazonCierre();
	    Usuario usuarioNeg = evento.getUsuarioNeg();
	    FolioPk fid = new FolioPk();
	    fid.idMatricula = idMatricula;
	    Folio f= new Folio();
	    
	    Turno turno = evento.getTurno();
	    TurnoPk oid = new TurnoPk();
		oid.anio=turno.getAnio();
		oid.idCirculo=turno.getIdCirculo();
		oid.idProceso=turno.getIdProceso();
		oid.idTurno=turno.getIdTurno();


		try {
			f.setIdMatricula(fid.idMatricula);
			EstadoFolio estado = new EstadoFolio();
			estado.setIdEstado(CEstadoFolio.CERRADO);
			estado.setComentario(razonCierre);
			f.setEstado(estado);
			
			forseti.updateFolio(f, usuarioNeg, oid ,true);


		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudo cerrar el folio:"+ep.toString());
			throw new CerrarFolioException("No se pudo cerrar el folio",e1);
		}

		return new EvnRespCalificacion(null, EvnRespCalificacion.CERRAR_FOLIO);
	}
    
    
    private EventoRespuesta cerrarFolios(EvnCalificacion evento) throws EventoException
	{
    	List lstFolios = evento.getLstFolios();
	    
    	//String idMatricula = evento.getIdMatricula();
	    
    	String razonCierre = evento.getRazonCierre();
	    Usuario usuarioNeg = evento.getUsuarioNeg();
	    
	    /*FolioPk fid = new FolioPk();
	    fid.idMatricula = idMatricula;*/
	    
	    Folio f= new Folio();
	    
	    Turno turno = evento.getTurno();
	    TurnoPk oid = new TurnoPk();
		oid.anio=turno.getAnio();
		oid.idCirculo=turno.getIdCirculo();
		oid.idProceso=turno.getIdProceso();
		oid.idTurno=turno.getIdTurno();


		try {
			//f.setIdMatricula(fid.idMatricula);
			//Iterator iter = lstFolios.iterator();
			for(int i = 0; i<lstFolios.size() ;i++){
				String vect1 =(String)lstFolios.get(i);				
				f.setIdMatricula(vect1);				
				EstadoFolio estado = new EstadoFolio();
				estado.setIdEstado(CEstadoFolio.CERRADO);
				estado.setComentario(razonCierre);
				f.setEstado(estado);
				
				forseti.updateFolio(f, usuarioNeg, oid ,true);
			}


		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCalificacion.class,"No se pudo cerrar el folio:"+ep.toString());
			throw new CerrarFolioException("No se pudo cerrar el folio",e1);
		}
		
		EvnRespCalificacion evnResp = new EvnRespCalificacion(null, EvnRespCalificacion.CERRAR_FOLIO);
		evnResp.setLstFoliosCerrados(lstFolios);
		//return new EvnRespCalificacion(null, EvnRespCalificacion.CERRAR_FOLIO);
		return evnResp;
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
			Log.getInstance().error(ANCalificacion.class,e);
		}

		return buf;
	}

	public static String getNombreCompleto(String path, String nombreArchivo)
		{

		String nombreCompleto=null;

		if (!path.trim().equals(""))
		  nombreCompleto = path + nombreArchivo;
		else
		  nombreCompleto = nombreArchivo;
	  return nombreCompleto;
	}

	private boolean verificarAlertaActos(CirculoPk id, Turno turno) throws EventoException{

		List actosAVerificar = new ArrayList();

		try {
			actosAVerificar = hermod.getActosQueVencen();
		} catch (HermodException e) {
			Log.getInstance().error(ANCalificacion.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANCalificacion.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		SolicitudRegistro solicitud = (SolicitudRegistro) turno.getSolicitud();
		Iterator itLiqui = solicitud.getLiquidaciones().iterator();
		Iterator itActos = (new ArrayList()).iterator();
		while(itLiqui.hasNext()){
			LiquidacionTurnoRegistro liq = (LiquidacionTurnoRegistro) itLiqui.next();
			if(liq.getIdLiquidacion().equals(String.valueOf(solicitud.getLastIdLiquidacion()))){
				itActos = liq.getActos().iterator();
				break;
			}
		}
		boolean tieneActosVerificables = false;
		while(itActos.hasNext()){
			Acto acto = (Acto)itActos.next();
			for(int i = 0;i<actosAVerificar.size();i++){
				OPLookupCodes code = (OPLookupCodes)actosAVerificar.get(i);
				if(code.getValor().equals(acto.getTipoActo().getIdTipoActo())){
					tieneActosVerificables = true;
				}
			}
		}

		if(!tieneActosVerificables){
			return false;
		} else {
			long miliFechaDoc = solicitud.getDocumento().getFecha().getTime();
			long miliFechaSol = solicitud.getFecha().getTime();
			String strPlazo = "";
			try {
				strPlazo = hermod.getPlazoVencimientoRegistroActos();
			} catch (HermodException e) {
				Log.getInstance().error(ANCalificacion.class,e.getMessage(), e);
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				Log.getInstance().error(ANCalificacion.class,e.getMessage(), e);
				throw new EventoException(e.getMessage(), e);
			}
			int numDiasHabiles = 0;
			while(miliFechaDoc<=miliFechaSol){
				try {
					if(!forseti.isFestivo(new Date(miliFechaDoc),id)){
						numDiasHabiles++;
					}
				} catch (ForsetiException e) {
					Log.getInstance().error(ANCalificacion.class,e.getMessage(), e);
					throw new EventoException(e.getMessage(), e);
				} catch (Throwable e) {
					Log.getInstance().error(ANCalificacion.class,e.getMessage(), e);
					throw new EventoException(e.getMessage(), e);
				}
				miliFechaDoc = miliFechaDoc + (24 * 60 * 60 * 1000);
			}
			if(numDiasHabiles>Integer.parseInt(strPlazo)){
				return true;
			}

		}

		return false;
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
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta consultarSegEngExistentes(EvnCalificacion evento) throws EventoException
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

			//CAST
			sOrden=String.valueOf(orden);
		}
		EvnRespCalificacion eventoResp = new EvnRespCalificacion(rta, EvnRespCalificacion.CONSULTAR_SEG_ENG_EXISTENTES);
		eventoResp.setNextOrden(sOrden);
		return eventoResp;
	}


	/**
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private boolean isFolioCreadoCalificacion(Folio f, List foliosPadre, List foliosHijo) throws EventoException
	{
		boolean rta = false;
		
		if(f!=null && !f.isDefinitivo()){

			if(foliosPadre==null){
				foliosPadre = new ArrayList();
			}
			if(foliosHijo==null){
				foliosHijo = new ArrayList();
			}	
			
			Iterator itPadres = foliosPadre.iterator();
			if(itPadres.hasNext()){
				rta = false;
			}	
			else
			{
				rta = true;
			}
			Iterator itHijos = foliosHijo.iterator();						
			if(rta == true){
				if(itHijos.hasNext())
				{
					rta = false;
				}
				else
				{
					rta = true;
				}
				
			}
		}
		
		return rta;
	}



/**
 * @param evento
 * @return
 */
private EventoRespuesta nuevoNegarCorreccionEncabezado(EvnCalificacion evento) throws EventoException {

	Turno turno = evento.getTurno();
	Fase fase = evento.getFase();
	Hashtable tablaParametros = new Hashtable();
	Usuario u = evento.getUsuarioNeg();
	/**
	 *  Modifica Pablo Quintana Junio 17 2008
	 *  Si en el workflow hay registros de fase de testamento la respuesta es * para que la próxima
	 *   fase sea testamentos y no calificación.
	 */
    List listaHistorials = turno.getHistorials();
    boolean testamento=false;
    boolean calificacion = false;
    String estacionRevision=null;
    if (listaHistorials != null){
    	for (int hist=(listaHistorials.size()-1); hist >= 0; hist--){
    		TurnoHistoria turnoHistoriaTemp = (TurnoHistoria)listaHistorials.get(hist);
    		if (turnoHistoriaTemp != null){
    			if (turnoHistoriaTemp.getFase().equals(CFase.REG_TESTAMENTO)){
    				testamento=true;
    				estacionRevision=turnoHistoriaTemp.getIdAdministradorSAS();
    				break;
    			}else if (turnoHistoriaTemp.getFase().equals(CFase.CAL_CALIFICACION)){
    				calificacion=true;
    				estacionRevision=turnoHistoriaTemp.getIdAdministradorSAS();
    				break;
    			}
    			
    		}
    	}
    }
	if(testamento){
		tablaParametros.put(Processor.RESULT, "*");
	}else{
		tablaParametros.put(Processor.RESULT, evento.getRespuestaWf());	
	}
	//tablaParametros.put(Processor.RESULT, evento.getRespuestaWf());
	

	// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance.
	try {
		Hashtable tablaAvance = new Hashtable(2);
		tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
		tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
		hermod.validarNotaInformativaAvanceTurno(fase, tablaAvance, turno);
	} catch (Throwable t) {
		throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
	}

	/****************************************************************/
	/*                       ELIMINACION SAS                        */
	/****************************************************************/
	 Fase faseAvance = evento.getFase();    
     

      	Hashtable tablaAvanzar = new Hashtable();
      	/**
      	 * Modifica Pablo Quintana junio 17*/
      	if(testamento){
      		tablaAvanzar.put(Processor.RESULT,"*");
      	}else{
      		tablaAvanzar.put(Processor.RESULT,evento.getRespuestaWf());	
      	}
      	/** Modifica Pablo Quintana Junio 17 2008
    	 *  Se asigna la estacion que revisa testamento y lo manda a correccion encabezado para que sea devuelta
    	 *  a la misma estacion
    	 */
      	tablaAvanzar.put(Processor.SIGUIENTE_ESTACION,estacionRevision);
		try 
		{
			hermod.avanzarTurnoNuevoPop(turno,faseAvance,tablaAvanzar,u);
		
		}
        catch (Throwable exception) {
		    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
	    }

	return null;
}


/**
 * @param evento
 * @return
 */
private EventoRespuesta nuevoRealizarCorreccionEncabezado(EvnCalificacion evento) throws EventoException {

	Turno turno = evento.getTurno();
	Fase fase = evento.getFase();
	Hashtable tablaParametros = new Hashtable();
	/**
	 * @author Cesar Ramírez
	 * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
	 * Se cambia el nombre de la variable.
	 **/
	Usuario usuarioNeg = evento.getUsuarioNeg();
	
	/**
	 *  Modifica Pablo Quintana Junio 17 2008
	 *  Si en el workflow hay registros de fase de testamento la respuesta es * para que la próxima
	 *  fase sea testamentos y no calificación.
	 */
    List listaHistorials = turno.getHistorials();
    boolean testamento=false;
    String estacionRevisionTestamento=null;
    if (listaHistorials != null){
    	for (int hist=(listaHistorials.size()-1); hist >= 0; hist--){
    		TurnoHistoria turnoHistoriaTemp = (TurnoHistoria)listaHistorials.get(hist);
    		if (turnoHistoriaTemp != null){
    			if (turnoHistoriaTemp.getFase().equals(CFase.REG_TESTAMENTO)){
    				testamento=true;
    				estacionRevisionTestamento=turnoHistoriaTemp.getIdAdministradorSAS();
    				break;
    			}
                        /*
                         * @author     :   Henry Gómez Rocha
                         * @change     :   Se verifica que al momento de finiquitar el proceso de corrección
                                           encabezado desde el role usuario operativo registro este retorne
                                           a la estación y role que lo envió a dicho proceso, para nuestro caso,
                                           role calificador.
                         * Caso Mantis :   0003331
                         */
                        else if(turnoHistoriaTemp.getFase().equals(CFase.CAL_CALIFICACION)){
    				testamento=false;
    				estacionRevisionTestamento=turnoHistoriaTemp.getIdAdministradorSAS();
    				break;
    			}
    		}
    	}
    }
	if(testamento){
		tablaParametros.put(Processor.RESULT, "*");
	}else{
		tablaParametros.put(Processor.RESULT, evento.getRespuestaWf());	
	}
	
	

	// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance.
	try {
		Hashtable tablaAvance = new Hashtable(2);
		tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
		tablaAvance.put(CInfoUsuario.USUARIO_SIR, usuarioNeg.getUsername());
		hermod.validarNotaInformativaAvanceTurno(fase, tablaAvance, turno);
	} catch (Throwable t) {
		throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
	}
	
	
	SolicitudRegistro sol=evento.getSolicitud();
          /**
                * @Author Carlos Torres
                * @Mantis 13176
                * @Chaged 
                */
        co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoriaSir = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
        java.util.Map infoUsuario = new java.util.HashMap();
        if(evento.getInfoUsuario() !=null){
            infoUsuario.put("user",evento.getInfoUsuario().getUser());
            infoUsuario.put("host",evento.getInfoUsuario().getHost());
            infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
            infoUsuario.put("address",evento.getInfoUsuario().getAddress());
            infoUsuario.put("idTurno",sol.getIdSolicitud());
        }
        try {
            auditoriaSir.guardarDatosTerminal(infoUsuario);
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANCalificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
                                       
	
	try {
		/**
		 * @author Cesar Ramírez
		 * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
		 * Se envía el usuario como parámetro.
		 **/
		hermod.updateEncabezadoInSolicitud(sol, usuarioNeg);
		
	}
	catch (Throwable e1) {
		
		throw new CorreccionEncabezadoNoEfectuadoException("Error en el servicio que corrige encabezado", e1);
	}
          /**
                * @Author Carlos Torres
                * @Mantis 13176
                * @Chaged 
                */
        try {
            auditoriaSir.borrarDatosTerminal(sol.getIdSolicitud());
        } catch (GeneralSIRException ex) {
            Logger.getLogger(ANCalificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
	/****************************************************************/
	/*                       ELIMINACION SAS                        */
	/****************************************************************/
	 Fase faseAvance = evento.getFase();    
     

      	Hashtable tablaAvanzar = new Hashtable();
      	/**
      	 * Modifica Pablo Quintana junio 17*/
      	if(testamento){
      		tablaAvanzar.put(Processor.RESULT,"*");
      	}else{
      		tablaAvanzar.put(Processor.RESULT,evento.getRespuestaWf());	
      	}
      	/** Modifica Pablo Quintana Junio 17 2008
    	 *  Se asigna la estacion que revisa testamento y lo manda a correccion encabezado para que sea devuelta
    	 *  a la misma estacion
    	 */
      	if(estacionRevisionTestamento!=null)
      		tablaAvanzar.put(Processor.SIGUIENTE_ESTACION,estacionRevisionTestamento);
		
		             
		try 
		{
			/**
			 * @author Cesar Ramírez
			 * @change: 1252.REPORTE TURNOS CORRECCION ENCABEZADO
			 * Se envía el usuario como parámetro.
			 **/
			hermod.avanzarTurnoNuevoPop(turno, faseAvance, tablaAvanzar, usuarioNeg);
		
		}
        catch (Throwable exception) {
		    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
	    }
        /**
        * @author Fernando Padilla Velez
        * @change Acta - Requerimiento No 178 - Integración Gestión Documental y SIR,
        *         Se agrega esta fragmento de codigo para la publicacion del mensaje del turno creado.
        */
        PublisherIntegracion  clienteJMS = new PublisherIntegracion();
        clienteJMS.setUsuario(usuarioNeg.getUsername());
        clienteJMS.setTurno(turno);
        clienteJMS.enviar();
	return null;
}



/**
* @param evento
* @return
*/
private EventoRespuesta delegarConfrontacionNuevo(EvnCalificacion evento) throws EventoException {

		Turno turno=evento.getTurno();
		
		SolicitudRegistro solicitud = (SolicitudRegistro)turno.getSolicitud();
		List folios= solicitud.getSolicitudFolios();
		//this.metodoGeneralTurnosCorreecion(folios,turno);
		
		String motivo = evento.getMotivoDevolucion();
		Fase fase=evento.getFase();
		
		Usuario u=evento.getUsuarioNeg();
		
		String USUARIO_INICIADOR = ( null != evento.getUsuarioNeg() )?(""+evento.getUsuarioNeg().getUsername()):("");
        if( "".equals( USUARIO_INICIADOR ) || null == USUARIO_INICIADOR )
            throw new TurnoNoAvanzadoException( "El usuario no se ha regostrado en la capa web." + this.getClass().getName() );

        

		//GENERAR LA NOTA INFORMATIVA.
		//ES REQUERIDO QUE EXISTA UNA NOTA INFORMATIVA PARA ESTA FASE EN LA BD.
		ProcesoPk procesoId = new ProcesoPk();
		Long longProceso = new Long(CProceso.PROCESO_CALIFICACIONES);
		procesoId.idProceso = longProceso.longValue();
		try
		{
			boolean condicionNota=false;
			TipoNota tipoNota = null;
			List listaInformativas = hermod.getTiposNotaProceso(procesoId, CFase.CAL_CALIFICACION);
			if (listaInformativas!=null)
			{
				for (int i=0; i<listaInformativas.size(); i++)
				{
					tipoNota = (TipoNota) listaInformativas.get(i);
					if (tipoNota.getNombre().equals(CNota.NOMBRE_DELEGAR_CONFRONTACION_CORRECTIVA))
					{
						condicionNota = true;
						i = listaInformativas.size()+1;

					}

				}
				if (condicionNota)
				{
					Nota nuevaNota = new Nota();
					nuevaNota.setTiponota(tipoNota);
					nuevaNota.setDescripcion(motivo);
					nuevaNota.setUsuario(evento.getUsuarioNeg());
					nuevaNota.setIdCirculo(evento.getIdCirculo());
					Date fecha = new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(fecha);
					nuevaNota.setAnio(String.valueOf(calendar.get(Calendar.YEAR)));
					nuevaNota.setIdFase(evento.getFase().getID());
					nuevaNota.setIdProceso(longProceso.longValue());
					nuevaNota.setTime(calendar.getTime());
					nuevaNota.setTurno(evento.getTurno());

					TurnoPk turnoId = new TurnoPk();
					turnoId.anio = turno.getAnio();
					turnoId.idCirculo = turno.getIdCirculo();
					turnoId.idProceso = turno.getIdProceso();
					turnoId.idTurno = turno.getIdTurno();

					hermod.addNotaToTurno(nuevaNota,turnoId);
					turno.addNota(nuevaNota);
				}

				else throw new AvanzarCalificacionException("No se encontró tipo de nota informativa para avanzar desde la fase de calificacion",null);

			}
			else
			{
				throw new AvanzarCalificacionException("No se encontró tipo de nota informativa para avanzar desde la fase de calificacion",null);
			}

		}
		catch(Throwable t)
		{
			throw new AvanzarCalificacionException("Error avanzando turno desde la fase de calificación",t);
		}
		
		/****************************************************************/
		/*                       ELIMINACION SAS                        */
		/****************************************************************/
		 Fase faseAvance = evento.getFase();    
	     

	      	Hashtable tablaAvanzar = new Hashtable();
			tablaAvanzar.put(Processor.RESULT,evento.getRespuestaWf());
			             
			try 
			{
				hermod.avanzarTurnoNuevoPush(turno,faseAvance,tablaAvanzar,u);
			
			}
	        catch (Throwable exception) {
			    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
		    }

		return new EvnRespCalificacion(EvnRespCalificacion.CONFRONTACION);
	}


private EventoRespuesta avanzarAntiguoSistemaNuevo(EvnCalificacion evento) throws EventoException {

	//Se realiza la validacion de que no tenga informacion Temporal

	Turno turno = evento.getTurno();	
	
	TurnoPk oid = new TurnoPk();
	oid.anio = turno.getAnio();
	oid.idCirculo = turno.getIdCirculo();
	oid.idProceso = turno.getIdProceso();
	oid.idTurno = turno.getIdTurno();

		
	Usuario u = evento.getUsuarioNeg();
	

	boolean temporalTurno = false;
	String matriculasInfoTemporal = "";
	int i = 0;

	try{
		SolicitudRegistro sol= (SolicitudRegistro)turno.getSolicitud();
		List folios=sol.getSolicitudFolios();
		this.metodoGeneralTurnosCorreecion(folios,turno);
		Iterator ifol= folios.iterator();
		while(ifol.hasNext()){

			SolicitudFolio sfol=(SolicitudFolio) ifol.next();
			Folio fol = sfol.getFolio();
			FolioPk fid= new FolioPk();
			fid.idMatricula = fol.getIdMatricula();
			
			//SE OBTIENE LA INFORMACIÓN PARA DETERMINAR SI SE PUEDE O NO DESASOCIAR LAS MATRÍCULAS.
			//NO SE PUEDE CUANDO EL FOLIO TIENE COSAS TEMPORALES
			temporalTurno = false;

			temporalTurno = forseti.hasDatosTemporalesTurno(oid, fid);

			if(temporalTurno){
				if (i==0) {
					matriculasInfoTemporal = fol.getIdMatricula();
					i++;
				} else {
					matriculasInfoTemporal = matriculasInfoTemporal + ", " + fol.getIdMatricula();
					i++;
				}
			}
		}

	} catch (Throwable e1) {
		e1.printStackTrace();
		throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti",e1);
	}

	//		SI HAY FOLIOS CON DATOS TEMPORALES NO SE PUEDE AVANZAR EL TURNO
	String exc = "";
	if (i!=0) {
		if (i==1) {
			exc = "No se puede avanzar el turno; la matrícula " + matriculasInfoTemporal + " tiene modificaciones temporales.";
		} else {
			exc = "No se puede avanzar el turno; las matrículas " + matriculasInfoTemporal + " tienen modificaciones temporales.";
		}
		AvanzarSinInformacionTemporalException excepcion = new AvanzarSinInformacionTemporalException(exc);
		throw excepcion;
	}

	//Si no tiene informacion Temporal se puede avanzar sin problema
	try {
		//Se borran las notas devolutivas una vez impreso el formulario de calificacion,
		//esto se hace únicamente si el avance es de calificación.
		TurnoPk tid = new TurnoPk();
		tid.anio = turno.getAnio();
		tid.idCirculo = turno.getIdCirculo();
		tid.idProceso = turno.getIdProceso();
		tid.idTurno = turno.getIdTurno();

		/****************************************************************/
		/*                       ELIMINACION SAS                        */
		/****************************************************************/
		 Fase faseAvance = evento.getFase();    
	     

	      	Hashtable tablaAvanzar = new Hashtable();
			tablaAvanzar.put(Processor.RESULT,evento.getRespuestaWf());
			             
			try 
			{
				hermod.avanzarTurnoNuevoPush(turno,faseAvance,tablaAvanzar,u);
			
			}
	        catch (Throwable exception) {
			    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
		    }
	} catch (Throwable e1) {
		ExceptionPrinter ep = new ExceptionPrinter(e1);
		Log.getInstance().error(ANCalificacion.class,"El turno no se ha podido avanzar:" + ep.toString());
		throw new AvanzarCalificacionException("El turno no se ha podido avanzar.", e1);
	}
	return null;
}




/**
 * @param evento
 * @return
 */
private EventoRespuesta delegarDigitacionMasivaNuevo(EvnCalificacion evento) throws EventoException {

	Turno turno = evento.getTurno();
	SolicitudRegistro solicitudR = (SolicitudRegistro)turno.getSolicitud();
	List folios= solicitudR.getSolicitudFolios();
	this.metodoGeneralTurnosCorreecion(folios,turno);
	Solicitud solicitud = turno.getSolicitud();
	Fase fase = evento.getFase();
	Hashtable parametrosAvance = new Hashtable();
	
	Usuario u = evento.getUsuarioNeg();
	

	// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance.
	try {
		Hashtable tablaAvance = new Hashtable(2);
		tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
		tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
		hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, turno);
	} catch (Throwable t) {
		throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
	}

	try
	{
	//Validar si tiene anotaciones temporales
	TurnoPk oid = new TurnoPk();
	oid.anio = turno.getAnio();
	oid.idCirculo = turno.getIdCirculo();
	oid.idProceso = turno.getIdProceso();
	oid.idTurno = turno.getIdTurno();
	boolean ok=false;
	ok=forseti.validarTerminacionCalificacion(oid,CTipoRevisionCalificacion.UN_FOLIO_UNA_ANOTACION);
	if(!ok){
		throw new ForsetiException("El turno debe tener al menos una anotacion temporal en alguno de los folios");
	}

	
	//SI EL CALIFICADOR TIENE EL ROL DE DIGITADOR MASIVO, SE ASIGNA A EL MISMO EL TURNO.
	
	String estacionPrivadaUsuario = "X-"+u.getUsername();
	Rol rolDigitador = new Rol();
	rolDigitador.setRolId(CRol.ROL_DIGITADOR);

	Estacion estacionSeleccionada = null;
        /**
        * @author Daniel Forero
        * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
        */
        List estaciones = fenrir.getEstacionesUsuarioByEstadoRol(u, rolDigitador, true);
	
	if((estaciones!=null) && (!estaciones.isEmpty())){
		Estacion estacion;
		//Se busca preferiblemente la estación privada
		for(Iterator it = estaciones.iterator(); it.hasNext();){
			estacion = (Estacion)it.next();
			if(estacion.getEstacionId()!=null){
				if(estacion.getEstacionId().equals(estacionPrivadaUsuario)){
					estacionSeleccionada = estacion;
				}
			}
		}
		//Si no encontró la estación privada se asigna la primera de la lista
		if(estacionSeleccionada == null){
			estacionSeleccionada = (Estacion)estaciones.get(0);
		}
		parametrosAvance.put(Processor.ESTACION,estacionSeleccionada.getEstacionId());
		
	  }
	}catch (Throwable t) {
		t.printStackTrace();
		throw new TurnoNoAvanzadoException("No fue posible obtener una estación para asociar el turno : " + t.getMessage());
	}
	
	
	/*****************************************/
	/*        ELIMINAR SAS                   */
	/*****************************************/
	Fase faseAvance = evento.getFase();    
    
  	parametrosAvance.put(Processor.RESULT,evento.getRespuestaWf());
	             
	try 
	{
		hermod.avanzarTurnoNuevoPush(turno,faseAvance,parametrosAvance,u);
	
	}
    catch (Throwable exception) {
	    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
    }

	return null;
}




/**
 * @param evento
 * @return
 */
private EventoRespuesta devolverCalificacionNuevo(EvnCalificacion evento) throws EventoException {

	//SE OBTIENEN LOS PARAMETROS
	Turno turno=evento.getTurno();
	Solicitud solicitud = turno.getSolicitud();
	Fase fase=evento.getFase();
	Usuario u=evento.getUsuarioNeg();
	TurnoPk idTurno = new TurnoPk();
	idTurno.anio = turno.getAnio();
	idTurno.idCirculo = turno.getIdCirculo();
	idTurno.idProceso = turno.getIdProceso();
	idTurno.idTurno = turno.getIdTurno();

	//	Se valida que sea el primero para salir de calificación
	try {
		forseti.isTurnoValidoSalidaCalificacion(idTurno,u);
	} catch (ForsetiException e) {
		if(e.getHashErrores() != null) 	{
			if(!e.getHashErrores().isEmpty()) {
				throw new BloqueoFoliosHTException(e);
			}
		}		
		throw new TomarFoliosException(e.getMessage(), e);
			
	} catch (Throwable e) {
		ExceptionPrinter printer = new ExceptionPrinter(e);
		Log.getInstance().error(ANCalificacion.class,"Error en la validación de principio de prioridad : " + printer.toString());
		throw new EventoException(e.getMessage(), e);
	}
	
	// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
	//del turno desde esta fase.
	try {
			 Hashtable tablaAvance = new Hashtable(2);
			 tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
			 tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
			 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
	}
	catch(Throwable t)
	{
		  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
	}




	//SE DESHACEN LOS CAMBIOS TEMPORALES Y SE VUELVE A BLOQUEARLOS
	try {
		forseti.deshacerCambiosTurno(idTurno,u,false);
		//forseti.delegarBloqueoFolios(idTurno, u);
	} catch (Throwable e1) {
		ExceptionPrinter ep=new ExceptionPrinter(e1);
		Log.getInstance().error(ANCalificacion.class,"No se pudieron deshacer los cambios:"+ep.toString());
		throw new AvanzarCalificacionException("Se presento una excepción al deshacer los cambios temporales",e1);
	}


	//SE CREA EL HASHTABLE DE PARAMETROS
	Hashtable tabla=new Hashtable();
	tabla.put(Processor.RESULT,evento.getRespuestaWf());
	tabla.put(CInfoUsuario.USUARIO_SIR, u.getUsername());

	//AVANZAR EL TURNO BORRANDO SEGREGACIONES O ENGLOBES PENDIENTES
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

		
		/*****************************************/
		/*        ELIMINAR SAS                   */
		/*****************************************/
		Fase faseAvance = evento.getFase();    
	    Hashtable parametrosAvance = new Hashtable();
	  	parametrosAvance.put(Processor.RESULT,evento.getRespuestaWf());
		             
		try 
		{
			hermod.avanzarTurnoNuevoPush(turno,faseAvance,parametrosAvance,u);
		
		}
	    catch (Throwable exception) {
		    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
	    }
		
		
	} catch (Throwable e1) {
		ExceptionPrinter ep=new ExceptionPrinter(e1);
		Log.getInstance().error(ANCalificacion.class,"No se pudo avanzar el turno:"+ep.toString());
		throw new AvanzarCalificacionException("No se pudo avanzar el turno se necesita agregar notas informativas",e1);
	}
	return null;
}





/**
 * @param evento
 * @return
 */
private EventoRespuesta avanzarInscripcionNuevo(EvnCalificacion evento) throws EventoException{
	Turno turno = evento.getTurno();
	Solicitud solicitud = turno.getSolicitud();
	TurnoPk oid=new TurnoPk();
	oid.anio=turno.getAnio();
	oid.idCirculo=turno.getIdCirculo();
	oid.idProceso=turno.getIdProceso();
	oid.idTurno=turno.getIdTurno();
	Usuario u=evento.getUsuarioNeg();
	if(u==null){
		throw new EventoException("el usuario es nulo");
	}

	//Se valida que sea el primero para salir de calificación
	try {
		forseti.isTurnoValidoSalidaCalificacion(oid,u);
	} catch (ForsetiException e) {
		if(e.getHashErrores() != null) 	{
			if(!e.getHashErrores().isEmpty()) {
				throw new BloqueoFoliosHTException(e);
			}
		}		
		throw new TomarFoliosException(e.getMessage(), e);
			
	} catch (Throwable e) {
		ExceptionPrinter printer = new ExceptionPrinter(e);
		Log.getInstance().error(ANCalificacion.class,"Error en la validación de principio de prioridad : " + printer.toString());
		throw new EventoException(e.getMessage(), e);
	}
	
	
	boolean Anotmp;

	// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
	//del turno desde esta fase.
	try {
			 Hashtable tablaAvance = new Hashtable(2);
			 tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
			 tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
			 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
	}
	catch(Throwable t)
	{
		  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
	}

			
	try{
	    List solicitudesFoliosCalificados = null;
	    solicitudesFoliosCalificados =this.getSolicitudesFolioCalificadas(forseti, oid, u);
	    
	    /*SIR-88*/
	    Hashtable tablaAnotacionesTemporales = new Hashtable();
		try {
			tablaAnotacionesTemporales = forseti.validarNuevasAnotacionesTurno(oid);
		} catch (Throwable e2) {
			e2.printStackTrace();
		}
		
	    for(Iterator it = solicitudesFoliosCalificados.iterator(); it.hasNext();){
			SolicitudFolio solFolio = (SolicitudFolio) it.next();
			Boolean tieneAnotacionesTemporales = (Boolean)tablaAnotacionesTemporales.get(solFolio.getIdMatricula());
			if(tieneAnotacionesTemporales.booleanValue()){
				solFolio.setEstado(CSolicitudFolio.ESTADO_INSCRITO);
				solFolio.setIdSolicitud(turno.getSolicitud().getIdSolicitud());
				solFolio.setSolicitud(turno.getSolicitud());
				try {
					hermod.updateEstadoSolicitudFolio(solFolio);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					Log.getInstance().error(ANCalificacion.class, e);
				}
			}
		}
	    Collections.sort(solicitudesFoliosCalificados, new IDMatriculaFolioComparator());
	    
		turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
	}catch (Throwable e2){
		ExceptionPrinter ep=new ExceptionPrinter(e2);
		Log.getInstance().error(ANCalificacion.class,"No se pudieron desbloquear los folios:"+ep.toString());
		throw new EventoException("No se pudieron desbloquear los folios:"+e2.getMessage(),e2);
	}
	
	try
	{
		boolean ok=false;
		ok=forseti.validarTerminacionCalificacion(oid,CTipoRevisionCalificacion.UN_FOLIO_UNA_ANOTACION);
		if(!ok){
			boolean tieneActosNoRepSellos = false;
			if (turno != null && turno.getSolicitud() != null)
			{
				List liquidaciones = turno.getSolicitud().getLiquidaciones();
				if (liquidaciones != null)
				{
					Acto acto = null;
					LiquidacionTurnoRegistro liq = null;
					liqitera:
					for (Iterator liqItera = liquidaciones.iterator();
							liqItera.hasNext();)
					{
						liq = (LiquidacionTurnoRegistro)liqItera.next();
						List actos = liq.getActos();
						if (actos != null)
						{
							for (Iterator actosItera = actos.iterator();
									actosItera.hasNext();)
							{
								acto = (Acto)actosItera.next();
								if (!acto.getTipoActo().getIdTipoActo().equals(CActo.TIPO_ACTO_REPRODUCCION_SELLOS))
								{
									tieneActosNoRepSellos = true;
									break liqitera;
								}
							}
						}
					}
				}
			}
			if (tieneActosNoRepSellos)
			{
				throw new ForsetiException("El turno debe tener anotaciones temporal en los folios");
			}
		}
	} catch (Throwable th)
	{
		throw new EventoException("", th);
	}
	
	
	//IMPRESION FORMULARIO DE CALIFICACION
	//Configurar los parametros de impresión del Certificado de calificacion
	
	/****************************************************************************/
	/*                       ELIMINAR SASS                                      */
	/****************************************************************************/
	
	//VARIABLES QUE ANTERIORMENTE ERAN PASADAS AL PROGRAMA CONCURRENTE SE TRABAJAN AHORA LOCALMENTE:
	
	String circuloImprimibleNuevo = null;   //Remplaza Processor.CIRCULO
	String respuestaWFNuevo = null;         //Remplaza Processor.RESULT
	respuestaWFNuevo = evento.getRespuestaWf();
	ImprimibleFormulario imprimibleFormularioNuevo = null; //Remplaza Processor.IMPRIMIBLE
	String intentosImpresionNuevo = null;   //Remplaza Processor.INTENTOS
	String tiempoEsperaNuevo = null;        //Remplaza Processor.ESPERA
	int numeroCopiasNuevo = 1;        //Remplaza Processor.NUM_COPIAS_IMPRIMIBLE
	String impresoraSeleccionadaNuevo = null; //Remplaza Processor.IMPRESORA_SELECCIONADA
	
	
	String idCirculo = evento.getIdCirculo();
	if (idCirculo != null) {
		circuloImprimibleNuevo = idCirculo;
	}
	
	String usuarioId = evento.getUsuario().getUsuarioId();
	
	String fechaImpresion = this.getFechaImpresion();
	ImprimibleFormulario impFormulario = new ImprimibleFormulario(turno, evento.getUsuarioNeg().getUsername(), CTipoFormulario.TIPO_CALIFICACION, fechaImpresion, CTipoImprimible.FORMULARIO_CALIFICACION);
	impFormulario.setPrintWatermarkEnabled(true);
	impFormulario.setIdUsuario(Long.toString(u.getIdUsuario()));

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

			if (nombre == null)
				nombre = "";

			impFormulario.setCargoRegistrador(cargoToPrint);
			impFormulario.setNombreRegistrador(nombre);
			
//			obtener textos base de los separadores
			String tbase1 ="";
			String tbase2 = "";
			List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
			for(Iterator j= variablesImprimibles.iterator(); j.hasNext();){
				OPLookupCodes op = (OPLookupCodes) j.next();
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
					tbase1= op.getValor();
				}
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
					tbase2 = op.getValor();
				}
			}
			impFormulario.setTEXTO_BASE1(tbase1);
			impFormulario.setTEXTO_BASE2(tbase2);
			
			imprimibleFormularioNuevo = impFormulario;
		
			//INGRESO DE INTENTOS DE IMPRESION
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
			if (intentosImpresion != null) {
				intentosImpresionNuevo = intentosImpresion;
			} else {
				intentosImpresionNuevo = CImpresion.DEFAULT_INTENTOS_IMPRESION;
				
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
			if (intentosImpresion != null) {
				tiempoEsperaNuevo = esperaImpresion;
			} else {
				tiempoEsperaNuevo = CImpresion.DEFAULT_ESPERA_IMPRESION;
				
			}

			// NUMERO DE COPIAS
			numeroCopiasNuevo = evento.getNumeroCopias();

			// IMPRESORA SELECCIONADA
			if (evento.getImpresoraSeleccionada() != null) {
				impresoraSeleccionadaNuevo = evento.getImpresoraSeleccionada();
			}

		}
	
	    catch (Throwable t) {
			throw new TurnoNoAvanzadoException("Error obteniendo parámetros de configuración de impresión");
		}
		
	
	  
	  //IMPRIMIR
	   Bundle b = new Bundle(imprimibleFormularioNuevo);
	    
	   //Transformación parámetros de impresión:
	   
	   //Tiempo de espera.
	   Long longEspera = new Long (tiempoEsperaNuevo);
	   int espera = longEspera.intValue();
		 
		
	   //INTENTOS DE IMPRESION
	   Integer intIntentos = new Integer (intentosImpresionNuevo);
	   int intentos = intIntentos.intValue();
	   
	   boolean resultadoImpresion = false;
	   b.setNumeroCopias(evento.getImprimirYN());
	    
	   if(impresoraSeleccionadaNuevo!=null){
        	b.setNombreImpresora(impresoraSeleccionadaNuevo);   
        }
	    
		try
		{
			printJobs.enviar(circuloImprimibleNuevo, b, intentos, espera);
			//si imprime exitosamente sale del ciclo. 
			resultadoImpresion = true;
		} 
		catch (Throwable t) 
		{
			t.printStackTrace();
			resultadoImpresion = false;
		}
		
		
		
		
	
	  //BORRADO NOTAS DEVOLUTIVAS.
	  
	  try{  
	    
	  TurnoPk tid = new TurnoPk();
	  tid.anio = turno.getAnio();
	  tid.idCirculo = turno.getIdCirculo();
	  tid.idProceso = turno.getIdProceso();
	  tid.idTurno = turno.getIdTurno();

	  hermod.removeDevolutivasFromTurno(tid);

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

	}catch (Throwable t) {
		throw new TurnoNoAvanzadoException("Error eliminando notas deovlutivas del turno.");
	}
	
	
	
	//AVANZAR EL TURNO
	
	/*****************************************/
	/*        ELIMINAR SAS                   */
	/*****************************************/
	Fase faseAvance = evento.getFase();    
    Hashtable parametrosAvance = new Hashtable();
  	parametrosAvance.put(Processor.RESULT,evento.getRespuestaWf());
        String numCadena= String.valueOf(numeroCopiasNuevo);
        parametrosAvance.put(Processor.NCOPIAS,numCadena);
	             
	try 
	{
		hermod.avanzarTurnoNuevoPush(turno,faseAvance,parametrosAvance,u);
	
	}
    catch (Throwable exception) {
	    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
    }
	
	return null;
}







/**
 * @param evento
 * @return
 */
private EventoRespuesta inscripcionParcialNuevo(EvnCalificacion evento) throws EventoException {

	//SE OBTIENEN LOS PARAMETROS
	Turno turno=evento.getTurno();
	Solicitud solicitud = turno.getSolicitud();
	
	SolicitudRegistro solicitud1 = (SolicitudRegistro)turno.getSolicitud();
	List folios= solicitud1.getSolicitudFolios();
	this.metodoGeneralTurnosCorreecion(folios,turno);
	Fase fase=evento.getFase();
	Usuario u=evento.getUsuarioNeg();
	TurnoPk idTurno = new TurnoPk();
	idTurno.anio = turno.getAnio();
	idTurno.idCirculo = turno.getIdCirculo();
	idTurno.idProceso = turno.getIdProceso();
	idTurno.idTurno = turno.getIdTurno();

	Hashtable tabla=new Hashtable();

	List foliosInscripcionParcial = evento.getSolicitudFoliosInscripcionParcial();

	//	Se valida que sea el primero para salir de calificación
	try {
		forseti.isTurnoValidoSalidaCalificacion(idTurno,u);
	} catch (ForsetiException e) {
		if(e.getHashErrores() != null) 	{
			if(!e.getHashErrores().isEmpty()) {
				throw new BloqueoFoliosHTException(e);
			}
		}		
		throw new TomarFoliosException(e.getMessage(), e);
			
	} catch (Throwable e) {
		ExceptionPrinter printer = new ExceptionPrinter(e);
		Log.getInstance().error(ANCalificacion.class,"Error en la validación de principio de prioridad : " + printer.toString());
		throw new EventoException(e.getMessage(), e);
	}
	
	//Se valida que no hayan anotaciones inscritas
	boolean ok=false;
	try {
		ok=forseti.validarTerminacionCalificacion(idTurno,CTipoRevisionCalificacion.UN_FOLIO_UNA_ANOTACION);

		if(!ok){
			throw new ForsetiException("El turno debe tener anotaciones temporal en alguno de los folios");
		}
	}
	catch (Throwable e3) {
		throw new EventoException("",e3);
	}

	/** COLOCAR EL ESTADO A LA SOLICITUD FOLIO DE INSCRIPCION PARCIAL **/

	Hashtable tablaAnotacionesTemporales = new Hashtable();
	try {
		tablaAnotacionesTemporales = forseti.validarNuevasAnotacionesTurno(idTurno);
	} catch (Throwable e2) {
		e2.printStackTrace();
	}

	List solFolios = solicitud.getSolicitudFolios();
	boolean esta = false;

	for(Iterator it = solFolios.iterator(); it.hasNext();){
		SolicitudFolio solFolio = (SolicitudFolio) it.next();
		esta = false;
		for(Iterator it2 = foliosInscripcionParcial.iterator(); it2.hasNext() && !esta;){
			SolicitudFolio solTemp = (SolicitudFolio) it2.next();
			if(solTemp.getIdMatricula().equals(solFolio.getIdMatricula())){
				esta = true;
			}
		}
		if(esta){
			FolioPk idFolio = new FolioPk();
			idFolio.idMatricula = solFolio.getIdMatricula();
			
			Boolean tieneAnotacionesTemporales = (Boolean)tablaAnotacionesTemporales.get(solFolio.getIdMatricula());
			if(tieneAnotacionesTemporales.booleanValue()){
				solFolio.setEstado(CSolicitudFolio.ESTADO_INSCRITO_PARCIALMENTE);
				try {
					hermod.updateEstadoSolicitudFolio(solFolio);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					Log.getInstance().error(ANCalificacion.class, e);
				}
			}else{
				solFolio.setEstado(CSolicitudFolio.ESTADO_INSCRITO_PARCIAL_NO_TMP);
				try {
					hermod.updateEstadoSolicitudFolio(solFolio);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					Log.getInstance().error(ANCalificacion.class, e);
				}
			}
		}else{
			//Si el folio no esta seleccionado es necesario ver si tiene anotaciones temporales
			Boolean tieneAnotacionesTemporales = (Boolean)tablaAnotacionesTemporales.get(solFolio.getIdMatricula());
			if(tieneAnotacionesTemporales.booleanValue()){
				//Modificar las solicitud del folio.
				solFolio.setEstado(CSolicitudFolio.ESTADO_INSCRITO);
				try {
					hermod.updateEstadoSolicitudFolio(solFolio);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					Log.getInstance().error(ANCalificacion.class,e);
				}
			}else{
				//Modificar las solicitud del folio.
				solFolio.setEstado(CSolicitudFolio.ESTADO_DEVUELTO);
				try {
					hermod.updateEstadoSolicitudFolio(solFolio);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					Log.getInstance().error(ANCalificacion.class,e);
				}
			}

		}
	}



	/** VALIDAR QUE SE NECESITE NOTA INFORMATIVA PARA AVANZAR EL TURNO **/
	try {
			 Hashtable tablaAvance = new Hashtable(2);
			 tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
			 tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
        		 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
	}
	catch(Throwable t)
	{
		  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
	}


	/** GENERAR EL IMPRIMIBLE DE CALIFICACION **/

	//Obtener las solciitudes folios calificadas
	try{
	    List solicitudesFoliosCalificados = null;
	    solicitudesFoliosCalificados =this.getSolicitudesFolioCalificadas(forseti, idTurno, u);
	    Collections.sort(solicitudesFoliosCalificados, new IDMatriculaFolioComparator());
		turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
	}catch (Throwable e2){
		ExceptionPrinter ep=new ExceptionPrinter(e2);
		Log.getInstance().error(ANCalificacion.class,"No se pudieron desbloquear los folios:"+ep.toString());
		throw new EventoException("No se pudieron desbloquear los folios:"+e2.getMessage(),e2);
	}


	//IMPRESION FORMULARIO DE CALIFICACION
	//Configurar los parametros de impresión del Certificado de calificacion
	
	/****************************************************************************/
	/*                       ELIMINAR SASS                                      */
	/****************************************************************************/
	
	//VARIABLES QUE ANTERIORMENTE ERAN PASADAS AL PROGRAMA CONCURRENTE SE TRABAJAN AHORA LOCALMENTE:
	
	String circuloImprimibleNuevo = null;   //Remplaza Processor.CIRCULO
	String respuestaWFNuevo = null;         //Remplaza Processor.RESULT
	respuestaWFNuevo = evento.getRespuestaWf();
	ImprimibleFormulario imprimibleFormularioNuevo = null; //Remplaza Processor.IMPRIMIBLE
	String intentosImpresionNuevo = null;   //Remplaza Processor.INTENTOS
	String tiempoEsperaNuevo = null;        //Remplaza Processor.ESPERA
	int numeroCopiasNuevo = 1;        //Remplaza Processor.NUM_COPIAS_IMPRIMIBLE
	String impresoraSeleccionadaNuevo = null; //Remplaza Processor.IMPRESORA_SELECCIONADA
	
	
	String idCirculo = evento.getIdCirculo();
	if (idCirculo != null) {
		circuloImprimibleNuevo = idCirculo;
	}
	
	String usuarioId = evento.getUsuario().getUsuarioId();
	
	String fechaImpresion = this.getFechaImpresion();
	ImprimibleFormulario impFormulario = new ImprimibleFormulario(turno, evento.getUsuarioNeg().getUsername(), CTipoFormulario.TIPO_CALIFICACION, fechaImpresion, CTipoImprimible.FORMULARIO_CALIFICACION);
	impFormulario.setPrintWatermarkEnabled(true);
        impFormulario.setIdUsuario(Long.toString(evento.getUsuarioNeg().getIdUsuario()));
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

			if (nombre == null)
				nombre = "";

			impFormulario.setCargoRegistrador(cargoToPrint);
			impFormulario.setNombreRegistrador(nombre);


			imprimibleFormularioNuevo = impFormulario;
		
			//INGRESO DE INTENTOS DE IMPRESION
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
			if (intentosImpresion != null) {
				intentosImpresionNuevo = intentosImpresion;
			} else {
				intentosImpresionNuevo = CImpresion.DEFAULT_INTENTOS_IMPRESION;
				
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_CALIFICACION);
			if (intentosImpresion != null) {
				tiempoEsperaNuevo = esperaImpresion;
			} else {
				tiempoEsperaNuevo = CImpresion.DEFAULT_ESPERA_IMPRESION;
				
			}

			// NUMERO DE COPIAS
			numeroCopiasNuevo = evento.getImprimirYN();

			// IMPRESORA SELECCIONADA
			if (evento.getImpresoraSeleccionada() != null) {
				impresoraSeleccionadaNuevo = evento.getImpresoraSeleccionada();
			}

		}
	
	    catch (Throwable t) {
			throw new TurnoNoAvanzadoException("Error obteniendo parámetros de configuración de impresión");
		}
		
	
	  
	  //IMPRIMIR
	   Bundle b = new Bundle(imprimibleFormularioNuevo);
	    
	   //Transformación parámetros de impresión:
	   
	   //Tiempo de espera.
	   Long longEspera = new Long (tiempoEsperaNuevo);
	   int espera = longEspera.intValue();
		 
		
	   //INTENTOS DE IMPRESION
	   Integer intIntentos = new Integer (intentosImpresionNuevo);
	   int intentos = intIntentos.intValue();
	   
	   boolean resultadoImpresion = false;
	   b.setNumeroCopias(numeroCopiasNuevo);
	    
	   if(impresoraSeleccionadaNuevo!=null){
        	b.setNombreImpresora(impresoraSeleccionadaNuevo);   
        }
	    
		try
		{
			printJobs.enviar(circuloImprimibleNuevo, b, intentos, espera);
			//si imprime exitosamente sale del ciclo. 
			resultadoImpresion = true;
		} 
		catch (Throwable t) 
		{
			t.printStackTrace();
			resultadoImpresion = false;
		}
		
		
       //		AVANZAR EL TURNO
		
		/*****************************************/
		/*        ELIMINAR SAS                   */
		/*****************************************/
		Fase faseAvance = evento.getFase();    
	    Hashtable parametrosAvance = new Hashtable();
	  	parametrosAvance.put(Processor.RESULT,evento.getRespuestaWf());
		parametrosAvance.put(Processor.NCOPIAS, String.valueOf(evento.getNumeroCopias()));
	
		try 
		{
			hermod.avanzarTurnoNuevoPush(turno,faseAvance,parametrosAvance,u);
		
		}
	    catch (Throwable exception) {
		    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
	    }
		
	return null;
}






private EventoRespuesta aprobarMayorValorNuevo(EvnCalificacion evento) throws EventoException
{
    Turno turno=evento.getTurno();
    Solicitud solicitud = turno.getSolicitud();
	SolicitudPk sid = new SolicitudPk();
	sid.idSolicitud = solicitud.getIdSolicitud();
	Usuario u=evento.getUsuarioNeg();

    LiquidacionTurnoRegistro liq= evento.getLiquidacion();
    Fase fase= evento.getFase();

	
	try {

		TurnoPk oid = new TurnoPk();
		oid.anio = turno.getAnio();
		oid.idCirculo = turno.getIdCirculo();
		oid.idProceso = turno.getIdProceso();
		oid.idTurno = turno.getIdTurno();

		//Se valida que no hayan anotaciones inscritas
		boolean ok=false;
		ok=forseti.validarTerminacionCalificacion(oid,CTipoRevisionCalificacion.UN_FOLIO_UNA_ANOTACION);
		if(ok){
			throw new ForsetiException("El turno no debe tener anotaciones temporal en alguno de los folios");
		}

		//SE VALIDA QUE NO EXISTAN ENGLOBES O SEGREGACIONES TEMPORALES.
		String exc = "";
		List segEngTemporales = null;
		segEngTemporales =  hermod.getWebSegEngBySolicitud(sid);
		if(segEngTemporales!=null && segEngTemporales.size()>0){
			exc = "No se puede avanzar el turno; existen englobes o segregaciones temporales. Debe eliminarlas si desea enviar el turno a otras dependencias.";
			TurnoNoAvanzadoException excepcion = new TurnoNoAvanzadoException(exc);
			throw excepcion;
		}
		
		// Se eliminan las notas Devolutivas
		hermod.removeDevolutivasFromTurno(oid);
		
		//Se valida que no hayan notas devolutivas.
		turno = hermod.getTurno(oid);
		List notas = turno.getNotas();
		for(Iterator it= notas.iterator(); it.hasNext();){
			Nota nota = (Nota) it.next();
			TipoNota tipo= nota.getTiponota();
			if(tipo.isDevolutiva()){
				throw new HermodException("El turno no puede tener notas devolutivas");
			}
		}
	} catch(TurnoNoAvanzadoException t){
		throw t;
		}catch(ForsetiException t){
		  throw new TurnoNoAvanzadoException("No es posible avanzar el turno: " + t.getMessage());
	}catch(HermodException t){
		  throw new TurnoNoAvanzadoException("No es posible avanzar el turno: " + t.getMessage());
	}catch(Throwable t) {
		  throw new TurnoNoAvanzadoException("No es posible avanzar el turno");
	}




	// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
	//del turno desde esta fase.
	try {
			 Hashtable tablaAvance = new Hashtable(2);
			 tablaAvance.put(Processor.RESULT, CRespuesta.PAGO_MAYOR_VALOR);
			 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
			 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
	}
	catch(Throwable t)
	{
		  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
	}

	/*****************************************/
	/*        ELIMINAR SASS                  */
	/*****************************************/
	

	try
	{
		SolicitudRegistro sol= (SolicitudRegistro)turno.getSolicitud();
		//se añade la liquidacion al turno
	    hermod.addLiquidacionToSolicitud(sol, liq);
                            /**
             * @author : Ricardo Chivata
             * @change : Tarifas - varible porcentual del recibo.
             */
            ActoEnhanced actoTmp = new ActoEnhanced();
            actoTmp.setValor(liq.getValorDerechos());
            TipoActoEnhanced tipoActoTmp = new TipoActoEnhanced();
            actoTmp.setIdSolicitud(sol.getIdSolicitud());
            tipoActoTmp.setIdTipoActo(CRespuesta.MAYOR_VALOR_ID);
            actoTmp.setTipoActo(tipoActoTmp);
            JDOLiquidacionesDAO liquidaJDO = new JDOLiquidacionesDAO();
            liquidaJDO.InsertarConservacion(actoTmp, "1","0",0);
            liquidaJDO.AsociarTurnoSolicitudConservacion(sol.getIdSolicitud());
	}catch(Throwable t)
	{
		  throw new TurnoNoAvanzadoException("Error añadiendo liquidación de mayor valor al turno.");
	}
	
	
	Fase faseAvance = evento.getFase();    
    Hashtable parametrosAvance = new Hashtable();
  	parametrosAvance.put(Processor.RESULT,CRespuesta.PAGO_MAYOR_VALOR);
	             
	try 
	{
		hermod.avanzarTurnoNuevoPush(turno,faseAvance,parametrosAvance,u);
	
	}
    catch (Throwable exception) {
	    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
    }
       
        if(fase.getID().equals("REG_FIRMAR")){
        try{
            Boolean turnoREL = hermod.isTurnoREL(turno.getIdWorkflow());
            if(turnoREL){
                TLSHttpClientComponent callerREL = new TLSHttpClientComponent();
                String phase = hermod.getStringByQuery(CQueries.getRespFromCalificacion(turno.getIdWorkflow()));
                callerREL.setFase(CRespuesta.getRespuestaREL(phase));
                callerREL.sendMsgToREL(turno.getIdWorkflow());
            }
        } catch(Exception e){
            Logger.getLogger(ANCalificacion.class.getName()).log(Level.SEVERE, null, e);
             throw new EventoException("El turno " + turno.getIdWorkflow() + " no pudo ser reportado hacia REL ");
        } catch(Throwable th){
             throw new EventoException("El turno " + turno.getIdWorkflow() + " no pudo ser reportado hacia REL ");
        }
        }
	return new EvnRespCalificacion(turno, EvnRespCalificacion.APROBAR_MAYOR_VALOR);
}


/**
	 * @param evento
	 * @return
	 */
    private EvnRespConfrontacion avanzarRevisor(EvnCalificacion evento) throws EventoException {
        //OBTENER PARAMETROS DEL EVENTO
      
        Turno turno = evento.getTurno();
        
        try{
                String revisionConfrontacionCorrectiva = CFase.REV_REVISION_CONFRONTACION;

                Fase faseAvance = evento.getFase();
                gov.sir.core.negocio.modelo.Usuario usuarioSIRAvance = evento.getUsuarioSir();
                String estacion = hermod.getEstacionFromRevisor(turno);
                if(!hermod.isEstacionActivaCalificador(estacion)){
                    estacion = null;
                }
                Hashtable tablaAvanzar = new Hashtable();
                tablaAvanzar.put(Processor.RESULT, evento.getRespuestaWf());
                if(evento.getRespuestaWf().equals(CRespuesta.EXITO) && estacion != null && !estacion.isEmpty()){
                    tablaAvanzar.put(Processor.ESTACION, estacion);
                }
                try {
                    hermod.avanzarTurnoNuevoNormal(turno, faseAvance, tablaAvanzar, usuarioSIRAvance);
                } catch (Throwable e) {
                    throw new EventoException("Error avanzando el turno en el servicio hermod.", e);
                }
            
        } catch (Throwable e1) {
             throw new TurnoNoAvanzadoException("No es posible avanzar el turno");
        }
        return null;
    }

/**
 * @param evento
 * @return
 */
private EventoRespuesta avanzarFirmaNuevo(EvnCalificacion evento) throws EventoException{
    
        Turno turno = evento.getTurno();
	Fase fase = evento.getFase();
	TurnoPk oid=new TurnoPk();
	oid.anio=turno.getAnio();
	oid.idCirculo=turno.getIdCirculo();
	oid.idProceso=turno.getIdProceso();
	oid.idTurno=turno.getIdTurno();
	Usuario u=evento.getUsuarioNeg();
	boolean Anotmp;
	
	Turno turnoAux = null;
	try{
		turno = hermod.getTurno(oid);
	}catch(Throwable t)
	{
		  throw new TurnoNoAvanzadoException(t.getMessage());
	}
	
	if(!fase.getID().equals(turno.getIdFase()) && fase.getID().equals(CFase.REG_FIRMAR)){
		throw new TurnoNoAvanzadoException ("El turno ya avanzo a la siguiente fase ya sea por relacion o por otro usuario");
	}
	
	if(turnoAux!=null){
		List solFolios = turnoAux.getSolicitud().getSolicitudFolios();
		this.metodoGeneralTurnosCorreecion(solFolios,turno);
	}

	// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
	//del turno desde esta fase.
	try {
			 Hashtable tablaAvance = new Hashtable(2);
			 tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
			 tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
			 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
	}
	catch(Throwable t)
	{
		  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
	}
        

  	try {
		List turnos = new ArrayList();
		turnos.add(oid);
		forseti.validarPrincipioPrioridadFirma(turnos);
	} catch (ForsetiException e1) {
		ExceptionPrinter ep=new ExceptionPrinter(e1);
                if(e1 != null && e1.getHashErrores() != null){
                    throw new TurnoNoAvanzadoException((String)e1.getHashErrores().get(turno.getIdWorkflow()));
                }
		throw new ValidacionParametrosHTException(e1);
	} catch (Throwable e2){
		ExceptionPrinter ep=new ExceptionPrinter(e2);
		throw new EventoException("No se pudieron desbloquear los folios:"+e2.getMessage(),e2);
	}

	SolicitudRegistro solReg = (SolicitudRegistro)turno.getSolicitud();

	if(solReg.getLiquidaciones().size()>0){
		LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro)solReg.getLiquidaciones().get(0);
		List actos = liquidacion.getActos();
		boolean testamento = tieneTestamentos(actos);

		if(!testamento){
                        /**
                     * @Autor: Fernando Padilla velez
                     * @Change: 2099.VALIDACION.AVANCE.TURNOS.FIRMAR.REGISTRO Se
                     * valida que existan datos temporales para el turno.
                     */
                    ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                    try {
                        if(!validacionesSIR.isActoInTurno(solReg.getIdSolicitud(), "12")){
                            if (turno.getSolicitud() != null && turno.getSolicitud() instanceof SolicitudRegistro) {
                                solReg = (SolicitudRegistro) turno.getSolicitud();
                                if ((CRespuestaCalificacion.INSCRITO.equals(solReg.getClasificacionRegistro())
                                        || "INSCRITO PARCIALMENTE".equals(solReg.getClasificacionRegistro())
                                        || "REGISTRO PARCIAL".equals(solReg.getClasificacionRegistro()))
                                        && !validacionesSIR.isTurnoMatriculasTemporalesByFirmaRegistro(turno.getIdWorkflow())) {
                                    throw new TurnoNoAvanzadoException("El turno debe tener anotaciones temporales en los folios.");
                                }
                            }
                        }
                    } catch (GeneralSIRException ex) {
                        throw new TurnoNoAvanzadoException("El turno debe tener anotaciones temporales en los folios.");
                    }
			//SolicitudRegistro solicitud = (SolicitudRegistro)turno.getSolicitud();
			SolicitudRegistro solicitud = null;
			try {
				solicitud = (SolicitudRegistro)hermod.getSolicitudById(turno.getSolicitud().getIdSolicitud());
			}catch (Throwable e3) {
				Log.getInstance().error(ANCalificacion.class,e3.getMessage(), e3);
				throw new EventoException("No se pudo recuperar los folios asociados al turno: "+e3.getMessage(), e3);
			}
			
			
			List folios= solicitud.getSolicitudFolios();
			List matriculas=new ArrayList();
			Iterator it=folios.iterator();
			
			TurnoPk turnoID=new TurnoPk();
			turnoID.anio=turno.getAnio();
			turnoID.idCirculo=turno.getIdCirculo();
			turnoID.idProceso=turno.getIdProceso();
			turnoID.idTurno=turno.getIdTurno();
			
			List turnos = new ArrayList();
			turnos.add(turnoID);

			try{
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
                                    infoUsuario.put("idTurno", turno.getIdWorkflow());
                                }
				co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR audSir = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                                audSir.guardarDatosTerminal(infoUsuario);				
				boolean isPrimeroFirma = forseti.validarTurnoFirmaPrincipioPrioridad(turnos);
				if (isPrimeroFirma) {
					forseti.delegarBloqueoFolios(turnoID,u);
				}
			    List solicitudesFoliosCalificados = null;
			    solicitudesFoliosCalificados =this.getSolicitudesFolioCalificadas(forseti, oid, u);
				turno.getSolicitud().setSolicitudFolios(solicitudesFoliosCalificados);
				Iterator itf= folios.iterator();
				for(;itf.hasNext();){
					SolicitudFolio sf=(SolicitudFolio) itf.next();
					Folio f=sf.getFolio();
					forseti.hacerDefinitivoFolio(f, u, oid, true);
				}
                                 /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                audSir.borrarDatosTerminal(turno.getIdWorkflow());
			} catch (ForsetiException e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANCalificacion.class,"No se pudieron hacer definitivos los folios:"+ep.toString());
				throw new EventoException("No se pudieron hacer definitivos los folios:"+e1.getMessage(),e1);
			}catch (Throwable e2){
				ExceptionPrinter ep=new ExceptionPrinter(e2);
				Log.getInstance().error(ANCalificacion.class,"No se pudieron desbloquear los folios:"+ep.toString());
				throw new EventoException("No se pudieron desbloquear los folios:"+e2.getMessage(),e2);
			}
		}
	}

	/*****************************************/
	/*        ELIMINAR SASS                  */
	/*****************************************/
	
	
	Fase faseAvance = evento.getFase();    
    Hashtable parametrosAvance = new Hashtable();
    
        if(evento.getRespuestaWf().equals(CRespuesta.NOTA_DEVOLUTIVA)){
            parametrosAvance.put(Processor.RESULT,CRespuesta.NOTA_DEVOLUTIVA);
        } else{
            parametrosAvance.put(Processor.RESULT,CRespuesta.CONFIRMAR);
        }
  	
	             
	try 
	{
		
		hermod.avanzarTurnoNuevoNormal(turno,faseAvance,parametrosAvance,u);
	
	}
    catch (Throwable exception) {
	    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
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
        // identificado, sigue ocurriendo cuando el turno avanza de la fase FIRMA.
        if(foliosLiberados > 0){
            // Imprimier el turno y el número de folios liberados para llevar un control del error.
            Log.getInstance().info(ANCalificacion.class, "REQ-1159: "
                    + "Folios liberados => " + foliosLiberados +", "
                    + "Turno => " + turno.getIdWorkflow());
        }
    } catch (HermodException e) {
        ExceptionPrinter printer = new ExceptionPrinter(e);
        Log.getInstance().error(ANCalificacion.class, "Ha ocurrido un error inesperado durante la liberación de los folios del turno "
                + turno.getIdWorkflow() + ": "
                + printer.toString());
    }
    List Reproduccion = null;
    Reproduccion = this.ObtenerReproduccionSellos(evento);
    if(Reproduccion == null){
        Reproduccion = new ArrayList();
    }
    if(Reproduccion.isEmpty()){
        if(evento.getFase().getID().equals(CFase.REG_FIRMAR)){
        try {
            evento.setUID(turno.getIdCirculo());
            int resp = evento.getIsFirma();
            int saber = 4;
            try{
            Testamento testamento = hermod.getTestamentoByID(evento.getTurno().getSolicitud().getIdSolicitud());
            if(testamento != null){
                HttpServletResponse res = null;
                HttpServletRequest req = null;
                this.imprimirTestamento(evento,testamento,0, res,req);
                saber = 0;
            }    
            }catch (Throwable ex) {
             
            }  
            
            
            if(saber != 0){
                if(evento.getTurno().getNotas() != null){
                if(evento.getTurno().getNotas().size() > 0){
                    List notas = evento.getTurno().getNotas();
                    Nota captura = null;
                    for(int i = 0 ; i < notas.size() ; i++){
                        Nota n = (Nota) notas.get(i);
                        if(n.getTiponota().isDevolutiva()){
                            captura = n;
                            saber=1;
                        }
                    }
                     if(saber == 1){
                         HttpServletResponse res = null;
                        HttpServletRequest req = null;
                         try{
                                           SolicitudRegistro solReg1 = (SolicitudRegistro) turno.getSolicitud();
                                           SolicitudRegistro finalS =(SolicitudRegistro) hermod.getSolicitudById(solReg1.getIdSolicitud());
                                                RelacionPk rel = new RelacionPk();
                                                rel.idFase = turno.getIdFaseRelacionActual();
                                                rel.idRelacion = turno.getIdRelacionActual();
                                                Relacion relacion = hermod.getRelacion(rel);
                                                TipoRelacion tipoRelacion = relacion.getTipoRelacion();
                                                if(tipoRelacion.getIdTipoRelacion().equals(CTipoRelacion.ID_DOCUMENTOS_CON_INSCRIPCION_PARCIAL_PARA_FIRMA)){
                                                    this.imprimirFormularioCalificacionReg(evento,resp,0,res,req);
                                                    this.imprimirNotaDevolutiva(evento,captura,0,res,req,null);
                                                 }else{
                                                    if("INSCRITO PARCIALMENTE".equals(finalS.getClasificacionRegistro()) || "REGISTRO PARCIAL".equals(finalS.getClasificacionRegistro())){
                                                   this.imprimirFormularioCalificacionReg(evento,resp,0,res,req);
                                                        this.imprimirNotaDevolutiva(evento,captura,0,res,req,null); 
                                                }else{
                                                    this.imprimirNotaDevolutiva(evento,captura,0,res,req,null);   
                                                } 
                                              }
                                       
                                       }catch(Throwable ex){
                                           this.imprimirNotaDevolutiva(evento,captura,0,res,req,null);   
                                       } 
                     }
                }
            }      
                if(saber == 4 ){
                    HttpServletResponse res = null;
                        HttpServletRequest req = null;
                 this.imprimirFormularioCalificacionReg(evento,resp,0,res,req);   
                }
            }
            
            gov.sir.core.util.TLSHttpClientComponent notifyREL = new gov.sir.core.util.TLSHttpClientComponent();
            if(fase.getID().equals("REG_FIRMAR")){
                try{
            boolean turnoREL = (hermod.isTurnoREL(turno.getIdWorkflow()));
            if(turnoREL){
                String phase = hermod.getStringByQuery(CQueries.getRespFromCalificacion(turno.getIdWorkflow()));
                notifyREL.setFase(CRespuesta.getRespuestaREL(phase));
                notifyREL.sendMsgToREL(turno.getIdWorkflow());
                    
            }
        } catch(Exception e){
            Logger.getLogger(ANCalificacion.class.getName()).log(Level.SEVERE, null, e);
             throw new EventoException("El turno " + turno.getIdWorkflow() + " no pudo ser reportado hacia REL ");
        } catch(Throwable th){
             throw new EventoException("El turno " + turno.getIdWorkflow() + " no pudo ser reportado hacia REL ");
        }

            }
            //this.imprimirCertificado(turno, u, turno.getIdCirculo(), evento.getImpresoraSeleccionada());
            } catch (Throwable ex) {
                        throw new EventoException ("Error imprimiento el Documento.");
            }  
        }
    }else{
         for(int i = 0 ; i < Reproduccion.size() ; i++){
            ReproduccionSellos n = (ReproduccionSellos) Reproduccion.get(i);
            if(n.getOpcion() == 1){
                EvnCalificacion e = new EvnCalificacion(evento.getUsuario(), EvnCalificacion.AVANZAR_FIRMA);
                Turno t = this.GetTurnoWK(n.getCode());
                e.setTurno(t);
                e.setNumeroImpresiones(Integer.valueOf(n.getNcopiasSello()));
                e.setUsuarioNeg(evento.getUsuarioNeg());
                this.VisualizarpdfRST(e,null,null,turno,n.getUsuariosir(),0);
            }else{
                EvnCalificacion e = new EvnCalificacion(evento.getUsuario(), EvnCalificacion.AVANZAR_FIRMA);
                e.setNumeroImpresiones(Integer.valueOf(n.getNcopiasSello()));
                e.setUsuarioNeg(evento.getUsuarioNeg());
                e.setTurno(turno);
                this.VisualizarpdfRSM(e,null,null,n.getCode(),n.getUsuariosir(),String.valueOf(n.getDesde()),String.valueOf(n.getHasta()),0);
            }
        }   
    }
    
    return null;
}
/**
	 * Accion que imprime una constancia de testamentos.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private Pageable imprimirTestamento(EvnCalificacion evento, Testamento test, int isvisua, HttpServletResponse response, HttpServletRequest request) throws EventoException {
                Pageable peg = null;
		Hashtable tabla = new Hashtable();
		Turno turno = evento.getTurno();
		Circulo circulo = null;
                CirculoPk cid = new CirculoPk();
                cid.idCirculo = turno.getIdCirculo();
                try {
                    circulo = forseti.getCirculo(cid);
                } catch (Throwable ex) {
                    circulo = evento.getSolicitud().getCirculo();
                }
                List historia = turno.getHistorials();
                int i1 = 0;
                String impresion = "";
                Usuario usuario = null;
                TurnoHistoria Histo = null;
                while (i1 < historia.size()) {
                    TurnoHistoria historial = (TurnoHistoria) historia.get(i1);
                    if (historial.getFase().equals(CFase.REG_TESTAMENTO)) {
                        if (i1 != historia.size() - 1) {
                            usuario = historial.getUsuarioAtiende();
                            impresion = historial.getNcopias();
                            Histo = historial;
                        }
                    }
                    i1++;
                }
                int saber = 0;
                 if(evento.getTurno().getNotas().size() > 0){
                    List notas = evento.getTurno().getNotas();
                    Nota captura = null;
                    for(int i = 0 ; i < notas.size() ; i++){
                        Nota n = (Nota) notas.get(i);
                        if(n.getTiponota().isDevolutiva()){
                            captura = n;
                            saber=1;
                        }
                    }
                    if(saber == 1){
                        if(isvisua ==1){
                          this.imprimirNotaDevolutiva(evento,captura,1,response,request,Histo);   
                          return peg;
                        }else{
                            this.imprimirNotaDevolutiva(evento,captura,0,response,request,Histo);          
                            return peg;
                      }
                    }
                 }
                 
		String idWorkflow = null;
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";
		
		int maxIntentos;
		int espera;
		
		SolicitudRegistro solicitud =  (SolicitudRegistro)turno.getSolicitud();  

		
		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS. 

		//INGRESO DE INTENTOS DE IMPRESION
		try
		{
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
			if (intentosImpresion != null)
			{
				 tabla.put(INTENTOS,intentosImpresion);
			}
			else
			{
				tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			}
			
			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null)
			{
				tabla.put(ESPERA,esperaImpresion);
			}
			else
			{
				tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);
			}
		}
		catch (Throwable t)
		{
			
			tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);
			
		}
		
		String fechaImpresion = this.getFechaImpresion();
		Date fechaRadicacion = null;
		if(solicitud.getFecha()!=null){
			fechaRadicacion = solicitud.getFecha(); 
		}
               
		ImprimibleInscripcionTestamento impTestamento = new ImprimibleInscripcionTestamento(test, solicitud.getDocumento(), turno.getIdWorkflow() , circulo.getNombre() , fechaImpresion , usuario , fechaRadicacion , CTipoImprimible.INSCRIPCION_TESTAMENTOS );
		if(isvisua == 0){
                impTestamento.setChangetextforregistrador(true);
		                 
                                }
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
                    if(isvisua == 0) {
                        impTestamento.setImpFirma(true);
                        impTestamento.setPixelesImagenFirmaRegistrador(pixeles);
                    }
                    
                }
		
		if (nombre==null){
                    
		  nombre="";
                }
		
		
		impTestamento.setCargoRegistrador(cargoToPrint);  
		impTestamento.setNombreRegistrador(nombre);
	
		int idImprimible =0;
                if(isvisua == 0) {
                    int impl = 1;
                    if(impresion != null){
                    if(!impresion.equals("") && !impresion.equals("NONE")){
                    impresion = impresion.trim();
                    impl = Integer.parseInt(impresion);
                    }
                }
                     String impresoraSeleccionadaNuevo = "";
                    if (evento.getImpresoraSeleccionada() != null) {
                            impresoraSeleccionadaNuevo = evento.getImpresoraSeleccionada();
                    }
                    tabla.put("Impresora", impresoraSeleccionadaNuevo);
                   idImprimible =  this.imprimir(impTestamento, tabla, circulo.getIdCirculo(), impl, false);
                  
                 
		}else{
                    PrinterJob printJob = PrinterJob.getPrinterJob();
                    PageFormat pf = printJob.defaultPage();
                    Paper papel = new Paper();
                    double mWidth = LETTER_WIDTH;
                    double mHeight = LETTER_HEIGHT;

                    papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                        mHeight - (0.5 * INCH));
                    pf.setPaper(papel);
                    impTestamento.generate(pf);
                    this.generarVisualizacion(impTestamento, request, response);
                }
                
               return peg;
	}
/**
 * @param evento
 * @return
 */
private EventoRespuesta devolverFirmaNuevo(EvnCalificacion evento) throws EventoException{
	Turno turno = evento.getTurno();
	TurnoPk oid=new TurnoPk();
	oid.anio=turno.getAnio();
	oid.idCirculo=turno.getIdCirculo();
	oid.idProceso=turno.getIdProceso();
	oid.idTurno=turno.getIdTurno();
	Usuario u=evento.getUsuarioNeg();
	boolean Anotmp;

	 try {
			List turnos = new ArrayList();
			turnos.add(oid);
			forseti.validarPrincipioPrioridadDevolucion(turnos);
		} catch (ForsetiException e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			throw new ValidacionParametrosHTException(e1);
		} catch (Throwable e2){
			ExceptionPrinter ep=new ExceptionPrinter(e2);
			throw new EventoException("No se pudieron obtener validacion de Devolución:"+e2.getMessage(),e2);
		}
		

	// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
	//del turno desde esta fase.
	try {
			 Hashtable tablaAvance = new Hashtable(2);
			 tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
			 tablaAvance.put(CInfoUsuario.USUARIO_SIR, u.getUsername());
			 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
	}catch(HermodException t)
	{
		/**
		 * @author Cesar Ramírez
		 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
		 * Excepción a visualizar en la ventana de "Error en la validación de datos del formulario". - Correcciones
		 **/
		throw new TurnoNoAvanzadoException("No es posible devolver el turno a la fase. Se requiere una nota informativa.");
	}catch (Throwable e2){
		ExceptionPrinter ep=new ExceptionPrinter(e2);
		Log.getInstance().error(ANCalificacion.class,"No se pudieron desbloquear los folios:"+ep.toString());
		throw new EventoException("No se pudieron desbloquear los folios:"+e2.getMessage(),e2);
	}

	/*****************************************/
	/*        ELIMINAR SASS                  */
	/*****************************************/
	
	
	Fase faseAvance = evento.getFase();    
    Hashtable parametrosAvance = new Hashtable();
  	parametrosAvance.put(Processor.RESULT,CRespuesta.AJUSTAR);
	             
	try 
	{
		//TFS 3582: SI UN TURNO SE VA A DEVOLVER, SE ELIMINAN LAS NOTAS DEVOLUTIVAS
		hermod.removeDevolutivasFromTurno(oid);
		
		hermod.avanzarTurnoNuevoNormal(turno,faseAvance,parametrosAvance,u);
	
	}
    catch (Throwable exception) {
	    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
    }
                 
    return null;
}

	/**
	 * Imprime el objeto imprimible en la estacion asociada al circulo del turno dado
	 * con los parametros asignados.
	 * @param turno
	 * @param imprimible
	 * @param parametros
	 * @return
	 * @throws EventoException
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
                String impresora = (String) parametros.get("Impresora");
                try{
                    if(impresora != null){
                        b.setNombreImpresora(impresora);
                    }else{
                        b.setNombreImpresora("Microsoft Print to PDF");
                    }
                }catch(Throwable T){
                    b.setNombreImpresora("Microsoft Print to PDF");
                }
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
		}
		catch (PrintJobsException t) {
			idImprimible = t.getIdImpresion();
			if(idImprimible == 0){
				throw new EventoException("Se genero una excepción al imprimir la Constancia de testamentos. " + t.getMessage());
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
			mensaje_error = t.getMessage();
			
			if (lanzarExcepcion && !impresion_ok) {
				throw new EventoException(mensaje_error);
			}
			
		}


		return idImprimible;
	}	
  /**
     * Imprime el objeto imprimible en la estacion asociada al circulo del turno
     * dado con los parametros asignados.
     *
     * @param turno es el Turno con el que se creo la solicitud.
     * @param imprimible es la representacion del documento que se desea
     * imprimir (Certificado, Oficio de Pertenencia, Nota Devolutiva, Formulario
     * de Calificacion, Formulario de Correccion, etc).
     * @param parametros tabla de Hashing con los parametros de impresión
     * (además de los parametros asociados al WorkFlow).
     * @param numCopias es el número de copias que se desea imprimir.
     * @return
     */
    private Hashtable imprimir(Turno turno, ImprimibleBase imprimible, Hashtable parametros, int numCopias, String impresora) {
        String circulo = turno.getIdCirculo();
        //String impresora = (String)parametros.get(IMPRESORA);
        String imp = (String) parametros.get("CIRCULO_O_UID");

        //Opción para imprimir en local o en el applet administrativo de impresión
        if (imp != null) {
            circulo = imp;
        }

        //Bundle b = new Bundle(imprimible,impresora);
        Bundle b = null;
        if (impresora != null) {
            b = new Bundle(imprimible, impresora);
        } else {
            b = new Bundle(imprimible);
        }

        String numIntentos = (String) parametros.get(Processor.INTENTOS);
        String espera = (String) parametros.get(Processor.ESPERA);

        Integer intentosInt = new Integer(numIntentos);
        int intentos = intentosInt.intValue();
        Integer esperaInt = new Integer(espera);
        int esperado = esperaInt.intValue();
        boolean resultadoImpresion = false;

        //settear el numero de impresiones
        b.setNumeroCopias(numCopias);

        //Ciclo que se ejecuta de acuerdo al valor recibido en intentos (Imprime 1 copia)
        try {
            printJobs.enviar(circulo, b, intentos, esperado);
            //si imprime exitosamente sale del ciclo.
            resultadoImpresion = true;
        } catch (Throwable t) {
            t.printStackTrace();
            resultadoImpresion = false;
        }

        if (resultadoImpresion) {
            parametros.put(Processor.RESULT, "CONFIRMAR");
        } else {
            parametros.put(Processor.RESULT, "ERROR");
        }
        return parametros;
    }
 /**
     * Imprimir el certificado asociado al turno.
     *
     * @param turno el turno
     * @param parametros tabla de Hashing con los parametros de impresion
     * (además de los del WorkFlow)
     * @return la tabla de hashing de parametros adicionando un registro
     * dependiendo de si la impresion fue o no exitosa.
     * @throws Throwable
     */
    private Hashtable imprimirCertificado(Turno turno, Usuario usuarioSIR, String UID, String impresora) throws Throwable {
        boolean usePrefabricado = false;
        boolean storePrefabricado = false;

        long initial_time = 0, final_time;

        String sNombre = "";
        String archivo = "";
        String cargoToPrint = "";
        String rutaFisica = null;

        String intentosImpresion;
        String esperaImpresion;

        ImprimibleCertificado imprimibleCertificado = null;
        FirmaRegistrador firmaRegistrador = null;
        
        SolicitudRegistro solCerti = (SolicitudRegistro) turno.getSolicitud();
        List listaFolios = solCerti.getSolicitudFolios();
        int numCopias = 1;
        Hashtable parametros = new Hashtable();

        parametros.put("USUARIO_SIR", usuarioSIR.getUsername());
        parametros.put("CIRCULO_O_UID", UID);

        Log.getInstance().debug(ANPago.class, "\n*******************************************************");
        Log.getInstance().debug(ANPago.class, "(ANTES METODO IMPRESION CERTIFICADO)");
        Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

        //Obtener los parámetros.
        //String notificationId = (String) parametros.get(Processor.NOT_ID);
        //INGRESO DE INTENTOS DE IMPRESION
        try {
            intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
            if (intentosImpresion != null) {
                parametros.put(Processor.INTENTOS, intentosImpresion);
            } else {
                intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
                parametros.put(Processor.INTENTOS, intentosImpresion);
            }

            //INGRESO TIEMPO DE ESPERA IMPRESION
            esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
            if (esperaImpresion != null) {
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

        /*
		 * No hay un folio asociado y no hay nada que imprimir
		 * (Por ejemplo certificado de pertenencia sin matricula asociada).
         */
        if (listaFolios.size() < 1) {
            return parametros;
        }

        SolicitudFolio solFolio = (SolicitudFolio) listaFolios.get(listaFolios.size() - 1);
        //List foliosDerivados = new Vector();

        List liquidaciones = solCerti.getLiquidaciones();
        LiquidacionTurnoRegistro liquidacion = (LiquidacionTurnoRegistro) liquidaciones.get(liquidaciones.size() - 1);
        String tipoTarifa ="";
        if(liquidacion.isEsExento()){
         tipoTarifa = CHermod.EXENTO;
        }
        String usuario = (String) parametros.get("USUARIO_SIR");
        Usuario usuarioNeg = new Usuario();
        usuarioNeg.setUsername(usuario);
        String fechaImpresion = this.getFechaImpresion();
        String tipoImprimible = CTipoImprimible.CERTIFICADO_INMEDIATO;
         if (tipoTarifa.equals(CHermod.EXENTO)) {
            tipoImprimible = CTipoImprimible.CERTIFICADO_EXENTO;
        }
        if (!solCerti.getSolicitudesPadres().isEmpty()) {
            if (((SolicitudAsociada) solCerti.getSolicitudesPadres().get(0)).getSolicitudPadre() instanceof SolicitudCertificadoMasivo) {
                tipoImprimible = CTipoImprimible.CERTIFICADOS_MASIVOS;
            } else {
                tipoImprimible = CTipoImprimible.CERTIFICADO_ASOCIADO;
            }

        }

        if (forseti.mayorExtensionFolio(solFolio.getIdMatricula())) {
            tipoImprimible = CTipoImprimible.CERTIFICADO_EXTENSO;
        }

        //obtener textos base de los separadores
        String tbase1 = "";
        String tbase2 = "";
        List variablesImprimibles = hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
        for (Iterator it = variablesImprimibles.iterator(); it.hasNext();) {
            OPLookupCodes op = (OPLookupCodes) it.next();
            if (op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)) {
                tbase1 = op.getValor();
            }
            if (op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)) {
                tbase2 = op.getValor();
            }
        }

        CirculoPk cid = new CirculoPk();
        cid.idCirculo = turno.getIdCirculo();

        /*
		 * obtenemos los valores configurados a nivel del sistema para la impresion de certificados prefabricados
         */
        try {
            ForsetiProperties prop = ForsetiProperties.getInstancia();

            /* verificamos si la aplicacion esta configurada para el uso de prefabricados */
            usePrefabricado = Boolean.valueOf(prop.getProperty(ForsetiProperties.USAR_PREFABRICADO)).booleanValue();

            if (usePrefabricado) {
                /* verificamos si el circulo asociado a la solicitud del certificado, se encuentra habilitado
				 * para el uso del proceso de prefabricados */
                final String circulos_activos = prop.getProperty(ForsetiProperties.CIRCULOS_ACTIVOS);

                if (circulos_activos != null) {
                    usePrefabricado = circulos_activos == "ALL" || circulos_activos.indexOf(solFolio.getIdMatricula().substring(0, 3)) != -1 ? true : false;
                }

                /* verificamos si esta activa la opcion de almacenar el certificado prefabricado, cuando este
				 * no se encuentre almacenado en la base de datos o se encuentre obsoleto */
                if (usePrefabricado) {
                    storePrefabricado = Boolean.valueOf(prop.getProperty(ForsetiProperties.ALMACER_ALGENERAR)).booleanValue();
                }
            }
        } catch (Exception ex) {
            usePrefabricado = false;
        }

        if (usePrefabricado) {
            try {
                Log.getInstance().debug(ANPago.class, "\n*******************************************************");
                initial_time = new Date().getTime();

                PrefabricadoPk id = new PrefabricadoPk(solFolio.getCirculo(), solFolio.getIdMatricula());
                Imprimible imprimible = impresion.getImpresionDAO().getPrefabricadoById(id);

                ByteArrayInputStream bais = new ByteArrayInputStream(imprimible.getDatosImprimible());
                ObjectInputStream ois = new ObjectInputStream(bais);

                Bundle bundle = (Bundle) ois.readObject();

                ois.close();
                bais.close();

                if (bundle.getImprimible() instanceof ImprimibleCertificado) {
                    imprimibleCertificado = (ImprimibleCertificado) bundle.getImprimible();
                } else {
                    usePrefabricado = false;
                }
            } catch (Exception ex) {
                Log.getInstance().fatal(ANPago.class, "Error al tratar de obtener el objeto prefabricado, " + ex.getMessage());
                usePrefabricado = false;
            }
        }

        /* en caso de no estar configurado el circulo para el certificado solicitado, o en caso de haberse
         * generado cualquier tipo de excepcion, procedemos a realizar el proceso normal de generacion del
         * imprimible */
        if (!usePrefabricado) {
            Folio folio = null;

            Log.getInstance().debug(ANPago.class, "\n*******************************************************");
            initial_time = new Date().getTime();
            folio = forseti.getFolioByMatricula(solFolio.getIdMatricula());
            List anotacionesFolio = folio.getAnotaciones();

            if (anotacionesFolio == null || anotacionesFolio.isEmpty()) {
                FolioPk fpk = new FolioPk();
                fpk.idMatricula = solFolio.getIdMatricula();
                anotacionesFolio = forseti.getAnotacionesFolio(fpk);
                folio.setAnotaciones(anotacionesFolio);
            }

            FolioPk fid = new FolioPk();
            fid.idMatricula = folio.getIdMatricula();

            // obtenemos los folios padres
            List padres = forseti.getFoliosPadre(fid);
            // obtenemos los folios hijos
            List hijos = forseti.getFolioHijosEnAnotacionesConDireccion(fid);
            /**
             * @author: David Panesso
             * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION Nuevo
             * listado de folios derivados
             *
             */
            List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

            imprimibleCertificado = new ImprimibleCertificado(folio, null, padres, hijos, foliosDerivadoHijos, null, null, null, null);
            // imprimibleCertificado = new ImprimibleCertificado(folio, turno, padres, hijos, fechaImpresion, tipoImprimible, tbase1, tbase2);  

            // almacenamos el imprimible como prefabricado
            if (storePrefabricado) {
                impresion.getImpresionDAO().guardarPrefabricado(imprimibleCertificado);
            }
        }

        imprimibleCertificado.setTurno(turno);
        imprimibleCertificado.setFechaImpresion(fechaImpresion);
        imprimibleCertificado.setTipoImprimible(tipoImprimible);
        imprimibleCertificado.setTextoBase1(tbase1);
        imprimibleCertificado.setTextoBase2(tbase2);
        /**
         * @author : Carlos Torres Caso Mantis : 14985: Acta - Requerimiento
         * 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
         */
        Traslado traslado = hermod.getFolioDestinoTraslado(solFolio.getIdMatricula());
        imprimibleCertificado.setInfoTraslado(traslado);

        /**
         * @author : Julio Alcazar @change : Set propiedad NIS en el imprimible.
         * Caso Mantis : 0006493: Acta - Requerimiento No 027 - Caracteristicas
         * Impresión certificados
         */
        String text = turno.getIdWorkflow() + "/" + turno.getSolicitud().getIdSolicitud();
        byte[] key = new byte[8];
        key[0] = 5;
        imprimibleCertificado.setNis(Encriptador.encriptar(text, key, "DES"));
        PrintJobsProperties prop = PrintJobsProperties.getInstancia();
        imprimibleCertificado.setIpverificacion(prop.getProperty(PrintJobsProperties.VERIFICACION_CERTIFICADO_IP));
        if (turno.isNacional()) {
            cid.idCirculo = imprimibleCertificado.getFolio().getCirculo();
        }

        final_time = new Date().getTime();
        Log.getInstance().debug(ANPago.class, "\n " + String.valueOf(final_time - initial_time) + " Milliseconds *****");
        Log.getInstance().debug(ANPago.class, "\n*******************************************************");

        imprimibleCertificado.setPrintWatermarkEnabled(true);
        imprimibleCertificado.setUsuario(usuarioSIR);

        //PageFormat pageFormat = new PageFormat();
        //imprimibleCertificado.generate(pageFormat);
        String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;

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
            throw e;
        } catch (Throwable t) {
            t.printStackTrace();
        }

        if (sNombre == null) {
            sNombre = "";
        }

        //+++
        imprimibleCertificado.setCargoRegistrador(cargoToPrint);
        imprimibleCertificado.setNombreRegistrador(sNombre);

        if (rutaFisica != null) {
            BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);

            byte pixeles[] = null;
            try {
                pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
            } catch (Throwable e1) {

                e1.printStackTrace();
            }
            //+++
            imprimibleCertificado.setPixelesImagenFirmaRegistrador(pixeles);
        }

        //+++
        Log.getInstance().debug(ANPago.class, "Tipo de Tarifa = " + tipoTarifa);

        if (tipoTarifa.equals(CHermod.EXENTO) && (solCerti.getSolicitudesPadres() != null
                && solCerti.getSolicitudesPadres().size() > 0)) {
            imprimibleCertificado.setExento(true);
            String textoExento = hermod.getTextoExento();
            imprimibleCertificado.setTextoExento(textoExento);

            ByteArrayOutputStream pdfFormulario = null;

            /**
             * @author: Daniel Forero
             * @change: 1195.IMPRESION.CERTIFICADOS.EXENTOS.PDF Método que
             * genera el archivo PDF del objeto imprimibleCertificado
             *
             */
            try {
                String rutaTempPDF = HermodProperties.getInstancia().getProperty(HermodProperties.HERMOD_RUTA_TEMP_GENERACION);
                PdfCreator pdf = new PdfCreator();
                imprimibleCertificado.setPdf(true);
                pdfFormulario = pdf.generar(imprimibleCertificado);

                SolicitudAsociada turnoMasivo = (SolicitudAsociada) solCerti.getSolicitudesPadres().get(0);

                String ruta = rutaTempPDF + turnoMasivo.getSolicitudPadre().getTurno().getIdWorkflow();

                File dir = new File(ruta);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File file = new File(ruta + FenrirProperties.getInstancia().getProperty(FenrirProperties.SO) + imprimibleCertificado.getFolio().getIdMatricula() + ".pdf");

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(pdfFormulario.toByteArray());
                fos.close();

                parametros.put(Processor.RESULT, "CONFIRMAR");
            } catch (IOException eio) {
                Log.getInstance().error(ANPago.class, "ERROR GENERANDO PDF (IO): " + eio.getMessage());
            } catch (Exception e) {
                parametros.put(Processor.RESULT, "ERROR");
                Log.getInstance().debug(ANPago.class, "ERROR GENERANDO PDF: " + e.getMessage());
            }
        } else {
            if (tipoTarifa.equals(CHermod.EXENTO)) {
                imprimibleCertificado.setTextoExento(hermod.getTextoExento());
                imprimibleCertificado.setExento(true);
            } else {
                imprimibleCertificado.setExento(false);
            }

            parametros = this.imprimir(turno, imprimibleCertificado, parametros, numCopias, impresora);
        }

        String resultado = (String) parametros.get(Processor.RESULT);
        boolean okImpresion = true;
        if (resultado != null) {
            if (resultado.equals("ERROR")) {
                okImpresion = false;
            }
        }
        Log.getInstance().debug(ANPago.class, "\n*******************************************************");
        Log.getInstance().debug(ANPago.class, "(DESPUES METODO IMPRESION CERTIFICADO)");
        Log.getInstance().debug(ANPago.class, "\n*******************************************************\n");

        return parametros;
    }

/**
 * @param evento
 * @return
 */
    private EventoRespuesta entregarRegistroNuevo(EvnCalificacion evento) throws EventoException {

        Turno turno = evento.getTurno();
        Usuario u = evento.getUsuarioNeg();

        /**
         * **************************************
         */
        /*        ELIMINAR SASS                  */
        /**
         * **************************************
         */
        Fase faseAvance = evento.getFase();
        Hashtable parametrosAvance = new Hashtable();
        parametrosAvance.put(Processor.RESULT, CRespuesta.CONFIRMAR);

        try {
            hermod.avanzarTurnoNuevoNormal(turno, faseAvance, parametrosAvance, u);
            String idWorkflowRegistro = turno.getIdWorkflow();
            turno = hermod.getTurnobyWF(idWorkflowRegistro);

        } catch (Throwable exception) {
            throw new EventoException("Error avanzando el turno en el servicio hermod.", exception);
        }

        //ENTREGAR LOS TURNOS DE CERTIFICADOS ASOCIADOS.
        // Constantes para identificador de fase y proceso de los certificados asociados
        String idFaseCertificados = CFase.CER_ENTREGAR;
        long idProceso = 1;

        //Sacar las solicitudes de certificados de la solicitud de registro
        List solicitudesHijas = null;
        SolicitudRegistro solicitudRegistro = (SolicitudRegistro) turno.getSolicitud();

        if (solicitudRegistro != null) {
            //Obtener listado de solicitudes hijas.
            solicitudesHijas = solicitudRegistro.getSolicitudesHijas();
        }

        List Valido = new Vector();
        List solCerts = new Vector();
        Iterator isolh = null;

        if (solicitudesHijas != null) {
            isolh = solicitudesHijas.iterator();
        }

        //Obtener las solicitudes Asociadas
        for (; isolh.hasNext();) {

            //Se agregan todas las solicitudes de certificados a la lista solCerts.
            SolicitudAsociada solA = (SolicitudAsociada) isolh.next();
            SolicitudCertificado solC = null;
            if (solA != null) {
                solC = (SolicitudCertificado) solA.getSolicitudHija();
                if (solC != null) {
                    solCerts.add(solC);
                }

            }
        }
        // Avanzar los turnos de los Certificados Asociados
        try {

            Iterator icerts = solCerts.iterator();

            //Creación de Tabla para paso de parámetros.
            Hashtable tabla = new Hashtable();
            tabla.put(Processor.RESULT, EvnCalificacion.WF_CERTIFICADOS_ENTREGA_OK);

            while (icerts.hasNext()) {

                try {
                    //obtener solicitud Certificado
                    SolicitudCertificado solCerti = (SolicitudCertificado) icerts.next();
                    if (solCerti != null) {
                        SolicitudPk sid = new SolicitudPk();
                        sid.idSolicitud = solCerti.getIdSolicitud();

                        //obtener turno , circulo y fase
                        Turno turnoCertificado = hermod.getTurnoBySolicitud(sid);
                        String idFase = turnoCertificado.getIdFase();
                        Fase fase = hermod.getFase(idFase);
                        String idCirculo = turnoCertificado.getIdCirculo();

                        /**
                         * **************************************
                         */
                        /*        ELIMINAR SASS                  */
                        /**
                         * **************************************
                         */
                        Fase faseAvanceCertificado = fase;
                        Hashtable parametrosAvanceCertificado = new Hashtable();
                        parametrosAvanceCertificado.put(Processor.RESULT, CRespuesta.OK);

                        try {
                            hermod.avanzarTurnoNuevoNormal(turnoCertificado, faseAvanceCertificado, parametrosAvanceCertificado, u);
                            /**
                             * @author Fernando Padilla Velez
                             * @change Acta - Requerimiento No 178 - Integración
                             * Gestión Documental y SIR, Se agrega esta
                             * fragmento de codigo para la publicacion del
                             * mensaje del turno creado.
                             */
                            PublisherIntegracion clienteJMS = new PublisherIntegracion();
                            clienteJMS.setUsuario(u.getUsername());
                            clienteJMS.setTurno(turnoCertificado);
                            clienteJMS.enviar();

                        } catch (Throwable exception) {
                            throw new EventoException("Error avanzando el turno en el servicio hermod.", exception);
                        }
                    }

                } catch (Throwable t) {
                    Log.getInstance().error(ANCalificacion.class, t.getMessage(), t);
                }
            }

        } catch (Throwable t) {
            Log.getInstance().error(ANCalificacion.class, t.getMessage(), t);
        }

        return null;
    }

 /* JALCAZAR Requerimiento MANTIS  0002225
  * funcion entregarRegistroNuevoMult (evento) encargada de actualizar la fase de los turnos (multiple entrega)
  * ha fase FINALIZADO esto se logra modificando la función entregarRegistroNuevo colocandolo un ciclo para
  * pasarle cada uno de los turnos del evento evento.getTurnos();
  */
private EventoRespuesta entregarRegistroNuevoMult(EvnCalificacion evento) throws EventoException{
	List turnos = evento.getTurnos();

	Usuario u=evento.getUsuarioNeg();


	/*****************************************/
	/*        ELIMINAR SASS                  */
	/*****************************************/


	Fase faseAvance = evento.getFase();
    Hashtable parametrosAvance = new Hashtable();
     /**
    * @author      :   Carlos Torres
    * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
    */ 
    LinkedHashMap relaciones = new LinkedHashMap();
    Circulo circulo = null;
  	parametrosAvance.put(Processor.RESULT,CRespuesta.CONFIRMAR);
    Iterator i = turnos.iterator();
	while (i.hasNext())
    {
        String idworkflow =  (String) i.next();
        Turno turno = new Turno();
        turno.setIdWorkflow(idworkflow);
		boolean wfValido = false;

		if (idworkflow.indexOf("-")!=-1){
			String[] partes = idworkflow.split("-");
			if (partes.length==4){
				wfValido = true;
	        	turno.setAnio(partes[0]);
	        	turno.setIdCirculo(partes[1]);
	        	turno.setIdProceso(Long.parseLong(partes[2]));
	        	turno.setIdTurno(partes[3]);
                        /*
                        * @autor         : JATENCIA 
                        * @mantis        : 0011604 
                        * @Requerimiento : 004_589_Funcionario_Fase_ Entregado  
                        * @descripción   : Se adiciona el IdFase dentor del turno temporal.
                        */
                        turno.setIdFase(faseAvance.getID());
                        /* Fin del bloque */
			}
		}

		if(!wfValido){
			throw new EventoException ("El formato del Turno es invalido");
		}
        try
        {
            hermod.avanzarTurnoNuevoNormal(turno,faseAvance,parametrosAvance,u);
            String idWorkflowRegistro = turno.getIdWorkflow();
            turno = hermod.getTurnobyWF(idWorkflowRegistro);
            /**
            * @author      :   Carlos Torres
            * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
            */ 
            circulo = new Circulo();
            circulo.setIdCirculo(turno.getIdCirculo());
        }
        catch (Throwable exception) {
             throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
        }
    
        /**
        * @author      :   Carlos Torres
        * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
        */ 
        if(relaciones.containsKey(turno.getIdRelacionActual())){
            List ts = (List)relaciones.get(turno.getIdRelacionActual());
            ts.add(turno);
        }else{
            List ts = new ArrayList();
            ts.add(turno);
            relaciones.put(turno.getIdRelacionActual(), ts);
        }
    //ENTREGAR LOS TURNOS DE CERTIFICADOS ASOCIADOS.

	// Constantes para identificador de fase y proceso de los certificados asociados
        /*
        * @autor         : JATENCIA 
        * @mantis        : 0011604 
        * @Requerimiento : 004_589_Funcionario_Fase_ Entregado  
        * @descripción   : Se establece una condición que valida si la fase 
        * pertenece a correcciones.
        */
        if (faseAvance.getID().equals(CFase.REG_ENTREGA))
        {
        /* Fin del bloque */    
            String idFaseCertificados=CFase.CER_ENTREGAR;
            long idProceso=1;
        
	//Sacar las solicitudes de certificados de la solicitud de registro

	List solicitudesHijas= null;
	SolicitudRegistro solicitudRegistro = (SolicitudRegistro)turno.getSolicitud();

	if (solicitudRegistro != null)
	{
		//Obtener listado de solicitudes hijas.
		solicitudesHijas= solicitudRegistro.getSolicitudesHijas();
	}

	List Valido=new Vector();
	List solCerts=new Vector();
	Iterator isolh= null;

	if (solicitudesHijas != null)
	{
		isolh= solicitudesHijas.iterator();
	}


	//Obtener las solicitudes Asociadas
	for(;isolh.hasNext();){

	    //Se agregan todas las solicitudes de certificados a la lista solCerts.
	    SolicitudAsociada solA= (SolicitudAsociada) isolh.next();
		SolicitudCertificado solC = null;
	    if (solA != null) {
			solC= (SolicitudCertificado) solA.getSolicitudHija();
			if (solC != null)
			{
				solCerts.add(solC);
			}

	    }
	}




	// Avanzar los turnos de los Certificados Asociados
	try{


		Iterator icerts= solCerts.iterator();

		//Creación de Tabla para paso de parámetros.
		Hashtable tabla=new Hashtable();
		tabla.put(Processor.RESULT, EvnCalificacion.WF_CERTIFICADOS_ENTREGA_OK);


		while(icerts.hasNext()){

			try
			{
			    //obtener solicitud Certificado
	            SolicitudCertificado solCerti = (SolicitudCertificado) icerts.next();
	            if (solCerti != null)
	            {
				    SolicitudPk sid = new SolicitudPk();
				    sid.idSolicitud = solCerti.getIdSolicitud();

                    //obtener turno , circulo y fase
				    Turno turnoCertificado = hermod.getTurnoBySolicitud(sid);
				    String idFase= turnoCertificado.getIdFase();
				    Fase fase= hermod.getFase(idFase);
				    String idCirculo = turnoCertificado.getIdCirculo();



				    	/*****************************************/
				    	/*        ELIMINAR SASS                  */
				    	/*****************************************/


				    	Fase faseAvanceCertificado = fase;
				        Hashtable parametrosAvanceCertificado = new Hashtable();
				      	parametrosAvanceCertificado.put(Processor.RESULT,CRespuesta.OK);

				    	try
				    	{
				    		hermod.avanzarTurnoNuevoNormal(turnoCertificado,faseAvanceCertificado,parametrosAvanceCertificado,u);

				    	}
				        catch (Throwable exception) {
				    	    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
				        }
				        }

			}
			catch (Throwable t)
			{
				Log.getInstance().error(ANCalificacion.class,t.getMessage(), t);
			}
		}

   }catch (Throwable t)
	{
		Log.getInstance().error(ANCalificacion.class,t.getMessage(), t);
	}
        /*jatencia - cerramos el if*/
        }
        /*jatencia - fin bloque*/
    }
    /**
    * @author      :   Carlos Torres
    * @Caso Mantis :   11604: Acta - Requerimiento No 004_589_Funcionario_Fase_ Entregado
    */ 
     if(CFase.REG_ENTREGA.equals(faseAvance.getID()) || CFase.COS_ENTREGAR_ASOCIADOS.equals(faseAvance.getID()) || CFase.REG_ENTREGA_EXTERNO.equals(faseAvance.getID())){
         Set relacionTurnos = relaciones.entrySet();
         TipoRelacion tr = new TipoRelacion();
         if(CFase.REG_ENTREGA.equals(faseAvance.getID())){
            tr.setIdTipoRelacion("25");
            tr.setIdFase(CFase.REG_ENTREGA);
         }else if(CFase.COS_ENTREGAR_ASOCIADOS.equals(faseAvance.getID())){
             tr.setIdTipoRelacion("26");
             tr.setIdFase(CFase.COS_ENTREGAR_ASOCIADOS);
         }else if(CFase.REG_ENTREGA_EXTERNO.equals(faseAvance.getID())){
             tr.setIdTipoRelacion("27");
             tr.setIdFase(CFase.REG_ENTREGA_EXTERNO);
         }
         for(Object entrada:relacionTurnos){
                try {
                    Map.Entry relacion = (Map.Entry)entrada;
                    List ts = (List)relacion.getValue();
                    hermod.crearRelacionNuevo(tr, u,circulo, ts,relacion.getKey().toString(),"");
                } catch (Throwable t) {
                    Log.getInstance().error(ANCalificacion.class,t.getMessage(), t);
                }
         }
     }
	 return null;
  }

	private String listaTurnosWF(List turnos){
		String turnosWF = "Tiene turno(s) de Correccion activos: ";
		
		for(int i=0;i<turnos.size();i++){
			Turno turno = (Turno)turnos.get(i);
			if(i==turnos.size()-1){
				turnosWF += turno.getIdWorkflow();
			}else{
				turnosWF += turno.getIdWorkflow() + ",  ";
			}
		}
		return turnosWF;
	}
    
}


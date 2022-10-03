package gov.sir.hermod.dao.impl.jdogenie;

/*Nueva Funcionalidad Boton de Pago Author: Ingeniero Diego Hernadez Modificacion en: 23/02/2010 by jvenegas */

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.portal.modelo.ProductoTransaccion;
import org.portal.modelo.Transaccion;
import org.portal.modelo.constantes.CTransaccion;

import com.versant.core.jdo.VersantPersistenceManager;
import gov.sir.core.is21.Encriptador;

import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CEstacion;
import gov.sir.core.negocio.modelo.constantes.CEstacionRecibo;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRol;
import gov.sir.core.negocio.modelo.constantes.CSecuencias;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.constantes.CTurnoPortal;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AplicacionPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.BancoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoProcesoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoConsignacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoEfectivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocPagoHeredadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoPagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EstacionReciboEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EstadoHistoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FirmaRegistradorEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ImprimibleEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ImprimibleEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.LiquidacionTurnoCertificadoMasivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficinaCategoriaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.OficinaOrigenEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ProcesoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SecuenciasEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudAsociadaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudCertificadoMasivoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SolicitudFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TarifaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoCertificadoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TransferUtils;
import gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TurnoFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.dao.impl.jdogenie.JDOGenieCiudadanoDAO;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodProperties;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.TurnosPortalDAO;
import gov.sir.hermod.pagos.LiquidadorCertificados;
import gov.sir.hermod.pagos.LiquidadorCertificadosMasivos;
import gov.sir.hermod.pagos.LiquidarException;
import gov.sir.print.common.Bundle;
import gov.sir.print.server.PrintJobsProperties;

public class JDOTurnosPortalDAO implements TurnosPortalDAO {
	
	/**
	 * Metodo encargado de la creacion de turno de certificado masivo con tertificados individuales asociados de orden nacional
	 * 
	 * @param transaccion
	 * @param solicitante
	 * @param rol
	 * @param estacion
	 * @param user
	 * @param pathFirmasRegistradores
	 * @return Tabla con los turnos, el recibo de pago, y los identificadores de los certificados
	 * @throws DAOException
	 */
	public Hashtable crearTurnoTransaccion(Transaccion transaccion, Ciudadano solicitante, Rol rol, Estacion estacion, Usuario user,
			String pathFirmasRegistradores, String idBanco) throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		Hashtable resultados = new Hashtable();
		JDOPagosDAO pagoDAO = new JDOPagosDAO();
		JDOTurnosDAO turnoDAO = new JDOTurnosDAO();
		TurnoEnhancedPk tID = new TurnoEnhancedPk();
		PagoEnhanced pago = new PagoEnhanced();
		List turnosIndividuales = new ArrayList();
		List idTurnosTemporales = new ArrayList();
		
		try{
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			UsuarioEnhancedPk uID = new UsuarioEnhancedPk();
			uID.idUsuario = user.getIdUsuario();
			UsuarioEnhanced usuarioSIR = this.getUsuarioByID(uID, pm);
					
			LiquidacionTurnoCertificadoMasivoEnhanced liquidacion = crearLiquidacionCertificadoMasivo(usuarioSIR, transaccion, solicitante, pm);
		
			BancoEnhanced banco = this.getBancoByID(new BancoEnhancedPk(idBanco),pm);
           /**
	        * Author: Ingeniero Diego Hernandez
            * Modificado en 2010/02/23 by jvenegas
	        */
			if(banco==null)
				banco = this.getBancoByID(new BancoEnhancedPk("19"),pm);
			DocPagoConsignacionEnhanced cons = new DocPagoConsignacionEnhanced(banco, "", "", Long.toString(transaccion.getNumTransaccion()), transaccion.getValor());
			cons.setFecha(this.getFecha());
			AplicacionPagoEnhanced ap = new AplicacionPagoEnhanced();
			ap.setDocumentoPago(cons);
			ap.setValorAplicado(cons.getValorDocumento());
			
			
			pago.addAplicacionPago(ap);
			pago.setLiquidacion(liquidacion);
			pago.setIdSolicitud(liquidacion.getIdSolicitud());
			pago.setUsuario(liquidacion.getSolicitud().getUsuario());
			
			TurnoEnhancedPk turnoID = pagoDAO.procesarPagoPortal(pago, estacion.getEstacionId(), "", usuarioSIR, pm);
			TurnoEnhanced turnoEnh = turnoDAO.getTurnoByID(turnoID, pm);
			
			turnoDAO.crearHistoriaTurnoMasivoPortal(turnoEnh, user, estacion, pm);
			
			String year=Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
			long numRecibo=this.getSecuencialMasivos("000",year,pm);
			pago.setNumRecibo(Long.toString(numRecibo));
			
			Iterator iter = ((SolicitudCertificadoMasivoEnhanced)pago.getLiquidacion().getSolicitud()).getSolicitudesHijas().iterator();
			
			while(iter.hasNext()){
				Hashtable ht = new Hashtable();
				ht.put(CInfoUsuario.USUARIO_SIR, usuarioSIR);
				ht.put(CEstacion.ESTACION_ID, estacion.getEstacionId());
				ht.put(CRol.ID_ROL, CRol.SIR_ROL_CAJERO_CERT_MASIVOS);
				ht.put(CAvanzarTurno.LISTA_VALIDACIONES_MASIVOS, new ArrayList());

				SolicitudAsociadaEnhanced solicitudAsociada = (SolicitudAsociadaEnhanced) iter.next();
				SolicitudCertificadoEnhanced solicitudCertificado = (SolicitudCertificadoEnhanced)solicitudAsociada.getSolicitudHija();
				
				DocumentoPagoEnhanced docPago;
				docPago = new DocPagoHeredadoEnhanced( 0d );
				
				LiquidacionTurnoCertificadoEnhanced liquidacionCertificado = (LiquidacionTurnoCertificadoEnhanced) solicitudCertificado.getLiquidaciones().get(0);							

				AplicacionPagoEnhanced appEfectivo = new AplicacionPagoEnhanced();
				appEfectivo.setIdLiquidacion(liquidacionCertificado.getIdLiquidacion());
				appEfectivo.setIdSolicitud(solicitudCertificado.getIdSolicitud());
				appEfectivo.setValorAplicado(liquidacionCertificado.getValor());
				appEfectivo.setDocumentoPago(docPago);

				PagoEnhanced pagoCertificado = new PagoEnhanced(liquidacionCertificado, null);
				pagoCertificado.addAplicacionPago(appEfectivo);
				pagoCertificado.setIdLiquidacion(liquidacionCertificado.getIdLiquidacion());
				pagoCertificado.setIdSolicitud(solicitudCertificado.getIdSolicitud());
				pagoCertificado.setLiquidacion(liquidacionCertificado);
				pagoCertificado.setUsuario(usuarioSIR);
				
				TurnoEnhanced turnoCert = pagoDAO.procesarPagoPortal(pagoCertificado, ht, pm);
				turnoCert.setNacional(true);
				
				TurnoEnhancedPk tempID = new TurnoEnhancedPk();
				tempID.anio = turnoCert.getAnio();
				tempID.idCirculo = turnoCert.getIdCirculo();
				tempID.idProceso = turnoCert.getIdProceso();
				tempID.idTurno = turnoCert.getIdTurno();
				
				idTurnosTemporales.add(tempID);
				
				EstacionReciboEnhancedPk estacionReciboID = new EstacionReciboEnhancedPk();
		        estacionReciboID.idEstacion = estacion.getEstacionId();
				
				String numReciboCert = String.valueOf(this.getNextNumeroRecibo(estacionReciboID,(Usuario)usuarioSIR.toTransferObject(), turnoCert.getIdProceso(),pm));
				pagoCertificado.setNumRecibo(numReciboCert);
                
			}			
			
			VersantPersistenceManager vpm = (VersantPersistenceManager)pm;
			vpm.flush();
			vpm.refreshAll();
			
			tID.anio = turnoID.anio;
			tID.idCirculo = turnoID.idCirculo;
			tID.idProceso = turnoID.idProceso;
			tID.idTurno = turnoID.idTurno;
			
			TurnoEnhanced turnoMasivo = turnoDAO.getTurnoByID(tID, pm);
			
			resultados.put(CTurnoPortal.TURNO_CERTIFICADO_MASIVO, turnoMasivo.getIdWorkflow());
			
			turnoDAO.makeTransientTurno(turnoMasivo, pm);
			
			Turno turno = (Turno)turnoMasivo.toTransferObject();
			
			Solicitud solicitud = turno.getSolicitud();
			Circulo circulo = solicitud.getCirculo();
			String fechaImpresion = this.getFechaImpresion();
			
			Liquidacion liqCert = (Liquidacion)solicitud.getLiquidaciones().get(0);
			
			PagoPk pID = new PagoPk();
			pID.idSolicitud = liqCert.getIdSolicitud();
			pID.idLiquidacion = liqCert.getIdLiquidacion();
			
			Pago pagoMasivo = pagoDAO.getPagoByIDPortal(pID,pm);
			
			if(pagoMasivo.getLiquidacion()==null){
				pagoMasivo.setLiquidacion((Liquidacion)solicitud.getLiquidaciones().get(0));
			}
			if(pagoMasivo.getLiquidacion().getSolicitud()==null){
				pagoMasivo.getLiquidacion().setSolicitud(solicitud);
			}
			if(pagoMasivo.getLiquidacion().getSolicitud().getTurno()==null){
				pagoMasivo.getLiquidacion().getSolicitud().setTurno(turno);
			}
			
			ImprimibleRecibo impRec = new ImprimibleRecibo(pagoMasivo, circulo, fechaImpresion,CTipoImprimible.RECIBO);
			impRec.setUsuarioGeneraRecibo(""+usuarioSIR.getIdUsuario());
			impRec.setTamanoCarta( true );
			
			int idImprimibleRecibo = guardarImprimible(this.crearImprimibleRecibo(impRec, turno.getIdWorkflow()),pm);
			
			resultados.put(turno.getIdWorkflow(), impRec);
			resultados.put(CTurnoPortal.ID_RECIBO, new Integer(idImprimibleRecibo));
			
			Iterator itera = idTurnosTemporales.iterator();
			
			while(itera.hasNext()){
				Hashtable ht = new Hashtable();
				ht.put(CInfoUsuario.USUARIO_SIR, usuarioSIR);
				ht.put(CEstacion.ESTACION_ID, estacion.getEstacionId());
				ht.put(CRol.ID_ROL, rol.getRolId());
				ht.put(CAvanzarTurno.LISTA_VALIDACIONES_MASIVOS, new ArrayList());

				TurnoEnhancedPk tCertID = (TurnoEnhancedPk) itera.next();
				
				TurnoEnhanced turnoCertEnh = turnoDAO.getTurnoByID(tCertID, pm);
				
				turnoDAO.makeTransientTurno(turnoCertEnh, pm);
				
				Turno turnoCert = (Turno)turnoCertEnh.toTransferObject();
				
				String idMatricula = ((SolicitudFolio)turnoCert.getSolicitud().getSolicitudFolios().get(0)).getIdMatricula();
                
                int idImprimible = crearCertificado(turnoCert, user, fechaImpresion, idMatricula, pathFirmasRegistradores, pm);
                
                TurnoPortal turnoPortal = new TurnoPortal(turnoCert.getIdWorkflow(),idMatricula,idImprimible);
                
                turnosIndividuales.add(turnoPortal);
			}

			pm.currentTransaction().commit();
			
		}catch (Exception e) {
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			DAOException he = new DAOException("No se pudo crear el turno por internet",e);
			throw he;
		}catch(Throwable t){
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			DAOException he = new DAOException("No se pudo crear el turno por internet",t);
			throw he;
		}finally{
			pm.close();
		}
		resultados.put(CTurnoPortal.TURNOS_INDIVIDUALES, turnosIndividuales);
		return resultados;
	}
      /**
	   * Author: Ingeniero Diego Hernandez
       * Modificado en 2010/02/23 by jvenegas
	   */
        	protected String getFecha() {
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

		String fechaImp = "" + dia + " de " + mes + " de " + ano;

		return fechaImp;
	}
/**
	 * Metodo encargado de procesar el pago por ventanilla unica de registr
	 * 
	 * @param transaccion
	 * @param solicitante
	 * @param rol
	 * @param estacion
	 * @param user
	 * @param pathFirmasRegistradores
	 * @param idBanco
	 * @return Tabla con el turno creado y el imprimible recibo
	 * @throws DAOException
	 */
	public Hashtable procesarPagoVUR(Transaccion transaccion, Ciudadano solicitante, Rol rol, Estacion estacion, Usuario user,
			String pathFirmasRegistradores, String idBanco, String cedula)throws DAOException{
		PersistenceManager pm = AdministradorPM.getPM();
		Hashtable resultados = new Hashtable();
		JDOPagosDAO pagoDAO = new JDOPagosDAO();
		JDOTurnosDAO turnoDAO = new JDOTurnosDAO();
		TurnoEnhancedPk tID = new TurnoEnhancedPk();
		PagoEnhanced pago = new PagoEnhanced();
		try{
			pm.currentTransaction().setOptimistic(false);
			pm.currentTransaction().begin();
			
			UsuarioEnhancedPk uID = new UsuarioEnhancedPk();
			uID.idUsuario = user.getIdUsuario();
			UsuarioEnhanced usuarioSIR = this.getUsuarioByID(uID, pm);
			
			ProductoTransaccion producto = (ProductoTransaccion)transaccion.getProductosTransaccion().get(0);
					
			LiquidacionTurnoCertificadoEnhanced liquidacion = obtenerLiquidacionCertificado(usuarioSIR, producto, solicitante, cedula, pm);
		
			BancoEnhanced banco = this.getBancoByID(new BancoEnhancedPk(idBanco),pm);
          /**
	       * Author: Ingeniero Diego Hernandez
           * Modificado en 2010/02/23 by jvenegas
	       */
           DocumentoPagoEnhanced cons = null;
                        
			if(banco == null){
				cons = new DocPagoEfectivoEnhanced(transaccion.getValor());
			}else{
				cons = new DocPagoConsignacionEnhanced(banco, "", "", Long.toString(transaccion.getNumTransaccion()), transaccion.getValor());
			}
			
//			DocPagoConsignacionEnhanced cons = new DocPagoConsignacionEnhanced(banco, "", "", Long.toString(transaccion.getNumTransaccion()), transaccion.getValor());
			
			AplicacionPagoEnhanced ap = new AplicacionPagoEnhanced();
			ap.setIdLiquidacion(liquidacion.getIdLiquidacion());
			ap.setIdSolicitud(liquidacion.getIdSolicitud());
			ap.setDocumentoPago(cons);
			ap.setValorAplicado(cons.getValorDocumento());
			
			pago.addAplicacionPago(ap);
			pago.setLiquidacion(liquidacion);
			pago.setIdLiquidacion(liquidacion.getIdLiquidacion());
			pago.setIdSolicitud(liquidacion.getIdSolicitud());
			pago.setUsuario(liquidacion.getSolicitud().getUsuario());
			
			TurnoEnhancedPk turnoID = pagoDAO.procesarPagoPortal(pago, estacion.getEstacionId(), "", usuarioSIR, pm);			
			TurnoEnhanced turnoEnh = turnoDAO.getTurnoByID(turnoID, pm);
			turnoEnh.setNacional(true);		
			turnoDAO.crearHistoriaTurnoCertificadoVUR(turnoEnh,(Usuario)usuarioSIR.toTransferObject(),pm);
			
			EstacionReciboEnhancedPk estacionReciboID = new EstacionReciboEnhancedPk();
	        estacionReciboID.idEstacion = estacion.getEstacionId();
			
			String numReciboCert = String.valueOf(this.getNextNumeroRecibo(estacionReciboID,(Usuario)usuarioSIR.toTransferObject(), turnoEnh.getIdProceso(),pm));
			pago.setNumRecibo(numReciboCert);
			
			VersantPersistenceManager vpm = (VersantPersistenceManager)pm;
			vpm.flush();
			vpm.refreshAll();
			
			tID.anio = turnoID.anio;
			tID.idCirculo = turnoID.idCirculo;
			tID.idProceso = turnoID.idProceso;
			tID.idTurno = turnoID.idTurno;
			
			TurnoEnhanced turnoCert = turnoDAO.getTurnoByID(tID, pm);
			
			turnoDAO.makeTransientTurno(turnoCert, pm);
			
			Turno turno = (Turno)turnoCert.toTransferObject();
			
			Solicitud solicitud = turno.getSolicitud();
			Circulo circulo = solicitud.getCirculo();
			String fechaImpresion = this.getFechaImpresion();
			
			Liquidacion liqCert = (Liquidacion)solicitud.getLiquidaciones().get(0);
			
			PagoPk pID = new PagoPk();
			pID.idSolicitud = liqCert.getIdSolicitud();
			pID.idLiquidacion = liqCert.getIdLiquidacion();
			
			Pago pagoCert = pagoDAO.getPagoByIDPortal(pID,pm);
			
			if(pagoCert.getLiquidacion()==null){
				pagoCert.setLiquidacion((Liquidacion)solicitud.getLiquidaciones().get(0));
			}
			if(pagoCert.getLiquidacion().getSolicitud()==null){
				pagoCert.getLiquidacion().setSolicitud(solicitud);
			}
			if(pagoCert.getLiquidacion().getSolicitud().getTurno()==null){
				pagoCert.getLiquidacion().getSolicitud().setTurno(turno);
			}
			
			ImprimibleRecibo impRec = new ImprimibleRecibo(pagoCert, circulo, fechaImpresion,CTipoImprimible.RECIBO);
			impRec.setUsuarioGeneraRecibo(""+usuarioSIR.getIdUsuario());
			impRec.setTamanoCarta( true );
			
			int idImprimibleRecibo = guardarImprimible(this.crearImprimibleRecibo(impRec, turno.getIdWorkflow()),pm);
			
			String idMatricula = ((SolicitudFolio)turno.getSolicitud().getSolicitudFolios().get(0)).getIdMatricula();
            
            int idImprimible = crearCertificado(turno, user, fechaImpresion, idMatricula, pathFirmasRegistradores, pm);
            
            TurnoPortal turnoPortal = new TurnoPortal(turno.getIdWorkflow(),idMatricula,idImprimible);
            
            resultados.put(CTurnoPortal.ID_RECIBO, new Integer(idImprimibleRecibo));
            resultados.put("NUM_RECIBO", numReciboCert);
            resultados.put(CTurnoPortal.TURNO_INDIVIDUAL_VUR, turnoPortal);
			
			pm.currentTransaction().commit();
			
		}catch (Exception e) {
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			DAOException he = new DAOException("No se pudo crear el turno por internet",e);
			throw he;
		}catch(Throwable t){
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			DAOException he = new DAOException("No se pudo crear el turno por internet",t);
			throw he;
		}finally{
			pm.close();
		}
		return resultados;
	}
	
	private LiquidacionTurnoCertificadoMasivoEnhanced crearLiquidacionCertificadoMasivo(UsuarioEnhanced usuarioSIR, Transaccion transaccion, Ciudadano solicitante, PersistenceManager pm) throws DAOException{
		LiquidacionTurnoCertificadoMasivoEnhanced liquidacionRta = null;
		
		SolicitudCertificadoMasivoEnhanced solicitudMasivo = new SolicitudCertificadoMasivoEnhanced();
		solicitudMasivo.setCiudadano(CiudadanoEnhanced.enhance(solicitante));
		solicitudMasivo.setComentario(CTransaccion.SOLICITUD_INTERNET);
		
		TipoCertificadoEnhanced tipoCertificado = new TipoCertificadoEnhanced();
		tipoCertificado.setIdTipoCertificado(CTipoCertificado.TIPO_INMEDIATO_ID);
		tipoCertificado.setNombre(CTipoCertificado.TIPO_INMEDIATO_NOMBRE);
                /**@author ctorres
                 **@requerimiento Tarifa Diferencia
                 **15243 Ficha Técnica No. 07 - Creación_de_tarifa_diferencial_SIR
                 ** 
                 **/
                HermodProperties hp = HermodProperties.getInstancia();
		String tarifaDiferencial = hp.getProperty(HermodProperties.HERMOD_TARIFA_DIFERENCIAL);
		try{
			ProcesoEnhancedPk pID = new ProcesoEnhancedPk();
			pID.idProceso = Long.parseLong(CProceso.PROCESO_CERTIFICADOS_MASIVOS);
			solicitudMasivo.setProceso(this.getProcesoByID(pID, pm));

			solicitudMasivo.setUsuario(usuarioSIR);
			
			CirculoEnhancedPk cID = new CirculoEnhancedPk();
			cID.idCirculo = CTurnoPortal.CIRCULO_PORTAL;
			solicitudMasivo.setCirculo(this.getCirculo(cID, pm));
			
			Iterator iter = transaccion.getProductosTransaccion().iterator();
			while(iter.hasNext()){
                                /**@author ctorres
                                 **@requerimiento Tarifa Diferencia
                                 **15243 Ficha Técnica No. 07 - Creación_de_tarifa_diferencial_SIR
                                 ** 
                                 **/
				solicitudMasivo.addSolicitudesHija(obtenerSolicitudCertificado((ProductoTransaccion)iter.next(),solicitudMasivo, tipoCertificado,transaccion.getValor(), pm));
			}
			
			LiquidacionTurnoCertificadoMasivoEnhanced lcm = new LiquidacionTurnoCertificadoMasivoEnhanced();
			lcm.setTipoCertificado(tipoCertificado);
                        /**@author ctorres
                         **@requerimiento Tarifa Diferencia
                         **15243 Ficha Técnica No. 07 - Creación_de_tarifa_diferencial_SIR
                         ** 
                         **/
                        if(transaccion.getValor()==Double.parseDouble(tarifaDiferencial)){
                            lcm.setTipoTarifa(CTipoTarifa.TARIFA_DIFERENCIAL);
                        }else{
                            lcm.setTipoTarifa(CTipoTarifa.NORMAL);
                        }
			lcm.setUsuario(solicitudMasivo.getUsuario());
			lcm.setCirculo("000");
			
			lcm.setSolicitud(solicitudMasivo);
			solicitudMasivo.addLiquidacion(lcm);
			
			liquidacionRta = this.liquidarMasivo(lcm,pm);
			
		}catch(HermodException e){
			DAOException he = new DAOException("No se pudo crear la liquidacion por internet",e);
			throw he;
		}
		catch(Exception e){
			DAOException he = new DAOException("No se pudo crear la liquidacion por internet",e);
			throw he;
		}
		catch(Throwable e){
			DAOException he = new DAOException("No se pudo crear la liquidacion por internet",e);
			throw he;
		}
		return liquidacionRta;
	}
	
	
	private SolicitudAsociadaEnhanced obtenerSolicitudCertificado(ProductoTransaccion producto, SolicitudCertificadoMasivoEnhanced solicitud,
			TipoCertificadoEnhanced tipoCertificado, PersistenceManager pm) throws Exception{
		ProcesoEnhancedPk procesoCertID = new ProcesoEnhancedPk();
		procesoCertID.idProceso = Long.parseLong(CProceso.PROCESO_CERTIFICADOS);
		
		SolicitudFolioEnhanced sfolio = new SolicitudFolioEnhanced();
		FolioEnhancedPk folioID = new FolioEnhancedPk();
		folioID.idMatricula = producto.getIdMatricula();
		sfolio.setFolio(this.getFolioByID(folioID, pm));
		
		SolicitudCertificadoEnhanced sol = new SolicitudCertificadoEnhanced();
		sol.setCirculo(solicitud.getCirculo());
		sol.setCiudadano(solicitud.getCiudadano());
		sol.setUsuario(solicitud.getUsuario());
		sol.setProceso(this.getProcesoByID(procesoCertID, pm)); 
		sol.setNumeroCertificados(producto.getCantidad());
		sol.addSolicitudFolio(sfolio);
		
		LiquidacionTurnoCertificadoEnhanced liquidacionACrear = new LiquidacionTurnoCertificadoEnhanced();
		liquidacionACrear.setSolicitud(sol);
		liquidacionACrear.setTipoCertificado(tipoCertificado);
		liquidacionACrear.setTipoTarifa(CTipoTarifa.NORMAL);
		liquidacionACrear.setUsuario(solicitud.getUsuario());
		liquidacionACrear.setUid("6549876541687654");
		liquidacionACrear.setCirculo(solicitud.getCirculo().getIdCirculo());
		sol.addLiquidacion(liquidacionACrear);
		
		LiquidacionTurnoCertificadoEnhanced liquidacionCreada = (LiquidacionTurnoCertificadoEnhanced)this.liquidarCertificado(liquidacionACrear, pm);
		
		liquidacionCreada.getSolicitud().addLiquidacion(liquidacionCreada);
		SolicitudAsociadaEnhanced solAsociada = new SolicitudAsociadaEnhanced();
		solAsociada.setSolicitudHija(liquidacionCreada.getSolicitud());
		return solAsociada;
	}
         /**@author ctorres
          **@requerimiento Tarifa Diferencia
          **15243 Ficha Técnica No. 07 - Creación_de_tarifa_diferencial_SIR
          ** 
          **/
	private SolicitudAsociadaEnhanced obtenerSolicitudCertificado(ProductoTransaccion producto, SolicitudCertificadoMasivoEnhanced solicitud,
			TipoCertificadoEnhanced tipoCertificado,Double valor, PersistenceManager pm) throws Exception{
		ProcesoEnhancedPk procesoCertID = new ProcesoEnhancedPk();
		procesoCertID.idProceso = Long.parseLong(CProceso.PROCESO_CERTIFICADOS);
		
		SolicitudFolioEnhanced sfolio = new SolicitudFolioEnhanced();
		FolioEnhancedPk folioID = new FolioEnhancedPk();
		folioID.idMatricula = producto.getIdMatricula();
		sfolio.setFolio(this.getFolioByID(folioID, pm));
		
		SolicitudCertificadoEnhanced sol = new SolicitudCertificadoEnhanced();
		sol.setCirculo(solicitud.getCirculo());
		sol.setCiudadano(solicitud.getCiudadano());
		sol.setUsuario(solicitud.getUsuario());
		sol.setProceso(this.getProcesoByID(procesoCertID, pm)); 
		sol.setNumeroCertificados(producto.getCantidad());
		sol.addSolicitudFolio(sfolio);
		
		LiquidacionTurnoCertificadoEnhanced liquidacionACrear = new LiquidacionTurnoCertificadoEnhanced();
		liquidacionACrear.setSolicitud(sol);
		liquidacionACrear.setTipoCertificado(tipoCertificado);
               
                HermodProperties hp = HermodProperties.getInstancia();
		String tarifaDiferencial = hp.getProperty(HermodProperties.HERMOD_TARIFA_DIFERENCIAL);
                if(valor==Double.parseDouble(tarifaDiferencial)){
                    liquidacionACrear.setTipoTarifa(CTipoTarifa.TARIFA_DIFERENCIAL);
                }else{
                    liquidacionACrear.setTipoTarifa(CTipoTarifa.NORMAL);
                }
		liquidacionACrear.setUsuario(solicitud.getUsuario());
		liquidacionACrear.setUid("6549876541687654");
		liquidacionACrear.setCirculo(solicitud.getCirculo().getIdCirculo());
		sol.addLiquidacion(liquidacionACrear);
		
		LiquidacionTurnoCertificadoEnhanced liquidacionCreada = (LiquidacionTurnoCertificadoEnhanced)this.liquidarCertificado(liquidacionACrear, pm);
		
		liquidacionCreada.getSolicitud().addLiquidacion(liquidacionCreada);
		SolicitudAsociadaEnhanced solAsociada = new SolicitudAsociadaEnhanced();
		solAsociada.setSolicitudHija(liquidacionCreada.getSolicitud());
		return solAsociada;
	}
	private LiquidacionTurnoCertificadoEnhanced obtenerLiquidacionCertificado(UsuarioEnhanced usuario,ProductoTransaccion producto, 
			Ciudadano solicitante, String cedula, PersistenceManager pm) throws Exception{
		ProcesoEnhancedPk procesoCertID = new ProcesoEnhancedPk();
		procesoCertID.idProceso = Long.parseLong(CProceso.PROCESO_CERTIFICADOS);
		
		TipoCertificadoEnhanced tipoCertificado = new TipoCertificadoEnhanced();
		tipoCertificado.setIdTipoCertificado(CTipoCertificado.TIPO_INMEDIATO_ID);
		tipoCertificado.setNombre(CTipoCertificado.TIPO_INMEDIATO_NOMBRE);
		
		CirculoEnhancedPk cID = new CirculoEnhancedPk();
		cID.idCirculo = CTurnoPortal.CIRCULO_VUR;
		
		SolicitudFolioEnhanced sfolio = new SolicitudFolioEnhanced();
		FolioEnhancedPk folioID = new FolioEnhancedPk();
		folioID.idMatricula = producto.getIdMatricula();
		sfolio.setFolio(this.getFolioByID(folioID, pm));
		
		SolicitudCertificadoEnhanced sol = new SolicitudCertificadoEnhanced();
		sol.setCirculo(this.getCirculo(cID, pm));
		sol.setCiudadano(CiudadanoEnhanced.enhance(solicitante));
		sol.setUsuario(usuario);
		sol.setProceso(this.getProcesoByID(procesoCertID, pm)); 
		sol.setNumeroCertificados(producto.getCantidad());
		sol.addSolicitudFolio(sfolio);
		sol.setComentario("USUARIO DE VENTANILLA UNICA CON CEDULA No. " + cedula);
		
		LiquidacionTurnoCertificadoEnhanced liquidacionACrear = new LiquidacionTurnoCertificadoEnhanced();
		liquidacionACrear.setSolicitud(sol);
		liquidacionACrear.setTipoCertificado(tipoCertificado);
		liquidacionACrear.setTipoTarifa(CTipoTarifa.NORMAL);
		liquidacionACrear.setUsuario(usuario);
		liquidacionACrear.setUid("6549876541687654");
		liquidacionACrear.setCirculo(sol.getCirculo().getIdCirculo());
		sol.addLiquidacion(liquidacionACrear);
		
		LiquidacionTurnoCertificadoEnhanced liquidacionCreada = (LiquidacionTurnoCertificadoEnhanced)this.liquidarCertificado(liquidacionACrear, pm);
		
		liquidacionCreada.getSolicitud().addLiquidacion(liquidacionCreada);
		return liquidacionCreada;
	}
	
	 public LiquidacionTurnoCertificadoMasivoEnhanced liquidarMasivo(LiquidacionTurnoCertificadoMasivoEnhanced liqCer, PersistenceManager pm) throws LiquidarException {
			
		LiquidacionTurnoCertificadoEnhanced liqIndividual;
		SolicitudCertificadoMasivoEnhanced s = (SolicitudCertificadoMasivoEnhanced) liqCer.getSolicitud();
		double valorLiquidado = 0;
		List listaLiquidaciones = new ArrayList(5);
		LiquidacionTurnoCertificadoMasivoEnhanced liqFinal;
		JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
			
		try
		{
			
	        //Lista con las solicitudes asociadas. (Solicitudes de Certificados Individuales).
			List certAsocs = s.getSolicitudesHijas();
				
			//Se recorre la lista, para obtener una liquidación total. 
			//Sumando los valores parciales de las liquidaciones de certificados. 
			Iterator it2 = certAsocs.iterator();
			
			while(it2.hasNext())
			{
				SolicitudAsociadaEnhanced solAsoc = (SolicitudAsociadaEnhanced) it2.next();
				SolicitudCertificadoEnhanced solCert = (SolicitudCertificadoEnhanced) solAsoc.getSolicitudHija();
				
				//Se valida que la solicitud de certificados no sea nula. 
				if (solCert == null)
				{
					throw new LiquidarException("No se pudo realizar la liquidacion, no existe la solicitud asociada");
				}		
					
				listaLiquidaciones = solCert.getLiquidaciones();
	            // 	Se valida que la lista de liquidaciones no sea nula y contenga al menos un elemento. 
				if (listaLiquidaciones == null)
				{ 
					throw new LiquidarException("No se pudo realizar la liquidacion de Certificados Masivos" +
						" no existen las liquidaciones para los certificados individuales");
				}
				else if (listaLiquidaciones.size()==0)
				{
					throw new LiquidarException("No se pudo realizar la liquidacion de Certificados Masivos" +
						" no existen las liquidaciones para los certificados individuales");
				}
				else
				{
				liqIndividual = (LiquidacionTurnoCertificadoEnhanced) listaLiquidaciones.get(0);
					valorLiquidado += liqIndividual.getValor();
				}
				
			}
			
			valorLiquidado = roundValue (valorLiquidado);
			
			liqCer.setValor(valorLiquidado);
			
			s = solDAO.crearSolicitudCertificadoMasivo(s,pm);

			List listaLiquidacionesMasivos = s.getLiquidaciones();
			
			if (listaLiquidacionesMasivos.size()==0)
			{
				  throw new LiquidarException("No se pudo realizar la liquidacion de Certificados Masivos" +
							   " no existen la liquidación");
			}
			liqFinal = (LiquidacionTurnoCertificadoMasivoEnhanced) listaLiquidacionesMasivos.get(0);
			liqFinal.setSolicitud(s);
			
		}
		catch (Exception e) {
			Log.getInstance().error(LiquidadorCertificadosMasivos.class,e.getMessage(),e);	
			throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
		}
		catch (Throwable e) {
			Log.getInstance().error(LiquidadorCertificadosMasivos.class,e.getMessage(),e);	
			throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
		}      
		
		return liqFinal;
			
				
	}
	
	
	/**
	* Calcula el valor que debe ser liquidado por concepto de una solicitud
	* de certificados. 
	*/
	public LiquidacionTurnoCertificadoEnhanced liquidarCertificado(LiquidacionEnhanced liquidacion, PersistenceManager pm) throws LiquidarException {
		LiquidacionTurnoCertificadoEnhanced liqCert = (LiquidacionTurnoCertificadoEnhanced) liquidacion;
		SolicitudCertificadoEnhanced s = (SolicitudCertificadoEnhanced) liqCert.getSolicitud();
		double valorTotal;
		JDOTarifasDAO tarifasDAO = new JDOTarifasDAO();
		JDOVariablesOperativasDAO varDAO = new JDOVariablesOperativasDAO();
		JDOSolicitudesDAO solDAO = new JDOSolicitudesDAO();
		   
		try {
				
			//Obtener el valor del certificado
			TipoCertificadoEnhancedPk tipoID = new TipoCertificadoEnhancedPk();
			tipoID.idTipoCertificado = liqCert.getTipoCertificado().getIdTipoCertificado();
			
			TipoCertificadoEnhanced tipoCert = varDAO.getTipoCertificadoByID(tipoID, pm);
			
			if(tipoCert==null){
				throw new DAOException ("No se encontró el tipo de certificado con el ID "+tipoID.idTipoCertificado);
			}
			
			TarifaEnhanced tarifaCertificado = tarifasDAO.getTarifa(CTipoTarifa.CERTIFICADOS, tipoCert.getNombre(),pm);
			//NO SE ENCONTRO VALOR  PARA EL TIPO DE CERTIFICADO
			if (tarifaCertificado == null)
			{
				throw new DAOException ("No se encontró valor para la tarifa de certificados con la llave: "+CTipoTarifa.CERTIFICADOS+" - "+liqCert.getTipoCertificado().getNombre());
			}
			double valorCertificado = tarifaCertificado.getValor();
			
			TarifaEnhanced tarifaApl = tarifasDAO.getTarifa(CTipoTarifa.CERTIFICADOS, liqCert.getTipoTarifa(),pm);
               //NO SE ENCONTRO VALOR  PARA EL TIPO DE TARIFA APLICADO
			if (tarifaApl == null)
			{
				throw new DAOException ("No se encontró valor para la tarifa aplicada con la llave: "+CTipoTarifa.CERTIFICADOS+" - "+liqCert.getTipoTarifa());
			}
			
			
			//CASO ESPECIAL TARIFA CERTIFICADO ESPECIAL
			if (liqCert.getTipoTarifa().equals(CTipoTarifa.TARIFA_ESPECIAL))
			{
				valorTotal = tarifaApl.getValor();
			}
			
			//CASO NORMAL
			else
			{
				double tarifaAplicada   = tarifaApl.getValor();
				valorTotal = (valorCertificado * tarifaAplicada)*  s.getNumeroCertificados();
			}
					
			liqCert.setValor(roundValue(valorTotal));
				
			s = solDAO.crearSolicitudCertificado(s,pm);

			Log.getInstance().info(LiquidadorCertificados.class,"SOLICITUD GENERADA");

			if (s.getLiquidaciones()!= null && s.getLiquidaciones().size()>0){
				liqCert = (LiquidacionTurnoCertificadoEnhanced) s.getLiquidaciones().get(0);
			}
		
			return liqCert;

		}catch (Exception e) {
			Log.getInstance().error(LiquidadorCertificados.class,e.getMessage(),e);
			throw new LiquidarException("No se pudo obtener el valor de la liquidacion", e);
		}                
	}
	    
	    
	    
	    
	    
	    
	    
	    /**
	     * Redondea el valor obtenido a la centena.
	     */
    private double roundValue (double valor)
	{
	   	double diferencia = valor%100;
	   	
	   	//Redondeo hacia la centena anterior.
	   	if (diferencia < 50)
	   	{
	   		valor -= diferencia;
	   	}
	   	//Redondeo hacia la centena siguiente.
	   	else
	   	{
	   		valor -= diferencia;
	   		valor +=100;
	   	}
	  	return valor;
	}
	

	private ProcesoEnhanced getProcesoByID(ProcesoEnhancedPk pID, PersistenceManager pm) throws DAOException {
		ProcesoEnhanced rta = null;

		if (String.valueOf(pID.idProceso) != null){
			try {
				rta = (ProcesoEnhanced) pm.getObjectById(pID, true);
			} catch (JDOObjectNotFoundException e) {
				rta = null;
			} catch (JDOException e) {
				Log.getInstance().error(JDOProcesosDAO.class,e.getMessage(),e);
				Log.getInstance().debug(JDOProcesosDAO.class,e);
				throw new DAOException(e.getMessage(), e);
			}
		}

		return rta;
	}
	
	private UsuarioEnhanced getUsuarioByID(UsuarioEnhancedPk usuarioId, PersistenceManager pm)
	throws DAOException {
		UsuarioEnhanced rta = null;
	
		//if (usuarioId.idUsuario != null) {
		try {
			rta = (UsuarioEnhanced) pm.getObjectById(usuarioId, true);
		} catch (JDOObjectNotFoundException e) {
			rta = null;
		} catch (JDOException e) {
			Log.getInstance().debug(this.getClass(), e);
			Log.getInstance().error(this.getClass(), e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}
		//}
		return rta;
	}
	
	protected CirculoEnhanced getCirculo(CirculoEnhancedPk oid,
	        PersistenceManager pm) throws DAOException {
		CirculoEnhanced rta = null;

	    if (oid.idCirculo != null) {
	    	try {
	    		rta = (CirculoEnhanced) pm.getObjectById(oid, true);
	    	} catch (JDOObjectNotFoundException e) {
	            rta = null;
	        } catch (JDOException e) {
	            Log.getInstance().error(this.getClass(), e.getMessage());
	            throw new DAOException(e.getMessage(), e);
	        }
		}

		return rta;
	}
	
	protected FolioEnhanced getFolioByID(FolioEnhancedPk oid,
	        PersistenceManager pm) throws DAOException {
		FolioEnhanced rta = null;

	    if ((oid.idMatricula != null)) {
	        try {
	            rta = (FolioEnhanced) pm.getObjectById(oid, true);

	            //Los folios en estado OBSOLETO no existen, están en la BD por auditoría
	            if (rta.getEstado().getIdEstado().equals(CEstadoFolio.OBSOLETO) || !rta.isDefinitivo()) {
	                rta = null;
	            }
	        } catch (JDOObjectNotFoundException e) {
	            rta = null;
	        } catch (JDOException e) {
	            Log.getInstance().error(this.getClass(), e.getMessage());
	            throw new DAOException(e.getMessage(), e);
	        }
	    }
	    return rta;
	}
	
	/**
	* Obtiene y avanza la secuencia de los recibos de certificados masivos, de acuerdo 
	* a los parametros recibidos. 
	* @param circuloId El identificador del <code>Circulo</code> en el que se va a 
	* expedir el recibo de certificados masivos.
	* @param year El año en el que se va a expedir el recibo de certificados masivos. 
	* @return El secuencial requerido. 
	* @throws HermodException
	*/
   public long getSecuencialMasivos(String circuloId, String year, PersistenceManager pm) throws DAOException {
	   long rta = -1;
	   VersantPersistenceManager pm2 = null;
	   JDOProcesosDAO proDAO = new JDOProcesosDAO();

	   if (circuloId == null) {
			
		   throw new DAOException("El identificador del círculo es inválido");
	   }
		
		   try {

			   //Se hace el cambio de tipo de bloqueo pesimista para
			   //que se bloquee la tabla la cual  nos
			   //provee el secuencial
			   pm2 = (VersantPersistenceManager) pm;
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
			   pm2.flush();

			   CirculoProcesoEnhancedPk sid = new CirculoProcesoEnhancedPk();
			   sid.anio = year;
			   sid.idCirculo = circuloId;
			   Long idProcesoString = new Long (CProceso.PROCESO_CERTIFICADOS_MASIVOS);
			   sid.idProceso = idProcesoString.longValue();

			   
			   CirculoProcesoEnhanced circuloProceso = proDAO.getCirculoProcesoByID(sid, pm);
			   if (circuloProceso == null)
			   {
				   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				   throw new DAOException ("No se pudo obtener el secuencial para el recibo de certificados masivos");
			   }
			   
			  
			   circuloProceso.setSecuencialConstMasivos(circuloProceso.getSecuencialConstMasivos() + 1);
			

			   //Volvemos a setear el tipo de bloqueo pesimista
			   //para que no nos bloquee los siquientes registros
			   //consultados
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);

			   rta = circuloProceso.getSecuencialConstMasivos();
			   
		   } 
		   
		   //No se encontró el círculo Proceso.
		   catch (JDOObjectNotFoundException e) {
                       pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos");
		   } 
		   
		   //Excepción JDO
		   catch (JDOException e) {
			   pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);	   
		  }
		  catch (DAOException e) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  catch (Exception e) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				 throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos",e);
		  }
		  //Otra Excepción
		  catch (Throwable t) {
			  pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
		    throw new DAOException("No se pudo obtener el secuencial para el recibo de certificados masivos");	   
		  }
   
	   return rta;
   }
   
	private int guardarImprimible(Imprimible imprimible, PersistenceManager pm) throws DAOException{
		int idBundle = 0;
		try{
	
			ImprimibleEnhanced imp = new ImprimibleEnhanced();
			
			//Asignacion de llave primaria:
			imp.setIdImprimible((int)this.getSecuencial(CSecuencias.SIR_OP_IMPRIMIBLE, null));
			
			imp.setDatosImprimible(imprimible.getDatosImprimible());
			imp.setIP(imprimible.getIP());
			imp.setUID(imprimible.getUID());
			imp.setTipoImprimible(imprimible.getTipoImprimible());
			imp.setTurno(imprimible.getTurno());			
			imp.setNumeroBytes(imprimible.getNumeroBytes());
			imp.setFolio(imprimible.getFolio());
			imp.setCirculo(imprimible.getCirculo());
			imp.setImprimibleExtenso(imprimible.isImprimibleExtenso());
			imp.setNumeroImpresiones(imprimible.getNumeroImpresiones());			
			imp.setFechaCreacion(new Date());			
	
			pm.makePersistent(imp);
			 
			ImprimibleEnhancedPk id = new ImprimibleEnhancedPk();
			id = (ImprimibleEnhancedPk)pm.getObjectId(imp);
			idBundle = id.idImprimible; 
			 
		 } catch (JDOException e) {
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		} catch (Throwable e) {
			throw new DAOException(e.getMessage(), e);
		}
		return idBundle;
	}
	
	/**
	 * Obtiene y avanza la secuencia de la tabla especificada 
	 * (TOMADO de FORSETTI)
	 * @param tableName Nombre de la tabla de la cual se quiere obtener
	 * el secuencial.
	 * @param pm Persistence Manager de la transacción.
	 * @return El secuencial requerido. 
	 * @throws DAOException
	 */
	protected long getSecuencial(String tableName, PersistenceManager pm) throws DAOException {
		long rta = -1;
		
		boolean transaccionIndependiente = false;
		VersantPersistenceManager pm2 = null;
		
		HermodProperties hprop = HermodProperties.getInstancia();
        String secuencias = hprop.getProperty(
                    HermodProperties.SECUENCIALES_POR_SECUENCIA);
        boolean usarSecuencia = false;
        
        if (secuencias != null)
        {
        	String []secuenciasSplit = secuencias.split(";");
        	for (int i= 0; i< secuenciasSplit.length; i++)
        	{
        		if (secuenciasSplit[i].equals(tableName))
        		{
        			usarSecuencia = true;
        			break;
        		}
        	}
        }

		if(pm==null){
			transaccionIndependiente = true;
		}
		
		if (tableName != null) {
			try {
				if(transaccionIndependiente){
					pm = AdministradorPM.getPM();
					pm.currentTransaction().setOptimistic(false);
					pm.currentTransaction().begin();
				}
				
				if (usarSecuencia)
		        {
					String sql = "SELECT SEC_" + (tableName.length() > 26 ? tableName.substring(0, 26) : tableName) + ".nextval FROM DUAL";
		        	
		        	pm2 = (VersantPersistenceManager) pm;
		        	Connection con = pm2.getJdbcConnection(null);
		        	
		        	PreparedStatement pst = con.prepareStatement(sql);
		        	
		        	ResultSet rs = pst.executeQuery();
		        	if (rs.next())
		        	{
		        		rta = rs.getLong(1);
		        	} else
		        	{
		        		throw new DAOException("No se encontró la secuencia");
		        	}

		        	rs.close();
		        	pst.close();
		        	con.close();
		        } else 
			        {
					//Se hace el cambio de tipo de bloqueo pesimista para
					//que se bloquee la tabla la cual  nos
					//provee el secuencial
					pm2 = (VersantPersistenceManager) pm;
					pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
					pm = pm2;
	
					SecuenciasEnhancedPk sid = new SecuenciasEnhancedPk();
					sid.tableName = tableName;
	
					SecuenciasEnhanced s = this.getSecuenciaByID(sid, pm);
					s.setLastUsedId(s.getLastUsedId() + 1);
	
					//Volvemos a setear el tipo de bloqueo pesimista
					//para que no nos bloquee los siquientes registros
					//consultados
					pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
	
					rta = s.getLastUsedId(); // + 1;
			    }
				
				if(transaccionIndependiente){
					pm.currentTransaction().commit();
				}
				
			} catch (JDOObjectNotFoundException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("No se encontró el registro de la secuencia", e);
			} catch (JDOException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("Error obteniendo el registro de la secuencia", e);
			} catch (DAOException e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("Error obteniendo el registro de la secuencia", e);
			} catch (Exception e) {
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if((pm!=null)&&(pm.currentTransaction().isActive())){
						pm.currentTransaction().rollback();
					}	
				}
				throw new DAOException("Error obteniendo el registro de la secuencia", e);
			}
			finally{
				pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
				if(transaccionIndependiente){
					if(pm!=null){
						pm.close();
					}
				}
			}
		}

		return rta;
	}
	
	protected SecuenciasEnhanced getSecuenciaByID(SecuenciasEnhancedPk sID,
			PersistenceManager pm) throws DAOException {
			SecuenciasEnhanced rta = null;

			if (sID.tableName != null) {
				try {
					rta = (SecuenciasEnhanced) pm.getObjectById(sID, true);
				} catch (JDOObjectNotFoundException e) {
					rta = null;
				} catch (JDOException e) {
					Log.getInstance().error(this.getClass(),e.getMessage());
					throw new DAOException(e.getMessage(), e);
				}
			}

			return rta;
		}
	
	private Imprimible crearImprimibleRecibo(ImprimibleRecibo imRec, String idWorkflow)throws Exception{
		Bundle bundle = new Bundle(imRec);
		bundle.setNumeroCopias(1);
		Imprimible imp = null;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(bundle);
			imp = new Imprimible();
			imp.setDatosImprimible(baos.toByteArray());
			imp.setIP("");
			imp.setUID("");
			imp.setTipoImprimible(imRec.getClass().getName());
			imp.setTurno(idWorkflow);
			imp.setNumeroBytes(imp.getDatosImprimible().length);
			imp.setNumeroImpresiones(0);
			imp.setFechaCreacion(new Date());
		}catch(Exception e){
			throw e;
		}
		return imp;
	}
	
	protected int crearCertificado(Turno turno, Usuario usuarioSIR, String fechaImpresion, String idMatricula, 
			String pathFirmasRegistradores, PersistenceManager pm)throws Exception{
		String tipoImprimible=CTipoImprimible.CERTIFICADO_INMEDIATO;
		FolioPk fID = new FolioPk();
		fID.idMatricula = idMatricula;
		int idImprimible = 0;
		JDOLookupDAO lookupDAO = new JDOLookupDAO();

		try{
			String tbase1 ="";
			String tbase2 = "";
			List variablesImprimibles =  lookupDAO.getLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES, pm);
			for(Iterator it= variablesImprimibles.iterator(); it.hasNext();){
				OPLookupCodes op = (OPLookupCodes) it.next();
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
					tbase1= op.getValor();
				}
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
					tbase2 = op.getValor();
				}
			}
			
			Folio folio = this.getFolioByID(fID, pm);
			folio.setAnotaciones(this.getAnotacionesFolio(fID, pm));
			List padres = this.getFoliosPadre(fID, pm);
			List hijos = this.getFolioHijosEnAnotacionesConDireccion(fID, pm);
                        /**
                        * @author: David Panesso
                        * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                        * Nuevo listado de folios derivados
                        **/
                        List<FolioDerivado> foliosDerivadoHijos = this.getFoliosDerivadoHijos(fID, pm);
			
			ImprimibleCertificado imprimibleCertificado = new ImprimibleCertificado(folio, null, padres, hijos, foliosDerivadoHijos, null, null, null, null);
			imprimibleCertificado.setTurno(turno);
                        
                        /**
                        * @Autor: Fernando Padilla Velez, 30/06/2015
                        * @change:1209.AJUSTE.IMPRIMIBLE.CERTIFICADO.BP.SIR,
                        *         Se agrega el folio destino para que sea impreso 
                        *         desde boton de pago.
                        * */
                        JDOTrasladoTurnosDAO trasladoTurnosDAO = new JDOTrasladoTurnosDAO();
                        imprimibleCertificado.setInfoTraslado(
                                trasladoTurnosDAO.getFolioDestinoTraslado(idMatricula));
                        
                        /**
                        * @Autor: Fernando Padilla Velez, 30/06/2015
                        * @change:1209.AJUSTE.IMPRIMIBLE.CERTIFICADO.BP.SIR,
                        *         Se agrega el folio destino para que sea impreso 
                        *         desde boton de pago.
                        * */
                        String text = turno.getIdWorkflow() +"/"+ turno.getSolicitud().getIdSolicitud();
                        byte [] key  = new byte [8];
                        key[0] = 5;
                        imprimibleCertificado.setNis(Encriptador.encriptar(text, key, "DES"));
                        PrintJobsProperties prop = PrintJobsProperties.getInstancia();
                        imprimibleCertificado.setIpverificacion(prop.getProperty(
                                PrintJobsProperties.VERIFICACION_CERTIFICADO_IP));
                        
			imprimibleCertificado.setFechaImpresion(fechaImpresion);
			imprimibleCertificado.setTipoImprimible(tipoImprimible);
			imprimibleCertificado.setTextoBase1(tbase1);
			imprimibleCertificado.setTextoBase2(tbase2);
			
			imprimibleCertificado.setPrintWatermarkEnabled(true);
			imprimibleCertificado.setUsuario(usuarioSIR);
			
			CirculoPk cid = new CirculoPk();
			cid.idCirculo = turno.getIdCirculo();
			
			String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
			
			FirmaRegistrador firmaRegistrador =  this.getFirmaRegistradorActiva(cid,cargo,pm);

			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
				firmaRegistrador =  this.getFirmaRegistradorActiva(cid,cargo,pm);
			}
			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
				firmaRegistrador =  this.getFirmaRegistradorActiva(cid,cargo,pm);
			}
			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
				firmaRegistrador =  this.getFirmaRegistradorActiva(cid,cargo,pm);
			}

			String rutaFisica =  pathFirmasRegistradores;

			String sNombre = firmaRegistrador.getNombreRegistrador();
			String archivo = firmaRegistrador.getIdArchivo();

			cargo = firmaRegistrador.getCargoRegistrador();

			List cargos = lookupDAO.getLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR,pm);

			String cargoToPrint = "";
			OPLookupCodes lookUp;
			for(Iterator it = cargos.iterator(); it.hasNext();){
				lookUp = (OPLookupCodes) it.next();
				if(lookUp.getCodigo().equals(cargo)){
					cargoToPrint = lookUp.getValor();
				}
			}

			if (sNombre==null)
			  sNombre="";
			imprimibleCertificado.setCargoRegistrador(cargoToPrint);
			imprimibleCertificado.setNombreRegistrador(sNombre);
			
			if(rutaFisica!=null)
			{
				BufferedImage imagenFirmaRegistrador=this.getImage(rutaFisica,archivo);

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
			imprimibleCertificado.setExento(false);
			idImprimible = guardarImprimible(crearImprimible(imprimibleCertificado),pm);
			
		}catch(ForsetiException e){
			throw new Exception(e.getMessage(),e);
		}catch(Exception e){
			throw e;
		}catch(Throwable e){
			throw new Exception(e.getMessage(),e);
		}
		
		return idImprimible;
	}
	
	
	
	/**
     * Retorna el archivo de la firma activa del círculo especificado
     * @param oid
     * @return
     * @throws DAOException
     */
    public FirmaRegistrador getFirmaRegistradorActiva(CirculoPk oid,
        String cargo, PersistenceManager pm) throws DAOException {
        FirmaRegistradorEnhanced rta = null;
        FirmaRegistrador firma = null;

        try {
            //	Traer Objeto Persistente
            CirculoEnhanced objPers = this.getCirculo(new CirculoEnhancedPk(
                        oid), pm);

            if (objPers == null) {
                throw new DAOException("No existe el circulo con el ID: " +
                    oid.idCirculo);
            }

            String idCir = oid.idCirculo;

            Query query = pm.newQuery(FirmaRegistradorEnhanced.class);
            query.declareParameters("String idCir");
            String filtro = "this.idCirculo==idCir && this.activo=="+CFirmaRegistrador.ACTIVA +" ";
			if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL)){
				filtro = filtro + " && ( (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL+"') || (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL_ENCARGADO+"') )";
			}else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO)){
				filtro = filtro + " && ( (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO+"') || (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO_ENCARGADO+"') )";
			}else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL)){
				filtro = filtro + " && ( (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL+"') || (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_ENCARGADO+"') )";
			}else if(cargo.equals(CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO)){
				filtro = filtro + " && ( (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO+"') || (this.cargoRegistrador== '"+CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO_ENCARGADO+"') )";
			}else{
				throw new DAOException("El cargo es inválido: "+cargo);
			}

            query.setFilter(filtro);

            Collection col = (Collection) query.execute(idCir);
            Iterator iter = col.iterator();

            if (iter.hasNext()) {
                rta = (FirmaRegistradorEnhanced) iter.next();
                pm.makeTransient(rta);
            }

            query.closeAll();
        } catch (JDOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }

        if (rta != null) {
            firma = (FirmaRegistrador) rta.toTransferObject();
        }

        return firma;
    }
	
	/**
     * Retorna la lista con las anotaciones de los folios hijos del folio especificado,
     * cada anotacion tiene el objeto folio asociado y la última direccion en la lista
     * de direcciones de este folio
     * Si el folio no tiene hijos o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws DAOException
     */
    public List getFolioHijosEnAnotacionesConDireccion(FolioPk oid, PersistenceManager pm)
        throws DAOException {
        FolioDerivadoEnhanced fd;
        List rta = new ArrayList();
        AnotacionEnhanced aux = new AnotacionEnhanced();

        try {

            String matriculaPadre = oid.idMatricula;

            Query query = pm.newQuery(FolioDerivadoEnhanced.class);
            query.setOrdering("this.hijo.folio.ordenLPAD ascending");
            query.declareParameters("String matriculaPadre");
            query.setFilter("this.padre.folio.idMatricula== matriculaPadre");

            Collection col = (Collection) query.execute(matriculaPadre);

            DireccionEnhanced lastDireccion = null;

            FolioEnhanced folioAux;

            for (Iterator iter = col.iterator(); iter.hasNext();) {
                fd = (FolioDerivadoEnhanced) iter.next();
                aux = fd.getPadre();
                pm.makeTransient(aux);

                folioAux = new FolioEnhanced();
                folioAux.setIdMatricula(fd.getIdMatricula1());
                FolioEnhancedPk fid = new FolioEnhancedPk();
                fid.idMatricula = fd.getIdMatricula1();
                lastDireccion = this.getUltimaDireccion(fid, pm);

                if (lastDireccion != null) {
                    folioAux.addDireccione(lastDireccion);
                }

                aux.setFolio(folioAux);
                rta.add(aux);
            }

            query.closeAll();
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Exception e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }
        return TransferUtils.makeTransferAll(rta);
    }
    
    /**
     * Retorna la lista de los Folios Derivados Hijos de un folio especificado (Folio)
     * Si el folio no tiene hijos o no existe retorna una lista vacía
     * @author: David Panesso
     * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
     * @param oid
     * @return List<FolioDerivado>
     * @throws DAOException
     */
    public List<FolioDerivado> getFoliosDerivadoHijos(FolioPk oid, PersistenceManager pm) 
            throws DAOException {
        FolioDerivadoEnhanced fd;
        List<FolioDerivado> rta = new ArrayList();
        FolioDerivado folDerivado = new FolioDerivado();

        try {
            String matriculaPadre = oid.idMatricula;

            Query query = pm.newQuery(FolioDerivadoEnhanced.class);
            query.setOrdering("this.hijo.folio.ordenLPAD ascending");
            query.declareParameters("String matriculaPadre");
            query.setFilter("this.idMatricula== matriculaPadre");

            Collection col = (Collection) query.execute(matriculaPadre);

            for (Iterator iter = col.iterator(); iter.hasNext();) {
                fd = (FolioDerivadoEnhanced) iter.next();
                folDerivado = new FolioDerivado();
                folDerivado.setIdAnotacion(fd.getIdAnotacion());
                folDerivado.setIdMatricula(fd.getIdMatricula());
                folDerivado.setIdAnotacion1(fd.getIdAnotacion1());
                folDerivado.setIdMatricula1(fd.getIdMatricula1());
                folDerivado.setArea(fd.getArea());
                folDerivado.setHectareas(fd.getHectareas());
                folDerivado.setMetros(fd.getMetros());
                folDerivado.setCentimetros(fd.getCentimetros());
                folDerivado.setLote(fd.getLote());
                folDerivado.setPorcentaje(fd.getPorcentaje());
                rta.add(folDerivado);
            }
            query.closeAll();
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }
	
    /**
     * Retorna la dirección con Mayor ID, de manera trasiente y sin dependencias
     * @param direcciones
     * @return
     */
    protected DireccionEnhanced getUltimaDireccion(FolioEnhancedPk fid,
        PersistenceManager pm) throws DAOException {
        VersantPersistenceManager jdoPM = (VersantPersistenceManager) pm;
        DireccionEnhanced rta = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean txInterna = false;

        try {
            if (!jdoPM.currentTransaction().isActive()) {
                txInterna = true;
            }

            if (txInterna) {
                jdoPM.currentTransaction().begin();
            }

            connection = jdoPM.getJdbcConnection(null);

            String consulta =
                "select dir2.drcc_especificacion, dir2.id_direccion " +
                "from sir_ne_direccion dir2 " +
                "where dir2.id_matricula= ? and " +
                "dir2.id_direccion = ( " +
                "select to_char(max(to_number(dir.id_direccion))) " +
                "from sir_ne_direccion dir " +
                "where dir.id_matricula=dir2.id_matricula)";

            ps = connection.prepareStatement(consulta);
            ps.setString(1, fid.idMatricula);

            rs = ps.executeQuery();

            if (rs.next()) {
                rta = new DireccionEnhanced();
                rta.setIdMatricula(fid.idMatricula);
                rta.setEspecificacion(rs.getString(1));
                rta.setIdDireccion(rs.getString(2));
            }

            ps.close();
            rs.close();
            connection.close();

            if (txInterna) {
                jdoPM.currentTransaction().commit();
            }
        } catch (SQLException e) {
            if (txInterna) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            if (txInterna) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable e) {
            if (txInterna) {
                jdoPM.currentTransaction().rollback();
            }

            Log.getInstance().error(this.getClass(), e.getMessage(), e);
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }
    
	/**
     * Retorna la lista con los los folios padre del folio especificado (Folio)
     * Si el folio no tiene padres o no existe retorna una lista vacía
     * @param oid
     * @return
     * @throws DAOException
     */
    public List getFoliosPadre(FolioPk oid, PersistenceManager pm) throws DAOException {
        FolioDerivadoEnhanced fd;
        List rta = new ArrayList();
        Folio aux = new Folio();

        try {
            String matriculaHija = oid.idMatricula;

            Query query = pm.newQuery(FolioDerivadoEnhanced.class);
            query.setOrdering("this.padre.folio.ordenLPAD ascending");
            query.declareParameters("String matriculaHija");
            query.setFilter("this.idMatricula1==matriculaHija");

            Collection col = (Collection) query.execute(matriculaHija);

            for (Iterator iter = col.iterator(); iter.hasNext();) {
                fd = (FolioDerivadoEnhanced) iter.next();
                aux = new Folio();
                aux.setIdMatricula(fd.getIdMatricula());
				aux.setDefinitivo(true);
                rta.add(aux);
            }

            query.closeAll();
        } catch (JDOObjectNotFoundException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (JDOException e) {
            Log.getInstance().error(this.getClass(), e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }
	
	private Imprimible crearImprimible(ImprimibleCertificado imCert)throws Exception{
		Bundle bundle = new Bundle(imCert);
		bundle.setNumeroCopias(1);
		Imprimible imp = null;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(bundle);
			imp = new Imprimible();
			imp.setDatosImprimible(baos.toByteArray());
			imp.setIP("");
			imp.setUID("");
			imp.setTipoImprimible(imCert.getClass().getName());
			imp.setTurno(imCert.getTurno().getIdWorkflow());
			imp.setNumeroBytes(imp.getDatosImprimible().length);
			imp.setNumeroImpresiones(0);
			imp.setFechaCreacion(new Date());
		}catch(Exception e){
			throw e;
		}
		return imp;
	}
	
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

		SimpleDateFormat format = new SimpleDateFormat("a.a");

		String fechaImp = "Impreso el " + dia + " de " + mes + " de " + ano + " a las " + formato(hora) + ":" + min + ":" + seg + " " + format.getDateFormatSymbols().getAmPmStrings()[c.get(Calendar.AM_PM)].toLowerCase();

		return fechaImp;
	}
	
	protected String formato(int i) {
		if (i < 10) {
			return "0" + (new Integer(i)).toString();
		}

		return (new Integer(i)).toString();
	}
	
	private Folio getFolioByID(FolioPk oid, PersistenceManager pm) throws DAOException {
        FolioEnhanced rta = null;
        Folio aux = null;

        try {
            rta = this.getFolioByID(new FolioEnhancedPk(oid), pm);

            //Solo muestra la información del folio si éste
            //es definitivo
            if (rta != null) {
                if (rta.isDefinitivo()) {
                    this.makeTransientFolio(rta, pm);
					//Revisar si el último estado tiene comentario para setearlo
					if(!rta.getHistorialEstados().isEmpty()){
						EstadoHistoriaEnhanced ultimoEstado = (EstadoHistoriaEnhanced)rta.getHistorialEstados().get(rta.getHistorialEstados().size()-1);
						EstadoFolioEnhanced estado = rta.getEstado();
						estado.setComentario(ultimoEstado.getComentario());
					}
                } else {
                    rta = null;
                }
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            Log.getInstance().error(this.getClass(), e);
        }

        if (rta != null) {
            aux = (Folio) rta.toTransferObject();
            this.ordenarDirecciones(aux.getDirecciones());
        }

        return aux;
    }
	
	/**
	 * Ordena una lista de direcciones por identificador transformado a número
	 * @param direcciones
	 */
	protected void ordenarDirecciones(List direcciones){
		Collections.sort(direcciones, new Comparator(){
			public int compare(Object o1, Object o2) {
				Direccion d1 = (Direccion) o1;
				Direccion d2 = (Direccion) o2;
				Integer i1 = new Integer(d1.getIdDireccion());
				Integer i2 = new Integer(d2.getIdDireccion());
				return i1.compareTo(i2);
			}
		});
	}
	
	/**
    *
    * @param folio
    * @param pm
    * @throws DAOException
    */
    protected void makeTransientFolio(FolioEnhanced folio, PersistenceManager pm)
        throws DAOException {
        if (folio != null) {
            try {
                List anotaciones = new ArrayList();

                //Se obtienen solamente las anotaciones reales, es decir, las que
                //tengan estado diferente a obsoletas y las guardamos en una lista
                //temporal
                
                // Este método se elimina para evitar traer todas las
                // anotaciones involuntariamente. Si se necesitan las
                // anotaciones es necesario consultarlas explicitamente
                /*
                for (Iterator itr = this.getAnotacionesRealesFolio(folio, pm)
                                        .iterator(); itr.hasNext();) {
                    cache = new ArrayList();
                    anota = (AnotacionEnhanced) itr.next();
                    this.makeTransientAnotacion(anota, pm, cache);
                    anotaciones.add(anota);
                }
                */

                for (Iterator itr2 = folio.getDirecciones().iterator();
                        itr2.hasNext();) {
                    this.makeTransientDireccion((DireccionEnhanced) itr2.next(),
                        pm);
                }



                for (Iterator itr3 = folio.getSalvedades().iterator();
                        itr3.hasNext();) {
                    this.makeTransientSalvedadFolio((SalvedadFolioEnhanced) itr3.next(),
                        pm);
                }

                for (Iterator itr4 = folio.getTurnosFolios().iterator();
                        itr4.hasNext();) {
                    this.makeTransientTurnoFolio((TurnoFolioEnhanced) itr4.next(),
                        pm);
                }

                for (Iterator itr5 = folio.getHistorialEstados().iterator();
                        itr5.hasNext();) {
                    this.makeTransientEstadoHistoria((EstadoHistoriaEnhanced) itr5.next(),
                        pm);
                }

                this.makeTransientZonaRegistral(folio.getZonaRegistral(), pm);
                this.makeTransientDocumento(folio.getDocumento(), pm);

                pm.makeTransient(folio.getComplementacion());
                pm.makeTransient(folio.getEstado());
                pm.makeTransient(folio.getTipoPredio());
                pm.makeTransient(folio.getUsuarioCreacion());
                pm.makeTransient(folio);

                //Una vez trasiente el folio seteamos la lista de anotaciones
                folio.setAnotaciones(anotaciones);
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
    /**
    *
    * @param direccion
    * @param pm
    * @throws DAOException
    */
    protected void makeTransientDireccion(DireccionEnhanced direccion,
        PersistenceManager pm) throws DAOException {
        if (direccion != null) {
            try {
                pm.makeTransient(direccion.getEje());
                pm.makeTransient(direccion.getEje1());
                pm.makeTransient(direccion.getUsuarioCreacion());
                pm.makeTransient(direccion.getUsuarioCreacionTMP());
                pm.makeTransient(direccion);
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
    /**
    *
    * @param salvedad
    * @param pm
    * @throws DAOException
    */
    protected void makeTransientSalvedadFolio(SalvedadFolioEnhanced salvedad,
        PersistenceManager pm) throws DAOException {
        if (salvedad != null) {
            try {
                pm.makeTransient(salvedad.getUsuarioCreacion());
                pm.makeTransient(salvedad.getUsuarioCreacionTMP());

                TurnoSalvedadFolioEnhanced aux;

                for (Iterator it = salvedad.getTurnoSalvedadFolios().iterator();
                        it.hasNext();) {
                    aux = (TurnoSalvedadFolioEnhanced) it.next();
                    pm.makeTransient(aux.getTurno());
                    pm.makeTransient(aux);
                }

                pm.makeTransient(salvedad);
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
    /**
    *
    * @param turnoFolio
    * @param pm
    * @throws DAOException
    */
    protected void makeTransientTurnoFolio(TurnoFolioEnhanced turnoFolio,
        PersistenceManager pm) throws DAOException {
    	JDOTurnosDAO turnoDAO = new JDOTurnosDAO();
        if (turnoFolio != null) {
            try {
            	turnoDAO.makeTransientTurno(turnoFolio.getTurno(), pm);
                pm.makeTransient(turnoFolio);
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
    /**
    *
    * @param estadoHistoria
    * @param pm
    * @throws DAOException
    */
   protected void makeTransientEstadoHistoria(
       EstadoHistoriaEnhanced estadoHistoria, PersistenceManager pm)
       throws DAOException {
       if (estadoHistoria != null) {
           try {
               pm.makeTransient(estadoHistoria.getEstadoDestino());
               pm.makeTransient(estadoHistoria.getEstadoOrigen());
               pm.makeTransient(estadoHistoria.getUsuario());
               pm.makeTransient(estadoHistoria);
           } catch (JDOException e) {
               Log.getInstance().error(this.getClass(), e.getMessage());
               throw new DAOException(e.getMessage(), e);
           }
       }
   }
   
   /**
   *
   * @param zona
   * @param pm
   * @throws DAOException
   */
   protected void makeTransientZonaRegistral(ZonaRegistralEnhanced zona,
       PersistenceManager pm) throws DAOException {
       if (zona != null) {
           try {
               pm.makeTransient(zona.getCirculo());
               pm.makeTransient(zona.getVereda().getMunicipio()
                                    .getDepartamento());
               pm.makeTransient(zona.getVereda().getMunicipio());
               pm.makeTransient(zona.getVereda());
               pm.makeTransient(zona);
           } catch (JDOException e) {
               Log.getInstance().error(this.getClass(), e.getMessage());
               throw new DAOException(e.getMessage(), e);
           }
       }
   }
   
   /**
   *
   * @param doc
   * @param pm
   * @throws DAOException
   */
   protected void makeTransientDocumento(DocumentoEnhanced doc,
       PersistenceManager pm) throws DAOException {
       if (doc != null) {
           try {
               pm.makeTransient(doc.getTipoDocumento());
               this.makeTransientOficinaOrigen(doc.getOficinaOrigen(), pm);

               pm.makeTransient(doc);
           } catch (JDOException e) {
               Log.getInstance().error(this.getClass(), e.getMessage());
               throw new DAOException(e.getMessage(), e);
           }
       }
   }
   
   /**
	 * Hace transientes las oficinas origen
	 * @param oficina
	 * @param pm
	 * @throws DAOException
	 */
	protected void makeTransientOficinaOrigen(OficinaOrigenEnhanced oficina,
		PersistenceManager pm) throws DAOException {
		if (oficina != null) {
			try {
				pm.makeTransient(oficina.getTipoOficina());

				if (oficina.getVereda() != null) {
					pm.makeTransient(oficina.getVereda().getMunicipio()
											.getDepartamento());
					pm.makeTransient(oficina.getVereda().getMunicipio());
					pm.makeTransient(oficina.getVereda());
				}

				for(Iterator it = oficina.getCategorias().iterator(); it.hasNext();){
					OficinaCategoriaEnhanced ofCat = (OficinaCategoriaEnhanced)it.next();
					pm.makeTransient(ofCat.getCategoria());
					pm.makeTransient(ofCat);
				}
				
				if (oficina.getCategoriaNotaria()!=null)
				{
					pm.makeTransient(oficina.getCategoriaNotaria());
				}

				pm.makeTransient(oficina);
			} catch (JDOException e) {
				Log.getInstance().error(this.getClass(), e.getMessage());
				throw new DAOException(e.getMessage(), e);
			}
		}
	}
	
	/**
    *
    * @param anota
    * @param pm
    * @throws DAOException
    */
    protected void makeTransientAnotacion(AnotacionEnhanced anota,
        PersistenceManager pm, List cache) throws DAOException {
        if ((anota != null) && (!cache.contains(anota))) {
            try {
                cache.add(anota);

                for (Iterator itr = anota.getAnotacionesCancelacions().iterator();
                        itr.hasNext();) {
                    this.makeTransientAnotacionCancelacion((CancelacionEnhanced) itr.next(),
                        pm, cache);
                }

                for (Iterator itr2 = anota.getAnotacionesCiudadanos().iterator();
                        itr2.hasNext();) {
                    this.makeTransientAnotacionCiudadano((AnotacionCiudadanoEnhanced) itr2.next(),
                        pm);
                }

                for (Iterator itr3 = anota.getAnotacionesHijos().iterator();
                        itr3.hasNext();) {
                    this.makeTransientAnotacionHijo((FolioDerivadoEnhanced) itr3.next(),
                        pm, cache);
                }

                for (Iterator itr4 = anota.getAnotacionesPadre().iterator();
                        itr4.hasNext();) {
                    this.makeTransientAnotacionPadre((FolioDerivadoEnhanced) itr4.next(),
                        pm, cache);
                }

                
                for (Iterator itr5 = anota.getSalvedades().iterator();
                        itr5.hasNext();) {
                    this.makeTransientSalvedadAnotacion((SalvedadAnotacionEnhanced) itr5.next(),
                        pm);
                }

                TurnoAnotacionEnhanced aux;

                for (Iterator it = anota.getTurnoAnotacions().iterator();
                        it.hasNext();) {
                    aux = (TurnoAnotacionEnhanced) it.next();
                    pm.makeTransient(aux.getTurno());
                    pm.makeTransient(aux);
                }

                this.makeTransientDocumento(anota.getDocumento(), pm);

                pm.makeTransient(anota.getEstado());

                if (anota.getNaturalezaJuridica() != null) {
                    pm.makeTransient(anota.getNaturalezaJuridica()
                                          .getGrupoNaturalezaJuridica());
                    pm.makeTransient(anota.getNaturalezaJuridica()
                                          .getDominioNaturalezaJuridica());
                    pm.makeTransient(anota.getNaturalezaJuridica());
                }

                pm.makeTransient(anota.getTipoAnotacion());
                pm.makeTransient(anota.getUsuarioCreacion());
                pm.makeTransient(anota.getUsuarioCreacionTMP());
                pm.makeTransient(anota.getDatosAntiguoSistema());
                pm.makeTransient(anota);
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
    /**
    *
    * @param salvedad
    * @param pm
    * @throws DAOException
    */
    protected void makeTransientSalvedadAnotacion(
        SalvedadAnotacionEnhanced salvedad, PersistenceManager pm)
        throws DAOException {
        if (salvedad != null) {
            try {
                pm.makeTransient(salvedad.getUsuarioCreacion());
                pm.makeTransient(salvedad.getUsuarioCreacionTMP());

                TurnoSalvedadAnotacionEnhanced aux;

                for (Iterator it = salvedad.getTurnoSalvedadAnotacions()
                                           .iterator(); it.hasNext();) {
                    aux = (TurnoSalvedadAnotacionEnhanced) it.next();
                    pm.makeTransient(aux.getTurno());
                    pm.makeTransient(aux);
                }

                pm.makeTransient(salvedad);
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
    /**
    *
    * @param folDer
    * @param pm
    * @throws DAOException
    */
    protected void makeTransientAnotacionPadre(FolioDerivadoEnhanced folDer,
        PersistenceManager pm, List cache) throws DAOException {
        if (folDer != null) {
            try {
                //this.makeTransientAnotacion(folDer.getPadre(), pm, cache);
				pm.makeTransient(folDer.getPadre());
                pm.makeTransient(folDer);
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
    /**
    *
    * @param folDer
    * @param pm
    * @throws DAOException
    */
    protected void makeTransientAnotacionHijo(FolioDerivadoEnhanced folDer,
        PersistenceManager pm, List cache) throws DAOException {
        if (folDer != null) {
            try {
                //this.makeTransientAnotacion(folDer.getHijo(), pm, cache);
				pm.makeTransient(folDer.getHijo());
                pm.makeTransient(folDer);
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
    /**
    *
    * @param canc
    * @param pm
    * @throws DAOException
    */
    protected void makeTransientAnotacionCancelacion(CancelacionEnhanced canc,
        PersistenceManager pm, List cache) throws DAOException {
        if (canc != null) {
            try {
                this.makeTransientAnotacion(canc.getCancelada(), pm, cache);
                pm.makeTransient(canc);
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
    /**
    *
    * @param anotaCiud
    * @param pm
    * @throws DAOException
    */
    protected void makeTransientAnotacionCiudadano(
        AnotacionCiudadanoEnhanced anotaCiud, PersistenceManager pm)
        throws DAOException {
		JDOGenieCiudadanoDAO ciudDAO = new JDOGenieCiudadanoDAO();
        if (anotaCiud != null) {
            try {
                this.makeTransientCiudadano(anotaCiud.getCiudadano(), pm);
				pm.makeTransient(anotaCiud);

				//Se verifica si se debe reemplazar el ciudadano por el temporal
				ciudDAO.reemplazarCiudadanoPorTemporal(anotaCiud, pm);
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            } catch(gov.sir.forseti.dao.DAOException e){
            	Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
    
    /**
    *
    * @param ciud
    * @param pm
    * @throws DAOException
    */
    protected void makeTransientCiudadano(CiudadanoEnhanced ciud,
        PersistenceManager pm) throws DAOException {
        if (ciud != null) {
            try {
                pm.makeTransient(ciud);
            } catch (JDOException e) {
                Log.getInstance().error(this.getClass(), e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }
	
	/**
	    * Obtiene una lista con las anotaciones del folio especificado por su ID
	    * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
	    * @return lista con las anotaciones del folio, cada anotación contiene los objetos
	    * tipoAnotacion, naturalezaJuridica y estado
	    * @see gov.sir.core.negocio.modelo.Anotacion
	    * @throws DAOException
	    */
	    private List getAnotacionesFolio(FolioPk oid, PersistenceManager pm) throws DAOException {
	        FolioEnhanced fol = null;
	        AnotacionEnhanced anota = null;
	        List rta = new ArrayList();

	        try {
	            fol = this.getFolioByID(new FolioEnhancedPk(oid), pm);

	            if (fol == null) {
	                throw new DAOException("El folio no existe. IdMatricula: " +
	                    oid.idMatricula);
	            }

	            if (!fol.isDefinitivo()) {
	                throw new DAOException(
	                    "El folio se encuentra en estado temporal");
	            }

	            List rta2 = this.getAnotacionesRealesFolio(fol, pm);

	            //List rta2 = fol.getAnotaciones();
	            for (Iterator itr = rta2.iterator(); itr.hasNext();) {
	                anota = (AnotacionEnhanced) itr.next();
	                this.makeTransientAnotacion(anota, pm, new ArrayList());
	                rta.add(anota);
	            }

	            //pm.makeTransientAll(rta);
	        } catch (JDOObjectNotFoundException e) {
	            Log.getInstance().error(this.getClass(), e.getMessage());
	            throw new DAOException(e.getMessage(), e);
	        } catch (JDOException e) {
	            Log.getInstance().error(this.getClass(), e.getMessage());
	            throw new DAOException(e.getMessage(), e);
	        }

	        rta = TransferUtils.makeTransferAll(rta);

	        return rta;
	    }
	    /**
	     * Obtiene una lista con las anotaciones del folio especificado por su ID
	     * @param oid identificador del Folio conformado por Nro matricula y ZonaRegistral
	     * @return lista con las anotaciones del folio, cada anotación contiene los objetos
	     * tipoAnotacion, naturalezaJuridica y estado
	     * @see gov.sir.core.negocio.modelo.Anotacion
	     * @throws DAOException
	     */
	     protected List getAnotacionesRealesFolio(FolioEnhanced fol,
	         PersistenceManager pm) throws DAOException {
	         List rta = null;

	         try {
	             if (fol != null) {
	                 Query query = pm.newQuery(AnotacionEnhanced.class);
	                 query.declareParameters("FolioEnhanced fol");
	                 query.setFilter("this.folio==fol && (this.link==null || this.link==false) ");
	                 //*query.setFilter("this.folio==fol && this.estado.idEstadoAn!='" +
	                 //*        CEstadoAnotacion.OBSOLETA + "' && (this.link==null || this.link==false) ");
	                 query.setOrdering("ordenLPAD ascending");
	                 rta = (List) query.execute(fol);
	             }
	         } catch (JDOException e) {
	             Log.getInstance().error(this.getClass(), e.getMessage());
	             throw new DAOException(e.getMessage(), e);
	         }

	         return rta;
	     }
	     
	     private BufferedImage getImage(String path, String nombreArchivo)
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
	 			Log.getInstance().error(JDOTurnosPortalDAO.class,e);
	 			Log.getInstance().error(JDOTurnosPortalDAO.class,"Error imprimiendo el gráfico en la ruta: "+nombreCompleto);
	 		}

	 		return buf;
	 	}



	 	private String getNombreCompleto(String path, String nombreArchivo)
	 	{

	 		String nombreCompleto=null;

	 		if (!path.trim().equals(CHermod.CADENA_VACIA))
	 		  nombreCompleto = path + nombreArchivo;
	 		else
	 		  nombreCompleto = nombreArchivo;

	 	  return nombreCompleto;
	 	}
	 	
	 	/**
		 * Obtiene un <code>Banco</code> dado su identificador, método utilizado
		 * para transacciones.
		 * <p>
		 * En caso de que el <code>Banco</code>con el identificador dado no se
		 * encuentre en la Base de Datos se retorna <code>null</code>
		 * @param bID identificador del <code>Banco</code>
		 * @param pm PersistenceManager de la transaccion
		 * @return <code>Banco</code> con sus atributos. 
		 * @throws DAOException
		 * @see gov.sir.core.negocio.modelo.Banco
		 */
		protected BancoEnhanced getBancoByID(
			BancoEnhancedPk bID,
			PersistenceManager pm)
			throws DAOException {
			BancoEnhanced rta = null;

			if (bID.idBanco != null) {
				try {
					rta = (BancoEnhanced) pm.getObjectById(bID, true);
				} catch (JDOObjectNotFoundException e) {
					rta = null;
				} catch (JDOException e) {
					Log.getInstance().debug(JDOTurnosPortalDAO.class,e);
					Log.getInstance().error(JDOTurnosPortalDAO.class,e.getMessage(), e);
					throw new DAOException(e.getMessage(), e);
				}
			}
			return rta;
		}
		
		/**
	     * Obtiene el siguiente número de recibo según la secuencia configurada para
	     * una estación en particular.
	     * <p>
	     * Método utilizado en transacciones.
	     * @param oid Identificador de la <code>EstacionRecibo</code> de la cual se desea
	     * obtener el siguiente número de recibo.
	     * @return El siguiente número de recibo asociado con la estación cuyo identificador
	     * fue recibido como parámetro.
	     * @throws DAOException
	     * @see gov.sir.core.negocio.modelo.EstacionRecibo
	     */
	    protected long getNextNumeroRecibo(EstacionReciboEnhancedPk oid, Usuario user, long idProceso, 
	        PersistenceManager pm) throws DAOException {
	        EstacionReciboEnhanced aux = null;
	        long rta;

	        try {
	            aux = this.getEstacionReciboEnhanced(oid, idProceso ,pm);
	            
	            if (aux != null) {
	            	if (aux.getUltimoNumero() == aux.getNumeroFinal()) {
	                    throw new DAOException(
	                        "No se puede generar número de recibo. Se ha alcanzado el número final de recibo configurado, se debe resetear la secuencia para la estación");
	                }

	                rta = aux.getUltimoNumero() + 1;
	                aux.setUltimoNumero(rta);
	            } else {
	            	rta = -2;
	            }

	            /*if (aux == null) {
	                throw new DAOException("No se encontró la estación con el ID: " +
	                    oid.idEstacion);
	            }*/

	            /*if (aux.getUltimoNumero() == aux.getNumeroFinal()) {
	                throw new DAOException(
	                    "No se puede generar número de recibo. Se ha alcanzado el número final de recibo configurado, se debe resetear la secuencia para la estación");
	            }

	            rta = aux.getUltimoNumero() + 1;
	            aux.setUltimoNumero(rta);*/
	        } catch (JDOObjectNotFoundException e) {
	            rta = 0;
	        } catch (JDOException e) {
	            Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
	            throw new DAOException(e.getMessage(), e);
	        }
	        
	            
	        EstacionReciboEnhancedPk oi = new EstacionReciboEnhancedPk();
	        
	        if (rta != -2) {
	        	return rta;
	        } else {
	            if(user==null){
	            	throw new DAOException("No se encontró la estación con el ID: " + oid.idEstacion + " el usuario es vacío");
	            }

	            String estacionPrivadaUsuario = "X-" + user.getUsername();

	        	//logger.debug("El nombre de la estacion Privada es " + estacionPrivadaUsuario);
	        	try {
	        		
	        		oi.idEstacion = estacionPrivadaUsuario;
	        		
	                aux = this.getEstacionReciboEnhanced(oi,idProceso,  pm);
	                
	                if (aux != null) {
	                	if (aux.getUltimoNumero() == aux.getNumeroFinal()) {
	                        throw new DAOException(
	                            "No se puede generar número de recibo. Se ha alcanzado el número final de recibo configurado, se debe resetear la secuencia para la estación");
	                    }
	                    rta = aux.getUltimoNumero() + 1;
	                    aux.setUltimoNumero(rta);
	                } else {
	                	rta = -2;
	                }
	            } catch (JDOObjectNotFoundException e) {
	                rta = 0;
	            } catch (JDOException e) {
	                Log.getInstance().error(JDOLookupDAO.class,e.getMessage());
	                throw new DAOException(e.getMessage(), e);
	            }
	        }
	   
	        try {
		        if (rta == -2) {
		        	 throw new DAOException("No se encontró la estación con el ID: " + oi.idEstacion);
		        }
	        } catch (JDOObjectNotFoundException e) {
	            rta = 0;
	        } catch (JDOException e) {
	            Log.getInstance().error(JDOTurnosPortalDAO.class,e.getMessage());
	            throw new DAOException(e.getMessage(), e);
	        }
	        
	        return rta;
	    }
	    
	    /**
	     * Obtiene un objeto <code>EstacionRecibo</code> estación recibo dado su
	     * identificador.
	     * <p>
	     * Método utilizado en transacciones.
	     * @param oid Identificador de la <code>EstacionRecibo</code> que quiere ser
	     * recuperada.
	     * @param pm <code>PersistenceManager</code> de la transacción.
	     * @return La <code>EstacionRecibo</code> asociada con el identificador pasado
	     * como parámetro.
	     * @throws DAOException
	     * @see gov.sir.core.negocio.modelo.EstacionRecibo
	     */
	    protected EstacionReciboEnhanced getEstacionReciboEnhanced(
	        EstacionReciboEnhancedPk oid, long idProceso, PersistenceManager pm)
	        throws DAOException {
	        EstacionReciboEnhanced rta = null;
	        EstacionReciboEnhanced rtadefault = null;

	        long idProcesodefault = Long.valueOf(CEstacionRecibo.PROCESO_DEFAULT).longValue();
	        
	        if (oid.idEstacion != null) {
	            try {
	            	
	            	Query query = pm.newQuery(EstacionReciboEnhanced.class);
	            	query.declareParameters("String idEstacion");
	            	query.setFilter("this.idEstacion == idEstacion");
	            	
	               // rta = (EstacionReciboEnhanced) pm.getObjectById(oid, true);
	            	Collection col = (Collection)query.execute(oid.idEstacion);
	            	
	            	boolean entroEstacionReciboProceso = false;
	            	
	            	//Buscar la estacion recibo de la estacion 
	            	for (Iterator iter = col.iterator(); iter.hasNext();) {
	            		EstacionReciboEnhanced estacionreciboenhanced = (EstacionReciboEnhanced)iter.next();
	            		if (estacionreciboenhanced.getNumeroProceso() == idProceso) {
	            			rta = estacionreciboenhanced;
	            			entroEstacionReciboProceso = true;
	            		}
	            		
	            		if (estacionreciboenhanced.getNumeroProceso() == idProcesodefault) {
	            			rtadefault = estacionreciboenhanced;
	            		}
	            	}
	            	
	            	// Si no encontro la del proceso, tiene que buscar la estación por default
	            	if (!entroEstacionReciboProceso) {
	            		if (rtadefault != null) {
	            			rta = rtadefault;
	            		} 
	            	}
	            	
	            } catch (JDOObjectNotFoundException e) {
	                rta = null;
	            } catch (JDOException e) {
	                Log.getInstance().error(JDOTurnosPortalDAO.class,e.getMessage());
	                throw new DAOException(e.getMessage(), e);
	            }
	        }

	        return rta;
	    }
}

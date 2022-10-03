package gov.sir.hermod.dao;

/*Nueva Funcionalidad Boton de Pago Author: Ingeniero Diego Hernadez Modificacion en: 23/02/2010 by jvenegas */

import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Usuario;

import java.util.Hashtable;
import java.util.List;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.portal.modelo.Transaccion;

public interface TurnosPortalDAO {
	
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
			String pathFirmasRegistradores, String idBanco) throws DAOException;
	/**
	 * Metodo encargado de procesar el pago por ventanilla unica de registro
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
			String pathFirmasRegistradores, String idBanco, String cedula)throws DAOException;
}

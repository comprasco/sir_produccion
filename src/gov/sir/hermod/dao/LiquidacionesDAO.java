/*
 * Interfaz para el manejo de las liquidaciones relacionadas
 * con los diferentes procesos de la aplicación. 
 *
 */

package gov.sir.hermod.dao;

import java.util.Date;
import java.util.Map;

import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionPk;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.SolicitudRestitucionReparto;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.TurnoPk;


/**
 *
 * @author  mortiz, dlopez
 */
public interface LiquidacionesDAO {
    
	
	
	
	/**
	* Agrega una liquidacion de registro en el sistema y la asocia a una solicitud
	* @param  l <code>LiquidacionTurnoRegistro</code> con sus atributos,
	* exceptuando el identificador.
	* @param  sID identificador de la Solicitud de Registro a la que se debe
	* asociar la <code>Liquidacion</code>
	* @return  <code>LiquidacionTurnoRegistro</code> generada.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.SolicitudRegistro
	*/
	LiquidacionTurnoRegistro addLiquidacionToSolicitudRegistro(LiquidacionTurnoRegistro l, SolicitudPk sID) throws DAOException;
	
	
	
	/**
	* Agrega una liquidacion de certificados en el sistema y la asocia a una solicitud
	* @param  l <code>LiquidacionTurnoCertificado</code> con sus atributos,
	* exceptuando el identificador.
	* @param  sID identificador de la Solicitud de Certificados a la que se debe
	* asociar la <code>Liquidacion</code>
	* @return  <code>LiquidacionTurnoCertificado</code> generada.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.SolicitudCertificado
	*/
	LiquidacionTurnoCertificado addLiquidacionToSolicitudCertificado(LiquidacionTurnoCertificado l, SolicitudPk sID) throws DAOException;
	
	
	
	/**
	 * Agrega una liquidacion de consultas en el sistema y la asocia a una solicitud
	 * @param  l <code>LiquidacionTurnoConsulta</code> con sus atributos,
	 * exceptuando el identificador.
	 * @param  s <code>SolicitudConsulta</code> a la que se debe
	 * asociar la <code>Liquidacion</code>
	 * @return  <code>LiquidacionTurnoConsulta</code> generada.
	 * @throws DAOException
	 * @see gov.sir.core.negocio.modelo.Liquidacion
	 * @see gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta
	 * @see gov.sir.core.negocio.modelo.Solicitud
	 * @see gov.sir.core.negocio.modelo.SolicitudConsulta
	 */
	LiquidacionTurnoConsulta addLiquidacionToSolicitudConsulta(LiquidacionTurnoConsulta l, SolicitudConsulta s) throws DAOException;
	
	
	
	/**
	* Agrega una liquidacion de correcciones en el sistema y la asocia a una solicitud
	* @param  l <code>LiquidacionTurnoCorreccion</code> con sus atributos,
	* exceptuando el identificador.
	* @param  s <code>SolicitudCorreccion<code> a la que se debe
	* asociar la <code>Liquidacion</code>
	* @return  <code>LiquidacionTurnoCorreccion</code> generada.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.SolicitudCorreccion
	*/
	LiquidacionTurnoCorreccion addLiquidacionToSolicitudCorreccion(LiquidacionTurnoCorreccion l, SolicitudCorreccion s) throws DAOException;

	
	
	
	
	/**
	* Obtiene una liquidacion con todos sus atributos, dado su identificador.
	* @param lID identificador de la <code>Liquidacion</code>
	* @return <code>Liquidacion</code> con sus atributos y jerarquia: Pago, Solicitud,
	* Círculo, Proceso, Turno.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoCertificadoMasivo
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoConsulta
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto
	*/
	Liquidacion getLiquidacionByID(LiquidacionPk lID ) throws DAOException;
	
	
	
	
	/**
	* Agrega una liquidacion de reparto Notarial en el sistema y la asocia a una solicitud
	* @param  l <code>LiquidacionTurnoRestitucionReparto</code> con sus atributos,
	* exceptuando el identificador.
	* @param  s <code>SolicitudRestitucionReparto</code> a la que se debe
	* asociar la <code>Liquidacion</code>
	* @return  <code>LiquidacionTurnoRestitucionReparto</code> generada.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoRestitucionReparto
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.SolicitudRestitucionReparto
	*/
	LiquidacionTurnoRestitucionReparto addLiquidacionToSolicitudRestitucionReparto(
	LiquidacionTurnoRestitucionReparto l, SolicitudRestitucionReparto s) throws DAOException;
	
	
	
	
	/**
	* Agrega una liquidacion de fotocopias en el sistema y la asocia a una solicitud
	* @param  l <code>LiquidacionTurnoFotocopia</code> con sus atributos,
	* exceptuando el identificador.
	* @param  s Solicitud de Fotocopia a la que se debe
	* asociar la <code>Liquidacion</code>
	* @return  <code>LiquidacionTurnoFotocopia</code> generada.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.SolicitudFotocopia
	*/
	LiquidacionTurnoFotocopia addLiquidacionToSolicitudFotocopia(
		LiquidacionTurnoFotocopia l, SolicitudFotocopia s) throws DAOException;
	
	
	
	
	/**
	* Agrega una liquidacion de Devolución en el sistema y la asocia a una solicitud
	* @param  l <code>LiquidacionTurnoDevolución</code> con sus atributos,
	* exceptuando el identificador.
	* @param  sID identificador de la Solicitud de Devolución a la que se debe
	* asociar la <code>Liquidacion</code>
	* @return  <code>LiquidacionTurnoDevolución</code> generada.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoDevolucion
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.SolicitudDevolucion
	*/
	LiquidacionTurnoDevolucion addLiquidacionToSolicitudDevolucion(LiquidacionTurnoDevolucion ltd, SolicitudPk sdID) throws DAOException;
	
	
	
	
	/**
	* Agrega una liquidacion de reparto Notarial en el sistema y la asocia a una solicitud
	* @param  l <code>LiquidacionTurnoRepartoNotarial</code> con sus atributos,
	* exceptuando el identificador.
	* @param  s <code>SolicitudRepartoNotarial</code> a la que se debe
	* asociar la <code>Liquidacion</code>
	* @return  <code>LiquidacionTurnoRepartoNotarial</code> generada.
	* @throws DAOException
	* @see gov.sir.core.negocio.modelo.Liquidacion
	* @see gov.sir.core.negocio.modelo.LiquidacionTurnoRepartoNotarial
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.SolicitudRepartoNotarial
	*/
	LiquidacionTurnoRepartoNotarial addLiquidacionToSolicitudRepartoNotarial (LiquidacionTurnoRepartoNotarial liq, SolicitudRepartoNotarial sol) throws DAOException;
	
	
	
	/**
	* Obtiene el valor a liquidar para un <code>Acto</code> de acuerdo
	* al tipo de <code>Acto</code> y la cuantia.
	* @param acto objeto <code>Acto</code> con la información ingresada
	* @return un objeto <code>Acto</code> con los detalles del valor
	* @see gov.sir.core.negocio.modelo.Acto
	* @throws <code>DAOException</code>
	*/
	Acto getLiquidacionActo(Acto acto) throws DAOException;
        
        /**
	* Valida la cuantia de un acto con respecto a lo que se edita.
	* @param acto objeto <code>Acto</code> con la información ingresada
	* @param i <code>int</code> posicion del acto
	* @return true o false dependiendo de la validacion.
	* @see gov.sir.core.negocio.modelo.Acto
	*/
	public boolean validacionActo(Acto acto, int i) throws DAOException;
	/**
	* Obtiene un valor realacionado a un <code>Turno</code> y a sus turnos
	* anteriores.
	* @param tID identificador del <code>Turno</code>
	* @return valor <code>long</code> 
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.Liquidacion
	*/
	double getValorByTurno(TurnoPk tID) throws DAOException;
	
	
	/** 
	* Elimina la <code>Solicitud</code> recibida como parámetro. 
	* @param solicitud La <code>Solicitud</code> que va a ser eliminada. 
	* @return <code>true</code> o <code>false</code> dependiendo del resultado de la operación.
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @throws <code>DAOException</code>
	*/
	public boolean deleteSolicitud (Solicitud solicitud) throws DAOException;
	
	
	/**
	* Este servicio permite agregar una liquidación, cuyo valor ha sido ingresado por el usuario
	* a una solicitud.
	* @param solicitud La <code>Solicitud</code> a la cual se va a agregar la liquidación.
	* @param liquidacion La <code>Liquidacion</code> que va a ser agregada a la solicitud. 
	* @return <code>true </code> o <code>false </code> dependiendo del resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.Liquidacion
	*/
    public boolean addLiquidacionToSolicitud(Solicitud solicitud, Liquidacion liquidacion) throws DAOException;
    
    
	/**
	* Permite modificar los datos de una liquidación de registro de documentos.
	* @author dlopez
	* @param nuevaLiquidacion Nueva liquidación que va a ser asociada a la solicitud. 
	* <p>La nueva liquidación tiene asociada su respectiva solicitud.  
	*/
	public Liquidacion modificarLiquidacionRegistro (Liquidacion nuevaLiquidacion) throws DAOException;
	
	
	

	/**
	* Obtiene un valor realacionado a un <code>Turno</code> y a sus turnos
	* anteriores.
	* @param tID identificador del <code>Turno</code>
	* @return valor <code>long</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.Liquidacion
	*/
	public double getValorDerechosByTurno(TurnoPk tId) throws DAOException;
	
	

	/**
	* Obtiene un valor realacionado a un <code>Turno</code> y a sus turnos
	* anteriores.
	* @param tID identificador del <code>Turno</code>
	* @return valor <code>long</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.Liquidacion
	*/
	public double getValorImpuestosByTurno(TurnoPk tId) throws DAOException;
	
	
	

	/**
	* Obtiene un valor realacionado a un <code>Turno</code> y a sus turnos
	* anteriores.
	* @param tID identificador del <code>Turno</code>
	* @return valor <code>long</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.Liquidacion
	*/
	public double getValorOtroImpuestoByTurno(TurnoPk tId) throws DAOException;


	/**
	* Obtiene un valor realacionado a un <code>Turno</code> y a sus turnos
	* anteriores.
	* @param tID identificador del <code>Turno</code>
	* @return valor <code>long</code>
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Turno
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.Liquidacion
	*/
	public double getValorMoraByTurno(TurnoPk tId) throws DAOException;



    /**
     * Obtiene un subconjunto de las solicitudes sin turno
     * @param fechaInicial
     * @param fechaFinal
     * @param indiceInicial
     * @param numeroResultados
     * @return
     * @throws DAOException 
     */
    public Map getSolicitudesSinTurno(Circulo circulo, Date fechaInicial, Date fechaFinal, String cadena, int indiceInicial, int numeroResultados) throws DAOException;
    
    /**
     * Elimina un subconjunto de las solicitudes sin turno
     * @param fechaInicial
     * @param fechaFinal
     * @param indiceInicial
     * @param numeroResultados
     * @return
     * @throws DAOException 
     */
    public boolean removeSolicitudesSinTurno(Circulo circulo, Date fechaInicial, Date fechaFinal, String cadena, int indiceInicial, int numeroResultados) throws DAOException;
    
    /**
     * @param fechaInicial
     * @param fechaFinal
     * @return
     * @throws DAOException
     */
    public long getNumeroSolicitudesSinTurno(Circulo circulo, Date fechaInicial, 
            Date fechaFinal) throws DAOException;
            
            
	/**
	* Este servicio permite modificar el valor de la última liquidación
	* asociada a un turno (Utilizado en el proceso de devoluciones).
	* @param turno El identificador del turno sobre el cual se va a modificar la última liquidación.
	* @param liquidacion La <code>Liquidacion</code> con los valores que van a ser modificados.
	* @return <code>true </code> o <code>false </code> dependiendo del resultado de la operación.
	* @throws <code>DAOException</code>
	* @see gov.sir.core.negocio.modelo.Solicitud
	* @see gov.sir.core.negocio.modelo.Liquidacion
	*/
	public boolean updateUltimaLiquidacion(TurnoPk turnoId, Liquidacion liquidacion) throws DAOException;

	
	/**
	 * Actualiza el valor de la liquidacion del turno de devoluciones
	 * @param liquidacion
	 * @return
	 * @throws DAOException
	 */
	public boolean updateValorLiquidacionDevolucion(Liquidacion liquidacion) throws DAOException;

        public boolean isIncentivoRegistral(Date fechaDocumento)  throws DAOException;
        
        public void deleteLiquidacionTurnoRegistro(String idLiquidacion, String idSolicitud) throws DAOException;
	
}
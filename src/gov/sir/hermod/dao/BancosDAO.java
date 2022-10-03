/*
 * BancosDAO.java
 * Interfaz para el manejo de los objetos de tipo Banco utilizados en la aplicacion.
 * Provee servicios para la consulta, modificación y adición de estos objetos.
 * Creado julio 12 de 2004, 9:10
 */
package gov.sir.hermod.dao;

import java.util.List;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.BancoPk;
import gov.sir.core.negocio.modelo.BancosXCirculo;
import gov.sir.core.negocio.modelo.BancosXCirculoPk;
import gov.sir.core.negocio.modelo.CanalesRecaudo;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.SucursalBanco;
import gov.sir.core.negocio.modelo.SucursalBancoPk;
import gov.sir.core.negocio.modelo.CuentasBancarias;
import gov.sir.core.negocio.modelo.CuentasBancariasPk;
import gov.sir.core.negocio.modelo.TipoPago;

/**
 * @author dlopez,mortiz Interfaz para el manejo de los objetos de tipo
 * <code>Banco</code> utilizados en la aplicacion.<p>
 * Provee servicios para la consulta, modificación y adición de estos objetos.
 * @see gov.sir.core.negocio.modelo.Banco
 * @see gov.sir.core.negocio.modelo.SucursalBanco
 */
public interface BancosDAO {

    /**
     * Obtiene una lista de objetos tipo <code>Banco </code>, organizada
     * ascendentemente según el id del banco.
     *
     * @return una lista de objetos <code>Banco</code>
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.Banco
     */
    List getBancos() throws DAOException;

    /**
     * Obtiene el objeto <code>Banco </code> según el id del banco.
     *
     * @return <code>Banco</code>
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.Banco
     */
    Banco getBancoByID(String idBanco) throws DAOException;

    /**
     * Obtiene un <code>Banco</code> dado su identificador.<p>
     * Retorna null, si no se encontró el <code>Banco</code> con el
     * identificador dado en la Base de Datos.
     *
     * @param bID identificador del banco.
     * @return Banco con todos sus atributos.
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Banco
     */
    Banco getBancoByID(BancoPk bID) throws DAOException;

    /**
     * Adiciona un <code>Banco<code> a la configuración del sistema.<p>
     * <p>
     * Se lanza una excepción en el caso en el que ya exista en la base de
     * datos, un <code>Banco</code> con el identificador pasado dentro del
     * parámetro.
     *
     * @param datos objeto <code>Banco</code> con sus atributos, incluido el
     * identificador.
     * @return identificador del Banco generado.
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.Banco
     */
    BancoPk addBanco(Banco datos) throws DAOException;

    /**
     * Adiciona una <code>SucursalBanco</code> a un <code>Banco</code> dentro de
     * la configuración del sistema. Se lanza una excepción en el caso en el que
     * ya exista en la base de datos, una <code>SucursalBanco</code> con el
     * identificador pasado dentro del parámetro.
     *
     * @param sucursal objeto <code>SucursalBanco</code> con sus atributos
     * @return identificador de la <code>SucursalBanco</code> generada.
     * @see gov.sir.core.negocio.modelo.Banco
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     */
    SucursalBancoPk addSucursal(SucursalBanco sucursal) throws DAOException;

    /**
     * Retorna un listado de las <code>SucursalesBanco</code> asociadas con un
     * <code>Banco</code>.
     * <p>
     * Se establece como criterio de ordenamiento el nombre de la <code>SucursalBanco<p>
     * .
     * @param bancoId identificador del <code>Banco</code> cuyas
     * <code>SucursalesBanco</code> van a ser listadas.
     * @return Lista con las <code>SucursalesBanco</code> asociadas con un
     * <code>Banco</code>.
     * @see gov.sir.core.negocio.modelo.Banco
     * @see gov.sir.core.negocio.modelo.SucursalBanco
     */
    List getSucursalesBanco(BancoPk bancoId) throws DAOException;

    /**
     * Adiciona una relación banco-círculo (SIR_OP_BANCOS_X_CIRCULO)
     *
     * @param bancoXCirculo
     * @return
     * @throws DAOException
     */
    BancosXCirculoPk addBancoCirculo(BancosXCirculo bancoXCirculo) throws DAOException;

    /**
     * Consulta los bancos configurados para un determinado círculo
     *
     * @return
     * @throws DAOException
     */
    List getBancosXCirculo(String idCirculo) throws DAOException;

    /**
     * Elimina un banco relacionado con un círculo (SIR_OP_BANCOS_X_CIRCULO)
     *
     * @param bancoXCirculo
     * @return
     * @throws DAOException
     */
    boolean eliminaBancoCirculo(BancosXCirculo bancoXCirculo) throws DAOException;

    /**
     * Método para activar un banco como principal (defecto) para una oficina
     *
     * @param bancoXCirculo
     * @return
     * @throws DAOException
     */
    boolean activarBancoPrincipal(BancosXCirculo bancoXCirculo) throws DAOException;

    /**
     * Consulta relación bancos círculo (SIR_OP_BANCOS_X_CIRCULO) para ser
     * cargada en contexto. Con ésta lista se cargan los bancos en las
     * diferentes modalidades de pago
     *
     * @return
     * @throws DAOException
     */
    List getRelacionBancosCirculo() throws DAOException;

    /**
     * Adiciona una relación circulo-banco-cuenta (SIR_OP_CUENTAS_BANCARIAS)
     *
     * @param cuentaBancaria
     * @return
     * @throws DAOException
     */
    CuentasBancariasPk addCuentaBancaria(CuentasBancarias cuentaBancaria) throws DAOException;

    /**
     * Consulta las cuentas bancarias configuradas para un determinado círculo y
     * banco
     *
     * @return
     * @throws DAOException
     */
    List getCuentasBancarias(String idCirculo, String idBanco) throws DAOException;

    /**
     * Consulta las cuentas bancarias configuradas para un determinado círculo
     *
     * @return
     * @throws DAOException
     */
    List getCuentasBancariasXCirculo(Circulo circulo) throws DAOException;

    /**
     * actualiza el estado para una cuenta bancaria de un circulo y banco en
     * especifico
     *
     * @return
     * @throws DAOException
     */
    void actualizarEstadoCtaBancaria(String idCirculo, String idBanco, String nroCuenta, boolean estado) throws DAOException;

    /**
     * Obtiene una lista de objetos tipo <code>CanalesRecaudo</code>
     *
     * @return una lista de objetos <code>CanalesRecaudo</code>
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     */
    List getCanalesRecaudo(boolean flag) throws DAOException;

    /**
     * Obtiene una lista de objetos tipo <code>CanalesRecaudo</code>
     *
     * @return una lista de objetos <code>CanalesRecaudo</code>
     * @param circulo
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     */
    List getCanalesRecaudoXCirculo(Circulo circulo) throws DAOException;

    /**
     * Adiciona un nuevo Canal de Recaudo <code>CanalesRecaudo</code>
     *
     * @return true o false dependiendo del resultado de la operación
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.CanalesRecaudo
     */
    boolean addCanalRecaudo(CanalesRecaudo canalesRecaudo) throws DAOException;

    /**
     * actualiza el estado para una canal de recaudo en especifico
     *
     * @return
     * @throws DAOException
     */
    void actualizarEstadoCanalRecaudo(int idCanal, boolean estado) throws DAOException;

    /**
     * Valida si para la forma de pago recibida esta activo o no el campo de
     * banco/franquicia
     *
     * @return true o false dependiendo del resultado de la operación
     * @throws DAOException.
     * @see gov.sir.core.negocio.modelo.TipoPago
     */
    boolean validaCampoBancoFranquicia(TipoPago tipoPago) throws DAOException;

    /**
     * Consulta las cuentas bancarias configuradas para un determinado círculo,
     * canal y forma pago
     *
     * @return
     * @throws DAOException
     */
    List getCuentasBancariasXCirculoCanalForma(Circulo circulo, String idCanalRecaudo, String idFormaPago) throws DAOException;
    
    /**
     * actualiza el estado para un registro en la tabla SIR_NE_CIRCULO_TIPO_PAGO
     *
     * @return
     * @throws DAOException
     */
    void actualizarEstadoCtp(String idCtp, boolean estado) throws DAOException;
}

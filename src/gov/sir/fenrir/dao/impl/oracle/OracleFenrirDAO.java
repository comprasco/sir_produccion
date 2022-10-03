package gov.sir.fenrir.dao.impl.oracle;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.EstacionRecibo;
import gov.sir.core.negocio.modelo.InfoUsuario;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.ArchivosJustifica;
import gov.sir.core.negocio.modelo.FormaPagoCampos;
import gov.sir.core.negocio.modelo.JustificaAdm;
import gov.sir.core.negocio.modelo.JustificaTipos;
import gov.sir.core.negocio.modelo.TramiteSuspension;
import gov.sir.core.negocio.modelo.TipoPago;
import gov.sir.core.negocio.modelo.UsuarioPk;
import gov.sir.core.negocio.modelo.jdogenie.ArchivosJustificaEnhanced;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.fenrir.FenrirProperties;
import gov.sir.fenrir.dao.DAOException;
import gov.sir.fenrir.dao.ErrorConexionException;
import gov.sir.fenrir.dao.FenrirDAO;

import gov.sir.fenrir.dao.UsuarioDuplicadoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.auriga.core.modelo.daoObjects.DAOFactory;
import org.auriga.core.modelo.daoObjects.EstacionDAO;
import org.auriga.core.modelo.daoObjects.NivelDAO;
import org.auriga.core.modelo.daoObjects.RolDAO;
import org.auriga.core.modelo.daoObjects.UsuarioDAO;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Nivel;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.pooldb.DBPoolConexiones;

import org.auriga.util.ExceptionPrinter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Es la implementacion del <code>FenrirDAO</code> para Oracle
 *
 * @author jfrias
 *
 */
public class OracleFenrirDAO implements FenrirDAO {

    private static DBPoolConexiones pool = null;
    private static String poolErrorMessage = "";

    /**
     * Constructor est�tico usado para inicializar el atributo DBPoolConexiones
     * pool.
     */
    static {
        FenrirProperties p = FenrirProperties.getInstancia();
        String driver = p.getProperty(OracleKeys.ORACLE_DRIVER);
        String jdbc = p.getProperty(OracleKeys.ORACLE_JDBC);
        String user = p.getProperty(OracleKeys.ORACLE_USER);
        String pass = p.getProperty(OracleKeys.ORACLE_PASS);
        String ini = p.getProperty(OracleKeys.ORACLE_POOL_INI);
        String max = p.getProperty(OracleKeys.ORACLE_POOL_MAX);

        try {
            int iniciales = 5;
            int maximas = 50;

            if ((ini != null) && (max != null)) {
                try {
                    iniciales = Integer.parseInt(ini);
                    maximas = Integer.parseInt(max);
                } catch (NumberFormatException e) {
                    Log.getInstance().error(OracleFenrirDAO.class, "Las propiedades de conexi�n a la base de datos son incorrectas");
                }
            }

            pool = new DBPoolConexiones("FENRIR", driver, jdbc, user, pass, iniciales, maximas, true, 3000);
        } catch (SQLException e) {
            poolErrorMessage
                    = "No se pudo establecer conexi�n con la base de datos.  \nCausa:" + e.getMessage();
            Log.getInstance().fatal(OracleFenrirDAO.class, poolErrorMessage);
        }
    }

    /**
     * Constructor por omisi�n del DAO
     */
    public OracleFenrirDAO() throws ErrorConexionException {
        if (pool == null) {
            throw new ErrorConexionException(poolErrorMessage);
        }
    }
    
    
    /**
     * Se hace la auditoria del login el usuario
     *
     * @param user el usuario al que se le va a agregar la auditoria
     * @return un numero entero que identifica al usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     * @throws UsuarioDuplicadoException cuando ya hay un usuario registrado con
     * el mismo nombre de usuario
     */
    
    public int agregarAuditoria(long id_usuario, InfoUsuario info) throws ErrorConexionException {
        /*
        PreparedStatement prepared = null;
        PreparedStatement prepared2 = null;
        PreparedStatement prepared3 = null;

        PreparedStatement prepared4 = null;
        PreparedStatement prepared5 = null;

        ResultSet rs1 = null;
        ResultSet rs2 = null;

        Log.getInstance().debug(this.getClass(), "SE USO ORACLEFENRIRDAO, NO SE DEBER�A");
       
        @Requerimiento: Secuencia SIR_OP_AUDITORIA
        Se agrega una secuencia p
        String conseguirSecuencia
                = "SELECT LAST_USED_ID FROM SIR_SECUENCIAS WHERE TABLE_NAME='SIR_OP_AUDITORIA'";
        String avanzarSecuencia
                = "UPDATE SIR_SECUENCIAS SET LAST_USED_ID = LAST_USED_ID + 1 WHERE TABLE_NAME = 'SIR_OP_AUDITORIA'";
       
        String insertarAuditoria = "INSERT INTO SIR_OP_AUDITORIA VALUES(?,?,SYSDATE,NULL,?,?,?)";
        String estaLogeado
                = "UPDATE SIR_OP_AUDITORIA SET FECHA_LOGOUT=SYSDATE WHERE ID_USUARIO=? AND FECHA_LOGOUT IS NULL";
        String getID
                = "SELECT ID_AUDITORIA FROM SIR_OP_AUDITORIA WHERE ID_USUARIO=? AND FECHA_LOGOUT IS NULL";
        int idAuditoria = -1;
        Connection conn = null;

        try {
            if (pool == null) {
                Log.getInstance().fatal(this.getClass(), "No se pudo instanciar el Pool de Conexiones");

                throw new ErrorConexionException(ErrorConexionException.POOL_NO_DISPONIBLE);
            }

            conn = pool.obtenerConexion();
            conn.setAutoCommit(false);
            if (conn == null) {
                Log.getInstance().fatal(this.getClass(), "No se pudo obtener conexi�n del Pool de Conexiones");

                throw new ErrorConexionException(ErrorConexionException.CONEXION_NO_DISPONIBLE);
            }

            prepared = conn.prepareStatement(estaLogeado);
            prepared2 = conn.prepareStatement(insertarAuditoria);
            prepared3 = conn.prepareStatement(getID);
//            prepared4 = conn.prepareStatement(avanzarSecuencia);
//            prepared5 = conn.prepareStatement(conseguirSecuencia);

            prepared.setLong(1, id_usuario);

            prepared.executeUpdate();
            //Se hace commit a conexion prepared
            prepared.getConnection().commit();
            
            prepared2.setLong(2, id_usuario);
            prepared2.setString(3, info.getUser());
            prepared2.setString(4, info.getHost());
            prepared2.setString(5, info.getAddress());

            int secuencia = -1;
            
            
            synchronized (OracleFenrirDAO.class) {
                prepared4.executeUpdate();
                //Se hace commit a conexion prepared4
                prepared4.getConnection().commit();
                conn.commit();
                if (prepared5.execute()) {
                    rs2 = prepared5.getResultSet();

                    if (rs2.next()) {
                        secuencia = rs2.getInt(1);
                    }
                }
                //Se hace commit a conexion prepared5
                prepared5.getConnection().commit();
                conn.commit();
            }
            
            prepared2.setInt(1, secuencia);

            prepared2.execute();
            //Se hace commit a conexion prepared2
            prepared2.getConnection().commit();
            conn.commit();

            prepared3.setLong(1, id_usuario);

            if (prepared3.execute()) {
                rs1 = prepared3.getResultSet();

                while (rs1.next()) {
                    idAuditoria = rs1.getInt(1);
                }

            }            
            //Se hace commit a conexion prepared3
            prepared3.getConnection().commit();
            conn.commit();
            
            //Se cierran las conexion del pool
//            prepared.getConnection().close();
//            prepared2.getConnection().close();
//            prepared3.getConnection().close();
//            prepared4.getConnection().close();
//            prepared5.getConnection().close();
            //conn.setAutoCommit(true);
            //pool.liberarConexion(conn);

        } catch (SQLException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error generando la auditor�a " + printer.toString());
            throw new ErrorConexionException(e.getMessage());
        } catch (Exception e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error generando la auditor�a " + printer.toString());
            throw new ErrorConexionException(e.getMessage());
        }finally {
            try {
                conn.setAutoCommit(true);
                if (rs1 != null) {
                    rs1.close();
                }

                if (rs2 != null) {
                    rs2.close();
                }
                if (prepared != null) {
                    prepared.close();
                }

                if (prepared2 != null) {
                    prepared2.close();
                }

                if (prepared3 != null) {
                    prepared3.close();
                }

                if (prepared4 != null) {
                    prepared4.close();
                }

                if (prepared5 != null) {
                    prepared5.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                Log.getInstance().error(this.getClass(), "Error generando la auditor�a " +e.getMessage());
            }
            pool.liberarConexion(conn);
        }
        return idAuditoria;
        */
        return 0;
    }

    
    /**
     * Se obtienen las estaciones a las que pertenece el usuario
     *
     * @param user el usuario del que se quieren saber sus estaciones
     * @return una lista con las estaciones a las que pertenece el usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darRolUsuario(long id_usuario) throws ErrorConexionException {
        Log.getInstance().debug(this.getClass(), "SE USO ORACLEFENRIRDAO, NO SE DEBER�A");
        List roles = new Vector();
        String username = "";
        String consultaUsuario = "SELECT USERNAME FROM SIR_OP_USUARIOS WHERE ID_USUARIO=?";

        Connection conn = null;
        PreparedStatement prepared2 = null;
        ResultSet rs = null;

        try {
            if (pool == null) {
                Log.getInstance().fatal(this.getClass(), "No se pudo instanciar el Pool de Conexiones");

                throw new ErrorConexionException(ErrorConexionException.POOL_NO_DISPONIBLE);
            }

            conn = pool.obtenerConexion();

            if (conn == null) {
                Log.getInstance().fatal(this.getClass(), "No se pudo obtener conexi�n del Pool de Conexiones");

                throw new ErrorConexionException(ErrorConexionException.CONEXION_NO_DISPONIBLE);
            }

            prepared2 = conn.prepareStatement(consultaUsuario);
            prepared2.setLong(1, id_usuario);

            if (prepared2.execute()) {
                rs = prepared2.getResultSet();

                while (rs.next()) {
                    username = rs.getString(1);
                }

            }

            DAOFactory fact = DAOFactory.getFactory();
            UsuarioDAO udao = fact.getUsuarioDAO();
            org.auriga.core.modelo.transferObjects.Usuario user = udao.consultarUsuario(username);

            RolDAO rolUsu = fact.getRolDAO();
            roles = rolUsu.obtenerRolesUsuario(user);

        } catch (SQLException e1) {
            ExceptionPrinter printer = new ExceptionPrinter(e1);
            Log.getInstance().error(this.getClass(), "Error listando roles: " + printer.toString());
            throw new ErrorConexionException(e1.getMessage());
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error listando roles: " + printer.toString());
            throw new ErrorConexionException(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prepared2 != null) {
                    prepared2.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            pool.liberarConexion(conn);
        }
        return roles;
    }

    /**
     * M�todo est�tico que libera el pool de conexiones
     */
    public void finalizar() {
        Log.getInstance().debug(this.getClass(), "SE USO ORACLEFENRIRDAO, NO SE DEBER�A");
        if ((pool != null) && (pool.totalConexiones() > 0)) {
            pool.cerrarConexiones();
        }
    }

    /**
     * Se registra el logout del usuario
     *
     * @param idLogin el identificador del usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public void hacerLogout(Integer idLogin) throws ErrorConexionException {
        Log.getInstance().debug(this.getClass(), "SE USO ORACLEFENRIRDAO, NO SE DEBER�A");
        String insertarLogout = "UPDATE SIR_OP_AUDITORIA SET FECHA_LOGOUT=SYSDATE WHERE ID_AUDITORIA=?";

        Connection conn = null;
        PreparedStatement prepared1 = null;

        try {
            if (pool == null) {
                Log.getInstance().fatal(this.getClass(), "No se pudo instanciar el Pool de Conexiones");

                throw new ErrorConexionException(ErrorConexionException.POOL_NO_DISPONIBLE);
            }

            conn = pool.obtenerConexion();

            if (conn == null) {
                Log.getInstance().fatal(this.getClass(), "No se pudo obtener conexi�n del Pool de Conexiones");

                throw new ErrorConexionException(ErrorConexionException.CONEXION_NO_DISPONIBLE);
            }

            prepared1 = conn.prepareStatement(insertarLogout);

            prepared1.setInt(1, idLogin.intValue());

            prepared1.executeUpdate();

        } catch (SQLException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error haciendo logout: " + printer.toString());
            throw new ErrorConexionException("Hubo un error en la conexion");
        } finally {

            try {
                if (prepared1 != null) {
                    prepared1.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(OracleFenrirDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            pool.liberarConexion(conn);
        }
    }

    /**
     * Se obtiene el id del usuario ingresado
     *
     * @param username el Username del usuario
     * @return el id del usuario
     */
    public long getIdUsuario(String username) throws ErrorConexionException {
        Log.getInstance().debug(this.getClass(), "SE USO ORACLEFENRIRDAO, NO SE DEBER�A");
        String consultaID = "SELECT ID_USUARIO FROM SIR_OP_USUARIOS WHERE USERNAME=?";
        long idUsuario = -1;

        Connection conn = null;
        PreparedStatement prepared1 = null;
        if (pool == null) {
            Log.getInstance().fatal(this.getClass(), "No se pudo instanciar el Pool de Conexiones");

            throw new ErrorConexionException(ErrorConexionException.POOL_NO_DISPONIBLE);
        }

        try {
            conn = pool.obtenerConexion();

            if (conn == null) {
                Log.getInstance().fatal(this.getClass(), "No se pudo obtener conexi�n del Pool de Conexiones");

                throw new ErrorConexionException(ErrorConexionException.CONEXION_NO_DISPONIBLE);
            }

            prepared1 = conn.prepareStatement(consultaID);

            prepared1.setString(1, username);

            if (prepared1.execute()) {
                ResultSet rs = prepared1.getResultSet();

                while (rs.next()) {
                    idUsuario = rs.getInt(1);
                }

                rs.close();
            }

        } catch (SQLException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consiguiendo ID: " + printer.toString());
            throw new ErrorConexionException("Hubo un error en la conexion");
        } finally {
            try {
                if (prepared1 != null) {
                    prepared1.close();
                }
            } catch (SQLException e) {
            }
            pool.liberarConexion(conn);
        }
        return idUsuario;
    }

    /**
     * Se obtienen las estaciones a las que pertenece un usuario
     *
     * @param id el usuario del que se quieren sus roles
     * @return una lista con los roles a los que pertenece el usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darEstacionUsuario(long id, Rol rol) throws ErrorConexionException {
        String username = "";
        String consultaUsuario = "SELECT USERNAME FROM SIR_OP_USUARIOS WHERE ID_USUARIO=?";
        Connection conn = null;
        PreparedStatement prepared2 = null;
        ResultSet rs = null;
        List esta = new Vector();

        try {
            if (pool == null) {
                Log.getInstance().fatal(this.getClass(), "No se pudo instanciar el Pool de Conexiones");

                throw new ErrorConexionException(ErrorConexionException.POOL_NO_DISPONIBLE);
            }

            conn = pool.obtenerConexion();

            if (conn == null) {
                Log.getInstance().fatal(this.getClass(), "No se pudo obtener conexi�n del Pool de Conexiones");
                throw new ErrorConexionException(ErrorConexionException.CONEXION_NO_DISPONIBLE);
            }

            prepared2 = conn.prepareStatement(consultaUsuario);
            prepared2.setLong(1, id);

            if (prepared2.execute()) {
                rs = prepared2.getResultSet();

                while (rs.next()) {
                    username = rs.getString(1);
                }

            }

            //						SE VA A OBTENER EL USUARIO DE AURIGA A PARTIR DEL USUARIO SIR
            DAOFactory fact = DAOFactory.getFactory();
            UsuarioDAO udao = fact.getUsuarioDAO();
            org.auriga.core.modelo.transferObjects.Usuario user = udao.consultarUsuario(username);

            EstacionDAO estUsu = fact.getEstacionDAO();

            esta = estUsu.obtenerEstacionesUsuario(user);
        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consiguiendo estaciones: " + printer.toString());
            throw new ErrorConexionException("Hubo un error en la conexion");
        } catch (SQLException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consiguiendo estaciones: " + printer.toString());
            throw new ErrorConexionException("Hubo un error en la conexion");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prepared2 != null) {
                    prepared2.close();
                }
            } catch (SQLException e) {
            }
            pool.liberarConexion(conn);
        }

        return esta;
    }

    /**
     * Se obtienen las estaciones a las que pertenece un usuario
     *
     * @param id el usuario del que se quieren sus roles
     * @return una lista con los roles a los que pertenece el usuario
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darEstacionesUsuario(long id) throws ErrorConexionException {
        return null;
    }

    /**
     * M�todo para consultar el c�rculo asociado a una <code>Estaci�n</code>
     * determinada
     *
     * @param estaci�n
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public Circulo darCirculoEstacion(Estacion estacion) throws ErrorConexionException {
        Circulo ef = null;

        Connection conn = null;
        PreparedStatement prepared = null;
        ResultSet rs = null;

        try {
            DAOFactory fact = DAOFactory.getFactory();
            NivelDAO nivelDao = fact.getNivelDAO();
            List niveles = nivelDao.obtenerNivelesEstacion(estacion);
            Nivel nivel = (Nivel) niveles.get(0);
            String idCirculo = nivel.getAtributo1();
            String consultaUsuario
                    = "SELECT LAST_NO_MATRICULA, NOMBRE FROM SIR_NE_CIRCULO WHERE ID_CIRCULO = ?";

            if (pool == null) {
                Log.getInstance().error(this.getClass(), "No se pudo instanciar el Pool de Conexiones");

                throw new ErrorConexionException(ErrorConexionException.POOL_NO_DISPONIBLE);
            }

            conn = pool.obtenerConexion();

            if (conn == null) {
                Log.getInstance().error(this.getClass(), "No se pudo obtener conexi�n del Pool de Conexiones");

                throw new ErrorConexionException(ErrorConexionException.CONEXION_NO_DISPONIBLE);
            }

            prepared = conn.prepareStatement(consultaUsuario);

            prepared.setString(1, idCirculo);
            if (prepared.execute()) {
                rs = prepared.getResultSet();
                String nombre = null;
                long last = -1;
                while (rs.next()) {
                    last = rs.getLong(1);
                    nombre = rs.getString(2);
                }
                ef.setIdCirculo(idCirculo);
                ef.setLastNoMatricula(last);
                ef.setNombre(nombre);

            }

        } catch (org.auriga.core.modelo.daoObjects.DAOException e) {

        } catch (SQLException e) {
            ExceptionPrinter printer = new ExceptionPrinter(e);
            Log.getInstance().error(this.getClass(), "Error consiguiendo estaciones: " + printer.toString());
            throw new ErrorConexionException("Hubo un error en la conexion");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prepared != null) {
                    prepared.close();
                }
            } catch (SQLException e) {
            }
            pool.liberarConexion(conn);
        }

        return ef;
    }

    /**
     * Consulta los usuarios Asociados con un Rol
     *
     * @param rol
     * @throws ErrorConexionException cuando hay un error en la conexion
     */
    public List darUsuariosRol(Rol rol) throws ErrorConexionException {
        //		TODO 
        return null;
    }

    /**
     * Consulta los roles existentes en el sistema
     *
     * @return la lista de los roles existentes en el sistema
     * @throws DAOException
     */
    public List consultarRoles() throws DAOException {
        return null;
    }

    /**
     * Consulta las responsabilidades existentes en el sistema
     *
     * @return la lista de las responsabilidades existentes en el sistema
     * @throws DAOException
     */
    public List consultarResponsabilidades() throws DAOException {
        return null;
    }

    /**
     * Obtiene una Lista, con los usuarios disponibles en el sistema
     * <code>Usuario</code> asociados.
     *
     * @return Lista con los usuarios disponibles en el sistema
     * @param nombreUsuario del usuario a ser consultado. Si este par�metro es
     * null retorna una lista con todos los usuarios
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.Usuario
     */
    public List consultarUsuarios(String nombreUsuario) throws DAOException {
        return null;
    }
    
    //consultarRespuestasUsuarios
    public List consultarRespuestasUsuarios(int idUsuario, String turno) throws DAOException {
        return null;
    }
     
    
    public void eliminarTramiteSuspensionTurno(int idUsuario, String turno) throws DAOException {
        
    }
    
    /**
     * Obtiene una Lista, con las justificaciones por usuario disponibles en el sistema
     * <code>JustificaAdm</code> asociados.
     *
     * @return Lista con las justificaciones por usuario disponibles en el sistema
     * @param idUsuario del usuario a ser consultado. Si este par�metro es
     * @param fechaIni
     * @param fechaFin
     * null retorna una lista con todos los usuarios
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.JustificaAdm
     */
    public List consultarJustificacionesUsuarios(int idUsuario, String fechaIni, String fechaFin) throws DAOException {
        return null;
    }
    
    /**
     * Obtiene una Lista, con los tipos de justificaciones por tipo
     * <code>JustificaTipos</code> asociados.
     *
     * @return Lista con los tipos de justificaciones
     * @param tipoJust
     * null retorna una lista con todos los tipos de justificaciones
     * @throws DAOException
     * @see gov.sir.core.negocio.modelo.JustificaTipos
     */
    public List consultarTiposJustificaciones(String tipoJust) throws DAOException {
        return null;
    }

    /**
     * Crea un <code>Rol</code> en el sistema
     *
     * @param rol  <code>Rol</code> a ser creado
     * @param Usuario que crea el rol
     * @return lista actualizada de los roles existentes en el sistema
     * @throws DAOException
     */
    public List crearRol(Rol rol, Usuario usuario) throws DAOException {
        return null;
    }

    /**
     * Consulta las estaciones existentes en el sistema
     *
     * @return la lista de las estaciones existentes en el sistema
     * @throws DAOException
     */
    public List consultarEstaciones() throws DAOException {
        return null;
    }

    /**
     * Consulta los niveles existentes en el sistema
     *
     * @return la lista de los niveles existentes en el sistema
     * @throws DAOException
     */
    public List consultarNiveles() throws DAOException {
        return null;
    }

    /**
     * Crea un <code>Nivel</code> en el sistema
     *
     * @param rol  <code>Nivel</code> a ser creado
     * @return lista actualizada de los niveles existentes en el sistema
     * @throws DAOException
     */
    public List crearNivel(Nivel nivel) throws DAOException {
        return null;
    }

    /**
     * Elimina un <code>Nivel</code> del sistema
     *
     * @param rol  <code>Nivel</code> a ser eliminado
     * @return lista actualizada de los niveles existentes en el sistema
     * @throws DAOException
     */
    public List eliminarNivel(Nivel nivel) throws DAOException {
        return null;
    }

    /**
     * Consulta las estaciones relacionadas con un <code>Circulo</code>
     * determinado
     *
     * @param circulo el <code>Circulo</code> del cual se requiere consultar sus
     * estaciones
     * @return la lista de las estaciones relacionadas con el
     * <code>Circulo</code> dado como par�metro
     * @throws DAOException
     */
    public List consultarEstacionesPorCirculo(Circulo circulo) throws DAOException {
        return null;
    }

    /**
     * Consulta los horarios de trabajo existentes en el sistema
     *
     * @return la lista de los horarios de trabajo
     * @throws DAOException
     */
    public List consultarHorarios() throws DAOException {
        return null;
    }

    /**
     * Consulta la lista de usuarios del sistema dado un Circulo y un Rol
     *
     * @param circulo  <code>Circulo</code> a ser consultado
     * @param rol  <code>Rol</code> a ser consultado
     * @return lista de usuarios que cumplan la condici�n
     * @throws DAOException
     */
    public List consultarUsuariosPorCirculoRol(Circulo circulo, Rol rol) throws DAOException {
        return null;
    }

    /**
     * @author Daniel Forero
     * @change REQ 1156 - Filtro de estaciones activas por rol y usuario.
     *
     * (non-Javadoc)
     * @see
     * gov.sir.fenrir.dao.FenrirDAO#consultarUsuariosPorCirculoRolByEstado(gov.sir.core.negocio.modelo.Circulo,
     * org.auriga.core.modelo.transferObjects.Rol, boolean)
     */
    public List consultarUsuariosPorCirculoRolByEstado(Circulo circulo, Rol rol, boolean estado) throws DAOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Consulta la lista de usuarios del sistema dado un Circulo
     *
     * @param circulo  <code>Circulo</code> a ser consultado
     * @return lista de usuarios que cumplan la condici�n
     * @throws DAOException
     */
    public List consultarUsuariosPorCirculo(Circulo circulo) throws DAOException {
        // TODO 
        return null;
    }

    /**
     * Habilita o deshabilita un  <code>Usuario</code> seg�n si su propiedad
     * activo es true o false.consultarRespuestasUsuarios
     *
     * @param roles  <code>Usuario</code> con la nueva clave
     * @return boolean true si se pudo cambiar el password
     * @throws DAOException
     */
    public void habilitarUsuario(Usuario usuario) throws DAOException {
        // TODO Auto-generated method stub 

    }

    /**
     * Actualiza un  <code>Usuario</code> en el modelo operativo, esta
     * actualizaci�n incluye el nombre, apellido1 y apellido2
     *
     * @param usuario  <code>Usuario</code> con los nuevos atributos del mismo
     * @return void
     * @throws DAOException
     */
    public void actualizarUsuario(Usuario usuario) throws DAOException {
        // TODO Auto-generated method stub

    }

    /**
     * Crea un registro <code>ArchivosJustifica</code> en el modelo operativo
     *
     * @param archivo <code>ArchivosJustifica</code> con los nuevos atributos
     * del mismo
     * @throws DAOException
     */
    public ArchivosJustifica nuevoArchivo(ArchivosJustifica archivo) throws DAOException {
        return null;
    }
    
    /**
     * Crea un registro <code>TramiteSuspension</code> en el modelo operativo
     *
     * @param tramSuspension <code>TramiteSuspension</code> con los nuevos atributos
     * @param infoArchivo <code>ArchivosJustifica</code> con los nuevos atributos  
     * del mismo
     * @return void
     * @throws DAOException
     */
    public void crearRespuestasUsuarios(TramiteSuspension tramSuspension, ArchivosJustifica infoArchivo) throws DAOException {

    }

    /**
     * Crea un registro <code>JustificaAdm</code> en el modelo operativo
     *
     * @param justificaAdm <code>JustificaAdm</code> con los nuevos atributos
     * @param infoArchivo <code>ArchivosJustifica</code> con los nuevos atributos   
     * @param justificaTipos <code>JustificaTipos</code> con los nuevos atributos   
     * del mismo
     * @return void
     * @throws DAOException
     */
    public void crearJustificaAdm(JustificaAdm justificaAdm, ArchivosJustifica infoArchivo, JustificaTipos justificaTipos) throws DAOException {

    }

    /**
     *
     */
    public Usuario getUsuario(org.auriga.core.modelo.transferObjects.Usuario usuario)
            throws DAOException {

        return null;
    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#getUsuarioByID(gov.sir.core.negocio.modelo.Usuario.ID)
     */
    public Usuario getUsuarioByID(UsuarioPk bID) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#getRecibosEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public EstacionRecibo getRecibosEstacion(Estacion estacion) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#getRolesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getRolesEstacion(Estacion estacion) throws ErrorConexionException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#loadRolesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public void loadRolesEstacion(Estacion estacion) throws ErrorConexionException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#getUsuariosEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getUsuariosEstacion(Estacion estacion) throws ErrorConexionException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#getRolesPotencialesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getRolesPotencialesEstacion(Estacion estacion) throws ErrorConexionException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#getUsuariosPotencialesEstacion(org.auriga.core.modelo.transferObjects.Estacion)
     */
    public List getUsuariosPotencialesEstacion(Estacion estacion) throws ErrorConexionException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#addRolEstacion(org.auriga.core.modelo.transferObjects.Estacion, org.auriga.core.modelo.transferObjects.Rol, Usuario usuario)
     */
    public void addRolEstacion(Estacion estacion, Rol rol, Usuario usuario) throws DAOException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#removeRolEstacion(org.auriga.core.modelo.transferObjects.Estacion, org.auriga.core.modelo.transferObjects.Rol)
     */
    public void removeRolEstacion(Estacion estacion, Rol rol) throws DAOException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#addRolUsuarioEstacion(org.auriga.core.modelo.transferObjects.Estacion, org.auriga.core.modelo.transferObjects.Rol, gov.sir.core.negocio.modelo.Usuario)
     */
    public void addRolUsuarioEstacion(Estacion estacion, Rol rol, Usuario usuario) throws DAOException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#removeRolUsuarioEstacion(org.auriga.core.modelo.transferObjects.Estacion, org.auriga.core.modelo.transferObjects.Rol, gov.sir.core.negocio.modelo.Usuario)
     */
    public void removeRolUsuarioEstacion(Estacion estacion, Rol rol, Usuario usuario) throws DAOException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#getEstacion(gov.sir.core.negocio.modelo.Usuario, org.auriga.core.modelo.transferObjects.Rol)
     */
    public Estacion getEstacion(Usuario usuario, Rol rol) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#getEstacion(gov.sir.core.negocio.modelo.Usuario, org.auriga.core.modelo.transferObjects.Rol)
     */
    public List getEstaciones(Usuario usuario, Rol rol) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#getEstacion(gov.sir.core.negocio.modelo.Usuario, org.auriga.core.modelo.transferObjects.Rol)
     */
    public List getEstacionesUsuarioByEstadoRol(Usuario usuario, Rol rol, boolean estado) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
	 * @see gov.sir.fenrir.dao.FenrirDAO#getEstacionByEstado(gov.sir.core.negocio.modelo.Usuario, org.auriga.core.modelo.transferObjects.Rol, boolean estado)
     */
    public Estacion getEstacionByEstado(Usuario usuario, Rol rol, boolean estado) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public Rol getRolByID(String idRol) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public void actualizarRol(Rol rol, Usuario usuario) throws DAOException {
        // TODO Auto-generated method stub

    }

    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioByUsername(String username) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List getEstacionesRol(Rol rol) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List getEstacionesRolByCirculo(Rol rol, Circulo circulo) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List obtenerEstacionesUsuarioByEstadoRol(Rol rol, Circulo circulo, boolean activo) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List getEstacionesCirculo(Circulo circulo) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public void agregarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema) throws DAOException {
        // TODO Auto-generated method stub

    }

    public void eliminarEstacionRolUsuario(Estacion estacion, Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario) throws DAOException {
        // TODO Auto-generated method stub

    }

    public void agregarRolUsuario(Rol rol, org.auriga.core.modelo.transferObjects.Usuario usuario, Usuario usuarioSistema) throws DAOException {
        // TODO Auto-generated method stub

    }

    public void agregarEstacionRol(Estacion estacion, Rol rol, Usuario usuario) throws DAOException {
        // TODO Auto-generated method stub

    }

    public void eliminarEstacionRol(Estacion estacion, Rol rol) throws DAOException {
        // TODO Auto-generated method stub

    }

    public List consultarUsuariosRolEstacion(Rol rol, Estacion estacion) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public org.auriga.core.modelo.transferObjects.Usuario getUsuarioNRelationsByUsername(String username) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    /*public List consultarTurnosRolEstacion(Rol rol, Estacion estacion) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}*/
 /*public List determinarTurnosUsuario(Rol rol, Estacion estacion)throws DAOException{
		// TODO Auto-generated method stub
		return null;
	}*/
    public List consultarEstacionesRolByEstado(Rol rol, boolean estado) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public void actualizarEstadoEstacionRol(Estacion estacion, Rol rol, Circulo circulo, boolean estado, Usuario usuario) throws DAOException {
        // TODO Auto-generated method stub
    }

    public List getEstacionesActivasRolByCirculo(Rol rol, Circulo circulo)
            throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    public void trasladarUsuarioCirculo(Usuario usuario, Circulo circulo) throws DAOException {
        // TODO Auto-generated method stub

    }

    public boolean crearUsuario(Usuario usuario, List roles, Usuario usuarioSistema) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Consulta la lista de usuarios del sistema dado un Circulo y roles dados
     *
     * @param circulo  <code>Circulo</code> a ser consultado
     * @param rol  <code>Rol</code> a ser consultado
     * @return lista de usuarios que cumplan la condici�n
     * @throws DAOException
     */
    public List consultarUsuariosPorCirculoRoles(Circulo circulo, List roles) throws DAOException {
        return null;
    }

    /**
     * Author: Santiago V�squez Change: 1156.111.USUARIOS.ROLES.INACTIVOS
     * Validaci�n de roles no configurados
     */
    @Override
    public boolean hayEstacionAsociadoARolPorCirculo(Circulo circulo, Rol rol, Estacion estacion) throws DAOException {
        throw new UnsupportedOperationException();
    }    
    
    /**
     * Consulta la lista de campos captura dada una forma de pago
     *
     * @param tipoPago  <code>TipoPago</code> a ser consultado
     * @return lista de campos captura que cumplan la condici�n
     * @throws DAOException
     */
    public List consultarCamposCaptura(TipoPago tipoPago) throws DAOException {
        // TODO 
        return null;
    }
    
    /**
     * Actualiza la realcion campos captura y forma pago en la tabla
     * SIR_OP_REL_FPAGO_CAMPOS
     *
     * @param formaPagoCampos  <code>FormaPagoCampos</code> a ser actualizado
     * @return true o false dependiendo resultado operaci�n
     * @throws DAOException
     */
    public boolean actualizarCamposCaptura(FormaPagoCampos formaPagoCampos) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * Consulta la lista de campos captura del sistema
     *
     * @return lista de campos captura del sistema
     * @throws DAOException
     */
    public List cargarCamposCaptura() throws DAOException {
        // TODO 
        return null;
    }
    
    /**
     * Crea una nueva forma pago en la tabla
     * SIR_OP_TIPO_DOC_PAGO
     *
     * @param nombreFormaPago  <code>String</code> a ser creado
     * @return objeti TipoPago
     * @throws DAOException
     */
    public TipoPago crearFormaPago(String nombreFormaPago) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

}

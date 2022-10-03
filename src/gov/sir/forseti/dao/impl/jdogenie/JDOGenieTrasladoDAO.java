/*
 * JDOGenieTrasladoDAO.java
 *
 * Created on 21 de enero de 2005, 16:22
 */

package gov.sir.forseti.dao.impl.jdogenie;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import gov.sir.core.negocio.modelo.util.Log;

import com.versant.core.jdo.VersantPersistenceManager;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CComplementacion;
import gov.sir.core.negocio.modelo.constantes.CEstadoAnotacion;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CSecuencias;
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.AnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CancelacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ComplementacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ComplementacionTMP;
import gov.sir.core.negocio.modelo.jdogenie.ComplementacionTMPPk;
import gov.sir.core.negocio.modelo.jdogenie.DireccionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.DocumentoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EstadoAnotacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.EstadoFolioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.FolioDerivadoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.NaturalezaJuridicaEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.SalvedadAnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.SalvedadFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoAnotacionEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TipoPredioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoAnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadAnotacionEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TurnoSalvedadFolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.UsuarioEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.ZonaRegistralEnhancedPk;

import gov.sir.forseti.dao.DAOException;
import gov.sir.forseti.dao.TrasladoDAO;


/**
 *
 * @author  jalvarez
 */
public class JDOGenieTrasladoDAO implements TrasladoDAO {
    
    /** Creates a new instance of JDOGenieTrasladoDAO */
    public JDOGenieTrasladoDAO() {
    }
    
    public List consultarTrasladosMatricula(FolioPk idFolio) throws DAOException {
        return null;
    }
    
    public Folio trasladarFolio(Folio folioOrigen, Folio folioDestino, Usuario us, String resolucion) throws DAOException {
        
        Folio folioTrasladado = null;
        PersistenceManager pm = AdministradorPM.getPM();
        //boolean flagPKManual = false;
        
		//FolioEnhanced fDestinoPersistente = null;
		FolioEnhanced fDestino = null;
        
        UsuarioEnhanced usuario;

        UsuarioEnhancedPk usId = new UsuarioEnhancedPk();
        usId.idUsuario = us.getIdUsuario();

        JDOGenieFolioDAO fDao = new JDOGenieFolioDAO();
        
        ZonaRegistralEnhanced zrh = ZonaRegistralEnhanced.enhance(folioDestino.getZonaRegistral());
        
        try {
        
            pm.currentTransaction().setOptimistic(false);
            pm.currentTransaction().begin();

            // Crear un nuevo registro en la tabla de folios, en estado abierto, 
            // con toda la información del folio origen, con un nuevo número de matrícula
            fDestino = fDao.getFolioByMatricula(folioOrigen.getIdMatricula(),pm);
			
            fDao.makeTransientFolio(fDestino, pm);
            fDestino.setIdMatricula(folioDestino.getIdMatricula());
            fDestino.setZonaRegistral(zrh);

            usuario = fDao.getUsuarioByID(usId, pm);

            this.crearFolio(fDestino, usuario, pm, fDao);
            
            FolioEnhanced fOrigen = fDao.getFolioByMatricula(folioOrigen.getIdMatricula(),pm);

            // Registrar el traslado en la tabla histórica
            TrasladoEnhanced traslado = new TrasladoEnhanced();
            traslado.setFolioOrigen(fOrigen);
            /**
              * @author      :  Julio Alcazar
              * @change      :  cambio realizado para mantener la compilacion del codigo debido a cambios del modelo
              *                 el metodo no se esta usando actualmente en el proceso
              * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
              */
            traslado.setFolioDestino(fDestino.getIdMatricula());
            traslado.setFechaTraslado(new Date());
            traslado.setIdTraslado(String.valueOf(fDao.getSecuencial(CSecuencias.TRASLADO, null)));
            traslado.setResolucion(resolucion);
            // TODO: Establecer la resolución que soporta al traslado.
            pm.makePersistent(traslado);
            
            EstadoFolioEnhancedPk estadoID = new EstadoFolioEnhancedPk();
            estadoID.idEstado = CEstadoFolio.TRASLADADO;
            EstadoFolioEnhanced estadoFolio = fDao.getEstadoFolio(estadoID, pm);
            
            fOrigen.setEstado(estadoFolio);
            
            pm.currentTransaction().commit();
            
            fDao.makeTransientFolio(fDestino, pm);
            
        } catch (JDOObjectNotFoundException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
		    Log.getInstance().error(JDOGenieTrasladoDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (DAOException e) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieTrasladoDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        } catch (Throwable t) {
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            Log.getInstance().error(JDOGenieTrasladoDAO.class, t.getMessage());
            throw new DAOException(t.getMessage(), t);
        } finally {
            pm.close();
        }
        
        folioTrasladado = (Folio)fDestino.toTransferObject();
        
        return folioTrasladado;
    }

    
    private boolean crearFolio(FolioEnhanced datos, UsuarioEnhanced usuario,
        PersistenceManager pm, JDOGenieFolioDAO fDao) throws DAOException {
        JDOGenieZonaRegistralDAO zonaRegistralDAO = new JDOGenieZonaRegistralDAO();
        //FolioEnhanced.ID rta = new FolioEnhanced.ID();
        FolioEnhanced val = null;
        boolean flagPKManual = false;
        VersantPersistenceManager pm2 = null;

        CirculoEnhanced cir;
        String idCirculo = "";

        try {
            ZonaRegistralEnhanced zr;

            //Si viene el número de matrícula se valida que no exista un folio
            //con el mismo número
            if ((datos.getIdMatricula() != null)) {
                flagPKManual = true;

                val = fDao.getFolioByMatriculaIncluyendoObsoletos(datos.getIdMatricula(),
                        pm);

                if (val != null) {
                    if (val.getEstado().getIdEstado().equals(CEstadoFolio.OBSOLETO)) {
                        throw new DAOException(
                            "El número de matrícula especificado ya fue utilizado para un folio");
                    } else if (!val.isDefinitivo()) {
                        throw new DAOException(
                            "El número de matrícula ya fue utilizado para un folio temporal");
                    } else {
                        throw new DAOException(
                            "El folio con el número de matrícula especificado ya existe");
                    }
					//flagPKManual = false;
                }
            }

            //Se valida que el folio tenga zona registral
            if (datos.getZonaRegistral() == null) {
                throw new DAOException(
                    "No existe una zona registral asociado al folio ");
            }

            //Se valida y asocia la zona registral, si existe un ID de zona
            //registral se busca por el id, en caso que no exista se busca
            //la zona registral por el círculo y la vereda
            if (datos.getZonaRegistral().getIdZonaRegistral() != null) {
                ZonaRegistralEnhancedPk zrId = new ZonaRegistralEnhancedPk();
                zrId.idZonaRegistral = datos.getZonaRegistral()
                                            .getIdZonaRegistral();
                zr = zonaRegistralDAO.getZonaRegistral(zrId, pm);

                if (zr == null) {
                    throw new DAOException(
                        "No encontró la zona registral con el ID: " +
                        zrId.idZonaRegistral);
                }
            } else {
                zr = zonaRegistralDAO.getZonaRegistralByCirculoVereda(datos.getZonaRegistral()
                                                                           .getCirculo(),
                        datos.getZonaRegistral().getVereda(), pm);

                if (zr == null) {
                    throw new DAOException(
                        "No encontró la zona registral del circulo y vereda especificados");
                }
            }

            //Lo primero que se necesita leer es el círculo con el fin
            //de bloquear el registro para obtener el secuencial. Esto se hace
            //sólamente si el número de matrícula es autogenerado
            if (!flagPKManual) {
                //Se hace el cambio de tipo de bloqueo pesimista para
                //que se bloquee la tabla de circulo la cual  nos
                //provee el secuencial
                pm2 = (VersantPersistenceManager) pm;
                try{
                    pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_ALL);
                    pm = pm2;

                    CirculoEnhancedPk cid = new CirculoEnhancedPk();
                    cid.idCirculo = zr.getCirculo().getIdCirculo();
                    cir = zonaRegistralDAO.getCirculo(cid, pm);

                    if (cir == null) {
                            pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                        throw new DAOException(
                            "El círculo de la zona registral no existe");
                    }

                    idCirculo = cid.idCirculo;

                    //Volvemos a setear el tipo de bloqueo pesimista
                    //para que no nos bloquee los siquientes registros
                    //consultados
                    pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
                }catch (Exception e) {	
			pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
			if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
			}
			Log.getInstance().error(this.getClass(),e.getMessage());
			throw new DAOException(e.getMessage(), e);
		}

                //Se busca el siguiente número de matricula para 
                //el nuevo folio dentro del circulo dado
                long cons = cir.getLastNoMatricula() + 1;
                boolean flag = true;
                String matAux;

                while (flag) {
                    matAux = cir.getIdCirculo() + "-" + String.valueOf(cons);

                    if (fDao.getFolioByMatricula(matAux, pm) == null) {
                        flag = false;
                    } else {
                        cons++;
                    }
                }

                datos.setIdMatricula(cir.getIdCirculo() + "-" +
                    String.valueOf(cons));
                cir.setLastNoMatricula(cons);
            } else {
                StringTokenizer st = new StringTokenizer(datos.getIdMatricula(),
                        "-");
                String cirID = st.nextToken();
                String secuencial = st.nextToken();

                long sec = Long.parseLong(secuencial);

                if ((zr.getCirculo().getLastNoMatricula() + 1) < sec) {
                    throw new DAOException(
                        "El número de matrícula especificado supera la secuencia del círculo");
                } else if ((zr.getCirculo().getLastNoMatricula() + 1) == sec) {
                    zr.getCirculo().setLastNoMatricula(zr.getCirculo()
                                                         .getLastNoMatricula() + 1);
                }

                if (!cirID.equals(datos.getZonaRegistral().getCirculo()
                                           .getIdCirculo())) {
                    throw new DAOException(
                        "El identificador del círculo de la matrícula no coincide con el círculo especificado en la zona registral");
                }
                
                idCirculo = cirID;
            }

            //Se asocia la zona registral al folio
            datos.setZonaRegistral(zr);
            
            datos.setCirculo(idCirculo);

            //Se valida y asocia el estado del folio por defecto (ACTIVO)
            EstadoFolioEnhanced ef;
            EstadoFolioEnhancedPk efId = new EstadoFolioEnhancedPk();
            efId.idEstado = CEstadoFolio.ACTIVO;
            ef = fDao.getEstadoFolio(efId, pm);

            if (ef == null) {
                throw new DAOException(
                    "No encontró el estado de folio con el ID: " +
                    efId.idEstado);
            }

            datos.setEstado(ef);

            //Se valida y asocia el tipo de predio
            TipoPredioEnhanced tp = datos.getTipoPredio();

            if (tp == null) {
                throw new DAOException(
                    "No existe un tipo de predio asociado con el folio");
            }

            TipoPredioEnhancedPk tpId = new TipoPredioEnhancedPk();
            tpId.idPredio = tp.getIdPredio();
            tp = fDao.getTipoPredio(tpId, pm);

            if (tp == null) {
                throw new DAOException(
                    "No encontró el tipo de predio con el ID: " +
                    tpId.idPredio);
            }

            datos.setTipoPredio(tp);

            //Se valida y asocia la complementación, si la complementación
            //ya existe en la base de datos se asocia, si no existe se crea
            //la complementación temporal
            ComplementacionEnhanced com = datos.getComplementacion();

            if (com != null) {
                //Si viene una complementacion:
                ComplementacionEnhancedPk comID = new ComplementacionEnhancedPk();
                comID.idComplementacion = com.getIdComplementacion();

                ComplementacionEnhanced comAux = fDao.getComplementacion(comID,
                        pm);

                if (comAux != null) {
                    //La complementación se debe asociar, sin embargo se 
                    //debe buscar si la complementacion ya se encuentra
                    //en estado temporal
                    //TODO
                    ComplementacionTMPPk cidTMP = new ComplementacionTMPPk();
                    cidTMP.idComplementacionTmp = com.getIdComplementacion();

                    ComplementacionTMP temp = fDao.getComplementacionTMP(cidTMP,
                            pm);

                    if (temp != null) {
                        //ya existe una complementación temporal para la complementación dada
                        //Se setea la complementación temporal
                        temp.setComplementacion(datos.getComplementacion()
                                                     .getComplementacion());
                        datos.setComplementacionTMP(temp);
                    } else {
                        //Se crea la complementación temporal con la información actual
                        datos.setComplementacionTMP(new ComplementacionTMP(
                                comAux));
                    }
                } else {
                    //La complementación se debe crear
                    com.setComplementacion(datos.getComplementacion()
                                                .getComplementacion());

                    //Se obtiene y se bloquea la tabla que provee la secuencia de complementacion
                    com.setIdComplementacion(String.valueOf(fDao.getSecuencial(
                                CComplementacion.TABLE_NAME, null)));

                    ComplementacionTMP cTMP = new ComplementacionTMP(com);
                    datos.setComplementacionTMP(cTMP);
                }

                datos.setComplementacion(null);
            }

            //Si tiene documento se asocia
            DocumentoEnhanced doc = datos.getDocumento();

            if (doc != null) {
            	if (doc.getCirculo()==null){
            		doc.setCirculo(datos.getCirculo());
            	}
                fDao.setDocumentoToFolio(datos, doc, pm);
            }

            datos.setDefinitivo(true);
            datos.setFechaApertura(new Date());
            datos.setLastIdAnotacion(0);
            datos.setLastIdDireccion(0);
            datos.setLastIdSalvedad(0);
            datos.setLastIdAnotacionTMP(0);
            datos.setLastIdDireccionTMP(0);
            datos.setLastIdSalvedadTMP(0);
			datos.setOrdenLPAD(null);

            //Se crean las direcciones temporales
            List direcciones = datos.getDirecciones();
            //DireccionTMP direccion;
            DireccionEnhanced dir;
			//DireccionEnhanced.ID dirID = new DireccionEnhanced.ID();

            for (Iterator itr = direcciones.iterator(); itr.hasNext();) {
                dir = (DireccionEnhanced) itr.next();
				UsuarioEnhanced usuarioDireccion = dir.getUsuarioCreacion();
				if(usuarioDireccion != null) {
					UsuarioEnhancedPk usuarioDireccionID = new UsuarioEnhancedPk();
					usuarioDireccionID.idUsuario = usuarioDireccion.getIdUsuario();
					usuarioDireccion = fDao.getUsuarioByID(usuarioDireccionID, pm);
					dir.setUsuarioCreacion(usuarioDireccion);
				}
                fDao.addDireccionToFolio(datos, dir, pm);
            }

            //Se crean las salvedades temporales
            List salvedades = datos.getSalvedades();
            //SalvedadFolioTMP salvedad;
            SalvedadFolioEnhanced sal;

            for (Iterator itr = salvedades.iterator(); itr.hasNext();) {
                sal = (SalvedadFolioEnhanced) itr.next();
                UsuarioEnhanced usuarioCreacion = sal.getUsuarioCreacion();
                UsuarioEnhanced usuarioTmp = sal.getUsuarioCreacionTMP();
                
                if(usuarioCreacion != null) {
                    UsuarioEnhancedPk usuarioCreacionID = new UsuarioEnhancedPk();
                    usuarioCreacionID.idUsuario = usuarioCreacion.getIdUsuario();
                    usuarioCreacion = fDao.getUsuarioByID(usuarioCreacionID, pm);
                }
                if(usuarioTmp != null) {
                    UsuarioEnhancedPk usuarioTmpID = new UsuarioEnhancedPk();
                    usuarioTmpID.idUsuario = usuarioTmp.getIdUsuario();
                    usuarioTmp = fDao.getUsuarioByID(usuarioTmpID, pm);
                }
                
                sal.setUsuarioCreacion(usuarioCreacion);
                sal.setUsuarioCreacionTMP(usuarioTmp);
                
                fDao.addSalvedadToFolio(datos, sal, pm);
                
                List turnosSalvedad = sal.getTurnoSalvedadFolios();
                
                for(Iterator iteradorTurnos = turnosSalvedad.iterator(); iteradorTurnos.hasNext();) {
                    TurnoSalvedadFolioEnhanced turnoSalvedad = (TurnoSalvedadFolioEnhanced)iteradorTurnos.next();
                    TurnoEnhanced turno = turnoSalvedad.getTurno();
                    TurnoEnhancedPk turnoID = new TurnoEnhancedPk();
                    turnoID.anio = turno.getAnio();
                    turnoID.idCirculo = turno.getIdCirculo();
                    turnoID.idProceso = turno.getIdProceso();
                    turnoID.idTurno = turno.getIdTurno();
                    turno = fDao.getTurnoByID(turnoID, pm);
                    turnoSalvedad.setTurno(turno);
                    turnoSalvedad.setSalvedad(sal);
                }
            }

            //Se crean las anotaciones temporales
            List anotaciones = datos.getAnotaciones();
            //AnotacionTMP anotacion;
            AnotacionEnhanced anota;
            //List cache;

            //Se agregan anotaciones temporales en caso
            //que se manden explicitamente en la lista
            //de anotaciones temporales
            //List anotacionesTMP = datos.getAnotacionesTMPs();

/*            for (Iterator itr = anotacionesTMP.iterator(); itr.hasNext();) {
                anotacion = (AnotacionTMP) itr.next();
                fDao.addAnotacionTMPToFolio(datos, anotacion, usuario, pm);
            }*/

            for (Iterator itr = anotaciones.iterator(); itr.hasNext();) {
                anota = (AnotacionEnhanced) itr.next();
                this.addAnotacionToFolio(datos, anota, pm, usuario, fDao);
            }

            //Se setea el usuario creador:
            
            UsuarioEnhanced usu = new UsuarioEnhanced();
            UsuarioEnhancedPk usuarioUD = new UsuarioEnhancedPk();
            usuarioUD.idUsuario = usuario.getIdUsuario();
            usu = fDao.getUsuarioByID(usuarioUD, pm);
	
            datos.setUsuarioCreacion(usu);
            
            //pm.makeTransient(datos.getUsuarioCreacion());
            pm.makePersistent(datos);

            return true;
        } catch (DAOException e) {
        	Log.getInstance().error(JDOGenieTrasladoDAO.class, e.getMessage());
            if(pm2!=null && !pm2.isClosed())
        		pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
            throw e;
        } catch (JDOException e) {
        	if(pm2!=null && !pm2.isClosed())
        		pm2.setDatastoreTxLocking(VersantPersistenceManager.LOCKING_NONE);
            DAOException daoe = new DAOException(e.getMessage(), e);
            Log.getInstance().error(JDOGenieTrasladoDAO.class, e.getMessage());
            throw daoe;
        }
    }
    
    protected boolean addAnotacionToFolio(FolioEnhanced folio,
        AnotacionEnhanced datos, PersistenceManager pm, UsuarioEnhanced usuario,
        JDOGenieFolioDAO fDao) 
        throws DAOException {
            
        //JDOGenieZonaRegistralDAO zonaRegistralDAO = new JDOGenieZonaRegistralDAO();
        boolean rta = false;

        try {
            EstadoAnotacionEnhanced estado;
            
            datos.setCirculo(folio.getCirculo());

            if (datos.getEstado() != null) {
                EstadoAnotacionEnhancedPk eId = new EstadoAnotacionEnhancedPk();
                eId.idEstadoAn = datos.getEstado().getIdEstadoAn();

                estado = fDao.getEstadoAnotacion(eId, pm);

                if (estado == null) {
                    throw new DAOException(
                        "No encontró el estado de la anotación con el ID: " +
                        eId.idEstadoAn);
                }
            } else {
                EstadoAnotacionEnhancedPk eId = new EstadoAnotacionEnhancedPk();
                eId.idEstadoAn = CEstadoAnotacion.ACTIVO;

                estado = fDao.getEstadoAnotacion(eId, pm);

                if (estado == null) {
                    throw new DAOException(
                        "No encontró el estado de la anotación con el ID: " +
                        eId.idEstadoAn);
                }
            }

            if (datos.getNaturalezaJuridica() == null) {
                throw new DAOException(
                    "La anotación debe tener naturaleza jurídica");
            }

            NaturalezaJuridicaEnhancedPk njId = new NaturalezaJuridicaEnhancedPk();
            njId.idNaturalezaJuridica = datos.getNaturalezaJuridica()
                                             .getIdNaturalezaJuridica();
           /*
            *  @author Carlos Torres
            *  @chage   se agrega validacion de version diferente
            *  @mantis 12705: Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
            */
            njId.version = datos.getNaturalezaJuridica().getVersion();
            NaturalezaJuridicaEnhanced naturaleza = fDao.getNaturalezaJuridica(njId,
                    pm);

            if (naturaleza == null) {
                throw new DAOException(
                    "No encontró la naturaleza jurídica de la anotación con el ID: " +
                    njId.idNaturalezaJuridica);
            }

            TipoAnotacionEnhancedPk taId = new TipoAnotacionEnhancedPk();

            if (datos.getTipoAnotacion() == null) {
                taId.idTipoAnotacion = CTipoAnotacion.ESTANDAR;
            } else {
                taId.idTipoAnotacion = datos.getTipoAnotacion()
                                            .getIdTipoAnotacion();
            }

            TipoAnotacionEnhanced tipoAnota = fDao.getTipoAnotacion(taId, pm);

            if (tipoAnota == null) {
                throw new DAOException(
                    "No encontró el tipo de la anotación con el ID: " +
                    taId.idTipoAnotacion);
            }

            datos.setEstado(estado);
            datos.setNaturalezaJuridica(naturaleza);
            datos.setTipoAnotacion(tipoAnota);

            if (datos.getDocumento() != null) {
                fDao.setDocumentoToAnotacion(datos, datos.getDocumento(), pm);
            }

            //Se establece la llave primaria de anotación, si es diferente de null
            //se asigna una con la secuencia que lleva folio
            if (datos.getIdAnotacion() == null) {
                datos.setIdAnotacion(String.valueOf(folio.getLastIdAnotacion() +
                        1));
                folio.setLastIdAnotacion(folio.getLastIdAnotacion() + 1);
            }

            datos.setFolio(folio);
			datos.setIdMatricula(folio.getIdMatricula());
			
            // Los turnos asociados a la anotación no pueden ser transientes,
            // es necesario hacer a cada uno de estos persistentes.
			List turnosAnotacion = datos.getTurnoAnotacions();
			TurnoAnotacionEnhanced turnoAnotacion = null;
			
			for(Iterator itr = turnosAnotacion.iterator(); itr.hasNext();) {
				turnoAnotacion = (TurnoAnotacionEnhanced)itr.next();
				TurnoEnhanced turno = turnoAnotacion.getTurno();
				if(turno != null) {
					TurnoEnhancedPk turnoID = new TurnoEnhancedPk();
					turnoID.anio = turno.getAnio();
					turnoID.idCirculo = turno.getIdCirculo();
					turnoID.idProceso = turno.getIdProceso();
					turnoID.idTurno = turno.getIdTurno();
					turno = fDao.getTurnoByID(turnoID, pm);
				}
				turnoAnotacion.setTurno(turno);
                turnoAnotacion.setAnotacion(datos);
			}

            //Salvedades para agregar
            List salvedades = datos.getSalvedades();
            SalvedadAnotacionEnhanced salvedad = null;

            for (Iterator itr = salvedades.iterator(); itr.hasNext();) {
                salvedad = (SalvedadAnotacionEnhanced) itr.next();
				UsuarioEnhanced usuarioCreacion = salvedad.getUsuarioCreacion();
                UsuarioEnhanced usuarioTmp = salvedad.getUsuarioCreacionTMP();
                
				if(usuarioCreacion != null) {
					UsuarioEnhancedPk usuarioCreacionID = new UsuarioEnhancedPk();
					usuarioCreacionID.idUsuario = usuarioCreacion.getIdUsuario();
					usuarioCreacion = fDao.getUsuarioByID(usuarioCreacionID, pm);
				}
                if(usuarioTmp != null) {
                    UsuarioEnhancedPk usuarioTmpID = new UsuarioEnhancedPk();
                    usuarioTmpID.idUsuario = usuarioTmp.getIdUsuario();
                    usuarioTmp = fDao.getUsuarioByID(usuarioTmpID, pm);
                }
                
                salvedad.setUsuarioCreacionTMP(usuarioTmp);
                
                // this.addSalvedadToAnotacion(datos, salvedad, usuario, pm);
                this.addSalvedadToAnotacion(datos, salvedad, usuarioCreacion, pm);
                
                List turnosSalvedad = salvedad.getTurnoSalvedadAnotacions();
                
                for(Iterator iteradorTurnos = turnosSalvedad.iterator(); iteradorTurnos.hasNext();) {
                    TurnoSalvedadAnotacionEnhanced turnoSalvedad = (TurnoSalvedadAnotacionEnhanced)iteradorTurnos.next();
                    TurnoEnhanced turno = turnoSalvedad.getTurno();
                    TurnoEnhancedPk turnoID = new TurnoEnhancedPk();
                    turnoID.anio = turno.getAnio();
                    turnoID.idCirculo = turno.getIdCirculo();
                    turnoID.idProceso = turno.getIdProceso();
                    turnoID.idTurno = turno.getIdTurno();
                    turno = fDao.getTurnoByID(turnoID, pm);
                    turnoSalvedad.setTurno(turno);
                    turnoSalvedad.setSalvedad(salvedad);
                }
            }

            //Anotaciones ciudadano para agregar
            List anotacionesCiudadano = datos.getAnotacionesCiudadanos();
            AnotacionCiudadanoEnhanced anotaCiud = null;

            for (Iterator itr = anotacionesCiudadano.iterator(); itr.hasNext();) {
                anotaCiud = (AnotacionCiudadanoEnhanced) itr.next();
                fDao.addAnotacionCiudadanoToAnotacion(datos, anotaCiud, pm);
            }

            //Anotaciones cancelacion para agregar
            List anotacionesCancelacion = datos.getAnotacionesCancelacions();
            CancelacionEnhanced cancel = null;

            for (Iterator itr = anotacionesCancelacion.iterator();
                    itr.hasNext();) {
                cancel = (CancelacionEnhanced) itr.next();
                fDao.addCancelacionToAnotacion(datos, cancel, pm);
            }

            List foliosHijos = new ArrayList();
            foliosHijos.addAll(datos.getAnotacionesHijos());
            
            for(Iterator iteradorHijos = foliosHijos.iterator(); iteradorHijos.hasNext();) {
            	FolioDerivadoEnhanced folioDerivado = (FolioDerivadoEnhanced) iteradorHijos.next();
            	datos.removeAnotacionesHijo(folioDerivado);
            }
            
            /*List foliosHijos = datos.getAnotacionesHijos();
            for (Iterator itr = foliosHijos.iterator(); itr.hasNext();) {
                FolioDerivadoEnhanced fd = (FolioDerivadoEnhanced) itr.next();
				AnotacionEnhanced anotacionHija = fd.getHijo();
				if(anotacionHija != null) {
					AnotacionEnhanced.ID anotacionHijaID = new AnotacionEnhanced.ID();
					anotacionHijaID.idAnotacion = anotacionHija.getIdAnotacion();
					anotacionHijaID.idMatricula = anotacionHija.getIdMatricula();
					anotacionHijaID.idZonaRegistral = anotacionHija.getIdZonaRegistral();
					anotacionHija = fDao.getAnotacionByID(anotacionHijaID, pm);
					/\*List anotacionesPadres = anotacionHija.getAnotacionesPadre();
					for(Iterator itrPadres = anotacionesPadres.iterator(); itrPadres.hasNext();) {
						FolioDerivadoEnhanced fdPadre = (FolioDerivadoEnhanced)itrPadres.next();
						AnotacionEnhanced anotacionPadre = fdPadre.getPadre();
						if(anotacionPadre.getIdAnotacion().equals(datos.getIdAnotacion()) &&
								anotacionPadre.getIdMatricula().equals(datos.getIdMatricula()) &&
								anotacionPadre.getIdZonaRegistral().equals(datos.getIdZonaRegistral())) {
							fdPadre.setPadre(datos);
							break;
						}
					}*\/
					fd.setHijo(anotacionHija);
				}
				fd.setIdMatricula(datos.getIdMatricula());
                fd.setIdZonaRegistral(datos.getIdZonaRegistral());
            }*/
			
            List foliosPadres = new ArrayList();
            foliosPadres.addAll(datos.getAnotacionesPadre());
            
            for(Iterator iteradorPadres = foliosPadres.iterator(); iteradorPadres.hasNext();) {
            	FolioDerivadoEnhanced folioDerivado = (FolioDerivadoEnhanced) iteradorPadres.next();
            	datos.removeAnotacionesPadre(folioDerivado);
            }
            
            /*List foliosPadres = datos.getAnotacionesPadre();
            for (Iterator itr = foliosPadres.iterator(); itr.hasNext();) {
                FolioDerivadoEnhanced fd = (FolioDerivadoEnhanced) itr.next();
				AnotacionEnhanced anotacionPadre = fd.getPadre();
				if(anotacionPadre != null) {
					AnotacionEnhanced.ID anotacionPadreID = new AnotacionEnhanced.ID();
					anotacionPadreID.idAnotacion = anotacionPadre.getIdAnotacion();
					anotacionPadreID.idMatricula = anotacionPadre.getIdMatricula();
					anotacionPadreID.idZonaRegistral = anotacionPadre.getIdZonaRegistral();
					anotacionPadre = fDao.getAnotacionByID(anotacionPadreID, pm);
					/\*List anotacionesHijas = anotacionPadre.getAnotacionesHijos();
					for(Iterator itrHijas = anotacionesHijas.iterator(); itrHijas.hasNext();) {
						FolioDerivadoEnhanced fdHijo = (FolioDerivadoEnhanced)itrHijas.next();
						AnotacionEnhanced anotacionHija = fdHijo.getHijo();
						if(anotacionHija.getIdAnotacion().equals(datos.getIdAnotacion()) &&
								anotacionHija.getIdMatricula().equals(datos.getIdMatricula()) &&
								anotacionHija.getIdZonaRegistral().equals(datos.getIdZonaRegistral())) {
							fdHijo.setHijo(datos);
							break;
						}
					}*\/
					fd.setPadre(anotacionPadre);
				}
				fd.setIdMatricula1(datos.getIdMatricula());
                fd.setIdZonaRegistral1(datos.getIdZonaRegistral());
            }*/
			
			UsuarioEnhanced usuarioCreacion = datos.getUsuarioCreacion();
			UsuarioEnhancedPk usuarioCreacionID = new UsuarioEnhancedPk();
			usuarioCreacionID.idUsuario = usuarioCreacion.getIdUsuario();
			usuarioCreacion = fDao.getUsuarioByID(usuarioCreacionID, pm);
			
			datos.setUsuarioCreacion(usuarioCreacion);
            
            rta = true;
        } catch (DAOException e) {
        	Log.getInstance().error(JDOGenieTrasladoDAO.class, e.getMessage());
            throw e;
        } catch (JDOException e) {
        	Log.getInstance().error(JDOGenieTrasladoDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }

    protected boolean addSalvedadToAnotacion(AnotacionEnhanced anota,
        SalvedadAnotacionEnhanced datos, UsuarioEnhanced usuario,
        PersistenceManager pm) throws DAOException {
        boolean rta = false;

        try {
            if (datos.getIdSalvedad() == null) {
                datos.setIdSalvedad(String.valueOf(anota.getLastIdSalvedad() +
                        1));
                anota.setLastIdSalvedad(anota.getLastIdSalvedad() + 1);
            }

            datos.setAnotacion(anota);

            //Se setea el usuario creador
            datos.setUsuarioCreacion(usuario);

            rta = true;
        } catch (JDOException e) {
        	Log.getInstance().error(JDOGenieTrasladoDAO.class, e.getMessage());
            throw new DAOException(e.getMessage(), e);
        }

        return rta;
    }
    
}

/*
 * OracleHermodService.java
 *
 * Created on 12 de julio de 2004, 9:10
 */
package gov.sir.hermod.dao.impl.jdogenie;

import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.TrasladoDatos;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.CirculoEnhancedPk;
import gov.sir.core.negocio.modelo.jdogenie.FolioEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TrasladoDatosEnhanced;
import gov.sir.core.negocio.modelo.jdogenie.TrasladoEnhanced;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.TrasladoTurnosDAO;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.jdo.JDOException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.auriga.core.modelo.transferObjects.Estacion;

/**
 *
 * @author  jmendez
 */
public class JDOTrasladoTurnosDAO implements TrasladoTurnosDAO {

	/** Creates a new instance of OracleHermodService */
	public JDOTrasladoTurnosDAO() {
	}

	/**
	 * Traslada un turno a un usuario específico
	 * @param turno
	 * @param usuario
	 * @return
	 * @throws DAOException
	 */
	public boolean trasladarTurnoSAS(Turno turno, Estacion estacion) throws DAOException {
		return false;
	}

    /**
    * @author      :  Julio Alcazar
    * @change      :  Se consulta la informacion origen de una matricula trasladada
    * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
    */
    public Traslado getFolioOrigenTraslado(String matricula) throws DAOException {
      PersistenceManager pm = AdministradorPM.getPM();

      TrasladoEnhanced rta = null;
      Traslado tras = null;
      try {
          Query query = pm.newQuery(TrasladoEnhanced.class);
          query.declareParameters("String matricula");
          query.setFilter("folioOrigen.idMatricula == matricula");
          Collection col = (Collection) query.execute(matricula);
          Iterator iter = col.iterator();
          while (iter.hasNext()) {
              rta = (TrasladoEnhanced) iter.next();
          }
          
          query.close(col);
          if(rta != null){
              tras = this.getTraslado(rta, pm);
          }
          
      } catch (JDOObjectNotFoundException e) {
    	  Log.getInstance().error(JDOTrasladoTurnosDAO.class,e.getMessage());
          throw new DAOException(e.getMessage(), e);
      } catch (JDOException e) {
    	  Log.getInstance().error(JDOTrasladoTurnosDAO.class,e.getMessage());
          throw new DAOException(e.getMessage(), e);
      }finally {
          pm.close();
      }
      return tras;
    }

    /**
    * @author      :  Julio Alcazar
    * @change      :  Se consulta la informacion destino de una matricula trasladada
    * @revision    :  Se cambio el manejo de folioDestino de tipo Folio a String
    * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
    */
    public Traslado getFolioDestinoTraslado(String matricula) throws DAOException {
      PersistenceManager pm = AdministradorPM.getPM();
      TrasladoEnhanced rta = null;
       Traslado tras = null;

      try {
          Query query = pm.newQuery(TrasladoEnhanced.class);
          query.declareParameters("String matricula");
          query.setFilter("folioDestino == matricula");
          Collection col = (Collection) query.execute(matricula);
          Iterator iter = col.iterator();
          while (iter.hasNext()) {
              rta = (TrasladoEnhanced) iter.next();
          }

          query.close(col);
          if(rta != null){
              tras = this.getTraslado(rta, pm);
          }

      } catch (JDOObjectNotFoundException e) {
    	  Log.getInstance().error(JDOTrasladoTurnosDAO.class,e.getMessage());
          throw new DAOException(e.getMessage(), e);
      } catch (JDOException e) {
    	  Log.getInstance().error(JDOTrasladoTurnosDAO.class,e.getMessage());
          throw new DAOException(e.getMessage(), e);
      }finally {
          pm.close();
      }

      return tras;
    }

    /**
    * @author      :  Julio Alcazar
    * @change      :  Nuevo Metodo
    * @revision    :  Se cambio el manejo de folioDestino de tipo Folio a String
    * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
    */
    private Traslado getTraslado(TrasladoEnhanced trasladoEnhanced, PersistenceManager pm){

        Traslado traslado = new Traslado();
        traslado.setResolucion(trasladoEnhanced.getResolucion());
        if(trasladoEnhanced.getFechaTraslado()!=null){
            traslado.setFechaTraslado(new Date(trasladoEnhanced.getFechaTraslado().getTime()));
        }
        traslado.setIdTraslado(trasladoEnhanced.getIdTraslado());
        traslado.setResolucionDestino(trasladoEnhanced.getResolucionDestino());
       /**
        * @author     : Carlos Torres
        * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
        */
        traslado.setFechaResolucionDestino((trasladoEnhanced.getFechaResolucionDestino()!=null)?new Date(trasladoEnhanced.getFechaResolucionDestino().getTime()):null);
        traslado.setFechaResolucionOrigen((trasladoEnhanced.getFechaResolucionOrigen()!=null)?new Date(trasladoEnhanced.getFechaResolucionOrigen().getTime()):null);
        FolioEnhanced folioorigen = trasladoEnhanced.getFolioOrigen();
        String foliodestino = trasladoEnhanced.getFolioDestino();
        /**
        * @author     : Carlos Torres
        * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
        */
        traslado.setFechaConfirTras((trasladoEnhanced.getFechaConfirTras()!=null)?new Date(trasladoEnhanced.getFechaConfirTras().getTime()):null);
        TrasladoDatosEnhanced tdEnhanced = trasladoEnhanced.getTrasladoDatos();
        TrasladoDatos td = (TrasladoDatos)tdEnhanced.toTransferObject();
        pm.makeTransient(trasladoEnhanced);
        CirculoEnhancedPk pk = new CirculoEnhancedPk(folioorigen.getCirculo());
        CirculoEnhanced circulo = (CirculoEnhanced) pm.getObjectById(pk, true);
        Folio folio1 = new Folio();        
        folio1.setIdMatricula(folioorigen.getIdMatricula());
        folio1.setCirculo(folioorigen.getCirculo());
        folio1.setCirculo(circulo.getNombre()+" "+circulo.getIdCirculo());
        traslado.setFolioOrigen(folio1);
        traslado.setFolioDestino(foliodestino);
        /**
        * @author     : Carlos Torres
        * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
        */
        traslado.setTrasladoDatos(td);
        return traslado;
    }

}

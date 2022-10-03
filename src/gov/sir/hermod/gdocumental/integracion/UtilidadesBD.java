package gov.sir.hermod.gdocumental.integracion;

import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.jdogenie.PagoEnhanced;
import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.impl.jdogenie.AdministradorPM;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Esta clase se encarga de realizar consultas en la base de datos SIR.
 * @author Fernando Padilla Velez
 */
public class UtilidadesBD {

    /**
     * Método encargado de consultar un pago dado un idSolicitud.
     * @author Fernando Padilla Velez
     * @throws DAOException
     */
    public static Pago consultarPago(String idSolicitud) {

        PersistenceManager pm = null;
        PagoEnhanced pagoEnhanced    = null;
        Query query = null;
        try {

            pm = AdministradorPM.getPM();
            pm.currentTransaction().begin();
            query = pm.newQuery(PagoEnhanced.class);

            query.declareParameters("String idSolicitud");

            query.setFilter("this.idSolicitud == idSolicitud");

            Collection col = (Collection) query.execute(idSolicitud);

            Iterator iter = col.iterator();
            while(iter.hasNext()){
                pagoEnhanced = (PagoEnhanced)iter.next();
            }
            
            pm.makeTransient(pagoEnhanced);

            return (Pago)pagoEnhanced.toTransferObject();
            
        } catch (DAOException ex) {
            Logger.getLogger(UtilidadesBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            query.closeAll();
            pm.currentTransaction().commit();
        }
        return null;
    }
}

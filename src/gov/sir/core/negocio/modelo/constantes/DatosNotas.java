/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.constantes;

import gov.sir.hermod.dao.DAOException;
import gov.sir.hermod.dao.impl.jdogenie.JDOVariablesOperativasDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author : HGOMEZ, FPADILLA
 *** @change : Clase sigleton para llevar un listado del idTipoNota y la version de la nota..
 *** Caso Mantis : 12621
 */
public class DatosNotas {

    private static DatosNotas instanciaUnica = null;
    private List<TipoNotaModel> listNotas;// = new ArrayList<DatosNotas>();

    private DatosNotas() {
        try {
            JDOVariablesOperativasDAO aO = new JDOVariablesOperativasDAO();
            listNotas = aO.getListadoTiposNotaVersion();
        } catch (DAOException ex) {
            Logger.getLogger(DatosNotas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static synchronized DatosNotas getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new DatosNotas();
        }
        return instanciaUnica;
    }

    public long obtenerVersionTipoNota(String tipoNota){
        for(TipoNotaModel n:listNotas){
            if(n.getIdTipoNota().equals(tipoNota)){
                return n.getVersion();
            }
        }
        return 0;
    }
    
  }

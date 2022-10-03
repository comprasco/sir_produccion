package gov.sir.core.negocio.modelo.jdogenie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gov.sir.core.negocio.modelo.LlaveBloqueo;




/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class LlaveBloqueoEnhanced  extends Enhanced {
    private String idLlave; // pk 
    private List bloqueoFolios = new ArrayList(); // contains BloqueoFolio  inverse BloqueoFolio.llaveBloqueo

    public LlaveBloqueoEnhanced() {
    }

    public String getIdLlave() {
        return idLlave;
    }

    public void setIdLlave(String idLlave) {
        this.idLlave = idLlave;
    }

    public List getBloqueoFolios() {
        return Collections.unmodifiableList(bloqueoFolios);
    }

    public boolean addBloqueoFolio(BloqueoFolioEnhanced newBloqueoFolio) {
        return bloqueoFolios.add(newBloqueoFolio);
    }

    public boolean removeBloqueoFolio(BloqueoFolioEnhanced oldBloqueoFolio) {
        return bloqueoFolios.remove(oldBloqueoFolio);
    }
    
    public static LlaveBloqueoEnhanced enhance(LlaveBloqueo llaveBloqueo){
        return (LlaveBloqueoEnhanced)Enhanced.enhance(llaveBloqueo);
    }
}
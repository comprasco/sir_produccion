package gov.sir.core.negocio.modelo.jdogenie;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class WebEnglobeEnhanced extends WebSegEngEnhanced{
    private String idMatriculaUbicacion;
    private List foliosEnglobar = new ArrayList(); // contains WebFolioEnglobeEnhanced  inverse WebFolioEnglobeEnhanced.englobe
    private List foliosNuevos = new ArrayList(); // contains WebFolioNuevoEnhanced  inverse WebFolioNuevoEnhanced.englobe

    /*
    public List getFoliosNuevos() {
        return Collections.unmodifiableList(foliosNuevos);
    }

    public boolean addFoliosNuevo(WebFolioNuevoEnhanced newFoliosNuevo) {
        return foliosNuevos.add(newFoliosNuevo);
    }

    public boolean removeFoliosNuevo(WebFolioNuevoEnhanced oldFoliosNuevo) {
        return foliosNuevos.remove(oldFoliosNuevo);
    }*/
    
    public WebFolioNuevoEnhanced getFolioNuevo(){
    	WebFolioNuevoEnhanced rta = null;
    	if(!foliosNuevos.isEmpty()){
    		rta = (WebFolioNuevoEnhanced)this.foliosNuevos.get(0);
    	}
    	return rta;
    }
    
    public void setFolioNuevo(WebFolioNuevoEnhanced folio){
    	if(!foliosNuevos.isEmpty()){
    		foliosNuevos.remove(0);
    	}
    	foliosNuevos.add(folio);
    }

    public List getFoliosEnglobars() {
        return Collections.unmodifiableList(foliosEnglobar);
    }

    public boolean addFoliosEnglobar(WebFolioEnglobeEnhanced newFoliosEnglobar) {
        return foliosEnglobar.add(newFoliosEnglobar);
    }

    public boolean removeFoliosEnglobar(WebFolioEnglobeEnhanced oldFoliosEnglobar) {
        return foliosEnglobar.remove(oldFoliosEnglobar);
    }
    
    public String getIdMatriculaUbicacion() {
        return idMatriculaUbicacion;
    }

    public void setIdMatriculaUbicacion(String idMatriculaUbicacion) {
        this.idMatriculaUbicacion = idMatriculaUbicacion;
    }
}

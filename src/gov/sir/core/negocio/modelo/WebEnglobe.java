package gov.sir.core.negocio.modelo;



import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.auriga.core.modelo.TransferObject;

public class WebEnglobe extends WebSegEng implements TransferObject{
    private String idMatriculaUbicacion;
    private List foliosEnglobar = new ArrayList(); // contains WebFolioEnglobeEnhanced  inverse WebFolioEnglobeEnhanced.englobe
    private List foliosNuevos = new ArrayList(); // contains WebFolioNuevoEnhanced  inverse WebFolioNuevoEnhanced.englobe
    private static final long serialVersionUID = 1L;
    /*
    public List getFoliosNuevos() {
        return Collections.unmodifiableList(foliosNuevos);
    }

    public boolean addFoliosNuevo(WebFolioNuevo newFoliosNuevo) {
        return foliosNuevos.add(newFoliosNuevo);
    }

    public boolean removeFoliosNuevo(WebFolioNuevo oldFoliosNuevo) {
        return foliosNuevos.remove(oldFoliosNuevo);
    }*/
    
    public WebFolioNuevo getFolioNuevo(){
    	WebFolioNuevo rta = null;
    	if(!foliosNuevos.isEmpty()){
    		rta = (WebFolioNuevo)this.foliosNuevos.get(0);
    	}
    	return rta;
    }
    
    public void setFolioNuevo(WebFolioNuevo folio){
    	if(!foliosNuevos.isEmpty()){
    		foliosNuevos.remove(0);
    	}
    	foliosNuevos.add(folio);
    }


    public List getFoliosEnglobars() {
        return Collections.unmodifiableList(foliosEnglobar);
    }

    public boolean addFoliosEnglobar(WebFolioEnglobe newFoliosEnglobar) {
        return foliosEnglobar.add(newFoliosEnglobar);
    }

    public boolean removeFoliosEnglobar(WebFolioEnglobe oldFoliosEnglobar) {
        return foliosEnglobar.remove(oldFoliosEnglobar);
    }
    
    public String getIdMatriculaUbicacion() {
        return idMatriculaUbicacion;
    }

    public void setIdMatriculaUbicacion(String idMatriculaUbicacion) {
        this.idMatriculaUbicacion = idMatriculaUbicacion;
    }
}

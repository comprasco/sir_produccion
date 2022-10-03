package gov.sir.core.negocio.modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.auriga.core.modelo.TransferObject;

public class WebSegregacion extends WebSegEng implements TransferObject{

    private List foliosDerivados = new ArrayList(); // contains WebFolioDerivadoEnhanced  inverse WebFolioDerivadoEnhanced.segregacion
    private static final long serialVersionUID = 1L;
    public List getFoliosDerivados() {
        return Collections.unmodifiableList(foliosDerivados);
    }

    public boolean addFoliosDerivado(WebFolioDerivado newFoliosDerivado) {
        return foliosDerivados.add(newFoliosDerivado);
    }

    public boolean removeFoliosDerivado(WebFolioDerivado oldFoliosDerivado) {
        return foliosDerivados.remove(oldFoliosDerivado);
    }
    
}

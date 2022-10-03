package gov.sir.core.negocio.modelo.jdogenie;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class WebSegregacionEnhanced extends WebSegEngEnhanced{

    private List foliosDerivados = new ArrayList(); // contains WebFolioDerivadoEnhanced  inverse WebFolioDerivadoEnhanced.segregacion

    public List getFoliosDerivados() {
        return Collections.unmodifiableList(foliosDerivados);
    }

    public boolean addFoliosDerivado(WebFolioDerivadoEnhanced newFoliosDerivado) {
        return foliosDerivados.add(newFoliosDerivado);
    }

    public boolean removeFoliosDerivado(WebFolioDerivadoEnhanced oldFoliosDerivado) {
        return foliosDerivados.remove(oldFoliosDerivado);
    }
    
}

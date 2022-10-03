/**
 * 
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.TextoImprimiblePk;

public class TextoImprimibleEnhancedPk implements java.io.Serializable {
    public String idTexto;

    public TextoImprimibleEnhancedPk() {
    }

    public TextoImprimibleEnhancedPk(String s) {
        int i;
        int p = 0;
        idTexto = s.substring(p);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TextoImprimiblePk)) {
            return false;
        }

        final TextoImprimiblePk id = (TextoImprimiblePk) o;

        if ((this.idTexto!= null)
                ? (!idTexto.equals(id.idTexto))
                    : (id.idTexto != null)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idTexto != null) ? idTexto.hashCode() : 0);

        return result;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTexto);

        return buffer.toString();
    }
}
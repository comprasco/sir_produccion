package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.Texto;


/*
 * Generated by Versant Open Access (version:3.2.18 (15 Jun 2005))
 */
public class TextoEnhanced extends Enhanced {

    private String idCirculo; // pk
    private String idLlave; // pk
    private String texto;

    public TextoEnhanced() {
    }

    public String getIdCirculo() {
        return idCirculo;
    }

    public void setIdCirculo(String idCirculo) {
        this.idCirculo = idCirculo;
    }

    public String getIdLlave() {
        return idLlave;
    }

    public void setIdLlave(String idLlave) {
        this.idLlave = idLlave;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    

	public static TextoEnhanced enhance(Texto texto) {
		return (TextoEnhanced) Enhanced.enhance(texto);
	}
}

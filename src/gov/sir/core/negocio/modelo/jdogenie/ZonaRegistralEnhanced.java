package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ZonaRegistral;




/*
 * Generated by JDO Genie (version:3.0.0 (08 Jun 2004))
 */
public class ZonaRegistralEnhanced extends Enhanced {
    private String idZonaRegistral; // pk 
    private CirculoEnhanced circulo;
    private VeredaEnhanced vereda;

    public ZonaRegistralEnhanced() {
    }

    public String getIdZonaRegistral() {
        return idZonaRegistral;
    }

    public void setIdZonaRegistral(String idZonaRegistral) {
        this.idZonaRegistral = idZonaRegistral;
    }

    public CirculoEnhanced getCirculo() {
        return circulo;
    }

    public void setCirculo(CirculoEnhanced circulo) {
        this.circulo = circulo;
    }

    public VeredaEnhanced getVereda() {
        return vereda;
    }

    public void setVereda(VeredaEnhanced vereda) {
        this.vereda = vereda;
    }
    

	public static ZonaRegistralEnhanced enhance(ZonaRegistral v){
		return (ZonaRegistralEnhanced)Enhanced.enhance(v);
	}
}
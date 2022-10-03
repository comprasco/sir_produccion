package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ZonaNotarial;

import java.util.Date;


/*
 * Generated by Versant Open Access (version:3.2.6 (17 Nov 2004))
 */
public class ZonaNotarialEnhanced extends Enhanced {
    private String idCirculoNotarial; // pk 
    private String idDepartamento; // pk 
    private String idMunicipio; // pk 
    private String idVereda; // pk 
    private Date fechaCreacion;
    private CirculoNotarialEnhanced circuloNotarial; // inverse CirculoNotarialEnhanced.zonasNotariales
    private VeredaEnhanced vereda;

    public ZonaNotarialEnhanced() {
    }

    public String getIdCirculoNotarial() {
        return idCirculoNotarial;
    }

    public void setIdCirculoNotarial(String idCirculoNotarial) {
        this.idCirculoNotarial = idCirculoNotarial;
    }

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(String idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getIdVereda() {
        return idVereda;
    }

    public void setIdVereda(String idVereda) {
        this.idVereda = idVereda;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public CirculoNotarialEnhanced getCirculoNotarial() {
        return circuloNotarial;
    }

    public void setCirculoNotarial(CirculoNotarialEnhanced circuloNotarial) {
        this.circuloNotarial = circuloNotarial;
    }

    public VeredaEnhanced getVereda() {
        return vereda;
    }

    public void setVereda(VeredaEnhanced vereda) {
        this.vereda = vereda;
        setIdDepartamento(vereda.getIdDepartamento());
        setIdMunicipio(vereda.getIdMunicipio());
        setIdVereda(vereda.getIdVereda());
    }

    public static ZonaNotarialEnhanced enhance(ZonaNotarial v) {
        return (ZonaNotarialEnhanced) Enhanced.enhance(v);
    }
}
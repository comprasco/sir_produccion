package gov.sir.core.negocio.modelo;

import java.util.Date;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela las zonas notariales configuradas en el sistema, 
 * es decir, cada una de las relaciones entre circulo notarial y vereda */
/*
 * Generated by Versant Open Access (version:3.2.6 (17 Nov 2004))
 */
public class ZonaNotarial implements TransferObject{

    private String idCirculoNotarial; // pk 
    private String idDepartamento; // pk 
    private String idMunicipio; // pk 
    private String idVereda; // pk 
    private Date fechaCreacion;
    private CirculoNotarial circuloNotarial; // inverse CirculoNotarial.zonasNotariales
    private Vereda vereda;
    private static final long serialVersionUID = 1L;
    /** Metodo constructor por defecto  */
    
    public ZonaNotarial() {
    }

    /** Retorna el identificador del circulo notarial  */
    
    public String getIdCirculoNotarial() {
        return idCirculoNotarial;
    }

    /** Modifica el identificador del circulo notarial  */
    
    public void setIdCirculoNotarial(String idCirculoNotarial) {
        this.idCirculoNotarial = idCirculoNotarial;
    }

    /** Retorna el identificador de vereda correspondiente al departamento  */
    
    public String getIdDepartamento() {
        return idDepartamento;
    }

    /** Modifica el identificador de vereda correspondiente al departamento  */
    
    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    /** Retorna el identificador de vereda correspondiente al municipio  */
    
    public String getIdMunicipio() {
        return idMunicipio;
    }

    /** Retorna el identificador de vereda correspondiente al municipio  */
    
    public void setIdMunicipio(String idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    /** Retorna el identificador de vereda  */
    
    public String getIdVereda() {
        return idVereda;
    }

    /** Modifica el identificador de vereda  */
    
    public void setIdVereda(String idVereda) {
        this.idVereda = idVereda;
    }

    /** Retorna la fecha de creacion del registro en la base de datos  */
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /** Modifica la fecha de creacion del registro en la base de datos  */
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /** Retorna el identificador del circulo notarial  */
    
    public CirculoNotarial getCirculoNotarial() {
        return circuloNotarial;
    }

    /** Modifica el identificador del circulo notarial  */
    
    public void setCirculoNotarial(CirculoNotarial circuloNotarial) {
        this.circuloNotarial = circuloNotarial;
    }

    /** Retorna el identificador de la vereda  */
    
    public Vereda getVereda() {
        return vereda;
    }

    /** Modifica el identificador de la vereda  */
    
    public void setVereda(Vereda vereda) {
        this.vereda = vereda;
        setIdDepartamento(vereda.getIdDepartamento());
        setIdMunicipio(vereda.getIdMunicipio());
        setIdVereda(vereda.getIdVereda());
    }
}
package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004)) */
/** Clase que modela la informacion de dominio de naturaleza juridica */
public class DominioNaturalezaJuridica  implements TransferObject{

    private String idDominioNatJur; // pk 
    private String nombre;
    private static final long serialVersionUID = 1L;
    /** Constructor por defecto */
    public DominioNaturalezaJuridica() {
    }

    /** Retorna el identificador de dominio de naturaleza jur?dica */
    public String getIdDominioNatJur() {
        return idDominioNatJur;
    }

    /** Cambia el identificador de dominio de naturaleza jur?dica */
    public void setIdDominioNatJur(String idDominioNatJur) {
        this.idDominioNatJur = idDominioNatJur;
    }

    /** Retorna el nombre de dominio de naturaleza jur?dica */
    public String getNombre() {
        return nombre;
    }

    /** Cambia el nombre de dominio de naturaleza jur?dica */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
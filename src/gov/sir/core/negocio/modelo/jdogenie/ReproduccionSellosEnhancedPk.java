/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ReproduccionSellosPk;

/**
 *
 * @author LENOVO
 */
public class ReproduccionSellosEnhancedPk implements java.io.Serializable {
    public String idTurnoRaiz;
    public int idReproduccionSellos;
    public String circulo;
    public int Fase_reg;

    
    public ReproduccionSellosEnhancedPk(ReproduccionSellosPk id){
            idTurnoRaiz = id.idTurnoRaiz;
            idReproduccionSellos = id.idReproduccionSellos;
            circulo = id.circulo;
            Fase_reg = id.Fase_reg;
    }
    public ReproduccionSellosEnhancedPk() {
    }
    public ReproduccionSellosPk getReproduccionSellos(){
            ReproduccionSellosPk id = new ReproduccionSellosPk();
            id.idTurnoRaiz = idTurnoRaiz;
            id.idReproduccionSellos = idReproduccionSellos;
            id.circulo = circulo;
            id.Fase_reg = Fase_reg;
            return id;
    }
    public ReproduccionSellosEnhancedPk(String s) {
            idTurnoRaiz = s;
    }
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idTurnoRaiz);
        return buffer.toString();
    }
    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((idTurnoRaiz != null) ? idTurnoRaiz.hashCode() : 0);

        return result;
    }
     public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof ReproduccionSellosEnhancedPk)) {
                return false;
            }

            final ReproduccionSellosEnhancedPk id = (ReproduccionSellosEnhancedPk) o;

            if ((this.idTurnoRaiz != null)
                    ? (!idTurnoRaiz.equals(id.idTurnoRaiz))
                        : (id.idTurnoRaiz != null)) {
                return false;
            }
            
            return true;
        }
}

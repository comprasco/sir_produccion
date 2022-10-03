/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;

/**
 *
 * @author LENOVO
 */
public class ReproduccionSellosPk implements java.io.Serializable {
    public String idTurnoRaiz;
    public int idReproduccionSellos;
    public String circulo;
    public int Fase_reg;

    public ReproduccionSellosPk() {
    }
    public ReproduccionSellosPk(String s) {
            idTurnoRaiz = s;
    }
    public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof ReproduccionSellosPk)) {
                return false;
            }

            final ReproduccionSellosPk id = (ReproduccionSellosPk) o;

            if ((this.idTurnoRaiz != null)
                    ? (!idTurnoRaiz.equals(id.idTurnoRaiz))
                        : (id.idTurnoRaiz != null)) {
                return false;
            }
            
            return true;
        }

        public int hashCode() {
            int result = 0;
            result = (29 * result) +
                ((idTurnoRaiz != null) ? idTurnoRaiz.hashCode() : 0);
            
            return result;
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append(idTurnoRaiz);
            return buffer.toString();
        }

}

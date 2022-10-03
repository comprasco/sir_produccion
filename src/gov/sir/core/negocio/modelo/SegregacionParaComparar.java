/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;

/**
 *
 * @author Edgar
 */
public class SegregacionParaComparar {
    private String idMatricula;
    private int idOrden;
    private static final long serialVersionUID = 1L;
    public SegregacionParaComparar(String s){
        if(s.indexOf(" : ") != -1){
            idMatricula = (s.split(" : "))[0];
            idOrden = Integer.parseInt((s.split(" : "))[1]);
        }else{
            idMatricula = s;
            idOrden = -1;
        }
    }

    public String getIdMatricula() {
        return idMatricula;
    }

    public int getIdOrden() {
        return idOrden;
    }
}

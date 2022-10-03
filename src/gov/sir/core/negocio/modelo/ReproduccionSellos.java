/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo;
import org.auriga.core.modelo.TransferObject;
/**
 *
 * @author LENOVO
 */
public class ReproduccionSellos  implements TransferObject {
    private int idReproduccionSellos;
    private String idTurnoRaiz;
    private String code;
    private String Usuariosir;
    private String circulo;
    private int desde;
    private int hasta;
    private int opcion;
    private int Fase_reg;
    private String NcopiasSello;
    private static final long serialVersionUID = 1L;
    public ReproduccionSellos() {
    }
        

    public String getIdTurnoRaiz() {
        return idTurnoRaiz;
    }

    public void setIdTurnoRaiz(String idTurnoRaiz) {
        this.idTurnoRaiz = idTurnoRaiz;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsuariosir() {
        return Usuariosir;
    }

    public void setUsuariosir(String Usuariosir) {
        this.Usuariosir = Usuariosir;
    }

    public int getIdReproduccionSellos() {
        return idReproduccionSellos;
    }

    public void setIdReproduccionSellos(int idReproduccionSellos) {
        this.idReproduccionSellos = idReproduccionSellos;
    }
    
    public String getCirculo() {
        return circulo;
    }

    public void setCirculo(String circulo) {
        this.circulo = circulo;
    }

    public int getDesde() {
        return desde;
    }

    public void setDesde(int desde) {
        this.desde = desde;
    }

    public int getHasta() {
        return hasta;
    }

    public void setHasta(int hasta) {
        this.hasta = hasta;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public int getFase_reg() {
        return Fase_reg;
    }

    public void setFase_reg(int Fase_reg) {
        this.Fase_reg = Fase_reg;
    }

    
    public String getNcopiasSello() {
        return NcopiasSello;
    }

    public void setNcopiasSello(String NcopiasSello) {
        this.NcopiasSello = NcopiasSello;
    }
        
        
}

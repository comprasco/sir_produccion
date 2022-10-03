/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.jdogenie;

import gov.sir.core.negocio.modelo.ReproduccionSellos;

/**
 * Clase que Representa Acciones en Reproduccion de Sellos.
 * <p>Una accion en calificador representada en reproduccion de sellos almacena 
 * turno raiz el cual esta en proceso, codigo ya sea turno workflow o codigo de matricula
 * desde hasta en anotaciones si es matricula circulo usuario y si paso por fase registrador
 * @author TSG
 */
public class ReproduccionSellosEnhanced  extends Enhanced{
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

    public ReproduccionSellosEnhanced() {
    }
    
    
    
    public static ReproduccionSellosEnhanced enhance(ReproduccionSellos reproduccionsellos) {
        return (ReproduccionSellosEnhanced) Enhanced.enhance(reproduccionsellos);
    }

    public String getIdTurnoRaiz() {
        return idTurnoRaiz;
    }

    public int getIdReproduccionSellos() {
        return idReproduccionSellos;
    }

    public void setIdReproduccionSellos(int idReproduccionSellos) {
        this.idReproduccionSellos = idReproduccionSellos;
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

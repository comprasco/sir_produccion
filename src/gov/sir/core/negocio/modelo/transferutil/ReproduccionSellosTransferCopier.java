/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.transferutil;

/**
 *
 * @author LENOVO
 */
public class ReproduccionSellosTransferCopier {
        public static org.auriga.core.modelo.TransferObject deepCopy( gov.sir.core.negocio.modelo.jdogenie.ReproduccionSellosEnhanced enhancedObject, java.util.HashMap cache)
	{
            gov.sir.core.negocio.modelo.ReproduccionSellos transferObject = new gov.sir.core.negocio.modelo.ReproduccionSellos();
	    cache.put(enhancedObject, transferObject );
            // Circulo
  	    try {
	  	transferObject.setCirculo(enhancedObject.getCirculo());
	  	} catch (Throwable th ) {}
            // Code
  	    try {
	  	transferObject.setCode(enhancedObject.getCode());
	  	} catch (Throwable th ) {}
            // Desde
  	    try {
	  	transferObject.setDesde(enhancedObject.getDesde());
	  	} catch (Throwable th ) {}
            // Fase reg
  	    try {
	  	transferObject.setFase_reg(enhancedObject.getFase_reg());
	  	} catch (Throwable th ) {}
            // Hasta
  	    try {
	  	transferObject.setHasta(enhancedObject.getHasta());
	  	} catch (Throwable th ) {}
            // Turno Raiz
  	    try {
	  	transferObject.setIdTurnoRaiz(enhancedObject.getIdTurnoRaiz());
	  	} catch (Throwable th ) {}
            // Ncopias
  	    try {
	  	transferObject.setNcopiasSello(enhancedObject.getNcopiasSello());
	  	} catch (Throwable th ) {}
            // UsuarioSir
  	    try {
	  	transferObject.setUsuariosir(enhancedObject.getUsuariosir());
	  	} catch (Throwable th ) {}
            // Id
  	    try {
	  	transferObject.setIdReproduccionSellos(enhancedObject.getIdReproduccionSellos());
	  	} catch (Throwable th ) {}
            // Opcion
  	    try {
	  	transferObject.setOpcion(enhancedObject.getOpcion());
	  	} catch (Throwable th ) {}
            
            return transferObject;
        }
        public static gov.sir.core.negocio.modelo.jdogenie.Enhanced deepCopy( gov.sir.core.negocio.modelo.ReproduccionSellos transferObject, java.util.HashMap cache)
	{
            gov.sir.core.negocio.modelo.jdogenie.ReproduccionSellosEnhanced enhancedObject = new gov.sir.core.negocio.modelo.jdogenie.ReproduccionSellosEnhanced();
	    cache.put(transferObject, enhancedObject);
                 // Circulo
  	    try {
	  	enhancedObject.setCirculo(transferObject.getCirculo());
	  	} catch (Throwable th ) {}
            // Code
  	    try {
	  	enhancedObject.setCode(transferObject.getCode());
	  	} catch (Throwable th ) {}
            // Desde
  	    try {
	  	enhancedObject.setDesde(transferObject.getDesde());
	  	} catch (Throwable th ) {}
            // Fase reg
  	    try {
	  	enhancedObject.setFase_reg(transferObject.getFase_reg());
	  	} catch (Throwable th ) {}
            // Hasta
  	    try {
	  	enhancedObject.setHasta(transferObject.getHasta());
	  	} catch (Throwable th ) {}
            // Turno Raiz
  	    try {
	  	enhancedObject.setIdTurnoRaiz(transferObject.getIdTurnoRaiz());
	  	} catch (Throwable th ) {}
            // Ncopias
  	    try {
	  	enhancedObject.setNcopiasSello(transferObject.getNcopiasSello());
	  	} catch (Throwable th ) {}
            // UsuarioSir
  	    try {
	  	enhancedObject.setUsuariosir(transferObject.getUsuariosir());
	  	} catch (Throwable th ) {}
            // id
  	    try {
	  	enhancedObject.setIdReproduccionSellos(transferObject.getIdReproduccionSellos());
	  	} catch (Throwable th ) {}
            // Opcion
  	    try {
	  	enhancedObject.setOpcion(transferObject.getOpcion());
	  	} catch (Throwable th ) {}
            
            return enhancedObject;
        }
                
}

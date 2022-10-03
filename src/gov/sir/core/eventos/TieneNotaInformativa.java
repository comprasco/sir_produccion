package gov.sir.core.eventos;

import gov.sir.core.negocio.modelo.Nota;

/**
 * Interfaz que indica que eventos tienen nota informativa. La nota informativa
 * es un objeto del modelo de negocio del Sistema de Informacion y Registro.
 * @see gov.sir.core.negocio.modelo.Nota
 */
public interface TieneNotaInformativa {	
    /**
     * Devolver una nota informativa
     * @return la nota informativa requerida
     */    
    Nota getNotaInf();
}

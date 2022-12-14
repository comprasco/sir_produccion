package gov.sir.core.negocio.modelo;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.auriga.core.modelo.TransferObject;

/** Clase que modela los subtipos de solicitud configurados en el sistema  */
/*
 * Generated by JDO Genie (version:3.2.0beta7 (20 Sep 2004))
 */
public class SubtipoSolicitud  implements TransferObject{

    private String idSubtipoSol; // pk 
    private String nombre;
    private List checkItems = new ArrayList(); // contains CheckItemEnhanced  inverse CheckItemEnhanced.subtipoSolicitud
    private static final long serialVersionUID = 1L;
    /** Metodo constructor por defecto  */
    public SubtipoSolicitud() {
    }

    /** Retorna el identificador del subtipo de solicitud */
    public String getIdSubtipoSol() {
        return idSubtipoSol;
    }

    /** Modifica el identificador del subtipo de solicitud */
    public void setIdSubtipoSol(String idSubtipoSol) {
        this.idSubtipoSol = idSubtipoSol;
    }

    /** Retorna el nombre del subtipo de solicitud */
    public String getNombre() {
        return nombre;
    }

    /** Modifica el nombre del subtipo de solicitud */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** Retorna la lista checkItems */
    public List getCheckItems() {
        return Collections.unmodifiableList(checkItems);
    }

    /** A?ade un identificador de requisito a la lista checkItem */
    public boolean addCheckItem(CheckItem newCheckItem) {
        return checkItems.add(newCheckItem);
    }

    /** Elimina un identificador de requisito a la lista checkItem */
    public boolean removeCheckItem(CheckItem oldCheckItem) {
        return checkItems.remove(oldCheckItem);
    }
}
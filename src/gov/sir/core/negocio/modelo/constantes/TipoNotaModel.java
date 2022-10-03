/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.negocio.modelo.constantes;



public class TipoNotaModel {
    
   private String idTipoNota;
   
   /** @author : HGOMEZ, FPADILLA
 *** @change : Permite llevar un control del idTipoNota y la version de la nota..
 *** Caso Mantis : 12621
 */
   private long version;
   private long activo;

    public long getActivo() {
        return activo;
    }

    public void setActivo(long activo) {
        this.activo = activo;
    }

    public String getIdTipoNota() {
        return idTipoNota;
    }

    public void setIdTipoNota(String idTipoNota) {
        this.idTipoNota = idTipoNota;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
   
}

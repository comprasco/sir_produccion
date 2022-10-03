/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo Banco Franquica del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo.jdogenie;
import gov.sir.core.negocio.modelo.BancoFranquiciaPk;

public class BancoFranquiciaEnhancedPk implements java.io.Serializable{
    public String idBanco;         // pk
    public int idTipoFranquicia;   // pk

    public BancoFranquiciaEnhancedPk() {
    }

    public BancoFranquiciaEnhancedPk(BancoFranquiciaPk bancoFranquiciaPk) {
        this.idBanco = bancoFranquiciaPk.idBanco;
        this.idTipoFranquicia = bancoFranquiciaPk.idTipoFranquicia;
    }
    
    public BancoFranquiciaEnhancedPk(String s) {
        int i, p = 0;
        i= s.indexOf('-', p);
        idBanco = s.substring(p, i);
        p = i + 1;
        idTipoFranquicia = Integer.parseInt(s.substring(p));
    }
    
    public BancoFranquiciaPk getBancoFranquicia(){
		BancoFranquiciaPk bancoFranquiciaPk = new BancoFranquiciaPk();
		bancoFranquiciaPk.idBanco = idBanco;
                bancoFranquiciaPk.idTipoFranquicia = idTipoFranquicia;
		return bancoFranquiciaPk;
    }

//    public String getIdBanco() {
//        return idBanco;
//    }
//
//    public void setIdBanco(String idBanco) {
//        this.idBanco = idBanco;
//    }
//
//    public int getIdTipoFranquicia() {
//        return idTipoFranquicia;
//    }
//
//    public void setIdTipoFranquicia(int idTipoFranquicia) {
//        this.idTipoFranquicia = idTipoFranquicia;
//    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(idBanco);
        buffer.append('-');
        buffer.append(idTipoFranquicia);
        return buffer.toString();
    }
}

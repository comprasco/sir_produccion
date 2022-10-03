package gov.sir.core.negocio.modelo.jdogenie;

/**
 * @author jfrias
 *
 */
public class LlaveMapaJDO {
    private String clase;
    private String atributo;
    
    /**  */
    public LlaveMapaJDO() {
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public boolean equals(Object arg0) {
        // TODO Auto-generated method stub
        if (arg0 instanceof LlaveMapaJDO){
            LlaveMapaJDO k=(LlaveMapaJDO)arg0;
            return ((clase.equals(k.getClase()) && atributo.equals(k.getAtributo())));
        }
        return false;
    }

    public int hashCode() {
        int result = 0;
        result = (29 * result) +
            ((clase != null) ? clase.hashCode() : 0);
        result = (29 * result) +
            ((atributo != null) ? atributo.hashCode() : 0);

        return result;
    }
}

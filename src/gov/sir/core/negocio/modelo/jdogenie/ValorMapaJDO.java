package gov.sir.core.negocio.modelo.jdogenie;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jfrias
 *
 */
public class ValorMapaJDO {
    
    private String tabla;
    private List columnas=new ArrayList();
    private boolean isPk;
    
    /**  */
    public ValorMapaJDO() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public List getColumnas() {
        return columnas;
    }
    
    public void addColumna(RelAtributoValorMapaJDO relacion){
        columnas.add(relacion);
    }

    public boolean isPk() {
        return isPk;
    }

    public void setPk(boolean isPk) {
        this.isPk = isPk;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public void setColumnas(List columnas) {
        this.columnas = columnas;
    }
}

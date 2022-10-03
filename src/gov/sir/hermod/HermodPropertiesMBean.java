/*
 * HermodPropertiesMBean.java
 *
 * Created on 11 de agosto de 2004, 18:52
 */
package gov.sir.hermod;


/**
 *
 * @author  mrios
 */
public interface HermodPropertiesMBean {
    /**
     * Obtener el valor de una propiedad dada una llave
     * @param key  llave de la que se desea obtener el valor
     * @return Valor asociado a la llave key
     */
    String getProperty(String key);

    /**
     * Actualizar el valor asociado a una llave
     * @param key  Llave
     * @param value Nuevo valor de la llave
     */
    void setProperty(String key, String value);

    /**
     * Cargar las propiedades de el MBean
     */
    void loadProperties();

    /**
     * Persistir las propiedades que estan en el MBean
     */
    void storeProperties();
}

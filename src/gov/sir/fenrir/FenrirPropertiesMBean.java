
package gov.sir.fenrir;


/**
 * 
 * @author jfrias
 *
 */
public interface FenrirPropertiesMBean {
	
    String getProperty(String key);

    void setProperty(String key, String value);

    void loadProperties();

    void storeProperties();
}

/*
 * Created on 04-ene-2005
*/
package gov.sir.forseti;

/**
 * @author jfrias
*/
public interface ForsetiPropertiesMBean {
	String getProperty(String key);

	void setProperty(String key, String value);

	void loadProperties();

	void storeProperties();
}

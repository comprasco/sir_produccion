package gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators;

import java.util.Enumeration;
import java.util.Properties;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DrawableComponentAttributes {
  Properties properties = new java.util.Properties() ;
  public DrawableComponentAttributes() {
  }

  public void addProperty( String propertyName, String propertyValue ) {
    properties.put( propertyName, propertyValue );
  }

  public void generateAttributeValuePairs( StringBuffer buffer ) {
  	 final String SEPARATOR = " ";
     StringBuffer temporalBuffer = new StringBuffer();
     for( Enumeration enumeration= properties.keys(); enumeration.hasMoreElements();  )   {
       String key = (String)enumeration.nextElement();
       String value = properties.getProperty( key );
       temporalBuffer.append( key  + "=" + "'" + value +  "'" );
       temporalBuffer.append(  SEPARATOR );
     }
     buffer.append( temporalBuffer );
  }

  public static
  void main( String[] args ) {
    
  	DrawableComponentAttributes dca = new DrawableComponentAttributes();
  	dca.addProperty("readonly","true" );
  	StringBuffer buffer = new StringBuffer(); 
    dca.generateAttributeValuePairs( buffer );
        
  }

}

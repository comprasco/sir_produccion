package gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators;

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
public class IndexSelectorComponent
    extends AbstractDrawableComponent {

        /* (non-Javadoc)
         * @see pck2.DrawableComponent#generateXmlTextFragment(java.lang.StringBuffer)
         */
    protected void generateXmlTextFragment_HtmImp(StringBuffer buffer) {
            StringBuffer result = new StringBuffer();

            result.append( "<input" );
            result.append( " id='" + this.getId() + "'" );
            result.append( " name='" + this.getId() + "'" );
            result.append( " type='checkbox'" );
            result.append( " value='"+ this.getValue().toString() +"' " );

            DrawableComponentAttributes atts;
            if( null != ( atts = getAttributes() ) ) {
              atts.generateAttributeValuePairs( result );
            }

            result.append( "/>" );

            buffer.append( result );
    }
	protected void generateXmlTextFragment_CFormsImp(StringBuffer buffer) {
	  StringBuffer result = new StringBuffer();

	  result.append( "<fi:booleanfield" );
	  result.append( " id='" + this.getId() + "'" );
	  result.append( ">" );
	  result.append( " <fi:value>"+ this.getValue().toString() +"</fi:value>" );
	  result.append( " <fi:styling type='index' />" );
	  result.append( "</fi:booleanfield>" );

	  buffer.append( result );
	  // throw new RuntimeException(IndexSelectorComponent.class.getName() + "no implementada");

	}

}

/*
 * Created on 30-may-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ahurtado
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ListDrawableComponent extends AbstractDrawableComponent {

	private List items; // conjunto de valores; es una lista de Item4ListDrawableComponent

	// inmutable
	// por el momento no hereda de AbstractDrawableComponent
	public static class Item4ListDrawableComponent {

		private String id;
		private String value;

		public Item4ListDrawableComponent( String id, String value ) {

			this.id = id;
			this.value = value;
		}
		/* (non-Javadoc)
		 * @see gov.sir.core.web.helpers.fotocopia.drawablecomponents.GeneratorComponent#generateXmlTextFragment(java.lang.StringBuffer)
		 */
		/**
		 * @return
		 */
		public String getId() {
			return id;
		}

		/**
		 * @return
		 */
		public String getValue() {
			return value;
		}

	}

	public ListDrawableComponent( List items ) {
		this.items = items;
	}

	protected void generateXmlTextFragment_HtmImp(StringBuffer buffer) {
			StringBuffer result = new StringBuffer();

			result.append( "<select" );
			result.append( " id='" + this.getId() + "'" );
			result.append( " name='" + this.getId() + "'" );

                        DrawableComponentAttributes atts;
                        if( null != ( atts = getAttributes() ) ) {
                          atts.generateAttributeValuePairs( result );
                        }

			result.append( ">" );

			Iterator iterator = this.items.iterator();
			for(;iterator.hasNext();){
			  Item4ListDrawableComponent item = (Item4ListDrawableComponent)iterator.next();
			  result.append( "<option" );
			  // result.append( " id='" + item.getId() );
			  result.append( " value='" + item.getId() + "'" );
			  if( ( null != this.getValue() )
			     && this.getValue().equals( item.getId() ) ) {
  			    result.append( " selected='selected'" );
			  }

			  result.append( " >" );
			  result.append( item.getValue().toString() );
			  result.append( "</option>" );
			}


		    result.append( "</select>" );

			buffer.append( result );
	}
	protected void generateXmlTextFragment_CFormsImp(StringBuffer buffer) {
	  StringBuffer result = new StringBuffer();

	  result.append( "<fi:field" );
	  result.append( " id='" + this.getId() + "'" );
	  result.append( ">" );
	  if( this.getValue() != null ) {
		result.append( " <fi:value>"+ this.getValue().toString() +"<fi:value>" );
	  }

	  result.append( " <fi:selection-list>" );

	  Iterator iterator = this.items.iterator();
	  for(;iterator.hasNext();){
		Item4ListDrawableComponent item = (Item4ListDrawableComponent)iterator.next();
		result.append( "<fi:item" );
		result.append( " value='" + item.getId() + "'" );
		result.append( " >" );
		result.append( "<fi:label>" );
		result.append( "" + item.getValue() );
		result.append( "</fi:label>" );
		result.append( "</fi:item>" );

	  }

	  result.append( " </fi:selection-list>" );
	  result.append( "</fi:field>" );

	  buffer.append( result );
	  // throw new RuntimeException(IndexSelectorComponent.class.getName() + "no implementada");

	}

	// TODO: generate junit test

	public static void main(String[] args) {
		StringBuffer stringBuffer = new StringBuffer();
		List items = new ArrayList();

		ListDrawableComponent.Item4ListDrawableComponent item;
		item = new ListDrawableComponent.Item4ListDrawableComponent( "id1", "value1" );
		items.add( item );
		item = new ListDrawableComponent.Item4ListDrawableComponent( "id2", "value2" );
		items.add( item );

		ListDrawableComponent list = new ListDrawableComponent( items );
		list.setId("list1");
		list.generateXmlTextFragment( stringBuffer );
		System.out.print( stringBuffer.toString() );
	}
}

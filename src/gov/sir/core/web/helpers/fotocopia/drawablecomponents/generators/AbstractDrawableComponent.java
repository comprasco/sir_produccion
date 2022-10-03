/*
 * Created on 28-may-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.web.helpers.fotocopia.drawablecomponents.generators;

/**
 * @author ahurtado
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class AbstractDrawableComponent implements DrawableComponent {
        protected DrawableComponentAttributes attributes;

	protected int renderMode = CFORMS_RENDER;

	public String id;
	private Object value;

	/**
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param object
	 */
	public void setValue(Object object) {
		value = object;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param string
	 */
	public void setId(Object string) {
		id = (String)string;
	}

	/** TODO: change this TemplateMethod after=createAFactory */
	public final void generateXmlTextFragment(StringBuffer buffer) {
		if( DrawableComponent.XHTML_RENDER == this.renderMode ) {
			generateXmlTextFragment_HtmImp( buffer );
		}
		else if( DrawableComponent.CFORMS_RENDER == this.renderMode ) {
			generateXmlTextFragment_CFormsImp( buffer );
		}
		else {
			throw new RuntimeException("Invalid RenderMode:" + AbstractDrawableComponent.class.getName() );
		}
	}

        public void setRenderMode( int renderMode ) {
          this.renderMode = renderMode;
        }

  public void setAttributes(DrawableComponentAttributes attributes) {
    this.attributes = attributes;
  }

  public int getRenderMode() {
           return this.renderMode;
        }

  public DrawableComponentAttributes getAttributes() {
    return attributes;
  }

  protected abstract void generateXmlTextFragment_HtmImp( StringBuffer buffer );
	protected abstract void generateXmlTextFragment_CFormsImp( StringBuffer buffer );

}

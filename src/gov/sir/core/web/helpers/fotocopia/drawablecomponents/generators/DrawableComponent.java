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
public interface DrawableComponent extends GeneratorComponent {

	int XHTML_RENDER = 0;
	int CFORMS_RENDER = 1;

	// form definition
	void setId( Object id );
	void setValue( Object value );
        void setRenderMode( int renderMode );
        void setAttributes( DrawableComponentAttributes attributes );

}

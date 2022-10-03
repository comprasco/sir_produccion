package gov.sir.core.web.helpers.administracion.reportes;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspWriter;

public class LegendHelper {
	
	public final String CONTEXT_PATH = "CONTEXT_PATH"; 
	public final String HTML_MESSAGE = "HTML_MESSAGE"; 
	
	
	public void configure( Map properties ) {
		
	  String contextPath = "" + (String)properties.get( CONTEXT_PATH );
	  imagePrefix = contextPath + "/jsp/images/" ;

	}
	
	private String message;
	private String regionId;
	private String regionName;
	
	public void setMessage( String message ) {
		this.message = message;
	}
	public void setRegionId( String regionId ) {
		this.regionId = regionId;
	}
	
	public void setRegionName( String regionName ) {
		this.regionName = regionName;
	}
	
	
	public void render( JspWriter out ) throws IOException {
	     
		StringBuffer buffer = new StringBuffer();
		
		write( buffer, this.regionId, this.regionName, this.message );
		
		out.write( buffer.toString() );
		
	}
		
	
	
	 public void write( StringBuffer buffer, String regionId, String regionName, String regionDescription ) {
		 
			 StringBuffer tempBuffer = new StringBuffer();
			 
			 PopupDisplayerBuilder popupDisplayerBuilder = new PopupDisplayerBuilder();
			 
			 PopupDisplayer popupDisplayer 
			   = popupDisplayerBuilder.build( regionId, regionName, "" + regionDescription, PopupDisplayerBuilder.LOCK_ENABLED , this );
				
			 popupDisplayer.write( tempBuffer );
			 
			 // String htm_Image = imagePrefix + "lock-enabled.gif";
			 // String htm_Alt   = "lock-enabled";
			 // String htm_JsMessage = "region " + getRegionName()+  " " + " deshabilitada por privilegios." ;
			 
			 // tempBuffer.append( "<a href='#' onClick='javascript:alert(\"" + htm_JsMessage +  "\")' >" );
			 // tempBuffer.append( "<img src='" + htm_Image + "' alt='" + htm_Alt + "' width='16' height='16' border='0' />" );
			 // tempBuffer.append( "</a>" );
			 
			 buffer.append( tempBuffer );
			 
		 }
	
	
	
	 String imagePrefix = "";
	
	
	 // :: inner-class ----------------------------------------------------------
	 protected static class PopupDisplayerBuilder {	 
	 
	    int state = LOCK_NULL;
		 
		 public static final int LOCK_NULL     = 0;	 
		 public static final int LOCK_ENABLED  = 1;
		 public static final int LOCK_DISABLED = 2;
		 
		 static final String[] images_Type1 = new String[] {
			   "privileged/3/legend-null.gif"
			 , "privileged/3/legend-enabled.gif"
			 , "privileged/3/legend-disabled.gif"
		 };
		 static final String closeImage = "privileged/2/close.gif";
		 static final String[] stateText_es = new String[] {
			   "ayuda (0)"
			 , "ayuda (1)"
			 , "ayuda (2)"
		 };
	 
	 
	    public PopupDisplayerBuilder(){
		 };
		 
		 public PopupDisplayer 
		 build( String $id, String title, String description, int state, LegendHelper tag ){
		    String popupDisplayerTitle = "region " + title;
			 
			 String stateMessage = "";
			 String stateImage   = "";
			  
			 if( ( state >= 0 )
			   &&( state < stateText_es.length ) ) {
				stateMessage = stateText_es[state];
				stateImage   = images_Type1[state];
			 }
			 
		    PopupDisplayer result = new PopupDisplayer( $id, popupDisplayerTitle, description, stateMessage );
			 result.setPopupResourcesImagePrefix( tag.imagePrefix );
			 result.setImgSrcSource( stateImage );
			 result.setCloseImage( closeImage );
			 
			 return result;
		 }
		 
	 }
	 // -------------------------------------------------------------------------

	 // :: inner-class ----------------------------------------------------------
	 protected static class PopupDisplayer {
	    public PopupDisplayer(){
			 
		 }
	 
	    public PopupDisplayer( String $id, String title, String description, String state ){
		    this();
			 
			 this.$id          = $id;
			 this.title        = title;
			 this.description  = description;
			 this.state        = state;  
		 }
		 
		 public void setPopupResourcesImagePrefix( String popupResourcesImagePrefix ){
			 this.popupResourcesImagePrefix = popupResourcesImagePrefix;
		 }
		 
		 protected String popupResourcesImagePrefix; 
		 
		 protected String $id;
		 protected String title;
		 protected String description;
		 protected String state;
		 
		 protected String imgSrcSource;
		 protected String closeImage;
		 
		 
		 public void setImgSrcSource( String imgSrcSource ) {
		    this.imgSrcSource = imgSrcSource;
		 }
		 
		 public void setCloseImage( String closeImage ) {
		    this.closeImage = closeImage;
		 }

		 
		 public void write( StringBuffer buffer ){
		 
		    final String preffix = "help";
		    final String wndPreffix = preffix + "Win";
			 
			 String _imgSrcClose  = popupResourcesImagePrefix + this.closeImage;
			 String _imgSrcSource = popupResourcesImagePrefix + this.imgSrcSource;
			 
			 buffer.append( "<div id='" + preffix + $id + "' name='"+ preffix + $id + "' style='visibility:hidden;position:absolute;'" + " class='forms-help'" + " >" );
			 buffer.append( " <span style='float:right' >" );
			 buffer.append( "  <a onclick='"+wndPreffix+ $id +".hidePopup();return false;' href='#'>" );
			 buffer.append( "   <img height='6' alt='close' src='" + _imgSrcClose + "' width='6' align='top' border='0'/>" );
			 buffer.append( "  </a>" );
			 buffer.append( " </span>" );
			 buffer.append( " <span>" );
			 buffer.append( (null==title)?(""):("<b>"+title+"</b>") );
			 buffer.append( ( null==state )?(""):( " [" + state + "]" ) );
			 buffer.append( ( null==title )?(""):(": ") );
			 buffer.append( "" + description );
			 buffer.append( " </span>" );
			 buffer.append( "</div>" );

			 buffer.append( "<script type='text/javascript'>" );
			 buffer.append( "var " + wndPreffix+ $id + "= " + "forms_createPopupWindow('" + preffix + $id +"');" );
			 buffer.append( "</script>" );

			 buffer.append( "<a id='"+$id+"' name='"+ $id + "' onclick=\"" + wndPreffix+ $id + ".showPopup('" +$id +"');return false;\" href='#' >" );
			 buffer.append( "<img alt='" + preffix + "popup' src='" + _imgSrcSource + "' border='0' width='16' height='16'>" );
			 buffer.append( "</a>" );
			 buffer.append( "</script>" );
 
		 }

	 }
	 // -------------------------------------------------------------------------	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

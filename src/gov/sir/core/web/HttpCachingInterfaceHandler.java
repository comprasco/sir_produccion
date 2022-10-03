package gov.sir.core.web;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.servlet.HttpConstants;
import org.directwebremoting.servlet.InterfaceHandler;

/**
 * This class is the InterfaceHandler for DWRHandler which
 * will add the functionality to add las modified header and
 * check if modified since header.
 * @since Jul 19, 2007
 */  

public class HttpCachingInterfaceHandler extends InterfaceHandler  

{
	//Store the application startup time. This will be the time we will set
	//as the Last-Modified time for all the interface scripts
	private final long lastUpdatedTime = (System.currentTimeMillis() / 1000) * 1000;  

	public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		long ifModifiedSince = req.getDateHeader(HttpConstants.HEADER_IF_MODIFIED);
		if (ifModifiedSince < lastUpdatedTime) {
			//If the browser does not have the script in the cache or the cached copy is stale
			//set the Last-Modified date header and send the new script file
			//Note: If the browser does not have the script in its cache ifModifiedSince will be -1  

			resp.setDateHeader(HttpConstants.HEADER_LAST_MODIFIED, lastUpdatedTime);
			super.handle(req, resp);
		} else {
			//If the browser has current version of the file, dont send the script. Just say it has not changed
			resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
		}
	}
}


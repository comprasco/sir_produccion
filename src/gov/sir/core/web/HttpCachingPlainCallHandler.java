/*
 * Copyright 2005 Joe Walker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.sir.core.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.servlet.HttpConstants;
import org.directwebremoting.servlet.PlainCallHandler;

/**
 * @author Ingenian Software ltda
 */
public class HttpCachingPlainCallHandler extends PlainCallHandler
{
    private final long lastUpdatedTime = (System.currentTimeMillis() / 1000) * 1000;
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {       
        long ifModifiedSince = req.getDateHeader(HttpConstants.HEADER_IF_MODIFIED);        
        resp.setDateHeader("Expires",lastUpdatedTime + 1000*60*60*24*365);
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
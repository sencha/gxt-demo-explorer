/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2021, Sencha Inc.
 *
 * licensing@sencha.com
 * http://www.sencha.com/products/gxt/license/
 *
 * ================================================================================
 * Commercial License
 * ================================================================================
 * This version of Sencha GXT is licensed commercially and is the appropriate
 * option for the vast majority of use cases.
 *
 * Please see the Sencha GXT Licensing page at:
 * http://www.sencha.com/products/gxt/license/
 *
 * For clarification or additional options, please contact:
 * licensing@sencha.com
 * ================================================================================
 *
 *
 *
 *
 *
 *
 *
 *
 * ================================================================================
 * Disclaimer
 * ================================================================================
 * THIS SOFTWARE IS DISTRIBUTED "AS-IS" WITHOUT ANY WARRANTIES, CONDITIONS AND
 * REPRESENTATIONS WHETHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, MERCHANTABLE QUALITY,
 * FITNESS FOR A PARTICULAR PURPOSE, DURABILITY, NON-INFRINGEMENT, PERFORMANCE AND
 * THOSE ARISING BY STATUTE OR FROM CUSTOM OR USAGE OF TRADE OR COURSE OF DEALING.
 * ================================================================================
 */
package com.sencha.gxt.examples.resources.server;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import org.apache.commons.io.FilenameUtils;

/**
 * Thia rpc service implementation base class loads serialization policies from a server relative url
 * rather than from the fully qualified module base url provided by the client.  This is useful if your
 * application is fronted by a proxy which rewrites your url path in ways that break serialization.
 *
 * @see RemoteServiceServlet
 */
public class RelativeRemoteServiceServlet extends RemoteServiceServlet {

  /**
   * This serialization policy implementation loads from a server relative url
   * rather than from the fully qualified module base url provided by the client.
   *
   * @see RemoteServiceServlet#doGetSerializationPolicy(HttpServletRequest, String, String)
   */
  @Override
  protected SerializationPolicy doGetSerializationPolicy(HttpServletRequest request, String moduleBaseURL, String strongName) {
    // extract the module base name, which is always the last portion of the fully qualified url
    String moduleBaseName = FilenameUtils.getName(FilenameUtils.getPathNoEndSeparator(moduleBaseURL));
    // construct a server relative url — yes, this is legal: https://en.wikipedia.org/wiki/Uniform_Resource_Locator
    String moduleRelativeURL = request.getScheme() + ":" + request.getContextPath() + "/" + moduleBaseName + "/";
    // load as normal, but using our server relative url instead of the fully qualified url
    return super.doGetSerializationPolicy(request, moduleRelativeURL, strongName);
  }

}

/**
 * Copyright © 2006-2016 Web Cohesion (info@webcohesion.com)
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
package com.webcohesion.enunciate.modules.idl;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.StringModel;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 * Outputs the qname in the form of "prefix:namespace".  The prefix is looked up in the model.
 *  
 * @author Ryan Heaton
 */
public class QNameModel extends StringModel {

  private final QName qname;
  private final Map<String, String> namespacePrefixes;

  public QNameModel(QName qname, BeansWrapper wrapper, Map<String, String> namespacePrefixes) {
    super(qname, wrapper);
    this.qname = qname;
    this.namespacePrefixes = namespacePrefixes;
  }

  @Override
  public String getAsString() {
    String string = qname.getLocalPart();
    String prefix = lookupPrefix(qname.getNamespaceURI());
    if (prefix != null) {
      string = prefix + ":" + string;
    }
    return string;
  }

  /**
   * Convenience method to lookup a namespace prefix given a namespace.
   *
   * @param namespace The namespace for which to lookup the prefix.
   * @return The namespace prefix.
   */
  protected String lookupPrefix(String namespace) {
    if ((namespace == null) || ("".equals(namespace))) {
      return null;
    }

    return this.namespacePrefixes.get(namespace);
  }

  @Override
  public String toString() {
    return getAsString();
  }


}

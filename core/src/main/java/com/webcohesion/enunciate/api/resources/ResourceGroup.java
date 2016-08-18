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
package com.webcohesion.enunciate.api.resources;

import com.webcohesion.enunciate.api.HasStyles;
import com.webcohesion.enunciate.api.PathSummary;
import com.webcohesion.enunciate.javac.javadoc.JavaDoc;

import javax.lang.model.element.AnnotationMirror;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan Heaton
 */
public interface ResourceGroup extends HasStyles {

  String getSlug();

  String getLabel();

  String getSortKey();

  String getDescription();

  String getDeprecated();

  List<PathSummary> getPaths();

  String getRelativeContextPath();

  List<Resource> getResources();

  Map<String, AnnotationMirror> getAnnotations();

  JavaDoc getJavaDoc();
}

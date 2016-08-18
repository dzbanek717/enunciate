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
package com.webcohesion.enunciate.util.freemarker;

import com.webcohesion.enunciate.EnunciateLogger;
import freemarker.core.Environment;
import freemarker.template.*;
import freemarker.template.utility.DeepUnwrap;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author Ryan Heaton
 */
public class FileDirective implements TemplateDirectiveModel {

  private final File outputDir;
  private final EnunciateLogger logger;

  public FileDirective(File outputDir, EnunciateLogger logger) {
    this.outputDir = outputDir;
    this.logger = logger;

    if (!outputDir.exists()) {
      outputDir.mkdirs();
    }
    else if (!outputDir.isDirectory()) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    String filePath = (String) DeepUnwrap.unwrap((TemplateModel)  params.get("name"));
    if (filePath == null) {
      throw new TemplateModelException("A 'name' parameter must be provided to create a new file.");
    }

    File dir = this.outputDir;

    String pckg = (String) DeepUnwrap.unwrap((TemplateModel) params.get("package"));
    if ((pckg != null) && (pckg.trim().length() > 0)) {
      String[] dirnames = pckg.split("\\.");
      for (String dirname : dirnames) {
        dir = new File(dir, dirname);
      }
    }


    String charset = (String) DeepUnwrap.unwrap((TemplateModel)  params.get("charset"));
    if (charset == null) {
      charset = "utf-8";
    }

    File output = new File(dir, filePath);
    if (!output.getParentFile().exists()) {
      output.getParentFile().mkdirs();
    }

    PrintWriter writer = new PrintWriter(output, charset);
    this.logger.debug("Writing %s...", output);
    body.render(writer);
    writer.close();
  }
}

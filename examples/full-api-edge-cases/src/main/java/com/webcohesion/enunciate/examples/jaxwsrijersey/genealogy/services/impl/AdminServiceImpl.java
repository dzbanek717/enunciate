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
package com.webcohesion.enunciate.examples.jaxwsrijersey.genealogy.services.impl;

import com.webcohesion.enunciate.examples.jaxwsrijersey.genealogy.data.PersonAdmin;
import com.webcohesion.enunciate.examples.jaxwsrijersey.genealogy.services.AdminService;

import javax.jws.WebService;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path ("/admin")
@WebService(endpointInterface = "com.webcohesion.enunciate.examples.jaxwsrijersey.genealogy.services.AdminService" )
public class AdminServiceImpl implements AdminService {

  @Path("/admin/person/{id}")
  public PersonAdmin readAdminPerson(@PathParam("id") String id) {
    return new PersonAdmin();
  }

}

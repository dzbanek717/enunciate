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
namespace Jaxws.Ri.Rest {

  using System;
  using Com.Webcohesion.Enunciate.Examples.Jaxwsrijersey.Genealogy.Services;
  using Com.Webcohesion.Enunciate.Examples.Jaxwsrijersey.Genealogy.Cite;
  using Com.Webcohesion.Enunciate.Examples.Jaxwsrijersey.Genealogy.Data;
  using System.Web.Services.Protocols;
  using System.Collections;
  using System.Collections.Generic;

  public class FullAPITest {

    public static void Main(string[] args) {
      SourceService sourceService = new SourceService("http://localhost:8080/full/sources/source");
      Source source = sourceService.GetSource("valid");
      Assert.IsNotNull(source);
      Assert.AreEqual("valid", source.Id);
      Assert.AreEqual("uri:some-uri", source.Link);
      Assert.AreEqual("some-title", source.Title);
      Assert.IsNull(sourceService.GetSource("invalid"));

      try {
        sourceService.GetSource("throw");
        Assert.Fail();
      }
      catch (SoapException) {
        //fall through...
      }

      try {
        sourceService.GetSource("unknown");
        Assert.Fail();
      }
      catch (SoapException) {
        //fall through...
      }

      Assert.AreEqual("newid", sourceService.AddInfoSet("somesource", new InfoSet()));
      Assert.AreEqual("okay", sourceService.AddInfoSet("othersource", new InfoSet()));
      Assert.AreEqual("resourceId", sourceService.AddInfoSet("resource", new InfoSet()));
      try {
        sourceService.AddInfoSet("unknown", new InfoSet());
        Assert.Fail("Should have thrown the exception.");
      }
      catch (SoapException) {
        //fall through...
      }

      PersonService personService = new PersonService("http://localhost:8080/full/PersonServiceService");
      List<string> pids = new List<string>();
      pids.Add("id1");
      pids.Add("id2");
      pids.Add("id3");
      pids.Add("id4");
      Person[] persons = personService.ReadPersons(pids);
      Assert.AreEqual(4, persons.Length);
      foreach (Person pers in persons) {
        Assert.IsTrue(pids.Contains(pers.Id));
        Assert.IsNotNull(pers.Events);
        Assert.IsTrue(pers.Events.Count > 0);
        Assert.IsNotNull(pers.Events[0].Date);
//        Assert.AreEqual(1970, pers.Events[0].Date.Year);
      }

      Person[] empty = personService.ReadPersons(null);
      Assert.IsTrue(empty == null || empty.Length == 0);

      personService.DeletePerson("somebody", "my message");
      try {
        personService.DeletePerson(null, null);
        Assert.Fail("Should have thrown the exception.");
      }
      catch (SoapException e) {
        //fall through...
      }

      Person person = new Person();
      person.Id = "new-person";
      Assert.AreEqual("new-person", personService.StorePerson(person).Id);

      System.Text.UTF8Encoding encoding = new System.Text.UTF8Encoding();
      byte[] pixBytes = encoding.GetBytes("this is a bunch of bytes that I would like to make sure are serialized correctly so that I can prove out that attachments are working properly");
      person.Picture = pixBytes;

      byte[] returnedPix = personService.StorePerson(person).Picture;
      Assert.AreEqual("this is a bunch of bytes that I would like to make sure are serialized correctly so that I can prove out that attachments are working properly", encoding.GetString(returnedPix));

      RelationshipService relationshipService = new RelationshipService("http://localhost:8080/full/RelationshipServiceService");
      Relationship[] list = relationshipService.GetRelationships("someid");
      for (int i = 0; i < list.Length; i++) {
        Relationship relationship = list[i];
        Assert.AreEqual(i.ToString(), relationship.Id);
      }

      try {
        relationshipService.GetRelationships("throw");
        Assert.Fail("Should have thrown the relationship service exception, even though it wasn't annotated with @WebFault.");
      }
      catch (SoapException e) {
        //fall through
      }

      relationshipService.Touch();
      AssertionService assertionService = new AssertionService("http://localhost:8080/full/AssertionServiceService");
      Assertion[] assertions = assertionService.ReadAssertions();
      Assertion gender = assertions[0];
      Assert.AreEqual("gender",gender.Id);
      Assert.IsTrue(gender is Gender);
      Assertion name = assertions[1];
      Assert.AreEqual("name",name.Id);
      Assert.IsTrue(name is Name);      	
    }
  }

  public class Assert {

    public static void IsTrue(bool flag) {
      if (!flag) {
        throw new Exception();
      }
    }

    public static void Fail() {
      throw new Exception();
    }

    public static void Fail(string message) {
      throw new Exception(message);
    }

    public static void IsNotNull(object obj) {
      if (obj == null) {
        throw new Exception();
      }
    }

    public static void IsNull(object obj) {
      if (obj != null) {
        throw new Exception();
      }
    }

    public static void AreEqual(object obj1, object obj2) {
      if (!Object.Equals(obj1, obj2)) {
        throw new Exception("Expected: " + obj1 + ", got " + obj2);
      }
    }

  }
}

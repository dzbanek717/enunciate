/*
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
var fs = require('fs');
var api = require('./api.js');

// Capture the arguments
var classNamespaceParts = process.argv[2].split('.');
var infile = process.argv[3];
var outfile = process.argv[4];

// Load json from a file
var fileContents = fs.readFileSync(infile, 'utf8');

// Deserialize to an object
var parsed = JSON.parse(fileContents);
var classRef = getClassReference(api, classNamespaceParts);
var o = new classRef(parsed);

// Serialize object to json
fs.writeFileSync(outfile, JSON.stringify(o));

/**
 * Given an array of class namespace parts ['Com', 'Webcohesion', 'Enunciate', 'Line'],
 * fetch and return a reference to the class
 */
function getClassReference(api, classNamespaceParts){
  var classRef = api;
  classNamespaceParts.forEach(function(part){
    classRef = classRef[part];
  });
  return classRef;
}
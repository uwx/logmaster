'use strict';

// crypto is actually a browser global. hah.
const crypto = require('crypto');
const util = require('util');
const xml2js = promisify(require('xml2js'));
const vkbeautify = require('vkbeautify');
const fs = require('mz/fs');

function buildXml(rootObj, rootName) {
  var name = '<?xml version="1.0" encoding="UTF-8"?>\n';
  var xml = name;
  if (rootName) {
    xml += '<' + rootName + '>\n';
  }

  (function traverse(obj) {
    Object.keys(obj).forEach(function(key) {
      var open = '<' + key + '>',
        close = '</' + key + '>\n',
        nonObj = (obj[key] && {}.toString.call(obj[key]) !== '[object Object]'),
        isArray = Array.isArray(obj[key]),
        isFunc = (typeof obj[key] === 'function');

      if (isArray) {
        obj[key].forEach(function(xmlNode) {
          var childNode = {};
          childNode[key] = xmlNode;
          traverse(childNode);
        });
        return;
      }

      xml += open;
      if (nonObj) {
        xml += (isFunc) ? obj[key]() : obj[key];
        xml += close;
        return;
      }

      xml += '\n';
      traverse(obj[key]);
      xml += close;
    });
  }(rootObj));

  if (rootName) {
    xml += '</' + rootName + '>';
  }
  return xml;
}


function promisify(object) {
  const out = {};
  Object.keys(object).forEach(k => {
    out[k] = typeof object[k] == 'function' ? util.promisify(object[k]) : object[k];
  });
  return out;
}

function checksum(str, algorithm, encoding) {
  return crypto
    .createHash(algorithm || 'md5')
    .update(str, 'utf8') // If data is a Buffer, TypedArray, or DataView, then input_encoding is ignored.
    .digest(encoding || 'hex');
}

(async() => {
  /*
  const data = await fs.readFile('maven-metadata.xml');
  console.log(Object.prototype.toString.call(data), data);
  const md5 = checksum(data, 'md5');
  const sha1 = checksum(data, 'sha1');

  console.log(md5, sha1);

  const metadata1 = await xml2js.parseString(await fs.readFile('maven-metadata.xml', 'utf8'));
  const metadata2 = await xml2js.parseString(await fs.readFile('maven-metadata_2.xml', 'utf8'));

  const metadata3 = metadata1;
  console.log(metadata3);
  metadata3.metadata.versioning = metadata1.metadata.versioning.concat(metadata2.metadata.versioning);
  */
  //console.log(JSON.stringify(metadata3, null, 2));
  //console.log(JSON.stringify(assign(metadata1, metadata2), null, 2));

  //
  //
  //

  const mavenPackage = (process.env.MAVEN_PROJECT_PACKAGE_NAME || 'fallk.jfunktion').replace(/\./g, '/');
  process.env.USERDIR = process.env.USERDIR || './';

  // the build dir (the current cd dir)
  const m1 = await xml2js.parseString(await fs.readFile('./target/mvn-repo/' + mavenPackage + '/maven-metadata.xml', 'utf8'));
  // the gh-pages dir (in the userdir variable)
  const m2 = await xml2js.parseString(await fs.readFile(process.env.USERDIR + '/' + mavenPackage + '/maven-metadata.xml', 'utf8'));

  const m3 = m1;
  m3.metadata.versioning[0].latest = m3.metadata.versioning[0].release; //not sure
  m3.metadata.versioning[0].versions[0].version = m1.metadata.versioning[0].versions[0].version.concat(m2.metadata.versioning[0].versions[0].version);

  console.log(JSON.stringify(m3, null, 2));

  const output = vkbeautify.xml(buildXml(m3), 2);
  console.log(output);

  await fs.writeFile(process.env.USERDIR == './' ? './maven-metadata.xml' : './target/mvn-repo/' + mavenPackage + '/maven-metadata.xml', output);
  await fs.writeFile(process.env.USERDIR == './' ? './maven-metadata.xml.md5' : './target/mvn-repo/' + mavenPackage + '/maven-metadata.xml.md5', checksum(output));
  await fs.writeFile(process.env.USERDIR == './' ? './maven-metadata.xml.sha1' : './target/mvn-repo/' + mavenPackage + '/maven-metadata.xml.sha1', checksum(output, 'sha1'));
})();

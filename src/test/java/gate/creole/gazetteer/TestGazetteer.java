/*
 *  TestGazetteer.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 25/10/2000
 *
 *  $Id: TestGazetteer.java 19218 2016-04-09 17:15:09Z markagreenwood $
 */

package gate.creole.gazetteer;

import gate.AnnotationSet;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.test.GATEPluginTestCase;

public class TestGazetteer extends GATEPluginTestCase {

  @Override
  public void tearDown() throws Exception {
  } // tearDown

  /** Test the default tokeniser */
  public void testDefaultGazetteer() throws Exception {
    //get a document
	  Document doc = Factory.newDocument(this.getClass().getResource("/tests/doc0.html"));

    //create a default gazetteer
    DefaultGazetteer gaz = (DefaultGazetteer) Factory.createResource(
                          "gate.creole.gazetteer.DefaultGazetteer");

    //runtime stuff
    gaz.setDocument(doc);
    gaz.setAnnotationSetName("GazetteerAS");
    //test with default parameters
    gaz.execute();
    AnnotationSet resultAS = doc.getAnnotations("GazetteerAS");
    assertEquals("Wrong number of annotations produced",63, resultAS.size());
    resultAS.clear();
    
    //test with partial words
    gaz.setWholeWordsOnly(false);
    gaz.execute();
    assertEquals("Wrong number of annotations produced", 400, resultAS.size());
    gaz.setWholeWordsOnly(true);
    resultAS.clear();

    //test with prefix matching
    gaz.setLongestMatchOnly(false);
    gaz.execute();
    assertEquals("Wrong number of annotations produced", 78, resultAS.size());
    gaz.setLongestMatchOnly(true);
    resultAS.clear();
    Factory.deleteResource(gaz);
    
    //test with case insensitive
    FeatureMap fm = Factory.newFeatureMap();
    fm.put(DefaultGazetteer.DEF_GAZ_CASE_SENSITIVE_PARAMETER_NAME, false);
    gaz = (DefaultGazetteer) Factory.createResource(
    "gate.creole.gazetteer.DefaultGazetteer", fm);
    gaz.setDocument(doc);
    gaz.setAnnotationSetName("GazetteerAS");
    gaz.execute();
    assertEquals("Wrong number of annotations generated", 104, resultAS.size());
    gaz.setCaseSensitive(true);
    resultAS.clear();
    Factory.deleteResource(gaz);
    Factory.deleteResource(doc);
  }
  
  /**
   * Tests additions and deletions from the gazetteer in both case
   * sensitive and case insensitive modes
   */
  public void testDynamicMethods() throws Exception {

    
    for (int i = 0 ; i < 2 ; ++i) {
     
      FeatureMap params = Factory.newFeatureMap();
      params.put(DefaultGazetteer.DEF_GAZ_CASE_SENSITIVE_PARAMETER_NAME, i != 0);
      
      DefaultGazetteer gaz = (DefaultGazetteer) Factory.createResource(
                            "gate.creole.gazetteer.DefaultGazetteer",params);
            
      Document doc = Factory.newDocument("RandomWord gazetteer tester");    
      gaz.setDocument(doc);
      
      gaz.execute();    
      assertEquals("Wrong number of lookup annotations in dynamic test 1/3", 0, doc.getAnnotations().get("Lookup").size());
      doc.getAnnotations().clear();
      
      Lookup lookup = new Lookup("random.lst","random","word","en");    
      gaz.add("RandomWord", lookup);
      
      gaz.execute();
      assertEquals("Wrong number of lookup annotations in dynamic test 2/3", 1, doc.getAnnotations().get("Lookup").size());
      doc.getAnnotations().clear();
      
      gaz.removeLookup("RandomWord", lookup);
      gaz.execute();
      assertEquals("Wrong number of lookup annotations in dynamic test 3/3", 0, doc.getAnnotations().get("Lookup").size());
      
      Factory.deleteResource(gaz);
    
    }
    
  }

} // TestGazetteer

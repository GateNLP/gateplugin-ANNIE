package com.ontotext.gate.gazetteer;
/*
 *  HashGazetteer.java
 *
 *  OntoText Lab.
 *
 *  borislav popov , 09/11/2001
 *
 *  $Id: TestHashGazetteer.java 19218 2016-04-09 17:15:09Z markagreenwood $
 */

import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.test.GATEPluginTestCase;

/**
 * Tests the HashGazetteer.
 */
public class TestHashGazetteer extends GATEPluginTestCase {

  private static final String GAZ_AS = "GazetteerAS";

  @Override
  public void tearDown() throws Exception {
  } // tearDown

  /** Test the default tokeniser */
  public void testHashGazetteer() throws Exception {

    Document doc = Factory.newDocument(this.getClass().getResource("/tests/doc0.html"));
    
    //create a default gazetteer
    FeatureMap params = Factory.newFeatureMap();
    HashGazetteer gaz = (HashGazetteer) Factory.createResource(
                          "com.ontotext.gate.gazetteer.HashGazetteer", params);

    //runtime stuff
    gaz.setDocument(doc);
    gaz.setAnnotationSetName(GAZ_AS);
    gaz.execute();

    assertTrue("the Annotation set resulting of the execution of the OntoText "
            +"Natural Gazetteer is empty."
            ,!doc.getAnnotations(GAZ_AS).isEmpty());

    //check whether the annotations are as expected
    assertEquals("wrong number of lookup annotations found",76,
      doc.getAnnotations(GAZ_AS).size());

  } // testHashGazetteer();

  /** Test suite routine for the test runner */
  /*public static Test suite() {
    return new TestSuite(TestHashGazetteer.class);
  } // suite*/

} // TestHashGazetteer

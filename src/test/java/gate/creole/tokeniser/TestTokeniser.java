/*
 *  TestTokeniser.java
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
 *  $Id: TestTokeniser.java 19218 2016-04-09 17:15:09Z markagreenwood $
 */


package gate.creole.tokeniser;

import gate.Document;
import gate.Factory;
import gate.Gate;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestTokeniser extends TestCase{

  public TestTokeniser(String name) {
    super(name);
  }



  @Override
  public void tearDown() throws Exception {
  } // tearDown

  /** Test the default tokeniser */
  public void testDefaultTokeniser() throws Exception {
    //get a document
	  Document doc = Factory.newDocument(this.getClass().getResource("/tests/doc0.html"));
	  
    //create a default tokeniser
   DefaultTokeniser tokeniser = (DefaultTokeniser) Factory.createResource(
                          "gate.creole.tokeniser.DefaultTokeniser");

    tokeniser.setDocument(doc);
    tokeniser.setAnnotationSetName("TokeniserAS");
    tokeniser.execute();
    assertTrue(! doc.getAnnotations("TokeniserAS").isEmpty());
  }

  /** Test suite routine for the test runner */
  public static Test suite() {
    return new TestSuite(TestTokeniser.class);
  } // suite

  public static void main(String[] args) {
    try{
      Gate.init();
      TestTokeniser testTokeniser1 = new TestTokeniser("");
      testTokeniser1.setUp();
      testTokeniser1.testDefaultTokeniser();
      testTokeniser1.tearDown();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}

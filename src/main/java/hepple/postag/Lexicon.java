/*
 *  Lexicon.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  HepTag was originally written by Mark Hepple, this version contains
 *  modifications by Valentin Tablan and Niraj Aswani.
 *
 *  $Id: Lexicon.java 20118 2017-02-14 14:43:59Z markagreenwood $
 */
package hepple.postag;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Title:        HepTag
 * Description:  Mark Hepple's POS tagger
 * Copyright:    Copyright (c) 2001
 * Company:      University of Sheffield
 * @author Mark Hepple
 * @version 1.0
 */

import gate.util.BomStrippingInputStreamReader;

/**
 * A {@link java.util.HashMap} that maps from lexical entry
 * ({@link java.lang.String}) to possible POS categories
 * ({@link java.util.List}
 */
class Lexicon extends HashMap<String,List<String>> {

  private static final long serialVersionUID = -2320126076517881896L;

  /**
   * Constructor.
   * @param lexiconURL an URL for the file contianing the lexicon.
   */
  public Lexicon(URL lexiconURL) throws IOException{
    this(lexiconURL, null, " ");
  }

  /**
   * Constructor.
   * @param lexiconURL an URL for the file containing the lexicon.
   * @param encoding the character encoding to use for reading the lexicon.
   */

  public Lexicon(URL lexiconURL, String encoding) throws IOException{
    this(lexiconURL,encoding," ");
  }
  
  public Lexicon(URL lexiconURL, String encoding, String separator) throws IOException{
    String line;
    
    // the PR doesn't set a default encoding but to create the reader here
    // we need to pass one in so use the default charset. This is exactly what
    // would happen internally if we used the constructor without an encoding
    // anyway so this just makes things nicely explicit
    try (BufferedReader lexiconReader = new BomStrippingInputStreamReader(lexiconURL.openStream(),
    		encoding != null ? encoding : Charset.defaultCharset().name())) {
      
      line = lexiconReader.readLine();
      String entry;
      List<String> categories;
      while(line != null){
        int index = line.indexOf(separator.charAt(0));
        if(index < 0)
          throw new RuntimeException("Invalid lexicon file (no separator)!: "+line);
        entry = line.substring(0, index);
        
        StringTokenizer tokens = new StringTokenizer(line.substring(index + 1));
        //entry = tokens.nextToken();
        categories = new ArrayList<String>();
        while(tokens.hasMoreTokens()) categories.add(tokens.nextToken());
        put(entry, categories);
  
        line = lexiconReader.readLine();
      }//while(line != null)
    }
  }//public Lexicon(URL lexiconURL) throws IOException

}//class Lexicon

package gate.jape;

import gate.jape.constraint.ConstraintFactory;
import gate.jape.parser.ParseCpsl;
import gate.util.BomStrippingInputStreamReader;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class JapeFactory {
	static Class<? extends ParseCpsl> japeParserClass = ParseCpsl.class;
	  public static Class<? extends ParseCpsl> getJapeParserClass() {
	      return japeParserClass;
	  }

	  public static void setJapeParserClass(Class<? extends ParseCpsl> newClass) {
	      japeParserClass = newClass;
	  }

	  public static ParseCpsl newJapeParser(java.io.Reader stream, Map<String,Object> existingMacros) {
	      try {
	          Constructor<? extends ParseCpsl> c = japeParserClass.getConstructor
	              (new Class<?>[] {Reader.class, Map.class});
	          return c.newInstance(new Object[] {stream, existingMacros});
	      } catch (NoSuchMethodException e) { // Shouldn't happen
	          throw new RuntimeException(e);
	      } catch (IllegalArgumentException e) { // Shouldn't happen
	          throw new RuntimeException(e);
	      } catch (InstantiationException e) { // Shouldn't happen
	          throw new RuntimeException(e);
	      } catch (IllegalAccessException e) { // Shouldn't happen
	          throw new RuntimeException(e);
	      } catch (InvocationTargetException e) { // Happens if the constructor throws an exception
	          throw new RuntimeException(e);
	      }
	  }

	  public static ParseCpsl newJapeParser(URL japeURL, String encoding) throws IOException {
	    // the stripping stream is buffered, no need to buffer the URL stream.
	      java.io.Reader stream = new BomStrippingInputStreamReader(japeURL.openStream(), encoding);

	      ParseCpsl parser = newJapeParser(stream, new HashMap<String,Object>());
	      parser.setBaseURL(japeURL);
	      parser.setEncoding(encoding);
	      return parser;
	  }

	  /**
	   * Active ConstraintFactory for creating and initializing Jape <b>Constraint</b>s.
	   */
	  private static ConstraintFactory japeConstraintFactory = new ConstraintFactory();

	  /**
	   * Return the active {@link ConstraintFactory} for creating and initializing Jape
	   * <b>Constraint</b>s.
	   */
	  public static ConstraintFactory getConstraintFactory() {
	    return japeConstraintFactory;
	  }
}

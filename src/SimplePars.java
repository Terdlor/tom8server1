
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.Serializer;

/**
 * Created by Terdlor on 23.04.2017.
 */
public class SimplePars {


    static Object getObject (String xml, Class c){
        Reader reader = new StringReader(xml);
        Persister serializer = new Persister();
        Object new_ob = null;
        try
        {
            new_ob = serializer.read(c, reader, false);
        }
        catch (Exception e)
        {
        }
        return new_ob;
    }

    static String getXml(Object p_object){
        Writer writer = new StringWriter();
        Serializer serializer = new Persister();
        String xml="";
        try
        {
            serializer.write(p_object, writer);
            xml = writer.toString();
        }
        catch (Exception e)
        {
        }
        return  xml;
    }
}

package www.ezrpro.com.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author nannan
 * @date 2018/8/18
 */
public class ConfigHelp {
    static String profile = "application.conf";

    private static Properties props = new Properties();
    static {
        try {
            InputStream is=ConfigHelp.class.getClassLoader().getResourceAsStream(profile);
            props.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    public static String getValue(String key) {
        return props.getProperty(key);
    }

}

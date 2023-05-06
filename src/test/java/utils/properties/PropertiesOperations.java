package utils.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesOperations{
    public static String getProperty(String propertyName,String fileFullPath){
        try (InputStream input = new FileInputStream(fileFullPath)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            String webSiteUrl=prop.getProperty(propertyName);
            System.out.println(prop.getProperty("propertyName="+propertyName));
            return webSiteUrl;
        } catch (IOException ex) {
            ex.printStackTrace();
            return "error";
        }
    }
}
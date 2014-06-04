package net.anotheria.tmt.config;

import com.sun.jna.platform.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Properties;

/**
 * @author VKoulakov
 * @since 03.06.14 18:54
 */
public class ConfigurationManager {

    private static Configuration configuration;

    public static Configuration getConfiguration() throws ConfigurationException {
        if (configuration == null){
            configuration = new Configuration();
            try {
                readConfig(configuration);
            } catch (IOException e) {
                throw new ConfigurationException(e.getMessage());
            }
        }
        return configuration;
    }

    private static void readConfig(Configuration configuration) throws IOException {
        String configFile = System.getProperty("config");
        if (configFile == null){
            configFile = System.getProperty("user.dir") + File.separatorChar + "tmt.properties";
        }
        File file = new File(configFile);
        if (!file.exists() && !file.isFile()){
            throw new FileNotFoundException();
        }
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));
        for(String property : properties.stringPropertyNames()){
            try {
                Field field = configuration.getClass().getField(property);
                String stringValue = properties.getProperty(property);
                Type type = field.getType();
                Object value = null;
                if (type.equals(Integer.TYPE)){
                    value = Integer.valueOf(stringValue);
                } else if (type.equals(String.class)){
                    value = stringValue;
                }
                field.set(configuration, value);
            } catch (NoSuchFieldException ignored) {
                System.out.println(ignored);
            } catch (IllegalAccessException ignored) {
                System.out.println(ignored);
            }
        }
    }
}

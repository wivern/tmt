package net.anotheria.tmt.config;

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

    private static ConfigurationImpl configuration;

    public static Configuration getConfiguration() throws ConfigurationException {
        if (configuration == null){
            configuration = new ConfigurationImpl();
            try {
                readConfig(configuration);
            } catch (IOException e) {
                throw new ConfigurationException(e.getMessage());
            }
        }
        return configuration;
    }

    private static void readConfig(final ConfigurationImpl configuration) throws IOException {
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
        setProperty("refreshOnSuccess", properties, Integer.class, new PropertySetter<Integer>() {
            @Override
            public void setProperty(Integer value) {
                configuration.setRefreshOnSuccess(value);
            }
        });
        setProperty("refreshOnFailure", properties, Integer.class, new PropertySetter<Integer>() {
            @Override
            public void setProperty(Integer value) {
                configuration.setRefreshOnFailure(value);
            }
        });
        setProperty("refreshConfig", properties, Integer.class, new PropertySetter<Integer>() {
            @Override
            public void setProperty(Integer value) {
                configuration.setRefreshConfig(value);
            }
        });
        setProperty("debugMessage", properties, String.class, new PropertySetter<String>() {
            @Override
            public void setProperty(String value) {
                configuration.setDebugMessage(value);
            }
        });
        setProperty("connectionTimeout", properties, Integer.class, new PropertySetter<Integer>() {
            @Override
            public void setProperty(Integer value) {
                configuration.setConnectionTimeout(value);
            }
        });
        setProperty("targetIp", properties, String.class, new PropertySetter<String>() {
            @Override
            public void setProperty(String value) {
                configuration.setTargetIp(value);
            }
        });
        setProperty("sourceIp", properties, String.class, new PropertySetter<String>() {
            @Override
            public void setProperty(String value) {
                configuration.setSourceIp(value);
            }
        });
    }

    private static  <T> void setProperty(String propertyName, Properties properties, Class<T> type, PropertySetter<T> setter){
        if (properties.containsKey(propertyName) && setter != null){
            String value = properties.getProperty(propertyName);
            if (String.class.isAssignableFrom(type)){
                setter.setProperty(type.cast(value));
            } else if (Integer.class.isAssignableFrom(type)){
                setter.setProperty(type.cast(Integer.parseInt(value)));
            }
        }
    }

    interface PropertySetter<T>{
        void setProperty(T value);
    }
}

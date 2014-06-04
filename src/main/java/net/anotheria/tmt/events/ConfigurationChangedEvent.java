package net.anotheria.tmt.events;

import net.anotheria.tmt.config.Configuration;

import java.util.EventObject;

/**
 * @author VKoulakov
 * @since 04.06.14 14:17
 */
public class ConfigurationChangedEvent extends EventObject {

    private Configuration configuration;

    public ConfigurationChangedEvent(Object source, Configuration configuration) {
        super(source);
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}

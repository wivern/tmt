package net.anotheria.tmt.events;

import java.util.EventListener;

/**
 * @author VKoulakov
 * @since 04.06.14 14:18
 */
public interface ConfigurationChangedEventListener extends EventListener {
    void configurationChanged(ConfigurationChangedEvent event);
}

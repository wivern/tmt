package net.anotheria.tmt.events;

import java.util.EventObject;
import java.util.Locale;

/**
 * User: AAbushkevich
 * Date: 04.06.14
 * Time: 15:44
 */
public class LocaleChangedEvent extends EventObject {

    private Locale locale;

    public LocaleChangedEvent(Object source, Locale locale) {
        super(source);
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}

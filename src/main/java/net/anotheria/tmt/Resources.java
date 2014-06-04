package net.anotheria.tmt;

import java.util.*;

/**
 * User: AAbushkevich
 * Date: 04.06.14
 * Time: 14:23
 */
public class Resources {
    private static ResourceBundle bundle;
    private static List<Locale> availableLocalizations = new ArrayList<Locale>() {{
        add(new Locale("EN"));
        add(new Locale("RU"));
    }};

    public static void init() {
        setLocalization(Locale.getDefault());
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static void setLocalization(Locale locale) {
        try {
            bundle = ResourceBundle.getBundle("i18n.Resources", locale);
        } catch(MissingResourceException e) {
            bundle = ResourceBundle.getBundle("i18n.Resources", availableLocalizations.get(0));
        }
    }

    public static List<Locale> getAvailableLocalizations() {
        return availableLocalizations;
    }
}

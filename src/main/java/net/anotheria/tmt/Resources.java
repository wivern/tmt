package net.anotheria.tmt;

import java.util.*;

/**
 * User: AAbushkevich
 * Date: 04.06.14
 * Time: 14:23
 */
public class Resources {
    private static ResourceBundle defaultBundle, bundle;
    private static List<Locale> availableLocalizations = new ArrayList<Locale>() {{
        add(new Locale("EN"));
        add(new Locale("RU"));
    }};

    public static void init() {
        defaultBundle = ResourceBundle.getBundle("i18n.Resources", availableLocalizations.get(0));
        setLocalization(Locale.getDefault());
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch(MissingResourceException e) {
            return defaultBundle.getString(key);
        }
    }

    public static void setLocalization(Locale locale) {
        try {
            bundle = ResourceBundle.getBundle("i18n.Resources", locale);
        } catch(MissingResourceException e) {
            bundle = defaultBundle;
        }
    }

    public static List<Locale> getAvailableLocalizations() {
        return availableLocalizations;
    }
}

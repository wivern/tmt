package net.anotheria.tmt;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * User: AAbushkevich
 * Date: 04.06.14
 * Time: 14:23
 */
public class Resources {
    private static ResourceBundle bundle;

    public static void init() {
        try {
            bundle = ResourceBundle.getBundle("i18n.Resources", Locale.getDefault());
        } catch(MissingResourceException e) {
            System.out.print("WARN! No localization resources for system default locale (" + Locale.getDefault() + ")");
            bundle = ResourceBundle.getBundle("i18n.Resources", Locale.ENGLISH);
        }
    }

    public static String get(String key) {
        return bundle.getString(key);
    }
}

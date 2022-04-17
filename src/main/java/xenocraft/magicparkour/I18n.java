package xenocraft.magicparkour;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18n {

    private static final String MESSAGES_KEY = "messages";
    private static ResourceBundle bundle;

    static {
        Locale.setDefault(Locale.ENGLISH);
    }

    private I18n() {}

    public static Locale getLocale() {
        return Locale.getDefault();
    }

    public static boolean isSupported(Locale l) {
        List<Locale> available = Arrays.asList(Locale.getAvailableLocales());
        if (!available.contains(l)) return false;

        Locale def = Locale.getDefault();
        Locale.setDefault(l);

        try {
            ResourceBundle.getBundle(MESSAGES_KEY);
        } catch (MissingResourceException exception) {
            Locale.setDefault(def);
            return false;
        }
        Locale.setDefault(def);

        return true;
    }

    public static void setLocale(Locale l) {
        Locale.setDefault(l);
        bundle = ResourceBundle.getBundle(MESSAGES_KEY);
    }

    public static String getMessage(String key) {
        if (bundle == null) bundle = ResourceBundle.getBundle(MESSAGES_KEY);
        return bundle.getString(key);
    }

}

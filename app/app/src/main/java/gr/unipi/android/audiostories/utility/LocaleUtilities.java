package gr.unipi.android.audiostories.utility;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

public class LocaleUtilities {
    public static void changeLocale(String locale) {
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(locale);
        AppCompatDelegate.setApplicationLocales(appLocale);
    }
}

package gr.unipi.android.audiostories;

import static gr.unipi.android.audiostories.constant.AppConstants.LANGUAGE_EXTRAS_KEY;
import static gr.unipi.android.audiostories.constant.AppConstants.LANGUAGE_PREFS_KEY;
import static gr.unipi.android.audiostories.constant.AppConstants.TITLE_EXTRAS_KEY;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import gr.unipi.android.audiostories.utility.LocaleUtilities;

public class MainActivity extends AppCompatActivity {
    RadioGroup languageGroup;
    SharedPreferences prefs;
    String language;
    public myTts myTts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        languageGroup = findViewById(R.id.languageGroup);
        setInitialLanguage();
        addLocaleListeners(languageGroup);

        // Initialize text to speech
        myTts = new myTts(this);
    }

    private void setInitialLanguage() {
        prefs = getPreferences(MODE_PRIVATE);
        language = prefs.getString(LANGUAGE_PREFS_KEY, getResources().getConfiguration().getLocales().get(0).getLanguage());
        switch (language) {
            case "el":
                languageGroup.check(R.id.gr);
                break;
            case "en":
                languageGroup.check(R.id.en);
                break;
            case "de":
                languageGroup.check(R.id.de);
                break;
        }
    }

    public void goToStory(View view) {
        Intent intent = new Intent(this, StoryActivity.class);
        // Usage of 'if' instead of 'switch' as resources are no longer declared final.
        if (view.getId() == R.id.red_riding_hood) {
            intent.putExtra(TITLE_EXTRAS_KEY, "red_riding_hood");
        } else if (view.getId() == R.id.snow_white) {
            intent.putExtra(TITLE_EXTRAS_KEY, "snow_white");
        } else if (view.getId() == R.id.sleeping_beauty) {
            intent.putExtra(TITLE_EXTRAS_KEY, "sleeping_beauty");
        } else if (view.getId() == R.id.jack_and_the_beanstalk) {
            intent.putExtra(TITLE_EXTRAS_KEY, "jack_and_the_beanstalk");
        } else if (view.getId() == R.id.ugly_duckling) {
            intent.putExtra(TITLE_EXTRAS_KEY, "ugly_duckling");
        }
        intent.putExtra(LANGUAGE_EXTRAS_KEY, language);
        startActivity(intent);
    }

    private void addLocaleListeners(RadioGroup languageGroup) {
        SharedPreferences.Editor editor = prefs.edit();
        languageGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == R.id.gr) {
                LocaleUtilities.changeLocale("el");
                editor.putString(LANGUAGE_PREFS_KEY, "el");
            }
            else if (checkedId == R.id.en) {
                LocaleUtilities.changeLocale("en");
                editor.putString(LANGUAGE_PREFS_KEY, "en");
            }
            else if (checkedId == R.id.de) {
                LocaleUtilities.changeLocale("de");
                editor.putString(LANGUAGE_PREFS_KEY, "de");
            }
            editor.apply();
            onConfigurationChanged(getResources().getConfiguration());
        });
    }
}
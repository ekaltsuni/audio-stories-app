package gr.unipi.android.audiostories;

import static gr.unipi.android.audiostories.constant.AppConstants.LANGUAGE_EXTRAS_KEY;
import static gr.unipi.android.audiostories.constant.AppConstants.LANGUAGE_PREFS_KEY;
import static gr.unipi.android.audiostories.constant.AppConstants.TITLE_EXTRAS_KEY;
import static gr.unipi.android.audiostories.constant.AppConstants.sDatabase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import gr.unipi.android.audiostories.utility.LocaleUtilities;

public class MainActivity extends AppCompatActivity {
    RadioGroup languageGroup;
    ImageView imageView;
    SharedPreferences prefs;
    String language;
    public MyTts myTts;

    String createTableSQL = "CREATE TABLE if not exists StoryStats (\n" +
            "    titleResourceId STRING PRIMARY KEY    NOT NULL,\n" +
            "    favorite BOOLEAN,\n" +
            "    audioCount INTEGER \n" +
            ");";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView2);

        languageGroup = findViewById(R.id.languageGroup);
        setInitialLanguage();
        addLocaleListeners(languageGroup);

        // Initialize text to speech
        myTts = new MyTts(this);

        sDatabase = openOrCreateDatabase("StoryStats.db",MODE_PRIVATE,null);
        sDatabase.execSQL(createTableSQL);

        // Insert initial values if the table was just created
        insertInitialValues();

        // In landscape mode hide extra information
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageView.setVisibility(View.GONE);
        }
    }

    private void insertInitialValues() {
        Object[][] entries = {
                {R.string.jack_and_the_beanstalk, false, 0},
                {R.string.red_riding_hood, false, 0},
                {R.string.sleeping_beauty, false, 0},
                {R.string.snow_white, false, 0},
                {R.string.ugly_duckling, false, 0}
        };

        for (Object[] entry : entries) {
            String insertSQL = "INSERT OR IGNORE INTO StoryStats (titleResourceId, favorite, audioCount) VALUES (?, ?, ?)";
            sDatabase.execSQL(insertSQL, entry);
        }
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
            // update sqlite +1
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

    public void goStats(View view) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }
}
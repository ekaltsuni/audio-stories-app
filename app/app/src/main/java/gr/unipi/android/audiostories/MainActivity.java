package gr.unipi.android.audiostories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

import gr.unipi.android.audiostories.utility.LocaleUtilities;

public class MainActivity extends AppCompatActivity {

    RadioGroup languageGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        languageGroup = findViewById(R.id.languageGroup);
        // TODO: keep last chosen language in shared prefs
        String language = getResources().getConfiguration().getLocales().get(0).getLanguage();
        if (language.equals("el")) {
            languageGroup.check(R.id.gr);
        } else if (language.equals("en")) {
            languageGroup.check(R.id.en);
        } else if (language.equals("de")) {
            languageGroup.check(R.id.de);
        }
        addLocaleListeners(languageGroup);
    }

    public void goToStory(View view) {
        Intent intent = new Intent(this, StoryActivity.class);
        // Usage of 'if' instead of 'switch' as resources are no longer declared final.
        if (view.getId() == R.id.button) {
            intent.putExtra("title", "red_riding_hood");
        }
        startActivity(intent);
    }

    private void addLocaleListeners(RadioGroup languageGroup) {
        languageGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            if (checkedId == R.id.gr) LocaleUtilities.changeLocale("el");
            else if (checkedId == R.id.en) LocaleUtilities.changeLocale("en");
            else if (checkedId == R.id.de) LocaleUtilities.changeLocale("de");
            finish();
            startActivity(getIntent());
        });
    }
}
package gr.unipi.android.audiostories;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

import static java.lang.String.format;

import static gr.unipi.android.audiostories.constant.AppConstants.FIREBASE_NEWLINE_SEPARATOR;
import static gr.unipi.android.audiostories.constant.AppConstants.FIREBASE_STORY_INFO_PATH;
import static gr.unipi.android.audiostories.constant.AppConstants.FIREBASE_STORY_TEXT_PATH;
import static gr.unipi.android.audiostories.constant.AppConstants.LANGUAGE_EXTRAS_KEY;
import static gr.unipi.android.audiostories.constant.AppConstants.TITLE_EXTRAS_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import gr.unipi.android.audiostories.constant.AppConstants;
import gr.unipi.android.audiostories.constant.ContextConstants;
import gr.unipi.android.audiostories.model.Story;

public class StoryActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    ContextConstants ctxConstants;

    Story currentStory;
    TextView storyTitle;
    TableLayout infoTable;
    TextView storyText;
    ImageView imageView;
    private MyTts ttsInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        // For translatable constants
        ctxConstants = new ContextConstants(this);

        String title = getIntent().getStringExtra(TITLE_EXTRAS_KEY);
        String language = getIntent().getStringExtra(LANGUAGE_EXTRAS_KEY);
        // Get the story object from the HashMap of stories.
        currentStory = AppConstants.storyMap.get(title);

        // Get the needed components.
        storyTitle = findViewById(R.id.storyTitle);
        storyTitle.setText(currentStory.getTitleResourceId());
        infoTable = findViewById(R.id.infoTable);
        storyText = findViewById(R.id.storyText);
        storyText.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        imageView = findViewById(R.id.imageView);

        // Initialize Text to Speech
        ttsInstance = new MyTts(this);

        // Set the components' contents.
        imageView.setImageResource(currentStory.getImageId());
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(format(FIREBASE_STORY_TEXT_PATH, currentStory.getKey()));
        addStoryDatabaseListener();
        reference = database.getReference(format(FIREBASE_STORY_INFO_PATH, currentStory.getKey()));
        setStoryInfoDatabaseListener(language);
    }

    private void addStoryDatabaseListener() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    storyText.setText(value.toString().replace(FIREBASE_NEWLINE_SEPARATOR, "\n\n"));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase error:", error.getMessage());
            }
        });
    }

    private void setStoryInfoDatabaseListener(String language) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String date = Objects.requireNonNull(snapshot.child("date").getValue()).toString();
                assert language != null;
                String country = Objects.requireNonNull(snapshot.child(language).child("country").getValue()).toString();
                String author = Objects.requireNonNull(snapshot.child(language).child("author").getValue()).toString();
                for (int i = 0; i < 3; i++) {
                    addInfoRow(i, country, date, author);
                }
                // In landscape mode hide extra information
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    infoTable.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase error:", error.getMessage());
            }
        });
    }

    private void addInfoRow(int i, String country, String date, String author) {
        TableRow row = new TableRow(StoryActivity.this);
        row.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        TextView rowText = new TextView(StoryActivity.this);
        switch (i) {
            case 0:
                rowText.setText(Html.fromHtml(format(ctxConstants.INFO_COUNTRY, country)));
                break;
            case 1:
                rowText.setText(Html.fromHtml(format(ctxConstants.INFO_DATE, date)));
                break;
            case 2:
                rowText.setText(Html.fromHtml(format(ctxConstants.INFO_AUTHOR, author)));
                break;
        }
        rowText.setPadding(50, 25, 25, 10);
        row.addView(rowText);
        infoTable.addView(row, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }

    public void speak(View view) {
        String storyContent = storyTitle.getText().toString();
        ttsInstance.speak(storyContent);
        storyContent = storyText.getText().toString();
        ttsInstance.speak(storyContent);
    }

    protected void onPause() {
        super.onPause();
        // Stop the text-to-speech when the activity is paused
        if (ttsInstance != null) {
            ttsInstance.stopSpeaking();
        }
    }
}
package gr.unipi.android.audiostories;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

import static java.lang.String.format;

import static gr.unipi.android.audiostories.constant.AppConstants.FIREBASE_NEWLINE_SEPARATOR;
import static gr.unipi.android.audiostories.constant.AppConstants.FIREBASE_STORY_INFO_PATH;
import static gr.unipi.android.audiostories.constant.AppConstants.FIREBASE_STORY_TEXT_PATH;
import static gr.unipi.android.audiostories.constant.AppConstants.INFO_AUTHOR;
import static gr.unipi.android.audiostories.constant.AppConstants.INFO_COUNTRY;
import static gr.unipi.android.audiostories.constant.AppConstants.INFO_DATE;
import static gr.unipi.android.audiostories.constant.AppConstants.TITLE_EXTRAS_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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

import gr.unipi.android.audiostories.constant.AppConstants;
import gr.unipi.android.audiostories.model.Story;

public class StoryActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;

    Story currentStory;
    TextView storyTitle;
    TableLayout infoTable;
    TextView storyText;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        // Get the story object from the HashMap of stories.
        String title = getIntent().getStringExtra(TITLE_EXTRAS_KEY);
        currentStory = AppConstants.storyMap.get(title);
        // Get the needed components.
        storyTitle = findViewById(R.id.storyTitle);
        storyTitle.setText(currentStory.getTitleResourceId());
        infoTable = findViewById(R.id.infoTable);
        storyText = findViewById(R.id.storyText);
        storyText.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        imageView = findViewById(R.id.imageView);
        // Set the components' contents.
        imageView.setImageResource(currentStory.getImageId());
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(format(FIREBASE_STORY_TEXT_PATH, currentStory.getKey()));
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
        reference = database.getReference(format(FIREBASE_STORY_INFO_PATH, currentStory.getKey()));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String country = snapshot.child("country").getValue().toString();
                String date = snapshot.child("date").getValue().toString();
                String author = snapshot.child("author").getValue().toString();

                for (int i = 0; i < 3; i++) {
                    TableRow row = new TableRow(StoryActivity.this);
                    row.setLayoutParams(new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    TextView label_android = new TextView(StoryActivity.this);
                    switch (i) {
                        case 0:
                            label_android.setText(Html.fromHtml(format(INFO_COUNTRY, country)));
                            break;
                        case 1:
                            label_android.setText(Html.fromHtml(format(INFO_DATE, date)));
                            break;
                        case 2:
                            label_android.setText(Html.fromHtml(format(INFO_AUTHOR, author)));
                            break;
                    }
                    label_android.setPadding(50, 25, 25, 10);
                    row.addView(label_android);

                    infoTable.addView(row, new TableLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase error:", error.getMessage());
            }
        });
    }
}
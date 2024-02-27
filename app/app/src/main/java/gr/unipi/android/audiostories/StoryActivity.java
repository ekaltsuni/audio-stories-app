package gr.unipi.android.audiostories;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

import static java.lang.String.format;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
    TextView storyText;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        // Get the story object from the HashMap of stories.
        String title = getIntent().getStringExtra("title");
        currentStory = AppConstants.storyMap.get(title);
        // Get the needed components.
        storyText = findViewById(R.id.storyText);
        storyText.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        imageView = findViewById(R.id.imageView);
        // Set the components' contents.
        imageView.setImageResource(currentStory.getImageId());
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(format("stories/%s/text", currentStory.getTitle()));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null) {
                    storyText.setText(value.toString().replace("_b ", "\n\n"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase error:", error.getMessage());
            }
        });
    }
}
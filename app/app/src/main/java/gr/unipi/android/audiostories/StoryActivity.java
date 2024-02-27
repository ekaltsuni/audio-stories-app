package gr.unipi.android.audiostories;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

import static java.lang.String.format;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.text.LineBreaker;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import gr.unipi.android.audiostories.model.Story;

public class StoryActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;

    Story currentStory;
    TextView storyText;
    ImageView imageView;
    Map<String, Story> resources = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        resources.put("Η Κοκκινοσκουφίτσα", new Story("red_riding_hood", R.drawable.red_riding_hood));
        currentStory = resources.get(getIntent().getStringExtra("story"));

        storyText = findViewById(R.id.storyText);
        storyText.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        imageView = findViewById(R.id.imageView);

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

            }
        });
    }
}
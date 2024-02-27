package gr.unipi.android.audiostories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToStory(View view) {
        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra("story", ((Button)view).getText());
        startActivity(intent);
    }
}
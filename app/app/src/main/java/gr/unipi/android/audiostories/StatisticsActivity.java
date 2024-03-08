package gr.unipi.android.audiostories;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        /*Cursor cursor = sDatabase.rawQuery("SELECT audioCount FROM StoryStats WHERE titleResourceId = ?",
                new String[]{String.valueOf(currentStory.getTitleResourceId())});
        int audioCount = 0;

        if (cursor.moveToFirst()) {
            audioCount = cursor.getInt(cursor.getColumnIndex("audioCount"));
        }

        cursor.close();



*/
        addRowsToTable(2);
    }
    private void addRowsToTable(int numRows) {
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // Add the "Favorite Stories" row
        TableRow favoriteRow = new TableRow(this);
        favoriteRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        TextView favoriteText = new TextView(this);
        favoriteText.setText(R.string.favorite_stories);
        favoriteRow.addView(favoriteText);
        tableLayout.addView(favoriteRow, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        // Add the "Audio Plays" row
        TableRow audioPlaysRow = new TableRow(this);
        audioPlaysRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        TextView audioPlaysText = new TextView(this);
        audioPlaysText.setText(R.string.audio_plays);
        audioPlaysRow.addView(audioPlaysText);
        tableLayout.addView(audioPlaysRow, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

    }

}
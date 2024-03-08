package gr.unipi.android.audiostories;

import static gr.unipi.android.audiostories.constant.AppConstants.sDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

//        Cursor cursor = sDatabase.rawQuery("SELECT audioCount FROM StoryStats WHERE titleResourceId = ?",
//                new String[]{String.valueOf(R.string.red_riding_hood)});
//        int audioCount = 0;
//
//        if (cursor.moveToFirst()) {
//            audioCount = cursor.getInt(cursor.getColumnIndex("audioCount"));
//        }
//        cursor.close();
        addRowsToTable();
    }

    private void addRowsToTable() {
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        // Add the "Favorite Stories" row
        TableRow favoriteRow = new TableRow(this);
        favoriteRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        TextView favoriteText = new TextView(this);
        favoriteText.setText(getResources().getString(R.string.favorite_stories));
        favoriteRow.addView(favoriteText);
        tableLayout.addView(favoriteRow, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        Cursor cur = sDatabase.rawQuery("SELECT titleResourceId FROM StoryStats WHERE favorite = 1", null);
        while (cur.moveToNext()) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            TextView text = new TextView(this);
            text.setText(getResources().getString(Integer.parseInt(cur.getString(0))));
            row.addView(text);
            tableLayout.addView(row, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
        }

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
        cur = sDatabase.rawQuery("SELECT titleResourceId, audioCount FROM StoryStats", null);
        while (cur.moveToNext()) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            TextView text = new TextView(this);
            StringBuilder sb = new StringBuilder();
            sb
                    .append(getResources().getString(Integer.parseInt(cur.getString(0))))
                    .append(" -> ")
                    .append(cur.getInt(1));
            text.setText(sb.toString());
            row.addView(text);
            tableLayout.addView(row, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
        }
        cur.close();

    }

}
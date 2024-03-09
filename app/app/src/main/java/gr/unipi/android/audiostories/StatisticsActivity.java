package gr.unipi.android.audiostories;

import static gr.unipi.android.audiostories.constant.AppConstants.sDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import gr.unipi.android.audiostories.constant.ContextConstants;

public class StatisticsActivity extends AppCompatActivity {
    ContextConstants ctxConstants;
    int totalCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        // For translatable constants
        ctxConstants = new ContextConstants(this);

        Cursor cur = sDatabase.rawQuery("SELECT SUM(audioCount) FROM StoryStats", null);
        if (cur.moveToFirst()) totalCount = cur.getInt(0);
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
        favoriteText.setPadding(0, 0, 25, 0);
        favoriteText.setText(Html.fromHtml(ctxConstants.STATISTICS_FAVORITE));
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
        audioPlaysText.setText(Html.fromHtml(ctxConstants.STATISTICS_REPLAYS));
        audioPlaysText.setPadding(0, 25, 0, 0);
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
                    .append(" : ")
                    .append(Math.round(cur.getInt(1) / (double)totalCount * 100))
                    .append("%");
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
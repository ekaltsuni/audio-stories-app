package gr.unipi.android.audiostories;

import static gr.unipi.android.audiostories.constant.AppConstants.SQL_SELECT_ALL_FAVORITE;
import static gr.unipi.android.audiostories.constant.AppConstants.SQL_SELECT_ALL_TITLE_AND_COUNT;
import static gr.unipi.android.audiostories.constant.AppConstants.SQL_SUM_OF_COUNTS;
import static gr.unipi.android.audiostories.constant.AppConstants.sDatabase;

import androidx.annotation.NonNull;
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
    TableLayout tableLayout;
    int totalCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        tableLayout = findViewById(R.id.tableLayout);
        // For translatable constants
        ctxConstants = new ContextConstants(this);
        // Needed for percentages
        Cursor cur = sDatabase.rawQuery(SQL_SUM_OF_COUNTS, null);
        if (cur.moveToFirst()) totalCount = cur.getInt(0);
        cur.close();
        // Populate tables
        addRowsToTable();
    }

    private void addRowsToTable() {
        // Add the "Favorite Stories" rows
        addFavorites();
        // Add the "Audio Plays" rows
        addPercentages();

    }

    private void addPercentages() {
        TableRow audioPlaysRow = createRow();
        addTitle(ctxConstants.STATISTICS_REPLAYS, audioPlaysRow);
        Cursor cur = sDatabase.rawQuery(SQL_SELECT_ALL_TITLE_AND_COUNT, null);
        while (cur.moveToNext()) {
            TableRow row = createRow();
            TextView text = new TextView(this);
            String sb =
                    getResources().getString(Integer.parseInt(cur.getString(0))) +
                    " : " +
                    getPercentage(cur) +
                    "%";
            text.setText(sb);
            row.addView(text);
            tableLayout.addView(row, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
        }
        cur.close();
    }

    private void addFavorites() {
        TableRow favoriteRow = createRow();
        addTitle(ctxConstants.STATISTICS_FAVORITE, favoriteRow);
        Cursor cur = sDatabase.rawQuery(SQL_SELECT_ALL_FAVORITE, null);
        while (cur.moveToNext()) {
            TableRow row = createRow();
            TextView text = new TextView(this);
            text.setText(getResources().getString(Integer.parseInt(cur.getString(0))));
            row.addView(text);
            tableLayout.addView(row, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
        }
        cur.close();
    }

    @NonNull
    private TableRow createRow() {
        TableRow favoriteRow = new TableRow(this);
        favoriteRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        return favoriteRow;
    }

    private void addTitle(String msg, TableRow row) {
        TextView titleText = new TextView(this);
        titleText.setText(Html.fromHtml(msg));
        titleText.setPadding(0, 25, 0, 0);
        row.addView(titleText);
        tableLayout.addView(row, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }

    private long getPercentage(Cursor cur) {
        if (totalCount > 0) return Math.round(cur.getInt(1) / (double) totalCount * 100);
        return 0;
    }

}
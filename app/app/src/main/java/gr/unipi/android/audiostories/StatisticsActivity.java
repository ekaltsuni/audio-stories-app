package gr.unipi.android.audiostories;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class StatisticsActivity extends AppCompatActivity {

    SQLiteDatabase database;
    int favoriteCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        String selectSQL = "SELECT * FROM Joke ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = database.rawQuery(selectSQL,null);
        if (cursor.moveToNext()){
            setup = cursor.getString(0);
            punchline = cursor.getString(1);
            jokeID = cursor.getInt(2);
        }
        cursor.close();
    }
}
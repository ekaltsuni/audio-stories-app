package gr.unipi.android.audiostories.constant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

import gr.unipi.android.audiostories.R;
import gr.unipi.android.audiostories.model.Story;

public class AppConstants {
    public static SQLiteDatabase sDatabase;
    public static final String SQL_CREATE_DB =
            "CREATE TABLE IF NOT EXISTS StoryStats ( \n" +
            "    titleResourceId STRING PRIMARY KEY    NOT NULL, \n" +
            "    favorite BOOLEAN, \n" +
            "    audioCount INTEGER  \n" +
            ")";
    public static final String SQL_INSERT_INITIAL_VALUES =
            "INSERT OR IGNORE INTO StoryStats \n" +
            "   (titleResourceId, favorite, audioCount) VALUES (?, ?, ?)";
    public static final String SQL_CHECK_IF_FAVORITE =
            "SELECT EXISTS ( \n" +
            "   SELECT * FROM StoryStats \n" +
            "   WHERE \n" +
            "       titleResourceId = ? AND \n" +
            "       favorite = 1)";
    public static final String SQL_UPDATE_COUNT =
            "UPDATE StoryStats \n" +
            "   SET audioCount = ? \n" +
            "   WHERE \n" +
            "       titleResourceId = ?";
    public static final String SQL_UPDATE_FAVORITE =
            "UPDATE StoryStats \n" +
            "   SET favorite = ? \n" +
            "   WHERE \n" +
            "       titleResourceId = ?";
    public static final String SQL_SELECT_COUNT =
            "SELECT audioCount FROM StoryStats \n" +
            "   WHERE \n" +
            "       titleResourceId = ?";
    public static final String SQL_SELECT_ALL_TITLE_AND_COUNT =
            "SELECT titleResourceId, audioCount FROM StoryStats";
    public static final String SQL_SELECT_ALL_FAVORITE =
            "SELECT titleResourceId FROM StoryStats \n" +
            "   WHERE \n" +
            "       favorite = 1";
    public static final String SQL_SUM_OF_COUNTS =
            "SELECT SUM(audioCount) FROM StoryStats";
    public static final String LANGUAGE_PREFS_KEY = "language";
    public static final String TITLE_EXTRAS_KEY = "title";
    public static final String LANGUAGE_EXTRAS_KEY = "language";
    public static final String FIREBASE_NEWLINE_SEPARATOR = "_b ";
    public static final String FIREBASE_STORY_TEXT_PATH = "stories/%s/text";
    public static final String FIREBASE_STORY_INFO_PATH = "stories/%s/info";
    public static final Map<String,Story> storyMap = new HashMap<String,Story>() {{
        put("red_riding_hood", new Story("red_riding_hood", R.string.red_riding_hood, R.drawable.red_riding_hood));
        put("snow_white", new Story("snow_white", R.string.snow_white, R.drawable.snow_white));
        put("sleeping_beauty" , new Story("sleeping_beauty", R.string.sleeping_beauty, R.drawable.sleeping_beauty));
        put("jack_and_the_beanstalk" , new Story("jack_and_the_beanstalk", R.string.jack_and_the_beanstalk, R.drawable.jack_and_the_beanstalk));
        put("ugly_duckling" , new Story("ugly_duckling", R.string.ugly_duckling, R.drawable.ugly_duckling));
    }};
}

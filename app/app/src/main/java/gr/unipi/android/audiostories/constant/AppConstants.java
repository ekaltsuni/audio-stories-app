package gr.unipi.android.audiostories.constant;

import java.util.HashMap;
import java.util.Map;

import gr.unipi.android.audiostories.R;
import gr.unipi.android.audiostories.model.Story;

public class AppConstants {

    public static final String LANGUAGE_PREFS_KEY = "language";
    public static final String TITLE_EXTRAS_KEY = "title";
    public static final String FIREBASE_NEWLINE_SEPARATOR = "_b ";
    public static final String FIREBASE_STORY_TEXT_PATH = "stories/%s/text";
    public static final Map<String,Story> storyMap = new HashMap<String,Story>() {{
        put("red_riding_hood", new Story("red_riding_hood", R.drawable.red_riding_hood));
    }};
}

package gr.unipi.android.audiostories.constant;

import java.util.HashMap;
import java.util.Map;

import gr.unipi.android.audiostories.R;
import gr.unipi.android.audiostories.model.Story;

public class AppConstants {

    public static final Map<String,Story> storyMap = new HashMap<String,Story>() {{
        put("red_riding_hood", new Story("red_riding_hood", R.drawable.red_riding_hood));
    }};
}

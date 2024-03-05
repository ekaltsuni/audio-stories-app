package gr.unipi.android.audiostories.constant;

import android.content.Context;

import gr.unipi.android.audiostories.R;

public class ContextConstants {
    private final Context ctx;
    public String INFO_COUNTRY;
    public String INFO_DATE;
    public String INFO_AUTHOR;
    public ContextConstants(Context ctx) {
        this.ctx = ctx;
        setValues();
    }

    private void setValues() {
        INFO_COUNTRY = "<b>" + ctx.getString(R.string.country) +"</b><br>%s";
        INFO_DATE = "<b>" + ctx.getString(R.string.date) + "</b><br>%s";
        INFO_AUTHOR = "<b>" + ctx.getString(R.string.author) + "</b><br>%s";
    }
}

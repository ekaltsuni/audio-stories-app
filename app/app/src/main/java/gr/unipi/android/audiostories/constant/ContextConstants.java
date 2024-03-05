package gr.unipi.android.audiostories.constant;

import android.content.Context;

import gr.unipi.android.audiostories.R;

public class ContextConstants {
    private Context ctx;
    public final String INFO_COUNTRY;
    public final String INFO_DATE;
    public final String INFO_AUTHOR;
    public ContextConstants(Context ctx) {
        this.ctx = ctx;
        INFO_COUNTRY = "<b>" + ctx.getString(R.string.country) +"</b><br>%s";
        INFO_DATE = "<b>" + ctx.getString(R.string.date) + "</b><br>%s";
        INFO_AUTHOR = "<b>" + ctx.getString(R.string.author) + "</b><br>%s";
    }
}

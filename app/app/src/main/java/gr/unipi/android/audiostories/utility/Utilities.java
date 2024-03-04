package gr.unipi.android.audiostories.utility;

import android.app.AlertDialog;
import android.content.Context;

public class Utilities {
    public static void showMessage(Context ctx, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setTitle(title).setMessage(message);
        builder.show();
    }
}

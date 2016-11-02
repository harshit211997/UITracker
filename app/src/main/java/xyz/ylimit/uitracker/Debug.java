package xyz.ylimit.uitracker;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by yuanchun on 31/10/2016.
 * For debugging
 */

public class Debug {
    private static boolean toastEnabled = false;

    public static void enableToast() {
        toastEnabled = true;
    }

    public static void disableToast() {
        toastEnabled = false;
    }

    public static void log(String message, Context context) {
        Log.d(Const.ProjectName, message);
        if (toastEnabled && context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}

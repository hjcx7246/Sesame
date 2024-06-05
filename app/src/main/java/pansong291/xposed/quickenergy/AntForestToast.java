package pansong291.xposed.quickenergy;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import pansong291.xposed.quickenergy.hook.XposedHook;
import pansong291.xposed.quickenergy.util.Config;
import pansong291.xposed.quickenergy.util.Log;

public class AntForestToast {
    private static final String TAG = AntForestToast.class.getSimpleName();

    public static void show(CharSequence cs) {
        show(cs, false);
    }

    public static void show(CharSequence cs, boolean force) {
        Context context = XposedHook.getContext();
        if (context != null && (force || Config.INSTANCE.isShowToast())) {
            show(context, XposedHook.getMainHandler(), cs);
        }
    }

    public static void show(Context context, Handler handler, CharSequence cs) {
        try {
            handler.post(() -> {
                try {
                    Toast toast = Toast.makeText(context, cs, Toast.LENGTH_SHORT);
                    toast.setGravity(toast.getGravity(), toast.getXOffset(), Config.INSTANCE.getToastOffsetY());
                    toast.show();
                } catch (Throwable t) {
                    Log.i(TAG, "show.run err:");
                    Log.printStackTrace(TAG, t);
                }
            });
        } catch (Throwable t) {
            Log.i(TAG, "show err:");
            Log.printStackTrace(TAG, t);
        }
    }
}

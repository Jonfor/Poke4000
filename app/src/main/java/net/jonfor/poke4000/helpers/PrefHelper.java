package net.jonfor.poke4000.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by jonfor on 9/28/15.
 */
public class PrefHelper {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PROPERTY_TOKEN = "token";
    private static final String PROPERTY_TOKEN_SENT = "token_sent";

    public static void storeTokenSent(Context context, boolean tokenSent) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean(PROPERTY_TOKEN_SENT, tokenSent);
        editor.apply();
    }

    public static boolean getTokenSent(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

        return prefs.getBoolean(PROPERTY_TOKEN_SENT, false);
    }

    public static void storeToken(Context context, String token) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(PROPERTY_TOKEN, token);
        editor.apply();
    }

    /**
     * Gets the current registration token for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration token, or empty string if there is no existing
     * registration token.
     */
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String registrationId = prefs.getString(PROPERTY_TOKEN, "");
        if (registrationId.isEmpty()) {
            Log.i("Not found", "Registration not found.");
            return "";
        }
        return registrationId;
    }
}

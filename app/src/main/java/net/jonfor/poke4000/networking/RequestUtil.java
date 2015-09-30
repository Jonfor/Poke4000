package net.jonfor.poke4000.networking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by jonfor on 9/29/15.
 */
public class RequestUtil {

    private static final String host = "http://45.55.236.219:8001";
    private static Poke4000Singleton mInstance;

    public static void sendPushToken(String token, final Context context) {
        String url = host + "/registration/" + token;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Token successfully pushed to database!",
                                Toast.LENGTH_LONG).show();
                        Log.i("sendPushToken response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            Log.e("sendPushToken", error.toString());
                            Toast.makeText(context, "An error has occurred while pushing register " +
                                            "token to database",
                                    Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        } else {
                            Log.e("sendPushToken", error.toString());
                            Toast.makeText(context, "An error has occurred while pushing register " +
                                            "token to database",
                                    Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }
                });

        mInstance = Poke4000Singleton.getInstance(context);
        mInstance.addToRequestQueue(request);
    }
}

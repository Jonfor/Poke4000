package net.jonfor.poke4000.networking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static void sendServerInfo(String ip, int port, int frequency, final Context context) {
        String url = host + "/data";


        JSONObject data = new JSONObject();
        try {
            data.put("ip", ip);
            data.put("port", port);
            data.put("frequency", frequency);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            Log.e("sendServerInfo", error.toString());
                            Toast.makeText(context, "An error has occurred while pushing server info " +
                                            "to database",
                                    Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        } else {
                            Log.e("sendServerInfo", error.toString());
                            Toast.makeText(context, "An error has occurred while pushing server info " +
                                            "to database",
                                    Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }
                });

        mInstance = Poke4000Singleton.getInstance(context);
        mInstance.addToRequestQueue(request);
    }
}

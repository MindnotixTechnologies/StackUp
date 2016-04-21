package com.webianks.stackup.Network;


import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.webianks.stackup.MainActivity;
import com.webianks.stackup.Parser.JsonParser;
import org.json.JSONObject;

public class RequestJasonSearchClass {

    private static String response_string=" ";

    public static void requestJasonSearch(String query, final Context context) {

        RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, query, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                JsonParser.parseJson(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                MainActivity.progressBar.setVisibility(View.INVISIBLE);

                try {
                    Toast.makeText(context, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                }catch (NullPointerException e){

                }



            }
        });


        requestQueue.add(request);
    }
  }


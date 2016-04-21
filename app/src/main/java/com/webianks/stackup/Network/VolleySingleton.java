package com.webianks.stackup.Network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.webianks.stackup.MyApplication;

public class VolleySingleton {

    private static VolleySingleton sInstance=null;
    private RequestQueue mRequestQueue;

    private VolleySingleton(){
        mRequestQueue= Volley.newRequestQueue(MyApplication.getAppContext());

    }


    public static VolleySingleton getsInstance(){

        if(sInstance==null){

            sInstance=new VolleySingleton();
        }
        return  sInstance;
    }


    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}

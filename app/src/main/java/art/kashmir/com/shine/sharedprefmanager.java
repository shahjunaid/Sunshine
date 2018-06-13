package art.kashmir.com.shine;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;

/**
 * Created by HP on 22-01-2018.
 */

public class sharedprefmanager {
    private static sharedprefmanager mInstance;
  //  private RequestQueue mRequestQueue;
    private static Context mCtx;
    private static final String weather="shared";
    private static final String key_deg="2";
    private static final String key_desc="abc";
    private static final String key_imgurl="abcukk";
    private static final String key_u="srinagar";
    private static final String sunrise="7:00 am";
    private static final String sunset="8:00 pm";
    private static final String name="srinagar";
    private static final String key_id="one";
    private static final String keyvalue="980";
    private static final String lon="889";
    private static final String getgeo="abc";
    private sharedprefmanager(Context context) {
        mCtx = context;
    }

    public static synchronized sharedprefmanager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new sharedprefmanager(context);
        }
        return mInstance;
    }
    public boolean weather(String deg,String desc,String imgurl,String sunr,String suns,String city){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(weather,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key_deg,deg);
        editor.putString(key_desc,desc);
        editor.putString(sunrise,sunr);
        editor.putString(sunset,suns);
        editor.putString(name,city);
        editor.putString(key_imgurl,imgurl);
        editor.apply();
        return true;
    }

    public boolean key_id(String key){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(key_id,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key_u,key);
        editor.apply();
        return true;
    }
    public String keyvalue(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(key_id,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key_u,null);
    }
    public String imgurls(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(weather,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key_imgurl,null);
    }
    public String cityname(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(weather,Context.MODE_PRIVATE);
        return sharedPreferences.getString(name,null);
    }
    public String des(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(weather,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key_desc,null);
    }
    public String degg(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(weather,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key_deg,null);
    }
    public String sunris(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(weather,Context.MODE_PRIVATE);
        return sharedPreferences.getString(sunrise,null);
    }
    public String sunse(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(weather,Context.MODE_PRIVATE);
        return sharedPreferences.getString(sunset,null);
    }

    public boolean  Lat() {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(key_id,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(key_u,null)!=null){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean clear(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(key_id,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
}

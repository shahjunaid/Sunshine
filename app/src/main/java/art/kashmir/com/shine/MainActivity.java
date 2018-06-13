package art.kashmir.com.shine;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public String deg="\u00b0";
    public LocationManager locationManager;
    public LocationListener locationListener;
    TextView tmp, sunny,cityname,sunr,suns;
    public static Double lat,lon;
    public static String url;
    ImageView tpimg;
    public static String im;
    public static String imgurl;
    String sunrise,sunset;
    Button search;
    EditText s;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progress=findViewById(R.id.pr);
        progress.setVisibility(View.GONE);
        tmp=findViewById(R.id.temp);
        tmp.setText(sharedprefmanager.getInstance(this).degg());
        sunny=findViewById(R.id.sunny);
        sunny.setText(sharedprefmanager.getInstance(this).des());
        sunr=findViewById(R.id.sunrise);
        sunr.setText(sharedprefmanager.getInstance(this).sunris());
        suns=findViewById(R.id.sunset);
        suns.setText(sharedprefmanager.getInstance(this).sunse());
        tpimg=findViewById(R.id.set);
        cityname=findViewById(R.id.appname);
        cityname.setText(sharedprefmanager.getInstance(this).cityname());
s=findViewById(R.id.location);
search=findViewById(R.id.go);
Picasso.with(this).load(imgurl).into(tpimg);
search.setOnClickListener(this);
String cityname=sharedprefmanager.getInstance(getApplicationContext()).keyvalue();
        url="http://api.openweathermap.org/data/2.5/weather?q="+cityname+"&APPID=6c964689c724d80bf611e4893cf11bdb";
        url.replaceAll("//s+","");
        openweather();
        }

    public void openweather() {
        StringRequest stringRequest = new StringRequest(
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progress.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject temp= (JSONObject) jsonObject.get("main");
                    //String t=temp.getString("temp");
                    Double d=temp.getDouble("temp");
                    int a=d.intValue();
                    int b=a-273;
                    String c= String.valueOf(b);
                    tmp.setText(c);
                    tmp.append(deg);
                    tmp.append("C");
                    JSONObject sun=(JSONObject)jsonObject.get("sys");
                    String city=jsonObject.getString("name");
                    cityname.setText(city);
                    sunrise= DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date(sun.getInt("sunrise")*1000L));
                  sunr.setText(sunrise);
                    sunset= DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date(sun.getInt("sunset")*1000L));
                  suns.setText(sunset);
                  JSONArray jsonArray=jsonObject.getJSONArray("weather");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject o=jsonArray.getJSONObject(i);
                        sunny.setText(o.getString("description"));
                        im=o.getString("icon");
                        imgurl="http://openweathermap.org/img/w/"+im+".png";
                        sharedprefmanager.getInstance(getApplicationContext()).weather(
                                tmp.getText().toString(),
                                o.getString("description"),
                                imgurl,
                                sunrise,
                                sunset,
                                city

                        );
                        imgurl.replaceAll("//s+","");
                        Picasso.with(MainActivity.this).load(imgurl).into(tpimg);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.setVisibility(View.GONE);

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(MainActivity.this, "No internet available", Toast.LENGTH_SHORT).show();
                }
                if (error instanceof ServerError) {
                    Toast.makeText(MainActivity.this, "server error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        singletone.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(MainActivity.this,Main3Activity.class);
            startActivity(intent);
            return true;
        }
        if(id==R.id.changestate){
                sharedprefmanager.getInstance(getApplicationContext()).clear();
                Intent intent=new Intent(MainActivity.this,getcity.class);
                startActivity(intent);
                finish();
        }
        if(id==R.id.refresh){
            openweather();
            progress.setVisibility(View.VISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.go:
                String searched=s.getText().toString();
                if(!TextUtils.isEmpty(searched)){
                    Intent intent =new Intent(MainActivity.this,search.class);
                    intent.putExtra("searched query",searched);
                    startActivity(intent);
                        }
                        else{
                    Toast.makeText(this, "please enter city name", Toast.LENGTH_SHORT).show();
                }

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

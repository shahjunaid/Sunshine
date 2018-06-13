package art.kashmir.com.shine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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

public class search extends AppCompatActivity {
    String search;
    private String url;
    public String deg="\u00b0";
    TextView city,tempe,desc;
    ImageView im;
    String imgurl;
   private static String a;
     private static String nature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        city=findViewById(R.id.cityname);
        tempe=findViewById(R.id.temp);
        desc=findViewById(R.id.desc);
        im=findViewById(R.id.img);
      search=getIntent().getStringExtra("searched query");
      url="http://api.openweathermap.org/data/2.5/weather?q="+search+"&APPID=6c964689c724d80bf611e4893cf11bdb";
      url.replaceAll("//s+","");
      getsearcheddata();
    }
    public void getsearcheddata(){
        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject temp = (JSONObject) jsonObject.get("main");
                    //String t=temp.getString("temp");
                    Double d = temp.getDouble("temp");
                    int a = d.intValue();
                    int b =a-273;
                    String c = String.valueOf(b);
                    tempe.setText(c);
                    tempe.append(deg);
                    tempe.append("C");
                    String cityname = jsonObject.getString("name");
                    city.setText(cityname);
                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        desc.setText(o.getString("description"));
                    String img;
                        img = o.getString("icon");
                        imgurl = "http://openweathermap.org/img/w/"+img+".png";
                        imgurl.replaceAll("//s+","");
                    }
                    Picasso.with(search.this).load(imgurl).into(im);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                        Toast.makeText(search.this, "No internet available", Toast.LENGTH_SHORT).show();
                    }
                    if (error instanceof ServerError) {
                        Toast.makeText(search.this, "server error", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(search.this, "error while parsing data", Toast.LENGTH_SHORT).show();

                }
            });

        singletone.getInstance(search.this).addToRequestQueue(stringRequest);
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.backs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
switch (item.getItemId()){
    case R.id.action_settings:
        onBackPressed();
        break;

}
        return true;
    }

}


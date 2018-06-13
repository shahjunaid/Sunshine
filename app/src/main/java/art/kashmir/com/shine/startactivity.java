package art.kashmir.com.shine;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

public class startactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String citys=sharedprefmanager.getInstance(startactivity.this).keyvalue();
                if(sharedprefmanager.getInstance(startactivity.this).Lat()){
                    Intent intent =new Intent(startactivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent=new Intent(startactivity.this,getcity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },2000L);
    }

}
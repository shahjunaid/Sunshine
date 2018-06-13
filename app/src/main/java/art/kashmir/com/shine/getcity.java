package art.kashmir.com.shine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class getcity extends AppCompatActivity implements View.OnClickListener {
EditText statename;
Button go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getcity);
        statename=findViewById(R.id.state);
        go=findViewById(R.id.go);
        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.go:
                String stat=statename.getText().toString();
                if(!TextUtils.isEmpty(stat)){
                    try {
                        sharedprefmanager.getInstance(getApplicationContext()).key_id(stat);
                            Intent intent=new Intent(getcity.this,MainActivity.class);
                            startActivity(intent);
                            finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
                else{
                    statename.setError("please enter your city name e.g Srinagar");
                }
        }
    }
}

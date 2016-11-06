package com.logistical.logistical;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.dd.CircularProgressButton;

import javax.crypto.Mac;

public class LoginActivity extends AppCompatActivity {
    TextView ID;
    TextView MAC;
    String id;
    private void save(String id,String mac){
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("ID",id);
        editor.putString("MAC",mac);
        editor.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ID =(TextView) findViewById(R.id.ID);
        MAC = (TextView) findViewById(R.id.MAC);
        final CircularProgressButton login =  (CircularProgressButton) findViewById(R.id.loginButton);
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        ID.setText(pref.getString("ID",""));
        MAC.setText(pref.getString("MAC",""));
        assert login != null;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getProgress() == 0) {
                    for(int times = 0;times<3;times++)
                        for(int i=0;i<=100;i++) login.setProgress(i);
                    Intent intent  = new Intent(LoginActivity.this,InsertActivity.class);
                    try {
                        id = ID.getText().toString();
                    }
                    catch (Exception e){
                        Toast.makeText(LoginActivity.this,"请重新输入ID",Toast.LENGTH_LONG).show();
                        ID.setText("");
                    }
                    intent.putExtra("ID",id);
                    intent.putExtra("MAC",MAC.getText().toString());
                    Log.d("test",""+id+":"+MAC.getText().toString());
                    save(id,MAC.getText().toString());
                    startActivity(intent);
                    login.setProgress(0);
                }
            }
        });

    }
}

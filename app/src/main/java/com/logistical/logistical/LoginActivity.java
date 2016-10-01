package com.logistical.logistical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.dd.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {
    TextView ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ID =(TextView) findViewById(R.id.ID);
        final CircularProgressButton login =  (CircularProgressButton) findViewById(R.id.loginButton);

        assert login != null;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getProgress() == 0) {
                    for(int times = 0;times<3;times++)
                        for(int i=0;i<=100;i++) login.setProgress(i);
                    Intent intent  = new Intent(LoginActivity.this,InsertActivity.class);
                    try {
                        int id = Integer.parseInt(ID.getText().toString());
                        intent.putExtra("ID",id);
                        startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(LoginActivity.this,"请重新输入ID",Toast.LENGTH_LONG).show();
                        ID.setText("");

                    }
                    login.setProgress(0);
                }
            }
        });

    }
}

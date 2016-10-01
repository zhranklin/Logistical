package com.logistical.logistical;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class detailActivity extends AppCompatActivity {
    public static final String edit[] = {"Fstation2", "Tstation2", "danhao",
            "Fname", "Ftel", "Tname", "Ttel", "number", "uniprice", "daishou", "fankuan", "baojia", "jiehuo", "songyun",
            "totnumber", "tottranpay", "totpay","staffnum", "payway", "category1", "category2", "Fstation", "Tstation", "Ffankuan", "Tfankuan",};
    HashMap<String, EditText> mse = new HashMap<String, EditText>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

    }
}

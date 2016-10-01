package com.logistical.logistical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.logistical.model.Order;
import java.lang.reflect.Field;
import java.util.HashMap;

public class detailActivity extends AppCompatActivity {
    private static final String edit[] = new String[]{"Fstation_detail", "Tstation_detail", "danhao_detail",
            "Fname_detail", "Ftel_detail", "Tname_detail", "Ttel_detail", "payway_detail", "fankuanfang_detail", "Ffankuan_detail", "Tfankuan_detail",
            "daishou_detail", "fankuan_detail", "baojia_detail", "jiehuo_detail", "songyun_detail", "tottranpay_detail",
            "totnumber_detail", "totpay_detail",};

    private static final String staffedit[]={"staffnum_detail", "category1_detail", "category2_detail", "number_detail", "uniprice_detail" };

    private final String[] Chinese = {"发站", "到站", "客户单号",
            "发货人", "发货人电话", "收货人", "收货人电话", "付款方式", "返款方", "返款方式1", "返款方式2",
            "代收款", "返款费", "保价费", "接货费", "送货费","总运费",
            "总件数", "总价"};
    HashMap<String, EditText> mse = new HashMap<String, EditText>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        for (String anEdit : edit) {
            Field r = null;
            try {
                r = R.id.class.getField(anEdit);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            assert r != null;
            try {
                mse.put(anEdit,(EditText)findViewById(r.getInt(null)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra("order");
        Log.e("aaa","aaa"+order.getAttribute("客户单号"));
        for(int i=0;i<edit.length;i++) {
            mse.get(edit[i]).setFocusable(false);
            if(i<=10){
                mse.get(edit[i]).setText(order.getAttribute(Chinese[i]));
            }
            else if(i<=15){
                mse.get(edit[i]).setText(""+order.getFee(Chinese[i]));
            }
        }


    }
}

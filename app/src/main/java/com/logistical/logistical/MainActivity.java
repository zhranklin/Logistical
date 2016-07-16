package com.logistical.logistical;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import express.*;


public class MainActivity extends AppCompatActivity{
    public static final String ATTRIBUTE_NAMES[][] = { { "发站", "到站" }, { "发货人", "发货人电话" }, { "收货人", "收货人电话" },
            { "付款方式", "品类", "小类", "件数", "单价", "已付", "提付", "月结" }, { "代收款", "返款方", "从", "到", "保价费", "接货费", "送运费" },
            { "总件数", "总运费" } };
    public static final String edit[] = { "Fname", "Ftel" ,
            "Tname", "Ttel" ,
             "number", "uniprice", "HavePay", "ToPay", "MonthPay" ,
            "daishou", "fankuan", "baojia", "jiehuo", "songyun" , { "totnumber", "totpay"  };
    public static final String list[] ={
        "payWay", "category1", "category2","Fstation", "Tstation","Fbaojia", "Tbaojia",
    }
    HashMap<String,EditText> mse = new HashMap<String, EditText>();
    HashMap<String,Spinner> mss = new HashMap<String, Spinner>();
    //HashMap<String,>

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            for(int i=0;i<edit.length;i++) {
                Field r = R.id.class.getField(edit[i]);
                mse.put(edit[i],(EditText) findViewById((Integer) r.getInt(null)));
            }
            for(int i=0;i<list.length;i++) {
                Field r = R.id.class.getField(list[i]);
                mss.put(list[i],(Spinner) findViewById((Integer) r.getInt(null)));
            }
            //xx.setText("aaa");
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    public static int getId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,"id", paramContext.getPackageName());
    }
}

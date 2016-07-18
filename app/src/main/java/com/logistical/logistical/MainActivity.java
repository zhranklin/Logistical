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

public class MainActivity extends AppCompatActivity{
    /*
    TODO: 当时有讨论过, 就是说每件商品里面不需要付款方式, 付款方式放在整个订单里面, 而且没有已付提付月结这三个数额,
    已经在付款方式里面了(已付提付月结三种) 最后还要加个总价 总运费(我的意见)是指所有商品的运费加起来, 总价是还要加上
    返款费之类的最终价格, 当然也可以分别起别的名字, 然后那个从和到是什么...最后差不多是这样(你把送货费打成了送运费):
    */
    public static final String ATTRIBUTE_NAMES[][] = { { "发站", "到站" }, { "发货人", "发货人电话" }, { "收货人", "收货人电话" },
    {"品类", "小类", "件数", "单价"}, { "付款方式", "代收款", "返款方", "从", "到", "保价费", "接货费", "送货费" },
    {"总件数", "总运费", "总价"}};

    public static final String edit[] = { "Fname", "Ftel" ,
            "Tname", "Ttel" ,
             "number", "uniprice", "HavePay", "ToPay", "MonthPay" ,
            "daishou", "fankuan", "baojia", "jiehuo", "songyun" ,  "totnumber", "totpay"  };
    public static final String list[] ={
        "payWay", "category1", "category2","Fstation", "Tstation","Ffankuan", "Tfankuan",
    };
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

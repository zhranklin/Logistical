package com.logistical.logistical;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import com.logistical.model.Order;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import java.lang.reflect.Field;
import java.util.HashMap;

public class InsertActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    HashMap<String,EditText> mse = new HashMap<String, EditText>();
    HashMap<String,Spinner> mss = new HashMap<String, Spinner>();
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
    public int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        ID = getIntent().getIntExtra("ID",0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView)  findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView TextForId = (TextView) header.findViewById(R.id.TextForID);
        TextForId.setText(""+ID);
        try {
                init();
            } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        }
    }
    private void init() throws NoSuchFieldException, IllegalAccessException {
        for(int i=0;i<edit.length;i++) {
            Field r = R.id.class.getField(edit[i]);
            mse.put(edit[i],(EditText) findViewById((Integer) r.getInt(null)));
            if(mse.get(edit[i])==null) Toast.makeText(this,"hello ",Toast.LENGTH_LONG).show();
        }
        for(int i=0;i<list.length;i++) {
            Field r = R.id.class.getField(list[i]);
            mss.put(list[i],(Spinner) findViewById((Integer) r.getInt(null)));
            if(mss.get(edit[i])==null) Toast.makeText(this,"hello ",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.query) {

        } else if (id == R.id.insert) {

        } else if (id == R.id.export) {

        } else if (id == R.id.print) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    boolean makeOrder(){
       // Order order = new Order();
        return true;
    }
}


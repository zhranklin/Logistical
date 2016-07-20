package com.logistical.logistical;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.logistical.model.*;
import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class InsertActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    HashMap<String,EditText> mse = new HashMap<String, EditText>();
    HashMap<String,Spinner> mss = new HashMap<String, Spinner>();
    private List<String> StaffString;
    private ArrayAdapter<String> StaffAdapter;
    public static final String edit[] = {"Fstation2",  "Tstation2","danhao",
            "Fname", "Ftel", "Tname", "Ttel", "number", "uniprice", "daishou", "fankuan", "baojia", "jiehuo", "songyun",
            "totnumber", "tottranpay", "totpay"};
    public static final String list[] ={
           "staffnum", "payWay", "category1", "category2","Fstation", "Tstation","Ffankuan", "Tfankuan",
    };
    public int ID;
    int index=1;
    private LayoutInflater inflater;
    AddFloatingActionButton addStaff;
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
        inflater=LayoutInflater.from(this);
//        mss.get("Fstation").setSelection(1);

        /*addStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaffAdapter.add(""+(++index));
                int now = Integer.parseInt(mss.get("staffnum").getSelectedItem().toString());

            }
        });
        mss.get("staffnum").setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mse.get("number").setText("");
                mse.get("uniprice").setText("");
                mss.get("category1").setSelection(0);
                mss.get("category2").setSelection(0);
            }
        });
        mse.get("number").addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO
            }
        });
        mse.get("uniprice").addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO
            }
        });
        mss.get("category1").setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mss.get("category2").setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
    }
    private void save(int x){

    }
    private void init() throws NoSuchFieldException, IllegalAccessException {
        for(int i=0;i<edit.length;i++) {
            Log.e("Insert",""+i);
            Field r = R.id.class.getField(edit[i]);
            mse.put(edit[i],(EditText) findViewById((Integer) r.getInt(null)));
            mse.get(edit[i]).setText("aaaa");
        }
        for(int i=0;i<list.length;i++) {
            Field r = R.id.class.getField(list[i]);
            mss.put(list[i],(Spinner) findViewById((Integer) r.getInt(null)));
            if(mss.get(edit[i])==null) Toast.makeText(this,"hello ",Toast.LENGTH_LONG).show();

        }
        addStaff = (AddFloatingActionButton) findViewById(R.id.content_frame).findViewById(R.id.addStaff);
        StaffString.add("1");
        StaffAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,StaffString);
        StaffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mss.get("staffnum").setAdapter(StaffAdapter);
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
            for(int i=0;i<list.length;i++) {
                //mss.get(list[i]).get
            }
        } else if (id == R.id.export) {

        } else if (id == R.id.print) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}


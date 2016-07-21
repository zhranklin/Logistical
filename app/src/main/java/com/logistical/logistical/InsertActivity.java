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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;
import scala.Array;

public class InsertActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    HashMap<String,EditText> mse = new HashMap<String, EditText>();
    HashMap<String,Spinner> mss = new HashMap<String, Spinner>();
    private ArrayList<String> StaffString = new ArrayList<String>();
    private ArrayAdapter<String> StaffAdapter;
    public static final String edit[] = {"Fstation2",  "Tstation2","danhao",
            "Fname", "Ftel", "Tname", "Ttel", "number", "uniprice", "daishou", "fankuan", "baojia", "jiehuo", "songyun",
            "totnumber", "tottranpay", "totpay"};
    public static final String list[] ={
           "staffnum", "payway", "category1", "category2","Fstation", "Tstation","Ffankuan", "Tfankuan",
    };
    public int ID;
    int index=1;
    AddFloatingActionButton addStaff;
    Staff staff[] = new Staff[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        ID = getIntent().getIntExtra("ID",0);
        staff[1] = new Staff();
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
        addStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Staff temstaff = new Staff(mss.get("category1").getSelectedItem().toString(), mss.get("category2").getSelectedItem().toString(),
                            Integer.parseInt(mse.get("number").getText().toString()), Integer.parseInt(mse.get("uniprice").getText().toString()));
                    staff[index] = temstaff;
                }catch (Exception e) {
                 //   Toast.makeText(InsertActivity.this,"不能有空或者非数字",Toast.LENGTH_SHORT).show();
                }
                StaffString.add(""+(++index));
                mss.get("staffnum").setSelection(index);
                /*mss.get("category1").setSelection(0);
                mss.get("category2").setSelection(0);
                mse.get("uniprice").setText("");
                mse.get("number").setText("");*/
            }
        });
        for(int i=2;i<=3;i++) {
            final Spinner tmp = mss.get(list[i]);
            tmp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int index = Integer.parseInt(mss.get("staffnum").getSelectedItem().toString());
                    if (staff[index] == null) {
                        staff[index] = new Staff();
                    }
                    staff[index].setType(tmp.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        for(int i=7;i<=8;i++) {
            final EditText tmp = mse.get(edit[i]);
            tmp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int index = Integer.parseInt(mss.get("staffnum").getSelectedItem().toString());
                    if (staff[index] == null) {
                        staff[index] = new Staff();
                    }
                    Toast.makeText(InsertActivity.this,""+index+(staff[index]==null),Toast.LENGTH_LONG).show();
                    // staff[index].setNumber(Integer.parseInt(tmp.getText().toString()));
                }
            });
        }

    }

    private void init() throws NoSuchFieldException, IllegalAccessException {
        addStaff = (AddFloatingActionButton) findViewById(R.id.main_content).findViewById(R.id.addStaff);
        for(int i=0;i<edit.length;i++) {
            Field r = R.id.class.getField(edit[i]);
            mse.put(edit[i],(EditText) findViewById(R.id.main_content).findViewById((Integer) r.getInt(null)));
        }
        for(int i=0;i<list.length;i++) {
            Field r = R.id.class.getField(list[i]);
            mss.put(list[i],(Spinner) findViewById(R.id.main_content).findViewById((Integer) r.getInt(null)));

        }
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


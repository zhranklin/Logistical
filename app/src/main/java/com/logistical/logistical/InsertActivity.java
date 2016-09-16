package com.logistical.logistical;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.logistical.model.*;
import com.getbase.floatingactionbutton.AddFloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import static android.support.v4.app.ActivityCompat.startActivityForResult;


public class InsertActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    HashMap<String, EditText> mse = new HashMap<String, EditText>();
    HashMap<String, Spinner> mss = new HashMap<String, Spinner>();
    private ArrayList<String> StaffString = new ArrayList<String>();
    private ArrayAdapter<String> StaffAdapter;
    private final int REQUEST_ENABLE_BT = 1;
    public static final String edit[] = {"Fstation2", "Tstation2", "danhao",
            "Fname", "Ftel", "Tname", "Ttel", "number", "uniprice", "daishou", "fankuan", "baojia", "jiehuo", "songyun",
            "totnumber", "tottranpay", "totpay"};
    public static final String list[] = {
            "staffnum", "payway", "category1", "category2", "Fstation", "Tstation", "Ffankuan", "Tfankuan",
    };
    public int ID;
    int index = 1;
    AddFloatingActionButton addStaff;
    Staff staff[] = new Staff[100];
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        ID = getIntent().getIntExtra("ID", 0);
        staff[1] = new Staff();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();


                if (id == R.id.query) {

                } else if (id == R.id.insert) {
                    Toast.makeText(InsertActivity.this,"press",Toast.LENGTH_LONG).show();
                } else if (id == R.id.export) {

                } else if (id == R.id.print) {

                }
                else if (id == R.id.bluetooth){
                    BluetoothAdapter mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
                    if(mBluetoothAdapter == null) {
                        Toast.makeText(InsertActivity.this,"Device not support Bluetooth",Toast.LENGTH_LONG).show();

                    }
                    if (!mBluetoothAdapter.isEnabled()) {
                        Toast.makeText(InsertActivity.this,"未打开蓝牙",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent,REQUEST_ENABLE_BT);
                        mBluetoothAdapter.startDiscovery();
                        intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        //设置可被发现的时间，300s
                        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                        startActivity(intent);
                    }
                    mBluetoothAdapter.startDiscovery();
                    BroadcastReceiver mReceiver = new BroadcastReceiver() {
                        public void onReceive(Context context, Intent intent) {
                            String action = intent.getAction();

                            // 当有设备被发现的时候会收到 action == BluetoothDevice.ACTION_FOUND 的广播
                            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                                //广播的 intent 里包含了一个 BluetoothDevice 对象
                                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                                //假设我们用一个 ListView 展示发现的设备，那么每收到一个广播，就添加一个设备到 adapter 里
                             //  Toast.makeText(InsertActivity.this,device.getName() + "\n" + device.getAddress(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
                    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                    BluetoothDevice useDevice = null;
                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                     //       Toast.makeText(InsertActivity.this,device.getName() + "\n" + device.getAddress(),Toast.LENGTH_LONG).show();
                           // Toast.makeText(InsertActivity.this,device.getAddress(),Toast.LENGTH_LONG).show();
                            if(device.getAddress().equals("00:0C:B6:02:EB:CF")){
                                useDevice = device;
                            }
                        }
                    }
                    Toast.makeText(InsertActivity.this,useDevice.getAddress(),Toast.LENGTH_LONG).show();
                    ConnectThread cnt = new ConnectThread(useDevice,new byte[]{1,0,0,1,1,0,1,1,1});
                    Toast.makeText(InsertActivity.this,"cnt"+(cnt.mmSocket==null),Toast.LENGTH_LONG).show();
                    cnt.start();
                    //cnet.write(new byte[]{0,0,0,1,1,1,});
                }
                item.setCheckable(true);
                drawer.closeDrawers();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView TextForId = (TextView) header.findViewById(R.id.TextForID);
        TextForId.setText("" + ID);
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
                } catch (Exception e) {
                    //   Toast.makeText(InsertActivity.this,"不能有空或者非数字",Toast.LENGTH_SHORT).show();
                }
                StaffAdapter.add("" + (++index));
                StaffString.add("" + index);
                mss.get("staffnum").setSelection(index);
                mss.get("category1").setSelection(0);
                mss.get("category2").setSelection(0);
                mse.get("uniprice").setText("");
                mse.get("number").setText("");
            }
        });
        for (int i = 2; i <= 3; i++) {
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
        for (int i = 7; i <= 8; i++) {
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
                    //Toast.makeText(InsertActivity.this, "" + index + (staff[index] == null), Toast.LENGTH_LONG).show();
                    try {
                        staff[index].setNumber(Integer.parseInt(tmp.getText().toString()));
                    } catch (NumberFormatException e) {
                        Toast.makeText(InsertActivity.this, "请输入数字", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private void init() throws NoSuchFieldException, IllegalAccessException {
        addStaff = (AddFloatingActionButton) findViewById(R.id.main_content).findViewById(R.id.addStaff);
        for (int i = 0; i < edit.length; i++) {
            Field r = R.id.class.getField(edit[i]);
            mse.put(edit[i], (EditText) findViewById(R.id.main_content).findViewById((Integer) r.getInt(null)));
        }
        for (int i = 0; i < list.length; i++) {
            Field r = R.id.class.getField(list[i]);
            mss.put(list[i], (Spinner) findViewById(R.id.main_content).findViewById((Integer) r.getInt(null)));

        }
        StaffString.add("1");
        StaffAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, StaffString);
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
        if (id == R.id.bluetooth){


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        return true;
    }

    private void makeOrder(){

    }
    class ConnectThread extends Thread {
        public final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private byte[] mbyte;
        public ConnectThread(BluetoothDevice device,byte[] mbyte) {
            this.mbyte = mbyte;
            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                // 通过 BluetoothDevice 获得 BluetoothSocket 对象
                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmSocket = tmp;
        }

        @Override
        public void run() {
            // 建立连接前记得取消设备发现
            try {
                // 耗时操作，所以必须在主线程之外进行
                mmSocket.connect();
                if(mmSocket.isConnected()){
                    try{
                        OutputStream outputStream = mmSocket.getOutputStream();
                        outputStream.write(mbyte);
                        outputStream.flush();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException connectException) {
                //处理连接建立失败的异常
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }
        }

        //关闭一个正在进行的连接
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }



}

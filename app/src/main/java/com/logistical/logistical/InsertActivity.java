package com.logistical.logistical;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.logistical.model.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class InsertActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    HashMap<String, EditText> mse = new HashMap<String, EditText>();
    HashMap<String, Spinner> mss = new HashMap<String, Spinner>();
    private ArrayList<String> StaffString = new ArrayList<>();
    private ArrayAdapter<String> StaffAdapter;
    private ListViewAdapter listviewadapter;
    private final int REQUEST_ENABLE_BT = 1;
    private static final String edit[] = {"Fstation2", "Tstation2", "danhao",
            "Fname", "Ftel", "Tname", "Ttel", "number", "uniprice", "daishou", "fankuan", "baojia", "jiehuo", "songyun",
            "totnumber", "tottranpay", "totpay"};
    private final String[] FEE = {"代收款", "返款费", "保价费", "接货费", "送货费", "总件数", "总运费", "总价"};
    private final String[] ATTR = {"发站", "到站", "客户单号", "发货人", "发货人电话", "收货人", "收货人电话", "付款方式",
            "返款方", "返款方式1", "返款方式2"};
    public static final String list[] = {
            "staffnum", "payway", "category1", "category2", "Fstation", "Tstation", "fankuanfang","Ffankuan", "Tfankuan"
    };
    private int ID, totindex = 1;
    private Button addStaff, saveStaff, confirm;
    private ListView listview;
    private Staff staff[] = new Staff[100];
    private View insert_layout, query_layout;
    private ArrayList<Order> OrderList;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        ID = getIntent().getIntExtra("ID", 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.query) {
                    insert_layout.setVisibility(View.GONE);
                    query_layout.setVisibility(View.VISIBLE);
                    listview = (ListView) findViewById(R.id.list_view);
                    IdentityHashMap<String, String> att = new IdentityHashMap<String, String>();
                    IdentityHashMap<String, Integer> fee = new IdentityHashMap<String, Integer>();
                    ArrayList stf = new ArrayList();
                    stf.add(new Staff("大","小",1,2));
                    for(int i=0;i<ATTR.length;i++) att.put(ATTR[i],""+i);
                    for(int i=0;i<FEE.length;i++) fee.put(FEE[i],i);
                    OrderList = new ArrayList<Order>();
                    OrderList.add(new Order(att,fee, stf));
                    OrderList.add( new Order(att,fee,stf));
                    OrderList.add( new Order(att,fee,stf));
                    OrderList.add( new Order(att,fee,stf));
                    listviewadapter = new ListViewAdapter(InsertActivity.this, R.layout.item,OrderList);
                    listview.setAdapter(listviewadapter);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                            Order order = OrderList.get(position);
                            Log.e("aaa","bbb"+order.getAttribute("客户单号"));
                            Intent intent = new Intent(InsertActivity.this,detailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("order",order);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });

                } else if (id == R.id.insert) {
                    insert_layout.setVisibility(View.VISIBLE);
                    query_layout.setVisibility(View.GONE);
                    staff = new Staff[100];
                    totindex = 1;
                    for (String anEdit : edit) {
                        mse.get(anEdit).setText("");
                    }
                    staff[1] = new Staff();
                    StaffString = new ArrayList<String>();
                    StaffString.add("1");
                    StaffAdapter = new ArrayAdapter<String>(InsertActivity.this, android.R.layout.simple_spinner_item, StaffString);
                    StaffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    mss.get("staffnum").setAdapter(StaffAdapter);
                    for (String anList : list) {
                        Log.e("anlist", anList);
                        mss.get(anList).setSelection(0);
                    }

                } else if (id == R.id.export) {

                } else if (id == R.id.print) {

                } else if (id == R.id.bluetooth) {
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter == null) {
                        Toast.makeText(InsertActivity.this, "Device not support Bluetooth", Toast.LENGTH_LONG).show();

                    }
                    if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
                        Toast.makeText(InsertActivity.this, "未打开蓝牙", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, REQUEST_ENABLE_BT);
                        mBluetoothAdapter.startDiscovery();
                        intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        //设置可被发现的时间，300s
                        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                        startActivity(intent);
                    }
                    if (mBluetoothAdapter != null) {
                        mBluetoothAdapter.startDiscovery();
                    }
                    BroadcastReceiver mReceiver = new BroadcastReceiver() {
                        public void onReceive(Context context, Intent intent) {
                            String action = intent.getAction();
                            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            }
                        }
                    };
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
                    Set<BluetoothDevice> pairedDevices = null;
                    if (mBluetoothAdapter != null) {
                        pairedDevices = mBluetoothAdapter.getBondedDevices();
                    }
                    BluetoothDevice useDevice = null;
                    if ((pairedDevices != null ? pairedDevices.size() : 0) > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            if (device.getAddress().equals("00:0C:B6:02:EB:CF")) {
                                useDevice = device;
                            }
                        }
                    }
                    if (useDevice != null) {
                        Toast.makeText(InsertActivity.this, useDevice.getAddress(), Toast.LENGTH_LONG).show();
                    }
                    ConnectThread cnt = new ConnectThread(useDevice, null);
                    Toast.makeText(InsertActivity.this, "cnt" + (cnt.mmSocket == null), Toast.LENGTH_LONG).show();
                    cnt.start();
                }

                item.setCheckable(true);
                drawer.closeDrawers();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                assert drawer != null;
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
                    save();
                } catch (NullValueException e1) {
                    Toast.makeText(InsertActivity.this, "存在未完成的表单", Toast.LENGTH_LONG).show();
                    return;
                }
                StaffString.add("" + (++totindex));
                // StaffAdapter.add("" + (++totindex));
                mss.get("staffnum").setSelection(totindex - 1);
                mss.get("category1").setSelection(0);
                mss.get("category2").setSelection(0);
                mse.get("uniprice").setText("");
                mse.get("number").setText("");
            }
        });
        saveStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    save();
                } catch (NullValueException e) {
                    Toast.makeText(InsertActivity.this, "存在未完成的表单", Toast.LENGTH_LONG).show();
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Order order = makeOrder();
                } catch (NullValueException e1) {
                    Toast.makeText(InsertActivity.this, "存在未完成的表单", Toast.LENGTH_LONG).show();
                } catch (NotNumberException e2) {
                    Toast.makeText(InsertActivity.this, "请正确填写数字", Toast.LENGTH_LONG).show();
                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    private void init() throws NoSuchFieldException, IllegalAccessException {
        addStaff = (Button) findViewById(R.id.main_content).findViewById(R.id.insert_layout).findViewById(R.id.Addstaff);
        saveStaff = (Button) findViewById(R.id.main_content).findViewById(R.id.insert_layout).findViewById(R.id.Savestaff);
        confirm = (Button) findViewById(R.id.main_content).findViewById(R.id.insert_layout).findViewById(R.id.confirm);
        query_layout = findViewById(R.id.query_layout);
        insert_layout = findViewById(R.id.insert_layout);
        query_layout.setVisibility(View.GONE);
        insert_layout.setVisibility(View.VISIBLE);
        staff[1] = new Staff();
        for (String anEdit : edit) {
            Field r = R.id.class.getField(anEdit);
            mse.put(anEdit, (EditText) findViewById(R.id.main_content).findViewById(R.id.insert_layout).findViewById(r.getInt(null)));
        }
        for (String aList : list) {
            Field r = R.id.class.getField(aList);
            mss.put(aList, (Spinner) findViewById(R.id.main_content).findViewById(R.id.insert_layout).findViewById(r.getInt(null)));

        }
        StaffString.add("1");
        StaffAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, StaffString);
        StaffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mss.get("staffnum").setAdapter(StaffAdapter);

    }

    private void save() throws NullValueException {
        int index = Integer.parseInt(mss.get("staffnum").getSelectedItem().toString());
        try {
            Staff temstaff = new Staff(mss.get("category1").getSelectedItem().toString(), mss.get("category2").getSelectedItem().toString(),
                    Integer.parseInt(mse.get("number").getText().toString()), Integer.parseInt(mse.get("uniprice").getText().toString()));
            staff[index] = temstaff;
        } catch (Exception e) {
            Toast.makeText(InsertActivity.this, "不能有空或者非数字", Toast.LENGTH_SHORT).show();
            throw new NullValueException();
        }
        // TODO 判断空
        Log.e("save", staff[index].toString());
        Log.e("save", staff[index].toString());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        return true;
    }

    private Order makeOrder() throws NullValueException, NotNumberException {
        IdentityHashMap<String, String> attributes = new IdentityHashMap<String, String>();
        IdentityHashMap<String, Integer> fee = new IdentityHashMap<>();
        for (int i = 9; i < edit.length; i++) {
            if ((mse.get(edit[i]).getText().toString().equals(""))) throw new NullValueException();
            try {
                fee.put(FEE[i - 9], Integer.parseInt(mse.get(edit[i]).getText().toString()));
            } catch (Exception e) {
                throw new NotNumberException();
            }
        }
        for (int i = 0; i <= 6; i++) {
            attributes.put(ATTR[i], mse.get(edit[i]).getText().toString());
        }
        attributes.put("付款方式", mss.get("payway").getSelectedItem().toString());
        attributes.put("返款方式1", mss.get("Ffankuan").getSelectedItem().toString());
        attributes.put("返款方式2", mss.get("Tfankuan").getSelectedItem().toString());
        //TODO 验证合法性
        return new Order(attributes, fee, Arrays.asList(staff));
    }



    class ConnectThread extends Thread {
        public final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private byte[] mbyte;

        public ConnectThread(BluetoothDevice device, byte[] mbyte) {
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
            Toast.makeText(InsertActivity.this, "socket:" + (mmSocket == null), Toast.LENGTH_LONG).show();
        }

        @Override
        public void run() {
            try {
                mmSocket.connect();
                String text = mse.get("danhao").getText().toString();
                Log.e("mmconnect", "" + mmSocket.isConnected());
                if (mmSocket.isConnected()) {
                    try {
                        OutputStream outputStream = mmSocket.getOutputStream();
                        Log.e("mmconnect", "" + (outputStream == null));
                        assert outputStream != null;
                 //       PrintWork.testPrintWork1(outputStream).run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (mmSocket.isConnected())
                        mmSocket.close();
                } catch (IOException closeException) {
                    closeException.printStackTrace();
                }

            }
        }
    }
}

class NullValueException extends Exception {

}

class NotNumberException extends Exception {

}
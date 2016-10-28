package com.logistical.logistical;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.logistical.model.Order;
import com.logistical.model.Staff;
import com.logistical.tools.Porting;
import com.logistical.tools.PrintWork;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class detailActivity extends AppCompatActivity {
    private static final String edit[] = new String[]{"Fstation_detail", "Tstation_detail", "danhao_detail",
            "Fname_detail", "Ftel_detail", "Tname_detail", "Ttel_detail", "payway_detail", "fankuanfang_detail", "Ffankuan_detail", "Tfankuan_detail",
            "daishou_detail", "fankuan_detail", "baojia_detail", "jiehuo_detail", "songyun_detail", "tottranpay_detail",
            "totnumber_detail", "totpay_detail", "category1_detail", "category2_detail", "number_detail", "uniprice_detail"};

    //private static final String staffedit[]={"staffnum_detail", "category1_detail", "category2_detail", "number_detail", "uniprice_detail" };

    private final String[] Chinese = {"发站", "到站", "客户单号",
            "发货人", "发货人电话", "收货人", "收货人电话", "付款方式", "返款方", "返款方式1", "返款方式2",
            "代收款", "返款费", "保价费", "接货费", "送货费","总运费",
            "总件数", "总价"};
    private HashMap<String, EditText> mse = new HashMap<String, EditText>();
    private Button print_part,print_all;
    private Order order;
    private List<Staff> staff;
    private String ID;
    private String MAC;
    private BroadcastReceiver mReceiver;
    private final int REQUEST_ENABLE_BT = 1;
    private BluetoothDevice useDevice;
    private Spinner staffSpinner;
    private ArrayList<String> adaptString=new ArrayList<String>();
    private ArrayAdapter<String> StaffAdapter;
    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(mReceiver);
        }catch(Exception e){
            Log.d("stop","not register");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String Sorder =  intent.getStringExtra("order");
        MAC= intent.getStringExtra("MAC");
        ID = intent.getStringExtra("ID");
        order = Order.fromJson(Sorder);
        staff = order.staff();
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

        for(int i=0;i<edit.length;i++) {
           if(!edit[i].equals("number_detail")) mse.get(edit[i]).setFocusable(false);
            if(i<=10){
                mse.get(edit[i]).setText(order.getAttribute(Chinese[i]));
            }
            else if(i<=15){
                mse.get(edit[i]).setText(""+order.getFee(Chinese[i]));
            }
        }
        mse.get("tottranpay_detail").setText(""+order.getFee("总运费"));
        mse.get("totpay_detail").setText(""+order.getTotalFee());
        mse.get("totnumber_detail").setText(""+order.getTotalNumber());
        print_all = (Button) findViewById(R.id.print_all);
        print_part = (Button) findViewById(R.id.print_part);
        staffSpinner = (Spinner) findViewById(R.id.staffnum_detail);
        int number = order.staff().size();
        for(int i=1;i<=number;i++) adaptString.add(""+i);
        StaffAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adaptString);
        staffSpinner.setAdapter(StaffAdapter);
        staffSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int tmp = Integer.parseInt(parent.getSelectedItem().toString())-1;
                Log.d("tmp",""+tmp);
                mse.get("category1_detail").setText(""+staff.get(tmp).getType());
                mse.get("category2_detail").setText(""+staff.get(tmp).getSubType());
                mse.get("uniprice_detail").setText(""+staff.get(tmp).getPrice());
                mse.get("number_detail").setText(""+staff.get(tmp).getNumber());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        print_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(detailActivity.this, "Device not support Bluetooth", Toast.LENGTH_LONG).show();
                }
                if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
                    Toast.makeText(detailActivity.this, "未打开蓝牙", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                    mBluetoothAdapter.startDiscovery();
                    intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    startActivity(intent);
                }
                if (mBluetoothAdapter != null) {
                    mBluetoothAdapter.startDiscovery();
                }
                mReceiver = new BroadcastReceiver() {
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
                useDevice = null;
                if ((pairedDevices != null ? pairedDevices.size() : 0) > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        if (device.getAddress().equals(MAC)) {
                            useDevice = device;
                        }
                    }
                }
                ConnectThreadOrder cnt = new ConnectThreadOrder(useDevice, null);
                cnt.start();
            }
        });
        print_part.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(detailActivity.this, "设备不支持蓝牙", Toast.LENGTH_LONG).show();
                }
                if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
                    Toast.makeText(detailActivity.this, "未打开蓝牙", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                    mBluetoothAdapter.startDiscovery();
                    intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    startActivity(intent);
                }
                if (mBluetoothAdapter != null) {
                    mBluetoothAdapter.startDiscovery();
                }
                mReceiver = new BroadcastReceiver() {
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
                useDevice = null;
                if ((pairedDevices != null ? pairedDevices.size() : 0) > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        if (device.getAddress().equals(MAC)) {
                            useDevice = device;
                        }
                    }
                }
                ConnectThreadStaff cnt = new ConnectThreadStaff(useDevice, null);
                cnt.start();
            }

        });


    }
    class ConnectThreadOrder extends Thread {
        public final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private byte[] mbyte;

        public ConnectThreadOrder(BluetoothDevice device, byte[] mbyte) {
            this.mbyte = mbyte;
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmSocket = tmp;
        }

        @Override
        public void run() {
            try {
                mmSocket.connect();
                String text = mse.get("danhao_detail").getText().toString();
                Log.e("mmconnect", "" + mmSocket.isConnected());
                if (mmSocket.isConnected()) {
                    try {
                        OutputStream outputStream = mmSocket.getOutputStream();
                        Log.e("mmconnect", "" + (outputStream == null));
                        assert outputStream != null;
                        PrintWork.builder().printOrder(order).build(outputStream).run();
                        outputStream.close();
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
    class ConnectThreadStaff extends Thread {
        public final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private byte[] mbyte;

        public ConnectThreadStaff(BluetoothDevice device, byte[] mbyte) {
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
            try {
                mmSocket.connect();
                String text = mse.get("danhao_detail").getText().toString();
                Log.e("mmconnect", "" + mmSocket.isConnected());
                if (mmSocket.isConnected()) {
                    try {
                        OutputStream outputStream = mmSocket.getOutputStream();
                        Log.e("mmconnect", "" + (outputStream == null));
                        assert outputStream != null;
                        int num = Integer.parseInt(staffSpinner.getSelectedItem().toString())-1;
                        int tmp = 0;
                        for (int i=1;i<num;i++) tmp+=staff.get(i).getNumber();
                        Log.d("tmp","tmp:"+tmp+"detail:"+(tmp+Integer.parseInt(mse.get("number_detail").getText().toString())));
                        PrintWork.builder().printStaff(order,staff.get(num),tmp+Integer.parseInt(mse.get("number_detail").getText().toString())).build(outputStream).run();
                        outputStream.close();
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

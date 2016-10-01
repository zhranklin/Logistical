package com.logistical.logistical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.logistical.model.Order;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by 晗涛 on 2016/10/1.
 */
class ListViewAdapter extends ArrayAdapter<Order> {
    private int resouceId;
    public ListViewAdapter(Context context, int textViewResourceId, List<Order> objects){
        super(context,textViewResourceId,objects);
        resouceId = textViewResourceId;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = (Order)getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resouceId,null);
        TextView ItemId = (TextView) view.findViewById(R.id.itemId);
        TextView ItemFname = (TextView) view.findViewById(R.id.itemFname);
        TextView ItemTname = (TextView) view.findViewById(R.id.itemTname);
        TextView ItemTotFee = (TextView) view.findViewById(R.id.itemTotFee);
        TextView ItemNumber = (TextView) view.findViewById(R.id.itemNumber);
        ItemId.setText(order.getAttribute("客户单号"));
        ItemFname.setText(order.getAttribute("发货人"));
        ItemTname.setText(order.getAttribute("收货人"));
        ItemTotFee.setText(order.getTotalFee());
        ItemNumber.setText(order.getTotalNumber());
        return view;
    }

}
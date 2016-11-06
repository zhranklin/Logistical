package com.logistical.logistical;

/**
 * Created by 晗涛 on 2016/10/1.
 */
public class Item{
    private String ItemId,ItemFname,ItemTname,ItemNumber,ItemTotFee;
    public Item(String ItemId,String ItemFname,String ItemTname,String ItemNumber,String ItemTotFee){
        this.ItemFname=ItemFname;
        this.ItemId=ItemId;
        this.ItemNumber=ItemNumber;
        this.ItemTotFee=ItemTotFee;
        this.ItemTname=ItemTname;
    }
    public String getItemFname() {
        return ItemFname;
    }

    public String getItemId() {
        return ItemId;
    }

    public String getItemNumber() {
        return ItemNumber;
    }

    public String getItemTname() {
        return ItemTname;
    }

    public String getItemTotFee() {
        return ItemTotFee;
    }

}
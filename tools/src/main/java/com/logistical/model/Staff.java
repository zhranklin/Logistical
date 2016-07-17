package com.logistical.model;

public class Staff {
  private String type;
  private String subType;
  private int number;

  public Staff(String type, String subType, int number, int price) {
    this.type = type;
    this.subType = subType;
    this.number = number;
    this.price = price;
  }

  private int price;

  public String getType() {
    return type;
  }

  public String getSubType() {
    return subType;
  }

  public int getNumber() {
    return number;
  }

  public int getPrice() {
    return price;
  }

}

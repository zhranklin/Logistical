package com.logistical.model;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Order {
  /**
   * 可用的属性名称, 作为getAttribute的参数, 以及构造器参数中attributes的键名
   */
  public static final List<String> ATTRIBUTE_NAMES =
    Arrays.asList("发站", "到站",
      "发货人", "发货人电话", "收货人", "收货人电话",
      "客户单号", "付款方式", "返款方", "返款方式1", "返款方式2");

  /**
   * 可用的费用名称, 作为getFee的参数, 以及构造器中fee的键名
   */
  public static final List<String> FEE_NAMES =
    Arrays.asList("总运费", "代收款", "返款费", "保价费", "接货费", "送货费");

  private Map<String, String> attributes;
  private Map<String, Integer> fees;
  private List<Staff> staff;

  /**
   * @param attributes 属性的键值对Map, 见ATTRIBUTE_NAMES
   * @param fees 费用的键值对Map, 见FEE_NAMES
   * @param staff 商品的List
   */
  public Order(Map<String, String> attributes, Map<String, Integer> fees, List<Staff> staff) {
    this.attributes = attributes;
    this.fees = fees;
    this.staff = staff;
  }

  public List<Staff> getStaff() {
    return staff;
  }

  public String getAttribute(String name) throws IllegalArgumentException {
    if (!ATTRIBUTE_NAMES.contains(name))
      throw new IllegalArgumentException("该属性名称不存在: " + name);
    String attr = attributes.get(name);
    if (attr == null)
      attr = "";
    return attr;
  }

  public int getFee(String name) throws IllegalArgumentException {
    if (!FEE_NAMES.contains(name))
      throw new IllegalArgumentException("该费用名称不存在: " + name);
    if (name.equals("总运费")) {
      int tot = 0;
      for (Staff s: staff) {
        tot += s.getNumber() * s.getPrice();
      }
      return tot;
    }
    Integer fee = fees.get(name);
    if (fee == null)
      fee = 0;
    return fee;
  }

  public int getTotalFee(){
    int tot = 0;
    for (String n: ATTRIBUTE_NAMES) {
      tot += getFee(n);
    }
    return tot;
  }

  public int getTotalNumber() {
    int tot = 0;
    for (Staff s : staff) {
      tot += s.getNumber();
    }
    return tot;
  }
}


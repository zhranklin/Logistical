package com.logistical.model;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Order为订单的模型类, 用来表示一个订单的各项信息, 包括各种属性(字符串)以及各种费用(int), 还有所包含的商品用Staff List表示
 * 在scala中(同一个包)写了一个Staff类, 在这里一并说明, Staff类表示商品, 包含四个属性:
 * type 大类, subType 小类, number 数量, price 单价
 * 在构造器中需要直接提供这四个属性(String String int int)
 * 获取这四个属性调用type()等方法即可
 * 注: Order和Staff均实现了自己的equals方法(内容相同则值相等), 且Order没实现hashCode(), 如果要作为map的键, 请考虑IdentityHashMap
 */
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

  /**
   * 获得所有的商品Staff对象
   * @return 一个Staff的List
   */
  public List<Staff> getStaff() {
    return staff;
  }

  /**
   * 获取订单的某个属性的值
   * @param name 表示需要的属性名称
   * @return 属性该属性的值
   * @throws IllegalArgumentException 如果这个属性名称不存在, 就会抛出
   */
  public String getAttribute(String name) throws IllegalArgumentException {
    if (!ATTRIBUTE_NAMES.contains(name))
      throw new IllegalArgumentException("该属性名称不存在: " + name);
    String attr = attributes.get(name);
    if (attr == null)
      attr = "";
    return attr;
  }

  /**
   * 返回订单的某个费用的值, 返回类型为int
   * @param name 费用的名称
   * @return 该费用的数值
   * @throws IllegalArgumentException 当该费用名称不存在时, 就会抛出
   */
  public int getFee(String name) throws IllegalArgumentException {
    if (!FEE_NAMES.contains(name))
      throw new IllegalArgumentException("该费用名称不存在: " + name);
    if (name.equals("总运费")) {
      int tot = 0;
      for (Staff s: staff) {
        tot += s.number() * s.price();
      }
      return tot;
    }
    Integer fee = fees.get(name);
    if (fee == null)
      fee = 0;
    return fee;
  }

  /**
   * 所有费用总和
   * @return 所有费用加起来的总和, 包括运费和各种杂项费用
   */
  public int getTotalFee(){
    int tot = 0;
    for (String n: ATTRIBUTE_NAMES) {
      tot += getFee(n);
    }
    return tot;
  }

  /**
   * 返回总件数, 由所有的Staff中的数量相加而得
   * @return 总件数
   */
  public int getTotalNumber() {
    int tot = 0;
    for (Staff s : staff) {
      tot += s.number();
    }
    return tot;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Order))
      return false;
    Order o = (Order) obj;
    for (String attrName: ATTRIBUTE_NAMES)
      if (!getAttribute(attrName).equals(o.getAttribute(attrName)))
        return false;
    for (String feeName: FEE_NAMES)
      if (getFee(feeName) != o.getFee(feeName))
        return false;
    return Objects.equals(staff, o.getStaff());
  }
}

